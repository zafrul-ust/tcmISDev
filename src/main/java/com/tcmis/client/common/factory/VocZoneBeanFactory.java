package com.tcmis.client.common.factory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.common.beans.VocZoneBean;
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
 * CLASSNAME: VocZoneBeanFactory <br>
 * @version: 1.0, Nov 29, 2011 <br>
 *****************************************************************************/

public class VocZoneBeanFactory extends BaseBeanFactory {

	//column names
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_VOC_ZONE_ID = "VOC_ZONE_ID";
	public String ATTRIBUTE_VOC_ZONE_DESCRIPTION = "VOC_ZONE_DESCRIPTION";
	public String ATTRIBUTE_STATUS = "STATUS";
	Log log = LogFactory.getLog(getClass());

	//table name
	public String TABLE = "VOC_ZONE";

	//constructor
	public VocZoneBeanFactory(DbManager dbManager) {
		super(dbManager);
	}

	//delete
	public int delete(VocZoneBean vocZoneBean) throws BaseException {
		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			return this.delete(vocZoneBean, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}

	public int delete(VocZoneBean vocZoneBean, Connection conn) throws BaseException {

		return delete(getKeyCriteria(vocZoneBean), conn);
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
		else if (attributeName.equals("vocZoneId")) {
			return ATTRIBUTE_VOC_ZONE_ID;
		}
		else if (attributeName.equals("vocZoneDescription")) {
			return ATTRIBUTE_VOC_ZONE_DESCRIPTION;
		}
		else if (attributeName.equals("status")) {
			return ATTRIBUTE_STATUS;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}

	public SearchCriteria getKeyCriteria(VocZoneBean vocZoneBean) {
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("companyId", SearchCriterion.EQUALS, "" + vocZoneBean.getCompanyId());
		criteria.addCriterion("facilityId", SearchCriterion.EQUALS, "" + vocZoneBean.getFacilityId());
		criteria.addCriterion("vocZoneId", SearchCriterion.EQUALS, "" + vocZoneBean.getVocZoneId());		
		return criteria;
	}

	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, VocZoneBean.class);
	}

	public int insert(VocZoneBean vocZoneBean) throws BaseException {

		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			return insert(vocZoneBean, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}

	public int insert(VocZoneBean vocZoneBean, Connection conn) throws BaseException {
		int nextId = getDbManager().getOracleSequence("misc_seq");
		SqlManager sqlManager = new SqlManager();

		String query = "insert into " + TABLE + " (";
		query += ATTRIBUTE_COMPANY_ID + ",";
		query += ATTRIBUTE_FACILITY_ID + ",";
		query += ATTRIBUTE_STATUS + ",";
		query += ATTRIBUTE_VOC_ZONE_ID + ",";
		query += ATTRIBUTE_VOC_ZONE_DESCRIPTION + ")";
		query += " values (";
		query += SqlHandler.delimitString(vocZoneBean.getCompanyId()) + ",";
		query += SqlHandler.delimitString(vocZoneBean.getFacilityId()) + ",";
		query += SqlHandler.delimitString(vocZoneBean.getStatus()) + ",";
		query += nextId + ",";
		query += SqlHandler.delimitString(vocZoneBean.getVocZoneDescription());
		query += ")";
		return sqlManager.update(conn, query);
	}

	//select
	public Collection<VocZoneBean> select(SearchCriteria criteria, SortCriteria sortCriteria) throws BaseException {

		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			return select(criteria, sortCriteria, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}

	public Collection<VocZoneBean> select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn) throws BaseException {

		Collection<VocZoneBean> vocZoneBeanColl = new Vector<VocZoneBean>();

		String query = "select * from " + TABLE + " " + getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			VocZoneBean vocZoneBean = new VocZoneBean();
			load(dataSetRow, vocZoneBean);
			vocZoneBeanColl.add(vocZoneBean);
		}

		return vocZoneBeanColl;
	}

	//update
	public int update(VocZoneBean vocZoneBean) throws BaseException {

		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			return update(vocZoneBean, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}

	public int update(VocZoneBean vocZoneBean, Connection conn) throws BaseException {

		String query = "update " + TABLE + " set ";
		query += ATTRIBUTE_VOC_ZONE_DESCRIPTION + "=" + SqlHandler.delimitString(vocZoneBean.getVocZoneDescription()) + ", ";
		query += ATTRIBUTE_STATUS + "=" + SqlHandler.delimitString(vocZoneBean.getStatus()) + " ";
		query += getWhereClause(getKeyCriteria(vocZoneBean));

		return new SqlManager().update(conn, query);
	}
}