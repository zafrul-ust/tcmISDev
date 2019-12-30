package com.tcmis.client.purchasing.factory;


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
import com.tcmis.client.purchasing.beans.InventoryToReceiveViewBean;


/******************************************************************************
 * CLASSNAME: InventoryToReceiveViewBeanFactory <br>
 * @version: 1.0, Apr 8, 2008 <br>
 *****************************************************************************/


public class InventoryToReceiveViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_USER_ID = "USER_ID";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_READ_ONLY = "READ_ONLY";
	public String ATTRIBUTE_OWNER_COMPANY_ID = "OWNER_COMPANY_ID";
	public String ATTRIBUTE_BUYER_ID = "BUYER_ID";
	public String ATTRIBUTE_BUYER_NAME = "BUYER_NAME";
	public String ATTRIBUTE_PO_NUMBER = "PO_NUMBER";
	public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
	public String ATTRIBUTE_INVENTORY_GROUP_NAME = "INVENTORY_GROUP_NAME";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_ITEM_DESC = "ITEM_DESC";
	public String ATTRIBUTE_QUANTITY_TO_RECEIVE = "QUANTITY_TO_RECEIVE";
	public String ATTRIBUTE_TOTAL_QUANTITY_RECEIVED = "TOTAL_QUANTITY_RECEIVED";
	public String ATTRIBUTE_PACKAGING = "PACKAGING";
	public String ATTRIBUTE_SUPPLIER_NAME = "SUPPLIER_NAME";
	public String ATTRIBUTE_EXPECTED_DELIVERY_DATE = "EXPECTED_DELIVERY_DATE";
	public String ATTRIBUTE_DATE_INSERTED = "DATE_INSERTED";
	public String ATTRIBUTE_DOC_NUM = "DOC_NUM";

	//table name
	public String TABLE = "INVENTORY_TO_RECEIVE_VIEW";


	//constructor
	public InventoryToReceiveViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("userId")) {
			return ATTRIBUTE_USER_ID;
		}
		else if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("readOnly")) {
			return ATTRIBUTE_READ_ONLY;
		}
		else if(attributeName.equals("ownerCompanyId")) {
			return ATTRIBUTE_OWNER_COMPANY_ID;
		}
		else if(attributeName.equals("buyerId")) {
			return ATTRIBUTE_BUYER_ID;
		}
		else if(attributeName.equals("buyerName")) {
			return ATTRIBUTE_BUYER_NAME;
		}
		else if(attributeName.equals("poNumber")) {
			return ATTRIBUTE_PO_NUMBER;
		}
		else if(attributeName.equals("catPartNo")) {
			return ATTRIBUTE_CAT_PART_NO;
		}
		else if(attributeName.equals("inventoryGroupName")) {
			return ATTRIBUTE_INVENTORY_GROUP_NAME;
		}
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("itemDesc")) {
			return ATTRIBUTE_ITEM_DESC;
		}
		else if(attributeName.equals("quantityToReceive")) {
			return ATTRIBUTE_QUANTITY_TO_RECEIVE;
		}
		else if(attributeName.equals("totalQuantityReceived")) {
			return ATTRIBUTE_TOTAL_QUANTITY_RECEIVED;
		}
		else if(attributeName.equals("packaging")) {
			return ATTRIBUTE_PACKAGING;
		}
		else if(attributeName.equals("supplierName")) {
			return ATTRIBUTE_SUPPLIER_NAME;
		}
		else if(attributeName.equals("expectedDeliveryDate")) {
			return ATTRIBUTE_EXPECTED_DELIVERY_DATE;
		}
		else if(attributeName.equals("dateInserted")) {
			return ATTRIBUTE_DATE_INSERTED;
		}
		else if(attributeName.equals("docNum")) {
			return ATTRIBUTE_DOC_NUM;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, InventoryToReceiveViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(InventoryToReceiveViewBean inventoryToReceiveViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("userId", "SearchCriterion.EQUALS",
			"" + inventoryToReceiveViewBean.getUserId());

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


	public int delete(InventoryToReceiveViewBean inventoryToReceiveViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("userId", "SearchCriterion.EQUALS",
			"" + inventoryToReceiveViewBean.getUserId());

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
	public int insert(InventoryToReceiveViewBean inventoryToReceiveViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(inventoryToReceiveViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(InventoryToReceiveViewBean inventoryToReceiveViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_USER_ID + "," +
			ATTRIBUTE_INVENTORY_GROUP + "," +
			ATTRIBUTE_BUYER_COMPANY_ID + "," +
			ATTRIBUTE_BUYER_ID + "," +
			ATTRIBUTE_BUYER_NAME + "," +
			ATTRIBUTE_PO_NUMBER + "," +
			ATTRIBUTE_INVENTORY_GROUP_NAME + "," +
			ATTRIBUTE_ITEM_ID + "," +
			ATTRIBUTE_ITEM_DESC + "," +
			ATTRIBUTE_QUANTITY_TO_RECEIVE + "," +
			ATTRIBUTE_TOTAL_QUANTITY_RECEIVED + "," +
			ATTRIBUTE_PACKAGING + "," +
			ATTRIBUTE_SUPPLIER_NAME + "," +
			ATTRIBUTE_EXPECTED_DELIVERY_DATE + "," +
			ATTRIBUTE_DATE_INSERTED + "," +
			ATTRIBUTE_DOC_NUM + ")" +
			" values (" +
			inventoryToReceiveViewBean.getUserId() + "," +
			SqlHandler.delimitString(inventoryToReceiveViewBean.getInventoryGroup()) + "," +
			SqlHandler.delimitString(inventoryToReceiveViewBean.getBuyerCompanyId()) + "," +
			inventoryToReceiveViewBean.getBuyerId() + "," +
			SqlHandler.delimitString(inventoryToReceiveViewBean.getBuyerName()) + "," +
			SqlHandler.delimitString(inventoryToReceiveViewBean.getPoNumber()) + "," +
			SqlHandler.delimitString(inventoryToReceiveViewBean.getInventoryGroupName()) + "," +
			inventoryToReceiveViewBean.getItemId() + "," +
			SqlHandler.delimitString(inventoryToReceiveViewBean.getItemDesc()) + "," +
			inventoryToReceiveViewBean.getQuantityToReceive() + "," +
			inventoryToReceiveViewBean.getTotalQuantityReceived() + "," +
			SqlHandler.delimitString(inventoryToReceiveViewBean.getPackaging()) + "," +
			SqlHandler.delimitString(inventoryToReceiveViewBean.getSupplierName()) + "," +
			DateHandler.getOracleToDateFunction(inventoryToReceiveViewBean.getExpectedDeliveryDate()) + "," +
			DateHandler.getOracleToDateFunction(inventoryToReceiveViewBean.getDateInserted()) + "," +
			inventoryToReceiveViewBean.getDocNum() + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(InventoryToReceiveViewBean inventoryToReceiveViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(inventoryToReceiveViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(InventoryToReceiveViewBean inventoryToReceiveViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_USER_ID + "=" + 
				StringHandler.nullIfZero(inventoryToReceiveViewBean.getUserId()) + "," +
			ATTRIBUTE_INVENTORY_GROUP + "=" + 
				SqlHandler.delimitString(inventoryToReceiveViewBean.getInventoryGroup()) + "," +
			ATTRIBUTE_BUYER_COMPANY_ID + "=" + 
				SqlHandler.delimitString(inventoryToReceiveViewBean.getBuyerCompanyId()) + "," +
			ATTRIBUTE_BUYER_ID + "=" + 
				StringHandler.nullIfZero(inventoryToReceiveViewBean.getBuyerId()) + "," +
			ATTRIBUTE_BUYER_NAME + "=" + 
				SqlHandler.delimitString(inventoryToReceiveViewBean.getBuyerName()) + "," +
			ATTRIBUTE_PO_NUMBER + "=" + 
				SqlHandler.delimitString(inventoryToReceiveViewBean.getPoNumber()) + "," +
			ATTRIBUTE_INVENTORY_GROUP_NAME + "=" + 
				SqlHandler.delimitString(inventoryToReceiveViewBean.getInventoryGroupName()) + "," +
			ATTRIBUTE_ITEM_ID + "=" + 
				StringHandler.nullIfZero(inventoryToReceiveViewBean.getItemId()) + "," +
			ATTRIBUTE_ITEM_DESC + "=" + 
				SqlHandler.delimitString(inventoryToReceiveViewBean.getItemDesc()) + "," +
			ATTRIBUTE_QUANTITY_TO_RECEIVE + "=" + 
				StringHandler.nullIfZero(inventoryToReceiveViewBean.getQuantityToReceive()) + "," +
			ATTRIBUTE_TOTAL_QUANTITY_RECEIVED + "=" + 
				StringHandler.nullIfZero(inventoryToReceiveViewBean.getTotalQuantityReceived()) + "," +
			ATTRIBUTE_PACKAGING + "=" + 
				SqlHandler.delimitString(inventoryToReceiveViewBean.getPackaging()) + "," +
			ATTRIBUTE_SUPPLIER_NAME + "=" + 
				SqlHandler.delimitString(inventoryToReceiveViewBean.getSupplierName()) + "," +
			ATTRIBUTE_EXPECTED_DELIVERY_DATE + "=" + 
				DateHandler.getOracleToDateFunction(inventoryToReceiveViewBean.getExpectedDeliveryDate()) + "," +
			ATTRIBUTE_DATE_INSERTED + "=" + 
				DateHandler.getOracleToDateFunction(inventoryToReceiveViewBean.getDateInserted()) + "," +
			ATTRIBUTE_DOC_NUM + "=" + 
				StringHandler.nullIfZero(inventoryToReceiveViewBean.getDocNum()) + " " +
			"where " + ATTRIBUTE_USER_ID + "=" +
				inventoryToReceiveViewBean.getUserId();

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

		Collection inventoryToReceiveViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			InventoryToReceiveViewBean inventoryToReceiveViewBean = new InventoryToReceiveViewBean();
			load(dataSetRow, inventoryToReceiveViewBean);
			inventoryToReceiveViewBeanColl.add(inventoryToReceiveViewBean);
		}

		return inventoryToReceiveViewBeanColl;
	}

	public Boolean hasPermission(SearchCriteria criteria)
		throws BaseException {

		Connection connection = null;
		Boolean b = new Boolean(false);
		try {
			connection = this.getDbManager().getConnection();
			b = hasPermission(criteria, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return b;
	}
	public Boolean hasPermission(SearchCriteria criteria, Connection conn)
		throws BaseException {

		String query = "select read_only from " + TABLE + " " +getWhereClause(criteria)+
								   " and read_only = 'N'";
		DataSet dataSet = new SqlManager().select(conn, query);
		return (new Boolean(dataSet.size() > 0));
	}
}