package com.tcmis.supplier.wbuy.factory;


import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.supplier.wbuy.beans.WbuyStatusViewBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;


/******************************************************************************
 * CLASSNAME: WbuyStatusViewBeanFactory <br>
 * @version: 1.0, Mar 17, 2009 <br>
 *****************************************************************************/


/**
 * Change History
 * --------------
 * 03/23/09 - Shahzad Butt - Recreated the factory to bring search results 							 
 */

public class WbuyStatusViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
	public String ATTRIBUTE_RADIAN_PO = "RADIAN_PO";
	public String ATTRIBUTE_DATE_ACKNOWLEDGEMENT = "DATE_ACKNOWLEDGEMENT";
	public String ATTRIBUTE_DATE_SENT = "DATE_SENT";
	public String ATTRIBUTE_DATE_CONFIRMED = "DATE_CONFIRMED";
	public String ATTRIBUTE_PROMISED_DATE = "PROMISED_DATE";
	public String ATTRIBUTE_VENDOR_SHIP_DATE = "VENDOR_SHIP_DATE";
	public String ATTRIBUTE_TRANSACTOR_MAIL_BOX_ADDRESS = "TRANSACTOR_MAIL_BOX_ADDRESS";
	public String ATTRIBUTE_TRANSACTOR_ID = "TRANSACTOR_ID";
	public String ATTRIBUTE_SUPPLIER = "SUPPLIER";
	public String ATTRIBUTE_SUPPLIER_NAME = "SUPPLIER_NAME";
	public String ATTRIBUTE_SHIP_TO_COMPANY_ID = "SHIP_TO_COMPANY_ID";
	public String ATTRIBUTE_SHIP_TO_LOCATION_ID = "SHIP_TO_LOCATION_ID";
	public String ATTRIBUTE_DATE_CREATED = "DATE_CREATED";
	public String ATTRIBUTE_CRITICAL = "CRITICAL";
	public String ATTRIBUTE_BPO = "BPO";
	public String ATTRIBUTE_SUPPLIER_CONTACT_ID = "SUPPLIER_CONTACT_ID";
	public String ATTRIBUTE_BUYER = "BUYER";
	public String ATTRIBUTE_BRANCH_PLANT = "BRANCH_PLANT";
	public String ATTRIBUTE_FREIGHT_ON_BOARD = "FREIGHT_ON_BOARD";
	public String ATTRIBUTE_CARRIER = "CARRIER";
	public String ATTRIBUTE_TERMS_AND_CONDITIONS = "TERMS_AND_CONDITIONS";
	public String ATTRIBUTE_CUSTOMER_PO = "CUSTOMER_PO";
	public String ATTRIBUTE_PAYMENT_TERMS = "PAYMENT_TERMS";
	public String ATTRIBUTE_QUALIFICATION_LEVEL = "QUALIFICATION_LEVEL";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_CONSIGNED_PO = "CONSIGNED_PO";
	public String ATTRIBUTE_DBUY_STATUS = "DBUY_STATUS";
	public String ATTRIBUTE_DBUY_STATUS_SET_DATE = "DBUY_STATUS_SET_DATE";
	public String ATTRIBUTE_DBUY_USER_ID = "DBUY_USER_ID";
	public String ATTRIBUTE_DAYS_SINCE_LAST_STATUS = "DAYS_SINCE_LAST_STATUS";
	public String ATTRIBUTE_OPERATING_ENTITY_NAME = "OPERATING_ENTITY_NAME";
	public String ATTRIBUTE_COMMENTS = "COMMENTS";

	//table name
	public String TABLE = "WBUY_STATUS_VIEW";


	//constructor
	public WbuyStatusViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("prNumber")) {
			return ATTRIBUTE_PR_NUMBER;
		}
		else if(attributeName.equals("radianPo")) {
			return ATTRIBUTE_RADIAN_PO;
		}
		else if(attributeName.equals("dateAcknowledgement")) {
			return ATTRIBUTE_DATE_ACKNOWLEDGEMENT;
		}
		else if(attributeName.equals("dateSent")) {
			return ATTRIBUTE_DATE_SENT;
		}
		else if(attributeName.equals("dateConfirmed")) {
			return ATTRIBUTE_DATE_CONFIRMED;
		}
		else if(attributeName.equals("promisedDate")) {
			return ATTRIBUTE_PROMISED_DATE;  
		}
		else if(attributeName.equals("vendorShipDate")) {
			return ATTRIBUTE_VENDOR_SHIP_DATE;
		}
		else if(attributeName.equals("transactorMailBoxAddress")) {
			return ATTRIBUTE_TRANSACTOR_MAIL_BOX_ADDRESS;
		}
		else if(attributeName.equals("transactorId")) {
			return ATTRIBUTE_TRANSACTOR_ID;
		}
		else if(attributeName.equals("supplier")) {
			return ATTRIBUTE_SUPPLIER;
		}
		else if(attributeName.equals("supplierName")) {
			return ATTRIBUTE_SUPPLIER_NAME;
		}
		else if(attributeName.equals("shipToCompanyId")) {
			return ATTRIBUTE_SHIP_TO_COMPANY_ID;
		}
		else if(attributeName.equals("shipToLocationId")) {
			return ATTRIBUTE_SHIP_TO_LOCATION_ID;
		}
		else if(attributeName.equals("dateCreated")) {
			return ATTRIBUTE_DATE_CREATED;
		}
		else if(attributeName.equals("critical")) {
			return ATTRIBUTE_CRITICAL;
		}
		else if(attributeName.equals("bpo")) {
			return ATTRIBUTE_BPO;
		}
		else if(attributeName.equals("supplierContactId")) {
			return ATTRIBUTE_SUPPLIER_CONTACT_ID;
		}
		else if(attributeName.equals("buyer")) {
			return ATTRIBUTE_BUYER;
		}
		else if(attributeName.equals("branchPlant")) {
			return ATTRIBUTE_BRANCH_PLANT;
		}
		else if(attributeName.equals("freightOnBoard")) {
			return ATTRIBUTE_FREIGHT_ON_BOARD;
		}
		else if(attributeName.equals("carrier")) {
			return ATTRIBUTE_CARRIER;
		}
		else if(attributeName.equals("termsAndConditions")) {
			return ATTRIBUTE_TERMS_AND_CONDITIONS;
		}
		else if(attributeName.equals("customerPo")) {
			return ATTRIBUTE_CUSTOMER_PO;
		}
		else if(attributeName.equals("paymentTerms")) {
			return ATTRIBUTE_PAYMENT_TERMS;
		}
		else if(attributeName.equals("qualificationLevel")) {
			return ATTRIBUTE_QUALIFICATION_LEVEL;
		}
		else if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("consignedPo")) {
			return ATTRIBUTE_CONSIGNED_PO;
		}
		else if(attributeName.equals("dbuyStatus")) {
			return ATTRIBUTE_DBUY_STATUS;
		}
		else if(attributeName.equals("dbuyStatusSetDate")) {
			return ATTRIBUTE_DBUY_STATUS_SET_DATE;
		}
		else if(attributeName.equals("dbuyUserId")) {
			return ATTRIBUTE_DBUY_USER_ID;
		}
		else if(attributeName.equals("daysSinceLastStatus")) {
			return ATTRIBUTE_DAYS_SINCE_LAST_STATUS;
		}
		else if(attributeName.equals("operatingEntityName")) {
			return ATTRIBUTE_OPERATING_ENTITY_NAME;
		}
		else if(attributeName.equals("comments")) {
			return ATTRIBUTE_COMMENTS;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, WbuyStatusViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(WbuyStatusViewBean wbuyStatusViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("prNumber", "SearchCriterion.EQUALS",
			"" + wbuyStatusViewBean.getPrNumber());

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


	public int delete(WbuyStatusViewBean wbuyStatusViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("prNumber", "SearchCriterion.EQUALS",
			"" + wbuyStatusViewBean.getPrNumber());

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
	public int insert(WbuyStatusViewBean wbuyStatusViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(wbuyStatusViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(WbuyStatusViewBean wbuyStatusViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_PR_NUMBER + "," +
			ATTRIBUTE_RADIAN_PO + "," +
			ATTRIBUTE_DATE_ACKNOWLEDGEMENT + "," +
			ATTRIBUTE_DATE_SENT + "," +
			ATTRIBUTE_DATE_CONFIRMED + "," +
			ATTRIBUTE_PROMISED_DATE + "," +
			ATTRIBUTE_VENDOR_SHIP_DATE + "," +
			ATTRIBUTE_TRANSACTOR_MAIL_BOX_ADDRESS + "," +
			ATTRIBUTE_TRANSACTOR_ID + "," +
			ATTRIBUTE_SUPPLIER + "," +
			ATTRIBUTE_SUPPLIER_NAME + "," +
			ATTRIBUTE_SHIP_TO_COMPANY_ID + "," +
			ATTRIBUTE_SHIP_TO_LOCATION_ID + "," +
			ATTRIBUTE_DATE_CREATED + "," +
			ATTRIBUTE_CRITICAL + "," +
			ATTRIBUTE_BPO + "," +
			ATTRIBUTE_SUPPLIER_CONTACT_ID + "," +
			ATTRIBUTE_BUYER + "," +
			ATTRIBUTE_BRANCH_PLANT + "," +
			ATTRIBUTE_FREIGHT_ON_BOARD + "," +
			ATTRIBUTE_CARRIER + "," +
			ATTRIBUTE_TERMS_AND_CONDITIONS + "," +
			ATTRIBUTE_CUSTOMER_PO + "," +
			ATTRIBUTE_PAYMENT_TERMS + "," +
			ATTRIBUTE_QUALIFICATION_LEVEL + "," +
			ATTRIBUTE_INVENTORY_GROUP + "," +
			ATTRIBUTE_CONSIGNED_PO + "," +
			ATTRIBUTE_DBUY_STATUS + "," +
			ATTRIBUTE_DBUY_STATUS_SET_DATE + "," +
			ATTRIBUTE_DBUY_USER_ID + "," +
			ATTRIBUTE_DAYS_SINCE_LAST_STATUS + "," +
			ATTRIBUTE_OPERATING_ENTITY_NAME + "," +
			ATTRIBUTE_COMMENTS + ")" +
			" values (" +
			wbuyStatusViewBean.getPrNumber() + "," +
			wbuyStatusViewBean.getRadianPo() + "," +
			DateHandler.getOracleToDateFunction(wbuyStatusViewBean.getDateAcknowledgement()) + "," +
			DateHandler.getOracleToDateFunction(wbuyStatusViewBean.getDateSent()) + "," +
			DateHandler.getOracleToDateFunction(wbuyStatusViewBean.getDateConfirmed()) + "," +
			DateHandler.getOracleToDateFunction(wbuyStatusViewBean.getPromisedDate()) + "," +
			DateHandler.getOracleToDateFunction(wbuyStatusViewBean.getVendorShipDate()) + "," +
			SqlHandler.delimitString(wbuyStatusViewBean.getTransactorMailBoxAddress()) + "," +
			SqlHandler.delimitString(wbuyStatusViewBean.getTransactorId()) + "," +
			SqlHandler.delimitString(wbuyStatusViewBean.getSupplier()) + "," +
			SqlHandler.delimitString(wbuyStatusViewBean.getSupplierName()) + "," +
			SqlHandler.delimitString(wbuyStatusViewBean.getShipToCompanyId()) + "," +
			SqlHandler.delimitString(wbuyStatusViewBean.getShipToLocationId()) + "," +
			DateHandler.getOracleToDateFunction(wbuyStatusViewBean.getDateCreated()) + "," +
			SqlHandler.delimitString(wbuyStatusViewBean.getCritical()) + "," +
			wbuyStatusViewBean.getBpo() + "," +
			wbuyStatusViewBean.getSupplierContactId() + "," +
			wbuyStatusViewBean.getBuyer() + "," +
			SqlHandler.delimitString(wbuyStatusViewBean.getBranchPlant()) + "," +
			SqlHandler.delimitString(wbuyStatusViewBean.getFreightOnBoard()) + "," +
			SqlHandler.delimitString(wbuyStatusViewBean.getCarrier()) + "," +
			SqlHandler.delimitString(wbuyStatusViewBean.getTermsAndConditions()) + "," +
			SqlHandler.delimitString(wbuyStatusViewBean.getCustomerPo()) + "," +
			SqlHandler.delimitString(wbuyStatusViewBean.getPaymentTerms()) + "," +
			SqlHandler.delimitString(wbuyStatusViewBean.getQualificationLevel()) + "," +
			SqlHandler.delimitString(wbuyStatusViewBean.getInventoryGroup()) + "," +
			SqlHandler.delimitString(wbuyStatusViewBean.getConsignedPo()) + "," +
			SqlHandler.delimitString(wbuyStatusViewBean.getDbuyStatus()) + "," +
			DateHandler.getOracleToDateFunction(wbuyStatusViewBean.getDbuyStatusSetDate()) + "," +
			wbuyStatusViewBean.getDbuyUserId() + "," +
			wbuyStatusViewBean.getDaysSinceLastStatus() + "," +
			SqlHandler.delimitString(wbuyStatusViewBean.getOperatingEntityName()) + "," +
			SqlHandler.delimitString(wbuyStatusViewBean.getComments()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(WbuyStatusViewBean wbuyStatusViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(wbuyStatusViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(WbuyStatusViewBean wbuyStatusViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_PR_NUMBER + "=" + 
				StringHandler.nullIfZero(wbuyStatusViewBean.getPrNumber()) + "," +
			ATTRIBUTE_RADIAN_PO + "=" + 
				StringHandler.nullIfZero(wbuyStatusViewBean.getRadianPo()) + "," +
			ATTRIBUTE_DATE_ACKNOWLEDGEMENT + "=" + 
				DateHandler.getOracleToDateFunction(wbuyStatusViewBean.getDateAcknowledgement()) + "," +
			ATTRIBUTE_DATE_SENT + "=" + 
				DateHandler.getOracleToDateFunction(wbuyStatusViewBean.getDateSent()) + "," +
			ATTRIBUTE_DATE_CONFIRMED + "=" + 
				DateHandler.getOracleToDateFunction(wbuyStatusViewBean.getDateConfirmed()) + "," +
			ATTRIBUTE_PROMISED_DATE + "=" + 
				DateHandler.getOracleToDateFunction(wbuyStatusViewBean.getPromisedDate()) + "," +
			ATTRIBUTE_VENDOR_SHIP_DATE + "=" + 
				DateHandler.getOracleToDateFunction(wbuyStatusViewBean.getVendorShipDate()) + "," +
			ATTRIBUTE_TRANSACTOR_MAIL_BOX_ADDRESS + "=" + 
				SqlHandler.delimitString(wbuyStatusViewBean.getTransactorMailBoxAddress()) + "," +
			ATTRIBUTE_TRANSACTOR_ID + "=" + 
				SqlHandler.delimitString(wbuyStatusViewBean.getTransactorId()) + "," +
			ATTRIBUTE_SUPPLIER + "=" + 
				SqlHandler.delimitString(wbuyStatusViewBean.getSupplier()) + "," +
			ATTRIBUTE_SUPPLIER_NAME + "=" + 
				SqlHandler.delimitString(wbuyStatusViewBean.getSupplierName()) + "," +
			ATTRIBUTE_SHIP_TO_COMPANY_ID + "=" + 
				SqlHandler.delimitString(wbuyStatusViewBean.getShipToCompanyId()) + "," +
			ATTRIBUTE_SHIP_TO_LOCATION_ID + "=" + 
				SqlHandler.delimitString(wbuyStatusViewBean.getShipToLocationId()) + "," +
			ATTRIBUTE_DATE_CREATED + "=" + 
				DateHandler.getOracleToDateFunction(wbuyStatusViewBean.getDateCreated()) + "," +
			ATTRIBUTE_CRITICAL + "=" + 
				SqlHandler.delimitString(wbuyStatusViewBean.getCritical()) + "," +
			ATTRIBUTE_BPO + "=" + 
				StringHandler.nullIfZero(wbuyStatusViewBean.getBpo()) + "," +
			ATTRIBUTE_SUPPLIER_CONTACT_ID + "=" + 
				StringHandler.nullIfZero(wbuyStatusViewBean.getSupplierContactId()) + "," +
			ATTRIBUTE_BUYER + "=" + 
				StringHandler.nullIfZero(wbuyStatusViewBean.getBuyer()) + "," +
			ATTRIBUTE_BRANCH_PLANT + "=" + 
				SqlHandler.delimitString(wbuyStatusViewBean.getBranchPlant()) + "," +
			ATTRIBUTE_FREIGHT_ON_BOARD + "=" + 
				SqlHandler.delimitString(wbuyStatusViewBean.getFreightOnBoard()) + "," +
			ATTRIBUTE_CARRIER + "=" + 
				SqlHandler.delimitString(wbuyStatusViewBean.getCarrier()) + "," +
			ATTRIBUTE_TERMS_AND_CONDITIONS + "=" + 
				SqlHandler.delimitString(wbuyStatusViewBean.getTermsAndConditions()) + "," +
			ATTRIBUTE_CUSTOMER_PO + "=" + 
				SqlHandler.delimitString(wbuyStatusViewBean.getCustomerPo()) + "," +
			ATTRIBUTE_PAYMENT_TERMS + "=" + 
				SqlHandler.delimitString(wbuyStatusViewBean.getPaymentTerms()) + "," +
			ATTRIBUTE_QUALIFICATION_LEVEL + "=" + 
				SqlHandler.delimitString(wbuyStatusViewBean.getQualificationLevel()) + "," +
			ATTRIBUTE_INVENTORY_GROUP + "=" + 
				SqlHandler.delimitString(wbuyStatusViewBean.getInventoryGroup()) + "," +
			ATTRIBUTE_CONSIGNED_PO + "=" + 
				SqlHandler.delimitString(wbuyStatusViewBean.getConsignedPo()) + "," +
			ATTRIBUTE_DBUY_STATUS + "=" + 
				SqlHandler.delimitString(wbuyStatusViewBean.getDbuyStatus()) + "," +
			ATTRIBUTE_DBUY_STATUS_SET_DATE + "=" + 
				DateHandler.getOracleToDateFunction(wbuyStatusViewBean.getDbuyStatusSetDate()) + "," +
			ATTRIBUTE_DBUY_USER_ID + "=" + 
				StringHandler.nullIfZero(wbuyStatusViewBean.getDbuyUserId()) + "," +
			ATTRIBUTE_DAYS_SINCE_LAST_STATUS + "=" + 
				StringHandler.nullIfZero(wbuyStatusViewBean.getDaysSinceLastStatus()) + "," +
			ATTRIBUTE_OPERATING_ENTITY_NAME + "=" +
				SqlHandler.delimitString(wbuyStatusViewBean.getOperatingEntityName()) + "," +
			ATTRIBUTE_COMMENTS + "=" + 
				SqlHandler.delimitString(wbuyStatusViewBean.getComments()) + " " +
			"where " + ATTRIBUTE_PR_NUMBER + "=" +
				wbuyStatusViewBean.getPrNumber();

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

		Collection wbuyStatusViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			WbuyStatusViewBean wbuyStatusViewBean = new WbuyStatusViewBean();
			load(dataSetRow, wbuyStatusViewBean);
			wbuyStatusViewBeanColl.add(wbuyStatusViewBean);
		}

		return wbuyStatusViewBeanColl;
	}
	
	public Collection select(SearchCriteria criteria)
	throws BaseException {

	Connection connection = null;
	Collection c = null;
	try {
		connection = this.getDbManager().getConnection();
		c = select(criteria, null, connection);
	}
	finally {
		this.getDbManager().returnConnection(connection);
	}
	return c;
}
	
	public Collection select(String conditions)
	throws BaseException {

	Connection connection = null;
	Collection wbuyStatusViewBeanColl = new Vector();

    String query = "select * from " + TABLE + " where " + conditions;
    
    try {
        connection = this.getDbManager().getConnection();
	
		DataSet dataSet = new SqlManager().select(connection, query);
	
		Iterator dataIter = dataSet.iterator();
	
		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			WbuyStatusViewBean wbuyStatusViewBean = new WbuyStatusViewBean();
			load(dataSetRow, wbuyStatusViewBean);
			wbuyStatusViewBeanColl.add(wbuyStatusViewBean);
		}
    } finally {
		this.getDbManager().returnConnection(connection);
	}
            
	return wbuyStatusViewBeanColl;
}
	
	
}