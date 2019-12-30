package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.server7.share.db.*;
/*
SQLWKS> desc traveler_container
Column Name                    Null?    Type
------------------------------ -------- ----
TRAVELER_ID                    NOT NULL NUMBER(38)
CONTAINER_ID                   NOT NULL NUMBER(38)
TRANSFER_DATE                           DATE
*/

import java.util.*;
import java.sql.*;

public class WasteTravelerContainer {
   protected TcmISDBModel db;

   public static final int STRING = 0;
   public static final int DATE = 2;
   public static final int INT = 1;
   public static final int NULLVAL = 3;

   protected int travelerId;
   protected int containerId;
   protected String transferDate;

   public WasteTravelerContainer(TcmISDBModel db){
      this.db = db;
   }
   public WasteTravelerContainer(){
   }
   public void setDb(TcmISDBModel db){
      this.db = db;
   }

   // get Methods
   public int getTravelerId(){return travelerId;}
   public int getContainerId(){return containerId;}
   public String getTransferDate(){return transferDate;}

   // set Methods
   public void setTravelerId(int s){travelerId = s;}
   public void setContainerId(int s){containerId = s;}
   public void setTransferDate(String s){transferDate = s;}

   public void insert(String col,String val,int type)  throws Exception {
     Integer I;
     DBResultSet dbrs = null;
     ResultSet rs = null;

     String query = new String("update traveler_container set " + col + "=");
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
     query += " where container_id = "+getContainerId();
     query += " and traveler_id = "+getTravelerId();
     //System.out.println("&&&&&&&&&&&&&&&& " + query);
     try {
       db.doUpdate(query);
     } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
        throw e;
       }
   }

  public static void deleteAll(TcmISDBModel db, Vector trID) throws Exception {
    String query = "delete traveler_container where traveler_id in ";
    String tmp = "";
    for (int i = 0; i < trID.size(); i++) {
      tmp += trID.elementAt(i).toString()+",";
    }
    //removing last commas
    tmp = tmp.substring(0,tmp.length()-1);
    query += "("+tmp+")";

    DBResultSet dbrs = null;
    try {
      db.doUpdate(query);
    }catch (Exception e) {
      e.printStackTrace();
      //HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
      throw e;
    } finally{}
    return;
   }

  public void delete() throws Exception {
    String query = "delete traveler_container where traveler_id = '"+getTravelerId()+"'";
    DBResultSet dbrs = null;
      try {
        db.doUpdate(query);
      }catch (Exception e) {
        e.printStackTrace();
        HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
        throw e;
      } finally{}
      return;
   }

   public void load()throws Exception{
     String query = "select * from traveler_container where traveler_id = '"+getTravelerId()+"'";
     query += " and container_id = '"+getContainerId()+"'";
     DBResultSet dbrs = null;
     ResultSet rs = null;
     int myCount = 0;
     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()){
          setTransferDate(BothHelpObjs.makeBlankFromNull(rs.getString("transfer_date")));
       }
     }catch (Exception e) { e.printStackTrace(); throw e;
     }finally{dbrs.close();}
   }

   public static void createWasteTravelerContainer(TcmISDBModel db,int travelerId, int containerId)throws Exception{
     ResultSet rs = null;
     try{
       String query = "insert into traveler_container ";
       query = query + "(traveler_id,container_id) ";
       query = query + "values ("+travelerId+","+containerId+")";
       db.doUpdate(query);
     }catch(Exception e){
       e.printStackTrace();
       throw e;
     }
   }

   public boolean travelerContainerExist() throws Exception {
    String query = "select count(*) from traveler_container where traveler_id = '"+getTravelerId()+"'";
    query += " and container_id = '"+getContainerId()+"'";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    boolean result = false;
    int myCount = 0;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while (rs.next()){
        myCount = BothHelpObjs.makeZeroFromNull(rs.getString("count(*)"));
      }
    }catch (Exception e) { e.printStackTrace(); throw e;
    }finally{dbrs.close();}
    if (myCount > 0) {
      result = true;
    }
    return result;
   }
}

