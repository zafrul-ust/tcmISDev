package com.tcmis.client.catalog.factory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.catalog.beans.FacLocAppPartCommentsViewBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;

/******************************************************************************
 * CLASSNAME: FacLocAppPartCommentsViewBeanFactory <br>
 * @version: 1.0, Jul 19, 2005 <br>
 *****************************************************************************/

public class FacLocAppPartCommentsViewBeanFactory
 extends BaseBeanFactory {

 Log log = LogFactory.getLog(this.getClass());

 //column names
 public String ATTRIBUTE_COMMENT_ID = "COMMENT_ID";
 public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
 public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
 public String ATTRIBUTE_APPLICATION = "APPLICATION";
 public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
 public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
 public String ATTRIBUTE_PART_GROUP_NO = "PART_GROUP_NO";
 public String ATTRIBUTE_DATE_ENTERED = "DATE_ENTERED";
 public String ATTRIBUTE_ENTERED_BY = "ENTERED_BY";
 public String ATTRIBUTE_COMMENTS = "COMMENTS";
 public String ATTRIBUTE_FULL_NAME = "FULL_NAME";
 public String ATTRIBUTE_CATALOG_COMPANY_ID = "CATALOG_COMPANY_ID";
 
 //table name
 public String TABLE = "FAC_LOC_APP_PART_COMMENTS_VIEW";

 //constructor
 public FacLocAppPartCommentsViewBeanFactory(DbManager dbManager) {
	super(dbManager);
 }

 //get column names
 public String getColumnName(String attributeName) {
	if (attributeName.equals("commentId")) {
	 return ATTRIBUTE_COMMENT_ID;
	}
	else if (attributeName.equals("companyId")) {
	 return ATTRIBUTE_COMPANY_ID;
	}
	else if (attributeName.equals("facilityId")) {
	 return ATTRIBUTE_FACILITY_ID;
	}
	else if (attributeName.equals("application")) {
	 return ATTRIBUTE_APPLICATION;
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
	else if (attributeName.equals("dateEntered")) {
	 return ATTRIBUTE_DATE_ENTERED;
	}
	else if (attributeName.equals("enteredBy")) {
	 return ATTRIBUTE_ENTERED_BY;
	}
	else if (attributeName.equals("comments")) {
	 return ATTRIBUTE_COMMENTS;
	}
	else if (attributeName.equals("fullName")) {
	 return ATTRIBUTE_FULL_NAME;
	}
	else if (attributeName.equals("catalogCompanyId")) {
		 return ATTRIBUTE_CATALOG_COMPANY_ID;
   }
	else {
	 return super.getColumnName(attributeName);
	}
 }

 //get column types
 public int getType(String attributeName) {
	return super.getType(attributeName, FacLocAppPartCommentsViewBean.class);
 }

//you need to verify the primary key(s) before uncommenting this
 /*
//delete
	 public int delete(FacLocAppPartCommentsViewBean facLocAppPartCommentsViewBean)
	 throws BaseException {
	 SearchCriteria criteria = new SearchCriteria("commentId", "SearchCriterion.EQUALS",
		"" + facLocAppPartCommentsViewBean.getCommentId());
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
	public int delete(FacLocAppPartCommentsViewBean facLocAppPartCommentsViewBean, Connection conn)
	 throws BaseException {
	 SearchCriteria criteria = new SearchCriteria("commentId", "SearchCriterion.EQUALS",
		"" + facLocAppPartCommentsViewBean.getCommentId());
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
	 public int insert(FacLocAppPartCommentsViewBean facLocAppPartCommentsViewBean)
	 throws BaseException {
	 Connection connection = null;
	 int result = 0;
	 try {
		connection = getDbManager().getConnection();
		result = insert(facLocAppPartCommentsViewBean, connection);
	 }
	 finally {
		this.getDbManager().returnConnection(connection);
	 }
	 return result;
	}
	public int insert(FacLocAppPartCommentsViewBean facLocAppPartCommentsViewBean, Connection conn)
	 throws BaseException {
	 SqlManager sqlManager = new SqlManager();
	 String query  =
		"insert into " + TABLE + " (" +
		ATTRIBUTE_COMMENT_ID + "," +
		ATTRIBUTE_COMPANY_ID + "," +
		ATTRIBUTE_FACILITY_ID + "," +
		ATTRIBUTE_APPLICATION + "," +
		ATTRIBUTE_CATALOG_ID + "," +
		ATTRIBUTE_CAT_PART_NO + "," +
		ATTRIBUTE_PART_GROUP_NO + "," +
		ATTRIBUTE_DATE_ENTERED + "," +
		ATTRIBUTE_ENTERED_BY + "," +
		ATTRIBUTE_COMMENTS + "," +
		ATTRIBUTE_FULL_NAME + ")" +
		" values (" +
		facLocAppPartCommentsViewBean.getCommentId() + "," +
	 SqlHandler.delimitString(facLocAppPartCommentsViewBean.getCompanyId()) + "," +
	 SqlHandler.delimitString(facLocAppPartCommentsViewBean.getFacilityId()) + "," +
	 SqlHandler.delimitString(facLocAppPartCommentsViewBean.getApplication()) + "," +
	 SqlHandler.delimitString(facLocAppPartCommentsViewBean.getCatalogId()) + "," +
	 SqlHandler.delimitString(facLocAppPartCommentsViewBean.getCatPartNo()) + "," +
		facLocAppPartCommentsViewBean.getPartGroupNo() + "," +
		DateHandler.getOracleToDateFunction(facLocAppPartCommentsViewBean.getDateEntered()) + "," +
		facLocAppPartCommentsViewBean.getEnteredBy() + "," +
	 SqlHandler.delimitString(facLocAppPartCommentsViewBean.getComments()) + "," +
	 SqlHandler.delimitString(facLocAppPartCommentsViewBean.getFullName()) + ")";
	 return sqlManager.update(conn, query);
	}
//update
	 public int update(FacLocAppPartCommentsViewBean facLocAppPartCommentsViewBean)
	 throws BaseException {
	 Connection connection = null;
	 int result = 0;
	 try {
		connection = getDbManager().getConnection();
		result = update(facLocAppPartCommentsViewBean, connection);
	 }
	 finally {
		this.getDbManager().returnConnection(connection);
	 }
	 return result;
	}
	public int update(FacLocAppPartCommentsViewBean facLocAppPartCommentsViewBean, Connection conn)
	 throws BaseException {
	 String query  = "update " + TABLE + " set " +
		ATTRIBUTE_COMMENT_ID + "=" +
	 StringHandler.nullIfZero(facLocAppPartCommentsViewBean.getCommentId()) + "," +
		ATTRIBUTE_COMPANY_ID + "=" +
	 SqlHandler.delimitString(facLocAppPartCommentsViewBean.getCompanyId()) + "," +
		ATTRIBUTE_FACILITY_ID + "=" +
	 SqlHandler.delimitString(facLocAppPartCommentsViewBean.getFacilityId()) + "," +
		ATTRIBUTE_APPLICATION + "=" +
	 SqlHandler.delimitString(facLocAppPartCommentsViewBean.getApplication()) + "," +
		ATTRIBUTE_CATALOG_ID + "=" +
	 SqlHandler.delimitString(facLocAppPartCommentsViewBean.getCatalogId()) + "," +
		ATTRIBUTE_CAT_PART_NO + "=" +
	 SqlHandler.delimitString(facLocAppPartCommentsViewBean.getCatPartNo()) + "," +
		ATTRIBUTE_PART_GROUP_NO + "=" +
	 StringHandler.nullIfZero(facLocAppPartCommentsViewBean.getPartGroupNo()) + "," +
		ATTRIBUTE_DATE_ENTERED + "=" +
		 DateHandler.getOracleToDateFunction(facLocAppPartCommentsViewBean.getDateEntered()) + "," +
		ATTRIBUTE_ENTERED_BY + "=" +
	 StringHandler.nullIfZero(facLocAppPartCommentsViewBean.getEnteredBy()) + "," +
		ATTRIBUTE_COMMENTS + "=" +
	 SqlHandler.delimitString(facLocAppPartCommentsViewBean.getComments()) + "," +
		ATTRIBUTE_FULL_NAME + "=" +
	 SqlHandler.delimitString(facLocAppPartCommentsViewBean.getFullName()) + " " +
		"where " + ATTRIBUTE_COMMENT_ID + "=" +
		 facLocAppPartCommentsViewBean.getCommentId();
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

	Collection facLocAppPartCommentsViewBeanColl = new Vector();

	String sortBy = " order by " + ATTRIBUTE_DATE_ENTERED;
	String query = "select * from " + TABLE + " " + getWhereClause(criteria) +
	 sortBy;

	DataSet dataSet = new SqlManager().select(conn, query);

	Iterator dataIter = dataSet.iterator();

	while (dataIter.hasNext()) {
	 DataSetRow dataSetRow = (DataSetRow) dataIter.next();
	 FacLocAppPartCommentsViewBean facLocAppPartCommentsViewBean = new
		FacLocAppPartCommentsViewBean();
	 load(dataSetRow, facLocAppPartCommentsViewBean);
	 facLocAppPartCommentsViewBeanColl.add(facLocAppPartCommentsViewBean);
	}

	return facLocAppPartCommentsViewBeanColl;
 }
}