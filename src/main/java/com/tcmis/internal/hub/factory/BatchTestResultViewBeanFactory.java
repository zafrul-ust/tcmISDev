package com.tcmis.internal.hub.factory;


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
import com.tcmis.internal.hub.beans.BatchTestResultViewBean;


/******************************************************************************
 * CLASSNAME: BatchTestResultViewBeanFactory <br>
 * @version: 1.0, Oct 1, 2007 <br>
 *****************************************************************************/


public class BatchTestResultViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_BATCH_ID = "BATCH_ID";
	public String ATTRIBUTE_RECEIPT_ID = "RECEIPT_ID";
	public String ATTRIBUTE_TEST_ID = "TEST_ID";
	public String ATTRIBUTE_TEST_DATE = "TEST_DATE";
	public String ATTRIBUTE_TEST_USER_ID = "TEST_USER_ID";
	public String ATTRIBUTE_TEST_USERNAME = "TEST_USERNAME";
	public String ATTRIBUTE_TEST_PARAMETER = "TEST_PARAMETER";
	public String ATTRIBUTE_TEST_RESULT = "TEST_RESULT";
	public String ATTRIBUTE_TEST_RESULT_UNIT = "TEST_RESULT_UNIT";

	//table name
	public String TABLE = "BATCH_TEST_RESULT_VIEW";


	//constructor
	public BatchTestResultViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("batchId")) {
			return ATTRIBUTE_BATCH_ID;
		}
		else if(attributeName.equals("receiptId")) {
			return ATTRIBUTE_RECEIPT_ID;
		}
		else if(attributeName.equals("testId")) {
			return ATTRIBUTE_TEST_ID;
		}
		else if(attributeName.equals("testDate")) {
			return ATTRIBUTE_TEST_DATE;
		}
		else if(attributeName.equals("testUserId")) {
			return ATTRIBUTE_TEST_USER_ID;
		}
		else if(attributeName.equals("testUsername")) {
			return ATTRIBUTE_TEST_USERNAME;
		}
		else if(attributeName.equals("testParameter")) {
			return ATTRIBUTE_TEST_PARAMETER;
		}
		else if(attributeName.equals("testResult")) {
			return ATTRIBUTE_TEST_RESULT;
		}
		else if(attributeName.equals("testResultUnit")) {
			return ATTRIBUTE_TEST_RESULT_UNIT;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, BatchTestResultViewBean.class);
	}


	//select
	public Collection select(SearchCriteria criteria)
		throws BaseException {
		return select(criteria, null);
	}
	  public Collection select(SearchCriteria criteria, SortCriteria sortCriteria) throws BaseException {
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

		Collection batchTestResultViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria)+ getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			BatchTestResultViewBean batchTestResultViewBean = new BatchTestResultViewBean();
			load(dataSetRow, batchTestResultViewBean);
			batchTestResultViewBeanColl.add(batchTestResultViewBean);
		}

		return batchTestResultViewBeanColl;
	}
}