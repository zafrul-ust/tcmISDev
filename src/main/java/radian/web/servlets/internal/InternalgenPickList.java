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
import radian.web.barcode.BolPagefromShipConfirm;
import radian.web.servlets.share.genPickList;
/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2001
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 * 11-05-03 - Using user group Picking to decide if a person has access to this page instead of the Inventory user group
 * 01-11-04 - Monitering Usage
 *
 */

public class InternalgenPickList  extends HttpServlet implements SingleThreadModel
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
		response.setContentType( "text/html" );
        RayTcmISDBModel db = null;
        genPickList obj = null;
        String User_Action = "";
        BolPagefromShipConfirm obj1 = null;
        HttpSession session = request.getSession();
		//Monitor mon=MonitorFactory.start("Create Picklist");
		//String personnelid= session.getAttribute( "PERSONNELID" ) == null ? "" : session.getAttribute( "PERSONNELID" ).toString();
		//Monitor monuser=MonitorFactory.start("Create Picklist by "+personnelid+"");

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
              out.println(radian.web.HTMLHelpObj.printLoginScreen(errormsg,"Generate PickList"));
              out.flush();
          }
          else
          {
            // get the users properties
            String branch_plant = request.getParameter("HubName");
            if (branch_plant == null)
              branch_plant = "All";
            session.setAttribute("BRANCH_PLANT", branch_plant );

            obj = new genPickList((ServerResourceBundle) new InternalServerResourceBundle(),db);

			if ( radian.web.HTMLHelpObj.hasaccessto( session,"Picking" ) )
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
				out.println( radian.web.HTMLHelpObj.printLoginScreenOptions( "You don't have access to this page.","Generate PickList",session ) );
				out.close();
				return;
			  }
            }

            String boldetails = request.getParameter("boldetails");
            if (boldetails == null)
            boldetails = "No";

            String generate_labels = request.getParameter("generate_labels");
            if (generate_labels == null)
            generate_labels ="No";

            User_Action= request.getParameter("UserAction");
            if (User_Action == null)
            User_Action = "New";

             if ("GenerateLabels".equalsIgnoreCase(User_Action) )
            {
                Vector Data = new Vector();
                Data  = (Vector) session.getAttribute("DATA");
                obj1 = new BolPagefromShipConfirm();


                //StringBuffer MsgSB = new StringBuffer();
                String url = "";
                url = obj1.GenerateBol(Data,boldetails);

                if (url.length() > 1)
                {
                out.println("<HTML><HEAD>\n");
                out.println("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; url="+url+"\">\n");
                out.println("<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n");
                out.println("<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n");
                out.println("<TITLE>Container Label Generator</TITLE>\n");
                out.println("</HEAD>  \n");
                out.println("<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">  \n");
                out.println("<CENTER><img src=\"/images/tcmintro.gif\"  border=1 align=\"middle\"><P></P><BR>\n");
                out.println("<FONT FACE=\"Arial\" size=\"5\" color=\"#000080\"><b>tcmIS Label Generator</b></font><P></P><BR>\n");
                out.println("<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b>Server Producing BOLs</b></font><P></P><BR>\n");
                out.println("<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b>Please Wait</b></font><P></P><BR>\n");
                out.println("</CENTER>\n");
                out.println("</BODY></HTML>    \n");
                }
                else
                {
                out.println("<HTML><HEAD>\n");
                out.println("<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n");
                out.println("<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n");
                out.println("<TITLE>Container Label Generator</TITLE>\n");
                out.println("</HEAD>  \n");
                out.println("<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">  \n");
                out.println("<CENTER><img src=\"/images/tcmintro.gif\"  border=1 align=\"middle\"><P></P><BR>\n");
                out.println("<FONT FACE=\"Arial\" size=\"5\" color=\"#000080\"><b>tcmIS Label Generator</b></font><P></P><BR>\n");
                out.println("<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b>No BOLs Were Built.</b></font><P></P><BR>\n");
                out.println("</CENTER>\n");
                out.println("</BODY></HTML>    \n");
                }
                //out.println(MsgSB);
                out.close();
            }
            else
            {
               obj.doResult(request,response,session);
            }
          }
          loginresult = null;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return;
        }
        finally
        {
            db.close();
			//mon.stop();
			//monuser.stop();
            obj = null;
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
    //
}

