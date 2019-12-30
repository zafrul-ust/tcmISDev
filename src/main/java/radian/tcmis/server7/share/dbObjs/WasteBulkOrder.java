package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.server7.share.db.*;

import java.util.*;
import java.sql.*;

public class WasteBulkOrder {
   protected TcmISDBModel db;

   public static final int STRING = 0;
   public static final int DATE = 2;
   public static final int INT = 1;
   public static final int NULLVAL = 3;

   protected String facilityId;
   protected int orderNo;
   protected String bulkId;
   protected int shipmentId;
   protected String manifestLineLetter;
   protected int plannedAmount;
   protected int actualAmount;

   public WasteBulkOrder(TcmISDBModel db){
      this.db = db;
   }
   public WasteBulkOrder(){
   }
   public void setDb(TcmISDBModel db){
      this.db = db;
   }

   // get Methods
   public String getFacilityId(){return facilityId;}
   public int getOrderNo(){return orderNo;}
   public String getBulkId(){return bulkId;}
   public int getShipmentId(){return shipmentId;}
   public String getManifestLineLetter(){return manifestLineLetter;}
   public int getPlannedAmount(){return plannedAmount;}
   public int getActualAmount(){return actualAmount;}

   // set Methods
   public void setFaciliyId(String s){facilityId = s;}
   public void setOrderNo(int s){orderNo = s;}
   public void setBulkId(String s){bulkId = s;}
   public void setShipmentId(int s){shipmentId = s;}
   public void setManifestLineLetter(String s){manifestLineLetter = s;}
   public void setPlannedAmount(int s){plannedAmount = s;}
   public void setActualAmount(int s){actualAmount = s;}

   public void delete() throws Exception {
   }

   public void updatePlannedAmount(int shipId,int plannedA) throws Exception {
    String query = "update bulk_order set planned_amount = "+plannedA;
    query += " where shipment_id = "+shipId;
    try {
       db.doUpdate(query);
     } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
        throw e;
     }finally{}
   }
   public Vector getShipmentForOrder(int orderNo) throws Exception {
    Vector result = new Vector();
    String query = "select shipment_id from bulk_order where order_no = "+orderNo;
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while (rs.next()){
        result.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("shipment_id")));
      }
    }catch (Exception e) {
      e.printStackTrace();
      throw e;
    }finally{dbrs.close();}
    return result;
   }

   public void load()throws Exception{
     String query = "select * from bulk_order where order_no = '"+getOrderNo()+"'";
     query += " and bulk_id = '"+getBulkId()+"'";
     query += " and facility_id = '"+getFacilityId()+"'";
     DBResultSet dbrs = null;
     ResultSet rs = null;
     int myCount = 0;
     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()){
         setShipmentId(BothHelpObjs.makeZeroFromNull(rs.getString("shipment_id")));
         setManifestLineLetter(BothHelpObjs.makeBlankFromNull(rs.getString("manifest_line_letter")));
         setPlannedAmount(BothHelpObjs.makeZeroFromNull(rs.getString("planned_amount")));
         setActualAmount(BothHelpObjs.makeZeroFromNull(rs.getString("actual_amount")));
       }
     }catch (Exception e) { e.printStackTrace(); throw e;
     }finally{dbrs.close();}
   }

   public static void insertWasteBulkOrder(TcmISDBModel db, int orderNo, String bulkId, int shipmentId,String facilityId)throws Exception{
     try{
       String query = "insert into bulk_order (order_no,bulk_id,shipment_id,facility_id)";
       query += " values ("+orderNo+",'"+bulkId+"',"+shipmentId+",'"+facilityId+"')";
       db.doUpdate(query);
     }catch(Exception e){
       e.printStackTrace();
       throw e;
     }finally{}
   }
   /*
   public static WasteBulkOrder createWasteBulkOrder(TcmISDBModel db,int bulkId,Vector orderReqV,Hashtable orderInfo) throws Exception{
     try{
      WasteBulkOrder.insertWasteBulkOrder(db,bulkId,orderInfo);
      for(int i=0;i<orderReqV.size();i++){
        Hashtable h = (Hashtable)orderReqV.elementAt(i);
         int wri = ((Integer)h.get("WASTE_REQUEST_ID")).intValue();
         int li = ((Integer)h.get("LINE_ITEM")).intValue();
         int cId = ((Integer)h.get("CONTAINER_ID")).intValue();
         WasteContainer wc = new WasteContainer(db);
         wc.setContainerId(cId);
         Integer tmp = new Integer(ws.getOrderNo());
         String si = new String(tmp.toString());
         wc.insert("order_no",si,WasteContainer.INT);
      }
     }catch(Exception e){
       e.printStackTrace();
       throw e;
     }finally{
     }
     return ws;
   }

   public void updateOrderRequest(Vector updateV, int orderNo) throws Exception {
    try {
      WasteRequestLineItem wrli = new WasteRequestLineItem(db);
      //set all container with current order no to null
      deleteOrderNo(orderNo);

      //update each container with order no
      for(int i = 0; i < updateV.size(); i++){
        Hashtable h = new Hashtable();
        h = (Hashtable)updateV.elementAt(i);

        Integer order = new Integer(orderNo);
        String cId = (String)h.get("CONTAINER_ID");
        Integer containerId = new Integer(cId);
        WasteContainer wc = new WasteContainer(db);
        wc.setContainerId(containerId.intValue());
        wc.insert("order_no",order.toString(),WasteContainer.INT);
      }
    }catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
   } */

   public void deleteBulkOrder(String bulkId, int orderNo) throws Exception{
    String query = "delete from bulk_order where order_no = " +orderNo;
    query += " and bulk_id = '"+bulkId+"'";
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

     String query = new String("update bulk_order set " + col + "=");
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
     query += " where order_no = " + getOrderNo();
     query += " and bulk_id = '"+getBulkId()+"'";
     query += " and facility_id = '"+getFacilityId()+"'";
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