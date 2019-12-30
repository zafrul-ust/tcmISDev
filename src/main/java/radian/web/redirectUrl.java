package radian.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Title:        TcmIS
 * Description:  Total Chemical Mgmt
 * Copyright:    Copyright (c) 2003
 * Company:      URS Corporation
 * @author       Nawaz Shaik
 * @version
 *
 */

public class redirectUrl  extends HttpServlet implements SingleThreadModel
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
      PrintWriter out=response.getWriter();
      response.setContentType( "text/html" );
      HttpSession session=request.getSession();

      try
      {
        String redpage="";
        redpage=request.getParameter( "page" );
        if ( redpage == null )
          redpage="";

        System.out.println("Page WHere it came from   "+redpage);

        StringBuffer MsgSB=new StringBuffer();
        MsgSB.append( "<HTML><HEAD>\n" );
        MsgSB.append( "<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n" );
        String httphost=radian.web.tcmisResourceLoader.gethttphosturl();
        MsgSB.append( "<LINK REL=\"SHORTCUT ICON\" HREF=\"" + httphost + "images/buttons/tcmIS.ico\"></LINK>\n" );
        MsgSB.append( "<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n" );
        MsgSB.append( "<TITLE>tcmIS Redirect Page</TITLE>\n" );
        MsgSB.append( "<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"/stylesheets/global.css\"></LINK>\n" );
        MsgSB.append( "<SCRIPT SRC=\"/clientscripts/redirect.js\" LANGUAGE=\"JavaScript\"></SCRIPT>\n" );
        MsgSB.append( "</HEAD>  \n" );
        MsgSB.append( "<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">  \n" );
        MsgSB.append( radian.web.HTMLHelpObj.PrintTitleBarNolink( "tcmIS Redirect Page" ) );
        MsgSB.append( "<form method=\"POST\" name=\"form1\">\n" );
        MsgSB.append( "<TABLE WIDTH=\"800\" BORDER=\"0\" CLASS=\"moveup\">\n" );
        MsgSB.append( "<TR>" );
        MsgSB.append( "<TD>\n" );

        redpage = "https://www.tcmis.com" +redpage;
        MsgSB.append( "The <B>www.ross.radian.com</B> address will be removed from service soon.<BR>\n" );
        MsgSB.append( "We are in the process of moving everybody over to the new domain <B>www.tcmis.com</B>.<BR><BR>\n" );
        //MsgSB.append( "The bookmark you followed was for <B>www.ross.radian.com</B>. The new Web Page address is shown below.<BR><BR>\n" );
        MsgSB.append( "<BR><BR><BR><B>You can bookmark the new address after clicking on the link below.</B><BR>\n" );
        MsgSB.append( "<P><A href=\""+redpage+"\">"+redpage+"</A></P>\n");
//        MsgSB.append( "<BR>If you are using Internet Explorer you can bookmark it by clicking below<BR><BR><INPUT TYPE=\"BUTTON\" ID=\"genbutton1\" VALUE=\"Click to Bookmark\" NAME=\"SearchButton\" onclick= \"javascript:addBookmark('Please Type Tittle Here','"+redpage+"')\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\">&nbsp;\n" );
        MsgSB.append( "<BR><BR><BR><BR>Thanks and please contact tech support if you need help doing this.\n");
        MsgSB.append( "</TD>\n" );
        MsgSB.append( "</TR></TABLE>\n" );
        MsgSB.append( "</BODY></HTML>    \n" );

        out.println( MsgSB );
        out.close();

      }
      catch ( Exception e )
      {
        e.printStackTrace();
        return;
      }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
     throws ServletException, IOException
    {
        doPost(request,response);
    }
}

