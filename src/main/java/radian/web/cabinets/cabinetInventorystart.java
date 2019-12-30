package radian.web.cabinets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2004
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 */

public class cabinetInventorystart  extends HttpServlet implements SingleThreadModel
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
        cabinetInventory obj = null;

        HttpSession session = request.getSession();

        try
        {
          db = new RayTcmISDBModel("Tcm_Ops");
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
		 String cuscabinvpage=session.getAttribute( "CUSTOMER_CABINET_INV_PAGE" ) == null ? "" : session.getAttribute( "CUSTOMER_CABINET_INV_PAGE" ).toString();

	     if ( !auth.equalsIgnoreCase( "authenticated" ) && !"Yes".equalsIgnoreCase(cuscabinvpage) )
         {
           out.println( radian.web.HTMLHelpObj.printLoginScreen( errormsg,"Cabinet Management" ) );
           out.flush();
         }
          else
          {
            String branch_plant=request.getParameter( "HubName" );
            if ( branch_plant == null )
              branch_plant="All";
            session.setAttribute( "BRANCH_PLANT",branch_plant );

            obj=new cabinetInventory( db );

			/*if ( radian.web.HTMLHelpObj.hasaccessto( session,"CabinetMgmt" ) )
			{
			  obj.setupdateStatus( true );
			}
			else
            {
              String CompanyID= session.getAttribute( "COMPANYID" ) == null ? "" : session.getAttribute( "COMPANYID" ).toString();
              if ( "Radian".equalsIgnoreCase( CompanyID ) )
              {
                obj.setupdateStatus( false );
              }
              else
              {
                out.println( radian.web.HTMLHelpObj.printLoginScreenOptions( "You don't have access to this page.","Cabinet Management",session ) );
                out.close();
                return;
              }
            }*/

            obj.doResult( request,response);
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
      //
    }
