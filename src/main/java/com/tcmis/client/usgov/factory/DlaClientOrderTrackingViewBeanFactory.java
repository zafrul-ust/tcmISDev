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
import com.tcmis.client.usgov.beans.DlaClientOrderTrackingViewBean;


/******************************************************************************
 * CLASSNAME: DlaClientOrderTrackingViewBeanFactory <br>
 * @version: 1.0, Aug 8, 2008 <br>
 *****************************************************************************/


public class DlaClientOrderTrackingViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_DOC_NUM = "DOC_NUM";
	public String ATTRIBUTE_CONTRACT_NUMBER = "CONTRACT_NUMBER";
	public String ATTRIBUTE_CALL_NO = "CALL_NO";
	public String ATTRIBUTE_SHIP_TO_DODAAC = "SHIP_TO_DODAAC";
	public String ATTRIBUTE_MARK_FOR_DODAAC = "MARK_FOR_DODAAC";
	public String ATTRIBUTE_TRANSACTION_TYPE = "TRANSACTION_TYPE";
	public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
	public String ATTRIBUTE_DATE_997 = "DATE_997";
	public String ATTRIBUTE_SHIP_TO_OK_DATE = "SHIP_TO_OK_DATE";
	public String ATTRIBUTE_QUANTITY = "QUANTITY";
	public String ATTRIBUTE_UOM = "UOM";
	public String ATTRIBUTE_STATUS = "STATUS";
	public String ATTRIBUTE_TRANSPORTATION_CONTROL_NO = "TRANSPORTATION_CONTROL_NO";
	public String ATTRIBUTE_USGOV_SHIPMENT_NUMBER = "USGOV_SHIPMENT_NUMBER";
	public String ATTRIBUTE_QTY_SHIPPED = "QTY_SHIPPED";
	public String ATTRIBUTE_ACTUAL_SHIP_DATE = "ACTUAL_SHIP_DATE";
	public String ATTRIBUTE_PROJECTED_DATE_SHIPPED = "PROJECTED_DATE_SHIPPED";
	public String ATTRIBUTE_ARRIVAL_DATE = "ARRIVAL_DATE";
	public String ATTRIBUTE_CARRIER = "CARRIER";
	public String ATTRIBUTE_TRACKING_NO = "TRACKING_NO";
	public String ATTRIBUTE_COMMENTS = "COMMENTS";
	public String ATTRIBUTE_OPEN_ORDER = "OPEN_ORDER";
	public String ATTRIBUTE_ADDRESS_CHANGE_REQUEST_ID = "ADDRESS_CHANGE_REQUEST_ID";
	public String ATTRIBUTE_PART_SHORT_NAME = "PART_SHORT_NAME";
	public String ATTRIBUTE_SHIP_TO_LOCATION_ID = "SHIP_TO_LOCATION_ID";
	public String ATTRIBUTE_SHIP_VIA_LOCATION_ID = "SHIP_VIA_LOCATION_ID";
	public String ATTRIBUTE_PRIORITY_RATING = "PRIORITY_RATING";
	public String ATTRIBUTE_DISPLAY_STATUS = "DISPLAY_STATUS";
	public String ATTRIBUTE_PO_SUFFIX = "PO_SUFFIX";
	public String ATTRIBUTE_ORIG_PRIORITY_RATING = "ORIG_PRIORITY_RATING";
	public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
	public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
	public String ATTRIBUTE_SHIPMENT_ID = "SHIPMENT_ID";
	public String ATTRIBUTE_OPEN = "OPEN";
	public String ATTRIBUTE_RADIAN_PO = "RADIAN_PO";
	public String ATTRIBUTE_PO_LINE = "PO_LINE";
	public String ATTRIBUTE_PROJECT_CODE = "PROJECT_CODE";
	public String ATTRIBUTE_PROJECT_NAME = "PROJECT_NAME";
	public String ATTRIBUTE_VERIFIED_FOR_ASN = "VERIFIED_FOR_ASN";
	public String ATTRIBUTE_SHIP_FROM_LOCATION_ID = "SHIP_FROM_LOCATION_ID";
	public String ATTRIBUTE_SUPPLIER_SALES_ORDER_NO = "SUPPLIER_SALES_ORDER_NO";
	public String ATTRIBUTE_SHIP_FROM_LOCATION_DESC = "SHIP_FROM_LOCATION_DESC";
	public String ATTRIBUTE_EXPEDITE_DATE = "EXPEDITE_DATE";
    public String ATTRIBUTE_MFG_LITERATURE_CONTENT = "MFG_LITERATURE_CONTENT";
    public String ATTRIBUTE_ORDER_STATUS  = "ORDER_STATUS";
    public String ATTRIBUTE_ORDER_DATE  = "ORDER_DATE";
    
    //table name
	public String TABLE = "DLA_CLIENT_ORDER_TRACKING_VIEW";


	//constructor
	public DlaClientOrderTrackingViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("docNum")) {
			return ATTRIBUTE_DOC_NUM;
		}
		else if(attributeName.equals("contractNumber")) {
			return ATTRIBUTE_CONTRACT_NUMBER;
		}
		else if(attributeName.equals("callNo")) {
			return ATTRIBUTE_CALL_NO;
		}
		else if(attributeName.equals("shipToDodaac")) {
			return ATTRIBUTE_SHIP_TO_DODAAC;
		}
		else if(attributeName.equals("markForDodaac")) {
			return ATTRIBUTE_MARK_FOR_DODAAC;
		}
		else if(attributeName.equals("transactionType")) {
			return ATTRIBUTE_TRANSACTION_TYPE;
		}
		else if(attributeName.equals("catPartNo")) {
			return ATTRIBUTE_CAT_PART_NO;
		}
		else if(attributeName.equals("date997")) {
			return ATTRIBUTE_DATE_997;
		}
		else if(attributeName.equals("shipToOkDate")) {
			return ATTRIBUTE_SHIP_TO_OK_DATE;
		}
		else if(attributeName.equals("quantity")) {
			return ATTRIBUTE_QUANTITY;
		}
		else if(attributeName.equals("uom")) {
			return ATTRIBUTE_UOM;
		}
		else if(attributeName.equals("status")) {
			return ATTRIBUTE_STATUS;
		}
		else if(attributeName.equals("transportationControlNo")) {
			return ATTRIBUTE_TRANSPORTATION_CONTROL_NO;
		}
		else if(attributeName.equals("usgovShipmentNumber")) {
			return ATTRIBUTE_USGOV_SHIPMENT_NUMBER;
		}
		else if(attributeName.equals("qtyShipped")) {
			return ATTRIBUTE_QTY_SHIPPED;
		}
		else if(attributeName.equals("actualShipDate")) {
			return ATTRIBUTE_ACTUAL_SHIP_DATE;
		}
		else if(attributeName.equals("projectedDateShipped")) {
			return ATTRIBUTE_PROJECTED_DATE_SHIPPED;
		}
		else if(attributeName.equals("arrivalDate")) {
			return ATTRIBUTE_ARRIVAL_DATE;
		}
		else if(attributeName.equals("carrier")) {
			return ATTRIBUTE_CARRIER;
		}
		else if(attributeName.equals("trackingNo")) {
			return ATTRIBUTE_TRACKING_NO;
		}
		else if(attributeName.equals("comments")) {
			return ATTRIBUTE_COMMENTS;
		}
		else if(attributeName.equals("openOrder")) {
			return ATTRIBUTE_OPEN_ORDER;
		}
		else if(attributeName.equals("addressChangeRequestId")) {
			return ATTRIBUTE_ADDRESS_CHANGE_REQUEST_ID;
		}
		else if(attributeName.equals("partShortName")) {
			return ATTRIBUTE_PART_SHORT_NAME;
		}
		else if(attributeName.equals("shipToLocationId")) {
			return ATTRIBUTE_SHIP_TO_LOCATION_ID;
		}
		else if(attributeName.equals("shipViaLocationId")) {
			return ATTRIBUTE_SHIP_VIA_LOCATION_ID;
		}
		else if(attributeName.equals("priorityRating")) {
			return ATTRIBUTE_PRIORITY_RATING;
		}
		else if(attributeName.equals("displayStatus")) {
			return ATTRIBUTE_DISPLAY_STATUS;
		}
		else if(attributeName.equals("poSuffix")) {
			return ATTRIBUTE_PO_SUFFIX;
		}
		else if(attributeName.equals("origPriorityRating")) {
			return ATTRIBUTE_ORIG_PRIORITY_RATING;
		}
		else if(attributeName.equals("prNumber")) {
			return ATTRIBUTE_PR_NUMBER;
		}
		else if(attributeName.equals("lineItem")) {
			return ATTRIBUTE_LINE_ITEM;
		}
		else if(attributeName.equals("shipmentId")) {
			return ATTRIBUTE_SHIPMENT_ID;
		}
		else if(attributeName.equals("open")) {
			return ATTRIBUTE_OPEN;
		}
		else if(attributeName.equals("radianPo")) {
			return ATTRIBUTE_RADIAN_PO;
		}
		else if(attributeName.equals("poLine")) {
			return ATTRIBUTE_PO_LINE;
		}
		else if(attributeName.equals("projectCode")) {
			return ATTRIBUTE_PROJECT_CODE;
		}
		else if(attributeName.equals("projectName")) {
			return ATTRIBUTE_PROJECT_NAME;
		}
		else if(attributeName.equals("verifiedForAsn")) {
			return ATTRIBUTE_VERIFIED_FOR_ASN;
		}
		else if(attributeName.equals("shipFromLocationId")) {
			return ATTRIBUTE_SHIP_FROM_LOCATION_ID;
		}
		else if(attributeName.equals("supplierSalesOrderNo")) {
			return ATTRIBUTE_SUPPLIER_SALES_ORDER_NO;
		}
		else if(attributeName.equals("shipFromLocationDesc")) {
			return ATTRIBUTE_SHIP_FROM_LOCATION_DESC;
		}
		else if(attributeName.equals("expediteDate")) {
			return ATTRIBUTE_EXPEDITE_DATE;
		}
		else if(attributeName.equals("mfgLiteratureContent")) {
			return ATTRIBUTE_MFG_LITERATURE_CONTENT;
		}    
		else if(attributeName.equals("orderStatus")) {
			return ATTRIBUTE_ORDER_STATUS;
		}
		else if(attributeName.equals("orderDate")) {
			return ATTRIBUTE_ORDER_DATE;
		} 
        else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, DlaClientOrderTrackingViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(DlaClientOrderTrackingViewBean dlaClientOrderTrackingViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("docNum", "SearchCriterion.EQUALS",
			"" + dlaClientOrderTrackingViewBean.getDocNum());

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


	public int delete(DlaClientOrderTrackingViewBean dlaClientOrderTrackingViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("docNum", "SearchCriterion.EQUALS",
			"" + dlaClientOrderTrackingViewBean.getDocNum());

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
	public int insert(DlaClientOrderTrackingViewBean dlaClientOrderTrackingViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(dlaClientOrderTrackingViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(DlaClientOrderTrackingViewBean dlaClientOrderTrackingViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_DOC_NUM + "," +
			ATTRIBUTE_CONTRACT_NUMBER + "," +
			ATTRIBUTE_CALL_NO + "," +
			ATTRIBUTE_SHIP_TO_DODAAC + "," +
			ATTRIBUTE_MARK_FOR_DODAAC + "," +
			ATTRIBUTE_TRANSACTION_TYPE + "," +
			ATTRIBUTE_CAT_PART_NO + "," +
			ATTRIBUTE_DATE_997 + "," +
			ATTRIBUTE_SHIP_TO_OK_DATE + "," +
			ATTRIBUTE_QUANTITY + "," +
			ATTRIBUTE_UOM + "," +
			ATTRIBUTE_STATUS + "," +
			ATTRIBUTE_TRANSPORTATION_CONTROL_NO + "," +
			ATTRIBUTE_USGOV_SHIPMENT_NUMBER + "," +
			ATTRIBUTE_QTY_SHIPPED + "," +
			ATTRIBUTE_ACTUAL_SHIP_DATE + "," +
			ATTRIBUTE_PROJECTED_DATE_SHIPPED + "," +
			ATTRIBUTE_ARRIVAL_DATE + "," +
			ATTRIBUTE_CARRIER + "," +
			ATTRIBUTE_TRACKING_NO + "," +
			ATTRIBUTE_COMMENTS + "," +
			ATTRIBUTE_OPEN_ORDER + "," +
			ATTRIBUTE_ADDRESS_CHANGE_REQUEST_ID + "," +
			ATTRIBUTE_PART_SHORT_NAME + "," +
			ATTRIBUTE_SHIP_TO_LOCATION_ID + "," +
			ATTRIBUTE_SHIP_VIA_LOCATION_ID + "," +
			ATTRIBUTE_PRIORITY_RATING + "," +
			ATTRIBUTE_DISPLAY_STATUS + "," +
			ATTRIBUTE_PO_SUFFIX + "," +
			ATTRIBUTE_ORIG_PRIORITY_RATING + "," +
			ATTRIBUTE_PR_NUMBER + "," +
			ATTRIBUTE_LINE_ITEM + "," +
			ATTRIBUTE_SHIPMENT_ID + "," +
			ATTRIBUTE_OPEN + "," +
			ATTRIBUTE_RADIAN_PO + "," +
			ATTRIBUTE_PO_LINE + "," +
			ATTRIBUTE_PROJECT_CODE + "," +
			ATTRIBUTE_PROJECT_NAME + "," +
			ATTRIBUTE_VERIFIED_FOR_ASN + "," +
			ATTRIBUTE_SHIP_FROM_LOCATION_ID + "," +
			ATTRIBUTE_SUPPLIER_SALES_ORDER_NO + "," +
			ATTRIBUTE_SHIP_FROM_LOCATION_DESC + ")" +
			" values (" +
			SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getDocNum()) + "," +
			SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getContractNumber()) + "," +
			SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getCallNo()) + "," +
			SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getShipToDodaac()) + "," +
			SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getMarkForDodaac()) + "," +
			SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getTransactionType()) + "," +
			SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getCatPartNo()) + "," +
			DateHandler.getOracleToDateFunction(dlaClientOrderTrackingViewBean.getDate997()) + "," +
			DateHandler.getOracleToDateFunction(dlaClientOrderTrackingViewBean.getShipToOkDate()) + "," +
			dlaClientOrderTrackingViewBean.getQuantity() + "," +
			SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getUom()) + "," +
			SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getStatus()) + "," +
			SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getTransportationControlNo()) + "," +
			SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getUsgovShipmentNumber()) + "," +
			dlaClientOrderTrackingViewBean.getQtyShipped() + "," +
			DateHandler.getOracleToDateFunction(dlaClientOrderTrackingViewBean.getActualShipDate()) + "," +
			DateHandler.getOracleToDateFunction(dlaClientOrderTrackingViewBean.getProjectedDateShipped()) + "," +
			DateHandler.getOracleToDateFunction(dlaClientOrderTrackingViewBean.getArrivalDate()) + "," +
			SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getCarrier()) + "," +
			SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getTrackingNo()) + "," +
			SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getComments()) + "," +
			SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getOpenOrder()) + "," +
			dlaClientOrderTrackingViewBean.getAddressChangeRequestId() + "," +
			SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getPartShortName()) + "," +
			SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getShipToLocationId()) + "," +
			SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getShipViaLocationId()) + "," +
			SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getPriorityRating()) + "," +
			SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getDisplayStatus()) + "," +
			SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getPoSuffix()) + "," +
			SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getOrigPriorityRating()) + "," +
			dlaClientOrderTrackingViewBean.getPrNumber() + "," +
			SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getLineItem()) + "," +
			dlaClientOrderTrackingViewBean.getShipmentId() + "," +
			SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getOpen()) + "," +
			dlaClientOrderTrackingViewBean.getRadianPo() + "," +
			dlaClientOrderTrackingViewBean.getPoLine() + "," +
			SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getProjectCode()) + "," +
			SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getProjectName()) + "," +
			SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getVerifiedForAsn()) + "," +
			SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getShipFromLocationId()) + "," +
			SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getSupplierSalesOrderNo()) + "," +
			SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getShipFromLocationDesc()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(DlaClientOrderTrackingViewBean dlaClientOrderTrackingViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(dlaClientOrderTrackingViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(DlaClientOrderTrackingViewBean dlaClientOrderTrackingViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_DOC_NUM + "=" + 
				SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getDocNum()) + "," +
			ATTRIBUTE_CONTRACT_NUMBER + "=" + 
				SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getContractNumber()) + "," +
			ATTRIBUTE_CALL_NO + "=" + 
				SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getCallNo()) + "," +
			ATTRIBUTE_SHIP_TO_DODAAC + "=" + 
				SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getShipToDodaac()) + "," +
			ATTRIBUTE_MARK_FOR_DODAAC + "=" + 
				SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getMarkForDodaac()) + "," +
			ATTRIBUTE_TRANSACTION_TYPE + "=" + 
				SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getTransactionType()) + "," +
			ATTRIBUTE_CAT_PART_NO + "=" + 
				SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getCatPartNo()) + "," +
			ATTRIBUTE_DATE_997 + "=" + 
				DateHandler.getOracleToDateFunction(dlaClientOrderTrackingViewBean.getDate997()) + "," +
			ATTRIBUTE_SHIP_TO_OK_DATE + "=" + 
				DateHandler.getOracleToDateFunction(dlaClientOrderTrackingViewBean.getShipToOkDate()) + "," +
			ATTRIBUTE_QUANTITY + "=" + 
				StringHandler.nullIfZero(dlaClientOrderTrackingViewBean.getQuantity()) + "," +
			ATTRIBUTE_UOM + "=" + 
				SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getUom()) + "," +
			ATTRIBUTE_STATUS + "=" + 
				SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getStatus()) + "," +
			ATTRIBUTE_TRANSPORTATION_CONTROL_NO + "=" + 
				SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getTransportationControlNo()) + "," +
			ATTRIBUTE_USGOV_SHIPMENT_NUMBER + "=" + 
				SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getUsgovShipmentNumber()) + "," +
			ATTRIBUTE_QTY_SHIPPED + "=" + 
				StringHandler.nullIfZero(dlaClientOrderTrackingViewBean.getQtyShipped()) + "," +
			ATTRIBUTE_ACTUAL_SHIP_DATE + "=" + 
				DateHandler.getOracleToDateFunction(dlaClientOrderTrackingViewBean.getActualShipDate()) + "," +
			ATTRIBUTE_PROJECTED_DATE_SHIPPED + "=" + 
				DateHandler.getOracleToDateFunction(dlaClientOrderTrackingViewBean.getProjectedDateShipped()) + "," +
			ATTRIBUTE_ARRIVAL_DATE + "=" + 
				DateHandler.getOracleToDateFunction(dlaClientOrderTrackingViewBean.getArrivalDate()) + "," +
			ATTRIBUTE_CARRIER + "=" + 
				SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getCarrier()) + "," +
			ATTRIBUTE_TRACKING_NO + "=" + 
				SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getTrackingNo()) + "," +
			ATTRIBUTE_COMMENTS + "=" + 
				SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getComments()) + "," +
			ATTRIBUTE_OPEN_ORDER + "=" + 
				SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getOpenOrder()) + "," +
			ATTRIBUTE_ADDRESS_CHANGE_REQUEST_ID + "=" + 
				StringHandler.nullIfZero(dlaClientOrderTrackingViewBean.getAddressChangeRequestId()) + "," +
			ATTRIBUTE_PART_SHORT_NAME + "=" + 
				SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getPartShortName()) + "," +
			ATTRIBUTE_SHIP_TO_LOCATION_ID + "=" + 
				SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getShipToLocationId()) + "," +
			ATTRIBUTE_SHIP_VIA_LOCATION_ID + "=" + 
				SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getShipViaLocationId()) + "," +
			ATTRIBUTE_PRIORITY_RATING + "=" + 
				SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getPriorityRating()) + "," +
			ATTRIBUTE_DISPLAY_STATUS + "=" + 
				SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getDisplayStatus()) + "," +
			ATTRIBUTE_PO_SUFFIX + "=" + 
				SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getPoSuffix()) + "," +
			ATTRIBUTE_ORIG_PRIORITY_RATING + "=" + 
				SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getOrigPriorityRating()) + "," +
			ATTRIBUTE_PR_NUMBER + "=" + 
				StringHandler.nullIfZero(dlaClientOrderTrackingViewBean.getPrNumber()) + "," +
			ATTRIBUTE_LINE_ITEM + "=" + 
				SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getLineItem()) + "," +
			ATTRIBUTE_SHIPMENT_ID + "=" + 
				StringHandler.nullIfZero(dlaClientOrderTrackingViewBean.getShipmentId()) + "," +
			ATTRIBUTE_OPEN + "=" + 
				SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getOpen()) + "," +
			ATTRIBUTE_RADIAN_PO + "=" + 
				StringHandler.nullIfZero(dlaClientOrderTrackingViewBean.getRadianPo()) + "," +
			ATTRIBUTE_PO_LINE + "=" + 
				StringHandler.nullIfZero(dlaClientOrderTrackingViewBean.getPoLine()) + "," +
			ATTRIBUTE_PROJECT_CODE + "=" + 
				SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getProjectCode()) + "," +
			ATTRIBUTE_PROJECT_NAME + "=" + 
				SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getProjectName()) + "," +
			ATTRIBUTE_VERIFIED_FOR_ASN + "=" + 
				SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getVerifiedForAsn()) + "," +
			ATTRIBUTE_SHIP_FROM_LOCATION_ID + "=" + 
				SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getShipFromLocationId()) + "," +
			ATTRIBUTE_SUPPLIER_SALES_ORDER_NO + "=" + 
				SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getSupplierSalesOrderNo()) + "," +
			ATTRIBUTE_SHIP_FROM_LOCATION_DESC + "=" + 
				SqlHandler.delimitString(dlaClientOrderTrackingViewBean.getShipFromLocationDesc()) + " " +
			"where " + ATTRIBUTE_DOC_NUM + "=" +
				dlaClientOrderTrackingViewBean.getDocNum();

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

		Collection dlaClientOrderTrackingViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
//log.debug("ds:"+dataSetRow);
            DlaClientOrderTrackingViewBean dlaClientOrderTrackingViewBean = new DlaClientOrderTrackingViewBean();
			load(dataSetRow, dlaClientOrderTrackingViewBean);
			dlaClientOrderTrackingViewBeanColl.add(dlaClientOrderTrackingViewBean);
		}

		return dlaClientOrderTrackingViewBeanColl;
	}
}