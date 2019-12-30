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
import com.tcmis.internal.hub.beans.InvGroupVesselCollObjBean;


import java.sql.SQLException;import com.tcmis.common.exceptions.DbSelectException;import java.sql.Statement;import java.sql.ResultSet;


/******************************************************************************
 * CLASSNAME: InvGroupVesselCollObjBeanFactory <br>
 * @version: 1.0, Sep 18, 2007 <br>
 *****************************************************************************/


public class InvGroupVesselCollObjBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_INVENTORY_GROUP_NAME = "INVENTORY_GROUP_NAME";
	public String ATTRIBUTE_COLLECTION = "COLLECTION";
	public String ATTRIBUTE_ISSUE_GENERATION = "ISSUE_GENERATION";
	public String ATTRIBUTE_SKIP_RECEIVING_QC = "SKIP_RECEIVING_QC";
	public String ATTRIBUTE_HAZARD_LABEL_GROUP = "HAZARD_LABEL_GROUP";
	public String ATTRIBUTE_ORDER_QTY_UPDATE_ON_RECEIPT = "ORDER_QTY_UPDATE_ON_RECEIPT";
	public String ATTRIBUTE_VESSELS = "VESSELS";

	//table name
	public String TABLE = "INV_GROUP_VESSEL_COLL_OBJ";


	//constructor
	public InvGroupVesselCollObjBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("inventoryGroupName")) {
			return ATTRIBUTE_INVENTORY_GROUP_NAME;
		}
		else if(attributeName.equals("collection")) {
			return ATTRIBUTE_COLLECTION;
		}
		else if(attributeName.equals("issueGeneration")) {
			return ATTRIBUTE_ISSUE_GENERATION;
		}
		else if(attributeName.equals("skipReceivingQc")) {
			return ATTRIBUTE_SKIP_RECEIVING_QC;
		}
		else if(attributeName.equals("hazardLabelGroup")) {
			return ATTRIBUTE_HAZARD_LABEL_GROUP;
		}
		else if(attributeName.equals("orderQtyUpdateOnReceipt")) {
			return ATTRIBUTE_ORDER_QTY_UPDATE_ON_RECEIPT;
		}
		else if(attributeName.equals("vessels")) {
			return ATTRIBUTE_VESSELS;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, InvGroupVesselCollObjBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(InvGroupVesselCollObjBean invGroupVesselCollObjBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("inventoryGroup", "SearchCriterion.EQUALS",
			"" + invGroupVesselCollObjBean.getInventoryGroup());

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


	public int delete(InvGroupVesselCollObjBean invGroupVesselCollObjBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("inventoryGroup", "SearchCriterion.EQUALS",
			"" + invGroupVesselCollObjBean.getInventoryGroup());

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
	public int insert(InvGroupVesselCollObjBean invGroupVesselCollObjBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(invGroupVesselCollObjBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(InvGroupVesselCollObjBean invGroupVesselCollObjBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  = 
			"insert into " + TABLE + " (" +
			ATTRIBUTE_INVENTORY_GROUP + "," +
			ATTRIBUTE_INVENTORY_GROUP_NAME + "," +
			ATTRIBUTE_COLLECTION + "," +
			ATTRIBUTE_ISSUE_GENERATION + "," +
			ATTRIBUTE_SKIP_RECEIVING_QC + "," +
			ATTRIBUTE_HAZARD_LABEL_GROUP + "," +
			ATTRIBUTE_ORDER_QTY_UPDATE_ON_RECEIPT + "," +
			ATTRIBUTE_VESSELS + ")" +
			" values (" +
			SqlHandler.delimitString(invGroupVesselCollObjBean.getInventoryGroup()) + "," +
			SqlHandler.delimitString(invGroupVesselCollObjBean.getInventoryGroupName()) + "," +
			SqlHandler.delimitString(invGroupVesselCollObjBean.getCollection()) + "," +
			SqlHandler.delimitString(invGroupVesselCollObjBean.getIssueGeneration()) + "," +
			SqlHandler.delimitString(invGroupVesselCollObjBean.getSkipReceivingQc()) + "," +
			SqlHandler.delimitString(invGroupVesselCollObjBean.getHazardLabelGroup()) + "," +
			SqlHandler.delimitString(invGroupVesselCollObjBean.getOrderQtyUpdateOnReceipt()) + "," +
			invGroupVesselCollObjBean.getVessels() + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(InvGroupVesselCollObjBean invGroupVesselCollObjBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(invGroupVesselCollObjBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(InvGroupVesselCollObjBean invGroupVesselCollObjBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_INVENTORY_GROUP + "=" + 
				SqlHandler.delimitString(invGroupVesselCollObjBean.getInventoryGroup()) + "," +
			ATTRIBUTE_INVENTORY_GROUP_NAME + "=" + 
				SqlHandler.delimitString(invGroupVesselCollObjBean.getInventoryGroupName()) + "," +
			ATTRIBUTE_COLLECTION + "=" + 
				SqlHandler.delimitString(invGroupVesselCollObjBean.getCollection()) + "," +
			ATTRIBUTE_ISSUE_GENERATION + "=" + 
				SqlHandler.delimitString(invGroupVesselCollObjBean.getIssueGeneration()) + "," +
			ATTRIBUTE_SKIP_RECEIVING_QC + "=" + 
				SqlHandler.delimitString(invGroupVesselCollObjBean.getSkipReceivingQc()) + "," +
			ATTRIBUTE_HAZARD_LABEL_GROUP + "=" + 
				SqlHandler.delimitString(invGroupVesselCollObjBean.getHazardLabelGroup()) + "," +
			ATTRIBUTE_ORDER_QTY_UPDATE_ON_RECEIPT + "=" + 
				SqlHandler.delimitString(invGroupVesselCollObjBean.getOrderQtyUpdateOnReceipt()) + "," +
			ATTRIBUTE_VESSELS + "=" + 
				StringHandler.nullIfZero(invGroupVesselCollObjBean.getVessels()) + " " +
			"where " + ATTRIBUTE_INVENTORY_GROUP + "=" +
				invGroupVesselCollObjBean.getInventoryGroup();

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

		Collection invGroupVesselCollObjBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			InvGroupVesselCollObjBean invGroupVesselCollObjBean = new InvGroupVesselCollObjBean();
			load(dataSetRow, invGroupVesselCollObjBean);
			invGroupVesselCollObjBeanColl.add(invGroupVesselCollObjBean);
		}

		return invGroupVesselCollObjBeanColl;
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
		Collection invGroupVesselCollObjBeanColl = new Vector();

//		String sortBy = " order by " + ATTRIBUTE_PRIORITY + "," + ATTRIBUTE_FACILITY_ID;
		String sortBy = " ";
		String query = "select value(p) from " + TABLE + " p " +
		 getWhereClause(criteria) + sortBy;
		//log.debug("QUERY:" + query);
			try {
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(query);
				while (rs.next()) {
					InvGroupVesselCollObjBean b = (InvGroupVesselCollObjBean) rs.getObject(1);
					invGroupVesselCollObjBeanColl.add(b);
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
			return invGroupVesselCollObjBeanColl;
		}

}