package radian.web.servlets.internal;

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
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.web.servlets.share.assignaddinvengroup;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2004
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 */

public class internalassignInvengroup  extends HttpServlet implements SingleThreadModel
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
        assignaddinvengroup obj = null;
        HttpSession session = request.getSession();

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
           out.println( radian.web.HTMLHelpObj.printpoLoginScreen( errormsg,"Ship To" ) );
           out.flush();
         }
         else
         {
		   if ( radian.web.HTMLHelpObj.hasaccessto( session,"Purchasing" ) )
			{

			}
			else
           {
             out.println( radian.web.HTMLHelpObj.printpoLoginScreenOptions( "You don't have access to this page.","Purchase Order",session ) );
             out.close();
             return;
           }

           obj=new assignaddinvengroup( ( ServerResourceBundle )new InternalServerResourceBundle(),db );
           obj.doResult( request,response );
         }
       }
       catch ( Exception e )
       {
         e.printStackTrace();
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