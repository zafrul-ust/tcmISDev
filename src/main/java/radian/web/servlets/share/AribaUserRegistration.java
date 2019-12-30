/**
 * Title:        MSDS Viewer
 * Copyright:    Copyright (c) 2001
 * Company:      Radian
 * @author Nawaz Shaik,Rajendar Rajput
 * @version
 *
 * This is the main servlet which handels the login page for a client.
 *
 * Note: Try to create the web pages in such a way that you can always pass all
 * the HTML you want on the right hand side of the page through a <TD> tag element.
 */

package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.util.Enumeration;
import java.util.Hashtable;
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

import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.DbHelpers;
import radian.tcmis.server7.share.dbObjs.Facility;
import radian.tcmis.server7.share.dbObjs.Personnel;
import radian.tcmis.server7.share.dbObjs.PersonnelProfile;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.HelpObjs;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.web.HeaderFooter;
import java.io.PrintStream;
import java.io.FileOutputStream;
import java.util.Random;

/**
 *
 * <p>Title: All the code</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Haas TCM</p>
 * @author Nawaz Shaik
 * @version 1.0
 *
 * 05-07-03 - Added the prerelase 14 to the login process
 * 11-11-03 - Made Changes so that this works with both ariba and oracle procurement
 * 06-14-03 - Updated the JRE install file link if the person logging in does not have JWS installed
 */
public abstract class AribaUserRegistration extends HttpServlet implements SingleThreadModel {
  //
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }

  private PrintStream ps = null;
  //Client Version
  private String fname = null;
  private String lname = null;
  private String mi = null;
  private String phone = null;
  private String altphone = null;
  private String pager = null;
  private String email = null;
  private String fax = null;
  private String fac = null;

  private String upmsg = null;
  private int PersonnelID = 0;
  private String personnelIDStr = null;
  private String logonID = null;
  private String AribaID = null;
  private String password = null;
  private boolean dbError = false;
  private Vector Vfac = null;
  //private PrintWriter out = null;
  private String client = null;

  private TcmISDBModel db = null;
  private HttpServletRequest reqInfo = null;
  private String ip = null;
  private String token = null;
  private ServerResourceBundle bundle = null;

  private String User_Action = null;

  private String pagetitle = null;
  private String AddUser = "AddUser";

  private String ADDUSER_Servlet = null;
  private String Login_Servlet = null;
  private String Lookup_Servlet = null;
  private String Register_Home_Servlet = null;
  private String ChangeUSR_Servlet = null;
  private String TestInstall_Servlet = null;
  private String Ariba_ID = "";

  private DBResultSet dbrs = null;
  private ResultSet rs = null;
  private int allrelease14count1 = 0;


  private boolean intcmIsApplication = false;

  //Process the HTTP Get request
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response) throws ServletException,
      IOException {
    //System.out.println("*** In doGet() of UserRegistarion ***");
    doPost(request, response);
  }

  //Process the HTTP Post request
  //This code won't login to tcmIS if the Ariba ID is ABCDEFGHTESTXYZ
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession();

    PersonnelBean personnelBean = (PersonnelBean) session.getAttribute("personnelBean");
		if (personnelBean !=null)
		{
			 Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
			 intcmIsApplication = false;
			 if (menuItemOvBeanCollection != null && menuItemOvBeanCollection.size() > 0) {
				intcmIsApplication = true;
			 }
		}

    db = null;
    String Ariba_Flag = "No";
    String payload_ID = "NO";

    String Logon_ID = "ABCDEFGHTESTXYZ";
    String session_ID = session.getId();

    String payloadidfromurl = "";

    try {
      payloadidfromurl = request.getParameter("payloadID").toString();
    } catch (Exception e) {
      payloadidfromurl = ""; /*e.printStackTrace();*/
    }

    ADDUSER_Servlet = "https://www.tcmis.com/tcmIS/seagate/AribaLogin?useraction=AddUser";
    Lookup_Servlet = "https://www.tcmis.com/tcmIS/seagate/AribaLogin?useraction=Lookup";
    Login_Servlet = "https://www.tcmis.com/tcmIS/seagate/AribaLogin?useraction=Login";
    TestInstall_Servlet = "https://www.tcmis.com/tcmIS/seagate/AribaLogin?useraction=startingtcmis";

    Vfac = new Vector();
    client = new String("");
    client = getBundle().getString("DB_CLIENT");
    pagetitle = client + " <I>tcm</I>IS User Registration";

    //Establish db connection for every request
    String client1 = getBundle().get("DB_CLIENT_NAME").toString();
    db = new RayTcmISDBModel(client1);

    //generate response
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    try {
      User_Action = request.getParameter("useraction").toString();
    } catch (Exception e) {
      User_Action = "Nothing";
    }

    String allre14query = "select count(*) from tcmis_prerelease_version where lower(USERNAME) = lower('All') and VERSION = '1.4' ";
    try {
      allrelease14count1 = DbHelpers.countQuery(db, allre14query);
    } catch (Exception e) {
      e.printStackTrace();
      allrelease14count1 = 0;
    }

    try {
      if (User_Action.equalsIgnoreCase(AddUser)) {
        addUser(request, response);
      } else if ("Login".equalsIgnoreCase(User_Action)) {
        UsrLogin(request, response);
      } else if ("Lookup".equalsIgnoreCase(User_Action)) {
        try {
          Ariba_ID = request.getParameter("aribaID").toString();
        } catch (Exception e) {
          AribaID = ""; /*e.printStackTrace();*/
        }
        try {
          payload_ID = request.getParameter("PAYLOAD_ID").toString();
        } catch (Exception e) {
          payload_ID = "NO"; /*e.printStackTrace();*/
        }
        try {
          logonID = request.getParameter("LoginID").toString();
        } catch (Exception e) {
          e.printStackTrace();
        }

        if (fetchAndFillForm(logonID)) {
          BuildAddUpdHTML(AddUser, "NA", Vfac, "", Ariba_ID, " ", payload_ID, "FOUND", out);
        } else {
          fname = "";
          lname = "";
          mi = "";
          phone = "";
          altphone = "";
          pager = "";
          email = "";
          fax = "";
          fac = "";
          //Ariba_ID = "";
          BuildAddUpdHTML(AddUser, "NA", Vfac, "", Ariba_ID, "<BR>Could not find the Global ID please fillout form.", payload_ID, "", out);
        }
        //out.close();
      } else if ("startingtcmis".equalsIgnoreCase(User_Action)) {
        try {
          Logon_ID = request.getParameter("LOGON_ID").toString();
        } catch (Exception e) {}
        try {
          payload_ID = request.getParameter("PAYLOAD_ID").toString();
        } catch (Exception e) {
          payload_ID = "NO";
        }
        BuildIntorPage("LOGIN", Logon_ID, payload_ID, out);
      } else {
        try {
          String query = "select session_id,payload_id,USER_ID from punchout_session where PAYLOAD_ID = '" + payloadidfromurl.trim() + "'";
          //For Local Testing when not coming from Ariba
          //String query = "select session_id,payload_id,USER_ID from punchout_session where session_id = '1182871007423587024'";
          //System.out.println("Query to check Ariba Status    " + query);
          dbrs = db.doQuery(query);
          rs = dbrs.getResultSet();

          while (rs.next()) {
            payload_ID = (rs.getString("payload_id") == null ? "NO" : rs.getString("payload_id"));
            Ariba_ID = (rs.getString("USER_ID") == null ? "ABCDEFGHTESTXYZ" : rs.getString("USER_ID"));
          }

          if ( (payload_ID != "0") && (payload_ID.length() > 5)) {
            Ariba_Flag = "Y";
          } else {
            //System.out.println("Not From Ariba    " + payload_ID + "     " + Ariba_ID);
            Ariba_Flag = "N";
            out.write("Not From Ariba");
            return;
          }

        } catch (Exception e) {
          e.printStackTrace();
          BuildIntorPage("ERROR", "", "", out);
          Ariba_Flag = "N";
          return;
        } finally {
          dbrs.close();
        }

        try {
          String query = "select p.LOGON_ID from personnel p, company_application_logon cal where cal.COMPANY_APPLICATION_LOGON_ID = '" + Ariba_ID.trim() + "'"+
                         " and p.personnel_id = cal.personnel_id and cal.application = 'iProcurement'";
          dbrs = db.doQuery(query);
          rs = dbrs.getResultSet();

          while (rs.next()) {
            Logon_ID = (rs.getString("LOGON_ID") == null ? "ABCDEFGHTESTXYZ" : rs.getString("LOGON_ID"));
          }
        } catch (Exception e) {
          e.printStackTrace();
          BuildIntorPage("ERROR", "", "", out);
          return;
        } finally {
          dbrs.close();
        }

        if ( ("ABCDEFGHTESTXYZ".equalsIgnoreCase(Ariba_ID)) || ("ABCDEFGHTESTXYZ".equalsIgnoreCase(Logon_ID))) {
          try {
            logonID = request.getParameter("LoginID").toString();
          } catch (Exception e) {
            logonID = "";
          }
          try {
            fname = request.getParameter("FName").toString();
          } catch (Exception e) {
            fname = "";
          }
          try {
            mi = request.getParameter("MName").toString();
          } catch (Exception e) {
            mi = "";
          }
          try {
            lname = request.getParameter("LName").toString();
          } catch (Exception e) {
            lname = "";
          }
          try {
            email = request.getParameter("EMail").toString();
          } catch (Exception e) {
            email = "";
          }
          try {
            phone = request.getParameter("Phone").toString();
          } catch (Exception e) {
            phone = "";
          }
          try {
            altphone = request.getParameter("AltPhone").toString();
          } catch (Exception e) {
            altphone = "";
          }
          try {
            pager = request.getParameter("Pager").toString();
          } catch (Exception e) {
            pager = "";
          }
          try {
            fax = request.getParameter("Fax").toString();
          } catch (Exception e) {
            fax = "";
          }
          try {
            fac = request.getParameter("Facility").toString();
          } catch (Exception e) {}
          BuildAddUpdHTML(AddUser, "NA", Vfac, "", Ariba_ID, " ", payload_ID, "", out);
        } else {
          //Login in to tcmIS
          BuildIntorPage("LOGIN", Logon_ID, payload_ID, out);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      db.close();
    }
    return;
  }

  //End of method

  private boolean getFacilities() {
    boolean result = false;
    Vfac.clear();
    try {
      Facility fac = null;
      fac = new Facility(db);
      if (fac == null) {
        //System.out.println("*** unable to get Facility object ***" );
      }
      Vfac = fac.getAllFacXRef2();
      if (!Vfac.isEmpty()) {
        result = true;
      }
    } catch (Exception e) {
      //System.out.println("*** Error on open DB on LoadFacs ***");
      e.printStackTrace();
    }
    return result;
  }

  //End of method

  private boolean fetchAndFillForm(String logonID) throws ServletException, IOException {

    boolean result = false;
    //get data from form
    //search
    try {
      Personnel p = new Personnel(db);
      //if (UserExists(logonID))
      if (p.isAvailable(logonID)) {
        //System.out.println("*** User does not exists in tcmIS ***");
        result = false;
      } else {
        //Personnel p = new Personnel(db);
        //System.out.println("Got Here at 333 12-04-01");
        p.load(logonID);
        //System.out.println("Done Loading Personnel Data for Dobject");

        fname = p.getFirstName();
        if (fname == null) {
          fname = "";
        }
        lname = p.getLastName();
        if (lname == null) {
          lname = "";
        }
        mi = p.getMidInitial();
        if (mi == null) {
          mi = "";
        }
        phone = p.getPhone();
        if (phone == null) {
          phone = "";
        }
        altphone = p.getAltPhone();
        if (altphone == null) {
          altphone = "";
        }
        pager = p.getPager();
        if (pager == null) {
          pager = "";
        }
        email = p.getEmail();
        if (email == null) {
          email = "";
        }
        fax = p.getFax();
        if (fax == null) {
          fax = "";
        }
        fac = p.getFacilityId();
        if (fac == null) {
          fac = "";
        }
        PersonnelID = p.getPersonnelId().intValue();

        //System.out.println("Done Fetching Form");
        result = true;
      }
    } catch (Throwable e) {
      e.printStackTrace();
      //System.out.println(e.getMessage());
      result = false;
    }
    return result;

  }

  //End of method

  private void addUser(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

    //get data from form
    String Passwordgiven = "";
    String AribaIDfromDB = "";
    String PerssonelID = "";
    String PayLID = "";
    PrintWriter out = response.getWriter();
    //System.out.println((String)request.getParameter("WebStart"));

    try {
      logonID = request.getParameter("LoginID").toString();
    } catch (Exception e) { /*e.printStackTrace();*/}
    try {
      AribaID = request.getParameter("aribaID").toString();
    } catch (Exception e) {
      AribaID = ""; /*e.printStackTrace();*/
    }
    try {
      PayLID = request.getParameter("PAYLOAD_ID").toString();
    } catch (Exception e) {
      PayLID = "NO"; /*e.printStackTrace();*/
    }

    try {
      fname = request.getParameter("FName").toString();
    } catch (Exception e) { /*e.printStackTrace();*/}
    try {
      mi = request.getParameter("MName").toString();
    } catch (Exception e) {
      mi = "";
    }
    try {
      lname = request.getParameter("LName").toString();
    } catch (Exception e) { /*e.printStackTrace();*/}
    try {
      email = request.getParameter("EMail").toString();
    } catch (Exception e) { /*e.printStackTrace();*/}
    try {
      phone = request.getParameter("Phone").toString();
    } catch (Exception e) { /*e.printStackTrace();*/}
    try {
      altphone = request.getParameter("AltPhone").toString();
    } catch (Exception e) {
      altphone = "";
    }
    try {
      pager = request.getParameter("Pager").toString();
    } catch (Exception e) {
      pager = "";
    }
    try {
      fax = request.getParameter("Fax").toString();
    } catch (Exception e) {
      fax = "";
    }
    try {
      fac = request.getParameter("Facility").toString();
    } catch (Exception e) { /*e.printStackTrace();*/}
    try {
      fac = request.getParameter("Facility").toString();
    } catch (Exception e) { /*e.printStackTrace();*/}
    try {
      Passwordgiven = request.getParameter("password1").toString();
    } catch (Exception e) { /*e.printStackTrace();*/}
    //
    //System.out.println(AribaID);
    try {
      String facUrl = URLDecoder.decode(fac);
      //System.out.println("*** fac Obtained from URL Decoder is ***"  + facUrl );
      fac = facUrl;
    } catch (Exception e) {
      e.printStackTrace();
    }
    //
    //System.out.println("*** Obtained the form data ***");

    //build has table of personal info
    Hashtable newData = new Hashtable();
    newData.put("FNAME", fname);
    newData.put("LNAME", lname);
    newData.put("MI", mi);
    newData.put("PHONE", phone);
    newData.put("ALTPHONE", altphone);
    newData.put("PAGER", pager);
    newData.put("EMAIL", email);
    newData.put("PASSWORD", "seagate"); //test
    newData.put("FAX", fax);
    newData.put("FAC", fac);
    newData.put("LOGONID", logonID);

    //System.out.println("*** Added data to Hashtable ***");
    try {
      if (getBundle().getString("DEBUG").equals("true")) {
        HelpObjs.monitor(1, "UserProfile:updating data:", getBundle());
      }
      PersonnelProfile p = new PersonnelProfile(db);
      boolean presult = false;
      dbError = false;

      if (!UserExists(logonID)) {
        //Find out if there is an Ariba ID associated with it.
        try {
          String query = "select cal.COMPANY_APPLICATION_LOGON_ID,cal.PERSONNEL_ID from personnel p, company_application_logon cal where p.LOGON_ID = '" + logonID + "'"+
                         " and p.personnel_id = cal.personnel_id and cal.application = 'iProcurement'";
          dbrs = db.doQuery(query);
          rs = dbrs.getResultSet();

          while (rs.next()) {
            AribaIDfromDB = (rs.getString("COMPANY_APPLICATION_LOGON_ID") == null ? "" : rs.getString("COMPANY_APPLICATION_LOGON_ID"));
            PerssonelID = (rs.getString("PERSONNEL_ID") == null ? "" : rs.getString("PERSONNEL_ID"));
          }
          Integer UID = new Integer(PerssonelID);
          newData.put("UID", UID);
        } catch (Exception e) {
          e.printStackTrace();
          throw e;
          //System.out.println("No Ariba ID");
        } finally {
          dbrs.close();
        }

        if ("DonTDoAnyThingHere1234".equalsIgnoreCase(Passwordgiven)) {
          presult = p.webUpdate(newData);
        } else {
          presult = p.webUpdate(newData);
        }
        if (!presult) {
          BuildAddUpdHTML(AddUser, "NA", Vfac, "An Error Occured \\n Please try again.", AribaID, " ", PayLID, "", out);
        } else {
          BuildIntorPage("LOGIN", logonID, PayLID, out);
        }
      } else {
        presult = p.webInsert(newData);
        //System.out.println("Personnel profile insert result " + presult);
        if (!presult) {
          BuildAddUpdHTML(AddUser, "NA", Vfac, "An Error Occured \\n Please try again.", AribaID, " ", PayLID, "", out);
        } else {
          BuildIntorPage("LOGIN", logonID, PayLID, out);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      //System.out.println("*** Error on open DB on UserRegistarion ***");
      BuildAddUpdHTML(AddUser, "NA", Vfac, "An Error Occured \\n Please try again.", AribaID, " ", PayLID, "", out);
    }
    return;
  }

  //End of method

  protected void printHTMLHeader(String Loggedint, String NewDestination, String NavigationHelp, String noshow,
                                 String BarTitle, String Errormessage, String PayID, PrintWriter regout) {
    //StringBuffer MsgHH = new StringBuffer();

    String hosturl = radian.web.tcmisResourceLoader.gethosturl();
    regout.println("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"10; url=" + hosturl + "tcmIS/aribacheckout?payloadID=" + PayID + "\">\n\n");

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

    if (! ("Yes".equalsIgnoreCase(noshow))) {
      regout.println("</TABLE>\n");

      regout.println("<TABLE WIDTH=\"725\" BORDER=\"0\">\n");
      regout.println("<TR VALIGN=\"top\">\n");
      regout.println("  <TD WIDTH=\"125\" BGCOLOR=\"#fff0e1\">\n");

      regout.println("    <TABLE cellSpacing=0 cellPadding=0 width=\"125\" BGCOLOR=\"#fff0e1\" border=0 height=\"600\">\n");
      regout.println("      <TR VALIGN=\"top\">\n");
      regout.println("      <TD>\n");
      regout.println(" <CENTER>\n");
      regout.println(" <IMG SRC=\"" + getBundle().getString("HOME_IMAGE") + "\">\n");
      regout.println(" </CENTER>\n");
      regout.println("<FONT FACE=\"Arial\" SIZE=1><BR>If you have registered before use your Global Id to look up your data.</FONT>\n");
      regout.println("<FORM NAME=\"LoginForm\" METHOD=\"post\" ACTION=\"" + Lookup_Servlet + "\" onsubmit =\"return getWebStart()\">\n");

      client = getBundle().getString("DB_CLIENT");
      regout.println("      <FONT FACE=\"Arial\" SIZE=\"1\">Global Id:</FONT>\n");
      regout.println("        <INPUT TYPE=\"text\" NAME=\"LoginID\" SIZE=\"10\" MAXLENGTH=\"25\" VALUE=\"" + logonID + "\">\n");
      regout.println("      \n");

      regout.println("<INPUT TYPE=\"hidden\" NAME=\"aribaID\" VALUE=\"" + (Ariba_ID == "ABCDEFGHTESTXYZ" ? "" : Ariba_ID) + "\">\n");
      regout.println("<INPUT TYPE=\"hidden\" NAME=\"PAYLOAD_ID\" VALUE=\"" + PayID + "\">\n");
      regout.println("<INPUT TYPE=\"hidden\" NAME=\"WebStart\" VALUE=\"No\">\n");
      regout.println("     <BR><BR>\n");
      regout.println("        <INPUT TYPE=\"submit\" NAME=\"Submit\" VALUE=\"Lookup\">\n");
      regout.println("    <BR><FONT FACE=\"Arial\" COLOR=\"#FF0000\" SIZE=2>\n");
      regout.println(Errormessage);
      regout.println("</FONT></FORM></TD>\n");
      regout.println("</TR>\n");

      regout.println("</TABLE>\n");
      regout.println("</TD>\n");
    } else {
      regout.println("<TR>\n");
    }

    return;
  }

  //End of method

  //Returns True if the use does not exist
  private boolean UserExists(String logonID) {
    boolean result = false;
    try {
      Personnel p = new Personnel(db);
      result = (p.isAvailable(logonID));
      //System.out.println("**** result of UserExists is " + result );
    } catch (Exception e) {
      e.printStackTrace();
      dbError = true;
      result = false;
    }
    return result;
  }

  //End of method

  private void BuildAddUpdHTML(String UserAction, String UserLoginID, Vector vfac, String ShowMsg,
                               String Aribaid, String Globalnotfound, String PAYLOADID, String Found, PrintWriter regout) {
    //StringBuffer Msg = new StringBuffer();
    HeaderFooter hf = new HeaderFooter(getBundle(), db);

    //System.out.println("This is the AribaId in BuildHTML  "+Aribaid);

    if (fname == null) {
      fname = "";
    }
    if (lname == null) {
      lname = "";
    }
    if (mi == null) {
      mi = "";
    }
    if (phone == null) {
      phone = "";
    }
    if (altphone == null) {
      altphone = "";
    }
    if (pager == null) {
      pager = "";
    }
    if (email == null) {
      email = "";
    }
    if (fax == null) {
      fax = "";
    }
    if (fac == null) {
      fac = "";
    }

    if (!getFacilities()) {
      //System.out.println("*** Unable to get facilities" );
    }

    //HEAD
    regout.println(hf.printHTMLHeader(client + " tcmIS User Registration",intcmIsApplication));

    //Top Tool Bars
    if (ShowMsg.length() < 1) {
      printJavaScripts("", regout);
      printJavaScriptsWeBStart("", "", regout);
      printHTMLHeader("", "", "tcmistcmis32.gif", "", " User Registration", Globalnotfound, PAYLOADID, regout);
    } else {
      printJavaScripts(ShowMsg, regout);
      printJavaScriptsWeBStart("", "", regout);
      printHTMLHeader("", "showMsg()", "tcmistcmis32.gif", "", " User Registration", Globalnotfound, PAYLOADID, regout);
    }

    regout.println("<TD WIDTH=\"600\">\n");
    regout.println("<FORM METHOD=\"POST\" NAME=\"RegForm\" ACTION=\"" + ADDUSER_Servlet + "\" onsubmit =\"return CheckValues(this)\">\n");

    regout.println("<TABLE BORDER=0 CELLSPACING=0 CELLPADDING=0 WIDTH=600>\n");
    regout.println("<TR><TD WIDTH=\"80\" VALIGN=\"middle\" ALIGN=\"RIGHT\" HEIGHT=\"35\">\n");
    regout.println("<FONT FACE=\"Arial\" SIZE=1>*<B>First Name:&nbsp;</B></FONT></TD>\n");

    //Name
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
    /*regout.println("<TR>\n");
             regout.println("<TD WIDTH=\"80\" VALIGN=\"middle\" ALIGN=\"RIGHT\" HEIGHT=\"35\">\n");
             regout.println("<FONT FACE=\"Arial\" SIZE=1>*<B>Ariba ID:&nbsp;</B></FONT></TD>\n");
             regout.println("<TD WIDTH=\"80\" VALIGN=\"middle\"  HEIGHT=\"35\">\n");
             regout.println("<FONT FACE=\"Arial\" SIZE=1>"+(Aribaid=="ABCDEFGHTESTXYZ"?"":Aribaid)+"<input TYPE=\"hidden\" NAME=\"aribaID\" VALUE=\""+(Aribaid=="ABCDEFGHTESTXYZ"?"":Aribaid)+"\"></FONT></TD>\n");
             regout.println("<TD WIDTH=\"120\" VALIGN=\"middle\"  ALIGN=\"RIGHT\" HEIGHT=\"35\">\n");
             regout.println("<FONT FACE=\"Arial\" SIZE=1><B>&nbsp;</B></FONT></TD>\n");
             regout.println("<TD WIDTH=\"80\" VALIGN=\"middle\" HEIGHT=\"35\">\n");
             regout.println("<FONT FACE=\"Arial\" SIZE=1>&nbsp;</FONT></TD>\n");
             regout.println("<TD WIDTH=\"80\" VALIGN=\"middle\"  HEIGHT=\"35\">\n");
             regout.println("<FONT FACE=\"Arial\" SIZE=1>&nbsp;</FONT></TD>\n");
             regout.println("<TD WIDTH=\"80\" VALIGN=\"middle\" HEIGHT=\"35\">\n");
             regout.println("<FONT FACE=\"Arial\" SIZE=1>&nbsp;</FONT></TD>\n");
             regout.println("</TR>\n");*/

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

    regout.println("<TR>\n");
    regout.println("<TD WIDTH=\"80\" VALIGN=\"middle\"  ALIGN=\"RIGHT\" HEIGHT=\"35\">\n");
    regout.println("<FONT FACE=\"Arial\" SIZE=1>*<B>Global Id:&nbsp;</B></FONT></TD>\n");

    regout.println("<TD WIDTH=\"80\" VALIGN=\"middle\" HEIGHT=\"35\">\n");
    //loginID
    if ("FOUND".equalsIgnoreCase(Found)) {
      regout.println("<FONT FACE=\"Arial\" SIZE=1>" + logonID.trim() + "<input TYPE=\"hidden\" NAME=\"LoginID\" VALUE=\"" + logonID.trim() + "\"></FONT></TD>\n");
    } else {
      regout.println("<FONT FACE=\"Arial\" SIZE=1><input TYPE=\"TEXT\"  SIZE=\"12\" MAXLENGTH=\"30\" NAME=\"LoginID\" VALUE=\"" + logonID.trim() + "\"></FONT></TD>\n");
    }
    //faclogonID
    if (UserAction.equalsIgnoreCase(AddUser)) {
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
        regout.println("<OPTION " + (fac.equalsIgnoreCase(key) ? "selected" : "") + " VALUE=\"" + key + "\">" + data.get(key).toString() + "</OPTION>\n");
      }
    }
    regout.println("</SELECT>\n");
    regout.println("</FONT></TD>\n");

    regout.println("<TD WIDTH=\"60\" VALIGN=\"middle\"  HEIGHT=\"35\">\n");
    regout.println("<FONT FACE=\"Arial\" SIZE=1>&nbsp;</FONT></TD>\n");
    regout.println("<TD WIDTH=\"60\" VALIGN=\"middle\" HEIGHT=\"35\">\n");
    regout.println("<FONT FACE=\"Arial\" SIZE=1>&nbsp;</FONT></TD>\n");

    regout.println("</TR>\n");

    /*regout.println("<TR>\n");
             if ("FOUND".equalsIgnoreCase(Found))
             {
             regout.println("<TD WIDTH=\"80\" VALIGN=\"middle\"  ALIGN=\"RIGHT\" HEIGHT=\"35\">\n");
             regout.println("<FONT FACE=\"Arial\" SIZE=1>*<B>Password:&nbsp;</B></FONT></TD>\n");
             regout.println("<TD WIDTH=\"80\" VALIGN=\"middle\" HEIGHT=\"35\">\n");
             regout.println("<FONT FACE=\"Arial\" SIZE=1><INPUT TYPE=\"password\" NAME=\"password1\" SIZE=\"12\" MAXLENGTH=\"25\" VALUE=\"\"></FONT></TD>\n");
             regout.println("<TD WIDTH=\"120\" VALIGN=\"middle\"  ALIGN=\"RIGHT\" HEIGHT=\"35\">\n");
             regout.println("<FONT FACE=\"Arial\" SIZE=1>*<B>Re-Enter Password:&nbsp;</B></FONT></TD>\n");
             regout.println("<TD WIDTH=\"80\" VALIGN=\"middle\" HEIGHT=\"35\">\n");
             regout.println("<FONT FACE=\"Arial\" SIZE=1><INPUT TYPE=\"password\" NAME=\"password2\" SIZE=\"12\" MAXLENGTH=\"25\" VALUE=\"\"></FONT></TD>\n");
             }
             else
             {
             regout.println("<TD WIDTH=\"80\" VALIGN=\"middle\"  ALIGN=\"RIGHT\" HEIGHT=\"35\">\n");
             regout.println("</TD>\n");
             regout.println("<TD WIDTH=\"80\" VALIGN=\"middle\" HEIGHT=\"35\">\n");
             regout.println("<INPUT TYPE=\"hidden\" NAME=\"password1\" VALUE=\"DonTDoAnyThingHere1234\"></TD>\n");
             regout.println("<TD WIDTH=\"80\" VALIGN=\"middle\"  ALIGN=\"RIGHT\" HEIGHT=\"35\">\n");
             regout.println("</TD>\n");
             regout.println("<TD WIDTH=\"80\" VALIGN=\"middle\" HEIGHT=\"35\">\n");
             regout.println("<INPUT TYPE=\"hidden\" NAME=\"password2\" VALUE=\"DonTDoAnyThingHere1234\"></TD>\n");
             }
             regout.println("<TD WIDTH=\"80\" VALIGN=\"middle\"  HEIGHT=\"35\">\n");
             regout.println("<FONT FACE=\"Arial\" SIZE=1>&nbsp;</FONT></TD>\n");
             regout.println("<TD WIDTH=\"80\" VALIGN=\"middle\" HEIGHT=\"35\">\n");
             regout.println("<FONT FACE=\"Arial\" SIZE=1>&nbsp;</FONT></TD>\n");
             regout.println("</TR>\n");
     */

    regout.println("<TR><TD WIDTH=\"100\" VALIGN=\"middle\" ALIGN=\"CENTER\" COLSPAN=3 HEIGHT=\"35\">\n");
    regout.println("<INPUT TYPE=\"hidden\" NAME=\"WebStart\" VALUE=\"No\">\n");
    regout.println("<INPUT TYPE=\"hidden\" NAME=\"PAYLOAD_ID\" VALUE=\"" + PAYLOADID + "\">\n");
    regout.println("<INPUT TYPE=\"submit\" VALUE=\"Continue\" NAME=\"B1\"></TD>\n");
    regout.println("<TD WIDTH=\"100\" VALIGN=\"middle\" ALIGN=\"CENTER\" COLSPAN=3 HEIGHT=\"35\">\n");
    regout.println("<INPUT TYPE=\"Reset\" VALUE=\"Reset\" NAME=\"B2\"></TD>\n");
    regout.println("</TR>\n");
    regout.println("</TABLE>\n");
    regout.println("</FORM>\n");

    regout.println("</TD>\n");
    regout.println("</TR>\n");
    regout.println("</TABLE>\n");

    regout.println("</BODY>\n");
    regout.println("</HTML>\n");

    //System.out.println("*** Created html *****");
    return;
  }

  private void BuildIntorPage(String ShowMsg, String LOGON_ID, String PAYLOAD_ID, PrintWriter regout) {
    //StringBuffer MsgI = new StringBuffer();
    HeaderFooter hf = new HeaderFooter(getBundle(), db);

    //HEAD
    regout.println(hf.printHTMLHeader("tcmIS Initialization",intcmIsApplication));

    //Top Tool Bars
    if (ShowMsg.equalsIgnoreCase("LOGIN")) {
      printJavaScripts("LOGIN", regout);
      printJavaScriptsWeBStart(LOGON_ID, PAYLOAD_ID, regout);
      printHTMLHeader("", "loginnow()", "tcmistcmis32.gif", "Yes", " Initialization", " ", PAYLOAD_ID, regout);
    } else if (ShowMsg.equalsIgnoreCase("NOTHING")) {
      printJavaScripts("NO", regout);
      printJavaScriptsWeBStart(LOGON_ID, PAYLOAD_ID, regout);
      printHTMLHeader("", "", "tcmistcmis32.gif", "Yes", " Initialization", " ", PAYLOAD_ID, regout);
    } else if (ShowMsg.equalsIgnoreCase("ERROR")) {
      printJavaScripts("An Error Occured.\\n Please try again.", regout);
      printJavaScriptsWeBStart("", "", regout);
      printHTMLHeader("", "showMsg()", "tcmistcmis32.gif", "Yes", " Initialization", "", "", regout);
    } else if (ShowMsg.equalsIgnoreCase("LOGGED")) {
      printJavaScripts("NO", regout);
      printJavaScriptsWeBStart("", "", regout);
      printHTMLHeader("", "", "tcmistcmis32.gif", "Yes", " Initialization", " ", PAYLOAD_ID, regout);
    } else {
      printJavaScripts(ShowMsg, regout);
      printJavaScriptsWeBStart("", "", regout);
      printHTMLHeader("", "showMsg()", "tcmistcmis32.gif", "Yes", " Initialization", " ", PAYLOAD_ID, regout);
    }
    if (! (ShowMsg.equalsIgnoreCase("ERROR"))) {
      regout.println("<TD BGCOLOR=#FFFFFF VALIGN=\"TOP\">\n");
      regout.println("  <TABLE cellspacing=0 border=0 width=725>\n");
      regout.println("    <TR VALIGN=\"TOP\">\n");
      regout.println("      <TD>\n");
      regout.println("      <FONT FACE=\"Arial\" SIZE=\"3\">\n");

      regout.println("<SCRIPT LANGUAGE=\"Javascript\" TYPE=\"text/javascript\">\n");
      regout.println("<!--\n");
      regout.println("launchLink(\"images/tcmislogo.jpg\", \"tcmisfromweb.jnlp\", 140, 94);\n");
      regout.println("// -->\n");
      regout.println("</SCRIPT>\n");

      regout.println("      </FONT>\n");
      regout.println("      </TD>\n");
      regout.println("    </TR>\n");
      regout.println("  </TABLE>\n");
      regout.println("</TD>\n");
      regout.println("</TR>\n");
      regout.println("</TABLE>\n");

      regout.println("<FORM NAME=\"LoginingForm\" METHOD=\"post\" ACTION=\"" + Login_Servlet + "\" onsubmit =\"return getWebStartlogin()\">\n");
      regout.println("<INPUT TYPE=\"hidden\" NAME=\"WebStart\" VALUE=\"No\">\n");
      regout.println("<INPUT TYPE=\"hidden\" NAME=\"LOGON_ID\" VALUE=\"" + LOGON_ID + "\">\n");
      regout.println("<INPUT TYPE=\"hidden\" NAME=\"PAYLOAD_ID\" VALUE=\"" + PAYLOAD_ID + "\">\n");
      regout.println("</FORM>\n");
    } else {

      regout.println("<TD BGCOLOR=#FFFFFF VALIGN=\"TOP\">\n");
      regout.println("  <TABLE cellspacing=0 border=0 width=725>\n");
      regout.println("    <TR VALIGN=\"TOP\">\n");
      regout.println("      <TD ALIGN=\"CENTER\">\n");
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

    regout.println("</BODY>\n");
    regout.println("</HTML>\n");

    return;
  }

  private void UsrLogin(HttpServletRequest request,
                        HttpServletResponse response) throws ServletException, IOException {

    StringBuffer Msg = new StringBuffer();
    PrintWriter out = response.getWriter();
    Random rand = new Random();
    int tmpReq = rand.nextInt();
    Integer tmpReqNum = new Integer(tmpReq);

    int tmpReq1 = rand.nextInt();
    Integer tmpReqNum1 = new Integer(tmpReq1);

    String passwd = "";
    String WebStartY = "";
    String Logon_ID1 = "";
    String payload_ID1 = "";
    String Ariba_Flag1 = "No";
    String Logged = "N";
    String iprocoracle = "N";

    try {
      Logon_ID1 = request.getParameter("LOGON_ID").toString();
    } catch (Exception e) {}
    try {
      payload_ID1 = request.getParameter("PAYLOAD_ID").toString();
    } catch (Exception e) {
      payload_ID1 = "NO";
    }
    try {
      WebStartY = request.getParameter("WebStart").toString();
    } catch (Exception e) {}

    //System.out.println("WebStart  " +WebStartY+" Logon Id "+Logon_ID1+"  "+payload_ID1);

    if ( (payload_ID1 != "NO") && (payload_ID1.length() > 5)) {
      //System.out.println("Payload ID else  " +payload_ID1);
      Ariba_Flag1 = "Y";
    } else {
      Ariba_Flag1 = "N";
      out.write("Not From Ariba");
      return;
    }

    //if ("Yes".equalsIgnoreCase(WebStartY))
    if (true) {
      try {
        String query = "select LOGGED,ORACLE from punchout_session where PAYLOAD_ID = '" + payload_ID1 + "'";
        dbrs = db.doQuery(query);
        rs = dbrs.getResultSet();

        while (rs.next()) {
          Logged = rs.getString("LOGGED") == null ? "N" : rs.getString("LOGGED").trim();
          iprocoracle = rs.getString("ORACLE") == null ? "N" : rs.getString("ORACLE").trim();
        }
      } catch (Exception e) {
        e.printStackTrace();
        BuildIntorPage("ERROR", "", "", out);
        return;
      } finally {
        dbrs.close();
      }

      if ("N".equalsIgnoreCase(Logged)) {
        HttpSession session = request.getSession();
        //System.out.println("Session Id"+session.getId());

        try {
          String query = "update punchout_session set LOGGED = 'Y' where PAYLOAD_ID = '" + payload_ID1 + "' and SESSION_ID = '" + session.getId() + "'";
          db.doUpdate(query);
        } catch (Exception e) {
          e.printStackTrace();
          BuildIntorPage("ERROR", "", "", out);
          return;
        } finally {
          dbrs.close();
        }

        try {
          String query = "select PASSWORD from personnel where lower(LOGON_ID) = lower('" + Logon_ID1 + "')";
          dbrs = db.doQuery(query);
          rs = dbrs.getResultSet();

          while (rs.next()) {
            passwd = (rs.getString("PASSWORD") == null ? "" : rs.getString("PASSWORD"));
          }
        } catch (Exception e) {
          e.printStackTrace();
          BuildIntorPage("ERROR", "", "", out);
          return;
        } finally {
          dbrs.close();
        }

        String query = "select count(*) from tcmis_prerelease_version where lower(USERNAME) = lower('" + Logon_ID1 + "')";
        String prere14query = "select count(*) from tcmis_prerelease_version where lower(USERNAME) = lower('" + Logon_ID1 + "') and VERSION = '1.4' ";
        String allre14query = "select count(*) from tcmis_prerelease_version where lower(USERNAME) = lower('All') and VERSION = '1.4' ";

        int prereleasecount1 = 0;
        int prerelease14count1 = 0;
        int allrelease14count1 = 0;

        try {
          prereleasecount1 = DbHelpers.countQuery(db, query);
          prerelease14count1 = DbHelpers.countQuery(db, prere14query);
          allrelease14count1 = DbHelpers.countQuery(db, allre14query);
          //System.out.println("Query count is " + prereleasecount1 );
        } catch (Exception e) {
          e.printStackTrace();
          BuildIntorPage("ERROR", "", "", out);
          return;
        }

        //response.setContentType("application/x-java-jnlp-file");
        String jnlpcodebase = radian.web.tcmisResourceLoader.getjnlpcodebase();
        String jnlp14dir = radian.web.tcmisResourceLoader.getprerelase14dir();

        //PrintWriter out1 = new PrintWriter (response.getOutputStream());
        //System.out.println("**** Starting tcmIS");

        Msg.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        if (prerelease14count1 > 0) {
          Msg.append("<jnlp spec=\"1.0+\" version=\"2.0\" codebase=\"" + jnlpcodebase + jnlp14dir + "/\">\n");
        } else if (prereleasecount1 > 0) {
          Msg.append("<jnlp spec=\"1.0+\" version=\"2.0\" codebase=\"" + jnlpcodebase + getBundle().getString("TEST_JNLP_DIRECTORY") + "/\">\n");
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
        Msg.append("	<all-permissions/>\n");
        Msg.append("</security>\n");
        Msg.append("  <resources>\n");
        if (prerelease14count1 > 0 || allrelease14count1 > 0) {
          Msg.append("    <j2se version=\"1.4+\" max-heap-size=\"128m\"/>\n");
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
        Msg.append("    <jar href=\"dx.jar\" download=\"eager\"/>\n");
        Msg.append("    <jar href=\"jsse.jar\" download=\"eager\"/>\n");
        Msg.append("    <jar href=\"jnet.jar\" download=\"eager\"/>\n");
        Msg.append("    <jar href=\"jcert.jar\" download=\"eager\"/>\n");
        Msg.append("  </resources>\n");

        Msg.append("  <application-desc main-class=\"" + getBundle().getString("TCMIS_START_CLASS") + "\">\n");
        Msg.append(" <argument>" + Logon_ID1 + "</argument>\n");
        Msg.append(" <argument>" + passwd + "</argument>\n");
        Msg.append(" <argument>" + Ariba_Flag1 + "</argument>\n");
        Msg.append(" <argument>" + payload_ID1 + "</argument>\n");
        Msg.append(" <argument>2</argument>\n");
        Msg.append("</application-desc>\n");
        Msg.append("</jnlp>\n");

        try {
          String filepathtojnlpfile = radian.web.tcmisResourceLoader.getjnlpfilepath();
          //System.out.println("----- jnlp: "+filepathtojnlpfile);
          ps = new PrintStream(new FileOutputStream(filepathtojnlpfile + Logon_ID1 + tmpReqNum.toString() + tmpReqNum1.toString() + ".jnlp"), true);

          byte outbuf[];
          outbuf = Msg.toString().getBytes();
          ps.write(outbuf);
          ps.close();
        } catch (IOException writee) {
          writee.printStackTrace();
        }

        BuildIntorPageredirect("" + Logon_ID1 + tmpReqNum.toString() + tmpReqNum1.toString() + ".jnlp", payload_ID1, Logon_ID1, iprocoracle, out);
        //System.out.println( jnlpcodebase + "/jnlp/loginfile/" + Logon_ID1 + tmpReqNum.toString() + tmpReqNum1.toString() + ".jnlp" );

        //Msg.toString());
        //out1.close();
      } else {
        //response.setContentType("text/html");
        //System.out.println("Already Logged in Once");
        BuildIntorPage("LOGGED", Logon_ID1, payload_ID1, out);
      }
    } else {
      //response.setContentType("text/html");
      BuildIntorPage("NOTHING", Logon_ID1, payload_ID1, out);
    }
  }

  private void BuildIntorPageredirect(String filename, String PayID, String LOGON_ID, String iprocoracle, PrintWriter regout) {
    //StringBuffer MsgI=new StringBuffer();
    HeaderFooter hf = new HeaderFooter(getBundle(), db);
    regout.println(hf.printHTMLHeader("tcmIS Initialization",intcmIsApplication));

    String hosturl = radian.web.tcmisResourceLoader.gethosturl();

    if ("Y".equalsIgnoreCase(iprocoracle)) {
      regout.println("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"10; url=" + hosturl + "tcmIS/aribacheckout?payloadID=" + PayID + "\">\n\n");
      String url = hosturl + "jnlp/loginfile/" + filename;
      printJavaScriptsWeBStart(LOGON_ID, PayID, regout);
      regout.println(hf.printTopToolBar("window.open('" + url + "')", "tcmistcmis32.gif"));
    } else {
      regout.println("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; url=" + hosturl + "jnlp/loginfile/" + filename + "\">\n\n");
      printJavaScriptsWeBStart(LOGON_ID, PayID, regout);
      regout.println(hf.printTopToolBar("", "tcmistcmis32.gif"));
    }

    regout.println("<TD BGCOLOR=#FFFFFF VALIGN=\"TOP\">\n");
    regout.println("  <TABLE cellspacing=0 border=0 width=725>\n");

    regout.println("    <TR VALIGN=\"TOP\">\n");
    regout.println("      <TD>\n");
    regout.println("      <FONT FACE=\"Arial\" SIZE=\"3\">\n");

    regout.println("<SCRIPT LANGUAGE=\"Javascript\" TYPE=\"text/javascript\">\n");
    regout.println("<!--\n");
    regout.println("launchLink(\"images/tcmislogo.jpg\", \"tcmisfromweb.jnlp\", 140, 94);\n");
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
    return;
  }

  protected void printJavaScripts(String ShowThis, PrintWriter regout) {
    //StringBuffer Msgt = new StringBuffer();
    regout.println("<SCRIPT LANGUAGE=\"JavaScript1.2\" TYPE=\"text/javascript\">\n <!--\n");
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
    } else if ("NO".equalsIgnoreCase(ShowThis)) {
      //regout.println("function showMsg(){\n alert(\""+ShowThis+"\"); \n}\n");
    } else {
      regout.println("function showMsg(){\n alert(\"" + ShowThis + "\"); \n}\n");
    }

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

    regout.println("testvar =window.document.RegForm.aribaID.value.length;\n");
    regout.println("if (testvar < 1)\n");
    regout.println(" {finalMsg = finalMsg + \"\\n Ariba ID.\";\n allClear = allClear + 1;}\n");

    regout.println("testvar =window.document.RegForm.Phone.value.length;\n");
    regout.println("if (testvar < 1)\n");
    regout.println(" {finalMsg = finalMsg + \"\\n Primary Contact Phone Number.\";\n allClear = allClear + 1;}\n");

    regout.println("testvar =window.document.RegForm.LoginID.value.length;\n");
    regout.println("if (testvar < 1)\n");
    regout.println(" {finalMsg = finalMsg + \"\\n LoginID.\";\n allClear = allClear + 1;}\n");

    /*regout.println("Passvar1 =window.document.RegForm.password1.value;\n");
             regout.println("Passvar1Len =window.document.RegForm.password1.value.length;\n");
             regout.println("Passvar2Len =window.document.RegForm.password1.value.length;\n");
             regout.println("Passvar2 =window.document.RegForm.password2.value;\n");
             regout.println("if ( (Passvar1Len < 1) || (Passvar2Len < 1) ){\n finalMsg = finalMsg + \"\\n\\nMake sure you type a password.\";\n allClear = allClear + 1; \n}\n");
             regout.println("else \n {\n");
             regout.println("if (Passvar1 == Passvar2){\n }\n else\n{\nfinalMsg = finalMsg + \"\\n\\nMake sure you type the same password twice.\";\n allClear = allClear + 1; \n}\n}\n");
     */
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

    return;
  }

  protected void printHTMLLoginError(PrintWriter regout) {
    //StringBuffer MsgER = new StringBuffer();
    HeaderFooter hf = new HeaderFooter(getBundle(), db);
    //HEAD
    regout.println(hf.printHTMLHeader("tcmIS",intcmIsApplication));
    printJavaScripts("", regout);
    printJavaScriptsWeBStart("", "", regout);
    //Top Tool Bars
    regout.println(hf.printTopToolBar("", "tcmistcmis32.gif"));
    regout.println("<TD  HEIGHT=\"600\" BGCOLOR=#FFFFFF VALIGN=\"TOP\">\n");
    //WIDTH=\"600\"
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
    return;

  }

  //End of method

  protected void printJavaScriptsWeBStart(String LOGON_ID, String PAYLOAD_ID, PrintWriter regout) {
    //StringBuffer Msg = new StringBuffer();
    regout.println("     <!--- JavaScript to detect if Java Web Start is installed or not --->\n");
    regout.println("<SCRIPT LANGUAGE=\"Javascript\"> <!--\n\n");
    regout.println("testurl3 = \"" + TestInstall_Servlet + "&LOGON_ID=" + LOGON_ID + "&PAYLOAD_ID=" + PAYLOAD_ID + "\";\n");
    regout.println("   var javawsInstalled = 0;\n");
    regout.println("   isIE = \"false\";\n");

    regout.println("   if (navigator.mimeTypes && navigator.mimeTypes.length) {\n");
    regout.println("      x = navigator.mimeTypes['application/x-java-jnlp-file'];\n");
    regout.println("      if (x) javawsInstalled = 1;\n");
    regout.println("    } else {\n");
    regout.println("      isIE = \"true\";\n");
    regout.println("    }\n");

    regout.println("function insertLink(url, name) {\n");
    regout.println("testurl = \"/download/installtcmis14.exe\";\n");
    regout.println("imagerl = \"/images/logo_s.gif\";\n");
    regout.println("imager2 = \"/images/tcminstallsave.gif\";\n");
    regout.println("imager3 = \"/images/installlogo.gif\";\n");
    regout.println("imager4 = \"/images/installstep1.gif\";\n");
    regout.println("imager5 = \"/images/installstep2.gif\";\n");
    regout.println("imager6 = \"/images/installstep3.gif\";\n");

    regout.println("style = \"color:#0000ff\";\n");

    regout.println("document.write(\"<FONT FACE=Arial SIZE=5><B><CENTER>Java Web Start Plugin not installed - use the following procedure<BR><BR></CENTER></B></FONT>\");\n");
    regout.println("document.write(\"<FONT FACE=Arial SIZE=2>1.  Download the <I>tcm</I>IS installer ..<A href=\"+testurl+\"><IMG src=\"+imagerl+\" BORDER=1></A>\");\n");
    regout.println("document.write(\"<BR><BR>&nbsp;&nbsp;&nbsp;&nbsp;Save file to your Desktop...\");\n");
    regout.println("document.write(\"<BR><BR><CENTER><IMG src=\"+imager2+\"  WIDTH=427 BORDER=1></CENTER><BR><BR>\");\n");
    regout.println("document.write(\"2.  Go to your desktop and double click on the <I>tcm</I>IS installation icon... <IMG src=\"+imager3+\" BORDER=1 WIDTH=55 HEIGHT=55>\");\n");
    regout.println("document.write(\"<BR><BR>3.  Click the button which says Step 1.\");\n");
    regout.println("document.write(\"<BR><BR><CENTER><IMG src=\"+imager4+\"  WIDTH=427 BORDER=1></CENTER><BR><BR>\");\n");
    regout.println("document.write(\"&nbsp;&nbsp;&nbsp;&nbsp;Accept Sun's license and accept the default answers during installation...\");\n");

    regout.println("document.write(\"<BR><BR>4.  Click the button which says Step 2.\");\n");
    regout.println("document.write(\"<BR><BR><CENTER><IMG src=\"+imager5+\"  WIDTH=427 BORDER=1></CENTER><BR><BR>\");\n");
    regout.println("document.write(\"&nbsp;&nbsp;&nbsp;&nbsp;Accept Sun's license and accept the default answers during installation...\");\n");

    regout.println("document.write(\"<BR><BR>5.  This completes your installation.\");\n");
    regout.println("document.write(\"<BR><BR><CENTER><IMG src=\"+imager6+\"  WIDTH=427 BORDER=1></CENTER><BR><BR>\");\n");
    regout.println("document.write(\"&nbsp;&nbsp;&nbsp;&nbsp;Click Exit.\");\n");
    regout.println("if (isIE == \"true\")\n {\n");
    regout.println("document.write(\"<BR><BR>6. Click <A href=\"+testurl3+\" STYLE=\"+style+\">here</A> to start <I>tcm</I>IS.</FONT><BR><BR>\");\n");
    regout.println("}\n else \n {\n");
    regout.println("document.write(\"<BR><BR>6. You need to restart your browser for the Java Web Start Plugin to work.</FONT><BR><BR>\");\n");
    regout.println("}\n");

    //regout.println("document.write(\"<BR><BR>4. Click <A href=\"+testurl3+\" STYLE=\"+style+\" onclick=\"+testurl4+\">here</A> to start <I>tcm</I>IS.</FONT><BR><BR>\");\n");
    regout.println("}\n");

    regout.println("function launchLink(imageUrl, jnlpUrl, title, desc) {\n");
    regout.println("imager5 = \"/images/tcmisstarting.gif\";\n");
    regout.println("testurl2 = \"/download/ar500enu.exe\";\n");
    regout.println("LoginUrl = \"" + Login_Servlet + "\"\n");
    regout.println("Scriptclose = \"JavaScript:parent.close();\"\n");
    regout.println("style = \"color:#0000ff\";\n");
    regout.println("       if (javawsInstalled)\n");
    regout.println("       {\n");
    regout.println("         document.write(\"<BR><BR><CENTER><IMG SRC=\"+imager5+\">\");\n");
    regout.println("         document.write(\"<BR><BR><BR><BR><BR><BR><CENTER>Please note that you will be needing <A href=\"+testurl2+\" STYLE=\"+style+\">Acrobat Reader</A> to view MSDS files</CENTER>\");\n");
    regout.println("       }\n");
    regout.println("       else\n");
    regout.println("       {\n");
    regout.println("      insertLink(jnlpUrl,\"Start tcmIS\");\n");
    regout.println("      }\n");
    regout.println("    }\n");

    regout.println("    function getWebStart() {\n");
    regout.println("       if (javawsInstalled)\n");
    regout.println("       {\n");
    regout.println("        window.document.LoginForm.WebStart.value = \"Yes\";\n return true;\n");
    regout.println("      }\n");
    regout.println("       else\n");
    regout.println("       {\n");
    regout.println("     window.document.LoginForm.WebStart.value = \"Notre\";\n return true;\n");
    regout.println("      }\n");
    regout.println("    }\n");

    regout.println("    function getWebStartlogin() {\n");
    regout.println("       if (javawsInstalled)\n");
    regout.println("       {\n");
    regout.println("        window.document.LoginingForm.WebStart.value = \"Yes\";\n return true;\n");
    regout.println("      }\n");
    regout.println("       else\n");
    regout.println("       {\n");
    regout.println("     window.document.LoginingForm.WebStart.value = \"Notre\";\n \n");
    //regout.println("      alert(\"You don't have Java Web Start Plugin installed.\\n \\nPlease follow these instructions to install and start tcmIS.\"); \n");
    regout.println("    return false;\n");
    regout.println("      }\n");
    regout.println("    }\n");

    regout.println("// -->\n</SCRIPT>\n");

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

    return;
  }

  //Get Servlet information
  public String getServletInfo() {
    return "radian.web.servlets.share.UserRegistartion";
  }

  //End of method

  protected abstract String getClient();

  protected abstract ServerResourceBundle getBundle();

}
