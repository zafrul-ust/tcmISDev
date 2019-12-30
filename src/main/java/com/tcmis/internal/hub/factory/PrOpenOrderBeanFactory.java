package com.tcmis.internal.hub.factory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.internal.hub.beans.AllocationAnalysisInputBean;
import com.tcmis.internal.hub.beans.PrOpenOrderBean;

/******************************************************************************
 * CLASSNAME: PrOpenOrderBeanFactory <br>
 * 
 * @version: 1.0, Sep 26, 2006 <br>
 *****************************************************************************/

public class PrOpenOrderBeanFactory extends BaseBeanFactory {

	Log				log										= LogFactory.getLog(this.getClass());

	// column names
	public String	ATTRIBUTE_SUPPLIER						= "SUPPLIER";
	public String	ATTRIBUTE_ORIGINAL_PROMISED_DATE		= "ORIGINAL_PROMISED_DATE";
	public String	ATTRIBUTE_REQUESTOR_LAST_NAME			= "REQUESTOR_LAST_NAME";
	public String	ATTRIBUTE_REQUESTOR_FIRST_NAME			= "REQUESTOR_FIRST_NAME";
	public String	ATTRIBUTE_COMPANY_ID					= "COMPANY_ID";
	public String	ATTRIBUTE_RELEASE_DATE					= "RELEASE_DATE";
	public String	ATTRIBUTE_REQUIRED_DATETIME				= "REQUIRED_DATETIME";
	public String	ATTRIBUTE_SYSTEM_REQUIRED_DATETIME		= "SYSTEM_REQUIRED_DATETIME";
	public String	ATTRIBUTE_DELAY							= "DELAY";
	public String	ATTRIBUTE_PR_NUMBER						= "PR_NUMBER";
	public String	ATTRIBUTE_LINE_ITEM						= "LINE_ITEM";
	public String	ATTRIBUTE_OPEN_QUANTITY					= "OPEN_QUANTITY";
	public String	ATTRIBUTE_ALLOCATION_TYPE				= "ALLOCATION_TYPE";
	public String	ATTRIBUTE_REF_NO						= "REF_NO";
	public String	ATTRIBUTE_REF_LINE						= "REF_LINE";
	public String	ATTRIBUTE_ALLOC_QUANTITY				= "ALLOC_QUANTITY";
	public String	ATTRIBUTE_REF_STATUS					= "REF_STATUS";
	public String	ATTRIBUTE_PROGRESS_STATUS				= "PROGRESS_STATUS";
	public String	ATTRIBUTE_REF_DATE						= "REF_DATE";
	public String	ATTRIBUTE_FAC_PART_NO					= "FAC_PART_NO";
	public String	ATTRIBUTE_SOURCE_HUB					= "SOURCE_HUB";
	public String	ATTRIBUTE_FACILITY_ID					= "FACILITY_ID";
	public String	ATTRIBUTE_REQUESTOR						= "REQUESTOR";
	public String	ATTRIBUTE_ITEM_TYPE						= "ITEM_TYPE";
	public String	ATTRIBUTE_ITEM_ID						= "ITEM_ID";
	public String	ATTRIBUTE_HAZMAT_ID_MISSING				= "HAZMAT_ID_MISSING";
	public String	ATTRIBUTE_QTY_ON_HAND					= "QTY_ON_HAND";
	public String	ATTRIBUTE_QTY_AVAILABLE					= "QTY_AVAILABLE";
	public String	ATTRIBUTE_IG_QTY_ON_HAND				= "IG_QTY_ON_HAND";
	public String	ATTRIBUTE_IG_QTY_AVAILABLE				= "IG_QTY_AVAILABLE";
	public String	ATTRIBUTE_REQUIRED_DATETIME_SORT		= "REQUIRED_DATETIME_SORT";
	public String	ATTRIBUTE_NOTES							= "NOTES";
	public String	ATTRIBUTE_MR_NOTES						= "MR_NOTES";
	public String	ATTRIBUTE_CRITICAL						= "CRITICAL";
	public String	ATTRIBUTE_INVENTORY_GROUP				= "INVENTORY_GROUP";
	public String	ATTRIBUTE_PICKABLE						= "PICKABLE";
	public String	ATTRIBUTE_MFG_LOT						= "MFG_LOT";
	public String	ATTRIBUTE_APPLICATION					= "APPLICATION";
	public String	ATTRIBUTE_APPLICATION_DESC				= "APPLICATION_DESC";
	public String	ATTRIBUTE_LOT_STATUS_AGE				= "LOT_STATUS_AGE";
	public String	ATTRIBUTE_ORDER_QUANTITY				= "ORDER_QUANTITY";
	public String	ATTRIBUTE_PART_DESCRIPTION				= "PART_DESCRIPTION";
	public String	ATTRIBUTE_DELIVERY_TYPE					= "DELIVERY_TYPE";
	public String	ATTRIBUTE_LOOK_AHEAD_DAYS				= "LOOK_AHEAD_DAYS";
	public String	ATTRIBUTE_REQUIRED_DATETIME_TYPE		= "REQUIRED_DATETIME_TYPE";
	public String	ATTRIBUTE_REQUEST_LINE_STATUS			= "REQUEST_LINE_STATUS";
	public String	ATTRIBUTE_CANCEL_STATUS					= "CANCEL_STATUS";
	public String	ATTRIBUTE_OWNER_INVENTORY_GROUP			= "OWNER_INVENTORY_GROUP";
	public String	ATTRIBUTE_OCONUS						= "OCONUS";
	public String	ATTRIBUTE_FOB_HUB						= "FOB_HUB";
	public String	ATTRIBUTE_GLOBAL_CATALOG				= "GLOBAL_CATALOG";
	public String	ATTRIBUTE_ALTERNATE_NAME				= "ALTERNATE_NAME";
	public String	ATTRIBUTE_CATALOG_ITEM_DESCRIPTION		= "CATALOG_ITEM_DESCRIPTION";
	public String	ATTRIBUTE_CUSTOMER_ID					= "CUSTOMER_ID";
	public String	ATTRIBUTE_CUSTOMER_NAME					= "CUSTOMER_NAME";
	public String	ATTRIBUTE_FACILITY_NAME					= "FACILITY_NAME";
	public String	ATTRIBUTE_INVENTORY_GROUP_NAME			= "INVENTORY_GROUP_NAME";
	public String	ATTRIBUTE_OWNER_INVENTORY_GROUP_NAME	= "OWNER_INVENTORY_GROUP_NAME";
	public String	ATTRIBUTE_MATERIAL_REQUEST_ORIGIN		= "MATERIAL_REQUEST_ORIGIN";
	public String	ATTRIBUTE_SHIP_TO_LOCATION_ID			= "SHIP_TO_LOCATION_ID";
	public String	ATTRIBUTE_PRICE_GROUP_ID				= "PRICE_GROUP_ID";
	public String	ATTRIBUTE_BILL_TO_COMPANY_ID			= "BILL_TO_COMPANY_ID";
	public String	ATTRIBUTE_BILL_TO_LOCATION_ID			= "BILL_TO_LOCATION_ID";
	public String	ATTRIBUTE_INCO_TERMS					= "INCO_TERMS";
	public String	ATTRIBUTE_UNIT_OF_SALE					= "UNIT_OF_SALE";
	public String	ATTRIBUTE_SHIP_COMPLETE					= "SHIP_COMPLETE";
	public String	ATTRIBUTE_CONSOLIDATE_SHIPMENT			= "CONSOLIDATE_SHIPMENT";
	public String	ATTRIBUTE_CURRENCY_ID					= "CURRENCY_ID";
	public String	ATTRIBUTE_OPS_ENTITY_ID					= "OPS_ENTITY_ID";
	public String	ATTRIBUTE_OPS_COMPANY_ID				= "OPS_COMPANY_ID";
	public String	ATTRIBUTE_LABEL_SPEC					= "LABEL_SPEC";
	public String	ATTRIBUTE_SPEC_LIST						= "SPEC_LIST";
	public String	ATTRIBUTE_SPEC_LIST_CONCAT				= "SPEC_LIST_CONCAT";
	public String	ATTRIBUTE_SPEC_LIBRARY_CONCAT			= "SPEC_LIBRARY_CONCAT";
	public String	ATTRIBUTE_SPEC_DETAIL_CONCAT			= "SPEC_DETAIL_CONCAT";
	public String	ATTRIBUTE_SPEC_COC_CONCAT				= "SPEC_COC_CONCAT";
	public String	ATTRIBUTE_SPEC_COA_CONCAT				= "SPEC_COA_CONCAT";
	public String	ATTRIBUTE_SHIP_TO_COMPANY_ID			= "SHIP_TO_COMPANY_ID";
	public String	ATTRIBUTE_REMAINING_SHELF_LIFE_PERCENT	= "REMAINING_SHELF_LIFE_PERCENT";
	public String	ATTRIBUTE_PROMISED_DATE					= "PROMISED_DATE";
	public String	ATTRIBUTE_CMS							= "CMS";
	public String	ATTRIBUTE_DISTRIBUTION					= "DISTRIBUTION";
	public String	ATTRIBUTE_SHIPPING_REFERENCE			= "SHIPPING_REFERENCE";
	public String	ATTRIBUTE_CUSTOMER_SERVICE_REP_NAME		= "CUSTOMER_SERVICE_REP_NAME";
	public String	ATTRIBUTE_RELEASE_STATUS				= "RELEASE_STATUS";
	public String	ATTRIBUTE_DIST_CUSTOMER_PART_LIST		= "DIST_CUSTOMER_PART_LIST";
	public String	ATTRIBUTE_CATALOG_PRICE					= "CATALOG_PRICE";

	// table name
	public String	TABLE									= "PR_OPEN_ORDER";

	// constructor
	public PrOpenOrderBeanFactory(DbManager dbManager) {
		super(dbManager);
	}

	// get column names
	@Override
	public String getColumnName(String attributeName) {
		if (attributeName.equals("supplier")) {
			return ATTRIBUTE_SUPPLIER;
		}
		else if (attributeName.equals("originalPromisedDate")) {
			return ATTRIBUTE_ORIGINAL_PROMISED_DATE;
		}
		else if (attributeName.equals("requestorLastName")) {
			return ATTRIBUTE_REQUESTOR_LAST_NAME;
		}
		else if (attributeName.equals("requestorFirstName")) {
			return ATTRIBUTE_REQUESTOR_FIRST_NAME;
		}
		else if (attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if (attributeName.equals("releaseDate")) {
			return ATTRIBUTE_RELEASE_DATE;
		}
		else if (attributeName.equals("requiredDatetime")) {
			return ATTRIBUTE_REQUIRED_DATETIME;
		}
		else if (attributeName.equals("systemRequiredDatetime")) {
			return ATTRIBUTE_SYSTEM_REQUIRED_DATETIME;
		}
		else if (attributeName.equals("delay")) {
			return ATTRIBUTE_DELAY;
		}
		else if (attributeName.equals("prNumber")) {
			return ATTRIBUTE_PR_NUMBER;
		}
		else if (attributeName.equals("lineItem")) {
			return ATTRIBUTE_LINE_ITEM;
		}
		else if (attributeName.equals("openQuantity")) {
			return ATTRIBUTE_OPEN_QUANTITY;
		}
		else if (attributeName.equals("allocationType")) {
			return ATTRIBUTE_ALLOCATION_TYPE;
		}
		else if (attributeName.equals("refNo")) {
			return ATTRIBUTE_REF_NO;
		}
		else if (attributeName.equals("refLine")) {
			return ATTRIBUTE_REF_LINE;
		}
		else if (attributeName.equals("allocQuantity")) {
			return ATTRIBUTE_ALLOC_QUANTITY;
		}
		else if (attributeName.equals("refStatus")) {
			return ATTRIBUTE_REF_STATUS;
		}
		else if (attributeName.equals("progressStatus")) {
			return ATTRIBUTE_PROGRESS_STATUS;
		}
		else if (attributeName.equals("refDate")) {
			return ATTRIBUTE_REF_DATE;
		}
		else if (attributeName.equals("facPartNo")) {
			return ATTRIBUTE_FAC_PART_NO;
		}
		else if (attributeName.equals("sourceHub")) {
			return ATTRIBUTE_SOURCE_HUB;
		}
		else if (attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if (attributeName.equals("requestor")) {
			return ATTRIBUTE_REQUESTOR;
		}
		else if (attributeName.equals("itemType")) {
			return ATTRIBUTE_ITEM_TYPE;
		}
		else if (attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if (attributeName.equals("hazmatIdMissing")) {
			return ATTRIBUTE_HAZMAT_ID_MISSING;
		}
		else if (attributeName.equals("qtyOnHand")) {
			return ATTRIBUTE_QTY_ON_HAND;
		}
		else if (attributeName.equals("qtyAvailable")) {
			return ATTRIBUTE_QTY_AVAILABLE;
		}
		else if (attributeName.equals("igQtyOnHand")) {
			return ATTRIBUTE_IG_QTY_ON_HAND;
		}
		else if (attributeName.equals("igQtyAvailable")) {
			return ATTRIBUTE_IG_QTY_AVAILABLE;
		}
		else if (attributeName.equals("requiredDatetimeSort")) {
			return ATTRIBUTE_REQUIRED_DATETIME_SORT;
		}
		else if (attributeName.equals("notes")) {
			return ATTRIBUTE_NOTES;
		}
		else if (attributeName.equals("mrNotes")) {
			return ATTRIBUTE_MR_NOTES;
		}
		else if (attributeName.equals("critical")) {
			return ATTRIBUTE_CRITICAL;
		}
		else if (attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if (attributeName.equals("pickable")) {
			return ATTRIBUTE_PICKABLE;
		}
		else if (attributeName.equals("mfgLot")) {
			return ATTRIBUTE_MFG_LOT;
		}
		else if (attributeName.equals("application")) {
			return ATTRIBUTE_APPLICATION;
		}
		else if (attributeName.equals("applicationDesc")) {
			return ATTRIBUTE_APPLICATION_DESC;
		}
		else if (attributeName.equals("lotStatusAge")) {
			return ATTRIBUTE_LOT_STATUS_AGE;
		}
		else if (attributeName.equals("orderQuantity")) {
			return ATTRIBUTE_ORDER_QUANTITY;
		}
		else if (attributeName.equals("partDescription")) {
			return ATTRIBUTE_PART_DESCRIPTION;
		}
		else if (attributeName.equals("deliveryType")) {
			return ATTRIBUTE_DELIVERY_TYPE;
		}
		else if (attributeName.equals("lookAheadDays")) {
			return ATTRIBUTE_LOOK_AHEAD_DAYS;
		}
		else if (attributeName.equals("requiredDatetimeType")) {
			return ATTRIBUTE_REQUIRED_DATETIME_TYPE;
		}
		else if (attributeName.equals("requestLineStatus")) {
			return ATTRIBUTE_REQUEST_LINE_STATUS;
		}
		else if (attributeName.equals("cancelStatus")) {
			return ATTRIBUTE_CANCEL_STATUS;
		}
		else if (attributeName.equals("ownerInventoryGroup")) {
			return ATTRIBUTE_OWNER_INVENTORY_GROUP;
		}
		else if (attributeName.equals("oconus")) {
			return ATTRIBUTE_OCONUS;
		}
		else if (attributeName.equals("fobHub")) {
			return ATTRIBUTE_FOB_HUB;
		}
		else if (attributeName.equals("globalCatalog")) {
			return ATTRIBUTE_GLOBAL_CATALOG;
		}
		else if (attributeName.equals("alternateName")) {
			return ATTRIBUTE_ALTERNATE_NAME;
		}
		else if (attributeName.equals("catalogItemDescription")) {
			return ATTRIBUTE_CATALOG_ITEM_DESCRIPTION;
		}
		else if (attributeName.equals("customerId")) {
			return ATTRIBUTE_CUSTOMER_ID;
		}
		else if (attributeName.equals("customerName")) {
			return ATTRIBUTE_CUSTOMER_NAME;
		}
		else if (attributeName.equals("facilityName")) {
			return ATTRIBUTE_FACILITY_NAME;
		}
		else if (attributeName.equals("inventoryGroupName")) {
			return ATTRIBUTE_INVENTORY_GROUP_NAME;
		}
		else if (attributeName.equals("ownerInventoryGroupName")) {
			return ATTRIBUTE_OWNER_INVENTORY_GROUP_NAME;
		}
		else if (attributeName.equals("materialRequestOrigin")) {
			return ATTRIBUTE_MATERIAL_REQUEST_ORIGIN;
		}
		else if (attributeName.equals("shipToLocationId")) {
			return ATTRIBUTE_SHIP_TO_LOCATION_ID;
		}
		else if (attributeName.equals("priceGroupId")) {
			return ATTRIBUTE_PRICE_GROUP_ID;
		}
		else if (attributeName.equals("billToCompanyId")) {
			return ATTRIBUTE_BILL_TO_COMPANY_ID;
		}
		else if (attributeName.equals("billToLocationId")) {
			return ATTRIBUTE_BILL_TO_LOCATION_ID;
		}
		else if (attributeName.equals("incoTerms")) {
			return ATTRIBUTE_INCO_TERMS;
		}
		else if (attributeName.equals("unitOfSale")) {
			return ATTRIBUTE_UNIT_OF_SALE;
		}
		else if (attributeName.equals("shipComplete")) {
			return ATTRIBUTE_SHIP_COMPLETE;
		}
		else if (attributeName.equals("consolidateShipment")) {
			return ATTRIBUTE_CONSOLIDATE_SHIPMENT;
		}
		else if (attributeName.equals("currencyId")) {
			return ATTRIBUTE_CURRENCY_ID;
		}
		else if (attributeName.equals("opsEntityId")) {
			return ATTRIBUTE_OPS_ENTITY_ID;
		}
		else if (attributeName.equals("opsCompanyId")) {
			return ATTRIBUTE_OPS_COMPANY_ID;
		}
		else if (attributeName.equals("labelSpec")) {
			return ATTRIBUTE_LABEL_SPEC;
		}
		else if (attributeName.equals("specList")) {
			return ATTRIBUTE_SPEC_LIST;
		}
		else if (attributeName.equals("specListConcat")) {
			return ATTRIBUTE_SPEC_LIST_CONCAT;
		}
		else if (attributeName.equals("specLibraryConcat")) {
			return ATTRIBUTE_SPEC_LIBRARY_CONCAT;
		}
		else if (attributeName.equals("specDetailConcat")) {
			return ATTRIBUTE_SPEC_DETAIL_CONCAT;
		}
		else if (attributeName.equals("specCocConcat")) {
			return ATTRIBUTE_SPEC_COC_CONCAT;
		}
		else if (attributeName.equals("specCoaConcat")) {
			return ATTRIBUTE_SPEC_COA_CONCAT;
		}
		else if (attributeName.equals("shipToCompanyId")) {
			return ATTRIBUTE_SHIP_TO_COMPANY_ID;
		}
		else if (attributeName.equals("remainingShelfLifePercent")) {
			return ATTRIBUTE_REMAINING_SHELF_LIFE_PERCENT;
		}
		else if (attributeName.equals("promisedDate")) {
			return ATTRIBUTE_PROMISED_DATE;
		}
		else if (attributeName.equals("cms")) {
			return ATTRIBUTE_CMS;
		}
		else if (attributeName.equals("distribution")) {
			return ATTRIBUTE_DISTRIBUTION;
		}
		else if (attributeName.equals("shippingReference")) {
			return ATTRIBUTE_SHIPPING_REFERENCE;
		}
		else if (attributeName.equals("customerServiceRepName")) {
			return ATTRIBUTE_CUSTOMER_SERVICE_REP_NAME;
		}
		else if (attributeName.equals("releaseStatus")) {
			return ATTRIBUTE_RELEASE_STATUS;
		}
		else if (attributeName.equals("distCustomerPartList")) {
			return ATTRIBUTE_DIST_CUSTOMER_PART_LIST;
		}
		else if (attributeName.equals("catalogPrice")) {
			return ATTRIBUTE_CATALOG_PRICE;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}

	// get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, PrOpenOrderBean.class);
	}

	// select
	public Collection<PrOpenOrderBean> select(AllocationAnalysisInputBean input) throws BaseException {
		Connection connection = null;
		Collection<PrOpenOrderBean> c = null;
		try {
			connection = getDbManager().getConnection();
			c = select(input, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}

	@SuppressWarnings("unchecked")
	public Collection<PrOpenOrderBean> select(AllocationAnalysisInputBean input, Connection conn) throws BaseException {
		Collection<PrOpenOrderBean> prOpenOrderBeanColl = new Vector<PrOpenOrderBean>();

		Collection inArgs = new Vector(10);
		inArgs.add(input.hasHub() ? input.getHub() : "");
		inArgs.add(input.hasLotStatus() ? input.getLotStatus() : "");
		inArgs.add(input.hasNeededDaySpan() ? input.getDaySpan() : "");
		inArgs.add(input.hasMr() ? input.getItemOrMr() : "");
		inArgs.add(input.hasProgressStatus() ? input.getProgressStatus() : "");
		inArgs.add(input.hasItemId() ? input.getItemOrMr() : "");
		inArgs.add(input.hasOnTimeDaySpan() ? input.getDaySpan() : "");
		inArgs.add(input.hasInventoryGroup() ? input.getInventoryGroup() : "");
		inArgs.add(input.hasFacilityId() ? input.getFacilityId() : "");
		inArgs.add(input.hasSortBy() ? input.getSortBy() : "");
		inArgs.add(input.hasCustomerId() ? input.getCustomerId() : "");
		inArgs.add(input.hasCsrPersonnelId() ? input.getCsrPersonnelId() : "");
		inArgs.add(input.hasSearchTypeNonScheduled() ? input.getSearchTypeNonScheduled() : "N");
		inArgs.add(input.hasSearchTypeScheduled() ? input.getSearchTypeScheduled() : "N");
		inArgs.add(input.hasSearchTypeTransfer() ? input.getSearchTypeTransfer() : "N");

		Collection outArgs = new Vector(6);
		/*
		 * outArgs.add( new Integer(oracle.jdbc.driver.OracleTypes.CURSOR) );
		 * outArgs.add(new Integer(java.sql.Types.VARCHAR)); outArgs.add(new
		 * Integer(java.sql.Types.VARCHAR));
		 */
		outArgs.add(new Integer(java.sql.Types.VARCHAR));
		outArgs.add(new Integer(java.sql.Types.VARCHAR));
		outArgs.add(new Integer(java.sql.Types.VARCHAR));
		GenericProcedureFactory procFactory = new GenericProcedureFactory(this.getDbManager());

		// log.debug("calling pkg_open_order.pr_open_order  "+inArgs);
		Vector<String> result = (Vector<String>) procFactory.doProcedure(conn, "pkg_open_order.pr_open_order", inArgs, outArgs);

		StringBuilder queryBuilder = new StringBuilder("");
		for (String curQuery : result)
			if (curQuery != null)
				queryBuilder.append(curQuery);
		String query = queryBuilder.toString();

		if (query.length() > 0) {
			DataSet dataSet = new SqlManager().select(conn, query);
			Iterator<DataSetRow> dataIter = dataSet.iterator();
			while (dataIter.hasNext()) {
				DataSetRow dataSetRow = dataIter.next();
				PrOpenOrderBean prOpenOrderBean = new PrOpenOrderBean();
				load(dataSetRow, prOpenOrderBean);
				prOpenOrderBeanColl.add(prOpenOrderBean);
			}
		}

		return prOpenOrderBeanColl;
	}
}