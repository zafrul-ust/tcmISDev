package com.tcmis.client.het.factory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.het.beans.HetPermitBean;
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
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: HetPermitBeanFactory <br>
 * @version: 1.0, May 31, 2011 <br>
 *****************************************************************************/

public class HetPermitBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(getClass());

	//column names
	public String ATTRIBUTE_AREA_ID = "AREA_ID";
	public String ATTRIBUTE_AREA_NAME = "AREA_NAME";
	public String ATTRIBUTE_BUILDING_ID = "BUILDING_ID";
	public String ATTRIBUTE_BUILDING_NAME = "BUILDING_NAME";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_CONTROL_DEVICE = "CONTROL_DEVICE";
	public String ATTRIBUTE_DESCRIPTION = "DESCRIPTION";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_PERMIT_ID = "PERMIT_ID";
	public String ATTRIBUTE_PERMIT_NAME = "PERMIT_NAME";
	public String ATTRIBUTE_STATUS = "STATUS";

	//table name
	public String SELECT_TABLE = "HET_PERMIT P, BUILDING B, AREA A";
	public String TABLE = "HET_PERMIT";

	//constructor
	public HetPermitBeanFactory(DbManager dbManager) {
		super(dbManager);
	}

	//get column names
	@Override
	public String getColumnName(String attributeName) {
		if (attributeName.equals("companyId")) {
			return (inWhere ? "P." : "") + ATTRIBUTE_COMPANY_ID;
		}
		else if (attributeName.equals("areaId")) {
			return ATTRIBUTE_AREA_ID;
		}
		else if (attributeName.equals("areaName")) {
			return ATTRIBUTE_AREA_NAME;
		}
		else if (attributeName.equals("buildingId")) {
			return (inWhere ? "P." : "") +  ATTRIBUTE_BUILDING_ID;
		}
		else if (attributeName.equals("buildingName")) {
			return ATTRIBUTE_BUILDING_NAME;
		}
		else if (attributeName.equals("controlDevice")) {
			return ATTRIBUTE_CONTROL_DEVICE;
		}
		else if (attributeName.equals("description")) {
			return ATTRIBUTE_DESCRIPTION;
		}
		else if (attributeName.equals("facilityId")) {
			return  (inWhere ? "P." : "") + ATTRIBUTE_FACILITY_ID;
		}
		else if (attributeName.equals("permitId")) {
			return ATTRIBUTE_PERMIT_ID;
		}
		else if (attributeName.equals("permitName")) {
			return ATTRIBUTE_PERMIT_NAME;
		}
		else if (attributeName.equals("status")) {
			return  (inWhere ? "P." : "") + ATTRIBUTE_STATUS;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}

	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, HetPermitBean.class);
	}

	public SearchCriteria getKeyCriteria(HetPermitBean HetPermitBean) {
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("companyId", SearchCriterion.EQUALS, "" + HetPermitBean.getCompanyId());
		criteria.addCriterion("permitId", SearchCriterion.EQUALS, "" + HetPermitBean.getPermitId());
		return criteria;
	}

	//delete
	public int delete(HetPermitBean HetPermitBean) throws BaseException {
		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			return this.delete(HetPermitBean, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}

	public int delete(HetPermitBean HetPermitBean, Connection conn) throws BaseException {

		return delete(getKeyCriteria(HetPermitBean), conn);
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

	public HetPermitBean getPermit(Object permitId) throws BaseException {
		SearchCriteria criteria = new SearchCriteria("permitId", SearchCriterion.EQUALS, "" + permitId);
		Collection results = select(criteria, null);
		return results.isEmpty() ? null : (HetPermitBean)results.iterator().next();
	}


	public int insert(HetPermitBean HetPermitBean) throws BaseException {

		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			return insert(HetPermitBean, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}

	public int insert(HetPermitBean HetPermitBean, Connection conn) throws BaseException {
		int newId = getDbManager().getOracleSequence("HET_MISC_SEQ");
		HetPermitBean.setPermitId(new BigDecimal(newId));
		SqlManager sqlManager = new SqlManager();

		String query = "insert into " + TABLE + " (";
		query += ATTRIBUTE_COMPANY_ID + ",";
		query += ATTRIBUTE_CONTROL_DEVICE + ",";
		query += ATTRIBUTE_FACILITY_ID + ",";
		query += ATTRIBUTE_PERMIT_ID + ",";
		query += ATTRIBUTE_BUILDING_ID + ",";
		query += ATTRIBUTE_PERMIT_NAME + ",";
		query += ATTRIBUTE_DESCRIPTION + ",";
		query += ATTRIBUTE_STATUS + ")";
		query += " values (";
		query += SqlHandler.delimitString(HetPermitBean.getCompanyId()) + ",";
		query += SqlHandler.delimitString(HetPermitBean.getControlDevice()) + ",";
		query += SqlHandler.delimitString(HetPermitBean.getFacilityId()) + ",";
		query += newId + ",";
		query += HetPermitBean.getBuildingId() + ",";
		query += SqlHandler.delimitString(HetPermitBean.getPermitName()) + ",";
		query += SqlHandler.delimitString(HetPermitBean.getDescription()) + ",";
		query += SqlHandler.delimitString(HetPermitBean.getStatus());
		query += ")";
		return sqlManager.update(conn, query);
	}

	//update
	public int update(HetPermitBean HetPermitBean) throws BaseException {

		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			return update(HetPermitBean, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}

	public int update(HetPermitBean HetPermitBean, Connection conn) throws BaseException {

		String query = "update " + TABLE + " set ";
		query += ATTRIBUTE_FACILITY_ID + "=" + SqlHandler.delimitString(HetPermitBean.getFacilityId()) + ",";
		query += ATTRIBUTE_BUILDING_ID + "=" + HetPermitBean.getBuildingId() + ",";
		query += ATTRIBUTE_PERMIT_NAME + "=" + SqlHandler.delimitString(HetPermitBean.getPermitName()) + ",";
		query += ATTRIBUTE_DESCRIPTION + "=" + SqlHandler.delimitString(HetPermitBean.getDescription()) + ",";
		query += ATTRIBUTE_CONTROL_DEVICE + "=" +  SqlHandler.delimitString(HetPermitBean.getControlDevice()) + ",";
		query += ATTRIBUTE_STATUS + "=" + SqlHandler.delimitString(HetPermitBean.getStatus()) + " ";
		query += getWhereClause(getKeyCriteria(HetPermitBean));

		return new SqlManager().update(conn, query);
	}

	//select
	public Collection<HetPermitBean> select(SearchCriteria criteria, SortCriteria sortCriteria) throws BaseException {

		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			return select(criteria, sortCriteria, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}
	

	private boolean inWhere = false;

	
	public String getSelectWhereClause(SearchCriteria criteria ) {
		inWhere= true;
		String clause = super.getWhereClause(criteria);
		if (!StringHandler.isBlankString(clause)) {
			clause += " and ";
		}
		clause += "B.BUILDING_ID(+) = P.BUILDING_ID ";
		clause += "AND A.AREA_ID(+) = B.AREA_ID ";
		inWhere = false;
		return clause;
	}

	public Collection<HetPermitBean> select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn) throws BaseException {

		Collection<HetPermitBean> HetPermitBeanColl = new Vector<HetPermitBean>();

		String query = "select P.*, B.building_name, B.AREA_ID, A.AREA_NAME FROM " + SELECT_TABLE + " " + getSelectWhereClause(criteria) + (sortCriteria == null ? " order by B.AREA_ID, P.BUILDING_ID, P.permit_id" : getOrderByClause(sortCriteria));

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			HetPermitBean HetPermitBean = new HetPermitBean();
			load(dataSetRow, HetPermitBean);
			HetPermitBeanColl.add(HetPermitBean);
		}

		return HetPermitBeanColl;
	}
}