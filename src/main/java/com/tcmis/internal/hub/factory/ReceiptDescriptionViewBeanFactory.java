package com.tcmis.internal.hub.factory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Collection;
import java.util.Date;
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
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.ReceiptDescriptionViewBean;
import com.tcmis.internal.hub.beans.ReceivingInputBean;

/******************************************************************************
 * CLASSNAME: ReceiptDescriptionViewBeanFactory <br>
 * @version: 1.0, Aug 23, 2005 <br>
 *****************************************************************************/

public class ReceiptDescriptionViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_RADIAN_PO = "RADIAN_PO";
	public String ATTRIBUTE_PO_LINE = "PO_LINE";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_MFG_LOT = "MFG_LOT";
	public String ATTRIBUTE_DATE_OF_RECEIPT = "DATE_OF_RECEIPT";
	public String ATTRIBUTE_APPROVED = "APPROVED";
	public String ATTRIBUTE_QC_DATE = "QC_DATE";
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
	public String ATTRIBUTE_LOT_STATUS_ROOT_CAUSE_NOTES = "LOT_STATUS_ROOT_CAUSE_NOTES";
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
	public String ATTRIBUTE_MANAGE_KITS_AS_SINGLE_UNIT = "MANAGE_KITS_AS_SINGLE_UNIT";
	public String ATTRIBUTE_COMPONENT_ID = "COMPONENT_ID";
	public String ATTRIBUTE_MATERIAL_DESC = "MATERIAL_DESC";
	public String ATTRIBUTE_INVENTORY_GROUP_NAME = "INVENTORY_GROUP_NAME";
	public String ATTRIBUTE_NUMBER_OF_KITS = "NUMBER_OF_KITS";
	public String ATTRIBUTE_NOTES = "NOTES";
	public String ATTRIBUTE_STORAGE_TEMP = "STORAGE_TEMP";
	public String ATTRIBUTE_CRITICAL = "CRITICAL";
	public String ATTRIBUTE_RETURN_PR_NUMBER = "RETURN_PR_NUMBER";
	public String ATTRIBUTE_RETURN_LINE_ITEM = "RETURN_LINE_ITEM";
	public String ATTRIBUTE_DELIVERY_TICKET = "DELIVERY_TICKET";
	public String ATTRIBUTE_MV_ITEM = "MV_ITEM";
	public String ATTRIBUTE_ORDER_QTY_UPDATE_ON_RECEIPT = "ORDER_QTY_UPDATE_ON_RECEIPT";
	public String ATTRIBUTE_HUB = "HUB";
	public String ATTRIBUTE_INVENTORY_GROUP_COLLECTION = "INVENTORY_GROUP_COLLECTION";
	public String ATTRIBUTE_PURCHASING_UNITS_PER_ITEM = "PURCHASING_UNITS_PER_ITEM";
	public String ATTRIBUTE_PURCHASING_UNIT_OF_MEASURE = "PURCHASING_UNIT_OF_MEASURE";
	public String ATTRIBUTE_DISPLAY_PKG_STYLE = "DISPLAY_PKG_STYLE";
	public String ATTRIBUTE_RECEIPT_GROUP = "RECEIPT_GROUP";
	public String ATTRIBUTE_COST_FACTOR = "COST_FACTOR";
	public String ATTRIBUTE_RECEIPT_DOCUMENT_AVAILABLE = "RECEIPT_DOCUMENT_AVAILABLE";
	public String ATTRIBUTE_NEW_CHEM_REQUEST_ID = "NEW_CHEM_REQUEST_ID";
	public String ATTRIBUTE_ITEM_TYPE = "ITEM_TYPE";
	public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
	public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
	public String ATTRIBUTE_CATALOG_COMPANY_ID = "CATALOG_COMPANY_ID";
	public String ATTRIBUTE_PO_NUMBER = "PO_NUMBER";
	public String ATTRIBUTE_SHIP_TO_COMPANY_ID = "SHIP_TO_COMPANY_ID";
	public String ATTRIBUTE_SHIP_TO_LOCATION_ID = "SHIP_TO_LOCATION_ID";
	public String ATTRIBUTE_DO_NUMBER_REQUIRED = "DO_NUMBER_REQUIRED";
	public String ATTRIBUTE_POLCHEM_IG = "POLCHEM_IG";
	public String ATTRIBUTE_SPECS = "SPECS";
	public String ATTRIBUTE_OPS_ENTITY_ID = "OPS_ENTITY_ID";
	public String ATTRIBUTE_OPS_COMPANY_ID = "OPS_COMPANY_ID";
	public String ATTRIBUTE_CUSTOMER_RMA_ID = "CUSTOMER_RMA_ID";
	public String ATTRIBUTE_MINIMUM_EXPIRE_DATE = "MINIMUM_EXPIRE_DATE";
	public String ATTRIBUTE_QA_STATEMENT = "QA_STATEMENT";
	public String ATTRIBUTE_INTERCOMPANY_PO = "INTERCOMPANY_PO";
	public String ATTRIBUTE_INTERCOMPANY_PO_LINE = "INTERCOMPANY_PO_LINE";
	public String ATTRIBUTE_INTERCOMPANY_SUPPLIER_NAME = "INTERCOMPANY_SUPPLIER_NAME";
	public String ATTRIBUTE_INCOMING_TESTING = "INCOMING_TESTING";
	public String ATTRIBUTE_LOCK_EXPIRE_DATE = "LOCK_EXPIRE_DATE";
	public String ATTRIBUTE_RECEIPT_SHELF_LIFE_BASIS = "RECEIPT_SHELF_LIFE_BASIS";
	public String ATTRIBUTE_QUALITY_TRACKING_NUMBER = "QUALITY_TRACKING_NUMBER";
    public String ATTRIBUTE_AMENDMENT = "AMENDMENT";
    public String ATTRIBUTE_INBOUND_SHIPMENT_DETAIL_ID = "INBOUND_SHIPMENT_DETAIL_ID";
    public String ATTRIBUTE_DELIVERY_COMMENTS = "DELIVERY_COMMENTS";
	public String ATTRIBUTE_DATE_OF_REPACKAGING = "DATE_OF_REPACKAGING";
    public String ATTRIBUTE_LAB_TEST_COMPLETE = "LAB_TEST_COMPLETE";

    //table name
	public String TABLE = "RECEIPT_DESCRIPTION_VIEW";

	//constructor
	public ReceiptDescriptionViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}

	//get column names
	@Override
	public String getColumnName(String attributeName) {
		if (attributeName.equals("radianPo")) {
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
		else if (attributeName.equals("dateOfReceipt")) {
			return ATTRIBUTE_DATE_OF_RECEIPT;
		}
		else if (attributeName.equals("approved")) {
			return ATTRIBUTE_APPROVED;
		}
		else if (attributeName.equals("qcDate")) {
			return ATTRIBUTE_QC_DATE;
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
		else if (attributeName.equals("critical")) {
			return ATTRIBUTE_CRITICAL;
		}
		else if (attributeName.equals("returnPrNumber")) {
			return ATTRIBUTE_RETURN_PR_NUMBER;
		}
		else if (attributeName.equals("returnLineItem")) {
			return ATTRIBUTE_RETURN_LINE_ITEM;
		}
		else if (attributeName.equals("deliveryTicket")) {
			return ATTRIBUTE_DELIVERY_TICKET;
		}
		else if (attributeName.equals("mvItem")) {
			return ATTRIBUTE_MV_ITEM;
		}
		else if (attributeName.equals("orderQtyUpdateOnReceipt")) {
			return ATTRIBUTE_ORDER_QTY_UPDATE_ON_RECEIPT;
		}
		else if (attributeName.equals("purchasingUnitsPerItem")) {
			return ATTRIBUTE_PURCHASING_UNITS_PER_ITEM;
		}
		else if (attributeName.equals("purchasingUnitOfMeasure")) {
			return ATTRIBUTE_PURCHASING_UNIT_OF_MEASURE;
		}
		else if (attributeName.equals("displayPkgStyle")) {
			return ATTRIBUTE_DISPLAY_PKG_STYLE;
		}
		else if (attributeName.equals("receiptGroup")) {
			return ATTRIBUTE_RECEIPT_GROUP;
		}
		else if (attributeName.equals("costFactor")) {
			return ATTRIBUTE_COST_FACTOR;
		}
		else if (attributeName.equals("receiptDocumentAvailable")) {
			return ATTRIBUTE_RECEIPT_DOCUMENT_AVAILABLE;
		}
		else if (attributeName.equals("newChemRequestId")) {
			return ATTRIBUTE_NEW_CHEM_REQUEST_ID;
		}
		else if (attributeName.equals("itemType")) {
			return ATTRIBUTE_ITEM_TYPE;
		}
		else if (attributeName.equals("catalogId")) {
			return ATTRIBUTE_CATALOG_ID;
		}
		else if (attributeName.equals("catPartNo")) {
			return ATTRIBUTE_CAT_PART_NO;
		}
		else if (attributeName.equals("catalogCompanyId")) {
			return ATTRIBUTE_CATALOG_COMPANY_ID;
		}
		else if (attributeName.equals("poNumber")) {
			return ATTRIBUTE_PO_NUMBER;
		}
		else if (attributeName.equals("shipToCompanyId")) {
			return ATTRIBUTE_SHIP_TO_COMPANY_ID;
		}
		else if (attributeName.equals("shipToLocationId")) {
			return ATTRIBUTE_SHIP_TO_LOCATION_ID;
		}
		else if (attributeName.equals("doNumberRequired")) {
			return ATTRIBUTE_DO_NUMBER_REQUIRED;
		}
		else if (attributeName.equals("polchemIg")) {
			return ATTRIBUTE_POLCHEM_IG;
		}
		else if (attributeName.equals("specs")) {
			return ATTRIBUTE_SPECS;
		}
		else if (attributeName.equals("opsEntityId")) {
			return ATTRIBUTE_OPS_ENTITY_ID;
		}
		else if (attributeName.equals("opsCompanyId")) {
			return ATTRIBUTE_OPS_COMPANY_ID;
		}
		else if (attributeName.equals("customerRmaId")) {
			return ATTRIBUTE_CUSTOMER_RMA_ID;
		}
		else if (attributeName.equals("minimumExpireDate")) {
			return ATTRIBUTE_MINIMUM_EXPIRE_DATE;
		}
		else if (attributeName.equals("qaStatement")) {
			return ATTRIBUTE_QA_STATEMENT;
		}
		else if(attributeName.equals("intercompanyPo")) {
			return ATTRIBUTE_INTERCOMPANY_PO;
		}
		else if(attributeName.equals("intercompanyPoLine")) {
			return ATTRIBUTE_INTERCOMPANY_PO_LINE;
		}
		else if(attributeName.equals("intercompanySupplierName")) {
			return ATTRIBUTE_INTERCOMPANY_SUPPLIER_NAME;
		}
		else if(attributeName.equals("incomingTesting")) {
			return ATTRIBUTE_INCOMING_TESTING;
		}
		else if(attributeName.equals("lockExpireDate")) {
			return ATTRIBUTE_LOCK_EXPIRE_DATE;
		}
		else if(attributeName.equals("receiptShelfLifeBasis")) {
			return ATTRIBUTE_RECEIPT_SHELF_LIFE_BASIS;
		}
		else if(attributeName.equals("qualityTrackingNumber")) {
			 return ATTRIBUTE_QUALITY_TRACKING_NUMBER;
		}
		else if(attributeName.equals("amendment")) {
			 return ATTRIBUTE_AMENDMENT;
		}
		else if(attributeName.equals("inboundShipmentDetailId")) {
			 return ATTRIBUTE_INBOUND_SHIPMENT_DETAIL_ID;
		}
		else if(attributeName.equals("deliveryComments")) {
			 return ATTRIBUTE_DELIVERY_COMMENTS;
		}
		else if(attributeName.equals("dateOfRepackaging")) {
			 return ATTRIBUTE_DATE_OF_REPACKAGING;
		}
        else if(attributeName.equals("labTestComplete")) {
			 return ATTRIBUTE_LAB_TEST_COMPLETE;
		}
        else {
			return super.getColumnName(attributeName);
		}
	}

	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, ReceiptDescriptionViewBean.class);
	}

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

	public int delete(SearchCriteria criteria, Connection conn) throws BaseException {

		String sqlQuery = " delete from " + TABLE + " " + getWhereClause(criteria);

		return new SqlManager().update(conn, sqlQuery);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Collection receiptQc(ReceiptDescriptionViewBean receiptBean, ReceiptDescriptionViewBean receiptKitBean, BigDecimal personnelId, boolean nonChemicalreceipt) throws Exception {
		Collection cin = new Vector();

		if ("Y".equalsIgnoreCase(receiptBean.getMvItem())) {
			cin.add(receiptKitBean.getReceiptId());
		}
		else if (receiptBean.getReceiptId() != null) {
			cin.add(receiptBean.getReceiptId());
		}
		else {
			cin.add("");
		}

		cin.add("" + personnelId + "");

		Date dateOfShipment = null;
		if ("Y".equalsIgnoreCase(receiptBean.getMvItem())) {
			dateOfShipment = receiptBean.getDateOfShipment();
		}
		else {
			dateOfShipment = receiptKitBean.getDateOfShipment();
		}
		if (dateOfShipment != null) {
			try {
				cin.add(new Timestamp(dateOfShipment.getTime()));
			}
			catch (Exception ex) {
			}
		}
		else {
			cin.add("");
		}

		Date dateOfManufacture = null;
		if ("Y".equalsIgnoreCase(receiptBean.getMvItem())) {
			dateOfManufacture = receiptBean.getDateOfManufacture();
		}
		else {
			dateOfManufacture = receiptKitBean.getDateOfManufacture();
		}
		if (dateOfManufacture != null) {
			try {
				cin.add(new Timestamp(dateOfManufacture.getTime()));
			}
			catch (Exception ex) {
			}
		}
		else {
			cin.add("");
		}

		Date dateOfReceipt = null;
		if ("Y".equalsIgnoreCase(receiptBean.getMvItem())) {
			dateOfReceipt = receiptBean.getDateOfReceipt();
		}
		else {
			dateOfReceipt = receiptKitBean.getDateOfReceipt();
		}
		if (dateOfReceipt != null) {
			try {
				cin.add(new Timestamp(dateOfReceipt.getTime()));
			}
			catch (Exception ex) {
			}
		}
		else {
			cin.add("");
		}

		Date expireDate = null;
		if(receiptBean.getLockExpireDate() == null || !receiptBean.getLockExpireDate().equals("Y")) {
			if ("Y".equalsIgnoreCase(receiptBean.getMvItem())) {
				expireDate = receiptBean.getExpireDate();
			}
			else {
				expireDate = receiptKitBean.getExpireDate();
			}
			if (expireDate != null) {
				try {
					cin.add(new Timestamp(expireDate.getTime()));
				}
				catch (Exception ex) {
				}
			}
			else {
				cin.add("");
			}
		}
		else
		{
			cin.add("");
		}

		Date vendorShipDate = null;
		if ("Y".equalsIgnoreCase(receiptBean.getMvItem())) {
			vendorShipDate = receiptBean.getVendorShipDate();
		}
		else {
			vendorShipDate = receiptKitBean.getVendorShipDate();
		}
		if (vendorShipDate != null) {
			try {
				cin.add(new Timestamp(vendorShipDate.getTime()));
			}
			catch (Exception ex) {
			}
		}
		else {
			cin.add("");
		}

		if (nonChemicalreceipt) {
			cin.add("Available");
		}
		else if (receiptBean.getLotStatus() != null && receiptBean.getLotStatus().trim().length() > 0) {
			cin.add(new String(receiptBean.getLotStatus()));
		}
		else {
			cin.add("");
		}

		cin.add("" + personnelId + "");

		if (receiptKitBean.getLotStatusRootCause() != null && receiptKitBean.getLotStatusRootCause().trim().length() > 0) {
			cin.add(new String(receiptKitBean.getLotStatusRootCause()));
		}
		else {
			cin.add("");
		}

		if (receiptKitBean.getResponsibleCompanyId() != null && receiptKitBean.getResponsibleCompanyId().trim().length() > 0) {
			cin.add(new String(receiptKitBean.getResponsibleCompanyId()));
		}
		else {
			cin.add("");
		}

		if (receiptKitBean.getLotStatusRootCauseNotes() != null && receiptKitBean.getLotStatusRootCauseNotes().trim().length() > 0) {
			cin.add(new String(receiptKitBean.getLotStatusRootCauseNotes()));
		}
		else {
			cin.add("");
		}

		cin.add("");
		cin.add("");

		if ("Y".equalsIgnoreCase(receiptBean.getMvItem())) {
			if (receiptBean.getQualityControlItem() != null && "Y".equalsIgnoreCase(receiptBean.getQualityControlItem().trim()) && receiptBean.getCertificationUpdate() != null
					&& "Yes".equalsIgnoreCase(receiptBean.getCertificationUpdate().trim())) {
				cin.add("" + personnelId + "");
			}
			else {
				cin.add("");
			}
		}
		else if (receiptKitBean.getQualityControlItem() != null && "Y".equalsIgnoreCase(receiptKitBean.getQualityControlItem().trim()) && receiptKitBean.getCertificationUpdate() != null
				&& "Yes".equalsIgnoreCase(receiptKitBean.getCertificationUpdate().trim())) {
			cin.add("" + personnelId + "");
		}
		else {
			cin.add("");
		}

		if ("Y".equalsIgnoreCase(receiptBean.getMvItem())) {
			if (receiptBean.getMfgLot() != null && receiptBean.getMfgLot().trim().length() > 0) {
				cin.add(new String(receiptBean.getMfgLot().trim()));
			}
			else {
				cin.add("");
			}
		}
		else if (receiptKitBean.getMfgLot() != null && receiptKitBean.getMfgLot().trim().length() > 0) {
			cin.add(new String(receiptKitBean.getMfgLot()));
		}
		else {
			cin.add("");
		}

		if ("Y".equalsIgnoreCase(receiptBean.getMvItem())) {
			if (receiptBean.getNotes() != null && receiptBean.getNotes().trim().length() > 0) {
				cin.add(new String(receiptBean.getNotes()));
			}
			else {
				cin.add("");
			}
		}
		else if (receiptKitBean.getNotes() != null && receiptKitBean.getNotes().trim().length() > 0) {
			cin.add(new String(receiptKitBean.getNotes()));
		}
		else {
			cin.add("");
		}

		Collection cout = new Vector();
		cout.add(new Integer(Types.VARCHAR));

		Collection cOptional = new Vector();
		if ("Y".equalsIgnoreCase(receiptBean.getMvItem())) {
			if (receiptBean.getDeliveryTicket() != null && receiptBean.getDeliveryTicket().trim().length() > 0) {
				cOptional.add(new String(receiptBean.getDeliveryTicket().trim()));
			}
			else {
				cOptional.add("");
			}
		}
		else if (receiptKitBean.getDeliveryTicket() != null && receiptKitBean.getDeliveryTicket().trim().length() > 0) {
			cOptional.add(new String(receiptKitBean.getDeliveryTicket().trim()));
		}
		else {
			cOptional.add("");
		}

		if ("Y".equalsIgnoreCase(receiptBean.getMvItem())) {
			if (receiptBean.getClosePoLine() != null && receiptBean.getClosePoLine().trim().length() > 0) {
				cOptional.add(new String(receiptBean.getClosePoLine().trim()));
			}
			else {
				cOptional.add("");
			}
		}
		else if (receiptKitBean.getClosePoLine() != null && receiptKitBean.getClosePoLine().trim().length() > 0) {
			cOptional.add(new String(receiptKitBean.getClosePoLine().trim()));
		}
		else {
			cOptional.add("");
		}

		if (receiptBean.getBin() != null && receiptBean.getBin().trim().length() > 0) {
			cOptional.add(new String(receiptBean.getBin().trim()));
		}
		else {
			cOptional.add("");
		}

		cOptional.add("");

		if (receiptBean.getUnitLabelPrinted() != null && receiptBean.getUnitLabelPrinted().trim().length() > 0 && receiptBean.getUnitLabelCatPartNo() != null && receiptBean.getUnitLabelCatPartNo().trim().length() > 0) {
			cOptional.add("Y");
			cOptional.add(receiptBean.getUnitLabelCatPartNo().trim());
		}
		else {
			cOptional.add("N");
			cOptional.add("");
		}

		if (receiptBean.getQaStatement() != null) {
			cOptional.add(receiptBean.getQaStatement());
		}
		else {
			cOptional.add("");
		}

		cOptional.add(nonChemicalreceipt ? "Y" : "N");

		if (receiptBean.getIntercompanyPo() != null) {
			cOptional.add(receiptBean.getIntercompanyPo());
		}
		else {
			cOptional.add("");
		}

		if (receiptBean.getIntercompanyPoLine() != null) {
			cOptional.add(receiptBean.getIntercompanyPoLine());
		}
		else {
			cOptional.add("");
		}

		if (receiptBean.getNewQuantityReceived() != null) {
			cOptional.add(receiptBean.getNewQuantityReceived());
		}
		else {
			cOptional.add("");
		}
		
		if (receiptBean.getQualityTrackingNumber()!= null) {
			cOptional.add(receiptBean.getQualityTrackingNumber());
		}
		else {
			cOptional.add("");
		}
		
		if (receiptBean.getInternalReceiptNotes()!= null) {
			cOptional.add(receiptBean.getInternalReceiptNotes());
		}
		else {
			cOptional.add("");
		}
		
		if (receiptBean.getMfgLabelExpireDate()!= null) {
			cOptional.add(receiptBean.getMfgLabelExpireDate());
		}
		else {
			cOptional.add("");
		}

		if (receiptBean.getNewMsdsRevReceivedFlag()!= null) {
			cOptional.add(receiptBean.getNewMsdsRevReceivedFlag());
		}
		else {
			cOptional.add("");
		}
		
		if (receiptBean.getBinned() != null) {
			cOptional.add(receiptBean.getBinned());
		}
		else {
			cOptional.add("");
		}
		
		if (receiptBean.getSupplierCageCode() != null) {
			cOptional.add(receiptBean.getSupplierCageCode());
		}
		else {
			cOptional.add("");
		}
		
		if(receiptBean.getDateOfRepackaging()!=null){
			cOptional.add(receiptBean.getDateOfRepackaging());
		}
		else {
			cOptional.add("");
		}

		cOptional.add(
				StringHandler.isBlankString(receiptBean.getCountryOfOrigin()) ? "" : receiptBean.getCountryOfOrigin());

		log.debug("P_RECEIPT_QC:\ncin - " + cin + "\ncout - " + cout + "\ncOptional - " + cOptional);

		return this.getDbManager().doProcedure("P_RECEIPT_QC", cin, cout, cOptional);
	}

	//update call p_receipt_component
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean insertReceiptComponenet(ReceiptDescriptionViewBean bean, BigDecimal receiptId, BigDecimal personnelId) throws Exception {
		Collection cin = new Vector();

		cin.add(receiptId);

		if (bean.getItemId() != null) {
			cin.add(bean.getItemId());
		}
		else {
			cin.add("");
		}

		if (bean.getComponentId() != null) {
			cin.add(bean.getComponentId());
		}
		else {
			cin.add("");
		}

		if (bean.getMfgLot() != null && bean.getMfgLot().trim().length() > 0) {
			cin.add(new String(bean.getMfgLot().trim()));
		}
		else {
			cin.add("");
		}

		if (bean.getBin() != null && bean.getBin().trim().length() > 0) {
			cin.add(bean.getBin());
		}
		else {
			cin.add("");
		}

		if (bean.getExpireDate() != null) {
			try {
				cin.add(new Timestamp(bean.getExpireDate().getTime()));
			}
			catch (Exception ex) {
			}
		}
		else {
			cin.add("");
		}
		
		if (bean.getMfgLabelExpireDate()!= null) {
			cin.add(bean.getMfgLabelExpireDate());
		}
		else {
			cin.add("");
		}
		
	   if(bean.getDateOfRepackaging()!=null){
		   cin.add(bean.getDateOfRepackaging());
        }
        else {
        	cin.add("");
        }
		
		log.debug("P_RECEIPT_COMPONENT :" + cin);
		this.getDbManager().doProcedure("P_RECEIPT_COMPONENT", cin);
		
		return true;
	}

	public Collection processRmaRequest(BigDecimal customerRmaId, BigDecimal personnelId) throws Exception {
		Connection connection = this.getDbManager().getConnection();
		Collection cin = new Vector();

		cin.add(customerRmaId);
		cin.add(personnelId);

		Collection cout = new Vector();
		cout.add(new Integer(java.sql.Types.VARCHAR));

		log.info("P_RMA_CUSTOMER_CREDIT: " + cin + "");

		Collection result = this.getDbManager().doProcedure("P_RMA_CUSTOMER_CREDIT", cin, cout);
		this.getDbManager().returnConnection(connection);
		return result;
	}

	public void processInvTransferAdjustment(BigDecimal receiptId, BigDecimal personnelId) throws Exception {
		Connection connection = this.getDbManager().getConnection();
		try {
			Collection cin = new Vector();

			cin.add(receiptId);
			cin.add(personnelId);

			log.info("P_INV_TRANSFER_ADJUSTMENT: " + cin + "");

			this.getDbManager().doProcedure("P_INV_TRANSFER_ADJUSTMENT", cin);
		} finally {
			getDbManager().returnConnection(connection);
		}

	}

	//update call p_receipt_component
	/*public boolean insertReceiptMv(ReceiptDescriptionViewBean bean,
	BigDecimal receiptId, BigDecimal personnelId) throws Exception {
	Connection connection = this.getDbManager().getConnection();
	Collection cin = new Vector();

	cin.add(receiptId);

	if (bean.getPendingQuantityReceived() != null) {
	 cin.add("" + bean.getPendingQuantityReceived() + "");
	}
	else {
	 cin.add("");
	}

	if (bean.getPendingActualPartSize() != null) {
	 cin.add("" + bean.getPendingActualPartSize() + "");
	}
	else {
	 cin.add("");
	}

	if (bean.getPendingReceiptId() != null) {
	 cin.add(bean.getPendingReceiptId());
	}
	else {
	 cin.add("");
	}

	this.getDbManager().doProcedure("P_RECEIPT_INSERT_MV", cin);

	this.getDbManager().returnConnection(connection);
	return true;
	}*/

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

	public Collection select(SearchCriteria criteria, Connection conn) throws BaseException {

		Collection receiptDescriptionViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " + getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			ReceiptDescriptionViewBean receiptDescriptionViewBean = new ReceiptDescriptionViewBean();
			load(dataSetRow, receiptDescriptionViewBean);
			receiptDescriptionViewBeanColl.add(receiptDescriptionViewBean);
		}

		return receiptDescriptionViewBeanColl;
	}

	public Collection selectSorted(SearchCriteria criteria, ReceivingInputBean bean, boolean iscollection, boolean hasUpdatePermission) throws BaseException {

		//construct sort by clause
		String sortBy = "";
		if (bean.getSort().equalsIgnoreCase("Bin")) {
			sortBy = " x." + ATTRIBUTE_BIN + "";
		}
		else if (bean.getSort().equalsIgnoreCase("Mfg Lot")) {
			sortBy = " x." + ATTRIBUTE_MFG_LOT + "";
		}
		else if (bean.getSort().equalsIgnoreCase("Item Id")) {
			sortBy = " x." + ATTRIBUTE_ITEM_ID + "";
		}
		else if (bean.getSort().equalsIgnoreCase("PO")) {
			sortBy = " x." + ATTRIBUTE_RADIAN_PO + "";
		}
		else if (bean.getSort().equalsIgnoreCase("Receipt ID")) {
			sortBy = " x." + ATTRIBUTE_RECEIPT_ID + "";
		}

		Collection receiptDescriptionViewBeanColl = new Vector();
		String whereClause = getWhereClause(criteria);
		if (iscollection) {
			if (whereClause.trim().length() > 0) {
				whereClause += "and ";
			}

			whereClause += " " + ATTRIBUTE_INVENTORY_GROUP + " in (select " + ATTRIBUTE_INVENTORY_GROUP + " from " + ATTRIBUTE_INVENTORY_GROUP_COLLECTION + " where " + ATTRIBUTE_HUB + " = '" + bean.getSourceHub() + "' and "
			+ ATTRIBUTE_INVENTORY_GROUP_COLLECTION + " = '" + bean.getInventoryGroup() + "') ";
		}

		String query = "select x.*, min(" + sortBy + ") OVER (partition by x.RECEIPT_ID) AS SORT_ORDER from " + TABLE + " x " + whereClause + " order by SORT_ORDER,x.RECEIPT_ID ";

		Connection connection = getDbManager().getConnection();
		DataSet dataSet = new SqlManager().select(connection, query);
		getDbManager().returnConnection(connection);

		Iterator dataIter = dataSet.iterator();
		ReceiptDescriptionViewBean receiptDescriptionViewBean = null;
		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			receiptDescriptionViewBean = new ReceiptDescriptionViewBean();
			load(dataSetRow, receiptDescriptionViewBean);
			receiptDescriptionViewBeanColl.add(receiptDescriptionViewBean);
        }
		return receiptDescriptionViewBeanColl;
	}

}