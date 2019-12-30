
package radian.tcmis.server7.share.dbObjs;


 

import java.sql.*;
import java.util.*;
import radian.tcmis.server7.share.db.*;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;

public class Inventory  {

   protected TcmISDBModel db;
   protected Integer item_id;
   protected String facility_id;
   protected String fac_part_no;
   protected String trade_name;
   protected float part_size;
   protected String size_unit;
   protected String pkg_style;
   protected String mfg_desc;
   protected String location;
   protected String lot_num;
   protected Integer on_hand;
   protected Integer on_order;
   protected String expire_date;
   protected String warehouse;
   protected String promise_date;
   protected Integer po_number;
   protected String rev_prom_date;

   public Inventory() throws java.rmi.RemoteException{
       
   }

   public Inventory(TcmISDBModel db) throws java.rmi.RemoteException{
     this.db = db;
    // System.out.println("Const");
   }

   public void setDB(TcmISDBModel db){
     this.db = db;
   }

   public void setItemId(int id) {
     this.item_id = new Integer(id);
   }

   public Integer getItemId() {
     return item_id;
   }

   public void setFacilityId(String d) {
     this.facility_id = d;
   }

   public String getFacilityId() {
     return facility_id;
   }

   public void setFacPartNo(String d) {
     this.fac_part_no = d;
   }

   public String getFacPartNo() {
     return fac_part_no;
   }

   public void setTradeName(String d) {
     this.trade_name = d;
   }

   public String getTradeName() {
     return trade_name;
   }

   public void setPartSize(float f) {
     this.part_size = f;
   }

   public float getPartSize() {
     return part_size;
   }

   public void setSizeUnit(String d) {
     this.size_unit = d;
   }

   public String getSizeUnit() {
     return size_unit;
   }

   public void setPkgStyle(String d) {
     this.pkg_style = d;
   }

   public String getPkgStyle() {
     return pkg_style;
   }

   public void setMfgDesc(String d) {
     this.mfg_desc = d;
   }

   public String getMfgDesc() {
     return mfg_desc;
   }

   public void setLocation(String d) {
     this.location = d;
   }

   public String getLocation() {
     return location;
   }

   public void setLotNum(String d) {
     this.lot_num = d;
   }

   public String getLotNum() {
     return lot_num;
   }

   public void setOnHand(int id) {
     this.on_hand = new Integer(id);
   }

   public Integer getOnHand() {
     return on_hand;
   }

   public void setOnOrder(int id) {
     this.on_order = new Integer(id);
   }

   public Integer getOnOrder() {
     return on_order;
   }

   public void setExpireDate(String d) {
     this.expire_date = d;
   }

   public String getExpireDate() {
     return expire_date;
   }

   public void setWarehouse(String d) {
     this.warehouse = d;
   }

   public String getWarehouse() {
     return warehouse;
   }

   public void setPromiseDate(String d) {
     this.promise_date = d;
   }

   public String getPromiseDate() {
     return promise_date;
   }
   public void setRevPromDate(String d) {
     this.rev_prom_date = d;
   }

   public String getRevPromDate() {
     return rev_prom_date;
   }

   public void setPONumber(int d) {
     this.po_number = new Integer(d);
   }

   public Integer getPONumber() {
     return po_number;
   }

   public void load()  throws Exception {
      String query = "select * from inventory_view_snapshot where item_id = " + item_id.toString() + " and facility_id = '" + facility_id + "'";

      DBResultSet dbrs = null;
      ResultSet rs = null;
      try {
         dbrs = db.doQuery(query);
         rs=dbrs.getResultSet();
         while (rs.next()){
           setItemId((int)rs.getInt("item_id"));
           setFacilityId(rs.getString("facility_id"));
           setFacPartNo(rs.getString("fac_part_no"));
           setTradeName(rs.getString("trade_name"));
           setPartSize((float)rs.getFloat("part_size"));
           setSizeUnit(rs.getString("size_unit"));
           setPkgStyle(rs.getString("pkg_style"));
           setMfgDesc(rs.getString("mfg_desc"));
           setLocation(rs.getString("location"));
           String lot = rs.getString("lot_num");
           if (lot != null) {
             if (lot.indexOf(" ") > 0) {
               setLotNum(lot.substring(0,lot.indexOf(" ",2)));
             } else {
               setLotNum(lot);
             }
           } else {
             setLotNum(lot);
           }
           setOnHand((int)rs.getInt("on_hand"));
           setOnOrder((int)rs.getInt("on_order"));
           setExpireDate(rs.getString("expire_date"));
           String ware = rs.getString("warehouse");
           if(ware.indexOf(" ",2) < 0){
             setWarehouse(ware);
           }else{
             setWarehouse(ware.substring(0,ware.indexOf(" ",2)));
           }
           setPromiseDate(rs.getString("promised_date"));
           setPONumber((int)rs.getInt("po_number"));
           setRevPromDate(rs.getString("rev_prom_date"));
         }
         
      
      } catch (Exception e) { e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);   throw e;
       } finally{
             dbrs.close();
            }
   }
   public void load2()  throws Exception {
      String query = "select a.* from inventory_view_snapshot a, facility b where a.item_id = " + item_id.toString() + " and a.facility_id = '" + facility_id + "' and b.facility_id = a.facility_id and a.warehouse = b.preferred_warehouse";
      //query += " and lot_status <> 'Expired'";
      query += " and a.lot_status not in ('Expired','Held','NonConforming')";   //6-26-01

      DBResultSet dbrs = null;
      ResultSet rs = null;
      try {
         dbrs = db.doQuery(query);
         rs=dbrs.getResultSet();
         while (rs.next()){
           setItemId((int)rs.getInt("item_id"));
           setFacilityId(rs.getString("facility_id"));
           setFacPartNo(rs.getString("fac_part_no"));
           setTradeName(rs.getString("trade_name"));
           setPartSize((float)rs.getFloat("part_size"));
           setSizeUnit(rs.getString("size_unit"));
           setPkgStyle(rs.getString("pkg_style"));
           setMfgDesc(rs.getString("mfg_desc"));
           setLocation(rs.getString("location"));
           String lot = rs.getString("lot_num");
           if (lot != null) {
             if (lot.indexOf(" ") > 0) {
               setLotNum(lot.substring(0,lot.indexOf(" ",2)));
             } else {
               setLotNum(lot);
             }
           } else {
             setLotNum(lot);
           }
           setOnHand((int)rs.getInt("on_hand"));
           setOnOrder((int)rs.getInt("on_order"));
           setExpireDate(rs.getString("expire_date"));
           String ware = rs.getString("warehouse");
           if(ware.indexOf(" ",2) < 0){
             setWarehouse(ware);
           }else{
             setWarehouse(ware.substring(0,ware.indexOf(" ",2)));
           }
           setPromiseDate(rs.getString("promised_date"));
           setPONumber((int)rs.getInt("po_number"));
           setRevPromDate(rs.getString("rev_prom_date"));
         }
         
      
      } catch (Exception e) { e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);   throw e;
       } finally{
             dbrs.close();
            }
   }

   public void loadRaw()  throws Exception {
      String query = "select unique item_id,trade_name,mfg_desc from inventory_view_snapshot where item_id = " + item_id.toString();

      DBResultSet dbrs = null;
      ResultSet rs = null;
      try {
         dbrs = db.doQuery(query);
         rs=dbrs.getResultSet();
         while (rs.next()){
           setItemId((int)rs.getInt(1));
           setTradeName(rs.getString(2));
           setMfgDesc(rs.getString(3));
         }
         
      
      } catch (Exception e) { e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);   throw e;
       } finally{
             dbrs.close();
            }
   }

   public Vector retrieve(String where,String sortBy)  throws Exception {
      String query = "select * from inventory_view_snapshot";
      if (where != (String)null) query = query + " where " + where ;
      if (sortBy != (String)null) query = query + " order by " + sortBy;

      DBResultSet dbrs = null;
      ResultSet rs = null;
      Inventory I;
      Vector allI = new Vector();
      try {
         dbrs = db.doQuery(query);
         rs=dbrs.getResultSet();
         while (rs.next()){
           I = new Inventory();
           I.setItemId((int)rs.getInt(1));
           I.setFacilityId(rs.getString(2));
           I.setFacPartNo(rs.getString(3));
           I.setTradeName(rs.getString(4));
           I.setPartSize((float)rs.getFloat(5));
           I.setSizeUnit(rs.getString(6));
           I.setPkgStyle(rs.getString(7));
           I.setMfgDesc(rs.getString(8));
           I.setLocation(rs.getString(9));
           I.setLotNum(rs.getString(10));
           I.setOnHand((int)rs.getInt(11));
           I.setOnOrder((int)rs.getInt(12));
           I.setExpireDate(rs.getString(13));
           I.setWarehouse(rs.getString(14));
           I.setPromiseDate(rs.getString(15));
           I.setPONumber((int)rs.getInt(16));
           allI.addElement(I);
         }
         allI.trimToSize();
         
      
      } catch (Exception e) { e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);   throw e;
       } finally{
             dbrs.close();
            }
      return allI;
   }

   public Vector getDetail(Integer item,String facility,boolean fac,String partnum)  throws Exception  {
   // // System.out.println("**********Here2:"+partnum);
      String query = "select item_id,warehouse,on_hand,lot_num,expire_date,on_order,promised_date,po_number,rev_prom_date from inventory_view_snapshot";
      query = query + " where item_id = " + item.toString();
      if (fac) {
       query = query + " and facility_id = '" + facility + "' ";
      }
      query = query + " and fac_part_no='"+partnum+"' ";

      if(BothHelpObjs.isBlankString(partnum) &&
        BothHelpObjs.isBlankString(facility)){
        query = "select item_id,warehouse,on_hand,lot_num,expire_date,on_order,promised_date,po_number,rev_prom_date from inventory_view_snapshot";
        query = query + " where item_id = " + item.toString();
        query = query + " group by item_id,warehouse,on_hand,lot_num,expire_date,on_order,promised_date,po_number,rev_prom_date";
      }

      query = query + " order by item_id,on_hand,on_order";
      DBResultSet dbrs = null;
      ResultSet rs = null;
      Inventory I;
      Vector allI = new Vector();
      try {
         dbrs = db.doQuery(query);
         rs=dbrs.getResultSet();
         while (rs.next()){
           I = new Inventory();
           I.setItemId((int)rs.getInt(1));
           String ware = rs.getString(2);
           if (ware != null) {
             if (ware.indexOf(" ") > 0) {
               I.setWarehouse(ware.substring(0,ware.indexOf(" ",2)));
             } else {
               I.setWarehouse(ware);
             }
           } else {
             I.setWarehouse(ware);
           }
//           I.setWarehouse(rs.getString(2));
           I.setOnHand((int)rs.getInt(3));
           String lot = rs.getString(4);
           if (lot != null) {
             if (lot.indexOf(" ") > 0) {
               I.setLotNum(lot.substring(0,lot.indexOf(" ",2)));
             } else {
               I.setLotNum(lot);
             }
           } else {
             I.setLotNum(lot);
           }
           I.setExpireDate(rs.getString(5));
           I.setOnOrder((int)rs.getInt(6));
           I.setPromiseDate(rs.getString(7));
           I.setPONumber((int)rs.getInt(8));
           I.setRevPromDate(rs.getString("rev_prom_date"));
           allI.addElement(I);
         }

         allI.trimToSize();
         
      
      } catch (Exception e) { e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);   throw e;
       } finally{
             dbrs.close();
            }
      return allI;
// CBK begin
      /*
      Vector uniqI = new Vector();
      Integer lastItem = new Integer(0);
      Integer lastHand = new Integer(0);
      Integer lastOrd = new Integer(0);
      String  lastExpDate = new String("date");
      String  lastProDate = new String("date");
      Integer thisItem,thisHand,thisOrd;
      String  thisExpDate, thisProDate;

      Vector handUnique = new Vector();
      Vector orderUnique = new Vector();
      int i;
      for (i=0;i<allI.size();i++) {
        thisItem = ((Inventory)allI.elementAt(i)).getItemId();
        thisHand = ((Inventory)allI.elementAt(i)).getOnHand();
        thisOrd = ((Inventory)allI.elementAt(i)).getOnOrder();
        if (thisHand.intValue() > 0){
           thisExpDate = ((Inventory)allI.elementAt(i)).getExpireDate() == null ? "" : ((Inventory)allI.elementAt(i)).getExpireDate();
           String tmp = thisItem.toString()+thisHand.toString()+thisExpDate;
           if (handUnique.isEmpty() || !handUnique.contains(tmp)){
              handUnique.addElement(tmp);
              uniqI.addElement(allI.elementAt(i));
           }
           lastExpDate = thisExpDate;
           lastHand = thisHand;
         } else if (thisOrd.intValue() >0){
           thisProDate = ((Inventory)allI.elementAt(i)).getPromiseDate() == null ? "date" : ((Inventory)allI.elementAt(i)).getPromiseDate();;
           String tmp = thisItem.toString()+thisOrd.toString()+thisProDate;
           if (orderUnique.isEmpty() || !orderUnique.contains(tmp)){
                  orderUnique.addElement(tmp);
                  uniqI.addElement(allI.elementAt(i));
           }
           lastProDate = thisProDate;
           lastOrd = thisOrd;
        }
        lastItem = thisItem;
      }

      return uniqI;
*/
// CBK end
   }

   public Vector retrieveSum(String where,String sortBy,String expDate,String promDate)  throws Exception {
      String query = "select item_id,facility_id,fac_part_no,trade_name,part_size,size_unit,pkg_style,mfg_desc,warehouse,sum(on_hand),sum(on_order)";
      //query = query + ",to_char(expire_date,'YYYYDDD')";
      //query = query + ",to_char(promised_date,'YYYYDDD')";
      query = query + " from inventory_view_snapshot";

      if (where != (String)null){
        if (where.length()>0){
           query = query + " where " + where ;
        }
      }
      query = query + "group by item_id,facility_id,fac_part_no,trade_name,part_size,size_unit,pkg_style,mfg_desc,warehouse";
      //query = query + ",to_char(expire_date,'YYYYDDD')";
      //query = query + ",to_char(promised_date,'YYYYDDD')";

      if (sortBy != (String)null) query = query + " order by " + sortBy;

      DBResultSet dbrs = null;
      ResultSet rs = null;
      Inventory I;
      Vector allI = new Vector();
      String dateComp = new String();
      int i =0 ;
      try {
         dbrs = db.doQuery(query);
         rs=dbrs.getResultSet();
         while (rs.next()){
           I = new Inventory();
           I.setItemId((int)rs.getInt(1));
           I.setFacilityId(rs.getString(2));
           I.setFacPartNo(rs.getString(3));
           I.setTradeName(rs.getString(4));
           I.setPartSize((float)rs.getFloat(5));
           I.setSizeUnit(rs.getString(6));
           I.setPkgStyle(rs.getString(7));
           I.setMfgDesc(rs.getString(8));
           //I.setLocation(rs.getString(9));
           I.setWarehouse(rs.getString(9));
           I.setOnHand((int)rs.getInt(10));
           I.setOnOrder((int)rs.getInt(11));
           //I.setExpireDate(rs.getString(13));
           //I.setPromiseDate(rs.getString(14));
           allI.addElement(I);
         }
         allI.trimToSize();
         
       
     } catch (Exception e) { e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);  throw e;
       } finally{
             dbrs.close();
             }
      return allI;
   }

   public boolean hasInventory(Integer item)   throws Exception {
     String query = "select item_id from inventory_view_snapshot where item_id = " + item.toString();
     DBResultSet dbrs = null;
      ResultSet rs = null;
     int flag =0;
     try {
        dbrs = db.doQuery(query);
        rs=dbrs.getResultSet();
        while (rs.next()){
           flag =  1;
           break;
        }
        
      
     } catch (Exception e) { e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);  throw e;
       } finally{
             dbrs.close();
             }
     return flag > 0;
   }

}




























