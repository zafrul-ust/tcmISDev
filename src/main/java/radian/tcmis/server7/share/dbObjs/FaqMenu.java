package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import java.util.*;
import java.sql.*;
import radian.tcmis.server7.share.db.*;

public class FaqMenu{
   protected TcmISDBModel db;
   protected String solution = "";
   protected String sol_count = null;

//Get methods
 public String getSolution(){return solution;}
 public String getSolCount(){return sol_count;}

//Set methods
 public void setSolution(String x){solution += x;}
 public void setSolCount(String x){sol_count = x;}

 //Constructor
   public FaqMenu(TcmISDBModel db){
      this.db = db;
   }
   public FaqMenu(){
   }
   public void setDbModel(TcmISDBModel db){
     this.db = db;
   }

//Query the database and return values
  public static Vector getFaq(TcmISDBModel db, String session)throws Exception{

     Vector v = new Vector();
     int count = 0;

     String query = "";
 try{
//*********************************************************//
     if (session.equalsIgnoreCase("1")){
       query = "select 'Y' result_exists from dual where exists (select 1 from faq_view)";
     }
//*********************************************************//
     else{
       query = "select * from faq_view";
     }
//*********************************************************//
       }catch(Exception ex){ex.printStackTrace();}

     DBResultSet dbrs = null;
     ResultSet rs = null;
     Hashtable result = new Hashtable();
     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();

       while (rs.next()){
         FaqMenu fm = new FaqMenu(db);
         count ++;
         if (session.equalsIgnoreCase("1")){
         fm.setSolCount(rs.getString("result_exists"));
         }else{
         String bg = "";
         String br = "";
         if (count%2==0){
         bg = "\"#cfcfcf\"";
         }else{
         bg = "\"#ffffff\"";
         br = "<BR><BR>";
         }
         String sset = "<tr><td bgcolor=" + bg + ">" + br + "<P><font face=\"Arial\" size=2 color=\"#000066\"><b>" + rs.getString("question") +
         "\n</font></b><BR><font face=\"Arial\" size=2 color=000000>" + rs.getString("answer") + "</font>" + br + "</td></tr>\n";
         fm.setSolution(sset);
         }
         v.addElement(fm);
      }
     }catch (Exception e) {
       e.printStackTrace();
       HelpObjs.monitor(1,"Error object(SideView): " + e.getMessage(),null);
       throw e;
     }finally{
       dbrs.close();
     }
     return v;
   }

public static Vector getQuestions(TcmISDBModel db, String session)throws Exception{

     Vector v = new Vector();
     int count = 0;

     String query = "";
 try{
//*********************************************************//
     if (session.equalsIgnoreCase("1")){
       query = "select 'Y' result_exists from dual where exists (select 1 from faq_view)";
     }
//*********************************************************//
     else{
       query = "select * from faq_view";
     }
//*********************************************************//
       }catch(Exception ex){ex.printStackTrace();}

     DBResultSet dbrs = null;
     ResultSet rs = null;
     Hashtable result = new Hashtable();
     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();

       while (rs.next()){
         FaqMenu fm = new FaqMenu(db);
         count ++;
         if (session.equalsIgnoreCase("1")){
         fm.setSolCount(rs.getString("result_exists"));
         }
         else
         {
         String bg = "";
         String br = "";
         if (count%2==0){
         bg = "\"#cfcfcf\"";
         }else{
         bg = "\"#ffffff\"";
         br = "<BR><BR>";
         }
         //String sset = "<tr><td bgcolor=" + bg + ">" + br + "<P><font face=\"Arial\" size=2 color=\"#000066\"><b>" + rs.getString("question");

         String sset = "";
         //String sset = "<tr><td bgcolor=" + bg + ">" + br + "<P><font face=\"Arial\" size=2 color=\"#000066\"><b>" + rs.getString("question");
         sset = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src=\"https://www.tcmis.com/images/buttons/menu/dot.gif\" border=0>&nbsp;\n";
         sset += "<a href=\"/tcmIS/servlet/radian.web.servlets.help.SeagatehelpMenu?SessionID=20&qid="+rs.getString("FAQ_ID")+"\" target=\"content\" onMouseOver=\"window.status='Help -> Frequently Asked Questions -> Answers ';return true\"";
         sset += "onMouseOut=\"window.status='Help ->Frequently Asked Questions';return true\"><font color=\"#990000\">"+rs.getString("question")+"</font></a><BR>\n";

         //"\n</font></b><BR><font face=\"Arial\" size=2 color=000000>" + rs.getString("answer") + "</font>" + br + "</td></tr>\n";
         fm.setSolution(sset);
         }
         v.addElement(fm);
      }
     }catch (Exception e) {
       e.printStackTrace();
       HelpObjs.monitor(1,"Error object(SideView): " + e.getMessage(),null);
       throw e;
     }finally{
       dbrs.close();
     }
     return v;
   }

   public static StringBuffer getAnswer(TcmISDBModel db, String questionid) throws Exception
   {

   StringBuffer Msg12 = new StringBuffer();
   int count = 0;

   String query = "";

   query = "select * from faq_view where FAQ_ID ="+questionid+"";

     DBResultSet dbrs = null;
     ResultSet rs = null;
     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();

       while (rs.next())
       {
         Msg12.append("<P><font face=\"Arial\" size=3 color=\"#000066\"><b>" + rs.getString("question") +"\n</font></b><BR><BR>");
         Msg12.append("<font face=\"Arial\" size=3 color=000000>" + rs.getString("answer") + "</font></P>\n");
      }
     }catch (Exception e) {
       e.printStackTrace();
       HelpObjs.monitor(1,"Error object(SideView): " + e.getMessage(),null);
       throw e;
     }finally{
       dbrs.close();
     }
     return Msg12;

   }
}
