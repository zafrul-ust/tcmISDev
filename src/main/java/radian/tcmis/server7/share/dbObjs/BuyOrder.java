/*SQLWKS> describe buy_order
Column Name                    Null?    Type
------------------------------ -------- ----
PR_NUMBER                      NOT NULL NUMBER
BUYER                                   VARCHAR2(30)
DATE_ASSIGNED                           DATE
ITEM_ID                                 NUMBER
NEED_DATE                               DATE
PART_ID                                 VARCHAR2(30)
ITEM_DESC                               VARCHAR2(61)
SHELF_LIFE_DAYS                         VARCHAR2(16)
LAST_PO                                 NUMBER
TRADE_NAME                              VARCHAR2(80)
SIZE_UNIT                               VARCHAR2(30)
MFG_ID                                  VARCHAR2(60)
MFG_PART_NO                             VARCHAR2(30)
ITEM_TYPE                               VARCHAR2(8)
ORDER_QUANTITY                          NUMBER
UOM                                     VARCHAR2(2)
PRIORITY                                NUMBER(1)
RADIAN_PO                               NUMBER
SPECIFICATION                           VARCHAR2(30)
SPEC_REVISION                           VARCHAR2(8)
LAST_BUYER                              VARCHAR2(60)
LAST_SUPPLIER                           VARCHAR2(80)
PO_IN_JDE                               CHAR(1)
FACILITY                                VARCHAR2(20)
SALES_ORDER                             NUMBER(8)
RAYTHEON_PO                             VARCHAR2(40)
BRANCH_PLANT                            VARCHAR2(12)
DATE_ISSUED                             DATE
DATE_PO_CREATED                         DATE
STATUS                                  VARCHAR2(30)
DATE_CHANGED                            DATE
COMMENTS                                VARCHAR2(160)
*/
package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import java.util.*;
import java.sql.*;
import radian.tcmis.server7.share.db.*;

public class BuyOrder {
   protected TcmISDBModel db;
   protected Integer pr_number;
   protected String buyer;
   protected String date_assigned;
   protected Integer item_id;
   protected String need_date;
   protected String part_id;
   protected String item_desc;
   protected String shelf_life_days;
   protected Integer last_po;
   protected String trade_name;
   protected String size_unit;
   protected String mfg_id;
   protected String mfg_part_no;
   protected String item_type;
   protected Integer order_quantity;
   protected String uom;
   protected Integer priority;
   protected Integer radian_po;
   protected String specification;
   protected String spec_revision;
   protected String last_buyer;
   protected String last_supplier;
   protected String po_in_jde;
   protected String facility;
   protected Integer sales_order;
   protected String raytheon_po;
   protected String branch_plant;
   protected String date_issued;
   protected String date_po_created;
   protected String status;
   protected String date_changed;
   protected String comments;

   public BuyOrder(TcmISDBModel db){
      this.db = db;
   }
   // Get methods
   public Integer getPrNumber(){ return pr_number;}
   public String getBuyer(){ return buyer;}
   public String getDateAssigned(){ return date_assigned;}
   public Integer getItemId(){ return item_id;}
   public String getNeedDate(){ return need_date;}
   public String getPartId(){ return part_id;}
   public String getItemDesc(){ return item_desc;}
   public String getShelfLifeDays(){ return shelf_life_days;}
   public Integer getLastPo(){ return last_po;}
   public String getTradename(){ return trade_name;}
   public String getSizeUnit(){ return size_unit;}
   public String getMfgId(){ return mfg_id;}
   public String getMfgPartNo(){ return mfg_part_no;}
   public String getItemType(){ return item_type;}
   public Integer getorderQuantity(){ return order_quantity;}
   public String getUom(){ return uom;}
   public Integer getPriority(){ return priority;}
   public Integer getRadianPo(){ return radian_po;}
   public String getSpecification(){ return specification;}
   public String getSpecRevision(){ return spec_revision;}
   public String getLastBuyer(){ return last_buyer;}
   public String getLastSupplier(){ return last_supplier;}
   public String getPoInJde(){ return po_in_jde;}
   public String getFacility(){ return facility;}
   public Integer getSalesOrder(){ return sales_order;}
   public String getRaytheonPo(){ return raytheon_po;}
   public String getBranchPlant(){ return branch_plant;}
   public String getDateIssued(){ return date_issued;}
   public String getDatePoCreated(){ return date_po_created;}
   public String getStatus(){ return status;}
   public String getDateChanged(){ return date_changed;}
   public String getComments(){ return comments;}

   // Set methods
   public void setPrNumber(Integer s){ pr_number = s;}
   public void setBuyer(String s){ buyer = s;}
   public void setDateAssigned(String s){ date_assigned = s;}
   public void setItemId(Integer s){ item_id = s;}
   public void setNeedDate(String s){ need_date = s;}
   public void setPartId(String s){ part_id = s;}
   public void setItemDesc(String s){ item_desc = s;}
   public void setShelfLifeDays(String s){ shelf_life_days = s;}
   public void setLastPo(Integer s){ last_po = s;}
   public void setTradename(String s){ trade_name = s;}
   public void setSizeUnit(String s){ size_unit = s;}
   public void setMfgId(String s){ mfg_id = s;}
   public void setMfgPartNo(String s){ mfg_part_no = s;}
   public void setItemType(String s){ item_type = s;}
   public void setOrderQuantity(Integer s){ order_quantity = s;}
   public void setUom(String s){ uom = s;}
   public void setPriority(Integer s){ priority = s;}
   public void setRadianPo(Integer s){ radian_po = s;}
   public void setSpecification(String s){ specification = s;}
   public void setSpecRevision(String s){ spec_revision = s;}
   public void setLastBuyer(String s){ last_buyer = s;}
   public void setLastSupplier(String s){ last_supplier = s;}
   public void setPoInJde(String s){ po_in_jde = s;}
   public void setFacility(String s){ facility = s;}
   public void setSalesOrder(Integer s){ sales_order = s;}
   public void setRaytheonPo(String s){ raytheon_po = s;}
   public void setBranchPlant(String s){ branch_plant = s;}
   public void setDateIssued(String s){ date_issued = s;}
   public void setDatePoCreated(String s){ date_po_created = s;}
   public void setStatus(String s){ status = s;}
   public void setDateChanged(String s){ date_changed = s;}
   public void setComments(String s){ comments = s;}

   //5-23-01 new
   public Vector getAllBuyerPersonnel()throws Exception{
     Vector v = new Vector();
     try{
       UserGroup ug = new UserGroup(db);
       ug.setGroupFacId("All");
       ug.setGroupId("Buyer");
       v = ug.getMembers();
     }catch(Exception e){
      e.printStackTrace();
      throw e;
     }
     return v;
   }

   public Personnel getBuyerPersonnel()throws Exception{
     try{
       UserGroup ug = new UserGroup(db);
       ug.setGroupFacId("All");
       ug.setGroupId("Buyer");
       Vector v = ug.getMembers();
       for(int i=0;i<v.size();i++){
         Personnel p = (Personnel)v.elementAt(i);
         if(p.getLastName().equalsIgnoreCase(buyer)){
           return p;
         }
       }
     }catch(Exception e){e.printStackTrace();throw e;}
     return null;
   }

   public void load() throws Exception{
      String query = "select * from buy_order where pr_number = " + getPrNumber().toString();
      DBResultSet dbrs = null;
      ResultSet rs = null;
      try {
         dbrs = db.doQuery(query);
         rs=dbrs.getResultSet();
         if(rs.next()){
           setBuyer(BothHelpObjs.makeBlankFromNull(rs.getString("buyer")));
           setDateAssigned(rs.getString("date_assigned"));
           setItemId(new Integer(rs.getInt("item_id")));
           setNeedDate(rs.getString("need_date"));
           setPartId(rs.getString("part_id"));
           setItemDesc(rs.getString("item_desc"));
           setShelfLifeDays(rs.getString("shelf_life_days"));
           setLastPo(new Integer(rs.getInt("last_po")));
           setTradename(rs.getString("trade_name"));
           setSizeUnit(rs.getString("size_unit"));
           setMfgId(rs.getString("mfg_id"));
           setMfgPartNo(rs.getString("mfg_part_no"));
           setItemType(rs.getString("item_type"));
           setOrderQuantity(new Integer(rs.getInt("order_quantity")));
           setUom(rs.getString("uom"));
           setPriority(new Integer(rs.getInt("priority")));
           setRadianPo(new Integer(rs.getInt("radian_po")));
           setSpecification(rs.getString("specification"));
           setSpecRevision(rs.getString("spec_revision"));
           setLastBuyer(rs.getString("last_buyer"));
           setLastSupplier(rs.getString("last_supplier"));
           setPoInJde(rs.getString("po_in_jde"));
           setFacility(rs.getString("facility"));
           setSalesOrder(new Integer(rs.getInt("sales_order")));
           setRaytheonPo(rs.getString("raytheon_po"));
           setBranchPlant(rs.getString("branch_plant"));
           setDateIssued(rs.getString("date_issued"));
           setDatePoCreated(rs.getString("date_po_created"));
           setStatus(rs.getString("status"));
           setDateChanged(rs.getString("date_changed"));
           setComments(rs.getString("comments"));
         }
        rs.close();

       } catch (Exception e) {
       e.printStackTrace();
       HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
       throw e; }
       finally { dbrs.close(); }
      return;
   }

   public static BuyOrder getBuyOrderFromSo(TcmISDBModel db,String so) throws Exception{
     DBResultSet dbrs = null;
     ResultSet rs = null;
     try{
       String query = "select pr_number from buy_order where sales_order = " + so;

       BuyOrder bo = new BuyOrder(db);
       dbrs = db.doQuery(query);
         rs=dbrs.getResultSet();
       if(rs.next()){
         bo.setPrNumber(new Integer(rs.getInt("pr_number")));
         bo.load();
       }
       return bo;
     }catch(Exception e){e.printStackTrace();throw e;}
     finally { dbrs.close(); }
   }

   public static Vector getBuyOrdersForItemId(TcmISDBModel db,String itemId,String branchPlant, Calendar onAfterDate) throws Exception{
     DBResultSet dbrs = null;
      ResultSet rs = null;
     try{
       String d = String.valueOf(onAfterDate.get(Calendar.YEAR))+"-"+String.valueOf(onAfterDate.get(Calendar.MONTH)+1)+"-"+String.valueOf(onAfterDate.get(Calendar.DAY_OF_MONTH));
       Vector v = new Vector();
       String query = "select pr_number from buy_order where item_id = "+itemId;
       query = query + " and branch_plant = '"+branchPlant+"' and ";
       query = query + "date_issued >= to_date('"+d+"','YYYY-MM-DD') ";
       query = query + "order by date_issued";

       dbrs = db.doQuery(query);
         rs=dbrs.getResultSet();
       while(rs.next()){
         BuyOrder bo = new BuyOrder(db);
         bo.setPrNumber(new Integer(rs.getInt("pr_number")));
         bo.load();
         v.addElement(bo);
       }

       return v;
     }catch(Exception e){e.printStackTrace();throw e;}
     finally { dbrs.close(); }
   }

   public static void deleteBySo(TcmISDBModel db,String so)throws Exception{
      //System.out.println("\n\n----------- why am i here");
     try{
       if(so==null)return;
       String where = "where sales_order = "+so;
       //String q = "insert into cancelled_buy_order select * from buy_order "+where;
       //By Nawaz 08-29-01 the two tables were not of the same structure which gave rise to the too many values error.
       String q = "insert into cancelled_buy_order ";
       q = q + "(PR_NUMBER,BUYER,DATE_ASSIGNED,ITEM_ID,NEED_DATE,PART_ID,ITEM_DESC,SHELF_LIFE_DAYS,LAST_PO,";
       q = q + "TRADE_NAME,SIZE_UNIT,MFG_ID,MFG_PART_NO,ITEM_TYPE,ORDER_QUANTITY,UOM,PRIORITY,RADIAN_PO,SPECIFICATION,SPEC_REVISION,";
       q = q + "LAST_BUYER,LAST_SUPPLIER,PO_IN_JDE,FACILITY,SALES_ORDER,RAYTHEON_PO,BRANCH_PLANT,DATE_ISSUED,DATE_PO_CREATED,STATUS,DATE_CHANGED,COMMENTS)";

       q = q + " select PR_NUMBER,BUYER,DATE_ASSIGNED,ITEM_ID,NEED_DATE,PART_ID,ITEM_DESC,SHELF_LIFE_DAYS,LAST_PO,";
       q = q + "TRADE_NAME,SIZE_UNIT,MFG_ID,MFG_PART_NO,ITEM_TYPE,ORDER_QUANTITY,UOM,PRIORITY,RADIAN_PO,SPECIFICATION,SPEC_REVISION,";
       q = q + "LAST_BUYER,LAST_SUPPLIER,PO_IN_JDE,FACILITY,SALES_ORDER,RAYTHEON_PO,BRANCH_PLANT,DATE_ISSUED,DATE_PO_CREATED,STATUS,DATE_CHANGED,COMMENTS";
       q = q + "  from buy_order "+where;

       //System.out.println("The Update Query of SO cancelation  :"+q);

       db.doUpdate(q);
       //System.out.println("Done Update Query of SO cancelation  :");
       q = "delete from buy_order "+where;
       db.doUpdate(q);
       //System.out.println("Done Update Query of SO cancelation in Buy Order Table  :"+q);
       //System.out.println("\n\n----------- why am i here 222222222222222");
     }catch(Exception e){e.printStackTrace();throw e;}
     finally {}
   }
   public static int getNumBuyOrdersForSO(TcmISDBModel db,String so)throws Exception{
     try{
       String q = "select count(*) from buy_order where sales_order = "+so;
       return DbHelpers.countQuery(db,q);
     }catch(Exception e){e.printStackTrace();throw e;}
   }
}
