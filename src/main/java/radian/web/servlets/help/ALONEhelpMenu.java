// FrontEnd Plus GUI for JAD
// DeCompiled : helpMenu.class

package radian.web.servlets.help;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;
import radian.tcmis.server7.share.dbObjs.FaqMenu;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

/**
 * <p>Title: HElp Object</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Haas TCM</p>
 * @author Nawaz Shaik
 * @version 1.0
 * 03-06-03 Changed the faq1.gif path
 */
public class ALONEhelpMenu extends HttpServlet
{

    ServerResourceBundle bundle;
    TcmISDBModel db;
    String msg;
    String solution;
    String sol_count;
    String faq;
    String stru0;
    String stru1;
    String stru2;
    String stru3;
    String stru4;
    String stru5;
    String stru6;
    String stru7;
    String stru8;
    String stru9;
    String jscript;
    String SessionID;
    String QuestionID;
    String html_FilePath = "/tcmIS/help/new/";
    String Help_Servlet_Path;

    boolean gettingHTML;
    boolean checkingFAQ;
    boolean gettingFAQ;
    boolean gettingAnswer;

    public ALONEhelpMenu(ServerResourceBundle b)
    {
        bundle = b;
        db = null;
        msg = "";
        solution = "";
        sol_count = null;
        faq = "off";
        stru0 = "c";
        stru1 = "c";
        stru2 = "c";
        stru3 = "c";
        stru4 = "c";
        stru5 = "c";
        stru6 = "c";
        stru7 = "c";
        stru8 = "c";
        stru9 = "c";

        jscript = "<SCRIPT LANGUAGE = \"JavaScript\">\n<!--\n        if (document.images) {\n            img1on = new Image();   img1on.src = \"/tcmIS/help/new/faq2.gif\";\n            img1off = new Image();  img1off.src = \"/tcmIS/help/new/faq1.gif\";\n         }\n    function imgOn(imgName) {\n            if (document.images) {\n                document[imgName].src = eval(imgName + \"on.src\");\n            }\n    }\n    function imgOff(imgName) {\n            if (document.images) {\n                document[imgName].src = eval(imgName + \"off.src\");\n            }\n    }\n// -->\n</script>";
        gettingHTML = false;
        checkingFAQ = false;
        gettingFAQ = false;
        gettingAnswer = false;
        bundle = b;

        SessionID = "";
        QuestionID= "";
        Help_Servlet_Path = "";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
        String client = bundle.get("DB_CLIENT_NAME").toString();
        db = new RayTcmISDBModel(client);

        String First_Time = "N";
        try {First_Time = request.getParameter("Session");} catch (Exception ee) { }

        if ("1".equalsIgnoreCase(First_Time))
        {
         BuildFramePage(response);
        }
        sol_count = request.getParameter("s");
        if(sol_count == null)
        {
            checkingFAQ = true;
            sol_count = "N";
        }
        faq = request.getParameter("faq");
        if(faq == null)
            faq = "off";
        stru0 = request.getParameter("stru0");
        if(stru0 == null)
            stru0 = "c";
        stru1 = request.getParameter("stru1");
        if(stru1 == null)
            stru1 = "c";
        stru2 = request.getParameter("stru2");
        if(stru2 == null)
            stru2 = "c";
        stru3 = request.getParameter("stru3");
        if(stru3 == null)
            stru3 = "c";
        stru4 = request.getParameter("stru4");
        if(stru4 == null)
            stru4 = "c";
        stru5 = request.getParameter("stru5");
        if(stru5 == null)
            stru5 = "c";
        stru6 = request.getParameter("stru6");
        if(stru6 == null)
            stru6 = "c";
        stru7 = request.getParameter("stru7");
        if(stru7 == null)
            stru7 = "c";
        stru8 = request.getParameter("stru8");
        if(stru8 == null)
            stru8 = "c";
        stru9 = request.getParameter("stru9");
        if(stru9 == null)
            stru9 = "c";

        //System.out.println(stru9);

        SessionID = request.getParameter("SessionID");
        if(SessionID == null)
            SessionID = "0";

        QuestionID= request.getParameter("qid");

        if (SessionID.equalsIgnoreCase("20"))
        {
        gettingAnswer = true;
        }

        //html_FilePath = "";
        //html_FilePath = bundle.getString("HELP_HTML_PATH");
        Help_Servlet_Path = bundle.getString("HELP_SERVLET_PATH");

        //System.out.println(html_FilePath);

        response.setContentType("text/html");

        if(faq.equalsIgnoreCase("on"))
        {
            gettingFAQ = true;
            gettingHTML = false;
        }
        else if(faq.equalsIgnoreCase("off"))
        {
            gettingHTML = true;
        }

        printOutput(response);
    }

    // right now all the clients show the Raytheon FAQ's Change the was the database connects to change that - Nawaz 17-08-01
    public void buildFAQ(PrintWriter out)
    {
        //Nawaz 10-01-01
        /*String client = bundle.get("DB_CLIENT_NAME").toString();
        db = new RayTcmISDBModel(client);

        if (client.equalsIgnoreCase("Raytheon"))
        {
            db = new RayTcmISDBModel("Ray");
        }
        else if (client.equalsIgnoreCase("Southwest Airlines"))
        {
            db = new SWATcmISDBModel("SWA");
        }
        else if (client.equalsIgnoreCase("Lockheed"))
        {
            db = new LMCOTcmISDBModel("LMCO");
        }
        else if (client.equalsIgnoreCase("DRS"))
        {
            db = new DRSTcmISDBModel("DRS");
        }*/

        try
        {
            buildData(FaqMenu.getFaq(db, "2"), out, "2");
        }
        catch(Exception exception)
        {
        }
         finally
        {
            //db.close();
        }
        msg = "<html><body bgcolor=ffffff><center><img src=\""+html_FilePath+"faqh.gif\" border=0>\n<table width=80%>\n" + solution + "\n</table>\n</body>\n</html>";
        out.println(msg);
    }

    public void checkFAQ()
    {
        //Nawaz 10-01-01
        /*String client = bundle.get("DB_CLIENT_NAME").toString();
        db = new RayTcmISDBModel(client);

        if (client.equalsIgnoreCase("Raytheon"))
        {
            db = new RayTcmISDBModel("Ray");
        }
        else if (client.equalsIgnoreCase("Southwest Airlines"))
        {
            db = new SWATcmISDBModel("SWA");
        }
        else if (client.equalsIgnoreCase("Lockheed"))
        {
            db = new LMCOTcmISDBModel("LMCO");
        }
        else if (client.equalsIgnoreCase("DRS"))
        {
            db = new DRSTcmISDBModel("DRS");
        }*/

        try
        {
            checkData(FaqMenu.getFaq(db, "1"), "1");
        }
        catch(Exception exception)
        {
        }
         finally
        {
            //db.close();
        }
    }

    public void buildHTML(PrintWriter out)
    {
        String WWWhomeDirectory = bundle.getString("WEBS_HOME_WWWS");
        StringBuffer msg = new StringBuffer();
        //Nawaz 10-01-01
        String client = bundle.get("DB_CLIENT").toString();
        String installfile = "";

        if (client.equalsIgnoreCase("Raytheon"))
        {
            installfile = "raytcmis.html";
        }
        else if (client.equalsIgnoreCase("Southwest Airlines"))
        {
            installfile = "swatcmis.html";
        }
        else if (client.equalsIgnoreCase("Lockheed"))
        {
            installfile = "lmcotcmis.html";
        }
        else if (client.equalsIgnoreCase("DRS"))
        {
            installfile = "drstcmis.html";
        }
        else if (client.equalsIgnoreCase("USGOV"))
        {
            installfile = "usgovtcmis.html";
        }


      msg.append("<html>\n<head>\n");
      msg.append("<style type=\"text/css\">\n");
      msg.append("<!--\n        a:link {text-decoration:none}\n");
      msg.append("a:visited {text-decoration:none}\n");
      msg.append("a:active {text-decoration:none}\n     -->\n");
      msg.append("</style>\n</head>\n");
      msg.append("<body bgcolor=\"#ffffff\" text=\"#3366CC\" vlink =\"#00ff40\" onLoad=\"window.status='Help';return true\">\n");
      msg.append(jscript);
      msg.append("<img src=\""+WWWhomeDirectory+"/images/buttons/menu/help.gif\" border=0 alt=\"tcmis -> HELP Viewer\"><BR>\n");
      msg.append("<font face=\"Arial\" size=1><b>\n");

      /*if(stru0.equalsIgnoreCase("o"))
      {
      msg.append("<a href=\""+Help_Servlet_Path+"?");
      msg.append("stru9=" + stru9 + "&stru0=c&stru1=" + stru1 + "&stru2=" + stru2 + "&stru3=" + stru3 + "&stru4=" + stru4 + "");
      msg.append("&stru5=" + stru5 + "&stru6=" + stru6 + "&stru7=" + stru7 + "&stru8=" + stru8 + "&s=" + sol_count + "\"");
      msg.append("onMouseOver=\"window.status='Help';return true\" onMouseOut=\"window.status='Help -> Installation';return true\">");
      msg.append("<img src=\""+WWWhomeDirectory+"/images/buttons/menu/neg.gif\" border=0></a>");
      msg.append("&nbsp;\n" + "<font color=red><B>NOTE:</b> </font>");
      msg.append("<font color=000000>To view screenshots, right-click the image and select 'View Image'. ");
      msg.append("To return, right-click again and select 'Back'. To Print, click inside the frame you wish to print from and press 'Ctrl+P'.");
      msg.append("</font><BR><BR>\n");
      }
      else
      {
      msg.append("<a href=\""+Help_Servlet_Path+"?");
      msg.append("stru9=" + stru9 + "&stru0=o&stru1=" + stru1 + "&stru2=" + stru2 + "&stru3=" + stru3 + "&stru4=" + stru4 + "&stru5=" + stru5 + "");
      msg.append("&stru6=" + stru6 + "&stru7=" + stru7 + "&stru8=" + stru8 + "&s=" + sol_count + "\" onMouseOver=\"window.status='Help';return true\"");
      msg.append("onMouseOut=\"window.status='Help -> Installation';return true\"><img src=\""+WWWhomeDirectory+"/images/buttons/menu/pos.gif\"");
      msg.append("border=0></a>&nbsp;\n" + "<font color=red><B>NOTE:</b></font><BR>\n");
      }*/

      /*if(stru1.equalsIgnoreCase("o"))
      {
        msg.append("<a href=\""+Help_Servlet_Path+"?");
        msg.append("stru9=" + stru9 + "&stru0=" + stru0 + "&stru1=c&stru2=" + stru2 + "&stru3=" + stru3 + "&stru4=" + stru4 + "&stru5=" + stru5 + "");
        msg.append("&stru6=" + stru6 + "&stru7=" + stru7 + "&stru8=" + stru8 + "&s=" + sol_count + "\" ");
        msg.append("onMouseOver=\"window.status='Help';return true\" onMouseOut=\"window.status='Help -> Installation';");
        msg.append("return true\"><img src=\""+WWWhomeDirectory+"/images/buttons/menu/neg.gif\" border=0></a>");
        msg.append("&nbsp;Installation<BR>\n" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n");
        msg.append("<img src=\""+WWWhomeDirectory+"/images/buttons/menu/dot.gif\" border=0>&nbsp;\n");
        msg.append("<a href=\""+html_FilePath+installfile+"\" target=\"content\" onMouseOver=\"window.status='Help -> Installation -> tcmIS';return true\"");
        msg.append("onMouseOut=\"window.status='Help -> Installation';return true\"><font color=\"#990000\">tcmIS</font></a>");
        msg.append("<BR>\n" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n");
        msg.append("<img src=\""+WWWhomeDirectory+"/images/buttons/menu/dot.gif\" border=0>&nbsp;\n");
        msg.append("<a href=\""+html_FilePath+"adobe.html\" target=\"content\" onMouseOver=\"window.status='Help -> Installation -> Adobe Acrobat';");
        msg.append("return true\" onMouseOut=\"window.status='Help -> Installation';return true\">");
        msg.append("<font color=\"#990000\">Adobe Acrobat</font></a><BR>\n");
      }
      else
      {
      msg.append("<a href=\""+Help_Servlet_Path+"?");
      msg.append("stru9=" + stru9 + "&stru0=" + stru0 + "&stru1=o&stru2=" + stru2 + "&stru3=" + stru3 + "&stru4=" + stru4 + "&stru5=" + stru5 + "");
      msg.append("&stru6=" + stru6 + "&stru7=" + stru7 + "&stru8=" + stru8 + "&s=" + sol_count + "\" ");
      msg.append("onMouseOver=\"window.status='Help -> Installation';return true\" onMouseOut=\"window.status='Help';return true\">");
      msg.append("<img src=\""+WWWhomeDirectory+"/images/buttons/menu/pos.gif\" border=0></a>&nbsp;Installation<BR>\n");
      }*/

        if(stru2.equalsIgnoreCase("o"))
        {
            msg.append("<a href=\""+Help_Servlet_Path+"?");
            msg.append("stru9=" + stru9 + "&stru0=" + stru0 + "&stru1=" + stru1 + "&stru2=c&stru3=" + stru3 + "&stru4=" + stru4 + "");
            msg.append("&stru5=" + stru5 + "&stru6=" + stru6 + "&stru7=" + stru7 + "&stru8=" + stru8 + "");
            msg.append("&s=" + sol_count + "\" onMouseOver=\"window.status='Help';");
            msg.append("return true\" onMouseOut=\"window.status='Help -> tcmIS Operation';return true\">");
            msg.append("<img src=\""+WWWhomeDirectory+"/images/buttons/menu/neg.gif\" border=0></a>");
            msg.append("&nbsp;tcmIS Operation<BR>\n");

            /*msg.append("&nbsp;<a href=\""+html_FilePath+"tcmisintro.html\" target=\"content\" ");
            msg.append("onMouseOver=\"window.status='Help -> tcmIS Operation ->';");
            msg.append("return true\" onMouseOut=\"window.status='Help -> tcmIS Operation ->';");
            msg.append("return true\"><font color=\"#990000\">&nbsp;tcmIS Operation</font></a><BR>\n");*/

            if(stru3.equalsIgnoreCase("o"))
            {
             msg.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href=\""+Help_Servlet_Path+"?");
             msg.append("stru9=" + stru9 + "&stru0=" + stru0 + "&stru1=" + stru1 + "&stru2=" + stru2 + "&stru3=c&stru4=" + stru4 + "");
             msg.append("&stru5=" + stru5 + "&stru6=" + stru6 + "&stru7=" + stru7 + "&stru8=" + stru8 + "&s=" + sol_count + "\"");
             msg.append("onMouseOver=\"window.status='Help -> tcmIS Operation';return true\" ");
             msg.append("onMouseOut=\"window.status='Help -> tcmIS Operation -> Chemical & Gas';return true\">");
             msg.append("<img src=\""+WWWhomeDirectory+"/images/buttons/menu/neg.gif\" border=0></a>&nbsp;\n" );

            msg.append("&nbsp;<a href=\""+html_FilePath+"chemicalintro.html\" target=\"content\" ");
            msg.append("onMouseOver=\"window.status='Help -> tcmIS Operation -> Chemical & Gas';");
            msg.append("return true\" onMouseOut=\"window.status='Help -> tcmIS Operation -> Chemical & Gas';");
            msg.append("return true\"><font color=\"#990000\">&nbsp;Chemical & Gas</font></a><BR>\n");

            msg.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
            //msg.append("Chemical & Gas<BR>\n" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
             msg.append("<img src=\""+WWWhomeDirectory+"/images/buttons/menu/dot.gif\" border=0>&nbsp;");
             msg.append("<a href=\""+html_FilePath+"chem_catalog.html\" target=\"content\" ");
             msg.append("onMouseOver=\"window.status='Help -> tcmIS Operation -> Chemical & Gas -> Chemical Catalog';");
             msg.append("return true\" onMouseOut=\"window.status='Help -> tcmIS Operation -> Chemical & Gas';");
             msg.append("return true\"><font color=\"#990000\">&nbsp;Chemical Catalog</font></a><BR>\n");

             msg.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
             msg.append("<img src=\""+WWWhomeDirectory+"/images/buttons/menu/dot.gif\" border=0>&nbsp;");
             msg.append("<a href=\""+html_FilePath+"chem_order.html\" target=\"content\" ");
             msg.append("onMouseOver=\"window.status='Help -> tcmIS Operation -> Chemical & Gas -> Placing a Chemical or Gas Order';");
             msg.append("return true\" onMouseOut=\"window.status='Help -> tcmIS Operation -> Chemical & Gas';");
             msg.append("return true\"><font color=\"#990000\">&nbsp;Placing an order</font></a><BR>\n");

             msg.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
             msg.append("<img src=\""+WWWhomeDirectory+"/images/buttons/menu/dot.gif\" border=0>&nbsp;\n");
             msg.append("<a href=\""+html_FilePath+"track_chem.html\" target=\"content\" ");
             msg.append("onMouseOver=\"window.status='Help -> tcmIS Operation -> Chemical & Gas -> Tracking a Chemical or Gas Order';return true\"");
             msg.append(" onMouseOut=\"window.status='Help -> tcmIS Operation -> Chemical & Gas';return true\">");
             msg.append("<font color=\"#990000\">Tracking an Order</font></a><BR>\n");

             msg.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
             msg.append("<img src=\""+WWWhomeDirectory+"/images/buttons/menu/dot.gif\" border=0>&nbsp;\n");
             msg.append("<a href=\""+html_FilePath+"check.html\" target=\"content\" ");
             msg.append("onMouseOver=\"window.status='Help -> tcmIS Operation -> Chemical & Gas -> Checking Inventory Status';return true\" ");
             msg.append("onMouseOut=\"window.status='Help -> tcmIS Operation -> Chemical & Gas';return true\">");
             msg.append("<font color=\"#990000\">Checking Inventory Status</font></a><BR>\n");

             //Nawaz 01-06-02
             msg.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
             msg.append("<img src=\""+WWWhomeDirectory+"/images/buttons/menu/dot.gif\" border=0>&nbsp;");
             msg.append("<a href=\""+html_FilePath+"newChem.html\" target=\"content\" ");
             msg.append("onMouseOver=\"window.status='Help -> tcmIS Operation -> Chemical & Gas -> Placing a New Chem Request';return true\"");
             msg.append("onMouseOut=\"window.status='Help -> tcmIS Operation -> Chemical & Gas';return true\">");
             msg.append("<font color=\"#990000\">&nbsp;Adding to the Catalog</font></a><BR>\n");

             msg.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
             msg.append("<img src=\""+WWWhomeDirectory+"/images/buttons/menu/dot.gif\" border=0>&nbsp;");
             msg.append("<a href=\""+html_FilePath+"order_ee.html\" target=\"content\" ");
             msg.append("onMouseOver=\"window.status='Help -> tcmIS Operation -> Chemical & Gas -> Placing an Engineering Evaluation Order';return true\"");
             msg.append("onMouseOut=\"window.status='Help -> tcmIS Operation -> Chemical & Gas';return true\">");
             msg.append("<font color=\"#990000\">&nbsp;Engineering Eval</font></a><BR>\n");

             /*msg.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
             msg.append("<img src=\""+WWWhomeDirectory+"/images/buttons/menu/dot.gif\" border=0>&nbsp;\n");
             msg.append("<a href=\""+html_FilePath+"add.html\" target=\"content\" ");
             msg.append("onMouseOver=\"window.status='Help -> tcmIS Operation -> Chemical & Gas -> Adding a new Chemical or Gas to the Catalog';return true\" ");
             msg.append("onMouseOut=\"window.status='Help -> tcmIS Operation -> Chemical & Gas';return true\">");
             msg.append("<font color=\"#990000\">Adding to the Catalog</font></a><BR>\n");*/

             /*msg.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
             msg.append("<img src=\""+WWWhomeDirectory+"/images/buttons/menu/dot.gif\" border=0>");
             msg.append("&nbsp;<a href=\""+html_FilePath+"stop_query.html\" target=\"content\" ");
             msg.append("onMouseOver=\"window.status='Help -> tcmIS Operation -> Chemical & Gas -> Stop Query';return true\" ");
             msg.append("onMouseOut=\"window.status='Help -> tcmIS Operation -> Chemical & Gas';return true\">");
             msg.append("<font color=\"#990000\">&nbsp;Stop Query</font></a><BR>\n");*/


            }
            else
            if(stru3.equalsIgnoreCase("c"))
                msg.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href=\""+Help_Servlet_Path+"?stru9=" + stru9 + "&stru0=" + stru0 + "&stru1=" + stru1 + "&stru2=" + stru2 + "&stru3=o&stru4=" + stru4 + "&stru5=" + stru5 + "&stru6=" + stru6 + "&stru7=" + stru7 + "&stru8=" + stru8 + "&s=" + sol_count + "\" onMouseOver=\"window.status='Help -> tcmIS Operation -> Chemical & Gas';return true\" onMouseOut=\"window.status='Help -> tcmIS Operation';return true\"><img src=\""+WWWhomeDirectory+"/images/buttons/menu/pos.gif\" border=0></a>&nbsp;\n" + "Chemical & Gas<BR>\n");
            if(stru4.equalsIgnoreCase("o"))
            {
                msg.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href=\""+Help_Servlet_Path+"?stru9=" + stru9 + "&stru0=" + stru0 + "&stru1=" + stru1 + "&stru2=" + stru2 + "&stru3=" + stru3 + "&stru4=c&stru5=" + stru5 + "&stru6=" + stru6 + "&stru7=" + stru7 + "&stru8=" + stru8 + "&s=" + sol_count + "\" onMouseOver=\"window.status='Help -> tcmIS Operation -> Waste';return true\" onMouseOut=\"window.status='Help -> tcmIS Operation';return true\"><img src=\""+WWWhomeDirectory+"/images/buttons/menu/neg.gif\" border=0></a>&nbsp;\n");

                msg.append("&nbsp;<a href=\""+html_FilePath+"wasteintro.html\" target=\"content\" ");
                msg.append("onMouseOver=\"window.status='Help -> tcmIS Operation -> Waste Intro';");
                msg.append("return true\" onMouseOut=\"window.status='Help -> tcmIS Operation -> Waste';");
                msg.append("return true\"><font color=\"#990000\">&nbsp;Waste</font></a><BR>\n");

                //msg.append("Waste<BR>\n");

                msg.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src=\""+WWWhomeDirectory+"/images/buttons/menu/dot.gif\" border=0>&nbsp;\n<a href=\""+html_FilePath+"waste_request.html\" target=\"content\" onMouseOver=\"window.status='Help -> tcmIS Operation -> Waste -> Waste Catalog';return true\" onMouseOut=\"window.status='Help -> tcmIS Operation -> Waste';return true\"><font color=\"#990000\">Waste Catalog</font></a><BR>\n");
                msg.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src=\""+WWWhomeDirectory+"/images/buttons/menu/dot.gif\" border=0>&nbsp;\n<a href=\""+html_FilePath+"initiate_request.html\" target=\"content\" onMouseOver=\"window.status='Help -> tcmIS Operation -> Waste -> Initiating Internal Pickup Requests';return true\" onMouseOut=\"window.status='Help -> tcmIS Operation -> Waste';return true\"><font color=\"#990000\">Initiating a Request</font></a><BR>\n");
                msg.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src=\""+WWWhomeDirectory+"/images/buttons/menu/dot.gif\" border=0>&nbsp;\n<a href=\""+html_FilePath+"waste_transfer.html\" target=\"content\" onMouseOver=\"window.status='Help -> tcmIS Operation -> Waste -> Waste Transfer Mgt';return true\" onMouseOut=\"window.status='Help -> tcmIS Operation -> Waste';return true\"><font color=\"#990000\">Waste Transfer Mgt</font></a><BR>\n");
                msg.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src=\""+WWWhomeDirectory+"/images/buttons/menu/dot.gif\" border=0>&nbsp;\n<a href=\""+html_FilePath+"waste_storage.html\" target=\"content\" onMouseOver=\"window.status='Help -> tcmIS Operation -> Waste -> Waste Storage Management';return true\" onMouseOut=\"window.status='Help -> tcmIS Operation -> Waste';return true\"><font color=\"#990000\">Storage Management</font></a><BR>\n");
                msg.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src=\""+WWWhomeDirectory+"/images/buttons/menu/dot.gif\" border=0>&nbsp;\n<a href=\""+html_FilePath+"waste_shipping.html\" target=\"content\" onMouseOver=\"window.status='Help -> tcmIS Operation -> Waste -> Waste Shipping';return true\" onMouseOut=\"window.status='Help -> tcmIS Operation -> Waste';return true\"><font color=\"#990000\">Waste Shipping</font></a><BR>\n");
                msg.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src=\""+WWWhomeDirectory+"/images/buttons/menu/dot.gif\" border=0>&nbsp;\n<a href=\""+html_FilePath+"waste_order.html\" target=\"content\" onMouseOver=\"window.status='Help -> tcmIS Operation -> Waste -> Waste Order';return true\" onMouseOut=\"window.status='Help -> tcmIS Operation -> Waste';return true\"><font color=\"#990000\">Waste Order</font></a><BR>\n");            }
            else
            {
                msg.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href=\""+Help_Servlet_Path+"?stru9=" + stru9 + "&stru0=" + stru0 + "&stru1=" + stru1 + "&stru2=" + stru2 + "&stru3=" + stru3 + "&stru4=o&stru5=" + stru5 + "&stru6=" + stru6 + "&stru7=" + stru7 + "&stru8=" + stru8 + "&s=" + sol_count + "\" onMouseOver=\"window.status='Help -> tcmIS Operation -> Waste';return true\" onMouseOut=\"window.status='Help -> tcmIS Operation';return true\"><img src=\""+WWWhomeDirectory+"/images/buttons/menu/pos.gif\" border=0></a>&nbsp;\n");
                msg.append("Waste<BR>\n");
            }
            if(stru5.equalsIgnoreCase("o"))
            {
                //Nawaz 10-01-01
                msg.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href=\""+Help_Servlet_Path+"?stru9=" + stru9 + "&stru0=" + stru0 + "&stru1=" + stru1 + "&stru2=" + stru2 + "&stru3=" + stru3 + "&stru4=" + stru4 + "&stru5=c&stru6=" + stru6 + "&stru7=" + stru7 + "&stru8=" + stru8 + "&s=" + sol_count + "\" onMouseOver=\"window.status='Help -> tcmIS Operation';return true\" onMouseOut=\"window.status='Help -> tcmIS Operation -> Reports';return true\"><img src=\""+WWWhomeDirectory+"/images/buttons/menu/neg.gif\" border=0></a>&nbsp;\n");
                //msg.append("Printing<BR>\n");

                msg.append("<a href=\""+html_FilePath+"reportsintro.html\" target=\"content\" ");
                msg.append("onMouseOver=\"window.status='Help -> tcmIS Operation -> Reports';");
                msg.append("return true\" onMouseOut=\"window.status='Help -> tcmIS Operation -> Reports';");
                msg.append("return true\"><font color=\"#990000\">&nbsp; Reports</font></a><BR>\n");

                /*msg.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src=\""+WWWhomeDirectory+"/images/buttons/menu/dot.gif\" border=0>&nbsp;\n" + "<a href=\""+html_FilePath+"general.html\" target=\"content\" onMouseOver=\"window.status='Help -> tcmIS Operation -> Print -> General Reports';return true\" onMouseOut=\"window.status='Help -> tcmIS Operation -> Print';return true\"><font color=\"#990000\">\n");
                msg.append("General Reports</font></a><BR>\n");*/

                //General Reports
                msg.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src=\""+WWWhomeDirectory+"/images/buttons/menu/dot.gif\" border=0>&nbsp;\n" + "<a href=\""+html_FilePath+"forusage.html\" target=\"content\" onMouseOver=\"window.status='Help -> tcmIS Operation -> Reports -> Formatted Usage Reports';return true\" onMouseOut=\"window.status='Help -> tcmIS Operation -> Reports';return true\"><font color=\"#990000\">\n");
                msg.append("Formatted Usage</font></a><BR>\n");

                msg.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src=\""+WWWhomeDirectory+"/images/buttons/menu/dot.gif\" border=0>&nbsp;\n" + "<a href=\""+html_FilePath+"forvocusage.html\" target=\"content\" onMouseOver=\"window.status='Help -> tcmIS Operation -> Reports -> Formatted VOC Usage Reports';return true\" onMouseOut=\"window.status='Help -> tcmIS Operation -> Reports';return true\"><font color=\"#990000\">\n");
                msg.append("Formatted VOC Usage</font></a><BR>\n");

                msg.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src=\""+WWWhomeDirectory+"/images/buttons/menu/dot.gif\" border=0>&nbsp;\n" + "<a href=\""+html_FilePath+"formsds.html\" target=\"content\" onMouseOver=\"window.status='Help -> tcmIS Operation -> Reports -> Formatted MSDS Reports';return true\" onMouseOut=\"window.status='Help -> tcmIS Operation -> Reports';return true\"><font color=\"#990000\">\n");
                msg.append("Formatted MSDS</font></a><BR>\n");

                //Ad-Hoc Reports
                msg.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src=\""+WWWhomeDirectory+"/images/buttons/menu/dot.gif\" border=0>&nbsp;\n" + "<a href=\""+html_FilePath+"adhocusage.html\" target=\"content\" onMouseOver=\"window.status='Help -> tcmIS Operation -> Reports -> Ad-Hoc Usage Reports';return true\" onMouseOut=\"window.status='Help -> tcmIS Operation -> Reports';return true\"><font color=\"#990000\">\n");
                msg.append("Ad-Hoc Usage</font></a><BR>\n");

                msg.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src=\""+WWWhomeDirectory+"/images/buttons/menu/dot.gif\" border=0>&nbsp;\n" + "<a href=\""+html_FilePath+"adhocmaterialmatrix.html\" target=\"content\" onMouseOver=\"window.status='Help -> tcmIS Operation -> Reports -> Ad-Hoc Material Matrix Reports';return true\" onMouseOut=\"window.status='Help -> tcmIS Operation -> Reports';return true\"><font color=\"#990000\">\n");
                msg.append("Ad-Hoc Material Matrix</font></a><BR>\n");

                msg.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src=\""+WWWhomeDirectory+"/images/buttons/menu/dot.gif\" border=0>&nbsp;\n" + "<a href=\""+html_FilePath+"adhocwaste.html\" target=\"content\" onMouseOver=\"window.status='Help -> tcmIS Operation -> Reports -> Ad-hoc Waste Reports';return true\" onMouseOut=\"window.status='Help -> tcmIS Operation -> Reports';return true\"><font color=\"#990000\">\n");
                msg.append("Ad-hoc Waste</font></a><BR>\n");

            }
            else
            {
                msg.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href=\""+Help_Servlet_Path+"?stru9=" + stru9 + "&stru0=" + stru0 + "&stru1=" + stru1 + "&stru2=" + stru2 + "&stru3=" + stru3 + "&stru4=" + stru4 + "&stru5=o&stru6=" + stru6 + "&stru7=" + stru7 + "&stru8=" + stru8 + "&s=" + sol_count + "\" onMouseOver=\"window.status='Help -> tcmIS Operation -> Reports';return true\" onMouseOut=\"window.status='Help -> tcmIS Operation';return true\"><img src=\""+WWWhomeDirectory+"/images/buttons/menu/pos.gif\" border=0></a>&nbsp;\n");
                msg.append("Reports<BR>\n");
            }
            if(stru6.equalsIgnoreCase("o"))
            {
                msg.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href=\""+Help_Servlet_Path+"?stru9=" + stru9 + "&stru0=" + stru0 + "&stru1=" + stru1 + "&stru2=" + stru2 + "&stru3=" + stru3 + "&stru4=" + stru4 + "&stru5=" + stru5 + "&stru6=c&stru7=" + stru7 + "&stru8=" + stru8 + "&s=" + sol_count + "\" onMouseOver=\"window.status='Help -> tcmIS Operation';return true\" onMouseOut=\"window.status='Help -> tcmIS Operation -> Utilities';return true\"><img src=\""+WWWhomeDirectory+"/images/buttons/menu/neg.gif\" border=0></a>&nbsp;\n");
                msg.append("Utilities<BR>\n");
                msg.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src=\""+WWWhomeDirectory+"/images/buttons/menu/dot.gif\" border=0>&nbsp;\n" + "<a href=\""+html_FilePath+"stop.html\" target=\"content\" onMouseOver=\"window.status='Help -> tcmIS Operation -> Utilities -> Stop Query';return true\" onMouseOut=\"window.status='Help -> tcmIS Operation -> Utilities';return true\"><font color=\"#990000\">Stop Query</font></a><BR>\n");
                msg.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src=\""+WWWhomeDirectory+"/images/buttons/menu/dot.gif\" border=0>&nbsp;\n" + "<a href=\""+html_FilePath+"login.html\" target=\"content\" onMouseOver=\"window.status='Help -> tcmIS Operation -> Utilities -> Re-Login';return true\" onMouseOut=\"window.status='Help -> tcmIS Operation -> Utilities';return true\"><font color=\"#990000\">Re-Login</font></a><BR>\n");
                 //Nawaz 10-01-01
                msg.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src=\""+WWWhomeDirectory+"/images/buttons/menu/dot.gif\" border=0>&nbsp;\n" + "<a href=\""+html_FilePath+"within.html\" target=\"content\" onMouseOver=\"window.status='Help -> tcmIS Operation -> Utilities -> Print Screen';return true\" onMouseOut=\"window.status='Help -> tcmIS Operation -> Utilities';return true\"><font color=\"#990000\">\n");
                msg.append("Print Screen</font></a><BR>\n");

                //Nawaz 10-01-01
                msg.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src=\""+WWWhomeDirectory+"/images/buttons/menu/dot.gif\" border=0>&nbsp;\n<a href=\""+html_FilePath+"admin.html\" target=\"content\" onMouseOver=\"window.status='Help -> tcmIS Operation -> Administration';return true\" onMouseOut=\"window.status='Help -> tcmIS Operation';return true\"><font color=\"#990000\">Administration</font></a><BR>\n");
            }
            else
            {
             msg.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href=\""+Help_Servlet_Path+"?stru9=" + stru9 + "&stru0=" + stru0 + "&stru1=" + stru1 + "&stru2=" + stru2 + "&stru3=" + stru3 + "&stru4=" + stru4 + "&stru5=" + stru5 + "&stru6=o&stru7=" + stru7 + "&stru8=" + stru8 + "&s=" + sol_count + "\" onMouseOver=\"window.status='Help -> tcmIS Operation -> Utilities';return true\" onMouseOut=\"window.status='Help -> tcmIS Operation';return true\"><img src=\""+WWWhomeDirectory+"/images/buttons/menu/pos.gif\" border=0></a>&nbsp;\n");
             msg.append("Utilities<BR>\n");
             msg.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src=\""+WWWhomeDirectory+"/images/buttons/menu/dot.gif\" border=0>&nbsp;\n<a href=\""+html_FilePath+"admin.html\" target=\"content\" onMouseOver=\"window.status='Help -> tcmIS Operation -> Administration';return true\" onMouseOut=\"window.status='Help -> tcmIS Operation';return true\"><font color=\"#990000\">Administration</font></a><BR>\n");
            }
        }
        else
        {
            msg.append("<a href=\""+Help_Servlet_Path+"?stru9=" + stru9 + "&stru0=" + stru0 + "&stru1=" + stru1 + "&stru2=o&stru3=" + stru3 + "&stru4=" + stru4 + "&stru5=" + stru5 + "&stru6=" + stru6 + "&stru7=" + stru7 + "&stru8=" + stru8 + "&s=" + sol_count + "\" onMouseOver=\"window.status='Help -> tcmIS Operation';return true\" onMouseOut=\"window.status='Help';return true\"><img src=\""+WWWhomeDirectory+"/images/buttons/menu/pos.gif\" border=0></a>&nbsp;&nbsp;tcmIS Operation<BR>\n");
        }

        /*if(stru8.equalsIgnoreCase("o"))
        {
            msg.append("<a href=\""+Help_Servlet_Path+"?stru9=" + stru9 + "&stru0=" + stru0 + "&stru1=" + stru1 + "&stru2=" + stru2 + "&stru3=" + stru3 + "&stru4=" + stru4 + "&stru5=" + stru5 + "&stru6=" + stru6 + "&stru7=" + stru7 + "&stru8=c&s=" + sol_count + "\" onMouseOver=\"window.status='Help';return true\" onMouseOut=\"window.status='Help -> MSDS Viewer Operation';return true\"><img src=\""+WWWhomeDirectory+"/images/buttons/menu/neg.gif\" border=0></a>&nbsp;MSDS Viewer Operation<BR>\n");
            msg.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src=\""+WWWhomeDirectory+"/images/buttons/menu/dot.gif\" border=0>&nbsp;\n" + "<a href=\""+html_FilePath+"cat.html\" target=\"content\" onMouseOver=\"window.status='Help -> MSDS Viewer Operation -> MSDS for a Catalog Material';return true\" onMouseOut=\"window.status='Help -> MSDS Viewer Operation';return true\"><font color=\"#990000\">\n");
            msg.append("MSDS Search Operation</font></a><BR>\n");
            /*msg.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src=\""+WWWhomeDirectory+"/images/buttons/menu/dot.gif\" border=0>&nbsp;<a href=\""+html_FilePath+"noncat.html\" target=\"content\" onMouseOver=\"window.status='Help -> MSDS Viewer Operation -> MSDS for a Non-Catalog Material';return true\" onMouseOut=\"window.status='Help -> MSDS Viewer Operation';return true\"><font color=\"#990000\">\n");
            msg.append("For a Non-Catalog&nbsp;Material</font></a><BR>\n");
        }
        else
        {
            msg.append("<a href=\""+Help_Servlet_Path+"?stru9=" + stru9 + "&stru0=" + stru0 + "&stru1=" + stru1 + "&stru2=" + stru2 + "&stru3=" + stru3 + "&stru4=" + stru4 + "&stru5=" + stru5 + "&stru6=" + stru6 + "&stru7=" + stru7 + "&stru8=o&s=" + sol_count + "\" onMouseOver=\"window.status='Help -> MSDS Viewer Operation';return true\" onMouseOut=\"window.status='Help';return true\">");
            msg.append("<img src=\""+WWWhomeDirectory+"/images/buttons/menu/pos.gif\" border=0></a>&nbsp;&nbsp;MSDS Viewer Operation<BR>\n");
        }*/

        if((stru9.equalsIgnoreCase("o")) && (sol_count.equalsIgnoreCase("Y")))
        {
            msg.append("<a href=\""+Help_Servlet_Path+"?stru9=c&stru0=" + stru0 + "&stru1=" + stru1 + "&stru2=" + stru2 + "&stru3=" + stru3 + "&stru4=" + stru4 + "&stru5=" + stru5 + "&stru6=" + stru6 + "&stru7=" + stru7 + "&stru8=" + stru8 + "&s=" + sol_count + "\" onMouseOver=\"window.status='Help -> Frequently Asked Questions';return true\" onMouseOut=\"window.status='Help';return true\">");
            msg.append("<img src=\""+WWWhomeDirectory+"/images/buttons/menu/neg.gif\" border=0></a>&nbsp;&nbsp;");
            msg.append("<a href=\""+Help_Servlet_Path+"?stru9=c&stru0=" + stru0 + "&stru1=" + stru1 + "&stru2=" + stru2 + "&stru3=" + stru3 + "&stru4=" + stru4 + "&stru5=" + stru5 + "&stru6=" + stru6 + "&stru7=" + stru7 + "&stru8=" + stru8 + "&s=" + sol_count + "\" onMouseOver=\"window.status='Help -> Frequently Asked Questions';return true\" onMouseOut=\"window.status='Help';return true\">");
            msg.append("<img src=\"/tcmIS/help/new/faq1.gif\" name=\"img1\" border=0></a><BR>\n");

            //Nawaz 10-01-01
            /*String client1 = bundle.get("DB_CLIENT_NAME").toString();
            db = new RayTcmISDBModel(client1);

            if (client.equalsIgnoreCase("Raytheon"))
            {
                db = new RayTcmISDBModel("Ray");
            }
            else if (client.equalsIgnoreCase("Southwest Airlines"))
            {
                db = new SWATcmISDBModel("SWA");
            }
            else if (client.equalsIgnoreCase("Lockheed"))
            {
                db = new LMCOTcmISDBModel("LMCO");
            }
            else if (client.equalsIgnoreCase("DRS"))
            {
                db = new DRSTcmISDBModel("DRS");
            }*/

            try
            {
                buildData(FaqMenu.getQuestions(db, "2"), out, "2");
            }
            catch(Exception exception)
            {
            }
             finally
            {
               // db.close();
            }
            msg.append(solution);

        }
        else if (sol_count.equalsIgnoreCase("Y"))
        {
            msg.append("<a href=\""+Help_Servlet_Path+"?stru9=o&stru0=" + stru0 + "&stru1=" + stru1 + "&stru2=" + stru2 + "&stru3=" + stru3 + "&stru4=" + stru4 + "&stru5=" + stru5 + "&stru6=" + stru6 + "&stru7=" + stru7 + "&stru8=" + stru8 + "&s=" + sol_count + "\" onMouseOver=\"window.status='Help -> Frequently Asked Questions';return true\" onMouseOut=\"window.status='Help';return true\">");
            msg.append("<img src=\""+WWWhomeDirectory+"/images/buttons/menu/pos.gif\" border=0></a>&nbsp;&nbsp;");
            msg.append("<a href=\""+Help_Servlet_Path+"?stru9=o&stru0=" + stru0 + "&stru1=" + stru1 + "&stru2=" + stru2 + "&stru3=" + stru3 + "&stru4=" + stru4 + "&stru5=" + stru5 + "&stru6=" + stru6 + "&stru7=" + stru7 + "&stru8=" + stru8 + "&s=" + sol_count + "\" onMouseOver=\"window.status='Help -> Frequently Asked Questions';return true\" onMouseOut=\"window.status='Help';return true\">");
            msg.append("<img src=\"/tcmIS/help/new/faq1.gif\" name=\"img1\" border=0></a><BR>\n");
        }

        if(sol_count.equalsIgnoreCase("Y"))
        {
            //msg.append("<a href=\""+Help_Servlet_Path+"?faq=on&s=" + sol_count + "\" target=\"content\" onMouseOver=\"imgOn('img1')\" onMouseOut=\"imgOff('img1')\"><img src=\"/tcmIS/help/new/faq1.gif\" name=\"img1\" border=0></a><BR>\n");
        }

        msg.append("<BR>\n");
        msg.append("</FONT><FONT FACE=\"Arial\" size=2>View the <A href=\""+html_FilePath+"tcmISHelp.pdf\" target=\"content\">Help Manual</A>\n in PDF format.</font>\n");
        msg.append("<CENTER><A href=\""+html_FilePath+"tcmISHelp.pdf\" target=\"content\"><img src=\""+WWWhomeDirectory+"/images/acopdflogo2.gif\" name=\"Adobe PDF\" border=0></A></CENTER>\n");
        msg.append("</body></html>\n");

        out.println(msg);
    }

    protected void buildData(Vector v, PrintWriter out, String session)
    {
        for(int j = 0; j <= v.size(); j++)
        {
            FaqMenu fm = (FaqMenu)v.elementAt(j);
            if(session.equalsIgnoreCase("1"))
            {
                sol_count = fm.getSolCount();
                continue;
            }
            if(session.equalsIgnoreCase("2"))
                solution += fm.getSolution();
        }

    }

    protected void checkData(Vector v, String session)
    {
        for(int j = 0; j <= v.size(); j++)
        {
            FaqMenu fm = (FaqMenu)v.elementAt(j);
            if(session.equalsIgnoreCase("1"))
                sol_count = fm.getSolCount();
        }

    }

     public void buildANSWER(PrintWriter out)
    {
        StringBuffer Msg1 = new StringBuffer();
        Msg1.append("<html><body bgcolor=ffffff><center><img src=\""+html_FilePath+"faqh.gif\" border=0></center>\n");

        //Nawaz 10-01-01
        /*if (client.equalsIgnoreCase("Raytheon"))
        {
            db = new RayTcmISDBModel("Ray");
        }
        else if (client.equalsIgnoreCase("Southwest Airlines"))
        {
            db = new SWATcmISDBModel("SWA");
        }
        else if (client.equalsIgnoreCase("Lockheed"))
        {
            db = new LMCOTcmISDBModel("LMCO");
        }
        else if (client.equalsIgnoreCase("DRS"))
        {
            db = new DRSTcmISDBModel("DRS");
        }*/

        try
        {
            Msg1.append(FaqMenu.getAnswer(db, QuestionID));
        }
        catch(Exception exception)
        {
        }
         finally
        {
            //db.close();
        }

        Msg1.append("</body>\n</html>\n");
        out.println(Msg1);
    }

    public void printOutput(HttpServletResponse response)
    {
        //System.out.println(gettingFAQ);
        PrintWriter out = null;
        try
        {
            out = new PrintWriter(response.getOutputStream());
            if (gettingAnswer)
            {
             buildANSWER(out);
            }
            else
            {
            if(checkingFAQ)
                checkFAQ();

            if(gettingHTML)
                buildHTML(out);
            else
            if(gettingFAQ)
                buildFAQ(out);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            db.close();
        }
        out.flush();
        out.close();
    }


    public void BuildFramePage(HttpServletResponse response)
    {
        //System.out.println(gettingFAQ);
        try
        {
            PrintWriter out = new PrintWriter(response.getOutputStream());
            response.setContentType("text/html");

            StringBuffer Msg = new StringBuffer();

            Msg.append("<HTML>\n");
            Msg.append("<HEAD>\n");
            Msg.append("<TITLE>"+bundle.get("DB_CLIENT").toString()+" -> tcmIS HELP Information\n");
            Msg.append("</TITLE>\n");
            Msg.append("</HEAD>\n");
            Msg.append("<FRAMESET cols=\"220,*\">\n");

            Msg.append("<FRAME SRC=\""+bundle.get("HELP_SERVLET_PATH").toString()+"\" NAME=\"menu\" SCROLLING=\"auto\">\n");
            Msg.append("<FRAME SRC=\""+bundle.get("HELP_HTML_PATH").toString()+"intropage.html\" NAME=\"content\" SCROLLING=\"auto\" >\n");

            /*Msg.append("<FRAME SRC=\"http://localhost"+bundle.get("HELP_SERVLET_PATH").toString()+"\" NAME=\"menu\" SCROLLING=\"auto\">\n");
            Msg.append("<FRAME SRC=\"http://localhost"+bundle.get("HELP_HTML_PATH").toString()+"intropage.html\" NAME=\"content\" SCROLLING=\"auto\" >\n");
*/
            Msg.append("<NOFRAMES>\n");
            Msg.append("<P>his page uses frames. Your browser does not seem to support frames.</P>\n");
            Msg.append("</NOFRAMES>\n");

            Msg.append("</FRAMESET>\n");

            Msg.append("</HTML>\n");

            out.println(Msg);

            out.flush();
            out.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public String getServletInfo()
    {
        return "radian.web.servlets.help.helpMenu Information";
    }
}
