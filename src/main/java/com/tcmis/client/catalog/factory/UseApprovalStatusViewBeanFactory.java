package com.tcmis.client.catalog.factory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.catalog.beans.UseApprovalStatusInputBean;
import com.tcmis.client.catalog.beans.UseApprovalStatusViewBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
/******************************************************************************
 * CLASSNAME: UseApprovalStatusViewBeanFactory <br>
 * @version: 1.0, Feb 13, 2006 <br>
 *****************************************************************************/

public class UseApprovalStatusViewBeanFactory
extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_USER_GROUP_ID = "USER_GROUP_ID";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_FAC_PART_NO = "FAC_PART_NO";
	public String ATTRIBUTE_EXPIRE_DATE = "EXPIRE_DATE";
	public String ATTRIBUTE_APPLICATION = "APPLICATION";
	public String ATTRIBUTE_APPROVAL_ID = "APPROVAL_ID";
	public String ATTRIBUTE_APPROVAL_STATUS = "APPROVAL_STATUS";
	public String ATTRIBUTE_REVIEWED_DATE = "REVIEWED_DATE";
	public String ATTRIBUTE_APP_GROUP = "APP_GROUP";
	public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
	public String ATTRIBUTE_PART_GROUP_NO = "PART_GROUP_NO";
	public String ATTRIBUTE_LIMIT_QUANTITY_PERIOD1 = "LIMIT_QUANTITY_PERIOD1";
	public String ATTRIBUTE_DAYS_PERIOD1 = "DAYS_PERIOD1";
	public String ATTRIBUTE_LIMIT_QUANTITY_PERIOD2 = "LIMIT_QUANTITY_PERIOD2";
	public String ATTRIBUTE_DAYS_PERIOD2 = "DAYS_PERIOD2";
	public String ATTRIBUTE_HAAS_SHIPTO_COMPANY_ID = "HAAS_SHIPTO_COMPANY_ID";
	public String ATTRIBUTE_DOCK_LOCATION_ID = "DOCK_LOCATION_ID";
	public String ATTRIBUTE_DOCK_DELIVERY_POINT = "DOCK_DELIVERY_POINT";
	public String ATTRIBUTE_DELIVERY_POINT_BARCODE = "DELIVERY_POINT_BARCODE";
	public String ATTRIBUTE_CUSTOMER_DELIVER_TO = "CUSTOMER_DELIVER_TO";
	public String ATTRIBUTE_BARCODE_REQUESTER = "BARCODE_REQUESTER";
	public String ATTRIBUTE_BARCODE_REQUESTER_NAME = "BARCODE_REQUESTER_NAME";
	public String ATTRIBUTE_PROCESS_DESC = "PROCESS_DESC";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_PART_DESCRIPTION = "PART_DESCRIPTION";
	public String ATTRIBUTE_PACKAGING = "PACKAGING";
	public String ATTRIBUTE_MW_LIMIT_QUANTITY_PERIOD1 = "MW_LIMIT_QUANTITY_PERIOD1";
	public String ATTRIBUTE_MW_DAYS_PERIOD1 = "MW_DAYS_PERIOD1";
	public String ATTRIBUTE_MW_LIMIT_QUANTITY_PERIOD2 = "MW_LIMIT_QUANTITY_PERIOD2";
	public String ATTRIBUTE_MW_DAYS_PERIOD2 = "MW_DAYS_PERIOD2";
	public String ATTRIBUTE_MW_ORDER_QUANTITY = "MW_ORDER_QUANTITY";
	public String ATTRIBUTE_MW_ORDER_QUANTITY_RULE = "MW_ORDER_QUANTITY_RULE";
	public String ATTRIBUTE_MW_ESTIMATE_ORDER_QUANTITY_PRD = "MW_ESTIMATE_ORDER_QUANTITY_PRD";
	public String ATTRIBUTE_MW_PROCESS_DESC = "MW_PROCESS_DESC";
	public String ATTRIBUTE_MW_APPROVAL_ID = "MW_APPROVAL_ID";
	public String ATTRIBUTE_MW_APPROVAL_DATE = "MW_APPROVAL_DATE";
	public String ATTRIBUTE_CATALOG_COMPANY_ID = "CATALOG_COMPANY_ID";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";

	//table name
	public String TABLE = "USE_APPROVAL_STATUS_VIEW";


	//constructor
	public UseApprovalStatusViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	@Override
	public String getColumnName(String attributeName) {
		if(attributeName.equals("userGroupId")) {
			return ATTRIBUTE_USER_GROUP_ID;
		}
		else if(attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if(attributeName.equals("facPartNo")) {
			return ATTRIBUTE_FAC_PART_NO;
		}
		else if(attributeName.equals("expireDate")) {
			return ATTRIBUTE_EXPIRE_DATE;
		}
		else if(attributeName.equals("application")) {
			return ATTRIBUTE_APPLICATION;
		}
		else if(attributeName.equals("approvalId")) {
			return ATTRIBUTE_APPROVAL_ID;
		}
		else if(attributeName.equals("approvalStatus")) {
			return ATTRIBUTE_APPROVAL_STATUS;
		}
		else if(attributeName.equals("reviewedDate")) {
			return ATTRIBUTE_REVIEWED_DATE;
		}
		else if(attributeName.equals("appGroup")) {
			return ATTRIBUTE_APP_GROUP;
		}
		else if(attributeName.equals("catalogId")) {
			return ATTRIBUTE_CATALOG_ID;
		}
		else if(attributeName.equals("partGroupNo")) {
			return ATTRIBUTE_PART_GROUP_NO;
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
		else if(attributeName.equals("haasShiptoCompanyId")) {
			return ATTRIBUTE_HAAS_SHIPTO_COMPANY_ID;
		}
		else if(attributeName.equals("dockLocationId")) {
			return ATTRIBUTE_DOCK_LOCATION_ID;
		}
		else if(attributeName.equals("dockDeliveryPoint")) {
			return ATTRIBUTE_DOCK_DELIVERY_POINT;
		}
		else if(attributeName.equals("deliveryPointBarcode")) {
			return ATTRIBUTE_DELIVERY_POINT_BARCODE;
		}
		else if(attributeName.equals("customerDeliverTo")) {
			return ATTRIBUTE_CUSTOMER_DELIVER_TO;
		}
		else if(attributeName.equals("barcodeRequester")) {
			return ATTRIBUTE_BARCODE_REQUESTER;
		}
		else if(attributeName.equals("barcodeRequesterName")) {
			return ATTRIBUTE_BARCODE_REQUESTER_NAME;
		}
		else if(attributeName.equals("processDesc")) {
			return ATTRIBUTE_PROCESS_DESC;
		}
		else if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("partDescription")) {
			return ATTRIBUTE_PART_DESCRIPTION;
		}
		else if(attributeName.equals("packaging")) {
			return ATTRIBUTE_PACKAGING;
		}
		else if(attributeName.equals("mwLimitQuantityPeriod1")) {
			return ATTRIBUTE_MW_LIMIT_QUANTITY_PERIOD1;
		}
		else if(attributeName.equals("mwDaysPeriod1")) {
			return ATTRIBUTE_MW_DAYS_PERIOD1;
		}
		else if(attributeName.equals("mwLimitQuantityPeriod2")) {
			return ATTRIBUTE_MW_LIMIT_QUANTITY_PERIOD2;
		}
		else if(attributeName.equals("mwDaysPeriod2")) {
			return ATTRIBUTE_MW_DAYS_PERIOD2;
		}
		else if(attributeName.equals("mwOrderQuantity")) {
			return ATTRIBUTE_MW_ORDER_QUANTITY;
		}
		else if(attributeName.equals("mwOrderQuantityRule")) {
			return ATTRIBUTE_MW_ORDER_QUANTITY_RULE;
		}
		else if(attributeName.equals("mwEstimateOrderQuantityPrd")) {
			return ATTRIBUTE_MW_ESTIMATE_ORDER_QUANTITY_PRD;
		}
		else if(attributeName.equals("mwProcessDesc")) {
			return ATTRIBUTE_MW_PROCESS_DESC;
		}
		else if(attributeName.equals("mwApprovalId")) {
			return ATTRIBUTE_MW_APPROVAL_ID;
		}
		else if(attributeName.equals("mwApprovalDate")) {
			return ATTRIBUTE_MW_APPROVAL_DATE;
		}
		else if(attributeName.equals("catalogCompanyId")) {
			return ATTRIBUTE_CATALOG_COMPANY_ID;
		}
		else if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, UseApprovalStatusViewBean.class);
	}

	//you need to verify the primary key(s) before uncommenting this
	public int delete(SearchCriteria criteria) throws BaseException {

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

	public int delete(SearchCriteria criteria,
			Connection conn) throws BaseException {

		String sqlQuery = " delete from " + TABLE + " " + getWhereClause(criteria);

		return new SqlManager().update(conn, sqlQuery);
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

	public Collection select(SearchCriteria criteria,
			Connection conn) throws BaseException {
		Collection useApprovalStatusViewBeanColl = new Vector();
		String query = "select * from " + TABLE + " " + getWhereClause(criteria);
		DataSet dataSet = new SqlManager().select(conn, query);
		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			UseApprovalStatusViewBean useApprovalStatusViewBean = new
			UseApprovalStatusViewBean();
			load(dataSetRow, useApprovalStatusViewBean);
			useApprovalStatusViewBeanColl.add(useApprovalStatusViewBean);
		}

		return useApprovalStatusViewBeanColl;
	}

	public Collection select(SearchCriteria criteria,UseApprovalStatusInputBean bean,
			Connection conn) throws BaseException {
		Collection useApprovalStatusViewBeanColl = new Vector();
		String sortby = "order by " + bean.getSortBy();
		String query = "select * from " + TABLE + " " + getWhereClause(criteria) +" "+ sortby;
		DataSet dataSet = new SqlManager().select(conn, query);
		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			UseApprovalStatusViewBean useApprovalStatusViewBean = new
			UseApprovalStatusViewBean();
			load(dataSetRow, useApprovalStatusViewBean);
			useApprovalStatusViewBeanColl.add(useApprovalStatusViewBean);
		}

		return useApprovalStatusViewBeanColl;
	}

	//select from procedure
	public Collection select(UseApprovalStatusInputBean bean) throws BaseException {
		Connection connection = this.getDbManager().getConnection();
		try {
			connection.setAutoCommit(false);
		}
		catch (SQLException ex) {
		}

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

		if (bean.getShowApprovedOnly() != null &&
				"Y".equalsIgnoreCase(bean.getShowApprovedOnly())) {
			cin.add(new String(bean.getShowApprovedOnly()));
		}
		else {
			cin.add("");
		}

		if (log.isDebugEnabled()) {
			log.debug("FacilistyId  " + bean.getFacilityId() + "");
			log.debug("Application  " + bean.getApplication() + "");
			log.debug("UserGroupId  " + bean.getUserGroupId() + "");
			log.debug("Search Text  " + bean.getSearchText() + "");
			log.debug("ShowApprovedOnly  " + bean.getShowApprovedOnly() + "");
			log.debug("ShowOnlyWithLimits  " + bean.getShowOnlyWithLimits() + "");
		}

		Collection cout = new Vector();
		Collection result = null;
		SqlManager sqlManager = new SqlManager();
		try {
			result = sqlManager.doProcedure(connection, "P_USE_APPROVAL_STATUS_QUERY",
					cin, cout);
		}
		finally {
		}

		SearchCriteria criteria = new SearchCriteria();
		if (bean.getShowActiveOnly() != null && bean.getShowActiveOnly().equalsIgnoreCase("Y")) {
			criteria.addCriterion("mwApprovalId", SearchCriterion.IS_NOT,null);
		}

		if (bean.getShowOnlyWithLimits() != null && bean.getShowOnlyWithLimits().equalsIgnoreCase("Y")) {
			criteria.addCriterion("mwLimitQuantityPeriod1", SearchCriterion.IS_NOT,null);
		}

		Collection c = select(criteria, bean, connection);
		try {
			connection.setAutoCommit(true);
		}
		catch (SQLException ex) {
		}

		this.getDbManager().returnConnection(connection);
		return c;
	}

	//call p_app_managed_use_approval
	public void updateUseApproval(UseApprovalStatusViewBean bean,
			BigDecimal personnelId) throws Exception {
		Connection connection = this.getDbManager().getConnection();
		Collection cin = new Vector();

		if (bean.getFacilityId() != null && bean.getFacilityId().length() > 0) {
			cin.add(bean.getFacilityId());
		}
		else {
			cin.add("");
		}

		if (bean.getApplication() != null && bean.getApplication().length() > 0) {
			cin.add(bean.getApplication());
		}
		else {
			cin.add("");
		}

		if (bean.getCatalogId() != null && bean.getCatalogId().length() > 0) {
			cin.add(bean.getCatalogId());
		}
		else {
			cin.add("");
		}

		if (bean.getFacPartNo() != null && bean.getFacPartNo().length() > 0) {
			cin.add(bean.getFacPartNo());
		}
		else {
			cin.add("");
		}

		if (bean.getPartGroupNo() != null) {
			cin.add(bean.getPartGroupNo());
		}
		else {
			cin.add("");
		}

		if (bean.getUserGroupId() != null && bean.getUserGroupId().length() > 0) {
			cin.add(bean.getUserGroupId());
		}
		else {
			cin.add("");
		}

		if (bean.getMwLimitQuantityPeriod1() != null) {
			cin.add(bean.getMwLimitQuantityPeriod1());
		}
		else {
			cin.add("");
		}

		if (bean.getMwDaysPeriod1() != null) {
			cin.add(bean.getMwDaysPeriod1());
		}
		else {
			cin.add("");
		}

		if (bean.getMwLimitQuantityPeriod2() != null) {
			cin.add(bean.getMwLimitQuantityPeriod2());
		}
		else {
			cin.add("");
		}

		if (bean.getMwDaysPeriod2() != null) {
			cin.add(bean.getMwDaysPeriod2());
		}
		else {
			cin.add("");
		}

		if (bean.getMwOrderQuantity() != null) {
			cin.add(bean.getMwOrderQuantity());
		}
		else {
			cin.add("");
		}

		if (bean.getMwOrderQuantity() != null && bean.getMwOrderQuantityRule() != null &&
				bean.getMwOrderQuantityRule().length() > 0) {
			cin.add(bean.getMwOrderQuantityRule());
		}
		else {
			cin.add("");
		}

		if (bean.getMwEstimateOrderQuantityPrd() != null) {
			cin.add(bean.getMwEstimateOrderQuantityPrd());
		}
		else {
			cin.add("");
		}

		if (bean.getMwProcessDesc() != null && bean.getMwProcessDesc().length() > 0) {
			cin.add(bean.getMwProcessDesc());
		}
		else {
			cin.add("");
		}

		cin.add(personnelId);
		//DELETE YES and NO
		if (bean.getOk().equalsIgnoreCase("false") && bean.getMwApprovalId() !=null)
		{
			cin.add("YES");
		}
		else
		{
			cin.add("NO");
		}

		if (bean.getCompanyId() != null && bean.getCompanyId().length() > 0) {
			cin.add(bean.getCompanyId());
		}else {
			cin.add("");
		}
		if (bean.getCatalogCompanyId() != null && bean.getCatalogCompanyId().length() > 0) {
			cin.add(bean.getCatalogCompanyId());
		}else {
			cin.add("");
		}
		
		if (bean.getUserGroupId() != null && bean.getUserGroupId().length() > 0) {
			cin.add(bean.getUserGroupId());
		}else {
			cin.add("");
		}

		/*log.debug("UserGroupId  " + bean.getUserGroupId() + "");
	log.debug("CatalogId  " + bean.getCatalogId() + "");
	log.debug("FacPartNo  " + bean.getFacPartNo() + "");
	log.debug("ExpireDate  " + bean.getExpireDate() + "");
	log.debug("Application  " + bean.getApplication() + "");
	log.debug("ApprovalId  " + bean.getApprovalId() + "");
	log.debug("ApprovalStatus  " + bean.getApprovalStatus() + "");
	log.debug("PartGroupNo  " + bean.getPartGroupNo() + "");
	log.debug("FacilityId  " + bean.getFacilityId() + "");
	log.debug("InventoryGroup  " + bean.getInventoryGroup() + "");
	log.debug("LimitQuantityPeriod1  " + bean.getMwLimitQuantityPeriod1() + "");
	log.debug("DaysPeriod1  " + bean.getMwDaysPeriod1() + "");
	log.debug("LimitQuantityPeriod2  " + bean.getMwLimitQuantityPeriod2() + "");
	log.debug("DaysPeriod2  " + bean.getMwDaysPeriod2() + "");
	log.debug("ProcessDesc  " + bean.getMwProcessDesc() + "");
	log.debug("OrderQuantity  " + bean.getMwOrderQuantity() + "");
	log.debug("OrderQuantityRule  " + bean.getMwOrderQuantityRule() + "");*/

		this.getDbManager().doProcedure("p_app_managed_use_approval", cin);
		this.getDbManager().returnConnection(connection);
	}

	//call  p_barcode_data
	public Collection updateAutomatedFeedInformation(UseApprovalStatusViewBean
			bean, BigDecimal personnelId) throws Exception {
		Connection connection = this.getDbManager().getConnection();
		Collection cin = new Vector();

		if (bean.getFacilityId() != null && bean.getFacilityId().length() > 0) {
			cin.add(bean.getFacilityId());
		}
		else {
			cin.add("");
		}

		if (bean.getApplication() != null && bean.getApplication().length() > 0) {
			cin.add(bean.getApplication());
		}
		else {
			cin.add("");
		}

		if (bean.getCatalogId() != null && bean.getCatalogId().length() > 0) {
			cin.add(bean.getCatalogId());
		}
		else {
			cin.add("");
		}

		if (bean.getFacPartNo() != null && bean.getFacPartNo().length() > 0) {
			cin.add(bean.getFacPartNo());
		}
		else {
			cin.add("");
		}

		if (bean.getPartGroupNo() != null) {
			cin.add(bean.getPartGroupNo());
		}
		else {
			cin.add("");
		}

		if (bean.getHaasShiptoCompanyId() != null &&
				bean.getHaasShiptoCompanyId().length() > 0) {
			cin.add(bean.getHaasShiptoCompanyId());
		}
		else {
			cin.add("");
		}

		if (bean.getDockLocationId() != null && bean.getDockLocationId().length() > 0) {
			cin.add(bean.getDockLocationId());
		}
		else {
			cin.add("");
		}

		if (bean.getDockDeliveryPoint() != null &&
				bean.getDockDeliveryPoint().length() > 0) {
			cin.add(bean.getDockDeliveryPoint());
		}
		else {
			cin.add("");
		}

		if (bean.getDeliveryPointBarcode() != null &&
				bean.getDeliveryPointBarcode().length() > 0) {
			cin.add(bean.getDeliveryPointBarcode());
		}
		else {
			cin.add("");
		}

		if (bean.getCustomerDeliverTo() != null &&
				bean.getCustomerDeliverTo().length() > 0) {
			cin.add(bean.getCustomerDeliverTo());
		}
		else {
			cin.add("");
		}

		if (bean.getBarcodeRequester() != null &&
				!bean.getBarcodeRequester().equals(new BigDecimal("0"))) {
			cin.add(bean.getBarcodeRequester());
		}
		else {
			cin.add("");
		}

		if (bean.getCompanyId() != null && bean.getCompanyId().length() > 0) {
			cin.add(bean.getCompanyId());
		}else {
			cin.add("");
		}
		if (bean.getCatalogCompanyId() != null && bean.getCatalogCompanyId().length() > 0) {
			cin.add(bean.getCatalogCompanyId());
		}else {
			cin.add("");
		}

		/*log.debug("FacilityId  " + bean.getFacilityId() + "");
	log.debug("Application  " + bean.getApplication() + "");
	log.debug("CatalogId  " + bean.getCatalogId() + "");
	log.debug("FacPartNo  " + bean.getFacPartNo() + "");
	log.debug("PartGroupNo  " + bean.getPartGroupNo() + "");
	log.debug("HaasShiptoCompanyId  " + bean.getHaasShiptoCompanyId() + "");
	log.debug("DockLocationId  " + bean.getDockLocationId() + "");
	log.debug("DockDeliveryPoint  " + bean.getDockDeliveryPoint() + "");
	log.debug("CustomerDeliverTo  " + bean.getCustomerDeliverTo() + "");
	log.debug("BarcodeRequester  " + bean.getBarcodeRequester() + "");*/

		Collection cout = new Vector();
		Collection result = this.getDbManager().doProcedure("p_barcode_data", cin,
				cout);

		this.getDbManager().returnConnection(connection);
		return result;
	}
}