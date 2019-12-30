package com.tcmis.client.catalog.factory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.catalog.beans.CatalogPartItemGroupBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;

/******************************************************************************
 * CLASSNAME: CatalogPartItemGroupBeanFactory <br>
 * @version: 1.0, May 13, 2005 <br>
 *****************************************************************************/

public class CatalogPartItemGroupBeanFactory
 extends BaseBeanFactory {

 Log log = LogFactory.getLog(this.getClass());

 //column names
 public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
 public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
 public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
 public String ATTRIBUTE_PART_GROUP_NO = "PART_GROUP_NO";
 public String ATTRIBUTE_PRIORITY = "PRIORITY";
 public String ATTRIBUTE_STATUS = "STATUS";
 public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
 public String ATTRIBUTE_BUNDLE = "BUNDLE";
 public String ATTRIBUTE_CATALOG_ITEM_ID = "CATALOG_ITEM_ID";
 public String ATTRIBUTE_DATE_INSERTED = "DATE_INSERTED";

 //table name
 public String TABLE = "CATALOG_PART_ITEM_GROUP";

 //constructor
 public CatalogPartItemGroupBeanFactory(DbManager dbManager) {
	super(dbManager);
 }

 //get column names
 public String getColumnName(String attributeName) {
	if (attributeName.equals("companyId")) {
	 return ATTRIBUTE_COMPANY_ID;
	}
	else if (attributeName.equals("catalogId")) {
	 return ATTRIBUTE_CATALOG_ID;
	}
	else if (attributeName.equals("catPartNo")) {
	 return ATTRIBUTE_CAT_PART_NO;
	}
	else if (attributeName.equals("partGroupNo")) {
	 return ATTRIBUTE_PART_GROUP_NO;
	}
	else if (attributeName.equals("priority")) {
	 return ATTRIBUTE_PRIORITY;
	}
	else if (attributeName.equals("status")) {
	 return ATTRIBUTE_STATUS;
	}
	else if (attributeName.equals("itemId")) {
	 return ATTRIBUTE_ITEM_ID;
	}
	else if (attributeName.equals("bundle")) {
	 return ATTRIBUTE_BUNDLE;
	}
	else if (attributeName.equals("catalogItemId")) {
	 return ATTRIBUTE_CATALOG_ITEM_ID;
	}
	else if (attributeName.equals("dateInserted")) {
	 return ATTRIBUTE_DATE_INSERTED;
	}
	else {
	 return super.getColumnName(attributeName);
	}
 }

 //get column types
 public int getType(String attributeName) {
	return super.getType(attributeName, CatalogPartItemGroupBean.class);
 }

//you need to verify the primary key(s) before uncommenting this
 /*
//delete
	public int delete(CatalogPartItemGroupBean catalogPartItemGroupBean)
	 throws BaseException {
	 SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
		"" + catalogPartItemGroupBean.getCompanyId());
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
	public int delete(CatalogPartItemGroupBean catalogPartItemGroupBean, Connection conn)
	 throws BaseException {
	 SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
		"" + catalogPartItemGroupBean.getCompanyId());
	 return delete(criteria, conn);
	}
	*/

 public int delete(SearchCriteria criteria) throws BaseException {

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

 public int delete(SearchCriteria criteria,
	Connection conn) throws BaseException {

	String sqlQuery = " delete from " + TABLE + " " + getWhereClause(criteria);

	return new SqlManager().update(conn, sqlQuery);
 }

//you need to verify the primary key(s) before uncommenting this
 /*
//insert
	public int insert(CatalogPartItemGroupBean catalogPartItemGroupBean)
	 throws BaseException {
	 Connection connection = null;
	 int result = 0;
	 try {
		connection = getDbManager().getConnection();
		result = insert(catalogPartItemGroupBean, connection);
	 }
	 finally {
		this.getDbManager().returnConnection(connection);
	 }
	 return result;
	}
	public int insert(CatalogPartItemGroupBean catalogPartItemGroupBean, Connection conn)
	 throws BaseException {
	 SqlManager sqlManager = new SqlManager();
	 String query  =
		"insert into " + TABLE + " (" +
		ATTRIBUTE_COMPANY_ID + "," +
		ATTRIBUTE_CATALOG_ID + "," +
		ATTRIBUTE_CAT_PART_NO + "," +
		ATTRIBUTE_PART_GROUP_NO + "," +
		ATTRIBUTE_PRIORITY + "," +
		ATTRIBUTE_STATUS + "," +
		ATTRIBUTE_ITEM_ID + "," +
		ATTRIBUTE_BUNDLE + "," +
		ATTRIBUTE_CATALOG_ITEM_ID + "," +
		ATTRIBUTE_DATE_INSERTED + ")" +
		" values (" +
		SqlHandler.delimitString(catalogPartItemGroupBean.getCompanyId()) + "," +
		SqlHandler.delimitString(catalogPartItemGroupBean.getCatalogId()) + "," +
		SqlHandler.delimitString(catalogPartItemGroupBean.getCatPartNo()) + "," +
		catalogPartItemGroupBean.getPartGroupNo() + "," +
		catalogPartItemGroupBean.getPriority() + "," +
		SqlHandler.delimitString(catalogPartItemGroupBean.getStatus()) + "," +
		catalogPartItemGroupBean.getItemId() + "," +
		SqlHandler.delimitString(catalogPartItemGroupBean.getBundle()) + "," +
	 SqlHandler.delimitString(catalogPartItemGroupBean.getCatalogItemId()) + "," +
		DateHandler.getOracleToDateFunction(catalogPartItemGroupBean.getDateInserted()) + ")";
	 return sqlManager.update(conn, query);
	}
//update
	public int update(CatalogPartItemGroupBean catalogPartItemGroupBean)
	 throws BaseException {
	 Connection connection = null;
	 int result = 0;
	 try {
		connection = getDbManager().getConnection();
		result = update(catalogPartItemGroupBean, connection);
	 }
	 finally {
		this.getDbManager().returnConnection(connection);
	 }
	 return result;
	}
	public int update(CatalogPartItemGroupBean catalogPartItemGroupBean, Connection conn)
	 throws BaseException {
	 String query  = "update " + TABLE + " set " +
		ATTRIBUTE_COMPANY_ID + "=" +
		 SqlHandler.delimitString(catalogPartItemGroupBean.getCompanyId()) + "," +
		ATTRIBUTE_CATALOG_ID + "=" +
		 SqlHandler.delimitString(catalogPartItemGroupBean.getCatalogId()) + "," +
		ATTRIBUTE_CAT_PART_NO + "=" +
		 SqlHandler.delimitString(catalogPartItemGroupBean.getCatPartNo()) + "," +
		ATTRIBUTE_PART_GROUP_NO + "=" +
	 StringHandler.nullIfZero(catalogPartItemGroupBean.getPartGroupNo()) + "," +
		ATTRIBUTE_PRIORITY + "=" +
		 StringHandler.nullIfZero(catalogPartItemGroupBean.getPriority()) + "," +
		ATTRIBUTE_STATUS + "=" +
		 SqlHandler.delimitString(catalogPartItemGroupBean.getStatus()) + "," +
		ATTRIBUTE_ITEM_ID + "=" +
		 StringHandler.nullIfZero(catalogPartItemGroupBean.getItemId()) + "," +
		ATTRIBUTE_BUNDLE + "=" +
		 SqlHandler.delimitString(catalogPartItemGroupBean.getBundle()) + "," +
		ATTRIBUTE_CATALOG_ITEM_ID + "=" +
	 SqlHandler.delimitString(catalogPartItemGroupBean.getCatalogItemId()) + "," +
		ATTRIBUTE_DATE_INSERTED + "=" +
		 DateHandler.getOracleToDateFunction(catalogPartItemGroupBean.getDateInserted()) + " " +
		"where " + ATTRIBUTE_COMPANY_ID + "=" +
		 catalogPartItemGroupBean.getCompanyId();
	 return new SqlManager().update(conn, query);
	}
	*/

 //select
 public Collection select(SearchCriteria criteria) throws BaseException {

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

 public Collection select(SearchCriteria criteria,
	Connection conn) throws BaseException {

	Collection catalogPartItemGroupBeanColl = new Vector();

	String query = "select * from " + TABLE + " " + getWhereClause(criteria);

	DataSet dataSet = new SqlManager().select(conn, query);

	Iterator dataIter = dataSet.iterator();

	while (dataIter.hasNext()) {
	 DataSetRow dataSetRow = (DataSetRow) dataIter.next();
	 CatalogPartItemGroupBean catalogPartItemGroupBean = new
		CatalogPartItemGroupBean();
	 load(dataSetRow, catalogPartItemGroupBean);
	 catalogPartItemGroupBeanColl.add(catalogPartItemGroupBean);
	}

	return catalogPartItemGroupBeanColl;
 }
}