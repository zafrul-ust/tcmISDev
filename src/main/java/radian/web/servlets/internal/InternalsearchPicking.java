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

//import com.jamonapi.Monitor;
//import com.jamonapi.MonitorFactory;
import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.web.barcode.BolPicklistPage;
import radian.web.barcode.BoxLabelPage;
import radian.web.barcode.PicklistPage;
import radian.web.barcode.ConsolidateditemPage;
import radian.web.servlets.share.genPickList;
import radian.web.servlets.share.searchPicking;
/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2001
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 * 11-05-03 - Using user group Picking to decide if a person has access to this page instead of the Inventory user group
 * 01-11-04 - Monitering Usage
 * 11-10-04 - Ability to sort the Picklist
 * 12-07-04 - Ability to print consolidated item picklist
 */

public class InternalsearchPicking  extends HttpServlet implements SingleThreadModel
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
        searchPicking obj = null;
        BolPicklistPage obj1 = null;
        HttpSession session = request.getSession();
		//Monitor mon=MonitorFactory.start("Search Picking");
		//String personnelid= session.getAttribute( "PERSONNELID" ) == null ? "" : session.getAttribute( "PERSONNELID" ).toString();
		//Monitor monuser=MonitorFactory.start("Search Picking by "+personnelid+"");

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

        if ( ! ( auth.equalsIgnoreCase( "authenticated" ) ) )
        {
          out.println( radian.web.HTMLHelpObj.printLoginScreen( errormsg,"Picking" ) );
          out.flush();
        }
        else
        {
          String branch_plant=request.getParameter( "HubName" );
          if ( branch_plant == null )
            branch_plant="";
          session.setAttribute( "BRANCH_PLANT",branch_plant );

          obj=new searchPicking( ( ServerResourceBundle )new InternalServerResourceBundle(),db );

		  if ( radian.web.HTMLHelpObj.hasaccessto( session,"Picking" ) )
		  {
			obj.setupdateStatus( true );
		  }
		  else
          {
            out.println( radian.web.HTMLHelpObj.printLoginScreenOptions( "You don't have access to this page.","Picking",session ) );
            out.close();
            return;

          }

          String boldetails=request.getParameter( "boldetails" );
          if ( boldetails == null )
            boldetails="No";

          String generate_picks=request.getParameter( "generate_picks" );
          if ( generate_picks == null )
            generate_picks="No";

          String generate_bols=request.getParameter( "generate_bols" );
          if ( generate_bols == null )
            generate_bols="No";

          String User_Action=request.getParameter( "UserAction" );
          if ( User_Action == null )
            User_Action="New";

          String subuserAction=null;
          subuserAction=request.getParameter( "SubUserAction" );
          if ( subuserAction == null )
            subuserAction="New";

		  String User_Sort=request.getParameter( "SortBy" );
		  if ( User_Sort == null )
			User_Sort="";
			//User_Sort="FACILITY_ID,APPLICATION";

          //System.out.println( "User Actions " + User_Action + "   Sub User Action  " + subuserAction );

          if ( "printpicks".equalsIgnoreCase( User_Action ) )
          {
            genPickList genpobj=new genPickList( ( ServerResourceBundle )new InternalServerResourceBundle(),db );
            genpobj.doResult( request,response,session );
            genpobj=null;
          }
          else if ( "generatebols".equalsIgnoreCase( User_Action ) )
          {
            String picklistid=session.getAttribute( "PICKLIST_ID" ).toString();
            Vector printdata= ( Vector ) session.getAttribute( "PRINTDATA" );

            obj1=new BolPicklistPage();

            //StringBuffer MsgSB=new StringBuffer();
            String url="";
            url=obj1.GenerateBol( printdata,picklistid,boldetails );
			out.println(radian.web.HTMLHelpObj.labelredirection(url));

            out.close();
          }
		  else if ( "generatepicks".equalsIgnoreCase( User_Action ) )
          {
            String picklistid=session.getAttribute( "PICKLIST_ID" ).toString();
            Vector printdata= ( Vector ) session.getAttribute( "PRINTDATA" );

            //StringBuffer MsgSB=new StringBuffer();
            PicklistPage picklistobj=new PicklistPage();

            String url="";
            url=picklistobj.generatePicklist( picklistid,printdata,User_Sort );

            out.println(radian.web.HTMLHelpObj.labelredirection(url));
            out.close();
          }
		  //else if ( "generatepicks".equalsIgnoreCase( User_Action ) )
		  else if ( "genconsolidatedpicks".equalsIgnoreCase( User_Action ) )
		  {
			String picklistid=session.getAttribute( "PICKLIST_ID" ).toString();
			 String opsEntityId=request.getParameter( "opsEntityId" );
			  if ( opsEntityId == null )
				  opsEntityId="";
			Vector printdata= ( Vector ) session.getAttribute( "PRINTDATA" );

			//StringBuffer MsgSB=new StringBuffer();
			ConsolidateditemPage consolidateobj=new ConsolidateditemPage();

			String url="";
			url=consolidateobj.generatePicklist( picklistid,printdata,User_Sort,opsEntityId );

			out.println(radian.web.HTMLHelpObj.labelredirection(url));
			out.close();
		  }
          else if ( "generateboxlabels".equalsIgnoreCase( User_Action ) )
          {
            Vector printdata= ( Vector ) session.getAttribute( "PRINTDATA" );
            BoxLabelPage boxlabelobj=new BoxLabelPage();
            String url="";
            url=boxlabelobj.GenerateBoxLabel( printdata );

            out.println( url );
            out.close();
          }
          else
          {
            obj.doResult( request,response,session );
          }
        }
      }
      catch ( Exception e )
      {
        e.printStackTrace();
        //StringBuffer MsgSB=new StringBuffer();
        out.println( "<HTML><HEAD>\n" );
        out.println( "<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n" );
        out.println( "<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n" );
        out.println( "<TITLE>Container Label Generator</TITLE>\n" );
        out.println( "</HEAD>  \n" );
        out.println( "<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">  \n" );
        out.println( "<CENTER><img src=\"/images/tcmintro.gif\"  border=1 align=\"middle\"><P></P><BR>\n" );
        out.println( "<FONT FACE=\"Arial\" size=\"5\" color=\"#000080\"><b>tcmIS Label Generator</b></font><P></P><BR>\n" );
        out.println( "<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b>No Pick List Was Built.</b></font><P></P><BR>\n" );
        out.println( "</CENTER>\n" );
        out.println( "</BODY></HTML>    \n" );
        //out.println( MsgSB );
        out.close();
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

    public void doGet( HttpServletRequest request,HttpServletResponse response ) throws ServletException,IOException
    {
      doPost( request,response );
    }
}

