package com.tcmis.supplier.shipping.factory;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.sql.Connection;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.util.*;
import com.tcmis.common.db.DbManager;
import com.tcmis.supplier.shipping.beans.SupplierShippingViewBean;


/******************************************************************************
 * CLASSNAME: SupplierShippingViewBeanFactory <br>
 * @version: 1.0, Oct 30, 2007 <br>
 *****************************************************************************/


public class SupplierShippingViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_SUPPLIER_PARENT = "SUPPLIER_PARENT";
	public String ATTRIBUTE_SUPPLIER = "SUPPLIER";
	public String ATTRIBUTE_SHIP_FROM_LOCATION_ID = "SHIP_FROM_LOCATION_ID";
	public String ATTRIBUTE_SHIP_FROM_LOCATION_NAME = "SHIP_FROM_LOCATION_NAME";
	public String ATTRIBUTE_SHIP_TO_COMPANY_ID = "SHIP_TO_COMPANY_ID";
	public String ATTRIBUTE_SHIP_TO_LOCATION_ID = "SHIP_TO_LOCATION_ID";
	public String ATTRIBUTE_MR_NUMBER = "MR_NUMBER";
	public String ATTRIBUTE_MR_LINE_ITEM = "MR_LINE_ITEM";
	public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
	public String ATTRIBUTE_REQUESTOR_NAME = "REQUESTOR_NAME";
	public String ATTRIBUTE_APPLICATION = "APPLICATION";
	public String ATTRIBUTE_DELIVERY_POINT = "DELIVERY_POINT";
	public String ATTRIBUTE_CUSTOMER_PO = "CUSTOMER_PO";
	public String ATTRIBUTE_RADIAN_PO = "RADIAN_PO";
	public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
	public String ATTRIBUTE_QTY_OPEN = "QTY_OPEN";
	public String ATTRIBUTE_QTY_DELIVERED = "QTY_DELIVERED";
	public String ATTRIBUTE_DATE_DELIVERED = "DATE_DELIVERED";
	public String ATTRIBUTE_MFG_LOT = "MFG_LOT";
	public String ATTRIBUTE_VENDOR_SHIP_DATE = "VENDOR_SHIP_DATE";
	public String ATTRIBUTE_EXPIRE_DATE = "EXPIRE_DATE";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_EXPECTED = "EXPECTED";
	public String ATTRIBUTE_QTY = "QTY";
	public String ATTRIBUTE_BRANCH_PLANT = "BRANCH_PLANT";
	public String ATTRIBUTE_ITEM_DESCRIPTION = "ITEM_DESCRIPTION";
	public String ATTRIBUTE_PO_NOTES = "PO_NOTES";
	public String ATTRIBUTE_TRANS_TYPE = "TRANS_TYPE";
	public String ATTRIBUTE_SUPPLIER_NAME = "SUPPLIER_NAME";
	public String ATTRIBUTE_INDEFINITE_SHELF_LIFE = "INDEFINITE_SHELF_LIFE";
	public String ATTRIBUTE_CRITICAL = "CRITICAL";
	public String ATTRIBUTE_ORIGINAL_MFG_LOT = "ORIGINAL_MFG_LOT";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_MANAGE_KITS_AS_SINGLE_UNIT = "MANAGE_KITS_AS_SINGLE_UNIT";
	public String ATTRIBUTE_COMPONENT_ID = "COMPONENT_ID";
	public String ATTRIBUTE_SHELF_LIFE_DAYS = "SHELF_LIFE_DAYS";
	public String ATTRIBUTE_SHELF_LIFE_BASIS = "SHELF_LIFE_BASIS";
	public String ATTRIBUTE_MATERIAL_ID = "MATERIAL_ID";
	public String ATTRIBUTE_PACKAGING = "PACKAGING";
	public String ATTRIBUTE_MATERIAL_DESC = "MATERIAL_DESC";
	public String ATTRIBUTE_NUMBER_OF_KITS = "NUMBER_OF_KITS";
	public String ATTRIBUTE_LOCATION_SHORT_NAME = "LOCATION_SHORT_NAME";
	public String ATTRIBUTE_LOCATION_DESC = "LOCATION_DESC";
	public String ATTRIBUTE_ADDRESS_LINE_1 = "ADDRESS_LINE_1";
	public String ATTRIBUTE_ADDRESS_LINE_2 = "ADDRESS_LINE_2";
	public String ATTRIBUTE_ADDRESS_LINE_3 = "ADDRESS_LINE_3";
	public String ATTRIBUTE_CITY = "CITY";
	public String ATTRIBUTE_STATE_ABBREV = "STATE_ABBREV";
	public String ATTRIBUTE_ZIP = "ZIP";
	public String ATTRIBUTE_CITY_COMMA_STATE = "CITY_COMMA_STATE";
	public String ATTRIBUTE_CONTAINER_LABEL = "CONTAINER_LABEL";
	public String ATTRIBUTE_DROP_SHIP = "DROP_SHIP";
	public String ATTRIBUTE_PO_LINE = "PO_LINE";
	public String ATTRIBUTE_PARTS_PER_BOX = "PARTS_PER_BOX";
	public String ATTRIBUTE_REQUIRES_OVERPACK = "REQUIRES_OVERPACK";
	public String ATTRIBUTE_PRIORITY_RATING = "PRIORITY_RATING";
	public String ATTRIBUTE_DESIRED_SHIP_DATE = "DESIRED_SHIP_DATE";
	public String ATTRIBUTE_DESIRED_DELIVERY_DATE = "DESIRED_DELIVERY_DATE";
	public String ATTRIBUTE_SHIPPED_AS_SINGLE = "SHIPPED_AS_SINGLE";
  public String ATTRIBUTE_OCONUS = "OCONUS";
	public String ATTRIBUTE_SUPPLIER_SALES_ORDER_NO = "SUPPLIER_SALES_ORDER_NO";
	public String ATTRIBUTE_UNIT_PRICE = "UNIT_PRICE";
	public String ATTRIBUTE_CURRENCY_ID = "CURRENCY_ID";
	public String ATTRIBUTE_RLI_SHIPTO_LOC_ID = "RLI_SHIPTO_LOC_ID";
	public String ATTRIBUTE_ORIGINAL_TRANSACTION_TYPE = "ORIGINAL_TRANSACTION_TYPE";
	public String ATTRIBUTE_ORIGIN_INSPECTION_REQUIRED = "ORIGIN_INSPECTION_REQUIRED";
	public String ATTRIBUTE_MFG_DATE_REQUIRED = "MFG_DATE_REQUIRED";
	public String ATTRIBUTE_TRACK_SERIAL_NUMBER = "TRACK_SERIAL_NUMBER";
	public String ATTRIBUTE_AVAILABLE_INVENTORY_QTY = "AVAILABLE_INVENTORY_QTY";
  
  //table name
	public String TABLE = "SUPPLIER_SHIPPING_VIEW";


	//constructor
	public SupplierShippingViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("supplierParent")) {
			return ATTRIBUTE_SUPPLIER_PARENT;
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
		else if(attributeName.equals("shipToCompanyId")) {
			return ATTRIBUTE_SHIP_TO_COMPANY_ID;
		}
		else if(attributeName.equals("shipToLocationId")) {
			return ATTRIBUTE_SHIP_TO_LOCATION_ID;
		}
		else if(attributeName.equals("mrNumber")) {
			return ATTRIBUTE_MR_NUMBER;
		}
		else if(attributeName.equals("mrLineItem")) {
			return ATTRIBUTE_MR_LINE_ITEM;
		}
		else if(attributeName.equals("catPartNo")) {
			return ATTRIBUTE_CAT_PART_NO;
		}
		else if(attributeName.equals("requestorName")) {
			return ATTRIBUTE_REQUESTOR_NAME;
		}
		else if(attributeName.equals("application")) {
			return ATTRIBUTE_APPLICATION;
		}
		else if(attributeName.equals("deliveryPoint")) {
			return ATTRIBUTE_DELIVERY_POINT;
		}
		else if(attributeName.equals("customerPo")) {
			return ATTRIBUTE_CUSTOMER_PO;
		}
		else if(attributeName.equals("radianPo")) {
			return ATTRIBUTE_RADIAN_PO;
		}
		else if(attributeName.equals("lineItem")) {
			return ATTRIBUTE_LINE_ITEM;
		}
		else if(attributeName.equals("qtyOpen")) {
			return ATTRIBUTE_QTY_OPEN;
		}
		else if(attributeName.equals("qtyDelivered")) {
			return ATTRIBUTE_QTY_DELIVERED;
		}
		else if(attributeName.equals("dateDelivered")) {
			return ATTRIBUTE_DATE_DELIVERED;
		}
		else if(attributeName.equals("mfgLot")) {
			return ATTRIBUTE_MFG_LOT;
		}
		else if(attributeName.equals("vendorShipDate")) {
			return ATTRIBUTE_VENDOR_SHIP_DATE;
		}
		else if(attributeName.equals("expireDate")) {
			return ATTRIBUTE_EXPIRE_DATE;
		}
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("expected")) {
			return ATTRIBUTE_EXPECTED;
		}
		else if(attributeName.equals("qty")) {
			return ATTRIBUTE_QTY;
		}
		else if(attributeName.equals("branchPlant")) {
			return ATTRIBUTE_BRANCH_PLANT;
		}
		else if(attributeName.equals("itemDescription")) {
			return ATTRIBUTE_ITEM_DESCRIPTION;
		}
		else if(attributeName.equals("poNotes")) {
			return ATTRIBUTE_PO_NOTES;
		}
		else if(attributeName.equals("transType")) {
			return ATTRIBUTE_TRANS_TYPE;
		}
		else if(attributeName.equals("supplierName")) {
			return ATTRIBUTE_SUPPLIER_NAME;
		}
		else if(attributeName.equals("indefiniteShelfLife")) {
			return ATTRIBUTE_INDEFINITE_SHELF_LIFE;
		}
		else if(attributeName.equals("critical")) {
			return ATTRIBUTE_CRITICAL;
		}
		else if(attributeName.equals("originalMfgLot")) {
			return ATTRIBUTE_ORIGINAL_MFG_LOT;
		}
		else if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("manageKitsAsSingleUnit")) {
			return ATTRIBUTE_MANAGE_KITS_AS_SINGLE_UNIT;
		}
		else if(attributeName.equals("componentId")) {
			return ATTRIBUTE_COMPONENT_ID;
		}
		else if(attributeName.equals("shelfLifeDays")) {
			return ATTRIBUTE_SHELF_LIFE_DAYS;
		}
		else if(attributeName.equals("shelfLifeBasis")) {
			return ATTRIBUTE_SHELF_LIFE_BASIS;
		}
		else if(attributeName.equals("materialId")) {
			return ATTRIBUTE_MATERIAL_ID;
		}
		else if(attributeName.equals("packaging")) {
			return ATTRIBUTE_PACKAGING;
		}
		else if(attributeName.equals("materialDesc")) {
			return ATTRIBUTE_MATERIAL_DESC;
		}
		else if(attributeName.equals("numberOfKits")) {
			return ATTRIBUTE_NUMBER_OF_KITS;
		}
		else if(attributeName.equals("locationShortName")) {
			return ATTRIBUTE_LOCATION_SHORT_NAME;
		}
		else if(attributeName.equals("locationDesc")) {
			return ATTRIBUTE_LOCATION_DESC;
		}
		else if(attributeName.equals("addressLine1")) {
			return ATTRIBUTE_ADDRESS_LINE_1;
		}
		else if(attributeName.equals("addressLine2")) {
			return ATTRIBUTE_ADDRESS_LINE_2;
		}
		else if(attributeName.equals("addressLine3")) {
			return ATTRIBUTE_ADDRESS_LINE_3;
		}
		else if(attributeName.equals("city")) {
			return ATTRIBUTE_CITY;
		}
		else if(attributeName.equals("stateAbbrev")) {
			return ATTRIBUTE_STATE_ABBREV;
		}
		else if(attributeName.equals("zip")) {
			return ATTRIBUTE_ZIP;
		}
		else if(attributeName.equals("cityCommaState")) {
			return ATTRIBUTE_CITY_COMMA_STATE;
		}
		else if(attributeName.equals("containerLabel")) {
			return ATTRIBUTE_CONTAINER_LABEL;
		}
		else if(attributeName.equals("dropShip")) {
			return ATTRIBUTE_DROP_SHIP;
		}
		else if(attributeName.equals("poLine")) {
			return ATTRIBUTE_PO_LINE;
		}
		else if(attributeName.equals("partsPerBox")) {
			return ATTRIBUTE_PARTS_PER_BOX;
		}
		else if(attributeName.equals("requiresOverpack")) {
			return ATTRIBUTE_REQUIRES_OVERPACK;
		}
		else if(attributeName.equals("priorityRating")) {
			return ATTRIBUTE_PRIORITY_RATING;
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
		else if(attributeName.equals("oconus")) {
			return ATTRIBUTE_OCONUS;
		}
		else if (attributeName.equals("supplierSalesOrderNo")) {
			return ATTRIBUTE_SUPPLIER_SALES_ORDER_NO;
		}
		else if(attributeName.equals("unitPrice")) {
			return ATTRIBUTE_UNIT_PRICE;
		}
		else if(attributeName.equals("currencyId")) {
			return ATTRIBUTE_CURRENCY_ID;
		}
		else if(attributeName.equals("rliShiptoLocId")) {
			return ATTRIBUTE_RLI_SHIPTO_LOC_ID;
		}   
		else if(attributeName.equals("originalTransactionType")) {
			return ATTRIBUTE_ORIGINAL_TRANSACTION_TYPE;
		}   
		else if(attributeName.equals("originInspectionRequired")) {
			return ATTRIBUTE_ORIGIN_INSPECTION_REQUIRED;
		}
		else if(attributeName.equals("originInspectionRequired")) {
			return ATTRIBUTE_ORIGIN_INSPECTION_REQUIRED;
		}
		
		else if(attributeName.equals("originInspectionRequired")) {
			return ATTRIBUTE_ORIGIN_INSPECTION_REQUIRED;
		}  
		else if(attributeName.equals("mfgDateRequired")) {
			return ATTRIBUTE_MFG_DATE_REQUIRED;
		}
		else if(attributeName.equals("trackSerialNumber")) {
			return ATTRIBUTE_TRACK_SERIAL_NUMBER;
		}
		else if (attributeName.equals("availableInventoryQty")) {
			return ATTRIBUTE_AVAILABLE_INVENTORY_QTY;
		}
		
    else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, SupplierShippingViewBean.class);
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

		Collection supplierShippingViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria)+ getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			SupplierShippingViewBean supplierShippingViewBean = new SupplierShippingViewBean();
			load(dataSetRow, supplierShippingViewBean);
			supplierShippingViewBeanColl.add(supplierShippingViewBean);
		}

		return supplierShippingViewBeanColl;
	}
}