package com.tcmis.internal.hub.factory;


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
import com.tcmis.internal.hub.beans.InventoryTransferDetailViewBean;


/******************************************************************************
 * CLASSNAME: InventoryTransferDetailViewBeanFactory <br>
 * @version: 1.0, Nov 30, 2006 <br>
 *****************************************************************************/


public class InventoryTransferDetailViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_TRANSFER_REQUEST_ID = "TRANSFER_REQUEST_ID";
	public String ATTRIBUTE_DOC_TYPE = "DOC_TYPE";
	public String ATTRIBUTE_RECEIPT_ID = "RECEIPT_ID";
	public String ATTRIBUTE_ORIGINAL_RECEIPT_ID = "ORIGINAL_RECEIPT_ID";
	public String ATTRIBUTE_QUANTITY = "QUANTITY";
	public String ATTRIBUTE_MFG_LOT = "MFG_LOT";
	public String ATTRIBUTE_BIN = "BIN";
	public String ATTRIBUTE_DATE_DELIVERED = "DATE_DELIVERED";
	public String ATTRIBUTE_TRANSACTION_DATE = "TRANSACTION_DATE";
	public String ATTRIBUTE_QC_DATE = "QC_DATE";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_HUB = "HUB";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";

	//table name
	public String TABLE = "INVENTORY_TRANSFER_DETAIL_VIEW";


	//constructor
	public InventoryTransferDetailViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("transferRequestId")) {
			return ATTRIBUTE_TRANSFER_REQUEST_ID;
		}
		else if(attributeName.equals("docType")) {
			return ATTRIBUTE_DOC_TYPE;
		}
		else if(attributeName.equals("receiptId")) {
			return ATTRIBUTE_RECEIPT_ID;
		}
		else if(attributeName.equals("originalReceiptId")) {
			return ATTRIBUTE_ORIGINAL_RECEIPT_ID;
		}
		else if(attributeName.equals("quantity")) {
			return ATTRIBUTE_QUANTITY;
		}
		else if(attributeName.equals("mfgLot")) {
			return ATTRIBUTE_MFG_LOT;
		}
		else if(attributeName.equals("bin")) {
			return ATTRIBUTE_BIN;
		}
		else if(attributeName.equals("dateDelivered")) {
			return ATTRIBUTE_DATE_DELIVERED;
		}
		else if(attributeName.equals("transactionDate")) {
			return ATTRIBUTE_TRANSACTION_DATE;
		}
		else if(attributeName.equals("qcDate")) {
			return ATTRIBUTE_QC_DATE;
		}
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("hub")) {
			return ATTRIBUTE_HUB;
		}
		else if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, InventoryTransferDetailViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(InventoryTransferDetailViewBean inventoryTransferDetailViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("transferRequestId", "SearchCriterion.EQUALS",
			"" + inventoryTransferDetailViewBean.getTransferRequestId());

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


	public int delete(InventoryTransferDetailViewBean inventoryTransferDetailViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("transferRequestId", "SearchCriterion.EQUALS",
			"" + inventoryTransferDetailViewBean.getTransferRequestId());

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
	public int insert(InventoryTransferDetailViewBean inventoryTransferDetailViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(inventoryTransferDetailViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(InventoryTransferDetailViewBean inventoryTransferDetailViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_TRANSFER_REQUEST_ID + "," +
			ATTRIBUTE_DOC_TYPE + "," +
			ATTRIBUTE_RECEIPT_ID + "," +
			ATTRIBUTE_ORIGINAL_RECEIPT_ID + "," +
			ATTRIBUTE_QUANTITY + "," +
			ATTRIBUTE_MFG_LOT + "," +
			ATTRIBUTE_BIN + "," +
			ATTRIBUTE_DATE_DELIVERED + "," +
			ATTRIBUTE_TRANSACTION_DATE + "," +
			ATTRIBUTE_QC_DATE + "," +
			ATTRIBUTE_ITEM_ID + "," +
			ATTRIBUTE_HUB + "," +
			ATTRIBUTE_INVENTORY_GROUP + ")" +
 values (
			StringHandler.nullIfZero(inventoryTransferDetailViewBean.getTransferRequestId()) + "," +
			SqlHandler.delimitString(inventoryTransferDetailViewBean.getDocType()) + "," +
			StringHandler.nullIfZero(inventoryTransferDetailViewBean.getReceiptId()) + "," +
			StringHandler.nullIfZero(inventoryTransferDetailViewBean.getOriginalReceiptId()) + "," +
			StringHandler.nullIfZero(inventoryTransferDetailViewBean.getQuantity()) + "," +
			SqlHandler.delimitString(inventoryTransferDetailViewBean.getMfgLot()) + "," +
			SqlHandler.delimitString(inventoryTransferDetailViewBean.getBin()) + "," +
			DateHandler.getOracleToDateFunction(inventoryTransferDetailViewBean.getDateDelivered()) + "," +
			DateHandler.getOracleToDateFunction(inventoryTransferDetailViewBean.getTransactionDate()) + "," +
			DateHandler.getOracleToDateFunction(inventoryTransferDetailViewBean.getQcDate()) + "," +
			StringHandler.nullIfZero(inventoryTransferDetailViewBean.getItemId()) + "," +
			SqlHandler.delimitString(inventoryTransferDetailViewBean.getHub()) + "," +
			SqlHandler.delimitString(inventoryTransferDetailViewBean.getInventoryGroup()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(InventoryTransferDetailViewBean inventoryTransferDetailViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(inventoryTransferDetailViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(InventoryTransferDetailViewBean inventoryTransferDetailViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_TRANSFER_REQUEST_ID + "=" + 
				StringHandler.nullIfZero(inventoryTransferDetailViewBean.getTransferRequestId()) + "," +
			ATTRIBUTE_DOC_TYPE + "=" + 
				SqlHandler.delimitString(inventoryTransferDetailViewBean.getDocType()) + "," +
			ATTRIBUTE_RECEIPT_ID + "=" + 
				StringHandler.nullIfZero(inventoryTransferDetailViewBean.getReceiptId()) + "," +
			ATTRIBUTE_ORIGINAL_RECEIPT_ID + "=" + 
				StringHandler.nullIfZero(inventoryTransferDetailViewBean.getOriginalReceiptId()) + "," +
			ATTRIBUTE_QUANTITY + "=" + 
				StringHandler.nullIfZero(inventoryTransferDetailViewBean.getQuantity()) + "," +
			ATTRIBUTE_MFG_LOT + "=" + 
				SqlHandler.delimitString(inventoryTransferDetailViewBean.getMfgLot()) + "," +
			ATTRIBUTE_BIN + "=" + 
				SqlHandler.delimitString(inventoryTransferDetailViewBean.getBin()) + "," +
			ATTRIBUTE_DATE_DELIVERED + "=" + 
				DateHandler.getOracleToDateFunction(inventoryTransferDetailViewBean.getDateDelivered()) + "," +
			ATTRIBUTE_TRANSACTION_DATE + "=" + 
				DateHandler.getOracleToDateFunction(inventoryTransferDetailViewBean.getTransactionDate()) + "," +
			ATTRIBUTE_QC_DATE + "=" + 
				DateHandler.getOracleToDateFunction(inventoryTransferDetailViewBean.getQcDate()) + "," +
			ATTRIBUTE_ITEM_ID + "=" + 
				StringHandler.nullIfZero(inventoryTransferDetailViewBean.getItemId()) + "," +
			ATTRIBUTE_HUB + "=" + 
				SqlHandler.delimitString(inventoryTransferDetailViewBean.getHub()) + "," +
			ATTRIBUTE_INVENTORY_GROUP + "=" + 
				SqlHandler.delimitString(inventoryTransferDetailViewBean.getInventoryGroup()) + " " +
			"where " + ATTRIBUTE_TRANSFER_REQUEST_ID + "=" +
				StringHandler.nullIfZero(inventoryTransferDetailViewBean.getTransferRequestId());

		return new SqlManager().update(conn, query);
	}
*/

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

		Collection inventoryTransferDetailViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			InventoryTransferDetailViewBean inventoryTransferDetailViewBean = new InventoryTransferDetailViewBean();
			load(dataSetRow, inventoryTransferDetailViewBean);
			inventoryTransferDetailViewBeanColl.add(inventoryTransferDetailViewBean);
		}

		return inventoryTransferDetailViewBeanColl;
	}

  public Collection selectOrdered(SearchCriteria criteria) throws BaseException {
    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = selectOrdered(criteria, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection selectOrdered(SearchCriteria criteria, Connection conn) throws BaseException {
    Collection inventoryTransferDetailViewBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria) + " " +
        "order by " + ATTRIBUTE_TRANSACTION_DATE + " asc";
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InventoryTransferDetailViewBean inventoryTransferDetailViewBean = new
          InventoryTransferDetailViewBean();
      load(dataSetRow, inventoryTransferDetailViewBean);
      inventoryTransferDetailViewBeanColl.add(inventoryTransferDetailViewBean);
    }
    return inventoryTransferDetailViewBeanColl;
  }
}
