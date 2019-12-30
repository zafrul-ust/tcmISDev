package radian.web.cabinets;

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
import radian.web.servlets.internal.InternalServerResourceBundle;
/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2001
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 */

public class cabinetlevelChangestart  extends HttpServlet implements SingleThreadModel
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
        cabinetlevelChange obj = null;
        cabilabelPage obj1 = null;
        HttpSession session = request.getSession();
        Hashtable loginresult = new Hashtable();

        Vector cabids = new Vector();
        Vector cab8ids = new Vector();

        try
        {
          //db = new RayTcmISDBModel("Tcm_Ops","1");
          db = new RayTcmISDBModel("Tcm_Ops");
          if (db == null)
          {
              PrintWriter out2 = new PrintWriter (response.getOutputStream());
              out2.println("Starting DB");
              out2.println("DB is null");
              out2.close();
              return;
          }

          loginresult = radian.web.loginhelpObj.logintopage(session,request,db,out);
          String auth = (String)loginresult.get("AUTH");
          String errormsg = (String)loginresult.get("ERROSMSG");

          if (auth == null) {auth = "challenge";}
          if (errormsg == null) {errormsg = "";}

          if (! ( auth.equalsIgnoreCase( "authenticated" ) ) )
          {
            out.println( radian.web.HTMLHelpObj.printLoginScreen( errormsg,"Cabinet Level Change" ) );
            out.flush();
          }
          else
          {
            obj=new cabinetlevelChange( ( ServerResourceBundle )new InternalServerResourceBundle(),db );

			if ( radian.web.HTMLHelpObj.hasaccessto( session,"CabinetMgmt" ) )
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
                out.println( radian.web.HTMLHelpObj.printLoginScreenOptions( "You don't have access to this page.","Cabinet Level Change",session ) );
                out.close();
                return;
              }
            }

            obj.doResult( request,response );

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
          loginresult = null;
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
