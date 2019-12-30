package com.tcmis.supplier.shipping.factory;


import java.math.BigDecimal;
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
import com.tcmis.common.util.SortCriteria;
import com.tcmis.supplier.shipping.beans.SupplierPackingViewBean;

/******************************************************************************
 * CLASSNAME: SupplierPackingViewBeanFactory <br>
 * @version: 1.0, Nov 21, 2007 <br>
 *****************************************************************************/


public class SupplierPackingViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_SUPPLIER = "SUPPLIER";
	public String ATTRIBUTE_SHIP_FROM_LOCATION_ID = "SHIP_FROM_LOCATION_ID";
	public String ATTRIBUTE_SHIP_FROM_LOCATION_NAME = "SHIP_FROM_LOCATION_NAME";
	public String ATTRIBUTE_SHIP_TO_LOCATION_NAME = "SHIP_TO_LOCATION_NAME";
	public String ATTRIBUTE_SHIP_TO_CITY_COMMA_STATE = "SHIP_TO_CITY_COMMA_STATE";
	public String ATTRIBUTE_SHIP_TO_ZIPCODE = "SHIP_TO_ZIPCODE";
	public String ATTRIBUTE_PICKLIST_ID = "PICKLIST_ID";
	public String ATTRIBUTE_PICKLIST_PRINT_DATE = "PICKLIST_PRINT_DATE";
	public String ATTRIBUTE_SHIP_TO_LOCATION_ID = "SHIP_TO_LOCATION_ID";
	public String ATTRIBUTE_ULTIMATE_SHIP_TO_DODAAC = "ULTIMATE_SHIP_TO_DODAAC";
	public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
	public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
	public String ATTRIBUTE_RADIAN_PO = "RADIAN_PO";
	public String ATTRIBUTE_PO_LINE = "PO_LINE";
	public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
	public String ATTRIBUTE_PART_SHORT_NAME = "PART_SHORT_NAME";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_ITEM_DESC = "ITEM_DESC";
	public String ATTRIBUTE_PACKAGING = "PACKAGING";
	public String ATTRIBUTE_BOX_LABEL_ID = "BOX_LABEL_ID";
	public String ATTRIBUTE_QUANTITY = "QUANTITY";
	public String ATTRIBUTE_NUMBER_OF_BOXES = "NUMBER_OF_BOXES";
	public String ATTRIBUTE_EXPIRE_DATE = "EXPIRE_DATE";
	public String ATTRIBUTE_ISSUE_ID = "ISSUE_ID";
	public String ATTRIBUTE_BOX_ID = "BOX_ID";
	public String ATTRIBUTE_PALLET_ID = "PALLET_ID";
	public String ATTRIBUTE_SHIPMENT_ID = "SHIPMENT_ID";
	public String ATTRIBUTE_TRACKING_NUMBER = "TRACKING_NUMBER";
	public String ATTRIBUTE_CARRIER_CODE = "CARRIER_CODE";
	public String ATTRIBUTE_CARRIER_NAME = "CARRIER_NAME";
	public String ATTRIBUTE_DELIVERY_TICKET = "DELIVERY_TICKET";
	public String ATTRIBUTE_DATE_DELIVERED = "DATE_DELIVERED";
	public String ATTRIBUTE_DATE_SHIPPED = "DATE_SHIPPED";
	public String ATTRIBUTE_DESIRED_SHIP_DATE = "DESIRED_SHIP_DATE";
	public String ATTRIBUTE_DESIRED_DELIVERY_DATE = "DESIRED_DELIVERY_DATE";
	public String ATTRIBUTE_SHIPPED_AS_SINGLE = "SHIPPED_AS_SINGLE";
	public String ATTRIBUTE_REQUIRES_OVERPACK = "REQUIRES_OVERPACK";
	public String ATTRIBUTE_OCONUS = "OCONUS";
  public String ATTRIBUTE_SUPPLIER_SALES_ORDER_NO = "SUPPLIER_SALES_ORDER_NO";
	public String ATTRIBUTE_RLI_SHIPTO_LOC_ID = "RLI_SHIPTO_LOC_ID";
  public String ATTRIBUTE_SHIP_CONFIRM_DATE = "SHIP_CONFIRM_DATE";
  public String ATTRIBUTE_PACKING_GROUP_ID = "PACKING_GROUP_ID";
  public String ATTRIBUTE_MILSTRIP_CODE = "MILSTRIP_CODE";
  public String ATTRIBUTE_IN_OUTBOUND_ASN = "IN_OUTBOUND_ASN";
  public String ATTRIBUTE_ORIGIN_INSPECTION_REQUIRED = "ORIGIN_INSPECTION_REQUIRED";
  public String ATTRIBUTE_USGOV_TCN = "USGOV_TCN";
  public String ATTRIBUTE_TRACK__SERIAL_NUMBER = "TRACK_SERIAL_NUMBER";
  public String ATTRIBUTE_SERIAL_NUMBER = "SERIAL_NUMBER";
  public String ATTRIBUTE_CUSTOMER_ORDER_QTY = "CUSTOMER_ORDER_QTY";
  public String ATTRIBUTE_CUSTOMER_PO_NO = "CUSTOMER_PO_NO";
  public String ATTRIBUTE_MFG_DATE_REQUIRED = "MFG_DATE_REQUIRED";
  public String ATTRIBUTE_SHIPTO_ADDRESS = "SHIPTO_ADDRESS";
  public String ATTRIBUTE_MR_COMPANY_ID = "MR_COMPANY_ID";

  //table name
	public String TABLE = "SUPPLIER_PACKING_VIEW";


	//constructor
	public SupplierPackingViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("supplier")) {
			return ATTRIBUTE_SUPPLIER;
		}
		else if(attributeName.equals("shipFromLocationId")) {
			return ATTRIBUTE_SHIP_FROM_LOCATION_ID;
		}
		else if(attributeName.equals("shipFromLocationName")) {
			return ATTRIBUTE_SHIP_FROM_LOCATION_NAME;
		}
		else if(attributeName.equals("shipToLocationName")) {
			return ATTRIBUTE_SHIP_TO_LOCATION_NAME;
		}
		else if(attributeName.equals("shipToCityCommaState")) {
			return ATTRIBUTE_SHIP_TO_CITY_COMMA_STATE;
		}
		else if(attributeName.equals("shipToZipcode")) {
			return ATTRIBUTE_SHIP_TO_ZIPCODE;
		}
		else if(attributeName.equals("picklistId")) {
			return ATTRIBUTE_PICKLIST_ID;
		}
		else if(attributeName.equals("picklistPrintDate")) {
			return ATTRIBUTE_PICKLIST_PRINT_DATE;
		}
		else if(attributeName.equals("shipToLocationId")) {
			return ATTRIBUTE_SHIP_TO_LOCATION_ID;
		}
		else if(attributeName.equals("ultimateShipToDodaac")) {
			return ATTRIBUTE_ULTIMATE_SHIP_TO_DODAAC;
		}
		else if(attributeName.equals("prNumber")) {
			return ATTRIBUTE_PR_NUMBER;
		}
		else if(attributeName.equals("lineItem")) {
			return ATTRIBUTE_LINE_ITEM;
		}
		else if(attributeName.equals("radianPo")) {
			return ATTRIBUTE_RADIAN_PO;
		}
		else if(attributeName.equals("poLine")) {
			return ATTRIBUTE_PO_LINE;
		}
		else if(attributeName.equals("catPartNo")) {
			return ATTRIBUTE_CAT_PART_NO;
		}
		else if(attributeName.equals("partShortName")) {
			return ATTRIBUTE_PART_SHORT_NAME;
		}
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("itemDesc")) {
			return ATTRIBUTE_ITEM_DESC;
		}
		else if(attributeName.equals("packaging")) {
			return ATTRIBUTE_PACKAGING;
		}
		else if(attributeName.equals("boxLabelId")) {
			return ATTRIBUTE_BOX_LABEL_ID;
		}
		else if(attributeName.equals("quantity")) {
			return ATTRIBUTE_QUANTITY;
		}
		else if(attributeName.equals("numberOfBoxes")) {
			return ATTRIBUTE_NUMBER_OF_BOXES;
		}
		else if(attributeName.equals("expireDate")) {
			return ATTRIBUTE_EXPIRE_DATE;
		}
		else if(attributeName.equals("issueId")) {
			return ATTRIBUTE_ISSUE_ID;
		}
		else if(attributeName.equals("boxId")) {
			return ATTRIBUTE_BOX_ID;
		}
		else if(attributeName.equals("palletId")) {
			return ATTRIBUTE_PALLET_ID;
		}
		else if(attributeName.equals("shipmentId")) {
			return ATTRIBUTE_SHIPMENT_ID;
		}
		else if(attributeName.equals("trackingNumber")) {
			return ATTRIBUTE_TRACKING_NUMBER;
		}
		else if(attributeName.equals("carrierCode")) {
			return ATTRIBUTE_CARRIER_CODE;
		}
		else if(attributeName.equals("carrierName")) {
			return ATTRIBUTE_CARRIER_NAME;
		}
		else if(attributeName.equals("deliveryTicket")) {
			return ATTRIBUTE_DELIVERY_TICKET;
		}
		else if(attributeName.equals("dateDelivered")) {
			return ATTRIBUTE_DATE_DELIVERED;
		}
		else if(attributeName.equals("dateShipped")) {
			return ATTRIBUTE_DATE_SHIPPED;
		}
		else if(attributeName.equals("desiredShipDate")) {
			return ATTRIBUTE_DESIRED_SHIP_DATE;
		}		
		else if(attributeName.equals("desiredDeliveryDate")) {
			return ATTRIBUTE_DESIRED_DELIVERY_DATE;
		}
		else if(attributeName.equals("shippedAsSingle")) {
			return ATTRIBUTE_SHIPPED_AS_SINGLE;
		}
		else if(attributeName.equals("requiresOverpack")) {
			return ATTRIBUTE_REQUIRES_OVERPACK;
		}
		else if(attributeName.equals("oconus")) {
			return ATTRIBUTE_OCONUS;
		}
		else if(attributeName.equals("supplierSalesOrderNo")) {
			return ATTRIBUTE_SUPPLIER_SALES_ORDER_NO;
		}
		else if(attributeName.equals("rliShiptoLocId")) {
			return ATTRIBUTE_RLI_SHIPTO_LOC_ID;
		}
		else if(attributeName.equals("shipConfirmDate")) {
			return ATTRIBUTE_SHIP_CONFIRM_DATE;
		}   
		else if(attributeName.equals("packingGroupId")) {
			return ATTRIBUTE_PACKING_GROUP_ID;
		}    
		else if(attributeName.equals("milstripCode")) {
			return ATTRIBUTE_MILSTRIP_CODE;
		}    
		else if(attributeName.equals("inOutboundAsn")) {
			return ATTRIBUTE_IN_OUTBOUND_ASN;
		}    
		else if(attributeName.equals("originInspectionRequired")) {
			return ATTRIBUTE_ORIGIN_INSPECTION_REQUIRED;
		}
		else if(attributeName.equals("usgovTcn")) {
			return ATTRIBUTE_USGOV_TCN;
		}
		else if(attributeName.equals("serialNumber")) {
			return ATTRIBUTE_SERIAL_NUMBER;
		}		
		else if(attributeName.equals("trackSerialNumber")) {
			return ATTRIBUTE_TRACK__SERIAL_NUMBER;
		}		
		else if(attributeName.equals("customerPoNo")) {
			return ATTRIBUTE_CUSTOMER_PO_NO;
		}
		else if(attributeName.equals("customerOrderQty")) {
			return ATTRIBUTE_CUSTOMER_ORDER_QTY;
		}
		else if(attributeName.equals("mfgDateRequired")) {
			return ATTRIBUTE_MFG_DATE_REQUIRED;
		}
		else if(attributeName.equals("shiptoAddress")) {
			return ATTRIBUTE_SHIPTO_ADDRESS;
		}
		else if(attributeName.equals("mrCompanyId")) {
			return ATTRIBUTE_MR_COMPANY_ID;
		}
    else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, SupplierPackingViewBean.class);
	}

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

		Collection supplierPackingViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria)+ getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			SupplierPackingViewBean supplierPackingViewBean = new SupplierPackingViewBean();
			load(dataSetRow, supplierPackingViewBean);
			supplierPackingViewBeanColl.add(supplierPackingViewBean);
		}

		return supplierPackingViewBeanColl;
	}
}