/*
SQLWKS> describe purchase_request
Column Name                    Null?    Type
------------------------------ -------- ----
PR_NUMBER                      NOT NULL NUMBER(38)
REQUESTOR                               NUMBER(38)
FACILITY_ID                             VARCHAR2(30)
REQUEST_DATE                            DATE
PR_STATUS                               VARCHAR2(8)
SHIP_TO                                 VARCHAR2(30)
REQUESTED_FINANCE_APPROVER              NUMBER(38)
REJECTION_REASON                        VARCHAR2(255)
REQUESTED_RELEASER                      NUMBER(38)
PO_NUMBER                               VARCHAR2(30)
FORWARD_TO                              VARCHAR2(30)
END_USER                                VARCHAR2(40)
DEPARTMENT                              VARCHAR2(16)
*/
package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import java.util.*;
import java.sql.*;
import radian.tcmis.server7.share.db.*;


import java.lang.String;



public class EngEval  {

   public static final int STRING = 0;
   public static final int INT = 1;
   public static final int DATE = 2;

   protected Integer item_id;
   protected TcmISDBModel db;

   //trong
   public static final int ITEM_ID_COL = 0;
   public static final int MATERIAL_DESC_COL = 1;
   public static final int GRADE_COL = 2;
    public static final int PACKAGING_COL = 3;
   public static final int MANUFACTURER_COL = 4;
   public static final int SHELF_LIFE_COL = 5;
   public static final int REQUEST_ID_COL = 6;
  // public static final int PR_NUMBER_COL = 7;
   public static final String[] colHeads = {"Item Id","Material Desc","Grade","Packaging","Manufacturer","Shelf Life","Request Id"};
   public static final int[] colWidths = {70,200,500,70,100,70,0};
   public static final int[] colTypes = {BothHelpObjs.TABLE_COL_TYPE_STRING,
                                        BothHelpObjs.TABLE_COL_TYPE_STRING,
                                        BothHelpObjs.TABLE_COL_TYPE_STRING,
                                        BothHelpObjs.TABLE_COL_TYPE_STRING,
                                        BothHelpObjs.TABLE_COL_TYPE_STRING,
                                        BothHelpObjs.TABLE_COL_TYPE_STRING,
                                        BothHelpObjs.TABLE_COL_TYPE_STRING};

/*   public EngEval()  throws java.rmi.RemoteException {

   } */

   public EngEval(){

   }

   public EngEval(TcmISDBModel db) {
     this.db = db;
   }

   public void setDB(TcmISDBModel db){
     this.db = db;
   }

   public void setItemID(int num) {
     this.item_id = new Integer(num);
   }

   public boolean rejectEval(Hashtable data) throws Exception {
    boolean result = false;
    String query = "update catalog_add_request_new set eval_status = 'Rejected',eval_rejected_by = "+(Integer)data.get("USER_ID")+
                   ",eval_rejected_comment = '"+(String)data.get("REJECT_REASON")+"' where request_id in (";
    Vector v = (Vector)data.get("REJECT_REQUEST");
    for (int i = 0; i < v.size(); i++) {
      query += (String)v.elementAt(i)+",";
    }
    //removing last commas and add close parenthesis
    query = query.substring(0,query.length()-1) + ")";
    try {
      HelpObjs.insertUpdateTable(db,query);
      result = true;
    }catch (Exception e) {
      result = false;
      e.printStackTrace();
      throw e;
    }
    return result;
   }  //end of method
   //4-19-02
   public Hashtable getEvalDetail (Hashtable searchData) throws Exception {
    String[] engEvalCols = {"Name","Phone","E-mail","Date","Qty","Status","Comment"};
    int[] engEvalColWidths = {105,90,150,80,35,115,350};
    int[] engEvalColTypes = {BothHelpObjs.TABLE_COL_TYPE_STRING,
                            BothHelpObjs.TABLE_COL_TYPE_STRING,
                            BothHelpObjs.TABLE_COL_TYPE_STRING,
                            BothHelpObjs.TABLE_COL_TYPE_STRING,
                            BothHelpObjs.TABLE_COL_TYPE_STRING,
                            BothHelpObjs.TABLE_COL_TYPE_STRING,
                            BothHelpObjs.TABLE_COL_TYPE_STRING};
    Hashtable result = new Hashtable(5);
    String query = "select a.submit_date,a.init_90_day  qty,a.eval_status status,nvl(a.eval_rejected_comment,(select e.reason from catalog_add_approval e where e.request_id=a.request_id and e.reason is not null and rownum=1)) comments,b.last_name|| ', '||b.first_name  name,b.phone,b.email from"+
                   " catalog_add_request_new a, personnel b where a.requestor = b.personnel_id and a.request_status > 5"+
                   " and a.item_id = "+(String)searchData.get("ITEM_ID")+
                   " and a.eng_eval_facility_id = '"+(String)searchData.get("FACILITY_ID")+"'"+
                   " and a.eng_eval_work_area = '"+(String)searchData.get("WORK_AREA")+"' order by submit_date desc";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    String itemDesc = "";
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      Vector v = new Vector();
      while (rs.next()){
        String[] oa = new String[7];
        oa[0] = BothHelpObjs.makeBlankFromNull(rs.getString("name"));
        oa[1] = BothHelpObjs.makeBlankFromNull(rs.getString("phone"));
        oa[2] = BothHelpObjs.makeBlankFromNull(rs.getString("email"));
        oa[3] = BothHelpObjs.formatDate("toCharfromDB",BothHelpObjs.makeBlankFromNull(rs.getString("submit_date")));
        oa[4] = BothHelpObjs.makeBlankFromNull(rs.getString("qty"));
        oa[5] = BothHelpObjs.makeBlankFromNull(rs.getString("status"));
        oa[6] = BothHelpObjs.makeBlankFromNull(rs.getString("comments"));
        v.addElement(oa);
      }
      result.put("HEADER_COLS",engEvalCols);
      result.put("COL_WIDTHS",engEvalColWidths);
      result.put("COL_TYPES",engEvalColTypes);
      result.put("ITEM_DESC",itemDesc);
      result.put("EVAL_DETAIL",v);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally{
      dbrs.close();
    }
    return result;
   }  //end of method
   //4-19-02
   public Hashtable getEvalData (Hashtable searchData) throws Exception {
    String[] engEvalCols = {"Facility","Work Area","Status","Date","Comment","Facility ID","Application","Request ID"};
    int[] engEvalColWidths = {110,130,115,100,350,0,0,0};
    int[] engEvalColTypes = {BothHelpObjs.TABLE_COL_TYPE_STRING,
                            BothHelpObjs.TABLE_COL_TYPE_STRING,
                            BothHelpObjs.TABLE_COL_TYPE_STRING,
                            BothHelpObjs.TABLE_COL_TYPE_STRING,
                            BothHelpObjs.TABLE_COL_TYPE_STRING,
                            BothHelpObjs.TABLE_COL_TYPE_STRING,
                            BothHelpObjs.TABLE_COL_TYPE_STRING,
                            BothHelpObjs.TABLE_COL_TYPE_STRING};
    Hashtable result = new Hashtable(10);
    String query = "select b.request_id,b.eval_status status,b.eval_rejected_by,nvl(b.eval_rejected_comment,"+
	                 "(select e.reason from catalog_add_approval e where e.request_id=b.request_id and e.reason is not null and rownum=1))  comments,"+
	                 "b.eng_eval_facility_id facility_id,d.facility_name,b.eng_eval_work_area application,c.application_desc,b.submit_date,a.item_desc"+
                   " from item a,catalog_add_request_new b,"+
	                 "(select eng_eval_work_area,eng_eval_facility_id,item_id,max(submit_date) submit_date"+
	                 " from catalog_add_request_new where item_id = "+(String)searchData.get("ITEM_ID")+
                   " and request_status>5 and lower(engineering_evaluation) = 'y'  group by item_id,eng_eval_work_area,eng_eval_facility_id) m,"+
                   "fac_loc_app c,facility d"+
                   " where a.item_id = b.item_id and b.request_status > 5 and lower(b.engineering_evaluation) = 'y' and"+
                   " b.eng_eval_facility_id = c.facility_id and b.eng_eval_work_area = c.application and c.facility_id = d.facility_id and"+
                   " b.item_id = m.item_id and b.SUBMIT_DATE=m.submit_date and b.eng_eval_facility_id = m.eng_eval_facility_id and b.eng_eval_work_area = m.eng_eval_work_area";

    DBResultSet dbrs = null;
    ResultSet rs = null;
    String itemDesc = "";
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      Vector v = new Vector();
      boolean firstTime = true;
      while (rs.next()){
        String[] oa = new String[8];
        if (firstTime) {
          itemDesc = BothHelpObjs.makeBlankFromNull(rs.getString("item_desc"));
          firstTime = false;
        }
        oa[0] = BothHelpObjs.makeBlankFromNull(rs.getString("facility_name"));
        oa[1] = BothHelpObjs.makeBlankFromNull(rs.getString("application_desc"));
        oa[2] = BothHelpObjs.makeBlankFromNull(rs.getString("status"));
        oa[3] = BothHelpObjs.formatDate("toCharfromDB",BothHelpObjs.makeBlankFromNull(rs.getString("submit_date")));
        oa[4] = BothHelpObjs.makeBlankFromNull(rs.getString("comments"));
        oa[5] = BothHelpObjs.makeBlankFromNull(rs.getString("facility_id"));
        oa[6] = BothHelpObjs.makeBlankFromNull(rs.getString("application"));
        oa[7] = BothHelpObjs.makeBlankFromNull(rs.getString("request_id"));
        v.addElement(oa);
      }
      result.put("HEADER_COLS",engEvalCols);
      result.put("COL_WIDTHS",engEvalColWidths);
      result.put("COL_TYPES",engEvalColTypes);
      result.put("ITEM_DESC",itemDesc);
      result.put("EVAL_DATA",v);
      result.put("KEY_COLS",HelpObjs.getKeyCol(engEvalCols));
      //get all facility where user can reject engineering evaluation
      query = "select facility_id from user_group_member where user_group_id = 'RejectEvaluation' and personnel_id = "+(Integer)searchData.get("USER_ID");
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      Vector facilityV = new Vector();
      while (rs.next()){
        facilityV.addElement(BothHelpObjs.makeBlankFromNull(rs.getString("facility_id")));
      }
      result.put("FACILITY_ALLOW_REJECT",facilityV);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally{
      dbrs.close();
    }
    return result;
   }  //end of method

   public void delete()  throws Exception {
     String query = "delete eng_eval where item_id = " + item_id.toString();
     DBResultSet dbrs = null;
     try {
       db.doUpdate(query);
     } catch (Exception e) {
       e.printStackTrace();
       throw e;
     }
   }

   public void load()  throws Exception {
      String query = "select * from eng_eval where item_id = " + item_id.toString();

      DBResultSet dbrs = null;
      ResultSet rs = null;
      try {
         dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
         while (rs.next()){
           // need to implement
         }
      } catch (Exception e) { e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);   throw e;
       } finally{
             dbrs.close();
            }
      return;
   }

   public void insert()  throws Exception {
     DBResultSet dbrs = null;
     String dummy = new String("");

     String query = new String("insert into eng_eval (item_id)");
     query = query + " values (" + item_id.toString() + ")";


     try {
       db.doUpdate(query);

    } catch (Exception e) {
       e.printStackTrace();
       HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
       throw e;
       }
   }

   public void insert(String col,String val,int type)  throws Exception {
     Integer I;
     DBResultSet dbrs = null;

     String query = new String("update eng_eval set " + col + " = ");
     switch (type) {
       case INT:
         I = new Integer(val);
         query = query + I.toString();
         break;
       case DATE:
         try {
           ResultSet rs = null;
           dbrs = db.doQuery("select to_char(sysdate,'HH24:Mi:SS') from dual");   rs=dbrs.getResultSet();

           if (rs.next()) {
	       val = val + " " + rs.getString(1);
           }


        } catch (Exception e){
        e.printStackTrace();
         throw e;
       } finally{
             dbrs.close();
           }
         if (val.equals("nowDate")){
           query = query + " SYSDATE ";
         } else {
           query = query + " to_date('"+val+"','MM/dd/yy HH24:MI:SS')";
         }
         break;
       case STRING:
         query = query + "'" + val + "'";
         break;
       default:
         query = query + "'" + val + "'";
         break;
     }
     query = query + " where item_id = " + item_id.toString();
     try {
       db.doUpdate(query);
    } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
       throw e;
       }
   }

   //trong
   public Hashtable getEvalTableData(Hashtable inData) throws Exception{
    Hashtable dataH = new Hashtable(4);
    Vector dataV = new Vector();
    String fac = (String) inData.get("FACID");
    String work = (String) inData.get("APPID");
    String search = (String) inData.get("ITEM");
    String query = "select * from eng_eval_view_new where facility_id = '"+fac+"' ";
    if (work.length() > 0 && fac.length() > 0 &&!"All".equalsIgnoreCase(work))
      query += "and work_area = '"+work+"' ";
    if (search.length() > 0)
      query += "and search_string like '%"+ String.valueOf(search).toLowerCase() +"%' ";
    query += " order by item_id";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    String temp = new String("");
    try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while(rs.next()){
        Object[] oa = new Object[colHeads.length];
        if (temp.equals(BothHelpObjs.makeBlankFromNull(rs.getString("item_id")))) continue;
        oa[ITEM_ID_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("item_id"));
        oa[MATERIAL_DESC_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("material_desc"));
        oa[GRADE_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("grade"));
        oa[MANUFACTURER_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("manufacturer"));
        oa[PACKAGING_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("packaging"));
        oa[SHELF_LIFE_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("shelf_life"));
        oa[REQUEST_ID_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("request_id"));
        temp = BothHelpObjs.makeBlankFromNull(rs.getString("item_id"));
        dataV.addElement(oa);
       }
    } catch(Exception ex){
      ex.printStackTrace();
      throw ex;
    }finally{
      dbrs.close();
    }

    dataH.put("TABLE_HEADER",colHeads);
    dataH.put("TABLE_WIDTHS",colWidths);
    dataH.put("TABLE_TYPES",colTypes);
    dataV.trimToSize();
    dataH.put("TABLE_DATA",dataV);
    return dataH;
   }  // end of method
   public DbTableModel getEvalTableDataOld(Hashtable inData) throws Exception{
    DbTableModel model = new DbTableModel(colHeads);
    String fac = (String) inData.get("FACID");
    String work = (String) inData.get("APPID");
    String search = (String) inData.get("ITEM");
    String query = "select * from eng_eval_view_new where facility_id = '"+fac+"' ";
    if (work.length() > 0 && fac.length() > 0 &&!"All".equalsIgnoreCase(work))
      query += "and work_area = '"+work+"' ";
    if (search.length() > 0)
      query += "and search_string like '%"+ String.valueOf(search).toLowerCase() +"%' ";
    query += " order by item_id";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    String temp = new String("");
    try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while(rs.next()){
        Object[] oa = new Object[colHeads.length];
        if (temp.equals(BothHelpObjs.makeBlankFromNull(rs.getString("item_id")))) continue;
        oa[ITEM_ID_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("item_id"));
        oa[MATERIAL_DESC_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("material_desc"));
        oa[GRADE_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("grade"));
        oa[MANUFACTURER_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("manufacturer"));
        oa[PACKAGING_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("packaging"));
        oa[SHELF_LIFE_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("shelf_life"));
        oa[REQUEST_ID_COL] = BothHelpObjs.makeBlankFromNull(rs.getString("request_id"));
        temp = BothHelpObjs.makeBlankFromNull(rs.getString("item_id"));
        model.addRow(oa);
       }
    } catch(Exception ex){
      ex.printStackTrace();
      throw ex;
    }finally{
      dbrs.close();
    }
    model.setColumnPrefWidth(colWidths);
    model.setColumnType(colTypes);
    return model;
   }  // end of method
}  //end of class
