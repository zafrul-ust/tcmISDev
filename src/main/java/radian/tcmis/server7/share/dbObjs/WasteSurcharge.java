/*SQLWKS> desc waste_lab_pack_request

*/
package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.both1.helpers.*;
import radian.tcmis.server7.share.db.*;


import java.util.*;
import java.sql.*;

public class WasteSurcharge {

   protected TcmISDBModel db;
   protected int orderNo;
   protected String item;
   protected String surcharge;
   protected String cost;
   protected String vendorId;
   protected int shipmentId;

   public static final String[] surchargeColHeads = {"Cost","Item","Pakaging","Surcharge","Surcharge ID","Waste Item ID","Bulk Or Container"};
   public static final int[] surchargeColWidths = {80,300,80,225,0,0,0};
   public static final int[] surchargeColTypes = {BothHelpObjs.TABLE_COL_TYPE_STRING,
                                            BothHelpObjs.TABLE_COL_TYPE_STRING,
                                            BothHelpObjs.TABLE_COL_TYPE_STRING,
                                            BothHelpObjs.TABLE_COL_TYPE_STRING,
                                            BothHelpObjs.TABLE_COL_TYPE_STRING,
                                            BothHelpObjs.TABLE_COL_TYPE_STRING,
                                            BothHelpObjs.TABLE_COL_TYPE_STRING};


   public WasteSurcharge(TcmISDBModel db){
      this.db = db;
   }
   public WasteSurcharge(){
   }
   public void setDb(TcmISDBModel db){
      this.db = db;
   }

   //set methods
   public void setOrderNo(int i){orderNo = i;}
   public void setItem(String s){item = s;}
   public void setSurcharge(String s){surcharge = s;}
   public void setCost(String s){cost = s;}
   public void setShipmentId(int i){shipmentId = i;}

   //get methods
   public int getOrderNo(){return orderNo;}
   public String getItem(){return item;}
   public String getSurcharge(){return surcharge;}
   public String getCost(){return cost;}
   public int getShipmentId(){return shipmentId;}


   public boolean updateSurchargeInfo(Vector updateV) {
    boolean bulkSurchargeCleared = false;
    boolean containerSurchargeCleared = false;
    for (int i = 0; i < updateV.size(); i++) {
      Hashtable h = (Hashtable)updateV.elementAt(i);
      String isBulk = (String)h.get("BULK_OR_CONTAINER");
      String wasteItemId = (String)h.get("WASTE_ITEM_ID");
      String surchargeId = (String)h.get("SURCHARGE_ID");
      String myCost = (String)h.get("COST");
      if (BothHelpObjs.isBlankString(myCost)) {
        myCost = "0";
      }
      Double currentCost = new Double(myCost);
      double unitCost = 0;
      boolean success = false;
      Vector containerID = null;
      Vector bulkID = null;
      if (isBulk.equalsIgnoreCase("B")) {
        if (!bulkSurchargeCleared) {
          if (deleteBulkSurcharge()) {
            bulkSurchargeCleared = true;
          }else {
            return false;
          }
        }
        if (currentCost.doubleValue() <= 0.0) {
          //System.out.println("\n --------- shipping");
          continue;
        }
        bulkID = getBulkIDs();
        if (bulkID.size() > 0 ) {
          unitCost = currentCost.doubleValue() / bulkID.size();
          for (int k = 0; k < bulkID.size(); k++) {
            Hashtable bulkH = (Hashtable)bulkID.elementAt(k);
            String tempBulkId = (String)bulkH.get("BULK_ID");
            String tempFacilityId = (String)bulkH.get("FACILITY_ID");
            String tempVendorId = (String)bulkH.get("VENDOR_ID");
            success = insertBulkSurcharge(tempFacilityId,tempBulkId,tempVendorId,surchargeId,1,unitCost);
            if (!success) {
              return false;
            }
          }
        }else {
          return false;
        }
      }else {
        if (!containerSurchargeCleared) {
          if (deleteContainerSurcharge()) {
            containerSurchargeCleared = true;
          }else {
            return false;
          }
        }
        if (currentCost.doubleValue() <= 0.0) {
          //System.out.println("\n --------- shipping");
          continue;
        }
        containerID = getContainerIDs(wasteItemId);
        if (containerID.size() > 0) {
          unitCost = currentCost.doubleValue() / containerID.size();
          for (int j = 0; j < containerID.size(); j++) {
            String tempContainerId = (String)containerID.elementAt(j);
            success = insertContainerSurcharge(tempContainerId,vendorId,surchargeId,1,unitCost);
            if (!success) {
              return false;
            }
          }
        }else {
          return false;
        }
      }
    }
    return true;
   }

   public Vector getBulkIDs() {
    String query = "select b.facility_id,b.bulk_id,wo.vendor_id from bulk_order b, waste_order wo";
    query += " where wo.order_no = b.order_no";
    query += " and b.shipment_id = "+getShipmentId();
    DBResultSet dbrs = null;
    ResultSet rs = null;
    Vector bulkId = new Vector();
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while (rs.next()){
        Hashtable h = new Hashtable();
        h.put("FACILITY_ID",BothHelpObjs.makeBlankFromNull(rs.getString("facility_id")));
        h.put("BULK_ID",BothHelpObjs.makeBlankFromNull(rs.getString("bulk_id")));
        h.put("VENDOR_ID",BothHelpObjs.makeBlankFromNull(rs.getString("vendor_id")));
        bulkId.addElement(h);
      }
    }catch (Exception e) {
      e.printStackTrace();
      return bulkId;
    }
    return bulkId;
   }

   public boolean deleteBulkSurcharge() {
    String query = "delete from bulk_order_surcharge where";
    query += " order_no in (select order_no from bulk_order where shipment_id = " + getShipmentId() + ")";
    try {
      db.doUpdate(query);
    }catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return true;
   }

   public boolean insertBulkSurcharge(String facilityId, String bulkId, String vendorId, String surchargeID, int quantity, double unitCost) {
    String query = "insert into bulk_order_surcharge (facility_id,bulk_id,order_no,vendor_id,surcharge_id,quantity,unit_cost)";
    query += " select '" + facilityId + "', '" + bulkId + "', order_no, '" + vendorId + "', '" + BothHelpObjs.changeSingleQuotetoTwoSingleQuote(surchargeID) + "', " + quantity + ", " + unitCost + " from bulk_order where shipment_id = " + this.getShipmentId();

    try {
      db.doUpdate(query);
    }catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return true;
   }

   public Vector getContainerIDs(String wasteItemId) {
    String query = "select wc.container_id,wo.vendor_id from waste_container wc,waste_order wo,waste_request_line_item wrli";
    query += " where wc.waste_request_id = wrli.waste_request_id";
    query += " and wc.line_item = wrli.line_item";
    query += " and wc.order_no = wo.order_no";
    query += " and wc.waste_item_id = '"+wasteItemId+"'";
    query += " and wc.shipment_id = "+getShipmentId();
    DBResultSet dbrs = null;
    ResultSet rs = null;
    Vector containerId = new Vector();
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while (rs.next()){
        containerId.addElement(rs.getString("container_id"));
        vendorId = BothHelpObjs.makeBlankFromNull(rs.getString("vendor_id"));
      }
    }catch (Exception e) {
      e.printStackTrace();
      return containerId;
    }
    return containerId;
   }

   public boolean deleteContainerSurcharge() {
    String query = "delete from waste_container_surcharge where";
    query += " container_id in (select container_id from waste_container where shipment_id = "+this.getShipmentId()+")";
    try {
      db.doUpdate(query);
    }catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return true;
   }

   public boolean insertContainerSurcharge(String containerId, String vendorId, String surchargeId, int quantity, double unitCost) {
    String query = "insert into waste_container_surcharge (container_id,vendor_id,surcharge_id,quantity,unit_cost)";
    query += " values ("+containerId+",'"+vendorId+"','"+BothHelpObjs.changeSingleQuotetoTwoSingleQuote(surchargeId)+"',"+quantity+",'"+unitCost+"')";
    try {
      //System.out.println("\n\n-------- what's going on: "+query);
      db.doUpdate(query);
    }catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return true;
   }


   public Hashtable getSurchargeInfo()throws Exception {
    Hashtable h = new Hashtable();
    Vector dataV = new Vector();
    String query = "select * from waste_cont_surcharge_sum_view where shipment_id = "+getShipmentId();
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while (rs.next()){
        Object[] oa4 = new Object[surchargeColHeads.length];
        String myItem = "Container: ";
        String vProfile = BothHelpObjs.makeBlankFromNull(rs.getString("vendor_profile_id"));
        String wItemId = BothHelpObjs.makeBlankFromNull(rs.getString("waste_item_id"));
        myItem += vProfile;
        myItem += " - "+BothHelpObjs.makeBlankFromNull(rs.getString("profile_desc"));
        oa4[0] = BothHelpObjs.makeBlankFromNull(rs.getString("cost"));
        oa4[1] = myItem;
        oa4[2] = BothHelpObjs.makeBlankFromNull(rs.getString("packaging"));
        oa4[3] = BothHelpObjs.makeBlankFromNull(rs.getString("surcharge_desc"));
        oa4[4] = BothHelpObjs.makeBlankFromNull(rs.getString("surcharge_id"));
        oa4[5] = wItemId;
        oa4[6] = "C";
        dataV.addElement(oa4);
      }
    }catch (Exception ee) {
      ee.printStackTrace();
      throw ee;
    }finally{
      dbrs.close();
    }

    getBulkSurchargeInfo(dataV);
    h.put("TABLE_HEADERS",surchargeColHeads);
    h.put("TABLE_WIDTHS",surchargeColWidths);
    h.put("TABLE_TYPES",surchargeColTypes);
    dataV.trimToSize();
    h.put("TABLE_DATA",dataV);
    h.put("KEY_COLS",getColKey());
    return h;
   }

  public void getBulkSurchargeInfo (Vector dataV) throws Exception {
    String query = "select * from waste_bulk_surcharge_sum_view where shipment_id = "+getShipmentId();
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while (rs.next()){
        Object[] oa4 = new Object[surchargeColHeads.length];
        String myItem = "Bulk: ";
        String vProfile = BothHelpObjs.makeBlankFromNull(rs.getString("vendor_profile_id"));
        String wItemId = BothHelpObjs.makeBlankFromNull(rs.getString("waste_item_id"));
        myItem += vProfile;
        myItem += " - "+BothHelpObjs.makeBlankFromNull(rs.getString("profile_desc"));
        oa4[0] = BothHelpObjs.makeBlankFromNull(rs.getString("cost"));
        oa4[1] = myItem;
        oa4[2] = BothHelpObjs.makeBlankFromNull(rs.getString("packaging"));
        oa4[3] = BothHelpObjs.makeBlankFromNull(rs.getString("surcharge_desc"));
        oa4[4] = BothHelpObjs.makeBlankFromNull(rs.getString("surcharge_id"));
        oa4[5] = wItemId;
        oa4[6] = "B";
        dataV.addElement(oa4);
      }
    }catch (Exception ee) {
      ee.printStackTrace();
      throw ee;
    }finally{
      dbrs.close();
    }
  }

   public Hashtable getColKey() {
    Hashtable h = new Hashtable();
    for (int i=0;i<surchargeColHeads.length;i++) {
      h.put(surchargeColHeads[i],new Integer(i));
    }
    return h;
   }
} //end of class


