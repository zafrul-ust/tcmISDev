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
import com.tcmis.internal.hub.beans.DodaacLocationBean;


/******************************************************************************
 * CLASSNAME: DodaacLocationBeanFactory <br>
 * @version: 1.0, Nov 21, 2008 <br>
 *****************************************************************************/


public class DodaacLocationBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_DODAAC = "DODAAC";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_LOCATION_ID = "LOCATION_ID";
	public String ATTRIBUTE_HAZMAT_CAPABLE = "HAZMAT_CAPABLE";
	public String ATTRIBUTE_DODAAC_TYPE = "DODAAC_TYPE";

	//table name
	public String TABLE = "DODAAC_LOCATION";


	//constructor
	public DodaacLocationBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("dodaac")) {
			return ATTRIBUTE_DODAAC;
		}
		else if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("locationId")) {
			return ATTRIBUTE_LOCATION_ID;
		}
		else if(attributeName.equals("hazmatCapable")) {
			return ATTRIBUTE_HAZMAT_CAPABLE;
		}
		else if(attributeName.equals("dodaacType")) {
			return ATTRIBUTE_DODAAC_TYPE;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, DodaacLocationBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(DodaacLocationBean dodaacLocationBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("dodaac", "SearchCriterion.EQUALS",
			"" + dodaacLocationBean.getDodaac());

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


	public int delete(DodaacLocationBean dodaacLocationBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("dodaac", "SearchCriterion.EQUALS",
			"" + dodaacLocationBean.getDodaac());

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
	public int insert(DodaacLocationBean dodaacLocationBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(dodaacLocationBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(DodaacLocationBean dodaacLocationBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
			ATTRIBUTE_DODAAC + "," +
			ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_LOCATION_ID + "," +
			ATTRIBUTE_HAZMAT_CAPABLE + "," +
			ATTRIBUTE_DODAAC_TYPE + ")" +
			" values (" +
			SqlHandler.delimitString(dodaacLocationBean.getDodaac()) + "," +
			SqlHandler.delimitString(dodaacLocationBean.getCompanyId()) + "," +
			SqlHandler.delimitString(dodaacLocationBean.getLocationId()) + "," +
			SqlHandler.delimitString(dodaacLocationBean.getHazmatCapable()) + "," +
			SqlHandler.delimitString(dodaacLocationBean.getDodaacType()) + ")";

		return sqlManager.update(conn, query);
	}
*/

	//update
	public int update(DodaacLocationBean dodaacLocationBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(dodaacLocationBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}

	//update
	public int updateLocation(String locationId, String country, String state ) 
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = updateLocation(locationId,country,state, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}

	public int updateLocation(String locationId, String country, String state, Connection conn)
	throws BaseException {

	String query  = "update location set " +
	"STATE_ABBREV = " + SqlHandler.delimitString(state) + "," +
	"COUNTRY_ABBREV = " + SqlHandler.delimitString(country) +
	" where location_id = " + SqlHandler.delimitString(locationId);

	return new SqlManager().update(conn, query);
	}

	
	public int update(DodaacLocationBean dodaacLocationBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			/*ATTRIBUTE_DODAAC + "=" +
				SqlHandler.delimitString(dodaacLocationBean.getDodaac()) + "," +
			ATTRIBUTE_COMPANY_ID + "=" +
				SqlHandler.delimitString(dodaacLocationBean.getCompanyId()) + "," +
			ATTRIBUTE_LOCATION_ID + "=" +
				SqlHandler.delimitString(dodaacLocationBean.getLocationId()) + "," +
			ATTRIBUTE_HAZMAT_CAPABLE + "=" +
				SqlHandler.delimitString(dodaacLocationBean.getHazmatCapable()) + "," +*/
			ATTRIBUTE_DODAAC_TYPE + "=" + 
				SqlHandler.delimitString(dodaacLocationBean.getDodaacType()) + " " +
			"where " + ATTRIBUTE_LOCATION_ID + "=" +
				SqlHandler.delimitString(dodaacLocationBean.getLocationId());

		return new SqlManager().update(conn, query);
	}


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

		Collection dodaacLocationBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			DodaacLocationBean dodaacLocationBean = new DodaacLocationBean();
			load(dataSetRow, dodaacLocationBean);
			dodaacLocationBeanColl.add(dodaacLocationBean);
		}

		return dodaacLocationBeanColl;
	}
}