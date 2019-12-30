package com.tcmis.supplier.shipping.factory;


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
import com.tcmis.supplier.shipping.beans.UsgovDd250ViewBean;


/******************************************************************************
 * CLASSNAME: UsgovDd250ViewBeanFactory <br>
 * @version: 1.0, Dec 1, 2007 <br>
 *****************************************************************************/


public class UsgovDd250ViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
    public String ATTRIBUTE_PACKING_GROUP_ID = "PACKING_GROUP_ID";
    public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
	public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
	public String ATTRIBUTE_ITEM_NO = "ITEM_NO";
	public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
	public String ATTRIBUTE_PART_SHORT_NAME = "PART_SHORT_NAME";
	public String ATTRIBUTE_SHIPPING_CONTAINERS = "SHIPPING_CONTAINERS";
	public String ATTRIBUTE_QUANTITY = "QUANTITY";
	public String ATTRIBUTE_UNIT_OF_SALE = "UNIT_OF_SALE";
	public String ATTRIBUTE_UNIT_PRICE = "UNIT_PRICE";
	public String ATTRIBUTE_AMOUNT = "AMOUNT";
	public String ATTRIBUTE_ACCEPTANCE_POINT = "ACCEPTANCE_POINT";
	public String ATTRIBUTE_SHIP_DATE = "SHIP_DATE";
	public String ATTRIBUTE_SHIPMENT_NUMBER = "SHIPMENT_NUMBER";
	public String ATTRIBUTE_CARRIER_NAME = "CARRIER_NAME";
	public String ATTRIBUTE_TRACKING_NUMBER = "TRACKING_NUMBER";
	public String ATTRIBUTE_TCN = "TCN";
	public String ATTRIBUTE_PRIME_CONTRACTOR = "PRIME_CONTRACTOR";
	public String ATTRIBUTE_PRIME_CONTRACTOR_DODAAC = "PRIME_CONTRACTOR_DODAAC";
	public String ATTRIBUTE_PRIME_CONTRACTOR_LINE1 = "PRIME_CONTRACTOR_LINE1";
	public String ATTRIBUTE_PRIME_CONTRACTOR_LINE2 = "PRIME_CONTRACTOR_LINE2";
	public String ATTRIBUTE_PRIME_CONTRACTOR_LINE3 = "PRIME_CONTRACTOR_LINE3";
	public String ATTRIBUTE_CONTRACT_NUMBER = "CONTRACT_NUMBER";
	public String ATTRIBUTE_ORDER_NUMBER = "ORDER_NUMBER";
	public String ATTRIBUTE_PAGE = "PAGE";
	public String ATTRIBUTE_NUMBER_OF_PAGES = "NUMBER_OF_PAGES";
	public String ATTRIBUTE_SHIP_FROM_DODAAC = "SHIP_FROM_DODAAC";
	public String ATTRIBUTE_SHIP_FROM_COMPANY_ID = "SHIP_FROM_COMPANY_ID";
	public String ATTRIBUTE_SHIP_FROM_LOCATION_ID = "SHIP_FROM_LOCATION_ID";
	public String ATTRIBUTE_IGD_COMPANY_ID = "IGD_COMPANY_ID";
	public String ATTRIBUTE_IGD_LOCATION_ID = "IGD_LOCATION_ID";
	public String ATTRIBUTE_SHIP_FROM_LINE1 = "SHIP_FROM_LINE1";
	public String ATTRIBUTE_SHIP_FROM_LINE2 = "SHIP_FROM_LINE2";
	public String ATTRIBUTE_SHIP_FROM_LINE3 = "SHIP_FROM_LINE3";
	public String ATTRIBUTE_SHIP_FROM_LINE4 = "SHIP_FROM_LINE4";
	public String ATTRIBUTE_SHIP_TO_DODAAC = "SHIP_TO_DODAAC";
	public String ATTRIBUTE_SHIP_TO_COMPANY_ID = "SHIP_TO_COMPANY_ID";
	public String ATTRIBUTE_SHIP_TO_LOCATION_ID = "SHIP_TO_LOCATION_ID";
	public String ATTRIBUTE_SHIP_TO_LINE1 = "SHIP_TO_LINE1";
	public String ATTRIBUTE_SHIP_TO_LINE2 = "SHIP_TO_LINE2";
	public String ATTRIBUTE_SHIP_TO_LINE3 = "SHIP_TO_LINE3";
	public String ATTRIBUTE_SHIP_TO_LINE4 = "SHIP_TO_LINE4";
	public String ATTRIBUTE_SHIP_VIA_DODAAC = "SHIP_VIA_DODAAC";
	public String ATTRIBUTE_SHIP_VIA_COMPANY_ID = "SHIP_VIA_COMPANY_ID";
	public String ATTRIBUTE_SHIP_VIA_LOCATION_ID = "SHIP_VIA_LOCATION_ID";
	public String ATTRIBUTE_SHIP_VIA_LINE1 = "SHIP_VIA_LINE1";
	public String ATTRIBUTE_SHIP_VIA_LINE2 = "SHIP_VIA_LINE2";
	public String ATTRIBUTE_SHIP_VIA_LINE3 = "SHIP_VIA_LINE3";
	public String ATTRIBUTE_SHIP_VIA_LINE4 = "SHIP_VIA_LINE4";
	public String ATTRIBUTE_ADMIN_DODAAC = "ADMIN_DODAAC";
	public String ATTRIBUTE_ADMIN_LINE1 = "ADMIN_LINE1";
	public String ATTRIBUTE_ADMIN_LINE2 = "ADMIN_LINE2";
	public String ATTRIBUTE_ADMIN_LINE3 = "ADMIN_LINE3";
	public String ATTRIBUTE_PAYER_DODAAC = "PAYER_DODAAC";
	public String ATTRIBUTE_PAYER_LINE1 = "PAYER_LINE1";
	public String ATTRIBUTE_PAYER_LINE2 = "PAYER_LINE2";
	public String ATTRIBUTE_PAYER_LINE3 = "PAYER_LINE3";
	public String ATTRIBUTE_DELIVERY_TICKET = "DELIVERY_TICKET";
  public String ATTRIBUTE_NOTES = "NOTES";
	public String ATTRIBUTE_ORIGIN_INSPECTION_REQUIRED = "ORIGIN_INSPECTION_REQUIRED";
public String ATTRIBUTE_ORIGINAL_TRANSACTION_TYPE = "ORIGINAL_TRANSACTION_TYPE";
  //table name
	public String TABLE = "USGOV_DD250_VIEW";


	//constructor
	public UsgovDd250ViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("packingGroupId")) {
			return ATTRIBUTE_PACKING_GROUP_ID;
		}
        else if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("prNumber")) {
			return ATTRIBUTE_PR_NUMBER;
		}
		else if(attributeName.equals("lineItem")) {
			return ATTRIBUTE_LINE_ITEM;
		}
		else if(attributeName.equals("itemNo")) {
			return ATTRIBUTE_ITEM_NO;
		}
		else if(attributeName.equals("catPartNo")) {
			return ATTRIBUTE_CAT_PART_NO;
		}
		else if(attributeName.equals("partShortName")) {
			return ATTRIBUTE_PART_SHORT_NAME;
		}
		else if(attributeName.equals("shippingContainers")) {
			return ATTRIBUTE_SHIPPING_CONTAINERS;
		}
		else if(attributeName.equals("quantity")) {
			return ATTRIBUTE_QUANTITY;
		}
		else if(attributeName.equals("unitOfSale")) {
			return ATTRIBUTE_UNIT_OF_SALE;
		}
		else if(attributeName.equals("unitPrice")) {
			return ATTRIBUTE_UNIT_PRICE;
		}
		else if(attributeName.equals("amount")) {
			return ATTRIBUTE_AMOUNT;
		}
		else if(attributeName.equals("acceptancePoint")) {
			return ATTRIBUTE_ACCEPTANCE_POINT;
		}
		else if(attributeName.equals("shipDate")) {
			return ATTRIBUTE_SHIP_DATE;
		}
		else if(attributeName.equals("shipmentNumber")) {
			return ATTRIBUTE_SHIPMENT_NUMBER;
		}
		else if(attributeName.equals("carrierName")) {
			return ATTRIBUTE_CARRIER_NAME;
		}
		else if(attributeName.equals("trackingNumber")) {
			return ATTRIBUTE_TRACKING_NUMBER;
		}
		else if(attributeName.equals("tcn")) {
			return ATTRIBUTE_TCN;
		}
		else if(attributeName.equals("primeContractor")) {
			return ATTRIBUTE_PRIME_CONTRACTOR;
		}
		else if(attributeName.equals("primeContractorDodaac")) {
			return ATTRIBUTE_PRIME_CONTRACTOR_DODAAC;
		}
		else if(attributeName.equals("primeContractorLine1")) {
			return ATTRIBUTE_PRIME_CONTRACTOR_LINE1;
		}
		else if(attributeName.equals("primeContractorLine2")) {
			return ATTRIBUTE_PRIME_CONTRACTOR_LINE2;
		}
		else if(attributeName.equals("primeContractorLine3")) {
			return ATTRIBUTE_PRIME_CONTRACTOR_LINE3;
		}
		else if(attributeName.equals("contractNumber")) {
			return ATTRIBUTE_CONTRACT_NUMBER;
		}
		else if(attributeName.equals("orderNumber")) {
			return ATTRIBUTE_ORDER_NUMBER;
		}
		else if(attributeName.equals("page")) {
			return ATTRIBUTE_PAGE;
		}
		else if(attributeName.equals("numberOfPages")) {
			return ATTRIBUTE_NUMBER_OF_PAGES;
		}
		else if(attributeName.equals("shipFromDodaac")) {
			return ATTRIBUTE_SHIP_FROM_DODAAC;
		}
		else if(attributeName.equals("shipFromCompanyId")) {
			return ATTRIBUTE_SHIP_FROM_COMPANY_ID;
		}
		else if(attributeName.equals("shipFromLocationId")) {
			return ATTRIBUTE_SHIP_FROM_LOCATION_ID;
		}
		else if(attributeName.equals("igdCompanyId")) {
			return ATTRIBUTE_IGD_COMPANY_ID;
		}
		else if(attributeName.equals("igdLocationId")) {
			return ATTRIBUTE_IGD_LOCATION_ID;
		}
		else if(attributeName.equals("shipFromLine1")) {
			return ATTRIBUTE_SHIP_FROM_LINE1;
		}
		else if(attributeName.equals("shipFromLine2")) {
			return ATTRIBUTE_SHIP_FROM_LINE2;
		}
		else if(attributeName.equals("shipFromLine3")) {
			return ATTRIBUTE_SHIP_FROM_LINE3;
		}
		else if(attributeName.equals("shipFromLine4")) {
			return ATTRIBUTE_SHIP_FROM_LINE4;
		}
		else if(attributeName.equals("shipToDodaac")) {
			return ATTRIBUTE_SHIP_TO_DODAAC;
		}
		else if(attributeName.equals("shipToCompanyId")) {
			return ATTRIBUTE_SHIP_TO_COMPANY_ID;
		}
		else if(attributeName.equals("shipToLocationId")) {
			return ATTRIBUTE_SHIP_TO_LOCATION_ID;
		}
		else if(attributeName.equals("shipToLine1")) {
			return ATTRIBUTE_SHIP_TO_LINE1;
		}
		else if(attributeName.equals("shipToLine2")) {
			return ATTRIBUTE_SHIP_TO_LINE2;
		}
		else if(attributeName.equals("shipToLine3")) {
			return ATTRIBUTE_SHIP_TO_LINE3;
		}
		else if(attributeName.equals("shipToLine4")) {
			return ATTRIBUTE_SHIP_TO_LINE4;
		}
		else if(attributeName.equals("shipViaDodaac")) {
			return ATTRIBUTE_SHIP_VIA_DODAAC;
		}
		else if(attributeName.equals("shipViaCompanyId")) {
			return ATTRIBUTE_SHIP_VIA_COMPANY_ID;
		}
		else if(attributeName.equals("shipViaLocationId")) {
			return ATTRIBUTE_SHIP_VIA_LOCATION_ID;
		}
		else if(attributeName.equals("shipViaLine1")) {
			return ATTRIBUTE_SHIP_VIA_LINE1;
		}
		else if(attributeName.equals("shipViaLine2")) {
			return ATTRIBUTE_SHIP_VIA_LINE2;
		}
		else if(attributeName.equals("shipViaLine3")) {
			return ATTRIBUTE_SHIP_VIA_LINE3;
		}
		else if(attributeName.equals("shipViaLine4")) {
			return ATTRIBUTE_SHIP_VIA_LINE4;
		}
		else if(attributeName.equals("adminDodaac")) {
			return ATTRIBUTE_ADMIN_DODAAC;
		}
		else if(attributeName.equals("adminLine1")) {
			return ATTRIBUTE_ADMIN_LINE1;
		}
		else if(attributeName.equals("adminLine2")) {
			return ATTRIBUTE_ADMIN_LINE2;
		}
		else if(attributeName.equals("adminLine3")) {
			return ATTRIBUTE_ADMIN_LINE3;
		}
		else if(attributeName.equals("payerDodaac")) {
			return ATTRIBUTE_PAYER_DODAAC;
		}
		else if(attributeName.equals("payerLine1")) {
			return ATTRIBUTE_PAYER_LINE1;
		}
		else if(attributeName.equals("payerLine2")) {
			return ATTRIBUTE_PAYER_LINE2;
		}
		else if(attributeName.equals("payerLine3")) {
			return ATTRIBUTE_PAYER_LINE3;
		}
    else if(attributeName.equals("deliveryTicket")) {
			return ATTRIBUTE_DELIVERY_TICKET;
		}
    else if (attributeName.equals("notes")) {
      return ATTRIBUTE_NOTES;
    }
		else if(attributeName.equals("originInspectionRequired")) {
			return ATTRIBUTE_ORIGIN_INSPECTION_REQUIRED;
		}
else if(attributeName.equals("originalTransactionType")) {
			return ATTRIBUTE_ORIGINAL_TRANSACTION_TYPE;
		}        
    else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, UsgovDd250ViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(UsgovDd250ViewBean usgovDd250ViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("inventoryGroup", "SearchCriterion.EQUALS",
			"" + usgovDd250ViewBean.getInventoryGroup());

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


	public int delete(UsgovDd250ViewBean usgovDd250ViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("inventoryGroup", "SearchCriterion.EQUALS",
			"" + usgovDd250ViewBean.getInventoryGroup());

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
	public int insert(UsgovDd250ViewBean usgovDd250ViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(usgovDd250ViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(UsgovDd250ViewBean usgovDd250ViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_INVENTORY_GROUP + "," +
			ATTRIBUTE_PR_NUMBER + "," +
			ATTRIBUTE_LINE_ITEM + "," +
			ATTRIBUTE_ITEM_NO + "," +
			ATTRIBUTE_CAT_PART_NO + "," +
			ATTRIBUTE_PART_SHORT_NAME + "," +
			ATTRIBUTE_SHIPPING_CONTAINERS + "," +
			ATTRIBUTE_QUANTITY + "," +
			ATTRIBUTE_UNIT_OF_SALE + "," +
			ATTRIBUTE_UNIT_PRICE + "," +
			ATTRIBUTE_AMOUNT + "," +
			ATTRIBUTE_ACCEPTANCE_POINT + "," +
			ATTRIBUTE_SHIP_DATE + "," +
			ATTRIBUTE_SHIPMENT_NUMBER + "," +
			ATTRIBUTE_CARRIER_NAME + "," +
			ATTRIBUTE_TRACKING_NUMBER + "," +
			ATTRIBUTE_TCN + "," +
			ATTRIBUTE_PRIME_CONTRACTOR + "," +
			ATTRIBUTE_PRIME_CONTRACTOR_DODAAC + "," +
			ATTRIBUTE_PRIME_CONTRACTOR_LINE1 + "," +
			ATTRIBUTE_PRIME_CONTRACTOR_LINE2 + "," +
			ATTRIBUTE_PRIME_CONTRACTOR_LINE3 + "," +
			ATTRIBUTE_CONTRACT_NUMBER + "," +
			ATTRIBUTE_ORDER_NUMBER + "," +
			ATTRIBUTE_PAGE + "," +
			ATTRIBUTE_NUMBER_OF_PAGES + "," +
			ATTRIBUTE_SHIP_FROM_DODAAC + "," +
			ATTRIBUTE_SHIP_FROM_COMPANY_ID + "," +
			ATTRIBUTE_SHIP_FROM_LOCATION_ID + "," +
			ATTRIBUTE_IGD_COMPANY_ID + "," +
			ATTRIBUTE_IGD_LOCATION_ID + "," +
			ATTRIBUTE_SHIP_FROM_LINE1 + "," +
			ATTRIBUTE_SHIP_FROM_LINE2 + "," +
			ATTRIBUTE_SHIP_FROM_LINE3 + "," +
			ATTRIBUTE_SHIP_FROM_LINE4 + "," +
			ATTRIBUTE_SHIP_TO_DODAAC + "," +
			ATTRIBUTE_SHIP_TO_COMPANY_ID + "," +
			ATTRIBUTE_SHIP_TO_LOCATION_ID + "," +
			ATTRIBUTE_SHIP_TO_LINE1 + "," +
			ATTRIBUTE_SHIP_TO_LINE2 + "," +
			ATTRIBUTE_SHIP_TO_LINE3 + "," +
			ATTRIBUTE_SHIP_TO_LINE4 + "," +
			ATTRIBUTE_SHIP_VIA_DODAAC + "," +
			ATTRIBUTE_SHIP_VIA_COMPANY_ID + "," +
			ATTRIBUTE_SHIP_VIA_LOCATION_ID + "," +
			ATTRIBUTE_SHIP_VIA_LINE1 + "," +
			ATTRIBUTE_SHIP_VIA_LINE2 + "," +
			ATTRIBUTE_SHIP_VIA_LINE3 + "," +
			ATTRIBUTE_SHIP_VIA_LINE4 + "," +
			ATTRIBUTE_ADMIN_DODAAC + "," +
			ATTRIBUTE_ADMIN_LINE1 + "," +
			ATTRIBUTE_ADMIN_LINE2 + "," +
			ATTRIBUTE_ADMIN_LINE3 + "," +
			ATTRIBUTE_PAYER_DODAAC + "," +
			ATTRIBUTE_PAYER_LINE1 + "," +
			ATTRIBUTE_PAYER_LINE2 + "," +
			ATTRIBUTE_PAYER_LINE3 + ")" +
			" values (" +
			SqlHandler.delimitString(usgovDd250ViewBean.getInventoryGroup()) + "," +
			usgovDd250ViewBean.getPrNumber() + "," +
			SqlHandler.delimitString(usgovDd250ViewBean.getLineItem()) + "," +
			SqlHandler.delimitString(usgovDd250ViewBean.getItemNo()) + "," +
			SqlHandler.delimitString(usgovDd250ViewBean.getCatPartNo()) + "," +
			SqlHandler.delimitString(usgovDd250ViewBean.getPartShortName()) + "," +
			SqlHandler.delimitString(usgovDd250ViewBean.getShippingContainers()) + "," +
			usgovDd250ViewBean.getQuantity() + "," +
			SqlHandler.delimitString(usgovDd250ViewBean.getUnitOfSale()) + "," +
			usgovDd250ViewBean.getUnitPrice() + "," +
			usgovDd250ViewBean.getAmount() + "," +
			SqlHandler.delimitString(usgovDd250ViewBean.getAcceptancePoint()) + "," +
			SqlHandler.delimitString(usgovDd250ViewBean.getShipDate()) + "," +
			SqlHandler.delimitString(usgovDd250ViewBean.getShipmentNumber()) + "," +
			SqlHandler.delimitString(usgovDd250ViewBean.getCarrierName()) + "," +
			SqlHandler.delimitString(usgovDd250ViewBean.getTrackingNumber()) + "," +
			SqlHandler.delimitString(usgovDd250ViewBean.getTcn()) + "," +
			SqlHandler.delimitString(usgovDd250ViewBean.getPrimeContractor()) + "," +
			SqlHandler.delimitString(usgovDd250ViewBean.getPrimeContractorDodaac()) + "," +
			SqlHandler.delimitString(usgovDd250ViewBean.getPrimeContractorLine1()) + "," +
			SqlHandler.delimitString(usgovDd250ViewBean.getPrimeContractorLine2()) + "," +
			SqlHandler.delimitString(usgovDd250ViewBean.getPrimeContractorLine3()) + "," +
			SqlHandler.delimitString(usgovDd250ViewBean.getContractNumber()) + "," +
			SqlHandler.delimitString(usgovDd250ViewBean.getOrderNumber()) + "," +
			SqlHandler.delimitString(usgovDd250ViewBean.getPage()) + "," +
			SqlHandler.delimitString(usgovDd250ViewBean.getNumberOfPages()) + "," +
			SqlHandler.delimitString(usgovDd250ViewBean.getShipFromDodaac()) + "," +
			SqlHandler.delimitString(usgovDd250ViewBean.getShipFromCompanyId()) + "," +
			SqlHandler.delimitString(usgovDd250ViewBean.getShipFromLocationId()) + "," +
			SqlHandler.delimitString(usgovDd250ViewBean.getIgdCompanyId()) + "," +
			SqlHandler.delimitString(usgovDd250ViewBean.getIgdLocationId()) + "," +
			SqlHandler.delimitString(usgovDd250ViewBean.getShipFromLine1()) + "," +
			SqlHandler.delimitString(usgovDd250ViewBean.getShipFromLine2()) + "," +
			SqlHandler.delimitString(usgovDd250ViewBean.getShipFromLine3()) + "," +
			SqlHandler.delimitString(usgovDd250ViewBean.getShipFromLine4()) + "," +
			SqlHandler.delimitString(usgovDd250ViewBean.getShipToDodaac()) + "," +
			SqlHandler.delimitString(usgovDd250ViewBean.getShipToCompanyId()) + "," +
			SqlHandler.delimitString(usgovDd250ViewBean.getShipToLocationId()) + "," +
			SqlHandler.delimitString(usgovDd250ViewBean.getShipToLine1()) + "," +
			SqlHandler.delimitString(usgovDd250ViewBean.getShipToLine2()) + "," +
			SqlHandler.delimitString(usgovDd250ViewBean.getShipToLine3()) + "," +
			SqlHandler.delimitString(usgovDd250ViewBean.getShipToLine4()) + "," +
			SqlHandler.delimitString(usgovDd250ViewBean.getShipViaDodaac()) + "," +
			SqlHandler.delimitString(usgovDd250ViewBean.getShipViaCompanyId()) + "," +
			SqlHandler.delimitString(usgovDd250ViewBean.getShipViaLocationId()) + "," +
			SqlHandler.delimitString(usgovDd250ViewBean.getShipViaLine1()) + "," +
			SqlHandler.delimitString(usgovDd250ViewBean.getShipViaLine2()) + "," +
			SqlHandler.delimitString(usgovDd250ViewBean.getShipViaLine3()) + "," +
			SqlHandler.delimitString(usgovDd250ViewBean.getShipViaLine4()) + "," +
			SqlHandler.delimitString(usgovDd250ViewBean.getAdminDodaac()) + "," +
			SqlHandler.delimitString(usgovDd250ViewBean.getAdminLine1()) + "," +
			SqlHandler.delimitString(usgovDd250ViewBean.getAdminLine2()) + "," +
			SqlHandler.delimitString(usgovDd250ViewBean.getAdminLine3()) + "," +
			SqlHandler.delimitString(usgovDd250ViewBean.getPayerDodaac()) + "," +
			SqlHandler.delimitString(usgovDd250ViewBean.getPayerLine1()) + "," +
			SqlHandler.delimitString(usgovDd250ViewBean.getPayerLine2()) + "," +
			SqlHandler.delimitString(usgovDd250ViewBean.getPayerLine3()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(UsgovDd250ViewBean usgovDd250ViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(usgovDd250ViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(UsgovDd250ViewBean usgovDd250ViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_INVENTORY_GROUP + "=" + 
				SqlHandler.delimitString(usgovDd250ViewBean.getInventoryGroup()) + "," +
			ATTRIBUTE_PR_NUMBER + "=" + 
				StringHandler.nullIfZero(usgovDd250ViewBean.getPrNumber()) + "," +
			ATTRIBUTE_LINE_ITEM + "=" + 
				SqlHandler.delimitString(usgovDd250ViewBean.getLineItem()) + "," +
			ATTRIBUTE_ITEM_NO + "=" + 
				SqlHandler.delimitString(usgovDd250ViewBean.getItemNo()) + "," +
			ATTRIBUTE_CAT_PART_NO + "=" + 
				SqlHandler.delimitString(usgovDd250ViewBean.getCatPartNo()) + "," +
			ATTRIBUTE_PART_SHORT_NAME + "=" + 
				SqlHandler.delimitString(usgovDd250ViewBean.getPartShortName()) + "," +
			ATTRIBUTE_SHIPPING_CONTAINERS + "=" + 
				SqlHandler.delimitString(usgovDd250ViewBean.getShippingContainers()) + "," +
			ATTRIBUTE_QUANTITY + "=" + 
				StringHandler.nullIfZero(usgovDd250ViewBean.getQuantity()) + "," +
			ATTRIBUTE_UNIT_OF_SALE + "=" + 
				SqlHandler.delimitString(usgovDd250ViewBean.getUnitOfSale()) + "," +
			ATTRIBUTE_UNIT_PRICE + "=" + 
				StringHandler.nullIfZero(usgovDd250ViewBean.getUnitPrice()) + "," +
			ATTRIBUTE_AMOUNT + "=" + 
				StringHandler.nullIfZero(usgovDd250ViewBean.getAmount()) + "," +
			ATTRIBUTE_ACCEPTANCE_POINT + "=" + 
				SqlHandler.delimitString(usgovDd250ViewBean.getAcceptancePoint()) + "," +
			ATTRIBUTE_SHIP_DATE + "=" + 
				SqlHandler.delimitString(usgovDd250ViewBean.getShipDate()) + "," +
			ATTRIBUTE_SHIPMENT_NUMBER + "=" + 
				SqlHandler.delimitString(usgovDd250ViewBean.getShipmentNumber()) + "," +
			ATTRIBUTE_CARRIER_NAME + "=" + 
				SqlHandler.delimitString(usgovDd250ViewBean.getCarrierName()) + "," +
			ATTRIBUTE_TRACKING_NUMBER + "=" + 
				SqlHandler.delimitString(usgovDd250ViewBean.getTrackingNumber()) + "," +
			ATTRIBUTE_TCN + "=" + 
				SqlHandler.delimitString(usgovDd250ViewBean.getTcn()) + "," +
			ATTRIBUTE_PRIME_CONTRACTOR + "=" + 
				SqlHandler.delimitString(usgovDd250ViewBean.getPrimeContractor()) + "," +
			ATTRIBUTE_PRIME_CONTRACTOR_DODAAC + "=" + 
				SqlHandler.delimitString(usgovDd250ViewBean.getPrimeContractorDodaac()) + "," +
			ATTRIBUTE_PRIME_CONTRACTOR_LINE1 + "=" + 
				SqlHandler.delimitString(usgovDd250ViewBean.getPrimeContractorLine1()) + "," +
			ATTRIBUTE_PRIME_CONTRACTOR_LINE2 + "=" + 
				SqlHandler.delimitString(usgovDd250ViewBean.getPrimeContractorLine2()) + "," +
			ATTRIBUTE_PRIME_CONTRACTOR_LINE3 + "=" + 
				SqlHandler.delimitString(usgovDd250ViewBean.getPrimeContractorLine3()) + "," +
			ATTRIBUTE_CONTRACT_NUMBER + "=" + 
				SqlHandler.delimitString(usgovDd250ViewBean.getContractNumber()) + "," +
			ATTRIBUTE_ORDER_NUMBER + "=" + 
				SqlHandler.delimitString(usgovDd250ViewBean.getOrderNumber()) + "," +
			ATTRIBUTE_PAGE + "=" + 
				SqlHandler.delimitString(usgovDd250ViewBean.getPage()) + "," +
			ATTRIBUTE_NUMBER_OF_PAGES + "=" + 
				SqlHandler.delimitString(usgovDd250ViewBean.getNumberOfPages()) + "," +
			ATTRIBUTE_SHIP_FROM_DODAAC + "=" + 
				SqlHandler.delimitString(usgovDd250ViewBean.getShipFromDodaac()) + "," +
			ATTRIBUTE_SHIP_FROM_COMPANY_ID + "=" + 
				SqlHandler.delimitString(usgovDd250ViewBean.getShipFromCompanyId()) + "," +
			ATTRIBUTE_SHIP_FROM_LOCATION_ID + "=" + 
				SqlHandler.delimitString(usgovDd250ViewBean.getShipFromLocationId()) + "," +
			ATTRIBUTE_IGD_COMPANY_ID + "=" + 
				SqlHandler.delimitString(usgovDd250ViewBean.getIgdCompanyId()) + "," +
			ATTRIBUTE_IGD_LOCATION_ID + "=" + 
				SqlHandler.delimitString(usgovDd250ViewBean.getIgdLocationId()) + "," +
			ATTRIBUTE_SHIP_FROM_LINE1 + "=" + 
				SqlHandler.delimitString(usgovDd250ViewBean.getShipFromLine1()) + "," +
			ATTRIBUTE_SHIP_FROM_LINE2 + "=" + 
				SqlHandler.delimitString(usgovDd250ViewBean.getShipFromLine2()) + "," +
			ATTRIBUTE_SHIP_FROM_LINE3 + "=" + 
				SqlHandler.delimitString(usgovDd250ViewBean.getShipFromLine3()) + "," +
			ATTRIBUTE_SHIP_FROM_LINE4 + "=" + 
				SqlHandler.delimitString(usgovDd250ViewBean.getShipFromLine4()) + "," +
			ATTRIBUTE_SHIP_TO_DODAAC + "=" + 
				SqlHandler.delimitString(usgovDd250ViewBean.getShipToDodaac()) + "," +
			ATTRIBUTE_SHIP_TO_COMPANY_ID + "=" + 
				SqlHandler.delimitString(usgovDd250ViewBean.getShipToCompanyId()) + "," +
			ATTRIBUTE_SHIP_TO_LOCATION_ID + "=" + 
				SqlHandler.delimitString(usgovDd250ViewBean.getShipToLocationId()) + "," +
			ATTRIBUTE_SHIP_TO_LINE1 + "=" + 
				SqlHandler.delimitString(usgovDd250ViewBean.getShipToLine1()) + "," +
			ATTRIBUTE_SHIP_TO_LINE2 + "=" + 
				SqlHandler.delimitString(usgovDd250ViewBean.getShipToLine2()) + "," +
			ATTRIBUTE_SHIP_TO_LINE3 + "=" + 
				SqlHandler.delimitString(usgovDd250ViewBean.getShipToLine3()) + "," +
			ATTRIBUTE_SHIP_TO_LINE4 + "=" + 
				SqlHandler.delimitString(usgovDd250ViewBean.getShipToLine4()) + "," +
			ATTRIBUTE_SHIP_VIA_DODAAC + "=" + 
				SqlHandler.delimitString(usgovDd250ViewBean.getShipViaDodaac()) + "," +
			ATTRIBUTE_SHIP_VIA_COMPANY_ID + "=" + 
				SqlHandler.delimitString(usgovDd250ViewBean.getShipViaCompanyId()) + "," +
			ATTRIBUTE_SHIP_VIA_LOCATION_ID + "=" + 
				SqlHandler.delimitString(usgovDd250ViewBean.getShipViaLocationId()) + "," +
			ATTRIBUTE_SHIP_VIA_LINE1 + "=" + 
				SqlHandler.delimitString(usgovDd250ViewBean.getShipViaLine1()) + "," +
			ATTRIBUTE_SHIP_VIA_LINE2 + "=" + 
				SqlHandler.delimitString(usgovDd250ViewBean.getShipViaLine2()) + "," +
			ATTRIBUTE_SHIP_VIA_LINE3 + "=" + 
				SqlHandler.delimitString(usgovDd250ViewBean.getShipViaLine3()) + "," +
			ATTRIBUTE_SHIP_VIA_LINE4 + "=" + 
				SqlHandler.delimitString(usgovDd250ViewBean.getShipViaLine4()) + "," +
			ATTRIBUTE_ADMIN_DODAAC + "=" + 
				SqlHandler.delimitString(usgovDd250ViewBean.getAdminDodaac()) + "," +
			ATTRIBUTE_ADMIN_LINE1 + "=" + 
				SqlHandler.delimitString(usgovDd250ViewBean.getAdminLine1()) + "," +
			ATTRIBUTE_ADMIN_LINE2 + "=" + 
				SqlHandler.delimitString(usgovDd250ViewBean.getAdminLine2()) + "," +
			ATTRIBUTE_ADMIN_LINE3 + "=" + 
				SqlHandler.delimitString(usgovDd250ViewBean.getAdminLine3()) + "," +
			ATTRIBUTE_PAYER_DODAAC + "=" + 
				SqlHandler.delimitString(usgovDd250ViewBean.getPayerDodaac()) + "," +
			ATTRIBUTE_PAYER_LINE1 + "=" + 
				SqlHandler.delimitString(usgovDd250ViewBean.getPayerLine1()) + "," +
			ATTRIBUTE_PAYER_LINE2 + "=" + 
				SqlHandler.delimitString(usgovDd250ViewBean.getPayerLine2()) + "," +
			ATTRIBUTE_PAYER_LINE3 + "=" + 
				SqlHandler.delimitString(usgovDd250ViewBean.getPayerLine3()) + " " +
			"where " + ATTRIBUTE_INVENTORY_GROUP + "=" +
				usgovDd250ViewBean.getInventoryGroup();

		return new SqlManager().update(conn, query);
	}
*/

	//select
	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria)
		throws BaseException {

		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = select(criteria, sortCriteria, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}
	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn)
		throws BaseException {

		Collection usgovDd250ViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			UsgovDd250ViewBean usgovDd250ViewBean = new UsgovDd250ViewBean();
			load(dataSetRow, usgovDd250ViewBean);
			usgovDd250ViewBeanColl.add(usgovDd250ViewBean);
		}

		return usgovDd250ViewBeanColl;
	}
}