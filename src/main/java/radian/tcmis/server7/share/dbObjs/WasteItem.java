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

import java.sql.*;
import java.lang.String;

public class WasteItem {
  // Search table constants
  public static final int SEARCH_ITEM_ID_COL = 0;
  public static final int SEARCH_FACILITY_COL = 1;
  public static final int SEARCH_VENDOR_COL = 2;
  public static final int SEARCH_VENDOR_PROFILE_COL = 3;
  public static final int SEARCH_DESC_COL = 4;
  public static final int SEARCH_WASTE_MGMT_OPT_COL = 5;
  //public static final int SEARCH_MGMT_OPT_LOCATION_COL = 6;
  public static final int SEARCH_PACKAGING_COL = 6;
  public static final int SEARCH_WASTE_CATEGORY_COL = 7;
  public static final int SEARCH_WASTE_TYPE_COL = 8;
  public static final int SEARCH_POUNDS_PER_UNIT_COL = 9;
  //public static final int SEARCH_PREFERRED_LOCATION_COL = 12;
  /*
  public static final int SEARCH_PROFILE_UNIT_COL = 4;
  public static final int SEARCH_WASTE_HAZARD_CLASS_COL = 5;
  public static final int SEARCH_WASTE_WORK_AREA_COL = 6;
  public static final int SEARCH_WASTE_ID_COL = 7;
  public static final int SEARCH_STATUS_COL = 8;
  public static final int SEARCH_START_DATE_COL = 9;
  public static final int SEARCH_STOP_DATE_COL = 10;
  public static final int SEARCH_LPP_COL = 12;  */


  public static final String[] colHeads = {"Rad Profile","Facility","Vendor","Vend Profile","Description","Waste Mgmt Opt","Packaging","Waste Category","Waste Type","lbs Full"};
  public static final int[] colWidths = {70,70,110,75,130,100,80,100,80,50};
  public static final int[] colTypes = {BothHelpObjs.TABLE_COL_TYPE_NUMERIC_STRING,
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
   protected int containerSize;
   protected String sizeUnit;
   protected String containerType;
   protected String epaContainerType;
   protected String epaSizeUnit;
   protected String vendorId;
   protected String vendorProfileId;
   protected String status;
   protected String startDate;
   protected String stopDate;
   protected String poundsPerUnit;
   protected String specialHandling;
   protected int lastPricePaid;
   protected String bulk;

   public WasteItem(TcmISDBModel db){
      this.db = db;
   }
   public WasteItem(){
   }
   public void setDb(TcmISDBModel db){
      this.db = db;
   }

   // get methods
   public int getWasteItemId(){return wasteItemId;}
   public String getCatalogId(){return catalogId;}
   public int getContainerSize(){return containerSize;}
   public String getSizeUnit(){return sizeUnit;}
   public String getContainerType(){return containerType;}
   public String getEpaContainerType(){return epaContainerType;}
   public String getEpaSizeUnit(){return epaSizeUnit;}
   public String getVendorId(){return vendorId;}
   public String getVendorProfileId(){return vendorProfileId;}
   public String getStatus(){return status;}
   public String getStartDate(){return startDate;}
   public String getStopDate(){return stopDate;}
   public String getPoundsPerUnit(){return poundsPerUnit;}
   public String getSpecialHandling(){return specialHandling;}
   public int getLastPricePaid(){return lastPricePaid;}
   public String getBulk(){return bulk;}

   // set methods
   public void setWasteItemId(int s){wasteItemId = s;}
   public void setCatalogId(String s){catalogId = s;}
   public void setContainerSize(int s){containerSize = s;}
   public void setSizeUnit(String s){sizeUnit = s;}
   public void setContainerType(String s){containerType = s;}
   public void setEpaContainerType(String s){epaContainerType = s;}
   public void setEpaSizeUnit(String s){epaSizeUnit = s;}
   public void setVendorId(String s){vendorId = s;}
   public void setVendorProfileId(String s){vendorProfileId = s;}
   public void setStatus(String s){status = s;}
   public void setStartDate(String s){startDate = s;}
   public void setStopDate(String s){stopDate = s;}
   public void setPoundsPerUnit(String s){poundsPerUnit = s;}
   public void setSpecialHandling(String s){specialHandling = s;}
   public void setLastPricePaid(int s){lastPricePaid = s;}
   public void setBulk(String s){bulk = s;}

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
         setVendorId(BothHelpObjs.makeBlankFromNull(rs.getString("vendor_id")));
         setVendorProfileId(BothHelpObjs.makeBlankFromNull(rs.getString("vendor_profile_id")));
         setSizeUnit(BothHelpObjs.makeBlankFromNull(rs.getString("size_unit")));
         //setHazardClass(BothHelpObjs.makeBlankFromNull(rs.getString("shipping_hazard_class")));
         //setWorkArea(BothHelpObjs.makeBlankFromNull(rs.getString("work_area")));
         //setWasteItemId(BothHelpObjs.makeBlankFromNull(rs.getString("waste_item_id")));
         setPoundsPerUnit(BothHelpObjs.makeBlankFromNull(rs.getString("pounds_per")));
         setStatus(BothHelpObjs.makeBlankFromNull(rs.getString("status")));
         setStartDate(BothHelpObjs.makeBlankFromNull(rs.getString("start_date")));
         setStopDate(BothHelpObjs.makeBlankFromNull(rs.getString("stop_date")));
         //setFacilityId(BothHelpObjs.makeBlankFromNull(rs.getString("facility_id")));
         //setPreferredLocation(BothHelpObjs.makeBlankFromNull(rs.getString("preferred_location")));
         setContainerType(BothHelpObjs.makeBlankFromNull(rs.getString("container_type")));
         //setPackingGroup(BothHelpObjs.makeBlankFromNull(rs.getString("packing_group")));
         //setProperShippingName(BothHelpObjs.makeBlankFromNull(rs.getString("proper_shipping_name")));
         //setShippingIdNumber(BothHelpObjs.makeBlankFromNull(rs.getString("shipping_id_number")));
         setContainerSize(BothHelpObjs.makeZeroFromNull(rs.getString("container_size")));
         setSpecialHandling(BothHelpObjs.makeBlankFromNull(rs.getString("special_handling")));
         setLastPricePaid(BothHelpObjs.makeZeroFromNull(rs.getString("last_price_paid")));
         setBulk(BothHelpObjs.makeBlankFromNull(rs.getString("bulk")));
         setEpaContainerType(BothHelpObjs.makeBlankFromNull(rs.getString("epa_container_type")));
         setEpaSizeUnit(BothHelpObjs.makeBlankFromNull(rs.getString("epa_size_unit")));
       }
     }catch (Exception e) { e.printStackTrace(); throw e;
     }finally{dbrs.close();}
   }

   public static DbTableModel doSearch(TcmISDBModel db,String fac,String workArea,String search_string) throws Exception{
    DbTableModel model = new DbTableModel(colHeads);
    String query;
    // String query = "select waste_profile_id from waste_synonym_view where facility_id = '"+ fac +"' ";
    if (BothHelpObjs.isBlankString(search_string)) {
      query = "select * from waste_catalog_view where facility_id = '"+fac+"'";
      /*if ((!BothHelpObjs.isBlankString(workArea)) && (!workArea.equalsIgnoreCase("all"))) {
        query += " and work_area = '"+ workArea +"' ";
      } */
    }else {
      query = "select * from waste_synonym_catalog_view where facility_id = '"+fac+"'";
      /*if ((!BothHelpObjs.isBlankString(workArea)) && (!workArea.equalsIgnoreCase("all"))) {
        query += " and work_area = '"+ workArea +"' ";
      } */
      query += " and search_string like '%"+ String.valueOf(search_string).toLowerCase() +"%' ";
     }
     query += " order by waste_item_id";

     //System.out.println("------------------------ " + query);
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
          Object[] oa = new Object[colHeads.length];
          oa[SEARCH_ITEM_ID_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("waste_item_id"));
          oa[SEARCH_VENDOR_PROFILE_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("vendor_profile_id"));
          oa[SEARCH_FACILITY_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("facility_id"));
          oa[SEARCH_VENDOR_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("vendor_id"));
          oa[SEARCH_DESC_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("description"));
          oa[SEARCH_WASTE_MGMT_OPT_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("management_option"));
          //oa[SEARCH_MGMT_OPT_LOCATION_COL] = rs.getString("management_option_location");
          oa[SEARCH_WASTE_CATEGORY_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("waste_category_id"));
          oa[SEARCH_WASTE_TYPE_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("waste_type_id"));
          oa[SEARCH_PACKAGING_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("packaging"));
          oa[SEARCH_POUNDS_PER_UNIT_COL] = "tdb";
          model.addRow(oa);
        }
        lastWPI = rs.getInt("waste_item_id");
       }
    }catch(Exception ex){ex.printStackTrace(); throw ex;
    }finally{dbrs.close();}
     model.setColumnPrefWidth(colWidths);
     model.setColumnType(colTypes);
     return model;
   }
}

