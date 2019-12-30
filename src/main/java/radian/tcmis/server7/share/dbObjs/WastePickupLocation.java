package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.both1.helpers.*;
import radian.tcmis.server7.share.db.*;
import java.util.*;
import java.sql.*;

public class WastePickupLocation {
   protected TcmISDBModel db;

   protected int locationId;
   protected String facilityId;
   protected String locationDesc;


   public WastePickupLocation(TcmISDBModel db){
      this.db = db;
   }
   public WastePickupLocation(){
   }
   public void setDb(TcmISDBModel db){
      this.db = db;
   }

   // get methods
   public int getLocationId(){return locationId;}
   public String getFacilityId(){return facilityId;}
   public String getLocationDesc(){return locationDesc;}

   // set methods
   public void setLocationId(int s){locationId = s;}
   public void setFacilityId(String s){facilityId = s;}
   public void setLocationDesc(String s){locationDesc = s;}

   public void load()throws Exception{
     String query = "select * from waste_pickup_location where location_id = "+getLocationId();
     DBResultSet dbrs = null;
     ResultSet rs = null;
     int myCount = 0;
     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()){
         setFacilityId(BothHelpObjs.makeBlankFromNull(rs.getString("facility_id")));
         setLocationDesc(BothHelpObjs.makeBlankFromNull(rs.getString("location_desc")));
       }
     }catch (Exception e) { e.printStackTrace(); throw e;
     }finally{dbrs.close();}
   }
   public static WastePickupLocation addWastePickupLocation(TcmISDBModel db,String facId,String desc)throws Exception{
     try{
       int next = DbHelpers.getNextVal(db,"waste_pickup_location_seq");
       String query = "insert into waste_pickup_location (location_id,facility_id,location_desc) values ("+next+",'"+facId+"','"+desc+"')";
       db.doUpdate(query);
       WastePickupLocation wr = new WastePickupLocation(db);
       wr.setLocationId(next);
       wr.load();
       return wr;
     }catch(Exception e){
       e.printStackTrace();
       throw e;
     }finally{
     }
   }
   public static Vector getAllPickupLocations(TcmISDBModel db,String facId)throws Exception{
     Vector v = new Vector();
     String query = "select * from waste_pickup_location where facility_id = '"+facId+"' order by location_desc";
     DBResultSet dbrs = null;
     ResultSet rs = null;
     int myCount = 0;
     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()){
         Hashtable h = new Hashtable();
         h.put("ID",BothHelpObjs.makeBlankFromNull(rs.getString("location_id")));
         h.put("DESC",BothHelpObjs.makeBlankFromNull(rs.getString("location_desc")));
         v.addElement(h);
       }
     }catch (Exception e) { e.printStackTrace(); throw e;
     }finally{dbrs.close();}
     return v;
   }

   public void updateDescription(String desc)throws Exception{
     try{
       String query = "update waste_pickup_location set location_desc = '"+desc+"' where location_id = '"+getLocationId()+"'";
       db.doUpdate(query);
     }catch(Exception e){
       e.printStackTrace();
       throw e;
     }finally{
     }
   }
}
