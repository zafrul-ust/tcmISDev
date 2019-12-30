package com.tcmis.internal.distribution.factory;


import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.distribution.beans.CustomerReturnReasonBean;


/******************************************************************************
 * CLASSNAME: CustomerReturnReasonBeanFactory <br>
 * @version: 1.0, Jul 16, 2009 <br>
 *****************************************************************************/


public class CustomerReturnReasonBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_CUSTOMER_RMA_ID = "CUSTOMER_RMA_ID";
	public String ATTRIBUTE_RETURN_REASON_ID = "RETURN_REASON_ID";

	//table name
	public String TABLE = "CUSTOMER_RETURN_REASON";


	//constructor
	public CustomerReturnReasonBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("customerRmaId")) {
			return ATTRIBUTE_CUSTOMER_RMA_ID;
		}
		else if(attributeName.equals("returnReasonId")) {
			return ATTRIBUTE_RETURN_REASON_ID;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, CustomerReturnReasonBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(CustomerReturnReasonBean customerReturnReasonBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("customerRmaId", "SearchCriterion.EQUALS",
			"" + customerReturnReasonBean.getCustomerRmaId());

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


	public int delete(CustomerReturnReasonBean customerReturnReasonBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("customerRmaId", "SearchCriterion.EQUALS",
			"" + customerReturnReasonBean.getCustomerRmaId());

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

	//insert
	public int insert(CustomerReturnReasonBean customerReturnReasonBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(customerReturnReasonBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(CustomerReturnReasonBean customerReturnReasonBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_CUSTOMER_RMA_ID + "," +
			ATTRIBUTE_RETURN_REASON_ID + ")" +
			" values (" +
			customerReturnReasonBean.getCustomerRmaId() + "," +
			SqlHandler.delimitString(customerReturnReasonBean.getReturnReasonId()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(CustomerReturnReasonBean customerReturnReasonBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(customerReturnReasonBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(CustomerReturnReasonBean customerReturnReasonBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_CUSTOMER_RMA_ID + "=" + 
				StringHandler.nullIfZero(customerReturnReasonBean.getCustomerRmaId()) + "," +
			ATTRIBUTE_RETURN_REASON_ID + "=" + 
				SqlHandler.delimitString(customerReturnReasonBean.getReturnReasonId()) + " " +
			"where " + ATTRIBUTE_CUSTOMER_RMA_ID + "=" +
				customerReturnReasonBean.getCustomerRmaId();

		return new SqlManager().update(conn, query);
	}


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

		Collection customerReturnReasonBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			CustomerReturnReasonBean customerReturnReasonBean = new CustomerReturnReasonBean();
			load(dataSetRow, customerReturnReasonBean);
			customerReturnReasonBeanColl.add(customerReturnReasonBean);
		}

		return customerReturnReasonBeanColl;
	}
}