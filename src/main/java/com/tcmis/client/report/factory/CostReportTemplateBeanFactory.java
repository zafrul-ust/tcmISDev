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
import com.tcmis.client.report.beans.CostReportTemplateBean;


/******************************************************************************
 * CLASSNAME: CostReportTemplateBeanFactory <br>
 * @version: 1.0, Aug 30, 2007 <br>
 *****************************************************************************/


public class CostReportTemplateBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_PERSONNEL_ID = "PERSONNEL_ID";
	public String ATTRIBUTE_REPORT_NAME = "REPORT_NAME";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_APPLICATION = "APPLICATION";
	public String ATTRIBUTE_ACCOUNT_SYS_ID = "ACCOUNT_SYS_ID";
	public String ATTRIBUTE_CHARGE_TYPE = "CHARGE_TYPE";
	public String ATTRIBUTE_CHARGE_NUMBER_1 = "CHARGE_NUMBER_1";
	public String ATTRIBUTE_CHARGE_NUMBER_2 = "CHARGE_NUMBER_2";
	public String ATTRIBUTE_SEARCH_BY = "SEARCH_BY";
	public String ATTRIBUTE_SEARCH_TYPE = "SEARCH_TYPE";
	public String ATTRIBUTE_SEARCH_TEXT = "SEARCH_TEXT";
	public String ATTRIBUTE_INVOICE = "INVOICE";
	public String ATTRIBUTE_INVOICE_PERIOD = "INVOICE_PERIOD";
	public String ATTRIBUTE_START_DATE = "START_DATE";
	public String ATTRIBUTE_END_DATE = "END_DATE";
	public String ATTRIBUTE_CATEGORY_ID = "CATEGORY_ID";
	public String ATTRIBUTE_REPORT_FIELD = "REPORT_FIELD";
	public String ATTRIBUTE_INVOICE_START_DATE = "INVOICE_START_DATE";
	public String ATTRIBUTE_INVOICE_END_DATE = "INVOICE_END_DATE";
	public String ATTRIBUTE_REQUESTOR = "REQUESTOR";
	public String ATTRIBUTE_REQUESTOR_NAME = "REQUESTOR_NAME";
	public String ATTRIBUTE_COMMODITY = "COMMODITY";
	public String ATTRIBUTE_DATE_DELIVERED_GROUP_BY = "DATE_DELIVERED_GROUP_BY";
	public String ATTRIBUTE_SOURCE_HUB = "SOURCE_HUB";
	public String ATTRIBUTE_ITEM_TYPE = "ITEM_TYPE";
	public String ATTRIBUTE_REPORT_TYPE = "REPORT_TYPE";
	public String ATTRIBUTE_UOM = "UOM";

	//table name
	public String TABLE = "COST_REPORT_TEMPLATE";


	//constructor
	public CostReportTemplateBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("personnelId")) {
			return ATTRIBUTE_PERSONNEL_ID;
		}
		else if(attributeName.equals("reportName")) {
			return ATTRIBUTE_REPORT_NAME;
		}
		else if(attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if(attributeName.equals("application")) {
			return ATTRIBUTE_APPLICATION;
		}
		else if(attributeName.equals("accountSysId")) {
			return ATTRIBUTE_ACCOUNT_SYS_ID;
		}
		else if(attributeName.equals("chargeType")) {
			return ATTRIBUTE_CHARGE_TYPE;
		}
		else if(attributeName.equals("chargeNumber11")) {
			return ATTRIBUTE_CHARGE_NUMBER_1;
		}
		else if(attributeName.equals("chargeNumber22")) {
			return ATTRIBUTE_CHARGE_NUMBER_2;
		}
		else if(attributeName.equals("searchBy")) {
			return ATTRIBUTE_SEARCH_BY;
		}
		else if(attributeName.equals("searchType")) {
			return ATTRIBUTE_SEARCH_TYPE;
		}
		else if(attributeName.equals("searchText")) {
			return ATTRIBUTE_SEARCH_TEXT;
		}
		else if(attributeName.equals("invoice")) {
			return ATTRIBUTE_INVOICE;
		}
		else if(attributeName.equals("invoicePeriod")) {
			return ATTRIBUTE_INVOICE_PERIOD;
		}
		else if(attributeName.equals("startDate")) {
			return ATTRIBUTE_START_DATE;
		}
		else if(attributeName.equals("endDate")) {
			return ATTRIBUTE_END_DATE;
		}
		else if(attributeName.equals("categoryId")) {
			return ATTRIBUTE_CATEGORY_ID;
		}
		else if(attributeName.equals("reportField")) {
			return ATTRIBUTE_REPORT_FIELD;
		}
		else if(attributeName.equals("invoiceStartDate")) {
			return ATTRIBUTE_INVOICE_START_DATE;
		}
		else if(attributeName.equals("invoiceEndDate")) {
			return ATTRIBUTE_INVOICE_END_DATE;
		}
		else if(attributeName.equals("requestor")) {
			return ATTRIBUTE_REQUESTOR;
		}
		else if(attributeName.equals("requestorName")) {
			return ATTRIBUTE_REQUESTOR_NAME;
		}
		else if(attributeName.equals("commodity")) {
			return ATTRIBUTE_COMMODITY;
		}
		else if(attributeName.equals("dateDeliveredGroupBy")) {
			return ATTRIBUTE_DATE_DELIVERED_GROUP_BY;
		}
		else if(attributeName.equals("sourceHub")) {
			return ATTRIBUTE_SOURCE_HUB;
		}
		else if(attributeName.equals("itemType")) {
			return ATTRIBUTE_ITEM_TYPE;
		}
		else if(attributeName.equals("reportType")) {
			return ATTRIBUTE_REPORT_TYPE;
		}
		else if(attributeName.equals("uom")) {
			return ATTRIBUTE_UOM;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, CostReportTemplateBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(CostReportTemplateBean costReportTemplateBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
			"" + costReportTemplateBean.getCompanyId());

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


	public int delete(CostReportTemplateBean costReportTemplateBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
			"" + costReportTemplateBean.getCompanyId());

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
	//insert
	public int insert(CostReportTemplateBean costReportTemplateBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(costReportTemplateBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(CostReportTemplateBean costReportTemplateBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_PERSONNEL_ID + "," +
			ATTRIBUTE_REPORT_NAME + "," +
			ATTRIBUTE_FACILITY_ID + "," +
			ATTRIBUTE_APPLICATION + "," +
			ATTRIBUTE_ACCOUNT_SYS_ID + "," +
			ATTRIBUTE_CHARGE_TYPE + "," +
			ATTRIBUTE_CHARGE_NUMBER_1 + "," +
			ATTRIBUTE_CHARGE_NUMBER_2 + "," +
			ATTRIBUTE_SEARCH_BY + "," +
			ATTRIBUTE_SEARCH_TEXT + "," +
			ATTRIBUTE_INVOICE + "," +
			ATTRIBUTE_INVOICE_PERIOD + "," +
			ATTRIBUTE_START_DATE + "," +
			ATTRIBUTE_END_DATE + "," +
			ATTRIBUTE_CATEGORY_ID + "," +
			ATTRIBUTE_REPORT_FIELD + "," +
			ATTRIBUTE_INVOICE_START_DATE + "," +
			ATTRIBUTE_INVOICE_END_DATE + "," +
			ATTRIBUTE_REQUESTOR + "," +
			ATTRIBUTE_REQUESTOR_NAME + "," +
			ATTRIBUTE_COMMODITY + "," +
			ATTRIBUTE_DATE_DELIVERED_GROUP_BY + "," +
			ATTRIBUTE_SOURCE_HUB + "," +
			ATTRIBUTE_REPORT_TYPE + "," +
			ATTRIBUTE_SEARCH_TYPE + "," +
			ATTRIBUTE_ITEM_TYPE + "," +
			ATTRIBUTE_UOM + ")" +
			" values (" +
			SqlHandler.delimitString(costReportTemplateBean.getCompanyId()) + "," +
			costReportTemplateBean.getPersonnelId() + "," +
			SqlHandler.delimitString(costReportTemplateBean.getReportName()) + "," +
			SqlHandler.delimitString(costReportTemplateBean.getFacilityId()) + "," +
			SqlHandler.delimitString(costReportTemplateBean.getApplication()) + "," +
			SqlHandler.delimitString(costReportTemplateBean.getAccountSysId()) + "," +
			SqlHandler.delimitString(costReportTemplateBean.getChargeType()) + "," +
			SqlHandler.delimitString(costReportTemplateBean.getChargeNumber1()) + "," +
			SqlHandler.delimitString(costReportTemplateBean.getChargeNumber2()) + "," +
			SqlHandler.delimitString(costReportTemplateBean.getSearchBy()) + "," +
			SqlHandler.delimitString(costReportTemplateBean.getSearchText()) + "," +
			costReportTemplateBean.getInvoice() + "," +
			costReportTemplateBean.getInvoicePeriod() + "," +
			DateHandler.getOracleToDateFunction(costReportTemplateBean.getStartDate()) + "," +
			DateHandler.getOracleToDateFunction(costReportTemplateBean.getEndDate()) + "," +
			costReportTemplateBean.getCategoryId() + "," +
			SqlHandler.delimitString(costReportTemplateBean.getReportField()) + "," +
			DateHandler.getOracleToDateFunction(costReportTemplateBean.getInvoiceStartDate()) + "," +
			DateHandler.getOracleToDateFunction(costReportTemplateBean.getInvoiceEndDate()) + "," +
			costReportTemplateBean.getRequestor() + "," +
			SqlHandler.delimitString(costReportTemplateBean.getRequestorName()) + "," +
			SqlHandler.delimitString(costReportTemplateBean.getCommodity()) + "," +
			SqlHandler.delimitString(costReportTemplateBean.getDateDeliveredGroupBy()) + "," +
			SqlHandler.delimitString(costReportTemplateBean.getSourceHub()) + "," +
			SqlHandler.delimitString(costReportTemplateBean.getReportType()) + "," +
			SqlHandler.delimitString(costReportTemplateBean.getSearchType()) + "," +
			SqlHandler.delimitString(costReportTemplateBean.getItemType()) + ","+
		   SqlHandler.delimitString(costReportTemplateBean.getUom()) + ")";

		return sqlManager.update(conn, query);
	}


	/*
	//update
	public int update(CostReportTemplateBean costReportTemplateBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(costReportTemplateBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(CostReportTemplateBean costReportTemplateBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_COMPANY_ID + "=" + 
				SqlHandler.delimitString(costReportTemplateBean.getCompanyId()) + "," +
			ATTRIBUTE_PERSONNEL_ID + "=" + 
				StringHandler.nullIfZero(costReportTemplateBean.getPersonnelId()) + "," +
			ATTRIBUTE_REPORT_NAME + "=" + 
				SqlHandler.delimitString(costReportTemplateBean.getReportName()) + "," +
			ATTRIBUTE_FACILITY_ID + "=" + 
				SqlHandler.delimitString(costReportTemplateBean.getFacilityId()) + "," +
			ATTRIBUTE_APPLICATION + "=" + 
				SqlHandler.delimitString(costReportTemplateBean.getApplication()) + "," +
			ATTRIBUTE_ACCOUNT_SYS_ID + "=" + 
				SqlHandler.delimitString(costReportTemplateBean.getAccountSysId()) + "," +
			ATTRIBUTE_CHARGE_TYPE + "=" + 
				SqlHandler.delimitString(costReportTemplateBean.getChargeType()) + "," +
			ATTRIBUTE_CHARGE_NUMBER_1 + "=" + 
				SqlHandler.delimitString(costReportTemplateBean.getChargeNumber1()) + "," +
			ATTRIBUTE_CHARGE_NUMBER_2 + "=" + 
				SqlHandler.delimitString(costReportTemplateBean.getChargeNumber2()) + "," +
			ATTRIBUTE_SEARCH_BY + "=" + 
				SqlHandler.delimitString(costReportTemplateBean.getSearchBy()) + "," +
			ATTRIBUTE_SEARCH_TEXT + "=" + 
				SqlHandler.delimitString(costReportTemplateBean.getSearchText()) + "," +
			ATTRIBUTE_INVOICE + "=" + 
				StringHandler.nullIfZero(costReportTemplateBean.getInvoice()) + "," +
			ATTRIBUTE_INVOICE_PERIOD + "=" + 
				StringHandler.nullIfZero(costReportTemplateBean.getInvoicePeriod()) + "," +
			ATTRIBUTE_START_DATE + "=" + 
				DateHandler.getOracleToDateFunction(costReportTemplateBean.getStartDate()) + "," +
			ATTRIBUTE_END_DATE + "=" + 
				DateHandler.getOracleToDateFunction(costReportTemplateBean.getEndDate()) + "," +
			ATTRIBUTE_CATEGORY_ID + "=" + 
				StringHandler.nullIfZero(costReportTemplateBean.getCategoryId()) + "," +
			ATTRIBUTE_REPORT_FIELD + "=" + 
				SqlHandler.delimitString(costReportTemplateBean.getReportField()) + "," +
			ATTRIBUTE_INVOICE_START_DATE + "=" + 
				DateHandler.getOracleToDateFunction(costReportTemplateBean.getInvoiceStartDate()) + "," +
			ATTRIBUTE_INVOICE_END_DATE + "=" + 
				DateHandler.getOracleToDateFunction(costReportTemplateBean.getInvoiceEndDate()) + "," +
			ATTRIBUTE_REQUESTOR + "=" + 
				StringHandler.nullIfZero(costReportTemplateBean.getRequestor()) + "," +
			ATTRIBUTE_REQUESTOR_NAME + "=" + 
				SqlHandler.delimitString(costReportTemplateBean.getRequestorName()) + "," +
			ATTRIBUTE_COMMODITY + "=" + 
				SqlHandler.delimitString(costReportTemplateBean.getCommodity()) + "," +
			ATTRIBUTE_DATE_DELIVERED_GROUP_BY + "=" + 
				SqlHandler.delimitString(costReportTemplateBean.getDateDeliveredGroupBy()) + "," +
			ATTRIBUTE_SOURCE_HUB + "=" + 
				SqlHandler.delimitString(costReportTemplateBean.getSourceHub()) + "," +
			ATTRIBUTE_ITEM_TYPE + "=" + 
				SqlHandler.delimitString(costReportTemplateBean.getItemType()) + " " +
			"where " + ATTRIBUTE_COMPANY_ID + "=" +
				costReportTemplateBean.getCompanyId();

		return new SqlManager().update(conn, query);
	}
*/

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

		Collection costReportTemplateBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			CostReportTemplateBean costReportTemplateBean = new CostReportTemplateBean();
			load(dataSetRow, costReportTemplateBean);
			costReportTemplateBeanColl.add(costReportTemplateBean);
		}

		return costReportTemplateBeanColl;
	}

	//select
	public Collection selectMyTemplate(SearchCriteria criteria)
		throws BaseException {

		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = selectMyTemplate(criteria, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}
	public Collection selectMyTemplate(SearchCriteria criteria, Connection conn)
		throws BaseException {

		Collection costReportTemplateBeanColl = new Vector();

		String query = "select distinct report_name from " + TABLE + " " +getWhereClause(criteria)+" order by report_name";

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			CostReportTemplateBean costReportTemplateBean = new CostReportTemplateBean();
			load(dataSetRow, costReportTemplateBean);
			costReportTemplateBeanColl.add(costReportTemplateBean);
		}
		return costReportTemplateBeanColl;
	}

	public int updateOldReportField(String query)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = updateOldReportField(query, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int updateOldReportField(String query, Connection conn)
		throws BaseException {

		return new SqlManager().update(conn, query);
	}

}