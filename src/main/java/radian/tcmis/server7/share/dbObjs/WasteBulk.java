package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.server7.share.db.*;

import java.sql.*;

public class WasteBulk {
   protected TcmISDBModel db;

   public static final int STRING = 0;
   public static final int DATE = 2;
   public static final int INT = 1;
   public static final int NULLVAL = 3;

   protected String bulkId;
   protected int wasteItemId;
   protected String accSysId;
   protected String facilityId;
   protected String workArea;
   protected String wasteLocationId;
   protected String bulkDesc;
   protected String chargeType;

   public WasteBulk(TcmISDBModel db){
      this.db = db;
   }
   public WasteBulk(){
   }
   public void setDb(TcmISDBModel db){
      this.db = db;
   }

   // get Methods
   public String getBulkId(){return bulkId;}
   public int getWasteItemId(){return wasteItemId;}
   public String getAccSysId(){return accSysId;}
   public String getFacilityId(){return facilityId;}
   public String getWorkArea(){return workArea;}
   public String getWasteLocationId(){return wasteLocationId;}
   public String getBulkDesc(){return bulkDesc;}
   public String getChargeType(){return chargeType;}

   // set Methods
   public void setBulkId(String s){bulkId = s;}
   public void setWasteItemId(int s){wasteItemId = s;}
   public void setAccSysId(String s){accSysId = s;}
   public void setFacilityId(String s){facilityId = s;}
   public void setWorkArea(String s){workArea = s;}
   public void setWasteLocationId(String s){wasteLocationId = s;}
   public void setBulkDesc(String s){bulkDesc = s;}
   public void setChargeType(String s){chargeType =s;}

   public void load()throws Exception{
     String query = "select * from bulk_waste where bulk_id = '"+getBulkId()+"'";
     DBResultSet dbrs = null;
     ResultSet rs = null;
     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()){
         setWasteItemId(BothHelpObjs.makeZeroFromNull(rs.getString("waste_item_id")));
         setAccSysId(BothHelpObjs.makeBlankFromNull(rs.getString("account_sys_id")));
         setFacilityId(BothHelpObjs.makeBlankFromNull(rs.getString("facility_id")));
         setWorkArea(BothHelpObjs.makeBlankFromNull(rs.getString("generation_point")));
         setWasteLocationId(BothHelpObjs.makeBlankFromNull(rs.getString("waste_location_id")));
         setBulkDesc(BothHelpObjs.makeBlankFromNull(rs.getString("bulk_desc")));
         setChargeType(BothHelpObjs.makeBlankFromNull(rs.getString("charge_type")));
       }
     }catch (Exception e) { e.printStackTrace(); throw e;
     }finally{dbrs.close();}
   }

 

   public void delete() throws Exception{
    String query = "delete from bulk_waste where bulk_id = '"+getBulkId()+"'";
    DBResultSet dbrs = null;
    try {
      db.doUpdate(query);
    }catch (Exception e) {
      e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);  throw e;
    } finally{}
   }

  public void insert(String col,String val,int type)  throws Exception {
     if(col.equalsIgnoreCase("NOTES")) {
       val = HelpObjs.singleQuoteToDouble(val);
     }

     Integer I;
     DBResultSet dbrs = null;
     ResultSet rs = null;

     String query = new String("update bulk_waste set " + col + "=");
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
     query += " where bulk_id = '"+getBulkId()+"'";
     try {
       db.doUpdate(query);
     } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
        throw e;
     }finally{}
   }


   public String getNowDate()  throws Exception {
     String query = "select to_char(sysdate,'MM/dd/yyyy') from dual";
     DBResultSet dbrs = null;
     ResultSet rs = null;
     String next = new String("");
     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()) {
         next = rs.getString(1);
         break;
       }
      } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);  throw e;
       } finally{
        dbrs.close();
     }
     return next;
   }
}