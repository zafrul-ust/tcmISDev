package com.tcmis.internal.hub.factory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.internal.hub.beans.ReceiptDescDropshipViewBean;

/******************************************************************************
 * CLASSNAME: ReceiptDescDropshipViewBeanFactory <br>
 * @version: 1.0, Aug 23, 2005 <br>
 *****************************************************************************/

public class ReceiptDescDropshipViewBeanFactory
 extends BaseBeanFactory {

 Log log = LogFactory.getLog(this.getClass());

 //column names
 public String ATTRIBUTE_RADIAN_PO = "RADIAN_PO";
 public String ATTRIBUTE_PO_LINE = "PO_LINE";
 public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
 public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
 public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
 public String ATTRIBUTE_MFG_LOT = "MFG_LOT";
 public String ATTRIBUTE_DATE_OF_RECEIPT = "DATE_OF_RECEIPT";
 public String ATTRIBUTE_BIN = "BIN";
 public String ATTRIBUTE_RECEIPT_ID = "RECEIPT_ID";
 public String ATTRIBUTE_QUANTITY_RECEIVED = "QUANTITY_RECEIVED";
 public String ATTRIBUTE_PACKAGING = "PACKAGING";
 public String ATTRIBUTE_LINE_DESC = "LINE_DESC";
 public String ATTRIBUTE_BRANCH_PLANT = "BRANCH_PLANT";
 public String ATTRIBUTE_DATE_OF_MANUFACTURE = "DATE_OF_MANUFACTURE";
 public String ATTRIBUTE_DATE_OF_SHIPMENT = "DATE_OF_SHIPMENT";
 public String ATTRIBUTE_EXPIRE_DATE = "EXPIRE_DATE";
 public String ATTRIBUTE_LOT_STATUS = "LOT_STATUS";
 public String ATTRIBUTE_EXCESS = "EXCESS";
 public String ATTRIBUTE_INDEFINITE_SHELF_LIFE = "INDEFINITE_SHELF_LIFE";
 public String ATTRIBUTE_SUPPLIER_NAME = "SUPPLIER_NAME";
 public String ATTRIBUTE_TRANSFER_RECEIPT_ID = "TRANSFER_RECEIPT_ID";
 public String ATTRIBUTE_ORIG_MFG_LOT = "ORIG_MFG_LOT";
 public String ATTRIBUTE_VENDOR_SHIP_DATE = "VENDOR_SHIP_DATE";
 public String ATTRIBUTE_LOT_STATUS_ROOT_CAUSE = "LOT_STATUS_ROOT_CAUSE";
 public String ATTRIBUTE_RESPONSIBLE_COMPANY_ID = "RESPONSIBLE_COMPANY_ID";
 public String ATTRIBUTE_LAST_MODIFIED_BY = "LAST_MODIFIED_BY";
 public String ATTRIBUTE_LOT_STATUS_ROOT_CAUSE_NOTES =
	"LOT_STATUS_ROOT_CAUSE_NOTES";
 public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
 public String ATTRIBUTE_OWNER_COMPANY_ID = "OWNER_COMPANY_ID";
 public String ATTRIBUTE_CONSIGNED_PO = "CONSIGNED_PO";
 public String ATTRIBUTE_QC_OK = "QC_OK";
 public String ATTRIBUTE_DOC_TYPE = "DOC_TYPE";
 public String ATTRIBUTE_TRANSFER_REQUEST_ID = "TRANSFER_REQUEST_ID";
 public String ATTRIBUTE_CERTIFICATION_DATE = "CERTIFICATION_DATE";
 public String ATTRIBUTE_PICKABLE = "PICKABLE";
 public String ATTRIBUTE_CERTIFIED_BY = "CERTIFIED_BY";
 public String ATTRIBUTE_CERTIFICATION_NUMBER = "CERTIFICATION_NUMBER";
 public String ATTRIBUTE_QUALITY_CONTROL_ITEM = "QUALITY_CONTROL_ITEM";
 public String ATTRIBUTE_MANAGE_KITS_AS_SINGLE_UNIT =
	"MANAGE_KITS_AS_SINGLE_UNIT";
 public String ATTRIBUTE_COMPONENT_ID = "COMPONENT_ID";
 public String ATTRIBUTE_MATERIAL_DESC = "MATERIAL_DESC";
 public String ATTRIBUTE_INVENTORY_GROUP_NAME = "INVENTORY_GROUP_NAME";
 public String ATTRIBUTE_NUMBER_OF_KITS = "NUMBER_OF_KITS";
 public String ATTRIBUTE_NOTES = "NOTES";
 public String ATTRIBUTE_STORAGE_TEMP = "STORAGE_TEMP";
 public String ATTRIBUTE_SHIP_TO_COMPANY_ID = "SHIP_TO_COMPANY_ID";
 public String ATTRIBUTE_SHIP_TO_LOCATION_ID = "SHIP_TO_LOCATION_ID";
 public String ATTRIBUTE_MATERIAL_REQUEST_ORIGIN = "MATERIAL_REQUEST_ORIGIN";
 public String ATTRIBUTE_SHIPMENT_ID = "SHIPMENT_ID";
 public String ATTRIBUTE_DELIVERY_TICKET = "DELIVERY_TICKET";
 public String ATTRIBUTE_QA_STATEMENT = "QA_STATEMENT";
 public String ATTRIBUTE_CMS = "CMS";
 public String ATTRIBUTE_DISTRIBUTION = "DISTRIBUTION";

 //table name
 public String TABLE = "RECEIPT_DESC_DROPSHIP_VIEW";

 //constructor
 public ReceiptDescDropshipViewBeanFactory(DbManager dbManager) {
	super(dbManager);
 }

 //get column names
 public String getColumnName(String attributeName) {
	if (attributeName.equals("radianPo")) {
	 return ATTRIBUTE_RADIAN_PO;
	}
	else if (attributeName.equals("poLine")) {
	 return ATTRIBUTE_PO_LINE;
	}
	else if (attributeName.equals("prNumber")) {
	 return ATTRIBUTE_PR_NUMBER;
	}
	else if (attributeName.equals("lineItem")) {
	 return ATTRIBUTE_LINE_ITEM;
	}
	else if (attributeName.equals("itemId")) {
	 return ATTRIBUTE_ITEM_ID;
	}
	else if (attributeName.equals("mfgLot")) {
	 return ATTRIBUTE_MFG_LOT;
	}
	else if (attributeName.equals("dateOfReceipt")) {
	 return ATTRIBUTE_DATE_OF_RECEIPT;
	}
	else if (attributeName.equals("bin")) {
	 return ATTRIBUTE_BIN;
	}
	else if (attributeName.equals("receiptId")) {
	 return ATTRIBUTE_RECEIPT_ID;
	}
	else if (attributeName.equals("quantityReceived")) {
	 return ATTRIBUTE_QUANTITY_RECEIVED;
	}
	else if (attributeName.equals("packaging")) {
	 return ATTRIBUTE_PACKAGING;
	}
	else if (attributeName.equals("lineDesc")) {
	 return ATTRIBUTE_LINE_DESC;
	}
	else if (attributeName.equals("branchPlant")) {
	 return ATTRIBUTE_BRANCH_PLANT;
	}
	else if (attributeName.equals("dateOfManufacture")) {
	 return ATTRIBUTE_DATE_OF_MANUFACTURE;
	}
	else if (attributeName.equals("dateOfShipment")) {
	 return ATTRIBUTE_DATE_OF_SHIPMENT;
	}
	else if (attributeName.equals("expireDate")) {
	 return ATTRIBUTE_EXPIRE_DATE;
	}
	else if (attributeName.equals("lotStatus")) {
	 return ATTRIBUTE_LOT_STATUS;
	}
	else if (attributeName.equals("excess")) {
	 return ATTRIBUTE_EXCESS;
	}
	else if (attributeName.equals("indefiniteShelfLife")) {
	 return ATTRIBUTE_INDEFINITE_SHELF_LIFE;
	}
	else if (attributeName.equals("supplierName")) {
	 return ATTRIBUTE_SUPPLIER_NAME;
	}
	else if (attributeName.equals("transferReceiptId")) {
	 return ATTRIBUTE_TRANSFER_RECEIPT_ID;
	}
	else if (attributeName.equals("origMfgLot")) {
	 return ATTRIBUTE_ORIG_MFG_LOT;
	}
	else if (attributeName.equals("vendorShipDate")) {
	 return ATTRIBUTE_VENDOR_SHIP_DATE;
	}
	else if (attributeName.equals("lotStatusRootCause")) {
	 return ATTRIBUTE_LOT_STATUS_ROOT_CAUSE;
	}
	else if (attributeName.equals("responsibleCompanyId")) {
	 return ATTRIBUTE_RESPONSIBLE_COMPANY_ID;
	}
	else if (attributeName.equals("lastModifiedBy")) {
	 return ATTRIBUTE_LAST_MODIFIED_BY;
	}
	else if (attributeName.equals("lotStatusRootCauseNotes")) {
	 return ATTRIBUTE_LOT_STATUS_ROOT_CAUSE_NOTES;
	}
	else if (attributeName.equals("inventoryGroup")) {
	 return ATTRIBUTE_INVENTORY_GROUP;
	}
	else if (attributeName.equals("ownerCompanyId")) {
	 return ATTRIBUTE_OWNER_COMPANY_ID;
	}
	else if (attributeName.equals("consignedPo")) {
	 return ATTRIBUTE_CONSIGNED_PO;
	}
	else if (attributeName.equals("qcOk")) {
	 return ATTRIBUTE_QC_OK;
	}
	else if (attributeName.equals("docType")) {
	 return ATTRIBUTE_DOC_TYPE;
	}
	else if (attributeName.equals("transferRequestId")) {
	 return ATTRIBUTE_TRANSFER_REQUEST_ID;
	}
	else if (attributeName.equals("certificationDate")) {
	 return ATTRIBUTE_CERTIFICATION_DATE;
	}
	else if (attributeName.equals("pickable")) {
	 return ATTRIBUTE_PICKABLE;
	}
	else if (attributeName.equals("certifiedBy")) {
	 return ATTRIBUTE_CERTIFIED_BY;
	}
	else if (attributeName.equals("certificationNumber")) {
	 return ATTRIBUTE_CERTIFICATION_NUMBER;
	}
	else if (attributeName.equals("qualityControlItem")) {
	 return ATTRIBUTE_QUALITY_CONTROL_ITEM;
	}
	else if (attributeName.equals("manageKitsAsSingleUnit")) {
	 return ATTRIBUTE_MANAGE_KITS_AS_SINGLE_UNIT;
	}
	else if (attributeName.equals("componentId")) {
	 return ATTRIBUTE_COMPONENT_ID;
	}
	else if (attributeName.equals("materialDesc")) {
	 return ATTRIBUTE_MATERIAL_DESC;
	}
	else if (attributeName.equals("inventoryGroupName")) {
	 return ATTRIBUTE_INVENTORY_GROUP_NAME;
	}
	else if (attributeName.equals("numberOfKits")) {
	 return ATTRIBUTE_NUMBER_OF_KITS;
	}
	else if (attributeName.equals("notes")) {
	 return ATTRIBUTE_NOTES;
	}
	else if (attributeName.equals("storageTemp")) {
	 return ATTRIBUTE_STORAGE_TEMP;
	}
	else if(attributeName.equals("shipToCompanyId")) {
	 return ATTRIBUTE_SHIP_TO_COMPANY_ID;
	}
	else if(attributeName.equals("shipToLocationId")) {
	 return ATTRIBUTE_SHIP_TO_LOCATION_ID;
	}
	else if(attributeName.equals("materialRequestOrigin")) {
		 return ATTRIBUTE_MATERIAL_REQUEST_ORIGIN;
	}
	else if(attributeName.equals("shipmentId")) {
		 return ATTRIBUTE_SHIPMENT_ID;
	}
	else if(attributeName.equals("deliveryTicket")) {
		 return ATTRIBUTE_DELIVERY_TICKET;
	}
    else if(attributeName.equals("qaStatement")) {
        return ATTRIBUTE_QA_STATEMENT;
    }
    else if (attributeName.equals("cms")) {
		return ATTRIBUTE_CMS;
	}
	else if (attributeName.equals("distribution")) {
		return ATTRIBUTE_DISTRIBUTION;
	}
    else {
	 return super.getColumnName(attributeName);
	}
 }

 //get column types
 public int getType(String attributeName) {
	return super.getType(attributeName, ReceiptDescDropshipViewBean.class);
 }

//you need to verify the primary key(s) before uncommenting this
 /*
//delete
	public int delete(ReceiptDescDropshipViewBean receiptDescDropshipViewBean)
	 throws BaseException {
	 SearchCriteria criteria = new SearchCriteria("radianPo", "SearchCriterion.EQUALS",
		"" + receiptDescDropshipViewBean.getRadianPo());
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
	public int delete(ReceiptDescDropshipViewBean receiptDescDropshipViewBean, Connection conn)
	 throws BaseException {
	 SearchCriteria criteria = new SearchCriteria("radianPo", "SearchCriterion.EQUALS",
		"" + receiptDescDropshipViewBean.getRadianPo());
	 return delete(criteria, conn);
	}
	*/

 public int delete(SearchCriteria criteria) throws BaseException {

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

 public int delete(SearchCriteria criteria,
	Connection conn) throws BaseException {

	String sqlQuery = " delete from " + TABLE + " " + getWhereClause(criteria);

	return new SqlManager().update(conn, sqlQuery);
 }

//you need to verify the primary key(s) before uncommenting this
 /*
//insert
	public int insert(ReceiptDescDropshipViewBean receiptDescDropshipViewBean)
	 throws BaseException {
	 Connection connection = null;
	 int result = 0;
	 try {
		connection = getDbManager().getConnection();
		result = insert(receiptDescDropshipViewBean, connection);
	 }
	 finally {
		this.getDbManager().returnConnection(connection);
	 }
	 return result;
	}
	public int insert(ReceiptDescDropshipViewBean receiptDescDropshipViewBean, Connection conn)
	 throws BaseException {
	 SqlManager sqlManager = new SqlManager();
	 String query  =
		"insert into " + TABLE + " (" +
		ATTRIBUTE_RADIAN_PO + "," +
		ATTRIBUTE_PO_LINE + "," +
		ATTRIBUTE_PR_NUMBER + "," +
		ATTRIBUTE_LINE_ITEM + "," +
		ATTRIBUTE_ITEM_ID + "," +
		ATTRIBUTE_MFG_LOT + "," +
		ATTRIBUTE_DATE_OF_RECEIPT + "," +
		ATTRIBUTE_BIN + "," +
		ATTRIBUTE_RECEIPT_ID + "," +
		ATTRIBUTE_QUANTITY_RECEIVED + "," +
		ATTRIBUTE_PACKAGING + "," +
		ATTRIBUTE_LINE_DESC + "," +
		ATTRIBUTE_BRANCH_PLANT + "," +
		ATTRIBUTE_DATE_OF_MANUFACTURE + "," +
		ATTRIBUTE_DATE_OF_SHIPMENT + "," +
		ATTRIBUTE_EXPIRE_DATE + "," +
		ATTRIBUTE_LOT_STATUS + "," +
		ATTRIBUTE_EXCESS + "," +
		ATTRIBUTE_INDEFINITE_SHELF_LIFE + "," +
		ATTRIBUTE_SUPPLIER_NAME + "," +
		ATTRIBUTE_TRANSFER_RECEIPT_ID + "," +
		ATTRIBUTE_ORIG_MFG_LOT + "," +
		ATTRIBUTE_VENDOR_SHIP_DATE + "," +
		ATTRIBUTE_LOT_STATUS_ROOT_CAUSE + "," +
		ATTRIBUTE_RESPONSIBLE_COMPANY_ID + "," +
		ATTRIBUTE_LAST_MODIFIED_BY + "," +
		ATTRIBUTE_LOT_STATUS_ROOT_CAUSE_NOTES + "," +
		ATTRIBUTE_INVENTORY_GROUP + "," +
		ATTRIBUTE_OWNER_COMPANY_ID + "," +
		ATTRIBUTE_CONSIGNED_PO + "," +
		ATTRIBUTE_QC_OK + "," +
		ATTRIBUTE_DOC_TYPE + "," +
		ATTRIBUTE_TRANSFER_REQUEST_ID + "," +
		ATTRIBUTE_CERTIFICATION_DATE + "," +
		ATTRIBUTE_PICKABLE + "," +
		ATTRIBUTE_CERTIFIED_BY + "," +
		ATTRIBUTE_CERTIFICATION_NUMBER + "," +
		ATTRIBUTE_QUALITY_CONTROL_ITEM + "," +
		ATTRIBUTE_MANAGE_KITS_AS_SINGLE_UNIT + "," +
		ATTRIBUTE_COMPONENT_ID + "," +
		ATTRIBUTE_MATERIAL_DESC + "," +
		ATTRIBUTE_INVENTORY_GROUP_NAME + "," +
		ATTRIBUTE_NUMBER_OF_KITS + "," +
		ATTRIBUTE_NOTES + "," +
		ATTRIBUTE_STORAGE_TEMP + ")" +
		" values (" +
		receiptDescDropshipViewBean.getRadianPo() + "," +
		receiptDescDropshipViewBean.getPoLine() + "," +
		receiptDescDropshipViewBean.getPrNumber() + "," +
		SqlHandler.delimitString(receiptDescDropshipViewBean.getLineItem()) + "," +
		receiptDescDropshipViewBean.getItemId() + "," +
		SqlHandler.delimitString(receiptDescDropshipViewBean.getMfgLot()) + "," +
	 SqlHandler.delimitString(receiptDescDropshipViewBean.getDateOfReceipt()) + "," +
		SqlHandler.delimitString(receiptDescDropshipViewBean.getBin()) + "," +
		receiptDescDropshipViewBean.getReceiptId() + "," +
		receiptDescDropshipViewBean.getQuantityReceived() + "," +
	 SqlHandler.delimitString(receiptDescDropshipViewBean.getPackaging()) + "," +
		SqlHandler.delimitString(receiptDescDropshipViewBean.getLineDesc()) + "," +
	 SqlHandler.delimitString(receiptDescDropshipViewBean.getBranchPlant()) + "," +
		DateHandler.getOracleToDateFunction(receiptDescDropshipViewBean.getDateOfManufacture()) + "," +
		DateHandler.getOracleToDateFunction(receiptDescDropshipViewBean.getDateOfShipment()) + "," +
		DateHandler.getOracleToDateFunction(receiptDescDropshipViewBean.getExpireDate()) + "," +
	 SqlHandler.delimitString(receiptDescDropshipViewBean.getLotStatus()) + "," +
		SqlHandler.delimitString(receiptDescDropshipViewBean.getExcess()) + "," +
		SqlHandler.delimitString(receiptDescDropshipViewBean.getIndefiniteShelfLife()) + "," +
	 SqlHandler.delimitString(receiptDescDropshipViewBean.getSupplierName()) + "," +
		receiptDescDropshipViewBean.getTransferReceiptId() + "," +
	 SqlHandler.delimitString(receiptDescDropshipViewBean.getOrigMfgLot()) + "," +
		DateHandler.getOracleToDateFunction(receiptDescDropshipViewBean.getVendorShipDate()) + "," +
		SqlHandler.delimitString(receiptDescDropshipViewBean.getLotStatusRootCause()) + "," +
		SqlHandler.delimitString(receiptDescDropshipViewBean.getResponsibleCompanyId()) + "," +
		receiptDescDropshipViewBean.getLastModifiedBy() + "," +
		SqlHandler.delimitString(receiptDescDropshipViewBean.getLotStatusRootCauseNotes()) + "," +
		SqlHandler.delimitString(receiptDescDropshipViewBean.getInventoryGroup()) + "," +
		SqlHandler.delimitString(receiptDescDropshipViewBean.getOwnerCompanyId()) + "," +
	 SqlHandler.delimitString(receiptDescDropshipViewBean.getConsignedPo()) + "," +
		SqlHandler.delimitString(receiptDescDropshipViewBean.getQcOk()) + "," +
		SqlHandler.delimitString(receiptDescDropshipViewBean.getDocType()) + "," +
		receiptDescDropshipViewBean.getTransferRequestId() + "," +
		DateHandler.getOracleToDateFunction(receiptDescDropshipViewBean.getCertificationDate()) + "," +
		SqlHandler.delimitString(receiptDescDropshipViewBean.getPickable()) + "," +
		receiptDescDropshipViewBean.getCertifiedBy() + "," +
		SqlHandler.delimitString(receiptDescDropshipViewBean.getCertificationNumber()) + "," +
		SqlHandler.delimitString(receiptDescDropshipViewBean.getQualityControlItem()) + "," +
		SqlHandler.delimitString(receiptDescDropshipViewBean.getManageKitsAsSingleUnit()) + "," +
		receiptDescDropshipViewBean.getComponentId() + "," +
	 SqlHandler.delimitString(receiptDescDropshipViewBean.getMaterialDesc()) + "," +
		SqlHandler.delimitString(receiptDescDropshipViewBean.getInventoryGroupName()) + "," +
		receiptDescDropshipViewBean.getNumberOfKits() + "," +
		SqlHandler.delimitString(receiptDescDropshipViewBean.getNotes()) + "," +
	 SqlHandler.delimitString(receiptDescDropshipViewBean.getStorageTemp()) + ")";
	 return sqlManager.update(conn, query);
	}
//update
	public int update(ReceiptDescDropshipViewBean receiptDescDropshipViewBean)
	 throws BaseException {
	 Connection connection = null;
	 int result = 0;
	 try {
		connection = getDbManager().getConnection();
		result = update(receiptDescDropshipViewBean, connection);
	 }
	 finally {
		this.getDbManager().returnConnection(connection);
	 }
	 return result;
	}
	public int update(ReceiptDescDropshipViewBean receiptDescDropshipViewBean, Connection conn)
	 throws BaseException {
	 String query  = "update " + TABLE + " set " +
		ATTRIBUTE_RADIAN_PO + "=" +
	 StringHandler.nullIfZero(receiptDescDropshipViewBean.getRadianPo()) + "," +
		ATTRIBUTE_PO_LINE + "=" +
		 StringHandler.nullIfZero(receiptDescDropshipViewBean.getPoLine()) + "," +
		ATTRIBUTE_PR_NUMBER + "=" +
	 StringHandler.nullIfZero(receiptDescDropshipViewBean.getPrNumber()) + "," +
		ATTRIBUTE_LINE_ITEM + "=" +
	 SqlHandler.delimitString(receiptDescDropshipViewBean.getLineItem()) + "," +
		ATTRIBUTE_ITEM_ID + "=" +
		 StringHandler.nullIfZero(receiptDescDropshipViewBean.getItemId()) + "," +
		ATTRIBUTE_MFG_LOT + "=" +
		 SqlHandler.delimitString(receiptDescDropshipViewBean.getMfgLot()) + "," +
		ATTRIBUTE_DATE_OF_RECEIPT + "=" +
	 SqlHandler.delimitString(receiptDescDropshipViewBean.getDateOfReceipt()) + "," +
		ATTRIBUTE_BIN + "=" +
		 SqlHandler.delimitString(receiptDescDropshipViewBean.getBin()) + "," +
		ATTRIBUTE_RECEIPT_ID + "=" +
	 StringHandler.nullIfZero(receiptDescDropshipViewBean.getReceiptId()) + "," +
		ATTRIBUTE_QUANTITY_RECEIVED + "=" +
		 StringHandler.nullIfZero(receiptDescDropshipViewBean.getQuantityReceived()) + "," +
		ATTRIBUTE_PACKAGING + "=" +
	 SqlHandler.delimitString(receiptDescDropshipViewBean.getPackaging()) + "," +
		ATTRIBUTE_LINE_DESC + "=" +
	 SqlHandler.delimitString(receiptDescDropshipViewBean.getLineDesc()) + "," +
		ATTRIBUTE_BRANCH_PLANT + "=" +
	 SqlHandler.delimitString(receiptDescDropshipViewBean.getBranchPlant()) + "," +
		ATTRIBUTE_DATE_OF_MANUFACTURE + "=" +
		 DateHandler.getOracleToDateFunction(receiptDescDropshipViewBean.getDateOfManufacture()) + "," +
		ATTRIBUTE_DATE_OF_SHIPMENT + "=" +
		 DateHandler.getOracleToDateFunction(receiptDescDropshipViewBean.getDateOfShipment()) + "," +
		ATTRIBUTE_EXPIRE_DATE + "=" +
		 DateHandler.getOracleToDateFunction(receiptDescDropshipViewBean.getExpireDate()) + "," +
		ATTRIBUTE_LOT_STATUS + "=" +
	 SqlHandler.delimitString(receiptDescDropshipViewBean.getLotStatus()) + "," +
		ATTRIBUTE_EXCESS + "=" +
		 SqlHandler.delimitString(receiptDescDropshipViewBean.getExcess()) + "," +
		ATTRIBUTE_INDEFINITE_SHELF_LIFE + "=" +
		 SqlHandler.delimitString(receiptDescDropshipViewBean.getIndefiniteShelfLife()) + "," +
		ATTRIBUTE_SUPPLIER_NAME + "=" +
	 SqlHandler.delimitString(receiptDescDropshipViewBean.getSupplierName()) + "," +
		ATTRIBUTE_TRANSFER_RECEIPT_ID + "=" +
		 StringHandler.nullIfZero(receiptDescDropshipViewBean.getTransferReceiptId()) + "," +
		ATTRIBUTE_ORIG_MFG_LOT + "=" +
	 SqlHandler.delimitString(receiptDescDropshipViewBean.getOrigMfgLot()) + "," +
		ATTRIBUTE_VENDOR_SHIP_DATE + "=" +
		 DateHandler.getOracleToDateFunction(receiptDescDropshipViewBean.getVendorShipDate()) + "," +
		ATTRIBUTE_LOT_STATUS_ROOT_CAUSE + "=" +
		 SqlHandler.delimitString(receiptDescDropshipViewBean.getLotStatusRootCause()) + "," +
		ATTRIBUTE_RESPONSIBLE_COMPANY_ID + "=" +
		 SqlHandler.delimitString(receiptDescDropshipViewBean.getResponsibleCompanyId()) + "," +
		ATTRIBUTE_LAST_MODIFIED_BY + "=" +
		 StringHandler.nullIfZero(receiptDescDropshipViewBean.getLastModifiedBy()) + "," +
		ATTRIBUTE_LOT_STATUS_ROOT_CAUSE_NOTES + "=" +
		 SqlHandler.delimitString(receiptDescDropshipViewBean.getLotStatusRootCauseNotes()) + "," +
		ATTRIBUTE_INVENTORY_GROUP + "=" +
		 SqlHandler.delimitString(receiptDescDropshipViewBean.getInventoryGroup()) + "," +
		ATTRIBUTE_OWNER_COMPANY_ID + "=" +
		 SqlHandler.delimitString(receiptDescDropshipViewBean.getOwnerCompanyId()) + "," +
		ATTRIBUTE_CONSIGNED_PO + "=" +
	 SqlHandler.delimitString(receiptDescDropshipViewBean.getConsignedPo()) + "," +
		ATTRIBUTE_QC_OK + "=" +
		 SqlHandler.delimitString(receiptDescDropshipViewBean.getQcOk()) + "," +
		ATTRIBUTE_DOC_TYPE + "=" +
		 SqlHandler.delimitString(receiptDescDropshipViewBean.getDocType()) + "," +
		ATTRIBUTE_TRANSFER_REQUEST_ID + "=" +
		 StringHandler.nullIfZero(receiptDescDropshipViewBean.getTransferRequestId()) + "," +
		ATTRIBUTE_CERTIFICATION_DATE + "=" +
		 DateHandler.getOracleToDateFunction(receiptDescDropshipViewBean.getCertificationDate()) + "," +
		ATTRIBUTE_PICKABLE + "=" +
	 SqlHandler.delimitString(receiptDescDropshipViewBean.getPickable()) + "," +
		ATTRIBUTE_CERTIFIED_BY + "=" +
	 StringHandler.nullIfZero(receiptDescDropshipViewBean.getCertifiedBy()) + "," +
		ATTRIBUTE_CERTIFICATION_NUMBER + "=" +
		 SqlHandler.delimitString(receiptDescDropshipViewBean.getCertificationNumber()) + "," +
		ATTRIBUTE_QUALITY_CONTROL_ITEM + "=" +
		 SqlHandler.delimitString(receiptDescDropshipViewBean.getQualityControlItem()) + "," +
		ATTRIBUTE_MANAGE_KITS_AS_SINGLE_UNIT + "=" +
		 SqlHandler.delimitString(receiptDescDropshipViewBean.getManageKitsAsSingleUnit()) + "," +
		ATTRIBUTE_COMPONENT_ID + "=" +
	 StringHandler.nullIfZero(receiptDescDropshipViewBean.getComponentId()) + "," +
		ATTRIBUTE_MATERIAL_DESC + "=" +
	 SqlHandler.delimitString(receiptDescDropshipViewBean.getMaterialDesc()) + "," +
		ATTRIBUTE_INVENTORY_GROUP_NAME + "=" +
		 SqlHandler.delimitString(receiptDescDropshipViewBean.getInventoryGroupName()) + "," +
		ATTRIBUTE_NUMBER_OF_KITS + "=" +
	 StringHandler.nullIfZero(receiptDescDropshipViewBean.getNumberOfKits()) + "," +
		ATTRIBUTE_NOTES + "=" +
		 SqlHandler.delimitString(receiptDescDropshipViewBean.getNotes()) + "," +
		ATTRIBUTE_STORAGE_TEMP + "=" +
	 SqlHandler.delimitString(receiptDescDropshipViewBean.getStorageTemp()) + " " +
		"where " + ATTRIBUTE_RADIAN_PO + "=" +
		 receiptDescDropshipViewBean.getRadianPo();
	 return new SqlManager().update(conn, query);
	}
	*/

 //select
 public Collection select(SearchCriteria criteria) throws BaseException {

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

 public Collection select(SearchCriteria criteria,
	Connection conn) throws BaseException {

	Collection receiptDescDropshipViewBeanColl = new Vector();

	String query = "select * from " + TABLE + " " + getWhereClause(criteria);

	DataSet dataSet = new SqlManager().select(conn, query);

	Iterator dataIter = dataSet.iterator();

	while (dataIter.hasNext()) {
	 DataSetRow dataSetRow = (DataSetRow) dataIter.next();
	 ReceiptDescDropshipViewBean receiptDescDropshipViewBean = new
		ReceiptDescDropshipViewBean();
	 load(dataSetRow, receiptDescDropshipViewBean);
	 receiptDescDropshipViewBeanColl.add(receiptDescDropshipViewBean);
	}

	return receiptDescDropshipViewBeanColl;
 }
}