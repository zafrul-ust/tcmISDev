/*
SQLWKS> describe catalog_add_request
Column Name                    Null?    Type
------------------------------ -------- ----
REQUEST_ID                     NOT NULL NUMBER
REQUESTOR                               NUMBER
REQUEST_DATE                            DATE
REQUEST_STATUS                          NUMBER
FACILITY_ID                             VARCHAR2(30)
FAC_PART_NO                             VARCHAR2(30)
STOCKED                                 VARCHAR2(8)
ANNUAL_USAGE                            NUMBER
SHELF_LIFE                              NUMBER
SHELF_LIFE_UNIT                         VARCHAR2(30)
SHELF_LIFE_BASIS                        VARCHAR2(1)
SPEC_NAME                               VARCHAR2(30)
SPEC_VERSION                            VARCHAR2(15)
SPEC_AMENDMENT                          VARCHAR2(10)
STORAGE_TEMP                            VARCHAR2(30)
STORAGE_TEMP_UNIT                       VARCHAR2(8)
C_OF_C                                  VARCHAR2(1)
C_OF_A                                  VARCHAR2(1)
USER_GROUP_ID                           VARCHAR2(30)
APPLICATION                             VARCHAR2(30)
QUANTITY                                NUMBER
PERIOD                                  NUMBER
PERIOD_UNIT                             VARCHAR2(30)
QUANTITY2                               NUMBER
PERIOD2                                 NUMBER
PERIOD_UNIT2                            VARCHAR2(30)
PROCESS_TITLE                           VARCHAR2(80)
PROCESS_DESC                            VARCHAR2(400)
MATERIALS_REPLACED                      VARCHAR2(60)
STORAGE_LOCATIONS                       VARCHAR2(60)
IMPORT_EXPORT                           VARCHAR2(1)
MONTHLY_USAGE                           NUMBER
MAX_ON_SITE                             NUMBER
ENVIRONMENTAL_EVAL                      VARCHAR2(400)
HEALTH_SAFETY_EVAL                      VARCHAR2(400)
ITEM_ID                                 NUMBER
STARTING_VIEW                           NUMBER
SUGGESTED_VENDOR                        VARCHAR2(80)
VENDOR_PART_NO                          VARCHAR2(60)
REQUEST_REJECTED                        VARCHAR2(1)
PROC_SPECIAL_INSTR                      VARCHAR(400)
CATALOG_ID                               VARCHAR2(30)



SQLWKS> describe catalog_add_compatibility
Column Name                    Null?    Type
------------------------------ -------- ----
REQUEST_ID                              NUMBER
FAC_PART_NO                             VARCHAR2(40)
PART_DESC                               VARCHAR2(120)

*/

package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.server7.share.db.*;
 

import java.util.*;
import java.sql.*;

public class CatalogAddRequest {
   protected TcmISDBModel db;
   protected Integer request_id = null;
   protected Integer requestor = null;
   protected String request_date = null;
   protected Integer request_status = null;
   protected String facility_id = null;
   protected String fac_part_no = null;
   protected String stocked = null;
   protected Integer annual_usage = null;
   protected String shelf_life = null;
   protected String shelf_life_unit = null;
   protected String shelf_life_basis = null;
   protected String spec_name = null;
   protected String spec_version = null;
   protected String spec_amendment = null;
   protected String storage_temp = null;
   protected String storage_temp_unit = null;
   protected String c_of_c = null;
   protected String c_of_a = null;
   protected String user_group = null;
   protected String application = null;
   protected Integer quantity = null;
   protected Integer period = null;
   protected String period_unit = null;
   protected Integer quantity2 = null;
   protected Integer period2 = null;
   protected String period_unit2 = null;
   protected String catalog_id = null;
   
   protected String process_title = null;
   protected String process_desc = null;
   protected String materials_replaced = null;
   protected String storage_locations = null;
   protected String import_export = null;
   protected Integer monthly_usage = null;
   protected Integer max_on_site = null;
   protected String environmental_eval = null;
   protected String health_safety_eval = null;
   protected Integer starting_view = null;
   protected String suggested_vendor = null;
   protected String vendor_part_no = null;
   protected String request_rejected = null;
   protected String proc_special_instr = null;


   protected Integer item_id = null;

   protected Vector compatibility = null;


   public static final int STRING = 0;
   public static final int INT = 1;
   public static final int DATE = 2;

    // request levels
   static final int NEW_MATERIAL = 0;
  static final int NEW_SIZE_PACK = 1;
  static final int NEW_PART = 2;
  static final int NEW_APPROVAL = 3;
  static final int NEW_GROUP = 4;
  static final int PENDING_APPROVAL = 5;
  static final int DRAFT = 6;

  static final int SPEC_VALUES = 4;

   protected boolean insertStatus;
   
   public CatalogAddRequest()  throws java.rmi.RemoteException {
     insertStatus = false;
   }

   public CatalogAddRequest(TcmISDBModel db)  throws java.rmi.RemoteException {
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

   public void setRequestor(int id) {
     this.requestor = new Integer(id);
   }
   public Integer getRequestor() {
     return requestor;
   }

   public void setRequestDate(String D) {
     this.request_date = D;
   }
   public String getRequestDate() {
     return request_date;
   }

   public void setRequestStatus(Integer D) {
     this.request_status = D;
   }
   public Integer getRequestStatus() {
     return request_status;
   }

   public void setFacilityId(String D) {
     this.facility_id = D;
   }
   public String getFacilityId() {
     return facility_id;
   }

   public void setFacPartNum(String D) {
     this.fac_part_no = D;
   }
   public String getFacPartNum() {
     return fac_part_no;
   }

   public void setStocked(String D) {
     this.stocked = D;
   }
   public String getStocked() {
     return stocked;
   }

   public void setAnnualUsage(Integer D) {
     this.annual_usage = D;
   }
   public Integer getAnnualUsage() {
     return annual_usage;
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

   public void setSpecName(String D) {
     this.spec_name = D;
   }
   public String getSpecName() {
     return spec_name;
   }

   public void setSpecVersion(String D) {
     this.spec_version = D;
   }
   public String getSpecVersion() {
     return spec_version;
   }

   public void setSpecAmendment(String D) {
     this.spec_amendment = D;
   }
   public String getSpecAmendment() {
     return spec_amendment;
   }

   public void setStorageTemp(String  D) {
     this.storage_temp = D;
   }
   public String getStorageTemp() {
     return storage_temp;
   }

   public void setStorageTempUnit(String D) {
     this.storage_temp_unit = D;
   }
   public String getStorageTempUnit() {
     return storage_temp_unit;
   }

   public void setCOfC(String D) {
     this.c_of_c = D;
   }
   public String getCOfC() {
     return c_of_c;
   }

   public void setCOfA(String D) {
     this.c_of_a = D;
   }
   public String getCOfA() {
     return c_of_a;
   }

   public void setUserGroup(String D) {
     this.user_group = D;
   }
   public String getUserGroup() {
     return user_group;
   }

   public void setApplication(String D) {
     this.application = D;
   }
   public String getApplication() {
     return application;
   }

   public void setQuantity(Integer D) {
     this.quantity = D;
   }
   public Integer getQuantity() {
     return quantity;
   }

   public void setPeriod(Integer D) {
     this.period = D;
   }
   public Integer getPeriod() {
     return period;
   }

   public void setPeriodUnit(String D) {
     this.period_unit = D;
   }
   public String getPeriodUnit() {
     return period_unit;
   }

   public void setQuantity2(Integer D) {
     this.quantity2 = D;
   }
   public Integer getQuantity2() {
     return quantity2;
   }

   public void setPeriod2(Integer D) {
     this.period2 = D;
   }
   public Integer getPeriod2() {
     return period2;
   }

   public void setPeriodUnit2(String D) {
     this.period_unit2 = D;
   }
   public String getPeriodUnit2() {
     return period_unit2;
   }

   public void setProcessTitle(String D) {
     this.process_title = D;
   }
   public String getProcessTitle() {
     return process_title;
   }

   public void setProcessDesc(String D) {
     this.process_desc = D;
   }
   public String getProcessDesc() {
     return process_desc;
   }

   public void setMaterialsReplaced(String D) {
     this.materials_replaced = D;
   }
   public String getMaterialsReplaced() {
     return materials_replaced;
   }

   public void setStorageLocations(String D) {
     this.storage_locations = D;
   }
   public String getStorageLocations() {
     return storage_locations;
   }

   public void setImportExport(String D) {
     this.import_export = D;
   }
   public String getImportExport() {
     return import_export;
   }

   public void setMonthlyUsage(Integer D) {
     this.monthly_usage = D;
   }
   public Integer getMonthlyUsage() {
     return monthly_usage;
   }

   public void setMaxOnSite(Integer D) {
     this.max_on_site = D;
   }
   public Integer getMaxOnSite() {
     return max_on_site;
   }

   public void setEnvironmentalEval(String D) {
     this.environmental_eval = D;
   }
   public String getEnvironmentalEval() {
     return environmental_eval;
   }

   public void setHealthSafetyEval(String D) {
     this.health_safety_eval = D;
   }
   public String getHealthSafetyEval() {
     return health_safety_eval;
   }

   public void setItemId(Integer D) {
     this.item_id = D;
   }
   public Integer getItemId() {
     return item_id;
   }

   public void setStartingView(Integer D) {
     this.starting_view = D;
   }
   public Integer getStartingView() {
     return starting_view;
   }

   public void setSuggestedVendor(String D) {
     this.suggested_vendor = D;
   }
   public String getSuggestedVendor() {
     return suggested_vendor;
   }
   public void setVendorPartNo(String D) {
     this.vendor_part_no = D;
   }
   public String getVendorPartNo() {
     return vendor_part_no;
   }
   public void setRequestRejected(String D) {
     this.request_rejected = D;
   }
   public String getRequestRejected() {
     return request_rejected;
   }
    public void setProcSpecInst(String D) {
     this.proc_special_instr = D;
   }
   public String getProcSpecInst() {
     return this.proc_special_instr;
   }

   public void setCatalogId(String D) {
     this.catalog_id = D;
   }
   public String getCatalogId() {
     return this.catalog_id;
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

   /*
   SQL> describe catalog_add_spec;
 Name                            Null?    Type
 ------------------------------- -------- ----
 SPEC_ID                         NOT NULL VARCHAR2(30)
 REQUEST_ID                      NOT NULL NUMBER
 SPEC_NAME                                VARCHAR2(30)
 SPEC_TITLE                      NOT NULL VARCHAR2(100)
 SPEC_VERSION                             VARCHAR2(15)
 SPEC_AMENDMENT                           VARCHAR2(10)
 SPEC_DATE                                DATE
 CONTENT                                  VARCHAR2(80)
 ON_LINE                                  CHAR(1)
   */
   public Vector getSpecV() throws Exception {

       // get all spec id for fac_part_no and spec_id as part_no
       String query = new String("select spec_id from fac_spec ");
       query = query + " where facility_id='"+this.getFacilityId()+
                       "' and fac_part_no='"+this.getFacPartNum()+"'";
       DBResultSet dbrs = null;
      ResultSet rs = null;
       Hashtable h = new Hashtable();
       Vector resV = new Vector();
       //// System.out.println("query1="+query);
       try {
         dbrs = db.doQuery(query);
         rs=dbrs.getResultSet();
         while (rs.next()) {
           resV = new Vector();
           resV.addElement(rs.getString("SPEC_ID")==null?" ":rs.getString("SPEC_ID"));
           h.put(rs.getString("SPEC_ID").toString(),resV);
           //// System.out.println("Specv from ori:"+resV);
           break;
         }

        } catch (Exception e) {
         e.printStackTrace();
         HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
         throw e;
       } finally{
         dbrs.close();
       }


       // find on request the specid
       query = new String("select * from catalog_add_spec ");
       query = query + " where request_id="+this.request_id;
       //// System.out.println("query2="+query);
       rs = null;
       resV = new Vector();
       try {
         dbrs = db.doQuery(query);
         rs=dbrs.getResultSet();
         while (rs.next()) {
           resV = new Vector();
           resV.addElement(rs.getString("SPEC_ID")==null?" ":rs.getString("SPEC_ID"));
           resV.addElement(rs.getString("SPEC_VERSION")==null?" ":rs.getString("SPEC_VERSION"));
           resV.addElement(rs.getString("SPEC_AMENDMENT")==null?" ":rs.getString("SPEC_AMENDMENT"));
           resV.addElement("N"); // online not possible
           //// System.out.println("Specv from add:"+resV);
           h.put(rs.getString("SPEC_ID").toString(),resV);
         }
         
        } catch (Exception e) {
         e.printStackTrace();
         HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
         throw e;
      } finally{
         dbrs.close();
       }

       // the real table
       /*
       SQL> describe spec;
 Name                            Null?    Type
 ------------------------------- -------- ----
 SPEC_ID                         NOT NULL VARCHAR2(30)
 SPEC_NAME                                VARCHAR2(30)
 SPEC_TITLE                      NOT NULL VARCHAR2(100)
 SPEC_VERSION                             VARCHAR2(15)
 SPEC_AMENDMENT                           VARCHAR2(10)
 SPEC_DATE                                DATE
 CONTENT                                  VARCHAR2(80)
 ON_LINE                                  CHAR(1)
 SPEC_LIBRARY                    NOT NULL VARCHAR2(20)
      */
       Enumeration E = null;
       Hashtable h1 = new Hashtable(); // clone
       for (E=h.keys(); E.hasMoreElements();){
         String k = new String(E.nextElement().toString());
         h1.put(k,h.get(k));
       }
       
       for (E=h.keys(); E.hasMoreElements();){
        String id = new String(E.nextElement().toString());
        query = new String("select * from spec ");
        query = query + " where spec_id='"+id+"'";
        //// System.out.println("query3="+query);
        rs = null;
        resV = new Vector();
        try {
          dbrs = db.doQuery(query);
          rs=dbrs.getResultSet();
          while (rs.next()) {
           resV = new Vector();
           resV.addElement(rs.getString("SPEC_ID")==null?" ":rs.getString("SPEC_ID"));
           resV.addElement(rs.getString("SPEC_VERSION")==null?" ":rs.getString("SPEC_VERSION"));
           resV.addElement(rs.getString("SPEC_AMENDMENT")==null?" ":rs.getString("SPEC_AMENDMENT"));
           resV.addElement(rs.getString("ON_LINE")==null?" ":rs.getString("ON_LINE"));
           //// System.out.println("Specv from ori 2:"+resV);
           h1.remove(id);
           h1.put(id,resV);
          }
          
        } catch (Exception e) {
          e.printStackTrace();
          HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
          throw e;
        } finally{
         dbrs.close();
       }
       }

       // add all together
       resV = new Vector();
       Vector temp = new Vector();
       for (E=h1.keys();E.hasMoreElements();){
          temp = new Vector();
          temp = (Vector) h1.get(E.nextElement().toString());
          for (int i=0;i<SPEC_VALUES;i++){
             String s = temp.elementAt(i).toString();
             resV.addElement(s==null?" ":s);
          }
       }
       //// System.out.println("Specv done:"+resV);
       return resV;
   }
  /*
  SQL> describe catalog_add_flow_down;
 Name                            Null?    Type
 ------------------------------- -------- ----
 REQUEST_ID                      NOT NULL NUMBER
 FLOW_DOWN                       NOT NULL VARCHAR2(10)
  */
   public Vector getFlowV() throws Exception {
       String query = new String("select * from catalog_add_flow_down ");
       query = query + " where request_id="+this.request_id;
       DBResultSet dbrs = null;
      ResultSet rs = null;
       Vector resV = new Vector();
       try {
         dbrs = db.doQuery(query);
         rs=dbrs.getResultSet();
         while (rs.next()) {
           resV.addElement(rs.getString("FLOW_DOWN")==null?" ":rs.getString("FLOW_DOWN"));
         }

        } catch (Exception e) {
         e.printStackTrace();
         HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
         throw e;
      } finally{
         dbrs.close();
       }
      return resV;
   }



   public void delete()  throws Exception {
     this.deleteComp(request_id);
     this.deleteSpec(request_id);
     this.deleteFlow(request_id);
     
     String query = "delete catalog_add_request where request_id = " + request_id.toString();
     try {
       db.doUpdate(query);
     } catch (Exception e) {
       e.printStackTrace();
        HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
         throw e;
     }
   }

   public void load()  throws Exception {
      String query = "select * from catalog_add_request where request_id = " + request_id.toString();

      DBResultSet dbrs = null;
      ResultSet rs = null;
      try {
         dbrs = db.doQuery(query);
         rs=dbrs.getResultSet();
         while (rs.next()){
           request_id = new Integer((int)rs.getInt("REQUEST_ID"));
           requestor = new Integer((int)rs.getInt("REQUESTOR"));
           request_date = rs.getString("REQUEST_DATE");
           facility_id = rs.getString("FACILITY_ID");
           fac_part_no = rs.getString("FAC_PART_NO");
           stocked = rs.getString("STOCKED");
           shelf_life = rs.getString("SHELF_LIFE");
           shelf_life_basis = rs.getString("SHELF_LIFE_BASIS");
           spec_name = rs.getString("SPEC_NAME");
           spec_version = rs.getString("SPEC_VERSION");
           spec_amendment = rs.getString("SPEC_AMENDMENT");
           storage_temp_unit = rs.getString("STORAGE_TEMP_UNIT");
           c_of_c = rs.getString("C_OF_C");
           c_of_a = rs.getString("C_OF_A");
           user_group = rs.getString("USER_GROUP_ID");
           application = rs.getString("APPLICATION");
           period_unit = rs.getString("PERIOD_UNIT");
           annual_usage = new Integer((int)rs.getInt("ANNUAL_USAGE"));
           shelf_life_unit = rs.getString("SHELF_LIFE_UNIT");
           storage_temp = rs.getString("STORAGE_TEMP");
           quantity = new Integer((int)rs.getInt("QUANTITY"));
           period = new Integer((int)rs.getInt("PERIOD"));
           request_status = new Integer((int)rs.getInt("REQUEST_STATUS"));
           quantity2 = new Integer((int)rs.getInt("QUANTITY2"));
           period2 = new Integer((int)rs.getInt("PERIOD2"));
           period_unit2 = rs.getString("PERIOD_UNIT");
           process_title = rs.getString("PROCESS_TITLE");
           process_desc = rs.getString("PROCESS_DESC");
           materials_replaced = rs.getString("MATERIALS_REPLACED");
           storage_locations = rs.getString("STORAGE_LOCATIONS");
           import_export = rs.getString("IMPORT_EXPORT");
           monthly_usage = new Integer((int)rs.getInt("MONTHLY_USAGE"));
           max_on_site = new Integer((int)rs.getInt("MAX_ON_SITE"));
           environmental_eval = rs.getString("ENVIRONMENTAL_EVAL");
           health_safety_eval = rs.getString("HEALTH_SAFETY_EVAL");
           item_id = new Integer((int)rs.getInt("ITEM_ID"));
           starting_view = new Integer((int)rs.getInt("STARTING_VIEW"));
           suggested_vendor = rs.getString("SUGGESTED_VENDOR");
           vendor_part_no = rs.getString("VENDOR_PART_NO");
           request_rejected = rs.getString("REQUEST_REJECTED");
           proc_special_instr = rs.getString("PROC_SPECIAL_INSTR");
           catalog_id = rs.getString("CATALOG_ID");
           break;
         }
         dbrs.close();
         

         // comp
         query = "select * from catalog_add_compatibility where request_id = " + request_id.toString();
         Vector compV = new Vector();
         dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
         while (rs.next()){
             compV.addElement(new Integer((int)rs.getInt("REQUEST_ID")));
             compV.addElement(rs.getString("FAC_PART_NO"));
             compV.addElement(rs.getString("PART_DESC"));
         }

         compatibility = compV;


         //load for facpartno
         if (this.fac_part_no!=null && this.fac_part_no.toString().length()>0 &&
             this.facility_id!=null && this.facility_id.toString().length()>0){
              this.loadFacPart(fac_part_no,facility_id);
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

   public void loadFacPart(String facpartno,String fac)  throws Exception {
/*
SQLWKS> describe catalog_fac_part_view
Column Name                    Null?    Type
------------------------------ -------- ----
FACILITY_ID                    NOT NULL VARCHAR2(30)
FAC_PART_NO                    NOT NULL VARCHAR2(30)
STOCKED                                 VARCHAR2(8)
ANNUAL_USAGE                            NUMBER
SHELF_LIFE                              NUMBER
SHELF_LIFE_UNIT                         VARCHAR2(30)
SHELF_LIFE_BASIS                        VARCHAR2(1)
SPEC_NAME                               VARCHAR2(30)
SPEC_VERSION                            VARCHAR2(15)
SPEC_AMENDMENT                          VARCHAR2(10)
STORAGE_TEMP                            VARCHAR2(30)
STORAGE_TEMP_UNIT                       VARCHAR2(8)
C_OF_C                                  VARCHAR2(1)
C_OF_A                                  VARCHAR2(1)
ITEM_ID                        NOT NULL NUMBER(38)
UNIT_PRICE                              NUMBER
SPEC_ON_LINE                            CHAR(1)
*/

      this.facility_id = fac;
      this.fac_part_no = facpartno;
      String query = "select * from catalog_fac_part_view where facility_id = '" + facility_id + "'";
      query = query + " and fac_part_no = '"+fac_part_no+"'";

      DBResultSet dbrs = null;
      ResultSet rs = null;
      try {
         dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
         while (rs.next()){
           stocked = rs.getString("STOCKED");
           shelf_life = rs.getString("SHELF_LIFE");
           shelf_life_basis = rs.getString("SHELF_LIFE_BASIS");
           spec_name = rs.getString("SPEC_NAME");
           spec_version = rs.getString("SPEC_VERSION");
           spec_amendment = rs.getString("SPEC_AMENDMENT");
           storage_temp_unit = rs.getString("STORAGE_TEMP_UNIT");
           c_of_c = rs.getString("C_OF_C");
           c_of_a = rs.getString("C_OF_A");
           annual_usage = new Integer((int)rs.getInt("ANNUAL_USAGE"));
           shelf_life_unit = rs.getString("SHELF_LIFE_UNIT");
           storage_temp = rs.getString("STORAGE_TEMP");
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
     DBResultSet dbrs = null;
      ResultSet rs = null;
     String dummy = new String("");

     String query = new String("insert into catalog_add_request (request_id,requestor,request_date)");
     query = query + " values (" + request_id.toString() + "," + requestor.toString() +",";
     if (request_date.equals("nowDate")){
        query = query + " SYSDATE)";
     } else {
        try {
	         dbrs = db.doQuery("select to_char(sysdate,'HH24:Mi:SS') from dual");
           rs = dbrs.getResultSet();
           if (rs.next()) {
	               dummy = " " + rs.getString(1);
           }
        } catch (Exception e){ throw e;  }
          finally{
         dbrs.close();
       }
        query = query + " to_date('"+request_date+dummy+"','MM/dd/yyyy HH24:MI:SS'))";
     }

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

     String query = new String("update catalog_add_request set " + col + " = ");
     switch (type) {
       case INT:
         I = new Integer(val);
         query = query + I.toString();
         break;
       case DATE:
         try {
           dbrs = db.doQuery("select to_char(sysdate,'HH24:Mi:SS') from dual");
           rs = dbrs.getResultSet();
           if (rs.next()) {
	            val = val + " " + rs.getString(1);
           }

         } catch (Exception e){ throw e; 
         } finally{
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
     query = query + " where request_id = " + request_id.toString();
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

   //specs
    void deleteSpec(Integer reqid,String spec)  throws Exception {

       String query = null;

       int count = HelpObjs.countQuery(db,"catalog_add_spec","request_id="+reqid.toString()+" and spec_id ='"+spec+"'");
       if (count>0){
        query = "delete catalog_add_spec where request_id="+reqid+" and spec_id ='"+spec+"'";
        try {
          db.doUpdate(query);
        } catch (Exception e) {
          e.printStackTrace();
          HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
          throw e;
        }
       }

    }

    void deleteSpec(Integer reqid)  throws Exception {
       DBResultSet dbrs = null;
      ResultSet rs = null;
       String query = null;

       int count = HelpObjs.countQuery(db,"catalog_add_spec","request_id="+reqid.toString());
       if (count>0){
        query = "delete catalog_add_spec where request_id="+reqid;
        try {
          db.doUpdate(query);
        } catch (Exception e) {
          e.printStackTrace();
          HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
          throw e;
        }
       }

    }

    public void insertSpec(Integer reqid,String spec, String version, String amend,String name, String title, String date)  throws Exception {

     if (BothHelpObjs.isBlankString(spec)) return;
     
     DBResultSet dbrs = null;
      ResultSet rs = null;
     String query = null;

     deleteSpec(reqid,spec);
     String maxLineItem = getMaxLineItem(reqid.toString());
     query = new String("insert into catalog_add_spec (request_id,spec_title, spec_id,spec_version,spec_amendment,spec_name,line_item,spec_source)");
     query = query + " values ( "    + reqid   +
                                ",'" + (title==null||title.length()<1?spec:title)+ "'" +
                                ",'" + spec    + "'" +
                                ",'" + version + "'" +
                                ",'" + amend    + "'" +
                                ",'" + name    + "'" +
                                ",'" + maxLineItem + "'" +
                                ",'catalog_add_spec')";
     try {
       db.doUpdate(query);
     } catch (Exception e) {
       e.printStackTrace();
       HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
       setInsertStatus(false);
       throw e;
     }

   }

  String getMaxLineItem(String reqid) {
        String maxLineItem = "1";
        String query = "select nvl(max(line_item)+1,1) max_line_item from catalog_add_spec where request_id = "+reqid;

        DBResultSet dbrs = null;
        ResultSet rs = null;
        try {
            dbrs = db.doQuery(query);
            rs=dbrs.getResultSet();
            while (rs.next()){
                maxLineItem = BothHelpObjs.makeBlankFromNull(rs.getString("max_line_item"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            dbrs.close();
        }
        return maxLineItem;
  } //end of method

   //
    //flow
    void deleteFlow(Integer reqid,String flow)  throws Exception  {
      DBResultSet dbrs = null;

      int count = HelpObjs.countQuery(db,"catalog_add_flow_down","request_id="+reqid.toString()+" and flow_down='"+flow+"'");
      if (count>0){
        String query = "delete catalog_add_flow_down where request_id="+reqid+" and flow_down='"+flow+"'";
        try {
          db.doUpdate(query);
        } catch (Exception e) {
          e.printStackTrace();
          HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
          throw e;
        }
      }
    }

    void deleteFlow(Integer reqid)  throws Exception  {

      String query = null;

      int count = HelpObjs.countQuery(db,"catalog_add_flow_down","request_id="+reqid.toString());
      if (count>0){
        query = "delete catalog_add_flow_down where request_id="+reqid;
        try {
          db.doUpdate(query);
        } catch (Exception e) {
          e.printStackTrace();
          HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
          throw e;
        }
      }
    }


    public void insertFlow(Integer reqid,String flow)  throws Exception {

     if (BothHelpObjs.isBlankString(flow)) return;

     String query = null;

     this.deleteFlow(reqid,flow);

     query = new String("insert into catalog_add_flow_down (request_id,flow_down)");
     query = query + " values ( "    + reqid   +
                                ",'" + flow    + "')" ;

     try {
       db.doUpdate(query);
     } catch (Exception e) {
       e.printStackTrace();
       HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
       throw e; 
     }
     
   }
   //

   public void deleteComp(Integer reqid)  throws Exception {
     Integer I;
     DBResultSet dbrs = null;
      ResultSet rs = null;

     String query = new String("delete catalog_add_compatibility where request_id = " + reqid);
     try {
       db.doUpdate(query);
     } catch (Exception e) {
       e.printStackTrace();
        HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
        throw e;
     }
   }

   public void insertComp(Vector comp)  throws Exception {
     Integer I;
     DBResultSet dbrs = null;
      ResultSet rs = null;
     String query = new String();

     this.deleteComp(request_id);
     
     for (int i=0;i<comp.size();i=i+2){
       query = " insert into catalog_add_compatibility (REQUEST_ID,FAC_PART_NO,PART_DESC) ";
       query = query + " values ("+request_id.toString()+",'"+comp.elementAt(i).toString()+"','"+comp.elementAt(i+1).toString()+"')";
       try {
         db.doUpdate(query);
       } catch (Exception e) {
         e.printStackTrace();
         HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
         setInsertStatus(false);;
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

   public int getNext()  throws Exception {
     String query = "select request_seq.nextval from dual";
     DBResultSet dbrs = null;
      ResultSet rs = null;
     int next = 0;
     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()) {
         next = (int)rs.getInt(1);
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

   public void commit()  throws Exception  {
     return;
   }

   public void rollback()  throws Exception  {
     return;
   }

   /** This will return all the DB variables, including all data from
       CatalogAddItem
SQLWKS> describe catalog_add_request
Column Name                    Null?    Type
------------------------------ -------- ----
REQUEST_ID                              NUMBER            0
REQUESTOR                               NUMBER            1
REQUEST_DATE                            DATE              2
REQUEST_STATUS                          NUMBER            3
FACILITY_ID                             VARCHAR2(30)      4
FAC_PART_NO                             VARCHAR2(30)      5
STOCKED                                 VARCHAR2(8)       6
ANNUAL_USAGE                            NUMBER            7
SHELF_LIFE                              NUMBER            8
SHELF_LIFE_UNIT                         VARCHAR2(30)      9
SHELF_LIFE_BASIS                        VARCHAR2(1)       0
SPEC_NAME                               VARCHAR2(30)      1
SPEC_VERSION                            VARCHAR2(15)      2
SPEC_AMENDMENT                          VARCHAR2(10)      3
STORAGE_TEMP                            NUMBER            4
STORAGE_TEMP_UNIT                       VARCHAR2(8)       5
C_OF_C                                  VARCHAR2(1)       6
C_OF_A                                  VARCHAR2(1)       7
USER_GROUP_ID                           VARCHAR2(30)      8
APPLICATION                             VARCHAR2(30)      9
QUANTITY                                NUMBER            0
PERIOD                                  NUMBER            1
PERIOD_UNIT                             VARCHAR2(30)      2
QUANTITY2                               NUMBER            3
PERIOD2                                 NUMBER            4
PERIOD_UNIT2                            VARCHAR2(30)      5
PROCESS_TITLE                           VARCHAR2(80)      6
PROCESS_DESC                            VARCHAR2(400)     7
MATERIALS_REPLACED                      VARCHAR2(60)      8
STORAGE_LOCATIONS                       VARCHAR2(60)      9
IMPORT_EXPORT                           VARCHAR2(1)       0
MONTHLY_USAGE                           NUMBER            1
MAX_ON_SITE                             NUMBER            2
ENVIRONMENTAL_EVAL                      VARCHAR2(400)     3
HEALTH_SAFETY_EVAL                      VARCHAR2(400)     4
ITEM_ID                                 NUMBER            5
STARTING_VIEW                           NUMBER            6
SUGGESTED_VENDOR                        VARCHAR2(80)      7
VENDOR_PART_NO                          VARCHAR2(60)      8
REQUEST_REJECTED                        VARCHAR2(1)       9

//added here status description
STATUS_DESCRIPTION                      VARCHAR           0

PROC_SPECIAL_INSTR                      VARCHAR(400)      1

CATALOG_ID                               VARCHAE(30)      2






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
HAS_MSDS                                extra 

///
radian.tcmis.server7.share.dbObjs.Catalog cat = new radian.tcmis.server7.share.dbObjs.Catalog(db);
    Vector v = cat.retrieve(" item_id = "+item_id.toString(),"material_id");
    Vector mats = new Vector();
    // get all materials
    for (int i =0;i<v.size();i++){
        radian.tcmis.server7.share.dbObjs.Catalog c = (radian.tcmis.server7.share.dbObjs.Catalog) v.elementAt(i);
        if (mats.contains(c.getMatId())) continue;
        mats.addElement(c.getMatId());
    }


///

   */
   public Hashtable getAllDBValues() throws Exception {
     this.load();
     Vector result = new Vector();
     Hashtable allData = new Hashtable();
     // CatalogAddRequest
     result.addElement((request_id == null?"":request_id.toString()));
     result.addElement((requestor == null?"":requestor.toString()));
     result.addElement((request_date == null?"":request_date));
     result.addElement((request_status == null?"0":request_status.toString()));
     result.addElement((facility_id == null?"":facility_id));
     result.addElement((fac_part_no == null?"":fac_part_no));
     result.addElement((stocked == null?"":stocked));
     result.addElement((annual_usage == null?"":annual_usage.toString()));
     result.addElement((shelf_life == null?"":shelf_life));
     result.addElement((shelf_life_unit == null?"":shelf_life_unit.toString()));
     result.addElement((shelf_life_basis == null?"":shelf_life_basis));
     result.addElement((spec_name == null?"":spec_name));
     result.addElement((spec_version == null?"":spec_version));
     result.addElement((spec_amendment == null?"":spec_amendment));
     result.addElement((storage_temp == null?"":storage_temp));
     result.addElement((storage_temp_unit == null?"":storage_temp_unit));
     result.addElement((c_of_c == null?"":c_of_c));
     result.addElement((c_of_a == null?"":c_of_a));
     result.addElement((user_group == null?"":user_group));
     result.addElement((application == null?"":application));
     result.addElement((quantity == null?"":quantity.toString()));
     result.addElement((period == null?"":period.toString()));
     result.addElement((period_unit == null?"":period_unit));
     result.addElement((quantity2 == null?"":quantity2.toString()));
     result.addElement((period2 == null?"":period2.toString()));
     result.addElement((period_unit2 == null?"":period_unit2));
     result.addElement((process_title == null?"":process_title));
     result.addElement((process_desc == null?"":process_desc));
     result.addElement((materials_replaced == null?"":materials_replaced));
     result.addElement((storage_locations == null?"":storage_locations));
     result.addElement((import_export == null?"":import_export));
     result.addElement((monthly_usage == null?"":monthly_usage.toString()));
     result.addElement((max_on_site == null?"":max_on_site.toString()));
     result.addElement((environmental_eval == null?"":environmental_eval));
     result.addElement((health_safety_eval == null?"":health_safety_eval));
     result.addElement((item_id == null?"":item_id.toString()));
     result.addElement((starting_view==null?"":starting_view.toString()));
     result.addElement((suggested_vendor==null?"":suggested_vendor));
     result.addElement((vendor_part_no==null?"":vendor_part_no));
     result.addElement((request_rejected==null?"":request_rejected));


     String status_desc = VVs.getVVAddRequestStatus(db,request_status.intValue());
     result.addElement((status_desc == null?"":status_desc.toString()));

     result.addElement((proc_special_instr==null?"":proc_special_instr));

     result.addElement((catalog_id==null?"":catalog_id));

     allData.put("REQUEST",result);
     allData.put("COMP",compatibility);

     // // System.out.println("REsult vector:\n"+result);
     CatalogAddItem ci = new CatalogAddItem(db);
     ci.setRequestId(this.getRequestId().intValue());
     Vector allItems = ci.retrieve();
     Vector allItemsData = new Vector();
     Vector allFateData = new Vector();
     if (allItems != null){
       for (int i=0;i<allItems.size();i++){
        // CatalogAddItem
        result = new Vector();
        ci  = (CatalogAddItem) allItems.elementAt(i);
        // // System.out.println("mat id:"+ci.getMaterialId());
        result.addElement((ci.getRequestId() == null?"":ci.getRequestId().toString()));
        result.addElement((ci.getMaterialDesc() == null?"":ci.getMaterialDesc()));
        result.addElement((ci.getManufacturer() == null?"":ci.getManufacturer()));
        String matid = (ci.getMaterialId() == null?"0":ci.getMaterialId().toString());
        result.addElement(matid);
        result.addElement((ci.getPartSize() == null?"":ci.getPartSize().toString()));
        result.addElement((ci.getSizeUnit() == null?"":ci.getSizeUnit()));
        result.addElement((ci.getPkgStyle() == null?"":ci.getPkgStyle()));
        result.addElement((ci.getMaterialApprovedBy() == null?"":ci.getMaterialApprovedBy().toString()));
        result.addElement((ci.getMaterialApprovedOn() == null?"":ci.getMaterialApprovedOn()));
        result.addElement((ci.getItemApprovedBy() == null?"":ci.getItemApprovedBy().toString()));
        result.addElement((ci.getItemApprovedOn() == null?"":ci.getItemApprovedOn()));
        result.addElement((ci.getMfgCatalogId() == null?"":ci.getMfgCatalogId()));
        result.addElement((ci.getPartId() == null?"":ci.getPartId().toString()));
        result.addElement((ci.getMaterialType() == null?"":ci.getMaterialType()));
        // msds
        result.addElement((VVs.hasMSDS(db,new Integer(matid))?"Y":"N"));
        allItemsData.addElement(result);

        Vector fate = ci.getFate();


        allFateData.addElement((Vector) fate.clone());
       }

       allData.put("ITEMS",allItemsData);
       allData.put("FATE",allFateData);

       //Specs
       allData.put("SPECS",this.getSpecV());

       //Flows
       allData.put("FLOWS",this.getFlowV());
     }

     return allData;
   }

   public int  getCount()  throws Exception {
     String query = "select count(*) from catalog_add_request where request_id = "+request_id.toString();
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

}

