package radian.web.servlets.pos;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import radian.tcmis.both1.helpers.BothHelpObjs;

public class purch_order_4 extends HttpServlet
  {
    public void init(ServletConfig config) throws ServletException
      {
        super.init(config);
      }
      String errormsgH = "";
    /**Process the HTTP Get request*/
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
      {
        response.setContentType("text/html; charset=ISO-8859-1");
        HttpSession session = request.getSession();
        PrintWriter out = response.getWriter();
        String po = "";
        String order_taken_by = "";
        String vendor_name = "";
        String vendor_fax  = "";
        String buyer_name = "";
        String buyer_email  = "";
        String buyer_phone  = "";
        String buyer_fax  = "";
        String reference = "";
        String urgent = "";
        String file = "";
        String filetoFind = "";
        String Comments = "";
        String FinalComments = "";
        String wheretolook = "";
        String confirmingpo = "";

        errormsgH = "";

        errormsgH = "<HTML><HEAD><TITLE>ERROR OCCURED</TITLE></HEAD>";
        errormsgH +="<BODY text=\"#000000\" link=\"#0000FF\" vlink= \"#FF00FF\"><FONT FACE=\"Arial\"><CENTER>\n";

        boolean faxForm = true;
        boolean pdfFile = true;
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
            Comments = new String(BothHelpObjs.makeBlankFromNull(request.getParameter("Comments")));
            wheretolook = new String(BothHelpObjs.makeBlankFromNull(request.getParameter("wheretolook")));
            confirmingpo = new String(BothHelpObjs.makeBlankFromNull(request.getParameter("confirmingpo")));

            vendor_fax = vendor_fax.substring(0,14);

            String test = null;
            System.out.println("po: " + po + "\n");
            System.out.println("order_taken_by: " + order_taken_by + "\n");
            System.out.println("vendor_name: " + vendor_name + "\n");
            System.out.println("vendor_fax: " + vendor_fax + "\n");
            System.out.println("buyer_name: " + buyer_name + "\n");
            System.out.println("buyer_email: " + buyer_email + "\n");
            System.out.println("buyer_phone: " + buyer_phone + "\n");
            System.out.println("buyer_phone: " + buyer_fax + "\n");
            System.out.println("reference: " + reference + "\n");
            System.out.println("Comments: " + Comments + "\n");

            //test for blanks
            if (po == null || po.trim().equals(""))
              {
                System.out.println("One of the fields is blank");
                System.out.println("po: " + po + "\n");
                faxForm = false;
                System.out.println("faxForm: " + faxForm + "\n");
              }
            else if (order_taken_by == null || order_taken_by.trim().equals(""))
              {
                System.out.println("One of the fields is blank");
                System.out.println("order_taken_by: " + order_taken_by + "\n");
                faxForm = false;
                System.out.println("faxForm: " + faxForm + "\n");
              }
            else if (vendor_name == null || vendor_name.trim().equals(""))
              {
                System.out.println("One of the fields is blank");
                faxForm = false;
                System.out.println("vendor_name: " + vendor_name + "\n");
                System.out.println("faxForm: " + faxForm + "\n");
              }
            else if (vendor_fax == null || vendor_fax.trim().equals(""))
              {
                System.out.println("One of the fields is blank");
                faxForm = false;
                System.out.println("vendor_fax: " + vendor_fax + "\n");
                System.out.println("faxForm: " + faxForm + "\n");
              }
            else if (buyer_name == null || buyer_name.trim().equals(""))
              {
                System.out.println("One of the fields is blank");
                faxForm = false;
                System.out.println("buyer_name: " + buyer_name + "\n");
                System.out.println("faxForm: " + faxForm + "\n");
              }
            else if (buyer_email == null || buyer_email.trim().equals(""))
              {
                System.out.println("One of the fields is blank");
                faxForm = false;
                System.out.println("buyer_email: " + buyer_email + "\n");
                System.out.println("faxForm: " + faxForm + "\n");
              }
            else if (buyer_phone == null || buyer_phone.trim().equals(""))
              {
                System.out.println("One of the fields is blank");
                faxForm = false;
                System.out.println("buyer_phone: " + buyer_phone + "\n");
                System.out.println("faxForm: " + faxForm + "\n");
              }
            else if (buyer_fax == null || buyer_fax.trim().equals(""))
            {
              System.out.println("The Buyer Fax fields is blank");
              faxForm = false;
              System.out.println("buyer_fax: " + buyer_fax + "\n");
              System.out.println("faxForm: " + faxForm + "\n");
            }
            else if (reference == null || reference.trim().equals(""))
              {
                System.out.println("One of the fields is blank");
                faxForm = false;
                System.out.println("reference: " + reference + "\n");
                System.out.println("faxForm: " + faxForm + "\n");
              }
            else if (Comments == null || Comments.trim().equals(""))
              {
                System.out.println("Comments field is blank");
                Comments = " ";
              }

           if (confirmingpo.equalsIgnoreCase("Yes"))
           {
              FinalComments = " Confirming PO" + Comments;
           }

        }
        catch(NullPointerException npe)
         {
           faxForm = false;
           System.out.println("faxForm: " + faxForm + "\n");
         }
        catch(NumberFormatException NFE)
         {
           faxForm = false;
           System.out.println("faxForm: " + faxForm + "\n");
         }
        try
          {
            urgent = new String(request.getParameter("urgent"));
          }
        catch(NullPointerException e)
          {
          }

        System.out.println("Where to Look:  "+wheretolook);


        if (wheretolook.equalsIgnoreCase("Backup"))
        {
        file = "/webdata/html/tmpPO/Backup/po_"+ po;
        }
        else
        {
        file = "/webdata/html/tmpPO/po_"+ po;
        }
        //file = "/export2/faxtemp0/po_"+ po;
        System.out.println("file: " + file + "\n");
        filetoFind = new String(file + ".pdf");
        System.out.println("filetoFind: " + filetoFind + "\n");

        //locate pdf file
        try
          {
            filetoFind = new String(file + ".pdf");
            //System.out.println("filetoFind" + filetoFind + "\n");
            File findFile = new File(filetoFind);
            if (findFile.isFile())
            {
             System.out.println("Found The File" + filetoFind + "\n");
            }
          }
        catch (SecurityException se)
          {
            pdfFile = false;
            System.out.println("pdfFile: " + pdfFile + "\n");
            PrintError(out,"File Not Found");
            return;
          }
        catch (NullPointerException npe)
          {
            pdfFile = false;
            System.out.println("pdfFile: " + pdfFile + " \n");
            System.out.println("pdfFile: " + filetoFind + " not found\n");
            PrintError(out,"File Not Found");
            return;
          }
        catch(Exception sqle)
         {
            sqle.printStackTrace();
            PrintError(out,"PDF File Not Found");
            return;
         }


        //encode redirected URL to maintain session
        //System.out.println("Redirecting URL\n");

        //start the html page
        //System.out.println("Starting html\n");
        out.println("<html>\n");
        out.println("<head><title>Purchase Order Number Info</title></head>\n");
        out.println("<body text=\"#000000\" link=\"#0000FF\" vlink= \"#FF00FF\">\n");
        //System.out.println("Finished starting html\n");

        //if all parameters are entered for fax form and pdf file is found, generate the ctx file and continue with faxing
        //System.out.println ("Starting checks.\n");
        if ((faxForm)&&(pdfFile))
          {
            //System.out.println("Found complete faxform and pdf file\n");
            try
              {
                File outFile = new File(file+".ctx");
	        FileOutputStream DBO = new FileOutputStream(outFile);
                String dbostr = "To : "+order_taken_by+"\n";
                dbostr += "Company : "+vendor_name+"\n";
                dbostr += "Fax Number : "+vendor_fax+"\n";
                dbostr += "From : "+buyer_name+"\n";
                dbostr += "Buyer phone : "+buyer_phone+"\n";
                dbostr += "Buyer fax : "+buyer_fax+"\n";
                dbostr += "Buyer email : "+buyer_email+"\n";
                dbostr += "Re : "+reference+"\n";
                dbostr += "Comments :  "+FinalComments+"\n";
                dbostr += "PO Number Test : "+po+"\n";
                //dbostr += "Urgent : "+urgent+"\n";
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

            String ftpCmd1 = "";

            if (wheretolook.equalsIgnoreCase("Backup"))
            {
            //ftpCmd1 = "/home/servlet/radian/web/servlets/pos/ftpPOBack.sh";
            ftpCmd1 = "/webdata/html/tmpPO/pos/ftpPOBack.sh";
            }
            else
            {
            //ftpCmd1 = "/home/servlet/radian/web/servlets/pos/ftpPO.sh";
            ftpCmd1 = "/webdata/html/tmpPO/pos/ftpPO.sh";
            }

            String[] ftpCmd = {"/bin/sh", ftpCmd1, file};
            //String[] ftpCmd = {"/bin/sh",ftpCmd1, file};

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
	            out.println(errStr);
	          }
              System.out.println("\n\nDone Doing the Shell Script");
              }
            catch (java.io.IOException e)
              {
	        System.out.println(e);
                PrintError(out,"Error While Running the FTP Script");
                return;
	      }

            out.println (" <FONT size=\"2\"><BR><BR><BR> This fax has been sent to the fax server.\n");
            out.println ("You will receive an e-mail confirming the successful faxing of your\n");
            out.println ("request or a notice stating that this fax did not go through.  \n");
            out.println (" You may be required to refax your document.</FONT>\n");
          }
        else
          {
            //System.out.println("Did not run ctx.\n");
          }
        //if the fax form is not complete, print a message
        if (!faxForm)
          {
            //System.out.println("Faxform was not complete.\n");
            out.println (" <FONT size=\"4\" COLOR=\"#ff0000\"><B>Please complete all of the data fields on the fax form.</B></FONT>\n");
          }

        //if the pdf file has not been generated, print a message
        if (!pdfFile)
          {
            //System.out.println("Did not find pdf file\n");
            out.println (" <FONT size=\"4\" COLOR=\"#ff0000\"><B>Please be sure you generate the pdf file before faxing.</B></FONT>\n");
          }

        //finish html
        //System.out.println("Finishing html.\n");

        out.print ("<P></P>\n");
        out.println (" <FORM method=\"POST\" action=\"/tcmIS/servlet/radian.web.servlets.pos.faxlogin?Session=2&po="+po+"&buyerfrompo="+buyer_name+"\">\n");
        //out.println (" <FORM method=\"GET\" action=\"/" + formURL + "\">\n");
        out.println ("<CENTER><INPUT type=\"submit\" name=\"New_Po\" value=\"Return to PDF\"></center>\n");
        out.println ("</FORM></BODY></HTML>\n");
        //System.out.println("Finished html.\n");
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


    /**Clean up resources*/
    public void destroy()
      {
      }
  }
