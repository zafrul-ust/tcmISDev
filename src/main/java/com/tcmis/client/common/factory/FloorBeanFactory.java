package com.tcmis.client.common.factory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.common.beans.FloorBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DbSelectException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.Globals;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: FloorBeanFactory <br>
 * @version: 1.0, Jan 12, 2012 <br>
 *****************************************************************************/

public class FloorBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(getClass());

	//column names
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_BUILDING_ID = "BUILDING_ID";
	public String ATTRIBUTE_FLOOR_ID = "FLOOR_ID";
	public String ATTRIBUTE_DESCRIPTION = "DESCRIPTION";

	//table name
	public String TABLE = "FLOOR";

	//constructor
	public FloorBeanFactory(DbManager dbManager) {
		super(dbManager);
	}

	//get column names
	@Override
	public String getColumnName(String attributeName) {
		if (attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if (attributeName.equals("buildingId")) {
			return ATTRIBUTE_BUILDING_ID;
		}
		else if (attributeName.equals("floorId")) {
			return ATTRIBUTE_FLOOR_ID;
		}
		else if (attributeName.equals("description")) {
			return ATTRIBUTE_DESCRIPTION;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}

	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, FloorBean.class);
	}

	public SearchCriteria getKeyCriteria(FloorBean floorBean) {
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("companyId", SearchCriterion.EQUALS, "" + floorBean.getCompanyId());
		criteria.addCriterion("floorId", SearchCriterion.EQUALS, "" + floorBean.getFloorId());
		return criteria;
	}

	//delete
	public int delete(FloorBean floorBean) throws BaseException {
		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			return this.delete(floorBean, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}

	public int delete(FloorBean floorBean, Connection conn) throws BaseException {

		return delete(getKeyCriteria(floorBean), conn);
	}

	public int delete(SearchCriteria criteria) throws BaseException {

		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			return delete(criteria, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}

	public int delete(SearchCriteria criteria, Connection conn) throws BaseException {

		String sqlQuery = " delete from " + TABLE + " " + getWhereClause(criteria);

		return new SqlManager().update(conn, sqlQuery);
	}

	public int insert(FloorBean floorBean)
	throws BaseException {

		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			return insert(floorBean, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}


	public int insert(FloorBean floorBean, Connection conn)
	throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = "insert into " + TABLE + " (";
		query += ATTRIBUTE_COMPANY_ID + ",";
		query += ATTRIBUTE_BUILDING_ID + ",";
		query += ATTRIBUTE_FLOOR_ID + ",";
		query += ATTRIBUTE_DESCRIPTION + ")";
		query += " values (";
		query += SqlHandler.delimitString(floorBean.getCompanyId()) + ",";
		query += floorBean.getBuildingId() + ",";
		query += floorBean.getFloorId() + ",";
		query += SqlHandler.delimitString(floorBean.getDescription());
		query += ")";
		return sqlManager.update(conn, query);
	}


	//update
	public int update(FloorBean floorBean)
	throws BaseException {

		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			return update(floorBean, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}


	public int update(FloorBean floorBean, Connection conn)
	throws BaseException {

		String query  = "update " + TABLE + " set ";
		query += ATTRIBUTE_COMPANY_ID + "=" + SqlHandler.delimitString(floorBean.getCompanyId()) + ",";
		query += ATTRIBUTE_BUILDING_ID + "=" + StringHandler.nullIfZero(floorBean.getBuildingId()) + ",";
		query += ATTRIBUTE_DESCRIPTION + "=" + SqlHandler.delimitString(floorBean.getDescription()) + " ";
		query += getWhereClause(getKeyCriteria(floorBean));

		return new SqlManager().update(conn, query);
	}

	//select
	public Collection<FloorBean> select(SearchCriteria criteria, SortCriteria sortCriteria) throws BaseException {

		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			return select(criteria, sortCriteria, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}

	public Collection<FloorBean> select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn) throws BaseException {

		Collection<FloorBean> floorBeanColl = new Vector<FloorBean>();

		String query = "select * from " + TABLE + " " + getWhereClauseForPreparedStatement(criteria) + getOrderByClause(sortCriteria);
		try {
			PreparedStatement statement = conn.prepareStatement(query);
			setPreparedStatementParameters(statement, criteria, 1);
			if (log.isDebugEnabled()) {
				log.debug(getDebugPreparedStatementQuery(query, criteria));
			}
			DataSet dataSet = new SqlManager().select(statement);

			Iterator dataIter = dataSet.iterator();

			while (dataIter.hasNext()) {
				DataSetRow dataSetRow = (DataSetRow) dataIter.next();
				FloorBean floorBean = new FloorBean();
				load(dataSetRow, floorBean);
				floorBeanColl.add(floorBean);
			}
		}
		catch (SQLException e) {
			log.error("ERROR:" + e.getMessage(), e);
			DbSelectException ex = new DbSelectException(Globals.DB_SELECT_EXCEPTION);
			ex.setRootCause(e);
			throw ex;
		}
		return floorBeanColl;
	}
}