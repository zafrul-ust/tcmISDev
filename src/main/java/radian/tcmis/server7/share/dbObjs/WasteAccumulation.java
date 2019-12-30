package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.share.db.*;
import java.sql.*;


public class WasteAccumulation {

  protected TcmISDBModel db;
  protected int accumulationId;
  protected int containerId;
  protected int amount;

  public static final int STRING = 0;
  public static final int DATE = 2;
  public static final int INT = 1;
  public static final int NULLVAL = 3;


  public WasteAccumulation(TcmISDBModel db){
      this.db = db;
  }
  public void setDb(TcmISDBModel db){
      this.db = db;
  }

  public void setAccumulationId(int n) {
    this.accumulationId = n;
  }
  public void setContianerId(int n) {
    this.containerId = n;
  }
  public void setAmount(int n) {
    this.amount = n;
  }
  public int getAccumulationId() {
    return this.accumulationId;
  }
  public int getContainerId() {
    return this.containerId;
  }
  public int getAmount() {
    return this.amount;
  }

  public void createWasteAccumulation() throws Exception{
    try {
      String query = "insert into waste_accumulation (accumulation_id,container_id,amount) values ("+getAccumulationId()+","+getContainerId()+","+getAmount()+")";
      db.doUpdate(query);
    }catch(Exception e){
       e.printStackTrace();
    }finally {
    }
  }

  public void insert(String col,String val,int type)  throws Exception {
     Integer I;
     DBResultSet dbrs = null;
     ResultSet rs = null;

     String query = new String("update waste_accumulation set " + col + "=");
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
     query += " where accumulation_id  = "+getAccumulationId();
     query += " and container_id = "+getContainerId();
     try {
       db.doUpdate(query);
     } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
        throw e;
     }finally{}
   }



}