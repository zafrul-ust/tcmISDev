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
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.internal.catalog.beans.CompositionBean;

/******************************************************************************
 * CLASSNAME: CompositionBeanFactory <br>
 * @version: 1.0, Dec 10, 2010 <br>
 *****************************************************************************/

public class CompositionBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(getClass());

	//column names
	public String ATTRIBUTE_MATERIAL_ID = "MATERIAL_ID";
	public String ATTRIBUTE_REVISION_DATE = "REVISION_DATE";
	public String ATTRIBUTE_PERCENT = "PERCENT";
	public String ATTRIBUTE_CHEMICAL_ID = "CHEMICAL_ID";
	public String ATTRIBUTE_PERCENT_AS_CAS = "PERCENT_AS_CAS";
	public String ATTRIBUTE_PERCENT_LOWER = "PERCENT_LOWER";
	public String ATTRIBUTE_PERCENT_UPPER = "PERCENT_UPPER";
	public String ATTRIBUTE_HAZARDOUS = "HAZARDOUS";
	public String ATTRIBUTE_CAS_NUMBER = "CAS_NUMBER";
	public String ATTRIBUTE_TRADE_SECRET = "TRADE_SECRET";
	public String ATTRIBUTE_REMARK = "REMARK";
	public String ATTRIBUTE_MSDS_CHEMICAL_NAME = "MSDS_CHEMICAL_NAME";
	public String ATTRIBUTE_INSERT_DATE = "INSERT_DATE";
	public String ATTRIBUTE_PERCENT_LOWER_CALC = "PERCENT_LOWER_CALC";
	public String ATTRIBUTE_PERCENT_CALC = "PERCENT_CALC";
	public String ATTRIBUTE_PERCENT_UPPER_CALC = "PERCENT_UPPER_CALC";
	public String ATTRIBUTE_DATA_SOURCE = "DATA_SOURCE";
	public String ATTRIBUTE_TRACE = "TRACE";
	public String ATTRIBUTE_SDS_SECTION_ID = "SDS_SECTION_ID";

	//table name
	public String TABLE = "COMPOSITION";

	//constructor
	public CompositionBeanFactory(DbManager dbManager) {
		super(dbManager);
	}

	//get column names
	@Override
	public String getColumnName(String attributeName) {
		if (attributeName.equals("materialId")) {
			return ATTRIBUTE_MATERIAL_ID;
		}
		else if (attributeName.equals("revisionDate")) {
			return ATTRIBUTE_REVISION_DATE;
		}
		else if (attributeName.equals("percent")) {
			return ATTRIBUTE_PERCENT;
		}
		else if (attributeName.equals("chemicalId")) {
			return ATTRIBUTE_CHEMICAL_ID;
		}
		else if (attributeName.equals("percentAsCas")) {
			return ATTRIBUTE_PERCENT_AS_CAS;
		}
		else if (attributeName.equals("percentLower")) {
			return ATTRIBUTE_PERCENT_LOWER;
		}
		else if (attributeName.equals("percentUpper")) {
			return ATTRIBUTE_PERCENT_UPPER;
		}
		else if (attributeName.equals("hazardous")) {
			return ATTRIBUTE_HAZARDOUS;
		}
		else if (attributeName.equals("casNumber")) {
			return ATTRIBUTE_CAS_NUMBER;
		}
		else if (attributeName.equals("tradeSecret")) {
			return ATTRIBUTE_TRADE_SECRET;
		}
		else if (attributeName.equals("remark")) {
			return ATTRIBUTE_REMARK;
		}
		else if (attributeName.equals("msdsChemicalName")) {
			return ATTRIBUTE_MSDS_CHEMICAL_NAME;
		}
		else if (attributeName.equals("insertDate")) {
			return ATTRIBUTE_INSERT_DATE;
		}
		else if (attributeName.equals("percentLowerCalc")) {
			return ATTRIBUTE_PERCENT_LOWER_CALC;
		}
		else if (attributeName.equals("percentCalc")) {
			return ATTRIBUTE_PERCENT_CALC;
		}
		else if (attributeName.equals("percentUpperCalc")) {
			return ATTRIBUTE_PERCENT_UPPER_CALC;
		}
		else if (attributeName.equals("dataSource")) {
			return ATTRIBUTE_DATA_SOURCE;
		}
		else if(attributeName.equals("trace")) {
			return ATTRIBUTE_TRACE;
		}
		else if (attributeName.equals("sdsSectionId")) {
			return ATTRIBUTE_SDS_SECTION_ID;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}

	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, CompositionBean.class);
	}

	//you need to verify the primary key(s) before uncommenting this
	/*
		//delete
		public int delete(CompositionBean compositionBean)
			throws BaseException {

			SearchCriteria criteria = new SearchCriteria("materialId", "SearchCriterion.EQUALS",
				"" + compositionBean.getMaterialId());

			Connection connection = null;
			int result = 0;
			try {
				connection = getDbManager().getConnection();
				result = delete(criteria, connection);
			}
			finally {
				getDbManager().returnConnection(connection);
			}
			return result;
		}


		public int delete(CompositionBean compositionBean, Connection conn)
			throws BaseException {

			SearchCriteria criteria = new SearchCriteria("materialId", "SearchCriterion.EQUALS",
				"" + compositionBean.getMaterialId());

			return delete(criteria, conn);
		}
	 */

	public int delete(SearchCriteria criteria) throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = delete(criteria, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
		return result;
	}

	public int delete(SearchCriteria criteria, Connection conn) throws BaseException {

		String sqlQuery = " delete from " + TABLE + " " + getWhereClause(criteria);

		return new SqlManager().update(conn, sqlQuery);
	}

	//insert
	public int insert(CompositionBean compositionBean) throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(compositionBean, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
		return result;
	}

	public int insert(CompositionBean compositionBean, Connection conn)
	throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
			ATTRIBUTE_MATERIAL_ID + "," +
			ATTRIBUTE_REVISION_DATE + "," +
			ATTRIBUTE_PERCENT + "," +
			ATTRIBUTE_CHEMICAL_ID + "," +
			ATTRIBUTE_PERCENT_AS_CAS + "," +
			ATTRIBUTE_PERCENT_LOWER + "," +
			ATTRIBUTE_PERCENT_UPPER + "," +
			ATTRIBUTE_HAZARDOUS + "," +
			ATTRIBUTE_CAS_NUMBER + "," +
			ATTRIBUTE_TRADE_SECRET + "," +
			ATTRIBUTE_REMARK + "," +
			ATTRIBUTE_MSDS_CHEMICAL_NAME + "," +
			//ATTRIBUTE_INSERT_DATE + "," +
			ATTRIBUTE_PERCENT_LOWER_CALC + "," +
			ATTRIBUTE_PERCENT_CALC + "," +
			ATTRIBUTE_PERCENT_UPPER_CALC + "," +
			ATTRIBUTE_DATA_SOURCE + "," +
			ATTRIBUTE_TRACE + "," + 
			ATTRIBUTE_SDS_SECTION_ID + ")" +
			" values (" +
			compositionBean.getMaterialId() + "," +
			DateHandler.getOracleToDateFunction(compositionBean.getRevisionDate()) + "," +
			compositionBean.getPercent() + "," +
			SqlHandler.delimitString(compositionBean.getChemicalId()) + "," +
			SqlHandler.delimitString(compositionBean.getPercentAsCas()) + "," +
			compositionBean.getPercentLower() + "," +
			compositionBean.getPercentUpper() + "," +
			SqlHandler.delimitString(compositionBean.getHazardous()) + "," +
			SqlHandler.delimitString(compositionBean.getCasNumber()) + "," +
			SqlHandler.delimitString(compositionBean.getTradeSecret()) + "," +
			SqlHandler.delimitString(compositionBean.getRemark()) + "," +
			SqlHandler.delimitString(compositionBean.getMsdsChemicalName()) + "," +
			//DateHandler.getOracleToDateFunction(compositionBean.getInsertDate()) + "," +
			compositionBean.getPercentLowerCalc() + "," +
			compositionBean.getPercentCalc() + "," +
			compositionBean.getPercentUpperCalc() + "," +
			SqlHandler.delimitString(compositionBean.getDataSource()) + "," +
			SqlHandler.delimitString(compositionBean.getTrace()) + "," +
			compositionBean.getSdsSectionId() + ")";

		return sqlManager.update(conn, query);
	}

	/*
		//update
		public int update(CompositionBean compositionBean)
			throws BaseException {

			Connection connection = null;
			int result = 0;
			try {
				connection = getDbManager().getConnection();
				result = update(compositionBean, connection);
			}
			finally {
				getDbManager().returnConnection(connection);
			}
			return result;
		}


		public int update(CompositionBean compositionBean, Connection conn)
			throws BaseException {

			String query  = "update " + TABLE + " set " +
				ATTRIBUTE_MATERIAL_ID + "=" +
					StringHandler.nullIfZero(compositionBean.getMaterialId()) + "," +
				ATTRIBUTE_REVISION_DATE + "=" +
					DateHandler.getOracleToDateFunction(compositionBean.getRevisionDate()) + "," +
				ATTRIBUTE_PERCENT + "=" +
					StringHandler.nullIfZero(compositionBean.getPercent()) + "," +
				ATTRIBUTE_CHEMICAL_ID + "=" +
					SqlHandler.delimitString(compositionBean.getChemicalId()) + "," +
				ATTRIBUTE_PERCENT_AS_CAS + "=" +
					SqlHandler.delimitString(compositionBean.getPercentAsCas()) + "," +
				ATTRIBUTE_PERCENT_LOWER + "=" +
					StringHandler.nullIfZero(compositionBean.getPercentLower()) + "," +
				ATTRIBUTE_PERCENT_UPPER + "=" +
					StringHandler.nullIfZero(compositionBean.getPercentUpper()) + "," +
				ATTRIBUTE_HAZARDOUS + "=" +
					SqlHandler.delimitString(compositionBean.getHazardous()) + "," +
				ATTRIBUTE_CAS_NUMBER + "=" +
					SqlHandler.delimitString(compositionBean.getCasNumber()) + "," +
				ATTRIBUTE_TRADE_SECRET + "=" +
					SqlHandler.delimitString(compositionBean.getTradeSecret()) + "," +
				ATTRIBUTE_REMARK + "=" +
					SqlHandler.delimitString(compositionBean.getRemark()) + "," +
				ATTRIBUTE_MSDS_CHEMICAL_NAME + "=" +
					SqlHandler.delimitString(compositionBean.getMsdsChemicalName()) + "," +
				ATTRIBUTE_INSERT_DATE + "=" +
					DateHandler.getOracleToDateFunction(compositionBean.getInsertDate()) + "," +
				ATTRIBUTE_PERCENT_LOWER_CALC + "=" +
					StringHandler.nullIfZero(compositionBean.getPercentLowerCalc()) + "," +
				ATTRIBUTE_PERCENT_CALC + "=" +
					StringHandler.nullIfZero(compositionBean.getPercentCalc()) + "," +
				ATTRIBUTE_PERCENT_UPPER_CALC + "=" +
					StringHandler.nullIfZero(compositionBean.getPercentUpperCalc()) + "," +
				ATTRIBUTE_DATA_SOURCE + "=" +
					SqlHandler.delimitString(compositionBean.getDataSource()) + " " +
				"where " + ATTRIBUTE_MATERIAL_ID + "=" +
					compositionBean.getMaterialId();

			return new SqlManager().update(conn, query);
		}
	 */

	//select
	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria) throws BaseException {

		Connection connection = null;
		Collection c = null;
		try {
			connection = getDbManager().getConnection();
			c = select(criteria, sortCriteria, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
		return c;
	}

	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn) throws BaseException {

		Collection compositionBeanColl = new Vector();

		String query = "select * from " + TABLE + " " + getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			CompositionBean compositionBean = new CompositionBean();
			load(dataSetRow, compositionBean);
			compositionBeanColl.add(compositionBean);
		}

		return compositionBeanColl;
	}
}