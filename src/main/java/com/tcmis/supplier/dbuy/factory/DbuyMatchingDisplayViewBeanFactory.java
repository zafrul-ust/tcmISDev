package com.tcmis.supplier.dbuy.factory;


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
import com.tcmis.supplier.dbuy.beans.DbuyMatchingDisplayViewBean;


/******************************************************************************
 * CLASSNAME: DbuyMatchingDisplayViewBeanFactory <br>
 * @version: 1.0, Apr 26, 2006 <br>
 *****************************************************************************/


public class DbuyMatchingDisplayViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_RADIAN_PO = "RADIAN_PO";
	public String ATTRIBUTE_PO_LINE = "PO_LINE";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_ITEM_SHORT_DESC = "ITEM_SHORT_DESC";
	public String ATTRIBUTE_SUPPLIER_NAME = "SUPPLIER_NAME";
	public String ATTRIBUTE_QUANTITY = "QUANTITY";
	public String ATTRIBUTE_UNIT_PRICE = "UNIT_PRICE";
	public String ATTRIBUTE_NEED_BY_DATE = "NEED_BY_DATE";
	public String ATTRIBUTE_SHIPTO_ADDRESS_LINE_1 = "SHIPTO_ADDRESS_LINE_1";
	public String ATTRIBUTE_SHIPTO_ADDRESS_LINE_2 = "SHIPTO_ADDRESS_LINE_2";
	public String ATTRIBUTE_SHIPTO_CITY = "SHIPTO_CITY";
	public String ATTRIBUTE_SHIPTO_ZIP = "SHIPTO_ZIP";

	//table name
	public String TABLE = "DBUY_MATCHING_DISPLAY_VIEW";


	//constructor
	public DbuyMatchingDisplayViewBeanFactory(DbManager dbManager) {
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
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("itemShortDesc")) {
			return ATTRIBUTE_ITEM_SHORT_DESC;
		}
		else if(attributeName.equals("supplierName")) {
			return ATTRIBUTE_SUPPLIER_NAME;
		}
		else if(attributeName.equals("quantity")) {
			return ATTRIBUTE_QUANTITY;
		}
		else if(attributeName.equals("unitPrice")) {
			return ATTRIBUTE_UNIT_PRICE;
		}
		else if(attributeName.equals("needByDate")) {
			return ATTRIBUTE_NEED_BY_DATE;
		}
		else if(attributeName.equals("shiptoAddressLine1")) {
			return ATTRIBUTE_SHIPTO_ADDRESS_LINE_1;
		}
		else if(attributeName.equals("shiptoAddressLine2")) {
			return ATTRIBUTE_SHIPTO_ADDRESS_LINE_2;
		}
		else if(attributeName.equals("shiptoCity")) {
			return ATTRIBUTE_SHIPTO_CITY;
		}
		else if(attributeName.equals("shiptoZip")) {
			return ATTRIBUTE_SHIPTO_ZIP;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, DbuyMatchingDisplayViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(DbuyMatchingDisplayViewBean dbuyMatchingDisplayViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("radianPo", "SearchCriterion.EQUALS",
			"" + dbuyMatchingDisplayViewBean.getRadianPo());

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


	public int delete(DbuyMatchingDisplayViewBean dbuyMatchingDisplayViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("radianPo", "SearchCriterion.EQUALS",
			"" + dbuyMatchingDisplayViewBean.getRadianPo());

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
	public int insert(DbuyMatchingDisplayViewBean dbuyMatchingDisplayViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(dbuyMatchingDisplayViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(DbuyMatchingDisplayViewBean dbuyMatchingDisplayViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_RADIAN_PO + "," +
			ATTRIBUTE_PO_LINE + "," +
			ATTRIBUTE_ITEM_ID + "," +
			ATTRIBUTE_ITEM_SHORT_DESC + "," +
			ATTRIBUTE_SUPPLIER_NAME + "," +
			ATTRIBUTE_QUANTITY + "," +
			ATTRIBUTE_UNIT_PRICE + "," +
			ATTRIBUTE_NEED_BY_DATE + "," +
			ATTRIBUTE_SHIPTO_ADDRESS_LINE_1 + "," +
			ATTRIBUTE_SHIPTO_ADDRESS_LINE_2 + "," +
			ATTRIBUTE_SHIPTO_CITY + "," +
			ATTRIBUTE_SHIPTO_ZIP + ")" +
 values (
			StringHandler.nullIfZero(dbuyMatchingDisplayViewBean.getRadianPo()) + "," +
			StringHandler.nullIfZero(dbuyMatchingDisplayViewBean.getPoLine()) + "," +
			StringHandler.nullIfZero(dbuyMatchingDisplayViewBean.getItemId()) + "," +
			SqlHandler.delimitString(dbuyMatchingDisplayViewBean.getItemShortDesc()) + "," +
			SqlHandler.delimitString(dbuyMatchingDisplayViewBean.getSupplierName()) + "," +
			StringHandler.nullIfZero(dbuyMatchingDisplayViewBean.getQuantity()) + "," +
			StringHandler.nullIfZero(dbuyMatchingDisplayViewBean.getUnitPrice()) + "," +
			DateHandler.getOracleToDateFunction(dbuyMatchingDisplayViewBean.getNeedByDate()) + "," +
			SqlHandler.delimitString(dbuyMatchingDisplayViewBean.getShiptoAddressLine1()) + "," +
			SqlHandler.delimitString(dbuyMatchingDisplayViewBean.getShiptoAddressLine2()) + "," +
			SqlHandler.delimitString(dbuyMatchingDisplayViewBean.getShiptoCity()) + "," +
			SqlHandler.delimitString(dbuyMatchingDisplayViewBean.getShiptoZip()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(DbuyMatchingDisplayViewBean dbuyMatchingDisplayViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(dbuyMatchingDisplayViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(DbuyMatchingDisplayViewBean dbuyMatchingDisplayViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_RADIAN_PO + "=" + 
				StringHandler.nullIfZero(dbuyMatchingDisplayViewBean.getRadianPo()) + "," +
			ATTRIBUTE_PO_LINE + "=" + 
				StringHandler.nullIfZero(dbuyMatchingDisplayViewBean.getPoLine()) + "," +
			ATTRIBUTE_ITEM_ID + "=" + 
				StringHandler.nullIfZero(dbuyMatchingDisplayViewBean.getItemId()) + "," +
			ATTRIBUTE_ITEM_SHORT_DESC + "=" + 
				SqlHandler.delimitString(dbuyMatchingDisplayViewBean.getItemShortDesc()) + "," +
			ATTRIBUTE_SUPPLIER_NAME + "=" + 
				SqlHandler.delimitString(dbuyMatchingDisplayViewBean.getSupplierName()) + "," +
			ATTRIBUTE_QUANTITY + "=" + 
				StringHandler.nullIfZero(dbuyMatchingDisplayViewBean.getQuantity()) + "," +
			ATTRIBUTE_UNIT_PRICE + "=" + 
				StringHandler.nullIfZero(dbuyMatchingDisplayViewBean.getUnitPrice()) + "," +
			ATTRIBUTE_NEED_BY_DATE + "=" + 
				DateHandler.getOracleToDateFunction(dbuyMatchingDisplayViewBean.getNeedByDate()) + "," +
			ATTRIBUTE_SHIPTO_ADDRESS_LINE_1 + "=" + 
				SqlHandler.delimitString(dbuyMatchingDisplayViewBean.getShiptoAddressLine1()) + "," +
			ATTRIBUTE_SHIPTO_ADDRESS_LINE_2 + "=" + 
				SqlHandler.delimitString(dbuyMatchingDisplayViewBean.getShiptoAddressLine2()) + "," +
			ATTRIBUTE_SHIPTO_CITY + "=" + 
				SqlHandler.delimitString(dbuyMatchingDisplayViewBean.getShiptoCity()) + "," +
			ATTRIBUTE_SHIPTO_ZIP + "=" + 
				SqlHandler.delimitString(dbuyMatchingDisplayViewBean.getShiptoZip()) + " " +
			"where " + ATTRIBUTE_RADIAN_PO + "=" +
				StringHandler.nullIfZero(dbuyMatchingDisplayViewBean.getRadianPo());

		return new SqlManager().update(conn, query);
	}
*/

	//select
	public Collection select(SearchCriteria criteria)
		throws BaseException {

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
	public Collection select(SearchCriteria criteria, Connection conn)
		throws BaseException {

		Collection dbuyMatchingDisplayViewBeanColl = new Vector();

		String query = "select distinct * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			DbuyMatchingDisplayViewBean dbuyMatchingDisplayViewBean = new DbuyMatchingDisplayViewBean();
			load(dataSetRow, dbuyMatchingDisplayViewBean);
			dbuyMatchingDisplayViewBeanColl.add(dbuyMatchingDisplayViewBean);
		}

		return dbuyMatchingDisplayViewBeanColl;
	}
}