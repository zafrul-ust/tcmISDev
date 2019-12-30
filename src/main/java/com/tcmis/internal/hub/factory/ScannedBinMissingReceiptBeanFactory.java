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
import com.tcmis.internal.hub.beans.ScannedBinMissingReceiptBean;


/******************************************************************************
 * CLASSNAME: ScannedBinMissingReceiptBeanFactory <br>
 * @version: 1.0, Aug 3, 2005 <br>
 *****************************************************************************/


public class ScannedBinMissingReceiptBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_UPLOAD_ID = "UPLOAD_ID";
	public String ATTRIBUTE_HUB = "HUB";
	public String ATTRIBUTE_BIN = "BIN";
	public String ATTRIBUTE_RECEIPT_ID = "RECEIPT_ID";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_ITEM_DESC = "ITEM_DESC";
	public String ATTRIBUTE_CURRENT_EXPECTED_QTY = "CURRENT_EXPECTED_QTY";
  public String ATTRIBUTE_LOT_STATUS = "LOT_STATUS";

	//table name
	public String TABLE = "SCANNED_BIN_MISSING_RECEIPT";


	//constructor
	public ScannedBinMissingReceiptBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("uploadId")) {
			return ATTRIBUTE_UPLOAD_ID;
		}
		else if(attributeName.equals("hub")) {
			return ATTRIBUTE_HUB;
		}
		else if(attributeName.equals("bin")) {
			return ATTRIBUTE_BIN;
		}
		else if(attributeName.equals("receiptId")) {
			return ATTRIBUTE_RECEIPT_ID;
		}
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("itemDesc")) {
			return ATTRIBUTE_ITEM_DESC;
		}
		else if(attributeName.equals("currentExpectedQty")) {
			return ATTRIBUTE_CURRENT_EXPECTED_QTY;
		}
		else if(attributeName.equals("lotStatus")) {
		 return ATTRIBUTE_LOT_STATUS;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, ScannedBinMissingReceiptBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(ScannedBinMissingReceiptBean scannedBinMissingReceiptBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("uploadId", "SearchCriterion.EQUALS",
			"" + scannedBinMissingReceiptBean.getUploadId());

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


	public int delete(ScannedBinMissingReceiptBean scannedBinMissingReceiptBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("uploadId", "SearchCriterion.EQUALS",
			"" + scannedBinMissingReceiptBean.getUploadId());

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
	public int insert(ScannedBinMissingReceiptBean scannedBinMissingReceiptBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(scannedBinMissingReceiptBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(ScannedBinMissingReceiptBean scannedBinMissingReceiptBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
			ATTRIBUTE_UPLOAD_ID + "," +
			ATTRIBUTE_HUB + "," +
			ATTRIBUTE_BIN + "," +
			ATTRIBUTE_RECEIPT_ID + "," +
			ATTRIBUTE_ITEM_ID + "," +
			ATTRIBUTE_ITEM_DESC + "," +
			ATTRIBUTE_CURRENT_EXPECTED_QTY + ")" +
			" values (" +
			scannedBinMissingReceiptBean.getUploadId() + "," +
			SqlHandler.delimitString(scannedBinMissingReceiptBean.getHub()) + "," +
			SqlHandler.delimitString(scannedBinMissingReceiptBean.getBin()) + "," +
			scannedBinMissingReceiptBean.getReceiptId() + "," +
			scannedBinMissingReceiptBean.getItemId() + "," +
			SqlHandler.delimitString(scannedBinMissingReceiptBean.getItemDesc()) + "," +
			scannedBinMissingReceiptBean.getCurrentExpectedQty() + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(ScannedBinMissingReceiptBean scannedBinMissingReceiptBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(scannedBinMissingReceiptBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(ScannedBinMissingReceiptBean scannedBinMissingReceiptBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_UPLOAD_ID + "=" +
				StringHandler.nullIfZero(scannedBinMissingReceiptBean.getUploadId()) + "," +
			ATTRIBUTE_HUB + "=" +
				SqlHandler.delimitString(scannedBinMissingReceiptBean.getHub()) + "," +
			ATTRIBUTE_BIN + "=" +
				SqlHandler.delimitString(scannedBinMissingReceiptBean.getBin()) + "," +
			ATTRIBUTE_RECEIPT_ID + "=" +
				StringHandler.nullIfZero(scannedBinMissingReceiptBean.getReceiptId()) + "," +
			ATTRIBUTE_ITEM_ID + "=" +
				StringHandler.nullIfZero(scannedBinMissingReceiptBean.getItemId()) + "," +
			ATTRIBUTE_ITEM_DESC + "=" +
				SqlHandler.delimitString(scannedBinMissingReceiptBean.getItemDesc()) + "," +
			ATTRIBUTE_CURRENT_EXPECTED_QTY + "=" +
				StringHandler.nullIfZero(scannedBinMissingReceiptBean.getCurrentExpectedQty()) + " " +
			"where " + ATTRIBUTE_UPLOAD_ID + "=" +
				scannedBinMissingReceiptBean.getUploadId();

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

		Collection scannedBinMissingReceiptBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			ScannedBinMissingReceiptBean scannedBinMissingReceiptBean = new ScannedBinMissingReceiptBean();
			load(dataSetRow, scannedBinMissingReceiptBean);
			scannedBinMissingReceiptBeanColl.add(scannedBinMissingReceiptBean);
		}

		return scannedBinMissingReceiptBeanColl;
	}
	//select distinct
	public Collection selectDistinct(SearchCriteria criteria)
		throws BaseException {

		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = selectDistinct(criteria, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}
	public Collection selectDistinct(SearchCriteria criteria, Connection conn)
		throws BaseException {

		Collection scannedBinMissingReceiptBeanColl = new Vector();

		String query = "select "+ATTRIBUTE_RECEIPT_ID+","+ATTRIBUTE_LOT_STATUS+","+ATTRIBUTE_BIN+","+ATTRIBUTE_ITEM_ID+","+ATTRIBUTE_ITEM_DESC+","
			+ATTRIBUTE_CURRENT_EXPECTED_QTY+" from " + TABLE + " " +getWhereClause(criteria) + "order by "+ATTRIBUTE_BIN+"";

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			ScannedBinMissingReceiptBean scannedBinMissingReceiptBean = new ScannedBinMissingReceiptBean();
			load(dataSetRow, scannedBinMissingReceiptBean);
			scannedBinMissingReceiptBeanColl.add(scannedBinMissingReceiptBean);
		}

		return scannedBinMissingReceiptBeanColl;
	}

}