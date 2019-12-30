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
import com.tcmis.internal.hub.beans.ReprintPicklistIdViewBean;


/******************************************************************************
 * CLASSNAME: ReprintPicklistIdViewBeanFactory <br>
 * @version: 1.0, Jan 26, 2007 <br>
 *****************************************************************************/


public class ReprintPicklistIdViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_HUB = "HUB";
	public String ATTRIBUTE_PICKLIST_ID = "PICKLIST_ID";
	public String ATTRIBUTE_PICKLIST_PRINT_DATE = "PICKLIST_PRINT_DATE";
	public String ATTRIBUTE_PACKING_GROUP_ID = "PACKING_GROUP_ID";

  //table name
	public String TABLE = "REPRINT_PICKLIST_ID_VIEW";


	//constructor
	public ReprintPicklistIdViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("hub")) {
			return ATTRIBUTE_HUB;
		}
		else if(attributeName.equals("picklistId")) {
			return ATTRIBUTE_PICKLIST_ID;
		}
		else if(attributeName.equals("picklistPrintDate")) {
			return ATTRIBUTE_PICKLIST_PRINT_DATE;
		}
		else if(attributeName.equals("packingGroupId")) {
			return ATTRIBUTE_PACKING_GROUP_ID;
		}    
    else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, ReprintPicklistIdViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(ReprintPicklistIdViewBean reprintPicklistIdViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("hub", "SearchCriterion.EQUALS",
			"" + reprintPicklistIdViewBean.getHub());

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


	public int delete(ReprintPicklistIdViewBean reprintPicklistIdViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("hub", "SearchCriterion.EQUALS",
			"" + reprintPicklistIdViewBean.getHub());

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
	public int insert(ReprintPicklistIdViewBean reprintPicklistIdViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(reprintPicklistIdViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(ReprintPicklistIdViewBean reprintPicklistIdViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_HUB + "," +
			ATTRIBUTE_PICKLIST_ID + "," +
			ATTRIBUTE_PICKLIST_PRINT_DATE + ")" +
 values (
			SqlHandler.delimitString(reprintPicklistIdViewBean.getHub()) + "," +
			StringHandler.nullIfZero(reprintPicklistIdViewBean.getPicklistId()) + "," +
			DateHandler.getOracleToDateFunction(reprintPicklistIdViewBean.getPicklistPrintDate()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(ReprintPicklistIdViewBean reprintPicklistIdViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(reprintPicklistIdViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(ReprintPicklistIdViewBean reprintPicklistIdViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_HUB + "=" + 
				SqlHandler.delimitString(reprintPicklistIdViewBean.getHub()) + "," +
			ATTRIBUTE_PICKLIST_ID + "=" + 
				StringHandler.nullIfZero(reprintPicklistIdViewBean.getPicklistId()) + "," +
			ATTRIBUTE_PICKLIST_PRINT_DATE + "=" + 
				DateHandler.getOracleToDateFunction(reprintPicklistIdViewBean.getPicklistPrintDate()) + " " +
			"where " + ATTRIBUTE_HUB + "=" +
				StringHandler.nullIfZero(reprintPicklistIdViewBean.getHub());

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

		Collection reprintPicklistIdViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			ReprintPicklistIdViewBean reprintPicklistIdViewBean = new ReprintPicklistIdViewBean();
			load(dataSetRow, reprintPicklistIdViewBean);
			reprintPicklistIdViewBeanColl.add(reprintPicklistIdViewBean);
		}

		return reprintPicklistIdViewBeanColl;
	}
 
        public Collection select(SearchCriteria criteria,SortCriteria sortCriteria) throws BaseException {
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
        
        public Collection select(SearchCriteria criteria,SortCriteria sortCriteria, Connection conn) throws BaseException {
 		Collection reprintPicklistIdViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

               if (sortCriteria != null) {
                  query += getOrderByClause(sortCriteria);
               }            
                
		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			ReprintPicklistIdViewBean reprintPicklistIdViewBean = new ReprintPicklistIdViewBean();
			load(dataSetRow, reprintPicklistIdViewBean);
			reprintPicklistIdViewBeanColl.add(reprintPicklistIdViewBean);
		}

		return reprintPicklistIdViewBeanColl;          
        }

   public Collection selectDistinctPicklist(SearchCriteria criteria,SortCriteria sortCriteria) throws BaseException {
 		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = selectDistinctPicklist(criteria, sortCriteria, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
        }

   public Collection selectDistinctPicklist(SearchCriteria criteria,SortCriteria sortCriteria, Connection conn) throws BaseException {
 		Collection reprintPicklistIdViewBeanColl = new Vector();

		String query = "select distinct "+ATTRIBUTE_HUB+","+ATTRIBUTE_PICKLIST_ID+","+ATTRIBUTE_PICKLIST_PRINT_DATE+" from " + TABLE + " " +
			getWhereClause(criteria);

     if (sortCriteria != null) {
        query += getOrderByClause(sortCriteria);
     }

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			ReprintPicklistIdViewBean reprintPicklistIdViewBean = new ReprintPicklistIdViewBean();
			load(dataSetRow, reprintPicklistIdViewBean);
			reprintPicklistIdViewBeanColl.add(reprintPicklistIdViewBean);
		}

		return reprintPicklistIdViewBeanColl;
   }  
}