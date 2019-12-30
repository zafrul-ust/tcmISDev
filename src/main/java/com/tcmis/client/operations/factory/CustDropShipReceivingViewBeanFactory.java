package com.tcmis.client.operations.factory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.operations.beans.CustDropShipReceivingViewBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.internal.hub.beans.ReceivingKitBean;

/******************************************************************************
 * CLASSNAME: CustDropShipReceivingViewBeanFactory <br>
 * @version: 1.0, Sep 26, 2007 <br>
 *****************************************************************************/

public class CustDropShipReceivingViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_MR_NUMBER = "MR_NUMBER";
	public String ATTRIBUTE_MR_LINE_ITEM = "MR_LINE_ITEM";
	public String ATTRIBUTE_MR_QTY_OPEN = "MR_QTY_OPEN";
	public String ATTRIBUTE_REQUESTOR_NAME = "REQUESTOR_NAME";
	public String ATTRIBUTE_DELIVERY_POINT = "DELIVERY_POINT";
	public String ATTRIBUTE_SHIP_TO_LOCATION_ID = "SHIP_TO_LOCATION_ID";
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
	public String ATTRIBUTE_CUSTOMER_PO = "CUSTOMER_PO";
	public String ATTRIBUTE_ORDER_QTY_UPDATE_ON_RECEIPT = "ORDER_QTY_UPDATE_ON_RECEIPT";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_MANAGE_KITS_AS_SINGLE_UNIT = "MANAGE_KITS_AS_SINGLE_UNIT";
	public String ATTRIBUTE_COMPONENT_ID = "COMPONENT_ID";
	public String ATTRIBUTE_MATERIAL_ID = "MATERIAL_ID";
	public String ATTRIBUTE_PACKAGING = "PACKAGING";
	public String ATTRIBUTE_MATERIAL_DESC = "MATERIAL_DESC";
	public String ATTRIBUTE_INVENTORY_GROUP_NAME = "INVENTORY_GROUP_NAME";
	public String ATTRIBUTE_NUMBER_OF_KITS = "NUMBER_OF_KITS";
	public String ATTRIBUTE_PURCHASING_UNITS_PER_ITEM = "PURCHASING_UNITS_PER_ITEM";
	public String ATTRIBUTE_PURCHASING_UNIT_OF_MEASURE = "PURCHASING_UNIT_OF_MEASURE";
	public String ATTRIBUTE_MV_ITEM = "MV_ITEM";
	public String ATTRIBUTE_DISPLAY_PKG_STYLE = "DISPLAY_PKG_STYLE";
	public String ATTRIBUTE_NONINTEGER_RECEIVING = "NONINTEGER_RECEIVING";
	public String ATTRIBUTE_SHIP_TO_FACILITY_ID = "SHIP_TO_FACILITY_ID";
	public String ATTRIBUTE_ENABLE_LINK_TO_PO = "ENABLE_LINK_TO_PO";
	public String ATTRIBUTE_HUB_NAME = "HUB_NAME";
	public String ATTRIBUTE_OPS_COMPANY_ID = "OPS_COMPANY_ID";
	public String ATTRIBUTE_OPS_ENTITY_ID = "OPS_ENTITY_ID";
	public String ATTRIBUTE_OPERATING_ENTITY_NAME = "OPERATING_ENTITY_NAME";
	public String ATTRIBUTE_CUSTOMER_ID = "CUSTOMER_ID";
	public String ATTRIBUTE_CUSTOMER_NAME = "CUSTOMER_NAME";
	public String ATTRIBUTE_CSR_NAME = "CSR_NAME";

	//table name
	public String TABLE = "CUST_DROP_SHIP_RECEIVING_VIEW";

	//constructor
	public CustDropShipReceivingViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}

	//get column names
	@Override
	public String getColumnName(String attributeName) {
		if (attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		} else if (attributeName.equals("mrNumber")) {
			return ATTRIBUTE_MR_NUMBER;
		} else if (attributeName.equals("mrLineItem")) {
			return ATTRIBUTE_MR_LINE_ITEM;
		} else if (attributeName.equals("mrQtyOpen")) {
			return ATTRIBUTE_MR_QTY_OPEN;
		} else if (attributeName.equals("requestorName")) {
			return ATTRIBUTE_REQUESTOR_NAME;
		} else if (attributeName.equals("deliveryPoint")) {
			return ATTRIBUTE_DELIVERY_POINT;
		} else if (attributeName.equals("shipToLocationId")) {
			return ATTRIBUTE_SHIP_TO_LOCATION_ID;
		} else if (attributeName.equals("radianPo")) {
			return ATTRIBUTE_RADIAN_PO;
		} else if (attributeName.equals("lineItem")) {
			return ATTRIBUTE_LINE_ITEM;
		} else if (attributeName.equals("qtyOpen")) {
			return ATTRIBUTE_QTY_OPEN;
		} else if (attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		} else if (attributeName.equals("expected")) {
			return ATTRIBUTE_EXPECTED;
		} else if (attributeName.equals("qty")) {
			return ATTRIBUTE_QTY;
		} else if (attributeName.equals("branchPlant")) {
			return ATTRIBUTE_BRANCH_PLANT;
		} else if (attributeName.equals("itemDescription")) {
			return ATTRIBUTE_ITEM_DESCRIPTION;
		} else if (attributeName.equals("poNotes")) {
			return ATTRIBUTE_PO_NOTES;
		} else if (attributeName.equals("transType")) {
			return ATTRIBUTE_TRANS_TYPE;
		} else if (attributeName.equals("transferRequestId")) {
			return ATTRIBUTE_TRANSFER_REQUEST_ID;
		} else if (attributeName.equals("lastSupplier")) {
			return ATTRIBUTE_LAST_SUPPLIER;
		} else if (attributeName.equals("indefiniteShelfLife")) {
			return ATTRIBUTE_INDEFINITE_SHELF_LIFE;
		} else if (attributeName.equals("critical")) {
			return ATTRIBUTE_CRITICAL;
		} else if (attributeName.equals("customerPo")) {
			return ATTRIBUTE_CUSTOMER_PO;
		} else if (attributeName.equals("orderQtyUpdateOnReceipt")) {
			return ATTRIBUTE_ORDER_QTY_UPDATE_ON_RECEIPT;
		} else if (attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		} else if (attributeName.equals("manageKitsAsSingleUnit")) {
			return ATTRIBUTE_MANAGE_KITS_AS_SINGLE_UNIT;
		} else if (attributeName.equals("componentId")) {
			return ATTRIBUTE_COMPONENT_ID;
		} else if (attributeName.equals("materialId")) {
			return ATTRIBUTE_MATERIAL_ID;
		} else if (attributeName.equals("packaging")) {
			return ATTRIBUTE_PACKAGING;
		} else if (attributeName.equals("materialDesc")) {
			return ATTRIBUTE_MATERIAL_DESC;
		} else if (attributeName.equals("inventoryGroupName")) {
			return ATTRIBUTE_INVENTORY_GROUP_NAME;
		} else if (attributeName.equals("numberOfKits")) {
			return ATTRIBUTE_NUMBER_OF_KITS;
		} else if (attributeName.equals("purchasingUnitsPerItem")) {
			return ATTRIBUTE_PURCHASING_UNITS_PER_ITEM;
		} else if (attributeName.equals("purchasingUnitOfMeasure")) {
			return ATTRIBUTE_PURCHASING_UNIT_OF_MEASURE;
		} else if (attributeName.equals("mvItem")) {
			return ATTRIBUTE_MV_ITEM;
		} else if (attributeName.equals("displayPkgStyle")) {
			return ATTRIBUTE_DISPLAY_PKG_STYLE;
		} else if (attributeName.equals("nonintegerReceiving")) {
			return ATTRIBUTE_NONINTEGER_RECEIVING;
		} else if (attributeName.equals("shipToFacilityId")) {
			return ATTRIBUTE_SHIP_TO_FACILITY_ID;
		} else if (attributeName.equals("enableLinkToPo")) {
			return ATTRIBUTE_ENABLE_LINK_TO_PO;
		}
		else if(attributeName.equals("hubName")) {
			return ATTRIBUTE_HUB_NAME;
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
		else if(attributeName.equals("customerId")) {
			return ATTRIBUTE_CUSTOMER_ID;
		}
		else if(attributeName.equals("customerName")) {
			return ATTRIBUTE_CUSTOMER_NAME;
		}
		else if(attributeName.equals("csrName")) {
			return ATTRIBUTE_CSR_NAME;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}

	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, CustDropShipReceivingViewBean.class);
	}

	public int delete(SearchCriteria criteria) throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = delete(criteria, connection);
		} finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}

	public int delete(SearchCriteria criteria, Connection conn) throws BaseException {

		String sqlQuery = " delete from " + TABLE + " " + getWhereClause(criteria);

		return new SqlManager().update(conn, sqlQuery);
	}

	//update call p_receipt_insert
	public Collection insertReceipt(CustDropShipReceivingViewBean reciptBean, ReceivingKitBean receiptKitBean, BigDecimal personnelId, boolean nonChemicalreceipt, boolean internalDropShipReceipt) throws Exception {
		Connection connection = this.getDbManager().getConnection();
		Collection cin = new Vector();

		if (reciptBean.getTransType().equalsIgnoreCase("IT")) {
			cin.add(new BigDecimal("0"));
			cin.add(new BigDecimal("0"));
		} else {
			if (reciptBean.getRadianPo() != null) {
				cin.add(reciptBean.getRadianPo());
			} else {
				cin.add("");
			}

			/*if (nonChemicalreceipt) {
             if (reciptBean.getReceivePoLine() != null) {
              cin.add(reciptBean.getReceivePoLine());
             }
             else {
              cin.add("");
             }
                     }
                     else*/ {
                	     if (reciptBean.getLineItem() != null) {
                		     cin.add(reciptBean.getLineItem());
                	     } else {
                		     cin.add("");
                	     }
                     }
		}

		if (reciptBean.getItemId() != null) {
			cin.add(reciptBean.getItemId());
		} else {
			cin.add("");
		}

		if ("Y".equalsIgnoreCase(reciptBean.getMvItem())) {
			if (reciptBean.getMfgLot() != null && reciptBean.getMfgLot().trim().length() > 0) {
				cin.add(new String(reciptBean.getMfgLot().trim())); //4
			} else {
				cin.add(""); //4
			}
		} else if (receiptKitBean.getMfgLot() != null && receiptKitBean.getMfgLot().trim().length() > 0) {
			cin.add(new String(receiptKitBean.getMfgLot().trim())); //4
		} else {
			cin.add(""); //4
		}

		if (nonChemicalreceipt) {
			cin.add("NBO");
		} else {
			if ("Y".equalsIgnoreCase(reciptBean.getMvItem())) {
				if (reciptBean.getBin() != null && reciptBean.getBin().trim().length() > 0) {
					cin.add(reciptBean.getBin()); //5
				} else {
					cin.add(""); //5
				}
			} else if (receiptKitBean.getBin() != null && receiptKitBean.getBin().trim().length() > 0) {
				cin.add(receiptKitBean.getBin()); //5
			} else {
				cin.add(""); //5
			}
		}

		cin.add(""); //6

		Date dom = null;
		if ("Y".equalsIgnoreCase(reciptBean.getMvItem())) {
			dom = reciptBean.getDom();
		} else {
			dom = receiptKitBean.getDom();
		}
		if (dom != null) {
			try {
				cin.add(new Timestamp(dom.getTime())); //7
			} catch (Exception ex) {
			}
		} else {
			cin.add(""); //7
		}

		Date dos = null;
		if ("Y".equalsIgnoreCase(reciptBean.getMvItem())) {
			dos = reciptBean.getDos();
		} else {
			dos = receiptKitBean.getDos();
		}
		if (dos != null) {
			try {
				cin.add(new Timestamp(dos.getTime())); //8
			} catch (Exception ex) {
			}
		} else {
			cin.add(""); //8
		}

		if ("N".equalsIgnoreCase(reciptBean.getManageKitsAsSingleUnit())) {
			if (reciptBean.getQuantityReceived() != null) {
				cin.add(reciptBean.getQuantityReceived()); //9
			} else {
				cin.add(""); //9
			}
		} else if ("Y".equalsIgnoreCase(reciptBean.getMvItem())) {
			if (receiptKitBean.getPackagedQty() != null && receiptKitBean.getPackagedQty().trim().length() > 0) {
				cin.add(new BigDecimal(receiptKitBean.getPackagedQty()));
			} else {
				cin.add(""); //9
			}
		} else if (receiptKitBean.getQuantityReceived() != null) {
			cin.add(receiptKitBean.getQuantityReceived()); //9
		} else {
			cin.add(""); //9
		}

		if ("Y".equalsIgnoreCase(reciptBean.getMvItem())) {
			if (reciptBean.getLotStatus() != null && reciptBean.getLotStatus().trim().length() > 0) {
				cin.add(new String(reciptBean.getLotStatus())); //9
			} else {
				cin.add("Available"); //9
			}
		} else if (receiptKitBean.getLotStatus() != null && receiptKitBean.getLotStatus().trim().length() > 0) {
			cin.add(new String(receiptKitBean.getLotStatus())); //9
		} else if (nonChemicalreceipt) {
			cin.add("Available");
		} else {
			cin.add("Available"); //9
		}

		/*if (reciptBean.getTransferRequestId() != null) {
      cin.add(reciptBean.getTransferRequestId());
         } else {
      cin.add("");
         }*/

		Date dateOfReceipt = null;
		if ("Y".equalsIgnoreCase(reciptBean.getMvItem())) {
			dateOfReceipt = reciptBean.getDateOfReceipt();
		} else {
			dateOfReceipt = receiptKitBean.getDateOfReceipt();
		}
		if (dateOfReceipt != null) {
			try {
				cin.add(new Timestamp(dateOfReceipt.getTime())); //10
			} catch (Exception ex) {
			}
		} else {
			cin.add(""); //10
		}

		if (reciptBean.getBranchPlant() != null && reciptBean.getBranchPlant().trim().length() > 0) {
			cin.add(new String(reciptBean.getBranchPlant())); //11
		} else {
			cin.add(""); //11
		}

		cin.add("" + personnelId + ""); //12

		/*if (reciptBean.getTransType() != null && reciptBean.getTransType().trim().length() > 0) {
      cin.add(new String(reciptBean.getTransType())); //
         } else {
      cin.add(""); //
         }*/

		if (nonChemicalreceipt) {
			cin.add(new Timestamp(com.tcmis.common.util.DateHandler.getDateFromString("MM/dd/yyyy", "01/01/2020").getTime()));
		} else {
			Date expirationDate = null;
			if ("Y".equalsIgnoreCase(reciptBean.getMvItem())) {
				expirationDate = reciptBean.getExpirationDate();
			} else {
				expirationDate = receiptKitBean.getExpirationDate();
			}
			if (expirationDate != null) {
				try {
					cin.add(new Timestamp(expirationDate.getTime())); //13
				} catch (Exception ex) {
				}
			} else {
				cin.add(""); //13
			}
		}

		if (receiptKitBean.getTransferReceiptId() != null) {
			cin.add(receiptKitBean.getTransferReceiptId()); //14
		} else {
			cin.add(""); //14
		}

		Date supplierShipDate = null;
		if ("Y".equalsIgnoreCase(reciptBean.getMvItem())) {
			supplierShipDate = reciptBean.getSupplierShipDate();
		} else {
			supplierShipDate = receiptKitBean.getSupplierShipDate();
		}
		if (supplierShipDate != null) {
			try {
				cin.add(new Timestamp(supplierShipDate.getTime())); //15
			} catch (Exception ex) {
			}
		} else {
			cin.add(""); //15
		}

		if (reciptBean.getMrNumber() != null) {
			cin.add(reciptBean.getMrNumber()); //16
		} else {
			cin.add(""); //16
		}

		if (reciptBean.getMrLineItem() != null && reciptBean.getMrLineItem().trim().length() > 0) {
			cin.add(new String(reciptBean.getMrLineItem())); //17
		} else {
			cin.add(""); //17
		}

		cin.add(reciptBean.getBatch()); //18

		/*if (reciptBean.getInventoryGroup() != null && reciptBean.getInventoryGroup().trim().length() > 0) {
      cin.add(new String(reciptBean.getInventoryGroup())); //
         } else {
      cin.add(""); //
         }*/

		if ("Y".equalsIgnoreCase(reciptBean.getMvItem())) {
			if (reciptBean.getReceiptNotes() != null && reciptBean.getReceiptNotes().trim().length() > 0) {
				cin.add(new String(reciptBean.getReceiptNotes().trim())); //19
			} else {
				cin.add(""); //19
			}
		} else if (receiptKitBean.getReceiptNotes() != null && receiptKitBean.getReceiptNotes().trim().length() > 0) {
			cin.add(new String(receiptKitBean.getReceiptNotes().trim())); //19
		} else {
			cin.add(""); //19
		}

		if ("Y".equalsIgnoreCase(reciptBean.getMvItem())) {
			if (reciptBean.getDeliveryTicket() != null && reciptBean.getDeliveryTicket().trim().length() > 0) {
				cin.add(new String(reciptBean.getDeliveryTicket().trim())); //20
			} else {
				cin.add("");
			}
		} else if (receiptKitBean.getDeliveryTicket() != null && receiptKitBean.getDeliveryTicket().trim().length() > 0) {
			cin.add(new String(receiptKitBean.getDeliveryTicket().trim()));
		} else {
			cin.add("");
		}

		if ("Y".equalsIgnoreCase(reciptBean.getMvItem())) {
			if (reciptBean.getClosePoLine() != null && reciptBean.getClosePoLine().trim().length() > 0) {
				cin.add(new String(reciptBean.getClosePoLine().trim())); //21
			} else {
				cin.add("");
			}
		} else if (receiptKitBean.getClosePoLine() != null && receiptKitBean.getClosePoLine().trim().length() > 0) {
			cin.add(new String(receiptKitBean.getClosePoLine().trim()));
		} else {
			cin.add("");
		}

		if ("Y".equalsIgnoreCase(reciptBean.getMvItem())) {
			if (receiptKitBean.getPackagedSize() != null && receiptKitBean.getPackagedSize().trim().length() > 0) {
				cin.add(new BigDecimal(receiptKitBean.getPackagedSize())); //22
			} else {
				cin.add("");
			}
		} else {
			cin.add("");
		}

		if ("Y".equalsIgnoreCase(reciptBean.getMvItem())) {
			if (reciptBean.getReceivedReceipt1() != null) {
				cin.add(reciptBean.getReceivedReceipt1()); //23
			} else {
				cin.add("");
			}
		} else {
			cin.add("");
		}

		Collection cout = new Vector();
		cout.add(new Integer(java.sql.Types.NUMERIC));
		cout.add(new Integer(java.sql.Types.NUMERIC));
		cout.add(new Integer(java.sql.Types.NUMERIC));
		cout.add(new Integer(java.sql.Types.VARCHAR));

		Collection cOptionalIn = new Vector();
		if (reciptBean.getShipmentId() != null) {
			cOptionalIn.add(reciptBean.getShipmentId());
		}
		else
		{
			cOptionalIn.add("");
		}
		
		if (reciptBean.getCountryAbbrev() != null) {
			cOptionalIn.add(reciptBean.getCountryAbbrev());
		}
        else
        {
            cOptionalIn.add("");
        }

        if(internalDropShipReceipt)
            cOptionalIn.add("Y");
        else
            cOptionalIn.add("");

        log.info("P_CUSTOMER_RECEIVE_DROP_SHIP :" + cin + cout + cOptionalIn);
        Collection result = this.getDbManager().doProcedure("P_CUSTOMER_RECEIVE_DROP_SHIP", cin, cout,cOptionalIn);
		this.getDbManager().returnConnection(connection);
		return result;
	}

	//update call p_receipt_component
	public boolean insertReceiptComponenet(ReceivingKitBean bean, BigDecimal receiptId, BigDecimal personnelId) throws Exception {
		Connection connection = this.getDbManager().getConnection();
		Collection cin = new Vector();

		cin.add(receiptId);

		if (bean.getItemId() != null) {
			cin.add(bean.getItemId());
		} else {
			cin.add("");
		}

		if (bean.getComponentId() != null) {
			cin.add(bean.getComponentId());
		} else {
			cin.add("");
		}

		if (bean.getMfgLot() != null && bean.getMfgLot().trim().length() > 0) {
			cin.add(new String(bean.getMfgLot().trim()));
		} else {
			cin.add("");
		}

		if (bean.getBin() != null && bean.getBin().trim().length() > 0) {
			cin.add(bean.getBin());
		} else {
			cin.add("");
		}

		if (bean.getExpirationDate() != null) {
			try {
				cin.add(new Timestamp(bean.getExpirationDate().getTime()));
			} catch (Exception ex) {
			}
		} else {
			cin.add("");
		}

		this.getDbManager().doProcedure("P_CUSTOMER_RECEIPT_COMPONENT", cin);

		this.getDbManager().returnConnection(connection);
		return true;
	}

	//call p_order_qty_update_from_rcpt
	public Collection closePoLine(CustDropShipReceivingViewBean bean, BigDecimal personnelId) throws Exception {
		Connection connection = this.getDbManager().getConnection();
		Collection cin = new Vector();

		if (bean.getRadianPo() != null) {
			cin.add(bean.getRadianPo());
		} else {
			cin.add("");
		}

		if (bean.getLineItem() != null) {
			cin.add(bean.getLineItem());
		} else {
			cin.add("");
		}

		cin.add(personnelId);

		Collection cout = new Vector();
		cout.add(new Integer(java.sql.Types.VARCHAR));

		Collection result = this.getDbManager().doProcedure("P_CUST_PO_QTY_UPDATE_FROM_RCPT", cin, cout);

		this.getDbManager().returnConnection(connection);
		return result;
	}

	public Collection generateInvoice(BigDecimal shipmentId)  throws Exception {
		Connection connection = this.getDbManager().getConnection();
		Collection cin = new Vector();

		cin.add(shipmentId);

		Collection cout = new Vector();
		cout.add(new Integer(java.sql.Types.VARCHAR));
        Collection optArg = new Vector();
        optArg.add(null);
        optArg.add("Y");

        Collection result =this.getDbManager().doProcedure("P_INVOICE_SHIPMENT", cin,cout,optArg);
		this.getDbManager().returnConnection(connection);
		return result;
	}

	//select
	public Collection select(SearchCriteria criteria, String sortBy) throws BaseException {

		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = select(criteria, sortBy, connection);
		} finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}

	public Collection select(SearchCriteria criteria, String sortBy, Connection conn) throws BaseException {

		Collection custDropShipReceivingViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " + getWhereClause(criteria);
		if (sortBy != null && sortBy.length() > 0) {
			query += " order by " + sortBy;
		}
		if (log.isDebugEnabled()) {
			log.debug("GOT HERE: "+query);
		}
		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			CustDropShipReceivingViewBean custDropShipReceivingViewBean = new CustDropShipReceivingViewBean();
			load(dataSetRow, custDropShipReceivingViewBean);

			/*Do this if we allow dropship receiving logistics - Nawaz 09-26-07*/
			/*//get collection of ReceiptItemPriorViewBeans
                             SearchCriteria childCriteria = new SearchCriteria();
                             childCriteria.addCriterion("itemId", SearchCriterion.EQUALS,
        "" + custDropShipReceivingViewBean.getItemId().intValue() + "");
                             childCriteria.addCriterion("hub", SearchCriterion.EQUALS,
        new Integer(custDropShipReceivingViewBean.getBranchPlant()).toString());
                             childCriteria.addCriterion("status", SearchCriterion.EQUALS, "A");
                             childCriteria.addCriterion("onSite", SearchCriterion.EQUALS, "Y");
                             custDropShipReceivingViewBean.setReceiptItemPriorBinViewBeanCollection(factory.select(
        childCriteria));*/

			custDropShipReceivingViewBeanColl.add(custDropShipReceivingViewBean);
		}
		return custDropShipReceivingViewBeanColl;
	}

	//select
	public Collection selectReadOnly(SearchCriteria criteria, String sortBy) throws BaseException {

		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = selectReadOnly(criteria, sortBy, connection);
		} finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}

	public Collection selectReadOnly(SearchCriteria criteria, String sortBy, Connection conn) throws BaseException {

		Collection custDropShipReceivingViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " + getWhereClause(criteria);
		if (sortBy != null && sortBy.length() > 0) {
			query += " order by " + sortBy;
		}
		if (log.isDebugEnabled()) {
			log.debug("GOT HERE readonly: "+query);
		}
		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			CustDropShipReceivingViewBean custDropShipReceivingViewBean = new CustDropShipReceivingViewBean();
			load(dataSetRow, custDropShipReceivingViewBean);
			custDropShipReceivingViewBeanColl.add(custDropShipReceivingViewBean);
		}

		return custDropShipReceivingViewBeanColl;
	}

}