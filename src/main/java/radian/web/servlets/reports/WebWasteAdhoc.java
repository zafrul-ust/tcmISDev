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
import radian.tcmis.server7.client.bae.helpers.BAEServerResourceBundle;
import radian.tcmis.server7.client.drs.helpers.DRSServerResourceBundle;
import radian.tcmis.server7.client.lmco.helpers.LMCOServerResourceBundle;
import radian.tcmis.server7.client.miller.helpers.MillerServerResourceBundle;
import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;
import radian.tcmis.server7.client.ray.helpers.RayServerResourceBundle;
import radian.tcmis.server7.client.sd.helpers.SDServerResourceBundle;
import radian.tcmis.server7.client.seagate.helpers.SeagateServerResourceBundle;
import radian.tcmis.server7.client.swa.helpers.SWAServerResourceBundle;
import radian.tcmis.server7.client.utc.helpers.UTCServerResourceBundle;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.share.servlets.WasteAhocReport;

/**
 * <p>Title: All the code</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Haas TCM</p>
 * @author Nawaz Shaik
 * @version 1.0
 *
 * 07-02-03 - Code Clean up
 * 09-24-03 - Not setting the static variable ReportTable1 to null and closing the db only if it is not null
 * 11-18-03 - Changed language on final page
 */

public class WebWasteAdhoc extends HttpServlet
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
                  buildHTML(out,Session_ID,client,userID);
          }
          else if (Session.equalsIgnoreCase("1"))
          {
                  buildURL(out,Session_ID,client,userID);
          }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void buildURL(PrintWriter out,String Session_ID,String client, String userID)
    {
	  Hashtable Result = new Hashtable();
	  Hashtable ReportTable1 = new Hashtable();
	  TcmISDBModel db = null;
        if (client.equalsIgnoreCase("Ray"))
        {
            db = new RayTcmISDBModel("Ray","2");
            WasteAhocReport obj = new WasteAhocReport((ServerResourceBundle) new RayServerResourceBundle(),db);
            try{Result = obj.createReportNew(Session_ID,userID);}
            catch (Exception e)
            {
                e.printStackTrace();
                buildERROR(out,"");
            }finally {
              if (db != null) {db.close();}
            }
            obj = null;

        }
        else if (client.equalsIgnoreCase("SWA"))
        {
            db = new RayTcmISDBModel("SWA","2");
            WasteAhocReport obj = new WasteAhocReport((ServerResourceBundle) new SWAServerResourceBundle(),db);
            try{Result = obj.createReportNew(Session_ID,userID);}
            catch (Exception e)
            {
                e.printStackTrace();
                buildERROR(out,"");
            }finally {
              if (db != null) {db.close();}
            }
            obj = null;
        }
        else if (client.equalsIgnoreCase("LMCO"))
        {
            db = new RayTcmISDBModel("LMCO","2");
            WasteAhocReport obj = new WasteAhocReport((ServerResourceBundle) new LMCOServerResourceBundle(),db);
            try{Result = obj.createReportNew(Session_ID,userID);}
            catch (Exception e)
            {
                e.printStackTrace();
                buildERROR(out,"");
            }finally {
              if (db != null) {db.close();}
            }
            obj = null;

        }
        else if (client.equalsIgnoreCase("DRS"))
        {
            db = new RayTcmISDBModel("DRS","2");
            WasteAhocReport obj = new WasteAhocReport((ServerResourceBundle) new DRSServerResourceBundle(),db);
            try{Result = obj.createReportNew(Session_ID,userID);}
            catch (Exception e)
            {
                e.printStackTrace();
                buildERROR(out,"");
            }finally {
              if (db != null) {db.close();}
            }
            obj = null;

        }
        else if (client.equalsIgnoreCase("Seagate"))
        {
            db = new RayTcmISDBModel("Seagate");
            WasteAhocReport obj = new WasteAhocReport((ServerResourceBundle) new SeagateServerResourceBundle(),db);
            try{Result = obj.createReportNew(Session_ID,userID);}
            catch (Exception e)
            {
                e.printStackTrace();
                buildERROR(out,"");
            }finally {
              if (db != null) {db.close();}
            }
            obj = null;

        }
        else if (client.equalsIgnoreCase("SD"))
        {
            db = new RayTcmISDBModel("SD","2");
            WasteAhocReport obj = new WasteAhocReport((ServerResourceBundle) new SDServerResourceBundle(),db);
            try{Result = obj.createReportNew(Session_ID,userID);}
            catch (Exception e)
            {
                e.printStackTrace();
                buildERROR(out,"");
            }finally {
              if (db != null) {db.close();}
            }
            obj = null;

        }
        else if (client.equalsIgnoreCase("BAE"))
        {
            db = new RayTcmISDBModel("BAE","2");
            WasteAhocReport obj = new WasteAhocReport((ServerResourceBundle) new BAEServerResourceBundle(),db);
            try{Result = obj.createReportNew(Session_ID,userID);}
            catch (Exception e)
            {
                e.printStackTrace();
                buildERROR(out,"");
            }finally {
              if (db != null) {db.close();}
            }
            obj = null;

        }
        else if (client.equalsIgnoreCase("UTC"))
        {
            db = new RayTcmISDBModel("UTC","2");
            WasteAhocReport obj = new WasteAhocReport((ServerResourceBundle) new UTCServerResourceBundle(),db);
            try{Result = obj.createReportNew(Session_ID,userID);}
            catch (Exception e)
            {
                e.printStackTrace();
                buildERROR(out,"");
            }finally {
              if (db != null) {db.close();}
            }
            obj = null;

        }
        else if (client.equalsIgnoreCase("Miller"))
        {
            db = new RayTcmISDBModel("Miller","2");
            WasteAhocReport obj = new WasteAhocReport((ServerResourceBundle) new MillerServerResourceBundle(),db);
            try{Result = obj.createReportNew(Session_ID,userID);}
            catch (Exception e)
            {
                e.printStackTrace();
                buildERROR(out,"");
            }finally {
              if (db != null) {db.close();}
            }
            obj = null;
        }
        else if (client.equalsIgnoreCase("CAL"))
        {
            db = new RayTcmISDBModel("CAL","2");
            WasteAhocReport obj = new WasteAhocReport((ServerResourceBundle) new MillerServerResourceBundle(),db);
            try{Result = obj.createReportNew(Session_ID,userID);}
            catch (Exception e)
            {
                e.printStackTrace();
                buildERROR(out,"");
            }finally {
              if (db != null) {db.close();}
            }
            obj = null;
        }
        else if (client.equalsIgnoreCase("GM"))
        {
            db = new RayTcmISDBModel("GM","2");
            WasteAhocReport obj = new WasteAhocReport((ServerResourceBundle) new MillerServerResourceBundle(),db);
            try{Result = obj.createReportNew(Session_ID,userID);}
            catch (Exception e)
            {
                e.printStackTrace();
                buildERROR(out,"");
            }finally {
              if (db != null) {db.close();}
            }
            obj = null;
        }
        else if (client.equalsIgnoreCase("FEC"))
        {
            db = new RayTcmISDBModel("FEC","2");
            WasteAhocReport obj = new WasteAhocReport((ServerResourceBundle) new MillerServerResourceBundle(),db);
            try{Result = obj.createReportNew(Session_ID,userID);}
            catch (Exception e)
            {
                e.printStackTrace();
                buildERROR(out,"");
            }finally {
              if (db != null) {db.close();}
            }
            obj = null;
        }
        else if (client.equalsIgnoreCase("Boeing"))
        {
            db = new RayTcmISDBModel("Boeing","2");
            WasteAhocReport obj = new WasteAhocReport((ServerResourceBundle) new MillerServerResourceBundle(),db);
            try{Result = obj.createReportNew(Session_ID,userID);}
            catch (Exception e)
            {
                e.printStackTrace();
                buildERROR(out,"");
            }finally {
              if (db != null) {db.close();}
            }
            obj = null;
        }
        else if (client.equalsIgnoreCase("AeroEco"))
        {
            db = new RayTcmISDBModel("AeroEco","2");
            WasteAhocReport obj = new WasteAhocReport((ServerResourceBundle) new MillerServerResourceBundle(),db);
            try{Result = obj.createReportNew(Session_ID,userID);}
            catch (Exception e)
            {
                e.printStackTrace();
                buildERROR(out,"");
            }finally {
              if (db != null) {db.close();}
            }
            obj = null;
        }
        else if (client.equalsIgnoreCase("Dana"))
        {
            db = new RayTcmISDBModel("Dana","2");
            WasteAhocReport obj = new WasteAhocReport((ServerResourceBundle) new MillerServerResourceBundle(),db);
            try{Result = obj.createReportNew(Session_ID,userID);}
            catch (Exception e)
            {
                e.printStackTrace();
                buildERROR(out,"");
            }finally {
              if (db != null) {db.close();}
            }
            obj = null;
        }
        else if (client.equalsIgnoreCase("ABLaero"))
        {
            db = new RayTcmISDBModel("ABLaero","2");
            WasteAhocReport obj = new WasteAhocReport((ServerResourceBundle) new MillerServerResourceBundle(),db);
            try{Result = obj.createReportNew(Session_ID,userID);}
            catch (Exception e)
            {
                e.printStackTrace();
                buildERROR(out,"");
            }finally {
              if (db != null) {db.close();}
            }
            obj = null;
        }
        else if (client.equalsIgnoreCase("DOE"))
        {
            db = new RayTcmISDBModel("DOE","2");
            WasteAhocReport obj = new WasteAhocReport((ServerResourceBundle) new MillerServerResourceBundle(),db);
            try{Result = obj.createReportNew(Session_ID,userID);}
            catch (Exception e)
            {
                e.printStackTrace();
                buildERROR(out,"");
            }finally {
              if (db != null) {db.close();}
            }
            obj = null;
        }

        db = null;
        ShowResult(out,Result);
    }
    public void buildHTML(PrintWriter out,String sessionID,String client, String userID)
    {
        //StringBuffer MsgSB = new StringBuffer();

        out.println("<HTML><HEAD><TITLE>Report Generator</TITLE>\n");
        out.println("<SCRIPT TYPE=\"text/javascript\" LANGUAGE=\"JavaScript\">\n");
        out.println("<!-- \n");
        out.println("    var Timer = null\n");
        out.println("    var winHandlewa = null\n");
        out.println("    function winClose(){    // close all open pop-up windows\n");
        out.println("      if ((winHandlewa != null) && !winHandlewa.closed) winHandlewa.close()\n");
        out.println("      }\n");
        out.println("function openwin2 (str)\n");
        out.println("        {\n");
        out.println("    winHandlewa = window.open(\"/Timer.html\", \"Timer5\",\n");
        out.println("        \"toolbar=no,location=no,directories=no,status=no\" +\n");
        out.println("        \",menubar=no,scrollbars=no,resizable=no,\" +\n");
        out.println("        \",width=200,height=150\");\n");
        out.println("  return winHandlewa; \n");
        out.println("        }\n");
        out.println("function DoLoad()\n");
        out.println("{ \n");
        out.println("openwin2(); \n");
        out.println("window.location.replace(\"/tcmIS/servlet/radian.web.servlets.reports.WebWasteAdhoc?session=1&client="+client+"&sessionID="+sessionID+"&userID="+userID+"\");\n");
        out.println("} \n");
        out.println("//-->     \n");
        out.println("</SCRIPT>\n");
        out.println("</HEAD>  \n");
        out.println("<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\" onload=\"DoLoad()\" onUnload=\"winClose()\">  \n");
        out.println("<CENTER><img src=\"/images/tcmintro.gif\"  border=1 align=\"center\"><P></P><BR>\n");
        out.println("<FONT FACE=\"Arial\" size=\"5\" color=\"#000080\"><b>tcmIS Report Generator</b></font><P></P><BR>\n");
        out.println("<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b>Server Producing Report</b></font><P></P><BR>\n");
        out.println("<FONT FACE=\"Arial\" size=\"4\" color=\"#000080\"><b>Please Wait</b></font><P></P><BR>\n");
        out.println("</CENTER></BODY></HTML>    \n");
        //out.println(MsgSB);
        out.close();
    }

    public void buildERROR(PrintWriter out, String emsg)
    {
        //StringBuffer MsgSB = new StringBuffer();

        out.println("<HTML><HEAD>\n");
        out.println("</HEAD>  \n");
        out.println("<BODY BGCOLOR=\"#FFFFFF\" TEXT=\"#000000\">  \n");
        out.println("<CENTER><img src=\"/images/tcmintro.gif\"  border=1 align=\"center\"><P></P><BR>\n");
        out.println("<font face=Arial size=\"4\" color=\"#000080\"><b>"+emsg+"</b></font><P></P><BR>\n");
        out.println("</CENTER></body></HTML>    \n");
        //out.println(MsgSB);
        out.close();
    }

   public void ShowResult(PrintWriter out,Hashtable Result)
    {
       Boolean suc = (Boolean)Result.get("SUCCEED");
       String emsg = (String)Result.get("MSG");
       //StringBuffer MsgSB = new StringBuffer();

        if (suc.booleanValue())
        {
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
                //out.println(MsgSB);
                out.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            buildERROR(out,emsg);
        }
        Result = null;
    }

    public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException
    {
        doPost(request,response);
    }
}
