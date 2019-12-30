/*
SQLWKS> describe inventory
Column Name                    Null?    Type
------------------------------ -------- ----
ITEM_ID                                 NUMBER
FACILITY_ID                             VARCHAR2(30)
FAC_PART_NO                    NOT NULL VARCHAR2(30)
TRADE_NAME                              VARCHAR2(80)
PART_SIZE                               NUMBER
SIZE_UNIT                               VARCHAR2(30)
PKG_STYLE                               VARCHAR2(30)
MFG_DESC                                VARCHAR2(60)
LOCATION                                CHAR(1)
LOT_NUM                                 VARCHAR2(30)
ON_HAND                                 NUMBER
ON_ORDER                                NUMBER
EXPIRE_DATE                             DATE
WAREHOUSE                      NOT NULL VARCHAR2(30)
PROMISED_DATE                           DATE
PO_NUMBER                               NUMBER
REV_PROM_DATE                           VARCHAR2(20)
LOT_STATUS                              VARCHAR2(13)
AVAILABLE                               NUMBER
ON_HOLD                                 NUMBER
RECEIVED_DATE                           DATE
READY_TO_SHIP_DATE                      DATE
IN_PURCHASING                           NUMBER
MATERIAL_ID                             NUMBER(38)
SPEC_ID                                 VARCHAR2(400)
SPEC_ON_LINE                            CHAR(1)
MSDS_ON_LINE                            CHAR(1)

*/
package radian.tcmis.server7.share.dbObjs;




import java.sql.*;
import java.util.*;
import java.awt.Color;
import java.math.BigDecimal;

import radian.tcmis.server7.share.db.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;

public class InventoryView  {

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


   public static final String[] colHeadsKey = {"Part Color","Part Group","Catalog","Catalog Company ID","Inventory Group","Group","Part","Description","Type","# per Part","Item","Item Group","Available","Held","On Order","In Puchasing","Component Description","Packaging","Manufacturer","Mfg Part No.","MSDS","Material"};
   public static final String[] colHeads = {" \nPart\nColor","Part\nGroup\n "," \nCatalog\n ","Catalog\nCompany\nID","Inventory\nGroup\n"," \nGroup\n "," \nPart\n "," \nDescription\n "," \nType\n ","#\nper\nPart"," \nItem\n ","Item\nGroup\n "," \nAvail.\n "," \nHeld\n "," \nOn\nOrder"," \nIn\nPurchasing"," \nComponent\nDescription"," \nPackaging\n "," \nManufacturer\n ","Mfg\nPart\nNo.","MSDS\nONLINE\n "," \nMaterial\n "};
   public static final int[] colWidths = {0,0,70,0,0,0,80,130,30,60,40,0,40,35,50,60,130,80,80,70,0,0};
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
                                        BothHelpObjs.TABLE_COL_TYPE_STRING};
   public static final String[] binColHeadsKey = {"Part Color","Part Group","Catalog","Catalog Company ID","Inventory Group","Group","Part","Description","Type","# per Part","Item","Item Group","Bin","Available","Held","On Order","In Puchasing","Component Description","Packaging","Manufacturer","Mfg Part No.","MSDS","Material"};
   public static final String[] binColHeads = {" \nPart\nColor","Part\nGroup\n "," \nCatalog\n ","Catalog\nCompany\nID","Inventory\nGroup\n"," \nGroup\n "," \nPart\n "," \nDescription\n "," \nType\n ","#\nper\nPart"," \nItem\n ","Item\nGroup\n "," \nBin\n "," \nAvail.\n "," \nHeld\n "," \nOn\nOrder"," \nIn\nPurchasing"," \nComponent\nDescription"," \nPackaging\n "," \nManufacturer\n ","Mfg\nPart\nNo.","MSDS\nONLINE\n "," \nMaterial\n "};
   public static final int[] binColWidths = {0,0,70,0,0,0,80,130,30,60,40,0,60,40,35,50,60,130,80,80,70,0,0};
   public static final int[] binColTypes = {BothHelpObjs.TABLE_COL_TYPE_STRING,
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
   public static final String[] lotColHeadsKey = {"Part Color","Part Group","Catalog","Catalog Company ID","Inventory Group","Group","Part","Description","Type","# per Part","Item","Item Group","Bin","Lot","Available","Held","On Order","In Puchasing","Component Description","Packaging","Manufacturer","Mfg Part No.","MSDS","Material"};
   public static final String[] lotColHeads = {" \nPart\nColor","Part\nGroup\n "," \nCatalog\n ","Catalog\nCompany\nID","Inventory\nGroup\n"," \nGroup\n "," \nPart\n "," \nDescription\n "," \nType\n ","#\nper\nPart"," \nItem\n ","Item\nGroup\n "," \nBin\n "," \nLot\n "," \nAvail.\n "," \nHeld\n "," \nOn\nOrder"," \nIn\nPurchasing"," \nComponent\nDescription"," \nPackaging\n "," \nManufacturer\n ","Mfg\nPart\nNo.","MSDS\nONLINE\n "," \nMaterial\n "};
   public static final int[] lotColWidths = {0,0,70,0,0,0,80,130,30,60,40,0,60,80,40,35,50,60,130,80,80,70,0,0};
   public static final int[] lotColTypes = {BothHelpObjs.TABLE_COL_TYPE_STRING,
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



   public InventoryView() throws java.rmi.RemoteException{

   }

   public InventoryView(TcmISDBModel db) throws java.rmi.RemoteException{
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
        //System.out.println("inventory query:"+query);
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
//System.out.println("Inve Vector:"+allI.toString());
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

  protected Hashtable getColKey(String orderBy){
    Hashtable h = new Hashtable();
    if ("ITEM".equalsIgnoreCase(orderBy)) {
      for (int i=0;i<colHeadsKey.length;i++) {
        h.put(colHeadsKey[i],new Integer(i));
      }
    }else if ("BIN".equalsIgnoreCase(orderBy)) {
      for (int i=0;i<binColHeadsKey.length;i++) {
        h.put(binColHeadsKey[i],new Integer(i));
      }
    }else {
      for (int i=0;i<lotColHeadsKey.length;i++) {
        h.put(lotColHeadsKey[i],new Integer(i));
      }
    }

    return h;
  }

  public Vector retrieveSum(String where,String sortBy,boolean allParts)  throws Exception {
      String query = "select item_id,facility_id,fac_part_no,trade_name,part_size,size_unit,"+
                     "pkg_style,mfg_desc,warehouse,sum(available) on_hand,sum(on_order) on_order,sum(on_hold) on_hold,sum(in_purchasing) in_purchasing,"+
                     "MATERIAL_ID,SPEC_ID,SPEC_ON_LINE,MSDS_ON_LINE ";
      query = query + " from inventory";

      if (where != (String)null){
        if (where.length()>0){
           query = query + " where " + where ;
        }
      }
      query = query + " group by item_id,facility_id,fac_part_no,trade_name,part_size,size_unit,pkg_style,mfg_desc,warehouse,MATERIAL_ID,SPEC_ID,SPEC_ON_LINE,MSDS_ON_LINE ";

      if (sortBy != (String)null) query = query + " order by " + sortBy;

      //System.out.println("Query:\n"+query);
      DBResultSet dbrs = null;
      ResultSet rs = null;
      Vector cols = new Vector();
      int i =0, lastItem=0 ;
      try {
         dbrs = db.doQuery(query);
         rs=dbrs.getResultSet();
         String allfpn = "", allfaci="";
         Vector row = new Vector();
        // System.out.println("--> allParts:"+allParts);
         while (rs.next()){
             int item = new Integer(BothHelpObjs.makeZeroFromNull(rs.getString("ITEM_ID"))).intValue();
             //System.out.println("--> items:"+item+"    "+lastItem);
             if (lastItem != item && lastItem!=0) {
               cols.addElement(row);
               row = new Vector();
             }
             if (!allParts){
               if (lastItem == item) {
                 row.setElementAt("Not unique",1);
                 row.setElementAt("",10);
                 continue;
               }
             }

             String facpartno = BothHelpObjs.makeBlankFromNull(rs.getString("FAC_PART_NO"));
             String faci = BothHelpObjs.makeBlankFromNull(rs.getString("FACILITY_ID"));
             if (lastItem == item) {    // same part num
                if (allfpn.indexOf(facpartno)>-1) continue; // avoid dup
                allfpn  += "\n" + facpartno;
                allfaci += "\n" + faci;
                row.setElementAt(allfpn,1);
                row.setElementAt(allfaci,10);
                continue;
             }    else   {
                allfpn = facpartno;
                allfaci = faci;
             }
             //System.out.println("--> facno:"+facpartno);
             row.addElement(String.valueOf(item));
             row.addElement(allfpn);
             row.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("TRADE_NAME")));
             String sizeS = new String(String.valueOf(BothHelpObjs.makeFloatFromNull(rs.getString("PART_SIZE"))) + " " +
                                       BothHelpObjs.makeBlankFromNull(rs.getString("SIZE_UNIT"))+ " " +
                                       BothHelpObjs.makeBlankFromNull(rs.getString("PKG_STYLE")));
             row.addElement(sizeS);
             row.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("MFG_DESC")));
             row.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("WAREHOUSE")));
             row.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("ON_HAND")));
             row.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("ON_HOLD")));
             row.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("ON_ORDER")));
             row.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("IN_PURCHASING")));
             row.addElement(allfaci);
             row.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("MATERIAL_ID")));
             row.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("SPEC_ID")));
             row.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("SPEC_ON_LINE")));
             row.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("MSDS_ON_LINE")));
             lastItem = item; // reset
         }
         cols.addElement(row);


     } catch (Exception e) {
         e.printStackTrace();
         HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);

         throw e;
       } finally{
             dbrs.close();

     }
     return cols;
   }

  public Vector buildQueryData(Hashtable inData) throws Exception {
    Vector tableData = new Vector();
	 DBResultSet dbrs = null;
	 ResultSet rs = null;
    try {
      dbrs = db.doQuery(buildSearchQuery(inData));
      rs = dbrs.getResultSet();
      while (rs.next()){
        HashMap data = new HashMap(19);
        data.put("DETAIL_0",BothHelpObjs.makeBlankFromNull(rs.getString("catalog_id")));
        data.put("DETAIL_1",BothHelpObjs.makeBlankFromNull(rs.getString("cat_part_no")));
        data.put("DETAIL_2",BothHelpObjs.makeBlankFromNull(rs.getString("part_description")));
        data.put("DETAIL_3",BothHelpObjs.makeBlankFromNull(rs.getString("part_group_no")));
        data.put("DETAIL_4",BothHelpObjs.makeBlankFromNull(rs.getString("stocking_method")));
        data.put("DETAIL_5",BothHelpObjs.makeBlankFromNull(rs.getString("items_per_part")));
        data.put("DETAIL_6",BothHelpObjs.makeBlankFromNull(rs.getString("item_id")));
        data.put("DETAIL_7",BothHelpObjs.makeBlankFromNull(rs.getString("qty_available")));
        data.put("DETAIL_8",BothHelpObjs.makeBlankFromNull(rs.getString("qty_held")));
        data.put("DETAIL_9",BothHelpObjs.makeBlankFromNull(rs.getString("qty_on_order")));
        data.put("DETAIL_10",BothHelpObjs.makeBlankFromNull(rs.getString("qty_in_purchasing")));
        data.put("DETAIL_11",BothHelpObjs.makeBlankFromNull(rs.getString("material_desc")));
        data.put("DETAIL_12",BothHelpObjs.makeBlankFromNull(rs.getString("packaging")));
        data.put("DETAIL_13",BothHelpObjs.makeBlankFromNull(rs.getString("mfg_desc")));
        data.put("DETAIL_14",BothHelpObjs.makeBlankFromNull(rs.getString("mfg_part_no")));
        data.put("DETAIL_15",BothHelpObjs.makeBlankFromNull(rs.getString("reorder_point")));
        data.put("DETAIL_16",BothHelpObjs.makeBlankFromNull(rs.getString("stocking_level")));
        //data.put("DETAIL_17",BothHelpObjs.makeBlankFromNull(rs.getString("shelf_life_storage_temp")));
        tableData.addElement(data);
      }

    } catch (Exception e) {
      e.printStackTrace();
    } finally{
      dbrs.close();
    }
    return tableData;
  }

  private String buildSearchQuery(Hashtable inData) {
	 String facility = (String) inData.get("FACILITY");
    String userFac = (String) inData.get("USER_FAC");
    String warehouse = (String) inData.get("WAREHOUSE");
    String itemIn = (String) inData.get("ITEM");
    String expDate = (String) inData.get("EXP_VAL");
    String exp2Date = (String) inData.get("EXP2_VAL");
    String promDate = (String) inData.get("PROM_VAL");
    boolean oH = ((Boolean) inData.get("ON_HOLD_BOL")).booleanValue();
    boolean oO = ((Boolean) inData.get("ON_ORDER_BOL")).booleanValue();
	 StringBuffer query = new StringBuffer("select * from table(PKG_INVENTORY_DETAIL_WEB.FX_INVENTORY('','"+BothHelpObjs.makeBlankFromNull(itemIn)+"'");

	   if (BothHelpObjs.isBlankString(facility) || facility.startsWith("All")) {
        query.append(",'ALL'");
      }else {
        query.append(",'"+facility+"'");
		}
		if (BothHelpObjs.isBlankString(warehouse) || warehouse.startsWith("All")) {
        query.append(",'ALL'");
      }else {
        query.append(",'"+warehouse+"'");
		}
		//inventory collection
		query.append(",'N'");
		if (BothHelpObjs.isBlankString(expDate) || !oH) {
        query.append(",''");
      }else {
        query.append(",'"+Integer.parseInt(expDate)+"'");
      }
      if (BothHelpObjs.isBlankString(exp2Date) || !oH) {
        query.append(",''");
      }else {
        query.append(",'"+Integer.parseInt(exp2Date)+"'");
      }
      if (BothHelpObjs.isBlankString(promDate) || !oO) {
        query.append(",''");
      }else {
        query.append(",'"+Integer.parseInt(promDate)+"'");
      }
	   query.append("))");

	  return query.toString();
  }

  public Hashtable retrieveSumNew(Hashtable inData)  throws Exception {
    Hashtable h = new Hashtable();
    Vector tableData = new Vector();
	 DBResultSet dbrs = null;
	 ResultSet rs = null;
    String expDate = (String) inData.get("EXP_VAL");
    String exp2Date = (String) inData.get("EXP2_VAL");
    String promDate = (String) inData.get("PROM_VAL");
    boolean oH = ((Boolean) inData.get("ON_HOLD_BOL")).booleanValue();
    boolean oO = ((Boolean) inData.get("ON_ORDER_BOL")).booleanValue();
    String orderBy = "ITEM";
    try {
      Vector partRows = new Vector();
      partRows.addElement(new Integer(0));
      Vector itemRows = new Vector();
      itemRows.addElement(new Integer(0));
      int[] cols = {0};
      int row = 0;
      boolean qtyOnHandChecked = false;
      boolean qtyOnOrderChecked = false;
      if (oH && (!BothHelpObjs.isBlankString(expDate) || !BothHelpObjs.isBlankString(exp2Date))) {
        qtyOnHandChecked = true;
      }
      if (oO && !BothHelpObjs.isBlankString(promDate)) {
        qtyOnOrderChecked = true;
      }

	   dbrs = db.doQuery(buildSearchQuery(inData));
      rs = dbrs.getResultSet();
		while (rs.next()){
        Vector data = new Vector();
        String catPartNo = BothHelpObjs.makeBlankFromNull(rs.getString("cat_part_no"));
        String partGroupNo = BothHelpObjs.makeBlankFromNull(rs.getString("part_group_no"));
        String itemID = BothHelpObjs.makeBlankFromNull(rs.getString("item_id"));
        String catalogID = BothHelpObjs.makeBlankFromNull(rs.getString("catalog_id"));
		  String catalogCompanyId = BothHelpObjs.makeBlankFromNull(rs.getString("catalog_company_id"));
		  //ignore records that we don't care about
        BigDecimal qtyOnHand = null;
        BigDecimal qtyOnOrder = null;
        if (qtyOnHandChecked) {
          qtyOnHand = rs.getBigDecimal("qty_available");
          qtyOnHand.add(rs.getBigDecimal("qty_held"));
          if (qtyOnHand.doubleValue() <= 0.0) {
            continue;
          }
        }
        if (qtyOnOrderChecked) {
          qtyOnOrder = rs.getBigDecimal("qty_on_order");
          qtyOnOrder.add(rs.getBigDecimal("qty_in_purchasing"));
          if (qtyOnOrder.doubleValue() <= 0.0) {
            continue;
          }
        }
        if (qtyOnHandChecked && qtyOnOrderChecked) {
          if (qtyOnHand.doubleValue() <= 0.0 || qtyOnOrder.doubleValue() <= 0.0) {
            continue;
          }
        }

        data.addElement(catPartNo);
        data.addElement(itemID);
        data.addElement(catPartNo+partGroupNo+catalogID+catalogCompanyId);
        data.addElement(catPartNo+itemID+partGroupNo+catalogID+catalogCompanyId);
        data.addElement("");        //declare this column for later use.  This column will tell be whether the part is colored
        data.addElement(partGroupNo);
        data.addElement(catalogID);
		  data.addElement(catalogCompanyId);
		  data.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("inventory_group")));
		  data.addElement(catPartNo+partGroupNo);
        data.addElement(catPartNo);
        data.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("part_description")));
        data.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("stocking_method")));
        //data.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("bundle"))); 
		  data.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("items_per_part")));
		  data.addElement(itemID);
        data.addElement("");    //declare column for later use.  This column will tell me if the item is a kit
        data.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("qty_available")));
        data.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("qty_held")));
        data.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("qty_on_order")));
        data.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("qty_in_purchasing")));
        //data.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("trade_name")));
		  data.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("material_desc")));
		  data.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("packaging")));
        data.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("mfg_desc")));
        data.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("mfg_part_no")));
        data.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("msds_on_line")));
        data.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("material_id")));

        tableData.addElement(data);
      }

    } catch (Exception e) {
      e.printStackTrace();
    } finally{
      dbrs.close();
      //cs.close();
    }

    if ("ITEM".equalsIgnoreCase(orderBy)) {
      h.put("COL_HEADS",colHeads);
      h.put("COL_TYPES",colTypes);
      h.put("COL_WIDTHS",colWidths);
      h.put("KEY_COLUMNS",getColKey(orderBy));
    }else if ("BIN".equalsIgnoreCase(orderBy)) {
      h.put("COL_HEADS",binColHeads);
      h.put("COL_TYPES",binColTypes);
      h.put("COL_WIDTHS",binColWidths);
      h.put("KEY_COLUMNS",getColKey(orderBy));
    }else {
      h.put("COL_HEADS",lotColHeads);
      h.put("COL_TYPES",lotColTypes);
      h.put("COL_WIDTHS",lotColWidths);
      h.put("KEY_COLUMNS",getColKey("LOT"));
    }

    h.put("TABLE_DATA",tableData);
    h.put("PART_COLOR",new Color(224,226,250));
    h.put("ITEM_COMP_COLOR",new Color(224,250,226));
    //h.put("RENDERERS",renderers);

    //setting table attribute from here
    Hashtable tableAttribute = new Hashtable();
    tableAttribute.put("FONT_NAME","Dialog");
    tableAttribute.put("FONT_STYLE",new Integer(0));
    tableAttribute.put("FONT_SIZE",new Integer(10));
    tableAttribute.put("HEADER_FOREGROUND",new Color(255,255,255));
    tableAttribute.put("HEADER_BACKGROUND",new Color(0,0,66));
    //cell border
    Vector v = new Vector();
    //part border
    Hashtable ph = new Hashtable();
    ph.put("BORDER_COLOR",new Color(255,255,255));
    ph.put("TOP_INSETS",new Integer(0));
    ph.put("LEFT_INSETS",new Integer(1));
    ph.put("BOTTOM_INSETS",new Integer(0));
    ph.put("RIGHT_INSETS",new Integer(1));
    ph.put("ALIGN",new Integer(3));                //center - 1: right, 2: left
    v.addElement(ph);
    //item border
    Hashtable ih = new Hashtable();
    ih.put("BORDER_COLOR",new Color(0,0,0));
    ih.put("TOP_INSETS",new Integer(0));
    ih.put("LEFT_INSETS",new Integer(0));
    ih.put("BOTTOM_INSETS",new Integer(1));
    ih.put("RIGHT_INSETS",new Integer(1));
    ih.put("ALIGN",new Integer(3));               //center - 1: right, 2: left
    v.addElement(ih);
    //component border
    Hashtable ch = new Hashtable();
    ch.put("BORDER_COLOR",new Color(255,255,255));
    ch.put("TOP_INSETS",new Integer(1));
    ch.put("LEFT_INSETS",new Integer(1));
    ch.put("BOTTOM_INSETS",new Integer(0));
    ch.put("RIGHT_INSETS",new Integer(1));
    ch.put("ALIGN",new Integer(1));
    v.addElement(ch);

    tableAttribute.put("BORDER_STYLE",v);

    h.put("TABLE_ATTRIBUTE",tableAttribute);
    return h;
  }

  public Hashtable retrieveSumNew(String query, String orderBy)  throws Exception {
    Hashtable h = new Hashtable();
    Vector tableData = new Vector();
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      //System.out.println("\n\nNUMBER of row from result set: "+rs.getrgetRow());
      String lastPartItemGroup = "";
      String lastPartGroup = "";
      Vector partRows = new Vector();
      partRows.addElement(new Integer(0));
      boolean partColor = false;
      boolean itemColor = false;
      Vector itemRows = new Vector();
      itemRows.addElement(new Integer(0));
      int[] cols = {0};
      int row = 0;
      while (rs.next()){
        Vector data = new Vector();
        data.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("cat_part_no")));
        data.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("item_id")));
        data.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("part_group")));
        data.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("part_item_group")));

        data.addElement("");        //declare this column for later use.  This column will tell be whether the part is colored
        data.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("part_group_no")));
        data.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("catalog_id")));
        data.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("part_group")));
        data.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("cat_part_no")));
        data.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("part_description")));
        data.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("stocking_method")));
        data.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("bundle")));
        /*
        if (BothHelpObjs.isBlankString(bundle)) {
          data.addElement("Each");
        }else {
          bundle += " of "+BothHelpObjs.makeBlankFromNull(rs.getString("quantity_per_bundle"));
          data.addElement(bundle);
        }   */
        data.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("item_id")));
        data.addElement("");    //declare column for later use.  This column will tell me if the item is a kit
        //1-30-02
        if ("BIN".equalsIgnoreCase(orderBy)) {
          data.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("bin")));
        }else if ("LOT".equalsIgnoreCase(orderBy)) {
          data.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("bin")));
          data.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("lot_num")));
        }
        data.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("on_hand")));
        data.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("on_hold")));
        data.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("on_order")));
        data.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("in_purchasing")));
        data.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("trade_name")));
        /*
        String pkg = BothHelpObjs.makeBlankFromNull(rs.getString("components_per_item"));
        if (BothHelpObjs.isBlankString(pkg)) {
          pkg = BothHelpObjs.makeBlankFromNull(rs.getString("packaging"));
        }else {
          pkg += " x "+BothHelpObjs.makeBlankFromNull(rs.getString("packaging"));
        } */
        data.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("packaging")));
        data.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("mfg_desc")));
        data.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("mfg_part_no")));
        data.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("msds_on_line")));
        data.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("material_id")));

        //System.out.println("\n\n------- search data: "+data);
        tableData.addElement(data);
      }

    } catch (Exception e) {
      e.printStackTrace();
    } finally{
      dbrs.close();
    }

    if ("ITEM".equalsIgnoreCase(orderBy)) {
      h.put("COL_HEADS",colHeads);
      h.put("COL_TYPES",colTypes);
      h.put("COL_WIDTHS",colWidths);
      h.put("KEY_COLUMNS",getColKey(orderBy));
    }else if ("BIN".equalsIgnoreCase(orderBy)) {
      h.put("COL_HEADS",binColHeads);
      h.put("COL_TYPES",binColTypes);
      h.put("COL_WIDTHS",binColWidths);
      h.put("KEY_COLUMNS",getColKey(orderBy));
    }else {
      h.put("COL_HEADS",lotColHeads);
      h.put("COL_TYPES",lotColTypes);
      h.put("COL_WIDTHS",lotColWidths);
      h.put("KEY_COLUMNS",getColKey("LOT"));
    }

    h.put("TABLE_DATA",tableData);
    h.put("PART_COLOR",new Color(224,226,250));
    h.put("ITEM_COMP_COLOR",new Color(224,250,226));
    //h.put("RENDERERS",renderers);

    //setting table attribute from here
    Hashtable tableAttribute = new Hashtable();
    tableAttribute.put("FONT_NAME","Dialog");
    tableAttribute.put("FONT_STYLE",new Integer(0));
    tableAttribute.put("FONT_SIZE",new Integer(10));
    tableAttribute.put("HEADER_FOREGROUND",new Color(255,255,255));
    tableAttribute.put("HEADER_BACKGROUND",new Color(0,0,66));
    //cell border
    Vector v = new Vector();
    //part border
    Hashtable ph = new Hashtable();
    ph.put("BORDER_COLOR",new Color(255,255,255));
    ph.put("TOP_INSETS",new Integer(0));
    ph.put("LEFT_INSETS",new Integer(1));
    ph.put("BOTTOM_INSETS",new Integer(0));
    ph.put("RIGHT_INSETS",new Integer(1));
    ph.put("ALIGN",new Integer(3));                //center - 1: right, 2: left
    v.addElement(ph);
    //item border
    Hashtable ih = new Hashtable();
    ih.put("BORDER_COLOR",new Color(0,0,0));
    ih.put("TOP_INSETS",new Integer(0));
    ih.put("LEFT_INSETS",new Integer(0));
    ih.put("BOTTOM_INSETS",new Integer(1));
    ih.put("RIGHT_INSETS",new Integer(1));
    ih.put("ALIGN",new Integer(3));               //center - 1: right, 2: left
    v.addElement(ih);
    //component border
    Hashtable ch = new Hashtable();
    ch.put("BORDER_COLOR",new Color(255,255,255));
    ch.put("TOP_INSETS",new Integer(1));
    ch.put("LEFT_INSETS",new Integer(1));
    ch.put("BOTTOM_INSETS",new Integer(0));
    ch.put("RIGHT_INSETS",new Integer(1));
    ch.put("ALIGN",new Integer(1));
    v.addElement(ch);

    tableAttribute.put("BORDER_STYLE",v);

    h.put("TABLE_ATTRIBUTE",tableAttribute);
    return h;
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



























