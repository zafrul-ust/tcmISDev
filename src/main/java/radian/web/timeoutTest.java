package radian.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;

public class timeoutTest extends HttpServlet
{
    //Initialize global variables

    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
    }

    //Process the HTTP Post request
    public void doGet(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
        IOException
    {
        doPost(request,response);
    }

    //Process the HTTP Get request
    public void doPost(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
        IOException
    {
      TcmISDBModel db = null;
      DBResultSet dbrs = null;
      ResultSet rs = null;

      PrintWriter out = new PrintWriter (response.getOutputStream());
      response.setContentType("text/html");
      String Session = "N";
      try
      {
      Session = request.getParameter("Session");
      }
      catch (Exception e)
      {
         e.printStackTrace();
         Session = "0";
      }

      StringBuffer MsgSB = new StringBuffer();
      System.out.println("Begin    :"+Session);

      if ("1".equalsIgnoreCase(Session))
      {
      System.out.println("Sleeping Now For a Long time :");
      try
      {
         // Sleep for 10 Minutes
         Thread.sleep(600000);
      }
      catch (java.lang.InterruptedException e)
      {
         System.out.println(e);
      }
      System.out.println("Sleeping Done :");

      MsgSB.append("<HTML><HEAD><TITLE>Report Generator</TITLE>\n");
      MsgSB.append("</HEAD>  \n");
      MsgSB.append("<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">  \n");
      MsgSB.append("<CENTER><img src=\"/images/tcmintro.gif\"  border=1 align=\"middle\"><P></P><BR>\n");
      MsgSB.append("<FONT FACE=\"Arial\" size=\"5\" color=\"#000080\"><b>Done With The Test</b></font><P></P><BR>\n");
      MsgSB.append("</CENTER></BODY></HTML>    \n");
      }
      else
      {
        MsgSB.append("<HTML><HEAD><TITLE>Report Generator</TITLE>\n");
        MsgSB.append("<SCRIPT TYPE=\"text/javascript\" LANGUAGE=\"JavaScript\">\n");
        MsgSB.append("<!-- \n");

        MsgSB.append("    var winHandle = null;\n");
        MsgSB.append( "function openwin2 ()\n");
        MsgSB.append(    "        {\n");
        MsgSB.append(    "    winHandle = window.open(\"https://www.tcmis.com/tcmIS/reports/Timer.html\", \"Timer1\",\n");
        MsgSB.append(    "        \"toolbar=no,location=no,directories=no,status=no\" +\n");
        MsgSB.append(    "        \",menubar=no,scrollbars=no,resizable=no,\" +\n");
        MsgSB.append(    "        \",width=200,height=150\");\n");
        MsgSB.append(    "  return winHandle; \n");
        MsgSB.append(    "        }\n");

        MsgSB.append("    function winClose(){    // close all open pop-up windows\n");
        MsgSB.append("      if ((winHandle != null) && !winHandle.closed) winHandle.close()\n");
        MsgSB.append("      }\n");

        MsgSB.append("function DoLoad()\n");
        MsgSB.append("{ \n");
        MsgSB.append("    var loc = \"\"; \n");
        MsgSB.append("openwin2(); \n");
        MsgSB.append("loc=loc + \"radian.web.timeoutTest?Session=1\";\n");
        MsgSB.append("window.location.replace(loc);\n");
        MsgSB.append("} \n");

        MsgSB.append("//-->     \n");
        MsgSB.append("</SCRIPT>\n");
        MsgSB.append("</HEAD>  \n");
        MsgSB.append("<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\" onload=\"DoLoad()\" onUnload=\"winClose()\">  \n");
        MsgSB.append("<CENTER><img src=\"/images/tcmintro.gif\"  border=1 align=\"middle\"><P></P><BR>\n");
        MsgSB.append("<FONT FACE=\"Arial\" size=\"5\" color=\"#000080\"><b>tcmIS Report Generator</b></font><P></P><BR>\n");
        MsgSB.append("<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b>This is a TEST. The Server is just keeping the connection Alive.</b></font><P></P><BR>\n");
        MsgSB.append("<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b>Please Wait</b></font><P></P><BR>\n");
        MsgSB.append("</CENTER></BODY></HTML>    \n");

        }
        out.println(MsgSB);
        out.close();
    }
}