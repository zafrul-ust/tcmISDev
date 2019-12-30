package com.tcmis.client.report.factory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.report.beans.FacPlanningReportOvBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DbSelectException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SortCriteria;


/******************************************************************************
 * CLASSNAME: HubIgBuyerOvBeanFactory <br>
 * @version: 1.0, May 7, 2008 <br>
 *****************************************************************************/


public class FacilityPlanningReportOvBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_FACILITY_NAME = "FACILITY_NAME";
	public String ATTRIBUTE_PLANNING_REPORT_AREA_LIST = "PLANNING_REPORT_AREA_LIST";

	//table name
	public String TABLE = "FACILITY_PLANNING_REPORT_OV";


	//constructor
	public FacilityPlanningReportOvBeanFactory(DbManager dbManager) {
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
		else if(attributeName.equals("facilityName")) {
			return ATTRIBUTE_FACILITY_NAME;
		}
		else if(attributeName.equals("planningReportAreaList")) {
			return ATTRIBUTE_PLANNING_REPORT_AREA_LIST;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, FacPlanningReportOvBean.class);
	}


	//you need to verify the primary key(s) before uncommenting this
	/*
	//delete
	public int delete(HubIgBuyerOvBean hubIgBuyerOvBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("hub", "SearchCriterion.EQUALS",
			"" + hubIgBuyerOvBean.getHub());

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


	public int delete(HubIgBuyerOvBean hubIgBuyerOvBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("hub", "SearchCriterion.EQUALS",
			"" + hubIgBuyerOvBean.getHub());

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

		Collection facPlanningReportOvBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
		getWhereClause(criteria)+ getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			FacPlanningReportOvBean facPlanningReportOvBean = new FacPlanningReportOvBean();
			load(dataSetRow, facPlanningReportOvBean);
			facPlanningReportOvBeanColl.add(facPlanningReportOvBean);
		}

		return facPlanningReportOvBeanColl;
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
			map.put("CUSTOMER.AREA_OBJ",
					Class.forName(
					"com.tcmis.client.report.beans.AreaObjBean"));
			map.put("CUSTOMER.PLANNING_REPORT_OBJ",
					Class.forName(
					"com.tcmis.client.report.beans.PlanningReportObjBean"));
			map.put("CUSTOMER.FACILITY_PLANNING_REPORT_OBJ",
					Class.forName(
					"com.tcmis.client.report.beans.FacPlanningReportOvBean"));

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
		Collection facPlanningReportOvBeanColl = new Vector();

		String query = "select value(p) from " + TABLE + " p " +
		getWhereClause(criteria) + getOrderByClause(sortCriteria);
		if (log.isDebugEnabled()) {
			log.debug("QUERY:" + query);
		}
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				FacPlanningReportOvBean b = (FacPlanningReportOvBean) rs.getObject(1);
				facPlanningReportOvBeanColl.add(b);
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
		return facPlanningReportOvBeanColl;
	}

}
