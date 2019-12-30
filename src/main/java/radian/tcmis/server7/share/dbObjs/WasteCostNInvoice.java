package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.server7.share.db.*;
import java.util.*;
import java.sql.*;


public abstract class WasteCostNInvoice {
    public static final int STRING = 0;
    public static final int DATE = 2;
    public static final int INT = 1;
    public static final int NULLVAL = 3;

    final static int PROFILE_COL = 0;
    final static int DESC_COL = 1;
    final static int LPP_COST_COL = 2;
    final static int QTY_COL = 3;
    final static int PACKAGING_COL = 4;
    final static int PRICE_COL = 5;
    final static int REBATE_COL = 6;
    final static int SURCHARGE_COL = 7;
    final static int TRANS_COL = 8;
    final static int TAX_COL = 9;
    final static int TOTAL_COL = 10;

    public static final String[] costColHeads = {"Desc","Cost","Qty","Extended"};
    public static final int[] costColWidths = {175,45,35,65};
    public static final int[] costColTypes = {BothHelpObjs.TABLE_COL_TYPE_STRING,
                                            BothHelpObjs.TABLE_COL_TYPE_NUMBER,
                                            BothHelpObjs.TABLE_COL_TYPE_NUMBER,
                                            BothHelpObjs.TABLE_COL_TYPE_NUMBER};

    public static final String[] invoiceColHeads = {"Invoice","Date","Amount"};
    public static final int[] invoiceColWidths = {60,70,47};
    public static final int[] invoiceColTypes = {BothHelpObjs.TABLE_COL_TYPE_STRING,
                                            BothHelpObjs.TABLE_COL_TYPE_NUMBER,
                                            BothHelpObjs.TABLE_COL_TYPE_NUMBER};

    final static int[] colT = {BothHelpObjs.TABLE_COL_TYPE_STRING,
                        BothHelpObjs.TABLE_COL_TYPE_STRING,
                        BothHelpObjs.TABLE_COL_TYPE_NUMERIC_STRING,
                        BothHelpObjs.TABLE_COL_TYPE_NUMBER,
                        BothHelpObjs.TABLE_COL_TYPE_NUMERIC_STRING,
                        BothHelpObjs.TABLE_COL_TYPE_NUMBER,
                        BothHelpObjs.TABLE_COL_TYPE_NUMBER,
                        BothHelpObjs.TABLE_COL_TYPE_NUMBER,
                        BothHelpObjs.TABLE_COL_TYPE_NUMBER,
                        BothHelpObjs.TABLE_COL_TYPE_NUMBER,
                        BothHelpObjs.TABLE_COL_TYPE_NUMBER};

  protected TcmISDBModel db;


  public static Hashtable getExtraCostInfo(TcmISDBModel db, String shipmentId, String currentProcess ) throws Exception {
    Hashtable dataH = new Hashtable();
    Vector dataV = new Vector();
    String query = "select cost_type_desc,rate,quantity,cost_type from waste_ship_opt_charge_view";
    query += " where shipment_id = "+shipmentId;
    query += " and invoice_process = '"+currentProcess+"'";
    query += " and quantity is not null";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while(rs.next()){
        Object[] oa4 = new Object[costColHeads.length];
        double cost = rs.getDouble("rate");
        double quantity = rs.getDouble("quantity");
        double extended = cost * quantity;
        oa4[0] = BothHelpObjs.makeBlankFromNull(rs.getString("cost_type_desc"));
        oa4[1] = new Double(cost);
        oa4[2] = new Double(quantity);
        oa4[3] = new Double(extended);
        dataV.addElement(oa4);
      }
    }catch(Exception ex2){
      ex2.printStackTrace();
      throw ex2;
    }finally{
      dbrs.close();
    }
    dataH.put("TABLE_HEADERS",costColHeads);
    dataH.put("TABLE_WIDTHS",costColWidths);
    dataH.put("TABLE_TYPES",costColTypes);
    dataV.trimToSize();
    dataH.put("TABLE_DATA",dataV);
    return dataH;
  }

  public static String deleteVendorInvoice(TcmISDBModel db, int shipmentId) throws Exception {
    String result = "";
    String query = "delete from waste_vendor_invoice";
    query += " where shipment_id = "+shipmentId;
    try {
      db.doUpdate(query);
      result = "OK";
    }catch (Exception e) {
      e.printStackTrace();
      result = "Error";
    }
    return result;
  }
  public static String insertVendorInvoice(TcmISDBModel db, int shipmentId,String invoice) throws Exception {
    String result = "";
    String query = "insert into waste_vendor_invoice (shipment_id,vendor_invoice_number)";
    query += " values("+shipmentId+",'"+invoice+"')";
    try {
      db.doUpdate(query);
      result = "OK";
    }catch (Exception e) {
      e.printStackTrace();
      result = "Error";
    }
    return result;
  }
  public static void insert(TcmISDBModel db, String col,String val,int type, int shipmentId, String invoice)  throws Exception {
     Integer I;
     DBResultSet dbrs = null;
     ResultSet rs = null;

     String query = new String("update waste_vendor_invoice set " + col + "=");
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
     query += " where shipment_id = " + shipmentId;
     query += " and vendor_invoice_number = '"+invoice+"'";
     //System.out.println("&&&&&&&&&&&&&&&& " + query);
     try {
       db.doUpdate(query);
     } catch (Exception e) {
       e.printStackTrace();
        throw e;
     }finally{}
   }

  public static Vector getInvoiceInfoV(TcmISDBModel db, String shipmentId) throws Exception {
    Vector v = new Vector();
    String query = "select vendor_invoice_number, to_char(vendor_invoice_date, 'MM/dd/yyyy') vendor_invoice_date,";
    query += " vendor_invoice_amount, to_char(received, 'MM/dd/yyyy') received from waste_vendor_invoice";
    query += " where shipment_id = "+shipmentId;
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while(rs.next()){
        Hashtable h = new Hashtable();
        h.put("INVOICE",BothHelpObjs.makeBlankFromNull(rs.getString("vendor_invoice_number")));
        h.put("DATE",BothHelpObjs.makeBlankFromNull(rs.getString("vendor_invoice_date")));
        h.put("AMOUNT",BothHelpObjs.makeBlankFromNull(rs.getString("vendor_invoice_amount")));
        h.put("RECEIVED",BothHelpObjs.makeBlankFromNull(rs.getString("received")));
        v.addElement(h);
      }
      //add four more rows to the existing records
      int count = 4;
      while (count > 0) {
        Hashtable h = new Hashtable();
        h.put("INVOICE","");
        h.put("DATE","");
        h.put("AMOUNT","");
        h.put("RECEIVED","");
        v.addElement(h);
        count--;
      }
    }catch(Exception ex2){
      ex2.printStackTrace();
      throw ex2;
    }finally{
      dbrs.close();
    }

    return v;
  }


  //running with one query
  public static Hashtable getDraftInvoiceInfoTab(TcmISDBModel db,int orderNo) throws Exception{
    String[] colH = {"Profile","Description","LPP","Qty","Packaging","Price","Rebate","Surcharge","Trans.","Tax","Total"};
    Hashtable result = new Hashtable();
    Vector shipmentIdV = new Vector();
    int[] colW = {80,100,45,42,90,50,55,70,55,50,70};
    String query = "select * from wst_ship_invoice_view where order_no = "+orderNo;
    query += " and invoice_process = 0 order by shipment_id";
    DBResultSet dbrs = null;
    ResultSet rs = null;

    int tempQty = 0;
    double tempPrice = 0.00;
    double tempRebate = 0.00;
    double tempSurcharge = 0.00;
    double tempTrans = 0.00;
    double tempTax = 0.00;
    double tempTotal = 0.00;

    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      String lastShipmentId = "";
      String lastWasteItemId = "";
      while(rs.next()){
       String shipmentId = BothHelpObjs.makeBlankFromNull(rs.getString("shipment_id"));
       if (!shipmentId.equals(lastShipmentId)){
        shipmentIdV.addElement(shipmentId);
       }
       //System.out.println("----------- shipment ID: "+shipmentIdV);
       String wasteItemId = BothHelpObjs.makeBlankFromNull(rs.getString("waste_item_id"));
       if (!wasteItemId.equals(lastWasteItemId)) {
        if(result.containsKey(shipmentId)) {
          Hashtable innerH = (Hashtable)result.get(shipmentId);
          Vector dataV = (Vector)innerH.get("COST_TABLE_DATA");
          Object[] oa = new Object[colH.length];
          oa[PROFILE_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("vendor_profile_id"));
          oa[DESC_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("description"));
          oa[LPP_COST_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("customer_unit_price"));
          tempQty = rs.getInt("quantity");
          oa[QTY_COL] = new Integer(tempQty);
          oa[PACKAGING_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("packaging"));
          tempPrice = rs.getDouble("customer_price");
          oa[PRICE_COL] = new Double(tempPrice);
          tempRebate = rs.getDouble("rebate");
          oa[REBATE_COL] = new Double(tempRebate);
          tempSurcharge = rs.getDouble("surcharge");
          oa[SURCHARGE_COL] = new Double(tempSurcharge);
          tempTrans = rs.getDouble("transportation");
          oa[TRANS_COL] = new Double(tempTrans);
          tempTax = rs.getDouble("tax");
          oa[TAX_COL] = new Double(tempTax);
          double customerTotal = rs.getDouble("customer_total");
          oa[TOTAL_COL] = new Double(customerTotal);
          dataV.addElement(oa);
          innerH.put("COST_TABLE_HEADERS",colH);
          innerH.put("COST_TABLE_WIDTHS",colW);
          innerH.put("COST_TABLE_TYPES",colT);
          innerH.put("COST_TABLE_DATA",dataV);
          result.put(shipmentId,innerH);
        } else {
          //System.out.println("----------- shipment ID: this is where i need to be");
          Hashtable innerH = new Hashtable();
          innerH.put("shipmentId",shipmentId);
          innerH.put("VENDOR_SHIPMENT_ID",BothHelpObjs.makeBlankFromNull(rs.getString("vendor_shipment_id")));
          Vector dataV = new Vector();
          Object[] oa = new Object[colH.length];
          oa[PROFILE_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("vendor_profile_id"));
          oa[DESC_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("description"));
          oa[LPP_COST_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("customer_unit_price"));;
          tempQty = rs.getInt("quantity");
          oa[QTY_COL] = new Integer(tempQty);
          oa[PACKAGING_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("packaging"));
          tempPrice = rs.getDouble("customer_price");
          oa[PRICE_COL] = new Double(tempPrice);
          tempRebate = rs.getDouble("rebate");
          oa[REBATE_COL] = new Double(tempRebate);
          tempSurcharge = rs.getDouble("surcharge");
          oa[SURCHARGE_COL] = new Double(tempSurcharge);
          tempTrans = rs.getDouble("transportation");
          oa[TRANS_COL] = new Double(tempTrans);
          tempTax = rs.getDouble("tax");
          oa[TAX_COL] = new Double(tempTax);
          double customerTotal = rs.getDouble("customer_total");
          oa[TOTAL_COL] = new Double(customerTotal);
          dataV.addElement(oa);
          innerH.put("COST_TABLE_HEADERS",colH);
          innerH.put("COST_TABLE_WIDTHS",colW);
          innerH.put("COST_TABLE_TYPES",colT);
          innerH.put("COST_TABLE_DATA",dataV);

          String currentProcess = rs.getString("invoice_process");
          innerH.put("EXTRA_COST_TABLE_DATA",getExtraCostInfo(db,shipmentId,currentProcess));
          result.put(shipmentId,innerH);
        }
       }
       lastShipmentId = shipmentId;
       lastWasteItemId = wasteItemId;
      }
      //System.out.println("----------- done ");
    }catch(Exception ex){ex.printStackTrace(); throw ex;
    }finally{dbrs.close();}
    result.put("SHIPMENT_ID",shipmentIdV);
    return result;
  }

  //running with one query
  public static Hashtable getVendorInvoiceInfoTab(TcmISDBModel db,int orderNo, int userId) throws Exception{
    String[] colH = {"Profile","Description","Cost","Qty","Packaging","Price","Rebate","Surcharge","Trans.","Tax","Total"};
    Hashtable result = new Hashtable();
    Vector shipmentIdV = new Vector();
    Vector shipmentStatusV = new Vector();
    int[] colW = {80,100,65,50,90,65,0,70,65,50,70};
    String query = "select invoice_process,to_char(time_stamp, 'MM/dd/yyyy HH24:MI') time_stamp,shipment_id,waste_item_id,vendor_shipment_id,";
    query += "vendor_profile_id,description,packaging,quantity,vendor_price,vendor_unit_price,surcharge,transportation,tax,vendor_total";
    query += " from wst_ship_invoice_view where order_no = "+orderNo;
    query += " order by shipment_id,invoice_process,time_stamp";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    Hashtable processH = new Hashtable();

    int tempQty = 0;
    double tempPrice = 0.00;
    double tempRebate = 0.00;
    double tempSurcharge = 0.00;
    double tempTrans = 0.00;
    double tempTax = 0.00;
    double tempTotal = 0.00;

    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      String lastShipmentId = "";
      String lastWasteItemId = "";
      String lastProcess = "";
      while(rs.next()){
       String shipmentId = BothHelpObjs.makeBlankFromNull(rs.getString("shipment_id"));
       if (!shipmentId.equals(lastShipmentId)){
        shipmentIdV.addElement(shipmentId);
        shipmentStatusV.addElement(getStatus(db,shipmentId));
        //for testing purposes only --- shipmentStatusV.addElement("ammended");
       }

       String currentProcess = rs.getString("invoice_process");
       String timeStamp = rs.getString("time_stamp");
       String key = shipmentId + currentProcess;
       if (processH.containsKey(shipmentId)) {
        Hashtable pht = (Hashtable)processH.get(shipmentId);
        Vector processV = (Vector)pht.get("PROCESS");
        Vector timeV = (Vector)pht.get("TIME");
        if (!processV.contains(currentProcess)) {
          processV.addElement(currentProcess);
          if (currentProcess.equalsIgnoreCase("0")) {
            timeV.addElement("In Progress...");
          }else {
            timeV.addElement(timeStamp);
          }
        }
       }else {
        Hashtable ph = new Hashtable();
        Vector processV = new Vector();
        Vector timeV = new Vector();
        processV.addElement(currentProcess);
        if (currentProcess.equalsIgnoreCase("0")) {
          timeV.addElement("In Progress...");
        }else {
          timeV.addElement(timeStamp);
        }
        ph.put("PROCESS",processV);
        ph.put("TIME",timeV);
        processH.put(shipmentId,ph);
       }

       String wasteItemId = BothHelpObjs.makeBlankFromNull(rs.getString("waste_item_id"));
       if (!wasteItemId.equals(lastWasteItemId) || !currentProcess.equals(lastProcess)) {
        if(result.containsKey(key)) {
          Hashtable innerH = (Hashtable)result.get(key);
          Vector dataV = (Vector) innerH.get("TABLE_DATA");
          Object[] oa = new Object[colH.length];
          oa[PROFILE_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("vendor_profile_id"));
          oa[DESC_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("description"));
          oa[LPP_COST_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("vendor_unit_price"));;

          tempQty = rs.getInt("quantity");
          oa[QTY_COL] = new Integer(tempQty);
          oa[PACKAGING_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("packaging"));
          tempPrice = rs.getDouble("vendor_price");
          oa[PRICE_COL] = new Double(tempPrice);
          oa[REBATE_COL] = new Double(tempRebate);
          tempSurcharge = rs.getDouble("surcharge");
          oa[SURCHARGE_COL] = new Double(tempSurcharge);
          tempTrans = rs.getDouble("transportation");
          oa[TRANS_COL] = new Double(tempTrans);
          tempTax = rs.getDouble("tax");
          oa[TAX_COL] = new Double(tempTax);
          double customerTotal = rs.getDouble("vendor_total");
          oa[TOTAL_COL] = new Double(customerTotal);
          dataV.addElement(oa);
          innerH.put("TABLE_HEADERS",colH);
          innerH.put("TABLE_WIDTHS",colW);
          innerH.put("TABLE_TYPES",colT);
          innerH.put("TABLE_DATA",dataV);
          result.put(key,innerH);
        } else {
          Hashtable innerH = new Hashtable();
          innerH.put("shipmentId",shipmentId);
          innerH.put("VENDOR_SHIPMENT_ID",BothHelpObjs.makeBlankFromNull(rs.getString("vendor_shipment_id")));
          Vector dataV = new Vector();
          Object[] oa = new Object[colH.length];
          oa[PROFILE_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("vendor_profile_id"));
          oa[DESC_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("description"));
          oa[LPP_COST_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("vendor_unit_price"));

          tempQty = rs.getInt("quantity");
          oa[QTY_COL] = new Integer(tempQty);
          oa[PACKAGING_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("packaging"));
          tempPrice = rs.getDouble("vendor_price");
          oa[PRICE_COL] = new Double(tempPrice);
          oa[REBATE_COL] = new Double(tempRebate);
          tempSurcharge = rs.getDouble("surcharge");
          oa[SURCHARGE_COL] = new Double(tempSurcharge);
          tempTrans = rs.getDouble("transportation");
          oa[TRANS_COL] = new Double(tempTrans);
          tempTax = rs.getDouble("tax");
          oa[TAX_COL] = new Double(tempTax);
          double customerTotal = rs.getDouble("vendor_total");
          oa[TOTAL_COL] = new Double(customerTotal);
          dataV.addElement(oa);
          innerH.put("TABLE_HEADERS",colH);
          innerH.put("TABLE_WIDTHS",colW);
          innerH.put("TABLE_TYPES",colT);
          innerH.put("TABLE_DATA",dataV);
          innerH.put("EXTRA_COST_TABLE_DATA",getExtraCostInfo(db,shipmentId,currentProcess));
          innerH.put("VENDOR_INVOICE",getInvoiceInfoV(db,shipmentId));
          result.put(key,innerH);
        }
       }
       lastShipmentId = shipmentId;
       lastWasteItemId = wasteItemId;
       lastProcess = currentProcess;
      }

    }catch(Exception ex){ex.printStackTrace(); throw ex;
    }finally{dbrs.close();}
    result.put("SHIPMENT_ID",shipmentIdV);
    result.put("SHIPMENT_STATUS",shipmentStatusV);
    result.put("INVOICE_PROCESS",processH);

    //6-25-01 sending error message to client
    result.put("MIXED_STATUS","We uncovered an error in your data.\nA message had been sent to our database expert.\nHe/she will contact you as soon as he/she get it resolved.\nIf he/she does not contact you within 3 hours.\nPlease call tcmIS Customer Service Representative (CSR).");
    result.put("LOCKED_STATUS","Invoice in process, please try again later.");
    for (int k = 0; k < shipmentStatusV.size(); k++) {
      if (shipmentStatusV.elementAt(k).toString().equalsIgnoreCase("mixed")) {
        sendErrorEmail(db,shipmentIdV.elementAt(k).toString(),userId,orderNo);
      }
    }

    return result;
  }

  public static void sendErrorEmail(TcmISDBModel db, String shipId, int userId, int orderNo)throws Exception {
    String sub = "tcmIS personnel id : "+userId+" is trying to access 'Mixed' status invoice.";
    String msg = "tcmIS personnel id : "+userId+" is trying to access order no: "+orderNo+" shipment id: "+shipId+" which has 'Mixed' status.\nPlease take appropriate action and contact user within 3 hours.";

    UserGroup userGroup = new UserGroup(db);
    userGroup.setGroupFacId("All");
    userGroup.setGroupId("WasteInvoiceError");
    HelpObjs.sendMail(userGroup,sub,msg);
  }

  public static String getStatus(TcmISDBModel db, String shipID)throws Exception {
    String query = "select fx_waste_invoice_status("+shipID+") status from dual";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    String status = "";
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while(rs.next()){
        status = BothHelpObjs.makeBlankFromNull(rs.getString("status"));
      }
    }catch(Exception e) {
      e.printStackTrace();
      throw e;
    }finally {
      dbrs.close();
    }
    return status;
  }
}

/*
  public static tablemodel getInvoiceInfo(TcmISDBModel db, String shipmentId) throws Exception {
    tablemodel invoiceModel = new tablemodel(invoiceColHeads);
    String query = "select * from waste_vendor_invoice";
    query += " where shipment_id = "+shipmentId;
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while(rs.next()){
        Object[] oa = new Object[invoiceColHeads.length];
        oa[0] = BothHelpObjs.makeBlankFromNull(rs.getString("vendor_invoice_number"));
        oa[1] = BothHelpObjs.makeBlankFromNull(rs.getString("vendor_invoice_date"));
        oa[2] = BothHelpObjs.makeBlankFromNull(rs.getString("vendor_invoice_amount"));
        invoiceModel.addRow(oa);
      }
      //add four more rows to the existing records
      int count = 4;
      while (count > 0) {
        Object[] oa = new Object[invoiceColHeads.length];
        //oa4[0] = BothHelpObjs.makeBlankFromNull(rs.getString("cost_type_desc"));
        oa[0] = "";
        oa[1] = "";
        oa[2] = "";
        invoiceModel.addRow(oa);
        count--;
      }
    }catch(Exception ex2){
      ex2.printStackTrace();
      throw ex2;
    }finally{
      dbrs.close();
    }
    invoiceModel.setColumnPrefWidth(invoiceColWidths);
    invoiceModel.setColumnType(invoiceColTypes);
    return invoiceModel;
  }

*/

