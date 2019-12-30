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
import com.tcmis.supplier.dbuy.beans.DbuyStatusViewBean;


/******************************************************************************
 * CLASSNAME: DbuyStatusViewBeanFactory <br>
 * @version: 1.0, Jan 29, 2007 <br>
 *****************************************************************************/


public class DbuyStatusViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
	public String ATTRIBUTE_RADIAN_PO = "RADIAN_PO";
	public String ATTRIBUTE_DATE_ACKNOWLEDGEMENT = "DATE_ACKNOWLEDGEMENT";
	public String ATTRIBUTE_DATE_SENT = "DATE_SENT";
	public String ATTRIBUTE_DATE_CONFIRMED = "DATE_CONFIRMED";
	public String ATTRIBUTE_TRANSACTOR_MAIL_BOX_ADDRESS = "TRANSACTOR_MAIL_BOX_ADDRESS";
	public String ATTRIBUTE_TRANSACTOR_ID = "TRANSACTOR_ID";
	public String ATTRIBUTE_SUPPLIER = "SUPPLIER";
	public String ATTRIBUTE_SUPPLIER_NAME = "SUPPLIER_NAME";

	//table name
	public String TABLE = "DBUY_STATUS_VIEW";


	//constructor
	public DbuyStatusViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("prNumber")) {
			return ATTRIBUTE_PR_NUMBER;
		}
		else if(attributeName.equals("radianPo")) {
			return ATTRIBUTE_RADIAN_PO;
		}
		else if(attributeName.equals("dateAcknowledgement")) {
			return ATTRIBUTE_DATE_ACKNOWLEDGEMENT;
		}
		else if(attributeName.equals("dateSent")) {
			return ATTRIBUTE_DATE_SENT;
		}
		else if(attributeName.equals("dateConfirmed")) {
			return ATTRIBUTE_DATE_CONFIRMED;
		}
		else if(attributeName.equals("transactorMailBoxAddress")) {
			return ATTRIBUTE_TRANSACTOR_MAIL_BOX_ADDRESS;
		}
		else if(attributeName.equals("transactorId")) {
			return ATTRIBUTE_TRANSACTOR_ID;
		}
		else if(attributeName.equals("supplier")) {
			return ATTRIBUTE_SUPPLIER;
		}
		else if(attributeName.equals("supplierName")) {
			return ATTRIBUTE_SUPPLIER_NAME;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, DbuyStatusViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(DbuyStatusViewBean dbuyStatusViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("prNumber", "SearchCriterion.EQUALS",
			"" + dbuyStatusViewBean.getPrNumber());

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


	public int delete(DbuyStatusViewBean dbuyStatusViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("prNumber", "SearchCriterion.EQUALS",
			"" + dbuyStatusViewBean.getPrNumber());

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
	public int insert(DbuyStatusViewBean dbuyStatusViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(dbuyStatusViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(DbuyStatusViewBean dbuyStatusViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_PR_NUMBER + "," +
			ATTRIBUTE_RADIAN_PO + "," +
			ATTRIBUTE_DATE_ACKNOWLEDGEMENT + "," +
			ATTRIBUTE_DATE_SENT + "," +
			ATTRIBUTE_DATE_CONFIRMED + "," +
			ATTRIBUTE_TRANSACTOR_MAIL_BOX_ADDRESS + "," +
			ATTRIBUTE_TRANSACTOR_ID + "," +
			ATTRIBUTE_SUPPLIER + "," +
			ATTRIBUTE_SUPPLIER_NAME + ")" +
 values (
			StringHandler.nullIfZero(dbuyStatusViewBean.getPrNumber()) + "," +
			StringHandler.nullIfZero(dbuyStatusViewBean.getRadianPo()) + "," +
			DateHandler.getOracleToDateFunction(dbuyStatusViewBean.getDateAcknowledgement()) + "," +
			DateHandler.getOracleToDateFunction(dbuyStatusViewBean.getDateSent()) + "," +
			DateHandler.getOracleToDateFunction(dbuyStatusViewBean.getDateConfirmed()) + "," +
			SqlHandler.delimitString(dbuyStatusViewBean.getTransactorMailBoxAddress()) + "," +
			SqlHandler.delimitString(dbuyStatusViewBean.getTransactorId()) + "," +
			SqlHandler.delimitString(dbuyStatusViewBean.getSupplier()) + "," +
			SqlHandler.delimitString(dbuyStatusViewBean.getSupplierName()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(DbuyStatusViewBean dbuyStatusViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(dbuyStatusViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(DbuyStatusViewBean dbuyStatusViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_PR_NUMBER + "=" + 
				StringHandler.nullIfZero(dbuyStatusViewBean.getPrNumber()) + "," +
			ATTRIBUTE_RADIAN_PO + "=" + 
				StringHandler.nullIfZero(dbuyStatusViewBean.getRadianPo()) + "," +
			ATTRIBUTE_DATE_ACKNOWLEDGEMENT + "=" + 
				DateHandler.getOracleToDateFunction(dbuyStatusViewBean.getDateAcknowledgement()) + "," +
			ATTRIBUTE_DATE_SENT + "=" + 
				DateHandler.getOracleToDateFunction(dbuyStatusViewBean.getDateSent()) + "," +
			ATTRIBUTE_DATE_CONFIRMED + "=" + 
				DateHandler.getOracleToDateFunction(dbuyStatusViewBean.getDateConfirmed()) + "," +
			ATTRIBUTE_TRANSACTOR_MAIL_BOX_ADDRESS + "=" + 
				SqlHandler.delimitString(dbuyStatusViewBean.getTransactorMailBoxAddress()) + "," +
			ATTRIBUTE_TRANSACTOR_ID + "=" + 
				SqlHandler.delimitString(dbuyStatusViewBean.getTransactorId()) + "," +
			ATTRIBUTE_SUPPLIER + "=" + 
				SqlHandler.delimitString(dbuyStatusViewBean.getSupplier()) + "," +
			ATTRIBUTE_SUPPLIER_NAME + "=" + 
				SqlHandler.delimitString(dbuyStatusViewBean.getSupplierName()) + " " +
			"where " + ATTRIBUTE_PR_NUMBER + "=" +
				StringHandler.nullIfZero(dbuyStatusViewBean.getPrNumber());

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

		Collection dbuyStatusViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			DbuyStatusViewBean dbuyStatusViewBean = new DbuyStatusViewBean();
			load(dataSetRow, dbuyStatusViewBean);
			dbuyStatusViewBeanColl.add(dbuyStatusViewBean);
		}

		return dbuyStatusViewBeanColl;
	}
}