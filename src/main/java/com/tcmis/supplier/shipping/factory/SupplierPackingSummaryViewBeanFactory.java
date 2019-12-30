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
import com.tcmis.supplier.shipping.beans.SupplierPackingSummaryViewBean;


/******************************************************************************
 * CLASSNAME: SupplierPackingSummaryViewBeanFactory <br>
 * @version: 1.0, Oct 30, 2007 <br>
 *****************************************************************************/


public class SupplierPackingSummaryViewBeanFactory extends BaseBeanFactory {

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
	public String ATTRIBUTE_QUANTITY = "QUANTITY";
	public String ATTRIBUTE_NUMBER_OF_BOXES = "NUMBER_OF_BOXES";
	public String ATTRIBUTE_PRIORITY_RATING = "PRIORITY_RATING";
  public String ATTRIBUTE_RECEIPT_ID = "RECEIPT_ID";
	public String ATTRIBUTE_SHIPPED_AS_SINGLE = "SHIPPED_AS_SINGLE";
	public String ATTRIBUTE_REQUIRES_OVERPACK = "REQUIRES_OVERPACK";
	public String ATTRIBUTE_OCONUS = "OCONUS";
  public String ATTRIBUTE_SUPPLIER_SALES_ORDER_NO = "SUPPLIER_SALES_ORDER_NO";
	public String ATTRIBUTE_RLI_SHIPTO_LOC_ID = "RLI_SHIPTO_LOC_ID";
	public String ATTRIBUTE_IN_OUTBOUND_ASN = "IN_OUTBOUND_ASN";
	public String ATTRIBUTE_TRACK_SERIAL_NUMBER = "TRACK_SERIAL_NUMBER";
	public String ATTRIBUTE_NUM_OF_SERIAL_NUMBER = "NUM_OF_SERIAL_NUMBER";
	public String ATTRIBUTE_NUM_OF_PALLET_ID = "NUM_OF_PALLET_ID";
  
  //table name
	public String TABLE = "SUPPLIER_PACKING_SUMMARY_VIEW";


	//constructor
	public SupplierPackingSummaryViewBeanFactory(DbManager dbManager) {
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
		else if(attributeName.equals("quantity")) {
			return ATTRIBUTE_QUANTITY;
		}
		else if(attributeName.equals("numberOfBoxes")) {
			return ATTRIBUTE_NUMBER_OF_BOXES;
		}
		else if(attributeName.equals("priorityRating")) {
			return ATTRIBUTE_PRIORITY_RATING;
		}
		else if(attributeName.equals("receiptId")) {
			return ATTRIBUTE_RECEIPT_ID;
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
		else if(attributeName.equals("inOutboundAsn")) {
			return ATTRIBUTE_IN_OUTBOUND_ASN;
		}
		else if(attributeName.equals("trackSerialNumber")) {
			return ATTRIBUTE_TRACK_SERIAL_NUMBER;
		}
		else if(attributeName.equals("numOfSerialNumber")) {
			return ATTRIBUTE_NUM_OF_SERIAL_NUMBER;
		}
		else if(attributeName.equals("numOfPalletId")) {
			return ATTRIBUTE_NUM_OF_PALLET_ID;
		}	
		
    else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, SupplierPackingSummaryViewBean.class);
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

		Collection supplierPackingSummaryViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria)+ getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			SupplierPackingSummaryViewBean supplierPackingSummaryViewBean = new SupplierPackingSummaryViewBean();
			load(dataSetRow, supplierPackingSummaryViewBean);
			supplierPackingSummaryViewBeanColl.add(supplierPackingSummaryViewBean);
		}

		return supplierPackingSummaryViewBeanColl;
	}
}