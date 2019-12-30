package com.tcmis.client.fec.factory;


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
import com.tcmis.client.fec.beans.FecReceiptStageOvBean;


/******************************************************************************
 * CLASSNAME: FecReceiptStageOvBeanFactory <br>
 * @version: 1.0, Mar 24, 2005 <br>
 *****************************************************************************/


public class FecReceiptStageOvBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_PO_NUMBER = "PO_NUMBER";
	public String ATTRIBUTE_PO_LINE = "PO_LINE";
	public String ATTRIBUTE_RECEIVED_QUANTITY = "RECEIVED_QUANTITY";
	public String ATTRIBUTE_PART_NUMBER = "PART_NUMBER";
	public String ATTRIBUTE_RECEIPT_DATE = "RECEIPT_DATE";
	public String ATTRIBUTE_LOAD_DATE = "LOAD_DATE";
	public String ATTRIBUTE_FILENAME = "FILENAME";
	public String ATTRIBUTE_LINE = "LINE";

	//table name
	public String TABLE = "FEC_RECEIPT_STAGE_OV";


	//constructor
	public FecReceiptStageOvBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("poNumber")) {
			return ATTRIBUTE_PO_NUMBER;
		}
		else if(attributeName.equals("poLine")) {
			return ATTRIBUTE_PO_LINE;
		}
		else if(attributeName.equals("receivedQuantity")) {
			return ATTRIBUTE_RECEIVED_QUANTITY;
		}
		else if(attributeName.equals("partNumber")) {
			return ATTRIBUTE_PART_NUMBER;
		}
		else if(attributeName.equals("receiptDate")) {
			return ATTRIBUTE_RECEIPT_DATE;
		}
		else if(attributeName.equals("loadDate")) {
			return ATTRIBUTE_LOAD_DATE;
		}
		else if(attributeName.equals("filename")) {
			return ATTRIBUTE_FILENAME;
		}
		else if(attributeName.equals("line")) {
			return ATTRIBUTE_LINE;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, FecReceiptStageOvBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(FecReceiptStageOvBean fecReceiptStageOvBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("poNumber", "SearchCriterion.EQUALS",
			"" + fecReceiptStageOvBean.getPoNumber());

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


	public int delete(FecReceiptStageOvBean fecReceiptStageOvBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("poNumber", "SearchCriterion.EQUALS",
			"" + fecReceiptStageOvBean.getPoNumber());

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
	public int insert(FecReceiptStageOvBean fecReceiptStageOvBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(fecReceiptStageOvBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(FecReceiptStageOvBean fecReceiptStageOvBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_PO_NUMBER + "," +
			ATTRIBUTE_PO_LINE + "," +
			ATTRIBUTE_RECEIVED_QUANTITY + "," +
			ATTRIBUTE_PART_NUMBER + "," +
			ATTRIBUTE_RECEIPT_DATE + "," +
			ATTRIBUTE_LOAD_DATE + "," +
			ATTRIBUTE_FILENAME + "," +
			ATTRIBUTE_LINE + ")" +
         " values (" +
			SqlHandler.delimitString(fecReceiptStageOvBean.getPoNumber()) + "," +
			SqlHandler.delimitString(fecReceiptStageOvBean.getPoLine()) + "," +
			StringHandler.nullIfZero(fecReceiptStageOvBean.getReceivedQuantity()) + "," +
			SqlHandler.delimitString(fecReceiptStageOvBean.getPartNumber()) + "," +
			DateHandler.getOracleToDateFunction(fecReceiptStageOvBean.getReceiptDate()) + "," +
			DateHandler.getOracleToDateFunction(fecReceiptStageOvBean.getLoadDate()) + "," +
			SqlHandler.delimitString(fecReceiptStageOvBean.getFilename()) + "," +
			StringHandler.nullIfZero(fecReceiptStageOvBean.getLine()) + ")";

		return sqlManager.update(conn, query);
	}

/*
	//update
	public int update(FecReceiptStageOvBean fecReceiptStageOvBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(fecReceiptStageOvBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(FecReceiptStageOvBean fecReceiptStageOvBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_PO_NUMBER + "=" + 
				SqlHandler.delimitString(fecReceiptStageOvBean.getPoNumber()) + "," +
			ATTRIBUTE_PO_LINE + "=" + 
				SqlHandler.delimitString(fecReceiptStageOvBean.getPoLine()) + "," +
			ATTRIBUTE_RECEIVED_QUANTITY + "=" + 
				StringHandler.nullIfZero(fecReceiptStageOvBean.getReceivedQuantity()) + "," +
			ATTRIBUTE_PART_NUMBER + "=" + 
				SqlHandler.delimitString(fecReceiptStageOvBean.getPartNumber()) + "," +
			ATTRIBUTE_RECEIPT_DATE + "=" + 
				DateHandler.getOracleToDateFunction(fecReceiptStageOvBean.getReceiptDate()) + "," +
			ATTRIBUTE_LOAD_DATE + "=" + 
				DateHandler.getOracleToDateFunction(fecReceiptStageOvBean.getLoadDate()) + "," +
			ATTRIBUTE_FILENAME + "=" + 
				SqlHandler.delimitString(fecReceiptStageOvBean.getFilename()) + "," +
			ATTRIBUTE_LINE + "=" + 
				StringHandler.nullIfZero(fecReceiptStageOvBean.getLine()) + " " +
			"where " + ATTRIBUTE_PO_NUMBER + "=" +
				StringHandler.nullIfZero(fecReceiptStageOvBean.getPoNumber());

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

		Collection fecReceiptStageOvBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			FecReceiptStageOvBean fecReceiptStageOvBean = new FecReceiptStageOvBean();
			load(dataSetRow, fecReceiptStageOvBean);
			fecReceiptStageOvBeanColl.add(fecReceiptStageOvBean);
		}

		return fecReceiptStageOvBeanColl;
	}
}