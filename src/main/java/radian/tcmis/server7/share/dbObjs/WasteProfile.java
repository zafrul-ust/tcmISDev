package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.both1.helpers.*;
import radian.tcmis.server7.share.db.*;

/*
SQLWKS> desc waste_item
Column Name                    Null?    Type
------------------------------ -------- ----
WASTE_ITEM_ID                  NOT NULL NUMBER(38)
CATALOG_ID                              VARCHAR2(20)
CONTAINER_TYPE                          VARCHAR2(20)
EPA_CONTAINER_TYPE                      CHAR(2)
SIZE_UNIT                               VARCHAR2(20)
EPA_SIZE_UNIT                           VARCHAR2(2)
VENDOR_ID                      NOT NULL VARCHAR2(20)
VENDOR_PROFILE_ID              NOT NULL VARCHAR2(60)
STATUS                         NOT NULL CHAR(1)
START_DATE                     NOT NULL DATE
STOP_DATE                               DATE
POUNDS_PER                              NUMBER
CONTAINER_SIZE                          NUMBER
SPECIAL_HANDLING                        VARCHAR2(200)
LAST_PRICE_PAID                         NUMBER(10,2)
BULK                                    CHAR(1)
*/

import java.util.*;
import java.sql.*;
import java.lang.String;
import java.math.BigDecimal;

public class WasteProfile {
  // Search table constants
  public static final int SEARCH_STATE_WASTE_CODE_COL = 0;
  public static final int SEARCH_DESC_COL = 1;
  public static final int SEARCH_PACKAGING_COL = 2;
  public static final int SEARCH_VENDOR_PROFILE_ID_COL = 3;
  public static final int SEARCH_VENDOR_ID_COL = 4;
  public static final int SEARCH_WASTE_CATEGORY_COL = 5;
  public static final int SEARCH_WASTE_TYPE_COL = 6;
  public static final int SEARCH_WASTE_MGMT_OPT_COL = 7;
  public static final int SEARCH_ITEM_ID_COL = 8;
  public static final int SEARCH_FACILITY_COL = 9;
  public static final int SEARCH_LPP_COL = 10;
  public static final int SEARCH_CURRENCY_ID_COL = 11;

  public static final String[] colHeads = {"State Waste Code","Description","Packaging","Vend Profile","Vendor","Waste Category","Waste Type","Waste Mgmt Opt","Rad Profile","Facility","LPP","Currency ID"};
  public static final int[] colWidths = {105,105,70,70,110,95,70,90,65,70,80,0};
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
                                        BothHelpObjs.TABLE_COL_TYPE_STRING};


   protected TcmISDBModel db;

   protected int wasteItemId;
   protected String catalogId;
   protected String containerType;
   protected String epaContainerType;
   protected String sizeUnit;
   protected String epaSizeUnit;
   protected String vendorId;
   protected String vendorProfileId;
   protected String status;
   protected String startDate;
   protected String stopDate;
   protected int poundsPerUnit;
   protected int containerSize;
   protected String specialHandling;
   protected int lastPricePaid;
   protected String bulk;
   protected String currencyID;

   public WasteProfile(TcmISDBModel db){
      this.db = db;
   }
   public WasteProfile(){
   }
   public void setDb(TcmISDBModel db){
      this.db = db;
   }

   // get methods
   public int getWasteItemId(){return wasteItemId;}
   public String getCatalogId(){return catalogId;}
   public String getContainerType(){return containerType;}
   public String getEpaContainerType(){return epaContainerType;}
   public String getSizeUnit(){return sizeUnit;}
   public String getEpaSizeUnit(){return epaSizeUnit;}
   public String getVendorId(){return vendorId;}
   public String getVendorProfileId(){return vendorProfileId;}
   public String getStatus(){return status;}
   public String getStartDate(){return startDate;}
   public String getStopDate(){return stopDate;}
   public int getPoundsPerUnit(){return poundsPerUnit;}
   public int getContainerSize(){return containerSize;}
   public String getSpecialHandling(){return specialHandling;}
   public int getLastPricePaid(){return lastPricePaid;}
   public String getBulk(){return bulk;}
   public String getCurrencyID(){return currencyID;}

   // set methods
   public void setWasteItemId(int s){wasteItemId = s;}
   public void setCatalogId(String s){catalogId = s;}
   public void setContainerType(String s){containerType = s;}
   public void setEpaContainerType(String s){epaContainerType = s;}
   public void setSizeUnit(String s){sizeUnit = s;}
   public void setEpaSizeUnit(String s){epaSizeUnit = s;}
   public void setVendorId(String s){vendorId = s;}
   public void setVendorProfileId(String s){vendorProfileId = s;}
   public void setStatus(String s){status = s;}
   public void setStartDate(String s){startDate = s;}
   public void setStopDate(String s){stopDate = s;}
   public void setPoundsPerUnit(int s){poundsPerUnit = s;}
   public void setContainerSize(int s){containerSize = s;}
   public void setSpecialHandling(String s){specialHandling = s;}
   public void setLastPricePaid(int s){lastPricePaid = s;}
   public void setBulk(String s){bulk = s;}
   public void setCurrencyID(String s){currencyID = s;}

   public void load()throws Exception{
     String query = "select * from waste_item where waste_item_id = "+getWasteItemId()+"";
     DBResultSet dbrs = null;
     ResultSet rs = null;
     int myCount = 0;
     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()){
         setCatalogId(BothHelpObjs.makeBlankFromNull(rs.getString("catalog_id")));
         setContainerType(BothHelpObjs.makeBlankFromNull(rs.getString("container_type")));
         setEpaContainerType(BothHelpObjs.makeBlankFromNull(rs.getString("epa_container_type")));
         setSizeUnit(BothHelpObjs.makeBlankFromNull(rs.getString("size_unit")));
         setEpaSizeUnit(BothHelpObjs.makeBlankFromNull(rs.getString("epa_size_unit")));
         setSizeUnit(BothHelpObjs.makeBlankFromNull(rs.getString("size_unit")));
         setVendorId(BothHelpObjs.makeBlankFromNull(rs.getString("vendor_id")));
         setVendorProfileId(BothHelpObjs.makeBlankFromNull(rs.getString("vendor_profile_id")));
         setStatus(BothHelpObjs.makeBlankFromNull(rs.getString("status")));
         setStartDate(BothHelpObjs.makeBlankFromNull(rs.getString("start_date")));
         setStopDate(BothHelpObjs.makeBlankFromNull(rs.getString("stop_date")));
         setPoundsPerUnit(BothHelpObjs.makeZeroFromNull(rs.getString("pounds_per")));
         setContainerSize(BothHelpObjs.makeZeroFromNull(rs.getString("container_size")));
         setSpecialHandling(BothHelpObjs.makeBlankFromNull(rs.getString("special_handling")));
         setLastPricePaid(BothHelpObjs.makeZeroFromNull(rs.getString("last_price_paid")));
         setBulk(BothHelpObjs.makeBlankFromNull(rs.getString("bulk")));
       }
     }catch (Exception e) { e.printStackTrace(); throw e;
     }finally{dbrs.close();}
   }

   public static String buildSearchString(String facID, String workAreaID, String searchString) {
     String result = "";
     if (BothHelpObjs.isBlankString(searchString)) {
       result = "select wcv.* from waste_catalog_view wcv,accum_to_vendor_catalog_view atcv where atcv.generation_facility_id = '"+facID+"'"+
                " and wcv.facility_id = atcv.facility_id and wcv.catalog_id = atcv.catalog_id and atcv.generation_location_id = '"+workAreaID+"'";
     }else {
       result = "select * from waste_synonym_catalog_view wcv,accum_to_vendor_catalog_view atcv where  atcv.generation_facility_id = '"+facID+"'"+
                " and search_string like '%"+ String.valueOf(searchString).toLowerCase() +"%'"+
                " and wcv.facility_id = atcv.facility_id and wcv.catalog_id = atcv.catalog_id and atcv.generation_location_id = '"+workAreaID+"'";
     }
     result += " order by waste_item_id";
     return result;
   } //end of method

   public static String buildPreviousSearchString(String facID, String workAreaID, String searchString) {
     String result = "";
     if (BothHelpObjs.isBlankString(searchString)) {
       result = "select * from waste_catalog_xfer_view where facility_id = '"+facID+"'";
       if ((!BothHelpObjs.isBlankString(workAreaID)) && (!workAreaID.equalsIgnoreCase("all"))) {
         result += " and generation_point = '"+ workAreaID +"' ";
       }
     }else {
       result = "select * from waste_synonym_cat_xfer_view where facility_id = '"+facID+"'";
       if ((!BothHelpObjs.isBlankString(workAreaID)) && (!workAreaID.equalsIgnoreCase("all"))) {
         result += " and generation_point = '"+ workAreaID +"' ";
       }
       result += " and search_string like '%"+ String.valueOf(searchString).toLowerCase() +"%' ";
     }
     result += " order by waste_item_id";
     return result;
   } //end of method


   public static Hashtable doSearch(TcmISDBModel db,Boolean previousTransfer, String fac,String workArea,String search_string, boolean printScreenXls) throws Exception{
     Hashtable dataH = new Hashtable(4);
     Vector dataV = new Vector();
     Vector printScrDataV = new Vector();
     String query = "";
     if (previousTransfer.booleanValue()) {
       query = buildPreviousSearchString(fac, workArea, search_string);
     }else {
       query = buildSearchString(fac, workArea, search_string);
     }

     DBResultSet dbrs = null;
     ResultSet rs = null;
     DBResultSet dbrs2 = null;
     ResultSet rs2 = null;
     int myCount = 0;
     int lastWPI = -1;

     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while(rs.next()){
        if(lastWPI != rs.getInt("waste_item_id")){
          if (printScreenXls) {
            Hashtable printScrDataH = new Hashtable(19);
            printScrDataH.put("STATE_WASTE_CODE",BothHelpObjs.makeBlankFromNull(rs.getString("state_waste_codes")));
            printScrDataH.put("WASTE_ITEM_ID",rs.getString("waste_item_id"));
            printScrDataH.put("VENDOR_PROFILE",BothHelpObjs.makeBlankFromNull(rs.getString("vendor_profile_id")));
            printScrDataH.put("FACILITY_ID",BothHelpObjs.makeBlankFromNull(rs.getString("facility_id")));
            printScrDataH.put("VENDOR_ID",BothHelpObjs.makeBlankFromNull(rs.getString("company_name")));
            printScrDataH.put("WASTE_MGMT_OPT",BothHelpObjs.makeBlankFromNull(rs.getString("management_option_desc")));
            printScrDataH.put("WASTE_CATEGORY",BothHelpObjs.makeBlankFromNull(rs.getString("waste_category_id")));
            printScrDataH.put("WASTE_TYPE",BothHelpObjs.makeBlankFromNull(rs.getString("waste_type_id")));
            printScrDataH.put("PACKAGING",BothHelpObjs.makeBlankFromNull(rs.getString("packaging")));
            printScrDataH.put("WASTE_DESC",BothHelpObjs.makeBlankFromNull(rs.getString("description")));
            printScrDataH.put("LPP",BothHelpObjs.makeBlankFromNull(rs.getString("last_price_paid")));
            printScrDataH.put("CURRENCY",BothHelpObjs.makeBlankFromNull(rs.getString("currency_id")));
            printScrDataV.addElement(printScrDataH);
          }else {
            Object[] oa = new Object[colHeads.length];
            oa[SEARCH_STATE_WASTE_CODE_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("state_waste_codes"));
            oa[SEARCH_ITEM_ID_COL] = rs.getString("waste_item_id");
            oa[SEARCH_VENDOR_PROFILE_ID_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("vendor_profile_id"));
            oa[SEARCH_FACILITY_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("facility_id"));
            oa[SEARCH_VENDOR_ID_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("company_name"));
            oa[SEARCH_WASTE_MGMT_OPT_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("management_option_desc"));
            oa[SEARCH_WASTE_CATEGORY_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("waste_category_id"));
            oa[SEARCH_WASTE_TYPE_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("waste_type_id"));
            oa[SEARCH_PACKAGING_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("packaging"));
            oa[SEARCH_DESC_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("description"));
            String lastPricePaid = BothHelpObjs.makeBlankFromNull(rs.getString("last_price_paid"));
            String currencyID = BothHelpObjs.makeBlankFromNull(rs.getString("currency_id"));
            if (lastPricePaid.length() > 0) {
              BigDecimal amt = new BigDecimal(lastPricePaid);
              amt = amt.setScale(4,BigDecimal.ROUND_HALF_UP);
              lastPricePaid = amt.toString()+" "+currencyID;
            }
            oa[SEARCH_LPP_COL] = lastPricePaid;
            oa[SEARCH_CURRENCY_ID_COL] = currencyID;
            dataV.addElement(oa);
          } //end of not xls print screen
        } //end of waste_item_id
        lastWPI = rs.getInt("waste_item_id");
       }
    }catch(Exception ex){
      ex.printStackTrace();
      throw ex;
    }finally{
      dbrs.close();
    }
    if (printScreenXls) {
      dataH.put("PRINT_SCR_DATA",printScrDataV);
    }else {
      dataH.put("TABLE_HEADERS",colHeads);
      dataH.put("TABLE_WIDTHS",colWidths);
      dataH.put("TABLE_TYPES",colTypes);
      dataV.trimToSize();
      dataH.put("TABLE_DATA",dataV);
    }
    return dataH;
   } //end of method
} //end of class


