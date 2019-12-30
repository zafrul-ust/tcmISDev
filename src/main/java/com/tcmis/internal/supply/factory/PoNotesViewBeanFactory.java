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
import com.tcmis.internal.supply.beans.PoNotesViewBean;


/******************************************************************************
 * CLASSNAME: PoNotesViewBeanFactory <br>
 * @version: 1.0, Nov 19, 2007 <br>
 *****************************************************************************/


public class PoNotesViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_RADIAN_PO = "RADIAN_PO";
	public String ATTRIBUTE_USER_ID = "USER_ID";
	public String ATTRIBUTE_NOTE_DATE = "NOTE_DATE";
	public String ATTRIBUTE_NOTE = "NOTE";
	public String ATTRIBUTE_NOTES_USER_NAME = "NOTES_USER_NAME";

	//table name
	public String TABLE = "PO_NOTES_VIEW";


	//constructor
	public PoNotesViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("radianPo")) {
			return ATTRIBUTE_RADIAN_PO;
		}
		else if(attributeName.equals("userId")) {
			return ATTRIBUTE_USER_ID;
		}
		else if(attributeName.equals("noteDate")) {
			return ATTRIBUTE_NOTE_DATE;
		}
		else if(attributeName.equals("note")) {
			return ATTRIBUTE_NOTE;
		}
		else if(attributeName.equals("notesUserName")) {
			return ATTRIBUTE_NOTES_USER_NAME;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, PoNotesViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(PoNotesViewBean poNotesViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("radianPo", "SearchCriterion.EQUALS",
			"" + poNotesViewBean.getRadianPo());

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


	public int delete(PoNotesViewBean poNotesViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("radianPo", "SearchCriterion.EQUALS",
			"" + poNotesViewBean.getRadianPo());

		return delete(criteria, conn);
	}
*/
/*
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


	public int insert(PoNotesViewBean poNotesViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(poNotesViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(PoNotesViewBean poNotesViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_RADIAN_PO + "," +
			ATTRIBUTE_USER_ID + "," +
			ATTRIBUTE_NOTE_DATE + "," +
			ATTRIBUTE_NOTE + "," +
			ATTRIBUTE_NOTES_USER_NAME + ")" +
			" values (" +
			poNotesViewBean.getRadianPo() + "," +
			poNotesViewBean.getUserId() + "," +
			DateHandler.getOracleToDateFunction(poNotesViewBean.getNoteDate()) + "," +
			SqlHandler.delimitString(poNotesViewBean.getNote()) + "," +
			SqlHandler.delimitString(poNotesViewBean.getNotesUserName()) + ")";

		return sqlManager.update(conn, query);
	}
*/
/*
	//update
	public int update(PoNotesViewBean poNotesViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(poNotesViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(PoNotesViewBean poNotesViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_RADIAN_PO + "=" + 
				StringHandler.nullIfZero(poNotesViewBean.getRadianPo()) + "," +
			ATTRIBUTE_USER_ID + "=" + 
				StringHandler.nullIfZero(poNotesViewBean.getUserId()) + "," +
			ATTRIBUTE_NOTE_DATE + "=" + 
				DateHandler.getOracleToDateFunction(poNotesViewBean.getNoteDate()) + "," +
			ATTRIBUTE_NOTE + "=" + 
				SqlHandler.delimitString(poNotesViewBean.getNote()) + "," +
			ATTRIBUTE_NOTES_USER_NAME + "=" + 
				SqlHandler.delimitString(poNotesViewBean.getNotesUserName()) + " " +
			"where " + ATTRIBUTE_RADIAN_PO + "=" +
				poNotesViewBean.getRadianPo();

		return new SqlManager().update(conn, query);
	}
*/
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

		Collection poNotesViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) 
		{
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			PoNotesViewBean poNotesViewBean = new PoNotesViewBean();
			load(dataSetRow, poNotesViewBean);
			poNotesViewBeanColl.add(poNotesViewBean);
		}

		return poNotesViewBeanColl;
	}
}