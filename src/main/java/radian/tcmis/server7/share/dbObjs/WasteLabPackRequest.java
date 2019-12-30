/*SQLWKS> desc waste_lab_pack_request
------------------------------ -------- ----
FACILITY_ID                    NOT NULL VARCHAR2(30)
WASTE_LOCATION_ID              NOT NULL VARCHAR2(30)
VENDOR_ID                      NOT NULL VARCHAR2(20)
DRUM_ESTIMATE                           NUMBER(38)
REQUEST_DATE                            DATE

*/
package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.server7.share.db.*;


import java.util.*;
import java.sql.*;

public class WasteLabPackRequest {

   protected TcmISDBModel db;
   protected String vendorId;
   protected String facilityId;
   protected String storageArea;
   protected String drumEst;
   protected String requestDate;
   protected String lastRequestor;
   protected String preferredServiceDate;

   public static final String[] colHeads = {"Order #"};
   public static final int[] colWidths = {185};
   public static final int[] colTypes = {BothHelpObjs.TABLE_COL_TYPE_NUMBER};

   public WasteLabPackRequest(TcmISDBModel db){
      this.db = db;
   }
   public WasteLabPackRequest(){
   }
   public void setDb(TcmISDBModel db){
      this.db = db;
   }

   //set methods
   public void setVendorId(String s){vendorId = s;}
   public void setFacilityId(String s){facilityId = s;}
   public void setStorageArea(String s){storageArea = s;}
   public void setDrumEst(String s){drumEst = s;}
   public void setPreferredServiceDate(String s){preferredServiceDate = s;}
   public void setRequestDate(String s){requestDate = s;}
   public void setLastRequestor(String s){lastRequestor = s;}

   //get methods
   public String getVendorId(){return vendorId;}
   public String getFacilityId(){return facilityId;}
   public String getStorageArea(){return storageArea;}
   public String getDrumEst(){return drumEst;}
   public String getPreferredServiceDate(){return preferredServiceDate;}
   public String getRequestDate(){return requestDate;}
   public String getLastRequestor(){return lastRequestor;}

   public Hashtable getLabPackOrders()throws Exception {
    Hashtable dataH = new Hashtable(4);
    Vector dataV = new Vector();
    String query = "select wo.order_no from waste_order wo, waste_shipment ws where";
    query += " ws.lab_pack_drum_estimate is not null and ws.actual_ship_date is null and";
    query += " wo.order_no = ws.order_no and";
    query += " wo.facility_id = '"+getFacilityId()+"' and";
    query += " wo.storage_location_id = '"+getStorageArea()+"' and";
    query += " wo.vendor_id = '"+getVendorId()+"'";

    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while(rs.next()){
        Object[] oa4 = new Object[colHeads.length];
        oa4[0] = BothHelpObjs.makeBlankFromNull(rs.getString("order_no"));
        dataV.addElement(oa4);
      }
    }catch(Exception ex2){
      ex2.printStackTrace();
      throw ex2;
    }finally{
      dbrs.close();
    }
    dataH.put("TABLE_HEADERS",colHeads);
    dataH.put("TABLE_WIDTHS",colWidths);
    dataH.put("TABLE_TYPES",colTypes);
    dataV.trimToSize();
    dataH.put("TABLE_DATA",dataV);
    return dataH;
   }

   public void load()throws Exception{
     String query = "select count(*) from waste_vendor_facility where vendor_id = '"+getVendorId()+"'";
     query += " and facility_id = '"+getFacilityId()+"'";
     query += " and waste_location_id = '"+getStorageArea()+"'";
     query += " and lab_pack_drum_estimate is not null";
     DBResultSet dbrs = null;
     ResultSet rs = null;
     int myCount = 0;
     try {
      myCount = DbHelpers.countQuery(db,query);
      if (myCount > 0) {
        query = "select lab_pack_drum_estimate,lab_pack_request_date,preferred_service_date,full_name from waste_lab_pack_request_view where vendor_id = '"+getVendorId()+"'";
        query += " and facility_id = '"+getFacilityId()+"'";
        query += " and waste_location_id = '"+getStorageArea()+"'";
        try {
          dbrs = db.doQuery(query);
          rs=dbrs.getResultSet();
          while (rs.next()){
            setDrumEst(BothHelpObjs.makeBlankFromNull(rs.getString("lab_pack_drum_estimate")));
            setRequestDate(BothHelpObjs.formatDate("toCharfromDB",BothHelpObjs.makeBlankFromNull(rs.getString("lab_pack_request_date"))));
            setPreferredServiceDate(BothHelpObjs.formatDate("toCharfromDB",BothHelpObjs.makeBlankFromNull(rs.getString("preferred_service_date"))));
            setLastRequestor(BothHelpObjs.makeBlankFromNull(rs.getString("full_name")));
          }
        }catch (Exception ee) {
          ee.printStackTrace();
          throw ee;
        }finally{dbrs.close();}
      }else {
        setDrumEst("");
        setRequestDate("");
        setLastRequestor("");
        setPreferredServiceDate("");
      }
     }catch (Exception e) {
      e.printStackTrace();
      throw e;
     }
   }

  public void delete() throws Exception {
    String query = "update waste_vendor_facility set lab_pack_drum_estimate = null, lab_pack_request_date = null, lab_pack_requester = null";
    query += " where vendor_id = '"+getVendorId()+"'";
    query += " and facility_id = '"+getFacilityId()+"'";
    query += " and waste_location_id = '"+getStorageArea()+"'";
    try {
      db.doUpdate(query);
    }catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }


  //public String submitLabPackRequest(String actionText, Integer userId, String orderN) throws Exception {
  public String submitLabPackRequest(String actionText, Integer userId) throws Exception {
    String MSG = "";
    String query = "update waste_vendor_facility set lab_pack_drum_estimate = '"+getDrumEst()+"',lab_pack_request_date = SYSDATE,lab_pack_requester = "+userId;
    if (!BothHelpObjs.isBlankString(getPreferredServiceDate())) {
      query += ",preferred_service_date = to_date('"+getPreferredServiceDate()+"','MM/dd/yyyy HH24:MI:SS')";
    }else {
      query += ",preferred_service_date = null";
    }
    query += " where vendor_id = '"+getVendorId()+"'";
    query += " and facility_id = '"+getFacilityId()+"'";
    query += " and waste_location_id = '"+getStorageArea()+"'";

    WasteOrder wo = null;
    int orderNo = 0;

    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      db.doUpdate(query);
      if (actionText.equalsIgnoreCase("Submit")) {
        Hashtable h = new Hashtable();
        h.put("FACILITY_ID",getFacilityId());
        h.put("VENDOR_ID",getVendorId());
        h.put("STORAGE_ID",getStorageArea());
        wo = WasteOrder.insertWasteOrder(db,userId.intValue(),h);
        orderNo = wo.getOrderNo();
        wo.insert("original_submit_date","nowDate",WasteOrder.DATE);
      }else {
        //Integer n = new Integer(orderN);
        Integer n = new Integer(0);
        orderNo = n.intValue();
        wo = new WasteOrder(db);
        wo.setOrderNo(orderNo);
        wo.insert("resubmit_date","nowDate",WasteOrder.DATE);
      }
      MSG = sendEmail(userId,actionText,orderNo);
    }catch (Exception e) {
      e.printStackTrace();
      return MSG;
    }
    return MSG;
   }

   public String sendEmail(Integer userId,String actionText, int orderNo) {
   String MSG = "";
   String esub = "";
   String emsg = "";
   String[] recipients;
    try {
      Personnel p = new Personnel(db);
      p.setPersonnelId(userId.intValue());
      p.load();
      if (actionText.equalsIgnoreCase("Submit")) {
        esub = "Lab Pack Requested";
        emsg = " Lab Pack request was requested:\n";
        emsg += " Requested by              : "+p.getFullName()+"\n";
      }else {
        esub = "Lab Pack Re-Requested";
        emsg = " Lab Pack request was modified:\n";
        emsg += " Re-Requested by           : "+p.getFullName()+"\n";
      }
      emsg += " Phone number              : "+p.getPhone()+"\n";
      emsg += " Facility                  : "+getFacilityId()+"\n";
      emsg += " Waste Location            : "+getStorageArea()+"\n";
      emsg += " Estimate number of drum(S): "+getDrumEst()+"\n";
      emsg += " Preferred service date    : "+getPreferredServiceDate()+"\n";
      emsg += " Order No                  : "+orderNo+"\n";
      //System.out.println("\n\n--------------- mail msg: "+esub+" - "+emsg);

      recipients = getVendorEmailAddress();
      HelpObjs.sendMail(esub,emsg,recipients);
      //HelpObjs.sendMail(db,esub,emsg,userId.intValue(),false);
      MSG = "Request processed";
    } catch (Exception e){
        e.printStackTrace();
        return MSG;
    } finally {
        //db.close();
    }
    return MSG;
  }

  public String[] getVendorEmailAddress() {
    String[] recipients;
    String query = "select email from waste_vendor_facility";
    query += " where vendor_id = '"+getVendorId()+"'";
    query += " and facility_id = '"+getFacilityId()+"'";
    query += " and waste_location_id = '"+getStorageArea()+"'";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    String vendorEmail = null;
    try{
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while (rs.next()){
        vendorEmail = BothHelpObjs.makeBlankFromNull(rs.getString("email"));
      }
    }catch(Exception e) {
      e.printStackTrace();
      vendorEmail = "tngo@haastcm.com";
      recipients = new String[]{vendorEmail};
      return recipients;
    }finally {
      dbrs.close();
    }

    if(BothHelpObjs.isBlankString(vendorEmail)) {
      vendorEmail = "tngo@haastcm.com";
      recipients = new String[]{vendorEmail};
    }else{
      StringTokenizer st = new StringTokenizer(vendorEmail,",");
      recipients = new String[st.countTokens()];
      int i = 0;
      while (st.hasMoreElements()) {
        recipients[i] = st.nextElement().toString();
        i++;
      }
    }
    return recipients;
  } //end of getVendorEmailAddress
} //end of class
