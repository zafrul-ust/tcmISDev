package com.tcmis.supplier.dbuy.factory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
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
import com.tcmis.supplier.dbuy.beans.DbuyContractBean;

/******************************************************************************
 * CLASSNAME: DbuyContractBeanFactory <br>
 * @version: 1.0, May 3, 2006 <br>
 *****************************************************************************/

public class DbuyContractBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());
	//column names
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_SHIP_TO_COMPANY_ID = "SHIP_TO_COMPANY_ID";
	public String ATTRIBUTE_SHIP_TO_LOCATION_ID = "SHIP_TO_LOCATION_ID";
	public String ATTRIBUTE_PRIORITY = "PRIORITY";
	public String ATTRIBUTE_DBUY_CONTRACT_ID = "DBUY_CONTRACT_ID";
	public String ATTRIBUTE_SUPPLIER = "SUPPLIER";
	public String ATTRIBUTE_CARRIER = "CARRIER";
	public String ATTRIBUTE_BUYER = "BUYER";
	public String ATTRIBUTE_SOURCER = "SOURCER";
	public String ATTRIBUTE_PAYMENT_TERMS = "PAYMENT_TERMS";
	public String ATTRIBUTE_STATUS = "STATUS";
	public String ATTRIBUTE_FREIGHT_ON_BOARD = "FREIGHT_ON_BOARD";
	public String ATTRIBUTE_MULTIPLE_OF = "MULTIPLE_OF";
	public String ATTRIBUTE_SUPPLIER_PART_NO = "SUPPLIER_PART_NO";
	public String ATTRIBUTE_SUPPLIER_CONTACT_ID = "SUPPLIER_CONTACT_ID";
	public String ATTRIBUTE_REMAINING_SHELF_LIFE_PERCENT = "REMAINING_SHELF_LIFE_PERCENT";
	public String ATTRIBUTE_CRITICAL_ORDER_CARRIER = "CRITICAL_ORDER_CARRIER";
	public String ATTRIBUTE_REF_CLIENT_PART_NO = "REF_CLIENT_PART_NO";
	public String ATTRIBUTE_REF_CLIENT_PO_NO = "REF_CLIENT_PO_NO";
	public String ATTRIBUTE_ROUND_TO_MULTIPLE = "ROUND_TO_MULTIPLE";
	public String ATTRIBUTE_CONSIGNMENT = "CONSIGNMENT";
	public String ATTRIBUTE_LEAD_TIME_DAYS = "LEAD_TIME_DAYS";
	public String ATTRIBUTE_DEFAULT_SHIPMENT_ORIGIN_STATE = "DEFAULT_SHIPMENT_ORIGIN_STATE";
	public String ATTRIBUTE_TRANSIT_TIME_IN_DAYS = "TRANSIT_TIME_IN_DAYS";
	public String ATTRIBUTE_CONTRACT_REFERENCE_COMMENTS = "CONTRACT_REFERENCE_COMMENTS";
	public String ATTRIBUTE_LOADING_COMMENTS = "LOADING_COMMENTS";
	public String ATTRIBUTE_PRICING_TYPE = "PRICING_TYPE";
	public String ATTRIBUTE_SUPPLY_PATH = "SUPPLY_PATH";
	public String ATTRIBUTE_LOADING_DATE = "LOADING_DATE";
	public String ATTRIBUTE_BUYER_COMPANY_ID = "BUYER_COMPANY_ID";

	//table name
	public String TABLE = "DBUY_CONTRACT";


	//constructor
	public DbuyContractBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("shipToCompanyId")) {
			return ATTRIBUTE_SHIP_TO_COMPANY_ID;
		}
		else if(attributeName.equals("shipToLocationId")) {
			return ATTRIBUTE_SHIP_TO_LOCATION_ID;
		}
		else if(attributeName.equals("priority")) {
			return ATTRIBUTE_PRIORITY;
		}
		else if(attributeName.equals("dbuyContractId")) {
			return ATTRIBUTE_DBUY_CONTRACT_ID;
		}
		else if(attributeName.equals("supplier")) {
			return ATTRIBUTE_SUPPLIER;
		}
		else if(attributeName.equals("carrier")) {
			return ATTRIBUTE_CARRIER;
		}
		else if(attributeName.equals("buyer")) {
			return ATTRIBUTE_BUYER;
		}
		else if(attributeName.equals("sourcer")) {
			return ATTRIBUTE_SOURCER;
		}
		else if(attributeName.equals("paymentTerms")) {
			return ATTRIBUTE_PAYMENT_TERMS;
		}
		else if(attributeName.equals("status")) {
			return ATTRIBUTE_STATUS;
		}
		else if(attributeName.equals("freightOnBoard")) {
			return ATTRIBUTE_FREIGHT_ON_BOARD;
		}
		else if(attributeName.equals("multipleOf")) {
			return ATTRIBUTE_MULTIPLE_OF;
		}
		else if(attributeName.equals("supplierPartNo")) {
			return ATTRIBUTE_SUPPLIER_PART_NO;
		}
		else if(attributeName.equals("supplierContactId")) {
			return ATTRIBUTE_SUPPLIER_CONTACT_ID;
		}
		else if(attributeName.equals("remainingShelfLifePercent")) {
			return ATTRIBUTE_REMAINING_SHELF_LIFE_PERCENT;
		}
		else if(attributeName.equals("criticalOrderCarrier")) {
			return ATTRIBUTE_CRITICAL_ORDER_CARRIER;
		}
		else if(attributeName.equals("refClientPartNo")) {
			return ATTRIBUTE_REF_CLIENT_PART_NO;
		}
		else if(attributeName.equals("refClientPoNo")) {
			return ATTRIBUTE_REF_CLIENT_PO_NO;
		}
		else if(attributeName.equals("roundToMultiple")) {
			return ATTRIBUTE_ROUND_TO_MULTIPLE;
		}
		else if(attributeName.equals("consignment")) {
			return ATTRIBUTE_CONSIGNMENT;
		}
		else if(attributeName.equals("leadTimeDays")) {
			return ATTRIBUTE_LEAD_TIME_DAYS;
		}
		else if(attributeName.equals("defaultShipmentOriginState")) {
			return ATTRIBUTE_DEFAULT_SHIPMENT_ORIGIN_STATE;
		}
		else if(attributeName.equals("transitTimeInDays")) {
			return ATTRIBUTE_TRANSIT_TIME_IN_DAYS;
		}
		else if(attributeName.equals("contractReferenceComments")) {
			return ATTRIBUTE_CONTRACT_REFERENCE_COMMENTS;
		}
		else if(attributeName.equals("loadingComments")) {
			return ATTRIBUTE_LOADING_COMMENTS;
		}
		else if(attributeName.equals("pricingType")) {
			return ATTRIBUTE_PRICING_TYPE;
		}
		else if(attributeName.equals("supplyPath")) {
			return ATTRIBUTE_SUPPLY_PATH;
		}
		else if(attributeName.equals("loadingDate")) {
			return ATTRIBUTE_LOADING_DATE;
		}
		else if(attributeName.equals("buyerCompanyId")) {
			return ATTRIBUTE_BUYER_COMPANY_ID;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, DbuyContractBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(DbuyContractBean dbuyContractBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("itemId", "SearchCriterion.EQUALS",
			"" + dbuyContractBean.getItemId());

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


	public int delete(DbuyContractBean dbuyContractBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("itemId", "SearchCriterion.EQUALS",
			"" + dbuyContractBean.getItemId());

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
	//insert
	public int insert(DbuyContractBean dbuyContractBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(dbuyContractBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(DbuyContractBean dbuyContractBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_ITEM_ID + "," +
			ATTRIBUTE_INVENTORY_GROUP + "," +
			ATTRIBUTE_SHIP_TO_COMPANY_ID + "," +
			ATTRIBUTE_SHIP_TO_LOCATION_ID + "," +
			ATTRIBUTE_PRIORITY + "," +
			ATTRIBUTE_DBUY_CONTRACT_ID + "," +
			ATTRIBUTE_SUPPLIER + "," +
			ATTRIBUTE_CARRIER + "," +
			ATTRIBUTE_BUYER + "," +
			ATTRIBUTE_SOURCER + "," +
			ATTRIBUTE_PAYMENT_TERMS + "," +
			ATTRIBUTE_STATUS + "," +
			ATTRIBUTE_FREIGHT_ON_BOARD + "," +
			ATTRIBUTE_MULTIPLE_OF + "," +
			ATTRIBUTE_SUPPLIER_PART_NO + "," +
			ATTRIBUTE_SUPPLIER_CONTACT_ID + "," +
			ATTRIBUTE_REMAINING_SHELF_LIFE_PERCENT + "," +
			ATTRIBUTE_CRITICAL_ORDER_CARRIER + "," +
			ATTRIBUTE_REF_CLIENT_PART_NO + "," +
			ATTRIBUTE_REF_CLIENT_PO_NO + "," +
			ATTRIBUTE_ROUND_TO_MULTIPLE + "," +
			ATTRIBUTE_CONSIGNMENT + "," +
			ATTRIBUTE_LEAD_TIME_DAYS + "," +
			ATTRIBUTE_DEFAULT_SHIPMENT_ORIGIN_STATE + "," +
			ATTRIBUTE_TRANSIT_TIME_IN_DAYS + "," +
			ATTRIBUTE_CONTRACT_REFERENCE_COMMENTS + "," +
			ATTRIBUTE_LOADING_COMMENTS + "," +
			ATTRIBUTE_PRICING_TYPE + "," +
			ATTRIBUTE_SUPPLY_PATH + "," +
			ATTRIBUTE_LOADING_DATE + "," +
			ATTRIBUTE_BUYER_COMPANY_ID + ")" +
                        " values (" +
			StringHandler.nullIfZero(dbuyContractBean.getItemId()) + "," +
			SqlHandler.delimitString(dbuyContractBean.getInventoryGroup()) + "," +
			SqlHandler.delimitString(dbuyContractBean.getShipToCompanyId()) + "," +
			SqlHandler.delimitString(dbuyContractBean.getShipToLocationId()) + "," +
			StringHandler.nullIfZero(dbuyContractBean.getPriority()) + "," +
			StringHandler.nullIfZero(dbuyContractBean.getDbuyContractId()) + "," +
			SqlHandler.delimitString(dbuyContractBean.getSupplier()) + "," +
			SqlHandler.delimitString(dbuyContractBean.getCarrier()) + "," +
			StringHandler.nullIfZero(dbuyContractBean.getBuyer()) + "," +
			StringHandler.nullIfZero(dbuyContractBean.getSourcer()) + "," +
			SqlHandler.delimitString(dbuyContractBean.getPaymentTerms()) + "," +
			SqlHandler.delimitString(dbuyContractBean.getStatus()) + "," +
			SqlHandler.delimitString(dbuyContractBean.getFreightOnBoard()) + "," +
			StringHandler.nullIfZero(dbuyContractBean.getMultipleOf()) + "," +
			SqlHandler.delimitString(dbuyContractBean.getSupplierPartNo()) + "," +
			StringHandler.nullIfZero(dbuyContractBean.getSupplierContactId()) + "," +
			StringHandler.nullIfZero(dbuyContractBean.getRemainingShelfLifePercent()) + "," +
			SqlHandler.delimitString(dbuyContractBean.getCriticalOrderCarrier()) + "," +
			SqlHandler.delimitString(dbuyContractBean.getRefClientPartNo()) + "," +
			SqlHandler.delimitString(dbuyContractBean.getRefClientPoNo()) + "," +
			SqlHandler.delimitString(dbuyContractBean.getRoundToMultiple()) + "," +
			SqlHandler.delimitString(dbuyContractBean.getConsignment()) + "," +
			StringHandler.nullIfZero(dbuyContractBean.getLeadTimeDays()) + "," +
			SqlHandler.delimitString(dbuyContractBean.getDefaultShipmentOriginState()) + "," +
			StringHandler.nullIfZero(dbuyContractBean.getTransitTimeInDays()) + "," +
			SqlHandler.delimitString(dbuyContractBean.getContractReferenceComments()) + "," +
			SqlHandler.delimitString(dbuyContractBean.getLoadingComments()) + "," +
			SqlHandler.delimitString(dbuyContractBean.getPricingType()) + "," +
			SqlHandler.delimitString(dbuyContractBean.getSupplyPath()) + "," +
			DateHandler.getOracleToDateFunction(dbuyContractBean.getLoadingDate()) + "," +
			SqlHandler.delimitString(dbuyContractBean.getBuyerCompanyId()) + ")";

		return sqlManager.update(conn, query);
	}

	//update
	public int update(DbuyContractBean dbuyContractBean, BigDecimal priority)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(dbuyContractBean, connection, priority);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(DbuyContractBean dbuyContractBean, Connection conn, BigDecimal priority)
		throws BaseException {

                Date now = new Date();                
                String date = DateHandler.formatOracleDate(now);
                
		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_PRIORITY + "=" + 
				StringHandler.nullIfZero(dbuyContractBean.getPriority()) + "," +
			ATTRIBUTE_CARRIER + "=" + 
				SqlHandler.delimitString(dbuyContractBean.getCarrier()) + "," +
			ATTRIBUTE_BUYER + "=" + 
				StringHandler.nullIfZero(dbuyContractBean.getBuyer()) + "," +
			ATTRIBUTE_SOURCER + "=" + 
				StringHandler.nullIfZero(dbuyContractBean.getSourcer()) + "," +
			ATTRIBUTE_PAYMENT_TERMS + "=" + 
				SqlHandler.delimitString(dbuyContractBean.getPaymentTerms()) + "," +
			ATTRIBUTE_STATUS + "=" + 
				SqlHandler.delimitString(dbuyContractBean.getStatus()) + "," +
			ATTRIBUTE_FREIGHT_ON_BOARD + "=" + 
				SqlHandler.delimitString(dbuyContractBean.getFreightOnBoard()) + "," +
			ATTRIBUTE_MULTIPLE_OF + "=" + 
				StringHandler.nullIfZero(dbuyContractBean.getMultipleOf()) + "," +
			ATTRIBUTE_SUPPLIER_PART_NO + "=" + 
				SqlHandler.delimitString(dbuyContractBean.getSupplierPartNo()) + "," +
			ATTRIBUTE_SUPPLIER_CONTACT_ID + "=" + 
				StringHandler.nullIfZero(dbuyContractBean.getSupplierContactId()) + "," +
			ATTRIBUTE_REMAINING_SHELF_LIFE_PERCENT + "=" + 
				StringHandler.nullIfZero(dbuyContractBean.getRemainingShelfLifePercent()) + "," +
			ATTRIBUTE_CRITICAL_ORDER_CARRIER + "=" + 
				SqlHandler.delimitString(dbuyContractBean.getCriticalOrderCarrier()) + "," +
			ATTRIBUTE_REF_CLIENT_PART_NO + "=" + 
				SqlHandler.delimitString(dbuyContractBean.getRefClientPartNo()) + "," +
			ATTRIBUTE_REF_CLIENT_PO_NO + "=" + 
				SqlHandler.delimitString(dbuyContractBean.getRefClientPoNo()) + "," +
			ATTRIBUTE_ROUND_TO_MULTIPLE + "=" + 
				SqlHandler.delimitString(dbuyContractBean.getRoundToMultiple()) + "," +
			ATTRIBUTE_CONSIGNMENT + "=" + 
				SqlHandler.delimitString(dbuyContractBean.getConsignment()) + "," +
			ATTRIBUTE_LEAD_TIME_DAYS + "=" + 
				StringHandler.nullIfZero(dbuyContractBean.getLeadTimeDays()) + "," +
			ATTRIBUTE_DEFAULT_SHIPMENT_ORIGIN_STATE + "=" + 
				SqlHandler.delimitString(dbuyContractBean.getDefaultShipmentOriginState()) + "," +
			ATTRIBUTE_TRANSIT_TIME_IN_DAYS + "=" + 
				StringHandler.nullIfZero(dbuyContractBean.getTransitTimeInDays()) + "," +
			ATTRIBUTE_CONTRACT_REFERENCE_COMMENTS + "=" + 
				SqlHandler.delimitString(dbuyContractBean.getContractReferenceComments() + "| Web Update on "+date) + "," +
			ATTRIBUTE_PRICING_TYPE + "=" + 
				SqlHandler.delimitString(dbuyContractBean.getPricingType()) + "," +
			ATTRIBUTE_SUPPLY_PATH + "=" + 
				SqlHandler.delimitString(dbuyContractBean.getSupplyPath()) + " " +
			"where " + ATTRIBUTE_ITEM_ID + "=" +
				StringHandler.nullIfZero(dbuyContractBean.getItemId()) +
			" and " + ATTRIBUTE_INVENTORY_GROUP + "=" +
				SqlHandler.delimitString(dbuyContractBean.getInventoryGroup()) +
			" and " + ATTRIBUTE_SHIP_TO_COMPANY_ID + "=" + 
				SqlHandler.delimitString(dbuyContractBean.getShipToCompanyId()) + 
			" and " + ATTRIBUTE_SHIP_TO_LOCATION_ID + "=" + 
				SqlHandler.delimitString(dbuyContractBean.getShipToLocationId()) + 
                        " and " + ATTRIBUTE_PRIORITY + "=" + 
				StringHandler.nullIfZero(priority) +  
			" and " + ATTRIBUTE_SUPPLIER + "=" +
				SqlHandler.delimitString(dbuyContractBean.getSupplier());
                                

		return new SqlManager().update(conn, query);
	}


	//select
	public Collection select(SearchCriteria criteria)
		throws BaseException {

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
	public Collection select(SearchCriteria criteria, Connection conn)
		throws BaseException {

		Collection dbuyContractBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			DbuyContractBean dbuyContractBean = new DbuyContractBean();
			load(dataSetRow, dbuyContractBean);
			dbuyContractBeanColl.add(dbuyContractBean);
		}

		return dbuyContractBeanColl;
	}
}