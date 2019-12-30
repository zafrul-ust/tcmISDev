
package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;

import radian.tcmis.both1.helpers.*;
import radian.tcmis.server7.share.db.*;


import java.util.*;
import java.sql.*;

public class ChargeNumber {

   protected TcmISDBModel db;
   protected String facility_id;
   protected String charge_type;
   protected String account_number;
   protected String account_number2;
   protected String account_sys_id;

   public ChargeNumber(TcmISDBModel db){
      this.db = db;
   }
   public void setAccountNumber2(String s){
     account_number2= new String(s);
   }
   public String getAccountNumber2(){
     return account_number2;
   }
   public void setAccountNumber(String s){
     account_number= new String(s);
   }
   public String getAccountNumber(){
     return account_number;
   }
   public void setChargeType(String s){
     charge_type= new String(s);
   }
   public String getChargeType(){
     return charge_type;
   }
   public void setFacilityId(String s){
     facility_id= new String(s);
   }
   public String getFacilityId(){
     return facility_id;
   }
   //trong
   public void setAccSysId(String s) {
    account_sys_id = s;
   }
   public String getAccSysId(){
    return account_sys_id;
   }


   public Vector getChargeNumsForFacNew(Hashtable h, String commodityType) throws Exception{
    //String facId = (String)h.get("FAC_ID");     before 7-18
    String facId = (String)h.get("FACILITY_ID");
    String accSysId = (String)h.get("ACC_SYS_ID");
    Hashtable tempH = (Hashtable)h.get("ACC_TYPE_H");
    Hashtable tempH2 = (Hashtable)tempH.get(accSysId);
    Vector display1 = (Vector)tempH2.get("DISPLAY_1");
    Vector display2 = (Vector)tempH2.get("DISPLAY_2");
    Vector chargeType = (Vector)tempH2.get("CHARGE_TYPE");

    String query = "select * from charge_number_display_view where ";
    query += "facility_id = '"+facId+"' and account_sys_id = '" +accSysId+ "'";
    query += " and status = 'A'";

    Vector v = new Vector();
    for (int i = 0; i < display1.size(); i++) {
       if (display1.elementAt(i).toString().equalsIgnoreCase("y") ||
           display2.elementAt(i).toString().equalsIgnoreCase("y")) {

          String query2 = query + " and charge_type = '"+chargeType.elementAt(i)+"'";
          query2 +=  " and rownum < (select max_charge_number_to_display from"+
                     " account_sys where account_sys_id = '"+accSysId+"' and charge_type = '"+chargeType.elementAt(i)+"')";
          query2 += " order by charge_number_1,charge_number_2";
          DBResultSet dbrs = null;
          ResultSet rs = null;
          try {
            dbrs = db.doQuery(query2);
            rs=dbrs.getResultSet();
            while(rs.next()){
              ChargeNumber cn = new ChargeNumber(db);
              if(rs.getString("charge_number_1")==null){
                cn.setAccountNumber("");
              }else{
                cn.setAccountNumber(rs.getString("charge_number_1"));
              }
              if(rs.getString("charge_number_2")==null){
                cn.setAccountNumber2("");
              }else{
                cn.setAccountNumber2(rs.getString("charge_number_2"));
              }
              if(rs.getString("charge_type")==null){
                cn.setChargeType("");
              }else{
                cn.setChargeType(rs.getString("charge_type"));
              }
              v.addElement(cn);
            }
          } catch (Exception e) {
            e.printStackTrace();
            HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
            throw e;
          } finally{
            dbrs.close();
          }
       }
    }
    return v;
  }


  public Vector getChargeNumsForFac(String facId, String accSysId, String commodityType)throws Exception{
      /* Not until Nish add commodity_type to pr_rules
      String query = "select * from charge_number_display_view where ";
      query += "facility_id = '"+facId+"' and account_sys_id = '" +accSysId+ "'";
      query += " and commodityType = '"+commodityType+"'";
      */
      String query = "select * from charge_number_display_view where ";
      query += "facility_id = '"+facId+"' and account_sys_id = '" +accSysId+ "'";

      DBResultSet dbrs = null;
      ResultSet rs = null;
      Vector v = new Vector();
      try {
         dbrs = db.doQuery(query);
         rs=dbrs.getResultSet();
         while(rs.next()){
           ChargeNumber cn = new ChargeNumber(db);
           if(rs.getString("charge_number_1")==null){
             cn.setAccountNumber("");
           }else{
             cn.setAccountNumber(rs.getString("charge_number_1"));
           }
           if(rs.getString("charge_number_2")==null){
             cn.setAccountNumber2("");
           }else{
             cn.setAccountNumber2(rs.getString("charge_number_2"));
           }
           if(rs.getString("charge_type")==null){
             cn.setChargeType("");
           }else{
             cn.setChargeType(rs.getString("charge_type"));
           }
           cn.setFacilityId(rs.getString("facility_id"));
           cn.setAccSysId(rs.getString("account_sys_id"));
           v.addElement(cn);
         }


      } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
      throw e;
      } finally{
         dbrs.close();
       }
      return v;
   }


   public String toString(){
     return "CHARGE_NUMBER: facility_id:"+getFacilityId()+",charge_type:"+getChargeType()+",account_number:"+getAccountNumber()+",account_number2:"+getAccountNumber2();
   }

   public Vector checkChargeNumberNew(ChargeNumber cn, Hashtable header) throws Exception{
    Vector result = new Vector();
    String accSysId = (String)header.get("ACC_SYS_ID");
    String facId = (String)header.get("FACILITY_ID");
    String key = accSysId + cn.getChargeType();
    Hashtable pack = (Hashtable)header.get("PACK");
    Hashtable innerH = (Hashtable)pack.get(key);
    String chargeId1 = (String)innerH.get("CHARGE_ID_1");
    String chargeId2 = (String)innerH.get("CHARGE_ID_2");
    String valid1 = (String)innerH.get("VALID_1");
    String valid2 = (String)innerH.get("VALID_2");
    //System.out.println("\n\n--------- charge id: "+chargeId1+"--"+chargeId2+"-");

    RequestsScreen rs = new RequestsScreen(db);
    String query = "select count(*) from charge_number where status = 'A' ";
    String query2 = new String("");
    int i = 0;
    //validate charge_1 and 2 together
     int len11 = 0;
     int len12 = 0;
     int len21 = 0;
     String chargePad11 = new String("");
     String chargePad12 = new String("");
     String chargePad21 = new String("");

    // int len22 = 0;
     //getting length for charge id 1
     if (chargeId1 != null) {
      String query3 = "select * from charge_field where charge_id = '"+chargeId1+"'";
      DBResultSet dbrs3 = null;
      ResultSet rs3 = null;
      try {
         dbrs3 = db.doQuery(query3);
         rs3=dbrs3.getResultSet();
         while (rs3.next()){
          len11 = BothHelpObjs.makeZeroFromNull(rs3.getString("charge_number_1_length"));
          len12 = BothHelpObjs.makeZeroFromNull(rs3.getString("charge_number_2_length"));
          chargePad11 = BothHelpObjs.makeBlankFromNull(rs3.getString("charge_number_1_pad"));
          chargePad12 = BothHelpObjs.makeBlankFromNull(rs3.getString("charge_number_2_pad"));
         }
         dbrs3.close();
      } catch (Exception e) {
          e.printStackTrace();
          HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
          dbrs3.close(); throw e;
      }
     }
     //getting length for charge id 2
     if (!BothHelpObjs.isBlankString(chargeId2)) {
      String query3 = "select * from charge_field where charge_id = '"+chargeId2+"'";
      DBResultSet dbrs3 = null;
      ResultSet rs3 = null;
      try {
         dbrs3 = db.doQuery(query3);
         rs3=dbrs3.getResultSet();
         while (rs3.next()){
          len21 = BothHelpObjs.makeZeroFromNull(rs3.getString("charge_number_1_length"));
          chargePad21 = BothHelpObjs.makeBlankFromNull(rs3.getString("charge_number_1_pad"));
          //len22 = BothHelpObjs.makeZeroFromNull(rs3.getString("charge_number_2_length"));
         }
         dbrs3.close();
      } catch (Exception e) {
          e.printStackTrace();
          HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
          dbrs3.close(); throw e;
      }
     }

      //System.out.println("======================= : " +chargePad11 + " " + chargePad12 + " " + chargePad21);
     //String lpad1 = " lpad(account_number,"+len1+",'0')";
     //String lpadn = " lpad("+i1.toString()+","+len1+",'0')";
     //select * from charge_number where charge_id = 'JDE' and charge_number_1 = upper(lpad('2702',5,'0'))

     if (chargeId1.equals(chargeId2) && chargeId1 != null && !BothHelpObjs.isBlankString(chargeId1)) {
        query2 = query + "and charge_id = '" +chargeId1+"'";
        if (len11 == 0 || chargePad11.equalsIgnoreCase("n")){
          query2 += " and upper(charge_number_1) = upper('" +cn.getAccountNumber()+ "')";
        }else if (chargePad11.equalsIgnoreCase("l")){
          query2 += " and upper(lpad(charge_number_1,"+len11+",'0')) = upper(lpad('" +cn.getAccountNumber()+ "'," +len11+ ",'0'))";
        } else {
          query2 += " and upper(rpad(charge_number_1,"+len11+",'0')) = upper(rpad('" +cn.getAccountNumber()+ "'," +len11+ ",'0'))";
        }
        if (len12 == 0 || chargePad12.equalsIgnoreCase("n")) {
          query2 += " and upper(charge_number_2) = upper('" +cn.getAccountNumber2()+ "')";
        } else if (chargePad12.equalsIgnoreCase("l")){
          query2 += " and upper(lpad(charge_number_2,"+len12+",'0')) = upper(lpad('" +cn.getAccountNumber2()+ "'," +len12+ ",'0'))";
        } else {
          query2 += " and upper(rpad(charge_number_2,"+len12+",'0')) = upper(rpad('" +cn.getAccountNumber2()+ "'," +len12+ ",'0'))";
        }
        try {
          i = DbHelpers.countQuery(db,query2);
        }catch(Exception e){e.printStackTrace();throw e;}
        if(i == 0) {
          result.addElement(new Boolean(false));
          result.addElement(new Boolean(false));
        } else {
          result.addElement(new Boolean(true));
          result.addElement(new Boolean(true));
        }
     } else {
        //validate charge_1 separately
        if(chargeId1 != null) {
          if (!valid1.equalsIgnoreCase("n")){
            if (valid1.equalsIgnoreCase("y")){
              query2 = query + "and charge_id = '" +chargeId1+"'";
              if (len11 == 0 || chargePad11.equalsIgnoreCase("n")) {
                query2 += " and upper(charge_number_1) = upper('" +cn.getAccountNumber()+ "')";
              } else if (chargePad11.equalsIgnoreCase("l")){
                query2 += " and upper(lpad(charge_number_1,"+len11+",'0')) = upper(lpad('" +cn.getAccountNumber()+ "'," +len11+ ",'0'))";
              } else {
                query2 += " and upper(rpad(charge_number_1,"+len11+",'0')) = upper(rpad('" +cn.getAccountNumber()+ "'," +len11+ ",'0'))";
              }
              try {
                i = DbHelpers.countQuery(db,query2);
              }catch(Exception e){e.printStackTrace();throw e;}
              if (i == 0) {
                result.addElement(new Boolean(false));
              } else {
                result.addElement(new Boolean(true));
              }
            } else {
               if (rs.getNumericFromAlpha(valid1) != cn.getAccountNumber().length()) {
                  result.addElement(new Boolean(false));
               } else {
                  result.addElement(new Boolean(true));
               }
            }
          } else {
             result.addElement(new Boolean(true));
          }
        } else {
          if (valid1.equalsIgnoreCase("y")){
            result.addElement(new Boolean(false));
          }else {
            if ("n".equalsIgnoreCase(valid1)) {
              result.addElement(new Boolean(true));
            }else {
              if (rs.getNumericFromAlpha(valid1) != cn.getAccountNumber().length()) {
                result.addElement(new Boolean(false));
              } else {
                result.addElement(new Boolean(true));
              }
            }
          }
        } //end of charge ID 1 is null

        // validate charge_2 separately
        if(!BothHelpObjs.isBlankString(chargeId2)) {
          if (!valid2.equalsIgnoreCase("n")){
            if (valid2.equalsIgnoreCase("y")){
              query2 = query + "and charge_id = '" +chargeId2+"'";
              if (len21 == 0 && chargePad21.equalsIgnoreCase("n")) {
                query2 += " and upper(charge_number_1) = upper('" +cn.getAccountNumber2()+ "')";
              }else if (chargePad21.equalsIgnoreCase("l")){
                query2 += " and upper(lpad(charge_number_1,"+len21+",'0')) = upper(lpad('" +cn.getAccountNumber2()+ "'," +len21+ ",'0'))";
              } else {
                query2 += " and upper(rpad(charge_number_1,"+len21+",'0')) = upper(rpad('" +cn.getAccountNumber2()+ "'," +len21+ ",'0'))";
              }
              try {
                i = DbHelpers.countQuery(db,query2);
              }catch(Exception e){e.printStackTrace();throw e;}
              if (i == 0) {
                result.addElement(new Boolean(false));
              } else {
                result.addElement(new Boolean(true));
              }
            } else {
               if (rs.getNumericFromAlpha(valid2) != cn.getAccountNumber2().length()) {
                  result.addElement(new Boolean(false));
               } else {
                  result.addElement(new Boolean(true));
               }
            }
          } else {
             result.addElement(new Boolean(true));
          }
        } else {
          if (valid2.equalsIgnoreCase("y")){
            result.addElement(new Boolean(false));
          }else {
            if ("n".equalsIgnoreCase(valid2)) {
              result.addElement(new Boolean(true));
            }else {
              if (rs.getNumericFromAlpha(valid2) != cn.getAccountNumber2().length()) {
                result.addElement(new Boolean(false));
              } else {
                result.addElement(new Boolean(true));
              }
            }
          }
        } //end of charge ID is null
     }
     return result;
   } //end of method

  public Vector checkWasteMaterialChargeNumber(ChargeNumber cn, Hashtable header) throws Exception{
    Vector result = new Vector();
    String accSysId = (String)header.get("ACC_SYS_ID");
    //String facId = (String)header.get("FACILITY_ID");
    String key = accSysId + cn.getChargeType();
    Hashtable innerH = (Hashtable)header.get(key);
    String chargeId1 = (String)innerH.get("CHARGE_ID_1");
    String chargeId2 = (String)innerH.get("CHARGE_ID_2");
    String valid1 = (String)innerH.get("VALID_1");
    String valid2 = (String)innerH.get("VALID_2");
    //System.out.println("\n\n--------- charge id: "+chargeId1+"--"+chargeId2+"-");

    RequestsScreen rs = new RequestsScreen(db);
    String query = "select count(*) from charge_number where ";
    String query2 = new String("");
    int i = 0;
    //validate charge_1 and 2 together
     int len11 = 0;
     int len12 = 0;
     int len21 = 0;
     String chargePad11 = new String("");
     String chargePad12 = new String("");
     String chargePad21 = new String("");

    // int len22 = 0;
     //getting length for charge id 1
     if (!BothHelpObjs.isBlankString(chargeId1)) {
      String query3 = "select * from charge_field where charge_id = '"+chargeId1+"'";
      DBResultSet dbrs3 = null;
      ResultSet rs3 = null;
      try {
         dbrs3 = db.doQuery(query3);
         rs3=dbrs3.getResultSet();
         while (rs3.next()){
          len11 = BothHelpObjs.makeZeroFromNull(rs3.getString("charge_number_1_length"));
          len12 = BothHelpObjs.makeZeroFromNull(rs3.getString("charge_number_2_length"));
          chargePad11 = BothHelpObjs.makeBlankFromNull(rs3.getString("charge_number_1_pad"));
          chargePad12 = BothHelpObjs.makeBlankFromNull(rs3.getString("charge_number_2_pad"));
         }
         dbrs3.close();
      } catch (Exception e) {
          e.printStackTrace();
          HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
          dbrs3.close(); throw e;
      }
     }
     //getting length for charge id 2
     if (!BothHelpObjs.isBlankString(chargeId2)) {
      String query3 = "select * from charge_field where charge_id = '"+chargeId2+"'";
      DBResultSet dbrs3 = null;
      ResultSet rs3 = null;
      try {
         dbrs3 = db.doQuery(query3);
         rs3=dbrs3.getResultSet();
         while (rs3.next()){
          len21 = BothHelpObjs.makeZeroFromNull(rs3.getString("charge_number_1_length"));
          chargePad21 = BothHelpObjs.makeBlankFromNull(rs3.getString("charge_number_1_pad"));
          //len22 = BothHelpObjs.makeZeroFromNull(rs3.getString("charge_number_2_length"));
         }
         dbrs3.close();
      } catch (Exception e) {
          e.printStackTrace();
          HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
          dbrs3.close(); throw e;
      }
     }

     if (chargeId1.equals(chargeId2) && chargeId1 != null && !BothHelpObjs.isBlankString(chargeId1)) {
        query2 = query + "charge_id = '" +chargeId1+"'";
        if (len11 == 0 || chargePad11.equalsIgnoreCase("n")){
          query2 += " and upper(charge_number_1) = upper('" +cn.getAccountNumber()+ "')";
        }else if (chargePad11.equalsIgnoreCase("l")){
          query2 += " and upper(lpad(charge_number_1,"+len11+",'0')) = upper(lpad('" +cn.getAccountNumber()+ "'," +len11+ ",'0'))";
        } else {
          query2 += " and upper(rpad(charge_number_1,"+len11+",'0')) = upper(rpad('" +cn.getAccountNumber()+ "'," +len11+ ",'0'))";
        }
        if (len12 == 0 || chargePad12.equalsIgnoreCase("n")) {
          query2 += " and upper(charge_number_2) = upper('" +cn.getAccountNumber2()+ "')";
        } else if (chargePad12.equalsIgnoreCase("l")){
          query2 += " and upper(lpad(charge_number_2,"+len12+",'0')) = upper(lpad('" +cn.getAccountNumber2()+ "'," +len12+ ",'0'))";
        } else {
          query2 += " and upper(rpad(charge_number_2,"+len12+",'0')) = upper(rpad('" +cn.getAccountNumber2()+ "'," +len12+ ",'0'))";
        }
        try {
          i = DbHelpers.countQuery(db,query2);
        }catch(Exception e){e.printStackTrace();throw e;}
        if(i == 0) {
          result.addElement(new Boolean(false));
          result.addElement(new Boolean(false));
        } else {
          result.addElement(new Boolean(true));
          result.addElement(new Boolean(true));
        }
     } else {
        //validate charge_1 separately
        if(!BothHelpObjs.isBlankString(chargeId1)) {
          if (!valid1.equalsIgnoreCase("n")){
            if (valid1.equalsIgnoreCase("y")){
              query2 = query + "charge_id = '" +chargeId1+"'";
              if (len11 == 0 || chargePad11.equalsIgnoreCase("n")) {
                query2 += " and upper(charge_number_1) = upper('" +cn.getAccountNumber()+ "')";
              } else if (chargePad11.equalsIgnoreCase("l")){
                query2 += " and upper(lpad(charge_number_1,"+len11+",'0')) = upper(lpad('" +cn.getAccountNumber()+ "'," +len11+ ",'0'))";
              } else {
                query2 += " and upper(rpad(charge_number_1,"+len11+",'0')) = upper(rpad('" +cn.getAccountNumber()+ "'," +len11+ ",'0'))";
              }
              try {
                i = DbHelpers.countQuery(db,query2);
              }catch(Exception e){e.printStackTrace();throw e;}
              if (i == 0) {
                result.addElement(new Boolean(false));
              } else {
                result.addElement(new Boolean(true));
              }
            } else {
               if (rs.getNumericFromAlpha(valid1) != cn.getAccountNumber().length()) {
                  result.addElement(new Boolean(false));
               } else {
                  result.addElement(new Boolean(true));
               }
            }
          } else {
             result.addElement(new Boolean(true));
          }
        } else {
          if (valid1.equalsIgnoreCase("y")){
            result.addElement(new Boolean(false));
          }else {
            if (rs.getNumericFromAlpha(valid1) != cn.getAccountNumber().length()) {
              result.addElement(new Boolean(false));
            } else {
              result.addElement(new Boolean(true));
            }
          }
        }

        // validate charge_2 separately
        if(!BothHelpObjs.isBlankString(chargeId2)) {
          if (!valid2.equalsIgnoreCase("n")){
            if (valid2.equalsIgnoreCase("y")){
              query2 = query + "charge_id = '" +chargeId2+"'";
              if (len21 == 0 && chargePad21.equalsIgnoreCase("n")) {
                query2 += " and upper(charge_number_1) = upper('" +cn.getAccountNumber2()+ "')";
              }else if (chargePad21.equalsIgnoreCase("l")){
                query2 += " and upper(lpad(charge_number_1,"+len21+",'0')) = upper(lpad('" +cn.getAccountNumber2()+ "'," +len21+ ",'0'))";
              } else {
                query2 += " and upper(rpad(charge_number_1,"+len21+",'0')) = upper(rpad('" +cn.getAccountNumber2()+ "'," +len21+ ",'0'))";
              }
              try {
                i = DbHelpers.countQuery(db,query2);
              }catch(Exception e){e.printStackTrace();throw e;}
              if (i == 0) {
                result.addElement(new Boolean(false));
              } else {
                result.addElement(new Boolean(true));
              }
            } else {
               if (rs.getNumericFromAlpha(valid2) != cn.getAccountNumber2().length()) {
                  result.addElement(new Boolean(false));
               } else {
                  result.addElement(new Boolean(true));
               }
            }
          } else {
             result.addElement(new Boolean(true));
          }
        } else {
          if (valid2.equalsIgnoreCase("y")){
            result.addElement(new Boolean(false));
          }else {
            if (rs.getNumericFromAlpha(valid2) != cn.getAccountNumber2().length()) {
              result.addElement(new Boolean(false));
            } else {
              result.addElement(new Boolean(true));
            }
          }
        }
     }
     return result;
   }    //end of method
}     //end of class