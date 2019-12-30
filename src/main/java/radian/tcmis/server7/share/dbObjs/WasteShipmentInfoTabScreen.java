
package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.both1.helpers.*;
import radian.tcmis.server7.share.db.*;
import java.util.*;
import java.sql.*;


public abstract class WasteShipmentInfoTabScreen {
  public static final int SHIPMENT_ID_COL = 0;
  public static final int BULK_ID_COL = 1;
  public static final int DESCRIPTION_COL = 2;
  public static final int PACKAGING_COL = 3;
  public static final int PLANNED_AMOUNT_COL = 4;
  public static final int PLANNED_PICKUP_COL = 5;
  public static final int VENDOR_ORDER_COL = 6;
  public static final int ACTUAL_AMOUNT_COL = 7;
  public static final int ACTUAL_PICKUP_COL = 8;
  public static final int MANIFEST_NO_COL = 9;
  public static final int STATE_COL = 10;
  public static final int COUNTRY_COL = 11;
  public static final int COPY_RETURNED_COL = 12;
  public static final int DISPOSITION_COL = 13;
  public static final int VENDOR_PROFILE_ID_COL = 14;


  public static final String[] costColHeads = {"Desc","Cost","Qty","Cost Type"};
  public static final int[] costColWidths = {175,35,35,0};
  public static final int[] costColTypes = {BothHelpObjs.TABLE_COL_TYPE_STRING,
                                            BothHelpObjs.TABLE_COL_TYPE_NUMERIC_STRING,
                                            BothHelpObjs.TABLE_COL_TYPE_NUMERIC_STRING,
                                            BothHelpObjs.TABLE_COL_TYPE_STRING};

  public static final String[] containerColHeads = {"Man. Line","Disposal Date"};
  public static final int[] containerColWidths = {60,80};
  public static final int[] containerColTypes = {BothHelpObjs.TABLE_COL_TYPE_STRING,
                                        BothHelpObjs.TABLE_COL_TYPE_STRING};

  public static final String[] colHeads = {"Shipment ID","Bulk ID","Description",
                                           "Packaging","Planned Amt.","Planned Date",
                                           "Vendor Order No","Actual Amt.","Actual Date","Manifest",
                                           "State","Country","Copy Returned","Disposition","Profile"};
  public static final int[] colWidths = {90,90,120,80,90,90,90,70,90,80,80,80,90,90,90,90};
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
                                        BothHelpObjs.TABLE_COL_TYPE_STRING,
                                        BothHelpObjs.TABLE_COL_TYPE_STRING,
                                        BothHelpObjs.TABLE_COL_TYPE_STRING,
                                        BothHelpObjs.TABLE_COL_TYPE_STRING,
                                        BothHelpObjs.TABLE_COL_TYPE_STRING};

  protected TcmISDBModel db;

  protected static int shipmentId;
  protected int containerId;
  protected String radianProfileId;
  protected String vendorProfileId;
  protected String description;
  protected int qty;
  protected String packaging;


  //set methods
  public static void setShipmentId(int s){shipmentId = s;}
  public void setContainerId(int s){containerId = s;}
  public void setRadianProfileId(String s){radianProfileId = s;}
  public void setVendorProfileId(String s){vendorProfileId = s;}
  public void setDescription(String s){ description = s;}
  public void setQty(int s){qty = s;}
  public void setPackaging(String s){packaging = s;}

  //get methods
  public static int getShipmentId(){return shipmentId;}
  public int getContainerId(){return containerId;}
  public String getRadianProfileId(){return radianProfileId;}
  public String getVendorProfileId(){return vendorProfileId;}
  public String getDescription(){return description;}
  public int getQty(int s){return qty;}
  public String getPackaging(){return packaging;}


  public static String showTransCost(TcmISDBModel db,int orderNo) throws Exception {
    String showTransCost = "";
    String query = "select wo.order_no,wvf.determine_trans_at_ship from waste_order wo, waste_vendor_facility wvf";
    query += " where wo.vendor_id = wvf.vendor_id and wo.facility_id = wvf.facility_id and wo.storage_location_id = wvf.waste_location_id";
    query += " and wo.order_no = "+orderNo;
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while(rs.next()){
        showTransCost = BothHelpObjs.makeBlankFromNull(rs.getString("determine_trans_at_ship"));
      }
    }catch(Exception ex1){
      ex1.printStackTrace();
      throw ex1;
    }finally{
      dbrs.close();
    }
    return showTransCost;
  }

  public static Hashtable getScreenData(TcmISDBModel db,int orderNo) throws Exception{
    Hashtable rhash = new Hashtable();
    Hashtable header = new Hashtable();
    Vector containerDataV = new Vector();
    Vector bulkDataV = new Vector();
    Vector costDataV = new Vector();
    Vector containerV = new Vector();
    String bulkContainer = "";
    String containerShipmentId = "";
    Hashtable containerInfoH = new Hashtable();

    String query = "select * from waste_order_view where order_no = " +orderNo;
    query += " order by manifest_line_letter";

    DBResultSet dbrs = null;
    ResultSet rs = null;
    String lastLetter = "";
    String date = null;

    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while(rs.next()){
        String bulkOrContainer = BothHelpObjs.makeBlankFromNull(rs.getString("bulk_or_container"));
        String currentLetter = BothHelpObjs.makeBlankFromNull(rs.getString("manifest_line_letter"));
        if (bulkOrContainer.equalsIgnoreCase("B")) {
          Object[] oa = new Object[colHeads.length];
          oa[BULK_ID_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("container_id"));
          oa[VENDOR_PROFILE_ID_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("vendor_profile_id"));
          oa[DESCRIPTION_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("description"));
          oa[PLANNED_AMOUNT_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("planned_amount"));
          oa[ACTUAL_AMOUNT_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("actual_amount"));
          oa[PACKAGING_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("packaging"));
          oa[VENDOR_ORDER_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("vendor_shipment_id"));
          date = BothHelpObjs.makeBlankFromNull(rs.getString("planned_ship_date"));
          date = BothHelpObjs.formatDate("toCharfromDB",date);
          oa[PLANNED_PICKUP_COL] = date;
          date = BothHelpObjs.makeBlankFromNull(rs.getString("actual_ship_date"));
          date = BothHelpObjs.formatDate("toCharfromDB",date);
          oa[ACTUAL_PICKUP_COL] = date;
          oa[MANIFEST_NO_COL]  = BothHelpObjs.makeBlankFromNull(rs.getString("manifest_id"));
          String tmpManifestState = BothHelpObjs.makeBlankFromNull(rs.getString("manifest_state"));
          if (BothHelpObjs.isBlankString(tmpManifestState)) {
            tmpManifestState = "UN";
          }
          oa[STATE_COL] = tmpManifestState;
          oa[COUNTRY_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("manifest_country"));
          date = BothHelpObjs.makeBlankFromNull(rs.getString("manifest_return_date"));
          date = BothHelpObjs.formatDate("toCharfromDB",date);
          oa[COPY_RETURNED_COL] = date;
          oa[SHIPMENT_ID_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("shipment_id"));
          date = BothHelpObjs.makeBlankFromNull(rs.getString("destruction_date"));
          date = BothHelpObjs.formatDate("toCharfromDB",date);
          oa[DISPOSITION_COL] = date;
          bulkDataV.addElement(oa);
          if (BothHelpObjs.isBlankString(bulkContainer) || bulkContainer.equalsIgnoreCase("bulk")) {
            bulkContainer = new String("bulk");
          } else {
            bulkContainer = new String("both");
          }
        }else {
          if (!lastLetter.equalsIgnoreCase(currentLetter)) {
            Object[] oa2 = new Object[containerColHeads.length];
            oa2[0] = currentLetter;
            date = BothHelpObjs.makeBlankFromNull(rs.getString("destruction_date"));
            date = BothHelpObjs.formatDate("toCharfromDB",date);
            oa2[1] = date;
            containerDataV.addElement(oa2);
          }
          lastLetter = currentLetter;
          containerShipmentId = BothHelpObjs.makeBlankFromNull(rs.getString("shipment_id"));
          containerV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("container_id")));
          containerInfoH.put("VENDOR_SHIPMENT_ID",BothHelpObjs.makeBlankFromNull(rs.getString("vendor_shipment_id")));
          date = BothHelpObjs.makeBlankFromNull(rs.getString("planned_ship_date"));
          date = BothHelpObjs.formatDate("toCharfromDB",date);
          containerInfoH.put("PLANNED_PICKUP_DATE",date);
          date = BothHelpObjs.makeBlankFromNull(rs.getString("actual_ship_date"));
          date = BothHelpObjs.formatDate("toCharfromDB",date);
          containerInfoH.put("ACTUAL_PICKUP_DATE",date);
          containerInfoH.put("MANIFEST_ID",BothHelpObjs.makeBlankFromNull(rs.getString("manifest_id")));
          String tmpManifestState = BothHelpObjs.makeBlankFromNull(rs.getString("manifest_state"));
          if (BothHelpObjs.isBlankString(tmpManifestState)) {
            tmpManifestState = "UN";
          }
          containerInfoH.put("STATE",tmpManifestState);
          containerInfoH.put("COUNTRY",BothHelpObjs.makeBlankFromNull(rs.getString("manifest_country")));
          date = BothHelpObjs.makeBlankFromNull(rs.getString("manifest_return_date"));
          date = BothHelpObjs.formatDate("toCharfromDB",date);
          containerInfoH.put("COPY_RETURNED",date);
          containerInfoH.put("TRANS_COST",BothHelpObjs.makeBlankFromNull(rs.getString("transportation_cost")));

          if (BothHelpObjs.isBlankString(bulkContainer) || bulkContainer.equalsIgnoreCase("container")) {
            bulkContainer = new String("container");
          } else {
            bulkContainer = new String("both");
          }
        }
      }
    }catch(Exception ex){ex.printStackTrace(); throw ex;
    }finally{dbrs.close();}

    rhash.put("BULK_CONTAINER",bulkContainer);
    //setting container id
    if (containerV.size() > 0){
      rhash.put("CONTAINER_ID",containerV);
      rhash.put("CONTAINER_SHIPMENT_ID",containerShipmentId);
      rhash.put("CONTAINER_INFO_H",containerInfoH);

      rhash.put("CONTAINER_MANIFEST_LINE_TABLE_HEADERS",containerColHeads);
      rhash.put("CONTAINER_MANIFEST_LINE_TABLE_WIDTHS",containerColWidths);
      rhash.put("CONTAINER_MANIFEST_LINE_TABLE_TYPES",containerColTypes);
      containerDataV.trimToSize();
      rhash.put("CONTAINER_MANIFEST_LINE_TABLE_DATA",containerDataV);

      //creating cost data
      query = "select cost_type_desc,rate,quantity,cost_type from waste_ship_opt_charge_view";
      query += " where shipment_id = "+containerShipmentId;
      try {
        dbrs = db.doQuery(query);
        rs=dbrs.getResultSet();
        while(rs.next()){
          Object[] oa4 = new Object[costColHeads.length];
          oa4[0] = BothHelpObjs.makeBlankFromNull(rs.getString("cost_type_desc"));
          oa4[1] = BothHelpObjs.makeBlankFromNull(rs.getString("rate"));
          oa4[2] = BothHelpObjs.makeBlankFromNull(rs.getString("quantity"));
          oa4[3] = BothHelpObjs.makeBlankFromNull(rs.getString("cost_type"));
          costDataV.addElement(oa4);
        }
      }catch(Exception ex2){
        ex2.printStackTrace();
        throw ex2;
      }finally{
        dbrs.close();
      }
      rhash.put("COST_TABLE_HEADERS",costColHeads);
      rhash.put("COST_TABLE_WIDTHS",costColWidths);
      rhash.put("COST_TABLE_TYPES",costColTypes);
      costDataV.trimToSize();
      rhash.put("COST_TABLE_DATA",costDataV);
      rhash.put("QTY_COL",new Integer(2));
    } //end of if container

    //get manifest state and country
    //for now hard code country to USA
    Vector countryV = new Vector();
    countryV.addElement("USA");
    rhash.put("COUNTRY_VECTOR",countryV);
    //getting all US states
    Vector stateV = new Vector();
    Vector stateDescV = new Vector();
    query = "select state_abbrev from vv_state where country_abbrev = 'USA' order by state_abbrev";
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while(rs.next()){
        stateV.addElement(rs.getString("state_abbrev"));
        stateDescV.addElement(rs.getString("state_abbrev"));
      }
    }catch(Exception ex){ex.printStackTrace(); throw ex;
    }finally{dbrs.close();}
    //adding Uniform to state
    stateV.addElement("UN");
    stateDescV.addElement("Uniform");
    rhash.put("STATE_VECTOR",stateV);
    rhash.put("STATE_DESC_VECTOR",stateDescV);

    //setting bulk info
    rhash.put("BULK_TABLE_HEADERS",colHeads);
    rhash.put("BULK_TABLE_WIDTHS",colWidths);
    rhash.put("BULK_TABLE_TYPES",colTypes);
    bulkDataV.trimToSize();
    rhash.put("BULK_TABLE_DATA",bulkDataV);
    rhash.put("KEY_COLS",getColKey());
    //System.out.println("\n\n========== cols key: "+getColKey());
    return rhash;
   }

   static protected Hashtable getColKey(){
    Hashtable h = new Hashtable();
    for (int i=0;i<colHeads.length;i++) {
      h.put(colHeads[i],new Integer(i));
    }
    return h;
  }

}



