package com.tcmis.internal.distribution.factory;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DbSelectException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.internal.distribution.beans.PriceListOvBean;


/******************************************************************************
 * CLASSNAME: PriceListOvBeanFactory <br>
 * @version: 1.0, Oct 30, 2009 <br>
 *****************************************************************************/


public class PriceListOvBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_OPS_COMPANY_ID = "OPS_COMPANY_ID";
	public String ATTRIBUTE_OPS_ENTITY_ID = "OPS_ENTITY_ID";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
	public String ATTRIBUTE_CATALOG_NAME = "CATALOG_NAME";
	public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
	public String ATTRIBUTE_PART_NAME = "PART_NAME";
	public String ATTRIBUTE_PART_DESC = "PART_DESC";
	public String ATTRIBUTE_SPEC_LIST = "SPEC_LIST";
	public String ATTRIBUTE_PART_GROUP_NO = "PART_GROUP_NO";
	public String ATTRIBUTE_PRICE_GROUP_ID = "PRICE_GROUP_ID";
	public String ATTRIBUTE_PRICE_GROUP_NAME = "PRICE_GROUP_NAME";
	public String ATTRIBUTE_START_DATE = "START_DATE";
	public String ATTRIBUTE_END_DATE = "END_DATE";
	public String ATTRIBUTE_CATALOG_PRICE = "CATALOG_PRICE";
	public String ATTRIBUTE_BASELINE_PRICE = "BASELINE_PRICE";
	public String ATTRIBUTE_CURRENCY_ID = "CURRENCY_ID";
	public String ATTRIBUTE_CATALOG_COMPANY_ID = "CATALOG_COMPANY_ID";
	public String ATTRIBUTE_CATALOG_COMPANY_NAME = "CATALOG_COMPANY_NAME";
	public String ATTRIBUTE_SPEC_LIST_ID = "SPEC_LIST_ID";
	public String ATTRIBUTE_PRICE_BREAK_COLLECTION = "PRICE_BREAK_COLLECTION";

	//table name
	public String TABLE = "PRICE_LIST_OV";


	//constructor
	public PriceListOvBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	@Override
	public String getColumnName(String attributeName) {
		if(attributeName.equals("opsCompanyId")) {
			return ATTRIBUTE_OPS_COMPANY_ID;
		}
		else if(attributeName.equals("opsEntityId")) {
			return ATTRIBUTE_OPS_ENTITY_ID;
		}
		else if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("catalogId")) {
			return ATTRIBUTE_CATALOG_ID;
		}
		else if(attributeName.equals("catalogName")) {
			return ATTRIBUTE_CATALOG_NAME;
		}
		else if(attributeName.equals("catPartNo")) {
			return ATTRIBUTE_CAT_PART_NO;
		}
		else if(attributeName.equals("partName")) {
			return ATTRIBUTE_PART_NAME;
		}
		else if(attributeName.equals("partDesc")) {
			return ATTRIBUTE_PART_DESC;
		}
		else if(attributeName.equals("specList")) {
			return ATTRIBUTE_SPEC_LIST;
		}
		else if(attributeName.equals("partGroupNo")) {
			return ATTRIBUTE_PART_GROUP_NO;
		}
		else if(attributeName.equals("priceGroupId")) {
			return ATTRIBUTE_PRICE_GROUP_ID;
		}
		else if(attributeName.equals("priceGroupName")) {
			return ATTRIBUTE_PRICE_GROUP_NAME;
		}
		else if(attributeName.equals("startDate")) {
			return ATTRIBUTE_START_DATE;
		}
		else if(attributeName.equals("endDate")) {
			return ATTRIBUTE_END_DATE;
		}
		else if(attributeName.equals("catalogPrice")) {
			return ATTRIBUTE_CATALOG_PRICE;
		}
		else if(attributeName.equals("baselinePrice")) {
			return ATTRIBUTE_BASELINE_PRICE;
		}
		else if(attributeName.equals("currencyId")) {
			return ATTRIBUTE_CURRENCY_ID;
		}
		else if(attributeName.equals("catalogCompanyId")) {
			return ATTRIBUTE_CATALOG_COMPANY_ID;
		}
		else if(attributeName.equals("catalogCompanyName")) {
			return ATTRIBUTE_CATALOG_COMPANY_NAME;
		}
		else if(attributeName.equals("specListId")) {
			return ATTRIBUTE_SPEC_LIST_ID;
		}
		else if(attributeName.equals("priceBreakCollection")) {
			return ATTRIBUTE_PRICE_BREAK_COLLECTION;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, PriceListOvBean.class);
	}


	//you need to verify the primary key(s) before uncommenting this
	/*
	//delete
	public int delete(PriceListOvBean priceListOvBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("opsCompanyId", "SearchCriterion.EQUALS",
			"" + priceListOvBean.getOpsCompanyId());

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


	public int delete(PriceListOvBean priceListOvBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("opsCompanyId", "SearchCriterion.EQUALS",
			"" + priceListOvBean.getOpsCompanyId());

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
	public int insert(PriceListOvBean priceListOvBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(priceListOvBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(PriceListOvBean priceListOvBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
			ATTRIBUTE_OPS_COMPANY_ID + "," +
			ATTRIBUTE_OPS_ENTITY_ID + "," +
			ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_CATALOG_ID + "," +
			ATTRIBUTE_CATALOG_NAME + "," +
			ATTRIBUTE_CAT_PART_NO + "," +
			ATTRIBUTE_PART_NAME + "," +
			ATTRIBUTE_PART_DESC + "," +
			ATTRIBUTE_SPEC_LIST + "," +
			ATTRIBUTE_PART_GROUP_NO + "," +
			ATTRIBUTE_PRICE_GROUP_ID + "," +
			ATTRIBUTE_PRICE_GROUP_NAME + "," +
			ATTRIBUTE_START_DATE + "," +
			ATTRIBUTE_END_DATE + "," +
			ATTRIBUTE_CATALOG_PRICE + "," +
			ATTRIBUTE_BASELINE_PRICE + "," +
			ATTRIBUTE_CURRENCY_ID + "," +
			ATTRIBUTE_CATALOG_COMPANY_ID + "," +
			ATTRIBUTE_CATALOG_COMPANY_NAME + "," +
			ATTRIBUTE_SPEC_LIST_ID + "," +
			ATTRIBUTE_PRICE_BREAK_COLLECTION + ")" +
			" values (" +
			SqlHandler.delimitString(priceListOvBean.getOpsCompanyId()) + "," +
			SqlHandler.delimitString(priceListOvBean.getOpsEntityId()) + "," +
			SqlHandler.delimitString(priceListOvBean.getCompanyId()) + "," +
			SqlHandler.delimitString(priceListOvBean.getCatalogId()) + "," +
			SqlHandler.delimitString(priceListOvBean.getCatalogName()) + "," +
			SqlHandler.delimitString(priceListOvBean.getCatPartNo()) + "," +
			SqlHandler.delimitString(priceListOvBean.getPartName()) + "," +
			SqlHandler.delimitString(priceListOvBean.getPartDesc()) + "," +
			SqlHandler.delimitString(priceListOvBean.getSpecList()) + "," +
			priceListOvBean.getPartGroupNo() + "," +
			SqlHandler.delimitString(priceListOvBean.getPriceGroupId()) + "," +
			SqlHandler.delimitString(priceListOvBean.getPriceGroupName()) + "," +
			DateHandler.getOracleToDateFunction(priceListOvBean.getStartDate()) + "," +
			DateHandler.getOracleToDateFunction(priceListOvBean.getEndDate()) + "," +
			priceListOvBean.getCatalogPrice() + "," +
			priceListOvBean.getBaselinePrice() + "," +
			SqlHandler.delimitString(priceListOvBean.getCurrencyId()) + "," +
			SqlHandler.delimitString(priceListOvBean.getCatalogCompanyId()) + "," +
			SqlHandler.delimitString(priceListOvBean.getCatalogCompanyName()) + "," +
			SqlHandler.delimitString(priceListOvBean.getSpecListId()) + "," +
			priceListOvBean.getPriceBreakCollection() + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(PriceListOvBean priceListOvBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(priceListOvBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(PriceListOvBean priceListOvBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_OPS_COMPANY_ID + "=" +
				SqlHandler.delimitString(priceListOvBean.getOpsCompanyId()) + "," +
			ATTRIBUTE_OPS_ENTITY_ID + "=" +
				SqlHandler.delimitString(priceListOvBean.getOpsEntityId()) + "," +
			ATTRIBUTE_COMPANY_ID + "=" +
				SqlHandler.delimitString(priceListOvBean.getCompanyId()) + "," +
			ATTRIBUTE_CATALOG_ID + "=" +
				SqlHandler.delimitString(priceListOvBean.getCatalogId()) + "," +
			ATTRIBUTE_CATALOG_NAME + "=" +
				SqlHandler.delimitString(priceListOvBean.getCatalogName()) + "," +
			ATTRIBUTE_CAT_PART_NO + "=" +
				SqlHandler.delimitString(priceListOvBean.getCatPartNo()) + "," +
			ATTRIBUTE_PART_NAME + "=" +
				SqlHandler.delimitString(priceListOvBean.getPartName()) + "," +
			ATTRIBUTE_PART_DESC + "=" +
				SqlHandler.delimitString(priceListOvBean.getPartDesc()) + "," +
			ATTRIBUTE_SPEC_LIST + "=" +
				SqlHandler.delimitString(priceListOvBean.getSpecList()) + "," +
			ATTRIBUTE_PART_GROUP_NO + "=" +
				StringHandler.nullIfZero(priceListOvBean.getPartGroupNo()) + "," +
			ATTRIBUTE_PRICE_GROUP_ID + "=" +
				SqlHandler.delimitString(priceListOvBean.getPriceGroupId()) + "," +
			ATTRIBUTE_PRICE_GROUP_NAME + "=" +
				SqlHandler.delimitString(priceListOvBean.getPriceGroupName()) + "," +
			ATTRIBUTE_START_DATE + "=" +
				DateHandler.getOracleToDateFunction(priceListOvBean.getStartDate()) + "," +
			ATTRIBUTE_END_DATE + "=" +
				DateHandler.getOracleToDateFunction(priceListOvBean.getEndDate()) + "," +
			ATTRIBUTE_CATALOG_PRICE + "=" +
				StringHandler.nullIfZero(priceListOvBean.getCatalogPrice()) + "," +
			ATTRIBUTE_BASELINE_PRICE + "=" +
				StringHandler.nullIfZero(priceListOvBean.getBaselinePrice()) + "," +
			ATTRIBUTE_CURRENCY_ID + "=" +
				SqlHandler.delimitString(priceListOvBean.getCurrencyId()) + "," +
			ATTRIBUTE_CATALOG_COMPANY_ID + "=" +
				SqlHandler.delimitString(priceListOvBean.getCatalogCompanyId()) + "," +
			ATTRIBUTE_CATALOG_COMPANY_NAME + "=" +
				SqlHandler.delimitString(priceListOvBean.getCatalogCompanyName()) + "," +
			ATTRIBUTE_SPEC_LIST_ID + "=" +
				SqlHandler.delimitString(priceListOvBean.getSpecListId()) + "," +
			ATTRIBUTE_PRICE_BREAK_COLLECTION + "=" +
				StringHandler.nullIfZero(priceListOvBean.getPriceBreakCollection()) + " " +
			"where " + ATTRIBUTE_OPS_COMPANY_ID + "=" +
				priceListOvBean.getOpsCompanyId();

		return new SqlManager().update(conn, query);
	}
	 */

	//select
	public Collection select(SearchCriteria criteria)
	throws BaseException {

		return select(criteria,null);

	}
	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria)
	throws BaseException {

		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = select(criteria,sortCriteria, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}
	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn)
	throws BaseException {

		Collection priceListOvBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
		getWhereClause(criteria)+ getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			PriceListOvBean priceListOvBean = new PriceListOvBean();
			load(dataSetRow, priceListOvBean);
			priceListOvBeanColl.add(priceListOvBean);
		}

		return priceListOvBeanColl;
	}

	public Collection selectObject(SearchCriteria criteria) throws BaseException {
		return selectObject(criteria,null);
	}

	public Collection selectObject(SearchCriteria criteria, SortCriteria sortCriteria) throws BaseException {
		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			java.util.Map map = connection.getTypeMap();
			map.put("TCM_OPS.CPP_PRICES_OBJ",
					Class.forName(
					"com.tcmis.internal.distribution.beans.PriceListOvBean"));
			map.put("TCM_OPS.CPP_PRICE_BREAK_OBJ",
					Class.forName(
					"com.tcmis.internal.distribution.beans.CppPriceBreakObjBean"));

			c = selectObject(criteria, sortCriteria,connection);
		}
		catch (Exception e) {
			log.error("selectObject error:" + e.getMessage());
			DbSelectException ex = new DbSelectException("error.db.select");
			ex.setRootCause(e);
			throw ex;
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}

	public Collection selectObject(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn) throws
	BaseException {
		Collection priceListOvBeanColl = new Vector();

		String query = "select value(p) from " + TABLE + " p " +
		getWhereClause(criteria) + getOrderByClause(sortCriteria);
		if (log.isDebugEnabled()) {
			log.debug("QUERY:" + query);
		}
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				PriceListOvBean b = (PriceListOvBean) rs.getObject(1);
				priceListOvBeanColl.add(b);
			}
			rs.close();
			st.close();
		}
		catch (SQLException e) {
			log.error("selectObject error:" + e.getMessage());
			DbSelectException ex = new DbSelectException("error.db.select");
			ex.setRootCause(e);
			throw ex;
		}
		return priceListOvBeanColl;
	}

}