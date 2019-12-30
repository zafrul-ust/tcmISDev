package com.tcmis.client.catalog.factory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.catalog.beans.FacLocAppBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: FacLocAppBeanFactory <br>
 * @version: 1.0, Jan 6, 2011 <br>
 *****************************************************************************/

public class FacLocAppBeanFactory extends BaseBeanFactory {

	public String ATTRIBUTE_ALLOCATE_BY_DISTANCE = "ALLOCATE_BY_DISTANCE";
	public String ATTRIBUTE_ALLOW_SPLIT_KITS = "ALLOW_SPLIT_KITS";
	public String ATTRIBUTE_ALLOW_STOCKING = "ALLOW_STOCKING";
	public String ATTRIBUTE_APPLICATION = "APPLICATION";
	public String ATTRIBUTE_APPLICATION_DESC = "APPLICATION_DESC";
	public String ATTRIBUTE_APPLICATION_ID = "APPLICATION_ID";
	public String ATTRIBUTE_AREA_DESC = "AREA_DESC";
	public String ATTRIBUTE_AREA_ID = "AREA_ID";
	public String ATTRIBUTE_AREA_NAME = "AREA_NAME";
	public String ATTRIBUTE_BUILDING_DESC = "BUILDING_DESC";
	public String ATTRIBUTE_BUILDING_ID = "BUILDING_ID";
	public String ATTRIBUTE_BUILDING_NAME = "BUILDING_NAME";
	public String ATTRIBUTE_CHARGE_TYPE_DEFAULT = "CHARGE_TYPE_DEFAULT";
	public String ATTRIBUTE_COMPANY_APPLICATION = "COMPANY_APPLICATION";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_COMPASS_POINT = "COMPASS_POINT";
	public String ATTRIBUTE_CONTACT_EMAIL = "CONTACT_EMAIL";
	public String ATTRIBUTE_CONTACT_NAME = "CONTACT_NAME";
	public String ATTRIBUTE_CONTACT_PHONE = "CONTACT_PHONE";
	public String ATTRIBUTE_CUSTOMER_CABINET_ID = "CUSTOMER_CABINET_ID";
	public String ATTRIBUTE_DAYS_BETWEEN_SCAN = "DAYS_BETWEEN_SCAN";
	public String ATTRIBUTE_DELIVERY_POINT = "DELIVERY_POINT";
	public String ATTRIBUTE_DELIVERY_POINT_DESC = "DELIVERY_POINT_DESC";
	public String ATTRIBUTE_DELIVERY_POINT_SELECTION_RULE = "DELIVERY_POINT_SELECTION_RULE";
	public String ATTRIBUTE_DEPT_ID = "DEPT_ID";
	public String ATTRIBUTE_DEPT_NAME = "DEPT_NAME";
	public String ATTRIBUTE_DOCK_DELIVER_TO_SELECTION_RULE = "DOCK_DELIVER_TO_SELECTION_RULE";
	public String ATTRIBUTE_DOCK_SELECTION_RULE = "DOCK_SELECTION_RULE";
	public String ATTRIBUTE_DROP_SHIP = "DROP_SHIP";
	public String ATTRIBUTE_EDIT_CHARGE_NUMBER = "EDIT_CHARGE_NUMBER";
	public String ATTRIBUTE_EMISSION_POINT = "EMISSION_POINT";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_FLOOR = "FLOOR";
	public String ATTRIBUTE_FLOOR_ID = "FLOOR_ID";
	public String ATTRIBUTE_HET_MULTIPLE_BUILDING_USAGE = "HET_MULTIPLE_BUILDING_USAGE";
	public String ATTRIBUTE_INCLUDE_EXPIRED_MATERIAL = "INCLUDE_EXPIRED_MATERIAL";
	public String ATTRIBUTE_INTERIOR = "INTERIOR";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_INVENTORY_GROUP_NAME = "INVENTORY_GROUP_NAME";
	public String ATTRIBUTE_LOC_COLUMN = "LOC_COLUMN";
	public String ATTRIBUTE_LOC_SECTION = "LOC_SECTION";
	public String ATTRIBUTE_LOCATION_DETAIL = "LOCATION_DETAIL";
	public String ATTRIBUTE_LOCATION_ID = "LOCATION_ID";
	public String ATTRIBUTE_MANAGED_USE_APPROVAL = "MANAGED_USE_APPROVAL";
	public String ATTRIBUTE_MANUAL_MR_CREATION = "MANUAL_MR_CREATION";
	public String ATTRIBUTE_OFFSITE = "OFFSITE";
	public String ATTRIBUTE_ORGANIZATION = "ORGANIZATION";
	public String ATTRIBUTE_OVERRIDE_USAGE_LOGGING = "OVERRIDE_USAGE_LOGGING";
	public String ATTRIBUTE_PROCESS_ID = "PROCESS_ID";
	public String ATTRIBUTE_PULL_WITHIN_DAYS_TO_EXPIRATION = "PULL_WITHIN_DAYS_TO_EXPIRATION";
	public String ATTRIBUTE_REPORT_USAGE = "REPORT_USAGE";
	public String ATTRIBUTE_REPORTING_ENTITY_ID = "REPORTING_ENTITY_ID";
	public String ATTRIBUTE_ROOM_DESC = "ROOM_DESC";
	public String ATTRIBUTE_ROOM_ID = "ROOM_ID";
	public String ATTRIBUTE_ROOM_NAME = "ROOM_NAME";
	public String ATTRIBUTE_STATUS = "STATUS";
	public String ATTRIBUTE_STOCKING_ACCOUNT_SYS_ID = "STOCKING_ACCOUNT_SYS_ID";
	public String ATTRIBUTE_UPDATED_BY = "UPDATED_BY";
	public String ATTRIBUTE_UPDATED_ON = "UPDATED_ON";
	public String ATTRIBUTE_USE_APPROVAL_LIMITS_OPTION = "USE_APPROVAL_LIMITS_OPTION";
	public String ATTRIBUTE_USE_CODE_DATA = "USE_CODE_DATA";
	public String ATTRIBUTE_USE_CODE_REQUIRED = "USE_CODE_REQUIRED";
	public String ATTRIBUTE_WORK_AREA_GROUP_DESC = "WORK_AREA_GROUP_DESC";
	public String ATTRIBUTE_CONTACT2_NAME = "CONTACT2_NAME";
	public String ATTRIBUTE_CONTACT2_PHONE = "CONTACT2_PHONE";
	public String ATTRIBUTE_CONTACT2_EMAIL = "CONTACT2_EMAIL";
	public String ATTRIBUTE_SPECIFIC_USE_APPROVAL_REQUIRED = "SPECIFIC_USE_APPROVAL_REQUIRED";
	public String ATTRIBUTE_FLAMMABILITY_CONTROL_ZONE_ID = "FLAMMABILITY_CONTROL_ZONE_ID";
	public String ATTRIBUTE_FLAMMABILITY_CONTROL_ZONE_DESC = "FLAMMABILITY_CONTROL_ZONE_DESC";	
	public String ATTRIBUTE_VOC_ZONE_ID = "VOC_ZONE_ID";
	public String ATTRIBUTE_VOC_ZONE_DESCRIPTION = "VOC_ZONE_DESCRIPTION";
	public String ATTRIBUTE_REPORT_INVENTORY = "REPORT_INVENTORY";


	private boolean creatingWhereClause = false;

	Log log = LogFactory.getLog(this.getClass());
	//table name
	public String TABLE = "FAC_LOC_APP";


	//constructor
	public FacLocAppBeanFactory(DbManager dbManager) {
		super(dbManager);
	}

	//delete
	public int delete(FacLocAppBean facLocAppBean) throws BaseException {
		Connection connection = null;
		int result = 0;
		try {
			connection = this.getDbManager().getConnection();
			result = this.delete(facLocAppBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}

	public int delete(FacLocAppBean facLocAppBean, Connection conn) throws BaseException {

		return delete(getKeyCriteria(facLocAppBean), conn);
	}

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

	public int delete(SearchCriteria criteria, Connection conn) throws BaseException {

		String sqlQuery = " delete from " + TABLE + " " + getWhereClause(criteria);

		return new SqlManager().update(conn, sqlQuery);
	}

	//get column names
	public String getActualColumnName(String attributeName) {
		if (attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if (attributeName.equals("application")) {
			return ATTRIBUTE_APPLICATION;
		}
		else if (attributeName.equals("locationId")) {
			return ATTRIBUTE_LOCATION_ID;
		}
		else if (attributeName.equals("applicationDesc")) {
			return ATTRIBUTE_APPLICATION_DESC;
		}
		else if (attributeName.equals("compassPoint")) {
			return ATTRIBUTE_COMPASS_POINT;
		}
		else if (attributeName.equals("deliveryPoint")) {
			return ATTRIBUTE_DELIVERY_POINT;
		}
		else if (attributeName.equals("deliveryPointDesc")) {
			return ATTRIBUTE_DELIVERY_POINT_DESC;
		}
		else if (attributeName.equals("status")) {
			return ATTRIBUTE_STATUS;
		}
		else if (attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if (attributeName.equals("organization")) {
			return ATTRIBUTE_ORGANIZATION;
		}
		else if (attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if (attributeName.equals("inventoryGroupName")) {
			return ATTRIBUTE_INVENTORY_GROUP_NAME;
		}
		else if (attributeName.equals("interior")) {
			return ATTRIBUTE_INTERIOR;
		}
		else if (attributeName.equals("useApprovalLimitsOption")) {
			return ATTRIBUTE_USE_APPROVAL_LIMITS_OPTION;
		}
		else if (attributeName.equals("areaId")) {
			return ATTRIBUTE_AREA_ID;
		}
		else if (attributeName.equals("deptId")) {
			return ATTRIBUTE_DEPT_ID;
		}
		else if (attributeName.equals("deptName")) {
			return ATTRIBUTE_DEPT_NAME;
		}
		else if (attributeName.equals("processId")) {
			return ATTRIBUTE_PROCESS_ID;
		}
		else if (attributeName.equals("managedUseApproval")) {
			return ATTRIBUTE_MANAGED_USE_APPROVAL;
		}
		else if (attributeName.equals("companyApplication")) {
			return ATTRIBUTE_COMPANY_APPLICATION;
		}
		else if (attributeName.equals("reportingEntityId")) {
			return ATTRIBUTE_REPORTING_ENTITY_ID;
		}
		else if (attributeName.equals("dropShip")) {
			return ATTRIBUTE_DROP_SHIP;
		}
		else if (attributeName.equals("offsite")) {
			return ATTRIBUTE_OFFSITE;
		}
		else if (attributeName.equals("dockDeliverToSelectionRule")) {
			return ATTRIBUTE_DOCK_DELIVER_TO_SELECTION_RULE;
		}
		else if (attributeName.equals("dockSelectionRule")) {
			return ATTRIBUTE_DOCK_SELECTION_RULE;
		}
		else if (attributeName.equals("deliveryPointSelectionRule")) {
			return ATTRIBUTE_DELIVERY_POINT_SELECTION_RULE;
		}
		else if (attributeName.equals("allocateByDistance")) {
			return ATTRIBUTE_ALLOCATE_BY_DISTANCE;
		}
		else if (attributeName.equals("editChargeNumber")) {
			return ATTRIBUTE_EDIT_CHARGE_NUMBER;
		}
		else if (attributeName.equals("contactName")) {
			return ATTRIBUTE_CONTACT_NAME;
		}
		else if (attributeName.equals("contactPhone")) {
			return ATTRIBUTE_CONTACT_PHONE;
		}
		else if (attributeName.equals("contactEmail")) {
			return ATTRIBUTE_CONTACT_EMAIL;
		}
		else if (attributeName.equals("buildingId")) {
			return ATTRIBUTE_BUILDING_ID;
		}
		else if (attributeName.equals("roomId")) {
			return ATTRIBUTE_ROOM_ID;
		}
		else if (attributeName.equals("locationDetail")) {
			return ATTRIBUTE_LOCATION_DETAIL;
		}
		else if (attributeName.equals("customerCabinetId")) {
			return ATTRIBUTE_CUSTOMER_CABINET_ID;
		}
		else if (attributeName.equals("manualMrCreation")) {
			return ATTRIBUTE_MANUAL_MR_CREATION;
		}
		else if (attributeName.equals("allowStocking")) {
			return ATTRIBUTE_ALLOW_STOCKING;
		}
		else if (attributeName.equals("applicationId")) {
			return ATTRIBUTE_APPLICATION_ID;
		}
		else if (attributeName.equals("workAreaGroupDesc")) {
			return ATTRIBUTE_WORK_AREA_GROUP_DESC;
		}
		else if (attributeName.equals("buildingDesc")) {
			return ATTRIBUTE_BUILDING_DESC;
		}
		else if (attributeName.equals("buildingName")) {
			return ATTRIBUTE_BUILDING_NAME;
		}
		else if (attributeName.equals("roomDesc")) {
			return ATTRIBUTE_ROOM_DESC;
		}
		else if (attributeName.equals("roomName")) {
			return ATTRIBUTE_ROOM_NAME;
		}
		else if (attributeName.equals("stockingAccountSysId")) {
			return ATTRIBUTE_STOCKING_ACCOUNT_SYS_ID;
		}
		else if (attributeName.equals("pullWithinDaysToExpiration")) {
			return ATTRIBUTE_PULL_WITHIN_DAYS_TO_EXPIRATION;
		}
		else if (attributeName.equals("daysBetweenScan")) {
			return ATTRIBUTE_DAYS_BETWEEN_SCAN;
		}
		else if (attributeName.equals("areaDescription")) {
			return ATTRIBUTE_AREA_DESC;
		}
		else if (attributeName.equals("areaName")) {
			return ATTRIBUTE_AREA_NAME;
		}
		else if (attributeName.equals("allowSplitKits")) {
			return ATTRIBUTE_ALLOW_SPLIT_KITS;
		}
		else if (attributeName.equals("useCodeRequired")) {
			return ATTRIBUTE_USE_CODE_REQUIRED;
		}
		else if (attributeName.equals("useCodeData")) {
			return ATTRIBUTE_USE_CODE_DATA ;
		}
		else if (attributeName.equals("overrideUsageLogging")) {
			return ATTRIBUTE_OVERRIDE_USAGE_LOGGING;
		}
		else if (attributeName.equals("floor")) {
			return ATTRIBUTE_FLOOR;
		}
		else if (attributeName.equals("floorId")) {
			return ATTRIBUTE_FLOOR_ID;
		}
		else if (attributeName.equals("compassPoint")) {
			return ATTRIBUTE_COMPASS_POINT;
		}
		else if (attributeName.equals("locColumn")) {
			return ATTRIBUTE_LOC_COLUMN;
		}
		else if (attributeName.equals("locSection")) {
			return ATTRIBUTE_LOC_SECTION;
		}
		else if (attributeName.equals("updatedBy")) {
			return ATTRIBUTE_UPDATED_BY;
		}
		else if (attributeName.equals("updatedOn")) {
			return ATTRIBUTE_UPDATED_ON;
		}
		else if (attributeName.equals("chargeTypeDefault")) {
			return ATTRIBUTE_CHARGE_TYPE_DEFAULT;
		}
		else if (attributeName.equals("includeExpiredMaterial")) {
			return ATTRIBUTE_INCLUDE_EXPIRED_MATERIAL;
		}
		else if (attributeName.equals("emissionPoint")) {
			return ATTRIBUTE_EMISSION_POINT;
		}
		else if (attributeName.equals("reportUsage")) {
			return ATTRIBUTE_REPORT_USAGE;
		}
		else if (attributeName.equals("contact2Name")) {
			return ATTRIBUTE_CONTACT2_NAME;
		}
		else if (attributeName.equals("contact2Phone")) {
			return ATTRIBUTE_CONTACT2_PHONE;
		}
		else if (attributeName.equals("contact2Email")) {
			return ATTRIBUTE_CONTACT2_EMAIL;
		}
		else if (attributeName.equals("specificUseApprovalRequired")) {
			return ATTRIBUTE_SPECIFIC_USE_APPROVAL_REQUIRED;
		}
		else if (attributeName.equals("hetMultipleBuildingUsage")) {
			return ATTRIBUTE_HET_MULTIPLE_BUILDING_USAGE;
		}
		else if (attributeName.equals("flammabilityControlZoneId")) {
			return ATTRIBUTE_FLAMMABILITY_CONTROL_ZONE_ID;
		}
		else if (attributeName.equals("flammabilityControlZoneDesc")) {
			return ATTRIBUTE_FLAMMABILITY_CONTROL_ZONE_DESC;
		}
		else if (attributeName.equals("vocZoneId")) {
			return ATTRIBUTE_VOC_ZONE_ID;
		}
		else if (attributeName.equals("vocZoneDescription")) {
			return ATTRIBUTE_VOC_ZONE_DESCRIPTION;
		}
		else if (attributeName.equals("reportInventory")) {
			return ATTRIBUTE_REPORT_INVENTORY;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}

	@Override
	public String getColumnName(String attributeName) {
		return creatingWhereClause ? getActualColumnName(attributeName) : getActualColumnName(attributeName);
	}

	public SearchCriteria getKeyCriteria(FacLocAppBean facLocAppBean) {
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("facilityId", SearchCriterion.EQUALS, "" + facLocAppBean.getFacilityId());
		criteria.addCriterion("application", SearchCriterion.EQUALS, "" + facLocAppBean.getApplication());
		criteria.addCriterion("companyId", SearchCriterion.EQUALS, "" + facLocAppBean.getCompanyId());
		return criteria;
	}

	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, FacLocAppBean.class);
	}

	@Override
	public String getWhereClause(SearchCriteria criteria) {
		try {
			creatingWhereClause = true;
			return super.getWhereClause(criteria);
		}
		finally {
			creatingWhereClause = false;
		}
	}

	//insert
	public int insert(FacLocAppBean facLocAppBean) throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(facLocAppBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}

	public int insert(FacLocAppBean facLocAppBean, Connection conn) throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query = "insert into " + TABLE + " (";
		query += ATTRIBUTE_FACILITY_ID + ",";
		query += ATTRIBUTE_APPLICATION + ",";
		query += ATTRIBUTE_LOCATION_ID + ",";
		query += ATTRIBUTE_APPLICATION_DESC + ",";
		query += ATTRIBUTE_DELIVERY_POINT + ",";
		query += ATTRIBUTE_STATUS + ",";
		query += ATTRIBUTE_COMPANY_ID + ",";
		query += ATTRIBUTE_ORGANIZATION + ",";
		query += ATTRIBUTE_INVENTORY_GROUP + ",";
		query += ATTRIBUTE_USE_APPROVAL_LIMITS_OPTION + ",";
		query += ATTRIBUTE_DEPT_ID + ",";
		query += ATTRIBUTE_PROCESS_ID + ",";
		query += ATTRIBUTE_MANAGED_USE_APPROVAL + ",";
		query += ATTRIBUTE_COMPANY_APPLICATION + ",";
		query += ATTRIBUTE_REPORTING_ENTITY_ID + ",";
		query += ATTRIBUTE_DROP_SHIP + ",";
		query += ATTRIBUTE_OFFSITE + ",";
		query += ATTRIBUTE_DOCK_DELIVER_TO_SELECTION_RULE + ",";
		query += ATTRIBUTE_DOCK_SELECTION_RULE + ",";
		query += ATTRIBUTE_DELIVERY_POINT_SELECTION_RULE + ",";
		query += ATTRIBUTE_ALLOCATE_BY_DISTANCE + ",";
		query += ATTRIBUTE_EDIT_CHARGE_NUMBER + ",";
		query += ATTRIBUTE_CONTACT_NAME + ",";
		query += ATTRIBUTE_CONTACT_PHONE + ",";
		query += ATTRIBUTE_CONTACT_EMAIL + ",";
		query += ATTRIBUTE_ROOM_ID + ",";
		query += ATTRIBUTE_LOCATION_DETAIL + ",";
		query += ATTRIBUTE_CUSTOMER_CABINET_ID + ",";
		query += ATTRIBUTE_MANUAL_MR_CREATION + ",";
		query += ATTRIBUTE_ALLOW_STOCKING + ",";
		query += ATTRIBUTE_STOCKING_ACCOUNT_SYS_ID + ",";
		query += ATTRIBUTE_PULL_WITHIN_DAYS_TO_EXPIRATION + ",";
		query += ATTRIBUTE_DAYS_BETWEEN_SCAN + ",";
		query += ATTRIBUTE_ALLOW_SPLIT_KITS + ",";
		query += ATTRIBUTE_OVERRIDE_USAGE_LOGGING + ",";
		query += ATTRIBUTE_COMPASS_POINT + ",";
		query += ATTRIBUTE_LOC_COLUMN + ",";
		query += ATTRIBUTE_LOC_SECTION + ",";
		query += ATTRIBUTE_UPDATED_BY + ",";
		query += ATTRIBUTE_UPDATED_ON + ",";
		query += ATTRIBUTE_CHARGE_TYPE_DEFAULT + ",";
		query += ATTRIBUTE_INCLUDE_EXPIRED_MATERIAL + ",";
		query += ATTRIBUTE_EMISSION_POINT + ",";
		query += ATTRIBUTE_REPORT_USAGE + ",";
		query += ATTRIBUTE_APPLICATION_ID + ",";
		query += ATTRIBUTE_CONTACT2_NAME + ",";
		query += ATTRIBUTE_CONTACT2_PHONE + ",";
		query += ATTRIBUTE_CONTACT2_EMAIL + ",";
		query += ATTRIBUTE_HET_MULTIPLE_BUILDING_USAGE + ",";
		query += ATTRIBUTE_SPECIFIC_USE_APPROVAL_REQUIRED + ",";
		query += ATTRIBUTE_FLAMMABILITY_CONTROL_ZONE_ID + ",";
		query += ATTRIBUTE_VOC_ZONE_ID  + ",";
		query += ATTRIBUTE_REPORT_INVENTORY + ")";
		query += " values (";
		query += SqlHandler.delimitString(facLocAppBean.getFacilityId()) + ",";
		query += SqlHandler.delimitString(facLocAppBean.getApplication()) + ",";
		query += SqlHandler.delimitString(facLocAppBean.getLocationId()) + ",";
		query += SqlHandler.delimitString(facLocAppBean.getApplicationDesc()) + ",";
		query += SqlHandler.delimitString(facLocAppBean.getDeliveryPoint()) + ",";
		query += SqlHandler.delimitString(facLocAppBean.getStatus()) + ",";
		query += SqlHandler.delimitString(facLocAppBean.getCompanyId()) + ",";
		query += SqlHandler.delimitString(facLocAppBean.getOrganization()) + ",";
		query += SqlHandler.delimitString(facLocAppBean.getInventoryGroup()) + ",";
		query += SqlHandler.delimitString(facLocAppBean.getUseApprovalLimitsOption()) + ",";
		query += SqlHandler.delimitString(facLocAppBean.getDeptId()) + ",";
		query += SqlHandler.delimitString(facLocAppBean.getProcessId()) + ",";
		query += SqlHandler.delimitString(facLocAppBean.getManagedUseApproval()) + ",";
		query += SqlHandler.delimitString(facLocAppBean.getCompanyApplication()) + ",";
		query += SqlHandler.delimitString(facLocAppBean.getReportingEntityId()) + ",";
		query += SqlHandler.delimitString(facLocAppBean.isDropShip() ? "Y" : "N") + ",";
		query += SqlHandler.delimitString(facLocAppBean.isOffsite() ? "Y" : "N") + ",";
		query += SqlHandler.delimitString(facLocAppBean.getDockDeliverToSelectionRule()) + ",";
		query += SqlHandler.delimitString(facLocAppBean.getDockSelectionRule()) + ",";
		query += SqlHandler.delimitString(facLocAppBean.getDeliveryPointSelectionRule()) + ",";
		query += SqlHandler.delimitString(facLocAppBean.getAllocateByDistance()) + ",";
		query += SqlHandler.delimitString(facLocAppBean.getEditChargeNumber()) + ",";
		query += SqlHandler.delimitString(facLocAppBean.getContactName()) + ",";
		query += SqlHandler.delimitString(facLocAppBean.getContactPhone()) + ",";
		query += SqlHandler.delimitString(facLocAppBean.getContactEmail()) + ",";
		query += facLocAppBean.getRoomId()+ ",";
		query += SqlHandler.delimitString(facLocAppBean.getLocationDetail()) + ",";
		query += SqlHandler.delimitString(facLocAppBean.getCustomerCabinetId()) + ",";
		query += SqlHandler.delimitString(facLocAppBean.getManualMrCreation()) + ",";
		query += SqlHandler.delimitString(facLocAppBean.getAllowStocking()) + ",";
		query += SqlHandler.delimitString(facLocAppBean.getStockingAccountSysId()) + ",";
		query += facLocAppBean.getPullWithinDaysToExpiration() + ",";
		query += facLocAppBean.getDaysBetweenScan() + ",";
		query += (facLocAppBean.isAllowSplitKits() ? "'Y'" : "'N'") + ",";
		query += (facLocAppBean.isOverrideUsageLogging() ? "'Y'" : "'N'") + ",";
		query += SqlHandler.delimitString(facLocAppBean.getCompassPoint()) + ",";
		query += SqlHandler.delimitString(facLocAppBean.getLocColumn()) + ",";
		query += SqlHandler.delimitString(facLocAppBean.getLocSection()) + ",";
		query += facLocAppBean.getUpdatedBy() + ",";
		query += "SYSDATE,";
		query += SqlHandler.delimitString(facLocAppBean.getChargeTypeDefault()) + ",";
		query += SqlHandler.delimitString(facLocAppBean.isIncludeExpiredMaterial() ? "Y" : "N") + ",";
		query += SqlHandler.delimitString(facLocAppBean.getEmissionPoint()) + ",";
		query += SqlHandler.delimitString(facLocAppBean.isReportUsage() ? "Y" : "N") + ",";
		query += facLocAppBean.getApplicationId() + ",";
		query += SqlHandler.delimitString(facLocAppBean.getContact2Name()) + ",";
		query += SqlHandler.delimitString(facLocAppBean.getContact2Phone()) + ",";
		query += SqlHandler.delimitString(facLocAppBean.getContact2Email()) + ",";
		query += SqlHandler.delimitString(facLocAppBean.isHetMultipleBuildingUsage() ? "Y" : "N") + ",";
		query += SqlHandler.delimitString(facLocAppBean.isSpecificUseApprovalRequired() ? "Y" : "N") + ",";
		query += facLocAppBean.getFlammabilityControlZoneId() + ",";
		query += facLocAppBean.getVocZoneId() + ",";
		query += SqlHandler.delimitString(facLocAppBean.isReportInventory() ? "Y" : "N");
		query += ")";
		return sqlManager.update(conn, query);
	}

	//select
	public Collection<FacLocAppBean> select(SearchCriteria criteria, SortCriteria sortCriteria) throws BaseException {

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

	public Collection<FacLocAppBean> select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn) throws BaseException {

		Collection<FacLocAppBean> facLocAppBeanColl = new Vector<FacLocAppBean>();

		String query = "select * from fla_dp_re_rm_bldg_area_view ";
		query += getWhereClause(criteria);
		query += getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			FacLocAppBean facLocAppBean = new FacLocAppBean();
			load(dataSetRow, facLocAppBean);
			facLocAppBeanColl.add(facLocAppBean);
		}

		return facLocAppBeanColl;
	}

	public FacLocAppBean selectBean(SearchCriteria criteria) throws BaseException {
		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			return selectBean(criteria, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}

	public FacLocAppBean selectBean(SearchCriteria criteria,  Connection conn) throws BaseException {
		Collection<FacLocAppBean> results = select(criteria, null, conn);
		return results.isEmpty() ? null : results.iterator().next();
	}

	//update
	public int update(FacLocAppBean facLocAppBean) throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(facLocAppBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}

	public int update(FacLocAppBean facLocAppBean, Connection conn) throws BaseException {

		String query = "update " + TABLE + " set ";
		query += ATTRIBUTE_LOCATION_ID + "=" + SqlHandler.delimitString(facLocAppBean.getLocationId()) + ",";
		query += ATTRIBUTE_APPLICATION_DESC + "=" + SqlHandler.delimitString(facLocAppBean.getApplicationDesc()) + ",";
		query += ATTRIBUTE_DELIVERY_POINT + "=" + SqlHandler.delimitString(facLocAppBean.getDeliveryPoint()) + ",";
		query += ATTRIBUTE_STATUS + "=" + SqlHandler.delimitString(facLocAppBean.getStatus()) + ",";
		query += ATTRIBUTE_ORGANIZATION + "=" + SqlHandler.delimitString(facLocAppBean.getOrganization()) + ",";
		query += ATTRIBUTE_INVENTORY_GROUP + "=" + SqlHandler.delimitString(facLocAppBean.getInventoryGroup()) + ",";
		query += ATTRIBUTE_USE_APPROVAL_LIMITS_OPTION + "=" + SqlHandler.delimitString(facLocAppBean.getUseApprovalLimitsOption()) + ",";
		query += ATTRIBUTE_DEPT_ID + "=" + SqlHandler.delimitString(facLocAppBean.getDeptId()) + ",";
		query += ATTRIBUTE_PROCESS_ID + "=" + SqlHandler.delimitString(facLocAppBean.getProcessId()) + ",";
		query += ATTRIBUTE_MANAGED_USE_APPROVAL + "=" + SqlHandler.delimitString(facLocAppBean.getManagedUseApproval()) + ",";
		query += ATTRIBUTE_COMPANY_APPLICATION + "=" + SqlHandler.delimitString(facLocAppBean.getCompanyApplication()) + ",";
		query += ATTRIBUTE_REPORTING_ENTITY_ID + "=" + SqlHandler.delimitString(facLocAppBean.getReportingEntityId()) + ",";
		query += ATTRIBUTE_DROP_SHIP + "=" + SqlHandler.delimitString(facLocAppBean.isDropShip() ? "Y" : "N") + ",";
		query += ATTRIBUTE_OFFSITE + "=" + SqlHandler.delimitString(facLocAppBean.isOffsite() ? "Y" : "N") + ",";
		query += ATTRIBUTE_DOCK_DELIVER_TO_SELECTION_RULE + "=" + SqlHandler.delimitString(facLocAppBean.getDockDeliverToSelectionRule()) + ",";
		query += ATTRIBUTE_DOCK_SELECTION_RULE + "=" + SqlHandler.delimitString(facLocAppBean.getDockSelectionRule()) + ",";
		query += ATTRIBUTE_DELIVERY_POINT_SELECTION_RULE + "=" + SqlHandler.delimitString(facLocAppBean.getDeliveryPointSelectionRule()) + ",";
		query += ATTRIBUTE_ALLOCATE_BY_DISTANCE + "=" + SqlHandler.delimitString(facLocAppBean.getAllocateByDistance()) + ",";
		query += ATTRIBUTE_EDIT_CHARGE_NUMBER + "=" + SqlHandler.delimitString(facLocAppBean.getEditChargeNumber()) + ",";
		query += ATTRIBUTE_CONTACT_NAME + "=" + SqlHandler.delimitString(facLocAppBean.getContactName()) + ",";
		query += ATTRIBUTE_CONTACT_PHONE + "=" + SqlHandler.delimitString(facLocAppBean.getContactPhone()) + ",";
		query += ATTRIBUTE_CONTACT_EMAIL + "=" + SqlHandler.delimitString(facLocAppBean.getContactEmail()) + ",";
		query += ATTRIBUTE_ROOM_ID + "=" + facLocAppBean.getRoomId() + ",";
		query += ATTRIBUTE_LOCATION_DETAIL + "=" + SqlHandler.delimitString(facLocAppBean.getLocationDetail()) + ",";
		query += ATTRIBUTE_CUSTOMER_CABINET_ID + "=" + SqlHandler.delimitString(facLocAppBean.getCustomerCabinetId()) + ",";
		query += ATTRIBUTE_MANUAL_MR_CREATION + "=" + SqlHandler.delimitString(facLocAppBean.getManualMrCreation()) + ",";
		query += ATTRIBUTE_ALLOW_STOCKING + "=" + SqlHandler.delimitString(facLocAppBean.getAllowStocking()) + ",";
		query += ATTRIBUTE_STOCKING_ACCOUNT_SYS_ID + "=" + SqlHandler.delimitString(facLocAppBean.getStockingAccountSysId()) + ",";
		query += ATTRIBUTE_PULL_WITHIN_DAYS_TO_EXPIRATION+ "=" + facLocAppBean.getPullWithinDaysToExpiration() + ",";
		query += ATTRIBUTE_DAYS_BETWEEN_SCAN  + "=" + facLocAppBean.getDaysBetweenScan() + ",";
		query += ATTRIBUTE_ALLOW_SPLIT_KITS + "=" + (facLocAppBean.isAllowSplitKits() ? "'Y'" : "'N'") + ",";
		query += ATTRIBUTE_OVERRIDE_USAGE_LOGGING + "=" + (facLocAppBean.isOverrideUsageLogging() ? "'Y'" : "'N'") + ",";
		query += ATTRIBUTE_COMPASS_POINT + "=" + SqlHandler.delimitString(facLocAppBean.getCompassPoint()) + ",";
		query += ATTRIBUTE_LOC_COLUMN + "=" + SqlHandler.delimitString(facLocAppBean.getLocColumn()) + ",";
		query += ATTRIBUTE_LOC_SECTION + "=" + SqlHandler.delimitString(facLocAppBean.getLocSection()) + ",";
		query += ATTRIBUTE_APPLICATION_ID + "=" + StringHandler.nullIfZero(facLocAppBean.getApplicationId()) + ",";
		query += ATTRIBUTE_CHARGE_TYPE_DEFAULT + "=" + SqlHandler.delimitString(facLocAppBean.getChargeTypeDefault()) + ",";
		query += ATTRIBUTE_INCLUDE_EXPIRED_MATERIAL + "=" + (facLocAppBean.isIncludeExpiredMaterial() ? "'Y'" : "'N'") + ",";
		query += ATTRIBUTE_EMISSION_POINT + "=" + SqlHandler.delimitString(facLocAppBean.getEmissionPoint()) + ",";
		query += ATTRIBUTE_REPORT_USAGE + "=" + (facLocAppBean.isReportUsage() ? "'Y'" : "'N'") + ",";
		query += ATTRIBUTE_CONTACT2_NAME + "=" + SqlHandler.delimitString(facLocAppBean.getContact2Name()) + ",";
		query += ATTRIBUTE_CONTACT2_PHONE + "=" + SqlHandler.delimitString(facLocAppBean.getContact2Phone()) + ",";
		query += ATTRIBUTE_CONTACT2_EMAIL + "=" + SqlHandler.delimitString(facLocAppBean.getContact2Email()) + ",";
		query += ATTRIBUTE_SPECIFIC_USE_APPROVAL_REQUIRED + "=" + (facLocAppBean.isSpecificUseApprovalRequired() ? "'Y'" : "'N'") + ",";
		query += ATTRIBUTE_HET_MULTIPLE_BUILDING_USAGE + "=" + (facLocAppBean.isHetMultipleBuildingUsage() ? "'Y'" : "'N'") + ",";
		query += ATTRIBUTE_FLAMMABILITY_CONTROL_ZONE_ID + "=" + (facLocAppBean.getFlammabilityControlZoneId()) + ",";
		query += ATTRIBUTE_VOC_ZONE_ID + "=" + (facLocAppBean.getVocZoneId()) + ",";
		query += ATTRIBUTE_REPORT_INVENTORY + "=" + (facLocAppBean.isReportInventory() ? "'Y'" : "'N'") + ",";
		query += ATTRIBUTE_UPDATED_BY + "=" + facLocAppBean.getUpdatedBy() + ",";
		query += ATTRIBUTE_UPDATED_ON + "= SYSDATE ";
		query += getWhereClause(getKeyCriteria(facLocAppBean));
		// log.debug("update query:"+query);
		return new SqlManager().update(conn, query);
	}

	//update UseApprovalLimitsOption
	public int updateManagedUseApproval(FacLocAppBean facLocAppBean, BigDecimal personnelId) throws BaseException {
		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = updateManagedUseApproval(facLocAppBean, personnelId, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}

	public int updateManagedUseApproval(FacLocAppBean facLocAppBean, BigDecimal personnelId, Connection conn) throws BaseException {

		Collection cin = new Vector();

		if (facLocAppBean.getFacilityId() != null && facLocAppBean.getFacilityId().trim().length() > 0 && !"All".equalsIgnoreCase(facLocAppBean.getFacilityId())) {
			cin.add(new String(facLocAppBean.getFacilityId()));
		}
		else {
			cin.add("");
		}

		if (facLocAppBean.getApplication() != null && facLocAppBean.getApplication().length() > 0 && !"All".equalsIgnoreCase(facLocAppBean.getApplication())) {
			cin.add(new String(facLocAppBean.getApplication()));
		}
		else {
			cin.add("");
		}

		cin.add(personnelId);

		if (facLocAppBean.getManagedUseApproval() != null && facLocAppBean.getManagedUseApproval().length() > 0) {
			cin.add(new String(facLocAppBean.getManagedUseApproval()));
		}
		else {
			cin.add("");
		}

		if (log.isDebugEnabled()) {
			log.debug("FacilityId  " + facLocAppBean.getFacilityId() + "");
			log.debug("Application  " + facLocAppBean.getApplication() + "");
			log.debug("managedUseApproval  " + facLocAppBean.getManagedUseApproval() + "");
		}

		SqlManager sqlManager = new SqlManager();
		try {
			sqlManager.doProcedure(conn, "p_activate_managed_work_area", cin);
		}
		finally {

		}

		return 0;

	}

	//update UseApprovalLimitsOption
	public int updateUseApprovalLimitsOption(FacLocAppBean facLocAppBean) throws BaseException {
		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = updateUseApprovalLimitsOption(facLocAppBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}

	public int updateUseApprovalLimitsOption(FacLocAppBean facLocAppBean, Connection conn) throws BaseException {
		String query = "update " + TABLE + " set ";
		query += ATTRIBUTE_USE_APPROVAL_LIMITS_OPTION + "=" + SqlHandler.delimitString(facLocAppBean.getUseApprovalLimitsOption());
		query +=  " where " + ATTRIBUTE_FACILITY_ID + "='" + facLocAppBean.getFacilityId();
		query +=  "' and " + ATTRIBUTE_APPLICATION + "='" + facLocAppBean.getApplication() + "'";

		return new SqlManager().update(conn, query);
	}

}