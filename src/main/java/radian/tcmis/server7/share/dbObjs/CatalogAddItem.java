/*
SQLWKS> describe catalog_add_item
Column Name                    Null?    Type
------------------------------ -------- ----
REQUEST_ID                              NUMBER
MATERIAL_DESC                           VARCHAR2(80)
MANUFACTURER                            VARCHAR2(60)
MATERIAL_ID                             NUMBER
PART_SIZE                               NUMBER
SIZE_UNIT                               VARCHAR2(30)
PKG_STYLE                               VARCHAR2(30)
MATERIAL_APPROVED_BY                    NUMBER
MATERIAL_APPROVED_ON                    DATE
ITEM_APPROVED_BY                        NUMBER
ITEM_APPROVED_ON                        DATE
MFG_CATALOG_ID                          VARCHAR2(30)
PART_ID                                 NUMBER
MATERIAL_TYPE                           VARCHAR2(16)

SQLWKS> describe catalog_add_fate
Column Name                    Null?    Type
------------------------------ -------- ----
REQUEST_ID                     NOT NULL NUMBER
FATE_ID                        NOT NULL NUMBER
FATE_PERCENTAGE                         NUMBER
PART_ID                        NOT NULL NUMBER

*/
package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.share.db.*;


import java.util.*;
import java.sql.*;

public class CatalogAddItem {
   protected TcmISDBModel db;
   protected Integer request_id = null;
   protected String material_desc = null;
   protected String manufacturer = null;
   protected String mfg_catalog_id = null;
   protected Integer material_id = null;
   protected Float part_size  = null;
   protected String size_unit = null;
   protected String pkg_style  = null;
   protected Integer material_approved_by  = null;
   protected String material_approved_on = null;
   protected Integer item_approved_by = null;
   protected String item_approved_on = null;
   protected Integer part_id = null;
   protected String material_type = null;

   protected Vector fate = null;

   public static final int STRING = 0;
   public static final int INT = 1;
   public static final int DATE = 2;


   protected boolean insertStatus;

   public CatalogAddItem()   {
     insertStatus = false;
   }

   public CatalogAddItem(TcmISDBModel db)  throws java.rmi.RemoteException {
     this.db = db;
     insertStatus = false;
   }

   public void setDB(TcmISDBModel db){
     this.db = db;
   }

   public void setRequestId(int num) {
     this.request_id = new Integer(num);
   }
   public Integer getRequestId() {
     return request_id;
   }

   public String getMaterialDesc(){
      return material_desc;
   }
   public void setMaterialDesc(String s){
      material_desc = s;
   }

   public String getManufacturer(){
      return manufacturer;
   }
   public void  setManufacturer(String s){
      manufacturer = s;
   }

   public String getMfgCatalogId(){
      return mfg_catalog_id;
   }
   public void  setMfgCatalogId(String s){
      mfg_catalog_id = s;
   }

   public Integer getMaterialId(){
      return material_id;
   }
   public void setMaterialId(Integer s){
      material_id = s;
   }

   public Float getPartSize(){
     return  part_size ;
   }
   public void setPartSize(Float s){
      part_size  = s;
   }

   public String getSizeUnit(){
      return size_unit;
   }
   public void setSizeUnit(String s){
      size_unit = s;
   }

   public String getPkgStyle(){
      return pkg_style ;
   }
   public void setPkgStyle(String s){
      pkg_style  = s;
   }

   public Integer getMaterialApprovedBy(){
      return material_approved_by ;
   }
   public void  setMaterialApprovedBy(Integer s){
      material_approved_by  = s;
   }

   public String getMaterialApprovedOn(){
      return material_approved_on;
   }
   public void setMaterialApprovedOn(String s){
      material_approved_on = s;
   }

   public Integer getItemApprovedBy(){
      return item_approved_by;
   }
   public void  setItemApprovedBy(Integer s){
      item_approved_by = s;
   }

   public String getItemApprovedOn(){
      return item_approved_on;

   }
   public void setItemApprovedOn(String s){
      item_approved_on = s;

   }

   public Integer getPartId(){
      return part_id;
   }
   public void  setPartId(Integer s){
      part_id = s;
   }

   public String getMaterialType(){
      return material_type;

   }
   public void setMaterialType(String s){
      material_type = s;

   }

   public Vector getFate(){
      return fate;

   }
   public void setFate(Vector s){
      fate = s;

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
       }

      } catch (Exception e) {
       e.printStackTrace();
       HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
       throw e;
    } finally{
         dbrs.close();
       }
     return next;
   }

   public void delete()  throws Exception {
     this.deleteFate();

     String query = "delete catalog_add_item where request_id = " + request_id.toString();
     try {
       db.doUpdate(query);
     } catch (Exception e) {
       e.printStackTrace();
       HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
       throw e;
     }
   }

   public void load()  throws Exception {
      String query = "select * from catalog_add_item where request_id = " + request_id.toString();
      query = query + " and part_id = "+this.part_id.toString();

      Vector reqid = new Vector();
      DBResultSet dbrs = null;
      ResultSet rs = null;
      try {
         dbrs = db.doQuery(query);
         rs=dbrs.getResultSet();
         while (rs.next()){
           request_id = new Integer((int)rs.getInt("REQUEST_ID"));
           material_desc = rs.getString("MATERIAL_DESC");
           manufacturer = rs.getString("MANUFACTURER");
           mfg_catalog_id = rs.getString("MFG_CATALOG_ID");
           material_id = new Integer((int)rs.getInt("MATERIAL_ID"));
           part_size = new Float((float)rs.getFloat("PART_SIZE"));
           //System.out.println("Float 2 :"+part_size);
           size_unit = rs.getString("SIZE_UNIT");
           pkg_style = rs.getString("PKG_STYLE");
           material_approved_by = new Integer((int)rs.getInt("MATERIAL_APPROVED_BY"));
           material_approved_on = rs.getString("MATERIAL_APPROVED_ON");
           item_approved_by = new Integer((int)rs.getInt("ITEM_APPROVED_BY"));
           item_approved_on = rs.getString("ITEM_APPROVED_ON");
           part_id = new Integer((int)rs.getInt("PART_ID"));
           material_type = rs.getString("MATERIAL_TYPE");
           break;
         }
         dbrs.close();

         //fate
         query = "select * from catalog_add_fate where request_id = " + request_id.toString();
         query = query + " and part_id = "+part_id.toString();
         Vector fateV = new Vector();
         dbrs = db.doQuery(query);
         rs=dbrs.getResultSet();
         while (rs.next()){
             fateV.addElement(new Integer((int)rs.getInt("REQUEST_ID")));
             fateV.addElement(new Integer((int)rs.getInt("FATE_ID")));
             fateV.addElement(new Integer((int)rs.getInt("FATE_PERCENTAGE")));
             fateV.addElement(new Integer((int)rs.getInt("PART_ID")));
         }

         this.fate = (Vector) fateV.clone();

         //load for material or part depending on what is on
         CatalogAddRequest car = new CatalogAddRequest(db);
         car.setRequestId(this.request_id.intValue());
         car.load();

         if (car.getItemId() !=null && car.getItemId().intValue()>0){
         // // System.out.println("going part item"+car.getItemId()+"   part"+part_id);
            this.loadPart(car.getItemId().intValue(),part_id.intValue());
         } else if (this.material_id!=null && this.material_id.intValue()>0){
         // // System.out.println("going mat "+material_id);
            this.loadMat(material_id.intValue());
         }

         // // System.out.println("here2");
      } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
      throw e;
      } finally{
         dbrs.close();
       }
      return;
   }

   public void loadMat(int mat_id)  throws Exception {
   /*
      SQLWKS> describe catalog_material_view
Column Name                    Null?    Type
------------------------------ -------- ----
MATERIAL_DESC                           VARCHAR2(80)
MFG_DESC                                VARCHAR2(60)
MATERIAL_ID                    NOT NULL NUMBER(38)
MATERIAL_TYPE                           VARCHAR2(0)
MSDS_ON_LINE                            CHAR(1)
   */
      material_id = new Integer(mat_id);
      String query = "select * from catalog_material_view where material_id = " + material_id.toString();
      // // System.out.println("query:"+query);
      DBResultSet dbrs = null;
      ResultSet rs = null;
      try {
         dbrs = db.doQuery(query);
         rs=dbrs.getResultSet();
         while (rs.next()){
           material_desc = rs.getString("MATERIAL_DESC");
           manufacturer = rs.getString("MFG_DESC");
           material_type = rs.getString("MATERIAL_TYPE");
           // // System.out.println("while:"+query);
         }

         // // System.out.println("done");
      } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
      } finally{
         dbrs.close();
       }
      return;
   }

   public void loadPart(int itemid,int partid)  throws Exception {
/*
SQLWKS> describe catalog_item_view
Column Name                    Null?    Type
------------------------------ -------- ----
ITEM_ID                        NOT NULL NUMBER(38)
PART_ID                        NOT NULL NUMBER(38)
MATERIAL_DESC                           VARCHAR2(80)
MFG_DESC                                VARCHAR2(60)
MATERIAL_ID                             NUMBER(38)
MATERIAL_TYPE                           VARCHAR2(0)
PART_SIZE                               NUMBER
SIZE_UNIT                               VARCHAR2(30)
PKG_STYLE                               VARCHAR2(30)
MSDS_ON_LINE                            CHAR(1)
*/
      part_id = new Integer(partid);
      String query = "select * from catalog_item_view where item_id = " + (new Integer(itemid)).toString();
      query = query + " and part_id = " + part_id.toString();
      DBResultSet dbrs = null;
      ResultSet rs = null;

      try {
         dbrs = db.doQuery(query);
         rs=dbrs.getResultSet();
         while (rs.next()){
           material_desc = rs.getString("MATERIAL_DESC");
           manufacturer = rs.getString("MFG_DESC");
           material_id = new Integer((int)rs.getInt("MATERIAL_ID"));
           part_size = new Float((float)rs.getFloat("PART_SIZE"));
           //System.out.println("Float:"+part_size);
           size_unit = rs.getString("SIZE_UNIT");
           pkg_style = rs.getString("PKG_STYLE");
           material_type = rs.getString("MATERIAL_TYPE");
             // // System.out.println("on load mat mat id:"+this.getMaterialId()+"  part:"+this.part_id);
         }


      } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
      throw e;
      } finally{
         dbrs.close();
       }
      return;
   }




   public void insert()  throws Exception {
     String dummy = new String("");
     String query = new String("insert into catalog_add_item (request_id,part_id)");
     query = query + " values (" + request_id.toString() + "," + part_id.toString() + ")" ;
     try {
       db.doUpdate(query);
       setInsertStatus(true);
    } catch (Exception e) {
       e.printStackTrace();
       HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
       setInsertStatus(false);
       throw e;
    }
   }

   public void insert(String col,String val,int type)  throws Exception {
     Integer I;
     DBResultSet dbrs = null;
      ResultSet rs = null;

     int records = this.getCount(part_id);
     if (records < 1){
        this.insert();
     }

     String query = new String("update catalog_add_item set " + col + " = ");
     switch (type) {
       case INT:
         query = query + val;
         break;
       case DATE:
         try {
           String q ="select to_char(sysdate,'HH24:Mi:SS') from dual";
           dbrs = db.doQuery(q);
           rs=dbrs.getResultSet();
           if (rs.next()) {
	            val = val + " " + rs.getString(1);
           }
         } catch (Exception e){ throw e;}
           finally{
           dbrs.close();
         }
         if (val.equals("nowDate")){
           query = query + " SYSDATE ";
         } else {
           query = query + " to_date('"+val+"','MM/dd/yyyy HH24:MI:SS')";
         }
         break;
       case STRING:
         query = query + "'" + val + "'";
         break;
       default:
         query = query + "'" + val + "'";
         break;
     }
     query = query + " where request_id = " + request_id.toString() + " and part_id = " + part_id.toString();
     try {
       db.doUpdate(query);
       setInsertStatus(true);
    } catch (Exception e) {
       e.printStackTrace();
       HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
       setInsertStatus(false);
       throw e;
    }
   }

   public void deleteFate()  throws Exception {
     Integer I;
     String query = new String("delete catalog_add_fate where request_id = " + request_id.toString());
     try {
       db.doUpdate(query);
     } catch (Exception e) {
       e.printStackTrace();
       HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
     }
   }

   public void insertFate(Vector fate)  throws Exception {
     Integer I;
     String query = new String("");
     for (int i=0;i<fate.size();i=i+2){  // last row is just a total
       Integer fate_id = this.getVVFateId(fate.elementAt(i).toString());
       if (fate_id == null ) continue;  // avoid total...
       query = " insert into catalog_add_fate (REQUEST_ID,FATE_ID,FATE_PERCENTAGE,PART_ID) ";
       query = query + " values ("+request_id.toString()+","+fate_id+","+fate.elementAt(i+1).toString()+","+part_id.toString()+")";
       try {
         db.doUpdate(query);
         setInsertStatus(true);
       } catch (Exception e) {
         e.printStackTrace();
         HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
         setInsertStatus(false);
         throw e;
       }
     }
   }


   public void setInsertStatus(boolean b) {
     this.insertStatus = b;
   }

   public boolean getInsertStatus() {
     return this.insertStatus;
   }


   public void commit()  throws Exception  {
     return;
   }

   public void rollback()  throws Exception  {
     return;
   }


   public int  getCount(Integer partId)  throws Exception {
     String query = "select count(*) from catalog_add_item where request_id = "+request_id.toString();
     query = query +"  and part_id =  "+ partId.toString();
     DBResultSet dbrs = null;
      ResultSet rs = null;
     int i = -1;
     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()) {
         i = (int)rs.getInt(1);
         break;
       }


      } catch (Exception e) {
       e.printStackTrace();
       HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
       throw e;
     } finally{
         dbrs.close();
       }
     return i;
   }

   public int  getCount()  throws Exception {
     String query = "select count(*) from catalog_add_item where request_id = "+request_id.toString();
     DBResultSet dbrs = null;
      ResultSet rs = null;
     int i = -1;
     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()) {
         i = (int)rs.getInt(1);
         break;
       }

      } catch (Exception e) {
       e.printStackTrace();
       HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
       throw e;
     } finally{
         dbrs.close();
     }
     return i;
   }

   public Vector retrieve()  throws Exception {
      String query = "select part_id from catalog_add_item where request_id = " + request_id.toString();
      Vector result = new Vector();
      DBResultSet dbrs = null;
      DBResultSet dbrs2 = null;
      ResultSet rs  = null;
      ResultSet rs2 = null;
      try {
         dbrs = db.doQuery(query);
         rs=dbrs.getResultSet();
         while (rs.next()){
           result.addElement(new Integer((int)rs.getInt("PART_ID")));
         }

      } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
      throw e;
      } finally{
         dbrs.close();
       }
      Vector result1 = new Vector();
      for (int i=0;i<result.size();i++){
         CatalogAddItem cai = new CatalogAddItem(db);
         cai.setRequestId(request_id.intValue());
         cai.setPartId(new Integer(result.elementAt(i).toString()));
         cai.load();
         result1.addElement(cai);
      }
      return result1;
   }


   public CatalogAddItem cloneVars()  {
     CatalogAddItem cai = new CatalogAddItem();
     cai.request_id = request_id   ;
     cai.material_desc =  material_desc  ;
     cai.manufacturer = manufacturer ;
     cai.mfg_catalog_id = mfg_catalog_id ;
     cai.material_id =   material_id  ;
     cai.part_size = part_size    ;
     cai.size_unit = size_unit ;
     cai.pkg_style =  pkg_style   ;
     cai.material_approved_by =   material_approved_by ;
     cai.material_approved_on = material_approved_on;
     cai.item_approved_by = item_approved_by     ;
     cai.item_approved_on = item_approved_on   ;
     cai.part_id =  part_id      ;
     cai.material_type = material_type  ;
     return cai;
   }


   public String getVVFateDesc(int id) throws Exception {
      String query = "select FATE_DESC from vv_fate where fate_id = "+id;
      DBResultSet dbrs = null;
      ResultSet rs = null;
      String d = new String();
      try {
         dbrs = db.doQuery(query);
         rs=dbrs.getResultSet();
         while (rs.next()){
          d = rs.getString("FATE_DESC");
         }

     } catch (Exception e) {
       throw e;
     } finally{
         dbrs.close();
       }
     return d;
   }

   public Integer getVVFateId(String desc) throws Exception {
      String query = "select FATE_ID from vv_fate where lower(fate_desc) like lower('%"+desc+"%')";
      DBResultSet dbrs = null;
      ResultSet rs = null;
      Integer d = null;
      try {
         dbrs = db.doQuery(query);
         rs=dbrs.getResultSet();
         while (rs.next()){
          d = new Integer(rs.getInt("FATE_ID"));
         }

     } catch (Exception e) {
       throw e;
     } finally{
         dbrs.close();
       }
     return d;
   }

}
