package com.tcmis.internal.hub.factory;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import com.tcmis.internal.hub.beans.PassThroughReportInputBean;
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
import com.tcmis.internal.hub.beans.IgBilledIssuesViewBean;


/******************************************************************************
 * CLASSNAME: IgBilledIssuesViewBeanFactory <br>
 * @version: 1.0, Apr 4, 2006 <br>
 *****************************************************************************/


public class IgBilledIssuesViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_APPLICATION = "APPLICATION";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
	public String ATTRIBUTE_FAC_PART_NO = "FAC_PART_NO";
	public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
	public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
	public String ATTRIBUTE_CUSTOMER_PO = "CUSTOMER_PO";
	public String ATTRIBUTE_RELEASE_NO = "RELEASE_NO";
	public String ATTRIBUTE_BILLING_METHOD = "BILLING_METHOD";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_PACKAGING = "PACKAGING";
	public String ATTRIBUTE_QUANTITY = "QUANTITY";
	public String ATTRIBUTE_DATE_DELIVERED = "DATE_DELIVERED";
	public String ATTRIBUTE_UNIT_PRICE = "UNIT_PRICE";
	public String ATTRIBUTE_INVOICE_DATE = "INVOICE_DATE";
	public String ATTRIBUTE_PART_DESCRIPTION = "PART_DESCRIPTION";
	public String ATTRIBUTE_HUB = "HUB";
	public String ATTRIBUTE_INVENTORY_GROUP_COLLECTION = "INVENTORY_GROUP_COLLECTION";

	//table name
	public String TABLE = "IG_BILLED_ISSUES_VIEW";


	//constructor
	public IgBilledIssuesViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if(attributeName.equals("application")) {
			return ATTRIBUTE_APPLICATION;
		}
		else if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("catalogId")) {
			return ATTRIBUTE_CATALOG_ID;
		}
		else if(attributeName.equals("facPartNo")) {
			return ATTRIBUTE_FAC_PART_NO;
		}
		else if(attributeName.equals("prNumber")) {
			return ATTRIBUTE_PR_NUMBER;
		}
		else if(attributeName.equals("lineItem")) {
			return ATTRIBUTE_LINE_ITEM;
		}
		else if(attributeName.equals("customerPo")) {
			return ATTRIBUTE_CUSTOMER_PO;
		}
		else if(attributeName.equals("releaseNo")) {
			return ATTRIBUTE_RELEASE_NO;
		}
		else if(attributeName.equals("billingMethod")) {
			return ATTRIBUTE_BILLING_METHOD;
		}
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("packaging")) {
			return ATTRIBUTE_PACKAGING;
		}
		else if(attributeName.equals("quantity")) {
			return ATTRIBUTE_QUANTITY;
		}
		else if(attributeName.equals("dateDelivered")) {
			return ATTRIBUTE_DATE_DELIVERED;
		}
		else if(attributeName.equals("unitPrice")) {
			return ATTRIBUTE_UNIT_PRICE;
		}
		else if(attributeName.equals("invoiceDate")) {
			return ATTRIBUTE_INVOICE_DATE;
		}
		else if(attributeName.equals("partDescription")) {
		 return ATTRIBUTE_PART_DESCRIPTION;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, IgBilledIssuesViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(IgBilledIssuesViewBean igBilledIssuesViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("inventoryGroup", "SearchCriterion.EQUALS",
			"" + igBilledIssuesViewBean.getInventoryGroup());

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


	public int delete(IgBilledIssuesViewBean igBilledIssuesViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("inventoryGroup", "SearchCriterion.EQUALS",
			"" + igBilledIssuesViewBean.getInventoryGroup());

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
	public int insert(IgBilledIssuesViewBean igBilledIssuesViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(igBilledIssuesViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(IgBilledIssuesViewBean igBilledIssuesViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
			ATTRIBUTE_INVENTORY_GROUP + "," +
			ATTRIBUTE_FACILITY_ID + "," +
			ATTRIBUTE_APPLICATION + "," +
			ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_CATALOG_ID + "," +
			ATTRIBUTE_FAC_PART_NO + "," +
			ATTRIBUTE_PR_NUMBER + "," +
			ATTRIBUTE_LINE_ITEM + "," +
			ATTRIBUTE_CUSTOMER_PO + "," +
			ATTRIBUTE_RELEASE_NO + "," +
			ATTRIBUTE_BILLING_METHOD + "," +
			ATTRIBUTE_ITEM_ID + "," +
			ATTRIBUTE_QUANTITY + "," +
			ATTRIBUTE_DATE_DELIVERED_DISPLAY + "," +
			ATTRIBUTE_DATE_DELIVERED + "," +
			ATTRIBUTE_UNIT_PRICE + "," +
			ATTRIBUTE_INVOICE_DATE + ")" +
			" values (" +
			SqlHandler.delimitString(igBilledIssuesViewBean.getInventoryGroup()) + "," +
			SqlHandler.delimitString(igBilledIssuesViewBean.getFacilityId()) + "," +
			SqlHandler.delimitString(igBilledIssuesViewBean.getApplication()) + "," +
			SqlHandler.delimitString(igBilledIssuesViewBean.getCompanyId()) + "," +
			SqlHandler.delimitString(igBilledIssuesViewBean.getCatalogId()) + "," +
			SqlHandler.delimitString(igBilledIssuesViewBean.getFacPartNo()) + "," +
			igBilledIssuesViewBean.getPrNumber() + "," +
			SqlHandler.delimitString(igBilledIssuesViewBean.getLineItem()) + "," +
			SqlHandler.delimitString(igBilledIssuesViewBean.getCustomerPo()) + "," +
			SqlHandler.delimitString(igBilledIssuesViewBean.getReleaseNo()) + "," +
			SqlHandler.delimitString(igBilledIssuesViewBean.getBillingMethod()) + "," +
			igBilledIssuesViewBean.getItemId() + "," +
			igBilledIssuesViewBean.getQuantity() + "," +
			DateHandler.getOracleToDateFunction(igBilledIssuesViewBean.getDateDeliveredDisplay()) + "," +
			DateHandler.getOracleToDateFunction(igBilledIssuesViewBean.getDateDelivered()) + "," +
			igBilledIssuesViewBean.getUnitPrice() + "," +
			DateHandler.getOracleToDateFunction(igBilledIssuesViewBean.getInvoiceDate()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(IgBilledIssuesViewBean igBilledIssuesViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(igBilledIssuesViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(IgBilledIssuesViewBean igBilledIssuesViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_INVENTORY_GROUP + "=" +
				SqlHandler.delimitString(igBilledIssuesViewBean.getInventoryGroup()) + "," +
			ATTRIBUTE_FACILITY_ID + "=" +
				SqlHandler.delimitString(igBilledIssuesViewBean.getFacilityId()) + "," +
			ATTRIBUTE_APPLICATION + "=" +
				SqlHandler.delimitString(igBilledIssuesViewBean.getApplication()) + "," +
			ATTRIBUTE_COMPANY_ID + "=" +
				SqlHandler.delimitString(igBilledIssuesViewBean.getCompanyId()) + "," +
			ATTRIBUTE_CATALOG_ID + "=" +
				SqlHandler.delimitString(igBilledIssuesViewBean.getCatalogId()) + "," +
			ATTRIBUTE_FAC_PART_NO + "=" +
				SqlHandler.delimitString(igBilledIssuesViewBean.getFacPartNo()) + "," +
			ATTRIBUTE_PR_NUMBER + "=" +
				StringHandler.nullIfZero(igBilledIssuesViewBean.getPrNumber()) + "," +
			ATTRIBUTE_LINE_ITEM + "=" +
				SqlHandler.delimitString(igBilledIssuesViewBean.getLineItem()) + "," +
			ATTRIBUTE_CUSTOMER_PO + "=" +
				SqlHandler.delimitString(igBilledIssuesViewBean.getCustomerPo()) + "," +
			ATTRIBUTE_RELEASE_NO + "=" +
				SqlHandler.delimitString(igBilledIssuesViewBean.getReleaseNo()) + "," +
			ATTRIBUTE_BILLING_METHOD + "=" +
				SqlHandler.delimitString(igBilledIssuesViewBean.getBillingMethod()) + "," +
			ATTRIBUTE_ITEM_ID + "=" +
				StringHandler.nullIfZero(igBilledIssuesViewBean.getItemId()) + "," +
			ATTRIBUTE_QUANTITY + "=" +
				StringHandler.nullIfZero(igBilledIssuesViewBean.getQuantity()) + "," +
			ATTRIBUTE_DATE_DELIVERED_DISPLAY + "=" +
				DateHandler.getOracleToDateFunction(igBilledIssuesViewBean.getDateDeliveredDisplay()) + "," +
			ATTRIBUTE_DATE_DELIVERED + "=" +
				DateHandler.getOracleToDateFunction(igBilledIssuesViewBean.getDateDelivered()) + "," +
			ATTRIBUTE_UNIT_PRICE + "=" +
				StringHandler.nullIfZero(igBilledIssuesViewBean.getUnitPrice()) + "," +
			ATTRIBUTE_INVOICE_DATE + "=" +
				DateHandler.getOracleToDateFunction(igBilledIssuesViewBean.getInvoiceDate()) + " " +
			"where " + ATTRIBUTE_INVENTORY_GROUP + "=" +
				igBilledIssuesViewBean.getInventoryGroup();

		return new SqlManager().update(conn, query);
	}
*/

	//select
	public Collection select(SearchCriteria criteria,PassThroughReportInputBean bean,boolean iscollection)
		throws BaseException {

		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = select(criteria,bean,iscollection, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}

 public Collection select(SearchCriteria criteria,PassThroughReportInputBean bean,boolean iscollection, Connection conn)
	throws BaseException {

	Collection igBilledIssuesViewBeanColl = new Vector();

	String whereClause = getWhereClause(criteria);
	if (iscollection) {
	 if (whereClause.trim().length() > 0) {
		whereClause += "and ";
	 }

	 whereClause += " " + ATTRIBUTE_INVENTORY_GROUP + " in (select " +
		ATTRIBUTE_INVENTORY_GROUP + " from " + ATTRIBUTE_INVENTORY_GROUP_COLLECTION +
		" where " + ATTRIBUTE_HUB + " = '" + bean.getHub() + "' and " +
		ATTRIBUTE_INVENTORY_GROUP_COLLECTION + " = '" + bean.getInventoryGroup() +
		"') ";
	}

	String query = "select * from " + TABLE + " " +
	 whereClause + "order by " + bean.getSort() + " DESC";

		DataSet dataSet = new SqlManager().select(conn, query);
		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			IgBilledIssuesViewBean igBilledIssuesViewBean = new IgBilledIssuesViewBean();
			load(dataSetRow, igBilledIssuesViewBean);
			igBilledIssuesViewBean.setExtPrice(igBilledIssuesViewBean.getQuantity().multiply(igBilledIssuesViewBean.getUnitPrice()));
			igBilledIssuesViewBeanColl.add(igBilledIssuesViewBean);
		}

		return igBilledIssuesViewBeanColl;
	}
}