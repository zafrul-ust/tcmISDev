package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.both1.helpers.*;
import radian.tcmis.server7.share.db.*;
import java.util.*;
import java.sql.*;

/*
SQLWKS> describe waste_tracking_view
Column Name                    Null?    Type
------------------------------ -------- ----
WORK_AREA                      NOT NULL VARCHAR2(30)
REQUESTER                               VARCHAR2(61)
REQUEST_DATE                            DATE
QUANTITY                                NUMBER
REPLACE_CONTAINER                       VARCHAR2(1)
NOTE                                    VARCHAR2(2000)
PROFILE_ID                     NOT NULL VARCHAR2(60)
CONTAINER_SIZE                 NOT NULL VARCHAR2(15)
PROFILE_DESCRIPTION                     VARCHAR2(200)
DOT_DESCRIPTION                         CHAR(40)
PICKED_UP                               NUMBER
PICKUP_DATE                             DATE
DAYS_SINCE_PICKUP                       VARCHAR2(40)
STORAGE_AREA                            CHAR(39)
*/


public abstract class WasteTrackScreen {
  public static final int WT_WASTE_ITEM_ID_COL = 0;
  public static final int WT_STATUS_COL = 1;
  public static final int WT_REQUESTED_TRANSFER_COL = 2;
  public static final int WT_AGE_COL = 3;
  public static final int WT_TRANSFERRED_COL = 4;
  public static final int WT_WASTE_FROM_LOCATION_ID_COL = 5;
  public static final int WT_WASTE_TO_LOCATION_ID_COL = 6;
  public static final int WT_CONTAINER_ID_COL = 7;
  public static final int WT_PACKAGING_COL = 8;
  public static final int WT_WASTE_PROFILE_ID_COL = 9;
  public static final int WT_WASTE_PROFILE_DESC_COL = 10;
  public static final int WT_WORK_AREA_COL = 11;
  public static final int WT_REQUESTER_ID_COL = 12;
  public static final int WT_DOT_DESC_COL = 13;
  public static final int WT_REPLACE_CONTAINER_COL = 14;
  public static final int WT_PICKUP_DATE_COL = 15;
  public static final int WT_NOTE_COL = 16;
  public static final int WT_DAYS_SINCE_PICKUP_COL = 17;
  public static final int WT_REAL_NOTE_COL = 18;
  public static final int WT_WASTE_REQUEST_ID_COL = 19;
  public static final int WT_LINE_ITEM_COL = 20;
  public static final int WT_REQUEST_DATE_COL = 21;
  public static final int WT_AGE_WARNING_COL = 22;
  public static final String[] colHeads = {"Waste Item Id","Status","Requested Transfer","Age","Transferred","From","To","Container ID","Packaging","Waste Profile","Description",
                                           "Accumulation Pt","Requester","DOT Desc.","Replace Container","Transfer Date","Note","Days Since Pickup","Real Notes",
                                           "Waste Request Id","Line Item","Request Date","Age Warning"};
  public static final int[] colWidths = {0,80,110,35,70,50,50,90,80,90,130,70,70,90,90,90,35,0,0,0,0,0,0};
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
                                        BothHelpObjs.TABLE_COL_TYPE_STRING};

  protected TcmISDBModel db;
  protected int transferred;
  protected String fromLocationId;
  protected static String workArea;
  protected String requester;
  protected String requestDate;
  //protected int qty;
  protected String wasteProfileId;
  protected String wasteProfileDesc;
  protected String containerSize;
  protected String dotDesc;
  protected String pickupDate;
  protected String replaceContainer;
  protected String toLocationId;
  protected String daysSincePickup;
  protected String note;
  protected int wasteRequestId;
  protected int lineItem;
  protected String facilityId;
  protected int wasteItemId;
  protected int containerId;


  //set methods
  public void setWasteItemId(int s){wasteItemId = s;}
  public void setWorkArea(String s){workArea = s;}
  public void setRequester(String s){requester = s;}
  public void setRequestDate(String s){requestDate = s;}
  //public void setQty(int s){qty = s;}
  public void setWasteProfileId(String s){ wasteProfileId = s;}
  public void setWasteProfileDesc(String s){ wasteProfileDesc = s;}
  public void setContainerSize(String s){ containerSize = s;}
  public void setDotDesc(String s){ dotDesc = s;}
  public void setTransferred(int s){ transferred = s;}
  public void setPickupDate(String s){ pickupDate = s;}
  public void setReplaceContainer(String s){ replaceContainer = s;}
  public void setToLocationId(String s){ toLocationId = s;}
  public void setDaysSincePickup(String s){ daysSincePickup = s;}
  public void setNote(String s){ note = s;}
  public void setWasteRequestId(int s){wasteRequestId = s;}
  public void setLineItem(int s){lineItem = s;}
  public void setFacilityId(String s){facilityId = s;}
  public void setFromLocationId(String s){fromLocationId = s;}
  public void setContainerId(int s){ containerId = s;}
  //get methods
  public int getWasteItemId(){return wasteItemId;}
  public String getWorkArea(){return workArea;}
  public String getRequester(){return requester;}
  public String getRequestDate(){return requestDate;}
  //public int getQty(int s){return qty;}
  public String getWasteProfileId(){return wasteProfileId;}
  public String getWasteProfileDesc(){return wasteProfileDesc;}
  public String getContainerSize(){return containerSize;}
  public String getDotDesc(){return dotDesc;}
  public int getTransferred(){return transferred;}
  public String getPickupDate(){return pickupDate;}
  public String getReplaceContainer(){return replaceContainer;}
  public String getToLocationId(){return toLocationId;}
  public String getDaysSincePickup(){return daysSincePickup;}
  public String getNote(){return note;}
  public int getWasteRequestId(){return wasteRequestId;}
  public int getLineItem(){return lineItem;}
  public String getFacilityId(){return facilityId;}
  public String getFromLocationId(){return fromLocationId;}

  public static Vector getPreferFac(TcmISDBModel db, int userId) throws Exception{
    Vector result = new Vector();
    String query = "select facility_id from fac_pref where personnel_id = "+userId;
    query += " order by facility_id";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while(rs.next()){
        result.addElement(rs.getString("facility_id"));
      }
    }catch(Exception ex){ex.printStackTrace(); throw ex;
    }finally{dbrs.close();}

    return result;
  }

  public static Hashtable doSearch(TcmISDBModel db,String facility,String action,String days) throws Exception {
    Hashtable dataH = new Hashtable(4);
    Vector dataV = new Vector();
    String query = "select * from waste_tracking_view where facility_id = '" +facility+ "'";
    //refresh
    if ((action.equalsIgnoreCase("update")) || (action.equalsIgnoreCase("search"))){
      if (days.length() != 0){
        Integer number = new Integer(days);
        int num = number.intValue();
        query += " and days_since_transfer <= " + num;
      }else {
        query += " and transfer_date is null";
      }
    }else {
      query += " and transfer_date is null";
    }
    query += " order by transfer_date desc";

    DBResultSet dbrs = null;
    ResultSet rs = null;

    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while(rs.next()){
          Object[] oa = new Object[colHeads.length];
          String requestDate = BothHelpObjs.makeBlankFromNull(rs.getString("request_date"));
          oa[WT_WASTE_ITEM_ID_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("waste_item_id"));
          String transferred = BothHelpObjs.makeBlankFromNull(rs.getString("transferred"));
          oa[WT_TRANSFERRED_COL] = new Boolean(transferred.equalsIgnoreCase("y"));
          oa[WT_WASTE_FROM_LOCATION_ID_COL] = rs.getString("from_location_id");
          oa[WT_WORK_AREA_COL] = rs.getString("generation_point");
          oa[WT_REQUESTER_ID_COL] = rs.getString("requester");


          String requestedTransferD = BothHelpObjs.makeBlankFromNull(rs.getString("requested_transfer_date"));
          String tm = BothHelpObjs.makeBlankFromNull(rs.getString("requested_transfer_time"));
          if (tm.equalsIgnoreCase("A")) {
            tm = " AM";
          }else {
            tm = " PM";
          }
          String temp = BothHelpObjs.formatDate("toCharfromDB",requestedTransferD);
          if (!BothHelpObjs.isBlankString(temp)) {
            oa[WT_REQUESTED_TRANSFER_COL] = temp + tm;
          }else {
            oa[WT_REQUESTED_TRANSFER_COL] = "";
          }

          oa[WT_REQUEST_DATE_COL] = BothHelpObjs.formatDate("toCharfromDB",requestDate);

          oa[WT_WASTE_PROFILE_ID_COL] = rs.getString("profile_id");
          oa[WT_WASTE_PROFILE_DESC_COL] = rs.getString("profile_description");
          oa[WT_PACKAGING_COL] = rs.getString("packaging");
          oa[WT_DOT_DESC_COL] = rs.getString("dot_description");
          oa[WT_REPLACE_CONTAINER_COL] = rs.getString("replace_container");
          oa[WT_WASTE_TO_LOCATION_ID_COL] = rs.getString("to_location_id");
          String d =  BothHelpObjs.makeBlankFromNull(rs.getString("transfer_date"));
          if (d.length() == 0){
            Calendar cal = Calendar.getInstance();
            oa[WT_PICKUP_DATE_COL] = BothHelpObjs.formatDate("toCharfromNum",new String((cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DATE)+"/"+cal.get(Calendar.YEAR)));
          }else {
            oa[WT_PICKUP_DATE_COL] = BothHelpObjs.formatDate("toCharfromDB",rs.getString("transfer_date"));
          }
          String tmpNote = BothHelpObjs.makeBlankFromNull(rs.getString("note"));
          if (tmpNote.length() == 0) {
            oa[WT_NOTE_COL] = new String("");
          }else {
            oa[WT_NOTE_COL] = new String("  +");
          }
          oa[WT_REAL_NOTE_COL] = rs.getString("note");
          oa[WT_DAYS_SINCE_PICKUP_COL] = rs.getString("days_since_transfer");
          oa[WT_WASTE_REQUEST_ID_COL] = rs.getString("waste_request_id");
          oa[WT_LINE_ITEM_COL] = rs.getString("line_item");
          oa[WT_CONTAINER_ID_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("container_id"));

          if (BothHelpObjs.isBlankString(requestDate) || requestDate == null) {
            oa[WT_STATUS_COL] = "Draft";
          }else {
            if (!BothHelpObjs.isBlankString(d)) {
              oa[WT_STATUS_COL] = "Transferred";
            }else {
              oa[WT_STATUS_COL] = "Submitted";
            }
          }

          String warning = BothHelpObjs.makeBlankFromNull(rs.getString("transfer_trigger"));
          oa[WT_AGE_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("days_since_seal"));
          if (warning.equalsIgnoreCase("Y")) {
            oa[WT_AGE_WARNING_COL] = new Boolean(true);
          }else {
            oa[WT_AGE_WARNING_COL] = new Boolean(false);
          }
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


