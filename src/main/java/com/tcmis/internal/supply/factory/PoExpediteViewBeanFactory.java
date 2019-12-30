package com.tcmis.internal.supply.factory;


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
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.internal.supply.beans.PoExpediteViewBean;


/******************************************************************************
 * CLASSNAME: PoExpediteViewBeanFactory <br>
 * @version: 1.0, Dec 8, 2008 <br>
 *****************************************************************************/


/**
* Change History ----- 03/13/09 - Shahzad Butt - Added new columns.
* 
* 
*/

public class PoExpediteViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_RADIAN_PO = "RADIAN_PO";
	public String ATTRIBUTE_BUYER_ID = "BUYER_ID";
	public String ATTRIBUTE_SUPPLIER = "SUPPLIER";
	public String ATTRIBUTE_BRANCH_PLANT = "BRANCH_PLANT";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_CUSTOMER_PO = "CUSTOMER_PO";
	public String ATTRIBUTE_CARRIER = "CARRIER";
	public String ATTRIBUTE_DATE_SENT = "DATE_SENT";
	public String ATTRIBUTE_CRITICAL = "CRITICAL";
	public String ATTRIBUTE_PO_LINE = "PO_LINE";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_ORDER_DATE = "ORDER_DATE";
	public String ATTRIBUTE_NEED_DATE = "NEED_DATE";
	public String ATTRIBUTE_PROMISED_DATE = "PROMISED_DATE";
	public String ATTRIBUTE_VENDOR_SHIP_DATE = "VENDOR_SHIP_DATE";
	public String ATTRIBUTE_QUANTITY = "QUANTITY";
	public String ATTRIBUTE_QTY_RECEIVED = "QTY_RECEIVED";
	public String ATTRIBUTE_AMENDMENT = "AMENDMENT";
	public String ATTRIBUTE_UNIT_PRICE = "UNIT_PRICE";
	public String ATTRIBUTE_CURRENCY_ID = "CURRENCY_ID";
	public String ATTRIBUTE_SUPPLIER_NAME = "SUPPLIER_NAME";
	public String ATTRIBUTE_REVISED_PROMISED_DATE = "REVISED_PROMISED_DATE";
	public String ATTRIBUTE_LAST_REVISED = "LAST_REVISED";
	public String ATTRIBUTE_LAST_REVISED_BY = "LAST_REVISED_BY";
	public String ATTRIBUTE_EXPEDITE_COMMENTS = "EXPEDITE_COMMENTS";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_SUPPLY_PATH = "SUPPLY_PATH";
	public String ATTRIBUTE_PART_NUMBER = "PART_NUMBER";
	public String ATTRIBUTE_BUYER_NAME = "BUYER_NAME";
	public String ATTRIBUTE_HUB_NAME = "HUB_NAME";
	public String ATTRIBUTE_MR_ALLOC = "MR_ALLOC";
	public String ATTRIBUTE_QUANTITY_OPEN = "QUANTITY_OPEN";
	public String ATTRIBUTE_ITEM_DESC = "ITEM_DESC";
	public String ATTRIBUTE_PACKAGING = "PACKAGING";
	public String ATTRIBUTE_EXPEDITE_AGE = "EXPEDITE_AGE";
	public String ATTRIBUTE_SHIP_TO_ADDRESS = "SHIP_TO_ADDRESS";
	public String ATTRIBUTE_DAYS_LATE = "DAYS_LATE";
	public String ATTRIBUTE_OPS_ENTITY_ID = "OPS_ENTITY_ID";
	public String ATTRIBUTE_CREDIT_HOLD = "CREDIT_HOLD";
	public String ATTRIBUTE_SUPPLIER_DATE_ACCEPTED = "SUPPLIER_DATE_ACCEPTED";

	//table name
	public String TABLE = "PO_EXPEDITE_VIEW";


	//constructor
	public PoExpediteViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("radianPo")) {
			return ATTRIBUTE_RADIAN_PO;
		}
		else if(attributeName.equals("buyerId")) {
			return ATTRIBUTE_BUYER_ID;
		}
		else if(attributeName.equals("supplier")) {
			return ATTRIBUTE_SUPPLIER;
		}
		else if(attributeName.equals("branchPlant")) {
			return ATTRIBUTE_BRANCH_PLANT;
		}
		else if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("customerPo")) {
			return ATTRIBUTE_CUSTOMER_PO;
		}
		else if(attributeName.equals("carrier")) {
			return ATTRIBUTE_CARRIER;
		}
		else if(attributeName.equals("dateSent")) {
			return ATTRIBUTE_DATE_SENT;
		}
		else if(attributeName.equals("critical")) {
			return ATTRIBUTE_CRITICAL;
		}
		else if(attributeName.equals("poLine")) {
			return ATTRIBUTE_PO_LINE;
		}
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("orderDate")) {
			return ATTRIBUTE_ORDER_DATE;
		}
		else if(attributeName.equals("needDate")) {
			return ATTRIBUTE_NEED_DATE;
		}
		else if(attributeName.equals("promisedDate")) {
			return ATTRIBUTE_PROMISED_DATE;
		}
		else if(attributeName.equals("vendorShipDate")) {
			return ATTRIBUTE_VENDOR_SHIP_DATE;
		}
		else if(attributeName.equals("quantity")) {
			return ATTRIBUTE_QUANTITY;
		}
		else if(attributeName.equals("qtyReceived")) {
			return ATTRIBUTE_QTY_RECEIVED;
		}
		else if(attributeName.equals("amendment")) {
			return ATTRIBUTE_AMENDMENT;
		}
		else if(attributeName.equals("unitPrice")) {
			return ATTRIBUTE_UNIT_PRICE;
		}
		else if(attributeName.equals("currencyId")) {
			return ATTRIBUTE_CURRENCY_ID;
		}
		else if(attributeName.equals("supplierName")) {
			return ATTRIBUTE_SUPPLIER_NAME;
		}
		else if(attributeName.equals("revisedPromisedDate")) {
			return ATTRIBUTE_REVISED_PROMISED_DATE;
		}
		else if(attributeName.equals("lastRevised")) {
			return ATTRIBUTE_LAST_REVISED;
		}
		else if(attributeName.equals("expediteComments")) {
			return ATTRIBUTE_EXPEDITE_COMMENTS;
		}
		
		else if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		
		else if(attributeName.equals("supplyPath")) {
			return ATTRIBUTE_SUPPLY_PATH;
		}
		else if(attributeName.equals("partNumber")) {
			return ATTRIBUTE_PART_NUMBER;
		}
		else if(attributeName.equals("buyerName")) {
			return ATTRIBUTE_BUYER_NAME;
		}
		else if(attributeName.equals("hubName")) {
			return ATTRIBUTE_HUB_NAME;
		}
		else if(attributeName.equals("mrAlloc")) {
			return ATTRIBUTE_MR_ALLOC;
		}
		else if(attributeName.equals("quantityOpen")) {
			return ATTRIBUTE_QUANTITY_OPEN;
		}
		else if(attributeName.equals("itemDesc")) {
			return ATTRIBUTE_ITEM_DESC;
		}
		else if(attributeName.equals("packaging")) {
			return ATTRIBUTE_PACKAGING;
		}
		else if(attributeName.equals("expediteAge")) {
			return ATTRIBUTE_EXPEDITE_AGE;
		}
		else if(attributeName.equals("lastRevisedBy")) {
			return ATTRIBUTE_LAST_REVISED_BY;
		}
		else if(attributeName.equals("shipToAddress")) {
			return ATTRIBUTE_SHIP_TO_ADDRESS;
		}
		else if(attributeName.equals("daysLate")) {
			return ATTRIBUTE_DAYS_LATE;
		}
		else if(attributeName.equals("opsEntityId")) {
			return ATTRIBUTE_OPS_ENTITY_ID;
		}
		else if(attributeName.equals("creditHold")) {
			return ATTRIBUTE_CREDIT_HOLD;
		}
		else if(attributeName.equals("supplierDateAccepted")) {
			return ATTRIBUTE_SUPPLIER_DATE_ACCEPTED;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, PoExpediteViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(PoExpediteViewBean poExpediteViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("radianPo", "SearchCriterion.EQUALS",
			"" + poExpediteViewBean.getRadianPo());

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


	public int delete(PoExpediteViewBean poExpediteViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("radianPo", "SearchCriterion.EQUALS",
			"" + poExpediteViewBean.getRadianPo());

		return delete(criteria, conn);
	}
*/

	public int delete(SearchCriteria criteria)
		throws BaseException {

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


	public int delete(SearchCriteria criteria, Connection conn)
		throws BaseException {

		String sqlQuery = " delete from " + TABLE + " " +
			getWhereClause(criteria);

		return new SqlManager().update(conn, sqlQuery);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//insert
	public int insert(PoExpediteViewBean poExpediteViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(poExpediteViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(PoExpediteViewBean poExpediteViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_RADIAN_PO + "," +
			ATTRIBUTE_BUYER_ID + "," +
			ATTRIBUTE_SUPPLIER + "," +
			ATTRIBUTE_BRANCH_PLANT + "," +
			ATTRIBUTE_INVENTORY_GROUP + "," +
			ATTRIBUTE_CUSTOMER_PO + "," +
			ATTRIBUTE_CARRIER + "," +
			ATTRIBUTE_DATE_SENT + "," +
			ATTRIBUTE_CRITICAL + "," +
			ATTRIBUTE_PO_LINE + "," +
			ATTRIBUTE_ITEM_ID + "," +
			ATTRIBUTE_ORDER_DATE + "," +
			ATTRIBUTE_NEED_DATE + "," +
			ATTRIBUTE_PROMISED_DATE + "," +
			ATTRIBUTE_VENDOR_SHIP_DATE + "," +
			ATTRIBUTE_QUANTITY + "," +
			ATTRIBUTE_QTY_RECEIVED + "," +
			ATTRIBUTE_AMENDMENT + "," +
			ATTRIBUTE_UNIT_PRICE + "," +
			ATTRIBUTE_CURRENCY_ID + "," +
			ATTRIBUTE_SUPPLIER_NAME + "," +
			ATTRIBUTE_REVISED_PROMISED_DATE + "," +
			ATTRIBUTE_LAST_REVISED + "," +
			ATTRIBUTE_LAST_REVISED_BY + "," +
			ATTRIBUTE_EXPEDITE_COMMENTS + "," +
			ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_SUPPLY_PATH + "," +
			ATTRIBUTE_PART_NUMBER + "," +
			ATTRIBUTE_BUYER_NAME + "," +
			ATTRIBUTE_HUB_NAME + "," +
			ATTRIBUTE_MR_ALLOC + "," +
			ATTRIBUTE_QUANTITY_OPEN + "," +
			ATTRIBUTE_ITEM_DESC + "," +
			ATTRIBUTE_PACKAGING + "," +
			ATTRIBUTE_EXPEDITE_AGE + "," +
			ATTRIBUTE_SHIP_TO_ADDRESS + ")" +
			" values (" +
			poExpediteViewBean.getRadianPo() + "," +
			poExpediteViewBean.getBuyerId() + "," +
			SqlHandler.delimitString(poExpediteViewBean.getSupplier()) + "," +
			SqlHandler.delimitString(poExpediteViewBean.getBranchPlant()) + "," +
			SqlHandler.delimitString(poExpediteViewBean.getInventoryGroup()) + "," +
			SqlHandler.delimitString(poExpediteViewBean.getCustomerPo()) + "," +
			SqlHandler.delimitString(poExpediteViewBean.getCarrier()) + "," +
			DateHandler.getOracleToDateFunction(poExpediteViewBean.getDateSent()) + "," +
			SqlHandler.delimitString(poExpediteViewBean.getCritical()) + "," +
			poExpediteViewBean.getPoLine() + "," +
			poExpediteViewBean.getItemId() + "," +
			DateHandler.getOracleToDateFunction(poExpediteViewBean.getOrderDate()) + "," +
			DateHandler.getOracleToDateFunction(poExpediteViewBean.getNeedDate()) + "," +
			DateHandler.getOracleToDateFunction(poExpediteViewBean.getPromisedDate()) + "," +
			DateHandler.getOracleToDateFunction(poExpediteViewBean.getVendorShipDate()) + "," +
			poExpediteViewBean.getQuantity() + "," +
			poExpediteViewBean.getQtyReceived() + "," +
			poExpediteViewBean.getAmendment() + "," +
			poExpediteViewBean.getUnitPrice() + "," +
			SqlHandler.delimitString(poExpediteViewBean.getCurrencyId()) + "," +
			SqlHandler.delimitString(poExpediteViewBean.getSupplierName()) + "," +
			DateHandler.getOracleToDateFunction(poExpediteViewBean.getRevisedPromisedDate()) + "," +
			DateHandler.getOracleToDateFunction(poExpediteViewBean.getLastRevised()) + "," +
			SqlHandler.delimitString(poExpediteViewBean.getLastRevisedBy()) + "," +
			SqlHandler.delimitString(poExpediteViewBean.getExpediteComments()) + "," +
			SqlHandler.delimitString(poExpediteViewBean.getCompanyId()) + "," +
			SqlHandler.delimitString(poExpediteViewBean.getSupplyPath()) + "," +
			SqlHandler.delimitString(poExpediteViewBean.getPartNumber()) + "," +
			SqlHandler.delimitString(poExpediteViewBean.getBuyerName()) + "," +
			SqlHandler.delimitString(poExpediteViewBean.getHubName()) + "," +
			poExpediteViewBean.getMrAlloc() + "," +
			poExpediteViewBean.getQuantityOpen() + "," +
			SqlHandler.delimitString(poExpediteViewBean.getItemDesc()) + "," +
			SqlHandler.delimitString(poExpediteViewBean.getPackaging()) + "," +
			poExpediteViewBean.getExpediteAge() + "," +
			SqlHandler.delimitString(poExpediteViewBean.getShipToAddress()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(PoExpediteViewBean poExpediteViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(poExpediteViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(PoExpediteViewBean poExpediteViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_RADIAN_PO + "=" + 
				StringHandler.nullIfZero(poExpediteViewBean.getRadianPo()) + "," +
			ATTRIBUTE_BUYER_ID + "=" + 
				StringHandler.nullIfZero(poExpediteViewBean.getBuyerId()) + "," +
			ATTRIBUTE_SUPPLIER + "=" + 
				SqlHandler.delimitString(poExpediteViewBean.getSupplier()) + "," +
			ATTRIBUTE_BRANCH_PLANT + "=" + 
				SqlHandler.delimitString(poExpediteViewBean.getBranchPlant()) + "," +
			ATTRIBUTE_INVENTORY_GROUP + "=" + 
				SqlHandler.delimitString(poExpediteViewBean.getInventoryGroup()) + "," +
			ATTRIBUTE_CUSTOMER_PO + "=" + 
				SqlHandler.delimitString(poExpediteViewBean.getCustomerPo()) + "," +
			ATTRIBUTE_CARRIER + "=" + 
				SqlHandler.delimitString(poExpediteViewBean.getCarrier()) + "," +
			ATTRIBUTE_DATE_SENT + "=" + 
				DateHandler.getOracleToDateFunction(poExpediteViewBean.getDateSent()) + "," +
			ATTRIBUTE_CRITICAL + "=" + 
				SqlHandler.delimitString(poExpediteViewBean.getCritical()) + "," +
			ATTRIBUTE_PO_LINE + "=" + 
				StringHandler.nullIfZero(poExpediteViewBean.getPoLine()) + "," +
			ATTRIBUTE_ITEM_ID + "=" + 
				StringHandler.nullIfZero(poExpediteViewBean.getItemId()) + "," +
			ATTRIBUTE_ORDER_DATE + "=" + 
				DateHandler.getOracleToDateFunction(poExpediteViewBean.getOrderDate()) + "," +
			ATTRIBUTE_NEED_DATE + "=" + 
				DateHandler.getOracleToDateFunction(poExpediteViewBean.getNeedDate()) + "," +
			ATTRIBUTE_PROMISED_DATE + "=" + 
				DateHandler.getOracleToDateFunction(poExpediteViewBean.getPromisedDate()) + "," +
			ATTRIBUTE_VENDOR_SHIP_DATE + "=" + 
				DateHandler.getOracleToDateFunction(poExpediteViewBean.getVendorShipDate()) + "," +
			ATTRIBUTE_QUANTITY + "=" + 
				StringHandler.nullIfZero(poExpediteViewBean.getQuantity()) + "," +
			ATTRIBUTE_QTY_RECEIVED + "=" + 
				StringHandler.nullIfZero(poExpediteViewBean.getQtyReceived()) + "," +
			ATTRIBUTE_AMENDMENT + "=" + 
				StringHandler.nullIfZero(poExpediteViewBean.getAmendment()) + "," +
			ATTRIBUTE_UNIT_PRICE + "=" + 
				StringHandler.nullIfZero(poExpediteViewBean.getUnitPrice()) + "," +
			ATTRIBUTE_CURRENCY_ID + "=" + 
				SqlHandler.delimitString(poExpediteViewBean.getCurrencyId()) + "," +
			ATTRIBUTE_SUPPLIER_NAME + "=" + 
				SqlHandler.delimitString(poExpediteViewBean.getSupplierName()) + "," +
			ATTRIBUTE_REVISED_PROMISED_DATE + "=" + 
				DateHandler.getOracleToDateFunction(poExpediteViewBean.getRevisedPromisedDate()) + "," +
			ATTRIBUTE_LAST_REVISED + "=" + 
				DateHandler.getOracleToDateFunction(poExpediteViewBean.getLastRevised()) + "," +
			ATTRIBUTE_LAST_REVISED_BY + "=" + 
				SqlHandler.delimitString(poExpediteViewBean.getLastRevisedBy()) + "," +
			ATTRIBUTE_EXPEDITE_COMMENTS + "=" + 
				SqlHandler.delimitString(poExpediteViewBean.getExpediteComments()) + "," +
			ATTRIBUTE_COMPANY_ID + "=" + 
				SqlHandler.delimitString(poExpediteViewBean.getCompanyId()) + "," +
			ATTRIBUTE_SUPPLY_PATH + "=" + 
				SqlHandler.delimitString(poExpediteViewBean.getSupplyPath()) + "," +
			ATTRIBUTE_PART_NUMBER + "=" + 
				SqlHandler.delimitString(poExpediteViewBean.getPartNumber()) + "," +
			ATTRIBUTE_BUYER_NAME + "=" + 
				SqlHandler.delimitString(poExpediteViewBean.getBuyerName()) + "," +
			ATTRIBUTE_HUB_NAME + "=" + 
				SqlHandler.delimitString(poExpediteViewBean.getHubName()) + "," +
			ATTRIBUTE_MR_ALLOC + "=" + 
				StringHandler.nullIfZero(poExpediteViewBean.getMrAlloc()) + "," +
			ATTRIBUTE_QUANTITY_OPEN + "=" + 
				StringHandler.nullIfZero(poExpediteViewBean.getQuantityOpen()) + "," +
			ATTRIBUTE_ITEM_DESC + "=" + 
				SqlHandler.delimitString(poExpediteViewBean.getItemDesc()) + "," +
			ATTRIBUTE_PACKAGING + "=" + 
				SqlHandler.delimitString(poExpediteViewBean.getPackaging()) + "," +
			ATTRIBUTE_EXPEDITE_AGE + "=" + 
				StringHandler.nullIfZero(poExpediteViewBean.getExpediteAge()) + "," +
			ATTRIBUTE_SHIP_TO_ADDRESS + "=" + 
				SqlHandler.delimitString(poExpediteViewBean.getShipToAddress()) + " " +
			"where " + ATTRIBUTE_RADIAN_PO + "=" +
				poExpediteViewBean.getRadianPo();

		return new SqlManager().update(conn, query);
	}
*/

	//select
	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria)
		throws BaseException {

		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = select(criteria, sortCriteria, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}
	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn)
		throws BaseException {

		Collection poExpediteViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			PoExpediteViewBean poExpediteViewBean = new PoExpediteViewBean();
			load(dataSetRow, poExpediteViewBean);
			poExpediteViewBeanColl.add(poExpediteViewBean);
		}

		return poExpediteViewBeanColl;
	}
}