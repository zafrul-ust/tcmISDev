package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.server7.share.db.*;

import java.util.*;
import java.sql.*;

public class WasteShipment {
   protected TcmISDBModel db;

   public static final int STRING = 0;
   public static final int DATE = 2;
   public static final int INT = 1;
   public static final int NULLVAL = 3;

   protected int wasteShipmentId;
   protected String vendorShipmentId;
   protected String vendorInvoiceNumber;
   protected String cancelDate;
   protected String invoiceDate;
   protected String invoiceNumber;
   protected String fullPartial;
   protected String shipName;
   protected String plannedShipDate;
   protected String revisionDate;
   protected String actualShipDate;
   protected String vendorInvoiceDate;
   protected int vendorInvoiceAmount;
   protected int orderNo;
   protected String manifestCountry;
   protected String manifestState;
   protected String manifestId;
   protected String manifestReturnedDate;
   protected String stateManifest;

   public WasteShipment(TcmISDBModel db){
      this.db = db;
   }
   public WasteShipment(){
   }
   public void setDb(TcmISDBModel db){
      this.db = db;
   }

   // get Methods
   public int getWasteShipmentId(){return wasteShipmentId;}
   public int getOrderNo(){return orderNo;}
   public String getVendorShipmentId(){return vendorShipmentId;}
   public String getVendorInvoiceNumber(){return vendorInvoiceNumber;}
   public String getInvoiceNumber(){return invoiceNumber;}
   public String getInvoiceDate(){return invoiceDate;}
   public String getCancelDate(){return cancelDate;}
   public String getFullPartial(){return fullPartial;}
   public String getShipName(){return shipName;}
   public String getPlannedShipDate1(){return plannedShipDate;}
   public String getRevisionDate(){return revisionDate;}
   public String getActualShipDate(){return actualShipDate;}
   public String getVendorInvoiceDate(){return vendorInvoiceDate;}
   public int getVendorInvoiceAmount(){return vendorInvoiceAmount;}
   public String getManifestCountry(){return manifestCountry;}
   public String getManifestState(){return manifestState;}
   public String getManifestId(){return manifestId;}
   public String getManifestReturnedDate(){return manifestReturnedDate;}
   public String getStateManifest(){return stateManifest;}

   // set Methods
   public void setWasteShipmentId(int s){wasteShipmentId = s;}
   public void setOrderNo(int s){orderNo = s;}
   public void setShipName(String s){shipName = s;}
   public void setFullPartial(String s){fullPartial = s;}
   public void setVendorShipmentId(String s){vendorShipmentId = s;}
   public void setVendorInvoiceNumber(String s){vendorInvoiceNumber = s;}
   public void setCancelDate(String s){cancelDate = s;}
   public void setInvoiceNumber(String s){invoiceNumber = s;}
   public void setInvoiceDate(String s){invoiceDate = s;}
   public void setPlannedShipDate(String s){plannedShipDate = s;}
   public void setRevisionDate(String s){revisionDate = s;}
   public void setActualShipDate(String s){actualShipDate = s;}
   public void setVendorInvoiceDate(String s){vendorInvoiceDate = s;}
   public void setVendorInvoiceAmount(int s){vendorInvoiceAmount = s;}
   public void setManifestCountry(String s){manifestCountry = s;}
   public void setManifestState(String s){manifestState = s;}
   public void setManifestId(String s){manifestId = s;}
   public void setManifestReturnedDate(String s){manifestReturnedDate = s;}
   public void setStateManifest(String s){stateManifest = s;}

   /*
  public Hashtable submitRequest(String action) throws Exception {
   //send email to vendor and update submit date
  }   */

   public void delete() throws Exception {
    try {
      WasteRequestLineItem wrli = new WasteRequestLineItem(db);
      wrli.setWasteRequestId(getWasteShipmentId());
      wrli.delete();

      String query = "delete waste_request where waste_request_id = " + getWasteShipmentId();
      DBResultSet dbrs = null;
      db.doUpdate(query);

      }catch (Exception e) {
        e.printStackTrace();
        HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
        throw e;
      }finally{}
       return;
    }

   public static void cancelShipment(TcmISDBModel db,int orderNo) throws Exception {
    String query = "update waste_shipment set cancel_date = SYSDATE where order_no = "+orderNo;
    try {
      db.doUpdate(query);
    }catch (Exception e) { e.printStackTrace(); throw e;
    }finally{}
   }

   public void load()throws Exception{
     String query = "select * from waste_shipment where shipment_id = '"+getWasteShipmentId()+"'";
     DBResultSet dbrs = null;
     ResultSet rs = null;
     int myCount = 0;
     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()){
         setOrderNo(BothHelpObjs.makeZeroFromNull(rs.getString("order_no")));
         setCancelDate(BothHelpObjs.makeBlankFromNull(rs.getString("cancel_date")));
         //setVendorInvoiceNumber(BothHelpObjs.makeBlankFromNull(rs.getString("vendor_invoice_number")));
         setVendorShipmentId(BothHelpObjs.makeBlankFromNull(rs.getString("vendor_shipment_id")));
         setShipName(BothHelpObjs.makeBlankFromNull(rs.getString("ship_name")));
         setFullPartial(BothHelpObjs.makeBlankFromNull(rs.getString("full_partial")));
         //setInvoiceDate(BothHelpObjs.makeBlankFromNull(rs.getString("invoice_date")));
         //setInvoiceNumber(BothHelpObjs.makeBlankFromNull(rs.getString("invoice_number")));
         setPlannedShipDate(BothHelpObjs.makeBlankFromNull(rs.getString("planned_ship_date")));
         setRevisionDate(BothHelpObjs.makeBlankFromNull(rs.getString("revision_date")));
         setActualShipDate(BothHelpObjs.makeBlankFromNull(rs.getString("actual_ship_date")));
         //setVendorInvoiceDate(BothHelpObjs.makeBlankFromNull(rs.getString("vendor_invoice_date")));
         //setVendorInvoiceAmount(BothHelpObjs.makeZeroFromNull(rs.getString("vendor_invoice_amount")));
         setManifestCountry(BothHelpObjs.makeBlankFromNull(rs.getString("manifest_country")));
         setManifestState(BothHelpObjs.makeBlankFromNull(rs.getString("manifest_state")));
         setManifestId(BothHelpObjs.makeBlankFromNull(rs.getString("manifest_id")));
         setManifestReturnedDate(BothHelpObjs.makeBlankFromNull(rs.getString("manifest_return_date")));
         setStateManifest(BothHelpObjs.makeBlankFromNull(rs.getString("state_manifest")));
       }
     }catch (Exception e) { e.printStackTrace(); throw e;
     }finally{dbrs.close();}
   }

   public static WasteShipment insertWasteShipment(TcmISDBModel db,int orderNo)throws Exception{
     try{
       int next = DbHelpers.getNextVal(db,"waste_shipment_seq");

       //now insert info in waste shipment
       String query = "insert into waste_shipment (shipment_id,order_no)";
       query += " values ("+next+","+orderNo+")";
       db.doUpdate(query);

       //running this for the trigger to set in
       query = "insert into waste_shipment_charge (shipment_id,cost_type,quantity)";
       query += " select '"+next+"',r.cost_type,r.default_quantity from waste_shipment_charge_rate r,waste_order o";
       query += " where o.order_no = "+orderNo;
       query += " and o.facility_id = r.facility_id";
       query += " and o.vendor_id = r.vendor_id";
       query += " and r.default_quantity > 0";
       db.doUpdate(query);


       WasteShipment ws = new WasteShipment(db);
       ws.setWasteShipmentId(next);
       ws.load();
       return ws;
     }catch(Exception e){
       e.printStackTrace();
       throw e;
     }finally{}
   }

   public static WasteShipment createWasteShipment(TcmISDBModel db,int orderNo,Vector shipReqV,Hashtable shipmentInfo) throws Exception{
     WasteShipment ws = null;
     try{
      ws = WasteShipment.insertWasteShipment(db,orderNo);
      for(int i=0;i<shipReqV.size();i++){
        Hashtable h = (Hashtable)shipReqV.elementAt(i);
         int wri = ((Integer)h.get("WASTE_REQUEST_ID")).intValue();
         int li = ((Integer)h.get("LINE_ITEM")).intValue();
         int cId = ((Integer)h.get("CONTAINER_ID")).intValue();
         WasteContainer wc = new WasteContainer(db);
         wc.setContainerId(cId);
         Integer tmp = new Integer(ws.getWasteShipmentId());
         String si = new String(tmp.toString());
         wc.insert("shipment_id",si,WasteContainer.INT);
      }
     }catch(Exception e){
       e.printStackTrace();
       throw e;
     }finally{
     }
     return ws;
   }

   public void updateShipmentContainer(int orderNo,String containerShipmentId,String actualPickup,String manifestId,String state, String country,String manifestReturn,Vector containerFormInfo, String transCost, Vector costInfo) throws Exception {
    String query = "update waste_shipment set";
    String tmp = "";
    /*
    if (!BothHelpObjs.isBlankString(vendorOrderNo)) {
      tmp += " vendor_shipment_id = '"+vendorOrderNo+"',";
    }
    if (!BothHelpObjs.isBlankString(plannedPickup)) {
      tmp += " planned_ship_date = to_date('"+plannedPickup+"','MM/dd/yyyy'),";
    } */
    if (!BothHelpObjs.isBlankString(actualPickup)) {
      tmp += " actual_ship_date = to_date('"+actualPickup+"','MM/dd/yyyy'),";
    }
    if (!BothHelpObjs.isBlankString(manifestReturn)) {
      tmp += " manifest_return_date = to_date('"+manifestReturn+"','MM/dd/yyyy'),";
    }
    if (!BothHelpObjs.isBlankString(manifestId)) {
      tmp += " manifest_id = '"+manifestId+"',";
      tmp += " manifest_state = '"+state+"',";
      tmp += " manifest_country = '"+country+"',";
    }
    if (!BothHelpObjs.isBlankString(transCost)) {
      tmp += " transportation_cost = "+transCost+",";
    }else {
      tmp += " transportation_cost = null,";
    }
    //removing the last commas
    if (tmp.length() > 1) {
      tmp = tmp.substring(0,tmp.length()-1);
      query += tmp + " where shipment_id = "+containerShipmentId;
      try {
        db.doUpdate(query);
      }catch(Exception e){
        e.printStackTrace();
        throw e;
      }finally{}
    }

    //now update manifest line item
    for (int i = 0; i < containerFormInfo.size(); i++) {
      Hashtable h = (Hashtable)containerFormInfo.elementAt(i);
      String letter = (String)h.get("MANIFEST_LINE_NO");
      String cod = (String)h.get("COD");
      query = "update manifest_line_item set";
      if (!BothHelpObjs.isBlankString(cod)){
        query += " destruction_date = to_date('"+cod+"','MM/dd/yyyy')";
        query += " where shipment_id = "+containerShipmentId;
        query += " and manifest_line_letter = '"+letter+"'";
        try {
          db.doUpdate(query);
        }catch(Exception e){
          e.printStackTrace();
          throw e;
        }finally{}
      }
    }
    /*
    //now update/insert cost info
    //first delete all cost record with current shipment id
    query = "delete from waste_shipment_charge";
    query += " where shipment_id = "+containerShipmentId;
    try {
      db.doUpdate(query);
    }catch(Exception e){
      e.printStackTrace();
      throw e;
    }

    //next re-insert cost info for current shipment
    for (int k = 0; k < costInfo.size(); k++) {
      Hashtable costH = (Hashtable)costInfo.elementAt(k);
      String costQty = (String)costH.get("QUANTITY");
      String costType = (String)costH.get("COST_TYPE");
      if (!BothHelpObjs.isBlankString(costQty)) {
        query = "insert into waste_shipment_charge (shipment_id,cost_type,quantity)";
        query += " values("+containerShipmentId+",'"+costType+"',"+costQty+")";
        try {
          db.doUpdate(query);
        }catch(Exception e){
          e.printStackTrace();
          throw e;
        }
      }
    } //end of for loop
    */
   }  //end of method

   public void updateShipmentBulk(int orderNo, Vector bulkInfo) throws Exception {
    String shipmentId = null;
    //String vendorOrderNo = null;
    //String plannedPickup = null;
    String actualPickup = null;
    String manifestNo = null;
    String state = null;
    String country = null;
    String manifestReturn = null;
    //String plannedAmount = null;
    String disposition = null;
    String actualAmount = null;
    try {
      for (int i = 0; i < bulkInfo.size();i++) {
        String query = "update waste_shipment set";
        String query2 = "update bulk_order set";
        String query3 = "update manifest_line_item set";
        Hashtable h = (Hashtable)bulkInfo.elementAt(i);
        shipmentId = (String)h.get("SHIPMENT_ID");
        //vendorOrderNo = (String)h.get("VENDOR_ORDER_NO");
        //plannedPickup = (String)h.get("PLANNED_PICKUP");
        //plannedAmount = (String)h.get("PLANNED_AMOUNT");
        actualPickup = (String)h.get("ACTUAL_PICKUP");
        manifestNo = (String)h.get("MANIFEST_NO");
        state = (String)h.get("STATE");
        country = new String("USA");
        manifestReturn = (String)h.get("COPY_RETURNED");
        actualAmount = (String)h.get("ACTUAL_AMOUNT");

        //set it to "" since i don't know what's it is
        disposition = (String)h.get("DISPOSITION");
        String tmp = "";
        String tmp2 = "";
        String tmp3 = "";
        /*
        if (!BothHelpObjs.isBlankString(vendorOrderNo)) {
          tmp += " vendor_shipment_id = '"+vendorOrderNo+"',";
        }
        if (!BothHelpObjs.isBlankString(plannedPickup)) {
          tmp += " planned_ship_date = to_date('"+plannedPickup+"','MM/dd/yyyy'),";
        }
        if (!BothHelpObjs.isBlankString(disposition)) {
          tmp += " disposition = '"+disposition+"',";
        } */
        if (!BothHelpObjs.isBlankString(actualPickup)) {
          tmp += " actual_ship_date = to_date('"+actualPickup+"','MM/dd/yyyy'),";
        }
        if (!BothHelpObjs.isBlankString(manifestReturn)) {
          tmp += " manifest_return_date = to_date('"+manifestReturn+"','MM/dd/yyyy'),";
        }
        if (!BothHelpObjs.isBlankString(manifestNo)) {
          tmp += " manifest_id = '"+manifestNo+"',";
          tmp += " manifest_state = '"+state+"',";
          tmp += " manifest_country = '"+country+"',";
        }
        //removing the last commas
        if (tmp.length() > 1) {
          tmp = tmp.substring(0,tmp.length()-1);
          query += tmp + " where shipment_id = "+shipmentId;
          db.doUpdate(query);
        }

        /*now updating bulk order amount
        if (!BothHelpObjs.isBlankString(plannedAmount)) {
          tmp2 += " planned_amount = '"+plannedAmount+"',";
        } */
        if (!BothHelpObjs.isBlankString(actualAmount)) {
          tmp2 += " actual_amount = '"+actualAmount+"',";
        }
        //removing the last commas
        if (tmp2.length() > 1) {
          tmp2 = tmp2.substring(0,tmp2.length()-1);
          query2 += tmp2 + " where shipment_id = "+shipmentId;
          db.doUpdate(query2);
        }

        //now update manifest_line_item for each bulk order
        if (!BothHelpObjs.isBlankString(disposition)) {
          tmp3 += " disposition = to_date('"+disposition+"','MM/dd/yyyy')";
        }
        if (tmp3.length() > 0) {
          query3 += tmp3 + " where shipment_id = "+shipmentId;
          query3 += "and manifest_line_letter = '"+manifestNo+"'";
          db.doUpdate(query3);
        }

      }
    }catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
   }

   public void updateShipmentRequest(Vector updateV, int shipmentId) throws Exception {
    try {
      WasteRequestLineItem wrli = new WasteRequestLineItem(db);
      //set all container with current shipment id to null
      deleteShipmentId(shipmentId);

      //update each container with shipment id
      for(int i = 0; i < updateV.size(); i++){
        Hashtable h = new Hashtable();
        h = (Hashtable)updateV.elementAt(i);
        Integer shipId = new Integer(shipmentId);
        String cId = (String)h.get("CONTAINER_ID");
        Integer containerId = new Integer(cId);
        WasteContainer wc = new WasteContainer(db);
        wc.setContainerId(containerId.intValue());
        wc.insert("shipment_id",shipId.toString(),WasteContainer.INT);
      }
    }catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
   }

   public void deleteShipmentId(int shipmentId) throws Exception{
    String query = "select * from waste_container where shipment_id = " +shipmentId;
    WasteContainer wc = new WasteContainer(db);
    DBResultSet dbrs = null;
    ResultSet rs = null;
    String val = "";
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while (rs.next()) {
        wc.setWasteRequestId(rs.getInt("waste_request_id"));
        wc.setLineItem(rs.getInt("line_item"));
        wc.insert("shipment_id",val,WasteContainer.NULLVAL);
      }
    }catch (Exception e) {
      e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);  throw e;
    } finally{
      dbrs.close();
    }
   }

  public void insert(String col,String val,int type)  throws Exception {
     if(col.equalsIgnoreCase("NOTES")) {
       val = HelpObjs.singleQuoteToDouble(val);
     }

     Integer I;
     DBResultSet dbrs = null;
     ResultSet rs = null;

     String query = new String("update waste_shipment set " + col + "=");
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
     query += " where shipment_id = " + getWasteShipmentId();
     //System.out.println("&&&&&&&&&&&&&&&& " + query);
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

