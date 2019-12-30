package radian.tcmis.server7.share.dbObjs;

/*
SQLWKS> desc fac_pref
Column Name                    Null?    Type
------------------------------ -------- ----
PERSONNEL_ID                   NOT NULL NUMBER(38)
FACILITY_ID                    NOT NULL VARCHAR2(30)
PRIORITY                                NUMBER(38)
PREFERRED_ACCOUNT_SYS_ID                VARCHAR2(12)
*/

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;

import java.util.*;
import java.sql.*;
import radian.tcmis.server7.share.db.*;
public class FacPref  {

   public static final int STRING = 0;
   public static final int INT = 1;
   public static final int DATE = 2;

   protected TcmISDBModel db;
   protected String facId;
   protected String userId;
   protected String prefAccSysId;

   public FacPref()  throws java.rmi.RemoteException {

   }

   public FacPref(TcmISDBModel  db)  throws java.rmi.RemoteException {
     this.db = db;
   }

   public void setDB(TcmISDBModel  db) {
     this.db = db;
   }

   public void setUserId(String s) {
     this.userId = s;
   }

   public String getUserId() {
     return this.userId;
   }

   public void setFacId(String s) {
     this.facId = s;
   }

   public String getFacId() {
     return this.facId;
   }

   public void setPreAccSysId(String s) {
    this.prefAccSysId = s;
   }

   public String getPreAccSysId() {
    return this.prefAccSysId;
   }

   public int getPrefFacCount() throws Exception{
    String query = "select count(*) from fac_pref where facility_id = '"+facId+"'";
    query += " and personnel_id = '"+userId+"'";
    int count;
    try {
      count = DbHelpers.countQuery(db,query);
    }catch(Exception e){e.printStackTrace();throw e;}
    return count;
   }

   public void load()  throws Exception {
     DBResultSet dbrs = null;
     ResultSet rs = null;
     String query = new String("select * from fac_pref where facility_id = '"+facId+"'");
     query += " and personnel_id = '"+userId+"'";
     try {
     	 dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
	     while(rs.next()) {
	       this.setPreAccSysId(BothHelpObjs.makeBlankFromNull(rs.getString("PREFERRED_ACCOUNT_SYS_ID")));
	     }


     } catch (Exception e) { e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);   throw e;
       } finally{
             dbrs.close();
       }
   }

  public static Vector getFacilityInEComList(TcmISDBModel  db, String uID) throws Exception{
     Vector resultV = new Vector();
     DBResultSet dbrs = null;
     ResultSet rs = null;
     String query = "select a.facility_id from fac_pref a, facility b where a.facility_id = b.facility_id and lower(b.ecommerce) = lower('y') and a.personnel_id = "+uID;
     try {
     	 dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
	     while(rs.next()) {
	       resultV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("facility_id")));
	     }
     } catch (Exception e) {
      e.printStackTrace();
      throw e;
     } finally{
      dbrs.close();
     }
     return resultV;
  }

  public static Vector getCatAddFacSingleAppList(TcmISDBModel  db, String uID) throws Exception{
     Vector resultV = new Vector();
     DBResultSet dbrs = null;
     ResultSet rs = null;
     String query = "select a.facility_id from fac_pref a, facility b where a.facility_id = b.facility_id and lower(b.cat_add_single_app) = lower('y') and a.personnel_id = "+uID;
     try {
     	 dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
	     while(rs.next()) {
	       resultV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("facility_id")));
	     }
     } catch (Exception e) {
      e.printStackTrace();
      throw e;
     } finally{
      dbrs.close();
     }
     return resultV;
  }

  public void insert(String col,String val,int type)  throws Exception {
     Integer I;
     DBResultSet dbrs = null;

     String query = new String("update fac_pref set " + col + " = ");
     switch (type) {
       case INT:
         I = new Integer(val);
         query = query + I.toString();
         break;
       case DATE:
         try {
           ResultSet rs = null;
           dbrs = db.doQuery("select to_char(sysdate,'HH24:Mi:SS') from dual");   rs=dbrs.getResultSet();

           if (rs.next()) {
	       val = val + " " + rs.getString(1);
           }
        } catch (Exception e){
        e.printStackTrace();
         throw e;
       } finally{
             dbrs.close();
           }
         if (val.equals("nowDate")){
           query = query + " SYSDATE ";
         } else {
           query = query + " to_date('"+val+"','MM/dd/yyyy HH24:MI:SS')";
         }
         break;
       case STRING:
         query = query + "'" + val + "'";
         break;
       default:
         query = query + "'" + val + "'";
         break;
     }
     query = query + " where facility_id = '" + this.getFacId()+"'";
     query += " and personnel_id = '" +this.getUserId()+"'";
     try {
       db.doUpdate(query);
    } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
       throw e;
    }
   }

}
