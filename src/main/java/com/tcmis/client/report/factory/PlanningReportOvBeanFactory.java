package com.tcmis.client.report.factory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import com.tcmis.client.report.beans.PlanningReportObjBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DbSelectException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SortCriteria;


public class PlanningReportOvBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_REPORT_ID = "REPORT_ID";
	public String ATTRIBUTE_REPORT_NAME = "REPORT_NAME";
	public String ATTRIBUTE_COUNTY = "COUNTY";
	public String ATTRIBUTE_SOLID_THRESHOLD = "SOLID_THRESHOLD";
	public String ATTRIBUTE_SOLID_THRESHOLD_UNIT = "SOLID_THRESHOLD_UNIT";
	public String ATTRIBUTE_LIQUID_THRESHOLD = "LIQUID_THRESHOLD";
	public String ATTRIBUTE_LIQUID_THRESHOLD_UNIT = "LIQUID_THRESHOLD_UNIT";
	public String ATTRIBUTE_GAS_THRESHOLD = "GAS_THRESHOLD";
	public String ATTRIBUTE_GAS_THRESHOLD_UNIT = "GAS_THRESHOLD_UNIT";
	public String ATTRIBUTE_PURE_THRESHOLD = "PURE_THRESHOLD";
	public String ATTRIBUTE_AREA_LIST = "AREA_LIST";

	//table name
	public String TABLE = "PLANNING_REPORT_OV";


	//constructor
	public PlanningReportOvBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	@Override
	public String getColumnName(String attributeName) {
		if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("reportId")) {
			return ATTRIBUTE_REPORT_ID;
		}
		else if(attributeName.equals("reportName")) {
			return ATTRIBUTE_REPORT_NAME;
		}
		else if(attributeName.equals("county")) {
			return ATTRIBUTE_COUNTY;
		}
		else if(attributeName.equals("solidThreshold")) {
			return ATTRIBUTE_SOLID_THRESHOLD;
		}
		else if(attributeName.equals("solidThresholdUnit")) {
			return ATTRIBUTE_SOLID_THRESHOLD_UNIT;
		}
		else if(attributeName.equals("liquidThreshold")) {
			return ATTRIBUTE_LIQUID_THRESHOLD;
		}
		else if(attributeName.equals("liquidThresholdUnit")) {
			return ATTRIBUTE_LIQUID_THRESHOLD_UNIT;
		}
		else if(attributeName.equals("gasThreshold")) {
			return ATTRIBUTE_GAS_THRESHOLD;
		}
		else if(attributeName.equals("gasThresholdUnit")) {
			return ATTRIBUTE_GAS_THRESHOLD_UNIT;
		}
		else if(attributeName.equals("pureThreshold")) {
			return ATTRIBUTE_PURE_THRESHOLD;
		}
		else if(attributeName.equals("areaList")) {
			return ATTRIBUTE_AREA_LIST;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, PlanningReportObjBean.class);
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
					"com.tcmis.client.report.beans.PlanningReportOvBean"));
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

	public Collection selectObject(SearchCriteria criteria, SortCriteria sortCriteria,Connection conn) throws
	BaseException {
		Collection planningReportOvBeanColl = new Vector();

		String query = "select value(p) from " + TABLE + " p " +
		 getWhereClause(criteria) + getOrderByClause(sortCriteria);
		
		if(log.isDebugEnabled()) {
			log.debug("QUERY:" + query);
		}
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				PlanningReportObjBean b = (PlanningReportObjBean) rs.getObject(1);
				planningReportOvBeanColl.add(b);
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
		return planningReportOvBeanColl;
	}
}