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

public class WasteRequestLineItem {
   protected TcmISDBModel db;

   public static final int STRING = 0;
   public static final int DATE = 2;
   public static final int INT = 1;
   public static final int NULLVAL = 3;

   protected int wasteRequestId;
   protected int lineItem;
   protected String workArea;
   protected int wasteItemId;
   protected int status;
   protected int qty;
   protected String notes;
   protected int replaceContainers;
   protected String chargeType;
   protected int travelerId;
   protected String sealDate;
   protected String requestedTransferDate;
   protected String requestedTransferTimeAm;
   protected String poNumber;
   protected String releaseNumber;


   public WasteRequestLineItem(TcmISDBModel db){
      this.db = db;
   }
   public WasteRequestLineItem(){
   }
   public void setDb(TcmISDBModel db){
      this.db = db;
   }

   // get Methods
   public int getWasteRequestId(){return wasteRequestId;}
   public int getLineItem(){return lineItem;}
   public String getWorkArea(){return workArea;}
   public int getWasteItemId(){return wasteItemId;}
   public int getStatus(){return status;}
   public int getQty(){return qty;}
   public String getNotes(){return notes;}
   public int getReplaceContainers(){return replaceContainers;}
   public String getChargeType(){return chargeType;}
   public int getTravelerId(){return travelerId;}
   public String getSealDate(){return sealDate;}
   public String getRequestedTransferDate(){return requestedTransferDate;}
   public String getRequestedTransferTimeAm(){return requestedTransferTimeAm;}
   public String getPoNumber(){return poNumber;}
   public String getReleaseNumber(){return releaseNumber;}


   // set Methods
   public void setWasteRequestId(int s){wasteRequestId = s;}
   public void setLineItem(int s){lineItem = s;}
   public void setWorkArea(String s){workArea = s;}
   public void setWasteItemId(int s){wasteItemId = s;}
   public void setStatus(int s){status = s;}
   public void setQty(int s){qty = s;}
   public void setNotes(String s){notes = s;}
   public void setReplaceContainers(int s){replaceContainers = s;}
   public void setChargeType(String s){chargeType = s;}
   public void setTravelerId(int s){travelerId = s;}
   public void setSealDate(String s){sealDate = s;}
   public void setRequestedTransferDate(String s){requestedTransferDate = s;}
   public void setRequestedTransferTimeAm(String s){requestedTransferTimeAm = s;}
   public void setPoNumber(String s){poNumber = s;}
   public void setReleaseNumber(String s){releaseNumber = s;}

   //trong 8-16
   public Vector getTravelerIds(int wasteRequestId) throws Exception {
    String query = "select traveler_id from waste_request_line_item where waste_request_id = "+wasteRequestId;
    Vector result = new Vector();
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()){
        result.addElement(rs.getString("traveler_id"));
       }
    } catch (Exception e) {
      e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
      throw e;
    }

    return result;
   }



   public void insert(String col,String val,int type)  throws Exception {
     if(col.equalsIgnoreCase("NOTES")) {
       val = HelpObjs.singleQuoteToDouble(val);
     }

     Integer I;
     DBResultSet dbrs = null;
     ResultSet rs = null;

     String query = new String("update waste_request_line_item set " + col + "=");
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
     query += " where waste_request_id = " + getWasteRequestId()+ " and line_item = " + getLineItem();
     //System.out.println("&&&&&&&&&&&&&&&& wrli " + query);
     try {
       db.doUpdate(query);
     } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
        throw e;
       }
   }

   public void deleteLineItem(Vector delInfo) throws Exception {
    String query;
    //Integer lineItem = new Integer(delLine);

    // creating a string to call a 'store procedure' -- pr_del_wr_line_item(int,int,string)
    query = "{call pr_waste_line_item_delete(";
    query += getWasteRequestId();
    query += ",'";
   // query += "," +delInfo.size() + ",'";

    Integer num = new Integer(0);
    int delLine;
    for(int i = 0; i < delInfo.size(); i++) {
      num = (Integer)delInfo.elementAt(i);
      delLine = num.intValue();
      query += delLine;
      query += ",";
    }
    //removing the extra commas (,)
    query = query.substring(0,query.length() - 1);
    query += "')}";

    DBResultSet dbrs = null;
    try {
      //System.out.println("---------- deleting line number :  " + query);
      db.doUpdate(query);
    }catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
      throw e;
    }
   }

   public void deleteLineItem() throws Exception {
    String query;
    //Integer lineItem = new Integer(delLine);

    // creating a string to call a 'store procedure' -- pr_del_wr_line_item(int,int,string)
    query = "{call pr_waste_line_item_delete(";
    query += getWasteRequestId();
    query += ",";
    query += getLineItem();
    query += ")}";

    DBResultSet dbrs = null;
    try {
      //System.out.println("\n\n\n---------- deleting line number :  " + query);
      db.doUpdate(query);
    }catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
      throw e;
    }
   }

  public void delete() throws Exception {
    String query = "delete waste_request_line_item where waste_request_id = " + getWasteRequestId();
    DBResultSet dbrs = null;
      try {
        db.doUpdate(query);
      }catch (Exception e) {
        e.printStackTrace();
        HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
        throw e;
      } finally {}
      return;
   }

  public Hashtable getCurrentInfo()throws Exception{
     Hashtable currentInfo = new Hashtable();
     String query = "select quantity,generation_point from waste_request_line_item where waste_request_id = "+getWasteRequestId()+ " and line_item = " +getLineItem();
     DBResultSet dbrs = null;
     ResultSet rs = null;
     int myCount = 0;
     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()){
        currentInfo.put("CURRENT_QTY",BothHelpObjs.makeBlankFromNull(rs.getString("quantity")));
        currentInfo.put("GENERATION_POINT",BothHelpObjs.makeBlankFromNull(rs.getString("generation_point")));
       }
     }catch (Exception e) { e.printStackTrace(); throw e;
     }finally{dbrs.close();}
     return currentInfo;
   }

   public void load()throws Exception{
     String query;
     if (getLineItem() == 0) {
        query = "select * from waste_request_line_item where waste_request_id = "+getWasteRequestId();
     } else {
        query = "select * from waste_request_line_item where waste_request_id = "+getWasteRequestId()+ " and line_item = " +getLineItem();
     }
     DBResultSet dbrs = null;
     ResultSet rs = null;
     int myCount = 0;
     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()){
         setWorkArea((BothHelpObjs.makeBlankFromNull(rs.getString("generation_point"))));
         setQty(BothHelpObjs.makeZeroFromNull(rs.getString("quantity")));
         setNotes(BothHelpObjs.makeBlankFromNull(rs.getString("note")));
         setReplaceContainers((BothHelpObjs.makeZeroFromNull(rs.getString("replace_container"))));
         setChargeType(BothHelpObjs.makeBlankFromNull(rs.getString("charge_type")));
         setTravelerId(BothHelpObjs.makeZeroFromNull(rs.getString("traveler_id")));

         String date = BothHelpObjs.makeBlankFromNull(rs.getString("seal_date"));
         if (!BothHelpObjs.isBlankString(date)) {
          date = BothHelpObjs.formatDate("toCharfromDB",date);
          date = BothHelpObjs.formatDate("toNumfromChar",date);
         } else {
           Calendar cal = Calendar.getInstance();
           date = new String((cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DATE)+"/"+cal.get(Calendar.YEAR));
         }
         setSealDate(date);

         //trong 9-21 NEED data
         date = BothHelpObjs.makeBlankFromNull(rs.getString("requested_transfer_date"));
         if (!BothHelpObjs.isBlankString(date)) {
          date = BothHelpObjs.formatDate("toCharfromDB",date);
          date = BothHelpObjs.formatDate("toNumfromChar",date);
         } else {
           Calendar cal = Calendar.getInstance();
           date = new String((cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DATE)+"/"+cal.get(Calendar.YEAR));
         }
         setRequestedTransferDate(date);
         setRequestedTransferTimeAm(BothHelpObjs.makeBlankFromNull(rs.getString("requested_transfer_time")));
         setPoNumber(BothHelpObjs.makeBlankFromNull(rs.getString("po_number")));
         setReleaseNumber(BothHelpObjs.makeBlankFromNull(rs.getString("release_number")));
       }
     }catch (Exception e) { e.printStackTrace(); throw e;
     }finally{dbrs.close();}
   }

   public static void createWasteRequestLineItem(TcmISDBModel db,int wrId,int lineItem,String workArea,int qty,int wasteItem,String facilityId)throws Exception{
     ResultSet rs = null;
     try{
       String query = "insert into waste_request_line_item ";
       query = query + "(waste_request_id,line_item,quantity,generation_point) ";
       query = query + "values ("+wrId+","+lineItem+","+qty+",'"+workArea+"')";
       db.doUpdate(query);
       for (int i = 0; i < qty; i++) {
        WasteContainer.createWasteContainer(db,wrId,lineItem,workArea,facilityId,wasteItem);
       }
     }catch(Exception e){
       e.printStackTrace();
       throw e;
     }finally{
     }
   }

   public static Vector getLineItems(TcmISDBModel db,int reqNum) throws Exception{
     Vector v = new Vector();
     DBResultSet dbrs = null;
     ResultSet rs = null;
     try{
       String query = "select waste_request_id, line_item from waste_request_line_item where waste_request_id = "+reqNum+" order by line_item";
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()){
         WasteRequestLineItem li = new WasteRequestLineItem(db);
         li.setWasteRequestId(reqNum);
         li.setLineItem(rs.getInt("line_item"));
         li.load();
         v.addElement(li);
       }
     }catch(Exception e){
       e.printStackTrace();
       throw e;
     }finally{dbrs.close();}
     return v;
   }
}

         /*

      //update the notes   1221
      int num = getLineItem();
      num -= 1;
      Hashtable myt = (Hashtable)lineItems.elementAt(0);
System.out.println("====================== " + num + " ********* " + myt.get("NOTES") + " --- " + myt.get("QTY"));

      query = "update waste_request_line_item set note = '"+myt.get("NOTES")+"'";
      query += " where waste_request_id = " + getWasteRequestId()+ " and line_item = " + num;
      try {
        db.doUpdate(query);
      }catch (Exception e) {
        e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);   throw e;
      }*/
