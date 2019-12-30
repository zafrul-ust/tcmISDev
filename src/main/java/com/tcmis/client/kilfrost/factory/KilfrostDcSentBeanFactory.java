package com.tcmis.client.kilfrost.factory;


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
import com.tcmis.client.kilfrost.beans.KilfrostDcSentBean;


/******************************************************************************
 * CLASSNAME: KilfrostDcSentBeanFactory <br>
 * @version: 1.0, Oct 7, 2008 <br>
 *****************************************************************************/


public class KilfrostDcSentBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_PO_NUMBER = "PO_NUMBER";
	public String ATTRIBUTE_ISSUE_ID = "ISSUE_ID";
	public String ATTRIBUTE_QUANTITY = "QUANTITY";
	public String ATTRIBUTE_DATE_SENT = "DATE_SENT";
	public String ATTRIBUTE_DATE_SHIPPED = "DATE_SHIPPED";
	public String ATTRIBUTE_ORDER_STATUS = "ORDER_STATUS";

	//table name
	public String TABLE = "KILFROST_DC_SENT";


	//constructor
	public KilfrostDcSentBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("poNumber")) {
			return ATTRIBUTE_PO_NUMBER;
		}
		else if(attributeName.equals("issueId")) {
			return ATTRIBUTE_ISSUE_ID;
		}
		else if(attributeName.equals("quantity")) {
			return ATTRIBUTE_QUANTITY;
		}
		else if(attributeName.equals("dateSent")) {
			return ATTRIBUTE_DATE_SENT;
		}
		else if(attributeName.equals("dateShipped")) {
			return ATTRIBUTE_DATE_SHIPPED;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, KilfrostDcSentBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(KilfrostDcSentBean kilfrostDcSentBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("poNumber", "SearchCriterion.EQUALS",
			"" + kilfrostDcSentBean.getPoNumber());

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


	public int delete(KilfrostDcSentBean kilfrostDcSentBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("poNumber", "SearchCriterion.EQUALS",
			"" + kilfrostDcSentBean.getPoNumber());

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



	//insert
	public int insert(KilfrostDcSentBean kilfrostDcSentBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(kilfrostDcSentBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(KilfrostDcSentBean kilfrostDcSentBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_PO_NUMBER + "," +
			ATTRIBUTE_ISSUE_ID + "," +
			ATTRIBUTE_QUANTITY + "," +
			ATTRIBUTE_DATE_SENT + "," +
			ATTRIBUTE_ORDER_STATUS + "," +
			ATTRIBUTE_DATE_SHIPPED + ")" +
			" values (" +
			SqlHandler.delimitString(kilfrostDcSentBean.getPoNumber()) + "," +
			kilfrostDcSentBean.getIssueId() + "," +
			kilfrostDcSentBean.getQuantity() + "," +
			DateHandler.getOracleToDateFunction(kilfrostDcSentBean.getDateSent()) + ",'" +
			kilfrostDcSentBean.getOrderStatus() + "'," +
			DateHandler.getOracleToDateFunction(kilfrostDcSentBean.getDateShipped()) + ")";

		return sqlManager.update(conn, query);
	}

/*
	//update
	public int update(KilfrostDcSentBean kilfrostDcSentBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(kilfrostDcSentBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(KilfrostDcSentBean kilfrostDcSentBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_PO_NUMBER + "=" + 
				SqlHandler.delimitString(kilfrostDcSentBean.getPoNumber()) + "," +
			ATTRIBUTE_ISSUE_ID + "=" + 
				StringHandler.nullIfZero(kilfrostDcSentBean.getIssueId()) + "," +
			ATTRIBUTE_QUANTITY + "=" + 
				StringHandler.nullIfZero(kilfrostDcSentBean.getQuantity()) + "," +
			ATTRIBUTE_DATE_SENT + "=" + 
				DateHandler.getOracleToDateFunction(kilfrostDcSentBean.getDateSent()) + "," +
			ATTRIBUTE_DATE_SHIPPED + "=" + 
				DateHandler.getOracleToDateFunction(kilfrostDcSentBean.getDateShipped()) + " " +
			"where " + ATTRIBUTE_PO_NUMBER + "=" +
				kilfrostDcSentBean.getPoNumber();

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

		Collection kilfrostDcSentBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			KilfrostDcSentBean kilfrostDcSentBean = new KilfrostDcSentBean();
			load(dataSetRow, kilfrostDcSentBean);
			kilfrostDcSentBeanColl.add(kilfrostDcSentBean);
		}

		return kilfrostDcSentBeanColl;
	}
}