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
import com.tcmis.client.fec.beans.EbpPendFinanceApprovalViewBean;


/******************************************************************************
 * CLASSNAME: EbpPendFinanceApprovalViewBeanFactory <br>
 * @version: 1.0, Jun 23, 2005 <br>
 *****************************************************************************/


public class EbpPendFinanceApprovalViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_NEW_ITEM_DESCRIPTION = "NEW_ITEM_DESCRIPTION";
	public String ATTRIBUTE_NEW_ITEM_QUANTITY = "NEW_ITEM_QUANTITY";
	public String ATTRIBUTE_NEW_ITEM_UNIT = "NEW_ITEM_UNIT";
	public String ATTRIBUTE_NEW_ITEM_PRICE = "NEW_ITEM_PRICE";
	public String ATTRIBUTE_NEW_ITEM_CURRENCY = "NEW_ITEM_CURRENCY";
	public String ATTRIBUTE_NEW_ITEM_EXT_SCHEMA_TYPE = "NEW_ITEM_EXT_SCHEMA_TYPE";
	public String ATTRIBUTE_NEW_ITEM_EXT_CATEGORY_ID = "NEW_ITEM_EXT_CATEGORY_ID";
	public String ATTRIBUTE_NEW_ITEM_CUST_FIELD1 = "NEW_ITEM_CUST_FIELD1";
	public String ATTRIBUTE_NEW_ITEM_CUST_FIELD2 = "NEW_ITEM_CUST_FIELD2";
	public String ATTRIBUTE_NEW_ITEM_CUST_FIELD3 = "NEW_ITEM_CUST_FIELD3";
	public String ATTRIBUTE_NEW_ITEM_CUST_FIELD4 = "NEW_ITEM_CUST_FIELD4";
	public String ATTRIBUTE_NEW_ITEM_CUST_FIELD5 = "NEW_ITEM_CUST_FIELD5";
	public String ATTRIBUTE_PO_NUMBER = "PO_NUMBER";
	public String ATTRIBUTE_RELEASE_NUMBER = "RELEASE_NUMBER";
	public String ATTRIBUTE_PAYLOAD_ID = "PAYLOAD_ID";

	//table name
	public String TABLE = "EBP_PEND_FINANCE_APPROVAL_VIEW";


	//constructor
	public EbpPendFinanceApprovalViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("newItemDescription")) {
			return ATTRIBUTE_NEW_ITEM_DESCRIPTION;
		}
		else if(attributeName.equals("newItemQuantity")) {
			return ATTRIBUTE_NEW_ITEM_QUANTITY;
		}
		else if(attributeName.equals("newItemUnit")) {
			return ATTRIBUTE_NEW_ITEM_UNIT;
		}
		else if(attributeName.equals("newItemPrice")) {
			return ATTRIBUTE_NEW_ITEM_PRICE;
		}
		else if(attributeName.equals("newItemCurrency")) {
			return ATTRIBUTE_NEW_ITEM_CURRENCY;
		}
		else if(attributeName.equals("newItemExtSchemaType")) {
			return ATTRIBUTE_NEW_ITEM_EXT_SCHEMA_TYPE;
		}
		else if(attributeName.equals("newItemExtCategoryId")) {
			return ATTRIBUTE_NEW_ITEM_EXT_CATEGORY_ID;
		}
		else if(attributeName.equals("newItemCustField1")) {
			return ATTRIBUTE_NEW_ITEM_CUST_FIELD1;
		}
		else if(attributeName.equals("newItemCustField2")) {
			return ATTRIBUTE_NEW_ITEM_CUST_FIELD2;
		}
		else if(attributeName.equals("newItemCustField3")) {
			return ATTRIBUTE_NEW_ITEM_CUST_FIELD3;
		}
		else if(attributeName.equals("newItemCustField4")) {
			return ATTRIBUTE_NEW_ITEM_CUST_FIELD4;
		}
		else if(attributeName.equals("newItemCustField5")) {
			return ATTRIBUTE_NEW_ITEM_CUST_FIELD5;
		}
		else if(attributeName.equals("poNumber")) {
			return ATTRIBUTE_PO_NUMBER;
		}
		else if(attributeName.equals("releaseNumber")) {
			return ATTRIBUTE_RELEASE_NUMBER;
		}
		else if(attributeName.equals("payloadId")) {
			return ATTRIBUTE_PAYLOAD_ID;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, EbpPendFinanceApprovalViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(EbpPendFinanceApprovalViewBean ebpPendFinanceApprovalViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("newItemDescription", "SearchCriterion.EQUALS",
			"" + ebpPendFinanceApprovalViewBean.getNewItemDescription());

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


	public int delete(EbpPendFinanceApprovalViewBean ebpPendFinanceApprovalViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("newItemDescription", "SearchCriterion.EQUALS",
			"" + ebpPendFinanceApprovalViewBean.getNewItemDescription());

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
	public int insert(EbpPendFinanceApprovalViewBean ebpPendFinanceApprovalViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(ebpPendFinanceApprovalViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(EbpPendFinanceApprovalViewBean ebpPendFinanceApprovalViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_NEW_ITEM_DESCRIPTION + "," +
			ATTRIBUTE_NEW_ITEM_QUANTITY + "," +
			ATTRIBUTE_NEW_ITEM_UNIT + "," +
			ATTRIBUTE_NEW_ITEM_PRICE + "," +
			ATTRIBUTE_NEW_ITEM_CURRENCY + "," +
			ATTRIBUTE_NEW_ITEM_EXT_SCHEMA_TYPE + "," +
			ATTRIBUTE_NEW_ITEM_EXT_CATEGORY_ID + "," +
			ATTRIBUTE_NEW_ITEM_CUST_FIELD1 + "," +
			ATTRIBUTE_NEW_ITEM_CUST_FIELD2 + "," +
			ATTRIBUTE_NEW_ITEM_CUST_FIELD3 + "," +
			ATTRIBUTE_NEW_ITEM_CUST_FIELD4 + "," +
			ATTRIBUTE_NEW_ITEM_CUST_FIELD5 + "," +
			ATTRIBUTE_PO_NUMBER + "," +
			ATTRIBUTE_RELEASE_NUMBER + "," +
			ATTRIBUTE_PAYLOAD_ID + ")" +
 values (
			SqlHandler.delimitString(ebpPendFinanceApprovalViewBean.getNewItemDescription()) + "," +
			StringHandler.nullIfZero(ebpPendFinanceApprovalViewBean.getNewItemQuantity()) + "," +
			SqlHandler.delimitString(ebpPendFinanceApprovalViewBean.getNewItemUnit()) + "," +
			StringHandler.nullIfZero(ebpPendFinanceApprovalViewBean.getNewItemPrice()) + "," +
			SqlHandler.delimitString(ebpPendFinanceApprovalViewBean.getNewItemCurrency()) + "," +
			SqlHandler.delimitString(ebpPendFinanceApprovalViewBean.getNewItemExtSchemaType()) + "," +
			SqlHandler.delimitString(ebpPendFinanceApprovalViewBean.getNewItemExtCategoryId()) + "," +
			SqlHandler.delimitString(ebpPendFinanceApprovalViewBean.getNewItemCustField1()) + "," +
			StringHandler.nullIfZero(ebpPendFinanceApprovalViewBean.getNewItemCustField2()) + "," +
			SqlHandler.delimitString(ebpPendFinanceApprovalViewBean.getNewItemCustField3()) + "," +
			SqlHandler.delimitString(ebpPendFinanceApprovalViewBean.getNewItemCustField4()) + "," +
			SqlHandler.delimitString(ebpPendFinanceApprovalViewBean.getNewItemCustField5()) + "," +
			SqlHandler.delimitString(ebpPendFinanceApprovalViewBean.getPoNumber()) + "," +
			SqlHandler.delimitString(ebpPendFinanceApprovalViewBean.getReleaseNumber()) + "," +
			SqlHandler.delimitString(ebpPendFinanceApprovalViewBean.getPayloadId()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(EbpPendFinanceApprovalViewBean ebpPendFinanceApprovalViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(ebpPendFinanceApprovalViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(EbpPendFinanceApprovalViewBean ebpPendFinanceApprovalViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_NEW_ITEM_DESCRIPTION + "=" + 
				SqlHandler.delimitString(ebpPendFinanceApprovalViewBean.getNewItemDescription()) + "," +
			ATTRIBUTE_NEW_ITEM_QUANTITY + "=" + 
				StringHandler.nullIfZero(ebpPendFinanceApprovalViewBean.getNewItemQuantity()) + "," +
			ATTRIBUTE_NEW_ITEM_UNIT + "=" + 
				SqlHandler.delimitString(ebpPendFinanceApprovalViewBean.getNewItemUnit()) + "," +
			ATTRIBUTE_NEW_ITEM_PRICE + "=" + 
				StringHandler.nullIfZero(ebpPendFinanceApprovalViewBean.getNewItemPrice()) + "," +
			ATTRIBUTE_NEW_ITEM_CURRENCY + "=" + 
				SqlHandler.delimitString(ebpPendFinanceApprovalViewBean.getNewItemCurrency()) + "," +
			ATTRIBUTE_NEW_ITEM_EXT_SCHEMA_TYPE + "=" + 
				SqlHandler.delimitString(ebpPendFinanceApprovalViewBean.getNewItemExtSchemaType()) + "," +
			ATTRIBUTE_NEW_ITEM_EXT_CATEGORY_ID + "=" + 
				SqlHandler.delimitString(ebpPendFinanceApprovalViewBean.getNewItemExtCategoryId()) + "," +
			ATTRIBUTE_NEW_ITEM_CUST_FIELD1 + "=" + 
				SqlHandler.delimitString(ebpPendFinanceApprovalViewBean.getNewItemCustField1()) + "," +
			ATTRIBUTE_NEW_ITEM_CUST_FIELD2 + "=" + 
				StringHandler.nullIfZero(ebpPendFinanceApprovalViewBean.getNewItemCustField2()) + "," +
			ATTRIBUTE_NEW_ITEM_CUST_FIELD3 + "=" + 
				SqlHandler.delimitString(ebpPendFinanceApprovalViewBean.getNewItemCustField3()) + "," +
			ATTRIBUTE_NEW_ITEM_CUST_FIELD4 + "=" + 
				SqlHandler.delimitString(ebpPendFinanceApprovalViewBean.getNewItemCustField4()) + "," +
			ATTRIBUTE_NEW_ITEM_CUST_FIELD5 + "=" + 
				SqlHandler.delimitString(ebpPendFinanceApprovalViewBean.getNewItemCustField5()) + "," +
			ATTRIBUTE_PO_NUMBER + "=" + 
				SqlHandler.delimitString(ebpPendFinanceApprovalViewBean.getPoNumber()) + "," +
			ATTRIBUTE_RELEASE_NUMBER + "=" + 
				SqlHandler.delimitString(ebpPendFinanceApprovalViewBean.getReleaseNumber()) + "," +
			ATTRIBUTE_PAYLOAD_ID + "=" + 
				SqlHandler.delimitString(ebpPendFinanceApprovalViewBean.getPayloadId()) + " " +
			"where " + ATTRIBUTE_NEW_ITEM_DESCRIPTION + "=" +
				StringHandler.nullIfZero(ebpPendFinanceApprovalViewBean.getNewItemDescription());

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

		Collection ebpPendFinanceApprovalViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			EbpPendFinanceApprovalViewBean ebpPendFinanceApprovalViewBean = new EbpPendFinanceApprovalViewBean();
			load(dataSetRow, ebpPendFinanceApprovalViewBean);
			ebpPendFinanceApprovalViewBeanColl.add(ebpPendFinanceApprovalViewBean);
		}

		return ebpPendFinanceApprovalViewBeanColl;
	}
}