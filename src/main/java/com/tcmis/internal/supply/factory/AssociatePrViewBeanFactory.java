package com.tcmis.internal.supply.factory;


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
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DataBeanCreationException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.util.*;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.internal.supply.beans.AssociatePrViewBean;


/******************************************************************************
 * CLASSNAME: AssociatePrViewBeanFactory <br>
 * @version: 1.0, Mar 10, 2006 <br>
 *****************************************************************************/


public class AssociatePrViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_FROZEN = "FROZEN";
	public String ATTRIBUTE_CRITICAL = "CRITICAL";
	public String ATTRIBUTE_NOTES = "NOTES";
	public String ATTRIBUTE_CANCEL_STATUS = "CANCEL_STATUS";
	public String ATTRIBUTE_REQUEST_LINE_STATUS = "REQUEST_LINE_STATUS";
	public String ATTRIBUTE_CATALOG_PRICE = "CATALOG_PRICE";
	public String ATTRIBUTE_BASELINE_PRICE = "BASELINE_PRICE";
	public String ATTRIBUTE_MR_QUANTITY = "MR_QUANTITY";
	public String ATTRIBUTE_REQUESTOR_FIRST_NAME = "REQUESTOR_FIRST_NAME";
	public String ATTRIBUTE_REQUESTOR_LAST_NAME = "REQUESTOR_LAST_NAME";
	public String ATTRIBUTE_EMAIL = "EMAIL";
	public String ATTRIBUTE_PHONE = "PHONE";
	public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
	public String ATTRIBUTE_BUYER = "BUYER";
	public String ATTRIBUTE_DATE_ASSIGNED = "DATE_ASSIGNED";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_NEED_DATE = "NEED_DATE";
	public String ATTRIBUTE_PART_ID = "PART_ID";
	public String ATTRIBUTE_ITEM_DESC = "ITEM_DESC";
	public String ATTRIBUTE_SHELF_LIFE_DAYS = "SHELF_LIFE_DAYS";
	public String ATTRIBUTE_TRADE_NAME = "TRADE_NAME";
	public String ATTRIBUTE_SIZE_UNIT = "SIZE_UNIT";
	public String ATTRIBUTE_MFG_ID = "MFG_ID";
	public String ATTRIBUTE_MFG_PART_NO = "MFG_PART_NO";
	public String ATTRIBUTE_ITEM_TYPE = "ITEM_TYPE";
	public String ATTRIBUTE_ORDER_QUANTITY = "ORDER_QUANTITY";
	public String ATTRIBUTE_UOM = "UOM";
	public String ATTRIBUTE_PRIORITY = "PRIORITY";
	public String ATTRIBUTE_RADIAN_PO = "RADIAN_PO";
	public String ATTRIBUTE_FACILITY = "FACILITY";
	public String ATTRIBUTE_RAYTHEON_PO = "RAYTHEON_PO";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_BRANCH_PLANT = "BRANCH_PLANT";
	public String ATTRIBUTE_HOME_CURRENCY_ID = "HOME_CURRENCY_ID";
	public String ATTRIBUTE_DATE_ISSUED = "DATE_ISSUED";
	public String ATTRIBUTE_DATE_PO_CREATED = "DATE_PO_CREATED";
	public String ATTRIBUTE_STATUS = "STATUS";
	public String ATTRIBUTE_DATE_CHANGED = "DATE_CHANGED";
	public String ATTRIBUTE_COMMENTS = "COMMENTS";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_MR_NUMBER = "MR_NUMBER";
	public String ATTRIBUTE_MR_LINE_ITEM = "MR_LINE_ITEM";
	public String ATTRIBUTE_REORDER_POINT = "REORDER_POINT";
	public String ATTRIBUTE_STOCKING_LEVEL = "STOCKING_LEVEL";
	public String ATTRIBUTE_AVAILABLE_QUANTITY = "AVAILABLE_QUANTITY";
	public String ATTRIBUTE_OPEN_PO_QUANTITY = "OPEN_PO_QUANTITY";
	public String ATTRIBUTE_SHIP_TO_DELIVERY_POINT = "SHIP_TO_DELIVERY_POINT";
	public String ATTRIBUTE_SHIP_TO_LOCATION_ID = "SHIP_TO_LOCATION_ID";
	public String ATTRIBUTE_SHIP_TO_COMPANY_ID = "SHIP_TO_COMPANY_ID";
	public String ATTRIBUTE_BUYER_ID = "BUYER_ID";
	public String ATTRIBUTE_LAST_SUPPLIER = "LAST_SUPPLIER";
	public String ATTRIBUTE_BUYER_NAME = "BUYER_NAME";
	public String ATTRIBUTE_EVER_CONFIRMED = "EVER_CONFIRMED";
	public String ATTRIBUTE_UNCONFIRMED = "UNCONFIRMED";
	public String ATTRIBUTE_DELIVERY_TYPE = "DELIVERY_TYPE";
	public String ATTRIBUTE_PO_IN_JDE = "PO_IN_JDE";
	public String ATTRIBUTE_LPP = "LPP";
	public String ATTRIBUTE_ENGINEERING_EVALUATION = "ENGINEERING_EVALUATION";
	public String ATTRIBUTE_REQUEST_ID = "REQUEST_ID";
	public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
	public String ATTRIBUTE_STOCKED = "STOCKED";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_CUSTOMER_PO_NUMBER = "CUSTOMER_PO_NUMBER";
	public String ATTRIBUTE_RELEASE_NUMBER = "RELEASE_NUMBER";
	public String ATTRIBUTE_BPO_DETAIL = "BPO_DETAIL";
	public String ATTRIBUTE_BPO = "BPO";
	public String ATTRIBUTE_SUPPLY_PATH = "SUPPLY_PATH";
	public String ATTRIBUTE_DELIVERY_POINT_DESC = "DELIVERY_POINT_DESC";
	public String ATTRIBUTE_CONSOLIDATION_ALLOWED = "CONSOLIDATION_ALLOWED";
	public String ATTRIBUTE_PURCHASING_UNITS_PER_ITEM = "PURCHASING_UNITS_PER_ITEM";
	public String ATTRIBUTE_PURCHASING_UNIT_OF_MEASURE = "PURCHASING_UNIT_OF_MEASURE";
	public String ATTRIBUTE_BUYER_COMPANY_ID = "BUYER_COMPANY_ID";
	public String ATTRIBUTE_REMAINING_SHELF_LIFE_PERCENT = "REMAINING_SHELF_LIFE_PERCENT";
	public String ATTRIBUTE_PICK_DATE = "PICK_DATE";
	public String ATTRIBUTE_CSR_NAME = "CSR_NAME";
	public String ATTRIBUTE_PURCHASING_NOTE = "PURCHASING_NOTE";
	
	//table name
	public String TABLE = "ASSOCIATE_PR_VIEW";
	public String BUYORDER_TABLE = "BUY_ORDER";

	//constructor
	public AssociatePrViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("frozen")) {
			return ATTRIBUTE_FROZEN;
		}
		else if(attributeName.equals("critical")) {
			return ATTRIBUTE_CRITICAL;
		}
		else if(attributeName.equals("notes")) {
			return ATTRIBUTE_NOTES;
		}
		else if(attributeName.equals("cancelStatus")) {
			return ATTRIBUTE_CANCEL_STATUS;
		}
		else if(attributeName.equals("requestLineStatus")) {
			return ATTRIBUTE_REQUEST_LINE_STATUS;
		}
		else if(attributeName.equals("catalogPrice")) {
			return ATTRIBUTE_CATALOG_PRICE;
		}
		else if(attributeName.equals("baselinePrice")) {
			return ATTRIBUTE_BASELINE_PRICE;
		}
		else if(attributeName.equals("mrQuantity")) {
			return ATTRIBUTE_MR_QUANTITY;
		}
		else if(attributeName.equals("requestorFirstName")) {
			return ATTRIBUTE_REQUESTOR_FIRST_NAME;
		}
		else if(attributeName.equals("requestorLastName")) {
			return ATTRIBUTE_REQUESTOR_LAST_NAME;
		}
		else if(attributeName.equals("email")) {
			return ATTRIBUTE_EMAIL;
		}
		else if(attributeName.equals("phone")) {
			return ATTRIBUTE_PHONE;
		}
		else if(attributeName.equals("prNumber")) {
			return ATTRIBUTE_PR_NUMBER;
		}
		else if(attributeName.equals("buyer")) {
			return ATTRIBUTE_BUYER;
		}
		else if(attributeName.equals("dateAssigned")) {
			return ATTRIBUTE_DATE_ASSIGNED;
		}
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("needDate")) {
			return ATTRIBUTE_NEED_DATE;
		}
		else if(attributeName.equals("partId")) {
			return ATTRIBUTE_PART_ID;
		}
		else if(attributeName.equals("itemDesc")) {
			return ATTRIBUTE_ITEM_DESC;
		}
		else if(attributeName.equals("shelfLifeDays")) {
			return ATTRIBUTE_SHELF_LIFE_DAYS;
		}
		else if(attributeName.equals("tradeName")) {
			return ATTRIBUTE_TRADE_NAME;
		}
		else if(attributeName.equals("sizeUnit")) {
			return ATTRIBUTE_SIZE_UNIT;
		}
		else if(attributeName.equals("mfgId")) {
			return ATTRIBUTE_MFG_ID;
		}
		else if(attributeName.equals("mfgPartNo")) {
			return ATTRIBUTE_MFG_PART_NO;
		}
		else if(attributeName.equals("itemType")) {
			return ATTRIBUTE_ITEM_TYPE;
		}
		else if(attributeName.equals("orderQuantity")) {
			return ATTRIBUTE_ORDER_QUANTITY;
		}
		else if(attributeName.equals("uom")) {
			return ATTRIBUTE_UOM;
		}
		else if(attributeName.equals("priority")) {
			return ATTRIBUTE_PRIORITY;
		}
		else if(attributeName.equals("radianPo")) {
			return ATTRIBUTE_RADIAN_PO;
		}
		else if(attributeName.equals("facility")) {
			return ATTRIBUTE_FACILITY;
		}
		else if(attributeName.equals("raytheonPo")) {
			return ATTRIBUTE_RAYTHEON_PO;
		}
		else if(attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if(attributeName.equals("branchPlant")) {
			return ATTRIBUTE_BRANCH_PLANT;
		}
		else if(attributeName.equals("homeCurrencyId")) {
			return ATTRIBUTE_HOME_CURRENCY_ID;
		}
		else if(attributeName.equals("dateIssued")) {
			return ATTRIBUTE_DATE_ISSUED;
		}
		else if(attributeName.equals("datePoCreated")) {
			return ATTRIBUTE_DATE_PO_CREATED;
		}
		else if(attributeName.equals("status")) {
			return ATTRIBUTE_STATUS;
		}
		else if(attributeName.equals("dateChanged")) {
			return ATTRIBUTE_DATE_CHANGED;
		}
		else if(attributeName.equals("comments")) {
			return ATTRIBUTE_COMMENTS;
		}
		else if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("mrNumber")) {
			return ATTRIBUTE_MR_NUMBER;
		}
		else if(attributeName.equals("mrLineItem")) {
			return ATTRIBUTE_MR_LINE_ITEM;
		}
		else if(attributeName.equals("reorderPoint")) {
			return ATTRIBUTE_REORDER_POINT;
		}
		else if(attributeName.equals("stockingLevel")) {
			return ATTRIBUTE_STOCKING_LEVEL;
		}
		else if(attributeName.equals("availableQuantity")) {
			return ATTRIBUTE_AVAILABLE_QUANTITY;
		}
		else if(attributeName.equals("openPoQuantity")) {
			return ATTRIBUTE_OPEN_PO_QUANTITY;
		}
		else if(attributeName.equals("shipToDeliveryPoint")) {
			return ATTRIBUTE_SHIP_TO_DELIVERY_POINT;
		}
		else if(attributeName.equals("shipToLocationId")) {
			return ATTRIBUTE_SHIP_TO_LOCATION_ID;
		}
		else if(attributeName.equals("shipToCompanyId")) {
			return ATTRIBUTE_SHIP_TO_COMPANY_ID;
		}
		else if(attributeName.equals("buyerId")) {
			return ATTRIBUTE_BUYER_ID;
		}
		else if(attributeName.equals("lastSupplier")) {
			return ATTRIBUTE_LAST_SUPPLIER;
		}
		else if(attributeName.equals("buyerName")) {
			return ATTRIBUTE_BUYER_NAME;
		}
		else if(attributeName.equals("everConfirmed")) {
			return ATTRIBUTE_EVER_CONFIRMED;
		}
		else if(attributeName.equals("unconfirmed")) {
			return ATTRIBUTE_UNCONFIRMED;
		}
		else if(attributeName.equals("deliveryType")) {
			return ATTRIBUTE_DELIVERY_TYPE;
		}
		else if(attributeName.equals("poInJde")) {
			return ATTRIBUTE_PO_IN_JDE;
		}
		else if(attributeName.equals("lpp")) {
			return ATTRIBUTE_LPP;
		}
		else if(attributeName.equals("engineeringEvaluation")) {
			return ATTRIBUTE_ENGINEERING_EVALUATION;
		}
		else if(attributeName.equals("requestId")) {
			return ATTRIBUTE_REQUEST_ID;
		}
		else if(attributeName.equals("catalogId")) {
			return ATTRIBUTE_CATALOG_ID;
		}
		else if(attributeName.equals("stocked")) {
			return ATTRIBUTE_STOCKED;
		}
		else if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("customerPoNumber")) {
			return ATTRIBUTE_CUSTOMER_PO_NUMBER;
		}
		else if(attributeName.equals("releaseNumber")) {
			return ATTRIBUTE_RELEASE_NUMBER;
		}
		else if(attributeName.equals("bpoDetail")) {
			return ATTRIBUTE_BPO_DETAIL;
		}
		else if(attributeName.equals("bpo")) {
			return ATTRIBUTE_BPO;
		}
		else if(attributeName.equals("supplyPath")) {
			return ATTRIBUTE_SUPPLY_PATH;
		}
		else if(attributeName.equals("deliveryPointDesc")) {
			return ATTRIBUTE_DELIVERY_POINT_DESC;
		}
		else if(attributeName.equals("consolidationAllowed")) {
			return ATTRIBUTE_CONSOLIDATION_ALLOWED;
		}
		else if(attributeName.equals("purchasingUnitsPerItem")) {
			return ATTRIBUTE_PURCHASING_UNITS_PER_ITEM;
		}
		else if(attributeName.equals("purchasingUnitOfMeasure")) {
			return ATTRIBUTE_PURCHASING_UNIT_OF_MEASURE;
		}
		else if(attributeName.equals("buyerCompanyId")) {
		 return ATTRIBUTE_BUYER_COMPANY_ID;
		}
		else if (attributeName.equals("remainingShelfLifePercent")) {
			return ATTRIBUTE_REMAINING_SHELF_LIFE_PERCENT;
		}
		else if (attributeName.equals("pickDate")) {
			return ATTRIBUTE_PICK_DATE;
		}
		else if (attributeName.equals("csrName")) {
			return ATTRIBUTE_CSR_NAME;
		}
		else if (attributeName.equals("purchasingNote")) {
			return ATTRIBUTE_PURCHASING_NOTE;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}

	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, AssociatePrViewBean.class);
	}

	//update
	public int updateBuyerCompanyId(AssociatePrViewBean associatePrViewBean,BigDecimal personnelId)
		throws BaseException {
		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = updateBuyerCompanyId(associatePrViewBean,personnelId, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}

	 public int updateBuyerCompanyId(AssociatePrViewBean associatePrViewBean,
		BigDecimal personnelId, Connection conn) throws BaseException {
		String query = "update " + BUYORDER_TABLE + " set " +
		 ATTRIBUTE_BUYER_COMPANY_ID + "=" +
		 SqlHandler.delimitString(associatePrViewBean.getBuyerCompanyId()) + "," +
		 ATTRIBUTE_BUYER_ID + "=" + StringHandler.nullIfZero(personnelId) + " where " +
		 ATTRIBUTE_PR_NUMBER + "=" +
		 associatePrViewBean.getPrNumber();

		return new SqlManager().update(conn, query);
	}

	//update call p_create_buyer_company_po
	public Collection createCustomerPo(AssociatePrViewBean inputBean,BigDecimal personnelId) throws Exception {
		Connection connection = this.getDbManager().getConnection();
		Collection cin = new Vector();

	if (inputBean.getRaytheonPo() != null &&
	 inputBean.getRaytheonPo().trim().length() > 0) {
	 cin.add(inputBean.getRaytheonPo().trim());
	}
	else {
	 cin.add("");
	}

	if (inputBean.getOrderQuantity() != null) {
	 cin.add(inputBean.getOrderQuantity());
	}
	else {
	 cin.add("");
	}

	cin.add(inputBean.getPrNumber());
	cin.add(personnelId);

	Collection cout = new Vector();
	cout.add(new Integer(java.sql.Types.NUMERIC));
	log.info("calling p_create_buyer_company_po "+inputBean.getPrNumber()+" ");
	Collection result = this.getDbManager().doProcedure("p_create_buyer_company_po",
	 cin, cout);
	this.getDbManager().returnConnection(connection);
	return result;
 }

 //select
	public Collection select(SearchCriteria criteria)
		throws BaseException {

		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = select(criteria,null, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}

 public Collection select(SearchCriteria criteria,SortCriteria sortCriteria)
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

	public Collection select(SearchCriteria criteria,SortCriteria sortCriteria, Connection conn)
		throws BaseException {

		Collection associatePrViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);
		if (sortCriteria !=null)
		{
			 query += getOrderByClause(sortCriteria);
		}

		//String query = "select * from " + TABLE + "where mr_number in (541200,541201,541202)";


		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			AssociatePrViewBean associatePrViewBean = new AssociatePrViewBean();
			load(dataSetRow, associatePrViewBean);
			associatePrViewBeanColl.add(associatePrViewBean);
		}

		return associatePrViewBeanColl;
	}

  public Collection selectDistinctHaasPo(SearchCriteria criteria,SortCriteria sortCriteria)
	throws BaseException {

	Connection connection = null;
	Collection c = null;
	try {
		connection = this.getDbManager().getConnection();
		c = selectDistinctHaasPo(criteria,sortCriteria, connection);
	}
	finally {
		this.getDbManager().returnConnection(connection);
	}
	return c;
 }

	public Collection selectDistinctHaasPo(SearchCriteria criteria,SortCriteria sortCriteria, Connection conn)
		throws BaseException {

		Collection associatePrViewBeanColl = new Vector();

		String query = "select distinct "+ATTRIBUTE_RADIAN_PO+" from " + TABLE + " " +
			getWhereClause(criteria);
		if (sortCriteria !=null)
		{
			 query += getOrderByClause(sortCriteria);
		}

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			AssociatePrViewBean associatePrViewBean = new AssociatePrViewBean();
			load(dataSetRow, associatePrViewBean);
			associatePrViewBeanColl.add(associatePrViewBean);
		}

		return associatePrViewBeanColl;
	}

}