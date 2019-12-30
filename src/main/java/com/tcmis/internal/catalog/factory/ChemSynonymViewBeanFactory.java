package com.tcmis.internal.catalog.factory;


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
import com.tcmis.common.util.*;
import com.tcmis.internal.catalog.beans.ChemSynonymViewBean;

/******************************************************************************
 * CLASSNAME: ChemSynonymViewBeanFactory <br>
 * @version: 1.0, Jan 10, 2008 <br>
 *****************************************************************************/

public class ChemSynonymViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_PREFERRED_NAME = "PREFERRED_NAME";
	public String ATTRIBUTE_ALTERNATE_NAME = "ALTERNATE_NAME";
	public String ATTRIBUTE_CAS_NUMBER = "CAS_NUMBER";
	public String ATTRIBUTE_EC_NUMBER = "EC_NUMBER";

	//table name
	public String TABLE = "CHEM_SYNONYM_VIEW";


	//constructor
	public ChemSynonymViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	@Override
	public String getColumnName(String attributeName) {
		if(attributeName.equals("preferredName")) {
			return ATTRIBUTE_PREFERRED_NAME;
		}
		else if(attributeName.equals("alternateName")) {
			return ATTRIBUTE_ALTERNATE_NAME;
		}
		else if(attributeName.equals("casNumber")) {
			return ATTRIBUTE_CAS_NUMBER;
		}
		else if(attributeName.equals("ecNumber")){
			return ATTRIBUTE_EC_NUMBER;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, ChemSynonymViewBean.class);
	}


	//you need to verify the primary key(s) before uncommenting this
	/*
	//delete
	public int delete(ChemSynonymViewBean chemSynonymViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("preferredName", "SearchCriterion.EQUALS",
			"" + chemSynonymViewBean.getPreferredName());

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


	public int delete(ChemSynonymViewBean chemSynonymViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("preferredName", "SearchCriterion.EQUALS",
			"" + chemSynonymViewBean.getPreferredName());

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
	public int insert(ChemSynonymViewBean chemSynonymViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(chemSynonymViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(ChemSynonymViewBean chemSynonymViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
			ATTRIBUTE_PREFERRED_NAME + "," +
			ATTRIBUTE_ALTERNATE_NAME + "," +
			ATTRIBUTE_CAS_NUMBER + ")" +
			" values (" +
			SqlHandler.delimitString(chemSynonymViewBean.getPreferredName()) + "," +
			SqlHandler.delimitString(chemSynonymViewBean.getAlternateName()) + "," +
			SqlHandler.delimitString(chemSynonymViewBean.getCasNumber()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(ChemSynonymViewBean chemSynonymViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(chemSynonymViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(ChemSynonymViewBean chemSynonymViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_PREFERRED_NAME + "=" +
				SqlHandler.delimitString(chemSynonymViewBean.getPreferredName()) + "," +
			ATTRIBUTE_ALTERNATE_NAME + "=" +
				SqlHandler.delimitString(chemSynonymViewBean.getAlternateName()) + "," +
			ATTRIBUTE_CAS_NUMBER + "=" +
				SqlHandler.delimitString(chemSynonymViewBean.getCasNumber()) + " " +
			"where " + ATTRIBUTE_PREFERRED_NAME + "=" +
				chemSynonymViewBean.getPreferredName();

		return new SqlManager().update(conn, query);
	}
	 */
	public Collection select(String searchField, String searchMode, String searchArgument,  boolean tradeSecret)	throws BaseException
	{
		Connection connection = null;
		Collection c = null;
		try
		{
			connection = this.getDbManager().getConnection();
			c = select(searchField, searchMode, searchArgument, tradeSecret, connection);
		}
		finally
		{
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}

	public Collection select(String searchField, String searchMode, String searchArgument, boolean tradeSecret, Connection conn)	throws BaseException
	{
		Collection chemSynonymViewBeanColl = new Vector();

		StringBuilder queryBuffer = new StringBuilder(" select distinct cas_number, preferred_name, ec_number from "); // + TABLE + " " +
		queryBuffer.append(TABLE);
		queryBuffer.append(" where ");

		if(searchMode == null) {
			searchMode = "is";
			searchField = "cas_number ";
		}
		
		if (searchMode.equals("is")) {
            if ("cas_number".equalsIgnoreCase(searchField) || "ec_number".equalsIgnoreCase(searchField))
                queryBuffer.append(searchField).append(" = UPPER(").append(SqlHandler.delimitString(searchArgument)).append(")");
            else
                queryBuffer.append("lower(").append(searchField).append(") = lower('").append(SqlHandler.validQuery(searchArgument)).append("')");
        }else if (searchMode.equals("like")) {
            if ("cas_number".equalsIgnoreCase(searchField) || "ec_number".equalsIgnoreCase(searchField))
                queryBuffer.append(searchField).append(" like UPPER('%").append(SqlHandler.validQuery(searchArgument)).append("%')");
            else
                queryBuffer.append("lower(").append(searchField).append(") like lower('%").append(SqlHandler.validQuery(searchArgument)).append("%')");
        }else if (searchMode.equals("startsWith")) {
            if ("cas_number".equalsIgnoreCase(searchField) || "ec_number".equalsIgnoreCase(searchField))
                queryBuffer.append(searchField).append(" like UPPER('").append(SqlHandler.validQuery(searchArgument)).append("%')");
            else
                queryBuffer.append("lower(").append(searchField).append(") like lower('").append(SqlHandler.validQuery(searchArgument)).append("%')");
        }else if (searchMode.equals("endsWith")) {
            if ("cas_number".equalsIgnoreCase(searchField) || "ec_number".equalsIgnoreCase(searchField))
                queryBuffer.append(searchField).append(" like UPPER('%").append(SqlHandler.validQuery(searchArgument)).append("')");
            else
                queryBuffer.append("lower(").append(searchField).append(") like lower('%").append(SqlHandler.validQuery(searchArgument)).append("')");
        }
		if (tradeSecret) {
			queryBuffer.append(" and CAS_NUMBER like 'TS%'");
		}
		queryBuffer.append(" order by CAS_NUMBER  asc ");

		DataSet dataSet = new SqlManager().select(conn, queryBuffer.toString() );

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext())
		{
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			ChemSynonymViewBean chemSynonymViewBean = new ChemSynonymViewBean();
			load(dataSetRow, chemSynonymViewBean);
			chemSynonymViewBeanColl.add(chemSynonymViewBean);
			//   log.debug("chemSynonymViewBean.toString() = [" + chemSynonymViewBean.toString() + "]; ");
		}
		return chemSynonymViewBeanColl;
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

		Collection chemSynonymViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
		getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			ChemSynonymViewBean chemSynonymViewBean = new ChemSynonymViewBean();
			load(dataSetRow, chemSynonymViewBean);
			chemSynonymViewBeanColl.add(chemSynonymViewBean);
		}

		return chemSynonymViewBeanColl;
	}
}