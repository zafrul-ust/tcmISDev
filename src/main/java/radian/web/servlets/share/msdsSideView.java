package radian.web.servlets.share;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Vector;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.dbObjs.SideView;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import java.net.HttpURLConnection;
import radian.tcmis.server7.share.helpers.HelpObjs;
import com.tcmis.common.util.ResourceLibrary;
import java.util.Locale;
import com.tcmis.common.admin.beans.PersonnelBean;
/**
 * Title:        MSDS Viewer
 * Copyright:    Copyright (c) 2001
 * Company:      Radian
 * @author Nawaz Shaik
 * @version
 *
 * 07-05-01 - Changed the code such that a page is displayed when there is only one revision date
 * for an MSDs and it is not available. Also changed the email link on the broken image link.
 *
 * 07-17-01 - Chnaged the code to display not Available when there is not even one MSDS available
 * Also the broken image email has been changed to allow the user to enter perosnal comments
 * about what problems he is having.
 *
 * 03-12-03
 * Whole new MSDSveiwer new package
 * 03-17-03
 * Null pointer bug
 * 07-15-03 Code cleanup and adding MSDS no
 * 08-15-03 Sending Spec information when a broken spec is reported
 * 09-01-03 taking out /tcmIS in the code
 * 02-04-04 - New Lists option
 * 06-01-04 - Changed spelling from guidence to guidance
 */
public class msdsSideView {
  private ServerResourceBundle msdsbundle = null;
  private TcmISDBModel db = null;
  private boolean gettingHTML = false;
  private boolean gettingMSDS = false;
  private boolean gettingSIDE = false;
  private boolean gettingTITLE = false;
  private boolean showPrinticon = false;
  private String printiconurl = "";
  private String wwwhomeurl = "";
  private String clinetname = "";
  private ResourceLibrary res = null; // res means resource.

  public msdsSideView(ServerResourceBundle b, TcmISDBModel d) {
    msdsbundle = b;
    db = d;
  }

  //Process the HTTP Get request
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response) throws ServletException, IOException {
    doPost(request, response);
  }

  //Process the HTTP Post request
  public void doPost(HttpServletRequest request,
                     HttpServletResponse response) throws ServletException, IOException {
    wwwhomeurl = radian.web.tcmisResourceLoader.gethosturl();

    clinetname = msdsbundle.getString("DB_CLIENT_NAME");
    HttpSession Session = request.getSession();
    res =  new ResourceLibrary("com.tcmis.common.resources.CommonResources", (Locale)request.getLocale());

    if ("Tcm_Ops".equalsIgnoreCase(clinetname)) {
      clinetname = "internal";
    }
    //System.out.println("clinetname  in MSDS      "+clinetname);

    Cookie sessionCookie = new Cookie("TCMISSESSIONID", request.getSession().getId());
    sessionCookie.setPath("/spec");
    sessionCookie.setMaxAge(300);
    response.addCookie(sessionCookie);
    Cookie sessionCookie2 = new Cookie("TCMISSESSIONID", request.getSession().getId());
    sessionCookie2.setPath("/OpSupport");
    sessionCookie2.setMaxAge(300);
    response.addCookie(sessionCookie2);
    
    String session = request.getParameter("SESSION");
    if (session == null) {
      session = "4";
    }

    if ( (session == null) || (session.equalsIgnoreCase("4"))) {
      gettingHTML = true;
    } else if (session.equalsIgnoreCase("0")) {
      gettingTITLE = true;
    } else if (session.equalsIgnoreCase("1")) {
      gettingSIDE = true;
    } else if (session.equalsIgnoreCase("2")) {
      gettingMSDS = true;
    }

    response.setContentType("text/html");

    try {

		PrintWriter out = new PrintWriter(response.getOutputStream());
      if (gettingHTML) {
        buildHTML(request, out);
      } else if (gettingSIDE) {
        buildSideView(request, out);
      } else if (gettingMSDS) {
        buildMSDS(request, out);
      } else if (gettingTITLE) {
        buildTitle(request, out);
      }

      out.flush();
      out.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public String getBrowserLocaleCode (HttpServletRequest request) {
        //get browser locale code
        HttpSession session = request.getSession(false);
        String	browserLocaleCode = "en_US";
        try
        {
            PersonnelBean personnelBean = (PersonnelBean) session.getAttribute("personnelBean");
            if (personnelBean != null)
            {
                //System.out.println("browserLocaleCode langSetting   "+session.getAttribute("langSetting"));
                browserLocaleCode = (String) session.getAttribute("langSetting");
                /*if (browserLocaleCode.equalsIgnoreCase("en_US"))
                {
                    browserLocaleCode = "";
                }*/
            }
            else
            {
                browserLocaleCode = "en_US";
            }
        }
        catch (Exception ex)
        {
         ex.printStackTrace();
         browserLocaleCode = "en_US";
        }
        /*  String	browserLocaleCode = request.getHeader("Accept-Language");
		//because browser locale has slightly different format (xx-yy i.e en_us) I'm going to convert it to xx_YY i.e en_US
		//here are other examples:
		//msdsSideView doPost browser locale:en-us
		//msdsSideView doPost browser locale:zh-tw,en-us;q=0.7,en;q=0.3
		//msdsSideView doPost browser locale:en-gb
		if (browserLocaleCode != null) {
			browserLocaleCode = browserLocaleCode.substring(0,5);
			String[] tmpBl = browserLocaleCode.split("-");
			if (tmpBl.length == 2) {
				browserLocaleCode = tmpBl[0]+"_"+tmpBl[1].toUpperCase();
			}else {
				radian.web.emailHelpObj.sendtrongemail("User's browser locale code does not match format",browserLocaleCode);
				browserLocaleCode = "";
			}
		}else {
			browserLocaleCode = "";
		}*/

		return browserLocaleCode;
  }

  public void buildHTML(HttpServletRequest requesthtml, PrintWriter out) {
    String rev_date = requesthtml.getParameter("date");
    String revision_date_no_format = requesthtml.getParameter("dateNoFormat");
    String localeCode = requesthtml.getParameter("localeCode");
    String showspec = requesthtml.getParameter("showspec");

	 if (showspec == null) {
      showspec = "";
    }
    showspec.trim();

    if (localeCode == null) {
      localeCode = "";
    }

	 String plug = requesthtml.getParameter("plug");
    String id = requesthtml.getParameter("id");
    String facility = requesthtml.getParameter("facility");
    if (facility == null) {
      facility = "";
    }
    //facility = facility.trim();
    facility = HelpObjs.findReplace(facility, "�", "");
    String URLfacility = facility;
    URLfacility = URLfacility.replace(' ', '+');

    String catpartno = requesthtml.getParameter("catpartno");
    if (catpartno == null) {
      catpartno = "";
    }
    catpartno.trim();

    String act = requesthtml.getParameter("act");
    if (showspec == null) {
      showspec = "msds";

    }
    String client = requesthtml.getParameter("cl");
    if (client == null) {
      client = "";
    }
    if (client.length() > 0) {
      client = BothHelpObjs.addSpaceForUrl(client);
    }

    String spec = requesthtml.getParameter("spec");
    if (spec == null) {
      spec = "";
    }
    spec.trim();

    String session = requesthtml.getParameter("SESSION");
    if (session == null) {
      session = "4";
    }

    if (rev_date == null) {
      //get the lastest revision date for material id and pass it on to all
      //three frames so they can display the right data
      SideView svObj = new SideView(msdsbundle);
		svObj.setBrowserLocaleCode(getBrowserLocaleCode(requesthtml));
		String[] tmpData = svObj.getLastestRevisionDate(db, id, facility, requesthtml);
      rev_date = tmpData[0];
      localeCode = tmpData[1];
      revision_date_no_format = tmpData[2];
    }

    //StringBuffer Msg=new StringBuffer();
    String msdsservlet = msdsbundle.getString("VIEW_MSDS");

    out.println("<HTML><TITLE>"+res.getString("label.msdsinfo")+"</title>\n");
    out.println("<FRAMESET rows=\"70,*\">\n");
    out.println("<FRAME src=\"" + msdsservlet + "SESSION=0&act=" + act + "&id=" + id +
    		    "&dateNoFormat=" + revision_date_no_format + "&date=" + rev_date + "&facility=" + URLfacility + "&catpartno=" + catpartno +
                "&spec=" + spec + "&cl=" + client + "&localeCode=" + localeCode + "\" name=\"title\" scrolling=\"auto\">\n");
    out.println("<FRAMESET cols=\"190,*\">\n");
    out.println("<FRAME src=\"" + msdsservlet + "SESSION=1&act=" + act + "&id=" + id +
    		    "&dateNoFormat=" + revision_date_no_format + "&date=" + rev_date + "&facility=" + URLfacility + "&catpartno=" + catpartno +
                "&spec=" + spec + "&cl=" + client + "&showspec=" + showspec + "&localeCode=" + localeCode +
                "\" name=\"opts\" scrolling=\"auto\">\n");
    if (act.equals("spec")) {
        out.println("<FRAME src=\"docViewer.do?uAction=viewSpec&" + requesthtml.getQueryString() + "\" name=\"data\">\n");
    }
    else {
    out.println("<FRAME src=\"" + msdsservlet + "SESSION=2&act=" + act + "&id=" + id +
    	    	"&dateNoFormat=" + revision_date_no_format + "&date=" + rev_date + "&facility=" + URLfacility + "&catpartno=" + catpartno +
                "&spec=" + spec + "&cl=" + client + "&plug=" + plug + "&localeCode=" + localeCode + "\" name=\"data\">\n");
    }
    out.println("</FRAMESET>\n");
    out.println("</FRAMESET>\n");
    out.println("</html>\n");

  } //end of method

  public void buildSideView(HttpServletRequest requestside, PrintWriter out) {
    String notshowmsds = "";
    String nottoshowspec = "No Records";
    String msdsservlet = msdsbundle.getString("VIEW_MSDS");
    //StringBuffer Msgsideview = new StringBuffer();
    String revdateforlinks = requestside.getParameter("date");
    String localeCodeForLinks = requestside.getParameter("localeCode");
    String showspec = requestside.getParameter("showspec");
    String revdatenoformatforlinks = requestside.getParameter("dateNoFormat");
    
    if (showspec == null) {
      showspec = "";
    }
    showspec.trim();

    if (localeCodeForLinks == null) {
      localeCodeForLinks = "";
    }

    String id = requestside.getParameter("id");
    String facility = requestside.getParameter("facility");
    if (facility == null) {
      facility = "";
    }
    facility = facility.trim();
    String URLfacility = facility;
    URLfacility = URLfacility.replace(' ', '+');

    String catpartno = requestside.getParameter("catpartno");
    if (catpartno == null) {
      catpartno = "";
    }
    catpartno.trim();

    String act = requestside.getParameter("act");
    String spec = requestside.getParameter("spec");
    if (spec == null) {
      spec = "";
    }
    spec.trim();

    if (act == null) {
      act = "msds";
    }

    String heal = "";
    String flam = "";
    String react = "";
    String haz = "";
    String radios = "";
    String radios2 = "";

    String hmisheal = "";
    String hmisflam = "";
    String hmisreact = "";
    String hmisppe = "";
    String clientmsdsno = "";

    Hashtable alldatamsds = new Hashtable();
    Hashtable alldataspec = new Hashtable();
    boolean showspecdrop = false;
    if (spec.length() > 0) {
      showspecdrop = true;
    }

    SideView svObj = new SideView(msdsbundle);
    try {
		svObj.setBrowserLocaleCode(getBrowserLocaleCode(requestside));
		alldatamsds = buildData(requestside, svObj.getSideViewVector(db, revdateforlinks, id, facility, requestside), revdateforlinks, localeCodeForLinks);
    } catch (Exception e) {
      e.printStackTrace();
      notshowmsds = "No Records";
    }

    if (showspecdrop) {
      try {
        alldataspec = buildData3(svObj.getSpecVector(db, id, catpartno, facility));
      } catch (Exception e) {
        e.printStackTrace();
        nottoshowspec = "No Records";
      }

      if (alldataspec == null) {
        nottoshowspec = "No Records";
      } else {
        nottoshowspec = alldataspec.get("SHOWRECORDS").toString();
        radios2 = alldataspec.get("RADIOS2").toString();
      }
    }

    if (alldatamsds == null) {
      notshowmsds = "No Records";
    } else {
      notshowmsds = alldatamsds.get("SHOWRECORDS").toString();
      radios = alldatamsds.get("RADIOS").toString();
      heal = alldatamsds.get("HEALTH").toString();
      flam = alldatamsds.get("FLAMABILITY").toString();
      react = alldatamsds.get("REACTIVITY").toString();
      haz = alldatamsds.get("HAZARD").toString();

      hmisheal = alldatamsds.get("HMIS_HEALTH").toString();
      hmisflam = alldatamsds.get("HMIS_FLAMABILITY").toString();
      hmisreact = alldatamsds.get("HMIS_REACTIVITY").toString();
      hmisppe = alldatamsds.get("HMIS_PPE").toString();

      clientmsdsno = alldatamsds.get("MSDS_NO").toString();
      //revdatenoformatforlinks = alldatamsds.get("REVDATENOFORMATLINK").toString();
      revdateforlinks = alldatamsds.get("REVDATELINK").toString();
    }

    if ( (notshowmsds.equalsIgnoreCase("No Records")) &&
        (nottoshowspec.equalsIgnoreCase("No Records"))) {
      out.println(
          "<HTML><HEAd><TITLE>"+res.getString("label.sideview")+"</title>\n<BODY><FONT FACE=\"Arial\"><BR><BR><BR><B>"+res.getString("main.nodatafound")+"\n");
      out.println("</B></FONT></BODY>\n");
      //out.println( Msgsideview );
      return;
    }

    out.println("<HTML><HEAd><TITLE>"+res.getString("label.sideview")+"</title>\n");
    out.println(
        "<SCRIPT SRC=\"/clientscripts/printframesmsds.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");
    out.println("</HEAD>\n");
    out.println("<BODY BGCOLOR=\"#ffffff\" text=\"#000000\" link=\"#000066\" alink=\"#8E236B\" vlink=\"#4A766E\" onLoad=\"window.focus()\">\n");
    if (clientmsdsno.trim().length() > 0) {
      out.println("<FONT FACE=\"Arial\" SIZE=\"2\"><B>"+res.getString("label.msdsno")+":</B>&nbsp;&nbsp;" +
                  clientmsdsno + "</FONT><BR>\n");
    }
    //=======Specifications=======//
    if (showspecdrop) {
      out.println("<P>\n");
      out.println("<FORM ACTION=\"\" METHOD=POST>\n");
      out.println("<TABLE CELLSPACING=\"0\" CELLPADDING=\"1\" BORDER=\"0\" BGCOLOR=\"#7093DB\" WIDTH=\"100%\">\n");
      out.println("<TR><TD>\n");
      out.println("<TABLE CELLSPACING=\"0\" CELLPADDING=\"0\" BORDER=\"0\" BGCOLOR=\"#FFFFFF\" WIDTH=\"100%\">\n");
      out.println("<TR><TD ALIGN=\"LEFT\" BGCOLOR=\"#7093DB\">\n");
      out.println(
          "<FONT FACE=\"Arial\" SIZE=\"2\" COLOR=\"#E6E8FA\"><B><center>"+res.getString("label.spec")+"<BR></FONT>\n");
      out.println("<FONT FACE=\"Arial\" SIZE=\"1\" COLOR=\"#000000\">\n");
      out.println(res.getString("msg.avspeclink")+":<BR>\n");
      out.println("</TD></TR>\n");
      out.println("<TR><TD ALIGN=\"CENTER\">\n");
      out.println("<FONT FACE=\"Arial\" SIZE=\"1\">\n");
      out.println("<SELECT NAME=\"specList\" onChange=\"DropDownMenuSpec(this)\">\n");
      out.println("<option value=\"*false\" selected>"+res.getString("label.pleaseselect")+"</option>\n");
      out.println("" + radios2 + "</select>\n");
      out.println("</FONT></FONT>\n");
      out.println("</TD></TR>\n");
      out.println("</TABLE>\n");
      out.println("</TD></TR>\n");
      out.println("</TABLE>\n");
      out.println("</FORM>\n");
    }

    if (act.equalsIgnoreCase("spec")) {
      out.println("<SCRIPT LANGUAGE = \"JavaScript\" TYPE=\"text/javascript\" >\n");
      out.println("<!--\n");
      out.println("  winact = \"spec\"\n");
      out.println("  specrevdate = \"" + revdateforlinks + "\"\n");
      out.println("  specid = \"" + id + "\"\n");

      out.println("// -->\n");
      out.println("</SCRIPT>\n");
    }

    out.println("<SCRIPT LANGUAGE = \"JavaScript\" TYPE=\"text/javascript\" >\n");
    out.println("<!--\n");
    out.println("  clientname = \"" + clinetname + "\"\n");
    out.println("// -->\n");
    out.println("</SCRIPT>\n");

    out.println("<FORM ACTION=\"\"  NAME=\"MSDSForm\" METHOD=POST>\n");
    out.println("<TABLE CELLSPACING=\"0\" CELLPADDING=\"1\" BORDER=\"0\" BGCOLOR=\"#7093DB\" WIDTH=\"100%\">\n");
    out.println("<TR>\n");
    out.println("<TD>\n");
    out.println("<TABLE CELLSPACING=\"0\" CELLPADDING=\"0\" BORDER=\"0\" BGCOLOR=\"#FFFFFF\" WIDTH=\"100%\">\n");
    out.println("<TR>\n");
    out.println("<TD ALIGN=\"LEFT\" BGCOLOR=\"#7093DB\">\n");
    out.println(
        "<FONT FACE=\"Arial\" SIZE=\"2\" COLOR=\"#E6E8FA\"><B><center>"+res.getString("label.msds")+"<BR></FONT>\n");
    out.println("<FONT FACE=\"Arial\" SIZE=\"1\" COLOR=\"#000000\">"+res.getString("label.revisiondate")+"</FONT>\n");
    out.println("&nbsp;&nbsp;&nbsp;<BR>\n");
    out.println("</TD>\n");
    out.println("</TR>\n");
    out.println("<TR>\n");
    out.println("<TD ALIGN=\"CENTER\">\n");
    out.println("<FONT FACE=\"Arial\" SIZE=\"1\">\n");
    out.println("<SELECT NAME=\"msdsList\" onChange=\"DropDownMenuMSDS(this)\">\n");
    out.println("" + radios + "</select>\n");
    out.println("</FONT>\n");
    out.println("</TD>\n");
    out.println("</TR>\n");
    out.println("</TABLE>\n");
    out.println("</TD>\n");
    out.println("</TR>\n");
    out.println("</TABLE>\n");

    String qurl = "";
    if (spec == null) {
      spec = "";
    }
    if (facility == null) {
      facility = "";
    }
    if (catpartno == null) {
      catpartno = "";
    }

    if (spec.length() != 0) {
      qurl = qurl + "&spec=" + spec;
    }

    if (facility.length() != 0) {
      qurl = qurl + "&facility=" + BothHelpObjs.addSpaceForUrl(facility);
    }

    if (catpartno.length() != 0) {
      qurl = qurl + "&catpartno=" + catpartno;
    }

    if (localeCodeForLinks.length() != 0) {
      qurl = qurl + "&localeCode=" + localeCodeForLinks;
    }

    if (act.equalsIgnoreCase("msds")) {
      out.println("<BR><center><a href=\"" + msdsservlet + "SESSION=2&act=msds&id=" + id +
    		      qurl + "&dateNoFormat=" + revdatenoformatforlinks + "&" + "date=" + revdateforlinks + "&plug=\" target=\"data\"\n");
      out.println("onClick=\"doTitle('img5')\">\n");
      out.println("<img name=\"img5\" src=\"" + wwwhomeurl +
                  "images/buttons/2msds.gif\" border=0>\n");
      out.println(
          "</a><INPUT TYPE=\"hidden\" NAME=\"msdsactive\" VALUE=\"msds\"><BR><BR>\n\n");

      out.println("<SCRIPT LANGUAGE = \"JavaScript\" TYPE=\"text/javascript\" >\n");
      out.println("<!--\n");
      out.println("  winact = \"msds\";\n");
      out.println("// -->\n");
      out.println("</SCRIPT>\n");

    } else {
      out.println("<BR><center><a href=\"" + msdsservlet + "SESSION=2&act=msds&id=" + id +
    		  qurl + "&dateNoFormat=" + revdatenoformatforlinks + "&" + "date=" + revdateforlinks + "&plug=\" target=\"data\"\n");
      out.println("onClick=\"doTitle('img5')\">\n");
      out.println("<img name=\"img5\" src=\"" + wwwhomeurl +
                  "images/buttons/1msds.gif\" border=0>\n");
      out.println(
          "</a><INPUT TYPE=\"hidden\" NAME=\"msdsactive\" VALUE=\"msds\" disabled ><BR><BR>\n");
    }

    if (act.equalsIgnoreCase("comp")) {
      out.println("<center><a href=\"" + msdsservlet + "SESSION=2&act=comp&id=" + id + "&dateNoFormat=" + revdatenoformatforlinks + "&" +
                  "date=" + revdateforlinks + "&localeCode=" + localeCodeForLinks + "\" target=\"data\"\n");
      out.println("onClick=\"doTitle('img1')\">\n");
      out.println("<img name=\"img1\" src=\"" + wwwhomeurl +
                  "images/buttons/comp/2comp.gif\" border=0>\n");
      out.println(
          "</a><INPUT TYPE=\"hidden\" NAME=\"composition\" VALUE=\"comp\"><BR><BR>\n");

      out.println("<SCRIPT LANGUAGE = \"JavaScript\" TYPE=\"text/javascript\" >\n");
      out.println("<!--\n");
      out.println("  winact = \"comp\";\n");
      out.println("// -->\n");
      out.println("</SCRIPT>\n");
    } else {
      out.println("<center><a href=\"" + msdsservlet + "SESSION=2&act=comp&id=" + id + "&dateNoFormat=" + revdatenoformatforlinks +  "&" +
                  "date=" + revdateforlinks + "&localeCode=" + localeCodeForLinks + "\" target=\"data\"\n");
      out.println("onClick=\"doTitle('img1')\">\n");
      out.println("<img name=\"img1\" src=\"" + wwwhomeurl +
                  "images/buttons/comp/1comp.gif\" border=0>\n");
      out.println(
          "</a><INPUT TYPE=\"hidden\" NAME=\"composition\" VALUE=\"comp\" disabled ><BR><BR>\n");
    }

    if (act.equalsIgnoreCase("prop")) {
      out.println("<a href=\"" + msdsservlet + "SESSION=2&act=prop&id=" + id + "&dateNoFormat=" + revdatenoformatforlinks  + "&" + "date=" +
                  revdateforlinks + "&localeCode=" + localeCodeForLinks + "\" target=\"data\"\n");
      out.println("onClick=\"doTitle('img2')\">\n");
      out.println("<img name=\"img2\" src=\"" + wwwhomeurl +
                  "images/buttons/prop/2prop.gif\" border=0>\n");
      out.println("</a><INPUT TYPE=\"hidden\" NAME=\"properties\" VALUE=\"prop\"><BR><BR>\n");

      out.println("<SCRIPT LANGUAGE = \"JavaScript\" TYPE=\"text/javascript\" >\n");
      out.println("<!--\n");
      out.println("  winact = \"prop\";\n");
      out.println("// -->\n");
      out.println("</SCRIPT>\n");
    } else {
      out.println("<a href=\"" + msdsservlet + "SESSION=2&act=prop&id=" + id + "&dateNoFormat=" + revdatenoformatforlinks +  "&" + "date=" +
                  revdateforlinks + "&localeCode=" + localeCodeForLinks + "\" target=\"data\"\n");
      out.println("onClick=\"doTitle('img2')\">\n");
      out.println("<img name=\"img2\" src=\"" + wwwhomeurl +
                  "images/buttons/prop/1prop.gif\" border=0>\n");
      out.println(
          "</a><INPUT TYPE=\"hidden\" NAME=\"properties\" VALUE=\"prop\" disabled ><BR><BR>\n");
    }

    if (act.equalsIgnoreCase("lists")) {
      out.println("<a href=\"" + msdsservlet + "SESSION=2&act=lists&id=" + id + "&dateNoFormat=" + revdatenoformatforlinks +  "&" +
                  "date=" + revdateforlinks + "&localeCode=" + localeCodeForLinks + "\" target=\"data\"\n");
      out.println("onClick=\"doTitle('img3')\">\n");
      out.println("<img name=\"img3\" src=\"" + wwwhomeurl +
                  "images/buttons/lists/2lists.gif\" border=0>\n");
      out.println("</a><INPUT TYPE=\"hidden\" NAME=\"lists\" VALUE=\"lists\"><BR><BR>\n");

      out.println("<SCRIPT LANGUAGE = \"JavaScript\" TYPE=\"text/javascript\" >\n");
      out.println("<!--\n");
      out.println("  winact = \"lists\";\n");
      out.println("// -->\n");
      out.println("</SCRIPT>\n");
    } else {
      out.println("<a href=\"" + msdsservlet + "SESSION=2&act=lists&id=" + id + "&dateNoFormat=" + revdatenoformatforlinks +  "&" +
                  "date=" + revdateforlinks + "&localeCode=" + localeCodeForLinks + "\" target=\"data\"\n");
      out.println("onClick=\"doTitle('img3')\">\n");
      out.println("<img name=\"img3\" src=\"" + wwwhomeurl +
                  "images/buttons/lists/1lists.gif\" border=0>\n");
      out.println(
          "</a><INPUT TYPE=\"hidden\" NAME=\"lists\" VALUE=\"lists\" disabled ><BR><BR>\n");
    }

    out.println("<INPUT TYPE=\"hidden\" NAME=\"landdetail\" VALUE=\"msds\" disabled>\n<INPUT TYPE=\"hidden\" NAME=\"airdetail\" VALUE=\"msds\" disabled>\n");
    out.println("</FORM>\n");

    out.println("<CENTER><B>"+res.getString("label.nfpa")+"</B>\n");
    if ( (flam.equalsIgnoreCase("&nbsp;")) && (heal.equalsIgnoreCase("&nbsp;")) &&
        (react.equalsIgnoreCase("&nbsp;"))) {
      out.println("<BR>"+res.getString("label.notlisted")+"<BR>\n");
    } else {
      out.println("<table background=\"" + wwwhomeurl +
                  "images/buttons/nfpa.gif\" width=80 height=82 cellpadding=0 cellspacing=0 border=0>\n");
      out.println("<tr><td valign=\"center\" align=\"center\"><FONT size=\"1\" face=Arial COLOR=ffffff><p><BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n");
      out.println("<B><FONT size=2>" + flam + "</FONT>\n");
      out.println("</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<BR>&nbsp;<B><FONT size=2>" +
                  heal + "</FONT>\n");
      out.println(
          "</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<FONT COLOR=000000><B>\n");
      out.println("<FONT size=2>" + react +
                  "</FONT></b>&nbsp;<BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<B>\n");
      out.println("<FONT size=2>" + haz +
                  "</FONT></b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<BR><BR>\n");
      out.println("</td>\n");
      out.println("</tr>\n");
      out.println("</table>\n");
    }

    out.println("<BR><B>"+res.getString("label.hmis")+"</B>\n");
    if ( (hmisflam.length() == 0) && (hmisheal.length() == 0) && (hmisreact.length() == 0) &&
        (hmisppe.length() == 0)) {
      out.println("<BR>"+res.getString("label.notlisted")+"<BR>\n");
    } else {
      out.println("<BR><TABLE height=63 cellSpacing=0 cellPadding=0 width=60 border=0>\n");
      out.println("<TR><TD BGCOLOR=#0000ff vAlign=TOP height=15 align=middle><FONT face=Arial color=#ffffff size=2><B>" +
                  hmisheal + "</B></FONT></TD></TR>\n");
      out.println("<TR><TD BGCOLOR=#ff0000 vAlign=TOP height=15 align=middle><FONT face=Arial color=#ffffff size=2><B>" +
                  hmisflam + "</B></FONT></TD></TR>\n");
      out.println("<TR><TD BGCOLOR=#ffff00 vAlign=TOP height=15 align=middle><FONT face=Arial color=#000000 size=2><B>" +
                  hmisreact + "</B></FONT></TD></TR>\n");
      out.println("<TR><TD BGCOLOR=#ffffff vAlign=TOP height=15 align=middle><FONT face=Arial color=#000000 size=2><B>" +
                  hmisppe + "</B></FONT></TD></TR>\n");
      out.println("</TABLE>\n");
    }

    if (showPrinticon) {
      out.println("<BR><A HREF=\"#\" ID=\"printlinkie\" onclick=\"javascript:window.open('" +
                  printiconurl + "')\" STYLE=\"color:#0000ff;cursor:hand;\"><U>"+res.getString("label.openmsds")+"</U></A>\n");
      out.println("<SCRIPT LANGUAGE=\"Javascript\" TYPE=\"text/javascript\">\n");
      out.println("<!--\n");
      out.println("insertprintLink('" + printiconurl + "');\n");
      out.println("// -->\n");
      out.println("</SCRIPT>\n");
    }
    out.println("</CENTER>\n");
    out.println("</body></html> \n");

    alldatamsds = null;
    alldataspec = null;
  }

  public void buildTitle(HttpServletRequest requesttitle, PrintWriter out) {
    String rev_date = requesttitle.getParameter("date");
    String localeCodeForLinks = requesttitle.getParameter("localeCode");
    String id = requesttitle.getParameter("id");
    String rev_date_hold = requesttitle.getParameter("date");
    String spec_hold = requesttitle.getParameter("spec");
    String facility = requesttitle.getParameter("facility");

    if (localeCodeForLinks == null) {
      localeCodeForLinks = "";
    }

    if (facility == null) {
      facility = "";
    }
    String URLfacility = facility;
    URLfacility = URLfacility.replace(' ', '+');

    String catpartno = requesttitle.getParameter("catpartno");
    String act = requesttitle.getParameter("act");
    String spec = requesttitle.getParameter("spec");

    String hasTIF = requesttitle.getParameter("hasTIF");
    if (hasTIF == null) {
      hasTIF = "N";
    }

    if (act == null) {
      act = "msds";
    }

    StringBuffer labelStr = new StringBuffer();
    StringBuffer tifStr = new StringBuffer();
    StringBuffer badimgStr = new StringBuffer();
    StringBuffer jscript = new StringBuffer();
    StringBuffer tailStr = new StringBuffer();
    StringBuffer mfgDescStr = new StringBuffer();
    String notshow = "";
    String msds_hold = "";
    String date_hold = "";
    String mat_desc_hold = "";
    String contentspec = "";
    String mfgDesc = "";

    if (rev_date == null) {
      rev_date = "null";
    }
    if (id == null) {
      id = "null";
    }
    if (spec == null) {
      spec = "null";
    }
    if (catpartno == null) {
      catpartno = "null";
    }

    SideView svObj = new SideView(msdsbundle);

    String router = "title";
    jscript.append("<HTML>\n");
    jscript.append(
        "<SCRIPT SRC=\"/clientscripts/printframesmsds.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n");

    Vector titlev = null;
    try {
		svObj.setLocaleCode(localeCodeForLinks);
      titlev = svObj.getContentVector(db, rev_date, id, "title", spec, catpartno, facility, "", "", "");
    } catch (Exception ex) {
      notshow = "No Records";
    }
    if (titlev.size() == 0 || titlev.elementAt(0).toString().equalsIgnoreCase("No Records")) {
      notshow = "No Records";
    } else {
      for (int j = 0; j < titlev.size(); j++) {
        SideView sv = (SideView) titlev.elementAt(j);
        if (spec_hold == null || spec.equalsIgnoreCase("null")) {
          spec_hold = sv.getSpecId();
        }
        if (rev_date_hold == null || rev_date.equalsIgnoreCase("null")) {
          rev_date_hold = sv.getDate2();
        }
        msds_hold = sv.getContent();
        date_hold = sv.getDate2();
        mfgDesc = sv.getManufacturer();
        String mat_desc_hold_temp = sv.getMaterial();
        if (mat_desc_hold_temp.trim().length() > 0) {
          mat_desc_hold = mat_desc_hold_temp;
        }
      }
    }

    jscript.append("<body bgcolor=white text=000000>\n<center>\n");
    jscript.append("<table width=100% height=100% border=0 cellpadding=0 cellspacing=0>\n");
    jscript.append("<tr><td width=15% align=\"center\" valign=\"center\">\n");
    jscript.append("<img src=\"" + wwwhomeurl + "images/buttons/" + clinetname.toLowerCase() +
                   "/" + clinetname.toLowerCase() + ".gif\">\n");
    jscript.append("</td><td width=\"58%\" valign=\"center\">\n");
    jscript.append("<center><table border=0 cellpadding=2 cellspacing=0 bgcolor=\"#7093DB\">\n");
    jscript.append("<tr><td><table border=0 cellspacing=0 cellpadding=0 bgcolor=\"#ffffff\">\n");
    jscript.append("<tr><td><FONT size=4 face=Arial><b>&nbsp;&nbsp;" + mat_desc_hold + "\n");
    jscript.append("&nbsp;&nbsp;</FONT></td></tr></table>\n");
    jscript.append("</td></tr></table></center><center>\n");

    String helpurl = msdsbundle.getString("HELP_SERVLET_PATH");
    tailStr.append("<td><a href=\"#\" onMouseOver=\"imgOn('imghelp')\" onMouseOut=\"imgOff('imghelp')\" onClick=\"openwin(\'/tcmIS/help/new/\')\">\n");
    tailStr.append("<img name=\"imghelp\" src=\"" + wwwhomeurl +
                   "images/help_up.gif\" border=0></a>\n");
    tailStr.append("</td></tr>\n");

    tailStr.append("<tr><td width=15%>&nbsp;<center><td width=58% align=center valign=center><font size=1 face=Arial><b>"+res.getString("label.manufacturer")+": " + mfgDesc +"</b></font></td></tr></center>");

    tailStr.append("</table>\n</body></html>\n");

    String WWWhomeDirectory = msdsbundle.getString("WEBS_HOME_WWWS");

    if ( (act.equalsIgnoreCase("msds")) || (act.equalsIgnoreCase("spec"))) {
      String erroremailurl = radian.web.tcmisResourceLoader.getmsdserrorurl();
      badimgStr.append("<td align=\"right\" valign=\"center\">\n");
      badimgStr.append("<a href=\"#\" onClick=\"openwin23(\'" + erroremailurl + "spec_hold=" +
                       spec_hold + "&catpartno=" + catpartno + "&act=" + act + "&material_id=" + id +
                       "&revision_date=" + date_hold +  "&localeCode="+localeCodeForLinks+"&cl=" + msdsbundle.getString("DB_CLIENT") +
                       "&facility=" + BothHelpObjs.addSpaceForUrl(facility) + "&url=" + msds_hold +
                       "\')\">\n");
      badimgStr.append("<img name=\"img2\" src=\"" + wwwhomeurl + "images/docx2.gif\" alt=\""+res.getString("label.clicktoreport")+"\" border=0></a></td>\n");
    }

    if ( (act.equalsIgnoreCase("msds")) && (msds_hold.equalsIgnoreCase(" ")) &&
        ! (notshow.equalsIgnoreCase("No Records"))) {
      String msdsDir = "/MSDS/";
      int msdsIndex = msds_hold.lastIndexOf(msdsDir) + msdsDir.length();
      msds_hold = msds_hold.substring(msdsIndex);
      labelStr.append("<FONT size=1 face=Arial COLOR=green>SDS/MSDS: <FONT COLOR=000000>" +
                      rev_date_hold + "</FONT></center></td>\n");
      if (hasTIF.startsWith("Y")) {
        String tifImg = msds_hold;
        if (tifImg.endsWith("pdf")) {
          tifImg = tifImg.substring(0, tifImg.lastIndexOf(".") + 1);
          tifImg = tifImg + "tif";
          tifStr.append("<td align=\"right\" valign=\"center\">&nbsp;\n");
          tifStr.append("<a href=\"" + wwwhomeurl + "MSDS/tifs/" + tifImg +
                        "\" target=\"data\">\n");
          tifStr.append("<img name=\"tif\" src=\"" + wwwhomeurl +
                        "images/tif.gif\"></a></td>\n");
        } else {
          // Only worry about PDF
        }
      } else {
        // No TIF available
      }
    } else if (act.equalsIgnoreCase("spec")) {
      labelStr.append(
          "<FONT size=1 face=Arial COLOR=green>"+res.getString("label.spec")+": <FONT COLOR=000000>" + spec_hold +
          "</FONT></center></td>\n");
      /* badimgStr = "<td align=\"right\" valign=\"center\">\n");
          "<a href=\"#\" onClick=\"openwin2(\'"+wwwhomeurl+"cgi-bin/gen/problem_image.cgi?pi=" + spec_hold + "&doc=spec\')\">\n");
          "<img name=\"img2\" src=\""+wwwhomeurl+"images/docx2.gif\" alt=\"Please click to report a problem with the current image\" border=0></a></td>\n";*/

      if (hasTIF.startsWith("Y")) {
        String tifImg = contentspec;
        if (tifImg.endsWith("pdf")) {
          tifImg = spec_hold;
          tifImg = tifImg + ".tif";
          tifStr.append("<td align=\"right\" valign=\"center\">&nbsp;\n");
          tifStr.append("<a href=\"" + wwwhomeurl + "OpSupport/Raytheon/spec_drawings/" +
                        tifImg + "\" target=\"data\">\n");
          tifStr.append("<img name=\"tif\" src=\"" + wwwhomeurl +
                        "images/tif.gif\"></a></td>\n");
        } else {
          // Only worry about PDF
        }
      } else {
        // No TIF available
      }
    } else {
      labelStr.append("<FONT size=1 face=Arial COLOR=green>"+res.getString("label.revisiondate")+": <FONT COLOR=000000>" + date_hold +"</FONT></center></td>\n");
      //Use rev_date_hold if you want the latest date always
      //labelStr = "<FONT size=1 face=Arial COLOR=green>Revision Date: <FONT COLOR=000000>" + rev_date + "</FONT></center></td>\n";
      //mfgDescStr.append("<center><tr><td><font size=1 face=Arial><b>Manufacturer: " + mfgDesc +"</b></font></td></tr></center>");
    }
    router = "";
    badimgStr.append(tailStr);
    tifStr.append(badimgStr);
    labelStr.append(tifStr);
    jscript.append(labelStr);
    out.println(jscript);
    titlev = null;
  }

  public void doAutoDetect(String content, String content2, String radios3, String plug1,
                           String action, String id1, String urlfacility1, String catpartno1, PrintWriter out) {
    //StringBuffer Msgauto =new StringBuffer();
    String msdsservlet = msdsbundle.getString("VIEW_MSDS");

    if (plug1.equalsIgnoreCase("null") || plug1 == null || plug1.equalsIgnoreCase("")) {
      out.println("<HTML><HEAd><TITLE>"+res.getString("title.mainview")+"</title>\n");
      out.println("        <SCRIPT LANGUAGE = \"JavaScript\">\n");
      out.println("<!--\n");
      out.println("    function plugdetect(plugName)\n");
      out.println("         {\n");
      out.println("          browserNN = (navigator.appName == \"Netscape\");\n");
      out.println(
          "          browserIE = (navigator.appName == \"Microsoft Internet Explorer\");\n");
      out.println("           if (browserNN){\n");
      out.println("             if (navigator.plugins[plugName]) return true;\n");
      out.println("             else return false;\n");
      out.println("           }else if (browserIE){\n");
      out.println("             return true;\n");
      out.println("           }\n");
      out.println("           }\n");
      out.println("    function detect(){\n");
      out.println("         if (plugdetect(\"Adobe Acrobat\") == true){\n");
      out.println("             return \"Y\";\n \n");
      out.println("         } else {  \n");
      out.println("             return \"N\";}\n");
      out.println("    }\n");
      out.println("function AutoDetect()\n");
      out.println("{\n");
      // Store the selected option in a variable called ref\n");
      out.println("ref=\"" + radios3.replaceFirst("https?://[^/]+", "") + "\";\n");
      // Count the position of the optional &\n");
      out.println("splitcharacter=ref.lastIndexOf(\"*\");\n");
      out.println("if (splitcharacter!=-1) {loc=ref.substring(0,splitcharacter);\n");
      out.println("target=ref.substring(splitcharacter+1,1000).toLowerCase();}\n");
      out.println("else {loc=ref; target=\"_self\";};\n");
      if (action.equalsIgnoreCase("spec")) {
        out.println("loc=loc + \"&plug=\" + detect() ;\n");
      } else {
        out.println("plug=detect();\n if(plug==\"N\"){loc=\"" + msdsservlet +
                    "SESSION=2&act=msds&id=" + id1 + "&date=null&facility=" + urlfacility1 +
                    "&catpartno=" + catpartno1 + "&spec=null&plug=N\";\n}else{loc=\"" + content.replaceFirst("https?://[^/]+", "") +
                    "\";}\n");
      }
      // create a varible called lowloc to store loc in lowercase\n");
      //characters.\n");
      // Skip the rest of this function if the selected optionvalue is\n");
      //"false".\n");
      out.println("parent.frames[target].location=loc;\n");
      out.println("}\n");
      out.println("// -->\n");
      out.println("</script>\n");
      out.println("</head>\n");
      out.println("<body bgcolor=\"#ffffff\" text=000000 link=000066 alink=8E236B vlink=4A766E onLoad=\"AutoDetect()\"></body></html>\n");
    } else if (plug1.startsWith("Y")) {
      out.println("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; url=" + content.replaceFirst("https?://[^/]+", "") + "\">\n");
    } else {
      out.println(
          "<HTML><body bgcolor=white text=000000>\n<center><table border=0 width=50%>\n");
      // "<img src=\""+wwwhomeurl+"images/buttons/spec/tcmspec.gif\">\n");
      out.println("<tr><td><BR><P>");
      out.println("<FONT face=arial size=3 COLOR=ff0000><b>"+res.getString("msds.msg41")+"</b></FONT>\n");
      out.println("<FONT size=2 face=Arial>"+res.getString("msds.msg42")+"<BR><BR>\n");
      out.println(res.getString("msds.msg43"));
      out.println(res.getString("msds.msg44"));
      out.println(res.getString("msds.msg45")+"<BR><BR><BR>\n");
      out.println("<center><a href=\"http://www.adobe.com/products/acrobat/readstep.html\" target=\"_new\">\n");
      out.println("<img src=\"" + wwwhomeurl +
                  "images/buttons/getacro.gif\" border=0 alt=\""+res.getString("label.adobereader")+"\"></a><BR><BR>\n");
      out.println(res.getString("msds.msg46")+" </center>\n");
      out.println("</td></tr>\n<tr><td><BR><HR>\n");
      out.println(
          "<P><FONT size=2 face=Arial>"+res.getString("msds.msg51")+":</FONT>\n");
      out.println("<FORM method=\"GET\" action=\"" + content + "\">\n");
      out.println("<center><input type=\"Submit\" value=\"" + content2 +
                  "\"></td></tr></table></form></body></html><BR><HR>\n");
      out.println("<center><h3>"+res.getString("msds.msg61")+"</h3><table width=80% border=0><tr><td align=left>\n");
      out.println("<FONT size=2 face=Arial><BR><BR>"+res.getString("msds.msg62"));
      out.println(res.getString("msds.msg63")+"<BR>\n");
      out.println("<ul><li>"+res.getString("msds.msg64")+"</li>\n");
      out.println("<li>"+res.getString("msds.msg65")+"</li>\n");
      out.println("<li>"+res.getString("msds.msg66"));
      out.println("<B>"+res.getString("msds.msg67")+"</b>"+res.getString("msds.msg675"));
      out.println("<B>"+res.getString("msds.msg68")+"</b>"+res.getString("msds.msg685"));
      out.println(res.getString("msds.msg69")+"</li>\n");
      out.println("<li>"+res.getString("msds.msg70"));
      out.println("</ul><BR><BR></td></tr></table>\n");
    }
    return;
  }

  public void buildMSDS(HttpServletRequest requestmsds,PrintWriter out) throws IOException {
    String rev_date = requestmsds.getParameter("date");
    String revDateNoFormat = requestmsds.getParameter("dateNoFormat");
    
    String localeCode = requestmsds.getParameter("localeCode");
    String plug = requestmsds.getParameter("plug");
    String id = requestmsds.getParameter("id");
    String facility = requestmsds.getParameter("facility");
    String listId = requestmsds.getParameter("listid");
    String lookupListId = requestmsds.getParameter("listid");
    String currentRow = requestmsds.getParameter("currentrow");
    String parentName = requestmsds.getParameter("parentname");
    String childName = requestmsds.getParameter("childname");

    if (localeCode == null) {
      localeCode = "";
    }

    if (facility == null) {
      facility = "";
    }
    String URLfacility = facility;
    URLfacility = URLfacility.replace(' ', '+');

    String catpartno = requestmsds.getParameter("catpartno");
    String act = requestmsds.getParameter("act");
    String spec = requestmsds.getParameter("spec");
    if (act == null) {
      act = "msds";
    }
    if (spec == null) {
      spec = "";
    }

    String msdscontent = "";
    String notshow1 = "";
    String material_desc = "";
    String composition = "";
    String density = "";
    String flash_point = "";
    String boiling_point = "";
    String freezing_point = "";
    String spec_grav = "";
    String phy_state = "";
    String trade_name = "";
    String radios3 = "";
    String ground_ship = "";
    String air_ship = "";
    String hazard_class = "";
    String un_num = "";
    String na_num = "";
    String pack_group = "";
    //list
    String listResult = "";
    String subListResult = "";
    String compoundResult = "";
    int count = 0;
    String manufacturer = "";
    String revisionDateDisplay = "";

   String compatibility="";
   String eyes="";
   String ingestion="";
   String inhalation="";
   String injection="";
   String oshaHazard="";
   String personalProtection="";
   String ph="";
   String ppe="";
   String routeOfEntry="";
   String sara311312Acute="";
   String sara311312Chronic="";
   String sara311312Fire="";
   String sara311312Pressure="";
   String sara311312Reactivity="";
   String signalWord="";
   String skin="";
   String solids="";
   String targetOrgan="";
   String tsca12B="";
   String tscaList="";
   String vaporPressure="";
   String voc="";
   String vocLessH2OExempt="";
   String vocPercent="";
   String chronic="";
   String lfcCode="";
   String polymerize="";
   String incompatible="";
   String corrosive="";
   String healthEffects="";
   String stable="";
   String vaporDensity="";
   String evaporationRate="";
   String fireConditionsToAvoid="";
   String alloy="";

     //StringBuffer Msgmsds = new StringBuffer();
    SideView svObj = new SideView(msdsbundle);
    Vector titlev = null;
    try {
      svObj.setLocaleCode(localeCode);
      titlev = svObj.getContentVector(db, rev_date, id, act, spec, catpartno, facility,listId, lookupListId, currentRow,revDateNoFormat);
    } catch (Exception ex) {
      notshow1 = "No Records";
    }
    if (titlev.size() == 0) {
      notshow1 = "No Records";
    } else if (titlev.elementAt(0).toString().equalsIgnoreCase("No Records")) {
      notshow1 = "No Records";
    } else {
		for (int j = 0; j < titlev.size(); j++) {
        SideView sv = (SideView) titlev.elementAt(j);
		  revisionDateDisplay = sv.getDate2();
        if (act.equalsIgnoreCase("msds")) {
        	if (msdscontent.isEmpty()) {
        		msdscontent = sv.getContent();
        	}
        } else if (act.equalsIgnoreCase("spec")) {
          msdscontent = sv.getContent();
          radios3 = sv.getRadios3();
        } else if (act.equalsIgnoreCase("comp")) {
          material_desc = sv.getMaterial();
          composition += sv.getComposition();
          if (j == 0) {
            trade_name = sv.getTradeName();
            manufacturer = sv.getManufacturer();
          }
        } else if (act.equalsIgnoreCase("prop")) {
			 boiling_point = sv.getBoiling();
			 compatibility = BothHelpObjs.isBlankString(sv.getCompatibility())?"noshow":sv.getCompatibility()+" </FONT></td>\n</tr>";
			 density = sv.getDensity();
			 eyes = BothHelpObjs.isBlankString(sv.getEyes())?"noshow":sv.getEyes()+" </FONT></td>\n</tr>";
			 flash_point = sv.getFlash();
          freezing_point = sv.getFreezing();
			 ingestion =  " </FONT></td>\n</tr>";
			 inhalation = BothHelpObjs.isBlankString(sv.getInhalation())?"noshow":sv.getInhalation()+" </FONT></td>\n</tr>";
			 injection = BothHelpObjs.isBlankString(sv.getInjection())?"noshow":sv.getInjection()+" </FONT></td>\n</tr>";
			 oshaHazard =  " </FONT></td>\n</tr>";
			 personalProtection = BothHelpObjs.isBlankString(sv.getPersonalProtection())?"noshow":sv.getPersonalProtection()+" </FONT></td>\n</tr>";
			 ph = BothHelpObjs.isBlankString(sv.getPh())?"noshow":sv.getPh()+" </FONT></td>\n</tr>";
			 phy_state = sv.getPhysicalState();
			 ppe = BothHelpObjs.isBlankString(sv.getPpe())?"noshow":sv.getPpe()+" </FONT></td>\n</tr>";
			 routeOfEntry = BothHelpObjs.isBlankString(sv.getRouteOfEntry())?"noshow":sv.getRouteOfEntry()+" </FONT></td>\n</tr>";
			 sara311312Acute = BothHelpObjs.isBlankString(sv.getSara311312Acute())?"noshow":sv.getSara311312Acute()+" </FONT></td>\n</tr>";
			 sara311312Chronic = BothHelpObjs.isBlankString(sv.getSara311312Chronic())?"noshow":sv.getSara311312Chronic()+" </FONT></td>\n</tr>";
			 sara311312Fire = BothHelpObjs.isBlankString(sv.getSara311312Fire())?"noshow":sv.getSara311312Fire()+" </FONT></td>\n</tr>";
			 sara311312Pressure = BothHelpObjs.isBlankString(sv.getSara311312Pressure())?"noshow":sv.getSara311312Pressure()+" </FONT></td>\n</tr>";
			 sara311312Reactivity = BothHelpObjs.isBlankString(sv.getSara311312Reactivity())?"noshow":sv.getSara311312Reactivity()+" </FONT></td>\n</tr>";
			 signalWord = BothHelpObjs.isBlankString(sv.getSignalWord())?"noshow":sv.getSignalWord()+" </FONT></td>\n</tr>";
			 skin = BothHelpObjs.isBlankString(sv.getSkin())?"noshow":sv.getSkin()+" </FONT></td>\n</tr>";
			 solids = BothHelpObjs.isBlankString(sv.getSolids())?"noshow":sv.getSolids()+" </FONT></td>\n</tr>";
			 spec_grav = sv.getSpecGrav();
			 targetOrgan = " </FONT></td>\n</tr>";
			 tsca12B = BothHelpObjs.isBlankString(sv.getTsca12B())?"noshow":sv.getTsca12B()+" </FONT></td>\n</tr>";
			 tscaList = BothHelpObjs.isBlankString(sv.getTscaList())?"noshow":sv.getTscaList()+" </FONT></td>\n</tr>";
			 vaporPressure = BothHelpObjs.isBlankString(sv.getVaporPressure())?"noshow":sv.getVaporPressure()+" </FONT></td>\n</tr>";
			 voc = BothHelpObjs.isBlankString(sv.getVoc())?"noshow":sv.getVoc()+" </FONT></td>\n</tr>";
			 vocPercent = BothHelpObjs.isBlankString(sv.getVocPercent())?"noshow":sv.getVocPercent()+" </FONT></td>\n</tr>";
			 vocLessH2OExempt = BothHelpObjs.isBlankString(sv.getVocLessH2OExempt())?"noshow":sv.getVocLessH2OExempt()+" </FONT></td>\n</tr>";

            chronic = BothHelpObjs.isBlankString(sv.getChronic())?"noshow":sv.getChronic()+" </FONT></td>\n</tr>";
            lfcCode = BothHelpObjs.isBlankString(sv.getLfcCode())?"noshow":sv.getLfcCode()+" </FONT></td>\n</tr>";
            polymerize = BothHelpObjs.isBlankString(sv.getPolymerize())?"noshow":sv.getPolymerize()+" </FONT></td>\n</tr>";
            incompatible = BothHelpObjs.isBlankString(sv.getIncompatible())?"noshow":sv.getIncompatible()+" </FONT></td>\n</tr>";
            corrosive = BothHelpObjs.isBlankString(sv.getCorrosive())?"noshow":sv.getCorrosive()+" </FONT></td>\n</tr>";
            healthEffects = BothHelpObjs.isBlankString(sv.getHealthEffects())?"noshow":sv.getHealthEffects()+" </FONT></td>\n</tr>";
            stable = BothHelpObjs.isBlankString(sv.getStable())?"noshow":sv.getStable()+" </FONT></td>\n</tr>";
            vaporDensity = BothHelpObjs.isBlankString(sv.getVaporDensity())?"noshow":sv.getVaporDensity()+" </FONT></td>\n</tr>";
            evaporationRate = BothHelpObjs.isBlankString(sv.getEvaporationRate())?"noshow":sv.getEvaporationRate()+" </FONT></td>\n</tr>";
            fireConditionsToAvoid = BothHelpObjs.isBlankString(sv.getFireConditionsToAvoid())?"noshow":sv.getFireConditionsToAvoid()+" </FONT></td>\n</tr>";
            alloy =  " </FONT></td>\n</tr>";

            trade_name = sv.getTradeName();

			 if (trade_name == null) {
            trade_name =
                "<td><FONT FACE=\"Arial\" SIZE=\"2\" COLOR=red>"+res.getString("label.notentered")+"</FONT></FONT></td>\n</tr>";
          }
          if (boiling_point.startsWith("null")) {
            boiling_point = "noshow";
          }
          if (flash_point.startsWith("null")) {
            flash_point = "noshow";
          }
          if (density.startsWith("null")) {
            density = "noshow";
          }
          if (phy_state.startsWith("null")) {
            phy_state = "noshow";
          }
          if (spec_grav.startsWith("null")) {
            spec_grav = "noshow";
          }
          if (freezing_point.startsWith("null")) {
            freezing_point = "noshow";
          }

          ground_ship = sv.getGroundShippingName();
          air_ship = sv.getAirShippingName();
          hazard_class = sv.getHazardClass();
          un_num = sv.getUnNumber();
          na_num = sv.getNaNumber();
          pack_group = sv.getPackingGroup();
          if (j == 0) {
            manufacturer = sv.getManufacturer();
          }
        } else if (act.equalsIgnoreCase("lists") || act.equalsIgnoreCase("sublist")) {
          if (j == 0) {
            manufacturer = sv.getManufacturer();
            trade_name = sv.getTradeName();
          }

          String startLink = "<a href=\"" + msdsbundle.getString("VIEW_MSDS") +
              "SESSION=2&id=" + id + "&date=" + rev_date + "&localeCode="+localeCode+"&act=sublist&listid=" + sv.getListId() +
              "#" + sv.getListId() + "\">";
          String compoundLink = "<a href=\"" + msdsbundle.getString("VIEW_MSDS") +
              "SESSION=2&act=compound&listid=" + sv.getListId() + "\" target=\"new\">";

          String color = "";
          //alternate row colors
          if (j % 2 == 1) {
            color = "bgcolor=\"#E6E8FA\"";
          }
          listResult += "<tr><td " + color +
              " ALIGN=\"CENTER\" WIDTH=\"5%\"><FONT FACE=\"Arial\" SIZE=\"2\">";
          //add anchor tag
          listResult += "<a name=\"" + sv.getListId() + "\">&nbsp;</a>";
          //display + sign with link if it has child records
          if ( (sv.getSubList()).equalsIgnoreCase("Yes")) {
            //display - sign if it's expanded
            if (act.equalsIgnoreCase("sublist") && listId.equalsIgnoreCase(sv.getListId())) {
              listResult += "<a href=\"" + msdsbundle.getString("VIEW_MSDS") + "SESSION=2&id=" +
                  id + "&date=" + rev_date +  "&localeCode="+localeCode+ "&act=lists#" + sv.getListId() + "\"><img src=\"" +
                  wwwhomeurl + "images/minus.jpg\"></a>";
            } else {
              listResult += startLink + "<img src=\"" + wwwhomeurl + "images/plus.jpg\"></a>";
            }
          } else {
            listResult += "&nbsp";
          }
          listResult += "</FONT></td><td " + color +
              " ALIGN=\"LEFT\" WIDTH=\"65%\"><FONT FACE=\"Arial\" SIZE=\"2\"><b>" + "<a href=\"" +
              msdsbundle.getString("VIEW_MSDS") + "SESSION=2&act=compound&parentname=" +
              sv.getListName() + "&listid=" + sv.getListId() + "\" target=\"new\">" +
              sv.getListName() + "</a></b></FONT></td>" + "<td " + color +
              " ALIGN=\"CENTER\" WIDTH=\"15%\"><FONT FACE=\"Arial\" SIZE=\"2\">";
          if (sv.getOnList().equalsIgnoreCase("Yes")) {
            listResult += "<a href=\"" + msdsbundle.getString("VIEW_MSDS") +
                "SESSION=2&act=compoundpercent&date=" + rev_date +"&localeCode="+localeCode+"&id=" + id + "&parentname=" +
                sv.getListName() + "&listid=" + sv.getListId() + "\" target=\"new\">" +
                sv.getOnList() + "</a>";
          } else {
            listResult += sv.getOnList();
          }
          listResult += "</font></td>" + "<td " + color +
              " ALIGN=\"CENTER\" WIDTH=\"15%\"><FONT FACE=\"Arial\" SIZE=\"2\">";
          //only display percent if it is not 0
          if (sv.getPercent() != null && ! (sv.getPercent().equalsIgnoreCase("0"))) {
            listResult += sv.getPercent();
          } else {
            listResult += "&nbsp;";
          }
          listResult += "</font></td></tr>\n";
          if (act.equalsIgnoreCase("sublist") && listId.equalsIgnoreCase(sv.getListId())) {

            for (int i = 0; i < sv.getSubListVector().size(); i++) {
              String subColor = "";
              if (i % 2 == 0) {
                subColor = "";
              }
              listResult += "<tr><td " + subColor + ">&nbsp;</td><td " + subColor +
                  "><FONT FACE=\"Arial\" SIZE=\"2\"><I>" + "<a href=\"" +
                  msdsbundle.getString("VIEW_MSDS") + "SESSION=2&act=compound&parentname=" +
                  sv.getListName() + "&childname=" +
                  ( (Vector) sv.getSubListVector().elementAt(i)).elementAt(0) + "&listid=" +
                  ( (Vector) sv.getSubListVector().elementAt(i)).elementAt(1) +
                  "\" target=\"new\">" + ( (Vector) sv.getSubListVector().elementAt(i)).elementAt(0) +
                  "</a></I></font></td><td " + subColor +
                  " align=\"center\"><FONT FACE=\"Arial\" SIZE=\"2\">";
              if ( ( (String) ( (Vector) sv.getSubListVector().elementAt(i)).elementAt(2)).
                  equalsIgnoreCase("Yes")) {
                listResult += "<a href=\"" + msdsbundle.getString("VIEW_MSDS") +
                    "SESSION=2&act=compoundpercent&date=" + rev_date + "&id=" + id + "&parentname=" +
                    sv.getListName() + "&childname=" +
                    ( (Vector) sv.getSubListVector().elementAt(i)).elementAt(0) + "&listid=" +
                    ( (Vector) sv.getSubListVector().elementAt(i)).elementAt(1) +
                    "\" target=\"new\">" +
                    ( (Vector) sv.getSubListVector().elementAt(i)).elementAt(2) + "</a>";
              } else {
                listResult += ( (Vector) sv.getSubListVector().elementAt(i)).elementAt(2);
              }
              listResult += "</font></td><td " + subColor +
                  " align=\"center\"><FONT FACE=\"Arial\" SIZE=\"2\">";
              if ( ( (Vector) sv.getSubListVector().elementAt(i)).elementAt(3) != null) {
                listResult += ( (Vector) sv.getSubListVector().elementAt(i)).elementAt(3);
              } else {
                listResult += "&nbsp;";
              }
              listResult += "</font></td></tr>\n";
            }
          }
        } else if (act.equalsIgnoreCase("compound")) {
          String color = "";
          //alternate row colors
          if (j % 2 == 1) {
            color = "bgcolor=\"#E6E8FA\"";
          }

          compoundResult += "<tr><td " + color +
              " valign=\"top\" align=\"right\"><FONT FACE=\"Arial\" SIZE=\"2\">" +
              sv.getCasNumber() + "</font></td>" + "<td " + color +
              " valign=\"top\" align=\"left\"><FONT FACE=\"Arial\" SIZE=\"2\">" +
              sv.getRptChemical() + "</font></td></tr>";
          //set count to see if we need more results later
          count = sv.getCount();
        } else if (act.equalsIgnoreCase("compoundpercent")) {
          String color = "";
          //alternate row colors
          if (j % 2 == 1) {
            color = "bgcolor=\"#E6E8FA\"";
          }

          compoundResult += "<tr><td " + color +
              " valign=\"top\" align=\"right\"><FONT FACE=\"Arial\" SIZE=\"2\">" + sv.getPercent() +
              "</font></td>" + "<td " + color +
              " valign=\"top\" align=\"right\"><FONT FACE=\"Arial\" SIZE=\"2\">" +
              sv.getCasNumber() + "</font></td>" + "<td " + color +
              " valign=\"top\" align=\"left\"><FONT FACE=\"Arial\" SIZE=\"2\">" +
              sv.getRptChemical() + "</font></td></tr>";
        }

      }
    }

    String testurl2 = msdscontent;

    int isss = 0;

    if (act.equalsIgnoreCase("msds") && !testurl2.endsWith("/")) {
      try {
        URL url = new URL(testurl2.replaceFirst("https?://[^/]+", "http://localhost"));
        HttpURLConnection huc = (HttpURLConnection) url.openConnection();
        isss = huc.getResponseCode();
      } catch (IOException ex1) {
        isss = 404;
      }
    } else if (act.equalsIgnoreCase("spec")) {
      if (msdscontent.startsWith("http://")) {
        testurl2 = msdscontent;
      } else {
        testurl2 = "" + wwwhomeurl + "OpSupport/Raytheon/spec_drawings/" + msdscontent;
      }
      if (!testurl2.endsWith("/")) {
        try {
          URL url = new URL(testurl2.replaceFirst("https?://[^/]+", "http://localhost"));
          HttpURLConnection huc = (HttpURLConnection) url.openConnection();
          isss = huc.getResponseCode();
        } catch (IOException ex2) {
          isss = 404;
        }
      }
    }

    if (isss == 404) {
      out.println("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; url=" + wwwhomeurl +
                  "gen/msds_not_found.html\">\n");
    } else if (act.equalsIgnoreCase("msds")) {
      if (isss == 404 ||
          msdscontent.trim().equalsIgnoreCase("" + wwwhomeurl + "gen/msds_not_found.html")) {
        out.println("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; url=" + wwwhomeurl +
                    "gen/msds_not_found.html\">\n");
      } else if (msdscontent.endsWith(".pdf")) {
        radios3 = msdscontent + "*data";
        String content2 = msdscontent.substring(33);
        doAutoDetect(msdscontent, content2, radios3, plug, act, id, URLfacility, catpartno,
                     out);
      } else {
        out.println("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; url=" + msdscontent.replaceFirst("https?://[^/]+", "") + "\">\n");
      }

    } else if (act.equalsIgnoreCase("spec")) {
      if (msdscontent.startsWith("http")) {
        out.println("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; url=" + msdscontent.replaceFirst("https?://[^/]+", "") + "\">\n");
      } else {
        if (msdscontent.endsWith(".pdf")) {
          String content2 = "";
          content2 = msdscontent;
          msdscontent = "" + wwwhomeurl + "OpSupport/Raytheon/spec_drawings/" + msdscontent;
          doAutoDetect(msdscontent, content2, radios3, plug, act, id, URLfacility, catpartno,
                       out);
        } else {
          out.println("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; url=" + wwwhomeurl +
                      "OpSupport/Raytheon/spec_drawings/" + msdscontent + "\">\n");
        }
      }
    } else if (act.equalsIgnoreCase("comp")) {
      out.println("<HTML><HEAd>\n");
      out.println("<TITLE>"+res.getString("label.chemicalfor")+" " + material_desc + "</title>\n");
      out.println("</head>\n");
      out.println("<body bgcolor=\"#FFFFFF\"><center>\n");
      out.println("<BR><BR>\n");
      out.println("<div align=\"center\"><center>\n");
      out.println("<TABLE BORDER=\"0\" CELLPADDING=\"5\" WIDTH=\"90%\" >\n");
      out.println("    <tr>\n");
      out.println("        <td align=\"CENTER\" BGCOLOR=\"#000066\" ><FONT SIZE=\"3\" WIDTH=\"15%\" FACE=\"Arial\" COLOR=\"#ffffff\"><B>"+res.getString("label.casno")+"</B></FONT></TD>\n");
      out.println("        <td align=\"CENTER\" BGCOLOR=\"#000066\" ><FONT SIZE=\"3\" WIDTH=\"55%\" FACE=\"Arial\" COLOR=\"#ffffff\"><B>"+res.getString("label.name")+"</B></FONT></TD>\n");
      out.println("        <td align=\"CENTER\" BGCOLOR=\"#000066\" ><FONT SIZE=\"3\" WIDTH=\"10%\" FACE=\"Arial\" COLOR=\"#ffffff\"><B>"+res.getString("label.lowerpercent")+"</B></FONT></TD>\n");
      out.println("        <td align=\"CENTER\" BGCOLOR=\"#000066\" ><FONT SIZE=\"3\" WIDTH=\"10%\" FACE=\"Arial\" COLOR=\"#ffffff\"><B>"+res.getString("label.upperpercent")+"</B></FONT></TD>\n");
      out.println("        <td align=\"CENTER\" BGCOLOR=\"#000066\" ><FONT SIZE=\"3\" WIDTH=\"10%\" FACE=\"Arial\" COLOR=\"#ffffff\"><B>"+res.getString("label.avepercent")+"</B></FONT></TD>\n");
      out.println("    </tr>\n");
      out.println(composition);
      out.println("\n</table></center></div><BR>\n");
      out.println(res.getString("msg.avgepaguid")+"<BR>\n");
      out.println("<center><TABLE BORDER=\"1\" CELLPADDING=\"5\" WIDTH=\"70%\" ><TR><TD>\n");
      out.println(res.getString("msds.msg71"));
      out.println(res.getString("msds.msg72"));
      out.println(res.getString("msds.msg73"));
      out.println(res.getString("msds.msg74"));
      out.println(res.getString("msds.msg75"));
      out.println(res.getString("msds.msg76"));
      out.println(res.getString("msds.msg77"));
      out.println(res.getString("msds.msg78"));
      out.println(res.getString("msds.msg79"));
      out.println(res.getString("msds.msg80"));
      out.println(res.getString("msds.msg81"));
      out.println(res.getString("msds.msg82"));
      out.println(res.getString("msds.msg83"));
      out.println(res.getString("msds.msg84"));
      out.println("</TD></TR></center></table>\n");
      out.println("</body></html>\n");
    } else if (act.equalsIgnoreCase("prop")) {
		out.println("<HTML><HEAd>\n");
      out.println(
          "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.println("<meta name=\"GENERATOR\" content=\"Microsoft FrontPage 2.0\">\n");
      out.println("<TITLE>"+res.getString("label.properfor") + trade_name + "</title>\n");
      out.println("</head>\n");
      out.println("<body bgcolor=\"#FFFFFF\"><center>\n");
      out.println("<p>\n");
      out.println("<p>\n");
      out.println("<div align=\"center\"><center>\n");
      out.println("<TABLE BORDER=\"0\" CELLPADDING=\"5\" WIDTH=\"65%\" >\n");
      out.println("<tr><td colspan=2 bgcolor=\"#000066\" align=\"center\"><FONT FACE=\"Arial\" SIZE=\"2\" COLOR=\"#ffffff\"><B>"+res.getString("label.matpro")+"</B></FONT></td></tr>\n");

		String blueLine = "<td BGCOLOR=\"E6E8FA\"><FONT FACE=\"Arial\" SIZE=\"2\" COLOR=\"#000066\"><B>";
		String blueLine2 = "</B></FONT></td>\n<td BGCOLOR=\"E6E8FA\"><FONT FACE=\"Arial\" SIZE=\"2\">";
		String whiteLine = "<td><FONT FACE=\"Arial\" SIZE=\"2\" COLOR=\"#000066\"><B>";
		String whiteLine2 = "</B></FONT></td>\n<td><FONT FACE=\"Arial\" SIZE=\"2\">";
		String currentLine = whiteLine;
		String currentLine2 = whiteLine2;
		int lineCount = 0;
		//first line
		if (! (boiling_point.equalsIgnoreCase("noshow"))) {
        out.println("<tr>\n");
        out.println(currentLine+res.getString("label.boilingpoint")+currentLine2+boiling_point + "\n");
		  lineCount++;
		}
		if (lineCount%2 == 0) {
			currentLine = whiteLine;
			currentLine2 = whiteLine2;
		}else {
			currentLine = blueLine;
			currentLine2 = blueLine2;
		}
		if (!(compatibility.equalsIgnoreCase("noshow"))) {
        out.println("<tr>\n");
        out.println(currentLine+res.getString("label.compatibility")+currentLine2+compatibility+"\n");
		  lineCount++;
		} 
		if (lineCount%2 == 0) {
			currentLine = whiteLine;
			currentLine2 = whiteLine2;
		}else {
			currentLine = blueLine;
			currentLine2 = blueLine2;
		}
		if (! (density.equalsIgnoreCase("noshow"))) {
        out.println("<tr>\n");
        out.println(currentLine+res.getString("label.density")+currentLine2+density + "\n");
		  lineCount++;
		}
		if (lineCount%2 == 0) {
			currentLine = whiteLine;
			currentLine2 = whiteLine2;
		}else {
			currentLine = blueLine;
			currentLine2 = blueLine2;
		}
		if (!(eyes.equalsIgnoreCase("noshow"))) {
        out.println("<tr>\n");
        out.println(currentLine+res.getString("label.eyes")+currentLine2+eyes+"\n");
		  lineCount++;
		}
		if (lineCount%2 == 0) {
			currentLine = whiteLine;
			currentLine2 = whiteLine2;
		}else {
			currentLine = blueLine;
			currentLine2 = blueLine2;
		}
		if (! (flash_point.equalsIgnoreCase("noshow"))) {
        out.println("<tr>\n");
        out.println(currentLine+res.getString("label.flashpoint")+currentLine2+flash_point + "\n");
		  lineCount++;
		}
		if (lineCount%2 == 0) {
			currentLine = whiteLine;
			currentLine2 = whiteLine2;
		}else {
			currentLine = blueLine;
			currentLine2 = blueLine2;
		}
		if (! (freezing_point.equalsIgnoreCase("noshow"))) {
        out.println("<tr>\n");
        out.println(currentLine+res.getString("label.freezingpoint")+currentLine2+freezing_point + "\n");
		  lineCount++;
		}
		if (lineCount%2 == 0) {
			currentLine = whiteLine;
			currentLine2 = whiteLine2;
		}else {
			currentLine = blueLine;
			currentLine2 = blueLine2;
		}
		/*if (!(ingestion.equalsIgnoreCase("noshow"))) {
        out.println("<tr>\n");
        out.println(currentLine+res.getString("label.ingestion")+currentLine2+ingestion+"\n");
		  lineCount++;
		}
		if (lineCount%2 == 0) {
			currentLine = whiteLine;
			currentLine2 = whiteLine2;
		}else {
			currentLine = blueLine;
			currentLine2 = blueLine2;
		}*/
		if (!(inhalation.equalsIgnoreCase("noshow"))) {
        out.println("<tr>\n");
        out.println(currentLine+res.getString("label.inhalation")+currentLine2+inhalation+"\n");
		  lineCount++;
		}
		if (lineCount%2 == 0) {
			currentLine = whiteLine;
			currentLine2 = whiteLine2;
		}else {
			currentLine = blueLine;
			currentLine2 = blueLine2;
		}
		if (!(injection.equalsIgnoreCase("noshow"))) {
        out.println("<tr>\n");
        out.println(currentLine+res.getString("label.injection")+currentLine2+injection+"\n");
		  lineCount++;
		}
		if (lineCount%2 == 0) {
			currentLine = whiteLine;
			currentLine2 = whiteLine2;
		}else {
			currentLine = blueLine;
			currentLine2 = blueLine2;
		}
		/*if (!(oshaHazard.equalsIgnoreCase("noshow"))) {
        out.println("<tr>\n");
        out.println(currentLine+res.getString("label.oshahazard")+currentLine2+oshaHazard+"\n");
		  lineCount++;
		}
		if (lineCount%2 == 0) {
			currentLine = whiteLine;
			currentLine2 = whiteLine2;
		}else {
			currentLine = blueLine;
			currentLine2 = blueLine2;
		}*/
		if (!(personalProtection.equalsIgnoreCase("noshow"))) {
        out.println("<tr>\n");
        out.println(currentLine+res.getString("label.personalProtection")+currentLine2+personalProtection+"\n");
		  lineCount++;
		}
		if (lineCount%2 == 0) {
			currentLine = whiteLine;
			currentLine2 = whiteLine2;
		}else {
			currentLine = blueLine;
			currentLine2 = blueLine2;
		}
		if (!(ph.equalsIgnoreCase("noshow"))) {
        out.println("<tr>\n");
        out.println(currentLine+res.getString("label.ph")+currentLine2+ph+"\n");
		  lineCount++;
		}
		if (lineCount%2 == 0) {
			currentLine = whiteLine;
			currentLine2 = whiteLine2;
		}else {
			currentLine = blueLine;
			currentLine2 = blueLine2;
		}
		if (! (phy_state.equalsIgnoreCase("noshow"))) {
        out.println("<tr>\n");
        out.println(currentLine+res.getString("label.physicalstate")+currentLine2+phy_state + "\n");
		  lineCount++;
		}
		if (lineCount%2 == 0) {
			currentLine = whiteLine;
			currentLine2 = whiteLine2;
		}else {
			currentLine = blueLine;
			currentLine2 = blueLine2;
		}
		if (!(ppe.equalsIgnoreCase("noshow"))) {
        out.println("<tr>\n");
        out.println(currentLine+res.getString("label.personalProtectiveEquip")+currentLine2+ppe+"\n");
		  lineCount++;
		}
		if (lineCount%2 == 0) {
			currentLine = whiteLine;
			currentLine2 = whiteLine2;
		}else {
			currentLine = blueLine;
			currentLine2 = blueLine2;
		}
		if (!(routeOfEntry.equalsIgnoreCase("noshow"))) {
        out.println("<tr>\n");
        out.println(currentLine+res.getString("label.routeofentry")+currentLine2+routeOfEntry+"\n");
		  lineCount++;
		}
		if (lineCount%2 == 0) {
			currentLine = whiteLine;
			currentLine2 = whiteLine2;
		}else {
			currentLine = blueLine;
			currentLine2 = blueLine2;
		}
		if (!(sara311312Acute.equalsIgnoreCase("noshow"))) {
        out.println("<tr>\n");
        out.println(currentLine+res.getString("label.sara311312acute")+currentLine2+sara311312Acute+"\n");
		  lineCount++;
		}
		if (lineCount%2 == 0) {
			currentLine = whiteLine;
			currentLine2 = whiteLine2;
		}else {
			currentLine = blueLine;
			currentLine2 = blueLine2;
		}
		if (!(sara311312Chronic.equalsIgnoreCase("noshow"))) {
        out.println("<tr>\n");
        out.println(currentLine+res.getString("label.sara311312chronic")+currentLine2+sara311312Chronic+"\n");
		  lineCount++;
		}
		if (lineCount%2 == 0) {
			currentLine = whiteLine;
			currentLine2 = whiteLine2;
		}else {
			currentLine = blueLine;
			currentLine2 = blueLine2;
		}
		if (!(sara311312Fire.equalsIgnoreCase("noshow"))) {
        out.println("<tr>\n");
        out.println(currentLine+res.getString("label.sara311312fire")+currentLine2+sara311312Fire+"\n");
		  lineCount++;
		}
		if (lineCount%2 == 0) {
			currentLine = whiteLine;
			currentLine2 = whiteLine2;
		}else {
			currentLine = blueLine;
			currentLine2 = blueLine2;
		}
		if (!(sara311312Pressure.equalsIgnoreCase("noshow"))) {
        out.println("<tr>\n");
        out.println(currentLine+res.getString("label.sara311312pressure")+currentLine2+sara311312Pressure+"\n");
		  lineCount++;
		}
		if (lineCount%2 == 0) {
			currentLine = whiteLine;
			currentLine2 = whiteLine2;
		}else {
			currentLine = blueLine;
			currentLine2 = blueLine2;
		}
		if (! (sara311312Reactivity.equalsIgnoreCase("noshow"))) {
        out.println("<tr>\n");
        out.println(currentLine+res.getString("label.sara311312reactivity")+currentLine2+sara311312Reactivity + "\n");
		  lineCount++;
		}
		if (lineCount%2 == 0) {
			currentLine = whiteLine;
			currentLine2 = whiteLine2;
		}else {
			currentLine = blueLine;
			currentLine2 = blueLine2;
		}
		if (! (signalWord.equalsIgnoreCase("noshow"))) {
        out.println("<tr>\n");
        out.println(currentLine+res.getString("label.signalWord")+currentLine2+signalWord + "\n");
		  lineCount++;
		}
		if (lineCount%2 == 0) {
			currentLine = whiteLine;
			currentLine2 = whiteLine2;
		}else {
			currentLine = blueLine;
			currentLine2 = blueLine2;
		}
		if (! (skin.equalsIgnoreCase("noshow"))) {
        out.println("<tr>\n");
        out.println(currentLine+res.getString("label.skin")+currentLine2+skin + "\n");
		  lineCount++;
		}
		if (lineCount%2 == 0) {
			currentLine = whiteLine;
			currentLine2 = whiteLine2;
		}else {
			currentLine = blueLine;
			currentLine2 = blueLine2;
		}
		if (! (solids.equalsIgnoreCase("noshow"))) {
        out.println("<tr>\n");
        out.println(currentLine+res.getString("label.solids")+currentLine2+solids + "\n");
		  lineCount++;
		}
		if (lineCount%2 == 0) {
			currentLine = whiteLine;
			currentLine2 = whiteLine2;
		}else {
			currentLine = blueLine;
			currentLine2 = blueLine2;
		}
		if (! (spec_grav.equalsIgnoreCase("noshow"))) {
        out.println("<tr>\n");
        out.println(currentLine+res.getString("label.specificgravity")+currentLine2+spec_grav + "\n");
		  lineCount++;
		}
		if (lineCount%2 == 0) {
			currentLine = whiteLine;
			currentLine2 = whiteLine2;
		}else {
			currentLine = blueLine;
			currentLine2 = blueLine2;
		}
		/*if (! (targetOrgan.equalsIgnoreCase("noshow"))) {
        out.println("<tr>\n");
        out.println(currentLine+res.getString("label.targetorgan")+currentLine2+targetOrgan + "\n");
		  lineCount++;
		} 
		if (lineCount%2 == 0) {
			currentLine = whiteLine;
			currentLine2 = whiteLine2;
		}else {
			currentLine = blueLine;
			currentLine2 = blueLine2;
		}*/
		if (! (tsca12B.equalsIgnoreCase("noshow"))) {
        out.println("<tr>\n");
        out.println(currentLine+res.getString("label.tsca12b")+currentLine2+tsca12B + "\n");
		  lineCount++;
		}
		if (lineCount%2 == 0) {
			currentLine = whiteLine;
			currentLine2 = whiteLine2;
		}else {
			currentLine = blueLine;
			currentLine2 = blueLine2;
		}
		if (! (tscaList.equalsIgnoreCase("noshow"))) {
        out.println("<tr>\n");
        out.println(currentLine+res.getString("label.tscalist")+currentLine2+tscaList + "\n");
		  lineCount++;	
		}
		if (lineCount%2 == 0) {
			currentLine = whiteLine;
			currentLine2 = whiteLine2;
		}else {
			currentLine = blueLine;
			currentLine2 = blueLine2;
		}
		if (! (vaporPressure.equalsIgnoreCase("noshow"))) {
        out.println("<tr>\n");
        out.println(currentLine+res.getString("label.vaporpressure")+currentLine2+vaporPressure + "\n");
		  lineCount++;
		}
		if (lineCount%2 == 0) {
			currentLine = whiteLine;
			currentLine2 = whiteLine2;
		}else {
			currentLine = blueLine;
			currentLine2 = blueLine2;
		}
		if (! (voc.equalsIgnoreCase("noshow"))) {
        out.println("<tr>\n");
        out.println(currentLine+res.getString("label.voc")+currentLine2+voc + "\n");
		  lineCount++;
		}
		if (lineCount%2 == 0) {
			currentLine = whiteLine;
			currentLine2 = whiteLine2;
		}else {
			currentLine = blueLine;
			currentLine2 = blueLine2;
		}
		if (! (vocPercent.equalsIgnoreCase("noshow"))) {
        out.println("<tr>\n");
        out.println(currentLine+res.getString("label.voc")+" (weight %)"+currentLine2+vocPercent + "\n");
		  lineCount++;
		}
		if (lineCount%2 == 0) {
			currentLine = whiteLine;
			currentLine2 = whiteLine2;
		}else {
			currentLine = blueLine;
			currentLine2 = blueLine2;
		}
		if (! (vocLessH2OExempt.equalsIgnoreCase("noshow"))) {
        out.println("<tr>\n");
        out.println(currentLine+res.getString("label.voclesswaterandexempt")+currentLine2+vocLessH2OExempt + "\n");
		  lineCount++;
		} 

        if (! (chronic.equalsIgnoreCase("noshow"))) {
        out.println("<tr>\n");
        out.println(currentLine+res.getString("label.chronic")+currentLine2+chronic + "\n");
		  lineCount++;
		}
		if (lineCount%2 == 0) {
			currentLine = whiteLine;
			currentLine2 = whiteLine2;
		}else {
			currentLine = blueLine;
			currentLine2 = blueLine2;
		}

        if (! (lfcCode.equalsIgnoreCase("noshow"))) {
        out.println("<tr>\n");
        out.println(currentLine+res.getString("label.lfccode")+currentLine2+lfcCode + "\n");
		  lineCount++;
		}
		if (lineCount%2 == 0) {
			currentLine = whiteLine;
			currentLine2 = whiteLine2;
		}else {
			currentLine = blueLine;
			currentLine2 = blueLine2;
		}

        if (! (polymerize.equalsIgnoreCase("noshow"))) {
        out.println("<tr>\n");
        out.println(currentLine+res.getString("label.polymerize")+currentLine2+polymerize + "\n");
		  lineCount++;
		}
		if (lineCount%2 == 0) {
			currentLine = whiteLine;
			currentLine2 = whiteLine2;
		}else {
			currentLine = blueLine;
			currentLine2 = blueLine2;
		}

        if (! (incompatible.equalsIgnoreCase("noshow"))) {
        out.println("<tr>\n");
        out.println(currentLine+res.getString("label.incompatible")+currentLine2+incompatible + "\n");
		  lineCount++;
		}
		if (lineCount%2 == 0) {
			currentLine = whiteLine;
			currentLine2 = whiteLine2;
		}else {
			currentLine = blueLine;
			currentLine2 = blueLine2;
		}

        if (! (corrosive.equalsIgnoreCase("noshow"))) {
        out.println("<tr>\n");
        out.println(currentLine+res.getString("label.corrosive")+currentLine2+corrosive + "\n");
		  lineCount++;
		}
		if (lineCount%2 == 0) {
			currentLine = whiteLine;
			currentLine2 = whiteLine2;
		}else {
			currentLine = blueLine;
			currentLine2 = blueLine2;
		}

        if (! (healthEffects.equalsIgnoreCase("noshow"))) {
        out.println("<tr>\n");
        out.println(currentLine+res.getString("label.healtheffects")+currentLine2+healthEffects + "\n");
		  lineCount++;
		}
		if (lineCount%2 == 0) {
			currentLine = whiteLine;
			currentLine2 = whiteLine2;
		}else {
			currentLine = blueLine;
			currentLine2 = blueLine2;
		}

        if (! (stable.equalsIgnoreCase("noshow"))) {
        out.println("<tr>\n");
        out.println(currentLine+res.getString("label.stable")+currentLine2+stable + "\n");
		  lineCount++;
		}
		if (lineCount%2 == 0) {
			currentLine = whiteLine;
			currentLine2 = whiteLine2;
		}else {
			currentLine = blueLine;
			currentLine2 = blueLine2;
		}

        if (! (vaporDensity.equalsIgnoreCase("noshow"))) {
        out.println("<tr>\n");
        out.println(currentLine+res.getString("label.vapordensity")+currentLine2+vaporDensity + "\n");
		  lineCount++;
		}
		if (lineCount%2 == 0) {
			currentLine = whiteLine;
			currentLine2 = whiteLine2;
		}else {
			currentLine = blueLine;
			currentLine2 = blueLine2;
		}

        if (! (evaporationRate.equalsIgnoreCase("noshow"))) {
        out.println("<tr>\n");
        out.println(currentLine+res.getString("label.evaporationrate")+currentLine2+evaporationRate + "\n");
		  lineCount++;
		}
		if (lineCount%2 == 0) {
			currentLine = whiteLine;
			currentLine2 = whiteLine2;
		}else {
			currentLine = blueLine;
			currentLine2 = blueLine2;
		}

        if (! (fireConditionsToAvoid.equalsIgnoreCase("noshow"))) {
        out.println("<tr>\n");
        out.println(currentLine+res.getString("label.fireconditionstoavoid")+currentLine2+fireConditionsToAvoid + "\n");
		  lineCount++;
		}
		if (lineCount%2 == 0) {
			currentLine = whiteLine;
			currentLine2 = whiteLine2;
		}else {
			currentLine = blueLine;
			currentLine2 = blueLine2;
		}

        /*if (! (alloy.equalsIgnoreCase("noshow"))) {
        out.println("<tr>\n");
        out.println(currentLine+"Alloy"+currentLine2+alloy + "\n");
		  lineCount++;
		}
		if (lineCount%2 == 0) {
			currentLine = whiteLine;
			currentLine2 = whiteLine2;
		}else {
			currentLine = blueLine;
			currentLine2 = blueLine2;
		}*/
        
        out.println("</table>\n");
      out.println("</center></div><BR><BR><center>\n");
      out.println("</body>\n");
      out.println("</html>\n");
    } else if (act.equalsIgnoreCase("lists") || act.equalsIgnoreCase("sublist")) {
      out.println("<HTML><HEAd>\n");
      out.println(
          "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.println("<meta name=\"GENERATOR\" content=\"Microsoft FrontPage 2.0\">\n");
      out.println("<TITLE>"+res.getString("label.listsfor") + listId + "</title>\n");
      out.println("</head>\n");
      out.println("<body bgcolor=\"#FFFFFF\"><center>\n");
      out.println("<p>\n");
      out.println("<div align=\"center\"><center>\n");
      out.println("<TABLE BORDER=\"0\" CELLPADDING=\"5\" WIDTH=\"65%\" >\n");
      /*
      out.println("<tr><td colspan=\"4\" align=\"LEFT\"><FONT SIZE=\"3\" FACE=\"Arial\"><b>Trade Name:</b>&nbsp;" +
                  trade_name + "</FONT></TD></tr>\n");
      out.println("<tr><td colspan=\"4\" align=\"LEFT\"><FONT SIZE=\"3\" FACE=\"Arial\"><b>Manufacturer:</b>&nbsp;" +
                  manufacturer + "</FONT></TD></tr>\n");
      out.println("<tr><td colspan=\"4\" align=\"LEFT\"><FONT SIZE=\"3\" FACE=\"Arial\"><b>Revision Date:</b>&nbsp;" +
                  revisionDateDisplay + "</FONT></TD></tr>\n");
      */
      out.println("    <tr>\n");
      out.println("        <td align=\"CENTER\" BGCOLOR=\"#000066\" ><FONT SIZE=\"3\" WIDTH=\"10%\" FACE=\"Arial\" COLOR=\"#ffffff\"> </FONT></TD>\n");
      out.println("        <td align=\"CENTER\" BGCOLOR=\"#000066\" ><FONT SIZE=\"3\" WIDTH=\"60%\" FACE=\"Arial\" COLOR=\"#ffffff\"><B>"+res.getString("label.list")+"</B></FONT></TD>\n");
      out.println("        <td align=\"CENTER\" BGCOLOR=\"#000066\" ><FONT SIZE=\"3\" WIDTH=\"15%\" FACE=\"Arial\" COLOR=\"#ffffff\"><B>"+res.getString("label.onlist")+"</B></FONT></TD>\n");
      out.println("        <td align=\"CENTER\" BGCOLOR=\"#000066\" ><FONT SIZE=\"3\" WIDTH=\"15%\" FACE=\"Arial\" COLOR=\"#ffffff\"><B>%</B></FONT></TD>\n");
      out.println("    </tr>\n");
      out.println(listResult);
      out.println("</table>\n");
      out.println("</center></div><BR><BR><center>\n");
      out.println("</body>\n");
      out.println("</html>\n");

    } else if (act.equalsIgnoreCase("compound")) {
      out.println("<HTML><HEAd>\n");
      out.println(
          "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.println("<meta name=\"GENERATOR\" content=\"Microsoft FrontPage 2.0\">\n");
      out.println("<TITLE>"+res.getString("label.listsfor") + listId + "</title>\n");
      out.println("</head>\n");
      out.println("<body bgcolor=\"#FFFFFF\"><center>\n");
      out.println("<p>\n");
      out.println("<div align=\"center\"><center>\n");
      out.println("<TABLE BORDER=\"0\" CELLPADDING=\"5\" WIDTH=\"100%\" >\n");
      if (childName == null || childName.length() == 0) {
        childName = "N/A";
      }
      out.println(
          "<tr><td colspan=\"2\" align=\"left\"><FONT FACE=\"Arial\" SIZE=\"3\"><b>List:</b>&nbsp;" +
          parentName + "</FONT></td><tr>\n");
      out.println("<tr><td colspan=\"2\" align=\"left\"><FONT FACE=\"Arial\" SIZE=\"3\"><b>"+res.getString("label.sublist")+":</b>&nbsp;" +
                  childName + "</FONT></td><tr>\n");
      out.println("<tr><td bgcolor=\"#000066\" align=\"center\" width=\"15%\"><FONT FACE=\"Arial\" SIZE=\"3\" COLOR=\"#ffffff\"><B>"+res.getString("label.cas")+"</B></FONT></td>" + "<td bgcolor=\"#000066\" align=\"center\" width=\"85%\"><FONT FACE=\"Arial\" SIZE=\"3\" COLOR=\"#ffffff\"><B>"+res.getString("label.CHEMICAL")+"</B></FONT></td></tr>\n");
      out.println(compoundResult);
      if (count > 200) {
        //check if it's the first 200 rows, if not add 200
        String message1 = "";
        String message2 = "";
        if (currentRow == null) {
          //add 200 to currentRow
          currentRow = "200";
        } else {
          int i = new Integer(currentRow).intValue();
          i += 200;
          currentRow = new Integer(i).toString();
        }
        if (currentRow.equalsIgnoreCase("200")) {
          //first rows, no "previous" option displayed
          message1 = "&nbsp;";
        } else {
          //display "previous" message
          int previousRow = new Integer(currentRow).intValue() - 400;
          message1 = "<a href=\"" + msdsbundle.getString("VIEW_MSDS") +
              "SESSION=2&act=compound&parentname=" + parentName + "&childname=" + childName +
              "&listid=" + listId + "&currentrow=" + previousRow + "\">Previous</a>";

        }
        if (count < new Integer(currentRow).intValue()) {
          //don't display "next" message
          message2 = "&nbsp;";
        } else {
          //display "next" message
          message2 = "<a href=\"" + msdsbundle.getString("VIEW_MSDS") +
              "SESSION=2&act=compound&parentname=" + parentName + "&childname=" + childName +
              "&listid=" + listId + "&currentrow=" + currentRow + "\">Next</a>";

        }
        out.println("<tr><td>" + message1 + "</td><td>" + message2 + "</td></tr>");

      }
    } else if (act.equalsIgnoreCase("compoundpercent")) {
      out.println("<HTML><HEAd>\n");
      out.println(
          "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.println("<meta name=\"GENERATOR\" content=\"Microsoft FrontPage 2.0\">\n");
      out.println("<TITLE>"+res.getString("label.listsfor") + listId + "</title>\n");
      out.println("</head>\n");
      out.println("<body bgcolor=\"#FFFFFF\"><center>\n");
      out.println("<p>\n");
      out.println("<div align=\"center\"><center>\n");
      out.println("<TABLE BORDER=\"0\" CELLPADDING=\"5\" WIDTH=\"100%\" >\n");
      if (childName == null || childName.length() == 0) {
        childName = "N/A";
      }
      out.println(
          "<tr><td colspan=\"3\" align=\"left\"><FONT FACE=\"Arial\" SIZE=\"3\"><b>"+res.getString("label.list")+":</b>&nbsp;" +
          parentName + "</FONT></td><tr>\n");
      out.println("<tr><td colspan=\"3\" align=\"left\"><FONT FACE=\"Arial\" SIZE=\"3\"><b>"+res.getString("label.sublist")+":</b>&nbsp;" +
                  childName + "</FONT></td><tr>\n");
      out.println("<tr><td bgcolor=\"#000066\" align=\"center\" width=\"5%\"><FONT FACE=\"Arial\" SIZE=\"3\" COLOR=\"#ffffff\"><B>"+res.getString("label.PERCENT")+"</B></FONT></td>" + "<td bgcolor=\"#000066\" align=\"center\" width=\"15%\"><FONT FACE=\"Arial\" SIZE=\"3\" COLOR=\"#ffffff\"><B>"+res.getString("label.cas")+"</B></FONT></td>" +
                  "<td bgcolor=\"#000066\" align=\"center\" width=\"80%\"><FONT FACE=\"Arial\" SIZE=\"3\" COLOR=\"#ffffff\"><B>"+res.getString("label.CHEMICAL")+"</B></FONT></td></tr>\n");
      out.println(compoundResult);
    }
    out.println("</table>\n");
    out.println("</center></div><BR><BR><center>\n");
    out.println("</body>\n");
    out.println("</html>\n");

    //out.println( Msgmsds );
    titlev = null;
  }
  
  protected Hashtable buildData(HttpServletRequest requestsidedata, Vector v, String revdatelnk, String localeCodeLink) {
	  return buildData(requestsidedata, v, revdatelnk, "", localeCodeLink);
  }

  protected Hashtable buildData(HttpServletRequest requestsidedata, Vector v, String revdatelnk, String revdatenoformatlnk, String localeCodeLink) {
    SideView sv;
    String testforme = "";
    String testforme1 = "";
    String NoRecords = "";
    String content1 = "";
    Hashtable dataall = new Hashtable();
    String msdsservlet = msdsbundle.getString("VIEW_MSDS");

    //String rev_date = requestsidedata.getParameter("date");
    String showspec = requestsidedata.getParameter("showspec");
    String id = requestsidedata.getParameter("id");
    String facility = requestsidedata.getParameter("facility");
    if (facility == null) {
      facility = "";
    }

    String catpartno = requestsidedata.getParameter("catpartno");
    String act = requestsidedata.getParameter("act");
    String client = requestsidedata.getParameter("cl");
    String spec = requestsidedata.getParameter("spec");

    if (act == null) {
      act = "msds";
    }

    String heal = "";
    String flam = "";
    String react = "";
    String haz = "";
    String radios = "";

    String hmisheal = "";
    String hmisflam = "";
    String hmisreact = "";
    String hmisppe = "";
    String clmsdsno = "";

    int K = 0;

    String testurlis = "";

    if (v == null) {
      NoRecords = "No Records";
    } else if (v.elementAt(0).toString().equalsIgnoreCase("No Records")) {
      NoRecords = "No Records";
    } else {
      if (v.size() == 0) {
        testurlis = "<option value=\"*notav\" selected >"+res.getString("label.notavailable")+"</option>\n";
        radios += testurlis;
      } else if (v.size() == 1) {
        sv = (SideView) v.elementAt(0);

        content1 = sv.getContent();
        if (content1.endsWith(".txt") || content1.endsWith(".html")) {
          showPrinticon = true;
          printiconurl = content1;
        }

        String tmpLocale = sv.getLocale();

        testforme = sv.getrevdated();
        testforme1 = sv.getLatestDate();
        flam = sv.getFlam();
        haz = sv.getHaz();
        heal = sv.getHeal();
        react = sv.getReact();

        hmisheal = sv.gethimshealth();
        hmisflam = sv.gethmisflamma();
        hmisreact = sv.gethmisreacti();
        hmisppe = sv.gethmisppe();
        clmsdsno = sv.getclmsdsno();

        String testdate = "";
        testdate = sv.getRadios();
        String selected = "";
        {
          selected = "selected";
        }

        if (testdate.equalsIgnoreCase("notav")) {
          testurlis = "<option value=\"*notav\" " + selected + ">" + testforme1 +
              "</option>\n";
        } else {
          String qurl = "";
          if (spec == null) {
            spec = "";
          }
          if (facility == null) {
            facility = "";
          }
          if (catpartno == null) {
            catpartno = "";
          }
          if (showspec == null) {
            showspec = "";
          }

          qurl = qurl + "&spec=" + spec;
          qurl = qurl + "&facility=" + BothHelpObjs.addSpaceForUrl(facility);
          qurl = qurl + "&catpartno=" + catpartno;
          qurl = qurl + "&showspec=" + showspec;
          qurl = qurl + "&cl=" + client;
          qurl = qurl + "&localeCode=" + sv.getLocaleCode();
       
          testurlis = "<option value=\"" + msdsservlet + "SESSION=4&id=" + id + "" + qurl +
               "&dateNoFormat=" + sv.getRevDateTime() + "&" + "date=" + testforme1 + "*data\" " + selected + ">" + testforme1 + tmpLocale +
              "</option>\n";
        }
        radios += testurlis;

        if (revdatelnk == null || revdatelnk.equalsIgnoreCase("null")) {
          revdatelnk = sv.getLatestDate();
          revdatenoformatlnk = sv.getRevDateTime();
        }
      } else {
        for (int j = 0; j < v.size(); j++) {
          sv = (SideView) v.elementAt(j);
          content1 = sv.getContent();

          String tmpLocale = sv.getLocale();
          String tmpLocaleId = sv.getLocaleCode();

          testforme = sv.getrevdated();
          testforme1 = sv.getLatestDate();
          if ( (revdatelnk == null || revdatelnk.equalsIgnoreCase("null")) && (j == 0)) {
            heal = sv.getHeal();
            haz = sv.getHaz();
            react = sv.getReact();
            flam = sv.getFlam();

            hmisheal = sv.gethimshealth();
            hmisflam = sv.gethmisflamma();
            hmisreact = sv.gethmisreacti();
            hmisppe = sv.gethmisppe();
            clmsdsno = sv.getclmsdsno();

            K = K + 1;
          } else {
            if (! (K > 0)) {
              if (testforme.equalsIgnoreCase(testforme1)) {
                heal = sv.getHeal();
                haz = sv.getHaz();
                react = sv.getReact();
                flam = sv.getFlam();

                hmisheal = sv.gethimshealth();
                hmisflam = sv.gethmisflamma();
                hmisreact = sv.gethmisreacti();
                hmisppe = sv.gethmisppe();
                clmsdsno = sv.getclmsdsno();
              }
            }
          }

          String testdate = "";
          String selected = "";

          if (testforme.equalsIgnoreCase(testforme1)) {
            if (tmpLocaleId.equals(localeCodeLink)) {
              selected = "selected";
            } else {
              selected = "";
            }

            if (content1.endsWith(".txt") || content1.endsWith(".html")) {
              showPrinticon = true;
              printiconurl = content1;
            }
          } else {
            selected = "";
          }

          if (testdate.equalsIgnoreCase("notav")) {
            testurlis = "<option value=\"*notav\" " + selected + ">" + testforme1 +
                "</option>\n";
          } else {
            String qurl = "";
            if (spec == null) {
              spec = "";
            }
            if (facility == null) {
              facility = "";
            }
            if (catpartno == null) {
              catpartno = "";
            }
            if (showspec == null) {
              showspec = "";
            }

            qurl = qurl + "&spec=" + spec;
            qurl = qurl + "&facility=" + BothHelpObjs.addSpaceForUrl(facility);
            qurl = qurl + "&catpartno=" + catpartno;
            qurl = qurl + "&showspec=" + showspec;
            qurl = qurl + "&cl=" + client;
            qurl = qurl + "&localeCode=" + sv.getLocaleCode();

            testurlis = "<option value=\"" + msdsservlet + "SESSION=4&id=" + id + "" + qurl +
            "&dateNoFormat=" + sv.getRevDateTime() + "&" + "date=" + testforme1 + "*data\" " + selected + ">" + testforme1 + tmpLocale +
                "</option>\n";
          }
          radios += testurlis;

          if (revdatelnk == null || revdatelnk.equalsIgnoreCase("null")) {
            revdatelnk = sv.getLatestDate();
            revdatenoformatlnk = sv.getRevDateTime();
          }
        }
      }
    }

    /*if (radios == null ) {radios = "";}
         if (heal == null ) {heal = "";}
         if (flam == null ) {flam = "";}
         if (react == null ) {react = "";}
         if (haz == null ) {haz = "";}*/

    dataall.put("SHOWRECORDS", NoRecords);
    dataall.put("RADIOS", radios);
    dataall.put("HEALTH", heal);
    dataall.put("FLAMABILITY", flam);
    dataall.put("REACTIVITY", react);
    dataall.put("HAZARD", haz);

    dataall.put("HMIS_HEALTH", hmisheal);
    dataall.put("HMIS_FLAMABILITY", hmisflam);
    dataall.put("HMIS_REACTIVITY", hmisreact);
    dataall.put("HMIS_PPE", hmisppe);
    dataall.put("MSDS_NO", clmsdsno);
    dataall.put("REVDATENOFORMATLINK", revdatenoformatlnk);
    dataall.put("REVDATELINK", revdatelnk);

    return dataall;
  }

  protected Hashtable buildData3(Vector v) {
    String NoRecords = "";
    String radios2 = "";
    Hashtable data3 = new Hashtable();

    if (v == null) {
      NoRecords = "No Records";
    }

    if (v.elementAt(0).toString().equalsIgnoreCase("No Records")) {
      NoRecords = "No Records";
    } else {
      for (int j = 0; j < v.size(); j++) {
        SideView sv = (SideView) v.elementAt(j);
        radios2 += sv.getRadios2();
      }
    }

    data3.put("SHOWRECORDS", NoRecords);
    data3.put("RADIOS2", radios2);
    return data3;
  }

  //Get Servlet information
  public String getServletInfo() {
    return "radian.web.servlets.share.msdsSideView Information";
  }
}