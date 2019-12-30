package radian.web.servlets.pos;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.net.*;
import java.lang.String;

import radian.tcmis.both1.helpers.BothHelpObjs;


public class purch_order_3 extends HttpServlet {
  private static final String CONTENT_TYPE = "text/html";
  /**Initialize global variables*/
  public void init(ServletConfig config) throws ServletException
    {
      super.init(config);
    }
  /**Process the HTTP Get request*/
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
      response.setContentType(CONTENT_TYPE);
      HttpSession session = request.getSession();
      PrintWriter out = response.getWriter();

      String po = "";
      String order_taken_by = "";
      String vendor_name = "";
      String vendor_fax = "";
      String buyer_name = "";
      String buyer_email = "";
      String buyer_phone = "";
      String buyer_fax = "";
      String reference = "";
      String urgent = "";
      String msg = "";
      String errormsg = "";
      String errormsgH = "";
      String wheretolook = "";

      StringBuffer Msg = new StringBuffer();


      errormsgH = "<HTML><HEAD><TITLE>ERROR OCCURED</TITLE></HEAD>";
      errormsgH +="<BODY bgcolor=\"#00CCFF\" text=\"#000000\" link=\"#0000FF\" vlink= \"#FF00FF\"><FONT FACE=\"Arial\"><CENTER>\n";

      try
        {
          po = new String(BothHelpObjs.makeBlankFromNull(request.getParameter("po")));
          order_taken_by = new String(BothHelpObjs.makeBlankFromNull(request.getParameter("order_taken_by")));
          vendor_name = new String(BothHelpObjs.makeBlankFromNull(request.getParameter("vendor_name")));
          vendor_fax = new String(BothHelpObjs.makeBlankFromNull(request.getParameter("vendor_fax")));
          buyer_name = new String(BothHelpObjs.makeBlankFromNull(request.getParameter("buyer_name")));
          buyer_email = new String(BothHelpObjs.makeBlankFromNull(request.getParameter("buyer_email")));
          buyer_phone = new String(BothHelpObjs.makeBlankFromNull(request.getParameter("buyer_phone")));
          buyer_fax = new String(BothHelpObjs.makeBlankFromNull(request.getParameter("buyer_fax")));
          reference = new String(BothHelpObjs.makeBlankFromNull(request.getParameter("reference")));
          wheretolook = new String(BothHelpObjs.makeBlankFromNull(request.getParameter("wheretolook")));
        }
     catch(Exception e)
     {
       errormsg +="<P>Error While Getting Parameters to Send FAX</P>";
       errormsg +="<P>Go Back and Please Try Again </P>";
       errormsg +="<P>If problem continues please let the tech team know. </P>";
       errormsg +="<P>Thanks.</P></FONT></CENTER></BODY></HTML>";
       errormsgH += errormsg;
       out.println(errormsgH);
       out.close();
       return;
     }

           /**check if the po_file is available check if it is online like in the MSDs
       * if it is online show it. otherwise build it and do a redirect to the
       * file url.
       *
       */
       String testurl2 = "";
       try
       {
          testurl2 = "http://eagle.tcmis.com/tmpPO/Backup/po_"+po+".pdf";
          URL aURL = new URL(testurl2);

          BufferedReader in = new BufferedReader(new InputStreamReader(aURL.openStream()));
          String inputLine;

          int test = 0;
          for (int i = 0; i < 10; i++)
          {
              inputLine = in.readLine();
              test = inputLine.indexOf("(404)");
              if (test > 0)
              {
                  break;
              }
          }
          in.close();
          if (test > 0)
          {
          wheretolook = "New";
          }
          else
          {

          }
      }
      catch (Exception e)
      {
         wheretolook = "New";
      }

     //encode redirected URL to maintain session
     /*String formURL1 = "servlet/radian.web.servlets.pos.purch_order_4";
     formURL1 = response.encodeRedirectUrl(formURL1);

     String formURL2 = "servlet/radian.web.servlets.pos.purch_order_2";
     formURL2 = response.encodeRedirectUrl(formURL2);*/

     Msg.append( "<html>\n");
     Msg.append( "<head>\n");
     Msg.append( "<title></title>\n");
     Msg.append( "</head>\n");
     Msg.append( "<body text=\"#000000\" background= \n");
     Msg.append( "\"https://tcmisfax1.radian.com/bg2.jpg\">\n");
     Msg.append( "<img src=\"https://www.tcmis.com/images/tcmIS/gif/tcmhead.gif\" width=\"400\"\n");
     Msg.append( "height=\"85\" border=\"0\"><br>\n");
     Msg.append( "<font size=\"3\">Complete this form to send the fax:</font> \n");
     Msg.append( "<hr align=\"left\" width=\"400\">\n");
     Msg.append( "<form method=\"POST\" action=\"/tcmIS/servlet/radian.web.servlets.pos.purch_order_4?\">\n");
     Msg.append( "<table cellpadding=\"2\">\n");
     Msg.append( "<tbody>\n");
     Msg.append( "<tr valign=\"top\" align=\"left\">\n");
     Msg.append( "<td valign=\"top\" align=\"right\"><font size=\"2\"><b>Sender:</b></font><br>\n");
     Msg.append( "</td>\n");
     Msg.append( "<td>" + buyer_name + "</td>\n");
     Msg.append( "</tr>\n");

     Msg.append( "<tr>\n");
     Msg.append( "<td valign=\"top\" align=\"right\"><font size=\"2\"><b>Sender Phone:</b></font><br>\n");
     Msg.append( "</td>\n");
     Msg.append( "<td><input type=\"text\" name=\"buyer_phone\" value=\"" + buyer_phone + "\"></td>\n");
     Msg.append( "</tr>\n");

     Msg.append( "<tr>\n");
     Msg.append( "<td valign=\"top\" align=\"right\"><font size=\"2\"><b>Sender Fax:</b></font><br>\n");
     Msg.append( "</td>\n");
     Msg.append( "<td><input type=\"text\" size=\"35\" name=\"buyer_fax\" value=" + buyer_fax + "></td>\n");
     Msg.append( "</tr>\n");

     Msg.append( "<tr valign=\"top\" align=\"left\">\n");
     Msg.append( "<td valign=\"top\" align=\"right\"><font size=\"2\"><b>Sender email:</b></font><br>\n");
     Msg.append( "</td>\n");
     Msg.append( "<td><input type=\"text\" size=\"35\" name=\"buyer_email\" value=" + buyer_email + ">\n");
     Msg.append( "</td>\n");
     Msg.append( "</tr>\n");

     Msg.append( "<tr>\n");
     Msg.append( "<td valign=\"top\" align=\"right\"><font size=\"2\"><b>Fax To (Person):</b></font><br>\n");
     Msg.append( "</td>\n");
     Msg.append( "<td><input type=\"text\" size=\"35\" name=\"order_taken_by\" value=" + order_taken_by + ">\n");
     Msg.append( "</td></tr>\n");

     Msg.append( "<tr>\n");
     Msg.append( "<td align=\"right\"><font size=\"2\"><b>Fax To (Company):</b></font><br>\n");
     Msg.append( "</td>\n");
     Msg.append( "<td>" + vendor_name + "</td>\n");
     Msg.append( "</tr>\n");

     Msg.append( "<tr>\n");
     Msg.append( "<td valign=\"top\" align=\"right\"><font size=\"2\"><b>Fax # (1-xxx-xxx-xxxx):</b></font><br>\n");
     Msg.append( "</td>\n");
     Msg.append( "<td><input type=\"text\" size=\"30\" name=\n");
     Msg.append( "\"vendor_fax\" value=\"" + vendor_fax + "\">\n");
     Msg.append( "</td>\n");
     Msg.append( "</tr>\n");

     Msg.append( "<tr>\n");
     Msg.append( "<td valign=\"top\" align=\"right\">\n");
     Msg.append( "<font size=\"2\"><b>Reference:</b></font></td>\n");
     Msg.append( "<td>Purchase Order " + po + "</td>\n");
     Msg.append( "</tr>\n");

     Msg.append( "<tr>\n");
     Msg.append( "<td valign=\"top\" align=\"right\">\n");
     Msg.append( "<font size=\"2\"><b>Comments:</b></font></td>\n");
     Msg.append( "<td>\n");
     /*Msg.append( "<TEXTAREA name=\"Comments\" rows=\"3\" cols=\"28\">\n");
     Msg.append( "</TEXTAREA>\n");*/

     Msg.append( "<input type=\"text\" size=\"50\" name=\"Comments\">\n");

     Msg.append( "</td>\n");
     Msg.append( "</tr>\n");

     Msg.append("<TR><td align=\"right\"><INPUT TYPE=\"checkbox\" value=\"Yes\" NAME=\"confirmingpo\" checked></td><td><font face=Arial  size=2>Confirming PO#</font></td></TR>\n");

     Msg.append( "<tr>\n");
     Msg.append( "<td></td>\n");
     Msg.append( "<td><BR>\n");
     //<input type=\"radio\" name=\"urgent\" value=\"yes\">urgent<br>\n");
     Msg.append( "<input type=\"hidden\" name=\"po\" value=\"" + po+ "\">\n");
     Msg.append( "<input type=\"hidden\" name=\"vendor_name\" value=\""+ vendor_name + "\">\n");
     Msg.append( "<input type=\"hidden\" name=\"buyer_name\" value=\"" + buyer_name +"\">\n");
     //Msg.append( "<input type=\"hidden\" name=\"buyer_phone\" value=\"" + buyer_phone + "\">\n");
     //Msg.append( "<input type=\"hidden\" name=\"buyer_fax\" value=\"" + buyer_fax +"\">\n");
     Msg.append( "<input type=\"hidden\" name=\"reference\" value=\"Purchase Order " + po + "\">\n");
      Msg.append( "<input type=\"hidden\" name=\"wheretolook\" value=\""+wheretolook+"\">\n");
     Msg.append( "</td>\n");
     Msg.append( "</tr>\n");

     Msg.append( "<tr>\n");
     Msg.append( "<td></td>\n");
     Msg.append( "</tr>\n");

     Msg.append( "<tr>\n");
     Msg.append( "<td></td>\n");
     Msg.append( "<td><input type=\"submit\" name=\"Send\" value=\"Submit\"></td>\n");
     Msg.append( "</form>\n");
     Msg.append( "</tr>\n");

     Msg.append( "<tr>\n");
     Msg.append( "<td></td>\n");
     Msg.append( "</tr>\n");
     Msg.append( "<tr>\n");
     Msg.append( "<td></td>\n");
     /*Msg.append( "<td>\n");
     Msg.append( "<form method=\"POST\" action=\"" + formURL2 + "\">\n");
     Msg.append( "<input type= \"submit\" name=\"cancel\" value=\"Cancel\">\n");
     Msg.append( "<input type=\"hidden\" name= \"filetoDelete\"\n");
     Msg.append( " value=\"/home/httpd/htdocs/tmpPO/po_" + po + ".pdf\">\n");
     Msg.append( "</form>\n");
     Msg.append( "</td>\n");*/
     Msg.append( "</tr>\n");
     Msg.append( "</tbody>\n");
     Msg.append( "</table>\n");
     Msg.append( "</body>\n");
     Msg.append( "</html>\n");

     out.println (Msg);
    }
  /**Process the HTTP Post request*/
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
      doGet (request, response);
    }
}