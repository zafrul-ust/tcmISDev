/*
SQLWKS> describe order_trackingf;
Column Name                    Null?    Type
------------------------------ -------- ----
ITEM_ID                                 NUMBER
PR_STATUS                               VARCHAR2(8)
REQUEST_ID                              VARCHAR2(30)
PR_NUMBER                               NUMBER
FAC_PART_NO                             VARCHAR2(30)
LINE_ITEM                               VARCHAR2(30)
MATERIAL_DESC                           VARCHAR2(100)
QUANTITY                                NUMBER
PO_NUMBER                               VARCHAR2(61)
RELEASE_NUMBER                          VARCHAR2(30)
REQUIRED_DATETIME                       DATE
FACILITY_ID                             VARCHAR2(30)
WORK_AREA                               VARCHAR2(30)
REQUESTED_FINANCE_APPROVER              NUMBER
DATE_SUBMITTED                          DATE
SALES_ORDER                             NUMBER
PROJECTED_DELIVERY                      DATE
LAST_SHIPPED                            DATE
TOTAL_SHIPPED                           NUMBER
ITEM_TYPE                               VARCHAR2(8)
UOM                                     CHAR(2)
REQUESTED_RELEASER                      NUMBER
REQUESTOR                               NUMBER
CRITICAL                                VARCHAR2(1)
TCMIS_GEN                               CHAR(1)
RADIAN_PO                               NUMBER
NOTES                                   VARCHAR2(1000)
NEXT_QUANTITY                           NUMBER
NEXT_DATE                               DATE
STATUS_TYPE                             VARCHAR2(3)
REQUESTOR_NAME                          VARCHAR2(0)
APPROVER_NAME                           VARCHAR2(0)
CANCEL_REQUEST                          DATE
CANCEL_REQUESTER                        NUMBER
CANCEL_REQUESTER_NAME                   VARCHAR2(0)
BRANCH_PLANT                            VARCHAR2(12)
MATERIAL_ID                             NUMBER(38)
SPEC_ID                                 VARCHAR2(400)
SPEC_ON_LINE                            CHAR(1)
MSDS_ON_LINE                            CHAR(1)
*/

package radian.tcmis.server7.share.dbObjs;

import java.util.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;

public class TrackingTable {
   protected TcmISDBModel db;
   protected Hashtable inputHash;
   public static final int DRAFT = 0;
   public static final int PENDING_APPROVAL = 1;
   public static final int PENDING_RELEASE = 2;
   public static final int ISSUED = 3;
   public static final int DELIVERED = 10;
   public static final int ARCHIVED = 5;
   public static final int CANCELED = 6;
   public static final int CANCELED2 = 7;
   public static final int CANCELED3 = 8;
   public static final int REJECTED  = 9;
   public static final int CENCELLED_REQ  = 13;
   public static final int CENCELLED_REJ  = 14;
   public static final int PENDING_USE  = 15;

   public static String[] STATUS_NAME = {"entered","compchk"          ,"approved"      ,"posubmit" ,"posubmit","posubmit","cancelled","canceled","cancel"  ,"rejected","delivered" ,"archived"          ,"partial"          ,"rqcancel","nocancel","compchk2"}; // the view does not bring delivered or archived
   public static String[] STATUS_DESC = {"Draft"  ,"Pending Approval","Pending Release","Submitted","Submitted","Submitted","Canceled" ,"Canceled","Canceled","Rejected","Delivered" ,"Del. > 30 days"    ,"Partial Del."     ,"Cancel Req.","Cancel Denied","Pending Approval"};
   //                                      0                1                2              3           4           5         6            7         8         9           10                 11              12                  13               14
   public TrackingTable(TcmISDBModel  db){
     this.db = db;
   }
   public void setInputHash(Hashtable h){
     inputHash = h;
   }

   public TrackingTable() {

   }

   /*Raj chg 9/22/01
   public Hashtable getTableData()throws Exception{
     // do the search and load the vectors
     String query = buildQuery();
     TrackView tv = new TrackView(db);
     return (tv.retrieve(query));
   }
   */

   public String buildQuery(){
     String userId = inputHash.get("USER_ID").toString();
     if(((Boolean)inputHash.get("NEED_MY_APPROVAL")).booleanValue()){
       String query = "select * from order_tracking_view where pr_status = '"+STATUS_DESC[PENDING_APPROVAL]+"' and requested_finance_approver = "+userId;
       return query;
     }
     String regularSearch = buildRegularWhere();

     //6-7-02 allow user to search everything in order_tracking_view
     //if (regularSearch.length()<1) return null;  // no criteria

     String searchTxt = BothHelpObjs.makeBlankFromNull(inputHash.get("SEARCH_TEXT").toString()).trim();
     String query = "select t.* from order_tracking_view t";
     if (!BothHelpObjs.isBlankString(searchTxt)){
        searchTxt = HelpObjs.singleQuoteToDouble(searchTxt);
        query += ", (select r.pr_number,r.line_item from rli r,item i where r.item_id=i.item_id and"+
                 " lower(to_char(i.item_id)||i.item_desc) like lower('%"+searchTxt+"%') union all"+
                 " select r.pr_number,r.line_item from rli r where"+
                 " lower(to_char(r.sales_order)||to_char(r.pr_number)||'-'||to_char(r.line_item)||r.po_number||r.fac_part_no)"+
                 " like lower('%"+searchTxt+"%')) s where t.pr_number = s.pr_number and t.line_item = s.line_item";
        if (regularSearch.length() > 0) {
          query += " and "+regularSearch;
        }
     } else {
        if (regularSearch.length() > 0) {
          query += " where "+regularSearch;
        }
     }
     return query;
   }

   private String buildRegularWhere(){
     Vector conditions = new Vector();
     String where = "";
     // first the easy ones
     if(!BothHelpObjs.isBlankString(inputHash.get("REQUESTOR").toString()) &&
         BothHelpObjs.makeZeroFromNull(inputHash.get("REQUESTOR").toString()) > 0){
       conditions.addElement(" requestor = "+inputHash.get("REQUESTOR").toString()+" ");
     }
     if(!BothHelpObjs.isBlankString(inputHash.get("APPROVER").toString()) &&
         BothHelpObjs.makeZeroFromNull(inputHash.get("APPROVER").toString()) > 0){
       conditions.addElement(" requested_finance_approver = "+inputHash.get("APPROVER").toString()+" ");
     }
     if(!BothHelpObjs.isBlankString(inputHash.get("FACILITY").toString()) && inputHash.get("FACILITY").toString().toLowerCase().trim().indexOf("all")<0 ){
       conditions.addElement(" facility_id = '"+inputHash.get("FACILITY").toString()+"' ");
     }
     // This is provisory untill next release (fixed on the gui - 1.4.06
     if(!BothHelpObjs.isBlankString(inputHash.get("WORK_AREA").toString())  && inputHash.get("WORK_AREA").toString().toLowerCase().trim().indexOf("all")<0){
       String wa = inputHash.get("WORK_AREA").toString().trim();
       conditions.addElement(" work_area = '"+wa+"' ");
     }

     // now the status
     boolean draft = ((Boolean)inputHash.get("STATUS_DRAFT")).booleanValue();
     boolean canceled = ((Boolean)inputHash.get("STATUS_CANCELED")).booleanValue();
     boolean pendApproval = ((Boolean)inputHash.get("STATUS_PEND_APPROVAL")).booleanValue();
     boolean pendRelease = ((Boolean)inputHash.get("STATUS_PEND_ISSUE")).booleanValue();
     boolean issued = ((Boolean)inputHash.get("STATUS_ISSUED")).booleanValue();
     boolean delivered= ((Boolean)inputHash.get("STATUS_DELIVERED")).booleanValue();
     boolean archived = ((Boolean)inputHash.get("STATUS_ARCHIVED")).booleanValue();
     boolean critical= ((Boolean)inputHash.get("STATUS_CRIT_ONLY")).booleanValue();
     boolean rejected= ((Boolean)inputHash.get("STATUS_REJECTED")).booleanValue();

     // first critical, it gets it's own condition
     if(critical){
       conditions.addElement(" lower(critical) = 'y' ");
     }

     // now the rest. These are all combined with 'OR' to create one big condition
     Vector statV = new Vector();
     if(draft){
       statV.addElement(" pr_status = '"+STATUS_DESC[DRAFT]+"' ");
     }
     if(pendApproval){
       statV.addElement(" pr_status = '"+STATUS_DESC[PENDING_APPROVAL]+"' ");
       statV.addElement(" pr_status = '"+STATUS_DESC[PENDING_USE]+"' ");
     }
     if(pendRelease){
       statV.addElement(" pr_status = '"+STATUS_DESC[PENDING_RELEASE]+"' ");
     }
     if(canceled){
       statV.addElement(" pr_status = '"+STATUS_DESC[CANCELED]+"' ");
       statV.addElement(" pr_status = '"+STATUS_DESC[CANCELED2]+"' ");
       statV.addElement(" pr_status = '"+STATUS_DESC[CANCELED3]+"' ");
     }
     if(rejected){
       statV.addElement(" pr_status = '"+STATUS_DESC[REJECTED]+"' ");
     }

     if(issued && delivered && archived){
       statV.addElement(" pr_status = '"+STATUS_DESC[ISSUED]+"' ");
       statV.addElement(" pr_status = '"+STATUS_DESC[CENCELLED_REQ]+"' ");  // rqcancel
       statV.addElement(" pr_status = '"+STATUS_DESC[CENCELLED_REJ]+"' ");  // cancel declined
     }else{
       if(issued){
         statV.addElement(" pr_status = '"+STATUS_DESC[ISSUED]+"' and (total_shipped is null or (total_shipped < quantity))");
         statV.addElement(" pr_status = '"+STATUS_DESC[CENCELLED_REQ]+"' and (total_shipped is null or (total_shipped < quantity))");  // rqcancel
         statV.addElement(" pr_status = '"+STATUS_DESC[CENCELLED_REJ]+"' and (total_shipped is null or (total_shipped < quantity))");  // cancel declined
       }
       if(delivered){
         statV.addElement(" pr_status = '"+STATUS_DESC[DELIVERED]+"' and total_shipped = quantity and sysdate - 31 < last_shipped ");
       }
       if(archived){
         statV.addElement(" pr_status = '"+STATUS_DESC[ARCHIVED]+"' and total_shipped = quantity and sysdate - 30 > last_shipped ");
       }
     }
     // combine the status clauses
     String statClause = "";
     for(int i=0;i<statV.size();i++){
       if(i>0){
         statClause += " or ";
       }
       statClause += "("+statV.elementAt(i).toString()+")";
     }
     if(!BothHelpObjs.isBlankString(statClause)){
       statClause = "("+statClause + ")";
       conditions.addElement(statClause);
     }

     // now combine all the conditions with AND
     for(int i=0;i<conditions.size();i++){
       if(i>0){
         where += " and ";
       }
       where += "("+conditions.elementAt(i).toString()+")";
     }

     return where;
   }

  private String getItemSymSearch(String view,String itemStr){
      /*
      String res = new String("select distinct a.* from "+view+" a, item_synonym b "+
                              " where lower(search_string) like lower('%"+itemStr+
                              "%') and a.item_id = b.item_id ");  */
      String res = new String("select distinct a.* from "+view+" a, ordertrack_item_synonym b "+
                              " where lower(search_string) like lower('%"+itemStr+
                              "%') and a.item_id = b.item_id ");
      return res;

  }

  private String getOrderSymSearch(String view,String itemStr,boolean pr){
    String res = null;

    if (!pr){
       res = new String("select distinct a.* from "+view+" a, order_synonym b "+
                        " where lower(search_string) like lower('%"+itemStr+
                        "%') and a.sales_order = b.sales_order ");
    } else {
       res = new String("select distinct a.* from "+view+" a, order_synonym b "+
                        " where lower(search_string) like lower('%"+itemStr+
                        "%') and a.pr_number = b.pr_number and a.line_item = b.line_item" +
                        " and b.sales_order is null ");
    }

    return res;
  }

}
