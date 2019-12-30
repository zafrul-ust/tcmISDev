package com.tcmis.internal.hub.factory;


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
import com.tcmis.internal.hub.beans.InventoryCountScanStageBean;


/******************************************************************************
 * CLASSNAME: InventoryCountScanStageBeanFactory <br>
 * @version: 1.0, Jul 26, 2006 <br>
 *****************************************************************************/


public class InventoryCountScanStageBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_UPLOAD_ID = "UPLOAD_ID";
	public String ATTRIBUTE_RECEIPT_ID = "RECEIPT_ID";
	public String ATTRIBUTE_SCAN_DATETIME = "SCAN_DATETIME";
	public String ATTRIBUTE_COUNTER_ID = "COUNTER_ID";
	public String ATTRIBUTE_SCANNED_BIN = "SCANNED_BIN";
	public String ATTRIBUTE_COUNTED_QUANTITY = "COUNTED_QUANTITY";
	public String ATTRIBUTE_UPLOAD_DATETIME = "UPLOAD_DATETIME";
	public String ATTRIBUTE_HUB = "HUB";

	//table name
	public String TABLE = "INVENTORY_COUNT_SCAN_STAGE";


	//constructor
	public InventoryCountScanStageBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("uploadId")) {
			return ATTRIBUTE_UPLOAD_ID;
		}
		else if(attributeName.equals("receiptId")) {
			return ATTRIBUTE_RECEIPT_ID;
		}
		else if(attributeName.equals("scanDatetime")) {
			return ATTRIBUTE_SCAN_DATETIME;
		}
		else if(attributeName.equals("counterId")) {
			return ATTRIBUTE_COUNTER_ID;
		}
		else if(attributeName.equals("scannedBin")) {
			return ATTRIBUTE_SCANNED_BIN;
		}
		else if(attributeName.equals("countedQuantity")) {
			return ATTRIBUTE_COUNTED_QUANTITY;
		}
		else if(attributeName.equals("uploadDatetime")) {
			return ATTRIBUTE_UPLOAD_DATETIME;
		}
		else if(attributeName.equals("hub")) {
			return ATTRIBUTE_HUB;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, InventoryCountScanStageBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(InventoryCountScanStageBean inventoryCountScanStageBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("uploadId", "SearchCriterion.EQUALS",
			"" + inventoryCountScanStageBean.getUploadId());

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


	public int delete(InventoryCountScanStageBean inventoryCountScanStageBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("uploadId", "SearchCriterion.EQUALS",
			"" + inventoryCountScanStageBean.getUploadId());

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


	 /*Getting the next upload Id*/
	 public BigDecimal getUploadId()
		throws BaseException {
		Connection connection = null;
		int result = 0;
		try {
		 connection = getDbManager().getConnection();
		 result = getDbManager().getOracleSequence("inventory_count_upload_id");
		}
		finally {
		 this.getDbManager().returnConnection(connection);
		}
		return new BigDecimal(result);
	 }

  //you need to verify the primary key(s) before uncommenting this
	//insert
	public int insert(InventoryCountScanStageBean inventoryCountScanStageBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(inventoryCountScanStageBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(InventoryCountScanStageBean inventoryCountScanStageBean, Connection conn)
		throws BaseException {
		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
			ATTRIBUTE_UPLOAD_ID + "," +
			ATTRIBUTE_RECEIPT_ID + "," +
			ATTRIBUTE_SCAN_DATETIME + "," +
			ATTRIBUTE_COUNTER_ID + "," +
			ATTRIBUTE_SCANNED_BIN + "," +
			ATTRIBUTE_COUNTED_QUANTITY + "," +
			//ATTRIBUTE_UPLOAD_DATETIME + "," +
			ATTRIBUTE_HUB + ")" +
			" values (" +
			inventoryCountScanStageBean.getUploadId() + "," +
			inventoryCountScanStageBean.getReceiptId() + "," +
			DateHandler.getOracleToDateFunction(inventoryCountScanStageBean.getScanDatetime()) + "," +
			inventoryCountScanStageBean.getCounterId() + "," +
			SqlHandler.delimitString(inventoryCountScanStageBean.getScannedBin()) + "," +
			inventoryCountScanStageBean.getCountedQuantity() + "," +
			//DateHandler.getOracleToDateFunction(inventoryCountScanStageBean.getUploadDatetime()) + "," +
			SqlHandler.delimitString(inventoryCountScanStageBean.getHub()) + ")";

		return sqlManager.update(conn, query);
	}

	 public String consolidateInventoryScan(BigDecimal uploadId) throws
		 Exception {
		 Connection connection = this.getDbManager().getConnection();
		 Collection cin = new Vector();
		 String errorMessage= "";
		 cin.add(uploadId);

		 try {
			this.getDbManager().doProcedure("P_INVENTORY_COUNT_SCAN", cin);
		 }
		 catch (Exception ex) {
			errorMessage = ex.getMessage();
			throw ex;
		 }

		 this.getDbManager().returnConnection(connection);
		 return errorMessage;
		}

/*
	//update
	public int update(InventoryCountScanStageBean inventoryCountScanStageBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(inventoryCountScanStageBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(InventoryCountScanStageBean inventoryCountScanStageBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_UPLOAD_ID + "=" +
				StringHandler.nullIfZero(inventoryCountScanStageBean.getUploadId()) + "," +
			ATTRIBUTE_RECEIPT_ID + "=" +
				StringHandler.nullIfZero(inventoryCountScanStageBean.getReceiptId()) + "," +
			ATTRIBUTE_SCAN_DATETIME + "=" +
				DateHandler.getOracleToDateFunction(inventoryCountScanStageBean.getScanDatetime()) + "," +
			ATTRIBUTE_COUNTER_ID + "=" +
				StringHandler.nullIfZero(inventoryCountScanStageBean.getCounterId()) + "," +
			ATTRIBUTE_SCANNED_BIN + "=" +
				SqlHandler.delimitString(inventoryCountScanStageBean.getScannedBin()) + "," +
			ATTRIBUTE_COUNTED_QUANTITY + "=" +
				StringHandler.nullIfZero(inventoryCountScanStageBean.getCountedQuantity()) + "," +
			ATTRIBUTE_UPLOAD_DATETIME + "=" +
				DateHandler.getOracleToDateFunction(inventoryCountScanStageBean.getUploadDatetime()) + "," +
			ATTRIBUTE_HUB + "=" +
				SqlHandler.delimitString(inventoryCountScanStageBean.getHub()) + " " +
			"where " + ATTRIBUTE_UPLOAD_ID + "=" +
				inventoryCountScanStageBean.getUploadId();

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

		Collection inventoryCountScanStageBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			InventoryCountScanStageBean inventoryCountScanStageBean = new InventoryCountScanStageBean();
			load(dataSetRow, inventoryCountScanStageBean);
			inventoryCountScanStageBeanColl.add(inventoryCountScanStageBean);
		}

		return inventoryCountScanStageBeanColl;
	}
}