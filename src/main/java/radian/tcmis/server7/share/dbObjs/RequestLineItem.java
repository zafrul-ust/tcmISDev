/*
SQLWKS> describe request_line_item
Column Name                    Null?    Type
------------------------------ -------- ----
PR_NUMBER                      NOT NULL NUMBER(38)
LINE_ITEM                      NOT NULL VARCHAR2(30)
ITEM_ID                                 NUMBER(38)
APPLICATION                             VARCHAR2(30)
SHIP_TO_LOCATION_ID                     VARCHAR2(30)
VENDOR_QUALIFIER                        VARCHAR2(30)
PROPOSED_VENDOR                         NUMBER(38)
QUANTITY                                NUMBER(38)
QUOTED_PRICE                            NUMBER(12,2)
REMARK                                  VARCHAR2(30)
REQUIRED_DATETIME                       DATE
PO_NUMBER                               VARCHAR2(30)
RELEASE_NUMBER                          VARCHAR2(30)
FAC_PART_NO                             VARCHAR2(30)
RELEASE_DATE                            DATE
CRITICAL                                VARCHAR2(1)
LAST_UPDATED                            DATE
LAST_UPDATED_BY                         NUMBER
DOC_NUM                                 NUMBER
DOC_STATE                               VARCHAR2(16)
SALES_ORDER                             NUMBER
NOTES                                   VARCHAR2(400)
DELIVERY_POINT                          VARCHAR2(30)
DELIVERY_TYPE                           VARCHAR2(20)
DELIVERY_QUANTITY                       NUMBER
DELIVERY_FREQUENCY                      VARCHAR2(20)
CHARGE_TYPE                             VARCHAR2(12)
RELAX_SHELF_LIFE
CANCEL_REQUEST                          DATE
CANCEL_REQUESTER                        NUMBER
CANCEL_STATUS                           VARCHAR2(10)
CANCEL_AUTHORIZER                       NUMBER(38)
CANCEL_ACTION_DATE                      DATE
*/
package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import java.math.BigDecimal;
import java.util.*;
import java.sql.*;
import radian.tcmis.server7.share.db.*;
public class RequestLineItem {

   public static final int STRING = 0;
   public static final int INT = 1;
   public static final int DATE = 2;
   public static final int NULLVAL = 3;
   public static final int BIGDECIMAL = 4;

   protected TcmISDBModel db;
   protected Integer pr_number;
   protected String line_item;
   protected String application;
   protected String application_desc;
   protected Integer item_id;
   protected String item_desc;
   protected String ship_to_location;
   protected String vendor_qualifier;
   protected String vendor_qualifier_desc;
   protected Integer proposed_vendor;
   protected BigDecimal quantity;
   protected float quoted_price;
   protected String remark;
   protected String required_datetime;
   protected String po_number;
   protected String release_number;
   protected String fac_part_no;
   protected boolean insertStatus;
   protected String release_date;
   protected String critical;
   protected String doc_num;
   protected String doc_state;
   protected String sales_order;
   protected String notes;
   protected String delivery_point;
   protected String delivery_type;
   protected String delivery_qty;
   protected String delivery_frequency;
   protected String charge_type;
   protected String relax_shelf_life;
   protected String cancel_request;
   protected Integer cancel_requestor;
   protected String cancel_status;
   protected Integer cancel_authorizer;
   protected String cancel_action_date;
   protected String item_type;
   protected String catalog_name;
   protected String packaging;
   protected String scrap;
   protected String exampleItemId;
   protected String useApprover;
   protected String useApprovalStatus;
   protected String useApprovalDate;
   protected String useApprovalComment;
   protected String dpas;
   protected String partGroupNo;
   protected String inventoryGroup;
   protected String catalogID;
   protected String currencyID;
   protected String orderQuantityRule;
   protected String customerRequisitionNumber;
   protected String requested_item_type;
	protected String catalogCompanyId;
	protected String companyId;

	public RequestLineItem()  throws java.rmi.RemoteException {

   }

   public RequestLineItem(TcmISDBModel db)  throws java.rmi.RemoteException {
     this.db = db;
   }

   public void setDB(TcmISDBModel db){
     this.db = db;
   }

   public void setPRNumber(int num) {
     this.pr_number = new Integer(num);
   }

   public Integer getPRNumber() {
     return this.pr_number;
   }

   public void setLineItem(String id) {
     this.line_item = id;
   }

   public String getLineItem() {
     return this.line_item;
   }

   public void setApplication(String app) {
     this.application = app;
   }

   public String getApplication() {
     return this.application;
   }

   public void setItemId(int id) {
     this.item_id = new Integer(id);
   }

   public Integer getItemId() {
     return this.item_id;
   }

   public void setShipToLocation(String num) {
     this.ship_to_location = num;
   }

   public String getShipToLocation() {
     return this.ship_to_location;
   }

   public void setVendorQualifier(String id) {
     this.vendor_qualifier = id;
   }

   public String getVendorQualifier() {
     return this.vendor_qualifier;
   }

   public void setProposedVendor(int id) {
     this.proposed_vendor = new Integer(id);
   }

   public Integer getProposedVendor() {
     return this.proposed_vendor;
   }

   public void setQuantity(BigDecimal num) {
     this.quantity = num;
   }

   public BigDecimal getQuantity() {
     return this.quantity;
   }

   public void setQuotedPrice(float num) {
     this.quoted_price = num;
   }

   public float getQuotedPrice() {
     return this.quoted_price;
   }

   public void setRemark(String id) {
     this.remark = id;
   }

   public String getRemark() {
     return this.remark;
   }

   public void setRequiredDatetime(String id) {
     this.required_datetime = id;
   }

   public String getRequiredDatetime() {
     return this.required_datetime;
   }

   public void setPONumber(String id) {
     this.po_number = id.trim();
   }

   public String getPONumber() {
     return this.po_number;
   }
   public void setDocNum(String id) {
     this.doc_num = id;
   }

   public String getDocNum() {
     return this.doc_num;
   }

   public void setDocState(String id) {
     this.doc_state = id;
   }

   public String getDocState() {
     return this.doc_state;
   }

   public void setSalesOrder(String id) {
     this.sales_order = id;
   }

   public String getSalesOrder() {
     return this.sales_order;
   }

   public void setReleaseNumber(String id) {
     this.release_number = id.trim();
   }

   public void setReleaseDate(String date) {
     this.release_date = date;
   }
   public String getReleaseNumber() {
     return this.release_number;
   }

   public String getReleaseDate() {
     return this.release_date;
   }
   public void setFacPartNo(String id) {
     this.fac_part_no = id;
   }

   public String getFacPartNo() {
     return this.fac_part_no;
   }

   public void setCritical(String c) {
     this.critical = c;
   }

   public String getCritical() {
     return this.critical;
   }

   public void setNotes(String c) {
     this.notes = c;
   }

   public String getNotes() {
     return this.notes;
   }

   public void setDeliveryPoint(String c) {
     this.delivery_point = c;
   }

   public String getDeliveryPoint() {
     return this.delivery_point;
   }

   public void setDeliveryType(String c) {
     this.delivery_type = c;
   }
   public String getDeliveryType() {
     return this.delivery_type;
   }
   public void setDeliveryQty(String c) {
     this.delivery_qty = c;
   }

   public String getDeliveryQty() {
     return this.delivery_qty;
   }
   public void setDeliveryFrequency(String c) {
     this.delivery_frequency = c;
   }

   public String getDeliveryFrequency() {
     return this.delivery_frequency;
   }
   public void setChargeType(String c) {
     this.charge_type = c;
   }

   public String getChargeType() {
     return this.charge_type;
   }

   public void setRelaxShelfLife(String c) {
     this.relax_shelf_life = c;
   }

   public String getRelaxShelfLife() {
     return this.relax_shelf_life;
   }

   public String getCancelRequestDate(){
     return cancel_request;
   }
   public void setCancelRequestDate(String s){
     cancel_request = s;
   }
   public Integer getCancelRequestor(){
     return cancel_requestor;
   }
   public void setCancelRequestor(Integer s){
     cancel_requestor = s;
   }
   public String getCancelStatus(){
     return cancel_status;
   }
   public void setCancelStatus(String s){
     cancel_status = s;
   }
   public Integer getCancelAuthorizer(){
     return cancel_authorizer;
   }
   public void setCancelAuthorizer(Integer s){
     cancel_authorizer = s;
   }
   public String getCancelActionDate(){
     return cancel_action_date;
   }
   public void setCancelActionDate(String s){
     cancel_action_date = s;
   }
   public String getItemType(){
     return item_type;
   }
   public void setItemType(String s){
     item_type = s;
   }
   public void setPackaging(String s) {
      packaging = s;
   }
   public String getPackaging() {
      return packaging;
   }
   public void setCatalogName(String s) {
      catalog_name = s;
   }
   public String getCatalogName() {
      return catalog_name;
   }
   public void setScrap(String s) {
      scrap = s;
   }
   public String getScrap() {
      return scrap;
   }
   public void setExampleItemId(String s) {
    exampleItemId = s;
   }
   public String getExampleItemId() {
    return exampleItemId;
   }

   public void setUseApprover(String s) {
    this.useApprover = s;
   }
   public String getUseApprover() {
    return this.useApprover;
   }
   public void setUseApprovalStatus(String s) {
    this.useApprovalStatus = s;
   }
   public String getUseApprovaltatus() {
    return this.useApprovalStatus;
   }
   public void setUseApprovalDate(String s) {
    this.useApprovalDate = s;
   }
   public String getUseApprovalDate() {
    return this.useApprovalDate;
   }
   public void setUseApprovalComment(String s) {
    this.useApprovalComment = s;
   }
   public String getUseApprovalComment() {
    return this.useApprovalComment;
   }

   public void setDPAS(String s) {
    this.dpas = s;
   }
   public String getDPAS() {
    return this.dpas;
   }
   public String getPartGoupNo() {
    return this.partGroupNo;
   }
   public void setPartGroupNo(String s) {
    this.partGroupNo = s;
   }
   public String getInventoryGroup() {
    return this.inventoryGroup;
   }
   public void setInventoryGroup(String s) {
    this.inventoryGroup = s;
   }
   public String getCatalogID() {
    return this.catalogID;
   }
   public void setCatalogID(String s) {
    this.catalogID = s;
   }

   public void setCurrencyID(String s) {
     this.currencyID = s;
   }
   public String getCurrencyID() {
     return this.currencyID;
   }

   public void setOrderQuantityRule(String s) {
     this.orderQuantityRule = s;
   }
   public String getOrderQuantityRule() {
     return this.orderQuantityRule;
   }

   public void setCustomerRequisitionNumber(String s) {
     this.customerRequisitionNumber = s;
   }
   public String getCustomerRequisitionNumber() {
     return this.customerRequisitionNumber;
   }

   public String getRequestedItemType(){
     return requested_item_type;
   }
   public void setRequestedItemType(String s){
     requested_item_type = s;
   }

   public String getCatalogCompanyId() {
    return this.catalogCompanyId;
   }
   public void setCatalogCompanyId(String s) {
    this.catalogCompanyId = s;
   }

	public String getCompanyId() {
    return this.companyId;
   }
   public void setCompanyId(String s) {
    this.companyId = s;
   }

	public String getItemDesc()  throws Exception {
     String query = new String("select item_desc from jde_item where item_id = " + this.item_id.toString());
     DBResultSet dbrs = null;
      ResultSet rs = null;
     try {
     dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
	   if (rs.next()) {
	     this.item_desc = rs.getString(1);
	   }


	   return this.item_desc;
     } catch (Exception e) {
   	 e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);   throw e;
       } finally{
             dbrs.close();

     }
   }

   public String getVendorQualifierDesc()  throws Exception {
     String query = new String("select vendor_qualifier_desc from vv_vendor_qualifier where vendor_qualifier = '" + this.vendor_qualifier + "'");
     DBResultSet dbrs = null;
      ResultSet rs = null;
     try {
    	 dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
	     if (rs.next()) {
	       this.vendor_qualifier_desc = rs.getString(1);
	      }


	      return this.vendor_qualifier_desc;
     }catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);   throw e;
       } finally{
             dbrs.close();

     }
   }

   public String getApplicationDesc(String fac)  throws Exception {
     String query = new String("select application_desc from fac_loc_app where application = '" + this.application + "' and facility_id = '"+fac+"'");
     DBResultSet dbrs = null;
      ResultSet rs = null;
     try {
	     dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
	     if (rs.next()) {
	       this.application_desc = rs.getString(1);
	     }


	     return this.application_desc;
     }catch (Exception e) {
	     e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);   throw e;
       } finally{
             dbrs.close();

     }
   }

   public int getNumLines(int num)  throws Exception {
     DBResultSet dbrs = null;
      ResultSet rs = null;
     Integer N = new Integer(num);
     int ret = 0;
     String query = "select count(*) from request_line_item where pr_number = " + N.toString();
     try {
        dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
        while(rs.next()) {
          ret = (int)rs.getInt(1);
        }


     } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);   throw e;
       } finally{
             dbrs.close();

     }
     return ret;
   }

   public int getCount()  throws Exception {
     DBResultSet dbrs = null;
      ResultSet rs = null;
     String query = new String("select count(*) from request_line_item where pr_number = " + this.pr_number.toString() + " and line_item = '" + this.line_item + "'");
     int num = 0;
     try {
        dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
        while(rs.next()) {
          num = (int)rs.getInt(1);
          break;
        }


     } catch (Exception e) { e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);  throw e;
       } finally{
             dbrs.close();
             }
     return num;
   }

   //6-18-01 this method is for SWA only
   public Hashtable getRequestLineFromPO(String myPo) throws Exception {
    Hashtable h = new Hashtable();
    DBResultSet dbrs = null;
    ResultSet rs = null;
    String query = "select pr_number,line_item from request_line_item where po_number = '"+ myPo + "'";
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while(rs.next()) {
        h.put("REQ_NUM",rs.getString("pr_number"));
        h.put("LINE_NUM",rs.getString("line_item"));
      }
    }catch (Exception e) {
      e.printStackTrace();
      throw e;
    }finally {
      dbrs.close();
    }
    return h;
   }


   public void load()  throws Exception {
     DBResultSet dbrs = null;
     ResultSet rs = null;
     String query = "select pr_number,line_item,decode(item_id,null,example_item_id,item_id) item_id,application,ship_to_location_id,vendor_qualifier,proposed_vendor,quantity,"+
              "quoted_price,remark,to_char(required_datetime,'mm/dd/yyyy') required_datetime,critical,scrap,example_item_id,doc_num,doc_state,sales_order,po_number,release_number,release_date,fac_part_no,notes,"+
              "delivery_point,delivery_type,delivery_quantity,delivery_frequency,charge_type,relax_shelf_life,cancel_request,cancel_requester,cancel_status,cancel_authorizer,"+
              "cancel_action_date,item_type,use_approval_status,use_approval_comment,use_approval_date,use_approver,nvl(dpas_code,'None') dpas_code"+
              ",catalog_id,catalog_company_id,part_group_no,inventory_group,currency_id,order_quantity_rule,customer_requisition_number,requested_item_type,company_id"+
              " from request_line_item where pr_number = " + this.pr_number.toString() + " and line_item = '" + this.line_item + "'";
     try {
        dbrs = db.doQuery(query);
        rs=dbrs.getResultSet();
        while(rs.next()) {
          this.setPRNumber((int)rs.getInt("pr_number"));
          this.setLineItem(rs.getString("line_item"));
          this.setItemId((int)rs.getInt("item_id"));
          //trong 3/7/00
          this.setItemType(BothHelpObjs.makeBlankFromNull(rs.getString("item_type")));
          this.setApplication(rs.getString("application"));
          this.setShipToLocation(rs.getString("ship_to_location_id"));
          this.setVendorQualifier(rs.getString("vendor_qualifier"));
          this.setProposedVendor((int)rs.getInt("proposed_vendor"));
          this.setQuantity(rs.getBigDecimal("quantity"));
          this.setQuotedPrice((float)rs.getFloat("quoted_price"));
          this.setRemark(rs.getString("remark"));
          this.setRequiredDatetime(rs.getString("required_datetime"));
          this.setCritical(rs.getString("critical"));
          this.setScrap(rs.getString("scrap"));
          this.setExampleItemId(rs.getString("example_item_id"));
          this.setDocNum(BothHelpObjs.makeBlankFromNull(rs.getString("doc_num")));
          this.setDocState(BothHelpObjs.makeBlankFromNull(rs.getString("doc_state")));
          this.setSalesOrder(rs.getString("sales_order"));
          if (rs.getString("po_number") != null){
            this.setPONumber(rs.getString("po_number"));
          } else {
            this.setPONumber ("");
          }

          if (rs.getString("release_number") != null){
            this.setReleaseNumber(rs.getString("release_number"));
          } else {
            this.setReleaseNumber ("");
          }
          if (rs.getString("release_date") != null){
            this.setReleaseDate(rs.getString("release_date"));
          } else {
            this.setReleaseDate("");
          }
          this.setFacPartNo(rs.getString("fac_part_no"));
          this.setNotes(rs.getString("notes"));

          this.setDeliveryPoint(rs.getString("delivery_point"));
          this.setDeliveryType(rs.getString("delivery_type"));
          this.setDeliveryQty(rs.getString("delivery_quantity"));
          this.setDeliveryFrequency(rs.getString("delivery_frequency"));
          this.setChargeType(rs.getString("charge_type"));

          if (rs.getString("relax_shelf_life") != null){
            this.setRelaxShelfLife(rs.getString("release_date"));
          } else {
            this.setRelaxShelfLife("");
          }
          if (rs.getString("cancel_request") != null){
            this.setCancelRequestDate(rs.getString("cancel_request"));
          } else {
            this.setCancelRequestDate("");
          }
          if (rs.getString("cancel_requester") != null){
            this.setCancelRequestor(new Integer(rs.getInt("cancel_requester")));
          } else {
            this.setCancelRequestor(new Integer(0));
          }
          if (rs.getString("cancel_status") != null){
            this.setCancelStatus(rs.getString("cancel_status"));
          } else {
            this.setCancelStatus("");
          }
          if (rs.getString("cancel_authorizer") != null){
            this.setCancelAuthorizer(new Integer(rs.getString("cancel_authorizer")));
          } else {
            this.setCancelAuthorizer(new Integer(0));
          }
          if (rs.getString("cancel_action_date") != null){
            this.setCancelActionDate(rs.getString("cancel_action_date"));
          } else {
            this.setCancelActionDate("");
          }
          if (rs.getString("item_type") != null){
            this.setCancelActionDate(rs.getString("item_type"));
          } else {
            this.setCancelActionDate("");
          }

          this.setUseApprovalComment(BothHelpObjs.makeBlankFromNull(rs.getString("use_approval_comment")));
          this.setUseApprovalDate(BothHelpObjs.makeBlankFromNull(rs.getString("use_approval_date")));
          this.setUseApprovalStatus(BothHelpObjs.makeBlankFromNull(rs.getString("use_approval_status")));
          this.setUseApprover(BothHelpObjs.makeBlankFromNull(rs.getString("use_approver")));
          this.setDPAS(BothHelpObjs.makeBlankFromNull(rs.getString("dpas_code")));
          this.setCatalogID(BothHelpObjs.makeBlankFromNull(rs.getString("catalog_id")));
          this.setPartGroupNo(BothHelpObjs.makeBlankFromNull(rs.getString("part_group_no")));
          this.setInventoryGroup(BothHelpObjs.makeBlankFromNull(rs.getString("inventory_group")));
          this.setCurrencyID(BothHelpObjs.makeBlankFromNull(rs.getString("currency_id")));
          this.setOrderQuantityRule(BothHelpObjs.makeBlankFromNull(rs.getString("order_quantity_rule")));
          this.setCustomerRequisitionNumber(BothHelpObjs.makeBlankFromNull(rs.getString("customer_requisition_number")));
          this.setRequestedItemType(BothHelpObjs.makeBlankFromNull(rs.getString("requested_item_type")));
			 this.setCatalogCompanyId(BothHelpObjs.makeBlankFromNull(rs.getString("catalog_company_id")));
			 this.setCompanyId(BothHelpObjs.makeBlankFromNull(rs.getString("company_id")));
		  }
     } catch (Exception e) { e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);  throw e;
       } finally{
             dbrs.close();
             }
     return;
   }

   public void insert()  throws Exception {
     String query = "insert into request_line_item (pr_number,line_item,application,ship_to_location_id,quantity,fac_part_no,item_type,catalog_id,example_packaging,critical,scrap,part_group_no,requested_item_type)";
     query += " values (" + pr_number.toString() + ",'" + line_item + "','" + application + "','" + ship_to_location + "'," + quantity.toString() + ",'" + fac_part_no + "','"+item_type+"','"+catalog_name+"','"+packaging+"','n','n',"+partGroupNo+",'"+item_type+"')";
     try {
       db.doUpdate(query);
     } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
       setInsertStatus(false);   throw e;
       }
   }
   public void insert(String facID)  throws Exception {
     String query = "insert into request_line_item (pr_number,line_item,application,ship_to_location_id,quantity,fac_part_no,item_type,catalog_id,example_packaging,critical,scrap,part_group_no,delivery_point,inventory_group,requested_item_type)";
     query += " values (" + pr_number.toString() + ",'" + line_item + "','" + application + "',(select location_id from fac_loc_app where application = '"+application+"' and facility_id = '"+facID+"')," + quantity.toString() + ",'" + fac_part_no + "','"+item_type+"','"+catalog_name+"','"+packaging+"','n','n',"+partGroupNo+",";
     query += "(select delivery_point from fac_loc_app where application = '"+application+"' and facility_id = '"+facID+"'),'"+inventoryGroup+"','"+item_type+"')";
     try {
       db.doUpdate(query);
     } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
       setInsertStatus(false);   throw e;
       }
   }

   //trong 2-15-01 this new method will also insert quotes_price and critical flag to -> N into request_line_item
   public void insertNew()  throws Exception {
     String query = new String("insert into request_line_item (pr_number,line_item,application,item_id,ship_to_location_id,quantity,fac_part_no,item_type,quoted_price,critical,requested_item_type)");
     query = query + new String(" values (" + pr_number.toString() + ",'" + line_item + "','" + application + "'," + item_id.toString() + ",'" + ship_to_location + "'," + quantity.toString() + ",'" + fac_part_no + "','"+item_type+"',"+this.getQuotedPrice()+",'n','"+item_type+"')");

     //System.out.println("\n\n------------ creating mr: "+query);
     try {
       db.doUpdate(query);
     } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
       setInsertStatus(false);   throw e;
       }
   }
   //trong 4-18-01 this new method will also insert notes, po and critical flag to -> N into request_line_item
   public void insertSWA()  throws Exception {
     String query = new String("insert into request_line_item (pr_number,line_item,application,item_id,ship_to_location_id,quantity,fac_part_no,item_type,notes,po_number,critical,quoted_price,required_datetime,requested_item_type)");
     query = query + new String(" values (" + pr_number.toString() + ",'" + line_item + "','" + application + "'," + item_id.toString() + ",'" + ship_to_location + "'," + quantity.toString() + ",'" + fac_part_no + "','"+item_type+"','"+this.getNotes()+"','"+this.getPONumber()+"','n','"+this.getQuotedPrice()+"',to_date(replace('"+this.getRequiredDatetime()+"','.0',''),'yyyy-mm-dd hh24:mi:ss'),'"+item_type+"')");
     try {
       db.doUpdate(query);
     } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
       setInsertStatus(false);   throw e;
       }
   }


   //trong 3/3/00
   public void insertEngEval()  throws Exception {
     String query = new String("insert into request_line_item (pr_number,line_item,application,item_id,quantity,fac_part_no,item_type,requested_item_type)");
     query = query + new String(" values (" + pr_number.toString() + ",'" + line_item + "','" + application + "'," + item_id.toString() + "," + quantity.toString() + ",'" + fac_part_no + "','"+item_type+"','"+item_type+"')");
     try {
       db.doUpdate(query);
     } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
       setInsertStatus(false);   throw e;
       }
   }

	public void rejectUsage(String userID,String comment) throws Exception {
    //only update this line if it is pending otherwise leave it alone
    String temp = "select count(*) from request_line_item where use_approval_status = 'pending' and pr_number = "+this.getPRNumber()+ " and fac_part_no = '"+this.getFacPartNo()+"'";
    int count = 0;
    try {
      count = DbHelpers.countQuery(db,temp);
    }catch (Exception e) {
      e.printStackTrace();
    }
    if (count > 0) {
      String query;
      if (BothHelpObjs.isBlankString(comment)) {
        query = "update request_line_item set use_approval_status = 'rejected',use_approval_date = SYSDATE,use_approver = "+userID;
      }else {
        query = "update request_line_item set use_approval_status = 'rejected',use_approval_date = SYSDATE,use_approver = "+userID+
                ",use_approval_comment = '"+BothHelpObjs.changeSingleQuotetoTwoSingleQuote(comment)+"'";
      }
      query += ",request_line_status = 'Rejected',category_status = 'Rejected',last_updated = sysdate"+
               ",last_updated_by = "+userID+" where pr_number = "+this.getPRNumber()+
               " and fac_part_no = '"+this.getFacPartNo()+"' and line_item = '"+this.getLineItem()+"'";
      try {
        db.doUpdate(query);
      }catch (Exception e) {
        e.printStackTrace();
      }
    }
   }

   public void approveUsage(String userID) throws Exception {
    //only update this line if it is pending otherwise leave it alone
    String temp = "select count(*) from request_line_item where use_approval_status = 'pending' and pr_number = "+this.getPRNumber()+ " and fac_part_no = '"+this.getFacPartNo()+"'";
    int count = 0;
    try {
      count = DbHelpers.countQuery(db,temp);
    }catch (Exception e) {
      e.printStackTrace();
    }
    if (count > 0) {
      String query = "update request_line_item set use_approval_status = 'approved',use_approval_date = SYSDATE,use_approver = "+userID;
      query += ",last_updated = sysdate,last_updated_by = "+userID+" where pr_number = "+this.getPRNumber()+
               " and fac_part_no = '"+this.getFacPartNo()+"' and line_item = '"+this.getLineItem()+"'";
      try {
        db.doUpdate(query);
      }catch (Exception e) {
        e.printStackTrace();
      }
    }
   }

   public void releaseLineItem(int rNum, String additionalWhere) throws Exception {
     DBResultSet dbrs = null;
     ResultSet rs = null;
     try {
       //first update request line item statuses
       String query = "update request_line_item set release_date = sysdate,request_line_status = 'In Progress',category_status = 'Open'"+
                      " where release_date is null and pr_number = "+rNum+" "+additionalWhere;
       db.doUpdate(query);
       //next call allocation procedure for released line in the last 10 minutes
       query = "select pr_number,line_item from request_line_item where release_date is not null and"+
               " release_date > sysdate - .001 and request_line_status = 'In Progress' and category_status = 'Open' and pr_number = "+rNum;
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while(rs.next()) {
         String prNumber = rs.getString("pr_number");
         String lineItem = rs.getString("line_item");
         allocateLineItem(prNumber,lineItem);
       }
     }catch (Exception e) {
       e.printStackTrace();
       throw e;
     }
   } //end of method

   void allocateLineItem(String prNumber, String lineItem) throws Exception {
     try {
       //System.out.println("----- allocating: "+prNumber+" - "+lineItem);
       String[] args = new String[2];
       args[0] = prNumber;
       args[1] = lineItem;
       db.doProcedure( "p_line_item_allocate",args );
     }catch ( Exception dbe ) {
       radian.web.emailHelpObj.sendtrongemail("p_line_item_allocate (pr_number: " + prNumber + " line: " + lineItem + ")","Error while executing p_line_item_allocate");
       throw dbe;
     }
   } //end of method

   public void insert(String col,String val,int type)  throws Exception {

     if(col.equalsIgnoreCase("NOTES")) {
       val = HelpObjs.singleQuoteToDouble(val);
     }

     Integer I;
     DBResultSet dbrs = null;
      ResultSet rs = null;

     String query = new String("update request_line_item set " + col + "=");
     switch (type) {
       case INT:
         I = new Integer(val);
         query = query + I.intValue();
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
       case NULLVAL:
         query = query + null;
         break;
       default:
         query = query + "'" + val + "'";
         break;
     }
     query = query + " where pr_number = " + getPRNumber();
     query += " and line_item = "+getLineItem();
     try {
       //System.out.println("\n\n-------- update query: "+query);
       db.doUpdate(query);
     } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
        throw e;
       }
   }

   public void updateCritical(boolean crit, String prnumber,int userid,int itemid, int so) throws Exception {
      boolean isJde = false;
      String query = null;
      DBResultSet dbrs = null;

      try {
        Integer tmp = new Integer(prnumber);
      } catch (Exception e){
        isJde = true;
      }
      if (!isJde){
        RequestLineItem rq = new RequestLineItem(db);
        rq.setPRNumber((new Integer(prnumber)).intValue());
        isJde = rq.getCount()>0;
      }
      if (isJde){
        // for jde records
        /*
        SQLWKS> describe critical_reqs
        Column Name                    Null?    Type
        ------------------------------ -------- ----
        REQUEST_ID                              VARCHAR2(40)
        SALES_ORDER                             NUMBER
        */
        //delete first
        query = new String("delete critical_reqs where sales_order = "+ (new Integer(so)).toString());
        query = query + " and request_id = '"+prnumber+"'";
        try {
         db.doUpdate(query);


        } catch (Exception e) {
          e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
           throw e;
       }
        if (crit) {  // insert
          query = new String("insert into critical_reqs (request_id,sales_order) ");
          query = query + " values ('"+prnumber+"',"+(new Integer(so)).toString()+")";
          try {
             db.doUpdate(query);


          } catch (Exception e) {
            e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
             throw e;
       }
        }
        //return;
      }

      // for tcmIS
      query = new String("update request_line_item set critical = '"+(crit?"Y":"N")+"'");
      query = query + " , last_updated = SYSDATE , last_updated_by="+ (new Integer(userid)).toString();
      query = query + " where item_id = " + (new Integer(itemid)).toString() ;
      query = query + " and   pr_number = " + prnumber;
      try {
        db.doUpdate(query);

      } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
        throw e;
       }
      return;
   }

   public void delete()  throws Exception {
     String query = "delete request_line_item where pr_number = " + getPRNumber();
     DBResultSet dbrs = null;
     try {
       db.doUpdate(query);

     } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);  throw e;
       }
   }


   public void updateOne(String col,String val,int type)  throws Exception {
     if(col.equalsIgnoreCase("NOTES")) {
       val = HelpObjs.singleQuoteToDouble(val);
     }
     Integer I;
     String query = new String("update request_line_item set " + col + "=");
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
      case BIGDECIMAL:
        query = query + val;
        break;
       default:
         query = query + "'" + val + "'";
         break;
     }
     query = query + " where pr_number = " + getPRNumber() + " and line_item = " + line_item;
     DBResultSet dbrs = null;

     try {
       db.doUpdate(query);
     } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
       setInsertStatus(false);  throw e;
       }
   }

   public void updateAll(String col,String val,int type)  throws Exception {
     Integer I;
     String query = new String("update request_line_item set " + col + "=");
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
     query = query + " where pr_number = " + getPRNumber() ;
     DBResultSet dbrs = null;
     try {
       db.doUpdate(query);
     } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
       setInsertStatus(false);  throw e;
       }
   }

   public void setInsertStatus(boolean b) {
     this.insertStatus = b;
   }

   public boolean getInsertStatus() {
     return this.insertStatus;
   }

   public void commit()  throws Exception {
     return;
   }

   public void rollback()  throws Exception  {
     return;
   }

   public void deleteLineItem(String lineItem)  throws Exception {
     int theLine = 0;
     Vector v = new Vector();
     try{
       theLine = Integer.parseInt(lineItem);
     }catch(Exception e) {e.printStackTrace();throw e;
       }

     try{
       //build a vector of all the PRAccount objects
       PRAccount pra = new PRAccount(db);
       pra.setPRNumber(pr_number.intValue());
       v = pra.retrieveAll();

       // delete everything from PRAccount with the same pr_number, line_item
       pra.delete();
     }catch(Exception e){e.printStackTrace();throw e;
       }
     // now delete the rli
     String query = "delete request_line_item where pr_number = " + getPRNumber();
     query =  query + " and line_item = '" + lineItem + "'";
     DBResultSet dbrs = null;

     try {
        //System.out.println("\n\n+++++++ deleting request_line_item :"+query);
        db.doUpdate(query);
     } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);    throw e;
       }

     //move all rli lines up
     query = "update request_line_item set line_item = to_char(to_number(line_item)-1)";
     query = query + " where to_number(line_item) > " + lineItem + " and pr_number = " + getPRNumber();
     try {
        //System.out.println("\n\n+++++++ updating request_line_item :"+query);
        db.doUpdate(query);
     } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);   throw e;
       }

     //put back the PRAccount objects and move up the line_item
     for(int x=0;x<v.size();x++) {
       PRAccount p = (PRAccount) v.elementAt(x);
       try{
         Integer i = new Integer(p.getLineItem());
			//skip deleted line
			if (theLine == i.intValue()) continue;
			
			if(theLine < i.intValue()) {
           i = new Integer(i.intValue() - 1);
         }
			p.setLineItem(i.toString());
         p.insert(); 
		 }catch(Exception e){continue;}
     }
   }

   public void updatePONumber(String val)  throws Exception {
     Integer I;
     String query = new String("update request_line_item set po_number = '" + val + "' ");

     query = query + " where pr_number = " + getPRNumber() + " and line_item = " + line_item;
     DBResultSet dbrs = null;
     try {
        db.doUpdate(query);

     } catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
       setInsertStatus(false);  throw e;
       }

     // check fac_app_po for new po's
     load();

     String app = getApplication();
     PurchaseRequest pR = new PurchaseRequest(db);
     pR.setPRNumber(getPRNumber().intValue());
     pR.load();
     String fac = pR.getFacilityId();
     int user = pR.getRequestor().intValue();

     query = "select count(*) from fac_app_po where facility_id = '"+fac+"' ";
     query = query + " and application ='"+app+"' and po_number = '"+val+"' ";

     ResultSet rs = null;
     int num = 0;
     try {
        dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
        while(rs.next()) {
          num = (int)rs.getInt(1);
          break;
        }


     } catch (Exception e) { e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);  throw e;
       } finally{
             dbrs.close();
             }

     if (num > 0) return;

     // add po to fac_app_po
     query = "insert into fac_app_po (facility_id,application,po_number,remark,preferred) values ";
     query = query + "('"+fac+"','"+app+"','"+val+"','Inserted from tcmIS. Requestor:"+user+"','n')";

     try {
        db.doUpdate(query);   rs=dbrs.getResultSet();
     } catch (Exception e) {
       e.printStackTrace();
       HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
       throw e;
       }
   }

   public int getAllowedQty(Vector groups,String facId,String app,String part) throws Exception {
      /*
      SQLWKS> describe Allowed_quantity_view
      Column Name                    Null?    Type
      ------------------------------ -------- ----
      FACILITY_ID                             VARCHAR2(30)
      APPLICATION                             VARCHAR2(30)
      FAC_PART_NO                             VARCHAR2(30)
      USER_GROUP_ID                           VARCHAR2(30)
      QUANTITY                                NUMBER
      ALLOWED                                 NUMBER
      UNLIMITED                               VARCHAR2(1)
     */
     int allowed = 0;
     int maxAllowed = 0;
     String unlim = null;
     for (int i=0;i<groups.size();i++){
       String query = new String("select count(*) from allowed_quantity_view where ");
       query = query + "facility_id ='"+facId+"' and application = '"+app+"' ";
       query = query + " and fac_part_no = '"+part+"' and user_group_id ='"+groups.elementAt(i).toString()+"'";
       DBResultSet dbrs = null;
      ResultSet rs = null;
       int count = 0;
       try {
        dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
        while (rs.next()){
              count = (new Integer(rs.getInt(1))).intValue();
              break;
        }


       } catch (Exception e) {
        e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
         throw e;
       } finally{
             dbrs.close();

       }

       if (count == 0)  continue;

       query = new String("select allowed, unlimited from allowed_quantity_view where ");
       query = query + "facility_id ='"+facId+"' and application = '"+app+"' ";
       query = query + " and fac_part_no = '"+part+"' and user_group_id ='"+groups.elementAt(i).toString()+"'";

       rs = null;
       try {
        dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
        while (rs.next()){
           if (rs.getString("allowed") == null){
              allowed = 0;
           } else {
              allowed = (new Integer(rs.getInt("allowed"))).intValue();
           }
           unlim = (rs.getString("unlimited")==null||!rs.getString("unlimited").equalsIgnoreCase("y")?"N":"Y");
           if (unlim.equalsIgnoreCase("y")){
              break;
           }
        }


       } catch (Exception e) {
        e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
         throw e;
       } finally{
             dbrs.close();

       }
       if (unlim.equalsIgnoreCase("y")){
         maxAllowed = -1;
         break;
       }
       if (allowed > maxAllowed){
         maxAllowed = allowed;
       }
     }

     return maxAllowed;
   }

   public boolean  isReleaseNumUsed(String num) throws Exception {
     String query = "select count(*) from used_release_view where po_release = '"+num+"'";
     DBResultSet dbrs = null;
      ResultSet rs = null;
     int count = 0;
     try {
        dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
        while (rs.next()){
          count = (new Integer(rs.getInt(1))).intValue();
          break;
        }


     } catch (Exception e) {
        e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
         throw e;
       } finally{
             dbrs.close();

     }

     return count>0;
   }

   public String getDefNeedByDate() throws Exception {  // on mm/dd/yyyy
       PurchaseRequest pr = new PurchaseRequest(db);
       pr.setPRNumber(this.getPRNumber().intValue());
       pr.load();
       /*
       Catalog cat = new Catalog(db);
       cat.setItemId(this.getItemId().intValue());
       cat.setFacilityId(pr.getFacilityId());
       cat.load();
       */
       //System.out.println("-----NOT SUPPOSE TO USE THIS METHOD.  USE LINEITEMVIEW GETDEFNEEDBYDATE() INSTEAD!!!!!");
       Facility f = new Facility(db);
       f.setFacilityId(pr.getFacilityId());
       f.load();
       Facility hub = new Facility(db);
       hub.setFacilityId(f.getPreferredWare());
       hub.load();
       //Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(hub.getLocale()));
       Calendar calendar = Calendar.getInstance();
       String tmpQ = "select count(*) from catalog_part_inventory where cat_part_no = '"+this.getFacPartNo()+
                     "' and inventory_group = '"+this.getInventoryGroup()+"' and stocking_method = 'MM'";
      //if(BothHelpObjs.isMinMax(cat.getType())){  // 24 hours
      if (HelpObjs.countQuery(db,tmpQ) > 0) {
        if (f.getPreferredWare().equalsIgnoreCase("Salem")) {           //8-15 dealling with TIME ZONE
          calendar.add(Calendar.HOUR,1);
        }
        if (f.getPreferredWare().equalsIgnoreCase("Tucson")) {
          calendar.add(Calendar.HOUR,-2);
        }
        if (calendar.get(Calendar.HOUR_OF_DAY) < 13) {
          if ((calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) ||
              (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)) {
            calendar.add(Calendar.DATE,3);
          }else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            calendar.add(Calendar.DATE,2);
          }else {
            calendar.add(Calendar.DATE,1);
          }
        }else {
          if ((calendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) ||
              (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY)) {
            calendar.add(Calendar.DATE,4);
          }else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            calendar.add(Calendar.DATE,3);
          } else {
            calendar.add(Calendar.DATE,2);
          }
        }
       } else {     // 21 days
          if (calendar.get(Calendar.HOUR_OF_DAY) < 13) {
            if ((calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) ||
                (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)) {
              calendar.add(Calendar.DATE,24);
            }else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
              calendar.add(Calendar.DATE,23);
            }else {
              calendar.add(Calendar.DATE,21);
            }
          }else {
            if ((calendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) ||
              (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY)) {
              calendar.add(Calendar.DATE,25);
            }else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
              calendar.add(Calendar.DATE,24);
            }else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
              calendar.add(Calendar.DATE,23);
            } else {
              calendar.add(Calendar.DATE,22);
            }
          }
       }
       //if (calendar.get(Calendar.AM_PM) == Calendar.PM) calendar.roll(Calendar.DATE, true);
       String bdate = new String((calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.DATE)+"/"+calendar.get(Calendar.YEAR));
       return bdate;
   }

   public boolean isRequestedCanceled()throws Exception{
     try{
       String q = "select count(*) from request_line_item where pr_number = "+pr_number+" and line_item = '"+line_item+"' and ";
       q = q + "lower(cancel_status) = 'rqcancel'";
       return DbHelpers.countQuery(db,q) > 0;
     }catch(Exception e){e.printStackTrace();throw e;
       }
   }
   public boolean isCancelApproved()throws Exception{
     try{
       String q = "select count(*) from request_line_item where pr_number = "+pr_number+" and line_item = '"+line_item+"' and ";
       q = q + "lower(cancel_status) = 'canceled'";
       return DbHelpers.countQuery(db,q) > 0;
     }catch(Exception e){e.printStackTrace();throw e;
       }
   }
   public boolean isCancelRejected()throws Exception{
     try{
       String q = "select count(*) from request_line_item where pr_number = "+pr_number+" and line_item = '"+line_item+"' and ";
       q = q + "lower(cancel_status) = 'nocancel'";
       return DbHelpers.countQuery(db,q) > 0;
     }catch(Exception e){e.printStackTrace();throw e;
       }
   }

   public static boolean linePendingUseApprover(TcmISDBModel db, int reqNum, String useApprovalType) throws Exception {
    boolean result = false;
	 String query = "";
	 if ("useLimit".equalsIgnoreCase(useApprovalType)) {
	 	query = "select use_approval_status status from request_line_item where pr_number = "+reqNum;
	 }else {
		query = "select list_approval_status status from request_line_item where pr_number = "+reqNum;
	 }
	 DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while(rs.next()) {
        if ("Pending".equalsIgnoreCase(rs.getString("status"))) {
          result = true;
          break;
        }
      }
    }catch (Exception e) {
      e.printStackTrace();
    }finally {
      dbrs.close();
    }
    return result;
   }

	public static boolean allLineRejectedByUseApprover(TcmISDBModel db, int reqNum, String useApprovalType) throws Exception {
    boolean result = true;
	 String query = "";
	 if ("useLimit".equalsIgnoreCase(useApprovalType)) {
	 	query = "select use_approval_status status from request_line_item where pr_number = "+reqNum;
	 }else {
		query = "select list_approval_status status from request_line_item where pr_number = "+reqNum;
	 }
	 DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);
      rs=dbrs.getResultSet();
      while(rs.next()) {
        if (!"rejected".equalsIgnoreCase(rs.getString("status"))) {
          result = false;
          break;
        }
      }
    }catch (Exception e) {
      e.printStackTrace();
    }finally {
      dbrs.close();
    }
    return result;
   }

	public static boolean needUseApproval(TcmISDBModel db, int reqNum) throws Exception {
    boolean result = false;
    String query = "select count(*) from request_line_item where (use_approval_status = 'pending' or list_approval_status = 'Pending') and pr_number = "+reqNum;
    try {
      return (DbHelpers.countQuery(db,query) > 0);
    }catch (Exception e) {
      e.printStackTrace();
    }
    return result;
   }

   public boolean approveCancelRequest(int authorizer)throws Exception{
     DBResultSet dbrs = null;
     ResultSet rs = null;
     try{
       if(!"rqcancel".equalsIgnoreCase(this.getCancelStatus())){
         return false;
       }
       // update request_line_item
       String q = "update request_line_item set cancel_authorizer = "+authorizer+",cancel_action_date=sysdate,"+
	                "cancel_status = 'canceled',request_line_status = 'Cancelled',cancel_comment =decode(cancel_comment,null,'',cancel_comment||'; ')||'Auto approved by tcmIS'"+
                  "where pr_number = "+getPRNumber()+" and line_item = '"+getLineItem()+"'";
       db.doUpdate(q);

       //cancel buy order
       q = "update buy_order set status = 'Cancel' where mr_number = "+getPRNumber()+" and mr_line_item = '"+getLineItem()+"'";
       db.doUpdate(q);

       //send message to Requester and cancel requestor (if different).
       String needDate = BothHelpObjs.formatDate("toCharfromDB",this.getRequiredDatetime());
       String s = "Material Request "+getPRNumber()+",line "+getLineItem()+" has been canceled.";
       String m = "Material Request "+getPRNumber()+",line "+getLineItem()+" has been canceled.\n\n";
       m = m + "This order was for Qty:"+this.getQuantity().toString()+"\tPart Number:"+this.getFacPartNo()+"\tNeed Date:"+needDate+".\n";
       m = m + "\nThis order WILL NOT BE DELIVERED.\n\n";
       m = m + "If this is not correct, please contact your Radian Hub Customer Service Representative.\n";
       String reqEmail = "";
       String cxReqEmail = "";

       q = "select p.email requestor_email,p2.email rqcancel_email from request_line_item b,purchase_request a,personnel p,personnel p2"+
           " where b.pr_number = "+getPRNumber()+" and b.line_item = '"+getLineItem()+"' and"+
           " a.pr_number = b.pr_number and a.requestor = p.personnel_id and b.cancel_requester = p2.personnel_id(+)";
       dbrs = db.doQuery(q);
       rs=dbrs.getResultSet();
       while(rs.next()) {
        reqEmail = BothHelpObjs.makeBlankFromNull(rs.getString("requestor_email"));
        cxReqEmail = BothHelpObjs.makeBlankFromNull(rs.getString("rqcancel_email"));
       }

       String[] sa = null;
       //5-25-01
       if (!BothHelpObjs.isBlankString(reqEmail)) {
        if(reqEmail.equals(cxReqEmail)){
          sa = new String[]{reqEmail};
        }else{
          if (!BothHelpObjs.isBlankString(cxReqEmail)) {
            sa = new String[]{reqEmail,cxReqEmail};
          }else {
            sa = new String[]{reqEmail};
          }
        }
       }else if (!BothHelpObjs.isBlankString(cxReqEmail)) {
        sa = new String[]{cxReqEmail};
       }else {
        sa = new String[]{"trong_ngo@urscorp.com"};
       }
       HelpObjs.sendMail(s,m,sa);

     }catch(Exception e){
      e.printStackTrace();
      HelpObjs.sendMail(db,"Approve cancel failed","Error while trying to approve cancellation mr_number: "+getPRNumber()+",line: "+getLineItem(),86030,false);
      throw e;
     }finally {
      dbrs.close();
     }
     return true;
   }

   public boolean rejectCancelRequest(int authorizer,String reason)throws Exception{
     DBResultSet dbrs = null;
     ResultSet rs = null;
     try{
       if(!"rqcancel".equalsIgnoreCase(this.getCancelStatus())){
         return false;
       }
       // mark as rejected
       String s = reason.length()>200?reason.substring(0,199):reason;
       String q = "update request_line_item "+
                  "set cancel_status = 'nocancel', cancel_authorizer = "+authorizer+", cancel_action_date = sysdate, cancel_comment =decode(cancel_comment,null,'',cancel_comment||'; ')||'"+s+"'"+
                  ",request_line_status = 'In Progress' where pr_number = "+pr_number+" and line_item = '"+line_item+"'";
       db.doUpdate(q);


       //send message to Requester and cancel requestor (if different).
       String su = "Material Request "+getPRNumber()+",line "+getLineItem()+" cancellation request rejected.";
       String m = "Material Request "+getPRNumber()+",line "+getLineItem()+" cancellation request rejected.\n\n";
       if(!BothHelpObjs.isBlankString(reason)){
           m = m + "Rejection Reason:"+reason+"\n\n";
       }
       String needDate = BothHelpObjs.formatDate("toCharfromDB",this.getRequiredDatetime());
       m = m + "This order is for Qty:"+this.getQuantity().toString()+"\tPart Number:"+this.getFacPartNo()+"\tNeed Date:"+needDate+".\n";
       m = m + "\nThis order WILL BE DELIVERED.\n\n";
       m = m + "If you have not previously spoken to a Radian purchaser about this or if you have any questions, please contact your Radian Hub Customer Service Representative.\n";

       String reqEmail = "";
       String cxReqEmail = "";
       q = "select p.email requestor_email,p2.email rqcancel_email from request_line_item b,purchase_request a,personnel p,personnel p2"+
           " where b.pr_number = "+getPRNumber()+" and b.line_item = '"+getLineItem()+"' and"+
           " a.pr_number = b.pr_number and a.requestor = p.personnel_id and b.cancel_requester = p2.personnel_id(+)";
       dbrs = db.doQuery(q);
       rs=dbrs.getResultSet();
       while(rs.next()) {
        reqEmail = BothHelpObjs.makeBlankFromNull(rs.getString("requestor_email"));
        cxReqEmail = BothHelpObjs.makeBlankFromNull(rs.getString("rqcancel_email"));
       }

       String[] sa = null;
       //5-25-01
       if (!BothHelpObjs.isBlankString(reqEmail)) {
        if(reqEmail.equals(cxReqEmail)){
          sa = new String[]{reqEmail};
        }else{
          if (!BothHelpObjs.isBlankString(cxReqEmail)) {
            sa = new String[]{reqEmail,cxReqEmail};
          }else {
            sa = new String[]{reqEmail};
          }
        }
       }else if (!BothHelpObjs.isBlankString(cxReqEmail)) {
        sa = new String[]{cxReqEmail};
       }else {
        sa = new String[]{"trong_ngo@urscorp.com"};
       }
       HelpObjs.sendMail(s,m,sa);

       try {
        String[] args = new String[2];
        args[0] = this.getPRNumber().toString();
        args[1] = this.getLineItem();
        db.doProcedure("p_line_item_allocate",args);
       }catch(Exception dbe) {
        HelpObjs.sendMail(db,"p_line_item_allocate error (pr_number: "+getPRNumber()+" line: "+getLineItem()+")","Error occured while trying to call procedure",86030,false);
       }

     }catch(Exception e){
      e.printStackTrace();
      HelpObjs.sendMail(db,"Reject cancel failed","Error while trying to reject cancellation mr_number: "+getPRNumber()+",line: "+getLineItem(),86030,false);
      throw e;
     }finally {
      dbrs.close();
     }
     return true;
   }


   public static RequestLineItem getRliFromSo(TcmISDBModel db,String so)throws Exception{
     try{
       String query = "select pr_number,line_item from request_line_item where sales_order = '"+so+"'";
       DBResultSet dbrs = null;
      ResultSet rs = null;
       int prNum = 0;
       String line = "";
       dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
       boolean b = false;
       while (rs.next()){
         prNum = rs.getInt(1);
         line = rs.getString("line_item");
         b = true;
         break;
       }

       if(!b)return null;

       RequestLineItem rli = new RequestLineItem(db);
       rli.setPRNumber(prNum);
       rli.setLineItem(line);
       rli.load();
       return rli;
     }catch(Exception e){e.printStackTrace();throw e;
       }
   }

   public PurchaseRequest getPurchaseRequest()throws Exception{
     try{
       PurchaseRequest pr = new PurchaseRequest(db);
       pr.setPRNumber(getPRNumber().intValue());
       pr.load();
       return pr;
     }catch(Exception e){e.printStackTrace();throw e;
       }
   }
}
