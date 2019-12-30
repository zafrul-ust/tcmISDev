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
import com.tcmis.supplier.dbuy.beans.DbuySupplierBean;


/******************************************************************************
 * CLASSNAME: DbuySupplierBeanFactory <br>
 * @version: 1.0, May 3, 2006 <br>
 *****************************************************************************/


public class DbuySupplierBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_SUPPLIER_NAME = "SUPPLIER_NAME";
	public String ATTRIBUTE_SUPPLIER = "SUPPLIER";

	//table name
	public String TABLE = "DBUY_SUPPLIER";


	//constructor
	public DbuySupplierBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("supplierName")) {
			return ATTRIBUTE_SUPPLIER_NAME;
		}
		else if(attributeName.equals("supplier")) {
			return ATTRIBUTE_SUPPLIER;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, DbuySupplierBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(DbuySupplierBean dbuySupplierBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("supplierName", "SearchCriterion.EQUALS",
			"" + dbuySupplierBean.getSupplierName());

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


	public int delete(DbuySupplierBean dbuySupplierBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("supplierName", "SearchCriterion.EQUALS",
			"" + dbuySupplierBean.getSupplierName());

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
	public int insert(DbuySupplierBean dbuySupplierBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(dbuySupplierBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(DbuySupplierBean dbuySupplierBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_SUPPLIER_NAME + "," +
			ATTRIBUTE_SUPPLIER + ")" +
 values (
			SqlHandler.delimitString(dbuySupplierBean.getSupplierName()) + "," +
			SqlHandler.delimitString(dbuySupplierBean.getSupplier()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(DbuySupplierBean dbuySupplierBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(dbuySupplierBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(DbuySupplierBean dbuySupplierBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_SUPPLIER_NAME + "=" + 
				SqlHandler.delimitString(dbuySupplierBean.getSupplierName()) + "," +
			ATTRIBUTE_SUPPLIER + "=" + 
				SqlHandler.delimitString(dbuySupplierBean.getSupplier()) + " " +
			"where " + ATTRIBUTE_SUPPLIER_NAME + "=" +
				StringHandler.nullIfZero(dbuySupplierBean.getSupplierName());

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

		Collection dbuySupplierBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			DbuySupplierBean dbuySupplierBean = new DbuySupplierBean();
			load(dataSetRow, dbuySupplierBean);
			dbuySupplierBeanColl.add(dbuySupplierBean);
		}

		return dbuySupplierBeanColl;
	}
        
        public Collection select() 
                throws BaseException {
                   
 
		Connection connection = null;
		Collection dbuySupplierBeanColl = new Vector();
		try {
			connection = this.getDbManager().getConnection();
                        String query = "select distinct y.supplier_name,x.supplier from dbuy_contract x, supplier y where x.supplier = y.supplier and x.status = 'DBUY'";
                        
                        DataSet dataSet = new SqlManager().select(connection, query);

                        Iterator dataIter = dataSet.iterator();

                        while (dataIter.hasNext()) {
                                DataSetRow dataSetRow = (DataSetRow)dataIter.next();
                                DbuySupplierBean dbuySupplierBean = new DbuySupplierBean();
                                load(dataSetRow, dbuySupplierBean);
                                dbuySupplierBeanColl.add(dbuySupplierBean);
                        }
                        
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return dbuySupplierBeanColl;
                  
        }
}