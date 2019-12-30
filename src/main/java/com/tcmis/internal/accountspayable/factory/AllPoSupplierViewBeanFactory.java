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
import com.tcmis.internal.accountspayable.beans.AllPoSupplierViewBean;


/******************************************************************************
 * CLASSNAME: AllPoSupplierViewBeanFactory <br>
 * @version: 1.0, Aug 30, 2007 <br>
 *****************************************************************************/


public class AllPoSupplierViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_SUPPLIER = "SUPPLIER";
	public String ATTRIBUTE_SUPPLIER_NAME = "SUPPLIER_NAME";
	public String ATTRIBUTE_RADIAN_PO = "RADIAN_PO";

	//table name
	public String TABLE = "ALL_PO_SUPPLIER_VIEW";


	//constructor
	public AllPoSupplierViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("supplier")) {
			return ATTRIBUTE_SUPPLIER;
		}
		else if(attributeName.equals("supplierName")) {
			return ATTRIBUTE_SUPPLIER_NAME;
		}
		else if(attributeName.equals("radianPo")) {
			return ATTRIBUTE_RADIAN_PO;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, AllPoSupplierViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(AllPoSupplierViewBean allPoSupplierViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("supplier", "SearchCriterion.EQUALS",
			"" + allPoSupplierViewBean.getSupplier());

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


	public int delete(AllPoSupplierViewBean allPoSupplierViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("supplier", "SearchCriterion.EQUALS",
			"" + allPoSupplierViewBean.getSupplier());

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
	public int insert(AllPoSupplierViewBean allPoSupplierViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(allPoSupplierViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(AllPoSupplierViewBean allPoSupplierViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_SUPPLIER + "," +
			ATTRIBUTE_SUPPLIER_NAME + "," +
			ATTRIBUTE_RADIAN_PO + ")" +
			" values (" +
			SqlHandler.delimitString(allPoSupplierViewBean.getSupplier()) + "," +
			SqlHandler.delimitString(allPoSupplierViewBean.getSupplierName()) + "," +
			allPoSupplierViewBean.getRadianPo() + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(AllPoSupplierViewBean allPoSupplierViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(allPoSupplierViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(AllPoSupplierViewBean allPoSupplierViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_SUPPLIER + "=" + 
				SqlHandler.delimitString(allPoSupplierViewBean.getSupplier()) + "," +
			ATTRIBUTE_SUPPLIER_NAME + "=" + 
				SqlHandler.delimitString(allPoSupplierViewBean.getSupplierName()) + "," +
			ATTRIBUTE_RADIAN_PO + "=" + 
				StringHandler.nullIfZero(allPoSupplierViewBean.getRadianPo()) + " " +
			"where " + ATTRIBUTE_SUPPLIER + "=" +
				allPoSupplierViewBean.getSupplier();

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

		Collection allPoSupplierViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			AllPoSupplierViewBean allPoSupplierViewBean = new AllPoSupplierViewBean();
			load(dataSetRow, allPoSupplierViewBean);
			allPoSupplierViewBeanColl.add(allPoSupplierViewBean);
		}

		return allPoSupplierViewBeanColl;
	}
}