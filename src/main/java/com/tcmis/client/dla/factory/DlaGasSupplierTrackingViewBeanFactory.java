package com.tcmis.client.dla.factory;


import com.tcmis.client.dla.beans.DlaGasSupplierTrackingViewBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SortCriteria;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;


/******************************************************************************
 * CLASSNAME: DlaGasSupplierTrackingViewBeanFactory <br>
 * @version: 1.0, Nov 25, 2008 <br>
 *****************************************************************************/


public class DlaGasSupplierTrackingViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_SUPPLIER_NAME = "SUPPLIER_NAME";
	public String ATTRIBUTE_SHIP_TO_INFO = "SHIP_TO_INFO";
	public String ATTRIBUTE_SHIP_FROM = "SHIP_FROM";
	public String ATTRIBUTE_ISSUER = "ISSUER";
	public String ATTRIBUTE_PART_SHORT_NAME = "PART_SHORT_NAME";
	public String ATTRIBUTE_IPG = "IPG";
	public String ATTRIBUTE_AIRGAS_IPG = "AIRGAS_IPG";
	public String ATTRIBUTE_ORDER_STATUS = "ORDER_STATUS";
	public String ATTRIBUTE_COMMENTS = "COMMENTS";
	public String ATTRIBUTE_RADIAN_PO = "RADIAN_PO";
	public String ATTRIBUTE_PO_LINE = "PO_LINE";
	public String ATTRIBUTE_DATE_CREATED = "DATE_CREATED";
	public String ATTRIBUTE_DATE_SENT = "DATE_SENT";
	public String ATTRIBUTE_DATE_ACKNOWLEDGEMENT = "DATE_ACKNOWLEDGEMENT";
	public String ATTRIBUTE_SHIP_TO_LOCATION_ID = "SHIP_TO_LOCATION_ID";
	public String ATTRIBUTE_UNIT_PRICE = "UNIT_PRICE";
	public String ATTRIBUTE_DATE_FIRST_CONFIRMED = "DATE_FIRST_CONFIRMED";
	public String ATTRIBUTE_DELIVERY_COMMENTS = "DELIVERY_COMMENTS";
	public String ATTRIBUTE_PROMISED_DATE = "PROMISED_DATE";
	public String ATTRIBUTE_PO_LINE_QUANTITY = "PO_LINE_QUANTITY";
	public String ATTRIBUTE_SHIP_FROM_LOCATION_ID = "SHIP_FROM_LOCATION_ID";
	public String ATTRIBUTE_SUPPLIER = "SUPPLIER";
	public String ATTRIBUTE_SUPPLIER_SALES_ORDER_NO = "SUPPLIER_SALES_ORDER_NO";
	public String ATTRIBUTE_VENDOR_SHIP_DATE = "VENDOR_SHIP_DATE";
	public String ATTRIBUTE_ORDER_QUANTITY = "ORDER_QUANTITY";
	public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
	public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
	public String ATTRIBUTE_MARK_FOR_LOCATION_ID = "MARK_FOR_LOCATION_ID";
	public String ATTRIBUTE_PO_NUMBER = "PO_NUMBER";
	public String ATTRIBUTE_FAC_PART_NO = "FAC_PART_NO";
	public String ATTRIBUTE_CANCEL_STATUS = "CANCEL_STATUS";
	public String ATTRIBUTE_CANCEL_COMMENT = "CANCEL_COMMENT";
	public String ATTRIBUTE_CUSTOMER_HAAS_CONTRACT_ID = "CUSTOMER_HAAS_CONTRACT_ID";
	public String ATTRIBUTE_RELEASE_NUM = "RELEASE_NUM";
	public String ATTRIBUTE_MILSTRIP_CODE = "MILSTRIP_CODE";
	public String ATTRIBUTE_DESIRED_SHIP_DATE = "DESIRED_SHIP_DATE";
	public String ATTRIBUTE_SHIP_TO_DODAAC = "SHIP_TO_DODAAC";
	public String ATTRIBUTE_SHIP_VIA_DODAAC = "SHIP_VIA_DODAAC";
	public String ATTRIBUTE_OCONUS = "OCONUS";
	public String ATTRIBUTE_ORDER_DATE = "ORDER_DATE";
	public String ATTRIBUTE_TRANSACTION_REF_NUM = "TRANSACTION_REF_NUM";
	public String ATTRIBUTE_ISSUE_QUANTITY = "ISSUE_QUANTITY";
	public String ATTRIBUTE_SHIPMENT_ID = "SHIPMENT_ID";
	public String ATTRIBUTE_DATE_SHIPPED = "DATE_SHIPPED";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_TRACKING_NUMBER = "TRACKING_NUMBER";
	public String ATTRIBUTE_CARRIER_NAME = "CARRIER_NAME";
	public String ATTRIBUTE_ASN_STATUS = "ASN_STATUS";
	public String ATTRIBUTE_TRANSPORTATION_CONTROL_NUM = "TRANSPORTATION_CONTROL_NUM";
	public String ATTRIBUTE_OPEN_QUANTITY = "OPEN_QUANTITY";
	public String ATTRIBUTE_ORDER_AGE = "ORDER_AGE";
	public String ATTRIBUTE_EXPEDITE_DATE = "EXPEDITE_DATE";
	public String ATTRIBUTE_ORIGINAL_TRANSACTION_TYPE = "ORIGINAL_TRANSACTION_TYPE";
	public String ATTRIBUTE_ORIGINAL_SHIP_FROM = "ORIGINAL_SHIP_FROM";
	public String ATTRIBUTE_HUB_NAME="HUB_NAME";
	public String ATTRIBUTE_INVENTORY_GROUP_NAME="INVENTORY_GROUP_NAME";
	public String ATTRIBUTE_ORIGIN_INSPECTION_REQUIRED="ORIGIN_INSPECTION_REQUIRED";
	public String ATTRIBUTE_DATE_SENT_WAWF="DATE_SENT_WAWF";

	//table name
	public String TABLE = "DLA_GAS_SUPPLIER_TRACKING_VIEW";


	//constructor
	public DlaGasSupplierTrackingViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("supplierName")) {
			return ATTRIBUTE_SUPPLIER_NAME;
		}
		else if(attributeName.equals("shipToInfo")) {
			return ATTRIBUTE_SHIP_TO_INFO;
		}
		else if(attributeName.equals("shipFrom")) {
			return ATTRIBUTE_SHIP_FROM;
		}
		else if(attributeName.equals("issuer")) {
			return ATTRIBUTE_ISSUER;
		}
		else if(attributeName.equals("partShortName")) {
			return ATTRIBUTE_PART_SHORT_NAME;
		}
		else if(attributeName.equals("ipg")) {
			return ATTRIBUTE_IPG;
		}
		else if(attributeName.equals("airgasIpg")) {
			return ATTRIBUTE_AIRGAS_IPG;
		}
		else if(attributeName.equals("orderStatus")) {
			return ATTRIBUTE_ORDER_STATUS;
		}
		else if(attributeName.equals("comments")) {
			return ATTRIBUTE_COMMENTS;
		}
		else if(attributeName.equals("radianPo")) {
			return ATTRIBUTE_RADIAN_PO;
		}
		else if(attributeName.equals("poLine")) {
			return ATTRIBUTE_PO_LINE;
		}
		else if(attributeName.equals("dateCreated")) {
			return ATTRIBUTE_DATE_CREATED;
		}
		else if(attributeName.equals("dateSent")) {
			return ATTRIBUTE_DATE_SENT;
		}
		else if(attributeName.equals("dateAcknowledgement")) {
			return ATTRIBUTE_DATE_ACKNOWLEDGEMENT;
		}
		else if(attributeName.equals("shipToLocationId")) {
			return ATTRIBUTE_SHIP_TO_LOCATION_ID;
		}
		else if(attributeName.equals("unitPrice")) {
			return ATTRIBUTE_UNIT_PRICE;
		}
		else if(attributeName.equals("dateFirstConfirmed")) {
			return ATTRIBUTE_DATE_FIRST_CONFIRMED;
		}
		else if(attributeName.equals("deliveryComments")) {
			return ATTRIBUTE_DELIVERY_COMMENTS;
		}
		else if(attributeName.equals("promisedDate")) {
			return ATTRIBUTE_PROMISED_DATE;
		}
		else if(attributeName.equals("poLineQuantity")) {
			return ATTRIBUTE_PO_LINE_QUANTITY;
		}
		else if(attributeName.equals("shipFromLocationId")) {
			return ATTRIBUTE_SHIP_FROM_LOCATION_ID;
		}
		else if(attributeName.equals("supplier")) {
			return ATTRIBUTE_SUPPLIER;
		}
		else if(attributeName.equals("supplierSalesOrderNo")) {
			return ATTRIBUTE_SUPPLIER_SALES_ORDER_NO;
		}
		else if(attributeName.equals("vendorShipDate")) {
			return ATTRIBUTE_VENDOR_SHIP_DATE;
		}
		else if(attributeName.equals("orderQuantity")) {
			return ATTRIBUTE_ORDER_QUANTITY;
		}
		else if(attributeName.equals("prNumber")) {
			return ATTRIBUTE_PR_NUMBER;
		}
		else if(attributeName.equals("lineItem")) {
			return ATTRIBUTE_LINE_ITEM;
		}
		else if(attributeName.equals("markForLocationId")) {
			return ATTRIBUTE_MARK_FOR_LOCATION_ID;
		}
		else if(attributeName.equals("poNumber")) {
			return ATTRIBUTE_PO_NUMBER;
		}
		else if(attributeName.equals("facPartNo")) {
			return ATTRIBUTE_FAC_PART_NO;
		}
		else if(attributeName.equals("cancelStatus")) {
			return ATTRIBUTE_CANCEL_STATUS;
		}
		else if(attributeName.equals("cancelComment")) {
			return ATTRIBUTE_CANCEL_COMMENT;
		}
		else if(attributeName.equals("customerHaasContractId")) {
			return ATTRIBUTE_CUSTOMER_HAAS_CONTRACT_ID;
		}
		else if(attributeName.equals("releaseNum")) {
			return ATTRIBUTE_RELEASE_NUM;
		}
		else if(attributeName.equals("milstripCode")) {
			return ATTRIBUTE_MILSTRIP_CODE;
		}
		else if(attributeName.equals("desiredShipDate")) {
			return ATTRIBUTE_DESIRED_SHIP_DATE;
		}
		else if(attributeName.equals("shipToDodaac")) {
			return ATTRIBUTE_SHIP_TO_DODAAC;
		}
		else if(attributeName.equals("shipViaDodaac")) {
			return ATTRIBUTE_SHIP_VIA_DODAAC;
		}
		else if(attributeName.equals("oconus")) {
			return ATTRIBUTE_OCONUS;
		}
		else if(attributeName.equals("orderDate")) {
			return ATTRIBUTE_ORDER_DATE;
		}
		else if(attributeName.equals("transactionRefNum")) {
			return ATTRIBUTE_TRANSACTION_REF_NUM;
		}
		else if(attributeName.equals("issueQuantity")) {
			return ATTRIBUTE_ISSUE_QUANTITY;
		}
		else if(attributeName.equals("shipmentId")) {
			return ATTRIBUTE_SHIPMENT_ID;
		}
		else if(attributeName.equals("dateShipped")) {
			return ATTRIBUTE_DATE_SHIPPED;
		}
		else if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("trackingNumber")) {
			return ATTRIBUTE_TRACKING_NUMBER;
		}
		else if(attributeName.equals("carrierName")) {
			return ATTRIBUTE_CARRIER_NAME;
		}
		else if(attributeName.equals("asnStatus")) {
			return ATTRIBUTE_ASN_STATUS;
		}
		else if(attributeName.equals("transportationControlNum")) {
			return ATTRIBUTE_TRANSPORTATION_CONTROL_NUM;
		}
		else if(attributeName.equals("openQuantity")) {
			return ATTRIBUTE_OPEN_QUANTITY;
		}
		else if(attributeName.equals("orderAge")) {
			return ATTRIBUTE_ORDER_AGE;
		}
		else if(attributeName.equals("expediteDate")) {
			return ATTRIBUTE_EXPEDITE_DATE;
		}
		else if(attributeName.equals("originalTransactionType")) {
    		return ATTRIBUTE_ORIGINAL_TRANSACTION_TYPE;
		}
		else if(attributeName.equals("originalShipFrom")) {
    		return ATTRIBUTE_ORIGINAL_SHIP_FROM;
		}
		else if(attributeName.equals("inventoryGroupName")) {
    		return ATTRIBUTE_INVENTORY_GROUP_NAME;
		}
		else if(attributeName.equals("hubName")) {
    		return ATTRIBUTE_HUB_NAME;
		}		
		else if(attributeName.equals("dateSentWawf")) {
    		return ATTRIBUTE_DATE_SENT_WAWF;
		}	
		else if(attributeName.equals("originInspectionRequired")) {
    		return ATTRIBUTE_ORIGIN_INSPECTION_REQUIRED;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, DlaGasSupplierTrackingViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(DlaGasSupplierTrackingViewBean dlaGasSupplierTrackingViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("supplierName", "SearchCriterion.EQUALS",
			"" + dlaGasSupplierTrackingViewBean.getSupplierName());

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


	public int delete(DlaGasSupplierTrackingViewBean dlaGasSupplierTrackingViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("supplierName", "SearchCriterion.EQUALS",
			"" + dlaGasSupplierTrackingViewBean.getSupplierName());

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
	public int insert(DlaGasSupplierTrackingViewBean dlaGasSupplierTrackingViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(dlaGasSupplierTrackingViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(DlaGasSupplierTrackingViewBean dlaGasSupplierTrackingViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_SUPPLIER_NAME + "," +
			ATTRIBUTE_SHIP_TO_INFO + "," +
			ATTRIBUTE_SHIP_FROM + "," +
			ATTRIBUTE_ISSUER + "," +
			ATTRIBUTE_PART_SHORT_NAME + "," +
			ATTRIBUTE_IPG + "," +
			ATTRIBUTE_AIRGAS_IPG + "," +
			ATTRIBUTE_ORDER_STATUS + "," +
			ATTRIBUTE_COMMENTS + "," +
			ATTRIBUTE_RADIAN_PO + "," +
			ATTRIBUTE_PO_LINE + "," +
			ATTRIBUTE_DATE_CREATED + "," +
			ATTRIBUTE_DATE_SENT + "," +
			ATTRIBUTE_DATE_ACKNOWLEDGEMENT + "," +
			ATTRIBUTE_SHIP_TO_LOCATION_ID + "," +
			ATTRIBUTE_UNIT_PRICE + "," +
			ATTRIBUTE_DATE_FIRST_CONFIRMED + "," +
			ATTRIBUTE_DELIVERY_COMMENTS + "," +
			ATTRIBUTE_PROMISED_DATE + "," +
			ATTRIBUTE_PO_LINE_QUANTITY + "," +
			ATTRIBUTE_SHIP_FROM_LOCATION_ID + "," +
			ATTRIBUTE_SUPPLIER + "," +
			ATTRIBUTE_SUPPLIER_SALES_ORDER_NO + "," +
			ATTRIBUTE_VENDOR_SHIP_DATE + "," +
			ATTRIBUTE_ORDER_QUANTITY + "," +
			ATTRIBUTE_PR_NUMBER + "," +
			ATTRIBUTE_LINE_ITEM + "," +
			ATTRIBUTE_MARK_FOR_LOCATION_ID + "," +
			ATTRIBUTE_PO_NUMBER + "," +
			ATTRIBUTE_FAC_PART_NO + "," +
			ATTRIBUTE_CANCEL_STATUS + "," +
			ATTRIBUTE_CANCEL_COMMENT + "," +
			ATTRIBUTE_CUSTOMER_HAAS_CONTRACT_ID + "," +
			ATTRIBUTE_RELEASE_NUM + "," +
			ATTRIBUTE_MILSTRIP_CODE + "," +
			ATTRIBUTE_DESIRED_SHIP_DATE + "," +
			ATTRIBUTE_SHIP_TO_DODAAC + "," +
			ATTRIBUTE_SHIP_VIA_DODAAC + "," +
			ATTRIBUTE_OCONUS + "," +
			ATTRIBUTE_ORDER_DATE + "," +
			ATTRIBUTE_TRANSACTION_REF_NUM + "," +
			ATTRIBUTE_ISSUE_QUANTITY + "," +
			ATTRIBUTE_SHIPMENT_ID + "," +
			ATTRIBUTE_DATE_SHIPPED + "," +
			ATTRIBUTE_INVENTORY_GROUP + "," +
			ATTRIBUTE_TRACKING_NUMBER + "," +
			ATTRIBUTE_CARRIER_NAME + "," +
			ATTRIBUTE_ASN_STATUS + "," +
			ATTRIBUTE_TRANSPORTATION_CONTROL_NUM + "," +
			ATTRIBUTE_OPEN_QUANTITY + "," +
			ATTRIBUTE_ORDER_AGE + ")" +
			" values (" +
			SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getSupplierName()) + "," +
			SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getShipToInfo()) + "," +
			SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getShipFrom()) + "," +
			SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getIssuer()) + "," +
			SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getPartShortName()) + "," +
			SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getIpg()) + "," +
			SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getAirgasIpg()) + "," +
			SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getOrderStatus()) + "," +
			SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getComments()) + "," +
			dlaGasSupplierTrackingViewBean.getRadianPo() + "," +
			dlaGasSupplierTrackingViewBean.getPoLine() + "," +
			DateHandler.getOracleToDateFunction(dlaGasSupplierTrackingViewBean.getDateCreated()) + "," +
			DateHandler.getOracleToDateFunction(dlaGasSupplierTrackingViewBean.getDateSent()) + "," +
			DateHandler.getOracleToDateFunction(dlaGasSupplierTrackingViewBean.getDateAcknowledgement()) + "," +
			SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getShipToLocationId()) + "," +
			dlaGasSupplierTrackingViewBean.getUnitPrice() + "," +
			DateHandler.getOracleToDateFunction(dlaGasSupplierTrackingViewBean.getDateFirstConfirmed()) + "," +
			SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getDeliveryComments()) + "," +
			DateHandler.getOracleToDateFunction(dlaGasSupplierTrackingViewBean.getPromisedDate()) + "," +
			dlaGasSupplierTrackingViewBean.getPoLineQuantity() + "," +
			SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getShipFromLocationId()) + "," +
			SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getSupplier()) + "," +
			SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getSupplierSalesOrderNo()) + "," +
			DateHandler.getOracleToDateFunction(dlaGasSupplierTrackingViewBean.getVendorShipDate()) + "," +
			dlaGasSupplierTrackingViewBean.getOrderQuantity() + "," +
			dlaGasSupplierTrackingViewBean.getPrNumber() + "," +
			SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getLineItem()) + "," +
			SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getMarkForLocationId()) + "," +
			SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getPoNumber()) + "," +
			SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getFacPartNo()) + "," +
			SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getCancelStatus()) + "," +
			SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getCancelComment()) + "," +
			SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getCustomerHaasContractId()) + "," +
			SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getReleaseNum()) + "," +
			SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getMilstripCode()) + "," +
			DateHandler.getOracleToDateFunction(dlaGasSupplierTrackingViewBean.getDesiredShipDate()) + "," +
			SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getShipToDodaac()) + "," +
			SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getShipViaDodaac()) + "," +
			SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getOconus()) + "," +
			DateHandler.getOracleToDateFunction(dlaGasSupplierTrackingViewBean.getOrderDate()) + "," +
			SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getTransactionRefNum()) + "," +
			dlaGasSupplierTrackingViewBean.getIssueQuantity() + "," +
			dlaGasSupplierTrackingViewBean.getShipmentId() + "," +
			DateHandler.getOracleToDateFunction(dlaGasSupplierTrackingViewBean.getDateShipped()) + "," +
			SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getInventoryGroup()) + "," +
			SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getTrackingNumber()) + "," +
			SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getCarrierName()) + "," +
			SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getAsnStatus()) + "," +
			SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getTransportationControlNum()) + "," +
			dlaGasSupplierTrackingViewBean.getOpenQuantity() + "," +
			dlaGasSupplierTrackingViewBean.getOrderAge() + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(DlaGasSupplierTrackingViewBean dlaGasSupplierTrackingViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(dlaGasSupplierTrackingViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(DlaGasSupplierTrackingViewBean dlaGasSupplierTrackingViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_SUPPLIER_NAME + "=" + 
				SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getSupplierName()) + "," +
			ATTRIBUTE_SHIP_TO_INFO + "=" + 
				SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getShipToInfo()) + "," +
			ATTRIBUTE_SHIP_FROM + "=" + 
				SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getShipFrom()) + "," +
			ATTRIBUTE_ISSUER + "=" + 
				SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getIssuer()) + "," +
			ATTRIBUTE_PART_SHORT_NAME + "=" + 
				SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getPartShortName()) + "," +
			ATTRIBUTE_IPG + "=" + 
				SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getIpg()) + "," +
			ATTRIBUTE_AIRGAS_IPG + "=" + 
				SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getAirgasIpg()) + "," +
			ATTRIBUTE_ORDER_STATUS + "=" + 
				SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getOrderStatus()) + "," +
			ATTRIBUTE_COMMENTS + "=" + 
				SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getComments()) + "," +
			ATTRIBUTE_RADIAN_PO + "=" + 
				StringHandler.nullIfZero(dlaGasSupplierTrackingViewBean.getRadianPo()) + "," +
			ATTRIBUTE_PO_LINE + "=" + 
				StringHandler.nullIfZero(dlaGasSupplierTrackingViewBean.getPoLine()) + "," +
			ATTRIBUTE_DATE_CREATED + "=" + 
				DateHandler.getOracleToDateFunction(dlaGasSupplierTrackingViewBean.getDateCreated()) + "," +
			ATTRIBUTE_DATE_SENT + "=" + 
				DateHandler.getOracleToDateFunction(dlaGasSupplierTrackingViewBean.getDateSent()) + "," +
			ATTRIBUTE_DATE_ACKNOWLEDGEMENT + "=" + 
				DateHandler.getOracleToDateFunction(dlaGasSupplierTrackingViewBean.getDateAcknowledgement()) + "," +
			ATTRIBUTE_SHIP_TO_LOCATION_ID + "=" + 
				SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getShipToLocationId()) + "," +
			ATTRIBUTE_UNIT_PRICE + "=" + 
				StringHandler.nullIfZero(dlaGasSupplierTrackingViewBean.getUnitPrice()) + "," +
			ATTRIBUTE_DATE_FIRST_CONFIRMED + "=" + 
				DateHandler.getOracleToDateFunction(dlaGasSupplierTrackingViewBean.getDateFirstConfirmed()) + "," +
			ATTRIBUTE_DELIVERY_COMMENTS + "=" + 
				SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getDeliveryComments()) + "," +
			ATTRIBUTE_PROMISED_DATE + "=" + 
				DateHandler.getOracleToDateFunction(dlaGasSupplierTrackingViewBean.getPromisedDate()) + "," +
			ATTRIBUTE_PO_LINE_QUANTITY + "=" + 
				StringHandler.nullIfZero(dlaGasSupplierTrackingViewBean.getPoLineQuantity()) + "," +
			ATTRIBUTE_SHIP_FROM_LOCATION_ID + "=" + 
				SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getShipFromLocationId()) + "," +
			ATTRIBUTE_SUPPLIER + "=" + 
				SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getSupplier()) + "," +
			ATTRIBUTE_SUPPLIER_SALES_ORDER_NO + "=" + 
				SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getSupplierSalesOrderNo()) + "," +
			ATTRIBUTE_VENDOR_SHIP_DATE + "=" + 
				DateHandler.getOracleToDateFunction(dlaGasSupplierTrackingViewBean.getVendorShipDate()) + "," +
			ATTRIBUTE_ORDER_QUANTITY + "=" + 
				StringHandler.nullIfZero(dlaGasSupplierTrackingViewBean.getOrderQuantity()) + "," +
			ATTRIBUTE_PR_NUMBER + "=" + 
				StringHandler.nullIfZero(dlaGasSupplierTrackingViewBean.getPrNumber()) + "," +
			ATTRIBUTE_LINE_ITEM + "=" + 
				SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getLineItem()) + "," +
			ATTRIBUTE_MARK_FOR_LOCATION_ID + "=" + 
				SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getMarkForLocationId()) + "," +
			ATTRIBUTE_PO_NUMBER + "=" + 
				SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getPoNumber()) + "," +
			ATTRIBUTE_FAC_PART_NO + "=" + 
				SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getFacPartNo()) + "," +
			ATTRIBUTE_CANCEL_STATUS + "=" + 
				SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getCancelStatus()) + "," +
			ATTRIBUTE_CANCEL_COMMENT + "=" + 
				SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getCancelComment()) + "," +
			ATTRIBUTE_CUSTOMER_HAAS_CONTRACT_ID + "=" + 
				SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getCustomerHaasContractId()) + "," +
			ATTRIBUTE_RELEASE_NUM + "=" + 
				SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getReleaseNum()) + "," +
			ATTRIBUTE_MILSTRIP_CODE + "=" + 
				SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getMilstripCode()) + "," +
			ATTRIBUTE_DESIRED_SHIP_DATE + "=" + 
				DateHandler.getOracleToDateFunction(dlaGasSupplierTrackingViewBean.getDesiredShipDate()) + "," +
			ATTRIBUTE_SHIP_TO_DODAAC + "=" + 
				SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getShipToDodaac()) + "," +
			ATTRIBUTE_SHIP_VIA_DODAAC + "=" + 
				SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getShipViaDodaac()) + "," +
			ATTRIBUTE_OCONUS + "=" + 
				SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getOconus()) + "," +
			ATTRIBUTE_ORDER_DATE + "=" + 
				DateHandler.getOracleToDateFunction(dlaGasSupplierTrackingViewBean.getOrderDate()) + "," +
			ATTRIBUTE_TRANSACTION_REF_NUM + "=" + 
				SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getTransactionRefNum()) + "," +
			ATTRIBUTE_ISSUE_QUANTITY + "=" + 
				StringHandler.nullIfZero(dlaGasSupplierTrackingViewBean.getIssueQuantity()) + "," +
			ATTRIBUTE_SHIPMENT_ID + "=" + 
				StringHandler.nullIfZero(dlaGasSupplierTrackingViewBean.getShipmentId()) + "," +
			ATTRIBUTE_DATE_SHIPPED + "=" + 
				DateHandler.getOracleToDateFunction(dlaGasSupplierTrackingViewBean.getDateShipped()) + "," +
			ATTRIBUTE_INVENTORY_GROUP + "=" + 
				SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getInventoryGroup()) + "," +
			ATTRIBUTE_TRACKING_NUMBER + "=" + 
				SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getTrackingNumber()) + "," +
			ATTRIBUTE_CARRIER_NAME + "=" + 
				SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getCarrierName()) + "," +
			ATTRIBUTE_ASN_STATUS + "=" + 
				SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getAsnStatus()) + "," +
			ATTRIBUTE_TRANSPORTATION_CONTROL_NUM + "=" + 
				SqlHandler.delimitString(dlaGasSupplierTrackingViewBean.getTransportationControlNum()) + "," +
			ATTRIBUTE_OPEN_QUANTITY + "=" + 
				StringHandler.nullIfZero(dlaGasSupplierTrackingViewBean.getOpenQuantity()) + "," +
			ATTRIBUTE_ORDER_AGE + "=" + 
				StringHandler.nullIfZero(dlaGasSupplierTrackingViewBean.getOrderAge()) + " " +
			"where " + ATTRIBUTE_SUPPLIER_NAME + "=" +
				dlaGasSupplierTrackingViewBean.getSupplierName();

		return new SqlManager().update(conn, query);
	}
*/

	//select
	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria,String additionalWhereClause)
		throws BaseException {

		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = select(criteria, sortCriteria, additionalWhereClause,connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}
	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria,String additionalWhereClause, Connection conn)
		throws BaseException {

		Collection dlaGasSupplierTrackingViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + additionalWhereClause+ getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			DlaGasSupplierTrackingViewBean dlaGasSupplierTrackingViewBean = new DlaGasSupplierTrackingViewBean();
			load(dataSetRow, dlaGasSupplierTrackingViewBean);
			dlaGasSupplierTrackingViewBeanColl.add(dlaGasSupplierTrackingViewBean);
		}

		return dlaGasSupplierTrackingViewBeanColl;
	}
}