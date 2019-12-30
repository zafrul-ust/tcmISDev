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
import com.tcmis.internal.hub.beans.LogisticsViewBean;


/******************************************************************************
 * CLASSNAME: LogisticsViewBeanFactory <br>
 * @version: 1.0, Mar 6, 2008 <br>
 *****************************************************************************/


public class LogisticsViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_CLIENT_PART_NOS = "CLIENT_PART_NOS";
	public String ATTRIBUTE_DATE_OF_RECEIPT = "DATE_OF_RECEIPT";
	public String ATTRIBUTE_DATE_OF_MANUFACTURE = "DATE_OF_MANUFACTURE";
	public String ATTRIBUTE_PO_LINE = "PO_LINE";
	public String ATTRIBUTE_RADIAN_PO = "RADIAN_PO";
	public String ATTRIBUTE_UNIT_COST = "UNIT_COST";
	public String ATTRIBUTE_ORIGINAL_RECEIPT_ID = "ORIGINAL_RECEIPT_ID";
	public String ATTRIBUTE_OWNER_COMPANY_ID = "OWNER_COMPANY_ID";
	public String ATTRIBUTE_PICKABLE = "PICKABLE";
	public String ATTRIBUTE_QUALITY_CONTROL_ITEM = "QUALITY_CONTROL_ITEM";
	public String ATTRIBUTE_QUANTITY_RECEIVED = "QUANTITY_RECEIVED";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_LOT_STATUS_ROOT_CAUSE = "LOT_STATUS_ROOT_CAUSE";
	public String ATTRIBUTE_RESPONSIBLE_COMPANY_ID = "RESPONSIBLE_COMPANY_ID";
	public String ATTRIBUTE_LAST_MODIFIED_BY = "LAST_MODIFIED_BY";
	public String ATTRIBUTE_LOT_STATUS_ROOT_CAUSE_NOTES = "LOT_STATUS_ROOT_CAUSE_NOTES";
	public String ATTRIBUTE_LOT_STATUS_AGE = "LOT_STATUS_AGE";
	public String ATTRIBUTE_NOTES = "NOTES";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_HUB = "HUB";
	public String ATTRIBUTE_RECERT_NUMBER = "RECERT_NUMBER";
	public String ATTRIBUTE_LAST_PRINT_DATE = "LAST_PRINT_DATE";
	public String ATTRIBUTE_ITEM_DESC = "ITEM_DESC";
	public String ATTRIBUTE_SEARCH = "SEARCH";
	public String ATTRIBUTE_LOT_STATUS = "LOT_STATUS";
	public String ATTRIBUTE_BIN = "BIN";
	public String ATTRIBUTE_MFG_LOT = "MFG_LOT";
	public String ATTRIBUTE_RECEIPT_ID = "RECEIPT_ID";
	public String ATTRIBUTE_QUANTITY = "QUANTITY";
	public String ATTRIBUTE_NO_OF_LABELS = "NO_OF_LABELS";
	public String ATTRIBUTE_EXPIRE_DATE1 = "EXPIRE_DATE1";
	public String ATTRIBUTE_EXPIRE_DATE = "EXPIRE_DATE";
	public String ATTRIBUTE_MATERIAL_DESC = "MATERIAL_DESC";
	public String ATTRIBUTE_INVENTORY_GROUP_NAME = "INVENTORY_GROUP_NAME";
	public String ATTRIBUTE_NUMBER_OF_KITS = "NUMBER_OF_KITS";
	public String ATTRIBUTE_MANAGE_KITS_AS_SINGLE_UNIT = "MANAGE_KITS_AS_SINGLE_UNIT";
	public String ATTRIBUTE_COMPONENT_ID = "COMPONENT_ID";
	public String ATTRIBUTE_STORAGE_TEMP = "STORAGE_TEMP";
	public String ATTRIBUTE_LABEL_STORAGE_TEMP = "LABEL_STORAGE_TEMP";
	public String ATTRIBUTE_RECEIPT_EXPIRE_DATE = "RECEIPT_EXPIRE_DATE";
	public String ATTRIBUTE_DATE_OF_SHIPMENT = "DATE_OF_SHIPMENT";
	public String ATTRIBUTE_VENDOR_SHIP_DATE = "VENDOR_SHIP_DATE";
	public String ATTRIBUTE_PACKAGING_DESC = "PACKAGING_DESC";
	public String ATTRIBUTE_RECEIPT_DOCUMENT_AVAILABLE = "RECEIPT_DOCUMENT_AVAILABLE";
	public String ATTRIBUTE_RECEIPT_PACKAGING = "RECEIPT_PACKAGING";
	public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
	public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
	public String ATTRIBUTE_CATALOG_COMPANY_ID = "CATALOG_COMPANY_ID";
	public String ATTRIBUTE_ITEM_TYPE = "ITEM_TYPE";
	public String ATTRIBUTE_NEW_CHEM_REQUEST_ID = "NEW_CHEM_REQUEST_ID";
	public String ATTRIBUTE_QUALITY_TRACKING_NUMBER = "QUALITY_TRACKING_NUMBER";

	//table name
	public String TABLE = "LOGISTICS_VIEW";


	//constructor
	public LogisticsViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("clientPartNos")) {
			return ATTRIBUTE_CLIENT_PART_NOS;
		}
		else if(attributeName.equals("dateOfReceipt")) {
			return ATTRIBUTE_DATE_OF_RECEIPT;
		}
		else if(attributeName.equals("dateOfManufacture")) {
			return ATTRIBUTE_DATE_OF_MANUFACTURE;
		}
		else if(attributeName.equals("poLine")) {
			return ATTRIBUTE_PO_LINE;
		}
		else if(attributeName.equals("radianPo")) {
			return ATTRIBUTE_RADIAN_PO;
		}
		else if(attributeName.equals("unitCost")) {
			return ATTRIBUTE_UNIT_COST;
		}
		else if(attributeName.equals("originalReceiptId")) {
			return ATTRIBUTE_ORIGINAL_RECEIPT_ID;
		}
		else if(attributeName.equals("ownerCompanyId")) {
			return ATTRIBUTE_OWNER_COMPANY_ID;
		}
		else if(attributeName.equals("pickable")) {
			return ATTRIBUTE_PICKABLE;
		}
		else if(attributeName.equals("qualityControlItem")) {
			return ATTRIBUTE_QUALITY_CONTROL_ITEM;
		}
		else if(attributeName.equals("quantityReceived")) {
			return ATTRIBUTE_QUANTITY_RECEIVED;
		}
		else if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("lotStatusRootCause")) {
			return ATTRIBUTE_LOT_STATUS_ROOT_CAUSE;
		}
		else if(attributeName.equals("responsibleCompanyId")) {
			return ATTRIBUTE_RESPONSIBLE_COMPANY_ID;
		}
		else if(attributeName.equals("lastModifiedBy")) {
			return ATTRIBUTE_LAST_MODIFIED_BY;
		}
		else if(attributeName.equals("lotStatusRootCauseNotes")) {
			return ATTRIBUTE_LOT_STATUS_ROOT_CAUSE_NOTES;
		}
		else if(attributeName.equals("lotStatusAge")) {
			return ATTRIBUTE_LOT_STATUS_AGE;
		}
		else if(attributeName.equals("notes")) {
			return ATTRIBUTE_NOTES;
		}
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("hub")) {
			return ATTRIBUTE_HUB;
		}
		else if(attributeName.equals("recertNumber")) {
			return ATTRIBUTE_RECERT_NUMBER;
		}
		else if(attributeName.equals("lastPrintDate")) {
			return ATTRIBUTE_LAST_PRINT_DATE;
		}
		else if(attributeName.equals("itemDesc")) {
			return ATTRIBUTE_ITEM_DESC;
		}
		else if(attributeName.equals("search")) {
			return ATTRIBUTE_SEARCH;
		}
		else if(attributeName.equals("lotStatus")) {
			return ATTRIBUTE_LOT_STATUS;
		}
		else if(attributeName.equals("bin")) {
			return ATTRIBUTE_BIN;
		}
		else if(attributeName.equals("mfgLot")) {
			return ATTRIBUTE_MFG_LOT;
		}
		else if(attributeName.equals("receiptId")) {
			return ATTRIBUTE_RECEIPT_ID;
		}
		else if(attributeName.equals("quantity")) {
			return ATTRIBUTE_QUANTITY;
		}
		else if(attributeName.equals("noOfLabels")) {
			return ATTRIBUTE_NO_OF_LABELS;
		}
		else if(attributeName.equals("expireDate1")) {
			return ATTRIBUTE_EXPIRE_DATE1;
		}
		else if(attributeName.equals("expireDate")) {
			return ATTRIBUTE_EXPIRE_DATE;
		}
		else if(attributeName.equals("materialDesc")) {
			return ATTRIBUTE_MATERIAL_DESC;
		}
		else if(attributeName.equals("inventoryGroupName")) {
			return ATTRIBUTE_INVENTORY_GROUP_NAME;
		}
		else if(attributeName.equals("numberOfKits")) {
			return ATTRIBUTE_NUMBER_OF_KITS;
		}
		else if(attributeName.equals("manageKitsAsSingleUnit")) {
			return ATTRIBUTE_MANAGE_KITS_AS_SINGLE_UNIT;
		}
		else if(attributeName.equals("componentId")) {
			return ATTRIBUTE_COMPONENT_ID;
		}
		else if(attributeName.equals("storageTemp")) {
			return ATTRIBUTE_STORAGE_TEMP;
		}
		else if(attributeName.equals("labelStorageTemp")) {
			return ATTRIBUTE_LABEL_STORAGE_TEMP;
		}
		else if(attributeName.equals("receiptExpireDate")) {
			return ATTRIBUTE_RECEIPT_EXPIRE_DATE;
		}
		else if(attributeName.equals("dateOfShipment")) {
			return ATTRIBUTE_DATE_OF_SHIPMENT;
		}
		else if(attributeName.equals("vendorShipDate")) {
			return ATTRIBUTE_VENDOR_SHIP_DATE;
		}
		else if(attributeName.equals("packagingDesc")) {
			return ATTRIBUTE_PACKAGING_DESC;
		}
		else if(attributeName.equals("receiptDocumentAvailable")) {
			return ATTRIBUTE_RECEIPT_DOCUMENT_AVAILABLE;
		}
		else if(attributeName.equals("receiptPackaging")) {
			return ATTRIBUTE_RECEIPT_PACKAGING;
		}
		else if(attributeName.equals("catalogId")) {
			return ATTRIBUTE_CATALOG_ID;
		}
		else if(attributeName.equals("catPartNo")) {
			return ATTRIBUTE_CAT_PART_NO;
		}
		else if(attributeName.equals("catalogCompanyId")) {
			return ATTRIBUTE_CATALOG_COMPANY_ID;
		}
		else if(attributeName.equals("itemType")) {
			return ATTRIBUTE_ITEM_TYPE;
		}
		else if(attributeName.equals("newChemRequestId")) {
			return ATTRIBUTE_NEW_CHEM_REQUEST_ID;
		}
		else if(attributeName.equals("qualityTrackingNumber")){
			return ATTRIBUTE_QUALITY_TRACKING_NUMBER;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, LogisticsViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(LogisticsViewBean logisticsViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("clientPartNos", "SearchCriterion.EQUALS",
			"" + logisticsViewBean.getClientPartNos());

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


	public int delete(LogisticsViewBean logisticsViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("clientPartNos", "SearchCriterion.EQUALS",
			"" + logisticsViewBean.getClientPartNos());

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
	public int insert(LogisticsViewBean logisticsViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(logisticsViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(LogisticsViewBean logisticsViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_CLIENT_PART_NOS + "," +
			ATTRIBUTE_DATE_OF_RECEIPT + "," +
			ATTRIBUTE_DATE_OF_MANUFACTURE + "," +
			ATTRIBUTE_PO_LINE + "," +
			ATTRIBUTE_RADIAN_PO + "," +
			ATTRIBUTE_UNIT_COST + "," +
			ATTRIBUTE_ORIGINAL_RECEIPT_ID + "," +
			ATTRIBUTE_OWNER_COMPANY_ID + "," +
			ATTRIBUTE_PICKABLE + "," +
			ATTRIBUTE_QUALITY_CONTROL_ITEM + "," +
			ATTRIBUTE_QUANTITY_RECEIVED + "," +
			ATTRIBUTE_INVENTORY_GROUP + "," +
			ATTRIBUTE_LOT_STATUS_ROOT_CAUSE + "," +
			ATTRIBUTE_RESPONSIBLE_COMPANY_ID + "," +
			ATTRIBUTE_LAST_MODIFIED_BY + "," +
			ATTRIBUTE_LOT_STATUS_ROOT_CAUSE_NOTES + "," +
			ATTRIBUTE_LOT_STATUS_AGE + "," +
			ATTRIBUTE_NOTES + "," +
			ATTRIBUTE_ITEM_ID + "," +
			ATTRIBUTE_HUB + "," +
			ATTRIBUTE_RECERT_NUMBER + "," +
			ATTRIBUTE_LAST_PRINT_DATE + "," +
			ATTRIBUTE_ITEM_DESC + "," +
			ATTRIBUTE_SEARCH + "," +
			ATTRIBUTE_LOT_STATUS + "," +
			ATTRIBUTE_BIN + "," +
			ATTRIBUTE_MFG_LOT + "," +
			ATTRIBUTE_RECEIPT_ID + "," +
			ATTRIBUTE_QUANTITY + "," +
			ATTRIBUTE_NO_OF_LABELS + "," +
			ATTRIBUTE_EXPIRE_DATE1 + "," +
			ATTRIBUTE_EXPIRE_DATE + "," +
			ATTRIBUTE_MATERIAL_DESC + "," +
			ATTRIBUTE_INVENTORY_GROUP_NAME + "," +
			ATTRIBUTE_NUMBER_OF_KITS + "," +
			ATTRIBUTE_MANAGE_KITS_AS_SINGLE_UNIT + "," +
			ATTRIBUTE_COMPONENT_ID + "," +
			ATTRIBUTE_STORAGE_TEMP + "," +
			ATTRIBUTE_LABEL_STORAGE_TEMP + "," +
			ATTRIBUTE_RECEIPT_EXPIRE_DATE + "," +
			ATTRIBUTE_DATE_OF_SHIPMENT + "," +
			ATTRIBUTE_VENDOR_SHIP_DATE + "," +
			ATTRIBUTE_PACKAGING_DESC + "," +
			ATTRIBUTE_RECEIPT_DOCUMENT_AVAILABLE + "," +
			ATTRIBUTE_RECEIPT_PACKAGING + "," +
			ATTRIBUTE_CATALOG_ID + "," +
			ATTRIBUTE_CAT_PART_NO + "," +
			ATTRIBUTE_CATALOG_COMPANY_ID + "," +
			ATTRIBUTE_ITEM_TYPE + "," +
			ATTRIBUTE_NEW_CHEM_REQUEST_ID + ")" +
			" values (" +
			SqlHandler.delimitString(logisticsViewBean.getClientPartNos()) + "," +
			SqlHandler.delimitString(logisticsViewBean.getDateOfReceipt()) + "," +
			SqlHandler.delimitString(logisticsViewBean.getDateOfManufacture()) + "," +
			logisticsViewBean.getPoLine() + "," +
			logisticsViewBean.getRadianPo() + "," +
			logisticsViewBean.getUnitCost() + "," +
			logisticsViewBean.getOriginalReceiptId() + "," +
			SqlHandler.delimitString(logisticsViewBean.getOwnerCompanyId()) + "," +
			SqlHandler.delimitString(logisticsViewBean.getPickable()) + "," +
			SqlHandler.delimitString(logisticsViewBean.getQualityControlItem()) + "," +
			logisticsViewBean.getQuantityReceived() + "," +
			SqlHandler.delimitString(logisticsViewBean.getInventoryGroup()) + "," +
			SqlHandler.delimitString(logisticsViewBean.getLotStatusRootCause()) + "," +
			SqlHandler.delimitString(logisticsViewBean.getResponsibleCompanyId()) + "," +
			logisticsViewBean.getLastModifiedBy() + "," +
			SqlHandler.delimitString(logisticsViewBean.getLotStatusRootCauseNotes()) + "," +
			logisticsViewBean.getLotStatusAge() + "," +
			SqlHandler.delimitString(logisticsViewBean.getNotes()) + "," +
			logisticsViewBean.getItemId() + "," +
			SqlHandler.delimitString(logisticsViewBean.getHub()) + "," +
			logisticsViewBean.getRecertNumber() + "," +
			SqlHandler.delimitString(logisticsViewBean.getLastPrintDate()) + "," +
			SqlHandler.delimitString(logisticsViewBean.getItemDesc()) + "," +
			SqlHandler.delimitString(logisticsViewBean.getSearch()) + "," +
			SqlHandler.delimitString(logisticsViewBean.getLotStatus()) + "," +
			SqlHandler.delimitString(logisticsViewBean.getBin()) + "," +
			SqlHandler.delimitString(logisticsViewBean.getMfgLot()) + "," +
			logisticsViewBean.getReceiptId() + "," +
			logisticsViewBean.getQuantity() + "," +
			logisticsViewBean.getNoOfLabels() + "," +
			DateHandler.getOracleToDateFunction(logisticsViewBean.getExpireDate1()) + "," +
			SqlHandler.delimitString(logisticsViewBean.getExpireDate()) + "," +
			SqlHandler.delimitString(logisticsViewBean.getMaterialDesc()) + "," +
			SqlHandler.delimitString(logisticsViewBean.getInventoryGroupName()) + "," +
			logisticsViewBean.getNumberOfKits() + "," +
			SqlHandler.delimitString(logisticsViewBean.getManageKitsAsSingleUnit()) + "," +
			logisticsViewBean.getComponentId() + "," +
			SqlHandler.delimitString(logisticsViewBean.getStorageTemp()) + "," +
			SqlHandler.delimitString(logisticsViewBean.getLabelStorageTemp()) + "," +
			DateHandler.getOracleToDateFunction(logisticsViewBean.getReceiptExpireDate()) + "," +
			DateHandler.getOracleToDateFunction(logisticsViewBean.getDateOfShipment()) + "," +
			DateHandler.getOracleToDateFunction(logisticsViewBean.getVendorShipDate()) + "," +
			SqlHandler.delimitString(logisticsViewBean.getPackagingDesc()) + "," +
			SqlHandler.delimitString(logisticsViewBean.getReceiptDocumentAvailable()) + "," +
			SqlHandler.delimitString(logisticsViewBean.getReceiptPackaging()) + "," +
			SqlHandler.delimitString(logisticsViewBean.getCatalogId()) + "," +
			SqlHandler.delimitString(logisticsViewBean.getCatPartNo()) + "," +
			SqlHandler.delimitString(logisticsViewBean.getCatalogCompanyId()) + "," +
			SqlHandler.delimitString(logisticsViewBean.getItemType()) + "," +
			logisticsViewBean.getNewChemRequestId() + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(LogisticsViewBean logisticsViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(logisticsViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(LogisticsViewBean logisticsViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_CLIENT_PART_NOS + "=" + 
				SqlHandler.delimitString(logisticsViewBean.getClientPartNos()) + "," +
			ATTRIBUTE_DATE_OF_RECEIPT + "=" + 
				SqlHandler.delimitString(logisticsViewBean.getDateOfReceipt()) + "," +
			ATTRIBUTE_DATE_OF_MANUFACTURE + "=" + 
				SqlHandler.delimitString(logisticsViewBean.getDateOfManufacture()) + "," +
			ATTRIBUTE_PO_LINE + "=" + 
				StringHandler.nullIfZero(logisticsViewBean.getPoLine()) + "," +
			ATTRIBUTE_RADIAN_PO + "=" + 
				StringHandler.nullIfZero(logisticsViewBean.getRadianPo()) + "," +
			ATTRIBUTE_UNIT_COST + "=" + 
				StringHandler.nullIfZero(logisticsViewBean.getUnitCost()) + "," +
			ATTRIBUTE_ORIGINAL_RECEIPT_ID + "=" + 
				StringHandler.nullIfZero(logisticsViewBean.getOriginalReceiptId()) + "," +
			ATTRIBUTE_OWNER_COMPANY_ID + "=" + 
				SqlHandler.delimitString(logisticsViewBean.getOwnerCompanyId()) + "," +
			ATTRIBUTE_PICKABLE + "=" + 
				SqlHandler.delimitString(logisticsViewBean.getPickable()) + "," +
			ATTRIBUTE_QUALITY_CONTROL_ITEM + "=" + 
				SqlHandler.delimitString(logisticsViewBean.getQualityControlItem()) + "," +
			ATTRIBUTE_QUANTITY_RECEIVED + "=" + 
				StringHandler.nullIfZero(logisticsViewBean.getQuantityReceived()) + "," +
			ATTRIBUTE_INVENTORY_GROUP + "=" + 
				SqlHandler.delimitString(logisticsViewBean.getInventoryGroup()) + "," +
			ATTRIBUTE_LOT_STATUS_ROOT_CAUSE + "=" + 
				SqlHandler.delimitString(logisticsViewBean.getLotStatusRootCause()) + "," +
			ATTRIBUTE_RESPONSIBLE_COMPANY_ID + "=" + 
				SqlHandler.delimitString(logisticsViewBean.getResponsibleCompanyId()) + "," +
			ATTRIBUTE_LAST_MODIFIED_BY + "=" + 
				StringHandler.nullIfZero(logisticsViewBean.getLastModifiedBy()) + "," +
			ATTRIBUTE_LOT_STATUS_ROOT_CAUSE_NOTES + "=" + 
				SqlHandler.delimitString(logisticsViewBean.getLotStatusRootCauseNotes()) + "," +
			ATTRIBUTE_LOT_STATUS_AGE + "=" + 
				StringHandler.nullIfZero(logisticsViewBean.getLotStatusAge()) + "," +
			ATTRIBUTE_NOTES + "=" + 
				SqlHandler.delimitString(logisticsViewBean.getNotes()) + "," +
			ATTRIBUTE_ITEM_ID + "=" + 
				StringHandler.nullIfZero(logisticsViewBean.getItemId()) + "," +
			ATTRIBUTE_HUB + "=" + 
				SqlHandler.delimitString(logisticsViewBean.getHub()) + "," +
			ATTRIBUTE_RECERT_NUMBER + "=" + 
				StringHandler.nullIfZero(logisticsViewBean.getRecertNumber()) + "," +
			ATTRIBUTE_LAST_PRINT_DATE + "=" + 
				SqlHandler.delimitString(logisticsViewBean.getLastPrintDate()) + "," +
			ATTRIBUTE_ITEM_DESC + "=" + 
				SqlHandler.delimitString(logisticsViewBean.getItemDesc()) + "," +
			ATTRIBUTE_SEARCH + "=" + 
				SqlHandler.delimitString(logisticsViewBean.getSearch()) + "," +
			ATTRIBUTE_LOT_STATUS + "=" + 
				SqlHandler.delimitString(logisticsViewBean.getLotStatus()) + "," +
			ATTRIBUTE_BIN + "=" + 
				SqlHandler.delimitString(logisticsViewBean.getBin()) + "," +
			ATTRIBUTE_MFG_LOT + "=" + 
				SqlHandler.delimitString(logisticsViewBean.getMfgLot()) + "," +
			ATTRIBUTE_RECEIPT_ID + "=" + 
				StringHandler.nullIfZero(logisticsViewBean.getReceiptId()) + "," +
			ATTRIBUTE_QUANTITY + "=" + 
				StringHandler.nullIfZero(logisticsViewBean.getQuantity()) + "," +
			ATTRIBUTE_NO_OF_LABELS + "=" + 
				StringHandler.nullIfZero(logisticsViewBean.getNoOfLabels()) + "," +
			ATTRIBUTE_EXPIRE_DATE1 + "=" + 
				DateHandler.getOracleToDateFunction(logisticsViewBean.getExpireDate1()) + "," +
			ATTRIBUTE_EXPIRE_DATE + "=" + 
				SqlHandler.delimitString(logisticsViewBean.getExpireDate()) + "," +
			ATTRIBUTE_MATERIAL_DESC + "=" + 
				SqlHandler.delimitString(logisticsViewBean.getMaterialDesc()) + "," +
			ATTRIBUTE_INVENTORY_GROUP_NAME + "=" + 
				SqlHandler.delimitString(logisticsViewBean.getInventoryGroupName()) + "," +
			ATTRIBUTE_NUMBER_OF_KITS + "=" + 
				StringHandler.nullIfZero(logisticsViewBean.getNumberOfKits()) + "," +
			ATTRIBUTE_MANAGE_KITS_AS_SINGLE_UNIT + "=" + 
				SqlHandler.delimitString(logisticsViewBean.getManageKitsAsSingleUnit()) + "," +
			ATTRIBUTE_COMPONENT_ID + "=" + 
				StringHandler.nullIfZero(logisticsViewBean.getComponentId()) + "," +
			ATTRIBUTE_STORAGE_TEMP + "=" + 
				SqlHandler.delimitString(logisticsViewBean.getStorageTemp()) + "," +
			ATTRIBUTE_LABEL_STORAGE_TEMP + "=" + 
				SqlHandler.delimitString(logisticsViewBean.getLabelStorageTemp()) + "," +
			ATTRIBUTE_RECEIPT_EXPIRE_DATE + "=" + 
				DateHandler.getOracleToDateFunction(logisticsViewBean.getReceiptExpireDate()) + "," +
			ATTRIBUTE_DATE_OF_SHIPMENT + "=" + 
				DateHandler.getOracleToDateFunction(logisticsViewBean.getDateOfShipment()) + "," +
			ATTRIBUTE_VENDOR_SHIP_DATE + "=" + 
				DateHandler.getOracleToDateFunction(logisticsViewBean.getVendorShipDate()) + "," +
			ATTRIBUTE_PACKAGING_DESC + "=" + 
				SqlHandler.delimitString(logisticsViewBean.getPackagingDesc()) + "," +
			ATTRIBUTE_RECEIPT_DOCUMENT_AVAILABLE + "=" + 
				SqlHandler.delimitString(logisticsViewBean.getReceiptDocumentAvailable()) + "," +
			ATTRIBUTE_RECEIPT_PACKAGING + "=" + 
				SqlHandler.delimitString(logisticsViewBean.getReceiptPackaging()) + "," +
			ATTRIBUTE_CATALOG_ID + "=" + 
				SqlHandler.delimitString(logisticsViewBean.getCatalogId()) + "," +
			ATTRIBUTE_CAT_PART_NO + "=" + 
				SqlHandler.delimitString(logisticsViewBean.getCatPartNo()) + "," +
			ATTRIBUTE_CATALOG_COMPANY_ID + "=" + 
				SqlHandler.delimitString(logisticsViewBean.getCatalogCompanyId()) + "," +
			ATTRIBUTE_ITEM_TYPE + "=" + 
				SqlHandler.delimitString(logisticsViewBean.getItemType()) + "," +
			ATTRIBUTE_NEW_CHEM_REQUEST_ID + "=" + 
				StringHandler.nullIfZero(logisticsViewBean.getNewChemRequestId()) + " " +
			"where " + ATTRIBUTE_CLIENT_PART_NOS + "=" +
				logisticsViewBean.getClientPartNos();

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

		Collection logisticsViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria)+ getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			LogisticsViewBean logisticsViewBean = new LogisticsViewBean();
			load(dataSetRow, logisticsViewBean);
			logisticsViewBeanColl.add(logisticsViewBean);
		}

		return logisticsViewBeanColl;
	}
}