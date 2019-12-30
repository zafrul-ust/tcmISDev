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
import com.tcmis.internal.hub.beans.HubInventoryGroupVesselOvBean;


import java.sql.SQLException;import com.tcmis.common.exceptions.DbSelectException;import java.sql.Statement;import java.sql.ResultSet;


/******************************************************************************
 * CLASSNAME: HubInventoryGroupVesselOvBeanFactory <br>
 * @version: 1.0, Sep 18, 2007 <br>
 *****************************************************************************/


public class HubInventoryGroupVesselOvBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_PERSONNEL_ID = "PERSONNEL_ID";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_PRIORITY = "PRIORITY";
	public String ATTRIBUTE_HOME_COMPANY_ID = "HOME_COMPANY_ID";
	public String ATTRIBUTE_HOME_CURRENCY_ID = "HOME_CURRENCY_ID";
	public String ATTRIBUTE_BRANCH_PLANT = "BRANCH_PLANT";
	public String ATTRIBUTE_HUB_NAME = "HUB_NAME";
	public String ATTRIBUTE_INVENTORY_GROUP_COLLECTION = "INVENTORY_GROUP_COLLECTION";

	//table name
	public String TABLE = "HUB_INVENTORY_GROUP_VESSEL_OV";


	//constructor
	public HubInventoryGroupVesselOvBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("personnelId")) {
			return ATTRIBUTE_PERSONNEL_ID;
		}
		else if(attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if(attributeName.equals("priority")) {
			return ATTRIBUTE_PRIORITY;
		}
		else if(attributeName.equals("homeCompanyId")) {
			return ATTRIBUTE_HOME_COMPANY_ID;
		}
		else if(attributeName.equals("homeCurrencyId")) {
			return ATTRIBUTE_HOME_CURRENCY_ID;
		}
		else if(attributeName.equals("branchPlant")) {
			return ATTRIBUTE_BRANCH_PLANT;
		}
		else if(attributeName.equals("hubName")) {
			return ATTRIBUTE_HUB_NAME;
		}
		else if(attributeName.equals("inventoryGroupCollection")) {
			return ATTRIBUTE_INVENTORY_GROUP_COLLECTION;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, HubInventoryGroupVesselOvBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(HubInventoryGroupVesselOvBean hubInventoryGroupVesselOvBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
			"" + hubInventoryGroupVesselOvBean.getCompanyId());

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


	public int delete(HubInventoryGroupVesselOvBean hubInventoryGroupVesselOvBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
			"" + hubInventoryGroupVesselOvBean.getCompanyId());

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
	public int insert(HubInventoryGroupVesselOvBean hubInventoryGroupVesselOvBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(hubInventoryGroupVesselOvBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(HubInventoryGroupVesselOvBean hubInventoryGroupVesselOvBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_PERSONNEL_ID + "," +
			ATTRIBUTE_FACILITY_ID + "," +
			ATTRIBUTE_PRIORITY + "," +
			ATTRIBUTE_HOME_COMPANY_ID + "," +
			ATTRIBUTE_HOME_CURRENCY_ID + "," +
			ATTRIBUTE_BRANCH_PLANT + "," +
			ATTRIBUTE_HUB_NAME + "," +
			ATTRIBUTE_INVENTORY_GROUP_COLLECTION + ")" +
			" values (" +
			SqlHandler.delimitString(hubInventoryGroupVesselOvBean.getCompanyId()) + "," +
			hubInventoryGroupVesselOvBean.getPersonnelId() + "," +
			SqlHandler.delimitString(hubInventoryGroupVesselOvBean.getFacilityId()) + "," +
			hubInventoryGroupVesselOvBean.getPriority() + "," +
			SqlHandler.delimitString(hubInventoryGroupVesselOvBean.getHomeCompanyId()) + "," +
			SqlHandler.delimitString(hubInventoryGroupVesselOvBean.getHomeCurrencyId()) + "," +
			SqlHandler.delimitString(hubInventoryGroupVesselOvBean.getBranchPlant()) + "," +
			SqlHandler.delimitString(hubInventoryGroupVesselOvBean.getHubName()) + "," +
			hubInventoryGroupVesselOvBean.getInventoryGroupCollection() + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(HubInventoryGroupVesselOvBean hubInventoryGroupVesselOvBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(hubInventoryGroupVesselOvBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(HubInventoryGroupVesselOvBean hubInventoryGroupVesselOvBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_COMPANY_ID + "=" + 
				SqlHandler.delimitString(hubInventoryGroupVesselOvBean.getCompanyId()) + "," +
			ATTRIBUTE_PERSONNEL_ID + "=" + 
				StringHandler.nullIfZero(hubInventoryGroupVesselOvBean.getPersonnelId()) + "," +
			ATTRIBUTE_FACILITY_ID + "=" + 
				SqlHandler.delimitString(hubInventoryGroupVesselOvBean.getFacilityId()) + "," +
			ATTRIBUTE_PRIORITY + "=" + 
				StringHandler.nullIfZero(hubInventoryGroupVesselOvBean.getPriority()) + "," +
			ATTRIBUTE_HOME_COMPANY_ID + "=" + 
				SqlHandler.delimitString(hubInventoryGroupVesselOvBean.getHomeCompanyId()) + "," +
			ATTRIBUTE_HOME_CURRENCY_ID + "=" + 
				SqlHandler.delimitString(hubInventoryGroupVesselOvBean.getHomeCurrencyId()) + "," +
			ATTRIBUTE_BRANCH_PLANT + "=" + 
				SqlHandler.delimitString(hubInventoryGroupVesselOvBean.getBranchPlant()) + "," +
			ATTRIBUTE_HUB_NAME + "=" + 
				SqlHandler.delimitString(hubInventoryGroupVesselOvBean.getHubName()) + "," +
			ATTRIBUTE_INVENTORY_GROUP_COLLECTION + "=" + 
				StringHandler.nullIfZero(hubInventoryGroupVesselOvBean.getInventoryGroupCollection()) + " " +
			"where " + ATTRIBUTE_COMPANY_ID + "=" +
				hubInventoryGroupVesselOvBean.getCompanyId();

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

		Collection hubInventoryGroupVesselOvBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			HubInventoryGroupVesselOvBean hubInventoryGroupVesselOvBean = new HubInventoryGroupVesselOvBean();
			load(dataSetRow, hubInventoryGroupVesselOvBean);
			hubInventoryGroupVesselOvBeanColl.add(hubInventoryGroupVesselOvBean);
		}

		return hubInventoryGroupVesselOvBeanColl;
	}

	public Collection selectObject(SearchCriteria criteria) throws BaseException {
		Connection connection = null;
		Collection c = null;
		try {
		 connection = this.getDbManager().getConnection();
		 java.util.Map map = connection.getTypeMap();
		 map.put("INVENTORY_GROUP_VESSEL_OBJ",
			Class.forName(
			"com.tcmis.internal.hub.beans.InventoryGroupVesselObjBean"));
		 map.put("INV_GROUP_VESSEL_COLL_OBJ",
			Class.forName(
			"com.tcmis.internal.hub.beans.InvGroupVesselCollObjBean"));
		 map.put("HUB_INVENTORY_GROUP_VESSEL_OBJ",
			Class.forName(
			"com.tcmis.internal.hub.beans.HubInventoryGroupVesselOvBean"));

		 c = selectObject(criteria, connection);
		}
		catch (Exception e) {
		 log.error("selectObject error:" + e.getMessage());
		 DbSelectException ex = new DbSelectException("error.db.select");
		 ex.setRootCause(e);
		 throw ex;
		}
		finally {
		 this.getDbManager().returnConnection(connection);
		}
		return c;
	 }

	 public Collection selectObject(SearchCriteria criteria, Connection conn) throws
		BaseException {
		Collection hubInventoryGroupVesselOvBeanColl = new Vector();

//		String sortBy = " order by " + ATTRIBUTE_PRIORITY + "," + ATTRIBUTE_FACILITY_ID;
		String sortBy = " ";
		String query = "select value(p) from " + TABLE + " p " +
		 getWhereClause(criteria) + sortBy;
		//log.debug("QUERY:" + query);
			try {
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(query);
				while (rs.next()) {
					HubInventoryGroupVesselOvBean b = (HubInventoryGroupVesselOvBean) rs.getObject(1);
					hubInventoryGroupVesselOvBeanColl.add(b);
				}
				rs.close();
				st.close();
			}
			catch (SQLException e) {
				log.error("selectObject error:" + e.getMessage());
				DbSelectException ex = new DbSelectException("error.db.select");
				ex.setRootCause(e);
				throw ex;
			}
			return hubInventoryGroupVesselOvBeanColl;
		}

}