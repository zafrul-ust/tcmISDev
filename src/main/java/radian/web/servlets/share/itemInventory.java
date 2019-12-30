package radian.web.servlets.share;

//
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;
import java.util.*;
import java.text.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.HubInventoryGroupProcess;

import radian.tcmis.common.util.SqlHandler;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2004
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 * 09-20-04 - Giving the ability to specify a need date
 * 08-17-05 - Operating method for item counting inventory groups can be changed on the MinMax page, linked on itemID
 * 01-13-06 - Making sure a null operating method is not passed to the procedure.
 */

public class itemInventory {
 private ServerResourceBundle bundle = null;
 private TcmISDBModel db = null;
 private String thedecidingRandomNumber = null;
 private boolean completeSuccess = true;
 private boolean noneToUpd = true;
 private boolean allowupdate;
 private static org.apache.log4j.Logger invlog = org.apache.log4j.Logger.getLogger(itemInventory.class);
 private String errormsg = "";
 private boolean intcmIsApplication = false;

 public itemInventory(ServerResourceBundle b, TcmISDBModel d) {
	bundle = b;
	db = d;
 }

 public void setupdateStatus(boolean id) {
	this.allowupdate = id;
 }

 private boolean getupdateStatus() throws Exception {
	return this.allowupdate;
 }

 //Process the HTTP Get request
 public void doGet(HttpServletRequest request,
	HttpServletResponse response) throws ServletException, IOException {
	doPost(request, response);
 }

 //Process the HTTP Post request
 public void doPost(HttpServletRequest request,
	HttpServletResponse response) throws ServletException, IOException {
	PrintWriter out = response.getWriter();
	response.setContentType("text/html");
	HttpSession itinvsession = request.getSession();

        PersonnelBean personnelBean = (PersonnelBean) itinvsession.getAttribute("personnelBean");
        Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
        if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
        {
         intcmIsApplication = true;
        }

	String personnelid = itinvsession.getAttribute("PERSONNELID") == null ? "" :
	 itinvsession.getAttribute("PERSONNELID").toString();
	Collection hubInventoryGroupOvBeanCollection = new Vector( (Collection) itinvsession.
	 getAttribute("hubInventoryGroupOvBeanCollection"));

	String User_Action = request.getParameter("UserAction");
	if (User_Action == null)
	 User_Action = "New";
	User_Action = User_Action.trim();

	String branch_plant = request.getParameter("hubC");
	if (branch_plant == null)
	 branch_plant = "";
	branch_plant = branch_plant.trim();

	String invengrp = request.getParameter("invengrp");
	if (invengrp == null)
	 invengrp = "";
	invengrp = invengrp.trim();

	String selplantid = request.getParameter("facilityName");
	if (selplantid == null)
	 selplantid = "";
	selplantid = selplantid.trim();

	String selbldgid = request.getParameter("workAreaName");
	if (selbldgid == null)
	 selbldgid = "";
	selbldgid = selbldgid.trim();

	String searchText = request.getParameter("searchText");
	if (searchText == null)
	 searchText = "";
	searchText = searchText.trim();

	String sortby = request.getParameter("sortby");
	if (sortby == null)
	 sortby = "";
	sortby = sortby.trim();

	String searchLike = request.getParameter("searchlike");
	if (searchLike == null)
	 searchLike = "";
	searchLike = searchLike.trim();

	String showActiveOnly = request.getParameter("showActiveOnly");
	if (showActiveOnly == null && "New".equalsIgnoreCase(User_Action))
	 showActiveOnly = "Yes";
	else if (showActiveOnly == null)
	 showActiveOnly = "";
	showActiveOnly = showActiveOnly.trim();

	String minMaxOnly = request.getParameter("minMaxOnly");
	if (minMaxOnly == null && "New".equalsIgnoreCase(User_Action))
	 minMaxOnly = "Yes";
	else if (minMaxOnly == null)
	 minMaxOnly = "";
	minMaxOnly = minMaxOnly.trim();

	thedecidingRandomNumber = request.getParameter("thedecidingRandomNumber");
	if (thedecidingRandomNumber == null)
	 thedecidingRandomNumber = "";
	thedecidingRandomNumber = thedecidingRandomNumber.trim();

	try {
	 Random rand = new Random();
	 int tmpReq = rand.nextInt();
	 Integer tmpReqNum = new Integer(tmpReq);

	 Hashtable hub_list_set = new Hashtable();
	 hub_list_set = (Hashtable) itinvsession.getAttribute("HUB_PREF_LIST");

	 String SubUser_Action = request.getParameter("SubUserAction");
	 if (SubUser_Action == null)
		SubUser_Action = "";

	 Hashtable initialData = null;
	 Hashtable iniOperatingMethodData = null;
	 Hashtable iniBillingMethodData = null;
	 String initialDataLoaded = "";
	 Vector forcebuy_invengrps = new Vector();
	 Vector updatezeom_invengrps = new Vector();

	 initialDataLoaded = itinvsession.getAttribute("ITEM_INVENTORY_DATA_LOADED") == null ?
		"" : itinvsession.getAttribute("ITEM_INVENTORY_DATA_LOADED").toString();
	 if (initialDataLoaded.equalsIgnoreCase("Yes")) {
		initialData = (Hashtable) itinvsession.getAttribute("INITIAL_ITEM_INVENTORY_DATA");
		iniOperatingMethodData = (Hashtable) itinvsession.getAttribute(
		 "INITIAL_OPERATING_METHOD_DATA");
		forcebuy_invengrps = (Vector) itinvsession.getAttribute("FORCEBUY_ALLOWED_INVENGRP");
		updatezeom_invengrps = (Vector) itinvsession.getAttribute("ZETOM_ALLOWED_INVENGRP");
	 }
	 else {
		String CompanyID = itinvsession.getAttribute("COMPANYID").toString();
		Hashtable hub_list1 = (Hashtable) (hub_list_set.get("HUB_LIST"));

		if (hub_list1.size() > 0) {
		 initialData = radian.web.dropdwnHelpObj.getgmInitialData(db, hub_list_set);
		 iniOperatingMethodData = radian.web.dropdwnHelpObj.getOperatingMethodData(db,
			hub_list_set);
		 iniBillingMethodData = radian.web.dropdwnHelpObj.getBillingMethodData(db,
			hub_list_set);
		}

		itinvsession.setAttribute("INITIAL_ITEM_INVENTORY_DATA", initialData);
		itinvsession.setAttribute("INITIAL_OPERATING_METHOD_DATA", iniOperatingMethodData);
		itinvsession.setAttribute("INITIAL_BILLING_METHOD_DATA", iniBillingMethodData);
		itinvsession.setAttribute("ITEM_INVENTORY_DATA_LOADED", "Yes");
		forcebuy_invengrps = radian.web.HTMLHelpObj.createvgrpmemlist(db, personnelid,
		 "ForceBuy");
		updatezeom_invengrps = radian.web.HTMLHelpObj.createvgrpmemlist(db, personnelid,
		 "ItemMgmt");
		itinvsession.setAttribute("FORCEBUY_ALLOWED_INVENGRP", forcebuy_invengrps);
		itinvsession.setAttribute("ZETOM_ALLOWED_INVENGRP", updatezeom_invengrps);
	 }

	 if (thedecidingRandomNumber.length() > 0) {
		String randomnumberfromsesion = itinvsession.getAttribute("LOGRNDITEMINVCOOKIE") == null ?
		 "" : itinvsession.getAttribute("LOGRNDITEMINVCOOKIE").toString();
		if (randomnumberfromsesion.equalsIgnoreCase(thedecidingRandomNumber)) {
		 thedecidingRandomNumber = tmpReqNum.toString();
		 itinvsession.setAttribute("LOGRNDITEMINVCOOKIE", thedecidingRandomNumber);
		}
		else {
		 thedecidingRandomNumber = tmpReqNum.toString();
		 itinvsession.setAttribute("LOGRNDITEMINVCOOKIE", thedecidingRandomNumber);
		 itinvsession.setAttribute("ITEM_INVENTORY_DATA", new Vector());
		 buildHeader(hub_list_set, branch_plant, selplantid, selbldgid, invengrp, searchText,
			sortby, minMaxOnly, searchLike, showActiveOnly, initialData, out);
		 out.println("<BR><BR><CENTER><FONT SIZE=\"4\" FACE=\"Arial\" COLOR=\"#fc0303\"><B>Please Do Not Use The Back Button of The Browser</B><BR> Thanks.</FONT>");
		 return;
		}
	 }
	 else {
		thedecidingRandomNumber = tmpReqNum.toString();
		itinvsession.setAttribute("LOGRNDITEMINVCOOKIE", thedecidingRandomNumber);
	 }

	 if (User_Action.equalsIgnoreCase("New")) {
		String CompanyID = itinvsession.getAttribute("COMPANYID").toString();
		Hashtable hub_list1 = (Hashtable) (hub_list_set.get("HUB_LIST"));

		if (hub_list1.size() < 1) {
		 buildHeader(hub_list_set, branch_plant, selplantid, selbldgid, invengrp, searchText,
			sortby, minMaxOnly, searchLike, showActiveOnly, initialData, out);
		 out.println(radian.web.HTMLHelpObj.printHTMLNoFacilities());
		 hub_list_set = null;
		 out.close();
		}
		else {
		 buildHeader(hub_list_set, branch_plant, selplantid, selbldgid, invengrp, searchText,
			sortby, minMaxOnly, searchLike, showActiveOnly, initialData, out);
		 out.println(radian.web.HTMLHelpObj.printHTMLSelect());
		 out.close();
		 hub_list_set = null;
		}
	 }
	 else if (User_Action.equalsIgnoreCase("Search")) {
		Vector openorder_data = new Vector();
		if (branch_plant.trim().length() > 0) {
		 openorder_data = getResult(hubInventoryGroupOvBeanCollection, branch_plant,
			selplantid, selbldgid, invengrp, searchText, minMaxOnly, searchLike,showActiveOnly);

		 Hashtable sum = (Hashtable) (openorder_data.elementAt(0));
		 int count = ( (Integer) sum.get("TOTAL_NUMBER")).intValue();
		 if (0 == count) {
			buildHeader(hub_list_set, branch_plant, selplantid, selbldgid, invengrp, searchText,
			 sortby, minMaxOnly, searchLike, showActiveOnly, initialData, out);
			out.println(radian.web.HTMLHelpObj.printHTMLNoData("No Items Found"));
			out.close();
			//clean up
			openorder_data = null;
			hub_list_set = null;
		 }
		 else {
			itinvsession.setAttribute("ITEM_INVENTORY_DATA", openorder_data);
			buildHeader(hub_list_set, branch_plant, selplantid, selbldgid, invengrp, searchText,
			 sortby, minMaxOnly, searchLike, showActiveOnly, initialData, out);
			out.println(buildDetails(openorder_data, User_Action, forcebuy_invengrps,
			 updatezeom_invengrps, branch_plant));

			out.close();
			//clean up
			openorder_data = null;
			hub_list_set = null;
		 } //when there are open orders for hub
		}
		else {
		 buildHeader(hub_list_set, branch_plant, selplantid, selbldgid, invengrp, searchText,
			sortby, minMaxOnly, searchLike, showActiveOnly, initialData, out);
		 out.println(radian.web.HTMLHelpObj.printHTMLNoData("Please Select a Hub"));
		 out.close();
		 //clean up
		 openorder_data = null;
		 hub_list_set = null;
		}
	 }
	 else if (User_Action.equalsIgnoreCase("Update")) {
		Vector retrn_data = (Vector) itinvsession.getAttribute("ITEM_INVENTORY_DATA");
		Vector synch_data = SynchServerData(request, retrn_data);
		retrn_data = null;

		if (SubUser_Action.equalsIgnoreCase("UpdPage")) {
		 itinvsession.setAttribute("ITEM_INVENTORY_DATA", synch_data);
		 Hashtable updateresults = UpdateDetails(synch_data, personnelid, SubUser_Action,
			forcebuy_invengrps);

		 Boolean resultfromup = (Boolean) updateresults.get("RESULT");
		 Vector errordata = getResult(hubInventoryGroupOvBeanCollection, branch_plant,
			selplantid, selbldgid, invengrp, searchText, minMaxOnly, searchLike,showActiveOnly);
		 itinvsession.setAttribute("ITEM_INVENTORY_DATA", errordata);
		 boolean resulttotest = resultfromup.booleanValue();

		 if (false == resulttotest) {
			buildHeader(hub_list_set, branch_plant, selplantid, selbldgid, invengrp, searchText,
			 sortby, minMaxOnly, searchLike, showActiveOnly, initialData, out);

			if (true == noneToUpd) {
			 out.println(radian.web.HTMLHelpObj.printMessageinTable(
				"No Changes were made to Update. Generating Labels if requested."));
			}
			else {
			 out.println(radian.web.HTMLHelpObj.printMessageinTable(
				"An Error Occurred.<BR>Please Check Data and try Again.<BR>" + errormsg + ""));
			}

			out.println(buildDetails(errordata, User_Action, forcebuy_invengrps,
			 updatezeom_invengrps, branch_plant));
			out.close();
		 }
		 else {
			if (true == completeSuccess) {
			 buildHeader(hub_list_set, branch_plant, selplantid, selbldgid, invengrp,
				searchText, sortby, minMaxOnly, searchLike, showActiveOnly, initialData, out);
			 out.println(radian.web.HTMLHelpObj.printMessageinTable(
				"All Your Selections Were Successfully Updated"));
			 out.println(buildDetails(errordata, User_Action, forcebuy_invengrps,
				updatezeom_invengrps, branch_plant));
			 out.close();
			}
			else {
			 buildHeader(hub_list_set, branch_plant, selplantid, selbldgid, invengrp,
				searchText, sortby, minMaxOnly, searchLike, showActiveOnly, initialData, out);
			 out.println(buildDetails(errordata, User_Action, forcebuy_invengrps,
				updatezeom_invengrps, branch_plant));
			 out.close();
			}
		 }
		 //clean up
		 synch_data = null;
		}
		else {
		 out.println(radian.web.HTMLHelpObj.printError("Inventory",intcmIsApplication));
		}
	 }
	 else {
		out.println(radian.web.HTMLHelpObj.printError("Inventory",intcmIsApplication));
	 }
	}
	catch (Exception e) {
	 e.printStackTrace();
	 out.println(radian.web.HTMLHelpObj.printError("Inventory",intcmIsApplication));
	}
	return;
 }

 private Vector getResult(Collection hubInventoryGroupOvBeanColl, String branchPlant,
	String selplantid, String selbldgid, String inventoryGroup,
	String searchText, String minMaxOnly, String searchLike,String showActiveOnly) throws Exception {
	Vector Data = new Vector();
	Hashtable DataH = new Hashtable();
	Hashtable summary = new Hashtable();
	boolean falgforand = false;
	DBResultSet dbrs = null;
	ResultSet searchRs = null;
	int count = 0;
	summary.put("TOTAL_NUMBER", new Integer(count));
	Data.addElement(summary);

	try {
	 String query = "select * from item_count_inventory_view where ";
	 falgforand = false;

	 if (! (selplantid.trim().length() == 0 || "All Plants".equalsIgnoreCase(selplantid))) {
		if (falgforand)
		 query += " and PLANT_ID = '" + selplantid + "'";
		else
		 query += " PLANT_ID = '" + selplantid + "'";
		falgforand = true;
	 }

	 if (! (selbldgid.trim().length() == 0 || "All Buildings".equalsIgnoreCase(selbldgid))) {
		if (falgforand)
		 query += " and BLDG_ID = '" + selbldgid + "'";
		else
		 query += " BLDG_ID = '" + selbldgid + "' ";
		falgforand = true;
	 }

	 HubInventoryGroupProcess hubInventoryGroupProcess = new
		HubInventoryGroupProcess("hub");
	 boolean iscollection = hubInventoryGroupProcess.isCollection(
		hubInventoryGroupOvBeanColl,
		branchPlant, inventoryGroup);

	 if (inventoryGroup != null && inventoryGroup.length() > 0 &&
		!inventoryGroup.equalsIgnoreCase("ALL")) {
		if (iscollection) {
		 if (falgforand)
			query += "and ";

		 query += " INVENTORY_GROUP in (select INVENTORY_GROUP from INVENTORY_GROUP_COLLECTION where HUB = " +
				 SqlHandler.delimitString(branchPlant) +
			" and INVENTORY_GROUP_COLLECTION = '" + inventoryGroup + "') ";
		}
		else if (inventoryGroup.length() > 0) {
		 if (falgforand)
			query += "and INVENTORY_GROUP ='" + inventoryGroup + "' ";
		 else {
			query += " INVENTORY_GROUP ='" + inventoryGroup + "' ";
		 }
		}
	 }

	 if (searchText.trim().length() > 0) {
		if ("ITEM_ID".equalsIgnoreCase(searchLike)) {
		 if (falgforand) {
			query +=
			 " and (CAT_PART_NO) in (select CAT_PART_NO from item_count_mr_create where ITEM_ID = '" +
			 searchText + "'";
			if (inventoryGroup != null && inventoryGroup.length() > 0 &&
			 !inventoryGroup.equalsIgnoreCase("ALL")) {
        if (iscollection) {
		    query += " and INVENTORY_GROUP in (select INVENTORY_GROUP from INVENTORY_GROUP_COLLECTION where HUB = " +
		    		SqlHandler.delimitString(branchPlant) + " and INVENTORY_GROUP_COLLECTION = '" + inventoryGroup + "')) ";
		    }
        else
        {
        query += " and inventory_group ='" + inventoryGroup + "') ";
        }
      }
			else {
			 query += ")";
			}
		 }
		 else {
			query +=
			 " (CAT_PART_NO) in (select CAT_PART_NO from item_count_mr_create where ITEM_ID = '" +
			 searchText + "'";
			if (inventoryGroup != null && inventoryGroup.length() > 0 &&
			 !inventoryGroup.equalsIgnoreCase("ALL")) {
			 query += " and inventory_group ='" + inventoryGroup + "') ";
			}
			else {
			 query += ")";
			}
		 }
		}
		else if ("CAT_PART_NO".equalsIgnoreCase(searchLike)) {
		 if (falgforand)
			query += " and CAT_PART_NO = '" + searchText + "'";
		 else
			query += " CAT_PART_NO = '" + searchText + "'";
		}

		falgforand = true;
	 }

	 if (searchText.trim().length() == 0) {
		if ("Yes".equalsIgnoreCase(minMaxOnly)) {
		 if (falgforand)
			query += " and STOCKING_METHOD = 'MM'";
		 else
			query += " STOCKING_METHOD = 'MM'";
		 falgforand = true;
		}
	 }

	 if ("Yes".equalsIgnoreCase(showActiveOnly)) {
		if (falgforand)
		 query += " and ICMRC_STATUS = 'A'";
		else
		 query += " ICMRC_STATUS = 'A'";
		falgforand = true;
	 }

	 query += "and BRANCH_PLANT = " + SqlHandler.delimitString(branchPlant) + " ORDER BY INVENTORY_GROUP,CAT_PART_NO,PART_GROUP_NO";
	 dbrs = db.doQuery(query);
	 searchRs = dbrs.getResultSet();

	 /*ResultSetMetaData rsMeta1 = searchRs.getMetaData();
		System.out.println("Finished The Querry\n "+rsMeta1.getColumnCount());
		for(int i =1; i<=rsMeta1.getColumnCount(); i++)
		{
		System.out.println("String "+rsMeta1.getColumnName(i).toString().toLowerCase()+" = hD.get(\""+rsMeta1.getColumnName(i).toString()+"\")==null?\"&nbsp;\":hD.get(\""+rsMeta1.getColumnName(i).toString()+"\").toString();");
		//System.out.println("DataH.put(\""+rsMeta1.getColumnName(i).toString()+"\",searchRs.getString(\""+rsMeta1.getColumnName(i).toString()+"\")==null?\"\":searchRs.getString(\""+rsMeta1.getColumnName(i).toString()+"\"));");
		//System.out.println("//"+rsMeta1.getColumnName(i).toString()+"");
		//System.out.println("DataH.put(\""+rsMeta1.getColumnName(i).toString()+"\",searchRs.getString(\""+rsMeta1.getColumnName(i).toString()+"\")==null?\"\":searchRs.getString(\""+rsMeta1.getColumnName(i).toString()+"\"));");
}*/

	 while (searchRs.next()) {
		DataH = new Hashtable();
		String status = searchRs.getString("STATUS") == null ? "" :
		 searchRs.getString("STATUS");
		String stockingMethod = searchRs.getString("STOCKING_METHOD") == null ? "" :
		 searchRs.getString("STOCKING_METHOD");
		if ("OOR".equalsIgnoreCase(stockingMethod) && !"A".equalsIgnoreCase(status)) {
		 status = "O";
		}

		String on_hand = searchRs.getString("PART_ON_HAND") == null ? "" :
		 searchRs.getString("PART_ON_HAND").trim();
		String in_purchasing = searchRs.getString("PART_IN_PURCHASING") == null ? "" :
		 searchRs.getString("PART_IN_PURCHASING").trim();
		if ("MM".equalsIgnoreCase(stockingMethod) && "D".equalsIgnoreCase(status)) {
		 float onHand = Float.parseFloat(on_hand);
		 float inPurchasing = Float.parseFloat(in_purchasing);
		 if ( (inPurchasing + onHand) == 0) {
			status = "O";
		 }
		}

		if (!"O".equalsIgnoreCase(status)) {
		 DataH.put("INVENTORY_GROUP",
			searchRs.getString("INVENTORY_GROUP") == null ? "" :
			searchRs.getString("INVENTORY_GROUP"));
		 DataH.put("STORAGE_AREA",
			searchRs.getString("STORAGE_AREA") == null ? "" : searchRs.getString("STORAGE_AREA"));
		 DataH.put("ITEM_ID",
			searchRs.getString("ITEM_ID") == null ? "" : searchRs.getString("ITEM_ID"));
		 DataH.put("CATALOG_ID",
			searchRs.getString("CATALOG_ID") == null ? "" : searchRs.getString("CATALOG_ID"));
		 DataH.put("CAT_PART_NO",
			searchRs.getString("CAT_PART_NO") == null ? "" : searchRs.getString("CAT_PART_NO"));
		 DataH.put("PART_GROUP_NO",
			searchRs.getString("PART_GROUP_NO") == null ? "" :
			searchRs.getString("PART_GROUP_NO"));
		 DataH.put("COUNT_UOM",
			searchRs.getString("COUNT_UOM") == null ? "" : searchRs.getString("COUNT_UOM"));
		 DataH.put("ITEM_DESC",
			searchRs.getString("ITEM_DESC") == null ? "" : searchRs.getString("ITEM_DESC"));
		 DataH.put("PACKAGING",
			searchRs.getString("PACKAGING") == null ? "" : searchRs.getString("PACKAGING"));
		 DataH.put("APPLICATION_DESC",
			searchRs.getString("APPLICATION_DESC") == null ? "" :
			searchRs.getString("APPLICATION_DESC"));
		 DataH.put("COMPANY_ID",
			searchRs.getString("COMPANY_ID") == null ? "" : searchRs.getString("COMPANY_ID"));
		 DataH.put("FACILITY_ID",
			searchRs.getString("FACILITY_ID") == null ? "" : searchRs.getString("FACILITY_ID"));
		 DataH.put("APPLICATION",
			searchRs.getString("APPLICATION") == null ? "" : searchRs.getString("APPLICATION"));
		 DataH.put("PART_ON_HAND", on_hand);
		 DataH.put("PART_IN_PURCHASING", in_purchasing);
		 DataH.put("AREA_ID",
			searchRs.getString("AREA_ID") == null ? "" : searchRs.getString("AREA_ID"));
		 DataH.put("PLANT_ID",
			searchRs.getString("PLANT_ID") == null ? "" : searchRs.getString("PLANT_ID"));
		 DataH.put("BLDG_ID",
			searchRs.getString("BLDG_ID") == null ? "" : searchRs.getString("BLDG_ID"));
		 DataH.put("DEPT_ID",
			searchRs.getString("DEPT_ID") == null ? "" : searchRs.getString("DEPT_ID"));
		 DataH.put("PROCESS_ID",
			searchRs.getString("PROCESS_ID") == null ? "" : searchRs.getString("PROCESS_ID"));
		 DataH.put("ITEM_TYPE",
			searchRs.getString("ITEM_TYPE") == null ? "" : searchRs.getString("ITEM_TYPE"));
		 DataH.put("RECEIPT_PROCESSING_METHOD",
			searchRs.getString("RECEIPT_PROCESSING_METHOD") == null ? "" :
			searchRs.getString("RECEIPT_PROCESSING_METHOD"));
		 DataH.put("STOCKING_METHOD", stockingMethod);
		 DataH.put("STATUS", status);
		 DataH.put("RECEIPT_PROCESSING_METHOD_DESC",
			searchRs.getString("RECEIPT_PROCESSING_METHOD_DESC") == null ? "" :
			searchRs.getString("RECEIPT_PROCESSING_METHOD_DESC"));
		 DataH.put("INVENTORY_GROUP_NAME",
			searchRs.getString("INVENTORY_GROUP_NAME") == null ? "" :
			searchRs.getString("INVENTORY_GROUP_NAME"));
		 DataH.put("ALLOW_FORCE_BUY",
			searchRs.getString("ALLOW_FORCE_BUY") == null ? "" :
			searchRs.getString("ALLOW_FORCE_BUY"));
		 DataH.put("TANK_NAME",
			searchRs.getString("TANK_NAME") == null ? "" : searchRs.getString("TANK_NAME"));
		 DataH.put("REORDER_POINT",
			searchRs.getString("REORDER_POINT") == null ? "0" :
			searchRs.getString("REORDER_POINT"));
		 DataH.put("STOCKING_LEVEL",
			searchRs.getString("STOCKING_LEVEL") == null ? "0" :
			searchRs.getString("STOCKING_LEVEL"));
		 DataH.put("REORDER_QUANTITY",
			searchRs.getString("REORDER_QUANTITY") == null ? "0" :
			searchRs.getString("REORDER_QUANTITY"));
		 DataH.put("LAST_COUNT_DATE",
			searchRs.getDate("LAST_COUNT_DATE") == null ? new Date() :
			searchRs.getDate("LAST_COUNT_DATE"));
		 DataH.put("ITEM_PACKAGING",
			searchRs.getString("ITEM_PACKAGING") == null ? "" :
			searchRs.getString("ITEM_PACKAGING"));
		 DataH.put("ITEM_ON_HAND",
			searchRs.getString("ITEM_ON_HAND") == null ? "" : searchRs.getString("ITEM_ON_HAND"));
		 DataH.put("ITEM_IN_PURCHASING",
			searchRs.getString("ITEM_IN_PURCHASING") == null ? "" :
			searchRs.getString("ITEM_IN_PURCHASING"));
		 DataH.put("BILLING_METHOD",
			searchRs.getString("BILLING_METHOD") == null ? "" :
			searchRs.getString("BILLING_METHOD"));
		 DataH.put("ICMRC_STATUS",
			searchRs.getString("ICMRC_STATUS") == null ? "" : searchRs.getString("ICMRC_STATUS"));
		 DataH.put("FORCE_BUY_QTY", "");
		 DataH.put("NEED_DATE", "");
		 DataH.put("DELETE", "N");
		 DataH.put("UPDATEINVTYPE", "N");
		 DataH.put("USERCHK", "");
     DataH.put("COMMENTS", "");

     Data.addElement(DataH);
		 count++;
		}
	 }
	}
	catch (Exception e) {
	 e.printStackTrace();
	 throw e;
	}
	finally {
	 if (dbrs != null) {
		dbrs.close();
	 }
	}

	Hashtable recsum = new Hashtable();
	recsum.put("TOTAL_NUMBER", new Integer(count));
	Data.setElementAt(recsum, 0);

	return Data;
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

		String item_id = "";
		item_id = request.getParameter( ("item_id" + i));
		if (item_id == null)
		 item_id = "";

		String invengroup = "";
		invengroup = request.getParameter( ("inventory_group" + i));
		if (invengroup == null)
		 invengroup = "";

		if (item_id.equalsIgnoreCase(hD.get("ITEM_ID").toString()) &&
		 invengroup.equalsIgnoreCase(hD.get("INVENTORY_GROUP").toString())) {
		 /*String check = "";
		 check = request.getParameter( ("row_chk" + i));
		 if (check == null)
			check = "";
		 hD.remove("DELETE");
		 hD.put("DELETE", check);*/

		 String mainrow_chk = "";
		 mainrow_chk = request.getParameter( ("mainrow_chk" + i));
		 if (mainrow_chk == null)
			mainrow_chk = "";
		 hD.remove("USERCHK");
		 hD.put("USERCHK", mainrow_chk);

		 String buy_qty = "";
		 buy_qty = request.getParameter( ("buy_qty" + i));
		 if (buy_qty == null)
			buy_qty = "";
		 hD.remove("FORCE_BUY_QTY");
		 hD.put("FORCE_BUY_QTY", buy_qty);

		 String needDate = "";
		 needDate = request.getParameter( ("needDate" + i));
		 if (needDate == null)
			needDate = "";
		 hD.remove("NEED_DATE");
		 hD.put("NEED_DATE", needDate);

		 String comments = "";
		 comments = request.getParameter( ("comments" + i));
		 if (comments == null)
			comments = "";
		 hD.remove("COMMENTS");
		 hD.put("COMMENTS", comments);
                
     /*String operatingMethod = "";
		 operatingMethod = request.getParameter( ("operatingMethod" + i));
		 if (operatingMethod == null)
			operatingMethod = "";
		 if (operatingMethod.length() == 0)
			operatingMethod = "";

		 String origOperatingMethod = hD.get("RECEIPT_PROCESSING_METHOD") == null ? "" :
			hD.get("RECEIPT_PROCESSING_METHOD").toString();

		 if (! (origOperatingMethod.equalsIgnoreCase(operatingMethod))) {
			hD.remove("RECEIPT_PROCESSING_METHOD");
			hD.put("RECEIPT_PROCESSING_METHOD", operatingMethod);

			hD.remove("UPDATEINVTYPE");
			hD.put("UPDATEINVTYPE", "Yes");
		 }*/

		 String icmrcStatus = "";
		 icmrcStatus = request.getParameter( ("icmrcStatus" + i));
		 if (icmrcStatus == null)
			icmrcStatus = "";

		 String storedIcmrcStatus = hD.get("ICMRC_STATUS") == null ? "" :
		 hD.get("ICMRC_STATUS").toString();
     //invlog.debug( "storedIcmrcStatus  "+storedIcmrcStatus+" icmrcStatus  "+icmrcStatus+"");
		 if (!storedIcmrcStatus.equalsIgnoreCase(icmrcStatus)) {
			hD.remove("UPDATEINVTYPE");
			hD.put("UPDATEINVTYPE", "Yes");

			hD.remove("ICMRC_STATUS");
			hD.put("ICMRC_STATUS", icmrcStatus);
		 }

		}
		new_data.addElement(hD);
	 }
	}
	catch (Exception e) {
	 e.printStackTrace();
	}
	return new_data;
 }

 private Hashtable UpdateDetails(Vector data, String logonid, String subuseraction,
	Vector allowedingrps) {
	boolean result = false;
	Hashtable updateresult = new Hashtable();
	Vector errordata = new Vector();

	try {
	 Hashtable summary = new Hashtable();
	 summary = (Hashtable) data.elementAt(0);
	 int total = ( (Integer) (summary.get("TOTAL_NUMBER"))).intValue();
	 errordata.addElement(summary);
	 boolean one_success = false;

	 for (int i = 1; i < total + 1; i++) {
		Hashtable hD = new Hashtable();
		hD = (Hashtable) data.elementAt(i);

		String updateitemprop = (hD.get("UPDATEINVTYPE") == null ? " " :
		 hD.get("UPDATEINVTYPE").toString());
		//String deleteiteminv = (hD.get("DELETE") == null ? "" : hD.get("DELETE").toString());
		String forcebuyqty = (hD.get("FORCE_BUY_QTY") == null ? " " :
		 hD.get("FORCE_BUY_QTY").toString());
		String usercheck = (hD.get("USERCHK") == null ? " " : hD.get("USERCHK").toString());
		String icmrcStatus = hD.get("ICMRC_STATUS") == null ? "" : hD.get("ICMRC_STATUS").toString();

		if ( ("Yes".equalsIgnoreCase(updateitemprop)) ||
		 (forcebuyqty.length() > 0 || "Y".equalsIgnoreCase(usercheck))) {
		 //boolean statusResult = false;
		 boolean line_result = false;
		 noneToUpd = false;

		 if ( "Yes".equalsIgnoreCase( updateitemprop ) && icmrcStatus.length() > 0)
		 {
			line_result=UpdateIcmcStatus( hD,logonid );
		 }

		 if (forcebuyqty.length() > 0 || "Y".equalsIgnoreCase(usercheck)) {
			line_result=forceabuy(hD, logonid);
		 }

		 if (!line_result) {
			completeSuccess = false;

			hD.remove("LINE_STATUS");
			hD.put("LINE_STATUS", "NO");

			errordata.addElement(hD);
		 }
		 else {
			one_success = true;
			hD.remove("LINE_STATUS");
			hD.put("LINE_STATUS", "YES");

			errordata.addElement(hD);
		 }
		}
		else {
		 errordata.addElement(hD);
		}
	 }

	 if (true == one_success) {
		result = true;
	 }
	}
	catch (Exception e) {
	 result = false;
	 e.printStackTrace();
	}

	updateresult.put("RESULT", new Boolean(result));
	updateresult.put("ERRORDATA", errordata);

	return updateresult;
 }

 public boolean UpdateIcmcStatus( Hashtable hD,String personnelId )
 {
	 boolean result=false;
	 String inventory_group=hD.get( "INVENTORY_GROUP" ) == null ? "" : hD.get( "INVENTORY_GROUP" ).toString();
	 String item_id=hD.get( "ITEM_ID" ) == null ? "" : hD.get( "ITEM_ID" ).toString();
	 String icmrcStatus = hD.get("ICMRC_STATUS") == null ? "" : hD.get("ICMRC_STATUS").toString();
	 //invlog.info( "bill_on_receipt  "+bill_on_receipt+" issue_on_receipt  "+issue_on_receipt+"");
	 CallableStatement cs=null;
	 try
	 {
	 Connection connect1=null;
	 connect1=db.getConnection();
	 cs=connect1.prepareCall( "{call PR_ITEM_COUNT_MR_CREATE_UPDATE(?,?,?)}" );
	 cs.setInt( 1,Integer.parseInt( item_id ) );
	 cs.setString( 2,inventory_group );
	 cs.setString( 3,icmrcStatus );

	 //cs.registerOutParameter( 7,java.sql.Types.VARCHAR );
	 cs.execute();
	 //String errorcode=cs.getString( 7 );
	 //invlog.info( "Result from PR_ITEM_COUNT_MR_CREATE_UPDATE procedure Error Code:  " + errorcode + "  item_id " + item_id + "  inventory_group " + inventory_group + "  deleteline " + deleteline+ "  operatingMethod " + operatingMethod+ "  deleteline " + deleteline + "" );
	 //if ( errorcode == null )
	 {
		result=true;
         }
	 /*else
	 {
		//errormsg+=errorcode + "<BR>";
		result=false;
         }*/
	 }
	 catch ( Exception e )
	 {
	 e.printStackTrace();
	 radian.web.emailHelpObj.senddatabaseHelpemails("Error Calling PR_ITEM_COUNT_MR_CREATE_UPDATE in item inventory","Error occured while running PR_ITEM_COUNT_MR_CREATE_UPDATE \nError Msg:\n" + e.getMessage() + "\nFor Input Parameters \nitem_id " + item_id + "  inventory_group " + inventory_group + "  icmrcStatus " + icmrcStatus+ " personnelId "+personnelId+"" );
	 result=false;
	}
	 finally
	 {
	 if ( cs != null )
	 {
	try
	{
	 cs.close();
	}
	catch ( SQLException se )
	{}
	}
	}
	 return result;
 }

 public boolean forceabuy(Hashtable hD, String personnelID) {
	boolean result = false;
	String inventory_group = hD.get("INVENTORY_GROUP") == null ? "" :
	 hD.get("INVENTORY_GROUP").toString();
	String item_id = hD.get("ITEM_ID") == null ? "" : hD.get("ITEM_ID").toString();
	String forcebuyqty = hD.get("FORCE_BUY_QTY") == null ? "" :
	 hD.get("FORCE_BUY_QTY").toString();
	String catalog_id = hD.get("CATALOG_ID") == null ? "" : hD.get("CATALOG_ID").toString();
	String cat_part_no = hD.get("CAT_PART_NO") == null ? "" :
	 hD.get("CAT_PART_NO").toString();
	String part_group_no = hD.get("PART_GROUP_NO") == null ? "" :
	 hD.get("PART_GROUP_NO").toString();
	String company_id = hD.get("COMPANY_ID") == null ? "" : hD.get("COMPANY_ID").toString();
	String facility_id = hD.get("FACILITY_ID") == null ? "" :
	 hD.get("FACILITY_ID").toString();
	String application = hD.get("APPLICATION") == null ? "" :
	 hD.get("APPLICATION").toString();
	String needDate = hD.get("NEED_DATE") == null ? "" : hD.get("NEED_DATE").toString();
  String comments = hD.get("COMMENTS") == null ? "" : hD.get("COMMENTS").toString();

	CallableStatement cs = null;
	try {
	 Connection connect1 = null;
	 connect1 = db.getConnection();

	 cs = connect1.prepareCall("{call p_force_buy_item_inventory(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
	 cs.setInt(1, Integer.parseInt(item_id));
	 cs.setString(2, inventory_group);
	 cs.setInt(3, Integer.parseInt(forcebuyqty));
	 cs.setString(4, catalog_id);
	 cs.setString(5, cat_part_no);
	 cs.setInt(6, Integer.parseInt(part_group_no));
	 cs.setString(7, facility_id);
	 cs.setString(8, application);
	 cs.setString(9, company_id);
	 cs.setInt(10, Integer.parseInt(personnelID));
	 if (needDate.length() == 10) {
		cs.setTimestamp(11, radian.web.HTMLHelpObj.getDateFromString("", needDate));
	 }
	 else {
		cs.setNull(11, java.sql.Types.DATE);
	 }

	 cs.registerOutParameter(12, java.sql.Types.VARCHAR);
   if (comments.length() > 0) {
		cs.setString(13, comments);
	 }
   else
   {
     cs.setNull(13, java.sql.Types.VARCHAR);
   }
   cs.execute();

	 String errorcode = cs.getString(12);
	 invlog.info("Result from p_force_buy_item_inventory procedure Error Code:  " +
		errorcode + "  item_id " + item_id + "  inventory_group " + inventory_group +
		"  forcebuyqty " + forcebuyqty + "  needDate " + needDate + " Personnel Id "+personnelID+"");

	 if (errorcode == null) {
		result = true;
	 }
	 else {
		errormsg += errorcode + "<BR>";
		result = false;
	 }
	}
	catch (Exception e) {
	 e.printStackTrace();
	 radian.web.emailHelpObj.senddatabaseHelpemails(
		"Error Calling p_force_buy_item_inventory in item inventory",
		"Error occured while running p_force_buy_item_inventory \nError Msg:\n" +
		e.getMessage() + "\nFor Input Parameters \nitem_id " + item_id + "  inventory_group " +
		inventory_group + "  forcebuyqty " + forcebuyqty + " Personnel Id "+personnelID+"");
	 result = false;
	}
	finally {
	 if (cs != null) {
		try {
		 cs.close();
		}
		catch (SQLException se) {}
	 }
	}

	return result;
 }

 private void buildHeader(Hashtable hub_list_set, String selectedHub, String selplantid,
	String selbuilding, String invengrp, String searchText, String sortby,
	String minMaxOnly, String searchLike, String showActiveOnly, Hashtable initialData,
	PrintWriter imout) {
	try {
	 imout.println(radian.web.HTMLHelpObj.printHTMLHeader("Item Management","iteminventory.js",intcmIsApplication));
	 imout.println(
		"<SCRIPT SRC=\"/clientscripts/FormChek.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
	 //imout.println("<SCRIPT SRC=\"/clientscripts/calendar.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
	 imout.println("<SCRIPT SRC=\"/js/calendar/newcalendar.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
	 imout.println("<SCRIPT SRC=\"/js/calendar/AnchorPosition.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
	 imout.println("<SCRIPT SRC=\"/js/calendar/PopupWindow.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");

	 imout.println("</HEAD>  \n");
	 imout.println("<BODY>\n");

	 imout.println("<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n");
	 imout.println("<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>Please wait while the page is updated...</B></FONT></center>\n");
	 imout.println("</DIV>\n");
	 imout.println("<DIV ID=\"MAINPAGE\" STYLE=\"\">\n");

	 imout.println(radian.web.HTMLHelpObj.PrintTitleBar("<B>Item Management</B>\n"));
	 Hashtable hub_list = (Hashtable) (hub_list_set.get("HUB_LIST"));
	 if (hub_list.size() < 1) {
		imout.println(radian.web.HTMLHelpObj.printMessageinTable("<BR><BR>You don't have access to this page.<BR><BR>Contact tech support for more information."));
		return;
	 }

	 Vector hubID = (Vector) initialData.get("HUB_ID");
	 if (hubID.size() == 0) {
		imout.println(radian.web.HTMLHelpObj.printMessageinTable("<BR><BR>There are no Items to Manage in the hubs you have access to.<BR><BR>Contact tech support for more information."));
		return;
	 }

	 int i = 0;
	 if (selectedHub.trim().length() == 0) {
		selectedHub = (String) hubID.firstElement(); //select the first facility if none were selected
	 }

	 imout.println("<SCRIPT LANGUAGE=\"JavaScript1.2\" TYPE=\"text/javascript\">\n <!--\n");
	 imout.println(radian.web.dropdwnHelpObj.createComboBoxData(initialData));
	 imout.println("// -->\n </SCRIPT>\n");

	 imout.println("<FORM method=\"POST\" NAME=\"iteminventory\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\"" +
		radian.web.tcmisResourceLoader.getiteminvenurl() + "\">\n");
	 imout.println("<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"100%\" CLASS=\"moveup\">\n");
	 imout.println("<TR VALIGN=\"MIDDLE\">\n");

	 //Hub
	 imout.println("<TD WIDTH=\"5%\" HEIGHT=\"35\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
	 imout.println("<B>Program:</B>\n");
	 imout.println("</TD>\n");
	 imout.println("<TD WIDTH=\"10%\">\n");
	 imout.println("<SELECT CLASS=\"HEADER\" NAME=\"hubC\" ID=\"hubC\" size=\"1\" OnChange=\"hubChanged(document.iteminventory.hubC)\">\n");
	 Vector hubDesc = (Vector) initialData.get("HUB_DESC");
	 String preSelect = "";
	 for (i = 0; i < hubID.size(); i++) {
		preSelect = "";
		if (selectedHub.equalsIgnoreCase( (String) hubID.elementAt(i))) {
		 preSelect = "selected";
		 selectedHub = (String) hubID.elementAt(i);
		}
		imout.println("<option " + preSelect + " value=\"" + (String) hubID.elementAt(i) +
		 "\">" + (String) hubDesc.elementAt(i) + "</option>\n");
	 }
     String selectedHubName = ( String ) ( hub_list.get( selectedHub ) );
	 imout.println("</SELECT>\n");
	 imout.println("</TD>\n");

	 imout.println("<TD WIDTH=\"25%\" HEIGHT=\"35\" CLASS=\"announce\" ALIGN=\"LEFT\">\n");
	 imout.println("&nbsp;</TD>\n");

	 imout.println("<TD WIDTH=\"5%\" HEIGHT=\"35\" CLASS=\"announce\" ALIGN=\"LEFT\">\n");
	 imout.println("&nbsp;</TD>\n");

	 imout.println("</TR>\n");

	 imout.println("<TR VALIGN=\"MIDDLE\">\n");
	 imout.println(
		"<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n");
	 imout.println("<B>Plant:</B>\n");
	 imout.println("</TD>\n");
	 imout.println("<TD WIDTH=\"10%\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\">\n");
	 imout.println("<SELECT CLASS=\"HEADER\" NAME=\"facilityName\" ID=\"facId\" size=\"1\" OnChange=\"facilityChanged(document.iteminventory.facilityName)\">\n");
	 Hashtable fh = (Hashtable) initialData.get(selectedHub);

	 Vector facID = (Vector) fh.get("PLANT_ID");
	 Vector facDesc = (Vector) fh.get("PLANT_DESC");
	 i = 0;
	 if (selplantid.trim().length() == 0) {
		selplantid = (String) facID.firstElement(); //select the first facility if none were selected
	 }
	 preSelect = "";
	 for (i = 0; i < facID.size(); i++) {
		preSelect = "";
		if (selplantid.equalsIgnoreCase( (String) facID.elementAt(i))) {
		 preSelect = "selected";
		 selplantid = (String) facID.elementAt(i);
		}
		imout.println("<option " + preSelect + " value=\"" + (String) facID.elementAt(i) +
		 "\">" + (String) facDesc.elementAt(i) + "</option>\n");
	 }
	 imout.println("</SELECT>\n");
	 imout.println("</TD>\n");

	 imout.println("<TD WIDTH=\"25%\" HEIGHT=\"35\" CLASS=\"announce\" ALIGN=\"LEFT\">\n");
	 imout.println("&nbsp;</TD>\n");

	 // Search
	 imout.println("<TD WIDTH=\"5%\" HEIGHT=\"35\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
	 imout.println("   <INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Search\" onclick= \"return search(this)\" NAME=\"SearchButton\">&nbsp;\n");
	 imout.println("</TD>\n");
	 imout.println("</TR>\n");

	 imout.println("<TR VALIGN=\"MIDDLE\">\n");
	 // Building
	 imout.println("<TD WIDTH=\"5%\" HEIGHT=\"35\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
	 imout.println("<B>Building:</B>\n");
	 imout.println("</TD>\n");

	 imout.println("<TD WIDTH=\"10%\" ALIGN=\"left\" CLASS=\"announce\">\n");
	 imout.println("<SELECT CLASS=\"HEADER\" NAME=\"workAreaName\" ID=\"waName\" size=\"1\" OnChange=\"workareaChanged()\">\n");
	 Hashtable h = (Hashtable) fh.get(selplantid);
	 Vector application = (Vector) h.get("BLDG");
	 Vector applicationDesc = (Vector) h.get("BLDG_DESC");
	 for (i = 0; i < application.size(); i++) {
		preSelect = "";
		if (selbuilding.equalsIgnoreCase( (String) application.elementAt(i))) {
		 preSelect = "selected";
		}
		imout.println("<option " + preSelect + " value=\"" + (String) application.elementAt(i) +
		 "\">" + (String) application.elementAt(i) + "</option>\n");
	 }
	 imout.println("</SELECT>\n");
	 imout.println("</TD>\n");
	 imout.println("<TD WIDTH=\"25%\" HEIGHT=\"35\" CLASS=\"announce\" ALIGN=\"LEFT\">\n");
	 imout.println("&nbsp;</TD>\n");

	 //Update
	 imout.println("<TD WIDTH=\"5%\" HEIGHT=\"35\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
	 if (this.getupdateStatus()) {
		imout.println("<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Update\" onclick= \"return update(this)\" NAME=\"UpdateButton\">&nbsp;\n");
		//imout.println("<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"OTB\" onclick= \"onetimebuy(this)\" NAME=\"otbButton\">&nbsp;\n");
	 }
	 else {
		imout.println("&nbsp;\n");
	 }
	 imout.println("</TD>\n");
	 imout.println("</TR>\n");

	 imout.println("<TR VALIGN=\"MIDDLE\">\n");
	 // Inventory Group
	 imout.println("<TD WIDTH=\"5%\" HEIGHT=\"35\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
	 imout.println("<B>Storage:</B>\n");
	 imout.println("</TD>\n");
	 imout.println("<TD WIDTH=\"10%\">\n");

	 imout.println("<SELECT CLASS=\"HEADER\"  NAME=\"invengrp\" size=\"1\" OnChange=\"getbuildingaddress()\">\n");
	 imout.println("<OPTION VALUE=\"All\">All Storage Areas</OPTION>\n");

	 if (! (selplantid.trim().length() == 0 || "All Plants".equalsIgnoreCase(selplantid))) {
		DBResultSet dbrs = null;
		ResultSet rs = null;
		try {
		 dbrs = db.doQuery("Select distinct INVENTORY_GROUP,STORAGE_AREA from item_inventory_header_view where lower(PREFERRED_WAREHOUSE) = lower('"+selectedHubName+"') and lower(PLANT_ID) = lower('" +
			selplantid + "') and lower(BLDG_ID) = lower('" + selbuilding + "') ");
		 rs = dbrs.getResultSet();

		 while (rs.next()) {
			String invgrp = rs.getString("INVENTORY_GROUP") == null ? "" :
			 rs.getString("INVENTORY_GROUP").trim();
			String invgrpname = rs.getString("STORAGE_AREA") == null ? "" :
			 rs.getString("STORAGE_AREA").trim();
			preSelect = "";
			if (invengrp.equalsIgnoreCase(invgrp)) {
			 preSelect = "selected";
			}
			imout.println("<option " + preSelect + " value=\"" + invgrp + "\">" + invgrpname +
			 "</option>\n");
		 }
		}
		catch (Exception e) {
		 e.printStackTrace();
		}
		finally {
		 if (dbrs != null) {
			dbrs.close();
		 }
		}
	 }
	 imout.println("</SELECT>\n");
	 imout.println("</TD>\n");

	 imout.println("<TD WIDTH=\"25%\" HEIGHT=\"35\" CLASS=\"announce\" ID=\"buildingaddress\" ALIGN=\"LEFT\">\n");
	 if (! (invengrp.trim().length() == 0 || "All".equalsIgnoreCase(invengrp))) {
		DBResultSet dbrs = null;
		ResultSet rs = null;

		try {
		 dbrs = db.doQuery("Select distinct LOCATION_DESC || ',  Attn:' || ATTENTION || ',  ' || ADDRESS_LINE_1 || ',  ' || ADDRESS_LINE_2 || ',  ' || ADDRESS_LINE_3 || ',  ' || CITY || ',  ' || STATE_ABBREV || '-' || ZIP || ',  ' || COUNTRY_ABBREV ADDRESS from item_inventory_header_view where lower(PLANT_ID) = lower('" +
			selplantid + "') and lower(BLDG_ID) = lower('" + selbuilding +
			"') and lower(INVENTORY_GROUP) = lower('" + invengrp + "') ");
		 rs = dbrs.getResultSet();
		 String buildgaddressline = "";
		 while (rs.next()) {
			buildgaddressline = rs.getString("ADDRESS") == null ? "" :
			 rs.getString("ADDRESS").trim();
		 }
		 imout.println(buildgaddressline);
		}
		catch (Exception e) {
		 e.printStackTrace();
		}
		finally {
		 if (dbrs != null) {
			dbrs.close();
		 }
		}
	 }
	 imout.println("&nbsp;</TD>\n");

	 //Show Active Only
	 imout.println("<TD WIDTH=\"10%\" HEIGHT=\"35\" CLASS=\"announce\" ALIGN=\"LEFT\">\n");
	 imout.println("<INPUT type=\"checkbox\" name=\"showActiveOnly\" value=\"Yes\" " +
		(showActiveOnly.equalsIgnoreCase("Yes") ? "checked" : "") + ">\n");
	 imout.println("&nbsp;&nbsp;Show Active Only</TD>\n");

	 imout.println("</TR>\n");
	 imout.println("<TR VALIGN=\"MIDDLE\">\n");

	 imout.println("<TD WIDTH=\"5%\" HEIGHT=\"35\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
	 imout.println("<B>Search:</B>&nbsp;\n");
	 imout.println("</TD>\n");

	 imout.println("<TD WIDTH=\"10%\" HEIGHT=\"35\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
	 imout.println("<SELECT CLASS=\"HEADER\"  NAME=\"searchlike\" size=\"1\">\n");
	 Hashtable searchthese = new Hashtable();
	 searchthese.put("Part Number", "CAT_PART_NO");
	 searchthese.put("Item Id", "ITEM_ID");
	 imout.println(radian.web.HTMLHelpObj.sorthashbyValueshowkey(searchthese.entrySet(),
		searchLike));
	 imout.println("</SELECT>\n");
	 imout.println("</TD>\n");

	 imout.println("<TD WIDTH=\"10%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
	 imout.println(
		"<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"searchText\" ID=\"searchText\" value=\"" +
		searchText + "\" size=\"8\" OnChange=\"searchTextChanged()\">\n");
	 imout.println("<BUTTON CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchitemidlike\" value=\"...\" OnClick=\"getitemId(this.form)\"><IMG src=\"/images/search_small.gif\" alt=\"Item ID\"></BUTTON>\n");
	 imout.println("</TD>\n");

	 imout.println("<TD WIDTH=\"10%\" HEIGHT=\"35\" CLASS=\"announce\" ALIGN=\"LEFT\">\n");
	 imout.println("<INPUT type=\"checkbox\" name=\"minMaxOnly\" value=\"Yes\" " +
		(minMaxOnly.equalsIgnoreCase("Yes") ? "checked" : "") + " OnChange=\"searchTextChanged()\">\n");
	 imout.println("&nbsp;&nbsp;Show Min/Max Only</TD>\n");
	 imout.println("</TR>\n");
	 imout.println("</TABLE>\n");

	 imout.println(
		"<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\" CLASS=\"moveup\">\n");
	 imout.println("<tr><td>");
	 imout.println("<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\"NEW\">\n");
	 imout.println("<INPUT TYPE=\"hidden\" NAME=\"SubUserAction\" VALUE=\" \">\n");
     imout.println("<INPUT TYPE=\"hidden\" NAME=\"thedecidingRandomNumber\" VALUE=\"" +
		thedecidingRandomNumber + "\">\n");
	 imout.println("</TD></tr>");
	 imout.println("</table>\n");
	 imout.println( "<BR><FONT SIZE=\"3\" FACE=\"Arial\" COLOR=\"#fc0303\"><B><A HREF=\"javascript:showItemManagementLegend()\" STYLE=\"color:#0000ff;cursor:hand\">Legend</A></B></FONT><BR>\n" );
	}
	catch (Exception e) {
	 e.printStackTrace();
	}
 }

//end of method

 private String buildDetails(Vector data, String SubuserAction, Vector allowforforcebuy,
	Vector allowforzeoatm, String hubname) {
	StringBuffer Msg = new StringBuffer();
	StringBuffer MsgTmp1 = new StringBuffer();
	Hashtable sortresult=new Hashtable();
	sortresult.put( "Active","A" );
	sortresult.put( "Inactive","I" );

	try {
	 Hashtable summary = new Hashtable();
	 summary = (Hashtable) data.elementAt(0);
	 int total = ( (Integer) (summary.get("TOTAL_NUMBER"))).intValue();
	 Msg.append("<TABLE  BORDER=\"0\" BGCOLOR=\"#a2a2a2\" CELLSPACING=1 CELLPADDING=2 WIDTH=\"100%\" CLASS=\"moveup\">\n");

	 int lineadded = 0;
	 int lastItemNum = 1;
	 int ItemIdCount = 0;
	 String Color = "CLASS=\"white";
	 String childColor = "CLASS=\"white";
	 String invcolor = "CLASS=\"INVISIBLEHEADWHITE";

	 int i = 0; //used for detail lines
	 for (int loop = 0; loop < total; loop++) {
		i++;
		boolean createHdr = false;

		if (loop % 10 == 0 && lastItemNum == 1) {
		 createHdr = true;
		}

		if (createHdr) {
		 Msg.append("<tr align=\"center\">\n");
		 Msg.append("<TH width=\"2%\"  height=\"38\">Ok</TH>\n");
		 Msg.append("<TH width=\"5%\"  height=\"38\">Inventory Group</TH>\n");
		 Msg.append("<TH width=\"5%\"  height=\"38\">Catalog Part Number</TH>\n");
		 Msg.append("<TH width=\"5%\"  height=\"38\">Set Points<BR>RP/SL/RQ</TH>\n");
		 Msg.append("<TH width=\"5%\"  height=\"38\">Receipt Processing Method</TH>\n");
		 Msg.append("<TH width=\"5%\"  height=\"38\">Billing Method</TH>\n");
		 Msg.append("<TH width=\"5%\"  height=\"38\">Stocking Method</TH>\n");
		 Msg.append("<TH width=\"5%\"  height=\"38\">Usage Facility</TH>\n");
		 Msg.append("<TH width=\"5%\"  height=\"38\">Usage Work Area</TH>\n");
		 Msg.append("<TH width=\"5%\"  height=\"38\">Count UOM</TH>\n");
		 Msg.append("<TH width=\"5%\"  height=\"38\">Inventory UOM</TH>\n");
		 Msg.append("<TH width=\"5%\"  height=\"38\">On Hand<BR>(Last Counted)</TH>\n");
		 Msg.append("<TH width=\"5%\"  height=\"38\">In Purchasing</TH>\n");
		 Msg.append("<TH width=\"5%\"  height=\"38\">Item</TH>\n");
		 Msg.append("<TH width=\"20%\" height=\"38\">Item Description</TH>\n");
		 Msg.append("<TH width=\"5%\"  height=\"38\">Replenish Qty</TH>\n");
		 Msg.append("<TH width=\"5%\"  height=\"38\">Need Date mm/dd/yyyy</TH>\n");
     Msg.append("<TH width=\"5%\"  height=\"38\">Comments</TH>\n");
     Msg.append("<TH width=\"5%\"  height=\"38\">Status</TH>\n");
		 Msg.append("</tr>\n");
		}

		Hashtable hD = new Hashtable();
		hD = (Hashtable) data.elementAt(i);

		String nextCatPartNo = "";
		String nextInventoryGroup = "";
		String nextPartGroupNumber = "";
		if (! (i == total)) {
		 Hashtable hDNext = new Hashtable();
		 hDNext = (Hashtable) data.elementAt(i + 1);
		 nextCatPartNo = hDNext.get("CAT_PART_NO") == null ? "" :
			hDNext.get("CAT_PART_NO").toString();
		 nextInventoryGroup = hDNext.get("INVENTORY_GROUP") == null ? "" :
			hDNext.get("INVENTORY_GROUP").toString();
		 nextPartGroupNumber = hDNext.get("PART_GROUP_NO") == null ? "" :
			hDNext.get("PART_GROUP_NO").toString();
		}
		else {
		 nextCatPartNo = "";
		 nextInventoryGroup = "";
		 nextPartGroupNumber = "";
		}

		String inventoryGroup = hD.get("INVENTORY_GROUP") == null ? "&nbsp;" :
		 hD.get("INVENTORY_GROUP").toString();
		String inventoryGroupName = hD.get("INVENTORY_GROUP_NAME") == null ? "&nbsp;" :
		 hD.get("INVENTORY_GROUP_NAME").toString();
		String itemId = hD.get("ITEM_ID") == null ? "&nbsp;" : hD.get("ITEM_ID").toString();
		String catPartNo = hD.get("CAT_PART_NO") == null ? "&nbsp;" :
		 hD.get("CAT_PART_NO").toString();
		String count_uom = hD.get("COUNT_UOM") == null ? "&nbsp;" :
		 hD.get("COUNT_UOM").toString();
		String itemDesc = hD.get("ITEM_DESC") == null ? "&nbsp;" :
		 hD.get("ITEM_DESC").toString();
		String packaging = hD.get("PACKAGING") == null ? "&nbsp;" :
		 hD.get("PACKAGING").toString();
		String usageWorkArea = hD.get("APPLICATION_DESC") == null ? "&nbsp;" :
		 hD.get("APPLICATION_DESC").toString();
		String usageFacilityId = hD.get("FACILITY_ID") == null ? "&nbsp;" :
		 hD.get("FACILITY_ID").toString();
		String partOnHand = hD.get("PART_ON_HAND") == null ? "&nbsp;" :
		 hD.get("PART_ON_HAND").toString();
		String partInPurchasing = hD.get("PART_IN_PURCHASING") == null ? "&nbsp;" :
		 hD.get("PART_IN_PURCHASING").toString();
		String LINE_STATUS = hD.get("LINE_STATUS") == null ? "&nbsp;" :
		 hD.get("LINE_STATUS").toString();
		String forcebuyqty = hD.get("FORCE_BUY_QTY") == null ? "" :
		 hD.get("FORCE_BUY_QTY").toString();
		String needDate = hD.get("NEED_DATE") == null ? "" : hD.get("NEED_DATE").toString();
		String itemtype = hD.get("ITEM_TYPE") == null ? "" : hD.get("ITEM_TYPE").toString();
		String operatingMethod = hD.get("RECEIPT_PROCESSING_METHOD") == null ? "" :
		 hD.get("RECEIPT_PROCESSING_METHOD").toString();
		String stockingMethod = hD.get("STOCKING_METHOD") == null ? "" :
		 hD.get("STOCKING_METHOD").toString();
		String status = hD.get("STATUS") == null ? "" : hD.get("STATUS").toString();
		String operatingMethodDesc = hD.get("RECEIPT_PROCESSING_METHOD_DESC") == null ? "" :
		 hD.get("RECEIPT_PROCESSING_METHOD_DESC").toString();
		String allowForceBuy = hD.get("ALLOW_FORCE_BUY") == null ? "" :
		 hD.get("ALLOW_FORCE_BUY").toString();
		String tankName = hD.get("TANK_NAME") == null ? "" : hD.get("TANK_NAME").toString();
		String reorderPoint = hD.get("REORDER_POINT") == null ? "" :
		 hD.get("REORDER_POINT").toString();
		String stockingLevel = hD.get("STOCKING_LEVEL") == null ? "" :
		 hD.get("STOCKING_LEVEL").toString();
		String reorderQuantity = hD.get("REORDER_QUANTITY") == null ? "" :
		 hD.get("REORDER_QUANTITY").toString();
		Date lastCountDate = (Date) hD.get("LAST_COUNT_DATE");
		String itemPackaging = hD.get("ITEM_PACKAGING") == null ? "" :
		 hD.get("ITEM_PACKAGING").toString();
		String billingMethod = hD.get("BILLING_METHOD") == null ? "" :
		 hD.get("BILLING_METHOD").toString();
		String partGroupNo = hD.get("PART_GROUP_NO") == null ? "" :
		 hD.get("PART_GROUP_NO").toString();
		String icmrcStatus = hD.get("ICMRC_STATUS") == null ? "" :
		 hD.get("ICMRC_STATUS").toString();

		String checkedmain = "";
		String Line_Check = (hD.get("USERCHK") == null ? "&nbsp;" :
		 hD.get("USERCHK").toString());
		if (Line_Check.equalsIgnoreCase("yes")) {
		 checkedmain = "checked";
		}
		else {
		 checkedmain = "";
		}
		if (SubuserAction.equalsIgnoreCase("UpdPage") && "NO".equalsIgnoreCase(LINE_STATUS)) {
		 Color = "CLASS=\"error";
		 childColor = "CLASS=\"error";
		}

		/*String checkednon="";
		if ("A".equalsIgnoreCase(icmrcStatus))
		{
		 checkednon = "checked";
		}*/

		boolean samePartNumberline = false;
		boolean firstRow = false;
		if (nextCatPartNo.equalsIgnoreCase(catPartNo) &&
		 nextInventoryGroup.equalsIgnoreCase(inventoryGroup) &&
		 nextPartGroupNumber.equalsIgnoreCase(partGroupNo)) {
		 samePartNumberline = true;
		 lastItemNum++;
		}
		else {
		 ItemIdCount++;
		}

		if (!"A".equalsIgnoreCase(status) || !"A".equalsIgnoreCase(icmrcStatus)) {
		 childColor = "CLASS=\"black";
		}
		else {
		 childColor = Color;
		}

		boolean forcebuyallowed = false;
		if (allowforforcebuy.contains(inventoryGroup) &&
		 !"OOR".equalsIgnoreCase(stockingMethod) && "A".equalsIgnoreCase(status) &&
		 !"N".equalsIgnoreCase(allowForceBuy) && "A".equalsIgnoreCase(icmrcStatus) ) {
		 forcebuyallowed = true;
		}

		boolean invtypeallowed = false;
		if (allowforzeoatm.contains(inventoryGroup) && !"D".equalsIgnoreCase(status)) {
		 invtypeallowed = true;
		}

		lineadded++;

		if (samePartNumberline) {
		 if (lastItemNum == 2) {
			firstRow = true;
		 }

		 if ( (!firstRow)) {
			MsgTmp1.append("<TR>\n");
		 }

		 MsgTmp1.append("<td height=\"25\" " + childColor + "\" width=\"5%\" >" + itemId +
			"</td>\n");
		 MsgTmp1.append("<td height=\"25\" " + childColor + "\" width=\"5%\" >" + itemDesc +
			"</td>\n");
		 if ( (forcebuyallowed || invtypeallowed) && !"MD".equalsIgnoreCase(itemtype)) {
			if (forcebuyallowed)
			{
			 MsgTmp1.append("<td " + childColor + "\" width=\"5%\" height=\"25\"><input type=\"text\" CLASS=\"DETAILS\" onChange=\"CheckQtyValue('" +
			 i + "')\" name=\"buy_qty" + i + "\"  value=\"" + forcebuyqty +
			 "\" maxlength=\"10\" size=\"3\"></td>\n");
			MsgTmp1.append("<td " + childColor +
			 "\" width=\"5%\" height=\"38\"><input type=\"text\" CLASS=\"DETAILS\" name=\"needDate" +
			 i + "\" ID=\"needDate"+i+"\" value=\"" + needDate + "\" maxlength=\"10\" size=\"6\"><FONT SIZE=\"4\"><a href=\"javascript: void(0);\" ID=\"linkneedDate"+i+"\" onClick=\"return getCalendar(document.iteminventory.needDate" +
			 i + ");\">&diams;</a></FONT></td>\n");
       MsgTmp1.append("<td " + childColor + "\" width=\"5%\" height=\"38\"><TEXTAREA name=\"comments" + i + "\" rows=\"5\" cols=\"25\"></TEXTAREA></TD>\n");
      }
			else
			{
			 MsgTmp1.append("<td " + childColor + "\" width=\"5%\" height=\"25\">&nbsp;</td>\n");
			 MsgTmp1.append("<td " + childColor + "\" width=\"5%\" height=\"25\">&nbsp;</td>\n");
       MsgTmp1.append("<td " + childColor + "\" width=\"5%\" height=\"25\">&nbsp;</td>\n");
      }

			MsgTmp1.append("<td " + childColor + "\" width=\"2%\">\n");
      if (invtypeallowed)
      {
      MsgTmp1.append( "<SELECT CLASS=\"HEADER\"  NAME=\"icmrcStatus"+i+"\" onChange= \"checkactivestatus('"+i+"')\" size=\"1\">\n" );
			MsgTmp1.append( radian.web.HTMLHelpObj.getDropDown( sortresult,icmrcStatus ) );
			MsgTmp1.append( "</SELECT>\n" );
      }
      else
      {
       MsgTmp1.append("<input type=\"hidden\" name=\"icmrcStatus" + i + "\" value=\"" + icmrcStatus + "\">\n");
       MsgTmp1.append( radian.web.HTMLHelpObj.getReadonlyDropDownValue( sortresult,icmrcStatus ) );
      }
      MsgTmp1.append("</td>\n");
		 }
		 else {
			if ("MD".equalsIgnoreCase(itemtype)) {
			 MsgTmp1.append("<td " + childColor +
				"\" width=\"5%\" height=\"25\"><A HREF=\"javascript:showdispensesrc('" + itemId +
				"','" + inventoryGroup + "')\">See Source</A></td>\n");
			}
			else {
			 MsgTmp1.append("<td " + childColor + "\" width=\"5%\" height=\"25\">" +
				forcebuyqty + "</td>\n");
			}
			MsgTmp1.append("<td " + childColor + "\" width=\"5%\" height=\"25\">&nbsp;</td>\n");
			MsgTmp1.append("<td " + childColor + "\" width=\"2%\" height=\"25\">&nbsp;</td>\n");
		 }
		 MsgTmp1.append("<input type=\"hidden\" name=\"item_id" + i + "\" value=\"" + itemId +
			"\">\n");
		 MsgTmp1.append("<input type=\"hidden\" name=\"inventory_group" + i + "\" value=\"" +
			inventoryGroup + "\">\n");

		 MsgTmp1.append("</TR>\n");
		 if (lastItemNum == 2) {
			MsgTmp1.append("<input type=\"hidden\" name=\"Start" + itemId + "\" value=\"" + i +
			 "\">\n");
		 }
		}
		else {
		 MsgTmp1.append("<td height=\"25\" " + childColor + "\" width=\"5%\" >" + itemId +
			"</td>\n");
		 MsgTmp1.append("<td height=\"25\" " + childColor + "\" width=\"5%\" >" + itemDesc +
			"</td>\n");
		 if ( (forcebuyallowed || invtypeallowed) && !"MD".equalsIgnoreCase(itemtype)) {
			if (forcebuyallowed)
			{
			MsgTmp1.append("<td " + childColor + "\" width=\"5%\" height=\"25\"><input type=\"text\" CLASS=\"DETAILS\" onChange=\"CheckQtyValue('" +
			 i + "')\" name=\"buy_qty" + i + "\"  value=\"" + forcebuyqty +
			 "\" maxlength=\"10\" size=\"3\"></td>\n");
			MsgTmp1.append("<td " + childColor +
			 "\" width=\"5%\" height=\"38\"><input type=\"text\" CLASS=\"DETAILS\" name=\"needDate" +
			 i + "\" ID=\"needDate"+i+"\" value=\"" + needDate + "\" maxlength=\"10\" size=\"6\"><FONT SIZE=\"4\"><a href=\"javascript: void(0);\" ID=\"linkneedDate"+i+"\" onClick=\"return getCalendar(document.iteminventory.needDate" +
			 i + ");\">&diams;</a></FONT></td>\n");
       MsgTmp1.append("<td " + childColor + "\" width=\"5%\" height=\"38\"><TEXTAREA name=\"comments" + i + "\" rows=\"5\" cols=\"25\"></TEXTAREA></TD>\n");
      }
			else
			{
			 MsgTmp1.append("<td " + childColor + "\" width=\"5%\" height=\"25\">&nbsp;</td>\n");
			 MsgTmp1.append("<td " + childColor + "\" width=\"5%\" height=\"25\">&nbsp;</td>\n");
       MsgTmp1.append("<td " + childColor + "\" width=\"5%\" height=\"25\">&nbsp;</td>\n");
      }

			MsgTmp1.append("<td " + childColor + "\" width=\"2%\">\n");
      if (invtypeallowed)
      {
      MsgTmp1.append( "<SELECT CLASS=\"HEADER\"  NAME=\"icmrcStatus"+i+"\" onChange= \"checkactivestatus('"+i+"')\" size=\"1\">\n" );
			MsgTmp1.append( radian.web.HTMLHelpObj.getDropDown( sortresult,icmrcStatus ) );
			MsgTmp1.append( "</SELECT>\n" );
      }
      else
      {
       MsgTmp1.append("<input type=\"hidden\" name=\"icmrcStatus" + i + "\" value=\"" + icmrcStatus + "\">\n");
       MsgTmp1.append( radian.web.HTMLHelpObj.getReadonlyDropDownValue( sortresult,icmrcStatus ) );
      }
      MsgTmp1.append("</td>\n");
		 }
		 else {
			if ("MD".equalsIgnoreCase(itemtype)) {
			 MsgTmp1.append("<td " + childColor +
				"\" width=\"5%\" height=\"25\"><A HREF=\"javascript:showdispensesrc('" + itemId +
				"','" + inventoryGroup + "')\">See Source</A></td>\n");
			}
			else {
			 MsgTmp1.append("<td " + childColor + "\" width=\"5%\" height=\"25\">" +
				forcebuyqty + "</td>\n");
			}
			MsgTmp1.append("<td " + childColor + "\" width=\"5%\" height=\"25\">&nbsp;</td>\n");
			MsgTmp1.append("<td " + childColor + "\" width=\"2%\" height=\"25\">&nbsp;</td>\n");
		 }
		 MsgTmp1.append("<input type=\"hidden\" name=\"item_id" + i + "\" value=\"" + itemId +
			"\">\n");
		 MsgTmp1.append("<input type=\"hidden\" name=\"inventory_group" + i + "\" value=\"" +
			inventoryGroup + "\">\n");
		 MsgTmp1.append("</TR>\n");

		 if (lastItemNum == 1) {
			MsgTmp1.append("<input type=\"hidden\" name=\"Start" + itemId + "\" value=\"" + i +
			 "\">\n");
		 }
		 MsgTmp1.append("<input type=\"hidden\" name=\"Stop" + itemId + "\" value=\"" + i +
			"\">\n");

		 Msg.append("<TR>\n");
		 if (this.getupdateStatus() && (invtypeallowed || forcebuyallowed)) {
			Msg.append("<td " + Color + "\" width=\"2%\" ROWSPAN=\"" + lastItemNum +
			 "\"><INPUT TYPE=\"checkbox\" value=\"Yes\" " + checkedmain +
			 "  NAME=\"mainrow_chk" + i + "\"></td>\n");
		 }
		 else {
			Msg.append("<td " + Color + "\" width=\"2%\" ROWSPAN=\"" + lastItemNum +
			 "\">&nbsp;</td>\n");
		 }
		 Msg.append("<td height=\"25\" " + Color + "\" width=\"5%\" ROWSPAN=\"" + lastItemNum +
			"\">" + inventoryGroupName + "</td>\n");
		 Msg.append("<td height=\"25\" " + Color + "\" width=\"5%\" ROWSPAN=\"" + lastItemNum +
			"\">\n");
		 Msg.append("<A HREF=\"javascript:showMinMax('" + catPartNo + "','" + inventoryGroup +
			"','" + partGroupNo + "','" + count_uom + "')\">" + catPartNo + "</A></td>\n");
		 Msg.append("</td>\n");
		 Msg.append("<td height=\"25\" " + Color + "\" width=\"5%\" ROWSPAN=\"" + lastItemNum +
			"\">" + reorderPoint + "/" + stockingLevel + "/" + reorderQuantity + "</td>\n");
		 Msg.append("<td height=\"25\" " + Color + "\" width=\"5%\" ROWSPAN=\"" + lastItemNum +
			"\">" + operatingMethodDesc + "</td>\n");
		 Msg.append("<td height=\"25\" " + Color + "\" width=\"5%\" ROWSPAN=\"" + lastItemNum +
			"\">" + billingMethod + "</td>\n");
		 Msg.append("<td height=\"25\" " + Color + "\" width=\"5%\" ROWSPAN=\"" + lastItemNum +
			"\">" + stockingMethod + "</td>\n");
		 Msg.append("<td height=\"25\" " + Color + "\" width=\"5%\" ROWSPAN=\"" + lastItemNum +
			"\">" + usageFacilityId + "</td>\n");
		 Msg.append("<td height=\"25\" " + Color + "\" width=\"5%\" ROWSPAN=\"" + lastItemNum +
			"\">" + usageWorkArea + "</td>\n");
		 Msg.append("<td height=\"25\" " + Color + "\" width=\"5%\" ROWSPAN=\"" + lastItemNum +
			"\">" + count_uom + "</td>\n");
		 Msg.append("<td height=\"25\" " + Color + "\" width=\"5%\" ROWSPAN=\"" + lastItemNum +
			"\">" + itemPackaging + "</td>\n");
		 Msg.append("<td height=\"25\" " + Color + "\" width=\"5%\" ROWSPAN=\"" + lastItemNum +
			"\">" + partOnHand + "");
		 //invlog.debug("partOnHand    "+partOnHand+"");
		 /*Msg.append( "<A HREF=\"javascript:showInventoryDetail('"+inventoryGroup+"','" + catPartNo + "','"+partGroupNo+"')\">" + partOnHand + "</A>\n" );*/
		 if (lastCountDate != null) {
			DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm");
			String formattedLastCountDate = formatter.format(lastCountDate);
			if (formattedLastCountDate != null && formattedLastCountDate.length() > 0) {
			 Msg.append("<BR>(" + formattedLastCountDate + ")");
			}
		 }
		 Msg.append("</td>\n");
		 Msg.append("<td height=\"25\" " + Color + "\" width=\"5%\" ROWSPAN=\"" + lastItemNum +
			"\">" + partInPurchasing + "</td>\n");

		 Msg.append(MsgTmp1);

		 MsgTmp1 = new StringBuffer();
		 lastItemNum = 1;
		 if ( (ItemIdCount) % 2 == 0) {
			Color = "CLASS=\"white";
		 }
		 else {
			Color = "CLASS=\"blue";
		 }
		 continue;
		}
	 }

	 Msg.append("</table>\n");
	 Msg.append(
		"<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\" CLASS=\"moveup\">\n");
	 Msg.append("<tr>");
	 Msg.append(
		"<TD HEIGHT=\"10\" WIDTH=\"100%\" VALIGN=\"MIDDLE\" BGCOLOR=\"#000066\">&nbsp;\n");
	 Msg.append("</TD></tr>");
	 Msg.append("</table>\n");

	 Msg.append("</form>\n");
	 Msg.append("</body></html>\n");
	}
	catch (Exception e) {
	 e.printStackTrace();
	}

	return Msg.toString();
 }
//End of method
}