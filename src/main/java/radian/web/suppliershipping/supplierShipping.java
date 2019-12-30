package radian.web.suppliershipping;

import com.tcmis.common.admin.beans.PersonnelBean;
import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.*;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2004
 * Company:      Haas TCM
 * @author       Nawaz Shaik
 * @version
 *
 * 09-06-05 - Fixed a null pointer exception when there are no POs ever cut
 * with the supplier id which is setup as a virtual hub supplier.
 */

public class supplierShipping {
 private String thedecidingRandomNumber = null;
 private TcmISDBModel db = null;
 private PrintWriter out = null;
 private supplierShippingdbObj suppObj = null;
// private dropShipReceivingTables hubObj = null;
 private boolean completeSuccess = true;
 private boolean noneToUpd = true;
 protected boolean allowupdate;
 private static org.apache.log4j.Logger reclog = org.apache.log4j.Logger.getLogger(
	supplierShipping.class);
 private boolean intcmIsApplication = false;

 public void setupdateStatus(boolean id) {
	this.allowupdate = id;
 }

 private boolean getupdateStatus() throws Exception {
	return this.allowupdate;
 }

 public supplierShipping(TcmISDBModel d) {
	db = d;
 }

 //Process the HTTP Get request
 public void doGet(HttpServletRequest request,
	HttpServletResponse response) throws ServletException,
	IOException {
	doPost(request, response);
 }

 //Process the HTTP Post request
 public void doPost(HttpServletRequest request,
	HttpServletResponse response) throws ServletException, IOException {
	out = response.getWriter();
	response.setContentType("text/html");
	HttpSession session = request.getSession();
	suppObj = new supplierShippingdbObj(db);

	PersonnelBean personnelBean = (PersonnelBean) session.getAttribute("personnelBean");
	Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();

	if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
	{
	 intcmIsApplication = true;
	}

	String personnelid = session.getAttribute("PERSONNELID") == null ? "" :
	 session.getAttribute("PERSONNELID").toString();

	String generate_labels = request.getParameter("generate_labels");
	if (generate_labels == null)
	 generate_labels = "No";

	String User_Session = request.getParameter("session");
	if (User_Session == null)
	 User_Session = "New";

	String User_Action = request.getParameter("UserAction");
	if (User_Action == null)
	 User_Action = "New";

	String User_Search = request.getParameter("SearchField");
	if (User_Search == null)
	 User_Search = "";

	String User_Sort = request.getParameter("SortBy");
	if (User_Sort == null)
	 User_Sort = "RADIAN_PO";

	String searchlike = request.getParameter("searchlike");
	if (searchlike == null) {
	 searchlike = "";
	}
	searchlike = searchlike.trim();

	String searchfor = request.getParameter("searchfor");
	if (searchfor == null) {
	 searchfor = "";
	}
	searchfor = searchfor.trim();
	if (searchlike.length() == 0) {
	 searchlike = "RADIAN_PO";
	}

	thedecidingRandomNumber = request.getParameter("thedecidingRandomNumber");
	if (thedecidingRandomNumber == null)
	 thedecidingRandomNumber = "";
	thedecidingRandomNumber = thedecidingRandomNumber.trim();

	String SubUser_Action = request.getParameter("SubUserAction");
	if (SubUser_Action == null)
	 SubUser_Action = "JSError2";

	String shipto = request.getParameter("shipto");
	if (shipto == null)
	 shipto = "";
	shipto = shipto.trim();

	String supplier = request.getParameter("supplier");
	if (supplier == null)
	 supplier = "";
	supplier = supplier.trim();

	String showhistory = request.getParameter("showhistory");
	if (showhistory == null)
	 showhistory = "";
	showhistory = showhistory.trim();

	reclog.debug("supplier  " + supplier + "  shipto  " + shipto + "   User_Action  " +
	 User_Action + "");

	try {
	 Random rand = new Random();
	 int tmpReq = rand.nextInt();
	 Integer tmpReqNum = new Integer(tmpReq);

	 if (! (SubUser_Action.equalsIgnoreCase("DuplLine"))) {
		if (thedecidingRandomNumber.length() > 0) {
		 String randomnumberfromsesion = session.getAttribute("SUPSHIPRNDCOOKIE") == null ?
			"" : session.getAttribute("SUPSHIPRNDCOOKIE").toString();
		 if (randomnumberfromsesion.equalsIgnoreCase(thedecidingRandomNumber)) {
			thedecidingRandomNumber = tmpReqNum.toString();
			session.setAttribute("SUPSHIPRNDCOOKIE", thedecidingRandomNumber);
		 }
		 else {
			thedecidingRandomNumber = tmpReqNum.toString();
			session.setAttribute("SUPSHIPRNDCOOKIE", thedecidingRandomNumber);
			session.setAttribute("SUPPSHIP_DATA", new Vector());
			Hashtable hub_list_set = (Hashtable) session.getAttribute("SUPPLIER_SET");
			Hashtable iniinvendata = (Hashtable) session.getAttribute("SHIPTO_DATA");

			buildHeader(null, hub_list_set, supplier, User_Search, User_Sort, "", iniinvendata,
			 shipto, searchlike, searchfor, showhistory, session);
			out.println("<BR><BR><CENTER><FONT SIZE=\"4\" FACE=\"Arial\" COLOR=\"#fc0303\"><B>Please Do Not Use The Back Button of The Browser</B><BR> Thanks.</FONT>");
			return;
		 }
		}
		else {
		 thedecidingRandomNumber = tmpReqNum.toString();
		 session.setAttribute("SUPSHIPRNDCOOKIE", thedecidingRandomNumber);
		}
	 }

	 if (User_Session.equalsIgnoreCase("New")) {
		String CompanyID = session.getAttribute("COMPANYID") == null ? "" :
		 session.getAttribute("COMPANYID").toString();
		Hashtable supplier_list = new Hashtable();
		supplier_list = radian.web.HTMLHelpObj.createSupplierList(db, personnelid, CompanyID);

		Hashtable initialshiptodata = new Hashtable();
		if (supplier_list.size() > 0) {
		 initialshiptodata = radian.web.HTMLHelpObj.getShiptoInitialData(db, supplier_list, true);
		 session.setAttribute("SHIPTO_DATA", initialshiptodata);
		}

		if (supplier_list.size() < 1) {
		 buildHeader(null, supplier_list, "", User_Search, User_Sort, "", initialshiptodata,
			shipto, searchlike, searchfor, showhistory, session);
		 out.println(radian.web.HTMLHelpObj.printHTMLNoFacilities());
		 supplier_list = null;
		 out.close();
		}
		else {
		 session.setAttribute("SUPPLIER_SET", supplier_list);
		 buildHeader(null, supplier_list, "", User_Search, User_Sort, "", initialshiptodata,
			shipto, searchlike, searchfor, showhistory, session);
		 out.println(radian.web.HTMLHelpObj.printHTMLSelect());
		 out.close();
		 supplier_list = null;
		}
	 }
	 else if ( (User_Session.equalsIgnoreCase("Active")) &&
		(User_Action.equalsIgnoreCase("New"))) {
		//read data and build page
		Hashtable supplier_list = (Hashtable) session.getAttribute("SUPPLIER_SET");
		Hashtable iniinvendata = (Hashtable) session.getAttribute("SHIPTO_DATA");

		Vector openorder_data = new Vector();
		openorder_data = suppObj.getAllopenOrder(supplier, User_Search, User_Sort, shipto,
		 searchlike, searchfor, showhistory);

		Hashtable sum = (Hashtable) (openorder_data.elementAt(0));
		int count = ( (Integer) sum.get("TOTAL_NUMBER")).intValue();
		if (0 == count) {
		 buildHeader(null, supplier_list, supplier, User_Search, User_Sort, "", iniinvendata,
			shipto, searchlike, searchfor, showhistory, session);
		 out.println(radian.web.HTMLHelpObj.printHTMLNoData("No Items Found"));
		 out.close();
		 //clean up
		 openorder_data = null;
		 supplier_list = null;
		}
		else {
		 session.setAttribute("SUPPSHIP_DATA", openorder_data);

		 buildHeader(openorder_data, supplier_list, supplier, User_Search, User_Sort, "",
			iniinvendata, shipto, searchlike, searchfor, showhistory, session);
		 buildDetails(openorder_data, "showorigstuff");

		 out.close();
		 //clean up
		 openorder_data = null;
		 supplier_list = null;
		} //when there are open orders for hub
	 }
	 else if (SubUser_Action.equalsIgnoreCase("PrintBOXLabels") ||
		SubUser_Action.equalsIgnoreCase("reprintlabels")) {
		Vector synch_data = (Vector) session.getAttribute("SUPPSHIP_DATA");
		Vector print_data = SynchprintData(request, synch_data);
		Vector synch_data1 = SynchServerData(request, synch_data);
		session.setAttribute("PRINTDATA", print_data);

		Hashtable supplier_list = (Hashtable) session.getAttribute("SUPPLIER_SET");
		Hashtable iniinvendata = (Hashtable) session.getAttribute("SHIPTO_DATA");

		buildHeader(synch_data1, supplier_list, supplier, User_Search, User_Sort,
		 SubUser_Action, iniinvendata, shipto, searchlike, searchfor, showhistory, session);
		buildDetails(synch_data, SubUser_Action);
	 }
	 else if ( (User_Session.equalsIgnoreCase("Active")) &&
		(User_Action.equalsIgnoreCase("Update"))) {
		Vector retrn_data = (Vector) session.getAttribute("SUPPSHIP_DATA");
		Vector synch_data = SynchServerData(request, retrn_data);
		Hashtable iniinvendata = (Hashtable) session.getAttribute("SHIPTO_DATA");

		retrn_data = null;

		if (SubUser_Action.equalsIgnoreCase("DuplLine")) {
		 String dupl_line = BothHelpObjs.makeBlankFromNull(request.getParameter(
			"DuplLineNumber"));
		 Vector new_data = createDuplicate(dupl_line, synch_data);
		 session.setAttribute("SUPPSHIP_DATA", new_data);
		 Hashtable supplier_list = (Hashtable) session.getAttribute("SUPPLIER_SET");

		 buildHeader(synch_data, supplier_list, supplier, User_Search, User_Sort, "",
			iniinvendata, shipto, searchlike, searchfor, showhistory, session);
		 buildDetails(new_data, SubUser_Action);

		 out.close();
		 //clean up
		 synch_data = null;
		 new_data = null;
		}
		else if (SubUser_Action.equalsIgnoreCase("UpdPage")) {
		 Hashtable supplier_list = (Hashtable) session.getAttribute("SUPPLIER_SET");
		 session.setAttribute("SUPPSHIP_DATA", synch_data);

		 Hashtable updateresults = UpdateDetails(synch_data, supplier, personnelid);
		 Boolean result = (Boolean) updateresults.get("RESULT");
		 Vector errordata = (Vector) updateresults.get("ERRORDATA");
		 Vector errMsgs = suppObj.getErrMsgs();

		 session.setAttribute("SUPPSHIP_DATA", errordata);
		 boolean resulttotest = result.booleanValue();

		 if (false == resulttotest) {
			buildHeader(errordata, supplier_list, supplier, User_Search, User_Sort, "",
			 iniinvendata, shipto, searchlike, searchfor, showhistory, session);
			if (true == noneToUpd) {
			 out.println(radian.web.HTMLHelpObj.printMessageinTable(
				"No Item was Choosen for Supplier Shipping"));
			 buildDetails(errordata, SubUser_Action);
			}
			else {
			 //out.println( radian.web.HTMLHelpObj.printMessageinTable("An Error Occurred.<BR>Please Check Data and try Again." ) );
			 out.println(radian.web.HTMLHelpObj.printHTMLPartialSuccess(errMsgs));
			 buildDetails(errordata, SubUser_Action);
			}
			out.close();
		 }
		 else {
			//contains a list of a list of lot seq that were added to receiving table
			Vector vList = suppObj.getLotSeqList();
			int size = vList.size();

			if (size > 0) {
			 session.setAttribute("REC_LABEL_SEQUENCE_NUMBERS", vList);
			}

			if (true == completeSuccess) {
			 if (size > 0) {
				buildHeader(errordata, supplier_list, supplier, User_Search, User_Sort,
				 "GENERATE_LABELS", iniinvendata, shipto, searchlike, searchfor, showhistory,
				 session);
			 }
			 else {
				buildHeader(errordata, supplier_list, supplier, User_Search, User_Sort, "",
				 iniinvendata, shipto, searchlike, searchfor, showhistory, session);
			 }

			 out.println(radian.web.HTMLHelpObj.printMessageinTable(
				"All Your Selections Were Successfully Updated"));

			 buildDetails(errordata, SubUser_Action);
			 out.close();
			}
			else {
			 Hashtable recsum1 = new Hashtable();
			 recsum1.put("TOTAL_NUMBER", new Integer(errordata.size() - 1));
			 errordata.setElementAt(recsum1, 0);

			 if (size > 0) {
				buildHeader(errordata, supplier_list, supplier, User_Search, User_Sort,
				 "GENERATE_LABELS", iniinvendata, shipto, searchlike, searchfor, showhistory,
				 session);
			 }
			 else {
				buildHeader(errordata, supplier_list, supplier, User_Search, User_Sort, "",
				 iniinvendata, shipto, searchlike, searchfor, showhistory, session);
			 }

			 out.println(radian.web.HTMLHelpObj.printHTMLPartialSuccess(errMsgs));
			 buildDetails(errordata, SubUser_Action);
			 out.close();
			}
		 }
		 //clean up
		 synch_data = null;
		 updateresults = null;
		 errordata = null;
		}
		else {
		 out.println(radian.web.HTMLHelpObj.printError("tcmIS Supplier Shipping",intcmIsApplication));
		}

	 } //End of User_session="Active" && User_Action="Update"
	 else {
		out.println(radian.web.HTMLHelpObj.printError("tcmIS Supplier Shipping",intcmIsApplication));
	 }
	}
	catch (Exception e) {
	 e.printStackTrace();
	 out.println(radian.web.HTMLHelpObj.printError("tcmIS Supplier Shipping",intcmIsApplication));
	}
	//clean up
	suppObj = null;
	return;
 }

 private Vector createDuplicate(String dupl_line, Vector in_data) {
	int line_to_dupl = (new Integer(dupl_line)).intValue();
	int size = in_data.size();
	Vector new_data = new Vector();

	try {
	 for (int i = 0; i < size; i++) {
		if (i == 0) {
		 Hashtable sum = (Hashtable) (in_data.elementAt(i));
		 int count = ( (Integer) sum.get("TOTAL_NUMBER")).intValue();
		 sum.put("TOTAL_NUMBER", new Integer(count + 1));
		 new_data.addElement(sum);
		}
		else {
		 Hashtable temp = (Hashtable) (in_data.elementAt(i));
		 new_data.addElement(temp);
		 if (i == line_to_dupl) {
			Hashtable hDupl = new Hashtable();
			hDupl.put("SUPPLIER_PARENT", temp.get("SUPPLIER_PARENT"));
			hDupl.put("SUPPLIER", temp.get("SUPPLIER"));
			hDupl.put("SHIP_TO_COMPANY_ID", temp.get("SHIP_TO_COMPANY_ID"));
			hDupl.put("SHIP_TO_LOCATION_ID", temp.get("SHIP_TO_LOCATION_ID"));
			hDupl.put("MR_NUMBER", temp.get("MR_NUMBER"));
			hDupl.put("MR_LINE_ITEM", temp.get("MR_LINE_ITEM"));
			hDupl.put("REQUESTOR_NAME", temp.get("REQUESTOR_NAME"));
			hDupl.put("APPLICATION", temp.get("APPLICATION"));
			hDupl.put("DELIVERY_POINT", temp.get("DELIVERY_POINT"));
			hDupl.put("CUSTOMER_PO", temp.get("CUSTOMER_PO"));
			hDupl.put("PURCHASE_ORDER", temp.get("PURCHASE_ORDER"));
			hDupl.put("PURCHASE_ORDER_LINE", temp.get("PURCHASE_ORDER_LINE"));
			hDupl.put("QTY_OPEN", temp.get("QTY_OPEN"));
			hDupl.put("ITEM_ID", temp.get("ITEM_ID"));
			hDupl.put("EXPECTED", temp.get("EXPECTED"));
			//hDupl.put( "QTY",temp.get( "QTY" ) );
			hDupl.put("BRANCH_PLANT", temp.get("BRANCH_PLANT"));
			hDupl.put("ITEM_DESCRIPTION", temp.get("ITEM_DESCRIPTION"));
			//hDupl.put( "PO_NOTES",temp.get( "PO_NOTES" ) );
			hDupl.put("TRANS_TYPE", temp.get("TRANS_TYPE"));
			//hDupl.put( "SUPPLIER_NAME",temp.get( "SUPPLIER_NAME" ) );
			hDupl.put("INDEFINITE_SHELF_LIFE", temp.get("INDEFINITE_SHELF_LIFE"));
			hDupl.put("CRITICAL", temp.get("CRITICAL"));
			//hDupl.put( "ORIGINAL_MFG_LOT",temp.get( "ORIGINAL_MFG_LOT" ) );
			//hDupl.put( "INVENTORY_GROUP",temp.get( "INVENTORY_GROUP" ) );
			hDupl.put("MANAGE_KITS_AS_SINGLE_UNIT", temp.get("MANAGE_KITS_AS_SINGLE_UNIT"));
			hDupl.put("COMPONENT_ID", temp.get("COMPONENT_ID"));
			hDupl.put("MATERIAL_ID", temp.get("MATERIAL_ID"));
			hDupl.put("PACKAGING", temp.get("PACKAGING"));
			hDupl.put("MATERIAL_DESC", temp.get("MATERIAL_DESC"));
			hDupl.put("NUMBER_OF_KITS", temp.get("NUMBER_OF_KITS"));
			/*hDupl.put( "LOCATION_SHORT_NAME",temp.get( "LOCATION_SHORT_NAME" ) );
						hDupl.put( "LOCATION_DESC",temp.get( "LOCATION_DESC" ) );
						hDupl.put( "ADDRESS_LINE_1",temp.get( "ADDRESS_LINE_1" ) );
						hDupl.put( "ADDRESS_LINE_2",temp.get( "ADDRESS_LINE_2" ) );
						hDupl.put( "ADDRESS_LINE_3",temp.get( "ADDRESS_LINE_3" ) );
						hDupl.put( "CITY",temp.get( "CITY" ) );
						hDupl.put( "STATE_ABBREV",temp.get( "STATE_ABBREV" ) );
						hDupl.put( "ZIP",temp.get( "ZIP" ) );
						hDupl.put( "SYS_DATE",temp.get( "SYS_DATE" ) );  */
			hDupl.put("SHELF_LIFE_DAYS", temp.get("SHELF_LIFE_DAYS"));
			hDupl.put("SHELF_LIFE_BASIS", temp.get("SHELF_LIFE_BASIS"));
			hDupl.put("BASIS_DATE", temp.get("BASIS_DATE"));
			hDupl.put("MFG_LOT", "");
			hDupl.put("QTY_RECD", "");
			hDupl.put("USERCHK", "");
			hDupl.put("DATE_RECIEVED", temp.get("DATE_RECIEVED"));
			hDupl.put("EXPIRE_DATE", "");
			hDupl.put("DATE_OF_SHIPMENT", "");
			hDupl.put("DATE_MFGD", "");
			hDupl.put("RECEIPT_ID", temp.get("RECEIPT_ID"));
			hDupl.put("LINE_STATUS", temp.get("LINE_STATUS"));
			hDupl.put("ACTUAL_SHIP_DATE", temp.get("ACTUAL_SHIP_DATE"));
			hDupl.put("CAT_PART_NO", temp.get("CAT_PART_NO"));
			hDupl.put("LABEL_QTY", "");
			hDupl.put("CONTAINER_LABEL", temp.get("CONTAINER_LABEL"));
			hDupl.put("BIN_NAME", temp.get("BIN_NAME"));
			hDupl.put("DROP_SHIP", temp.get("DROP_SHIP"));
      hDupl.put("NOTES", temp.get("NOTES"));

			new_data.addElement(hDupl);
		 } //end of last if
		} //end of else
	 } //end of for
}
	catch (Exception e) {
	 e.printStackTrace();
}

	return new_data;
 }

 private Vector SynchprintData(HttpServletRequest request, Vector in_data) {
	Vector new_data = new Vector();
	Hashtable sum = (Hashtable) (in_data.elementAt(0));
	int count = ( (Integer) sum.get("TOTAL_NUMBER")).intValue();
	Vector prnumbers = new Vector();
	Vector linenumber = new Vector();
	Vector partnumber = new Vector();
	Vector labelqty = new Vector();
	Vector dropShipVector = new Vector();
	Vector radianPoVector = new Vector();
	Vector poLineVector = new Vector();
    Vector inventoryGroupVector = new Vector();
    Vector itemIdVector = new Vector();

    Hashtable FResult = new Hashtable();

	try {
	 for (int i = 1; i < count + 1; i++) {
		Hashtable hD = new Hashtable();
		hD = (Hashtable) (in_data.elementAt(i));

		String prnumber = hD.get("MR_NUMBER").toString();
		String lineitem = hD.get("MR_LINE_ITEM").toString();
		String partnumberstg = hD.get("CAT_PART_NO").toString();
		String dropShip = hD.get("DROP_SHIP").toString();
		String radianPo = hD.get("PURCHASE_ORDER").toString();
		String poLine = hD.get("PURCHASE_ORDER_LINE").toString();
        String inventoryGroup = hD.get("INVENTORY_GROUP").toString();
        String itemId = hD.get("ITEM_ID").toString();
                                              
        String lbl_qty = "";
		lbl_qty = BothHelpObjs.makeBlankFromNull(request.getParameter("lbl_qty" + i));
		hD.remove("LABEL_QTY");
		hD.put("LABEL_QTY", lbl_qty);

		if (lbl_qty.length() > 0) {
		 prnumbers.addElement(prnumber);
		 linenumber.addElement(lineitem);
		 partnumber.addElement(partnumberstg);
		 labelqty.addElement(lbl_qty);
		 dropShipVector.addElement(dropShip);
		 radianPoVector.addElement(radianPo);
		 poLineVector.addElement(poLine);
         inventoryGroupVector.addElement(inventoryGroup);
         itemIdVector.addElement(itemId);
        }
	 }
	}
	catch (Exception e) {
	 e.printStackTrace();
	 out.println(radian.web.HTMLHelpObj.printError("tcmIS Supplier Shipping",intcmIsApplication));
	}

	FResult.put("mr_number", prnumbers);
	FResult.put("line_number", linenumber);
	FResult.put("part_number", partnumber);
	FResult.put("lbl_qty", labelqty);
	FResult.put("dropShip", dropShipVector);
	FResult.put("radianPo", radianPoVector);
	FResult.put("poLine", poLineVector);
    FResult.put("inventoryGroup", inventoryGroupVector);
    FResult.put("itemId", itemIdVector);

    new_data.add(FResult);
	return new_data;
 }

 private Vector SynchServerData(HttpServletRequest request, Vector in_data) {
	Vector new_data = new Vector();
	Hashtable sum = (Hashtable) (in_data.elementAt(0));
	int count = ( (Integer) sum.get("TOTAL_NUMBER")).intValue();
	new_data.addElement(sum);

	try {
	 for (int i = 1; i < count + 1; i++) {
		Hashtable hD = new Hashtable();
		hD = (Hashtable) (in_data.elementAt(i));

		String check = "";
		check = BothHelpObjs.makeBlankFromNull(request.getParameter("row_chk" + i));
		hD.remove("USERCHK");
		hD.put("USERCHK", check);

		String lbl_qty = "";
		lbl_qty = BothHelpObjs.makeBlankFromNull(request.getParameter("lbl_qty" + i));
		hD.remove("LABEL_QTY");
		hD.put("LABEL_QTY", lbl_qty);

		String qty_open = hD.get("QTY_OPEN") == null ? "&nbsp;" : hD.get("QTY_OPEN").toString();
		int qtyopen = Integer.parseInt(qty_open);
		if (this.getupdateStatus() && qtyopen > 0) {
		 String mfg_lot = "";
		 mfg_lot = BothHelpObjs.makeBlankFromNull(request.getParameter("mfg_lot" + i));
		 hD.remove("MFG_LOT");
		 hD.put("MFG_LOT", mfg_lot);

		 String date_recieved = "";
		 date_recieved = BothHelpObjs.makeBlankFromNull(request.getParameter("date_recieved" +
			i));
		 hD.remove("DATE_RECIEVED");
		 hD.put("DATE_RECIEVED", date_recieved);

		 String actshipDate = "";
		 actshipDate = BothHelpObjs.makeBlankFromNull(request.getParameter(
			"act_suppship_date" + i));
		 hD.remove("ACTUAL_SHIP_DATE");
		 hD.put("ACTUAL_SHIP_DATE", actshipDate);

		 String qty_recd = "";
		 qty_recd = BothHelpObjs.makeBlankFromNull(request.getParameter("qty_recd" + i));
		 hD.remove("QTY_RECD");
		 hD.put("QTY_RECD", qty_recd);

		 String expiry_date = "";
		 expiry_date = BothHelpObjs.makeBlankFromNull(request.getParameter("expiry_date" + i));

		 if ("Indefinite".equalsIgnoreCase(expiry_date)) {
			expiry_date = "01/01/3000";
		 }
		 hD.remove("EXPIRE_DATE");
		 hD.put("EXPIRE_DATE", expiry_date);

		 String basis_date = "";
		 basis_date = BothHelpObjs.makeBlankFromNull(request.getParameter("basis_date" + i));
		 hD.remove("BASIS_DATE");
		 hD.put("BASIS_DATE", basis_date);
		}

     String recnotes = "";
		 recnotes = BothHelpObjs.makeBlankFromNull(request.getParameter("recnotes" + i));
		 hD.remove("NOTES");
		 hD.put("NOTES", recnotes);

    new_data.addElement(hD);
	 }
	}
	catch (Exception e) {
	 e.printStackTrace();
	 out.println(radian.web.HTMLHelpObj.printError("tcmIS Supplier Shipping",intcmIsApplication));
	}
	return new_data;
 }

 private Hashtable UpdateDetails(Vector data, String BranchPlant, String logonid) {
	boolean result = false;
	Hashtable updateresult = new Hashtable();
	Vector errordata = new Vector();
	suppObj = new supplierShippingdbObj(db);

	try {
	 Hashtable summary = new Hashtable();
	 summary = (Hashtable) data.elementAt(0);
	 int total = ( (Integer) (summary.get("TOTAL_NUMBER"))).intValue();
	 errordata.addElement(summary);
	 int linecheckcount = 0;
	 int lastItemNum = 1;
	 String returnedReceiptId1 = "";
	 //String returnedReceiptId2="";
	 boolean callreccomp = false;
	 boolean one_success = false;

	 for (int i = 1; i < total + 1; i++) {
		Hashtable hD = new Hashtable();
		hD = (Hashtable) data.elementAt(i);

		String Line_Check = "";
		Line_Check = (hD.get("USERCHK") == null ? "&nbsp;" : hD.get("USERCHK").toString());
		String lineStatus = hD.get("LINE_STATUS") == null ? "" : hD.get("LINE_STATUS").toString();
		String trans_type = (hD.get("TRANS_TYPE") == null ? " " :
		 hD.get("TRANS_TYPE").toString());
		String Purchase_order = (hD.get("PURCHASE_ORDER") == null ? "" :
		 hD.get("PURCHASE_ORDER").toString());
		String PO_Line = (hD.get("PURCHASE_ORDER_LINE") == null ? "" :
		 hD.get("PURCHASE_ORDER_LINE").toString());
		String Item_id = (hD.get("ITEM_ID") == null ? "" : hD.get("ITEM_ID").toString());
		String mngkitassingl = hD.get("MANAGE_KITS_AS_SINGLE_UNIT").toString();
		String componentid = hD.get("COMPONENT_ID").toString();

		String Next_po = "";
		String Next_poline = "";
		String Next_item = "";
		if (! (i == total)) {
		 Hashtable hDNext = new Hashtable();
		 hDNext = (Hashtable) data.elementAt(i + 1);
		 Next_po = hDNext.get("PURCHASE_ORDER") == null ? "&nbsp;" :
			hDNext.get("PURCHASE_ORDER").toString();
		 Next_poline = hDNext.get("PURCHASE_ORDER_LINE") == null ? "&nbsp;" :
			hDNext.get("PURCHASE_ORDER_LINE").toString();
		 Next_item = hDNext.get("ITEM_ID") == null ? "&nbsp;" :
			hDNext.get("ITEM_ID").toString();
		}
		else {
		 Next_po = " ";
		 Next_poline = "";
		 Next_item = " ";
		}

		boolean Samepoline = false;
		boolean FirstRow = false;
		if ("N".equalsIgnoreCase(mngkitassingl) && Next_po.equalsIgnoreCase(Purchase_order) &&
		 Next_poline.equalsIgnoreCase(PO_Line) && Next_item.equalsIgnoreCase(Item_id)) {
		 Samepoline = true;
		 if (Line_Check.equalsIgnoreCase("yes")) {
			callreccomp = true;
		 }
		 lastItemNum++;
		}
		else {
		 lastItemNum = 1;
		}

		if (Samepoline) {
		 if (lastItemNum == 2) {
			FirstRow = true;
		 }
		}
		else if ("Y".equalsIgnoreCase(mngkitassingl)) {
		 FirstRow = true;
		}

		if ( (Line_Check.equalsIgnoreCase("yes") || callreccomp) && !"Yes".equalsIgnoreCase(lineStatus)) {
		 noneToUpd = false;
		 linecheckcount++;
		 Hashtable ResultH = new Hashtable();
		 boolean line_result = false;

		 if (FirstRow) {
			ResultH = suppObj.UpdateLine(hD, BranchPlant, logonid); // update database

			Boolean resultline = (Boolean) ResultH.get("RESULT");
			line_result = resultline.booleanValue();
			returnedReceiptId1 = ResultH.get("RCECIPT_ID").toString();
		 }

		 if ("N".equalsIgnoreCase(mngkitassingl) && callreccomp) {
			reclog.info("Calling p_receipt_component for COMPONENT_ID " + componentid +
			 " RECEIPT_ID1 " + returnedReceiptId1 + " Personnel ID  " + logonid + "");
			line_result = radian.web.HTMLHelpObj.insreceiptcomp(db, hD, returnedReceiptId1,
			 logonid);
		 }

		 if (!line_result) {
			completeSuccess = false;
			hD.remove("USERCHK");
			hD.put("USERCHK", " ");

			hD.remove("LINE_STATUS");
			hD.put("LINE_STATUS", "NO");

			errordata.addElement(hD);
		 }
		 else {
			one_success = true;
			hD.remove("LINE_STATUS");
			hD.put("LINE_STATUS", "YES");

			if (!trans_type.equalsIgnoreCase("IT")) {
			 hD.remove("RECEIPT_ID");
			 hD.put("RECEIPT_ID", returnedReceiptId1);
			}
			errordata.addElement(hD);
		 }
		 ResultH = null;
		}
		else {
		 errordata.addElement(hD);
		}

		if (!Samepoline) {
		 returnedReceiptId1 = "";
		 callreccomp = false;
		}
	 } //end of for

	 if (linecheckcount == 1) {
		result = true;
	 }
	 if (true == one_success) {
		result = true;
	 }
	}
	catch (Exception e) {
	 result = false;
	 e.printStackTrace();
	 out.println(radian.web.HTMLHelpObj.printError("tcmIS Supplier Shipping",intcmIsApplication));
	}

	updateresult.put("RESULT", new Boolean(result));
	updateresult.put("ERRORDATA", errordata);
	return updateresult;
 }

 private void buildHeader(Vector datwa, Hashtable supplier_list, String selsupplier,
	String search_text, String sortby, String buildlabels,
	Hashtable initialshiptodata, String selshipto, String searchLike, String searchfor,
	String showhistory, HttpSession session) {
	try {
	 out.println(radian.web.HTMLHelpObj.printHTMLHeader("Supplier Shipping",
		"suppliershipping.js",intcmIsApplication));
	 out.println(
		"<SCRIPT SRC=\"/clientscripts/FormChek.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
	 out.println("</HEAD>  \n");
	 out.println( "<SCRIPT SRC=\"/js/calendar/newcalendar.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n" );
	 out.println( "<SCRIPT SRC=\"/js/calendar/AnchorPosition.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n" );
	 out.println( "<SCRIPT SRC=\"/js/calendar/PopupWindow.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n" );

	 if ("GENERATE_LABELS".equalsIgnoreCase(buildlabels)) {
		out.println("<BODY onLoad=\"showrecrecipts()\">\n");
	 }
	 else if ("printboxlabels".equalsIgnoreCase(buildlabels)) {
		out.println("<BODY onLoad=\"doPrintbox()\">\n");
	 }
	 else if ("reprintlabels".equalsIgnoreCase(buildlabels)) {
		out.println("<BODY onLoad=\"doPrintrelabel()\">\n");
	 }
	 else {
		out.println("<BODY>\n");
	 }

	 out.println("<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n");
	 out.println("<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>Please wait while the page is updated...</B></FONT></center>\n");
	 out.println("</DIV>\n");
	 out.println("<DIV ID=\"MAINPAGE\" STYLE=\"\">\n");

	 out.println(radian.web.HTMLHelpObj.printSupplierTitleBar("Supplier Shipping\n"));

	 if (supplier_list.size() < 1) {
		out.println(radian.web.HTMLHelpObj.printMessageinTable(
		 "You don't have access to this page.<BR><BR>Contact tech support for more information."));
		return;
	 }

	 out.println("<SCRIPT LANGUAGE=\"JavaScript\" TYPE=\"text/javascript\">\n <!--\n");
	 out.println(radian.web.HTMLHelpObj.createshiptoinvgrjs(initialshiptodata));
	 out.println("// -->\n </SCRIPT>\n");

	 out.println("<FORM method=\"POST\" NAME=\"suppliershipping\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\"" +
		radian.web.tcmisResourceLoader.getsuppliershipping() + "\">\n");
	 //start table #1
	 out.println("<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"100%\" CLASS=\"moveup\">\n");
	 out.println("<TR VALIGN=\"MIDDLE\">\n");
	 //Hub

	 out.println("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
	 out.println("<B>Supplier:</B>&nbsp;\n");
	 out.println("</TD>\n");
	 out.println("<TD WIDTH=\"10%\" HEIGHT=\"35\">\n");
	 out.println("<SELECT CLASS=\"HEADER\" NAME=\"supplier\" ID=\"supplier\" OnChange=\"suppchanged(document.suppliershipping.supplier)\" size=\"1\">\n");

	 for (Enumeration e = supplier_list.keys(); e.hasMoreElements(); ) {
		String branch_plant = (String) (e.nextElement());
		String hub_name = (String) (supplier_list.get(branch_plant));

		if (selsupplier.trim().length() == 0) {
		 selsupplier = branch_plant;
		}

		String hub_selected = "";
		if (branch_plant.equalsIgnoreCase(selsupplier)) {
		 hub_selected = "selected";
		}
		out.println("<option  " + hub_selected + " value=\"" + branch_plant + "\">" +
		 hub_name + "</option>\n");

	 }
	 out.println("</SELECT>\n");
	 out.println("</TD>\n");

	 PersonnelBean personnelBean = session.getAttribute("personnelBean") == null ? null :
		(PersonnelBean) session.getAttribute("personnelBean");
	 if (personnelBean.getPermissionBean().hasFacilityPermission("suppliershipping", selsupplier,null)) {
		this.setupdateStatus(true);
	 }
	 else {
		this.setupdateStatus(false);
	 }

	 // Search Text
	 out.println("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
	 out.println("<B>Search:</B>&nbsp;\n");
	 out.println("</TD>\n");
	 out.println("<TD WIDTH=\"5%\" ALIGN=\"right\" CLASS=\"announce\">\n");
	 out.println("<SELECT CLASS=\"HEADER\"  NAME=\"searchlike\" id=\"searchlike\" size=\"1\">\n");
	 Hashtable searchthese = new Hashtable();
	 searchthese.put("PO", "RADIAN_PO");
	 searchthese.put("Item Id", "ITEM_ID");
	 searchthese.put("Item Desc", "ITEM_DESCRIPTION");
	 searchthese.put("MR", "MR_NUMBER");
	 searchthese.put("Requestor", "REQUESTOR_NAME");
	 out.println(radian.web.HTMLHelpObj.sorthashbyValueshowkey(searchthese.entrySet(),
		searchLike));
	 out.println("</SELECT>\n");
	 out.println("</TD>\n");

	 out.println("<TD WIDTH=\"5%\" ALIGN=\"left\" CLASS=\"announce\">\n");
	 out.println("<SELECT CLASS=\"HEADER\"  NAME=\"searchfor\" id=\"searchfor\" size=\"1\">\n");
	 Hashtable sortresult = new Hashtable();
	 sortresult.put("contains", "Like");
	 sortresult.put("is", "Equals");
	 out.println(radian.web.HTMLHelpObj.sorthashbyValueshowkey(sortresult.entrySet(),
		searchfor));
	 out.println("</SELECT>\n");
	 out.println("</TD>\n");

	 out.println("<TD WIDTH=\"5%\" VALIGN=\"MIDDLE\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
	 out.println("<B>For</B>&nbsp;&nbsp;<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"SearchField\" value=\"" +
		search_text + "\" size=\"20\">\n");
	 out.println("</TD>\n");

	 //List Orders
	 out.println("<TD WIDTH=\"5%\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n");
	 out.println("   <INPUT TYPE=\"submit\" VALUE=\"List Orders\" onclick= \"return search(this)\" NAME=\"SearchButton\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n");
	 out.println("</TD>\n");

	 //Ship Confirm
	 out.println("<TD WIDTH=\"5%\" CLASS=\"announce\">\n");
	 if (this.getupdateStatus()) {
		out.println("<INPUT TYPE=\"submit\" VALUE=\"Ship Confirm\" onclick= \"return update(this)\" NAME=\"UpdateButton\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n");
	 }
	 else {
		out.println("&nbsp;\n");
	 }
	 out.println("</TD>\n");
	 out.println("</TR>\n");

	 out.println("<TR>\n");
	 out.println("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
	 out.println("<B>Ship To:</B>&nbsp;</TD>\n");
	 out.println("<TD WIDTH=\"10%\" HEIGHT=\"35\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
	 out.println("<SELECT CLASS=\"HEADER\"  NAME=\"shipto\"  id=\"shipto\" size=\"1\">\n");

	 Hashtable fh = (Hashtable) initialshiptodata.get(selsupplier);

	 if (fh == null) {
		out.println("<option value=\"All\">All</option>\n");
	 }
	 else {
		Vector invidV = (Vector) fh.get("SHIP_TO_LOCATION_ID");
		for (int i = 0; i < invidV.size(); i++) {
		 String preSelect = "";
		 String wacid = (String) invidV.elementAt(i);

		 if (selshipto.equalsIgnoreCase(wacid)) {
			preSelect = "selected";
		 }
		 out.println("<option " + preSelect + " value=\"" + wacid + "\"> " + wacid +
			"</option>\n");
		}
	 }
	 out.println("</SELECT>\n");
	 out.println("</TD>\n");

	 //Include History
	 out.println("<TD WIDTH=\"5%\" HEIGHT=\"35\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
	 out.println("<INPUT type=\"checkbox\" name=\"showhistory\" id=\"showhistory\" value=\"Yes\" " +
		(showhistory.equalsIgnoreCase("Yes") ? "checked" : "") + ">\n");
	 out.println("</TD>\n");
	 out.println(
		"<TD WIDTH=\"5%\" HEIGHT=\"35\" CLASS=\"announce\" ALIGN=\"LEFT\" COLSPAN=\"2\">\n");
	 out.println("Include History\n");
	 out.println("</TD>\n");
	 out.println("<TD WIDTH=\"5%\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n");
	 out.println("&nbsp;\n");
	 out.println("</TD>\n");
	 //Box Labels
	 out.println("<TD CLASS=\"announce\" HEIGHT=\"35\" WIDTH=\"5%\" ALIGN=\"LEFT\">\n");
	 out.println("<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Delivery Labels\"  onclick= \"return boxlabels(this)\" NAME=\"genalleButton\">&nbsp;\n");
	 out.println("</TD>\n");
	 //PMC Labels
	 out.println("<TD CLASS=\"announce\" HEIGHT=\"35\" WIDTH=\"5%\" ALIGN=\"LEFT\">\n");
	 out.println("<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Drum Labels\" onclick= \"return reprintlabels(this)\" NAME=\"genalleButton\">&nbsp;\n");
	 out.println("</TD>\n");

	 out.println("</TR>\n");
	 out.println("<TR VALIGN=\"MIDDLE\">\n");

	 // Sort
	 out.println("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
	 out.println("<B>Sort:</B>&nbsp;\n");
	 out.println("</TD>\n");
	 out.println("<TD WIDTH=\"10%\" COLSPAN=\"2\">\n");
	 out.println("<SELECT CLASS=\"HEADER\"  NAME=\"SortBy\" id=\"SortBy\" size=\"1\">\n");
	 sortresult = new Hashtable();
	 sortresult.put("PO", "RADIAN_PO");
	 sortresult.put("Ship To", "SHIP_TO_LOCATION_ID");
	 sortresult.put("MR", "MR_NUMBER");
	 sortresult.put("Item Id", "ITEM_ID");

	 out.println(radian.web.HTMLHelpObj.sorthashbyValueshowkey(sortresult.entrySet(),
		sortby));
	 out.println("</SELECT>\n");
	 out.println("</TD>\n");
	 out.println("</TD>\n");

	 out.println("<TD WIDTH=\"5%\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n");
	 out.println("&nbsp;\n");
	 out.println("</TD>\n");

	 out.println("<TD WIDTH=\"5%\" COLSPAN=\"2\" ALIGN=\"LEFT\">\n");
	 out.println("&nbsp;\n");
	 out.println("</TD>\n");
	 //Container Labels
	 out.println("<TD WIDTH=\"5%\" COLSPAN=\"2\" ALIGN=\"LEFT\">\n");

	 String containerLabels="select max(CONTAINER_LABEL) CONTAINER_LABEL from virtual_hub_supplier  where SUPPLIER= '" + selsupplier + "' ";
	 String allowContainerLabels = "";
	 DBResultSet dbrs=null;
	 ResultSet rs=null;
	 try
	 {
		dbrs=db.doQuery( containerLabels );
		rs=dbrs.getResultSet();

		while ( rs.next() )
		{
		 allowContainerLabels=rs.getString( "CONTAINER_LABEL" ) == null ? "" : rs.getString( "CONTAINER_LABEL" );
		}
	 }
	 catch ( Exception e )
	 {
		e.printStackTrace();
	 }
	 finally
	 {
		if ( dbrs != null ){dbrs.close();}
	 }

	 if ("y".equalsIgnoreCase(allowContainerLabels))
	 {
		out.println("<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Container Labels\" onclick= \"containerLabels()\" NAME=\"containerLabelsButton\">&nbsp;\n");
	 }
	 out.println("</TD>\n");
	 out.println("</TR></TABLE>\n");

	 out.println("<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\" CLASS=\"moveup\">\n");
	 out.println("<tr><td>");
	 out.println("<INPUT TYPE=\"hidden\" NAME=\"UserAction\" id=\"UserAction\" VALUE=\"\">\n");
	 out.println("<INPUT TYPE=\"hidden\" NAME=\"SubUserAction\" id=\"SubUserAction\" VALUE=\" \">\n");
	 out.println("<INPUT TYPE=\"hidden\" NAME=\"DuplLineNumber\" id=\"DuplLineNumber\" VALUE=\" \">\n");
	 out.println("<INPUT TYPE=\"hidden\" NAME=\"thedecidingRandomNumber\" id=\"thedecidingRandomNumber\" VALUE=\"" +
		thedecidingRandomNumber + "\">\n");

	 out.println("</TD></tr>");
	 out.println("</table>\n");
	 //end table #2
	 sortresult = null;
	}
	catch (Exception e) {
	 e.printStackTrace();
	 out.println(radian.web.HTMLHelpObj.printError("tcmIS Supplier Shipping",intcmIsApplication));
	}

	return;
 }

 private void buildDetails(Vector data, String SubuserAction) {
	try {
	 Hashtable summary = new Hashtable();
	 summary = (Hashtable) data.elementAt(0);
	 int total = ( (Integer) (summary.get("TOTAL_NUMBER"))).intValue();
	 out.println("<BR><FONT SIZE=\"3\" FACE=\"Arial\" COLOR=\"#fc0303\"><B><A HREF=\"javascript:showshippingpagelegend()\" STYLE=\"color:#0000ff;cursor:hand\">Legend</A></B></FONT><BR>\n");
	 out.println("<TABLE  BORDER=\"0\" BGCOLOR=\"#a2a2a2\" CELLSPACING=1 CELLPADDING=1 WIDTH=\"100%\" CLASS=\"moveup\">\n");
	 int i = 0; //used for detail lines
	 int linefadded = 0;
	 int lastItemNum = 1;
	 int ItemIdCount = 0;
	 String Color = "CLASS=\"white";

	 for (int loop = 0; loop < total; loop++) {
		i++;
		boolean createHdr = false;

		if (loop % 10 == 0 && lastItemNum == 1) {
		 createHdr = true;
		}

		if (createHdr) {
		 out.println("<tr align=\"center\">\n");
		 out.println("<TH width=\"5%\"  height=\"38\">Haas PO-Line</TH>\n");
		 out.println("<TH width=\"5%\"  height=\"38\">Promised Date</TH>\n");
		 out.println("<TH width=\"7%\"  height=\"38\">Open Qty</TH>\n");
		 out.println("<TH width=\"10%\"  height=\"38\">Packaging</TH>\n");
		 out.println("<TH width=\"15%\" height=\"38\">Description</TH>\n");
		 out.println("<TH width=\"12%\" height=\"38\">Requestor<BR>WA<BR>DT</TH>\n");
		 out.println("<TH width=\"5%\" height=\"38\">MR-Line<BR>(User PO)</TH>\n");
		 out.println("<TH width=\"5%\" height=\"38\">Ship To</TH>\n");
		 out.println("<TH width=\"2%\"  height=\"38\"># Labels &nbsp;</TH>\n");
		 out.println("<TH width=\"2%\"  height=\"38\">OK &nbsp;</TH>\n");
		 out.println("<TH width=\"5%\"  height=\"38\">Ship Date mm/dd/yyyy</TH>\n");
		 out.println("<TH width=\"5%\"  height=\"38\">Receipt Date mm/dd/yyyy</TH>\n");
		 out.println("<TH width=\"8%\"  height=\"38\">Lot</TH>\n");
		 /*out.println("<TH width=\"5%\"  height=\"38\">Shelf Life Basis</TH>\n");
		 out.println("<TH width=\"5%\"  height=\"38\">Basis Date mm/dd/yyyy</TH>\n");*/
		 out.println("<TH width=\"5%\"  height=\"38\">Exp Date mm/dd/yyyy</TH>\n");
		 out.println("<TH width=\"5%\"  height=\"38\">Qty Shiped</TH>\n");
     out.println("<TH width=\"5%\"  height=\"38\">Notes</TH>\n");
     out.println("<TH width=\"5%\"  height=\"38\">Duplicate</TH>\n");
		 out.println("</tr>\n");
		}

		Hashtable hD = new Hashtable();
		hD = (Hashtable) data.elementAt(i);

		String Next_po = "";
		String Next_poline = "";
		String Next_item = "";
		if (! (i == total)) {
		 Hashtable hDNext = new Hashtable();
		 hDNext = (Hashtable) data.elementAt(i + 1);
		 Next_po = hDNext.get("PURCHASE_ORDER") == null ? "&nbsp;" :
			hDNext.get("PURCHASE_ORDER").toString();
		 Next_poline = hDNext.get("PURCHASE_ORDER_LINE") == null ? "&nbsp;" :
			hDNext.get("PURCHASE_ORDER_LINE").toString();
		 Next_item = hDNext.get("ITEM_ID") == null ? "&nbsp;" :
			hDNext.get("ITEM_ID").toString();
		}
		else {
		 Next_po = " ";
		 Next_poline = "";
		 Next_item = " ";
		}

		String Qty_recd = (hD.get("QTY_RECD") == null ? "&nbsp;" :
		 hD.get("QTY_RECD").toString());
		String Line_Check = (hD.get("USERCHK") == null ? "&nbsp;" :
		 hD.get("USERCHK").toString());
		String checkednon = "";
		if (Line_Check.equalsIgnoreCase("yes")) {
		 checkednon = "checked";
		}
		else {
		 checkednon = "";
		}

	  String containerLabel = (hD.get("CONTAINER_LABEL") == null ? "" :
		 hD.get("CONTAINER_LABEL").toString());
		String receiptId = (hD.get("RECEIPT_ID") == null ? "" :
		 hD.get("RECEIPT_ID").toString());
		/*String indefshelflie = (hD.get("INDEFINITE_SHELF_LIFE") == null ? " " :
		 hD.get("INDEFINITE_SHELF_LIFE").toString());
		String supplier = (hD.get("SUPPLIER") == null ? "&nbsp;" :
		 hD.get("SUPPLIER").toString());*/
		String Date_recieved = (hD.get("DATE_RECIEVED") == null ? "" :
		 hD.get("DATE_RECIEVED").toString());
		String Expire_date = hD.get("EXPIRE_DATE").toString();
		String LINE_STATUS = hD.get("LINE_STATUS").toString();
		String actshipDate = (hD.get("ACTUAL_SHIP_DATE") == null ? "" :
		 hD.get("ACTUAL_SHIP_DATE").toString());
		/*String supplier_parent = hD.get("SUPPLIER_PARENT") == null ? "&nbsp;" :
		 hD.get("SUPPLIER_PARENT").toString();*/
		String ship_to_company_id = hD.get("SHIP_TO_COMPANY_ID") == null ? "&nbsp;" :
		 hD.get("SHIP_TO_COMPANY_ID").toString();
		String ship_to_location_id = hD.get("SHIP_TO_LOCATION_ID") == null ? "&nbsp;" :
		 hD.get("SHIP_TO_LOCATION_ID").toString();
		String mr_number = hD.get("MR_NUMBER") == null ? "&nbsp;" :
		 hD.get("MR_NUMBER").toString();
		String mr_line_item = hD.get("MR_LINE_ITEM") == null ? "&nbsp;" :
		 hD.get("MR_LINE_ITEM").toString();
		String requestor_name = hD.get("REQUESTOR_NAME") == null ? "&nbsp;" :
		 hD.get("REQUESTOR_NAME").toString();
		String application = hD.get("APPLICATION") == null ? "&nbsp;" :
		 hD.get("APPLICATION").toString();
		String delivery_point = hD.get("DELIVERY_POINT") == null ? "&nbsp;" :
		 hD.get("DELIVERY_POINT").toString();
		String customerPo = hD.get("CUSTOMER_PO") == null ? "&nbsp;" :
		 hD.get("CUSTOMER_PO").toString();
		String Purchase_order = hD.get("PURCHASE_ORDER") == null ? "&nbsp;" :
		 hD.get("PURCHASE_ORDER").toString();
		String PO_Line = hD.get("PURCHASE_ORDER_LINE") == null ? "&nbsp;" :
		 hD.get("PURCHASE_ORDER_LINE").toString();
		String qty_open = hD.get("QTY_OPEN") == null ? "&nbsp;" : hD.get("QTY_OPEN").toString();
		String Item_id = hD.get("ITEM_ID") == null ? "&nbsp;" : hD.get("ITEM_ID").toString();
		String Expected_dt = hD.get("EXPECTED") == null ? "&nbsp;" :
		 hD.get("EXPECTED").toString();
		String qty = hD.get("QTY") == null ? "&nbsp;" : hD.get("QTY").toString();
		String branch_plant = hD.get("BRANCH_PLANT") == null ? "&nbsp;" :
		 hD.get("BRANCH_PLANT").toString();
		String item_description = hD.get("ITEM_DESCRIPTION") == null ? "&nbsp;" :
		 hD.get("ITEM_DESCRIPTION").toString();
		String recnotes = hD.get("NOTES") == null ? "&nbsp;" : hD.get("NOTES").toString();
		/*String supplier_name = hD.get("SUPPLIER_NAME") == null ? "&nbsp;" :
		 hD.get("SUPPLIER_NAME").toString();
		String indefinite_shelf_life = hD.get("INDEFINITE_SHELF_LIFE") == null ? "&nbsp;" :
		 hD.get("INDEFINITE_SHELF_LIFE").toString();*/
		String critical = hD.get("CRITICAL") == null ? "&nbsp;" : hD.get("CRITICAL").toString();
		/*String original_mfg_lot = hD.get("ORIGINAL_MFG_LOT") == null ? "&nbsp;" :
		 hD.get("ORIGINAL_MFG_LOT").toString();*/
		String inventory_group = hD.get("INVENTORY_GROUP") == null ? "&nbsp;" :
		 hD.get("INVENTORY_GROUP").toString();
		String mngkitassingl = hD.get("MANAGE_KITS_AS_SINGLE_UNIT") == null ? "&nbsp;" :
		 hD.get("MANAGE_KITS_AS_SINGLE_UNIT").toString();
		String component_id = hD.get("COMPONENT_ID") == null ? "&nbsp;" :
		 hD.get("COMPONENT_ID").toString();
		/*String material_id = hD.get("MATERIAL_ID") == null ? "&nbsp;" :
		 hD.get("MATERIAL_ID").toString();*/
		String packaging = hD.get("PACKAGING") == null ? "&nbsp;" :
		 hD.get("PACKAGING").toString();
		String material_desc = hD.get("MATERIAL_DESC") == null ? "&nbsp;" :
		 hD.get("MATERIAL_DESC").toString();
		String numofkits = hD.get("NUMBER_OF_KITS") == null ? "&nbsp;" :
		 hD.get("NUMBER_OF_KITS").toString();
		/*String location_short_name = hD.get("LOCATION_SHORT_NAME") == null ? "&nbsp;" :
		 hD.get("LOCATION_SHORT_NAME").toString();
		String location_desc = hD.get("LOCATION_DESC") == null ? "&nbsp;" :
		 hD.get("LOCATION_DESC").toString();
		String address_line_1 = hD.get("ADDRESS_LINE_1") == null ? "&nbsp;" :
		 hD.get("ADDRESS_LINE_1").toString();
		String address_line_2 = hD.get("ADDRESS_LINE_2") == null ? "&nbsp;" :
		 hD.get("ADDRESS_LINE_2").toString();
		String address_line_3 = hD.get("ADDRESS_LINE_3") == null ? "&nbsp;" :
		 hD.get("ADDRESS_LINE_3").toString();
		String city = hD.get("CITY") == null ? "&nbsp;" : hD.get("CITY").toString();
		String state_abbrev = hD.get("STATE_ABBREV") == null ? "&nbsp;" :
		 hD.get("STATE_ABBREV").toString();
		String zip = hD.get("ZIP")==null?"&nbsp;":hD.get("ZIP").toString();*/
		String Mfg_lot = (hD.get("MFG_LOT") == null ? "&nbsp;" : hD.get("MFG_LOT").toString());
		/*String basis_date = (hD.get("BASIS_DATE") == null ? "&nbsp;" :
		 hD.get("BASIS_DATE").toString());
		String shelf_life_bassis = (hD.get("SHELF_LIFE_BASIS") == null ? "&nbsp;" :
		 hD.get("SHELF_LIFE_BASIS").toString());
		String shelf_life_days = (hD.get("SHELF_LIFE_DAYS") == null ? "&nbsp;" :
		 hD.get("SHELF_LIFE_DAYS").toString());
		if (shelf_life_bassis.length() > 0) {
		 shelf_life_bassis = "DO" + shelf_life_bassis;
		}*/

		String lbl_qty = (hD.get("LABEL_QTY") == null ? "" : hD.get("LABEL_QTY").toString());

		String chkbox_value = "no";
		if (checkednon.equalsIgnoreCase("checked") && !SubuserAction.equalsIgnoreCase("UpdPage")) {
		 chkbox_value = "yes";
		}

		if ("Y".equalsIgnoreCase(critical)) {
		 Color = "CLASS=\"red";
		}

		if ("S".equalsIgnoreCase(critical)) {
		 Color = "CLASS=\"pink";
		}

		if (SubuserAction.equalsIgnoreCase("UpdPage") && "NO".equalsIgnoreCase(LINE_STATUS)) {
		 Color = "CLASS=\"error";
		}

		int qtyopen = Integer.parseInt(qty_open);

		boolean recqcallowed = false;
		if ( (this.getupdateStatus() && qtyopen > 0)) {
		 recqcallowed = true;
		}

		if (SubuserAction.equalsIgnoreCase("UpdPage") && "Yes".equalsIgnoreCase(LINE_STATUS)) {
		 recqcallowed = false;
		}

		boolean Samepoline = false;
		boolean FirstRow = false;

		//System.out.println("  "+mngkitassingl+"  Next_po "+Next_po+"  Purchase_order"+Purchase_order+"  Next_poline"+Next_poline+"  PO_Line"+PO_Line+" Next_item "+Next_item+"  Item_id"+Item_id+"");
		if ("N".equalsIgnoreCase(mngkitassingl) && Next_po.equalsIgnoreCase(Purchase_order) &&
		 Next_poline.equalsIgnoreCase(PO_Line) && Next_item.equalsIgnoreCase(Item_id)) {
		 Samepoline = true;
		 lastItemNum++;
		}
		else {
		 ItemIdCount++;
		 lastItemNum = 1;
		}

		if (Samepoline) {
		 if (lastItemNum == 2) {
			FirstRow = true;
		 }
		}
		else if ("Y".equalsIgnoreCase(mngkitassingl)) {
		 FirstRow = true;
		 numofkits = "1";
		}

		if (!this.getupdateStatus() || !recqcallowed) {
		 out.println("<tr align=\"center\">\n");
		 if (FirstRow) {
			out.println("<td " + Color + "\" width=\"5%\" height=\"38\" rowspan=\"" + numofkits +
			 "\">" + Purchase_order + "-" + PO_Line + "</td>\n");
			out.println("<td " + Color + "\" width=\"5%\" height=\"38\" rowspan=\"" + numofkits +
			 "\">" + Expected_dt + "</td>\n");
			out.println("<td " + Color + "\" width=\"5%\" height=\"38\" rowspan=\"" + numofkits +
			 "\">" + qty_open + "</td>\n");
		 }
		 out.println("<td " + Color + "l\" width=\"10%\" height=\"38\">" + packaging +
			"</td>\n");
		 if ("N".equalsIgnoreCase(mngkitassingl)) {
			out.println("<td " + Color + "l\" width=\"15%\" height=\"38\" >" + material_desc +
			 "</td>\n");
		 }
		 else {
			out.println("<td " + Color + "l\" width=\"15%\" height=\"38\" >" + item_description +
			 "</td>\n");
		 }

		 if (FirstRow) {
			out.println("<td " + Color + "\" width=\"5%\" height=\"38\" rowspan=\"" + numofkits +
			 "\">" + requestor_name + "<BR>" + application + "<BR>" + delivery_point +
			 "</td>\n");
			out.println("<td " + Color + "\" width=\"5%\" height=\"38\" rowspan=\"" + numofkits +
			 "\">" + mr_number + "-" + mr_line_item + "");
			if (customerPo.trim().length() > 0)
			{
			 out.println("<BR>(" + customerPo + ")");
			}
			out.println("</td>\n");
			out.println("<td " + Color + "\" width=\"5%\" height=\"38\" rowspan=\"" + numofkits +
			 "\">" + ship_to_location_id + "</td>\n");
			out.println("<td " + Color + "\" width=\"5%\" height=\"38\" rowspan=\"" + numofkits +
			 "\"><input type=\"text\" CLASS=\"DETAILS\" name=\"lbl_qty" + i + "\"  id=\"lbl_qty" + i + "\" value=\"" +
			 lbl_qty + "\" maxlength=\"10\" size=\"3\"></td>\n");
		 }

		 if (!recqcallowed) {
			out.println( "<td " + Color + "l\" width=\"2%\" height=\"38\" ><INPUT TYPE=\"hidden\" value=\"no\" NAME=\"row_chk" + i + "\" id=\"row_chk" + i + "\"></td>\n" );
			//out.println("<td " + Color + "l\" width=\"3%\" height=\"38\" >&nbsp;</td>\n");
			out.println("<td " + Color + "l\" width=\"3%\" height=\"38\" >" + actshipDate +
			 "</td>\n");
			out.println("<td " + Color + "l\" width=\"3%\" height=\"38\" >" + Date_recieved +
			 "</td>\n");
			out.println("<td " + Color + "l\" width=\"1%\" height=\"38\" >" + Mfg_lot +
			 "</td>\n");
			/*out.println("<td " + Color + "l\" width=\"3%\" height=\"38\" >&nbsp;</td>\n");
			out.println("<td " + Color + "l\" width=\"8%\" height=\"38\" >&nbsp;</td>\n");*/
			out.println("<td " + Color + "l\" width=\"4%\" height=\"38\" >" + Expire_date +
			 "</td>\n");
			out.println("<td " + Color + "l\" width=\"4%\" height=\"38\" >" + Qty_recd +
			 "</td>\n");
      out.println("<td " + Color + "l\" width=\"5%\" height=\"38\" >&nbsp;</td>\n");
      out.println("<td " + Color + "l\" width=\"5%\" height=\"38\" >&nbsp;</td>\n");
		 }
		 out.println("<input type=\"hidden\" name=\"receiptId" + i + "\" id=\"receiptId" + i + "\" value=\"" +
			 receiptId + "\">\n");
		 out.println("</tr>\n");
		}
		else {
		 if ( (SubuserAction.equalsIgnoreCase("Update") && "YES".equalsIgnoreCase(LINE_STATUS)) ||
			("YES".equalsIgnoreCase(LINE_STATUS))) {
			out.println("<INPUT TYPE=\"hidden\" value=\"no\" NAME=\"row_chk" + i + "\" id=\"row_chk" + i + "\">\n");
			out.println("<input type=\"hidden\" name=\"mfg_lot" + i + "\" id=\"mfg_lot" + i + "\" value=\"" + Mfg_lot +
			 "\">");
			out.println("<input type=\"hidden\" name=\"act_suppship_date" + i + "\" id=\"act_suppship_date" + i + "\" value=\"" +
			 actshipDate + "\">\n");
			out.println("<input type=\"hidden\" name=\"date_recieved" + i + "\" id=\"date_recieved" + i + "\" value=\"" +
			 Date_recieved + "\">\n");
			out.println("<input type=\"hidden\" name=\"expiry_date" + i + "\" id=\"expiry_date" + i + "\" value=\"" +
			 Expire_date + "\">\n");
			out.println("<input type=\"hidden\" name=\"qty_recd" + i + "\" id=\"qty_recd" + i + "\" value=\"" +
			 Qty_recd + "\">\n");
			out.println("<input type=\"hidden\" name=\"po\" id=\"po\" value=\"" + Purchase_order +
			 "\">\n");
			out.println("<input type=\"hidden\" name=\"po_line" + i + "\" id=\"po_line" + i + "\" value=\"" + PO_Line +
			 "\">\n");
			out.println("<input type=\"hidden\" name=\"item_id" + i + "\" id=\"item_id" + i + "\" value=\"" + Item_id +
			 "\">\n");
			out.println("<input type=\"hidden\" name=\"item_id\" id=\"item_id\" value=\"" + Item_id + "\">\n");
		 }
		 else {
			out.println("<tr align=\"center\">\n");
			if (FirstRow) {
			 out.println("<td " + Color + "\" width=\"5%\" height=\"38\" rowspan=\"" +
				numofkits + "\">" + Purchase_order + "-" + PO_Line + "</td>\n");
			 out.println("<td " + Color + "\" width=\"5%\" height=\"38\" rowspan=\"" +
				numofkits + "\">" + Expected_dt + "</td>\n");
			 out.println("<td " + Color + "\" width=\"5%\" height=\"38\" rowspan=\"" +
				numofkits + "\">" + qty_open + "</td>\n");
			}

			out.println("<td " + Color + "l\" width=\"10%\" height=\"38\">" + packaging +
			 "</td>\n");

			if ("N".equalsIgnoreCase(mngkitassingl)) {
			 out.println("<td " + Color + "l\" width=\"15%\" height=\"38\" >" + material_desc +
				"</td>\n");
			}
			else {
			 out.println("<td " + Color + "l\" width=\"15%\" height=\"38\" >" +
				item_description + "</td>\n");
			}

			if (FirstRow) {
			 out.println("<td " + Color + "\" width=\"5%\" height=\"38\" rowspan=\"" +
				numofkits + "\">" + requestor_name + "<BR>" + application + "<BR>" +
				delivery_point + "</td>\n");
			 out.println("<td " + Color + "\" width=\"5%\" height=\"38\" rowspan=\"" +
				numofkits + "\">" + mr_number + "-" + mr_line_item + "");
			 if (customerPo.trim().length() > 0)
			 {
				out.println("<BR>(" + customerPo + ")");
			 }
			 out.println("</td>\n");
			 out.println("<td " + Color + "\" width=\"5%\" height=\"38\" rowspan=\"" +
				numofkits + "\"><A HREF=\"javascript:showShippingaddress('" + ship_to_location_id +
				"','" + ship_to_company_id + "')\">" + ship_to_location_id + "</A></td>\n");
			 out.println("<td " + Color + "\" width=\"5%\" height=\"38\" rowspan=\"" +
				numofkits + "\"><input type=\"text\" CLASS=\"DETAILS\" name=\"lbl_qty" + i +
				"\" id=\"lbl_qty" + i +"\" value=\"" + lbl_qty + "\" maxlength=\"10\" size=\"3\"></td>\n");
			}

			out.println("<td " + Color +
			 "\" width=\"2%\" height=\"38\"><INPUT TYPE=\"checkbox\" value=\"" + chkbox_value +
			 "\"  onClick= \"checkValues(" + i + ")\" " + checkednon + "  NAME=\"row_chk" + i +
			 "\" id=\"row_chk" + i +"\"></td>\n");

			out.println("<td " + Color + "\" width=\"5%\" height=\"38\"><input type=\"text\" CLASS=\"DETAILS\" name=\"act_suppship_date" + i +"\" id=\"act_suppship_date" + i +"\" value=\"" + actshipDate + "\" maxlength=\"10\" size=\"6\"><FONT SIZE=\"4\"><a href=\"javascript: void(0);\" id=\"linkact_suppship_date" + i +"\"  onClick=\"return getCalendar(document.suppliershipping.act_suppship_date" +
			 i + ");\">&diams;</a></FONT></td>\n");
			out.println("<td " + Color + "\" width=\"5%\" height=\"38\"><input type=\"text\" CLASS=\"DETAILS\" name=\"date_recieved" + i + "\" id=\"date_recieved" + i + "\" value=\"" +
			 Date_recieved + "\" maxlength=\"10\" size=\"6\"><FONT SIZE=\"4\"><a href=\"javascript: void(0);\" id=\"linkdate_recieved" + i + "\" onClick=\"return getCalendar(document.suppliershipping.date_recieved" +
			 i + ");\">&diams;</a></FONT></td>\n");
			//out.println("<td " + Color + "l\" width=\"1%\" height=\"38\" >" + Mfg_lot +"</td>\n");
			if ("Y".equalsIgnoreCase(containerLabel))
			{
			out.println( "<td " + Color + "\" width=\"8%\" height=\"38\"><input type=\"text\" CLASS=\"DETAILS\" name=\"mfg_lot" + i + "\" id=\"mfg_lot" + i + "\" value=\"" + Mfg_lot + "\" maxlength=\"30\" size=\"15\"></td>\n" );

			/*out.println("<td " + Color + "l\" width=\"3%\" height=\"38\" >&nbsp;</td>\n");
			out.println("<td " + Color + "l\" width=\"3%\" height=\"38\" >&nbsp;</td>\n");*/
			//out.println("<td " + Color + "l\" width=\"3%\" height=\"38\" >&nbsp;</td>\n");
			if ( "01/01/3000".equalsIgnoreCase( Expire_date ) )
			{
			 out.print( "<td " + Color + "\" width=\"5%\" height=\"38\"><input type=\"hidden\" name=\"expiry_date" + i + "\" id=\"expiry_date" + i + "\" value=\"Indefinite\">" );
			}
			else
			{
			 out.print( "<td " + Color + "\" width=\"5%\" height=\"38\"><input type=\"text\" CLASS=\"DETAILS\" name=\"expiry_date" + i + "\" id=\"expiry_date" + i + "\" value=\"" +
				Expire_date + "\" maxlength=\"10\" size=\"6\">" );
			}
			out.println( "<FONT SIZE=\"4\"><a href=\"javascript: void(0);\" id=\"linkexpiry_date" + i + "\" onClick=\"return getCalendar(document.suppliershipping.expiry_date" + i + ");\">&diams;</a></FONT></td>\n" );
			}
			else
			{
			 out.println("<td " + Color + "l\" width=\"3%\" height=\"38\" >&nbsp;</td>\n");
  		 out.println("<td " + Color + "l\" width=\"3%\" height=\"38\" >&nbsp;</td>\n");
			}
			 /*out.println( "<td " + Color + "\" width=\"5%\" height=\"38\">"+shelf_life_bassis+"\n<input type=\"hidden\" name=\"shelf_life_days" + i + "\" value=\"" + shelf_life_days + "\"><input type=\"hidden\" name=\"shelf_life_bassis" + i + "\" value=\"" + shelf_life_bassis + "\"></TD>" );
					if (shelf_life_bassis.length() > 0)
					{
				out.println( "<td " + Color + "\" width=\"5%\" height=\"38\"><input type=\"text\" CLASS=\"DETAILS\" name=\"basis_date" + i + "\" value=\"" +
				 basis_date + "\" onClick= \"calculateexpdate(" + i + ")\" maxlength=\"10\" size=\"6\"><FONT SIZE=\"4\"><a href=\"javascript: void(0);\" ID=\"datelink\" onClick=\"return getCalendar(document.suppliershipping.basis_date" +
				 i + ");\">&diams;</a></FONT></td>\n" );
					}
					else
					{
				out.println( "<td " + Color + "l\" width=\"5%\" height=\"38\" ><input type=\"hidden\" name=\"basis_date" + i + "\" value=\"\">&nbsp;</td>\n" );
					}
						 if ( "Y".equalsIgnoreCase( indefshelflie ) || "01/01/3000".equalsIgnoreCase( Expire_date ) )
						 {
							 out.println( "<td " + Color + "\" width=\"5%\" height=\"38\"><input type=\"hidden\" name=\"expiry_date" + i + "\" value=\"Indefinite\">Indefinite</td>\n" );
						 }
			 else if (shelf_life_bassis.length() > 0)
			 {
					out.println( "<td " + Color + "\" width=\"5%\" ID=\"expiry_datecell" + i + "\" height=\"38\"></td><input type=\"hidden\" name=\"expiry_date" + i + "\" value=\"\">\n" );
			 }
						 else
						 {
							 out.println( "<td " + Color + "\" width=\"5%\" height=\"38\"><input type=\"text\" CLASS=\"DETAILS\" name=\"expiry_date" + i + "\" value=\"" +
													 Expire_date + "\" maxlength=\"10\" size=\"6\"><FONT SIZE=\"4\"><a href=\"javascript: void(0);\" ID=\"datelink\" onClick=\"return getCalendar(document.suppliershipping.expiry_date" +
													 i + ");\">&diams;</a></FONT></td>\n" );
						 }
*/
			if (FirstRow) {
			 out.println("<td " + Color + "\" width=\"5%\" height=\"38\" rowspan=\"" +
				numofkits + "\"><input type=\"text\" CLASS=\"DETAILS\" name=\"qty_recd" + i +
				"\" id=\"qty_recd" + i +"\" value=\"" + Qty_recd + "\" maxlength=\"10\" size=\"3\"></td>\n");
			 out.println( "<TD WIDTH=\"5%\" " + Color + "\" width=\"5%\" height=\"4\" rowspan=\""+numofkits+"\"><TEXTAREA name=\"recnotes" + i + "\" id=\"recnotes" + i + "\" rows=\"4\" cols=\"25\">" + recnotes + "</TEXTAREA></TD>\n" );
			 if ("Y".equalsIgnoreCase(mngkitassingl)) {
				out.println("<td " + Color + "\" width=\"5%\" height=\"38\" rowspan=\"" +
				 numofkits + "\"><INPUT TYPE=\"submit\" CLASS=\"SUBMITDET\" onmouseover=\"className='SUBMITHOVERDET'\" onMouseout=\"className='SUBMITDET'\" onclick= \" return duplLine(" +
				 i + ")\" value=\"Dupl\" name=\"Button" + i + "\">\n");
			 }
			 else {
				out.println("<td " + Color + "\" width=\"5%\" height=\"38\" rowspan=\"" +
				 numofkits + "\">&nbsp;\n");
			 }
			}
			out.println("<input type=\"hidden\" name=\"qty_open" + i + "\" id=\"qty_open" + i + "\" value=\"" + qty_open +
			 "\">\n");

			if ("N".equalsIgnoreCase(mngkitassingl) && !FirstRow) {
			 out.println("<input type=\"hidden\" name=\"qtyoveride" + i + "\" id=\"qtyoveride" + i + "\" value=\"Y\">\n");
			}
			else {
			 out.println("<input type=\"hidden\" name=\"qtyoveride" + i + "\" id=\"qtyoveride" + i + "\" value=\"N\">\n");
			}
			out.println("<input type=\"hidden\" name=\"receiptId" + i + "\" id=\"receiptId" + i + "\" value=\"" +receiptId + "\">\n");
			out.println("</tr>\n");
		 }
		}
		if ( (ItemIdCount) % 2 == 0) {
		 Color = "CLASS=\"white";
		}
		else {
		 Color = "CLASS=\"blue";
		}

	 }

	 out.println("</table>\n");
	 out.println( "<input type=\"hidden\" name=\"totalRows\" id=\"totalRows\" value=\"" + ItemIdCount + "\">\n" );
	 out.println(
		"<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\" CLASS=\"moveup\">\n");
	 out.println("<tr>");
	 out.println(
		"<TD HEIGHT=\"10\" WIDTH=\"100%\" VALIGN=\"MIDDLE\" BGCOLOR=\"#000066\">&nbsp;\n");
	 out.println("</TD></tr>");
	 out.println("</table>\n");
	 out.println("</form>\n");
	 out.println("</body></html>\n");
	}
	catch (Exception e) {
	 e.printStackTrace();
	 out.println(radian.web.HTMLHelpObj.printError("tcmIS Supplier Shipping",intcmIsApplication));
	}

	return;
 }
}
