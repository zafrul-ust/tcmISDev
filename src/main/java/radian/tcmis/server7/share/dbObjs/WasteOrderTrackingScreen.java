package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.both1.helpers.*;
import radian.tcmis.server7.share.db.*;
import java.util.*;
import java.sql.*;



public abstract class WasteOrderTrackingScreen {
  public static final int STATUS_COL = 0;
  public static final int SHIPMENT_COL = 1;
  public static final int VENDOR_COL = 2;
  //public static final int GENERATE_COL = 3;
  public static final int SUBMIT_COL = 3;
  public static final int RESUBMIT_COL = 4;
  public static final int CANCEL_COL = 5;
  public static final int SHIPPED_COL = 6;
  public static final int COPY1_COL = 7;
  public static final int CERTOFDEST_COL = 8;
  public static final int INVOICE_COL = 9;
  public static final int ORDER_COL = 10;

  public static final String[] colHeads = {"Status","Shipment","Vendor","Submitted",
                                           "Resubmitted","Cancelled","Shipped","Copy1 Return","Cert of Disp.","Invoice","Order"};

  public static final int[] colWidths = {62,60,110,78,78,78,78,78,80,50,50};
  public static final int[] colTypes = {BothHelpObjs.TABLE_COL_TYPE_STRING,
                                        BothHelpObjs.TABLE_COL_TYPE_STRING,
                                        BothHelpObjs.TABLE_COL_TYPE_STRING,
                                        BothHelpObjs.TABLE_COL_TYPE_STRING,
                                        BothHelpObjs.TABLE_COL_TYPE_STRING,
                                        BothHelpObjs.TABLE_COL_TYPE_STRING,
                                        BothHelpObjs.TABLE_COL_TYPE_STRING,
                                        BothHelpObjs.TABLE_COL_TYPE_STRING,
                                        BothHelpObjs.TABLE_COL_TYPE_STRING,
                                        BothHelpObjs.TABLE_COL_TYPE_STRING,
                                        BothHelpObjs.TABLE_COL_TYPE_STRING};

  protected TcmISDBModel db;

  public static Hashtable getInitialInfo(TcmISDBModel db, int userId) throws Exception {
    Vector vendorId = new Vector();
    Vector companyName = new Vector();
    Hashtable result = new Hashtable();
    String query = "select company_name,vendor_id from waste_vendor_view";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while(rs.next()){
        companyName.addElement(rs.getString("company_name"));
        vendorId.addElement(rs.getString("vendor_id"));
      }
    }catch(Exception ex){ex.printStackTrace(); throw ex;
    }finally{dbrs.close();}

    result.put("VENDOR_ID",vendorId);
    result.put("COMPANY_NAME",companyName);

    query = "select facility from fac_pref where personnel_id = "+userId+ " order by facility_id";
    Vector facilityV = new Vector();
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while(rs.next()){
        facilityV.addElement(rs.getString("facility_id"));
      }
    }catch(Exception ex){ex.printStackTrace(); throw ex;
    }finally{dbrs.close();}

    result.put("FACILITY_ID",facilityV);

    return result;
  }

  public static Hashtable doSearch(TcmISDBModel db, Hashtable search_info) throws Exception {
    Hashtable dataH = new Hashtable(4);
    Vector dataV = new Vector();
    String orderNo = (String)search_info.get("SEARCH_SHIPMENT_NUM");
    String vendorId = (String)search_info.get("SEARCH_VENDOR");
    String facilityId = (String)search_info.get("SEARCH_FACILITY");
    String storageArea = (String)search_info.get("STORAGE_AREA");
    String shippedAfter = (String)search_info.get("SHIPPED_AFTER");
    String shippedBefore = (String)search_info.get("SHIPPED_BEFORE");

    Boolean draftD = (Boolean)search_info.get("STATUS_DRAFT");
    Boolean submittedD = (Boolean)search_info.get("STATUS_SUBMITTED");
    Boolean cancelledD = (Boolean)search_info.get("STATUS_CANCELLED");
    Boolean shippedD = (Boolean)search_info.get("STATUS_SHIPPED");
    Boolean copy1ReturnD = (Boolean)search_info.get("STATUS_COPY1_RETURN");
    Boolean certOfDispD = (Boolean)search_info.get("STATUS_CERT_OF_DEST");
    Boolean invoiceD = (Boolean)search_info.get("STATUS_INVOICE");

    String query = "select * from waste_shipment_sum_view ";
    String where = " where ";
    Vector conditionV = new Vector();
    if (facilityId.length() > 0) {
      conditionV.addElement(" facility_id = '"+facilityId+"'");
    }
    if (storageArea.length() > 0 && !BothHelpObjs.isBlankString(storageArea) && !storageArea.equalsIgnoreCase("all")) {
      conditionV.addElement(" storage_location_id = '"+storageArea+"'");
    }
    if (orderNo.length() > 0) {
      conditionV.addElement(" order_no = '"+orderNo+"'");
    }
    if (vendorId.length() > 0) {
      conditionV.addElement(" company_name = '"+vendorId+"' ");
    }
    if (shippedAfter.length() > 0) {
      conditionV.addElement(" actual_ship_date > to_date('"+shippedAfter+"','MM/DD/YYYY')");
    }
    if (shippedBefore.length() > 0) {
      conditionV.addElement(" actual_ship_date < to_date('"+shippedBefore+"','MM/DD/YYYY')");
    }


    Vector statusV = new Vector();
    if (draftD.booleanValue())
      statusV.addElement(" status = 'draft'");
    if (submittedD.booleanValue()){
      statusV.addElement(" status = 'submitted'");
    }
    if (cancelledD.booleanValue())
      statusV.addElement(" status = 'cancelled'");
    if (shippedD.booleanValue())
      statusV.addElement(" status = 'shipped'");
    if (invoiceD.booleanValue())
      statusV.addElement(" status = 'invoiced'");

    if (copy1ReturnD.booleanValue())
      statusV.addElement(" manifest_return_date is null");
    if (certOfDispD.booleanValue())
      statusV.addElement(" ( last_cod_date != 'all' and last_cod_date != 'n/a')");

    //combine status clauses with OR;
    String statusQ = "";
    for (int i = 0; i < statusV.size(); i++){
      if (i > 0) {
        statusQ += " or ";
      }
      statusQ += "("+statusV.elementAt(i).toString()+")";
    }
    if(!BothHelpObjs.isBlankString(statusQ)){
      statusQ = "("+statusQ+ ")";
      conditionV.addElement(statusQ);
    }

    //now combine all the conditions with AND
    for (int j = 0; j < conditionV.size(); j++){
      if (j > 0){
        where += " and ";
      }
      where += "("+conditionV.elementAt(j).toString()+")";
    }
    query += where;
    query += " order by shipment_id";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while(rs.next()){
          Object[] oa = new Object[colHeads.length];
          oa[STATUS_COL] = rs.getString("status");
          oa[SHIPMENT_COL] = rs.getString("shipment_id");
          oa[VENDOR_COL] = rs.getString("company_name");
          oa[SUBMIT_COL] = BothHelpObjs.formatDate("toCharfromDB",rs.getString("original_submit_date"));
          oa[RESUBMIT_COL] = BothHelpObjs.formatDate("toCharfromDB",rs.getString("resubmit_date"));
          oa[CANCEL_COL] = BothHelpObjs.formatDate("toCharfromDB",rs.getString("cancel_date"));
          oa[SHIPPED_COL] = BothHelpObjs.formatDate("toCharfromDB",rs.getString("actual_ship_date"));
          oa[INVOICE_COL] = BothHelpObjs.formatDate("toCharfromDB",rs.getString("invoice_date"));
          oa[COPY1_COL] = BothHelpObjs.formatDate("toCharfromDB",rs.getString("manifest_return_date"));
          oa[CERTOFDEST_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("last_cod_date"));
          oa[ORDER_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("order_no"));
          dataV.addElement(oa);
      }
    }catch(Exception ex){ex.printStackTrace(); throw ex;
    }finally{dbrs.close();}
    dataH.put("TABLE_HEADERS",colHeads);
    dataH.put("TABLE_WIDTHS",colWidths);
    dataH.put("TABLE_TYPES",colTypes);
    dataV.trimToSize();
    dataH.put("TABLE_DATA",dataV);
    return dataH;
   }

}

