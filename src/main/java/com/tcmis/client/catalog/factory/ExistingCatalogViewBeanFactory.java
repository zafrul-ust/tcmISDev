package com.tcmis.client.catalog.factory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.catalog.beans.ExistingCatalogViewBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;

/******************************************************************************
 * CLASSNAME: ExistingCatalogView BeanFactory <br>
 * @version: 1.0, Dec 13, 2005 <br>
 *****************************************************************************/

public class ExistingCatalogViewBeanFactory
 extends BaseBeanFactory {

 Log log = LogFactory.getLog(this.getClass());

 //column names
 public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
 public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
 public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
 public String ATTRIBUTE_PART_GROUP_NO = "PART_GROUP_NO";
 public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
 public String ATTRIBUTE_STATUS = "STATUS";
 public String ATTRIBUTE_PRIORITY = "PRIORITY";
 public String ATTRIBUTE_KIT_PACKAGING = "KIT_PACKAGING";
 public String ATTRIBUTE_ITEM_DESC = "ITEM_DESC";

 //table name
 public String TABLE = "EXISTING_CATALOG_VIEW ";

 //constructor
 public ExistingCatalogViewBeanFactory(DbManager dbManager) {
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
	else if (attributeName.equals("itemId")) {
	 return ATTRIBUTE_ITEM_ID;
	}
	else if (attributeName.equals("status")) {
	 return ATTRIBUTE_STATUS;
	}
	else if (attributeName.equals("priority")) {
	 return ATTRIBUTE_PRIORITY;
	}
	else if (attributeName.equals("kitPackaging")) {
	 return ATTRIBUTE_KIT_PACKAGING;
	}
	else if (attributeName.equals("itemDesc")) {
	 return ATTRIBUTE_ITEM_DESC;
	}
	else {
	 return super.getColumnName(attributeName);
	}
 }

 //get column types
 public int getType(String attributeName) {
	return super.getType(attributeName, ExistingCatalogViewBean.class);
 }

//you need to verify the primary key(s) before uncommenting this
 /*
//delete
	public int delete(ExistingCatalogView Bean existingCatalogView Bean)
	 throws BaseException {
	 SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
		"" + existingCatalogView Bean.getCompanyId());
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
	public int delete(ExistingCatalogView Bean existingCatalogView Bean, Connection conn)
	 throws BaseException {
	 SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
		"" + existingCatalogView Bean.getCompanyId());
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
	public int insert(ExistingCatalogView Bean existingCatalogView Bean)
	 throws BaseException {
	 Connection connection = null;
	 int result = 0;
	 try {
		connection = getDbManager().getConnection();
		result = insert(existingCatalogView Bean, connection);
	 }
	 finally {
		this.getDbManager().returnConnection(connection);
	 }
	 return result;
	}
	public int insert(ExistingCatalogView Bean existingCatalogView Bean, Connection conn)
	 throws BaseException {
	 SqlManager sqlManager = new SqlManager();
	 String query  =
		"insert into " + TABLE + " (" +
		ATTRIBUTE_COMPANY_ID + "," +
		ATTRIBUTE_CATALOG_ID + "," +
		ATTRIBUTE_CAT_PART_NO + "," +
		ATTRIBUTE_PART_GROUP_NO + "," +
		ATTRIBUTE_ITEM_ID + "," +
		ATTRIBUTE_STATUS + "," +
		ATTRIBUTE_PRIORITY + "," +
		ATTRIBUTE_KIT_PACKAGING + "," +
		ATTRIBUTE_ITEM_DESC + ")" +
		" values (" +
		SqlHandler.delimitString(existingCatalogView Bean.getCompanyId()) + "," +
		SqlHandler.delimitString(existingCatalogView Bean.getCatalogId()) + "," +
		SqlHandler.delimitString(existingCatalogView Bean.getCatPartNo()) + "," +
		existingCatalogView Bean.getPartGroupNo() + "," +
		existingCatalogView Bean.getItemId() + "," +
		SqlHandler.delimitString(existingCatalogView Bean.getStatus()) + "," +
		existingCatalogView Bean.getPriority() + "," +
	 SqlHandler.delimitString(existingCatalogView Bean.getKitPackaging()) + "," +
		SqlHandler.delimitString(existingCatalogView Bean.getItemDesc()) + ")";
	 return sqlManager.update(conn, query);
	}
//update
	public int update(ExistingCatalogView Bean existingCatalogView Bean)
	 throws BaseException {
	 Connection connection = null;
	 int result = 0;
	 try {
		connection = getDbManager().getConnection();
		result = update(existingCatalogView Bean, connection);
	 }
	 finally {
		this.getDbManager().returnConnection(connection);
	 }
	 return result;
	}
	public int update(ExistingCatalogView Bean existingCatalogView Bean, Connection conn)
	 throws BaseException {
	 String query  = "update " + TABLE + " set " +
		ATTRIBUTE_COMPANY_ID + "=" +
		 SqlHandler.delimitString(existingCatalogView Bean.getCompanyId()) + "," +
		ATTRIBUTE_CATALOG_ID + "=" +
		 SqlHandler.delimitString(existingCatalogView Bean.getCatalogId()) + "," +
		ATTRIBUTE_CAT_PART_NO + "=" +
		 SqlHandler.delimitString(existingCatalogView Bean.getCatPartNo()) + "," +
		ATTRIBUTE_PART_GROUP_NO + "=" +
	 StringHandler.nullIfZero(existingCatalogView Bean.getPartGroupNo()) + "," +
		ATTRIBUTE_ITEM_ID + "=" +
		 StringHandler.nullIfZero(existingCatalogView Bean.getItemId()) + "," +
		ATTRIBUTE_STATUS + "=" +
		 SqlHandler.delimitString(existingCatalogView Bean.getStatus()) + "," +
		ATTRIBUTE_PRIORITY + "=" +
		 StringHandler.nullIfZero(existingCatalogView Bean.getPriority()) + "," +
		ATTRIBUTE_KIT_PACKAGING + "=" +
	 SqlHandler.delimitString(existingCatalogView Bean.getKitPackaging()) + "," +
		ATTRIBUTE_ITEM_DESC + "=" +
		 SqlHandler.delimitString(existingCatalogView Bean.getItemDesc()) + " " +
		"where " + ATTRIBUTE_COMPANY_ID + "=" +
		 existingCatalogView Bean.getCompanyId();
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

	Collection existingCatalogViewBeanColl = new Vector();

	String query = "select * from " + TABLE + " " + getWhereClause(criteria);

	DataSet dataSet = new SqlManager().select(conn, query);

	Iterator dataIter = dataSet.iterator();

	while (dataIter.hasNext()) {
	 DataSetRow dataSetRow = (DataSetRow) dataIter.next();
	 ExistingCatalogViewBean existingCatalogViewBean = new
		ExistingCatalogViewBean();
	 load(dataSetRow, existingCatalogViewBean);
	 existingCatalogViewBeanColl.add(existingCatalogViewBean);
	}

	return existingCatalogViewBeanColl;
 }
}