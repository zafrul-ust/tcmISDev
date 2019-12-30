package radian.web.servlets.pos;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import radian.tcmis.both1.helpers.*;



public class purch_order_1 extends HttpServlet
  {
    private static final String CONTENT_TYPE = "text/html";
    /**Initialize global variables*/
    String filetoDelete = "";
    String formURL;
    String msg = "";
    public void init(ServletConfig config) throws ServletException
      {
        super.init(config);
      }
    /**Process the HTTP Get request*/
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
      {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        //get name of previous unsent file
        //find and delete file before generating new purchase order
        try
          {
            filetoDelete = new String(BothHelpObjs.makeBlankFromNull(request.getParameter("filetoDelete")));
            File outFile = new File(filetoDelete);
            if (outFile.isFile())
              {
                boolean deleteFile;
                deleteFile = outFile.delete();
              }
          }
        catch (SecurityException se)
           {
            se.printStackTrace();
	   }
        catch (NullPointerException npe)
          {
            npe.printStackTrace();
          }

        formURL = "tcmIS/servlet/radian.web.servlets.pos.purch_order_2";
       // formURL = response.encodeRedirectUrl(formURL);
        msg = "";
        msg +="<html>\n";
        msg +="<head><title>Purchase Order Number Info</title></head>\n";
        msg +="<body bgcolor=\"#00CCFF\" text=\"#000000\" link=\"#0000FF\" vlink= \"#FF00FF\">\n";
        msg +="<center>\n";
        msg +="<img src=\"http://www.tcmis.com/images/tcmIS/gif/tcmhead.gif\" alt=\"could not display\">\n";
        msg +="</CENTER><HR>\n";
        msg +="<center>\n";
        msg +="<h1>Purchase Order Number Info</h1>\n";
        msg +="<form action=\"/"+formURL+"\" method=\"post\">\n";
        msg +="<table>\n";
        msg +="	<tr>\n";
        msg +="	<td align=\"left\">Radian PO # is:          </td>\n";
        msg +="	<td align=\"left\"><input type=\"text\" name=\"po\" size=\"10\"></td>\n";
        msg +="	</tr>\n";
        msg +="</table>\n";
        msg +="<P></P>\n";
        msg +="<input type=\"submit\" name = \"enter\" value=\"Enter Info\">\n";
        msg +="</form></center></body></html>\n";
        out.println(msg);
        out.close();
      }

    /**Process the HTTP Post request*/
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
      {
        response.setContentType(CONTENT_TYPE);
        HttpSession session = request.getSession();
        doGet(request,response);
      }

    /**Clean up resources*/
    public void destroy()
      {
      }
  }