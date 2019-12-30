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

import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.web.servlets.internal.InternalServerResourceBundle;
import radian.web.servlets.share.Binlabels;
import radian.web.barcode.hublabelPage;
/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2004
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 */

public class internalBinlabels  extends HttpServlet implements SingleThreadModel
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
        Binlabels obj = null;
        hublabelPage obj1 = null;
        String User_Action = "";
        HttpSession session = request.getSession();
        Hashtable loginresult = new Hashtable();
        Vector bindata = new Vector();

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

          loginresult = radian.web.loginhelpObj.logintopage(session,request,db,out);
          String auth = (String)loginresult.get("AUTH");
          String errormsg = (String)loginresult.get("ERROSMSG");

          if (auth == null) {auth = "challenge";}
          if (errormsg == null) {errormsg = "";}

          if (! ( auth.equalsIgnoreCase( "authenticated" ) ) )
          {
            out.println( radian.web.HTMLHelpObj.printLoginScreen( errormsg,"Bin Labels" ) );
            out.flush();
          }
          else
          {
            obj=new Binlabels( ( ServerResourceBundle )new InternalServerResourceBundle(),db );

			/*if ( radian.web.HTMLHelpObj.hasaccessto( session,"Cabinet Label" ) )
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
                out.println( radian.web.HTMLHelpObj.printLoginScreenOptions( "You don't have access to this page.","Cabinet Labels",session ) );
                out.close();
                return;
              }
            }*/

            User_Action= request.getParameter("UserAction");
            if (User_Action == null)
              User_Action = "New";
            String generate_labels = request.getParameter("generate_labels");
            if (generate_labels == null)
              generate_labels ="No";
            String genaction = request.getParameter("genaction");
            if (genaction == null)
              genaction ="generatecablabels";

            if ( "yes".equalsIgnoreCase( generate_labels ) && "GenerateLabels".equalsIgnoreCase( User_Action ) )
            {
              bindata = ( Vector ) session.getAttribute( "HUB_LABEL_DATA" );

              String PaperSize = request.getParameter("paperSize");
              if (PaperSize == null)
                PaperSize = "31";

              obj1=new hublabelPage( db );
              String url="";
			  String HubNoforlabel=request.getParameter( "HubNoforlabel" );
			  if ( HubNoforlabel == null )
				HubNoforlabel="";
			  HubNoforlabel.trim();

			  String personnelid= session.getAttribute( "PERSONNELID" ) == null ? "" : session.getAttribute( "PERSONNELID" ).toString();
			  url=obj1.generateContainerLabel(bindata,personnelid,HubNoforlabel );

              out.println(radian.web.HTMLHelpObj.labelredirection(url));
              out.close();
            }
            else
            {
              obj.doResult( request,response );
            }
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

	  public void doGet( HttpServletRequest request,HttpServletResponse response ) throws ServletException,IOException
      {
        doPost( request,response );
      }
    }
