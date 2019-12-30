package com.tcmis.client.catalog.factory;


import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.catalog.beans.AppManagedUseApprovalBean;
import com.tcmis.client.catalog.beans.UseApprovalStatusInputBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;

/******************************************************************************
 * CLASSNAME: AppManagedUseApprovalBeanFactory <br>
 * @version: 1.0, Jul 18, 2006 <br>
 *****************************************************************************/


public class AppManagedUseApprovalBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_APPLICATION = "APPLICATION";
	public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
	public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
	public String ATTRIBUTE_PART_GROUP_NO = "PART_GROUP_NO";
	public String ATTRIBUTE_USER_GROUP_ID = "USER_GROUP_ID";
	public String ATTRIBUTE_LIMIT_QUANTITY_PERIOD1 = "LIMIT_QUANTITY_PERIOD1";
	public String ATTRIBUTE_DAYS_PERIOD1 = "DAYS_PERIOD1";
	public String ATTRIBUTE_LIMIT_QUANTITY_PERIOD2 = "LIMIT_QUANTITY_PERIOD2";
	public String ATTRIBUTE_DAYS_PERIOD2 = "DAYS_PERIOD2";
	public String ATTRIBUTE_ORDER_QUANTITY = "ORDER_QUANTITY";
	public String ATTRIBUTE_ORDER_QUANTITY_RULE = "ORDER_QUANTITY_RULE";
	public String ATTRIBUTE_ESTIMATE_ORDER_QUANTITY_PERIOD = "ESTIMATE_ORDER_QUANTITY_PERIOD";
	public String ATTRIBUTE_PROCESS_DESC = "PROCESS_DESC";
	public String ATTRIBUTE_APPROVAL_ID = "APPROVAL_ID";
	public String ATTRIBUTE_APPROVAL_DATE = "APPROVAL_DATE";

	//table name
	public String TABLE = "APP_MANAGED_USE_APPROVAL";


	//constructor
	public AppManagedUseApprovalBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	@Override
	public String getColumnName(String attributeName) {
		if(attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if(attributeName.equals("application")) {
			return ATTRIBUTE_APPLICATION;
		}
		else if(attributeName.equals("catalogId")) {
			return ATTRIBUTE_CATALOG_ID;
		}
		else if(attributeName.equals("catPartNo")) {
			return ATTRIBUTE_CAT_PART_NO;
		}
		else if(attributeName.equals("partGroupNo")) {
			return ATTRIBUTE_PART_GROUP_NO;
		}
		else if(attributeName.equals("userGroupId")) {
			return ATTRIBUTE_USER_GROUP_ID;
		}
		else if(attributeName.equals("limitQuantityPeriod1")) {
			return ATTRIBUTE_LIMIT_QUANTITY_PERIOD1;
		}
		else if(attributeName.equals("daysPeriod1")) {
			return ATTRIBUTE_DAYS_PERIOD1;
		}
		else if(attributeName.equals("limitQuantityPeriod2")) {
			return ATTRIBUTE_LIMIT_QUANTITY_PERIOD2;
		}
		else if(attributeName.equals("daysPeriod2")) {
			return ATTRIBUTE_DAYS_PERIOD2;
		}
		else if(attributeName.equals("orderQuantity")) {
			return ATTRIBUTE_ORDER_QUANTITY;
		}
		else if(attributeName.equals("orderQuantityRule")) {
			return ATTRIBUTE_ORDER_QUANTITY_RULE;
		}
		else if(attributeName.equals("estimateOrderQuantityPeriod")) {
			return ATTRIBUTE_ESTIMATE_ORDER_QUANTITY_PERIOD;
		}
		else if(attributeName.equals("processDesc")) {
			return ATTRIBUTE_PROCESS_DESC;
		}
		else if(attributeName.equals("approvalId")) {
			return ATTRIBUTE_APPROVAL_ID;
		}
		else if(attributeName.equals("approvalDate")) {
			return ATTRIBUTE_APPROVAL_DATE;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, AppManagedUseApprovalBean.class);
	}


	//call p_populate_managed_work_area
	public void populateManagedWorkArea(UseApprovalStatusInputBean bean,BigDecimal personnelId) throws
	BaseException {
		Connection connection = this.getDbManager().getConnection();
		Collection cin = new Vector();

		if (bean.getFacilityId() != null && bean.getFacilityId().trim().length() > 0 &&
				!"All".equalsIgnoreCase(bean.getFacilityId())) {
			cin.add(new String(bean.getFacilityId()));
		}
		else {
			cin.add("");
		}

		if (bean.getApplication() != null && bean.getApplication().length() > 0 &&
				!"All".equalsIgnoreCase(bean.getApplication())) {
			cin.add(new String(bean.getApplication()));
		}
		else {
			cin.add("");
		}

		if (bean.getUserGroupId() != null && bean.getUserGroupId().length() > 0) {
			cin.add(new String(bean.getUserGroupId()));
		}
		else {
			cin.add("");
		}

		if (bean.getSearchText() != null && bean.getSearchText().length() > 0) {
			cin.add(new String(bean.getSearchText()));
		}
		else {
			cin.add("");
		}

		cin.add(personnelId);

		if (bean.getUpdateAllRows() != null &&
				"AddAll".equalsIgnoreCase(bean.getUpdateAllRows())) {
			cin.add(new String("POPULATE"));
		}
		else if (bean.getUpdateAllRows() != null &&
				"deleteAll".equalsIgnoreCase(bean.getUpdateAllRows())) {
			cin.add(new String("REMOVE"));
		}
		else {
			cin.add("");
		}

		if (log.isDebugEnabled()) {
			log.debug("FacilistyId  " + bean.getFacilityId() + "");
			log.debug("Application  " + bean.getApplication() + "");
			log.debug("UserGroupId  " + bean.getUserGroupId() + "");
			log.debug("Search Text  " + bean.getSearchText() + "");
		}
		SqlManager sqlManager = new SqlManager();
		try {
			sqlManager.doProcedure(connection, "P_POPULATE_MANAGED_WORK_AREA",cin);
		}
		finally {
		}

		this.getDbManager().returnConnection(connection);
	}

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

		Collection appManagedUseApprovalBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
		getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			AppManagedUseApprovalBean appManagedUseApprovalBean = new AppManagedUseApprovalBean();
			load(dataSetRow, appManagedUseApprovalBean);
			appManagedUseApprovalBeanColl.add(appManagedUseApprovalBean);
		}

		return appManagedUseApprovalBeanColl;
	}
}