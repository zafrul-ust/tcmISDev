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
import com.tcmis.internal.supply.beans.PoAddChargeAllocationAllBean;


/******************************************************************************
 * CLASSNAME: PoAddChargeAllocationAllBeanFactory <br>
 * @version: 1.0, Aug 30, 2007 <br>
 *****************************************************************************/


public class PoAddChargeAllocationAllBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_RADIAN_PO = "RADIAN_PO";
	public String ATTRIBUTE_PO_LINE = "PO_LINE";
	public String ATTRIBUTE_PO_ADD_CHARGE_LINE = "PO_ADD_CHARGE_LINE";
	public String ATTRIBUTE_AMENDMENT = "AMENDMENT";

	//table name
	public String TABLE = "PO_ADD_CHARGE_ALLOCATION_ALL";


	//constructor
	public PoAddChargeAllocationAllBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("radianPo")) {
			return ATTRIBUTE_RADIAN_PO;
		}
		else if(attributeName.equals("poLine")) {
			return ATTRIBUTE_PO_LINE;
		}
		else if(attributeName.equals("poAddChargeLine")) {
			return ATTRIBUTE_PO_ADD_CHARGE_LINE;
		}
		else if(attributeName.equals("amendment")) {
			return ATTRIBUTE_AMENDMENT;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, PoAddChargeAllocationAllBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(PoAddChargeAllocationAllBean poAddChargeAllocationAllBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("radianPo", "SearchCriterion.EQUALS",
			"" + poAddChargeAllocationAllBean.getRadianPo());

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


	public int delete(PoAddChargeAllocationAllBean poAddChargeAllocationAllBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("radianPo", "SearchCriterion.EQUALS",
			"" + poAddChargeAllocationAllBean.getRadianPo());

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
	public int insert(PoAddChargeAllocationAllBean poAddChargeAllocationAllBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(poAddChargeAllocationAllBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(PoAddChargeAllocationAllBean poAddChargeAllocationAllBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_RADIAN_PO + "," +
			ATTRIBUTE_PO_LINE + "," +
			ATTRIBUTE_PO_ADD_CHARGE_LINE + "," +
			ATTRIBUTE_AMENDMENT + ")" +
			" values (" +
			poAddChargeAllocationAllBean.getRadianPo() + "," +
			poAddChargeAllocationAllBean.getPoLine() + "," +
			poAddChargeAllocationAllBean.getPoAddChargeLine() + "," +
			poAddChargeAllocationAllBean.getAmendment() + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(PoAddChargeAllocationAllBean poAddChargeAllocationAllBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(poAddChargeAllocationAllBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(PoAddChargeAllocationAllBean poAddChargeAllocationAllBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_RADIAN_PO + "=" + 
				StringHandler.nullIfZero(poAddChargeAllocationAllBean.getRadianPo()) + "," +
			ATTRIBUTE_PO_LINE + "=" + 
				StringHandler.nullIfZero(poAddChargeAllocationAllBean.getPoLine()) + "," +
			ATTRIBUTE_PO_ADD_CHARGE_LINE + "=" + 
				StringHandler.nullIfZero(poAddChargeAllocationAllBean.getPoAddChargeLine()) + "," +
			ATTRIBUTE_AMENDMENT + "=" + 
				StringHandler.nullIfZero(poAddChargeAllocationAllBean.getAmendment()) + " " +
			"where " + ATTRIBUTE_RADIAN_PO + "=" +
				poAddChargeAllocationAllBean.getRadianPo();

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

		Collection poAddChargeAllocationAllBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			PoAddChargeAllocationAllBean poAddChargeAllocationAllBean = new PoAddChargeAllocationAllBean();
			load(dataSetRow, poAddChargeAllocationAllBean);
			poAddChargeAllocationAllBeanColl.add(poAddChargeAllocationAllBean);
		}

		return poAddChargeAllocationAllBeanColl;
	}
}