package radian.web.servlets.pos;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.lang.String;

import radian.tcmis.both1.helpers.BothHelpObjs;


public class CancelFaxpo extends HttpServlet {
  private static final String CONTENT_TYPE = "text/html";
  /**Initialize global variables*/
  String errormsgH = "";

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
      String errormsg = "";


      String Action = "";

      StringBuffer Msg = new StringBuffer();

      errormsgH = "<HTML><HEAD><TITLE>ERROR OCCURED</TITLE></HEAD>";
      errormsgH +="<BODY bgcolor=\"#00CCFF\" text=\"#000000\" link=\"#0000FF\" vlink= \"#FF00FF\"><FONT FACE=\"Arial\"><CENTER>\n";

      try{ Action = request.getParameter("Action").toString();}catch (Exception e){ Action = "No";}

      try
        {
          po = new String(BothHelpObjs.makeBlankFromNull(request.getParameter("po")));
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

     System.out.println("Cancel Request  " +po);

     if ("Cancel".equalsIgnoreCase(Action))
     {
        String  file = "/webdata/html/tmpPO/cancel/po_"+ po;
        try
              {
                File outFile = new File(file+".ctx");
	        FileOutputStream DBO = new FileOutputStream(outFile);
                String dbostr = "To :  \n";
                dbostr += "PO Number Test : "+po+"\n";
                byte outbuf[];
                outbuf = new byte[dbostr.length()];
	        outbuf = dbostr.getBytes();
	        DBO.write(outbuf);
	        DBO.close();
              }
            catch(IOException ioe)
              {
	        System.out.println("\n\nError opening output file: ");
	        System.out.println(String.valueOf(ioe));
	        System.out.println("\n");
                PrintError(out,"Could not Write Context File");
                return;
	      }
	    // The shell command to ftp the files to tcmisfax1

            String ftpCmd1 = "/webdata/html/tmpPO/pos/ftpPOCancel.sh";
            String[] ftpCmd = {"/bin/sh", ftpCmd1, file};

            try
            {
              // A Runtime object to execute shell commands
              Runtime rt = Runtime.getRuntime();
              // Move the job's files to the newFaxes directory on tcmisfax1
              Process p = rt.exec(ftpCmd);
              // Check for any errors in the ftp command
              BufferedReader stdError = new BufferedReader(new
              InputStreamReader(p.getErrorStream()));
              String errStr = null;
              while ((errStr = stdError.readLine()) != null)
              {
                //out.println(errStr);
              }
            System.out.println("\n\nDone Doing the Shell Script");
            }
            catch (java.io.IOException e)
            {
              System.out.println(e);
              PrintError(out,"Error While Running the FTP Script");
              return;
            }

            out.println (" <FONT FACE=\"Arial\" size=\"3\"><BR><BR> A request has been sent to cancel the fax.<BR>\n");
            out.println ("If the fax is not sent yet, you will receive an e-mail giving more details regarding the request.</FONT>\n");
     }
     else
     {
     Msg.append( "<html>\n");
     Msg.append( "<head>\n");
     Msg.append( "<title></title>\n");
     Msg.append( "</head>\n");
     Msg.append( "<body text=\"#000000\">\n");
     Msg.append( "<img src=\"https://www.tcmis.com/images/tcmIS/gif/tcmhead.gif\" width=\"400\"\n");
     Msg.append( "height=\"85\" border=\"0\"><br><br><br>\n");
     Msg.append( "<font size=\"3\">Do you want to cancel Faxing this PO: "+po+"</font> \n");
     Msg.append( "<hr align=\"left\" width=\"400\">\n");
     Msg.append( "<form method=\"POST\" action=\"/tcmIS/servlet/radian.web.servlets.pos.CancelFaxpo?\">\n");
     Msg.append( "<input type=\"hidden\" name=\"po\" value=\"" + po+ "\">\n");
     Msg.append( "<input type=\"hidden\" name=\"Action\" value=\"Cancel\">\n");
     Msg.append( "<input type=\"submit\" name=\"Cancel\" value=\"Yes\"></td>\n");
     Msg.append( "</form>\n");
     Msg.append( "</body>\n");
     Msg.append( "</html>\n");
     out.println (Msg);
     }

     out.close();
    }

  public void PrintError(PrintWriter out, String testadd)
  {
   String errormsg = "";
   errormsg += testadd + "<P>ERROR!! Go Back and Please Try Again </P>";
   errormsg +="<P>Thanks.</P></FONT></CENTER></BODY></HTML>";
   errormsgH += errormsg;
   out.println(errormsgH);
  }

  /**Process the HTTP Post request*/
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
      doGet (request, response);
    }
}