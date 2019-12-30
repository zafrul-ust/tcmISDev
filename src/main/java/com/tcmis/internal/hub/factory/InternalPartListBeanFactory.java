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
import com.tcmis.internal.hub.beans.InternalPartListBean;


/******************************************************************************
 * CLASSNAME: InternalPartListBeanFactory <br>
 * @version: 1.0, Dec 9, 2005 <br>
 *****************************************************************************/


public class InternalPartListBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
	public String ATTRIBUTE_PART_DESCRIPTION = "PART_DESCRIPTION";
	public String ATTRIBUTE_CATALOG_UOS = "CATALOG_UOS";
	public String ATTRIBUTE_UOS_PER_PACKAGING = "UOS_PER_PACKAGING";
	public String ATTRIBUTE_MR_QTY = "MR_QTY";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_TCM_PACKAGING = "TCM_PACKAGING";
	public String ATTRIBUTE_ITEM_DESC = "ITEM_DESC";

	//table name
	public String TABLE = "INTERNAL_PART_LIST";


	//constructor
	public InternalPartListBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("catPartNo")) {
			return ATTRIBUTE_CAT_PART_NO;
		}
		else if(attributeName.equals("partDescription")) {
			return ATTRIBUTE_PART_DESCRIPTION;
		}
		else if(attributeName.equals("catalogUos")) {
			return ATTRIBUTE_CATALOG_UOS;
		}
		else if(attributeName.equals("uosPerPackaging")) {
			return ATTRIBUTE_UOS_PER_PACKAGING;
		}
		else if(attributeName.equals("mrQty")) {
			return ATTRIBUTE_MR_QTY;
		}
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("tcmPackaging")) {
			return ATTRIBUTE_TCM_PACKAGING;
		}
		else if(attributeName.equals("itemDesc")) {
			return ATTRIBUTE_ITEM_DESC;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, InternalPartListBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(InternalPartListBean internalPartListBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("catPartNo", "SearchCriterion.EQUALS",
			"" + internalPartListBean.getCatPartNo());

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


	public int delete(InternalPartListBean internalPartListBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("catPartNo", "SearchCriterion.EQUALS",
			"" + internalPartListBean.getCatPartNo());

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
	public int insert(InternalPartListBean internalPartListBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(internalPartListBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(InternalPartListBean internalPartListBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_CAT_PART_NO + "," +
			ATTRIBUTE_PART_DESCRIPTION + "," +
			ATTRIBUTE_CATALOG_UOS + "," +
			ATTRIBUTE_UOS_PER_PACKAGING + "," +
			ATTRIBUTE_MR_QTY + "," +
			ATTRIBUTE_ITEM_ID + "," +
			ATTRIBUTE_TCM_PACKAGING + "," +
			ATTRIBUTE_ITEM_DESC + ")" +
 values (
			SqlHandler.delimitString(internalPartListBean.getCatPartNo()) + "," +
			SqlHandler.delimitString(internalPartListBean.getPartDescription()) + "," +
			SqlHandler.delimitString(internalPartListBean.getCatalogUos()) + "," +
			StringHandler.nullIfZero(internalPartListBean.getUosPerPackaging()) + "," +
			StringHandler.nullIfZero(internalPartListBean.getMrQty()) + "," +
			StringHandler.nullIfZero(internalPartListBean.getItemId()) + "," +
			SqlHandler.delimitString(internalPartListBean.getTcmPackaging()) + "," +
			SqlHandler.delimitString(internalPartListBean.getItemDesc()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(InternalPartListBean internalPartListBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(internalPartListBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(InternalPartListBean internalPartListBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_CAT_PART_NO + "=" + 
				SqlHandler.delimitString(internalPartListBean.getCatPartNo()) + "," +
			ATTRIBUTE_PART_DESCRIPTION + "=" + 
				SqlHandler.delimitString(internalPartListBean.getPartDescription()) + "," +
			ATTRIBUTE_CATALOG_UOS + "=" + 
				SqlHandler.delimitString(internalPartListBean.getCatalogUos()) + "," +
			ATTRIBUTE_UOS_PER_PACKAGING + "=" + 
				StringHandler.nullIfZero(internalPartListBean.getUosPerPackaging()) + "," +
			ATTRIBUTE_MR_QTY + "=" + 
				StringHandler.nullIfZero(internalPartListBean.getMrQty()) + "," +
			ATTRIBUTE_ITEM_ID + "=" + 
				StringHandler.nullIfZero(internalPartListBean.getItemId()) + "," +
			ATTRIBUTE_TCM_PACKAGING + "=" + 
				SqlHandler.delimitString(internalPartListBean.getTcmPackaging()) + "," +
			ATTRIBUTE_ITEM_DESC + "=" + 
				SqlHandler.delimitString(internalPartListBean.getItemDesc()) + " " +
			"where " + ATTRIBUTE_CAT_PART_NO + "=" +
				StringHandler.nullIfZero(internalPartListBean.getCatPartNo());

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

		Collection internalPartListBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			InternalPartListBean internalPartListBean = new InternalPartListBean();
			load(dataSetRow, internalPartListBean);
			internalPartListBeanColl.add(internalPartListBean);
		}

		return internalPartListBeanColl;
	}

	public Collection select(String query)
		throws BaseException {

		Collection internalPartListBeanColl = new Vector();

                Connection connection = null;
                connection = this.getDbManager().getConnection();

		DataSet dataSet = new SqlManager().select(connection, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			InternalPartListBean internalPartListBean = new InternalPartListBean();
			load(dataSetRow, internalPartListBean);
			internalPartListBeanColl.add(internalPartListBean);
		}

		return internalPartListBeanColl;
	}
        
}