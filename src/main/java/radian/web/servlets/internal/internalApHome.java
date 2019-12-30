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

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 1998
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 * 08-18-03 - Added a http host fo the tcmIS.ico link so that the Icon shows up in the bookmark.
 * 12-22-03 - Code Refactoring, moved vv_stuff to different class
 * 01-30-04 - Showing only Active Payment Terms for PO pages and showing all for AP pages
 */

public class internalApHome extends HttpServlet implements SingleThreadModel
{
  private RayTcmISDBModel db=null;

  public void init( ServletConfig config )  throws ServletException
  {
    super.init( config );
  }

  /**
   * HTTP POST handler.
   *
   * @param request               An object implementing the request context
   * @param response              An object implementing the response context
   * @exception ServletException  A wrapper for all exceptions thrown by the
   *      servlet.
   */

  public void doPost( HttpServletRequest request,HttpServletResponse response )  throws ServletException,IOException
  {
    PrintWriter apout=response.getWriter();
    HttpSession session=request.getSession();
    response.setContentType( "text/html" );

    try
    {
      db = new RayTcmISDBModel("Tcm_Ops","2",radian.web.HTMLHelpObj.getTcmISLocaleString(request));
      if ( db == null )
      {
        PrintWriter out2=new PrintWriter( response.getOutputStream() );
        out2.println( "Starting DB" );
        out2.println( "DB is null" );
        out2.close();
        return;
      }

      Hashtable loginresult=new Hashtable();
      loginresult=radian.web.loginhelpObj.logintopage( session,request,db,apout );
      String auth= ( String ) loginresult.get( "AUTH" );
      String errormsg= ( String ) loginresult.get( "ERROSMSG" );

      if ( auth == null )
      {
        auth="challenge";
      }
      if ( errormsg == null )
      {
        errormsg="";
      }

      if ( ! ( auth.equalsIgnoreCase( "authenticated" ) ) )
      {
        apout.println( radian.web.HTMLHelpObj.printapLoginScreen( errormsg,"Accounts Payable Home" ) );
        apout.flush();
      }
      else
      {
        String User_Action=request.getParameter( "UserAction" );
        if ( User_Action == null )
          User_Action="New";

        String personnelid= session.getAttribute( "PERSONNELID" ) == null ? "" : session.getAttribute( "PERSONNELID" ).toString();
        String CompanyID= session.getAttribute( "COMPANYID" ) == null ? "" : session.getAttribute( "COMPANYID" ).toString();
        String FullName= session.getAttribute( "FULLNAME" ) == null ? "" : session.getAttribute( "FULLNAME" ).toString();
        if (personnelid.trim().length() == 0) {User_Action = "LOGOUT";}

        if ( "LOGOUT".equalsIgnoreCase( User_Action ) )
        {
          session.setAttribute( "loginState","challenge" );
 				  session.removeAttribute("personnelBean");
          session.invalidate();

          apout.println( "<HTML><HEAD>\n" );
          if ( CompanyID.length() > 1 & !"Radian".equalsIgnoreCase(CompanyID) )
          {
            apout.println( "<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; url=/tcmIS/AccountsPayable/Home/" + CompanyID + "\">\n" );
          }
          else
          {
            apout.println( "<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; url=/tcmIS/AccountsPayable/Home\">\n" );
          }
          apout.println( "<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n" );
          String httphost = radian.web.tcmisResourceLoader.gethttphosturl();
          apout.println( "<LINK REL=\"SHORTCUT ICON\" HREF=\""+httphost+"images/buttons/tcmIS.ico\"></LINK>\n" );
          apout.println( "<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n" );
          apout.println( "<TITLE>Accounts Payable Home</TITLE>\n" );
          apout.println( "</HEAD>  \n" );
          apout.println( "<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">  \n" );
          apout.println( "<CENTER><img src=\"/images/tcmintro.gif\"  border=1 align=\"middle\"><P></P><BR>\n" );
          apout.println( "<FONT FACE=\"Arial\" size=\"5\" color=\"#000080\"><b>Logging Out ..</b></font><P></P><BR>\n" );
          apout.println( "</CENTER>\n" );
          apout.println( "</BODY></HTML>    \n" );
        }
        else
        {
				 apout.println("<html><head>\n");
				 apout.println("<meta http-equiv=\"Refresh\" content=\"0; url=/tcmIS/accountspayable/home.do\">\n");
				 apout.println("<meta http-equiv=\"Pragma\" content=\"no-cache\">\n");
				 String httphost = radian.web.tcmisResourceLoader.gethttphosturl();
				 apout.println("<link rel=\"SHORTCUT ICON\" href=\""+httphost+"images/buttons/tcmIS.ico\"></link>\n");
				 apout.println("<meta http-equiv=\"Expires\" content=\"-1\">\n");
				 apout.println("<title>Accounts Payable Home</title>\n");
				 apout.println("</head><body></body></html>\n");
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
      try {
        apout.close();
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