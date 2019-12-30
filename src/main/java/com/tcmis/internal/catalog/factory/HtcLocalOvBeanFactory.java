package com.tcmis.internal.catalog.factory;


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
import com.tcmis.internal.catalog.beans.HtcLocalOvBean;


/******************************************************************************
 * CLASSNAME: HtcLocalOvBeanFactory <br>
 * @version: 1.0, Jun 1, 2010 <br>
 *****************************************************************************/


public class HtcLocalOvBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_ITEM_DESC = "ITEM_DESC";
	public String ATTRIBUTE_HARMONIZED_TRADE_CODE = "HARMONIZED_TRADE_CODE";
	public String ATTRIBUTE_HTC_LOCAL_COLLECTION = "HTC_LOCAL_COLLECTION";

	//table name
	public String TABLE = "HTC_LOCAL_OV";


	//constructor
	public HtcLocalOvBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	@Override
	public String getColumnName(String attributeName) {
		if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("itemDesc")) {
			return ATTRIBUTE_ITEM_DESC;
		}
		else if(attributeName.equals("harmonizedTradeCode")) {
			return ATTRIBUTE_HARMONIZED_TRADE_CODE;
		}
		else if(attributeName.equals("htcLocalCollection")) {
			return ATTRIBUTE_HTC_LOCAL_COLLECTION;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, HtcLocalOvBean.class);
	}


	//you need to verify the primary key(s) before uncommenting this
	/*
	//delete
	public int delete(HtcLocalOvBean htcLocalOvBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("itemId", "SearchCriterion.EQUALS",
			"" + htcLocalOvBean.getItemId());

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


	public int delete(HtcLocalOvBean htcLocalOvBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("itemId", "SearchCriterion.EQUALS",
			"" + htcLocalOvBean.getItemId());

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
	public int insert(HtcLocalOvBean htcLocalOvBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(htcLocalOvBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(HtcLocalOvBean htcLocalOvBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
			ATTRIBUTE_ITEM_ID + "," +
			ATTRIBUTE_ITEM_DESC + "," +
			ATTRIBUTE_HARMONIZED_TRADE_CODE + "," +
			ATTRIBUTE_HTC_LOCAL_COLLECTION + ")" +
			" values (" +
			htcLocalOvBean.getItemId() + "," +
			SqlHandler.delimitString(htcLocalOvBean.getItemDesc()) + "," +
			SqlHandler.delimitString(htcLocalOvBean.getHarmonizedTradeCode()) + "," +
			htcLocalOvBean.getHtcLocalCollection() + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(HtcLocalOvBean htcLocalOvBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(htcLocalOvBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(HtcLocalOvBean htcLocalOvBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_ITEM_ID + "=" +
				StringHandler.nullIfZero(htcLocalOvBean.getItemId()) + "," +
			ATTRIBUTE_ITEM_DESC + "=" +
				SqlHandler.delimitString(htcLocalOvBean.getItemDesc()) + "," +
			ATTRIBUTE_HARMONIZED_TRADE_CODE + "=" +
				SqlHandler.delimitString(htcLocalOvBean.getHarmonizedTradeCode()) + "," +
			ATTRIBUTE_HTC_LOCAL_COLLECTION + "=" +
				StringHandler.nullIfZero(htcLocalOvBean.getHtcLocalCollection()) + " " +
			"where " + ATTRIBUTE_ITEM_ID + "=" +
				htcLocalOvBean.getItemId();

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

		Collection htcLocalOvBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
		getWhereClause(criteria)+ getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			HtcLocalOvBean htcLocalOvBean = new HtcLocalOvBean();
			load(dataSetRow, htcLocalOvBean);
			htcLocalOvBeanColl.add(htcLocalOvBean);
		}

		return htcLocalOvBeanColl;
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
			map.put("TCM_OPS.HTC_LOCAL_OBJ",
					Class.forName(
					"com.tcmis.internal.catalog.beans.HtcLocalObjBean"));
			map.put("TCM_OPS.HTC_ITEM_OBJ",
					Class.forName(
					"com.tcmis.internal.catalog.beans.HtcLocalOvBean"));

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
		Collection htcLocalOvBeanColl = new Vector();

		String query = "select value(p) from " + TABLE + " p " +
		getWhereClause(criteria) + getOrderByClause(sortCriteria);
		if (log.isDebugEnabled()) {
			log.debug("QUERY:" + query);
		}
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				HtcLocalOvBean b = (HtcLocalOvBean) rs.getObject(1);
				htcLocalOvBeanColl.add(b);
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
		return htcLocalOvBeanColl;
	}

}