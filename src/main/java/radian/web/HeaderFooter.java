package radian.web;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import com.tcmis.common.admin.beans.PersonnelBean;
import javax.servlet.http.HttpSession;

/**
 * Title:        MSDS Viewer
 * Copyright:    Copyright (c) 2001
 * Company:      Radian
 * @author Nawaz Shaik
 * @version
 *
 * Use the methods in this class to get the header and footer information.
 * You can pass a title variable to add to the Title tag in the HEAD section
 * of the HTML
 *
 *  01-08-03
 *  var submitcount=0;
 * 07-16-03 - Added the client name to the title of the page
 * 08-18-03 - Added a http host fo the tcmIS.ico link so that the Icon shows up in the bookmark.
 * 01-16-05 - Projects link in the title bar
 * 03-03-05 - Link for Monthly Inventory Detail report
 * 06-22-05 - showing the logged in person's name on the header. Showing the help link only if a person is logged in
 */

public class HeaderFooter {
  private ServerResourceBundle bundle = null;
  private TcmISDBModel db = null;

  public HeaderFooter() {

  }

  public HeaderFooter(ServerResourceBundle b, TcmISDBModel d) {
    bundle = b;
    db = d;
  }

  public StringBuffer printHTMLHeader(String PageTitle, boolean intcmIsApplication) {
    StringBuffer MsgHH = new StringBuffer();

    MsgHH.append("<HTML>\n");
    MsgHH.append("<HEAD>\n");
    MsgHH.append("<TITLE>" + bundle.getString("DB_CLIENT") + " " + PageTitle + "</TITLE>\n");
    String httphost = radian.web.tcmisResourceLoader.gethttphosturl();
    MsgHH.append("<LINK REL=\"SHORTCUT ICON\" HREF=\"" + httphost + "images/buttons/tcmIS.ico\"></LINK>\n");
    MsgHH.append("<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=iso-8859-1\">\n");
    MsgHH.append("<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n");
    MsgHH.append("<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n");

    MsgHH.append("<SCRIPT SRC=\"/clientscripts/register.js\" LANGUAGE=\"JavaScript\">\n</SCRIPT>\n");
    if (intcmIsApplication) {
      MsgHH.append("<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"/css/clientpagesApplication.css\"></LINK>\n");
    } else {
      MsgHH.append("<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"/css/clientpages.css\"></LINK>\n");
    }

    /*This handels which key press events are disabeled on this page*/
    MsgHH.append("<script src=\"/js/common/disableKeys.js\" language=\"JavaScript\"></script>\n");
    /*This handels the menu style and what happens to the right click on the whole page */
    MsgHH.append("<script type=\"text/javascript\" src=\"/js/menu/milonic_src.js\"></script>\n");
    MsgHH.append("<script type=\"text/javascript\" src=\"/js/menu/mmenudom.js\"></script>\n");
    MsgHH.append("<script type=\"text/javascript\" src=\"/js/menu/mainmenudata.js\"></script>\n");
    MsgHH.append("<script type=\"text/javascript\" src=\"/js/menu/contextmenu.js\"></script>\n");

    MsgHH.append("<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n \n");
    MsgHH.append("<!--\n");
    MsgHH.append("var submitcount=0; \n");
    MsgHH.append("function openwin ()\n");
    MsgHH.append(" {\n");
    MsgHH.append(" MSDS_Help = window.open(\"" + bundle.getString("HELP_HTML_PATH") + "index.html\", \"MSDS_Help\",\n");
    MsgHH.append(" \"toolbar=no,location=no,directories=no,status=yes\" +\n");
    MsgHH.append(" \",menubar=no,scrollbars=yes,resizable=yes,\" +\n");
    MsgHH.append(" \"width=730,height=520\");\n");
    MsgHH.append(" }\n");
    MsgHH.append(" // -->\n");
    MsgHH.append("</SCRIPT>\n");

    return MsgHH;
  }

  public StringBuffer printTopToolBar(String NewDestination, String NavigationHelp) {
    return printTopToolBar(NewDestination, NavigationHelp, null);
  }

  public StringBuffer printTopToolBar(String NewDestination, String NavigationHelp,
                                      HttpSession loginSession) {
    StringBuffer MsgHB = new StringBuffer();
    MsgHB.append("</HEAD>\n");

    if ("".equalsIgnoreCase(NewDestination)) {
      MsgHB.append("<BODY TEXT=\"#000000\" LINK=\"#FFFFFF\" ALINK=\"\" VLINK=\"#FFFF66\">\n");
    } else {
      MsgHB.append(
          "<BODY TEXT=\"#000000\" LINK=\"#FFFFFF\" ALINK=\"#FFFF66\" VLINK=\"#FFFF66\" onload =\"" +
          NewDestination + "\">\n");
    }

    MsgHB.append("<div class=\"topNavigation\" id=\"topNavigation\">\n");
    MsgHB.append("<TABLE BORDER=0 WIDTH=100%>\n");
    MsgHB.append("<TR VALIGN=\"TOP\">\n");
    MsgHB.append("<TD WIDTH=\"200\">\n");
    MsgHB.append("<img src=\"/images/tcmtitlegif.gif\" border=0 align=\"left\">\n");
    MsgHB.append("</TD>\n");
    MsgHB.append("<TD ALIGN=\"right\">\n");
    MsgHB.append("<img src=\"/images/" + NavigationHelp + "\" border=0 align=\"right\">\n");
    MsgHB.append("</TD>\n");
    MsgHB.append("</TR>\n");
    MsgHB.append("</Table>\n");
    MsgHB.append("<TABLE WIDTH=\"100%\" BGCOLOR=\"#000066\" BORDER=\"0\">\n");
    MsgHB.append("<TR VALIGN=\"middle\">  \n");
    MsgHB.append("<TD WIDTH=\"60%\" HEIGHT=\"20\" BGCOLOR=#000066 VALIGN=\"MIDDLE\">\n");
    MsgHB.append("  &nbsp;&nbsp;&nbsp;<B><FONT FACE=\"Arial\" SIZE=\"2\" COLOR=\"#FFFFFF\">\n");

    String showHome = bundle.getString("SHOW_HOME") == null ? "" : bundle.getString("SHOW_HOME").toString();
    if (!"No".equalsIgnoreCase(showHome)) {
      MsgHB.append("  <A HREF=\"" + bundle.getString("WEBS_HOME_WWWS") + "" + bundle.getString("HOME_SERVLET") + "\">Home</A>\n");
      MsgHB.append("  |\n");
    }
    MsgHB.append("  <A HREF=\"" + bundle.getString("WEBS_HOME_WWWS") + "" + bundle.getString("BAR_BUILD_SERVLET") + "&noncatalog=yes\">SDS</A>\n");
    MsgHB.append("  |\n");

    String funllName = null;
    String personnelId = null;
    try {
      personnelId = loginSession.getAttribute("PERSONNELID") == null ? "" :
          loginSession.getAttribute("PERSONNELID").toString();
      funllName = loginSession.getAttribute("FULLNAME") == null ? "&nbsp;" :
          loginSession.getAttribute("FULLNAME").toString();
    } catch (Exception ex) {
      funllName = "";
      personnelId = "";
    }

    String showGui = bundle.getString("SHOW_GUI") == null ? "" : bundle.getString("SHOW_GUI").toString();
    if (!"No".equalsIgnoreCase(showGui)) {
      MsgHB.append("  <A HREF=\"" + bundle.getString("WEBS_HOME_WWWS") + "" + bundle.getString("REGISTER_SERVLET") + "\">tcmIS</A>\n");
      MsgHB.append("  |\n");
    }

    String droprec = bundle.getString("DROPRECEIVING") == null ? "" : bundle.getString("DROPRECEIVING").toString();
    if ("Yes".equalsIgnoreCase(droprec)) {
      MsgHB.append("  <A HREF=\"" + bundle.getString("WEBS_HOME_WWWS") + "" + bundle.getString("DROPRECEIVING_SERVLET_HEADER") + "\">Receiving</A>\n");
      MsgHB.append("  |\n");
    }

    String receiptDocument = bundle.getString("RECEIPT_DOCUMENT") == null ? "" : bundle.getString("RECEIPT_DOCUMENT").toString();
    if ("Yes".equalsIgnoreCase(receiptDocument)) {
      MsgHB.append("  <A HREF=\"" + bundle.getString("WEBS_HOME_WWWS") + "" + bundle.getString("RECEIPT_DOCUMENT_SERVLET") + "\">Receiving Documents</A>\n");
      MsgHB.append("  |\n");
    }

    //String relabeling = bundle.getString( "RELABELING" );
    String relabeling = bundle.getString("RELABELING") == null ? "" : bundle.getString("RELABELING").toString();
    if ("Yes".equalsIgnoreCase(relabeling)) {
      MsgHB.append("  <A HREF=\"" + bundle.getString("WEBS_HOME_WWWS") + "" + bundle.getString("RELABELING_SERVLET") + "\">ReLabel</A>\n");
      MsgHB.append("  |\n");
    }

    String shipinginfo = bundle.getString("SHIPPINGINFO") == null ? "" : bundle.getString("SHIPPINGINFO").toString();
    if ("Yes".equalsIgnoreCase(shipinginfo)) {
      MsgHB.append("  <A HREF=\"" + bundle.getString("WEBS_HOME_WWWS") + "" + bundle.getString("SHIPPINGINFO_SERVLET_HEADER") + "\">Shipping Info</A>\n");
      MsgHB.append("  |\n");
    }

    String cabinetinventory = bundle.getString("CABINET_INVENTORY") == null ? "" : bundle.getString("CABINET_INVENTORY").toString();
    if ("Yes".equalsIgnoreCase(cabinetinventory)) {
      MsgHB.append("  <A HREF=\"" + bundle.getString("WEBS_HOME_WWWS") + "" + bundle.getString("CABINET_INVENTORY_SERVLET") + "\">Cabinet Inventory</A>\n");
      MsgHB.append("  |\n");
    }

    String monthlyInventoryDetailPage = bundle.getString("MONTHLY_INVENTORY_DETAIL") == null ? "" : bundle.getString("MONTHLY_INVENTORY_DETAIL").toString();
    if ("Yes".equalsIgnoreCase(monthlyInventoryDetailPage)) {
      MsgHB.append("  <A HREF=\"" + bundle.getString("WEBS_HOME_WWWS") + "" + bundle.getString("MONTHLY_INVENTORY_DETAIL_SERVLET") + "\">Monthly Inventory Detail</A>\n");
      MsgHB.append("  |\n");
    }

    String projectspage = bundle.getString("PROJECTS") == null ? "" : bundle.getString("PROJECTS").toString();
    if ("Yes".equalsIgnoreCase(projectspage)) {
      MsgHB.append("  <A HREF=\"" + bundle.getString("WEBS_HOME_WWWS") + "" + bundle.getString("PROJECTS_SERVLET") + "\">Projects</A>\n");
      MsgHB.append("  |\n");
    }

    String openPosPage = bundle.getString("OPEN_POS") == null ? "" : bundle.getString("OPEN_POS").toString();
    if ("Yes".equalsIgnoreCase(openPosPage)) {
      MsgHB.append("  <A HREF=\"" + bundle.getString("WEBS_HOME_WWWS") + "" + bundle.getString("OPEN_POS_SERVLET") + "\">Open POs</A>\n");
      MsgHB.append("  |\n");
    }

    String webCatalogPage = bundle.getString("WEB_CATALOG") == null ? "" : bundle.getString("WEB_CATALOG").toString();
    if ("Yes".equalsIgnoreCase(webCatalogPage)) {
      MsgHB.append("  <A HREF=\"" + bundle.getString("WEBS_HOME_WWWS") + "" + bundle.getString("WEB_CATALOG_SERVLET") + "\">Catalog</A>\n");
      MsgHB.append("  |\n");
    }

    String webOrderTrackingPage = bundle.getString("WEB_ORDER_TRACKING") == null ? "" : bundle.getString("WEB_ORDER_TRACKING").toString();
    if ("Yes".equalsIgnoreCase(webOrderTrackingPage)) {
      MsgHB.append("  <A HREF=\"" + bundle.getString("WEBS_HOME_WWWS") + "" + bundle.getString("WEB_ORDER_TRACKING_SERVLET") + "\">Order Tracking</A>\n");
      MsgHB.append("  |\n");
    }

    String webInventoryPage = bundle.getString("WEB_INVENTORY") == null ? "" : bundle.getString("WEB_INVENTORY").toString();
    if ("Yes".equalsIgnoreCase(webInventoryPage)) {
      MsgHB.append("  <A HREF=\"" + bundle.getString("WEBS_HOME_WWWS") + "" + bundle.getString("WEB_INVENTORY_SERVLET") + "\">Inventory</A>\n");
      MsgHB.append("  |\n");
    }

    String webScaqmdReport = bundle.getString("WEB_SCAQMD_REPORT") == null ? "" : bundle.getString("WEB_SCAQMD_REPORT").toString();
    if ("Yes".equalsIgnoreCase(webScaqmdReport)) {
      MsgHB.append("  <A HREF=\"" + bundle.getString("WEBS_HOME_WWWS") + "" + bundle.getString("WEB_SCAQMD_REPORT_SERVLET") + "\">SCAQMD Report</A>\n");
      MsgHB.append("  |\n");
    }

    String workareaManagement = bundle.getString("WORKAREA_MANAGEMENT") == null ? "" : bundle.getString("WORKAREA_MANAGEMENT").toString();
    if ("Yes".equalsIgnoreCase(workareaManagement)) {
      MsgHB.append("  <A HREF=\"" + bundle.getString("WEBS_HOME_WWWS") + "" + bundle.getString("WORKAREA_MANAGEMENT_SERVLET") + "\">Workarea Management</A>\n");
      MsgHB.append("  |\n");
    }

    String workareaMaterialsUsed = bundle.getString("MATERIALS_USED") == null ? "" : bundle.getString("MATERIALS_USED").toString();
    if ("Yes".equalsIgnoreCase(workareaMaterialsUsed)) {
      MsgHB.append("  <A HREF=\"" + bundle.getString("WEBS_HOME_WWWS") + "" + bundle.getString("MATERIALS_USED_SERVLET") + "\">Work Area Materials Used</A>\n");
      MsgHB.append("  |\n");
    }

    String tsdf = bundle.getString("TSDF") == null ? "" : bundle.getString("TSDF").toString();
    if ("Yes".equalsIgnoreCase(tsdf)) {
      MsgHB.append("  <A HREF=\"" + bundle.getString("WEBS_HOME_WWWS") + "" + bundle.getString("TSDF_RECEIVING_SERVLET") + "\">TSDF Waste Receiving</A>\n");
      MsgHB.append("  |\n");
      MsgHB.append("  <A HREF=\"" + bundle.getString("WEBS_HOME_WWWS") + "" + bundle.getString("TSDF_CONTAINER_REPORT_SERVLET") + "\">TSDF Container Report</A>\n");
      MsgHB.append("  |\n");
      MsgHB.append("  <A HREF=\"" + bundle.getString("WEBS_HOME_WWWS") + "" + bundle.getString("WASTE_MANIFEST_REPORT_SERVLET") + "\">Sub-manifest Report</A>\n");
      MsgHB.append("  |\n");
    }

    if (loginSession != null) {
      PersonnelBean personnelBean = loginSession.getAttribute("personnelBean") == null ? null : (PersonnelBean) loginSession.getAttribute("personnelBean");
      if (personnelBean != null && (personnelBean.getPermissionBean().hasFacilityPermission("customerPurchasing", null, null) || personnelBean.getPermissionBean().hasFacilityPermission("readonlyCustomerPurchasing", null, null))) {
        String purchaseRequest = bundle.getString("WEB_PURCHASE_REQUESTS") == null ? "" : bundle.getString("WEB_PURCHASE_REQUESTS").toString();
        if ("Yes".equalsIgnoreCase(purchaseRequest)) {
          MsgHB.append("  <A HREF=\"" + bundle.getString("WEBS_HOME_WWWS") + "" + bundle.getString("WEB_PURCHASE_REQUESTS_SERVLET") + "\">Purchase Requests</A>\n");
          MsgHB.append("  |\n");
        }

        String purchaseOrders = bundle.getString("WEB_PURCHASE_ORDERS") == null ? "" : bundle.getString("WEB_PURCHASE_ORDERS").toString();
        if ("Yes".equalsIgnoreCase(purchaseOrders)) {
          MsgHB.append("  <A HREF=\"" + bundle.getString("WEBS_HOME_WWWS") + "" + bundle.getString("WEB_PURCHASE_ORDERS_SERVLET") + "\">Purchase Orders</A>\n");
          MsgHB.append("  |\n");
        }
      }

      String showHelp = bundle.getString("SHOW_HELP") == null ? "" : bundle.getString("SHOW_HELP").toString();
      if (!"No".equalsIgnoreCase(showHelp)) {
        MsgHB.append("  <A HREF=\"javascript:openwin();\">Help</A>\n");
      }
      MsgHB.append("  </FONT></B>\n</TD>\n");
      MsgHB.append("<TD WIDTH=\"40%\" HEIGHT=\"20\" BGCOLOR=#000066 VALIGN=\"MIDDLE\" ALIGN=\"RIGHT\">\n");
      MsgHB.append("<FONT FACE=\"Arial\" SIZE=\"2\" COLOR=\"#FFFFFF\"><B>Logged in as:</B>&nbsp;" + funllName + "\n</FONT>\n");
    }

    MsgHB.append("</FONT></TD>\n");
    MsgHB.append("</TR>\n");
    MsgHB.append("</TABLE>\n");
    MsgHB.append("</div>");
    return MsgHB;
  }

  public StringBuffer printFooter() {
    StringBuffer MsgPF = new StringBuffer();

    /*MsgPF.append( "<TABLE WIDTH=\"725\" BORDER=\"0\">\n" );
        MsgPF.append( "<TR VALIGN=\"middle\">\n" );
        MsgPF.append( "<TD WIDTH=\"725\" HEIGHT=\"20\">\n" );
        MsgPF.append( "<CENTER><B><FONT FACE=\"Arial\" SIZE=\"2\">\n" );
        MsgPF.append( "  <A HREF=\"" + bundle.getString( "WEBS_HOME_WWWS" ) + "" + bundle.getString( "HOME_PAGE" ) + "\" STYLE=\"color:#000000\">Home</A>\n" );
        MsgPF.append( "  |\n" );
        MsgPF.append( "  <A HREF=\"" + bundle.getString( "WEBS_HOME_WWWS" ) + "" + bundle.getString( "REGISTER_SERVLET" ) + "\" STYLE=\"color:#000000\">tcmIS</A>\n" );
        MsgPF.append( "  |\n" );
        MsgPF.append( "  <A HREF=\"" + bundle.getString( "WEBS_HOME_WWWS" ) + "" + bundle.getString( "BAR_BUILD_SERVLET" ) + "&noncatalog=yes\" STYLE=\"color:#000000\">SDS</A>\n" );
        MsgPF.append( "  |\n" );
        MsgPF.append( "  <A HREF=\"javascript:openwin();\" STYLE=\"color:#000000\">Help</A>\n" );
        MsgPF.append( "</FONT></B></CENTER>\n" );
        MsgPF.append( "</TD>\n" );
        MsgPF.append( "</TR>\n" );
        MsgPF.append( "</TABLE>\n" );
        MsgPF.append( "</BODY>\n" );
        MsgPF.append( "</HTML>\n" );*/

    return MsgPF;
  }

  public StringBuffer printclientLoginScreen(String errmsg1, String ScreenName, String helpimagename, boolean intcmIsApplication) {
    StringBuffer body = new StringBuffer("");
    body.append(this.printHTMLHeader(ScreenName, intcmIsApplication));
    body.append("<script type=\"text/javascript\" src=\"/js/common/sessiontimeout.js\"></script>\n");
    body.append("</HEAD>  \n");
    body.append("<BODY BGCOLOR=\"#FFFFFF\" onload=\"sessionLogout()\" TEXT=\"#000000\">  \n");
    body.append(this.printTopToolBar("", helpimagename));
    body.append("<form method=\"POST\" name=\"form1\">\n");
    body.append("<TABLE WIDTH=\"150\" BORDER=\"0\" CLASS=\"moveup\">\n");
    body.append("<TR VALIGN=\"TOP\"><TD BGCOLOR=\"#E6E8FA\" HEIGHT=\"400\">\n");
    body.append("<TABLE WIDTH=\"150\" BORDER=\"0\" CLASS=\"moveup\">\n");
    body.append("<tr>\n");
    body.append("<td width=\"5%\" CLASS=\"announce\" ALIGN=\"RIGHT\" ><B>Logon Id : </B></td>\n");
    body.append("<td width=\"95%\" ALIGN=\"LEFT\" ><input CLASS=\"HEADER\" type=\"text\" name=\"loginId\" value=\"\" size=\"10\"></td>\n");
    body.append("</tr><tr>\n");
    body.append("<td width=\"5%\" CLASS=\"announce\" ALIGN=\"RIGHT\" ><B>Password : </B></td>\n");
    body.append("<td width=\"95%\" ALIGN=\"LEFT\" ><input CLASS=\"HEADER\" type=\"password\" name=\"passwd\" value=\"\" size=\"10\"></td>\n");
    body.append("</tr><tr>\n");
    body.append("<td width=\"5%\" align=\"right\"><input CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" type=\"submit\" value=\"Login\" name=\"Login\"></td>\n");
    body.append("<td width=\"95%\" align=\"left\"><input type=\"reset\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" value=\"Reset\" name=\"Reset\"></td>\n");
    body.append("</tr>\n");
    body.append("<TR><TD>&nbsp;</TD></TR><TR><TD>&nbsp;</TD></TR><TR><TD COLSPAN=\"2\" CLASS=\"bluel\"><FONT SIZE=\"2\">" + errmsg1 + "</FONT></TD></TR></form></TABLE></TD></TR></TABLE>\n");
    body.append("</body></html>");
    return body;
  }
}
