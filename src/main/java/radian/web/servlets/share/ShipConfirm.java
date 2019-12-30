package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import java.util.Collection;
import java.util.Random;
import java.io.FileOutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.tcmis.common.admin.beans.PersonnelBean;
import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import com.tcmis.common.admin.process.HubInventoryGroupProcess;
import radian.tcmis.server7.share.helpers.HelpObjs;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 1998
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 *
 * 10-29-02
 * When batch number is null changed it to blank not &nbsp;
 * 11-06-03 - Changed the query to not include transfer requests, Code cleanup
 * 12-11-03 - Changed query to show stuff with transfer_request_id = 0
 * 02-16-04 - Sorting Drop Downs
 * 02-20-04 - Made the long ibg query into a view
 * 03-01-04 - Ordering by MR,LINE ITEM, PICKLIST
 * 04-25-04 - Changed the ship confirm process to conrim as shipments
 * 05-16-05 - Log statements to find a bug
 * 07-06-05 - Showing Delivery Point and allowing the user to sort the page by different options.
 * Can not release this because phoenix is doing something with the ship confirm page. By exporting it into excel
 *
 */

public class ShipConfirm {
  private ServerResourceBundle bundle = null;
  private TcmISDBModel db = null;
  private PrintWriter out = null;
  private boolean completeSuccess = true;
  private boolean noneToUpd = true;
  private boolean allowupdate;
  private static org.apache.log4j.Logger shipConfirmLog = org.apache.log4j.Logger.getLogger(ShipConfirm.class);
  private String errormsg = "";
	private boolean intcmIsApplication = false;

  public void setupdateStatus(boolean id) {
    this.allowupdate = id;
  }

  private boolean getupdateStatus() throws Exception {
    return this.allowupdate;
  }

  public ShipConfirm(ServerResourceBundle b, TcmISDBModel d) {
    bundle = b;
    db = d;
  }

  //Process the HTTP Post request passed from whoever called it
  public void doResult(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
    out = response.getWriter();
    response.setContentType("text/html");

    //String branch_plant= ( String ) session.getAttribute( "BRANCH_PLANT" );
    String branch_plant = request.getParameter("HubName");
    if (branch_plant == null) {
      branch_plant = "";
    }
    String personnelid = BothHelpObjs.makeBlankFromNull( (String) session.getAttribute("PERSONNELID"));
    String CompanyID = session.getAttribute("COMPANYID").toString();
    Collection hubInventoryGroupOvBeanCollection = new Vector( (Collection) session.getAttribute("hubInventoryGroupOvBeanCollection"));
		PersonnelBean personnelBean = (PersonnelBean) session.getAttribute("personnelBean");
		Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
		if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
		{
		 intcmIsApplication = true;
		}

    String invengrp = request.getParameter("invengrp");
    if (invengrp == null) {
      invengrp = "";
    }
    invengrp = invengrp.trim();

    HubInventoryGroupProcess hubInventoryGroupProcess = new
        HubInventoryGroupProcess("hub");
    boolean isCollection = hubInventoryGroupProcess.isCollection(
        hubInventoryGroupOvBeanCollection, branch_plant, invengrp);

    String User_Action = null;
    User_Action = request.getParameter("UserAction");
    if (User_Action == null) {
      User_Action = "New";

    }
    String datedelivered = "";
    datedelivered = request.getParameter("datedelivered");
    if (datedelivered == null || datedelivered.length() < 10) {
      datedelivered = radian.web.HTMLHelpObj.getSysDate(db);
    }

    String sortResultsBy = request.getParameter("sortResultsBy");
    if (sortResultsBy == null) {
      sortResultsBy = "SHIP_TO_LOCATION_ID,DELIVERY_POINT";

    }
    try {
      Hashtable hub_list_set = new Hashtable();
      hub_list_set = (Hashtable) session.getAttribute("HUB_PREF_LIST");

      Hashtable ValidShipmentIds = new Hashtable();
      ValidShipmentIds = (Hashtable) session.getAttribute("VALID_SHIPMENT_IDS");
      boolean autoShipConfirmHub = false;

      Vector autoShipConfirmlistV = new Vector();
      if (!User_Action.equalsIgnoreCase("New")) {
        autoShipConfirmlistV = (Vector) session.getAttribute("AUTO_SHIP_CONFIRM_LIST");
        if (autoShipConfirmlistV == null) {
          autoShipConfirmlistV = radian.web.HTMLHelpObj.autoShipConfirmlist(db);
          session.setAttribute("AUTO_SHIP_CONFIRM_LIST", autoShipConfirmlistV);
        }
        if (autoShipConfirmlistV.contains(branch_plant)) {
          autoShipConfirmHub = true;
        }
      }

      if (User_Action.equalsIgnoreCase("New")) {
        autoShipConfirmlistV = radian.web.HTMLHelpObj.autoShipConfirmlist(db);
        session.setAttribute("AUTO_SHIP_CONFIRM_LIST", autoShipConfirmlistV);

        /*Hashtable initialinvData = new Hashtable ();
           Hashtable hub_list1= ( Hashtable ) ( hub_list_set.get( "HUB_LIST" ) );
           if (hub_list1.size() > 0)
           {
          initialinvData=radian.web.poHelpObj.getinvgrpInitialData( db,hub_list_set );
          session.setAttribute( "INVENGRP_DATA",initialinvData );
           }*/

        if (autoShipConfirmlistV.contains(branch_plant)) {
          autoShipConfirmHub = true;
        }

        buildHeader(branch_plant, hub_list_set, "", "", "", session, autoShipConfirmlistV, hubInventoryGroupOvBeanCollection, invengrp, sortResultsBy);
        out.println(radian.web.HTMLHelpObj.printHTMLSelect());
        out.close();
        hub_list_set = null;
      } else if (User_Action.equalsIgnoreCase("Search")) {
        buildSearchScreen(isCollection, session, branch_plant, hub_list_set, datedelivered, invengrp, sortResultsBy);
      } else if (User_Action.equalsIgnoreCase("generatexls")) {
        Vector returnData = (Vector) session.getAttribute("DATA");
        buildXlsDetails(personnelid, returnData);
      } else if (User_Action.equalsIgnoreCase("PrintBOXLabels")) {
        Vector retrn_data = (Vector) session.getAttribute("DATA");
        Vector print_data = SynchprintData(request, retrn_data);
        session.setAttribute("PRINTDATA", print_data);
        Vector synch_data = SynchServerData(request, retrn_data, datedelivered);
        session.setAttribute("DATA", synch_data);
        //Hashtable iniInvenData= ( Hashtable ) session.getAttribute( "INVENGRP_DATA" );
        buildHeader(branch_plant, hub_list_set, "GENERATE_BOX_LABELS", "Yes", datedelivered, session, autoShipConfirmlistV, hubInventoryGroupOvBeanCollection, invengrp, sortResultsBy);
        buildDetails(synch_data, "", autoShipConfirmHub, ValidShipmentIds);
      } else if (User_Action.equalsIgnoreCase("PrintBOLDetail")) {
        Vector retrn_data = (Vector) session.getAttribute("DATA");
        Vector synch_data = SynchServerData(request, retrn_data, datedelivered);
        session.setAttribute("DATA", synch_data);
        //Hashtable iniInvenData= ( Hashtable ) session.getAttribute( "INVENGRP_DATA" );
        buildHeader(branch_plant, hub_list_set, "GENERATE_LABELS", "Yes", datedelivered, session, autoShipConfirmlistV, hubInventoryGroupOvBeanCollection, invengrp, sortResultsBy);
        buildDetails(synch_data, "", autoShipConfirmHub, ValidShipmentIds);
      } else if (User_Action.equalsIgnoreCase("PrintBOL")) {
        Vector retrn_data = (Vector) session.getAttribute("DATA");
        Vector synch_data = SynchServerData(request, retrn_data, datedelivered);
        session.setAttribute("DATA", synch_data);
        //Hashtable iniInvenData= ( Hashtable ) session.getAttribute( "INVENGRP_DATA" );
        buildHeader(branch_plant, hub_list_set, "GENERATE_LABELS", "No", datedelivered, session, autoShipConfirmlistV, hubInventoryGroupOvBeanCollection, invengrp, sortResultsBy);
        buildDetails(synch_data, "", autoShipConfirmHub, ValidShipmentIds);
      } else if (User_Action.equalsIgnoreCase("Update")) {
        Vector retrn_data = (Vector) session.getAttribute("DATA");
        Vector synch_data = SynchServerData(request, retrn_data, datedelivered);

        retrn_data = null;
        session.setAttribute("DATA", synch_data);
        session.removeAttribute("PRINTDATA");
        Hashtable updateresults = UpdateDetails(synch_data, personnelid, datedelivered, branch_plant);
        Boolean result = (Boolean) updateresults.get("RESULT");
        Vector errordata = (Vector) updateresults.get("ERRORDATA");

        boolean resulttotest = result.booleanValue();
        //Hashtable iniInvenData= ( Hashtable ) session.getAttribute( "INVENGRP_DATA" );
        buildHeader(branch_plant, hub_list_set, "", "", datedelivered, session, autoShipConfirmlistV, hubInventoryGroupOvBeanCollection, invengrp, sortResultsBy);

        if (false == resulttotest) {
          if (true == noneToUpd) {
            out.println(radian.web.HTMLHelpObj.printMessageinTable("No Item was Choosen for Shipment Confirmation"));
            buildDetails(errordata, "Update", autoShipConfirmHub, ValidShipmentIds);
          } else {
            out.println(radian.web.HTMLHelpObj.printMessageinTable(
                "An Error Occurred.<BR>Please Check Data and try Again."));
            buildDetails(errordata, "Update", autoShipConfirmHub, ValidShipmentIds);
          }
          out.close();
        } else {
          if (true == completeSuccess) {
            out.println(radian.web.HTMLHelpObj.printMessageinTable("All Your Selections Were Successfully Updated"));
            buildDetails(errordata, "Update", autoShipConfirmHub, ValidShipmentIds);
            out.close();
          } else {
            out.println(radian.web.HTMLHelpObj.printMessageinTable("Some of Your Selections Shown below Were not Updated"));
            buildDetails(errordata, "Update", autoShipConfirmHub, ValidShipmentIds);
            out.close();
          }
        }

        //clean up
        errordata = null;
        updateresults = null;
      } else if (User_Action.equalsIgnoreCase("CreateShipment")) {
        Vector retrn_data = (Vector) session.getAttribute("DATA");
        Vector synch_data = SynchServerData(request, retrn_data, datedelivered);
        retrn_data = null;
        session.removeAttribute("PRINTDATA");
        createShipment(synch_data, personnelid);
        buildSearchScreen(isCollection, session, branch_plant, hub_list_set, datedelivered, invengrp, sortResultsBy);
      } else if (User_Action.equalsIgnoreCase("AddToShipment")) {
        Vector retrn_data = (Vector) session.getAttribute("DATA");
        Vector synch_data = SynchServerData(request, retrn_data, datedelivered);
        retrn_data = null;
        session.removeAttribute("PRINTDATA");
        updateShipment(synch_data, personnelid);
        buildSearchScreen(isCollection, session, branch_plant, hub_list_set, datedelivered, invengrp, sortResultsBy);
      } else if (User_Action.equalsIgnoreCase("PrintConsolPL")) {
        Vector searchdata = new Vector();
        searchdata = getShipmentData(request.getParameter("branchPlant").toString());
        buildShipmentConfirmHeader(searchdata, personnelid, request.getParameter("branchPlant").toString(), datedelivered, "Print Packing List");

        /*Vector retrn_data= ( Vector ) session.getAttribute( "DATA" );
           Vector synch_data=SynchServerData( request,retrn_data,datedelivered );
           retrn_data=null;
           session.removeAttribute( "PRINTDATA" );
           String shipmentIdList = printConsolPL( synch_data,personnelid );
           session.setAttribute("SHIPMENT_ID_LIST",shipmentIdList);
           buildSearchScreen( session,branch_plant,hub_list_set,datedelivered );*/
      }
      /*else if ( User_Action.equalsIgnoreCase( "RemoveFromShipment" ) )
          {
         Vector retrn_data= ( Vector ) session.getAttribute( "DATA" );
         Vector synch_data=SynchServerData( request,retrn_data,datedelivered );
         retrn_data=null;
         session.removeAttribute( "PRINTDATA" );
         removeFromShipment( synch_data,personnelid );
         buildSearchScreen( session,branch_plant,hub_list_set,datedelivered );
          }*/
      else if (User_Action.equalsIgnoreCase("consolidatedBol")) {
        Vector retrn_data = (Vector) session.getAttribute("DATA");
        Vector synch_data = SynchServerData(request, retrn_data, datedelivered);
        session.setAttribute("DATA", synch_data);
        String shipmentIdList = printConsolPL(synch_data, personnelid);
        session.setAttribute("SHIPMENT_ID_LIST", shipmentIdList);

        //Hashtable iniInvenData= ( Hashtable ) session.getAttribute( "INVENGRP_DATA" );
        buildHeader(branch_plant, hub_list_set, "ConsolidatedBol", "Yes", datedelivered, session, autoShipConfirmlistV, hubInventoryGroupOvBeanCollection, invengrp, sortResultsBy);
        buildDetails(synch_data, "", autoShipConfirmHub, ValidShipmentIds);
        retrn_data = null;
      } else if (User_Action.equalsIgnoreCase("AddTrackingNoPage")) {
        Vector retrn_data = (Vector) session.getAttribute("DATA");
        //Vector synch_data=SynchServerData( request,retrn_data,datedelivered );
        //retrn_data = null;
        session.removeAttribute("PRINTDATA");
        /*String rowCheckedIndex=request.getParameter( "rowCheckedIndex" );
           //remove the last commas ','
           Vector rowCheckedIndexV=new Vector();
           if ( rowCheckedIndex.length() > 0 )
           {
          rowCheckedIndex=rowCheckedIndex.substring( 0,rowCheckedIndex.length() - 1 );
          StringTokenizer st=new StringTokenizer( rowCheckedIndex,"," );
          while ( st.hasMoreTokens() )
          {
         rowCheckedIndexV.addElement( ( String ) st.nextElement() );
          }
           }*/
        Vector searchdata = new Vector();
        searchdata = getShipmentData(request.getParameter("branchPlant").toString());
        buildShipmentConfirmHeader(searchdata, personnelid, request.getParameter("branchPlant").toString(), datedelivered, "Shipment Confirm");
      } else if (User_Action.equalsIgnoreCase("AddTrackingNo")) {
        confirmShipment(request, datedelivered, personnelid);
      } else if (User_Action.equalsIgnoreCase("BuildCarrierInfoPage")) {
        searchCarrierInfo(request, false);
      } else if (User_Action.equalsIgnoreCase("SearchCarrierInfo")) {
        searchCarrierInfo(request, true);
      }
    } catch (Exception e) {
      e.printStackTrace();
			out.println(radian.web.HTMLHelpObj.printError("Ship Confirm",intcmIsApplication));
    }

    return;
  } //end of method

  private void buildXlsDetails(String personnelID, Vector data) {
    //StringBuffer Msg=new StringBuffer();
    String url = "";
    String file = "";

    Random rand = new Random();
    int tmpReq = rand.nextInt();
    Integer tmpReqNum = new Integer(tmpReq);

    String wwwHome = radian.web.tcmisResourceLoader.gethosturl();
    String writefilepath = radian.web.tcmisResourceLoader.getsaveltempreportpath();

    file = writefilepath + personnelID + tmpReqNum.toString() + "logistics.csv";
    url = wwwHome + "reports/temp/" + personnelID + tmpReqNum.toString() + "logistics.csv";

    try {
      PrintWriter pw = new PrintWriter(new FileOutputStream(file));

      Hashtable summary = new Hashtable();
      summary = (Hashtable) data.elementAt(0);
      int total = ( (Integer) (summary.get("TOTAL_NUMBER"))).intValue();

      if (total == 0) {
        //StringBuffer Msg=new StringBuffer();
        out.println("No Records Found ");
        return;
      }
      pw.print("OK,Shipment ID,Company,Facility,Work Area,Deliver To,Dock,Hazard Category,MR-Line,Picklist ID,Part,Quantity,Date Picked mm/dd/yyyy,Picker\n");

      int i = 0; //used for detail lines
      for (int loop = 0; loop < total; loop++) {
        i++;
        Hashtable hD = new Hashtable();
        hD = (Hashtable) data.elementAt(i);

        pw.print("\" \",");
        pw.print("\"" + hD.get("SHIPMENT_ID").toString() + "\",");
        pw.print("\"" + hD.get("COMPANY_ID").toString() + "\",");
        pw.print("\"" + hD.get("FACILITY_ID").toString() + "\",");
        pw.print("\"" + hD.get("APPLICATION_DESC").toString() + "\",");
        pw.print("\"" + hD.get("DELIVERY_POINT").toString() + "\",");
        pw.print("\"" + hD.get("SHIP_TO_LOCATION_ID").toString() + "\",");
        pw.print("\"" + hD.get("HAZARD_CATEGORY").toString() + "\",");
        pw.print("\"" + hD.get("PR_NUMBER").toString() + " - " + hD.get("LINE_ITEM").toString() + "\",");
        pw.print("\"" + hD.get("BATCH").toString() + "\",");
        pw.print("\"" + hD.get("CAT_PART_NO").toString() + "\",");
        pw.print("\"" + hD.get("QUANTITY").toString() + "\",");
        pw.print("\"" + hD.get("DATE_PICKED").toString() + "\",");
        pw.print("\"" + hD.get("ISSUER_NAME").toString() + "\"\n");
      }
      pw.close();
    } catch (Exception e) {
      e.printStackTrace();
      url = "";
    }

    if (url.length() > 0) {
      out.println("<HTML><HEAD>\n");
      out.println("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; url=" + url + "\">\n");
      out.println("<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n");
      out.println("<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n");
      out.println("<TITLE>Container Label Generator</TITLE>\n");
      out.println("</HEAD>  \n");
      out.println("<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">  \n");
      out.println("<CENTER><img src=\"/images/tcmintro.gif\"  border=1 align=\"middle\"><P></P><BR>\n");
      out.println("<FONT FACE=\"Arial\" size=\"5\" color=\"#000080\"><b>Downloading Excel File</b></font><P></P><BR>\n");
      out.println("<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b></b></font><P></P><BR>\n");
      out.println("<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b>Please Wait ...</b></font><P></P><BR>\n");
      out.println("</CENTER>\n");
      out.println("</BODY></HTML>    \n");
    } else {
      out.println("<HTML><HEAD>\n");
      out.println("<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n");
      out.println("<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n");
      out.println("<TITLE>Container Label Generator</TITLE>\n");
      out.println("</HEAD>  \n");
      out.println("<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">  \n");
      out.println("<CENTER><img src=\"/images/tcmintro.gif\"  border=1 align=\"middle\"><P></P><BR>\n");
      out.println("<FONT FACE=\"Arial\" size=\"5\" color=\"#000080\"><b>An Error Occured </b></font><P></P><BR>\n");
      out.println("<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b></b></font><P></P><BR>\n");
      out.println("<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b>Please try Again or contact Tech Center.</b></font><P></P><BR>\n");
      out.println("</CENTER>\n");
      out.println("</BODY></HTML>    \n");
    }
  }

  private void searchCarrierInfo(HttpServletRequest request, boolean searchData) {
    out.println(radian.web.HTMLHelpObj.printHTMLHeader("Search Carrier Info", "shipconfirm.js",intcmIsApplication));
    out.println("<BODY>\n");
    out.println(radian.web.HTMLHelpObj.PrintTitleBarNolink("<B>Search Carrier Info</B>\n"));
    out.println("<FORM METHOD=\"POST\" name=\"searchCarrierInfo\" action=\"" + bundle.getString("SHIP_CONFIRM") + "?UserAction=SearchCarrierInfo\" onsubmit =\"return auditSearchCarrierInfo(this)\">\n");
    //header info
    out.println("<TABLE BORDER=0 CELLPADDING=\"2\" WIDTH=100% >\n");
    //parent selected row
    String parentSelectedRow = request.getParameter("parentSelectedRow");
    if (parentSelectedRow == null) {
      parentSelectedRow = "";
    }
    //inventory group
    String inventoryGroup = request.getParameter("inventoryGroup");
    if (inventoryGroup == null) {
      inventoryGroup = "";
    }
    out.println("<INPUT TYPE=\"hidden\" NAME=\"inventoryGroup\" ID=\"inventoryGroup\" VALUE=\"" + inventoryGroup + "\">\n");
    out.println("<INPUT TYPE=\"hidden\" NAME=\"parentSelectedRow\" ID=\"parentSelectedRow\" VALUE=\"" + parentSelectedRow + "\">\n");
    out.println("<INPUT TYPE=\"hidden\" NAME=\"selectedRow\" ID=\"selectedRow\" VALUE=\"" + "" + "\">\n");
    //search text
    String searchText = request.getParameter("searchText");
    if (searchText == null) {
      searchText = "";
    }
    out.println("<TR><TD WIDTH=\"20%\" COLSPAN=\"2\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\" CLASS=\"announce\" >\n");
    out.println("<FONT FACE=\"Arial\" SIZE=\"2\">\n");
    out.println("<B>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Search Text: </B>\n");
    out.println("<INPUT type=\"text\" name=\"searchText\" value=\"" + searchText + "\" SIZE=\"30\">");
    out.println("</FONT>\n");
    out.println("</TD>\n");
    //search button
    out.println("<TD WIDTH=\"20%\" ALIGN=\"LEFT\" BGCOLOR=\"#E6E8FA\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n");
    out.println("<INPUT type=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" value=\"Search\" name=\"searchCarrierButton\">\n");
    out.println("</TD>\n");
    out.println("</TR>\n");
    //carrier code
    out.println("<TR><TD WIDTH=\"20%\" COLSPAN=\"2\" BGCOLOR=\"#E6E8FA\" ALIGN=\"LEFT\" VALIGN=\"MIDDLE\" CLASS=\"announce\" >\n");
    out.println("<FONT FACE=\"Arial\" SIZE=\"2\">\n");
    out.println("<B>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Carrier Code: </B>\n");
    out.println("<INPUT CLASS=\"INVISIBLEHEADBLUE\" type=\"text\" name=\"carrierCode\" value=\"" + "" + "\" SIZE=\"15\">");
    out.println("<INPUT CLASS=\"INVISIBLEHEADBLUE\" type=\"text\" name=\"carrierMethod\" value=\"" + "" + "\" SIZE=\"15\">");
    out.println("</FONT>\n");
    out.println("</TD>\n");
    //process button
    out.println("<TD WIDTH=\"20%\" ALIGN=\"LEFT\" BGCOLOR=\"#E6E8FA\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n");
    out.println("<INPUT type=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"processSearchCarrier\" value=\"Process\" OnClick=\"processCarrierInfo()\">\n");
    //cancel button
    out.println("<INPUT type=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"searchCarrierCancel\" value=\"Cancel\" OnClick=\"cancel()\">\n");
    out.println("</TD>\n");
    out.println("</TR>\n");
    //add a blank row here
    out.println("<TR><TD WIDTH=\"20%\" ALIGN=\"RIGHT\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n");
    out.println("&nbsp;\n");
    out.println("</TD>\n");
    out.println("</TR>\n");
    out.println("</TABLE>\n");
    //detail info
    buildSearchCarrierDetail(searchText, searchData, inventoryGroup);
    //
    out.println("</FORM>\n");
    out.println("</BODY>\n</HTML>\n");
  } //end of method

  private void buildSearchCarrierDetail(String searchText, boolean searchData, String inventoryGroup) {
    out.println("<TABLE BORDER=0 CELLPADDING=\"2\" WIDTH= 100% >\n");
    out.println("<TR align=\"center\">\n");
    out.println("<TH width=\"2%\"  height=\"38\">Carrier Code</TH>\n");
    out.println("<TH width=\"2%\"  height=\"38\">Carrier Name</TH>\n");
    out.println("<TH width=\"2%\"  height=\"38\">Method</TH>\n");
    out.println("<TH width=\"2%\"  height=\"38\">Account Owner</TH>\n");
    out.println("<TH width=\"2%\"  height=\"38\">Account #</TH>\n");
    out.println("</TR>\n");
    if (searchData) {
      DBResultSet dbrs = null;
      ResultSet rs = null;
      try {
        String query = "select carrier_code,carrier_name,carrier_method,carrier_owner,account from carrier_info_view" +
            " where inventory_group in ('ALL','" + inventoryGroup + "')";
        if (searchText.trim().length() > 0 && !"&nbsp;".equalsIgnoreCase(searchText)) {
          query += " and lower(search_text) like '%" + searchText.toLowerCase() + "%'";
        }
        dbrs = db.doQuery(query);
        rs = dbrs.getResultSet();
        int count = 0;
        while (rs.next()) {
          String color = " ";
          String bgColor = " ";
          if (count % 2 == 0) {
            color = "CLASS=\"INVISIBLEHEADWHITE";
            bgColor = "BGCOLOR=\"#FFFFFF";
          } else {
            color = "CLASS=\"INVISIBLEHEADBLUE";
            bgColor = "BGCOLOR=\"#E6E8FA";
          }
          out.println("<td width=\"2%\" height=\"38\" " + bgColor + "\" ALIGN=\"CENTER\">");
          out.println("<INPUT " + color + "\" TYPE=\"button\" size=10 \" NAME=\"carrierCode" + count + "\" ID=\"carrierCode" + count + "\" value=\"" + BothHelpObjs.makeBlankFromNull(rs.getString("carrier_code")) + "\" OnClick=\"carrierCodeClicked(" + count + ")\">\n");
          out.println("</td>");
          out.println("<td width=\"2%\" height=\"38\" " + bgColor + "\" ALIGN=\"CENTER\">");
          out.println("<INPUT " + color + "\" TYPE=\"button\" size=10 \" NAME=\"carrierName" + count + "\" ID=\"carrierName" + count + "\" value=\"" + BothHelpObjs.makeBlankFromNull(rs.getString("carrier_name")) + "\" OnClick=\"carrierCodeClicked(" + count + ")\">\n");
          out.println("</td>");
          out.println("<td width=\"2%\" height=\"38\" " + bgColor + "\" ALIGN=\"CENTER\">");
          out.println("<INPUT " + color + "\" TYPE=\"button\" size=10 \" NAME=\"carrierMethod" + count + "\" ID=\"carrierMethod" + count + "\" value=\"" + BothHelpObjs.makeBlankFromNull(rs.getString("carrier_method")) + "\" OnClick=\"carrierCodeClicked(" + count + ")\">\n");
          out.println("</td>");
          out.println("<td width=\"2%\" height=\"38\" " + bgColor + "\" ALIGN=\"CENTER\">");
          out.println("<INPUT " + color + "\" TYPE=\"button\" size=10 \" NAME=\"accountOwner" + count + "\" ID=\"accountOwner" + count + "\" value=\"" + BothHelpObjs.makeBlankFromNull(rs.getString("carrier_owner")) + "\" OnClick=\"carrierCodeClicked(" + count + ")\">\n");
          out.println("</td>");
          out.println("<td width=\"2%\" height=\"38\" " + bgColor + "\" ALIGN=\"CENTER\">");
          out.println("<INPUT " + color + "\" TYPE=\"button\" size=10 \" NAME=\"accountNumber" + count + "\" ID=\"accountNumber" + count + "\" value=\"" + BothHelpObjs.makeBlankFromNull(rs.getString("account")) + "\" OnClick=\"carrierCodeClicked(" + count + ")\">\n");
          out.println("</td>");
          out.println("</tr>\n");
          count++;
        }
      } catch (Exception e) {
        e.printStackTrace();
        out.println(radian.web.HTMLHelpObj.printError("Search Carrier Info",intcmIsApplication));
      } finally {
        dbrs.close();
      }
    }
    out.println("</TABLE>\n");
  } //end of method

  private void confirmShipment(HttpServletRequest request, String dateDelivered, String personnelId) {
    try {
      String branchPlant = request.getParameter("HubName");
      if (branchPlant == null) {
        branchPlant = "";
      }
      Connection connect1 = null;
      CallableStatement cs = null;
      int totalLines = Integer.parseInt(request.getParameter("addTrackingNoTotalLines").toString());
      Vector shipmentIds = new Vector(totalLines);
      for (int i = 0; i < totalLines; i++) {
        String shipmentID = request.getParameter("shipmentId" + (i + 1)).toString();
        String trackingNumber = BothHelpObjs.makeBlankFromNull(request.getParameter("trackingNumber" + (i + 1)).toString());
        String carrierCode = BothHelpObjs.makeBlankFromNull(request.getParameter("carrierCode" + (i + 1)).toString());
        String freightCharge = BothHelpObjs.makeBlankFromNull(request.getParameter("freightCharge" + (i + 1)).toString());
        String lineChecked = BothHelpObjs.makeBlankFromNull(request.getParameter("row_chk" + (i + 1)));
        if ("yes".equalsIgnoreCase(lineChecked)) {
          try {
            //shipConfirmLog.info("calling p_ship_confirm for shipmentID " + shipmentID + "");
            connect1 = db.getConnection();
            cs = connect1.prepareCall("{call p_ship_confirm(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            if (shipmentID.trim().length() > 0) {
              cs.setInt(1, Integer.parseInt(shipmentID));
            } else {
              cs.setNull(1, java.sql.Types.INTEGER);
            }
            if (carrierCode.trim().length() > 0) {
              cs.setString(2, carrierCode);
            } else {
              cs.setNull(2, java.sql.Types.VARCHAR);
            }
            if (trackingNumber.trim().length() > 0) {
              cs.setString(3, trackingNumber);
            } else {
              cs.setNull(3, java.sql.Types.VARCHAR);
            }
            if (dateDelivered.length() > 1) {
              cs.setTimestamp(4, radian.web.HTMLHelpObj.getDateFromString("", dateDelivered));
            } else {
              cs.setNull(4, java.sql.Types.DATE);
            }
            if (personnelId.trim().length() > 0) {
              cs.setInt(5, Integer.parseInt(personnelId));
            } else {
              cs.setNull(5, java.sql.Types.INTEGER);
            }
            
            if (freightCharge.trim().length() > 0) {
              cs.setString(6,"USD");
              cs.setString(7, freightCharge);
            } else {
              cs.setNull(6, java.sql.Types.VARCHAR);
              cs.setNull(7, java.sql.Types.INTEGER);
            }            
        cs.setNull(8, java.sql.Types.VARCHAR);
        cs.setNull(9, java.sql.Types.INTEGER);
        cs.setNull(10, java.sql.Types.INTEGER);
        cs.setNull(11, java.sql.Types.INTEGER);
        cs.setString(12,"N");
        cs.registerOutParameter(13,java.sql.Types.VARCHAR);
        cs.registerOutParameter(14,java.sql.Types.VARCHAR);
            cs.execute();
          } catch (Exception e) {
            //e.printStackTrace();
            shipConfirmLog.info("erro calling p_ship_confirm for shipmentID " + shipmentID + "   "+e.getMessage()+"");
            errormsg += e.getMessage();
            out.println(radian.web.HTMLHelpObj.printError("Ship Confirm",intcmIsApplication));
          }
          //keeping track of shipments for delivery notification
          if (!shipmentIds.contains(shipmentID)) {
            shipmentIds.addElement(shipmentID);
          }
        }
      } //end of for loop

      Vector searchdata = new Vector();
      try {
        searchdata = getShipmentData(branchPlant);
        Hashtable sum = (Hashtable) (searchdata.elementAt(0));

        int count = ( (Integer) sum.get("TOTAL_NUMBER")).intValue();
        if (0 == count) {
          //refresh ship confirm screen
          out.println("<HTML>\n<BODY>\n");
          out.println("<SCRIPT>\n");
          out.println("  openrWin = window.opener;\n");
          //out.println("  realParent = openrWin.parent;\n");
          out.println("  openrWin.search(\"blah\");");
          out.println("  openrWin.document.shipconfirm.submit();\n");
          out.println("  window.close();\n");
          out.println("</SCRIPT>\n");
          out.println("</BODY>\n</HTML>\n");
        } else {
          buildShipmentConfirmHeader(searchdata, personnelId, branchPlant, dateDelivered, "Shipment Confirm");
        }
      } catch (Exception e) {
        e.printStackTrace();
        out.println(radian.web.HTMLHelpObj.printError("Ship Confirm",intcmIsApplication));
      }
      //send delivery notification
      if (shipmentIds.size() > 0) {
        sendDeliveryNotification(shipmentIds);
      }
    } catch (Exception ee) {
      ee.printStackTrace();
      out.println("<CENTER>\n");
      out.println("<H3>Error during confirming shipment.\nPlease try again.</H3>\n");
      out.println("<INPUT TYPE=\"BUTTON\" VALUE=\"Close\" onClick=\"window.close();\">\n");
      out.println("</CENTER>");
    }
  } //end of method

  private void buildShipmentConfirmHeader(Vector data, String personnelid, String branchPlant, String dateDelivered, String userAction) {
    out.println(radian.web.HTMLHelpObj.printHTMLHeader("" + userAction + "", "shipconfirm.js",intcmIsApplication));
    out.println("<SCRIPT SRC=\"/clientscripts/calendar.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
    out.println("</HEAD>  \n");
    out.println("<BODY>\n");

    out.println("<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n");
    out.println("<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>Please wait while the page is updated...</B></FONT></center>\n");
    out.println("</DIV>\n");
    out.println("<DIV ID=\"MAINPAGE\" STYLE=\"\">\n");
    out.println(radian.web.HTMLHelpObj.PrintTitleBarNolink("<B>" + userAction + "</B>\n"));
    out.println("<FORM METHOD=\"POST\" name=\"addTrackingNo\" action=\"" + bundle.getString("SHIP_CONFIRM") + "?UserAction=AddTrackingNo\" onsubmit =\"return auditAddTrackingNo(this)\">\n");
    out.println("<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"2\" WIDTH=\"100%\">\n");
    out.println("<TR>\n");

    if ("Print Packing List".equalsIgnoreCase(userAction)) {
      //Print Packing List
      out.println("<TD WIDTH=\"10%\" ALIGN=\"RIGHT\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n");
      out.println("<INPUT type=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" value=\"Print Packing List\" name=\"printPackingList\" onclick= \"return consolidate()\">\n");
      out.println("</TD>\n");
    } else {
      //Date Delivered
      out.println("<TD WIDTH=\"10%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
      out.println("<B>Date Delivered:<BR>(mm/dd/yyyy)</B>&nbsp;\n");
      out.println("</TD>\n");
      out.println("<TD  ALIGN=\"LEFT\" WIDTH=\"10%\">\n");
      out.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"datedelivered\" value=\"" + dateDelivered + "\" maxlength=\"10\" size=\"10\">\n");
      out.println("<FONT SIZE=\"4\"><a href=\"javascript: void(0);\" ID=\"datelink\" onClick=\"return getCalendar(document.addTrackingNo.datedelivered);\">&diams;</a></FONT></td>\n");
      out.println("</TD>\n");

      //process
      out.println("<TD WIDTH=\"10%\" ALIGN=\"RIGHT\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n");
      out.println("<INPUT type=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" value=\"Ship Confirm\" name=\"addTrackingNoButton\" onclick= \"return shipconfirmshipment(this)\">\n");
      out.println("</TD>\n");

      //Return to Main
      out.println("<TD WIDTH=\"10%\" ALIGN=\"RIGHT\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n");
      out.println("<INPUT type=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" value=\"Return to Main\" name=\"returnToMainButton\" OnClick=\"returnToMain()\">\n");
      out.println("</TD>\n");
    }

    out.println("<TD WIDTH=\"5%\" HEIGHT=\"35\" CLASS=\"announce\" ALIGN=\"LEFT\">\n");
    out.println("<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Consolidated BOL\" onclick= \"printConsolidatedBol()\" NAME=\"ConsolidatedBol\">\n");
    out.println("</TD>\n");

    //cancel
    out.println("<TD WIDTH=\"10%\" ALIGN=\"RIGHT\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n");
    out.println("<INPUT type=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"addTrackingNoCancel\" value=\"Cancel\" OnClick=\"cancel()\">\n");
    out.println("</TD>\n");
    out.println("</TR>\n");
    out.println("</TABLE>\n");

    //detail info
    buildShipmentConfirmDetail(data, personnelid, userAction);
    out.println("<INPUT TYPE=\"hidden\" NAME=\"HubName\" ID=\"HubName\" VALUE=\"" + branchPlant + "\">\n");
    out.println("</FORM>\n");
    out.println("</BODY>\n</HTML>\n");
  } //end of method

  private void buildShipmentConfirmDetail(Vector data, String personnelid, String userAction) {
    out.println("<TABLE BORDER=0 CELLPADDING=\"2\" WIDTH= 100% >\n");
    out.println("<TR align=\"center\">\n");
    out.println("<TH width=\"2%\"  height=\"38\">OK<BR><INPUT TYPE=\"checkbox\" value=\"\" onClick=\"checkall('row_chk','0')\" NAME=\"chkall\" id=\"chkall\"></TH>\n");
    out.println("<TH width=\"2%\"  height=\"38\">Shipment ID</TH>\n");
    if ("Print Packing List".equalsIgnoreCase(userAction)) {
      out.println("<TH width=\"5%\"  height=\"38\">Hub/IG</TH>\n");
      out.println("<TH width=\"5%\"  height=\"38\">Company ID/Shipto</TH>\n");
    } else {
      out.println("<TH width=\"5%\"  height=\"38\">Carrier</TH>\n");
      out.println("<TH width=\"5%\"  height=\"38\">Tracking #</TH>\n");
      out.println("<TH width=\"5%\"  height=\"38\">Account Owner</TH>\n");
      out.println("<TH width=\"5%\"  height=\"38\">Account #</TH>\n");
      out.println("<TH width=\"5%\"  height=\"38\">Freight Charge</TH>\n");
    }
    out.println("</TR>\n");

    Hashtable updateresult = new Hashtable();
    try {
      Hashtable summary = new Hashtable();
      summary = (Hashtable) data.elementAt(0);
      int total = ( (Integer) (summary.get("TOTAL_NUMBER"))).intValue();
      int count = 0;
      //Vector shipmentIDExist = new Vector(total);
      for (int loop = 0; loop < total; loop++) {
        count++;
        Hashtable hD = new Hashtable();
        hD = (Hashtable) data.elementAt(count);
        //if (rowCheckedIndexV.contains((new Integer(i)).toString())) {
        String tmp = hD.get("SHIPMENT_ID").toString().trim();
        //if (tmp.length() > 0 && !shipmentIDExist.contains(tmp)) {

        String Color = " ";
        String trackingNoColor = " ";
        String bgColor = " ";
        if (count % 2 == 0) {
          Color = "CLASS=\"INVISIBLEHEADWHITE";
          trackingNoColor = "CLASS=\"white";
          bgColor = "BGCOLOR=\"#FFFFFF";
        } else {
          Color = "CLASS=\"INVISIBLEHEADBLUE";
          trackingNoColor = "CLASS=\"blue";
          bgColor = "BGCOLOR=\"#E6E8FA";
        }
        out.println("<tr align=\"center\">\n");
        out.println("<td " + trackingNoColor + "\" width=\"2%\" height=\"38\"><INPUT TYPE=\"checkbox\" value=\"yes\" NAME=\"row_chk" + count + "\" ID=\"row_chk" + count + "\"></td>\n");
        //shipment ID
        out.println("<td width=\"2%\" height=\"38\" " + bgColor + "\" ALIGN=\"CENTER\">");
        out.println("<INPUT " + Color + "\" TYPE=\"text\" size=5 \" NAME=\"shipmentId" + count + "\" ID=\"shipmentId" + count + "\" value=\"" + tmp + "\" READONLY>\n");
        out.println("</td>");

        if ("Print Packing List".equalsIgnoreCase(userAction)) {
          out.println("<td " + trackingNoColor + "\" width=\"5%\" height=\"38\">" + hD.get("BRANCH_PLANT").toString().trim() + "/" + hD.get("INVENTORY_GROUP").toString().trim() + "</td>\n");
          out.println("<td " + trackingNoColor + "\" width=\"5%\" height=\"38\">" + hD.get("SHIP_TO_COMPANY_ID").toString().trim() + "/" + hD.get("SHIP_TO_LOCATION_ID").toString().trim() + "</td>\n");
        } else {
          //carrier code
          out.println("<td width=\"2%\" height=\"38\" " + bgColor + "\" ALIGN=\"CENTER\">");
          out.println("<INPUT " + Color + "\" TYPE=\"text\" NAME=\"carrierCode" + count + "\" ID=\"carrierCode" + count + "\" value=\"" + hD.get("CARRIER_CODE").toString().trim() + "\" READONLY>");
          out.println("<BUTTON CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"...\" onClick= \"searchCarrierInfo(" + count + ")\" NAME=\"carrierSearchB\"><IMG src=\"/images/search_small.gif\" alt=\"Search\"></BUTTON></td>\n");
          out.println("</td>");
          //tracking number
          out.println("<td " + trackingNoColor + "\" width=\"2%\" height=\"38\"><INPUT TYPE=\"text\" size=16 \" value=\"" + hD.get("TRACKING_NUMBER").toString().trim() + "\"  NAME=\"trackingNumber" + count + "\" ID=\"trackingNumber" + count + "\"></td>\n");
          //account owner
          out.println("<td width=\"2%\" height=\"38\" " + bgColor + "\" ALIGN=\"CENTER\">");
          out.println("<INPUT " + Color + "\" TYPE=\"text\" size=8 \" NAME=\"accountOwner" + count + "\" ID=\"accountOwner" + count + "\" value=\"" + "&nbsp;" + "\" READONLY>\n");
          out.println("</td>");
          //account #
          out.println("<td width=\"2%\" height=\"38\" " + bgColor + "\" ALIGN=\"CENTER\">");
          out.println("<INPUT " + Color + "\" TYPE=\"text\" size=16 \" NAME=\"accountNumber" + count + "\" ID=\"accountNumber" + count + "\" value=\"" + "&nbsp;" + "\" READONLY>\n");
          out.println("</td>");
          out.println("<td width=\"2%\" height=\"38\" " + bgColor + "\" ALIGN=\"CENTER\">");
          out.println("<INPUT TYPE=\"text\" size=4 NAME=\"freightCharge" + count + "\" ID=\"freightCharge" + count + "\" value=\"" + "" + "\">(USD)\n");
          out.println("</td>");
          //inventory group
          String inventoryGroup = BothHelpObjs.makeBlankFromNull(hD.get("INVENTORY_GROUP").toString());
          out.println("<INPUT TYPE=\"hidden\" NAME=\"inventoryGroup" + count + "\" ID=\"inventoryGroup" + count + "\" VALUE=\"" + inventoryGroup.trim() + "\">\n");
          //new row
        }
        out.println("</tr>\n");
        //shipmentIDExist.addElement(tmp);
        //}
        //}
      } //end of for
      out.println("<INPUT TYPE=\"hidden\" NAME=\"addTrackingNoTotalLines\" ID=\"addTrackingNoTotalLines\" VALUE=\"" + count + "\">\n");
      out.println("<INPUT TYPE=\"hidden\" NAME=\"totallines\" ID=\"totallines\" VALUE=\"" + count + "\">\n");
    } catch (Exception e) {
      e.printStackTrace();
      out.println(radian.web.HTMLHelpObj.printError("Ship Confirm",intcmIsApplication));
    }
    out.println("</TABLE>\n");
  } //end of method

  private String printConsolPL(Vector data, String personnelid) {
    String shipmetIdList = "";
    try {
      Hashtable summary = new Hashtable();
      summary = (Hashtable) data.elementAt(0);
      int total = ( (Integer) (summary.get("TOTAL_NUMBER"))).intValue();
      Vector shipmentIdV = new Vector();
      for (int i = 1; i < total + 1; i++) {
        Hashtable hD = new Hashtable();
        hD = (Hashtable) data.elementAt(i);
        String Line_Check = (hD.get("USERCHK") == null ? "&nbsp;" : hD.get("USERCHK").toString());
        if ("yes".equalsIgnoreCase(Line_Check)) {
          String tmp = hD.get("SHIPMENT_ID").toString().trim();
          if (tmp.length() > 0 && !shipmentIdV.contains(tmp)) {
            shipmentIdV.addElement(tmp);
          }
        }
      } //end of for

      for (int j = 0; j < shipmentIdV.size(); j++) {
        String shipmentId = (String) shipmentIdV.elementAt(j);
        if (j > 0) {
          shipmetIdList += ",";
        }
        shipmetIdList += shipmentId;
      }
    } catch (Exception e) {
      e.printStackTrace();
      out.println(radian.web.HTMLHelpObj.printError("Ship Confirm",intcmIsApplication));
    }
    return shipmetIdList;
  } //end of method

  private void updateShipment(Vector data, String personnelid) {
    Hashtable updateresult = new Hashtable();
    try {
      Hashtable summary = new Hashtable();
      summary = (Hashtable) data.elementAt(0);
      int total = ( (Integer) (summary.get("TOTAL_NUMBER"))).intValue();
      Vector prNumberV = new Vector();
      Vector lineItemV = new Vector();
      Vector pickListV = new Vector();
      Vector shipmentIdV = new Vector();
      for (int i = 1; i < total + 1; i++) {
        Hashtable hD = new Hashtable();
        hD = (Hashtable) data.elementAt(i);
        String Line_Check = (hD.get("USERCHK") == null ? "&nbsp;" : hD.get("USERCHK").toString());
        if ("yes".equalsIgnoreCase(Line_Check)) {
          shipmentIdV.addElement(hD.get("SHIPMENT_ID").toString().trim());
          prNumberV.addElement(hD.get("PR_NUMBER").toString().trim());
          lineItemV.addElement(hD.get("LINE_ITEM").toString().trim());
          pickListV.addElement(hD.get("BATCH").toString().trim());
        }
      } //end of for

      String insertToStage = "insert all\n";
      for (int j = 0; j < prNumberV.size(); j++) {
        String shipmentId = (String) shipmentIdV.elementAt(j);
        if (shipmentId.length() == 0) {
          shipmentId = "null";
        }
        insertToStage += "into shipment_creation_stage (PR_NUMBER, LINE_ITEM, PICKLIST_ID, SHIPMENT_ID, ACTION) values (" + (String) prNumberV.elementAt(j) + ",'" + (String) lineItemV.elementAt(j) + "'," + (String) pickListV.elementAt(j) + "," + shipmentId + ",'UPDATE')\n";
      }
      insertToStage += "select 1 from dual";

      if (prNumberV.size() > 0) {
        Connection connect1 = null;
        CallableStatement cs = null;
        PreparedStatement pstmt = null;
        connect1 = db.getConnection();
        connect1.setAutoCommit(false);
        //shipConfirmLog.info(insertToStage);
        pstmt = connect1.prepareStatement(insertToStage);
        pstmt.executeQuery();

        cs = connect1.prepareCall("{call P_CREATE_SHIPMENT(?)}");
        cs.registerOutParameter(1, java.sql.Types.VARCHAR);
        cs.execute();
        String errorcode = cs.getString(1);
        if (errorcode !=null)
        {
        shipConfirmLog.info("Error from P_CREATE_SHIPMENT  " + errorcode + "");
        }
        connect1.setAutoCommit(true);
        connect1.commit();
      }
    } catch (Exception e) {
      e.printStackTrace();
      out.println(radian.web.HTMLHelpObj.printError("Ship Confirm",intcmIsApplication));
    }
  } //end of method

  private void createShipment(Vector data, String personnelid) {
    Hashtable updateresult = new Hashtable();
    try {
      Hashtable summary = new Hashtable();
      summary = (Hashtable) data.elementAt(0);
      int total = ( (Integer) (summary.get("TOTAL_NUMBER"))).intValue();
      Vector prNumberV = new Vector();
      Vector lineItemV = new Vector();
      Vector pickListV = new Vector();
      for (int i = 1; i < total + 1; i++) {
        Hashtable hD = new Hashtable();
        hD = (Hashtable) data.elementAt(i);
        String Line_Check = (hD.get("USERCHK") == null ? "&nbsp;" : hD.get("USERCHK").toString());
        if ("yes".equalsIgnoreCase(Line_Check)) {
          String tmp = hD.get("SHIPMENT_ID").toString().trim();
          if (tmp.length() > 0) {
            continue;
          }
          prNumberV.addElement(hD.get("PR_NUMBER").toString().trim());
          lineItemV.addElement(hD.get("LINE_ITEM").toString().trim());
          pickListV.addElement(hD.get("BATCH").toString().trim());
        }
      } //end of for
      //create array to pass into procedure
      /*String[] prNumberArray = new String[prNumberV.size()];
           String[] lineItemArray = new String[lineItemV.size()];
           String[] pickListArray = new String[pickListV.size()];*/
      String insertToStage = "insert all\n";
      for (int j = 0; j < prNumberV.size(); j++) {
        insertToStage += "into shipment_creation_stage (PR_NUMBER, LINE_ITEM, PICKLIST_ID, SHIPMENT_ID, ACTION) values (" + (String) prNumberV.elementAt(j) + ",'" + (String) lineItemV.elementAt(j) + "'," + (String) pickListV.elementAt(j) + ",null,'CREATE')\n";

        /*prNumberArray[j] = (String)prNumberV.elementAt(j);
                lineItemArray[j] = (String)lineItemV.elementAt(j);
                pickListArray[j] = (String)pickListV.elementAt(j);*/
      }
      insertToStage += "select 1 from dual";
      //db.doUpdate(insertToStage);
      if (prNumberV.size() > 0) {
        Connection connect1 = null;
        CallableStatement cs = null;
        PreparedStatement pstmt = null;
        connect1 = db.getConnection();
        connect1.setAutoCommit(false);
        shipConfirmLog.debug(insertToStage);
        pstmt = connect1.prepareStatement(insertToStage);
        pstmt.executeQuery();

        cs = connect1.prepareCall("{call P_CREATE_SHIPMENT(?)}");
        cs.registerOutParameter(1, java.sql.Types.VARCHAR);
        cs.execute();
        String errorcode = cs.getString(1);
        if (errorcode != null) {
          shipConfirmLog.info("Error from P_CREATE_SHIPMENT  " + errorcode + "");
          errormsg += errorcode;
        }
        connect1.setAutoCommit(true);
        connect1.commit();
      }
      /*if (prNumberArray.length > 0) {
         Vector args = new Vector(3);
         args.addElement("");
         args.addElement("");
         args.addElement("error_val");
         String error = db.doProcedureWithErrorMsg("SOME PROCEDURE NAME",args,3);
         shipConfirmLog.info("------- output from createShipment procedure:"+error);
           }
           shipConfirmLog.info("----- selected PR:"+prNumberV+"\nline:"+lineItemV+"\npicklist:"+pickListV);*/
    } catch (Exception e) {
      e.printStackTrace();
      out.println(radian.web.HTMLHelpObj.printError("Ship Confirm",intcmIsApplication));
    }
  } //end of method

  /*private void removeFromShipment(Vector data, String personnelid) {
    shipConfirmLog.info("--------- here in RemoveFromShipment");
    Hashtable updateresult = new Hashtable();
    try {
     Hashtable summary = new Hashtable();
     summary = (Hashtable)data.elementAt(0);
     int total = ((Integer)(summary.get("TOTAL_NUMBER"))).intValue()  ;
     String shipmentIDs = "";
        String prNumbers = "";
     String lineItems = "";
     String pickLists = "";
     for (int i = 1; i < total+1  ; i++) {
      Hashtable hD = new Hashtable();
      hD = (Hashtable) data.elementAt(i);
      String Line_Check = (hD.get("USERCHK")==null?"&nbsp;":hD.get("USERCHK").toString());
      if ("yes".equalsIgnoreCase(Line_Check)) {
       shipConfirmLog.info("check Found at line # : "+ i );
       String tmp = hD.get("SHIPMENT_ID").toString().trim();
       if (tmp.length() > 0 ) {
      shipmentIDs += tmp;
      prNumbers += hD.get( "PR_NUMBER" ).toString().trim() + ",";
      lineItems += hD.get( "LINE_ITEM" ).toString().trim() + ",";
      pickLists += hD.get( "BATCH" ).toString().trim() + ",";
       }
      }
     } //end of for
     //remove the last commas ','
     if (shipmentIDs.length() > 0 ) {
    shipmentIDs = shipmentIDs.substring(0,shipmentIDs.length()-1);
    prNumbers = prNumbers.substring(0,prNumbers.length()-1);
    lineItems = lineItems.substring(0,lineItems.length()-1);
    pickLists = pickLists.substring(0,pickLists.length()-1);
     }
     shipConfirmLog.info("----- selected PR:"+prNumbers+"\nline:"+lineItems+"\npicklist:"+pickLists);
    }catch (Exception e) {
   e.printStackTrace();
   out.println(radian.web.HTMLHelpObj.printError("Ship Confirm"));
    }
    } //end of method*/

  private void buildSearchScreen(boolean isCollection, HttpSession session, String branch_plant, Hashtable hub_list_set, String datedelivered, String invenGroup, String sortResultsBy) {
    Vector searchdata = new Vector();
    Vector autoShipConfirmlistV = new Vector();
    autoShipConfirmlistV = (Vector) session.getAttribute("AUTO_SHIP_CONFIRM_LIST");
    Collection hubInventoryGroupOvBeanCollection = new Vector( (Collection) session.getAttribute("hubInventoryGroupOvBeanCollection"));

    try {
      searchdata = getSearchData(isCollection, branch_plant, invenGroup, sortResultsBy);
      Hashtable sum = (Hashtable) (searchdata.elementAt(0));
      session.removeAttribute("PRINTDATA");
      session.setAttribute("UPDATEDONE", "NO");
      session.setAttribute("DATA", searchdata);
      //Hashtable iniInvenData= ( Hashtable ) session.getAttribute( "INVENGRP_DATA" );
      int count = ( (Integer) sum.get("TOTAL_NUMBER")).intValue();
      if (0 == count) {
        buildHeader(branch_plant, hub_list_set, "", "", "", session, autoShipConfirmlistV, hubInventoryGroupOvBeanCollection, invenGroup, sortResultsBy);
        out.println(radian.web.HTMLHelpObj.printHTMLNoData("No Items Found"));
        out.close();
        //clean up
        searchdata = null;
        hub_list_set = null;
      } else {
        Hashtable validShipmentIds = new Hashtable();
        if (!autoShipConfirmlistV.contains(branch_plant)) {
          validShipmentIds = createShipmentSet(searchdata, branch_plant);
        }
        session.setAttribute("VALID_SHIPMENT_IDS", validShipmentIds);
        boolean autoShipConfirmHub = false;
        if (autoShipConfirmlistV.contains(branch_plant)) {
          autoShipConfirmHub = true;
        }

        String shipmentIdList = session.getAttribute("SHIPMENT_ID_LIST") == null ? "" : (String) session.getAttribute("SHIPMENT_ID_LIST");
        if (shipmentIdList.trim().length() > 0) {
          buildHeader(branch_plant, hub_list_set, "GENERATE_PACKING_LIST", "", datedelivered, session, autoShipConfirmlistV, hubInventoryGroupOvBeanCollection, invenGroup, sortResultsBy);
        } else {
          buildHeader(branch_plant, hub_list_set, "", "", datedelivered, session, autoShipConfirmlistV, hubInventoryGroupOvBeanCollection, invenGroup, sortResultsBy);
        }
        buildDetails(searchdata, "", autoShipConfirmHub, validShipmentIds);
        out.close();
        //clean up
        searchdata = null;
        hub_list_set = null;
      } //when there are open orders for hub
    } catch (Exception e) {
      e.printStackTrace();
      out.println(radian.web.HTMLHelpObj.printError("Ship Confirm",intcmIsApplication));
    }
  } //end of method

  private Hashtable createShipmentSet(Vector data, String branch_plant) {
    Hashtable shipmentIdSet = new Hashtable();
    Hashtable sum = (Hashtable) (data.elementAt(0));
    int count = ( (Integer) sum.get("TOTAL_NUMBER")).intValue();

    //Vector shipmentV = new Vector();
    Vector shiptoV = new Vector();
    Vector shiptoCompanyV = new Vector();
    try {
      for (int i = 1; i < count + 1; i++) {
        Hashtable hD = new Hashtable();
        hD = (Hashtable) data.elementAt(i);

        String shipToLocationId = hD.get("SHIP_TO_LOCATION_ID") == null ? "" : hD.get("SHIP_TO_LOCATION_ID").toString();
        String shipToCompanyId = hD.get("SHIP_TO_COMPANY_ID") == null ? "" : hD.get("SHIP_TO_COMPANY_ID").toString();
            //String shipmentId = hD.get("SHIPMENT_ID")==null?"":hD.get("SHIPMENT_ID").toString();

        if (!shiptoV.contains(shipToLocationId)) {
          //shipmentV.addElement( shipmentId );
          shiptoV.addElement(shipToLocationId);
          shiptoCompanyV.addElement(shipToCompanyId);
        }
      } //end of for

      for (int j = 0; j < shiptoV.size(); j++) {
        String shipToLocationId = (String) shiptoV.elementAt(j);

        Vector shipmentIdV = radian.web.HTMLHelpObj.CreateShipmentForIssues(db, shipToLocationId, (String) shiptoCompanyV.elementAt(j));
        shipmentIdSet.put(shipToLocationId, shipmentIdV);
      }
    } catch (Exception e) {
      e.printStackTrace();
      out.println(radian.web.HTMLHelpObj.printError("Ship Confirm",intcmIsApplication));
    }

    return shipmentIdSet;
  }

  private Vector getShipmentData(String BranchPlant) throws Exception {
    Vector Data = new Vector();
    Hashtable DataH = new Hashtable();
    Hashtable summary = new Hashtable();

    DBResultSet dbrs = null;
    ResultSet rs = null;

    int count = 0;
    summary.put("TOTAL_NUMBER", new Integer(count));
    Data.addElement(summary);

    int num_rec = 0;

    try {
      String query = "select BRANCH_PLANT, CARRIER_CODE, COMPANY_ID, DATE_DELIVERED, INVENTORY_GROUP, SHIP_CONFIRM_DATE, SHIP_TO_LOCATION_ID, SHIPMENT_ID, TRACKING_NUMBER ";
      query += " from shipment where BRANCH_PLANT='" + BranchPlant + "' and SHIP_CONFIRM_DATE is null order by SHIPMENT_ID";

      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        DataH = new Hashtable();
        num_rec++;
        /*DataH.put("ISSUER_NAME",rs.getString("ISSUER_NAME")==null?"&nbsp;":rs.getString("ISSUER_NAME"));
           DataH.put("CAT_PART_NO",rs.getString("CAT_PART_NO")==null?"&nbsp;":rs.getString("CAT_PART_NO"));
           DataH.put("QUANTITY",rs.getString("QUANTITY")==null?"&nbsp;":rs.getString("QUANTITY"));
           DataH.put("DATE_PICKED",rs.getString("DATE_PICKED")==null?"&nbsp;":rs.getString("DATE_PICKED"));
           DataH.put("PR_NUMBER",rs.getString("PR_NUMBER")==null?"":rs.getString("PR_NUMBER"));
           DataH.put("LINE_ITEM",rs.getString("LINE_ITEM")==null?"":rs.getString("LINE_ITEM"));
           DataH.put("FACILITY_ID",rs.getString("FACILITY_ID")==null?"&nbsp;":rs.getString("FACILITY_ID"));
           DataH.put("APPLICATION",rs.getString("APPLICATION")==null?"&nbsp;":rs.getString("APPLICATION"));*/
        DataH.put("DATE_DELIVERED", rs.getString("DATE_DELIVERED") == null ? "" : rs.getString("DATE_DELIVERED"));
        /*DataH.put("COMPANY_ID",rs.getString("COMPANY_ID")==null?"&nbsp;":rs.getString("COMPANY_ID"));
           DataH.put("BATCH",rs.getString("BATCH")==null?"":rs.getString("BATCH"));
           DataH.put("LINE_STATUS", "" );
           DataH.put("USERCHK", "" );*/
        DataH.put("SHIPMENT_ID", rs.getString("SHIPMENT_ID") == null ? "" : rs.getString("SHIPMENT_ID"));
        String carrierCode = rs.getString("CARRIER_CODE") == null ? "" : rs.getString("CARRIER_CODE");
        String trackingNumber = rs.getString("TRACKING_NUMBER") == null ? "" : rs.getString("TRACKING_NUMBER");
            /*String tmpCarrierCodeTracking = "";
               if (carrierCode.length() > 0 && trackingNumber.length() > 0) {
              tmpCarrierCodeTracking = carrierCode+" / "+trackingNumber;
               }else if (carrierCode.length() > 0) {
              tmpCarrierCodeTracking = carrierCode;
               }else if (trackingNumber.length() > 0) {
              tmpCarrierCodeTracking = trackingNumber;
               }
               DataH.put("CARRIER_CODE_TRACKING",tmpCarrierCodeTracking);*/
        DataH.put("CARRIER_CODE", carrierCode);
        DataH.put("TRACKING_NUMBER", trackingNumber);
        DataH.put("INVENTORY_GROUP", rs.getString("INVENTORY_GROUP") == null ? "" : rs.getString("INVENTORY_GROUP"));
        DataH.put("SHIP_TO_LOCATION_ID", rs.getString("SHIP_TO_LOCATION_ID") == null ? "" : rs.getString("SHIP_TO_LOCATION_ID"));
        DataH.put("SHIP_TO_COMPANY_ID", rs.getString("COMPANY_ID") == null ? "" : rs.getString("COMPANY_ID"));
        DataH.put("BRANCH_PLANT", rs.getString("BRANCH_PLANT") == null ? "" : rs.getString("BRANCH_PLANT"));

        Data.addElement(DataH);
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (dbrs != null) {
        dbrs.close();
      }
    }

    Hashtable recsum = new Hashtable();
    recsum.put("TOTAL_NUMBER", new Integer(num_rec));
    Data.setElementAt(recsum, 0);

    return Data;
  } //end of method

  private Vector getSearchData(boolean isCollection, String branchPlant, String inventoryGroup, String sortResultsBy) throws Exception {
    Vector Data = new Vector();
    Hashtable DataH = new Hashtable();
    Hashtable summary = new Hashtable();

    DBResultSet dbrs = null;
    ResultSet rs = null;

    int count = 0;
    summary.put("TOTAL_NUMBER", new Integer(count));
    Data.addElement(summary);

    int num_rec = 0;

    try {
      String query =
          "select DELIVERY_POINT, APPLICATION_DESC,OVER_SHIPPED,HAZARD_CATEGORY, SHIP_TO_COMPANY_ID, SHIP_TO_LOCATION_ID,shipment_id,carrier_code,tracking_number,inventory_group,PR_NUMBER, LINE_ITEM, COMPANY_ID, FACILITY_ID, APPLICATION, CAT_PART_NO, SOURCE_HUB, QUANTITY, DATE_PICKED, ISSUER_NAME,";
      query += "BATCH, DELIVERED_DATE from ship_confirm_view where SOURCE_HUB='" + branchPlant + "'";

      if (inventoryGroup != null && inventoryGroup.length() > 0 &&
          !inventoryGroup.equalsIgnoreCase("ALL")) {
        if (isCollection) {
          query += " and INVENTORY_GROUP in (select INVENTORY_GROUP from INVENTORY_GROUP_COLLECTION where HUB = '" +
              branchPlant +
              "' and INVENTORY_GROUP_COLLECTION = '" + inventoryGroup + "') ";
        } else if (inventoryGroup.length() > 0) {
          query += " and INVENTORY_GROUP ='" + inventoryGroup + "' ";
        }
      }

      /*if (inventoryGroup.length() > 0 && !"All".equalsIgnoreCase(inventoryGroup))
          {
         query+=" and INVENTORY_GROUP = '"+inventoryGroup+"' ";
          }*/
      query += " order by " + sortResultsBy + "";

      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        DataH = new Hashtable();
        num_rec++;
        DataH.put("ISSUER_NAME", rs.getString("ISSUER_NAME") == null ? "&nbsp;" : rs.getString("ISSUER_NAME"));
        DataH.put("CAT_PART_NO", rs.getString("CAT_PART_NO") == null ? "&nbsp;" : rs.getString("CAT_PART_NO"));
        DataH.put("QUANTITY", rs.getString("QUANTITY") == null ? "&nbsp;" : rs.getString("QUANTITY"));
        DataH.put("DATE_PICKED", rs.getString("DATE_PICKED") == null ? "&nbsp;" : rs.getString("DATE_PICKED"));
        DataH.put("PR_NUMBER", rs.getString("PR_NUMBER") == null ? "" : rs.getString("PR_NUMBER"));
        DataH.put("LINE_ITEM", rs.getString("LINE_ITEM") == null ? "" : rs.getString("LINE_ITEM"));
        DataH.put("FACILITY_ID", rs.getString("FACILITY_ID") == null ? "&nbsp;" : rs.getString("FACILITY_ID"));
        DataH.put("APPLICATION", rs.getString("APPLICATION") == null ? "&nbsp;" : rs.getString("APPLICATION"));
        DataH.put("DELIVERED_DATE", rs.getString("DELIVERED_DATE") == null ? "" : rs.getString("DELIVERED_DATE"));
        DataH.put("COMPANY_ID", rs.getString("COMPANY_ID") == null ? "&nbsp;" : rs.getString("COMPANY_ID"));
        DataH.put("BATCH", rs.getString("BATCH") == null ? "" : rs.getString("BATCH"));
        DataH.put("LINE_STATUS", "");
        DataH.put("USERCHK", "");
        DataH.put("SHIPMENT_ID", rs.getString("SHIPMENT_ID") == null ? "" : rs.getString("SHIPMENT_ID"));
        String carrierCode = rs.getString("CARRIER_CODE") == null ? "" : rs.getString("CARRIER_CODE");
        String trackingNumber = rs.getString("TRACKING_NUMBER") == null ? "" : rs.getString("TRACKING_NUMBER");
        String tmpCarrierCodeTracking = "";
        if (carrierCode.length() > 0 && trackingNumber.length() > 0) {
          tmpCarrierCodeTracking = carrierCode + " / " + trackingNumber;
        } else if (carrierCode.length() > 0) {
          tmpCarrierCodeTracking = carrierCode;
        } else if (trackingNumber.length() > 0) {
          tmpCarrierCodeTracking = trackingNumber;
        }
        DataH.put("CARRIER_CODE_TRACKING", tmpCarrierCodeTracking);
        DataH.put("CARRIER_CODE", carrierCode);
        DataH.put("TRACKING_NUMBER", trackingNumber);
        DataH.put("INVENTORY_GROUP", rs.getString("INVENTORY_GROUP") == null ? "" : rs.getString("INVENTORY_GROUP"));
        DataH.put("SHIP_TO_LOCATION_ID", rs.getString("SHIP_TO_LOCATION_ID") == null ? "" : rs.getString("SHIP_TO_LOCATION_ID"));
        DataH.put("SHIP_TO_COMPANY_ID", rs.getString("SHIP_TO_COMPANY_ID") == null ? "" : rs.getString("SHIP_TO_COMPANY_ID"));
        DataH.put("HAZARD_CATEGORY", rs.getString("HAZARD_CATEGORY") == null ? "" : rs.getString("HAZARD_CATEGORY"));
        DataH.put("OVER_SHIPPED", rs.getString("OVER_SHIPPED") == null ? "" : rs.getString("OVER_SHIPPED"));
        DataH.put("DELIVERY_POINT", rs.getString("DELIVERY_POINT") == null ? "" : rs.getString("DELIVERY_POINT"));
        DataH.put("APPLICATION_DESC", rs.getString("APPLICATION_DESC") == null ? "" : rs.getString("APPLICATION_DESC"));

        Data.addElement(DataH);
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (dbrs != null) {
        dbrs.close();
      }
    }

    Hashtable recsum = new Hashtable();
    recsum.put("TOTAL_NUMBER", new Integer(num_rec));
    Data.setElementAt(recsum, 0);

    return Data;
  } //end of method

  private Vector SynchprintData(HttpServletRequest request, Vector in_data) {
    Vector new_data = new Vector();
    Hashtable sum = (Hashtable) (in_data.elementAt(0));
    int count = ( (Integer) sum.get("TOTAL_NUMBER")).intValue();

    Vector prnumbers = new Vector();
    Vector linenumber = new Vector();
    Vector picklistid = new Vector();
    Hashtable FResult = new Hashtable();

    try {
      for (int i = 1; i < count + 1; i++) {
        Hashtable hD = new Hashtable();
        hD = (Hashtable) (in_data.elementAt(i));

        String mrline = hD.get("PR_NUMBER").toString();
        String lineitem = hD.get("LINE_ITEM").toString();

        String check = "";
        check = BothHelpObjs.makeBlankFromNull(request.getParameter("row_chk" + i));

        if ("yes".equalsIgnoreCase(check)) {
          prnumbers.addElement(mrline);
          linenumber.addElement(lineitem);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      out.println(radian.web.HTMLHelpObj.printError("Ship Confirm",intcmIsApplication));
    }

    FResult.put("mr_number", prnumbers);
    FResult.put("line_number", linenumber);
    FResult.put("picklist_number", picklistid);

    new_data.add(FResult);
    return new_data;
  }

  private Vector SynchServerData(HttpServletRequest request, Vector in_data, String deliverydate) {
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

        /*String date_recieved = "";
                         date_recieved  = BothHelpObjs.makeBlankFromNull(request.getParameter("delivery_date"+i));*/
        hD.remove("DELIVERED_DATE");
        hD.put("DELIVERED_DATE", deliverydate);

        if (deliverydate.length() == 10) {
          hD.remove("DOSTATUSUPDATE");
          hD.put("DOSTATUSUPDATE", "Yes");
        }

        String shipmentId = "";
        shipmentId = BothHelpObjs.makeBlankFromNull(request.getParameter("shipmentId" + i));
        hD.remove("SHIPMENT_ID");
        hD.put("SHIPMENT_ID", shipmentId);

        new_data.addElement(hD);
      }
    } catch (Exception e) {
      e.printStackTrace();
      out.println(radian.web.HTMLHelpObj.printError("Ship Confirm",intcmIsApplication));
    }
    return new_data;
  }

  /*public boolean Updatedeliverydate( Hashtable hD, String personnelID )
       {
     // get main information
     boolean result = false;
     String prnumber       = hD.get("PR_NUMBER").toString().trim();
     String lineitem       = hD.get("LINE_ITEM").toString().trim();
     String batch         = hD.get("BATCH").toString().trim();
     String datepicked     = hD.get("DATE_PICKED").toString().trim();
     String datedelivered  = hD.get("DELIVERED_DATE").toString().trim();
     shipConfirmLog.info("Doing Update deliverydate ");
     Connection connect1 = null;
     CallableStatement cs = null;
     try
     {
         if ( prnumber.trim().length() < 1 || lineitem.trim().length() < 1 || batch.trim().length() < 1 )
         {
           result = false;
         }
         else
         {
           String updquery = "update issue set (date_delivered,ship_confirm_user,ship_confirm_date) = ";
           updquery +=" (select to_date('"+datedelivered+"','MM/DD/YYYY'), "+personnelID+", sysdate from dual) where pr_number = "+prnumber+" and line_item = "+lineitem+" and nvl(picklist_id, batch) = "+batch+" ";
           //updquery +=" (select to_date('"+datedelivered+"','MM/DD/YYYY'), "+personnelID+", sysdate from dual) where pr_number = "+prnumber+" and line_item = "+lineitem+" and item_id = "+itemid+" ";
           updquery +=" and date_picked = to_date('"+datepicked+"','MM/DD/YYYY hh24:mi:ss') and date_delivered is null ";
           shipConfirmLog.info(updquery);
           db.doUpdate(updquery);
           //vLotSeqList.addElement(LOT_UID);
           connect1 = db.getConnection();
           shipConfirmLog.info("Doing P_UPDATE_RLI_STATUS_SHIPPED");
           cs = connect1.prepareCall("{call P_UPDATE_RLI_STATUS_SHIPPED(?,?)}");
           cs.setInt(1,Integer.parseInt(prnumber));
           cs.setString(2,lineitem);
           cs.execute();
           result = true;
         }
     }
     catch (Exception e)
     {
         e.printStackTrace();
         //errorMsgs.addElement("Error for prnumber :"+prnumber+"");
         result = false;
     }
     finally
     {
         if (cs != null) {
           try {
                   cs.close();
           } catch (Exception se) {se.printStackTrace();}
         }
     }
     return result;
       }*/

  private Hashtable UpdateDetails(Vector data, String personnelid, String deliverydate, String brachPlant) {
    boolean result = false;

    Hashtable updateresult = new Hashtable();
    Vector errordata = new Vector();
    Connection connect1 = null;
    CallableStatement cs = null;
    PreparedStatement pstmt = null;

    try {
      Hashtable summary = new Hashtable();
      summary = (Hashtable) data.elementAt(0);
      int total = ( (Integer) (summary.get("TOTAL_NUMBER"))).intValue();
      errordata.addElement(summary);
      int mrcount = 0;
      boolean one_success = false;
      String insertToStage = "insert all\n";
      //String mrLineitemIn = "(iss.pr_number,iss.line_item)  in (";

      for (int i = 1; i < total + 1; i++) {
        Hashtable hD = new Hashtable();
        hD = (Hashtable) data.elementAt(i);
        String Line_Check = "";
        Line_Check = (hD.get("USERCHK") == null ? "&nbsp;" : hD.get("USERCHK").toString());

        String dostatusupdate = "";
        dostatusupdate = (hD.get("DOSTATUSUPDATE") == null ? " " : hD.get("DOSTATUSUPDATE").toString());

        if ("yes".equalsIgnoreCase(Line_Check) && "yes".equalsIgnoreCase(dostatusupdate)) {
          noneToUpd = false;
          String prnumber = hD.get("PR_NUMBER").toString().trim();
          String lineitem = hD.get("LINE_ITEM").toString().trim();
          String batch = hD.get("BATCH").toString().trim();
          String datepicked = hD.get("DATE_PICKED").toString().trim();
          String datedelivered = hD.get("DELIVERED_DATE").toString().trim();

          /*boolean line_result = false;
                               line_result = this.Updatedeliverydate(hD, personnelid  ); // update database
                               if ( false == line_result)
                               {
                               completeSuccess = false;
                               hD.remove("USERCHK");
                               hD.put("USERCHK", " ");
                               hD.remove("LINE_STATUS");
                               hD.put("LINE_STATUS", "NO");
                               errordata.addElement(hD);
                               }
                               if ( true == line_result)*/ {
            one_success = true;
            hD.remove("LINE_STATUS");
            hD.put("LINE_STATUS", "YES");
          }

          errordata.addElement(hD);
          /*if ( mrcount > 0 )
                {
            mrLineitemIn+=",";
                }
                mrLineitemIn+= "("+prnumber+","+lineitem+")";*/

          insertToStage += "into shipment_creation_stage (PR_NUMBER, LINE_ITEM, PICKLIST_ID, SHIPMENT_ID, ACTION) values (" + prnumber + ",'" + lineitem + "'," + batch + ",null,'CREATE')\n";
          mrcount++;
        } else {
          errordata.addElement(hD);
        }
      } //end of for
      insertToStage += "select 1 from dual";

      connect1 = db.getConnection();
      connect1.setAutoCommit(false);
      //shipConfirmLog.info( insertToStage );
      pstmt = connect1.prepareStatement(insertToStage);
      pstmt.executeQuery();

      cs = connect1.prepareCall("{call P_CREATE_SHIPMENT(?)}");
      cs.registerOutParameter(1, java.sql.Types.VARCHAR);
      cs.execute();
      String errorcode = cs.getString(1);
      //shipConfirmLog.info( "Error from P_CREATE_SHIPMENT  " + errorcode + "" );
      if (errorcode != null) {
        errormsg += errorcode;
      }

      connect1.setAutoCommit(true);
      connect1.commit();

      String query = "select distinct s.shipment_id,s.TRACKING_NUMBER,s.CARRIER_CODE from shipment s where ";
      /*query +="iss.shipment_id=s.shipment_id and ";
          query +="iss.SHIP_CONFIRM_DATE is null and ";
          query +="iss.SOURCE_HUB = s.BRANCH_PLANT and ";
          query +="iss.COMPANY_ID = s.COMPANY_ID and ";
          query +="iss.SOURCE_HUB = "+brachPlant+" and ";*/
      query += " s.BRANCH_PLANT='" + brachPlant + "' and SHIP_CONFIRM_DATE is null order by SHIPMENT_ID";
      /*mrLineitemIn+=")";
          query +=mrLineitemIn;*/

      DBResultSet dbrs = null;
      ResultSet rs = null;
      Vector ResultV = new Vector();
      Hashtable DataH = new Hashtable();

      try {
        dbrs = db.doQuery(query);
        rs = dbrs.getResultSet();
        while (rs.next()) {
          DataH = new Hashtable();
          DataH.put("SHIPMENT_ID", rs.getString("SHIPMENT_ID") == null ? "" : rs.getString("SHIPMENT_ID"));
          DataH.put("TRACKING_NUMBER", rs.getString("TRACKING_NUMBER") == null ? "" : rs.getString("TRACKING_NUMBER"));
          DataH.put("CARRIER_CODE", rs.getString("CARRIER_CODE") == null ? "" : rs.getString("CARRIER_CODE"));

          ResultV.addElement(DataH);
        }
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        if (dbrs != null) {
          dbrs.close();
        }
      }

      Vector shipmentIds = new Vector(ResultV.size());
      for (int i = 0; i < ResultV.size(); i++) {
        Hashtable hD = new Hashtable();
        hD = (Hashtable) ResultV.elementAt(i);

        String shipmentId = hD.get("SHIPMENT_ID").toString().trim();
        String trackingNumber = hD.get("TRACKING_NUMBER").toString().trim();
        String carrierCode = hD.get("CARRIER_CODE").toString().trim();
        //shipConfirmLog.info( "Calling p_ship_confirm  " + shipmentId + " deliverydate  "+deliverydate+"" );

        connect1 = db.getConnection();
        cs = connect1.prepareCall("{call p_ship_confirm(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
        if (shipmentId.trim().length() > 0) {
          cs.setInt(1, Integer.parseInt(shipmentId));
        } else {
          cs.setNull(1, java.sql.Types.INTEGER);
        }
        if (carrierCode.trim().length() > 0) {
          cs.setString(2, carrierCode);
        } else {
          cs.setNull(2, java.sql.Types.VARCHAR);
        }
        if (trackingNumber.trim().length() > 0) {
          cs.setString(3, trackingNumber);
        } else {
          cs.setNull(3, java.sql.Types.VARCHAR);
        }
        if (deliverydate.length() > 1) {
          cs.setTimestamp(4, radian.web.HTMLHelpObj.getDateFromString("", deliverydate));
        } else {
          cs.setNull(4, java.sql.Types.DATE);
        }
        if (personnelid.trim().length() > 0) {
          cs.setInt(5, Integer.parseInt(personnelid));
        } else {
          cs.setNull(5, java.sql.Types.INTEGER);
        }
        cs.setNull(6, java.sql.Types.VARCHAR);
        cs.setNull(7, java.sql.Types.INTEGER);
        cs.setNull(8, java.sql.Types.VARCHAR);
        cs.setNull(9, java.sql.Types.INTEGER);
        cs.setNull(10, java.sql.Types.INTEGER);
        cs.setNull(11, java.sql.Types.INTEGER);
        cs.setString(12,"N");
        cs.registerOutParameter(13,java.sql.Types.VARCHAR);
        cs.registerOutParameter(14,java.sql.Types.VARCHAR);
        cs.execute();
        //keeping track of shipments for delivery notification
        if (!shipmentIds.contains(shipmentId)) {
          shipmentIds.addElement(shipmentId);
        }
      } //end of for loop

      //if ( true == one_success )
      {
        result = true;
      }
      //send delivery notification
      if (shipmentIds.size() > 0) {
        sendDeliveryNotification(shipmentIds);
      }
    } catch (Exception e) {
      result = false;
      e.printStackTrace();
      errormsg += e.getMessage();
    } finally {
      if (cs != null) {
        try {
          cs.close();
        } catch (Exception se) {
          se.printStackTrace();
        }
      }
    }

    updateresult.put("RESULT", new Boolean(result));
    updateresult.put("ERRORDATA", errordata);
    return updateresult;
  }

  //only sending out delivery notification if there are people
  //who want it.  user_group_member_location.user_group_id = 'DeliveryNotification'
  private void sendDeliveryNotification(Vector shipmentIds) {
    DBResultSet dbrs = null;
    ResultSet rs = null;
    String shipmentId = "";
    for (int i = 0; i < shipmentIds.size(); i++) {
      shipmentId += shipmentIds.elementAt(i).toString()+",";
    }
    //remove last commas ','
    //and send email to people in concern
    //if no shipment Id do nothing
    if (shipmentId.length() > 1) {
      shipmentId = shipmentId.substring(0, shipmentId.length() - 1);
      try {
        String query = "select distinct p.email,s.shipment_id from personnel p, user_group_member_location ugml, shipment s"+
                       " where s.shipment_id in ("+shipmentId+") and s.ship_to_location_id = ugml.ship_to_location_id"+
                       " and s.company_id = ugml.company_id and ugml.user_group_id = 'DeliveryNotification'"+
                       " and ugml.personnel_id = p.personnel_id";
        dbrs = db.doQuery(query);
        rs = dbrs.getResultSet();
        Hashtable emailAddress = new Hashtable(13);
        while (rs.next()) {
          String email = rs.getString("email");
          if (emailAddress.containsKey(email)) {
            String shipId = (String)emailAddress.get(email);
            shipId += rs.getString("shipment_id")+",";
          }else {
            String shipId = "";
            shipId += rs.getString("shipment_id")+",";
            emailAddress.put(email,shipId);
          }
        }

        //sending email
        Enumeration enumCollec = emailAddress.keys();
        while (enumCollec.hasMoreElements()) {
          String key =  enumCollec.nextElement().toString();
          String shipId = (String)emailAddress.get(key);
          //removing the last commas ','
          if (shipId.lastIndexOf(",") == shipId.length()-1) {
            shipId = shipId.substring(0, shipId.length() - 1);
          }
          String[] cc = new String[0];
          String[] receiver = {key};
          String msg = "Delivery Notification for shipment: " + shipId +
                       "\n\nClick on link below to print Packing List" +
                       "\n\nhttps://www.tcmis.com/tcmIS/hub/printpackinglist.do?shipmentIds=" + shipId;
          HelpObjs.javaSendMail("Delivery Notification for shipment: " + shipId, msg, receiver, cc);
        }
      } catch (Exception e) {
        e.printStackTrace();
        HelpObjs.sendMail(db, "Delivery Notification failed", "Delivery Notification failed: " + shipmentId, 86030, false);
      } finally {
        dbrs.close();
      }
    } //end of if shipment is not empty
  } //end of method

  private void buildHeader(String selHubnum, Hashtable hub_list_set, String buildlabels, String boloptions, String deliverydate, HttpSession session,
                           Vector autoShipConfirmlistV, Collection hubInventoryGroupOvBeanCollection, String selInvenGroup, String sortResultsBy) {
    try {
      out.println(radian.web.HTMLHelpObj.printHTMLHeader("Ship Confirm", "shipconfirm.js",intcmIsApplication));
      out.println("<SCRIPT SRC=\"/clientscripts/calendar.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
      out.println("</HEAD>  \n");

      String shipmentIdList = session.getAttribute("SHIPMENT_ID_LIST") == null ? "" : (String) session.getAttribute("SHIPMENT_ID_LIST");

      if ("GENERATE_LABELS".equalsIgnoreCase(buildlabels)) {
        out.println("<BODY onLoad=\"doPrintbol()\">\n");
      } else if ("GENERATE_BOX_LABELS".equalsIgnoreCase(buildlabels)) {
        out.println("<BODY onLoad=\"doPrintbox()\">\n");
      } else if ("GENERATE_PACKING_LIST".equalsIgnoreCase(buildlabels) && shipmentIdList.length() > 0) {
        out.println("<BODY onLoad=\"doPrintPackingList('" + shipmentIdList + "')\">\n");
        session.setAttribute("SHIPMENT_ID_LIST", "");
      } else if ("ConsolidatedBol".equalsIgnoreCase(buildlabels) && shipmentIdList.length() > 0) {
        out.println("<BODY onLoad=\"doPrintConsolidatedBol(" + shipmentIdList + ")\">\n");
      } else {
        out.println("<BODY>\n");
      }

      out.println("<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n");
      out.println("<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>Please wait while the page is updated...</B></FONT></center>\n");
      out.println("</DIV>\n");
      out.println("<DIV ID=\"MAINPAGE\" STYLE=\"\">\n");
      out.println(radian.web.HTMLHelpObj.PrintTitleBar("<B>Ship Confirm</B>\n"));

      out.println("<SCRIPT LANGUAGE=\"JavaScript\" TYPE=\"text/javascript\">\n <!--\n");
      //out.println(radian.web.poHelpObj.createhubinvgrjs(initialinvData));
      out.println(radian.web.poHelpObj.getHubInventoryGroupJavaScript(hubInventoryGroupOvBeanCollection, true, false));
      out.println("// -->\n </SCRIPT>\n");

      Hashtable hub_list = (Hashtable) (hub_list_set.get("HUB_LIST"));
      if (hub_list.size() < 1) {
        out.println(radian.web.HTMLHelpObj.printMessageinTable("<BR><BR>You don't have access to this page.<BR><BR>Contact tech support for more information."));
        return;
      }

      Hashtable hub_prority_list = (Hashtable) (hub_list_set.get("HUB_PRORITY_LIST"));
      if (selHubnum.trim().length() == 0) {
        //selHubnum=radian.web.HTMLHelpObj.intselectedhub( hub_prority_list.entrySet() );
        selHubnum = radian.web.HTMLHelpObj.getFirstBranchPlant(hubInventoryGroupOvBeanCollection);
      }
      boolean autoShipConfirmHub = false;
      if (autoShipConfirmlistV.contains(selHubnum)) {
        autoShipConfirmHub = true;
      }

      String hubname = radian.web.HTMLHelpObj.gethubNamefromBP(hub_list_set, selHubnum);
      PersonnelBean personnelBean = session.getAttribute("personnelBean") == null ? null : (PersonnelBean) session.getAttribute("personnelBean");
      if (personnelBean.getPermissionBean().hasFacilityPermission("Picking QC", hubname,null)) {
        this.setupdateStatus(true);
      } else {
        this.setupdateStatus(false);
      }

      //Eagle form for update
      out.println("<FORM method=\"POST\" NAME=\"shipconfirm\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\"" + bundle.getString("SHIP_CONFIRM") + "\">\n");
      //start table #1
      out.println("<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"100%\" CLASS=\"moveup\">\n");
      out.println("<TR>\n");

      //Hub
      out.println("<TD WIDTH=\"7%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
      out.println("<B>Hub:</B>&nbsp;\n");
      out.println("</TD>\n");
      out.println("<TD WIDTH=\"10%\">\n");
      //out.println( "<SELECT CLASS=\"HEADER\"  NAME=\"HubName\" size=\"1\">\n" );
      out.println("<SELECT CLASS=\"HEADER\"  NAME=\"HubName\" OnChange=\"hubchanged(document.shipconfirm.HubName)\" size=\"1\">\n");
      //Hashtable hub_prority_list = (Hashtable)(hub_list_set.get("HUB_PRORITY_LIST"));
      /*if ( selHubnum.trim().length() == 0 )
         {
        selHubnum=radian.web.HTMLHelpObj.intselectedhub( hub_prority_list.entrySet() );
         }
         out.println(radian.web.HTMLHelpObj.sorthashbyValue(hub_prority_list.entrySet(),selHubnum,hub_list));*/
      out.println(radian.web.HTMLHelpObj.getHubDropDown(hubInventoryGroupOvBeanCollection, selHubnum));
      //String SelectvvedHubName = ( String ) ( hub_list.get( selHubnum ) );

      //out.println( radian.web.HTMLHelpObj.sorthashbyValue( hub_prority_list.entrySet(),selHubnum,hub_list ) );
      out.println("</SELECT>\n");
      out.println("</TD>\n");

      //Search
      out.println("<TD WIDTH=\"5%\" CLASS=\"announce\">\n");
      out.println("<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Search\" onclick= \"return search(this)\" NAME=\"SearchButton\">&nbsp;\n");
      out.println("</TD>\n");

      //first row
      if (this.getupdateStatus()) {
        //Print BOL SHORT
        out.println("<TD WIDTH=\"10%\" CLASS=\"announce\">\n");
        out.println("<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Print BOL Short\" onclick= \"return bolshort(this)\" NAME=\"SearchButton\">&nbsp;\n");
        out.println("</TD>\n");
        //Box Labels
        out.println("<TD WIDTH=\"10%\" CLASS=\"announce\">\n");
        out.println("<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Print BOX Labels\" onclick= \"return boxlabels(this)\" NAME=\"UpdateButton\">&nbsp;\n");
        out.println("</TD>\n");
        if (autoShipConfirmHub) {
          out.println("<TD WIDTH=\"10%\">\n");
          out.println("&nbsp;\n");
          out.println("</TD>\n");
        } else {
          //create shipment
          out.println("<TD WIDTH=\"10%\" CLASS=\"announce\">\n");
          out.println("<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Create Shipment\" onclick= \"return createShipment(this)\" NAME=\"createShipmentButton\">&nbsp;\n");
          out.println("</TD>\n");
          //add to shipment
          out.println("<TD WIDTH=\"10%\" CLASS=\"announce\">\n");
          out.println("<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Update Shipment\" onclick= \"return addToShipment(this)\" NAME=\"addToShipmentButton\">&nbsp;\n");
          out.println("</TD>\n");
        }
        /*//remove from shipment
            out.println("<TD WIDTH=\"10%\" CLASS=\"announce\">\n");
            out.println("<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Remove from Shipment\" onclick= \"return removeFromShipment(this)\" NAME=\"removeFromShipmentButton\">&nbsp;\n");
            out.println("</TD>\n");*/
      } else {
        out.println("<TD WIDTH=\"10%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
        out.println("&nbsp;\n");
        out.println("</TD>\n");
        out.println("<TD WIDTH=\"10%\">\n");
        out.println("&nbsp;\n");
        out.println("</TD>\n");
        out.println("<TD WIDTH=\"10%\">\n");
        out.println("&nbsp;\n");
        out.println("</TD>\n");
        /*out.println("<TD WIDTH=\"10%\">\n");
         out.println("&nbsp;\n");
         out.println("</TD>\n");*/
      }
      out.println("</TR>\n");

      out.println("<TR VALIGN=\"MIDDLE\">\n");
      out.println("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
      out.println("<B>Inv Grp:</B>&nbsp;</TD>\n");
      out.println("<TD WIDTH=\"10%\" HEIGHT=\"35\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
      out.println("<SELECT CLASS=\"HEADER\"  NAME=\"invengrp\" size=\"1\">\n");
      out.println(radian.web.HTMLHelpObj.getInventoryGroupDropDown(hubInventoryGroupOvBeanCollection, selHubnum, selInvenGroup, false));

      /*Hashtable fh = (Hashtable)initialinvData.get(selHubnum);
         Vector invidV = ( Vector ) fh.get( "INVENTORY_GROUP" );
         Vector invidName = ( Vector ) fh.get( "INVENTORY_GROUP_NAME" );
         for (int i=0; i < invidV.size(); i++ )
         {
        String preSelect="";
        String invGroup= ( String ) invidV.elementAt( i );
        String invGroupName= ( String ) invidName.elementAt( i );
        if ( selInvenGroup.equalsIgnoreCase( invGroup ) )
        {
       preSelect="selected";
        }
        out.println( "<option " + preSelect + " value=\"" + invGroup + "\"> " + invGroupName + "</option>\n" );
         }*/
      out.println("</SELECT>\n");
      out.println("</TD>\n");

      if (this.getupdateStatus()) {
        //Update
        out.println("<TD WIDTH=\"5%\" CLASS=\"announce\">\n");
        if (autoShipConfirmHub) {
          out.println("<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Confirm Shipment\" onclick= \"return update(this)\" NAME=\"UpdateButton\">&nbsp;\n");
        } else {
          out.println("<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Confirm Shipment\" onclick= \"addTrackingNo(" +
                      selHubnum + ")\" NAME=\"addTrackingNoButton\">&nbsp;\n");
        }
        out.println("</TD>\n");
      } else {
        out.println("<TD WIDTH=\"5%\">\n");
        out.println("&nbsp;\n");
        out.println("</TD>\n");
      }

      if (this.getupdateStatus()) {
        //Print BOL Long
        out.println("<TD WIDTH=\"10%\" CLASS=\"announce\">\n");
        out.println("<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Print BOL Long\" onclick= \"return bollong(this)\" NAME=\"UpdateButton\">&nbsp;\n");
        out.println("</TD>\n");
        if (autoShipConfirmHub) {
          out.println("<TD WIDTH=\"10%\">\n");
          out.println("&nbsp;\n");
          out.println("</TD>\n");

          out.println("<TD WIDTH=\"10%\">\n");
          out.println("&nbsp;\n");
          out.println("</TD>\n");
        } else {
          //print packing list
          out.println("<TD WIDTH=\"10%\" CLASS=\"announce\">\n");
          out.println("<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Print Packing List\" onclick= \"return printConsolPL(" +
                      selHubnum + ")\" NAME=\"printConsolPLButton\">&nbsp;\n");
          out.println("</TD>\n");
                    out.println("<TD WIDTH=\"10%\" CLASS=\"announce\">\n");
          out.println("<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Print Delivery Documents\" onclick= \"return printDelDocuments(" +
                      selHubnum + ")\" NAME=\"printDelDocumentsButton\">&nbsp;\n");
          out.println("</TD>\n");

          /*//Add tracking No.
            out.println("<TD WIDTH=\"10%\" CLASS=\"announce\">\n");
            out.println("<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Add Tracking #\" onclick= \"addTrackingNo("+selHubnum+")\" NAME=\"addTrackingNoButton\">&nbsp;\n");
            out.println("</TD>\n");*/
        }
      } else {
        out.println("<TD WIDTH=\"10%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
        out.println("&nbsp;\n");
        out.println("</TD>\n");
        out.println("<TD WIDTH=\"10%\">\n");
        out.println("&nbsp;\n");
        out.println("</TD>\n");
        /*out.println("<TD WIDTH=\"10%\">\n");
         out.println("&nbsp;\n");
         out.println("</TD>\n");*/
      }
      out.println("</TR>\n");

      out.println("<TR>\n");
      if (this.getupdateStatus()) {
        if (autoShipConfirmHub) {
          //Date Delivered
          out.println("<TD WIDTH=\"7%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
          out.println("<B>Date Delivered:<BR>(mm/dd/yyyy)</B>&nbsp;\n");
          out.println("</TD>\n");
          out.println("<TD  ALIGN=\"LEFT\" WIDTH=\"10%\">\n");
          out.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"datedelivered\" value=\"" + deliverydate + "\" maxlength=\"10\" size=\"10\">\n");
          out.println("<FONT SIZE=\"4\"><a href=\"javascript: void(0);\" ID=\"datelink\" onClick=\"return getCalendar(document.shipconfirm.datedelivered);\">&diams;</a></FONT></td>\n");
          out.println("</TD>\n");
        } else {
          out.println("<TD WIDTH=\"7%\">\n");
          out.println("&nbsp;\n");
          out.println("</TD>\n");
          out.println("<TD WIDTH=\"10%\">\n");
          out.println("&nbsp;\n");
          out.println("<INPUT CLASS=\"HEADER\" TYPE=\"hidden\" NAME=\"datedelivered\" value=\"" + deliverydate + "\">\n");
          out.println("</TD>\n");
        }
      } else {
        out.println("<TD WIDTH=\"7%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
        out.println("&nbsp;\n");
        out.println("</TD>\n");
        out.println("<TD WIDTH=\"10%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
        out.println("&nbsp;\n");
        out.println("</TD>\n");
      }

      out.println("<TD HEIGHT=\"35\" WIDTH=\"5%\" VALIGN=\"MIDDLE\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
      out.println("<B>Sort By:</B>&nbsp;\n");
      out.println("</TD>\n");
      out.println("<TD WIDTH=\"10%\" ALIGN=\"LEFT\">\n");
      out.println("<SELECT CLASS=\"HEADER\" NAME=\"sortResultsBy\" size=\"1\">\n");

      Hashtable sortresult = new Hashtable();
      sortresult.put("Dock/Deliver To", "SHIP_TO_LOCATION_ID,DELIVERY_POINT");
      sortresult.put("Dock/Facility/WA", "SHIP_TO_LOCATION_ID,FACILITY_ID,APPLICATION");
      sortresult.put("MR/Line/Picklist", "PR_NUMBER,LINE_ITEM,BATCH");
      sortresult.put("Facility/MR/Line", "FACILITY_ID,PR_NUMBER,LINE_ITEM");

      out.println(radian.web.HTMLHelpObj.getDropDown(sortresult, sortResultsBy));
      out.println("</SELECT>\n");
      out.println("</TD>\n");

      out.println("<TD WIDTH=\"5%\" HEIGHT=\"35\" CLASS=\"announce\" ALIGN=\"LEFT\">\n");
      out.println("<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Create Exl File\" onclick= \"generatexls(this)\" NAME=\"CreateExl\">\n");
      out.println("</TD>\n");

      if (this.getupdateStatus()) {
        if (!autoShipConfirmHub) {
          out.println("<TD WIDTH=\"5%\" HEIGHT=\"35\" CLASS=\"announce\" ALIGN=\"LEFT\">\n");
          out.println("<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Consolidated BOL\" onclick= \"generatConsolidatedBol()\" NAME=\"ConsolidatedBol\">\n");
          out.println("</TD>\n");
        } else {
          out.println("<TD WIDTH=\"5%\" HEIGHT=\"35\" CLASS=\"announce\" ALIGN=\"LEFT\">\n");
          out.println("&nbsp;\n");
          out.println("</TD>\n");
        }
      }
      out.println("</TR>\n");

      out.println("</TABLE>\n");

      out.println("<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\">\n");
      out.println("<tr><td>");
      out.println("<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\"NEW\">\n");
      out.println("<INPUT TYPE=\"hidden\" NAME=\"boldetails\" VALUE=\"" + boloptions + "\">\n");
      out.println("</TD></tr>");
      out.println("</table>\n");
      //end table #2

      if (errormsg.trim().length() > 0) {
        radian.web.HTMLHelpObj.printMessageinTable(errormsg);
      }
    } catch (Exception e) {
      e.printStackTrace();
      out.println(radian.web.HTMLHelpObj.printError("Ship Confirm",intcmIsApplication));
    }

    return;
  }

  private void buildDetails(Vector data, String SubuserAction, boolean autoShipConfirmHub, Hashtable validShipmentIds) {
    String checkednon = "";
    try {
      Hashtable summary = new Hashtable();
      summary = (Hashtable) data.elementAt(0);
      int total = ( (Integer) (summary.get("TOTAL_NUMBER"))).intValue();
      int vsize = data.size();
      out.println("<BR><FONT SIZE=\"2\" FACE=\"Arial\" COLOR=\"#fc0303\"><B><A HREF=\"javascript:showshipconfirmlegend()\" STYLE=\"color:#0000ff;cursor:hand\">Legend</A></B></FONT><BR>\n");
      out.println("<TABLE BORDER=\"0\" BGCOLOR=\"#000000\" CELLSPACING=\"1\" CELLPADDING=\"2\" WIDTH=\"100%\" CLASS=\"moveup\">\n");
      int i = 0; //used for detail lines
      int lineadded = 0;

      /*out.println( "<tr align=\"center\">\n" );
         if ( this.getupdateStatus() )
         {
        out.println("<TH width=\"2%\"  height=\"38\">OK<BR><INPUT TYPE=\"checkbox\" value=\"\" onClick=\"checkall('row_chk','1')\" NAME=\"chkall\"></TH>\n" );
         }
         out.println( "<TH width=\"5%\"  height=\"38\">Shipment ID</TH>\n" );
         //out.println("<TH width=\"5%\"  height=\"38\">Carrier / Tracking #</TH>\n");
         out.println( "<TH width=\"5%\"  height=\"38\">Company</TH>\n" );
         out.println( "<TH width=\"5%\"  height=\"38\">Facility</TH>\n" );
         out.println( "<TH width=\"15%\"  height=\"38\">Work Area/Deliver To</TH>\n" );
         out.println( "<TH width=\"5%\"  height=\"38\">Dock</TH>\n" );
         out.println( "<TH width=\"5%\"  height=\"38\">Hazard Category</TH>\n" );
         out.println( "<TH width=\"5%\"  height=\"38\">MR-Line</TH>\n" );
         out.println( "<TH width=\"5%\"  height=\"38\">Picklist ID</TH>\n" );
         out.println( "<TH width=\"5%\"  height=\"38\">Part</TH>\n" );
         out.println( "<TH width=\"5%\"  height=\"38\">Quantity</TH>\n" );
         out.println( "<TH width=\"5%\"  height=\"38\">Date Picked mm/dd/yyyy</TH>\n" );
         out.println( "<TH width=\"5%\"  height=\"38\">Picker</TH>\n" );
         out.println( "</tr>\n" );*/

      for (int loop = 0; loop < total; loop++) {
        i++;
        boolean createHdr = false;

        if (loop % 10 == 0)
        //if ( ( lineadded % 10 == 0 ) && ( lineadded != 0 ) )
        {
          createHdr = true;
        }

        if (createHdr) {
          out.println("<tr align=\"center\">\n");
          if (this.getupdateStatus() && loop == 0) {
            out.println("<TH width=\"2%\"  height=\"38\">OK<BR><INPUT TYPE=\"checkbox\" value=\"\" onClick=\"checkall('row_chk','1')\" NAME=\"chkall\"></TH>\n");
          } else if (this.getupdateStatus()) {
            out.println("<TH width=\"2%\"  height=\"38\">OK</TH>\n");
          }
          out.println("<TH width=\"5%\"  height=\"38\">Shipment ID</TH>\n");
          //out.println("<TH width=\"5%\"  height=\"38\">Carrier / Tracking #</TH>\n");
          out.println("<TH width=\"5%\"  height=\"38\">Company</TH>\n");
          out.println("<TH width=\"10%\"  height=\"38\">Facility</TH>\n");
          out.println("<TH width=\"15%\"  height=\"38\">Work Area</TH>\n");
          out.println("<TH width=\"5%\"  height=\"38\">Dock</TH>\n");
          out.println("<TH width=\"5%\"  height=\"38\">Hazard Category</TH>\n");
          out.println("<TH width=\"5%\"  height=\"38\">MR-Line</TH>\n");
          out.println("<TH width=\"5%\"  height=\"38\">Picklist ID</TH>\n");
          out.println("<TH width=\"5%\"  height=\"38\">Part</TH>\n");
          out.println("<TH width=\"3%\"  height=\"38\">Quantity</TH>\n");
          out.println("<TH width=\"5%\"  height=\"38\">Date Picked mm/dd/yyyy</TH>\n");
          out.println("<TH width=\"5%\"  height=\"38\">Picker</TH>\n");
          out.println("</tr>\n");
          createHdr = false;
        }

        Hashtable hD = new Hashtable();
        hD = (Hashtable) data.elementAt(i);

        // get main information
        String Qty = hD.get("QUANTITY").toString();
        String facility = hD.get("FACILITY_ID").toString();
        String picker = hD.get("ISSUER_NAME").toString();
        String workarea = hD.get("APPLICATION").toString();
        String prnumber = hD.get("PR_NUMBER").toString();
        String lineitem = hD.get("LINE_ITEM").toString();
        String part = hD.get("CAT_PART_NO").toString();
        String datepicked = hD.get("DATE_PICKED").toString();
        String datedelivered = hD.get("DELIVERED_DATE").toString();
        String companyid = hD.get("COMPANY_ID").toString();
        String Line_Check = (hD.get("USERCHK") == null ? "&nbsp;" : hD.get("USERCHK").toString());
        String shipmentID = hD.get("SHIPMENT_ID").toString();
        String carrierCodeTracking = hD.get("CARRIER_CODE_TRACKING").toString();
        String batchno = hD.get("BATCH").toString();
        String shipToLocationId = hD.get("SHIP_TO_LOCATION_ID") == null ? "" : hD.get("SHIP_TO_LOCATION_ID").toString();
        String shipToCompanyId = hD.get("SHIP_TO_COMPANY_ID") == null ? "" : hD.get("SHIP_TO_COMPANY_ID").toString();
        String hazardCategory = hD.get("HAZARD_CATEGORY") == null ? "" : hD.get("HAZARD_CATEGORY").toString();
        String overShipped = hD.get("OVER_SHIPPED") == null ? "" : hD.get("OVER_SHIPPED").toString();
            //String deliveryPoint=hD.get( "DELIVERY_POINT" ) == null ? "" : hD.get( "DELIVERY_POINT" ).toString();
        String applicationDesc = hD.get("APPLICATION_DESC") == null ? "" : hD.get("APPLICATION_DESC").toString();

        if (Line_Check.equalsIgnoreCase("yes")) {
          checkednon = "checked";
        } else {
          checkednon = "";
        }

        String LINE_STATUS = hD.get("LINE_STATUS").toString();
        String Color = " ";
        if (lineadded % 2 == 0) {
          Color = "CLASS=\"white";
        } else {
          Color = "CLASS=\"blue";
        }

        if ("Y".equalsIgnoreCase(overShipped)) {
          Color = "CLASS=\"orange";
        }

        String chkbox_value = "no";
        if (checkednon.equalsIgnoreCase("checked")) {
          chkbox_value = "yes";
        }

        if (SubuserAction.equalsIgnoreCase("Update") && "NO".equalsIgnoreCase(LINE_STATUS)) {
          Color = "CLASS=\"error";
        }

        if ( (SubuserAction.equalsIgnoreCase("Update") && "YES".equalsIgnoreCase(LINE_STATUS)) || ("YES".equalsIgnoreCase(LINE_STATUS))) {
          out.println("<input type=\"hidden\" name=\"delivery_date" + i + "\" value=\"" + datedelivered + "\">\n");
        } else {
          lineadded++;
          out.println("<tr align=\"center\">\n");

          if (this.getupdateStatus()) {
            out.println("<td " + Color + "\" width=\"2%\" height=\"38\"><INPUT TYPE=\"checkbox\" value=\"" + (chkbox_value) +
                        "\"  onClick= \"CheckValue(name,this,'delivery_date" + i + "','"+i+"')\" " + checkednon + "  NAME=\"row_chk" + i + "\" ID=\"row_chk" + i +
                        "\"></td>\n");
          }

          out.println("<td " + Color + "\" width=\"5%\" height=\"38\">\n");
          if (autoShipConfirmHub) {
            out.println("" + shipmentID + "\n");
            out.println("<INPUT TYPE=\"hidden\" NAME=\"shipmentId" + i + "\" VALUE=\"" + shipmentID + "\">\n");
          } else {
            out.println("<select name=\"shipmentId" + i + "\" onChange= \"checkBoxOnChangeValue('row_chk" + i + "')\">\n");

            Vector shipmentIdset = (Vector) validShipmentIds.get(shipToLocationId);
            int shipmentIdVsize = shipmentIdset.size();
            String shipmentIdselected = "";

            if (shipmentIdVsize > 0) {
              if (shipmentID.length() == 0) {
                out.println("<option selected value=\"\"></option>\n");
              } else {
                out.println("<option value=\"\"></option>\n");
              }

              for (int j = 0; j < shipmentIdVsize; j++) {
                String tmpShipmentId = (String) shipmentIdset.elementAt(j);
                shipmentIdselected = "";
                if (tmpShipmentId.equalsIgnoreCase(shipmentID)) {
                  shipmentIdselected = "selected";
                }
                out.println("<option " + shipmentIdselected + " value=\"" + tmpShipmentId + "\">" + tmpShipmentId + "</option>\n");
              }
            } else {
              out.println("<option " + shipmentIdselected + " value=\"" + shipmentID + "\">" + shipmentID + "</option>\n");
            }
            out.println("</select>\n");
          }
          out.println("</td>\n");

          out.println("<input type=\"hidden\" name=\"delyDocViewBean["+ i +"].lineItem\" id=\"lineItem"+i+"\" value=\"" + lineitem + "\">\n");
          out.println("<input type=\"hidden\" name=\"delyDocViewBean["+ i +"].prNumber\" id=\"prNumber"+i+"\" value=\"" + prnumber + "\">\n");
          out.println("<input type=\"hidden\" name=\"delyDocViewBean["+ i +"].picklistId\" id=\"picklistId"+i+"\" value=\"" + batchno + "\">\n");
          out.println("<input type=\"hidden\" name=\"delyDocViewBean["+ i +"].printOk\" id=\"printOk"+i+"\" value=\"\">\n");

          //out.println("<td "+Color+"\" width=\"5%\" height=\"38\">"+shipmentID+"</td>\n");
          //out.println("<td "+Color+"\" width=\"5%\" height=\"38\">"+carrierCodeTracking+"</td>\n");
          out.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + companyid + "</td>\n");
          out.println("<td " + Color + "\" width=\"10%\" height=\"38\">" + facility + "</td>\n");
          out.println("<td " + Color + "\" width=\"15%\" height=\"38\">" + applicationDesc + "</td>\n");
          out.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + shipToLocationId + "</td>\n");
          out.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + hazardCategory + "</td>\n");
          out.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + prnumber + " - " + lineitem + "</td>\n");
          out.println("<td " + Color + "\" width=\"5%\" height=\"38\">PL " + batchno + "</td>\n");
          out.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + part + "</td>\n");
          out.println("<td " + Color + "\" width=\"3%\" height=\"38\">" + Qty + "</td>\n");
          out.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + datepicked + "</td>\n");
          out.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + picker + "</td>\n");
          out.println("</tr>\n");
        }
      }

      out.println("</table>\n");
      out.println("<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\" CLASS=\"moveup\">\n");
      out.println("<tr>");
      out.println("<TD HEIGHT=\"25\" WIDTH=\"100%\" VALIGN=\"MIDDLE\" BGCOLOR=\"#000066\">&nbsp;\n");
      out.println("</TD></tr>");
      out.println("</table>\n");
      out.println("<INPUT TYPE=\"hidden\" NAME=\"totallines\" ID=\"totallines\" VALUE=\"" + i + "\">\n");

      out.println("</form>\n");
      out.println("</body></html>\n");
    } catch (Exception e) {
      e.printStackTrace();
      out.println(radian.web.HTMLHelpObj.printError("Ship Confirm",intcmIsApplication));
    }

    return;
  }
}