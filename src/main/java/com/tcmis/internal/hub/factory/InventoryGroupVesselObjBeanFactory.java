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
import com.tcmis.internal.hub.beans.InventoryGroupVesselObjBean;


import java.sql.SQLException;import com.tcmis.common.exceptions.DbSelectException;import java.sql.Statement;import java.sql.ResultSet;


/******************************************************************************
 * CLASSNAME: InventoryGroupVesselObjBeanFactory <br>
 * @version: 1.0, Sep 18, 2007 <br>
 *****************************************************************************/


public class InventoryGroupVesselObjBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_VESSEL_ID = "VESSEL_ID";
	public String ATTRIBUTE_VESSEL_NAME = "VESSEL_NAME";

	//table name
	public String TABLE = "INVENTORY_GROUP_VESSEL_OBJ";


	//constructor
	public InventoryGroupVesselObjBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("vesselId")) {
			return ATTRIBUTE_VESSEL_ID;
		}
		else if(attributeName.equals("vesselName")) {
			return ATTRIBUTE_VESSEL_NAME;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, InventoryGroupVesselObjBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(InventoryGroupVesselObjBean inventoryGroupVesselObjBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("vesselId", "SearchCriterion.EQUALS",
			"" + inventoryGroupVesselObjBean.getVesselId());

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


	public int delete(InventoryGroupVesselObjBean inventoryGroupVesselObjBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("vesselId", "SearchCriterion.EQUALS",
			"" + inventoryGroupVesselObjBean.getVesselId());

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
	public int insert(InventoryGroupVesselObjBean inventoryGroupVesselObjBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(inventoryGroupVesselObjBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(InventoryGroupVesselObjBean inventoryGroupVesselObjBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_VESSEL_ID + "," +
			ATTRIBUTE_VESSEL_NAME + ")" +
			" values (" +
			SqlHandler.delimitString(inventoryGroupVesselObjBean.getVesselId()) + "," +
			SqlHandler.delimitString(inventoryGroupVesselObjBean.getVesselName()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(InventoryGroupVesselObjBean inventoryGroupVesselObjBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(inventoryGroupVesselObjBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(InventoryGroupVesselObjBean inventoryGroupVesselObjBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_VESSEL_ID + "=" + 
				SqlHandler.delimitString(inventoryGroupVesselObjBean.getVesselId()) + "," +
			ATTRIBUTE_VESSEL_NAME + "=" + 
				SqlHandler.delimitString(inventoryGroupVesselObjBean.getVesselName()) + " " +
			"where " + ATTRIBUTE_VESSEL_ID + "=" +
				inventoryGroupVesselObjBean.getVesselId();

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

		Collection inventoryGroupVesselObjBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			InventoryGroupVesselObjBean inventoryGroupVesselObjBean = new InventoryGroupVesselObjBean();
			load(dataSetRow, inventoryGroupVesselObjBean);
			inventoryGroupVesselObjBeanColl.add(inventoryGroupVesselObjBean);
		}

		return inventoryGroupVesselObjBeanColl;
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
		Collection inventoryGroupVesselObjBeanColl = new Vector();

//		String sortBy = " order by " + ATTRIBUTE_PRIORITY + "," + ATTRIBUTE_FACILITY_ID;
		String sortBy = " ";
		String query = "select value(p) from " + TABLE + " p " +
		 getWhereClause(criteria) + sortBy;
		//log.debug("QUERY:" + query);
			try {
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(query);
				while (rs.next()) {
					InventoryGroupVesselObjBean b = (InventoryGroupVesselObjBean) rs.getObject(1);
					inventoryGroupVesselObjBeanColl.add(b);
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
			return inventoryGroupVesselObjBeanColl;
		}

}