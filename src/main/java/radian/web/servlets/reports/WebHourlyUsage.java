package radian.web.servlets.reports;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;
import radian.tcmis.server7.client.swa.helpers.SWAServerResourceBundle;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.share.servlets.AdHocHourlyVocReport;

/**
 * <p>Title: All the code</p>
 * <p>Description: </p>
 * Description: This is the web servlet used to open a browser when a Active report is
 * requested. This just redirects the data to another server6 share servlet which does the actual
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Haas TCM</p>
 * @author Nawaz Shaik
 * @version 1.0
 *
 * 07-02-03 - Code Clean up
 * 09-24-03 - Not setting the static variable ReportTable1 to null and closing the db only if it is not null
 * 11-18-03 - Changed language on final page
 */

public class WebHourlyUsage extends HttpServlet
{
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException
    {
        PrintWriter out = new PrintWriter (response.getOutputStream());
        response.setContentType("text/html");

        try
        {
          String Session_ID = BothHelpObjs.makeBlankFromNull((String)request.getParameter("sessionID"));
          String Session = request.getParameter("session");
          String client = BothHelpObjs.makeBlankFromNull((String)request.getParameter("client"));
          String userID = BothHelpObjs.makeBlankFromNull((String)request.getParameter("userID"));

            if (Session == null)
            {
                buildHTML(out,client,Session_ID,userID);
            }
            else if (Session.equalsIgnoreCase("1"))
            {
                buildURL(out,client,Session_ID,userID);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void buildURL(PrintWriter out,String client,String Session_ID, String userID)
    {
	    Hashtable Result = new Hashtable();
        if (client.equalsIgnoreCase("SWA"))
        {
            TcmISDBModel db = new RayTcmISDBModel("SWA");
            AdHocHourlyVocReport obj = new AdHocHourlyVocReport((ServerResourceBundle) new SWAServerResourceBundle(),db);
            try{Result = obj.createReportNew(Session_ID,userID);}
            catch (Exception e)
            {
                e.printStackTrace();
                buildERROR(out,"");
            }finally
			{
              if (db != null) {db.close();}
            }
            obj = null;
            db = null;
        }
        ShowResult(out,Result);
    }
    public void buildHTML(PrintWriter out,String client,String sessionID, String userID)
    {
        //StringBuffer MsgSB = new StringBuffer();

        out.println("<HTML><HEAD><TITLE>Report Generator</TITLE>\n");
        out.println("<SCRIPT TYPE=\"text/javascript\" LANGUAGE=\"JavaScript\">\n");
        out.println("<!-- \n");
        out.println("    function winClose(){    // close all open pop-up windows\n");
        out.println("      if ((winHandle != null) && !winHandle.closed) winHandle.close()\n");
        out.println("      }\n");
        out.println( "function openwin2 (str)\n");
        out.println(    "        {\n");
        out.println(    "    winHandle = window.open(\"/Timer.html\", \"Timer1\",\n");
        out.println(    "        \"toolbar=no,location=no,directories=no,status=no\" +\n");
        out.println(    "        \",menubar=no,scrollbars=no,resizable=no,\" +\n");
        out.println(    "        \",width=200,height=150\");\n");
        out.println(    "  return winHandle; \n");
        out.println(    "        }\n");
        out.println("function DoLoad()\n");
        out.println("{ \n");
        out.println("    var loc = \"\"; \n");
        out.println("openwin2(); \n");
        out.println("loc=loc + \"/tcmIS/servlet/radian.web.servlets.reports.WebHourlyUsage?session=1&client="+client+"&sessionID="+sessionID+"&userID="+userID+"\"\n");
        out.println("window.location.replace(loc);\n");
        out.println("} \n");
        out.println("//-->     \n");
        out.println("</SCRIPT>\n");
        out.println("</HEAD>  \n");
        out.println("<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\" onload=\"DoLoad()\" onUnload=\"winClose()\">  \n");
        out.println("<CENTER><img src=\"/images/tcmintro.gif\"  border=1 align=\"middle\"><P></P><BR>\n");
        out.println("<FONT FACE=\"Arial\" size=\"5\" color=\"#000080\"><b>tcmIS Report Generator</b></font><P></P><BR>\n");
        out.println("<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b>Server Producing Report</b></font><P></P><BR>\n");
        out.println("<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b>Please Wait</b></font><P></P><BR>\n");
        out.println("</CENTER></BODY></HTML>    \n");
        //out.println(MsgSB);
        out.close();
    }

    public void buildERROR(PrintWriter out, String emsg)
    {
        //StringBuffer MsgEr = new StringBuffer();

        out.println("<HTML><HEAD>\n");
        out.println("</HEAD>  \n");
        out.println("<BODY BGCOLOR=#FFFFFF TEXT=#000000>  \n");
        out.println("<CENTER><img src=\"/images/tcmintro.gif\"  border=1 align=\"center\"><P></P><BR>\n");
        out.println("<font face=Arial size=\"4\" color=\"#000080\"><b>"+emsg+"</b></font><P></P><BR>\n");
        out.println("</CENTER></body></HTML>    \n");
        //out.println(MsgEr);
        out.close();
    }

    public void ShowResult(PrintWriter out,Hashtable Result)
    {
       Boolean suc = (Boolean)Result.get("SUCCEED");
       String emsg = (String)Result.get("MSG");
       //StringBuffer MsgEr = new StringBuffer();

        if (suc.booleanValue())
        {
           boolean sucess = true;
           try
            {
                String url = (String)Result.get("URL");
                out.println("<HTML><HEAD>\n");
                out.println("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; url="+url+"\">\n");
                out.println("</HEAD>  \n");
                out.println("<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">  \n");
                out.println("<CENTER><img src=\"/images/tcmintro.gif\"  border=1 align=\"center\"><P></P><BR>\n");
                out.println("<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\">\n");
                out.println("<BR>    \n");
                out.println("<B>Report Finished </B><BR><BR>\n");
                out.println("<B>Retrieving Data.</B>\n");
                out.println("</FONT>\n");
                out.println("</CENTER></BODY></HTML>    \n");
                //out.println(MsgEr);
                out.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            boolean sucess = false;
            buildERROR(out,emsg);
        }
        Result = null;
    }

    public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException
    {
        doPost(request,response);
    }
}
