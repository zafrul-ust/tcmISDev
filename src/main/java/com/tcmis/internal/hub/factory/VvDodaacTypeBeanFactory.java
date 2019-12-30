package com.tcmis.internal.hub.factory;


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
import com.tcmis.internal.hub.beans.VvDodaacTypeBean;


/******************************************************************************
 * CLASSNAME: VvDodaacTypeBeanFactory <br>
 * @version: 1.0, Nov 24, 2008 <br>
 *****************************************************************************/


public class VvDodaacTypeBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_DODAAC_TYPE = "DODAAC_TYPE";
	public String ATTRIBUTE_DODAAC_TYPE_DESC = "DODAAC_TYPE_DESC";

	//table name
	public String TABLE = "VV_DODAAC_TYPE";


	//constructor
	public VvDodaacTypeBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("dodaacType")) {
			return ATTRIBUTE_DODAAC_TYPE;
		}
		else if(attributeName.equals("dodaacTypeDesc")) {
			return ATTRIBUTE_DODAAC_TYPE_DESC;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, VvDodaacTypeBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(VvDodaacTypeBean vvDodaacTypeBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("dodaacType", "SearchCriterion.EQUALS",
			"" + vvDodaacTypeBean.getDodaacType());

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


	public int delete(VvDodaacTypeBean vvDodaacTypeBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("dodaacType", "SearchCriterion.EQUALS",
			"" + vvDodaacTypeBean.getDodaacType());

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
	public int insert(VvDodaacTypeBean vvDodaacTypeBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(vvDodaacTypeBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(VvDodaacTypeBean vvDodaacTypeBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_DODAAC_TYPE + "," +
			ATTRIBUTE_DODAAC_TYPE_DESC + ")" +
			" values (" +
			SqlHandler.delimitString(vvDodaacTypeBean.getDodaacType()) + "," +
			SqlHandler.delimitString(vvDodaacTypeBean.getDodaacTypeDesc()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(VvDodaacTypeBean vvDodaacTypeBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(vvDodaacTypeBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(VvDodaacTypeBean vvDodaacTypeBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_DODAAC_TYPE + "=" + 
				SqlHandler.delimitString(vvDodaacTypeBean.getDodaacType()) + "," +
			ATTRIBUTE_DODAAC_TYPE_DESC + "=" + 
				SqlHandler.delimitString(vvDodaacTypeBean.getDodaacTypeDesc()) + " " +
			"where " + ATTRIBUTE_DODAAC_TYPE + "=" +
				vvDodaacTypeBean.getDodaacType();

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

		Collection vvDodaacTypeBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			VvDodaacTypeBean vvDodaacTypeBean = new VvDodaacTypeBean();
			load(dataSetRow, vvDodaacTypeBean);
			vvDodaacTypeBeanColl.add(vvDodaacTypeBean);
		}

		return vvDodaacTypeBeanColl;
	}
}