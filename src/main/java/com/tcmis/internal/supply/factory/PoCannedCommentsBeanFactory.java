
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
import com.tcmis.internal.supply.beans.PoCannedCommentsBean;


/******************************************************************************
 * CLASSNAME: PoCannedCommentsBeanFactory <br>
 * @version: 1.0, Dec 13, 2007 <br>
 *****************************************************************************/


public class PoCannedCommentsBeanFactory extends BaseBeanFactory 
{

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_COMMENT_ID = "COMMENT_ID";
	public String ATTRIBUTE_COMMENTS = "COMMENTS";
	public String ATTRIBUTE_COMMENT_NAME = "COMMENT_NAME";

	//table name
	public String TABLE = "PO_CANNED_COMMENTS";


	//constructor
	public PoCannedCommentsBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("commentId")) {
			return ATTRIBUTE_COMMENT_ID;
		}
		else if(attributeName.equals("comments")) {
			return ATTRIBUTE_COMMENTS;
		}
		else if(attributeName.equals("commentName")) {
			return ATTRIBUTE_COMMENT_NAME;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, PoCannedCommentsBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(PoCannedCommentsBean poCannedCommentsBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("commentId", "SearchCriterion.EQUALS",
			"" + poCannedCommentsBean.getCommentId());

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


	public int delete(PoCannedCommentsBean poCannedCommentsBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("commentId", "SearchCriterion.EQUALS",
			"" + poCannedCommentsBean.getCommentId());

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
	public int insert(PoCannedCommentsBean poCannedCommentsBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(poCannedCommentsBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(PoCannedCommentsBean poCannedCommentsBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_COMMENT_ID + "," +
			ATTRIBUTE_COMMENTS + "," +
			ATTRIBUTE_COMMENT_NAME + ")" +
			" values (" +
			poCannedCommentsBean.getCommentId() + "," +
			SqlHandler.delimitString(poCannedCommentsBean.getComments()) + "," +
			SqlHandler.delimitString(poCannedCommentsBean.getCommentName()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(PoCannedCommentsBean poCannedCommentsBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(poCannedCommentsBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(PoCannedCommentsBean poCannedCommentsBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_COMMENT_ID + "=" + 
				StringHandler.nullIfZero(poCannedCommentsBean.getCommentId()) + "," +
			ATTRIBUTE_COMMENTS + "=" + 
				SqlHandler.delimitString(poCannedCommentsBean.getComments()) + "," +
			ATTRIBUTE_COMMENT_NAME + "=" + 
				SqlHandler.delimitString(poCannedCommentsBean.getCommentName()) + " " +
			"where " + ATTRIBUTE_COMMENT_ID + "=" +
				poCannedCommentsBean.getCommentId();

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

		Collection poCannedCommentsBeanColl = new Vector();

		String query = "select * from " + TABLE + " " 
		//	+ getWhereClause(criteria) 
			+ getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) 
		{
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			PoCannedCommentsBean poCannedCommentsBean = new PoCannedCommentsBean();
			load(dataSetRow, poCannedCommentsBean);
			poCannedCommentsBeanColl.add(poCannedCommentsBean);
		}

		return poCannedCommentsBeanColl;
	}
}
