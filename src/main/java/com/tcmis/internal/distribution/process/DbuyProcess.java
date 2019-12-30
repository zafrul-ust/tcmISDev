package com.tcmis.internal.distribution.process;

import java.math.BigDecimal;
import java.sql.Connection;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

import com.tcmis.common.admin.beans.PermissionBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.beans.VvStateBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.*;
import com.tcmis.internal.distribution.beans.CustomerCreditBean;
import com.tcmis.internal.distribution.beans.DbuyContractViewBean;
import com.tcmis.internal.distribution.beans.DbuyInputBean;
import com.tcmis.internal.hub.beans.CustomerInventoryToReceiveBean;
import com.tcmis.internal.hub.factory.CustomerInventoryToReceiveBeanFactory;
import com.tcmis.internal.supply.beans.DbuyContractPriceBreakObjBean;
import com.tcmis.internal.supply.beans.DbuyContractPriceOvBean;
import com.tcmis.internal.supply.factory.DbuyContractPriceOvBeanFactory;

/******************************************************************************
 * Process for DbuyProcess
 * @version 1.0
 *****************************************************************************/
public class DbuyProcess extends GenericProcess {

	private GenericSqlFactory genericSqlFactory;

	public DbuyProcess(TcmISBaseAction act) throws BaseException {
		super(act);
	}
	
	public DbuyProcess(String client, String locale) {
	    super(client,locale);
	}

	static private BigDecimal zero = new BigDecimal(0);

	public Vector ModifyPriceList(DbuyContractPriceOvBean bean, BigDecimal personnelId) throws BaseException, Exception {
		//	   setting business logic...
		/*
		CREATE OR REPLACE PROCEDURE P_MODIFY_DBUY_CONTRACT (
		    a_dbuy_contract_id              tcm_ops.dbuy_contract.dbuy_contract_id%type,
		    a_item_id                       tcm_ops.dbuy_contract.item_id%type,
		    a_inventory_group               tcm_ops.dbuy_contract.inventory_group%type,
		    a_ship_to_company_id            tcm_ops.dbuy_contract.ship_to_company_id%type,
		    a_ship_to_location_id           tcm_ops.dbuy_contract.ship_to_location_id%type,
		    a_priority                      tcm_ops.dbuy_contract.priority%type,
		    a_supplier                      tcm_ops.dbuy_contract.supplier%type,
		    a_carrier                       tcm_ops.dbuy_contract.carrier%type default 'VT',
		    a_freight_on_board              tcm_ops.dbuy_contract.freight_on_board%type default 'CIP',
		    a_sourcer                       tcm_ops.dbuy_contract.sourcer%type,
		    a_multiple_of                   tcm_ops.dbuy_contract.multiple_of%type,
		    a_round_to_multiple             tcm_ops.dbuy_contract.round_to_multiple%type,
		    a_supplier_part_no              tcm_ops.dbuy_contract.supplier_part_no%type,
		    a_consignment                   tcm_ops.dbuy_contract.consignment%type default 'N',
		    a_lead_time_days                tcm_ops.dbuy_contract.lead_time_days%type,
		    a_loading_comments              tcm_ops.dbuy_contract.loading_comments%type,
		    a_supply_path                   tcm_ops.dbuy_contract.supply_path%type default 'Purchaser',
		    a_status                        tcm_ops.dbuy_contract.status%type,
		    a_updated_by                    tcm_ops.dbuy_contract.updated_by%type,
		    a_error    out                  varchar2,
		    a_new_dbuy_contract_id out      number,
		    a_buyer                         tcm_ops.dbuy_contract.buyer%type default null, TODO: pass here . No blank --> add it to validation
		    a_buyer_company_id              tcm_ops.dbuy_contract.buyer_company_id%type default null,  TODO: Radian Here
		    a_ops_entity_id                 tcm_ops.dbuy_contract.ops_entity_id%type default null,
		    a_ops_company_id                tcm_ops.dbuy_contract.ops_company_id%type default 'HAAS',
		    a_supplier_contact_id           tcm_ops.dbuy_contract.supplier_contact_id%type default null,
		    a_remaining_shelf_life_percent  tcm_ops.dbuy_contract.remaining_shelf_life_percent%type default 80,
		    a_critical_order_carrier        tcm_ops.dbuy_contract.critical_order_carrier%type default null,
		    a_ref_client_part_no            tcm_ops.dbuy_contract.ref_client_part_no%type default null,
		    a_ref_client_po_no              tcm_ops.dbuy_contract.ref_client_po_no%type default null,
		    a_transit_time_in_days          tcm_ops.dbuy_contract.transit_time_in_days%type default null,
		    a_pricing_type                  tcm_ops.dbuy_contract.pricing_type%type default null,
		    a_loading_date                  tcm_ops.dbuy_contract.loading_date%type default null,
		    a_ref_delivery_point            tcm_ops.dbuy_contract.ref_delivery_point%type default null,
		    a_ref_material_request          tcm_ops.dbuy_contract.ref_material_request%type default null,
		    a_ref_customer_contact_info     tcm_ops.dbuy_contract.ref_customer_contact_info%type default null,
		    a_additional_line_notes         tcm_ops.dbuy_contract.additional_line_notes%type default null,
		    a_purchaser_review              tcm_ops.dbuy_contract.purchaser_review%type default null),
		    a_buy_type                      tcm_ops.dbuy_contract.buy_type%type default null

		 */// I don't have update routine now.
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);

		Vector result = null;
		BigDecimal dbuyContractId = bean.getDbuyContractId();
		if (bean.getDbuyContractId().compareTo(zero) < 0) {
			dbuyContractId = null;
			result = new Vector();
		}
		String consign = "N";
		if (bean.getConsignment() != null && bean.getConsignment().contains("true"))
			consign = "Y";

		if (bean.getLeadTimeDays() == null)
			bean.setLeadTimeDays(new BigDecimal(1));
		
		String roundToMultiple = bean.getRoundToMultiple();
		if(bean.getRoundToMultiple().length() <= 1)
			roundToMultiple = bean.getOldRoundToMultiple();

		Vector inArgs = new Vector();
		inArgs = buildProcedureInput(dbuyContractId, bean.getItemId(),//
				bean.getInventoryGroup(), bean.getShipToCompanyId(), bean.getShipToLocationId(), bean.getPriority(), bean.getSupplier(), bean.getCarrier(), bean.getFreightOnBoard(), bean.getSourcer(), bean.getMultipleOf(), 
				roundToMultiple, bean.getSupplierPartNo(), consign, bean.getLeadTimeDays(), bean.getLoadingComments(), bean.getSupplyPath(),//Purchaser
				bean.getStatus(), personnelId, bean.getMinBuyQuantity(), bean.getMinBuyValue());

		Vector outArgs = new Vector();
		outArgs.add(new Integer(java.sql.Types.VARCHAR));//java.sql.Types.NUMERIC
		outArgs.add(new Integer(java.sql.Types.NUMERIC));//java.sql.Types.NUMERIC

		Vector intArgsOp = new Vector();
		intArgsOp.add(bean.getBuyer());
		intArgsOp.add("Radian");
		intArgsOp.add(bean.getOpsEntityId());
		intArgsOp.add(bean.getOpsCompanyId());
		intArgsOp.add(bean.getSupplierContactId());
		intArgsOp.add(bean.getRemainingShelfLifePercent());		
		intArgsOp.add(bean.getCriticalOrderCarrier());	
		
		// save the buy type if the inventory group allows setting the buy type
		if(bean.isBuyTypeAllowed() && !StringHandler.isBlankString(bean.getBuyType())){
			
			// fill in preceding optional arguments
			intArgsOp.add(""); // ref client part no
			intArgsOp.add(""); // ref client po no
			intArgsOp.add(""); // transit time in days
			intArgsOp.add(""); // pricing type
			intArgsOp.add(""); // loading date
			intArgsOp.add(""); // ref delivery point
			intArgsOp.add(""); // ref material request
			intArgsOp.add(""); // ref customer contact info
			intArgsOp.add(""); // additional line notes
			intArgsOp.add(""); // purchaser review
			
			intArgsOp.add(bean.getBuyType());
		}
		
		if (log.isDebugEnabled()) {
			log.debug("P_MODIFY_DBUY_CONTRACT" + inArgs + outArgs + intArgsOp );
		}

		result = (Vector) factory.doProcedure("P_MODIFY_DBUY_CONTRACT", inArgs, outArgs, intArgsOp);

		return result;
	}

	public String ModifyPriceListStartDate(DbuyContractPriceOvBean bean, BigDecimal personnelId) throws BaseException, Exception {
		//	   setting business logic...
		/*
		CREATE OR REPLACE PROCEDURE P_MOD_DBUY_CONTRACT_PRICE(
		a_dbuy_contract_id dbuy_contract_price.dbuy_contract_id%type,
		a_start_date dbuy_contract_price.start_date%type,
		a_unit_price dbuy_contract_price.unit_price%type,
		a_currency_id dbuy_contract_price.currency_id%type,
		a_comments dbuy_contract_price.comments%type,
		a_entered_by dbuy_contract_price.entered_by%type,
		a_updated_by dbuy_contract_price.updated_by%type,
		a_action varchar2,
		a_error_code out varchar2
		 */// I don't have update routine now.
		if (bean.getDbuyContractId().compareTo(zero) < 0)
			return "";
		String pkgCall = "P_MOD_DBUY_CONTRACT_PRICE";
		String action = "UPDATE";
		BigDecimal enterBy = null;
		if ("New".equals(bean.getLevel2Changed())) {
			action = "INSERT";
			enterBy = personnelId;
		}
		Vector outArgs = new Vector();
		outArgs.add(new Integer(java.sql.Types.VARCHAR));//java.sql.Types.NUMERIC
		outArgs.add(new Integer(java.sql.Types.NUMERIC));//java.sql.Types.NUMERIC

		return getProcError(buildProcedureInput(bean.getDbuyContractId(), bean.getStartDate(), bean.getUnitPrice(), bean.getCurrencyId(), bean.getComments(),//bean.getcomments.
				enterBy, personnelId, action), null, pkgCall);
	}

	public String DeletePriceList(DbuyContractPriceOvBean bean) throws BaseException, Exception {
		//	   setting business logic...
		/*
		PROCEDURE P_DELETE_PRICE_LIST(a_company_id IN customer.catalog_part_pricing_orig.company_id%TYPE,
		                                     a_catalog_id IN customer.catalog_part_pricing_orig.catalog_id%TYPE,
		                                     a_cat_part_no IN customer.catalog_part_pricing_orig.cat_part_no%TYPE,
		                                     a_part_group_no IN customer.catalog_part_pricing_orig.part_group_no%TYPE,
		                                     a_price_group_id IN customer.catalog_part_pricing_orig.price_group_id%TYPE,
		                                     a_start_date IN customer.catalog_part_pricing_orig.start_date%TYPE,
		                                     a_error_message OUT VARCHAR2) IS
		 */// I don't have update routine now.
		if (bean.getDbuyContractId().compareTo(zero) < 0)
			return "";

		String query = "delete from dbuy_contract_price_break where dbuy_contract_id = {0} and start_date = {1}";
		//     System.out.println("Delete q:"+query);
		deleteInsertUpdate(query, bean.getDbuyContractId(), bean.getStartDate());
		return "";
	}

	public String newPriceBreak(DbuyContractPriceOvBean bean, DbuyContractPriceOvBean bean1, BigDecimal personnelId) throws BaseException, Exception {//String companyId,String name,String desc,String ops,String currencyId,Date bd)throws BaseException, Exception{
		//	   setting business logic...
		/*
		CREATE OR REPLACE PROCEDURE P_MOD_DBUY_CONTRACT_PRICE_BRK(
		a_dbuy_contract_id dbuy_contract_price_break.dbuy_contract_id%type,
		a_start_date dbuy_contract_price_break.start_date%type,
		a_break_quantity dbuy_contract_price_break.break_quantity%type,
		a_unit_price dbuy_contract_price_break.unit_price%type,
		a_comments dbuy_contract_price_break.comments%type,
		a_entered_by dbuy_contract_price_break.entered_by%type,
		a_updated_by dbuy_contract_price_break.updated_by%type,
		a_action varchar2,
		a_error_code out varchar2*/
		// I don't have update routine now.
		if (bean.getDbuyContractId().compareTo(zero) < 0)
			return "";
		String pkgCall = "P_MOD_DBUY_CONTRACT_PRICE_BRK";
		Vector outArgs = new Vector();
		outArgs.add(new Integer(java.sql.Types.VARCHAR)); // price group id
		outArgs.add(new Integer(java.sql.Types.VARCHAR)); // error

		return getProcOut(buildProcedureInput(bean.getDbuyContractId(), bean.getStartDate(), bean1.getBreakQuantity(), bean1.getUnitPrice(), bean1.getComments(), personnelId,//bean.getcomments,bean.getenteredby
				personnelId, "INSERT"), null, pkgCall);
	}

	public Collection getDbuy(DbuyInputBean bean, BigDecimal personnelId) throws BaseException {
		DbuyContractPriceOvBeanFactory factory = new DbuyContractPriceOvBeanFactory(dbManager);
		SearchCriteria sc = new SearchCriteria();
		SortCriteria sort = new SortCriteria();
		sort.addCriterion("itemId");
		sort.addCriterion("dbuyContractId");
		sort.addCriterion("startDate");

		String rowCount = "0";
		if (!isBlank(bean.getOpsEntityId())) {
			sc.addCriterion("opsEntityId", SearchCriterion.EQUALS, bean.getOpsEntityId());

			GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager);
			rowCount = genericSqlFactory.selectSingleValue("select count(*) from dbuy_contract where ops_entity_id = '" + bean.getOpsEntityId() + "' and inventory_group = 'All'");
		}
		String userInventory = "";
		if (!isBlank(bean.getInventoryGroup())) {
			userInventory = " and inventory_group = '" + bean.getInventoryGroup() + "' ";
		}
		else {
			userInventory = " and inventory_group in (select inventory_group from user_inventory_group where personnel_id = " + personnelId + " and ops_entity_id = '" + bean.getOpsEntityId() + "') ";
		}

		sc.addCriterionWithMode(bean.getSearchField(), bean.getSearchMode(), bean.getSearchArgument());

		if (!isBlank(bean.getSupplier()))
			sc.addCriterion("supplier", SearchCriterion.EQUALS, bean.getSupplier());

		if ("Y".equals(bean.getShowExpired()))
			sc.addCriterion("status", SearchCriterion.IN, "'DBUY','PRICING','INACTIVE'");
		else
			sc.addCriterion("status", SearchCriterion.IN, "'DBUY','PRICING'");

		if (bean.hasSupplyPath()) {
			sc.addCriterion("supplyPath", SearchCriterion.EQUALS, bean.getSupplyPath());
		}
		
		Collection c1 = null;
		Collection c2 = null;
		Collection finalColleciton;
		if (rowCount.equalsIgnoreCase("0")) {
			if (!isBlank(bean.getHub()) && !isBlank(bean.getInventoryGroup()))
				sc.addCriterion("inventoryGroupHub", SearchCriterion.EQUALS, bean.getHub());
			c1 = factory.selectObject(sc, sort, userInventory,bean.getShowPriceHistory());
		}
		else {
			/*get records fo IG All*/
			userInventory = " and inventory_group = 'All' ";
			c1 = factory.selectObject(sc, sort, userInventory,bean.getShowPriceHistory());

			/*then specific hub records*/
			if (!isBlank(bean.getHub())) {
				sc.addCriterion("inventoryGroupHub", SearchCriterion.EQUALS, bean.getHub());
				if (!isBlank(bean.getInventoryGroup())) {
					userInventory = "and inventory_group <> 'All' and inventory_group = '" + bean.getInventoryGroup() + "' ";
				}
				else if (!isBlank(bean.getHub())) {
					userInventory = "and inventory_group <> 'All' and inventory_group in (select inventory_group from user_inventory_group where personnel_id = " + personnelId + " and hub = " + bean.getHub() + " and ops_entity_id = '"
					+ bean.getOpsEntityId() + "') ";
				}
				else {
					userInventory = "and inventory_group <> 'All' and inventory_group in (select inventory_group from user_inventory_group where personnel_id = " + personnelId + " and ops_entity_id = '" + bean.getOpsEntityId() + "') ";
				}
				c2 = factory.selectObject(sc, sort, userInventory,bean.getShowPriceHistory());
			}
			else if (isBlank(bean.getHub()) && isBlank(bean.getInventoryGroup())) {
				userInventory = "and inventory_group <> 'All' and inventory_group in (select inventory_group from user_inventory_group where personnel_id = " + personnelId + " and ops_entity_id = '" + bean.getOpsEntityId() + "') ";
				c2 = factory.selectObject(sc, sort, userInventory,bean.getShowPriceHistory());
			}
            else {
				if (!isBlank(bean.getInventoryGroup())) {
					userInventory = "and inventory_group <> 'All' and inventory_group = '" + bean.getInventoryGroup() + "' ";
				}
				else {
					userInventory = "and inventory_group <> 'All' and inventory_group in (select inventory_group from user_inventory_group where personnel_id = " + personnelId + " and ops_entity_id = '" + bean.getOpsEntityId() + "') ";
				}
				c2 = factory.selectObject(sc, sort, userInventory,bean.getShowPriceHistory());
			}
        }

		if (c2 != null) {
			c1.addAll(c2);
		}

		return c1;
	}

	protected String getProcOut(Collection inArgs, Collection outArgs, String procname) {
		boolean hasError = false;
		String errorMsg = "";
		Collection c = null;

		if (inArgs == null)
			inArgs = new Vector();
		if (outArgs == null) {
			outArgs = new Vector();
			outArgs.add(new Integer(java.sql.Types.VARCHAR));
		}

		try {
			c = dbManager.doProcedure(procname, inArgs, outArgs);
		}
		catch (Exception ex) {
			hasError = true;
		}
		String procResult = "";
		if (c != null) {
			Iterator it = c.iterator();
			if (it.hasNext())
				procResult = (String) it.next(); // price group id
			System.out.println("Out 1:" + procResult);
			if (it.hasNext())
				procResult = (String) it.next(); // error
			System.out.println("Out 2:" + procResult);
			if (!isBlank(procResult) && !"OK".equalsIgnoreCase(procResult)) {
				hasError = true;
				log.error(procname + " returned:" + procResult);
			}
		}
		if (hasError) {
			ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
			errorMsg = library.getString("generic.error");
		}
		return errorMsg;
	}

	public ExcelHandler getExcelReport(DbuyInputBean ibean, PersonnelBean pbean) throws BaseException {

		BigDecimal personnelId = new BigDecimal(pbean.getPersonnelId());
		Locale locale = pbean.getLocale();
		Collection<DbuyContractPriceOvBean> data = this.getDbuy(ibean, personnelId);

		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		ExcelHandler pw = new ExcelHandler(library);

		pw.addTable();

		//  write column headers
		pw.addRow();

		/*Pass the header keys for the Excel.*/
		String[] headerkeys = { "label.catalogitem", "label.description", "label.shipto", "label.inventorygroup", "label.priority", "label.supplier", "label.supplierpartnum", "label.leadtimeindays",
				"label.status","label.startDate","label.currencyid","label.fromquantity", "label.unitprice","label.multipleOf","label.minimumbuyqty", "label.minimumordervalue",
				"label.supplypath","label.remainingShelfLife","label.incoterms","label.consignment",
				"label.carriername", "label.buyername", "label.rounding","label.suppliercontact", "label.lastUpdatedBy", "label.laastUpdatedDate" };
		/*This array defines the type of the excel column.
		  0 means default depending on the data type. */
		int[] types = { 0, 0, 0, 0, pw.TYPE_NUMBER, 0, 0, pw.TYPE_NUMBER, 0,pw.TYPE_CALENDAR, 0, pw.TYPE_NUMBER, 0, 0,  0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, pw.TYPE_CALENDAR };
		int[] widths = { 0, 25, 0, 15, 0, 25, 0, 0, 0, 0, 12, 15, 0, 12, 0, 10, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		pw.applyColumnHeader(headerkeys, types, widths);//, horizAligns);

		BigDecimal one = new BigDecimal("1");
		for (DbuyContractPriceOvBean bean : data) {
			pw.addRow();
			pw.addCell(bean.getItemId());
			pw.addCell(bean.getItemDesc());
			pw.addCell(bean.getShipToLocationId());
			pw.addCell(bean.getInventoryGroupName());
			pw.addCell(bean.getPriority());
			pw.addCell(bean.getSupplier()+"-"+bean.getSupplierName());
			pw.addCell(bean.getSupplierPartNo());
			pw.addCell(bean.getLeadTimeDays());
			pw.addCell(bean.getStatus());
			pw.addCell(bean.getStartDate());
			pw.addCell(bean.getCurrencyId());
			pw.addCell(one);
			pw.addCell(bean.getUnitPrice());
			pw.addCell(bean.getMultipleOf());
			pw.addCell(bean.getMinBuyQuantity());
			pw.addCell(bean.getMinBuyValue());
			pw.addCell(bean.getSupplyPath());
			pw.addCell(bean.getRemainingShelfLifePercent());
			pw.addCell(bean.getFreightOnBoard());
			pw.addCell(bean.getConsignment());
			pw.addCell(bean.getCarrierName());
			pw.addCell(bean.getBuyerName());
			if (bean.getMultipleOf() != null && bean.getMultipleOf().intValue() > 1)
				pw.addCell(bean.getRoundToMultiple());
			else
				pw.addCell("");
			pw.addCell(bean.getSupplierContactName());
			pw.addCell(bean.getUpdateByName());
			pw.addCell(bean.getUpdatedDate());
			for (DbuyContractPriceBreakObjBean bb : (Collection<DbuyContractPriceBreakObjBean>) bean.getPriceBreakCollection()) {
				pw.addRow();
				pw.addCell(bean.getItemId());
				pw.addCell(bean.getItemDesc());
				pw.addCell(bean.getShipToLocationId());
				pw.addCell(bean.getInventoryGroupName());
				pw.addCell(bean.getPriority());
				pw.addCell(bean.getSupplier()+"-"+bean.getSupplierName());
				pw.addCell(bean.getSupplierPartNo());
				pw.addCell(bean.getLeadTimeDays());
				pw.addCell(bean.getStatus());
				pw.addCell(bean.getStartDate());
				pw.addCell(bean.getCurrencyId());
				pw.addCell(bb.getBreakQuantity());
				pw.addCell(bb.getUnitPrice());
				pw.addCell(bean.getMultipleOf());
				pw.addCell(bean.getMinBuyQuantity());
				pw.addCell(bean.getMinBuyValue());
				pw.addCell(bean.getSupplyPath());
				pw.addCell(bean.getRemainingShelfLifePercent());
				pw.addCell(bean.getFreightOnBoard());
				pw.addCell(bean.getConsignment());
				pw.addCell(bean.getCarrierName());
				pw.addCell(bean.getBuyerName());
				pw.addCell("");	//roundToMultiple
				pw.addCell(bean.getSupplierContactName());
				pw.addCell(bean.getUpdateByName());
				pw.addCell(bean.getUpdatedDate());
			}
		}
		return pw;
	}

	public String getPriority(DbuyContractPriceOvBean bean) throws BaseException, Exception {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new CustomerCreditBean());

		StringBuilder query = new StringBuilder("select max(priority)+1 from dbuy_contract where OPS_ENTITY_ID  = '");
		query.append(bean.getOpsEntityId());
		query.append("' and ITEM_ID = ");
		query.append(bean.getItemId());
		query.append(" and INVENTORY_GROUP = '");
		query.append(bean.getInventoryGroup());
		query.append("' and SHIP_TO_COMPANY_ID = '");
		query.append(bean.getShipToCompanyId());
		query.append("' and SHIP_TO_LOCATION_ID = '");
		query.append(bean.getShipToLocationId());
		query.append("'");
//		log.debug(query);

		return factory.selectSingleValue(query.toString());
	}
	
	public Collection getSupData(BigDecimal dBuyContractId) throws BaseException {
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new DbuyContractPriceOvBean());
		String query = "";
		query="select * from DBUY_CONTRACT_PRICE_OV where dbuy_contract_id="+dBuyContractId;
		
		return factory.selectQuery(query);
	}
	
	public Collection getState() throws BaseException {
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new VvStateBean());
		String query = "";
		query="select * from vv_state where country_abbrev='USA' order by state";
		
		return factory.selectQuery(query);
	}
	
	public String editSupData(DbuyContractPriceOvBean bean) throws BaseException, Exception {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new DbuyContractPriceOvBean());
		String result = "OK";
		
		StringBuilder query = new StringBuilder("update DBUY_CONTRACT set");
		if(bean.getSourcer() != null){query.append(" SOURCER = ").append(bean.getSourcer()).append(",");}
		else{query.append(" SOURCER = '' ,");}
//		if(bean.getCriticalOrderCarrier() != null){query.append(" CRITICAL_ORDER_CARRIER = '").append(bean.getCriticalOrderCarrier()).append("',");} 
//		else{query.append(" CRITICAL_ORDER_CARRIER = '' ,");}
		if(bean.getRefClientPartNo() != null){query.append(" REF_CLIENT_PART_NO = '").append(bean.getRefClientPartNo()).append("',");}
		else{query.append(" REF_CLIENT_PART_NO = '' ,");}
		if(bean.getRefClientPoNo() != null){query.append(" REF_CLIENT_PO_NO = '").append(bean.getRefClientPoNo()).append("',");}
		else{query.append(" REF_CLIENT_PO_NO = '' ,");}
		if(bean.getRefDeliveryPoint() != null){query.append(" REF_DELIVERY_POINT = '").append(bean.getRefDeliveryPoint()).append("',");}
		else{query.append(" REF_DELIVERY_POINT = '' ,");}
		if(bean.getRefMaterialRequest() != null){query.append(" REF_MATERIAL_REQUEST = '").append(bean.getRefMaterialRequest()).append("',");}
		else{query.append(" REF_MATERIAL_REQUEST = '' ,");}
		if(bean.getRefCustomerContactInfo() != null){query.append(" REF_CUSTOMER_CONTACT_INFO = '").append(bean.getRefCustomerContactInfo()).append("',");}
		else{query.append(" REF_CUSTOMER_CONTACT_INFO = '' ,");}
		if(bean.getPurchaserReview() != null){query.append(" PURCHASER_REVIEW = '").append(bean.getPurchaserReview()).append("',");}
		else{query.append(" PURCHASER_REVIEW = 'N' ,");}
		if(bean.getConsignment() != null){query.append(" CONSIGNMENT = '").append(bean.getConsignment()).append("',");}
		else{query.append(" CONSIGNMENT = 'N' ,");}
		if(bean.getDefaultShipmentOriginState() != null){query.append(" DEFAULT_SHIPMENT_ORIGIN_STATE = '").append(bean.getDefaultShipmentOriginState()).append("',");}
		if(bean.getTransitTimeInDays() != null){query.append(" TRANSIT_TIME_IN_DAYS = ").append(bean.getTransitTimeInDays()).append(",");}
		else{query.append(" TRANSIT_TIME_IN_DAYS = 4 ,");}
		if(bean.getLeadTimeDays() != null){query.append(" LEAD_TIME_DAYS = ").append(bean.getLeadTimeDays()).append(",");}
		else{query.append(" LEAD_TIME_DAYS = 1 ,");}
		if(bean.getPricingType() != null){query.append(" PRICING_TYPE = '").append(bean.getPricingType()).append("',");}
		if(bean.getDefCustomerServiceRepId() != null){query.append(" DEF_CUSTOMER_SERVICE_REP_ID = ").append(bean.getDefCustomerServiceRepId()).append(",");}
		else{query.append(" DEF_CUSTOMER_SERVICE_REP_ID = '' ,");}
		if(bean.getFreightOnBoard() != null){query.append(" FREIGHT_ON_BOARD = '").append(bean.getFreightOnBoard()).append("',");}
		else{query.append(" FREIGHT_ON_BOARD = '' ,");}
		if(bean.getAdditionalLineNotes() != null){query.append(" ADDITIONAL_LINE_NOTES = ").append(SqlHandler.delimitString(bean.getAdditionalLineNotes())).append("");}
		else{query.append(" ADDITIONAL_LINE_NOTES = ''");}
		query.append(" where DBUY_CONTRACT_ID = ").append(bean.getDbuyContractId());
		log.debug(query);
		try{
		    factory.deleteInsertUpdate(query.toString());
		   }
	   catch (Exception e) {
		e.printStackTrace();
		result = "Not Updated";
	   }
		return result;
	}
	
	public Collection getPriceList(DbuyInputBean bean, BigDecimal personnelId) throws BaseException {
		String viewCall = "DBUY_CONTRACT_VIEW";
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory dbuyContractViewFactory = new GenericSqlFactory(dbManager, new DbuyContractViewBean());
		GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager);
		SearchCriteria sc = new SearchCriteria();
		SearchCriteria sc2 = new SearchCriteria();
		SortCriteria sort = new SortCriteria();
		sort.addCriterion("itemId");
		sort.addCriterion("dbuyContractId");
		sort.addCriterion("breakQuantity");

		String rowCount = "0";
		if (!isBlank(bean.getOpsEntityId())) {
			sc.addCriterion("opsEntityId", SearchCriterion.EQUALS, bean.getOpsEntityId());
			sc2.addCriterion("opsEntityId", SearchCriterion.EQUALS, bean.getOpsEntityId());

			rowCount = genericSqlFactory.selectSingleValue("select count(*) from dbuy_contract where ops_entity_id = '" + bean.getOpsEntityId() + "' and inventory_group = 'All'");
		}
		

		if(!isBlank(bean.getSearchArgument())){
			sc.addCriterionWithMode(bean.getSearchField(), bean.getSearchMode(), bean.getSearchArgument());
			sc2.addCriterionWithMode(bean.getSearchField(), bean.getSearchMode(), bean.getSearchArgument());
		}

		if (!isBlank(bean.getSupplier())){
			sc.addCriterion("supplier", SearchCriterion.EQUALS, bean.getSupplier());
			sc2.addCriterion("supplier", SearchCriterion.EQUALS, bean.getSupplier());
		}

		//Collection userInventory = factory.selectQuery("select inventory_group from user_inventory_group where personnel_id = " + personnelId + " and ops_entity_id = '" + bean.getOpsEntityId() + "'");	
		String nestedUserInventoryQuery = "select inventory_group from user_inventory_group";
		SearchCriteria nestedCriteria = new SearchCriteria("personnelId", SearchCriterion.EQUALS, personnelId + "");
		nestedCriteria.addCriterion("opsEntityId", SearchCriterion.EQUALS,  bean.getOpsEntityId());
		
		if ("Y".equals(bean.getShowExpired()))
			sc.addCriterion("status", SearchCriterion.IN, "'DBUY','PRICING','INACTIVE'");
		else
			sc.addCriterion("status", SearchCriterion.IN, "'DBUY','PRICING'");
		
		Collection c1 = null;
		Collection c2 = null;
		Collection finalColleciton;
		if (rowCount.equalsIgnoreCase("0")) {
			if (!isBlank(bean.getInventoryGroup())) {
				sc.addCriterion("inventoryGroup", SearchCriterion.EQUALS, bean.getInventoryGroup());
			}
			else {
				//sc.addCriterion("inventoryGroup", SearchCriterion.IN, userInventory, "true");
				sc.addNestedQuery("inventoryGroup", nestedUserInventoryQuery, nestedCriteria);
			}
			
			if (!isBlank(bean.getHub()) && !isBlank(bean.getInventoryGroup()))
				sc.addCriterion("hub", SearchCriterion.EQUALS, bean.getHub());
			
			c1 = dbuyContractViewFactory.select(sc, sort, viewCall);
		}
		else {
			/*get records fo IG All*/
			sc.addCriterion("inventoryGroup", SearchCriterion.EQUALS, "All");
			
			c1 = dbuyContractViewFactory.select(sc, sort, viewCall);

			/*then specific hub records*/
			if (!isBlank(bean.getHub())) {
				sc2.addCriterion("hub", SearchCriterion.EQUALS, bean.getHub());
				
				if (!isBlank(bean.getInventoryGroup())) {	
					sc2.addCriterion("inventoryGroup", SearchCriterion.EQUALS, bean.getInventoryGroup());
					//sc2.addNestedQuery("inventoryGroup", nestedUserInventoryQuery, nestedCriteria);
				}
				else {
					//userInventory = factory.selectQuery("select inventory_group from user_inventory_group where personnel_id = " + personnelId + " and hub = " + bean.getHub() + " and ops_entity_id = '" + bean.getOpsEntityId() + "'");	
					nestedCriteria.addCriterion("hub",  SearchCriterion.EQUALS,  bean.getHub());
					sc2.addNestedQuery("inventoryGroup", nestedUserInventoryQuery, nestedCriteria);
					//sc2.addCriterion("inventoryGroup", SearchCriterion.IN, userInventory, "true");
				}

				c2 = dbuyContractViewFactory.select(sc2, sort, viewCall);
			}
			else if (isBlank(bean.getHub()) && isBlank(bean.getInventoryGroup())) {
				//sc2.addCriterion("inventoryGroup", SearchCriterion.IN, userInventory, "true");
				sc2.addNestedQuery("inventoryGroup", nestedUserInventoryQuery, nestedCriteria);
				c2 = dbuyContractViewFactory.select(sc2, sort, viewCall);
			}
            else {
				if (!isBlank(bean.getInventoryGroup())) {
					sc2.addCriterion("inventoryGroup", SearchCriterion.EQUALS, bean.getInventoryGroup());
				}
				else {
					//sc2.addCriterion("inventoryGroup", SearchCriterion.IN, userInventory, "true");
					sc2.addNestedQuery("inventoryGroup", nestedUserInventoryQuery, nestedCriteria);
				}
				c2 = dbuyContractViewFactory.select(sc2, sort, viewCall);
			}
        }

		if (c2 != null) {
			c1.addAll(c2);
		}
		return c1;
	}
	
	public ExcelHandlerXlsx getPriceListUpdateTemplateExcel(DbuyInputBean ibean, PersonnelBean pbean) throws BaseException {

		BigDecimal personnelId = new BigDecimal(pbean.getPersonnelId());
		Locale locale = pbean.getLocale();
		Collection<DbuyContractViewBean> data = this.getPriceList(ibean, personnelId);

		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		ExcelHandlerXlsx pw = new ExcelHandlerXlsx(library);

		pw.addTable();

		//  write column headers
		pw.addRow();

		/*Pass the header keys for the Excel.*/		
		String[] headerkeys = { "label.id", "label.operatingentityid", "label.operatingentity", "label.inventorygroupname", "label.shipto", "label.supplierid", "label.supplier", "label.supplierpartnum", "label.catalogitem", "label.description", "label.currency", 
				"label.unitprice", "label.startDate", "label.newsingleunitprice", "label.newstartdate", "label.breakquantity", "label.breakprice", "label.newbreakquantity", "label.newbreakprice", "label.leadtimeindays", "label.newleadtimeindays", "label.minimumbuyqty", "label.minimumordervalue",  "label.newminimumbuyqty" ,"label.newminimumordervalue"};
		/*This array defines the type of the excel column.
		  0 means default depending on the data type. */
		int[] types = { pw.TYPE_NUMBER, 0, 0, 0, 0, 0, 0, 0, pw.TYPE_NUMBER, pw.TYPE_STRING, 0, pw.TYPE_NUMBER, pw.TYPE_CALENDAR,pw.TYPE_NUMBER, pw.TYPE_CALENDAR,pw.TYPE_NUMBER,pw.TYPE_NUMBER, pw.TYPE_NUMBER,pw.TYPE_NUMBER, pw.TYPE_NUMBER, pw.TYPE_NUMBER,pw.TYPE_NUMBER,pw.TYPE_NUMBER,pw.TYPE_NUMBER,pw.TYPE_NUMBER};
		
		/*This array defines the default width of the column when the Excel file opens.
		0 means the width will be default depending on the data type.*/

		int[] widths = { 0, 15, 20, 15, 10, 10, 10, 10, 0, 50, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		
		/*This array defines the horizontal alignment of the data in a cell.
		0 means excel defaults the horizontal alignemnt by the data type.*/
		//    int[] horizAligns = {
		//      0,pw.ALIGN_CENTER,pw.ALIGN_CENTER,0};

		//    pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

		pw.applyColumnHeader(headerkeys, types, widths);//, horizAligns);

		//  now write data
		BigDecimal one = new BigDecimal("1");
		for (DbuyContractViewBean bean : data) {
			pw.addRow();
			pw.addCell(bean.getDbuyContractId());
			pw.addCell(bean.getOpsEntityId());
			pw.addCell(bean.getOpsEntity());
			pw.addCell(bean.getInventoryGroupName());
			pw.addCell(bean.getShipToLocationId());
			pw.addCell(bean.getSupplier());
			pw.addCell(bean.getSupplierName());
			pw.addCell(bean.getSupplierPartNo());
			pw.addCell(bean.getItemId());
			pw.addCell(bean.getItemDesc());
			pw.addCell(bean.getCurrencyId());
			pw.addCell(bean.getCurrentPrice());
			pw.addCell(bean.getCurrentPriceDt());
			pw.addCell("");
			pw.addCell(new Date());
			pw.addCell(bean.getBreakQuantity());
			pw.addCell(bean.getUnitPrice());
			pw.addCell(bean.getBreakQuantity());
			pw.addCell(bean.getUnitPrice());
			pw.addCell(bean.getLeadTimeDays());
			pw.addCell(bean.getLeadTimeDays());
			pw.addCell(bean.getMinBuyQuantity());
			pw.addCell(bean.getMinBuyValue());
			pw.addCell(bean.getMinBuyQuantity());
			pw.addCell(bean.getMinBuyValue());
			
		}
		return pw;
	}
	
	public ExcelHandlerXlsx getPriceListInsertTemplateExcel(Locale locale) throws BaseException {

		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		ExcelHandlerXlsx pw = new ExcelHandlerXlsx(library);

		pw.addTable();

		//  write column headers
		pw.addRow();

		/*Pass the header keys for the Excel.*/		
		String[] headerkeys = { "label.operatingentity", "label.catalogitemid", "label.itemdescription", "label.inventorygroupname","label.priority", 
				"label.supplier", "label.supplierid", "label.supplierpartnum", "label.leadtimeindays", "peiproject.label.startdate", "label.currency", 
				"label.unitprice", "label.multipleOf", "label.minimumbuyqty", "label.minimumordervalue", "label.breakquantity", 
				"label.breakprice", "label.supplypath", "label.remainingShelfLife", "label.incoterms","label.buyerid"};
		/*This array defines the type of the excel column.
		  0 means default depending on the data type. */
		int[] types = { 0, 0, 0, 0, 0, 
				0, 0, 0, 0, 0, 0, 
				0, 0, 0, 0, 0,
				0, 0, 0, 0,0};
		
		/*This array defines the default width of the column when the Excel file opens.
		0 means the width will be default depending on the data type.*/

		int[] widths = { 20, 0, 50, 15, 0, 
				20, 0, 20, 0, 0,
				0, 0, 0, 0, 0, 0, 
				0, 15, 0, 0, 0,0};
		
		pw.applyColumnHeader(headerkeys, types, widths);

		return pw;
	}
}
