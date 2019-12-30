package com.tcmis.supplier.wbuy.factory;


import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.supplier.wbuy.beans.ProblemRejectionWbuyViewBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;


/******************************************************************************
 * CLASSNAME: ProblemRejectionWbuyViewBeanFactory <br>
 * @version: 1.0, Jul 1, 2005 <br>
 *****************************************************************************/


public class ProblemRejectionWbuyViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_RADIAN_PO = "RADIAN_PO";
	public String ATTRIBUTE_DBUY_USER_ID = "DBUY_USER_ID";
	public String ATTRIBUTE_COMMENTS = "COMMENTS";
	public String ATTRIBUTE_STATUS_DATE = "STATUS_DATE";
	public String ATTRIBUTE_PROMISED_DATE = "PROMISED_DATE";
	public String ATTRIBUTE_VENDOR_SHIP_DATE = "VENDOR_SHIP_DATE";
	public String ATTRIBUTE_SUPPLIER = "SUPPLIER";
	public String ATTRIBUTE_SUPPLIER_NAME = "SUPPLIER_NAME";
	public String ATTRIBUTE_SHIP_TO_COMPANY_ID = "SHIP_TO_COMPANY_ID";
	public String ATTRIBUTE_SHIP_TO_LOCATION_ID = "SHIP_TO_LOCATION_ID";
	public String ATTRIBUTE_DATE_CREATED = "DATE_CREATED";
	public String ATTRIBUTE_CRITICAL = "CRITICAL";
	public String ATTRIBUTE_FREIGHT_ON_BOARD = "FREIGHT_ON_BOARD";
	public String ATTRIBUTE_CARRIER = "CARRIER";
	public String ATTRIBUTE_PAYMENT_TERMS = "PAYMENT_TERMS";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_CONSIGNED_PO = "CONSIGNED_PO";
	public String ATTRIBUTE_DBUY_STATUS = "DBUY_STATUS";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_UNIT_PRICE = "UNIT_PRICE";
	public String ATTRIBUTE_QUANTITY = "QUANTITY";
	public String ATTRIBUTE_USER_NAME = "USER_NAME";

	//table name
	public String TABLE = "PROBLEM_REJECTION_WBUY_VIEW";


	//constructor
	public ProblemRejectionWbuyViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("radianPo")) {
			return ATTRIBUTE_RADIAN_PO;
		}
		else if(attributeName.equals("dbuyUserId")) {
			return ATTRIBUTE_DBUY_USER_ID;
		}
		else if(attributeName.equals("comments")) {
			return ATTRIBUTE_COMMENTS;
		}
		else if(attributeName.equals("statusDate")) {
			return ATTRIBUTE_STATUS_DATE;
		}
		else if(attributeName.equals("promisedDate")) {
			return ATTRIBUTE_PROMISED_DATE;
		}
		else if(attributeName.equals("vendorShipDate")) {
			return ATTRIBUTE_VENDOR_SHIP_DATE;
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
		else if(attributeName.equals("freightOnBoard")) {
			return ATTRIBUTE_FREIGHT_ON_BOARD;
		}
		else if(attributeName.equals("carrier")) {
			return ATTRIBUTE_CARRIER;
		}
		else if(attributeName.equals("paymentTerms")) {
			return ATTRIBUTE_PAYMENT_TERMS;
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
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("unitPrice")) {
			return ATTRIBUTE_UNIT_PRICE;
		}
		else if(attributeName.equals("quantity")) {
			return ATTRIBUTE_QUANTITY;
		}
		else if(attributeName.equals("userName")) {
			return ATTRIBUTE_USER_NAME;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, ProblemRejectionWbuyViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(ProblemRejectionWbuyViewBean problemRejectionWbuyViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("radianPo", "SearchCriterion.EQUALS",
			"" + problemRejectionWbuyViewBean.getRadianPo());

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


	public int delete(ProblemRejectionWbuyViewBean problemRejectionWbuyViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("radianPo", "SearchCriterion.EQUALS",
			"" + problemRejectionWbuyViewBean.getRadianPo());

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
	public int insert(ProblemRejectionWbuyViewBean problemRejectionWbuyViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(problemRejectionWbuyViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(ProblemRejectionWbuyViewBean problemRejectionWbuyViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_RADIAN_PO + "," +
			ATTRIBUTE_DBUY_USER_ID + "," +
			ATTRIBUTE_COMMENTS + "," +
			ATTRIBUTE_STATUS_DATE + "," +
			ATTRIBUTE_PROMISED_DATE + "," +
			ATTRIBUTE_VENDOR_SHIP_DATE + "," +
			ATTRIBUTE_SUPPLIER + "," +
			ATTRIBUTE_SUPPLIER_NAME + "," +
			ATTRIBUTE_SHIP_TO_COMPANY_ID + "," +
			ATTRIBUTE_SHIP_TO_LOCATION_ID + "," +
			ATTRIBUTE_DATE_CREATED + "," +
			ATTRIBUTE_CRITICAL + "," +
			ATTRIBUTE_FREIGHT_ON_BOARD + "," +
			ATTRIBUTE_CARRIER + "," +
			ATTRIBUTE_PAYMENT_TERMS + "," +
			ATTRIBUTE_INVENTORY_GROUP + "," +
			ATTRIBUTE_CONSIGNED_PO + "," +
			ATTRIBUTE_DBUY_STATUS + "," +
			ATTRIBUTE_ITEM_ID + "," +
			ATTRIBUTE_UNIT_PRICE + "," +
			ATTRIBUTE_QUANTITY + "," +
			ATTRIBUTE_USER_NAME + ")" +
 values (
			StringHandler.nullIfZero(problemRejectionWbuyViewBean.getRadianPo()) + "," +
			StringHandler.nullIfZero(problemRejectionWbuyViewBean.getDbuyUserId()) + "," +
			SqlHandler.delimitString(problemRejectionWbuyViewBean.getComments()) + "," +
			DateHandler.getOracleToDateFunction(problemRejectionWbuyViewBean.getStatusDate()) + "," +
			DateHandler.getOracleToDateFunction(problemRejectionWbuyViewBean.getPromisedDate()) + "," +
			DateHandler.getOracleToDateFunction(problemRejectionWbuyViewBean.getVendorShipDate()) + "," +
			SqlHandler.delimitString(problemRejectionWbuyViewBean.getSupplier()) + "," +
			SqlHandler.delimitString(problemRejectionWbuyViewBean.getSupplierName()) + "," +
			SqlHandler.delimitString(problemRejectionWbuyViewBean.getShipToCompanyId()) + "," +
			SqlHandler.delimitString(problemRejectionWbuyViewBean.getShipToLocationId()) + "," +
			DateHandler.getOracleToDateFunction(problemRejectionWbuyViewBean.getDateCreated()) + "," +
			SqlHandler.delimitString(problemRejectionWbuyViewBean.getCritical()) + "," +
			SqlHandler.delimitString(problemRejectionWbuyViewBean.getFreightOnBoard()) + "," +
			SqlHandler.delimitString(problemRejectionWbuyViewBean.getCarrier()) + "," +
			SqlHandler.delimitString(problemRejectionWbuyViewBean.getPaymentTerms()) + "," +
			SqlHandler.delimitString(problemRejectionWbuyViewBean.getInventoryGroup()) + "," +
			SqlHandler.delimitString(problemRejectionWbuyViewBean.getConsignedPo()) + "," +
			SqlHandler.delimitString(problemRejectionWbuyViewBean.getDbuyStatus()) + "," +
			StringHandler.nullIfZero(problemRejectionWbuyViewBean.getItemId()) + "," +
			StringHandler.nullIfZero(problemRejectionWbuyViewBean.getUnitPrice()) + "," +
			StringHandler.nullIfZero(problemRejectionWbuyViewBean.getQuantity()) + "," +
			SqlHandler.delimitString(problemRejectionWbuyViewBean.getUserName()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(ProblemRejectionWbuyViewBean problemRejectionWbuyViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(problemRejectionWbuyViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(ProblemRejectionWbuyViewBean problemRejectionWbuyViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_RADIAN_PO + "=" + 
				StringHandler.nullIfZero(problemRejectionWbuyViewBean.getRadianPo()) + "," +
			ATTRIBUTE_DBUY_USER_ID + "=" + 
				StringHandler.nullIfZero(problemRejectionWbuyViewBean.getDbuyUserId()) + "," +
			ATTRIBUTE_COMMENTS + "=" + 
				SqlHandler.delimitString(problemRejectionWbuyViewBean.getComments()) + "," +
			ATTRIBUTE_STATUS_DATE + "=" + 
				DateHandler.getOracleToDateFunction(problemRejectionWbuyViewBean.getStatusDate()) + "," +
			ATTRIBUTE_PROMISED_DATE + "=" + 
				DateHandler.getOracleToDateFunction(problemRejectionWbuyViewBean.getPromisedDate()) + "," +
			ATTRIBUTE_VENDOR_SHIP_DATE + "=" + 
				DateHandler.getOracleToDateFunction(problemRejectionWbuyViewBean.getVendorShipDate()) + "," +
			ATTRIBUTE_SUPPLIER + "=" + 
				SqlHandler.delimitString(problemRejectionWbuyViewBean.getSupplier()) + "," +
			ATTRIBUTE_SUPPLIER_NAME + "=" + 
				SqlHandler.delimitString(problemRejectionWbuyViewBean.getSupplierName()) + "," +
			ATTRIBUTE_SHIP_TO_COMPANY_ID + "=" + 
				SqlHandler.delimitString(problemRejectionWbuyViewBean.getShipToCompanyId()) + "," +
			ATTRIBUTE_SHIP_TO_LOCATION_ID + "=" + 
				SqlHandler.delimitString(problemRejectionWbuyViewBean.getShipToLocationId()) + "," +
			ATTRIBUTE_DATE_CREATED + "=" + 
				DateHandler.getOracleToDateFunction(problemRejectionWbuyViewBean.getDateCreated()) + "," +
			ATTRIBUTE_CRITICAL + "=" + 
				SqlHandler.delimitString(problemRejectionWbuyViewBean.getCritical()) + "," +
			ATTRIBUTE_FREIGHT_ON_BOARD + "=" + 
				SqlHandler.delimitString(problemRejectionWbuyViewBean.getFreightOnBoard()) + "," +
			ATTRIBUTE_CARRIER + "=" + 
				SqlHandler.delimitString(problemRejectionWbuyViewBean.getCarrier()) + "," +
			ATTRIBUTE_PAYMENT_TERMS + "=" + 
				SqlHandler.delimitString(problemRejectionWbuyViewBean.getPaymentTerms()) + "," +
			ATTRIBUTE_INVENTORY_GROUP + "=" + 
				SqlHandler.delimitString(problemRejectionWbuyViewBean.getInventoryGroup()) + "," +
			ATTRIBUTE_CONSIGNED_PO + "=" + 
				SqlHandler.delimitString(problemRejectionWbuyViewBean.getConsignedPo()) + "," +
			ATTRIBUTE_DBUY_STATUS + "=" + 
				SqlHandler.delimitString(problemRejectionWbuyViewBean.getDbuyStatus()) + "," +
			ATTRIBUTE_ITEM_ID + "=" + 
				StringHandler.nullIfZero(problemRejectionWbuyViewBean.getItemId()) + "," +
			ATTRIBUTE_UNIT_PRICE + "=" + 
				StringHandler.nullIfZero(problemRejectionWbuyViewBean.getUnitPrice()) + "," +
			ATTRIBUTE_QUANTITY + "=" + 
				StringHandler.nullIfZero(problemRejectionWbuyViewBean.getQuantity()) + "," +
			ATTRIBUTE_USER_NAME + "=" + 
				SqlHandler.delimitString(problemRejectionWbuyViewBean.getUserName()) + " " +
			"where " + ATTRIBUTE_RADIAN_PO + "=" +
				StringHandler.nullIfZero(problemRejectionWbuyViewBean.getRadianPo());

		return new SqlManager().update(conn, query);
	}
*/

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

		Collection problemRejectionWbuyViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			ProblemRejectionWbuyViewBean problemRejectionWbuyViewBean = new ProblemRejectionWbuyViewBean();
			load(dataSetRow, problemRejectionWbuyViewBean);
			problemRejectionWbuyViewBeanColl.add(problemRejectionWbuyViewBean);
		}

		return problemRejectionWbuyViewBeanColl;
	}
        
	public Collection select(String conditions)
		throws BaseException {

		Connection connection = null;
		Collection problemRejectionWbuyViewBeanColl = new Vector();

        String query = "select * from " + TABLE + " where " + conditions;

        try {
            connection = this.getDbManager().getConnection();
 
			DataSet dataSet = new SqlManager().select(connection, query);
	
			Iterator dataIter = dataSet.iterator();
	
			while (dataIter.hasNext()) {
				DataSetRow dataSetRow = (DataSetRow)dataIter.next();
				ProblemRejectionWbuyViewBean problemRejectionWbuyViewBean = new ProblemRejectionWbuyViewBean();
				load(dataSetRow, problemRejectionWbuyViewBean);
				problemRejectionWbuyViewBeanColl.add(problemRejectionWbuyViewBean);
			}
        } finally {
        	this.getDbManager().returnConnection(connection);
		}
	                
		return problemRejectionWbuyViewBeanColl;
	}
        
}