package com.tcmis.client.operations.factory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import oracle.jdbc.OracleTypes;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.operations.beans.CabinetInventoryCountBean;
import com.tcmis.client.operations.beans.ClientCabinetInventoryInputBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: CabinetInventoryCountBeanFactory <br>
 * 
 * @version: 1.0, Aug 3, 2009 <br>
 *****************************************************************************/

public class CabinetInventoryCountBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	// column names
	public String ATTRIBUTE_BIN_ID = "BIN_ID";
	public String ATTRIBUTE_COUNT_DATETIME = "COUNT_DATETIME";
	public String ATTRIBUTE_RECEIPT_ID = "RECEIPT_ID";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_PERSONNEL_ID = "PERSONNEL_ID";
	public String ATTRIBUTE_COUNT_QUANTITY = "COUNT_QUANTITY";
	public String ATTRIBUTE_DATE_PROCESSED = "DATE_PROCESSED";
	public String ATTRIBUTE_MODIFIED_TIMESTAMP = "MODIFIED_TIMESTAMP";
	public String ATTRIBUTE_HUB = "HUB";
	public String ATTRIBUTE_CABINET_ID = "CABINET_ID";
	public String ATTRIBUTE_CABINET_NAME = "CABINET_NAME";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID=";
	public String ATTRIBUTE_ORDERING_APPLICATION = "ORDERING_APPLICATION";
	public String ATTRIBUTE_USE_APPLICATION = "USE_APPLICATION";
	public String ATTRIBUTE_MFG_LOT = "MFG_LOT";
	public String ATTRIBUTE_EXPIRE_DATE = "EXPIRE_DATE";
	public String ATTRIBUTE_COUNTED_BY_PERSONNEL_ID = "COUNTED_BY_PERSONNEL_ID";
	public String ATTRIBUTE_COUNTED_BY_NAME = "COUNTED_BY_NAME";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_ITEM_DESC = "ITEM_DESC";
	public String ATTRIBUTE_QTY_ISSUED_AFTER_COUNT = "ISSUED_AFTER_COUNT";
	public String ATTRIBUTE_TOTAL_QUANTITY = "TOTAL_QUANTITY";
	public String ATTRIBUTE_QC_DOC = "QC_DOC";
	public String ATTRIBUTE_UNIT_COST = "UNIT_COST";
	public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
	public String ATTRIBUTE_REORDER_POINT = "REORDER_POINT";
	public String ATTRIBUTE_STOCKING_LEVEL = "STOCKING_LEVEL";
	public String ATTRIBUTE_QTY_AVAILABLE_AFTER_ALLOC = "QTY_AVAILABLE_AFTER_ALLOC";
	public String ATTRIBUTE_LEAD_TIME_DAYS = "LEAD_TIME_DAYS";
	public String ATTRIBUTE_OPEN_MR_QTY = "OPEN_MR_QTY";
	public String ATTRIBUTE_CURRENCY_ID = "CURRENCY_ID";
	public String ATTRIBUTE_STATUS = "STATUS";

	// table name
	public String TABLE = "CABINET_INVENTORY_COUNT";

	// constructor
	public CabinetInventoryCountBeanFactory(DbManager dbManager) {
		super(dbManager);
	}

	// get column names
	@Override
	public String getColumnName(String attributeName) {
		if (attributeName.equals("binId")) {
			return ATTRIBUTE_BIN_ID;
		}
		else if (attributeName.equals("countDatetime")) {
			return ATTRIBUTE_COUNT_DATETIME;
		}
		else if (attributeName.equals("receiptId")) {
			return ATTRIBUTE_RECEIPT_ID;
		}
		else if (attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if (attributeName.equals("personnelId")) {
			return ATTRIBUTE_PERSONNEL_ID;
		}
		else if (attributeName.equals("countQuantity")) {
			return ATTRIBUTE_COUNT_QUANTITY;
		}
		else if (attributeName.equals("dateProcessed")) {
			return ATTRIBUTE_DATE_PROCESSED;
		}
		else if (attributeName.equals("modifiedTimestamp")) {
			return ATTRIBUTE_MODIFIED_TIMESTAMP;
		}

		else if (attributeName.equals("hub")) {
			return ATTRIBUTE_HUB;
		}
		else if (attributeName.equals("cabinetId")) {
			return ATTRIBUTE_CABINET_ID;
		}
		else if (attributeName.equals("cabinetName")) {
			return ATTRIBUTE_CABINET_NAME;
		}
		else if (attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if (attributeName.equals("orderingApplication")) {
			return ATTRIBUTE_ORDERING_APPLICATION;
		}
		else if (attributeName.equals("useApplication")) {
			return ATTRIBUTE_USE_APPLICATION;
		}
		else if (attributeName.equals("mfgLot")) {
			return ATTRIBUTE_MFG_LOT;
		}
		else if (attributeName.equals("expireDate")) {
			return ATTRIBUTE_EXPIRE_DATE;
		}
		else if (attributeName.equals("countedByPersonnelId")) {
			return ATTRIBUTE_COUNTED_BY_PERSONNEL_ID;
		}
		else if (attributeName.equals("countedByName")) {
			return ATTRIBUTE_COUNTED_BY_NAME;
		}
		else if (attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if (attributeName.equals("itemDesc")) {
			return ATTRIBUTE_ITEM_DESC;
		}
		else if (attributeName.equals("qtyIssuedAfterCount")) {
			return ATTRIBUTE_QTY_ISSUED_AFTER_COUNT;
		}
		else if (attributeName.equals("totalQuantity")) {
			return ATTRIBUTE_TOTAL_QUANTITY;
		}
		else if (attributeName.equals("qcDoc")) {
			return ATTRIBUTE_QC_DOC;
		}
		else if (attributeName.equals("unitCost")) {
			return ATTRIBUTE_UNIT_COST;
		}
		else if (attributeName.equals("catPartNo")) {
			return ATTRIBUTE_CAT_PART_NO;
		}
		else if (attributeName.equals("reorderPoint")) {
			return ATTRIBUTE_REORDER_POINT;
		}
		else if (attributeName.equals("stockingLevel")) {
			return ATTRIBUTE_STOCKING_LEVEL;
		}
		else if (attributeName.equals("qtyAvailableAfterAlloc")) {
			return ATTRIBUTE_QTY_AVAILABLE_AFTER_ALLOC;
		}
		else if (attributeName.equals("leadTimeDays")) {
			return ATTRIBUTE_LEAD_TIME_DAYS;
		}
		else if (attributeName.equals("openMrQty")) {
			return ATTRIBUTE_OPEN_MR_QTY;
		}
		else if (attributeName.equals("currencyId")) {
			return ATTRIBUTE_CURRENCY_ID;
		}
		else if (attributeName.equals("status")) {
			return ATTRIBUTE_STATUS;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}

	// get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, CabinetInventoryCountBean.class);
	}

	// you need to verify the primary key(s) before uncommenting this
	/*
	 * //delete public int delete(CabinetInventoryCountBean
	 * cabinetInventoryCountBean) throws BaseException {
	 * 
	 * SearchCriteria criteria = new SearchCriteria("binId",
	 * "SearchCriterion.EQUALS", "" + cabinetInventoryCountBean.getBinId());
	 * 
	 * Connection connection = null; int result = 0; try { connection =
	 * this.getDbManager().getConnection(); result = this.delete(criteria,
	 * connection); } finally {
	 * this.getDbManager().returnConnection(connection); } return result; }
	 * 
	 * 
	 * public int delete(CabinetInventoryCountBean
	 * cabinetInventoryCountBean, Connection conn) throws BaseException {
	 * 
	 * SearchCriteria criteria = new SearchCriteria("binId",
	 * "SearchCriterion.EQUALS", "" + cabinetInventoryCountBean.getBinId());
	 * 
	 * return delete(criteria, conn); }
	 */

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

	// you need to verify the primary key(s) before uncommenting this
	/*
	 * //insert public int insert(CabinetInventoryCountBean
	 * cabinetInventoryCountBean) throws BaseException {
	 * 
	 * Connection connection = null; int result = 0; try { connection =
	 * getDbManager().getConnection(); result =
	 * insert(cabinetInventoryCountBean, connection); } finally {
	 * this.getDbManager().returnConnection(connection); } return result; }
	 * 
	 * 
	 * public int insert(CabinetInventoryCountBean
	 * cabinetInventoryCountBean, Connection conn) throws BaseException {
	 * 
	 * SqlManager sqlManager = new SqlManager();
	 * 
	 * String query = "insert into " + TABLE + " (" + ATTRIBUTE_BIN_ID + ","
	 * + ATTRIBUTE_COUNT_DATETIME + "," + ATTRIBUTE_RECEIPT_ID + "," +
	 * ATTRIBUTE_COMPANY_ID + "," + ATTRIBUTE_PERSONNEL_ID + "," +
	 * ATTRIBUTE_COUNT_QUANTITY + "," + ATTRIBUTE_DATE_PROCESSED + "," +
	 * ATTRIBUTE_MODIFIED_TIMESTAMP + ")" + " values (" +
	 * cabinetInventoryCountBean.getBinId() + "," +
	 * DateHandler.getOracleToDateFunction
	 * (cabinetInventoryCountBean.getCountDatetime()) + "," +
	 * cabinetInventoryCountBean.getReceiptId() + "," +
	 * SqlHandler.delimitString(cabinetInventoryCountBean.getCompanyId()) +
	 * "," + cabinetInventoryCountBean.getPersonnelId() + "," +
	 * cabinetInventoryCountBean.getCountQuantity() + "," +
	 * DateHandler.getOracleToDateFunction
	 * (cabinetInventoryCountBean.getDateProcessed()) + "," +
	 * DateHandler.getOracleToDateFunction
	 * (cabinetInventoryCountBean.getModifiedTimestamp()) + ")";
	 * 
	 * return sqlManager.update(conn, query); }
	 * 
	 * 
	 * //update public int update(CabinetInventoryCountBean
	 * cabinetInventoryCountBean) throws BaseException {
	 * 
	 * Connection connection = null; int result = 0; try { connection =
	 * getDbManager().getConnection(); result =
	 * update(cabinetInventoryCountBean, connection); } finally {
	 * this.getDbManager().returnConnection(connection); } return result; }
	 * 
	 * 
	 * public int update(CabinetInventoryCountBean
	 * cabinetInventoryCountBean, Connection conn) throws BaseException {
	 * 
	 * String query = "update " + TABLE + " set " + ATTRIBUTE_BIN_ID + "=" +
	 * StringHandler.nullIfZero(cabinetInventoryCountBean.getBinId()) + ","
	 * + ATTRIBUTE_COUNT_DATETIME + "=" +
	 * DateHandler.getOracleToDateFunction
	 * (cabinetInventoryCountBean.getCountDatetime()) + "," +
	 * ATTRIBUTE_RECEIPT_ID + "=" +
	 * StringHandler.nullIfZero(cabinetInventoryCountBean.getReceiptId()) +
	 * "," + ATTRIBUTE_COMPANY_ID + "=" +
	 * SqlHandler.delimitString(cabinetInventoryCountBean.getCompanyId()) +
	 * "," + ATTRIBUTE_PERSONNEL_ID + "=" +
	 * StringHandler.nullIfZero(cabinetInventoryCountBean.getPersonnelId())
	 * + "," + ATTRIBUTE_COUNT_QUANTITY + "=" +
	 * StringHandler.nullIfZero(cabinetInventoryCountBean
	 * .getCountQuantity()) + "," + ATTRIBUTE_DATE_PROCESSED + "=" +
	 * DateHandler
	 * .getOracleToDateFunction(cabinetInventoryCountBean.getDateProcessed
	 * ()) + "," + ATTRIBUTE_MODIFIED_TIMESTAMP + "=" +
	 * DateHandler.getOracleToDateFunction
	 * (cabinetInventoryCountBean.getModifiedTimestamp()) + " " + "where " +
	 * ATTRIBUTE_BIN_ID + "=" + cabinetInventoryCountBean.getBinId();
	 * 
	 * return new SqlManager().update(conn, query); }
	 */

	// select
	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria) throws BaseException {

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

	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn) throws BaseException {

		Collection cabinetInventoryCountBeanColl = new Vector();

		String query = "select * from " + TABLE + " " + getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			CabinetInventoryCountBean cabinetInventoryCountBean = new CabinetInventoryCountBean();
			load(dataSetRow, cabinetInventoryCountBean);
			cabinetInventoryCountBeanColl.add(cabinetInventoryCountBean);
		}

		return cabinetInventoryCountBeanColl;
	}

	@SuppressWarnings("unchecked")
	public Collection selectUsingProc(ClientCabinetInventoryInputBean inputBean) throws Exception {
		Connection connection = this.getDbManager().getConnection();
		Collection c = null;
		Collection inArgs = new Vector(10);

		Collection clientCabinetInvCountColl = new Vector();

		// 1
		if (StringHandler.isBlankString(inputBean.getBranchPlant())) {
			inArgs.add("");
		}
		else {
			inArgs.add(inputBean.getBranchPlant());
		}

		// 2
		if (StringHandler.isBlankString(inputBean.getFacilityId())) {
			inArgs.add("");
		}
		else {
			inArgs.add(inputBean.getFacilityId());
		}

		// 3
		if (StringHandler.isBlankString(inputBean.getApplication()) || ("All".equals(inputBean.getApplication()))) {
			inArgs.add("");
		}
		else {
			inArgs.add(inputBean.getApplication());
		}
		// 4
		if ((inputBean.getCabinetIdArray().length == 0) || ("All".equals(inputBean.getCabinetIdArray()))) {
			inArgs.add("");
		}
		else {
			String cabinetIdCsvString = getCsvStringFromArray(inputBean.getCabinetIdArray());

			inArgs.add(cabinetIdCsvString);
		}

		if (inputBean.getSearchArgument() != null && inputBean.getSearchArgument().length() > 0) {
			// 5
			if (inputBean.getMatchingMode().equalsIgnoreCase("Contains")) {
				inArgs.add("Y");
			}
			else {
				inArgs.add("");

			}
			// 6
			if (inputBean.getSearchField() != null && inputBean.getSearchField().length() > 0) {
				inArgs.add(inputBean.getSearchField());
			}
			else {
				inArgs.add("");
			}

			// 7
			inArgs.add(inputBean.getSearchArgument());

		}
		else {
			inArgs.add("");
			inArgs.add("");
			inArgs.add("");
		}

		// 8
		if (inputBean.getExpireInFrom() != null) {
			inArgs.add(inputBean.getExpireInFrom());
		}
		else {
			inArgs.add("");
		}
		// 9
		if (inputBean.getExpireInTo() != null) {
			inArgs.add(inputBean.getExpireInTo());
		}
		else {
			inArgs.add("");
		}
		// 10
		if (inputBean.getSortBy() != null && inputBean.getSortBy().length() > 0) {
			inArgs.add(inputBean.getSortBy()); // position 11
		}
		else {
			inArgs.add("");
		}

		Vector outArgs = new Vector();
		outArgs.add(new Integer(OracleTypes.CURSOR));
		outArgs.add(new Integer(java.sql.Types.VARCHAR));

		if (log.isDebugEnabled()) {
			log.debug("inArgs for PKG_CABINET_INV_COUNT_REPORT.PR_CAB_INV_COUNT_REP_CLIENT:" + inArgs);
			log.debug("outArgs for PKG_CABINET_INV_COUNT_REPORT.PR_CAB_INV_COUNT_REP_CLIENT:" + outArgs);
		}
		SqlManager sqlManager = new SqlManager();
		Vector result = (Vector) sqlManager.doProcedure(connection, "PKG_CABINET_INV_COUNT_REPORT.PR_CAB_INV_COUNT_REP_CLIENT", inArgs, outArgs);
		String query = null;
		ResultSet resultSet = null;

		if (result.size() != 0)
			query = ((Vector<String>) result).get(1);
		else
			query = null;
		if (null != query) {
			resultSet = (ResultSet) (result).elementAt(0);
		}

		DataSet dataSet = new DataSet();
		try {
			new SqlManager().populateDataSet(dataSet, resultSet);
		}
		catch (Exception e) {
			log.error("Exception in SqlManager().populateDataSet(dataSet, resultSet)", e);
			throw new BaseException(e);
		}
		Iterator dataIter = dataSet.iterator();
        boolean positiveQ = ( inputBean.getPositiveQ() == null || !inputBean.getPositiveQ().equals("Yes") );
        while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			CabinetInventoryCountBean clientCabinetBean = new CabinetInventoryCountBean();
			load(dataSetRow, clientCabinetBean);
            String totalQ = clientCabinetBean.getTotalQuantity().toString();
            if(  positiveQ || !totalQ.equals("0") )
                clientCabinetInvCountColl.add(clientCabinetBean);
		}
		this.getDbManager().returnConnection(connection);
		return clientCabinetInvCountColl;
	}

	public static String getCsvStringFromArray(String[] stringArray) {
		StringBuilder csvStringBuilder = new StringBuilder();
		if (stringArray == null || stringArray.length == 0)
			return "";
		for (int i = 0; i < stringArray.length; i++)
			if (stringArray[i].equals(""))
				return "";
		for (int i = 0; i < stringArray.length; i++) {
			if (i > 0) {
				csvStringBuilder.append(",");
			}
			csvStringBuilder.append("");
			csvStringBuilder.append(stringArray[i]);
			csvStringBuilder.append("");
		}
		return csvStringBuilder.toString();
	}

}
