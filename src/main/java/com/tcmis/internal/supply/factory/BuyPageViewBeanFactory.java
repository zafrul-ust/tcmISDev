package com.tcmis.internal.supply.factory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.supply.beans.BuyOrdersInputBean;
import com.tcmis.internal.supply.beans.BuyPageViewBean;

/******************************************************************************
 * CLASSNAME: BuyPageViewBeanFactory <br>
 * @version: 1.0, Mar 15, 2007 <br>
 *****************************************************************************/

public class BuyPageViewBeanFactory
extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_FROZEN = "FROZEN";
	public String ATTRIBUTE_CRITICAL = "CRITICAL";
	public String ATTRIBUTE_NOTES = "NOTES";
	public String ATTRIBUTE_CANCEL_STATUS = "CANCEL_STATUS";
	public String ATTRIBUTE_REQUEST_LINE_STATUS = "REQUEST_LINE_STATUS";
	public String ATTRIBUTE_CATALOG_PRICE = "CATALOG_PRICE";
	public String ATTRIBUTE_BASELINE_PRICE = "BASELINE_PRICE";
	public String ATTRIBUTE_MR_QUANTITY = "MR_QUANTITY";
	public String ATTRIBUTE_REQUESTOR_FIRST_NAME = "REQUESTOR_FIRST_NAME";
	public String ATTRIBUTE_REQUESTOR_LAST_NAME = "REQUESTOR_LAST_NAME";
	public String ATTRIBUTE_EMAIL = "EMAIL";
	public String ATTRIBUTE_PHONE = "PHONE";
	public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
	public String ATTRIBUTE_BUYER = "BUYER";
	public String ATTRIBUTE_DATE_ASSIGNED = "DATE_ASSIGNED";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_NEED_DATE = "NEED_DATE";
	public String ATTRIBUTE_PART_ID = "PART_ID";
	public String ATTRIBUTE_ITEM_DESC = "ITEM_DESC";
	public String ATTRIBUTE_SHELF_LIFE_DAYS = "SHELF_LIFE_DAYS";
	public String ATTRIBUTE_TRADE_NAME = "TRADE_NAME";
	public String ATTRIBUTE_SIZE_UNIT = "SIZE_UNIT";
	public String ATTRIBUTE_MFG_ID = "MFG_ID";
	public String ATTRIBUTE_MFG_PART_NO = "MFG_PART_NO";
	public String ATTRIBUTE_ITEM_TYPE = "ITEM_TYPE";
	public String ATTRIBUTE_ORDER_QUANTITY = "ORDER_QUANTITY";
	public String ATTRIBUTE_UOM = "UOM";
	public String ATTRIBUTE_PRIORITY = "PRIORITY";
	public String ATTRIBUTE_RADIAN_PO = "RADIAN_PO";
	public String ATTRIBUTE_FACILITY = "FACILITY";
	public String ATTRIBUTE_RAYTHEON_PO = "RAYTHEON_PO";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_BRANCH_PLANT = "BRANCH_PLANT";
	public String ATTRIBUTE_HOME_CURRENCY_ID = "HOME_CURRENCY_ID";
	public String ATTRIBUTE_DATE_ISSUED = "DATE_ISSUED";
	public String ATTRIBUTE_DATE_PO_CREATED = "DATE_PO_CREATED";
	public String ATTRIBUTE_STATUS = "STATUS";
	public String ATTRIBUTE_DATE_CHANGED = "DATE_CHANGED";
	public String ATTRIBUTE_COMMENTS = "COMMENTS";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_MR_NUMBER = "MR_NUMBER";
	public String ATTRIBUTE_MR_LINE_ITEM = "MR_LINE_ITEM";
	public String ATTRIBUTE_REORDER_POINT = "REORDER_POINT";
	public String ATTRIBUTE_STOCKING_LEVEL = "STOCKING_LEVEL";
	public String ATTRIBUTE_AVAILABLE_QUANTITY = "AVAILABLE_QUANTITY";
	public String ATTRIBUTE_OPEN_PO_QUANTITY = "OPEN_PO_QUANTITY";
	public String ATTRIBUTE_SHIP_TO_DELIVERY_POINT = "SHIP_TO_DELIVERY_POINT";
	public String ATTRIBUTE_SHIP_TO_LOCATION_ID = "SHIP_TO_LOCATION_ID";
	public String ATTRIBUTE_SHIP_TO_COMPANY_ID = "SHIP_TO_COMPANY_ID";
	public String ATTRIBUTE_BUYER_ID = "BUYER_ID";
	public String ATTRIBUTE_LAST_SUPPLIER = "LAST_SUPPLIER";
	public String ATTRIBUTE_BUYER_NAME = "BUYER_NAME";
	public String ATTRIBUTE_EVER_CONFIRMED = "EVER_CONFIRMED";
	public String ATTRIBUTE_UNCONFIRMED = "UNCONFIRMED";
	public String ATTRIBUTE_DELIVERY_TYPE = "DELIVERY_TYPE";
	public String ATTRIBUTE_PO_IN_JDE = "PO_IN_JDE";
	public String ATTRIBUTE_LPP = "LPP";
	public String ATTRIBUTE_ENGINEERING_EVALUATION = "ENGINEERING_EVALUATION";
	public String ATTRIBUTE_REQUEST_ID = "REQUEST_ID";
	public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
	public String ATTRIBUTE_STOCKED = "STOCKED";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_CUSTOMER_PO_NUMBER = "CUSTOMER_PO_NUMBER";
	public String ATTRIBUTE_RELEASE_NUMBER = "RELEASE_NUMBER";
	public String ATTRIBUTE_BPO_DETAIL = "BPO_DETAIL";
	public String ATTRIBUTE_BPO = "BPO";
	public String ATTRIBUTE_SUPPLY_PATH = "SUPPLY_PATH";
	public String ATTRIBUTE_DELIVERY_POINT_DESC = "DELIVERY_POINT_DESC";
	public String ATTRIBUTE_CONSOLIDATION_ALLOWED = "CONSOLIDATION_ALLOWED";
	public String ATTRIBUTE_PURCHASING_UNITS_PER_ITEM =
		"PURCHASING_UNITS_PER_ITEM";
	public String ATTRIBUTE_PURCHASING_UNIT_OF_MEASURE =
		"PURCHASING_UNIT_OF_MEASURE";
	public String ATTRIBUTE_BUYER_COMPANY_ID = "BUYER_COMPANY_ID";
	public String ATTRIBUTE_SURPLUS_INVENTORY = "SURPLUS_INVENTORY";
	public String ATTRIBUTE_BUYPAGE_ASSIGNABLE = "BUYPAGE_ASSIGNABLE";
	public String ATTRIBUTE_LOCK_STATUS = "LOCK_STATUS";
	public String ATTRIBUTE_LAST_SUPPLIER_NAME = "LAST_SUPPLIER_NAME";
	public String ATTRIBUTE_PREFERRED_SUPPLIER = "PREFERRED_SUPPLIER";
	public String ATTRIBUTE_PREFERRED_SUPPLIER_NAME = "PREFERRED_SUPPLIER_NAME";
	public String ATTRIBUTE_SELECTED_SUPPLIER = "SELECTED_SUPPLIER";
	public String ATTRIBUTE_SELECTED_SUPPLIER_NAME = "SELECTED_SUPPLIER_NAME";
	public String ATTRIBUTE_CURRENT_SUPPLIER = "CURRENT_SUPPLIER";
	public String ATTRIBUTE_CURRENT_SUPPLIER_NAME = "CURRENT_SUPPLIER_NAME";
	public String ATTRIBUTE_OPS_ENTITY_ID = "OPS_ENTITY_ID";
	public String ATTRIBUTE_INVENTORY_GROUP_NAME = "INVENTORY_GROUP_NAME";
	public String ATTRIBUTE_SHIPTO_NOTE = "SHIPTO_NOTE";
	public String ATTRIBUTE_PR_INTERNAL_NOTE = "PR_INTERNAL_NOTE";
	public String ATTRIBUTE_LINE_INTERNAL_NOTE = "LINE_INTERNAL_NOTE";
	public String ATTRIBUTE_LINE_PURCHASING_NOTE = "LINE_PURCHASING_NOTE";
	public String ATTRIBUTE_SPEC_LIST = "SPEC_LIST";
	public String ATTRIBUTE_CSR_NAME = "CSR_NAME";
	public String ATTRIBUTE_SHIP_TO_LOCATION_DESC = "SHIP_TO_LOCATION_DESC";
	public String ATTRIBUTE_PROMISE_DATE = "PROMISE_DATE";
	public String ATTRIBUTE_ITEM_ITEM_TYPE = "ITEM_ITEM_TYPE";
	public String ATTRIBUTE_RELEASE_DATE = "RELEASE_DATE";
	public String ATTRIBUTE_REACH_ANNEX_XIV = "REACH_ANNEX_XIV";

	public String ATTRIBUTE_LAST_UPDATED_BY = "LAST_UPDATED_BY";
	public String ATTRIBUTE_LAST_UPDATED_DATE = "LAST_UPDATED_DATE";
	public String ATTRIBUTE_ACTIVE_DBUY_COUNT = "ACTIVE_DBUY_COUNT";
	public String ATTRIBUTE_BUY_TYPE_FLAG = "BUY_TYPE_FLAG";
	public String ATTRIBUTE_BUY_TYPE = "BUY_TYPE";

	//table name
	public String TABLE = "BUY_PAGE_VIEW";

	//constructor
	public BuyPageViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}

	//get column names
	@Override
	public String getColumnName(String attributeName) {
		if (attributeName.equals("frozen")) {
			return ATTRIBUTE_FROZEN;
		}
		else if (attributeName.equals("critical")) {
			return ATTRIBUTE_CRITICAL;
		}
		else if (attributeName.equals("notes")) {
			return ATTRIBUTE_NOTES;
		}
		else if (attributeName.equals("cancelStatus")) {
			return ATTRIBUTE_CANCEL_STATUS;
		}
		else if (attributeName.equals("requestLineStatus")) {
			return ATTRIBUTE_REQUEST_LINE_STATUS;
		}
		else if (attributeName.equals("catalogPrice")) {
			return ATTRIBUTE_CATALOG_PRICE;
		}
		else if (attributeName.equals("baselinePrice")) {
			return ATTRIBUTE_BASELINE_PRICE;
		}
		else if (attributeName.equals("mrQuantity")) {
			return ATTRIBUTE_MR_QUANTITY;
		}
		else if (attributeName.equals("requestorFirstName")) {
			return ATTRIBUTE_REQUESTOR_FIRST_NAME;
		}
		else if (attributeName.equals("requestorLastName")) {
			return ATTRIBUTE_REQUESTOR_LAST_NAME;
		}
		else if (attributeName.equals("email")) {
			return ATTRIBUTE_EMAIL;
		}
		else if (attributeName.equals("phone")) {
			return ATTRIBUTE_PHONE;
		}
		else if (attributeName.equals("prNumber")) {
			return ATTRIBUTE_PR_NUMBER;
		}
		else if (attributeName.equals("buyer")) {
			return ATTRIBUTE_BUYER;
		}
		else if (attributeName.equals("dateAssigned")) {
			return ATTRIBUTE_DATE_ASSIGNED;
		}
		else if (attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if (attributeName.equals("needDate")) {
			return ATTRIBUTE_NEED_DATE;
		}
		else if (attributeName.equals("partId")) {
			return ATTRIBUTE_PART_ID;
		}
		else if (attributeName.equals("itemDesc")) {
			return ATTRIBUTE_ITEM_DESC;
		}
		else if (attributeName.equals("shelfLifeDays")) {
			return ATTRIBUTE_SHELF_LIFE_DAYS;
		}
		else if (attributeName.equals("tradeName")) {
			return ATTRIBUTE_TRADE_NAME;
		}
		else if (attributeName.equals("sizeUnit")) {
			return ATTRIBUTE_SIZE_UNIT;
		}
		else if (attributeName.equals("mfgId")) {
			return ATTRIBUTE_MFG_ID;
		}
		else if (attributeName.equals("mfgPartNo")) {
			return ATTRIBUTE_MFG_PART_NO;
		}
		else if (attributeName.equals("itemType")) {
			return ATTRIBUTE_ITEM_TYPE;
		}
		else if (attributeName.equals("orderQuantity")) {
			return ATTRIBUTE_ORDER_QUANTITY;
		}
		else if (attributeName.equals("uom")) {
			return ATTRIBUTE_UOM;
		}
		else if (attributeName.equals("priority")) {
			return ATTRIBUTE_PRIORITY;
		}
		else if (attributeName.equals("radianPo")) {
			return ATTRIBUTE_RADIAN_PO;
		}
		else if (attributeName.equals("facility")) {
			return ATTRIBUTE_FACILITY;
		}
		else if (attributeName.equals("raytheonPo")) {
			return ATTRIBUTE_RAYTHEON_PO;
		}
		else if (attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if (attributeName.equals("branchPlant")) {
			return ATTRIBUTE_BRANCH_PLANT;
		}
		else if (attributeName.equals("homeCurrencyId")) {
			return ATTRIBUTE_HOME_CURRENCY_ID;
		}
		else if (attributeName.equals("dateIssued")) {
			return ATTRIBUTE_DATE_ISSUED;
		}
		else if (attributeName.equals("datePoCreated")) {
			return ATTRIBUTE_DATE_PO_CREATED;
		}
		else if (attributeName.equals("status")) {
			return ATTRIBUTE_STATUS;
		}
		else if (attributeName.equals("dateChanged")) {
			return ATTRIBUTE_DATE_CHANGED;
		}
		else if (attributeName.equals("comments")) {
			return ATTRIBUTE_COMMENTS;
		}
		else if (attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if (attributeName.equals("mrNumber")) {
			return ATTRIBUTE_MR_NUMBER;
		}
		else if (attributeName.equals("mrLineItem")) {
			return ATTRIBUTE_MR_LINE_ITEM;
		}
		else if (attributeName.equals("reorderPoint")) {
			return ATTRIBUTE_REORDER_POINT;
		}
		else if (attributeName.equals("stockingLevel")) {
			return ATTRIBUTE_STOCKING_LEVEL;
		}
		else if (attributeName.equals("availableQuantity")) {
			return ATTRIBUTE_AVAILABLE_QUANTITY;
		}
		else if (attributeName.equals("openPoQuantity")) {
			return ATTRIBUTE_OPEN_PO_QUANTITY;
		}
		else if (attributeName.equals("shipToDeliveryPoint")) {
			return ATTRIBUTE_SHIP_TO_DELIVERY_POINT;
		}
		else if (attributeName.equals("shipToLocationId")) {
			return ATTRIBUTE_SHIP_TO_LOCATION_ID;
		}
		else if (attributeName.equals("shipToCompanyId")) {
			return ATTRIBUTE_SHIP_TO_COMPANY_ID;
		}
		else if (attributeName.equals("buyerId")) {
			return ATTRIBUTE_BUYER_ID;
		}
		else if (attributeName.equals("lastSupplier")) {
			return ATTRIBUTE_LAST_SUPPLIER;
		}
		else if (attributeName.equals("buyerName")) {
			return ATTRIBUTE_BUYER_NAME;
		}
		else if (attributeName.equals("everConfirmed")) {
			return ATTRIBUTE_EVER_CONFIRMED;
		}
		else if (attributeName.equals("unconfirmed")) {
			return ATTRIBUTE_UNCONFIRMED;
		}
		else if (attributeName.equals("deliveryType")) {
			return ATTRIBUTE_DELIVERY_TYPE;
		}
		else if (attributeName.equals("poInJde")) {
			return ATTRIBUTE_PO_IN_JDE;
		}
		else if (attributeName.equals("lpp")) {
			return ATTRIBUTE_LPP;
		}
		else if (attributeName.equals("engineeringEvaluation")) {
			return ATTRIBUTE_ENGINEERING_EVALUATION;
		}
		else if (attributeName.equals("requestId")) {
			return ATTRIBUTE_REQUEST_ID;
		}
		else if (attributeName.equals("catalogId")) {
			return ATTRIBUTE_CATALOG_ID;
		}
		else if (attributeName.equals("stocked")) {
			return ATTRIBUTE_STOCKED;
		}
		else if (attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if (attributeName.equals("customerPoNumber")) {
			return ATTRIBUTE_CUSTOMER_PO_NUMBER;
		}
		else if (attributeName.equals("releaseNumber")) {
			return ATTRIBUTE_RELEASE_NUMBER;
		}
		else if (attributeName.equals("bpoDetail")) {
			return ATTRIBUTE_BPO_DETAIL;
		}
		else if (attributeName.equals("bpo")) {
			return ATTRIBUTE_BPO;
		}
		else if (attributeName.equals("supplyPath")) {
			return ATTRIBUTE_SUPPLY_PATH;
		}
		else if (attributeName.equals("deliveryPointDesc")) {
			return ATTRIBUTE_DELIVERY_POINT_DESC;
		}
		else if (attributeName.equals("consolidationAllowed")) {
			return ATTRIBUTE_CONSOLIDATION_ALLOWED;
		}
		else if (attributeName.equals("purchasingUnitsPerItem")) {
			return ATTRIBUTE_PURCHASING_UNITS_PER_ITEM;
		}
		else if (attributeName.equals("purchasingUnitOfMeasure")) {
			return ATTRIBUTE_PURCHASING_UNIT_OF_MEASURE;
		}
		else if (attributeName.equals("buyerCompanyId")) {
			return ATTRIBUTE_BUYER_COMPANY_ID;
		}
		else if (attributeName.equals("surplusInventory")) {
			return ATTRIBUTE_SURPLUS_INVENTORY;
		}
		else if (attributeName.equals("buypageAssignable")) {
			return ATTRIBUTE_BUYPAGE_ASSIGNABLE;
		}
		else if (attributeName.equals("lockStatus")) {
			return ATTRIBUTE_LOCK_STATUS;
		}
		else if (attributeName.equals("lastSupplierName")) {
			return ATTRIBUTE_LAST_SUPPLIER_NAME;
		}
		else if (attributeName.equals("preferredSupplier")) {
			return ATTRIBUTE_PREFERRED_SUPPLIER;
		}
		else if (attributeName.equals("preferredSupplierName")) {
			return ATTRIBUTE_PREFERRED_SUPPLIER_NAME;
		}
		else if (attributeName.equals("selectedSupplier")) {
			return ATTRIBUTE_SELECTED_SUPPLIER;
		}
		else if (attributeName.equals("selectedSupplierName")) {
			return ATTRIBUTE_SELECTED_SUPPLIER_NAME;
		}
		else if (attributeName.equals("currentSupplier")) {
			return ATTRIBUTE_CURRENT_SUPPLIER;
		}
		else if (attributeName.equals("currentSupplierName")) {
			return ATTRIBUTE_CURRENT_SUPPLIER_NAME;
		}
		else if(attributeName.equals("opsEntityId")) {
			return ATTRIBUTE_OPS_ENTITY_ID;
		}
		else if(attributeName.equals("inventoryGroupName")) {
			return ATTRIBUTE_INVENTORY_GROUP_NAME;
		}
		else if(attributeName.equals("shiptoNote")) {
			return ATTRIBUTE_SHIPTO_NOTE;
		}
		else if(attributeName.equals("prInternalNote")) {
			return ATTRIBUTE_PR_INTERNAL_NOTE;
		}
		else if(attributeName.equals("lineInternalNote")) {
			return ATTRIBUTE_LINE_INTERNAL_NOTE;
		}
		else if(attributeName.equals("linePurchasingNote")) {
			return ATTRIBUTE_LINE_PURCHASING_NOTE;
		}
		else if(attributeName.equals("specList")) {
			return ATTRIBUTE_SPEC_LIST;
		}
		else if(attributeName.equals("csrName")) {
			return ATTRIBUTE_CSR_NAME;
		}
		else if(attributeName.equals("shipToLocationDesc")) {
			return ATTRIBUTE_SHIP_TO_LOCATION_DESC;
		}
		else if(attributeName.equals("promiseDate")) {
			return ATTRIBUTE_PROMISE_DATE;
		}
		else if(attributeName.equals("itemItemType")) {
			return ATTRIBUTE_ITEM_ITEM_TYPE;
		}
		else if(attributeName.equals("releaseDate")) {
			return ATTRIBUTE_RELEASE_DATE;
		}
		else if(attributeName.equals("reachAnnexXiv")) {
			return ATTRIBUTE_REACH_ANNEX_XIV;
		}
		else if (attributeName.equals("lastUpdatedBy")) {
			return ATTRIBUTE_LAST_UPDATED_BY;
		}
		else if (attributeName.equals("lastUpdatedDate")) {
			return ATTRIBUTE_LAST_UPDATED_DATE;
		}
		else if (attributeName.equals("activeDbuyCount")) {
			return ATTRIBUTE_ACTIVE_DBUY_COUNT;
		}	
		else if(attributeName.equals("buyTypeFlag")) {
			return ATTRIBUTE_BUY_TYPE_FLAG;
		}
		else if(attributeName.equals("buyType")) {
			return ATTRIBUTE_BUY_TYPE;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}

	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, BuyPageViewBean.class);
	}

	//you need to verify the primary key(s) before uncommenting this
	/*
//delete
   public int delete(BuyPageViewBean buyPageViewBean)
    throws BaseException {
       SearchCriteria criteria = new SearchCriteria("frozen", "SearchCriterion.EQUALS",
     "" + buyPageViewBean.getFrozen());
    Connection connection = null;
    int result = 0;
    try {
     connection = this.getDbManager().getConnection();
     result = this.delete(criteria, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int delete(BuyPageViewBean buyPageViewBean, Connection conn)
    throws BaseException {
       SearchCriteria criteria = new SearchCriteria("frozen", "SearchCriterion.EQUALS",
     "" + buyPageViewBean.getFrozen());
    return delete(criteria, conn);
   }
	 */

	public int delete(SearchCriteria criteria) throws BaseException {
		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = delete(criteria, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}

	public int delete(SearchCriteria criteria, Connection conn) throws
	BaseException {
		String sqlQuery = " delete from " + TABLE + " " +
		getWhereClause(criteria);
		return new SqlManager().update(conn, sqlQuery);
	}

	//you need to verify the primary key(s) before uncommenting this
	/*
//insert
   public int insert(BuyPageViewBean buyPageViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(buyPageViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(BuyPageViewBean buyPageViewBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_FROZEN + "," +
     ATTRIBUTE_CRITICAL + "," +
     ATTRIBUTE_NOTES + "," +
     ATTRIBUTE_CANCEL_STATUS + "," +
     ATTRIBUTE_REQUEST_LINE_STATUS + "," +
     ATTRIBUTE_CATALOG_PRICE + "," +
     ATTRIBUTE_BASELINE_PRICE + "," +
     ATTRIBUTE_MR_QUANTITY + "," +
     ATTRIBUTE_REQUESTOR_FIRST_NAME + "," +
     ATTRIBUTE_REQUESTOR_LAST_NAME + "," +
     ATTRIBUTE_EMAIL + "," +
     ATTRIBUTE_PHONE + "," +
     ATTRIBUTE_PR_NUMBER + "," +
     ATTRIBUTE_BUYER + "," +
     ATTRIBUTE_DATE_ASSIGNED + "," +
     ATTRIBUTE_ITEM_ID + "," +
     ATTRIBUTE_NEED_DATE + "," +
     ATTRIBUTE_PART_ID + "," +
     ATTRIBUTE_ITEM_DESC + "," +
     ATTRIBUTE_SHELF_LIFE_DAYS + "," +
     ATTRIBUTE_TRADE_NAME + "," +
     ATTRIBUTE_SIZE_UNIT + "," +
     ATTRIBUTE_MFG_ID + "," +
     ATTRIBUTE_MFG_PART_NO + "," +
     ATTRIBUTE_ITEM_TYPE + "," +
     ATTRIBUTE_ORDER_QUANTITY + "," +
     ATTRIBUTE_UOM + "," +
     ATTRIBUTE_PRIORITY + "," +
     ATTRIBUTE_RADIAN_PO + "," +
     ATTRIBUTE_FACILITY + "," +
     ATTRIBUTE_RAYTHEON_PO + "," +
     ATTRIBUTE_FACILITY_ID + "," +
     ATTRIBUTE_BRANCH_PLANT + "," +
     ATTRIBUTE_HOME_CURRENCY_ID + "," +
     ATTRIBUTE_DATE_ISSUED + "," +
     ATTRIBUTE_DATE_PO_CREATED + "," +
     ATTRIBUTE_STATUS + "," +
     ATTRIBUTE_DATE_CHANGED + "," +
     ATTRIBUTE_COMMENTS + "," +
     ATTRIBUTE_COMPANY_ID + "," +
     ATTRIBUTE_MR_NUMBER + "," +
     ATTRIBUTE_MR_LINE_ITEM + "," +
     ATTRIBUTE_REORDER_POINT + "," +
     ATTRIBUTE_STOCKING_LEVEL + "," +
     ATTRIBUTE_AVAILABLE_QUANTITY + "," +
     ATTRIBUTE_OPEN_PO_QUANTITY + "," +
     ATTRIBUTE_SHIP_TO_DELIVERY_POINT + "," +
     ATTRIBUTE_SHIP_TO_LOCATION_ID + "," +
     ATTRIBUTE_SHIP_TO_COMPANY_ID + "," +
     ATTRIBUTE_BUYER_ID + "," +
     ATTRIBUTE_LAST_SUPPLIER + "," +
     ATTRIBUTE_BUYER_NAME + "," +
     ATTRIBUTE_EVER_CONFIRMED + "," +
     ATTRIBUTE_UNCONFIRMED + "," +
     ATTRIBUTE_DELIVERY_TYPE + "," +
     ATTRIBUTE_PO_IN_JDE + "," +
     ATTRIBUTE_LPP + "," +
     ATTRIBUTE_ENGINEERING_EVALUATION + "," +
     ATTRIBUTE_REQUEST_ID + "," +
     ATTRIBUTE_CATALOG_ID + "," +
     ATTRIBUTE_STOCKED + "," +
     ATTRIBUTE_INVENTORY_GROUP + "," +
     ATTRIBUTE_CUSTOMER_PO_NUMBER + "," +
     ATTRIBUTE_RELEASE_NUMBER + "," +
     ATTRIBUTE_BPO_DETAIL + "," +
     ATTRIBUTE_BPO + "," +
     ATTRIBUTE_SUPPLY_PATH + "," +
     ATTRIBUTE_DELIVERY_POINT_DESC + "," +
     ATTRIBUTE_CONSOLIDATION_ALLOWED + "," +
     ATTRIBUTE_PURCHASING_UNITS_PER_ITEM + "," +
     ATTRIBUTE_PURCHASING_UNIT_OF_MEASURE + "," +
     ATTRIBUTE_BUYER_COMPANY_ID + "," +
     ATTRIBUTE_SURPLUS_INVENTORY + ")" +
     " values (" +
     SqlHandler.delimitString(buyPageViewBean.getFrozen()) + "," +
     SqlHandler.delimitString(buyPageViewBean.getCritical()) + "," +
     SqlHandler.delimitString(buyPageViewBean.getNotes()) + "," +
     SqlHandler.delimitString(buyPageViewBean.getCancelStatus()) + "," +
     SqlHandler.delimitString(buyPageViewBean.getRequestLineStatus()) + "," +
     buyPageViewBean.getCatalogPrice() + "," +
     buyPageViewBean.getBaselinePrice() + "," +
     buyPageViewBean.getMrQuantity() + "," +
     SqlHandler.delimitString(buyPageViewBean.getRequestorFirstName()) + "," +
     SqlHandler.delimitString(buyPageViewBean.getRequestorLastName()) + "," +
     SqlHandler.delimitString(buyPageViewBean.getEmail()) + "," +
     SqlHandler.delimitString(buyPageViewBean.getPhone()) + "," +
     buyPageViewBean.getPrNumber() + "," +
     SqlHandler.delimitString(buyPageViewBean.getBuyer()) + "," +
       DateHandler.getOracleToDateFunction(buyPageViewBean.getDateAssigned()) + "," +
     buyPageViewBean.getItemId() + "," +
     DateHandler.getOracleToDateFunction(buyPageViewBean.getNeedDate()) + "," +
     SqlHandler.delimitString(buyPageViewBean.getPartId()) + "," +
     SqlHandler.delimitString(buyPageViewBean.getItemDesc()) + "," +
     SqlHandler.delimitString(buyPageViewBean.getShelfLifeDays()) + "," +
     SqlHandler.delimitString(buyPageViewBean.getTradeName()) + "," +
     SqlHandler.delimitString(buyPageViewBean.getSizeUnit()) + "," +
     SqlHandler.delimitString(buyPageViewBean.getMfgId()) + "," +
     SqlHandler.delimitString(buyPageViewBean.getMfgPartNo()) + "," +
     SqlHandler.delimitString(buyPageViewBean.getItemType()) + "," +
     buyPageViewBean.getOrderQuantity() + "," +
     SqlHandler.delimitString(buyPageViewBean.getUom()) + "," +
     buyPageViewBean.getPriority() + "," +
     buyPageViewBean.getRadianPo() + "," +
     SqlHandler.delimitString(buyPageViewBean.getFacility()) + "," +
     SqlHandler.delimitString(buyPageViewBean.getRaytheonPo()) + "," +
     SqlHandler.delimitString(buyPageViewBean.getFacilityId()) + "," +
     SqlHandler.delimitString(buyPageViewBean.getBranchPlant()) + "," +
     SqlHandler.delimitString(buyPageViewBean.getHomeCurrencyId()) + "," +
       DateHandler.getOracleToDateFunction(buyPageViewBean.getDateIssued()) + "," +
       DateHandler.getOracleToDateFunction(buyPageViewBean.getDatePoCreated()) + "," +
     SqlHandler.delimitString(buyPageViewBean.getStatus()) + "," +
       DateHandler.getOracleToDateFunction(buyPageViewBean.getDateChanged()) + "," +
     SqlHandler.delimitString(buyPageViewBean.getComments()) + "," +
     SqlHandler.delimitString(buyPageViewBean.getCompanyId()) + "," +
     buyPageViewBean.getMrNumber() + "," +
     SqlHandler.delimitString(buyPageViewBean.getMrLineItem()) + "," +
     buyPageViewBean.getReorderPoint() + "," +
     buyPageViewBean.getStockingLevel() + "," +
     buyPageViewBean.getAvailableQuantity() + "," +
     buyPageViewBean.getOpenPoQuantity() + "," +
     SqlHandler.delimitString(buyPageViewBean.getShipToDeliveryPoint()) + "," +
     SqlHandler.delimitString(buyPageViewBean.getShipToLocationId()) + "," +
     SqlHandler.delimitString(buyPageViewBean.getShipToCompanyId()) + "," +
     buyPageViewBean.getBuyerId() + "," +
     SqlHandler.delimitString(buyPageViewBean.getLastSupplier()) + "," +
     SqlHandler.delimitString(buyPageViewBean.getBuyerName()) + "," +
     SqlHandler.delimitString(buyPageViewBean.getEverConfirmed()) + "," +
     SqlHandler.delimitString(buyPageViewBean.getUnconfirmed()) + "," +
     SqlHandler.delimitString(buyPageViewBean.getDeliveryType()) + "," +
     SqlHandler.delimitString(buyPageViewBean.getPoInJde()) + "," +
     SqlHandler.delimitString(buyPageViewBean.getLpp()) + "," +
       SqlHandler.delimitString(buyPageViewBean.getEngineeringEvaluation()) + "," +
     buyPageViewBean.getRequestId() + "," +
     SqlHandler.delimitString(buyPageViewBean.getCatalogId()) + "," +
     SqlHandler.delimitString(buyPageViewBean.getStocked()) + "," +
     SqlHandler.delimitString(buyPageViewBean.getInventoryGroup()) + "," +
     SqlHandler.delimitString(buyPageViewBean.getCustomerPoNumber()) + "," +
     SqlHandler.delimitString(buyPageViewBean.getReleaseNumber()) + "," +
     SqlHandler.delimitString(buyPageViewBean.getBpoDetail()) + "," +
     SqlHandler.delimitString(buyPageViewBean.getBpo()) + "," +
     SqlHandler.delimitString(buyPageViewBean.getSupplyPath()) + "," +
     SqlHandler.delimitString(buyPageViewBean.getDeliveryPointDesc()) + "," +
       SqlHandler.delimitString(buyPageViewBean.getConsolidationAllowed()) + "," +
     buyPageViewBean.getPurchasingUnitsPerItem() + "," +
       SqlHandler.delimitString(buyPageViewBean.getPurchasingUnitOfMeasure()) + "," +
     SqlHandler.delimitString(buyPageViewBean.getBuyerCompanyId()) + "," +
     SqlHandler.delimitString(buyPageViewBean.getSurplusInventory()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(BuyPageViewBean buyPageViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(buyPageViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(BuyPageViewBean buyPageViewBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_FROZEN + "=" +
      SqlHandler.delimitString(buyPageViewBean.getFrozen()) + "," +
     ATTRIBUTE_CRITICAL + "=" +
      SqlHandler.delimitString(buyPageViewBean.getCritical()) + "," +
     ATTRIBUTE_NOTES + "=" +
      SqlHandler.delimitString(buyPageViewBean.getNotes()) + "," +
     ATTRIBUTE_CANCEL_STATUS + "=" +
      SqlHandler.delimitString(buyPageViewBean.getCancelStatus()) + "," +
     ATTRIBUTE_REQUEST_LINE_STATUS + "=" +
      SqlHandler.delimitString(buyPageViewBean.getRequestLineStatus()) + "," +
     ATTRIBUTE_CATALOG_PRICE + "=" +
      StringHandler.nullIfZero(buyPageViewBean.getCatalogPrice()) + "," +
     ATTRIBUTE_BASELINE_PRICE + "=" +
      StringHandler.nullIfZero(buyPageViewBean.getBaselinePrice()) + "," +
     ATTRIBUTE_MR_QUANTITY + "=" +
      StringHandler.nullIfZero(buyPageViewBean.getMrQuantity()) + "," +
     ATTRIBUTE_REQUESTOR_FIRST_NAME + "=" +
      SqlHandler.delimitString(buyPageViewBean.getRequestorFirstName()) + "," +
     ATTRIBUTE_REQUESTOR_LAST_NAME + "=" +
      SqlHandler.delimitString(buyPageViewBean.getRequestorLastName()) + "," +
     ATTRIBUTE_EMAIL + "=" +
      SqlHandler.delimitString(buyPageViewBean.getEmail()) + "," +
     ATTRIBUTE_PHONE + "=" +
      SqlHandler.delimitString(buyPageViewBean.getPhone()) + "," +
     ATTRIBUTE_PR_NUMBER + "=" +
      StringHandler.nullIfZero(buyPageViewBean.getPrNumber()) + "," +
     ATTRIBUTE_BUYER + "=" +
      SqlHandler.delimitString(buyPageViewBean.getBuyer()) + "," +
     ATTRIBUTE_DATE_ASSIGNED + "=" +
       DateHandler.getOracleToDateFunction(buyPageViewBean.getDateAssigned()) + "," +
     ATTRIBUTE_ITEM_ID + "=" +
      StringHandler.nullIfZero(buyPageViewBean.getItemId()) + "," +
     ATTRIBUTE_NEED_DATE + "=" +
       DateHandler.getOracleToDateFunction(buyPageViewBean.getNeedDate()) + "," +
     ATTRIBUTE_PART_ID + "=" +
      SqlHandler.delimitString(buyPageViewBean.getPartId()) + "," +
     ATTRIBUTE_ITEM_DESC + "=" +
      SqlHandler.delimitString(buyPageViewBean.getItemDesc()) + "," +
     ATTRIBUTE_SHELF_LIFE_DAYS + "=" +
      SqlHandler.delimitString(buyPageViewBean.getShelfLifeDays()) + "," +
     ATTRIBUTE_TRADE_NAME + "=" +
      SqlHandler.delimitString(buyPageViewBean.getTradeName()) + "," +
     ATTRIBUTE_SIZE_UNIT + "=" +
      SqlHandler.delimitString(buyPageViewBean.getSizeUnit()) + "," +
     ATTRIBUTE_MFG_ID + "=" +
      SqlHandler.delimitString(buyPageViewBean.getMfgId()) + "," +
     ATTRIBUTE_MFG_PART_NO + "=" +
      SqlHandler.delimitString(buyPageViewBean.getMfgPartNo()) + "," +
     ATTRIBUTE_ITEM_TYPE + "=" +
      SqlHandler.delimitString(buyPageViewBean.getItemType()) + "," +
     ATTRIBUTE_ORDER_QUANTITY + "=" +
      StringHandler.nullIfZero(buyPageViewBean.getOrderQuantity()) + "," +
     ATTRIBUTE_UOM + "=" +
      SqlHandler.delimitString(buyPageViewBean.getUom()) + "," +
     ATTRIBUTE_PRIORITY + "=" +
      StringHandler.nullIfZero(buyPageViewBean.getPriority()) + "," +
     ATTRIBUTE_RADIAN_PO + "=" +
      StringHandler.nullIfZero(buyPageViewBean.getRadianPo()) + "," +
     ATTRIBUTE_FACILITY + "=" +
      SqlHandler.delimitString(buyPageViewBean.getFacility()) + "," +
     ATTRIBUTE_RAYTHEON_PO + "=" +
      SqlHandler.delimitString(buyPageViewBean.getRaytheonPo()) + "," +
     ATTRIBUTE_FACILITY_ID + "=" +
      SqlHandler.delimitString(buyPageViewBean.getFacilityId()) + "," +
     ATTRIBUTE_BRANCH_PLANT + "=" +
      SqlHandler.delimitString(buyPageViewBean.getBranchPlant()) + "," +
     ATTRIBUTE_HOME_CURRENCY_ID + "=" +
      SqlHandler.delimitString(buyPageViewBean.getHomeCurrencyId()) + "," +
     ATTRIBUTE_DATE_ISSUED + "=" +
       DateHandler.getOracleToDateFunction(buyPageViewBean.getDateIssued()) + "," +
     ATTRIBUTE_DATE_PO_CREATED + "=" +
       DateHandler.getOracleToDateFunction(buyPageViewBean.getDatePoCreated()) + "," +
     ATTRIBUTE_STATUS + "=" +
      SqlHandler.delimitString(buyPageViewBean.getStatus()) + "," +
     ATTRIBUTE_DATE_CHANGED + "=" +
       DateHandler.getOracleToDateFunction(buyPageViewBean.getDateChanged()) + "," +
     ATTRIBUTE_COMMENTS + "=" +
      SqlHandler.delimitString(buyPageViewBean.getComments()) + "," +
     ATTRIBUTE_COMPANY_ID + "=" +
      SqlHandler.delimitString(buyPageViewBean.getCompanyId()) + "," +
     ATTRIBUTE_MR_NUMBER + "=" +
      StringHandler.nullIfZero(buyPageViewBean.getMrNumber()) + "," +
     ATTRIBUTE_MR_LINE_ITEM + "=" +
      SqlHandler.delimitString(buyPageViewBean.getMrLineItem()) + "," +
     ATTRIBUTE_REORDER_POINT + "=" +
      StringHandler.nullIfZero(buyPageViewBean.getReorderPoint()) + "," +
     ATTRIBUTE_STOCKING_LEVEL + "=" +
      StringHandler.nullIfZero(buyPageViewBean.getStockingLevel()) + "," +
     ATTRIBUTE_AVAILABLE_QUANTITY + "=" +
      StringHandler.nullIfZero(buyPageViewBean.getAvailableQuantity()) + "," +
     ATTRIBUTE_OPEN_PO_QUANTITY + "=" +
      StringHandler.nullIfZero(buyPageViewBean.getOpenPoQuantity()) + "," +
     ATTRIBUTE_SHIP_TO_DELIVERY_POINT + "=" +
       SqlHandler.delimitString(buyPageViewBean.getShipToDeliveryPoint()) + "," +
     ATTRIBUTE_SHIP_TO_LOCATION_ID + "=" +
      SqlHandler.delimitString(buyPageViewBean.getShipToLocationId()) + "," +
     ATTRIBUTE_SHIP_TO_COMPANY_ID + "=" +
      SqlHandler.delimitString(buyPageViewBean.getShipToCompanyId()) + "," +
     ATTRIBUTE_BUYER_ID + "=" +
      StringHandler.nullIfZero(buyPageViewBean.getBuyerId()) + "," +
     ATTRIBUTE_LAST_SUPPLIER + "=" +
      SqlHandler.delimitString(buyPageViewBean.getLastSupplier()) + "," +
     ATTRIBUTE_BUYER_NAME + "=" +
      SqlHandler.delimitString(buyPageViewBean.getBuyerName()) + "," +
     ATTRIBUTE_EVER_CONFIRMED + "=" +
      SqlHandler.delimitString(buyPageViewBean.getEverConfirmed()) + "," +
     ATTRIBUTE_UNCONFIRMED + "=" +
      SqlHandler.delimitString(buyPageViewBean.getUnconfirmed()) + "," +
     ATTRIBUTE_DELIVERY_TYPE + "=" +
      SqlHandler.delimitString(buyPageViewBean.getDeliveryType()) + "," +
     ATTRIBUTE_PO_IN_JDE + "=" +
      SqlHandler.delimitString(buyPageViewBean.getPoInJde()) + "," +
     ATTRIBUTE_LPP + "=" +
      SqlHandler.delimitString(buyPageViewBean.getLpp()) + "," +
     ATTRIBUTE_ENGINEERING_EVALUATION + "=" +
       SqlHandler.delimitString(buyPageViewBean.getEngineeringEvaluation()) + "," +
     ATTRIBUTE_REQUEST_ID + "=" +
      StringHandler.nullIfZero(buyPageViewBean.getRequestId()) + "," +
     ATTRIBUTE_CATALOG_ID + "=" +
      SqlHandler.delimitString(buyPageViewBean.getCatalogId()) + "," +
     ATTRIBUTE_STOCKED + "=" +
      SqlHandler.delimitString(buyPageViewBean.getStocked()) + "," +
     ATTRIBUTE_INVENTORY_GROUP + "=" +
      SqlHandler.delimitString(buyPageViewBean.getInventoryGroup()) + "," +
     ATTRIBUTE_CUSTOMER_PO_NUMBER + "=" +
      SqlHandler.delimitString(buyPageViewBean.getCustomerPoNumber()) + "," +
     ATTRIBUTE_RELEASE_NUMBER + "=" +
      SqlHandler.delimitString(buyPageViewBean.getReleaseNumber()) + "," +
     ATTRIBUTE_BPO_DETAIL + "=" +
      SqlHandler.delimitString(buyPageViewBean.getBpoDetail()) + "," +
     ATTRIBUTE_BPO + "=" +
      SqlHandler.delimitString(buyPageViewBean.getBpo()) + "," +
     ATTRIBUTE_SUPPLY_PATH + "=" +
      SqlHandler.delimitString(buyPageViewBean.getSupplyPath()) + "," +
     ATTRIBUTE_DELIVERY_POINT_DESC + "=" +
      SqlHandler.delimitString(buyPageViewBean.getDeliveryPointDesc()) + "," +
     ATTRIBUTE_CONSOLIDATION_ALLOWED + "=" +
       SqlHandler.delimitString(buyPageViewBean.getConsolidationAllowed()) + "," +
     ATTRIBUTE_PURCHASING_UNITS_PER_ITEM + "=" +
       StringHandler.nullIfZero(buyPageViewBean.getPurchasingUnitsPerItem()) + "," +
     ATTRIBUTE_PURCHASING_UNIT_OF_MEASURE + "=" +
       SqlHandler.delimitString(buyPageViewBean.getPurchasingUnitOfMeasure()) + "," +
     ATTRIBUTE_BUYER_COMPANY_ID + "=" +
      SqlHandler.delimitString(buyPageViewBean.getBuyerCompanyId()) + "," +
     ATTRIBUTE_SURPLUS_INVENTORY + "=" +
      SqlHandler.delimitString(buyPageViewBean.getSurplusInventory()) + " " +
     "where " + ATTRIBUTE_FROZEN + "=" +
      buyPageViewBean.getFrozen();
    return new SqlManager().update(conn, query);
   }
	 */

	//select
	public Collection select(SearchCriteria criteria) throws BaseException {
		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = select(criteria, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}

	public Collection select(SearchCriteria criteria, Connection conn) throws
	BaseException {
		Collection buyPageViewBeanColl = new Vector();
		String query = "select * from " + TABLE + " " +
		getWhereClause(criteria);
		DataSet dataSet = new SqlManager().select(conn, query);
		Iterator dataIter = dataSet.iterator();
		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			BuyPageViewBean buyPageViewBean = new BuyPageViewBean();
			load(dataSetRow, buyPageViewBean);
			buyPageViewBeanColl.add(buyPageViewBean);
		}
		return buyPageViewBeanColl;
	}

	public Collection select(PersonnelBean personnelBean, BuyOrdersInputBean bean, Collection hubIgBuyerCollection) throws BaseException {
		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			connection.setAutoCommit(false);
			c = select(personnelBean, bean, hubIgBuyerCollection, connection);
			connection.setAutoCommit(true);
		}
		catch(SQLException e) {
			log.error("Error:" + e.getMessage(),e);
			throw new BaseException(e);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}

	public Collection select(PersonnelBean personnelBean, BuyOrdersInputBean bean, Collection hubIgBuyerCollection, Connection conn) throws
	BaseException, SQLException {
		Collection buyPageViewBeanColl = new Vector();

		Collection inArgs = new Vector(15);
		Collection optArgs = new Vector(3);

		if(bean.getBranchPlant() == null || "All".equalsIgnoreCase(bean.getBranchPlant())) {
			inArgs.add("ALL");
		}
		else {
			inArgs.add(bean.getBranchPlant());
		}
		if(bean.getInventoryGroup() == null || "All".equalsIgnoreCase(bean.getInventoryGroup())) {
			inArgs.add("ALL");
		}
		else {
			inArgs.add(bean.getInventoryGroup());
		}
		if(bean.getFacilityId() == null || "All".equalsIgnoreCase(bean.getFacilityId())) {
			inArgs.add("ALL");
		}
		else {
			inArgs.add(bean.getFacilityId());
		}
		if(bean.getCompanyIdArray() == null || bean.getCompanyIdArray().length == 0) {
			inArgs.add("ALL");
		}
		else {
			inArgs.add(StringHandler.arrayToCommaDelimited(bean.getCompanyIdArray()));
		}
		if(bean.getBuyerId() == null) {
			inArgs.add("");
		}else {
			if(bean.getBuyerId().intValue() == 0) {
				inArgs.add("ALL");
			}
			else {
				inArgs.add(bean.getBuyerId());
			}
		}
		if(bean.getSearchWhat() == null) {
			inArgs.add("");
		}
		else {
			inArgs.add(bean.getSearchWhat());
		}
		if(bean.getSearchType() == null) {
			inArgs.add("");
		}
		else {
			inArgs.add(bean.getSearchType());
		}
		if(bean.getSearchText() == null) {
			inArgs.add("");
		}
		else {
			inArgs.add(bean.getSearchText());
		}
		if(bean.getStatusArray() == null || bean.getStatusArray().length == 0) {
			inArgs.add("All Statuses");
		}
		else {
			inArgs.add(StringHandler.arrayToCommaDelimited(bean.getStatusArray()));
		}
		if(bean.getSupplyPath() == null) {
			inArgs.add("ALL");
		}
		else {
			inArgs.add(bean.getSupplyPath());
		}
		if(bean.getSortBy() == null) {
			inArgs.add("");
		}
		else {
			inArgs.add(bean.getSortBy() + ",item_id");
		}
		if(bean.getBoForConfirmedPo() == null || StringHandler.isBlankString(bean.getBoForConfirmedPo())) {
			inArgs.add("NO");
		}
		else {
			inArgs.add(bean.getBoForConfirmedPo());
		}
		if(bean.getBoForUnconfirmedPo() == null || StringHandler.isBlankString(bean.getBoForUnconfirmedPo())) {
			inArgs.add("NO");
		}
		else {
			inArgs.add(bean.getBoForUnconfirmedPo());
		}
		if(bean.getBoWithNoPo() == null || StringHandler.isBlankString(bean.getBoWithNoPo())) {
			inArgs.add("NO");
		}
		else {
			inArgs.add(bean.getBoWithNoPo());
		}
		if(bean.getConfirmedButNoAssociation() == null || StringHandler.isBlankString(bean.getConfirmedButNoAssociation())) {
			inArgs.add("NO");
		}
		else {
			inArgs.add(bean.getConfirmedButNoAssociation());
		}

		optArgs.add(personnelBean.getPersonnelId());
		if(bean.getOpsEntityId() == null || "All".equalsIgnoreCase(bean.getOpsEntityId())) {
			optArgs.add("");
		}
		else {
			optArgs.add(bean.getOpsEntityId());
		}
        optArgs.add(TcmISBaseAction.getMaxDataCount());

        Collection outArgs = new Vector(1);
		outArgs.add(new Integer(java.sql.Types.VARCHAR));
		GenericProcedureFactory procFactory = new GenericProcedureFactory(this.getDbManager());
		Collection resultCollection = procFactory.doProcedure(conn, "PKG_BUY_ORDER.p_buy_page_query", inArgs, outArgs, optArgs);

		Iterator iterator = resultCollection.iterator();
		String query = null;
		while(iterator.hasNext()) {
			query = (String)iterator.next();
		}

		DataSet dataSet = new SqlManager().select(conn, query);
		Iterator dataIter = dataSet.iterator();
		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			BuyPageViewBean buyPageViewBean = new BuyPageViewBean();
			load(dataSetRow, buyPageViewBean);
			getHubIgBuyers(buyPageViewBean,hubIgBuyerCollection);
			if ( ! (buyPageViewBean.isMxItem() && bean.isExcludeMxItems())) {
				buyPageViewBeanColl.add(buyPageViewBean);
			}
		}

		return buyPageViewBeanColl;
	}

	public Collection selectOpenBuy(PersonnelBean personnelBean, BuyOrdersInputBean bean) throws BaseException {
		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			connection.setAutoCommit(false);
			c = selectOpenBuy(personnelBean, bean,  connection);
			connection.setAutoCommit(true);
		}
		catch(SQLException e) {
			log.error("Error:" + e.getMessage(),e);
			throw new BaseException(e);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}

	public Collection selectOpenBuy(PersonnelBean personnelBean, BuyOrdersInputBean bean, Connection connection) throws
	BaseException {
		Collection buyPageViewBeanColl = null;

		buyPageViewBeanColl = new Vector();

		Collection inArgs = new Vector(15);
		Collection optArgs = new Vector(6);

// need ops_entity_id, inventory_group_id, item_id, ship_to_location_id, ship_to_company_id		
//		getBranchPlant()
		inArgs.add(bean.getBranchPlant());//ALL"); ");
//		inventory_group_id
		inArgs.add(bean.getInventoryGroup());
//		bean.getFacilityId()
		inArgs.add("ALL");
//		bean.getCompanyIdArray()		
		inArgs.add("ALL");
//		bean.getBuyerId()
		inArgs.add("ALL");
// always search for item_id
//		bean.getSearchWhat()
		inArgs.add("ITEM_ID");
		inArgs.add("IS");
		inArgs.add(bean.getItemId()+"");
//		bean.getStatusArray()
		inArgs.add("All Statuses");//All Statuses"); New");
//		bean.getSupplyPath() 
		inArgs.add(bean.getSupplyPath());
// sort by.., need to specify here..?
		inArgs.add("order_quantity,item_id");
//		bean.getBoForConfirmedPo() // only 
		inArgs.add("NO");
//		bean.getBoForUnconfirmedPo() // only
		inArgs.add("NO");
// only with no assigned po.		
		inArgs.add("YES");
//		bean.getConfirmedButNoAssociation() 
		inArgs.add("NO");
// ops_entity_id		
		optArgs.add(personnelBean.getPersonnelId());
		optArgs.add(bean.getOpsEntityId());
        optArgs.add( 2000 );
// add shiptocompanyid, shiptolocationid and needdate required.
        optArgs.add(bean.getShipToCompanyId());
        optArgs.add(bean.getShipToLocationId());
        optArgs.add(bean.getNeedDate());

//      bean.isExcludeMxItems() on page. not on query.        
        bean.setExcludeMxItems(true);
        
        Collection outArgs = new Vector(1);
		outArgs.add(new Integer(java.sql.Types.VARCHAR));
		GenericProcedureFactory procFactory = new GenericProcedureFactory(this.getDbManager());
		Collection resultCollection = procFactory.doProcedure(connection, "PKG_BUY_ORDER.p_buy_page_query", inArgs, outArgs, optArgs);

		Iterator iterator = resultCollection.iterator();
		String query = null;
		while(iterator.hasNext()) {
			query = (String)iterator.next();
		}

		DataSet dataSet = new SqlManager().select(connection, query);
		Iterator dataIter = dataSet.iterator();
		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			BuyPageViewBean buyPageViewBean = new BuyPageViewBean();
			load(dataSetRow, buyPageViewBean);
//			getHubIgBuyers(buyPageViewBean,hubIgBuyerCollection);
			if( !bean.getPrNumber().equals(buyPageViewBean.getPrNumber() ) ){
					if ( ! buyPageViewBean.isMxItem() ) {
						buyPageViewBeanColl.add(buyPageViewBean);
					}
			}
		}
	return buyPageViewBeanColl;
	}

	public void getHubIgBuyers( BuyPageViewBean buyPageViewBean, Collection hubIgBuyerCollection )	throws BaseException {
		Iterator iter = hubIgBuyerCollection.iterator();
		while(iter.hasNext()) {
			com.tcmis.common.admin.beans.HubIgBuyerOvBean hubBean = (com.tcmis.common.admin.beans.HubIgBuyerOvBean)iter.next();
			if (buyPageViewBean.getBranchPlant() != null) {
				if (buyPageViewBean.getBranchPlant().equals(hubBean.getHub())) {
					for(com.tcmis.common.admin.beans.IgBuyerObjBean ig:(Collection<com.tcmis.common.admin.beans.IgBuyerObjBean>) hubBean.getIgBuyerList()) {
						if (buyPageViewBean.getInventoryGroup() != null) {
							if (buyPageViewBean.getInventoryGroup().equals(ig.getInventoryGroup())) {
								buyPageViewBean.setBuyerDropDown(ig.getBuyers());
								break;
							}
						}
					}
				}
			}
		}
	}

}