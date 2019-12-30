package com.tcmis.internal.hub.factory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.internal.hub.beans.ReceivingInputBean;
import com.tcmis.internal.hub.beans.ReceivingKitBean;
import com.tcmis.internal.hub.beans.ReceivingViewBean;

/******************************************************************************
 * CLASSNAME: ReceivingViewBeanFactory <br>
 * @version: 1.0, Jul 27, 2005 <br>
 *****************************************************************************/
public class ReceivingViewBeanFactory
extends BaseBeanFactory {
	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_RADIAN_PO = "RADIAN_PO";
	public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
	public String ATTRIBUTE_QTY_OPEN = "QTY_OPEN";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_EXPECTED = "EXPECTED";
	public String ATTRIBUTE_QTY = "QTY";
	public String ATTRIBUTE_BRANCH_PLANT = "BRANCH_PLANT";
	public String ATTRIBUTE_ITEM_DESCRIPTION = "ITEM_DESCRIPTION";
	public String ATTRIBUTE_PO_NOTES = "PO_NOTES";
	public String ATTRIBUTE_TRANS_TYPE = "TRANS_TYPE";
	public String ATTRIBUTE_TRANSFER_REQUEST_ID = "TRANSFER_REQUEST_ID";
	public String ATTRIBUTE_LAST_SUPPLIER = "LAST_SUPPLIER";
	public String ATTRIBUTE_INDEFINITE_SHELF_LIFE = "INDEFINITE_SHELF_LIFE";
	public String ATTRIBUTE_CRITICAL = "CRITICAL";
	public String ATTRIBUTE_ORIGINAL_RECEIPT_ID = "ORIGINAL_RECEIPT_ID";
	public String ATTRIBUTE_ORIGINAL_MFG_LOT = "ORIGINAL_MFG_LOT";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_MANAGE_KITS_AS_SINGLE_UNIT =
		"MANAGE_KITS_AS_SINGLE_UNIT";
	public String ATTRIBUTE_COMPONENT_ID = "COMPONENT_ID";
	public String ATTRIBUTE_MATERIAL_ID = "MATERIAL_ID";
	public String ATTRIBUTE_PACKAGING = "PACKAGING";
	public String ATTRIBUTE_MATERIAL_DESC = "MATERIAL_DESC";
	public String ATTRIBUTE_INVENTORY_GROUP_NAME = "INVENTORY_GROUP_NAME";
	public String ATTRIBUTE_NUMBER_OF_KITS = "NUMBER_OF_KITS";
	public String ATTRIBUTE_SKIP_RECEIVING_QC = "SKIP_RECEIVING_QC";
	public String ATTRIBUTE_MV_ITEM = "MV_ITEM";
	public String ATTRIBUTE_PURCHASING_UNITS_PER_ITEM = "PURCHASING_UNITS_PER_ITEM";
	public String ATTRIBUTE_PURCHASING_UNIT_OF_MEASURE = "PURCHASING_UNIT_OF_MEASURE";
	public String ATTRIBUTE_DISPLAY_PKG_STYLE = "DISPLAY_PKG_STYLE";
	public String ATTRIBUTE_DATE_OF_RECEIPT = "DATE_OF_RECEIPT";
	public String ATTRIBUTE_ORDER_QTY_UPDATE_ON_RECEIPT = "ORDER_QTY_UPDATE_ON_RECEIPT";
	public String ATTRIBUTE_HUB = "HUB";
	public String ATTRIBUTE_INVENTORY_GROUP_COLLECTION = "INVENTORY_GROUP_COLLECTION";
	public String ATTRIBUTE_CUSTOMER_PO = "CUSTOMER_PO";
	public String ATTRIBUTE_NONINTEGER_RECEIVING = "NONINTEGER_RECEIVING";
	public String ATTRIBUTE_DROPSHIP = "DROPSHIP";
	public String ATTRIBUTE_OWNER_COMPANY_ID = "OWNER_COMPANY_ID";
	public String ATTRIBUTE_DOC_NUM = "DOC_NUM";
	public String ATTRIBUTE_RECEIVING_PAGE_PACKAGED_SIZE = "RECEIVING_PAGE_PACKAGED_SIZE";
	public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
	public String ATTRIBUTE_OPS_ENTITY_ID = "OPS_ENTITY_ID";
	public String ATTRIBUTE_OPS_COMPANY_ID = "OPS_COMPANY_ID";
	public String ATTRIBUTE_CUSTOMER_RMA_ID = "CUSTOMER_RMA_ID";
	public String ATTRIBUTE_ORIGINAL_QTY = "ORIGINAL_QTY";
	public String ATTRIBUTE_INTERCOMPANY_PO = "INTERCOMPANY_PO";
	public String ATTRIBUTE_INTERCOMPANY_PO_LINE = "INTERCOMPANY_PO_LINE";
	public String ATTRIBUTE_INTERCOMPANY_SUPPLIER_NAME = "INTERCOMPANY_SUPPLIER_NAME";
	public String ATTRIBUTE_DEFINED_SHELF_LIFE_ITEM = "DEFINED_SHELF_LIFE_ITEM";
	public String ATTRIBUTE_DEFINED_SHELF_LIFE_BASIS = "DEFINED_SHELF_LIFE_BASIS";

	public String ATTRIBUTE_OWNER_SEGMENT_ID = "OWNER_SEGMENT_ID";
	public String ATTRIBUTE_ACCOUNT_NUMBER = "ACCOUNT_NUMBER";
	public String ATTRIBUTE_ACCOUNT_NUMBER2 = "ACCOUNT_NUMBER2";
	public String ATTRIBUTE_ACCOUNT_NUMBER3 = "ACCOUNT_NUMBER3";
	public String ATTRIBUTE_ACCOUNT_NUMBER4 = "ACCOUNT_NUMBER4";
	public String ATTRIBUTE_CUSTOMER_RECEIPT_ID = "CUSTOMER_RECEIPT_ID";
	public String ATTRIBUTE_QUALITY_TRACKING_NUMBER = "QUALITY_TRACKING_NUMBER";
	public String ATTRIBUTE_DATE_OF_REPACKAGING= "DATE_OF_REPACKAGING";

	
	//table name
	public String TABLE = "RECEIVING_VIEW";

	//constructor
	public ReceivingViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}

	//get column names
	@Override
	public String getColumnName(String attributeName) {
		if (attributeName.equals("radianPo")) {
			return ATTRIBUTE_RADIAN_PO;
		}
		else if (attributeName.equals("lineItem")) {
			return ATTRIBUTE_LINE_ITEM;
		}
		else if (attributeName.equals("qtyOpen")) {
			return ATTRIBUTE_QTY_OPEN;
		}
		else if (attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if (attributeName.equals("expected")) {
			return ATTRIBUTE_EXPECTED;
		}
		else if (attributeName.equals("qty")) {
			return ATTRIBUTE_QTY;
		}
		else if (attributeName.equals("branchPlant")) {
			return ATTRIBUTE_BRANCH_PLANT;
		}
		else if (attributeName.equals("itemDescription")) {
			return ATTRIBUTE_ITEM_DESCRIPTION;
		}
		else if (attributeName.equals("poNotes")) {
			return ATTRIBUTE_PO_NOTES;
		}
		else if (attributeName.equals("transType")) {
			return ATTRIBUTE_TRANS_TYPE;
		}
		else if (attributeName.equals("transferRequestId")) {
			return ATTRIBUTE_TRANSFER_REQUEST_ID;
		}
		else if (attributeName.equals("lastSupplier")) {
			return ATTRIBUTE_LAST_SUPPLIER;
		}
		else if (attributeName.equals("indefiniteShelfLife")) {
			return ATTRIBUTE_INDEFINITE_SHELF_LIFE;
		}
		else if (attributeName.equals("critical")) {
			return ATTRIBUTE_CRITICAL;
		}
		else if (attributeName.equals("originalReceiptId")) {
			return ATTRIBUTE_ORIGINAL_RECEIPT_ID;
		}
		else if (attributeName.equals("originalMfgLot")) {
			return ATTRIBUTE_ORIGINAL_MFG_LOT;
		}
		else if (attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if (attributeName.equals("manageKitsAsSingleUnit")) {
			return ATTRIBUTE_MANAGE_KITS_AS_SINGLE_UNIT;
		}
		else if (attributeName.equals("componentId")) {
			return ATTRIBUTE_COMPONENT_ID;
		}
		else if (attributeName.equals("materialId")) {
			return ATTRIBUTE_MATERIAL_ID;
		}
		else if (attributeName.equals("packaging")) {
			return ATTRIBUTE_PACKAGING;
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
		else if (attributeName.equals("skipReceivingQc")) {
			return ATTRIBUTE_SKIP_RECEIVING_QC;
		}
		else if (attributeName.equals("dateOfReceipt")) {
			return ATTRIBUTE_DATE_OF_RECEIPT;
		}
		else if (attributeName.equals("orderQtyUpdateOnReceipt")) {
			return ATTRIBUTE_ORDER_QTY_UPDATE_ON_RECEIPT;
		}
		else if (attributeName.equals("mvItem")) {
			return ATTRIBUTE_MV_ITEM;
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
		else if (attributeName.equals("customerPo")) {
			return ATTRIBUTE_CUSTOMER_PO;
		}
		else if (attributeName.equals("nonintegerReceiving")) {
			return ATTRIBUTE_NONINTEGER_RECEIVING;
		}
		else if (attributeName.equals("dropship")) {
			return ATTRIBUTE_DROPSHIP;
		}
		else if(attributeName.equals("ownerCompanyId")) {
			return ATTRIBUTE_OWNER_COMPANY_ID;
		}
		else if(attributeName.equals("docNum")) {
			return ATTRIBUTE_DOC_NUM;
		}
		else if(attributeName.equals("receivingPagePackagedSize")) {
			return ATTRIBUTE_RECEIVING_PAGE_PACKAGED_SIZE;
		}
		else if(attributeName.equals("catPartNo")) {
			return ATTRIBUTE_CAT_PART_NO;
		}
		else if(attributeName.equals("opsEntityId")) {
			return ATTRIBUTE_OPS_ENTITY_ID;
		}
		else if(attributeName.equals("opsCompanyId")) {
			return ATTRIBUTE_OPS_COMPANY_ID;
		}
		else if(attributeName.equals("customerRmaId")) {
			return ATTRIBUTE_CUSTOMER_RMA_ID;
		}
		else if(attributeName.equals("originalQty")) {
			return ATTRIBUTE_ORIGINAL_QTY;
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
		else if(attributeName.equals("definedShelfLifeItem")) {
			return ATTRIBUTE_DEFINED_SHELF_LIFE_ITEM;
		}
		else if(attributeName.equals("definedShelfLifeBasis")) {
			return ATTRIBUTE_DEFINED_SHELF_LIFE_BASIS;
		}
		else if(attributeName.equals("ownerSegmentId")) {
			return ATTRIBUTE_OWNER_SEGMENT_ID;
		}
		else if(attributeName.equals("accountNumber")) {
			return ATTRIBUTE_ACCOUNT_NUMBER;
		}
		else if(attributeName.equals("accountNumber2")) {
			return ATTRIBUTE_ACCOUNT_NUMBER2;
		}
		else if(attributeName.equals("accountNumber3")) {
			return ATTRIBUTE_ACCOUNT_NUMBER3;
		}
		else if(attributeName.equals("accountNumber4")) {
			return ATTRIBUTE_ACCOUNT_NUMBER4;
		}
		else if(attributeName.equals("customerReceiptId")) {
			return ATTRIBUTE_CUSTOMER_RECEIPT_ID;
		}
		else if(attributeName.equals("qualityTrackingNumber")) {
			return ATTRIBUTE_QUALITY_TRACKING_NUMBER;
		}
		else if(attributeName.equals("dateOfRepackaging")) {
			 return ATTRIBUTE_DATE_OF_REPACKAGING;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}

	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, ReceivingViewBean.class);
	}

	/*public int delete(SearchCriteria criteria)
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
	 }*/

	//update call p_receipt_insert
	public Collection insertReceipt(ReceivingViewBean reciptBean,
			ReceivingKitBean receiptKitBean, BigDecimal personnelId,
			boolean nonChemicalreceipt) throws Exception {
		Connection connection = this.getDbManager().getConnection();
		Collection cin = new Vector();

		if (reciptBean.getTransType().equalsIgnoreCase("IT")) {
			cin.add(new BigDecimal("0"));
			cin.add(new BigDecimal("0"));
		}
		else {
			if (reciptBean.getRadianPo() != null) {
				cin.add(reciptBean.getRadianPo());
			}
			else {
				cin.add("");
			}

			if (nonChemicalreceipt) {
				if (reciptBean.getReceivePoLine() != null) {
					cin.add(reciptBean.getReceivePoLine());
				}
				else {
					cin.add("");
				}
			}
			else {
				if (reciptBean.getLineItem() != null) {
					cin.add(reciptBean.getLineItem());
				}
				else {
					cin.add("");
				}
			}
		}

		if (reciptBean.getItemId() != null) {
			cin.add(reciptBean.getItemId());
		}
		else {
			cin.add("");
		}

		if ("Y".equalsIgnoreCase(reciptBean.getMvItem())) {
			if (reciptBean.getMfgLot() != null &&
					reciptBean.getMfgLot().trim().length() > 0) {
				cin.add(new String(reciptBean.getMfgLot().trim())); //4
			}
			else
			{
				cin.add(""); //4
			}
		}
		else if (receiptKitBean.getMfgLot() != null &&
				receiptKitBean.getMfgLot().trim().length() > 0) {
			cin.add(new String(receiptKitBean.getMfgLot().trim())); //4
		}
		else {
			cin.add(""); //4
		}

		if (nonChemicalreceipt) {
			cin.add("NBO");
		}
		else {
			if ("Y".equalsIgnoreCase(reciptBean.getMvItem())) {
				if (reciptBean.getBin() != null && reciptBean.getBin().trim().length() > 0) {
					cin.add(reciptBean.getBin()); //5
				}
				else {
					cin.add(""); //5
				}
			}
			else if (receiptKitBean.getBin() != null &&
					receiptKitBean.getBin().trim().length() > 0) {
				cin.add(receiptKitBean.getBin()); //5
			}
			else {
				cin.add(""); //5
			}
		}

		cin.add("");

		Date dom =null;
		if ("Y".equalsIgnoreCase(reciptBean.getMvItem())) {
			dom = reciptBean.getDom();
		}
		else
		{
			dom = receiptKitBean.getDom();
		}
		if (dom != null) {
			try {
				cin.add(new Timestamp(dom.getTime())); //6
			}
			catch (Exception ex) {
			}
		}
		else {
			cin.add(""); //6
		}

		Date dos =null;
		if ("Y".equalsIgnoreCase(reciptBean.getMvItem())) {
			dos = reciptBean.getDos();
		}
		else {
			dos = receiptKitBean.getDos();
		}
		if (dos != null) {
			try {
				cin.add(new Timestamp(dos.getTime())); //7
			}
			catch (Exception ex) {
			}
		}
		else {
			cin.add(""); //7
		}

		if ("N".equalsIgnoreCase(reciptBean.getManageKitsAsSingleUnit())) {
			if (reciptBean.getQuantityReceived() != null) {
				cin.add(reciptBean.getQuantityReceived()); //8
			}
			else {
				cin.add(""); //8
			}
		}
		else if ("Y".equalsIgnoreCase(reciptBean.getMvItem())) {
			if (receiptKitBean.getPackagedQty() != null &&
					receiptKitBean.getPackagedQty().trim().length() > 0) {
				cin.add(new BigDecimal(receiptKitBean.getPackagedQty()));
			}
			else {
				cin.add(""); //8
			}
		}
		else if (receiptKitBean.getQuantityReceived() != null) {
			cin.add(receiptKitBean.getQuantityReceived()); //8
		}
		else {
			cin.add(""); //8
		}

		if ("Y".equalsIgnoreCase(reciptBean.getMvItem())) {
			if (reciptBean.getLotStatus() != null &&
					reciptBean.getLotStatus().trim().length() > 0) {
				cin.add(new String(reciptBean.getLotStatus())); //9
			}
			else {
				cin.add("Incoming"); //9
			}
		}
		else if (receiptKitBean.getLotStatus() != null &&
				receiptKitBean.getLotStatus().trim().length() > 0) {
			cin.add(new String(receiptKitBean.getLotStatus())); //9
		}
		else if (nonChemicalreceipt) {
			cin.add("Available");
		}
		else {
			cin.add("Incoming"); //9
		}

		if (reciptBean.getTransferRequestId() != null) {
			cin.add(reciptBean.getTransferRequestId()); //10
		}
		else {
			cin.add(""); //10
		}

		Date dateOfReceipt = null;
		if ("Y".equalsIgnoreCase(reciptBean.getMvItem())) {
			dateOfReceipt = reciptBean.getDateOfReceipt();
		}
		else {
			dateOfReceipt = receiptKitBean.getDateOfReceipt();
		}
		if (dateOfReceipt != null) {
			try {
				cin.add(new Timestamp(dateOfReceipt.getTime())); //11
			}
			catch (Exception ex) {
			}
		}
		else {
			cin.add(""); //11
		}

		if (reciptBean.getBranchPlant() != null &&
				reciptBean.getBranchPlant().trim().length() > 0) {
			cin.add(new String(reciptBean.getBranchPlant())); //12
		}
		else {
			cin.add(""); //12
		}

		cin.add("" + personnelId + ""); //13

		if (reciptBean.getTransType() != null && reciptBean.getTransType().trim().length() > 0) {
			cin.add(new String(reciptBean.getTransType())); //14
		}
		else {
			cin.add(""); //14
		}

		if (nonChemicalreceipt) {
			cin.add(new Timestamp(com.tcmis.common.util.DateHandler.getDateFromString(
					"MM/dd/yyyy", "01/01/2020").getTime()));
		}
		else if(reciptBean.getDefinedShelfLifeItem() == null || !reciptBean.getDefinedShelfLifeItem().equals("Y")){
			Date expirationDate = null;

				if ("Y".equalsIgnoreCase(reciptBean.getMvItem())) {
					expirationDate = reciptBean.getExpirationDate();
				}
				else {
					expirationDate = receiptKitBean.getExpirationDate();
				}


			if (expirationDate != null) {
				try {
					cin.add(new Timestamp(expirationDate.getTime())); //15
				}
				catch (Exception ex) {
				}
			}
			else {
				cin.add(""); //15
			}
		}
        else
        {
				cin.add(""); //15
	    }

        if (receiptKitBean.getTransferReceiptId() != null) {
			cin.add(receiptKitBean.getTransferReceiptId()); //16
		}
		else {
			cin.add(""); //16
		}

		Date supplierShipDate = null;
		if ("Y".equalsIgnoreCase(reciptBean.getMvItem())) {
			supplierShipDate = reciptBean.getSupplierShipDate();
		}
		else {
			supplierShipDate = receiptKitBean.getSupplierShipDate();
		}
		if (supplierShipDate != null) {
			try {
				cin.add(new Timestamp(supplierShipDate.getTime())); //17
			}
			catch (Exception ex) {
			}
		}
		else {
			cin.add(""); //17
		}

		if (reciptBean.getInventoryGroup() != null &&
				reciptBean.getInventoryGroup().trim().length() > 0) {
			cin.add(new String(reciptBean.getInventoryGroup())); //18
		}
		else {
			cin.add(""); //18
		}

		if ("Y".equalsIgnoreCase(reciptBean.getMvItem())) {
			if (reciptBean.getReceiptNotes() != null &&
					reciptBean.getReceiptNotes().trim().length() > 0) {
				cin.add(new String(reciptBean.getReceiptNotes().trim())); //19
			}
			else {
				cin.add(""); //19
			}
		}
		else if (receiptKitBean.getReceiptNotes() != null &&
				receiptKitBean.getReceiptNotes().trim().length() > 0) {
			cin.add(new String(receiptKitBean.getReceiptNotes().trim())); //19
		}
		else {
			cin.add(""); //19
		}

		Collection cout = new Vector();
		cout.add(new Integer(java.sql.Types.NUMERIC));
		cout.add(new Integer(java.sql.Types.NUMERIC));
		cout.add(new Integer(java.sql.Types.VARCHAR));

		Collection cOptional = new Vector();
		if ("Y".equalsIgnoreCase(reciptBean.getMvItem())) {
			if (reciptBean.getDeliveryTicket() != null &&
					reciptBean.getDeliveryTicket().trim().length() > 0) {
				cOptional.add(new String(reciptBean.getDeliveryTicket().trim()));
			}
			else {
				cOptional.add("");
			}
		}
		else if ("N".equalsIgnoreCase(reciptBean.getManageKitsAsSingleUnit())) {
			if (reciptBean.getDeliveryTicket() != null &&
					reciptBean.getDeliveryTicket().trim().length() > 0) {
				cOptional.add(reciptBean.getDeliveryTicket()); //8
			}
			else {
				cOptional.add(""); //8
			}
		}
		else if (receiptKitBean.getDeliveryTicket() != null &&
				receiptKitBean.getDeliveryTicket().trim().length() > 0) {
			cOptional.add(new String(receiptKitBean.getDeliveryTicket().trim()));
		}
		else {
			cOptional.add("");
		}

		if ("Y".equalsIgnoreCase(reciptBean.getMvItem())) {
			if (reciptBean.getClosePoLine() != null &&
					reciptBean.getClosePoLine().trim().length() > 0) {
				cOptional.add(new String(reciptBean.getClosePoLine().trim()));
			}
			else {
				cOptional.add("");
			}
		}
		else if (receiptKitBean.getClosePoLine() != null &&
				receiptKitBean.getClosePoLine().trim().length() > 0) {
			cOptional.add(new String(receiptKitBean.getClosePoLine().trim()));
		}
		else {
			cOptional.add("");
		}

		if ("Y".equalsIgnoreCase(reciptBean.getMvItem())) {
			if (receiptKitBean.getPackagedSize() != null &&
					receiptKitBean.getPackagedSize().trim().length() > 0) {
				cOptional.add(new BigDecimal(receiptKitBean.getPackagedSize()));
			}
			else {
				cOptional.add("");
			}
		}
		else {
			cOptional.add("");
		}

		if ("Y".equalsIgnoreCase(reciptBean.getMvItem())) {
			if (reciptBean.getReceivedReceipt1() != null) {
				cOptional.add(reciptBean.getReceivedReceipt1());
			}
			else {
				cOptional.add("");
			}
		}
		else {
			cOptional.add("");
		}

		//Customer PO
		if (reciptBean.getCustomerPo() != null &&
				reciptBean.getCustomerPo().trim().length() > 0) {
			cOptional.add(new String(reciptBean.getCustomerPo().trim()));
		}
		else
		{
			cOptional.add("");
		}

		//ownerCompanyId
		if (reciptBean.getOwnerCompanyId() != null &&
				reciptBean.getOwnerCompanyId().trim().length() > 0) {
			cOptional.add(new String(reciptBean.getOwnerCompanyId().trim()));
		}
		else
		{
			cOptional.add("");
		}

		//docNum
		if (reciptBean.getDocNum() != null) {
			cOptional.add(reciptBean.getDocNum());
		}
		else
		{
			cOptional.add("");
		}

		cOptional.add("");
		cOptional.add("");
		cOptional.add("");
		cOptional.add("");
		cOptional.add("Y");
		if (reciptBean.getCustomerRmaId() != null) {
			cOptional.add(reciptBean.getCustomerRmaId());
		}
		else
		{
			cOptional.add("");
		}
		cOptional.add("");
		if (reciptBean.getQaStatement() != null) {
			cOptional.add(reciptBean.getQaStatement());
		}
		else
		{
			cOptional.add("");
		}

        cOptional.add("");

        if (reciptBean.getIntercompanyPo() != null) {
			cOptional.add(reciptBean.getIntercompanyPo());
		}
		else {
			cOptional.add("");
		}

        if (reciptBean.getIntercompanyPoLine() != null) {
			cOptional.add(reciptBean.getIntercompanyPoLine());
		}
		else {
			cOptional.add("");
		}

        cOptional.add("");
        
        if(reciptBean.getDefinedShelfLifeItem() != null){
        	cOptional.add(reciptBean.getDefinedShelfLifeItem());
        }
        else {
        	cOptional.add("");
        }
        
        if(reciptBean.getOwnerSegmentId() !=null){
        	cOptional.add(reciptBean.getOwnerSegmentId());
        }
        else {
        	cOptional.add("");
        }
        
        if(reciptBean.getAccountNumber() !=null){
        	cOptional.add(reciptBean.getAccountNumber());
        }
        else {
        	cOptional.add("");
        }
        
        if(reciptBean.getAccountNumber() !=null){
        	cOptional.add(reciptBean.getAccountNumber2());
        }
        else {
        	cOptional.add("");
        }
        
        if(reciptBean.getAccountNumber() !=null){
        	cOptional.add(reciptBean.getAccountNumber3());
        }
        else {
        
        	cOptional.add("");
        }
        
        if(reciptBean.getAccountNumber() !=null){
        	cOptional.add(reciptBean.getAccountNumber4());
        }
        else {
        	cOptional.add("");
        }
        
        if(reciptBean.getCustomerReceiptId() !=null){
        	cOptional.add(reciptBean.getCustomerReceiptId());
        }
        else {
        	cOptional.add("");
        }
        
        if(reciptBean.getQualityTrackingNumber() !=null){
        	cOptional.add(reciptBean.getQualityTrackingNumber());
        }
        else {
        	cOptional.add("");
        }     

        cOptional.add("");
        cOptional.add("");
        cOptional.add("");
        cOptional.add("");
        cOptional.add("");
        
        if(receiptKitBean.getDateOfRepackaging()!=null){
        	cOptional.add(receiptKitBean.getDateOfRepackaging());
        }
        else {
        	cOptional.add("");
        }
        
        //if (log.isDebugEnabled()) {
			log.debug("P_RECEIPT_INSERT:"+cin+cout+cOptional);
		//}
		Collection result = this.getDbManager().doProcedure("P_RECEIPT_INSERT", cin,
				cout, cOptional);

		this.getDbManager().returnConnection(connection);
		return result;
	}

	//update call p_receipt_component
	public boolean insertReceiptComponenet(ReceivingKitBean bean,
			BigDecimal receiptId, BigDecimal personnelId) throws Exception {
		Connection connection = this.getDbManager().getConnection();
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

		if (bean.getExpirationDate() != null) {
			try {
				cin.add(new Timestamp(bean.getExpirationDate().getTime()));
			}
			catch (Exception ex) {
			}
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

		this.getDbManager().doProcedure("P_RECEIPT_COMPONENT", cin);

		this.getDbManager().returnConnection(connection);
		return true;
	}
	//call p_order_qty_update_from_rcpt
	public Collection closePoLine(ReceivingViewBean bean,BigDecimal personnelId) throws Exception {
		Connection connection = this.getDbManager().getConnection();
		Collection cin = new Vector();

		if (bean.getRadianPo() != null) {
			cin.add(bean.getRadianPo());
		}
		else {
			cin.add("");
		}

		if (bean.getLineItem() != null) {
			cin.add(bean.getLineItem());
		}
		else {
			cin.add("");
		}

		cin.add(personnelId);

		Collection cout = new Vector();
		cout.add(new Integer(java.sql.Types.VARCHAR));

		Collection result = this.getDbManager().doProcedure("P_ORDER_QTY_UPDATE_FROM_RCPT", cin,
				cout);

		this.getDbManager().returnConnection(connection);
		return result;
	}

	//update call p_receipt_component
	/*public boolean insertReceiptMv(ReceivingKitBean bean, BigDecimal receiptId,
	BigDecimal personnelId) throws Exception {
	Connection connection = this.getDbManager().getConnection();
	Collection cin = new Vector();

	cin.add(receiptId);

	if (bean.getPackagedQty() != null &&
	 bean.getPackagedQty().trim().length() > 0) {
	 cin.add(new String(bean.getPackagedQty()));
	}
	else {
	 cin.add("");
	}

	if (bean.getPackagedSize() != null &&
	 bean.getPackagedSize().trim().length() > 0) {
	 cin.add(new String(bean.getPackagedSize()));
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

		Connection connection = this.getDbManager().getConnection();
		Collection c = select(criteria, connection);
		this.getDbManager().returnConnection(connection);
		return c;
	}

	public Collection select(SearchCriteria criteria,
			Connection conn) throws BaseException {

		Collection receivingViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " + getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			ReceivingViewBean receivingViewBean = new ReceivingViewBean();
			load(dataSetRow, receivingViewBean);
			receivingViewBeanColl.add(receivingViewBean);
		}

		return receivingViewBeanColl;
	}

	public Collection selectSorted(SearchCriteria criteria,
			ReceivingInputBean bean,boolean iscollection,String searchWhat,String searchString) throws BaseException {

		//construct sort by clause
		String sortBy = "";
		if (bean.getSort().equalsIgnoreCase("Date Exptd/Supplier")) {
			sortBy = " order by " + ATTRIBUTE_RADIAN_PO + "," + ATTRIBUTE_LINE_ITEM+ "," + ATTRIBUTE_ORIGINAL_RECEIPT_ID+"," + ATTRIBUTE_EXPECTED + "," + ATTRIBUTE_LAST_SUPPLIER;
		}
		else if (bean.getSort().equalsIgnoreCase("PO/POLine")) {
			sortBy = " order by " + ATTRIBUTE_RADIAN_PO + "," + ATTRIBUTE_LINE_ITEM+ "," + ATTRIBUTE_ORIGINAL_RECEIPT_ID;
		}
		else if (bean.getSort().equalsIgnoreCase("Supplier/Date Exptd")) {
			sortBy = " order by " + ATTRIBUTE_RADIAN_PO + "," + ATTRIBUTE_LINE_ITEM+ "," + ATTRIBUTE_ORIGINAL_RECEIPT_ID+"," + ATTRIBUTE_LAST_SUPPLIER + "," + ATTRIBUTE_EXPECTED;
		}
		else if (bean.getSort().equalsIgnoreCase("Item Id/Date Exptd")) {
			sortBy = " order by " + ATTRIBUTE_RADIAN_PO + "," + ATTRIBUTE_LINE_ITEM+ "," + ATTRIBUTE_ORIGINAL_RECEIPT_ID+"," + ATTRIBUTE_ITEM_ID + "," + ATTRIBUTE_EXPECTED;
		}

		Collection receivingViewBeanColl = new Vector();
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
        if (searchWhat.equalsIgnoreCase("radianPo") && (searchString != null && searchString.trim().length() > 0))
        {
            if (whereClause.trim().length() > 0) {
				whereClause += "and ";
			}
            whereClause += " (" + ATTRIBUTE_INTERCOMPANY_PO + " = "+searchString+" or "+ATTRIBUTE_RADIAN_PO+" = "+searchString+") ";
        }
		String query = "select * from " + TABLE + " " + whereClause +
		sortBy;

		Connection connection = getDbManager().getConnection();
		DataSet dataSet = new SqlManager().select(connection, query);
		getDbManager().returnConnection(connection);

		Iterator dataIter = dataSet.iterator();
		ReceivingViewBean receivingViewBean = null;
		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			receivingViewBean = new ReceivingViewBean();
			load(dataSetRow, receivingViewBean);
			receivingViewBeanColl.add(receivingViewBean);
		}
		
		if(receivingViewBean != null) {
			ReceiptItemPriorBinViewBeanFactory factory = new ReceiptItemPriorBinViewBeanFactory(getDbManager());

			HashMap<String,Collection> map = new HashMap();
			for(ReceivingViewBean viewBean:(Collection<ReceivingViewBean>)receivingViewBeanColl) {
				map.put(viewBean.getItemId().intValue()+"%%%"+viewBean.getBranchPlant(), null);
			}

			for(String key:map.keySet()){
				String[] vals = key.split("%%%");
				SearchCriteria crit = new SearchCriteria();
				crit.addCriterion("itemId", SearchCriterion.EQUALS, "" + vals[0] + "");
				crit.addCriterion("hub", SearchCriterion.EQUALS, vals[1]);
				crit.addCriterion("status", SearchCriterion.EQUALS, "A");
				crit.addCriterion("onSite", SearchCriterion.EQUALS, "Y");
				SortCriteria sortCrit = new SortCriteria();
				sortCrit.addCriterion("transactionDate");
				sortCrit.setSortAscending(false);
				map.put(key,factory.selectCustomOrder(crit, sortCrit));
			}

			for(ReceivingViewBean viewBean:(Collection<ReceivingViewBean>)receivingViewBeanColl) {
				viewBean.setReceiptItemPriorBinViewBeanCollection( map.get(viewBean.getItemId().intValue()+"%%%"+viewBean.getBranchPlant()) );
				viewBean.setPackagedSize(com.tcmis.common.util.NumberHandler.emptyIfNull(
						viewBean.getReceivingPagePackagedSize()));
			}

		}

		return receivingViewBeanColl;
	}

	public Collection selectSortedReadonly(SearchCriteria criteria,
			ReceivingInputBean bean,boolean iscollection,String searchWhat,String searchString) throws BaseException {

		//construct sort by clause
		String sortBy = "";
		if (bean.getSort().equalsIgnoreCase("Date Exptd/Supplier")) {
			sortBy = " order by " + ATTRIBUTE_RADIAN_PO + "," + ATTRIBUTE_LINE_ITEM+ "," + ATTRIBUTE_ORIGINAL_RECEIPT_ID+"," + ATTRIBUTE_EXPECTED + "," + ATTRIBUTE_LAST_SUPPLIER;
		}
		else if (bean.getSort().equalsIgnoreCase("PO/POLine")) {
			sortBy = " order by " + ATTRIBUTE_RADIAN_PO + "," + ATTRIBUTE_LINE_ITEM+ "," + ATTRIBUTE_ORIGINAL_RECEIPT_ID+"," + ATTRIBUTE_RADIAN_PO + "," + ATTRIBUTE_LINE_ITEM+ "," + ATTRIBUTE_ORIGINAL_RECEIPT_ID;
		}
		else if (bean.getSort().equalsIgnoreCase("Supplier/Date Exptd")) {
			sortBy = " order by " + ATTRIBUTE_RADIAN_PO + "," + ATTRIBUTE_LINE_ITEM+ "," + ATTRIBUTE_ORIGINAL_RECEIPT_ID+"," + ATTRIBUTE_LAST_SUPPLIER + "," + ATTRIBUTE_EXPECTED;
		}
		else if (bean.getSort().equalsIgnoreCase("Item Id/Date Exptd")) {
			sortBy = " order by " + ATTRIBUTE_RADIAN_PO + "," + ATTRIBUTE_LINE_ITEM+ "," + ATTRIBUTE_ORIGINAL_RECEIPT_ID+"," + ATTRIBUTE_ITEM_ID + "," + ATTRIBUTE_EXPECTED;
		}

		Collection receivingViewBeanColl = new Vector();
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
        if (searchWhat.equalsIgnoreCase("radianPo") && (searchString != null && searchString.trim().length() > 0))
        {
            if (whereClause.trim().length() > 0) {
				whereClause += "and ";
			}
            
            whereClause += " (" + ATTRIBUTE_INTERCOMPANY_PO + " = "+searchString+" or "+ATTRIBUTE_RADIAN_PO+" = "+searchString+") ";
        }
        String query = "select * from " + TABLE + " " + whereClause +
		sortBy;

		Connection connection = getDbManager().getConnection();
		DataSet dataSet = new SqlManager().select(connection, query);
		getDbManager().returnConnection(connection);

		Iterator dataIter = dataSet.iterator();
		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			ReceivingViewBean receivingViewBean = new ReceivingViewBean();
			load(dataSetRow, receivingViewBean);
			receivingViewBeanColl.add(receivingViewBean);
		}

		return receivingViewBeanColl;
	}
}