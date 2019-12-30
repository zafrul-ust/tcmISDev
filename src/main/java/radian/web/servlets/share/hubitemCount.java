package radian.web.servlets.share;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.HubInventoryGroupProcess;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import com.tcmis.common.util.ResourceLibrary;
import java.util.Locale;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2003
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 *
 * 04-07-03 - By default putting the expected quantity in the actual quantity
 * 05-07-03 - Make sure they can not use the back button
 * 06-12-03 - QC ing the issued mrs and code clean up
 * 08-15-03 - Sending error emails and email to steve when they enter more qty than in inventory
 * 10-06-03 - Giving the Correct error message whenever an error happens
 * 10-20-03 - Sending myself an email to run the p_issue_insert_count procedure when it fails. Also not doing the update to issue because it is already done in the procedure
 * 01-28-04 - Making relative URLs to go to the property file to get hosturl. Changed the page to work based in Inventory Groups instead of Hub
 * 02-10-04 - Sorting Drop Downs
 * 03-29-04 - Changes to Move this page to be based on inventory group instead of hub. The count also inculdes OOR items so that they don't have to pick.
 * 05-07-04 - Sorting the Hub Drop Down based on the prority in hub_pref table
 * 06-04-04 - Sending an email with all the results after count processing is done
 * 07-15-04 - Can start a count for all inventory groups
 * 07-19-04 - Calling a procedure to do all the processing
 * 07-29-04 - Showing Inv Grp in the details
 * 03-14-05 - Moving permissions from user group member to user_group_member_ig, controling by inventory group
 * 05-17-05 - Fixed a null pointer exception in hubItemCount page
 */

public class hubitemCount {
 protected boolean allowupdate;
 private ServerResourceBundle bundle = null;
 private TcmISDBModel db = null;
 //private PrintWriter out = null;
 private boolean completeSuccess = true;
 private boolean noneToUpd = true;
 private String User_Sort = "";
 private String mainErrormsg = "";
 private String thedecidingRandomNumber = null;
 private String finalmrlist = "";
 private String actualCountDate = "";
 private String countStartDate = "";
 //private String lastDateCounted = "";
 private boolean intcmIsApplication = false;
 private ResourceLibrary res = null; // res means resource.

 private static org.apache.log4j.Logger hiclog = org.apache.log4j.Logger.
	getLogger(hubitemCount.class);

 public void setupdateStatus(boolean id) {
	this.allowupdate = id;
 }

 private boolean getupdateStatus() throws Exception {
	return this.allowupdate;
 }

 public hubitemCount(ServerResourceBundle b, TcmISDBModel d) {
	bundle = b;
	db = d;
 }

 //Process the HTTP Post request
 public void doResult(HttpServletRequest request,
	HttpServletResponse response,PrintWriter out) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setLocale((Locale)session.getAttribute("tcmISLocale"));
		//response.setContentType( "text/html; charset=UTF-8" );
		//out = response.getWriter();
	    res =  new ResourceLibrary("com.tcmis.common.resources.CommonResources", (Locale)session.getAttribute("tcmISLocale"));

        PersonnelBean personnelBean = (PersonnelBean) session.getAttribute("personnelBean");
        Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
        intcmIsApplication = false;
        if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
        {
         intcmIsApplication = true;
        }

	String personnelid = session.getAttribute("PERSONNELID") == null ? "" :
	 session.getAttribute("PERSONNELID").toString();
	//PersonnelBean personnelBean = session.getAttribute("personnelBean") == null ? null :(PersonnelBean) session.getAttribute("personnelBean");
	Collection hubInventoryGroupOvBeanCollection = new Vector( (Collection)
	 session.getAttribute("hubInventoryGroupOvBeanCollection"));

	String branch_plant = request.getParameter("HubName");
	if (branch_plant == null)
	 branch_plant = "";
	branch_plant = branch_plant.trim();

	String invengrp = request.getParameter("invengrp");
		if (invengrp == null)
		 invengrp = "";
		invengrp = invengrp.trim();

	HubInventoryGroupProcess hubInventoryGroupProcess = new
	 HubInventoryGroupProcess("hub");
	boolean iscollection = hubInventoryGroupProcess.isCollection(hubInventoryGroupOvBeanCollection, branch_plant, invengrp);

	 actualCountDate = request.getParameter("actualCountDate");
	 if (actualCountDate == null)
		actualCountDate = "";
	 actualCountDate = actualCountDate.trim();

	String scategory = request.getParameter("Category");
	if (scategory == null)
	 scategory = "i.INVENTORY_GROUP,i.ITEM_ID";

	String User_Session = request.getParameter("session");
	if (User_Session == null)
	 User_Session = "New";

	String User_Action = request.getParameter("UserAction");
	if (User_Action == null)
	 User_Action = "New";

	String SubUser_Action = request.getParameter("SubUserAction");
	if (SubUser_Action == null)
	 SubUser_Action = "New";

	//hiclog.debug("Hub: " + branch_plant + " invengrp: " + invengrp + " iscollection "+iscollection+"  User_Action: " + User_Action + "");
	if ( (User_Session.equalsIgnoreCase("Active")) &&
	 "STARTNEW".equalsIgnoreCase(SubUser_Action)) {
	 if ("All".equalsIgnoreCase(invengrp)) {
		mainErrormsg += this.startNewCount(iscollection,
		 branch_plant, invengrp, personnelid);
	 }
	 else {
		if ( (!iscollection && personnelBean.getPermissionBean().hasInventoryGroupPermission("Hub ItemCount", invengrp,null,null)) || iscollection) {
		 mainErrormsg += this.startNewCount(iscollection,
			branch_plant, invengrp, personnelid);
		}
		else {
		 mainErrormsg +=
			" "+res.getString("err.perminvgrp")+": " +
			invengrp + "";
		}
	 }
	}

	thedecidingRandomNumber = request.getParameter("thedecidingRandomNumber");
	if (thedecidingRandomNumber == null)
	 thedecidingRandomNumber = "";
	thedecidingRandomNumber = thedecidingRandomNumber.trim();

	Random rand = new Random();
	int tmpReq = rand.nextInt();
	Integer tmpReqNum = new Integer(tmpReq);

	try {
	 Hashtable hub_list_set = new Hashtable();
	 hub_list_set = (Hashtable) session.getAttribute("HUB_PREF_LIST");

	 if (thedecidingRandomNumber.length() > 0) {
		String randomnumberfromsesion = session.getAttribute("RANDOMNUMBERCOOKIE") == null ?
		 "" : session.getAttribute("RANDOMNUMBERCOOKIE").toString();
		if (randomnumberfromsesion.equalsIgnoreCase(thedecidingRandomNumber)) {
		 thedecidingRandomNumber = tmpReqNum.toString();
		 session.setAttribute("RANDOMNUMBERCOOKIE", thedecidingRandomNumber);
		}
		else {
		 thedecidingRandomNumber = tmpReqNum.toString();
		 session.setAttribute("RANDOMNUMBERCOOKIE", thedecidingRandomNumber);
		 Hashtable hub_list1 = (Hashtable) (hub_list_set.get("HUB_LIST"));
		 Vector countAllowInvenGrps = (Vector) session.getAttribute(
			"COUNT_ALLOWED_INVENGRP");

		 if (hub_list1.size() < 1) {
			buildHeader(hub_list_set, "", scategory,
			 hubInventoryGroupOvBeanCollection, invengrp, mainErrormsg, session,out);
			out.print(radian.web.HTMLHelpObj.printHTMLNoFacilities(res));
			hub_list_set = null;
		 }
		 else {
			Hashtable hub_list = (Hashtable) (hub_list_set.get("HUB_LIST"));
			for (Enumeration e = hub_list.keys(); e.hasMoreElements(); ) {
			 String branchplant = (String) (e.nextElement());
			 branch_plant = branchplant;
			}

			Vector openorder_data = new Vector();
			int count = 0;
			openorder_data = this.getResult(iscollection,
			 branch_plant, invengrp, scategory,personnelid);
			Hashtable sum = (Hashtable) (openorder_data.elementAt(0));
			count = ( (Integer) sum.get("TOTAL_NUMBER")).intValue();

			if (0 == count) {
			 buildHeader(hub_list_set, branch_plant, scategory,
				hubInventoryGroupOvBeanCollection, invengrp, mainErrormsg, session,out);
			 out.print(radian.web.HTMLHelpObj.printMessageinTable("<CENTER><FONT SIZE=\"4\" FACE=\"Arial\" COLOR=\"#fc0303\"><B>"+res.getString("label.pleasenobackbutton")+"</B></FONT>"));
			 out.print(radian.web.HTMLHelpObj.printHTMLNoData(res.getString("label.noopencount")));

			 openorder_data = null;
			 hub_list_set = null;
			}
			else {
			 session.setAttribute("ITEM_COUNT_DATA", openorder_data);
			 buildHeader(hub_list_set, branch_plant, scategory,
				hubInventoryGroupOvBeanCollection, invengrp, mainErrormsg, session,out);
			 out.print(radian.web.HTMLHelpObj.printMessageinTable("<CENTER><FONT SIZE=\"4\" FACE=\"Arial\" COLOR=\"#fc0303\"><B>"+res.getString("label.pleasenobackbutton")+"</B></FONT>"));
			 buildDetails(openorder_data, countAllowInvenGrps,actualCountDate,out);
			 openorder_data = null;
			 hub_list_set = null;
			}
		 }
		 return;
		}
	 }
	 else {
		thedecidingRandomNumber = tmpReqNum.toString();
		session.setAttribute("RANDOMNUMBERCOOKIE", thedecidingRandomNumber);
	 }

	 if (User_Session.equalsIgnoreCase("New")) {
		String CompanyID = session.getAttribute("COMPANYID").toString();
		Hashtable hub_list1 = (Hashtable) (hub_list_set.get("HUB_LIST"));
		if (hub_list1.size() < 1) {
		 buildHeader(hub_list_set, "", scategory, hubInventoryGroupOvBeanCollection,
			invengrp, mainErrormsg, session,out);
		 out.print(radian.web.HTMLHelpObj.printHTMLNoFacilities(res));
		 hub_list_set = null;
		}
		else {
		 Hashtable hub_list = (Hashtable) (hub_list_set.get("HUB_LIST"));
		 for (Enumeration e = hub_list.keys(); e.hasMoreElements(); ) {
			String branchplant = (String) (e.nextElement());
			branch_plant = branchplant;
		 }

		 Vector countAllowInvenGrps = radian.web.HTMLHelpObj.createvgrpmemlist(db,
			personnelid, "Hub ItemCount");
		 session.setAttribute("COUNT_ALLOWED_INVENGRP", countAllowInvenGrps);

		 Vector openorder_data = new Vector();
		 int count = 0;
		 openorder_data = this.getResult(iscollection,
			branch_plant, invengrp, scategory,personnelid);
		 Hashtable sum = (Hashtable) (openorder_data.elementAt(0));
		 count = ( (Integer) sum.get("TOTAL_NUMBER")).intValue();

		 if (0 == count) {
			buildHeader(hub_list_set, branch_plant, scategory,
			 hubInventoryGroupOvBeanCollection, invengrp, mainErrormsg, session,out);
			out.print(radian.web.HTMLHelpObj.printHTMLNoData(res.getString("label.noopencount")));
			openorder_data = null;
			hub_list_set = null;
		 }
		 else {
			session.setAttribute("ITEM_COUNT_DATA", openorder_data);
			buildHeader(hub_list_set, branch_plant, scategory,
			 hubInventoryGroupOvBeanCollection, invengrp, mainErrormsg, session,out);
			buildDetails(openorder_data, countAllowInvenGrps,actualCountDate,out);
			openorder_data = null;
			hub_list_set = null;
		 }
		}
	 }
	 else if ( (User_Session.equalsIgnoreCase("Active")) &&
		(User_Action.equalsIgnoreCase("Search"))) {
		Vector countAllowInvenGrps = (Vector) session.getAttribute(
		 "COUNT_ALLOWED_INVENGRP");
		Vector openorder_data = new Vector();
		openorder_data = this.getResult(iscollection,
		 branch_plant, invengrp, scategory,personnelid);
		Hashtable sum = (Hashtable) (openorder_data.elementAt(0));
		int count = ( (Integer) sum.get("TOTAL_NUMBER")).intValue();

		buildHeader(hub_list_set, branch_plant, scategory,
		 hubInventoryGroupOvBeanCollection, invengrp, mainErrormsg, session,out);
		if (0 == count) {
		 out.print(radian.web.HTMLHelpObj.printHTMLNoData(res.getString("label.noopencount")));
		}
		else {
		 session.setAttribute("ITEM_COUNT_DATA", openorder_data);
		 buildDetails(openorder_data, countAllowInvenGrps,actualCountDate,out);
		}

		openorder_data = null;
		hub_list_set = null;
	 }
	 else if ( (User_Session.equalsIgnoreCase("Active")) &&
		(User_Action.equalsIgnoreCase("Close"))) {
		Vector countAllowInvenGrps = (Vector) session.getAttribute(
		 "COUNT_ALLOWED_INVENGRP");
		if ("All".equalsIgnoreCase(invengrp)) {
		 mainErrormsg += this.closeopencount(iscollection,
			branch_plant, invengrp, personnelid);
		}
		else {
		 if ((!iscollection && personnelBean.getPermissionBean().hasInventoryGroupPermission("Hub ItemCount", invengrp,null,null)) || iscollection) {
			mainErrormsg += this.closeopencount(iscollection,
			 branch_plant, invengrp, personnelid);
		 }

		 else {
			mainErrormsg +=
			 " "+res.getString("label.nopermcount") +	 invengrp + "";
		 }
		}

		if (mainErrormsg == null) {
		 mainErrormsg = "";
		}
		Vector openorder_data = new Vector();
		int count = 0;
		openorder_data = this.getResult(iscollection,
		 branch_plant, invengrp, scategory,personnelid);
		Hashtable sum = (Hashtable) (openorder_data.elementAt(0));
		count = ( (Integer) sum.get("TOTAL_NUMBER")).intValue();

		if (0 == count && mainErrormsg.trim().length() == 0) {
		 buildHeader(hub_list_set, branch_plant, scategory,
			hubInventoryGroupOvBeanCollection, invengrp, mainErrormsg, session,out);
		 out.print(radian.web.HTMLHelpObj.printMessageinTable(
			"<B>"+res.getString("label.closedcountsucc")+"</B>"));
		 out.print(radian.web.HTMLHelpObj.printHTMLNoData(
				 res.getString("label.noopencount")));
		 openorder_data = null;
		 hub_list_set = null;
		 session.removeAttribute("ITEM_COUNT_DATA");
		}
		else {
		 session.setAttribute("ITEM_COUNT_DATA", openorder_data);
		 buildHeader(hub_list_set, branch_plant, scategory,
			hubInventoryGroupOvBeanCollection, invengrp, mainErrormsg, session,out);
		 out.print(radian.web.HTMLHelpObj.printMessageinTable(
			"<B>"+res.getString("label.coundnotclosecount")+"</B>"));
		 buildDetails(openorder_data, countAllowInvenGrps,actualCountDate,out);
		 openorder_data = null;
		 hub_list_set = null;
		}
	 }
	 else if ( (User_Session.equalsIgnoreCase("Active")) &&
		(User_Action.equalsIgnoreCase("New"))) {
		Vector countAllowInvenGrps = (Vector) session.getAttribute(
		 "COUNT_ALLOWED_INVENGRP");
		Vector openorder_data = new Vector();
		openorder_data = this.getResult(iscollection,
		 branch_plant, invengrp, scategory,personnelid);
		Hashtable sum = (Hashtable) (openorder_data.elementAt(0));
		int count = ( (Integer) sum.get("TOTAL_NUMBER")).intValue();
		if (0 == count) {
		 buildHeader(hub_list_set, branch_plant, scategory,
			hubInventoryGroupOvBeanCollection, invengrp, mainErrormsg, session,out);
		 out.print(radian.web.HTMLHelpObj.printHTMLNoData(res.getString("label.noopencount")));

		 openorder_data = null;
		 hub_list_set = null;
		}
		else {
		 session.setAttribute("ITEM_COUNT_DATA", openorder_data);
		 buildHeader(hub_list_set, branch_plant, scategory,
			hubInventoryGroupOvBeanCollection, invengrp, mainErrormsg, session,out);
		 buildDetails(openorder_data, countAllowInvenGrps,actualCountDate,out);

		 openorder_data = null;
		 hub_list_set = null;
		}
	 }
	 else if ( (User_Session.equalsIgnoreCase("Active")) &&
		(User_Action.equalsIgnoreCase("generatexls"))) {
		Vector retrndata = (Vector) session.getAttribute("ITEM_COUNT_DATA");
		out.print(buildxlsDetails(retrndata, personnelid,out));
		retrndata = null;
	 }
	 else if ( (User_Session.equalsIgnoreCase("Active")) &&
		(User_Action.equalsIgnoreCase("Update"))) {
		Vector retrn_data = new Vector();
		Vector synch_data = new Vector();
		Vector countAllowInvenGrps = (Vector) session.getAttribute(
		 "COUNT_ALLOWED_INVENGRP");
		retrn_data = (Vector) session.getAttribute("ITEM_COUNT_DATA");
		synch_data = SynchServerData(request, retrn_data,out);

		retrn_data = null;

		if (SubUser_Action.equalsIgnoreCase("UpdPage")) {
		 String hub_name = "";
		 Hashtable hub_list = (Hashtable) (hub_list_set.get("HUB_LIST"));
		 for (Enumeration e = hub_list.keys(); e.hasMoreElements(); ) {
			String branchplant = (String) (e.nextElement());

			if (branch_plant.equalsIgnoreCase(branchplant)) {
			 hub_name = (String) (hub_list.get(branch_plant));
			}
		 }

		 Hashtable updateresults = UpdateDetails(iscollection,
			branch_plant, invengrp, synch_data, personnelid,out);
		 Boolean result = (Boolean) updateresults.get("RESULT");
		 boolean resulttotest = result.booleanValue();
		 Vector openorder_data = new Vector();
		 openorder_data = this.getResult(iscollection,
			branch_plant, invengrp, scategory,personnelid);

		 Hashtable sum = (Hashtable) (openorder_data.elementAt(0));
		 int count = ( (Integer) sum.get("TOTAL_NUMBER")).intValue();
		 if (0 == count && resulttotest) {
			buildHeader(hub_list_set, branch_plant, scategory,
			 hubInventoryGroupOvBeanCollection, invengrp, mainErrormsg, session,out);
			out.print(radian.web.HTMLHelpObj.printMessageinTable(res.getString("label.updatecountsucc")));
			out.print(radian.web.HTMLHelpObj.printHTMLNoData(res.getString("label.noopencount")));

			openorder_data = null;
			hub_list_set = null;
			session.removeAttribute("ITEM_COUNT_DATA");
		 }
		 else {
			session.setAttribute("ITEM_COUNT_DATA", openorder_data);
			buildHeader(hub_list_set, branch_plant, scategory,
			 hubInventoryGroupOvBeanCollection, invengrp, "", session,out);
			if (resulttotest) {
			 out.print(radian.web.HTMLHelpObj.printMessageinTable(res.getString("label.updatecountsucc")));
			 session.removeAttribute("ITEM_COUNT_DATA");
			}
			else {
			 out.print(radian.web.HTMLHelpObj.printMessageinTable("<B>"+res.getString("label.couldnotupdatecount")+"</B><BR>" +
				mainErrormsg + ""));
			}
			buildDetails(openorder_data, countAllowInvenGrps,actualCountDate,out);

			openorder_data = null;
			hub_list_set = null;
		 }

		}
	 }
	}
	catch (Exception e) {
	 e.printStackTrace();
	 out.print(radian.web.HTMLHelpObj.printError("label.hubitemcunt",intcmIsApplication,res));
	}
	finally {
	 out.close();
	}
 }

 private StringBuffer buildxlsDetails(Vector data,
	String personnelID,PrintWriter out) throws Exception {
	String url = "";
	String file = "";
	Random rand = new Random();
	int tmpReq = rand.nextInt();
	Integer tmpReqNum = new Integer(tmpReq);

	String wwwHome = radian.web.tcmisResourceLoader.gethosturl();
	String writefilepath = radian.web.tcmisResourceLoader.getsaveltempreportpath();

	file = writefilepath + personnelID + tmpReqNum.toString() + "hubitemcnt.csv";
	url = wwwHome + "reports/temp/" + personnelID + tmpReqNum.toString() +
	 "hubitemcnt.csv";

	try {
	 PrintWriter pw = new PrintWriter(new FileOutputStream(file));

	 Hashtable summary = new Hashtable();
	 summary = (Hashtable) data.elementAt(0);
	 int total = ( (Integer) (summary.get("TOTAL_NUMBER"))).intValue();
	 pw.println(
		"Part Number,Inventory Group,Item,Description,Packaging,UOM,In Purchasing,Qty,Actual Count\n");

	 int i = 0;
	 for (int loop = 0; loop < total; loop++) {
		i++;
		Hashtable countData = new Hashtable();
		countData = (Hashtable) data.elementAt(i);

		String invengrp = countData.get("INVENTORY_GROUP") == null ? " " :
		 countData.get("INVENTORY_GROUP").toString();
		String item_id = countData.get("ITEM_ID") == null ? " " :
		 countData.get("ITEM_ID").toString();
		String description = countData.get("DESCRIPTION") == null ? " " :
		 countData.get("DESCRIPTION").toString();
		String packaging = countData.get("PACKAGING") == null ? " " :
		 countData.get("PACKAGING").toString();
		String qty = countData.get("QTY") == null ? "" :
		 countData.get("QTY").toString();
		String actonHand = countData.get("ACTUAL_ON_HAND") == null ? "" :
		 countData.get("ACTUAL_ON_HAND").toString();
		String catpartnum = countData.get("CAT_PART_NO") == null ? "" :
		 countData.get("CAT_PART_NO").toString();
		String itemuom = countData.get("UOM") == null ? "" :
		 countData.get("UOM").toString();
		String inPurchasing = countData.get("IN_PURCHASING") == null ? "&nbsp;" : countData.get("IN_PURCHASING").toString();

		pw.print("\"" + catpartnum + "\",");
		pw.print("\"" + invengrp + "\",");
		pw.print("\"" + item_id + "\",");
		pw.print("\"" + description + "\",");
		pw.print("\"" + packaging + "\",");
		pw.print("\"" + itemuom + "\",");
		pw.print("\"" + inPurchasing + "\",");
		pw.print("\"" + qty + "\",");
		pw.print("\"" + actonHand + "\"\n");
	 }
	 pw.close();
	}
	catch (Exception e) {
	 e.printStackTrace();
	 out.print(radian.web.HTMLHelpObj.printError("tcmIS Hub Item Count Page",intcmIsApplication));
	}
	finally {
	}

	StringBuffer MsgSB = new StringBuffer();
	if (url.length() > 0) {
	 MsgSB.append("<HTML><HEAD>\n");
	 MsgSB.append("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; url=" + url +"\">\n");
	 MsgSB.append("<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n");
	 MsgSB.append("<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n");
	 MsgSB.append("<TITLE>tcmIS Hub Item Count Page</TITLE>\n");
	 MsgSB.append("</HEAD>  \n");
	 MsgSB.append("<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">  \n");
	 MsgSB.append("<CENTER><img src=\"/images/tcmintro.gif\"  border=1 align=\"middle\"><P></P><BR>\n");
	 MsgSB.append("<FONT FACE=\"Arial\" size=\"5\" color=\"#000080\"><b>Downloading Excel File</b></font><P></P><BR>\n");
	 MsgSB.append("<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b></b></font><P></P><BR>\n");
	 MsgSB.append("<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b>Please Wait ...</b></font><P></P><BR>\n");
	 MsgSB.append("</CENTER>\n");
	 MsgSB.append("</BODY></HTML>    \n");
	}
	else {
	 MsgSB.append("<HTML><HEAD>\n");
	 MsgSB.append("<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n");
	 MsgSB.append("<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n");
	 MsgSB.append("<TITLE>tcmIS Hub Item Count Page</TITLE>\n");
	 MsgSB.append("</HEAD>  \n");
	 MsgSB.append("<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">  \n");
	 MsgSB.append("<CENTER><img src=\"/images/tcmintro.gif\"  border=1 align=\"middle\"><P></P><BR>\n");
	 MsgSB.append("<FONT FACE=\"Arial\" size=\"5\" color=\"#000080\"><b>An Error Occured </b></font><P></P><BR>\n");
	 MsgSB.append("<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b></b></font><P></P><BR>\n");
	 MsgSB.append("<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b>Please try Again or contact Tech Center.</b></font><P></P><BR>\n");
	 MsgSB.append("</CENTER>\n");
	 MsgSB.append("</BODY></HTML>    \n");
	}
	return MsgSB;
 }

 private Vector getResult(boolean iscollection,
	String branchPlant, String inventoryGroup, String sortby,String personnelId) throws Exception {
	Vector Data = new Vector();
	Hashtable DataH = new Hashtable();
	Hashtable summary = new Hashtable();
	DBResultSet dbrs = null;
	ResultSet searchRs = null;
	int count = 0;
	summary.put("TOTAL_NUMBER", new Integer(count));
	Data.addElement(summary);
	boolean doquery = true;

	//mainErrormsg += this.closeCountIfReceipt(iscollection, branchPlant,inventoryGroup, personnelId);

	try {
	 String query = "select IN_PURCHASING,to_char(LAST_DATE_COUNTED,'mm/dd/yyyy') LAST_DATE_COUNTED1,to_char(LAST_DATE_OF_RECEIPT,'mm/dd/yyyy') LAST_DATE_OF_RECEIPT1 ,to_char(LAST_RECEIPT_QC_DATE,'mm/dd/yyyy') LAST_RECEIPT_QC_DATE1, ";
	 query += "to_char(PICK_DATE,'mm/dd/yyyy') PICK_DATE1, to_char(DATE_COUNTED,'mm/dd/yyyy') DATE_COUNTED1,to_char(START_DATE,'dd Mon yyyy hh24:mi') START_DATE1,to_char(START_DATE,'mm/dd/yyyy') START_DATE2, ";
	 query += "i.* ";
	 //query += "i.* from  item_count_display_view i where (i.count_id) in (select count_id from ig_count_definition where end_date is null and ";

	 if (inventoryGroup != null && inventoryGroup.length() > 0 &&
		!inventoryGroup.equalsIgnoreCase("ALL")) {
		if (iscollection) {
		 query += " FROM item_count_display_group_view i WHERE HUB = '"+branchPlant+"'";
		 query += " and INVENTORY_GROUP_COLLECTION='" +
			inventoryGroup + "' ";

		 /*query += " INVENTORY_GROUP in (select INVENTORY_GROUP from INVENTORY_GROUP_COLLECTION where HUB = '" +
			branchPlant +
			"' and INVENTORY_GROUP_COLLECTION = '" + inventoryGroup + "') ";*/
		}
		else if (inventoryGroup.length() > 0) {
		 query += " FROM item_count_display_view i WHERE HUB = '"+branchPlant+"'";
		 query += " and INVENTORY_GROUP='" +inventoryGroup+ "' ";
		}
	 }
	 else if (inventoryGroup.length() > 0 &&
		"All".equalsIgnoreCase(inventoryGroup)) {
		query += " FROM item_count_display_view i WHERE HUB = '"+branchPlant+"'";
		/*query += " INVENTORY_GROUP in (select INVENTORY_GROUP from INVENTORY_GROUP_DEFINITION where HUB = '" +
		 branchPlant + "' and ISSUE_GENERATION = 'Item Counting') ";*/

		/*String inventoryGroupIn = "";
		Iterator hubIterator = hubInventoryGroupOvBeanColl.iterator();
		int itemCountingHubCount = 0;

		while (hubIterator.hasNext()) {
		 HubInventoryGroupOvBean hubInventoryGroupOvBean = (HubInventoryGroupOvBean)
			hubIterator.next();
		 String currentBranchPlant = hubInventoryGroupOvBean.getBranchPlant();
		 Collection inventoryGroupCollection = (Collection) hubInventoryGroupOvBean.
			getInventoryGroupCollection();
		 if (branchPlant.equalsIgnoreCase(currentBranchPlant)) {
			Iterator inventoryGroupIterator = inventoryGroupCollection.iterator();
			while (inventoryGroupIterator.hasNext()) {
			 InventoryGroupObjBean inventoryGroupObjBean = (InventoryGroupObjBean)
				inventoryGroupIterator.next();
			 if ("Item Counting".equalsIgnoreCase(inventoryGroupObjBean.
				getIssueGeneration())) {
				if (itemCountingHubCount > 0) {
				 inventoryGroupIn += ",'" + inventoryGroupObjBean.getInventoryGroup() +
					"'";
				}
				else {
				 inventoryGroupIn += "'" + inventoryGroupObjBean.getInventoryGroup() +
					"'";
				}

				itemCountingHubCount++;
			 }
			}
		 }
		}

		if (inventoryGroupIn.length() > 1) {
		 query += "INVENTORY_GROUP in (" + inventoryGroupIn + ")";
		}
		else {
		 query += " rownum = 0";
		 doquery = false;
		}*/
	 }
	 else {
		query += " FROM item_count_display_group_view i WHERE rownum = 0";
		doquery = false;
	 }

	 if (sortby.length() > 0) {
		query += " order by " + sortby + "";
	 }

	 if (doquery) {

		dbrs = db.doQuery(query);
		searchRs = dbrs.getResultSet();
		while (searchRs.next()) {
		 if (count == 0) {
			User_Sort = searchRs.getString("START_DATE1") == null ? "" :
			 searchRs.getString("START_DATE1");
			countStartDate = searchRs.getString("START_DATE2") == null ? "" :
			 searchRs.getString("START_DATE2");
			actualCountDate = searchRs.getString("PICK_DATE1") == null ? "" :
			 searchRs.getString("PICK_DATE1");
			 //hiclog.debug("actualCountDate   "+actualCountDate+"");
		 }
		 DataH = new Hashtable();
		 //HUB
		 DataH.put("INVENTORY_GROUP",
			searchRs.getString("INVENTORY_GROUP") == null ? "" :
			searchRs.getString("INVENTORY_GROUP"));
		 //ITEM_ID
		 DataH.put("ITEM_ID",
			searchRs.getString("ITEM_ID") == null ? "" : searchRs.getString("ITEM_ID"));
		 //DESCRIPTION
		 DataH.put("DESCRIPTION",
			searchRs.getString("DESCRIPTION") == null ? "" :
			searchRs.getString("DESCRIPTION"));
		 //PACKAGING
		 DataH.put("PACKAGING",
			searchRs.getString("PACKAGING") == null ? "" :
			searchRs.getString("PACKAGING"));
		 //QTY
		 DataH.put("QTY",
			searchRs.getString("INVENTORY_QUANTITY") == null ? "" :
			searchRs.getString("INVENTORY_QUANTITY"));
		 //COUNT_ID
		 DataH.put("COUNT_ID",
			searchRs.getString("COUNT_ID") == null ? "" :
			searchRs.getString("COUNT_ID"));
		 //COUNTED_QUANTITY
		 //DataH.put("COUNTED_QUANTITY", "");
		 /*//PICK_DATE
		 DataH.put("PICK_DATE",
			searchRs.getString("PICK_DATE") == null ? "" :
			searchRs.getString("PICK_DATE"));*/
		 //CAT_PART_NO
		 DataH.put("CAT_PART_NO",
			searchRs.getString("CAT_PART_NO") == null ? "" :
			searchRs.getString("CAT_PART_NO"));
		 //CREDIT_AT_OVER_COUNT
		 DataH.put("CREDIT_AT_OVER_COUNT",
			searchRs.getString("CREDIT_AT_OVER_COUNT") == null ? "" :
			searchRs.getString("CREDIT_AT_OVER_COUNT"));
		 //UOM
		 DataH.put("UOM",
			searchRs.getString("UOM") == null ? "" : searchRs.getString("UOM"));
		 DataH.put("RECEIPT_PROCESSING_METHOD",
			searchRs.getString("RECEIPT_PROCESSING_METHOD") == null ? "" :
			searchRs.getString("RECEIPT_PROCESSING_METHOD"));

		 DataH.put("ACTUAL_ON_HAND", "");
		 DataH.put("UPDATESTATUS", "");
		 DataH.put("INVENTORY_GROUP_NAME",
			searchRs.getString("INVENTORY_GROUP_NAME") == null ? "" :
			searchRs.getString("INVENTORY_GROUP_NAME"));

 		 //COUNTED_QUANTITY
		 DataH.put("COUNTED_QUANTITY",searchRs.getString("COUNTED_QUANTITY")==null?"":searchRs.getString("COUNTED_QUANTITY"));
		 DataH.put("CLOSED",searchRs.getString("CLOSED")==null?"":searchRs.getString("CLOSED"));
		 DataH.put("CATALOG_ID",searchRs.getString("CATALOG_ID")==null?"":searchRs.getString("CATALOG_ID"));
		 DataH.put("PART_GROUP_NO",searchRs.getString("PART_GROUP_NO")==null?"":searchRs.getString("PART_GROUP_NO"));
		 DataH.put("COMPANY_ID",searchRs.getString("COMPANY_ID")==null?"":searchRs.getString("COMPANY_ID"));
		 DataH.put("COUNT_TYPE",searchRs.getString("COUNT_TYPE")==null?"":searchRs.getString("COUNT_TYPE"));
		 DataH.put("LAST_DATE_OF_RECEIPT",searchRs.getString("LAST_DATE_OF_RECEIPT1")==null?"":searchRs.getString("LAST_DATE_OF_RECEIPT1"));
		 DataH.put("LAST_RECEIPT_QC_DATE",searchRs.getString("LAST_RECEIPT_QC_DATE1")==null?"":searchRs.getString("LAST_RECEIPT_QC_DATE1"));
		 DataH.put("LAST_DATE_COUNTED",searchRs.getString("LAST_DATE_COUNTED1")==null?"":searchRs.getString("LAST_DATE_COUNTED1"));
		 DataH.put("IN_PURCHASING",searchRs.getString("IN_PURCHASING")==null?"":searchRs.getString("IN_PURCHASING"));
		 DataH.put("LAST_BIN",searchRs.getString("LAST_BIN")==null?"":searchRs.getString("LAST_BIN"));

		 DataH.put("COUNTSTATUS", "Uncounted");
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
	Data.trimToSize();
	return Data;
 }

 private Vector SynchServerData(HttpServletRequest request, Vector in_data,PrintWriter out) {
	Vector new_data = new Vector();
	Hashtable sum = (Hashtable) (in_data.elementAt(0));
	int count = ( (Integer) sum.get("TOTAL_NUMBER")).intValue();
	new_data.addElement(sum);

	try {
	 for (int i = 1; i < count + 1; i++) {
		Hashtable hD = new Hashtable();
		hD = (Hashtable) (in_data.elementAt(i));

		String act_on_hand = "";
		act_on_hand = request.getParameter( ("act_on_hand" + i)).toString();
		if (act_on_hand == null) {
		 act_on_hand = "";
		}

		if ( (act_on_hand.trim().length() > 0)) {
		 hD.remove("ACTUAL_ON_HAND");
		 hD.put("ACTUAL_ON_HAND", act_on_hand.trim());

		}

		String countStatus = "";
		countStatus = request.getParameter( ("countStatus" + i));
		if (countStatus == null) {
		 countStatus = "Uncounted";
		}
		hD.remove("COUNTSTATUS");
		hD.put("COUNTSTATUS", countStatus.trim());

		new_data.addElement(hD);
	 }
	}
	catch (Exception e) {
	 e.printStackTrace();
	 out.print(radian.web.HTMLHelpObj.printError("label.hubitemcunt",intcmIsApplication,res));
	}
	return new_data;
 }

 private Hashtable UpdateDetails(boolean iscollection,
	String hubnum, String inventoryGroup, Vector data, String personnelId,PrintWriter out) {
	boolean result = true;
	Hashtable updateresult = new Hashtable();
	Vector errordata = new Vector();
	Hashtable dosomething = new Hashtable();
	int count = 0;
	//int creditcnt = 0;
	String count_id = "";
	dosomething.put("TOTAL_NUMBER", new Integer(count));
	errordata.addElement(dosomething);
	String creditatoverage = "";
	Connection connect1;
	CallableStatement cs = null;

	try {
	 Hashtable summary = new Hashtable();
	 summary = (Hashtable) data.elementAt(0);
	 int total = ( (Integer) (summary.get("TOTAL_NUMBER"))).intValue();
	 int linecheckcount = 0;

	 mainErrormsg += this.setDateCounted(iscollection, hubnum,inventoryGroup, personnelId);

	 for (int i = 1; i < total + 1; i++) {
		Hashtable hDcount = new Hashtable();
		hDcount = (Hashtable) data.elementAt(i);
		String qty = hDcount.get("QTY") == null ? "" : hDcount.get("QTY").toString();
		String actonHand = hDcount.get("ACTUAL_ON_HAND") == null ? "" :
		 hDcount.get("ACTUAL_ON_HAND").toString();
		String item_id = hDcount.get("ITEM_ID") == null ? "" :
		 hDcount.get("ITEM_ID").toString();
		String invengrp = hDcount.get("INVENTORY_GROUP") == null ? "" :
		 hDcount.get("INVENTORY_GROUP").toString();
		count_id = hDcount.get("COUNT_ID") == null ? "" :
		 hDcount.get("COUNT_ID").toString();
		creditatoverage = hDcount.get("CREDIT_AT_OVER_COUNT") == null ? "" :
		 hDcount.get("CREDIT_AT_OVER_COUNT").toString();
		String operatingMethod = hDcount.get("RECEIPT_PROCESSING_METHOD") == null ? "" :
		 hDcount.get("RECEIPT_PROCESSING_METHOD").toString();

		String closed = hDcount.get("CLOSED") == null ? "" : hDcount.get("CLOSED").toString();
		String catalogId = hDcount.get("CATALOG_ID") == null ? "" : hDcount.get("CATALOG_ID").toString();
		String catPartNo = hDcount.get("CAT_PART_NO") == null ? "" : hDcount.get("CAT_PART_NO").toString();
		String partGroupNo = hDcount.get("PART_GROUP_NO") == null ? "" : hDcount.get("PART_GROUP_NO").toString();
		String companyId = hDcount.get("COMPANY_ID") == null ? "" : hDcount.get("COMPANY_ID").toString();
		String countType = hDcount.get("COUNT_TYPE") == null ? "" : hDcount.get("COUNT_TYPE").toString();
		//String itemuom = countData.get("UOM") == null ? "" : countData.get("UOM").toString();
	        String countStatus = hDcount.get("COUNTSTATUS") == null ? "" :	hDcount.get("COUNTSTATUS").toString();

		if ("Issue On Receipt".equalsIgnoreCase(operatingMethod)) {
		 actonHand = "0";
		 countStatus = "Counted";
		}

		if (actonHand.length() > 0 && qty.length() > 0 && "Counted".equalsIgnoreCase(countStatus)) {
		 noneToUpd = false;
		 linecheckcount++;
		 boolean line_result = false;

		 try {
			connect1 = db.getConnection();
			cs = connect1.prepareCall("{call p_ig_count_update(?,?,?,?,?,?,?,?,?,?,?,?)}");
			cs.setInt(1, Integer.parseInt(count_id));
			cs.setString(2, item_id);
			cs.setString(3, companyId);
			cs.setString(4, catalogId);
			cs.setString(5, catPartNo);
			if (partGroupNo.length() > 0)
			{
			 cs.setInt(6, Integer.parseInt(partGroupNo));
			}
			else
			{
			 cs.setNull(6,Types.INTEGER);
			}
			cs.setInt(7, Integer.parseInt(personnelId));
			BigDecimal bigActonHand = new BigDecimal(actonHand);
			cs.setBigDecimal(8, bigActonHand);
			cs.setString(9, countType);

			cs.registerOutParameter(10, Types.VARCHAR);
			cs.registerOutParameter(11, Types.INTEGER);
			cs.registerOutParameter(12, Types.VARCHAR);
			//cs.registerOutParameter(13, Types.VARCHAR);
			cs.execute();

			String erm = cs.getString(10);
			String prnumber = cs.getString(11);
			/*String countgtinv = cs.getString(10);
			if (countgtinv == null) {
			 countgtinv = "";
			}
			countgtinv = countgtinv.trim();*/

			if (!"OK".equalsIgnoreCase(erm)) {
       hiclog.info("Done calling p_ig_count_update Hub: " + hubnum +	 "  invengrp : " + invengrp + " item_id: " + item_id + " invqty: " + qty + " actonhand: " + actonHand + " errormesage:" + erm + "");
       mainErrormsg += "Error For Item: "+item_id+" "+ erm + "<BR>";
			 result = false;
			}
			else {
			 if (prnumber != null) {
				finalmrlist += prnumber + ",";
			 }
			 result = true;
			 line_result = true;
			}

			/*if ("Y".equalsIgnoreCase(countgtinv)) {
			 //hiclog.debug(" Inreasing Credit Count: "+countgtinv+"");
			 creditcnt++;
			}*/

			connect1.commit();
			if (cs != null) {
			 cs.close();
			}
		 }
		 catch (Exception e) {
			//e.printStackTrace();
			radian.web.emailHelpObj.senddatabaseHelpemails(
			 "Error calling p_ig_count_update  in Item Count Page",
			 "Error calling p_ig_count_update \nError Msg:\n" + e.getMessage() +
			 "\n invengrp " + invengrp + "\n  Hub " + hubnum + "\n  count_id " +
			 count_id + "\n  actonhand " + actonHand + "\n   item_id " + item_id +
			 "\n  Personnel ID  " + personnelId + "");

			result = false;
		 }

		 if (false == line_result) {
			completeSuccess = false;
			result = false;
			hDcount.remove("UPDATESTATUS");
			hDcount.put("UPDATESTATUS", "NO");
			errordata.addElement(hDcount);
		 }
		 else {
		 }
		}
	 } //end of for

	 /*if (creditcnt > 0) {
		result = this.issuecredit(hubnum, inventoryGroup, creditatoverage, personnelId);
	 }*/
	}
	catch (Exception e) {
	 result = false;
	 e.printStackTrace();
	 out.print(radian.web.HTMLHelpObj.printError("label.hubitemcunt",intcmIsApplication,res));
	}

	if (finalmrlist.length() > 0) {
	 finalmrlist = finalmrlist.substring(0, finalmrlist.length() - 1);
	}
	String wwwHome = radian.web.tcmisResourceLoader.gethosturl();
	wwwHome = wwwHome.substring(0, wwwHome.length() - 1);
	String resulturl = wwwHome +
	 radian.web.tcmisResourceLoader.getcabupconfirmurl();
	/*radian.web.emailHelpObj.sendnawazemail("Item Count Processing Results",
	 "The item count has been processed, please go to the URL below to look at the results.\n\n\n" + resulturl +
	 "UserAction=Search&mrslist=" + finalmrlist + "\n\n\nPersonnel ID : " +
	 personnelId + "");*/
	radian.tcmis.server7.share.helpers.HelpObjs.sendMail(db,
	 "Item Count Processing Results",
	 "The item count has been processed, please go to the URL below to look at the results.\n\n\n" + resulturl +
	 "UserAction=Search&mrslist=" + finalmrlist + "", Integer.parseInt(personnelId), true);

	updateresult.put("RESULT", new Boolean(result));
	updateresult.put("ERRORDATA", errordata);

	mainErrormsg += this.closeopencount(iscollection, hubnum,
	 inventoryGroup, personnelId);

	return updateresult;
 }

 private String startNewCount(boolean iscollection,
	String branchPlant, String inventoryGroup, String personnelid) {
	Connection connect1;
	CallableStatement cs = null;
	String errorMessage = "";

	try {
	 connect1 = db.getConnection();
	 cs = connect1.prepareCall("{call p_start_count(?,?,?,?,?)}");
	 if (inventoryGroup.length() == 0 && branchPlant.length() > 0) {
		inventoryGroup = "All";
	 }
	 cs.setString(1, inventoryGroup);
	 cs.setString(2, branchPlant);
	 cs.setInt(3, Integer.parseInt(personnelid));
	 if (iscollection) {
		cs.setString(4, "y");
	 }
	 else {
		cs.setNull(4, Types.VARCHAR);
	 }
	 cs.registerOutParameter(5, Types.VARCHAR);
	 cs.execute();
	 errorMessage = cs.getString(5);
	 //hiclog.info("Done calling p_start_count Hub:   " + branchPlant +	"   inventoryGroup : " + inventoryGroup + "  personnelid  "+personnelid+" errormesage    " +		errorMessage + "");
	 connect1.commit();
	 if (cs != null) {
		cs.close();
	 }
	}
	catch (Exception e) {
	 //e.printStackTrace();
	 radian.web.emailHelpObj.senddatabaseHelpemails(
		"Error calling p_start_count in Item Count SD Page",
		"Error calling p_start_count\nError Msg:\n" + e.getMessage() + "\n Hub:" +
		branchPlant + "\n inventoryGroup:" + inventoryGroup + " Personnel ID  " +
		personnelid + "");
	}

	if (errorMessage == null) {
	 errorMessage = "";
	}

	return errorMessage;
 }

 private String closeopencount(boolean iscollection,
	String branchPlant, String inventoryGroup, String personnelid) {
	Connection connect1;
	CallableStatement cs = null;
	String errorMessage = "";

	try {
	 connect1 = db.getConnection();
	 cs = connect1.prepareCall("{call p_end_count(?,?,?,?,?)}");
	 if (inventoryGroup.length() == 0 && branchPlant.length() > 0) {
		inventoryGroup = "All";
	 }
	 cs.setString(1, inventoryGroup);
	 cs.setString(2, branchPlant);
	 cs.setInt(3, Integer.parseInt(personnelid));

	 if (iscollection) {
		cs.setString(4, "y");
	 }
	 else {
		cs.setNull(4, Types.VARCHAR);
	 }
	 cs.registerOutParameter(5, Types.VARCHAR);
	 cs.execute();

	 errorMessage = cs.getString(5);
	 //hiclog.info("Done calling p_end_count  Hub:   " + branchPlant +	"   inventoryGroup : " + inventoryGroup + "  personnelid  "+personnelid+" errormesage  "+errorMessage+"");
	 connect1.commit();
	 if (cs != null) {
		cs.close();
	 }
	}
	catch (Exception e) {
	 //e.printStackTrace();
	 radian.web.emailHelpObj.senddatabaseHelpemails(
		"Error calling p_end_count in Item Count SD Page",
		"Error calling p_end_count\nError Msg:\n" + e.getMessage() + "\n Hub:" +
		branchPlant + "\n inventoryGroup:" + inventoryGroup + " Personnel ID  " +
		personnelid + " errormesage  " + errorMessage + "");
	 //result=false;
	}

	if (errorMessage == null) {
	 errorMessage = "";
	}

	return errorMessage;
 }

 /*private String closeCountIfReceipt (boolean iscollection,
	 String branchPlant, String inventoryGroup, String personnelid) {
	 Connection connect1;
	 CallableStatement cs = null;
	 String errorMessage = "";

	 try {
		connect1 = db.getConnection();
		cs = connect1.prepareCall("{call p_close_count_if_receipt (?,?,?,?,?)}");
		if (inventoryGroup.length() == 0 && branchPlant.length() > 0) {
		 inventoryGroup = "All";
		}
		cs.setString(1, inventoryGroup);
		cs.setString(2, branchPlant);
		cs.setInt(3, Integer.parseInt(personnelid));

		if (iscollection) {
		 cs.setString(4, "y");
		}
		else {
		 cs.setNull(4, Types.VARCHAR);
		}
		cs.registerOutParameter(5, Types.VARCHAR);
		cs.execute();

		errorMessage = cs.getString(5);
		hiclog.debug("Done calling p_close_count_if_receipt errormesage  "+errorMessage+"");
		connect1.commit();
		if (cs != null) {
		 cs.close();
		}
	 }
	 catch (Exception e) {
		e.printStackTrace();
		radian.web.emailHelpObj.senddatabaseHelpemails(
		 "Error calling p_close_count_if_receipt in Item Count SD Page",
		 "Error calling p_close_count_if_receipt\nError Msg:\n" + e.getMessage() + "\n Hub:" +
		 branchPlant + "\n inventoryGroup:" + inventoryGroup + " Personnel ID  " +
		 personnelid + " errormesage  " + errorMessage + "");
		//result=false;
	 }

	 if (errorMessage == null) {
		errorMessage = "";
	 }

	 return errorMessage;
	}*/

	private String setDateCounted(boolean iscollection,
	 String branchPlant, String inventoryGroup, String personnelid) {
	 Connection connect1;
	 CallableStatement cs = null;
	 String errorMessage = "";

	 try {
		connect1 = db.getConnection();
		cs = connect1.prepareCall("{call p_set_date_counted (?,?,?,?,?,?)}");
		if (inventoryGroup.length() == 0 && branchPlant.length() > 0) {
		 inventoryGroup = "All";
		}
		cs.setString(1, inventoryGroup);
		cs.setString(2, branchPlant);
		cs.setInt(3, Integer.parseInt(personnelid));

		if (iscollection) {
		 cs.setString(4, "y");
		}
		else {
		 cs.setNull(4, Types.VARCHAR);
		}

	 if (actualCountDate.length() == 10) {
		cs.setTimestamp(5, radian.web.HTMLHelpObj.getDateFromString("", actualCountDate));
	 }
	 else {
		cs.setNull(5, java.sql.Types.DATE);
	 }

	 cs.registerOutParameter(6, Types.VARCHAR);
	 cs.execute();

	 errorMessage = cs.getString(6);
	 hiclog.debug("Done calling p_set_date_counted errormesage  " + errorMessage + "");
	 connect1.commit();
	 if (cs != null) {
		cs.close();
	 }
	}
	catch (Exception e) {
	 //e.printStackTrace();
	 radian.web.emailHelpObj.senddatabaseHelpemails(
		"Error calling p_set_date_counted in Item Count SD Page",
		"Error calling p_set_date_counted \nError Msg:\n" + e.getMessage() + "\n Hub:" +
		branchPlant + "\n inventoryGroup:" + inventoryGroup + " Personnel ID  " +
		personnelid + " actualCountDate "+actualCountDate+" errormesage  " + errorMessage + "");
	}

	if (errorMessage == null) {
	 errorMessage = "";
	}

	return errorMessage;
 }

 /*private boolean issuecredit(String hubnum, String invengrp,
	String creditatoverage, String personneld) {
	boolean result = false;
	Connection connect1;
	CallableStatement cs = null;

	try {
	 connect1 = db.getConnection();
	 cs = connect1.prepareCall("{call p_item_count_to_inventory(?,?,?,?,?)}");
	 if (invengrp.length() == 0 && hubnum.length() > 0) {
		invengrp = "All";
	 }
	 cs.setString(1, invengrp);
	 cs.setString(2, hubnum);
	 cs.setInt(3, Integer.parseInt(personneld));
	 cs.setString(4, creditatoverage);
	 cs.registerOutParameter(5, Types.VARCHAR);
	 cs.execute();

	 String erm = cs.getString(5);
	 if (erm != null || "null".equalsIgnoreCase(erm)) {
		mainErrormsg += erm + "<BR>";
	 }
	 hiclog.info("Done calling p_item_count_to_inventory  Hub:   " + hubnum +
		"   inventoryGroup : " + invengrp + "  personnelid  "+personneld+" errormesage:" + erm + " ");

	 connect1.commit();
	 if (cs != null) {
		cs.close();
	 }

	 result = true;
	}
	catch (Exception e) {
	 e.printStackTrace();
	 radian.web.emailHelpObj.senddatabaseHelpemails(
		"Error calling item_count_to_inventory in Item Count SD Page",
		"Error calling item_count_to_inventory\nError Msg:\n" + e.getMessage() +
		"\n invengrp " + invengrp + "\n  Hub " + hubnum + "\n  creditatoverage " +
		creditatoverage + "\n  Personnel ID  " + personneld + "");

	 result = false;
	}

	return result;
 }*/

 private void buildHeader(Hashtable hub_list_set, String selHubnum,
	String category, Collection hubInventoryGroupOvBeanCollection,
	String selinvengrp, String errormsg, HttpSession session,PrintWriter out) {
	//StringBuffer Msg=new StringBuffer();
	boolean opencntexists = false;

	try {
	 if (this.getupdateStatus() && (User_Sort.trim().length() > 0)) {
		opencntexists = true;
	 }

	 out.print(radian.web.HTMLHelpObj.printHTMLHeader(res.getString("itemCount"),"hubitemcount.js",intcmIsApplication));
	 out.println("<SCRIPT SRC=\"/js/common/formchek.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
	 out.println("<SCRIPT SRC=\"/js/calendar/newcalendar.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
	 out.println("<SCRIPT SRC=\"/js/calendar/AnchorPosition.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
	 out.println("<SCRIPT SRC=\"/js/calendar/PopupWindow.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
	 out.print("</HEAD>  \n");

	 out.print("<BODY>\n");

	 out.print("<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n");
	 out.print("<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>"+res.getString("msg.waitupdate")+"</B></FONT></center>\n");
	 out.print("</DIV>\n");
	 out.print("<DIV ID=\"MAINPAGE\" STYLE=\"\">\n");

	 out.print(radian.web.HTMLHelpObj.PrintTitleBar("<B>"+res.getString("itemCount")+"</B>\n"));
	 Hashtable hub_list = (Hashtable) (hub_list_set.get("HUB_LIST"));
	 if (hub_list.size() < 1) {
		out.println(radian.web.HTMLHelpObj.printMessageinTable("<BR><BR>"+res.getString("msg.noaccess")));
		return;
	 }

	 if (radian.web.poHelpObj.noItemCountingHubs(
		hubInventoryGroupOvBeanCollection)) {
		out.println(radian.web.HTMLHelpObj.printMessageinTable("<BR><BR>"+res.getString("label.noitemcountinggroup")+"<BR><BR>"+res.getString("label.contacttech")));
		return;
	 }

	 out.print(
		"<SCRIPT LANGUAGE=\"JavaScript\" TYPE=\"text/javascript\">\n <!--\n");
	 //out.print( radian.web.poHelpObj.createhubinvgrjs( iniinvendata,hub_list_set ) );
	 out.println(radian.web.poHelpObj.getHubInventoryGroupJavaScript(
		hubInventoryGroupOvBeanCollection, true, true));
	 out.print("// -->\n </SCRIPT>\n");

	 Hashtable hub_prority_list = (Hashtable) (hub_list_set.get(
		"HUB_PRORITY_LIST"));
	 if (selHubnum.trim().length() == 0) {
		//selHubnum=radian.web.HTMLHelpObj.intselectedhub( hub_prority_list.entrySet() );
		selHubnum = radian.web.HTMLHelpObj.getFirstBranchPlant(
		 hubInventoryGroupOvBeanCollection);
	 }

	 String servlettocall = radian.web.tcmisResourceLoader.hubitemcounturl();
	 out.print("<FORM method=\"POST\" NAME=\"receiving\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\"" +
		servlettocall + "\">\n");
	 out.print("<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"5\" WIDTH=\"100%\" CLASS=\"moveup\">\n");
	 out.print("<TR>\n");

	 //Hub
	 out.print("<TD WIDTH=\"7%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
	 out.print("<B>"+res.getString("label.hub")+":</B>&nbsp;\n");
	 out.print("</TD>\n");
	 out.print("<TD WIDTH=\"10%\">\n");
	 out.print("<SELECT CLASS=\"HEADER\" NAME=\"HubName\" OnChange=\"hubchanged(document.receiving.HubName)\" size=\"1\">\n");
	 //Hashtable hub_list= ( Hashtable ) ( hub_list_set.get( "HUB_LIST" ) );

	 out.println(radian.web.HTMLHelpObj.getHubDropDown(
		hubInventoryGroupOvBeanCollection, selHubnum));
	 //out.print(radian.web.HTMLHelpObj.sorthashbyValue(hub_prority_list.entrySet(),selHubnum,hub_list));
	 out.print("</SELECT>\n");
	 out.print("</TD>\n");

	 out.print("<TD WIDTH=\"7%\" ALIGN=\"LEFT\">\n");
	 if (!opencntexists && this.getupdateStatus()) {
		out.print("<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\""+res.getString("label.startnewcount")+"\" onclick= \"return StartNew(this)\" NAME=\"StartNewCount\">&nbsp;\n");
	 }
	 else {
		out.print("&nbsp;");
	 }
	 out.print("</TD>\n");

	 out.print("<TD WIDTH=\"10%\" ALIGN=\"LEFT\">\n");
	 out.print("<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\""+res.getString("label.search")+"\" onclick= \"return searchcount(this)\" NAME=\"searchButton\">&nbsp;\n");
	 out.print("</TD>\n");

	 out.print("<TD WIDTH=\"10%\" ALIGN=\"LEFT\">\n");
	 if (opencntexists) {
		out.print("<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\""+res.getString("label.process")+"\" onclick= \"return update(this)\" NAME=\"processButton\">&nbsp;\n");
	 }
	 else {
		out.print("&nbsp;\n");
	 }
	 out.print("</TD>\n");

	 //Create XLS
	 out.print(
		"<TD WIDTH=\"5%\" ALIGN=\"LEFT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n");
	 if (opencntexists) {
		out.print("<INPUT TYPE=\"BUTTON\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\""+res.getString("label.createexcel")+"\" onclick= \"return createxls(this)\" NAME=\"createXlsButton\">&nbsp;\n");
	 }
	 else {
		out.print("&nbsp;\n");
	 }
	 out.print("</TD>\n");
	 out.print("</TR>\n");

	 out.print("<TR>\n");
	 out.print("<TD HEIGHT=45 WIDTH=\"7%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
	 out.print("<B>"+res.getString("label.invgrp")+":</B>&nbsp;\n");
	 out.print("</TD>\n");
	 out.print("<TD WIDTH=\"10%\">\n");
	 out.print("<SELECT CLASS=\"HEADER\"  NAME=\"invengrp\" onChange=\"RefreshPage(this)\" size=\"1\">\n");
	 out.println(radian.web.HTMLHelpObj.getInventoryGroupDropDown(
		hubInventoryGroupOvBeanCollection, selHubnum, selinvengrp, true));
	 out.print("</SELECT>\n");
	 out.print("</TD>\n");

	 out.print(
		"<TD WIDTH=\"7%\" ALIGN=\"RIGHT\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n");
	 out.print("<B>"+res.getString("label.sortby")+":</B>&nbsp;\n");
	 out.print("</TD>\n");
	 out.print("<TD WIDTH=\"10%\" ALIGN=\"LEFT\">\n");
	 out.print("<SELECT CLASS=\"HEADER\" NAME=\"Category\" onChange=\"RefreshPage(this)\" size=\"1\">\n");

	 Hashtable sortresult = new Hashtable();
	 sortresult.put(res.getString("label.description"), "i.INVENTORY_GROUP,i.DESCRIPTION");
	 sortresult.put(res.getString("label.bin"), "i.LAST_BIN,i.ITEM_ID");
	 sortresult.put(res.getString("label.partnumber"), "i.INVENTORY_GROUP,i.CAT_PART_NO");
	 sortresult.put(res.getString("label.itemid"), "i.INVENTORY_GROUP,i.ITEM_ID");

	 out.println(radian.web.HTMLHelpObj.getDropDown(sortresult, category));
	 out.print("</SELECT>\n");
	 out.print("</TD>\n");
	 out.print("<TD WIDTH=\"5%\" ALIGN=\"LEFT\">\n");
	 if (opencntexists) {
		out.print("<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\""+res.getString("label.additem")+"\" onclick= \"additem2count()\" NAME=\"additemtocount\">&nbsp;\n");
	 }
	 else {
		out.print("&nbsp;\n");
	 }
	 out.print("</TD>\n");

	 out.print("<TD WIDTH=\"10%\" ALIGN=\"LEFT\">\n");
	 if (opencntexists) {
		out.print("<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\""+res.getString("label.close")+"\" onclick= \"return closecount(this)\" NAME=\"closecount12\">&nbsp;\n");
	 }
	 else {
		out.print("&nbsp;\n");
	 }
	 out.print("</TD>\n");
	 out.print("<TD WIDTH=\"5%\" ALIGN=\"LEFT\">\n");
	 out.print("&nbsp;</TD>\n");
	 out.print("</TR>\n");
	 out.print("</TABLE>\n");

	 if (errormsg == null) {
		errormsg = "";
	 }
	 if (errormsg.length() > 0) {
		out.print(radian.web.HTMLHelpObj.printMessageinTable(errormsg));
	 }

	 out.print("<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\" CLASS=\"moveup\">\n");
	 out.print("<TR><td>");
	 out.print("<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\"NEW\">\n");
	 out.print("<INPUT TYPE=\"hidden\" NAME=\"SubUserAction\" VALUE=\" \">\n");
	 out.print("<INPUT TYPE=\"hidden\" NAME=\"toRedirectItem\" VALUE=\"No\">\n");
	 out.print("<INPUT TYPE=\"hidden\" NAME=\"RedirectItemValue\" VALUE=\"\">\n");
	 //out.print( "<INPUT TYPE=\"hidden\" NAME=\"countId\" VALUE=\"" + countDateH + "\">\n" );
	 out.print("<INPUT TYPE=\"hidden\" NAME=\"thedecidingRandomNumber\" VALUE=\"" +
		thedecidingRandomNumber + "\">\n");
	 out.print("</TD></TR>");
	 out.print("</TABLE>\n");
	}
	catch (Exception e) {
	 e.printStackTrace();
	 out.print(radian.web.HTMLHelpObj.printError("label.hubitemcunt",intcmIsApplication,res));
	}
	return;
 }

 private void buildDetails(Vector data, Vector allowedinvengrps, String actualCountDate,PrintWriter out) {
	//StringBuffer Msg = new StringBuffer();
	int i = 0;
	/*Hashtable sortresult=new Hashtable();
	sortresult.put( "Uncounted","Uncounted" );
	sortresult.put( "Counted","Counted" );
	sortresult.put( "Excluded","Excluded" );*/

	out.println("<DIV ID=\"COUNTDETAILS\" STYLE=\"\">\n");
	out.println("<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\">\n");
	out.println("<TR><TD WIDTH=\"5%\" ALIGN=\"LEFT\">");
	out.println("<FONT SIZE=\"3\" FACE=\"Arial\" COLOR=\"#fc0303\"><B><A HREF=\"javascript:showitemcountpagelegend()\" STYLE=\"color:#0000ff;cursor:hand\">Legend</A></B></FONT></TD>\n");
	out.println("<TD WIDTH=\"25%\" ALIGN=\"LEFT\">");
	out.println(res.getString("label.showSearchResultDate")+": <B>" + User_Sort + "</B></TD>");
	//Actual Count Date
	out.println("<TD WIDTH=\"7%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	out.println("<B>"+res.getString("label.actualCountDate2")+":<BR>("+res.getString("label.mmddyyyy")+")</B>&nbsp;\n" );
	out.println("</TD>\n" );
	out.println("<TD  ALIGN=\"LEFT\" WIDTH=\"10%\">\n" );
	out.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"actualCountDate\" ID=\"actualCountDate\" value=\"" + actualCountDate + "\" maxlength=\"10\" size=\"6\" onChange=\"checkLastDateReceived(false)\">\n" );
	out.println("<FONT SIZE=\"4\"><a href=\"javascript: void(0);\" ID=\"linkactualCountDate\" onClick=\"checkCount = 0;return getCalendar(document.receiving.actualCountDate);\">&diams;</a></FONT></td>\n" );
	out.println("<input type=\"hidden\" name=\"initialActualCountDate\" value=\"" + actualCountDate + "\">\n");
	out.println("<input type=\"hidden\" name=\"countStartDate\" value=\"" + countStartDate + "\">\n");
	out.println("</TD>\n" );
	out.println("<TR>");

	try {
	 Hashtable summary = new Hashtable();
	 summary = (Hashtable) data.elementAt(0);
	 int total = ( (Integer) (summary.get("TOTAL_NUMBER"))).intValue();
	 out.print("<BR><TABLE BORDER=\"0\" BGCOLOR=\"#a2a2a2\" CELLSPACING=\"1\" CELLPADDING=\"5\" WIDTH=\"100%\">\n");

	 for (int loop = 0; loop < total; loop++) {
		i++;
		boolean createHdr = false;

		if (loop % 10 == 0) {
		 createHdr = true;
		}

		if (createHdr) {
		 out.print("<tr align=\"center\">\n");
		 out.print("<TH width=\"5%\" ROWSPAN=\"2\" height=\"25\">"+res.getString("label.partnumber")+"</TH>\n");
		 out.print("<TH width=\"5%\" ROWSPAN=\"2\" height=\"25\">"+res.getString("label.invgrp")+"</TH>\n");
		 out.print("<TH width=\"5%\" ROWSPAN=\"2\" height=\"25\">"+res.getString("label.item")+"</TH>\n");
		 out.print("<TH width=\"20%\" ROWSPAN=\"2\" height=\"25\">"+res.getString("label.description")+"</TH>\n");
		 out.print("<TH width=\"5%\" colspan=\"2\" height=\"25\">"+res.getString("label.inpurchasing")+"</TH>\n");
		 out.print("<TH width=\"5%\" colspan=\"2\" height=\"25\">"+res.getString("label.onhand")+"</TH>\n");
		 out.print("<TH width=\"5%\" ROWSPAN=\"2\" height=\"25\">"+res.getString("label.bin")+"</TH>\n");
		 out.print("<TH width=\"5%\" ROWSPAN=\"2\" height=\"25\">"+res.getString("label.actualcount")+"</TH>\n");
		 out.print("<TH width=\"5%\" ROWSPAN=\"2\" height=\"25\">"+res.getString("label.counted")+"\n");
		 if (loop == 0)
		 {
			out.print("<BR><INPUT TYPE=\"checkbox\" value=\"\" onClick=\"checkall('countStatus')\" NAME=\"chkall\"></TH>\n");
		 }
		 else
		 {
			out.print("</TH>\n");
		 }
		 out.print("</TR>\n");
		 out.print("<tr align=\"center\">\n");
		 out.print("<TH width=\"5%\"  height=\"25\">"+res.getString("label.inpurchasing")+"</TH>\n");
		 out.print("<TH width=\"5%\" height=\"25\">"+res.getString("inventory.label.inventoryuom")+"</TH>\n");
		 out.print("<TH width=\"5%\" height=\"25\">"+res.getString("label.qty")+"</TH>\n");
		 out.print("<TH width=\"5%\" height=\"25\">"+res.getString("label.countuom")+"</TH>\n");
		 out.print("</TR>\n");
		}

		Hashtable countData = new Hashtable();
		countData = (Hashtable) data.elementAt(i);
		//String hub = countData.get("HUB")==null?"&nbsp;":countData.get("HUB").toString();
		String item_id = countData.get("ITEM_ID") == null ? "&nbsp;" :
		 countData.get("ITEM_ID").toString();
		String description = countData.get("DESCRIPTION") == null ? "&nbsp;" :
		 countData.get("DESCRIPTION").toString();
		String packaging = countData.get("PACKAGING") == null ? "&nbsp;" :
		 countData.get("PACKAGING").toString();
		String qty = countData.get("QTY") == null ? "" :
		 countData.get("QTY").toString();
		String actonHand = countData.get("ACTUAL_ON_HAND") == null ? "" :
		 countData.get("ACTUAL_ON_HAND").toString();
		 //String zeroetomon=countData.get( "ZERO_AT_END_OF_MONTH" ) == null ? "" : countData.get( "ZERO_AT_END_OF_MONTH" ).toString();
		String catpartnum = countData.get("CAT_PART_NO") == null ? "" :
		 countData.get("CAT_PART_NO").toString();
		String itemuom = countData.get("UOM") == null ? "" :
		 countData.get("UOM").toString();
		String invenGroup = countData.get("INVENTORY_GROUP") == null ? "" :
		 countData.get("INVENTORY_GROUP").toString();
		String inventoryGroupName = countData.get("INVENTORY_GROUP_NAME") == null ?
		 "" : countData.get("INVENTORY_GROUP_NAME").toString();
		String operatingMethod = countData.get("RECEIPT_PROCESSING_METHOD") == null ? "" :
		 countData.get("RECEIPT_PROCESSING_METHOD").toString();
		String lastDateOfReceipt = countData.get("LAST_DATE_OF_RECEIPT") == null ? "" :
		 countData.get("LAST_DATE_OF_RECEIPT").toString();
		String lastReceiptQcDate = countData.get("LAST_RECEIPT_QC_DATE") == null ? "" :
		 countData.get("LAST_RECEIPT_QC_DATE").toString();
		String countStatus = countData.get("COUNTSTATUS") == null ? "" :
		 countData.get("COUNTSTATUS").toString();
		String countType = countData.get("COUNT_TYPE") == null ? "" : countData.get("COUNT_TYPE").toString();
		String disabled = "";
		String countChecked = "";
		String lastDateCounted = countData.get("LAST_DATE_COUNTED") == null ? "" : countData.get("LAST_DATE_COUNTED").toString();
		String inPurchasing = countData.get("IN_PURCHASING") == null ? "&nbsp;" : countData.get("IN_PURCHASING").toString();
 	  String lastBin = countData.get("LAST_BIN") == null ? "&nbsp;" : countData.get("LAST_BIN").toString();

		boolean countAllowed = false;
		if (allowedinvengrps.contains(invenGroup)) {
		 countAllowed = true;
		}

		String Color = " ";
		if (i % 2 == 0) {
		 Color = "CLASS=\"blue";
		}
		else {
		 Color = "CLASS=\"white";
		}

		if ("Issue On Receipt".equalsIgnoreCase(operatingMethod) &&
		 qty.length() > 0) {
		 Color = "CLASS=\"red";
		}

		boolean disableLine = false;
		if (countAllowed) {
		 try {
			if (actualCountDate.trim().length() == 10) {
			 if (lastDateOfReceipt.trim().length() == 10) {
				if (radian.web.HTMLHelpObj.getDateValueFromString(lastDateOfReceipt).after(radian.
				 web.HTMLHelpObj.getDateValueFromString(actualCountDate))) {
				 disableLine = true;
				}
			 }

			 if (lastReceiptQcDate.trim().length() == 10) {
				if (radian.web.HTMLHelpObj.getDateValueFromString(lastReceiptQcDate).after(
				 radian.web.HTMLHelpObj.getDateValueFromString(countStartDate))) {
				 disableLine = true;
				}
			 }
			}
		 }
		 catch (Exception ex) {
		 }
		}

		if (disableLine)
		{
		 Color = "CLASS=\"black";
		 disabled = "true";
		 countChecked = "checked";
		}

		out.print("<tr align=\"center\" " + Color + "\" ID=\"rowId"+i+"\">\n");
		out.print("<td align=\"left\" width=\"5%\">" + catpartnum + "</td>\n");
		out.print("<td align=\"left\" width=\"5%\">" + inventoryGroupName +"</td>\n");
		out.print("<td align=\"left\" width=\"5%\">" + item_id + "</td>\n");
		out.print("<td align=\"left\" width=\"5%\">" + description + "</td>\n");
		out.print("<td align=\"right\" width=\"5%\">" + inPurchasing + "</td>\n");
		out.print("<td align=\"left\" width=\"5%\">" + packaging + "</td>\n");
		out.print("<td align=\"right\" width=\"5%\">" + qty + "</td>\n");
		out.print("<td align=\"left\" width=\"5%\">" + itemuom + "</td>\n");
		out.print("<td align=\"left\" width=\"5%\">" + lastBin + "</td>\n");

		if (countAllowed) {
		 out.print("<td align=\"left\" width=\"5%\">\n<input type=\"text\" CLASS=\"DETAILS\" name=\"act_on_hand" +
			i + "\"  value=\"" + qty.trim() +
			"\" maxlength=\"30\" size=\"6\" onChange=\"checkValue('" + i +
			"')\"></td>\n");

		 out.print("<td width=\"2%\">\n");
		 out.println( "<INPUT TYPE=\"checkbox\" value=\"Counted\" "+countChecked+" NAME=\"countStatus" + i + "\" ID=\"countStatus" + i +"\" onClick=\"checkCheckBox('" + i +"')\">\n" );
		 out.print("</td>\n");
		}
		else {
		 out.print("<td align=\"left\" width=\"5%\">\n" + actonHand + "</td>\n");
		 out.print("<td align=\"left\" width=\"5%\">&nbsp;</td>\n");
		}

		out.print("<input type=\"hidden\" name=\"operatingMethod" + i +"\" value=\"" + operatingMethod + "\">\n");
		out.print("<input type=\"hidden\" name=\"lastDateOfReceipt" + i +"\" value=\"" + lastDateOfReceipt + "\">\n");
		out.print("<input type=\"hidden\" name=\"lastReceiptQcDate" + i +"\" value=\"" + lastReceiptQcDate + "\">\n");
		out.print("<input type=\"hidden\" name=\"lastDateCounted" + i +"\" value=\"" + lastDateCounted + "\">\n");
		out.print("<input type=\"hidden\" name=\"countAllowed" + i +"\" value=\"" + countAllowed + "\">\n");
		out.print("<input type=\"hidden\" name=\"itemId" + i +"\" value=\"" + item_id + "\">\n");
		out.print("<input type=\"hidden\" name=\"expectedQuantity" + i +"\" value=\"" + qty + "\">\n");
		out.print("<input type=\"hidden\" name=\"catPartNum" + i +"\" value=\"" + catpartnum + "\">\n");
		out.print("<input type=\"hidden\" name=\"countType" + i +"\" value=\"" + countType + "\">\n");
		out.print("<input type=\"hidden\" name=\"inventoryGroupName" + i +"\" value=\"" + inventoryGroupName + "\">\n");
		out.print("<input type=\"hidden\" name=\"disabled" + i +"\" value=\""+disabled+"\">\n");

		out.print("</TR>\n");
	 }

	 out.print("</TABLE>\n");
	 out.print("<input type=\"hidden\" name=\"totalNumber\" value=\"" + i +	"\">\n");
	 out.print("<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\" CLASS=\"moveup\">\n");
	 out.print("<TR>");
	 out.print(
		"<TD HEIGHT=38 WIDTH=\"100%\" VALIGN=\"MIDDLE\" BGCOLOR=\"#000066\">&nbsp;\n");
	 out.print("</TD></TR>");
	 out.print("</TABLE>\n");
	 out.print("</form>\n");
	 out.print("</body></html>\n");
	}
	catch (Exception e) {
	 e.printStackTrace();
	 out.print(radian.web.HTMLHelpObj.printError("label.hubitemcunt",intcmIsApplication,res));
	}

	out.print("</DIV>\n");
	return;
 }
}