package radian.web.servlets.share;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Random;
import java.util.Vector;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.HubInvenLabelsTables;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.HelpObjs;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

import com.tcmis.common.admin.beans.HubInventoryGroupOvBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.action.LogisticsQVRThread;
import com.tcmis.internal.hub.beans.ReceivingInputBean;
import com.tcmis.internal.hub.process.ReceivingQcCheckListProcess;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 1998
 * Company:      URS Corporation
 * @author       Rajendra Rajput
 * @version
 */

/**
 * This class provides receiving functionality for the client's receiving
 * servlet. Clients session are oraganized as follows - New
 * 
 * 10-31-02 Removed the notes stuff I was working on.
 * 
 * Added code to print page to choose Hub Name if the branch_plant is null
 * 
 * 11-14-02 Adding receipt_notes
 * 
 * 11-27-02 Adding option to sort by AGE
 * 
 * Adding capability to export the search resutls to Exl file 05-06-03 Took out
 * the relod option I put in to test the new servers and load balancer 05-08-03
 * - Remembering which option the user choose to generate labels 1''/3'' or
 * 8.5''/11'' also rolled back changes done for testing 05-16-03 Code Celanup
 * and fixed the excel , problem 06-30-03 If BIN is null then show BIN as NONE
 * 07-28-03 Giving a link to the DOT shipping page 08-06-03 Don't truncate
 * receipt notes 08-15-03 Restring the number of days they can enter in expires
 * within 09-11-03 Inc TD % for Notes 10-27-03 Not aloowing Back button usage
 * and checking to make sure the receipt ID matches on server side to do update
 * 11-26-03 Doing Quality Control and spliting the permisisons to allow
 * changeing mfg_lot,status and Exp Date. Also made changes to the split qty
 * procedure Cannot put the recipt in a pickable status if you don't have
 * quality control permissions for items which are quality controled 12-01-03 -
 * Bug Fixing 12-08-03 - Giving option to print 4-3/16" / 3/8" Labels 12-11-03 -
 * New method to sort BIN location in descending order of date last used. and
 * ability to print all Labels listed on the page 12-12-03 - changed the
 * randomnumbercookie name to make it unique for the page. 12-30-03 - Updating
 * the transaction Date and showing the Owener company ID on the logistics page
 * 01-12-04 - Added Original Receipt ID, UNIT_COST to the display, also added
 * Radian_po,Po_line to the excel sheet 01-27-04 - Code refactoring change
 * 02-02-04 - Showing DATE_OF_RECEIPT,DATE_OF_MANUFACTURE in the excel sheet
 * 02-10-04 - Sorting Drop Downs 02-16-04 - Shwing CLIENT_PART_NOS in excel
 * sheet 03-09-04 - Fixed code to show the correct selected bin in the drop down
 * when an receipt in inventory has a bin which is not in vv_hub_bins table as
 * active 03-15-04 - Fixed a Bug to show the added bin from the + button to be
 * sellected when they add a bin. Also showing Indefinite for EXP date
 * 01/01/3000 for readonly version when a person is not allowed to update exp
 * date 04-26-04 - Changes to show kits as split on the screen. 05-07-04 -
 * Sorting the Hub Drop Down based on the prority in hub_pref table 06-01-04 -
 * Breaking up the search to make the query faster 06-23-04 - Giving an option
 * to show Include History, so they can update exp date on receipts that have
 * been all issued out. 07-29-04 - Showing Storage Temp 10-04-04 - Allowing to
 * put a receipt into Incoming status only if it is not approved. 12-10-04 - can
 * Search by OWNER_COMPANY_ID 01-21-05 - If you are a member of
 * onlynonPickableStatus user group, you can only place the receipt into
 * non-pickable statuses 02-28-05 Giving the option to not print Kit/Case Qty
 * Labels. Also giving the option to pick a prnter if there are multiple
 * printers at a hub 07-06-05 - Allowing QC for non-pickable statuses without a
 * exp date 02-28-06 - Giving ability to view/add receipt documents 05-03-06 -
 * There wasa bug regarding stamping the certification information. When a user
 * changed the Status,Exp Date or recert information which would cause an update
 * to the certification information on the server side because the values being
 * uodated were diffewrent from the original values in the database. Now if
 * beforew hitting the update button the addes a new bin to this item by
 * clicking on the + button, I refresh the page and when the daa is synched with
 * the data on the server side, the original data from the database is lost. So
 * when they hit update now there is no difference between data on the server
 * and data on the client. I fixed this by not refreshing the page when they add
 * a new bin
 */
@SuppressWarnings({"unchecked","rawtypes"})
public class HubInvenLabels {
	private ServerResourceBundle bundle = null;
	private TcmISDBModel db = null;
	private String thedecidingRandomNumber = null;

	private Vector receiptIdstoLabel = null;
	private Vector numbersoflabels = null;
	private String checkednon = "";
	private HubInvenLabelsTables hubObj = null;
	private boolean completeSuccess = true;
	private boolean noneToUpd = true;
	private int AddNewBinLineNum;
	private boolean allowupdate;
	private String Receiving_Servlet = "";
	private String Label_Servlet = "";
	private String numOldDays = "";
	private String paper_size = "";
	private boolean intcmIsApplication = false;
	private ResourceLibrary res = null;

	private static org.apache.log4j.Logger invlog = org.apache.log4j.Logger
			.getLogger(HubInvenLabels.class);

	public HubInvenLabels(ServerResourceBundle b, TcmISDBModel d) {
		bundle = b;
		db = d;
	}

	// Nawaz 05-02-02
	private void setAddBinLineNum(int id) {
		this.AddNewBinLineNum = id;
	}

	private int getAddBinLineNum() throws Exception {
		return this.AddNewBinLineNum;
	}

	// Nawaz 06-24-02
	public void setupdateStatus(boolean id) {
		this.allowupdate = id;
	}

	private boolean getupdateStatus() throws Exception {
		return this.allowupdate;
	}

	// Process the HTTP Post request
	public void doPost(HttpServletRequest request,
			HttpServletResponse response, PrintWriter out)
			throws ServletException, IOException {
		hubObj = new HubInvenLabelsTables(db);
		HttpSession session = request.getSession();
		res = new ResourceLibrary("com.tcmis.common.resources.CommonResources",
				(Locale) session.getAttribute("tcmISLocale"));

		PersonnelBean personnelBean = (PersonnelBean) session
				.getAttribute("personnelBean");
		Collection menuItemOvBeanCollection = personnelBean
				.getMenuItemOvBeanCollection();
		intcmIsApplication = false;
		if (menuItemOvBeanCollection != null
				&& menuItemOvBeanCollection.size() > 0) {
			intcmIsApplication = true;
		}

		String branch_plant = session.getAttribute("BRANCH_PLANT") == null ? ""
				: session.getAttribute("BRANCH_PLANT").toString();
		String personnelid = session.getAttribute("PERSONNELID") == null ? ""
				: session.getAttribute("PERSONNELID").toString();
		String CompanyID = session.getAttribute("COMPANYID").toString();
		Collection hubInventoryGroupOvBeanCollection = new Vector(
				(Collection) session
						.getAttribute("hubInventoryGroupOvBeanCollection"));

		Receiving_Servlet = bundle.getString("HUB_LABEL_SERVLET");
		Label_Servlet = bundle.getString("RE_LABEL_SERVLET");

		DBResultSet receiptdbrs = null;
		ResultSet receiptrs = null;

		String User_Search = null;
		String User_Action = null;
		String User_Session = "";
		String User_Sort = "";
		String generate_labels = "";

		User_Session = request.getParameter("session");
		if (User_Session == null)
			User_Session = "New";
		User_Action = request.getParameter("UserAction");
		if (User_Action == null)
			User_Action = "New";
		generate_labels = request.getParameter("generate_labels");
		if (generate_labels == null)
			generate_labels = "No";
		paper_size = request.getParameter("paperSize");
		if (paper_size == null)
			paper_size = "31";
		User_Search = request.getParameter("SearchField");
		if (User_Search == null)
			User_Search = "";
		User_Sort = request.getParameter("SortBy");
		if (User_Sort == null)
			User_Sort = "receipt_id desc";

		numOldDays = BothHelpObjs.makeBlankFromNull(request
				.getParameter("nodaysold"));
		if (numOldDays == null)
			numOldDays = "";

		String invengrp = request.getParameter("invengrp");
		if (invengrp == null)
			invengrp = "";
		invengrp = invengrp.trim();

		String showneedingprint = request.getParameter("showneedingprint");
		if (showneedingprint == null)
			showneedingprint = "";
		showneedingprint = showneedingprint.trim();

		String showissuedreceipts = request.getParameter("showissuedreceipts");
		if (showissuedreceipts == null)
			showissuedreceipts = "";
		showissuedreceipts = showissuedreceipts.trim();

		thedecidingRandomNumber = request
				.getParameter("thedecidingRandomNumber");
		if (thedecidingRandomNumber == null)
			thedecidingRandomNumber = "";
		thedecidingRandomNumber = thedecidingRandomNumber.trim();

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
			searchlike = "RECEIPT_ID";
		}

		String printKitLabels = request.getParameter("printKitLabels");
		if (printKitLabels == null)
			printKitLabels = "";

		String[] lotStatusarray = { "All" };
		lotStatusarray = request.getParameterValues("lotStatus");
		String lotStringfromArray = "";
		Vector selectedLotStatus = new Vector();
		int statusAdded = 0;
		if (lotStatusarray != null) {
			for (int loop = 0; loop < lotStatusarray.length; loop++) {
				if (statusAdded > 0) {
					lotStringfromArray += ",";
				}
				if ("All".equalsIgnoreCase(lotStatusarray[loop])
						|| lotStatusarray[loop].length() == 0) {

				} else {
					lotStringfromArray += "'" + lotStatusarray[loop] + "'";
					statusAdded++;
				}
				selectedLotStatus.add(lotStatusarray[loop]);
			}
		}
		if (lotStringfromArray.length() == 0) {
			lotStringfromArray = "";
			selectedLotStatus.add("");
		}

		String transactiondate = request.getParameter("transactiondate");
		if (transactiondate == null)
			transactiondate = "";

		String receivedbyid = request.getParameter("receivedbyid");
		if (receivedbyid == null)
			receivedbyid = "";

		String receivedbyname = request.getParameter("receivedbyname");
		if (receivedbyname == null)
			receivedbyname = "";

		String addbin_bin_id = "";
		String addbin_item_id = "";
		try {

			Random rand = new Random();
			int tmpReq = rand.nextInt();
			Integer tmpReqNum = new Integer(tmpReq);

			String SubUser_Action = request.getParameter("SubUserAction");
			if (SubUser_Action == null)
				SubUser_Action = "JSError2";

			if (!(SubUser_Action.equalsIgnoreCase("AddBin")
					|| User_Action.equalsIgnoreCase("receiptnotesUpdate") || User_Action
						.equalsIgnoreCase("addReceiptNotes"))) {
				if (thedecidingRandomNumber.length() > 0) {
					String randomnumberfromsesion = session
							.getAttribute("LOGRNDCOOKIE") == null ? ""
							: session.getAttribute("LOGRNDCOOKIE").toString();
					if (randomnumberfromsesion
							.equalsIgnoreCase(thedecidingRandomNumber)) {
						thedecidingRandomNumber = tmpReqNum.toString();
						session.setAttribute("LOGRNDCOOKIE",
								thedecidingRandomNumber);
					} else {
						thedecidingRandomNumber = tmpReqNum.toString();
						session.setAttribute("LOGRNDCOOKIE",
								thedecidingRandomNumber);
						session.setAttribute("LOGISTICS_DATA", new Vector());
						Vector lot_status_set = (Vector) session
								.getAttribute("STATUS_SET");
						buildHeader(null, branch_plant, User_Search, User_Sort,
								"", hubInventoryGroupOvBeanCollection,
								invengrp, showneedingprint, searchlike,
								searchfor, showissuedreceipts, session,
								printKitLabels, lot_status_set,
								selectedLotStatus, out, lotStringfromArray,
								transactiondate, receivedbyid, receivedbyname,
								CompanyID);
						out.println("<BR><BR><CENTER><FONT SIZE=\"4\" FACE=\"Arial\" COLOR=\"#fc0303\"><B>"
								+ res.getString("label.pleasenobackbutton")
								+ "</B><BR></FONT>");
						return;
					}
				} else {
					thedecidingRandomNumber = tmpReqNum.toString();
					session.setAttribute("LOGRNDCOOKIE",
							thedecidingRandomNumber);
				}
			}

			String logisticsDataLoaded = session
					.getAttribute("LOGISTICS_DATA_LOADED") == null ? ""
					: session.getAttribute("LOGISTICS_DATA_LOADED").toString();
			
			if (!logisticsDataLoaded.equalsIgnoreCase("Yes")) {
				Vector lot_status_set11 = radian.web.HTMLHelpObj
						.createStatusSet(db);
				session.setAttribute("STATUS_SET", lot_status_set11);
				session.setAttribute("HUB_COMPANY_IDS",
						radian.web.HTMLHelpObj.createCompanylist(db));

				Vector expdate_invengrps = new Vector();
				Vector lotstatus_invengrps = new Vector();
				Vector lnventory_invengrps = new Vector();
				Vector nonpickable_invengrps = new Vector();

				expdate_invengrps = radian.web.HTMLHelpObj.createvgrpmemlist(
						db, personnelid, "ExpUpdate");
				lotstatus_invengrps = radian.web.HTMLHelpObj.createvgrpmemlist(
						db, personnelid, "PickStatusUpd");
				lnventory_invengrps = radian.web.HTMLHelpObj.createvgrpmemlist(
						db, personnelid, "Inventory");
				session.setAttribute("EXP_ALLOWED_INVENGRP", expdate_invengrps);
				session.setAttribute("STATUS_SET_ALLOWED_INVENGRP",
						lotstatus_invengrps);
				session.setAttribute("INVENTORY_ALLOWED_INVENGRP",
						lnventory_invengrps);
				nonpickable_invengrps = radian.web.HTMLHelpObj
						.createvgrpmemlist(db, personnelid,
								"onlynonPickableStatus");
				session.setAttribute("NON_PICKABLE_INVENGRP",
						nonpickable_invengrps);

				session.setAttribute("LOGISTICS_DATA_LOADED", "Yes");
			}
			if (User_Session.equalsIgnoreCase("New")) {

				Vector lot_status_set = (Vector) session
						.getAttribute("STATUS_SET");
				if (hubInventoryGroupOvBeanCollection.size() < 1) {
					buildHeader(null, "", User_Search, User_Sort, "",
							hubInventoryGroupOvBeanCollection, invengrp,
							showneedingprint, searchlike, searchfor,
							showissuedreceipts, session, printKitLabels,
							lot_status_set, selectedLotStatus, out,
							lotStringfromArray, transactiondate, receivedbyid,
							receivedbyname, CompanyID);
					out.println(radian.web.HTMLHelpObj
							.printHTMLNoFacilities(res));
				} else {
					buildHeader(null, "", User_Search, User_Sort, "",
							hubInventoryGroupOvBeanCollection, invengrp,
							showneedingprint, searchlike, searchfor,
							showissuedreceipts, session, printKitLabels,
							lot_status_set, selectedLotStatus, out,
							lotStringfromArray, transactiondate, receivedbyid,
							receivedbyname, CompanyID);
					out.println(radian.web.HTMLHelpObj.printHTMLSelect(res));
				}

				hubInventoryGroupOvBeanCollection = null;

			} else if ((User_Session.equalsIgnoreCase("Active"))
					&& (User_Action.equalsIgnoreCase("New"))) {

				Vector openorder_data = new Vector();

				openorder_data = hubObj.getAllopenOrder(branch_plant,
						User_Search, User_Sort, numOldDays, invengrp,
						showneedingprint, searchlike, searchfor,
						showissuedreceipts, hubInventoryGroupOvBeanCollection,
						lotStringfromArray, transactiondate, receivedbyid,
						personnelid, CompanyID);

				Vector openbinSet = new Vector();
				if (!branch_plant.trim().equalsIgnoreCase("All")) {
					openbinSet = radian.web.HTMLHelpObj.createEmptyBinSet(db,
							branch_plant);
				}
				session.setAttribute("EMPTYBIN", openbinSet);
				session.setAttribute("UPDATEDONE", "NO");
				Vector lot_status_set = (Vector) session
						.getAttribute("STATUS_SET");
				Hashtable sum = (Hashtable) (openorder_data.elementAt(0));
				int count = ((Integer) sum.get("TOTAL_NUMBER")).intValue();
				if (0 == count) {
					buildHeader(null, branch_plant, User_Search, User_Sort, "",
							hubInventoryGroupOvBeanCollection, invengrp,
							showneedingprint, searchlike, searchfor,
							showissuedreceipts, session, printKitLabels,
							lot_status_set, selectedLotStatus, out,
							lotStringfromArray, transactiondate, receivedbyid,
							receivedbyname, CompanyID);
					out.println(radian.web.HTMLHelpObj.printHTMLNoData(res
							.getString("msg.noitemfound")));
					// clean up
					openorder_data = null;
				} else {
					Hashtable in_bin_set = new Hashtable();
					Hashtable out_bin_set2 = new Hashtable();

					Vector expdate_invengrps = (Vector) session
							.getAttribute("EXP_ALLOWED_INVENGRP");
					Vector lotstatus_invengrps = (Vector) session
							.getAttribute("STATUS_SET_ALLOWED_INVENGRP");
					Vector lnventory_invengrps = (Vector) session
							.getAttribute("INVENTORY_ALLOWED_INVENGRP");
					Vector nonpickable_invengrps = (Vector) session
							.getAttribute("NON_PICKABLE_INVENGRP");
					session.setAttribute("LOGISTICS_DATA", openorder_data);
					session.setAttribute("BIN_SET", out_bin_set2);
					session.setAttribute("SEARCHED_HUB", branch_plant);
					session.setAttribute("SEARCHED_STRING", User_Search);
					session.setAttribute("SEARCHED_WITHIN", numOldDays);

					buildHeader(openorder_data, branch_plant, User_Search,
							User_Sort, "", hubInventoryGroupOvBeanCollection,
							invengrp, showneedingprint, searchlike, searchfor,
							showissuedreceipts, session, printKitLabels,
							lot_status_set, selectedLotStatus, out,
							lotStringfromArray, transactiondate, receivedbyid,
							receivedbyname, CompanyID);

					buildDetails(openorder_data, out_bin_set2, lot_status_set,
							"NONE", "", expdate_invengrps, lotstatus_invengrps,
							lnventory_invengrps, nonpickable_invengrps, out);

					// clean up
					openorder_data = null;
					in_bin_set = null;
					out_bin_set2 = null;
					lot_status_set = null;
				} // when there are open orders for hub

				hubInventoryGroupOvBeanCollection = null;
				openorder_data = null;
			} else if ((User_Session.equalsIgnoreCase("Active"))
					&& (User_Action.equalsIgnoreCase("Update"))) {
				if (SubUser_Action.equalsIgnoreCase("generatexls")) {
					Vector exlsdata = (Vector) session
							.getAttribute("LOGISTICS_DATA");
					out.println(buildXlsDetails(
							(String) session.getAttribute("SEARCHED_HUB"),
							(String) session.getAttribute("SEARCHED_STRING"),
							(String) session.getAttribute("SEARCHED_WITHIN"),
							personnelid, exlsdata, res));
					exlsdata = null;
					return;
				}
				Vector all_status_set_e = (Vector) session
						.getAttribute("STATUS_SET");
				Vector retrn_data = (Vector) session
						.getAttribute("LOGISTICS_DATA");
				Vector lnventory_invengrps = (Vector) session
						.getAttribute("INVENTORY_ALLOWED_INVENGRP");
				Vector synch_data = SynchServerData(request, retrn_data,
						SubUser_Action, all_status_set_e, lnventory_invengrps,
						out);
				retrn_data = null;

				if (SubUser_Action.equalsIgnoreCase("JSError2")) {
					Hashtable all_bin_set_e = (Hashtable) session
							.getAttribute("BIN_SET");

					Vector expdate_invengrps = (Vector) session
							.getAttribute("EXP_ALLOWED_INVENGRP");
					Vector lotstatus_invengrps = (Vector) session
							.getAttribute("STATUS_SET_ALLOWED_INVENGRP");
					Vector nonpickable_invengrps = (Vector) session
							.getAttribute("NON_PICKABLE_INVENGRP");

					session.setAttribute("LOGISTICS_DATA", synch_data);

					buildHeader(synch_data, branch_plant, User_Search,
							User_Sort, "", hubInventoryGroupOvBeanCollection,
							invengrp, showneedingprint, searchlike, searchfor,
							showissuedreceipts, session, printKitLabels,
							all_status_set_e, selectedLotStatus, out,
							lotStringfromArray, transactiondate, receivedbyid,
							receivedbyname, CompanyID);
					out.println(radian.web.HTMLHelpObj
							.printJavaScriptError(res));
					buildDetails(synch_data, all_bin_set_e, all_status_set_e,
							"NONE", "", expdate_invengrps, lotstatus_invengrps,
							lnventory_invengrps, nonpickable_invengrps, out);

					synch_data = null;
					all_bin_set_e = null;
					all_status_set_e = null;
				} else if (SubUser_Action.equalsIgnoreCase("AddBin")) {
					String AddBin_Line_No = "0";
					addbin_item_id = request.getParameter("AddBin_Item_Id");
					if (addbin_item_id == null)
						addbin_item_id = "";
					addbin_bin_id = request.getParameter("AddBin_Bin_Id");
					if (addbin_bin_id == null)
						addbin_bin_id = "";
					AddBin_Line_No = request.getParameter("AddBin_Line_No");
					if (AddBin_Line_No == null)
						AddBin_Line_No = "0";

					setAddBinLineNum(Integer.parseInt(AddBin_Line_No));

					Hashtable all_bin_set_a = (Hashtable) session
							.getAttribute("BIN_SET");
					Vector all_status_set_a = (Vector) session
							.getAttribute("STATUS_SET");
					Hashtable all_new_bin_set = addToItemBinSet(addbin_item_id,
							addbin_bin_id, all_bin_set_a, out);
					Vector expdate_invengrps = (Vector) session
							.getAttribute("EXP_ALLOWED_INVENGRP");
					Vector lotstatus_invengrps = (Vector) session
							.getAttribute("STATUS_SET_ALLOWED_INVENGRP");
					session.setAttribute("LOGISTICS_DATA", synch_data);
					Vector nonpickable_invengrps = (Vector) session
							.getAttribute("NON_PICKABLE_INVENGRP");

					buildHeader(synch_data, branch_plant, User_Search,
							User_Sort, "", hubInventoryGroupOvBeanCollection,
							invengrp, showneedingprint, searchlike, searchfor,
							showissuedreceipts, session, printKitLabels,
							all_status_set_a, selectedLotStatus, out,
							lotStringfromArray, transactiondate, receivedbyid,
							receivedbyname, CompanyID);
					buildDetails(synch_data, all_new_bin_set, all_status_set_a,
							addbin_item_id, "", expdate_invengrps,
							lotstatus_invengrps, lnventory_invengrps,
							nonpickable_invengrps, out);

					synch_data = null;
					all_new_bin_set = null;
					all_status_set_a = null;
					all_bin_set_a = null;
					//
				} else if (SubUser_Action.equalsIgnoreCase("UpdPage")
						|| SubUser_Action
								.equalsIgnoreCase("generatelargelabels")
						|| SubUser_Action.equalsIgnoreCase("generatelabels")
						|| SubUser_Action.equalsIgnoreCase("generatealllabels")) {
					Hashtable temp1 = (Hashtable) session
							.getAttribute("BIN_SET");
					Vector temp2 = (Vector) session.getAttribute("STATUS_SET");
					Vector expdate_invengrps = (Vector) session
							.getAttribute("EXP_ALLOWED_INVENGRP");
					Vector lotstatus_invengrps = (Vector) session
							.getAttribute("STATUS_SET_ALLOWED_INVENGRP");
					Vector nonpickable_invengrps = (Vector) session
							.getAttribute("NON_PICKABLE_INVENGRP");

					session.setAttribute("LOGISTICS_DATA", synch_data);
					Hashtable updateresults = UpdateDetails(synch_data,
							personnelid, SubUser_Action, lnventory_invengrps,
							out, personnelBean);
					session.setAttribute("UPDATEDONE", "YES");

					Boolean resultfromup = (Boolean) updateresults
							.get("RESULT");
					Vector errordata = (Vector) updateresults.get("ERRORDATA");
					Vector errMsgs = hubObj.getErrMsgs();
					session.setAttribute("LOGISTICS_DATA", errordata);
					if ("generatelargelabels".equalsIgnoreCase(SubUser_Action)) {
						session.setAttribute("LARGE_LABEL_DATA", errordata);
					}

					boolean resulttotest = resultfromup.booleanValue();
					boolean generatelabel = false;
					if (SubUser_Action.equalsIgnoreCase("generatelabels")
							|| SubUser_Action
									.equalsIgnoreCase("generatealllabels")
							|| SubUser_Action
									.equalsIgnoreCase("generatelargelabels")) {
						generatelabel = true;
					}

					// 12-06-01
					session.setAttribute("LABEL_SEQUENCE_NUMBERS",
							receiptIdstoLabel);
					session.setAttribute("LABEL_QUANTITYS", numbersoflabels);

					if (false == resulttotest) {
						if (receiptIdstoLabel.size() > 0 && generatelabel) {
							if (SubUser_Action
									.equalsIgnoreCase("generatelargelabels")) {
								buildHeader(synch_data, branch_plant,
										User_Search, User_Sort,
										"GENERATE_LARGE_LABELS",
										hubInventoryGroupOvBeanCollection,
										invengrp, showneedingprint, searchlike,
										searchfor, showissuedreceipts, session,
										printKitLabels, temp2,
										selectedLotStatus, out,
										lotStringfromArray, transactiondate,
										receivedbyid, receivedbyname, CompanyID);
							} else {
								buildHeader(synch_data, branch_plant,
										User_Search, User_Sort,
										"GENERATE_LABELS",
										hubInventoryGroupOvBeanCollection,
										invengrp, showneedingprint, searchlike,
										searchfor, showissuedreceipts, session,
										printKitLabels, temp2,
										selectedLotStatus, out,
										lotStringfromArray, transactiondate,
										receivedbyid, receivedbyname, CompanyID);
							}
						} else {
							buildHeader(synch_data, branch_plant, User_Search,
									User_Sort, "",
									hubInventoryGroupOvBeanCollection,
									invengrp, showneedingprint, searchlike,
									searchfor, showissuedreceipts, session,
									printKitLabels, temp2, selectedLotStatus,
									out, lotStringfromArray, transactiondate,
									receivedbyid, receivedbyname, CompanyID);
						}

						if (true == noneToUpd) {
							out.println(radian.web.HTMLHelpObj
									.printMessageinTable(res
											.getString("err.noupdategen")));
						} else {
							out.println(radian.web.HTMLHelpObj
									.printMessageinTable(res
											.getString("msg.tcmiserror")));
						}
						buildDetails(errordata, temp1, temp2, "NONE",
								SubUser_Action, expdate_invengrps,
								lotstatus_invengrps, lnventory_invengrps,
								nonpickable_invengrps, out);

					} else {
						if (true == completeSuccess) {
							if (generatelabel) {
								if (SubUser_Action
										.equalsIgnoreCase("generatelargelabels")) {
									buildHeader(synch_data, branch_plant,
											User_Search, User_Sort,
											"GENERATE_LARGE_LABELS",
											hubInventoryGroupOvBeanCollection,
											invengrp, showneedingprint,
											searchlike, searchfor,
											showissuedreceipts, session,
											printKitLabels, temp2,
											selectedLotStatus, out,
											lotStringfromArray,
											transactiondate, receivedbyid,
											receivedbyname, CompanyID);
								} else {
									buildHeader(synch_data, branch_plant,
											User_Search, User_Sort,
											"GENERATE_LABELS",
											hubInventoryGroupOvBeanCollection,
											invengrp, showneedingprint,
											searchlike, searchfor,
											showissuedreceipts, session,
											printKitLabels, temp2,
											selectedLotStatus, out,
											lotStringfromArray,
											transactiondate, receivedbyid,
											receivedbyname, CompanyID);
								}
							} else {
								buildHeader(synch_data, branch_plant,
										User_Search, User_Sort, "",
										hubInventoryGroupOvBeanCollection,
										invengrp, showneedingprint, searchlike,
										searchfor, showissuedreceipts, session,
										printKitLabels, temp2,
										selectedLotStatus, out,
										lotStringfromArray, transactiondate,
										receivedbyid, receivedbyname, CompanyID);
							}

							out.println(radian.web.HTMLHelpObj
									.printMessageinTable(res
											.getString("msg.updatesuccess")));

							buildDetails(errordata, temp1, temp2, "NONE",
									SubUser_Action, expdate_invengrps,
									lotstatus_invengrps, lnventory_invengrps,
									nonpickable_invengrps, out);
						} else {
							if (generatelabel) {
								if (SubUser_Action
										.equalsIgnoreCase("generatelargelabels")) {
									buildHeader(synch_data, branch_plant,
											User_Search, User_Sort,
											"GENERATE_LARGE_LABELS",
											hubInventoryGroupOvBeanCollection,
											invengrp, showneedingprint,
											searchlike, searchfor,
											showissuedreceipts, session,
											printKitLabels, temp2,
											selectedLotStatus, out,
											lotStringfromArray,
											transactiondate, receivedbyid,
											receivedbyname, CompanyID);
								} else {
									buildHeader(synch_data, branch_plant,
											User_Search, User_Sort,
											"GENERATE_LABELS",
											hubInventoryGroupOvBeanCollection,
											invengrp, showneedingprint,
											searchlike, searchfor,
											showissuedreceipts, session,
											printKitLabels, temp2,
											selectedLotStatus, out,
											lotStringfromArray,
											transactiondate, receivedbyid,
											receivedbyname, CompanyID);
								}
							} else {
								buildHeader(synch_data, branch_plant,
										User_Search, User_Sort, "",
										hubInventoryGroupOvBeanCollection,
										invengrp, showneedingprint, searchlike,
										searchfor, showissuedreceipts, session,
										printKitLabels, temp2,
										selectedLotStatus, out,
										lotStringfromArray, transactiondate,
										receivedbyid, receivedbyname, CompanyID);
							}

							out.println(radian.web.HTMLHelpObj
									.printHTMLPartialSuccess(errMsgs, res));
							buildDetails(errordata, temp1, temp2, "NONE",
									SubUser_Action, expdate_invengrps,
									lotstatus_invengrps, lnventory_invengrps,
									nonpickable_invengrps, out);
						}
					}
					// clean up
					synch_data = null;
					temp1 = null;
					temp2 = null;
					//
				} else {
					invlog.debug("Line 633: User_Session==" + User_Session
							+ ", User_action==" + User_Action
							+ ", SubUser_Action==" + SubUser_Action);
					out.println(radian.web.HTMLHelpObj.printError("title.inv",
							intcmIsApplication, res));
				}

			}// End of User_Session="Active" && User_Action="Update"
			else if ((User_Session.equalsIgnoreCase("Active"))
					&& (User_Action.equalsIgnoreCase("JSError1"))) {
				Vector retrn_data = (Vector) session
						.getAttribute("LOGISTICS_DATA");
				Vector all_status_set_e = (Vector) session
						.getAttribute("STATUS_SET");
				Vector lnventory_invengrps = (Vector) session
						.getAttribute("INVENTORY_ALLOWED_INVENGRP");
				Vector synch_data = SynchServerData(request, retrn_data, "",
						all_status_set_e, lnventory_invengrps, out);
				retrn_data = null;

				Hashtable all_bin_set_e = (Hashtable) session
						.getAttribute("BIN_SET");

				Vector expdate_invengrps = (Vector) session
						.getAttribute("EXP_ALLOWED_INVENGRP");
				Vector lotstatus_invengrps = (Vector) session
						.getAttribute("STATUS_SET_ALLOWED_INVENGRP");
				Vector nonpickable_invengrps = (Vector) session
						.getAttribute("NON_PICKABLE_INVENGRP");

				session.setAttribute("LOGISTICS_DATA", synch_data);

				buildHeader(synch_data, branch_plant, User_Search, User_Sort,
						"", hubInventoryGroupOvBeanCollection, invengrp,
						showneedingprint, searchlike, searchfor,
						showissuedreceipts, session, printKitLabels,
						all_status_set_e, selectedLotStatus, out,
						lotStringfromArray, transactiondate, receivedbyid,
						receivedbyname, CompanyID);
				out.println(radian.web.HTMLHelpObj.printJavaScriptError(res));

				buildDetails(synch_data, all_bin_set_e, all_status_set_e,
						"NONE", "", expdate_invengrps, lotstatus_invengrps,
						lnventory_invengrps, nonpickable_invengrps, out);

				synch_data = null;
				all_bin_set_e = null;
				all_status_set_e = null;

			} else if ((User_Session.equalsIgnoreCase("Active"))
					&& (User_Action.equalsIgnoreCase("receiptnotesUpdate"))) {

				String receiptIdnote = BothHelpObjs.makeBlankFromNull(request
						.getParameter("receiptId"));
				if (receiptIdnote == null)
					receiptIdnote = "";

				String receiptComments = BothHelpObjs.makeBlankFromNull(request
						.getParameter("receiptComments"));
				if (receiptComments == null)
					receiptComments = "";
				receiptComments = BothHelpObjs
						.changeSingleQuotetoTwoSingleQuote(receiptComments);

				boolean addedreceiptNotes = false;
				try {
					String receiptuptdatequery = "update receipt set NOTES = '"
							+ receiptComments + "' where receipt_id = "
							+ receiptIdnote + "";
					db.doUpdate(receiptuptdatequery);
					addedreceiptNotes = true;
				} catch (Exception e) {
					invlog.error(e.getMessage(), e);
				} finally {

				}

				out.println(radian.web.HTMLHelpObj.printHTMLHeader(
						"transactions.receiptnotes", "receivingjs.js",
						intcmIsApplication, res));
				out.println("</HEAD>  \n");

				out.println("<BODY>\n");
				out.println("<FORM METHOD=\"POST\" id=\"receiptNotes\" name=\"receiptNotes\" action=\""
						+ Receiving_Servlet + "\">\n");

				if (addedreceiptNotes) {
					out.println("<TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"0\" CELLSPACING=\"0\" CLASS=\"moveup\">\n");
					out.println("<TR><TD WIDTH=\"100%\" CLASS=\"announce\" ALIGN=\"LEFT\">"
							+ res.getString("updatereceiptnotes.notesforreceipt")
							+ ":\n");
					out.println("" + receiptIdnote
							+ res.getString("msg.addsucess"));
					out.println("</TD></TR>\n");

					out.println("</TABLE>\n");
					out.println("<CENTER><BR><BR>\n");
					out.println("&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"searchlike\" name=\"searchlike\" value=\"Ok\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n");
				} else {
					out.println("<TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"0\" CELLSPACING=\"0\" CLASS=\"moveup\">\n");
					out.println("<TR><TD WIDTH=\"100%\" CLASS=\"announce\" ALIGN=\"LEFT\">"
							+ res.getString("msg.errornote") + "\n");
					out.println("" + receiptIdnote
							+ res.getString("msg.errornote2") + "\n");
					out.println("</TD></TR>\n");

					out.println("</TABLE>\n");
					out.println("<CENTER><BR><BR>\n");
					out.println("&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"searchlike\" name=\"searchlike\" value=\"Ok\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n");
				}
				out.println("</FORM></BODY></HTML>\n");

			} else if ((User_Session.equalsIgnoreCase("Active"))
					&& (User_Action.equalsIgnoreCase("addReceiptNotes"))) {

				String receiptIdnote = BothHelpObjs.makeBlankFromNull(request
						.getParameter("receiptId"));
				if (receiptIdnote == null)
					receiptIdnote = "";

				out.println(radian.web.HTMLHelpObj.printHTMLHeader(
						"transactions.receiptnotes", "receivingjs.js",
						intcmIsApplication, res));
				out.println("</HEAD>  \n");

				out.println("<BODY>\n");
				out.println("<FORM METHOD=\"POST\" id=\"receiptNotes\" name=\"receiptNotes\" action=\""
						+ Receiving_Servlet + "\">\n");

				if (receiptIdnote.trim().length() > 0) {
					String notesfromReceipt = "";
					try {
						String query = "select notes from receipt where receipt_id = "
								+ receiptIdnote + "";
						receiptdbrs = db.doQuery(query);
						receiptrs = receiptdbrs.getResultSet();

						while (receiptrs.next()) {
							notesfromReceipt = receiptrs.getString("NOTES") == null ? ""
									: receiptrs.getString("NOTES");
						}
					} catch (Exception e) {
						invlog.error(e.getMessage(), e);
					} finally {
						if (receiptdbrs != null) {
							receiptdbrs.close();
						}
					}

					out.println("<INPUT TYPE=\"hidden\" ID=\"UserAction\" NAME=\"UserAction\" VALUE=\"receiptnotesUpdate\">\n");
					out.println("<INPUT TYPE=\"hidden\" ID=\"session\" NAME=\"session\" VALUE=\"Active\">\n");
					out.println("<INPUT TYPE=\"hidden\" ID=\"receiptId\" NAME=\"receiptId\" VALUE=\""
							+ receiptIdnote + "\">\n");

					out.println("<TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"0\" CELLSPACING=\"0\" CLASS=\"moveup\">\n");
					out.println("<TR><TD WIDTH=\"5%\" CLASS=\"announce\" ALIGN=\"RIGHT\"><B>Receipt ID:</B></TD><TD WIDTH=\"45%\">\n");
					out.println("&nbsp;" + receiptIdnote + "\n");
					out.println("</TD></TR>\n");

					out.println("<TR>\n");
					out.println("<TD WIDTH=\"5%\" CLASS=\"announce\" ALIGN=\"RIGHT\"><B>Notes:</B></TD>\n");
					out.println("<TD WIDTH=\"45%\" CLASS=\"announce\">\n");
					out.println("<TEXTAREA id=\"receiptComments\" name=\"receiptComments\" rows=\"5\" cols=\"70\">"
							+ notesfromReceipt + "</TEXTAREA></TD>\n");
					out.println("</TD>\n");
					out.println("</TR>\n");

					out.println("</TABLE>\n");
					out.println("<CENTER><BR><BR><INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" type=\"submit\" value=\"Add\" id=\"CreateNew\" name=\"CreateNew\">\n");
					out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"searchlike\" name=\"searchlike\" value=\"Cancel\" OnClick=\"cancel()\" type=\"button\"></CENTER>\n");
				} else {
					out.println(res.getString("No Receipt Id Choosen.") + "\n");
				}
				out.println("</FORM></BODY></HTML>\n");
			} else {
				invlog.debug("Line 783: User_Session==" + User_Session
						+ ", User_action==" + User_Action
						+ ", SubUser_Action==" + SubUser_Action);
				out.println(radian.web.HTMLHelpObj.printError("title.inv",
						intcmIsApplication, res));
			}
		} catch (Exception e) {
			invlog.error(e.getMessage(), e);
			out.println(radian.web.HTMLHelpObj.printError("title.inv",
					intcmIsApplication, res));
		} finally {
			out.close();
		}

		// clean up
		hubObj = null;
		return;
	}

	private Vector SynchServerData(HttpServletRequest request, Vector in_data,
			String subuseraction, Vector all_status_set,
			Vector allowedigforinv, PrintWriter out) {
		Vector new_data = new Vector();
		Hashtable sum = (Hashtable) (in_data.elementAt(0));
		int count = ((Integer) sum.get("TOTAL_NUMBER")).intValue();
		new_data.addElement(sum);

		try {
			for (int i = 1; i < count + 1; i++) {
				Hashtable hD = new Hashtable();
				hD = (Hashtable) (in_data.elementAt(i));

				String receipt_id = "";
				receipt_id = request.getParameter(("receipt_id" + i));
				if (receipt_id == null)
					receipt_id = "";

				if (receipt_id
						.equalsIgnoreCase(hD.get("RECEIPT_ID").toString())) {
					String check = "";
					check = request.getParameter(("row_chk" + i));
					if (check == null)
						check = "";
					hD.remove("USERCHK");
					hD.put("USERCHK", check);

					String qty_recd = "";
					qty_recd = request.getParameter(("qty_recd" + i));
					if (qty_recd == null)
						qty_recd = "";

					String invengrp = BothHelpObjs.makeBlankFromNull(hD.get(
							"INVENTORY_GROUP").toString());
					String qualitycntitem = (hD.get("QUALITY_CONTROL_ITEM") == null ? " "
							: hD.get("QUALITY_CONTROL_ITEM").toString());
					boolean checkforpick = false;
					if ("Y".equalsIgnoreCase(qualitycntitem)
							&& "yes".equalsIgnoreCase(check)) {
						checkforpick = true;
					}

					hD.remove("NO_OF_LABELS");
					hD.put("NO_OF_LABELS", qty_recd);

					if (("generatelabels".equalsIgnoreCase(subuseraction)
							|| "generatealllabels"
									.equalsIgnoreCase(subuseraction) || subuseraction
								.equalsIgnoreCase("generatelargelabels"))
							&& checkforpick) {
						hD.remove("PICKABLE");
						hD.put("PICKABLE", "Y");
					} else if (allowedigforinv.contains(invengrp)) {
						String status_id = "";
						status_id = request
								.getParameter(("selectElementStatus" + i));
						if (status_id == null)
							status_id = "";
						status_id = status_id.trim();
						if (!status_id.equalsIgnoreCase(hD.get("LOT_STATUS")
								.toString()) && status_id.length() > 0) {
							hD.remove("LOT_STATUS");
							hD.put("LOT_STATUS", status_id);

							hD.remove("DOSTATUSUPDATE");
							hD.put("DOSTATUSUPDATE", "Yes");
							
							hD.remove("LOTSTATUSUPDATE");
							hD.put("LOTSTATUSUPDATE", "Yes");

							hD.put("SYNCWITHWMS", true);

							if (checkforpick) {
								for (int h = 0; h < all_status_set.size(); h++) {
									Hashtable data1 = (Hashtable) all_status_set
											.elementAt(h);
									Enumeration E;
									for (E = data1.keys(); E.hasMoreElements();) {
										String key = (String) E.nextElement();
										String keyvalue = data1.get(key)
												.toString();

										if (status_id.equalsIgnoreCase(key)
												&& "Y".equalsIgnoreCase(keyvalue)) {
											hD.remove("CERTUPDATE");
											hD.put("CERTUPDATE", "Yes");
										}
									}
								}
							}
						}

						String rootcause = "";
						rootcause = request.getParameter(("rootcause" + i));
						if (rootcause == null)
							rootcause = "";

						String rootcausecompany = "";
						rootcausecompany = request
								.getParameter(("rootcausecompany" + i));
						if (rootcausecompany == null)
							rootcausecompany = "";

						String rootcausenotes = "";
						rootcausenotes = request
								.getParameter(("rootcausenotes" + i));
						if (rootcausenotes == null)
							rootcausenotes = "";

						if (!rootcause.equalsIgnoreCase(hD.get(
								"TERMINAL_ROOT_CAUSE").toString())
								|| !rootcausecompany.equalsIgnoreCase(hD.get(
										"TERMINAL_COMPANY").toString())
								|| !rootcausenotes.equalsIgnoreCase(hD.get(
										"TERMINAL_NOTES").toString())) {
							hD.remove("DOSTATUSUPDATE");
							hD.put("DOSTATUSUPDATE", "Yes");
						}

						hD.remove("TERMINAL_ROOT_CAUSE");
						hD.put("TERMINAL_ROOT_CAUSE", rootcause);

						hD.remove("TERMINAL_COMPANY");
						hD.put("TERMINAL_COMPANY", rootcausecompany);

						hD.remove("TERMINAL_NOTES");
						hD.put("TERMINAL_NOTES", rootcausenotes);

						String bin_name = "";
						bin_name = request
								.getParameter(("selectElementBin" + i));
						if (bin_name == null)
							bin_name = "";
						if (!bin_name.equalsIgnoreCase(hD.get("BIN_NAME")
								.toString())) {
							hD.remove("BIN_NAME");
							hD.put("BIN_NAME", bin_name);

							hD.remove("DOSTATUSUPDATE");
							hD.put("DOSTATUSUPDATE", "Yes");

							if (checkforpick) {
								hD.remove("PICKABLE");
								hD.put("PICKABLE", "N");
							}
						}

						String deliveryTicket = "";
						deliveryTicket = request
								.getParameter(("deliveryTicket" + i));
						if (deliveryTicket == null)
							deliveryTicket = "";
						if (!deliveryTicket.equals(hD.get("DELIVERY_TICKET")
								.toString())) {
							hD.remove("DELIVERY_TICKET");
							hD.put("DELIVERY_TICKET", deliveryTicket);

							hD.remove("DOSTATUSUPDATE");
							hD.put("DOSTATUSUPDATE", "Yes");
						}

						String qualityTrackingNumber = "";
						qualityTrackingNumber = request
								.getParameter(("qualityTrackingNumber" + i));
						if (qualityTrackingNumber == null)
							qualityTrackingNumber = "";
						if (!qualityTrackingNumber.equals(hD.get(
								"QUALITY_TRACKING_NUMBER").toString())) {
							hD.remove("QUALITY_TRACKING_NUMBER");
							hD.put("QUALITY_TRACKING_NUMBER",
									qualityTrackingNumber);

							hD.remove("DOSTATUSUPDATE");
							hD.put("DOSTATUSUPDATE", "Yes");
						}

						String unitLabelCatPartNo = "";
						unitLabelCatPartNo = request
								.getParameter(("unitLabelCatPartNo" + i));
						if (unitLabelCatPartNo == null)
							unitLabelCatPartNo = "";
						hD.remove("UNIT_LABEL_CAT_PART_NO");
						hD.put("UNIT_LABEL_CAT_PART_NO", unitLabelCatPartNo);
						hD.remove("NEW_UNIT_LABEL_CAT_PART_NO");
						hD.put("NEW_UNIT_LABEL_CAT_PART_NO", unitLabelCatPartNo);

						String unitLabelPrinted = "";
						unitLabelPrinted = request
								.getParameter(("unitLabelPrinted" + i));
						if (unitLabelPrinted == null) {
							unitLabelPrinted = "N";
							hD.remove("UNIT_LABEL_CAT_PART_NO");
							hD.put("UNIT_LABEL_CAT_PART_NO", "");
							hD.remove("NEW_UNIT_LABEL_CAT_PART_NO");
							hD.put("NEW_UNIT_LABEL_CAT_PART_NO", "");
						}
						if (!unitLabelPrinted.equalsIgnoreCase(hD.get(
								"UNIT_LABEL_PRINTED").toString())) {
							hD.remove("UNIT_LABEL_PRINTED");
							hD.put("UNIT_LABEL_PRINTED", unitLabelPrinted);

							hD.remove("DOSTATUSUPDATE");
							hD.put("DOSTATUSUPDATE", "Yes");
						}

						String expiry_date = "";
						expiry_date = request.getParameter(("expiry_date" + i));
						if (expiry_date == null)
							expiry_date = "";

						if (!expiry_date.equalsIgnoreCase(hD.get("EXPIRE_DATE")
								.toString())) {
							hD.remove("EXPIRE_DATE");
							hD.put("EXPIRE_DATE", expiry_date);

							hD.remove("DOSTATUSUPDATE");
							hD.put("DOSTATUSUPDATE", "Yes");
							hD.remove("CERTUPDATE");
							hD.put("CERTUPDATE", "Yes");

							hD.remove("EXPIREDATEUPDATE");
							hD.put("EXPIREDATEUPDATE", "Yes");

							if (checkforpick) {
								hD.remove("PICKABLE");
								hD.put("PICKABLE", "N");
							}
							hD.put("SYNCWITHWMS", true);
						}

						String mfgLabelExpireDate = "";
						mfgLabelExpireDate = request
								.getParameter(("mfgLabelExpireDate" + i));
						if (mfgLabelExpireDate == null)
							mfgLabelExpireDate = "";

						if (!mfgLabelExpireDate.equalsIgnoreCase(hD.get(
								"MFG_LABEL_EXPIRE_DATE").toString())) {
							hD.remove("MFG_LABEL_EXPIRE_DATE");
							hD.put("MFG_LABEL_EXPIRE_DATE", mfgLabelExpireDate);

							hD.remove("DOSTATUSUPDATE");
							hD.put("DOSTATUSUPDATE", "Yes");
						}

						String dateOfShipment = "";
						dateOfShipment = request
								.getParameter(("dateOfShipment" + i));
						if (dateOfShipment == null)
							dateOfShipment = "";
						dateOfShipment = dateOfShipment.trim();

						String beDateOfShipment = (hD.get("DATE_OF_SHIPMENT") == null ? ""
								: hD.get("DATE_OF_SHIPMENT").toString().trim());
						if (!dateOfShipment.equalsIgnoreCase(beDateOfShipment)) {
							hD.remove("DATE_OF_SHIPMENT");
							hD.put("DATE_OF_SHIPMENT", dateOfShipment);

							hD.remove("DOSTATUSUPDATE");
							hD.put("DOSTATUSUPDATE", "Yes");
						}

						String dateOfManufacture = "";
						dateOfManufacture = request
								.getParameter(("dateOfManufacture" + i));
						if (dateOfManufacture == null)
							dateOfManufacture = "";
						dateOfManufacture = dateOfManufacture.trim();

						String beDateOfManufacture = (hD
								.get("DATE_OF_MANUFACTURE") == null ? "" : hD
								.get("DATE_OF_MANUFACTURE").toString().trim());
						if (!dateOfManufacture
								.equalsIgnoreCase(beDateOfManufacture)) {
							hD.remove("DATE_OF_MANUFACTURE");
							hD.put("DATE_OF_MANUFACTURE", dateOfManufacture);

							hD.remove("DOSTATUSUPDATE");
							hD.put("DOSTATUSUPDATE", "Yes");
						}

						String dateOfRepackaging = "";
						dateOfRepackaging = request
								.getParameter(("dateOfRepackaging" + i));
						if (dateOfRepackaging == null)
							dateOfRepackaging = "";
						dateOfRepackaging = dateOfRepackaging.trim();

						String beDateOfRepackaging = (hD
								.get("DATE_OF_REPACKAGING") == null ? "" : hD
								.get("DATE_OF_REPACKAGING").toString().trim());
						if (!dateOfRepackaging
								.equalsIgnoreCase(beDateOfRepackaging)) {
							hD.remove("DATE_OF_REPACKAGING");
							hD.put("DATE_OF_REPACKAGING", dateOfRepackaging);

							hD.remove("DOSTATUSUPDATE");
							hD.put("DOSTATUSUPDATE", "Yes");
						}

						String mfglot = "";
						mfglot = request.getParameter(("mfg_lot" + i));
						if (mfglot == null)
							mfglot = "";
						if (!(mfglot.equals(hD.get("MFG_LOT").toString()) && mfglot
								.length() > 0)) {
							hD.remove("MFG_LOT");
							hD.put("MFG_LOT", mfglot);

							hD.remove("DOSTATUSUPDATE");
							hD.put("DOSTATUSUPDATE", "Yes");
							hD.remove("CERTUPDATE");
							hD.put("CERTUPDATE", "Yes");

							if (checkforpick) {
								hD.remove("PICKABLE");
								hD.put("PICKABLE", "N");
							}
							hD.put("SYNCWITHWMS", true);
						}

						String recerts = "";
						recerts = request.getParameter(("recerts" + i));
						if (recerts == null)
							recerts = "";
						recerts = recerts.trim();

						String befrecerts = (hD.get("RECERT_NUMBER") == null ? ""
								: hD.get("RECERT_NUMBER").toString().trim());
						if (!recerts.equalsIgnoreCase(befrecerts)) {
							hD.remove("RECERT_NUMBER");
							hD.put("RECERT_NUMBER", recerts);

							hD.remove("DOSTATUSUPDATE");
							hD.put("DOSTATUSUPDATE", "Yes");

							hD.remove("CERTUPDATE");
							hD.put("CERTUPDATE", "Yes");

							if (checkforpick) {
								hD.remove("PICKABLE");
								hD.put("PICKABLE", "N");
							}
						}

						String qaStatement = "";
						qaStatement = request.getParameter(("qaStatement" + i));
						if (qaStatement == null)
							qaStatement = "";
						qaStatement = qaStatement.trim();
						String oldQaStatement = (hD.get("QA_STATEMENT") == null ? ""
								: hD.get("QA_STATEMENT").toString().trim());
						if (!qaStatement.equalsIgnoreCase(oldQaStatement)) {
							hD.remove("QA_STATEMENT");
							hD.put("QA_STATEMENT", qaStatement);

							hD.remove("DOSTATUSUPDATE");
							hD.put("DOSTATUSUPDATE", "Yes");
						}

						if (request.getParameter(("origlotstatus" + i)) == null)
							hD.put("ORIG_LOT_STATUS", "");
						else
							hD.put("ORIG_LOT_STATUS", request
									.getParameter(("origlotstatus" + i)
											.toString().trim()));

						String supplierCageCode = (request
								.getParameter("supplierCageCode" + i) == null ? ""
								: request.getParameter("supplierCageCode" + i)
										.trim());
						String oldSupplierCageCode = (hD
								.get("SUPPLIER_CAGE_CODE") == null ? "" : hD
								.get("SUPPLIER_CAGE_CODE").toString().trim());
						if (!supplierCageCode
								.equalsIgnoreCase(oldSupplierCageCode)) {
							hD.put("SUPPLIER_CAGE_CODE", supplierCageCode);
							hD.put("DOSTATUSUPDATE", "Yes");

						}
						
						String countryOfOrigin = (request
								.getParameter("selectElementCountryOfOrigin" + i) == null ? ""
								: request.getParameter("selectElementCountryOfOrigin" + i)
										.trim());
						String oldCountryOfOrigin = (hD
								.get("COUNTRY_OF_ORIGIN") == null ? "" : hD
								.get("COUNTRY_OF_ORIGIN").toString().trim());
						if (!countryOfOrigin.equalsIgnoreCase(oldCountryOfOrigin)) {
							hD.put("COUNTRY_OF_ORIGIN", countryOfOrigin);
							hD.put("DOSTATUSUPDATE", "Yes");
							hD.put("SYNCWITHWMS", true);
						}
						
					}
				}
				new_data.addElement(hD);
			}
		} catch (Exception e) {
			invlog.error(e.getMessage(), e);
			out.println(radian.web.HTMLHelpObj.printError("title.inv",
					intcmIsApplication, res));
		}
		return new_data;
	}

	private Hashtable addToItemBinSet(String item_id, String bin_id,
			Hashtable in_set, PrintWriter out) {
		Hashtable bin_set = new Hashtable();
		try {
			bin_set = (Hashtable) in_set.get(item_id);
			int size = bin_set.size();
			if ((size == 1) && bin_set.containsValue("NONE")) {
				bin_set.remove(new Integer(0));
				bin_set.put(new Integer(0), bin_id);
			} else {
				bin_set.put(new Integer(size), bin_id);
			}
			in_set.put(item_id, bin_set);
		} catch (Exception e) {
			invlog.error(e.getMessage(), e);
			out.println(radian.web.HTMLHelpObj.printError("title.inv",
					intcmIsApplication, res));
		}
		return in_set;
	}

	private Hashtable createItemBinSet(Vector data, String branch_plant,
			PrintWriter out) {
		String item_id;
		Hashtable bin_set = new Hashtable();
		Hashtable sum = (Hashtable) (data.elementAt(0));
		int count = ((Integer) sum.get("TOTAL_NUMBER")).intValue();

		try {
			for (int i = 1; i < count + 1; i++) {
				Hashtable hD = new Hashtable();
				hD = (Hashtable) (data.elementAt(i));
				item_id = (String) hD.get("ITEM_ID");
				if (false == bin_set.containsKey(item_id)) {
					Hashtable bin_for_item = radian.web.HTMLHelpObj
							.CreateBinForItem(db, item_id, branch_plant);
					bin_set.put(item_id, bin_for_item);
				}
			}
		} catch (Exception e) {
			invlog.error(e.getMessage(), e);
			out.println(radian.web.HTMLHelpObj.printError("title.inv",
					intcmIsApplication, res));
		}

		return bin_set;
	}

	// OVERLOAD BELOW FUNCTION FOR QVR PDF
	public Hashtable UpdateDetails(Vector data, String logonid,
			String subuseraction, Vector allowedingrps, PrintWriter out) {
		return UpdateDetails(data, logonid, subuseraction, allowedingrps, out,
				null);

	}

	private Hashtable UpdateDetails(Vector data, String logonid,
			String subuseraction, Vector allowedingrps, PrintWriter out,
			PersonnelBean personnelBean) {
		boolean result = false;
		Hashtable updateresult = new Hashtable();
		Vector errordata = new Vector();
		receiptIdstoLabel = new Vector();
		numbersoflabels = new Vector();
		boolean generatelabel = false;
		if (subuseraction.equalsIgnoreCase("generatelabels")
				|| subuseraction.equalsIgnoreCase("generatealllabels")
				|| subuseraction.equalsIgnoreCase("generatelargelabels")) {
			generatelabel = true;
		}

		try {
			Hashtable summary = new Hashtable();
			summary = (Hashtable) data.elementAt(0);
			int total = ((Integer) (summary.get("TOTAL_NUMBER"))).intValue();
			errordata.addElement(summary);
			boolean callreccomp = false;
			int linecheckcount = 0;
			int lastItemNum = 1;
			boolean one_success = false;

			for (int i = 1; i < total + 1; i++) {
				Hashtable hD = new Hashtable();
				hD = (Hashtable) data.elementAt(i);
				String Line_Check = "";
				String dostatusupdate = "";

				Line_Check = (hD.get("USERCHK") == null ? " " : hD.get(
						"USERCHK").toString());
				dostatusupdate = (hD.get("DOSTATUSUPDATE") == null ? " " : hD
						.get("DOSTATUSUPDATE").toString());
				String lotstatusupdate = (hD.get("LOTSTATUSUPDATE") == null ? " "
						: hD.get("LOTSTATUSUPDATE").toString());
				String mngkitassingl = hD.get("MANAGE_KITS_AS_SINGLE_UNIT")
						.toString();
				String componentid = hD.get("COMPONENT_ID").toString();
				String receipt_id = (hD.get("RECEIPT_ID") == null ? "&nbsp;"
						: hD.get("RECEIPT_ID").toString());
				String invengrp = (hD.get("INVENTORY_GROUP") == null ? " " : hD
						.get("INVENTORY_GROUP").toString());

				String Next_rec = "";
				if (!(i == total)) {
					Hashtable hDNext = new Hashtable();
					hDNext = (Hashtable) data.elementAt(i + 1);
					Next_rec = hDNext.get("RECEIPT_ID") == null ? "&nbsp;"
							: hDNext.get("RECEIPT_ID").toString();
				} else {
					Next_rec = " ";
				}

				boolean Samepoline = false;
				boolean FirstRow = false;
				if ("N".equalsIgnoreCase(mngkitassingl)
						&& Next_rec.equalsIgnoreCase(receipt_id)) {
					Samepoline = true;
					if ("yes".equalsIgnoreCase(Line_Check)
							&& this.getupdateStatus() && !(generatelabel)) {
						callreccomp = true;
					}
					lastItemNum++;
				} else {
					lastItemNum = 1;
				}

				if (Samepoline) {
					if (lastItemNum == 2) {
						FirstRow = true;
					}
				} else if ("Y".equalsIgnoreCase(mngkitassingl)) {
					FirstRow = true;
				}

				if (subuseraction.equalsIgnoreCase("generatealllabels")
						&& (hD.get("NO_OF_LABELS").toString().length() > 0)) {
					numbersoflabels.addElement(hD.get("NO_OF_LABELS")
							.toString());
					receiptIdstoLabel.addElement(hD.get("RECEIPT_ID")
							.toString());
				} else if (subuseraction
						.equalsIgnoreCase("generatelargelabels")
						&& "yes".equalsIgnoreCase(Line_Check)) {
					numbersoflabels.addElement(hD.get("NO_OF_LABELS")
							.toString());
					receiptIdstoLabel.addElement(hD.get("RECEIPT_ID")
							.toString());
				} else if ("yes".equalsIgnoreCase(Line_Check)
						&& (hD.get("NO_OF_LABELS").toString().length() > 0)) {
					numbersoflabels.addElement(hD.get("NO_OF_LABELS")
							.toString());
					receiptIdstoLabel.addElement(hD.get("RECEIPT_ID")
							.toString());
				}

				if (allowedingrps.contains(invengrp)
						&& (("yes".equalsIgnoreCase(Line_Check)
								&& "yes".equalsIgnoreCase(dostatusupdate)
								&& this.getupdateStatus() && !(generatelabel)) || callreccomp)) {
					noneToUpd = false;
					linecheckcount++;
					boolean line_result = false;

					if (FirstRow) {
						line_result = hubObj.UpdateLine(hD, logonid,
								lotstatusupdate);
					}

					if ("N".equalsIgnoreCase(mngkitassingl) && callreccomp) {
						invlog.info("Calling p_receipt_component fromLogistics for COMPONENT_ID "
								+ componentid
								+ " RECEIPT_ID "
								+ receipt_id
								+ " Personnel ID  " + logonid + "");
						line_result = radian.web.HTMLHelpObj.insreceiptcomp(db,
								hD, receipt_id, logonid);
					}

					if (!line_result) {
						completeSuccess = false;

						hD.remove("USERCHK");
						hD.put("USERCHK", " ");

						hD.remove("CERTUPDATE");
						hD.put("CERTUPDATE", " ");

						hD.remove("EXPIREDATEUPDATE");
						hD.put("EXPIREDATEUPDATE", " ");

						hD.remove("DOSTATUSUPDATE");
						hD.put("DOSTATUSUPDATE", " ");

						hD.remove("LOTSTATUSUPDATE");
						hD.put("LOTSTATUSUPDATE", " ");

						hD.remove("LINE_STATUS");
						hD.put("LINE_STATUS", "NO");

						errordata.addElement(hD);
					} else {
						one_success = true;
						hD.remove("LINE_STATUS");
						hD.put("LINE_STATUS", "YES");

						hD.remove("CERTUPDATE");
						hD.put("CERTUPDATE", " ");

						hD.remove("EXPIREDATEUPDATE");
						hD.put("EXPIREDATEUPDATE", " ");

						hD.remove("DOSTATUSUPDATE");
						hD.put("DOSTATUSUPDATE", " ");

						hD.remove("LOTSTATUSUPDATE");
						hD.put("LOTSTATUSUPDATE", " ");

						errordata.addElement(hD);

						if (personnelBean != null
								&& FirstRow
								&& !StringHandler.isBlankString((String) hD
										.get("RECEIVING_STATUS"))
								&& ((("available").equalsIgnoreCase((String) hD
										.get("LOT_STATUS")) && !("available")
										.equalsIgnoreCase((String) hD
												.get("ORIG_LOT_STATUS"))) || (("Client Review")
										.equalsIgnoreCase((String) hD
												.get("LOT_STATUS")) && !("Client Review")
										.equalsIgnoreCase((String) hD
												.get("ORIG_LOT_STATUS"))))) {
							ReceivingQcCheckListProcess receivingQcCheckListProcess = new ReceivingQcCheckListProcess(
									db.getClient().toUpperCase());
							ReceivingInputBean receivingInputBean = new ReceivingInputBean();

							receivingInputBean.setSourceHub((String) hD
									.get("HUB"));
							receivingInputBean.setSort("Bin");
							receivingInputBean.setSourceHubName((String) hD
									.get("HUB_NAME"));
							receivingInputBean.setOpsEntityId((String) hD
									.get("OPS_ENTITY_ID"));
							receivingInputBean.setSearch((String) hD
									.get("RECEIPT_ID"));

							LogisticsQVRThread logisticsQVRThread = new LogisticsQVRThread(
									receivingQcCheckListProcess,
									receivingInputBean, personnelBean);
							logisticsQVRThread.start();
						}
					}
				} else {
					errordata.addElement(hD);
				}
				if (!Samepoline) {
					callreccomp = false;
				}
			} // end of for

			if (linecheckcount == 1) {
				result = true;
			}
			if (true == one_success) {
				result = true;
			}
		} catch (Exception e) {
			result = false;
			invlog.error(e.getMessage(), e);
			out.println(radian.web.HTMLHelpObj.printError("title.inv",
					intcmIsApplication, res));
		}

		updateresult.put("RESULT", new Boolean(result));
		updateresult.put("ERRORDATA", errordata);

		return updateresult;
	}

	private void buildHeader(Vector data, String hub_branch_id,
			String search_text, String sortby, String buildlabels,
			Collection hubInventoryGroupOvBeanCollection, String selinvengrp,
			String showneedingprint, String searchLike, String searchfor,
			String showissuedreceipts, HttpSession session,
			String printKitLabels, Vector all_status_set, Vector lotStatus,
			PrintWriter out, String lotArray, String transactiondate,
			String receivedbyid, String receivedbyname, String CompanyID) {
		try {
			out.println(radian.web.HTMLHelpObj.printHTMLHeader(
					"label.logistics", "", intcmIsApplication, res));
			out.println("<SCRIPT SRC=\"/clientscripts/invengrpdropdown.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
			out.println("<SCRIPT SRC=\"/js/hub/logistics.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
			out.println("<SCRIPT SRC=\"/js/hub/bindata.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
			out.println("<SCRIPT SRC=\"/js/common/formchek.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
			out.println("<SCRIPT SRC=\"/js/common/commonutil.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
			out.println("<SCRIPT SRC=\"/js/calendar/newcalendar.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
			out.println("<SCRIPT SRC=\"/js/calendar/AnchorPosition.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
			out.println("<SCRIPT SRC=\"/js/calendar/PopupWindow.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
			out.println("<SCRIPT SRC=\"/js/jquery/jquery-1.6.4.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
			out.println("<SCRIPT SRC=\"/js/jquery/autocomplete.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
			out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"/css/autocomplete.css\" />\n");
			out.println("</HEAD>  \n");

			if ("GENERATE_LARGE_LABELS".equalsIgnoreCase(buildlabels)) {
				out.println("<BODY onLoad=\"dolargelabelPopup()\">\n");
			} else if ("GENERATE_LABELS".equalsIgnoreCase(buildlabels)) {
				out.println("<BODY onLoad=\"doInvenPopup()\">\n");
			} else {
				out.println("<BODY>\n");
			}

			String hubname = radian.web.HTMLHelpObj.getHubName(
					hubInventoryGroupOvBeanCollection, hub_branch_id);
			PersonnelBean personnelBean = session.getAttribute("personnelBean") == null ? null
					: (PersonnelBean) session.getAttribute("personnelBean");

			out.println("<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n");
			out.println("<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>"
					+ res.getString("msg.waitupdate")
					+ "</B></FONT></center>\n");
			out.println("</DIV>\n");
			out.println("<DIV ID=\"MAINPAGE\" STYLE=\"\">\n");

			out.println(radian.web.HTMLHelpObj.PrintTitleBar("<B>"
					+ res.getString("label.logistics") + "</B>\n"));
			if (hubInventoryGroupOvBeanCollection.size() < 1) {
				out.println(radian.web.HTMLHelpObj
						.printMessageinTable("<BR><BR>"
								+ res.getString("msg.noaccess")));
				return;
			}

			out.println("<SCRIPT LANGUAGE=\"JavaScript\" TYPE=\"text/javascript\">\n <!--\n");

			if (personnelBean.isFeatureReleased("LabTestForm", hubname, "HAAS")) {
				out.println("var showLabTestForm = true; \n");
			} else
				out.println("var showLabTestForm = false; \n");

			if (personnelBean.isFeatureReleased("PrintGHSLabels", hubname,
					"HAAS")) {
				out.println("var printGHSLabels = true; \n");
			} else
				out.println("var printGHSLabels = false; \n");

			out.println(radian.web.poHelpObj.getHubInventoryGroupJavaScript(
					hubInventoryGroupOvBeanCollection, true, false));
			out.println("var searchMyArr = new Array({value:'Like', text:'"
					+ res.getString("label.contains")
					+ "'},{value:'Equals', text: '"
					+ res.getString("label.equals") + "'});");
			Iterator hubInventoryGroupOvBeanCollectionItr = hubInventoryGroupOvBeanCollection.iterator();
			
			out.println("var autoPutHub = {");
			int hubInventoryGroupOvBeanCollCount = 0;
			while(hubInventoryGroupOvBeanCollectionItr.hasNext())
			{
				HubInventoryGroupOvBean hubInventoryGroupOvBean = (HubInventoryGroupOvBean)hubInventoryGroupOvBeanCollectionItr.next();
				if("Y".equalsIgnoreCase(hubInventoryGroupOvBean.getAutomatedPutaway()))
					out.println(hubInventoryGroupOvBean.getBranchPlant()+":true");
				else
					out.println(hubInventoryGroupOvBean.getBranchPlant()+":false");
				if(hubInventoryGroupOvBeanCollCount < hubInventoryGroupOvBeanCollection.size() - 1)
					out.println(",");
				++hubInventoryGroupOvBeanCollCount;
			}
			out.println("};// -->\n </SCRIPT>\n");

			out.println("<FORM method=\"POST\" ID=\"generateLabelsForm\" NAME=\"generateLabelsForm\" ACTION=\"/tcmIS/hub/printreceiptboxlabels.do\" target=\"_blank\">");
			out.println("<input type=\"hidden\" name=\"labelReceipts\" id=\"labelReceipts\">");
			out.println("<input type=\"hidden\" name=\"paperSize\" id=\"paperSize\">");
			out.println("<input type=\"hidden\" name=\"printerPath\" id=\"printerPath\">");
			out.println("<input type=\"hidden\" name=\"printLabelsNow\" id=\"printLabelsNow\" value=\"true\">");
			out.println("<input type=\"hidden\" name=\"personnelCompanyId\" id=\"personnelCompanyId\" VALUE=\""
					+ CompanyID + "\">\n");
			out.println("</FORM>");

			out.println("<FORM method=\"POST\" ID=\"receiving\" NAME=\"receiving\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\""
					+ Receiving_Servlet + "\">\n");
			out.println("<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"100%\" CLASS=\"moveup\">\n");

			out.println("<TR VALIGN=\"MIDDLE\">\n");

			// Hub
			out.println("<TD WIDTH=\"5%\" HEIGHT=\"35\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
			out.println("<B>" + res.getString("label.hub") + ":</B>&nbsp;\n");
			out.println("</TD>\n");
			out.println("<TD WIDTH=\"10%\">\n");
			out.println("<SELECT CLASS=\"HEADER\"  NAME=\"HubName\" id=\"HubName\" size=\"1\" OnChange=\"hubchanged(document.receiving.HubName)\">\n");
			if (hub_branch_id.trim().length() == 0) {
				hub_branch_id = radian.web.HTMLHelpObj
						.getFirstBranchPlant(hubInventoryGroupOvBeanCollection);
			}

			out.println("<option value=\"All\">" + res.getString("label.all")
					+ "</option>\n");
			out.println(radian.web.HTMLHelpObj.getHubDropDown(
					hubInventoryGroupOvBeanCollection, hub_branch_id));
			out.println("</SELECT>\n");
			out.println("</TD>\n");

			out.println("<TD WIDTH=\"5%\" HEIGHT=\"35\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
			out.println("<B>" + res.getString("label.search") + ":</B>&nbsp;\n");
			out.println("</TD>\n");

			out.println("<TD WIDTH=\"5%\" HEIGHT=\"35\" ALIGN=\"right\" CLASS=\"announce\">\n");
			out.println("<SELECT CLASS=\"HEADER\"  NAME=\"searchlike\" id=\"searchlike\" onchange=\"changeSearchTypeOptions(this.value)\" size=\"1\">\n");
			Hashtable searchthese = new Hashtable();
			searchthese.put(res.getString("label.po"), "RADIAN_PO");
			searchthese.put(res.getString("label.itemid"), "ITEM_ID");
			searchthese.put(res.getString("label.itemdesc"), "ITEM_DESC");
			searchthese.put(res.getString("label.bin"), "BIN");
			searchthese.put(res.getString("label.receiptid"), "RECEIPT_ID");
			searchthese.put(res.getString("label.mfglot"), "FULL_MFG_LOT");
			searchthese.put(res.getString("label.lotstatus"), "LOT_STATUS");
			searchthese.put(res.getString("label.origreceiptid"),
					"ORIGINAL_RECEIPT_ID");
			searchthese.put(res.getString("label.ownercompany"),
					"OWNER_COMPANY_ID");
			searchthese.put(res.getString("label.partnumber"), "CAT_PART_NO");
			searchthese.put(res.getString("label.customerpo"), "PO_NUMBER");
			searchthese.put(res.getString("label.deliveryticket"),
					"DELIVERY_TICKET");
			searchthese.put(res.getString("label.legacyreceiptid"),
					"CUSTOMER_RECEIPT_ID");

			out.println(radian.web.HTMLHelpObj.sorthashbyValueshowkey(
					searchthese.entrySet(), searchLike));
			out.println("</SELECT>\n");
			out.println("</TD>\n");

			out.println("<TD WIDTH=\"5%\" HEIGHT=\"35\" ALIGN=\"left\" CLASS=\"announce\">\n");
			out.println("<SELECT CLASS=\"HEADER\"  NAME=\"searchfor\" ID=\"searchfor\" size=\"1\">\n");
			Hashtable sortresult = new Hashtable();
			sortresult.put(res.getString("label.equals"), "Equals");
			if ("Like".equalsIgnoreCase(searchfor))
				sortresult.put(res.getString("label.contains"), "Like");
			out.println(radian.web.HTMLHelpObj.sorthashbyValueshowkey(
					sortresult.entrySet(), searchfor));
			out.println("</SELECT>\n");
			out.println("</TD>\n");

			out.println("<TD WIDTH=\"5%\" HEIGHT=\"35\" VALIGN=\"MIDDLE\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
			out.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" id=\"SearchField\" NAME=\"SearchField\" value=\""
					+ search_text + "\" size=\"20\">\n");
			out.println("</TD>\n");

			// Search
			out.println("<TD WIDTH=\"5%\" HEIGHT=\"35\" CLASS=\"announce\" ALIGN=\"LEFT\">\n");
			out.println("<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\""
					+ res.getString("label.containerlabels")
					+ "\" onclick= \"generatelabels(this)\" NAME=\"UpdateButton\" ID=\"UpdateButton\">&nbsp;\n");
			out.println("</TD>\n");

			// Add new Bin
			out.println("<TD WIDTH=\"5%\" HEIGHT=\"35\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
			out.println("<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\""
					+ res.getString("label.genAllLabels")
					+ "\" onclick= \"generatealllabels(this)\" NAME=\"genalleButton\" ID=\"genalleButton\">&nbsp;\n");
			out.println("</TD>\n");

			out.println("</TR>\n");

			out.println("<TR VALIGN=\"MIDDLE\">\n");
			// Inventory Group
			out.println("<TD WIDTH=\"5%\" HEIGHT=\"35\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
			out.println("<B>" + res.getString("label.inventorygroup")
					+ ":</B>&nbsp;\n");
			out.println("</TD>\n");
			out.println("<TD WIDTH=\"10%\">\n");

			out.println("<SELECT CLASS=\"HEADER\" ID=\"invengrp\" NAME=\"invengrp\" size=\"1\">\n");
			if (hub_branch_id.trim().equalsIgnoreCase("All")) {
				out.println("<option value=\"All\">"
						+ res.getString("label.all") + "</option>\n");
			}
			out.println(radian.web.HTMLHelpObj.getInventoryGroupDropDown(
					hubInventoryGroupOvBeanCollection, hub_branch_id,
					selinvengrp, false));


			out.println("</SELECT>\n");
			out.println("</TD>\n");

			// Only Receipts Needing Printing
			out.println("<TD WIDTH=\"5%\" HEIGHT=\"35\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
			out.println("<INPUT type=\"checkbox\" id=\"showneedingprint\" name=\"showneedingprint\" value=\"Yes\" "
					+ (showneedingprint.equalsIgnoreCase("Yes") ? "checked"
							: "") + ">\n");
			out.println("</TD>\n");
			out.println("<TD WIDTH=\"5%\" HEIGHT=\"35\" CLASS=\"announce\" ALIGN=\"LEFT\" COLSPAN=\"2\">\n");
			out.println(res.getString("label.showonlyreceipts") + "\n");
			out.println("</TD>\n");

			// Expires Within
			out.println("<TD WIDTH=\"5%\" HEIGHT=\"35\" ALIGN=\"RIGHT\"><B>"
					+ res.getString("label.expwithin") + ":</B>&nbsp;\n");
			out.println("<INPUT CLASS=\"HEADER\" type=\"text\" id=\"nodaysold\" name=\"nodaysold\" value=\""
					+ (numOldDays.length() < 1 ? "" : numOldDays)
					+ "\" SIZE=\"3\" MAXLENGTH=\"5\">\n");
			out.println("&nbsp;" + res.getString("label.days") + "</TD>\n");

			out.println("<TD WIDTH=\"5%\" VALIGN=\"MIDDLE\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
			if (!this.getupdateStatus()) {
				out.println("&nbsp;\n");
			} else {
				out.println("<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\""
						+ res.getString("pickingqc.receivinglabels")
						+ "\" onclick= \"printReceivingBoxLabels()\" NAME=\"UpdateButton\" ID=\"UpdateButton\">&nbsp;\n");
				out.println("<INPUT TYPE=\"hidden\" NAME=\"generateLargeLabels\" ID=\"generateLargeLabels\" VALUE=\"yes\">\n");

			}
			out.println("</TD>\n");
			out.println("<TD WIDTH=\"15%\" HEIGHT=\"35\" CLASS=\"announce\">\n");
			out.println("<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\""
					+ res.getString("label.documentlabels")
					+ "\" onclick= \"printDocumentLabels()\" NAME=\"documentlabelsButton\" ID=\"documentlabelsButton\">\n");
			out.println("</TD>\n");
			out.println("</TR>\n");

			out.println("<TR VALIGN=\"MIDDLE\">\n");
			out.println("<TD WIDTH=\"5%\" COLSPAN=\"1\"  VALIGN=\"MIDDLE\" ALIGN=\"RIGHT\" rowspan=\"2\" CLASS=\"announce\">\n");
			out.println("<B>" + res.getString("label.lotstatus") + ":</B>");
			out.println("</TD>\n");

			out.println("<TD WIDTH=\"10%\" COLSPAN=\"1\"  VALIGN=\"TOP\" ALIGN=\"LEFT\" rowspan=\"2\" CLASS=\"announce\">\n");
			out.println("<select CLASS=\"HEADER\" name=\"lotStatus\" id=\"lotStatus\" multiple size=\"5\">\n");
			if (lotArray.length() == 0) {
				out.println("<OPTION VALUE=\"\" selected>"
						+ res.getString("label.all") + "</OPTION>\n");
			} else {
				out.println("<OPTION VALUE=\"\">" + res.getString("label.all")
						+ "</OPTION>\n");
			}
			out.println(radian.web.HTMLHelpObj.getLogisticsMultipleDropDown(
					all_status_set, lotStatus));
			out.println("</select>\n");
			out.println("</TD>\n");

			// Include History
			out.println("<TD WIDTH=\"5%\" HEIGHT=\"35\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
			out.println("<INPUT type=\"checkbox\" name=\"showissuedreceipts\" id=\"showissuedreceipts\" value=\"Yes\" "
					+ (showissuedreceipts.equalsIgnoreCase("Yes") ? "checked"
							: "") + ">\n");
			out.println("</TD>\n");
			out.println("<TD WIDTH=\"5%\" HEIGHT=\"35\" CLASS=\"announce\" ALIGN=\"LEFT\" COLSPAN=\"2\">\n");
			out.println(res.getString("label.includehistory") + "\n");
			out.println("</TD>\n");

			out.println("<TD WIDTH=\"5%\" VALIGN=\"MIDDLE\" ALIGN=\"RIGHT\" CLASS=\"announce\">&nbsp;\n");
			out.println("<INPUT type=\"checkbox\" name=\"printKitLabels\" id=\"printKitLabels\" value=\"Yes\" "
					+ (printKitLabels.equalsIgnoreCase("Yes") ? "checked" : "")
					+ ">\n");
			out.println("&nbsp;" + res.getString("label.skipkitcaseqtylabels")
					+ "\n");
			out.println("</TD>\n");

			// Generate Large Labels

			out.println("<TD WIDTH=\"1%\" HEIGHT=\"35\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
			out.println("<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\""
					+ res.getString("label.receiptdetaillables")
					+ "\" onclick= \"printReceiptDetailLabels()\" NAME=\"receiptdetaillabelsButton\" ID=\"receiptdetaillabelsButton\">\n");
			out.println("</TD>\n");
			out.println("<TD WIDTH=\"5%\" HEIGHT=\"35\" COLSPAN=\"2\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
			out.println("<INPUT TYPE=\"radio\" NAME=\"paperSize\" ID=\"paperSize31\" ONCLICK=\"assignPaper('31')\" value=\"31\" "
					+ ("31".equalsIgnoreCase(paper_size) ? "checked" : "")
					+ ">3\"/1\"&nbsp;\n");
			out.println("<INPUT TYPE=\"radio\" NAME=\"paperSize\" ID=\"paperSize038\" ONCLICK=\"assignPaper('038')\" value=\"038\" "
					+ ("038".equalsIgnoreCase(paper_size) ? "checked" : "")
					+ ">4-3/16\" / 3/8\"\n");
			out.println("&nbsp;\n");
			out.println("</TD>\n");

			out.println("</TR>\n");

			out.println("<TR VALIGN=\"MIDDLE\">\n");
			// Sort
			out.println("<TD WIDTH=\"5%\" HEIGHT=\"35\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
			out.println("<B>" + res.getString("label.sortby") + ":</B>&nbsp;\n");
			out.println("</TD>\n");
			out.println("<TD WIDTH=\"10%\" COLSPAN=\"2\" HEIGHT=\"35\">\n");
			out.println("<SELECT CLASS=\"HEADER\" ID=\"SortBy\" NAME=\"SortBy\" size=\"1\">\n");
			/*
			 * Have to sort by receipt ID always to keep the kit componenets
			 * together.
			 */
			Hashtable sortThese = new Hashtable();
			sortThese.put(res.getString("label.itemStatusReceiptID"),
					"item_id,lot_status,receipt_id");
			sortThese.put(res.getString("label.binRceId"), "bin,receipt_id");
			sortThese.put(res.getString("label.ownerBinRecp"),
					"owner_company_id,bin,receipt_id");
			sortThese.put(res.getString("label.receiptid"), "receipt_id desc");
			sortThese.put(res.getString("label.ageStatusItem"),
					"LOT_STATUS_AGE,LOT_STATUS,ITEM_ID,receipt_id");
			sortThese.put(res.getString("label.dor"),
					"DATE_OF_RECEIPT,receipt_id desc");
			sortThese.put("" + res.getString("label.inventorygroup") + "/"
					+ res.getString("label.item") + "",
					"inventory_group,item_id,receipt_id desc");
			sortThese.put("" + res.getString("label.inventorygroup") + "/"
					+ res.getString("label.expiredate") + "",
					"inventory_group,RECEIPT_EXPIRE_DATE,receipt_id desc");
			sortThese.put("" + res.getString("label.expiredate") + "",
					"RECEIPT_EXPIRE_DATE,receipt_id desc");
			out.println(radian.web.HTMLHelpObj.sorthashbyValueshowkey(
					sortThese.entrySet(), sortby));
			out.println("</SELECT>\n");
			out.println("</TD>\n");

			// Transaction Date
			out.println("<TD WIDTH=\"5%\" HEIGHT=\"35\" ALIGN=\"LEFT\"><B>"
					+ res.getString("label.transactiondate") + ":</B>&nbsp;\n");
			out.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"transactiondate\" ID=\"transactiondate\" value=\""
					+ transactiondate
					+ "\" size=\"10\" readonly><a href=\"javascript: void(0);\" ID=\"transactiondatelink\" onClick=\"return getCalendar(document.receiving.transactiondate);\">&diams;</a>\n");
			out.println("</TD>\n");

			out.println("<TD WIDTH=\"3%\" COLSPAN=\"2\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
			out.println("<B>" + res.getString("label.receivedby") + ":</B>\n");
			out.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"receivedbyname\" ID=\"receivedbyname\" value=\""
					+ receivedbyname
					+ "\"  onfocus=\"completeReceivedBy(this.form)\" size=\"14\">\n");
			out.println("<input type=\"hidden\" name=\"receivedbyid\" id=\"receivedbyid\" value=\""
					+ receivedbyid + "\">\n");
			out.println("<BUTTON type=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"selectedreceived\" name=\"selectedreceived\" value=\"...\" OnClick=\"lookupPerson(this.form)\"><IMG src=\"/images/search_small.gif\" alt=\"PO\"></BUTTON>\n");
			out.println("<INPUT type=\"checkbox\" name=\"activeonly\" id=\"activeonly\" value=\"Yes\"  checked ><B>"
					+ res.getString("label.activeOnly") + "</B>&nbsp;\n");
			out.println("</TD>\n");
			out.println("</TR>\n");

			out.println("<TR>\n");
			out.println("<TD HEIGHT=\"35\" COLSPAN = \"8\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
			out.println("<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\""
					+ res.getString("label.search")
					+ "\" onclick= \"return search(this)\" NAME=\"SearchButton\" id=\"SearchButton\">\n");
			out.println("&nbsp;&nbsp;\n");
			out.println("<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\""
					+ res.getString("label.createExcel")
					+ "\" onclick= \"generatexls(this)\" NAME=\"CreateExl\" id=\"CreateExl\">\n");
			if (this.getupdateStatus()) {
				out.println("&nbsp;&nbsp;<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\""
						+ res.getString("label.update")
						+ "\" onclick= \"return update(this)\" NAME=\"UpdateButton\" ID=\"UpdateButton\">\n");
			} else {
				out.println("&nbsp;\n");
			}

			if (personnelBean.getPermissionBean().hasInventoryGroupPermission(
					"addNewBin", null, hubname, null)) {
				
				boolean isAutoPutHub = radian.web.HTMLHelpObj.isAutomatedPutawayHub(hubInventoryGroupOvBeanCollection,hub_branch_id);
				
				out.println("&nbsp;&nbsp<INPUT TYPE=\"BUTTON\" ID=\"genbutton1\" VALUE=\""
						+ res.getString("receiving.button.newbin")
						+ "\" NAME=\"SearchButton\" onclick= \"javascript:addnewBin()\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\"");
						if(isAutoPutHub)
							out.println(" style=\"display:none;\"");
						out.println(">\n");
			}
			else {
				out.println("&nbsp;\n");
			}

			String checkInventoryGroup = "";
			if (!selinvengrp.equalsIgnoreCase("All")) {
				checkInventoryGroup = selinvengrp;
			}
			if (!personnelBean.getPermissionBean().hasInventoryGroupPermission(
					"Inventory", checkInventoryGroup, hubname, null)) {
				out.println("&nbsp;\n");
			} else {
				out.println("&nbsp;&nbsp<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\""
						+ res.getString("label.changeitem")
						+ "\" onclick= \"changeMlItem()\" ID=\"changeMlItemButton\" NAME=\"changeMlItemButton\">\n");
			}
			out.println("</TD>\n");
			out.println("</TR>\n");

			out.println("</TABLE>\n");

			int total = 0;
			if (null == data) {
				total = 0;
			} else {
				Hashtable summary = new Hashtable();
				summary = (Hashtable) data.elementAt(0);
				total = ((Integer) (summary.get("TOTAL_NUMBER"))).intValue();
			}

			out.println("<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\" CLASS=\"moveup\">\n");
			out.println("<tr><td>");
			out.println("<input type=\"hidden\" name=\"Total_number\" id=\"Total_number\" value=\""
					+ total + "\">\n");
			out.println("<INPUT TYPE=\"hidden\" NAME=\"UserAction\" ID=\"UserAction\" VALUE=\"NEW\">\n");
			out.println("<INPUT TYPE=\"hidden\" NAME=\"SubUserAction\" ID=\"SubUserAction\" VALUE=\" \">\n");
			out.println("<INPUT TYPE=\"hidden\" NAME=\"DuplLineNumber\" ID=\"DuplLineNumber\" VALUE=\" \">\n");
			out.println("<INPUT TYPE=\"hidden\" NAME=\"AddBin_Item_Id\" ID=\"AddBin_Item_Id\" VALUE=\" \">\n");
			out.println("<INPUT TYPE=\"hidden\" NAME=\"AddBin_Bin_Id\" ID=\"AddBin_Bin_Id\" VALUE=\" \">\n");
			out.println("<INPUT TYPE=\"hidden\" NAME=\"AddBin_Line_No\" ID=\"AddBin_Line_No\" VALUE=\" \">\n");
			out.println("<INPUT TYPE=\"hidden\" NAME=\"HubRef\" ID=\"HubRef\" VALUE=\""
					+ hub_branch_id + "\">\n");
			out.println("<INPUT TYPE=\"hidden\" NAME=\"Paper\" id=\"Paper\" VALUE=\""
					+ paper_size + "\">\n");
			out.println("<INPUT TYPE=\"hidden\" NAME=\"thedecidingRandomNumber\" ID=\"thedecidingRandomNumber\" VALUE=\""
					+ thedecidingRandomNumber + "\">\n");

			out.println("<input type=\"hidden\" name=\"selectedItem\" id=\"selectedItem\" value=\"\">\n");
			out.println("<input type=\"hidden\" name=\"selectedInventoryGroup\" id=\"selectedInventoryGroup\" value=\"\">\n");
			out.println("<input type=\"hidden\" name=\"selectedCatalogId\" id=\"selectedCatalogId\" value=\"\">\n");
			out.println("<input type=\"hidden\" name=\"selectedCatPartNo\" id=\"selectedCatPartNo\" value=\"\">\n");
			out.println("<input type=\"hidden\" name=\"selectedCatalogCompanyId\" id=\"selectedCatalogCompanyId\" value=\"\">\n");

			out.println("<INPUT TYPE=\"hidden\" NAME=\"personnelId\" ID=\"personnelId\" VALUE=\""
					+ personnelBean.getPersonnelId() + "\">\n");
			out.println("<INPUT TYPE=\"hidden\" NAME=\"printerLocation\" ID=\"printerLocation\" VALUE=\""
					+ personnelBean.getPrinterLocation() + "\">\n");
			out.println("</TD></tr>");
			out.println("</table>\n");
		} catch (Exception e) {
			invlog.error(e.getMessage(), e);
			out.println(radian.web.HTMLHelpObj.printError("label.logistics",
					intcmIsApplication, res));
		}

		return;
	}

	// end of method

	private StringBuffer buildXlsDetails(String branch_plant1,
			String search_String, String expireswithin, String personnelID,
			Vector data, ResourceLibrary resource) {
		String url = "";
		String file = "";

		Random rand = new Random();
		int tmpReq = rand.nextInt();
		Integer tmpReqNum = new Integer(tmpReq);

		String wwwHome = radian.web.tcmisResourceLoader.gethosturl();
		String writefilepath = radian.web.tcmisResourceLoader
				.getsaveltempreportpath();

		file = writefilepath + personnelID + tmpReqNum.toString()
				+ "logistics.xls";
		url = wwwHome + "reports/temp/" + personnelID + tmpReqNum.toString()
				+ "logistics.xls";

		try {
			OutputStream newPw = (new FileOutputStream(file));
			ExcelHandler pw = new ExcelHandler(resource);
			Hashtable summary = new Hashtable();
			summary = (Hashtable) data.elementAt(0);
			int total = ((Integer) (summary.get("TOTAL_NUMBER"))).intValue();

			if (total == 0) {
				StringBuffer Msg = new StringBuffer();
				Msg.append(res.getString("label.norecordfound"));
				return Msg;
			}

			pw.addRow();
			pw.addCellBold(res.getString("label.branchplant"));
			pw.addCell(branch_plant1);

			pw.addRow();
			pw.addCellBold(res.getString("label.searchstring"));
			pw.addCell(search_String);

			pw.addRow();
			pw.addCellBold(res.getString("inventory.label.expireswithin"));
			pw.addCell(expireswithin);

			pw.addRow();
			pw.addCellBold(res.getString("label.item"));
			pw.addCellBold(res.getString("label.description"));
			pw.addCellBold(res.getString("label.status"));
			pw.addCellBold(res.getString("label.lotstatusage"));
			pw.addCellBold(res.getString("label.expdate(mm/dd/yyyy)"));
			pw.addCellBold(res.getString("label.minexpdate"));
			pw.addCellBold(res.getString("label.bin"));
			pw.addCellBold(res.getString("label.lot"));
			pw.addCellBold(res.getString("label.receiptspecs"));
			pw.addCellBold(res.getString("label.receiptid"));
			pw.addCellBold(res.getString("label.originalreceiptid"));
			pw.addCellBold(res.getString("label.currency"));
			pw.addCellBold(res.getString("label.unitcost"));
			pw.addCellBold(res.getString("label.recertNo"));
			pw.addCellBold(res.getString("label.quantity"));
			pw.addCellBold(res.getString("label.qtyreceived"));
			pw.addCellBold(res.getString("label.pendingadjustment"));
			pw.addCellBold(res.getString("label.pkg"));
			pw.addCellBold(res.getString("label.lastprintdate"));
			pw.addCellBold(res.getString("label.notes"));
			pw.addCellBold(res.getString("label.inventorygroup"));
			pw.addCellBold(res.getString("label.ownercompanyid"));
			pw.addCellBold(res.getString("label.po"));
			pw.addCellBold(res.getString("label.poline"));
			pw.addCellBold(res.getString("label.customerpo"));
			pw.addCellBold(res.getString("label.dor"));
			pw.addCellBold(res.getString("label.dom"));
			pw.addCellBold(res.getString("label.dateofrepackaging"));
			pw.addCellBold(res
					.getString("receivedreceipts.label.manufacturerdos"));
			pw.addCellBold(res.getString("label.partnumber"));
			pw.addCellBold(res.getString("receiving.label.deliveryticket"));
			pw.addCellBold(res.getString("label.dot"));
			pw.addCellBold(res.getString("label.legacyreceiptid"));
			pw.addCellBold(res.getString("label.storagetemp"));
			pw.addCellBold(res.getString("label.internalreceiptnotes"));
			pw.addCellBold(res.getString("label.labelexpiredate"));
			pw.addCellBold(res.getString("label.cagecode"));
			pw.addCellBold(res.getString("label.countryoforigin"));

			// pw.addCellBold(res.getString("label.qastatement"));

			int i = 0; // used for detail lines
			for (int loop = 0; loop < total; loop++) {
				i++;

				Hashtable hD = new Hashtable();
				hD = (Hashtable) data.elementAt(i);

				String Item_desc = (hD.get("ITEM_DESC") == null ? "" : hD.get(
						"ITEM_DESC").toString());
				Item_desc = HelpObjs.findReplace(Item_desc, ",", " ");
				Item_desc = HelpObjs.findReplace(Item_desc, "<BR>", " ");

				pw.addRow();
				pw.addCell(hD.get("ITEM_ID").toString());
				pw.addCell(Item_desc);
				pw.addCell(hD.get("LOT_STATUS").toString());
				pw.addCell(hD.get("LOT_STATUS_AGE").toString());
				pw.addCell(hD.get("EXPIRE_DATE").toString());
				pw.addCell(hD.get("MINIMUM_EXPIRE_DATE").toString());
				pw.addCell(hD.get("BIN").toString());
				pw.addCell(hD.get("MFG_LOT").toString());
				pw.addCell(hD.get("SPECS").toString());
				pw.addCell(hD.get("RECEIPT_ID").toString());
				pw.addCell(hD.get("ORIGINAL_RECEIPT_ID").toString());
				pw.addCell(hD.get("CURRENCY_ID").toString());
				pw.addCell(hD.get("UNIT_COST").toString());
				pw.addCell(hD.get("RECERT_NUMBER").toString());
				pw.addCell(hD.get("QUANTITY").toString());
				pw.addCell(hD.get("QUANTITY_RECEIVED").toString());
				String netPendingAdj = hD.get("NET_PENDING_ADJ") == null ? ""
						: hD.get("NET_PENDING_ADJ").toString();
				BigDecimal netPendingAdjValue = null;
				if (netPendingAdj.length() > 0) {
					netPendingAdjValue = new BigDecimal(netPendingAdj);
					netPendingAdjValue = netPendingAdjValue
							.multiply(new BigDecimal("-1"));
				}
				pw.addCell("" + netPendingAdjValue + "");
				pw.addCell(hD.get("RECEIPT_PACKAGING").toString());
				pw.addCell(hD.get("LAST_PRINT_DATE").toString());
				String reciptnots = (hD.get("NOTES") == null ? "&nbsp;" : hD
						.get("NOTES").toString());
				if ("<BR>".equalsIgnoreCase(reciptnots)) {
					reciptnots = "";
				}
				pw.addCell(reciptnots);
				pw.addCell(hD.get("INVENTORY_GROUP").toString());
				pw.addCell(hD.get("OWNER_COMPANY_ID").toString());
				pw.addCell(hD.get("RADIAN_PO").toString());
				pw.addCell(hD.get("PO_LINE").toString());
				pw.addCell(hD.get("PO_NUMBER").toString());
				pw.addCell(hD.get("DATE_OF_RECEIPT").toString());
				pw.addCell(hD.get("DATE_OF_MANUFACTURE").toString());
				pw.addCell(hD.get("DATE_OF_REPACKAGING").toString());
				pw.addCell(hD.get("DATE_OF_SHIPMENT").toString());
				pw.addCell(hD.get("CLIENT_PART_NOS").toString());
				pw.addCell(hD.get("DELIVERY_TICKET").toString());
				pw.addCell(hD.get("DOT").toString());
				pw.addCell(hD.get("CUSTOMER_RECEIPT_ID").toString());
				pw.addCell(hD.get("LABEL_STORAGE_TEMP").toString());
				pw.addCell(hD.get("INTERNAL_RECEIPT_NOTES").toString());
				pw.addCell(hD.get("MFG_LABEL_EXPIRE_DATE").toString());
				pw.addCell(hD.get("SUPPLIER_CAGE_CODE").toString());
				pw.addCell(hD.get("COUNTRY_OF_ORIGIN_NAME").toString());
			}
			pw.write(newPw);
			newPw.close();
		} catch (Exception e) {
			invlog.error(e.getMessage(), e);
			return radian.web.HTMLHelpObj.printHTMLError(res);
		}

		StringBuffer MsgSB = new StringBuffer();

		if (url.length() > 0) {
			MsgSB.append("<HTML><HEAD>\n");
			MsgSB.append("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; url=" + url
					+ "\">\n");
			MsgSB.append("<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n");
			MsgSB.append("<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n");
			MsgSB.append("<TITLE>"
					+ res.getString("label.containerlabelgenerator")
					+ "</TITLE>\n");
			MsgSB.append("</HEAD>  \n");
			MsgSB.append("<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">  \n");
			MsgSB.append("<CENTER><img src=\"/images/tcmintro.gif\"  border=1 align=\"middle\"><P></P><BR>\n");
			MsgSB.append("<FONT FACE=\"Arial\" size=\"5\" color=\"#000080\"><b>"
					+ res.getString("label.downloadexlfile")
					+ "</b></font><P></P><BR>\n");
			MsgSB.append("<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b></b></font><P></P><BR>\n");
			MsgSB.append("<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b>"
					+ res.getString("label.pleasewait")
					+ "</b></font><P></P><BR>\n");
			MsgSB.append("</CENTER>\n");
			MsgSB.append("</BODY></HTML>    \n");
		} else {
			MsgSB.append("<HTML><HEAD>\n");
			MsgSB.append("<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n");
			MsgSB.append("<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n");
			MsgSB.append("<TITLE>"
					+ res.getString("label.containerlabelgenerator")
					+ "</TITLE>\n");
			MsgSB.append("</HEAD>  \n");
			MsgSB.append("<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">  \n");
			MsgSB.append("<CENTER><img src=\"/images/tcmintro.gif\"  border=1 align=\"middle\"><P></P><BR>\n");
			MsgSB.append("<FONT FACE=\"Arial\" size=\"5\" color=\"#000080\"><b>"
					+ res.getString("label.anerroroccured")
					+ " </b></font><P></P><BR>\n");
			MsgSB.append("<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b></b></font><P></P><BR>\n");
			MsgSB.append("<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b>"
					+ res.getString("msg.pleasetryagain")
					+ "</b></font><P></P><BR>\n");
			MsgSB.append("</CENTER>\n");
			MsgSB.append("</BODY></HTML>    \n");
		}

		return MsgSB;
	}

	private void buildDetails(Vector data, Hashtable all_bin_set,
			Vector all_status_set, String Add_Item_id, String SubuserAction,
			Vector allowforexpdate, Vector allowforlotstatus,
			Vector allowedigforinv, Vector nonpickableinv,PrintWriter out) {
		try {
			Vector<Hashtable<String,String>> vvCountries = hubObj.getVVCountries();
			Hashtable summary = new Hashtable();
			summary = (Hashtable) data.elementAt(0);
			int total = ((Integer) (summary.get("TOTAL_NUMBER"))).intValue();

			out.println("<SCRIPT LANGUAGE=\"JavaScript\" TYPE=\"text/javascript\">\n <!--\n");
			out.println("var lotStatus = new Array();\n");
			for (int i = 0; i < all_status_set.size(); i++) {
				Hashtable data1 = (Hashtable) all_status_set.elementAt(i);
				Enumeration E;
				for (E = data1.keys(); E.hasMoreElements();) {
					String key = (String) E.nextElement();
					String keyvalue = data1.get(key).toString();

					if ("Y".equalsIgnoreCase(keyvalue)) {
						out.println("lotStatus[\"" + key + "\"] = new Array(\""
								+ key + "\");\n");
					}
				}
			}

			out.println("with ( milonic=new menuname(\"printFormMenu\") ) {\n");
			out.println("top=\"offset=2\";\n");
			out.println("style=submenuStyle;\n");
			out.println("itemheight=17;\n");
			out.println("// style = contextStyle;\n");
			out.println("// margin=3;\n");
			out.println("if(showLabTestForm)\n");
			out.println("aI( \"text=Print Lab Test Form;url=javascript:printLabTestForm();\" );\n");
			out.println("if(printGHSLabels)\n");
			out.println("aI( \"text=Print GHS Labels;url=javascript:printGHSLabel();\" );\n");
			out.println("aI( \"text=Print Right to Know Labels;url=javascript:printRTKLabel();\" );\n");
			out.println("}\n");

			out.println("drawMenus();\n");

			out.println("toggleContextMenu(\"printFormMenu\");\n");

			out.println("// -->\n </SCRIPT>\n");

			out.println("<BR><FONT SIZE=\"3\" FACE=\"Arial\" COLOR=\"#fc0303\"><B><A HREF=\"javascript:showlogisticspagelegend()\" STYLE=\"color:#0000ff;cursor:hand\">Legend</A></B></FONT><BR>\n");
			out.println("<TABLE  BORDER=\"0\" BGCOLOR=\"#a2a2a2\" CELLSPACING=1 CELLPADDING=2 WIDTH=\"100%\" CLASS=\"moveup\">\n");
			int AddBinLineNum1 = getAddBinLineNum();

			int lineadded = 0;
			int lastItemNum = 1;
			int ItemIdCount = 0;
			String Color = "CLASS=\"white";
			String invcolor = "CLASS=\"INVISIBLEHEADWHITE";

			int i = 0; // used for detail lines
			for (int loop = 0; loop < total; loop++) {
				i++;
				boolean createHdr = false;

				if (lineadded % 10 == 0 && lastItemNum == 1) {
					createHdr = true;
				}

				if (createHdr) {
					out.println("<tr align=\"center\">\n");
					out.println("<TH width=\"2%\"  height=\"38\">"
							+ res.getString("label.ok") + "</TH>\n");
					out.println("<TH width=\"5%\"  height=\"38\">"
							+ res.getString("label.item(inventorygroup)")
							+ "</TH>\n");
					out.println("<TH width=\"20%\"  height=\"38\">"
							+ res.getString("label.description") + "</TH>\n");
					out.println("<TH width=\"5%\"  height=\"38\">"
							+ res.getString("label.status") + "<br>("
							+ res.getString("label.rootcause") + ")</TH>\n");
					out.println("<TH width=\"5%\"  height=\"38\">"
							+ res.getString("label.lotstatusage") + "</TH>\n");
					out.println("<TH width=\"5%\"  height=\"38\">"
							+ res.getString("label.expdate(mm/dd/yyyy)")
							+ "</TH>\n");
					out.println("<TH width=\"5%\"  height=\"38\">"
							+ res.getString("label.minexpdate") + "</TH>\n");
					out.println("<TH width=\"4%\" COLSPAN =\"3\" height=\"38\">"
							+ res.getString("label.bin") + "</TH>\n");
					out.println("<TH width=\"5%\"  height=\"38\">"
							+ res.getString("label.lot") + "</TH>\n");
					out.println("<TH width=\"5%\"  height=\"38\">"
							+ res.getString("label.receiptspecs") + "</TH>\n");
					out.println("<TH width=\"5%\"  height=\"38\">"
							+ res.getString("label.recidorigid(owner)utcost")
							+ "</TH>\n");
					out.println("<TH width=\"5%\"  height=\"38\">"
							+ res.getString("label.customerpo") + "</TH>\n");
					out.println("<TH width=\"5%\"  height=\"38\">"
							+ res.getString("receivedreceipts.label.dor")
							+ "</TH>\n");
					out.println("<TH width=\"2%\"  height=\"38\">"
							+ res.getString("label.recertNo") + "</TH>\n");
					out.println("<TH width=\"5%\"  height=\"38\">"
							+ res.getString("label.quantity(recvqty)")
							+ "</TH>\n");
					out.println("<TH width=\"5%\"  height=\"38\">"
							+ res.getString("label.pendingadjustment")
							+ "</TH>\n");
					out.println("<TH width=\"5%\"  height=\"38\">"
							+ res.getString("label.pkg") + "</TH>\n");
					out.println("<TH width=\"2%\"  height=\"38\">"
							+ res.getString("label.Nolabels") + "</TH>\n");
					out.println("<TH width=\"3%\"  height=\"38\">"
							+ res.getString("label.lastprintdate") + "</TH>\n");
					out.println("<TH width=\"5%\"  height=\"38\">"
							+ res.getString("label.storagetemp") + "</TH>\n");
					out.println("<TH width=\"5%\"  height=\"38\">"
							+ res.getString("label.manufacturerdateofshipment(dos)")
							+ "</TH>\n");
					out.println("<TH width=\"5%\"  height=\"38\">"
							+ res.getString("label.dateofmanufacturer(dom)")
							+ "</TH>\n");
					out.println("<TH width=\"5%\"  height=\"38\">"
							+ res.getString("label.dateofrepackaging")
							+ "</TH>\n");
					out.println("<TH width=\"5%\"  height=\"38\">"
							+ res.getString("label.notes") + "</TH>\n");
					out.println("<TH width=\"5%\"  height=\"38\">"
							+ res.getString("label.deliveryticket") + "</TH>\n");
					out.println("<TH width=\"5%\"  height=\"38\">"
							+ res.getString("label.unitlabeledper129p")
							+ "</TH>\n");
					out.println("<TH width=\"5%\"  height=\"38\">"
							+ res.getString("label.legacyreceiptid")
							+ "</TH>\n");
					out.println("<TH width=\"5%\"  height=\"38\">"
							+ res.getString("label.haas") + "&nbsp;"
							+ res.getString("label.poline") + "</TH>\n");
					out.println("<TH width=\"5%\"  height=\"38\">"
							+ res.getString("label.addqualitynote") + "</TH>\n");
					out.println("<TH width=\"5%\"  height=\"38\">"
							+ res.getString("label.internalreceiptnotes")
							+ "</TH>\n");
					out.println("<TH width=\"5%\"  height=\"38\">"
							+ res.getString("label.labelexpiredate")
							+ "</TH>\n");
					out.println("<TH width=\"5%\"  height=\"38\">"
							+ res.getString("label.cagecode") + "</TH>\n");
					out.println("<TH width=\"5%\"  height=\"38\">"
							+ res.getString("label.countryoforigin") + "</TH>\n");
					out.println("</tr>\n");
				}

				Hashtable hD = new Hashtable();
				hD = (Hashtable) data.elementAt(i);

				String Next_rec = "";
				if (!(i == total)) {
					Hashtable hDNext = new Hashtable();
					hDNext = (Hashtable) data.elementAt(i + 1);
					Next_rec = hDNext.get("RECEIPT_ID") == null ? "&nbsp;"
							: hDNext.get("RECEIPT_ID").toString();
				} else {
					Next_rec = " ";
				}

				// get main information
				String Item_id = (hD.get("ITEM_ID") == null ? "&nbsp;" : hD
						.get("ITEM_ID").toString());
				String Item_desc = (hD.get("ITEM_DESC") == null ? "&nbsp;" : hD
						.get("ITEM_DESC").toString());
				String Qty = (hD.get("QUANTITY") == null ? "" : hD.get(
						"QUANTITY").toString());
				String Mfg_lot = (hD.get("MFG_LOT") == null ? "&nbsp;" : hD
						.get("MFG_LOT").toString());
				String status = (hD.get("LOT_STATUS") == null ? "&nbsp;" : hD
						.get("LOT_STATUS").toString());
				String receipt_id = (hD.get("RECEIPT_ID") == null ? "&nbsp;"
						: hD.get("RECEIPT_ID").toString());
				String Line_Check = (hD.get("USERCHK") == null ? "&nbsp;" : hD
						.get("USERCHK").toString());
				String nooflabels = (hD.get("NO_OF_LABELS") == null ? "&nbsp;"
						: hD.get("NO_OF_LABELS").toString());
				String Sel_bin = (hD.get("BIN_NAME") == null ? "&nbsp;" : hD
						.get("BIN_NAME").toString());
				String recert = (hD.get("RECERT_NUMBER") == null ? "" : hD.get(
						"RECERT_NUMBER").toString());
				String lastprintdate = (hD.get("LAST_PRINT_DATE") == null ? "&nbsp;"
						: hD.get("LAST_PRINT_DATE").toString());
				String Expire_date = hD.get("EXPIRE_DATE").toString();
				if (Line_Check.equalsIgnoreCase("yes")) {
					checkednon = "checked";
				} else {
					checkednon = "";
				}
				String LINE_STATUS = BothHelpObjs.makeBlankFromNull(hD.get(
						"LINE_STATUS").toString());
				String reciptnots = (hD.get("NOTES") == null ? "&nbsp;" : hD
						.get("NOTES").toString());
				if ("<BR>".equalsIgnoreCase(reciptnots)) {
					reciptnots = "";
				}
				String internalReceiptNotes = (hD.get("INTERNAL_RECEIPT_NOTES") == null ? "&nbsp;"
						: hD.get("INTERNAL_RECEIPT_NOTES").toString());
				String lotStatusAge = (hD.get("LOT_STATUS_AGE") == null ? "&nbsp;"
						: hD.get("LOT_STATUS_AGE").toString());

				String rootCause = BothHelpObjs.makeBlankFromNull(hD.get(
						"TERMINAL_ROOT_CAUSE").toString());
				String rootcausecompany = BothHelpObjs.makeBlankFromNull(hD
						.get("TERMINAL_COMPANY").toString());
				String rootcausenotes = BothHelpObjs.makeBlankFromNull(hD.get(
						"TERMINAL_NOTES").toString());
				String invengrp = BothHelpObjs.makeBlankFromNull(hD.get(
						"INVENTORY_GROUP").toString());
				String qtyreceived = (hD.get("QUANTITY_RECEIVED") == null ? "&nbsp;"
						: hD.get("QUANTITY_RECEIVED").toString());
				String qualitycntitem = (hD.get("QUALITY_CONTROL_ITEM") == null ? " "
						: hD.get("QUALITY_CONTROL_ITEM").toString());
				String pickable = (hD.get("PICKABLE") == null ? " " : hD.get(
						"PICKABLE").toString());
				String ownercomp = (hD.get("OWNER_COMPANY_ID") == null ? " "
						: hD.get("OWNER_COMPANY_ID").toString());
				if (ownercomp.length() == 0
						|| "Radian".equalsIgnoreCase(ownercomp)) {
					ownercomp = "Haas";
				}

				String origrecid = (hD.get("ORIGINAL_RECEIPT_ID") == null ? " "
						: hD.get("ORIGINAL_RECEIPT_ID").toString());
				if (origrecid.trim().length() == 0) {
					origrecid = "-";
				}
				String unitcost = (hD.get("UNIT_COST") == null ? " " : hD.get(
						"UNIT_COST").toString());
				String currencyId = (hD.get("CURRENCY_ID") == null ? " " : hD
						.get("CURRENCY_ID").toString());

				String mngkitassingl = hD.get("MANAGE_KITS_AS_SINGLE_UNIT")
						.toString();
				String componentid = hD.get("COMPONENT_ID").toString();
				String compmatdesc = hD.get("MATERIAL_DESC").toString();
				String invgrpname = hD.get("INVENTORY_GROUP_NAME").toString();
				String numofkits = hD.get("NUMBER_OF_KITS").toString();
				String storalgeTemp = hD.get("LABEL_STORAGE_TEMP").toString();
				String dateOfShipment = hD.get("DATE_OF_SHIPMENT") == null ? ""
						: hD.get("DATE_OF_SHIPMENT").toString();
				String receiptDocumentAvailable = hD
						.get("RECEIPT_DOCUMENT_AVAILABLE") == null ? "" : hD
						.get("RECEIPT_DOCUMENT_AVAILABLE").toString();
				String dateOfManufacture = hD.get("DATE_OF_MANUFACTURE") == null ? ""
						: hD.get("DATE_OF_MANUFACTURE").toString();
				String receiptPackaging = hD.get("RECEIPT_PACKAGING") == null ? ""
						: hD.get("RECEIPT_PACKAGING").toString();

				String catalogId = hD.get("CATALOG_ID") == null ? "" : hD.get(
						"CATALOG_ID").toString();
				String catPartNo = hD.get("CAT_PART_NO") == null ? "" : hD.get(
						"CAT_PART_NO").toString();
				String catalogCompanyId = hD.get("CATALOG_COMPANY_ID") == null ? ""
						: hD.get("CATALOG_COMPANY_ID").toString();
				String itemType = hD.get("ITEM_TYPE") == null ? "" : hD.get(
						"ITEM_TYPE").toString();
				String newChemRequestId = hD.get("NEW_CHEM_REQUEST_ID") == null ? ""
						: hD.get("NEW_CHEM_REQUEST_ID").toString();
				String deliveryTicket = hD.get("DELIVERY_TICKET") == null ? ""
						: hD.get("DELIVERY_TICKET").toString();
				String dateOfReceipt = hD.get("DATE_OF_RECEIPT") == null ? ""
						: hD.get("DATE_OF_RECEIPT").toString();
				String branchPlant = hD.get("HUB") == null ? "" : hD.get("HUB")
						.toString();
				String poNumber = hD.get("PO_NUMBER") == null ? "" : hD.get(
						"PO_NUMBER").toString();
				String qcDate = hD.get("QC_DATE") == null ? "" : hD.get(
						"QC_DATE").toString();
				String unitLabelPrinted = hD.get("UNIT_LABEL_PRINTED") == null ? "N"
						: hD.get("UNIT_LABEL_PRINTED").toString();
				String doNumberRequired = hD.get("DO_NUMBER_REQUIRED") == null ? "N"
						: hD.get("DO_NUMBER_REQUIRED").toString();
				String polchemIg = hD.get("POLCHEM_IG") == null ? "N" : hD.get(
						"POLCHEM_IG").toString();
				String specs = hD.get("SPECS") == null ? "N" : hD.get("SPECS")
						.toString();
				String customerReceiptId = hD.get("CUSTOMER_RECEIPT_ID") == null ? "N"
						: hD.get("CUSTOMER_RECEIPT_ID").toString();
				String radianPo = hD.get("RADIAN_PO") == null ? "" : hD.get(
						"RADIAN_PO").toString();
				String poLine = hD.get("PO_LINE") == null ? "" : hD.get(
						"PO_LINE").toString();
				String minimumExpireDate = hD.get("MINIMUM_EXPIRE_DATE") == null ? ""
						: hD.get("MINIMUM_EXPIRE_DATE").toString();
				String opsEntityId = hD.get("OPS_ENTITY_ID") == null ? "" : hD
						.get("OPS_ENTITY_ID").toString();
				String unitLabelCatPartNo = hD.get("UNIT_LABEL_CAT_PART_NO") == null ? ""
						: hD.get("UNIT_LABEL_CAT_PART_NO").toString();
				String qaStatement = hD.get("QA_STATEMENT") == null ? "N" : hD
						.get("QA_STATEMENT").toString();
				String netPendingAdj = hD.get("NET_PENDING_ADJ") == null ? ""
						: hD.get("NET_PENDING_ADJ").toString();
				String lockExpireDate = hD.get("LOCK_EXPIRE_DATE") == null ? ""
						: hD.get("LOCK_EXPIRE_DATE").toString();
				String qualityTrackingNumber = hD
						.get("QUALITY_TRACKING_NUMBER") == null ? "" : hD.get(
						"QUALITY_TRACKING_NUMBER").toString();
				String incomingTesting = hD.get("INCOMING_TESTING") == null ? ""
						: hD.get("INCOMING_TESTING").toString();
				String mfgLabelExpireDate = hD.get("MFG_LABEL_EXPIRE_DATE") == null ? ""
						: hD.get("MFG_LABEL_EXPIRE_DATE").toString();
				String supplierCageCode = hD.get("SUPPLIER_CAGE_CODE") == null ? ""
						: hD.get("SUPPLIER_CAGE_CODE").toString();
				String dateOfRepackaging = hD.get("DATE_OF_REPACKAGING") == null ? ""
						: hD.get("DATE_OF_REPACKAGING").toString();				
				String countryOfOrigin = hD.get("COUNTRY_OF_ORIGIN") == null ? ""
						: hD.get("COUNTRY_OF_ORIGIN").toString();	
				String countryOfOriginName = hD.get("COUNTRY_OF_ORIGIN_NAME") == null ? ""
						: hD.get("COUNTRY_OF_ORIGIN_NAME").toString();
				String automatedPutaway = hD.get("AUTOMATED_PUTAWAY") == null ? ""
						: hD.get("AUTOMATED_PUTAWAY").toString();
				

				BigDecimal netPendingAdjValue = null;
				if (netPendingAdj.length() > 0) {
					netPendingAdjValue = new BigDecimal(netPendingAdj);
					netPendingAdjValue = netPendingAdjValue
							.multiply(new BigDecimal("-1"));
				}

				if (!"Y".equalsIgnoreCase(pickable)) {
					Color = "CLASS=\"yellow";
					invcolor = "CLASS=\"INVISIBLEDETYELLOW";
				}

				if ("ML".equalsIgnoreCase(itemType)) {
					Color = "CLASS=\"green";
					invcolor = "CLASS=\"INVISIBLEDETGREEN";
				}

				if (SubuserAction.equalsIgnoreCase("UpdPage")
						&& "NO".equalsIgnoreCase(LINE_STATUS)) {
					Color = "CLASS=\"error";
				}

				boolean Samepoline = false;
				boolean FirstRow = false;
				if ("N".equalsIgnoreCase(mngkitassingl)
						&& Next_rec.equalsIgnoreCase(receipt_id)) {
					Samepoline = true;
					lastItemNum++;
				} else {
					ItemIdCount++;
					lastItemNum = 1;
				}

				if (Samepoline) {
					if (lastItemNum == 2) {
						FirstRow = true;
					}
				} else if ("Y".equalsIgnoreCase(mngkitassingl)) {
					FirstRow = true;
					numofkits = "1";
				}

				out.println("<tr align=\"center\" onmouseup=\"selectRow('" + i
						+ "')\">\n");
				String chkbox_value = "no";
				if (checkednon.equalsIgnoreCase("checked")) {
					chkbox_value = "yes";
				}

				boolean recqcallowed = false;
				if (allowedigforinv.contains(invengrp)) {
					recqcallowed = true;
				}

				lineadded++;
				if (!this.getupdateStatus() || !(recqcallowed)) {
					if (FirstRow) {
						if (this.getupdateStatus() && !(recqcallowed)) {
							out.println("<td "
									+ Color
									+ "\" width=\"2%\" rowspan=\""
									+ numofkits
									+ "\"><INPUT TYPE=\"checkbox\" value=\""
									+ (chkbox_value)
									+ "\"  onClick= \"checkInvenreadonlyValues(name,this)\" "
									+ checkednon + "  NAME=\"row_chk" + i
									+ "\" id=\"row_chk" + i + "\"></td>\n");
						} else {
							out.println("<td " + Color
									+ "\" width=\"2%\" rowspan=\"" + numofkits
									+ "\">&nbsp;</td>\n");
						}
						out.println("<td " + Color
								+ "\" width=\"5%\" rowspan=\"" + numofkits
								+ "\"><A HREF=\"javascript:enterdotinfo('"
								+ Item_id + "')\">" + Item_id + "</A><BR>("
								+ invgrpname + ")</td>\n\n");
					}

					if ("N".equalsIgnoreCase(mngkitassingl)) {
						out.println("<td " + Color + "l\" width=\"20%\">"
								+ compmatdesc + "</td>\n");
					} else {
						out.println("<td " + Color + "l\" width=\"20%\">"
								+ Item_desc + "</td>\n");
					}

					if (FirstRow) {
						out.println("<td " + Color
								+ "\" width=\"5%\" rowspan=\"" + numofkits
								+ "\">" + status + "");
						if (rootCause.length() > 0) {
							out.println("<br>(" + rootCause + ")</td>\n");
						}
						/*
						 * out.println("<td "+Color+"\" width=\"5%\" rowspan=\""+
						 * numofkits+"\">"+rootcause+"\n</td>\n");
						 */
						out.println("<td " + Color
								+ "\" width=\"4%\" rowspan=\"" + numofkits
								+ "\">" + lotStatusAge + "</td>\n");
					}

					if ("INDEFINITE".equalsIgnoreCase(Expire_date)
							|| "01/01/3000".equalsIgnoreCase(Expire_date)) {
						out.println("<td " + Color + "\" width=\"5%\">"
								+ res.getString("label.indefinite") + "</td>\n");
					} else {
						out.println("<td " + Color + "\" width=\"5%\">"
								+ Expire_date + "</td>\n");
					}
					out.println("<td " + Color + "\" width=\"5%\">"
							+ minimumExpireDate + "</td>\n");
					out.println("<td " + Color
							+ "r\" width=\"1%\">&nbsp;</td>\n");
					out.println("<td " + Color + "l\" width=\"3%\">" + Sel_bin
							+ "\n</td>\n");
					out.println("<td " + Color
							+ "r\" width=\"1%\">&nbsp;</td>\n");
					out.println("<td "
							+ Color
							+ "\" width=\"5%\"><A HREF=\"javascript:showPreviousTransactions('"
							+ Mfg_lot + "'," + branchPlant + ")\">" + Mfg_lot
							+ "</A></td>\n\n");
					out.println("<td " + Color + "l\" width=\"5%\">\n");
					if (specs.length() > 0) {
						out.println("<a href=\"javascript:receiptSpecs('"
								+ receipt_id + "')\">" + specs + "</a>\n");
					} else {
						out.println("<a href=\"javascript:receiptSpecs('"
								+ receipt_id + "')\">Add Specs</a>\n");
					}
					out.println("</td>\n");
					if (FirstRow) {
						out.println("<td "
								+ Color
								+ "\" width=\"5%\" rowspan=\""
								+ numofkits
								+ "\"><A HREF=\"javascript:showPreviousReceiptTransactions('"
								+ receipt_id + "'," + branchPlant + ")\">"
								+ receipt_id + "</A><BR>" + origrecid + "<BR>("
								+ ownercomp + ")<BR>" + currencyId + " "
								+ unitcost + "<BR>");
						if ("Y".equalsIgnoreCase(receiptDocumentAvailable)) {
							out.println("<IMG src=\"/images/buttons/document.gif\" alt=\""
									+ res.getString("pickingqc.viewaddreceipts")
									+ "\" onMouseOver=style.cursor=\"hand\" onclick=\"javascript:showProjectDocuments('"
									+ receipt_id
									+ "','"
									+ invengrp
									+ "')\"></td>\n\n");
						} else {
							out.println("<IMG src=\"/images/buttons/plus.gif\" alt=\""
									+ res.getString("label.addReceiptDocuments")
									+ "\" onMouseOver=style.cursor=\"hand\" onclick=\"javascript:showProjectDocuments('"
									+ receipt_id
									+ "','"
									+ invengrp
									+ "')\"></td>\n\n");
						}

						out.println("<TD WIDTH=\"5%\" " + Color
								+ "\" width=\"5%\" height=\"38\" rowspan=\""
								+ numofkits + "\">" + poNumber + "</TD>\n");
						out.println("<TD WIDTH=\"5%\" " + Color
								+ "\" width=\"5%\" height=\"38\" rowspan=\""
								+ numofkits + "\">" + dateOfReceipt + "</TD>\n");
						out.println("<td " + Color
								+ "\" width=\"2%\" rowspan=\"" + numofkits
								+ "\">" + (recert) + "</td>\n");
						out.println("<td " + Color
								+ "\" width=\"5%\" rowspan=\"" + numofkits
								+ "\">" + Qty + "<BR>(" + qtyreceived
								+ ")</td>\n");
						out.println("<td " + Color
								+ "\" width=\"5%\" rowspan=\"" + numofkits
								+ "\">" + netPendingAdjValue + "</td>\n");
						out.println("<td " + Color
								+ "\" width=\"5%\" rowspan=\"" + numofkits
								+ "\">" + receiptPackaging + "</td>\n");
						if (this.getupdateStatus() && !(recqcallowed)) {
							out.println("<td "
									+ Color
									+ "\" width=\"5%\" rowspan=\""
									+ numofkits
									+ "\"><input type=\"text\" CLASS=\"DETAILS\" name=\"qty_recd"
									+ i + "\" id=\"qty_recd" + i
									+ "\"  value=\"" + nooflabels
									+ "\" maxlength=\"30\" size=\"3\"></td>\n");
						} else {
							out.println("<td " + Color
									+ "\" width=\"5%\" rowspan=\"" + numofkits
									+ "\">&nbsp;</td>\n");
						}
						out.println("<td " + Color
								+ "\" width=\"3%\" rowspan=\"" + numofkits
								+ "\">" + lastprintdate + "</td>\n");
						out.println("<td " + Color
								+ "\" width=\"5%\" rowspan=\"" + numofkits
								+ "\">" + storalgeTemp + "</td>\n");
						out.println("<td " + Color
								+ "\" width=\"5%\" rowspan=\"" + numofkits
								+ "\">" + dateOfShipment + "</td>\n");
						out.println("<td " + Color
								+ "\" width=\"5%\" rowspan=\"" + numofkits
								+ "\">" + dateOfManufacture + "</td>\n");
					}
					out.println("<td " + Color + "\" width=\"5%\">"
							+ dateOfRepackaging + "</td>\n");
					if (FirstRow) {
						out.println("<td " + Color
								+ "\" width=\"5%\" rowspan=\"" + numofkits
								+ "\">" + reciptnots + "</td>\n");
						out.println("<td " + Color
								+ "\" width=\"5%\" rowspan=\"" + numofkits
								+ "\">" + deliveryTicket + "</td>\n");
						out.println("<td "
								+ Color
								+ "\" width=\"5%\" rowspan=\""
								+ numofkits
								+ "\">"
								+ unitLabelPrinted
								+ "<input type=\"hidden\" name=\"unitLabelPrinted"
								+ i + "\" name=\"unitLabelPrinted" + i
								+ "\" value=\"" + unitLabelPrinted
								+ "\"></td>\n");
						out.println("<TD WIDTH=\"5%\" " + Color
								+ "\" width=\"5%\" height=\"38\" rowspan=\""
								+ numofkits + "\">" + customerReceiptId
								+ "</TD>\n");
						if (radianPo.length() > 0) {
							out.println("<TD WIDTH=\"5%\" "
									+ Color
									+ "\" width=\"5%\" height=\"38\" rowspan=\""
									+ numofkits
									+ "\"><A onclick=\"javascript:showPurchOrder('"
									+ radianPo
									+ "')\" STYLE=\"color:#0000ff;cursor:hand;text-decoration:underline\" id=\"radianPo_"
									+ i + "\">" + radianPo + "-" + poLine
									+ "</A></TD>\n");
						} else {
							out.println("<TD WIDTH=\"5%\" "
									+ Color
									+ "\" width=\"5%\" height=\"38\" rowspan=\""
									+ numofkits + "\" id=\"radianPo_" + i
									+ "\">" + radianPo + "-" + poLine
									+ "</TD>\n");
						}
						out.println("<TD WIDTH=\"5%\" " + Color
								+ "\" width=\"5%\" height=\"38\" rowspan=\""
								+ numofkits + "\">" + qualityTrackingNumber
								+ "</TD>\n");
						out.println("<TD WIDTH=\"5%\" " + Color
								+ "\" width=\"5%\" height=\"38\" rowspan=\""
								+ numofkits + "\">" + internalReceiptNotes
								+ "</TD>\n");
					}
					if ("INDEFINITE".equalsIgnoreCase(mfgLabelExpireDate)
							|| "01/01/3000"
									.equalsIgnoreCase(mfgLabelExpireDate)) {
						out.println("<td " + Color + "\" width=\"5%\">"
								+ res.getString("label.indefinite") + "</td>\n");
					} else {
						out.println("<td " + Color + "\" width=\"5%\">"
								+ mfgLabelExpireDate + "</td>\n");
					}
					out.println("<td " + Color + "\" width=\"5%\" rowspan=\""
							+ numofkits + "\">" + supplierCageCode + "</td>\n");
					out.println("<td " + Color + "\" width=\"5%\" rowspan=\""
							+ numofkits + "\">" + countryOfOriginName + "</td>\n");
					out.println("<input type=\"hidden\" name=\"receipt_id" + i
							+ "\" id=\"receipt_id" + i + "\" value=\""
							+ receipt_id + "\">\n");
					out.println("<input type=\"hidden\" name=\"item_id" + i
							+ "\" id=\"item_id" + i + "\" value=\"" + Item_id
							+ "\">\n");
				} else // If member of Logistics Access
				{
					if (FirstRow) {
						out.println("<td "
								+ Color
								+ "\" width=\"2%\" rowspan=\""
								+ numofkits
								+ "\"><INPUT TYPE=\"checkbox\" value=\""
								+ (chkbox_value)
								+ "\"  onClick= \"checkInvenValues(name,this)\" "
								+ checkednon + "  NAME=\"row_chk" + i
								+ "\" id=\"row_chk" + i + "\"></td>\n");
						out.println("<td " + Color
								+ "\" width=\"5%\" rowspan=\"" + numofkits
								+ "\"><A HREF=\"javascript:enterdotinfo('"
								+ Item_id + "')\">" + Item_id + "</A><BR>("
								+ invgrpname + ")</td>\n\n");
					}

					if ("N".equalsIgnoreCase(mngkitassingl)) {
						out.println("<td " + Color + "l\" width=\"20%\">"
								+ compmatdesc + "</td>\n");
					} else {
						out.println("<td " + Color + "l\" width=\"20%\">"
								+ Item_desc + "</td>\n");
					}

					boolean stautsupdallowed = false;
					boolean expupdallowed = false;

					if (qcDate.trim().length() > 0
							&& (allowforlotstatus.contains(invengrp) || !"Y"
									.equalsIgnoreCase(qualitycntitem))
							&& !nonpickableinv.contains(invengrp)) {
						stautsupdallowed = true;
					}

					if (allowforexpdate.contains(invengrp)
							|| !"Y".equalsIgnoreCase(qualitycntitem)) {
						expupdallowed = true;
					}
					if ("Y".equalsIgnoreCase(lockExpireDate)) {
						expupdallowed = false;
					}

					if (FirstRow) {
						out.println("<td " + Color
								+ "\" width=\"5%\" rowspan=\"" + numofkits
								+ "\">\n");
						if (status.equalsIgnoreCase("Write Off Requested")
								|| status
										.equalsIgnoreCase("Write Off Approved")) {
							out.println(status);
							out.println("<input type=\"hidden\" name=\"selectElementStatus"
									+ i
									+ "\" id=\"selectElementStatus"
									+ i
									+ "\" value=\"" + status + "\">\n");
						} else {
							out.println("<select name=\"selectElementStatus"
									+ i
									+ "\" id=\"selectElementStatus\" onChange=\"checkterminalstatus('"
									+ i + "')\">\n");
							out.println("<OPTION VALUE=\"\">"
									+ res.getString("label.pleaseselect")
									+ "</OPTION>\n");
							if ("Incoming".equalsIgnoreCase(status)) {
								out.println("<OPTION VALUE=\"Incoming\" selected>"
										+ res.getString("label.incoming")
										+ "</OPTION>\n");
							} else {
								out.println(radian.web.HTMLHelpObj
										.getlogisticsDropDown(all_status_set,
												status, stautsupdallowed,
												netPendingAdjValue));
							}
							out.println("</select>\n");
						}
						out.println("<input type=\"hidden\" id=\"origlotstatus\" name=\"origlotstatus"
								+ i + "\" value=\"" + status + "\">\n");
						if (rootCause.length() > 0) {
							out.println("<br>(" + rootCause + ")");
						}
						out.println("</td>\n");

						/*
						 * out.println("<td "+Color+"\" width=\"5%\" rowspan=\""+
						 * numofkits+"\">"+rootcause+"\n</td>\n");
						 */
						out.println("<td " + Color
								+ "\" width=\"4%\" rowspan=\"" + numofkits
								+ "\">" + lotStatusAge + "</td>\n");
					}

					if (expupdallowed) {
						if ("INDEFINITE".equalsIgnoreCase(Expire_date)
								|| "01/01/3000".equalsIgnoreCase(Expire_date)) {
							out.println("<td "
									+ Color
									+ "\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"expiry_date"
									+ i
									+ "\" id=\"expiry_date"
									+ i
									+ "\" value=\"Indefinite\" onchange=\"checkExpireDate("
									+ i
									+ ")\" maxlength=\"10\" size=\"7\"><FONT SIZE=\"4\"><a href=\"javascript: void(0);\" ID=\"linkexpiry_date"
									+ i
									+ "\" onclick=\"return getCalendar(document.receiving.expiry_date"
									+ i
									+ ",null,null,null,null,'Y');\">&diams;</a></FONT></td>\n");
						} else {
							out.println("<td "
									+ Color
									+ "\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"expiry_date"
									+ i
									+ "\" id=\"expiry_date"
									+ i
									+ "\" value=\""
									+ Expire_date
									+ "\" onchange=\"checkExpireDate("
									+ i
									+ ")\" maxlength=\"10\" size=\"7\"><FONT SIZE=\"4\"><a href=\"javascript: void(0);\" ID=\"linkexpiry_date"
									+ i
									+ "\" onclick=\"return getCalendar(document.receiving.expiry_date"
									+ i
									+ ",null,null,null,null,'Y');\">&diams;</a></FONT></td>\n");
						}
					} else {
						if ("INDEFINITE".equalsIgnoreCase(Expire_date)
								|| "01/01/3000".equalsIgnoreCase(Expire_date)) {
							out.println("<td "
									+ Color
									+ "\" width=\"5%\"><input type=\"text\" "
									+ invcolor
									+ "\" name=\"expiry_date"
									+ i
									+ "\" id=\"expiry_date"
									+ i
									+ "\" value=\"Indefinite\" maxlength=\"10\" size=\"7\" readonly></td>\n");
						} else {
							out.println("<td "
									+ Color
									+ "\" width=\"5%\"><input type=\"text\" "
									+ invcolor
									+ "\" name=\"expiry_date"
									+ i
									+ "\" id=\"expiry_date"
									+ i
									+ "\" value=\""
									+ Expire_date
									+ "\" maxlength=\"10\" size=\"7\" readonly></td>\n");
						}
					}
					out.println("<td " + Color + "\" width=\"5%\">"
							+ minimumExpireDate + "</td>\n");
					if(!"Y".equalsIgnoreCase(automatedPutaway))
					{
						out.println("<td " + Color + "r\" width=\"1%\">\n");
						out.println("<INPUT TYPE=\"BUTTON\" CLASS=\"SUBMITDET\" onmouseover=\"className='SUBMITHOVERDET'\" onMouseout=\"className='SUBMITDET'\" name=\"addBin"
								+ i
								+ "\" id=\"addBin"
								+ i
								+ "\" value=\"+\" OnClick=\"showVvHubBins("
								+ Item_id
								+ ","
								+ i
								+ ","
								+ branchPlant
								+ ")\" ></TD>\n");
						out.println("<td " + Color + "l\" width=\"3%\">\n");
						out.println("<select name=\"selectElementBin" + i
								+ "\" id=\"selectElementBin" + i + "\">\n");
						out.println("<option value=\"" + Sel_bin + "\">" + Sel_bin
								+ "</option>\n");
	
						out.println("</select></td>\n");
						out.println("<td " + Color + "r\" width=\"1%\">\n");
						out.println("<INPUT TYPE=\"BUTTON\" CLASS=\"SUBMITDET\" onmouseover=\"className='SUBMITHOVERDET'\" onMouseout=\"className='SUBMITDET'\" name=\"moreBin"
								+ i
								+ "\" id=\"moreBin"
								+ i
								+ "\" value=\"more\"  onclick=\"loadBins("
								+ Item_id
								+ ",'"
								+ branchPlant
								+ "',"
								+ i
								+ ")\" ></TD>\n");
					}
					else
					{
						out.println("<td " + Color
								+ "r\" width=\"1%\">&nbsp;</td>\n");
						out.println("<td " + Color + "l\" width=\"3%\">" + Sel_bin
								+ "\n<INPUT TYPE=\"hidden\" NAME=\"selectElementBin" + i + "\" ID=\"selectElementBin"+ i+ "\" value=\"" + Sel_bin + "\"/>"
								+ "</td>\n");
						out.println("<td " + Color
								+ "r\" width=\"1%\">&nbsp;</td>\n");	

					}
					
					if (expupdallowed) {
						out.println("<td "
								+ Color
								+ "\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"mfg_lot"
								+ i + "\" id=\"mfg_lot" + i + "\" value=\""
								+ Mfg_lot
								+ "\" maxlength=\"36\" size=\"15\"></td>\n");
					} else {
						out.println("<td "
								+ Color
								+ "\" width=\"5%\"><input type=\"text\" "
								+ invcolor
								+ "\" name=\"mfg_lot"
								+ i
								+ "\" id=\"mfg_lot"
								+ i
								+ "\" value=\""
								+ Mfg_lot
								+ "\" maxlength=\"36\" size=\"15\" readonly></td>\n");
					}
					out.println("<td " + Color + "l\" width=\"5%\">\n");
					if (specs.length() > 0) {
						out.println("<a href=\"javascript:receiptSpecs('"
								+ receipt_id + "')\">" + specs + "</a>\n");
					} else {
						out.println("<a href=\"javascript:receiptSpecs('"
								+ receipt_id + "')\">Add Specs</a>\n");
					}
					out.println("</td>\n");

					if (FirstRow) {
						out.println("<td "
								+ Color
								+ "\" width=\"5%\" rowspan=\""
								+ numofkits
								+ "\"><A HREF=\"javascript:showPreviousReceiptTransactions('"
								+ receipt_id + "'," + branchPlant + ")\">"
								+ receipt_id + "</A><BR>" + origrecid + "<BR>("
								+ ownercomp + ")<BR>" + currencyId + " "
								+ unitcost + "<BR>");
						if ("Y".equalsIgnoreCase(receiptDocumentAvailable)) {
							out.println("<IMG src=\"/images/buttons/document.gif\" alt=\"View/Add Receipt Documents\" onMouseOver=style.cursor=\"hand\" onclick=\"javascript:showProjectDocuments('"
									+ receipt_id
									+ "','"
									+ invengrp
									+ "')\"></td>\n\n");
						} else {
							out.println("<IMG src=\"/images/buttons/plus.gif\" alt=\"Add Receipt Documents\" onMouseOver=style.cursor=\"hand\" onclick=\"javascript:showProjectDocuments('"
									+ receipt_id
									+ "','"
									+ invengrp
									+ "')\"></td>\n\n");
						}
						out.println("<TD WIDTH=\"5%\" " + Color
								+ "\" width=\"5%\" height=\"38\" rowspan=\""
								+ numofkits + "\">" + poNumber + "</TD>\n");
						out.println("<TD WIDTH=\"5%\" " + Color
								+ "\" width=\"5%\" height=\"38\" rowspan=\""
								+ numofkits + "\">" + dateOfReceipt + "</TD>\n");
						if (expupdallowed) {
							out.println("<td "
									+ Color
									+ "\" width=\"5%\" rowspan=\""
									+ numofkits
									+ "\"><input type=\"text\" CLASS=\"DETAILS\" name=\"recerts"
									+ i + "\" id=\"recerts" + i + "\" value=\""
									+ recert
									+ "\" maxlength=\"30\" size=\"3\">");
							if ("Y".equalsIgnoreCase(incomingTesting)) {
								out.println("<BR><a href=\"javascript:startMARStest('"
										+ receipt_id
										+ "')\">"
										+ res.getString("label.startmarstest")
										+ "</a>\n");
							}
							out.println("</td>\n");
						} else {
							out.println("<td "
									+ Color
									+ "\" width=\"5%\" rowspan=\""
									+ numofkits
									+ "\"><input type=\"text\" "
									+ invcolor
									+ "\" name=\"recerts"
									+ i
									+ "\" id=\"recerts"
									+ i
									+ "\" value=\""
									+ recert
									+ "\" maxlength=\"30\" size=\"3\" readonly></td>\n");
						}

						float quantityfromhas = (Float.parseFloat(Qty));

						if (quantityfromhas > 0
								&& qcDate.trim().length() > 0
								&& !(status
										.equalsIgnoreCase("Write Off Requested") || status
										.equalsIgnoreCase("Incoming"))) {
							out.println("<td " + Color
									+ "\" width=\"5%\" rowspan=\"" + numofkits
									+ "\"><A HREF=\"javascript:splitQty('"
									+ receipt_id + "'," + branchPlant + ",'"
									+ Qty + "','" + opsEntityId + "','"
									+ netPendingAdj + "')\">" + Qty
									+ "</A><BR>(" + qtyreceived + ")\n\n");
							out.println("<BR><IMG src=\"/images/buttons/plus.gif\" alt=\"Write On\" onMouseOver=style.cursor=\"hand\" onclick=\"javascript:writeOnRequest('"
									+ receipt_id + "')\"></td>\n\n");
						} else {
							out.println("<td " + Color
									+ "\" width=\"5%\" rowspan=\"" + numofkits
									+ "\">" + Qty + "<BR>(" + qtyreceived
									+ ")\n");
							out.println("<BR><IMG src=\"/images/buttons/plus.gif\" alt=\"Write On\" onMouseOver=style.cursor=\"hand\" onclick=\"javascript:writeOnRequest('"
									+ receipt_id + "')\"></td>\n\n");
						}

						out.println("<td " + Color
								+ "\" width=\"5%\" rowspan=\"" + numofkits
								+ "\">" + netPendingAdjValue + "</td>\n");
						out.println("<td " + Color
								+ "\" width=\"5%\" rowspan=\"" + numofkits
								+ "\">" + receiptPackaging + "</td>\n");
						out.println("<td "
								+ Color
								+ "\" width=\"2%\" rowspan=\""
								+ numofkits
								+ "\"><input type=\"text\" CLASS=\"DETAILS\" name=\"qty_recd"
								+ i + "\" id=\"qty_recd" + i + "\" value=\""
								+ nooflabels
								+ "\" maxlength=\"30\" size=\"3\"></td>\n");
						out.println("<td " + Color
								+ "\" width=\"3%\" rowspan=\"" + numofkits
								+ "\">" + lastprintdate + "</td>\n");
						out.println("<td " + Color
								+ "\" width=\"5%\" rowspan=\"" + numofkits
								+ "\">" + storalgeTemp + "</td>\n");
						out.println("<td "
								+ Color
								+ "\" width=\"5%\" rowspan=\""
								+ numofkits
								+ "\"><input type=\"text\" CLASS=\"DETAILS\" name=\"dateOfShipment"
								+ i
								+ "\" id=\"dateOfShipment"
								+ i
								+ "\" value=\""
								+ dateOfShipment
								+ "\" maxlength=\"10\" size=\"7\"><FONT SIZE=\"4\"><a href=\"javascript: void(0);\" id=\"linkdateOfShipment"
								+ i
								+ "\" onClick=\"return getCalendar(document.receiving.dateOfShipment"
								+ i + ");\">&diams;</a></FONT></td>\n");
						out.println("<td "
								+ Color
								+ "\" width=\"5%\" rowspan=\""
								+ numofkits
								+ "\"><input type=\"text\" CLASS=\"DETAILS\" name=\"dateOfManufacture"
								+ i
								+ "\" id=\"dateOfManufacture"
								+ i
								+ "\" value=\""
								+ dateOfManufacture
								+ "\" maxlength=\"10\" size=\"7\"><FONT SIZE=\"4\"><a href=\"javascript: void(0);\" id=\"linkdateOfManufacture"
								+ i
								+ "\" onClick=\"return getCalendar(document.receiving.dateOfManufacture"
								+ i + ");\">&diams;</a></FONT></td>\n");
					}
					out.println("<td "
							+ Color
							+ "\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"dateOfRepackaging"
							+ i
							+ "\" id=\"dateOfRepackaging"
							+ i
							+ "\" value=\""
							+ dateOfRepackaging
							+ "\" maxlength=\"10\" size=\"7\"><FONT SIZE=\"4\"><a href=\"javascript: void(0);\" id=\"linkdateOfRepackaging"
							+ i
							+ "\" onClick=\"return getCalendar(document.receiving.dateOfRepackaging"
							+ i + ",document.receiving.dateOfManufacture" + i
							+ ",null,null,document.receiving.dateOfReceipt" + i
							+ ");\">&diams;</a></FONT></td>\n");
					if (FirstRow) {
						if (reciptnots.trim().length() > 0) {
							out.println("<td "
									+ Color
									+ "\" width=\"5%\" rowspan=\""
									+ numofkits
									+ "\" ID=\"lineNotesTd"
									+ i
									+ "\" onMouseOver=style.cursor=\"hand\" onclick='showLineNotes("
									+ i + ")'>\n");
							out.println("<P ID=\"lineNoteslink" + i
									+ "\">+</P>\n");
							out.println("<DIV ID=\"lineNotes"
									+ i
									+ "\" CLASS=\"displaynone\" onMouseOver=style.cursor=\"hand\">\n");
							out.println("" + reciptnots + "\n");
							out.println("</DIV>\n");
							out.println("<INPUT TYPE=\"hidden\" NAME=\"notesVisible"
									+ i
									+ "\" ID=\"notesVisible"
									+ i
									+ "\" value=\"No\" >\n");
						} else {
							out.println("<td " + Color
									+ "\" width=\"5%\" rowspan=\"" + numofkits
									+ "\" ID=\"lineNotesTd" + i + "\">\n");
						}
						out.println("<BUTTON type=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"receiptNotes\" name=\"receiptNotes\" value=\"...\" OnClick=\"getReceiptnotes('"
								+ receipt_id
								+ "')\"><IMG src=\"/images/ponotes.gif\" alt=\""
								+ res.getString("label.viewaddreceiptnotes")
								+ "\"></BUTTON></td>\n");
						if (this.getupdateStatus() && recqcallowed) {
							out.println("<TD WIDTH=\"5%\" "
									+ Color
									+ "\" width=\"5%\" height=\"38\" rowspan=\""
									+ numofkits
									+ "\"><input type=\"text\" CLASS=\"DETAILS\" name=\"deliveryTicket"
									+ i + "\" id=\"deliveryTicket" + i
									+ "\"  value=\"" + deliveryTicket
									+ "\" maxlength=\"50\" size=\"6\"></TD>\n");
						} else {
							out.println("<TD WIDTH=\"5%\" "
									+ Color
									+ "\" width=\"5%\" height=\"38\" rowspan=\""
									+ numofkits
									+ "\">"
									+ deliveryTicket
									+ "<input type=\"hidden\" name=\"deliveryTicket"
									+ i + "\" id=\"deliveryTicket" + i
									+ "\"  value=\"" + deliveryTicket
									+ "\"></TD>\n");
						}
						if (this.getupdateStatus() && recqcallowed) {
							if (polchemIg.equalsIgnoreCase("Y")
									&& doNumberRequired.equalsIgnoreCase("N")) {
								out.println("<TD WIDTH=\"5%\" "
										+ Color
										+ "\" width=\"5%\" height=\"38\" rowspan=\""
										+ numofkits
										+ "\"><input type=\"checkbox\" name=\"unitLabelPrinted"
										+ i
										+ "\" id=\"unitLabelPrinted"
										+ i
										+ "\" onClick=\"unitLabelPartNumber('"
										+ i
										+ "')\" value=\"Y\" "
										+ (unitLabelPrinted
												.equalsIgnoreCase("Y") ? "checked"
												: "") + "></TD>\n");
							} else {
								out.println("<TD WIDTH=\"5%\" "
										+ Color
										+ "\" width=\"5%\" height=\"38\" rowspan=\""
										+ numofkits
										+ "\">"
										+ unitLabelPrinted
										+ "<input type=\"hidden\" name=\"unitLabelPrinted"
										+ i + "\" name=\"unitLabelPrinted" + i
										+ "\" value=\"" + unitLabelPrinted
										+ "\"></TD>\n");
							}
						} else {
							out.println("<TD WIDTH=\"5%\" "
									+ Color
									+ "\" width=\"5%\" height=\"38\" rowspan=\""
									+ numofkits
									+ "\">"
									+ unitLabelPrinted
									+ "<input type=\"hidden\" name=\"unitLabelPrinted"
									+ i + "\" id=\"unitLabelPrinted" + i
									+ "\" value=\"" + unitLabelPrinted
									+ "\"></TD>\n");
						}

						out.println("<TD WIDTH=\"5%\" " + Color
								+ "\" width=\"5%\" height=\"38\" rowspan=\""
								+ numofkits + "\">" + customerReceiptId
								+ "</TD>\n");
						if (radianPo.length() > 0) {
							out.println("<TD WIDTH=\"5%\" "
									+ Color
									+ "\" width=\"5%\" height=\"38\" rowspan=\""
									+ numofkits
									+ "\"><A onclick=\"javascript:showPurchOrder('"
									+ radianPo
									+ "')\" STYLE=\"color:#0000ff;cursor:hand;text-decoration:underline\" id=\"radianPo_"
									+ i + "\">" + radianPo + "-" + poLine
									+ "</A></TD>\n");
						} else {
							out.println("<TD WIDTH=\"5%\" "
									+ Color
									+ "\" width=\"5%\" height=\"38\" rowspan=\""
									+ numofkits + "\" id=\"radianPo_" + i
									+ "\">" + radianPo + "-" + poLine
									+ "</TD>\n");
						}
						out.println("<TD WIDTH=\"5%\" " + Color
								+ "\" width=\"5%\" height=\"38\" rowspan=\""
								+ numofkits + "\">\n");
						out.println("<input type=\"text\" CLASS=\"DETAILS\" name=\"qualityTrackingNumber"
								+ i
								+ "\" id=\"qualityTrackingNumber"
								+ i
								+ "\" value=\""
								+ qualityTrackingNumber
								+ "\" maxlength=\"30\" size=\"6\"/>");
						if (internalReceiptNotes.trim().length() > 0) {
							out.println("<td "
									+ Color
									+ "\" width=\"5%\" rowspan=\""
									+ numofkits
									+ "\" ID=\"internalreceiptNotesTd"
									+ i
									+ "\" onMouseOver=style.cursor=\"hand\" onclick='showInternalReceiptNotes("
									+ i + ")'>\n");
							out.println("<P ID=\"internalReceiptNoteslink" + i
									+ "\">+</P>\n");
							out.println("<DIV ID=\"internalReceiptNotes"
									+ i
									+ "\" CLASS=\"displaynone\" onMouseOver=style.cursor=\"hand\">\n");
							out.println("" + internalReceiptNotes + "\n");
							out.println("</DIV>\n");
							out.println("<INPUT TYPE=\"hidden\" NAME=\"internalReceiptnotesVisible"
									+ i
									+ "\" ID=\"internalReceiptnotesVisible"
									+ i + "\" value=\"No\" >\n");
						} else {
							out.println("<td " + Color
									+ "\" width=\"5%\" rowspan=\"" + numofkits
									+ "\" ID=\"internalreceiptNotesTd" + i
									+ "\">\n");
						}
						out.println("<BUTTON type=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" id=\"internalreceiptNotes\" name=\"internalreceiptNotes\" value=\"...\" OnClick=\"getReceiptnotes('"
								+ receipt_id
								+ "')\"><IMG src=\"/images/ponotes.gif\" alt=\""
								+ res.getString("label.viewaddreceiptnotes")
								+ "\"></BUTTON></td>\n");
					}
					if (expupdallowed) {
						if ("INDEFINITE".equalsIgnoreCase(mfgLabelExpireDate)
								|| "01/01/3000"
										.equalsIgnoreCase(mfgLabelExpireDate)) {
							out.println("<td "
									+ Color
									+ "\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"mfgLabelExpireDate"
									+ i
									+ "\" id=\"mfgLabelExpireDate"
									+ i
									+ "\" value=\"Indefinite\" onchange=\"checkLabelExpireDate("
									+ i
									+ ")\" maxlength=\"10\" size=\"7\"><FONT SIZE=\"4\"><a href=\"javascript: void(0);\" ID=\"linkmfgLabelExpireDate"
									+ i
									+ "\" onclick=\"return getCalendar(document.receiving.mfgLabelExpireDate"
									+ i + ");\">&diams;</a></FONT></td>\n");
						} else {
							out.println("<td "
									+ Color
									+ "\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"mfgLabelExpireDate"
									+ i
									+ "\" id=\"mfgLabelExpireDate"
									+ i
									+ "\" value=\""
									+ mfgLabelExpireDate
									+ "\" onchange=\"checkLabelExpireDate("
									+ i
									+ ")\" maxlength=\"10\" size=\"7\"><FONT SIZE=\"4\"><a href=\"javascript: void(0);\" ID=\"linkmfgLabelExpireDate"
									+ i
									+ "\" onclick=\"return getCalendar(document.receiving.mfgLabelExpireDate"
									+ i + ");\">&diams;</a></FONT></td>\n");
						}
					} else {
						if ("INDEFINITE".equalsIgnoreCase(mfgLabelExpireDate)
								|| "01/01/3000"
										.equalsIgnoreCase(mfgLabelExpireDate)) {
							out.println("<td "
									+ Color
									+ "\" width=\"5%\"><input type=\"text\" "
									+ invcolor
									+ "\" name=\"mfgLabelExpireDate"
									+ i
									+ "\" id=\"mfgLabelExpireDate"
									+ i
									+ "\" value=\"Indefinite\" maxlength=\"10\" size=\"7\" readonly></td>\n");
						} else {
							out.println("<td "
									+ Color
									+ "\" width=\"5%\"><input type=\"text\" "
									+ invcolor
									+ "\" name=\"mfgLabelExpireDate"
									+ i
									+ "\" id=\"mfgLabelExpireDate"
									+ i
									+ "\" value=\""
									+ mfgLabelExpireDate
									+ "\" maxlength=\"10\" size=\"7\" readonly></td>\n");
						}
					}
					out.println("<td "
							+ Color
							+ "\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" rowspan=\""
							+ numofkits + "\"  name=\"supplierCageCode" + i
							+ "\" id=\"supplierCageCode" + i + "\" value=\""
							+ supplierCageCode
							+ "\" maxlength=\"10\" size=\"7\"></td>\n");
					out.println("<td " + Color + "l\" width=\"3%\">\n");
					out.println("<select name=\"selectElementCountryOfOrigin" + i
							+ "\" id=\"selectElementCountryOfOrigin" + i + "\">\n");
					out.println("<option value=\"\" />");
					for(Hashtable<String,String> country :  vvCountries)
						if(countryOfOrigin.equalsIgnoreCase(country.get("COUNTRY_ABBREV")))
							out.println("<option value=\"" + country.get("COUNTRY_ABBREV") + "\" selected>" + country.get("COUNTRY") + "</option>\n");
						else
							out.println("<option value=\"" + country.get("COUNTRY_ABBREV") + "\">" + country.get("COUNTRY") + "</option>\n");
						
					out.println("</select></td>\n");
					out.println("</TD>\n");

					out.println("<input type=\"hidden\" name=\"item_id" + i
							+ "\" id=\"item_id" + i + "\" value=\"" + Item_id
							+ "\">\n");
					out.println("<input type=\"hidden\" name=\"item_id\" id=\"item_id\" value=\""
							+ Item_id + "\">\n");
					out.println("<input type=\"hidden\" name=\"rootcause" + i
							+ "\" id=\"rootcause" + i + "\" value=\""
							+ rootCause + "\">\n");
					out.println("<input type=\"hidden\" name=\"rootcausecompany"
							+ i
							+ "\" id=\"rootcausecompany"
							+ i
							+ "\" value=\"" + rootcausecompany + "\">\n");
					out.println("<input type=\"hidden\" name=\"rootcausenotes"
							+ i + "\" id=\"rootcausenotes" + i + "\" value=\""
							+ rootcausenotes + "\">\n");
					out.println("<input type=\"hidden\" name=\"receipt_id" + i
							+ "\" id=\"receipt_id" + i + "\" value=\""
							+ receipt_id + "\">\n");

					out.println("<input type=\"hidden\" name=\"inventoryGroup"
							+ i + "\" id=\"inventoryGroup" + i + "\" value=\""
							+ invengrp + "\">\n");
					out.println("<input type=\"hidden\" name=\"catalogId" + i
							+ "\" id=\"catalogId" + i + "\" value=\""
							+ catalogId + "\">\n");
					out.println("<input type=\"hidden\" name=\"catPartNo" + i
							+ "\" id=\"catPartNo" + i + "\" value=\""
							+ catPartNo + "\">\n");
					out.println("<input type=\"hidden\" name=\"catalogCompanyId"
							+ i
							+ "\" id=\"catalogCompanyId"
							+ i
							+ "\" value=\"" + catalogCompanyId + "\">\n");
					out.println("<input type=\"hidden\" name=\"itemType" + i
							+ "\" id=\"itemType" + i + "\" value=\"" + itemType
							+ "\">\n");
					out.println("<input type=\"hidden\" name=\"newChemRequestId"
							+ i
							+ "\" id=\"newChemRequestId"
							+ i
							+ "\" value=\"" + newChemRequestId + "\">\n");
					out.println("<input type=\"hidden\" name=\"unitLabelCatPartNo"
							+ i
							+ "\" id=\"unitLabelCatPartNo"
							+ i
							+ "\" value=\"" + unitLabelCatPartNo + "\">\n");
					out.println("<input type=\"hidden\" name=\"branchPlant" + i
							+ "\" id=\"branchPlant" + i + "\" value=\""
							+ branchPlant + "\">\n");
					out.println("<input type=\"hidden\" name=\"opsEntityId" + i
							+ "\" id=\"opsEntityId" + i + "\" value=\""
							+ opsEntityId + "\">\n");
					out.println("<input type=\"hidden\" name=\"netPendingAdj"
							+ i + "\" id=\"netPendingAdj" + i + "\" value=\""
							+ netPendingAdj + "\">\n");

					out.println("</tr>\n");
				}

				if ((ItemIdCount) % 2 == 0) {
					Color = "CLASS=\"white";
					invcolor = "CLASS=\"INVISIBLEHEADWHITE";
				} else {
					Color = "CLASS=\"blue";
					invcolor = "CLASS=\"INVISIBLEHEADBLUE";
				}
			}

			out.println("</table>\n");
			out.println("<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\" CLASS=\"moveup\">\n");
			out.println("<tr>");
			out.println("<TD HEIGHT=\"10\" WIDTH=\"100%\" VALIGN=\"MIDDLE\" BGCOLOR=\"#000066\">&nbsp;\n");
			out.println("</TD></tr>");
			out.println("</table>\n");

			out.println("</form>\n");
			out.println("</body></html>\n");
		} catch (Exception e) {
			invlog.error(e.getMessage(), e);
			out.println(radian.web.HTMLHelpObj.printError("label.logistics",
					intcmIsApplication, res));
		}
	}
	// End of method
}
// END OF CLASS

