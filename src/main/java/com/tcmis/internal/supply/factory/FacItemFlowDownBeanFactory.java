package com.tcmis.internal.supply.factory;


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
import com.tcmis.internal.supply.beans.FacItemFlowDownBean;


/******************************************************************************
 * CLASSNAME: FacItemFlowDownBeanFactory <br>
 * @version: 1.0, Aug 30, 2007 <br>
 *****************************************************************************/


public class FacItemFlowDownBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
	public String ATTRIBUTE_FAC_PART_NO = "FAC_PART_NO";
	public String ATTRIBUTE_FLOW_DOWN = "FLOW_DOWN";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";

	//table name
	public String TABLE = "FAC_ITEM_FLOW_DOWN";


	//constructor
	public FacItemFlowDownBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("catalogId")) {
			return ATTRIBUTE_CATALOG_ID;
		}
		else if(attributeName.equals("facPartNo")) {
			return ATTRIBUTE_FAC_PART_NO;
		}
		else if(attributeName.equals("flowDown")) {
			return ATTRIBUTE_FLOW_DOWN;
		}
		else if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, FacItemFlowDownBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(FacItemFlowDownBean facItemFlowDownBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("catalogId", "SearchCriterion.EQUALS",
			"" + facItemFlowDownBean.getCatalogId());

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


	public int delete(FacItemFlowDownBean facItemFlowDownBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("catalogId", "SearchCriterion.EQUALS",
			"" + facItemFlowDownBean.getCatalogId());

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
	public int insert(FacItemFlowDownBean facItemFlowDownBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(facItemFlowDownBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(FacItemFlowDownBean facItemFlowDownBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_CATALOG_ID + "," +
			ATTRIBUTE_FAC_PART_NO + "," +
			ATTRIBUTE_FLOW_DOWN + "," +
			ATTRIBUTE_COMPANY_ID + ")" +
			" values (" +
			SqlHandler.delimitString(facItemFlowDownBean.getCatalogId()) + "," +
			SqlHandler.delimitString(facItemFlowDownBean.getFacPartNo()) + "," +
			SqlHandler.delimitString(facItemFlowDownBean.getFlowDown()) + "," +
			SqlHandler.delimitString(facItemFlowDownBean.getCompanyId()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(FacItemFlowDownBean facItemFlowDownBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(facItemFlowDownBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(FacItemFlowDownBean facItemFlowDownBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_CATALOG_ID + "=" + 
				SqlHandler.delimitString(facItemFlowDownBean.getCatalogId()) + "," +
			ATTRIBUTE_FAC_PART_NO + "=" + 
				SqlHandler.delimitString(facItemFlowDownBean.getFacPartNo()) + "," +
			ATTRIBUTE_FLOW_DOWN + "=" + 
				SqlHandler.delimitString(facItemFlowDownBean.getFlowDown()) + "," +
			ATTRIBUTE_COMPANY_ID + "=" + 
				SqlHandler.delimitString(facItemFlowDownBean.getCompanyId()) + " " +
			"where " + ATTRIBUTE_CATALOG_ID + "=" +
				facItemFlowDownBean.getCatalogId();

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

		Collection facItemFlowDownBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			FacItemFlowDownBean facItemFlowDownBean = new FacItemFlowDownBean();
			load(dataSetRow, facItemFlowDownBean);
			facItemFlowDownBeanColl.add(facItemFlowDownBean);
		}

		return facItemFlowDownBeanColl;
	}
}