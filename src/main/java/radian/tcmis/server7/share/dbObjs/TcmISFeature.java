package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;

import java.util.*;
import java.sql.*;
import radian.tcmis.server7.share.db.*;
public class TcmISFeature  {

   public static final int STRING = 0;
   public static final int INT = 1;
   public static final int DATE = 2;

   protected TcmISDBModel db;
   protected String feature;
   protected String featureMode;

   public TcmISFeature()  throws java.rmi.RemoteException {

   }

   public TcmISFeature(TcmISDBModel  db)  throws java.rmi.RemoteException {
     this.db = db;
   }

   public void setDB(TcmISDBModel  db) {
     this.db = db;
   }

   public void setFeature(String s) {
     this.feature = s;
   }

   public String getFeature() {
     return this.feature;
   }

   public void setFeatureMode(String s) {
     this.featureMode = s;
   }

   public String getFeatureMode() {
     return this.featureMode;
   }

  public Hashtable getTcmISMainFeatures() throws Exception {
    Hashtable h = new Hashtable();
    DBResultSet dbrs = null;
    ResultSet rs = null;
    String query = "select feature,feature_mode from tcmis_feature where feature like 'main.%'";
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while(rs.next()) {
        h.put(rs.getString("feature"),rs.getString("feature_mode"));
      }
    }catch (Exception ex) {
      ex.printStackTrace();
    }finally {
      dbrs.close();
    }
    return h;
  }

  //now get report year and num of year display
  public Hashtable getYearOfReports() throws Exception {
    Hashtable result = new Hashtable(2);
    DBResultSet dbrs = null;
    ResultSet rs = null;
    //String query = "select constant,value from tcmis_constant where constant like 'Report.%'";
    String query = "select constant,value from tcmis_constant where constant like 'Report.%'";
    Integer numOfYear = null;
    Integer yearOfReport = null;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      Calendar cal = Calendar.getInstance();
      while (rs.next()) {
        String tmp = rs.getString("constant");
        if ("Report.Year".equalsIgnoreCase(tmp)) {
          yearOfReport = new Integer(rs.getInt("value") - cal.get(Calendar.YEAR));
        }else if ("Report.NumOfYearDisplay".equalsIgnoreCase(tmp)) {
          numOfYear = new Integer(rs.getInt("value"));
        }
      }
      if (yearOfReport == null) {
        yearOfReport = new Integer(1998 - cal.get(Calendar.YEAR));
      }
      if (numOfYear == null) {
        numOfYear = new Integer(7);
      }
    }catch (Exception ex) {
      ex.printStackTrace();
    }finally {
      dbrs.close();
    }
    result.put("YEAR_OF_REPORT",yearOfReport);
    result.put("NUM_OF_YEAR",numOfYear);
    return result;
  }

   public void load()  throws Exception {
     DBResultSet dbrs = null;
     ResultSet rs = null;
     String query = "select feature,feature_mode from tcmis_feature";
     try {
     	 dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
	     while(rs.next()) {
         setFeature(BothHelpObjs.makeBlankFromNull(rs.getString("feature")));
         setFeatureMode(BothHelpObjs.makeBlankFromNull(rs.getString("feature_mode")));
	     }
     } catch (Exception e) {
      e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);   throw e;
     } finally{
      dbrs.close();
     }
   }

   public Hashtable getTcmISMessage() throws Exception {
    Hashtable h = new Hashtable();
    h.put("DISPLAY_MESSAGE",new Boolean(false));
    h.put("MESSAGE","");
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      String query = "select feature_mode,feature_message from tcmis_feature where feature = 'tcmISMessage'";
      dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       //System.out.println("\n\n------ here i am...");
	     while(rs.next()) {
        //System.out.println("got here "+new Boolean(BothHelpObjs.makeBlankFromNull(rs.getString("feature_mode")).equalsIgnoreCase("1")));
        h.put("DISPLAY_MESSAGE",new Boolean(BothHelpObjs.makeBlankFromNull(rs.getString("feature_mode")).equalsIgnoreCase("1")));
        h.put("MESSAGES",BothHelpObjs.makeBlankFromNull(rs.getString("feature_message")));
      }
    }catch (Exception e) {
      e.printStackTrace();
      HelpObjs.sendMail(db,"Error while getting display message","Error while getting display message info.",86030,false);
    }finally {
      dbrs.close();
    }
    return h;
  }

}
