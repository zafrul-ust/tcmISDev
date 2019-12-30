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
import com.tcmis.internal.supply.beans.PoLineHistoryViewBean;


/******************************************************************************
 * CLASSNAME: PoLineHistoryViewBeanFactory <br>
 * @version: 1.0, Aug 30, 2007 <br>
 *****************************************************************************/


public class PoLineHistoryViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_RADIAN_PO = "RADIAN_PO";
	public String ATTRIBUTE_PO_LINE = "PO_LINE";
	public String ATTRIBUTE_AMENDMENT = "AMENDMENT";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_QUANTITY = "QUANTITY";
	public String ATTRIBUTE_UNIT_PRICE = "UNIT_PRICE";
	public String ATTRIBUTE_CURRENCY_ID = "CURRENCY_ID";
	public String ATTRIBUTE_NEED_DATE = "NEED_DATE";
	public String ATTRIBUTE_PROMISED_DATE = "PROMISED_DATE";
	public String ATTRIBUTE_ALLOWED_PRICE_VARIANCE = "ALLOWED_PRICE_VARIANCE";
	public String ATTRIBUTE_MFG_PART_NO = "MFG_PART_NO";
	public String ATTRIBUTE_SUPPLIER_PART_NO = "SUPPLIER_PART_NO";
	public String ATTRIBUTE_DPAS_RATING = "DPAS_RATING";
	public String ATTRIBUTE_QUALITY_FLOW_DOWNS = "QUALITY_FLOW_DOWNS";
	public String ATTRIBUTE_PACKAGING_FLOW_DOWNS = "PACKAGING_FLOW_DOWNS";
	public String ATTRIBUTE_PO_LINE_NOTE = "PO_LINE_NOTE";
	public String ATTRIBUTE_TRANSACTION_DATE = "TRANSACTION_DATE";
	public String ATTRIBUTE_DATE_CONFIRMED = "DATE_CONFIRMED";
	public String ATTRIBUTE_REMARKS = "REMARKS";
	public String ATTRIBUTE_SUPPLIER_QTY = "SUPPLIER_QTY";
	public String ATTRIBUTE_SUPPLIER_PKG = "SUPPLIER_PKG";
	public String ATTRIBUTE_SUPPLIER_UNIT_PRICE = "SUPPLIER_UNIT_PRICE";
	public String ATTRIBUTE_GENERIC_COC = "GENERIC_COC";
	public String ATTRIBUTE_GENERIC_COA = "GENERIC_COA";
	public String ATTRIBUTE_MSDS_REQUESTED_DATE = "MSDS_REQUESTED_DATE";
	public String ATTRIBUTE_REMAINING_SHELF_LIFE_PERCENT = "REMAINING_SHELF_LIFE_PERCENT";
	public String ATTRIBUTE_DELIVERY_COMMENTS = "DELIVERY_COMMENTS";
	public String ATTRIBUTE_VENDOR_SHIP_DATE = "VENDOR_SHIP_DATE";

	//table name
	public String TABLE = "PO_LINE_HISTORY_VIEW";


	//constructor
	public PoLineHistoryViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("radianPo")) {
			return ATTRIBUTE_RADIAN_PO;
		}
		else if(attributeName.equals("poLine")) {
			return ATTRIBUTE_PO_LINE;
		}
		else if(attributeName.equals("amendment")) {
			return ATTRIBUTE_AMENDMENT;
		}
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("quantity")) {
			return ATTRIBUTE_QUANTITY;
		}
		else if(attributeName.equals("unitPrice")) {
			return ATTRIBUTE_UNIT_PRICE;
		}
		else if(attributeName.equals("currencyId")) {
			return ATTRIBUTE_CURRENCY_ID;
		}
		else if(attributeName.equals("needDate")) {
			return ATTRIBUTE_NEED_DATE;
		}
		else if(attributeName.equals("promisedDate")) {
			return ATTRIBUTE_PROMISED_DATE;
		}
		else if(attributeName.equals("allowedPriceVariance")) {
			return ATTRIBUTE_ALLOWED_PRICE_VARIANCE;
		}
		else if(attributeName.equals("mfgPartNo")) {
			return ATTRIBUTE_MFG_PART_NO;
		}
		else if(attributeName.equals("supplierPartNo")) {
			return ATTRIBUTE_SUPPLIER_PART_NO;
		}
		else if(attributeName.equals("dpasRating")) {
			return ATTRIBUTE_DPAS_RATING;
		}
		else if(attributeName.equals("qualityFlowDowns")) {
			return ATTRIBUTE_QUALITY_FLOW_DOWNS;
		}
		else if(attributeName.equals("packagingFlowDowns")) {
			return ATTRIBUTE_PACKAGING_FLOW_DOWNS;
		}
		else if(attributeName.equals("poLineNote")) {
			return ATTRIBUTE_PO_LINE_NOTE;
		}
		else if(attributeName.equals("transactionDate")) {
			return ATTRIBUTE_TRANSACTION_DATE;
		}
		else if(attributeName.equals("dateConfirmed")) {
			return ATTRIBUTE_DATE_CONFIRMED;
		}
		else if(attributeName.equals("remarks")) {
			return ATTRIBUTE_REMARKS;
		}
		else if(attributeName.equals("supplierQty")) {
			return ATTRIBUTE_SUPPLIER_QTY;
		}
		else if(attributeName.equals("supplierPkg")) {
			return ATTRIBUTE_SUPPLIER_PKG;
		}
		else if(attributeName.equals("supplierUnitPrice")) {
			return ATTRIBUTE_SUPPLIER_UNIT_PRICE;
		}
		else if(attributeName.equals("genericCoc")) {
			return ATTRIBUTE_GENERIC_COC;
		}
		else if(attributeName.equals("genericCoa")) {
			return ATTRIBUTE_GENERIC_COA;
		}
		else if(attributeName.equals("msdsRequestedDate")) {
			return ATTRIBUTE_MSDS_REQUESTED_DATE;
		}
		else if(attributeName.equals("remainingShelfLifePercent")) {
			return ATTRIBUTE_REMAINING_SHELF_LIFE_PERCENT;
		}
		else if(attributeName.equals("deliveryComments")) {
			return ATTRIBUTE_DELIVERY_COMMENTS;
		}
		else if(attributeName.equals("vendorShipDate")) {
			return ATTRIBUTE_VENDOR_SHIP_DATE;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, PoLineHistoryViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(PoLineHistoryViewBean poLineHistoryViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("radianPo", "SearchCriterion.EQUALS",
			"" + poLineHistoryViewBean.getRadianPo());

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


	public int delete(PoLineHistoryViewBean poLineHistoryViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("radianPo", "SearchCriterion.EQUALS",
			"" + poLineHistoryViewBean.getRadianPo());

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
	public int insert(PoLineHistoryViewBean poLineHistoryViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(poLineHistoryViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(PoLineHistoryViewBean poLineHistoryViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_RADIAN_PO + "," +
			ATTRIBUTE_PO_LINE + "," +
			ATTRIBUTE_AMENDMENT + "," +
			ATTRIBUTE_ITEM_ID + "," +
			ATTRIBUTE_QUANTITY + "," +
			ATTRIBUTE_UNIT_PRICE + "," +
			ATTRIBUTE_CURRENCY_ID + "," +
			ATTRIBUTE_NEED_DATE + "," +
			ATTRIBUTE_PROMISED_DATE + "," +
			ATTRIBUTE_ALLOWED_PRICE_VARIANCE + "," +
			ATTRIBUTE_MFG_PART_NO + "," +
			ATTRIBUTE_SUPPLIER_PART_NO + "," +
			ATTRIBUTE_DPAS_RATING + "," +
			ATTRIBUTE_QUALITY_FLOW_DOWNS + "," +
			ATTRIBUTE_PACKAGING_FLOW_DOWNS + "," +
			ATTRIBUTE_PO_LINE_NOTE + "," +
			ATTRIBUTE_TRANSACTION_DATE + "," +
			ATTRIBUTE_DATE_CONFIRMED + "," +
			ATTRIBUTE_REMARKS + "," +
			ATTRIBUTE_SUPPLIER_QTY + "," +
			ATTRIBUTE_SUPPLIER_PKG + "," +
			ATTRIBUTE_SUPPLIER_UNIT_PRICE + "," +
			ATTRIBUTE_GENERIC_COC + "," +
			ATTRIBUTE_GENERIC_COA + "," +
			ATTRIBUTE_MSDS_REQUESTED_DATE + "," +
			ATTRIBUTE_REMAINING_SHELF_LIFE_PERCENT + "," +
			ATTRIBUTE_DELIVERY_COMMENTS + "," +
			ATTRIBUTE_VENDOR_SHIP_DATE + ")" +
			" values (" +
			poLineHistoryViewBean.getRadianPo() + "," +
			poLineHistoryViewBean.getPoLine() + "," +
			poLineHistoryViewBean.getAmendment() + "," +
			poLineHistoryViewBean.getItemId() + "," +
			poLineHistoryViewBean.getQuantity() + "," +
			poLineHistoryViewBean.getUnitPrice() + "," +
			SqlHandler.delimitString(poLineHistoryViewBean.getCurrencyId()) + "," +
			DateHandler.getOracleToDateFunction(poLineHistoryViewBean.getNeedDate()) + "," +
			DateHandler.getOracleToDateFunction(poLineHistoryViewBean.getPromisedDate()) + "," +
			poLineHistoryViewBean.getAllowedPriceVariance() + "," +
			SqlHandler.delimitString(poLineHistoryViewBean.getMfgPartNo()) + "," +
			SqlHandler.delimitString(poLineHistoryViewBean.getSupplierPartNo()) + "," +
			SqlHandler.delimitString(poLineHistoryViewBean.getDpasRating()) + "," +
			SqlHandler.delimitString(poLineHistoryViewBean.getQualityFlowDowns()) + "," +
			SqlHandler.delimitString(poLineHistoryViewBean.getPackagingFlowDowns()) + "," +
			SqlHandler.delimitString(poLineHistoryViewBean.getPoLineNote()) + "," +
			DateHandler.getOracleToDateFunction(poLineHistoryViewBean.getTransactionDate()) + "," +
			DateHandler.getOracleToDateFunction(poLineHistoryViewBean.getDateConfirmed()) + "," +
			SqlHandler.delimitString(poLineHistoryViewBean.getRemarks()) + "," +
			poLineHistoryViewBean.getSupplierQty() + "," +
			SqlHandler.delimitString(poLineHistoryViewBean.getSupplierPkg()) + "," +
			poLineHistoryViewBean.getSupplierUnitPrice() + "," +
			SqlHandler.delimitString(poLineHistoryViewBean.getGenericCoc()) + "," +
			SqlHandler.delimitString(poLineHistoryViewBean.getGenericCoa()) + "," +
			DateHandler.getOracleToDateFunction(poLineHistoryViewBean.getMsdsRequestedDate()) + "," +
			poLineHistoryViewBean.getRemainingShelfLifePercent() + "," +
			SqlHandler.delimitString(poLineHistoryViewBean.getDeliveryComments()) + "," +
			DateHandler.getOracleToDateFunction(poLineHistoryViewBean.getVendorShipDate()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(PoLineHistoryViewBean poLineHistoryViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(poLineHistoryViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(PoLineHistoryViewBean poLineHistoryViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_RADIAN_PO + "=" + 
				StringHandler.nullIfZero(poLineHistoryViewBean.getRadianPo()) + "," +
			ATTRIBUTE_PO_LINE + "=" + 
				StringHandler.nullIfZero(poLineHistoryViewBean.getPoLine()) + "," +
			ATTRIBUTE_AMENDMENT + "=" + 
				StringHandler.nullIfZero(poLineHistoryViewBean.getAmendment()) + "," +
			ATTRIBUTE_ITEM_ID + "=" + 
				StringHandler.nullIfZero(poLineHistoryViewBean.getItemId()) + "," +
			ATTRIBUTE_QUANTITY + "=" + 
				StringHandler.nullIfZero(poLineHistoryViewBean.getQuantity()) + "," +
			ATTRIBUTE_UNIT_PRICE + "=" + 
				StringHandler.nullIfZero(poLineHistoryViewBean.getUnitPrice()) + "," +
			ATTRIBUTE_CURRENCY_ID + "=" + 
				SqlHandler.delimitString(poLineHistoryViewBean.getCurrencyId()) + "," +
			ATTRIBUTE_NEED_DATE + "=" + 
				DateHandler.getOracleToDateFunction(poLineHistoryViewBean.getNeedDate()) + "," +
			ATTRIBUTE_PROMISED_DATE + "=" + 
				DateHandler.getOracleToDateFunction(poLineHistoryViewBean.getPromisedDate()) + "," +
			ATTRIBUTE_ALLOWED_PRICE_VARIANCE + "=" + 
				StringHandler.nullIfZero(poLineHistoryViewBean.getAllowedPriceVariance()) + "," +
			ATTRIBUTE_MFG_PART_NO + "=" + 
				SqlHandler.delimitString(poLineHistoryViewBean.getMfgPartNo()) + "," +
			ATTRIBUTE_SUPPLIER_PART_NO + "=" + 
				SqlHandler.delimitString(poLineHistoryViewBean.getSupplierPartNo()) + "," +
			ATTRIBUTE_DPAS_RATING + "=" + 
				SqlHandler.delimitString(poLineHistoryViewBean.getDpasRating()) + "," +
			ATTRIBUTE_QUALITY_FLOW_DOWNS + "=" + 
				SqlHandler.delimitString(poLineHistoryViewBean.getQualityFlowDowns()) + "," +
			ATTRIBUTE_PACKAGING_FLOW_DOWNS + "=" + 
				SqlHandler.delimitString(poLineHistoryViewBean.getPackagingFlowDowns()) + "," +
			ATTRIBUTE_PO_LINE_NOTE + "=" + 
				SqlHandler.delimitString(poLineHistoryViewBean.getPoLineNote()) + "," +
			ATTRIBUTE_TRANSACTION_DATE + "=" + 
				DateHandler.getOracleToDateFunction(poLineHistoryViewBean.getTransactionDate()) + "," +
			ATTRIBUTE_DATE_CONFIRMED + "=" + 
				DateHandler.getOracleToDateFunction(poLineHistoryViewBean.getDateConfirmed()) + "," +
			ATTRIBUTE_REMARKS + "=" + 
				SqlHandler.delimitString(poLineHistoryViewBean.getRemarks()) + "," +
			ATTRIBUTE_SUPPLIER_QTY + "=" + 
				StringHandler.nullIfZero(poLineHistoryViewBean.getSupplierQty()) + "," +
			ATTRIBUTE_SUPPLIER_PKG + "=" + 
				SqlHandler.delimitString(poLineHistoryViewBean.getSupplierPkg()) + "," +
			ATTRIBUTE_SUPPLIER_UNIT_PRICE + "=" + 
				StringHandler.nullIfZero(poLineHistoryViewBean.getSupplierUnitPrice()) + "," +
			ATTRIBUTE_GENERIC_COC + "=" + 
				SqlHandler.delimitString(poLineHistoryViewBean.getGenericCoc()) + "," +
			ATTRIBUTE_GENERIC_COA + "=" + 
				SqlHandler.delimitString(poLineHistoryViewBean.getGenericCoa()) + "," +
			ATTRIBUTE_MSDS_REQUESTED_DATE + "=" + 
				DateHandler.getOracleToDateFunction(poLineHistoryViewBean.getMsdsRequestedDate()) + "," +
			ATTRIBUTE_REMAINING_SHELF_LIFE_PERCENT + "=" + 
				StringHandler.nullIfZero(poLineHistoryViewBean.getRemainingShelfLifePercent()) + "," +
			ATTRIBUTE_DELIVERY_COMMENTS + "=" + 
				SqlHandler.delimitString(poLineHistoryViewBean.getDeliveryComments()) + "," +
			ATTRIBUTE_VENDOR_SHIP_DATE + "=" + 
				DateHandler.getOracleToDateFunction(poLineHistoryViewBean.getVendorShipDate()) + " " +
			"where " + ATTRIBUTE_RADIAN_PO + "=" +
				poLineHistoryViewBean.getRadianPo();

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

		Collection poLineHistoryViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			PoLineHistoryViewBean poLineHistoryViewBean = new PoLineHistoryViewBean();
			load(dataSetRow, poLineHistoryViewBean);
			poLineHistoryViewBeanColl.add(poLineHistoryViewBean);
		}

		return poLineHistoryViewBeanColl;
	}
}