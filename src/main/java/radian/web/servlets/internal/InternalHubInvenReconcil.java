package radian.web.servlets.internal;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;
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
import radian.web.servlets.share.HubInvenReconcil;
import radian.web.servlets.share.InvenReconcilePage;
import com.tcmis.common.util.ResourceLibrary;
import java.util.Locale;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2001
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 */

public class InternalHubInvenReconcil  extends HttpServlet implements SingleThreadModel
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
        HubInvenReconcil obj = null;
        String User_Action = "";

        // get the current session context (create one if needed)
        HttpSession session = request.getSession();
        ResourceLibrary res =  new ResourceLibrary("com.tcmis.common.resources.CommonResources", (Locale)session.getAttribute("tcmISLocale"));

        try
        {
          db = new RayTcmISDBModel("Tcm_Ops","2",radian.web.HTMLHelpObj.getTcmISLocaleString(request));
          if ( db == null )
          {
            PrintWriter out2=new PrintWriter( response.getOutputStream() );
            out2.println( "Starting DB" );
            out2.println( "DB is null" );
            out2.close();
            return;
          }

          Hashtable loginresult=new Hashtable();
          loginresult=radian.web.loginhelpObj.logintopage( session,request,db,out );
          String auth= ( String ) loginresult.get( "AUTH" );
          String errormsg= ( String ) loginresult.get( "ERROSMSG" );

          if ( auth == null )
          {
            auth="challenge";
          }
          if ( errormsg == null )
          {
            errormsg="";
          }

          if ( ! ( auth.equalsIgnoreCase( "authenticated" ) ) )
          {
            out.println( radian.web.HTMLHelpObj.printLoginScreen( errormsg,"Inventory Reconciliation" ) );
            out.flush();
          }
	  else
	  {
	  branch_plant=request.getParameter( "HubName" );
	  if ( branch_plant == null )
		branch_plant="";
	  session.setAttribute( "BRANCH_PLANT",branch_plant );

	  obj=new HubInvenReconcil( ( ServerResourceBundle )new InternalServerResourceBundle(),db );

	  if ( radian.web.HTMLHelpObj.hasaccessto( session,"Inventory" ) )
	  {
		obj.setupdateStatus( true );
	  }
	  else
	  {
		String CompanyID=session.getAttribute( "COMPANYID" ) == null ? "" : session.getAttribute( "COMPANYID" ).toString();
		if ( "Radian".equalsIgnoreCase( CompanyID ) )
		{
		  obj.setupdateStatus( false );
		}
		else
		{
		  out.println( radian.web.HTMLHelpObj.printLoginScreenOptions( "You don't have access to this page.","Inventory Reconciliation",session ) );
		  out.close();
		  return;
		}
	  }

            User_Action=request.getParameter( "UserAction" );
            if ( User_Action == null )
              User_Action="New";

            if ( "generatepdf".equalsIgnoreCase( User_Action ) )
            {
              Vector Data=new Vector();
              Data= ( Vector ) session.getAttribute( "DATA" );
              InvenReconcilePage pdfobj=new InvenReconcilePage(( ServerResourceBundle )new InternalServerResourceBundle(),db );

              String hubname=session.getAttribute( "LAST_SEARCHHUB" ).toString();
              String lastcount=session.getAttribute( "LAST_COUNTDATE" ).toString();
              String lastall=session.getAttribute( "LAST_ALL" ).toString();

			  Hashtable hub_list_set=new Hashtable();
			  hub_list_set= ( Hashtable ) session.getAttribute( "HUB_PREF_LIST" );

              Hashtable hub_list= ( Hashtable ) ( hub_list_set.get( "HUB_LIST" ) );
              String branchname=hub_list.get( hubname ).toString();

              String skipreconhand=request.getParameter( "skipreconhand" );
      				if ( skipreconhand == null )
      					skipreconhand="N";
      				skipreconhand=skipreconhand.trim();

              //StringBuffer MsgSB=new StringBuffer();
              String url="";
              url=pdfobj.generatePicklist( Data,branchname,lastcount,lastall,skipreconhand );

              if ( url.length() > 1 )
              {
                out.println( "<HTML><HEAD>\n" );
                out.println( "<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; url=" + url + "\">\n" );
                out.println( "<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n" );
                out.println( "<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n" );
                out.println( "<TITLE>Report Generator</TITLE>\n" );
                out.println( "</HEAD>  \n" );
                out.println( "<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">  \n" );
                out.println( "<CENTER><img src=\"/images/tcmintro.gif\"  border=1 align=\"middle\"><P></P><BR>\n" );
                out.println( "<FONT FACE=\"Arial\" size=\"5\" color=\"#000080\"><b>tcmIS PDF Generator</b></font><P></P><BR>\n" );
                out.println( "<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b>Server PDF File</b></font><P></P><BR>\n" );
                out.println( "<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b>Please Wait</b></font><P></P><BR>\n" );
                out.println( "</CENTER>\n" );
                out.println( "</BODY></HTML>    \n" );
              }
              else
              {
                out.println( "<HTML><HEAD>\n" );
                out.println( "<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n" );
                out.println( "<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n" );
                out.println( "<TITLE>PDF Generator</TITLE>\n" );
                out.println( "</HEAD>  \n" );
                out.println( "<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">  \n" );
                out.println( "<CENTER><img src=\"/images/tcmintro.gif\"  border=1 align=\"middle\"><P></P><BR>\n" );
                out.println( "<FONT FACE=\"Arial\" size=\"5\" color=\"#000080\"><b>PDF Generator Failed</b></font><P></P><BR>\n" );
                out.println( "<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b>No PDF was Built.</b></font><P></P><BR>\n" );
                out.println( "</CENTER>\n" );
                out.println( "</BODY></HTML>    \n" );
              }
              //out.println( MsgSB );
              out.close();
            }
            else if ( "generatexls".equalsIgnoreCase( User_Action ) )
            {
              Vector Data=new Vector();
              Data= ( Vector ) session.getAttribute( "DATA" );

              String hubname=session.getAttribute( "LAST_SEARCHHUB" ).toString();
              String lastcount=session.getAttribute( "LAST_COUNTDATE" ).toString();
              String lastall=session.getAttribute( "LAST_ALL" ).toString();
              String orderBy=session.getAttribute( "LAST_ORDER_BY" ).toString();

              Hashtable hub_list_set= ( Hashtable ) session.getAttribute( "HUB_PREF_LIST" );
              Hashtable hub_list= ( Hashtable ) ( hub_list_set.get( "HUB_LIST" ) );
              String branchname=hub_list.get( hubname ).toString();

              //StringBuffer MsgSB=new StringBuffer();
              String url="";
              InvenReconcilePage pdfobj=new InvenReconcilePage();
              url=pdfobj.generateXlsReport( Data,branchname,lastcount,lastall,orderBy,res );

              if ( url.length() > 1 )
              {
                out.println( "<HTML><HEAD>\n" );
                out.println( "<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; url=" + url + "\">\n" );
                out.println( "<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n" );
                out.println( "<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n" );
                out.println( "<TITLE>Report Generator</TITLE>\n" );
                out.println( "</HEAD>  \n" );
                out.println( "<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">  \n" );
                out.println( "<CENTER><img src=\"/images/tcmintro.gif\"  border=1 align=\"middle\"><P></P><BR>\n" );
                out.println( "<FONT FACE=\"Arial\" size=\"5\" color=\"#000080\"><b>tcmIS XLS Generator</b></font><P></P><BR>\n" );
                out.println( "<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b>Server XLS File</b></font><P></P><BR>\n" );
                out.println( "<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b>Please Wait</b></font><P></P><BR>\n" );
                out.println( "</CENTER>\n" );
                out.println( "</BODY></HTML>    \n" );
              }
              else
              {
                out.println( "<HTML><HEAD>\n" );
                out.println( "<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n" );
                out.println( "<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n" );
                out.println( "<TITLE>PDF Generator</TITLE>\n" );
                out.println( "</HEAD>  \n" );
                out.println( "<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">  \n" );
                out.println( "<CENTER><img src=\"/images/tcmintro.gif\"  border=1 align=\"middle\"><P></P><BR>\n" );
                out.println( "<FONT FACE=\"Arial\" size=\"5\" color=\"#000080\"><b>XLS Generator Failed</b></font><P></P><BR>\n" );
                out.println( "<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b>No XLS was Built.</b></font><P></P><BR>\n" );
                out.println( "</CENTER>\n" );
                out.println( "</BODY></HTML>    \n" );
              }
              //out.println( MsgSB );
              out.close();
            }
            else
            {
              obj.doResult( request,response,out );
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

      public void doGet( HttpServletRequest request,HttpServletResponse response )
         throws ServletException,IOException
      {
        doPost( request,response );
      }
    }