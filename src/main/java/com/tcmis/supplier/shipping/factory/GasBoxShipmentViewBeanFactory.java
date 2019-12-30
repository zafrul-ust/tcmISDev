package com.tcmis.supplier.shipping.factory;


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
import com.tcmis.supplier.shipping.beans.GasBoxShipmentViewBean;


/******************************************************************************
 * CLASSNAME: GasBoxShipmentViewBeanFactory <br>
 * @version: 1.0, May 29, 2008 <br>
 *****************************************************************************/


public class GasBoxShipmentViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_SUPPLIER = "SUPPLIER";
	public String ATTRIBUTE_SHIP_FROM_LOCATION_ID = "SHIP_FROM_LOCATION_ID";
	public String ATTRIBUTE_RADIAN_PO = "RADIAN_PO";
	public String ATTRIBUTE_SHIP_VIA_LOCATION_ID = "SHIP_VIA_LOCATION_ID";
	public String ATTRIBUTE_SHIP_VIA_COMPANY_ID = "SHIP_VIA_COMPANY_ID";
	public String ATTRIBUTE_ULTIMATE_DODAAC = "ULTIMATE_DODAAC";
	public String ATTRIBUTE_SHIP_VIA_DODAAC = "SHIP_VIA_DODAAC";
	public String ATTRIBUTE_RECEIPT_ID = "RECEIPT_ID";
	public String ATTRIBUTE_ISSUE_ID = "ISSUE_ID";
	public String ATTRIBUTE_SUPPLIER_SALES_ORDER_NO = "SUPPLIER_SALES_ORDER_NO";
	public String ATTRIBUTE_QUANTITY = "QUANTITY";
	public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
	public String ATTRIBUTE_PART_SHORT_NAME = "PART_SHORT_NAME";
	public String ATTRIBUTE_CURRENT_CARRIER_NAME = "CURRENT_CARRIER_NAME";
	public String ATTRIBUTE_CURRENT_TRACKING_NUMBER = "CURRENT_TRACKING_NUMBER";
	public String ATTRIBUTE_CURRENT_SHIPMENT_ID = "CURRENT_SHIPMENT_ID";
	public String ATTRIBUTE_BOX_ID = "BOX_ID";
	public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
	public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
	public String ATTRIBUTE_SHIP_FROM_LOCATION_NAME = "SHIP_FROM_LOCATION_NAME";
	public String ATTRIBUTE_SHIP_TO_LOCATION_NAME = "SHIP_TO_LOCATION_NAME";
	public String ATTRIBUTE_SHIP_TO_CITY_COMMA_STATE = "SHIP_TO_CITY_COMMA_STATE";
	public String ATTRIBUTE_SHIP_TO_ZIPCODE = "SHIP_TO_ZIPCODE";
  public String ATTRIBUTE_USGOV_TCN = "USGOV_TCN";
  public String ATTRIBUTE_CUSTOMER_PO_NO = "CUSTOMER_PO_NO";

  //table name
	public String TABLE = "GAS_BOX_SHIPMENT_VIEW";


	//constructor
	public GasBoxShipmentViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("supplier")) {
			return ATTRIBUTE_SUPPLIER;
		}
		else if(attributeName.equals("shipFromLocationId")) {
			return ATTRIBUTE_SHIP_FROM_LOCATION_ID;
		}
		else if(attributeName.equals("radianPo")) {
			return ATTRIBUTE_RADIAN_PO;
		}
		else if(attributeName.equals("shipViaLocationId")) {
			return ATTRIBUTE_SHIP_VIA_LOCATION_ID;
		}
		else if(attributeName.equals("shipViaCompanyId")) {
			return ATTRIBUTE_SHIP_VIA_COMPANY_ID;
		}
		else if(attributeName.equals("ultimateDodaac")) {
			return ATTRIBUTE_ULTIMATE_DODAAC;
		}
		else if(attributeName.equals("shipViaDodaac")) {
			return ATTRIBUTE_SHIP_VIA_DODAAC;
		}
		else if(attributeName.equals("receiptId")) {
			return ATTRIBUTE_RECEIPT_ID;
		}
		else if(attributeName.equals("issueId")) {
			return ATTRIBUTE_ISSUE_ID;
		}
		else if(attributeName.equals("supplierSalesOrderNo")) {
			return ATTRIBUTE_SUPPLIER_SALES_ORDER_NO;
		}
		else if(attributeName.equals("quantity")) {
			return ATTRIBUTE_QUANTITY;
		}
		else if(attributeName.equals("catPartNo")) {
			return ATTRIBUTE_CAT_PART_NO;
		}
		else if(attributeName.equals("partShortName")) {
			return ATTRIBUTE_PART_SHORT_NAME;
		}
		else if(attributeName.equals("currentCarrierName")) {
			return ATTRIBUTE_CURRENT_CARRIER_NAME;
		}
		else if(attributeName.equals("currentTrackingNumber")) {
			return ATTRIBUTE_CURRENT_TRACKING_NUMBER;
		}
		else if(attributeName.equals("currentShipmentId")) {
			return ATTRIBUTE_CURRENT_SHIPMENT_ID;
		}
		else if(attributeName.equals("boxId")) {
			return ATTRIBUTE_BOX_ID;
		}
		else if(attributeName.equals("prNumber")) {
			return ATTRIBUTE_PR_NUMBER;
		}
		else if(attributeName.equals("lineItem")) {
			return ATTRIBUTE_LINE_ITEM;
		}
		else if(attributeName.equals("shipFromLocationName")) {
			return ATTRIBUTE_SHIP_FROM_LOCATION_NAME;
		}
		else if(attributeName.equals("shipToLocationName")) {
			return ATTRIBUTE_SHIP_TO_LOCATION_NAME;
		}
		else if(attributeName.equals("shipToCityCommaState")) {
			return ATTRIBUTE_SHIP_TO_CITY_COMMA_STATE;
		}
		else if(attributeName.equals("shipToZipcode")) {
			return ATTRIBUTE_SHIP_TO_ZIPCODE;
		}
		else if(attributeName.equals("usgovTcn")) {
			return ATTRIBUTE_USGOV_TCN;
		}    
		else if(attributeName.equals("customerPoNo")) {
			return ATTRIBUTE_CUSTOMER_PO_NO;
		}
    else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, GasBoxShipmentViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(GasBoxShipmentViewBean gasBoxShipmentViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("supplier", "SearchCriterion.EQUALS",
			"" + gasBoxShipmentViewBean.getSupplier());

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


	public int delete(GasBoxShipmentViewBean gasBoxShipmentViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("supplier", "SearchCriterion.EQUALS",
			"" + gasBoxShipmentViewBean.getSupplier());

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
	public int insert(GasBoxShipmentViewBean gasBoxShipmentViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(gasBoxShipmentViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(GasBoxShipmentViewBean gasBoxShipmentViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_SUPPLIER + "," +
			ATTRIBUTE_SHIP_FROM_LOCATION_ID + "," +
			ATTRIBUTE_RADIAN_PO + "," +
			ATTRIBUTE_SHIP_VIA_LOCATION_ID + "," +
			ATTRIBUTE_SHIP_VIA_COMPANY_ID + "," +
			ATTRIBUTE_ULTIMATE_DODAAC + "," +
			ATTRIBUTE_SHIP_VIA_DODAAC + "," +
			ATTRIBUTE_RECEIPT_ID + "," +
			ATTRIBUTE_ISSUE_ID + "," +
			ATTRIBUTE_SUPPLIER_SALES_ORDER_NO + "," +
			ATTRIBUTE_QUANTITY + "," +
			ATTRIBUTE_CAT_PART_NO + "," +
			ATTRIBUTE_PART_SHORT_NAME + "," +
			ATTRIBUTE_CURRENT_CARRIER_NAME + "," +
			ATTRIBUTE_CURRENT_TRACKING_NUMBER + "," +
			ATTRIBUTE_CURRENT_SHIPMENT_ID + "," +
			ATTRIBUTE_BOX_ID + "," +
			ATTRIBUTE_PR_NUMBER + "," +
			ATTRIBUTE_LINE_ITEM + ")" +
			" values (" +
			SqlHandler.delimitString(gasBoxShipmentViewBean.getSupplier()) + "," +
			SqlHandler.delimitString(gasBoxShipmentViewBean.getShipFromLocationId()) + "," +
			gasBoxShipmentViewBean.getRadianPo() + "," +
			SqlHandler.delimitString(gasBoxShipmentViewBean.getShipViaLocationId()) + "," +
			SqlHandler.delimitString(gasBoxShipmentViewBean.getShipViaCompanyId()) + "," +
			SqlHandler.delimitString(gasBoxShipmentViewBean.getUltimateDodaac()) + "," +
			SqlHandler.delimitString(gasBoxShipmentViewBean.getShipViaDodaac()) + "," +
			gasBoxShipmentViewBean.getReceiptId() + "," +
			gasBoxShipmentViewBean.getIssueId() + "," +
			SqlHandler.delimitString(gasBoxShipmentViewBean.getSupplierSalesOrderNo()) + "," +
			gasBoxShipmentViewBean.getQuantity() + "," +
			SqlHandler.delimitString(gasBoxShipmentViewBean.getCatPartNo()) + "," +
			SqlHandler.delimitString(gasBoxShipmentViewBean.getPartShortName()) + "," +
			SqlHandler.delimitString(gasBoxShipmentViewBean.getCurrentCarrierName()) + "," +
			SqlHandler.delimitString(gasBoxShipmentViewBean.getCurrentTrackingNumber()) + "," +
			gasBoxShipmentViewBean.getCurrentShipmentId() + "," +
			SqlHandler.delimitString(gasBoxShipmentViewBean.getBoxId()) + "," +
			gasBoxShipmentViewBean.getPrNumber() + "," +
			SqlHandler.delimitString(gasBoxShipmentViewBean.getLineItem()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(GasBoxShipmentViewBean gasBoxShipmentViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(gasBoxShipmentViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(GasBoxShipmentViewBean gasBoxShipmentViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_SUPPLIER + "=" + 
				SqlHandler.delimitString(gasBoxShipmentViewBean.getSupplier()) + "," +
			ATTRIBUTE_SHIP_FROM_LOCATION_ID + "=" + 
				SqlHandler.delimitString(gasBoxShipmentViewBean.getShipFromLocationId()) + "," +
			ATTRIBUTE_RADIAN_PO + "=" + 
				StringHandler.nullIfZero(gasBoxShipmentViewBean.getRadianPo()) + "," +
			ATTRIBUTE_SHIP_VIA_LOCATION_ID + "=" + 
				SqlHandler.delimitString(gasBoxShipmentViewBean.getShipViaLocationId()) + "," +
			ATTRIBUTE_SHIP_VIA_COMPANY_ID + "=" + 
				SqlHandler.delimitString(gasBoxShipmentViewBean.getShipViaCompanyId()) + "," +
			ATTRIBUTE_ULTIMATE_DODAAC + "=" + 
				SqlHandler.delimitString(gasBoxShipmentViewBean.getUltimateDodaac()) + "," +
			ATTRIBUTE_SHIP_VIA_DODAAC + "=" + 
				SqlHandler.delimitString(gasBoxShipmentViewBean.getShipViaDodaac()) + "," +
			ATTRIBUTE_RECEIPT_ID + "=" + 
				StringHandler.nullIfZero(gasBoxShipmentViewBean.getReceiptId()) + "," +
			ATTRIBUTE_ISSUE_ID + "=" + 
				StringHandler.nullIfZero(gasBoxShipmentViewBean.getIssueId()) + "," +
			ATTRIBUTE_SUPPLIER_SALES_ORDER_NO + "=" + 
				SqlHandler.delimitString(gasBoxShipmentViewBean.getSupplierSalesOrderNo()) + "," +
			ATTRIBUTE_QUANTITY + "=" + 
				StringHandler.nullIfZero(gasBoxShipmentViewBean.getQuantity()) + "," +
			ATTRIBUTE_CAT_PART_NO + "=" + 
				SqlHandler.delimitString(gasBoxShipmentViewBean.getCatPartNo()) + "," +
			ATTRIBUTE_PART_SHORT_NAME + "=" + 
				SqlHandler.delimitString(gasBoxShipmentViewBean.getPartShortName()) + "," +
			ATTRIBUTE_CURRENT_CARRIER_NAME + "=" + 
				SqlHandler.delimitString(gasBoxShipmentViewBean.getCurrentCarrierName()) + "," +
			ATTRIBUTE_CURRENT_TRACKING_NUMBER + "=" + 
				SqlHandler.delimitString(gasBoxShipmentViewBean.getCurrentTrackingNumber()) + "," +
			ATTRIBUTE_CURRENT_SHIPMENT_ID + "=" + 
				StringHandler.nullIfZero(gasBoxShipmentViewBean.getCurrentShipmentId()) + "," +
			ATTRIBUTE_BOX_ID + "=" + 
				SqlHandler.delimitString(gasBoxShipmentViewBean.getBoxId()) + "," +
			ATTRIBUTE_PR_NUMBER + "=" + 
				StringHandler.nullIfZero(gasBoxShipmentViewBean.getPrNumber()) + "," +
			ATTRIBUTE_LINE_ITEM + "=" + 
				SqlHandler.delimitString(gasBoxShipmentViewBean.getLineItem()) + " " +
			"where " + ATTRIBUTE_SUPPLIER + "=" +
				gasBoxShipmentViewBean.getSupplier();

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

		Collection gasBoxShipmentViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria)+ getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			GasBoxShipmentViewBean gasBoxShipmentViewBean = new GasBoxShipmentViewBean();
			load(dataSetRow, gasBoxShipmentViewBean);
			gasBoxShipmentViewBeanColl.add(gasBoxShipmentViewBean);
		}

		return gasBoxShipmentViewBeanColl;
	}
	public Collection selectDistinct(SearchCriteria criteria)
	throws BaseException {

	return selectDistinct(criteria,null);

	}
	public Collection selectDistinct(SearchCriteria criteria, SortCriteria sortCriteria)
	throws BaseException {

		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = selectDistinct(criteria,sortCriteria, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}
	public Collection selectDistinct(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn)
	throws BaseException {

		Collection gasBoxShipmentViewBeanColl = new Vector();

		String query = "select distinct current_Carrier_Name,current_Tracking_Number,current_Shipment_Id,SHIP_FROM_LOCATION_NAME from "
			+ TABLE + " " +	getWhereClause(criteria) + getOrderByClause(sortCriteria); 

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			GasBoxShipmentViewBean gasBoxShipmentViewBean = new GasBoxShipmentViewBean();
			load(dataSetRow, gasBoxShipmentViewBean);
			gasBoxShipmentViewBeanColl.add(gasBoxShipmentViewBean);
		}

		return gasBoxShipmentViewBeanColl;
	}
}