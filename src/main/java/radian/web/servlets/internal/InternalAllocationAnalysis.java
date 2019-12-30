package radian.web.servlets.internal;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.web.servlets.share.AllocationAnalysis;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2001
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 */

public class InternalAllocationAnalysis extends HttpServlet implements SingleThreadModel
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
        AllocationAnalysis obj = null;

        HttpSession session = request.getSession();

        Cookie[] cookies = request.getCookies();
        String searchString = radian.web.HTMLHelpObj.getCookieValue(cookies,"InternalAllocationAnalysis","");

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
           out.println( radian.web.HTMLHelpObj.printLoginScreen( errormsg,"Allocation Analysis" ) );
           out.flush();
         }
         else
         {
            /*branch_plant = request.getParameter("HubName");
            if (branch_plant == null)
              branch_plant = "";
            session.setAttribute("BRANCH_PLANT", branch_plant );*/

            obj = new AllocationAnalysis((ServerResourceBundle) new InternalServerResourceBundle(),db);
            obj.setupdateStatus(true);
            obj.doResult(request,response,session);
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

