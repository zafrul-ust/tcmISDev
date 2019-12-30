package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.both1.helpers.*;
import radian.tcmis.server7.share.db.*;
import java.util.*;
import java.sql.*;


public abstract class WasteShipmentScreen {
  public static final int RADIAN_PROFILE_ID_COL = 0;
  public static final int STATE_WASTE_CODES_COL = 1;
  public static final int ORDER_NUMBER_COL = 2;
  public static final int CONTAINER_ID_COL = 3;
  public static final int DRUM_AGE_COL = 4;
  public static final int WEIGHT_COL = 5;
  public static final int FACILITY_COL = 6;
  public static final int WORK_AREA_COL = 7;
  public static final int VENDOR_PROFILE_ID_COL = 8;
  public static final int VENDOR_COL = 9;
  public static final int QTY_COL = 10;
  public static final int VENDOR_PROFILE_DESC_COL = 11;
  public static final int MGMT_OPTION_COL = 12;
  public static final int MGMT_OPTION_LOCATION_COL = 13;
  public static final int CONTAINER_COL = 14;
  public static final int WASTE_CATEGORY_COL = 15;
  public static final int WASTE_TYPE_COL = 16;
  public static final int LPP_COL = 17;
  public static final int TRANSFER_DATE_COL = 18;
  public static final int COD_COL = 19;
  public static final int WASTE_REQUEST_ID_COL = 20;
  public static final int LINE_ITEM_COL = 21;
  public static final int SELECTED_COL = 22;
  public static final int BULK_OR_CONTAINER_COL = 23;
  public static final int SHIPMENT_ID_COL = 24;
  public static final int AGE_WARNING_COL = 25;
  public static final int PREF_COL = 26;
  public static final int SIZE_UNIT = 27;
  public static final int TSDF_COL = 28;

  public static final String[] colHeads = {"Rad Profile","State Waste Code","Order","ID","Drum Age","Weight","Facility","Accumulation Pt","Vend Profile","Vendor","Qty","Description","Waste Mgmt Opt",
                                           "Mgmt Opt Location","Packaging","Waste Category","Waste Type","LPP","Transfer Date","COD","Waste Request","Line Item","Selected Row","Bulk Or Container","Shipment Id","Age Warning","Prefer","Size Unit","Tsdf"};
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
                                        BothHelpObjs.TABLE_COL_TYPE_STRING,
                                        BothHelpObjs.TABLE_COL_TYPE_STRING};

  public static final int BRADIAN_PROFILE_ID_COL = 0;
  public static final int BSTATE_WASTE_CODES_COL = 1;
  public static final int BORDER_NUMBER_COL = 2;
  public static final int BCONTAINER_ID_COL = 3;
  public static final int BPREFERED_DATE_COL = 4;
  public static final int BVENDOR_PROFILE_ID_COL = 5;
  public static final int BVENDOR_PROFILE_DESC_COL = 6;
  public static final int BCONTAINER_COL = 7;
  public static final int BPLANNED_AMOUNT_COL = 8;
  public static final int BFACILITY_COL = 9;
  public static final int BWORK_AREA_COL = 10;
  public static final int BVENDOR_COL = 11;
  public static final int BQTY_COL = 12;
  public static final int BMGMT_OPTION_COL = 13;
  public static final int BMGMT_OPTION_LOCATION_COL = 14;
  public static final int BWASTE_CATEGORY_COL = 15;
  public static final int BWASTE_TYPE_COL = 16;
  public static final int BLPP_COL = 17;
  public static final int BTRANSFER_DATE_COL = 18;
  public static final int BCOD_COL = 19;
  public static final int BWASTE_REQUEST_ID_COL = 20;
  public static final int BLINE_ITEM_COL = 21;
  public static final int BSELECTED_COL = 22;
  public static final int BBULK_OR_CONTAINER_COL = 23;
  public static final int BSHIPMENT_ID_COL = 24;

  public static final String[] bulkColHeads = {"Rad Profile","State Waste Code","Order","ID","Preferred","Vend Profile","Description","Packaging","Planned Amt.","Facility","Accumulation Pt","Vendor","Qty","Waste Mgmt Opt",
                                           "Mgmt Opt Location","Waste Category","Waste Type","LPP","Transfer Date","COD","Waste Request","Line Item","Selected Row","Bulk Or Container","Shipment Id"};
  public static final int[] bulkColTypes = {BothHelpObjs.TABLE_COL_TYPE_STRING,
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
  protected String radianProfileId;
  protected String facilityId;
  protected static String workArea;
  protected String vendorProfileId;
  protected String vendor;
  protected int qty;
  protected String wasteProfileDesc;
  protected String containerSize;
  protected String wasteMgmtOpt;
  protected String transferDate;
  protected String mgmtOptLocation;
  protected String wasteCategory;
  protected String wasteType;
  protected String lpp;
  protected String cod;
  protected String wasteRequestId;
  protected String lineITem;
  protected int containerId;


  //set methods
  public static void setShipmentId(int s){shipmentId = s;}
  public void setWorkArea(String s){workArea = s;}
  public void setRadianProfileId(String s){radianProfileId = s;}
  public void setVendorProfileId(String s){vendorProfileId = s;}
  public void setQty(int s){qty = s;}
  public void setWasteProfileDesc(String s){ wasteProfileDesc = s;}
  public void setContainerSize(String s){ containerSize = s;}
  public void setVendor(String s){ vendor = s;}
  public void setTransferDate(String s){ transferDate = s;}
  public void setWasteMgmtOpt(String s){ wasteMgmtOpt = s;}
  public void setMgmtOptLocation(String s){ mgmtOptLocation = s;}
  public void setWasteCategory(String s){ wasteCategory = s;}
  public void setLpp(String s){ lpp = s;}
  public void setWasteType(String s){ wasteType = s;}
  public void setFacilityId(String s){facilityId = s;}
  public void setCod(String s){ cod = s;}
  public void setContainerId(int s){ containerId = s;}
  //get methods
  public static int getShipmentId(){return shipmentId;}
  public String getWorkArea(){return workArea;}
  public String getRadianProfileId(){return radianProfileId;}
  public String getVendorProfileId(){return vendorProfileId;}
  public int getQty(int s){return qty;}
  public String getWasteProfileDesc(){return wasteProfileDesc;}
  public String getContainerSize(){return containerSize;}
  public String getVendor(){return vendor;}
  public String getTransferDate(){return transferDate;}
  public String getWasteMgmtOpt(){return wasteMgmtOpt;}
  public String getMgmtOptLocation(){return mgmtOptLocation;}
  public String getWasteCategory(){return wasteCategory;}
  public String getLpp(){return lpp;}
  public String getWasteType(){return wasteType;}
  public String getFacilityId(){return facilityId;}
  public String getCod(){return cod;}
  public int getContainerId(){return containerId;}

  //waste case running with lots of query
  public static Hashtable getInitialInfo(TcmISDBModel db,int userId) throws Exception{
    Hashtable result = new Hashtable();
    Vector facilityId = new Vector();

    String query = "select distinct(facility_id) from waste_facility_storage_view where personnel_id = "+userId;
    query += " order by facility_id";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      //System.out.println("++++++++++++++++++++++++ " + query);
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while(rs.next()){
        facilityId.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("facility_id")));
      }
    }catch(Exception ex){ex.printStackTrace(); throw ex;
    }finally{dbrs.close();}
    result.put("FACILITY_ID",facilityId);

    for (int i = 0; i < facilityId.size(); i++) {
      query = "select * from waste_facility_storage_view where personnel_id = "+userId;
      query += " and facility_id = '"+facilityId.elementAt(i).toString()+"'";
      Vector storageDesc = new Vector();
      Vector storageId = new Vector();
      Hashtable storageInfo = new Hashtable();
      try {
        //System.out.println("++++++++++++++++++++++++ " + query);
        dbrs = db.doQuery(query);
        rs=dbrs.getResultSet();
        while(rs.next()){
          storageId.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("waste_location_id")));
          storageDesc.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("location_desc")));
        }
      }catch(Exception ex){ex.printStackTrace(); throw ex;
      }finally{dbrs.close();}
      storageInfo.put("LOCATION_DESC",storageDesc);
      storageInfo.put("LOCATION_ID",storageId);
      result.put(facilityId.elementAt(i).toString(),storageInfo);
    }

    return result;
  }

  //running with two query
  public static Hashtable getInitialInfo2(TcmISDBModel db,int userId) throws Exception{
    Hashtable result = new Hashtable();
    Vector facilityId = new Vector();
    Vector storageDesc = new Vector();
    Vector storageId = new Vector();
    Hashtable storageInfo = new Hashtable();
    String query = "select * from waste_facility_storage_view where personnel_id = "+userId;
    query += " order by facility_id";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      //System.out.println("++++++++++++++++++++++++ " + query);
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      String lastFac = "";
      int count = 0;
      while(rs.next()){
       String facId = BothHelpObjs.makeBlankFromNull(rs.getString("facility_id"));
       if (!lastFac.equals(facId)) {
        facilityId.addElement(facId);
       }
       lastFac = facId;
      }
      }catch(Exception ex){ex.printStackTrace(); throw ex;
      }finally{dbrs.close();}
      try{
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while (rs.next()){
        for (int i = 0; i < facilityId.size(); i++) {
          String key = rs.getString("facility_id");
          if (key.equals(facilityId.elementAt(i).toString())) {
            if(result.containsKey(key)) {
              String temp1 = "";
              String temp2 = "";
              Vector locV = new Vector();
              Vector descV = new Vector();
              Hashtable h = (Hashtable)result.get(key);
              descV = (Vector)h.get("LOCATION_DESC");
              temp1 = rs.getString("location_desc");
              descV.addElement(temp1);
              locV = (Vector)h.get("WASTE_LOCATION_ID");
              temp2 = rs.getString("waste_location_id");
              locV.addElement(temp2);
              h.put("LOCATION_DESC",descV);
              h.put("WASTE_LOCATION_ID",locV);
              result.put(key,h);
            } else {
              Hashtable h = new Hashtable();
              Vector locV = new Vector();
              Vector descV = new Vector();
              String temp1 = "";
              String temp2 = "";
              temp1 = rs.getString("location_desc");
              descV.addElement(temp1);
              temp2 = rs.getString("waste_location_id");
              locV.addElement(temp2);
              h.put("LOCATION_DESC",descV);
              h.put("WASTE_LOCATION_ID",locV);
              result.put(key,h);
            }
          }
        }
      }

    }catch(Exception ex){ex.printStackTrace(); throw ex;
    }finally{dbrs.close();}
    result.put("FACILITY_ID",facilityId);
    return result;
  }

  //running with one query
  public static Hashtable getInitialInfo3(TcmISDBModel db,int userId, String status) throws Exception{
    Hashtable result = new Hashtable();
    Vector facilityId = new Vector();
    Vector labPackOption = new Vector();
    Hashtable storageInfo = new Hashtable();
    String query = "select * from waste_facility_storage_view where personnel_id = "+userId;
    if (status.equalsIgnoreCase("A")) {
      query += " and status = 'A' and active = 'Y'";
    }
    query += " order by facility_id";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      String lastFac = "";
      int count = 0;
      while(rs.next()){
       String facId = BothHelpObjs.makeBlankFromNull(rs.getString("facility_id"));
       if (!lastFac.equals(facId)) {
        facilityId.addElement(facId);
       }

       if(result.containsKey(facId)) {
        String temp1 = "";
        String temp2 = "";
        String name = "";
        String vendId = "";
        String lastVendId = "";
        Vector locV = new Vector();
        Vector descV = new Vector();
        Vector vendorDesc = new Vector();
        Vector vendorId = new Vector();
        Hashtable h = (Hashtable)result.get(facId);

        locV = (Vector)h.get("WASTE_LOCATION_ID");
        temp2 = rs.getString("waste_location_id");
        if (!locV.contains(temp2)) {
          descV = (Vector)h.get("LOCATION_DESC");
          temp1 = rs.getString("location_desc");
          descV.addElement(temp1);

          locV.addElement(temp2);

          h.put("LOCATION_DESC",descV);
          h.put("WASTE_LOCATION_ID",locV);
        }

        vendorId = (Vector)h.get("VENDOR_ID");
        vendId = rs.getString("vendor_id");
        if (!vendorId.contains(vendId)) {
          vendorId.addElement(vendId);

          vendorDesc = (Vector)h.get("COMPANY_NAME");
          name = rs.getString("waste_vendor_name");
          vendorDesc.addElement(name);

          h.put("VENDOR_ID",vendorId);
          h.put("COMPANY_NAME",vendorDesc);
        }
        result.put(facId,h);
       } else {
        Hashtable h = new Hashtable();
        Vector locV = new Vector();
        Vector descV = new Vector();
        String temp1 = "";
        String temp2 = "";
        String name = "";
        String vendId = "";
        Vector vendorDesc = new Vector();
        Vector vendorId = new Vector();

        temp1 = rs.getString("location_desc");
        descV.addElement(temp1);

        temp2 = rs.getString("waste_location_id");
        locV.addElement(temp2);

        name = rs.getString("waste_vendor_name");
        vendorDesc.addElement(name);

        vendId = rs.getString("vendor_id");
        vendorId.addElement(vendId);

        h.put("LOCATION_DESC",descV);
        h.put("WASTE_LOCATION_ID",locV);
        h.put("COMPANY_NAME",vendorDesc);
        h.put("VENDOR_ID",vendorId);
        result.put(facId,h);
       }

       lastFac = facId;

       //getting lab pack option
       //facility+waste_location_id+vendor_id
       if ("Y".equalsIgnoreCase(rs.getString("lab_pack_option"))) {
         String key = facId+rs.getString("waste_location_id")+rs.getString("vendor_id");
         if (!labPackOption.contains(key)) {
           labPackOption.addElement(key);
         }
       }
      } //end of while

    }catch(Exception ex){ex.printStackTrace(); throw ex;
    }finally{dbrs.close();}
    result.put("FACILITY_ID",facilityId);
    result.put("LAB_PACK_OPTION",labPackOption);
    return result;
  }

  public static boolean allowToDeleteContainer(TcmISDBModel db, Integer uid) throws Exception {
    DBResultSet dbrs = null;
    ResultSet rs = null;
    int result = 0;
    String query = "select count(*) from user_group_member where user_group_id = 'WasteContainerDelete' and personnel_id = "+uid.intValue();
    int count = 0;
    try {
      count = DbHelpers.countQuery(db,query);
    }catch(Exception e) {
      e.printStackTrace();
      return false;
    }
    return (count > 0);
  }


  public static Hashtable doSearch(TcmISDBModel db,String facility,String storageArea,String vendor) throws Exception {
    Hashtable dataH = new Hashtable(4);
    Vector dataV = new Vector();
    int[] colWidths = {0,100,50,80,50,0,60,60,80,120,0,130,100,150,60,100,75,50,90,0,0,0,0,0,0,0,0,0,0};
    String query = "select * from waste_order_view where actual_ship_date is null and";
    query += " (transfer_date is not null or traveler_id is null or bulk_or_container = 'B' or (transfer_date is null and accumulation = 'Y'))";
    query += " and container_id is not null";   //don't want to show lab pack order without container
    if (!facility.equalsIgnoreCase("all")){
      query += " and facility_id = '"+facility+"'";
    }
    if (!BothHelpObjs.isBlankString(storageArea) && (!storageArea.equalsIgnoreCase("all"))){
      query += " and storage_location = '"+storageArea+"'";
    }
    if ((!BothHelpObjs.isBlankString(vendor)) && (!vendor.equalsIgnoreCase("all"))){
      query += " and vendor_id = '"+vendor+"'";
    }
    query += " order by container_id";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    int lastWri = -1;
    int lastLi = -1;

    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      int count = 0;
      while(rs.next()){
          Object[] oa = new Object[colHeads.length];
          oa[RADIAN_PROFILE_ID_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("waste_item_id"));
          oa[STATE_WASTE_CODES_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("state_waste_codes"));
          oa[ORDER_NUMBER_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("order_no"));
          oa[CONTAINER_ID_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("container_id"));
          oa[FACILITY_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("facility_id"));
          oa[WORK_AREA_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("generation_point_desc"));      //work area desc
          oa[VENDOR_PROFILE_ID_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("vendor_profile_id"));
          oa[VENDOR_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("vendor_name"));
          oa[VENDOR_PROFILE_DESC_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("description"));
          oa[MGMT_OPTION_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("management_option"));
          oa[MGMT_OPTION_LOCATION_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("management_option_location"));
          oa[CONTAINER_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("packaging"));
          oa[WASTE_CATEGORY_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("waste_category_id"));
          oa[WASTE_TYPE_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("waste_type_id"));
          oa[LPP_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("last_price_paid"));           //9-30
          String d =  BothHelpObjs.makeBlankFromNull(rs.getString("transfer_date"));
          if (d.length() == 0){
            Calendar cal = Calendar.getInstance();
            oa[TRANSFER_DATE_COL] = BothHelpObjs.formatDate("toCharfromNum",new String((cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DATE)+"/"+cal.get(Calendar.YEAR)));
          }else {
            oa[TRANSFER_DATE_COL] = BothHelpObjs.formatDate("toCharfromDB",rs.getString("transfer_date"));
          }
          oa[COD_COL] = "tbd";
          oa[QTY_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("capacity"));
          oa[WASTE_REQUEST_ID_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("waste_request_id"));
          oa[LINE_ITEM_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("line_item"));
          String sealDate = BothHelpObjs.makeBlankFromNull(rs.getString("seal_date"));
          String flag = BothHelpObjs.makeBlankFromNull(rs.getString("accumulation"));
          if (flag.equalsIgnoreCase("Y") && BothHelpObjs.isBlankString(sealDate)) {
            oa[SELECTED_COL] = new Boolean(true);
          }else {
            oa[SELECTED_COL] = new Boolean(false);
          }
          oa[BULK_OR_CONTAINER_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("bulk_or_container"));
          oa[DRUM_AGE_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("days_since_seal"));
          String shipmentTrigger = BothHelpObjs.makeBlankFromNull(rs.getString("shipment_trigger"));
          if (shipmentTrigger.equalsIgnoreCase("Y") ) {
            oa[AGE_WARNING_COL] = new Boolean(true);
          }else {
            oa[AGE_WARNING_COL] = new Boolean(false);
          }
          oa[PREF_COL] = "";
          oa[SIZE_UNIT] = BothHelpObjs.makeBlankFromNull(rs.getString("size_unit"));
          oa[TSDF_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("tsdf"));
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

   public static Hashtable getScreenData(TcmISDBModel db,int orderNo, int userId) throws Exception{
    Hashtable rhash = new Hashtable();
    Hashtable header = new Hashtable();
    Vector containerV = new Vector();
    Vector bulkV = new Vector();
    int[] bulkColWidths = {0,100,0,80,80,80,130,100,80,50,60,0,0,100,150,100,75,50,0,0,0,0,0,0,0};
    int[] colWidths = {0,100,0,80,50,50,50,60,80,0,0,130,100,150,60,100,75,50,0,0,0,0,0,0,0,0,0,0,0};
    String query = null;
    DBResultSet dbrs = null;
    ResultSet rs = null;
    String containerShippedOn = "";

    //header info
    query = "select * from waste_order_header_view where rownum = 1 and order_no = "+orderNo;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while(rs.next()){
        header.put("VENDOR_NAME",BothHelpObjs.makeBlankFromNull(rs.getString("vendor_name")));
        header.put("VENDOR_ID",BothHelpObjs.makeBlankFromNull(rs.getString("vendor_id")));
        header.put("FACILITY_ID",BothHelpObjs.makeBlankFromNull(rs.getString("facility_id")));
        String date = BothHelpObjs.formatDate("toCharfromDB",BothHelpObjs.makeBlankFromNull(rs.getString("original_submit_date")));
        header.put("DATE_SUBMIT",date);
        date = BothHelpObjs.formatDate("toCharfromDB",BothHelpObjs.makeBlankFromNull(rs.getString("resubmit_date")));
        header.put("DATE_RESUBMIT",date);
        header.put("STORAGE_DESC",BothHelpObjs.makeBlankFromNull(rs.getString("storage_location_id")));
        header.put("STORAGE_ID",BothHelpObjs.makeBlankFromNull(rs.getString("storage_location_id")));
        header.put("REQUESTER_NAME",BothHelpObjs.makeBlankFromNull(rs.getString("requester_name")));
        String itemID = BothHelpObjs.makeBlankFromNull(rs.getString("item_id"));
        header.put("CONTAIN_MATERIAL",new Boolean(!BothHelpObjs.isBlankString(itemID)));
        header.put("LAB_PACK_OPTION",BothHelpObjs.makeBlankFromNull(rs.getString("lab_pack_option")));
      }
      //4-12-01 getting the account system for given facility
      WasteMaterialRequest wmr = new WasteMaterialRequest(db);
      Vector v = wmr.getActSysID((String)header.get("FACILITY_ID"));
      header.put("ACT_SYS_ID",v);
    }catch(Exception ex){ex.printStackTrace(); throw ex;
    }finally{dbrs.close();}

    //shipment table
    query = "select x.*, to_char(preferred_service_date, 'mm/dd/yyyy') formatted_pref_date,to_char(lab_pack_preferred_service_dat, 'mm/dd/yyyy') lab_pack_service_date from waste_order_view x where order_no = " +orderNo;
    query += " order by container_id";
    String labPackPreferredServiceDate = "";
    String labPackDrumEstimate = "";
    String contShipId = "";
    String bulkShipId = "";
    Vector bulkShipStatusV = new Vector();
    Vector contShipStatusV = new Vector();
    boolean firstTime = true;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      int count = 0;
      while(rs.next()){
          Object[] oa = new Object[colHeads.length];
          Object[] bulkOa = new Object[bulkColHeads.length];
          String containerType = BothHelpObjs.makeBlankFromNull(rs.getString("bulk_or_container"));
          String myContId = BothHelpObjs.makeBlankFromNull(rs.getString("container_id"));
          if (containerType.equalsIgnoreCase("C")) {
            if (!BothHelpObjs.isBlankString(myContId)) {
              oa[RADIAN_PROFILE_ID_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("waste_item_id"));
              oa[STATE_WASTE_CODES_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("state_waste_codes"));
              oa[CONTAINER_ID_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("container_id"));
              oa[FACILITY_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("facility_id"));
              oa[WORK_AREA_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("generation_point_desc"));
              oa[VENDOR_PROFILE_ID_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("vendor_profile_id"));
              oa[VENDOR_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("vendor_id"));
              oa[VENDOR_PROFILE_DESC_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("description"));
              oa[MGMT_OPTION_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("management_option"));
              oa[MGMT_OPTION_LOCATION_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("management_option_location"));
              oa[CONTAINER_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("packaging"));
              oa[WASTE_CATEGORY_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("waste_category_id"));
              oa[WASTE_TYPE_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("waste_type_id"));
              oa[LPP_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("last_price_paid"));
              oa[QTY_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("capacity"));
              oa[WASTE_REQUEST_ID_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("waste_request_id"));
              oa[LINE_ITEM_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("line_item"));
              oa[DRUM_AGE_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("days_since_seal"));
              String shipmentTrigger = BothHelpObjs.makeBlankFromNull(rs.getString("shipment_trigger"));
              if (shipmentTrigger.equalsIgnoreCase("Y") ) {
                oa[AGE_WARNING_COL] = new Boolean(true);
              }else {
                oa[AGE_WARNING_COL] = new Boolean(false);
              }
              oa[SHIPMENT_ID_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("SHIPMENT_ID"));
              oa[WEIGHT_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("weight"));
              oa[PREF_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("formatted_pref_date"));
              oa[SIZE_UNIT] = "";
              oa[TSDF_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("tsdf"));
              containerV.addElement(oa);
            }
            //4-10-01
            labPackPreferredServiceDate = BothHelpObjs.makeBlankFromNull(rs.getString("lab_pack_service_date"));
            labPackDrumEstimate = BothHelpObjs.makeBlankFromNull(rs.getString("lab_pack_drum_estimate"));

            containerShippedOn = BothHelpObjs.makeBlankFromNull(rs.getString("actual_ship_date"));     //3-13-01
            contShipId = BothHelpObjs.makeBlankFromNull(rs.getString("shipment_id"));
            //6-26-01 passing the status of each shipment to client so it know how to display the edit buttons
            if (firstTime) {
              contShipStatusV.addElement((String)getShipmentStatus(db,contShipId));
              firstTime = false;
            }
          }else {
            bulkOa[BRADIAN_PROFILE_ID_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("waste_item_id"));
            bulkOa[BSTATE_WASTE_CODES_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("state_waste_codes"));
            bulkOa[BCONTAINER_ID_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("container_id"));
            bulkOa[BPLANNED_AMOUNT_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("planned_amount"));
            bulkOa[BPREFERED_DATE_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("formatted_pref_date"));;
            bulkOa[BFACILITY_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("facility_id"));
            bulkOa[BWORK_AREA_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("generation_point_desc"));
            bulkOa[BVENDOR_PROFILE_ID_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("vendor_profile_id"));
            bulkOa[BVENDOR_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("vendor_id"));
            bulkOa[BVENDOR_PROFILE_DESC_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("description"));
            bulkOa[BMGMT_OPTION_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("management_option"));
            bulkOa[BMGMT_OPTION_LOCATION_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("management_option_location"));
            bulkOa[BCONTAINER_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("packaging"));
            bulkOa[BWASTE_CATEGORY_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("waste_category_id"));
            bulkOa[BWASTE_TYPE_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("waste_type_id"));
            bulkOa[BLPP_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("last_price_paid"));
            bulkOa[BQTY_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("capacity"));
            bulkOa[BWASTE_REQUEST_ID_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("waste_request_id"));
            bulkOa[BLINE_ITEM_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("line_item"));
            bulkOa[BSHIPMENT_ID_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("shipment_id"));
            bulkV.addElement(bulkOa);
            bulkShipId = BothHelpObjs.makeBlankFromNull(rs.getString("shipment_id"));
            //6-26-01 passing the status of each shipment to client so it know how to display the edit buttons
            bulkShipStatusV.addElement((String)getShipmentStatus(db,bulkShipId));
          }
      }
    }catch(Exception ex){ex.printStackTrace(); throw ex;
    }finally{dbrs.close();}

    //set default shipment id
    if (contShipId.length() >= 1) {
      header.put("SHIPMENT_ID",contShipId);
    }else if (bulkShipId.length() >= 1) {
      header.put("SHIPMENT_ID",bulkShipId);
    }else {
      header.put("SHIPMENT_ID","0");      //should not reach here
    }

    //4-23-01 setting container shipment id to waste_order submit/resubmit, and lab pack
    if (contShipId.length() >= 1) {
      header.put("CONTAINER_SHIPMENT_ID",contShipId);
    }else{
      header.put("CONTAINER_SHIPMENT_ID","0");
    }

    //6-26-01 passing the status of each shipment to client so it know how to display the edit buttons
    header.put("CONTAINER_SHIPMENT_STATUS",contShipStatusV);
    header.put("BULK_SHIPMENT_STATUS",bulkShipStatusV);

    rhash.put("HEADER",header);
    containerV.trimToSize();
    rhash.put("SHIPMENT_TABLE_HEADERS",colHeads);
    rhash.put("SHIPMENT_TABLE_WIDTHS",colWidths);
    rhash.put("SHIPMENT_TABLE_TYPES",colTypes);
    rhash.put("SHIPMENT_TABLE_DATA",containerV);
    bulkV.trimToSize();
    rhash.put("BULK_SHIPMENT_TABLE_HEADERS",bulkColHeads);
    rhash.put("BULK_SHIPMENT_TABLE_WIDTHS",bulkColWidths);
    rhash.put("BULK_SHIPMENT_TABLE_TYPES",bulkColTypes);
    rhash.put("BULK_SHIPMENT_TABLE_DATA",bulkV);
    rhash.put("PLANNED_AMOUNT_COL",new Integer(BPLANNED_AMOUNT_COL));
    rhash.put("BULK_SHIPMENT_ID_COL",new Integer(BSHIPMENT_ID_COL));
    rhash.put("SHIPMENT_ID_COL",new Integer(BSHIPMENT_ID_COL));
    rhash.put("PREF_COL",new Integer(PREF_COL));
    rhash.put("BPREFERED_DATE_COL",new Integer(BPREFERED_DATE_COL));

    rhash.put("CONTAINER_SHIPPED_ON",containerShippedOn);   //3-13-01 whether to redisplay the submit/resubmit to vendor
    rhash.put("LAB_PACK_PREFERRED_SERVICE_DATE",labPackPreferredServiceDate);
    rhash.put("LAB_PACK_DRUM_ESTIMATE",labPackDrumEstimate);

    rhash.put("BULK_FACILITY_COL",new Integer(9));
    rhash.put("BULK_VENDOR_COL",new Integer(11));

    //checking to see if the user belong to radian invoice group
    UserGroup ug = new UserGroup(db);
    ug.setGroupFacId("All");                      //hard code to All until Ron added it to specific facility
    ug.setGroupId(new String("RadianWaste"));
    boolean b = ug.isMember(userId);
    rhash.put("RADIAN_WASTE",new Boolean(b));

    return rhash;
   }

   static String getShipmentStatus(TcmISDBModel db, String shipID) throws Exception {
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

   static Hashtable getShipmentStatusInfo(TcmISDBModel db, int orderNo) throws Exception {
    String query = "select shipment_id from waste_container where shipment_id is not null and order_no = "+orderNo;
    DBResultSet dbrs = null;
    ResultSet rs = null;
    Hashtable result = new Hashtable();
    //first get container shipment status
    String shipId = "";
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while(rs.next()){
        shipId = BothHelpObjs.makeBlankFromNull(rs.getString("shipment_id"));
      }
    }catch(Exception e) {
      e.printStackTrace();
      throw e;
    }finally {
      dbrs.close();
    }
    if (shipId.length() > 0 ) {
      result.put("CONTAINER_SHIPMENT_STATUS",getShipmentStatus(db,shipId));
      //result.put("CONTAINER_SHIPMENT_STATUS","invoiced");   //for testing only
    }else {
      result.put("CONTAINER_SHIPMENT_STATUS","NoContainerShipment");
    }

    //now get bulk shipment status
    query = "select shipment_id from bulk_order where order_no = "+orderNo;
    Vector bulkShipId = new Vector();
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while(rs.next()){
        bulkShipId.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("shipment_id")));
      }
    }catch(Exception e) {
      e.printStackTrace();
      throw e;
    }finally {
      dbrs.close();
    }
    Vector v = new Vector();
    for (int i = 0; i < bulkShipId.size(); i++) {
      v.addElement(getShipmentStatus(db,bulkShipId.elementAt(i).toString()));
      //v.addElement("locked");     //for testing only
    }
    result.put("BULK_SHIPMENT_STATUS",v);
    result.put("BULK_SHIPMENT_ID",bulkShipId);

    return result;
   }

   public static Hashtable getEditInfo(TcmISDBModel db,String facility,String storageArea,String vendor) throws Exception {
    int[] colWidths = {0,100,50,80,50,0,50,60,80,120,0,130,100,150,60,100,75,50,90,0,0,0,0,0,0,0,0,0,0};
    String containerShipmentId = new String("0");
    Hashtable result = new Hashtable();
    Vector dataV = new Vector();
    String query = "select * from waste_order_edit_view where (transfer_date is not null or (traveler_id is null and seal_date is not null) or bulk_or_container = 'B' or (transfer_date is null and accumulation = 'Y' and seal_date is not null))";
    query += " and vendor_id = '"+vendor+"'";
    query += " and (query_order_no = " +getShipmentId();
    query += " or query_order_no is null)";
    query += " and storage_location = '"+storageArea+"'";
    query += " and facility_id = '"+facility+"'";
    query += " and vendor_profile_id != 'UNKNOWN'";
    query += " order by container_id";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    int lastWri = -1;
    int lastLi = -1;

    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      int count = 0;
      while(rs.next()){
          Object[] oa = new Object[colHeads.length];
          oa[RADIAN_PROFILE_ID_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("waste_item_id"));
          oa[STATE_WASTE_CODES_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("state_waste_codes"));
          oa[ORDER_NUMBER_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("order_no"));
          oa[CONTAINER_ID_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("container_id"));
          oa[FACILITY_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("facility_id"));
          oa[WORK_AREA_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("generation_point_desc"));
          oa[VENDOR_PROFILE_ID_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("vendor_profile_id"));
          oa[VENDOR_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("vendor_name"));
          oa[VENDOR_PROFILE_DESC_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("description"));
          oa[MGMT_OPTION_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("management_option"));
          oa[MGMT_OPTION_LOCATION_COL] = rs.getString("management_option_location");
          oa[CONTAINER_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("packaging"));
          oa[WASTE_CATEGORY_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("waste_category_id"));
          oa[WASTE_TYPE_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("waste_type_id"));
          oa[LPP_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("last_price_paid"));
          String d =  BothHelpObjs.makeBlankFromNull(rs.getString("transfer_date"));
          if (d.length() == 0){
            Calendar cal = Calendar.getInstance();
            oa[TRANSFER_DATE_COL] = BothHelpObjs.formatDate("toCharfromNum",new String((cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DATE)+"/"+cal.get(Calendar.YEAR)));
          }else {
            oa[TRANSFER_DATE_COL] = BothHelpObjs.formatDate("toCharfromDB",rs.getString("transfer_date"));
          }
          oa[COD_COL] = "tbd";
          oa[QTY_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("capacity"));
          oa[WASTE_REQUEST_ID_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("waste_request_id"));
          //until I get a chance to go back to fix waste I will hardcode line item
          oa[LINE_ITEM_COL] = "";

          int orderNo =  BothHelpObjs.makeZeroFromNull(rs.getString("order_no"));
          if (orderNo == getShipmentId()) {
            oa[SELECTED_COL] = new Boolean(true);
          }else {
            oa[SELECTED_COL] = new Boolean(false);
          }
          oa[BULK_OR_CONTAINER_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("bulk_or_container"));
          oa[SHIPMENT_ID_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("shipment_id"));
          String containerType = BothHelpObjs.makeBlankFromNull(rs.getString("bulk_or_container"));
          String tmpOrderNo = BothHelpObjs.makeBlankFromNull(rs.getString("order_no"));
          if (containerType.equalsIgnoreCase("C") && !BothHelpObjs.isBlankString(tmpOrderNo)) {
            containerShipmentId = BothHelpObjs.makeBlankFromNull(rs.getString("shipment_id"));
          }
          oa[DRUM_AGE_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("days_since_seal"));
          String shipmentTrigger = BothHelpObjs.makeBlankFromNull(rs.getString("shipment_trigger"));
          if (shipmentTrigger.equalsIgnoreCase("Y") ) {
            oa[AGE_WARNING_COL] = new Boolean(true);
          }else {
            oa[AGE_WARNING_COL] = new Boolean(false);
          }
          oa[SIZE_UNIT] = "";
          oa[TSDF_COL] = "";
          dataV.addElement(oa);
      }
    }catch(Exception ex){ex.printStackTrace(); throw ex;
    }finally{dbrs.close();}
    result.put("TABLE_HEADERS",colHeads);
    result.put("TABLE_WIDTHS",colWidths);
    result.put("TABLE_TYPES",colTypes);
    dataV.trimToSize();
    result.put("TABLE_DATA",dataV);
    result.put("CONTAINER_SHIPMENT_ID",containerShipmentId);
    return result;
   }


}




