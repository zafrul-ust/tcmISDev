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
import radian.tcmis.server7.share.dbObjs.Password;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 1998
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 * 08-18-03 - Added a http host fo the tcmIS.ico link so that the Icon shows up in the bookmark.
 */

public class internalChangePassword extends HttpServlet implements SingleThreadModel
{
  public void init( ServletConfig config )
     throws ServletException
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

  public void doPost( HttpServletRequest request,HttpServletResponse response )
     throws ServletException,IOException
  {
    PrintWriter out=response.getWriter();
    response.setContentType( "text/html" );
    RayTcmISDBModel db=null;
    String User_Action="";
    HttpSession session=request.getSession();

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
      loginresult=radian.web.loginhelpObj.logintopage( session,request,db,out );
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
        out.println( radian.web.HTMLHelpObj.printpoLoginScreen( errormsg,"Supply Home" ) );
        out.flush();
      }
      else
      {

        User_Action=request.getParameter( "UserAction" );
        if ( User_Action == null )
          User_Action="New";

        String whichHome1=request.getParameter( "whichHome" );
        if ( whichHome1 == null )
          whichHome1="";

        String CompanyID=session.getAttribute( "COMPANYID" ) == null ? "" : session.getAttribute( "COMPANYID" ).toString();
        //String FullName=session.getAttribute( "FULLNAME" ) == null ? "" : session.getAttribute( "FULLNAME" ).toString();
        String loginId=session.getAttribute( "loginId" ) == null ? "" : session.getAttribute( "loginId" ).toString();
        //System.out.println( "Which Home Page  " + whichHome1 + "  User_Action   " + User_Action );

        //StringBuffer MsgSB=new StringBuffer();
        if ( "update".equalsIgnoreCase( User_Action ) )
        {
          String oldpasswd= ( String ) request.getParameter( "oldpasswd" );
          String newpasswd1= ( String ) request.getParameter( "newpasswd1" );
          String newpasswd2= ( String ) request.getParameter( "newpasswd2" );
          boolean changedPassword=false;

          try
          {
            if ( oldpasswd.trim().length() > 0 && newpasswd1.trim().length() > 0 && newpasswd2.trim().length() > 0 &&
                 newpasswd1.equalsIgnoreCase( newpasswd2 ) )
            {
              Password pw=new Password( db,loginId,oldpasswd,CompanyID );
              if ( pw.isAuthenticatedWeb() )
              {
                pw.changePassword( newpasswd1 );
                changedPassword=true;
              }
            }
          }
          catch ( Exception e )
          {
            e.printStackTrace();
            changedPassword=false;
          }
          out.println( "<HTML><HEAD>\n" );

          if ( CompanyID.length() > 1 )
          {
            out.println( "<META HTTP-EQUIV=\"Refresh\" CONTENT=\"3; url=/tcmIS/" + whichHome1 + "/Home/" + CompanyID + "\">\n" );
          }
          else
          {
            out.println( "<META HTTP-EQUIV=\"Refresh\" CONTENT=\"3; url=/tcmIS/" + whichHome1 + "/Home\">\n" );
          }

          out.println( "<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n" );
          String httphost = radian.web.tcmisResourceLoader.gethttphosturl();
          out.println( "<LINK REL=\"SHORTCUT ICON\" HREF=\""+httphost+"images/buttons/tcmIS.ico\"></LINK>\n" );
          out.println( "<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n" );
          out.println( "<TITLE>Change Password</TITLE>\n" );
          out.println( "</HEAD>  \n" );
          out.println( "<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">  \n" );
          out.println( "<CENTER><img src=\"/images/tcmintro.gif\"  border=1 align=\"middle\"><P></P><BR>\n" );
          if ( changedPassword )
          {
            out.println( "<FONT FACE=\"Arial\" size=\"3\" color=\"#000080\"><b>Changed The Password Successfully.<BR>Please Use the New Password The Next Time you Login.</b></font><P></P><BR>\n" );
          }
          else
          {
            out.println( "<FONT FACE=\"Arial\" size=\"3\" color=\"#000080\"><b>Did not Change Password Please Try Again</b></font><P></P><BR>\n" );
          }
          out.println( "</CENTER>\n" );
          out.println( "</BODY></HTML>    \n" );
        }
        else
        {
          out.println( "<HTML><HEAD>\n" );
          out.println( "<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=iso-8859-1\">\n" );
          String httphost = radian.web.tcmisResourceLoader.gethttphosturl();
          out.println( "<LINK REL=\"SHORTCUT ICON\" HREF=\""+httphost+"images/buttons/tcmIS.ico\"></LINK>\n" );
          out.println( "<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n" );
          out.println( "<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n" );
          out.println( "<TITLE>Change Password</TITLE>\n" );
          out.println( "<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"/stylesheets/global.css\"></LINK>\n" );
          out.println( "<SCRIPT SRC=\"/clientscripts/FormChek.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n" );
          out.println( "</HEAD>  \n" );
          out.println( "<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">  \n" );
          out.println( "<TABLE BORDER=0 WIDTH=100% CLASS=\"moveupmore\">\n" );
          out.println( "<TR VALIGN=\"TOP\">\n" );
          out.println( "<TD WIDTH=\"200\">\n" );
          out.println( "<img src=\"/images/tcmtitlegif.gif\" border=0 align=\"left\">\n" );
          out.println( "</TD>\n" );
          out.println( "<TD ALIGN=\"right\">\n" );
          out.println( "<img src=\"/images/tcmistcmis32.gif\" border=0 align=\"right\">\n" );
          out.println( "</TD>\n" );
          out.println( "</TR>\n" );
          out.println( "</Table>\n" );
          out.println( "<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"0\" WIDTH=\"100%\" CLASS=\"moveup\">\n" );
          out.println( "<TR><TD WIDTH=\"70%\" ALIGN=\"LEFT\" HEIGHT=\"22\" CLASS=\"heading\">\n" );
          out.println( "Change Password" );
          out.println( "</TD>\n" );
          out.println( "<TD WIDTH=\"30%\" ALIGN=\"RIGHT\" HEIGHT=\"22\" CLASS=\"headingr\">\n" );
          out.println( "<A HREF=\"/tcmIS/" + whichHome1 + "/Home\" STYLE=\"color:#FFFFFF\">Home</A>\n" );
          out.println( "</TD>\n" );
          out.println( "</TR>\n" );
          out.println( "</TABLE>\n" );
          out.println( "<form method=\"POST\" name=\"form1\">\n" );
          out.println( "<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\"update\">\n" );
          out.println( "<INPUT TYPE=\"hidden\" NAME=\"whichHome\" VALUE=\"" + whichHome1 + "\">\n" );
          out.println( "<TABLE WIDTH=\"400\" BORDER=\"0\" CLASS=\"moveup\">\n" );
          out.println( "<TR VALIGN=\"TOP\"><TD BGCOLOR=\"#E6E8FA\" HEIGHT=\"400\">\n" );
          out.println( "<TABLE WIDTH=\"400\" BORDER=\"0\" CLASS=\"moveup\">\n" );
          out.println( "<tr>\n" );
          out.println( "<td width=\"35%\" CLASS=\"announce\" ALIGN=\"RIGHT\" ><B>Logon Id : </B></td>\n" );
          out.println( "<td width=\"85%\" ALIGN=\"LEFT\" ><input CLASS=\"HEADER\" type=\"hidden\" name=\"loginId\" value=\"\" size=\"10\">" + loginId + "</td>\n" );
          out.println( "</tr><tr>\n" );
          out.println( "<td width=\"35%\" CLASS=\"announce\" ALIGN=\"RIGHT\" >Old Password : </td>\n" );
          out.println( "<td width=\"85%\" ALIGN=\"LEFT\" ><input CLASS=\"HEADER\" type=\"password\" name=\"oldpasswd\" value=\"\" size=\"10\"></td>\n" );
          out.println( "</tr><tr>\n" );
          out.println( "<td width=\"35%\" CLASS=\"announce\" ALIGN=\"RIGHT\" >New Password : </td>\n" );
          out.println( "<td width=\"85%\" ALIGN=\"LEFT\" ><input CLASS=\"HEADER\" type=\"password\" name=\"newpasswd1\" value=\"\" size=\"10\"></td>\n" );
          out.println( "</tr><tr>\n" );
          out.println( "<td width=\"35%\" CLASS=\"announce\" ALIGN=\"RIGHT\" >New Password : </td>\n" );
          out.println( "<td width=\"85%\" ALIGN=\"LEFT\" ><input CLASS=\"HEADER\" type=\"password\" name=\"newpasswd2\" value=\"\" size=\"10\"></td>\n" );
          out.println( "</tr><tr>\n" );
          out.println( "<td width=\"35%\" align=\"right\"><input CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" type=\"submit\" onclick=\"return checkPasswords(this)\"  value=\"Change\" name=\"Login\"></td>\n" );
          out.println( "<td width=\"85%\" align=\"left\"><input type=\"reset\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" value=\"Reset\" name=\"Reset\"></td>\n" );
          out.println( "</tr>\n" );
          out.println( "<TR><TD>&nbsp;</TD></TR><TR><TD>&nbsp;</TD></TR><TR><TD COLSPAN=\"2\" CLASS=\"bluel\"><FONT SIZE=\"2\"></FONT></TD></TR></form></TABLE></TD></TR></TABLE>\n" );
          out.println( "</body></html>" );
          out.println( "</TD>\n" );
          out.println( "</TR></TABLE>\n" );
          out.println( "</BODY></HTML>    \n" );
        }
        //out.println( MsgSB );
        out.close();
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
}