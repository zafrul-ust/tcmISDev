package radian.web.servlets.share;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import radian.tcmis.server7.client.ray.dbObjs.RayTcmISDBModel;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.dbObjs.WideView;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class batchReport extends HttpServlet implements SingleThreadModel
{
    // Initializing global Variables
    ServerResourceBundle bundle=null;
    TcmISDBModel db = null;
    public batchReport(ServerResourceBundle b, TcmISDBModel d)
    {
        bundle = b;
        db = d;
    }

    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
        IOException
    {
        doPost(request,response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
        IOException
    {
        RayTcmISDBModel db = null;
        String client = new String("");
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        String Logon_Id = request.getParameter("Logon_Id");
        String userId = request.getParameter("userId");
        String Session = request.getParameter("Session");
        String content = request.getParameter("content");
        String batchreportser = bundle.getString("BATCH_REPORT_SERVLET");

        out.println("<html><HEAD><title>Batch Reports</title></HEAD>\n");
        out.println("<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=iso-8859-1\">\n");
        out.println("<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n");
        out.println("<META HTTP-EQUIV=\"Expires\" CONTENT=\"-1\">\n");
        out.println("<title>Batch Reports</title></HEAD>\n");
        out.println("<body>\n");
        out.println("<font face=Arial size=5>\n<img src=\"/images/logo_s.gif\" border=1 align=\"top\">\n&nbsp;&nbsp;&nbsp;<font color=\"#000080\"><B>\n");
        out.println("Batch Reports </font></B>\n</font>\n<BR><P></P>\n");
        out.println("<font face=Arial size=3><table BORDER=\"0\" CELLPADDING=\"1\" WIDTH=\"75%\">\n<tr align=\"left\">\n<form method=\"POST\" name=\"form1\" action=\""+batchreportser+"\"> \n");
        out.println("<td width=\"10%\"><B>Login Id : </B></td>\n<td width=\"20%\"><input type=\"text\" name=\"Logon_Id\" value=\""+(Logon_Id==null?"":Logon_Id)+"\" size=\"10\"></td>\n");
        out.println("<td width=\"10%\"><input type=\"submit\" value=\"Search\" name=\"search\"></td>\n</form>\n</tr></TABLE></font>\n<HR noshade size=\"5\" width=\"75%\" align=\"left\">\n<P></P>\n");

        if ((Logon_Id != null))
        {
            if (Session == null)       // This is the first time the servlet is called
            {
                getReports(Logon_Id,out);
                out.close();
            }
            else if (Session.equalsIgnoreCase("1"))
            {
                updateReport(userId,content);
                getReports(Logon_Id,out);
                out.close();
            }
        }
		out.println("</BODY></HTML>\n");
        out.close();
    }
    public void getReports(String Logon_Id,PrintWriter out)
    {
        //String header = "";
		Vector data = new Vector();

        try
        {
            WideView wo = new WideView(db);

            data = wo.getReport(db,Logon_Id);
        }
        catch (Exception e)
        {
            System.out.println("*** Error on open DB on Getting Reports ***");
            e.printStackTrace();
        }

        int recs = data.size();
        if (recs == 0)
        {
            out.println("<font face=Arial size=3>No Reports Found \n");
        }
        else
        {
            Hashtable hD = null;

            out.println("<table BORDER=\"1\" CELLPADDING=\"0\" WIDTH=\"75%\"><tr>\n");
            out.println("<font face=Arial size=2>\n<tr align=\"center\"><td bgcolor=\"#B0BFD0\" width=\"10%\" height=\"38\"><strong><font face=Arial size=2>Report Date</font></strong></td>\n");
            out.println("  <td bgcolor=\"#B0BFD0\" width=\"60%\" height=\"38\"><strong><font face=Arial size=2>Report Name</font></strong></td>\n");
            out.println("  <td bgcolor=\"#B0BFD0\" width=\"10%\" height=\"38\"><strong><font face=Arial size=2>Delete</font></strong></td></tr>\n");

            for (int i=0;i<data.size(); i++)
            {

                int Beta = 1;

                hD = (Hashtable) data.elementAt(i);
                String Color = "";
                /*if (i%2==0)
                {
                Color = "bgcolor=\"#dddddd\"";
                }*/
                //System.out.println("this is the "+i+"th time");

                String personnel = (hD.get("PERSONNEL_ID")==null?"&nbsp;":hD.get("PERSONNEL_ID").toString());
                String report_date = (hD.get("REPORT_DATE")==null?"&nbsp;":hD.get("REPORT_DATE").toString());
                String report_name = (hD.get("REPORT_NAME")==null?"&nbsp; ":hD.get("REPORT_NAME").toString());
                String content = (hD.get("CONTENT")==null?"&nbsp; ":hD.get("CONTENT").toString());
                String status = (hD.get("STATUS")==null?"&nbsp; ":hD.get("STATUS").toString());

                if (status.equalsIgnoreCase("delete"))
                {
                    Beta = 10;
                    //System.out.println("This is the number" + status +" ");
                }

                if ( Beta == 1)
                {
                    String batchreportser = bundle.getString("BATCH_REPORT_SERVLET");
                    out.println("<form method=\"POST\" name=\"form1\" action=\""+batchreportser+"Session=1&Logon_Id="+Logon_Id+"&userId="+personnel+"\"> \n");
                    out.println("<tr align=\"center\">\n");
                    out.println("<input type=\"hidden\" name=\"Logon_Id\" value=\""+Logon_Id+"\">\n");
                    out.println("<input type=\"hidden\" name=\"Session\" value=\""+1+"\">\n");
                    out.println("<input type=\"hidden\" name=\"userId\" value=\""+personnel+"\">\n");
                    out.println("<input type=\"hidden\" name=\"content\" value=\""+content+"\">\n");
                    out.println("<td "+Color+" width=\"10%\"><a href=\""+content+"\">"+report_date+"</a></td>\n");
                    out.println("<td "+Color+" width=\"60%\">"+report_name+"</td>\n");
                    out.println("<td "+Color+" width=\"10%\"><input type=\"submit\" value=\"Delete\" name=\"Delete"+i+"\">\n");
                    out.println("</tr>\n");
                    out.println("</form>\n");
                }
            }
        }
        out.println("</font></table>\n");
        return;
    }
    public void updateReport(String userId,String content)
    {
        try
        {
            WideView wo = new WideView(db);
            wo.delReport(db,userId,content);
        }
        catch (Exception e)
        {
            System.out.println("*** Error on open DB on Getting Reports ***");
            e.printStackTrace();
        }

    }
    public String getServletInfo()
    {
        return "radian.web.servlets.SearchMsds Information";
    }

}

