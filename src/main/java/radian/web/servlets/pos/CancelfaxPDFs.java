package radian.web.servlets.pos;

import java.util.*;
import java.io.*;
import sun.net.smtp.SmtpClient;
/**
 * This application faxes confirming POs to TCM vendors. It should be run
 * periodically (e.g. every 10 min) as a cron job. Whenever it runs, it looks
 * in the "newFaxes" directory for context files named <jobName>.ctx. Each
 * context file contains all parameters necessary to fax a single PO. The PO
 * itself is assumed to be in a PDF file named <jobName>.pdf in the newFaxes
 * directory. The application also parses the fax logs and sends confirmation
 * of the status of each fax to the originating buyer.
 *
 * @author Bill Forbes
 * @version 1.0
 * @see purch_order_info.cgi
 * @see purch_order_preview.cgi
 * @since JDK1.1.7-V3
 */
public class CancelfaxPDFs
{

   // job name
   private static String jobName = null;
   // HylaFax job id
   private static String jobId = null;
   // sender (buyer) name string
   private static String fromStr = null;
   // recipient name string
   private static String toStr = null;
   // recipient company name string
   private static String coStr = null;
   // recipient fax # string
   private static String faxStr = null;
   // "Regarding" string
   private static String inreStr = null;
   // "Comments" string
   private static String commentStr = null;
   // sender email address for confirmation
   private static String emailStr = null;
   // sender phone number
   private static String senderPhoneStr = null;
   // sender Fax number
   private static String senderFaxStr = null;
   // po number
   private static String poNumStr = null;
   // urgent notification designation
   private static String urgentStr = null;
   // Hylafax job id
   private static String jobIdStr = null;
   // time when the job was originally submitted (in milliseconds since the epoch)
   private static String timeSubmittedStr = null;

   //NewFile Flag
   private static boolean NewFaxFile = true;

   //Old File Not Found
   private static boolean inProgFileNotFound = false;

   private static BufferedReader stdInput;
   private static BufferedReader stdError;

   /**
    * The main method of the faxPDFs application. Looks for jobs in the newFaxes
    * directory (by calling getJobName) and sends each corresponding fax (by
    * calling sendFax).
    *
    * This application also checks for HylaFax logs and sends email notification
    * to the originator with the status of the fax job.
    *
    * @param args  Not used.
    */
   public static void main(String[] args)
   {

      String contents = "\n["+new Date()+"]\n";
      System.out.println(contents);

      getReaders ("Cancel");
      // For each old job name found...
      while (getJobName ("New"))
      {
         // send the confirmation (maybe)
         DeleteFax();
         NewFaxFile = true;
         inProgFileNotFound = false;
      }

      getReaders ("Cancel");
      while (getJobName ("Old"))
      {
         // send the confirmation (maybe)
         CancelFax();
         NewFaxFile = true;
         inProgFileNotFound = false;
      }
   }

   /**
    * This method executes a shell script which looks for files named *.ctx in
    * either the "newFaxes" or "inProgress" directory, and initializes a global
    * BufferedReader object from which the the file list may be read sequentially.
    *
    * @param newOld String with value "new" or "old" to designate whether to look for
    *                      files in the newFaxes directory or the inProgress directory
    */
   public static void getReaders(String newOld)
   {
      // The shell command string to list *.ctx files in the newFaxes directory
      String[] cmdNew =
         {
         "/bin/csh",
            "-c", "ls -1 /usr/local/faxPO/CancelFaxes/*.ctx"
      };

      try
      {
         // A runtime object for executing shell commands
         Runtime rt = Runtime.getRuntime();
         // A Process object for executing the specific shell command
         Process p;
         p = rt.exec(cmdNew);
         // A BufferedReader object to receive the stdout stream of the command
         stdInput = new BufferedReader(new
                                       InputStreamReader(p.getInputStream()));
         // A BufferedReader object to receive the stderr stream of the command
         stdError = new BufferedReader(new
                                       InputStreamReader(p.getErrorStream()));

         // Read any errors from the attempted command
         // print all errors to System.out
         String errStr = null;
         while ((errStr = stdError.readLine()) != null)
         {
            System.out.println(errStr);
         }
      }
      catch (java.io.IOException e)
      {
         System.out.println(e);
      }
   }

   /**
    * This method reads 1 line from the BufferedReader containing the list of
    * *.ctx files to parse and then parses the file (by calling readPOContext).
    * Parsed information is stored in the class's static global data members.
    *
    * @param newOld String with value "new" or "old" to designate whether to look for
    *                      files in the newFaxes directory or the inProgress directory
    * @return      <code>true</code> if a context file was found; <code>false</code>
    *                      if no context files found
    */
   public static boolean getJobName(String newOld)
   {
      // Buffer for a line of input from command stdin or the context file
      String s = null;
      // Error string buffer
      String errStr = null;

      try
      {
         // Read the next line of output from the command
         s = stdInput.readLine();
         // If input is null there are no context files to process
         if (s == null)
            return false;
         // Echo it for debug purposes
         System.out.println (s);

         // Strip path from filename
         StringTokenizer fn = new StringTokenizer(s, "/");
         try
         {
            // Parse out the last "/"-separated element of the StringTokenizer
            while (fn.hasMoreElements())
            {
               s = fn.nextToken();
            }
         }
         catch (NoSuchElementException e)
         {
            System.out.println (e);
         }

      }
      catch (java.io.IOException e)
      {
         System.out.println(e);
      }

      // Parse out the jobName
      StringTokenizer parser = new StringTokenizer(s, ".");
      // jobName is the name of the context file without the .ctx extension
      jobName = parser.nextToken();

      System.out.println ("Next " + newOld + " jobName is: " + jobName);

      // Make sure the file has time to be completely written
      try
      {
         // Sleep for 5 seconds
         Thread.sleep(5000);
      }
      catch (java.lang.InterruptedException e)
      {
         System.out.println(e);
      }

      // Read and parse the context file
      readPOContext(newOld);

      // All done - return true
      return true;
   }

   public static void DeleteFax()
   {
      if (NewFaxFile)
      {
        try
        {
           // The shell command to send Confirmation email
          String[] storeIdCmd =
             {
             "/bin/csh","-c", "/usr/local/faxPO/DeleteNewCancelFile.sh "+jobName+" "+emailStr+" PO_"+poNumStr+"_Fax_Status----:Deleted_from_Queue"
          };

         Runtime rt = Runtime.getRuntime();
         System.out.println (storeIdCmd[2]);
         Process p = rt.exec(storeIdCmd);

        }
        catch (java.io.IOException e321)
        {
           System.out.println(e321);
        }

        // Make sure the files have time to be completely removed
        try
        {
           // Sleep for 5 seconds
           Thread.sleep(5000);
        }
        catch (java.lang.InterruptedException e)
        {
           System.out.println(e);
        }
      }
   }


   /**
    * This routine sends a confirming email to the originators of the most recently
    * parsed job (*.ctx file) from the "inProgress" directory by invoking the shell
    * script sendConfirmation.sh. Before invoking that shell script, this routine
    * checks to see whether the timeout for the job has expired. For jobs marked
    * "urgent", the timeout is 10 minutes; for all other jobs, the timeout is 1
    * hour. The determination as to whether the timeout has expired is made by
    * comparing the current system time with the time that the job was submitted
    * (taken from the *.ctx file).
    *
    * The sendConfirmation.sh script collects all the HylaFax log entries and emails
    * them to the originator. Then it deletes the job from the HylaFax queue,
    * if it is still there.
    */
   public static void CancelFax()
   {
      if (inProgFileNotFound)
      {
        try
        {
           // The shell command to send Confirmation email
          String[] confirmCmd =
             {
             "/bin/csh","-c", "/usr/local/faxPO/DeleteCancelFile.sh " +jobName + ""
          };

          // A Runtime object to execute shell commands
         Runtime rt = Runtime.getRuntime();
         // Print out the move command for information only
         System.out.println (confirmCmd[2]);
         // Send email notification and delete the job
         Process p = rt.exec(confirmCmd);
        }
        catch (java.io.IOException e321)
        {
           System.out.println(e321);
        }

        // Make sure the files have time to be completely removed
        try
        {
           // Sleep for 5 seconds
           Thread.sleep(5000);
        }
        catch (java.lang.InterruptedException e21)
        {
           System.out.println(e21);
        }
        return;
      }

      // Buffer to hold the stderr stream from any of the shell commands
      String errStr = null;
      //Status String to be added to the email subject line
      String Statusoffax = "";

      int NoLinesCtx = 0;

      //creating a awk file from the fax logs and reading it to find if the fax has been sent or failed.
      String[] confirmCmd1 =
         {
         "/bin/csh",
            "-c", "/usr/local/faxPO/createTestfile.sh " +
            jobIdStr + " " + jobName + ""
      };

      try
      {
         // A Runtime object to execute shell commands
         Runtime rt = Runtime.getRuntime();
         // Print out the move command for information only
         System.out.println (confirmCmd1[2]);
         // Send email notification and delete the job
         Process p = rt.exec(confirmCmd1);
      }
      catch (java.io.IOException e)
      {
         System.out.println(e);
      }

      try
      {
         // Sleep for 5 seconds
         Thread.sleep(10000);
      }
      catch (java.lang.InterruptedException e)
      {
         System.out.println(e);
      }

      BufferedReader ctx;
      // Read and check each line of the email file
      try
      {
         // BufferedReader for the context file
         ctx = new BufferedReader(new FileReader ("/usr/local/faxPO/inProgress/"+jobName+".mail"));

         Statusoffax = "SEND_FAILED";
         System.out.println("This is the CTX file Name "+jobName+"");

         while(ctx.ready())
         {
           String reeer = ctx.readLine();
           if (reeer.length() > 0)
           {
             NoLinesCtx = NoLinesCtx + 1;
             int test = reeer.indexOf("FAILED");
             System.out.println("New: "+reeer+" Index "+test);
             if (test > 0)
             {
               Statusoffax = "SEND_FAILED";
             }
             else
             {
               Statusoffax = "SUCCESSFULL";
             }
           }
           else
           {
              System.out.println("Null in .mail file");
           }
         }
        ctx.close();
       }
       catch (java.io.IOException e)
       {
         System.out.println(e);
         String[]recipients = new String[]{"nawaz_shaik@haastcm.com"};
         javaSendMail("tcmIS@haastcm.com","Fax Po Error",recipients,"Error occured while running Fax Po Send Confirmation for po "+jobName+"");
         System.out.println("Error not good Stops here");
         return;
       }
       finally
       {

       }

       System.out.println(Statusoffax+"\n");

    /*
    * This routine appends the Status of the fax to the context file This is accomplished by invoking
    * the "echo" shell command.
    */

      String errStr1 = null;
      String timeInMillis = String.valueOf (System.currentTimeMillis());

      System.out.println("No Of Lines :"+NoLinesCtx);
      try
      {

          if (NoLinesCtx == 0)
          {
                String[] storeIdCmd =
                {
                   "/bin/csh", "-c", "echo Sent Status : "+Statusoffax+
                      " >> /usr/local/faxPO/inProgress/" + jobName + ".ctx"
                };

                try
                {
                   Runtime rt = Runtime.getRuntime();
                   System.out.println (storeIdCmd[2]);
                   Process p = rt.exec(storeIdCmd);
                }
                catch (java.io.IOException e)
                {
                   System.out.println(e);
                }

                 // The shell command to send Confirmation email
                String[] confirmCmd =
                   {
                   "/bin/csh",
                      "-c", "/usr/local/faxPO/CancelEmail.sh " +
                      jobIdStr + " " + jobName + " " + emailStr + " PO_"+poNumStr+"_Fax_Status----PARTIAL:Can_not_Confirm,_Deleting_from_Queue"
                };

                // A Runtime object to execute shell commands
               Runtime rt = Runtime.getRuntime();
               // Print out the move command for information only
               System.out.println (confirmCmd[2]);
               // Send email notification and delete the job
               Process p = rt.exec(confirmCmd);
          }
          else
          {
           // The shell command to send Confirmation email

            String[] storeIdCmd =
            {
               "/bin/csh", "-c", "echo Sent Status : "+Statusoffax+
                  " >> /usr/local/faxPO/inProgress/" + jobName + ".ctx"
            };

            try
            {
               Runtime rt = Runtime.getRuntime();
               System.out.println (storeIdCmd[2]);
               Process p = rt.exec(storeIdCmd);
            }
            catch (java.io.IOException e)
            {
               System.out.println(e);
            }

            String[] confirmCmd =
               {
               "/bin/csh",
                  "-c", "/usr/local/faxPO/CancelEmail.sh " +
                  jobIdStr + " " + jobName + " " + emailStr + " PO_"+poNumStr+"_Fax_Status-----:PARTIAL_"+Statusoffax+"_Deleting_from_Queue"
            };

             // A Runtime object to execute shell commands
             Runtime rt = Runtime.getRuntime();
             // Print out the move command for information only
             System.out.println (confirmCmd[2]);
             // Send email notification and delete the job
             Process p = rt.exec(confirmCmd);
          }
      }
      catch (java.io.IOException e)
      {
         System.out.println(e);
      }

      // Make sure the files have time to be completely removed
      try
      {
         // Sleep for 5 seconds
         Thread.sleep(5000);
      }
      catch (java.lang.InterruptedException e)
      {
         System.out.println(e);
      }
      System.out.println("\n\n");
   }


   /**
    * This routine parses the context file and stores the information in static
    * global variables
    *
    * Format of the context file is:
    *      To : Bozo the Clown (ext 1400)
    *      Company : MegaCorp of Tulsa, Oklahoma
    *      Fax Number : 419-6991
    *      From : Barry Manilow
    *      Buyer phone : (512)419-6100
    *      Buyer fax : (512)419-6991
    *      Buyer email : Barry_Manilow@haastcm.com
    *      Re : What it's in reference to...
    *      Comments : Just a single line of comments?
    *      PO : 529544
    *      Urgent : yes
    *  Job ID : 164
    *  Submitted : 123456789
    *
    * Note that the last 2 lines (job id and submitted time) are only present in
    * "old" files (that is, files in the inProgress directory)
    *
    * @param newOld String with value "new" or "old" to designate whether to look for
    *                      files in the newFaxes directory or the inProgress directory
    */
   public static void readPOContext(String newOld)
   {

      try
      {
         // BufferedReader for the context file
         BufferedReader ctx;
         if (newOld.equals ("New"))
         {
            ctx = new BufferedReader(
                                     new FileReader ("/usr/local/faxPO/newFaxes/" + jobName + ".ctx"));
         }

         else
         {
            ctx = new BufferedReader(
                                     new FileReader ("/usr/local/faxPO/inProgress/" + jobName + ".ctx"));
         }
                                        /*
                                                DOS version

                                        new FileReader ("d:\\projects\\tcm\\fax\\newFaxes\\" + jobName + ".ctx"));
                                         */

         // Read and parse each line of the context file

         String s = ctx.readLine();
         try
         {
            toStr = getValueString(s, 1);
         }
         catch (NoSuchElementException e)
         {
            System.out.println ("TO string empty: substituting");
            toStr = new String ("Order Processing");
         }
         //System.out.println (toStr);

         s = ctx.readLine();
         try
         {
            coStr = getValueString(s, 1);
         }
         catch (NoSuchElementException e)
         {
            System.out.println ("COMPANY string empty: substituting");
            coStr = new String (" ");
         }
         //System.out.println (coStr);

         s = ctx.readLine();
         try
         {
            faxStr = getValueString(s, 2);
         }
         catch (NoSuchElementException e)
         {
            System.out.println ("FAX string empty: aborting");
            System.out.println (e);
            return;
         }
         //System.out.println (faxStr);

         s = ctx.readLine();
         try
         {
            fromStr = getValueString(s, 1);
         }
         catch (NoSuchElementException e)
         {
            System.out.println ("FROM string empty: substituting");
            fromStr = new String ("TCM Purchasing");
         }
         //System.out.println (fromStr);

         s = ctx.readLine();
         try
         {
            senderPhoneStr = getValueString(s, 2);
         }
         catch (NoSuchElementException e)
         {
            System.out.println ("SENDER PHONE string empty: substituting");
            senderPhoneStr = new String (" ");
         }
         //System.out.println (senderPhoneStr);

         s = ctx.readLine();
         try
         {
            senderFaxStr = getValueString(s, 2);
         }
         catch (NoSuchElementException e)
         {
            System.out.println ("SENDER FAX string empty: substituting");
            senderFaxStr = new String (" ");
         }
         //System.out.println (senderFaxStr);

         s = ctx.readLine();
         try
         {
            emailStr = getValueString(s, 2);
         }
         catch (NoSuchElementException e)
         {
            System.out.println ("EMAIL string empty: substituting");
            emailStr = new String (" ");
         }
         //System.out.println (emailStr);

         s = ctx.readLine();
         try
         {
            inreStr = getValueString(s, 1);
         }
         catch (NoSuchElementException e)
         {
            System.out.println ("INRE string empty: substituting");
            inreStr = new String (" ");
         }
         //System.out.println (inreStr);

         s = ctx.readLine();
         try
         {
            commentStr ="Sender Fax "+senderFaxStr+" ";
            commentStr += getValueString(s, 1);
         }
         catch (NoSuchElementException e)
         {
            System.out.println ("COMMENT string empty: substituting");
            commentStr = new String (" ");
            commentStr += "Sender Fax "+senderFaxStr+"  ";
         }

         String stopTest = "";
         s = ctx.readLine();

         if (s.startsWith("PO Number Test :"))
         {
          stopTest = "Stop";
         }
         else
         {
         commentStr += s;
         s = ctx.readLine();
         //System.out.println ("Test"+s);

         while (!(s.startsWith("PO Number Test :")))
         {
          commentStr += s;
          s = ctx.readLine();
         }
         }

         //System.out.println (commentStr);

         try
         {
            poNumStr = getValueString(s, 3);
         }
         catch (NoSuchElementException e)
         {
            System.out.println ("PONUM string empty: substituting");
            poNumStr = new String ("999999");
         }
         System.out.println ("PO : "+poNumStr);

         //s = ctx.readLine();
         /*try
         {
            urgentStr = getValueString(s, 1);
         }
         catch (NoSuchElementException e)
         {
            System.out.println ("URGENT string empty: substituting");
            urgentStr = new String ("no");
         }
         System.out.println (urgentStr);*/

         if (newOld.equals ("Old"))
         {
            s = ctx.readLine();
            try
            {
               System.out.println ("This is the String being sent to getValue: " +s);
               jobIdStr = getValueString(s, 2);
            }
            catch (NoSuchElementException e)
            {
               System.out.println (e);
            }
            //System.out.println (jobIdStr);

            s = ctx.readLine();
            try
            {
               //System.out.println ("This is the String being sent to getValue: " +s);
               timeSubmittedStr = getValueString(s, 1);
            }
            catch (NoSuchElementException e)
            {
               System.out.println (e);
            }
            //System.out.println (timeSubmittedStr);
         }
      ctx.close();
      }
      catch (FileNotFoundException e)
      {
         System.out.println (e);

         NewFaxFile = false;
         if (!newOld.equals ("New"))
         {
         inProgFileNotFound =true;
         }
      }
      catch (java.io.IOException e)
      {
         System.out.println (e);
      }
   }

   /*
    * This routine appends the job id to the context file, which by this time
    * has been moved to the "inProgress" directory. This is accomplished by invoking
    * the "echo" shell command.
    */
   public static void storeContext ()
   {
      String errStr = null;
      String timeInMillis = String.valueOf (System.currentTimeMillis());
      String[] storeIdCmd =
         {
         "/bin/csh", "-c", "echo Job ID : " + jobId +
            " >> /usr/local/faxPO/inProgress/" + jobName + ".ctx"
      };
      String[] storeTimeCmd =
         {
         "/bin/csh",
            "-c", "echo Submitted : " + timeInMillis +
            " >> /usr/local/faxPO/inProgress/" + jobName + ".ctx"
      };

      try
      {

         Runtime rt = Runtime.getRuntime();
         System.out.println (storeIdCmd[2]);
         Process p = rt.exec(storeIdCmd);
         BufferedReader stdError = new BufferedReader(new
                                                      InputStreamReader(p.getErrorStream()));
         while ((errStr = stdError.readLine()) != null)
         {
            System.out.println(errStr);
         }
         p = rt.exec(storeTimeCmd);
         stdError = new BufferedReader(new
                                       InputStreamReader(p.getErrorStream()));
         while ((errStr = stdError.readLine()) != null)
         {
            System.out.println(errStr);
         }
      }
      catch (java.io.IOException e)
      {
         System.out.println(e);
      }
   }

   /**
    * This utility routine gets the value string from a single line
    * of the context file.
    *
    * @param s String containing the "keyword : value" string to be parsed
    * @param keyCount integer value containing the number of tokens in the keyword
    *                      (e.g. "Fax Number" has 2)
    *
    * @return A String containing the value
    */

   public static String getValueString(String s, int keyCount)
   {
      String valFull = new String("");
      try
      {
          StringTokenizer parser = new StringTokenizer(s);

          // skip over the key tokens and colon
          for (int keyTok=0; keyTok<=keyCount; keyTok++)
          {
             parser.nextToken();
          }

          String valPart = null;


          // get the first token in the value string
          valPart = parser.nextToken();
          valFull = valFull.concat(valPart);

          // get all the rest of the tokens in the value string
          while (parser.hasMoreElements())
          {
             valFull = valFull.concat(" ");
             valPart = parser.nextToken();
             valFull = valFull.concat(valPart);
          }
      }
      catch (Exception e)
      {
      String[]recipients = new String[]{"nawaz_shaik@haastcm.com"};
      javaSendMail("tcmIS@haastcm.com","Fax Po Error",recipients,"Error occured while running Fax Po Send Confirmation for po "+jobName+"");
      }
      return valFull;
   }

    //5-23-01 will put every email address on a list, it make it easier for debug
    public static void javaSendMail(String sender, String subject, String[] rec,String msg){
      PrintStream ps = null;
      SmtpClient sendmail = null;

      String from = (sender==null?"tcmIS@haastcm.com":sender);

      StringBuffer emailAddress = new StringBuffer();
      Vector recV = new Vector();
      for (int k = 0; k < rec.length; k++) {
        emailAddress.append(rec[k]);
        emailAddress.append(",");
      }
      emailAddress.append("sent-to-tcmis");

      /*testing purpose just print
      System.out.println("From: "+from);
      System.out.println("To: "+emailAddress.toString());
      System.out.println("Subject: "+subject);
      System.out.println(msg);   */

      try {
        // open smtp connection
        //sendmail = new SmtpClient("smtp101.haastcm.com");  // mail.ciptx.com
        sendmail = new SmtpClient("www.tcmis.com");
        //sendmail = new SmtpClient("condor.tcmis.com");
        sendmail.from(from);
        sendmail.to(emailAddress.toString());
        // get printstream
        ps = sendmail.startMessage();
      } catch (Exception e) {
        e.printStackTrace();
        // System.out.println("Error on sending mail");
        return;
      }

      try {
        // send headers.
        ps.println("From: "+from);
        ps.println("To: "+emailAddress.toString());
        ps.println("Subject: "+subject);
        ps.print("\r\n"); //header area delimiter

        // now send data to it
        ps.println(msg);
        ps.flush();
        ps.close();
        sendmail.closeServer();
      } catch (Exception e) {
        e.printStackTrace();
        // System.out.println("There was an error sending you mail.");
        return;
      }
    }

}

