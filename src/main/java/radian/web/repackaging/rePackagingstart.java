package radian.web.repackaging;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.web.barcode.BarCodePage;
import radian.web.servlets.internal.InternalServerResourceBundle;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2004
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 *
 * 06-30-04 Changing Printer Path to be based on personnel ID
 * 02-28-05 Giving the option to not print Kit/Case Qty Labels.
 */

public class rePackagingstart  extends HttpServlet implements SingleThreadModel
{

	public void init(ServletConfig config) throws ServletException
	{
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

	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException
	{
	  PrintWriter out = response.getWriter();
	  response.setContentType("text/html");
	  RayTcmISDBModel db = null;
	  String branch_plant;
	  rePackaging obj = null;
	  BarCodePage obj1 = null;
	  String User_Action = "";
	  String User_Session = "";
	  String generate_labels = "";

	  Vector sequencenumbers = new Vector();
	  Vector qunatitynumbers = new Vector();

	  HttpSession session = request.getSession();
	  //Cookie[] cookies = request.getCookies();
	  //String searchString = radian.web.HTMLHelpObj.getCookieValue(cookies,"InternalHubInvenLabels","");

	  try
	  {
		db = new RayTcmISDBModel("Tcm_Ops","2",radian.web.HTMLHelpObj.getTcmISLocaleString(request));
		if (db == null)
		{
			PrintWriter out2 = new PrintWriter (response.getOutputStream());
			out2.println("Starting DB");
			out2.println("DB is null");
			out2.close();
			return;
		}

		Hashtable loginresult = new Hashtable();
		loginresult = radian.web.loginhelpObj.logintopage(session,request,db,out);
		String auth = (String)loginresult.get("AUTH");
		String errormsg = (String)loginresult.get("ERROSMSG");

		if (auth == null) {auth = "challenge";}
		if (errormsg == null) {errormsg = "";}

		if (! ( auth.equalsIgnoreCase( "authenticated" ) ) )
		{
		  out.println( radian.web.HTMLHelpObj.printLoginScreen( errormsg,"Repackaging" ) );
		  out.flush();
		}
		else
		{
		  // get the users properties
		  branch_plant = request.getParameter("HubName");
		  if (branch_plant == null)
		  {
			  branch_plant = "";
		  }

		  session.setAttribute("BRANCH_PLANT", branch_plant );
		  obj = new rePackaging((ServerResourceBundle) new InternalServerResourceBundle(),db);

		  if ( radian.web.HTMLHelpObj.hasaccessto( session,"Repackaging" ) )
		  {
			obj.setupdateStatus( true );
		  }
		  else
		  {
			String CompanyID= session.getAttribute( "COMPANYID" ) == null ? "" : session.getAttribute( "COMPANYID" ).toString();
			if ("Radian".equalsIgnoreCase(CompanyID))
			{
			  obj.setupdateStatus(false);
			}
			else
			{
			  out.println(radian.web.HTMLHelpObj.printLoginScreenOptions("You don't have access to this page.", "Repackaging",session));
			  out.close();
			  return;
			}
		  }

		  String PaperSize = "";
		  User_Session= request.getParameter("session");
		  if (User_Session == null)
			User_Session = "New";
		  User_Action= request.getParameter("UserAction");
		  if (User_Action == null)
			User_Action = "New";
		  generate_labels = request.getParameter("generate_labels");
		  if (generate_labels == null)
			generate_labels ="No";
		  PaperSize = request.getParameter("paperSize");
		  if (PaperSize == null)
			PaperSize = "31";

		  String HubNoforlabel=request.getParameter( "HubNoforlabel" );
		  if ( HubNoforlabel == null )
			HubNoforlabel="";
		  HubNoforlabel.trim();

		  String printKitLabels=request.getParameter( "printKitLabels" );
		  if ( printKitLabels == null )
			printKitLabels="";

		  if ( "yes".equalsIgnoreCase( generate_labels ) && "GenerateLabels".equalsIgnoreCase( User_Action ) )
		  {
			sequencenumbers= ( Vector ) session.getAttribute( "REPACK_LABEL_SEQUENCE_NUMBERS" );
			qunatitynumbers= ( Vector ) session.getAttribute( "REPACK_LABEL_QUANTITYS" );

			obj1=new BarCodePage( ( ServerResourceBundle )new InternalServerResourceBundle(),db,qunatitynumbers,PaperSize );
			obj1.sethubNum(HubNoforlabel);
			String url="";

			String personnelid= session.getAttribute( "PERSONNELID" ) == null ? "" : session.getAttribute( "PERSONNELID" ).toString();
			url=obj1.generateContainerLabel( sequencenumbers,personnelid,!"Yes".equalsIgnoreCase(printKitLabels) );

			out.println(radian.web.HTMLHelpObj.labelredirection(url));
			out.close();
		  }
		  else
		  {
			obj.doPost( request,response );
		  }
		}
	  }
	  catch ( Exception e )
	  {
		e.printStackTrace();
		return;
	  }
	  finally
	  {
		db.close();
		obj=null;
                try {
                  out.close();
                }
                catch(Exception e) {
                  //ignore
                }
                //Runtime.getRuntime().gc();
		return;
	  }
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	 throws ServletException, IOException
	{
		doPost(request,response);
	}
}

