package com.tcmis.client.report.factory;


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
import com.tcmis.client.report.beans.DeliveredReceiptDocsViewBean;


/******************************************************************************
 * CLASSNAME: DeliveredReceiptDocsViewBeanFactory <br>
 * @version: 1.0, Sep 26, 2006 <br>
 *****************************************************************************/


public class DeliveredReceiptDocsViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_RECEIPT_ID = "RECEIPT_ID";
	public String ATTRIBUTE_DATE_DELIVERED = "DATE_DELIVERED";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
	public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_APPLICATION = "APPLICATION";
	public String ATTRIBUTE_SHIP_TO_LOCATION_ID = "SHIP_TO_LOCATION_ID";
	public String ATTRIBUTE_PO_NUMBER = "PO_NUMBER";
	public String ATTRIBUTE_RELEASE_NUMBER = "RELEASE_NUMBER";
	public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
	public String ATTRIBUTE_CATALOG_DESC = "CATALOG_DESC";
	public String ATTRIBUTE_FAC_PART_NO = "FAC_PART_NO";
	public String ATTRIBUTE_PART_DESCRIPTION = "PART_DESCRIPTION";
	public String ATTRIBUTE_SPEC = "SPEC";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_ITEM_DESC = "ITEM_DESC";
	public String ATTRIBUTE_CATALOG_PRICE = "CATALOG_PRICE";
	public String ATTRIBUTE_BASELINE_PRICE = "BASELINE_PRICE";
	public String ATTRIBUTE_CURRENCY_ID = "CURRENCY_ID";
	public String ATTRIBUTE_UNIT_OF_SALE = "UNIT_OF_SALE";
	public String ATTRIBUTE_UNIT_OF_SALE_QUANTITY_PER_EACH = "UNIT_OF_SALE_QUANTITY_PER_EACH";
	public String ATTRIBUTE_DOCUMENT_URL = "DOCUMENT_URL";
	public String ATTRIBUTE_ON_LINE = "ON_LINE";
    public String ATTRIBUTE_MR_LINE = "MR_LINE";
    public String ATTRIBUTE_PO_LINE = "PO_LINE";
    public String ATTRIBUTE_QUANTITY_ISSUED = "QUANTITY_ISSUED";
    public String ATTRIBUTE_MFG_LOT = "MFG_LOT";
	public String ATTRIBUTE_EXPIRE_DATE = "EXPIRE_DATE";
	public String ATTRIBUTE_APPLICATION_DESC = "APPLICATION_DESC";

	//table name
	public String TABLE = "DELIVERED_RECEIPT_DOCS_VIEW";


	//constructor
	public DeliveredReceiptDocsViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("receiptId")) {
			return ATTRIBUTE_RECEIPT_ID;
		}
		else if(attributeName.equals("dateDelivered")) {
			return ATTRIBUTE_DATE_DELIVERED;
		}
		else if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("prNumber")) {
			return ATTRIBUTE_PR_NUMBER;
		}
		else if(attributeName.equals("lineItem")) {
			return ATTRIBUTE_LINE_ITEM;
		}
		else if(attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if(attributeName.equals("application")) {
			return ATTRIBUTE_APPLICATION;
		}
		else if(attributeName.equals("shipToLocationId")) {
			return ATTRIBUTE_SHIP_TO_LOCATION_ID;
		}
		else if(attributeName.equals("poNumber")) {
			return ATTRIBUTE_PO_NUMBER;
		}
		else if(attributeName.equals("releaseNumber")) {
			return ATTRIBUTE_RELEASE_NUMBER;
		}
		else if(attributeName.equals("catalogId")) {
			return ATTRIBUTE_CATALOG_ID;
		}
		else if(attributeName.equals("catalogDesc")) {
			return ATTRIBUTE_CATALOG_DESC;
		}
		else if(attributeName.equals("facPartNo")) {
			return ATTRIBUTE_FAC_PART_NO;
		}
		else if(attributeName.equals("partDescription")) {
			return ATTRIBUTE_PART_DESCRIPTION;
		}
		else if(attributeName.equals("spec")) {
			return ATTRIBUTE_SPEC;
		}
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("itemDesc")) {
			return ATTRIBUTE_ITEM_DESC;
		}
		else if(attributeName.equals("catalogPrice")) {
			return ATTRIBUTE_CATALOG_PRICE;
		}
		else if(attributeName.equals("baselinePrice")) {
			return ATTRIBUTE_BASELINE_PRICE;
		}
		else if(attributeName.equals("currencyId")) {
			return ATTRIBUTE_CURRENCY_ID;
		}
		else if(attributeName.equals("unitOfSale")) {
			return ATTRIBUTE_UNIT_OF_SALE;
		}
		else if(attributeName.equals("unitOfSaleQuantityPerEach")) {
			return ATTRIBUTE_UNIT_OF_SALE_QUANTITY_PER_EACH;
		}
		else if(attributeName.equals("documentUrl")) {
			return ATTRIBUTE_DOCUMENT_URL;
		}
		else if(attributeName.equals("onLine")) {
			return ATTRIBUTE_ON_LINE;
		}
		else if(attributeName.equals("mrLine")) {
			return ATTRIBUTE_MR_LINE;
		}
		else if(attributeName.equals("poLine")) {
			return ATTRIBUTE_PO_LINE;
		}
		else if(attributeName.equals("quantityIssued")) {
			return ATTRIBUTE_QUANTITY_ISSUED;
		}
		else if(attributeName.equals("mfgLot")) {
			return ATTRIBUTE_MFG_LOT;
		}
		else if(attributeName.equals("expireDate")) {
			return ATTRIBUTE_EXPIRE_DATE;
		}
		else if(attributeName.equals("applicationDesc")) {
			return ATTRIBUTE_APPLICATION_DESC;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, DeliveredReceiptDocsViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(DeliveredReceiptDocsViewBean deliveredReceiptDocsViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("receiptId", "SearchCriterion.EQUALS",
			"" + deliveredReceiptDocsViewBean.getReceiptId());

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


	public int delete(DeliveredReceiptDocsViewBean deliveredReceiptDocsViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("receiptId", "SearchCriterion.EQUALS",
			"" + deliveredReceiptDocsViewBean.getReceiptId());

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
	public int insert(DeliveredReceiptDocsViewBean deliveredReceiptDocsViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(deliveredReceiptDocsViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(DeliveredReceiptDocsViewBean deliveredReceiptDocsViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
			ATTRIBUTE_RECEIPT_ID + "," +
			ATTRIBUTE_DATE_DELIVERED + "," +
			ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_PR_NUMBER + "," +
			ATTRIBUTE_LINE_ITEM + "," +
			ATTRIBUTE_FACILITY_ID + "," +
			ATTRIBUTE_APPLICATION + "," +
			ATTRIBUTE_SHIP_TO_LOCATION_ID + "," +
			ATTRIBUTE_PO_NUMBER + "," +
			ATTRIBUTE_RELEASE_NUMBER + "," +
			ATTRIBUTE_CATALOG_ID + "," +
			ATTRIBUTE_CATALOG_DESC + "," +
			ATTRIBUTE_FAC_PART_NO + "," +
			ATTRIBUTE_PART_DESCRIPTION + "," +
			ATTRIBUTE_SPEC + "," +
			ATTRIBUTE_ITEM_ID + "," +
			ATTRIBUTE_ITEM_DESC + "," +
			ATTRIBUTE_CATALOG_PRICE + "," +
			ATTRIBUTE_BASELINE_PRICE + "," +
			ATTRIBUTE_CURRENCY_ID + "," +
			ATTRIBUTE_UNIT_OF_SALE + "," +
			ATTRIBUTE_UNIT_OF_SALE_QUANTITY_PER_EACH + "," +
			ATTRIBUTE_DOCUMENT_URL + "," +
			ATTRIBUTE_ON_LINE + ")" +
			" values (" +
			deliveredReceiptDocsViewBean.getReceiptId() + "," +
			DateHandler.getOracleToDateFunction(deliveredReceiptDocsViewBean.getDateDelivered()) + "," +
			SqlHandler.delimitString(deliveredReceiptDocsViewBean.getCompanyId()) + "," +
			deliveredReceiptDocsViewBean.getPrNumber() + "," +
			SqlHandler.delimitString(deliveredReceiptDocsViewBean.getLineItem()) + "," +
			SqlHandler.delimitString(deliveredReceiptDocsViewBean.getFacilityId()) + "," +
			SqlHandler.delimitString(deliveredReceiptDocsViewBean.getApplication()) + "," +
			SqlHandler.delimitString(deliveredReceiptDocsViewBean.getShipToLocationId()) + "," +
			SqlHandler.delimitString(deliveredReceiptDocsViewBean.getPoNumber()) + "," +
			SqlHandler.delimitString(deliveredReceiptDocsViewBean.getReleaseNumber()) + "," +
			SqlHandler.delimitString(deliveredReceiptDocsViewBean.getCatalogId()) + "," +
			SqlHandler.delimitString(deliveredReceiptDocsViewBean.getCatalogDesc()) + "," +
			SqlHandler.delimitString(deliveredReceiptDocsViewBean.getFacPartNo()) + "," +
			SqlHandler.delimitString(deliveredReceiptDocsViewBean.getPartDescription()) + "," +
			SqlHandler.delimitString(deliveredReceiptDocsViewBean.getSpec()) + "," +
			deliveredReceiptDocsViewBean.getItemId() + "," +
			SqlHandler.delimitString(deliveredReceiptDocsViewBean.getItemDesc()) + "," +
			deliveredReceiptDocsViewBean.getCatalogPrice() + "," +
			deliveredReceiptDocsViewBean.getBaselinePrice() + "," +
			SqlHandler.delimitString(deliveredReceiptDocsViewBean.getCurrencyId()) + "," +
			SqlHandler.delimitString(deliveredReceiptDocsViewBean.getUnitOfSale()) + "," +
			deliveredReceiptDocsViewBean.getUnitOfSaleQuantityPerEach() + "," +
			SqlHandler.delimitString(deliveredReceiptDocsViewBean.getDocumentUrl()) + "," +
			SqlHandler.delimitString(deliveredReceiptDocsViewBean.getOnLine()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(DeliveredReceiptDocsViewBean deliveredReceiptDocsViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(deliveredReceiptDocsViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(DeliveredReceiptDocsViewBean deliveredReceiptDocsViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_RECEIPT_ID + "=" +
				StringHandler.nullIfZero(deliveredReceiptDocsViewBean.getReceiptId()) + "," +
			ATTRIBUTE_DATE_DELIVERED + "=" +
				DateHandler.getOracleToDateFunction(deliveredReceiptDocsViewBean.getDateDelivered()) + "," +
			ATTRIBUTE_COMPANY_ID + "=" +
				SqlHandler.delimitString(deliveredReceiptDocsViewBean.getCompanyId()) + "," +
			ATTRIBUTE_PR_NUMBER + "=" +
				StringHandler.nullIfZero(deliveredReceiptDocsViewBean.getPrNumber()) + "," +
			ATTRIBUTE_LINE_ITEM + "=" +
				SqlHandler.delimitString(deliveredReceiptDocsViewBean.getLineItem()) + "," +
			ATTRIBUTE_FACILITY_ID + "=" +
				SqlHandler.delimitString(deliveredReceiptDocsViewBean.getFacilityId()) + "," +
			ATTRIBUTE_APPLICATION + "=" +
				SqlHandler.delimitString(deliveredReceiptDocsViewBean.getApplication()) + "," +
			ATTRIBUTE_SHIP_TO_LOCATION_ID + "=" +
				SqlHandler.delimitString(deliveredReceiptDocsViewBean.getShipToLocationId()) + "," +
			ATTRIBUTE_PO_NUMBER + "=" +
				SqlHandler.delimitString(deliveredReceiptDocsViewBean.getPoNumber()) + "," +
			ATTRIBUTE_RELEASE_NUMBER + "=" +
				SqlHandler.delimitString(deliveredReceiptDocsViewBean.getReleaseNumber()) + "," +
			ATTRIBUTE_CATALOG_ID + "=" +
				SqlHandler.delimitString(deliveredReceiptDocsViewBean.getCatalogId()) + "," +
			ATTRIBUTE_CATALOG_DESC + "=" +
				SqlHandler.delimitString(deliveredReceiptDocsViewBean.getCatalogDesc()) + "," +
			ATTRIBUTE_FAC_PART_NO + "=" +
				SqlHandler.delimitString(deliveredReceiptDocsViewBean.getFacPartNo()) + "," +
			ATTRIBUTE_PART_DESCRIPTION + "=" +
				SqlHandler.delimitString(deliveredReceiptDocsViewBean.getPartDescription()) + "," +
			ATTRIBUTE_SPEC + "=" +
				SqlHandler.delimitString(deliveredReceiptDocsViewBean.getSpec()) + "," +
			ATTRIBUTE_ITEM_ID + "=" +
				StringHandler.nullIfZero(deliveredReceiptDocsViewBean.getItemId()) + "," +
			ATTRIBUTE_ITEM_DESC + "=" +
				SqlHandler.delimitString(deliveredReceiptDocsViewBean.getItemDesc()) + "," +
			ATTRIBUTE_CATALOG_PRICE + "=" +
				StringHandler.nullIfZero(deliveredReceiptDocsViewBean.getCatalogPrice()) + "," +
			ATTRIBUTE_BASELINE_PRICE + "=" +
				StringHandler.nullIfZero(deliveredReceiptDocsViewBean.getBaselinePrice()) + "," +
			ATTRIBUTE_CURRENCY_ID + "=" +
				SqlHandler.delimitString(deliveredReceiptDocsViewBean.getCurrencyId()) + "," +
			ATTRIBUTE_UNIT_OF_SALE + "=" +
				SqlHandler.delimitString(deliveredReceiptDocsViewBean.getUnitOfSale()) + "," +
			ATTRIBUTE_UNIT_OF_SALE_QUANTITY_PER_EACH + "=" +
				StringHandler.nullIfZero(deliveredReceiptDocsViewBean.getUnitOfSaleQuantityPerEach()) + "," +
			ATTRIBUTE_DOCUMENT_URL + "=" +
				SqlHandler.delimitString(deliveredReceiptDocsViewBean.getDocumentUrl()) + "," +
			ATTRIBUTE_ON_LINE + "=" +
				SqlHandler.delimitString(deliveredReceiptDocsViewBean.getOnLine()) + " " +
			"where " + ATTRIBUTE_RECEIPT_ID + "=" +
				deliveredReceiptDocsViewBean.getReceiptId();

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

		Collection deliveredReceiptDocsViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			DeliveredReceiptDocsViewBean deliveredReceiptDocsViewBean = new DeliveredReceiptDocsViewBean();
			load(dataSetRow, deliveredReceiptDocsViewBean);
			deliveredReceiptDocsViewBeanColl.add(deliveredReceiptDocsViewBean);
		}

		return deliveredReceiptDocsViewBeanColl;
	}

	public Collection select(SearchCriteria criteria,SortCriteria sortCriteria)
		throws BaseException {

		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = select(criteria,sortCriteria,connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}
	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn)
		throws BaseException {

		Collection deliveredReceiptDocsViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria)+getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			DeliveredReceiptDocsViewBean deliveredReceiptDocsViewBean = new DeliveredReceiptDocsViewBean();
			load(dataSetRow, deliveredReceiptDocsViewBean);
			deliveredReceiptDocsViewBeanColl.add(deliveredReceiptDocsViewBean);
		}

		return deliveredReceiptDocsViewBeanColl;
	}


		  //select
        public Collection selectEndStartWith(SearchCriteria criteria, String additionalWhere)
                throws BaseException {

                Connection connection = null;
                Collection c = null;
                try {
                        connection = this.getDbManager().getConnection();
                        c = selectEndStartWith(criteria,connection,additionalWhere);
                }
                finally {
                        this.getDbManager().returnConnection(connection);
                }
                return c;
        }
        public Collection selectEndStartWith(SearchCriteria criteria, Connection conn, String additionalWhere)
                throws BaseException {

                Collection deliveredReceiptDocsViewBeanColl = new Vector();

                String query = "select * from " + TABLE + " "+getWhereClause(criteria)+" and "+additionalWhere;

                DataSet dataSet = new SqlManager().select(conn, query);

                Iterator dataIter = dataSet.iterator();

                while (dataIter.hasNext()) {
                        DataSetRow dataSetRow = (DataSetRow)dataIter.next();
                        DeliveredReceiptDocsViewBean deliveredReceiptDocsViewBean = new DeliveredReceiptDocsViewBean();
                        load(dataSetRow, deliveredReceiptDocsViewBean);
                        deliveredReceiptDocsViewBeanColl.add(deliveredReceiptDocsViewBean);
                }

                return deliveredReceiptDocsViewBeanColl;
        }

}