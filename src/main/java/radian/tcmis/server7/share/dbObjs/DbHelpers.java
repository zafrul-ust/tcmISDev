package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import java.sql.*;
import radian.tcmis.server7.share.db.*;

public class DbHelpers {
  public static int countQuery(TcmISDBModel db,String table, String where) throws Exception{
    String query = "select count(*) from " +table+ " " +where;
    return countQuery(db,query);
  }
  public static int countQuery(TcmISDBModel db,String query) throws Exception{
      DBResultSet dbrs = null;
      ResultSet rs = null;
      int myCount = 0;
      try {
         dbrs = db.doQuery(query);
         rs=dbrs.getResultSet();
         while (rs.next()){
           myCount = rs.getInt(1);
         }

      } catch (Exception e) {
      e.printStackTrace();
      throw e;
     } finally{
         dbrs.close();
       }
      return myCount;
  }
  public static String getNowDate(TcmISDBModel db)  throws Exception {
     String query = "select to_char(sysdate,'MM/dd/yyyy') from dual";
     DBResultSet dbrs = null;
      ResultSet rs = null;
     String next = new String("");
     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()) {
         next = rs.getString(1);
       }


      } catch (Exception e) {
       e.printStackTrace();
         throw e;
     } finally{
         dbrs.close();
       }
     return next;
   }

   public static int getNextVal(TcmISDBModel db,String sequence)  throws Exception {
     String query = "select "+sequence+".nextval from dual";
     DBResultSet dbrs = null;
     ResultSet rs = null;
     int next = 0;
     try {
       dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
       while (rs.next()) {
         next = (int)rs.getInt(1);
       }
     } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object(DbHelpers.getNextVal()): " + e.getMessage(),null);
       throw e;
     } finally{
       dbrs.close();
     }
     return next;
   }
}
