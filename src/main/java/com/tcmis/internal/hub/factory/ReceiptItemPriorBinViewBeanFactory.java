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
import com.tcmis.common.util.SortCriteria;
import com.tcmis.internal.hub.beans.ReceiptItemPriorBinViewBean;

/******************************************************************************
 * CLASSNAME: ReceiptItemPriorBinViewBeanFactory <br>
 * 
 * @version: 1.0, Jan 21, 2005 <br>
 *****************************************************************************/

public class ReceiptItemPriorBinViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	// column names
	public String ATTRIBUTE_HUB = "HUB";
	public String ATTRIBUTE_BIN = "BIN";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_TRANSACTION_DATE = "TRANSACTION_DATE";
	public String ATTRIBUTE_ON_SITE = "ON_SITE";
	public String ATTRIBUTE_STATUS = "STATUS";

	// table name
	public String TABLE = "RECEIPT_ITEM_PRIOR_BIN_VIEW";

	// constructor
	public ReceiptItemPriorBinViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}

	// get column names
	public String getColumnName(String attributeName) {
		if (attributeName.equals("hub")) {
			return ATTRIBUTE_HUB;
		} else if (attributeName.equals("bin")) {
			return ATTRIBUTE_BIN;
		} else if (attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		} else if (attributeName.equals("transactionDate")) {
			return ATTRIBUTE_TRANSACTION_DATE;
		} else if (attributeName.equals("onSite")) {
			return ATTRIBUTE_ON_SITE;
		} else if (attributeName.equals("status")) {
			return ATTRIBUTE_STATUS;
		} else {
			return super.getColumnName(attributeName);
		}
	}

	// get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, ReceiptItemPriorBinViewBean.class);
	}

	// you need to verify the primary key(s) before uncommenting this
	/*
	 * //delete public int delete(ReceiptItemPriorBinViewBean
	 * receiptItemPriorBinViewBean) throws BaseException { SearchCriteria
	 * criteria = new SearchCriteria("hub", "=", "" +
	 * receiptItemPriorBinViewBean.getHub()); Connection connection =
	 * this.getDbManager().getConnection(); int result = this.delete(criteria,
	 * connection); this.getDbManager().returnConnection(connection); return
	 * result; } public int delete(ReceiptItemPriorBinViewBean
	 * receiptItemPriorBinViewBean, Connection conn) throws BaseException {
	 * SearchCriteria criteria = new SearchCriteria("hub", "=", "" +
	 * receiptItemPriorBinViewBean.getHub()); return delete(criteria, conn); }
	 */

	public int delete(SearchCriteria criteria) throws BaseException {

		Connection connection = getDbManager().getConnection();
		int result = delete(criteria, connection);
		this.getDbManager().returnConnection(connection);
		return result;
	}

	public int delete(SearchCriteria criteria, Connection conn) throws BaseException {

		String sqlQuery = " delete from " + TABLE + " " + getWhereClause(criteria);

		return new SqlManager().update(conn, sqlQuery);
	}

	// you need to verify the primary key(s) before uncommenting this
	/*
	 * //insert public int insert(ReceiptItemPriorBinViewBean
	 * receiptItemPriorBinViewBean) throws BaseException { Connection connection
	 * = getDbManager().getConnection(); int result = insert(criteria,
	 * connection); this.getDbManager().returnConnection(connection); return
	 * result; } public int insert(ReceiptItemPriorBinViewBean
	 * receiptItemPriorBinViewBean, Connection conn) throws BaseException {
	 * SqlManager sqlManager = new SqlManager(); String query = "insert into " +
	 * TABLE + " (" + ATTRIBUTE_HUB + "," + ATTRIBUTE_BIN + "," +
	 * ATTRIBUTE_ITEM_ID + "," + ATTRIBUTE_TRANSACTION_DATE + "," +
	 * ATTRIBUTE_ON_SITE + "," + ATTRIBUTE_STATUS + ")" +
	 * SqlHandler.delimitString(receiptItemPriorBinViewBean.getHub()) + "," +
	 * SqlHandler.delimitString(receiptItemPriorBinViewBean.getBin()) + "," +
	 * StringHandler.nullIfZero(receiptItemPriorBinViewBean.getItemId()) + "," +
	 * DateHandler.getOracleToDateFunction(receiptItemPriorBinViewBean.
	 * getTransactionDate()) + "," +
	 * SqlHandler.delimitString(receiptItemPriorBinViewBean.getOnSite()) + "," +
	 * SqlHandler.delimitString(receiptItemPriorBinViewBean.getStatus()) + ")";
	 * return sqlManager.update(conn, query); } //update public int
	 * update(ReceiptItemPriorBinViewBean receiptItemPriorBinViewBean) throws
	 * BaseException { Connection connection = getDbManager().getConnection();
	 * int result = update(criteria, connection);
	 * this.getDbManager().returnConnection(connection); return result; } public
	 * int update(ReceiptItemPriorBinViewBean receiptItemPriorBinViewBean,
	 * Connection conn) throws BaseException { String query = "update " + TABLE
	 * + " set " + ATTRIBUTE_HUB + "=" +
	 * SqlHandler.delimitString(receiptItemPriorBinViewBean.getHub()) + "," +
	 * ATTRIBUTE_BIN + "=" +
	 * SqlHandler.delimitString(receiptItemPriorBinViewBean.getBin()) + "," +
	 * ATTRIBUTE_ITEM_ID + "=" +
	 * StringHandler.nullIfZero(receiptItemPriorBinViewBean.getItemId()) + "," +
	 * ATTRIBUTE_TRANSACTION_DATE + "=" +
	 * DateHandler.getOracleToDateFunction(receiptItemPriorBinViewBean.
	 * getTransactionDate()) + "," + ATTRIBUTE_ON_SITE + "=" +
	 * SqlHandler.delimitString(receiptItemPriorBinViewBean.getOnSite()) + "," +
	 * ATTRIBUTE_STATUS + "=" +
	 * SqlHandler.delimitString(receiptItemPriorBinViewBean.getStatus()) + " " +
	 * "where " + ATTRIBUTE_HUB + "=" +
	 * StringHandler.nullIfZero(receiptItemPriorBinViewBean.getHub()); return
	 * new SqlManager().update(conn, query); }
	 */

	// select
	public Collection select(SearchCriteria criteria) throws BaseException {

		Connection connection = this.getDbManager().getConnection();
		SortCriteria sortCriteria = new SortCriteria();
		sortCriteria.addCriterion("bin");
		Collection c = select(criteria, sortCriteria, connection);
		this.getDbManager().returnConnection(connection);
		return c;
	}

	public Collection selectCustomOrder(SearchCriteria criteria, SortCriteria sortCriteria) throws BaseException {

		Connection connection = this.getDbManager().getConnection();
		Collection c = select(criteria, sortCriteria, connection);
		this.getDbManager().returnConnection(connection);
		return c;
	}

	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn) throws BaseException {

		Collection receiptItemPriorBinViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " + getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			ReceiptItemPriorBinViewBean receiptItemPriorBinViewBean = new ReceiptItemPriorBinViewBean();
			load(dataSetRow, receiptItemPriorBinViewBean);
			receiptItemPriorBinViewBeanColl.add(receiptItemPriorBinViewBean);
		}

		return receiptItemPriorBinViewBeanColl;
	}
}