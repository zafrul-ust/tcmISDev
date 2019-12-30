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
import com.tcmis.client.report.beans.ReportTemplateFieldBean;


/******************************************************************************
 * CLASSNAME: ReportTemplateFieldBeanFactory <br>
 * @version: 1.0, Mar 28, 2008 <br>
 *****************************************************************************/


public class ReportTemplateFieldBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_TEMPLATE_FIELD_ID = "TEMPLATE_FIELD_ID";
	public String ATTRIBUTE_REPORT_TEMPLATE_ID = "REPORT_TEMPLATE_ID";
	public String ATTRIBUTE_REPORT_FIELD = "REPORT_FIELD";

	//table name
	public String TABLE = "REPORT_TEMPLATE_FIELD";
    //sequence name
    public String SEQUENCE_NAME = "REPORT_TEMPLATE_FIELD_SEQ";

    //constructor
	public ReportTemplateFieldBeanFactory(DbManager dbManager) {
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
		else if(attributeName.equals("reportField")) {
			return ATTRIBUTE_REPORT_FIELD;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, ReportTemplateFieldBean.class);
	}


//you need to verify the primary key(s) before uncommenting this

	//delete
	public int delete(ReportTemplateFieldBean reportTemplateFieldBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("templateFieldId", "SearchCriterion.EQUALS",
			"" + reportTemplateFieldBean.getTemplateFieldId());

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


	public int delete(ReportTemplateFieldBean reportTemplateFieldBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("templateFieldId", "SearchCriterion.EQUALS",
			"" + reportTemplateFieldBean.getTemplateFieldId());

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


//you need to verify the primary key(s) before uncommenting this

	//insert
	public int insert(ReportTemplateFieldBean reportTemplateFieldBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(reportTemplateFieldBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(ReportTemplateFieldBean reportTemplateFieldBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_TEMPLATE_FIELD_ID + "," +
			ATTRIBUTE_REPORT_TEMPLATE_ID + "," +
			ATTRIBUTE_REPORT_FIELD + ")" +
			" values (" +
			reportTemplateFieldBean.getTemplateFieldId() + "," +
			reportTemplateFieldBean.getReportTemplateId() + "," +
			SqlHandler.delimitString(reportTemplateFieldBean.getReportField()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(ReportTemplateFieldBean reportTemplateFieldBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(reportTemplateFieldBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(ReportTemplateFieldBean reportTemplateFieldBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_TEMPLATE_FIELD_ID + "=" + 
				StringHandler.nullIfZero(reportTemplateFieldBean.getTemplateFieldId()) + "," +
			ATTRIBUTE_REPORT_TEMPLATE_ID + "=" + 
				StringHandler.nullIfZero(reportTemplateFieldBean.getReportTemplateId()) + "," +
			ATTRIBUTE_REPORT_FIELD + "=" + 
				SqlHandler.delimitString(reportTemplateFieldBean.getReportField()) + " " +
			"where " + ATTRIBUTE_TEMPLATE_FIELD_ID + "=" +
				reportTemplateFieldBean.getTemplateFieldId();

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

		Collection reportTemplateFieldBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);
        if(log.isDebugEnabled()) {
            log.debug("QUERY:" + query);
        }
		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			ReportTemplateFieldBean reportTemplateFieldBean = new ReportTemplateFieldBean();
			load(dataSetRow, reportTemplateFieldBean);
			reportTemplateFieldBeanColl.add(reportTemplateFieldBean);
		}

		return reportTemplateFieldBeanColl;
	}

  public BigDecimal getNextSequenceNumber() throws BaseException {
    int i = this.getDbManager().getOracleSequence(this.SEQUENCE_NAME);
    if(log.isDebugEnabled()){
        log.debug("seq:" + i);
    }
    return new BigDecimal(String.valueOf(i));
  }
}