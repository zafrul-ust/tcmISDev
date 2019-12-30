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
public class PurchaseRequest  {

   public static final int STRING = 0;
   public static final int INT = 1;
   public static final int DATE = 2;

   protected TcmISDBModel db;
   protected Integer pr_number;
   protected String facility_id;
   protected String rejection_reason;
   protected String pr_status;
   protected String pr_status_desc;
   protected Integer requestor;
   protected String request_date;
   protected String ship_to = null;
   protected String forward_to = null;
   protected Integer financial_approver;
   protected Integer releaser;
   protected boolean insertStatus;
   protected String po_number = new String("");
   protected String now_date;
   protected String end_user;
   protected String department;
   protected boolean engineering_eval;   //trong 3/6/00
   protected Integer request_id;
   protected String accSysId;        //trong 4/8
   protected String financeApprovalStatus;
   protected String contactInfo;
   protected String creditCardType;
   protected String creditCardNumber;
   protected String creditCardExpDate;
   protected String creditCardName;

   public PurchaseRequest()  throws java.rmi.RemoteException {

     insertStatus = false;
   }

   public PurchaseRequest(TcmISDBModel db)  throws java.rmi.RemoteException {
     this.db = db;
     insertStatus = false;
   }

   public void setDB(TcmISDBModel db){
     this.db = db;
   }

   public void setPRNumber(int num) {
     this.pr_number = new Integer(num);
   }

   public Integer getPRNumber() {
     return pr_number;
   }

   public void setFacilityId(String fac) {
     this.facility_id = fac;
   }

   public String getFacilityId() {
     return facility_id;
   }

   public void setPRStatus(String stat) {
     this.pr_status = stat;
   }

   public String getPRStatus() {
     return pr_status;
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

   public void setShipTo(String loc) {
     this.ship_to = loc;
   }

   public String getShipTo() {
     return ship_to;
   }

   public void setForwardTo(String loc) {
     this.forward_to = loc;
   }

   public String getForwardTo() {
     return forward_to;
   }

   public void setFinancialApprover(int id) {
     this.financial_approver = new Integer(id);
   }

   public Integer getFinancialApprover() {
     return financial_approver;
   }

   public void setReleaser(int id) {
     this.releaser = new Integer(id);
   }

   public Integer getReleaser() {
     return releaser;
   }

   public void setRejectionReason(String s) {
     this.rejection_reason = s;
   }

   public String getRejectionReason() {
     return this.rejection_reason;
   }

   public void setPONumber(String id) {
     this.po_number = id;
   }

   public String getPONumber() {
     return this.po_number;
   }
   public void setEndUser(String id) {
     this.end_user = id;
   }

   public String getEndUser() {
     return this.end_user;
   }

   public void setDepartment(String id) {
     this.department = id;
   }

   public String getDepartment() {
     return this.department;
   }

   //trong 3/6/00
   public void setAccSysId(String s) {
    this.accSysId = s;
   }
   public String getAccSysId() {
    return this.accSysId;
   }
   public void setEngineeringEval(Boolean id) {
     this.engineering_eval = id.booleanValue();
   }
   public boolean getEngineeringEval() {
     return this.engineering_eval;
   }
   public void setRequestId(int id) {
     this.request_id = new Integer(id);
   }
   public Integer getRequestId() {
     return this.request_id;
   }
   public void setFinanceApprovalStatus(String s) {
    this.financeApprovalStatus = s;
   }
   public String getFinanceApprovalStatus() {
    return this.financeApprovalStatus;
   }
   public void setContactInfo(String s) {
    this.contactInfo = s;
   }
   public String getContactInfo() {
    return this.contactInfo;
   }

   public void setCreditCardType(String s) {
    this.creditCardType = s;
   }
   public String getCreditCardType() {
    return this.creditCardType;
   }
   public void setCreditCardNumber(String s) {
    this.creditCardNumber = s;
   }
   public String getCreditCardNumber() {
    return this.creditCardNumber;
   }
   public void setCreditCardExpDate(String s) {
    this.creditCardExpDate = s;
   }
   public String getCreditCardExpDate() {
    return this.creditCardExpDate;
   }
   public void setCreditCardName(String s) {
    this.creditCardName = s;
   }
   public String getCreditCardName() {
    return this.creditCardName;
   }

   public void updatePayLoadForMR(String prNumb, String payLoadID) throws Exception {
    String query = "update request_line_item set payload_id = '"+payLoadID+"' where pr_number = "+prNumb;
    try {
       db.doUpdate(query);
    } catch (Exception e) {
       e.printStackTrace();
       HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
       throw e;
    }
   }
   //getting pr_number from punchout_session with the given payload ID
   public String getMRForPayLoadID(String payLoadID) throws Exception {
    String query = "select mr_number from punchout_session where payload_id = '"+payLoadID+"'";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    String result = "";
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while (rs.next()) {
        result = BothHelpObjs.makeBlankFromNull(rs.getString("mr_number"));
      }
    } catch (Exception e) {
       e.printStackTrace();
       throw e;
    } finally{
       dbrs.close();
    }
    return result;
   }

   //getting pr_number from table with the given payload ID
   public String getMRForPayLoadID(String col, String colName, String table, String payLoadID) throws Exception {
    String query = "select "+col+" from "+table+" where payload_id = '"+payLoadID+"'";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    String result = "";
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while (rs.next()) {
        result = BothHelpObjs.makeBlankFromNull(rs.getString(colName));
      }
    } catch (Exception e) {
       e.printStackTrace();
       throw e;
    } finally{
       dbrs.close();
    }
    return result;
   }

   public void deletePunchoutFlagByPayLoadId(String payLoadId)  throws Exception {
     String query = "delete punchout_order_message where payload_id = '"+payLoadId+"'";
     DBResultSet dbrs = null;
     try {
       db.doUpdate(query);
     } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);   throw e;
       }
   }


   //getting data to reload shopping cart
   public Vector getUpdateMRInfo(int prNum) throws Exception {
    String query = "select * from purchase_request_view where pr_number = " +prNum;
    query += " order by to_number(line_item)";
    DBResultSet dbrs = null;
    ResultSet rs = null;
    Vector result = new Vector();
    try {
      boolean showCurrency = HelpObjs.showCurrency(db);

      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      boolean firstTime = true;
      while (rs.next()) {
        Hashtable h = new Hashtable();
        h.put("WORK_AREA_ID",BothHelpObjs.makeBlankFromNull(rs.getString("application")));
        h.put("WORK_AREA",BothHelpObjs.makeBlankFromNull(rs.getString("application_desc")));
        h.put("PART_NO",BothHelpObjs.makeBlankFromNull(rs.getString("part_no")));
        h.put("QTY",BothHelpObjs.makeBlankFromNull(rs.getString("quantity")));
        h.put("PACKAGING",BothHelpObjs.makeBlankFromNull(rs.getString("packaging")));
        h.put("ITEM_ID",BothHelpObjs.makeBlankFromNull(rs.getString("item_id")));
        h.put("FACILITY_ID",BothHelpObjs.makeBlankFromNull(rs.getString("facility_id")));
        h.put("PR_NUMBER",BothHelpObjs.makeBlankFromNull(rs.getString("PR_NUMBER")));
        h.put("LINE_ITEM",BothHelpObjs.makeBlankFromNull(rs.getString("line_item")));
        h.put("CATALOG",BothHelpObjs.makeBlankFromNull(rs.getString("catalog")));
        h.put("TYPE",BothHelpObjs.makeBlankFromNull(rs.getString("type")));
        h.put("EXAMPLE_ITEM_ID",BothHelpObjs.makeBlankFromNull(rs.getString("example_item_id")));
        h.put("UNIT_PRICE",BothHelpObjs.makeBlankFromNull(rs.getString("unit_price")));
        h.put("CATALOG_PRICE",BothHelpObjs.makeBlankFromNull(rs.getString("catalog_price")));
        String currencyID = " "+BothHelpObjs.makeBlankFromNull(rs.getString("currency_id"));
        h.put("CURRENCY_ID",currencyID);
        String extPrice = BothHelpObjs.makeBlankFromNull(rs.getString("extended_price"));
        if (extPrice.length() > 0) {
          h.put("EXT_PRICE",extPrice+currencyID);
        }else {
          h.put("EXT_PRICE",extPrice);
        }
        h.put("MIN_BUY",BothHelpObjs.makeBlankFromNull(rs.getString("min_buy")));           //2-26-02
        h.put("REQUIRED_DATE",BothHelpObjs.formatDate("toNumfromChar",(BothHelpObjs.formatDate("toCharfromDB",BothHelpObjs.makeBlankFromNull(rs.getString("required_datetime"))))));
        h.put("PART_GROUP",BothHelpObjs.makeBlankFromNull(rs.getString("part_group_no")));
        h.put("ACCOUNT_SYS_ID",BothHelpObjs.makeBlankFromNull(rs.getString("account_sys_id")));
        h.put("PART_DESC",BothHelpObjs.makeBlankFromNull(rs.getString("part_description")));
        h.put("NOTES",BothHelpObjs.makeBlankFromNull(rs.getString("notes")));
        h.put("CRITICAL",BothHelpObjs.makeBlankFromNull(rs.getString("critical")));
        h.put("INVENTORY_GROUP",BothHelpObjs.makeBlankFromNull(rs.getString("inventory_group")));
        h.put("ORDER_QUANTITY_RULE",BothHelpObjs.makeBlankFromNull(rs.getString("order_quantity_rule")));
		  h.put("CATALOG_COMPANY_ID",BothHelpObjs.makeBlankFromNull(rs.getString("catalog_company_id")));
		  result.addElement(h);
      }
    } catch (Exception e) {
       e.printStackTrace();
       throw e;
    } finally{
       dbrs.close();
    }
    return result;
   }



   public Integer getEvalPrNumber(int req_id) throws Exception {
    String query = "select * from purchase_request where request_id = " +req_id;
    DBResultSet dbrs = null;
    ResultSet rs = null;
    String num = null;
    try {
       dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
       while (rs.next()) {
        num =  BothHelpObjs.makeBlankFromNull(rs.getString("PR_NUMBER"));
       }
    } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);  throw e;
    } finally{
       dbrs.close();
    }
    return (new Integer(num));
   }

   public String getNowDate()  throws Exception {
     String query = "select to_char(sysdate,'MM/dd/yyyy') from dual";
     DBResultSet dbrs = null;
      ResultSet rs = null;
     String next = new String("");
     try {
       dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
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

   public String getPRStatusDesc()  throws Exception {
     String query = new String("select pr_status_desc from vv_pr_status where pr_status = '" + this.pr_status + "'");
     DBResultSet dbrs = null;
      ResultSet rs = null;
     try {
        dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
        if (rs.next()) {
           this.pr_status_desc = rs.getString("pr_status_desc");
        }


        return this.pr_status_desc;
     } catch (Exception e) {
        e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);  throw e;
       } finally{
             dbrs.close();

     }
   }

   public void delete()  throws Exception {
     String query = "delete purchase_request where pr_number = " + pr_number.toString();
     DBResultSet dbrs = null;
     try {
       db.doUpdate(query);

     } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);   throw e;
       }
   }

   public void load()  throws Exception {
      String query = "select * from purchase_request where pr_number = " + pr_number.toString();

      DBResultSet dbrs = null;
      ResultSet rs = null;
      try {
         dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
         while (rs.next()){
           this.setPRNumber((int)rs.getInt("PR_NUMBER"));
           this.setRequestor((int)rs.getInt("REQUESTOR"));
           this.setFacilityId(rs.getString("FACILITY_ID"));
           this.setRequestDate(rs.getString("REQUEST_DATE"));
           this.setPRStatus(rs.getString("PR_STATUS"));
           //this.setShipTo(rs.getString("SHIP_TO_LOCATION_ID"));
			  this.setShipTo("");
			  this.setFinancialApprover((int)rs.getInt("REQUESTED_FINANCE_APPROVER"));
           this.setRejectionReason(rs.getString("REJECTION_REASON"));
           this.setReleaser((int)rs.getInt("REQUESTED_RELEASER"));
           this.setPONumber(rs.getString("PO_NUMBER"));
           this.setForwardTo(rs.getString("FORWARD_TO"));
           this.setEndUser(rs.getString("END_USER"));
           this.setDepartment(rs.getString("DEPARTMENT"));
           //trong 3/6/00
           this.setEngineeringEval(new Boolean(rs.getString("ENGINEERING_EVALUATION").equalsIgnoreCase("Y")));
           this.setRequestId((int)rs.getInt("REQUEST_ID"));
           this.setAccSysId(BothHelpObjs.makeBlankFromNull(rs.getString("ACCOUNT_SYS_ID")));
           this.setContactInfo(BothHelpObjs.makeBlankFromNull(rs.getString("contact_info")));
           this.setCreditCardType(BothHelpObjs.makeBlankFromNull(rs.getString("credit_card_type")));
           this.setCreditCardNumber(BothHelpObjs.makeBlankFromNull(rs.getString("credit_card_number")));
           this.setCreditCardExpDate(BothHelpObjs.makeBlankFromNull(rs.getString("credit_card_expiration_date")));
           this.setCreditCardName(BothHelpObjs.makeBlankFromNull(rs.getString("credit_card_name")));
           //this.setFinanceApprovalStatus(BothHelpObjs.makeBlankFromNull(rs.getString("finance_approval_status")));
         }


      } catch (Exception e) { e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);   throw e;
       } finally{
             dbrs.close();
       }
      return;
   }

   public Vector fillEntryQueue()  throws Exception  {
     String query = "select pr_number from purchase_request where requestor = " + requestor.toString() + " and pr_status = 'entered'";
     DBResultSet dbrs = null;
      ResultSet rs = null;
     Vector V = new Vector();
     try {
       dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
       while (rs.next()) {
         V.addElement(new Integer((int)rs.getInt("pr_number")));
       }


     } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);  throw e;
       } finally{
             dbrs.close();

     }
     return V;
   }

   public Vector fillPendingQueue()   throws Exception {
     String query = "select pr_number from purchase_request where requestor = " + requestor.toString() + " and pr_status = 'compchk'";
     DBResultSet dbrs = null;
      ResultSet rs = null;
     Vector V = new Vector();
     try {
       dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
       Integer I = new Integer(0);
       String S = new String("");
       while (rs.next()) {
         I = new Integer((int)rs.getInt("pr_number"));
         S = new String(I.toString() + " (requested by you)");
         V.addElement(S);
       }


     } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);   throw e;
       } finally{
             dbrs.close();

     }
     query = "select pr_number from purchase_request where requested_finance_approver = " + requestor.toString() + " and pr_status = 'compchk'";
     try {
       dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
       Integer J = new Integer(0);
       String T = new String("");
       while (rs.next()) {
         J = new Integer((int)rs.getInt("pr_number"));
         T = new String(J.toString() + " (needs your approval)");
         V.addElement(T);
       }


     } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);  throw e;
       } finally{
             dbrs.close();

     }
     query = "select pr_number from purchase_request where requested_releaser = " + requestor.toString() + " and pr_status = 'approved'";
     try {
       dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
       Integer K = new Integer(0);
       String U = new String("");
       while (rs.next()) {
         K = new Integer((int)rs.getInt("pr_number"));
         U = new String(K.toString() + " (release numbers requested)");
         V.addElement(U);
       }


     } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);   throw e;
       } finally{
             dbrs.close();

     }
     return V;
   }

   public Vector fillApprovedQueue()  throws Exception {
     String query = "select pr_number from purchase_request where requestor = " + requestor.toString() + " and pr_status = 'approved'";
     DBResultSet dbrs = null;
      ResultSet rs = null;
     Vector V = new Vector();
     try {
       dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
       while (rs.next()) {
         V.addElement(new Integer((int)rs.getInt("pr_number")));
       }


     } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);   throw e;
       } finally{
             dbrs.close();

     }
     return V;
   }

   public Vector fillRejectedQueue()  throws Exception {
     String query = "select pr_number from purchase_request where requestor = " + requestor.toString() + " and pr_status = 'rejected'";
     DBResultSet dbrs = null;
      ResultSet rs = null;
     Vector V = new Vector();
     try {
       dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
       while (rs.next()) {
         V.addElement(new Integer((int)rs.getInt("pr_number")));
       }


     } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);   throw e;
       } finally{
             dbrs.close();

     }
     return V;
   }

   public Vector fillReleasedQueue()  throws Exception {
     String query = "select pr_number from purchase_request where requestor = " + requestor.toString() + " and pr_status = 'posubmit'";
     DBResultSet dbrs = null;
      ResultSet rs = null;
     Vector V = new Vector();
     try {
       dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
       while (rs.next()) {
         V.addElement(new Integer((int)rs.getInt("pr_number")));
       }


    } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);    throw e;
       } finally{
             dbrs.close();

     }
     return V;
   }

   public void insert()  throws Exception {
     DBResultSet dbrs = null;
     String dummy = new String("");

     String query = "insert into purchase_request (pr_number,facility_id,pr_status,requestor,account_sys_id,request_date";
     String values = " values ("+pr_number.toString()+",'"+facility_id+"','"+pr_status+"',"+requestor.toString()+",'"+accSysId.toString()+"',";
     if (request_date.equals("nowDate")){
        values += " SYSDATE";
     } else {
        values += " to_date('"+request_date+"','MM/dd/yyyy HH24:MI:SS')";
     }
     if (!BothHelpObjs.isBlankString(ship_to)) {
      query += ",ship_to";
      values += ",'"+ship_to+"'";
     }
     if (!BothHelpObjs.isBlankString(forward_to)) {
      query += ",forward_to";
      values += ",'"+forward_to+"'";
     }
     query += ")" + values + ")";
     try {
       db.doUpdate(query);
    } catch (Exception e) {
       e.printStackTrace();
       HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
       setInsertStatus(false);
       throw e;
    }
   }

   public void insert(String col,String val,int type)  throws Exception {
     if(col.equalsIgnoreCase("END_USER") || col.equalsIgnoreCase("DEPARTMENT") || col.equalsIgnoreCase("REJECTION_REASON") || col.equalsIgnoreCase("CONTACT_INFO")) {
       val = HelpObjs.singleQuoteToDouble(val);
     }

     Integer I;
     DBResultSet dbrs = null;

     String query = new String("update purchase_request set " + col + " = ");
     switch (type) {
       case INT:
         I = new Integer(val);
         query = query + I.toString();
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
       default:
         query = query + "'" + val + "'";
         break;
     }
     query = query + " where pr_number = " + getPRNumber();
     //System.out.println("\n\n-------- inserting into purchase request: "+query);
     try {
       db.doUpdate(query);
    } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
       setInsertStatus(false);   throw e;
       }
   }

   public void setInsertStatus(boolean b) {
     this.insertStatus = b;
   }

   public boolean getInsertStatus() {
     return this.insertStatus;
   }

   public int getNext()  throws Exception {
     String query = "select pr_seq.nextval from dual";
     DBResultSet dbrs = null;
      ResultSet rs = null;
     int next = 0;
     try {
       dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
       while (rs.next()) {
         next = (int)rs.getInt(1);
       }


     } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);      throw e;
       } finally{
             dbrs.close();

     }
     return next;
   }

   public void commit()  throws Exception  {
     DBResultSet dbrs = null;
      ResultSet rs = null;
     try {
       dbrs = db.doQuery("commit");   rs=dbrs.getResultSet();


     } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);  throw e;
       } finally{
             dbrs.close();

     }
     setInsertStatus(false);
   }

   public void rollback()  throws Exception  {
     DBResultSet dbrs = null;
      ResultSet rs = null;
     try {
     dbrs = db.doQuery("rollback");   rs=dbrs.getResultSet();


      } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);   throw e;
       } finally{
             dbrs.close();

     }
     setInsertStatus(false);
   }

   public boolean isIssued() {
     return pr_status.equalsIgnoreCase("posubmit");
   }
}
