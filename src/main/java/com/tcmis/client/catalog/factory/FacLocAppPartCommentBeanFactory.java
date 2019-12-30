package com.tcmis.client.catalog.factory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.catalog.beans.FacLocAppPartCommentBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: FacLocAppPartCommentBeanFactory <br>
 * @version: 1.0, Jul 11, 2005 <br>
 *****************************************************************************/

public class FacLocAppPartCommentBeanFactory
 extends BaseBeanFactory {

 Log log = LogFactory.getLog(this.getClass());

 //column names
 public String ATTRIBUTE_COMMENT_ID = "COMMENT_ID";
 public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
 public String ATTRIBUTE_APPLICATION = "APPLICATION";
 public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
 public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
 public String ATTRIBUTE_PART_GROUP_NO = "PART_GROUP_NO";
 public String ATTRIBUTE_DATE_ENTERED = "DATE_ENTERED";
 public String ATTRIBUTE_ENTERED_BY = "ENTERED_BY";
 public String ATTRIBUTE_COMMENTS = "COMMENTS";
 public String ATTRIBUTE_CATALOG_COMPANY_ID = "CATALOG_COMPANY_ID";

 //table name
 public String TABLE = "FAC_LOC_APP_PART_COMMENT";

 //constructor
 public FacLocAppPartCommentBeanFactory(DbManager dbManager) {
	super(dbManager);
 }

 //get column names
 public String getColumnName(String attributeName) {
	if (attributeName.equals("commentId")) {
	 return ATTRIBUTE_COMMENT_ID;
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
	else if (attributeName.equals("catalogCompanyId")) {
		 return ATTRIBUTE_CATALOG_COMPANY_ID;
    }
	else {
	 return super.getColumnName(attributeName);
	}
 }

 //get column types
 public int getType(String attributeName) {
	return super.getType(attributeName, FacLocAppPartCommentBean.class);
 }

//you need to verify the primary key(s) before uncommenting this
 /*
//delete
	public int delete(FacLocAppPartCommentBean facLocAppPartCommentBean)
	 throws BaseException {
	 SearchCriteria criteria = new SearchCriteria("commentId", "SearchCriterion.EQUALS",
		"" + facLocAppPartCommentBean.getCommentId());
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
	public int delete(FacLocAppPartCommentBean facLocAppPartCommentBean, Connection conn)
	 throws BaseException {
	 SearchCriteria criteria = new SearchCriteria("commentId", "SearchCriterion.EQUALS",
		"" + facLocAppPartCommentBean.getCommentId());
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
 //insert
 public int insert(FacLocAppPartCommentBean facLocAppPartCommentBean) throws
	BaseException {

	Connection connection = null;
	int result = 0;
	try {
	 connection = getDbManager().getConnection();
	 result = insert(facLocAppPartCommentBean, connection);
	}
	finally {
	 this.getDbManager().returnConnection(connection);
	}
	return result;
 }

 public int insert(FacLocAppPartCommentBean facLocAppPartCommentBean,
	Connection conn) throws BaseException {

	SqlManager sqlManager = new SqlManager();
	if ("My Work Areas".equalsIgnoreCase(facLocAppPartCommentBean.getApplication())) {
		facLocAppPartCommentBean.setApplicationId("All");
	}else {
		facLocAppPartCommentBean.setApplicationId(facLocAppPartCommentBean.getApplication());
	}

	String query = "insert into " + TABLE + " (" +
	 //ATTRIBUTE_COMMENT_ID + "," +
	 ATTRIBUTE_FACILITY_ID + "," + ATTRIBUTE_APPLICATION + "," +
	 ATTRIBUTE_CATALOG_ID + "," + ATTRIBUTE_CAT_PART_NO + "," +
	 ATTRIBUTE_PART_GROUP_NO + "," +
	 //ATTRIBUTE_DATE_ENTERED + "," +
	 ATTRIBUTE_ENTERED_BY + "," + ATTRIBUTE_COMMENTS + ")" + " values (" +
	 //facLocAppPartCommentBean.getCommentId() + "," +
	 SqlHandler.delimitString(facLocAppPartCommentBean.getFacilityId()) + "," +
	 SqlHandler.delimitString(facLocAppPartCommentBean.getApplicationId()) + "," +
	 SqlHandler.delimitString(facLocAppPartCommentBean.getCatalogId()) + "," +
	 SqlHandler.delimitString(facLocAppPartCommentBean.getCatPartNo()) + "," +
	 facLocAppPartCommentBean.getPartGroupNo() + "," +
	 //DateHandler.getOracleToDateFunction(facLocAppPartCommentBean.getDateEntered()) + "," +
	 facLocAppPartCommentBean.getEnteredBy() + "," +
	 SqlHandler.delimitString(facLocAppPartCommentBean.getComments()) + ")";

	return sqlManager.update(conn, query);
 }

 //update
 public int update(FacLocAppPartCommentBean facLocAppPartCommentBean) throws
	BaseException {

	Connection connection = null;
	int result = 0;
	try {
	 connection = getDbManager().getConnection();
	 result = update(facLocAppPartCommentBean, connection);
	}
	finally {
	 this.getDbManager().returnConnection(connection);
	}
	return result;
 }

 public int update(FacLocAppPartCommentBean facLocAppPartCommentBean,
	Connection conn) throws BaseException {

	String query = "update " + TABLE + " set " +
	 //ATTRIBUTE_COMMENT_ID + "=" +
	 //	StringHandler.nullIfZero(facLocAppPartCommentBean.getCommentId()) + "," +
	 //ATTRIBUTE_FACILITY_ID + "=" +
	 //	SqlHandler.delimitString(facLocAppPartCommentBean.getFacilityId()) + "," +
	 //ATTRIBUTE_APPLICATION + "=" +
	 //	SqlHandler.delimitString(facLocAppPartCommentBean.getApplication()) + "," +
	 //ATTRIBUTE_CATALOG_ID + "=" +
	 //	SqlHandler.delimitString(facLocAppPartCommentBean.getCatalogId()) + "," +
	 //ATTRIBUTE_CAT_PART_NO + "=" +
	 //	SqlHandler.delimitString(facLocAppPartCommentBean.getCatPartNo()) + "," +
	 //ATTRIBUTE_PART_GROUP_NO + "=" +
	 //	StringHandler.nullIfZero(facLocAppPartCommentBean.getPartGroupNo()) + "," +
	 ATTRIBUTE_DATE_ENTERED + "=sysdate" + "," +
	 //	DateHandler.getOracleToDateFunction(facLocAppPartCommentBean.getDateEntered()) + "," +
	 ATTRIBUTE_ENTERED_BY + "=" +
	 StringHandler.nullIfZero(facLocAppPartCommentBean.getEnteredBy()) + "," +
	 ATTRIBUTE_COMMENTS + "=" +
	 SqlHandler.delimitString(facLocAppPartCommentBean.getComments()) + " " +
	 "where " + ATTRIBUTE_COMMENT_ID + "=" +
	 facLocAppPartCommentBean.getCommentId();

	return new SqlManager().update(conn, query);
 }

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

	Collection facLocAppPartCommentBeanColl = new Vector();

	String sortBy = " order by " + ATTRIBUTE_DATE_ENTERED;
	String query = "select * from " + TABLE + " " + getWhereClause(criteria) +
	 sortBy;

	DataSet dataSet = new SqlManager().select(conn, query);

	Iterator dataIter = dataSet.iterator();

	while (dataIter.hasNext()) {
	 DataSetRow dataSetRow = (DataSetRow) dataIter.next();
	 FacLocAppPartCommentBean facLocAppPartCommentBean = new
		FacLocAppPartCommentBean();
	 load(dataSetRow, facLocAppPartCommentBean);
	 facLocAppPartCommentBeanColl.add(facLocAppPartCommentBean);
	}

	return facLocAppPartCommentBeanColl;
 }
}