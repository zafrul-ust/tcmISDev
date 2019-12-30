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
import com.tcmis.internal.supply.beans.BpoNotesViewBean;


/******************************************************************************
 * CLASSNAME: BpoNotesBeanFactory <br>
 * @version: 1.0, Nov 19, 2007 <br>
 *****************************************************************************/


public class BpoNotesBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_BPO = "BPO";
	public String ATTRIBUTE_USER_ID = "USER_ID";
	public String ATTRIBUTE_NOTE_DATE = "NOTE_DATE";
	public String ATTRIBUTE_NOTE = "NOTE";

	//table name
	public String TABLE = "BPO_NOTES";


	//constructor
	public BpoNotesBeanFactory(DbManager dbManager) {
		super(dbManager);
	}

	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("bpo")) {
			return ATTRIBUTE_BPO;
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
		return super.getType(attributeName, BpoNotesViewBean.class);
	}

//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(BpoNotesBean bpoNotesBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("bpo", "SearchCriterion.EQUALS",
			"" + bpoNotesBean.getBpo());

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


	public int delete(BpoNotesBean bpoNotesBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("bpo", "SearchCriterion.EQUALS",
			"" + bpoNotesBean.getBpo());

		return delete(criteria, conn);
	}


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
	public int insert(BpoNotesViewBean bpoNotesBean)
		throws BaseException 
	{
		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(bpoNotesBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}

	public int insert(BpoNotesViewBean bpoNotesBean, Connection conn)
		throws BaseException 
	{
		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_BPO + "," +
			ATTRIBUTE_USER_ID + "," +
			ATTRIBUTE_NOTE_DATE + "," +
			ATTRIBUTE_NOTE + ")" +
			" values (" +
			bpoNotesBean.getBpo() + "," +
			bpoNotesBean.getUserId() + "," +
			"sysdate, " +
			// DateHandler.getOracleToDateFunction(bpoNotesBean.getNoteDate()) + "," +
			SqlHandler.delimitString(bpoNotesBean.getNote()) + ")";

		return sqlManager.update(conn, query);
	}

/*
	//update
	public int update(BpoNotesBean bpoNotesBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(bpoNotesBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(BpoNotesBean bpoNotesBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_BPO + "=" + 
				StringHandler.nullIfZero(bpoNotesBean.getBpo()) + "," +
			ATTRIBUTE_USER_ID + "=" + 
				StringHandler.nullIfZero(bpoNotesBean.getUserId()) + "," +
			ATTRIBUTE_NOTE_DATE + "=" + 
				DateHandler.getOracleToDateFunction(bpoNotesBean.getNoteDate()) + "," +
			ATTRIBUTE_NOTE + "=" + 
				SqlHandler.delimitString(bpoNotesBean.getNote()) + " " +
			"where " + ATTRIBUTE_BPO + "=" +
				bpoNotesBean.getBpo();

		return new SqlManager().update(conn, query);
	}
*/
/*
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

		Collection bpoNotesBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			BpoNotesBean bpoNotesBean = new BpoNotesBean();
			load(dataSetRow, bpoNotesBean);
			bpoNotesBeanColl.add(bpoNotesBean);
		}

		return bpoNotesBeanColl;
	}
*/
}