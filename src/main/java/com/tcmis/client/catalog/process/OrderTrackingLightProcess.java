package com.tcmis.client.catalog.process;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.catalog.beans.ApplicationObjBean;
import com.tcmis.client.catalog.beans.OrderTrackingInputBean;
import com.tcmis.client.catalog.beans.PkgOrderTrackPrOrderTrackBean;
import com.tcmis.client.catalog.factory.PkgOrderTrackPrOrderTrackBeanFactory;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;
/******************************************************************************
 * Process for OrderTrackingLightProcess
 * @version 1.0
 *****************************************************************************/
public class OrderTrackingLightProcess extends GenericProcess {

  Log log = LogFactory.getLog(this.getClass());

  public OrderTrackingLightProcess(String client) {
    super(client);
  }
  
  public OrderTrackingLightProcess(String client,String locale) {
	    super(client,locale);
  }

  public Collection getsearchResult(OrderTrackingInputBean bean, PersonnelBean personnelBean) throws BaseException,Exception {
	  factory.setBeanObject(new PkgOrderTrackPrOrderTrackBean());
      StringBuilder query = new StringBuilder("WITH stage_order_track_rli AS(");
      query.append("SELECT rli.company_id, rli.facility_id,rli.pr_number, rli.line_item, rli.application, rli.catalog_company_id, rli.ship_to_location_id, ")
      	   .append("rli.required_datetime, rli.po_number, rli.release_number, rli.fac_part_no, rli.release_date, rli.critical, ")
           .append("rli.last_updated, rli.last_updated_by, rli.notes, rli.quantity, rli.delivery_point, rli.delivery_type, rli.delivery_quantity, ")
           .append("rli.cancel_request, rli.cancel_requester, rli.item_type, rli.so_creation_date, rli.catalog_id, rli.example_packaging, ")
           .append("rli.dpas_code, rli.part_group_no, rli.inventory_group, rli.request_line_status, rli.last_shipped, rli.catalog_price, ")
           .append("rli.category_status, rli.archived_qty_shipped, rli.total_quantity_issued, rli.total_quantity_delivered, ")
           .append("fx_cat_part_desc(rli.catalog_company_id, rli.catalog_id, rli.fac_part_no ) part_description,  fi.unit_of_sale,")
           .append("rli.allocate_by_charge_number_1, rli.allocate_by_charge_number_2, rli.allocate_by_charge_number_3, rli.allocate_by_charge_number_4, ")
           .append("bi.item_id best_item_id ")
           .append("  FROM request_line_item rli, catalog_facility cf, issue_key ik, fac_item fi,  ")
           .append("       (SELECT * from(SELECT g.company_id, g.catalog_id, g.cat_part_no, g.part_group_no, g.item_id, g.status, priority, ")
           .append("                             ROW_NUMBER() OVER (PARTITION BY g.company_id, g.catalog_id, g.cat_part_no, g.part_group_no ORDER BY status, priority) AS rownumber ")
           .append(" 					  FROM catalog_part_item_group g WHERE ");
      
      
      if("ITEM_ID".equals(bean.getSearchWhat()))
		  query.append(" item_id = ").append(bean.getSearchText()).append(" AND ");
      
      query.append(" g.status in ('A', 'D', 'R', 'O')) WHERE rownumber = 1");

      query.append(") bi  WHERE  ");
      
      if(!StringUtils.isBlank(bean.getSearchText()) && "PR_NUMBER".equals(bean.getSearchWhat()))
    	  query.append(" rli.pr_number = ").append(bean.getSearchText());
      else if(!StringUtils.isBlank(bean.getSearchText()) && ("CAT_PART_NO".equals(bean.getSearchWhat()) || "ITEM_ID".equals(bean.getSearchWhat()))) {
    	  if(!"My Companies".equals(bean.getCompanyId()))
			  query.append(" rli.company_id = '").append(bean.getCompanyId()).append("'");  // Users have to choose a Company for ITEM_ID
    	  
    	  if(!"My Facilities".equals(bean.getFacilityId()) && !"All".equals(bean.getFacilityId())) {
    		  String facilityGroupId = bean.getFacilityId().substring(0, bean.getFacilityId().indexOf("."));
    		  String facilityId = bean.getFacilityId().substring(bean.getFacilityId().indexOf(".")+1, bean.getFacilityId().length());
    		  if(!StringUtils.isBlank(facilityId)) {
		    	  query.append(" AND rli.facility_id = '").append(facilityId).append("'");
		    	  if(!StringUtils.isBlank(bean.getApplicationId()) && !"My Work Areas".equals(bean.getApplicationId()))
		    	    	  query.append(" AND rli.application = '").append(bean.getApplicationId()).append("'");
		      } else
		    	  query.append(" AND exists (SELECT z.company_id, z.facility_id, z.application FROM ")
		    	  		.append("		(SELECT DISTINCT fla.company_id, fla.facility_id, fla.application ")
		    		  	.append(" 		FROM user_application_expand_view fla ")
		    		  	.append(" 		WHERE (fla.company_id, fla.facility_id) IN ")
		    		  	.append(" 			(SELECT x.company_id, x.facility_id ")
		    		  	.append("      		FROM facility_group_member x ")
		    		  	.append("      		WHERE facility_group_id = '").append(facilityGroupId).append("' AND company_id = '").append(bean.getCompanyId()).append("') ")
		    		  	.append("      		AND fla.personnel_id = ").append(personnelBean.getPersonnelId()).append(")  z ")
                        .append("WHERE z.company_id = rli.company_id AND z.facility_id = rli.facility_id AND z.application = rli.application)  ");
    	  } else if("My Facilities".equals(bean.getFacilityId())) {
    		  query.append(" AND exists (SELECT DISTINCT fla.company_id, fla.facility_id, fla.application ")
		  		.append(" FROM user_application_expand_view fla ")
		  		.append(" WHERE fla.company_id = '").append(bean.getCompanyId()).append("' ")
		  		.append("      AND fla.personnel_id = ").append(personnelBean.getPersonnelId()).append(")");
    	  } else { // All Facilities
    	  }
    	  
    	  if("CAT_PART_NO".equals(bean.getSearchWhat())) {
	    	  if("My Companies".equals(bean.getCompanyId())) 
	    		  query.append(" rli.fac_part_no ");
	    	  else 
	    		  query.append(" AND rli.fac_part_no ");
	    	  
	    	  if("IS".equals(bean.getSearchType()))
	    		  query.append(" = '").append(bean.getSearchText()).append("'");	  
	    	  else if("START_WITH".equals(bean.getSearchType())) 
	    		  query.append(" like '").append(bean.getSearchText()).append("%'");
	      	  else // searchType = "LIKE"
	    		  query.append(" like '%").append(bean.getSearchText()).append("%'");
    	  }
      }
      
      if(!"ITEM_ID".equals(bean.getSearchWhat()) || !"My Companies".equals(bean.getCompanyId())) 
    	  query.append(" AND ");
      
      query.append(" cf.display = 'Y' AND rli.company_id = cf.company_id AND rli.facility_id = cf.facility_id AND rli.catalog_company_id = cf.catalog_company_id ")
           .append(" AND rli.catalog_id = cf.catalog_id AND rli.company_id = ik.company_id(+) AND rli.pr_number = ik.pr_number(+) AND rli.line_item = ik.line_item(+) ")
           .append(" AND rli.catalog_company_id = fi.company_id(+) AND rli.catalog_id = fi.facility_id(+) AND rli.fac_part_no = fi.fac_part_no(+) ");
      
      if("DRAFT".equals(bean.getStatus()))
    	  query.append("   AND rli.category_status IN ('Draft', 'Draft Eval')");
      else if("PENDING".equals(bean.getStatus()))
    	  query.append("   AND rli.category_status = 'Open' AND NVL(rli.archived_qty_shipped, 0) < NVL(rli.quantity, 0) AND rli.sales_order IS NULL ")
               .append("   AND rli.request_line_status IN ('Pending Release', 'Pending Cancellation', 'Pending Use Approval', 'Pending Finance Approval') ");
      else if("OPEN".equals(bean.getStatus()))
    	  query.append("   AND rli.category_status = 'Open' AND NVL(rli.archived_qty_shipped, 0) < NVL(rli.quantity, 0) AND rli.sales_order IS NULL ")
               .append("   AND rli.quantity > NVL(ik.quantity_delivered, 0) AND rli.release_date IS NOT NULL ")
               .append("   AND rli.request_line_status NOT IN ('Pending Release', 'Pending Cancellation', 'Pending Use Approval', 'Pending Finance Approval') ");
      
      query.append("AND bi.cat_part_no = rli.fac_part_no AND bi.company_id = rli.company_id AND bi.catalog_id = rli.catalog_id AND bi.part_group_no = rli.part_group_no");
      
      query.append(") SELECT l.company_id, co.company_name, l.best_item_id, ")
           .append("  l.request_line_status, l.pr_number, l.line_item, l.fac_part_no, l.quantity, ")
           .append("  DECODE(l.release_number, null, l.po_number, l.po_number || '-' || l.release_number) po_number, l.release_number, l.required_datetime, ")
           .append("  l.facility_id, l.application work_area,  w.application_desc, l.catalog_id, l.release_date date_submitted, l.release_date,  l.critical, ")
           .append("  DECODE(l.request_line_status, 'Delivered', l.last_shipped, 'Partial Del.', l.last_shipped, null) last_shipped, ")
           .append("  NVL(NVL(l.total_quantity_delivered, l.archived_qty_shipped), 0) total_shipped, ")
           .append("  NVL(NVL(total_quantity_issued, l.archived_qty_shipped), 0) total_picked, ")
           .append("  DECODE(pr.engineering_evaluation, 'Y', 'EVAL', decode(l.DELIVERY_TYPE, 'Schedule', 'SCH', l.item_type)) item_type, ")
           .append("  DECODE(l.notes, null, null,'REQ:') || l.notes notes, ")
           .append("   (CASE WHEN pr.requestor = -2 or pr.requestor = -1 ")
           .append("      THEN  (SELECT max(cpos.BUYER_NAME_ON_PO) FROM customer_po_stage cpos WHERE cpos.company_id = l.company_id ")
           .append("              AND cpos.customer_po_no = l.po_number ")
           .append("              AND cpos.customer_po_line_no = l.release_number) ")
           .append("      ELSE fx_personnel_id_to_name(pr.requestor) ")
           .append("    END) requestor_name, ")
           .append("  DECODE(pr.requested_finance_approver, null, to_char(null), fx_personnel_id_to_name(pr.requested_finance_approver)) approver_name, ")
           .append(" DECODE(l.cancel_requester, null, to_char(null), fx_personnel_id_to_name(l.cancel_requester)) cancel_requester_name, ")
           .append(" NVL(l.dpas_code, 'None') dpas_code, l.cancel_request, l.cancel_requester, l.part_description, ct.catalog_desc, fc.facility_name, ")
           .append(" NVL(l.category_status, ' ') category_status, l.delivery_type, l.example_packaging packaging, l.ship_to_location_id, loc.location_desc, l.delivery_point, ")
           .append(" pr.requested_finance_approver, pr.requested_releaser, pr.requestor, pr.submitted_date, pr.pr_status, pr.engineering_evaluation, pr.request_id, ")
           .append(" TRIM ('/' FROM TRIM(REPLACE(REPLACE (l.allocate_by_charge_number_1 || ' / ' || l.allocate_by_charge_number_2 || ' / ' || l.allocate_by_charge_number_3 || ' / ' || l.allocate_by_charge_number_4, '/  /','/'),'/  /','/' ))) program_id,")
           .append(" fldp.delivery_point_desc,  l.unit_of_sale uom, l.inventory_group, igd.inventory_group_name ")
           .append("FROM stage_order_track_rli l, purchase_request pr, inventory_group_definition igd, catalog ct, customer.facility# fc, ")
           .append("     fac_loc_app w, location loc, company co, fac_loc_delivery_point fldp ")
           .append("WHERE l.company_id = pr.company_id AND l.pr_number = pr.pr_number AND l.company_id = co.company_id ")
      	   .append("   AND w.company_id  = l.company_id AND w.facility_id  = l.facility_id AND w.application  = l.application ")
           .append("   AND ct.company_id  = l.company_id AND ct.catalog_company_id  = l.catalog_company_id AND ct.catalog_id  = l.catalog_id ")
           .append("   AND fc.company_id  = l.company_id AND fc.facility_id  = l.facility_id ")
           .append("   AND l.company_id = loc.company_id(+) AND l.ship_to_location_id = loc.location_id(+) ")
           .append("   AND l.company_id = fldp.company_id(+) AND l.facility_id = fldp.facility_id(+) AND l.ship_to_location_id = fldp.location_id(+) AND l.delivery_point = fldp.delivery_point(+) ")
           .append("   AND l.inventory_group = igd.inventory_group(+) ");
      
      query.append("ORDER BY l.pr_number, l.line_item ");
      
      return factory.selectQuery(query.toString());
  }
  
  
  // TODO: The improvement for Excel is on hold because probably no one will need this functionality.
  public Collection getsearchResultForExcel(OrderTrackingInputBean bean, PersonnelBean personnelBean) throws BaseException,Exception {
      DbManager dbManager = new DbManager(getClient(),getLocale());
      PkgOrderTrackPrOrderTrackBeanFactory factory = new PkgOrderTrackPrOrderTrackBeanFactory(dbManager);
      String procedure = "";
 /*     
      if(personnelBean.getCompanyId().equals("Radian"))
    	  procedure = "PKG_ORDER_TRACKING.PR_ORDER_TRACK";
      else
    	  procedure = "PKG_ORDER_TRACK_TEST.PR_ORDER_TRACK";
  */    
      Boolean isOrderPRDescend = personnelBean.isFeatureReleased("OrderTrackingOrderBy", "ALL", personnelBean.getCompanyId());
      
      return factory.select(bean,procedure, isOrderPRDescend);
  }

  public ExcelHandler getExcelReport(OrderTrackingInputBean bean, PersonnelBean personnel) throws NoDataException, BaseException, Exception {
   ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", personnel.getLocale());
   Collection<PkgOrderTrackPrOrderTrackBean> data = this.getsearchResultForExcel(bean, personnel);

//   Iterator iterator = data.iterator();
   ExcelHandler pw = new ExcelHandler(library);
	  
	  pw.addTable();

//	  write column headers
   if ("Y".equalsIgnoreCase(bean.getNeedMyApproval())) {
     pw.addRow();
     pw.addCellKeyBold("ordertracking.label.needmyapproval");
     
   }else {
     pw.addRow();
     pw.addCellKeyBold("label.company");
     pw.addCell(bean.getCompanyName());
     
     pw.addRow();
     pw.addCellKeyBold("label.facility");
     pw.addCell(bean.getFacilityName());
     
     pw.addRow();
     pw.addCellKeyBold("label.workarea");
     pw.addCell(bean.getApplicationDesc());
     
     pw.addRow();
     pw.addCellKeyBold("label.requestor");
     pw.addCell(bean.getRequestorName());
     
     pw.addRow();
     pw.addCellKeyBold("label.status");
     if ("ANY".equalsIgnoreCase(bean.getStatus())) {
       pw.addCell(library.getString("label.any"));
	   }else if ("DRAFT".equalsIgnoreCase(bean.getStatus())) {
       pw.addCell(library.getString("label.draft"));
     }else if ("PENDING".equalsIgnoreCase(bean.getStatus())) {
       pw.addCell(library.getString("label.pending"));
     }else if ("OPEN".equalsIgnoreCase(bean.getStatus())) {
       pw.addCell(library.getString("label.open"));
     }else if ("RELEASED".equalsIgnoreCase(bean.getStatus())) {
	    pw.addCell(library.getString("ordertracking.label.released") +" "+StringHandler.emptyIfNull(bean.getReleasedSinceDays())+" "+library.getString("label.days"));
     }else if ("DELIVERED".equalsIgnoreCase(bean.getStatus())) {
       pw.addCell(library.getString("ordertracking.label.fullydelivered") +" "+StringHandler.emptyIfNull(bean.getDeliveredSinceDays())+" "+library.getString("label.days"));
     }else if ("CANCELED".equalsIgnoreCase(bean.getStatus())) {
	     pw.addCell(library.getString("ordertracking.label.cancelled") +" "+StringHandler.emptyIfNull(bean.getCancelledSinceDays())+" "+library.getString("label.days"));
     }
     
     pw.addRow();
     pw.addCellKeyBold("label.searchby");
     pw.addCell(bean.getSearchWhatDesc());
     pw.addCell(bean.getSearchTypeDesc());
     pw.addCell(bean.getSearchText());
     
     if ("Y".equalsIgnoreCase(bean.getCritical())) {
       pw.addRow();
       pw.addCellKeyBold("ordertracking.label.criticalonly");
       pw.addCell(bean.getCritical());
       
     }
   }
   //blank row
   pw.addRow();

   //result table
   //write column headers
   pw.addRow();

   boolean isDisplayItemId = personnel.isFeatureReleasedForMyFacilities("OTAllowSearchByItem",bean.getCompanyId());
   boolean isDisplayChangeNoOwnerSegment = personnel.isFeatureReleased("DisplayChargeNoOwnerSeqment", "ALL",bean.getCompanyId());
   // boolean isShowQtyInCustomerUom = personnel.isFeatureReleased("ShowQtyInCustomerUom", bean.getFacilityId());
   boolean isShowQtyInCustomerUom = false;
   
   ArrayList<String> hk = new ArrayList<String>();
   hk.add("label.status");
   hk.add("label.customerpo");
   if(isDisplayChangeNoOwnerSegment)
	   hk.add("label.program");
   hk.add("label.requestor");
   hk.add("label.company");
   hk.add("label.facility");
   hk.add("label.workarea");
   hk.add("label.catalog");
   hk.add("label.partnum");
   hk.add("label.partdesc");
   hk.add("label.packaging");
   hk.add("label.type");
   hk.add("label.mrline");
   hk.add("label.notes");
   hk.add("ordertracking.label.released");
   hk.add("label.needed");
   hk.add("label.picked");
   hk.add("label.orderedqty");
   hk.add("label.allocatedqty");
   hk.add("mrallocationreport.label.ref");
   hk.add("label.projecteddelivery");
   hk.add("label.delivered");
   if(isShowQtyInCustomerUom)
   {
	   hk.add("label.pickedinuom");
	   hk.add("label.deliveredinuom");
	   hk.add("label.uom");
   }   
   hk.add("label.lastdelivered");
   hk.add("label.shipto");
   hk.add("label.deliverto");
   hk.add("label.critical");
   hk.add("label.approver");
   if(isDisplayItemId)
	   hk.add("label.itemid");
   
	String [] headerkeys = new String[hk.size()];
	headerkeys = hk.toArray(headerkeys);
	
	ArrayList<Integer> t = new ArrayList<Integer>();
	t.add(0);
	t.add(0);
	if(isDisplayChangeNoOwnerSegment)
		t.add(0);
	t.add(0);
	t.add(0);
	t.add(0);
	t.add(0);
	t.add(0);
	t.add(0);
	t.add(pw.TYPE_PARAGRAPH);
	t.add(pw.TYPE_PARAGRAPH);
	t.add(0);
	t.add(0);
	t.add(pw.TYPE_PARAGRAPH);
	t.add(pw.TYPE_CALENDAR);
	t.add(pw.TYPE_CALENDAR);
	t.add(0);
	t.add(0);
	t.add(0);
	t.add(0);
	t.add(pw.TYPE_CALENDAR);
	t.add(0);
	if(isShowQtyInCustomerUom)
	{
		t.add(0);
		t.add(0);
		t.add(0);
	}   
	t.add(pw.TYPE_CALENDAR);
	t.add(0);
	t.add(0);
	t.add(0);
	t.add(0);
	
	int[] types = new int[t.size()];
	types = ArrayUtils.toPrimitive(t.toArray(new Integer[t.size()]));
	
	ArrayList<Integer> w = new ArrayList<Integer>();
	w.add(15);
	w.add(20);
	if(isDisplayChangeNoOwnerSegment)
		w.add(20);
	w.add(20);
	w.add(25);
	w.add(25);
	w.add(36);
	w.add(22);
	w.add(22);
	w.add(0);
	w.add(0);
	w.add(0);
	w.add(0);
	w.add(0);
	w.add(0);
	w.add(0);
	w.add(0);
	w.add(0);
	w.add(0);
	w.add(10);
	w.add(22);
	w.add(0);
	w.add(10);
	w.add(22);
	w.add(8);
	w.add(0);
	w.add(15);

	int[] widths = new int[w.size()];
	widths = ArrayUtils.toPrimitive(w.toArray(new Integer[w.size()]));
	
	ArrayList<Integer> ha = new ArrayList<Integer>();
	ha.add(pw.ALIGN_CENTER);
	ha.add(0);
	if(isDisplayChangeNoOwnerSegment)
		ha.add(0);
	ha.add(0);
	ha.add(pw.ALIGN_CENTER);
	ha.add(pw.ALIGN_CENTER);
	ha.add(0);
	ha.add(0);
	ha.add(0);
	ha.add(0);
	ha.add(0);
	ha.add(0);
	ha.add(0);
	ha.add(0);
	ha.add(0);
	ha.add(0);
	ha.add(0);
	ha.add(0);
	ha.add(0);
	ha.add(0);
	ha.add(0);
	ha.add(0);
	if(isShowQtyInCustomerUom)
	{
		ha.add(0);
		ha.add(0);
		ha.add(0);
	}  
	ha.add(0);
	ha.add(0);
	ha.add(0);
	ha.add(0);
	ha.add(0);

	int[] horizAligns  = new int[ha.size()];
	horizAligns = ArrayUtils.toPrimitive(ha.toArray(new Integer[ha.size()]));
	
	pw.applyColumnHeader(headerkeys, types, widths, horizAligns);
	String of = library.getString("label.of");
   //now write data
	for (PkgOrderTrackPrOrderTrackBean member : data) {
		 pw.addRow();
		 pw.addCell(member.getRequestLineStatus());
		 pw.addCell(member.getPoNumber());
		if(isDisplayChangeNoOwnerSegment)
			 pw.addCell(member.getProgramId());
		 pw.addCell(member.getRequestorName());
		 pw.addCell(member.getCompanyName());
		 pw.addCell(member.getFacilityName());
		 pw.addCell(member.getApplicationDesc());
		 pw.addCell(member.getCatalogDesc());
		 pw.addCell(member.getFacPartNo());
		 pw.addCell(member.getPartDescription());
		 pw.addCell(member.getPackaging());
		 pw.addCell(member.getItemType());
		 pw.addCell(member.getPrNumber() + "-" + member.getLineItem());
		 pw.addCell(member.getNotes());
		 pw.addCell(member.getReleaseDate());
		 pw.addCell(member.getRequiredDatetime());
		 pw.addCell(member.getTotalPicked() + " " + of + " " + member.getQuantity());
		 pw.addCell(member.getQuantity());
		 pw.addCell(member.getAllocatedQuantity());
		 if (member.getAllocationRef() != null) {
		 	pw.addCell(member.getAllocationStatus()+" ("+member.getAllocationRef()+")");
		 }else {
			pw.addCell(member.getAllocationStatus());
		 }
		 pw.addCell(member.getAllocationReferenceDate());
		 pw.addCell(member.getTotalShipped());
		if(isShowQtyInCustomerUom)
		{
			 if(member.getTotalUosPicked() == null)
				pw.addCell("");
			 else
				 pw.addCell(member.getTotalUosPicked() + " " + of + " " + member.getTotalQuantity());
			 pw.addCell(member.getTotalUosShipped());
			 pw.addCell(member.getUnitOfSale());
		}
		 pw.addCell(member.getLastShipped());
		 pw.addCell(member.getLocationDesc());
		 pw.addCell(member.getDeliveryPointDesc());
		 if("n".equalsIgnoreCase(member.getCritical())) {
		 	pw.addCell(library.getString("label.no"));
		 }else {
			pw.addCell(library.getString("label.yes"));
		 }
		 pw.addCell(member.getApproverName());
		 if(isDisplayItemId)
			 pw.addCell(member.getBestItemId());
	}
   return pw;
 } //end of method
  
  
  public Collection getApplicationColl(String companyId, String facilityId, int userId) throws BaseException,Exception {
	    factory.setBeanObject(new ApplicationObjBean());
		StringBuilder 	query = new StringBuilder("SELECT DISTINCT fl.application,  SUBSTR (fx_fac_loc_app_desc (fl.company_id,  fl.facility_id, fl.application), 1, 50)  application_desc ")
		    						.append(" FROM customer.fac_loc_app fl,  customer.facility f, customer.user_application_expand_view ua ")
		    						.append(" WHERE  fl.company_id = '").append(companyId)
		    						.append("'    AND fl.facility_id = '").append(facilityId)
		    						.append("'    AND fl.company_id = ua.company_id ")
		    						.append("    AND fl.facility_id = ua.facility_id ")
		    						.append("    AND fl.application = ua.application ")
		    						.append("    AND ua.personnel_id = ").append(userId)
		    						.append("    AND fl.company_id = f.company_id ")
		    						.append("    AND fl.facility_id = f.facility_id ")
		    						.append("    AND f.active = 'y' order by application_desc");
		    		
	    return factory.selectQuery(query.toString());
    } //end of method

  public void truncateMRs(PersonnelBean user, Collection orders) {
	  Iterator itr = orders.iterator();
	  
	  while(itr.hasNext()) {
		  PkgOrderTrackPrOrderTrackBean order = (PkgOrderTrackPrOrderTrackBean) itr.next();

          try {
        	  Vector inArgs = new Vector(2);
              inArgs.add(order.getPrNumber());
              inArgs.add(order.getLineItem());
              
        	  factory.doProcedure("P_LINE_FINISH", inArgs);
        	  
        	  log.info(user.getPersonnelId() + " truncated " + order.getPrNumber() + "-" + order.getLineItem());
          }
          catch(Exception e) {
        	  log.error("Unable to truncate " + order.getPrNumber() + "-" + order.getLineItem());
        	  log.error(e);
          }
	  }
  }
}

