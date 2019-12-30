package com.tcmis.internal.accountspayable.factory;


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
import com.tcmis.internal.accountspayable.beans.SupplierBillingLocationBean;


/******************************************************************************
 * CLASSNAME: SupplierBillingLocationBeanFactory <br>
 * @version: 1.0, Aug 30, 2007 <br>
 *****************************************************************************/


public class SupplierBillingLocationBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_SUPPLIER = "SUPPLIER";
	public String ATTRIBUTE_BILLING_LOCATION_ID = "BILLING_LOCATION_ID";
	public String ATTRIBUTE_LOCATION_KEY = "LOCATION_KEY";
	public String ATTRIBUTE_E_SUPPLIER_ID = "E_SUPPLIER_ID";
	public String ATTRIBUTE_E_REMIT_TO_ID = "E_REMIT_TO_ID";
	public String ATTRIBUTE_COMMENTS = "COMMENTS";
	public String ATTRIBUTE_BILL_ADDRESS_VERFIED_WITH_E = "BILL_ADDRESS_VERFIED_WITH_E";
	public String ATTRIBUTE_SAME_AS_VENDOR = "SAME_AS_VENDOR";
	public String ATTRIBUTE_T_SUPPLIER_E_SUPPLIER_ADD_SAME = "T_SUPPLIER_E_SUPPLIER_ADD_SAME";
	public String ATTRIBUTE_NOT_USED1 = "NOT_USED1";
	public String ATTRIBUTE_NOT_USED2 = "NOT_USED2";
	public String ATTRIBUTE_VOUCHERED_ADDRESS = "VOUCHERED_ADDRESS";

	//table name
	public String TABLE = "SUPPLIER_BILLING_LOCATION";


	//constructor
	public SupplierBillingLocationBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("supplier")) {
			return ATTRIBUTE_SUPPLIER;
		}
		else if(attributeName.equals("billingLocationId")) {
			return ATTRIBUTE_BILLING_LOCATION_ID;
		}
		else if(attributeName.equals("locationKey")) {
			return ATTRIBUTE_LOCATION_KEY;
		}
		else if(attributeName.equals("eSupplierId")) {
			return ATTRIBUTE_E_SUPPLIER_ID;
		}
		else if(attributeName.equals("eRemitToId")) {
			return ATTRIBUTE_E_REMIT_TO_ID;
		}
		else if(attributeName.equals("comments")) {
			return ATTRIBUTE_COMMENTS;
		}
		else if(attributeName.equals("billAddressVerfiedWithEe")) {
			return ATTRIBUTE_BILL_ADDRESS_VERFIED_WITH_E;
		}
		else if(attributeName.equals("sameAsVendor")) {
			return ATTRIBUTE_SAME_AS_VENDOR;
		}
		else if(attributeName.equals("tSupplierEeSupplierAddSame")) {
			return ATTRIBUTE_T_SUPPLIER_E_SUPPLIER_ADD_SAME;
		}
		else if(attributeName.equals("notUsed1")) {
			return ATTRIBUTE_NOT_USED1;
		}
		else if(attributeName.equals("notUsed2")) {
			return ATTRIBUTE_NOT_USED2;
		}
		else if(attributeName.equals("voucheredAddress")) {
			return ATTRIBUTE_VOUCHERED_ADDRESS;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, SupplierBillingLocationBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(SupplierBillingLocationBean supplierBillingLocationBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("supplier", "SearchCriterion.EQUALS",
			"" + supplierBillingLocationBean.getSupplier());

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


	public int delete(SupplierBillingLocationBean supplierBillingLocationBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("supplier", "SearchCriterion.EQUALS",
			"" + supplierBillingLocationBean.getSupplier());

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
	public int insert(SupplierBillingLocationBean supplierBillingLocationBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(supplierBillingLocationBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(SupplierBillingLocationBean supplierBillingLocationBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_SUPPLIER + "," +
			ATTRIBUTE_BILLING_LOCATION_ID + "," +
			ATTRIBUTE_LOCATION_KEY + "," +
			ATTRIBUTE_E_SUPPLIER_ID + "," +
			ATTRIBUTE_E_REMIT_TO_ID + "," +
			ATTRIBUTE_COMMENTS + "," +
			ATTRIBUTE_BILL_ADDRESS_VERFIED_WITH_E + "," +
			ATTRIBUTE_SAME_AS_VENDOR + "," +
			ATTRIBUTE_T_SUPPLIER_E_SUPPLIER_ADD_SAME + "," +
			ATTRIBUTE_NOT_USED1 + "," +
			ATTRIBUTE_NOT_USED2 + "," +
			ATTRIBUTE_VOUCHERED_ADDRESS + ")" +
			" values (" +
			SqlHandler.delimitString(supplierBillingLocationBean.getSupplier()) + "," +
			SqlHandler.delimitString(supplierBillingLocationBean.getBillingLocationId()) + "," +
			SqlHandler.delimitString(supplierBillingLocationBean.getLocationKey()) + "," +
			SqlHandler.delimitString(supplierBillingLocationBean.getESupplierId()) + "," +
			SqlHandler.delimitString(supplierBillingLocationBean.getERemitToId()) + "," +
			SqlHandler.delimitString(supplierBillingLocationBean.getComments()) + "," +
			SqlHandler.delimitString(supplierBillingLocationBean.getBillAddressVerfiedWithE()) + "," +
			SqlHandler.delimitString(supplierBillingLocationBean.getSameAsVendor()) + "," +
			SqlHandler.delimitString(supplierBillingLocationBean.getTSupplierESupplierAddSame()) + "," +
			SqlHandler.delimitString(supplierBillingLocationBean.getNotUsed1()) + "," +
			SqlHandler.delimitString(supplierBillingLocationBean.getNotUsed2()) + "," +
			SqlHandler.delimitString(supplierBillingLocationBean.getVoucheredAddress()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(SupplierBillingLocationBean supplierBillingLocationBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(supplierBillingLocationBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(SupplierBillingLocationBean supplierBillingLocationBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_SUPPLIER + "=" + 
				SqlHandler.delimitString(supplierBillingLocationBean.getSupplier()) + "," +
			ATTRIBUTE_BILLING_LOCATION_ID + "=" + 
				SqlHandler.delimitString(supplierBillingLocationBean.getBillingLocationId()) + "," +
			ATTRIBUTE_LOCATION_KEY + "=" + 
				SqlHandler.delimitString(supplierBillingLocationBean.getLocationKey()) + "," +
			ATTRIBUTE_E_SUPPLIER_ID + "=" + 
				SqlHandler.delimitString(supplierBillingLocationBean.getESupplierId()) + "," +
			ATTRIBUTE_E_REMIT_TO_ID + "=" + 
				SqlHandler.delimitString(supplierBillingLocationBean.getERemitToId()) + "," +
			ATTRIBUTE_COMMENTS + "=" + 
				SqlHandler.delimitString(supplierBillingLocationBean.getComments()) + "," +
			ATTRIBUTE_BILL_ADDRESS_VERFIED_WITH_E + "=" + 
				SqlHandler.delimitString(supplierBillingLocationBean.getBillAddressVerfiedWithE()) + "," +
			ATTRIBUTE_SAME_AS_VENDOR + "=" + 
				SqlHandler.delimitString(supplierBillingLocationBean.getSameAsVendor()) + "," +
			ATTRIBUTE_T_SUPPLIER_E_SUPPLIER_ADD_SAME + "=" + 
				SqlHandler.delimitString(supplierBillingLocationBean.getTSupplierESupplierAddSame()) + "," +
			ATTRIBUTE_NOT_USED1 + "=" + 
				SqlHandler.delimitString(supplierBillingLocationBean.getNotUsed1()) + "," +
			ATTRIBUTE_NOT_USED2 + "=" + 
				SqlHandler.delimitString(supplierBillingLocationBean.getNotUsed2()) + "," +
			ATTRIBUTE_VOUCHERED_ADDRESS + "=" + 
				SqlHandler.delimitString(supplierBillingLocationBean.getVoucheredAddress()) + " " +
			"where " + ATTRIBUTE_SUPPLIER + "=" +
				supplierBillingLocationBean.getSupplier();

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

		Collection supplierBillingLocationBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			SupplierBillingLocationBean supplierBillingLocationBean = new SupplierBillingLocationBean();
			load(dataSetRow, supplierBillingLocationBean);
			supplierBillingLocationBeanColl.add(supplierBillingLocationBean);
		}

		return supplierBillingLocationBeanColl;
	}
}