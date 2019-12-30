package com.tcmis.internal.hub.factory;


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
import com.tcmis.internal.hub.beans.TransactionInputBean;
import com.tcmis.internal.hub.beans.TransactionTrackingViewBean;


/******************************************************************************
 * CLASSNAME: TransactionTrackingViewBeanFactory <br>
 * @version: 1.0, Apr 30, 2007 <br>
 *****************************************************************************/


public class TransactionTrackingViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_HUB = "HUB";
	public String ATTRIBUTE_RECEIPT_ID = "RECEIPT_ID";
	public String ATTRIBUTE_ISSUE_ID = "ISSUE_ID";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_QUANTITY = "QUANTITY";
	public String ATTRIBUTE_MFG_LOT = "MFG_LOT";
	public String ATTRIBUTE_BIN = "BIN";
	public String ATTRIBUTE_DATE_OF_RECEIPT = "DATE_OF_RECEIPT";
	public String ATTRIBUTE_TRANSACTION_DATE = "TRANSACTION_DATE";
	public String ATTRIBUTE_RADIAN_PO = "RADIAN_PO";
	public String ATTRIBUTE_PO_LINE = "PO_LINE";
	public String ATTRIBUTE_DATE_OF_MANUFACTURE = "DATE_OF_MANUFACTURE";
	public String ATTRIBUTE_RECEIVER_ID = "RECEIVER_ID";
	public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
	public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
	public String ATTRIBUTE_DATE_DELIVERED = "DATE_DELIVERED";
	public String ATTRIBUTE_SALES_ORDER = "SALES_ORDER";
	public String ATTRIBUTE_CONFIRMED = "CONFIRMED";
	public String ATTRIBUTE_RECEIVER_NAME = "RECEIVER_NAME";
	public String ATTRIBUTE_ISSUER_NAME = "ISSUER_NAME";
	public String ATTRIBUTE_TRANSACTION_TYPE = "TRANSACTION_TYPE";
	public String ATTRIBUTE_TRANSFERRED_FROM = "TRANSFERRED_FROM";
	public String ATTRIBUTE_TRANSFERRED_TO = "TRANSFERRED_TO";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
	public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
	public String ATTRIBUTE_LOT_STATUS = "LOT_STATUS";
	public String ATTRIBUTE_QC_USER = "QC_USER";
	public String ATTRIBUTE_QC_DATE = "QC_DATE";
	public String ATTRIBUTE_SHIP_CONFIRM_DATE = "SHIP_CONFIRM_DATE";
	public String ATTRIBUTE_SHIP_CONFIRM_USER = "SHIP_CONFIRM_USER";
	public String ATTRIBUTE_PRICE_QC_USER = "PRICE_QC_USER";
	public String ATTRIBUTE_PRICE_QC_DATE = "PRICE_QC_DATE";
	public String ATTRIBUTE_PICKLIST_ID = "PICKLIST_ID";
	public String ATTRIBUTE_PICKLIST_PRINT_DATE = "PICKLIST_PRINT_DATE";
	public String ATTRIBUTE_PICKLIST_QUANTITY = "PICKLIST_QUANTITY";
	public String ATTRIBUTE_NEED_DATE = "NEED_DATE";
	public String ATTRIBUTE_EXPIRE_DATE = "EXPIRE_DATE";
	public String ATTRIBUTE_NOTES = "NOTES";
	public String ATTRIBUTE_BATCH = "BATCH";
	public String ATTRIBUTE_STORAGE_TEMP = "STORAGE_TEMP";
	public String ATTRIBUTE_INVENTORY_GROUP_NAME = "INVENTORY_GROUP_NAME";
	public String ATTRIBUTE_LABEL_STORAGE_TEMP = "LABEL_STORAGE_TEMP";
	public String ATTRIBUTE_DELIVERY_TICKET = "DELIVERY_TICKET";
	public String ATTRIBUTE_TRACKING_NUMBER = "TRACKING_NUMBER";
    public String ATTRIBUTE_PACKAGING = "PACKAGING";
    public String ATTRIBUTE_ITEM_DESC = "ITEM_DESC";
    public String ATTRIBUTE_SHIPMENT_ID = "SHIPMENT_ID";
    public String ATTRIBUTE_DISTRIBUTOR_OPS = "DISTRIBUTOR_OPS";
    public String ATTRIBUTE_COST = "COST";
    public String ATTRIBUTE_LANDED_COST = "LANDED_COST";
    public String ATTRIBUTE_HOME_CURRENCY_ID = "HOME_CURRENCY_ID";
    public String ATTRIBUTE_OPS_COMPANY_ID = "OPS_COMPANY_ID";
    public String ATTRIBUTE_OPS_ENTITY_ID = "OPS_ENTITY_ID";
    public String ATTRIBUTE_OPERATING_ENTITY_NAME = "OPERATING_ENTITY_NAME";
    public String ATTRIBUTE_ITEM_DESCRIPTION = "ITEM_DESCRIPTION";
    public String ATTRIBUTE_CUSTOMER_NAME = "CUSTOMER_NAME";
    public String ATTRIBUTE_SUPPLIER_NAME = "SUPPLIER_NAME";
    public String ATTRIBUTE_SOURCE_INV_GROUP_NAME = "SOURCE_INV_GROUP_NAME";
    public String ATTRIBUTE_DESTINATION_INV_GROUP_NAME = "DESTINATION_INV_GROUP_NAME";
    public String ATTRIBUTE_RECEIVING_STATUS = "RECEIVING_STATUS";

  
  //table name
	public String TABLE = "TRANSACTION_TRACKING_VIEW";


	//constructor
	public TransactionTrackingViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("hub")) {
			return ATTRIBUTE_HUB;
		}
		else if(attributeName.equals("receiptId")) {
			return ATTRIBUTE_RECEIPT_ID;
		}
		else if(attributeName.equals("issueId")) {
			return ATTRIBUTE_ISSUE_ID;
		}
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("quantity")) {
			return ATTRIBUTE_QUANTITY;
		}
		else if(attributeName.equals("mfgLot")) {
			return ATTRIBUTE_MFG_LOT;
		}
		else if(attributeName.equals("bin")) {
			return ATTRIBUTE_BIN;
		}
		else if(attributeName.equals("dateOfReceipt")) {
			return ATTRIBUTE_DATE_OF_RECEIPT;
		}
		else if(attributeName.equals("transactionDate")) {
			return ATTRIBUTE_TRANSACTION_DATE;
		}
		else if(attributeName.equals("radianPo")) {
			return ATTRIBUTE_RADIAN_PO;
		}
		else if(attributeName.equals("poLine")) {
			return ATTRIBUTE_PO_LINE;
		}
		else if(attributeName.equals("dateOfManufacture")) {
			return ATTRIBUTE_DATE_OF_MANUFACTURE;
		}
		else if(attributeName.equals("receiverId")) {
			return ATTRIBUTE_RECEIVER_ID;
		}
		else if(attributeName.equals("prNumber")) {
			return ATTRIBUTE_PR_NUMBER;
		}
		else if(attributeName.equals("lineItem")) {
			return ATTRIBUTE_LINE_ITEM;
		}
		else if(attributeName.equals("dateDelivered")) {
			return ATTRIBUTE_DATE_DELIVERED;
		}
		else if(attributeName.equals("salesOrder")) {
			return ATTRIBUTE_SALES_ORDER;
		}
		else if(attributeName.equals("confirmed")) {
			return ATTRIBUTE_CONFIRMED;
		}
		else if(attributeName.equals("receiverName")) {
			return ATTRIBUTE_RECEIVER_NAME;
		}
		else if(attributeName.equals("issuerName")) {
			return ATTRIBUTE_ISSUER_NAME;
		}
		else if(attributeName.equals("transactionType")) {
			return ATTRIBUTE_TRANSACTION_TYPE;
		}
		else if(attributeName.equals("transferredFrom")) {
			return ATTRIBUTE_TRANSFERRED_FROM;
		}
		else if(attributeName.equals("transferredTo")) {
			return ATTRIBUTE_TRANSFERRED_TO;
		}
		else if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("catalogId")) {
			return ATTRIBUTE_CATALOG_ID;
		}
		else if(attributeName.equals("catPartNo")) {
			return ATTRIBUTE_CAT_PART_NO;
		}
		else if(attributeName.equals("lotStatus")) {
			return ATTRIBUTE_LOT_STATUS;
		}
		else if(attributeName.equals("qcUser")) {
			return ATTRIBUTE_QC_USER;
		}
		else if(attributeName.equals("qcDate")) {
			return ATTRIBUTE_QC_DATE;
		}
		else if(attributeName.equals("shipConfirmDate")) {
			return ATTRIBUTE_SHIP_CONFIRM_DATE;
		}
		else if(attributeName.equals("shipConfirmUser")) {
			return ATTRIBUTE_SHIP_CONFIRM_USER;
		}
		else if(attributeName.equals("priceQcUser")) {
			return ATTRIBUTE_PRICE_QC_USER;
		}
		else if(attributeName.equals("priceQcDate")) {
			return ATTRIBUTE_PRICE_QC_DATE;
		}
		else if(attributeName.equals("picklistId")) {
			return ATTRIBUTE_PICKLIST_ID;
		}
		else if(attributeName.equals("picklistPrintDate")) {
			return ATTRIBUTE_PICKLIST_PRINT_DATE;
		}
		else if(attributeName.equals("picklistQuantity")) {
			return ATTRIBUTE_PICKLIST_QUANTITY;
		}
		else if(attributeName.equals("needDate")) {
			return ATTRIBUTE_NEED_DATE;
		}
		else if(attributeName.equals("expireDate")) {
			return ATTRIBUTE_EXPIRE_DATE;
		}
		else if(attributeName.equals("notes")) {
			return ATTRIBUTE_NOTES;
		}
		else if(attributeName.equals("batch")) {
			return ATTRIBUTE_BATCH;
		}
		else if(attributeName.equals("storageTemp")) {
			return ATTRIBUTE_STORAGE_TEMP;
		}
		else if(attributeName.equals("inventoryGroupName")) {
			return ATTRIBUTE_INVENTORY_GROUP_NAME;
		}
		else if(attributeName.equals("labelStorageTemp")) {
			return ATTRIBUTE_LABEL_STORAGE_TEMP;
		}
		else if(attributeName.equals("deliveryTicket")) {
			return ATTRIBUTE_DELIVERY_TICKET;
		}
		else if(attributeName.equals("trackingNumber")) {
			return ATTRIBUTE_TRACKING_NUMBER;
		}
		else if(attributeName.equals("packaging")) {
			return ATTRIBUTE_PACKAGING;
		}
		else if(attributeName.equals("itemDesc")) {
			return ATTRIBUTE_ITEM_DESC;
		}
		else if(attributeName.equals("shipmentId")) {
			return ATTRIBUTE_SHIPMENT_ID;
		}
		else if(attributeName.equals("distributorOps")) {
			return ATTRIBUTE_DISTRIBUTOR_OPS;
		}
		else if(attributeName.equals("cost")) {
			return ATTRIBUTE_COST;
		}
		else if(attributeName.equals("landedCost")) {
			return ATTRIBUTE_LANDED_COST;
		}
		else if(attributeName.equals("homeCurrencyId")) {
			return ATTRIBUTE_HOME_CURRENCY_ID;
		}
		else if(attributeName.equals("opsCompanyId")) {
			return ATTRIBUTE_OPS_COMPANY_ID;
		}
		else if(attributeName.equals("opsEntityId")) {
			return ATTRIBUTE_OPS_ENTITY_ID;
		}
		else if(attributeName.equals("operatingEntityName")) {
			return ATTRIBUTE_OPERATING_ENTITY_NAME;
		}
		else if(attributeName.equals("itemDescription")) {
			return ATTRIBUTE_ITEM_DESCRIPTION;
		}
		else if(attributeName.equals("customerName")) {
			return ATTRIBUTE_CUSTOMER_NAME;
		}
		else if(attributeName.equals("supplierName")) {
			return ATTRIBUTE_SUPPLIER_NAME;
		}
		else if(attributeName.equals("sourceInvGroupName")) {
			return ATTRIBUTE_SOURCE_INV_GROUP_NAME;
		}
		else if(attributeName.equals("destinationInvGroupName")) {
			return ATTRIBUTE_DESTINATION_INV_GROUP_NAME;
		}
		else if(attributeName.equals("receivingStatus")) {
			return ATTRIBUTE_RECEIVING_STATUS;
		}
    else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, TransactionTrackingViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(TransactionTrackingViewBean transactionTrackingViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("inventoryGroup", "SearchCriterion.EQUALS",
			"" + transactionTrackingViewBean.getInventoryGroup());

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


	public int delete(TransactionTrackingViewBean transactionTrackingViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("inventoryGroup", "SearchCriterion.EQUALS",
			"" + transactionTrackingViewBean.getInventoryGroup());

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
	public int insert(TransactionTrackingViewBean transactionTrackingViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(transactionTrackingViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(TransactionTrackingViewBean transactionTrackingViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_INVENTORY_GROUP + "," +
			ATTRIBUTE_HUB + "," +
			ATTRIBUTE_RECEIPT_ID + "," +
			ATTRIBUTE_ISSUE_ID + "," +
			ATTRIBUTE_ITEM_ID + "," +
			ATTRIBUTE_QUANTITY + "," +
			ATTRIBUTE_MFG_LOT + "," +
			ATTRIBUTE_BIN + "," +
			ATTRIBUTE_DATE_OF_RECEIPT + "," +
			ATTRIBUTE_TRANSACTION_DATE + "," +
			ATTRIBUTE_RADIAN_PO + "," +
			ATTRIBUTE_PO_LINE + "," +
			ATTRIBUTE_DATE_OF_MANUFACTURE + "," +
			ATTRIBUTE_RECEIVER_ID + "," +
			ATTRIBUTE_PR_NUMBER + "," +
			ATTRIBUTE_LINE_ITEM + "," +
			ATTRIBUTE_DATE_DELIVERED + "," +
			ATTRIBUTE_SALES_ORDER + "," +
			ATTRIBUTE_CONFIRMED + "," +
			ATTRIBUTE_RECEIVER_NAME + "," +
			ATTRIBUTE_ISSUER_NAME + "," +
			ATTRIBUTE_TRANSACTION_TYPE + "," +
			ATTRIBUTE_TRANSFERRED_FROM + "," +
			ATTRIBUTE_TRANSFERRED_TO + "," +
			ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_CATALOG_ID + "," +
			ATTRIBUTE_CAT_PART_NO + "," +
			ATTRIBUTE_LOT_STATUS + "," +
			ATTRIBUTE_QC_USER + "," +
			ATTRIBUTE_QC_DATE + "," +
			ATTRIBUTE_SHIP_CONFIRM_DATE + "," +
			ATTRIBUTE_SHIP_CONFIRM_USER + "," +
			ATTRIBUTE_PRICE_QC_USER + "," +
			ATTRIBUTE_PRICE_QC_DATE + "," +
			ATTRIBUTE_PICKLIST_ID + "," +
			ATTRIBUTE_PICKLIST_PRINT_DATE + "," +
			ATTRIBUTE_PICKLIST_QUANTITY + "," +
			ATTRIBUTE_NEED_DATE + "," +
			ATTRIBUTE_EXPIRE_DATE + "," +
			ATTRIBUTE_NOTES + "," +
			ATTRIBUTE_BATCH + "," +
			ATTRIBUTE_STORAGE_TEMP + "," +
			ATTRIBUTE_INVENTORY_GROUP_NAME + "," +
			ATTRIBUTE_LABEL_STORAGE_TEMP + "," +
			ATTRIBUTE_DELIVERY_TICKET + ")" +
 values (
			SqlHandler.delimitString(transactionTrackingViewBean.getInventoryGroup()) + "," +
			SqlHandler.delimitString(transactionTrackingViewBean.getHub()) + "," +
			StringHandler.nullIfZero(transactionTrackingViewBean.getReceiptId()) + "," +
			StringHandler.nullIfZero(transactionTrackingViewBean.getIssueId()) + "," +
			StringHandler.nullIfZero(transactionTrackingViewBean.getItemId()) + "," +
			StringHandler.nullIfZero(transactionTrackingViewBean.getQuantity()) + "," +
			SqlHandler.delimitString(transactionTrackingViewBean.getMfgLot()) + "," +
			SqlHandler.delimitString(transactionTrackingViewBean.getBin()) + "," +
			DateHandler.getOracleToDateFunction(transactionTrackingViewBean.getDateOfReceipt()) + "," +
			DateHandler.getOracleToDateFunction(transactionTrackingViewBean.getTransactionDate()) + "," +
			StringHandler.nullIfZero(transactionTrackingViewBean.getRadianPo()) + "," +
			StringHandler.nullIfZero(transactionTrackingViewBean.getPoLine()) + "," +
			DateHandler.getOracleToDateFunction(transactionTrackingViewBean.getDateOfManufacture()) + "," +
			SqlHandler.delimitString(transactionTrackingViewBean.getReceiverId()) + "," +
			StringHandler.nullIfZero(transactionTrackingViewBean.getPrNumber()) + "," +
			SqlHandler.delimitString(transactionTrackingViewBean.getLineItem()) + "," +
			DateHandler.getOracleToDateFunction(transactionTrackingViewBean.getDateDelivered()) + "," +
			StringHandler.nullIfZero(transactionTrackingViewBean.getSalesOrder()) + "," +
			SqlHandler.delimitString(transactionTrackingViewBean.getConfirmed()) + "," +
			SqlHandler.delimitString(transactionTrackingViewBean.getReceiverName()) + "," +
			SqlHandler.delimitString(transactionTrackingViewBean.getIssuerName()) + "," +
			SqlHandler.delimitString(transactionTrackingViewBean.getTransactionType()) + "," +
			SqlHandler.delimitString(transactionTrackingViewBean.getTransferredFrom()) + "," +
			SqlHandler.delimitString(transactionTrackingViewBean.getTransferredTo()) + "," +
			SqlHandler.delimitString(transactionTrackingViewBean.getCompanyId()) + "," +
			SqlHandler.delimitString(transactionTrackingViewBean.getCatalogId()) + "," +
			SqlHandler.delimitString(transactionTrackingViewBean.getCatPartNo()) + "," +
			SqlHandler.delimitString(transactionTrackingViewBean.getLotStatus()) + "," +
			StringHandler.nullIfZero(transactionTrackingViewBean.getQcUser()) + "," +
			DateHandler.getOracleToDateFunction(transactionTrackingViewBean.getQcDate()) + "," +
			DateHandler.getOracleToDateFunction(transactionTrackingViewBean.getShipConfirmDate()) + "," +
			StringHandler.nullIfZero(transactionTrackingViewBean.getShipConfirmUser()) + "," +
			StringHandler.nullIfZero(transactionTrackingViewBean.getPriceQcUser()) + "," +
			DateHandler.getOracleToDateFunction(transactionTrackingViewBean.getPriceQcDate()) + "," +
			StringHandler.nullIfZero(transactionTrackingViewBean.getPicklistId()) + "," +
			DateHandler.getOracleToDateFunction(transactionTrackingViewBean.getPicklistPrintDate()) + "," +
			StringHandler.nullIfZero(transactionTrackingViewBean.getPicklistQuantity()) + "," +
			DateHandler.getOracleToDateFunction(transactionTrackingViewBean.getNeedDate()) + "," +
			DateHandler.getOracleToDateFunction(transactionTrackingViewBean.getExpireDate()) + "," +
			SqlHandler.delimitString(transactionTrackingViewBean.getNotes()) + "," +
			StringHandler.nullIfZero(transactionTrackingViewBean.getBatch()) + "," +
			SqlHandler.delimitString(transactionTrackingViewBean.getStorageTemp()) + "," +
			SqlHandler.delimitString(transactionTrackingViewBean.getInventoryGroupName()) + "," +
			SqlHandler.delimitString(transactionTrackingViewBean.getLabelStorageTemp()) + "," +
			SqlHandler.delimitString(transactionTrackingViewBean.getDeliveryTicket()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(TransactionTrackingViewBean transactionTrackingViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(transactionTrackingViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(TransactionTrackingViewBean transactionTrackingViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_INVENTORY_GROUP + "=" + 
				SqlHandler.delimitString(transactionTrackingViewBean.getInventoryGroup()) + "," +
			ATTRIBUTE_HUB + "=" + 
				SqlHandler.delimitString(transactionTrackingViewBean.getHub()) + "," +
			ATTRIBUTE_RECEIPT_ID + "=" + 
				StringHandler.nullIfZero(transactionTrackingViewBean.getReceiptId()) + "," +
			ATTRIBUTE_ISSUE_ID + "=" + 
				StringHandler.nullIfZero(transactionTrackingViewBean.getIssueId()) + "," +
			ATTRIBUTE_ITEM_ID + "=" + 
				StringHandler.nullIfZero(transactionTrackingViewBean.getItemId()) + "," +
			ATTRIBUTE_QUANTITY + "=" + 
				StringHandler.nullIfZero(transactionTrackingViewBean.getQuantity()) + "," +
			ATTRIBUTE_MFG_LOT + "=" + 
				SqlHandler.delimitString(transactionTrackingViewBean.getMfgLot()) + "," +
			ATTRIBUTE_BIN + "=" + 
				SqlHandler.delimitString(transactionTrackingViewBean.getBin()) + "," +
			ATTRIBUTE_DATE_OF_RECEIPT + "=" + 
				DateHandler.getOracleToDateFunction(transactionTrackingViewBean.getDateOfReceipt()) + "," +
			ATTRIBUTE_TRANSACTION_DATE + "=" + 
				DateHandler.getOracleToDateFunction(transactionTrackingViewBean.getTransactionDate()) + "," +
			ATTRIBUTE_RADIAN_PO + "=" + 
				StringHandler.nullIfZero(transactionTrackingViewBean.getRadianPo()) + "," +
			ATTRIBUTE_PO_LINE + "=" + 
				StringHandler.nullIfZero(transactionTrackingViewBean.getPoLine()) + "," +
			ATTRIBUTE_DATE_OF_MANUFACTURE + "=" + 
				DateHandler.getOracleToDateFunction(transactionTrackingViewBean.getDateOfManufacture()) + "," +
			ATTRIBUTE_RECEIVER_ID + "=" + 
				SqlHandler.delimitString(transactionTrackingViewBean.getReceiverId()) + "," +
			ATTRIBUTE_PR_NUMBER + "=" + 
				StringHandler.nullIfZero(transactionTrackingViewBean.getPrNumber()) + "," +
			ATTRIBUTE_LINE_ITEM + "=" + 
				SqlHandler.delimitString(transactionTrackingViewBean.getLineItem()) + "," +
			ATTRIBUTE_DATE_DELIVERED + "=" + 
				DateHandler.getOracleToDateFunction(transactionTrackingViewBean.getDateDelivered()) + "," +
			ATTRIBUTE_SALES_ORDER + "=" + 
				StringHandler.nullIfZero(transactionTrackingViewBean.getSalesOrder()) + "," +
			ATTRIBUTE_CONFIRMED + "=" + 
				SqlHandler.delimitString(transactionTrackingViewBean.getConfirmed()) + "," +
			ATTRIBUTE_RECEIVER_NAME + "=" + 
				SqlHandler.delimitString(transactionTrackingViewBean.getReceiverName()) + "," +
			ATTRIBUTE_ISSUER_NAME + "=" + 
				SqlHandler.delimitString(transactionTrackingViewBean.getIssuerName()) + "," +
			ATTRIBUTE_TRANSACTION_TYPE + "=" + 
				SqlHandler.delimitString(transactionTrackingViewBean.getTransactionType()) + "," +
			ATTRIBUTE_TRANSFERRED_FROM + "=" + 
				SqlHandler.delimitString(transactionTrackingViewBean.getTransferredFrom()) + "," +
			ATTRIBUTE_TRANSFERRED_TO + "=" + 
				SqlHandler.delimitString(transactionTrackingViewBean.getTransferredTo()) + "," +
			ATTRIBUTE_COMPANY_ID + "=" + 
				SqlHandler.delimitString(transactionTrackingViewBean.getCompanyId()) + "," +
			ATTRIBUTE_CATALOG_ID + "=" + 
				SqlHandler.delimitString(transactionTrackingViewBean.getCatalogId()) + "," +
			ATTRIBUTE_CAT_PART_NO + "=" + 
				SqlHandler.delimitString(transactionTrackingViewBean.getCatPartNo()) + "," +
			ATTRIBUTE_LOT_STATUS + "=" + 
				SqlHandler.delimitString(transactionTrackingViewBean.getLotStatus()) + "," +
			ATTRIBUTE_QC_USER + "=" + 
				StringHandler.nullIfZero(transactionTrackingViewBean.getQcUser()) + "," +
			ATTRIBUTE_QC_DATE + "=" + 
				DateHandler.getOracleToDateFunction(transactionTrackingViewBean.getQcDate()) + "," +
			ATTRIBUTE_SHIP_CONFIRM_DATE + "=" + 
				DateHandler.getOracleToDateFunction(transactionTrackingViewBean.getShipConfirmDate()) + "," +
			ATTRIBUTE_SHIP_CONFIRM_USER + "=" + 
				StringHandler.nullIfZero(transactionTrackingViewBean.getShipConfirmUser()) + "," +
			ATTRIBUTE_PRICE_QC_USER + "=" + 
				StringHandler.nullIfZero(transactionTrackingViewBean.getPriceQcUser()) + "," +
			ATTRIBUTE_PRICE_QC_DATE + "=" + 
				DateHandler.getOracleToDateFunction(transactionTrackingViewBean.getPriceQcDate()) + "," +
			ATTRIBUTE_PICKLIST_ID + "=" + 
				StringHandler.nullIfZero(transactionTrackingViewBean.getPicklistId()) + "," +
			ATTRIBUTE_PICKLIST_PRINT_DATE + "=" + 
				DateHandler.getOracleToDateFunction(transactionTrackingViewBean.getPicklistPrintDate()) + "," +
			ATTRIBUTE_PICKLIST_QUANTITY + "=" + 
				StringHandler.nullIfZero(transactionTrackingViewBean.getPicklistQuantity()) + "," +
			ATTRIBUTE_NEED_DATE + "=" + 
				DateHandler.getOracleToDateFunction(transactionTrackingViewBean.getNeedDate()) + "," +
			ATTRIBUTE_EXPIRE_DATE + "=" + 
				DateHandler.getOracleToDateFunction(transactionTrackingViewBean.getExpireDate()) + "," +
			ATTRIBUTE_NOTES + "=" + 
				SqlHandler.delimitString(transactionTrackingViewBean.getNotes()) + "," +
			ATTRIBUTE_BATCH + "=" + 
				StringHandler.nullIfZero(transactionTrackingViewBean.getBatch()) + "," +
			ATTRIBUTE_STORAGE_TEMP + "=" + 
				SqlHandler.delimitString(transactionTrackingViewBean.getStorageTemp()) + "," +
			ATTRIBUTE_INVENTORY_GROUP_NAME + "=" + 
				SqlHandler.delimitString(transactionTrackingViewBean.getInventoryGroupName()) + "," +
			ATTRIBUTE_LABEL_STORAGE_TEMP + "=" + 
				SqlHandler.delimitString(transactionTrackingViewBean.getLabelStorageTemp()) + "," +
			ATTRIBUTE_DELIVERY_TICKET + "=" + 
				SqlHandler.delimitString(transactionTrackingViewBean.getDeliveryTicket()) + " " +
			"where " + ATTRIBUTE_INVENTORY_GROUP + "=" +
				StringHandler.nullIfZero(transactionTrackingViewBean.getInventoryGroup());

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

		Collection transactionTrackingViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			TransactionTrackingViewBean transactionTrackingViewBean = new TransactionTrackingViewBean();
			load(dataSetRow, transactionTrackingViewBean);
			transactionTrackingViewBeanColl.add(transactionTrackingViewBean);
		}

		return transactionTrackingViewBeanColl;
	}

   public Collection select(SearchCriteria criteria,SortCriteria sortCriteria,boolean isCollection,TransactionInputBean inputBean) throws BaseException {
      
      Connection connection = null;
      Collection c = null;
      try {
         connection = this.getDbManager().getConnection();
         c = select(criteria, sortCriteria, connection, isCollection, inputBean);
      }
      finally {
         this.getDbManager().returnConnection(connection);
      }
      return c;
   }
   
   public Collection select(SearchCriteria criteria,  SortCriteria sortCriteria, Connection conn, boolean isCollection,TransactionInputBean inputBean)
   throws BaseException {
      
      Collection transactionTrackingViewBeanColl = new Vector();
      
      String query = "select x.*,fx_kit_packaging(x.item_id) packaging, (select item_desc from item where item_id = x.item_id) item_desc from " + TABLE + " x " +
      getWhereClause(criteria);
                  
      if (isCollection) {
	 if (getWhereClause(criteria).trim().length() > 0) {
            query += " and ";
	 }        
         query +=  getIGCollectionQry(inputBean);
      }
      
      if (sortCriteria != null) {
         query += getOrderByClause(sortCriteria);
      }            
      
      DataSet dataSet = new SqlManager().select(conn, query);
      
      Iterator dataIter = dataSet.iterator();
      
      while (dataIter.hasNext()) {
         DataSetRow dataSetRow = (DataSetRow)dataIter.next();
         TransactionTrackingViewBean transactionTrackingViewBean = new TransactionTrackingViewBean();
         load(dataSetRow, transactionTrackingViewBean);
         transactionTrackingViewBeanColl.add(transactionTrackingViewBean);
      }
      
      return transactionTrackingViewBeanColl;
   }
   
   private String getIGCollectionQry(TransactionInputBean inputBean) {
      String clause = ATTRIBUTE_INVENTORY_GROUP + " IN ( SELECT " + ATTRIBUTE_INVENTORY_GROUP + " FROM INVENTORY_GROUP_COLLECTION WHERE ";
      clause += ATTRIBUTE_HUB + "= '" + inputBean.getBranchPlant() + "' AND INVENTORY_GROUP_COLLECTION = '" + inputBean.getInventoryGroup() + "')"; 
      return clause;
   }        
}
