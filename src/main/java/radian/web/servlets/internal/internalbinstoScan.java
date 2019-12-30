package radian.web.servlets.internal;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Vector;
import java.util.Calendar;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;
import radian.web.servlets.share.binstoScan;
//import radian.web.barcode.ZPLBarCodeGenerator;
//import radian.web.barcode.hublabelPage;
/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2004
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 */

public class internalbinstoScan  extends HttpServlet implements SingleThreadModel
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
        binstoScan obj = null;
        //hublabelPage obj1 = null;
        String User_Action = "";
        HttpSession session = request.getSession();
        Hashtable loginresult = new Hashtable();
        Vector bindata = new Vector();

				Calendar calToday = Calendar.getInstance();
				//System.out.println("Calendar  "+calToday.getTime()+"");
				Calendar cal30Days = Calendar.getInstance();
				cal30Days.set(2006,11,1);
				//System.out.println("cal30Days  "+cal30Days.getTime()+"");

				String httphost=radian.web.tcmisResourceLoader.gethosturl();
				out.println( "<html><head>\n" );
				out.println( "<meta http-equiv=\"Pragma\" content=\"no-cache\">\n" );
				if (calToday.before(cal30Days))
				{
					 out.println( "<meta http-equiv=\"Refresh\" content=\"5; url=" + httphost + "tcmIS/hub/binstoscan.do" + "\">\n" );
				}
				out.println( "<link rel=\"SHORTCUT ICON\" href=\"" + httphost + "images/buttons/tcmIS.ico\"></link>\n" );
				out.println( "<meta http-equiv=\"Expires\" content=\"-1\">\n" );
				out.println( "<title>tcmIS Redirect Page</title>\n" );
				out.println( "<link rel=\"stylesheet\" type=\"text/css\" href=\"/stylesheets/global.css\"></link>\n" );
				out.println( "<script src=\"/clientscripts/redirect.js\" language=\"JavaScript\"></script>\n" );
				out.println( "</head>  \n" );
				out.println( "<body bgcolor=\"#FFFFFF\" text=\"#000000\">  \n" );
				out.println( radian.web.HTMLHelpObj.PrintTitleBarNolink( "tcmIS Redirect Page" ) );
				out.println( "<table width=\"800\" border=\"0\" class=\"moveup\">\n" );
				out.println( "<tr>" );
				out.println( "<td>\n" );
				if (calToday.before(cal30Days))
				{
					 out.println( "This page has moved, you will be redirected shortly.<br>\n" );
					 out.println( "<br>You can also get to the new page by clicking on the link below.<br>\n" );
					 out.println( "<br>Update your Bookmark/Favorites to the new address after you are redirected.<br>\n" );
				}
				else
				{
					 out.println( "This page has moved.<br>\n" );
					 out.println( "<br>You can get to the new page by clicking on the link below.<br>\n" );
					 out.println( "<br>Update your Bookmark/Favorites to the new address after clicking on the link below.<br>\n" );
				}
				out.println( "<p><a href=\"/tcmIS/hub/binstoscan.do\">" + httphost + "tcmIS/hub/binstoscan.do" + "</a></p>\n");
				out.println( "</td>\n" );
				out.println( "</tr></table>\n" );
				out.println( "</body></html>    \n" );
				out.close();

        /*try
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
            obj=new binstoScan( ( ServerResourceBundle )new InternalServerResourceBundle(),db );

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

           /* obj.doResult( request,response );
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
        }*/
      }

	  public void doGet( HttpServletRequest request,HttpServletResponse response ) throws ServletException,IOException
      {
        doPost( request,response );
      }
    }
