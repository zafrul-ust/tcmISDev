package radian.web.servlets.swa;

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
import com.tcmis.common.admin.beans.PersonnelBean;
import java.util.Collection;
import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.web.HeaderFooter;
import radian.web.bol.clientdotshippingname;
/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2001
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 */

public class SWAdotshippingname  extends HttpServlet implements SingleThreadModel
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
        clientdotshippingname obj = null;
        HttpSession session = request.getSession();

				PersonnelBean personnelBean = (PersonnelBean) session.getAttribute("personnelBean");
				boolean intcmIsApplication = false;
				if (personnelBean !=null)
				{
					 Collection menuItemOvBeanCollection = personnelBean.getMenuItemOvBeanCollection();
					 if (menuItemOvBeanCollection != null && menuItemOvBeanCollection.size() > 0) {
						intcmIsApplication = true;
					 }
				}


        try
        {
          db = new RayTcmISDBModel("SWA");
          if (db == null)
          {
              PrintWriter out2 = new PrintWriter (response.getOutputStream());
              out2.println("Starting DB");
              out2.println("DB is null");
              out2.close();
              return;
          }

		  ServerResourceBundle bundleswa=new SWAServerResourceBundle();
		  HeaderFooter hf=new HeaderFooter( bundleswa,db );

		  Hashtable loginresult = new Hashtable();
		 loginresult = radian.web.loginhelpObj.logintopage(session,request,db,out);
		 String auth = (String)loginresult.get("CLIENT_AUTH");
		 String errormsg = (String)loginresult.get("ERROSMSG");

		 if (auth == null) {auth = "challenge";}
		 if (errormsg == null) {errormsg = "";}

		 if ( ! ( auth.equalsIgnoreCase( "authenticated" ) ) )
		 {
		   session.setAttribute( "clientloginState","challenge" );
		   out.println( hf.printclientLoginScreen( errormsg,"DOT Shipping","tcmisclientreceiving.gif",intcmIsApplication ) );
		   out.flush();
		 }
        else
        {
		  String personnelid= session.getAttribute( "PERSONNELID" ) == null ? "" : session.getAttribute( "PERSONNELID" ).toString();
          session.setAttribute("ALLOWEDGROUPS", radian.web.HTMLHelpObj.createallowedusrgrps(db,personnelid,""));

		  obj=new clientdotshippingname( db );

		  if ( radian.web.HTMLHelpObj.hasaccessto(session,"Dotinfo"))
          {
            obj.setupdateStatus( true );
          }
          else
          {
            obj.setupdateStatus( false );
          }

		  obj.setclientdotStatus(true);
          obj.doResult( request,response,session );
        }
        loginresult = null;
      }
      catch ( Exception e )
      {
        e.printStackTrace();
      }
      finally
      {
        db.close();
        obj=null;
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



