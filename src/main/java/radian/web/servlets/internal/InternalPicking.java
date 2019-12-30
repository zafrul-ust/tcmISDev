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
import radian.web.barcode.deliveryTicket;
import radian.web.servlets.share.pickingm;
/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2001
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 * 01-11-04 - Monitering Usage
 */

public class InternalPicking  extends HttpServlet implements SingleThreadModel
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
        pickingm obj = null;
        String User_Action = "";
        BolPicklistPage obj1 = null;
        HttpSession session = request.getSession();
		//Monitor mon=MonitorFactory.start("Picking QC");
		//String personnelid= session.getAttribute( "PERSONNELID" ) == null ? "" : session.getAttribute( "PERSONNELID" ).toString();
		//Monitor monuser=MonitorFactory.start("Picking QC by "+personnelid+"");

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
          out.println( radian.web.HTMLHelpObj.printLoginScreen( errormsg,"Picking QC" ) );
          out.flush();
        }
        else
        {
          // get the users properties
          String branch_plant=request.getParameter( "HubName" );
          if ( branch_plant == null )
            branch_plant="";
          session.setAttribute( "BRANCH_PLANT",branch_plant );

          obj=new pickingm( ( ServerResourceBundle )new InternalServerResourceBundle(),db );

		  if ( radian.web.HTMLHelpObj.hasaccessto( session,"Picking QC" ) )
		  {
			obj.setupdateStatus( true );
		  }
		  else
          {
            out.println( radian.web.HTMLHelpObj.printLoginScreenOptions( "You don't have access to this page.","Picking QC",session ) );
            out.close();
            return;

          }

            String boldetails = request.getParameter("boldetails");
            if (boldetails == null)
            boldetails = "No";

            String generate_picks = request.getParameter("generate_picks");
            if (generate_picks == null)
            generate_picks ="No";

            String generate_bols = request.getParameter("generate_bols");
            if (generate_bols == null)
            generate_bols ="No";

            User_Action= request.getParameter("UserAction");
            if (User_Action == null)
            User_Action = "New";

            String subuserAction = null;
            subuserAction = request.getParameter("SubUserAction");
            if (subuserAction == null)
                  subuserAction = "New";

            if ("generatebols".equalsIgnoreCase(User_Action) )
            {
                Vector printdata = (Vector)session.getAttribute("PRINTDATA");
                obj1 = new BolPicklistPage();
                out.println(obj1.GenerateBol(printdata,boldetails));
                out.close();
            }
            else if ("generateboxlabels".equalsIgnoreCase(User_Action) )
            {
                Vector printdata = (Vector)session.getAttribute("PRINTDATA");
                BoxLabelPage boxlabelobj = new BoxLabelPage();
                out.println(boxlabelobj.GenerateBoxLabel(printdata));
                out.close();
            }
            else if ("generatedelvtkt".equalsIgnoreCase(User_Action) )
            {
                Vector printdata = (Vector)session.getAttribute("PICKQC_DATA");
                deliveryTicket boxlabelobj = new deliveryTicket();
                out.println(boxlabelobj.gendeliveryticket(printdata));
                out.close();
            }
            else
            {
               obj.doResult(request,response,session);
            }
        }
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
}

