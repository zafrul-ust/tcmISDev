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
import com.tcmis.client.report.beans.ReportTemplateChemicalListBean;


/******************************************************************************
 * CLASSNAME: ReportTemplateChemicalListBeanFactory <br>
 * @version: 1.0, Mar 28, 2008 <br>
 *****************************************************************************/


public class ReportTemplateChemicalListBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_TEMPLATE_FIELD_ID = "TEMPLATE_FIELD_ID";
	public String ATTRIBUTE_REPORT_TEMPLATE_ID = "REPORT_TEMPLATE_ID";
	public String ATTRIBUTE_CHEMICAL_LIST = "CHEMICAL_LIST";

	//table name
	public String TABLE = "REPORT_TEMPLATE_CHEMICAL_LIST";
    public String SEQUENCE_NAME = "REPORT_TEMPLATE_FIELD_SEQ";

	//constructor
	public ReportTemplateChemicalListBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("templateFieldId")) {
			return ATTRIBUTE_TEMPLATE_FIELD_ID;
		}
		else if(attributeName.equals("reportTemplateId")) {
			return ATTRIBUTE_REPORT_TEMPLATE_ID;
		}
		else if(attributeName.equals("chemicalList")) {
			return ATTRIBUTE_CHEMICAL_LIST;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, ReportTemplateChemicalListBean.class);
	}



	//delete
	public int delete(ReportTemplateChemicalListBean reportTemplateChemicalListBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("templateFieldId", "SearchCriterion.EQUALS",
			"" + reportTemplateChemicalListBean.getTemplateFieldId());

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


	public int delete(ReportTemplateChemicalListBean reportTemplateChemicalListBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("templateFieldId", "SearchCriterion.EQUALS",
			"" + reportTemplateChemicalListBean.getTemplateFieldId());

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
	public int insert(ReportTemplateChemicalListBean reportTemplateChemicalListBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(reportTemplateChemicalListBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(ReportTemplateChemicalListBean reportTemplateChemicalListBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_TEMPLATE_FIELD_ID + "," +
			ATTRIBUTE_REPORT_TEMPLATE_ID + "," +
			ATTRIBUTE_CHEMICAL_LIST + ")" +
			" values (" +
			reportTemplateChemicalListBean.getTemplateFieldId() + "," +
			reportTemplateChemicalListBean.getReportTemplateId() + "," +
			SqlHandler.delimitString(reportTemplateChemicalListBean.getChemicalList()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(ReportTemplateChemicalListBean reportTemplateChemicalListBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(reportTemplateChemicalListBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(ReportTemplateChemicalListBean reportTemplateChemicalListBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_TEMPLATE_FIELD_ID + "=" + 
				StringHandler.nullIfZero(reportTemplateChemicalListBean.getTemplateFieldId()) + "," +
			ATTRIBUTE_REPORT_TEMPLATE_ID + "=" + 
				StringHandler.nullIfZero(reportTemplateChemicalListBean.getReportTemplateId()) + "," +
			ATTRIBUTE_CHEMICAL_LIST + "=" + 
				SqlHandler.delimitString(reportTemplateChemicalListBean.getChemicalList()) + " " +
			"where " + ATTRIBUTE_TEMPLATE_FIELD_ID + "=" +
				reportTemplateChemicalListBean.getTemplateFieldId();

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

		Collection reportTemplateChemicalListBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			ReportTemplateChemicalListBean reportTemplateChemicalListBean = new ReportTemplateChemicalListBean();
			load(dataSetRow, reportTemplateChemicalListBean);
			reportTemplateChemicalListBeanColl.add(reportTemplateChemicalListBean);
		}

		return reportTemplateChemicalListBeanColl;
	}

  public BigDecimal getNextSequenceNumber() throws BaseException {
    return new BigDecimal(this.getDbManager().getOracleSequence(this.SEQUENCE_NAME));
  }
}