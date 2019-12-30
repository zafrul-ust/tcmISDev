package radian.tcmis.server7.share.dbObjs;

/*
SQLWKS> desc waste_cont_account
------------------------------ -------- ----
ACCOUNT_SYS_ID                          VARCHAR2(12)
CONTAINER_ID                            NUMBER(38)
CHARGE_TYPE                             CHAR(1)
ACCOUNT_NUMBER                          VARCHAR2(12)
ACCOUNT_NUMBER2                         VARCHAR2(12)
PERCENTAGE
*/

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;

import java.util.*;
import java.sql.*;
import radian.tcmis.server7.share.db.*;
public class WasteContainerAccount  {

   protected TcmISDBModel db;
   protected String account_number;
   protected String account_number2;
   protected String pct;
   protected boolean insertStatus;
   protected String actSysId;
   protected String chargeType;
   protected int containerId;

   public WasteContainerAccount(TcmISDBModel db){
     this.db = db;
   }

   public void setDB(TcmISDBModel db){
     this.db = db;
   }

   public void setContainerId(int id) {
     this.containerId = id;
   }

   public int getContainerId() {
     return containerId;
   }

   public void setActSysId(String s) {
     this.actSysId = s;
   }

   public String getActSysId() {
     return actSysId;
   }

   public void setChargeType(String s) {
    this.chargeType = s;
   }

   public String getChargeType() {
    return chargeType;
   }

   public void setAccountNumber(String num) {
     if(BothHelpObjs.isBlankString(num)) num = "";
     this.account_number = num;
   }

   public String getAccountNumber() {
     if(BothHelpObjs.isBlankString(account_number)) account_number = "";
     return account_number;
   }

   public void setAccountNumber2(String num) {
     if(BothHelpObjs.isBlankString(num)) num = "";
     this.account_number2 = num;
   }

   public String getAccountNumber2() {
     if(BothHelpObjs.isBlankString(account_number2)) account_number2 = "";
     return account_number2;
   }

   public void setPct(String num) {
     if(BothHelpObjs.isBlankString(num)) pct = "";
     this.pct = num;
   }

   public String getPct() {
     if(BothHelpObjs.isBlankString(pct)) pct = "";
     return pct;
   }

   public void setInsertStatus(boolean b) {
     this.insertStatus = b;
   }

   public boolean getInsertStatus() {
     return insertStatus;
   }

   public void delete()  throws Exception {
      String query = "delete waste_cont_account where container_id = " +containerId;
      DBResultSet dbrs = null;
      try {
         db.doUpdate(query);

      }catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
      throw e;
       }
      return;
   }

   /*
   public void deleteLineItem(String lineItem)  throws Exception {
      String query = "delete waste_cont_account where waste_request_id = " + wr_number.toString();
      query = query + " and line_item = '" + lineItem + "'";

      DBResultSet dbrs = null;
      try {
          db.doUpdate(query);
      } catch (Exception e) {
         e.printStackTrace();
         HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
         throw e;
       }
   }

   public void sortLineItem(String delLine) throws Exception {
    String query;

     //move all lines up
      query = "update waste_cont_account set line_item = to_char(to_number(line_item)-1)";
      query = query + " where to_number(line_item) > " + delLine + " and  waste_request_id = " + wr_number.toString();
      try {
         db.doUpdate(query);
      } catch (Exception e) {
        e.printStackTrace();
        HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
        throw e;
      }
   }


   public void deleteItem(String lineItem)  throws Exception {
      String query = "delete waste_cont_account where waste_request_id = " + wr_number.toString();
      query = query + " and line_item = '" + lineItem + "'";

      DBResultSet dbrs = null;

      try {
        db.doUpdate(query);
      }catch (Exception e) {
        e.printStackTrace();
        HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
        throw e;
      }
   }
   */

   public void load()  throws Exception {
     String query = "select account_number,percentage from waste_cont_account where container_id = " +containerId;
      if(!isSingle()) {
       query = query + " and account_number = '"+account_number+"'";
       if(!BothHelpObjs.isBlankString(account_number2)){
         query = query + " and account_number2 = '"+account_number2+"'";
       }else{
         query = query + " and account_number2 in null";
       }
     }

     DBResultSet dbrs = null;
      ResultSet rs = null;
     try {
       dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
       while (rs.next()){
         this.setAccountNumber(rs.getString("account_number"));
         this.setPct(rs.getString("percentage"));
         this.setAccountNumber2(rs.getString("account_number2"));
       }
     } catch (Exception e) { e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);   throw e;
     } finally{
      dbrs.close();
     }
     return;
   }

   public boolean isSingle()  throws Exception {
     String query = "select count(*) from waste_cont_account where container_id = " +containerId;
     DBResultSet dbrs = null;
      ResultSet rs = null;
     int n = 0;
     try {
       dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
       while(rs.next()) {
         n = (int)rs.getInt(1);
       }
      } catch (Exception e) { e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);   throw e;
      } finally{
        dbrs.close();
      }
     if (n == 1) {
       return true;
     } else {
       return false;
     }
   }


   public void insert()  throws Exception {
     DBResultSet dbrs = null;
     ResultSet rs = null;
     String query = "insert into waste_cont_account (container_id,account_sys_id,charge_type,account_number,percentage,account_number2) ";
     query = query + "values (" +containerId+ ",'"+getActSysId()+"','" +getChargeType()+ "','" + getAccountNumber() + "','" + getPct() + "','"+getAccountNumber2()+"')";
     try {
        db.doUpdate(query);
     } catch (Exception e) {
       e.printStackTrace();
       HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
       setInsertStatus(false);
        throw e;
       } 
   }


   public void commit()  throws Exception {
      return;
   }

   public void rollback()  throws Exception {
     return;
   }

   public Vector getMultiAcctNumbers()  throws Exception {
     Vector V = new Vector();
     String query = "select * from waste_cont_account where container_id = " +containerId;
     DBResultSet dbrs = null;
      ResultSet rs = null;
     String S;
     try {
       dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
       while(rs.next()) {
         WasteContainerAccount pra = new WasteContainerAccount(db);
         pra.setAccountNumber(rs.getString("account_number"));
         pra.setAccountNumber2(rs.getString("account_number2"));
         //pra.setLineItem(rs.getString("line_item"));
         pra.setPct(rs.getString("percentage"));
         //pra.setWasteRequestId(Integer.parseInt(rs.getString("waste_request_id")));
         V.addElement(pra);
       }
     } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);    throw e;
       } finally{
             dbrs.close();
     }
     return V;
   }

   public Vector retrieveAll() throws Exception{
     Vector v = new Vector();
     DBResultSet dbrs = null;
      ResultSet rs = null;
     String query = "select account_number from waste_cont_account where container_id = "+containerId;
     try {
       dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
       while(rs.next()) {
         WasteContainerAccount zow = new WasteContainerAccount(db);
         zow.setContainerId(containerId);
         //zow.setLineItem(rs.getString(1));
         zow.setAccountNumber(rs.getString(account_number));
         zow.load();
         v.addElement(zow);
       }

     }catch (Exception e) {e.printStackTrace(); throw e;
       } finally{
             dbrs.close();
           }
     return v;
   }
}