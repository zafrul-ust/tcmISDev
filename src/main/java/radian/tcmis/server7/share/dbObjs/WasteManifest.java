package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.server7.share.db.*;

import java.util.*;
import java.sql.*;

public class WasteManifest {
   protected TcmISDBModel db;

   public static final int STRING = 0;
   public static final int DATE = 2;
   public static final int INT = 1;
   public static final int NULLVAL = 3;

   protected String manifestId;
   protected String stateAbbrev;
   protected String countryAbbrev;
   protected int shipmentId;
   protected String stateManifestDocNo;
   protected String returnDate;

   public WasteManifest(TcmISDBModel db){
      this.db = db;
   }
   public WasteManifest(){
   }
   public void setDb(TcmISDBModel db){
      this.db = db;
   }

   // get Methods
   public String getManifestId(){return manifestId;}
   public String getStateAbbrev(){return stateAbbrev;}
   public String getCountryAbbrev(){return countryAbbrev;}
   public int getShipmentId(){return shipmentId;}
   public String getStateManifestDocNo(){return stateManifestDocNo;}
   public String getReturnDate(){return returnDate;}

   // set Methods
   public void setManifestId(String s){manifestId = s;}
   public void setStateAbbrev(String s){stateAbbrev = s;}
   public void setCountryAbbrev(String s){countryAbbrev = s;}
   public void setShipmentId(int s){shipmentId = s;}
   public void setStateManifestDocNo(String s){stateManifestDocNo = s;}
   public void setReturnDate(String s){returnDate = s;}

   public static void setManifestLine(TcmISDBModel db, int orderNo) throws Exception{
    String[] manLineLetter = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","x","y","z"};
    String query = "select * from waste_man_detail_view where order_no ="+orderNo;
    query += " order by order_no,shipment_id,vendor_profile_id,management_option_location";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    Vector bulkManLine = new Vector();
    Vector containerManLine = new Vector();
    int bulkNextLetter = 0;
    String lastContainerId = "";
    String lastShipmentId = "";
    String lastDOT = "";
    int containerNextLetter = 0;
    boolean firstPass = true;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while(rs.next()){
        String isBulk = BothHelpObjs.makeBlankFromNull(rs.getString("isbulk"));
        String currentShipmentId = BothHelpObjs.makeBlankFromNull(rs.getString("shipment_id"));
        String currentContainerId = BothHelpObjs.makeBlankFromNull(rs.getString("container_id"));
        // 6/08 for now use this but later use DOT
        String currentDOT = BothHelpObjs.makeBlankFromNull(rs.getString("dot"));
        if (isBulk.equalsIgnoreCase("Y")) {
          if (!currentDOT.equals(lastDOT) && !currentShipmentId.equals(lastShipmentId)) {
            bulkNextLetter = 0;
          }
          String letter = manLineLetter[bulkNextLetter];
          Hashtable innerH = new Hashtable();
          innerH.put("FACILITY_ID",BothHelpObjs.makeBlankFromNull(rs.getString("facility_id")));
          innerH.put("ORDER_NO",BothHelpObjs.makeBlankFromNull(rs.getString("order_no")));
          innerH.put("SHIPMENT_ID",currentShipmentId);
          innerH.put("CONTAINER_ID",currentContainerId);
          innerH.put("MAN_LINE_LETTER",letter);
          bulkManLine.addElement(innerH);
          bulkNextLetter++;
        } else {
          if (firstPass) {
            firstPass = false;
          }else {
            if (!currentDOT.equals(lastDOT)) {
              containerNextLetter++;
            }
          }

          String letter = manLineLetter[containerNextLetter];
          Hashtable innerH = new Hashtable();
          innerH.put("FACILITY_ID",BothHelpObjs.makeBlankFromNull(rs.getString("facility_id")));
          innerH.put("ORDER_NO",BothHelpObjs.makeBlankFromNull(rs.getString("order_no")));
          innerH.put("SHIPMENT_ID",currentShipmentId);
          innerH.put("CONTAINER_ID",currentContainerId);
          innerH.put("MAN_LINE_LETTER",letter);
          containerManLine.addElement(innerH);
        }
        lastContainerId = currentContainerId;
        lastShipmentId = currentShipmentId;
        lastDOT = currentDOT;
      }
    }catch(Exception ex){ex.printStackTrace(); throw ex;
    }finally{dbrs.close();}
    setBulkManLineLetter(db,bulkManLine);
    setContainerManLineLetter(db,containerManLine);
  }

   public static void setLabPackManifestLine(TcmISDBModel db, int orderNo) throws Exception{
    String[] manLineLetter = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","x","y","z"};
    String query = "select * from waste_man_detail_view where order_no ="+orderNo;
    query += " and lower(isbulk) != 'y' order by order_no,shipment_id,vendor_profile_id,management_option_location";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    Vector bulkManLine = new Vector();
    Vector containerManLine = new Vector();
    int bulkNextLetter = 0;
    String lastContainerId = "";
    String lastShipmentId = "";
    String lastDOT = "";
    int containerNextLetter = 0;
    boolean firstPass = true;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while(rs.next()){
        String isBulk = BothHelpObjs.makeBlankFromNull(rs.getString("isbulk"));
        String currentShipmentId = BothHelpObjs.makeBlankFromNull(rs.getString("shipment_id"));
        String currentContainerId = BothHelpObjs.makeBlankFromNull(rs.getString("container_id"));
        // 6/08 for now use this but later use DOT
        String currentDOT = BothHelpObjs.makeBlankFromNull(rs.getString("dot"));
        if (isBulk.equalsIgnoreCase("Y")) {
          if (!currentDOT.equals(lastDOT) && !currentShipmentId.equals(lastShipmentId)) {
            bulkNextLetter = 0;
          }
          String letter = manLineLetter[bulkNextLetter];
          Hashtable innerH = new Hashtable();
          innerH.put("FACILITY_ID",BothHelpObjs.makeBlankFromNull(rs.getString("facility_id")));
          innerH.put("ORDER_NO",BothHelpObjs.makeBlankFromNull(rs.getString("order_no")));
          innerH.put("SHIPMENT_ID",currentShipmentId);
          innerH.put("CONTAINER_ID",currentContainerId);
          innerH.put("MAN_LINE_LETTER",letter);
          bulkManLine.addElement(innerH);
          bulkNextLetter++;
        } else {
          if (firstPass) {
            firstPass = false;
          }else {
            if (!currentDOT.equals(lastDOT)) {
              containerNextLetter++;
            }
          }

          String letter = manLineLetter[containerNextLetter];
          Hashtable innerH = new Hashtable();
          innerH.put("FACILITY_ID",BothHelpObjs.makeBlankFromNull(rs.getString("facility_id")));
          innerH.put("ORDER_NO",BothHelpObjs.makeBlankFromNull(rs.getString("order_no")));
          innerH.put("SHIPMENT_ID",currentShipmentId);
          innerH.put("CONTAINER_ID",currentContainerId);
          innerH.put("MAN_LINE_LETTER",letter);
          containerManLine.addElement(innerH);
        }
        lastContainerId = currentContainerId;
        lastShipmentId = currentShipmentId;
        lastDOT = currentDOT;
      }
    }catch(Exception ex){ex.printStackTrace(); throw ex;
    }finally{dbrs.close();}
    //System.out.print("\n\n--- here it 99999999999999");
    setContainerManLineLetter(db,containerManLine);
  }

  public static void setContainerManLineLetter(TcmISDBModel db,Vector manLineLetter) throws Exception {
    String lastLetter = "";
    for (int i = 0; i < manLineLetter.size(); i++) {
      Hashtable innerH = (Hashtable)manLineLetter.elementAt(i);
      String shipmentId = (String)innerH.get("SHIPMENT_ID");
      Integer shipId = new Integer(shipmentId);
      String containerId = (String)innerH.get("CONTAINER_ID");
      Integer cId = new Integer(containerId);
      String letter = (String)innerH.get("MAN_LINE_LETTER");
      //creating manifest_line_item
      try {
        if (!lastLetter.equals(letter)){
          String query = "insert into manifest_line_item (shipment_id,manifest_line_letter)";
          query += " values ("+shipId.intValue()+",'"+letter+"')";
          db.doUpdate(query);
        }
        //now setting that line letter in waste container
        WasteContainer wc = new WasteContainer(db);
        wc.setContainerId(cId.intValue());
        wc.insert("manifest_line_letter",letter,WasteContainer.STRING);
        lastLetter = letter;
      }catch(Exception ex){ex.printStackTrace(); throw ex;
      }finally{}
    }
  }

  public static void setBulkManLineLetter(TcmISDBModel db,Vector manLineLetter) throws Exception {
    for (int i = 0; i < manLineLetter.size(); i++) {
      Hashtable innerH = (Hashtable)manLineLetter.elementAt(i);
      String facilityId = (String)innerH.get("FACILITY_ID");
      String orderNo = (String)innerH.get("ORDER_NO");
      Integer oId = new Integer(orderNo);
      String shipmentId = (String)innerH.get("SHIPMENT_ID");
      Integer shipId = new Integer(shipmentId);
      String bulkId = (String)innerH.get("CONTAINER_ID");
      String letter = (String)innerH.get("MAN_LINE_LETTER");
      //creating manifest_line_item
      String query = "insert into manifest_line_item (shipment_id,manifest_line_letter)";
      query += " values ("+shipId.intValue()+",'"+letter+"')";
      try {
        db.doUpdate(query);
        //now setting that line letter in bulk order
        WasteBulkOrder wbo = new WasteBulkOrder(db);
        wbo.setBulkId(bulkId);
        wbo.setFaciliyId(facilityId);
        wbo.setShipmentId(shipId.intValue());
        wbo.setOrderNo(oId.intValue());
        wbo.insert("manifest_line_letter",letter,WasteBulkOrder.STRING);
      }catch(Exception ex){ex.printStackTrace(); throw ex;
      }finally{}
    }
  }

  public void load()throws Exception{
     String query = "select * from manifest where manifest_id = '"+getManifestId()+"'";
     query += " and state_abbrev = '"+getStateAbbrev()+"'";
     query += " and country_abbrev = '"+getCountryAbbrev()+"'";
     DBResultSet dbrs = null;
     ResultSet rs = null;
     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()){
         setShipmentId(BothHelpObjs.makeZeroFromNull(rs.getString("shipment_id")));
         setStateManifestDocNo(BothHelpObjs.makeBlankFromNull(rs.getString("state_manifest_doc_no")));
         setReturnDate(BothHelpObjs.makeBlankFromNull(rs.getString("return_date")));
       }
     }catch (Exception e) { e.printStackTrace(); throw e;
     }finally{dbrs.close();}
   }

   public static void insertWasteManifest(TcmISDBModel db,String manifestId, int shipmentId,String state, String country)throws Exception{
     try{
       String query = "insert into manifest (manifest_id,shipment_id,state_abbrev,country_abbrev)";
       query += " values ('"+manifestId+"',"+shipmentId+",'"+state+"','"+country+"')";
       db.doUpdate(query);
     }catch(Exception e){
       e.printStackTrace();
       throw e;
     }finally{}
   }

   //need to go back and fix if we want to use this method
   public void delete() throws Exception{
    String query = "delete from manifest where manifest_id = '"+getManifestId()+"'";
    DBResultSet dbrs = null;
    try {
      db.doUpdate(query);
    }catch (Exception e) {
      e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);  throw e;
    } finally{}
   }

   //4-10-01
    public static void deleteManifestShipment(TcmISDBModel db,int orderN) throws Exception{
    String query = "delete from manifest_line_item where shipment_id in (select a.shipment_id from waste_shipment a, waste_container b where a.shipment_id = b.shipment_id and a.order_no = "+orderN+")";
    DBResultSet dbrs = null;
    try {
      ////System.out.print("\n\n-------- my delete: "+query);
      db.doUpdate(query);
    }catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally{}
   }


  public void insert(String col,String val,int type)  throws Exception {
     Integer I;
     DBResultSet dbrs = null;
     ResultSet rs = null;

     String query = new String("update manifest set " + col + "=");
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
     query += " where manifest_id = '"+getManifestId()+"'";
     query += " and state_abbrev = '"+getStateAbbrev()+"'";
     query += " and country_abbrev = '"+getCountryAbbrev()+"'";
     try {
       db.doUpdate(query);
     } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
        throw e;
     }finally{}
   }


   public String getNowDate()  throws Exception {
     String query = "select to_char(sysdate,'MM/dd/yyyy') from dual";
     DBResultSet dbrs = null;
     ResultSet rs = null;
     String next = new String("");
     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()) {
         next = rs.getString(1);
         break;
       }
      } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);  throw e;
       } finally{
        dbrs.close();
     }
     return next;
   }
}