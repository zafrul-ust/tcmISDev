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
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DataBeanCreationException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.util.*;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.internal.supply.beans.PoLineDraftBean;


/******************************************************************************
 * CLASSNAME: PoLineDraftBeanFactory <br>
 * @version: 1.0, Oct 8, 2008 <br>
 *****************************************************************************/


public class PoLineDraftBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_RADIAN_PO = "RADIAN_PO";
	public String ATTRIBUTE_PO_LINE = "PO_LINE";
	public String ATTRIBUTE_AMENDMENT = "AMENDMENT";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_QUANTITY = "QUANTITY";
	public String ATTRIBUTE_UNIT_PRICE = "UNIT_PRICE";
	public String ATTRIBUTE_NEED_DATE = "NEED_DATE";
	public String ATTRIBUTE_PROMISED_DATE = "PROMISED_DATE";
	public String ATTRIBUTE_ALLOWED_PRICE_VARIANCE = "ALLOWED_PRICE_VARIANCE";
	public String ATTRIBUTE_MFG_PART_NO = "MFG_PART_NO";
	public String ATTRIBUTE_SUPPLIER_PART_NO = "SUPPLIER_PART_NO";
	public String ATTRIBUTE_DPAS_RATING = "DPAS_RATING";
	public String ATTRIBUTE_QUALITY_FLOW_DOWNS = "QUALITY_FLOW_DOWNS";
	public String ATTRIBUTE_PACKAGING_FLOW_DOWNS = "PACKAGING_FLOW_DOWNS";
	public String ATTRIBUTE_PO_LINE_NOTE = "PO_LINE_NOTE";
	public String ATTRIBUTE_SUPPLIER_QTY = "SUPPLIER_QTY";
	public String ATTRIBUTE_SUPPLIER_PKG = "SUPPLIER_PKG";
	public String ATTRIBUTE_SUPPLIER_UNIT_PRICE = "SUPPLIER_UNIT_PRICE";
	public String ATTRIBUTE_GENERIC_COC = "GENERIC_COC";
	public String ATTRIBUTE_GENERIC_COA = "GENERIC_COA";
	public String ATTRIBUTE_MSDS_REQUESTED_DATE = "MSDS_REQUESTED_DATE";
	public String ATTRIBUTE_REMAINING_SHELF_LIFE_PERCENT = "REMAINING_SHELF_LIFE_PERCENT";
	public String ATTRIBUTE_DELIVERY_COMMENTS = "DELIVERY_COMMENTS";
	public String ATTRIBUTE_TRANSACTION_DATE = "TRANSACTION_DATE";
	public String ATTRIBUTE_VENDOR_SHIP_DATE = "VENDOR_SHIP_DATE";
	public String ATTRIBUTE_TRANSACTION_USER = "TRANSACTION_USER";
	public String ATTRIBUTE_CURRENCY_ID = "CURRENCY_ID";
	public String ATTRIBUTE_SUPPLIER = "SUPPLIER";
	public String ATTRIBUTE_PURCHASING_UNITS_PER_ITEM = "PURCHASING_UNITS_PER_ITEM";
	public String ATTRIBUTE_PURCHASING_UNIT_OF_MEASURE = "PURCHASING_UNIT_OF_MEASURE";
	public String ATTRIBUTE_SHIP_FROM_LOCATION_ID = "SHIP_FROM_LOCATION_ID";
	public String ATTRIBUTE_SUPPLIER_SALES_ORDER_NO = "SUPPLIER_SALES_ORDER_NO";

	//table name
	public String TABLE = "PO_LINE_DRAFT";


	//constructor
	public PoLineDraftBeanFactory(DbManager dbManager) {
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
		else if(attributeName.equals("transactionDate")) {
			return ATTRIBUTE_TRANSACTION_DATE;
		}
		else if(attributeName.equals("vendorShipDate")) {
			return ATTRIBUTE_VENDOR_SHIP_DATE;
		}
		else if(attributeName.equals("transactionUser")) {
			return ATTRIBUTE_TRANSACTION_USER;
		}
		else if(attributeName.equals("currencyId")) {
			return ATTRIBUTE_CURRENCY_ID;
		}
		else if(attributeName.equals("supplier")) {
			return ATTRIBUTE_SUPPLIER;
		}
		else if(attributeName.equals("purchasingUnitsPerItem")) {
			return ATTRIBUTE_PURCHASING_UNITS_PER_ITEM;
		}
		else if(attributeName.equals("purchasingUnitOfMeasure")) {
			return ATTRIBUTE_PURCHASING_UNIT_OF_MEASURE;
		}
		else if(attributeName.equals("shipFromLocationId")) {
			return ATTRIBUTE_SHIP_FROM_LOCATION_ID;
		}
		else if(attributeName.equals("supplierSalesOrderNo")) {
			return ATTRIBUTE_SUPPLIER_SALES_ORDER_NO;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, PoLineDraftBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(PoLineDraftBean poLineDraftBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("radianPo", "SearchCriterion.EQUALS",
			"" + poLineDraftBean.getRadianPo());

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


	public int delete(PoLineDraftBean poLineDraftBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("radianPo", "SearchCriterion.EQUALS",
			"" + poLineDraftBean.getRadianPo());

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

  //update
	public int updateSuppSalesOrderNo(PoLineDraftBean poLineDraftBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = updateSuppSalesOrderNo(poLineDraftBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int updateSuppSalesOrderNo(PoLineDraftBean poLineDraftBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_SUPPLIER_SALES_ORDER_NO + "=" +
				SqlHandler.delimitString(poLineDraftBean.getSupplierSalesOrderNo()) + " " +
			"where " + ATTRIBUTE_RADIAN_PO + "=" +
				poLineDraftBean.getRadianPo() +
      "and " + ATTRIBUTE_PO_LINE + "=" +
				poLineDraftBean.getPoLine();
		return new SqlManager().update(conn, query);
	}

  //update
	public int updateBuyOrderStatus(PoLineDraftBean poLineDraftBean,String status)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = updateBuyOrderStatus(poLineDraftBean,status, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int updateBuyOrderStatus(PoLineDraftBean poLineDraftBean,String status, Connection conn)
		throws BaseException {

		String query  = "update BUY_ORDER set STATUS =" +
				SqlHandler.delimitString(status) + " " +
			"where " + ATTRIBUTE_RADIAN_PO + "=" +
				poLineDraftBean.getRadianPo();
		return new SqlManager().update(conn, query);
	}

  //update call p_confirm_xbuy_to_airgas
	public void confirmXbuyOrder(PoLineDraftBean inputBean) throws BaseException {
	 Connection connection = this.getDbManager().getConnection();
	 Collection cin = new Vector();

	 cin.add(inputBean.getRadianPo());
	 cin.add(inputBean.getPoLine());

   this.getDbManager().doProcedure("p_confirm_xbuy_to_airgas",cin);
 	 this.getDbManager().returnConnection(connection);
 }

  //update call pkg_dbuy.sent_po_for_wbuy to keep track of date sent
	public void updateDateSent(PoLineDraftBean inputBean) throws BaseException {
	 Connection connection = this.getDbManager().getConnection();
	 Collection cin = new Vector();

	 cin.add(inputBean.getRadianPo());
	 Collection cout = new Vector();
   cout.add(new Integer(java.sql.Types.VARCHAR));

   this.getDbManager().doProcedure("pkg_dbuy.sent_po_for_wbuy",cin,cout);
 	 this.getDbManager().returnConnection(connection);
 }

/*  //update
	public int update(PoLineDraftBean poLineDraftBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(poLineDraftBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(PoLineDraftBean poLineDraftBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_RADIAN_PO + "=" + 
				StringHandler.nullIfZero(poLineDraftBean.getRadianPo()) + "," +
			ATTRIBUTE_PO_LINE + "=" + 
				StringHandler.nullIfZero(poLineDraftBean.getPoLine()) + "," +
			ATTRIBUTE_AMENDMENT + "=" + 
				StringHandler.nullIfZero(poLineDraftBean.getAmendment()) + "," +
			ATTRIBUTE_ITEM_ID + "=" + 
				StringHandler.nullIfZero(poLineDraftBean.getItemId()) + "," +
			ATTRIBUTE_QUANTITY + "=" + 
				StringHandler.nullIfZero(poLineDraftBean.getQuantity()) + "," +
			ATTRIBUTE_UNIT_PRICE + "=" + 
				StringHandler.nullIfZero(poLineDraftBean.getUnitPrice()) + "," +
			ATTRIBUTE_NEED_DATE + "=" + 
				DateHandler.getOracleToDateFunction(poLineDraftBean.getNeedDate()) + "," +
			ATTRIBUTE_PROMISED_DATE + "=" + 
				DateHandler.getOracleToDateFunction(poLineDraftBean.getPromisedDate()) + "," +
			ATTRIBUTE_ALLOWED_PRICE_VARIANCE + "=" + 
				StringHandler.nullIfZero(poLineDraftBean.getAllowedPriceVariance()) + "," +
			ATTRIBUTE_MFG_PART_NO + "=" + 
				SqlHandler.delimitString(poLineDraftBean.getMfgPartNo()) + "," +
			ATTRIBUTE_SUPPLIER_PART_NO + "=" + 
				SqlHandler.delimitString(poLineDraftBean.getSupplierPartNo()) + "," +
			ATTRIBUTE_DPAS_RATING + "=" + 
				SqlHandler.delimitString(poLineDraftBean.getDpasRating()) + "," +
			ATTRIBUTE_QUALITY_FLOW_DOWNS + "=" + 
				SqlHandler.delimitString(poLineDraftBean.getQualityFlowDowns()) + "," +
			ATTRIBUTE_PACKAGING_FLOW_DOWNS + "=" + 
				SqlHandler.delimitString(poLineDraftBean.getPackagingFlowDowns()) + "," +
			ATTRIBUTE_PO_LINE_NOTE + "=" + 
				SqlHandler.delimitString(poLineDraftBean.getPoLineNote()) + "," +
			ATTRIBUTE_SUPPLIER_QTY + "=" + 
				StringHandler.nullIfZero(poLineDraftBean.getSupplierQty()) + "," +
			ATTRIBUTE_SUPPLIER_PKG + "=" + 
				SqlHandler.delimitString(poLineDraftBean.getSupplierPkg()) + "," +
			ATTRIBUTE_SUPPLIER_UNIT_PRICE + "=" + 
				StringHandler.nullIfZero(poLineDraftBean.getSupplierUnitPrice()) + "," +
			ATTRIBUTE_GENERIC_COC + "=" + 
				SqlHandler.delimitString(poLineDraftBean.getGenericCoc()) + "," +
			ATTRIBUTE_GENERIC_COA + "=" + 
				SqlHandler.delimitString(poLineDraftBean.getGenericCoa()) + "," +
			ATTRIBUTE_MSDS_REQUESTED_DATE + "=" + 
				DateHandler.getOracleToDateFunction(poLineDraftBean.getMsdsRequestedDate()) + "," +
			ATTRIBUTE_REMAINING_SHELF_LIFE_PERCENT + "=" + 
				StringHandler.nullIfZero(poLineDraftBean.getRemainingShelfLifePercent()) + "," +
			ATTRIBUTE_DELIVERY_COMMENTS + "=" + 
				SqlHandler.delimitString(poLineDraftBean.getDeliveryComments()) + "," +
			ATTRIBUTE_TRANSACTION_DATE + "=" + 
				DateHandler.getOracleToDateFunction(poLineDraftBean.getTransactionDate()) + "," +
			ATTRIBUTE_VENDOR_SHIP_DATE + "=" + 
				DateHandler.getOracleToDateFunction(poLineDraftBean.getVendorShipDate()) + "," +
			ATTRIBUTE_TRANSACTION_USER + "=" + 
				StringHandler.nullIfZero(poLineDraftBean.getTransactionUser()) + "," +
			ATTRIBUTE_CURRENCY_ID + "=" + 
				SqlHandler.delimitString(poLineDraftBean.getCurrencyId()) + "," +
			ATTRIBUTE_SUPPLIER + "=" + 
				SqlHandler.delimitString(poLineDraftBean.getSupplier()) + "," +
			ATTRIBUTE_PURCHASING_UNITS_PER_ITEM + "=" + 
				StringHandler.nullIfZero(poLineDraftBean.getPurchasingUnitsPerItem()) + "," +
			ATTRIBUTE_PURCHASING_UNIT_OF_MEASURE + "=" + 
				SqlHandler.delimitString(poLineDraftBean.getPurchasingUnitOfMeasure()) + "," +
			ATTRIBUTE_SHIP_FROM_LOCATION_ID + "=" + 
				SqlHandler.delimitString(poLineDraftBean.getShipFromLocationId()) + "," +
			ATTRIBUTE_SUPPLIER_SALES_ORDER_NO + "=" + 
				SqlHandler.delimitString(poLineDraftBean.getSupplierSalesOrderNo()) + " " +
			"where " + ATTRIBUTE_RADIAN_PO + "=" +
				poLineDraftBean.getRadianPo();

		return new SqlManager().update(conn, query);
	}*/


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

		Collection poLineDraftBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			PoLineDraftBean poLineDraftBean = new PoLineDraftBean();
			load(dataSetRow, poLineDraftBean);
			poLineDraftBeanColl.add(poLineDraftBean);
		}

		return poLineDraftBeanColl;
	}
}