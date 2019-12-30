package com.tcmis.client.common.factory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.common.beans.FlammabilityControlZoneBean;
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
 * CLASSNAME: FlammabilityControlZoneBeanFactory <br>
 * @version: 1.0, Nov 29, 2011 <br>
 *****************************************************************************/

public class FlammabilityControlZoneBeanFactory extends BaseBeanFactory {

	//column names
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_FLAMMABILITY_CONTROL_ZONE_ID = "FLAMMABILITY_CONTROL_ZONE_ID";
	public String ATTRIBUTE_FLAMMABILITY_CONTROL_ZONE_DESC = "FLAMMABILITY_CONTROL_ZONE_DESC";
	public String ATTRIBUTE_STATUS = "STATUS";
	public String ATTRIBUTE_FLASH_POINT_LIMIT_OPERATOR = "FLASH_POINT_LIMIT_OPERATOR";
	public String ATTRIBUTE_FLASH_POINT_LIMIT = "FLASH_POINT_LIMIT";
	public String ATTRIBUTE_FLASH_POINT_LIMIT_UNIT = "FLASH_POINT_LIMIT_UNIT";
	public String ATTRIBUTE_AMOUNT_LIMIT_OPERATOR = "AMOUNT_LIMIT_OPERATOR";
	public String ATTRIBUTE_AMOUNT_LIMIT = "AMOUNT_LIMIT";
	public String ATTRIBUTE_AMOUNT_LIMIT_UNIT = "AMOUNT_LIMIT_UNIT";

	Log log = LogFactory.getLog(getClass());

	//table name
	public String TABLE = "FLAMMABILITY_CONTROL_ZONE";

	//constructor
	public FlammabilityControlZoneBeanFactory(DbManager dbManager) {
		super(dbManager);
	}

	//delete
	public int delete(FlammabilityControlZoneBean flammabilityControlZoneBean) throws BaseException {
		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			return this.delete(flammabilityControlZoneBean, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}

	public int delete(FlammabilityControlZoneBean flammabilityControlZoneBean, Connection conn) throws BaseException {

		return delete(getKeyCriteria(flammabilityControlZoneBean), conn);
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

	//get column names
	@Override
	public String getColumnName(String attributeName) {
		if (attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		} 
		else if (attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}		
		else if (attributeName.equals("flammabilityControlZoneId")) {
			return ATTRIBUTE_FLAMMABILITY_CONTROL_ZONE_ID;
		}
		else if (attributeName.equals("flammabilityControlZoneDesc")) {
			return ATTRIBUTE_FLAMMABILITY_CONTROL_ZONE_DESC;
		}
		else if (attributeName.equals("status")) {
			return ATTRIBUTE_STATUS;
		}
		else if (attributeName.equals("amountLimitOperator")) {
			return ATTRIBUTE_AMOUNT_LIMIT_OPERATOR;
		}		
		else if (attributeName.equals("amountLimit")) {
			return ATTRIBUTE_AMOUNT_LIMIT;
		}
		else if (attributeName.equals("amountLimitUnit")) {
			return ATTRIBUTE_AMOUNT_LIMIT_UNIT;
		}
		else if (attributeName.equals("flashPointLimitOperator")) {
			return ATTRIBUTE_FLASH_POINT_LIMIT_OPERATOR;
		}
		else if (attributeName.equals("flashPointLimit")) {
			return ATTRIBUTE_FLASH_POINT_LIMIT;
		}
		else if (attributeName.equals("flashPointLimitUnit")) {
			return ATTRIBUTE_FLASH_POINT_LIMIT_UNIT;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}

	public SearchCriteria getKeyCriteria(FlammabilityControlZoneBean flammabilityControlZoneBean) {
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("companyId", SearchCriterion.EQUALS, "" + flammabilityControlZoneBean.getCompanyId());
		criteria.addCriterion("facilityId", SearchCriterion.EQUALS, "" + flammabilityControlZoneBean.getFacilityId());
		criteria.addCriterion("flammabilityControlZoneId", SearchCriterion.EQUALS, "" + flammabilityControlZoneBean.getFlammabilityControlZoneId());		
		return criteria;
	}

	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, FlammabilityControlZoneBean.class);
	}

	public int insert(FlammabilityControlZoneBean flammabilityControlZoneBean) throws BaseException {

		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			return insert(flammabilityControlZoneBean, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}

	public int insert(FlammabilityControlZoneBean flammabilityControlZoneBean, Connection conn) throws BaseException {
		int nextId = getDbManager().getOracleSequence("misc_seq");
		SqlManager sqlManager = new SqlManager();

		String query = "insert into " + TABLE + " (";
		query += ATTRIBUTE_COMPANY_ID + ",";
		query += ATTRIBUTE_FACILITY_ID + ",";
		query += ATTRIBUTE_STATUS + ",";
		query += ATTRIBUTE_FLAMMABILITY_CONTROL_ZONE_ID + ",";
		query += ATTRIBUTE_FLAMMABILITY_CONTROL_ZONE_DESC + ",";
		query += ATTRIBUTE_FLASH_POINT_LIMIT_OPERATOR + ",";
		query += ATTRIBUTE_FLASH_POINT_LIMIT + ",";
		query += ATTRIBUTE_FLASH_POINT_LIMIT_UNIT + ",";
		query += ATTRIBUTE_AMOUNT_LIMIT_OPERATOR + ",";
		query += ATTRIBUTE_AMOUNT_LIMIT + ",";
		query += ATTRIBUTE_AMOUNT_LIMIT_UNIT;
		query += ") values (";
		query += SqlHandler.delimitString(flammabilityControlZoneBean.getCompanyId()) + ",";
		query += SqlHandler.delimitString(flammabilityControlZoneBean.getFacilityId()) + ",";
		query += SqlHandler.delimitString(flammabilityControlZoneBean.getStatus()) + ",";
		query += nextId + ",";
		query += SqlHandler.delimitString(flammabilityControlZoneBean.getFlammabilityControlZoneDesc())+ ",";
		query += SqlHandler.delimitString(flammabilityControlZoneBean.getFlashPointLimitOperator())+ ",";
		query += SqlHandler.delimitString(flammabilityControlZoneBean.getFlashPointLimit())+ ",";
		query += SqlHandler.delimitString(flammabilityControlZoneBean.getFlashPointLimitUnit())+ ",";
		query += SqlHandler.delimitString(flammabilityControlZoneBean.getAmountLimitOperator())+ ",";
		query += SqlHandler.delimitString(flammabilityControlZoneBean.getAmountLimit())+ ",";
		query += SqlHandler.delimitString(flammabilityControlZoneBean.getAmountLimitUnit());
		query += ")";
		return sqlManager.update(conn, query);
	}

	//select
	public Collection<FlammabilityControlZoneBean> select(SearchCriteria criteria, SortCriteria sortCriteria) throws BaseException {

		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			return select(criteria, sortCriteria, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}

	public Collection<FlammabilityControlZoneBean> select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn) throws BaseException {

		Collection<FlammabilityControlZoneBean> flammabilityControlZoneBeanColl = new Vector<FlammabilityControlZoneBean>();

		String query = "select * from " + TABLE + " " + getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			FlammabilityControlZoneBean flammabilityControlZoneBean = new FlammabilityControlZoneBean();
			load(dataSetRow, flammabilityControlZoneBean);
			flammabilityControlZoneBeanColl.add(flammabilityControlZoneBean);
		}

		return flammabilityControlZoneBeanColl;
	}

	//update
	public int update(FlammabilityControlZoneBean flammabilityControlZoneBean) throws BaseException {

		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			return update(flammabilityControlZoneBean, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}

	public int update(FlammabilityControlZoneBean flammabilityControlZoneBean, Connection conn) throws BaseException {

		String query = "update " + TABLE + " set ";
		query += ATTRIBUTE_FLAMMABILITY_CONTROL_ZONE_DESC + "=" + SqlHandler.delimitString(flammabilityControlZoneBean.getFlammabilityControlZoneDesc()) + ", ";
		query += ATTRIBUTE_AMOUNT_LIMIT + "=" + SqlHandler.delimitString(flammabilityControlZoneBean.getAmountLimit()) + ", ";
		query += ATTRIBUTE_AMOUNT_LIMIT_OPERATOR + "=" + SqlHandler.delimitString(flammabilityControlZoneBean.getAmountLimitOperator()) + ", ";
		query += ATTRIBUTE_AMOUNT_LIMIT_UNIT + "=" + SqlHandler.delimitString(flammabilityControlZoneBean.getAmountLimitUnit()) + ", ";
		query += ATTRIBUTE_FLASH_POINT_LIMIT + "=" + SqlHandler.delimitString(flammabilityControlZoneBean.getFlashPointLimit()) + ", ";
		query += ATTRIBUTE_FLASH_POINT_LIMIT_OPERATOR + "=" + SqlHandler.delimitString(flammabilityControlZoneBean.getFlashPointLimitOperator()) + ", ";
		query += ATTRIBUTE_FLASH_POINT_LIMIT_UNIT + "=" + SqlHandler.delimitString(flammabilityControlZoneBean.getFlashPointLimitUnit()) + ", ";
		query += ATTRIBUTE_STATUS + "=" + SqlHandler.delimitString(flammabilityControlZoneBean.getStatus()) + " ";
		query += getWhereClause(getKeyCriteria(flammabilityControlZoneBean));

		return new SqlManager().update(conn, query);
	}
}