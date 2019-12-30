package radian.web.servlets.pos;

import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.web.servlets.internal.InternalServerResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Vector;

/**
 * Title:        Fax Purchase Orders
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      URS/Radian Corporation
 * @author
 * @version 1.0
 */

public class SupplierFaxlogin
 extends HttpServlet {
 private RayTcmISDBModel db = null;
 public void init(ServletConfig config) throws ServletException {
	super.init(config);
 }

 /**
	* HTTP POST handler.
*
	* @param request               An object implementing the request context
	* @param response              An object implementing the response context
	* @exception ServletException  A wrapper for all exceptions thrown by the
	*      servlet.
	*/

 public void doPost(HttpServletRequest request,
	HttpServletResponse response) throws ServletException,
	IOException {
	// get the current session context (create one if needed)
	//String buyername = "";
	PrintWriter out = response.getWriter();
	response.setContentType("text/html");
	HttpSession session = request.getSession();

	try {

     db = new RayTcmISDBModel("supplier","2",radian.web.HTMLHelpObj.getTcmISLocaleString(request));
	 if (db == null) {
		PrintWriter out2 = new PrintWriter(response.getOutputStream());
		out2.println("Starting DB");
		out2.println("DB is null");
		out2.close();
		return;
	 }

	 Hashtable loginresult = new Hashtable();
	 loginresult = radian.web.loginhelpObj.logintopage(session, request, db, out);
	 String auth = (String) loginresult.get("AUTH");
	 String errormsg = (String) loginresult.get("ERROSMSG");

	 if (auth == null) {
		auth = "challenge";
	 }
	 if (errormsg == null) {
		errormsg = "";
	 }

	 if (! (auth.equalsIgnoreCase("authenticated"))) {
		out.println(radian.web.HTMLHelpObj.printpoLoginScreen(errormsg, "Print PO"));
		out.flush();
	 }
	 else {
		String ponumber = request.getParameter("po");
		if (ponumber == null)
		 ponumber = "";

		String blanketpo = request.getParameter("bpo");
		if (blanketpo == null)
		 blanketpo = "";

		if ( (ponumber == "null") || (ponumber.equalsIgnoreCase("")) ||
		 (ponumber.length() == 0)) {
		 ponumber = "N";
		}

		String headerNote = request.getParameter("HeaderNote");
		if (headerNote == null)
		 headerNote = "";

       String localeCode = "";
        localeCode = request.getParameter("localeCode");
		if (localeCode == null)
		 localeCode = "";

       Locale localeObject = (Locale)session.getAttribute("tcmISLocale");
       if (localeObject == null)
       {
           localeObject = new Locale("en","US");
       }

       if( localeCode !=null && localeCode.length() == 5)
       {
          String codes[] = localeCode.split("_");
		  if( codes.length != 2)
			  throw new RuntimeException("Locale needs to be of the format xx_YY.");
		  else
			  localeObject = new Locale(codes[0],codes[1]);
	   }
        session.setAttribute("HeaderNote",
		 BothHelpObjs.makeBlankFromNull( (String) request.getParameter("HeaderNote")));

		String donevvstuff=session.getAttribute( "VVSUPPLYSTUFF" ) == null ? "" : session.getAttribute( "VVSUPPLYSTUFF" ).toString();
		if ( !donevvstuff.equalsIgnoreCase( "Yes" ) )
		{
		 session.setAttribute( "PO_ADD_CHARGE_ITEM_TYPE",radian.web.vvHelpObj.getaddchargeType(db) );
		}
		Vector addChargesList = (Vector) session.getAttribute("PO_ADD_CHARGE_ITEM_TYPE");
		String personnelId = session.getAttribute("PERSONNELID") == null ? "" :
		 session.getAttribute("PERSONNELID").toString();
		String logonName = session.getAttribute("FULLNAME") == null ? "" :
		 session.getAttribute("FULLNAME").toString();

		PoCreate obj1 = new PoCreate( (ServerResourceBundle)new InternalServerResourceBundle(),db);
		try {
		 out.println(obj1.buildPage(ponumber, blanketpo, logonName, addChargesList, db,
			headerNote, personnelId,localeObject,localeCode));
		}
		catch (Exception ee) {
		 ee.printStackTrace();
		}

		addChargesList = null;
		out.close();
	 }
	}
	catch (Exception e) {
	 e.printStackTrace();
	 return;
	}
	finally {
	 db.close();
	}
 }

 public void doGet(HttpServletRequest request,
	HttpServletResponse response) throws ServletException,
	IOException {
	doPost(request, response);
 }
}