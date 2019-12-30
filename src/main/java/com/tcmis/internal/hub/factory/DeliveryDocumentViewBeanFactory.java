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
import com.tcmis.internal.hub.beans.DeliveryDocumentViewBean;


/******************************************************************************
 * CLASSNAME: DeliveryDocumentViewBeanFactory <br>
 * @version: 1.0, Oct 8, 2008 <br>
 *****************************************************************************/


public class DeliveryDocumentViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_PICKLIST_ID = "PICKLIST_ID";
	public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
	public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
	public String ATTRIBUTE_FILE_NAME = "FILE_NAME";
	public String ATTRIBUTE_DOCUMENT_TYPE = "DOCUMENT_TYPE";

	//table name
	public String TABLE = "DELIVERY_DOCUMENT_VIEW";


	//constructor
	public DeliveryDocumentViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("picklistId")) {
			return ATTRIBUTE_PICKLIST_ID;
		}
		else if(attributeName.equals("prNumber")) {
			return ATTRIBUTE_PR_NUMBER;
		}
		else if(attributeName.equals("lineItem")) {
			return ATTRIBUTE_LINE_ITEM;
		}
		else if(attributeName.equals("fileName")) {
			return ATTRIBUTE_FILE_NAME;
		}
		else if(attributeName.equals("documentType")) {
			return ATTRIBUTE_DOCUMENT_TYPE;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, DeliveryDocumentViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(DeliveryDocumentViewBean deliveryDocumentViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("picklistId", "SearchCriterion.EQUALS",
			"" + deliveryDocumentViewBean.getPicklistId());

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


	public int delete(DeliveryDocumentViewBean deliveryDocumentViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("picklistId", "SearchCriterion.EQUALS",
			"" + deliveryDocumentViewBean.getPicklistId());

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
	public int insert(DeliveryDocumentViewBean deliveryDocumentViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(deliveryDocumentViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(DeliveryDocumentViewBean deliveryDocumentViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_PICKLIST_ID + "," +
			ATTRIBUTE_PR_NUMBER + "," +
			ATTRIBUTE_LINE_ITEM + "," +
			ATTRIBUTE_FILE_NAME + "," +
			ATTRIBUTE_DOCUMENT_TYPE + ")" +
			" values (" +
			deliveryDocumentViewBean.getPicklistId() + "," +
			deliveryDocumentViewBean.getPrNumber() + "," +
			SqlHandler.delimitString(deliveryDocumentViewBean.getLineItem()) + "," +
			SqlHandler.delimitString(deliveryDocumentViewBean.getFileName()) + "," +
			SqlHandler.delimitString(deliveryDocumentViewBean.getDocumentType()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(DeliveryDocumentViewBean deliveryDocumentViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(deliveryDocumentViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(DeliveryDocumentViewBean deliveryDocumentViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_PICKLIST_ID + "=" + 
				StringHandler.nullIfZero(deliveryDocumentViewBean.getPicklistId()) + "," +
			ATTRIBUTE_PR_NUMBER + "=" + 
				StringHandler.nullIfZero(deliveryDocumentViewBean.getPrNumber()) + "," +
			ATTRIBUTE_LINE_ITEM + "=" + 
				SqlHandler.delimitString(deliveryDocumentViewBean.getLineItem()) + "," +
			ATTRIBUTE_FILE_NAME + "=" + 
				SqlHandler.delimitString(deliveryDocumentViewBean.getFileName()) + "," +
			ATTRIBUTE_DOCUMENT_TYPE + "=" + 
				SqlHandler.delimitString(deliveryDocumentViewBean.getDocumentType()) + " " +
			"where " + ATTRIBUTE_PICKLIST_ID + "=" +
				deliveryDocumentViewBean.getPicklistId();

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

		Collection deliveryDocumentViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			DeliveryDocumentViewBean deliveryDocumentViewBean = new DeliveryDocumentViewBean();
			load(dataSetRow, deliveryDocumentViewBean);
			deliveryDocumentViewBeanColl.add(deliveryDocumentViewBean);
		}

		return deliveryDocumentViewBeanColl;
	}
}