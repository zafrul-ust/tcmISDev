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
import java.util.Date;
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
import com.tcmis.supplier.dbuy.beans.DbuyContractPriceBean;


/******************************************************************************
 * CLASSNAME: DbuyContractPriceBeanFactory <br>
 * @version: 1.0, Jul 19, 2006 <br>
 *****************************************************************************/


public class DbuyContractPriceBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_DBUY_CONTRACT_ID = "DBUY_CONTRACT_ID";
	public String ATTRIBUTE_END_DATE = "END_DATE";
	public String ATTRIBUTE_UPTO_QUANTITY = "UPTO_QUANTITY";
	public String ATTRIBUTE_UNIT_PRICE = "UNIT_PRICE";
	public String ATTRIBUTE_CURRENCY_ID = "CURRENCY_ID";
	public String ATTRIBUTE_COMMENTS = "COMMENTS";

	//table name
	public String TABLE = "DBUY_CONTRACT_PRICE";


	//constructor
	public DbuyContractPriceBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("dbuyContractId")) {
			return ATTRIBUTE_DBUY_CONTRACT_ID;
		}
		else if(attributeName.equals("endDate")) {
			return ATTRIBUTE_END_DATE;
		}
		else if(attributeName.equals("uptoQuantity")) {
			return ATTRIBUTE_UPTO_QUANTITY;
		}
		else if(attributeName.equals("unitPrice")) {
			return ATTRIBUTE_UNIT_PRICE;
		}
		else if(attributeName.equals("currencyId")) {
			return ATTRIBUTE_CURRENCY_ID;
		}
		else if(attributeName.equals("comments")) {
			return ATTRIBUTE_COMMENTS;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, DbuyContractPriceBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(DbuyContractPriceBean dbuyContractPriceBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("dbuyContractId", "SearchCriterion.EQUALS",
			"" + dbuyContractPriceBean.getDbuyContractId());

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


	public int delete(DbuyContractPriceBean dbuyContractPriceBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("dbuyContractId", "SearchCriterion.EQUALS",
			"" + dbuyContractPriceBean.getDbuyContractId());

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
	public int insert(DbuyContractPriceBean dbuyContractPriceBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(dbuyContractPriceBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(DbuyContractPriceBean dbuyContractPriceBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_DBUY_CONTRACT_ID + "," +
			ATTRIBUTE_END_DATE + "," +
			ATTRIBUTE_UPTO_QUANTITY + "," +
			ATTRIBUTE_UNIT_PRICE + "," +
			ATTRIBUTE_CURRENCY_ID + "," +
			ATTRIBUTE_COMMENTS + ")" +
 values (
			StringHandler.nullIfZero(dbuyContractPriceBean.getDbuyContractId()) + "," +
			DateHandler.getOracleToDateFunction(dbuyContractPriceBean.getEndDate()) + "," +
			StringHandler.nullIfZero(dbuyContractPriceBean.getUptoQuantity()) + "," +
			StringHandler.nullIfZero(dbuyContractPriceBean.getUnitPrice()) + "," +
			SqlHandler.delimitString(dbuyContractPriceBean.getCurrencyId()) + "," +
			SqlHandler.delimitString(dbuyContractPriceBean.getComments()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(DbuyContractPriceBean dbuyContractPriceBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(dbuyContractPriceBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(DbuyContractPriceBean dbuyContractPriceBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_DBUY_CONTRACT_ID + "=" + 
				StringHandler.nullIfZero(dbuyContractPriceBean.getDbuyContractId()) + "," +
			ATTRIBUTE_END_DATE + "=" + 
				DateHandler.getOracleToDateFunction(dbuyContractPriceBean.getEndDate()) + "," +
			ATTRIBUTE_UPTO_QUANTITY + "=" + 
				StringHandler.nullIfZero(dbuyContractPriceBean.getUptoQuantity()) + "," +
			ATTRIBUTE_UNIT_PRICE + "=" + 
				StringHandler.nullIfZero(dbuyContractPriceBean.getUnitPrice()) + "," +
			ATTRIBUTE_CURRENCY_ID + "=" + 
				SqlHandler.delimitString(dbuyContractPriceBean.getCurrencyId()) + "," +
			ATTRIBUTE_COMMENTS + "=" + 
				SqlHandler.delimitString(dbuyContractPriceBean.getComments()) + " " +
			"where " + ATTRIBUTE_DBUY_CONTRACT_ID + "=" +
				StringHandler.nullIfZero(dbuyContractPriceBean.getDbuyContractId());

		return new SqlManager().update(conn, query);
	}
*/
	//update
	public int updatePrice(DbuyContractPriceBean dbuyContractPriceBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = updatePrice(dbuyContractPriceBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int updatePrice(DbuyContractPriceBean dbuyContractPriceBean, Connection conn)
		throws BaseException {

                Date now = new Date();                
                String date = DateHandler.formatOracleDate(now);
                
		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_UNIT_PRICE + "=" + 
				StringHandler.nullIfZero(dbuyContractPriceBean.getUnitPrice()) + "," +
			ATTRIBUTE_COMMENTS + "=" + 
				SqlHandler.delimitString(dbuyContractPriceBean.getComments() + "| Web Update " + date) + " " +
			"where " + ATTRIBUTE_DBUY_CONTRACT_ID + "=" +
				StringHandler.nullIfZero(dbuyContractPriceBean.getDbuyContractId()) +
			" and " + ATTRIBUTE_END_DATE + "=" + 
				DateHandler.getOracleToDateFunction(dbuyContractPriceBean.getEndDate()) + 
			" and " + ATTRIBUTE_UPTO_QUANTITY + "=" + 
				StringHandler.nullIfZero(dbuyContractPriceBean.getUptoQuantity());


		return new SqlManager().update(conn, query);
	}

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

		Collection dbuyContractPriceBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			DbuyContractPriceBean dbuyContractPriceBean = new DbuyContractPriceBean();
			load(dataSetRow, dbuyContractPriceBean);
			dbuyContractPriceBeanColl.add(dbuyContractPriceBean);
		}

		return dbuyContractPriceBeanColl;
	}
}