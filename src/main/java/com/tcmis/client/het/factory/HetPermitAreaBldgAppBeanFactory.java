package com.tcmis.client.het.factory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.het.beans.HetPermitAreaBldgAppBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;

/******************************************************************************
 * CLASSNAME: HetPermitAreaBldgAppBeanFactory <br>
 * @version: 1.0, Aug 9, 2011 <br>
 *****************************************************************************/

public class HetPermitAreaBldgAppBeanFactory extends BaseBeanFactory {
	Log log = LogFactory.getLog(getClass());

	//column names
	public String ATTRIBUTE_APPLICATION_ID = "APPLICATION_ID";
	public String ATTRIBUTE_AREA_ID = "AREA_ID";
	public String ATTRIBUTE_BUILDING_ID = "BUILDING_ID";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_PERMIT_ID = "PERMIT_ID";

	//table name
	public String TABLE = "HET_PERMIT_AREA_BLDG_APP";

	//constructor
	public HetPermitAreaBldgAppBeanFactory(DbManager dbManager) {
		super(dbManager);
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

	public SearchCriteria getKeyCriteria(HetPermitAreaBldgAppBean hetPermitAreaBldgAppBean) {
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("permitId", SearchCriterion.EQUALS, "" + hetPermitAreaBldgAppBean.getPermitId());
		criteria.addCriterion("companyId", SearchCriterion.EQUALS, "" + hetPermitAreaBldgAppBean.getCompanyId());
		criteria.addCriterion("facilityId", SearchCriterion.EQUALS, "" + hetPermitAreaBldgAppBean.getFacilityId());
		criteria.addCriterion("areaId", SearchCriterion.EQUALS, "" + hetPermitAreaBldgAppBean.getAreaId());
		criteria.addCriterion("buildingId", SearchCriterion.EQUALS, "" + hetPermitAreaBldgAppBean.getBuildingId());
		criteria.addCriterion("applicationId", SearchCriterion.EQUALS, "" + hetPermitAreaBldgAppBean.getApplicationId());
		return criteria;
	}

	//delete
	public int delete(HetPermitAreaBldgAppBean hetPermitAreaBldgAppBean) throws BaseException {
		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			return this.delete(hetPermitAreaBldgAppBean, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}

	public int delete(HetPermitAreaBldgAppBean hetPermitAreaBldgAppBean, Connection conn) throws BaseException {

		return delete(getKeyCriteria(hetPermitAreaBldgAppBean), conn);
	}

	//get column names
	@Override
	public String getColumnName(String attributeName) {
		if (attributeName.equals("permitId")) {
			return ATTRIBUTE_PERMIT_ID;
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

	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, HetPermitAreaBldgAppBean.class);
	}

	//insert
	public int insert(HetPermitAreaBldgAppBean hetPermitAreaBldgAppBean) throws BaseException {

		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			return insert(hetPermitAreaBldgAppBean, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}

	public int insert(HetPermitAreaBldgAppBean hetPermitAreaBldgAppBean, Connection conn) throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query = "insert into " + TABLE + " (";
		query += ATTRIBUTE_PERMIT_ID + ",";
		query += ATTRIBUTE_COMPANY_ID + ",";
		query += ATTRIBUTE_FACILITY_ID + ",";
		query += ATTRIBUTE_AREA_ID + ",";
		query += ATTRIBUTE_BUILDING_ID + ",";
		query += ATTRIBUTE_APPLICATION_ID + ")";
		query += " values (";
		query += hetPermitAreaBldgAppBean.getPermitId() + ",'";
		query += hetPermitAreaBldgAppBean.getCompanyId() + "','";
		query += hetPermitAreaBldgAppBean.getFacilityId() + "',";
		query += hetPermitAreaBldgAppBean.getAreaId() + ",";
		query += hetPermitAreaBldgAppBean.getBuildingId() + ",";
		query += hetPermitAreaBldgAppBean.getApplicationId();
		query += ")";
		return sqlManager.update(conn, query);
	}

	//select
	public Collection<HetPermitAreaBldgAppBean> select(SearchCriteria criteria, SortCriteria sortCriteria) throws BaseException {

		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			return select(criteria, sortCriteria, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}

	public Collection<HetPermitAreaBldgAppBean> select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn) throws BaseException {

		Collection<HetPermitAreaBldgAppBean> hetPermitAreaBldgAppBeanColl = new Vector<HetPermitAreaBldgAppBean>();

		String query = "select * from " + TABLE + " " + getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			HetPermitAreaBldgAppBean hetPermitAreaBldgAppBean = new HetPermitAreaBldgAppBean();
			load(dataSetRow, hetPermitAreaBldgAppBean);
			hetPermitAreaBldgAppBeanColl.add(hetPermitAreaBldgAppBean);
		}

		return hetPermitAreaBldgAppBeanColl;
	}

	public boolean permitHasLocations(BigDecimal permitId) throws BaseException {
		Connection connection = null;
		try {
			StringBuilder query = new StringBuilder("select COUNT(*) from ").append(TABLE).append(" where permit_id = ").append(permitId);
			connection = getDbManager().getConnection();
			DataSetRow result = (DataSetRow) new SqlManager().select(connection, query.toString()).iterator().next() ;
			return !"0".equals(result.getString("COUNT(*)"));
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}

}