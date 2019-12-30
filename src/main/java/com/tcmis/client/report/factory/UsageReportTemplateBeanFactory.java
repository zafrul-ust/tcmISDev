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
import com.tcmis.client.report.beans.UsageReportTemplateBean;


/******************************************************************************
 * CLASSNAME: UsageReportTemplateBeanFactory <br>
 * @version: 1.0, Mar 28, 2008 <br>
 *****************************************************************************/


public class UsageReportTemplateBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_REPORT_TEMPLATE_ID = "REPORT_TEMPLATE_ID";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_PERSONNEL_ID = "PERSONNEL_ID";
	public String ATTRIBUTE_TEMPLATE_NAME = "TEMPLATE_NAME";
	public String ATTRIBUTE_REPORTING_ENTITY = "REPORTING_ENTITY";
	public String ATTRIBUTE_CHEMICAL_LIST_NAME = "CHEMICAL_LIST_NAME";
	public String ATTRIBUTE_CAS_NUMBER = "CAS_NUMBER";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_APPLICATION = "APPLICATION";
	public String ATTRIBUTE_PART_NUMBER = "PART_NUMBER";
	public String ATTRIBUTE_BEGIN_DATE = "BEGIN_DATE";
	public String ATTRIBUTE_END_DATE = "END_DATE";
	public String ATTRIBUTE_CATEGORY = "CATEGORY";
	public String ATTRIBUTE_METHOD = "METHOD";
	public String ATTRIBUTE_MANUFACTURER = "MANUFACTURER";
	public String ATTRIBUTE_DOCK_ID = "DOCK_ID";
	public String ATTRIBUTE_DELIVERY_POINT = "DELIVERY_POINT";
	public String ATTRIBUTE_MATERIAL_CATEGORY = "MATERIAL_CATEGORY";
	public String ATTRIBUTE_PART_NUMBER_CRITERIA = "PART_NUMBER_CRITERIA";
	public String ATTRIBUTE_MANUFACTURER_CRITERIA = "MANUFACTURER_CRITERIA";
	public String ATTRIBUTE_REPORT_GENERATION_TYPE = "REPORT_GENERATION_TYPE";
	public String ATTRIBUTE_REPORT_NAME = "REPORT_NAME";
	public String ATTRIBUTE_REPORT_TYPE = "REPORT_TYPE";
	public String ATTRIBUTE_LOCATION = "LOCATION";

	//table name
	public String TABLE = "USAGE_REPORT_TEMPLATE";
  public String SEQUENCE_NAME = "REPORT_TEMPLATE_SEQ";

	//constructor
	public UsageReportTemplateBeanFactory(DbManager dbManager) {
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
		else if(attributeName.equals("reportingEntity")) {
			return ATTRIBUTE_REPORTING_ENTITY;
		}
		else if(attributeName.equals("chemicalListName")) {
			return ATTRIBUTE_CHEMICAL_LIST_NAME;
		}
		else if(attributeName.equals("casNumber")) {
			return ATTRIBUTE_CAS_NUMBER;
		}
		else if(attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if(attributeName.equals("application")) {
			return ATTRIBUTE_APPLICATION;
		}
		else if(attributeName.equals("partNumber")) {
			return ATTRIBUTE_PART_NUMBER;
		}
		else if(attributeName.equals("beginDate")) {
			return ATTRIBUTE_BEGIN_DATE;
		}
		else if(attributeName.equals("endDate")) {
			return ATTRIBUTE_END_DATE;
		}
		else if(attributeName.equals("category")) {
			return ATTRIBUTE_CATEGORY;
		}
		else if(attributeName.equals("method")) {
			return ATTRIBUTE_METHOD;
		}
		else if(attributeName.equals("manufacturer")) {
			return ATTRIBUTE_MANUFACTURER;
		}
		else if(attributeName.equals("dockId")) {
			return ATTRIBUTE_DOCK_ID;
		}
		else if(attributeName.equals("deliveryPoint")) {
			return ATTRIBUTE_DELIVERY_POINT;
		}
		else if(attributeName.equals("materialCategory")) {
			return ATTRIBUTE_MATERIAL_CATEGORY;
		}
		else if(attributeName.equals("partNumberCriteria")) {
			return ATTRIBUTE_PART_NUMBER_CRITERIA;
		}
		else if(attributeName.equals("manufacturerCriteria")) {
			return ATTRIBUTE_MANUFACTURER_CRITERIA;
		}
		else if(attributeName.equals("reportGenerationType")) {
			return ATTRIBUTE_REPORT_GENERATION_TYPE;
		}
		else if(attributeName.equals("reportName")) {
			return ATTRIBUTE_REPORT_NAME;
		}
		else if(attributeName.equals("reportType")) {
			return ATTRIBUTE_REPORT_TYPE;
		}
		else if(attributeName.equals("location")) {
			return ATTRIBUTE_LOCATION;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, UsageReportTemplateBean.class);
	}



	//delete
	public int delete(UsageReportTemplateBean usageReportTemplateBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("reportTemplateId", "SearchCriterion.EQUALS",
			"" + usageReportTemplateBean.getReportTemplateId());

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


	public int delete(UsageReportTemplateBean usageReportTemplateBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("reportTemplateId", "SearchCriterion.EQUALS",
			"" + usageReportTemplateBean.getReportTemplateId());

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
	public int insert(UsageReportTemplateBean usageReportTemplateBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(usageReportTemplateBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(UsageReportTemplateBean usageReportTemplateBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_REPORT_TEMPLATE_ID + "," +
			//ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_PERSONNEL_ID + "," +
			ATTRIBUTE_TEMPLATE_NAME + "," +
			ATTRIBUTE_REPORTING_ENTITY + "," +
			ATTRIBUTE_CHEMICAL_LIST_NAME + "," +
			ATTRIBUTE_CAS_NUMBER + "," +
			ATTRIBUTE_FACILITY_ID + "," +
			ATTRIBUTE_APPLICATION + "," +
			ATTRIBUTE_PART_NUMBER + "," +
			ATTRIBUTE_BEGIN_DATE + "," +
			ATTRIBUTE_END_DATE + "," +
			ATTRIBUTE_CATEGORY + "," +
			ATTRIBUTE_METHOD + "," +
			ATTRIBUTE_MANUFACTURER + "," +
			ATTRIBUTE_DOCK_ID + "," +
			ATTRIBUTE_DELIVERY_POINT + "," +
			ATTRIBUTE_MATERIAL_CATEGORY + "," +
			ATTRIBUTE_PART_NUMBER_CRITERIA + "," +
			ATTRIBUTE_MANUFACTURER_CRITERIA + "," +
			ATTRIBUTE_REPORT_GENERATION_TYPE + "," +
			ATTRIBUTE_REPORT_NAME + "," +
			ATTRIBUTE_REPORT_TYPE + "," +
			ATTRIBUTE_LOCATION + ")" +
			" values (" +
			usageReportTemplateBean.getReportTemplateId() + "," +
			//SqlHandler.delimitString(usageReportTemplateBean.getCompanyId()) + "," +
			usageReportTemplateBean.getPersonnelId() + "," +
			SqlHandler.delimitString(usageReportTemplateBean.getTemplateName()) + "," +
			SqlHandler.delimitString(usageReportTemplateBean.getReportingEntity()) + "," +
			SqlHandler.delimitString(usageReportTemplateBean.getChemicalListName()) + "," +
			SqlHandler.delimitString(usageReportTemplateBean.getCasNumber()) + "," +
			SqlHandler.delimitString(usageReportTemplateBean.getFacilityId()) + "," +
			SqlHandler.delimitString(usageReportTemplateBean.getApplication()) + "," +
			SqlHandler.delimitString(usageReportTemplateBean.getPartNumber()) + "," +
			DateHandler.getOracleToDateFunction(usageReportTemplateBean.getBeginDate()) + "," +
			DateHandler.getOracleToDateFunction(usageReportTemplateBean.getEndDate()) + "," +
			SqlHandler.delimitString(usageReportTemplateBean.getCategory()) + "," +
			SqlHandler.delimitString(usageReportTemplateBean.getMethod()) + "," +
			SqlHandler.delimitString(usageReportTemplateBean.getManufacturer()) + "," +
			SqlHandler.delimitString(usageReportTemplateBean.getDockId()) + "," +
			SqlHandler.delimitString(usageReportTemplateBean.getDeliveryPoint()) + "," +
			SqlHandler.delimitString(usageReportTemplateBean.getMaterialCategory()) + "," +
			SqlHandler.delimitString(usageReportTemplateBean.getPartNumberCriteria()) + "," +
			SqlHandler.delimitString(usageReportTemplateBean.getManufacturerCriteria()) + "," +
			SqlHandler.delimitString(usageReportTemplateBean.getReportGenerationType()) + "," +
			SqlHandler.delimitString(usageReportTemplateBean.getReportName()) + "," +
			SqlHandler.delimitString(usageReportTemplateBean.getReportType()) + "," +
			SqlHandler.delimitString(usageReportTemplateBean.getLocation()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(UsageReportTemplateBean usageReportTemplateBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(usageReportTemplateBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(UsageReportTemplateBean usageReportTemplateBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_REPORT_TEMPLATE_ID + "=" + 
				StringHandler.nullIfZero(usageReportTemplateBean.getReportTemplateId()) + "," +
			/*ATTRIBUTE_COMPANY_ID + "=" +
				SqlHandler.delimitString(usageReportTemplateBean.getCompanyId()) + "," +
			*/ATTRIBUTE_PERSONNEL_ID + "=" +
				StringHandler.nullIfZero(usageReportTemplateBean.getPersonnelId()) + "," +
			ATTRIBUTE_TEMPLATE_NAME + "=" + 
				SqlHandler.delimitString(usageReportTemplateBean.getTemplateName()) + "," +
			ATTRIBUTE_REPORTING_ENTITY + "=" + 
				SqlHandler.delimitString(usageReportTemplateBean.getReportingEntity()) + "," +
			ATTRIBUTE_CHEMICAL_LIST_NAME + "=" + 
				SqlHandler.delimitString(usageReportTemplateBean.getChemicalListName()) + "," +
			ATTRIBUTE_CAS_NUMBER + "=" + 
				SqlHandler.delimitString(usageReportTemplateBean.getCasNumber()) + "," +
			ATTRIBUTE_FACILITY_ID + "=" + 
				SqlHandler.delimitString(usageReportTemplateBean.getFacilityId()) + "," +
			ATTRIBUTE_APPLICATION + "=" + 
				SqlHandler.delimitString(usageReportTemplateBean.getApplication()) + "," +
			ATTRIBUTE_PART_NUMBER + "=" + 
				SqlHandler.delimitString(usageReportTemplateBean.getPartNumber()) + "," +
			ATTRIBUTE_BEGIN_DATE + "=" + 
				DateHandler.getOracleToDateFunction(usageReportTemplateBean.getBeginDate()) + "," +
			ATTRIBUTE_END_DATE + "=" + 
				DateHandler.getOracleToDateFunction(usageReportTemplateBean.getEndDate()) + "," +
			ATTRIBUTE_CATEGORY + "=" + 
				SqlHandler.delimitString(usageReportTemplateBean.getCategory()) + "," +
			ATTRIBUTE_METHOD + "=" + 
				SqlHandler.delimitString(usageReportTemplateBean.getMethod()) + "," +
			ATTRIBUTE_MANUFACTURER + "=" + 
				SqlHandler.delimitString(usageReportTemplateBean.getManufacturer()) + "," +
			ATTRIBUTE_DOCK_ID + "=" + 
				SqlHandler.delimitString(usageReportTemplateBean.getDockId()) + "," +
			ATTRIBUTE_DELIVERY_POINT + "=" + 
				SqlHandler.delimitString(usageReportTemplateBean.getDeliveryPoint()) + "," +
			ATTRIBUTE_MATERIAL_CATEGORY + "=" + 
				SqlHandler.delimitString(usageReportTemplateBean.getMaterialCategory()) + "," +
			ATTRIBUTE_PART_NUMBER_CRITERIA + "=" + 
				SqlHandler.delimitString(usageReportTemplateBean.getPartNumberCriteria()) + "," +
			ATTRIBUTE_MANUFACTURER_CRITERIA + "=" + 
				SqlHandler.delimitString(usageReportTemplateBean.getManufacturerCriteria()) + "," +
			ATTRIBUTE_REPORT_GENERATION_TYPE + "=" + 
				SqlHandler.delimitString(usageReportTemplateBean.getReportGenerationType()) + "," +
			ATTRIBUTE_REPORT_NAME + "=" + 
				SqlHandler.delimitString(usageReportTemplateBean.getReportName()) + "," +
			ATTRIBUTE_REPORT_TYPE + "=" + 
				SqlHandler.delimitString(usageReportTemplateBean.getReportType()) + "," +
			ATTRIBUTE_LOCATION + "=" + 
				SqlHandler.delimitString(usageReportTemplateBean.getLocation()) + " " +
			"where " + ATTRIBUTE_REPORT_TEMPLATE_ID + "=" +
				usageReportTemplateBean.getReportTemplateId();

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

		Collection usageReportTemplateBeanColl = new Vector();

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
			UsageReportTemplateBean usageReportTemplateBean = new UsageReportTemplateBean();
			load(dataSetRow, usageReportTemplateBean);
            SearchCriteria childCriteria = new SearchCriteria();
            childCriteria.addCriterion("reportTemplateId", SearchCriterion.EQUALS, usageReportTemplateBean.getReportTemplateId().toString());
			   SortCriteria childSortCriteria = new SortCriteria();
      		childSortCriteria.addCriterion("templateFieldId");
				usageReportTemplateBean.setReportFieldCollection(factory.select(childCriteria, childSortCriteria));
            usageReportTemplateBeanColl.add(usageReportTemplateBean);
		}

		return usageReportTemplateBeanColl;
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

		Collection usageReportTemplateBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);
        if(log.isDebugEnabled()) {
            log.debug("QUERY:" + query);
        }
		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();
		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			UsageReportTemplateBean usageReportTemplateBean = new UsageReportTemplateBean();
			load(dataSetRow, usageReportTemplateBean);
         usageReportTemplateBeanColl.add(usageReportTemplateBean);
		}

		return usageReportTemplateBeanColl;
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