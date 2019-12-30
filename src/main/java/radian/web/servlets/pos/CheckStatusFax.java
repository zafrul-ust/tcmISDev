
/**
 * Title:        Fax Purchase Orders
 * Description:  This class searches for files in the temp/sent directory where I upload the CTX files
 *                after being sent out. The ctx files has a line which tells me if the fax was sent successfully fo failed.
 *                I search all the files for this line and update the database with the information if the fax was sent or
 *                did it fail.
 *
 * Copyright:    Copyright (c) 2001
 * Company:      URS/Radian Corporation
 * @author:      Nawaz Shaik
 * @version 1.0
 */
package radian.web.servlets.pos;

import java.io.*;
import java.util.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

//public class CheckStatusFax implements Runnable
public class CheckStatusFax extends HttpServlet
{
    //private Thread CheckThread = null;

    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException
    {
        doPost(request,response);
    }

    public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException
    {
    //public static void main(String[] args )

        File file1  = new File("/webdata/html/tmpPO/temp");
        Vector v = FileSearch.getDirectoryContent(file1,false);
        System.out.println ("Start No of Files :"+v.size());
        for ( int j = 0; j <= v.size()-1 ; j++)
        {
            String str1 = (v.elementAt(j).toString());
            //System.out.println (str1);
            readPOContext("/webdata/html/tmpPO/temp/"+str1+"");
        }
        //System.out.println ("End");

        String[] confirmCmd1 =
         {"/bin/csh","-c", "/webdata/html/tmpPO/pos/CheckFaxStatus.sh"};

        try
        {
           // A Runtime object to execute shell commands
           Runtime rt = Runtime.getRuntime();
           // Print out the move command for information only
           //System.out.println (confirmCmd1[2]);
           // Send email notification and delete the job
           Process p = rt.exec(confirmCmd1);
        }
        catch (java.io.IOException e)
        {
           System.out.println(e);
        }
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

   String toStr = "";
   String senderFaxStr = "";
   String commentStr = "";
   String poNumStr = "";
   String jobIdStr = "";
   String timeSubmittedStr = "";
   String coStr = "";
   String faxStr = "";
   String fromStr = "";
   String senderPhoneStr = "";
   String emailStr = "";
   String inreStr = "";
   String SentStatus = "";

      try
      {
         // BufferedReader for the context file
         BufferedReader ctx;
         ctx = new BufferedReader(new FileReader (newOld));

         //DOS version
         //ctx= new BufferedReader(new FileReader ("D:\\temp\\faxpo\\"+newOld+""));

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
            //System.out.println ("COMPANY string empty: substituting");
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
            //System.out.println ("FAX string empty: aborting");
            //System.out.println (e);
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
            //System.out.println ("FROM string empty: substituting");
            fromStr = new String ("TCM Purchasing");
         }
         System.out.println (fromStr);

         s = ctx.readLine();
         try
         {
            senderPhoneStr = getValueString(s, 2);
         }
         catch (NoSuchElementException e)
         {
            //System.out.println ("SENDER PHONE string empty: substituting");
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
            //System.out.println ("SENDER FAX string empty: substituting");
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
            //System.out.println ("EMAIL string empty: substituting");
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
            //System.out.println ("INRE string empty: substituting");
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
            //System.out.println ("COMMENT string empty: substituting");
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
            //System.out.println ("PONUM string empty: substituting");
            poNumStr = new String ("999999");
         }
         //System.out.println (poNumStr);

         s = ctx.readLine(); s = ctx.readLine();s = ctx.readLine();
         if (s ==null)
         {
         s = "NONE";
         }

         if (s.startsWith("Sent Status"))
         {
          SentStatus = getValueString(s, 2);
         }

         System.out.println ("Sent Status    :"+SentStatus+" PO : "+poNumStr);

      ctx.close();
      }
      catch (FileNotFoundException e)
      {
         //System.out.println (e);
      }
      catch (java.io.IOException e)
      {
         //System.out.println (e);
      }


      if (SentStatus.equalsIgnoreCase("SUCCESSFULL"))
      {
           //System.out.println ("Fax Sent Succesfully Update Records in the Database");
           Connection connection = null;
           try
           {
           Class.forName("oracle.jdbc.driver.OracleDriver");
           connection = DriverManager.getConnection("jdbc:oracle:thin:@owl.tcmis.com:1521:owlprod", "tcm_ops", "n0s3ha1r");
           Statement statement = connection.createStatement();
            //values ('Nawasz','nawas2s','Jack','Jisdll',1-512-419-6955,'551113')

           String stmt  = "insert into fax_po_history  (BUYER_NAME,EMAIL_ID,SENT_TO_NAME,SENT_TO_COMPANY,SENT_TO_FAX_NO,PO_NUMBER) ";
           stmt += "values ('"+fromStr+"','"+emailStr+"','"+toStr+"','"+coStr+"','"+faxStr+"',"+poNumStr+")";
           //System.out.println ("stmt:" + stmt);
           statement.executeQuery(stmt);
           connection.close();
           }
           catch(ClassNotFoundException cnfe)
          {
              //System.out.println ("Could Not Update the Database");
              cnfe.printStackTrace();
              return;
          }
          catch(SQLException sqle)
          {
              //System.out.println ("Could Not Update the Database");
              sqle.printStackTrace();
              return;
          }
          finally
          {

          }
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

      StringTokenizer parser = new StringTokenizer(s);

      // skip over the key tokens and colon
      for (int keyTok=0; keyTok<=keyCount; keyTok++)
      {
         parser.nextToken();
      }

      String valPart = null;
      String valFull = new String("");

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
      return valFull;
   }

}

