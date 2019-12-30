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
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.distribution.beans.BillToCustomerTaxExemptionBean;


/******************************************************************************
 * CLASSNAME: BillToCustomerTaxExemptionBeanFactory <br>
 * @version: 1.0, Sep 2, 2009 <br>
 *****************************************************************************/


public class BillToCustomerTaxExemptionBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_CUSTOMER_ID = "CUSTOMER_ID";
	public String ATTRIBUTE_STATE_ABBREV = "STATE_ABBREV";
	public String ATTRIBUTE_TAX_EXEMPTION_CERTIFICATE = "TAX_EXEMPTION_CERTIFICATE";
	public String ATTRIBUTE_COUNTRY_ABBREV = "COUNTRY_ABBREV";
	public String ATTRIBUTE_EXPIRATION_DATE = "EXPIRATION_DATE";

	//table name
	public String TABLE = "CUSTOMER.BILL_TO_CUSTOMER_TAX_EXEMPTION";


	//constructor
	public BillToCustomerTaxExemptionBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("customerId")) {
			return ATTRIBUTE_CUSTOMER_ID;
		}
		else if(attributeName.equals("stateAbbrev")) {
			return ATTRIBUTE_STATE_ABBREV;
		}
		else if(attributeName.equals("taxExemptionCertificate")) {
			return ATTRIBUTE_TAX_EXEMPTION_CERTIFICATE;
		}
		else if(attributeName.equals("countryAbbrev")) {
			return ATTRIBUTE_COUNTRY_ABBREV;
		}
		else if(attributeName.equals("expirationDate")) {
			return ATTRIBUTE_EXPIRATION_DATE;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, BillToCustomerTaxExemptionBean.class);
	}


//you need to verify the primary key(s) before uncommenting this

	//delete
	public int delete(BillToCustomerTaxExemptionBean BillToCustomerTaxExemptionBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("customerId", "SearchCriterion.EQUALS",
			"" + BillToCustomerTaxExemptionBean.getCustomerId());

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


	public int delete(BillToCustomerTaxExemptionBean BillToCustomerTaxExemptionBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("customerId", "SearchCriterion.EQUALS",
			"" + BillToCustomerTaxExemptionBean.getCustomerId());

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
	public int insert(BillToCustomerTaxExemptionBean BillToCustomerTaxExemptionBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(BillToCustomerTaxExemptionBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(BillToCustomerTaxExemptionBean BillToCustomerTaxExemptionBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_CUSTOMER_ID + "," +
			ATTRIBUTE_STATE_ABBREV + "," +
			ATTRIBUTE_TAX_EXEMPTION_CERTIFICATE + "," +
			ATTRIBUTE_COUNTRY_ABBREV + "," +
			ATTRIBUTE_EXPIRATION_DATE + ")" +
			" values (" +
			BillToCustomerTaxExemptionBean.getCustomerId() + "," +
			SqlHandler.delimitString(BillToCustomerTaxExemptionBean.getStateAbbrev()) + "," +
			SqlHandler.delimitString(BillToCustomerTaxExemptionBean.getTaxExemptionCertificate()) + "," +
			SqlHandler.delimitString(BillToCustomerTaxExemptionBean.getCountryAbbrev()) + "," +
			DateHandler.getOracleToDateFunction(BillToCustomerTaxExemptionBean.getExpirationDate()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(BillToCustomerTaxExemptionBean BillToCustomerTaxExemptionBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(BillToCustomerTaxExemptionBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(BillToCustomerTaxExemptionBean BillToCustomerTaxExemptionBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_CUSTOMER_ID + "=" + 
				StringHandler.nullIfZero(BillToCustomerTaxExemptionBean.getCustomerId()) + "," +
			ATTRIBUTE_STATE_ABBREV + "=" + 
				SqlHandler.delimitString(BillToCustomerTaxExemptionBean.getStateAbbrev()) + "," +
			ATTRIBUTE_TAX_EXEMPTION_CERTIFICATE + "=" + 
				SqlHandler.delimitString(BillToCustomerTaxExemptionBean.getTaxExemptionCertificate()) + "," +
			ATTRIBUTE_COUNTRY_ABBREV + "=" + 
				SqlHandler.delimitString(BillToCustomerTaxExemptionBean.getCountryAbbrev()) + "," +
			ATTRIBUTE_EXPIRATION_DATE + "=" + 
				DateHandler.getOracleToDateFunction(BillToCustomerTaxExemptionBean.getExpirationDate()) + " " +
			"where " + ATTRIBUTE_CUSTOMER_ID + "=" +
				BillToCustomerTaxExemptionBean.getCustomerId();

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

		Collection BillToCustomerTaxExemptionBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			BillToCustomerTaxExemptionBean BillToCustomerTaxExemptionBean = new BillToCustomerTaxExemptionBean();
			load(dataSetRow, BillToCustomerTaxExemptionBean);
			BillToCustomerTaxExemptionBeanColl.add(BillToCustomerTaxExemptionBean);
		}

		return BillToCustomerTaxExemptionBeanColl;
	}
}