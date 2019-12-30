package com.tcmis.supplier.shipping.factory;


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
import com.tcmis.supplier.shipping.beans.VirtualHubSupplierBean;


/******************************************************************************
 * CLASSNAME: VirtualHubSupplierBeanFactory <br>
 * @version: 1.0, Aug 30, 2007 <br>
 *****************************************************************************/


public class VirtualHubSupplierBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_SUPPLIER = "SUPPLIER";
	public String ATTRIBUTE_CONTAINER_LABEL = "CONTAINER_LABEL";

	//table name
	public String TABLE = "VIRTUAL_HUB_SUPPLIER";


	//constructor
	public VirtualHubSupplierBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("supplier")) {
			return ATTRIBUTE_SUPPLIER;
		}
		else if(attributeName.equals("containerLabel")) {
			return ATTRIBUTE_CONTAINER_LABEL;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, VirtualHubSupplierBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(VirtualHubSupplierBean virtualHubSupplierBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("supplier", "SearchCriterion.EQUALS",
			"" + virtualHubSupplierBean.getSupplier());

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


	public int delete(VirtualHubSupplierBean virtualHubSupplierBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("supplier", "SearchCriterion.EQUALS",
			"" + virtualHubSupplierBean.getSupplier());

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
	public int insert(VirtualHubSupplierBean virtualHubSupplierBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(virtualHubSupplierBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(VirtualHubSupplierBean virtualHubSupplierBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_SUPPLIER + "," +
			ATTRIBUTE_CONTAINER_LABEL + ")" +
			" values (" +
			SqlHandler.delimitString(virtualHubSupplierBean.getSupplier()) + "," +
			SqlHandler.delimitString(virtualHubSupplierBean.getContainerLabel()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(VirtualHubSupplierBean virtualHubSupplierBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(virtualHubSupplierBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(VirtualHubSupplierBean virtualHubSupplierBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_SUPPLIER + "=" + 
				SqlHandler.delimitString(virtualHubSupplierBean.getSupplier()) + "," +
			ATTRIBUTE_CONTAINER_LABEL + "=" + 
				SqlHandler.delimitString(virtualHubSupplierBean.getContainerLabel()) + " " +
			"where " + ATTRIBUTE_SUPPLIER + "=" +
				virtualHubSupplierBean.getSupplier();

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

		Collection virtualHubSupplierBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			VirtualHubSupplierBean virtualHubSupplierBean = new VirtualHubSupplierBean();
			load(dataSetRow, virtualHubSupplierBean);
			virtualHubSupplierBeanColl.add(virtualHubSupplierBean);
		}

		return virtualHubSupplierBeanColl;
	}
}