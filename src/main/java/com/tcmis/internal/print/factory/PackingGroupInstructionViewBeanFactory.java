package com.tcmis.internal.print.factory;


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
import com.tcmis.internal.print.beans.PackingGroupInstructionViewBean;


/******************************************************************************
 * CLASSNAME: PackingGroupInstructionViewBeanFactory <br>
 * @version: 1.0, Feb 20, 2008 <br>
 *****************************************************************************/


public class PackingGroupInstructionViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_PACKING_GROUP_ID = "PACKING_GROUP_ID";
	public String ATTRIBUTE_INSTRUCTIONS = "INSTRUCTIONS";

	//table name
	public String TABLE = "PACKING_GROUP_INSTRUCTION_VIEW";


	//constructor
	public PackingGroupInstructionViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("packingGroupId")) {
			return ATTRIBUTE_PACKING_GROUP_ID;
		}
		else if(attributeName.equals("instructions")) {
			return ATTRIBUTE_INSTRUCTIONS;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, PackingGroupInstructionViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(PackingGroupInstructionViewBean packingGroupInstructionViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("packingGroupId", "SearchCriterion.EQUALS",
			"" + packingGroupInstructionViewBean.getPackingGroupId());

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


	public int delete(PackingGroupInstructionViewBean packingGroupInstructionViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("packingGroupId", "SearchCriterion.EQUALS",
			"" + packingGroupInstructionViewBean.getPackingGroupId());

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
	public int insert(PackingGroupInstructionViewBean packingGroupInstructionViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(packingGroupInstructionViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(PackingGroupInstructionViewBean packingGroupInstructionViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_PACKING_GROUP_ID + "," +
			ATTRIBUTE_INSTRUCTIONS + ")" +
			" values (" +
			packingGroupInstructionViewBean.getPackingGroupId() + "," +
			SqlHandler.delimitString(packingGroupInstructionViewBean.getInstructions()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(PackingGroupInstructionViewBean packingGroupInstructionViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(packingGroupInstructionViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(PackingGroupInstructionViewBean packingGroupInstructionViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_PACKING_GROUP_ID + "=" + 
				StringHandler.nullIfZero(packingGroupInstructionViewBean.getPackingGroupId()) + "," +
			ATTRIBUTE_INSTRUCTIONS + "=" + 
				SqlHandler.delimitString(packingGroupInstructionViewBean.getInstructions()) + " " +
			"where " + ATTRIBUTE_PACKING_GROUP_ID + "=" +
				packingGroupInstructionViewBean.getPackingGroupId();

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

		Collection packingGroupInstructionViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			PackingGroupInstructionViewBean packingGroupInstructionViewBean = new PackingGroupInstructionViewBean();
			load(dataSetRow, packingGroupInstructionViewBean);
			packingGroupInstructionViewBeanColl.add(packingGroupInstructionViewBean);
		}

		return packingGroupInstructionViewBeanColl;
	}
}