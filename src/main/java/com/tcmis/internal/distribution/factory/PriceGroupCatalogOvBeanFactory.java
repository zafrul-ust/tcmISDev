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
import com.tcmis.internal.distribution.beans.PriceGroupCatalogOvBean;


/******************************************************************************
 * CLASSNAME: PriceGroupCatalogOvBeanFactory <br>
 * @version: 1.0, Oct 30, 2009 <br>
 *****************************************************************************/


public class PriceGroupCatalogOvBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_PRICE_GROUP_ID = "PRICE_GROUP_ID";
	public String ATTRIBUTE_OPS_ENTITY_ID = "OPS_ENTITY_ID";
	public String ATTRIBUTE_OPS_COMPANY_ID = "OPS_COMPANY_ID";
	public String ATTRIBUTE_CATALOG_COMPANY_ID = "CATALOG_COMPANY_ID";
	public String ATTRIBUTE_CATALOG_COMPANY_NAME = "CATALOG_COMPANY_NAME";
	public String ATTRIBUTE_CATALOG_COLLECTION = "CATALOG_COLLECTION";

	//table name
	public String TABLE = "PRICE_GROUP_CATALOG_OV";


	//constructor
	public PriceGroupCatalogOvBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	@Override
	public String getColumnName(String attributeName) {
		if(attributeName.equals("priceGroupId")) {
			return ATTRIBUTE_PRICE_GROUP_ID;
		}
		else if(attributeName.equals("opsEntityId")) {
			return ATTRIBUTE_OPS_ENTITY_ID;
		}
		else if(attributeName.equals("opsCompanyId")) {
			return ATTRIBUTE_OPS_COMPANY_ID;
		}
		else if(attributeName.equals("catalogCompanyId")) {
			return ATTRIBUTE_CATALOG_COMPANY_ID;
		}
		else if(attributeName.equals("catalogCompanyName")) {
			return ATTRIBUTE_CATALOG_COMPANY_NAME;
		}
		else if(attributeName.equals("catalogCollection")) {
			return ATTRIBUTE_CATALOG_COLLECTION;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, PriceGroupCatalogOvBean.class);
	}


	//you need to verify the primary key(s) before uncommenting this
	/*
	//delete
	public int delete(PriceGroupCatalogOvBean priceGroupCatalogOvBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("priceGroupId", "SearchCriterion.EQUALS",
			"" + priceGroupCatalogOvBean.getPriceGroupId());

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


	public int delete(PriceGroupCatalogOvBean priceGroupCatalogOvBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("priceGroupId", "SearchCriterion.EQUALS",
			"" + priceGroupCatalogOvBean.getPriceGroupId());

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
	public int insert(PriceGroupCatalogOvBean priceGroupCatalogOvBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(priceGroupCatalogOvBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(PriceGroupCatalogOvBean priceGroupCatalogOvBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
			ATTRIBUTE_PRICE_GROUP_ID + "," +
			ATTRIBUTE_OPS_ENTITY_ID + "," +
			ATTRIBUTE_OPS_COMPANY_ID + "," +
			ATTRIBUTE_CATALOG_COMPANY_ID + "," +
			ATTRIBUTE_CATALOG_COMPANY_NAME + "," +
			ATTRIBUTE_CATALOG_COLLECTION + ")" +
			" values (" +
			SqlHandler.delimitString(priceGroupCatalogOvBean.getPriceGroupId()) + "," +
			SqlHandler.delimitString(priceGroupCatalogOvBean.getOpsEntityId()) + "," +
			SqlHandler.delimitString(priceGroupCatalogOvBean.getOpsCompanyId()) + "," +
			SqlHandler.delimitString(priceGroupCatalogOvBean.getCatalogCompanyId()) + "," +
			SqlHandler.delimitString(priceGroupCatalogOvBean.getCatalogCompanyName()) + "," +
			priceGroupCatalogOvBean.getCatalogCollection() + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(PriceGroupCatalogOvBean priceGroupCatalogOvBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(priceGroupCatalogOvBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(PriceGroupCatalogOvBean priceGroupCatalogOvBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_PRICE_GROUP_ID + "=" +
				SqlHandler.delimitString(priceGroupCatalogOvBean.getPriceGroupId()) + "," +
			ATTRIBUTE_OPS_ENTITY_ID + "=" +
				SqlHandler.delimitString(priceGroupCatalogOvBean.getOpsEntityId()) + "," +
			ATTRIBUTE_OPS_COMPANY_ID + "=" +
				SqlHandler.delimitString(priceGroupCatalogOvBean.getOpsCompanyId()) + "," +
			ATTRIBUTE_CATALOG_COMPANY_ID + "=" +
				SqlHandler.delimitString(priceGroupCatalogOvBean.getCatalogCompanyId()) + "," +
			ATTRIBUTE_CATALOG_COMPANY_NAME + "=" +
				SqlHandler.delimitString(priceGroupCatalogOvBean.getCatalogCompanyName()) + "," +
			ATTRIBUTE_CATALOG_COLLECTION + "=" +
				StringHandler.nullIfZero(priceGroupCatalogOvBean.getCatalogCollection()) + " " +
			"where " + ATTRIBUTE_PRICE_GROUP_ID + "=" +
				priceGroupCatalogOvBean.getPriceGroupId();

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

		Collection priceGroupCatalogOvBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
		getWhereClause(criteria)+ getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			PriceGroupCatalogOvBean priceGroupCatalogOvBean = new PriceGroupCatalogOvBean();
			load(dataSetRow, priceGroupCatalogOvBean);
			priceGroupCatalogOvBeanColl.add(priceGroupCatalogOvBean);
		}

		return priceGroupCatalogOvBeanColl;
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
			map.put("TCM_OPS.PRICE_GROUP_CATALOG_OBJ",
					Class.forName(
					"com.tcmis.internal.distribution.beans.PriceGroupCatalogOvBean"));
			map.put("CUSTOMER.CATALOG_OBJ",
					Class.forName(
					"com.tcmis.internal.distribution.beans.CatalogObjBean"));

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
		Collection priceGroupCatalogOvBeanColl = new Vector();

		String query = "select value(p) from " + TABLE + " p " +
		getWhereClause(criteria) + getOrderByClause(sortCriteria);
		if (log.isDebugEnabled()) {
			log.debug("QUERY:" + query);
		}
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				PriceGroupCatalogOvBean b = (PriceGroupCatalogOvBean) rs.getObject(1);
				priceGroupCatalogOvBeanColl.add(b);
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
		return priceGroupCatalogOvBeanColl;
	}

}