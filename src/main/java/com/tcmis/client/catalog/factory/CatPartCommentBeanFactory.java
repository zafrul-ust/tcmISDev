package com.tcmis.client.catalog.factory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.catalog.beans.CatPartCommentBean;
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
 * CLASSNAME: CatPartCommentBeanFactory <br>
 * @version: 1.0, Jul 11, 2005 <br>
 *****************************************************************************/

public class CatPartCommentBeanFactory
 extends BaseBeanFactory {

 Log log = LogFactory.getLog(this.getClass());

 //column names
 public String ATTRIBUTE_COMMENT_ID = "COMMENT_ID";
 public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
 public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
 public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
 public String ATTRIBUTE_DATE_ENTERED = "DATE_ENTERED";
 public String ATTRIBUTE_ENTERED_BY = "ENTERED_BY";
 public String ATTRIBUTE_COMMENTS = "COMMENTS";
 public String ATTRIBUTE_CATALOG_COMPANY_ID = "CATALOG_COMPANY_ID";

 //table name
 public String TABLE = "CAT_PART_COMMENT";

 //constructor
 public CatPartCommentBeanFactory(DbManager dbManager) {
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
	else if (attributeName.equals("catalogId")) {
	 return ATTRIBUTE_CATALOG_ID;
	}
	else if (attributeName.equals("catPartNo")) {
	 return ATTRIBUTE_CAT_PART_NO;
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
	return super.getType(attributeName, CatPartCommentBean.class);
 }

//you need to verify the primary key(s) before uncommenting this
 /*
//delete
	public int delete(CatPartCommentBean catPartCommentBean)
	 throws BaseException {
	 SearchCriteria criteria = new SearchCriteria("commentId", "SearchCriterion.EQUALS",
		"" + catPartCommentBean.getCommentId());
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
	public int delete(CatPartCommentBean catPartCommentBean, Connection conn)
	 throws BaseException {
	 SearchCriteria criteria = new SearchCriteria("commentId", "SearchCriterion.EQUALS",
		"" + catPartCommentBean.getCommentId());
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
 public int insert(CatPartCommentBean catPartCommentBean) throws BaseException {

	Connection connection = null;
	int result = 0;
	try {
	 connection = getDbManager().getConnection();
	 result = insert(catPartCommentBean, connection);
	}
	finally {
	 this.getDbManager().returnConnection(connection);
	}
	return result;
 }

 public int insert(CatPartCommentBean catPartCommentBean,
	Connection conn) throws BaseException {

	SqlManager sqlManager = new SqlManager();

	String query = "insert into " + TABLE + " (" +
	 //ATTRIBUTE_COMMENT_ID + "," +
   ATTRIBUTE_CATALOG_COMPANY_ID + "," +
   ATTRIBUTE_COMPANY_ID + "," +
	 ATTRIBUTE_CATALOG_ID + "," + ATTRIBUTE_CAT_PART_NO + "," +
	 //ATTRIBUTE_DATE_ENTERED + "," +
	 ATTRIBUTE_ENTERED_BY + "," + ATTRIBUTE_COMMENTS + ")" + " values (" +
	 //catPartCommentBean.getCommentId() + "," +
   SqlHandler.delimitString(catPartCommentBean.getCatalogCompanyId()) + "," +
   SqlHandler.delimitString(catPartCommentBean.getCompanyId()) + "," +
	 SqlHandler.delimitString(catPartCommentBean.getCatalogId()) + "," +
	 SqlHandler.delimitString(catPartCommentBean.getCatPartNo()) + "," +
	 //DateHandler.getOracleToDateFunction(catPartCommentBean.getDateEntered()) + "," +
	 catPartCommentBean.getEnteredBy() + "," +
	 SqlHandler.delimitString(catPartCommentBean.getComments()) + ")";

	return sqlManager.update(conn, query);
 }

 //update
 public int update(CatPartCommentBean catPartCommentBean) throws BaseException {

	Connection connection = null;
	int result = 0;
	try {
	 connection = getDbManager().getConnection();
	 result = update(catPartCommentBean, connection);
	}
	finally {
	 this.getDbManager().returnConnection(connection);
	}
	return result;
 }

 public int update(CatPartCommentBean catPartCommentBean,
	Connection conn) throws BaseException {

	String query = "update " + TABLE + " set " +
	 //ATTRIBUTE_COMMENT_ID + "=" +
	 //	StringHandler.nullIfZero(catPartCommentBean.getCommentId()) + "," +
	 //ATTRIBUTE_COMPANY_ID + "=" +
	 //	SqlHandler.delimitString(catPartCommentBean.getCompanyId()) + "," +
	 ATTRIBUTE_CATALOG_ID + "=" +
	 SqlHandler.delimitString(catPartCommentBean.getCatalogId()) + "," +
	 ATTRIBUTE_CAT_PART_NO + "=" +
	 SqlHandler.delimitString(catPartCommentBean.getCatPartNo()) + "," +
	 ATTRIBUTE_DATE_ENTERED + "=sysdate" + "," +
	 //	DateHandler.getOracleToDateFunction(catPartCommentBean.getDateEntered()) + "," +
	 ATTRIBUTE_ENTERED_BY + "=" +
	 StringHandler.nullIfZero(catPartCommentBean.getEnteredBy()) + "," +
	 ATTRIBUTE_COMMENTS + "=" +
	 SqlHandler.delimitString(catPartCommentBean.getComments()) + " " + "where " +
	 ATTRIBUTE_COMMENT_ID + "=" + catPartCommentBean.getCommentId();

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

	Collection catPartCommentBeanColl = new Vector();

	String query = "select * from " + TABLE + " " + getWhereClause(criteria);

	DataSet dataSet = new SqlManager().select(conn, query);

	Iterator dataIter = dataSet.iterator();

	while (dataIter.hasNext()) {
	 DataSetRow dataSetRow = (DataSetRow) dataIter.next();
	 CatPartCommentBean catPartCommentBean = new CatPartCommentBean();
	 load(dataSetRow, catPartCommentBean);
	 catPartCommentBeanColl.add(catPartCommentBean);
	}

	return catPartCommentBeanColl;
 }
}