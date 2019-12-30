package com.tcmis.client.het.factory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.het.beans.HetUsageBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: HetUsageBeanFactory <br>
 * @version: 1.0, May 31, 2011 <br>
 *****************************************************************************/

public class HetUsageBeanFactory extends BaseBeanFactory {

	public String ATTRIBUTE_APPLICATION_ID = "APPLICATION_ID";
	public String ATTRIBUTE_APPLICATION = "APPLICATION";
	public String ATTRIBUTE_APPLICATION_METHOD = "APPLICATION_METHOD";
	public String ATTRIBUTE_AMOUNT_REMAINING = "AMOUNT_REMAINING";
	public String ATTRIBUTE_BUILDING_ID = "BUILDING_ID";
	public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
	public String ATTRIBUTE_CHECKED_OUT = "CHECKED_OUT";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_CONTAINER_ID = "CONTAINER_ID";
	public String ATTRIBUTE_CONTAINER_TYPE = "CONTAINER_TYPE";
	public String ATTRIBUTE_CART_ID = "CART_ID";
	public String ATTRIBUTE_DISCARDED = "DISCARDED";
	public String ATTRIBUTE_EMPLOYEE = "EMPLOYEE";
	public String ATTRIBUTE_EXPORTED = "EXPORTED";
	public String ATTRIBUTE_EXPORTED_DATE = "EXPORTED_DATE";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_INSERT_DATE = "INSERT_DATE";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_LOGGER_NAME = "LOGGER_NAME";
	public String ATTRIBUTE_MSDS_NO = "MSDS_NO";
	public String ATTRIBUTE_MODIFIED = "MODIFIED";
	public String ATTRIBUTE_MODIFIED_DATE = "MODIFIED_DATE";
	public String ATTRIBUTE_PACKAGING = "PACKAGING";
	public String ATTRIBUTE_PART_TYPE = "PART_TYPE";
	public String ATTRIBUTE_KIT_ID = "KIT_ID";
	public String ATTRIBUTE_PERMIT = "PERMIT";
	public String ATTRIBUTE_QUANTITY = "QUANTITY";
	public String ATTRIBUTE_DELETION_CODE = "DELETION_CODE";
	public String ATTRIBUTE_REMARKS = "REMARKS";
	public String ATTRIBUTE_REPORTING_ENTITY_ID = "REPORTING_ENTITY_ID";
	public String ATTRIBUTE_SUBSTRATE = "SUBSTRATE";
	public String ATTRIBUTE_UNIT_OF_MEASURE = "UNIT_OF_MEASURE";
	public String ATTRIBUTE_USAGE_DATE = "USAGE_DATE";
	public String ATTRIBUTE_USAGE_ID = "USAGE_ID";
	public String ATTRIBUTE_USER_ID = "USER_ID";
	public String ATTRIBUTE_PAINTED = "PAINTED";
	public String ATTRIBUTE_CONTROL_DEVICE = "CONTROL_DEVICE";
	public String ATTRIBUTE_TRANSACTION_ID = "TRANSACTION_ID";
	public String ATTRIBUTE_TRADE_NAME = "TRADE_NAME";
	public String ATTRIBUTE_BUILDING_NAME = "BUILDING_NAME";


	Log log = LogFactory.getLog(getClass());

	//table name
	public String TABLE = "HET_USAGE";
	public String VIEW = "HET_USAGE_VIEW";

	//constructor
	public HetUsageBeanFactory(DbManager dbManager) {
		super(dbManager);
	}

	//delete
	public int delete(HetUsageBean HetUsageBean) throws BaseException {
		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			return delete(HetUsageBean, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}

	public int delete(HetUsageBean HetUsageBean, Connection conn) throws BaseException {

		return delete(getKeyCriteria(HetUsageBean), conn);
	}

	public int delete(SearchCriteria criteria) throws BaseException {

		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			return delete(criteria, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}

	public int delete(SearchCriteria criteria, Connection conn) throws BaseException {

		String sqlQuery = " delete from " + TABLE + " " + getWhereClause(criteria);

		return new SqlManager().update(conn, sqlQuery);
	}

	//get column names
	@Override
	public String getColumnName(String attributeName) {
		if (attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if (attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if (attributeName.equals("reportingEntityId")) {
			return ATTRIBUTE_REPORTING_ENTITY_ID;
		}
		else if (attributeName.equals("application")) {
			return ATTRIBUTE_APPLICATION;
		}
		else if (attributeName.equals("applicationId")) {
			return ATTRIBUTE_APPLICATION_ID;
		}
		else if (attributeName.equals("amountRemaining")) {
			return ATTRIBUTE_AMOUNT_REMAINING;
		}
		else if (attributeName.equals("catPartNo")) {
			return ATTRIBUTE_CAT_PART_NO;
		}
		else if (attributeName.equals("checkedOut")) {
			return ATTRIBUTE_CHECKED_OUT;
		}
		else if (attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if (attributeName.equals("cartId")) {
			return ATTRIBUTE_CART_ID;
		}
		else if (attributeName.equals("msdsNo")) {
			return ATTRIBUTE_MSDS_NO;
		}
		else if (attributeName.equals("modified")) {
			return ATTRIBUTE_MODIFIED;
		}
		else if (attributeName.equals("modifiedDate")) {
			return ATTRIBUTE_MODIFIED_DATE;
		}
		else if (attributeName.equals("quantity")) {
			return ATTRIBUTE_QUANTITY;
		}
		else if (attributeName.equals("unitOfMeasure")) {
			return ATTRIBUTE_UNIT_OF_MEASURE;
		}
		else if (attributeName.equals("permit")) {
			return ATTRIBUTE_PERMIT;
		}
		else if (attributeName.equals("applicationMethod")) {
			return ATTRIBUTE_APPLICATION_METHOD;
		}
		else if (attributeName.equals("substrate")) {
			return ATTRIBUTE_SUBSTRATE;
		}
		else if (attributeName.equals("containerId")) {
			return ATTRIBUTE_CONTAINER_ID;
		}
		else if (attributeName.equals("containerType")) {
			return ATTRIBUTE_CONTAINER_TYPE;
		}
		else if (attributeName.equals("deletionCode")) {
			return ATTRIBUTE_DELETION_CODE;
		}
		else if (attributeName.equals("usageDate")) {
			return ATTRIBUTE_USAGE_DATE;
		}
		else if (attributeName.equals("insertDate")) {
			return ATTRIBUTE_INSERT_DATE;
		}
		else if (attributeName.equals("userId")) {
			return ATTRIBUTE_USER_ID;
		}
		else if (attributeName.equals("usageId")) {
			return ATTRIBUTE_USAGE_ID;
		}
		else if (attributeName.equals("partType")) {
			return ATTRIBUTE_PART_TYPE;
		}
		else if (attributeName.equals("kitId")) {
			return ATTRIBUTE_KIT_ID;
		}
		else if (attributeName.equals("remarks")) {
			return ATTRIBUTE_REMARKS;
		}
		else if (attributeName.equals("discarded")) {
			return ATTRIBUTE_DISCARDED;
		}
		else if (attributeName.equals("exported")) {
			return ATTRIBUTE_EXPORTED;
		}
		else if (attributeName.equals("exportedDate")) {
			return ATTRIBUTE_EXPORTED_DATE;
		}
		else if(attributeName.equals("employee")){
			return ATTRIBUTE_EMPLOYEE;
		}
		else if (attributeName.equals("packaging")) {
			return ATTRIBUTE_PACKAGING;
		}
		else if (attributeName.equals("loggerName")) {
			return ATTRIBUTE_LOGGER_NAME;
		}
		else if (attributeName.equals("painted")) {
			return ATTRIBUTE_PAINTED;
		}
		else if (attributeName.equals("controlDevice")) {
			return ATTRIBUTE_CONTROL_DEVICE;
		}
		else if (attributeName.equals("transactionId")) {
			return ATTRIBUTE_TRANSACTION_ID;
		}
		else if (attributeName.equals("tradeName")) {
			return ATTRIBUTE_TRADE_NAME;
		}
		else if (attributeName.equals("buildingName")) {
			return ATTRIBUTE_BUILDING_NAME;
		}
		else if (attributeName.equals("buildingId")) {
			return ATTRIBUTE_BUILDING_ID;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}

	public SearchCriteria getKeyCriteria(HetUsageBean HetUsageBean) {
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("usageId", SearchCriterion.EQUALS, "" + HetUsageBean.getUsageId());
		return criteria;
	}

	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, HetUsageBean.class);
	}

	public int insert(HetUsageBean HetUsageBean) throws BaseException {

		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			return insert(HetUsageBean, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}

	public int insert(HetUsageBean usageBean, Connection conn) throws BaseException {
		//String newId = usageBean.isStandalone() ? "''" : "" +getDbManager().getOracleSequence("HET_USAGE_ID_SEQ");
		SqlManager sqlManager = new SqlManager();

		String query = "insert into " + TABLE + " (";
		query += ATTRIBUTE_COMPANY_ID + ",";
		query += ATTRIBUTE_FACILITY_ID + ",";
		query += ATTRIBUTE_REPORTING_ENTITY_ID + ",";
		query += ATTRIBUTE_APPLICATION_ID + ",";
		query += ATTRIBUTE_BUILDING_ID + ",";
		query += ATTRIBUTE_CAT_PART_NO + ",";
		query += ATTRIBUTE_ITEM_ID + ",";
		query += ATTRIBUTE_CART_ID + ",";
		query += ATTRIBUTE_MSDS_NO + ",";
		query += ATTRIBUTE_MODIFIED + ",";
		query += ATTRIBUTE_DISCARDED + ",";
		query += ATTRIBUTE_QUANTITY + ",";
		query += ATTRIBUTE_AMOUNT_REMAINING + ",";
		query += ATTRIBUTE_UNIT_OF_MEASURE + ",";
		query += ATTRIBUTE_PERMIT + ",";
		query += ATTRIBUTE_APPLICATION_METHOD + ",";
		query += ATTRIBUTE_SUBSTRATE + ",";
		query += ATTRIBUTE_CONTAINER_ID + ",";
		query += ATTRIBUTE_USAGE_DATE + ",";
		query += ATTRIBUTE_INSERT_DATE + ",";
		query += ATTRIBUTE_MODIFIED_DATE + ",";
		query += ATTRIBUTE_USER_ID + ",";
		//query += ATTRIBUTE_USAGE_ID + ",";
		query += ATTRIBUTE_PART_TYPE + ",";
		query += ATTRIBUTE_DELETION_CODE + ",";
		query += ATTRIBUTE_KIT_ID + ",";
		query += ATTRIBUTE_REMARKS + ",";
		query += ATTRIBUTE_EMPLOYEE + ",";
		query += ATTRIBUTE_PAINTED + ",";
		query += ATTRIBUTE_CONTROL_DEVICE + ",";
		query += ATTRIBUTE_TRANSACTION_ID + ",";
		query += ATTRIBUTE_CONTAINER_TYPE + ",";
		query += ATTRIBUTE_EXPORTED_DATE + ",";
		query += ATTRIBUTE_EXPORTED + ")";
		query += " values (";
		query += SqlHandler.delimitString(usageBean.getCompanyId()) + ",";
		query += SqlHandler.delimitString(usageBean.getFacilityId()) + ",";
		query += SqlHandler.delimitString(usageBean.getReportingEntityId())+ ",";
		query += usageBean.getApplicationId() + ",";
		query += usageBean.getBuildingId() + ",";
		query += SqlHandler.delimitString(usageBean.getCatPartNo()) + ",";
		query += usageBean.getItemId() + ",";
		query += usageBean.getCartId() + ",";
		query += SqlHandler.delimitString(usageBean.getMsdsNo()) + ",";
		query +=  "'N',";
		query +=  "'" + (usageBean.isDiscarded() ? "Y" : "N") + "',";
		query += usageBean.getQuantity() + ",";
		query += usageBean.getAmountRemaining() + ",";
		query += SqlHandler.delimitString(usageBean.getUnitOfMeasure()) + ",";
		query += SqlHandler.delimitString(usageBean.getPermit()) + ",";
		query += SqlHandler.delimitString(usageBean.getApplicationMethod()) + ",";
		query += SqlHandler.delimitString(usageBean.getSubstrate()) + ",";
		query += SqlHandler.delimitString(usageBean.getContainerId()) + ",";
		query += DateHandler.getOracleToDateFunction(usageBean.getUsageDate()) + ",";
		query += "sysdate,";
		query += "null,";
		query += usageBean.getUserId() + ",";
		//query += newId + ",";
		query += SqlHandler.delimitString(usageBean.getPartType()) + ",";
		query += SqlHandler.delimitString(usageBean.getDeletionCode()) + ",";
		query += usageBean.getKitId() + ",";
		query += SqlHandler.delimitString(usageBean.getRemarks()) + ",";
		query += SqlHandler.delimitString(usageBean.getEmployee()) + ",";
		query += "'" + (usageBean.isPainted() ? "Y" : "N") + "',";
		query += SqlHandler.delimitString(usageBean.getControlDevice()) + ",";
		query += usageBean.getTransactionId() + ",";
		query += SqlHandler.delimitString(usageBean.getContainerType()) + ",";
		query += DateHandler.getOracleToDateFunction(usageBean.getExportedDate()) + ",";
		query += SqlHandler.delimitString("N");
		query += ")";
		return sqlManager.update(conn, query);
	}

	public HetUsageBean selectBean (BigDecimal usageId) throws BaseException {
		SearchCriteria search = new SearchCriteria("usageId", SearchCriterion.EQUALS, "" + usageId);
		return select(search, null).iterator().next();
	}

	//select
	public Collection<HetUsageBean> select(SearchCriteria criteria, SortCriteria sortCriteria) throws BaseException {

		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			return select(criteria, sortCriteria, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}

	public Collection<HetUsageBean> select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn) throws BaseException {

		Collection<HetUsageBean> HetUsageBeanColl = new Vector<HetUsageBean>();

		String query = "select * from " + VIEW + " " + getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			HetUsageBean HetUsageBean = new HetUsageBean();
			load(dataSetRow, HetUsageBean);
			HetUsageBeanColl.add(HetUsageBean);
		}

		return HetUsageBeanColl;
	}

	//update
	public int update(HetUsageBean HetUsageBean) throws BaseException {

		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			return update(HetUsageBean, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}

	public int update(HetUsageBean HetUsageBean, Connection conn) throws BaseException {

		String query = "update " + TABLE + " set ";
		query += ATTRIBUTE_REPORTING_ENTITY_ID + "=" + SqlHandler.delimitString(HetUsageBean.getReportingEntityId()) + ",";
		query += ATTRIBUTE_APPLICATION_ID + "=" + StringHandler.nullIfZero(HetUsageBean.getApplicationId()) + ",";
		query += ATTRIBUTE_BUILDING_ID + "=" + StringHandler.nullIfZero(HetUsageBean.getBuildingId()) + ",";
		query += ATTRIBUTE_CAT_PART_NO + "=" + SqlHandler.delimitString(HetUsageBean.getCatPartNo()) + ",";
		query += ATTRIBUTE_ITEM_ID + "=" + StringHandler.nullIfZero(HetUsageBean.getItemId()) + ",";
		query += ATTRIBUTE_CART_ID + "=" + StringHandler.nullIfZero(HetUsageBean.getCartId()) + ",";
		query += ATTRIBUTE_MSDS_NO + "=" + SqlHandler.delimitString(HetUsageBean.getMsdsNo()) + ",";
		query += ATTRIBUTE_QUANTITY + "=" + StringHandler.nullIfZero(HetUsageBean.getQuantity()) + ",";
		query += ATTRIBUTE_AMOUNT_REMAINING + "=" + StringHandler.nullIfZero(HetUsageBean.getAmountRemaining()) + ",";
		query += ATTRIBUTE_UNIT_OF_MEASURE + "=" + SqlHandler.delimitString(HetUsageBean.getUnitOfMeasure()) + ",";
		query += ATTRIBUTE_PERMIT + "=" + SqlHandler.delimitString(HetUsageBean.getPermit()) + ",";
		query += ATTRIBUTE_APPLICATION_METHOD + "=" + SqlHandler.delimitString(HetUsageBean.getApplicationMethod()) + ",";
		query += ATTRIBUTE_SUBSTRATE + "=" + SqlHandler.delimitString(HetUsageBean.getSubstrate()) + ",";
		query += ATTRIBUTE_CONTAINER_ID + "=" + SqlHandler.delimitString(HetUsageBean.getContainerId()) + ",";
		query += ATTRIBUTE_USAGE_DATE + "=" + DateHandler.getOracleToDateFunction(HetUsageBean.getUsageDate()) + ",";
		query += ATTRIBUTE_EXPORTED_DATE + "=" + DateHandler.getOracleToDateFunction(HetUsageBean.getExportedDate()) + ",";
		query += ATTRIBUTE_USER_ID + "=" + StringHandler.nullIfZero(HetUsageBean.getUserId()) + ",";
		query += ATTRIBUTE_PART_TYPE + "=" + SqlHandler.delimitString(HetUsageBean.getPartType()) + ",";
		query += ATTRIBUTE_KIT_ID + "=" + StringHandler.nullIfZero(HetUsageBean.getKitId()) + ",";
		query += ATTRIBUTE_REMARKS + "=" + SqlHandler.delimitString(HetUsageBean.getRemarks()) + ",";
		query += ATTRIBUTE_EXPORTED + "=" + SqlHandler.delimitString(HetUsageBean.isExported() ? "Y" : "N") + ",";
		query += ATTRIBUTE_DISCARDED + "=" + SqlHandler.delimitString(HetUsageBean.isDiscarded() ? "Y" : "N") + ",";
		query += ATTRIBUTE_EMPLOYEE +  "=" + SqlHandler.delimitString(HetUsageBean.getEmployee()) + ",";
		query += ATTRIBUTE_DELETION_CODE +  "=" + SqlHandler.delimitString(HetUsageBean.getDeletionCode()) + ",";
		query += ATTRIBUTE_PAINTED +  "=" + SqlHandler.delimitString(HetUsageBean.isPainted() ? "Y" : "N") + ",";
		query += ATTRIBUTE_CONTROL_DEVICE +  "=" + SqlHandler.delimitString(HetUsageBean.getControlDevice()) + ",";
		query += ATTRIBUTE_CONTAINER_TYPE + "=" + SqlHandler.delimitString(HetUsageBean.getContainerType()) + ",";
		query += ATTRIBUTE_MODIFIED +  "='Y',";
		query += ATTRIBUTE_MODIFIED_DATE +  "=sysdate ";
		query += getWhereClause(getKeyCriteria(HetUsageBean));

		return new SqlManager().update(conn, query);
	}
}