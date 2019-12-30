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
import com.tcmis.internal.distribution.beans.CustomerReturnFeeBean;


/******************************************************************************
 * CLASSNAME: CustomerReturnFeeBeanFactory <br>
 * @version: 1.0, Jul 22, 2009 <br>
 *****************************************************************************/


public class CustomerReturnFeeBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_CUSTOMER_RMA_ID = "CUSTOMER_RMA_ID";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_RETURN_PRICE = "RETURN_PRICE";
	public String ATTRIBUTE_CURRENCY_ID = "CURRENCY_ID";
	public String ATTRIBUTE_FEE_DESCRIPTION = "FEE_DESCRIPTION";
	
	
	//table name
	public String TABLE = "CUSTOMER_RETURN_FEE";


	//constructor
	public CustomerReturnFeeBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("customerRmaId")) {
			return ATTRIBUTE_CUSTOMER_RMA_ID;
		}
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("returnPrice")) {
			return ATTRIBUTE_RETURN_PRICE;
		}
		else if(attributeName.equals("currencyId")) {
			return ATTRIBUTE_CURRENCY_ID;
		}
		else if(attributeName.equals("feeDescription")) {
			return ATTRIBUTE_FEE_DESCRIPTION;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}

	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, CustomerReturnFeeBean.class);
	}


//you need to verify the primary key(s) before uncommenting this

	//delete
	public int delete(CustomerReturnFeeBean customerReturnFeeBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("customerRmaId", "SearchCriterion.EQUALS",
			"" + customerReturnFeeBean.getCustomerRmaId());

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


	public int delete(CustomerReturnFeeBean customerReturnFeeBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("customerRmaId", "SearchCriterion.EQUALS",
			"" + customerReturnFeeBean.getCustomerRmaId());

		return delete(criteria, conn);
	}


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
	public int insert(CustomerReturnFeeBean customerReturnFeeBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(customerReturnFeeBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}



	
	
	public int insert(CustomerReturnFeeBean customerReturnFeeBean, Connection conn)
	throws BaseException {

	SqlManager sqlManager = new SqlManager();

	String query  = 
		"insert into " + TABLE + " (" +
		ATTRIBUTE_CUSTOMER_RMA_ID + "," +
		ATTRIBUTE_ITEM_ID + "," +
		ATTRIBUTE_RETURN_PRICE + "," +
		ATTRIBUTE_CURRENCY_ID + "," +
		ATTRIBUTE_FEE_DESCRIPTION + ")" +
		" values (" +
		customerReturnFeeBean.getCustomerRmaId() + "," +
		customerReturnFeeBean.getItemId() + "," +
		customerReturnFeeBean.getReturnPrice() + "," +
		SqlHandler.delimitString(customerReturnFeeBean.getCurrencyId()) + "," +
		SqlHandler.delimitString(customerReturnFeeBean.getFeeDescription()) + ")";

	return sqlManager.update(conn, query);
}
	

	//update
	public int update(CustomerReturnFeeBean customerReturnFeeBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(customerReturnFeeBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}




	
	public int update(CustomerReturnFeeBean customerReturnFeeBean, Connection conn)
	throws BaseException {

	String query  = "update " + TABLE + " set " +
		ATTRIBUTE_CUSTOMER_RMA_ID + "=" + 
			StringHandler.nullIfZero(customerReturnFeeBean.getCustomerRmaId()) + "," +
		ATTRIBUTE_ITEM_ID + "=" + 
			StringHandler.nullIfZero(customerReturnFeeBean.getItemId()) + "," +
		ATTRIBUTE_RETURN_PRICE + "=" + 
			StringHandler.nullIfZero(customerReturnFeeBean.getReturnPrice()) + "," +
		ATTRIBUTE_CURRENCY_ID + "=" + 
			SqlHandler.delimitString(customerReturnFeeBean.getCurrencyId()) + "," +
		ATTRIBUTE_FEE_DESCRIPTION + "=" + 
			SqlHandler.delimitString(customerReturnFeeBean.getFeeDescription()) + " " +
		"where " + ATTRIBUTE_CUSTOMER_RMA_ID + "=" +
		customerReturnFeeBean.getCustomerRmaId();

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

		Collection customerReturnFeeBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			CustomerReturnFeeBean customerReturnFeeBean = new CustomerReturnFeeBean();
			load(dataSetRow, customerReturnFeeBean);
			customerReturnFeeBeanColl.add(customerReturnFeeBean);
		}

		return customerReturnFeeBeanColl;
	}
}