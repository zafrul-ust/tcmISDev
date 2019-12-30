package com.tcmis.internal.catalog.factory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
import com.tcmis.internal.catalog.beans.ReportingEntityBean;

/******************************************************************************
 * CLASSNAME: ReportingEntityBeanFactory <br>
 * @version: 1.0, Jan 4, 2011 <br>
 *****************************************************************************/

public class ReportingEntityBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_REPORTING_ENTITY_ID = "REPORTING_ENTITY_ID";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_DESCRIPTION = "DESCRIPTION";

	//table name
	public String TABLE = "REPORTING_ENTITY";

	//constructor
	public ReportingEntityBeanFactory(DbManager dbManager) {
		super(dbManager);
	}

	private SearchCriteria getKeyCriteria(ReportingEntityBean reportingEntityBean) {
		SearchCriteria criteria = new SearchCriteria("companyId", SearchCriterion.EQUALS, reportingEntityBean.getCompanyId());
		criteria.addCriterion("facilityId", SearchCriterion.EQUALS, reportingEntityBean.getFacilityId());
		criteria.addCriterion("reportingEntityId", SearchCriterion.EQUALS, reportingEntityBean.getReportingEntityId());
		return criteria;
	}

	//get column names
	@Override
	public String getColumnName(String attributeName) {
		if (attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if (attributeName.equals("reportingEntityId")) {
			return ATTRIBUTE_REPORTING_ENTITY_ID;
		}
		else if (attributeName.equals("description")) {
			return ATTRIBUTE_DESCRIPTION;
		}
		else if (attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}

	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, ReportingEntityBean.class);
	}

	//delete
	public int delete(ReportingEntityBean reportingEntityBean) throws BaseException {
		return delete(reportingEntityBean, null);
	}

	public int delete(ReportingEntityBean reportingEntityBean, Connection conn) throws BaseException {
		SearchCriteria criteria = getKeyCriteria(reportingEntityBean);

		return conn == null ? delete(criteria) : delete(criteria, conn);
	}

	public int delete(SearchCriteria criteria) throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = delete(criteria, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
		return result;
	}

	public int delete(SearchCriteria criteria, Connection conn) throws BaseException {

		String sqlQuery = " delete from " + TABLE + " " + getWhereClause(criteria);

		return new SqlManager().update(conn, sqlQuery);
	}

	//insert
	public int insert(ReportingEntityBean reportingEntityBean) throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(reportingEntityBean, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
		return result;
	}

	public int insert(ReportingEntityBean reportingEntityBean, Connection conn) throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query = "insert into " + TABLE + " (" + ATTRIBUTE_COMPANY_ID + "," + ATTRIBUTE_REPORTING_ENTITY_ID + "," + ATTRIBUTE_FACILITY_ID + "," + ATTRIBUTE_DESCRIPTION + ")" + " values (";
		query += SqlHandler.delimitString(reportingEntityBean.getCompanyId()) + ",";
		query += SqlHandler.delimitString(reportingEntityBean.getReportingEntityId()) + ",";
		query += SqlHandler.delimitString(reportingEntityBean.getFacilityId()) + ",";
		query += SqlHandler.delimitString(reportingEntityBean.getDescription());
		query += ")";

		return sqlManager.update(conn, query);
	}

	//update
	public int update(ReportingEntityBean reportingEntityBean) throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(reportingEntityBean, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
		return result;
	}

	public int update(ReportingEntityBean reportingEntityBean, Connection conn) throws BaseException {

		String query = "update " + TABLE + " set " + ATTRIBUTE_DESCRIPTION + "=" + SqlHandler.delimitString(reportingEntityBean.getDescription()) + " ";
		query += getWhereClause(getKeyCriteria(reportingEntityBean));

		return new SqlManager().update(conn, query);
	}

	//select
	public Collection<ReportingEntityBean> select(SearchCriteria criteria, SortCriteria sortCriteria) throws BaseException {

		Connection connection = null;
		Collection<ReportingEntityBean> c = null;
		try {
			connection = getDbManager().getConnection();
			c = select(criteria, sortCriteria, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}

	public Collection<ReportingEntityBean> select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn) throws BaseException {

		Collection<ReportingEntityBean> reportingEntityBeanColl = new Vector<ReportingEntityBean>();

		String query = "select * from " + TABLE + " " + getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			ReportingEntityBean reportingEntityBean = new ReportingEntityBean();
			load(dataSetRow, reportingEntityBean);
			reportingEntityBeanColl.add(reportingEntityBean);
		}

		return reportingEntityBeanColl;
	}
}