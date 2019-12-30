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
 * CLASSNAME: PoNotesBeanFactory <br>
 * @version: 1.0, Nov 19, 2007 <br>
 *****************************************************************************/


public class PoNotesBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_RADIAN_PO = "RADIAN_PO";
	public String ATTRIBUTE_USER_ID = "USER_ID";
	public String ATTRIBUTE_NOTE_DATE = "NOTE_DATE";
	public String ATTRIBUTE_NOTE = "NOTE";

	//table name
	public String TABLE = "PO_NOTES";


	//constructor
	public PoNotesBeanFactory(DbManager dbManager) {
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
	public int delete(PoNotesBean poNotesBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("radianPo", "SearchCriterion.EQUALS",
			"" + poNotesBean.getRadianPo());

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


	public int delete(PoNotesBean poNotesBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("radianPo", "SearchCriterion.EQUALS",
			"" + poNotesBean.getRadianPo());

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

*/
	//insert
	public int insert(PoNotesViewBean poNotesBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(poNotesBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(PoNotesViewBean poNotesBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_RADIAN_PO + "," +
			ATTRIBUTE_USER_ID + "," +
			ATTRIBUTE_NOTE_DATE + "," +
			ATTRIBUTE_NOTE + ")" +
			" values (" +
			poNotesBean.getRadianPo() + "," +
			poNotesBean.getUserId() + "," +
			"sysdate, " +
			// DateHandler.getOracleToDateFunction(poNotesBean.getNoteDate()) + "," +
			SqlHandler.delimitString(poNotesBean.getNote()) + ")";

		return sqlManager.update(conn, query);
	}

/*
	//update
	public int update(PoNotesBean poNotesBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(poNotesBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(PoNotesBean poNotesBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_RADIAN_PO + "=" + 
				StringHandler.nullIfZero(poNotesBean.getRadianPo()) + "," +
			ATTRIBUTE_USER_ID + "=" + 
				StringHandler.nullIfZero(poNotesBean.getUserId()) + "," +
			ATTRIBUTE_NOTE_DATE + "=" + 
				DateHandler.getOracleToDateFunction(poNotesBean.getNoteDate()) + "," +
			ATTRIBUTE_NOTE + "=" + 
				SqlHandler.delimitString(poNotesBean.getNote()) + " " +
			"where " + ATTRIBUTE_RADIAN_PO + "=" +
				poNotesBean.getRadianPo();

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

		Collection poNotesBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			PoNotesViewBean poNotesBean = new PoNotesViewBean();
			load(dataSetRow, poNotesBean);
			poNotesBeanColl.add(poNotesBean);
		}

		return poNotesBeanColl;
	}
}