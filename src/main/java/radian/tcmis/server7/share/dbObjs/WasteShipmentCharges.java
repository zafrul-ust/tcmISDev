package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.both1.helpers.*;
import radian.tcmis.server7.share.db.*;


import java.util.*;
import java.sql.*;

public class WasteShipmentCharges {

   protected TcmISDBModel db;
   protected int orderNo;
   protected String item;
   protected String shipmentCharges;
   protected String cost;
   protected String vendorId;

   public static final String[] shipmentChargesColHeads = {"Qty","Cost Desc","Rate","Cost Type"};
   public static final int[] shipmentChargesColWidths = {80,210,50,0};
   public static final int[] shipmentChargesColTypes = {BothHelpObjs.TABLE_COL_TYPE_NUMERIC_STRING,
                                            BothHelpObjs.TABLE_COL_TYPE_STRING,
                                            BothHelpObjs.TABLE_COL_TYPE_NUMERIC_STRING,
                                            BothHelpObjs.TABLE_COL_TYPE_STRING};


   public WasteShipmentCharges(TcmISDBModel db){
      this.db = db;
   }
   public WasteShipmentCharges(){
   }
   public void setDb(TcmISDBModel db){
      this.db = db;
   }

   //set methods
   public void setOrderNo(int i){orderNo = i;}
   public void setItem(String s){item = s;}
   public void setshipmentCharges(String s){shipmentCharges = s;}
   public void setCost(String s){cost = s;}

   //get methods
   public int getOrderNo(){return orderNo;}
   public String getItem(){return item;}
   public String getShipmentCharges(){return shipmentCharges;}
   public String getCost(){return cost;}

  public boolean updateShipmentCharges(int shipId,Vector updateV) {
    //first clear all charge cost for this shipment
    if (!deleteShipmentCharges(shipId)) {
      return false;
    }

    //now insert each cost type for shipment
    for (int i = 0; i < updateV.size(); i++) {
      Hashtable h = (Hashtable)updateV.elementAt(i);
      String qty = BothHelpObjs.makeBlankFromNull((String)h.get("QTY"));
      if (qty.length() > 0 && !BothHelpObjs.isBlankString(qty)) {
        String costType = (String)h.get("COST_TYPE");
        Float myQty = new Float(qty);
        if (!insertShipmentCharges(shipId,costType,myQty.floatValue())) {
          return false;
        }
      }
    }
    return true;
  }

  public boolean deleteShipmentCharges(int shipId) {
    String query = "delete from waste_shipment_charge where";
    query += " shipment_id = "+shipId;
    try {
      db.doUpdate(query);
    }catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return true;
   }

   public boolean insertShipmentCharges(int shipId, String costType, float quantity) {
    String query = "insert into waste_shipment_charge (shipment_id,cost_type,quantity)";
    query += " values ("+shipId+",'"+costType+"',"+quantity+")";
    try {
      //System.out.println("\n============ insert statement: "+query);
      db.doUpdate(query);
    }catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return true;
   }

  public Hashtable getShipmentChargesInfo()throws Exception {
    Hashtable h = new Hashtable();
    h.put("SHIPMENT_ID",getShipmentId());
    h.put("KEY_COLS",getColKey());
    return h;
  }

  protected Vector getShipmentId()throws Exception {
    Vector v = new Vector();
    String query = "select shipment_id from waste_order_shipment_view";
    query += " where order_no = "+orderNo;
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while(rs.next()){
        v.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("shipment_id")));
      }
    }catch(Exception ex){
      ex.printStackTrace();
      throw ex;
    }finally{
      dbrs.close();
    }
    return v;
  }

  public Hashtable doSearch(int myShipmentId)throws Exception {
    Hashtable h = new Hashtable();
    Vector dataV = new Vector();
    DBResultSet dbrs = null;
    ResultSet rs = null;
    String query = "select cost_type_desc,rate,quantity,cost_type from waste_ship_opt_charge_view";
    query += " where shipment_id = "+myShipmentId;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while(rs.next()){
        Object[] oa4 = new Object[shipmentChargesColHeads.length];
        oa4[0] = BothHelpObjs.makeBlankFromNull(rs.getString("quantity"));;
        oa4[1] = BothHelpObjs.makeBlankFromNull(rs.getString("cost_type_desc"));
        oa4[2] = BothHelpObjs.makeBlankFromNull(rs.getString("rate"));
        oa4[3] = BothHelpObjs.makeBlankFromNull(rs.getString("cost_type"));
        dataV.addElement(oa4);
      }
    }catch(Exception ex2){
      ex2.printStackTrace();
      throw ex2;
    }finally{
      dbrs.close();
    }
    h.put("TABLE_HEADERS",shipmentChargesColHeads);
    h.put("TABLE_WIDTHS",shipmentChargesColWidths);
    h.put("TABLE_TYPES",shipmentChargesColTypes);
    dataV.trimToSize();
    h.put("TABLE_DATA",dataV);
    h.put("KEY_COLS",getColKey());
    return h;
   }

   public Hashtable getColKey() {
    Hashtable h = new Hashtable();
    for (int i=0;i<shipmentChargesColHeads.length;i++) {
      h.put(shipmentChargesColHeads[i],new Integer(i));
    }
    return h;
   }
} //end of class
