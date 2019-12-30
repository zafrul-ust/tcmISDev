package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;

import java.util.*;
import java.sql.*;
import radian.tcmis.server7.share.db.*;
public class WiAccount  {

   protected TcmISDBModel db;
   protected String orderNo;
   protected String itemID;
   protected String account_number;
   protected String account_number2;
   protected String pct;
   protected boolean insertStatus;

   public static final int STRING = 0;
   public static final int DATE = 2;
   public static final int INT = 1;
   public static final int NULLVAL = 3;

   protected String actSysId;
   protected String chargeType;


   public WiAccount(TcmISDBModel db){
     this.db = db;
   }

   public void setDB(TcmISDBModel db){
     this.db = db;
   }

   public void setOrderNo(String val) {
     this.orderNo = val;
   }

   public String getOrderNo() {
     return orderNo;
   }

   public void setItemID(String value) {
     this.itemID = value;
   }

   public String getItemID() {
     return itemID;
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

   public void delete()  throws Exception {
    //  String query = "delete wi_account where waste_request_id = " + wr_number.toString();
      String query = "delete wi_account where order_no = " + orderNo;
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

   public void deleteLineItem()  throws Exception {
      String query = "delete wi_account where order_no = " +this.getOrderNo();
      query = query + " and item_id = '" +this.getItemID()+ "'";

      DBResultSet dbrs = null;
      try {
          db.doUpdate(query);
      } catch (Exception e) {
         e.printStackTrace();
         HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
         throw e;
       }
   }

   public void load()  throws Exception {
     String query = "select account_number,account_number2,percentage from wi_account where order_no = " + orderNo + " and item_id = '" + itemID + "'";
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
     String query = "select count(*) from wi_account where orderNo = " + orderNo + " and item_id = '" + itemID + "'";
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

  String getPaddedChargeNumber() throws Exception {
    DBResultSet dbrs = null;
    ResultSet rs = null;
    String insert = "insert into wi_account (order_no,item_id,percentage,account_number,account_number2) ";
    String values = "values ("+getOrderNo()+",'"+getItemID()+"',"+getPct();
    try {
      String chargeNumber1 = "";
      String chargeNumber2 = "";
      String query = "select cf.charge_id,cf.charge_number_1_length,cf.charge_number_2_length,cf.charge_number_1_pad,"+
              "cf.charge_number_2_pad,cf2.charge_id charge_id_2,cf2.charge_number_1_length charge_number_1_length_2,cf2.charge_number_1_pad charge_number_1_pad_2"+
              " from waste_order_item woi,account_sys a,charge_field cf,charge_field cf2"+
              " where woi.account_sys_id = a.account_sys_id and woi.order_no = "+getOrderNo()+" and woi.item_id = "+getItemID()+" and a.charge_type = '"+chargeType+"' and"+
              " a.charge_id_1 = cf.charge_id(+) and a.charge_id_2 = cf2.charge_id(+)";
      String chargeId1 = "";
      String chargeId2 = "";
      int len11 = 0;
      int len12 = 0;
      int len21 = 0;
      String chargePad11 = "";
      String chargePad12 = "";
      String chargePad21 = "";
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while(rs.next()) {
        chargeId1 = rs.getString("charge_id");
        len11 = rs.getInt("charge_number_1_length");
        len12 = rs.getInt("charge_number_2_length");
        chargePad11 = rs.getString("charge_number_1_pad");
        chargePad12 = rs.getString("charge_number_2_pad");
        chargeId2 = BothHelpObjs.makeBlankFromNull(rs.getString("charge_id_2"));
        len21 = BothHelpObjs.makeZeroFromNull(rs.getString("charge_number_1_length_2"));
        chargePad21 = BothHelpObjs.makeBlankFromNull(rs.getString("charge_number_1_pad_2"));
      }

      if (chargeId1.equals(chargeId2) && chargeId1 != null && !BothHelpObjs.isBlankString(chargeId1)) {
        if (len11 == 0 || chargePad11.equalsIgnoreCase("n")){
          chargeNumber1 = " upper('" +getAccountNumber()+ "')";
        }else if (chargePad11.equalsIgnoreCase("l")){
          chargeNumber1 = " upper(lpad('" +getAccountNumber()+ "'," +len11+ ",'0'))";
        }else {
          chargeNumber1 = " upper(rpad('" +getAccountNumber()+ "'," +len11+ ",'0'))";
        }
        if (len12 == 0 || chargePad12.equalsIgnoreCase("n")) {
          chargeNumber2 = " upper('" +getAccountNumber2()+ "')";
        }else if (chargePad12.equalsIgnoreCase("l")){
          chargeNumber2 = " upper(lpad('" +getAccountNumber2()+ "'," +len12+ ",'0'))";
        }else {
          chargeNumber2 = " upper(rpad('" +getAccountNumber2()+ "'," +len12+ ",'0'))";
        }
      }else {
        //validate charge_1 separately
        if(chargeId1 != null) {
          if (len11 == 0 || chargePad11.equalsIgnoreCase("n")) {
            chargeNumber1 = " upper('" +getAccountNumber()+ "')";
          }else if (chargePad11.equalsIgnoreCase("l")){
            chargeNumber1 = " upper(lpad('" +getAccountNumber()+ "'," +len11+ ",'0'))";
          }else {
            chargeNumber1 = " upper(rpad('" +getAccountNumber()+ "'," +len11+ ",'0'))";
          }
        }else {
          if (!BothHelpObjs.isBlankString(getAccountNumber())) {
            chargeNumber1 = " upper('" +getAccountNumber()+ "')";
          }
        }

        // validate charge_2 separately
        if(!BothHelpObjs.isBlankString(chargeId2)) {
          if (len21 == 0 && chargePad21.equalsIgnoreCase("n")) {
            chargeNumber2 = " upper('" +getAccountNumber2()+ "')";
          }else if (chargePad21.equalsIgnoreCase("l")){
            chargeNumber2 = " upper(lpad('" +getAccountNumber2()+ "'," +len21+ ",'0'))";
          } else {
            chargeNumber2 = " upper(rpad('" +getAccountNumber2()+ "'," +len21+ ",'0'))";
          }
        }else {
          if (!BothHelpObjs.isBlankString(getAccountNumber2())) {
            chargeNumber2 = " upper('" +getAccountNumber2()+ "')";
          }
        }
      } //end of separate charge ID

      if (chargeNumber1.length() < 1) {
        values += ",'"+chargeNumber1+"'";
      }else {
        values += ","+chargeNumber1;
      }
      if (chargeNumber2.length() < 1) {
        values += ",'"+chargeNumber2+"')";
      }else {
        values += ","+chargeNumber2+")";
      }
    }catch (Exception e) {
      throw e;
    }finally {
      dbrs.close();
    }
    return (insert + values);
   }

   public void createWiAccount()throws Exception{
     ResultSet rs = null;
     String query = "";
     try{
       if (BothHelpObjs.isBlankString(chargeType)) {
        query = "insert into wi_account (order_no,item_id,account_number,account_number2,percentage) "+
                "values ("+this.getOrderNo()+",'"+this.getItemID()+"','"+this.getAccountNumber()+"','"+this.getAccountNumber2()+"',"+this.getPct()+")";
       }else {
        query = getPaddedChargeNumber();
       }
       db.doUpdate(query);
     }catch(Exception e){
       e.printStackTrace();
       throw e;
     }finally{
     }
   }

   public void insert(String col,String val,int type)  throws Exception {
     Integer I;
     DBResultSet dbrs = null;
     ResultSet rs = null;

     String query = new String("update wi_account set " + col + "=");
     switch (type) {
       case INT:
         I = new Integer(val);
         query = query + I.intValue();
         break;
       case DATE:
         if (val.equals("nowDate")){
           query = query + " SYSDATE";
         } else {
           query = query + " to_date('"+val+"','MM/dd/yyyy HH24:MI:SS')";
         }
         break;
       case STRING:
         query = query + "'" + val + "'";
         break;
       case NULLVAL:
         query = query + null;
         break;
       default:
         query = query + "'" + val + "'";
         break;
     }
     query += " where order_no = " + this.getOrderNo()+ " and item_id = '" +this.getItemID()+"'";
     //System.out.println("&&&&&&&&&&&&&&&& " + query);
     try {
       db.doUpdate(query);
     } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
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
     String query = "select * from wi_account where order_no = " + orderNo + " and item_id = '" + itemID + "'";
     DBResultSet dbrs = null;
      ResultSet rs = null;
     String S;
     try {
       dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
       while(rs.next()) {
         WiAccount pra = new WiAccount(db);
         pra.setAccountNumber(rs.getString("account_number"));
         pra.setAccountNumber2(rs.getString("account_number2"));
         pra.setItemID(rs.getString("item_id"));
         pra.setPct(rs.getString("percentage"));
         pra.setOrderNo(rs.getString("order_no"));
         V.addElement(pra);
       }
     } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);    throw e;
       } finally{
             dbrs.close();

     }
     return V;
   }  //end of method
}    //end of class