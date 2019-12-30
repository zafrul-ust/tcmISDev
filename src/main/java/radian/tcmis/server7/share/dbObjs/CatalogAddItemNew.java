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
import radian.tcmis.both1.helpers.*;
import radian.tcmis.server7.share.db.*;


import java.util.*;
import java.sql.*;

public class CatalogAddItemNew {
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
   protected String dimension = null;
   protected String netwt = null;
   protected String netwt_unit = null;
   protected String trade = null;
   protected String grade = null;
   protected Vector fate = null;
   protected String customerMSDS = null;
   protected String numberPerPart = null;
   protected String sampleSizing = null;

   protected String shelf_life = null;
   protected String shelf_life_unit = null;
   protected String shelf_life_basis = null;
   protected String storage_temp = null;
   protected String label_color = null;


   public static final int STRING = 0;
   public static final int INT = 1;
   public static final int DATE = 2;

   protected boolean insertStatus;

   public CatalogAddItemNew()   {
     insertStatus = false;
   }

   public CatalogAddItemNew(TcmISDBModel db)  throws java.rmi.RemoteException {
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

   public String getTrade(){
      return trade;
   }
   public void  setTrade(String s){
      trade = s;
   }

   public String getMfgCatID(){
      return mfg_catalog_id;
   }
   public void  setMfgCatID(String s){
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

   public String getDimension(){
      return dimension;

   }
   public void setDimension(String s){
      dimension = s;

   }

   public String getNetWt(){
      return netwt;

   }
   public void setNetWt(String s){
      netwt = s;

   }

   public String getNetWtUnit(){
      return netwt_unit;

   }
   public void setNetWtUnit(String s){
      netwt_unit = s;

   }

   public String getGrade(){
      return grade;
   }

   public void setGrade(String s){
      grade = s;
   }

   public Vector getFate(){
      return fate;
   }
   public void setFate(Vector s){
      fate = s;
   }

   public void setCustomerMSDS(String s) {
    customerMSDS = s;
   }
   public String getCustomerMSDS() {
    return customerMSDS;
   }
   public void setNumberPerPart(String s) {
    numberPerPart = s;
   }
   public String getNumberPerPart() {
    return numberPerPart;
   }
   public void setSampleSizing(String s) {
    sampleSizing = s;
   }
   public String getSampleSizing() {
    return sampleSizing;
   }

   public void setShelfLife(String D) {
     this.shelf_life = D;
   }
   public String getShelfLife() {
     return shelf_life;
   }

   public void setShelfLifeUnit(String D) {
     this.shelf_life_unit = D;
   }
   public String getShelfLifeUnit() {
     return shelf_life_unit;
   }

   public void setShelfLifeBasis(String D) {
     this.shelf_life_basis = D;
   }
   public String getShelfLifeBasis() {
     return shelf_life_basis;
   }
   public void setStorageTemp(String  D) {
     this.storage_temp = D;
   }
   public String getStorageTemp() {
     return storage_temp;
   }
   public void setLabelColor(String  D) {
     this.label_color = D;
   }
   public String getLabelColor() {
     return label_color;
   }

   //1-12-02
   public void setCustomerMSDSFromSeagatePartStageView(String catID,String catPartNo,int reqID) throws Exception {
    String query = "update catalog_add_item set customer_msds_number = (select msds_number from seagate_part_stage_view";
    //query += " where seagate_part_number = '"+catPartNo+"' and catalog_id = '"+catID+"' group by seagate_part_number,catalog_id)";
    query += " where seagate_part_number = '"+catPartNo+"' and catalog_id = '"+catID+"' and rownum = 1)";
    query += " where line_item = 1 and request_id = "+reqID;
    try {
      db.doUpdate(query);
    }catch (Exception e) {
      e.printStackTrace();
    }
   }


   //trong 3/7/00
    public Vector getPartInfoForEvalRequest(int requestId) throws Exception{
      Vector v = new Vector();
      String query = "select nvl(to_char(part_id),' ') part_id,nvl(grade,' ') grade,part_size||' '||size_unit||' '||pkg_style packaging,nvl(material_desc,' ') material_desc"+
                     " from catalog_add_item where line_item = 1 and request_id = " + requestId;
      DBResultSet dbrs = null;
      ResultSet rs = null;

      try {
         dbrs = db.doQuery(query);
         rs=dbrs.getResultSet();
         while(rs.next()){
           Hashtable h = new Hashtable();
           h.put("PART_ID",rs.getString("part_id"));
           h.put("GRADE",rs.getString("grade"));
           h.put("PKG",rs.getString("packaging"));
           h.put("DESC",rs.getString("material_desc"));
           v.addElement(h);
         }
       } catch (Exception e) {
       e.printStackTrace();
       HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
       throw e;
       } finally{
         dbrs.close();
       }
      return v;
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
     NChem.deleteChildreen(db," where request_id = " + request_id.toString());

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
      String query = "select * from catalog_add_item where line_item = 1 and request_id = " + request_id.toString();
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
           material_id = new Integer(BothHelpObjs.makeZeroFromNull(rs.getString("MATERIAL_ID")));     //5-08-01
           part_size = new Float((float)rs.getFloat("PART_SIZE"));
           size_unit = rs.getString("SIZE_UNIT");
           pkg_style = rs.getString("PKG_STYLE");
           material_approved_by = new Integer((int)rs.getInt("MATERIAL_APPROVED_BY"));
           material_approved_on = rs.getString("MATERIAL_APPROVED_ON");
           item_approved_by = new Integer((int)rs.getInt("ITEM_APPROVED_BY"));
           item_approved_on = rs.getString("ITEM_APPROVED_ON");
           part_id = new Integer((int)rs.getInt("PART_ID"));
           material_type = rs.getString("MATERIAL_TYPE");
           grade = rs.getString("GRADE");
           netwt = rs.getString("NETWT");
           netwt_unit = rs.getString("NETWT_UNIT");
           trade = rs.getString("MFG_TRADE_NAME");
           dimension = rs.getString("DIMENSION");
           customerMSDS = BothHelpObjs.makeBlankFromNull(rs.getString("customer_msds_number"));
           numberPerPart = BothHelpObjs.makeBlankFromNull(rs.getString("components_per_item"));
           sampleSizing = BothHelpObjs.makeBlankFromNull(rs.getString("sample_only"));
           String shelfLife = BothHelpObjs.makeBlankFromNull(rs.getString("shelf_life_days"));
           shelf_life = shelfLife;
           if ("-1".equalsIgnoreCase(shelfLife)) {
             shelf_life_unit = "Indefinite";
           }else {
             shelf_life_unit = "days";
           }
           shelf_life_basis = BothHelpObjs.makeBlankFromNull(rs.getString("SHELF_LIFE_BASIS"));
           storage_temp = BothHelpObjs.makeBlankFromNull(rs.getString("STORAGE_TEMP"));
           label_color = BothHelpObjs.makeBlankFromNull(rs.getString("LABEL_COLOR"));
         } //end of while loop

         //fate
         query = "select * from catalog_add_fate_new where request_id = " + request_id.toString();
         query = query + " and part_id = "+part_id.toString();
         Vector fateV = new Vector();
         dbrs = db.doQuery(query);
         rs=dbrs.getResultSet();
         while (rs.next()){
             fateV.addElement(rs.getString("FACILITY_ID"));
             fateV.addElement(rs.getString("work_area"));
             fateV.addElement(rs.getString("user_group_id"));
             fateV.addElement(new Integer((int)rs.getInt("REQUEST_ID")));
             fateV.addElement(new Integer((int)rs.getInt("FATE_ID")));
             fateV.addElement(new Integer((int)rs.getInt("FATE_PERCENTAGE")));
             fateV.addElement(new Integer((int)rs.getInt("PART_ID")));
         }
         this.fate = (Vector) fateV.clone();
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
    BUNDLE                                  VARCHAR2(2)
    QUANTITY_PER_BUNDLE                     NUMBER
    PART_ID                        NOT NULL NUMBER(38)
    COMPONENTS_PER_ITEM                     NUMBER
    SIZE_VARIES                             VARCHAR2(1)
    MATERIAL_DESC                           VARCHAR2(80)
    MFG_DESC                                VARCHAR2(60)
    MATERIAL_ID                             NUMBER(38)
    PART_SIZE                               NUMBER
    SIZE_UNIT                               VARCHAR2(30)
    PKG_STYLE                               VARCHAR2(30)
    DIMENSION                               VARCHAR2(40)
    MFG_PART_NO                             VARCHAR2(30)
    MSDS_ON_LINE                            VARCHAR2(1)
    TRADE_NAME                              VARCHAR2(80)
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
           //material_type = rs.getString("MATERIAL_TYPE");
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
     String query = new String("insert into catalog_add_item (request_id,line_item,part_id)");
     query = query + " values (" + request_id.toString() + ",1," + part_id.toString() + ")" ;
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
         query = query + "'" + BothHelpObjs.changeSingleQuotetoTwoSingleQuote(val) + "'";
         break;
       default:
         query = query + "'" + BothHelpObjs.changeSingleQuotetoTwoSingleQuote(val) + "'";
         break;
     }
     query = query + " where line_item = 1 and request_id = " + request_id.toString() + " and part_id = " + part_id.toString();
     try {
       //System.out.println("ERRROR: "+query);
       db.doUpdate(query);
       setInsertStatus(true);
    } catch (Exception e) {
       e.printStackTrace();
       HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
       setInsertStatus(false);
       throw e;
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
     query = query +" and line_item = 1 and part_id =  "+ partId.toString();
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
     String query = "select count(*) from catalog_add_item where line_item = 1 and request_id = "+request_id.toString();
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
      String query = "select part_id from catalog_add_item where line_item = 1 and request_id = " + request_id.toString();
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
         CatalogAddItemNew cai = new CatalogAddItemNew(db);
         cai.setRequestId(request_id.intValue());
         cai.setPartId(new Integer(result.elementAt(i).toString()));
         cai.load();
         result1.addElement(cai);
      }
      return result1;
   }


   //trong
   public Vector retrieveEvalData() throws Exception{
    Vector result = new Vector();
    String query = "select * from catalog_add_item where line_item = 1 and request_id = " +this.getRequestId();
    query += " order by part_id";
    DBResultSet dbrs = null;
    ResultSet rs  = null;
   int i = 0;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while (rs.next()){
        Hashtable h = new Hashtable();
		  h.put("LINE_ITEM",BothHelpObjs.makeBlankFromNull(rs.getString("line_item")));
		  h.put("ITEM_ID",BothHelpObjs.makeBlankFromNull(rs.getString("item_id")));
		  h.put("PART_ID",BothHelpObjs.makeBlankFromNull(rs.getString("part_id")));
        h.put("MATERIAL_DESC",BothHelpObjs.makeBlankFromNull(rs.getString("material_desc")));
        h.put("MATERIAL_ID",BothHelpObjs.makeBlankFromNull(rs.getString("material_id")));
        h.put("MANUFACTURER",BothHelpObjs.makeBlankFromNull(rs.getString("manufacturer")));
        h.put("COMPONENTS_PER_ITEM",BothHelpObjs.makeBlankFromNull(rs.getString("components_per_item")));
        h.put("PART_SIZE",BothHelpObjs.makeBlankFromNull(rs.getString("part_size")));
        h.put("SIZE_UNIT",BothHelpObjs.makeBlankFromNull(rs.getString("size_unit")));
        h.put("PKG_STYLE",BothHelpObjs.makeBlankFromNull(rs.getString("pkg_style")));
        h.put("DIMENSION",BothHelpObjs.makeBlankFromNull(rs.getString("dimension")));
        h.put("GRADE",BothHelpObjs.makeBlankFromNull(rs.getString("grade")));
        h.put("MFG_TRADE_NAME",BothHelpObjs.makeBlankFromNull(rs.getString("mfg_trade_name")));
        h.put("NETWT",BothHelpObjs.makeBlankFromNull(rs.getString("netwt")));
        h.put("NETWT_UNIT",BothHelpObjs.makeBlankFromNull(rs.getString("netwt_unit")));
        String shelfLife = BothHelpObjs.makeBlankFromNull(rs.getString("shelf_life_days"));
        h.put("SHELF_LIFE",shelfLife);
        if ("-1".equalsIgnoreCase(shelfLife)) {
          h.put("SHELF_LIFE_UNIT", "Indefinite");
        }else {
          h.put("SHELF_LIFE_UNIT", "days");
        }
        h.put("SHELF_LIFE_BASIS",BothHelpObjs.makeBlankFromNull(rs.getString("shelf_life_basis")));
        h.put("STORAGE_TEMP",BothHelpObjs.makeBlankFromNull(rs.getString("storage_temp")));
        h.put("LABEL_COLOR",BothHelpObjs.makeBlankFromNull(rs.getString("label_color")));
		  h.put("VENDOR_CONTACT_NAME",BothHelpObjs.makeBlankFromNull(rs.getString("vendor_contact_name")));
		  h.put("VENDOR_CONTACT_EMAIL",BothHelpObjs.makeBlankFromNull(rs.getString("vendor_contact_email")));
		  h.put("VENDOR_CONTACT_PHONE",BothHelpObjs.makeBlankFromNull(rs.getString("vendor_contact_phone")));
		  h.put("VENDOR_CONTACT_FAX",BothHelpObjs.makeBlankFromNull(rs.getString("vendor_contact_fax")));
		  h.put("SUGGESTED_VENDOR",BothHelpObjs.makeBlankFromNull(rs.getString("suggested_vendor")));
		  result.addElement(h);
      }

      } catch (Exception e) {
      e.printStackTrace();
      HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
      throw e;
      } finally{
         dbrs.close();
      }
      return result;
   }

   public CatalogAddItemNew cloneVars()  {
     CatalogAddItemNew cai = new CatalogAddItemNew();
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

   public void updateDataFromPerviousRequest(Integer reqID, Hashtable h) {
     try {
       boolean runUpdate = false;
       String query = "update catalog_add_item set ";
       String where = " where line_item = "+(String)h.get("LINE_ITEM")+" and request_id = "+reqID.intValue()+" and part_id = "+(String)h.get("PART_ID");

       String tempString = (String)h.get("MATERIAL_DESC");
       tempString = BothHelpObjs.changeSingleQuotetoTwoSingleQuote(tempString);
       if (tempString.length() > 0 ) {
         query += "material_desc = '"+tempString+"',";
         runUpdate = true;
       }
       tempString = (String)h.get("MATERIAL_ID");
       if (tempString.length() > 0 ) {
         query += "material_id = "+tempString+",";
         runUpdate = true;
       }
       tempString = (String)h.get("MANUFACTURER");
       tempString = BothHelpObjs.changeSingleQuotetoTwoSingleQuote(tempString);
       if (tempString.length() > 0 ) {
         query += "manufacturer = '"+tempString+"',";
         runUpdate = true;
       }
       tempString = (String)h.get("COMPONENTS_PER_ITEM");
       if (tempString.length() > 0 ) {
         query += "components_per_item = "+tempString+",";
         runUpdate = true;
       }
       tempString = (String)h.get("PART_SIZE");
       if (tempString.length() > 0 ) {
         query += "part_size = "+tempString+",";
         runUpdate = true;
       }
       tempString = (String)h.get("SIZE_UNIT");
       if (tempString.length() > 0 ) {
         query += "size_unit = '"+tempString+"',";
         runUpdate = true;
       }
       tempString = (String)h.get("PKG_STYLE");
       if (tempString.length() > 0 ) {
         query += "pkg_style = '"+tempString+"',";
         runUpdate = true;
       }
       tempString = (String)h.get("DIMENSION");
       tempString = BothHelpObjs.changeSingleQuotetoTwoSingleQuote(tempString);
       if (tempString.length() > 0 ) {
         query += "dimension = '"+tempString+"',";
         runUpdate = true;
       }
       tempString = (String)h.get("GRADE");
       tempString = BothHelpObjs.changeSingleQuotetoTwoSingleQuote(tempString);
       if (tempString.length() > 0 ) {
         query += "grade = '"+tempString+"',";
         runUpdate = true;
       }
       tempString = (String)h.get("MFG_TRADE_NAME");
       tempString = BothHelpObjs.changeSingleQuotetoTwoSingleQuote(tempString);
       if (tempString.length() > 0 ) {
         query += "mfg_trade_name = '"+tempString+"',";
         runUpdate = true;
       }
       tempString = (String)h.get("NETWT");
       if (tempString.length() > 0 ) {
         query += "netwt = "+tempString+",";
         runUpdate = true;
       }
       tempString = (String)h.get("NETWT_UNIT");
       if (tempString.length() > 0 ) {
         query += "netwt_unit = '"+tempString+"',";
         runUpdate = true;
       }
		 tempString = (String)h.get("ITEM_ID");
       if (tempString.length() > 0 ) {
         query += "item_id = '"+tempString+"',";
         runUpdate = true;
       }
		 tempString = (String)h.get("VENDOR_CONTACT_NAME");
       if (tempString.length() > 0 ) {
         query += "vendor_contact_name = '"+tempString+"',";
         runUpdate = true;
       }
		 tempString = (String)h.get("VENDOR_CONTACT_EMAIL");
       if (tempString.length() > 0 ) {
         query += "vendor_contact_email = '"+tempString+"',";
         runUpdate = true;
       }
		 tempString = (String)h.get("VENDOR_CONTACT_PHONE");
       if (tempString.length() > 0 ) {
         query += "vendor_contact_phone = '"+tempString+"',";
         runUpdate = true;
       }
		 tempString = (String)h.get("VENDOR_CONTACT_FAX");
       if (tempString.length() > 0 ) {
         query += "vendor_contact_fax = '"+tempString+"',";
         runUpdate = true;
       }
		 tempString = (String)h.get("SUGGESTED_VENDOR");
       if (tempString.length() > 0 ) {
         query += "suggested_vendor = '"+tempString+"',";
         runUpdate = true;
       }

		 if (runUpdate) {
         //remove the last commas
         query = query.substring(0,query.length()-1);
         db.doUpdate(query+where);
       }
     } catch (Exception e) {
       e.printStackTrace();
     }
   } //end of method

   public void setDefaultShelfNStorageTemp(int reqID, String itemID) throws Exception {
     try {
       String query = "update catalog_add_item cai set (shelf_life_days,shelf_life_basis,storage_temp,label_color) = "+
                      "(select c.shelf_life_days,c.shelf_life_basis,c.storage_temp,c.label_color from catalog_add_request_new a,"+
                      "facility b, component_inventory_group c where a.request_id = "+reqID+" and c.item_id = "+itemID+" and a.eng_eval_facility_id = b.facility_id"+
                      " and b.inventory_group_default = c.inventory_group and cai.part_id = c.part_id) where cai.line_item = 1 and cai.request_id = "+reqID;
       db.doUpdate(query);
		 query = "update catalog_add_item set item_id = "+itemID+" where line_item = 1 and request_id = "+reqID;
		 db.doUpdate(query);
	  }catch (Exception e) {
       e.printStackTrace();
       throw e;
     }
   } //end of method
} //end of class