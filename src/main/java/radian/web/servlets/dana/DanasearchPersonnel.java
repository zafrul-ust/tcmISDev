package radian.web.servlets.dana;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.web.servlets.share.searchPersonnel;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2004
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 */

public class DanasearchPersonnel  extends HttpServlet implements SingleThreadModel
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
        RayTcmISDBModel db = null;
        searchPersonnel obj = null;
        HttpSession session = request.getSession();

        try
        {
          db = new RayTcmISDBModel("DANA");
          if (db == null)
          {
              PrintWriter out2 = new PrintWriter (response.getOutputStream());
              out2.println("Starting DB");
              out2.println("DB is null");
              out2.close();
              return;
          }

         /*Hashtable loginresult = new Hashtable();
         loginresult = radian.web.loginhelpObj.logintopage(session,request,db,out);
         String auth = (String)loginresult.get("AUTH");
         String errormsg = (String)loginresult.get("ERROSMSG");

         if (auth == null) {auth = "challenge";}
         if (errormsg == null) {errormsg = "";}

         if ( ! ( auth.equalsIgnoreCase( "authenticated" ) ) )
         {
           out.println( radian.web.HTMLHelpObj.printCatalogLoginScreen( errormsg,"Manufacturer's Id" ) );
           out.flush();
         }
         else*/
         {
           obj=new searchPersonnel ( ( ServerResourceBundle )new DanaServerResourceBundle(),db );
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

