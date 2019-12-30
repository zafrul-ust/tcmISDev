package radian.web.servlets.internal;

/**
 * Title:        TcmIS
 * Description:  Hub Receiving Page
 * Copyright:    Copyright (c) 2001
 * Company:      URS Corp
 * @author
 * @version 1.0
 * 08-18-03 - Added a http host fo the tcmIS.ico link so that the Icon shows up in the bookmark.
 * 02-10-03 - Made this page client bassed so that there is a new class for each client
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Invoice extends HttpServlet implements SingleThreadModel
{
  public void init( ServletConfig config )
     throws ServletException
  {
    super.init( config );
  }

  public void doPost( HttpServletRequest request,HttpServletResponse response )
     throws ServletException,IOException
  {
    PrintWriter out=response.getWriter();
    response.setContentType( "text/html" );

    try
    {
      String tmpCompany="ray";
      StringTokenizer st=new StringTokenizer( request.getRequestURI(),"/" );
      String lastToken="";
      while ( st.hasMoreTokens() )
      {
        String currentToken=st.nextToken();
        if ( "tcmIS".equalsIgnoreCase( lastToken ) )
        {
          tmpCompany=currentToken;
          break;
        }
        lastToken=currentToken;
      }
      //System.out.println( "------- company: " + tmpCompany );
	  String redpage = radian.web.tcmisResourceLoader.gethosturl() + "tcmIS/";

      if ( "ray".equalsIgnoreCase( tmpCompany ) )
      {
        redpage +=  "ray/costreport";
      }
      else if ( "drs".equalsIgnoreCase( tmpCompany ) )
      {
        redpage +=  "drs/costreport";
      }
      else if ( "bae".equalsIgnoreCase( tmpCompany ) )
      {
        redpage +=  "bae/costreport";
      }
      else if ( "seagate".equalsIgnoreCase( tmpCompany ) )
      {
        redpage +=  "seagate/costreport";
      }
      else if ( "utc".equalsIgnoreCase( tmpCompany ) )
      {
        redpage +=  "utc/costreport";
      }
      else if ( "swa".equalsIgnoreCase( tmpCompany ) )
      {
        redpage +=  "swa/costreport";
      }
      else if ( "sd".equalsIgnoreCase( tmpCompany ) )
      {
        redpage +=  "sd/costreport";
      }
      else if ( "lmco".equalsIgnoreCase( tmpCompany ) )
      {
        redpage +=  "lmco/costreport";
      }
      else if ( "miller".equalsIgnoreCase( tmpCompany ) )
      {
        redpage +=  "miller/costreport";
      }

	//StringBuffer MsgSB=new StringBuffer();
	out.println( "<HTML><HEAD>\n" );
	out.println( "<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n" );
	String httphost=radian.web.tcmisResourceLoader.gethttphosturl();
	out.println( "<LINK REL=\"SHORTCUT ICON\" HREF=\"" + httphost + "images/buttons/tcmIS.ico\"></LINK>\n" );
	out.println( "<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n" );
	out.println( "<TITLE>tcmIS Redirect Page</TITLE>\n" );
	out.println( "<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"/stylesheets/global.css\"></LINK>\n" );
	out.println( "<SCRIPT SRC=\"/clientscripts/redirect.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n" );
	out.println( "</HEAD>  \n" );
	out.println( "<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">  \n" );
	out.println( radian.web.HTMLHelpObj.PrintTitleBarNolink( "tcmIS Redirect Page" ) );
	out.println( "<form method=\"POST\" name=\"form1\">\n" );
	out.println( "<TABLE WIDTH=\"800\" BORDER=\"0\" CLASS=\"moveup\">\n" );
	out.println( "<TR>" );
	out.println( "<TD>\n" );


	out.println( "This address will be removed from service soon.<BR><BR>\n" );
	out.println( "The bookmark you followed will be replaced with the Web Page address shown below.<BR><BR>\n" );
	out.println( "<B>You can bookmark the new address after clicking on the link below.</B><BR>\n" );
	out.println( "<P><A href=\""+redpage+"\">"+redpage+"</A></P>\n");
	out.println( "<BR><BR><BR><BR>Thanks and please contact tech support if you need help doing this.\n");
	out.println( "</TD>\n" );
	out.println( "</TR></TABLE>\n" );
	out.println( "</BODY></HTML>    \n" );

	//out.println( MsgSB );
	out.close();

    }
    catch ( Exception e )
    {
      e.printStackTrace();
      return;
    }
    finally
    {
      try {
        out.close();
      }
      catch(Exception e) {
        //ignore
      }
      //Runtime.getRuntime().gc();
    }
  } //end of method

  public void doGet( HttpServletRequest request,HttpServletResponse response )
     throws ServletException,IOException
  {
    doPost( request,response );
  } //end of method
} //end of class