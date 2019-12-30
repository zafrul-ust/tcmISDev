package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;

import java.util.*;
import java.sql.*;
import radian.tcmis.server7.share.db.*;
public class WasteDirectedCharge  {

   protected TcmISDBModel db;
   protected String facility_id;
   protected String account_sys_id;
   protected String waste_location_id;
   protected String charge_type;
   protected String charge_id;
   protected String charge_number_1;
   protected String charge_number_2;
   protected String pct;
   protected String banned;

   public WasteDirectedCharge()  throws java.rmi.RemoteException {

   }

   public WasteDirectedCharge(TcmISDBModel db)  throws java.rmi.RemoteException {
     this.db = db;
   }

   public void setDB(TcmISDBModel db){
     this.db = db;
   }

   public void setFacilityId(String s) {
     this.facility_id = s;
   }

   public String getFacilityId() {
     return facility_id;
   }

   public void setAccountSysId(String s) {
     this.account_sys_id= s;
   }

   public String getAccountSysId() {
     return account_sys_id;
   }

   public void setWasteLocationId(String s) {
     this.waste_location_id= s;
   }

   public String getWasteLocationId() {
     return waste_location_id;
   }

   public void setChargeType(String s) {
     this.charge_type = s;
   }

   public String getChargeType() {
     return charge_type;
   }

   public void setChargeId(String s) {
     this.charge_id = s;
   }

   public String getChargeId() {
     return charge_id;
   }

   public void setChargeNumber1(String s) {
     this.charge_number_1 = s;
   }

   public String getChargeNumber1() {
     return charge_number_1;
   }

   public void setChargeNumber2(String s) {
     //if (BothHelpObjs.isBlankString(s)) {
     // this.charge_number_2 = "";
     //}else {
      this.charge_number_2 = s;
     //}
   }

   public String getChargeNumber2() {
     return charge_number_2;
   }

   public void setPct(String s) {
     this.pct = s;
   }

   public String getPct() {
     return pct;
   }

   public void setBanned(String s) {
     this.banned = s;
   }

   public String getBanned() {
     return banned;
   }


   public void load()  throws Exception {
     String query = "select facility_id,account_sys_id,waste_location_id,charge_type,charge_number_1,charge_number_2,banned,percent";
     query += " from waste_directed_charge where facility_id = '"+getFacilityId()+"'";
     query += " and account_sys_id = '"+getAccountSysId()+"'";
     String wasteLocID = getWasteLocationId();
     if (wasteLocID.length() > 0 ) {
      query += " and waste_location_id = '"+getWasteLocationId()+"'";
     }
     query += " and charge_type = '"+getChargeType()+"'";

     DBResultSet dbrs = null;
      ResultSet rs = null;
     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()){
         this.setChargeNumber1(BothHelpObjs.makeBlankFromNull(rs.getString("charge_number_1")));
         this.setPct(rs.getString("percentage"));
         this.setChargeNumber2(BothHelpObjs.makeBlankFromNull(rs.getString("charge_number_2")));
         this.setBanned(BothHelpObjs.makeBlankFromNull(rs.getString("banned")));
       }
     } catch (Exception e) {
        e.printStackTrace();
        HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
        throw e;
     } finally{
        dbrs.close();
     }
     return;
   }

   public boolean isDirected()  throws Exception {
     String query = "select count(*)";
     query += " from waste_directed_charge where facility_id = '"+getFacilityId()+"'";
     query += " and account_sys_id = '"+getAccountSysId()+"'";
     String wasteLocID = getWasteLocationId();
     if (wasteLocID.length() > 0 ) {
      query += " and waste_location_id = '"+getWasteLocationId()+"'";
     }
     query += " and charge_type = '"+getChargeType()+"'";
     DBResultSet dbrs = null;
     ResultSet rs = null;
     int n = 0;
     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while(rs.next()) {
         n = (int)rs.getInt(1);
       }
      } catch (Exception e) {
        e.printStackTrace();
        HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
        throw e;
      } finally{
        dbrs.close();
      }
     if (n >= 1) {
       return true;
     } else {
       return false;
     }
   }

   public boolean isBanned()  throws Exception {
     String query = "select count(*)";
     query += " from waste_directed_charge where facility_id = '"+getFacilityId()+"'";
     query += " and account_sys_id = '"+getAccountSysId()+"'";
     String wasteLocID = getWasteLocationId();
     if (wasteLocID.length() > 0 ) {
      query += " and waste_location_id = '"+getWasteLocationId()+"'";
     }
     query += " and charge_type = '"+getChargeType()+"'";
     query += " and upper(banned) = upper('Y')";
     DBResultSet dbrs = null;
     ResultSet rs = null;
     int n = 0;
     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while(rs.next()) {
         n = (int)rs.getInt(1);
       }
      } catch (Exception e) {
        e.printStackTrace();
        HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
        throw e;
      } finally{
        dbrs.close();
      }
     if (n == 1) {
       return true;
     } else {
       return false;
     }
   }

   public Hashtable retrieveAllNew()  throws Exception {
     Hashtable result = new Hashtable();
     String query = "select charge_number_1,charge_number_2,percent,waste_location_id"+
                    " from waste_directed_charge where facility_id = '"+getFacilityId()+"'"+
                    " and account_sys_id = '"+getAccountSysId()+"'";
     String wasteLocID = getWasteLocationId();
     if (wasteLocID.length() > 0 ) {
      query += " and waste_location_id = '"+getWasteLocationId()+"'";
     }
     query += " and charge_type = '"+getChargeType()+"' order by waste_location_id,charge_number_1";;
     DBResultSet dbrs = null;
     ResultSet rs = null;
     String S;
     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       String lastWasteLoc = "";
       while(rs.next()) {
         String currentWasteLoc = rs.getString("waste_location_id");
         if (lastWasteLoc.equalsIgnoreCase(currentWasteLoc)) {
          Vector v = (Vector)result.get(currentWasteLoc);
          Hashtable innerH = new Hashtable(3);
          innerH.put("ACT_NUM_1",BothHelpObjs.makeBlankFromNull(rs.getString("charge_number_1")));
          innerH.put("ACT_NUM_2",BothHelpObjs.makeBlankFromNull(rs.getString("charge_number_2")));
          innerH.put("PERCENTAGE",BothHelpObjs.makeBlankFromNull(rs.getString("percent")));
          v.addElement(innerH);
          result.put(currentWasteLoc,v);
         }else {
          Vector v = new Vector(10);
          Hashtable innerH = new Hashtable(3);
          innerH.put("ACT_NUM_1",BothHelpObjs.makeBlankFromNull(rs.getString("charge_number_1")));
          innerH.put("ACT_NUM_2",BothHelpObjs.makeBlankFromNull(rs.getString("charge_number_2")));
          innerH.put("PERCENTAGE",BothHelpObjs.makeBlankFromNull(rs.getString("percent")));
          v.addElement(innerH);
          result.put(currentWasteLoc,v);
         }
         lastWasteLoc = currentWasteLoc;
       }
     } catch (Exception e) {
       e.printStackTrace();
       HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
       throw e;
     } finally{
       dbrs.close();
     }
     return result;
   }

   public Vector retrieveAll()  throws Exception {
     Vector V = new Vector();
     String query = "select charge_number_1,charge_number_2,percent";
     query += " from waste_directed_charge where facility_id = '"+getFacilityId()+"'";
     query += " and account_sys_id = '"+getAccountSysId()+"'";
     query += " and waste_location_id = '"+getWasteLocationId()+"'";
     query += " and charge_type = '"+getChargeType()+"'";;
     DBResultSet dbrs = null;
     ResultSet rs = null;
     String S;
     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while(rs.next()) {
         WasteDirectedCharge dc = new WasteDirectedCharge(db);
         //System.out.println("\n\n====== got here");
         dc.setChargeNumber1(BothHelpObjs.makeBlankFromNull(rs.getString("charge_number_1")));
         //System.out.println("\n\n====== got here 111111111111");
         dc.setChargeNumber2(BothHelpObjs.makeBlankFromNull(rs.getString("charge_number_2")));
         //System.out.println("\n\n====== got here2222222222222");
         dc.setPct(BothHelpObjs.makeBlankFromNull(rs.getString("percent")));
         V.addElement(dc);
       }
     } catch (Exception e) {
       e.printStackTrace();
       HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
       throw e;
     } finally{
       dbrs.close();
     }
     return V;
   }
}





























