package com.tcmis.client.report.factory;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import java.lang.reflect.Method;

import java.sql.Connection;

import java.text.ParseException;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DataBeanCreationException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.util.*;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.client.report.beans.WasteReportTemplateBean;


/******************************************************************************
 * CLASSNAME: WasteReportTemplateBeanFactory <br>
 * @version: 1.0, Mar 28, 2008 <br>
 *****************************************************************************/


public class WasteReportTemplateBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_REPORT_TEMPLATE_ID = "REPORT_TEMPLATE_ID";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_PERSONNEL_ID = "PERSONNEL_ID";
	public String ATTRIBUTE_TEMPLATE_NAME = "TEMPLATE_NAME";
	public String ATTRIBUTE_REPORT_CRITERIA = "REPORT_CRITERIA";
	public String ATTRIBUTE_PROFILE_ID = "PROFILE_ID";
	public String ATTRIBUTE_PROFILE_DESC = "PROFILE_DESC";
	public String ATTRIBUTE_VENDOR = "VENDOR";
	public String ATTRIBUTE_ACCUMULATION_POINT = "ACCUMULATION_POINT";
	public String ATTRIBUTE_METHOD = "METHOD";
	public String ATTRIBUTE_BEGIN_DATE = "BEGIN_DATE";
	public String ATTRIBUTE_END_DATE = "END_DATE";
	public String ATTRIBUTE_APPLICATION = "APPLICATION";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_MANAGEMENT_OPTION = "MANAGEMENT_OPTION";
	public String ATTRIBUTE_MANAGEMENT_OPTION_DESC = "MANAGEMENT_OPTION_DESC";
	public String ATTRIBUTE_EXCLUDE_WASTE = "EXCLUDE_WASTE";
	public String ATTRIBUTE_REPORT_GENERATION_TYPE = "REPORT_GENERATION_TYPE";
	public String ATTRIBUTE_REPORT_NAME = "REPORT_NAME";
    public String ATTRIBUTE_PROFILE_FLAG = "PROFILE_FLAG";

    //table name
	public String TABLE = "WASTE_REPORT_TEMPLATE";
  public String SEQUENCE_NAME = "REPORT_TEMPLATE_SEQ";

	//constructor
	public WasteReportTemplateBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("reportTemplateId")) {
			return ATTRIBUTE_REPORT_TEMPLATE_ID;
		}
		else if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("personnelId")) {
			return ATTRIBUTE_PERSONNEL_ID;
		}
		else if(attributeName.equals("templateName")) {
			return ATTRIBUTE_TEMPLATE_NAME;
		}
		else if(attributeName.equals("reportCriteria")) {
			return ATTRIBUTE_REPORT_CRITERIA;
		}
		else if(attributeName.equals("profileId")) {
			return ATTRIBUTE_PROFILE_ID;
		}
		else if(attributeName.equals("profileDesc")) {
			return ATTRIBUTE_PROFILE_DESC;
		}
		else if(attributeName.equals("vendor")) {
			return ATTRIBUTE_VENDOR;
		}
		else if(attributeName.equals("accumulationPoint")) {
			return ATTRIBUTE_ACCUMULATION_POINT;
		}
		else if(attributeName.equals("method")) {
			return ATTRIBUTE_METHOD;
		}
		else if(attributeName.equals("beginDate")) {
			return ATTRIBUTE_BEGIN_DATE;
		}
		else if(attributeName.equals("endDate")) {
			return ATTRIBUTE_END_DATE;
		}
		else if(attributeName.equals("application")) {
			return ATTRIBUTE_APPLICATION;
		}
		else if(attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if(attributeName.equals("managementOption")) {
			return ATTRIBUTE_MANAGEMENT_OPTION;
		}
		else if(attributeName.equals("managementOptionDesc")) {
			return ATTRIBUTE_MANAGEMENT_OPTION_DESC;
		}
		else if(attributeName.equals("excludeWaste")) {
			return ATTRIBUTE_EXCLUDE_WASTE;
		}
		else if(attributeName.equals("reportGenerationType")) {
			return ATTRIBUTE_REPORT_GENERATION_TYPE;
		}
		else if(attributeName.equals("reportName")) {
			return ATTRIBUTE_REPORT_NAME;
		}
        else if(attributeName.equals("profileFlag")) {
			return ATTRIBUTE_PROFILE_FLAG;
		}
        else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, WasteReportTemplateBean.class);
	}


	//delete
	public int delete(WasteReportTemplateBean wasteReportTemplateBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("reportTemplateId", "SearchCriterion.EQUALS",
			"" + wasteReportTemplateBean.getReportTemplateId());

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


	public int delete(WasteReportTemplateBean wasteReportTemplateBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("reportTemplateId", "SearchCriterion.EQUALS",
			"" + wasteReportTemplateBean.getReportTemplateId());

		return delete(criteria, conn);
	}


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



	//insert
	public int insert(WasteReportTemplateBean wasteReportTemplateBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(wasteReportTemplateBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(WasteReportTemplateBean wasteReportTemplateBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_REPORT_TEMPLATE_ID + "," +
			//ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_PERSONNEL_ID + "," +
			ATTRIBUTE_TEMPLATE_NAME + "," +
			ATTRIBUTE_REPORT_CRITERIA + "," +
			ATTRIBUTE_PROFILE_ID + "," +
			ATTRIBUTE_PROFILE_DESC + "," +
            ATTRIBUTE_PROFILE_FLAG + "," +
            ATTRIBUTE_VENDOR + "," +
			ATTRIBUTE_ACCUMULATION_POINT + "," +
			ATTRIBUTE_METHOD + "," +
			ATTRIBUTE_BEGIN_DATE + "," +
			ATTRIBUTE_END_DATE + "," +
			ATTRIBUTE_APPLICATION + "," +
			ATTRIBUTE_FACILITY_ID + "," +
			ATTRIBUTE_MANAGEMENT_OPTION + "," +
			ATTRIBUTE_MANAGEMENT_OPTION_DESC + "," +
			ATTRIBUTE_EXCLUDE_WASTE + "," +
			ATTRIBUTE_REPORT_GENERATION_TYPE + "," +
			ATTRIBUTE_REPORT_NAME + ")" +
			" values (" +
			wasteReportTemplateBean.getReportTemplateId() + "," +
			//SqlHandler.delimitString(wasteReportTemplateBean.getCompanyId()) + "," +
			wasteReportTemplateBean.getPersonnelId() + "," +
			SqlHandler.delimitString(wasteReportTemplateBean.getTemplateName()) + "," +
			SqlHandler.delimitString(wasteReportTemplateBean.getReportCriteria()) + "," +
			SqlHandler.delimitString(wasteReportTemplateBean.getProfileId()) + "," +
			SqlHandler.delimitString(wasteReportTemplateBean.getProfileDesc()) + "," +
            SqlHandler.delimitString(wasteReportTemplateBean.getProfileFlag()) + "," +
            SqlHandler.delimitString(wasteReportTemplateBean.getVendor()) + "," +
			SqlHandler.delimitString(wasteReportTemplateBean.getAccumulationPoint()) + "," +
			SqlHandler.delimitString(wasteReportTemplateBean.getMethod()) + "," +
			DateHandler.getOracleToDateFunction(wasteReportTemplateBean.getBeginDate()) + "," +
			DateHandler.getOracleToDateFunction(wasteReportTemplateBean.getEndDate()) + "," +
			SqlHandler.delimitString(wasteReportTemplateBean.getApplication()) + "," +
			SqlHandler.delimitString(wasteReportTemplateBean.getFacilityId()) + "," +
			SqlHandler.delimitString(wasteReportTemplateBean.getManagementOption()) + "," +
			SqlHandler.delimitString(wasteReportTemplateBean.getManagementOptionDesc()) + "," +
			SqlHandler.delimitString(wasteReportTemplateBean.getExcludeWaste()) + "," +
			SqlHandler.delimitString(wasteReportTemplateBean.getReportGenerationType()) + "," +
			SqlHandler.delimitString(wasteReportTemplateBean.getReportName()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(WasteReportTemplateBean wasteReportTemplateBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(wasteReportTemplateBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(WasteReportTemplateBean wasteReportTemplateBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_REPORT_TEMPLATE_ID + "=" + 
				StringHandler.nullIfZero(wasteReportTemplateBean.getReportTemplateId()) + "," +
			/*ATTRIBUTE_COMPANY_ID + "=" +
				SqlHandler.delimitString(wasteReportTemplateBean.getCompanyId()) + "," +
			*/
			ATTRIBUTE_PERSONNEL_ID + "=" +
				StringHandler.nullIfZero(wasteReportTemplateBean.getPersonnelId()) + "," +
			ATTRIBUTE_TEMPLATE_NAME + "=" + 
				SqlHandler.delimitString(wasteReportTemplateBean.getTemplateName()) + "," +
			ATTRIBUTE_REPORT_CRITERIA + "=" + 
				SqlHandler.delimitString(wasteReportTemplateBean.getReportCriteria()) + "," +
			ATTRIBUTE_PROFILE_ID + "=" + 
				SqlHandler.delimitString(wasteReportTemplateBean.getProfileId()) + "," +
			ATTRIBUTE_PROFILE_DESC + "=" + 
				SqlHandler.delimitString(wasteReportTemplateBean.getProfileDesc()) + "," +
            ATTRIBUTE_PROFILE_FLAG + "=" +
				SqlHandler.delimitString(wasteReportTemplateBean.getProfileFlag()) + "," +
            ATTRIBUTE_VENDOR + "=" +
				SqlHandler.delimitString(wasteReportTemplateBean.getVendor()) + "," +
			ATTRIBUTE_ACCUMULATION_POINT + "=" + 
				SqlHandler.delimitString(wasteReportTemplateBean.getAccumulationPoint()) + "," +
			ATTRIBUTE_METHOD + "=" + 
				SqlHandler.delimitString(wasteReportTemplateBean.getMethod()) + "," +
			ATTRIBUTE_BEGIN_DATE + "=" + 
				DateHandler.getOracleToDateFunction(wasteReportTemplateBean.getBeginDate()) + "," +
			ATTRIBUTE_END_DATE + "=" + 
				DateHandler.getOracleToDateFunction(wasteReportTemplateBean.getEndDate()) + "," +
			ATTRIBUTE_APPLICATION + "=" + 
				SqlHandler.delimitString(wasteReportTemplateBean.getApplication()) + "," +
			ATTRIBUTE_FACILITY_ID + "=" + 
				SqlHandler.delimitString(wasteReportTemplateBean.getFacilityId()) + "," +
			ATTRIBUTE_MANAGEMENT_OPTION + "=" + 
				SqlHandler.delimitString(wasteReportTemplateBean.getManagementOption()) + "," +
			ATTRIBUTE_MANAGEMENT_OPTION_DESC + "=" + 
				SqlHandler.delimitString(wasteReportTemplateBean.getManagementOptionDesc()) + "," +
			ATTRIBUTE_EXCLUDE_WASTE + "=" + 
				SqlHandler.delimitString(wasteReportTemplateBean.getExcludeWaste()) + "," +
			ATTRIBUTE_REPORT_GENERATION_TYPE + "=" + 
				SqlHandler.delimitString(wasteReportTemplateBean.getReportGenerationType()) + "," +
			ATTRIBUTE_REPORT_NAME + "=" + 
				SqlHandler.delimitString(wasteReportTemplateBean.getReportName()) + " " +
			"where " + ATTRIBUTE_REPORT_TEMPLATE_ID + "=" +
				wasteReportTemplateBean.getReportTemplateId();

		return new SqlManager().update(conn, query);
	}


	//select
	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria)
		throws BaseException {

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
	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn)
		throws BaseException {

		Collection wasteReportTemplateBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);
        if(log.isDebugEnabled()) {
            log.debug("QUERY:" + query);
        }
		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();
        ReportTemplateFieldBeanFactory factory = new ReportTemplateFieldBeanFactory(getDbManager());
		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			WasteReportTemplateBean wasteReportTemplateBean = new WasteReportTemplateBean();
			load(dataSetRow, wasteReportTemplateBean);
            SearchCriteria childCriteria = new SearchCriteria();
            childCriteria.addCriterion("reportTemplateId", SearchCriterion.EQUALS,wasteReportTemplateBean.getReportTemplateId().toString());
			   SortCriteria childSortCriteria = new SortCriteria();
      		childSortCriteria.addCriterion("templateFieldId");
				wasteReportTemplateBean.setReportFieldCollection(factory.select(childCriteria, childSortCriteria));
            wasteReportTemplateBeanColl.add(wasteReportTemplateBean);
		}

		return wasteReportTemplateBeanColl;
	}

	public Collection selectMyTemplate(SearchCriteria criteria, SortCriteria sortCriteria)
		throws BaseException {

		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = selectMyTemplate(criteria, sortCriteria, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}
	public Collection selectMyTemplate(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn)
		throws BaseException {

		Collection wasteReportTemplateBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);
        if(log.isDebugEnabled()) {
            log.debug("QUERY:" + query);
        }
		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();
		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			WasteReportTemplateBean wasteReportTemplateBean = new WasteReportTemplateBean();
			load(dataSetRow, wasteReportTemplateBean);
         wasteReportTemplateBeanColl.add(wasteReportTemplateBean);
		}

		return wasteReportTemplateBeanColl;
	}

  public boolean check(SearchCriteria criteria) throws BaseException {

    Connection connection = null;
    boolean b = false;
    try {
      connection = this.getDbManager().getConnection();
      b = check(criteria, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return b;
  }

  public boolean check(SearchCriteria criteria, Connection conn) throws
      BaseException {

    boolean b = false;
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext() && !b) {
      b = true;
    }

    return b;
  }

  public BigDecimal getNextSequenceNumber() throws BaseException {
    int i = this.getDbManager().getOracleSequence(this.SEQUENCE_NAME);
    if(log.isDebugEnabled()){
        log.debug("seq:" + i);
    }
    return new BigDecimal(String.valueOf(i));
  }
}