package com.tcmis.internal.supply.factory;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import java.lang.reflect.Method;

import java.sql.Connection;

import java.text.ParseException;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DataBeanCreationException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.util.*;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.internal.supply.beans.PoLineExpediteHistoryViewBean;


/******************************************************************************
 * CLASSNAME: PoLineExpediteHistoryViewBeanFactory <br>
 * @version: 1.0, Jan 30, 2010 <br>
 *****************************************************************************/


public class PoLineExpediteHistoryViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_BUYER_ID = "BUYER_ID";
	public String ATTRIBUTE_SUPPLIER = "SUPPLIER";
	public String ATTRIBUTE_BRANCH_PLANT = "BRANCH_PLANT";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_CUSTOMER_PO = "CUSTOMER_PO";
	public String ATTRIBUTE_CARRIER = "CARRIER";
	public String ATTRIBUTE_DATE_SENT = "DATE_SENT";
	public String ATTRIBUTE_CRITICAL = "CRITICAL";
	public String ATTRIBUTE_DATE_FIRST_CONFIRMED = "DATE_FIRST_CONFIRMED";
	public String ATTRIBUTE_NEED_DATE = "NEED_DATE";
	public String ATTRIBUTE_VENDOR_SHIP_DATE = "VENDOR_SHIP_DATE";
	public String ATTRIBUTE_QUANTITY = "QUANTITY";
	public String ATTRIBUTE_QTY_RECEIVED = "QTY_RECEIVED";
	public String ATTRIBUTE_UNIT_PRICE = "UNIT_PRICE";
	public String ATTRIBUTE_CURRENCY_ID = "CURRENCY_ID";
	public String ATTRIBUTE_SUPPLIER_NAME = "SUPPLIER_NAME";
	public String ATTRIBUTE_BUYER_NAME = "BUYER_NAME";
	public String ATTRIBUTE_QUANTITY_OPEN = "QUANTITY_OPEN";
	public String ATTRIBUTE_SHIP_TO_ADDRESS = "SHIP_TO_ADDRESS";
	public String ATTRIBUTE_RADIAN_PO = "RADIAN_PO";
	public String ATTRIBUTE_PO_LINE = "PO_LINE";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_PROMISED_DATE = "PROMISED_DATE";
	public String ATTRIBUTE_REVISED_PROMISED_DATE = "REVISED_PROMISED_DATE";
	public String ATTRIBUTE_DATE_ENTERED = "DATE_ENTERED";
	public String ATTRIBUTE_COMMENTS = "COMMENTS";
	public String ATTRIBUTE_ENTERED_BY_NAME = "ENTERED_BY_NAME";
	public String ATTRIBUTE_OPS_ENTITY_ID = "OPS_ENTITY_ID";
	public String ATTRIBUTE_CREDIT_HOLD = "CREDIT_HOLD";

	//table name
	public String TABLE = "PO_LINE_EXPEDITE_HISTORY_VIEW";


	//constructor
	public PoLineExpediteHistoryViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("buyerId")) {
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
		else if(attributeName.equals("dateFirstConfirmed")) {
			return ATTRIBUTE_DATE_FIRST_CONFIRMED;
		}
		else if(attributeName.equals("needDate")) {
			return ATTRIBUTE_NEED_DATE;
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
		else if(attributeName.equals("unitPrice")) {
			return ATTRIBUTE_UNIT_PRICE;
		}
		else if(attributeName.equals("currencyId")) {
			return ATTRIBUTE_CURRENCY_ID;
		}
		else if(attributeName.equals("supplierName")) {
			return ATTRIBUTE_SUPPLIER_NAME;
		}
		else if(attributeName.equals("buyerName")) {
			return ATTRIBUTE_BUYER_NAME;
		}
		else if(attributeName.equals("quantityOpen")) {
			return ATTRIBUTE_QUANTITY_OPEN;
		}
		else if(attributeName.equals("shipToAddress")) {
			return ATTRIBUTE_SHIP_TO_ADDRESS;
		}
		else if(attributeName.equals("radianPo")) {
			return ATTRIBUTE_RADIAN_PO;
		}
		else if(attributeName.equals("poLine")) {
			return ATTRIBUTE_PO_LINE;
		}
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("promisedDate")) {
			return ATTRIBUTE_PROMISED_DATE;
		}
		else if(attributeName.equals("revisedPromisedDate")) {
			return ATTRIBUTE_REVISED_PROMISED_DATE;
		}
		else if(attributeName.equals("dateEntered")) {
			return ATTRIBUTE_DATE_ENTERED;
		}
		else if(attributeName.equals("comments")) {
			return ATTRIBUTE_COMMENTS;
		}
		else if(attributeName.equals("enteredByName")) {
			return ATTRIBUTE_ENTERED_BY_NAME;
		}
		else if(attributeName.equals("opsEntityId")) {
			return ATTRIBUTE_OPS_ENTITY_ID;
		}
		else if(attributeName.equals("creditHold")) {
			return ATTRIBUTE_CREDIT_HOLD;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, PoLineExpediteHistoryViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(PoLineExpediteHistoryViewBean poLineExpediteHistoryViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("buyerId", "SearchCriterion.EQUALS",
			"" + poLineExpediteHistoryViewBean.getBuyerId());

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


	public int delete(PoLineExpediteHistoryViewBean poLineExpediteHistoryViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("buyerId", "SearchCriterion.EQUALS",
			"" + poLineExpediteHistoryViewBean.getBuyerId());

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
	public int insert(PoLineExpediteHistoryViewBean poLineExpediteHistoryViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(poLineExpediteHistoryViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(PoLineExpediteHistoryViewBean poLineExpediteHistoryViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_BUYER_ID + "," +
			ATTRIBUTE_SUPPLIER + "," +
			ATTRIBUTE_BRANCH_PLANT + "," +
			ATTRIBUTE_INVENTORY_GROUP + "," +
			ATTRIBUTE_CUSTOMER_PO + "," +
			ATTRIBUTE_CARRIER + "," +
			ATTRIBUTE_DATE_SENT + "," +
			ATTRIBUTE_CRITICAL + "," +
			ATTRIBUTE_DATE_FIRST_CONFIRMED + "," +
			ATTRIBUTE_NEED_DATE + "," +
			ATTRIBUTE_VENDOR_SHIP_DATE + "," +
			ATTRIBUTE_QUANTITY + "," +
			ATTRIBUTE_QTY_RECEIVED + "," +
			ATTRIBUTE_UNIT_PRICE + "," +
			ATTRIBUTE_CURRENCY_ID + "," +
			ATTRIBUTE_SUPPLIER_NAME + "," +
			ATTRIBUTE_BUYER_NAME + "," +
			ATTRIBUTE_QUANTITY_OPEN + "," +
			ATTRIBUTE_SHIP_TO_ADDRESS + "," +
			ATTRIBUTE_RADIAN_PO + "," +
			ATTRIBUTE_PO_LINE + "," +
			ATTRIBUTE_ITEM_ID + "," +
			ATTRIBUTE_PROMISED_DATE + "," +
			ATTRIBUTE_REVISED_PROMISED_DATE + "," +
			ATTRIBUTE_DATE_ENTERED + "," +
			ATTRIBUTE_COMMENTS + "," +
			ATTRIBUTE_ENTERED_BY_NAME + "," +
			ATTRIBUTE_OPS_ENTITY_ID + ")" +
			" values (" +
			poLineExpediteHistoryViewBean.getBuyerId() + "," +
			SqlHandler.delimitString(poLineExpediteHistoryViewBean.getSupplier()) + "," +
			SqlHandler.delimitString(poLineExpediteHistoryViewBean.getBranchPlant()) + "," +
			SqlHandler.delimitString(poLineExpediteHistoryViewBean.getInventoryGroup()) + "," +
			SqlHandler.delimitString(poLineExpediteHistoryViewBean.getCustomerPo()) + "," +
			SqlHandler.delimitString(poLineExpediteHistoryViewBean.getCarrier()) + "," +
			DateHandler.getOracleToDateFunction(poLineExpediteHistoryViewBean.getDateSent()) + "," +
			SqlHandler.delimitString(poLineExpediteHistoryViewBean.getCritical()) + "," +
			DateHandler.getOracleToDateFunction(poLineExpediteHistoryViewBean.getDateFirstConfirmed()) + "," +
			DateHandler.getOracleToDateFunction(poLineExpediteHistoryViewBean.getNeedDate()) + "," +
			DateHandler.getOracleToDateFunction(poLineExpediteHistoryViewBean.getVendorShipDate()) + "," +
			poLineExpediteHistoryViewBean.getQuantity() + "," +
			poLineExpediteHistoryViewBean.getQtyReceived() + "," +
			poLineExpediteHistoryViewBean.getUnitPrice() + "," +
			SqlHandler.delimitString(poLineExpediteHistoryViewBean.getCurrencyId()) + "," +
			SqlHandler.delimitString(poLineExpediteHistoryViewBean.getSupplierName()) + "," +
			SqlHandler.delimitString(poLineExpediteHistoryViewBean.getBuyerName()) + "," +
			poLineExpediteHistoryViewBean.getQuantityOpen() + "," +
			SqlHandler.delimitString(poLineExpediteHistoryViewBean.getShipToAddress()) + "," +
			poLineExpediteHistoryViewBean.getRadianPo() + "," +
			poLineExpediteHistoryViewBean.getPoLine() + "," +
			poLineExpediteHistoryViewBean.getItemId() + "," +
			DateHandler.getOracleToDateFunction(poLineExpediteHistoryViewBean.getPromisedDate()) + "," +
			DateHandler.getOracleToDateFunction(poLineExpediteHistoryViewBean.getRevisedPromisedDate()) + "," +
			DateHandler.getOracleToDateFunction(poLineExpediteHistoryViewBean.getDateEntered()) + "," +
			SqlHandler.delimitString(poLineExpediteHistoryViewBean.getComments()) + "," +
			SqlHandler.delimitString(poLineExpediteHistoryViewBean.getEnteredByName()) + "," +
			SqlHandler.delimitString(poLineExpediteHistoryViewBean.getOpsEntityId()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(PoLineExpediteHistoryViewBean poLineExpediteHistoryViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(poLineExpediteHistoryViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(PoLineExpediteHistoryViewBean poLineExpediteHistoryViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_BUYER_ID + "=" + 
				StringHandler.nullIfZero(poLineExpediteHistoryViewBean.getBuyerId()) + "," +
			ATTRIBUTE_SUPPLIER + "=" + 
				SqlHandler.delimitString(poLineExpediteHistoryViewBean.getSupplier()) + "," +
			ATTRIBUTE_BRANCH_PLANT + "=" + 
				SqlHandler.delimitString(poLineExpediteHistoryViewBean.getBranchPlant()) + "," +
			ATTRIBUTE_INVENTORY_GROUP + "=" + 
				SqlHandler.delimitString(poLineExpediteHistoryViewBean.getInventoryGroup()) + "," +
			ATTRIBUTE_CUSTOMER_PO + "=" + 
				SqlHandler.delimitString(poLineExpediteHistoryViewBean.getCustomerPo()) + "," +
			ATTRIBUTE_CARRIER + "=" + 
				SqlHandler.delimitString(poLineExpediteHistoryViewBean.getCarrier()) + "," +
			ATTRIBUTE_DATE_SENT + "=" + 
				DateHandler.getOracleToDateFunction(poLineExpediteHistoryViewBean.getDateSent()) + "," +
			ATTRIBUTE_CRITICAL + "=" + 
				SqlHandler.delimitString(poLineExpediteHistoryViewBean.getCritical()) + "," +
			ATTRIBUTE_DATE_FIRST_CONFIRMED + "=" + 
				DateHandler.getOracleToDateFunction(poLineExpediteHistoryViewBean.getDateFirstConfirmed()) + "," +
			ATTRIBUTE_NEED_DATE + "=" + 
				DateHandler.getOracleToDateFunction(poLineExpediteHistoryViewBean.getNeedDate()) + "," +
			ATTRIBUTE_VENDOR_SHIP_DATE + "=" + 
				DateHandler.getOracleToDateFunction(poLineExpediteHistoryViewBean.getVendorShipDate()) + "," +
			ATTRIBUTE_QUANTITY + "=" + 
				StringHandler.nullIfZero(poLineExpediteHistoryViewBean.getQuantity()) + "," +
			ATTRIBUTE_QTY_RECEIVED + "=" + 
				StringHandler.nullIfZero(poLineExpediteHistoryViewBean.getQtyReceived()) + "," +
			ATTRIBUTE_UNIT_PRICE + "=" + 
				StringHandler.nullIfZero(poLineExpediteHistoryViewBean.getUnitPrice()) + "," +
			ATTRIBUTE_CURRENCY_ID + "=" + 
				SqlHandler.delimitString(poLineExpediteHistoryViewBean.getCurrencyId()) + "," +
			ATTRIBUTE_SUPPLIER_NAME + "=" + 
				SqlHandler.delimitString(poLineExpediteHistoryViewBean.getSupplierName()) + "," +
			ATTRIBUTE_BUYER_NAME + "=" + 
				SqlHandler.delimitString(poLineExpediteHistoryViewBean.getBuyerName()) + "," +
			ATTRIBUTE_QUANTITY_OPEN + "=" + 
				StringHandler.nullIfZero(poLineExpediteHistoryViewBean.getQuantityOpen()) + "," +
			ATTRIBUTE_SHIP_TO_ADDRESS + "=" + 
				SqlHandler.delimitString(poLineExpediteHistoryViewBean.getShipToAddress()) + "," +
			ATTRIBUTE_RADIAN_PO + "=" + 
				StringHandler.nullIfZero(poLineExpediteHistoryViewBean.getRadianPo()) + "," +
			ATTRIBUTE_PO_LINE + "=" + 
				StringHandler.nullIfZero(poLineExpediteHistoryViewBean.getPoLine()) + "," +
			ATTRIBUTE_ITEM_ID + "=" + 
				StringHandler.nullIfZero(poLineExpediteHistoryViewBean.getItemId()) + "," +
			ATTRIBUTE_PROMISED_DATE + "=" + 
				DateHandler.getOracleToDateFunction(poLineExpediteHistoryViewBean.getPromisedDate()) + "," +
			ATTRIBUTE_REVISED_PROMISED_DATE + "=" + 
				DateHandler.getOracleToDateFunction(poLineExpediteHistoryViewBean.getRevisedPromisedDate()) + "," +
			ATTRIBUTE_DATE_ENTERED + "=" + 
				DateHandler.getOracleToDateFunction(poLineExpediteHistoryViewBean.getDateEntered()) + "," +
			ATTRIBUTE_COMMENTS + "=" + 
				SqlHandler.delimitString(poLineExpediteHistoryViewBean.getComments()) + "," +
			ATTRIBUTE_ENTERED_BY_NAME + "=" + 
				SqlHandler.delimitString(poLineExpediteHistoryViewBean.getEnteredByName()) + "," +
			ATTRIBUTE_OPS_ENTITY_ID + "=" + 
				SqlHandler.delimitString(poLineExpediteHistoryViewBean.getOpsEntityId()) + " " +
			"where " + ATTRIBUTE_BUYER_ID + "=" +
				poLineExpediteHistoryViewBean.getBuyerId();

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

		Collection poLineExpediteHistoryViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			PoLineExpediteHistoryViewBean poLineExpediteHistoryViewBean = new PoLineExpediteHistoryViewBean();
			load(dataSetRow, poLineExpediteHistoryViewBean);
			poLineExpediteHistoryViewBeanColl.add(poLineExpediteHistoryViewBean);
		}

		return poLineExpediteHistoryViewBeanColl;
	}
}