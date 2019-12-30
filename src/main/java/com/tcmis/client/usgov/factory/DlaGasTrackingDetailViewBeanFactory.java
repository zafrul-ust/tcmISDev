package com.tcmis.client.usgov.factory;


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
import com.tcmis.client.usgov.beans.DlaGasTrackingDetailViewBean;


/******************************************************************************
 * CLASSNAME: DlaGasTrackingDetailViewBeanFactory <br>
 * @version: 1.0, May 13, 2008 <br>
 *****************************************************************************/


public class DlaGasTrackingDetailViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_TRANSACTION_TYPE = "TRANSACTION_TYPE";
	public String ATTRIBUTE_EXPORT_FLAG = "EXPORT_FLAG";
	public String ATTRIBUTE_CUSTOMER_PO_NO = "CUSTOMER_PO_NO";
	public String ATTRIBUTE_CUSTOMER_PO_LINE_NO = "CUSTOMER_PO_LINE_NO";
	public String ATTRIBUTE_MR = "MR";
	public String ATTRIBUTE_MR_LINE = "MR_LINE";
	public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
	public String ATTRIBUTE_QUANTITY = "QUANTITY";
	public String ATTRIBUTE_UOM = "UOM";
	public String ATTRIBUTE_UNIT_PRICE = "UNIT_PRICE";
	public String ATTRIBUTE_EXTENDED_PRICE = "EXTENDED_PRICE";
	public String ATTRIBUTE_DESIRED_SHIP_DATE = "DESIRED_SHIP_DATE";
	public String ATTRIBUTE_DESIRED_DELIVERY_DATE = "DESIRED_DELIVERY_DATE";
	public String ATTRIBUTE_ACTUAL_SHIP_DATE = "ACTUAL_SHIP_DATE";
	public String ATTRIBUTE_DAYS_LATE = "DAYS_LATE";
	public String ATTRIBUTE_DLA_ORDER_ISSUED = "DLA_ORDER_ISSUED";
	public String ATTRIBUTE_DLA_ORDER_RECEIVED = "DLA_ORDER_RECEIVED";
	public String ATTRIBUTE_DLA_ORDER_IN_STAGE = "DLA_ORDER_IN_STAGE";
	public String ATTRIBUTE_INTERNAL_DPMS_REQUEST = "INTERNAL_DPMS_REQUEST";
	public String ATTRIBUTE_INTERNAL_NOL_REQUEST = "INTERNAL_NOL_REQUEST";
	public String ATTRIBUTE_MR_CREATION = "MR_CREATION";
	public String ATTRIBUTE_BUY_ORDER_CREATION = "BUY_ORDER_CREATION";
	public String ATTRIBUTE_HAAS_ORDER_ISSUED = "HAAS_ORDER_ISSUED";
	public String ATTRIBUTE_AIRGAS_997_ISSUED = "AIRGAS_997_ISSUED";
	public String ATTRIBUTE_AIRGAS_997_RECEIVED = "AIRGAS_997_RECEIVED";
	public String ATTRIBUTE_AIRGAS_855_ISSUED = "AIRGAS_855_ISSUED";
	public String ATTRIBUTE_AIRGAS_855_RECEIVED = "AIRGAS_855_RECEIVED";
	public String ATTRIBUTE_PO_CONFIRM_DATE = "PO_CONFIRM_DATE";
	public String ATTRIBUTE_AIRGAS_856_ISSUED = "AIRGAS_856_ISSUED";
	public String ATTRIBUTE_AIRGAS_856_RECEIVED = "AIRGAS_856_RECEIVED";
	public String ATTRIBUTE_DLA_856_CREATED = "DLA_856_CREATED";
	public String ATTRIBUTE_DLA_856_TO_ELLIS = "DLA_856_TO_ELLIS";
	public String ATTRIBUTE_DLA_856_TO_WAWF = "DLA_856_TO_WAWF";
	public String ATTRIBUTE_DLA_945_CREATED = "DLA_945_CREATED";
	public String ATTRIBUTE_DLA_945_SENT = "DLA_945_SENT";

	//table name
	public String TABLE = "DLA_GAS_TRACKING_DETAIL_VIEW";


	//constructor
	public DlaGasTrackingDetailViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("transactionType")) {
			return ATTRIBUTE_TRANSACTION_TYPE;
		}
		else if(attributeName.equals("exportFlag")) {
			return ATTRIBUTE_EXPORT_FLAG;
		}
		else if(attributeName.equals("customerPoNo")) {
			return ATTRIBUTE_CUSTOMER_PO_NO;
		}
		else if(attributeName.equals("customerPoLineNo")) {
			return ATTRIBUTE_CUSTOMER_PO_LINE_NO;
		}
		else if(attributeName.equals("mr")) {
			return ATTRIBUTE_MR;
		}
		else if(attributeName.equals("mrLine")) {
			return ATTRIBUTE_MR_LINE;
		}
		else if(attributeName.equals("catPartNo")) {
			return ATTRIBUTE_CAT_PART_NO;
		}
		else if(attributeName.equals("quantity")) {
			return ATTRIBUTE_QUANTITY;
		}
		else if(attributeName.equals("uom")) {
			return ATTRIBUTE_UOM;
		}
		else if(attributeName.equals("unitPrice")) {
			return ATTRIBUTE_UNIT_PRICE;
		}
		else if(attributeName.equals("extendedPrice")) {
			return ATTRIBUTE_EXTENDED_PRICE;
		}
		else if(attributeName.equals("desiredShipDate")) {
			return ATTRIBUTE_DESIRED_SHIP_DATE;
		}
		else if(attributeName.equals("desiredDeliveryDate")) {
			return ATTRIBUTE_DESIRED_DELIVERY_DATE;
		}
		else if(attributeName.equals("actualShipDate")) {
			return ATTRIBUTE_ACTUAL_SHIP_DATE;
		}
		else if(attributeName.equals("daysLate")) {
			return ATTRIBUTE_DAYS_LATE;
		}
		else if(attributeName.equals("dlaOrderIssued")) {
			return ATTRIBUTE_DLA_ORDER_ISSUED;
		}
		else if(attributeName.equals("dlaOrderReceived")) {
			return ATTRIBUTE_DLA_ORDER_RECEIVED;
		}
		else if(attributeName.equals("dlaOrderInStage")) {
			return ATTRIBUTE_DLA_ORDER_IN_STAGE;
		}
		else if(attributeName.equals("internalDpmsRequest")) {
			return ATTRIBUTE_INTERNAL_DPMS_REQUEST;
		}
		else if(attributeName.equals("internalNolRequest")) {
			return ATTRIBUTE_INTERNAL_NOL_REQUEST;
		}
		else if(attributeName.equals("mrCreation")) {
			return ATTRIBUTE_MR_CREATION;
		}
		else if(attributeName.equals("buyOrderCreation")) {
			return ATTRIBUTE_BUY_ORDER_CREATION;
		}
		else if(attributeName.equals("haasOrderIssued")) {
			return ATTRIBUTE_HAAS_ORDER_ISSUED;
		}
		else if(attributeName.equals("airgas997Issued")) {
			return ATTRIBUTE_AIRGAS_997_ISSUED;
		}
		else if(attributeName.equals("airgas997Received")) {
			return ATTRIBUTE_AIRGAS_997_RECEIVED;
		}
		else if(attributeName.equals("airgas855Issued")) {
			return ATTRIBUTE_AIRGAS_855_ISSUED;
		}
		else if(attributeName.equals("airgas855Received")) {
			return ATTRIBUTE_AIRGAS_855_RECEIVED;
		}
		else if(attributeName.equals("poConfirmDate")) {
			return ATTRIBUTE_PO_CONFIRM_DATE;
		}
		else if(attributeName.equals("airgas856Issued")) {
			return ATTRIBUTE_AIRGAS_856_ISSUED;
		}
		else if(attributeName.equals("airgas856Received")) {
			return ATTRIBUTE_AIRGAS_856_RECEIVED;
		}
		else if(attributeName.equals("dla856Created")) {
			return ATTRIBUTE_DLA_856_CREATED;
		}
		else if(attributeName.equals("dla856ToEllis")) {
			return ATTRIBUTE_DLA_856_TO_ELLIS;
		}
		else if(attributeName.equals("dla856ToWawf")) {
			return ATTRIBUTE_DLA_856_TO_WAWF;
		}
		else if(attributeName.equals("dla945Created")) {
			return ATTRIBUTE_DLA_945_CREATED;
		}
		else if(attributeName.equals("dla945Sent")) {
			return ATTRIBUTE_DLA_945_SENT;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, DlaGasTrackingDetailViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(DlaGasTrackingDetailViewBean dlaGasTrackingDetailViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("transactionType", "SearchCriterion.EQUALS",
			"" + dlaGasTrackingDetailViewBean.getTransactionType());

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


	public int delete(DlaGasTrackingDetailViewBean dlaGasTrackingDetailViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("transactionType", "SearchCriterion.EQUALS",
			"" + dlaGasTrackingDetailViewBean.getTransactionType());

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
	public int insert(DlaGasTrackingDetailViewBean dlaGasTrackingDetailViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(dlaGasTrackingDetailViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(DlaGasTrackingDetailViewBean dlaGasTrackingDetailViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_TRANSACTION_TYPE + "," +
			ATTRIBUTE_EXPORT_FLAG + "," +
			ATTRIBUTE_CUSTOMER_PO_NO + "," +
			ATTRIBUTE_CUSTOMER_PO_LINE_NO + "," +
			ATTRIBUTE_MR + "," +
			ATTRIBUTE_MR_LINE + "," +
			ATTRIBUTE_CAT_PART_NO + "," +
			ATTRIBUTE_QUANTITY + "," +
			ATTRIBUTE_UOM + "," +
			ATTRIBUTE_UNIT_PRICE + "," +
			ATTRIBUTE_EXTENDED_PRICE + "," +
			ATTRIBUTE_DESIRED_SHIP_DATE + "," +
			ATTRIBUTE_DESIRED_DELIVERY_DATE + "," +
			ATTRIBUTE_ACTUAL_SHIP_DATE + "," +
			ATTRIBUTE_DAYS_LATE + "," +
			ATTRIBUTE_DLA_ORDER_ISSUED + "," +
			ATTRIBUTE_DLA_ORDER_RECEIVED + "," +
			ATTRIBUTE_DLA_ORDER_IN_STAGE + "," +
			ATTRIBUTE_INTERNAL_DPMS_REQUEST + "," +
			ATTRIBUTE_INTERNAL_NOL_REQUEST + "," +
			ATTRIBUTE_MR_CREATION + "," +
			ATTRIBUTE_BUY_ORDER_CREATION + "," +
			ATTRIBUTE_HAAS_ORDER_ISSUED + "," +
			ATTRIBUTE_AIRGAS_997_ISSUED + "," +
			ATTRIBUTE_AIRGAS_997_RECEIVED + "," +
			ATTRIBUTE_AIRGAS_855_ISSUED + "," +
			ATTRIBUTE_AIRGAS_855_RECEIVED + "," +
			ATTRIBUTE_PO_CONFIRM_DATE + "," +
			ATTRIBUTE_AIRGAS_856_ISSUED + "," +
			ATTRIBUTE_AIRGAS_856_RECEIVED + "," +
			ATTRIBUTE_DLA_856_CREATED + "," +
			ATTRIBUTE_DLA_856_TO_ELLIS + "," +
			ATTRIBUTE_DLA_856_TO_WAWF + "," +
			ATTRIBUTE_DLA_945_CREATED + "," +
			ATTRIBUTE_DLA_945_SENT + ")" +
			" values (" +
			SqlHandler.delimitString(dlaGasTrackingDetailViewBean.getTransactionType()) + "," +
			SqlHandler.delimitString(dlaGasTrackingDetailViewBean.getExportFlag()) + "," +
			SqlHandler.delimitString(dlaGasTrackingDetailViewBean.getCustomerPoNo()) + "," +
			SqlHandler.delimitString(dlaGasTrackingDetailViewBean.getCustomerPoLineNo()) + "," +
			dlaGasTrackingDetailViewBean.getMr() + "," +
			SqlHandler.delimitString(dlaGasTrackingDetailViewBean.getMrLine()) + "," +
			SqlHandler.delimitString(dlaGasTrackingDetailViewBean.getCatPartNo()) + "," +
			dlaGasTrackingDetailViewBean.getQuantity() + "," +
			SqlHandler.delimitString(dlaGasTrackingDetailViewBean.getUom()) + "," +
			dlaGasTrackingDetailViewBean.getUnitPrice() + "," +
			dlaGasTrackingDetailViewBean.getExtendedPrice() + "," +
			DateHandler.getOracleToDateFunction(dlaGasTrackingDetailViewBean.getDesiredShipDate()) + "," +
			DateHandler.getOracleToDateFunction(dlaGasTrackingDetailViewBean.getDesiredDeliveryDate()) + "," +
			DateHandler.getOracleToDateFunction(dlaGasTrackingDetailViewBean.getActualShipDate()) + "," +
			dlaGasTrackingDetailViewBean.getDaysLate() + "," +
			DateHandler.getOracleToDateFunction(dlaGasTrackingDetailViewBean.getDlaOrderIssued()) + "," +
			DateHandler.getOracleToDateFunction(dlaGasTrackingDetailViewBean.getDlaOrderReceived()) + "," +
			DateHandler.getOracleToDateFunction(dlaGasTrackingDetailViewBean.getDlaOrderInStage()) + "," +
			DateHandler.getOracleToDateFunction(dlaGasTrackingDetailViewBean.getInternalDpmsRequest()) + "," +
			DateHandler.getOracleToDateFunction(dlaGasTrackingDetailViewBean.getInternalNolRequest()) + "," +
			DateHandler.getOracleToDateFunction(dlaGasTrackingDetailViewBean.getMrCreation()) + "," +
			DateHandler.getOracleToDateFunction(dlaGasTrackingDetailViewBean.getBuyOrderCreation()) + "," +
			DateHandler.getOracleToDateFunction(dlaGasTrackingDetailViewBean.getHaasOrderIssued()) + "," +
			DateHandler.getOracleToDateFunction(dlaGasTrackingDetailViewBean.getAirgas997Issued()) + "," +
			DateHandler.getOracleToDateFunction(dlaGasTrackingDetailViewBean.getAirgas997Received()) + "," +
			DateHandler.getOracleToDateFunction(dlaGasTrackingDetailViewBean.getAirgas855Issued()) + "," +
			DateHandler.getOracleToDateFunction(dlaGasTrackingDetailViewBean.getAirgas855Received()) + "," +
			DateHandler.getOracleToDateFunction(dlaGasTrackingDetailViewBean.getPoConfirmDate()) + "," +
			DateHandler.getOracleToDateFunction(dlaGasTrackingDetailViewBean.getAirgas856Issued()) + "," +
			DateHandler.getOracleToDateFunction(dlaGasTrackingDetailViewBean.getAirgas856Received()) + "," +
			DateHandler.getOracleToDateFunction(dlaGasTrackingDetailViewBean.getDla856Created()) + "," +
			DateHandler.getOracleToDateFunction(dlaGasTrackingDetailViewBean.getDla856ToEllis()) + "," +
			DateHandler.getOracleToDateFunction(dlaGasTrackingDetailViewBean.getDla856ToWawf()) + "," +
			DateHandler.getOracleToDateFunction(dlaGasTrackingDetailViewBean.getDla945Created()) + "," +
			DateHandler.getOracleToDateFunction(dlaGasTrackingDetailViewBean.getDla945Sent()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(DlaGasTrackingDetailViewBean dlaGasTrackingDetailViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(dlaGasTrackingDetailViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(DlaGasTrackingDetailViewBean dlaGasTrackingDetailViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_TRANSACTION_TYPE + "=" + 
				SqlHandler.delimitString(dlaGasTrackingDetailViewBean.getTransactionType()) + "," +
			ATTRIBUTE_EXPORT_FLAG + "=" + 
				SqlHandler.delimitString(dlaGasTrackingDetailViewBean.getExportFlag()) + "," +
			ATTRIBUTE_CUSTOMER_PO_NO + "=" + 
				SqlHandler.delimitString(dlaGasTrackingDetailViewBean.getCustomerPoNo()) + "," +
			ATTRIBUTE_CUSTOMER_PO_LINE_NO + "=" + 
				SqlHandler.delimitString(dlaGasTrackingDetailViewBean.getCustomerPoLineNo()) + "," +
			ATTRIBUTE_MR + "=" + 
				StringHandler.nullIfZero(dlaGasTrackingDetailViewBean.getMr()) + "," +
			ATTRIBUTE_MR_LINE + "=" + 
				SqlHandler.delimitString(dlaGasTrackingDetailViewBean.getMrLine()) + "," +
			ATTRIBUTE_CAT_PART_NO + "=" + 
				SqlHandler.delimitString(dlaGasTrackingDetailViewBean.getCatPartNo()) + "," +
			ATTRIBUTE_QUANTITY + "=" + 
				StringHandler.nullIfZero(dlaGasTrackingDetailViewBean.getQuantity()) + "," +
			ATTRIBUTE_UOM + "=" + 
				SqlHandler.delimitString(dlaGasTrackingDetailViewBean.getUom()) + "," +
			ATTRIBUTE_UNIT_PRICE + "=" + 
				StringHandler.nullIfZero(dlaGasTrackingDetailViewBean.getUnitPrice()) + "," +
			ATTRIBUTE_EXTENDED_PRICE + "=" + 
				StringHandler.nullIfZero(dlaGasTrackingDetailViewBean.getExtendedPrice()) + "," +
			ATTRIBUTE_DESIRED_SHIP_DATE + "=" + 
				DateHandler.getOracleToDateFunction(dlaGasTrackingDetailViewBean.getDesiredShipDate()) + "," +
			ATTRIBUTE_DESIRED_DELIVERY_DATE + "=" + 
				DateHandler.getOracleToDateFunction(dlaGasTrackingDetailViewBean.getDesiredDeliveryDate()) + "," +
			ATTRIBUTE_ACTUAL_SHIP_DATE + "=" + 
				DateHandler.getOracleToDateFunction(dlaGasTrackingDetailViewBean.getActualShipDate()) + "," +
			ATTRIBUTE_DAYS_LATE + "=" + 
				StringHandler.nullIfZero(dlaGasTrackingDetailViewBean.getDaysLate()) + "," +
			ATTRIBUTE_DLA_ORDER_ISSUED + "=" + 
				DateHandler.getOracleToDateFunction(dlaGasTrackingDetailViewBean.getDlaOrderIssued()) + "," +
			ATTRIBUTE_DLA_ORDER_RECEIVED + "=" + 
				DateHandler.getOracleToDateFunction(dlaGasTrackingDetailViewBean.getDlaOrderReceived()) + "," +
			ATTRIBUTE_DLA_ORDER_IN_STAGE + "=" + 
				DateHandler.getOracleToDateFunction(dlaGasTrackingDetailViewBean.getDlaOrderInStage()) + "," +
			ATTRIBUTE_INTERNAL_DPMS_REQUEST + "=" + 
				DateHandler.getOracleToDateFunction(dlaGasTrackingDetailViewBean.getInternalDpmsRequest()) + "," +
			ATTRIBUTE_INTERNAL_NOL_REQUEST + "=" + 
				DateHandler.getOracleToDateFunction(dlaGasTrackingDetailViewBean.getInternalNolRequest()) + "," +
			ATTRIBUTE_MR_CREATION + "=" + 
				DateHandler.getOracleToDateFunction(dlaGasTrackingDetailViewBean.getMrCreation()) + "," +
			ATTRIBUTE_BUY_ORDER_CREATION + "=" + 
				DateHandler.getOracleToDateFunction(dlaGasTrackingDetailViewBean.getBuyOrderCreation()) + "," +
			ATTRIBUTE_HAAS_ORDER_ISSUED + "=" + 
				DateHandler.getOracleToDateFunction(dlaGasTrackingDetailViewBean.getHaasOrderIssued()) + "," +
			ATTRIBUTE_AIRGAS_997_ISSUED + "=" + 
				DateHandler.getOracleToDateFunction(dlaGasTrackingDetailViewBean.getAirgas997Issued()) + "," +
			ATTRIBUTE_AIRGAS_997_RECEIVED + "=" + 
				DateHandler.getOracleToDateFunction(dlaGasTrackingDetailViewBean.getAirgas997Received()) + "," +
			ATTRIBUTE_AIRGAS_855_ISSUED + "=" + 
				DateHandler.getOracleToDateFunction(dlaGasTrackingDetailViewBean.getAirgas855Issued()) + "," +
			ATTRIBUTE_AIRGAS_855_RECEIVED + "=" + 
				DateHandler.getOracleToDateFunction(dlaGasTrackingDetailViewBean.getAirgas855Received()) + "," +
			ATTRIBUTE_PO_CONFIRM_DATE + "=" + 
				DateHandler.getOracleToDateFunction(dlaGasTrackingDetailViewBean.getPoConfirmDate()) + "," +
			ATTRIBUTE_AIRGAS_856_ISSUED + "=" + 
				DateHandler.getOracleToDateFunction(dlaGasTrackingDetailViewBean.getAirgas856Issued()) + "," +
			ATTRIBUTE_AIRGAS_856_RECEIVED + "=" + 
				DateHandler.getOracleToDateFunction(dlaGasTrackingDetailViewBean.getAirgas856Received()) + "," +
			ATTRIBUTE_DLA_856_CREATED + "=" + 
				DateHandler.getOracleToDateFunction(dlaGasTrackingDetailViewBean.getDla856Created()) + "," +
			ATTRIBUTE_DLA_856_TO_ELLIS + "=" + 
				DateHandler.getOracleToDateFunction(dlaGasTrackingDetailViewBean.getDla856ToEllis()) + "," +
			ATTRIBUTE_DLA_856_TO_WAWF + "=" + 
				DateHandler.getOracleToDateFunction(dlaGasTrackingDetailViewBean.getDla856ToWawf()) + "," +
			ATTRIBUTE_DLA_945_CREATED + "=" + 
				DateHandler.getOracleToDateFunction(dlaGasTrackingDetailViewBean.getDla945Created()) + "," +
			ATTRIBUTE_DLA_945_SENT + "=" + 
				DateHandler.getOracleToDateFunction(dlaGasTrackingDetailViewBean.getDla945Sent()) + " " +
			"where " + ATTRIBUTE_TRANSACTION_TYPE + "=" +
				dlaGasTrackingDetailViewBean.getTransactionType();

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

		Collection dlaGasTrackingDetailViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);
        if(log.isDebugEnabled()){
            log.debug("QUERY:" + query);
        }
		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			DlaGasTrackingDetailViewBean dlaGasTrackingDetailViewBean = new DlaGasTrackingDetailViewBean();
			load(dataSetRow, dlaGasTrackingDetailViewBean);
			dlaGasTrackingDetailViewBeanColl.add(dlaGasTrackingDetailViewBean);
		}

		return dlaGasTrackingDetailViewBeanColl;
	}
}