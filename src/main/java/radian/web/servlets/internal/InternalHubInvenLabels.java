package radian.web.servlets.internal;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;
import java.util.Hashtable;
import java.util.Vector;
import java.sql.ResultSet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import radian.tcmis.server7.share.db.DBResultSet;
//import com.jamonapi.Monitor;
//import com.jamonapi.MonitorFactory;
import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.web.barcode.BarCodePage;
import radian.tcmis.server7.share.dbObjs.DbHelpers;
import radian.web.servlets.share.HubInvenLabels;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 1998
 * Company:      URS Corporation
 * @author       Rajendra Rajput
 * @version
 *
 * 11-11-02
 * testing seagate labels
 * 01-11-04 - Monitering Usage
 * 04-30-04 - Company Id Raytheon can have read only pages
 * 06-30-04 Changing Printer Path to be based on personnel ID
 * 02-28-05 Giving the option to not print Kit/Case Qty Labels. Also giving the option to pick a prnter if there are multiple printers at a hub
 */

public class InternalHubInvenLabels  extends HttpServlet implements SingleThreadModel
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
      PrintWriter out = new PrintWriter ( new OutputStreamWriter(response.getOutputStream (), "UTF8" ) );        
      response.setContentType( "text/html; charset=UTF-8" );
      RayTcmISDBModel db = null;
      String branch_plant;
      HubInvenLabels obj = null;
      BarCodePage obj1 = null;
      String User_Action = "";
      String User_Session = "";
      String generate_labels = "";
      String po_line = "";

      Vector sequencenumbers = new Vector();
      Vector qunatitynumbers = new Vector();

      HttpSession session = request.getSession();
	  //Monitor mon=MonitorFactory.start("Hub Logistics");
	  //String personnelid= session.getAttribute( "PERSONNELID" ) == null ? "" : session.getAttribute( "PERSONNELID" ).toString();
	  //Monitor monuser=MonitorFactory.start("Hub Logistics by "+personnelid+"");

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
          out.println( radian.web.HTMLHelpObj.printLoginScreen( errormsg,"Hub Inventory" ) );
          out.flush();
        }
        else
        {
          String CompanyID = session.getAttribute("COMPANYID").toString();
        	
          branch_plant = request.getParameter("HubName");
          if (branch_plant == null)
          {
              branch_plant = "";
          }
          session.setAttribute("BRANCH_PLANT", branch_plant );

          obj = new HubInvenLabels((ServerResourceBundle) new InternalServerResourceBundle(),db);
		  if ( radian.web.HTMLHelpObj.hasaccessto( session,"Inventory" ) )
		  {
			 obj.setupdateStatus( true );
		  }
		  else
		  {
			 if ( radian.web.HTMLHelpObj.hasaccessto( session,"inventoryReadonly" ) )
			 {
				obj.setupdateStatus( true );
			 }
			 else
			 {
				obj.setupdateStatus(false);
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
          po_line = request.getParameter("poline");
          if (po_line == null)
            po_line = "";

          //System.out.println("Checkbok   "+generate_labels+" Size  "+PaperSize+" User Action  :"+ User_Action+" Po Line: "+po_line);
          if ( "yes".equalsIgnoreCase( generate_labels ) && "GenerateLabels".equalsIgnoreCase( User_Action ) )
          {
            sequencenumbers= ( Vector ) session.getAttribute( "LABEL_SEQUENCE_NUMBERS" );
            qunatitynumbers= ( Vector ) session.getAttribute( "LABEL_QUANTITYS" );

            obj1=new BarCodePage( ( ServerResourceBundle )new InternalServerResourceBundle(),db,qunatitynumbers,PaperSize );

            String url="";

            String HubNoforlabel=request.getParameter( "HubNoforlabel" );
            if ( HubNoforlabel == null )
              HubNoforlabel="";
            HubNoforlabel.trim();
            obj1.sethubNum(HubNoforlabel);

			String printKitLabels=request.getParameter( "printKitLabels" );
			if ( printKitLabels == null )
			  printKitLabels="";

			String printerPath=request.getParameter( "printerPath" );
			if ( printerPath == null )
			  printerPath="";

			String printerRes=request.getParameter( "printerRes" );
			if ( printerRes == null )
			  printerRes="";

			String personnelId= session.getAttribute( "PERSONNELID" ) == null ? "" : session.getAttribute( "PERSONNELID" ).toString();

			if ( printerPath.length() == 0 )
			{
			  int printerCount=DbHelpers.countQuery( db,"select count(distinct x.PRINTER_PATH) from LOCATION_LABEL_PRINTER x, PERSONNEL Y WHERE X.PRINTER_LOCATION = Y.PRINTER_LOCATION AND Y.PERSONNEL_ID = " +
													 personnelId + " and x.LABEL_STOCK = '" + PaperSize + "'" );
			  if ( printerCount > 1 )
			  {
				printPrinterChoice( db,out,printKitLabels,printerPath,printerRes,PaperSize,personnelId,HubNoforlabel, CompanyID);
				return;
			  }
		    }

            url=obj1.generateContainerLabel( sequencenumbers,personnelId,!"Yes".equalsIgnoreCase(printKitLabels),printerPath,printerRes );

            out.println(radian.web.HTMLHelpObj.labelredirection(url));
            out.close();
          }
          else
          {
            obj.doPost( request,response,out );
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
		//mon.stop();
		//monuser.stop();
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

  private void printPrinterChoice(TcmISDBModel db,PrintWriter out,String printKitLabels,String printerPath,String printerRes,String paperSize,String personnelId,String HubNoforlabel, String CompanyID)
  {
	out.println("<HTML>\n");
	out.println("<HEAD>\n");
	out.println("<TITLE>Re-Print Labels</TITLE>\n");
	out.println("<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=iso-8859-1\">\n");
	out.println("<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n");
	out.println("<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n");
	out.println("<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"/stylesheets/global.css\"></LINK>\n");
	out.println("<SCRIPT SRC=\"/clientscripts/receivingjs.js\" LANGUAGE=\"JavaScript\">\n</SCRIPT>\n");
	out.println("</HEAD>\n");
	out.println("<BODY>\n");

	out.println("<DIV ID=\"TRANSITPAGE\" STYLE=\"display: none;\">\n");
	out.println("<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B>Please wait while the page is updated...</B></FONT></center>\n");
	out.println("</DIV>\n");
	out.println("<DIV ID=\"MAINPAGE\" STYLE=\"\">\n");

	out.println(radian.web.HTMLHelpObj.PrintTitleBarNolink("<B>Re-Print Labels</B>\n"));

	out.println("<FORM method=\"POST\" NAME=\"receiving\" onSubmit=\"return SubmitOnlyOnce()\">\n");
	out.println("<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\"GenerateLabels\">\n");
	out.println("<INPUT TYPE=\"hidden\" NAME=\"printerRes\" VALUE=\""+printerRes+"\">\n");
	out.println("<INPUT TYPE=\"hidden\" NAME=\"printKitLabels\" VALUE=\""+printKitLabels+"\">\n");
	out.println("<INPUT TYPE=\"hidden\" NAME=\"paperSize\" VALUE=\""+paperSize+"\">\n");
	out.println("<input type=\"hidden\" name=\"personnelCompanyId\" id=\"personnelCompanyId\" VALUE=\""+CompanyID+"\">\n");

	out.println("<BR><B>Please Pick a Printer:</B>");
	out.println("<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"3\" WIDTH=\"500\" CLASS=\"moveup\">\n");
	DBResultSet dbrs = null;
	ResultSet rs = null;
	String tmpPrinterPath = "";
	String tmpPrinterRes = "";

	try
	{
	  String iszplprinter="select distinct x.PRINTER_PATH,x.PRINTER_RESOLUTION_DPI from LOCATION_LABEL_PRINTER x, PERSONNEL Y WHERE X.PRINTER_LOCATION = Y.PRINTER_LOCATION AND Y.PERSONNEL_ID = " +
						  personnelId + " and x.LABEL_STOCK = '" + paperSize + "' ORDER BY x.PRINTER_PATH";
	  dbrs=db.doQuery( iszplprinter );
	  rs=dbrs.getResultSet();

	  while ( rs.next() )
	  {
		tmpPrinterPath=rs.getString( "PRINTER_PATH" ) == null ? " " : rs.getString( "PRINTER_PATH" );
		tmpPrinterRes=rs.getString( "PRINTER_RESOLUTION_DPI" ) == null ? " " : rs.getString( "PRINTER_RESOLUTION_DPI" );

		out.println( "<TR VALIGN=\"MIDDLE\">\n" );
		out.println( "<TD WIDTH=\"1%\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
		out.println( "<INPUT ID=\"option1\" TYPE=\"radio\" NAME=\"printerPath\" onChange=\"setprinterRes('" + tmpPrinterRes + "')\" value=\"" + tmpPrinterPath + "\" " + ( tmpPrinterPath.equalsIgnoreCase( printerPath ) ? "checked" : "" ) + ">\n" );
		out.println( "</TD>\n<TD WIDTH=\"5%\" ALIGN=\"LEFT\" CLASS=\"announce\" ID=\"option3\">" + tmpPrinterPath + "&nbsp;</TD>\n" );
		out.println( "</TR>\n" );
	  }
	}
	catch ( Exception e )
	{
	  e.printStackTrace();
	}
	finally
	{
	  if ( dbrs != null ){dbrs.close();}
	}

	  out.println("<TR VALIGN=\"MIDDLE\">\n");
	  //Generate Labels Button
	  out.println("<TD WIDTH=\"1%\" ALIGN=\"RIGHT\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n");
	  out.println("<INPUT TYPE=\"submit\" VALUE=\"Generate Labels\" NAME=\"SearchButton\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n");
	  out.println("</TD>\n");

	  out.println("<TD WIDTH=\"5%\" VALIGN=\"MIDDLE\" CLASS=\"announce\">\n");
	  out.println("<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" VALUE=\"Close\" OnClick=\"cancel()\" NAME=\"CloseButton\">\n");
	  out.println("</TD></TR>\n");

	  /*out.println("<TR VALIGN=\"MIDDLE\">\n");
	  out.println( "<TD WIDTH=\"5%\" HEIGHT=\"35\" ALIGN=\"RIGHT\" CLASS=\"announce\">\n" );
	  out.println( "<INPUT type=\"checkbox\" name=\"printKitLabels\" value=\"Yes\" " + ( printKitLabels.equalsIgnoreCase( "Yes" ) ? "checked" : "" ) + ">\n" );
	  out.println( "</TD>\n" );
	  out.println( "<TD WIDTH=\"5%\" COLSPAN=\"7\" HEIGHT=\"35\" ALIGN=\"LEFT\" CLASS=\"announce\">\n" );
	  out.println( "&nbsp;Skip Kit/Case Qty Labels\n" );
	  out.println( "</TD>\n" );*/
	  out.println("</TABLE>\n");
  }

}

