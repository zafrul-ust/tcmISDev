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
import com.tcmis.client.report.beans.MaterialmatrixReportTemplateBean;


/******************************************************************************
 * CLASSNAME: MaterialmatrixReportTemplateBeanFactory <br>
 * @version: 1.0, Mar 28, 2008 <br>
 *****************************************************************************/


public class MaterialmatrixReportTemplateBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_REPORT_TEMPLATE_ID = "REPORT_TEMPLATE_ID";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_PERSONNEL_ID = "PERSONNEL_ID";
	public String ATTRIBUTE_TEMPLATE_NAME = "TEMPLATE_NAME";
	public String ATTRIBUTE_REPORTING_ENTITY = "REPORTING_ENTITY";
	public String ATTRIBUTE_PART_NUMBER = "PART_NUMBER";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_BEGIN_DATE = "BEGIN_DATE";
	public String ATTRIBUTE_APPLICATION = "APPLICATION";
	public String ATTRIBUTE_REPORT_CRITERIA = "REPORT_CRITERIA";
	public String ATTRIBUTE_LIST_FORMAT = "LIST_FORMAT";
	public String ATTRIBUTE_INVENTORY_DATE = "INVENTORY_DATE";
	public String ATTRIBUTE_PART_NUMBER_CRITERIA = "PART_NUMBER_CRITERIA";
	public String ATTRIBUTE_REPORT_GENERATION_TYPE = "REPORT_GENERATION_TYPE";
	public String ATTRIBUTE_REPORT_NAME = "REPORT_NAME";
	public String ATTRIBUTE_END_DATE = "END_DATE";

	//table name
	public String TABLE = "MATERIALMATRIX_REPORT_TEMPLATE";
    public String SEQUENCE_NAME = "REPORT_TEMPLATE_SEQ";

	//constructor
	public MaterialmatrixReportTemplateBeanFactory(DbManager dbManager) {
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
		else if(attributeName.equals("partNumber")) {
			return ATTRIBUTE_PART_NUMBER;
		}
		else if(attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if(attributeName.equals("beginDate")) {
			return ATTRIBUTE_BEGIN_DATE;
		}
		else if(attributeName.equals("application")) {
			return ATTRIBUTE_APPLICATION;
		}
		else if(attributeName.equals("reportCriteria")) {
			return ATTRIBUTE_REPORT_CRITERIA;
		}
		else if(attributeName.equals("listFormat")) {
			return ATTRIBUTE_LIST_FORMAT;
		}
		else if(attributeName.equals("inventoryDate")) {
			return ATTRIBUTE_INVENTORY_DATE;
		}
		else if(attributeName.equals("partNumberCriteria")) {
			return ATTRIBUTE_PART_NUMBER_CRITERIA;
		}
		else if(attributeName.equals("reportGenerationType")) {
			return ATTRIBUTE_REPORT_GENERATION_TYPE;
		}
		else if(attributeName.equals("reportName")) {
			return ATTRIBUTE_REPORT_NAME;
		}
		else if(attributeName.equals("endDate")) {
			return ATTRIBUTE_END_DATE;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, MaterialmatrixReportTemplateBean.class);
	}


	//delete
	public int delete(MaterialmatrixReportTemplateBean materialmatrixReportTemplateBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("reportTemplateId", "SearchCriterion.EQUALS",
			"" + materialmatrixReportTemplateBean.getReportTemplateId());

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


	public int delete(MaterialmatrixReportTemplateBean materialmatrixReportTemplateBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("reportTemplateId", "SearchCriterion.EQUALS",
			"" + materialmatrixReportTemplateBean.getReportTemplateId());

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
	public int insert(MaterialmatrixReportTemplateBean materialmatrixReportTemplateBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(materialmatrixReportTemplateBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(MaterialmatrixReportTemplateBean materialmatrixReportTemplateBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_REPORT_TEMPLATE_ID + "," +
			//ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_PERSONNEL_ID + "," +
			ATTRIBUTE_TEMPLATE_NAME + "," +
			ATTRIBUTE_REPORTING_ENTITY + "," +
			ATTRIBUTE_PART_NUMBER + "," +
			ATTRIBUTE_FACILITY_ID + "," +
			ATTRIBUTE_BEGIN_DATE + "," +
			ATTRIBUTE_APPLICATION + "," +
			ATTRIBUTE_REPORT_CRITERIA + "," +
			ATTRIBUTE_LIST_FORMAT + "," +
			ATTRIBUTE_INVENTORY_DATE + "," +
			ATTRIBUTE_PART_NUMBER_CRITERIA + "," +
			ATTRIBUTE_REPORT_GENERATION_TYPE + "," +
			ATTRIBUTE_REPORT_NAME + "," +
			ATTRIBUTE_END_DATE + ")" +
			" values (" +
			materialmatrixReportTemplateBean.getReportTemplateId() + "," +
			//SqlHandler.delimitString(materialmatrixReportTemplateBean.getCompanyId()) + "," +
			materialmatrixReportTemplateBean.getPersonnelId() + "," +
			SqlHandler.delimitString(materialmatrixReportTemplateBean.getTemplateName()) + "," +
			SqlHandler.delimitString(materialmatrixReportTemplateBean.getReportingEntity()) + "," +
			SqlHandler.delimitString(materialmatrixReportTemplateBean.getPartNumber()) + "," +
			SqlHandler.delimitString(materialmatrixReportTemplateBean.getFacilityId()) + "," +
			DateHandler.getOracleToDateFunction(materialmatrixReportTemplateBean.getBeginDate()) + "," +
			SqlHandler.delimitString(materialmatrixReportTemplateBean.getApplication()) + "," +
			SqlHandler.delimitString(materialmatrixReportTemplateBean.getReportCriteria()) + "," +
			SqlHandler.delimitString(materialmatrixReportTemplateBean.getListFormat()) + "," +
			DateHandler.getOracleToDateFunction(materialmatrixReportTemplateBean.getInventoryDate()) + "," +
			SqlHandler.delimitString(materialmatrixReportTemplateBean.getPartNumberCriteria()) + "," +
			SqlHandler.delimitString(materialmatrixReportTemplateBean.getReportGenerationType()) + "," +
			SqlHandler.delimitString(materialmatrixReportTemplateBean.getReportName()) + "," +
			DateHandler.getOracleToDateFunction(materialmatrixReportTemplateBean.getEndDate()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(MaterialmatrixReportTemplateBean materialmatrixReportTemplateBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(materialmatrixReportTemplateBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(MaterialmatrixReportTemplateBean materialmatrixReportTemplateBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_REPORT_TEMPLATE_ID + "=" + 
				StringHandler.nullIfZero(materialmatrixReportTemplateBean.getReportTemplateId()) + "," +
/*
			ATTRIBUTE_COMPANY_ID + "=" +
				SqlHandler.delimitString(materialmatrixReportTemplateBean.getCompanyId()) + "," +
*/
			ATTRIBUTE_PERSONNEL_ID + "=" +
				StringHandler.nullIfZero(materialmatrixReportTemplateBean.getPersonnelId()) + "," +
			ATTRIBUTE_TEMPLATE_NAME + "=" + 
				SqlHandler.delimitString(materialmatrixReportTemplateBean.getTemplateName()) + "," +
			ATTRIBUTE_REPORTING_ENTITY + "=" + 
				SqlHandler.delimitString(materialmatrixReportTemplateBean.getReportingEntity()) + "," +
			ATTRIBUTE_PART_NUMBER + "=" + 
				SqlHandler.delimitString(materialmatrixReportTemplateBean.getPartNumber()) + "," +
			ATTRIBUTE_FACILITY_ID + "=" + 
				SqlHandler.delimitString(materialmatrixReportTemplateBean.getFacilityId()) + "," +
			ATTRIBUTE_BEGIN_DATE + "=" + 
				DateHandler.getOracleToDateFunction(materialmatrixReportTemplateBean.getBeginDate()) + "," +
			ATTRIBUTE_APPLICATION + "=" + 
				SqlHandler.delimitString(materialmatrixReportTemplateBean.getApplication()) + "," +
			ATTRIBUTE_REPORT_CRITERIA + "=" + 
				SqlHandler.delimitString(materialmatrixReportTemplateBean.getReportCriteria()) + "," +
			ATTRIBUTE_LIST_FORMAT + "=" + 
				SqlHandler.delimitString(materialmatrixReportTemplateBean.getListFormat()) + "," +
			ATTRIBUTE_INVENTORY_DATE + "=" + 
				DateHandler.getOracleToDateFunction(materialmatrixReportTemplateBean.getInventoryDate()) + "," +
			ATTRIBUTE_PART_NUMBER_CRITERIA + "=" + 
				SqlHandler.delimitString(materialmatrixReportTemplateBean.getPartNumberCriteria()) + "," +
			ATTRIBUTE_REPORT_GENERATION_TYPE + "=" + 
				SqlHandler.delimitString(materialmatrixReportTemplateBean.getReportGenerationType()) + "," +
			ATTRIBUTE_REPORT_NAME + "=" + 
				SqlHandler.delimitString(materialmatrixReportTemplateBean.getReportName()) + "," +
			ATTRIBUTE_END_DATE + "=" + 
				DateHandler.getOracleToDateFunction(materialmatrixReportTemplateBean.getEndDate()) + " " +
			"where " + ATTRIBUTE_REPORT_TEMPLATE_ID + "=" +
				materialmatrixReportTemplateBean.getReportTemplateId();

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

		Collection materialmatrixReportTemplateBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();
        ReportTemplateFieldBeanFactory factory = new ReportTemplateFieldBeanFactory(getDbManager());
        ReportTemplateChemicalListBeanFactory chemFactory = new ReportTemplateChemicalListBeanFactory(getDbManager());
        while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			MaterialmatrixReportTemplateBean materialmatrixReportTemplateBean = new MaterialmatrixReportTemplateBean();
			load(dataSetRow, materialmatrixReportTemplateBean);
				SearchCriteria childCriteria = new SearchCriteria();
            childCriteria.addCriterion("reportTemplateId", SearchCriterion.EQUALS, materialmatrixReportTemplateBean.getReportTemplateId().toString());
            //childCriteria.addCriterion("reportField", SearchCriterion.IS_NOT, "null");
			   SortCriteria childSortCriteria = new SortCriteria();
      		childSortCriteria.addCriterion("templateFieldId");
				materialmatrixReportTemplateBean.setReportFieldCollection(factory.select(childCriteria, childSortCriteria));
            childCriteria = new SearchCriteria();
				childCriteria.addCriterion("reportTemplateId", SearchCriterion.EQUALS, materialmatrixReportTemplateBean.getReportTemplateId().toString());
            //childCriteria.addCriterion("chemicalList", SearchCriterion.IS_NOT, "null");
				materialmatrixReportTemplateBean.setChemicalFieldCollection(chemFactory.select(childCriteria, childSortCriteria));
            materialmatrixReportTemplateBeanColl.add(materialmatrixReportTemplateBean);
		}
		return materialmatrixReportTemplateBeanColl;
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

		Collection materialmatrixReportTemplateBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();
      while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			MaterialmatrixReportTemplateBean materialmatrixReportTemplateBean = new MaterialmatrixReportTemplateBean();
			load(dataSetRow, materialmatrixReportTemplateBean);
			materialmatrixReportTemplateBeanColl.add(materialmatrixReportTemplateBean);
		}
		return materialmatrixReportTemplateBeanColl;
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