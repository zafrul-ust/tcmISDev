
/*
SQLWKS> describe INVENTORY_STATUS
Column Name                    Null?    Type
------------------------------ -------- ----
BRANCH_PLANT                            VARCHAR2(12)
ITEM_ID                                 NUMBER
LOT_NUM                                 VARCHAR2(30)
LOT_STATUS                              VARCHAR2(13)
AVAILABLE                               NUMBER
ON_HOLD                                 NUMBER
ON_ORDER                                NUMBER
IN_PURCHASING                           NUMBER
EXPIRE_DATE                             DATE
PROMISED_DATE                           DATE
PO_NUMBER                               NUMBER
REV_PROM_DATE                           VARCHAR2(20)
RECEIVED_DATE                           DATE
READY_TO_SHIP_DATE                      DATE
NOTES                                   VARCHAR2(160)
*/
package radian.tcmis.server7.share.dbObjs;




import java.util.*;
import java.sql.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;

import oracle.jdbc.OracleTypes;

public class InventoryFloat {

   protected TcmISDBModel db;

   public InventoryFloat() throws Exception {
   }

   public InventoryFloat(TcmISDBModel db) throws Exception {
     this.db = db;
   }

   public void setDB(TcmISDBModel db){
     this.db = db;
   }

   public Hashtable getData (Hashtable data) throws Exception {
     Hashtable result = new Hashtable(3);
     int itemID = ((Integer) data.get("ITEM_ID")).intValue();

     ResultSet rs = null;
     Connection connect1 = null;
     CallableStatement cs = null;
     try {
      connect1 = db.getConnection();
      cs = connect1.prepareCall("{call PKG_INVENTORY_DETAIL.PR_INVENTORY_DETAIL(?,?,?,?)}");
      cs.setInt(1,itemID);
      cs.registerOutParameter(2, OracleTypes.CURSOR);
      cs.registerOutParameter(3, OracleTypes.CURSOR);
      cs.registerOutParameter(4, OracleTypes.CURSOR);
      cs.execute();
      getLeftTableData((ResultSet)cs.getObject(2),result);
      getRightTableData((ResultSet)cs.getObject(3),result);
      getPartNo((ResultSet)cs.getObject(4),result);
     }catch (Exception e){
      e.printStackTrace();
      HelpObjs.sendMail(db,"Error occured while trying to access 'Inventory Float Detail'","item_id: "+itemID,86030,false);
      throw e;
     }finally{
      cs.close();
     }
     return result;
   }

   public void getPartNo(ResultSet rs, Hashtable result) throws Exception {
    try {
      Vector parts = new Vector();
       while (rs.next()){
         parts.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("CAT_PART_NO")));
       }
       result.put("PART_NUM",parts);
       /*
       result.put("TRADE_NAME","will pass this from client");
       result.put("MFG","will pass this from client");
       result.put("PACK","will pass this from client");
       */
    }catch (Exception e){
      e.printStackTrace();
      throw e;
    }finally{
      rs.close();
    }
   }

   public void getLeftTableData(ResultSet rs, Hashtable result) throws Exception {
    try {
      Vector cols = new Vector();
       while (rs.next()){
        Vector row = new Vector(6);
        row.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("lot_status")));
        row.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("hub")));
        row.addElement(new Float(BothHelpObjs.makeBlankFromNull(rs.getString("quantity"))));
        row.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("mfg_lot")));
        //row.addElement(BothHelpObjs.formatDate("toCharfromDB",BothHelpObjs.makeBlankFromNull(rs.getString("expire_date"))));
        String tmpD = (BothHelpObjs.formatDate("toCharfromDB",BothHelpObjs.makeBlankFromNull(rs.getString("expire_date"))));
        if ("Jan 01, 3000".equalsIgnoreCase(tmpD)) {
          tmpD = "Indefinite";
        }
        row.addElement(tmpD);
        row.addElement(BothHelpObjs.formatDate("toCharfromDB",BothHelpObjs.makeBlankFromNull(rs.getString("ready_to_ship_date"))));
        cols.addElement(row);
       }
       result.put("TABLE_DATA1",cols);
    }catch (Exception e){
      e.printStackTrace();
      throw e;
    }finally{
      rs.close();
    }
   }

   public void getRightTableData(ResultSet rs, Hashtable result) throws Exception {
    try {
      Vector cols = new Vector();
       while (rs.next()){
        Vector row = new Vector(6);
        row.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("status")));
        row.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("hub")));
        row.addElement(new Integer(BothHelpObjs.makeBlankFromNull(rs.getString("quantity"))));
        row.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("radian_po")));
        row.addElement(BothHelpObjs.formatDate("toCharfromDB",BothHelpObjs.makeBlankFromNull(rs.getString("ready_to_ship_date"))));
        String notes = BothHelpObjs.makeBlankFromNull(rs.getString("notes"));
        row.addElement(notes.length()>0?"+":" ");
        row.addElement(notes);
        cols.addElement(row);
       }
       result.put("TABLE_DATA2",cols);
    }catch (Exception e){
      e.printStackTrace();
      throw e;
    }finally{
      rs.close();
    }
   }
}

/*
   public Hashtable getData (Hashtable data) throws Exception {
     int itemid = ((Integer) data.get("ITEM_ID")).intValue();
     String hub = BothHelpObjs.makeBlankFromNull((String) data.get("HUB")) ;
     String fac = BothHelpObjs.makeBlankFromNull((String) data.get("FAC")) ;
     if (hub.length()==0){
        // get Hub
        DBResultSet dbrs = null;
      ResultSet rs = null;
        String query = "select preferred_warehouse from facility where facility_id ='"+fac+"'";
        try{
          dbrs = db.doQuery(query);
          rs=dbrs.getResultSet();
          int i=0;
          while (rs.next()){
             hub = BothHelpObjs.makeBlankFromNull(rs.getString("preferred_warehouse"));
             break;
          }
        } catch (Exception e){
          e.printStackTrace();
          HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
          dbrs.close();

          throw e;
       } finally{
             dbrs.close();

        }
     }
     Hashtable result = new Hashtable();

     String query = "select LOT_STATUS,ON_ORDER,IN_PURCHASING,facility.BRANCH_PLANT,PO_NUMBER,READY_TO_SHIP_DATE,"+
                    " READY_TO_SHIP_DATE,NOTES,ON_HOLD,AVAILABLE,LOT_NUM,EXPIRE_DATE, preferred_warehouse "+
                    " from INVENTORY_STATUS, facility where item_id = "+itemid +
                    " and INVENTORY_STATUS.BRANCH_PLANT = facility.BRANCH_PLANT";
     if (!"All".equalsIgnoreCase(hub)) {
      query += " and facility.preferred_warehouse = '"+hub+"'";
     }

     Vector cols1 = new Vector();
     Vector cols2 = new Vector();
     DBResultSet dbrs = null;
      ResultSet rs = null;
     try{
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       int i=0;
       while (rs.next()){
         Vector row1 = new Vector();
         Vector row2 = new Vector();
         String status = BothHelpObjs.makeBlankFromNull(rs.getString("LOT_STATUS"));
         //row2 right table
         if (status.trim().equalsIgnoreCase("OnOrder") ||
             status.trim().equalsIgnoreCase("InPurchasing")){ // right table
             int oo = new Integer(BothHelpObjs.makeZeroFromNull(rs.getString("ON_ORDER"))).intValue();
             int ip = new Integer(BothHelpObjs.makeZeroFromNull(rs.getString("IN_PURCHASING"))).intValue();
             row2.addElement(status);
             row2.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("preferred_warehouse")));
             row2.addElement(new Integer(oo+ip));
             row2.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("PO_NUMBER")));
             row2.addElement(BothHelpObjs.formatDate("toCharfromDB",BothHelpObjs.makeBlankFromNull(rs.getString("READY_TO_SHIP_DATE"))));
             String notes = BothHelpObjs.makeBlankFromNull(rs.getString("NOTES"));
             row2.addElement(notes.length()>0?"+":" ");
             row2.addElement(notes);
             cols2.addElement(row2);
         //left table
         } else {
             int a = new Integer(BothHelpObjs.makeZeroFromNull(rs.getString("AVAILABLE"))).intValue();
             int oo = new Integer(BothHelpObjs.makeZeroFromNull(rs.getString("ON_HOLD"))).intValue();
             row1.addElement(status);
             row1.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("preferred_warehouse")));
             row1.addElement(new Integer(oo+a));
             row1.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("LOT_NUM")));
             row1.addElement(BothHelpObjs.formatDate("toCharfromDB",BothHelpObjs.makeBlankFromNull(rs.getString("EXPIRE_DATE"))));
             row1.addElement(BothHelpObjs.formatDate("toCharfromDB",BothHelpObjs.makeBlankFromNull(rs.getString("READY_TO_SHIP_DATE"))));
             cols1.addElement(row1);
         }
       }

     } catch (Exception e){
       e.printStackTrace();
       HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);

       throw e;
       } finally{
             dbrs.close();

     }
     result.put("TABLE_DATA1",cols1);
     result.put("TABLE_DATA2",cols2);

     //header info which I have (pass in from parent screen)
     query = "select distinct x.ITEM_ID,x.MFG_DESC,x.PART_SIZE,x.SIZE_UNIT,x.PKG_STYLE ,x.MATERIAL_DESC trade_name,y.cat_part_no";
     query += " from catalog_item_view x,catalog_part_item_group y,catalog_facility z,facility f";
     query += " where x.item_id=y.ITEM_ID and y.status in ('A','D') and y.catalog_id=z.catalog_id and f.facility_id=z.facility_id";
     query += " and x.item_id = "+itemid;
     if (!"All".equalsIgnoreCase(hub)) {
      query += " and f.preferred_warehouse='"+hub+"'";
     }
     try{
       rs = null;
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       int i=0;
       Vector parts = new Vector();
       while (rs.next()){
         String part = BothHelpObjs.makeBlankFromNull(rs.getString("CAT_PART_NO"));
         if (!parts.contains(part)) parts.addElement(part);
         result.put("TRADE_NAME",BothHelpObjs.makeBlankFromNull(rs.getString("TRADE_NAME")));
         result.put("MFG",BothHelpObjs.makeBlankFromNull(rs.getString("MFG_DESC")));
         String pack = BothHelpObjs.makeBlankFromNull(rs.getString("PART_SIZE")) + " " +
                       BothHelpObjs.makeBlankFromNull(rs.getString("SIZE_UNIT")) + " " +
                       BothHelpObjs.makeBlankFromNull(rs.getString("PKG_STYLE"));
         result.put("PACK",pack);
       }
       result.put("PART_NUM",parts);
     } catch (Exception e){
       e.printStackTrace();
       HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
       throw e;
     } finally{
      dbrs.close();
     }
     return result;
   }

*/





























