package com.tcmis.client.het.factory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.het.beans.HetContainerUsageBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: HetContainerUsageBeanFactory <br>
 * @version: 1.0, Jun 2, 2011 <br>
 *****************************************************************************/

public class HetContainerUsageBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(getClass());

	//column names
	public String ATTRIBUTE_APPLICATION_ID = "APPLICATION_ID";
	public String ATTRIBUTE_CONTAINER_ID = "CONTAINER_ID";
	public String ATTRIBUTE_CUSTOMER_MSDS_DB = "CUSTOMER_MSDS_DB";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_KIT_ID = "KIT_ID";
	public String ATTRIBUTE_MATERIAL_ID = "MATERIAL_ID";
	public String ATTRIBUTE_AMOUNT_REMAINING = "AMOUNT_REMAINING";
	public String ATTRIBUTE_UNIT_OF_MEASURE = "UNIT_OF_MEASURE";
	public String ATTRIBUTE_PART_ID = "PART_ID";
	public String ATTRIBUTE_RECEIPT_ID = "RECEIPT_ID";
	public String ATTRIBUTE_SHIPMENT_ID = "SHIPMENT_ID";
	public String ATTRIBUTE_EXPIRATION_DATE = "EXPIRATION_DATE";
	public String ATTRIBUTE_MFG_LOT = "MFG_LOT";
	public String ATTRIBUTE_LOCATION = "LOCATION";
	public String ATTRIBUTE_SOLVENT = "SOLVENT";
	public String ATTRIBUTE_DILUENT = "DILUENT";
	public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
	public String ATTRIBUTE_CONTAINER_TYPE = "CONTAINER_TYPE";
	public String ATTRIBUTE_MSDS_NUMBER = "MSDS_NUMBER";

	//table name
	public String TABLE = "HET_CONTAINER_USAGE";

	//constructor
	public HetContainerUsageBeanFactory(DbManager dbManager) {
		super(dbManager);
	}

	//get column names
	@Override
	public String getColumnName(String attributeName) {
		if (attributeName.equals("containerId")) {
			return ATTRIBUTE_CONTAINER_ID;
		}
		else if (attributeName.equals("customerMsdsDb")) {
			return ATTRIBUTE_CUSTOMER_MSDS_DB;
		}
		else if (attributeName.equals("applicationId")) {
			return ATTRIBUTE_APPLICATION_ID;
		}
		else if (attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if (attributeName.equals("kitId")) {
			return ATTRIBUTE_KIT_ID;
		}
		else if (attributeName.equals("materialId")) {
			return ATTRIBUTE_MATERIAL_ID;
		}
		else if (attributeName.equals("amountRemaining")) {
			return ATTRIBUTE_AMOUNT_REMAINING;
		}
		else if (attributeName.equals("unitOfMeasure")) {
			return ATTRIBUTE_UNIT_OF_MEASURE;
		}
		else if (attributeName.equals("partId")) {
			return ATTRIBUTE_PART_ID;
		}
		else if (attributeName.equals("receiptId")) {
			return ATTRIBUTE_RECEIPT_ID;
		}
		else if (attributeName.equals("shipmentId")) {
			return ATTRIBUTE_SHIPMENT_ID;
		}
		else if (attributeName.equals("expirationDate")) {
			return ATTRIBUTE_EXPIRATION_DATE;
		}
		else if (attributeName.equals("mfgLot")) {
			return ATTRIBUTE_MFG_LOT;
		}
		else if (attributeName.equals("location")) {
			return ATTRIBUTE_LOCATION;
		}
		else if (attributeName.equals("solvent")) {
			return ATTRIBUTE_SOLVENT;
		}
		else if (attributeName.equals("diluent")) {
			return ATTRIBUTE_SOLVENT;
		}
		else if (attributeName.equals("catPartNo")) {
			return ATTRIBUTE_CAT_PART_NO;
		}
		else if (attributeName.equals("containerType")) {
			return ATTRIBUTE_CONTAINER_TYPE;
		}
		else if (attributeName.equals("msdsNumber")) {
			return ATTRIBUTE_MSDS_NUMBER;
		}
		else {
			return super.getColumnName(attributeName);
		}
		
	}

	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, HetContainerUsageBean.class);
	}

	public SearchCriteria getKeyCriteria(HetContainerUsageBean HetContainerUsageBean) {
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("containerId", SearchCriterion.EQUALS, "" + HetContainerUsageBean.getContainerId());
		return criteria;
	}

	//delete
	public int delete(HetContainerUsageBean HetContainerUsageBean) throws BaseException {
		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			return this.delete(HetContainerUsageBean, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}

	public int delete(HetContainerUsageBean HetContainerUsageBean, Connection conn) throws BaseException {

		return delete(getKeyCriteria(HetContainerUsageBean), conn);
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

	public int insert(HetContainerUsageBean HetContainerUsageBean) throws BaseException {

		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			return insert(HetContainerUsageBean, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}

	public int insert(HetContainerUsageBean HetContainerUsageBean, Connection conn) throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query = "insert into " + TABLE + " (";
		query += ATTRIBUTE_CONTAINER_ID + ",";
		query += ATTRIBUTE_APPLICATION_ID + ",";
		query += ATTRIBUTE_ITEM_ID + ",";
		query += ATTRIBUTE_KIT_ID + ",";
		query += ATTRIBUTE_MATERIAL_ID + ",";
		query += ATTRIBUTE_AMOUNT_REMAINING + ",";
		query += ATTRIBUTE_UNIT_OF_MEASURE+ ",";
		query += ATTRIBUTE_PART_ID  + ",";
		query += ATTRIBUTE_RECEIPT_ID + ",";
		query += ATTRIBUTE_SHIPMENT_ID + ",";
		query += ATTRIBUTE_EXPIRATION_DATE + ",";
		query += ATTRIBUTE_MFG_LOT + ",";
		query += ATTRIBUTE_LOCATION + ",";
		query += ATTRIBUTE_DILUENT + ",";
		query += ATTRIBUTE_SOLVENT + ",";
		query += ATTRIBUTE_CAT_PART_NO + ",";
		query += ATTRIBUTE_MSDS_NUMBER + ",";
		query += ATTRIBUTE_CUSTOMER_MSDS_DB + ",";
		query += ATTRIBUTE_CONTAINER_TYPE   + ")";
		query += " values (";
		query += SqlHandler.delimitString(HetContainerUsageBean.getContainerId()) + ",";
		query += HetContainerUsageBean.getApplicationId() + ",";
		query += HetContainerUsageBean.getItemId() + ",";
		query += HetContainerUsageBean.getKitId() + ",";
		query += HetContainerUsageBean.getMaterialId() + ",";
		query += HetContainerUsageBean.getAmountRemaining() + ",";
		query += SqlHandler.delimitString(HetContainerUsageBean.getUnitOfMeasure()) + ",";
		query += HetContainerUsageBean.getPartId() + ",";
		query += HetContainerUsageBean.getReceiptId() + ",";
		query += HetContainerUsageBean.getShipmentId() + ",";
		query += DateHandler.getOracleToDateFunction(HetContainerUsageBean.getExpirationDate()) + ",";
		query += SqlHandler.delimitString(HetContainerUsageBean.getMfgLot()) + ",";
		query += SqlHandler.delimitString(HetContainerUsageBean.getLocation()) + ",";
		query += (HetContainerUsageBean.isDiluent() ? "'Y'" : "'N'") + ",";
		query += (HetContainerUsageBean.isSolvent() ? "'Y'" : "'N'") + ",";
		query += SqlHandler.delimitString(HetContainerUsageBean.getCatPartNo()) + ",";
		query += SqlHandler.delimitString(HetContainerUsageBean.getMsdsNumber()) + ",";
		query += SqlHandler.delimitString(HetContainerUsageBean.getCustomerMsdsDb()) + ",";
		query += SqlHandler.delimitString(HetContainerUsageBean.getContainerType()) + ")";
		return sqlManager.update(conn, query);
	}

	//update
	public int update(HetContainerUsageBean HetContainerUsageBean) throws BaseException {

		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			return update(HetContainerUsageBean, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}

	public int update(HetContainerUsageBean HetContainerUsageBean, Connection conn) throws BaseException {

		String query = "update " + TABLE + " set ";
		query += ATTRIBUTE_APPLICATION_ID + "=" + HetContainerUsageBean.getApplicationId() + ", ";
		if (HetContainerUsageBean.hasUnitOfMeasure()) {
			query += ATTRIBUTE_UNIT_OF_MEASURE + "=" + SqlHandler.delimitString(HetContainerUsageBean.getUnitOfMeasure()) + ", ";
		}
		query += ATTRIBUTE_AMOUNT_REMAINING + "=" + StringHandler.nullIfEmpty(HetContainerUsageBean.getAmountRemaining()) + " " ;
		query += getWhereClause(getKeyCriteria(HetContainerUsageBean));

		return new SqlManager().update(conn, query);
	}

	public HetContainerUsageBean select(String containerId)throws BaseException {
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("containerId", SearchCriterion.EQUALS, containerId);
		Collection<HetContainerUsageBean> results = select(criteria, null);
		return results.isEmpty() ? null : results.iterator().next();
	}

	//select
	public Collection<HetContainerUsageBean> select(SearchCriteria criteria, SortCriteria sortCriteria) throws BaseException {

		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			return select(criteria, sortCriteria, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
	}

	public Collection<HetContainerUsageBean> select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn) throws BaseException {

		Collection<HetContainerUsageBean> HetContainerUsageBeanColl = new Vector<HetContainerUsageBean>();

		String query = "select * from " + TABLE + " " + getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			HetContainerUsageBean HetContainerUsageBean = new HetContainerUsageBean();
			load(dataSetRow, HetContainerUsageBean);
			HetContainerUsageBeanColl.add(HetContainerUsageBean);
		}

		return HetContainerUsageBeanColl;
	}

	public boolean exists(String containerId) throws BaseException {
		GenericSqlFactory factory = new GenericSqlFactory(getDbManager());
		String query = "select count(*) from " + TABLE + " where " + ATTRIBUTE_CONTAINER_ID + " = '" + containerId + "'";
		return !"0".equals(factory.selectSingleValue(query));
	}

	public BigDecimal getNewKitId() throws BaseException {
		return new BigDecimal( getDbManager().getOracleSequence("HET_MISC_SEQ"));
	}
}