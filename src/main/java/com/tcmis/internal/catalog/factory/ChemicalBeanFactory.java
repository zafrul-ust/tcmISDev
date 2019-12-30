package com.tcmis.internal.catalog.factory;


import java.math.BigDecimal;
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
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.internal.catalog.beans.ChemicalBean;


/******************************************************************************
 * CLASSNAME: ChemicalBeanFactory <br>
 * @version: 1.0, Jan 11, 2008 <br>
 *****************************************************************************/


public class ChemicalBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_CAS_NUMBER = "CAS_NUMBER";
	public String ATTRIBUTE_PREFERRED_NAME = "PREFERRED_NAME";
	public String ATTRIBUTE_MOLECULAR_WEIGHT = "MOLECULAR_WEIGHT";
	public String ATTRIBUTE_CHEMICAL_FORMULA = "CHEMICAL_FORMULA";
	public String ATTRIBUTE_COMMON_NAME = "COMMON_NAME";
	public String ATTRIBUTE_SPECIFIC_GRAVITY = "SPECIFIC_GRAVITY";
	public String ATTRIBUTE_EC_NUMBER = "EC_NUMBER";

	//table name
	public String TABLE = "CHEMICAL";


	//constructor
	public ChemicalBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	@Override
	public String getColumnName(String attributeName) {
		if(attributeName.equals("casNumber")) {
			return ATTRIBUTE_CAS_NUMBER;
		}
		else if(attributeName.equals("preferredName")) {
			return ATTRIBUTE_PREFERRED_NAME;
		}
		else if(attributeName.equals("molecularWeight")) {
			return ATTRIBUTE_MOLECULAR_WEIGHT;
		}
		else if(attributeName.equals("chemicalFormula")) {
			return ATTRIBUTE_CHEMICAL_FORMULA;
		}
		else if(attributeName.equals("commonName")) {
			return ATTRIBUTE_COMMON_NAME;
		}
		else if(attributeName.equals("specificGravity")) {
			return ATTRIBUTE_SPECIFIC_GRAVITY;
		}
		else if(attributeName.equals("ecNumber")) {
			return ATTRIBUTE_EC_NUMBER;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, ChemicalBean.class);
	}


	//you need to verify the primary key(s) before uncommenting this
	/*
	//delete
	public int delete(ChemicalBean chemicalBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("casNumber", "SearchCriterion.EQUALS",
			"" + chemicalBean.getCasNumber());

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


	public int delete(ChemicalBean chemicalBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("casNumber", "SearchCriterion.EQUALS",
			"" + chemicalBean.getCasNumber());

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

	public BigDecimal getNextChemicalId( Connection conn) throws BaseException
	{

		//		No such thing yet, just reserve it
		String query = "select global.chemical_seq.nextval from dual ";

		DataSet dataSet = new SqlManager().select(conn, query );

		Iterator dataIter = dataSet.iterator();
		BigDecimal nextId = new BigDecimal( 0 );
		while (dataIter.hasNext())
		{
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			nextId = new BigDecimal( dataSetRow.getInt("NEXTVAL") );
		}
		return nextId;
	}

	//insert
	public int insert(ChemicalBean chemicalBean)
	throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(chemicalBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(ChemicalBean chemicalBean, Connection conn)
	throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
			ATTRIBUTE_CAS_NUMBER + "," +
			ATTRIBUTE_PREFERRED_NAME + "," +
			ATTRIBUTE_MOLECULAR_WEIGHT + "," +
			ATTRIBUTE_CHEMICAL_FORMULA + "," +
			ATTRIBUTE_COMMON_NAME + "," +
			ATTRIBUTE_SPECIFIC_GRAVITY + "," +
			ATTRIBUTE_EC_NUMBER + ")" +
			" values (" +
			SqlHandler.delimitString(chemicalBean.getCasNumber()) + "," +
			SqlHandler.delimitString(chemicalBean.getPreferredName()) + "," +
			chemicalBean.getMolecularWeight() + "," +
			SqlHandler.delimitString(chemicalBean.getChemicalFormula()) + "," +
			SqlHandler.delimitString(chemicalBean.getCommonName()) + "," +
			chemicalBean.getSpecificGravity() + "," +
			SqlHandler.delimitString(chemicalBean.getEcNumber()) + ")";

		return sqlManager.update(conn, query);
	}

	/*
	//update
	public int update(ChemicalBean chemicalBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(chemicalBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(ChemicalBean chemicalBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_CAS_NUMBER + "=" +
				SqlHandler.delimitString(chemicalBean.getCasNumber()) + "," +
			ATTRIBUTE_PREFERRED_NAME + "=" +
				SqlHandler.delimitString(chemicalBean.getPreferredName()) + "," +
			ATTRIBUTE_MOLECULAR_WEIGHT + "=" +
				StringHandler.nullIfZero(chemicalBean.getMolecularWeight()) + "," +
			ATTRIBUTE_CHEMICAL_FORMULA + "=" +
				SqlHandler.delimitString(chemicalBean.getChemicalFormula()) + "," +
			ATTRIBUTE_COMMON_NAME + "=" +
				SqlHandler.delimitString(chemicalBean.getCommonName()) + "," +
			ATTRIBUTE_SPECIFIC_GRAVITY + "=" +
				StringHandler.nullIfZero(chemicalBean.getSpecificGravity()) + "," +
			ATTRIBUTE_EC_NUMBER + "=" +
				SqlHandler.delimitString(chemicalBean.getEcNumber()) + " " +
			"where " + ATTRIBUTE_CAS_NUMBER + "=" +
				chemicalBean.getCasNumber();

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

		Collection chemicalBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
		getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			ChemicalBean chemicalBean = new ChemicalBean();
			load(dataSetRow, chemicalBean);
			chemicalBeanColl.add(chemicalBean);
		}

		return chemicalBeanColl;
	}

	@SuppressWarnings("unchecked")
	public boolean isValidCASNumber(String casNumber) throws BaseException {
		GenericSqlFactory factory = new GenericSqlFactory(getDbManager());
		String query = "select fx_is_valid_cas('"+ casNumber + "') from dual";
		return "Y".equals(factory.selectSingleValue(query));
	}

	public boolean exists(String casNumber) throws BaseException {
		GenericSqlFactory factory = new GenericSqlFactory(getDbManager());
		String query = "select count(*) from " + TABLE + " where CAS_NUMBER ='" + casNumber + "'";
		return !"0".equals(factory.selectSingleValue(query));
	}
}