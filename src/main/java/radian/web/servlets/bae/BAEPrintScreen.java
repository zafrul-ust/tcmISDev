package radian.web.servlets.bae;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.web.erw.printScreen.SPrintScreen;

public class BAEPrintScreen  extends HttpServlet implements SingleThreadModel
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

    public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,  IOException
    {
        response.setContentType("text/html");
        RayTcmISDBModel db = null;
        SPrintScreen obj = null;

        try
        {
          db = new RayTcmISDBModel("BAE");
          if (db == null)
          {
              PrintWriter out2 = response.getWriter();

              out2.println("Starting DB");
              out2.println("DB is null");
              out2.close();
              return;
            }

            obj=new SPrintScreen( ( ServerResourceBundle )new BAEServerResourceBundle(),db );
            obj.doResult( request,response );
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
            return;
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
     throws ServletException,  IOException
    {
        doPost(request,response);
    }
}
