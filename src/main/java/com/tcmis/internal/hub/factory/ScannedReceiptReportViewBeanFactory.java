package com.tcmis.internal.hub.factory;


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
import com.tcmis.internal.hub.beans.ScannedReceiptReportViewBean;


/******************************************************************************
 * CLASSNAME: ScannedReceiptReportViewBeanFactory <br>
 * @version: 1.0, Aug 1, 2005 <br>
 *****************************************************************************/


public class ScannedReceiptReportViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_UPLOAD_ID = "UPLOAD_ID";
	public String ATTRIBUTE_RECEIPT_ID = "RECEIPT_ID";
	public String ATTRIBUTE_SCAN_DATETIME = "SCAN_DATETIME";
	public String ATTRIBUTE_COUNTER_ID = "COUNTER_ID";
	public String ATTRIBUTE_SCANNED_BIN = "SCANNED_BIN";
	public String ATTRIBUTE_COUNTED_QUANTITY = "COUNTED_QUANTITY";
	public String ATTRIBUTE_UPLOAD_DATETIME = "UPLOAD_DATETIME";
	public String ATTRIBUTE_EXPECTED_BIN = "EXPECTED_BIN";
	public String ATTRIBUTE_TOTAL_QTY_COUNTED_FOR_RECEIPT = "TOTAL_QTY_COUNTED_FOR_RECEIPT";
	public String ATTRIBUTE_EXPECTED_QTY_FOR_RECEIPT = "EXPECTED_QTY_FOR_RECEIPT";
	public String ATTRIBUTE_LOT_STATUS = "LOT_STATUS";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	//table name
	public String TABLE = "SCANNED_RECEIPT_REPORT_VIEW";


	//constructor
	public ScannedReceiptReportViewBeanFactory(DbManager dbManager) {
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
		else if(attributeName.equals("expectedBin")) {
			return ATTRIBUTE_EXPECTED_BIN;
		}
		else if(attributeName.equals("totalQtyCountedForReceipt")) {
			return ATTRIBUTE_TOTAL_QTY_COUNTED_FOR_RECEIPT;
		}
		else if(attributeName.equals("expectedQtyForReceipt")) {
			return ATTRIBUTE_EXPECTED_QTY_FOR_RECEIPT;
		}
		else if(attributeName.equals("lotStatus")) {
			return ATTRIBUTE_LOT_STATUS;
		}
		else if(attributeName.equals("itemId")) {
		 return ATTRIBUTE_ITEM_ID;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, ScannedReceiptReportViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(ScannedReceiptReportViewBean scannedReceiptReportViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("uploadId", "SearchCriterion.EQUALS",
			"" + scannedReceiptReportViewBean.getUploadId());

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


	public int delete(ScannedReceiptReportViewBean scannedReceiptReportViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("uploadId", "SearchCriterion.EQUALS",
			"" + scannedReceiptReportViewBean.getUploadId());

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
	public int insert(ScannedReceiptReportViewBean scannedReceiptReportViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(scannedReceiptReportViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(ScannedReceiptReportViewBean scannedReceiptReportViewBean, Connection conn)
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
			ATTRIBUTE_UPLOAD_DATETIME + "," +
			ATTRIBUTE_EXPECTED_BIN + "," +
			ATTRIBUTE_TOTAL_QTY_COUNTED_FOR_RECEIPT + "," +
			ATTRIBUTE_EXPECTED_QTY_FOR_RECEIPT + ")" +
			" values (" +
			scannedReceiptReportViewBean.getUploadId() + "," +
			scannedReceiptReportViewBean.getReceiptId() + "," +
			DateHandler.getOracleToDateFunction(scannedReceiptReportViewBean.getScanDatetime()) + "," +
			scannedReceiptReportViewBean.getCounterId() + "," +
			SqlHandler.delimitString(scannedReceiptReportViewBean.getScannedBin()) + "," +
			scannedReceiptReportViewBean.getCountedQuantity() + "," +
			DateHandler.getOracleToDateFunction(scannedReceiptReportViewBean.getUploadDatetime()) + "," +
			SqlHandler.delimitString(scannedReceiptReportViewBean.getExpectedBin()) + "," +
			scannedReceiptReportViewBean.getTotalQtyCountedForReceipt() + "," +
			scannedReceiptReportViewBean.getExpectedQtyForReceipt() + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(ScannedReceiptReportViewBean scannedReceiptReportViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(scannedReceiptReportViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(ScannedReceiptReportViewBean scannedReceiptReportViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_UPLOAD_ID + "=" +
				StringHandler.nullIfZero(scannedReceiptReportViewBean.getUploadId()) + "," +
			ATTRIBUTE_RECEIPT_ID + "=" +
				StringHandler.nullIfZero(scannedReceiptReportViewBean.getReceiptId()) + "," +
			ATTRIBUTE_SCAN_DATETIME + "=" +
				DateHandler.getOracleToDateFunction(scannedReceiptReportViewBean.getScanDatetime()) + "," +
			ATTRIBUTE_COUNTER_ID + "=" +
				StringHandler.nullIfZero(scannedReceiptReportViewBean.getCounterId()) + "," +
			ATTRIBUTE_SCANNED_BIN + "=" +
				SqlHandler.delimitString(scannedReceiptReportViewBean.getScannedBin()) + "," +
			ATTRIBUTE_COUNTED_QUANTITY + "=" +
				StringHandler.nullIfZero(scannedReceiptReportViewBean.getCountedQuantity()) + "," +
			ATTRIBUTE_UPLOAD_DATETIME + "=" +
				DateHandler.getOracleToDateFunction(scannedReceiptReportViewBean.getUploadDatetime()) + "," +
			ATTRIBUTE_EXPECTED_BIN + "=" +
				SqlHandler.delimitString(scannedReceiptReportViewBean.getExpectedBin()) + "," +
			ATTRIBUTE_TOTAL_QTY_COUNTED_FOR_RECEIPT + "=" +
				StringHandler.nullIfZero(scannedReceiptReportViewBean.getTotalQtyCountedForReceipt()) + "," +
			ATTRIBUTE_EXPECTED_QTY_FOR_RECEIPT + "=" +
				StringHandler.nullIfZero(scannedReceiptReportViewBean.getExpectedQtyForReceipt()) + " " +
			"where " + ATTRIBUTE_UPLOAD_ID + "=" +
				scannedReceiptReportViewBean.getUploadId();

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

		Collection scannedReceiptReportViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			ScannedReceiptReportViewBean scannedReceiptReportViewBean = new ScannedReceiptReportViewBean();
			load(dataSetRow, scannedReceiptReportViewBean);
			scannedReceiptReportViewBeanColl.add(scannedReceiptReportViewBean);
		}

		return scannedReceiptReportViewBeanColl;
	}

  public Collection selectForErrorReport(SearchCriteria criteria,boolean excelReport)
		throws BaseException {

		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = selectForErrorReport(criteria,excelReport, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}

	public Collection selectForErrorReport(SearchCriteria criteria,boolean excelReport, Connection conn)
		throws BaseException {

		Collection scannedReceiptReportViewBeanColl = new Vector();

		String query = "select "+ATTRIBUTE_UPLOAD_ID+","+ATTRIBUTE_RECEIPT_ID+","+ATTRIBUTE_ITEM_ID+","+ATTRIBUTE_LOT_STATUS+","+ATTRIBUTE_SCANNED_BIN+""
		+ ",sum("+ATTRIBUTE_COUNTED_QUANTITY+") "+ATTRIBUTE_COUNTED_QUANTITY+", "+ATTRIBUTE_EXPECTED_BIN+", "+ATTRIBUTE_TOTAL_QTY_COUNTED_FOR_RECEIPT+", "
		+ ""+ATTRIBUTE_EXPECTED_QTY_FOR_RECEIPT+" from " + TABLE + " " +getWhereClause(criteria);

	 if (!excelReport)
	 {
		query += "and ( ("+ATTRIBUTE_EXPECTED_BIN + " <> " + ATTRIBUTE_SCANNED_BIN + ") or (" + ATTRIBUTE_EXPECTED_QTY_FOR_RECEIPT + " <> " +
		 ATTRIBUTE_TOTAL_QTY_COUNTED_FOR_RECEIPT + ") ) and ("+ATTRIBUTE_TOTAL_QTY_COUNTED_FOR_RECEIPT+" <> 0)";
	 }

		query += " group by "+ATTRIBUTE_UPLOAD_ID+","+ATTRIBUTE_RECEIPT_ID+","+ATTRIBUTE_ITEM_ID+","+ATTRIBUTE_LOT_STATUS+","+ATTRIBUTE_SCANNED_BIN+","
		+ ""+ATTRIBUTE_EXPECTED_BIN+", "+ATTRIBUTE_TOTAL_QTY_COUNTED_FOR_RECEIPT+", "+ ATTRIBUTE_EXPECTED_QTY_FOR_RECEIPT+" order by "+ATTRIBUTE_RECEIPT_ID+","+ATTRIBUTE_SCANNED_BIN+"";

		DataSet dataSet = new SqlManager().select(conn, query);
		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			ScannedReceiptReportViewBean scannedReceiptReportViewBean = new ScannedReceiptReportViewBean();
			load(dataSetRow, scannedReceiptReportViewBean);
			scannedReceiptReportViewBeanColl.add(scannedReceiptReportViewBean);
		}

		return scannedReceiptReportViewBeanColl;
	}


}