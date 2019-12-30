package radian.web.servlets.internal;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;
import java.util.Hashtable;

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
import radian.web.servlets.share.soundsLikePurchaseOrder;
/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2001
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 *
 * Check Done
 *
 * 11-13-02
 * setting updateStatus to be used in the Obj
 * 01-11-04 - Monitering Usage
 */

public class internalsoundsLikePurchaseOrder  extends HttpServlet implements SingleThreadModel
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
        PrintWriter out = new PrintWriter ( new OutputStreamWriter(response.getOutputStream (), "UTF8" ) );
		    response.setContentType( "text/html; charset=UTF-8" );
        RayTcmISDBModel db = null;
        soundsLikePurchaseOrder obj = null;
        HttpSession session = request.getSession();
		//Monitor mon=MonitorFactory.start("Recal PO");
		//String personnelid= session.getAttribute( "PERSONNELID" ) == null ? "" : session.getAttribute( "PERSONNELID" ).toString();
		//Monitor monuser=MonitorFactory.start("Recal PO by "+personnelid+"");

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
          out.println( radian.web.HTMLHelpObj.printpoLoginScreen( errormsg,"Purchase Order" ) );
          out.flush();
        }
        else
        {
          obj=new soundsLikePurchaseOrder( ( ServerResourceBundle )new InternalServerResourceBundle(),db );

					if (radian.web.HTMLHelpObj.hasaccessto(session, "specOverRide")) {
					 obj.setSpecOverRide(true);
					}
					else {
					 obj.setSpecOverRide(false);
					}
	
		if("getRate".equalsIgnoreCase(request.getParameter( "Action" )))
		{
			out.write(obj.getConverionRate(request.getParameter( "currentCurrency"),request.getParameter( "thePO")).toPlainString());
			out.close();			
		}
		else
		{
			  if ( radian.web.HTMLHelpObj.hasaccessto( session,"Purchasing" ) )
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
	              out.println( radian.web.HTMLHelpObj.printpoLoginScreenOptions( "You don't have access to this page.","Purchase Order",session ) );
	              out.close();
	              return;
	            }
	          }
	
	          obj.doResult( request,response,out );
		}
        }
      }
      catch ( Exception e )
      {
        e.printStackTrace();
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

    public void doGet( HttpServletRequest request,HttpServletResponse response )
       throws ServletException,IOException
    {
      doPost( request,response );
    }
    //
  }