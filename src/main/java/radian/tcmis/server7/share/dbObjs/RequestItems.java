
package radian.tcmis.server7.share.dbObjs;

import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.server7.share.db.*;


import java.util.*;
import java.sql.*;

public class RequestItems {

   protected TcmISDBModel db;
   protected Integer pr_number;
   protected LineItemView RLI;
   protected Vector items = new Vector();
	protected String useApprovalType;

	public RequestItems()  throws java.rmi.RemoteException {

   }

   public RequestItems(int pr)  throws java.rmi.RemoteException {
     this.pr_number = new Integer(pr);
   }

   public RequestItems(TcmISDBModel db)  throws java.rmi.RemoteException {
      this.db = db;
   }

   public RequestItems(int pr,TcmISDBModel db)  throws java.rmi.RemoteException {
     this.db = db;
     this.pr_number = new Integer(pr);
   }

   public void setDB(TcmISDBModel db){
     this.db = db;
   }

   public void setPRNumber(int pr) {
     this.pr_number = new Integer(pr);
   }

   public Integer getPRNumber() {
     return pr_number;
   }

	public void setUseApprovalType(String s) {
		this.useApprovalType = s;
	}

  public Vector getItemsForUseApprover(Hashtable header, int userID, int rNum)  throws Exception {
	  String  query = "";
	  if ("useLimit".equalsIgnoreCase(useApprovalType)) {
	  	query = "select a.*," +
				  "nvl(a.quantity,0)*nvl(a.unit_price,0) extended_price from line_item_view a,over_limit_use_approver b"+
     			  " where a.application = decode(b.application,'All',a.application,b.application) and a.facility_id = b.facility_id and"+
    			  " b.personnel_id = "+userID+ " and"+
		  		  " a.use_approval_status = 'pending' and"+
     			  " a.pr_number = "+rNum;
	  }else {
		query = "select a.*," +
			     "nvl(a.quantity,0)*nvl(a.unit_price,0) extended_price from line_item_view a, rli_facility_list_release rflr, facility_list_approver fla"+
			     " where a.pr_number = "+rNum+" and a.list_approval_status = 'Pending' and a.pr_number = rflr.pr_number and a.line_item = rflr.line_item"+
				  " and a.list_approval_status = rflr.list_approval_status and a.facility_id = fla.facility_id"+
			     " and rflr.list_id = fla.list_id and fla.personnel_id = "+userID;
	  }
	  query += " order by to_number(a.line_item)";
     DBResultSet dbrs = null;
     ResultSet rs = null;
     if (items.size() > 0) {
       items.removeAllElements();
     }
     Vector lineItemWaitingForApprovalV = new Vector();
     try {
        dbrs = db.doQuery(query);
        rs=dbrs.getResultSet();
        int lastLineItem = 0;
        int line = 1;
        float approvedDollarLimit = 0;
        while (rs.next()) {
           //case of kits
           if((int)rs.getInt("LINE_ITEM") == lastLineItem){
             continue;
           }
           lastLineItem = rs.getInt("LINE_ITEM");
           lineItemWaitingForApprovalV.addElement(rs.getString("LINE_ITEM"));
           approvedDollarLimit += rs.getFloat("extended_price");

           RLI = new LineItemView(db);
           RLI.setPRNumber((int)rs.getInt("PR_NUMBER"));
           RLI.setLineItem(new Integer(line).toString());      //***** the reason for this is because the line need my approval might not be in sequence (i.e 1,3,4)
           line++;
           RLI.setApplication(rs.getString("APPLICATION"));
           RLI.setQuantity(rs.getBigDecimal("QUANTITY"));
           RLI.setItemId((int)rs.getInt("ITEM_ID"));
           RLI.setFacPartNo(rs.getString("FAC_PART_NO"));
           RLI.setMaterial(rs.getString("MATERIAL"));
           RLI.setMfg(rs.getString("MFG"));
           RLI.setGrade(rs.getString("GRADE"));
           RLI.setPkgStyle(rs.getString("PKG_STYLE"));
           RLI.setPartSize((float)rs.getFloat("PART_SIZE"));
           RLI.setSizeUnit(rs.getString("SIZE_UNIT"));
           RLI.setShelfLife((float)rs.getFloat("SHELF_LIFE"));
           RLI.setShelfLifeUnit(rs.getString("SHELF_LIFE_UNIT"));
           RLI.setRequiredDatetime(rs.getString ("REQUIRED_DATETIME"));
           RLI.setReleaseNumber(rs.getString("RELEASE_NUMBER"));
           RLI.setPO(rs.getString("PO_NUMBER"));
           RLI.setScrap(rs.getString("SCRAP"));
           RLI.setExampleItemId(rs.getString("EXAMPLE_ITEM_ID"));
           RLI.setCritical(rs.getString("CRITICAL"));
           RLI.setNotes(rs.getString("NOTES"));
           RLI.setDPAS(BothHelpObjs.makeBlankFromNull(rs.getString("dpas_code")));

           RLI.setUseApprovalDate(BothHelpObjs.makeBlankFromNull(rs.getString("use_approval_date")));
           RLI.setUseApprover(BothHelpObjs.makeBlankFromNull(rs.getString("use_approver")));
           RLI.setUseApprovalComment(BothHelpObjs.makeBlankFromNull(rs.getString("use_approval_comment")));
           RLI.setUseApprovalStatus(BothHelpObjs.makeBlankFromNull(rs.getString("use_approval_status")));

           RLI.setShipToLocationId(rs.getString("ship_to_location_id"));
           RLI.setDeliveryPoint(rs.getString("delivery_point"));
           RLI.setDeliveryType(rs.getString("delivery_type"));
           RLI.setDeliveryQty(rs.getString("delivery_quantity"));
           RLI.setDeliveryFrequency(rs.getString("delivery_frequency"));
           RLI.setChargeType(rs.getString("charge_type"));

           //trong 4/10
           RLI.setDocNum(BothHelpObjs.makeBlankFromNull(rs.getString("doc_num")));
           RLI.setDocState(BothHelpObjs.makeBlankFromNull(rs.getString("doc_state")));

           if(rs.getString("relax_shelf_life")==null){
             RLI.setRelaxShelfLife("");
           }else{
             RLI.setRelaxShelfLife(rs.getString("relax_shelf_life"));
           }

           RLI.setApplicationDisplay(rs.getString("application_display"));
           RLI.setCancelStatus(BothHelpObjs.makeBlankFromNull(rs.getString("cancel_status")));
           RLI.setUnitPrice(BothHelpObjs.makeBlankFromNull(rs.getString("unit_price")));
           RLI.setItemType(BothHelpObjs.makeBlankFromNull(rs.getString("item_type")));
           RLI.setFacilityID(BothHelpObjs.makeBlankFromNull(rs.getString("facility_id")));
           RLI.setCatalogID(BothHelpObjs.makeBlankFromNull(rs.getString("catalog_id")));    //1-13-03
           RLI.setPartGroupNo(BothHelpObjs.makeBlankFromNull(rs.getString("part_group_no")));    //1-20-03;
           RLI.setInventoryGroup(BothHelpObjs.makeBlankFromNull(rs.getString("inventory_group")));      //1-20-03
           RLI.setCurrencyID(BothHelpObjs.makeBlankFromNull(rs.getString("currency_id")));
           RLI.setCatalogPrice(BothHelpObjs.makeBlankFromNull(rs.getString("catalog_price")));
           RLI.setOrderQuantityRule(BothHelpObjs.makeBlankFromNull(rs.getString("order_quantity_rule")));
           RLI.setCustomerRequisitionNumber(BothHelpObjs.makeBlankFromNull(rs.getString("customer_requisition_number")));
           RLI.setCurrentCatalogPrice(BothHelpObjs.makeBlankFromNull(rs.getString("current_catalog_price")));
           RLI.setCurrentCurrencyId(BothHelpObjs.makeBlankFromNull(rs.getString("current_currency_id")));
           RLI.setTotalQtyIssued(rs.getBigDecimal("total_quantity_issued"));
           RLI.setTotalInvoicePrepQty(rs.getBigDecimal("total_quantity_invoice_prep"));
           RLI.setTotalQtyAllocated(rs.getBigDecimal("total_quantity_allocated"));
           RLI.setTotalQtyDelivered(rs.getBigDecimal("total_quantity_delivered"));
           RLI.setUnitOfSale(BothHelpObjs.makeBlankFromNull(rs.getString("unit_of_sale")));
           RLI.setUnitOfSaleQuantityPerEach(BothHelpObjs.makeBlankFromNull(rs.getString("unit_of_sale_quantity_per_each")));
           RLI.setUnitOfSalePrice(BothHelpObjs.makeBlankFromNull(rs.getString("unit_of_sale_price")));
           RLI.setAuditUnitOfSale(BothHelpObjs.makeBlankFromNull(rs.getString("audit_unit_of_sale")));
           RLI.setAuditUnitOfSaleQuantityPerEach(BothHelpObjs.makeBlankFromNull(rs.getString("audit_uos_quantity_per_each")));
           RLI.setPartDescription(BothHelpObjs.makeBlankFromNull(rs.getString("part_description")));
           RLI.setPoQty(BothHelpObjs.makeBlankFromNull(rs.getString("po_qty")));
           RLI.setItemDisplayPkgStyle(BothHelpObjs.makeBlankFromNull(rs.getString("item_display_pkg_style")));
           RLI.setRequestedItemType(BothHelpObjs.makeBlankFromNull(rs.getString("requested_item_type")));
			  RLI.setListApprovalStatus(BothHelpObjs.makeBlankFromNull(rs.getString("list_approval_status")));
			  RLI.setCatalogCompanyId(BothHelpObjs.makeBlankFromNull(rs.getString("catalog_company_id")));
			  RLI.setCompanyId(BothHelpObjs.makeBlankFromNull(rs.getString("company_id")));

			  items.addElement(RLI);
        }
        lineItemWaitingForApprovalV.trimToSize();
        header.put("LINE_ITEM_WAITING_FOR_APPROVAL",lineItemWaitingForApprovalV);
        header.put("APPROVED_DOLLAR_LIMIT",new Float(approvedDollarLimit));
        return items;
     }catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
       throw e;
     } finally{
       dbrs.close();
     }
   } //end of method

   public Vector getItems()  throws Exception {
     String query = "select * from line_item_view where pr_number = " + this.pr_number.toString()+
     					  " order by to_number(line_item)";
     DBResultSet dbrs = null;
      ResultSet rs = null;
     if (items.size() > 0) {
       items.removeAllElements();
     }
     try {
        dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
        int lastLineItem = 0;
        while (rs.next()) {
           //case of kits
           if((int)rs.getInt("LINE_ITEM") == lastLineItem){
             continue;
           }
           lastLineItem = rs.getInt("LINE_ITEM");

           RLI = new LineItemView(db);
           RLI.setPRNumber((int)rs.getInt("PR_NUMBER"));
           RLI.setLineItem(rs.getString("LINE_ITEM"));
           RLI.setApplication(rs.getString("APPLICATION"));
           RLI.setQuantity(rs.getBigDecimal("QUANTITY"));
           RLI.setItemId((int)rs.getInt("ITEM_ID"));
           RLI.setFacPartNo(rs.getString("FAC_PART_NO"));
           RLI.setMaterial(rs.getString("MATERIAL"));
           RLI.setMfg(rs.getString("MFG"));
           RLI.setGrade(rs.getString("GRADE"));
           RLI.setPkgStyle(rs.getString("PKG_STYLE"));
           RLI.setPartSize((float)rs.getFloat("PART_SIZE"));
           RLI.setSizeUnit(rs.getString("SIZE_UNIT"));
           RLI.setShelfLife((float)rs.getFloat("SHELF_LIFE"));
           RLI.setShelfLifeUnit(rs.getString("SHELF_LIFE_UNIT"));
           RLI.setRequiredDatetime(rs.getString ("REQUIRED_DATETIME"));
           RLI.setReleaseNumber(rs.getString("RELEASE_NUMBER"));
           RLI.setPO(rs.getString("PO_NUMBER"));
           RLI.setScrap(rs.getString("SCRAP"));
           RLI.setExampleItemId(rs.getString("EXAMPLE_ITEM_ID"));
           RLI.setCritical(rs.getString("CRITICAL"));
           RLI.setNotes(rs.getString("NOTES"));
           RLI.setDPAS(BothHelpObjs.makeBlankFromNull(rs.getString("dpas_code")));

           RLI.setUseApprovalDate(BothHelpObjs.makeBlankFromNull(rs.getString("use_approval_date")));
           RLI.setUseApprover(BothHelpObjs.makeBlankFromNull(rs.getString("use_approver")));
           RLI.setUseApprovalComment(BothHelpObjs.makeBlankFromNull(rs.getString("use_approval_comment")));
           RLI.setUseApprovalStatus(BothHelpObjs.makeBlankFromNull(rs.getString("use_approval_status")));

           RLI.setShipToLocationId(rs.getString("ship_to_location_id"));
           RLI.setDeliveryPoint(rs.getString("delivery_point"));
           RLI.setDeliveryType(rs.getString("delivery_type"));
           RLI.setDeliveryQty(rs.getString("delivery_quantity"));
           RLI.setDeliveryFrequency(rs.getString("delivery_frequency"));
           RLI.setChargeType(rs.getString("charge_type"));

           RLI.setDocNum(BothHelpObjs.makeBlankFromNull(rs.getString("doc_num")));
           RLI.setDocState(BothHelpObjs.makeBlankFromNull(rs.getString("doc_state")));

           if(rs.getString("relax_shelf_life")==null){
             RLI.setRelaxShelfLife("");
           }else{
             RLI.setRelaxShelfLife(rs.getString("relax_shelf_life"));
           }

           RLI.setApplicationDisplay(rs.getString("application_display"));
           RLI.setCancelStatus(BothHelpObjs.makeBlankFromNull(rs.getString("cancel_status")));
           RLI.setUnitPrice(BothHelpObjs.makeBlankFromNull(rs.getString("unit_price")));
           RLI.setItemType(BothHelpObjs.makeBlankFromNull(rs.getString("item_type")));
           RLI.setFacilityID(BothHelpObjs.makeBlankFromNull(rs.getString("facility_id")));
           RLI.setCatalogID(BothHelpObjs.makeBlankFromNull(rs.getString("catalog_id")));
           RLI.setPartGroupNo(BothHelpObjs.makeBlankFromNull(rs.getString("part_group_no")));
           RLI.setInventoryGroup(BothHelpObjs.makeBlankFromNull(rs.getString("inventory_group")));
           RLI.setCurrencyID(BothHelpObjs.makeBlankFromNull(rs.getString("currency_id")));
           RLI.setCatalogPrice(BothHelpObjs.makeBlankFromNull(rs.getString("catalog_price")));
           RLI.setOrderQuantityRule(BothHelpObjs.makeBlankFromNull(rs.getString("order_quantity_rule")));
           RLI.setCustomerRequisitionNumber(BothHelpObjs.makeBlankFromNull(rs.getString("customer_requisition_number")));
           RLI.setCurrentCatalogPrice(BothHelpObjs.makeBlankFromNull(rs.getString("current_catalog_price")));
           RLI.setCurrentCurrencyId(BothHelpObjs.makeBlankFromNull(rs.getString("current_currency_id")));
           RLI.setTotalQtyIssued(rs.getBigDecimal("total_quantity_issued"));
           RLI.setTotalInvoicePrepQty(rs.getBigDecimal("total_quantity_invoice_prep"));
           RLI.setTotalQtyAllocated(rs.getBigDecimal("total_quantity_allocated"));
           RLI.setTotalQtyDelivered(rs.getBigDecimal("total_quantity_delivered"));
           RLI.setUnitOfSale(BothHelpObjs.makeBlankFromNull(rs.getString("unit_of_sale")));
           RLI.setUnitOfSaleQuantityPerEach(BothHelpObjs.makeBlankFromNull(rs.getString("unit_of_sale_quantity_per_each")));
           RLI.setUnitOfSalePrice(BothHelpObjs.makeBlankFromNull(rs.getString("unit_of_sale_price")));
           RLI.setAuditUnitOfSale(BothHelpObjs.makeBlankFromNull(rs.getString("audit_unit_of_sale")));
           RLI.setAuditUnitOfSaleQuantityPerEach(BothHelpObjs.makeBlankFromNull(rs.getString("audit_uos_quantity_per_each")));
           RLI.setPartDescription(BothHelpObjs.makeBlankFromNull(rs.getString("part_description")));
           RLI.setPoQty(BothHelpObjs.makeBlankFromNull(rs.getString("po_qty")));
           RLI.setItemDisplayPkgStyle(BothHelpObjs.makeBlankFromNull(rs.getString("item_display_pkg_style")));
           RLI.setRequestedItemType(BothHelpObjs.makeBlankFromNull(rs.getString("requested_item_type")));
			  RLI.setListApprovalStatus(BothHelpObjs.makeBlankFromNull(rs.getString("list_approval_status")));
			  RLI.setCatalogCompanyId(BothHelpObjs.makeBlankFromNull(rs.getString("catalog_company_id")));
			  RLI.setCompanyId(BothHelpObjs.makeBlankFromNull(rs.getString("company_id")));

			  items.addElement(RLI);
        }
        return items;
     }catch (Exception e) {
       e.printStackTrace(); HelpObjs.monitor(1,"Error object("+this.getClass().getName()+"): " + e.getMessage(),null);
       throw e;
       } finally{
             dbrs.close();

     }
   }  //end of method
}   //end of class