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
import com.tcmis.internal.supply.beans.XbuyViewBean;


/******************************************************************************
 * CLASSNAME: XbuyViewBeanFactory <br>
 * @version: 1.0, Oct 8, 2008 <br>
 *****************************************************************************/


public class XbuyViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_RADIAN_PO = "RADIAN_PO";
	public String ATTRIBUTE_PO_LINE = "PO_LINE";
	public String ATTRIBUTE_QUANTITY = "QUANTITY";
	public String ATTRIBUTE_UNIT_PRICE = "UNIT_PRICE";
	public String ATTRIBUTE_UOM = "UOM";
	public String ATTRIBUTE_SUPPLIER_PART_NO = "SUPPLIER_PART_NO";
	public String ATTRIBUTE_VENDOR_SHIP_DATE = "VENDOR_SHIP_DATE";
	public String ATTRIBUTE_PROMISED_DATE = "PROMISED_DATE";
	public String ATTRIBUTE_SUPPLIER_ACCOUNT_NUMBER = "SUPPLIER_ACCOUNT_NUMBER";
	public String ATTRIBUTE_VMI_LOCATION_CODE = "VMI_LOCATION_CODE";
	public String ATTRIBUTE_SUPPLIER_LOCATION_CODE = "SUPPLIER_LOCATION_CODE";
	public String ATTRIBUTE_SUPPLIER_REGION_CODE = "SUPPLIER_REGION_CODE";
	public String ATTRIBUTE_ORIGIN_INSPECTION_REQUIRED = "ORIGIN_INSPECTION_REQUIRED";
	public String ATTRIBUTE_PRIORITY_RATING = "PRIORITY_RATING";
	public String ATTRIBUTE_DELIVERY_COMMENTS = "DELIVERY_COMMENTS";
	public String ATTRIBUTE_NOTES = "NOTES";
	public String ATTRIBUTE_ORIGINAL_TRANSACTION_TYPE = "ORIGINAL_TRANSACTION_TYPE";
	public String ATTRIBUTE_SHIP_TO_LOCATION_ID = "SHIP_TO_LOCATION_ID";

  //table name
	public String TABLE = "XBUY_VIEW";


	//constructor
	public XbuyViewBeanFactory(DbManager dbManager) {
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
		else if(attributeName.equals("quantity")) {
			return ATTRIBUTE_QUANTITY;
		}
		else if(attributeName.equals("unitPrice")) {
			return ATTRIBUTE_UNIT_PRICE;
		}
		else if(attributeName.equals("uom")) {
			return ATTRIBUTE_UOM;
		}
		else if(attributeName.equals("supplierPartNo")) {
			return ATTRIBUTE_SUPPLIER_PART_NO;
		}
		else if(attributeName.equals("vendorShipDate")) {
			return ATTRIBUTE_VENDOR_SHIP_DATE;
		}
		else if(attributeName.equals("promisedDate")) {
			return ATTRIBUTE_PROMISED_DATE;
		}
		else if(attributeName.equals("supplierAccountNumber")) {
			return ATTRIBUTE_SUPPLIER_ACCOUNT_NUMBER;
		}
		else if(attributeName.equals("vmiLocationCode")) {
			return ATTRIBUTE_VMI_LOCATION_CODE;
		}
		else if(attributeName.equals("supplierLocationCode")) {
			return ATTRIBUTE_SUPPLIER_LOCATION_CODE;
		}
		else if(attributeName.equals("supplierRegionCode")) {
			return ATTRIBUTE_SUPPLIER_REGION_CODE;
		}
		else if(attributeName.equals("originInspectionRequired")) {
			return ATTRIBUTE_ORIGIN_INSPECTION_REQUIRED;
		}
		else if(attributeName.equals("priorityRating")) {
			return ATTRIBUTE_PRIORITY_RATING;
		}
		else if(attributeName.equals("deliveryComments")) {
			return ATTRIBUTE_DELIVERY_COMMENTS;
		}
		else if(attributeName.equals("notes")) {
			return ATTRIBUTE_NOTES;
		}
		else if(attributeName.equals("originalTransactionType")) {
			return ATTRIBUTE_ORIGINAL_TRANSACTION_TYPE;
		}
		else if(attributeName.equals("shipToLocationId")) {
			return ATTRIBUTE_SHIP_TO_LOCATION_ID;
		}
    else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, XbuyViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(XbuyViewBean xbuyViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("radianPo", "SearchCriterion.EQUALS",
			"" + xbuyViewBean.getRadianPo());

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


	public int delete(XbuyViewBean xbuyViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("radianPo", "SearchCriterion.EQUALS",
			"" + xbuyViewBean.getRadianPo());

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
	public int insert(XbuyViewBean xbuyViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(xbuyViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(XbuyViewBean xbuyViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_RADIAN_PO + "," +
			ATTRIBUTE_PO_LINE + "," +
			ATTRIBUTE_QUANTITY + "," +
			ATTRIBUTE_UNIT_PRICE + "," +
			ATTRIBUTE_UOM + "," +
			ATTRIBUTE_SUPPLIER_PART_NO + "," +
			ATTRIBUTE_VENDOR_SHIP_DATE + "," +
			ATTRIBUTE_PROMISED_DATE + "," +
			ATTRIBUTE_SUPPLIER_ACCOUNT_NUMBER + "," +
			ATTRIBUTE_VMI_LOCATION_CODE + "," +
			ATTRIBUTE_SUPPLIER_LOCATION_CODE + "," +
			ATTRIBUTE_SUPPLIER_REGION_CODE + "," +
			ATTRIBUTE_ORIGIN_INSPECTION_REQUIRED + "," +
			ATTRIBUTE_PRIORITY_RATING + ")" +
			" values (" +
			xbuyViewBean.getRadianPo() + "," +
			xbuyViewBean.getPoLine() + "," +
			xbuyViewBean.getQuantity() + "," +
			xbuyViewBean.getUnitPrice() + "," +
			SqlHandler.delimitString(xbuyViewBean.getUom()) + "," +
			SqlHandler.delimitString(xbuyViewBean.getSupplierPartNo()) + "," +
			DateHandler.getOracleToDateFunction(xbuyViewBean.getVendorShipDate()) + "," +
			DateHandler.getOracleToDateFunction(xbuyViewBean.getPromisedDate()) + "," +
			SqlHandler.delimitString(xbuyViewBean.getSupplierAccountNumber()) + "," +
			SqlHandler.delimitString(xbuyViewBean.getVmiLocationCode()) + "," +
			SqlHandler.delimitString(xbuyViewBean.getSupplierLocationCode()) + "," +
			SqlHandler.delimitString(xbuyViewBean.getSupplierRegionCode()) + "," +
			SqlHandler.delimitString(xbuyViewBean.getOriginInspectionRequired()) + "," +
			xbuyViewBean.getPriorityRating() + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(XbuyViewBean xbuyViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(xbuyViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(XbuyViewBean xbuyViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_RADIAN_PO + "=" + 
				StringHandler.nullIfZero(xbuyViewBean.getRadianPo()) + "," +
			ATTRIBUTE_PO_LINE + "=" + 
				StringHandler.nullIfZero(xbuyViewBean.getPoLine()) + "," +
			ATTRIBUTE_QUANTITY + "=" + 
				StringHandler.nullIfZero(xbuyViewBean.getQuantity()) + "," +
			ATTRIBUTE_UNIT_PRICE + "=" + 
				StringHandler.nullIfZero(xbuyViewBean.getUnitPrice()) + "," +
			ATTRIBUTE_UOM + "=" + 
				SqlHandler.delimitString(xbuyViewBean.getUom()) + "," +
			ATTRIBUTE_SUPPLIER_PART_NO + "=" + 
				SqlHandler.delimitString(xbuyViewBean.getSupplierPartNo()) + "," +
			ATTRIBUTE_VENDOR_SHIP_DATE + "=" + 
				DateHandler.getOracleToDateFunction(xbuyViewBean.getVendorShipDate()) + "," +
			ATTRIBUTE_PROMISED_DATE + "=" + 
				DateHandler.getOracleToDateFunction(xbuyViewBean.getPromisedDate()) + "," +
			ATTRIBUTE_SUPPLIER_ACCOUNT_NUMBER + "=" + 
				SqlHandler.delimitString(xbuyViewBean.getSupplierAccountNumber()) + "," +
			ATTRIBUTE_VMI_LOCATION_CODE + "=" + 
				SqlHandler.delimitString(xbuyViewBean.getVmiLocationCode()) + "," +
			ATTRIBUTE_SUPPLIER_LOCATION_CODE + "=" + 
				SqlHandler.delimitString(xbuyViewBean.getSupplierLocationCode()) + "," +
			ATTRIBUTE_SUPPLIER_REGION_CODE + "=" + 
				SqlHandler.delimitString(xbuyViewBean.getSupplierRegionCode()) + "," +
			ATTRIBUTE_ORIGIN_INSPECTION_REQUIRED + "=" + 
				SqlHandler.delimitString(xbuyViewBean.getOriginInspectionRequired()) + "," +
			ATTRIBUTE_PRIORITY_RATING + "=" + 
				StringHandler.nullIfZero(xbuyViewBean.getPriorityRating()) + " " +
			"where " + ATTRIBUTE_RADIAN_PO + "=" +
				xbuyViewBean.getRadianPo();

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

		Collection xbuyViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			XbuyViewBean xbuyViewBean = new XbuyViewBean();
			load(dataSetRow, xbuyViewBean);
			xbuyViewBeanColl.add(xbuyViewBean);
		}

		return xbuyViewBeanColl;
	}
}