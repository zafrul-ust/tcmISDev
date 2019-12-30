package com.tcmis.supplier.dbuy.factory;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

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
import com.tcmis.supplier.dbuy.beans.DbuyAcknowledgementBean;


/******************************************************************************
 * CLASSNAME: DbuyAcknowledgementBeanFactory <br>
 * @version: 1.0, Nov 16, 2005 <br>
 *****************************************************************************/


public class DbuyAcknowledgementBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_RADIAN_PO = "RADIAN_PO";
	public String ATTRIBUTE_PO_LINE = "PO_LINE";
	public String ATTRIBUTE_SUPPLIER_NAME = "SUPPLIER_NAME";
	public String ATTRIBUTE_SHIPTO_COUNTRY_ABBREV = "SHIPTO_COUNTRY_ABBREV";
	public String ATTRIBUTE_SHIPTO_STATE_ABBREV = "SHIPTO_STATE_ABBREV";
	public String ATTRIBUTE_SHIPTO_ADDRESS_LINE_1 = "SHIPTO_ADDRESS_LINE_1";
	public String ATTRIBUTE_SHIPTO_ADDRESS_LINE_2 = "SHIPTO_ADDRESS_LINE_2";
	public String ATTRIBUTE_SHIPTO_ADDRESS_LINE_3 = "SHIPTO_ADDRESS_LINE_3";
	public String ATTRIBUTE_SHIPTO_CITY = "SHIPTO_CITY";
	public String ATTRIBUTE_SHIPTO_ZIP = "SHIPTO_ZIP";
	public String ATTRIBUTE_SUPPLIER_SHIP_TO_CODE = "SUPPLIER_SHIP_TO_CODE";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_ACCEPTED_QUANTITY = "ACCEPTED_QUANTITY";
	public String ATTRIBUTE_ACCEPTED_UOM = "ACCEPTED_UOM";
	public String ATTRIBUTE_ACCEPTED_UNIT_PRICE = "ACCEPTED_UNIT_PRICE";
	public String ATTRIBUTE_SUPPLIER_PART_NO = "SUPPLIER_PART_NO";
	public String ATTRIBUTE_EDI_UOM = "EDI_UOM";
	public String ATTRIBUTE_PROMISED_DATE = "PROMISED_DATE";
	public String ATTRIBUTE_EDI_DOCUMENT_CONTROL_NUMBER = "EDI_DOCUMENT_CONTROL_NUMBER";
	public String ATTRIBUTE_DATE_PO_CREATED = "DATE_PO_CREATED";
	public String ATTRIBUTE_TRANSACTION_DATE = "TRANSACTION_DATE";
	public String ATTRIBUTE_PO_LINE_CONFIRMED_DATE = "PO_LINE_CONFIRMED_DATE";
	public String ATTRIBUTE_SUPPLIER_PART_DESC = "SUPPLIER_PART_DESC";
	public String ATTRIBUTE_EDI_ACKNOWLEDGEMENT_CODE = "EDI_ACKNOWLEDGEMENT_CODE";
	public String ATTRIBUTE_ACCEPTED_EDI_DATE_CODE = "ACCEPTED_EDI_DATE_CODE";
	public String ATTRIBUTE_MATCHED = "MATCHED";
	public String ATTRIBUTE_REVIEWER_ID = "REVIEWER_ID";
	public String ATTRIBUTE_REVIEW_DATE = "REVIEW_DATE";
	public String ATTRIBUTE_REVIEW_COMMENTS = "REVIEW_COMMENTS";
	public String ATTRIBUTE_DBUY_ACKNOWLEDGEMENT_ID = "DBUY_ACKNOWLEDGEMENT_ID";
	public String ATTRIBUTE_ORDER_QUANTITY = "ORDER_QUANTITY";
	public String ATTRIBUTE_MISMATCH_COMMENTS = "MISMATCH_COMMENTS";
	public String ATTRIBUTE_SUPPLIER_SALES_ORDER_NO = "SUPPLIER_SALES_ORDER_NO";
	public String ATTRIBUTE_SUPPLIER_SALES_ORDER_DATE = "SUPPLIER_SALES_ORDER_DATE";
	public String ATTRIBUTE_DONE = "DONE";
	public String ATTRIBUTE_DOCK_DATE = "DOCK_DATE";
	public String ATTRIBUTE_PO_NOTES = "PO_NOTES";
	public String ATTRIBUTE_PO_LINE_NOTES = "PO_LINE_NOTES";
        public String ATTRIBUTE_BATCH_ID = "BATCH_ID";

	//table name
	public String TABLE = "DBUY_ACKNOWLEDGEMENT";


	//constructor
	public DbuyAcknowledgementBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("radianPo")) {
			return ATTRIBUTE_RADIAN_PO;
		}
		else if(attributeName.equals("poLine")) {
			return ATTRIBUTE_PO_LINE;
		}
		else if(attributeName.equals("supplierName")) {
			return ATTRIBUTE_SUPPLIER_NAME;
		}
		else if(attributeName.equals("shiptoCountryAbbrev")) {
			return ATTRIBUTE_SHIPTO_COUNTRY_ABBREV;
		}
		else if(attributeName.equals("shiptoStateAbbrev")) {
			return ATTRIBUTE_SHIPTO_STATE_ABBREV;
		}
		else if(attributeName.equals("shiptoAddressLine1")) {
			return ATTRIBUTE_SHIPTO_ADDRESS_LINE_1;
		}
		else if(attributeName.equals("shiptoAddressLine2")) {
			return ATTRIBUTE_SHIPTO_ADDRESS_LINE_2;
		}
		else if(attributeName.equals("shiptoAddressLine3")) {
			return ATTRIBUTE_SHIPTO_ADDRESS_LINE_3;
		}
		else if(attributeName.equals("shiptoCity")) {
			return ATTRIBUTE_SHIPTO_CITY;
		}
		else if(attributeName.equals("shiptoZip")) {
			return ATTRIBUTE_SHIPTO_ZIP;
		}
		else if(attributeName.equals("supplierShipToCode")) {
			return ATTRIBUTE_SUPPLIER_SHIP_TO_CODE;
		}
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("acceptedQuantity")) {
			return ATTRIBUTE_ACCEPTED_QUANTITY;
		}
		else if(attributeName.equals("acceptedUom")) {
			return ATTRIBUTE_ACCEPTED_UOM;
		}
		else if(attributeName.equals("acceptedUnitPrice")) {
			return ATTRIBUTE_ACCEPTED_UNIT_PRICE;
		}
		else if(attributeName.equals("supplierPartNo")) {
			return ATTRIBUTE_SUPPLIER_PART_NO;
		}
		else if(attributeName.equals("ediUom")) {
			return ATTRIBUTE_EDI_UOM;
		}
		else if(attributeName.equals("promisedDate")) {
			return ATTRIBUTE_PROMISED_DATE;
		}
		else if(attributeName.equals("ediDocumentControlNumber")) {
			return ATTRIBUTE_EDI_DOCUMENT_CONTROL_NUMBER;
		}
		else if(attributeName.equals("datePoCreated")) {
			return ATTRIBUTE_DATE_PO_CREATED;
		}
		else if(attributeName.equals("transactionDate")) {
			return ATTRIBUTE_TRANSACTION_DATE;
		}
		else if(attributeName.equals("poLineConfirmedDate")) {
			return ATTRIBUTE_PO_LINE_CONFIRMED_DATE;
		}
		else if(attributeName.equals("supplierPartDesc")) {
			return ATTRIBUTE_SUPPLIER_PART_DESC;
		}
		else if(attributeName.equals("ediAcknowledgementCode")) {
			return ATTRIBUTE_EDI_ACKNOWLEDGEMENT_CODE;
		}
		else if(attributeName.equals("acceptedEdiDateCode")) {
			return ATTRIBUTE_ACCEPTED_EDI_DATE_CODE;
		}
		else if(attributeName.equals("matched")) {
			return ATTRIBUTE_MATCHED;
		}
		else if(attributeName.equals("reviewerId")) {
			return ATTRIBUTE_REVIEWER_ID;
		}
		else if(attributeName.equals("reviewDate")) {
			return ATTRIBUTE_REVIEW_DATE;
		}
		else if(attributeName.equals("reviewComments")) {
			return ATTRIBUTE_REVIEW_COMMENTS;
		}
		else if(attributeName.equals("dbuyAcknowledgementId")) {
			return ATTRIBUTE_DBUY_ACKNOWLEDGEMENT_ID;
		}
		else if(attributeName.equals("orderQuantity")) {
			return ATTRIBUTE_ORDER_QUANTITY;
		}
		else if(attributeName.equals("mismatchComments")) {
			return ATTRIBUTE_MISMATCH_COMMENTS;
		}
		else if(attributeName.equals("supplierSalesOrderNo")) {
			return ATTRIBUTE_SUPPLIER_SALES_ORDER_NO;
		}
		else if(attributeName.equals("supplierSalesOrderDate")) {
			return ATTRIBUTE_SUPPLIER_SALES_ORDER_DATE;
		}
		else if(attributeName.equals("done")) {
			return ATTRIBUTE_DONE;
		}
		else if(attributeName.equals("dockDate")) {
			return ATTRIBUTE_DOCK_DATE;
		}
		else if(attributeName.equals("poNotes")) {
			return ATTRIBUTE_PO_NOTES;
		}
		else if(attributeName.equals("poLineNotes")) {
			return ATTRIBUTE_PO_LINE_NOTES;
		}
		else if(attributeName.equals("batchId")) {
			return ATTRIBUTE_BATCH_ID;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, DbuyAcknowledgementBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(DbuyAcknowledgementBean dbuyAcknowledgementBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("radianPo", "SearchCriterion.EQUALS",
			"" + dbuyAcknowledgementBean.getRadianPo());

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


	public int delete(DbuyAcknowledgementBean dbuyAcknowledgementBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("radianPo", "SearchCriterion.EQUALS",
			"" + dbuyAcknowledgementBean.getRadianPo());

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
	public int insert(DbuyAcknowledgementBean dbuyAcknowledgementBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(dbuyAcknowledgementBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(DbuyAcknowledgementBean dbuyAcknowledgementBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_RADIAN_PO + "," +
			ATTRIBUTE_PO_LINE + "," +
			ATTRIBUTE_SUPPLIER_NAME + "," +
			ATTRIBUTE_SHIPTO_COUNTRY_ABBREV + "," +
			ATTRIBUTE_SHIPTO_STATE_ABBREV + "," +
			ATTRIBUTE_SHIPTO_ADDRESS_LINE_1 + "," +
			ATTRIBUTE_SHIPTO_ADDRESS_LINE_2 + "," +
			ATTRIBUTE_SHIPTO_ADDRESS_LINE_3 + "," +
			ATTRIBUTE_SHIPTO_CITY + "," +
			ATTRIBUTE_SHIPTO_ZIP + "," +
			ATTRIBUTE_SUPPLIER_SHIP_TO_CODE + "," +
			ATTRIBUTE_ITEM_ID + "," +
			ATTRIBUTE_ACCEPTED_QUANTITY + "," +
			ATTRIBUTE_ACCEPTED_UOM + "," +
			ATTRIBUTE_ACCEPTED_UNIT_PRICE + "," +
			ATTRIBUTE_SUPPLIER_PART_NO + "," +
			ATTRIBUTE_EDI_UOM + "," +
			ATTRIBUTE_PROMISED_DATE + "," +
			ATTRIBUTE_EDI_DOCUMENT_CONTROL_NUMBER + "," +
			ATTRIBUTE_DATE_PO_CREATED + "," +
			ATTRIBUTE_TRANSACTION_DATE + "," +
			ATTRIBUTE_PO_LINE_CONFIRMED_DATE + "," +
			ATTRIBUTE_SUPPLIER_PART_DESC + "," +
			ATTRIBUTE_EDI_ACKNOWLEDGEMENT_CODE + "," +
			ATTRIBUTE_ACCEPTED_EDI_DATE_CODE + "," +
			ATTRIBUTE_MATCHED + "," +
			ATTRIBUTE_REVIEWER_ID + "," +
			ATTRIBUTE_REVIEW_DATE + "," +
			ATTRIBUTE_REVIEW_COMMENTS + "," +
			ATTRIBUTE_DBUY_ACKNOWLEDGEMENT_ID + "," +
			ATTRIBUTE_ORDER_QUANTITY + "," +
			ATTRIBUTE_MISMATCH_COMMENTS + "," +
			ATTRIBUTE_SUPPLIER_SALES_ORDER_NO + "," +
			ATTRIBUTE_SUPPLIER_SALES_ORDER_DATE + "," +
			ATTRIBUTE_DONE + "," +
			ATTRIBUTE_DOCK_DATE + "," +
			ATTRIBUTE_PO_NOTES + "," +
			ATTRIBUTE_PO_LINE_NOTES + ")" +
 values (
			StringHandler.nullIfZero(dbuyAcknowledgementBean.getRadianPo()) + "," +
			StringHandler.nullIfZero(dbuyAcknowledgementBean.getPoLine()) + "," +
			SqlHandler.delimitString(dbuyAcknowledgementBean.getSupplierName()) + "," +
			SqlHandler.delimitString(dbuyAcknowledgementBean.getShiptoCountryAbbrev()) + "," +
			SqlHandler.delimitString(dbuyAcknowledgementBean.getShiptoStateAbbrev()) + "," +
			SqlHandler.delimitString(dbuyAcknowledgementBean.getShiptoAddressLine1()) + "," +
			SqlHandler.delimitString(dbuyAcknowledgementBean.getShiptoAddressLine2()) + "," +
			SqlHandler.delimitString(dbuyAcknowledgementBean.getShiptoAddressLine3()) + "," +
			SqlHandler.delimitString(dbuyAcknowledgementBean.getShiptoCity()) + "," +
			SqlHandler.delimitString(dbuyAcknowledgementBean.getShiptoZip()) + "," +
			SqlHandler.delimitString(dbuyAcknowledgementBean.getSupplierShipToCode()) + "," +
			StringHandler.nullIfZero(dbuyAcknowledgementBean.getItemId()) + "," +
			StringHandler.nullIfZero(dbuyAcknowledgementBean.getAcceptedQuantity()) + "," +
			SqlHandler.delimitString(dbuyAcknowledgementBean.getAcceptedUom()) + "," +
			StringHandler.nullIfZero(dbuyAcknowledgementBean.getAcceptedUnitPrice()) + "," +
			SqlHandler.delimitString(dbuyAcknowledgementBean.getSupplierPartNo()) + "," +
			SqlHandler.delimitString(dbuyAcknowledgementBean.getEdiUom()) + "," +
			DateHandler.getOracleToDateFunction(dbuyAcknowledgementBean.getPromisedDate()) + "," +
			SqlHandler.delimitString(dbuyAcknowledgementBean.getEdiDocumentControlNumber()) + "," +
			DateHandler.getOracleToDateFunction(dbuyAcknowledgementBean.getDatePoCreated()) + "," +
			DateHandler.getOracleToDateFunction(dbuyAcknowledgementBean.getTransactionDate()) + "," +
			DateHandler.getOracleToDateFunction(dbuyAcknowledgementBean.getPoLineConfirmedDate()) + "," +
			SqlHandler.delimitString(dbuyAcknowledgementBean.getSupplierPartDesc()) + "," +
			SqlHandler.delimitString(dbuyAcknowledgementBean.getEdiAcknowledgementCode()) + "," +
			SqlHandler.delimitString(dbuyAcknowledgementBean.getAcceptedEdiDateCode()) + "," +
			SqlHandler.delimitString(dbuyAcknowledgementBean.getMatched()) + "," +
			StringHandler.nullIfZero(dbuyAcknowledgementBean.getReviewerId()) + "," +
			DateHandler.getOracleToDateFunction(dbuyAcknowledgementBean.getReviewDate()) + "," +
			SqlHandler.delimitString(dbuyAcknowledgementBean.getReviewComments()) + "," +
			StringHandler.nullIfZero(dbuyAcknowledgementBean.getDbuyAcknowledgementId()) + "," +
			StringHandler.nullIfZero(dbuyAcknowledgementBean.getOrderQuantity()) + "," +
			SqlHandler.delimitString(dbuyAcknowledgementBean.getMismatchComments()) + "," +
			SqlHandler.delimitString(dbuyAcknowledgementBean.getSupplierSalesOrderNo()) + "," +
			DateHandler.getOracleToDateFunction(dbuyAcknowledgementBean.getSupplierSalesOrderDate()) + "," +
			SqlHandler.delimitString(dbuyAcknowledgementBean.getDone()) + "," +
			DateHandler.getOracleToDateFunction(dbuyAcknowledgementBean.getDockDate()) + "," +
			SqlHandler.delimitString(dbuyAcknowledgementBean.getPoNotes()) + "," +
			SqlHandler.delimitString(dbuyAcknowledgementBean.getPoLineNotes()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(DbuyAcknowledgementBean dbuyAcknowledgementBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(dbuyAcknowledgementBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(DbuyAcknowledgementBean dbuyAcknowledgementBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_RADIAN_PO + "=" + 
				StringHandler.nullIfZero(dbuyAcknowledgementBean.getRadianPo()) + "," +
			ATTRIBUTE_PO_LINE + "=" + 
				StringHandler.nullIfZero(dbuyAcknowledgementBean.getPoLine()) + "," +
			ATTRIBUTE_SUPPLIER_NAME + "=" + 
				SqlHandler.delimitString(dbuyAcknowledgementBean.getSupplierName()) + "," +
			ATTRIBUTE_SHIPTO_COUNTRY_ABBREV + "=" + 
				SqlHandler.delimitString(dbuyAcknowledgementBean.getShiptoCountryAbbrev()) + "," +
			ATTRIBUTE_SHIPTO_STATE_ABBREV + "=" + 
				SqlHandler.delimitString(dbuyAcknowledgementBean.getShiptoStateAbbrev()) + "," +
			ATTRIBUTE_SHIPTO_ADDRESS_LINE_1 + "=" + 
				SqlHandler.delimitString(dbuyAcknowledgementBean.getShiptoAddressLine1()) + "," +
			ATTRIBUTE_SHIPTO_ADDRESS_LINE_2 + "=" + 
				SqlHandler.delimitString(dbuyAcknowledgementBean.getShiptoAddressLine2()) + "," +
			ATTRIBUTE_SHIPTO_ADDRESS_LINE_3 + "=" + 
				SqlHandler.delimitString(dbuyAcknowledgementBean.getShiptoAddressLine3()) + "," +
			ATTRIBUTE_SHIPTO_CITY + "=" + 
				SqlHandler.delimitString(dbuyAcknowledgementBean.getShiptoCity()) + "," +
			ATTRIBUTE_SHIPTO_ZIP + "=" + 
				SqlHandler.delimitString(dbuyAcknowledgementBean.getShiptoZip()) + "," +
			ATTRIBUTE_SUPPLIER_SHIP_TO_CODE + "=" + 
				SqlHandler.delimitString(dbuyAcknowledgementBean.getSupplierShipToCode()) + "," +
			ATTRIBUTE_ITEM_ID + "=" + 
				StringHandler.nullIfZero(dbuyAcknowledgementBean.getItemId()) + "," +
			ATTRIBUTE_ACCEPTED_QUANTITY + "=" + 
				StringHandler.nullIfZero(dbuyAcknowledgementBean.getAcceptedQuantity()) + "," +
			ATTRIBUTE_ACCEPTED_UOM + "=" + 
				SqlHandler.delimitString(dbuyAcknowledgementBean.getAcceptedUom()) + "," +
			ATTRIBUTE_ACCEPTED_UNIT_PRICE + "=" + 
				StringHandler.nullIfZero(dbuyAcknowledgementBean.getAcceptedUnitPrice()) + "," +
			ATTRIBUTE_SUPPLIER_PART_NO + "=" + 
				SqlHandler.delimitString(dbuyAcknowledgementBean.getSupplierPartNo()) + "," +
			ATTRIBUTE_EDI_UOM + "=" + 
				SqlHandler.delimitString(dbuyAcknowledgementBean.getEdiUom()) + "," +
			ATTRIBUTE_PROMISED_DATE + "=" + 
				DateHandler.getOracleToDateFunction(dbuyAcknowledgementBean.getPromisedDate()) + "," +
			ATTRIBUTE_EDI_DOCUMENT_CONTROL_NUMBER + "=" + 
				SqlHandler.delimitString(dbuyAcknowledgementBean.getEdiDocumentControlNumber()) + "," +
			ATTRIBUTE_DATE_PO_CREATED + "=" + 
				DateHandler.getOracleToDateFunction(dbuyAcknowledgementBean.getDatePoCreated()) + "," +
			ATTRIBUTE_TRANSACTION_DATE + "=" + 
				DateHandler.getOracleToDateFunction(dbuyAcknowledgementBean.getTransactionDate()) + "," +
			ATTRIBUTE_PO_LINE_CONFIRMED_DATE + "=" + 
				DateHandler.getOracleToDateFunction(dbuyAcknowledgementBean.getPoLineConfirmedDate()) + "," +
			ATTRIBUTE_SUPPLIER_PART_DESC + "=" + 
				SqlHandler.delimitString(dbuyAcknowledgementBean.getSupplierPartDesc()) + "," +
			ATTRIBUTE_EDI_ACKNOWLEDGEMENT_CODE + "=" + 
				SqlHandler.delimitString(dbuyAcknowledgementBean.getEdiAcknowledgementCode()) + "," +
			ATTRIBUTE_ACCEPTED_EDI_DATE_CODE + "=" + 
				SqlHandler.delimitString(dbuyAcknowledgementBean.getAcceptedEdiDateCode()) + "," +
			ATTRIBUTE_MATCHED + "=" + 
				SqlHandler.delimitString(dbuyAcknowledgementBean.getMatched()) + "," +
			ATTRIBUTE_REVIEWER_ID + "=" + 
				StringHandler.nullIfZero(dbuyAcknowledgementBean.getReviewerId()) + "," +
			ATTRIBUTE_REVIEW_DATE + "=" + 
				DateHandler.getOracleToDateFunction(dbuyAcknowledgementBean.getReviewDate()) + "," +
			ATTRIBUTE_REVIEW_COMMENTS + "=" + 
				SqlHandler.delimitString(dbuyAcknowledgementBean.getReviewComments()) + "," +
			ATTRIBUTE_DBUY_ACKNOWLEDGEMENT_ID + "=" + 
				StringHandler.nullIfZero(dbuyAcknowledgementBean.getDbuyAcknowledgementId()) + "," +
			ATTRIBUTE_ORDER_QUANTITY + "=" + 
				StringHandler.nullIfZero(dbuyAcknowledgementBean.getOrderQuantity()) + "," +
			ATTRIBUTE_MISMATCH_COMMENTS + "=" + 
				SqlHandler.delimitString(dbuyAcknowledgementBean.getMismatchComments()) + "," +
			ATTRIBUTE_SUPPLIER_SALES_ORDER_NO + "=" + 
				SqlHandler.delimitString(dbuyAcknowledgementBean.getSupplierSalesOrderNo()) + "," +
			ATTRIBUTE_SUPPLIER_SALES_ORDER_DATE + "=" + 
				DateHandler.getOracleToDateFunction(dbuyAcknowledgementBean.getSupplierSalesOrderDate()) + "," +
			ATTRIBUTE_DONE + "=" + 
				SqlHandler.delimitString(dbuyAcknowledgementBean.getDone()) + "," +
			ATTRIBUTE_DOCK_DATE + "=" + 
				DateHandler.getOracleToDateFunction(dbuyAcknowledgementBean.getDockDate()) + "," +
			ATTRIBUTE_PO_NOTES + "=" + 
				SqlHandler.delimitString(dbuyAcknowledgementBean.getPoNotes()) + "," +
			ATTRIBUTE_PO_LINE_NOTES + "=" + 
				SqlHandler.delimitString(dbuyAcknowledgementBean.getPoLineNotes()) + " " +
			"where " + ATTRIBUTE_RADIAN_PO + "=" +
				StringHandler.nullIfZero(dbuyAcknowledgementBean.getRadianPo());

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

		Collection dbuyAcknowledgementBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			DbuyAcknowledgementBean dbuyAcknowledgementBean = new DbuyAcknowledgementBean();
			load(dataSetRow, dbuyAcknowledgementBean);
			dbuyAcknowledgementBeanColl.add(dbuyAcknowledgementBean);
		}

		return dbuyAcknowledgementBeanColl;
	}

	public Collection selectDistinct(SearchCriteria criteria)
		throws BaseException {

		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = selectDistinct(criteria, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}
	public Collection selectDistinct(SearchCriteria criteria, Connection conn)
		throws BaseException {

		Collection dbuyAcknowledgementBeanColl = new Vector();

		String query = "select distinct RADIAN_PO,PO_LINE,SUPPLIER_NAME,SHIPTO_COUNTRY_ABBREV,SHIPTO_STATE_ABBREV,SHIPTO_ADDRESS_LINE_1,";
                       query+= "SHIPTO_ADDRESS_LINE_2,SHIPTO_ADDRESS_LINE_3,SHIPTO_CITY,SHIPTO_ZIP,SUPPLIER_SHIP_TO_CODE,";
          	       query+= "ITEM_ID,ACCEPTED_QUANTITY,ACCEPTED_UOM,ACCEPTED_UNIT_PRICE,SUPPLIER_PART_NO,EDI_UOM,PROMISED_DATE,";
                       query+= "DATE_PO_CREATED,SUPPLIER_PART_DESC,EDI_ACKNOWLEDGEMENT_CODE,ACCEPTED_EDI_DATE_CODE,";
                       query+= "ORDER_QUANTITY,MISMATCH_COMMENTS,SUPPLIER_SALES_ORDER_NO,MATCHED,";
                       query+= "SUPPLIER_SALES_ORDER_DATE,DOCK_DATE,PO_NOTES,PO_LINE_NOTES, BATCH_ID from " + TABLE + " " + getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			DbuyAcknowledgementBean dbuyAcknowledgementBean = new DbuyAcknowledgementBean();
			load(dataSetRow, dbuyAcknowledgementBean);
			dbuyAcknowledgementBeanColl.add(dbuyAcknowledgementBean);
		}

		return dbuyAcknowledgementBeanColl;
	}
        /*  -- leftover fields
            EDI_DOCUMENT_CONTROL_NUMBER
            PO_LINE_CONFIRMED_DATE
            TRANSACTION_DATE
            DBUY_ACKNOWLEDGEMENT_ID
            
            REVIEWER_ID
            REVIEW_COMMENTS
            REVIEW_DATE
            DONE              
         */
}