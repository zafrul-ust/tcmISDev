package com.tcmis.client.common.factory;


import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.common.beans.AreaBean;
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
 * CLASSNAME: AreaBeanFactory <br>
 * @version: 1.0, Jan 11, 2011 <br>
 *****************************************************************************/


public class AreaBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(getClass());

	//column names
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_AREA_ID = "AREA_ID";
	public String ATTRIBUTE_AREA_NAME = "AREA_NAME";
	public String ATTRIBUTE_AREA_DESCRIPTION = "AREA_DESCRIPTION";
	public String ATTRIBUTE_ORGANIZATION = "ORGANIZATION";

	//table name
	public String TABLE = "AREA";


	//constructor
	public AreaBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	@Override
	public String getColumnName(String attributeName) {
		if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if(attributeName.equals("areaId")) {
			return ATTRIBUTE_AREA_ID;
		}
		else if(attributeName.equals("areaName")) {
			return ATTRIBUTE_AREA_NAME;
		}
		else if(attributeName.equals("areaDescription")) {
			return ATTRIBUTE_AREA_DESCRIPTION;
		}
		else if(attributeName.equals("orginization")) {
			return ATTRIBUTE_ORGANIZATION;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, AreaBean.class);
	}


	public SearchCriteria getKeyCriteria(AreaBean AreaBean) {
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("companyId", SearchCriterion.EQUALS, "" + AreaBean.getCompanyId());
		criteria.addCriterion("facilityId", SearchCriterion.EQUALS, "" + AreaBean.getFacilityId());
		criteria.addCriterion("areaId", SearchCriterion.EQUALS, "" + AreaBean.getAreaId());
		return criteria;
	}


	//delete
	public int delete(AreaBean AreaBean)
	throws BaseException {
		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			return this.delete(AreaBean, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}


	public int delete(AreaBean AreaBean, Connection conn)
	throws BaseException {

		return delete(getKeyCriteria(AreaBean), conn);
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
	public int insert(AreaBean AreaBean)
	throws BaseException {

		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			return insert(AreaBean, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}


	public int insert(AreaBean AreaBean, Connection conn)
	throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = "insert into " + TABLE + " (";
		query += ATTRIBUTE_COMPANY_ID + ",";
		query += ATTRIBUTE_FACILITY_ID = "FACILITY_ID" + ",";
		query += ATTRIBUTE_AREA_ID + ",";
		query += ATTRIBUTE_AREA_DESCRIPTION + ",";
		query += ATTRIBUTE_AREA_NAME + ")";
		query += " values (";
		query += SqlHandler.delimitString(AreaBean.getCompanyId()) + ",";
		query += SqlHandler.delimitString(AreaBean.getFacilityId()) + ",";
		query += SqlHandler.delimitString(AreaBean.getAreaId()) + ",";
		query += SqlHandler.delimitString(AreaBean.getAreaDescription()) + ",";
		query += SqlHandler.delimitString(AreaBean.getAreaName());
		query += ")";
		return sqlManager.update(conn, query);
	}


	//update
	public int update(AreaBean AreaBean)
	throws BaseException {

		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			return update(AreaBean, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}


	public int update(AreaBean AreaBean, Connection conn)
	throws BaseException {

		String query  = "update " + TABLE + " set ";
		query += ATTRIBUTE_AREA_NAME + "=" + SqlHandler.delimitString(AreaBean.getAreaName()) + ",";
		query += ATTRIBUTE_AREA_DESCRIPTION + "=" + SqlHandler.delimitString(AreaBean.getAreaDescription()) + " ";
		query += getWhereClause(getKeyCriteria(AreaBean));

		return new SqlManager().update(conn, query);
	}

	//select
	public Collection<AreaBean> select(SearchCriteria criteria, SortCriteria sortCriteria)
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
	public Collection<AreaBean> select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn)
	throws BaseException {

		Collection<AreaBean> AreaBeanColl = new Vector<AreaBean>();

		String query = "select * from " + TABLE + " " +
		getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			AreaBean AreaBean = new AreaBean();
			load(dataSetRow, AreaBean);
			AreaBeanColl.add(AreaBean);
		}

		return AreaBeanColl;
	}
}