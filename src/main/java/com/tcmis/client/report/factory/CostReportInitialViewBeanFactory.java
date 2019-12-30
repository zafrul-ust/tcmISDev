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
import com.tcmis.client.report.beans.CostReportInitialViewBean;


/******************************************************************************
 * CLASSNAME: CostReportInitialViewBeanFactory <br>
 * @version: 1.0, Aug 30, 2007 <br>
 *****************************************************************************/


public class CostReportInitialViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_COST_REPORT_GROUP = "COST_REPORT_GROUP";
	public String ATTRIBUTE_FACILITY_NAME = "FACILITY_NAME";
	public String ATTRIBUTE_APPLICATION = "APPLICATION";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_ACCOUNT_SYS_ID = "ACCOUNT_SYS_ID";
	public String ATTRIBUTE_CHARGE_TYPE = "CHARGE_TYPE";
	public String ATTRIBUTE_PO_REQUIRED = "PO_REQUIRED";
	public String ATTRIBUTE_PR_ACCOUNT_REQUIRED = "PR_ACCOUNT_REQUIRED";
	public String ATTRIBUTE_CHARGE_LABEL_1 = "CHARGE_LABEL_1";
	public String ATTRIBUTE_CHARGE_LABEL_2 = "CHARGE_LABEL_2";
	public String ATTRIBUTE_APPLICATION_DESC = "APPLICATION_DESC";
	public String ATTRIBUTE_ACCOUNT_TYPE = "ACCOUNT_TYPE";
	public String ATTRIBUTE_PERSONNEL_ID = "PERSONNEL_ID";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
  public String ATTRIBUTE_COMPANY_NAME = "COMPANY_NAME";

	//table name
	public String TABLE = "COST_REPORT_INITIAL_VIEW";


	//constructor
	public CostReportInitialViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("costReportGroup")) {
			return ATTRIBUTE_COST_REPORT_GROUP;
		}
		else if(attributeName.equals("facilityName")) {
			return ATTRIBUTE_FACILITY_NAME;
		}
		else if(attributeName.equals("application")) {
			return ATTRIBUTE_APPLICATION;
		}
		else if(attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if(attributeName.equals("accountSysId")) {
			return ATTRIBUTE_ACCOUNT_SYS_ID;
		}
		else if(attributeName.equals("chargeType")) {
			return ATTRIBUTE_CHARGE_TYPE;
		}
		else if(attributeName.equals("poRequired")) {
			return ATTRIBUTE_PO_REQUIRED;
		}
		else if(attributeName.equals("prAccountRequired")) {
			return ATTRIBUTE_PR_ACCOUNT_REQUIRED;
		}
		else if(attributeName.equals("chargeLabel1")) {
			return ATTRIBUTE_CHARGE_LABEL_1;
		}
		else if(attributeName.equals("chargeLabel2")) {
			return ATTRIBUTE_CHARGE_LABEL_2;
		}
		else if(attributeName.equals("applicationDesc")) {
			return ATTRIBUTE_APPLICATION_DESC;
		}
		else if(attributeName.equals("accountType")) {
			return ATTRIBUTE_ACCOUNT_TYPE;
		}
		else if(attributeName.equals("personnelId")) {
			return ATTRIBUTE_PERSONNEL_ID;
		}
		else if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("companyName")) {
			return ATTRIBUTE_COMPANY_NAME;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, CostReportInitialViewBean.class);
	}

	//select
	public Collection select(SearchCriteria criteria) throws BaseException {
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

	public Collection select(SearchCriteria criteria, Connection conn) throws BaseException {
		Collection costReportInitialViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " ";
		if (criteria != null) {
			query += getWhereClause(criteria);
		}

		DataSet dataSet = new SqlManager().select(conn, query);
		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			CostReportInitialViewBean costReportInitialViewBean = new CostReportInitialViewBean();
			load(dataSetRow, costReportInitialViewBean);
			costReportInitialViewBeanColl.add(costReportInitialViewBean);
		}

		return costReportInitialViewBeanColl;
	}

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

		Collection costReportInitialViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " ";
		if (criteria != null) {
			query += getWhereClause(criteria);
		}
		if (sortCriteria != null) {
			query += getOrderByClause(sortCriteria);
		}

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			CostReportInitialViewBean costReportInitialViewBean = new CostReportInitialViewBean();
			load(dataSetRow, costReportInitialViewBean);
			costReportInitialViewBeanColl.add(costReportInitialViewBean);
		}

		return costReportInitialViewBeanColl;
	}
}