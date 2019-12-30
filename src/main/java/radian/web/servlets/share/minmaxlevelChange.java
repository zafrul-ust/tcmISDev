package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.HubInventoryGroupProcess;
import com.tcmis.common.util.ResourceLibrary;

import radian.tcmis.both1.helpers.BothHelpObjs;
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
 * 10-13-04 - Giving the option to change stuff to exitMM from OOR
 * 02-24-05 - Allowing Buyers to update LookAhead days
 * 08-17-05 - Ability to change operating method for item counting inventory groups
 * 11-29-05 - Showing a Message for item counting hubs, to use item management page to change levels.
 */

public class minmaxlevelChange {
  private ServerResourceBundle bundle = null;
  private TcmISDBModel db = null;
  protected boolean allowupdate = false;
  protected boolean allowOperatingMethodUpdate = false;
  private boolean nonetoupdate = true;
  private String errormesages = "";
  private static org.apache.log4j.Logger minmaxlog = org.apache.log4j.Logger.getLogger(minmaxlevelChange.class);
  private boolean intcmIsApplication = false;
  private ResourceLibrary res = null; // res means resource.

  public void setupdateStatus(boolean id) {
    this.allowupdate = id;
  }

  private boolean getupdateStatus() throws Exception {
    return this.allowupdate;
  }

  public void setOperatingMethodPermission(boolean id) {
    this.allowOperatingMethodUpdate = id;
  }

  private boolean getOperatingMethodPermission() {
    return this.allowOperatingMethodUpdate;
  }

  public minmaxlevelChange(ServerResourceBundle b, TcmISDBModel d) {
    bundle = b;
    db = d;
  }

  public void doResult(HttpServletRequest request,
                       HttpServletResponse response,PrintWriter out) throws ServletException, IOException {
    //PrintWriter out = response.getWriter();
    //response.setContentType( "text/html; charset=UTF-8" );
    HttpSession minmaxsession = request.getSession();

    res =  new ResourceLibrary("com.tcmis.common.resources.CommonResources", (Locale)minmaxsession.getAttribute("tcmISLocale"));
    PersonnelBean personnelBean = (PersonnelBean) minmaxsession.getAttribute("personnelBean");
    Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
    if (menuItemOvBeanCollection != null && menuItemOvBeanCollection.size() > 0) {
      intcmIsApplication = true;
    }

    String personnelid = BothHelpObjs.makeBlankFromNull( (String) minmaxsession.getAttribute("PERSONNELID"));
    boolean purchaser = false;
    if (radian.web.HTMLHelpObj.hasaccessto(minmaxsession, "Purchasing")) {
      purchaser = true;
    }
    Collection hubInventoryGroupOvBeanCollection = new Vector( (Collection) minmaxsession.getAttribute("hubInventoryGroupOvBeanCollection"));

    String User_Action = "";
    String facility = "";
    String workarea = "";

    User_Action = request.getParameter("UserAction");
    if (User_Action == null) {
      User_Action = "New";
    }
    User_Action = User_Action.trim();

    String invengrp = request.getParameter("invengrp");
    if (invengrp == null) {
      invengrp = "";
    }
    invengrp = invengrp.trim();

    String selHub = request.getParameter("HubName");
    if (selHub == null) {
      selHub = "";
    }
    selHub = selHub.trim();

    String searchtext = request.getParameter("searchtext");
    if (searchtext == null) {
      searchtext = "";
    }
    searchtext = searchtext.trim();

    String searchlike = request.getParameter("searchlike");
    if (searchlike == null) {
      searchlike = "";
    }
    searchlike = searchlike.trim();

    String changeOperatingMethod = request.getParameter("changeOperatingMethod");
    if (changeOperatingMethod == null) {
      changeOperatingMethod = "";
    }
    changeOperatingMethod = changeOperatingMethod.trim();

    /*String operatingMethod =request.getParameter( "operatingMethod" );
      if ( operatingMethod == null )
      operatingMethod="";
      operatingMethod = operatingMethod.trim();
      String origOperatingMethod =request.getParameter( "origOperatingMethod" );
      if ( origOperatingMethod == null )
      origOperatingMethod="";
      origOperatingMethod = origOperatingMethod.trim();*/

    /*String countUom =request.getParameter( "countUom" );
      if ( countUom == null )
      countUom="";
      countUom = countUom.trim();*/

    /*String itemId =request.getParameter( "itemId" );
      if ( itemId == null )
      itemId="";
      itemId = itemId.trim();*/

    String catPartNo = request.getParameter("catPartNo");
    if (catPartNo == null) {
      catPartNo = "";
    }
    catPartNo = catPartNo.trim();

    String partGroupNo = request.getParameter("partGroupNo");
    if (partGroupNo == null) {
      partGroupNo = "";
    }
    partGroupNo = partGroupNo.trim();

    String displaySearchOptions = request.getParameter("displaySearchOptions");
    if (displaySearchOptions == null) {
      displaySearchOptions = "";
    }
    displaySearchOptions = displaySearchOptions.trim();

    boolean noSearchOptions = false;
    if ("Yes".equalsIgnoreCase(changeOperatingMethod) || "No".equalsIgnoreCase(displaySearchOptions)) {
      noSearchOptions = true;
    }

    try {
      Hashtable hub_list_set = new Hashtable();
      hub_list_set = (Hashtable) minmaxsession.getAttribute("HUB_PREF_LIST");

      String initialMgmtDataLoaded = minmaxsession.getAttribute("ITEM_INVENTORY_DATA_LOADED") == null ?
          "" : minmaxsession.getAttribute("ITEM_INVENTORY_DATA_LOADED").toString();
      if (!initialMgmtDataLoaded.equalsIgnoreCase("Yes") && "Yes".equalsIgnoreCase(changeOperatingMethod)) {
        Hashtable hub_list1 = (Hashtable) (hub_list_set.get("HUB_LIST"));

        if (hub_list1.size() > 0) {
          Hashtable iniOperatingMethodData = null;
          Hashtable iniBillingMethodData = null;
          iniOperatingMethodData = radian.web.dropdwnHelpObj.getOperatingMethodData(db, hub_list_set);
          iniBillingMethodData = radian.web.dropdwnHelpObj.getBillingMethodData(db, hub_list_set);
          minmaxsession.setAttribute("INITIAL_OPERATING_METHOD_DATA", iniOperatingMethodData);
          minmaxsession.setAttribute("INITIAL_BILLING_METHOD_DATA", iniBillingMethodData);
        }
      }

      Hashtable iniOperatingMethodData = null;
      iniOperatingMethodData = (Hashtable) minmaxsession.getAttribute("INITIAL_OPERATING_METHOD_DATA");
      if (iniOperatingMethodData == null) {
        iniOperatingMethodData = new Hashtable();
      }

      Hashtable iniBillingMethodData = null;
      iniBillingMethodData = (Hashtable) minmaxsession.getAttribute("INITIAL_BILLING_METHOD_DATA");
      if (iniBillingMethodData == null) {
        iniBillingMethodData = new Hashtable();
      }

      if (selHub.trim().length() == 0) {
        selHub = radian.web.HTMLHelpObj.getFirstBranchPlant(hubInventoryGroupOvBeanCollection);
      }

      if (radian.web.HTMLHelpObj.isItemCountingHub(
          hubInventoryGroupOvBeanCollection, selHub) && !"Yes".equalsIgnoreCase(changeOperatingMethod)) {
        this.setupdateStatus(false);
        this.setOperatingMethodPermission(false);
      }

      String hubname = radian.web.HTMLHelpObj.gethubNamefromBP(hub_list_set, selHub);
      if (hubname.length() > 0) {
        //PersonnelBean personnelBean=minmaxsession.getAttribute( "personnelBean" ) == null ? null : ( PersonnelBean ) minmaxsession.getAttribute( "personnelBean" );

        if (personnelBean.getPermissionBean().hasInventoryGroupPermission("ItemMgmt", invengrp, hubname, null)) {
          this.setOperatingMethodPermission(true);
        } else {
          this.setOperatingMethodPermission(false);
        }
      }

      //Hashtable initialData = null;
      //Hashtable hub_list_set = new Hashtable();
      Vector lnventory_invengrps = new Vector();
      String initialDataLoaded = "";
      try {
        initialDataLoaded = minmaxsession.getAttribute("MIN_MAX_DATA_LOADED").toString();
      } catch (Exception getdoneFlag) {}

      if (initialDataLoaded.equalsIgnoreCase("Yes")) {
        //initialData= ( Hashtable ) minmaxsession.getAttribute( "INVENGRP_DATA" );
        //hub_list_set= ( Hashtable ) minmaxsession.getAttribute( "MIN_MAX_HUB_SET" );
        lnventory_invengrps = (Vector) minmaxsession.getAttribute("MIMMAX_ALLOWED_INVENGRP");
      } else {
        String CompanyID = minmaxsession.getAttribute("COMPANYID") == null ? "" : minmaxsession.getAttribute("COMPANYID").toString();
            /*hub_list_set=new Hashtable();
               hub_list_set=radian.web.HTMLHelpObj.createHubList( db,personnelid,CompanyID );*/
            //Hashtable hub_list= ( Hashtable ) ( hub_list_set.get( "HUB_LIST" ) );
            /*if ( hub_list.size() > 0 )
               {
              //hub_list_set=radian.web.HTMLHelpObj.createAllHubList( db,CompanyID );
              initialData=radian.web.poHelpObj.getinvgrpInitialData( db,hub_list_set );
               }*/

        lnventory_invengrps = radian.web.HTMLHelpObj.createvgrpmemlist(db, personnelid, "MinMaxChg");
        minmaxsession.setAttribute("MIMMAX_ALLOWED_INVENGRP", lnventory_invengrps);
        //minmaxsession.setAttribute( "MIN_MAX_HUB_SET",hub_list_set );
        //minmaxsession.setAttribute( "INVENGRP_DATA",initialData );
      }

      if (User_Action.equalsIgnoreCase("showlevels")) {
        /*if ("Yes".equalsIgnoreCase(changeOperatingMethod))
           {
          operatingMethod = getOperatingMethod(invengrp,itemId);
           }*/
        Vector data = new Vector();

        try {
          data = getResult(selHub, invengrp, searchtext, searchlike, hubInventoryGroupOvBeanCollection, partGroupNo);

        } catch (Exception e1) {
          e1.printStackTrace();
          out.println(radian.web.HTMLHelpObj.printHTMLError());
        }

        minmaxsession.setAttribute("CABLVLCHGMGMTDATA", data);

        Hashtable sum = (Hashtable) (data.elementAt(0));
        int count = ( (Integer) sum.get("TOTAL_NUMBER")).intValue();

        buildHeader(selHub, invengrp, searchtext, hub_list_set, hubInventoryGroupOvBeanCollection, "", searchlike, changeOperatingMethod, partGroupNo, noSearchOptions, out);

        if (0 == count) {
          out.println(radian.web.HTMLHelpObj.printHTMLNoData("No Items Found"));
        } else {
          buildDetails(data, lnventory_invengrps, out, purchaser, searchlike, iniOperatingMethodData, invengrp, changeOperatingMethod, searchtext, iniBillingMethodData,out);
          buildHistdetails(selHub, invengrp, searchtext, searchlike, out);
          /*if ("Yes".equalsIgnoreCase(changeOperatingMethod))
               {
           operatingMethodHistory(invengrp,searchtext,searchlike,out);
               }*/
          out.println("</body></html>\n");
        }

        //clean up
        data = null;
      } else if (User_Action.equalsIgnoreCase("Update")) {
        Vector retrn_data = (Vector) minmaxsession.getAttribute("CABLVLCHGMGMTDATA");
        Vector synch_data = SynchServerData(request, retrn_data);

        boolean updateResult = false;
        /*if (!origOperatingMethod.equalsIgnoreCase(operatingMethod))
           {
         if ("Issue On Receipt".equalsIgnoreCase(operatingMethod))
         {
           updateResult = UpdateDetails(synch_data,personnelid);
           if (updateResult)
           {
          updateResult=UpdateLine( invengrp,itemId,operatingMethod,countUom,personnelid );
           }
         }
         else
         {
           updateResult=UpdateLine( invengrp,itemId,operatingMethod,countUom,personnelid );
           if (updateResult)
           {
          updateResult = UpdateDetails(synch_data,personnelid);
           }
         }
           }
           else*/
        {
          updateResult = UpdateDetails(synch_data, personnelid);
        }

        if (updateResult) {
          buildHeader(selHub, invengrp, searchtext, hub_list_set, hubInventoryGroupOvBeanCollection, "All Your Selections Were Successfully Updated", searchlike,
                      changeOperatingMethod, partGroupNo, noSearchOptions, out);
        } else {
          /*if ("Yes".equalsIgnoreCase(changeOperatingMethod))
               {
              operatingMethod = getOperatingMethod(invengrp,itemId);
               }*/

          buildHeader(selHub, invengrp, searchtext, hub_list_set, hubInventoryGroupOvBeanCollection, "An Error Occurred.<BR>" + errormesages + "Please Check Data and try Again.",
                      searchlike, changeOperatingMethod, partGroupNo, noSearchOptions, out);
        }

        Vector data = new Vector();
        try {
          data = getResult(selHub, invengrp, searchtext, searchlike, hubInventoryGroupOvBeanCollection, partGroupNo);
        } catch (Exception e1) {
          e1.printStackTrace();
          out.println(radian.web.HTMLHelpObj.printHTMLError());
        }
        minmaxsession.setAttribute("CABLVLCHGMGMTDATA", data);

        Hashtable sum = (Hashtable) (data.elementAt(0));
        int count = ( (Integer) sum.get("TOTAL_NUMBER")).intValue();

        if (0 == count) {
          out.println(radian.web.HTMLHelpObj.printHTMLNoData("No Items Found"));
        } else {
          buildDetails(data, lnventory_invengrps, out, purchaser, searchlike, iniOperatingMethodData, invengrp, changeOperatingMethod, searchtext, iniBillingMethodData,out);
          buildHistdetails(selHub, invengrp, searchtext, searchlike, out);
          /*if ("Yes".equalsIgnoreCase(changeOperatingMethod))
               {
           operatingMethodHistory(invengrp,searchtext,searchlike,out);
               }*/
          out.println("</body></html>\n");
        }
      } else {
        buildHeader(selHub, invengrp, searchtext, hub_list_set, hubInventoryGroupOvBeanCollection, "", searchlike, changeOperatingMethod, partGroupNo, noSearchOptions, out);
        out.println(radian.web.HTMLHelpObj.printHTMLSelect());
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      out.close();
    }
  }

  private void buildDetails(Vector data, Vector allowedig, PrintWriter minmaxout,
                            boolean purchaser, String searchLike, Hashtable iniOperatingMethodData,
                            String selectedInventoryGroup, String changeOperatingMethod, String catPartNo, Hashtable iniBillingMethodData,PrintWriter out) {
    try {
      Hashtable summary = new Hashtable();
      summary = (Hashtable) data.elementAt(0);
      int total = ( (Integer) (summary.get("TOTAL_NUMBER"))).intValue();
      int vsize = data.size();
      Hashtable stockmethdtypes = new Hashtable();
      stockmethdtypes.put("MM", "MM");
      stockmethdtypes.put("OOR", "OOR");
      stockmethdtypes.put("exitMM", "exitMM");
      stockmethdtypes.put("virtualMM", "virtualMM");

      minmaxout.println("<INPUT TYPE=\"hidden\" NAME=\"total\" VALUE=\"" + total + "\">\n");
      //start table #3
      if (!"ITEM_ID".equalsIgnoreCase(searchLike) || "Yes".equalsIgnoreCase(changeOperatingMethod)) {
        minmaxout.println("<TABLE  BORDER=\"0\" BGCOLOR=\"#a2a2a2\" CELLSPACING=1 CELLPADDING=2 WIDTH=\"100%\" CLASS=\"moveup\">\n");
      }
      String error_item;

      int i = 0; //used for detail lines
      for (int loop = 0; loop < total; loop++) {
        i++;
        boolean createHdr = false;

        if (loop % 10 == 0) {
          createHdr = true;
        }

        Hashtable hD = new Hashtable();
        hD = (Hashtable) data.elementAt(i);

        if ("ITEM_ID".equalsIgnoreCase(searchLike) && loop == 0) {
          String item_desc = hD.get("ITEM_DESC") == null ? "&nbsp;" : hD.get("ITEM_DESC").toString();
          minmaxout.println("<TABLE WIDTH=\"100%\" BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" CLASS=\"moveup\">\n");
          minmaxout.println("<tr align=\"center\">\n");
          minmaxout.println("<td CLASS=\"HEADER\" WIDTH=\"10%\" ALIGN=\"RIGHT\" height=\"38\"><B>"+res.getString("label.itemdesc")+":</B></td>\n");
          minmaxout.println("<td CLASS=\"HEADER\" ALIGN=\"LEFT\" height=\"38\">" + item_desc + "</td>\n");
          minmaxout.println("</tr>\n");
          minmaxout.println("</TABLE><TABLE  BORDER=\"0\" BGCOLOR=\"#a2a2a2\" CELLSPACING=1 CELLPADDING=2 WIDTH=\"100%\" CLASS=\"moveup\">\n");
        } else if ("Yes".equalsIgnoreCase(changeOperatingMethod)) {
          String partDescription = hD.get("PART_DESCRIPTION") == null ? "&nbsp;" : hD.get("PART_DESCRIPTION").toString();
          minmaxout.println("<TABLE WIDTH=\"100%\" BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" CLASS=\"moveup\">\n");
          minmaxout.println("<tr align=\"center\">\n");
          minmaxout.println("<td CLASS=\"HEADER\" WIDTH=\"10%\" ALIGN=\"RIGHT\" height=\"38\"><B>"+res.getString("label.partdesc")+":</B></td>\n");
          minmaxout.println("<td CLASS=\"HEADER\" ALIGN=\"LEFT\" height=\"38\">" + partDescription + "</td>\n");
          minmaxout.println("</tr>\n");
          minmaxout.println("</TABLE><TABLE  BORDER=\"0\" BGCOLOR=\"#a2a2a2\" CELLSPACING=1 CELLPADDING=2 WIDTH=\"100%\" CLASS=\"moveup\">\n");
        }

        if (createHdr) {
          minmaxout.println("<tr align=\"center\">\n");
          minmaxout.println("<TH width=\"5%\"  height=\"38\">"+res.getString("label.inventorygroup")+"</TH>\n");
          minmaxout.println("<TH width=\"5%\"  height=\"38\">"+res.getString("label.company")+"</TH>\n");
          minmaxout.println("<TH width=\"5%\"  height=\"38\">"+res.getString("label.catalog")+"</TH>\n");
          minmaxout.println("<TH width=\"5%\"  height=\"38\">"+res.getString("label.partnumber")+"</TH>\n");
          minmaxout.println("<TH width=\"2%\"  height=\"38\">"+res.getString("label.partgroupnumber")+"</TH>\n");
          minmaxout.println("<TH width=\"2%\"  height=\"38\">"+res.getString("label.itemid")+"</TH>\n");
          minmaxout.println("<TH width=\"5%\"  height=\"38\">"+res.getString("label.currentstockingmethod")+"</TH>\n");
          minmaxout.println("<TH width=\"5%\"  height=\"38\">"+res.getString("label.stockingmethod")+"</TH>\n");
          minmaxout.println("<TH width=\"5%\"  height=\"38\">"+res.getString("label.reorderpoint")+"</TH>\n");
          minmaxout.println("<TH width=\"5%\"  height=\"38\">"+res.getString("label.stockinglevel")+"</TH>\n");
          minmaxout.println("<TH width=\"5%\"  height=\"38\">"+res.getString("label.reorderquantity")+"</TH>\n");
          minmaxout.println("<TH width=\"5%\"  height=\"38\">"+res.getString("label.lookaheaddays")+"</TH>\n");
          minmaxout.println("<TH width=\"5%\"  height=\"38\">"+res.getString("label.receiptprocessingmethod")+"</TH>\n");
          minmaxout.println("<TH width=\"5%\"  height=\"38\">"+res.getString("label.billingmethod")+"</TH>\n");
          minmaxout.println("<TH width=\"15%\"  height=\"38\">"+res.getString("label.remarks")+"</TH>\n");
          minmaxout.println("</tr>\n");
        }

        String company_id = hD.get("COMPANY_ID") == null ? "&nbsp;" : hD.get("COMPANY_ID").toString();
        String catalog_id = hD.get("CATALOG_ID") == null ? "&nbsp;" : hD.get("CATALOG_ID").toString();
        String cat_part_no = hD.get("CAT_PART_NO") == null ? "&nbsp;" : hD.get("CAT_PART_NO").toString();
        String part_group_no = hD.get("PART_GROUP_NO") == null ? "&nbsp;" : hD.get("PART_GROUP_NO").toString();
        String item_id = hD.get("ITEM_ID") == null ? "&nbsp;" : hD.get("ITEM_ID").toString();
        String stocking_method = hD.get("STOCKING_METHOD") == null ? "&nbsp;" : hD.get("STOCKING_METHOD").toString();
        String reorder_point = hD.get("REORDER_POINT") == null ? "&nbsp;" : hD.get("REORDER_POINT").toString();
        String stocking_level = hD.get("STOCKING_LEVEL") == null ? "&nbsp;" : hD.get("STOCKING_LEVEL").toString();
        String look_ahead_days = hD.get("LOOK_AHEAD_DAYS") == null ? "&nbsp;" : hD.get("LOOK_AHEAD_DAYS").toString();
        String established_stock_flag = hD.get("ESTABLISHED_STOCK_FLAG") == null ? "&nbsp;" : hD.get("ESTABLISHED_STOCK_FLAG").toString();
        String inventoryGroup = hD.get("INVENTORY_GROUP") == null ? "&nbsp;" : hD.get("INVENTORY_GROUP").toString();
        String inventoryGroupName = hD.get("INVENTORY_GROUP_NAME") == null ? "&nbsp;" : hD.get("INVENTORY_GROUP_NAME").toString();
        String cur_stocking_method = hD.get("CURRENT_STOCKING_METHOD") == null ? "&nbsp;" : hD.get("CURRENT_STOCKING_METHOD").toString();
        String reorderQuantity = hD.get("REORDER_QUANTITY") == null ? "&nbsp;" : hD.get("REORDER_QUANTITY").toString();
        String operatingMethod = hD.get("RECEIPT_PROCESSING_METHOD") == null ? "&nbsp;" : hD.get("RECEIPT_PROCESSING_METHOD").toString();
        String billingMethod = hD.get("BILLING_METHOD") == null ? "&nbsp;" : hD.get("BILLING_METHOD").toString();
		  String catalog_company_id = hD.get("CATALOG_COMPANY_ID") == null ? "&nbsp;" : hD.get("CATALOG_COMPANY_ID").toString();

		  String Color = " ";
        if (i % 2 == 0) {
          Color = "CLASS=\"blue";
        } else {
          Color = "CLASS=\"white";
        }

        boolean updateallowed = false;
        if (this.getupdateStatus() && (allowedig.contains(inventoryGroup) || this.getOperatingMethodPermission())) {
          updateallowed = true;
        }

        minmaxout.println("<tr align=\"center\">\n");
        minmaxout.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + inventoryGroupName + "</td>\n");
        minmaxout.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + company_id + "</td>\n");
        minmaxout.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + catalog_id + "</td>\n");
        minmaxout.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + cat_part_no + "</td>\n");
        minmaxout.println("<INPUT TYPE=\"hidden\" NAME=\"catpartno" + i + "\" VALUE=\"" + cat_part_no + "\">\n");
        minmaxout.println("<td " + Color + "\" width=\"2%\" height=\"38\">" + part_group_no + "</td>\n");
        minmaxout.println("<td " + Color + "\" width=\"2%\" height=\"38\">" + item_id + "</td>\n");
        minmaxout.println("<td " + Color + "\" width=\"2%\" height=\"38\">" + cur_stocking_method + "</td>\n");

        if (updateallowed) {
          minmaxout.println("<td " + Color + "\" width=\"5%\" height=\"38\">\n");
          minmaxout.println("<SELECT CLASS=\"HEADER\" NAME=\"stockmethod" + i + "\" size=\"1\" onChange=\"checkoorvalues('" + i + "')\">\n");
          minmaxout.println(radian.web.HTMLHelpObj.getDropDown(stockmethdtypes, stocking_method));
          minmaxout.println("</SELECT>\n</TD>\n");

          minmaxout.println("<td " + Color + "\" width=\"5%\" height=\"38\"><input type=\"text\" CLASS=\"DETAILS\" name=\"reorderpt" + i + "\" value=\"" + reorder_point + "\" onChange=\"checkreorderpt('" + i + "')\" maxlength=\"10\" size=\"4\"></td>\n");
          minmaxout.println("<td " + Color + "\" width=\"5%\" height=\"38\"><input type=\"text\" CLASS=\"DETAILS\" name=\"stocklevel" + i + "\" value=\"" + stocking_level + "\" onChange=\"checkstocklevel('" + i + "')\" maxlength=\"10\" size=\"4\"></td>\n");
          minmaxout.println("<td " + Color + "\" width=\"5%\" height=\"38\"><input type=\"text\" CLASS=\"DETAILS\" name=\"reorderQuantity" + i + "\" value=\"" + reorderQuantity + "\" onChange=\"checkreorderQuantity('" + i + "')\" maxlength=\"10\" size=\"4\"></td>\n");
          minmaxout.println("<td " + Color + "\" width=\"5%\" height=\"38\"><input type=\"text\" CLASS=\"DETAILS\" name=\"lookahddays" + i + "\" value=\"" + look_ahead_days + "\" onChange=\"checkqtyvalue('lookahddays" + i + "')\" maxlength=\"10\" size=\"4\"></td>\n");
          minmaxout.println("<TD " + Color + "\" WIDTH=\"5%\" ALIGN=\"LEFT\">\n");
          Hashtable fhOp = (Hashtable) iniOperatingMethodData.get(selectedInventoryGroup);
          if (this.getOperatingMethodPermission() && fhOp != null) {
            minmaxout.println("<SELECT CLASS=\"HEADER\"  NAME=\"operatingMethod" + i + "\" size=\"1\" OnChange=\"checkOperatingMethod(" + i + ")\">\n");
            if (operatingMethod.length() == 0) {
              minmaxout.println("<option selected value=\"\"></option>\n");
            }

            //Hashtable fhOp= ( Hashtable ) iniOperatingMethodData.get( selectedInventoryGroup );
            if (operatingMethod.length() == 0) {
              minmaxout.println("<option value=\"\"></option>\n");
            }

            Vector oprValueV = (Vector) fhOp.get("OPERATIONAL_VALUE");
            Vector oprMethodV = (Vector) fhOp.get("OPERATIONAL_METHOD");
            //System.out.println( selinvengrp );
            for (int ii = 0; ii < oprValueV.size(); ii++) {
              String preSelect = "";
              String operatingValue = (String) oprValueV.elementAt(ii);
              String oprMethod = (String) oprMethodV.elementAt(ii);

              if (operatingMethod.equalsIgnoreCase(operatingValue)) {
                preSelect = "selected";
              }
              minmaxout.println("<option " + preSelect + " value=\"" + operatingValue + "\"> " + oprMethod + "</option>\n");
            }

            minmaxout.println("</SELECT>\n");
          } else if (fhOp == null) {
            minmaxout.println("" + operatingMethod + "\n");
          } else {
            minmaxout.println("<INPUT TYPE=\"hidden\" NAME=\"operatingMethod" + i + "\" VALUE=\"" + operatingMethod + "\">\n");
            String operatingMethodDesc = "";
            //Hashtable fhOp= ( Hashtable ) iniOperatingMethodData.get( selectedInventoryGroup );
            if (fhOp != null) {
              Vector oprValueV = (Vector) fhOp.get("OPERATIONAL_VALUE");
              Vector oprMethodV = (Vector) fhOp.get("OPERATIONAL_METHOD");

              for (int ii = 0; ii < oprValueV.size(); ii++) {
                String operatingValue = (String) oprValueV.elementAt(ii);
                String oprMethod = (String) oprMethodV.elementAt(ii);

                if (operatingMethod.equalsIgnoreCase(operatingValue)) {
                  operatingMethodDesc = oprMethod;
                }
              }
            }
            minmaxout.println(operatingMethodDesc);
          }
          minmaxout.println("</TD>\n");

          //Billing Method
          minmaxout.println("<TD " + Color + "\" WIDTH=\"5%\" ALIGN=\"LEFT\">\n");
          Hashtable fhbillingMethod = (Hashtable) iniBillingMethodData.get(selectedInventoryGroup);
          if (this.getOperatingMethodPermission() && fhbillingMethod != null) {
            minmaxout.println("<SELECT CLASS=\"HEADER\"  NAME=\"billingMethod" + i + "\" size=\"1\" OnChange=\"checkBillingMethod(" + i + ")\">\n");
            if (operatingMethod.length() == 0) {
              minmaxout.println("<option selected value=\"\"></option>\n");
            }

            if (billingMethod.length() == 0) {
              minmaxout.println("<option value=\"\"></option>\n");
            }

            Vector billingValueV = (Vector) fhbillingMethod.get("BILLING_VALUE");
            Vector billingMethodV = (Vector) fhbillingMethod.get("BILLING_METHOD");
            //System.out.println( selinvengrp );
            for (int ii = 0; ii < billingValueV.size(); ii++) {
              String preSelect = "";
              String billingValue = (String) billingValueV.elementAt(ii);
              String billMethod = (String) billingMethodV.elementAt(ii);

              if (billingMethod.equalsIgnoreCase(billingValue)) {
                preSelect = "selected";
              }
              minmaxout.println("<option " + preSelect + " value=\"" + billingValue + "\"> " + billMethod + "</option>\n");
            }

            minmaxout.println("</SELECT>\n");
          } else if (fhbillingMethod == null) {
            minmaxout.println("" + billingMethod + "\n");
          } else {
            minmaxout.println("<INPUT TYPE=\"hidden\" NAME=\"billingMethod" + i + "\" VALUE=\"" + billingMethod + "\">\n");
            String billingMethodDesc = "";
            //Hashtable fhOp= ( Hashtable ) iniOperatingMethodData.get( selectedInventoryGroup );
            if (fhbillingMethod != null) {
              Vector billingValueV = (Vector) fhbillingMethod.get("BILLING_VALUE");
              Vector billingMethodV = (Vector) fhbillingMethod.get("BILLING_METHOD");

              for (int ii = 0; ii < billingValueV.size(); ii++) {
                String billingValue = (String) billingValueV.elementAt(ii);
                String billMethod = (String) billingMethodV.elementAt(ii);

                if (billingMethod.equalsIgnoreCase(billingValue)) {
                  billingMethodDesc = billMethod;
                }
              }
            }
            minmaxout.println(billingMethodDesc);
          }
          minmaxout.println("</TD>\n");

          //minmaxout.println( "<td " + Color + "\" width=\"15%\" height=\"38\"><input type=\"text\" CLASS=\"DETAILS\" name=\"remarks"+i+"\" value=\"\" maxlength=\"200\" size=\"40\"></td>\n" );
          minmaxout.println("<td " + Color + "\" width=\"15%\" height=\"38\"><TEXTAREA name=\"remarks" + i + "\" rows=\"2\" cols=\"50\"></TEXTAREA></TD>\n");
        } else {
          minmaxout.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + stocking_method + "</td>\n");
          minmaxout.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + reorder_point + "</td>\n");
          minmaxout.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + stocking_level + "</td>\n");
          minmaxout.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + reorderQuantity + "</td>\n");
          if (purchaser) {
            minmaxout.println("<td " + Color + "\" width=\"5%\" height=\"38\"><input type=\"text\" CLASS=\"DETAILS\" name=\"lookahddays" + i + "\" value=\"" + look_ahead_days + "\" onChange=\"checkqtyvalue('lookahddays" + i + "')\" maxlength=\"10\" size=\"4\"></td>\n");
          } else {
            minmaxout.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + look_ahead_days + "</td>\n");
          }
          minmaxout.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + operatingMethod + "</td>\n");
          minmaxout.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + billingMethod + "</td>\n");
          minmaxout.println("<td " + Color + "\" width=\"5%\" height=\"38\"></td>\n");
        }

        minmaxout.println("</tr>\n");
		  //HIDDEN VALUES
		  minmaxout.println("<INPUT TYPE=\"hidden\" NAME=\"catalogCompanyId" + i + "\" VALUE=\"" + catalog_company_id + "\">\n");	
		}  //end of for loop


		minmaxout.println("</table>\n");
      minmaxout.println("<TABLE  BORDER=\"0\" CELLSPACING=0 CELLPADDING=0 WIDTH=\"100%\" CLASS=\"moveup\">\n");
      minmaxout.println("<tr>");
      minmaxout.println("<TD HEIGHT=\"10\" WIDTH=\"100%\" VALIGN=\"MIDDLE\" BGCOLOR=\"#000066\">&nbsp;\n");
      minmaxout.println("</TD></tr>");
      minmaxout.println("</table>\n");
      minmaxout.println("</form>\n");
    } catch (Exception e) {
      e.printStackTrace();
      minmaxout.println(radian.web.HTMLHelpObj.printError("Min Max Level Change", intcmIsApplication));
    }

    return;
  }

  private void buildHistdetails(String sourcehub, String selinvengrp, String searchtext,
                                String searchLike, PrintWriter minmaxout) {
    String query = "select x.*,to_char(DATE_MODIFIED,'dd Mon yyyy hh24:mi') DATE_MODIFIED1, fx_personnel_id_to_name(MODIFIED_BY) MODIFIED_BY_NAME from min_max_level_log_view x where ";
    boolean falgforand = false;
    query += " HUB = '" + sourcehub + "'  ";
    falgforand = true;
    if (! (selinvengrp.trim().length() == 0 || "All".equalsIgnoreCase(selinvengrp))) {
      if (falgforand) {
        query += " and INVENTORY_GROUP = '" + selinvengrp + "'  ";
      } else {
        query += " INVENTORY_GROUP = '" + selinvengrp + "'  ";
      }
      falgforand = true;
    }

    if (! (searchtext.trim().length() == 0)) {
      if (falgforand) {
        query += " and " + searchLike + " = '" + searchtext + "'  ";
      } else {
        query += " " + searchLike + " = '" + searchtext + "'  ";
      }
      falgforand = true;
    }

    query += " order by DATE_MODIFIED desc";

    DBResultSet dbrs = null;
    ResultSet hisrs = null;

    minmaxout.println("<BR><TABLE WIDTH=\"100%\" BORDER=\"0\" CLASS=\"moveup\">\n");
    minmaxout.println("<TR><TD WIDTH=\"100%\" ALIGN=\"LEFT\" HEIGHT=\"22\" CLASS=\"heading\">"+res.getString("label.changehistory")+"</TD></TR>\n");
    minmaxout.println("</TABLE>\n");

    minmaxout.println("<TABLE  BORDER=\"0\" BGCOLOR=\"#a2a2a2\" CELLSPACING=1 CELLPADDING=2 WIDTH=\"100%\" CLASS=\"moveup\">\n");
    int linecount = 0;
    boolean createHdr = false;

    try {
      dbrs = db.doQuery(query);
      hisrs = dbrs.getResultSet();

      while (hisrs.next()) {
        createHdr = false;
        if (linecount % 10 == 0) {
          createHdr = true;
        }

        if (createHdr) {
          minmaxout.println("<tr align=\"center\">\n");
          minmaxout.println("<TH width=\"5%\"  height=\"38\">"+res.getString("label.inventorygroup")+"</TH>\n");
          minmaxout.println("<TH width=\"5%\"  height=\"38\">"+res.getString("label.company")+"</TH>\n");
          minmaxout.println("<TH width=\"5%\"  height=\"38\">"+res.getString("label.catalog")+"</TH>\n");
          minmaxout.println("<TH width=\"5%\"  height=\"38\">"+res.getString("label.partnumber")+"</TH>\n");
          minmaxout.println("<TH width=\"2%\"  height=\"38\">"+res.getString("label.partgroupnumber")+"</TH>\n");
          minmaxout.println("<TH width=\"5%\"  height=\"38\">"+res.getString("label.changedon")+"</TH>\n");
          minmaxout.println("<TH width=\"5%\"  height=\"38\">"+res.getString("label.changedby")+"</TH>\n");
          minmaxout.println("<TH width=\"5%\"  height=\"38\">"+res.getString("label.oldstockingmethod")+"</TH>\n");
          minmaxout.println("<TH width=\"5%\"  height=\"38\">"+res.getString("label.newstockingmethod")+"</TH>\n");
          minmaxout.println("<TH width=\"5%\"  height=\"38\">"+res.getString("label.oldreorderpoint")+"</TH>\n");
          minmaxout.println("<TH width=\"5%\"  height=\"38\">"+res.getString("label.newreorderpoint")+"</TH>\n");
          minmaxout.println("<TH width=\"5%\"  height=\"38\">"+res.getString("label.oldstockinglevel")+"</TH>\n");
          minmaxout.println("<TH width=\"5%\"  height=\"38\">"+res.getString("label.newstockinglevel")+"</TH>\n");
          minmaxout.println("<TH width=\"5%\"  height=\"38\">"+res.getString("label.oldreorderquantity")+"</TH>\n");
          minmaxout.println("<TH width=\"5%\"  height=\"38\">"+res.getString("label.newreorderquantity")+"</TH>\n");
          minmaxout.println("<TH width=\"5%\"  height=\"38\">"+res.getString("label.oldlookahead")+"</TH>\n");
          minmaxout.println("<TH width=\"5%\"  height=\"38\">"+res.getString("label.newlookahead")+"</TH>\n");
          minmaxout.println("<TH width=\"5%\"  height=\"38\">"+res.getString("label.oldreceiptprocessingmethod")+"</TH>\n");
          minmaxout.println("<TH width=\"5%\"  height=\"38\">"+res.getString("label.newreceiptprocessingmethod")+"</TH>\n");
          minmaxout.println("<TH width=\"5%\"  height=\"38\">"+res.getString("label.oldbillingmethod")+"</TH>\n");
          minmaxout.println("<TH width=\"5%\"  height=\"38\">"+res.getString("label.newbillingmethod")+"</TH>\n");
          minmaxout.println("<TH width=\"15%\"  height=\"38\">"+res.getString("label.remarks")+"</TH>\n");
          minmaxout.println("</tr>\n");
        }

        String company_id = hisrs.getString("COMPANY_ID") == null ? "" : hisrs.getString("COMPANY_ID");
        String catalog_id = hisrs.getString("CATALOG_ID") == null ? "" : hisrs.getString("CATALOG_ID");
            //String inven_grp=hisrs.getString( "INVENTORY_GROUP" ) == null ? "" : hisrs.getString( "INVENTORY_GROUP" );
        String inventoryGroupName = hisrs.getString("INVENTORY_GROUP_NAME") == null ? "" : hisrs.getString("INVENTORY_GROUP_NAME");
        String cat_part_no = hisrs.getString("CAT_PART_NO") == null ? "" : hisrs.getString("CAT_PART_NO");
        String part_group_no = hisrs.getString("PART_GROUP_NO") == null ? "" : hisrs.getString("PART_GROUP_NO");
        String old_reorder_point = hisrs.getString("OLD_ORDER_POINT") == null ? "" : hisrs.getString("OLD_ORDER_POINT");
        String old_stocking_level = hisrs.getString("OLD_STOCKING_LEVEL") == null ? "" : hisrs.getString("OLD_STOCKING_LEVEL");
        String new_reorder_point = hisrs.getString("NEW_ORDER_POINT") == null ? "" : hisrs.getString("NEW_ORDER_POINT");
        String new_stocking_level = hisrs.getString("NEW_STOCKING_LEVEL") == null ? "" : hisrs.getString("NEW_STOCKING_LEVEL");
        String date_modified = hisrs.getString("DATE_MODIFIED1") == null ? "" : hisrs.getString("DATE_MODIFIED1");
        String modified_by = hisrs.getString("MODIFIED_BY_NAME") == null ? "" : hisrs.getString("MODIFIED_BY_NAME");
        String remarks = hisrs.getString("REMARKS") == null ? "" : hisrs.getString("REMARKS");
        String oldstockmethod = hisrs.getString("OLD_STOCKED") == null ? "" : hisrs.getString("OLD_STOCKED");
        String newstockmethod = hisrs.getString("NEW_STOCKED") == null ? "" : hisrs.getString("NEW_STOCKED");
        String oldLookAhead = hisrs.getString("OLD_LOOK_AHEAD") == null ? "" : hisrs.getString("OLD_LOOK_AHEAD");
        String newLookAhead = hisrs.getString("NEW_LOOK_AHEAD") == null ? "" : hisrs.getString("NEW_LOOK_AHEAD");
        String oldReorderQuantity = hisrs.getString("OLD_REORDER_QUANTITY") == null ? "" : hisrs.getString("OLD_REORDER_QUANTITY");
        String newReorderQuantity = hisrs.getString("NEW_REORDER_QUANTITY") == null ? "" : hisrs.getString("NEW_REORDER_QUANTITY");
        String oldoperatingmethod = hisrs.getString("OLD_RECEIPT_PROCESSING_METHOD") == null ? "" : hisrs.getString("OLD_RECEIPT_PROCESSING_METHOD");
        String newoperatingmethod = hisrs.getString("NEW_RECEIPT_PROCESSING_METHOD") == null ? "" : hisrs.getString("NEW_RECEIPT_PROCESSING_METHOD");
        String oldBillingmethod = hisrs.getString("OLD_BILLING_METHOD") == null ? "" : hisrs.getString("OLD_BILLING_METHOD");
        String newBillingmethod = hisrs.getString("NEW_BILLING_METHOD") == null ? "" : hisrs.getString("NEW_BILLING_METHOD");

        String Color = " ";
        if (linecount % 2 == 0) {
          Color = "CLASS=\"white";
        } else {
          Color = "CLASS=\"blue";
        }

        minmaxout.println("<tr align=\"center\">\n");
        minmaxout.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + inventoryGroupName + "</td>\n");
        minmaxout.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + company_id + "</td>\n");
        minmaxout.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + catalog_id + "</td>\n");
        minmaxout.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + cat_part_no + "</td>\n");
        minmaxout.println("<td " + Color + "\" width=\"2%\" height=\"38\">" + part_group_no + "</td>\n");
        minmaxout.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + date_modified + "</td>\n");
        minmaxout.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + modified_by + "</td>\n");
        minmaxout.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + oldstockmethod + "</td>\n");
        minmaxout.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + newstockmethod + "</td>\n");
        minmaxout.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + old_reorder_point + "</td>\n");
        minmaxout.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + new_reorder_point + "</td>\n");
        minmaxout.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + old_stocking_level + "</td>\n");
        minmaxout.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + new_stocking_level + "</td>\n");
        minmaxout.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + oldReorderQuantity + "</td>\n");
        minmaxout.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + newReorderQuantity + "</td>\n");
        minmaxout.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + oldLookAhead + "</td>\n");
        minmaxout.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + newLookAhead + "</td>\n");
        minmaxout.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + oldoperatingmethod + "</td>\n");
        minmaxout.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + newoperatingmethod + "</td>\n");
        minmaxout.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + oldBillingmethod + "</td>\n");
        minmaxout.println("<td " + Color + "\" width=\"5%\" height=\"38\">" + newBillingmethod + "</td>\n");
        minmaxout.println("<td " + Color + "\" width=\"15%\" height=\"38\">" + remarks + "</td>\n");
        minmaxout.println("</tr>\n");
        linecount++;
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (dbrs != null) {
        dbrs.close();
      }
    }

    minmaxout.println("</TABLE>\n");
  }

  /*private void operatingMethodHistory( String selinvengrp,String searchtext,String searchLike,PrintWriter minmaxout )
   {
     String query="select x.*,to_char(TRANSACTION_DATE,'dd Mon yyyy hh24:mi') TRANSACTION_DATE1 from item_count_mr_create_hist_view x where ";
     boolean falgforand=false;
     if ( ! ( selinvengrp.trim().length() == 0 || "All".equalsIgnoreCase( selinvengrp ) ) )
     {
    if ( falgforand )
      query+=" and INVENTORY_GROUP = '" + selinvengrp + "'  ";
    else
      query+=" INVENTORY_GROUP = '" + selinvengrp + "'  ";
    falgforand=true;
     }
     if ( ! ( searchtext.trim().length() == 0 ) )
     {
    if ( falgforand )
      query+=" and " + searchLike + " = '" + searchtext + "'  ";
    else
      query+=" " + searchLike + " = '" + searchtext + "'  ";
    falgforand=true;
     }
     query+=" order by TRANSACTION_DATE desc";
     DBResultSet dbrs=null;
     ResultSet hisrs=null;
     minmaxout.println( "<BR><TABLE WIDTH=\"100%\" BORDER=\"0\" CLASS=\"moveup\">\n" );
     minmaxout.println( "<TR><TD WIDTH=\"100%\" ALIGN=\"LEFT\" HEIGHT=\"22\" CLASS=\"heading\">Operating Method Change History</TD></TR>\n" );
     minmaxout.println( "</TABLE>\n" );
     minmaxout.println( "<TABLE  BORDER=\"0\" BGCOLOR=\"#a2a2a2\" CELLSPACING=1 CELLPADDING=2 WIDTH=\"100%\" CLASS=\"moveup\">\n" );
     int linecount=0;
     boolean createHdr=false;
     try
     {
    dbrs=db.doQuery( query );
    hisrs=dbrs.getResultSet();
    while ( hisrs.next() )
    {
      createHdr=false;
      if ( linecount % 10 == 0 )
      {
     createHdr=true;
      }
      if ( createHdr )
      {
     minmaxout.println( "<tr align=\"center\">\n" );
     minmaxout.println( "<TH width=\"5%\"  height=\"38\">Inventory Group</TH>\n" );
     minmaxout.println( "<TH width=\"5%\"  height=\"38\">Item Id</TH>\n" );
     minmaxout.println( "<TH width=\"5%\"  height=\"38\">Old Operating Method</TH>\n" );
     minmaxout.println( "<TH width=\"5%\"  height=\"38\">New Operating Method</TH>\n" );
     minmaxout.println( "<TH width=\"5%\"  height=\"38\">Changed On</TH>\n" );
     minmaxout.println( "<TH width=\"5%\"  height=\"38\">Changed By</TH>\n" );
     minmaxout.println( "</tr>\n" );
      }
      String inven_grp=hisrs.getString( "INVENTORY_GROUP" ) == null ? "" : hisrs.getString( "INVENTORY_GROUP" );
      String itemId=hisrs.getString( "ITEM_ID" ) == null ? "" : hisrs.getString( "ITEM_ID" );
      String date_modified=hisrs.getString( "TRANSACTION_DATE1" ) == null ? "" : hisrs.getString( "TRANSACTION_DATE1" );
      String modified_by=hisrs.getString( "TRANSACTION_USER_NAME" ) == null ? "" : hisrs.getString( "TRANSACTION_USER_NAME" );
      String oldoperatingmethod=hisrs.getString( "OLD_OPERATING_METHOD" ) == null ? "" : hisrs.getString( "OLD_OPERATING_METHOD" );
      String newoperatingmethod=hisrs.getString( "NEW_OPERATING_METHOD" ) == null ? "" : hisrs.getString( "NEW_OPERATING_METHOD" );
      String Color=" ";
      if ( linecount % 2 == 0 )
      {
     Color="CLASS=\"white";
      }
      else
      {
     Color="CLASS=\"blue";
      }
      minmaxout.println( "<tr align=\"center\">\n" );
      minmaxout.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + inven_grp + "</td>\n" );
      minmaxout.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + itemId + "</td>\n" );
      minmaxout.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + oldoperatingmethod + "</td>\n" );
      minmaxout.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + newoperatingmethod + "</td>\n" );
      minmaxout.println( "<td " + Color + "\" width=\"2%\" height=\"38\">" + date_modified + "</td>\n" );
      minmaxout.println( "<td " + Color + "\" width=\"5%\" height=\"38\">" + modified_by + "</td>\n" );
      minmaxout.println( "</tr>\n" );
      linecount++;
    }
     }
     catch ( Exception e )
     {
    e.printStackTrace();
     }
     finally
     {
    if ( dbrs != null )
    {
      dbrs.close();
    }
     }
     minmaxout.println( "</TABLE>\n" );
   }*/

  //Build HTML Header
  private void buildHeader(String selectedHub, String selinvGrp, String searchtext, Hashtable hub_list_set, Collection hubInventoryGroupOvBeanCollection, String message,
                           String searchLike, String changeOperatingMethod, String partGroupNo, boolean noSearchOptions, PrintWriter minmaxout) {
    //StringBuffer Msg=new StringBuffer();
    if (noSearchOptions) {
      minmaxout.println(radian.web.HTMLHelpObj.printHTMLHeader("Edit Material Parameters", "minmaxmgmt.js", intcmIsApplication));
    } else {
      minmaxout.println(radian.web.HTMLHelpObj.printHTMLHeader("Min Max Level Change", "minmaxmgmt.js", intcmIsApplication));
    }
    minmaxout.println("<SCRIPT SRC=\"/clientscripts/FormChek.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
    minmaxout.println("</HEAD>  \n");

    String Search_servlet = radian.web.tcmisResourceLoader.getminmaxurl();

    minmaxout.println("<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n");
    minmaxout.println("<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>"+res.getString("msg.waitupdate")+"</B></FONT><center>\n");
    minmaxout.println("</DIV>\n");
    minmaxout.println("<DIV ID=\"MAINPAGE\" STYLE=\"\">\n");

    if (noSearchOptions) {
      minmaxout.println(radian.web.HTMLHelpObj.PrintTitleBarNolink("Edit Material Parameters\n"));
    } else {
      minmaxout.println(radian.web.HTMLHelpObj.PrintTitleBar("Min Max Level Change\n"));
    }

    Hashtable hub_list = (Hashtable) (hub_list_set.get("HUB_LIST"));
    if (hub_list.size() < 1) {
      minmaxout.println(radian.web.HTMLHelpObj.printMessageinTable("<BR><BR>"+res.getString("msg.noaccess")));
      return;
    }

    minmaxout.println("<SCRIPT LANGUAGE=\"JavaScript\" TYPE=\"text/javascript\">\n <!--\n");
    //minmaxout.println( radian.web.poHelpObj.createhubinvgrjs( initialinvData ) );
    try {
      minmaxout.println(radian.web.poHelpObj.getHubInventoryGroupJavaScript(hubInventoryGroupOvBeanCollection, true, false));
    } catch (Exception ex1) {
      ex1.printStackTrace();
    }
    minmaxout.println("// -->\n </SCRIPT>\n");

    minmaxout.println("<FORM METHOD=\"POST\" NAME=\"minmaxlvls\" onSubmit=\"return SubmitOnlyOnce()\" ACTION=\"" + Search_servlet + "\">\n");
    minmaxout.println("<INPUT TYPE=\"hidden\" NAME=\"changeOperatingMethod\" VALUE=\"" + changeOperatingMethod + "\">\n");
    //minmaxout.println("<INPUT TYPE=\"hidden\" NAME=\"origOperatingMethod\" VALUE=\""+origOperatingMethod+"\">\n");
    if (noSearchOptions) {
      minmaxout.println("<INPUT TYPE=\"hidden\" NAME=\"displaySearchOptions\" VALUE=\"Yes\">\n");
    }

    minmaxout.println("<INPUT TYPE=\"hidden\" NAME=\"partGroupNo\" VALUE=\"" + partGroupNo + "\">\n");
    //minmaxout.println("<INPUT TYPE=\"hidden\" NAME=\"countUom\" VALUE=\""+countUom+"\">\n");
    minmaxout.println("<TABLE WIDTH=\"100%\" BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" CLASS=\"moveup\">\n");
    minmaxout.println("<TR VALIGN=\"MIDDLE\">\n");

    if (noSearchOptions) {
      if (noSearchOptions) {
        minmaxout.println("<TR VALIGN=\"MIDDLE\">\n");
        minmaxout.println("<INPUT TYPE=\"hidden\" NAME=\"invengrp\" VALUE=\"" + selinvGrp + "\">\n");
        minmaxout.println("<INPUT TYPE=\"hidden\" NAME=\"HubName\" VALUE=\"" + selectedHub + "\">\n");
        minmaxout.println("<INPUT TYPE=\"hidden\" NAME=\"searchlike\" VALUE=\"" + searchLike + "\">\n");
        minmaxout.println("<INPUT TYPE=\"hidden\" NAME=\"searchtext\" VALUE=\"" + searchtext + "\">\n");

        //Change Levels
        minmaxout.println("<TD WIDTH=\"5%\" ALIGN=\"LEFT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n");
        try {
          if (this.getupdateStatus()) {
            minmaxout.println("<INPUT TYPE=\"submit\" VALUE=\""+res.getString("button.update")+"\" onclick=\"return actionValue(name,this)\"  NAME=\"Update\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n");
          }
        } catch (Exception ex) {
        }
        minmaxout.println("</TD>\n");
        minmaxout.println("<TD WIDTH=\"5%\" COLSPAN=\"4\" ALIGN=\"LEFT\" CLASS=\"announce\" VALIGN=\"MIDDLE\"></TD><TD>\n");
        minmaxout.println("<INPUT CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name=\"closeButton\" value=\""+res.getString("label.close")+"\" OnClick=\"cancel()\" type=\"button\">\n");
        minmaxout.println("</TD>\n");
        minmaxout.println("</TR>\n");
      }
    } else {
      //Hub
      minmaxout.println("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
      minmaxout.println("<B>"+res.getString("label.hub")+":</B>&nbsp;\n");
      minmaxout.println("</TD>\n");
      minmaxout.println("<TD WIDTH=\"15%\">\n");
      minmaxout.println("<SELECT CLASS=\"HEADER\"  NAME=\"HubName\" size=\"1\" OnChange=\"hubchanged(document.minmaxlvls.HubName)\">\n");
      Hashtable hub_prority_list = (Hashtable) (hub_list_set.get("HUB_PRORITY_LIST"));
      if (selectedHub.trim().length() == 0) {
        selectedHub = radian.web.HTMLHelpObj.getFirstBranchPlant(hubInventoryGroupOvBeanCollection);
      }
      minmaxout.println(radian.web.HTMLHelpObj.getHubDropDown(hubInventoryGroupOvBeanCollection, selectedHub));
      minmaxout.println("</SELECT>\n");
      minmaxout.println("</TD>\n");

      // Search Text
      minmaxout.println("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
      minmaxout.println("<SELECT CLASS=\"HEADER\"  NAME=\"searchlike\" size=\"1\">\n");
      Hashtable sortresult = new Hashtable();
      sortresult.put(res.getString("label.itemid"), "ITEM_ID");
      //sortresult.put( "Item Desc","ITEM_DESC" );
      sortresult.put(res.getString("label.partnumber"), "CAT_PART_NO");

      minmaxout.println(radian.web.HTMLHelpObj.getDropDown(sortresult, searchLike));
      minmaxout.println("</SELECT>\n");
      minmaxout.println("</TD>\n");

      minmaxout.println("<TD WIDTH=\"10%\" ALIGN=\"LEFT\">\n");
      minmaxout.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"searchtext\" value=\"" + searchtext + "\" size=\"15\">\n");
      minmaxout.println("</TD>\n");
      minmaxout.println("<TD WIDTH=\"5%\" ALIGN=\"LEFT\" CLASS=\"announce\">\n");
      minmaxout.println("<INPUT TYPE=\"submit\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\""+res.getString("label.search")+"\" onclick=\"return actionValue(name,this)\" NAME=\"showlevels\">\n");
      minmaxout.println("</TD>\n");
      minmaxout.println("</TR>\n");

      minmaxout.println("<TR VALIGN=\"MIDDLE\">\n");
      // Inventory Group
      minmaxout.println("<TD WIDTH=\"5%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n");
      minmaxout.println("<B>"+res.getString("label.invgrp")+":</B>&nbsp;\n");
      minmaxout.println("</TD>\n");
      minmaxout.println("<TD WIDTH=\"15%\">\n");
      minmaxout.println("<SELECT CLASS=\"HEADER\"  NAME=\"invengrp\" size=\"1\">\n");
      minmaxout.println(radian.web.HTMLHelpObj.getInventoryGroupDropDown(hubInventoryGroupOvBeanCollection, selectedHub, selinvGrp, false));
      minmaxout.println("</SELECT>\n");
      minmaxout.println("</TD>\n");

      minmaxout.println("<TD WIDTH=\"5%\" CLASS=\"announce\">\n");
      minmaxout.println("&nbsp;\n");
      minmaxout.println("</TD>\n");

      minmaxout.println("<TD WIDTH=\"10%\" CLASS=\"announce\">\n");
      minmaxout.println("&nbsp;\n");
      minmaxout.println("</TD>\n");

      //Change Levels
      minmaxout.println("<TD WIDTH=\"5%\" ALIGN=\"LEFT\" CLASS=\"announce\" VALIGN=\"MIDDLE\">\n");
      try {
        if (this.getupdateStatus()) {
          minmaxout.println("<INPUT TYPE=\"submit\" VALUE=\""+res.getString("button.update")+"\" onclick=\"return actionValue(name,this)\"  NAME=\"Update\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n");
        }
      } catch (Exception ex) {
      }
      minmaxout.println("</TD>\n");
      minmaxout.println("</TR>\n");
    }
    minmaxout.println("</TABLE>\n");

    minmaxout.println("<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\"Search\">\n");
    minmaxout.println("<INPUT TYPE=\"hidden\" NAME=\"selitem_id\" VALUE=\"" + searchtext + "\">\n");
    minmaxout.println("<INPUT TYPE=\"hidden\" NAME=\"selHubName\" VALUE=\"" + selectedHub + "\">\n");
    minmaxout.println("<INPUT TYPE=\"hidden\" NAME=\"selinvengrp\" VALUE=\"" + selinvGrp + "\">\n");
    try {
      if (radian.web.HTMLHelpObj.isItemCountingHub(hubInventoryGroupOvBeanCollection,
          selectedHub) && !"Yes".equalsIgnoreCase(changeOperatingMethod)) {
        minmaxout.println(radian.web.HTMLHelpObj.printMessageinTable(
            "Use the Item Management Page to change Min Max levels."));
      }
    } catch (Exception ex2) {
    }

    if (message.length() > 0) {
      minmaxout.println("<BR><B>" + message + "</B>");
    }
    return;
  }

  /*private String getOperatingMethod( String inventoryGroup,String catPartNo ) throws Exception
     {
    String operatingMethod = "";
    boolean falgforand=false;
    DBResultSet dbrs=null;
    ResultSet searchRs=null;
    try
    {
   String query="select distinct OPERATING_METHOD from item_inventory_detail_view where ";
   falgforand=false;
   if ( ! ( inventoryGroup.trim().length() == 0 || "All".equalsIgnoreCase( inventoryGroup ) ) )
   {
     if ( falgforand )
    query+=" and INVENTORY_GROUP = '" + inventoryGroup + "'";
     else
    query+=" INVENTORY_GROUP = '" + inventoryGroup + "'";
    falgforand=true;
   }
   if ( catPartNo.trim().length() > 0 )
   {
     if ( falgforand )
    query+=" and CAT_PART_NO = '" + catPartNo + "'";
     else
    query+=" CAT_PART_NO = '" + catPartNo + "'";
    falgforand=true;
   }
   if ( falgforand )
     query+=" and STATUS = 'A'";
   else
   {
     query+=" STATUS = 'A'";
     falgforand=true;
   }
   //query+=" ORDER BY ITEM_ID,INVENTORY_GROUP";
   dbrs=db.doQuery( query );
   searchRs=dbrs.getResultSet();
   while ( searchRs.next() )
   {
     operatingMethod  = searchRs.getString("OPERATING_METHOD")==null?"":searchRs.getString("OPERATING_METHOD");
   }
    }
    catch ( Exception e )
    {
   e.printStackTrace();
   throw e;
    }
    finally
    {
   if ( dbrs != null ){dbrs.close();}
    }
    return operatingMethod;
     }*/

  private Vector getResult(String sourcehub, String inventoryGroup, String searchtext, String searchLike, Collection hubInventoryGroupOvBeanColl, String partGroupNo) throws Exception {

    Vector Data = new Vector();
    Hashtable DataH = new Hashtable();
    Hashtable summary = new Hashtable();
    String nodaysago = "60";
    boolean falgforand = false;
    DBResultSet dbrs = null;
    ResultSet searchRs = null;

    int count = 0;
    summary.put("TOTAL_NUMBER", new Integer(count));
    Data.addElement(summary);

    try {
      String query = "select distinct x.* from current_min_max_level_view x  where ";
      query += " HUB = '" + sourcehub + "'  ";
      falgforand = true;

      HubInventoryGroupProcess hubInventoryGroupProcess = new
          HubInventoryGroupProcess("hub");
      boolean iscollection = hubInventoryGroupProcess.isCollection(hubInventoryGroupOvBeanColl,
          sourcehub, inventoryGroup);

      if (inventoryGroup != null && inventoryGroup.length() > 0 &&
          !inventoryGroup.equalsIgnoreCase("ALL")) {
        if (iscollection) {
          if (falgforand) {
            query += "and ";

          }
          query += " INVENTORY_GROUP in (select INVENTORY_GROUP from INVENTORY_GROUP_COLLECTION where HUB = '" + sourcehub +
              "' and INVENTORY_GROUP_COLLECTION = '" + inventoryGroup + "') ";
        } else if (inventoryGroup.length() > 0) {
          if (falgforand) {
            query += "and INVENTORY_GROUP ='" + inventoryGroup + "' ";
          } else {
            query += " INVENTORY_GROUP ='" + inventoryGroup + "' ";
          }
        }
      }

      /*if ( ! ( selinvengrp.trim().length() == 0 || "All".equalsIgnoreCase( selinvengrp ) ) )
        {
        if ( falgforand )
       query+=" and INVENTORY_GROUP = '" + selinvengrp + "'  ";
        else
       query+=" INVENTORY_GROUP = '" + selinvengrp + "'  ";
        falgforand=true;
        }*/

      if (! (searchtext.trim().length() == 0)) {
        if (falgforand) {
          query += " and " + searchLike + " like '%" + searchtext + "%'  ";
        } else {
          query += " " + searchLike + " like '%" + searchtext + "%'  ";
        }
        falgforand = true;
      }

      if (partGroupNo.length() > 0) {
        if (falgforand) {
          query += "and PART_GROUP_NO = " + partGroupNo + " ";
        } else {
          query += " PART_GROUP_NO = " + partGroupNo + " ";
        }
      }

      query += " order by INVENTORY_GROUP ";

      dbrs = db.doQuery(query);
      searchRs = dbrs.getResultSet();

      while (searchRs.next()) {
        DataH = new Hashtable();
        DataH.put("COMPANY_ID", searchRs.getString("COMPANY_ID") == null ? "" : searchRs.getString("COMPANY_ID"));
        DataH.put("CATALOG_ID", searchRs.getString("CATALOG_ID") == null ? "" : searchRs.getString("CATALOG_ID"));
        DataH.put("CAT_PART_NO", searchRs.getString("CAT_PART_NO") == null ? "" : searchRs.getString("CAT_PART_NO"));
        DataH.put("PART_GROUP_NO", searchRs.getString("PART_GROUP_NO") == null ? "" : searchRs.getString("PART_GROUP_NO"));
        DataH.put("ITEM_ID", searchRs.getString("ITEM_ID") == null ? "" : searchRs.getString("ITEM_ID"));
        DataH.put("ITEM_DESC", searchRs.getString("ITEM_DESC") == null ? "" : searchRs.getString("ITEM_DESC"));
        DataH.put("STOCKING_METHOD", searchRs.getString("STOCKING_METHOD") == null ? "" : searchRs.getString("STOCKING_METHOD"));
        DataH.put("OLD_STOCKING_METHOD", searchRs.getString("STOCKING_METHOD") == null ? "" : searchRs.getString("STOCKING_METHOD"));
        DataH.put("REORDER_POINT", searchRs.getString("REORDER_POINT") == null ? "" : searchRs.getString("REORDER_POINT"));
        DataH.put("STOCKING_LEVEL", searchRs.getString("STOCKING_LEVEL") == null ? "" : searchRs.getString("STOCKING_LEVEL"));
        DataH.put("LOOK_AHEAD_DAYS", searchRs.getString("LOOK_AHEAD_DAYS") == null ? "" : searchRs.getString("LOOK_AHEAD_DAYS"));
        DataH.put("ESTABLISHED_STOCK_FLAG", searchRs.getString("ESTABLISHED_STOCK_FLAG") == null ? "" : searchRs.getString("ESTABLISHED_STOCK_FLAG"));
        DataH.put("INVENTORY_GROUP", searchRs.getString("INVENTORY_GROUP") == null ? "" : searchRs.getString("INVENTORY_GROUP"));
        DataH.put("CURRENT_STOCKING_METHOD", searchRs.getString("CURRENT_STOCKING_METHOD") == null ? "" : searchRs.getString("CURRENT_STOCKING_METHOD"));
        DataH.put("RECEIPT_PROCESSING_METHOD", searchRs.getString("RECEIPT_PROCESSING_METHOD") == null ? "" : searchRs.getString("RECEIPT_PROCESSING_METHOD"));
        DataH.put("INVENTORY_GROUP_NAME", searchRs.getString("INVENTORY_GROUP_NAME") == null ? "" : searchRs.getString("INVENTORY_GROUP_NAME"));
        DataH.put("REORDER_QUANTITY", searchRs.getString("REORDER_QUANTITY") == null ? "" : searchRs.getString("REORDER_QUANTITY"));
        DataH.put("PART_DESCRIPTION", searchRs.getString("PART_DESCRIPTION") == null ? "" : searchRs.getString("PART_DESCRIPTION"));
        DataH.put("BILLING_METHOD", searchRs.getString("BILLING_METHOD") == null ? "" : searchRs.getString("BILLING_METHOD"));

        DataH.put("REMARKS", "");
        DataH.put("UPDATE_LEVELS", "");
        DataH.put("UPDATE_LOOK_AHEAD_DAYS", "");
		  DataH.put("CATALOG_COMPANY_ID", searchRs.getString("CATALOG_COMPANY_ID") == null ? "" : searchRs.getString("CATALOG_COMPANY_ID"));

		  Data.addElement(DataH);
        count++;
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

        String stockmethod = request.getParameter( ("stockmethod" + i));
        if (stockmethod == null) {
          stockmethod = "";
        }
        stockmethod = stockmethod.trim();
        if (!stockmethod.equalsIgnoreCase(hD.get("STOCKING_METHOD").toString()) && stockmethod.length() > 0) {
          hD.remove("STOCKING_METHOD");
          hD.put("STOCKING_METHOD", stockmethod);

          hD.remove("UPDATE_LEVELS");
          hD.put("UPDATE_LEVELS", "Yes");
        }

        String reorderpt = "";
        reorderpt = request.getParameter( ("reorderpt" + i));
        if (reorderpt == null) {
          reorderpt = "";
        }
        reorderpt = reorderpt.trim();
        if (!reorderpt.equalsIgnoreCase(hD.get("REORDER_POINT").toString()) && reorderpt.length() > 0) {
          hD.remove("REORDER_POINT");
          hD.put("REORDER_POINT", reorderpt);

          hD.remove("UPDATE_LEVELS");
          hD.put("UPDATE_LEVELS", "Yes");
        }

        String stocklevel = "";
        stocklevel = request.getParameter( ("stocklevel" + i));
        if (stocklevel == null) {
          stocklevel = "";
        }
        stocklevel = stocklevel.trim();
        if (!stocklevel.equalsIgnoreCase(hD.get("STOCKING_LEVEL").toString()) && stocklevel.length() > 0) {
          hD.remove("STOCKING_LEVEL");
          hD.put("STOCKING_LEVEL", stocklevel);

          hD.remove("UPDATE_LEVELS");
          hD.put("UPDATE_LEVELS", "Yes");
        }

        String lookahddays = "";
        lookahddays = request.getParameter( ("lookahddays" + i));
        if (lookahddays == null) {
          lookahddays = "";
        }
        lookahddays = lookahddays.trim();
        if (!lookahddays.equalsIgnoreCase(hD.get("LOOK_AHEAD_DAYS").toString())) {
          hD.remove("LOOK_AHEAD_DAYS");
          hD.put("LOOK_AHEAD_DAYS", lookahddays);

          hD.remove("UPDATE_LOOK_AHEAD_DAYS");
          hD.put("UPDATE_LOOK_AHEAD_DAYS", "Yes");

          hD.remove("UPDATE_LEVELS");
          hD.put("UPDATE_LEVELS", "Yes");
        }

        String reorderQuantity = "";
        reorderQuantity = request.getParameter( ("reorderQuantity" + i));
        if (reorderQuantity == null) {
          reorderQuantity = "";
        }
        reorderQuantity = reorderQuantity.trim();
        if (!reorderQuantity.equalsIgnoreCase(hD.get("REORDER_QUANTITY").toString())) {
          hD.remove("REORDER_QUANTITY");
          hD.put("REORDER_QUANTITY", reorderQuantity);

          hD.remove("UPDATE_LEVELS");
          hD.put("UPDATE_LEVELS", "Yes");
        }

        String billingMethod = "";
        billingMethod = request.getParameter( ("billingMethod" + i));
        if (billingMethod == null) {
          billingMethod = "";
        }
        billingMethod = billingMethod.trim();
        if (!billingMethod.equalsIgnoreCase(hD.get("BILLING_METHOD").toString())) {
          hD.remove("BILLING_METHOD");
          hD.put("BILLING_METHOD", billingMethod);

          hD.remove("UPDATE_LEVELS");
          hD.put("UPDATE_LEVELS", "Yes");
        }

        String operatingMethod = "";
        operatingMethod = request.getParameter( ("operatingMethod" + i));
        if (operatingMethod == null) {
          operatingMethod = "";
        }
        operatingMethod = operatingMethod.trim();
        if (!operatingMethod.equalsIgnoreCase(hD.get("RECEIPT_PROCESSING_METHOD").toString())) {
          hD.remove("RECEIPT_PROCESSING_METHOD");
          hD.put("RECEIPT_PROCESSING_METHOD", operatingMethod);

          hD.remove("UPDATE_LEVELS");
          hD.put("UPDATE_LEVELS", "Yes");
        }

        String remarks = "";
        remarks = request.getParameter( ("remarks" + i));
        if (remarks == null) {
          remarks = "";
        }
        remarks = remarks.trim();
        if (!remarks.equalsIgnoreCase(hD.get("REMARKS").toString())) {
          hD.remove("REMARKS");
          hD.put("REMARKS", remarks.trim());

          hD.remove("UPDATE_LEVELS");
          hD.put("UPDATE_LEVELS", "Yes");
        }

		  String catalogCompanyId = request.getParameter(("catalogCompanyId"+i));
		  if(catalogCompanyId ==null) {
			  catalogCompanyId = "";
		  }
		  hD.put("CATALOG_COMPANY_ID",catalogCompanyId.trim());

		  new_data.addElement(hD);
      }
    } catch (Exception e) {
      e.printStackTrace();
      //minmaxout.println( radian.web.HTMLHelpObj.printError( "Min Max Level Change" ) );
    }

    return new_data;
  }

  /*public boolean UpdateLine( String inventoryGroup,String itemId,String operatingMethod,String countUom,String personnelID )
   {
    boolean result=false;
    CallableStatement cs=null;
    try
    {
   Connection connect1=null;
   connect1=db.getConnection();
   cs=connect1.prepareCall( "{call PKG_ITEM_INVENTORY_PAGE.PR_ITEM_COUNT_MR_CREATE_UPDATE(?,?,?,?,?,?,?,?,?)}" );
   cs.setInt( 1,Integer.parseInt( itemId ) );
   cs.setString( 2,inventoryGroup );
//cs.setString( 3,deleteline );
   cs.setNull(3,java.sql.Types.VARCHAR);
   cs.setString( 4,operatingMethod );
   cs.setString( 5,countUom );
   cs.setNull(6,java.sql.Types.VARCHAR);
   cs.setInt( 7,Integer.parseInt( personnelID ) );
   cs.setNull(8,java.sql.Types.DATE);
   cs.registerOutParameter( 9,java.sql.Types.VARCHAR );
   cs.execute();
   String errorcode=cs.getString( 9 );
   minmaxlog.info( "Result from PR_ITEM_COUNT_MR_CREATE_UPDATE procedure Error Code:  " + errorcode + "  item_id " + itemId + "  inventory_group " + inventoryGroup + "  operatingMethod " + operatingMethod+ " countUom "+countUom+"" );
   if ( errorcode == null )
   {
     result=true;
   }
   else
   {
     errormesages+= "Error updating Operating Method. " + errorcode + "<BR>";
     result=false;
   }
    }
    catch ( Exception e )
    {
   e.printStackTrace();
   radian.web.emailHelpObj.senddatabaseHelpemails("Error Calling PR_ITEM_COUNT_MR_CREATE_UPDATE in item inventory","Error occured while running PR_ITEM_COUNT_MR_CREATE_UPDATE \nError Msg:\n" + e.getMessage() + "\nFor Input Parameters \nitem_id " + itemId + "  inventory_group " + inventoryGroup + "  operatingMethod " + operatingMethod+ "" );
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
   }*/

  private boolean UpdateDetails(Vector data, String logonid) {
    boolean result = false;

    try {
      Hashtable summary = new Hashtable();
      summary = (Hashtable) data.elementAt(0);
      int total = ( (Integer) (summary.get("TOTAL_NUMBER"))).intValue();

      for (int i = 1; i < total + 1; i++) {
        Hashtable hD = new Hashtable();
        hD = (Hashtable) data.elementAt(i);
        String returnedReceiptId = "";
        String Line_Check = "";
        Line_Check = (hD.get("UPDATE_LEVELS") == null ? "" : hD.get("UPDATE_LEVELS").toString());
        String company_id = hD.get("COMPANY_ID") == null ? "" : hD.get("COMPANY_ID").toString();
        String catalog_id = hD.get("CATALOG_ID") == null ? "" : hD.get("CATALOG_ID").toString();
        String cat_part_no = hD.get("CAT_PART_NO") == null ? "" : hD.get("CAT_PART_NO").toString();
        String part_group_no = hD.get("PART_GROUP_NO") == null ? "" : hD.get("PART_GROUP_NO").toString();
        String item_desc = hD.get("ITEM_DESC") == null ? "" : hD.get("ITEM_DESC").toString();
        String stocking_method = hD.get("STOCKING_METHOD") == null ? "" : hD.get("STOCKING_METHOD").toString();
        String old_stocking_method = hD.get("OLD_STOCKING_METHOD") == null ? "" : hD.get("OLD_STOCKING_METHOD").toString();
        String reorder_point = hD.get("REORDER_POINT") == null ? "" : hD.get("REORDER_POINT").toString();
        String stocking_level = hD.get("STOCKING_LEVEL") == null ? "" : hD.get("STOCKING_LEVEL").toString();
        String look_ahead_days = hD.get("LOOK_AHEAD_DAYS") == null ? "" : hD.get("LOOK_AHEAD_DAYS").toString();
        String established_stock_flag = hD.get("ESTABLISHED_STOCK_FLAG") == null ? "" : hD.get("ESTABLISHED_STOCK_FLAG").toString();
        String inventoryGroup = hD.get("INVENTORY_GROUP") == null ? "" : hD.get("INVENTORY_GROUP").toString();
        String remarks = hD.get("REMARKS") == null ? "" : hD.get("REMARKS").toString();
        String updateLookaheadDays = hD.get("UPDATE_LOOK_AHEAD_DAYS") == null ? "" : hD.get("UPDATE_LOOK_AHEAD_DAYS").toString();
        String reorderQuantity = hD.get("REORDER_QUANTITY") == null ? "" : hD.get("REORDER_QUANTITY").toString();
        String operatingMethod = hD.get("RECEIPT_PROCESSING_METHOD") == null ? "" : hD.get("RECEIPT_PROCESSING_METHOD").toString();
        String billingMethod = hD.get("BILLING_METHOD") == null ? "" : hD.get("BILLING_METHOD").toString();
		  String catalog_company_id = hD.get("CATALOG_COMPANY_ID") == null ? "" : hD.get("CATALOG_COMPANY_ID").toString();

		  if (Line_Check.equalsIgnoreCase("yes")) {
          /*if (operatingMethod.trim().length() == 0 || billingMethod.trim().length() == 0)
              {
              errormesages+="Error updating levels.Please pick valid Receipt Processing Method and Billing Method.<BR>";
              result=false;
              }*/
          nonetoupdate = false;
          CallableStatement cs = null;
          try {
            Connection connect1 = null;
            connect1 = db.getConnection();
            cs = connect1.prepareCall("{call PKG_LEVEL_MANAGEMENT.PR_UPDATE_LEVELS_FOR_EXITMM(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            cs.setString(1, inventoryGroup);
            cs.setString(2, company_id);
            cs.setString(3, catalog_id);
            cs.setString(4, cat_part_no);
            cs.setString(5, part_group_no);
            cs.setString(6, old_stocking_method);
            cs.setString(7, stocking_method);
            if (reorder_point.trim().length() > 0 && !"OOR".equalsIgnoreCase(stocking_method)) {
              cs.setInt(8, Integer.parseInt(reorder_point));
            } else {
              cs.setNull(8, java.sql.Types.INTEGER);
            }
            if (stocking_level.trim().length() > 0 && !"OOR".equalsIgnoreCase(stocking_method)) {
              cs.setInt(9, Integer.parseInt(stocking_level));
            } else {
              cs.setNull(9, java.sql.Types.INTEGER);
            }
            if (look_ahead_days.trim().length() > 0) {
              cs.setInt(10, Integer.parseInt(look_ahead_days));
            } else {
              cs.setNull(10, java.sql.Types.INTEGER);
            }

            if (reorderQuantity.trim().length() > 0 && !"OOR".equalsIgnoreCase(stocking_method)) {
              cs.setInt(11, Integer.parseInt(reorderQuantity));
            } else {
              cs.setNull(11, java.sql.Types.INTEGER);
            }

            cs.setString(12, operatingMethod);
            cs.setString(13, billingMethod);

            if (logonid.trim().length() > 0) {
              cs.setInt(14, Integer.parseInt(logonid));
            } else {
              cs.setNull(14, java.sql.Types.INTEGER);
            }
            if (remarks.trim().length() > 0) {
              cs.setString(15, remarks);
            } else {
              cs.setNull(15, java.sql.Types.VARCHAR);
            }
            cs.registerOutParameter(16, java.sql.Types.VARCHAR);

				if (catalog_company_id.trim().length() > 0) {
              cs.setString(17, catalog_company_id);
            } else {
              cs.setNull(17, java.sql.Types.VARCHAR);
            }
				cs.execute();

            String errorcode = cs.getString(16);

            minmaxlog.info("Result from PKG_LEVEL_MANAGEMENT.PR_UPDATE_LEVELS_FOR_EXITMM procedure Error Code:  " + errorcode + " inventoryGroup  " + inventoryGroup + " Part Num  " + cat_part_no + "  billingMethod  " + billingMethod + " operatingMethod " + operatingMethod + " logonid " + logonid +
                           "");

            if (errorcode == null) {
              result = true;
            } else {
              errormesages += "Error updating levels. " + errorcode + "<BR>";
              result = false;
            }
          } catch (Exception e) {
            e.printStackTrace();
            radian.web.emailHelpObj.senddatabaseHelpemails(
                "Error Calling PKG_LEVEL_MANAGEMENT.PR_UPDATE_LEVELS_FOR_EXITMM in Min Max Level Change",
                "Error occured while running PKG_LEVEL_MANAGEMENT.PR_UPDATE_LEVELS_FOR_EXITMM \nError Msg:\n" +
                e.getMessage() + "\n\ninventoryGroup  " + inventoryGroup + " Part Num  " + cat_part_no + " Catalog Id =" + catalog_id + "  billingMethod  " + billingMethod + " \noperatingMethod " + operatingMethod + " logonid " + logonid + "");
            result = false;
          } finally {
            if (cs != null) {
              try {
                cs.close();
              } catch (SQLException se) {}
            }
          }

          if (updateLookaheadDays.equalsIgnoreCase("yes")) {
            try {
              Connection connect1 = null;
              connect1 = db.getConnection();
              cs = connect1.prepareCall("{call p_cpi_rli_allocate (?,?,?,?,?,?,?)}");

              cs.setString(1, company_id);
              cs.setString(2, catalog_id);
              cs.setString(3, cat_part_no);
              if (inventoryGroup.length() > 0) {
                cs.setString(4, inventoryGroup);
              } else {
                cs.setNull(4, java.sql.Types.VARCHAR);
              }
              if (part_group_no.length() > 0) {
                cs.setInt(5, Integer.parseInt(part_group_no));
              } else {
                cs.setNull(5, java.sql.Types.INTEGER);
              }
				  cs.setString(6,"Y");
				  if (catalog_company_id.length() > 0) {
                cs.setString(7, catalog_company_id);
              } else {
                cs.setNull(7, java.sql.Types.VARCHAR);
              }
				  cs.execute();
            } catch (Exception lokahead) {
              lokahead.printStackTrace();
              radian.web.emailHelpObj.sendnawazemail("Error Calling p_cpi_rli_allocate in PO Page ",
                  "Error Calling p_cpi_rli_allocate\nError Msg:\n" + lokahead.getMessage() + "\n  COMPANY_ID='" +
                  company_id + "' and CATALOG_ID='" + catalog_id + "' and CAT_PART_NO ='" +
                  cat_part_no + "'");
            } finally {
              if (cs != null) {
                try {
                  cs.close();
                } catch (Exception se) {
                  se.printStackTrace();
                }
              }
            }
          }
        }
      } //end of for
    } catch (Exception e) {
      result = false;
      e.printStackTrace();
      //minmaxout.println( radian.web.HTMLHelpObj.printError( "Min Max Level Change" ) );
    }

    if (nonetoupdate) {
      result = true;
    }
    return result;
  }
}