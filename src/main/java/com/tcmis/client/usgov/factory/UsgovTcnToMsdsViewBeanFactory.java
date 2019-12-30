package com.tcmis.client.usgov.factory;


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
import com.tcmis.client.usgov.beans.UsgovTcnToMsdsViewBean;


/******************************************************************************
 * CLASSNAME: UsgovTcnToMsdsViewBeanFactory <br>
 * @version: 1.0, May 20, 2008 <br>
 *****************************************************************************/


public class UsgovTcnToMsdsViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_USGOV_TCN = "USGOV_TCN";
	public String ATTRIBUTE_MATERIAL_ID = "MATERIAL_ID";
	public String ATTRIBUTE_REVISION_DATE = "REVISION_DATE";
	public String ATTRIBUTE_URL = "URL";
    public String ATTRIBUTE_SHIPMENT_ID = "SHIPMENT_ID";
    public String ATTRIBUTE_MFG_LITERATURE_CONTENT = "MFG_LITERATURE_CONTENT";
    public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
    public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";

    //table name
	public String TABLE = "USGOV_TCN_TO_MSDS_VIEW";


	//constructor
	public UsgovTcnToMsdsViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("usgovTcn")) {
			return ATTRIBUTE_USGOV_TCN;
		}
		else if(attributeName.equals("materialId")) {
			return ATTRIBUTE_MATERIAL_ID;
		}
		else if(attributeName.equals("revisionDate")) {
			return ATTRIBUTE_REVISION_DATE;
		}
		else if(attributeName.equals("url")) {
			return ATTRIBUTE_URL;
		}
		else if(attributeName.equals("shipmentId")) {
			return ATTRIBUTE_SHIPMENT_ID;
		}
		else if(attributeName.equals("mfgLiteratureContent")) {
			return ATTRIBUTE_MFG_LITERATURE_CONTENT;
		}
		else if(attributeName.equals("prNumber")) {
			return ATTRIBUTE_PR_NUMBER;
		}
		else if(attributeName.equals("lineItem")) {
			return ATTRIBUTE_LINE_ITEM;
		}
        else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, UsgovTcnToMsdsViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(UsgovTcnToMsdsViewBean usgovTcnToMsdsViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("usgovTcn", "SearchCriterion.EQUALS",
			"" + usgovTcnToMsdsViewBean.getUsgovTcn());

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


	public int delete(UsgovTcnToMsdsViewBean usgovTcnToMsdsViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("usgovTcn", "SearchCriterion.EQUALS",
			"" + usgovTcnToMsdsViewBean.getUsgovTcn());

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
	public int insert(UsgovTcnToMsdsViewBean usgovTcnToMsdsViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(usgovTcnToMsdsViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(UsgovTcnToMsdsViewBean usgovTcnToMsdsViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_USGOV_TCN + "," +
			ATTRIBUTE_MATERIAL_ID + "," +
			ATTRIBUTE_REVISION_DATE + "," +
			ATTRIBUTE_URL + ")" +
			" values (" +
			SqlHandler.delimitString(usgovTcnToMsdsViewBean.getUsgovTcn()) + "," +
			usgovTcnToMsdsViewBean.getMaterialId() + "," +
			DateHandler.getOracleToDateFunction(usgovTcnToMsdsViewBean.getRevisionDate()) + "," +
			SqlHandler.delimitString(usgovTcnToMsdsViewBean.getUrl()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(UsgovTcnToMsdsViewBean usgovTcnToMsdsViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(usgovTcnToMsdsViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(UsgovTcnToMsdsViewBean usgovTcnToMsdsViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_USGOV_TCN + "=" + 
				SqlHandler.delimitString(usgovTcnToMsdsViewBean.getUsgovTcn()) + "," +
			ATTRIBUTE_MATERIAL_ID + "=" + 
				StringHandler.nullIfZero(usgovTcnToMsdsViewBean.getMaterialId()) + "," +
			ATTRIBUTE_REVISION_DATE + "=" + 
				DateHandler.getOracleToDateFunction(usgovTcnToMsdsViewBean.getRevisionDate()) + "," +
			ATTRIBUTE_URL + "=" + 
				SqlHandler.delimitString(usgovTcnToMsdsViewBean.getUrl()) + " " +
			"where " + ATTRIBUTE_USGOV_TCN + "=" +
				usgovTcnToMsdsViewBean.getUsgovTcn();

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

		Collection usgovTcnToMsdsViewBeanColl = new Vector();

		String query = "select distinct "+ATTRIBUTE_MATERIAL_ID+","+ ATTRIBUTE_REVISION_DATE+ ","+ATTRIBUTE_URL+"  from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);
        if(log.isDebugEnabled()){
            log.debug("QUERY:" + query);
        }

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			UsgovTcnToMsdsViewBean usgovTcnToMsdsViewBean = new UsgovTcnToMsdsViewBean();
			load(dataSetRow, usgovTcnToMsdsViewBean);
			usgovTcnToMsdsViewBeanColl.add(usgovTcnToMsdsViewBean);
		}

		return usgovTcnToMsdsViewBeanColl;
	}
}