package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.server7.share.db.*;
/*
SQLWKS> desc waste_traveler
Column Name                    Null?    Type
------------------------------ -------- ----
TRAVELER_ID                   NOT NULL NUMBER(38)
REQUESTER_ID                   NOT NULL NUMBER(38)
PICKUP_USER_ID                          NUMBER(38)
FROM_FACILITY_ID               NOT NULL VARCHAR2(30)
FROM_LOCATION_ID               NOT NULL VARCHAR2(30)
TO_FACILITY_ID                          VARCHAR2(30)
TO_LOCATION_ID                          VARCHAR2(30)
REQUEST_DATE                            DATE
*/

import java.sql.*;

public class WasteTraveler {
   protected TcmISDBModel db;

   public static final int STRING = 0;
   public static final int DATE = 2;
   public static final int INT = 1;
   public static final int NULLVAL = 3;

   protected int travelerId;
   protected int requesterId;
   protected int pickupUserId;
   protected String fromFacilityId;
   protected String fromLocationId;
   protected String toFacilityId;
   protected String toLocationId;
   protected String requestDate;
   protected String transferDate;

   public WasteTraveler(TcmISDBModel db){
      this.db = db;
   }
   public WasteTraveler(){
   }
   public void setDb(TcmISDBModel db){
      this.db = db;
   }

   // get Methods
   public int getTravelerId(){return travelerId;}
   public int getRequestId(){return requesterId;}
   public int getPickupUserId(){return pickupUserId;}
   public String getFromFacilityId(){return fromFacilityId;}
   public String getFromLocationId(){return fromLocationId;}
   public String getToFacilityId(){return toFacilityId;}
   public String getToLocationId(){return toLocationId;}
   public String getRequestDate(){return requestDate;}
   public String getTransferDate(){return transferDate;}

   // set Methods
   public void setTravelerId(int s){travelerId = s;}
   public void setRequesterId(int s){requesterId = s;}
   public void setPickupUserId(int s){pickupUserId = s;}
   public void setFromFacilityId(String s){fromFacilityId = s;}
   public void setFromLocationId(String s){fromLocationId = s;}
   public void setToFacilityId(String s){toFacilityId = s;}
   public void setToLocationId(String s){toLocationId = s;}
   public void setRequestDate(String s){requestDate = s;}
   public void setTransferDate(String s){transferDate = s;}

   public void updateToLocation(String toLocationId,String toFacilityId)  throws Exception {
     Integer I;
     DBResultSet dbrs = null;
     ResultSet rs = null;

     String query = new String("update waste_traveler set to_location_id = '"+toLocationId+"',to_facility_id = '"+toFacilityId+"'");
     query += " where traveler_id = '"+getTravelerId()+"'";
     //System.out.println("&&&&&&&&&&&&&&&& " + query);
     try {
       db.doUpdate(query);
     } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
        throw e;
       }
   }


   public void insert(String col,String val,int type)  throws Exception {
     Integer I;
     DBResultSet dbrs = null;
     ResultSet rs = null;

     String query = new String("update waste_traveler set " + col + "=");
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
     query += " where traveler_id = '"+getTravelerId()+"'";
     //System.out.println("&&&&&&&&&&&&&&&& " + query);
     try {
       db.doUpdate(query);
     } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
        throw e;
       }
   }


  public void delete() throws Exception {
    String query = "delete waste_traveler where traveler_id = '"+getTravelerId()+"'";
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

   public void load()throws Exception{
     String query = "select * from waste_traveler where traveler_id = '"+getTravelerId()+"'";

     DBResultSet dbrs = null;
     ResultSet rs = null;
     int myCount = 0;
     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()){
         setRequesterId(BothHelpObjs.makeZeroFromNull(rs.getString("requester_id")));
         setPickupUserId(BothHelpObjs.makeZeroFromNull(rs.getString("pickup_user_id")));
         setFromFacilityId(BothHelpObjs.makeBlankFromNull(rs.getString("from_facility_id")));
         setFromLocationId(BothHelpObjs.makeBlankFromNull(rs.getString("from_location_id")));
         setToFacilityId(BothHelpObjs.makeBlankFromNull(rs.getString("to_facility_id")));
         setToLocationId(BothHelpObjs.makeBlankFromNull(rs.getString("to_location_id")));
         setRequestDate(BothHelpObjs.makeBlankFromNull(rs.getString("request_date")));
         //setTransferDate(BothHelpObjs.makeBlankFromNull(rs.getString("transfer_date")));
       }
     }catch (Exception e) { e.printStackTrace(); throw e;
     }finally{dbrs.close();}
   }

   public void createWasteTraveler(int requesterId, String facilityId, String fromLocationId, String toLocationId)throws Exception{
     ResultSet rs = null;
     try{
       int travelerId = DbHelpers.getNextVal(db,"waste_traveler_seq");
       String query = "insert into waste_traveler ";
       query = query + "(traveler_id,requester_id,from_facility_id,from_location_id,to_location_id) ";
       query = query + "values ("+travelerId+","+requesterId+",'"+facilityId+"','"+fromLocationId+"','"+toLocationId+"')";
       db.doUpdate(query);
       setTravelerId(travelerId);
     }catch(Exception e){
       e.printStackTrace();
       throw e;
     }finally{
     }
   }

   //trong 8-16
   public void createWasteTraveler(int requesterId)throws Exception{
     ResultSet rs = null;
     try{
       int travelerId = DbHelpers.getNextVal(db,"waste_traveler_seq");
       String query = "insert into waste_traveler ";
       query = query + "(traveler_id,requester_id) ";
       query = query + "values ("+travelerId+","+requesterId+")";
       db.doUpdate(query);
       setTravelerId(travelerId);
     }catch(Exception e){
       e.printStackTrace();
       throw e;
     }finally{
     }
   }
}