package com.tcmis.client.het.factory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.het.beans.HetMethodAreaBldgAppBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: HetMethodAreaBldgAppBeanFactory <br>
 * 
 * @version: 1.0, Aug 11, 2011 <br>
 *****************************************************************************/

public class HetMethodAreaBldgAppBeanFactory extends BaseBeanFactory {

	Log				log							= LogFactory.getLog(this.getClass());

	// column names
	public String	ATTRIBUTE_METHOD_ID			= "METHOD_ID";
	public String	ATTRIBUTE_AREA_ID			= "AREA_ID";
	public String	ATTRIBUTE_BUILDING_ID		= "BUILDING_ID";
	public String	ATTRIBUTE_APPLICATION_ID	= "APPLICATION_ID";
	public String	ATTRIBUTE_COMPANY_ID		= "COMPANY_ID";
	public String	ATTRIBUTE_FACILITY_ID		= "FACILITY_ID";

	// table name
	public String	TABLE						= "HET_METHOD_AREA_BLDG_APP";

	// constructor
	public HetMethodAreaBldgAppBeanFactory(DbManager dbManager) {
		super(dbManager);
	}

	// get column names
	@Override
	public String getColumnName(String attributeName) {
		if (attributeName.equals("methodId")) {
			return ATTRIBUTE_METHOD_ID;
		}
		else if (attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if (attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if (attributeName.equals("areaId")) {
			return ATTRIBUTE_AREA_ID;
		}
		else if (attributeName.equals("buildingId")) {
			return ATTRIBUTE_BUILDING_ID;
		}
		else if (attributeName.equals("applicationId")) {
			return ATTRIBUTE_APPLICATION_ID;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}

	// get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, HetMethodAreaBldgAppBean.class);
	}

	// you need to verify the primary key(s) before uncommenting this

	// delete
	public int delete(HetMethodAreaBldgAppBean hetMethodAreaBldgAppBean) throws BaseException {

		SearchCriteria criteria = new SearchCriteria("methodId", SearchCriterion.EQUALS, "" + hetMethodAreaBldgAppBean.getMethodId());

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

	public int delete(HetMethodAreaBldgAppBean hetMethodAreaBldgAppBean, Connection conn) throws BaseException {

		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("methodId", SearchCriterion.EQUALS, "" + hetMethodAreaBldgAppBean.getMethodId());
		criteria.addCriterion("companyId", SearchCriterion.EQUALS, "" + hetMethodAreaBldgAppBean.getCompanyId());
		criteria.addCriterion("facilityId", SearchCriterion.EQUALS, "" + hetMethodAreaBldgAppBean.getFacilityId());
		criteria.addCriterion("areaId", SearchCriterion.EQUALS, "" + hetMethodAreaBldgAppBean.getAreaId());
		criteria.addCriterion("buildingId", SearchCriterion.EQUALS, "" + hetMethodAreaBldgAppBean.getBuildingId());
		criteria.addCriterion("applicationId", SearchCriterion.EQUALS, "" + hetMethodAreaBldgAppBean.getApplicationId());

		return delete(criteria, conn);
	}

	public int delete(SearchCriteria criteria) throws BaseException {

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

	public int delete(SearchCriteria criteria, Connection conn) throws BaseException {

		String sqlQuery = " delete from " + TABLE + " " + getWhereClause(criteria);

		return new SqlManager().update(conn, sqlQuery);
	}

	// you need to verify the primary key(s) before uncommenting this

	// insert
	public int insert(HetMethodAreaBldgAppBean hetMethodAreaBldgAppBean) throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(hetMethodAreaBldgAppBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}

	public int insert(HetMethodAreaBldgAppBean hetMethodAreaBldgAppBean, Connection conn) throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query = "insert into " + TABLE + " (";
		query += ATTRIBUTE_METHOD_ID + ",";
		query += ATTRIBUTE_COMPANY_ID + ",";
		query += ATTRIBUTE_FACILITY_ID + ",";
		query += ATTRIBUTE_AREA_ID + ",";
		query += ATTRIBUTE_BUILDING_ID + ",";
		query += ATTRIBUTE_APPLICATION_ID + ") values (";
		query += hetMethodAreaBldgAppBean.getMethodId() + ",'";
		query += hetMethodAreaBldgAppBean.getCompanyId() + "','";
		query += hetMethodAreaBldgAppBean.getFacilityId() + "',";
		query += hetMethodAreaBldgAppBean.getAreaId() + ",";
		query += hetMethodAreaBldgAppBean.getBuildingId() + ",";
		query += hetMethodAreaBldgAppBean.getApplicationId() + ")";

		return sqlManager.update(conn, query);
	}


	// select
	public Collection select(SearchCriteria criteria) throws BaseException {

		return select(criteria, null);

	}

	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria) throws BaseException {

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

	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn) throws BaseException {

		Collection hetMethodAreaBldgAppBeanColl = new Vector();

		String query = "select * from " + TABLE + " " + getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			HetMethodAreaBldgAppBean hetMethodAreaBldgAppBean = new HetMethodAreaBldgAppBean();
			load(dataSetRow, hetMethodAreaBldgAppBean);
			hetMethodAreaBldgAppBeanColl.add(hetMethodAreaBldgAppBean);
		}

		return hetMethodAreaBldgAppBeanColl;
	}

	public boolean methodHasLocations(BigDecimal methodId) throws BaseException {
		Connection connection = null;
		try {
			StringBuilder query = new StringBuilder("select COUNT(*) from ").append(TABLE).append(" where method_id = ").append(methodId);
			connection = getDbManager().getConnection();
			DataSetRow result = (DataSetRow) new SqlManager().select(connection, query.toString()).iterator().next();
			return !"0".equals(result.getString("COUNT(*)"));
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}

}