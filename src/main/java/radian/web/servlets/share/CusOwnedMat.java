package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.tcmis.common.admin.beans.PersonnelBean;
import java.util.Collection;
import java.math.BigDecimal;

import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2001
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 * 06-27-03 - Code cleanup
 * 08-19-03 - Sending Error Emails
 * 09-11-03 - Showing a POP with the returned receipts
 * 12-12-03 - Ordering BINs by last date used
 * 02-10-04 - Sorting Drop Downs
 * 04-26-04 - Removing the need to use HubReceivingTables here, Getting Hub list from Hub_pref
 * 05-07-04 - Sorting the Hub Drop Down based on the prority in hub_pref table. For each Hub in hub pref there has to be some permission in user_group_member
 */

public class CusOwnedMat {
  private ServerResourceBundle bundle = null;
  private TcmISDBModel db = null;
  private PrintWriter out = null;
  private boolean completeSuccess = true;
  private boolean noneToUpd = true;
  private int AddNewBinLineNum;
  private boolean intcmIsApplication = false;

  private void setAddBinLineNum(int id) {
    this.AddNewBinLineNum = id;
  }

  private int getAddBinLineNum() throws Exception {
    return this.AddNewBinLineNum;
  }

  private boolean allowupdate;
  public void setupdateStatus(boolean id) {
    this.allowupdate = id;
  }

  private boolean getupdateStatus() {
    return this.allowupdate;
  }

  public CusOwnedMat(ServerResourceBundle b, TcmISDBModel d) {
    bundle = b;
    db = d;
  }

  public void doResult(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
    out = response.getWriter();
    response.setContentType("text/html");

    String reciptId = "";
    String item = "";
    String part = "";
    String mrnum = "";
    String facility = "";

    PersonnelBean personnelBean = (PersonnelBean) session.getAttribute("personnelBean");
    Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
    intcmIsApplication = false;
    if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
    {
     intcmIsApplication = true;
    }

    String branch_plant = session.getAttribute("BRANCH_PLANT") == null ? "" : session.getAttribute("BRANCH_PLANT").toString();
    String personnelid = session.getAttribute("PERSONNELID") == null ? "" : session.getAttribute("PERSONNELID").toString();
    String CompanyID = session.getAttribute("COMPANYID") == null ? "" : session.getAttribute("COMPANYID").toString();

    String User_Action = request.getParameter("UserAction");
    if (User_Action == null) {
      User_Action = "New";
    }
    String User_Sort = request.getParameter("SortBy");
    if (User_Sort == null) {
      User_Sort = "1";
    }
    reciptId = request.getParameter("receiptid");
    if (reciptId == null) {
      reciptId = "";
    }
    item = request.getParameter("item");
    if (item == null) {
      item = "";
    }
    part = request.getParameter("part");
    if (part == null) {
      part = "";
    }
    mrnum = request.getParameter("mrnum");
    if (mrnum == null) {
      mrnum = "";
    }
    facility = request.getParameter("FacName");
    if (facility == null) {
      facility = "";

    }
    String SubUser_Action = request.getParameter("SubUserAction");
    if (SubUser_Action == null) {
      SubUser_Action = "JSError2";

    }
    String addbin_item_id = "";
    String addbin_bin_id = "";

    try {
      Map warehouseInfo = (Map) session.getAttribute("warehouseInfo");
      if (warehouseInfo == null) {
        //PersonnelBean personnelBean = session.getAttribute("personnelBean") == null ? null : (PersonnelBean) session.getAttribute("personnelBean");
        radian.web.HTMLHelpObj.LoginSetup(session, db, "" + personnelBean.getPersonnelId() + "", CompanyID, personnelBean.getLogonId());
      }

      Hashtable hub_list_set = new Hashtable();
      hub_list_set = (Hashtable) session.getAttribute("HUB_PREF_LIST");

      if ("Search".equalsIgnoreCase(User_Action)) {
        String companyArrayJs = (String) session.getAttribute("CATALOGS_JS");
        //Hashtable hub_list_set  = (Hashtable)session.getAttribute("HUB_SET");
        Vector openbinSet = radian.web.HTMLHelpObj.createEmptyBinSet(db, branch_plant);
        session.setAttribute("EMPTYBIN", openbinSet);

        Vector searchdata = new Vector();
        searchdata = this.getResultData(item, part, branch_plant, reciptId, mrnum, facility);
        Hashtable sum = (Hashtable) (searchdata.elementAt(0));

        int count = ( (Integer) sum.get("TOTAL_NUMBER")).intValue();
        if (0 == count) {
          buildHeader(hub_list_set, item, part, reciptId, mrnum, User_Sort, branch_plant, facility, companyArrayJs, session, User_Action);
          out.println(radian.web.HTMLHelpObj.printHTMLNoData("No Items Found"));
          out.close();
          //clean up
          searchdata = null;
          hub_list_set = null;
        } else {
          session.setAttribute("DATA", searchdata);

          Hashtable out_bin_set2 = createItemBinSet(searchdata, branch_plant);
          session.setAttribute("BIN_SET", out_bin_set2);

          buildHeader(hub_list_set, item, part, reciptId, mrnum, User_Sort, branch_plant, facility, companyArrayJs, session, User_Action);
          buildDetails(searchdata, "", out_bin_set2, branch_plant, addbin_item_id);
          out.close();
          //clean up
          searchdata = null;
          hub_list_set = null;

        }
      } else if (User_Action.equalsIgnoreCase("Update") && SubUser_Action.equalsIgnoreCase("AddBin")) {
        Vector retrn_data = (Vector) session.getAttribute("DATA");
        Vector synch_data = SynchServerData(request, retrn_data);

        String AddBin_Line_No = "0";

        addbin_item_id = BothHelpObjs.makeBlankFromNull(request.getParameter("AddBin_Item_Id"));
        addbin_bin_id = BothHelpObjs.makeBlankFromNull(request.getParameter("AddBin_Bin_Id"));
        AddBin_Line_No = BothHelpObjs.makeBlankFromNull(request.getParameter("AddBin_Line_No"));

        setAddBinLineNum(Integer.parseInt(AddBin_Line_No));

        Hashtable all_bin_set_a = (Hashtable) session.getAttribute("BIN_SET");
        //Hashtable hub_list_set     = (Hashtable)session.getAttribute("HUB_SET");
        String companyArrayJs = (String) session.getAttribute("CATALOGS_JS");

        Hashtable all_new_bin_set = addToItemBinSet(addbin_item_id, addbin_bin_id, all_bin_set_a);
        //
        session.setAttribute("DATA", synch_data);
        session.setAttribute("BIN_SET", all_new_bin_set);

        buildHeader(hub_list_set, item, part, reciptId, mrnum, User_Sort, branch_plant, facility, companyArrayJs, session, "");
        buildDetails(synch_data, "", all_bin_set_a, branch_plant, addbin_item_id);

        out.close();
        //clean up
        synch_data = null;
        all_new_bin_set = null;
        all_bin_set_a = null;
        //
      } else if (User_Action.equalsIgnoreCase("Update")) {
        Vector retrn_data = (Vector) session.getAttribute("DATA");
        Vector synch_data = SynchServerData(request, retrn_data);

        retrn_data = null;
        //Hashtable hub_list_set = (Hashtable)session.getAttribute("HUB_SET");
        Hashtable all_bin_set_e = (Hashtable) session.getAttribute("BIN_SET");
        String companyArrayJs = (String) session.getAttribute("CATALOGS_JS");

        session.setAttribute("DATA", synch_data);

        Hashtable updateresults = UpdateDetails(synch_data, personnelid);
        Boolean result = (Boolean) updateresults.get("RESULT");
        Vector errordata = (Vector) updateresults.get("ERRORDATA");

        boolean resulttotest = result.booleanValue();

        buildHeader(hub_list_set, item, part, reciptId, mrnum, User_Sort, branch_plant, facility, companyArrayJs, session, User_Action);

        if (false == resulttotest)

        {
          if (true == noneToUpd) {
            out.println(radian.web.HTMLHelpObj.printMessageinTable("No Item was Choosen for Receiving"));
            buildDetails(errordata, "Update", all_bin_set_e, branch_plant, addbin_item_id);
          } else {
            out.println(radian.web.HTMLHelpObj.printMessageinTable("An Error Occurred.<BR>Please Check Data and try Again."));
            buildDetails(errordata, "Update", all_bin_set_e, branch_plant, addbin_item_id);
          }
          out.close();
        } else {
          if (true == completeSuccess) {
            out.println(radian.web.HTMLHelpObj.printMessageinTable("All Your Selections Were Successfully Received"));
            buildDetails(errordata, "Update", all_bin_set_e, branch_plant, addbin_item_id);
            out.close();
          } else {
            out.println(radian.web.HTMLHelpObj.printMessageinTable("Some of Your Selections Shown below Were not Updated"));
            buildDetails(errordata, "Update", all_bin_set_e, branch_plant, addbin_item_id);
            out.close();
          }
        }
        //clean up
        errordata = null;
        updateresults = null;
      } else {
        Hashtable hub_list1 = (Hashtable) (hub_list_set.get("HUB_LIST"));

        /*if ( this.getupdateStatus() )
             {
          hub_list_set=radian.web.HTMLHelpObj.createHubList( db,personnelid,CompanyID );
             }
             else
             {
          hub_list_set=radian.web.HTMLHelpObj.createAllHubList( db,CompanyID );
             }*/

        String companyArrayJs = "";
        if (hub_list1.size() > 0) {
          companyArrayJs = radian.web.HTMLHelpObj.getHubJs(hub_list_set, session).toString();
          session.setAttribute("CATALOGS_JS", companyArrayJs);
        }

        //session.setAttribute("HUB_SET", hub_list_set );
        buildHeader(hub_list_set, item, part, reciptId, mrnum, User_Sort, branch_plant, facility, companyArrayJs, session, User_Action);
        out.println(radian.web.HTMLHelpObj.printHTMLSelect());
        out.close();

        hub_list_set = null;
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      out.close();
    }
  }

  private Vector getResultData(String itemsea, String parts, String BranchPlant, String receiptid, String mrnum, String facility) throws Exception {
    Vector Data = new Vector();
    Hashtable DataH = new Hashtable();
    boolean flagforwhere = false;
    Hashtable summary = new Hashtable();

    DBResultSet dbrs = null;
    ResultSet rs = null;

    int count = 0;
    summary.put("TOTAL_NUMBER", new Integer(count));
    Data.addElement(summary);

    try {
      String query = "select to_char(sysdate,'mm/dd/yyyy') SYS_DATE,a.* from mr_ISSUE_receipt_VIEW a where ";

      if (itemsea.length() > 1) {
        query += "ITEM_ID = " + itemsea.trim() + " ";
        flagforwhere = true;
      } else if (parts.length() > 1) {
        query += "CAT_PART_NO like '%" + parts.trim() + "%' ";
        flagforwhere = true;
      }

      if (receiptid.length() > 1) {
        if (flagforwhere) {
          query += "and RECEIPT_ID = " + receiptid + " ";
        } else {
          query += " RECEIPT_ID = " + receiptid + " ";
          flagforwhere = true;
        }
      }

      if (mrnum.length() > 1) {
        if (flagforwhere) {
          query += "and PR_NUMBER = " + mrnum + " ";
        } else {
          query += " PR_NUMBER = " + mrnum + " ";
          flagforwhere = true;
        }
      }

      if (facility.length() > 1 && !"All".equalsIgnoreCase(facility)) {
        if (flagforwhere) {
          query += "and FACILITY_ID = '" + facility + "' ";
        } else {
          query += " FACILITY_ID = '" + facility + "' ";
          flagforwhere = true;
        }
      }

      if (flagforwhere) {
        query += " and SOURCE_HUB = " + BranchPlant + " ";
      } else {
        query += " rownum = 0";
      }

      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();

      while (rs.next()) {
        count++;
        DataH = new Hashtable();
        DataH.put("COMPANY_ID", rs.getString("COMPANY_ID") == null ? "&nbsp;" : rs.getString("COMPANY_ID"));
        DataH.put("PR_NUMBER", rs.getString("PR_NUMBER") == null ? "&nbsp;" : rs.getString("PR_NUMBER"));
        DataH.put("LINE_ITEM", rs.getString("LINE_ITEM") == null ? "&nbsp;" : rs.getString("LINE_ITEM"));
        DataH.put("SOURCE_HUB", rs.getString("SOURCE_HUB") == null ? "&nbsp;" : rs.getString("SOURCE_HUB"));
        DataH.put("RECEIPT_ID", rs.getString("RECEIPT_ID") == null ? "&nbsp;" : rs.getString("RECEIPT_ID"));
        DataH.put("ITEM_ID", rs.getString("ITEM_ID") == null ? "&nbsp;" : rs.getString("ITEM_ID"));
        DataH.put("FACILITY_ID", rs.getString("FACILITY_ID") == null ? "&nbsp;" : rs.getString("FACILITY_ID"));
        DataH.put("CAT_PART_NO", rs.getString("CAT_PART_NO") == null ? "&nbsp;" : rs.getString("CAT_PART_NO"));
        DataH.put("TOTAL_SHIPPED", rs.getString("TOTAL_SHIPPED") == null ? "&nbsp;" : rs.getString("TOTAL_SHIPPED"));
        DataH.put("QUANTITY", rs.getString("QUANTITY") == null ? "&nbsp;" : rs.getString("QUANTITY"));
        DataH.put("QUANTITY_RETURNED", rs.getString("QUANTITY_RETURNED") == null ? "" : rs.getString("QUANTITY_RETURNED"));
        DataH.put("LINE_STATUS", "");
        DataH.put("USERCHK", "");
        DataH.put("DOUPDATE", "");
        DataH.put("MFG_LOT", rs.getString("MFG_LOT") == null ? "&nbsp;" : rs.getString("MFG_LOT"));
        DataH.put("BIN", "");
        DataH.put("DOR", rs.getString("SYS_DATE") == null ? "&nbsp;" : rs.getString("SYS_DATE"));

        Data.addElement(DataH);
      }

    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      dbrs.close();
    }

    Hashtable recsum = new Hashtable();
    recsum.put("TOTAL_NUMBER", new Integer(count));
    Data.setElementAt(recsum, 0);

    return Data;
  }

  //Build HTML Header
  private void buildHeader(Hashtable hub_list_set, String sitem, String spart, String receiptid, String mrnum, String sortby, String selHubnum,
                           String facility, String compArrayJs, HttpSession session, String useAction) {
    out.println(radian.web.HTMLHelpObj.printHTMLHeader("Customer Returns", "customerreturns.js",intcmIsApplication));
    out.println("</HEAD>  \n");

    if ("Update".equalsIgnoreCase(useAction)) {
      out.println("<BODY onLoad=\"doUnConfPopup()\">\n");
    } else {
      out.println("<BODY>\n");
    }

    out.println("<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n");
    out.println("<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>Please wait while the page is updated...</B></FONT><center>\n");
    out.println("</DIV>\n");
    out.println("<DIV ID=\"MAINPAGE\" STYLE=\"\">\n");
    out.println(radian.web.HTMLHelpObj.PrintTitleBar("Customer Returns\n"));
    Hashtable hub_list = (Hashtable) (hub_list_set.get("HUB_LIST"));
    if (hub_list.size() < 1) {
      out.println(radian.web.HTMLHelpObj.printMessageinTable("<BR><BR>You don't have access to this page.<BR><BR>Contact tech support for more information."));
      return;
    }

    out.println("<SCRIPT LANGUAGE=\"JavaScript1.2\" TYPE=\"text/javascript\">\n <!--\n");
    out.println(compArrayJs);
    out.println("// -->\n </SCRIPT>\n");

    Hashtable hub_prority_list = (Hashtable) (hub_list_set.get("HUB_PRORITY_LIST"));
    if (selHubnum.trim().length() == 0) {
      selHubnum = radian.web.HTMLHelpObj.intselectedhub(hub_prority_list.entrySet());
    }

    String hubname = radian.web.HTMLHelpObj.gethubNamefromBP(hub_list_set, selHubnum);
    PersonnelBean personnelBean = session.getAttribute("personnelBean") == null ? null : (PersonnelBean) session.getAttribute("personnelBean");
    if (personnelBean.getPermissionBean().hasFacilityPermission("CusReturns", hubname,null)) {
      this.setupdateStatus(true);
    } else {
      this.setupdateStatus(false);
    }
    //System.out.println("User Group Id: Inventory  Facility Id: "+hubname+"  "+personnelBean.getPermissionBean().hasFacilityPermission("Inventory",hubname,null)+"");

    out.println("<FORM method=\"POST\" NAME=\"receiving\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\"" + bundle.getString("CUSTOMER_RETURNS") + "\">\n");
    out.println("<TABLE BORDER=0 WIDTH=100% CELLSPACING=\"0\" CELLPADDING=\"3\" CLASS=\"moveup\">\n");
    out.println("<TR>\n");

    //Hub List
    out.println("<TD WIDTH=\"8%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
    out.println("<B>Hub:</B>&nbsp;\n");
    out.println("</TD>\n");
    out.println("<TD WIDTH=\"20%\">\n");
    out.println("<SELECT CLASS=\"HEADER\" NAME=\"HubName\" ONCHANGE=\"reshow(document.receiving.HubName)\" size=\"1\">\n");
    out.println("<OPTION VALUE=\"All\">Select A Hub</OPTION>\n");
    //Hashtable hub_list= ( Hashtable ) ( hub_list_set.get( "HUB_LIST" ) );
    //Hashtable hub_prority_list = (Hashtable)(hub_list_set.get("HUB_PRORITY_LIST"));
    out.println(radian.web.HTMLHelpObj.sorthashbyValue(hub_prority_list.entrySet(), selHubnum, hub_list));
    out.println("</SELECT>\n");
    out.println("</TD>\n");

    // search item
    out.println("<TD WIDTH=\"8%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
    out.println("<B>Item:</B>&nbsp;\n");
    out.println("</TD>\n");
    out.println("<TD WIDTH=\"20%\" ALIGN=\"left\" CLASS=\"announce\">\n");
    out.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"item\" value=\"" + sitem + "\" size=\"19\">\n");
    out.println("</TD>\n");

    //Search
    out.println("<TD WIDTH=\"10%\" VALIGN=\"MIDDLE\">\n");
    //out.println( " <INPUT TYPE=\"submit\" VALUE=\"Search\" onclick= \"return search(this)\" NAME=\"SearchButton\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n" );
    out.println("  <INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Search\" onclick= \"return search(this)\" NAME=\"SearchButton\">&nbsp;\n");

    out.println("</TD>\n");

    //Receive
    out.println("<TD WIDTH=\"10%\" VALIGN=\"MIDDLE\">\n");
    if (this.getupdateStatus()) {
      out.println("<INPUT TYPE=\"submit\" VALUE=\"Receive\" onclick= \"return update(this)\" NAME=\"UpdateButton\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n");
    } else {
      out.println("&nbsp;");
    }
    out.println("</TD>\n");
    out.println("</TR>\n");

    out.println("<TR>\n");

    //Facility List
    out.println("<TD WIDTH=\"8%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
    out.println("<B>Facility:</B>&nbsp;\n");
    out.println("</TD>\n");
    out.println("<TD WIDTH=\"20%\">\n");
    out.println("<SELECT CLASS=\"HEADER\" NAME=\"FacName\" size=\"1\">\n");
    Map warehouseInfo = (Map) session.getAttribute("warehouseInfo");
    Map facilityInfo = (Map) warehouseInfo.get(selHubnum);
    if ("All".equalsIgnoreCase(selHubnum) || selHubnum.trim().length() == 0 || facilityInfo == null) {
      out.println("<OPTION VALUE=\"\">Choose a Hub to get Facilities</OPTION>\n");
    } else {
      out.println("<OPTION VALUE=\"\">All</OPTION>\n");
      out.println(radian.web.HTMLHelpObj.sorthashbyValue(facilityInfo.entrySet(), facility));
    }
    out.println("</SELECT>\n");
    out.println("</TD>\n");

    // search part
    out.println("<TD WIDTH=\"8%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
    out.println("<B>Part:</B>&nbsp;\n");
    out.println("</TD>\n");
    out.println("<TD WIDTH=\"20%\" ALIGN=\"left\" CLASS=\"announce\">\n");
    out.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"part\" value=\"" + spart + "\" size=\"19\">\n");
    out.println("</TD>\n");

    //New to TCM
    out.println("<TD WIDTH=\"10%\" VALIGN=\"MIDDLE\">\n");
    //out.println("   <INPUT TYPE=\"submit\" VALUE=\"Search\" NAME=\"SearchButton\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">\n");
    out.println("&nbsp;</TD>\n");

    //Blank
    out.println("<TD WIDTH=\"10%\" VALIGN=\"MIDDLE\">\n");
    out.println("<INPUT TYPE=\"BUTTON\" ID=\"genbutton1\" VALUE=\"Reprint\" NAME=\"SearchButton\" onclick= \"javascript:doUnConfPopup()\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n");
    out.println("</TD>\n");

    out.println("</TR>\n");
    out.println("<TR>\n");
    // search receipt
    out.println("<TD WIDTH=\"8%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
    out.println("<B>Receipt ID:</B>&nbsp;\n");
    out.println("</TD>\n");
    out.println("<TD WIDTH=\"20%\" ALIGN=\"left\" CLASS=\"announce\">\n");
    out.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"receiptid\" value=\"" + receiptid + "\" size=\"19\">\n");
    out.println("</TD>\n");

    // search mrnum
    out.println("<TD WIDTH=\"8%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
    out.println("<B>MR:</B>&nbsp;\n");
    out.println("</TD>\n");
    out.println("<TD WIDTH=\"20%\" ALIGN=\"left\" CLASS=\"announce\">\n");
    out.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"mrnum\" value=\"" + mrnum + "\" size=\"19\">\n");
    out.println("</TD>\n");

    // Sort
    out.println("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
    out.println("<B>Sort By:</B>&nbsp;\n");
    out.println("</TD>\n");
    out.println("<TD WIDTH=\"25%\" ALIGN=\"LEFT\">\n");
    out.println("<SELECT NAME=\"SortBy\" CLASS=\"HEADER\" size=\"1\">\n");
    if (sortby.equalsIgnoreCase("FACILITY_ID")) {
      out.println("<option value=\"RECEIPT_ID\">Receipt Id</option>\n");
      out.println("<option value=\"item_id\">Item</option>\n");
      out.println("<option selected value=\"FACILITY_ID\">Facility</option>\n");
      out.println("<option value=\"pr_number\">MR</option>\n");
      out.println("<option value=\"CAT_PART_NO\">Part</option>\n");
    } else if (sortby.equalsIgnoreCase("pr_number")) {
      out.println("<option value=\"RECEIPT_ID\">Receipt Id</option>\n");
      out.println("<option value=\"item_id\">Item</option>\n");
      out.println("<option value=\"FACILITY_ID\">Facility</option>\n");
      out.println("<option selected value=\"pr_number\">MR</option>\n");
      out.println("<option value=\"CAT_PART_NO\">Part</option>\n");
    } else if (sortby.equalsIgnoreCase("CAT_PART_NO")) {
      out.println("<option value=\"RECEIPT_ID\">Receipt Id</option>\n");
      out.println("<option value=\"item_id\">Item</option>\n");
      out.println("<option value=\"FACILITY_ID\">Facility</option>\n");
      out.println("<option value=\"pr_number\">MR</option>\n");
      out.println("<option selected value=\"CAT_PART_NO\">Part</option>\n");
    } else if (sortby.equalsIgnoreCase("item_id")) {
      out.println("<option value=\"RECEIPT_ID\">Receipt Id</option>\n");
      out.println("<option selected value=\"item_id\">Item</option>\n");
      out.println("<option value=\"FACILITY_ID\">Facility</option>\n");
      out.println("<option value=\"pr_number\">MR</option>\n");
      out.println("<option value=\"CAT_PART_NO\">Part</option>\n");
    } else {
      out.println("<option selected value=\"RECEIPT_ID\">Receipt Id</option>\n");
      out.println("<option value=\"item_id\">Item</option>\n");
      out.println("<option value=\"FACILITY_ID\">Facility</option>\n");
      out.println("<option value=\"pr_number\">MR</option>\n");
      out.println("<option value=\"CAT_PART_NO\">Part</option>\n");
    }

    out.println("</SELECT>\n");
    out.println("</TD>\n");
    out.println("</TR>\n");
    out.println("</TABLE>\n");

    out.println("<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\">\n");
    out.println("<tr><td>");
    out.println("<INPUT TYPE=\"hidden\" NAME=\"Action\" VALUE=\"Search\">\n");
    out.println("<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\"NEW\">\n");
    out.println("<INPUT TYPE=\"hidden\" NAME=\"SubUserAction\" VALUE=\" \">\n");
    out.println("<INPUT TYPE=\"hidden\" NAME=\"DuplLineNumber\" VALUE=\" \">\n");
    out.println("<INPUT TYPE=\"hidden\" NAME=\"AddBin_Item_Id\" VALUE=\" \">\n");
    out.println("<INPUT TYPE=\"hidden\" NAME=\"AddBin_Bin_Id\" VALUE=\" \">\n");
    out.println("<INPUT TYPE=\"hidden\" NAME=\"AddBin_Line_No\" VALUE=\" \">\n");

    out.println("</TD></tr>");
    out.println("</TABLE>\n");

    return;
  }

  private void buildDetails(Vector data, String SubuserAction, Hashtable all_bin_set, String Hub, String Add_Item_id) {
    StringBuffer Msg = new StringBuffer();
    String checkednon = "";

    try {
      Hashtable summary = new Hashtable();
      summary = (Hashtable) data.elementAt(0);
      int total = ( (Integer) (summary.get("TOTAL_NUMBER"))).intValue();
      int AddBinLineNum1 = getAddBinLineNum();

      //start table #3
      out.println("<TABLE BORDER=\"0\" BGCOLOR=\"#000000\" CELLSPACING=\"1\" CELLPADDING=\"2\" WIDTH=\"100%\" CLASS=\"moveup\">\n");

      out.println("<tr align=\"center\">\n");
      if (this.getupdateStatus()) {
        out.println("<TH width=\"2%\"  ROWSPAN=\"2\" height=\"15\">OK</TH>\n");
      }
      out.println("<TH width=\"5%\"  ROWSPAN=\"2\" height=\"15\">Company</TH>\n");
      out.println("<TH width=\"5%\"  ROWSPAN=\"2\" height=\"15\">Facility</TH>\n");
      out.println("<TH width=\"5%\"  ROWSPAN=\"2\" height=\"15\">MR-Line</TH>\n");
      out.println("<TH width=\"5%\"  ROWSPAN=\"2\" height=\"15\">Item</TH>\n");
      out.println("<TH width=\"5%\"  ROWSPAN=\"2\" height=\"15\">Part</TH>\n");
      out.println("<TH width=\"5%\"  ROWSPAN=\"2\" height=\"15\">Receipt ID</TH>\n");
      out.println("<TH width=\"5%\"  ROWSPAN=\"2\" height=\"15\">Mfg Lot</TH>\n");
      out.println("<TH width=\"5%\"  ROWSPAN=\"2\" height=\"15\">Qty</TH>\n");
      out.println("<TH width=\"4%\"  height=\"15\" colspan=\"4\">Return Info</TH></TR>\n");
      out.println("<TH width=\"8%\"  height=\"15\" colspan=\"2\">Bin</TH>\n");
      out.println("<TH width=\"5%\"  height=\"15\">DOR mm/dd/yyyy</TH>\n");
      out.println("<TH width=\"5%\"  height=\"15\">Qty Returned</TH>\n");
      out.println("</TR>\n");

      int i = 0; //used for detail lines
      int lineadded = 0;

      for (int loop = 0; loop < total; loop++) {
        i++;
        boolean createHdr = false;

        if ( (lineadded % 10 == 0) && (lineadded != 0)) {
          createHdr = true;
        }

        if (createHdr) {
          out.println("<tr align=\"center\">\n");
          if (this.getupdateStatus()) {
            out.println("<TH width=\"2%\"  ROWSPAN=\"2\" height=\"15\">OK</TH>\n");
          }
          out.println("<TH width=\"5%\"  ROWSPAN=\"2\" height=\"15\">Company</TH>\n");
          out.println("<TH width=\"5%\"  ROWSPAN=\"2\" height=\"15\">Facility</TH>\n");
          out.println("<TH width=\"5%\"  ROWSPAN=\"2\" height=\"15\">MR-Line</TH>\n");
          out.println("<TH width=\"5%\"  ROWSPAN=\"2\" height=\"15\">Item</TH>\n");
          out.println("<TH width=\"5%\"  ROWSPAN=\"2\" height=\"15\">Part</TH>\n");
          out.println("<TH width=\"5%\"  ROWSPAN=\"2\" height=\"15\">Receipt ID</TH>\n");
          out.println("<TH width=\"5%\"  ROWSPAN=\"2\" height=\"15\">Mfg Lot</TH>\n");
          out.println("<TH width=\"5%\"  ROWSPAN=\"2\" height=\"15\">Qty</TH>\n");
          out.println("<TH width=\"4%\"  height=\"15\" colspan=\"4\">Return Info</TH></TR>\n");
          out.println("<TH width=\"8%\"  height=\"15\" colspan=\"2\">Bin</TH>\n");
          out.println("<TH width=\"5%\"  height=\"15\">DOR mm/dd/yyyy</TH>\n");
          out.println("<TH width=\"5%\"  height=\"15\">Qty Returned</TH>\n");
          out.println("</TR>\n");

          createHdr = false;
        }

        Hashtable hD = new Hashtable();
        hD = (Hashtable) data.elementAt(i);

        // get main information
        String Itemid = hD.get("ITEM_ID").toString();
        String Qty = hD.get("QUANTITY").toString();
        String facility = hD.get("FACILITY_ID").toString();
        String prnumber = hD.get("PR_NUMBER").toString();
        String lineitem = hD.get("LINE_ITEM").toString();
        String part = hD.get("CAT_PART_NO").toString();
        String companyid = hD.get("COMPANY_ID").toString();
        String Qtyrtd = hD.get("QUANTITY_RETURNED").toString();
        String receipt = hD.get("RECEIPT_ID").toString();
        String Sel_bin = (hD.get("BIN") == null ? "&nbsp;" : hD.get("BIN").toString());
        String Date_recieved = (hD.get("DOR") == null ? "" : hD.get("DOR").toString());
        String mfglot = hD.get("MFG_LOT").toString();
        String Line_Check = (hD.get("USERCHK") == null ? "&nbsp;" : hD.get("USERCHK").toString());
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

        String chkbox_value = "no";
        if (checkednon.equalsIgnoreCase("checked")) {
          chkbox_value = "yes";
        }

        if (SubuserAction.equalsIgnoreCase("Update") && "NO".equalsIgnoreCase(LINE_STATUS)) {
          Color = "CLASS=\"error";
        }

        if ( (SubuserAction.equalsIgnoreCase("Update") && "YES".equalsIgnoreCase(LINE_STATUS)) || ("YES".equalsIgnoreCase(LINE_STATUS))) {
          out.println("<INPUT TYPE=\"hidden\" value=\"no\" NAME=\"row_chk" + i + "\">\n");
          out.println("<input type=\"hidden\" name=\"qtyreturned" + i + "\" value=\"" + Qtyrtd + "\">\n");
        } else {
          lineadded++;
          out.println("<tr align=\"center\">\n");

          if (this.getupdateStatus()) {
            out.println("<td " + Color + "\" width=\"2%\"><INPUT TYPE=\"checkbox\" value=\"" + (chkbox_value) + "\" NAME=\"row_chk" + i + "\" onClick=\"checkreturnvalue(name,this,'" + i + "')\" " + checkednon + "></td>\n");
          }
          out.println("<td " + Color + "\" width=\"5%\">" + companyid + "</td>\n");
          out.println("<td " + Color + "\" width=\"5%\">" + facility + "</td>\n");
          out.println("<td " + Color + "\" width=\"5%\">" + prnumber + " - " + lineitem + "</td>\n");
          out.println("<td " + Color + "\" width=\"5%\">" + Itemid + "</td>\n");
          out.println("<td " + Color + "\" width=\"5%\">" + part + "</td>\n");
          out.println("<td " + Color + "\" width=\"5%\">" + receipt + "</td>\n");
          out.println("<td " + Color + "\" width=\"5%\">" + mfglot + "</td>\n");
          out.println("<td " + Color + "\" width=\"5%\">" + Qty + "</td>\n");
          out.println("<INPUT TYPE=\"hidden\" NAME=\"qtyonmr" + i + "\" VALUE=\"" + Qty + "\">\n");
          out.println("<td " + Color + "l\" width=\"8%\" COLSPAN=\"2\">\n");
          out.println("<INPUT TYPE=\"BUTTON\" CLASS=\"SUBMITDET\" onmouseover=\"className='SUBMITHOVERDET'\" onMouseout=\"className='SUBMITDET'\" name=\"addBin" + i + "\" value=\"+\" OnClick=\"showEmtyBins(" + Itemid + "," + i + "," + Hub + ")\">\n");
          out.println("<select name=\"selectElementBin" + i + "\">\n");

          Hashtable in_bin_set = (Hashtable) all_bin_set.get(Itemid);
          int bin_size = in_bin_set.size();
          //Select the last bin that was added for an item
          String bin_selected = "";
          if (Add_Item_id.equalsIgnoreCase("NONE")) {
            for (int j = 0; j < bin_size; j++) {
              Integer key = new Integer(j);
              String bin_name = (String) in_bin_set.get(key);
              bin_selected = "";
              if (bin_name.equalsIgnoreCase(Sel_bin)) {
                bin_selected = "selected";
              }
              out.println("<option " + bin_selected + " value=\"" + bin_name + "\">" + bin_name + "</option>\n");
            }
          } else {
            for (int m = 0; m < bin_size; m++) {
              Integer key = new Integer(m);
              String bin_name = (String) in_bin_set.get(key);
              bin_selected = "";

              if (i == AddBinLineNum1) {
                if (m == (bin_size - 1)) {
                  bin_selected = "selected";
                }
              } else {
                if (bin_name.equalsIgnoreCase(Sel_bin)) {
                  bin_selected = "selected";
                }
              }

              out.println("<option " + bin_selected + " value=\"" + bin_name + "\">" + bin_name + "</option>\n");
            }
          }

          out.println("</select></td>\n");
          out.println("<td " + Color + "\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"date_recieved" + i + "\" value=\"" + Date_recieved + "\" maxlength=\"10\" size=\"11\"></td>\n");
          out.println("<td " + Color + "\" width=\"5%\"><input type=\"text\" CLASS=\"DETAILS\" name=\"qtyreturned" + i + "\" value=\"" + Qtyrtd + "\" maxlength=\"10\" size=\"5\"></td>\n");
          out.println("</tr>\n");
        }
      }

      out.println("</TABLE>\n");
      out.println("<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\" CLASS=\"moveup\">\n");
      out.println("<tr>");
      out.println("<TD HEIGHT=\"25\" WIDTH=\"100%\" VALIGN=\"MIDDLE\" BGCOLOR=\"#000066\">&nbsp;\n");
      out.println("</TD></tr>");
      out.println("</TABLE>\n");
      out.println("</form>\n");
      out.println("</body></html>\n");
    } catch (Exception e) {
      e.printStackTrace();
      out.println(radian.web.HTMLHelpObj.printError("Coustomer Returns",intcmIsApplication));
    }

    return;
  }

  private Hashtable addToItemBinSet(String item_id, String bin_id, Hashtable in_set) {
    Hashtable bin_set = new Hashtable();
    try {
      bin_set = (Hashtable) in_set.get(item_id);
      int size = bin_set.size();
      if ( (size == 1) && bin_set.containsValue("NONE")) {
        bin_set.remove(new Integer(0));
        bin_set.put(new Integer(0), bin_id);
      } else {
        bin_set.put(new Integer(size), bin_id);
      }
      in_set.put(item_id, bin_set);
    } catch (Exception e) {
      e.printStackTrace();
      out.println(radian.web.HTMLHelpObj.printError("tcmIS Hub Receiving",intcmIsApplication));
    }

    return in_set;
  }

  private Hashtable createItemBinSet(Vector data, String branch_plant) {
    String item_id;
    Hashtable bin_set = new Hashtable();
    Hashtable sum = (Hashtable) (data.elementAt(0));
    int count = ( (Integer) sum.get("TOTAL_NUMBER")).intValue();

    try {
      for (int i = 1; i < count + 1; i++) {
        Hashtable hD = new Hashtable();
        hD = (Hashtable) (data.elementAt(i));
        item_id = (String) hD.get("ITEM_ID");
        if (false == bin_set.containsKey(item_id)) {
          Hashtable bin_for_item = radian.web.HTMLHelpObj.CreateBinForItem(db, item_id, branch_plant);
          bin_set.put(item_id, bin_for_item);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      out.println(radian.web.HTMLHelpObj.printError("tcmIS Hub Receiving",intcmIsApplication));
    }
    return bin_set;
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

        String qtyreturned = "";
        qtyreturned = BothHelpObjs.makeBlankFromNull(request.getParameter("qtyreturned" + i));

        if (! (qtyreturned.length() == 0)) {
          hD.remove("QUANTITY_RETURNED");
          hD.put("QUANTITY_RETURNED", qtyreturned);

          hD.remove("DOUPDATE");
          hD.put("DOUPDATE", "Yes");
        }

        String date_recieved = "";
        date_recieved = BothHelpObjs.makeBlankFromNull(request.getParameter("date_recieved" + i));
        hD.remove("DOR");
        hD.put("DOR", date_recieved);

        String bin_name = "";
        bin_name = BothHelpObjs.makeBlankFromNull(request.getParameter("selectElementBin" + i));
        hD.remove("BIN");
        hD.put("BIN", bin_name);

        new_data.addElement(hD);
      }
    } catch (Exception e) {
      e.printStackTrace();
      out.println(radian.web.HTMLHelpObj.printError("Customer Returns",intcmIsApplication));
    }
    return new_data;
  }

  public boolean Updatematerial(Hashtable hD, String personnelID) {
    boolean result = false;

    String prnumber = hD.get("PR_NUMBER").toString().trim();
    String lineitem = hD.get("LINE_ITEM").toString().trim();
    String receiptid = hD.get("RECEIPT_ID").toString().trim();
    String qtyreturned = hD.get("QUANTITY_RETURNED").toString().trim();
    String company = hD.get("COMPANY_ID").toString().trim();
    String Sel_bin = (hD.get("BIN") == null ? "&nbsp;" : hD.get("BIN").toString());
    String Date_recieved = (hD.get("DOR") == null ? "" : hD.get("DOR").toString());

    try {
      Connection connect1 = null;
      CallableStatement cs = null;
      connect1 = db.getConnection();
      //System.out.println("Doing P_customer_owned_RECEIPT");
      cs = connect1.prepareCall("{call P_customer_owned_RECEIPT(?,?,?,?,?,?,?,?,?,?)}");
      cs.setString(1, company);
      cs.setInt(2, Integer.parseInt(receiptid));
      cs.setBigDecimal( 3,new BigDecimal(""+ prnumber +"") );
      cs.setString(4, lineitem);
      cs.setInt(5, Integer.parseInt(qtyreturned));
      cs.setString(6, Sel_bin);
      cs.setTimestamp(7, radian.web.HTMLHelpObj.getDateFromString(Date_recieved));

      cs.setString(8, personnelID);

      cs.registerOutParameter(9, java.sql.Types.NUMERIC);
      cs.registerOutParameter(10, java.sql.Types.VARCHAR);

      cs.execute();

      int cusreceiptid = cs.getInt(9);
      String errorcode = cs.getString(10);

      //System.out.println("Result from Customer Return  ID" + cusreceiptid + " Error Code  " + errorcode);

      if (errorcode == null) {
        result = true;
      } else {
        result = false;
      }
    } catch (Exception e) {
      e.printStackTrace();
      radian.web.emailHelpObj.senddatabaseHelpemails("Error Calling P_customer_owned_RECEIPT in Customer Return Page ",
          "Error Calling P_customer_owned_RECEIPT\nError Msg:\n" + e.getMessage() + "\n company  " + company + "  receiptid  " + receiptid + "  prnumber  " + prnumber + " lineitem " + lineitem + " \n  qtyreturned  " + qtyreturned + "  Bin  " + Sel_bin + " \nUser : " + personnelID + "");
      result = false;
    }

    return result;
  }

  private Hashtable UpdateDetails(Vector data, String personnelid) {
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
        String Line_Check = "";
        Line_Check = (hD.get("USERCHK") == null ? "&nbsp;" : hD.get("USERCHK").toString());

        String dostatusupdate = "";
        dostatusupdate = (hD.get("DOUPDATE") == null ? " " : hD.get("DOUPDATE").toString());

        if ("yes".equalsIgnoreCase(Line_Check) && "yes".equalsIgnoreCase(dostatusupdate)) {
          noneToUpd = false;
          boolean line_result = false;
          line_result = this.Updatematerial(hD, personnelid); // update database

          if (false == line_result) {
            completeSuccess = false;

            hD.remove("USERCHK");
            hD.put("USERCHK", " ");

            hD.remove("LINE_STATUS");
            hD.put("LINE_STATUS", "NO");
            errordata.addElement(hD);
          }

          if (true == line_result) {
            one_success = true;
            hD.remove("LINE_STATUS");
            hD.put("LINE_STATUS", "YES");

            errordata.addElement(hD);
          }
        } else {
          errordata.addElement(hD);
        }
      } //end of for
      if (true == one_success) {
        result = true;
      }
    } catch (Exception e) {
      result = false;
      e.printStackTrace();
    }
    updateresult.put("RESULT", new Boolean(result));
    updateresult.put("ERRORDATA", errordata);
    return updateresult;
  }
}
