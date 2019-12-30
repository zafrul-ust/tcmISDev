package com.tcmis.internal.distribution.factory;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DbSelectException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.internal.distribution.beans.SalesQuoteHoldOvBean;


/******************************************************************************
 * CLASSNAME: SalesQuoteHoldOvBeanFactory <br>
 * @version: 1.0, Mar 3, 2010 <br>
 *****************************************************************************/


public class SalesQuoteHoldOvBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_INVENTORY_GROUP_NAME = "INVENTORY_GROUP_NAME";
	public String ATTRIBUTE_SHIP_TO_ADDRESS_LINE_1 = "SHIP_TO_ADDRESS_LINE_1";
	public String ATTRIBUTE_SHIP_TO_ADDRESS_LINE_2 = "SHIP_TO_ADDRESS_LINE_2";
	public String ATTRIBUTE_SHIP_TO_ADDRESS_LINE_3 = "SHIP_TO_ADDRESS_LINE_3";
	public String ATTRIBUTE_SHIP_TO_ADDRESS_LINE_4 = "SHIP_TO_ADDRESS_LINE_4";
	public String ATTRIBUTE_SHIP_TO_ADDRESS_LINE_5 = "SHIP_TO_ADDRESS_LINE_5";
	public String ATTRIBUTE_RELEASE_STATUS = "RELEASE_STATUS";
	public String ATTRIBUTE_TOTAL_EXTENDED_PRICE = "TOTAL_EXTENDED_PRICE";
	public String ATTRIBUTE_CUSTOMER_ID = "CUSTOMER_ID";
	public String ATTRIBUTE_CUSTOMER_NAME = "CUSTOMER_NAME";
	public String ATTRIBUTE_PO_NUMBER = "PO_NUMBER";
	public String ATTRIBUTE_SUBMITTED_BY_NAME = "SUBMITTED_BY_NAME";
	public String ATTRIBUTE_REQUESTOR_NAME = "REQUESTOR_NAME";
	public String ATTRIBUTE_AVAILABLE_CREDIT = "AVAILABLE_CREDIT";
	public String ATTRIBUTE_WITHIN_TERMS = "WITHIN_TERMS";
	public String ATTRIBUTE_CUSTOMER_NOTE = "CUSTOMER_NOTE";
	public String ATTRIBUTE_INTERNAL_NOTE = "INTERNAL_NOTE";
	public String ATTRIBUTE_SHIPTO_NOTE = "SHIPTO_NOTE";
	public String ATTRIBUTE_SPECIAL_INSTRUCTIONS = "SPECIAL_INSTRUCTIONS";
	public String ATTRIBUTE_OPS_ENTITY_ID = "OPS_ENTITY_ID";
	public String ATTRIBUTE_HUB = "HUB";
	public String ATTRIBUTE_HUB_NAME = "HUB_NAME";
	public String ATTRIBUTE_ORDER_STATUS = "ORDER_STATUS";
	public String ATTRIBUTE_SUBMITTED_DATE = "SUBMITTED_DATE";
	public String ATTRIBUTE_SUBMITTED_BY = "SUBMITTED_BY";
	public String ATTRIBUTE_QUALITY_HOLD = "QUALITY_HOLD";
	public String ATTRIBUTE_BILL_TO_COMPANY_ID = "BILL_TO_COMPANY_ID";
	public String ATTRIBUTE_OPS_COMPANY_ID = "OPS_COMPANY_ID";
	public String ATTRIBUTE_DISPLAY_STATUS = "DISPLAY_STATUS";
	public String ATTRIBUTE_HOLD_COMMENTS = "HOLD_COMMENTS";
	public String ATTRIBUTE_DATE_FIRST_CONFIRMED = "DATE_FIRST_CONFIRMED";
	public String ATTRIBUTE_LINE = "LINE";
	public String ATTRIBUTE_MATERIAL_REQUEST_ORIGIN = "MATERIAL_REQUEST_ORIGIN";
		
	//table name
	public String TABLE = "SALES_QUOTE_HOLD_OV";


	//constructor
	public SalesQuoteHoldOvBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	@Override
	public String getColumnName(String attributeName) {
		if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("prNumber")) {
			return ATTRIBUTE_PR_NUMBER;
		}
		else if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("inventoryGroupName")) {
			return ATTRIBUTE_INVENTORY_GROUP_NAME;
		}
		else if(attributeName.equals("shipToAddressLine1")) {
			return ATTRIBUTE_SHIP_TO_ADDRESS_LINE_1;
		}
		else if(attributeName.equals("shipToAddressLine2")) {
			return ATTRIBUTE_SHIP_TO_ADDRESS_LINE_2;
		}
		else if(attributeName.equals("shipToAddressLine3")) {
			return ATTRIBUTE_SHIP_TO_ADDRESS_LINE_3;
		}
		else if(attributeName.equals("shipToAddressLine4")) {
			return ATTRIBUTE_SHIP_TO_ADDRESS_LINE_4;
		}
		else if(attributeName.equals("shipToAddressLine5")) {
			return ATTRIBUTE_SHIP_TO_ADDRESS_LINE_5;
		}
		else if(attributeName.equals("releaseStatus")) {
			return ATTRIBUTE_RELEASE_STATUS;
		}
		else if(attributeName.equals("totalExtendedPrice")) {
			return ATTRIBUTE_TOTAL_EXTENDED_PRICE;
		}
		else if(attributeName.equals("customerId")) {
			return ATTRIBUTE_CUSTOMER_ID;
		}
		else if(attributeName.equals("customerName")) {
			return ATTRIBUTE_CUSTOMER_NAME;
		}
		else if(attributeName.equals("poNumber")) {
			return ATTRIBUTE_PO_NUMBER;
		}
		else if(attributeName.equals("submittedByName")) {
			return ATTRIBUTE_SUBMITTED_BY_NAME;
		}
		else if(attributeName.equals("requestorName")) {
			return ATTRIBUTE_REQUESTOR_NAME;
		}
		else if(attributeName.equals("availableCredit")) {
			return ATTRIBUTE_AVAILABLE_CREDIT;
		}
		else if(attributeName.equals("withinTerms")) {
			return ATTRIBUTE_WITHIN_TERMS;
		}
		else if(attributeName.equals("customerNote")) {
			return ATTRIBUTE_CUSTOMER_NOTE;
		}
		else if(attributeName.equals("internalNote")) {
			return ATTRIBUTE_INTERNAL_NOTE;
		}
		else if(attributeName.equals("shiptoNote")) {
			return ATTRIBUTE_SHIPTO_NOTE;
		}
		else if(attributeName.equals("specialInstructions")) {
			return ATTRIBUTE_SPECIAL_INSTRUCTIONS;
		}
		else if(attributeName.equals("opsEntityId")) {
			return ATTRIBUTE_OPS_ENTITY_ID;
		}
		else if(attributeName.equals("hub")) {
			return ATTRIBUTE_HUB;
		}
		else if(attributeName.equals("hubName")) {
			return ATTRIBUTE_HUB_NAME;
		}
		else if(attributeName.equals("orderStatus")) {
			return ATTRIBUTE_ORDER_STATUS;
		}
		else if(attributeName.equals("submittedDate")) {
			return ATTRIBUTE_SUBMITTED_DATE;
		}
		else if(attributeName.equals("submittedBy")) {
			return ATTRIBUTE_SUBMITTED_BY;
		}
		else if(attributeName.equals("qualityHold")) {
			return ATTRIBUTE_QUALITY_HOLD;
		}
		else if(attributeName.equals("billToCompanyId")) {
			return ATTRIBUTE_BILL_TO_COMPANY_ID;
		}		
		else if(attributeName.equals("opsCompanyId")) {
			return ATTRIBUTE_OPS_COMPANY_ID;
		}
		else if(attributeName.equals("displayStatus")) {
			return ATTRIBUTE_DISPLAY_STATUS;
		}
		else if(attributeName.equals("holdComments")) {
			return ATTRIBUTE_HOLD_COMMENTS;
		}
		else if(attributeName.equals("dateFirstConfirmed")) {
			return ATTRIBUTE_DATE_FIRST_CONFIRMED;
		}
		else if(attributeName.equals("line")) {
			return ATTRIBUTE_LINE;
		}
		else if(attributeName.equals("materialRequestOrigin")) {
			return ATTRIBUTE_MATERIAL_REQUEST_ORIGIN;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, SalesQuoteHoldOvBean.class);
	}


	//you need to verify the primary key(s) before uncommenting this
	/*
	//delete
	public int delete(SalesQuoteHoldOvBean salesQuoteHoldOvBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("prNumber", "SearchCriterion.EQUALS",
			"" + salesQuoteHoldOvBean.getPrNumber());

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


	public int delete(SalesQuoteHoldOvBean salesQuoteHoldOvBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("prNumber", "SearchCriterion.EQUALS",
			"" + salesQuoteHoldOvBean.getPrNumber());

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
	public int insert(SalesQuoteHoldOvBean salesQuoteHoldOvBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(salesQuoteHoldOvBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(SalesQuoteHoldOvBean salesQuoteHoldOvBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
			ATTRIBUTE_PR_NUMBER + "," +
			ATTRIBUTE_INVENTORY_GROUP + "," +
			ATTRIBUTE_INVENTORY_GROUP_NAME + "," +
			ATTRIBUTE_SHIP_TO_ADDRESS_LINE_1 + "," +
			ATTRIBUTE_SHIP_TO_ADDRESS_LINE_2 + "," +
			ATTRIBUTE_SHIP_TO_ADDRESS_LINE_3 + "," +
			ATTRIBUTE_SHIP_TO_ADDRESS_LINE_4 + "," +
			ATTRIBUTE_SHIP_TO_ADDRESS_LINE_5 + "," +
			ATTRIBUTE_RELEASE_STATUS + "," +
			ATTRIBUTE_TOTAL_EXTENDED_PRICE + "," +
			ATTRIBUTE_CUSTOMER_ID + "," +
			ATTRIBUTE_CUSTOMER_NAME + "," +
			ATTRIBUTE_PO_NUMBER + "," +
			ATTRIBUTE_SUBMITTED_BY_NAME + "," +
			ATTRIBUTE_REQUESTOR_NAME + "," +
			ATTRIBUTE_AVAILABLE_CREDIT + "," +
			ATTRIBUTE_WITHIN_TERMS + "," +
			ATTRIBUTE_CUSTOMER_NOTE + "," +
			ATTRIBUTE_INTERNAL_NOTE + "," +
			ATTRIBUTE_SHIPTO_NOTE + "," +
			ATTRIBUTE_SPECIAL_INSTRUCTIONS + "," +
			ATTRIBUTE_OPS_ENTITY_ID + "," +
			ATTRIBUTE_HUB + "," +
			ATTRIBUTE_HUB_NAME + "," +
			ATTRIBUTE_ORDER_STATUS + "," +
			ATTRIBUTE_SUBMITTED_DATE + "," +
			ATTRIBUTE_SUBMITTED_BY + "," +
			ATTRIBUTE_QUALITY_HOLD + "," +
			ATTRIBUTE_DISPLAY_STATUS + "," +
			ATTRIBUTE_LINE + ")" +
			" values (" +
			salesQuoteHoldOvBean.getPrNumber() + "," +
			SqlHandler.delimitString(salesQuoteHoldOvBean.getInventoryGroup()) + "," +
			SqlHandler.delimitString(salesQuoteHoldOvBean.getInventoryGroupName()) + "," +
			SqlHandler.delimitString(salesQuoteHoldOvBean.getShipToAddressLine1()) + "," +
			SqlHandler.delimitString(salesQuoteHoldOvBean.getShipToAddressLine2()) + "," +
			SqlHandler.delimitString(salesQuoteHoldOvBean.getShipToAddressLine3()) + "," +
			SqlHandler.delimitString(salesQuoteHoldOvBean.getShipToAddressLine4()) + "," +
			SqlHandler.delimitString(salesQuoteHoldOvBean.getShipToAddressLine5()) + "," +
			SqlHandler.delimitString(salesQuoteHoldOvBean.getReleaseStatus()) + "," +
			salesQuoteHoldOvBean.getTotalExtendedPrice() + "," +
			salesQuoteHoldOvBean.getCustomerId() + "," +
			SqlHandler.delimitString(salesQuoteHoldOvBean.getCustomerName()) + "," +
			SqlHandler.delimitString(salesQuoteHoldOvBean.getPoNumber()) + "," +
			SqlHandler.delimitString(salesQuoteHoldOvBean.getSubmittedByName()) + "," +
			SqlHandler.delimitString(salesQuoteHoldOvBean.getRequestorName()) + "," +
			salesQuoteHoldOvBean.getAvailableCredit() + "," +
			SqlHandler.delimitString(salesQuoteHoldOvBean.getWithinTerms()) + "," +
			SqlHandler.delimitString(salesQuoteHoldOvBean.getCustomerNote()) + "," +
			SqlHandler.delimitString(salesQuoteHoldOvBean.getInternalNote()) + "," +
			SqlHandler.delimitString(salesQuoteHoldOvBean.getShiptoNote()) + "," +
			SqlHandler.delimitString(salesQuoteHoldOvBean.getSpecialInstructions()) + "," +
			SqlHandler.delimitString(salesQuoteHoldOvBean.getOpsEntityId()) + "," +
			SqlHandler.delimitString(salesQuoteHoldOvBean.getHub()) + "," +
			SqlHandler.delimitString(salesQuoteHoldOvBean.getHubName()) + "," +
			SqlHandler.delimitString(salesQuoteHoldOvBean.getOrderStatus()) + "," +
			DateHandler.getOracleToDateFunction(salesQuoteHoldOvBean.getSubmittedDate()) + "," +
			salesQuoteHoldOvBean.getSubmittedBy() + "," +
			SqlHandler.delimitString(salesQuoteHoldOvBean.getQualityHold()) + "," +
			SqlHandler.delimitString(salesQuoteHoldOvBean.getDisplayStatus()) + "," +
			salesQuoteHoldOvBean.getLine() + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(SalesQuoteHoldOvBean salesQuoteHoldOvBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(salesQuoteHoldOvBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(SalesQuoteHoldOvBean salesQuoteHoldOvBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_PR_NUMBER + "=" +
				StringHandler.nullIfZero(salesQuoteHoldOvBean.getPrNumber()) + "," +
			ATTRIBUTE_INVENTORY_GROUP + "=" +
				SqlHandler.delimitString(salesQuoteHoldOvBean.getInventoryGroup()) + "," +
			ATTRIBUTE_INVENTORY_GROUP_NAME + "=" +
				SqlHandler.delimitString(salesQuoteHoldOvBean.getInventoryGroupName()) + "," +
			ATTRIBUTE_SHIP_TO_ADDRESS_LINE_1 + "=" +
				SqlHandler.delimitString(salesQuoteHoldOvBean.getShipToAddressLine1()) + "," +
			ATTRIBUTE_SHIP_TO_ADDRESS_LINE_2 + "=" +
				SqlHandler.delimitString(salesQuoteHoldOvBean.getShipToAddressLine2()) + "," +
			ATTRIBUTE_SHIP_TO_ADDRESS_LINE_3 + "=" +
				SqlHandler.delimitString(salesQuoteHoldOvBean.getShipToAddressLine3()) + "," +
			ATTRIBUTE_SHIP_TO_ADDRESS_LINE_4 + "=" +
				SqlHandler.delimitString(salesQuoteHoldOvBean.getShipToAddressLine4()) + "," +
			ATTRIBUTE_SHIP_TO_ADDRESS_LINE_5 + "=" +
				SqlHandler.delimitString(salesQuoteHoldOvBean.getShipToAddressLine5()) + "," +
			ATTRIBUTE_RELEASE_STATUS + "=" +
				SqlHandler.delimitString(salesQuoteHoldOvBean.getReleaseStatus()) + "," +
			ATTRIBUTE_TOTAL_EXTENDED_PRICE + "=" +
				StringHandler.nullIfZero(salesQuoteHoldOvBean.getTotalExtendedPrice()) + "," +
			ATTRIBUTE_CUSTOMER_ID + "=" +
				StringHandler.nullIfZero(salesQuoteHoldOvBean.getCustomerId()) + "," +
			ATTRIBUTE_CUSTOMER_NAME + "=" +
				SqlHandler.delimitString(salesQuoteHoldOvBean.getCustomerName()) + "," +
			ATTRIBUTE_PO_NUMBER + "=" +
				SqlHandler.delimitString(salesQuoteHoldOvBean.getPoNumber()) + "," +
			ATTRIBUTE_SUBMITTED_BY_NAME + "=" +
				SqlHandler.delimitString(salesQuoteHoldOvBean.getSubmittedByName()) + "," +
			ATTRIBUTE_REQUESTOR_NAME + "=" +
				SqlHandler.delimitString(salesQuoteHoldOvBean.getRequestorName()) + "," +
			ATTRIBUTE_AVAILABLE_CREDIT + "=" +
				StringHandler.nullIfZero(salesQuoteHoldOvBean.getAvailableCredit()) + "," +
			ATTRIBUTE_WITHIN_TERMS + "=" +
				SqlHandler.delimitString(salesQuoteHoldOvBean.getWithinTerms()) + "," +
			ATTRIBUTE_CUSTOMER_NOTE + "=" +
				SqlHandler.delimitString(salesQuoteHoldOvBean.getCustomerNote()) + "," +
			ATTRIBUTE_INTERNAL_NOTE + "=" +
				SqlHandler.delimitString(salesQuoteHoldOvBean.getInternalNote()) + "," +
			ATTRIBUTE_SHIPTO_NOTE + "=" +
				SqlHandler.delimitString(salesQuoteHoldOvBean.getShiptoNote()) + "," +
			ATTRIBUTE_SPECIAL_INSTRUCTIONS + "=" +
				SqlHandler.delimitString(salesQuoteHoldOvBean.getSpecialInstructions()) + "," +
			ATTRIBUTE_OPS_ENTITY_ID + "=" +
				SqlHandler.delimitString(salesQuoteHoldOvBean.getOpsEntityId()) + "," +
			ATTRIBUTE_HUB + "=" +
				SqlHandler.delimitString(salesQuoteHoldOvBean.getHub()) + "," +
			ATTRIBUTE_HUB_NAME + "=" +
				SqlHandler.delimitString(salesQuoteHoldOvBean.getHubName()) + "," +
			ATTRIBUTE_ORDER_STATUS + "=" +
				SqlHandler.delimitString(salesQuoteHoldOvBean.getOrderStatus()) + "," +
			ATTRIBUTE_SUBMITTED_DATE + "=" +
				DateHandler.getOracleToDateFunction(salesQuoteHoldOvBean.getSubmittedDate()) + "," +
			ATTRIBUTE_SUBMITTED_BY + "=" +
				StringHandler.nullIfZero(salesQuoteHoldOvBean.getSubmittedBy()) + "," +
			ATTRIBUTE_QUALITY_HOLD + "=" +
				SqlHandler.delimitString(salesQuoteHoldOvBean.getQualityHold()) + "," +
			ATTRIBUTE_DISPLAY_STATUS + "=" +
				SqlHandler.delimitString(salesQuoteHoldOvBean.getDisplayStatus()) + "," +
			ATTRIBUTE_LINE + "=" +
				StringHandler.nullIfZero(salesQuoteHoldOvBean.getLine()) + " " +
			"where " + ATTRIBUTE_PR_NUMBER + "=" +
				salesQuoteHoldOvBean.getPrNumber();

		return new SqlManager().update(conn, query);
	}
	 */

	//select
	public Collection select(SearchCriteria criteria)
	throws BaseException {

		return select(criteria,null);

	}
	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria)
	throws BaseException {

		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = select(criteria,sortCriteria, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}
	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn)
	throws BaseException {

		Collection salesQuoteHoldOvBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
		getWhereClause(criteria)+ getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			SalesQuoteHoldOvBean salesQuoteHoldOvBean = new SalesQuoteHoldOvBean();
			load(dataSetRow, salesQuoteHoldOvBean);
			salesQuoteHoldOvBeanColl.add(salesQuoteHoldOvBean);
		}

		return salesQuoteHoldOvBeanColl;
	}

	public Collection selectObject(SearchCriteria criteria) throws BaseException {
		return selectObject(criteria,null,"");
	}

	public Collection selectObject(SearchCriteria criteria, SortCriteria sortCriteria, String additionalWhere) throws BaseException {
		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			java.util.Map map = connection.getTypeMap();
			map.put("TCM_OPS.SALES_QUOTE_LINE_OBJ",
					Class.forName(
					"com.tcmis.internal.distribution.beans.SalesQuoteLineObjBean"));
			map.put("TCM_OPS.SALES_QUOTE_OBJ",
					Class.forName(
					"com.tcmis.internal.distribution.beans.SalesQuoteHoldOvBean"));

			c = selectObject(criteria, sortCriteria,connection,additionalWhere);
		}
		catch (Exception e) {
			log.error("selectObject error:" + e.getMessage());
			DbSelectException ex = new DbSelectException("error.db.select");
			ex.setRootCause(e);
			throw ex;
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}

	public Collection selectObject(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn, String additionalWhere) throws
	BaseException {
		Collection salesQuoteHoldOvBeanColl = new Vector();

		String query = "select value(p) from " + TABLE + " p " +getWhereClause(criteria);
		if (additionalWhere.length() > 0) {
			query += additionalWhere+" ";
		}
		query += getOrderByClause(sortCriteria);
		if (log.isDebugEnabled()) {
			log.debug("QUERY:" + query);
		}
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				SalesQuoteHoldOvBean b = (SalesQuoteHoldOvBean) rs.getObject(1);
				salesQuoteHoldOvBeanColl.add(b);
			}
			rs.close();
			st.close();
		}
		catch (SQLException e) {
			log.error("selectObject error:" + e.getMessage());
			DbSelectException ex = new DbSelectException("error.db.select");
			ex.setRootCause(e);
			throw ex;
		}
		return salesQuoteHoldOvBeanColl;
	}

}