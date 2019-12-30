package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.tcmis.common.admin.beans.PersonnelBean;
import java.util.Collection;
import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;
import radian.tcmis.server7.share.dbObjs.Facility;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.web.HeaderFooter;
import com.tcmis.common.util.ResourceLibrary;
import java.util.Locale;

/**
 * <p>Title: All the code</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Haas TCM</p>
 * @author Nawaz Shaik
 * @version 1.0
 *
 * 03-17-03
 * Showing All three options for all clients
 * 04-07-03 - Fixing a null pointer exception
 * 07-14-03 - Code Cleanup
 * 01-26-04 - Making Search Button Visible in Netscape
 * 06-22-05 - Showing the help link only if a person is logged in
 */
public abstract class BuildSearchPageNew extends HttpServlet implements SingleThreadModel {
  //Initialize global variables
  /*private String checked = "";
       private String limit = "";
       private String item = "";
       private String recs = "";
       private String sortby = "";
       private String fac = "";
       private String from = "";
       private String interval = "";
       private String window = "";
       private String headers = "";
       private String msdsurl = "";
       private String client = "";
       private String nonCatalogue = "";
       private String msdsDatabase = "";
       private String checkednon = "";
       private String checkedmsds = "";*/
  private boolean intcmIsApplication = false;
  private ResourceLibrary res = null; // res means resource.

  public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }

  //Process the HTTP Post request
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doPost(request, response);
  }

  //Process the HTTP Get request
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String session_id = "T";
    String client = getBundle().getString("DB_CLIENT");
    HttpSession Session = request.getSession();
    res =  new ResourceLibrary("com.tcmis.common.resources.CommonResources", (Locale)request.getLocale());
    TcmISDBModel db = null;
    Hashtable data = null;
    Hashtable Count_Data = new Hashtable();
    Hashtable ResultDdata = new Hashtable();

    PersonnelBean personnelBean = (PersonnelBean) Session.getAttribute("personnelBean");
    intcmIsApplication = false;
    if (personnelBean != null) {
      Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
      if (menuItemOvBeanCollection != null && menuItemOvBeanCollection.size() > 0) {
        intcmIsApplication = true;
      }
    }

    String istcmIsApplication = "";
    istcmIsApplication = request.getParameter("istcmIsApplication");
    if (istcmIsApplication == null) {
      istcmIsApplication = "";
    }
    if (istcmIsApplication.equalsIgnoreCase("Y")) {
      intcmIsApplication = true;
    }

    String[] All = {
        "MATERIAL_ID", "FAC_ITEM_ID", "TRADE_NAME", "MFG_DESC", "CATALOG_ID"};
    String[] Rest = {
        "MATERIAL_ID", "FAC_ITEM_ID", "TRADE_NAME", "MFG_DESC", "FACILITY_ID"};

    Vector dataV = new Vector();
    String Test_Count = "";
    session_id = request.getParameter("Session_ID");
    if (session_id == null) {
      session_id = "New";

    }
    String Second = "";
    Second = request.getParameter("Second");
    if (Second == null) {
      Second = "";

    }
    String User_Action = request.getParameter("UserAction");
    if (User_Action == null) {
      User_Action = "New";

    }
    String client1 = getBundle().get("DB_CLIENT_NAME").toString();
    db = new RayTcmISDBModel(client1,"2",radian.web.HTMLHelpObj.getTcmISLocaleString(request));

    if (session_id.equalsIgnoreCase("New")) {
      try {
        Facility fac = new Facility(db);
        dataV = fac.getAllFacXRef2();
        Count_Data = fac.getCataloguCount();
      } catch (Exception e) {
        e.printStackTrace();
      }

      session_id = Count_Data.get("SESSION_IDN").toString();
      Test_Count = Count_Data.get("COUNT").toString();

      Session.setAttribute("" + session_id + "Facilitys", dataV);
      Session.setAttribute("" + session_id + "Test_Count", Count_Data.get("COUNT"));
    } else {
      dataV = (Vector) Session.getAttribute("" + session_id + "Facilitys");
      String testCount = (String) Session.getAttribute("" + session_id + "Test_Count");
      if (testCount == null) {
        testCount = "";
      }
      Test_Count = testCount;
    }

    String nonCatalogue = "";
    nonCatalogue = request.getParameter("noncatalog");
    if (nonCatalogue == null) {
      nonCatalogue = "";

    }
    String msdsDatabase = "";
    msdsDatabase = request.getParameter("msdsdataabase");
    if (msdsDatabase == null) {
      msdsDatabase = "";

    }
    String TestFac = "";
    TestFac = request.getParameter("TestFac");
    if (TestFac == null) {
      TestFac = "";

    }
    String limit = "";
    limit = request.getParameter("limit");
    if (limit == null) {
      limit = "No";

    }
    String checkednon = "";
    String checkedmsds = "";
    if (nonCatalogue.equalsIgnoreCase("yes")) {
      checkednon = "checked";
    } else {
      checkednon = "No";
    }
    if (msdsDatabase.equalsIgnoreCase("yes")) {
      checkedmsds = "checked";
    } else {
      checkedmsds = "No";
    }

    String recs = "";
    recs = request.getParameter("recs");
    if (recs == null) {
      recs = "";

    }
    String interval = "";
    interval = request.getParameter("interval");
    if (interval == null) {
      interval = "";

    }
    String item = "";
    item = request.getParameter("item");
    if (item == null) {
      item = "NAWAZ!";

    }
    String from = "";
    from = request.getParameter("from");
    if (from == null) {
      from = "0";

    }
    String checked = "";
    if (limit == null) {
      limit = "";
    }
    if (limit.equalsIgnoreCase("yes")) {
      checked = "checked";
    } else {
      checked = "";
    }

    String sortbyIndex = "";
    sortbyIndex = request.getParameter("sortby");
    if (sortbyIndex == null) {
      sortbyIndex = "0";

    }
    String fac = "";
    fac = request.getParameter("fac");
    if (fac == null) {
      fac = "Nu";

    }
    String sortby = "";
    if (sortbyIndex.trim().length() == 1) {
      if ("All".equalsIgnoreCase(fac)) {
        sortby = All[Integer.parseInt(sortbyIndex)];
      } else {
        sortby = Rest[Integer.parseInt(sortbyIndex)];
      }
    } else {
      sortby = sortbyIndex;
    }

    String headers = "";
    headers = request.getParameter("headers");
    if (headers == null) {
      headers = "";

    }
    String window = "";
    window = request.getParameter("window");
    if (window == null) {
      window = "";

    }
    String msdsurl = "";
    msdsurl = request.getParameter("msdsurl");
    if (msdsurl == null) {
      msdsurl = "";

    }
    response.setContentType("text/html");
    //StringBuffer Msg1=new StringBuffer();
    PrintWriter msdsout = new PrintWriter(response.getOutputStream());
    String MsgBN = "";
    String MsgB = "Help";

    //If the search string is NAWAZ! then no search will be performed
    if ( (checkednon.equalsIgnoreCase("checked")) || (checkedmsds.equalsIgnoreCase("checked")) || (checked.equalsIgnoreCase("checked"))) {
      if (! ("NAWAZ!".equalsIgnoreCase(item))) {
        Hashtable SearchDdata = new Hashtable();
        SearchDdata.put("Session_ID", session_id);
        SearchDdata.put("noncatalog", nonCatalogue);
        SearchDdata.put("msdsdataabase", msdsDatabase);
        SearchDdata.put("TestFac", TestFac);
        SearchDdata.put("limit", limit);
        SearchDdata.put("recs", recs);
        SearchDdata.put("interval", interval);
        SearchDdata.put("item", item);
        SearchDdata.put("from", from);
        SearchDdata.put("sortby", sortby);
        SearchDdata.put("fac", fac);
        SearchDdata.put("headers", headers);
        SearchDdata.put("window", window);
        SearchDdata.put("msdsurl", msdsurl);
        SearchDdata.put("Second", Second);

        SearchMsdsNew USgovSE = new SearchMsdsNew(getBundle(), db);
        ResultDdata = USgovSE.GetResult(SearchDdata, Test_Count, Session, istcmIsApplication,res);

        try {
          MsgBN = (String) ResultDdata.get("MSG_BACK_NEXT");
          MsgB = (String) ResultDdata.get("MSG_BODY");
        } catch (Exception e1d2) {
          e1d2.printStackTrace();
          MsgBN = "&nbsp;";
          MsgB = "&nbsp;";
        }
      }
    } else {
      if ("Search".equalsIgnoreCase(User_Action)) {
        MsgB = res.getString("label.catalogpleasecheck")+"<BR><BR><BR><BR><BR><BR><BR>\n";
      }
    }

    HeaderFooter hf = new HeaderFooter(getBundle(), db);
    //HEAD
    msdsout.println(hf.printHTMLHeader(res.getString("title.msdsviewer"), intcmIsApplication));
    //Top Tool Bars
    printJavaScripts(msdsout);

    msdsout.println("<SCRIPT SRC=\"/clientscripts/msdsviewer.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
    if (!"Internal".equalsIgnoreCase(client)) {
      msdsout.println(hf.printTopToolBar("resetApplicationTimer()", "tcmisMsds.gif", Session));
    } else {
      msdsout.println("<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"/stylesheets/global.css\"></LINK>\n");
      msdsout.println("<BODY onload=\"resetApplicationTimer()\" >\n");
      //msdsout.println(radian.web.HTMLHelpObj.PrintTitleBarNolink("MSDS Viewer\n"));
      msdsout.append("<div class=\"topNavigation\" id=\"topNavigation\">\n");
      msdsout.append("<TABLE BORDER=0 WIDTH=100% CLASS=\"\">\n");
      msdsout.append("<TR VALIGN=\"TOP\">\n");
      msdsout.append("<TD WIDTH=\"200\">\n");
      msdsout.append("<img src=\"/images/tcmtitlegif.gif\" border=0 align=\"left\">\n");
      msdsout.append("</TD>\n");
      msdsout.append("<TD ALIGN=\"right\">\n");
      msdsout.append("<img src=\"/images/tcmistcmis32.gif\" border=0 align=\"right\">\n");
      msdsout.append("</TD>\n");
      msdsout.append("</TR>\n");
      msdsout.append("</Table>\n");
      msdsout.append("<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"0\" WIDTH=\"100%\" CLASS=\"\">\n");

      msdsout.append("<TR><TD WIDTH=\"70%\" ALIGN=\"LEFT\" HEIGHT=\"22\" CLASS=\"heading\">\n");
      msdsout.append(res.getString("title.msdsviewer"));
      msdsout.append("</TD>\n");
      msdsout.append("<TD WIDTH=\"30%\" ALIGN=\"RIGHT\" HEIGHT=\"22\" CLASS=\"headingr\">\n");
      msdsout.append("</TD>\n");

      msdsout.append("</TR>\n");
      msdsout.append("</TABLE>\n");
      msdsout.append("</div>");
    }

		if ( !"Search".equalsIgnoreCase(User_Action) && !(checkednon.equalsIgnoreCase("checked") || checkedmsds.equalsIgnoreCase("checked") || checked.equalsIgnoreCase("checked") )) {
			 checked = "checked";
			 checkednon = "checked";
	  }

    printHead(data, dataV, Test_Count, session_id, MsgBN, fac, recs, sortby, item, checked, checkednon, checkedmsds, msdsout, istcmIsApplication);

    if (!"Search".equalsIgnoreCase(User_Action)) {
      msdsout.println("<TABLE BORDER=0 WIDTH=725><TR><TD>\n");
      msdsout.println("<B><FONT SIZE=\"3\" FACE=ARIAL>"+res.getString("label.instructions")+":</FONT></B><P>\n");
      msdsout.println("<UL>\n");
      msdsout.println("<LI><FONT FACE=ARIAL SIZE=2 COLOR=990000><B>"+res.getString("label.search")+"\n");
      msdsout.println("-</B></FONT>"+res.getString("msds.msg11"));
      msdsout.println(res.getString("msds.msg12"));
      msdsout.println(res.getString("msds.msg13"));
      msdsout.println(res.getString("msds.msg14"));
      msdsout.println(res.getString("msds.msg15")+"<P>\n");
      if (! (Test_Count.equalsIgnoreCase("1") || client.equalsIgnoreCase("Internal"))) {
          msdsout.println("<LI><FONT FACE=ARIAL SIZE=2 COLOR=009900><B>"+res.getString("label.facility")+" -\n");
          msdsout.println("</B></FONT>"+res.getString("msds.msg22"));
          msdsout.println(res.getString("msds.msg23")+"<P>\n");
      }
      msdsout.println("<LI><FONT FACE=ARIAL SIZE=2 COLOR=000000><B>"+res.getString("label.sort")+" -\n");
      msdsout.println("</B></FONT>"+res.getString("msds.msg32"));
      msdsout.println(res.getString("msds.msg33")+"<P></UL>\n");
      msdsout.println("</CENTER></TD></TR>\n");
      msdsout.println("</TABLE><P> </P><P> </P>\n");
    } else {
      msdsout.println(MsgB);
    }

    msdsout.println(hf.printFooter());

    //out.println( Msg1 );
    db.close();
    msdsout.close();
  }

  //Get Servlet information
  public String getServletInfo() {
    return "radian.web.servlets.share.BuildSearchPage Information";
  }

  public static String Remove20from(String s) {
    StringTokenizer st = new StringTokenizer(s, "%20");

    String result = "";
    if (st.countTokens() > 1) {
      while (st.hasMoreTokens()) {
        String tok = st.nextToken();
        result += tok + " ";
      }
      //removing the last space
      result = result.substring(0, result.length() - 1);
    } else {
      result = s;
    }
    return result;
  }

  protected void printHead(Hashtable data, Vector dataV, String Testcount, String Session_ID, String BackNext, String fac, String recs,
                           String sortby, String item, String checked, String checkednon, String checkedmsds, PrintWriter msdsout, String istcmIsApplication) {

    //StringBuffer Msg=new StringBuffer();
    fac = this.Remove20from(fac);
    String client = getBundle().getString("DB_CLIENT");

    int r = 0;
    try {
      r = Integer.parseInt(recs);
    } catch (Exception e2) {
      r = 0;
    }

    msdsout.println("<FORM METHOD=\"POST\" NAME=\"form1\" ACTION=\"" + getBundle().getString("BUILD_SERVLET") + "\"> \n");
    msdsout.println("<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\"Search\">\n");
    msdsout.println("<TABLE BORDER=0 WIDTH=725 CELLPADDING=0 cellspacing=0 BGCOLOR=\"#fff0e1\">\n");
    msdsout.println(" <TR VALIGN=\"MIDDLE\">\n");

    //Facitiy Drop Box
    if (! (Testcount.equalsIgnoreCase("1") || client.equalsIgnoreCase("Seagate") || client.equalsIgnoreCase("Internal"))) {
      msdsout.println("<TD HEIGHT=45 WIDTH=\"33%\" ALIGN=\"CENTER\">\n");
      msdsout.println("<FONT SIZE=\"2\" FACE=\"Arial\"><B>"+res.getString("label.facility")+":</B></FONT>&nbsp; \n");
      msdsout.println("<SELECT NAME=\"fac\" onChange=\"reshow(document.form1.fac)\" size=\"1\">\n");
      msdsout.println("<OPTION " + (fac.equalsIgnoreCase("All") ? "selected" : "") + " VALUE=\"All\">"+res.getString("label.allfacilities")+"</OPTION>\n");
      for (int i = 0; i < dataV.size(); i++) {
        data = (Hashtable) dataV.elementAt(i);
        Enumeration E;
        for (E = data.keys(); E.hasMoreElements(); ) {
          String key = (String) E.nextElement();
          msdsout.println("<OPTION " + (fac.equalsIgnoreCase(key) ? "selected" : "") + " VALUE=\"" + BothHelpObjs.addSpaceForUrl(key) + "\">" + data.get(key).toString() + "</OPTION>\n");
        }
      }
      msdsout.println("</SELECT>\n");
      msdsout.println("</TD>\n");
    } else if (Testcount.equalsIgnoreCase("1")) {
      msdsout.println("<TD HEIGHT=45 ALIGN=\"CENTER\">\n");
      msdsout.println("<INPUT TYPE=\"hidden\" NAME=\"fac\" VALUE=\"All\">\n");
      msdsout.println("</TD>\n");
    } else {
      msdsout.println("<TD HEIGHT=45 ALIGN=\"CENTER\">\n");
      msdsout.println("&nbsp;");
      msdsout.println("</TD>\n");
    }

    //Sort By
    if (! (Testcount.equalsIgnoreCase("1") || client.equalsIgnoreCase("Seagate"))) {
      msdsout.println("<TD HEIGHT=45 WIDTH=\"33%\" nowrap=\"nowrap\">\n");
    } else {
      msdsout.println("<TD HEIGHT=45 WIDTH=\"45%\" nowrap=\"nowrap\">\n");
    }
    msdsout.println("<FONT SIZE=\"2\" FACE=\"Arial\" COLOR=\"#000000\">&nbsp;<B>"+res.getString("label.sortby")+":</B></FONT>&nbsp;\n");
    msdsout.println("<SELECT CLASS=\"HEADER\" NAME=\"sortby\" size=\"1\"> \n");

    if (client.equalsIgnoreCase("Internal")) {
      msdsout.println("  <OPTION " + (sortby.equalsIgnoreCase("MATERIAL_ID") ? "selected" : "") + " VALUE=\"0\">"+res.getString("label.material")+"</OPTION>\n");
      msdsout.println("  <OPTION " + (sortby.equalsIgnoreCase("TRADE_NAME") ? "selected" : "") + " VALUE=\"2\">"+res.getString("label.tradename")+"</OPTION>\n");
      msdsout.println("  <OPTION " + (sortby.equalsIgnoreCase("MFG_DESC") ? "selected" : "") + " VALUE=\"3\">"+res.getString("label.manufacturer")+"Manufacturer</OPTION>\n");
    } else {
      msdsout.println("  <OPTION " + (sortby.equalsIgnoreCase("MATERIAL_ID") ? "selected" : "") + " VALUE=\"0\">"+res.getString("label.material")+"</OPTION>\n");
      msdsout.println("  <OPTION " + (sortby.equalsIgnoreCase("FAC_ITEM_ID") ? "selected" : "") + " VALUE=\"1\">"+res.getString("label.partnumber")+"</OPTION>\n");
      msdsout.println("  <OPTION " + (sortby.equalsIgnoreCase("TRADE_NAME") ? "selected" : "") + " VALUE=\"2\">"+res.getString("label.tradename")+"</OPTION>\n");
      msdsout.println("  <OPTION " + (sortby.equalsIgnoreCase("MFG_DESC") ? "selected" : "") + " VALUE=\"3\">"+res.getString("label.manufacturer")+"</OPTION>\n");

      if (! (Testcount.equalsIgnoreCase("1") || client.equalsIgnoreCase("Seagate"))) {
        if ( (fac.equalsIgnoreCase("All")) && (sortby.equalsIgnoreCase("CATALOG_ID"))) {
          msdsout.println("  <OPTION " + (sortby.equalsIgnoreCase("CATALOG_ID") ? "selected" : "") + " VALUE=\"4\">"+res.getString("label.catalog")+"</OPTION>\n");
        } else if (fac.equalsIgnoreCase("All")) {
          msdsout.println("  <OPTION VALUE=\"4\">"+res.getString("label.catalog")+"</OPTION>\n");
        } else if (fac.equalsIgnoreCase("Nu")) {
          msdsout.println("  <OPTION " + (sortby.equalsIgnoreCase("CATALOG_ID") ? "selected" : "") + " VALUE=\"4\">"+res.getString("label.catalog")+"</OPTION>\n");
        } else if (! (fac.equalsIgnoreCase("All")) && (sortby.equalsIgnoreCase("FACILITY_ID"))) {
          msdsout.println("  <OPTION " + (sortby.equalsIgnoreCase("FACILITY_ID") ? "selected" : "") + " VALUE=\"4\">"+res.getString("label.facility")+"</OPTION>\n");
        } else if (! (fac.equalsIgnoreCase("All"))) {
          msdsout.println("  <OPTION " + (sortby.equalsIgnoreCase("FACILITY_ID") ? "selected" : "") + " VALUE=\"4\">"+res.getString("label.facility")+"</OPTION>\n");
        }
      }
    }

    msdsout.println("</SELECT>\n");
    msdsout.println("</TD>\n");

    if (! (Testcount.equalsIgnoreCase("1") || client.equalsIgnoreCase("Seagate"))) {
      msdsout.println(" <TD HEIGHT=45 WIDTH=\"3%\">&nbsp;\n");
    } else {
      msdsout.println(" <TD HEIGHT=45 WIDTH=\"20%\">&nbsp;\n");
    }
    msdsout.println(" </TD>\n");

    msdsout.println("  <TD HEIGHT=45 WIDTH=\"3%\" VALIGN=\"MIDDLE\" ALIGN=\"RIGHT\">\n");
    msdsout.println("<INPUT TYPE=\"submit\" VALUE=\""+res.getString("label.search")+"\" NAME=\"B1\">&nbsp;&nbsp;\n");
    msdsout.println("</TD>\n");
    msdsout.println("  <TD HEIGHT=45 WIDTH=\"33%\" VALIGN=\"MIDDLE\" ALIGN=\"LEFT\">\n");
    msdsout.println("<INPUT CLASS=\"HEADER\" TYPE=\"text\" NAME=\"item\" VALUE=\"" + (item == "NAWAZ!" ? "" : item) + "\" size=\"19\">\n");
    msdsout.println("</TD>\n");
    msdsout.println(" </TR>\n");
    msdsout.println(" <TR VALIGN=\"MIDDLE\">\n");
    msdsout.println("<TD HEIGHT=45 COLSPAN=2 ALIGN=\"left\">\n");

    if (client.equalsIgnoreCase("Internal")) {
      msdsout.println("&nbsp;\n");
      msdsout.println("<INPUT TYPE=\"hidden\" NAME=\"msdsdataabase\" VALUE=\"Yes\">\n");
    } else {
      msdsout.println(" <INPUT TYPE=\"checkbox\" NAME=\"limit\" VALUE=\"yes\" " + checked + "> \n");
      msdsout.println("<FONT SIZE=\"2\" FACE=\"Arial\"><B>"+res.getString("label.catalog")+"</B></FONT> \n");
      msdsout.println("&nbsp;&nbsp;\n");
      msdsout.println("<INPUT TYPE=\"checkbox\" NAME=\"noncatalog\" VALUE=\"yes\" " + checkednon + "><FONT SIZE=\"2\" FACE=\"Arial\"><B>"+res.getString("label.noncatalog")+"</B></FONT>\n");
      msdsout.println("<INPUT TYPE=\"checkbox\" NAME=\"msdsdataabase\" VALUE=\"yes\" " + checkedmsds + "><FONT SIZE=\"2\" FACE=\"Arial\"><B>"+res.getString("label.fulldatabase")+"</B></FONT>  \n");
    }
    msdsout.println("</TD>\n");

    msdsout.println("  <TD HEIGHT=45 WIDTH=\"3%\" VALIGN=\"MIDDLE\" ALIGN=\"right\">\n");
    msdsout.println("&nbsp;\n");
    msdsout.println("</TD>\n");

    msdsout.println("<TD COLSPAN=\"2\" HEIGHT=45 WIDTH=\"45%\" ALIGN=\"RIGHT\">\n");
    msdsout.println(BackNext == null ? "&nbsp;" : BackNext);
    msdsout.println("&nbsp;</TD>\n");

    msdsout.println(" </TR>\n");
    msdsout.println("</TABLE> \n");
    msdsout.println("<INPUT TYPE=\"hidden\" NAME=\"from\" VALUE=\"1\">\n");
    msdsout.println("<INPUT TYPE=\"hidden\" NAME=\"SortByAct\" VALUE=\"0\">\n");
    msdsout.println("<INPUT TYPE=\"hidden\" NAME=\"Session_ID\" VALUE=\"" + Session_ID + "\">\n");
    msdsout.println("<INPUT TYPE=\"hidden\" NAME=\"interval\" VALUE=\"20\">\n");
    msdsout.println("<INPUT TYPE=\"hidden\" NAME=\"window\" VALUE=\"New\">\n");
    msdsout.println("<INPUT TYPE=\"hidden\" NAME=\"istcmIsApplication\" ID=\"istcmIsApplication\" VALUE=\"" + istcmIsApplication + "\">\n");
    msdsout.println("<INPUT TYPE=\"hidden\" NAME=\"Second\" VALUE=\"No\">\n</FORM>\n");

    return;
  }

  protected void printJavaScripts(PrintWriter msdsout) {
    //StringBuffer Msgt=new StringBuffer();
    msdsout.println("<SCRIPT LANGUAGE=\"JavaScript\"  TYPE=\"text/javascript\">\n<!--\n ");
    /*      msdsout.println( "var SortBy = \" \"; \n" );
          msdsout.println( "var submitcount=0; \n" );
          msdsout.println( "var companynames = new Array(\"ALL\",\"REST\"); \n" );
          msdsout.println( "var altName = new Array(); \n" );
          msdsout.println( "altName[\"ALL\"] = new Array(\"Material\",\"Part Number\",\"Trade Name\",\"Manufacturer\",\"Catalog\"); \n" );
          msdsout.println( "altName[\"REST\"] = new Array(\"Material\",\"Part Number\",\"Trade Name\",\"Manufacturer\",\"Facility\"); \n" );
          msdsout.println( "var OptValue = new Array(); \n" );
          msdsout.println( "OptValue[\"All\"] = new Array(\"MATERIAL_ID\",\"FAC_ITEM_ID\",\"TRADE_NAME\",\"MFG_DESC\",\"CATALOG_ID\"); \n" );
          msdsout.println( "OptValue[\"REST\"] = new Array(\"MATERIAL_ID\",\"FAC_ITEM_ID\",\"TRADE_NAME\",\"MFG_DESC\",\"FACILITY_ID\"); \n" );
          msdsout.println( "function reshow(object) \n" );
          msdsout.println( "{ \n" );
          msdsout.println( "artist = object.options[object.selectedIndex].text; \n" );
          msdsout.println( "if (artist == \"All Catalogs\") \n" );
          msdsout.println( "{ \n" );
          msdsout.println( "var indexofxompany = 0; \n" );
          msdsout.println( "} \n" );
          msdsout.println( "else \n" );
          msdsout.println( "{ \n" );
          msdsout.println( "var indexofxompany = 1; \n" );
          msdsout.println( "} \n" );
          msdsout.println( "var testbin;\n" );
          msdsout.println( "eval( testbin =  \"window.document.form1.sortby\");\n" );
          msdsout.println( "var cur = null;\n" );
          msdsout.println( "eval( cur = (eval(testbin.toString())).selectedIndex );\n" );
          msdsout.println( "    for (var i = document.form1.sortby.length;i > 0;i--) \n" );
          msdsout.println( "   { \n" );
          msdsout.println( "        document.form1.sortby.options[i] = null; \n" );
          msdsout.println( "   } \n" );
          msdsout.println( "   showlinks(indexofxompany); \n" );
          msdsout.println( "   window.document.form1.sortby.selectedIndex = cur; \n" );
          msdsout.println( "} \n" );
          msdsout.println( "function showlinks(selectedIndex) \n" );
          msdsout.println( "{ \n" );
          msdsout.println( "var companytoshow = companynames[selectedIndex]; \n" );
          msdsout.println( "var nickNameValue = new Array(); \n" );
          msdsout.println( "var OptionValue = new Array(); \n" );
          msdsout.println( "nickNameValue = altName[companytoshow]; \n" );
          msdsout.println( "OptionValue = OptValue[companytoshow]; \n" );
          msdsout.println( "for (var i=0; i < nickNameValue.length; i++) \n" );
          msdsout.println( "{ \n" );
          msdsout.println( "      opt(i,nickNameValue[i]) \n" );
          msdsout.println( "} \n" );
          msdsout.println( "} \n" );
          msdsout.println( "function opt(href,text) \n" );
          msdsout.println( "{ \n" );
          msdsout.println( "   var optionName = new Option(text,href, false, false) \n" );
          msdsout.println( "   document.form1.sortby.options[href] = optionName; \n" );
          msdsout.println("} \n");
     */
    msdsout.println("function RefreshSortBy(entered){\n with (entered)\n { \n");
    // Store the selected OPTION in a variable called ref\n" +
    msdsout.println("SortBy = document.form1.sortby.options[selectedIndex].VALUE;\n} \n }\n function RefreshPage(entered){ \n");
    msdsout.println("with (entered)\n {\n");
    // Store the selected OPTION in a variable called ref\n" +
    msdsout.println("ref=document.form1.fac.options[selectedIndex].value;\n }   \n");

    msdsout.println("SortByNo = document.form1.sortby.selectedIndex;\n");
    msdsout.println("SortBy = document.form1.sortby.options[SortByNo].value;\n");

    msdsout.println("loc = \"" + getBundle().getString("BUILD_SERVLET") + "sortby=\";\n");

    msdsout.println("loc=loc + SortBy; \n");
    msdsout.println("loc=loc+\"&TestFac=Yes&Session_ID=\"; \n");
    msdsout.println("loc=loc+document.form1.Session_ID.value;\n");
    msdsout.println("loc=loc+\"&limit=\"; \n");
    msdsout.println("loc=loc+document.buildForm.limit.value;\n");
    msdsout.println("loc=loc+\"&fac=\"+ref;\n");
    msdsout.println("loc=loc+\"&item=\";\n");
    msdsout.println("loc=loc+document.buildForm.item.value;\n");
    msdsout.println("loc=loc+\"&window=\";\n");
    msdsout.println("loc=loc+document.buildForm.window.value;\n");
    msdsout.println("loc=loc+\"&noncatalog=\"; \n");
    msdsout.println("loc=loc+document.buildForm.noncatalog.value; \n");
    msdsout.println("loc=loc+\"&msdsdataabase=\"; \n");
    msdsout.println("loc=loc+document.buildForm.msdsdataabase.value; \n");
    msdsout.println("loc=loc+\"&headers=\";\n");
    msdsout.println("loc=loc+document.buildForm.headers.value; \n");
    msdsout.println("document.location=loc;\n");
    msdsout.println("}\n ");
    /*
          msdsout.println( " function goBack(){\n" );
          msdsout.println( "     if (submitcount == 0) \n" );
          msdsout.println( "     { \n" );
          msdsout.println( "        submitcount++; \n" );
          msdsout.println( "        window.document.backForm.Second.value = \"Yes\" ;\n" );
          msdsout.println( "        document.backForm.submit();\n" );
          msdsout.println( "     } \n" );
          msdsout.println( "     else \n" );
          msdsout.println( "     { \n" );
          msdsout.println( "     }\n" );
          msdsout.println( " } \n" );
          msdsout.println( "function goNext(){ \n" );
          msdsout.println( "     if (submitcount == 0) \n" );
          msdsout.println( "     { \n" );
          msdsout.println( "       submitcount++; \n" );
          msdsout.println( "       window.document.nextForm.Second.value = \"Yes\" ;\n" );
          msdsout.println( "       document.nextForm.submit();\n" );
          msdsout.println( "     } \n" );
          msdsout.println( "     else \n" );
          msdsout.println( "     { \n" );
          msdsout.println( "     }\n" );
          msdsout.println( " } \n" );
     */
    msdsout.println("--> \n</script> \n\n");

    return;
  }

  protected abstract String getClient();

  protected abstract ServerResourceBundle getBundle();

}
