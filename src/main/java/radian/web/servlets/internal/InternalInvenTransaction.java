package radian.web.servlets.internal;

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
import radian.web.barcode.BolPagefromShipConfirm;
import radian.web.servlets.share.InventoryTransactions;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2001
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 * 12-09-03 print BOLs for ship confirmed MRs
 */

public class InternalInvenTransaction  extends HttpServlet implements SingleThreadModel
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
      PrintWriter out=response.getWriter();
	  response.setContentType( "text/html" );
      RayTcmISDBModel db=null;
      InventoryTransactions obj=null;
      HttpSession session=request.getSession();

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

        if (auth == null) {auth = "challenge";}
        if (errormsg == null) {errormsg = "";}

        if ( ! ( auth.equalsIgnoreCase( "authenticated" ) ) )
        {
          out.println( radian.web.HTMLHelpObj.printLoginScreen( errormsg,"Inventory Transactions" ) );
          out.flush();
        }
        else
        {
		  String branch_plant = request.getParameter("HubName");
		  if (branch_plant == null)
			branch_plant = "";
		  session.setAttribute("BRANCH_PLANT", branch_plant );

          obj=new InventoryTransactions( ( ServerResourceBundle )new InternalServerResourceBundle(),db );

		  if ( radian.web.HTMLHelpObj.hasaccessto( session,"Inventory" ) )
		  {
			obj.setupdateStatus( true );
		  }
		  else
          {
           obj.setupdateStatus( false );
          }

		  String useraction=request.getParameter( "useraction" );
		  if ( useraction == null )
			useraction="";
		  useraction = useraction.trim();

		  if ( "generatebol".equalsIgnoreCase( useraction ) )
		  {
			String prnumber=request.getParameter( "prnumber" );
			if ( prnumber == null )
			  prnumber="";
			prnumber=prnumber.trim();

			String prlineitem=request.getParameter( "prlineitem" );
			if ( prlineitem == null )
			  prlineitem="";
			prlineitem=prlineitem.trim();

			String prbatch=request.getParameter( "prbatch" );
			if ( prbatch == null )
			  prbatch="";
			prbatch=prbatch.trim();

			BolPagefromShipConfirm obj1=new BolPagefromShipConfirm();
			//StringBuffer MsgSB=new StringBuffer();
			String url="";

			Hashtable hD=new Hashtable();
			Vector Data=new Vector();
			Hashtable summary=new Hashtable();
			int count=0;
			summary.put( "TOTAL_NUMBER",new Integer( count ) );
			Data.addElement( summary );

			hD.put( "USERCHK","Yes" );
			hD.put( "PR_NUMBER",prnumber );
			hD.put( "LINE_ITEM",prlineitem );
			hD.put( "BATCH",prbatch );

			Data.addElement( hD );

			Hashtable recsum=new Hashtable();
			recsum.put( "TOTAL_NUMBER",new Integer( "1" ) );
			Data.setElementAt( recsum,0 );

			url=obj1.GenerateBol( Data,"Yes" );

			if ( url.length() > 1 )
			{
			  out.println( "<HTML><HEAD>\n" );
			  out.println( "<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; url=" + url + "\">\n" );
			  out.println( "<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n" );
			  out.println( "<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n" );
			  out.println( "<TITLE>Container Label Generator</TITLE>\n" );
			  out.println( "</HEAD>  \n" );
			  out.println( "<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">  \n" );
			  out.println( "<CENTER><img src=\"/images/tcmintro.gif\"  border=1 align=\"middle\"><P></P><BR>\n" );
			  out.println( "<FONT FACE=\"Arial\" size=\"5\" color=\"#000080\"><b>tcmIS Label Generator</b></font><P></P><BR>\n" );
			  out.println( "<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b>Server Producing BOLs</b></font><P></P><BR>\n" );
			  out.println( "<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b>Please Wait</b></font><P></P><BR>\n" );
			  out.println( "</CENTER>\n" );
			  out.println( "</BODY></HTML>    \n" );
			}
			else
			{
			  out.println( "<HTML><HEAD>\n" );
			  out.println( "<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n" );
			  out.println( "<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n" );
			  out.println( "<TITLE>Container Label Generator</TITLE>\n" );
			  out.println( "</HEAD>  \n" );
			  out.println( "<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">  \n" );
			  out.println( "<CENTER><img src=\"/images/tcmintro.gif\"  border=1 align=\"middle\"><P></P><BR>\n" );
			  out.println( "<FONT FACE=\"Arial\" size=\"5\" color=\"#000080\"><b>tcmIS Label Generator</b></font><P></P><BR>\n" );
			  out.println( "<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b>No BOLs Were Built.</b></font><P></P><BR>\n" );
			  out.println( "</CENTER>\n" );
			  out.println( "</BODY></HTML>    \n" );
			}
			//out.println( MsgSB );
			out.close();

		  }
		  else
		  {
			obj.doResult( request,response );
		  }
		}
        loginresult=null;
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

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request,response);
    }
}

