package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.server7.share.db.*;
/*
SQLWKS> desc waste_request_line_item
Column Name                    Null?    Type
------------------------------ -------- ----
WASTE_REQUEST_ID               NOT NULL NUMBER(38)
LINE_ITEM                      NOT NULL NUMBER(38)
FACILITY_ID                             VARCHAR2(30)
WORK_AREA                               VARCHAR2(30)
WASTE_ITEM_ID                           NUMBER(38)
STATUS_ID                               VARCHAR2(8)
QUANTITY                                NUMBER
NOTE                                    VARCHAR2(2000)
REPLACE_CONTAINER                       NUMBER(38)
CHARGE_TYPE                             VARCHAR2(1)
*/

import java.util.*;
import java.sql.*;

public class WasteContainer {
   protected TcmISDBModel db;

   public static final int STRING = 0;
   public static final int DATE = 2;
   public static final int INT = 1;
   public static final int NULLVAL = 3;

   protected int containerId;
   /*
   protected String manifestId;
   protected String stateAbbrev;
   protected String countryAbbrev;
   */
   protected int wasteRequestId;
   protected int lineItem;
   protected int orderNo;
   protected String currentFacilityId;
   protected String currentLocationId;
   protected int manifestLineNumber;

   public static final String[] colHeads = {"Container Id"};
   public static final int[] colWidths = {80};
   public static final int[] colTypes = {BothHelpObjs.TABLE_COL_TYPE_NUMERIC_STRING};
   public static final int CONTAINER_ID_COL = 0;

   public WasteContainer(TcmISDBModel db){
      this.db = db;
   }
   public WasteContainer(){
   }
   public void setDb(TcmISDBModel db){
      this.db = db;
   }

   // get Methods
   public int getContainerId(){return containerId;}
   /*
   public String getManifestId(){return manifestId;}
   public String getStateAbbrev(){return stateAbbrev;}
   public String getCountryAbbrev(){return countryAbbrev;}
   */
   public int getWasteRequestId(){return wasteRequestId;}
   public int getLineItem(){return lineItem;}
   public int getOrderNo(){return orderNo;}
   public String getCurrentFacilityId(){return currentFacilityId;}
   public String getCurrentLocationId(){return currentLocationId;}
   public int getManifestLineNumber(){return manifestLineNumber;}

   // set Methods
   public void setContainerId(int s){containerId = s;}
   /*
   public void setManifestId(String s){manifestId = s;}
   public void setStateAbbrev(String s){stateAbbrev = s;}
   public void setCountryAbbrev(String s){countryAbbrev = s;}
   */
   public void setWasteRequestId(int s){wasteRequestId = s;}
   public void setLineItem(int s){lineItem = s;}
   public void setOrderNo(int s){orderNo = s;}
   public void setCurrentFacilityId(String s){currentFacilityId = s;}
   public void setCurrentLocationId(String s){currentLocationId = s;}
   public void setManifestLineNumber(int s){manifestLineNumber = s;}

   public void insert(String col,String val,int type)  throws Exception {
     Integer I;
     DBResultSet dbrs = null;
     ResultSet rs = null;

     String query = new String("update waste_container set " + col + "=");
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
     query += " where container_id = '"+getContainerId()+"'";
     //System.out.println("&&&&&&&&&&&&&&&& " + query);
     try {
       db.doUpdate(query);
     } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
        throw e;
     }finally {}
   }

  //4-23-01
  public void insertOrderShipment(String orderN,int wstReqId, int currentShipId)  throws Exception {
     Integer I;
     DBResultSet dbrs = null;
     ResultSet rs = null;
     String query = "update waste_container set order_no = "+orderN;
     query += " ,shipment_id = "+currentShipId;
     query += " where waste_request_id = '"+wstReqId+"'";
     query += " and line_item = 1";
     //System.out.println("&&&&&&&&&&&&&&&& " + query);
     try {
       db.doUpdate(query);
     } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
        throw e;
     }finally {}
   }
  //4-10-01
  public void insertOrderShipment(String orderN,int wstReqId)  throws Exception {
     Integer I;
     DBResultSet dbrs = null;
     ResultSet rs = null;
     String query = "update waste_container set (order_no,shipment_id) = (select order_no,shipment_id from waste_shipment";
     query += " where order_no = "+orderN+ " and lab_pack_drum_estimate is not null)";
     query += " where waste_request_id = '"+wstReqId+"'";
     query += " and line_item = 1";
     //System.out.println("&&&&&&&&&&&&&&&& " + query);
     try {
       db.doUpdate(query);
     } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
        throw e;
     }finally {}
   }
  //4-29-01
  public void updateCurrentLocationId(String orderN,int wstReqId)  throws Exception {
     Integer I;
     DBResultSet dbrs = null;
     ResultSet rs = null;
     String query = "update waste_container set current_location_id = (select storage_location_id from waste_order";
     query += " where order_no = "+orderN+")";
     query += " where waste_request_id = '"+wstReqId+"'";
     query += " and line_item = 1";
     //System.out.println("&&&&&&&&&&&&&&&& " + query);
     try {
       db.doUpdate(query);
     } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
        throw e;
     }finally {}
   }
   public void removeManifestLineLetter(String orderN)throws Exception{
     DBResultSet dbrs = null;
     ResultSet rs = null;
     String query = "update waste_container set manifest_line_letter = null";
     query += " where order_no = "+orderN;
     //System.out.println("&&&&&&&&&&&&&&&& " + query);
     try {
       db.doUpdate(query);
     } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
        throw e;
     }finally {}
   }

  public void delete() throws Exception {
    String query = "delete waste_container where container_id = '"+getContainerId()+"'";
    DBResultSet dbrs = null;
      try {
        db.doUpdate(query);
      }catch (Exception e) {
        e.printStackTrace();
        HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
        throw e;
      }finally {}
      return;
   }

   public void deleteAllContainerForReq() throws Exception {
    String query = "delete waste_container where waste_request_id = '"+getWasteRequestId()+"'";
    DBResultSet dbrs = null;
      try {
        db.doUpdate(query);
      }catch (Exception e) {
        e.printStackTrace();
        HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
        throw e;
      }finally {}
      return;
   }

   public void load()throws Exception{
     String query = "select * from waste_container where container_id = '"+getContainerId()+"'";

     DBResultSet dbrs = null;
     ResultSet rs = null;
     int myCount = 0;
     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()){
         /*
         setManifestId(BothHelpObjs.makeBlankFromNull(rs.getString("manifest_id")));
         setStateAbbrev(BothHelpObjs.makeBlankFromNull(rs.getString("state_abbrev")));
         setCountryAbbrev(BothHelpObjs.makeBlankFromNull(rs.getString("country_abbrev")));
         */
         setWasteRequestId(BothHelpObjs.makeZeroFromNull(rs.getString("waste_request_id")));
         setLineItem(BothHelpObjs.makeZeroFromNull(rs.getString("line_item")));
         setOrderNo(BothHelpObjs.makeZeroFromNull(rs.getString("order_no")));
         setCurrentFacilityId(BothHelpObjs.makeBlankFromNull(rs.getString("current_facility_id")));
         setCurrentLocationId(BothHelpObjs.makeBlankFromNull(rs.getString("current_location_id")));
         setManifestLineNumber(BothHelpObjs.makeZeroFromNull(rs.getString("manifest_line_letter")));
       }
     }catch (Exception e) { e.printStackTrace(); throw e;
     }finally{dbrs.close();}
   }

   public Vector getContainerIdTable(int reqId,int lineItem) throws Exception{
    Vector resultV = new Vector();
    String query = "select container_id from waste_container where waste_request_id = '"+reqId+"'";
    query += " and line_item = "+lineItem;
    query += " order by container_id";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()){
        resultV.addElement(new Integer(BothHelpObjs.makeZeroFromNull(rs.getString("container_id"))));
       }
     }catch (Exception e) { e.printStackTrace(); throw e;
     }finally{dbrs.close();}
     return resultV;
   }

   public String getSealDate(int reqId,int lineItem) throws Exception{
    String date = null;
    String query = "select seal_date from waste_container where waste_request_id = '"+reqId+"'";
    query += " and line_item = "+lineItem;
    query += " and rownum = 1";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()){
        date = BothHelpObjs.makeBlankFromNull(rs.getString("seal_date"));
        if (!BothHelpObjs.isBlankString(date)) {
          date = BothHelpObjs.formatDate("toCharfromDB",date);
          date = BothHelpObjs.formatDate("toNumfromChar",date);
        } else {
           Calendar cal = Calendar.getInstance();
           date = new String((cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DATE)+"/"+cal.get(Calendar.YEAR));
        }
       }
     }catch (Exception e) { e.printStackTrace(); throw e;
     }finally{dbrs.close();}
     return date;
   }

   public static void deleteContainer(TcmISDBModel db,int wrId,int lineItem, int count) throws Exception{
    String query = "delete from waste_container where container_id in (select container_id from waste_container where";
    query += " waste_request_id = "+wrId;
    query += " and line_item = "+lineItem;
    query += " and rownum <= "+count;
    query += ")";
    //DBResultSet dbrs = null;
    try {
      db.doUpdate(query);
    }catch (Exception e) {
      e.printStackTrace();
      throw e;
    }finally {}
    return;
   }

   public static void createWasteContainer(TcmISDBModel db,int wrId,int lineItem,String currentLoc, String currentFac, int wasteItemId)throws Exception{
     ResultSet rs = null;
     try{
       int containerId = DbHelpers.getNextVal(db,"waste_container_seq");
       String query = "insert into waste_container ";
       if (wasteItemId == 0) {
         query += "(container_id,waste_request_id,line_item,current_location_id,current_facility_id) "+
                  "values (" + containerId + "," + wrId + "," + lineItem + ",'" + currentLoc + "','" + currentFac + "')";
       }else {
         query += "(container_id,waste_request_id,line_item,current_location_id,current_facility_id,waste_item_id) "+
                  "values (" + containerId + "," + wrId + "," + lineItem + ",'" + currentLoc + "','" + currentFac + "'," + wasteItemId + ")";
       }
       db.doUpdate(query);
     }catch(Exception e){
       e.printStackTrace();
       throw e;
     }finally{}
   }

   public static int getWasteItemIdForWasteRequestLine(TcmISDBModel db,int wasteRequestId, int lineItem)throws Exception{
     int result = 0;
     String query = "select distinct waste_item_id from waste_container where waste_request_id = "+wasteRequestId+" and line_item = "+lineItem;

     DBResultSet dbrs = null;
     ResultSet rs = null;
     int myCount = 0;
     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()){
         result = BothHelpObjs.makeZeroFromNull(rs.getString("waste_item_id"));
       }
     }catch (Exception e) { e.printStackTrace(); throw e;
     }finally{dbrs.close();}
     return result;
   } //end of method

} //end of class


