/**
 * Title:        Catalog Login Screen
 * Copyright:    Copyright (c) 2001
 * Company:      Radian
 * @author Nawaz Shaik,Rajendar Rajput
 * @version
 *
 * This is the main servlet which handels the login page for a client.
 *
 * Note: Try to create the web pages in such a way that you can always pass all
 * the HTML you want on the right hand side of the page through a <TD> tag element.
 *
 * 02-13-03
 * Adding another randomnumber as there is a chance of getting the same randomnumber twice in a short period of time
 * which can cuase trouble
 *
 * 02-26-03
 * If already logged in from the receiving page not asking them to login again
 * 03-17-03
 * Removed URS Corp in the page
 * 04-30-03
 * Added another testjnlp directory for JDK 1.4
 * 05-08-03 - If login fails I give the option to enter their user name password
 * 06-18-03 - Invalidating the whole session if the user loges out. so that we can start fresh
 * 07-16-03 - Fixed the logout bug. it was giving a blank screen
 * 08-18-03 - Fixed the Seagate Registration error
 * 08-18-03 - Added a http host fo the tcmIS.ico link so that the Icon shows up in the bookmark.
 * 08-26-03 - Removing the java JDK 1.4 update message. It has been a while
 * 09-29-03 - If the client is SWA || DRS || UTC hardcoding the jnlpcodebase to be http it is https for all others
 * 10-15-03 - Using prereleasejnlp for all the prerelease stuff
 * 12-15-03 - Added an extra parameter in the login process so that people login in the client page first can login to hub pages without problem
 * 4-26-04 - if user from CAL then add additional tcmis.jar
 * 05-19-04 - If it is CAL and they need to install JWS giving lilnk to offline install instead of online install from Sun's site
 * 05-25-04 - There is no Ariba ID for seagate anymore logonId is the company_application_id
 * 06-22-05 - Showing the help link only if a person is logged in
 * 01-09-06 - Removed the Flash information log
 */

package radian.web.servlets.share;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.util.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.LoginProcess;
import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.DbHelpers;
import radian.tcmis.server7.share.dbObjs.Facility;
import radian.tcmis.server7.share.dbObjs.Password;
import radian.tcmis.server7.share.dbObjs.Personnel;
import radian.tcmis.server7.share.dbObjs.PersonnelProfile;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.web.HeaderFooter;
import javax.servlet.jsp.jstl.core.Config;

public abstract class UserRegistration extends HttpServlet implements SingleThreadModel {
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }

  //Client Version
  private Vector Vfac = null;
  private TcmISDBModel db = null;
  private String ADDUSER_Servlet = null;
  private String Login_Servlet = null;
  private String Register_Home_Servlet = null;
  private String ChangeUSR_Servlet = null;
  private String TestInstall_Servlet = null;
  private String Register_Home_Servlet1 = null;
  private int allrelease14count1 = 0;
  private DBResultSet dbrs = null;
  private ResultSet rs = null;
  private PrintStream ps = null;
  private boolean ifloggedin = false;
  private HttpSession session = null;
private boolean intcmIsApplication = false;
  //Process the HTTP Get request
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doPost(request, response);
  }

  //Process the HTTP Post request
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    session = request.getSession();
    db = null;
    String Ariba_Flag = "No";
    String payload_ID = "0";
    ADDUSER_Servlet = getBundle().getString("ADDUSER_SERVLET");
    Login_Servlet = getBundle().getString("LOGINREG_SERVLET");
    Register_Home_Servlet = getBundle().getString("ADD_SERVLET");
    ChangeUSR_Servlet = getBundle().getString("CHGUSER_SERVLET");
    TestInstall_Servlet = getBundle().getString("TEST_INSTALL_SERVLET");
    Register_Home_Servlet1 = getBundle().getString("REGISTER_HOME_SERVLET");
		PersonnelBean personnelBean = (PersonnelBean) session.getAttribute("personnelBean");
    /*
		if (personnelBean !=null)
		{
			 Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
			 if (menuItemOvBeanCollection !=null && menuItemOvBeanCollection.size() > 0)
			 {
					 System.out.println("menuItemOvBeanCollection.size()  "+menuItemOvBeanCollection.size()+"");
					 intcmIsApplication = true;
			 }
		}*/
    PrintWriter usrout = response.getWriter();

    /*Don't want to release this till the purchasing department is moved over to the new application - Nawaz 07-14-08*/
    /*I think Purchasing uses the new applicaiton now 12-12-2012*/
    String newWebApplication="";
    try
    {
     newWebApplication = getBundle().get( "NEW_WEB_APPLICATION" ).toString();
    }
    catch (Exception e)
    {
    }

    if ("Yes".equalsIgnoreCase(newWebApplication))
    {
     radian.web.HTMLHelpObj.redirectPage(usrout,"" + getBundle().get("HOME_SERVLET").toString().substring(1) + "",2008,5,1);
 	 return;
    }

    Vfac = new Vector();
    String client = "";
    client = getBundle().getString("DB_CLIENT");

    //pagetitle=client + " <I>tcm</I>IS User Registration";
    String client1 = getBundle().get("DB_CLIENT_NAME").toString();
    db = new RayTcmISDBModel(client1);
    response.setContentType("text/html");


    String User_Action = request.getParameter("useraction");
    if (User_Action == null) {
      User_Action = "Nothing";

    }
    String logoutAction = request.getParameter("logoutAction");
    if (logoutAction == null) {
      logoutAction = "";
    }
    logoutAction.trim();

    if ("logoutclicked".equalsIgnoreCase(logoutAction)) {
      session.setAttribute("clientloginState", "challenge");
      session.invalidate();
      usrout.println("<HTML><HEAD>\n");
      usrout.println("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; url=" + getBundle().getString("WEBS_HOME_WWWS") + "" + getBundle().getString("REGISTER_SERVLET") + "\">\n");
      usrout.println("<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n");
      String httphost = radian.web.tcmisResourceLoader.gethttphosturl();
      usrout.println("<LINK REL=\"SHORTCUT ICON\" HREF=\"" + httphost + "images/buttons/tcmIS.ico\"></LINK>\n");
      usrout.println("<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n");
      usrout.println("<TITLE>Hub Home</TITLE>\n");
      usrout.println("</HEAD>  \n");
      usrout.println("<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">  \n");
      usrout.println("<CENTER><img src=\"/images/tcmintro.gif\"  border=1 align=\"middle\"><P></P><BR>\n");
      usrout.println("<FONT FACE=\"Arial\" size=\"5\" color=\"#000080\"><b>Logging Out ..</b></font><P></P><BR>\n");
      usrout.println("</CENTER>\n");
      usrout.println("</BODY></HTML>    \n");

      User_Action = "";
      return;
    }

    ifloggedin = false;
    String clientlogon = session.getAttribute("clientloginState") == null ? "" : session.getAttribute("clientloginState").toString();

    if ("authenticated".equalsIgnoreCase(clientlogon)) {
      ifloggedin = true;
    }

    String allre14query = "select count(*) from tcmis_prerelease_version where lower(USERNAME) = lower('All') and VERSION = '1.4' ";
    try {
      allrelease14count1 = DbHelpers.countQuery(db, allre14query);
    } catch (Exception e) {
      e.printStackTrace();
      allrelease14count1 = 0;
    }

    try {
      if ("Add".equalsIgnoreCase(User_Action)) {
        if (!getFacilities()) {
          throw new ServletError("Unable to get facilities");
        }

        BuildAddUpdHTML(request, "AddUser", "", usrout);
      } else if (User_Action.equalsIgnoreCase("AddUser")) {
        addUser(request, usrout);
      } else if ("Change".equalsIgnoreCase(User_Action)) {
        if (fetchAndFillForm(request, usrout)) {

        }
        BuildAddUpdHTML(request, "ChgUser", "", usrout);
      } else if ("ChgUser".equalsIgnoreCase(User_Action)) {
        chgUser(request, usrout);
      } else if ("Login".equalsIgnoreCase(User_Action)) {

        String passwd = "";
        passwd = request.getParameter("password");
        if (passwd == null) {
          passwd = "";

        }
        if (ifloggedin) {
          passwd = session.getAttribute("passwd") == null ? "" : session.getAttribute("passwd").toString();
        }

        String loginId = (String) request.getParameter("loginname");
        if (loginId == null) {
          loginId = "";

        }
        if (ifloggedin) {
          loginId = session.getAttribute("loginId") == null ? "" : session.getAttribute("loginId").toString();
        }

        if (UserExists(loginId)) {
          if ("Seagate".equalsIgnoreCase(client)) {
            BuildAddUpdHTML(request, "NoTolBarReg", "This Global Id is not registered.\\n\\n Please use this form to register.", usrout);
          } else {
            ifloggedin = false;
            session.setAttribute("clientloginState", "");
            BuildIntorPage("You are not registered to Login.\\n \\nPlease contact your facility tcmIS Admin.", usrout);
          }
        } else {
          if (!"Seagate".equalsIgnoreCase(client)) {

          }
          session.setAttribute("loginId", loginId);
          session.setAttribute("passwd", passwd);
          //session.setAttribute( "clientloginState","challenge" );

          BuildIntorPage("LOGIN", " ", " ", usrout);
        }
      } else if ("startingtcmis".equalsIgnoreCase(User_Action)) {
        Ariba_Flag = "N";
        String loginId = session.getAttribute("loginId") == null ? "" : session.getAttribute("loginId").toString();

        if (loginId == null) {
          session.setAttribute("LOGGED", "N");
          BuildIntorPage("", usrout);
          return;
        } else if ("null".equalsIgnoreCase(loginId)) {
          session.setAttribute("LOGGED", "N");
          BuildIntorPage("", usrout);
          return;
        }

        UsrLogin(request, Ariba_Flag, payload_ID, usrout);

      } else {
        session.setAttribute("LOGGED", "N");
        BuildIntorPage("", usrout);
      }

    } catch (Exception e) {
      e.printStackTrace();
      BuildIntorPage("ERROR", "", "", usrout);
    } finally {
      db.close();
      usrout.close();
    }
    return;
  }

  private boolean getFacilities() {
    boolean result = false;
    Vfac.clear();
    try {
      Facility facdbobj = null;
      facdbobj = new Facility(db);

      Vfac = facdbobj.getAllFacXRef2();
      if (!Vfac.isEmpty()) {
        result = true;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  private void addUser(HttpServletRequest request, PrintWriter regout) throws ServletException, IOException {
    String Passwordgiven = "";
    String AribaID = "";

    String logonID = request.getParameter("loginname");
    if (logonID == null) {
      logonID = "";

    }
    AribaID = request.getParameter("aribaID");
    if (AribaID == null) {
      AribaID = "";

    }
    String fname = request.getParameter("FName");
    if (fname == null) {
      fname = "";

    }
    String mi = request.getParameter("MName");
    if (mi == null) {
      mi = "";

    }
    String lname = request.getParameter("LName");
    if (lname == null) {
      lname = "";

    }
    String email = request.getParameter("EMail");
    if (email == null) {
      email = "";

    }
    String phone = request.getParameter("Phone");
    if (phone == null) {
      phone = "";

    }
    String altphone = request.getParameter("AltPhone");
    if (altphone == null) {
      altphone = "";

    }
    String pager = request.getParameter("Pager");
    if (pager == null) {
      pager = "";

    }
    String fax = request.getParameter("Fax");
    if (fax == null) {
      fax = "";

    }
    String fac = request.getParameter("Facility");
    if (fac == null) {
      fac = "";

    }
    Passwordgiven = request.getParameter("password1");
    if (Passwordgiven == null) {
      Passwordgiven = "";

    }
    try {
      String facUrl = URLDecoder.decode(fac);
      fac = facUrl;
    } catch (Exception e) {
      e.printStackTrace();
    }

    Hashtable newData = new Hashtable();
    newData.put("FNAME", fname);
    newData.put("LNAME", lname);
    newData.put("MI", mi);
    newData.put("PHONE", phone);
    newData.put("ALTPHONE", altphone);
    newData.put("PAGER", pager);
    newData.put("EMAIL", email);
    newData.put("PASSWORD", "seagate");
    newData.put("FAX", fax);
    newData.put("FAC", fac);
    newData.put("LOGONID", logonID);

    try {
      PersonnelProfile p = new PersonnelProfile(db);
      boolean presult = false;

      if (UserExists(logonID)) {
        presult = p.webInsert(newData);
        if (!presult) {
          BuildAddUpdHTML(request, "AddUser", "An Error Occured \\n Please try again.", regout);
        } else {
          HttpSession session = request.getSession();
          session.setAttribute("loginId", logonID);
          session.setAttribute("passwd", "");

          BuildIntorPage("LOGIN", logonID, "", regout);
        }
      } else {
        BuildAddUpdHTML(request, "NoTolBarReg", "An Error Occured.\\nMake sure you entered the correct Global ID.\\nPlease try again.", regout);
      }
    } catch (Exception e) {
      e.printStackTrace();
      BuildAddUpdHTML(request, "NoTolBarReg", "An Error Occured.\\nMake sure you entered the correct Global ID.\\nPlease try again.", regout);
    }
    return;
  }

  private boolean fetchAndFillForm(HttpServletRequest request, PrintWriter regout) throws ServletException, IOException {
    boolean result = false;
    String logonID = request.getParameter("loginname");
    if (logonID == null) {
      logonID = "";

    }
    String fname = request.getParameter("FName");
    if (fname == null) {
      fname = "";

    }
    String mi = request.getParameter("MName");
    if (mi == null) {
      mi = "";

    }
    String lname = request.getParameter("LName");
    if (lname == null) {
      lname = "";

    }
    String email = request.getParameter("EMail");
    if (email == null) {
      email = "";

    }
    String phone = request.getParameter("Phone");
    if (phone == null) {
      phone = "";

    }
    String altphone = request.getParameter("AltPhone");
    if (altphone == null) {
      altphone = "";

    }
    String pager = request.getParameter("Pager");
    if (pager == null) {
      pager = "";

    }
    String fax = request.getParameter("Fax");
    if (fax == null) {
      fax = "";

    }
    String fac = request.getParameter("Facility");
    if (fac == null) {
      fac = "";

    }
    try {
      if (UserExists(logonID)) {
        printUserDoesNotExists(regout);
        result = false;
      } else {
        Personnel p = new Personnel(db);
        p.load(logonID);
        fname = p.getFirstName();
        if (fname == null) {
          fname = " ";
        }
        lname = p.getLastName();
        if (lname == null) {
          lname = " ";
        }
        mi = p.getMidInitial();
        if (mi == null) {
          mi = " ";
        }
        phone = p.getPhone();
        if (phone == null) {
          phone = " ";
        }
        altphone = p.getAltPhone();
        if (altphone == null) {
          altphone = " ";
        }
        pager = p.getPager();
        if (pager == null) {
          pager = " ";
        }
        email = p.getEmail();
        if (email == null) {
          email = " ";
        }
        fax = p.getFax();
        if (fax == null) {
          fax = " ";
        }
        fac = p.getFacilityId();
        if (fac == null) {
          fac = " ";
        }

        result = true;
      }
    } catch (Exception e) {
      e.printStackTrace();
      printError(regout);
      result = false;
    }
    return result;

  }

  private void chgUser(HttpServletRequest request, PrintWriter regout) throws ServletException, IOException {
    String logonID = request.getParameter("loginname");
    if (logonID == null) {
      logonID = "";

    }
    String personnelIDStr = request.getParameter("PersonnelID");
    if (personnelIDStr == null) {
      personnelIDStr = "";

    }
    String fname = request.getParameter("FName");
    if (fname == null) {
      fname = "";

    }
    String mi = request.getParameter("MName");
    if (mi == null) {
      mi = "";

    }
    String lname = request.getParameter("LName");
    if (lname == null) {
      lname = "";

    }
    String email = request.getParameter("EMail");
    if (email == null) {
      email = "";

    }
    String phone = request.getParameter("Phone");
    if (phone == null) {
      phone = "";

    }
    String altphone = request.getParameter("AltPhone");
    if (altphone == null) {
      altphone = "";

    }
    String pager = request.getParameter("Pager");
    if (pager == null) {
      pager = "";

    }
    String fax = request.getParameter("Fax");
    if (fax == null) {
      fax = "";

    }
    String fac = request.getParameter("Facility");
    if (fac == null) {
      fac = "";

    }
    try {
      String facUrl = URLDecoder.decode(fac);
      fac = facUrl;
    } catch (Exception e) {
      e.printStackTrace();
    }

    //build has table of personal info
    Hashtable newData = new Hashtable();
    newData.put("FNAME", fname);
    newData.put("LNAME", lname);
    newData.put("MI", mi);
    newData.put("PHONE", phone);
    newData.put("ALTPHONE", altphone);
    newData.put("PAGER", pager);
    newData.put("EMAIL", email);
    newData.put("FAX", fax);
    newData.put("FAC", fac);
    newData.put("LOGONID", logonID);
    Integer UID = new Integer(personnelIDStr);
    newData.put("UID", UID);

    try {
      PersonnelProfile p = new PersonnelProfile(db);
      boolean presult = false;

      presult = p.webUpdate(newData);
      if (!presult) {
        printError(regout);
      } else {
        printSuccess("ChgUser", regout);
      }
    } catch (Exception e) {
      e.printStackTrace();
      printError(regout);
    }
    return;
  }

  public String getServletInfo() {
    return "radian.web.servlets.share.UserRegistartion";
  }

  private void printSuccess(String UserAction, PrintWriter regout) {

    if (UserAction.equalsIgnoreCase("AddUser")) {
      printHTMLAddSuccess(regout);
    }
    if (UserAction.equalsIgnoreCase("ChgUser")) {
      printHTMLChgSuccess(regout);
    }
  }

  private void printUserDoesNotExists(PrintWriter regout) {
    printHTMLHeader("", regout);
    printHTMLUserDoesNotExists(regout);
  }

  private void printError(PrintWriter regout) {
    printHTMLHeader("", regout);
    printHTMLError(regout);
  }

  protected void printHTMLHeader(String NewDestination, String NavigationHelp, String BarTitle, PrintWriter regout) {
    //StringBuffer MsgHH=new StringBuffer();
    regout.println("</HEAD>\n");
    if ("".equalsIgnoreCase(NewDestination)) {
      regout.println("<BODY TEXT=\"#000000\" LINK=\"#FFFFFF\" ALINK=\"\" VLINK=\"#FFFF66\">\n");
    } else {
      regout.println("<BODY TEXT=\"#000000\" LINK=\"#FFFFFF\" ALINK=\"#FFFF66\" VLINK=\"#FFFF66\" onload =\"" + NewDestination + "\">\n");
    }

    regout.println("<TABLE BORDER=0 WIDTH=725 >\n");
    regout.println("<TR VALIGN=\"TOP\">\n");
    regout.println("<TD WIDTH=\"200\">\n");
    regout.println("<img src=\"/images/tcmtitlegif.gif\" border=0 align=\"left\">\n");
    regout.println("</TD>\n");
    regout.println("<TD WIDTH=\"525\" ALIGN=\"right\">\n");
    regout.println("<img src=\"/images/" + NavigationHelp + "\" border=0 align=\"right\">\n");
    regout.println("</TD>\n");
    regout.println("</TR>\n");
    regout.println("</Table>\n");

    regout.println("<TABLE WIDTH=\"725\" BORDER=\"0\">\n");
    regout.println("<TR><TD VALIGN=\"middle\" BGCOLOR=#000066 VALIGN=\"MIDDLE\" WIDTH=\"600\" HEIGHT=\"20\">\n");
    regout.println("<B><FONT FACE=\"Arial\" COLOR=\"#FFFFFF\"><I>tcm</I>IS" + BarTitle + "</FONT></B>\n");

    if (" User Registration".equalsIgnoreCase(BarTitle)) {
      regout.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n");
      regout.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n");
      regout.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n");
      regout.println("<FONT SIZE=1 COLOR=\"#FFFFFF\">*Required fields</FONT>\n");
    }
    regout.println("</TD>\n");
    regout.println("</TR>\n");
    regout.println("<TR>\n");
  }

  protected void printHTMLHeader(String Loggedint, PrintWriter regout) {
    //StringBuffer MsgHH=new StringBuffer();

    regout.println("<TABLE WIDTH=\"725\" BORDER=\"0\">\n");
    regout.println("<TR VALIGN=\"top\">\n");
    regout.println("  <TD WIDTH=\"125\" HEIGHT=\"600\" BGCOLOR=\"#fff0e1\">\n");
    regout.println("    <TABLE cellSpacing=0 cellPadding=0 width=\"125\" BGCOLOR=\"#fff0e1\" border=0 height=\"600\">\n");
    regout.println("      <TR VALIGN=\"top\">\n");
    regout.println("      <TD>\n");
    regout.println(" <CENTER>\n");
    regout.println(" <IMG SRC=\"" + getBundle().getString("HOME_IMAGE") + "\">\n");
    regout.println(" </CENTER>\n");

    if ("Yes".equalsIgnoreCase(Loggedint)) {
      regout.println("    <FORM NAME=\"ChangeForm\" METHOD=\"post\" ACTION=\"" + ChangeUSR_Servlet + "\">\n");
    } else {
      regout.println("    <FORM NAME=\"LoginForm\" METHOD=\"post\" ACTION=\"" + Login_Servlet + "\" onsubmit =\"return getWebStart()\">\n");
    }

    if (ifloggedin) {
      regout.println("      \n");
      regout.println("      <INPUT TYPE=\"submit\" NAME=\"Submit4\" VALUE=\"Start tcmIS\">\n");
      regout.println("      <INPUT TYPE=\"hidden\" NAME=\"WebStart\" VALUE=\"No\">\n");
      regout.println("       <INPUT TYPE=\"hidden\" NAME=\"logoutAction\" VALUE=\"\">\n");
      regout.println("<BR><BR><BR><input type=\"submit\" value=\"Log Out\" onclick= \"return logout()\" name=\"Login\"></td>\n");

      regout.println("      \n");
    } else if ("Yes".equalsIgnoreCase(Loggedint)) {
      regout.println("      \n");
      regout.println("        <INPUT TYPE=\"submit\" NAME=\"Submit4\" VALUE=\"Update Profile\">\n");
      regout.println("      \n");
    } else {
      String clienthead = getBundle().getString("DB_CLIENT");

      if ("Seagate".equalsIgnoreCase(clienthead)) {
        regout.println("      <FONT FACE=\"Arial\" SIZE=\"1\">Global Id:<BR></FONT>\n");
      } else {
        regout.println("      <FONT FACE=\"Arial\" SIZE=\"1\">Logon Id:<BR></FONT>\n");
      }
      regout.println("        <INPUT TYPE=\"text\" NAME=\"loginname\" SIZE=\"10\" MAXLENGTH=\"25\" VALUE=\"" + Loggedint + "\"><BR>\n");
      regout.println("      \n");

      if (!"Seagate".equalsIgnoreCase(clienthead)) {
        regout.println("      <FONT FACE=\"Arial\" SIZE=\"1\">Password:<BR></FONT>\n");
        regout.println("        <INPUT TYPE=\"password\" NAME=\"password\" SIZE=\"10\" MAXLENGTH=\"25\" VALUE=\"\"><BR>\n");
        regout.println("      \n");
      }

      regout.println("<INPUT TYPE=\"hidden\" NAME=\"WebStart\" VALUE=\"No\">\n");
      regout.println("     <BR>\n");
      regout.println("        <INPUT TYPE=\"submit\" NAME=\"Submit\" VALUE=\"Login\">\n");
      regout.println("        <INPUT TYPE=\"reset\" NAME=\"Reset\" VALUE=\"Reset\">\n");
      regout.println("      \n");

      if ("Seagate".equalsIgnoreCase(clienthead)) {
        regout.println("      <FONT FACE=\"Arial\" SIZE=\"1\">Please enter your\n");
        regout.println("      Seagate Global Id and click Login to start <I>tcm</I>IS<BR><BR></FONT>\n");
      }
      if ("Internal".equalsIgnoreCase(clienthead)) {

      }
      regout.println("    </FORM>\n");
    }

    regout.println(" </TD>\n");
    regout.println("</TR>\n");

    regout.println("</TABLE>\n");
    regout.println("</TD>\n");

    //return MsgHH.toString();
  }

  protected void printFooter(PrintWriter regout) {
    //StringBuffer MsgFR = new StringBuffer();
    regout.println("</TR>\n");
    regout.println("  <TR VALIGN=\"middle\">\n");
    regout.println("    <TD WIDTH=\"24%\" HEIGHT=\"50\" BGCOLOR=#FF9933>&nbsp; </TD>\n");
    regout.println("    <TD WIDTH=\"76%\" HEIGHT=\"50\" BGCOLOR=#FF9933>\n");
    regout.println("      &nbsp;&nbsp;&nbsp;MSDS          Other Utilities\n");
    regout.println("    </TD>\n");
    regout.println("  </TR>\n");
    regout.println("</TABLE>\n");
    regout.println("</BODY>\n");
    regout.println("</HTML>\n");
    //return MsgFR;
  }

  protected void printHTMLError(PrintWriter regout) {
    //StringBuffer MsgER = new StringBuffer();
    regout.println("<TR>\n");
    regout.println("<TD width=\"5%\">\n");
    regout.println("</TD>\n");
    regout.println("<TD width=\"16%\"><FONT SIZE=\"1\" FACE=\"Arial\">" + "An Error Occurred.<BR>\n");
    regout.println("Please check data and try again." + "</FONT></TD>\n");
    regout.println("</TR></TABLE>\n");
    regout.println("</BODY></HTML>\n");
    //return MsgER.toString();
  }

  protected void printHTMLUserExists(PrintWriter regout) {
    //StringBuffer MsgER = new StringBuffer();
    regout.println("<TR>\n");
    regout.println("<TD width=\"5%\">\n");
    regout.println("</TD>\n");
    regout.println("<TD width=\"16%\"><FONT SIZE=\"1\" FACE=\"Arial\">" + "An Error Occurred.<BR>\n");
    regout.println("Logon Name already exists in <I>TcmIS.</I> Please use another logon name." + "</FONT></TD>\n");
    regout.println("</TR></TABLE>\n");
    regout.println("</BODY></HTML>\n");
    //return MsgER;
  }

  protected void printHTMLUserDoesNotExists(PrintWriter regout) {
    //StringBuffer MsgER = new StringBuffer();
    regout.println("<TR>\n");
    regout.println("<TD width=\"5%\">\n");
    regout.println("</TD>\n");
    regout.println("<TD width=\"16%\"><FONT SIZE=\"1\" FACE=\"Arial\">" + "An Error Occurred.<BR>\n");
    regout.println("Logon Name does not exists in <I>TcmIS.</I> Please use another logon name." + "</FONT></TD>\n");
    regout.println("</TR></TABLE>\n");
    regout.println("</BODY></HTML>\n");
    //return MsgER.toString();
  }

  protected void printHTMLAddSuccess(PrintWriter regout) {
    //StringBuffer MsgER = new StringBuffer();
    HeaderFooter hf = new HeaderFooter(getBundle(), db);

    regout.println(hf.printHTMLHeader("New tcmIS",intcmIsApplication));
    printJavaScripts("", regout);
    regout.println(hf.printTopToolBar("", "tcmistcmis32.gif", session));
    printHTMLHeader("", regout);

    regout.println("<TD  HEIGHT=\"600\" BGCOLOR=#FFFFFF VALIGN=\"TOP\">\n");
    regout.println("  <table cellspacing=0 border=0 width=600 height=\"600\">\n");
    regout.println("    <TR VALIGN=\"TOP\">\n");
    regout.println("      <TD>\n");
    regout.println("<P>&nbsp;</P><P>&nbsp;</P>\n");
    regout.println("      <DIV ALIGN=\"center\"><FONT FACE=\"Arial\" SIZE=\"3\">You are now a registered user of <I>tcm</I>IS.<BR><BR>\n");
    regout.println("      </FONT></DIV>\n");

    regout.println("<SCRIPT LANGUAGE=\"Javascript\" TYPE=\"text/javascript\">\n");
    regout.println("<!--\n");
    regout.println("testForWebStart();\n");
    regout.println("// -->\n");
    regout.println("</SCRIPT>\n");

    regout.println("      <BR><BR><CENTER><IMG SRC=\"/images/tcmintro.gif\"></CENTER></TD>\n");
    regout.println("    </TR>\n");
    regout.println("  </TABLE>\n");
    regout.println("</TD>\n");
    regout.println("</TR>\n");
    regout.println("</TABLE>\n");
    regout.println(hf.printFooter());
    //return MsgER.toString();
  }

  protected void printHTMLChgSuccess(PrintWriter regout) {
    //StringBuffer MsgER = new StringBuffer();
    regout.println("<TR>\n");
    regout.println("<TD width=\"5%\">\n");
    regout.println("</TD>\n");
    regout.println("<TD width=\"16%\"><FONT SIZE=\"1\" FACE=\"Arial\">" + "Sucessful.<BR>\n");
    regout.println("Your information has been changed in <I>TcmIS.</I>" + "</FONT></TD>\n");
    regout.println("</TR></TABLE>\n");
    regout.println("</BODY></HTML>\n");
    //return MsgER.toString();
  }

  protected void printHTMLSuccess(PrintWriter regout) {
    //StringBuffer MsgER = new StringBuffer();
    HeaderFooter hf = new HeaderFooter(getBundle(), db);
    regout.println(hf.printHTMLHeader("New tcmIS",intcmIsApplication));
    printJavaScripts("", regout);
    regout.println(hf.printTopToolBar("", "tcmistcmis32.gif", session));
    printHTMLHeader("", regout);

    regout.println("<TD  HEIGHT=\"600\" BGCOLOR=#FFFFFF VALIGN=\"middle\">\n");
    regout.println("  <table cellspacing=0 border=0 width=600 height=\"600\">\n");
    regout.println("    <tr>\n");
    regout.println("      <TD>\n");
    regout.println("      <DIV ALIGN=\"center\"><FONT FACE=\"Arial\" SIZE=\"3\">You are now a registered user of <I>TcmIS.</I>\n");
    regout.println("      You can login to tcmIS now!</FONT></DIV>\n");
    regout.println("      </TD>\n");
    regout.println("    </TR>\n");
    regout.println("  </TABLE>\n");
    regout.println("</TD>\n");
    regout.println("</TR>\n");
    regout.println("</TABLE>\n");
    regout.println(hf.printFooter());
    //return MsgER;
  }

  protected abstract String getClient();

  protected abstract ServerResourceBundle getBundle();

  //Returns true if the user does not exist.
  private boolean UserExists(String logonID) {
    boolean result = false;
    try {
      Personnel p = new Personnel(db);
      result = (p.isAvailable(logonID));
    } catch (Exception e) {
      result = false;
    }
    return result;
  }

  private String gettcmISVersion(String personnelID) {
    String versionNo = "";
    try {
      dbrs = db.doQuery("select fx_tcmis_version(" + personnelID + ") VERSIONNO from dual");
      rs = dbrs.getResultSet();
      while (rs.next()) {
        versionNo = rs.getString("VERSIONNO");
      }

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      dbrs.close();
    }

    return versionNo;
  }

  private void BuildAddUpdHTML(HttpServletRequest request, String UserAction, String ShowMsg, PrintWriter regout) {
    //StringBuffer Msg=new StringBuffer();
    HeaderFooter hf = new HeaderFooter(getBundle(), db);

    String logonID = request.getParameter("loginname");
    if (logonID == null) {
      logonID = "";

    }
    String fname = request.getParameter("FName");
    if (fname == null) {
      fname = "";

    }
    String mi = request.getParameter("MName");
    if (mi == null) {
      mi = "";

    }
    String lname = request.getParameter("LName");
    if (lname == null) {
      lname = "";

    }
    String email = request.getParameter("EMail");
    if (email == null) {
      email = "";

    }
    String phone = request.getParameter("Phone");
    if (phone == null) {
      phone = "";

    }
    String altphone = request.getParameter("AltPhone");
    if (altphone == null) {
      altphone = "";

    }
    String pager = request.getParameter("Pager");
    if (pager == null) {
      pager = "";

    }
    String fax = request.getParameter("Fax");
    if (fax == null) {
      fax = "";

    }
    String fac = request.getParameter("Facility");
    if (fac == null) {
      fac = "";

    }
    if (!getFacilities()) {
      //System.out.println("*** Unable to get facilities");
    }

    String clientaddup = getBundle().getString("DB_CLIENT");

    regout.println(hf.printHTMLHeader(clientaddup + " tcmIS User Registration",intcmIsApplication));
    if ("NoTolBarReg".equalsIgnoreCase(UserAction)) {
      if (ShowMsg.length() < 1) {
        printJavaScripts("", regout);
        printHTMLHeader("", "tcmistcmis32.gif", " User Registration", regout);
      } else {
        printJavaScripts(ShowMsg, regout);
        printHTMLHeader("showMsg()", "tcmistcmis32.gif", " User Registration", regout);
      }
    } else {
      if (ShowMsg.length() < 1) {
        printJavaScripts("", regout);
        regout.println(hf.printTopToolBar("", "tcmistcmis32.gif", session));
      } else {
        printJavaScripts(ShowMsg, regout);
        regout.println(hf.printTopToolBar("showMsg()", "tcmistcmis32.gif", session));
      }
      printHTMLHeader("", regout);
    }

    regout.println("<TD WIDTH=\"600\">\n");
    if ("AddUser".equalsIgnoreCase(UserAction) || "NoTolBarReg".equalsIgnoreCase(UserAction)) {
      regout.println("<FORM METHOD=\"POST\" NAME=\"RegForm\" ACTION=\"" + ADDUSER_Servlet + "\" onsubmit =\"return CheckValues(this)\">\n");
    }
    if ("ChgUser".equalsIgnoreCase(UserAction)) {
      regout.println("<FORM METHOD=\"POST\" NAME=\"RegForm\" ACTION=\"\" onsubmit =\"return CheckValues(this)\">\n");
    }

    regout.println("<TABLE BORDER=0 CELLSPACING=0 CELLPADDING=0 WIDTH=600>\n");

    regout.println("<TR><TD VALIGN=\"middle\" WIDTH=\"600\" COLSPAN=\"6\" HEIGHT=\"35\">\n");
    regout.println("<B><FONT FACE=\"Arial\" COLOR=\"#000080\">" + clientaddup + " <I>tcm</I>IS User Registration</FONT></B>\n");
    regout.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n");
    regout.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n");
    regout.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n");
    regout.println("<FONT SIZE=1>*Required fields</FONT></TD>\n");
    regout.println("</TR>\n");

    regout.println("<TR><TD WIDTH=\"80\" VALIGN=\"middle\" ALIGN=\"RIGHT\" HEIGHT=\"35\">\n");
    regout.println("<FONT FACE=\"Arial\" SIZE=1>*<B>First Name:&nbsp;</B></FONT></TD>\n");
    regout.println("<TD WIDTH=\"80\" VALIGN=\"middle\" HEIGHT=\"35\">\n");
    regout.println("<FONT FACE=\"Arial\" SIZE=1><input TYPE=\"TEXT\" MAXLENGTH=\"30\" SIZE=\"12\" NAME=\"FName\" VALUE=\"" + fname + "\"></FONT></TD>\n");
    regout.println("<TD WIDTH=\"120\" VALIGN=\"middle\" ALIGN=\"RIGHT\"  HEIGHT=\"35\">\n");
    regout.println("<FONT FACE=\"Arial\" SIZE=1>*<B>Last Name:&nbsp;</B></FONT></TD>\n");
    regout.println("<TD WIDTH=\"80\" VALIGN=\"middle\" HEIGHT=\"35\">\n");
    regout.println("<FONT FACE=\"Arial\" SIZE=1><input TYPE=\"TEXT\" MAXLENGTH=\"30\" SIZE=\"12\" NAME=\"LName\" VALUE=\"" + lname + "\"></FONT></TD>\n");
    regout.println("<TD WIDTH=\"80\" VALIGN=\"middle\" ALIGN=\"LEFT\"  HEIGHT=\"35\">\n");
    regout.println("<FONT FACE=\"Arial\" SIZE=1><B>M.I:&nbsp;</B></FONT>\n");
    regout.println("<FONT FACE=\"Arial\" SIZE=1><input TYPE=\"TEXT\" MAXLENGTH=\"5\" SIZE=\"2\" NAME=\"MName\" VALUE=\"" + mi + "\"></FONT></TD>\n");
    regout.println("<TD WIDTH=\"80\" VALIGN=\"middle\" HEIGHT=\"35\">\n");
    regout.println("<FONT FACE=\"Arial\" SIZE=1>&nbsp;</FONT></TD>\n");
    regout.println("</TR>\n");
    regout.println("<TR><TD WIDTH=\"80\" VALIGN=\"middle\" ALIGN=\"RIGHT\" HEIGHT=\"35\">\n");
    regout.println("<FONT FACE=\"Arial\" SIZE=1>*<B>Email:&nbsp;</B></FONT></TD>\n");
    regout.println("<TD WIDTH=\"300\" VALIGN=\"middle\" HEIGHT=\"35\" COLSPAN=5>\n");
    regout.println("<FONT FACE=\"Arial\" SIZE=1><input TYPE=\"TEXT\" MAXLENGTH=\"80\" SIZE=\"40\" NAME=\"EMail\" VALUE=\"" + email + "\"></FONT></TD>\n");
    regout.println("</TR>\n");
    /*regout.println( "<TR>\n" );
           regout.println( "<TD WIDTH=\"80\" VALIGN=\"middle\" ALIGN=\"RIGHT\" HEIGHT=\"35\">\n" );
           regout.println( "<FONT FACE=\"Arial\" SIZE=1><B>Ariba ID:&nbsp;</B></FONT></TD>\n" );
           regout.println( "<TD WIDTH=\"80\" VALIGN=\"middle\"  HEIGHT=\"35\">\n" );
           regout.println( "<FONT FACE=\"Arial\" SIZE=1><input TYPE=\"TEXT\" SIZE=\"12\" MAXLENGTH=\"30\" NAME=\"aribaID\" VALUE=\"\"></FONT></TD>\n" );
           regout.println( "<TD WIDTH=\"120\" VALIGN=\"middle\"  ALIGN=\"RIGHT\" HEIGHT=\"35\">\n" );
           regout.println( "<FONT FACE=\"Arial\" SIZE=1><B>&nbsp;</B></FONT></TD>\n" );
           regout.println( "<TD WIDTH=\"80\" VALIGN=\"middle\" HEIGHT=\"35\">\n" );
           regout.println( "<FONT FACE=\"Arial\" SIZE=1>&nbsp;</FONT></TD>\n" );
           regout.println( "<TD WIDTH=\"80\" VALIGN=\"middle\"  HEIGHT=\"35\">\n" );
           regout.println( "<FONT FACE=\"Arial\" SIZE=1>&nbsp;</FONT></TD>\n" );
           regout.println( "<TD WIDTH=\"80\" VALIGN=\"middle\" HEIGHT=\"35\">\n" );
           regout.println( "<FONT FACE=\"Arial\" SIZE=1>&nbsp;</FONT></TD>\n" );
           regout.println( "</TR>\n" );*/
    regout.println("<TR>\n");
    regout.println("<TD WIDTH=\"80\" VALIGN=\"middle\" ALIGN=\"RIGHT\" HEIGHT=\"35\">\n");
    regout.println("<FONT FACE=\"Arial\" SIZE=1>*<B>Phone:&nbsp;</B></FONT></TD>\n");
    regout.println("<TD WIDTH=\"80\" VALIGN=\"middle\"  HEIGHT=\"35\">\n");
    regout.println("<FONT FACE=\"Arial\" SIZE=1><input TYPE=\"TEXT\" SIZE=\"12\" MAXLENGTH=\"30\" NAME=\"Phone\" VALUE=\"" + phone + "\"></FONT></TD>\n");
    regout.println("<TD WIDTH=\"120\" VALIGN=\"middle\"  ALIGN=\"RIGHT\" HEIGHT=\"35\">\n");
    regout.println("<FONT FACE=\"Arial\" SIZE=1><B>Alt Phone:&nbsp;</B></FONT></TD>\n");
    regout.println("<TD WIDTH=\"80\" VALIGN=\"middle\" HEIGHT=\"35\">\n");
    regout.println("<FONT FACE=\"Arial\" SIZE=1><input TYPE=\"TEXT\" SIZE=\"12\" MAXLENGTH=\"30\" NAME=\"AltPhone\" VALUE=\"" + altphone + "\"></FONT></TD>\n");
    regout.println("<TD WIDTH=\"80\" VALIGN=\"middle\"  HEIGHT=\"35\">\n");
    regout.println("<FONT FACE=\"Arial\" SIZE=1>&nbsp;</FONT></TD>\n");
    regout.println("<TD WIDTH=\"80\" VALIGN=\"middle\" HEIGHT=\"35\">\n");
    regout.println("<FONT FACE=\"Arial\" SIZE=1>&nbsp;</FONT></TD>\n");
    regout.println("</TR>\n");
    regout.println("<TR>\n");
    regout.println("<TD WIDTH=\"80\" VALIGN=\"middle\"  ALIGN=\"RIGHT\" HEIGHT=\"35\">\n");
    regout.println("<FONT FACE=\"Arial\" SIZE=1><B>Pager:&nbsp;</B></FONT></TD>\n");
    regout.println("<TD WIDTH=\"80\" VALIGN=\"middle\" HEIGHT=\"35\">\n");
    regout.println("<FONT FACE=\"Arial\" SIZE=1><input TYPE=\"TEXT\"  SIZE=\"12\" MAXLENGTH=\"30\" NAME=\"Pager\" VALUE=\"" + pager + "\"></FONT></TD>\n");
    regout.println("<TD WIDTH=\"120\" VALIGN=\"middle\"  ALIGN=\"RIGHT\" HEIGHT=\"35\">\n");
    regout.println("<FONT FACE=\"Arial\" SIZE=1><B>Fax:&nbsp;</B></FONT></TD>\n");
    regout.println("<TD WIDTH=\"80\" VALIGN=\"middle\" HEIGHT=\"35\">\n");
    regout.println("<FONT FACE=\"Arial\" SIZE=1><input TYPE=\"TEXT\"  SIZE=\"12\" MAXLENGTH=\"30\" NAME=\"Fax\" VALUE=\"" + fax + "\"></FONT></TD>\n");
    regout.println("<TD WIDTH=\"80\" VALIGN=\"middle\"  HEIGHT=\"35\">\n");
    regout.println("<FONT FACE=\"Arial\" SIZE=1>&nbsp;</FONT></TD>\n");
    regout.println("<TD WIDTH=\"80\" VALIGN=\"middle\" HEIGHT=\"35\">\n");
    regout.println("<FONT FACE=\"Arial\" SIZE=1>&nbsp;</FONT></TD>\n");
    regout.println("</TR>\n");
    regout.println("<TR><TD WIDTH=\"80\" VALIGN=\"middle\"  ALIGN=\"RIGHT\" HEIGHT=\"35\">\n");
    regout.println("<FONT FACE=\"Arial\" SIZE=1>*<B>Global Id:&nbsp;</B></FONT></TD>\n");
    regout.println("<TD WIDTH=\"80\" VALIGN=\"middle\" HEIGHT=\"35\">\n");
    regout.println("<FONT FACE=\"Arial\" SIZE=1>" + logonID.trim() + "<input TYPE=\"hidden\" NAME=\"loginname\" VALUE=\"" + logonID.trim() + "\"></FONT></TD>\n");

    if ("AddUser".equalsIgnoreCase(UserAction)) {
      fac = " ";
    }

    regout.println("<TD WIDTH=\"120\" VALIGN=\"middle\"  ALIGN=\"RIGHT\" HEIGHT=\"35\">\n");
    regout.println("<FONT FACE=\"Arial\" SIZE=1>*<B>Preferred Facility:&nbsp;</B></FONT></TD>\n");
    regout.println("<TD WIDTH=\"80\" VALIGN=\"middle\" HEIGHT=\"35\">\n");
    regout.println("<FONT FACE=\"Arial\" SIZE=1>\n");
    regout.println("<SELECT NAME=\"Facility\" size=\"1\">");

    //fac
    Hashtable data = null;
    for (int i = 0; i < Vfac.size(); i++) {
      data = (Hashtable) Vfac.elementAt(i);
      Enumeration E;
      for (E = data.keys(); E.hasMoreElements(); ) {
        String key = (String) E.nextElement();
        regout.println("<OPTION " + (fac.equalsIgnoreCase(data.get(key).toString()) ? "selected" : "") + " VALUE=\"" + key + "\">" + data.get(key).toString() + "</OPTION>\n");
      }
    }
    regout.println("</SELECT>\n");
    regout.println("</FONT></TD>\n");

    regout.println("<TD WIDTH=\"60\" VALIGN=\"middle\"  HEIGHT=\"35\">\n");
    regout.println("<FONT FACE=\"Arial\" SIZE=1>&nbsp;</FONT></TD>\n");
    regout.println("<TD WIDTH=\"60\" VALIGN=\"middle\" HEIGHT=\"35\">\n");
    regout.println("<FONT FACE=\"Arial\" SIZE=1>&nbsp;</FONT></TD>\n");
    regout.println("</TR>\n");
    regout.println("<TR><TD WIDTH=\"100\" VALIGN=\"middle\" ALIGN=\"CENTER\" COLSPAN=2 HEIGHT=\"35\">\n");
    regout.println("<input TYPE=\"submit\" VALUE=\"Continue\" NAME=\"B1\"></TD>\n");
    regout.println("<TD WIDTH=\"100\" VALIGN=\"middle\" ALIGN=\"CENTER\" COLSPAN=2 HEIGHT=\"35\">\n");
    regout.println("<A HREF=" + Register_Home_Servlet1 + " STYLE=\"color:#0000ff\">Back</A></TD>\n");
    regout.println("<TD WIDTH=\"100\" VALIGN=\"middle\" ALIGN=\"CENTER\" COLSPAN=2 HEIGHT=\"35\">\n");
    regout.println("<input TYPE=\"Reset\" VALUE=\"Reset\" NAME=\"B2\"></TD>\n");
    regout.println("<INPUT TYPE=\"hidden\" NAME=\"WebStart\" VALUE=\"No\">\n");
    regout.println("</TR>\n");
    regout.println("</TABLE>\n");
    regout.println("</FORM>\n");
    regout.println("</TD>\n");
    regout.println("</TR>\n");
    regout.println("</TABLE>\n");

    if ("NoTolBarReg".equalsIgnoreCase(UserAction)) {
      regout.println("</BODY>\n");
      regout.println("</HTML>\n");
    } else {
      regout.println(hf.printFooter());
    }
    //return Msg.toString();
  }

  private void BuildIntorPageredirect(String filename, String tmpHost, PrintWriter regout) {
    //StringBuffer MsgI=new StringBuffer();
    HeaderFooter hf = new HeaderFooter(getBundle(), db);
    regout.println(hf.printHTMLHeader("tcmIS Initialization",intcmIsApplication));

    if (tmpHost.length() > 0) {
      regout.println("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; url=" + tmpHost + "/jnlp/loginfile/" + filename + "\">\n\n");
    } else {
      String hosturl = radian.web.tcmisResourceLoader.gethosturl();
      regout.println("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; url=" + hosturl + "jnlp/loginfile/" + filename + "\">\n\n");
    }
    printJavaScripts("", regout);
    regout.println(hf.printTopToolBar("", "tcmistcmis32.gif", session));

    regout.println("<TD BGCOLOR=#FFFFFF VALIGN=\"TOP\">\n");
    regout.println("  <TABLE cellspacing=0 border=0 width=725>\n");

    regout.println("    <TR VALIGN=\"TOP\">\n");
    regout.println("      <TD>\n");
    regout.println("      <FONT FACE=\"Arial\" SIZE=\"3\">\n");

    regout.println("<SCRIPT LANGUAGE=\"Javascript\" TYPE=\"text/javascript\">\n");
    regout.println("<!--\n");
    String client1 = getBundle().getString("DB_CLIENT_NAME");
    regout.println("launchLink('" + allrelease14count1 + "','" + client1 + "');\n");
    regout.println("// -->\n");
    regout.println("</SCRIPT>\n");

    regout.println("      </FONT>\n");
    regout.println("      </TD>\n");
    regout.println("    </TR>\n");
    regout.println("  </TABLE>\n");
    regout.println("</TD>\n");
    regout.println("</TR>\n");
    regout.println("</TABLE>\n");

    regout.println("<FORM NAME=\"LoginingForm\" METHOD=\"post\" ACTION=\"" + TestInstall_Servlet + "\" onsubmit =\"return getWebStartlogin()\">\n");
    regout.println("<INPUT TYPE=\"hidden\" NAME=\"WebStart\" VALUE=\"No\">\n");
    regout.println("</FORM>\n");

    regout.println(hf.printFooter());
    //return MsgI.toString();
  }

  private void BuildIntorPage(String ShowMsg, PrintWriter regout) {
    //StringBuffer MsgI=new StringBuffer();
    HeaderFooter hf = new HeaderFooter(getBundle(), db);

    //HEAD
    regout.println(hf.printHTMLHeader("New tcmIS",intcmIsApplication));

    //Top Tool Bars
    if (ShowMsg.length() < 1) {
      printJavaScripts("", regout);
      regout.println(hf.printTopToolBar("", "tcmistcmis32.gif", session));
    } else {
      printJavaScripts(ShowMsg, regout);
      regout.println(hf.printTopToolBar("showMsg()", "tcmistcmis32.gif", session));
    }

    printHTMLHeader("", regout);

    regout.println("<TD  HEIGHT=\"600\" BGCOLOR=#FFFFFF VALIGN=\"TOP\">\n");
    regout.println("  <table cellspacing=0 border=0 width=600 height=\"600\">\n");

    regout.println("    <TR VALIGN=\"TOP\">\n");
    regout.println("    <TD ALIGN=\"LEFT\">\n");

    String client = getBundle().get("DB_CLIENT_NAME").toString();
    radian.web.loginhelpObj.buildcomments(client, allrelease14count1, regout);

    //regout.println( "    <BR><BR><HR>\n" );
    regout.println("    </TD>\n");
    regout.println("    </TR>\n");
    regout.println("    <TR VALIGN=\"TOP\">\n");
    regout.println("      <TD>\n");
    regout.println("      <FONT FACE=\"Arial\" SIZE=\"3\">\n");

    regout.println("<INPUT TYPE=\"hidden\" NAME=\"flashversion\" VALUE=\"\">\n");
    regout.println("<INPUT TYPE=\"hidden\" NAME=\"flashInstalled\" VALUE=\"No\">\n");

    regout.println("<SCRIPT LANGUAGE=\"JavaScript\" type=\"text/javascript\" src=\"/clientscripts/fpi-init.js\"></SCRIPT>\n");
    regout.println("<SCRIPT LANGUAGE=\"JavaScript\" type=\"text/javascript\" src=\"/clientscripts/fpi-writevb.js\"></SCRIPT>\n");
    regout.println("<SCRIPT LANGUAGE=\"JavaScript\" type=\"text/javascript\" src=\"/clientscripts/fpi-main.js\"></SCRIPT>\n");

    regout.println("<SCRIPT LANGUAGE=\"Javascript\" TYPE=\"text/javascript\">\n");
    regout.println("<!--\n");
    String client1 = getBundle().getString("DB_CLIENT_NAME");
    regout.println("launchLink2('" + allrelease14count1 + "','" + client1 + "');\n");
    regout.println("// -->\n");
    regout.println("</SCRIPT>\n");
    regout.println("      </FONT>\n");
    regout.println("      </TD>\n");
    regout.println("    </TR>\n");
    regout.println("  </TABLE>\n");
    regout.println("</TD>\n");
    regout.println("</TR>\n");
    regout.println("</TABLE>\n");

    regout.println(hf.printFooter());

    //return MsgI.toString();
  }

  private void BuildIntorPage(String ShowMsg, String LOGON_ID, String Password, PrintWriter regout) {
    //StringBuffer MsgI=new StringBuffer();
    HeaderFooter hf = new HeaderFooter(getBundle(), db);

    regout.println(hf.printHTMLHeader("tcmIS Initialization",intcmIsApplication));

    //Top Tool Bars
    if (ShowMsg.equalsIgnoreCase("LOGIN")) {
      printJavaScripts("LOGIN", regout);
      regout.println(hf.printTopToolBar("loginnow()", "tcmistcmis32.gif", session));
    } else if (ShowMsg.equalsIgnoreCase("NOTHING")) {
      printJavaScripts("NO", regout);
      regout.println(hf.printTopToolBar("", "tcmistcmis32.gif", session));
    } else if (ShowMsg.equalsIgnoreCase("LOGGED")) {
      printJavaScripts("NO", regout);
      regout.println(hf.printTopToolBar("", "tcmistcmis32.gif", session));
    } else if (ShowMsg.equalsIgnoreCase("ERROR")) {
      printJavaScripts("An Error Occured.\\n Please try again.", regout);
      regout.println(hf.printTopToolBar("", "tcmistcmis32.gif", session));
    } else {
      printJavaScripts(ShowMsg, regout);
      regout.println(hf.printTopToolBar("", "tcmistcmis32.gif", session));
    }

    if (! (ShowMsg.equalsIgnoreCase("ERROR"))) {
      regout.println("<TD BGCOLOR=#FFFFFF VALIGN=\"TOP\">\n");
      regout.println("  <TABLE cellspacing=0 border=0 width=725>\n");

      regout.println("    <TR VALIGN=\"TOP\">\n");
      regout.println("    <TD ALIGN=\"LEFT\">\n");

      String client = getBundle().get("DB_CLIENT_NAME").toString();
      radian.web.loginhelpObj.buildcomments(client, allrelease14count1, regout);

      regout.println("    </TD>\n");
      regout.println("    </TR>\n");
      regout.println("    <TR VALIGN=\"TOP\">\n");
      regout.println("      <TD>\n");
      regout.println("      <FONT FACE=\"Arial\" SIZE=\"3\">\n");
      regout.println("<FORM NAME=\"LoginingForm\" METHOD=\"post\" ACTION=\"" + TestInstall_Servlet + "\" onsubmit =\"return getWebStartlogin()\">\n");
      regout.println("<INPUT TYPE=\"hidden\" NAME=\"flashversion\" VALUE=\"\">\n");
      regout.println("<INPUT TYPE=\"hidden\" NAME=\"flashInstalled\" VALUE=\"No\">\n");

      regout.println("<SCRIPT LANGUAGE=\"JavaScript\" type=\"text/javascript\" src=\"/clientscripts/fpi-init.js\"></SCRIPT>\n");
      regout.println("<SCRIPT LANGUAGE=\"JavaScript\" type=\"text/javascript\" src=\"/clientscripts/fpi-writevb.js\"></SCRIPT>\n");
      regout.println("<SCRIPT LANGUAGE=\"JavaScript\" type=\"text/javascript\" src=\"/clientscripts/fpi-main.js\"></SCRIPT>\n");

      regout.println("<SCRIPT LANGUAGE=\"Javascript\" TYPE=\"text/javascript\">\n");
      regout.println("<!--\n");
      String client1 = getBundle().getString("DB_CLIENT_NAME");
      regout.println("launchLink('" + allrelease14count1 + "','" + client1 + "');\n");
      regout.println("// -->\n");
      regout.println("</SCRIPT>\n");

      regout.println("      </FONT>\n");
      regout.println("      </TD>\n");
      regout.println("    </TR>\n");
      regout.println("  </TABLE>\n");
      regout.println("</TD>\n");
      regout.println("</TR>\n");
      regout.println("</TABLE>\n");

      regout.println("<INPUT TYPE=\"hidden\" NAME=\"WebStart\" VALUE=\"No\">\n");
      regout.println("<INPUT TYPE=\"hidden\" NAME=\"loginname\" VALUE=\"" + LOGON_ID + "\">\n");
      regout.println("<INPUT TYPE=\"hidden\" NAME=\"Password\" VALUE=\"" + Password + "\">\n");

      regout.println("</FORM>\n");
    } else {
      regout.println("<TD BGCOLOR=#FFFFFF VALIGN=\"TOP\">\n");
      regout.println("  <TABLE cellspacing=0 border=0 width=725>\n");
      regout.println("    <TR VALIGN=\"TOP\">\n");
      regout.println("      <TD>\n");
      regout.println("      <FONT FACE=\"Arial\" SIZE=\"3\">\n");
      regout.println("<B>An Error Ocurred.<BR>\nCan not login to tcmIS</B><BR>\n");
      regout.println("<BR>Please try again.<BR>\n");
      regout.println("\n<BR><BR>If problem recurs, contact your tcmIS Coustomer Service Representative (CSR).<BR>\n");
      regout.println("<BR>You can close this window.<BR>\n");
      regout.println("      </FONT>\n");
      regout.println("      </TD>\n");
      regout.println("    </TR>\n");
      regout.println("  </TABLE>\n");
      regout.println("</TD>\n");
      regout.println("</TR>\n");
      regout.println("</TABLE>\n");
    }

    regout.println(hf.printFooter());
    //return MsgI.toString();
  }

  private void UsrLogin(HttpServletRequest request, String Ariba_Flag, String PayLoadID, PrintWriter regout) throws ServletException, IOException {
    StringBuffer Msg = new StringBuffer();
    //HttpSession session=request.getSession();
    Random rand = new Random();
    int tmpReq = rand.nextInt();
    Integer tmpReqNum = new Integer(tmpReq);
    String tmpUrl = "";
    String tmpHost = "";
    String tmpVersion = "";
    String personnelId = "";

    int tmpReq1 = rand.nextInt();
    Integer tmpReqNum1 = new Integer(tmpReq1);

    String loginId = session.getAttribute("loginId") == null ? "" : session.getAttribute("loginId").toString();
    String passwd = session.getAttribute("passwd") == null ? "" : session.getAttribute("passwd").toString();

    String WebStartY = request.getParameter("WebStart");
    if (WebStartY == null) {
      WebStartY = "";

    }
    String tcmVersion = "";
    String loggedIn = session.getAttribute("LOGGED") == null ? "" : session.getAttribute("LOGGED").toString();

    if ("Y".equalsIgnoreCase(loggedIn)) {
      BuildIntorPage("NOTHING", "", "", regout);
      return;
    }

    String clientuserlogin = getBundle().getString("DB_CLIENT_NAME");
    if (loginId != null && passwd != null) {
      if (!"Seagate".equalsIgnoreCase(clientuserlogin)) {
        try {
          Password pw = new Password(db, loginId, passwd);
          personnelId = pw.getUserId();
          tcmVersion = this.gettcmISVersion(personnelId);

          if (BothHelpObjs.isBlankString(tcmVersion)) {
            ifloggedin = false;
            session.setAttribute("clientloginState", "");
            BuildIntorPage("You Do not Have Access to this version of tcmIS.\\n \\nPlease contact your facility administrator.", regout);
            return;
          }

          if ("0".equalsIgnoreCase(tcmVersion)) {
            //System.out.println("You Don't Have Access ");
            ifloggedin = false;
            session.setAttribute("clientloginState", "");
            BuildIntorPage("You Do not Have Access to this version of tcmIS.\\n \\nPlease contact your facility administrator.", regout);
            return;
          }

          if (!pw.isAuthenticatedWeb()) {
            //System.out.println("Password Did not Match  ");
            ifloggedin = false;
            session.setAttribute("clientloginState", "");
            BuildIntorPage("An error occured while trying to login.\\n \\nPlease Re-Enter your Logon Id, Password and try again", regout);
            return;
          } else {
            session.setAttribute("CLIENTPERSONNELID", personnelId);
            session.setAttribute("PERSONNELID", personnelId);
            session.setAttribute("FULLNAME", radian.web.HTMLHelpObj.getfullname(db, personnelId, ""));
            /* 05-01-06 - Could not figure out a qucik way to make this loginProcess same as the struts login process which is used everywhere else.
               This whole class needs to be rewritten soon.
               I am copying the contents of the lgoinProcess method here as a quick fix.
             */
            String auth = session.getAttribute("loginState") == null ? "" : session.getAttribute("loginState").toString();
            if (!"authenticated".equals(auth)) {
              String CompanyID = "";
              session.setAttribute("ALLOWEDGROUPS", radian.web.HTMLHelpObj.createallowedusrgrps(db, pw.getUserId(), CompanyID));
              //session.setAttribute("HUB_PREF_LIST", radian.web.HTMLHelpObj.createHubList(db,pw.getUserId(),CompanyID));
              //session.setAttribute( "VV_CURRENCY",radian.web.vvHelpObj.getcurrency(db) );
              session.setAttribute("loginState", "authenticated");
              String dbclientname = db.getClient();

              //copy input into bean
              PersonnelBean personnelBean = new PersonnelBean();
              personnelBean.setClient(dbclientname.toUpperCase());
              personnelBean.setCompanyId(CompanyID);
              personnelBean.setLogonId(loginId);
              personnelBean.setPassword(passwd);

              //login
              if (personnelBean != null && personnelBean.getClient() != null) {
                LoginProcess loginProcess = new LoginProcess(personnelBean.getClient());
                personnelBean = loginProcess.loginWeb(personnelBean);
                if (personnelBean != null) {
                  //System.out.println( "bean:" + personnelBean.getLogonId() + " - " +  personnelBean.getPersonnelId() + " - " + personnelBean.getClient() );
                  session.setAttribute("personnelBean", personnelBean);
                }
                //Storing the Hub_Pref and inventory groups in the session
                //session.setAttribute("hubPrefViewBeanCollection",loginProcess.getHubPrefView(personnelBean));
                //session.setAttribute("personnelUserGroupViewBeanCollection",loginProcess.getDistinctPersonnelUserGroupView(personnelBean));
                //VvDataProcess vvDataProcess = new VvDataProcess(personnelBean.getClient());
                //session.setAttribute("vvCurrencyCollection",vvDataProcess.getVvCurrency());
                //session.setAttribute("hubInventoryGroupOvBeanCollection",loginProcess.getHubInventoryGroupData(personnelBean));
                personnelBean.setLocale(setLocale(request,session,request.getParameter("langSetting")));
              }
            }
            session.setAttribute("clientloginState", "authenticated");
          }
        } catch (Exception wq) {
          wq.printStackTrace();
          BuildIntorPage("ERROR", "", "", regout);
          return;
        }
      } //end of if not seagate

      try {
        //if seagate
        if ("Seagate".equalsIgnoreCase(clientuserlogin)) {
          tcmVersion = "2";
          personnelId = "";
          try {
            String query = "select PASSWORD,PERSONNEL_ID from personnel where lower(LOGON_ID) = lower('" + loginId + "')";
            dbrs = db.doQuery(query);
            rs = dbrs.getResultSet();
            while (rs.next()) {
              passwd = (rs.getString("PASSWORD") == null ? "" : rs.getString("PASSWORD"));
              personnelId = (rs.getString("PERSONNEL_ID") == null ? "" : rs.getString("PERSONNEL_ID"));
            }
          } catch (Exception e) {
            e.printStackTrace();
            BuildIntorPage("ERROR", "", "", regout);
            return;
          } finally {
            if (dbrs != null) {
              dbrs.close();
            }
          }
          session.setAttribute("CLIENTPERSONNELID", personnelId);
          session.setAttribute("PERSONNELID", personnelId);
          session.setAttribute("FULLNAME", radian.web.HTMLHelpObj.getfullname(db, personnelId, ""));
          session.setAttribute("clientloginState", "authenticated");
        } //end of if seagate

        String prerequery = "select count(*) from tcmis_prerelease_version where lower(USERNAME) = lower('" + loginId + "')";
        int prereleasecount1 = 0;
        try {
          prereleasecount1 = DbHelpers.countQuery(db, prerequery);
        } catch (Exception e) {
          e.printStackTrace();
          BuildIntorPage("ERROR", "", "", regout);
          return;
        }
        //System.out.println("The Parameters Being Passed is  " + loginId + " " + Ariba_Flag + " " + PayLoadID + "  " + tcmVersion);
        String jnlpcodebase = radian.web.tcmisResourceLoader.getjnlpcodebase();
        String client2 = getBundle().getString("DB_CLIENT_NAME");
        if ( ("SWA".equalsIgnoreCase(client2) || "DRS".equalsIgnoreCase(client2) || "UTC".equalsIgnoreCase(client2))) {
          jnlpcodebase = "http://www.tcmis.com";
        }

        /*//Saving the flash info
          String flashversion=request.getParameter( "flashversion" );
          if ( flashversion == null )
         flashversion="";
          String flashInstalled=request.getParameter( "flashInstalled" );
          if ( flashInstalled == null )
         flashInstalled="";
          System.out.println( "flashversion :   "+flashversion+"    flashInstalled :  "+flashInstalled+"");
          String finalflashver = "";
          if ("Yes".equalsIgnoreCase(flashInstalled))
          {
         finalflashver = "Flash " + flashversion;
          }
          else
          {
         finalflashver = "No Flash";
          }
          //copy input into bean
          UserEnvLogBean flashBean=new UserEnvLogBean();
          flashBean.setPersonnelId( Integer.parseInt(personnelId) );
          flashBean.setDetail( finalflashver );
          //login
          if ( flashBean != null && flashBean.getPersonnelId() > 0 )
          {
         SnoopProcess flashProcess=new SnoopProcess( clientuserlogin );
         flashProcess.registerFlash(flashBean);
          }*/

       session.setAttribute("LOGGED", "Y");
       //building jnlp files
        Msg.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        if (prereleasecount1 > 0 || allrelease14count1 > 0) {
          //if user is in tcmis_prerelease_version than get host, path and version from table
          try {
            String query = "select nvl(host,' ') host, nvl(path,' ') path, nvl(version,' ') version from tcmis_prerelease_version where";
            //if user is in tcmis_prereleaser_version as well as All (entired company) then specific user win
            if (prereleasecount1 > 0 && allrelease14count1 > 0) {
              query += " lower(username) = lower('" + loginId + "')";
            } else if (allrelease14count1 > 0) {
              query += " lower(username) = lower('All')";
            } else {
              query += " lower(username) = lower('" + loginId + "')";
            }
            dbrs = db.doQuery(query);
            rs = dbrs.getResultSet();
            while (rs.next()) {
              tmpHost = (rs.getString("host") == null ? "" : rs.getString("host"));
              tmpUrl = tmpHost + (rs.getString("path") == null ? "" : rs.getString("path"));
              tmpVersion = rs.getString("version");
            }
          } catch (Exception eee3) {
            eee3.printStackTrace();
            BuildIntorPage("ERROR", "", "", regout);
            return;
          } finally {
            if (dbrs != null) {
              dbrs.close();
            }
          }
          if (tmpUrl.length() > 0) {
            Msg.append("<jnlp spec=\"1.0+\" version=\"2.0\" codebase=\"" + tmpUrl + "/\">\n");
          } else {
            Msg.append("<jnlp spec=\"1.0+\" version=\"2.0\" codebase=\"" + jnlpcodebase + getBundle().getString("TEST_JNLP_DIRECTORY") + "/\">\n");
          }
        } else {
          Msg.append("<jnlp spec=\"1.0+\" version=\"2.0\" codebase=\"" + jnlpcodebase + getBundle().getString("JNLP_DIRECTORY") + "/\">\n");
        }
        Msg.append("  <information>\n");
        Msg.append("    <title>Updating tcmIS</title>\n");
        Msg.append("    <vendor>Haas TCM</vendor>\n");
        Msg.append("    <homepage href=\"http://www.tcmis.com\" />\n");
        Msg.append("    <description kind=\"\">tcmIs is a total chemical Management tool</description>\n");
        Msg.append("    <description kind=\"short\">Use this to manage your chemical needs</description>\n");
        Msg.append("    <description kind=\"one-line\">tcmIS</description>\n");
        Msg.append("    <description kind=\"tooltip\">tcmIS Application</description>\n");
        Msg.append("    <icon kind=\"default\" href=\"images/tcmissquare.gif\" />\n");
        Msg.append("    <icon kind=\"selected\" href=\"images/tcmissquare.gif\" />\n");
        Msg.append("    <icon kind=\"disabled\" href=\"images/tcmissquare.gif\" />\n");
        Msg.append("    <icon kind=\"rollover\" href=\"images/tcmissquare.gif\" width=\"75\" height=\"25\" />\n");
        Msg.append("  </information>\n");
        Msg.append("<security>\n");
        Msg.append("      <all-permissions/>\n");
        Msg.append("</security>\n");
        Msg.append("  <resources>\n");
        if (prereleasecount1 > 0 || allrelease14count1 > 0) {
          if (tmpVersion.length() > 0) {
            Msg.append("    <j2se version=\"" + tmpVersion + "\" max-heap-size=\"128m\"/>\n");
          } else {
            Msg.append("    <j2se version=\"1.4+\" max-heap-size=\"128m\"/>\n");
          }
        } else {
          Msg.append("    <j2se version=\"1.3+\" max-heap-size=\"128m\"/>\n");
        }
        Msg.append("    <jar href=\"" + getBundle().getString("JNLP_MAINCLASS") + "\" main=\"true\" download=\"eager\" />\n");
        Msg.append("    <jar href=\"RadianBoth1.jar\" main=\"false\" download=\"eager\" />\n");
        Msg.append("    <jar href=\"RadianCSAdmin.jar\" main=\"false\" download=\"eager\" />\n");
        Msg.append("    <jar href=\"RadianCSDelivery.jar\" main=\"false\" download=\"eager\" />\n");
        Msg.append("    <jar href=\"RadianCSGui.jar\" main=\"false\" download=\"eager\" />\n");
        Msg.append("    <jar href=\"RadianCSHelpers.jar\" main=\"false\" download=\"eager\" />\n");
        Msg.append("    <jar href=\"RadianCSHttpCgi.jar\" main=\"false\" download=\"eager\" />\n");
        Msg.append("    <jar href=\"RadianCSNChem.jar\" main=\"false\" download=\"eager\" />\n");
        Msg.append("    <jar href=\"RadianCSSpanCellTable.jar\" main=\"false\" download=\"eager\" />\n");
        Msg.append("    <jar href=\"RadianCSWaste.jar\" main=\"false\" download=\"eager\" />\n");
        Msg.append("    <jar href=\"ResourceLoader.jar\" main=\"false\" download=\"eager\" />\n");
        Msg.append("    <jar href=\"jbcl3.0.jar\" main=\"false\" download=\"eager\" />\n");
        Msg.append("    <jar href=\"SSLava.jar\" main=\"false\" download=\"eager\" />\n");
        Msg.append("    <jar href=\"acme.jar\" main=\"false\" download=\"eager\" />\n");
        Msg.append("    <jar href=\"dx.jar\" main=\"false\" download=\"eager\"/>\n");
        Msg.append("    <jar href=\"jsse.jar\" main=\"false\" download=\"eager\"/>\n");
        Msg.append("    <jar href=\"jnet.jar\" main=\"false\" download=\"eager\"/>\n");
        Msg.append("    <jar href=\"jcert.jar\" main=\"false\" download=\"eager\"/>\n");
        if ("CAL".equalsIgnoreCase(client2)) {
          Msg.append("    <jar href=\"tcmis.jar\" main=\"false\" download=\"eager\"/>\n");
        }
        Msg.append("  </resources>\n");
        Msg.append("  <application-desc main-class=\"" + getBundle().getString("TCMIS_START_CLASS") + "\">\n");
        Msg.append(" <argument>" + loginId + "</argument>\n");
        Msg.append(" <argument>" + passwd + "</argument>\n");
        Msg.append(" <argument>" + Ariba_Flag + "</argument>\n");
        Msg.append(" <argument>" + PayLoadID + "</argument>\n");
        Msg.append(" <argument>" + tcmVersion + "</argument>\n");
        Msg.append("</application-desc>\n");
        Msg.append("</jnlp>\n");
        //execute jnlp file
        try {
          String filepathtojnlpfile = radian.web.tcmisResourceLoader.getjnlpfilepath();
          ps = new PrintStream(new FileOutputStream(filepathtojnlpfile + loginId + tmpReqNum.toString() + tmpReqNum1.toString() + ".jnlp"), true);
          byte outbuf[];
          outbuf = Msg.toString().getBytes();
          ps.write(outbuf);
          ps.close();
        } catch (IOException writee) {
          writee.printStackTrace();
        }
        BuildIntorPageredirect("" + loginId + tmpReqNum.toString() + tmpReqNum1.toString() + ".jnlp", tmpHost, regout);
        //System.out.println(jnlpcodebase + "/jnlp/loginfile/" + loginId + tmpReqNum.toString() + tmpReqNum1.toString() + ".jnlp");
      } catch (Exception e) {
        BuildIntorPage("An error occured while trying to login.\\n \\nPlease Enter your Global Id, Password and try again", regout);
      }
    }
  } //end of method

  public static Locale setLocale(HttpServletRequest request,HttpSession session,String lang){
	  Locale locale = (Locale) session.getAttribute(org.apache.struts.Globals.LOCALE_KEY);
	  if (locale == null)
		  locale = request.getLocale(); // take from brower.
	  if (lang != null && lang.length() == 5) {
		  String[] langArr = lang.split("_");
		  locale = new Locale(langArr[0],langArr[1],"");
		  session.setAttribute("langSetting",lang); // for our own use...
	  }
      
      session.setAttribute("tcmISLocale", locale);
	  session.setAttribute(org.apache.struts.Globals.LOCALE_KEY, locale);
	  Config.set(session, Config.FMT_LOCALE, locale);
	  return locale;
  }

  protected void printJavaScripts(String ShowThis, PrintWriter regout) {
    //StringBuffer Msgt=new StringBuffer();
    regout.println("<SCRIPT LANGUAGE=\"JavaScript1.2\" TYPE=\"text/javascript\">\n <!--\n");
    regout.println("testurl3 = \"" + Register_Home_Servlet1 + "\";\n");
    if ("LOGIN".equalsIgnoreCase(ShowThis)) {
      regout.println("function loginnow(){\n");
      regout.println(" if (javawsInstalled)\n");
      regout.println("  {\n");
      regout.println("  window.document.LoginingForm.WebStart.value = \"Yes\";\n ");
      regout.println("  }\n");
      regout.println("  else\n");
      regout.println("  {\n");
      regout.println("  window.document.LoginingForm.WebStart.value = \"Notre\";\n ");
      regout.println("  }\n ");
      regout.println("window.document.LoginingForm.submit();\n}\n");
    } else if (!"NO".equalsIgnoreCase(ShowThis)) {
      regout.println("function showMsg(){\n alert(\"" + ShowThis + "\"); \n}\n");
    }

    /*regout.println("function opennewwin ()\n");
           regout.println(" {\n");
           regout.println(" MSDS_Help = window.open(\"/images/newrelease/index.html\", \"new_release\",\n");
           regout.println(" \"toolbar=yes,location=no,directories=no,status=yes\" +\n");
           regout.println(" \",menubar=no,scrollbars=yes,resizable=yes,\" +\n");
           regout.println(" \"width=750,height=520\");\n");
           regout.println(" }\n");*/

    regout.println("function CheckValues(entered)\n");
    regout.println("{\nvar testvar;\n var finalMsg;\n var allClear = 0;\n var loc =\"" + ADDUSER_Servlet + "\" \n");
    regout.println("ref =window.document.RegForm.Facility.selectedIndex;\n");
    regout.println("ref1 =window.document.RegForm.Facility.options[ref].value;\n");
    regout.println("finalMsg = \"Please Enter the following Values \\n\";\ntestvar =window.document.RegForm.FName.value.length;\n");
    regout.println("if (testvar < 1)\n");
    regout.println(" {finalMsg = finalMsg + \"\\n First Name.\";\nallClear = 1;}\n");

    regout.println("testvar =window.document.RegForm.LName.value.length;\n");
    regout.println("if (testvar < 1)\n");
    regout.println(" {finalMsg = finalMsg + \"\\n Last Name.\";\n allClear = allClear + 1;}\n");

    regout.println("testvar =window.document.RegForm.EMail.value.length;\n");
    regout.println("if (testvar < 1)\n");
    regout.println(" {finalMsg = finalMsg + \"\\n Email Address.\";\n allClear = allClear + 1;}\n");

    regout.println("testvar =window.document.RegForm.Phone.value.length;\n");
    regout.println("if (testvar < 1)\n");
    regout.println(" {finalMsg = finalMsg + \"\\n Primary Contact Phone Number.\";\n allClear = allClear + 1;}\n");

    regout.println("testvar =window.document.RegForm.loginname.value.length;\n");
    regout.println("if (testvar < 1)\n");
    regout.println(" {finalMsg = finalMsg + \"\\n LoginID.\";\n allClear = allClear + 1;}\n");
    regout.println(" finalMsg = finalMsg + \"\\n \\nPlease fill out the form properly and try again!.\";\n");

    regout.println("  if (javawsInstalled)\n");
    regout.println("  {\n");
    regout.println("  window.document.RegForm.WebStart.value = \"Yes\";\n ");
    regout.println("  }\n");
    regout.println("  else\n");
    regout.println("  {\n");
    regout.println("  window.document.RegForm.WebStart.value = \"Notre\";\n ");
    regout.println("  }\n");

    regout.println(" if (allClear < 1) \n{\n");
    regout.println("return true;\n");
    regout.println("}\n");
    regout.println(" else \n{alert(finalMsg); \nreturn false;}\n");
    regout.println("}\n");
    regout.println("// -->\n </SCRIPT>\n");

    regout.println("<SCRIPT LANGUAGE=\"VBScript\"> \n<!--\n\n");
    regout.println("on error resume next\n");
    regout.println("If isIE = \"true\" Then\n");
    regout.println("  If Not(IsObject(CreateObject(\"JavaWebStart.IsInstalled\"))) Then\n");
    regout.println("     javawsInstalled = 0\n");
    regout.println("  Else\n");
    regout.println("    javawsInstalled = 1\n");
    regout.println("  End If\n");
    regout.println("End If\n");
    regout.println("// -->\n</SCRIPT>\n");
  }

  protected void printHTMLLoginError(PrintWriter regout) {
    //StringBuffer MsgER=new StringBuffer();
    HeaderFooter hf = new HeaderFooter(getBundle(), db);
    //HEAD
    regout.println(hf.printHTMLHeader("New tcmIS",intcmIsApplication));
    printJavaScripts("", regout);
    //Top Tool Bars
    regout.println(hf.printTopToolBar("", "tcmistcmis32.gif", session));
    printHTMLHeader("", regout);
    regout.println("<TD  HEIGHT=\"600\" BGCOLOR=#FFFFFF VALIGN=\"TOP\">\n");
    regout.println("  <table cellspacing=0 border=0 width=600 height=\"600\">\n");
    regout.println("    <TR VALIGN=\"TOP\">\n");
    regout.println("      <TD>\n");
    regout.println("      <DIV ALIGN=\"center\"><FONT FACE=\"Arial\" SIZE=\"3\">\n");
    regout.println("<BR><BR><BR><BR><BR><BR><BR><BR><BR><BR><B><B>Authentication Error\n<BR>\n");
    regout.println("Please Enter your Global Id, Password and try again.</FONT></DIV>\n\n");
    regout.println("      </TD>\n");
    regout.println("    </TR>\n");
    regout.println("  </TABLE>\n");
    regout.println("</TD>\n");
    regout.println("</TR>\n");
    regout.println("</TABLE>\n");
    regout.println(hf.printFooter());
  }
}
