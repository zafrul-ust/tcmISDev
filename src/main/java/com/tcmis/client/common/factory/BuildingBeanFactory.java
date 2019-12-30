package com.tcmis.client.common.factory;


import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.common.beans.BuildingBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.SqlHandler;


/******************************************************************************
 * CLASSNAME: BuildingBeanFactory <br>
 * @version: 1.0, Jan 11, 2011 <br>
 *****************************************************************************/


public class BuildingBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(getClass());

	//column names
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_AREA_ID = "AREA_ID";
	public String ATTRIBUTE_BUILDING_ID = "BUILDING_ID";
	public String ATTRIBUTE_BUILDING_NAME = "BUILDING_NAME";
	public String ATTRIBUTE_BUILDING_DESCRIPTION = "BUILDING_DESCRIPTION";

	//table name
	public String TABLE = "BUILDING";


	//constructor
	public BuildingBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	@Override
	public String getColumnName(String attributeName) {
		if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("areaId")) {
			return ATTRIBUTE_AREA_ID;
		}
		else if(attributeName.equals("buildingId")) {
			return ATTRIBUTE_BUILDING_ID;
		}
		else if(attributeName.equals("buildingName")) {
			return ATTRIBUTE_BUILDING_NAME;
		}
		else if(attributeName.equals("buildingDescription")) {
			return ATTRIBUTE_BUILDING_DESCRIPTION;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, BuildingBean.class);
	}


	public SearchCriteria getKeyCriteria(BuildingBean buildingBean) {
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("companyId", SearchCriterion.EQUALS, "" + buildingBean.getCompanyId());
		criteria.addCriterion("areaId", SearchCriterion.EQUALS, "" + buildingBean.getAreaId());
		criteria.addCriterion("buildingId", SearchCriterion.EQUALS, "" + buildingBean.getBuildingId());
		return criteria;
	}


	//delete
	public int delete(BuildingBean buildingBean)
	throws BaseException {
		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			return this.delete(buildingBean, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}


	public int delete(BuildingBean buildingBean, Connection conn)
	throws BaseException {

		return delete(getKeyCriteria(buildingBean), conn);
	}

	public int delete(SearchCriteria criteria)
	throws BaseException {

		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			return delete(criteria, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}


	public int delete(SearchCriteria criteria, Connection conn)
	throws BaseException {

		String sqlQuery = " delete from " + TABLE + " " +
		getWhereClause(criteria);

		return new SqlManager().update(conn, sqlQuery);
	}


	//insert
	public int insert(BuildingBean buildingBean)
	throws BaseException {

		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			return insert(buildingBean, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}


	public int insert(BuildingBean buildingBean, Connection conn)
	throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = "insert into " + TABLE + " (";
		query += ATTRIBUTE_COMPANY_ID + ",";
		query += ATTRIBUTE_AREA_ID + ",";
		query += ATTRIBUTE_BUILDING_ID + ",";
		query += ATTRIBUTE_BUILDING_NAME + ",";
		query += ATTRIBUTE_BUILDING_DESCRIPTION + ")";
		query += " values (";
		query += SqlHandler.delimitString(buildingBean.getCompanyId()) + ",";
		query += SqlHandler.delimitString(buildingBean.getAreaId()) + ",";
		query += SqlHandler.delimitString(buildingBean.getBuildingId()) + ",";
		query += SqlHandler.delimitString(buildingBean.getBuildingName()) + ",";
		query += SqlHandler.delimitString(buildingBean.getBuildingDescription());
		query += ")";
		return sqlManager.update(conn, query);
	}


	//update
	public int update(BuildingBean buildingBean)
	throws BaseException {

		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			return update(buildingBean, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}


	public int update(BuildingBean buildingBean, Connection conn)
	throws BaseException {

		String query  = "update " + TABLE + " set ";
		query += ATTRIBUTE_BUILDING_NAME+ "=" + SqlHandler.delimitString(buildingBean.getBuildingName()) + ",";
		query += ATTRIBUTE_BUILDING_DESCRIPTION + "=" + SqlHandler.delimitString(buildingBean.getBuildingDescription()) + " ";
		query += getWhereClause(getKeyCriteria(buildingBean));

		return new SqlManager().update(conn, query);
	}

	//select
	public Collection<BuildingBean> select(SearchCriteria criteria, SortCriteria sortCriteria)
	throws BaseException {

		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			return select(criteria, sortCriteria, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}
	public Collection<BuildingBean> select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn)
	throws BaseException {

		Collection<BuildingBean> buildingBeanColl = new Vector<BuildingBean>();

		String query = "select * from " + TABLE + " " +
		getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			BuildingBean buildingBean = new BuildingBean();
			load(dataSetRow, buildingBean);
			buildingBeanColl.add(buildingBean);
		}

		return buildingBeanColl;
	}
}