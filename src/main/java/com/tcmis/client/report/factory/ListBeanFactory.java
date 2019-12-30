package com.tcmis.client.report.factory;


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
import com.tcmis.client.report.beans.ListBean;


/******************************************************************************
 * CLASSNAME: ListBeanFactory <br>
 * @version: 1.0, Oct 6, 2005 <br>
 *****************************************************************************/


public class ListBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_LIST_ID = "LIST_ID";
	public String ATTRIBUTE_LIST_NAME = "LIST_NAME";

	//table name
	public String TABLE = "LIST";


	//constructor
	public ListBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("listId")) {
			return ATTRIBUTE_LIST_ID;
		}
		else if(attributeName.equals("listName")) {
			return ATTRIBUTE_LIST_NAME;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, ListBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(ListBean listBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("listId", "SearchCriterion.EQUALS",
			"" + listBean.getListId());

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


	public int delete(ListBean listBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("listId", "SearchCriterion.EQUALS",
			"" + listBean.getListId());

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
	public int insert(ListBean listBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(listBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(ListBean listBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
			ATTRIBUTE_LIST_ID + "," +
			ATTRIBUTE_LIST_NAME + ")" +
			" values (" +
			SqlHandler.delimitString(listBean.getListId()) + "," +
			SqlHandler.delimitString(listBean.getListName()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(ListBean listBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(listBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(ListBean listBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_LIST_ID + "=" +
				SqlHandler.delimitString(listBean.getListId()) + "," +
			ATTRIBUTE_LIST_NAME + "=" +
				SqlHandler.delimitString(listBean.getListName()) + " " +
			"where " + ATTRIBUTE_LIST_ID + "=" +
				listBean.getListId();

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

		Collection listBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			ListBean listBean = new ListBean();
			load(dataSetRow, listBean);
			listBeanColl.add(listBean);
		}

		return listBeanColl;
	}

        //select
        public Collection selectSortByName(SearchCriteria criteria)
                throws BaseException {

                Connection connection = null;
                Collection c = null;
                try {
                        connection = this.getDbManager().getConnection();
                        c = selectSortByName(criteria, connection);
                }
                finally {
                        this.getDbManager().returnConnection(connection);
                }
                return c;
        }
        public Collection selectSortByName(SearchCriteria criteria, Connection conn)
                throws BaseException {

                Collection listBeanColl = new Vector();

                String query = "select * from " + TABLE + " " +
                        getWhereClause(criteria) +" order by list_name";


                DataSet dataSet = new SqlManager().select(conn, query);

                Iterator dataIter = dataSet.iterator();

                while (dataIter.hasNext()) {
                        DataSetRow dataSetRow = (DataSetRow)dataIter.next();
                        ListBean listBean = new ListBean();
                        load(dataSetRow, listBean);
                        listBeanColl.add(listBean);
                }

                return listBeanColl;
        }
}