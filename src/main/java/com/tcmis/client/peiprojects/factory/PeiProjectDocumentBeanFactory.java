package com.tcmis.client.peiprojects.factory;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
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
import com.tcmis.client.peiprojects.beans.PeiProjectDocumentBean;


/******************************************************************************
 * CLASSNAME: PeiProjectDocumentBeanFactory <br>
 * @version: 1.0, Dec 15, 2005 <br>
 *****************************************************************************/


public class PeiProjectDocumentBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_PROJECT_ID = "PROJECT_ID";
	public String ATTRIBUTE_DOCUMENT_ID = "DOCUMENT_ID";
	public String ATTRIBUTE_DOCUMENT_NAME = "DOCUMENT_NAME";
	public String ATTRIBUTE_URL = "URL";
	public String ATTRIBUTE_CUSTOMER_DISPLAY = "CUSTOMER_DISPLAY";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";

	//table name
	public String TABLE = "PEI_PROJECT_DOCUMENT";


	//constructor
	public PeiProjectDocumentBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("projectId")) {
			return ATTRIBUTE_PROJECT_ID;
		}
		else if(attributeName.equals("documentId")) {
			return ATTRIBUTE_DOCUMENT_ID;
		}
		else if(attributeName.equals("documentName")) {
			return ATTRIBUTE_DOCUMENT_NAME;
		}
		else if(attributeName.equals("url")) {
			return ATTRIBUTE_URL;
		}
		else if(attributeName.equals("customerDisplay")) {
			return ATTRIBUTE_CUSTOMER_DISPLAY;
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
		return super.getType(attributeName, PeiProjectDocumentBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(PeiProjectDocumentBean peiProjectDocumentBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("projectId", "SearchCriterion.EQUALS",
			"" + peiProjectDocumentBean.getProjectId());

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


	public int delete(PeiProjectDocumentBean peiProjectDocumentBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("projectId", "SearchCriterion.EQUALS",
			"" + peiProjectDocumentBean.getProjectId());

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
	public int insert(PeiProjectDocumentBean peiProjectDocumentBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(peiProjectDocumentBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(PeiProjectDocumentBean peiProjectDocumentBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
			ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_PROJECT_ID + "," +
			ATTRIBUTE_DOCUMENT_ID + "," +
			ATTRIBUTE_DOCUMENT_NAME + "," +
			ATTRIBUTE_URL + "," +
			ATTRIBUTE_CUSTOMER_DISPLAY + ")" +
			" values (" +
			SqlHandler.delimitString(peiProjectDocumentBean.getCompanyId()) + "," +
			peiProjectDocumentBean.getProjectId() + "," +
			peiProjectDocumentBean.getDocumentId() + "," +
			SqlHandler.delimitString(peiProjectDocumentBean.getDocumentName()) + "," +
			SqlHandler.delimitString(peiProjectDocumentBean.getUrl()) + "," +
			SqlHandler.delimitString(peiProjectDocumentBean.getCustomerDisplay()) + ")";

		return sqlManager.update(conn, query);
	}

	 //insert new project documents
	 public int insertNewProjectDocuments(BigDecimal oldProjectId,BigDecimal newProjectId) throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
		 connection = getDbManager().getConnection();
		 result = insertNewProjectDocuments(oldProjectId,newProjectId, connection);
		}
		finally {
		 this.getDbManager().returnConnection(connection);
		}
		return result;
	 }

	 public int insertNewProjectDocuments(BigDecimal oldProjectId,BigDecimal newProjectId, Connection conn) throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query =
		 "insert into " + TABLE + " select " +
		 ATTRIBUTE_COMPANY_ID + "," +
		 newProjectId + "," +
		 ATTRIBUTE_DOCUMENT_ID + "," +
		 ATTRIBUTE_DOCUMENT_NAME + "," +
		 ATTRIBUTE_URL + "," +
		 ATTRIBUTE_CUSTOMER_DISPLAY +
		 " from "+ TABLE +" where "+ATTRIBUTE_PROJECT_ID+"="+oldProjectId+"";

	return sqlManager.update(conn, query);
 }

/*
	//update
	public int update(PeiProjectDocumentBean peiProjectDocumentBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(peiProjectDocumentBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(PeiProjectDocumentBean peiProjectDocumentBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_PROJECT_ID + "=" +
				StringHandler.nullIfZero(peiProjectDocumentBean.getProjectId()) + "," +
			ATTRIBUTE_DOCUMENT_ID + "=" +
				StringHandler.nullIfZero(peiProjectDocumentBean.getDocumentId()) + "," +
			ATTRIBUTE_DOCUMENT_NAME + "=" +
				SqlHandler.delimitString(peiProjectDocumentBean.getDocumentName()) + "," +
			ATTRIBUTE_URL + "=" +
				SqlHandler.delimitString(peiProjectDocumentBean.getUrl()) + "," +
			ATTRIBUTE_CUSTOMER_DISPLAY + "=" +
				SqlHandler.delimitString(peiProjectDocumentBean.getCustomerDisplay()) + " " +
			"where " + ATTRIBUTE_PROJECT_ID + "=" +
				peiProjectDocumentBean.getProjectId();

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

		Collection peiProjectDocumentBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			PeiProjectDocumentBean peiProjectDocumentBean = new PeiProjectDocumentBean();
			load(dataSetRow, peiProjectDocumentBean);
			peiProjectDocumentBeanColl.add(peiProjectDocumentBean);
		}

		return peiProjectDocumentBeanColl;
	}
}