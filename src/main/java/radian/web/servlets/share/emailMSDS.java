package radian.web.servlets.share;

/**
 * Title:        Your Product Name
 * Description:  Your description
 * Copyright:    Copyright (c) 2001
 * Company:      Radian
 * @author Nawaz Shaik
 * @version
 * 08-15-03 - Sending emails through common object. Sending Spec information when a broken spec is reported
 * 09-01-03 - taking out /tcmIS in the code
 */

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.web.servlets.seagate.SeagateServerResourceBundle;
public class emailMSDS extends HttpServlet
{
    //Process the HTTP Post request
    public emailMSDS()
    {

    }

    public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,
        IOException
    {
        doPost(request,response);
    }

    protected ServerResourceBundle getBundle()
    {
      return ( ServerResourceBundle )new SeagateServerResourceBundle();
    }

    //Process the HTTP Get request
    public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,
        IOException
    {
        String Session = "";
        String material_id = "";
        String revision_date = "";
        String client = "";
        String facility = "";
        String content = "";
        String Act = "";
        String textadd = "";

        response.setContentType("text/html");
        PrintWriter out = new PrintWriter (response.getOutputStream());

        String WWWHomeDirect = getBundle().getString("WEBS_HOME_WWWS");

        try{material_id= request.getParameter("material_id").toString();}catch (Exception e1){material_id = "0";}
        try{revision_date= request.getParameter("revision_date").toString();}catch (Exception e1){revision_date = "Null";}
        try{client= request.getParameter("cl").toString();}catch (Exception e1){client = "Not Known";}
        try{facility= request.getParameter("facility").toString();}catch (Exception e1){facility = "Not Known";}
        try{content= request.getParameter("url").toString();}catch (Exception e1){content = "Not Known";}
        try{Act= request.getParameter("act").toString();}catch (Exception e1){Act = "Not Known";}
        try{Session= request.getParameter("Session").toString();}catch (Exception e1){Session = "Not Known";}
        try{textadd= request.getParameter("thetext").toString();}catch (Exception e1){textadd = "Not Known";}


        String spec_hold=request.getParameter( "spec_hold" );
        if ( spec_hold == null )
          spec_hold="";
        spec_hold=spec_hold.trim();

        String catpartno=request.getParameter( "catpartno" );
        if ( catpartno == null )
          catpartno="";
        catpartno=catpartno.trim();

        if ( Session.equalsIgnoreCase( "1" ) )
        {
          String message="";
          if ( "spec".equalsIgnoreCase( Act ) )
          {
            message="The following Spec has problems.\n\n";
            message+="Spec : " + spec_hold + "\n";
            message+="Catalog Part Number : " + catpartno + "\n";
          }
          else
          {
            message="The following Material ID has problems.\n\n";
            message+="Material_Id : " + material_id + "\n";
            message+="Revision Date : " + revision_date + "\n";
          }

          message+="Client : " + client + "\n";
          message+="Facility : " + facility + "\n";
          message+="Content (URL) : " + content + "\n";
          message+="Viewing : " + Act + "\n";

					String name = request.getParameter("name");
					if (name == null)
					 name = "";
					name = name.trim();
					message += "Name : " + name + "\n";

					String phone = request.getParameter("phone");
					if (phone == null)
					 phone = "";
					phone = phone.trim();
					message += "Phone : " + phone + "\n";

					String email = request.getParameter("email");
					if (email == null)
					 email = "";
					email = email.trim();
					message += "Email : " + email + "\n";

					message += "Message : " + textadd + "\n";

          radian.web.emailHelpObj.sendmsdshelpemail( "MSDS ERROR",message );

          out.println( "<HTML>\n" );
					out.println("<HEAD><SCRIPT SRC=\"/js/common/msds/msdserror.js\" LANGUAGE=\"JavaScript\"></SCRIPT></HEAD>\n");
          out.println( "<BODY BGCOLOR=#FFFFFF text=990000>\n" );
          out.println( "<CENTER><BR><BR><BR><BR>\n" );
          out.println( "THANK YOU<BR><BR>\n" );
          out.println( "This problem has been reported \n" );
          out.println( "to the catalog team.\n" );
          out.println( "</CENTER>\n" );
          out.println( "</BODY></HTML>\n" );
        }
        else
        {
        out.println("<HTML>\n");
				out.println("<HEAD><SCRIPT SRC=\"/js/common/msdserror.js\" LANGUAGE=\"JavaScript\"></SCRIPT></HEAD>\n");
        out.println("<BODY><img src=\"/images/logo_s.gif\">\n<BR><BR>\n");
				String erroremailurl = radian.web.tcmisResourceLoader.getmsdserrorurl();
        out.println("<FORM METHOD=\"POST\" name=\"MainForm\" action=\""+erroremailurl+"Session=1\">\n");

        if ("spec".equalsIgnoreCase(Act))
        {
          out.println( "<B>Catalog Part Number :</B> " + catpartno + "<BR>\n" );
          out.println( "<B>Spec :</B> " + spec_hold + "<BR>\n" );
        }
        else
        {
          out.println( "<B>Material_Id :</B> " + material_id + "<BR>\n" );
          out.println( "<B>Revision Date : </B>" + revision_date + "<BR>\n" );
        }

        out.println( "<B>Client : </B>" + client + "<BR>\n" );
        out.println( "<B>Facility : </B>" + facility + "<BR>\n" );
        out.println("<B>Viewing : </B>"+Act+"<BR>\n");
				out.println("<BR>Please provide any further information which can help the Catalog team<BR>to resolve your problem faster, including your name and phone number.<BR> Thank You.<BR>\n");
				out.println("<B>Name : </B><INPUT TYPE=\"text\" NAME=\"name\" ID=\"name\" value=\"\" size=\"40\" MAXLENGTH=\"50\"><BR>\n");
				out.println("<B>Phone : </B><INPUT TYPE=\"text\" NAME=\"phone\" ID=\"phone\" value=\"\" size=\"11\" MAXLENGTH=\"20\"><BR>\n");
				out.println("<B>Email :  </B><INPUT TYPE=\"text\" NAME=\"email\" ID=\"email\" value=\"\" size=\"40\" MAXLENGTH=\"50\"><BR>\n");
        out.println("<B>Comments : </B><BR><TEXTAREA name=\"thetext\" rows=\"15\" cols=\"50\">");
        out.println("</TEXTAREA>\n");
				out.println("<BR><INPUT type=\"submit\" value=\"Submit\" name=\"Submit\" onclick= \"return checkName()\">&nbsp;&nbsp;<INPUT type=\"reset\">\n");
				out.println("<INPUT TYPE=\"hidden\" NAME=\"material_id\" VALUE=\""+material_id+"\">\n");
				out.println("<INPUT TYPE=\"hidden\" NAME=\"revision_date\" VALUE=\""+revision_date+"\">\n");
				out.println("<INPUT TYPE=\"hidden\" NAME=\"cl\" VALUE=\""+client+"\">\n");
				out.println("<INPUT TYPE=\"hidden\" NAME=\"facility\" VALUE=\""+facility+"\">\n");
				out.println("<INPUT TYPE=\"hidden\" NAME=\"url\" VALUE=\""+content+"\">\n");
				out.println("<INPUT TYPE=\"hidden\" NAME=\"act\" VALUE=\""+Act+"\">\n");
				out.println("<INPUT TYPE=\"hidden\" NAME=\"spec_hold\" VALUE=\""+spec_hold+"\">\n");
				out.println("<INPUT TYPE=\"hidden\" NAME=\"catpartno\" VALUE=\""+catpartno+"\">\n");
				out.println("<INPUT TYPE=\"hidden\" NAME=\"Session\" VALUE=\"1\">\n");

        out.println("</FORM>\n");
        out.println("</BODY></HTML>\n");
        }
        out.close();
    }
}
