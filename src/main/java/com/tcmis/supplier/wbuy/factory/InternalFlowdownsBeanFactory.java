package com.tcmis.supplier.wbuy.factory;


import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.supplier.wbuy.beans.InternalFlowdownsBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;


/******************************************************************************
 * CLASSNAME: InternalFlowdownsBeanFactory <br>
 * @version: 1.0, Sep 9, 2005 <br>
 *****************************************************************************/


public class InternalFlowdownsBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
	public String ATTRIBUTE_FLOW_DOWN = "FLOW_DOWN";
	public String ATTRIBUTE_FLOW_DOWN_DESC = "FLOW_DOWN_DESC";
	public String ATTRIBUTE_REVISION_DATE = "REVISION_DATE";
	public String ATTRIBUTE_FLOW_DOWN_TYPE = "FLOW_DOWN_TYPE";
	public String ATTRIBUTE_CONTENT = "CONTENT";
	public String ATTRIBUTE_ON_LINE = "ON_LINE";
	public String ATTRIBUTE_ATTACH = "ATTACH";
	public String ATTRIBUTE_OK = "OK";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_COLOR = "COLOR";

	//table name
	public String TABLE = "INTERNAL_FLOWDOWNS";


	//constructor
	public InternalFlowdownsBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("catalogId")) {
			return ATTRIBUTE_CATALOG_ID;
		}
		else if(attributeName.equals("flowDown")) {
			return ATTRIBUTE_FLOW_DOWN;
		}
		else if(attributeName.equals("flowDownDesc")) {
			return ATTRIBUTE_FLOW_DOWN_DESC;
		}
		else if(attributeName.equals("revisionDate")) {
			return ATTRIBUTE_REVISION_DATE;
		}
		else if(attributeName.equals("content")) {
			return ATTRIBUTE_CONTENT;
		}
		else if(attributeName.equals("onLine")) {
			return ATTRIBUTE_ON_LINE;
		}
		else if(attributeName.equals("attach")) {
			return ATTRIBUTE_ATTACH;
		}
		else if(attributeName.equals("ok")) {
			return ATTRIBUTE_OK;
		}
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("color")) {
			return ATTRIBUTE_COLOR;
		}
		else if(attributeName.equals("flowDownType")) {
			return ATTRIBUTE_FLOW_DOWN_TYPE;
		}	
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, InternalFlowdownsBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(InternalFlowdownsBean internalFlowdownsBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
			"" + internalFlowdownsBean.getCompanyId());

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


	public int delete(InternalFlowdownsBean internalFlowdownsBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
			"" + internalFlowdownsBean.getCompanyId());

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
	public int insert(InternalFlowdownsBean internalFlowdownsBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(internalFlowdownsBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(InternalFlowdownsBean internalFlowdownsBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_CATALOG_ID + "," +
			ATTRIBUTE_FLOW_DOWN + "," +
			ATTRIBUTE_FLOW_DOWN_DESC + "," +
			ATTRIBUTE_REVISION_DATE + "," +
			ATTRIBUTE_CONTENT + "," +
			ATTRIBUTE_ON_LINE + "," +
			ATTRIBUTE_ATTACH + "," +
			ATTRIBUTE_OK + "," +
			ATTRIBUTE_ITEM_ID + "," +
			ATTRIBUTE_COLOR + ")" +
 values (
			SqlHandler.delimitString(internalFlowdownsBean.getCompanyId()) + "," +
			SqlHandler.delimitString(internalFlowdownsBean.getCatalogId()) + "," +
			SqlHandler.delimitString(internalFlowdownsBean.getFlowDown()) + "," +
			SqlHandler.delimitString(internalFlowdownsBean.getFlowDownDesc()) + "," +
			SqlHandler.delimitString(internalFlowdownsBean.getRevisionDate()) + "," +
			SqlHandler.delimitString(internalFlowdownsBean.getContent()) + "," +
			SqlHandler.delimitString(internalFlowdownsBean.getOnLine()) + "," +
			SqlHandler.delimitString(internalFlowdownsBean.getAttach()) + "," +
			SqlHandler.delimitString(internalFlowdownsBean.getOk()) + "," +
			StringHandler.nullIfZero(internalFlowdownsBean.getItemId()) + "," +
			StringHandler.nullIfZero(internalFlowdownsBean.getColor()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(InternalFlowdownsBean internalFlowdownsBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(internalFlowdownsBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(InternalFlowdownsBean internalFlowdownsBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_COMPANY_ID + "=" + 
				SqlHandler.delimitString(internalFlowdownsBean.getCompanyId()) + "," +
			ATTRIBUTE_CATALOG_ID + "=" + 
				SqlHandler.delimitString(internalFlowdownsBean.getCatalogId()) + "," +
			ATTRIBUTE_FLOW_DOWN + "=" + 
				SqlHandler.delimitString(internalFlowdownsBean.getFlowDown()) + "," +
			ATTRIBUTE_FLOW_DOWN_DESC + "=" + 
				SqlHandler.delimitString(internalFlowdownsBean.getFlowDownDesc()) + "," +
			ATTRIBUTE_REVISION_DATE + "=" + 
				SqlHandler.delimitString(internalFlowdownsBean.getRevisionDate()) + "," +
			ATTRIBUTE_CONTENT + "=" + 
				SqlHandler.delimitString(internalFlowdownsBean.getContent()) + "," +
			ATTRIBUTE_ON_LINE + "=" + 
				SqlHandler.delimitString(internalFlowdownsBean.getOnLine()) + "," +
			ATTRIBUTE_ATTACH + "=" + 
				SqlHandler.delimitString(internalFlowdownsBean.getAttach()) + "," +
			ATTRIBUTE_OK + "=" + 
				SqlHandler.delimitString(internalFlowdownsBean.getOk()) + "," +
			ATTRIBUTE_ITEM_ID + "=" + 
				StringHandler.nullIfZero(internalFlowdownsBean.getItemId()) + "," +
			ATTRIBUTE_COLOR + "=" + 
				StringHandler.nullIfZero(internalFlowdownsBean.getColor()) + " " +
			"where " + ATTRIBUTE_COMPANY_ID + "=" +
				StringHandler.nullIfZero(internalFlowdownsBean.getCompanyId());

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

		Collection internalFlowdownsBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			InternalFlowdownsBean internalFlowdownsBean = new InternalFlowdownsBean();
			load(dataSetRow, internalFlowdownsBean);
			internalFlowdownsBeanColl.add(internalFlowdownsBean);
		}

		return internalFlowdownsBeanColl;
	}
        
	public Collection select(String query)
		throws BaseException {

		Collection internalFlowdownsBeanColl = new Vector();

  		Connection connection = null;
    	try {
    	           connection = this.getDbManager().getConnection();

			DataSet dataSet = new SqlManager().select(connection, query);
	
			Iterator dataIter = dataSet.iterator();
	
			while (dataIter.hasNext()) {
				DataSetRow dataSetRow = (DataSetRow)dataIter.next();
				InternalFlowdownsBean internalFlowdownsBean = new InternalFlowdownsBean();
				load(dataSetRow, internalFlowdownsBean);
				internalFlowdownsBeanColl.add(internalFlowdownsBean);
			}
    	} catch (Exception e) {
             return internalFlowdownsBeanColl;
        } finally {
        	 this.getDbManager().returnConnection(connection);
		}
                
		return internalFlowdownsBeanColl;
	}
}