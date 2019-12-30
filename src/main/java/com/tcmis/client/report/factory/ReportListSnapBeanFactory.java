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

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DataBeanCreationException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.util.*;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.client.report.beans.ReportListSnapBean;


/******************************************************************************
 * CLASSNAME: ReportListSnapBeanFactory <br>
 * @version: 1.0, Nov 22, 2005 <br>
 *****************************************************************************/


public class ReportListSnapBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_LIST_ID = "LIST_ID";
	public String ATTRIBUTE_PARENT_LIST_ID = "PARENT_LIST_ID";
	public String ATTRIBUTE_CHILD_LIST_ID = "CHILD_LIST_ID";
	public String ATTRIBUTE_LIST_LEVEL = "LIST_LEVEL";
	public String ATTRIBUTE_CAS_NUMBER = "CAS_NUMBER";
	public String ATTRIBUTE_RPT_CHEMICAL = "RPT_CHEMICAL";
	public String ATTRIBUTE_THRESHOLD = "THRESHOLD";
	public String ATTRIBUTE_THRESHOLD_UNIT = "THRESHOLD_UNIT";

	//table name
	public String TABLE = "REPORT_LIST_SNAP";


	//constructor
	public ReportListSnapBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("listId")) {
			return ATTRIBUTE_LIST_ID;
		}
		else if(attributeName.equals("parentListId")) {
			return ATTRIBUTE_PARENT_LIST_ID;
		}
		else if(attributeName.equals("childListId")) {
			return ATTRIBUTE_CHILD_LIST_ID;
		}
		else if(attributeName.equals("listLevel")) {
			return ATTRIBUTE_LIST_LEVEL;
		}
		else if(attributeName.equals("casNumber")) {
			return ATTRIBUTE_CAS_NUMBER;
		}
		else if(attributeName.equals("rptChemical")) {
			return ATTRIBUTE_RPT_CHEMICAL;
		}
		else if(attributeName.equals("threshold")) {
			return ATTRIBUTE_THRESHOLD;
		}
		else if(attributeName.equals("thresholdUnit")) {
			return ATTRIBUTE_THRESHOLD_UNIT;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, ReportListSnapBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(ReportListSnapBean reportListSnapBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("listId", "SearchCriterion.EQUALS",
			"" + reportListSnapBean.getListId());

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


	public int delete(ReportListSnapBean reportListSnapBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("listId", "SearchCriterion.EQUALS",
			"" + reportListSnapBean.getListId());

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


//you need to verify the primary key(s) before uncommenting this
/*
	//insert
	public int insert(ReportListSnapBean reportListSnapBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(reportListSnapBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(ReportListSnapBean reportListSnapBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
			ATTRIBUTE_LIST_ID + "," +
			ATTRIBUTE_PARENT_LIST_ID + "," +
			ATTRIBUTE_CHILD_LIST_ID + "," +
			ATTRIBUTE_LIST_LEVEL + "," +
			ATTRIBUTE_CAS_NUMBER + "," +
			ATTRIBUTE_RPT_CHEMICAL + "," +
			ATTRIBUTE_THRESHOLD + "," +
			ATTRIBUTE_THRESHOLD_UNIT + ")" +
			" values (" +
			SqlHandler.delimitString(reportListSnapBean.getListId()) + "," +
			SqlHandler.delimitString(reportListSnapBean.getParentListId()) + "," +
			SqlHandler.delimitString(reportListSnapBean.getChildListId()) + "," +
			reportListSnapBean.getListLevel() + "," +
			SqlHandler.delimitString(reportListSnapBean.getCasNumber()) + "," +
			SqlHandler.delimitString(reportListSnapBean.getRptChemical()) + "," +
			reportListSnapBean.getThreshold() + "," +
			SqlHandler.delimitString(reportListSnapBean.getThresholdUnit()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(ReportListSnapBean reportListSnapBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(reportListSnapBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(ReportListSnapBean reportListSnapBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_LIST_ID + "=" +
				SqlHandler.delimitString(reportListSnapBean.getListId()) + "," +
			ATTRIBUTE_PARENT_LIST_ID + "=" +
				SqlHandler.delimitString(reportListSnapBean.getParentListId()) + "," +
			ATTRIBUTE_CHILD_LIST_ID + "=" +
				SqlHandler.delimitString(reportListSnapBean.getChildListId()) + "," +
			ATTRIBUTE_LIST_LEVEL + "=" +
				StringHandler.nullIfZero(reportListSnapBean.getListLevel()) + "," +
			ATTRIBUTE_CAS_NUMBER + "=" +
				SqlHandler.delimitString(reportListSnapBean.getCasNumber()) + "," +
			ATTRIBUTE_RPT_CHEMICAL + "=" +
				SqlHandler.delimitString(reportListSnapBean.getRptChemical()) + "," +
			ATTRIBUTE_THRESHOLD + "=" +
				StringHandler.nullIfZero(reportListSnapBean.getThreshold()) + "," +
			ATTRIBUTE_THRESHOLD_UNIT + "=" +
				SqlHandler.delimitString(reportListSnapBean.getThresholdUnit()) + " " +
			"where " + ATTRIBUTE_LIST_ID + "=" +
				reportListSnapBean.getListId();

		return new SqlManager().update(conn, query);
	}
*/

	//select
	public Collection select(SearchCriteria criteria)
		throws BaseException {

		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = select(criteria, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}
	public Collection select(SearchCriteria criteria, Connection conn)
		throws BaseException {

		Collection reportListSnapBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			ReportListSnapBean reportListSnapBean = new ReportListSnapBean();
			load(dataSetRow, reportListSnapBean);
			reportListSnapBeanColl.add(reportListSnapBean);
		}

		return reportListSnapBeanColl;
	}

        //select
        public Collection selectDistinctCASForList(SearchCriteria criteria)
                throws BaseException {

                Connection connection = null;
                Collection c = null;
                try {
                        connection = this.getDbManager().getConnection();
                        c = selectDistinctCASForList(criteria, connection);
                }
                finally {
                        this.getDbManager().returnConnection(connection);
                }
                return c;
        }
        public Collection selectDistinctCASForList(SearchCriteria criteria, Connection conn)
                throws BaseException {

                Collection reportListSnapBeanColl = new Vector();

                String query = "select distinct cas_number,rpt_chemical from " + TABLE + " " +
                        getWhereClause(criteria)+" order by to_number(fx_first_number_parse(replace(cas_number,'-','')))";

                DataSet dataSet = new SqlManager().select(conn, query);
                Iterator dataIter = dataSet.iterator();
                while (dataIter.hasNext()) {
                        DataSetRow dataSetRow = (DataSetRow)dataIter.next();
                        ReportListSnapBean reportListSnapBean = new ReportListSnapBean();
                        load(dataSetRow, reportListSnapBean);
                        reportListSnapBeanColl.add(reportListSnapBean);
                }

                return reportListSnapBeanColl;
        }

}