package com.tcmis.internal.distribution.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;
import java.sql.Connection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.beans.PermissionBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.ConnectionPool;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.distribution.beans.AllocationDetailBean;
import com.tcmis.internal.distribution.beans.AllocationInputBean;
import com.tcmis.internal.distribution.beans.CustomerAddRequestViewBean;
import com.tcmis.internal.distribution.factory.CustomerAddRequestBeanFactory;
import com.tcmis.internal.hub.beans.ItemCountInventoryViewBean;
import com.tcmis.internal.hub.beans.LogisticsInputBean;
import com.tcmis.internal.hub.factory.ItemCountInventoryViewBeanFactory;

   /******************************************************************************
   * Process for CustomerAddressSearchView
   * @version 1.0
   *****************************************************************************/
  public class AllocationDetailProcess
  		extends GenericProcess {
	  Log log = LogFactory.getLog(this.getClass());
	  private static String RESOURCE_BUNDLE= "com.tcmis.common.resources.CommonResources";
	  private ResourceLibrary library = new ResourceLibrary(RESOURCE_BUNDLE, getLocaleObject());
	  
   public AllocationDetailProcess(TcmISBaseAction act) throws BaseException{
	    super(act);
   }
   
   public AllocationDetailProcess(String client,String locale) {
	    super(client,locale);
   }
   
   public Collection getSearchResult(AllocationInputBean in,PersonnelBean pb) throws BaseException {
  	SearchCriteria criteria = new SearchCriteria();

//	a_company_id customer.facility.company_id%type,
//	a_facility_id customer.facility.facility_id%type,
//	a_item_id item.item_id%type,
//	a_ref_inventory_group inventory_group_definition.inventory_group%type,
//	a_personnel_id personnel.personnel_id%type,
//	a_search_inventory_group inventory_group_definition.inventory_group%type default null,
//	a_spec_list catalog_item_spec.spec_list%type default null,

//    in.setCompanyId("RAYTHEON");
//    in.setFacilityId("McKinney");
//    in.setItemId(new BigDecimal("123457"));
//    in.setRefInventoryGroup("Dallas");
//	  in.setpersonnelid();
//    in.setInventoryGroup("Dallas");
//    in.setSpecList(null);
//sample query select * from table (pkg_sales_search.fx_allocation_detail('RAYTHEON','McKinney',123457,'Dallas',86405,'Dallas',null,null,null)) order by spec_list,decode(source,'Receipt', 1,'PO', 2,'Buy Order', 3,4),reference,pr_number nulls first        

	 String specList = in.getSpecList();
    String specDetailList = in.getSpecDetailList();
    String specLibraryList = in.getSpecLibraryList();
    String specCocList = in.getSpecCocList();
    String specCoaList = in.getSpecCoaList();
    if ("No Specification".equalsIgnoreCase(specList))
    {
        specList = "";
        specDetailList = "";
        specLibraryList = "";
        specCocList = "";
        specCoaList = "";
    }
    String scrap = StringHandler.emptyIfNull(in.getScrap());
    if (scrap ==null || scrap.length() ==0)
    {
      scrap = "n";
    }
    String qs = "select * from table (pkg_sales_search.fx_allocation_detail({0},{1},{2},{3},{4},{5},{6},{7},{8},{9},{10},{11},{12},{13},{14},{15},{16},{17},{18},{19},{20})) order by spec_full_list,decode(source,''Receipt'', 1,''PO'', 2,''Buy Order'', 3,4),reference,pr_number nulls first";
    String key="INVENTORY GROUP";
    if( "REGION".equals(in.getSearchKey()))
    	key = "REGION";
    if( "GLOBAL".equals(in.getSearchKey()))
    	key = "";
	return	this.getSearchResult(qs, new AllocationDetailBean(),
		    in.getCompanyId(),
	        in.getFacilityId(),
	        in.getItemId(),
	        in.getInventoryGroup(),
	        new BigDecimal(pb.getPersonnelId()),
	        in.getPriceGroupId(),
	        key,//in.getInventoryGroup(),
	        specList,
            specLibraryList,
            specDetailList,
	        specCocList,
	        specCoaList,
	        null,
	        in.getCurrencyId(),
	        in.getUnitOfMeasure(),
		 	in.getOrderPrNumber(),
		 	in.getScratchPadLineItem(),
            scrap,
            null,
            null,
            in.getPromisedDate()
              );
   }
   
   public Object[] showResult(AllocationInputBean inputBean,PersonnelBean pb) throws BaseException {
		Vector bv = (Vector) this.getSearchResult(inputBean,pb);
		HashMap m1 = new HashMap();
		Integer i1 = null;
		String key = null;

		AllocationDetailBean bean = null;
		for (int i = 0; i < bv.size(); i++) {
			bean = (AllocationDetailBean) bv.get(i);
			key = bean.getSpecDetailList();
			if( isBlank(key) )
				key="";
			key += "key";
			
			if (m1.get(key) == null) {
				i1 = new Integer(0);
				m1.put(key, i1);
			}
			i1 = (Integer) m1.get(key);
			i1 = new Integer(i1.intValue() + 1);
			m1.put(key, i1);
		}
		Object[] objs = {bv,m1};
		return objs;
	}
     
   public String deleteStage(Connection connection) {
	   return getProcError(connection,null,new Vector(),"Pkg_rli_sales_order.P_DELETE_STAGE_CR_ALLOC_RLI");
   }
   
   public String processForceBuyData(Collection<AllocationDetailBean> beans, AllocationInputBean in,BigDecimal personnelId, PermissionBean perbean,PersonnelBean personnelBean) throws
	 BaseException {
	   DbManager dbManager = new DbManager(getClient(),getLocale());
	   ItemCountInventoryViewBeanFactory factory = new
	   ItemCountInventoryViewBeanFactory(dbManager);
	   String errorMessage = null;
	   PermissionBean permissionBean = personnelBean.getPermissionBean();
	   Collection results;
	   Iterator resultsIter;
	   ItemCountInventoryViewBean tmpBean = new ItemCountInventoryViewBean();
	   BeanHandler.copyAttributes(in, tmpBean);
	   if(((AllocationDetailBean)((Vector)beans).firstElement()).getNeedType().equalsIgnoreCase("IT"))
		   tmpBean.setMrLineItem("0");
		results = factory.forceBuyItemInventory(tmpBean,personnelId.toString());
		resultsIter = results.iterator();
		if (resultsIter.hasNext()) {
			errorMessage = (String) resultsIter.next();
			if (log.isDebugEnabled()) {
				log.debug("p_force_buy_item_inventory    "+errorMessage);
			}
			if (errorMessage != null && !errorMessage.equalsIgnoreCase("OK"))
			{
				return errorMessage;
			}
		}
	
		return	errorMessage;
   }

   public String processData(Collection<AllocationDetailBean> beans, AllocationInputBean in,BigDecimal personnelId, PermissionBean perbean,PersonnelBean personnelBean) throws
	  BaseException {
    String errorMessage = "";
    dbManager = new DbManager(client,this.getLocale());
    Connection conn = dbManager.getConnection();
    GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager);
    
    try {
       //this.factory.beginBatch();

       deleteStage(conn);
       for(AllocationDetailBean bean:beans) {
       if( perbean.hasInventoryGroupPermission("GenerateOrders", bean.getInventoryGroup(), null, null) )
          	allocateLine(conn,bean, in, personnelBean);
       }
       errorMessage = allocatePkg(conn,personnelId,in);
       
       if(in.getForceSpecHold() != null || in.getForceQualityHold() != null)
       {
       try 
       {
	       String query = "update REQUEST_LINE_ITEM set ";
	       if(in.getForceSpecHold() == null && in.getForceQualityHold().equalsIgnoreCase("Yes"))
	       {
	    	  query = query + "rcpt_quality_hold_shelf_life = 'Y'";
	       }
	       else if(in.getForceQualityHold() == null && in.getForceSpecHold().equalsIgnoreCase("Yes"))
	       {
	    	   query = query + "rcpt_quality_hold_spec = 'Y'" ;
	       }
	       else if(in.getForceSpecHold().equalsIgnoreCase("Yes") && in.getForceQualityHold().equalsIgnoreCase("Yes"))
	       { 
	    	   query = query + "rcpt_quality_hold_shelf_life = 'Y', rcpt_quality_hold_spec = 'Y'"; 
	       }
	       
	       query = query + " where line_item = '" + in.getScratchPadLineItem()+"' and pr_number = " + in.getOrderPrNumber();
	       genericSqlFactory.deleteInsertUpdate(query);

			 query = "update purchase_request set quality_hold = 'Y' where pr_number = "+in.getOrderPrNumber();
			 genericSqlFactory.deleteInsertUpdate(query);
		 }
	   catch (Exception e) {
			e.printStackTrace();
			errorMessage = library.getString("error.db.update");
		  } 
       }
    }
    catch(Exception ex) {throw new BaseException(ex);}
    finally {
    //this.factory.endBatch();
    ConnectionPool.returnConnection(conn);
    }
    return errorMessage;
   }

   public String allocateLineDirect(AllocationInputBean ib) throws
	  BaseException {

//	   CREATE OR REPLACE PROCEDURE P_LINE_ITEM_ALLOCATE  (
//				a_company_id request_line_item.company_id%type,
//				a_pr_number request_line_item.pr_number%type,
//				a_line_item request_line_item.line_item%type default '0',
//				a_commit char default 'Y',
//				a_date_to_deliver delivery_schedule.date_to_deliver%type default null,
//				a_deleted_inventory_group_item char default 'Y',
//				a_debug char default 'N',
//				a_preserve_dedicated char default 'Y',
//				a_preserve_delete_after_buy char default 'N') is
		  String errorMsg = getProcError(
				  buildProcedureInput( 
						  ib.getCompanyId(),
						  ib.getOrderPrNumber(),
						  ib.getScratchPadLineItem()  
				   ),new Vector(),"Pkg_rli_sales_order.P_LINE_ITEM_ALLOCATE");
		  return errorMsg;
}
			
   public String allocateLine(Connection connection,AllocationDetailBean bean,AllocationInputBean ib,PersonnelBean personnelBean) throws
			  BaseException {

//	   procedure P_STAGE_CREATE_ALLOCATE_RLI (
//				a_company_id stage_create_allocate_rli.company_id%type,
//				a_pr_number stage_create_allocate_rli.pr_number%type,
//				a_line_item stage_create_allocate_rli.line_item%type,
//				a_date_to_deliver stage_create_allocate_rli.date_to_deliver%type,
//				a_catalog_price stage_create_allocate_rli.catalog_price%type,
//				a_alloc_pr_number stage_create_allocate_rli.alloc_pr_number%type,
//				a_alloc_line_item stage_create_allocate_rli.alloc_line_item%type,
//				a_doc_type stage_create_allocate_rli.doc_type%type,
//				a_doc_num stage_create_allocate_rli.doc_num%type,
//				a_doc_line stage_create_allocate_rli.doc_line%type,
//				a_quantity stage_create_allocate_rli.quantity%type,
//				a_dedicated stage_create_allocate_rli.dedicated%type,
//				a_expire_datetime stage_create_allocate_rli.expire_datetime%type,
//				a_inventory_group stage_create_allocate_rli.inventory_group%type,
//				a_doc_quantity_per_item stage_create_allocate_rli.doc_quantity_per_item%type,
//				a_cat_part_no stage_create_allocate_rli.cat_part_no%type,
//				a_catalog_company_id stage_create_allocate_rli.catalog_company_id%type default 'HAAS',
//				a_catalog_id stage_create_allocate_rli.catalog_id%type default 'Global',
//				a_part_group_no stage_create_allocate_rli.part_group_no%type default 1) is
	   			  if( isBlank(bean.getAllocation() ) )
	   					  return "";
				  Collection outArgs = new Vector();
				  BigDecimal price = bean.getUnitPrice();
				  if( bean.getPriceoverride() != null )
					  	price = bean.getPriceoverride();
//				  outArgs.add(new Integer(java.sql.Types.VARCHAR));
				  String errorMsg = getProcError(connection,
						  buildProcedureInput( 
								  ib.getCompanyId(),
								  ib.getOrderPrNumber(),
								  ib.getScratchPadLineItem(),
								  ib.getNeedDate(),
                                  //null, //if this does not work need to pass need date from MR/SP
                                  price,
								  bean.getPrNumber(),
								  bean.getLineItem(),
								  bean.getDocType(),
								  bean.getDocNum(),
								  bean.getDocLine(),
								  bean.getAllocation(),//bean.getQuantity(),
								  "Y",// delicated
								  null, // allocation expire date.
								  bean.getInventoryGroup(),
								  bean.getDocQuantityPerItem(),
								  null,// cat part no,
								  bean.getShelfLifeRequired(),
                                  "HAAS",
                                  "Global",
                                  "1",
                                  bean.getQualityHold(), 
                                  bean.getMeetSpec(), 
                                  bean.getMeetShelfLife(),
                                  ib.getForceSpecHold() == null?"N":"Y",
                                  ib.getForceQualityHold() == null?"N":"Y"

						   ),outArgs,"PKG_RLI_SALES_ORDER.P_STAGE_CREATE_ALLOCATE_RLI");
				  return errorMsg;
	  }

   public String allocatePkg(Connection connection,BigDecimal personnelId,AllocationInputBean ib) throws BaseException {

//	   procedure P_CREATE_MR_FROM_SEARCH (
//				a_company_id request_line_item.company_id%type,
// url orderPrNumber	   
//				a_pr_number request_line_item.pr_number%type,
//				a_inventory_group request_line_item.inventory_group%type,
// put in today and user will modify later.	   
//				a_required_datetime request_line_item.required_datetime%type,
//				a_item_id request_line_item.item_id%type,
//				a_spec_list catalog_item_spec.spec_list%type,
//				a_facility_id request_line_item.facility_id%type,
//null or defaults.	   
//				a_application request_line_item.application%type,
//				a_delivery_point request_line_item.delivery_point%type,
//				a_delivery_type request_line_item.delivery_type%type,
//				a_relax_shelf_life request_line_item.relax_shelf_life%type,
//				a_item_type request_line_item.item_type%type,
//			url
//				a_ship_to_company_id request_line_item.ship_to_company_id%type, url
//				a_ship_to_location_id request_line_item.ship_to_location_id%type,
//				a_bill_to_company_id purchase_request.bill_to_company_id%type,
//				a_bill_to_location_id purchase_request.bill_to_location_id%type,
//				a_ops_entity_id request_line_item.ops_entity_id%type,
//				a_ops_company_id request_line_item.ops_company_id%type,
//				a_remaining_shelf_life_percent request_line_item.remaining_shelf_life_percent%type,
//			null
//				a_shelf_life_days request_line_item.shelf_life_days%type,
//				a_shelf_life_basis request_line_item.shelf_life_basis%type,
//				a_promise_date request_line_item.promise_date%type,
//			url	
//				a_ship_complete request_line_item.ship_complete%type,
//				a_consolidate_shipment request_line_item.consolidate_shipment%type,
//			null
//				a_charge_recurrence request_line_item.charge_recurrence%type,
//				a_po_number request_line_item.po_number%type,
//				a_customer_part_no request_line_item.customer_part_no%type,
//				a_customer_po_line request_line_item.customer_po_line%type,
//				a_customer_po_price_factor request_line_item.customer_po_price_factor%type,
//				a_unit_of_sale request_line_item.unit_of_sale%type,
//				a_unit_of_sale_quantity_per_ea request_line_item.unit_of_sale_quantity_per_each%type,
//				a_tax_exempt request_line_item.tax_exempt%type,
//				a_expected_unit_cost request_line_item.expected_unit_cost%type,
//				a_notes request_line_item.notes%type,
//				a_critical request_line_item.critical%type,
//			url
//				a_currency_id request_line_item.currency_id%type,
//		        a_catalog_company_id request_line_item.catalog_company_id%type default 'HAAS',
//				a_catalog_id request_line_item.catalog_id%type default 'Global',
//				a_error out varchar2) is

	    String specList = ib.getSpecList();
	    String specDetailList = ib.getSpecDetailList();
	    String specLibraryList = ib.getSpecLibraryList();
	    String specCocList = ib.getSpecCocList();
	    String specCoaList = ib.getSpecCoaList();
	    if (specList.equalsIgnoreCase("No Specification"))
	    {
	        specList = "";
	        specDetailList = "";
	        specLibraryList = "";
	        specCocList = "";
	        specCoaList = "";
	    }
	   			  
				  Collection outArgs = new Vector(1);
				  outArgs.add(new Integer(java.sql.Types.VARCHAR));
				  String errorMsg = getProcError(connection,
						  buildProcedureInput( 
								  ib.getCompanyId(),
								  ib.getOrderPrNumber(),
								  ib.getInventoryGroup(),
								  null,
								  ib.getItemId(),
								  specList,// just to be safe. ib.getSpecList(),
								  ib.getFacilityId(),
								  null,//application
								  null,//delivery point
								  "Deliver by",// delivery type
								  null,// relax shelf life
								  null,// item type
								  ib.getShipToCompanyId(),
								  ib.getShipToLocationId(),
//								  ib.getBillToCompanyId(),
//								  ib.getBillToLocationId(),
								  ib.getOpsEntityId(),
								  ib.getOpsCompanyId(),
								  null,//ib.getRemainingShelfLifePercent(),
								  null,
								  null,
								  null,
								  ib.getShipComplete(),
								  ib.getConsolidateShipment(),
								  null,
								  null,
								  null,
								  null,
								  null,
								  ib.getUnitOfMeasure(),//
								  ib.getUnitsPerItem(),//
								  null,
								  null,
								  null,
								  null,
								  ib.getCurrencyId(),
								  ib.getIncoTerms(), //inco_terms
								  specDetailList,
								  specCocList,
								  specCoaList,
								  specLibraryList,
                                  personnelId,
                                  "Y",
                                  null,
                                  null,
                                  "n",
                                  StringHandler.emptyIfNull(ib.getScratchPadLineItem())
                           ),outArgs,"Pkg_rli_sales_order.P_CREATE_MR_FROM_SEARCH");
       log.debug(errorMsg);
                  return errorMsg;
	  }
   public String newProcessData(Collection<AllocationDetailBean> beans, AllocationInputBean in,BigDecimal personnelId, PermissionBean perbean,PersonnelBean personnelBean) throws
	  BaseException {
		 String errorMessage = "";
		 dbManager = new DbManager(client,this.getLocale());
		 Connection conn = dbManager.getConnection();
		 try {
		    //this.factory.beginBatch();
		    deleteStage(conn);
		    for(AllocationDetailBean bean:beans) {
			    if( perbean.hasInventoryGroupPermission("GenerateOrders", bean.getInventoryGroup(), null, null) )
			    	allocateLine(conn,bean, in, personnelBean);
			    }
			    errorMessage = allocatePkg(conn,personnelId,in);
		 }
		 catch(Exception ex) {throw new BaseException(ex);}
		 finally {
		 //this.factory.endBatch();
		 ConnectionPool.returnConnection(conn);
		 }
		 return errorMessage;
	}
}
