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

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 1998
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 * 03-17-03
 * Set the path of the cookie
 * 08-18-03 - Added a http host fo the tcmIS.ico link so that the Icon shows up in the bookmark. Setting the domain by using the request.getServerName() method
 */

public class internalcgiHome  extends HttpServlet  implements SingleThreadModel
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
    RayTcmISDBModel db=null;
    PrintWriter cgiout=response.getWriter();
    response.setContentType( "text/html" );

    String User_Action="";
    HttpSession session=request.getSession();

    Cookie[] cookies=request.getCookies();
    String searchString=radian.web.HTMLHelpObj.getCookieValue( cookies,"internalcgiHome","" );

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
      loginresult=radian.web.loginhelpObj.logintopage( session,request,db,cgiout );
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
        cgiout.println( radian.web.HTMLHelpObj.printLoginScreen( errormsg,"tcmIS Main Menu" ) );
        cgiout.flush();
      }
      else
      {
        String personnelid=session.getAttribute( "PERSONNELID" ) == null ? "" : session.getAttribute( "PERSONNELID" ).toString();
        int cookiecount=0;
        if ( cookies != null )
        {
          for ( int i=0; i < cookies.length; i++ )
          {
            Cookie cookie=cookies[i];
            if ( cookie != null )
            {
              String cookiename=cookie.getName();
              if ( "token_1".equalsIgnoreCase( cookiename ) )
              {
                cookie.setValue( personnelid );
                cookie.setPath( "/" );
                String hostdomain =request.getServerName() == null ? "" : request.getServerName();
                cookie.setDomain( hostdomain );
                cookiecount++;
              }
            }
          }
        }

        if ( cookiecount == 0 )
        {
          //System.out.println( "Adding New Cookie" );
          Cookie arg1okie=new Cookie( "token_1",personnelid );
          arg1okie.setPath( "/" );
          String hostdomain =request.getServerName() == null ? "" : request.getServerName();
          arg1okie.setDomain( hostdomain );
          response.addCookie( arg1okie );
        }

        User_Action=request.getParameter( "UserAction" );
        if ( User_Action == null )
          User_Action="New";

        String CompanyID=session.getAttribute( "COMPANYID" ) == null ? "" : session.getAttribute( "COMPANYID" ).toString();
        String FullName=session.getAttribute( "FULLNAME" ) == null ? "" : session.getAttribute( "FULLNAME" ).toString();
        if ( personnelid.trim().length() == 0 )
        {
          User_Action="LOGOUT";
        }

        if ( "LOGOUT".equalsIgnoreCase( User_Action ) )
        {
          session.setAttribute( "loginState","challenge" );
 				  session.removeAttribute("personnelBean");
          session.invalidate();

          cgiout.println( "<HTML><HEAD>\n" );
          if ( CompanyID.length() > 1 & !"Radian".equalsIgnoreCase(CompanyID) )
          {
            cgiout.println( "<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; url=/tcmIS/cgihome/" + CompanyID + "\">\n" );
          }
          else
          {
            cgiout.println( "<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; url=/tcmIS/cgihome\">\n" );
          }
          cgiout.println( "<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n" );
          String httphost = radian.web.tcmisResourceLoader.gethttphosturl();
          cgiout.println( "<LINK REL=\"SHORTCUT ICON\" HREF=\""+httphost+"images/buttons/tcmIS.ico\"></LINK>\n" );
          cgiout.println( "<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n" );
          cgiout.println( "<TITLE>tcmIS Main Menu</TITLE>\n" );
          cgiout.println( "</HEAD>  \n" );
          cgiout.println( "<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">  \n" );
          cgiout.println( "<CENTER><img src=\"/images/tcmintro.gif\"  border=1 align=\"middle\"><P></P><BR>\n" );
          cgiout.println( "<FONT FACE=\"Arial\" size=\"5\" color=\"#000080\"><b>Logging Out ..</b></font><P></P><BR>\n" );
          cgiout.println( "</CENTER>\n" );
          cgiout.println( "</BODY></HTML>    \n" );
        }
        else
        {
          cgiout.println( "<HTML><HEAD>\n" );
          cgiout.println( "<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n" );
          String httphost = radian.web.tcmisResourceLoader.gethttphosturl();
          cgiout.println( "<LINK REL=\"SHORTCUT ICON\" HREF=\""+httphost+"images/buttons/tcmIS.ico\"></LINK>\n" );
          cgiout.println( "<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n" );
          cgiout.println( "<TITLE>tcmIS Main Menu</TITLE>\n" );
          cgiout.println( "<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"/stylesheets/global.css\"></LINK>\n" );
          cgiout.println( "</HEAD>  \n" );
          cgiout.println( "<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">  \n" );
          cgiout.println( radian.web.HTMLHelpObj.PrintTitleBarNolink( "tcmIS Main Menu" ) );
          cgiout.println( "<form method=\"POST\" name=\"form1\">\n" );
          cgiout.println( "<INPUT TYPE=\"hidden\" NAME=\"UserAction\" VALUE=\"LOGOUT\">\n" );
          cgiout.println( "<TABLE WIDTH=\"800\" BORDER=\"0\" CLASS=\"moveup\">\n" );
          cgiout.println( "<TR VALIGN=\"TOP\"><TD BGCOLOR=\"#E6E8FA\" HEIGHT=\"400\">\n" );
          cgiout.println( "<TABLE WIDTH=\"150\" BORDER=\"0\" CLASS=\"moveup\">\n" );
          cgiout.println( "<TR><TD>&nbsp;</TD></TR><TR><TD>&nbsp;</TD></TR><tr>\n" );
          cgiout.println( "<td width=\"10%\" align=\"CENTER\"><input CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" type=\"submit\" value=\"Log Out\" name=\"Login\"></td>\n" );
          cgiout.println( "</tr>\n" );

          cgiout.println( "<TR><TD>&nbsp;</TD></TR><TR><TD>&nbsp;</TD></TR><TR><TD CLASS=\"bluel\">\n" );
          cgiout.println( "<FONT SIZE=\"2\"></FONT></TD></TR></form>\n" );
          cgiout.println( "</TABLE>\n" );
          cgiout.println( "</TD>\n" );
          cgiout.println( "<TD WIDTH=\"650\" HEIGHT=\"400\" VALIGN=\"TOP\">\n" );
          cgiout.println( "<TABLE  BORDER=\"0\" CELLSPACING=1 CELLPADDING=0 WIDTH=\"100%\" CLASS=\"moveup\">\n" );
          String psuedoCommpany="";
          if ( "Radian".equalsIgnoreCase( CompanyID ) )
          {
            psuedoCommpany="Haas TCM";
          }
          cgiout.println( "<TR><TD WIDTH=\"550\" COLSPAN=\"3\"><FONT FACE=\"Arial\" size=\"3\">Logged in as: <B>" + FullName + "</B> for Company : <B>" +  psuedoCommpany + "</B></TD></TR><TR><TD>&nbsp;</TD></TR>\n" );
          cgiout.println( "</TABLE>\n" );
          cgiout.println( radian.web.HTMLHelpObj.printlinkstoClientHome() );
          cgiout.println( "<CENTER>\n" );
          cgiout.println( "<H2>\n" );
          cgiout.println( "<i>tcm</I>IS Important links:</H2></CENTER>\n" );
          cgiout.println( "<TABLE BORDER COLS=2 WIDTH=\"98%\" >\n" );
          cgiout.println( "<TR>\n" );
          cgiout.println( "<TD>&nbsp;<B>Material Request / Order Status / Purchase Tracking</B></TD>\n" );

          cgiout.println( "<TD><B>Description</B></TD>\n" );
          cgiout.println( "</TR>\n" );

          cgiout.println( "<TR>\n" );
          cgiout.println( "<TD><A HREF=\"/tcmIS/supply/edititemnotes.do\">Enter Buyer Item Information</A>&nbsp;</TD>\n" );

          cgiout.println( "<TD>Add information about an item to the database</TD>\n" );
          cgiout.println( "</TR>\n" );

          cgiout.println( "<TR>\n" );
          cgiout.println( "<TD><A HREF=\"/tcmIS/supply/edititemnotes.do\">Lookup Buyer Item Info</A>&nbsp;</TD>\n" );

          cgiout.println( "<TD>Look up existing information about an item and add more</TD>\n" );
          cgiout.println( "</TR>\n" );

          cgiout.println( "<TR>\n" );
          cgiout.println( "<TD><A HREF=\"/purch/price_quote.html\">Price Quote Page</A>&nbsp;</TD>\n" );

          cgiout.println( "<TD>List of Items Needing Price Quotes and Entry Form for New Quotes</TD>\n" );
          cgiout.println( "</TR>\n" );

          cgiout.println( "<TR>\n" );
          cgiout.println( "<TD><A HREF=\"/purch/price_quote_lookup.html\">Price Quote History</A>&nbsp;</TD>\n" );

          cgiout.println( "<TD>Price Quote History</TD>\n" );
          cgiout.println( "</TR>\n" );

          cgiout.println( "<TR>\n" );
          cgiout.println( "<TD><A HREF=\"/purch/req_price_quote.html\">Request Price Quote</A>&nbsp;</TD>\n" );

          cgiout.println( "<TD>Request a Price Quote</TD>\n" );
          cgiout.println( "</TR>\n" );

          cgiout.println( "<TR>\n" );
          cgiout.println( "<TD>Expediting</TD>\n" );

          cgiout.println( "<TD>Radian Expediting Page</TD>\n" );
          cgiout.println( "</TR>\n" );

          cgiout.println( "<TR>\n" );
          cgiout.println( "<TD>Look Up Expediting History</TD>\n" );

          cgiout.println( "<TD>Check historical progress of expediting POs</TD>\n" );
          cgiout.println( "</TR>\n" );

          cgiout.println( "<TR>\n" );
          cgiout.println( "<TD><A HREF=\"/radweb/po_receive.html\">List New Jackets</A>&nbsp;</TD>\n" );

          cgiout.println( "<TD>List of (New) Jackets to Receive</TD>\n" );
          cgiout.println( "</TR>\n" );

          cgiout.println( "<TR>\n" );
          cgiout.println( "<TD><A HREF=\"/radweb/old_jacket.html\">List Old Jackets</A>&nbsp;</TD>\n" );

          cgiout.println( "<TD>List of (Old) Jackets Already Received</TD>\n" );
          cgiout.println( "</TR>\n" );

          /*cgiout.println( "<TR>\n" );
          cgiout.println( "<TD><A HREF=\"/gen/material.html\">Look up Material IDs</A>&nbsp;</TD>\n" );

          cgiout.println( "<TD>Find Material ID for MSDS Lookup</TD>\n" );
          cgiout.println( "</TR>\n" );*/

          /*cgiout.println( "<TR>\n" );
          cgiout.println( "<TD><A HREF=\"/purch/msds_lookup.html\">Look up Available MSDS's</A>&nbsp;</TD>\n" );

          cgiout.println( "<TD>Search online system for available MSDS's</TD>\n" );
          cgiout.println( "</TR>\n" );*/

          cgiout.println( "<TR>\n" );
          cgiout.println( "<TD><A HREF=\"/radweb/rec_info.html\">Enter Receiving Info</A>&nbsp;</TD>\n" );

          cgiout.println( "<TD>Enter Receiving info for received items</TD>\n" );
          cgiout.println( "</TR>\n" );

          cgiout.println( "<TR>\n" );
          cgiout.println( "<TD><A HREF=\"/radweb/rec_lookup.html\">Lookup Receiving Info</A>&nbsp;</TD>\n" );

          cgiout.println( "<TD>Look up previously-entered Receiving info</TD>\n" );
          cgiout.println( "</TR>\n" );

          cgiout.println( "<TR>\n" );
          cgiout.println( "<TD><A HREF=\"/tcmIS/hub/minmaxchg\">READ-ONLY Minmax Info</A>&nbsp;</TD>\n" );

          cgiout.println( "<TD>Look up minmax order/stocking levels READ ONLY</TD>\n" );
          cgiout.println( "</TR>\n" );

          cgiout.println( "<TR>\n" );
          cgiout.println( "<TD><A HREF=\"/tcmIS/hub/minmaxchg\">Check/Change Minmax Info</A>&nbsp;</TD>\n" );

          cgiout.println( "<TD>Look up and/or change minmax order/stocking levels</TD>\n" );
          cgiout.println( "</TR>\n" );

          cgiout.println( "<TR>\n" );
          cgiout.println( "<TD><A HREF=\"/tcmIS/hub/forcebuymain.do\">Force a Buy Order</A>&nbsp;</TD>\n" );

          cgiout.println( "<TD>Force a buy order for the buy page</TD>\n" );
          cgiout.println( "</TR>\n" );

          /*cgiout.println( "<TR>\n" );
          cgiout.println( "<TD><A HREF=\"/cgi-bin/radadmin/new_item_list.cgi\">List New Item Requests</A>&nbsp;</TD>\n" );

          cgiout.println( "<TD>Show status of new chemical creation requests and link to creation page</TD>\n" );
          cgiout.println( "</TR>\n" );

          cgiout.println( "<TR>\n" );
          cgiout.println( "<TD><A HREF=\"/radadmin/newitem.html\">Create An Item</A>&nbsp;</TD>\n" );

          cgiout.println( "<TD>Create an item in JDE and the Catalog</TD>\n" );
          cgiout.println( "</TR>\n" );*/

		  cgiout.println( "<TR>\n" );
		  cgiout.println( "<TD><A HREF=\"/tcmIS/purchase/posupplier\">Supplier Search</A>&nbsp;</TD>\n" );

		  cgiout.println( "<TD>Search for suppliers defined in the database</TD>\n" );
		  cgiout.println( "</TR>\n" );

		  cgiout.println( "<TR>\n" );
		  cgiout.println( "<TD><A HREF=\"/tcmIS/supply/supplierreturnsmain.do\">Supplier Return</A>&nbsp;</TD>\n" );

		  cgiout.println( "<TD>Use the RMA process to return a receipt to a vendor</TD>\n" );
		  cgiout.println( "</TR>\n" );

          /*cgiout.println( "<TR>\n" );
          cgiout.println( "<TD></TD>\n" );

          cgiout.println( "<TD></TD>\n" );
          cgiout.println( "</TR>\n" );

          cgiout.println( "<TR>\n" );
          cgiout.println( "<TD><B>Start JDE</B></TD>\n" );

          cgiout.println( "<TD>&nbsp;</TD>\n" );
          cgiout.println( "</TR>\n" );

          cgiout.println( "<TR>\n" );
          cgiout.println( "<TD></TD>\n" );

          cgiout.println( "<TD></TD></TR><TR><TD><B>tmcIS</B></TD><TD>&nbsp;</TD></TR><TR><TD><A HREF=\"/tcmIS/install/rad\">tcmIS Demo Install</A>\n" );
          cgiout.println( "</TD><TD>Install the tcmIS Demo version.</TD></TR><!--<TR><TD><A HREF=\"/gdcmis\">GD Cmis</A></TD>\n" );

          cgiout.println( "<TD>General Dynamics CMIS system.</TD></TR>--></TABLE>&nbsp;\n" );

          cgiout.println( "</TD>\n" );
          cgiout.println( "</TR>\n" );*/
          cgiout.println( "</TABLE></BODY></HTML>    \n" );

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
        cgiout.close();
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