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
import com.tcmis.internal.hub.beans.ReceiptDescriptionViewBean;
import com.tcmis.internal.hub.beans.ReceivingInputBean;

/******************************************************************************
 * CLASSNAME: NonchemicalReceivingQcViewBeanFactory <br>
 * @version: 1.0, Sep 7, 2005 <br>
 *****************************************************************************/

public class NonchemicalReceivingQcViewBeanFactory
 extends BaseBeanFactory {

 Log log = LogFactory.getLog(this.getClass());

 //column names
 public String ATTRIBUTE_TRANSFER_REQUEST_ID = "TRANSFER_REQUEST_ID";
 public String ATTRIBUTE_DOC_TYPE = "DOC_TYPE";
 public String ATTRIBUTE_RETURN_PR_NUMBER = "RETURN_PR_NUMBER";
 public String ATTRIBUTE_RETURN_LINE_ITEM = "RETURN_LINE_ITEM";
 public String ATTRIBUTE_RADIAN_PO = "RADIAN_PO";
 public String ATTRIBUTE_PO_LINE = "PO_LINE";
 public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
 public String ATTRIBUTE_MFG_LOT = "MFG_LOT";
 public String ATTRIBUTE_BIN = "BIN";
 public String ATTRIBUTE_RECEIPT_ID = "RECEIPT_ID";
 public String ATTRIBUTE_QUANTITY_RECEIVED = "QUANTITY_RECEIVED";
 public String ATTRIBUTE_LOT_STATUS = "LOT_STATUS";
 public String ATTRIBUTE_EXPIRE_DATE = "EXPIRE_DATE";
 public String ATTRIBUTE_DATE_OF_SHIPMENT = "DATE_OF_SHIPMENT";
 public String ATTRIBUTE_DATE_OF_MANUFACTURE = "DATE_OF_MANUFACTURE";
 public String ATTRIBUTE_DATE_OF_RECEIPT = "DATE_OF_RECEIPT";
 public String ATTRIBUTE_INVENTORY_GROUP_NAME = "INVENTORY_GROUP_NAME";
 public String ATTRIBUTE_LINE_DESC = "LINE_DESC";
 public String ATTRIBUTE_PACKAGING = "PACKAGING";
 public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
 public String ATTRIBUTE_NOTES = "NOTES";
 public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
 public String ATTRIBUTE_LOT_STATUS_ROOT_CAUSE = "LOT_STATUS_ROOT_CAUSE";
 public String ATTRIBUTE_RESPONSIBLE_COMPANY_ID = "RESPONSIBLE_COMPANY_ID";
 public String ATTRIBUTE_LAST_MODIFIED_BY = "LAST_MODIFIED_BY";
 public String ATTRIBUTE_LOT_STATUS_ROOT_CAUSE_NOTES =
	"LOT_STATUS_ROOT_CAUSE_NOTES";
 public String ATTRIBUTE_EXCESS = "EXCESS";
 public String ATTRIBUTE_BRANCH_PLANT = "BRANCH_PLANT";
 public String ATTRIBUTE_DELIVERY_TICKET = "DELIVERY_TICKET";
 public String ATTRIBUTE_APPROVED = "APPROVED";
 public String ATTRIBUTE_END_DATE = "END_DATE";
 public String ATTRIBUTE_HUB = "HUB";
 public String ATTRIBUTE_INVENTORY_GROUP_COLLECTION = "INVENTORY_GROUP_COLLECTION";
 public String ATTRIBUTE_RECEIPT_DOCUMENT_AVAILABLE = "RECEIPT_DOCUMENT_AVAILABLE";
 public String ATTRIBUTE_OPS_ENTITY_ID= "OPS_ENTITY_ID";
 public String ATTRIBUTE_OPS_COMPANY_ID= "OPS_COMPANY_ID";
 public String ATTRIBUTE_QUALITY_TRACKING_NUMBER= "QUALITY_TRACKING_NUMBER";
 
 //table name
 public String TABLE = "NONCHEMICAL_RECEIVING_QC_VIEW";

 //constructor
 public NonchemicalReceivingQcViewBeanFactory(DbManager dbManager) {
	super(dbManager);
 }

 //get column names
 public String getColumnName(String attributeName) {
	if (attributeName.equals("transferRequestId")) {
	 return ATTRIBUTE_TRANSFER_REQUEST_ID;
	}
	else if (attributeName.equals("docType")) {
	 return ATTRIBUTE_DOC_TYPE;
	}
	else if (attributeName.equals("returnPrNumber")) {
	 return ATTRIBUTE_RETURN_PR_NUMBER;
	}
	else if (attributeName.equals("returnLineItem")) {
	 return ATTRIBUTE_RETURN_LINE_ITEM;
	}
	else if (attributeName.equals("radianPo")) {
	 return ATTRIBUTE_RADIAN_PO;
	}
	else if (attributeName.equals("poLine")) {
	 return ATTRIBUTE_PO_LINE;
	}
	else if (attributeName.equals("itemId")) {
	 return ATTRIBUTE_ITEM_ID;
	}
	else if (attributeName.equals("mfgLot")) {
	 return ATTRIBUTE_MFG_LOT;
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
	else if (attributeName.equals("lotStatus")) {
	 return ATTRIBUTE_LOT_STATUS;
	}
	else if (attributeName.equals("expireDate")) {
	 return ATTRIBUTE_EXPIRE_DATE;
	}
	else if (attributeName.equals("dateOfShipment")) {
	 return ATTRIBUTE_DATE_OF_SHIPMENT;
	}
	else if (attributeName.equals("dateOfManufacture")) {
	 return ATTRIBUTE_DATE_OF_MANUFACTURE;
	}
	else if (attributeName.equals("dateOfReceipt")) {
	 return ATTRIBUTE_DATE_OF_RECEIPT;
	}
	else if (attributeName.equals("inventoryGroupName")) {
	 return ATTRIBUTE_INVENTORY_GROUP_NAME;
	}
	else if (attributeName.equals("lineDesc")) {
	 return ATTRIBUTE_LINE_DESC;
	}
	else if (attributeName.equals("packaging")) {
	 return ATTRIBUTE_PACKAGING;
	}
	else if (attributeName.equals("companyId")) {
	 return ATTRIBUTE_COMPANY_ID;
	}
	else if (attributeName.equals("notes")) {
	 return ATTRIBUTE_NOTES;
	}
	else if (attributeName.equals("inventoryGroup")) {
	 return ATTRIBUTE_INVENTORY_GROUP;
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
	else if (attributeName.equals("excess")) {
	 return ATTRIBUTE_EXCESS;
	}
	else if (attributeName.equals("branchPlant")) {
	 return ATTRIBUTE_BRANCH_PLANT;
	}
	else if (attributeName.equals("deliveryTicket")) {
	 return ATTRIBUTE_DELIVERY_TICKET;
	}
	else if (attributeName.equals("approved")) {
	 return ATTRIBUTE_APPROVED;
	}
	else if (attributeName.equals("endDate")) {
	 return ATTRIBUTE_END_DATE;
	}
	else if(attributeName.equals("receiptDocumentAvailable")) {
	 return ATTRIBUTE_RECEIPT_DOCUMENT_AVAILABLE;
	}
	else if(attributeName.equals("opsEntityId")) {
	 return ATTRIBUTE_OPS_ENTITY_ID;
	}
	else if(attributeName.equals("opsCompanyId")) {
	 return ATTRIBUTE_OPS_COMPANY_ID;
	}
	else if(attributeName.equals("qualityTrackingNumber")) {
		 return ATTRIBUTE_QUALITY_TRACKING_NUMBER;
	}
	
	else {
	 return super.getColumnName(attributeName);
	}
 }

 //get column types
 public int getType(String attributeName) {
	return super.getType(attributeName, ReceiptDescriptionViewBean.class);
 }

//you need to verify the primary key(s) before uncommenting this
 /*
//delete
	 public int delete(NonchemicalReceivingQcViewBean nonchemicalReceivingQcViewBean)
	 throws BaseException {
	 SearchCriteria criteria = new SearchCriteria("transferRequestId", "SearchCriterion.EQUALS",
		"" + nonchemicalReceivingQcViewBean.getTransferRequestId());
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
	public int delete(NonchemicalReceivingQcViewBean nonchemicalReceivingQcViewBean, Connection conn)
	 throws BaseException {
	 SearchCriteria criteria = new SearchCriteria("transferRequestId", "SearchCriterion.EQUALS",
		"" + nonchemicalReceivingQcViewBean.getTransferRequestId());
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
	 public int insert(NonchemicalReceivingQcViewBean nonchemicalReceivingQcViewBean)
	 throws BaseException {
	 Connection connection = null;
	 int result = 0;
	 try {
		connection = getDbManager().getConnection();
		result = insert(nonchemicalReceivingQcViewBean, connection);
	 }
	 finally {
		this.getDbManager().returnConnection(connection);
	 }
	 return result;
	}
	public int insert(NonchemicalReceivingQcViewBean nonchemicalReceivingQcViewBean, Connection conn)
	 throws BaseException {
	 SqlManager sqlManager = new SqlManager();
	 String query  =
		"insert into " + TABLE + " (" +
		ATTRIBUTE_TRANSFER_REQUEST_ID + "," +
		ATTRIBUTE_DOC_TYPE + "," +
		ATTRIBUTE_RETURN_PR_NUMBER + "," +
		ATTRIBUTE_RETURN_LINE_ITEM + "," +
		ATTRIBUTE_RADIAN_PO + "," +
		ATTRIBUTE_PO_LINE + "," +
		ATTRIBUTE_ITEM_ID + "," +
		ATTRIBUTE_MFG_LOT + "," +
		ATTRIBUTE_BIN + "," +
		ATTRIBUTE_RECEIPT_ID + "," +
		ATTRIBUTE_QUANTITY_RECEIVED + "," +
		ATTRIBUTE_LOT_STATUS + "," +
		ATTRIBUTE_EXPIRE_DATE + "," +
		ATTRIBUTE_DATE_OF_SHIPMENT + "," +
		ATTRIBUTE_DATE_OF_MANUFACTURE + "," +
		ATTRIBUTE_DATE_OF_RECEIPT + "," +
		ATTRIBUTE_INVENTORY_GROUP_NAME + "," +
		ATTRIBUTE_LINE_DESC + "," +
		ATTRIBUTE_PACKAGING + "," +
		ATTRIBUTE_PART_DESCRIPTION + "," +
		ATTRIBUTE_COMPANY_ID + ")" +
		" values (" +
		nonchemicalReceivingQcViewBean.getTransferRequestId() + "," +
	 SqlHandler.delimitString(nonchemicalReceivingQcViewBean.getDocType()) + "," +
		nonchemicalReceivingQcViewBean.getReturnPrNumber() + "," +
		SqlHandler.delimitString(nonchemicalReceivingQcViewBean.getReturnLineItem()) + "," +
		nonchemicalReceivingQcViewBean.getRadianPo() + "," +
		nonchemicalReceivingQcViewBean.getPoLine() + "," +
		nonchemicalReceivingQcViewBean.getItemId() + "," +
	 SqlHandler.delimitString(nonchemicalReceivingQcViewBean.getMfgLot()) + "," +
		SqlHandler.delimitString(nonchemicalReceivingQcViewBean.getBin()) + "," +
		nonchemicalReceivingQcViewBean.getReceiptId() + "," +
		nonchemicalReceivingQcViewBean.getQuantityReceived() + "," +
	 SqlHandler.delimitString(nonchemicalReceivingQcViewBean.getLotStatus()) + "," +
		DateHandler.getOracleToDateFunction(nonchemicalReceivingQcViewBean.getExpireDate()) + "," +
		DateHandler.getOracleToDateFunction(nonchemicalReceivingQcViewBean.getDateOfShipment()) + "," +
		DateHandler.getOracleToDateFunction(nonchemicalReceivingQcViewBean.getDateOfManufacture()) + "," +
		DateHandler.getOracleToDateFunction(nonchemicalReceivingQcViewBean.getDateOfReceipt()) + "," +
		SqlHandler.delimitString(nonchemicalReceivingQcViewBean.getInventoryGroupName()) + "," +
	 SqlHandler.delimitString(nonchemicalReceivingQcViewBean.getLineDesc()) + "," +
	 SqlHandler.delimitString(nonchemicalReceivingQcViewBean.getPackaging()) + "," +
		SqlHandler.delimitString(nonchemicalReceivingQcViewBean.getPartDescription()) + "," +
	 SqlHandler.delimitString(nonchemicalReceivingQcViewBean.getCompanyId()) + ")";
	 return sqlManager.update(conn, query);
	}
//update
	 public int update(NonchemicalReceivingQcViewBean nonchemicalReceivingQcViewBean)
	 throws BaseException {
	 Connection connection = null;
	 int result = 0;
	 try {
		connection = getDbManager().getConnection();
		result = update(nonchemicalReceivingQcViewBean, connection);
	 }
	 finally {
		this.getDbManager().returnConnection(connection);
	 }
	 return result;
	}
	public int update(NonchemicalReceivingQcViewBean nonchemicalReceivingQcViewBean, Connection conn)
	 throws BaseException {
	 String query  = "update " + TABLE + " set " +
		ATTRIBUTE_TRANSFER_REQUEST_ID + "=" +
		 StringHandler.nullIfZero(nonchemicalReceivingQcViewBean.getTransferRequestId()) + "," +
		ATTRIBUTE_DOC_TYPE + "=" +
	 SqlHandler.delimitString(nonchemicalReceivingQcViewBean.getDocType()) + "," +
		ATTRIBUTE_RETURN_PR_NUMBER + "=" +
		 StringHandler.nullIfZero(nonchemicalReceivingQcViewBean.getReturnPrNumber()) + "," +
		ATTRIBUTE_RETURN_LINE_ITEM + "=" +
		 SqlHandler.delimitString(nonchemicalReceivingQcViewBean.getReturnLineItem()) + "," +
		ATTRIBUTE_RADIAN_PO + "=" +
	 StringHandler.nullIfZero(nonchemicalReceivingQcViewBean.getRadianPo()) + "," +
		ATTRIBUTE_PO_LINE + "=" +
	 StringHandler.nullIfZero(nonchemicalReceivingQcViewBean.getPoLine()) + "," +
		ATTRIBUTE_ITEM_ID + "=" +
	 StringHandler.nullIfZero(nonchemicalReceivingQcViewBean.getItemId()) + "," +
		ATTRIBUTE_MFG_LOT + "=" +
	 SqlHandler.delimitString(nonchemicalReceivingQcViewBean.getMfgLot()) + "," +
		ATTRIBUTE_BIN + "=" +
		 SqlHandler.delimitString(nonchemicalReceivingQcViewBean.getBin()) + "," +
		ATTRIBUTE_RECEIPT_ID + "=" +
	 StringHandler.nullIfZero(nonchemicalReceivingQcViewBean.getReceiptId()) + "," +
		ATTRIBUTE_QUANTITY_RECEIVED + "=" +
		 StringHandler.nullIfZero(nonchemicalReceivingQcViewBean.getQuantityReceived()) + "," +
		ATTRIBUTE_LOT_STATUS + "=" +
	 SqlHandler.delimitString(nonchemicalReceivingQcViewBean.getLotStatus()) + "," +
		ATTRIBUTE_EXPIRE_DATE + "=" +
		 DateHandler.getOracleToDateFunction(nonchemicalReceivingQcViewBean.getExpireDate()) + "," +
		ATTRIBUTE_DATE_OF_SHIPMENT + "=" +
		 DateHandler.getOracleToDateFunction(nonchemicalReceivingQcViewBean.getDateOfShipment()) + "," +
		ATTRIBUTE_DATE_OF_MANUFACTURE + "=" +
		 DateHandler.getOracleToDateFunction(nonchemicalReceivingQcViewBean.getDateOfManufacture()) + "," +
		ATTRIBUTE_DATE_OF_RECEIPT + "=" +
		 DateHandler.getOracleToDateFunction(nonchemicalReceivingQcViewBean.getDateOfReceipt()) + "," +
		ATTRIBUTE_INVENTORY_GROUP_NAME + "=" +
		 SqlHandler.delimitString(nonchemicalReceivingQcViewBean.getInventoryGroupName()) + "," +
		ATTRIBUTE_LINE_DESC + "=" +
	 SqlHandler.delimitString(nonchemicalReceivingQcViewBean.getLineDesc()) + "," +
		ATTRIBUTE_PACKAGING + "=" +
	 SqlHandler.delimitString(nonchemicalReceivingQcViewBean.getPackaging()) + "," +
		ATTRIBUTE_PART_DESCRIPTION + "=" +
		 SqlHandler.delimitString(nonchemicalReceivingQcViewBean.getPartDescription()) + "," +
		ATTRIBUTE_COMPANY_ID + "=" +
	 SqlHandler.delimitString(nonchemicalReceivingQcViewBean.getCompanyId()) + " " +
		"where " + ATTRIBUTE_TRANSFER_REQUEST_ID + "=" +
		 nonchemicalReceivingQcViewBean.getTransferRequestId();
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

	Collection nonchemicalReceivingQcViewBeanColl = new Vector();

	String query = "select * from " + TABLE + " " + getWhereClause(criteria);

	DataSet dataSet = new SqlManager().select(conn, query);

	Iterator dataIter = dataSet.iterator();

	while (dataIter.hasNext()) {
	 DataSetRow dataSetRow = (DataSetRow) dataIter.next();
	 ReceiptDescriptionViewBean nonchemicalReceivingQcViewBean = new
		ReceiptDescriptionViewBean();
	 load(dataSetRow, nonchemicalReceivingQcViewBean);
	 nonchemicalReceivingQcViewBeanColl.add(nonchemicalReceivingQcViewBean);
	}

	return nonchemicalReceivingQcViewBeanColl;
 }

 //select Sorted
 public Collection selectSorted(SearchCriteria criteria,
	ReceivingInputBean bean,boolean iscollection) throws BaseException {

	Connection connection = null;
	Collection c = null;
	try {
	 connection = this.getDbManager().getConnection();
	 c = selectSorted(criteria, bean,iscollection, connection);
	}
	finally {
	 this.getDbManager().returnConnection(connection);
	}
	return c;
 }

 public Collection selectSorted(SearchCriteria criteria, ReceivingInputBean bean,boolean iscollection,
	Connection conn) throws BaseException {

	//construct sort by clause
	String sortBy = "";
	if (bean.getSort().equalsIgnoreCase("Bin")) {
	 sortBy = " order by x." + ATTRIBUTE_BIN + "";
	}
	else if (bean.getSort().equalsIgnoreCase("Mfg Lot")) {
	 sortBy = " order by x." + ATTRIBUTE_MFG_LOT + "";
	}
	else if (bean.getSort().equalsIgnoreCase("Item Id")) {
	 sortBy = " order by x." + ATTRIBUTE_ITEM_ID + "";
	}
	else if (bean.getSort().equalsIgnoreCase("PO")) {
	 sortBy = " order by x." + ATTRIBUTE_RADIAN_PO + "";
	}
	else if (bean.getSort().equalsIgnoreCase("Receipt ID")) {
	 sortBy = " order by x." + ATTRIBUTE_RECEIPT_ID + "";
	}

	Collection nonchemicalReceivingQcViewBeanColl = new Vector();
	String whereClause = getWhereClause(criteria);
	if (iscollection) {
	 if (whereClause.trim().length() > 0) {
		whereClause += "and ";
	 }

	 whereClause += " " + ATTRIBUTE_INVENTORY_GROUP + " in (select " +
		ATTRIBUTE_INVENTORY_GROUP + " from " + ATTRIBUTE_INVENTORY_GROUP_COLLECTION +
		" where " + ATTRIBUTE_HUB + " = '" + bean.getSourceHub() + "' and " +
		ATTRIBUTE_INVENTORY_GROUP_COLLECTION + " = '" + bean.getInventoryGroup() +
		"') ";
	}

	String query = "select x.* from " + TABLE + " x " + whereClause + sortBy;
	DataSet dataSet = new SqlManager().select(conn, query);
	Iterator dataIter = dataSet.iterator();

	while (dataIter.hasNext()) {
	 DataSetRow dataSetRow = (DataSetRow) dataIter.next();
	 ReceiptDescriptionViewBean nonchemicalReceivingQcViewBean = new
		ReceiptDescriptionViewBean();
	 load(dataSetRow, nonchemicalReceivingQcViewBean);
	 nonchemicalReceivingQcViewBeanColl.add(nonchemicalReceivingQcViewBean);
	}

	return nonchemicalReceivingQcViewBeanColl;
 }
}