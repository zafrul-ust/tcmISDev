
package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;

import java.util.*;
import java.sql.*;
import radian.tcmis.server7.share.db.*;
public class PRAccount  {

   protected TcmISDBModel db;
   protected Integer pr_number;
   protected String line_item;
   protected String account_number;
   protected String account_number2;
   protected String pct;
   protected boolean insertStatus;
   protected String chargeType = "";      //10-03-02

   public PRAccount()  throws java.rmi.RemoteException {

   }

   public PRAccount(TcmISDBModel db)  throws java.rmi.RemoteException {
     this.db = db;
   }

   public void setDB(TcmISDBModel db){
     this.db = db;
   }

   public void setPRNumber(int id) {
     this.pr_number = new Integer(id);
   }

   public Integer getPRNumber() {
     return pr_number;
   }

   public void setLineItem(String item) {
     this.line_item= item;
   }

   public String getLineItem() {
     return line_item;
   }

   public void setAccountNumber(String num) {
     this.account_number = num;
   }

   public String getAccountNumber() {
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
     this.pct = num;
   }

   public String getPct() {
     return pct;
   }

   public void setInsertStatus(boolean b) {
     this.insertStatus = b;
   }

   public boolean getInsertStatus() {
     return insertStatus;
   }

   public void setChargeType(String s) {
    chargeType = s;
   }

   public void delete()  throws Exception {
      String query = "delete pr_account where pr_number = " + pr_number.toString();
      DBResultSet dbrs = null;
      try {
         //System.out.println("\n\n----- deleting pr_account: "+query);
         db.doUpdate(query);
      }catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
      throw e;
       }
      return;
   }

   public void deleteLineItem(String lineItem)  throws Exception {
      String query = "delete pr_account where pr_number = " + pr_number.toString();
      query = query + " and line_item = '" + lineItem + "'";

      DBResultSet dbrs = null;
      try {
          db.doUpdate(query);

      } catch (Exception e) {
         e.printStackTrace();
         HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
         throw e;
       }
      //move all lines up
      query = "update pr_account set line_item = to_char(to_number(line_item)-1)";
      query = query + " where to_number(line_item) > " + lineItem + " and  pr_number = " + pr_number.toString();
      try {
         db.doUpdate(query);
      } catch (Exception e) {
        e.printStackTrace();
        HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
        throw e;
       }

   }


   public void deleteItem(String lineItem)  throws Exception {
      String query = "delete pr_account where pr_number = " + pr_number.toString();
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

   public void load()  throws Exception {
     String query = "select account_number,account_number2,percentage from pr_account where pr_number = " + pr_number.toString() + " and line_item = '" + line_item + "'";
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
     String query = "select count(*) from pr_account where pr_number = " + pr_number.toString() + " and line_item = '" + line_item + "'";
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
    String insert = "insert into pr_account (pr_number,line_item,percentage,account_number,account_number2) ";
    String values = "values ("+pr_number+",'"+line_item+"',"+pct;
    try {
      String chargeNumber1 = "";
      String chargeNumber2 = "";
      String query = "select cf.charge_id,cf.charge_number_1_length,cf.charge_number_2_length,cf.charge_number_1_pad,"+
              "cf.charge_number_2_pad,cf2.charge_id charge_id_2,cf2.charge_number_1_length charge_number_1_length_2,cf2.charge_number_1_pad charge_number_1_pad_2"+
              " from purchase_request pr,account_sys a,charge_field cf,charge_field cf2"+
              " where pr.account_sys_id = a.account_sys_id and pr.pr_number = "+pr_number+" and a.charge_type = '"+chargeType+"' and"+
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

   public void insert()  throws Exception {
     DBResultSet dbrs = null;
     ResultSet rs = null;
     String query = "";
     try {
      if (BothHelpObjs.isBlankString(chargeType)) {
        query = "insert into pr_account (pr_number,line_item,account_number,percentage,account_number2) "+
              "values (" + pr_number.toString() + ",'" + line_item + "','" + account_number + "','" + pct + "','"+getAccountNumber2()+"')";
      }else {
        query = getPaddedChargeNumber();
      }
      db.doUpdate(query);
     } catch (Exception e) {
       e.printStackTrace();
       HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
       setInsertStatus(false);
        throw e;
       }
   }

   /* before 10-03-02
    public void insert()  throws Exception {
     DBResultSet dbrs = null;
     ResultSet rs = null;
     String query = "insert into pr_account (pr_number,line_item,account_number,percentage,account_number2) ";
     query = query + "values (" + pr_number.toString() + ",'" + line_item + "','" + account_number + "','" + pct + "','"+getAccountNumber2()+"')";
     try {
        db.doUpdate(query);
     } catch (Exception e) {
       e.printStackTrace();
       HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
       setInsertStatus(false);
        throw e;
       }
   }
   */


   public void commit()  throws Exception {
      return;
   }

   public void rollback()  throws Exception {
     return;
   }

   public Vector getMultiAcctNumbers()  throws Exception {
     Vector V = new Vector();
     String query = "select * from pr_account where pr_number = " + pr_number.toString() + " and line_item = '" + line_item + "'";
     DBResultSet dbrs = null;
      ResultSet rs = null;
     String S;
     try {
       dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
       while(rs.next()) {
         PRAccount pra = new PRAccount(db);
         pra.setAccountNumber(rs.getString("account_number"));
         pra.setAccountNumber2(rs.getString("account_number2"));
         pra.setLineItem(rs.getString("line_item"));
         pra.setPct(rs.getString("percentage"));
         pra.setPRNumber(Integer.parseInt(rs.getString("pr_number")));
         V.addElement(pra);
       }


     } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);    throw e;
       } finally{
             dbrs.close();

     }
     return V;
   }

   /*public Hashtable getAllChargeNum()  throws Exception {
     Hashtable h = new Hashtable();
     String query = "select line_item,account_number from pr_account where pr_number = " + pr_number.toString() + " order by line_item";
     DBResultSet dbrs = null;
      ResultSet rs = null;
     String s;
     String last = "";
     Vector v = new Vector();
     try {
       dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
       while(rs.next()) {
         s = rs.getString(1);
         if(!s.equals(last) && !last.equals("")) {
           h.put(last,v);
           v = new Vector();
           last = s;
         }
         v.addElement(rs.getString(2));
       }


     } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);    throw e;
       } finally{
             dbrs.close();

     }
     return h;
   }   */

   /*public Hashtable getAllPercentageNum()  throws Exception {
     Hashtable h = new Hashtable();
     String query = "select line_item,percentage from pr_account where pr_number = " + pr_number.toString() + " order by line_item";
     DBResultSet dbrs = null;
      ResultSet rs = null;
     String s;
     String last = "";
     Vector v = new Vector();
     try {
       dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
       while(rs.next()) {
         s = rs.getString(1);
         if(!s.equals(last) && !last.equals("")) {
           h.put(last,v);
           v = new Vector();
           last = s;
         }
         v.addElement(rs.getString(2));
       }


     } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);    throw e;
       } finally{
             dbrs.close();

     }
     return h;
   }     */

   public Vector retrieveAll() throws Exception{
     Vector v = new Vector();
     DBResultSet dbrs = null;
      ResultSet rs = null;
     String query = "select line_item,account_number from pr_account where pr_number = '"+pr_number.toString()+"' order by to_number(line_item)";
     try {
       dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
       while(rs.next()) {
         PRAccount zow = new PRAccount(db);
         zow.setPRNumber(pr_number.intValue());
         zow.setLineItem(rs.getString(1));
         zow.setAccountNumber(rs.getString(2));
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






























