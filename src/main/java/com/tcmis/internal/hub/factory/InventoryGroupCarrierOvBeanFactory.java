package com.tcmis.internal.hub.factory;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DbSelectException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.internal.hub.beans.InventoryGroupCarrierOvBean;


/******************************************************************************
 * CLASSNAME: InventoryGroupCarrierOvBeanFactory <br>
 * @version: 1.0, Jan 25, 2008 <br>
 *****************************************************************************/


public class InventoryGroupCarrierOvBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_PERSONNEL_ID = "PERSONNEL_ID";
	public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
	public String ATTRIBUTE_MODE_CARRIER = "MODE_CARRIER";

	//table name
	public String TABLE = "INVENTORY_GROUP_CARRIER_OV";


	//constructor
	public InventoryGroupCarrierOvBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	@Override
	public String getColumnName(String attributeName) {
		if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("personnelId")) {
			return ATTRIBUTE_PERSONNEL_ID;
		}
		else if(attributeName.equals("inventoryGroup")) {
			return ATTRIBUTE_INVENTORY_GROUP;
		}
		else if(attributeName.equals("modeCarrier")) {
			return ATTRIBUTE_MODE_CARRIER;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, InventoryGroupCarrierOvBean.class);
	}


	//you need to verify the primary key(s) before uncommenting this
	/*
	//delete
	public int delete(InventoryGroupCarrierOvBean inventoryGroupCarrierOvBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
			"" + inventoryGroupCarrierOvBean.getCompanyId());

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


	public int delete(InventoryGroupCarrierOvBean inventoryGroupCarrierOvBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
			"" + inventoryGroupCarrierOvBean.getCompanyId());

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
	public int insert(InventoryGroupCarrierOvBean inventoryGroupCarrierOvBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(inventoryGroupCarrierOvBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(InventoryGroupCarrierOvBean inventoryGroupCarrierOvBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
			ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_PERSONNEL_ID + "," +
			ATTRIBUTE_INVENTORY_GROUP + "," +
			ATTRIBUTE_MODE_CARRIER + ")" +
			" values (" +
			SqlHandler.delimitString(inventoryGroupCarrierOvBean.getCompanyId()) + "," +
			inventoryGroupCarrierOvBean.getPersonnelId() + "," +
			SqlHandler.delimitString(inventoryGroupCarrierOvBean.getInventoryGroup()) + "," +
			inventoryGroupCarrierOvBean.getModeCarrier() + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(InventoryGroupCarrierOvBean inventoryGroupCarrierOvBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(inventoryGroupCarrierOvBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(InventoryGroupCarrierOvBean inventoryGroupCarrierOvBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_COMPANY_ID + "=" +
				SqlHandler.delimitString(inventoryGroupCarrierOvBean.getCompanyId()) + "," +
			ATTRIBUTE_PERSONNEL_ID + "=" +
				StringHandler.nullIfZero(inventoryGroupCarrierOvBean.getPersonnelId()) + "," +
			ATTRIBUTE_INVENTORY_GROUP + "=" +
				SqlHandler.delimitString(inventoryGroupCarrierOvBean.getInventoryGroup()) + "," +
			ATTRIBUTE_MODE_CARRIER + "=" +
				StringHandler.nullIfZero(inventoryGroupCarrierOvBean.getModeCarrier()) + " " +
			"where " + ATTRIBUTE_COMPANY_ID + "=" +
				inventoryGroupCarrierOvBean.getCompanyId();

		return new SqlManager().update(conn, query);
	}
	 */

	//select
	public Collection select(SearchCriteria criteria)
	throws BaseException {

		return select(criteria,null);

	}
	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria)
	throws BaseException {

		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = select(criteria,sortCriteria, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}
	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn)
	throws BaseException {

		Collection inventoryGroupCarrierOvBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
		getWhereClause(criteria)+ getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			InventoryGroupCarrierOvBean inventoryGroupCarrierOvBean = new InventoryGroupCarrierOvBean();
			load(dataSetRow, inventoryGroupCarrierOvBean);
			inventoryGroupCarrierOvBeanColl.add(inventoryGroupCarrierOvBean);
		}

		return inventoryGroupCarrierOvBeanColl;
	}

	public Collection selectObject(SearchCriteria criteria) throws BaseException {
		return selectObject(criteria,null);
	}

	public Collection selectObject(SearchCriteria criteria, SortCriteria sortCriteria) throws BaseException {
		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			java.util.Map map = connection.getTypeMap();
			map.put("TCM_OPS.CARRIER_OBJ",
					Class.forName(
					"com.tcmis.internal.hub.beans.CarrierObjBean"));
			map.put("TCM_OPS.MODE_CARRIER_OBJ",
					Class.forName(
					"com.tcmis.internal.hub.beans.ModeCarrierObjBean"));
			map.put("TCM_OPS.IG_MODE_CARRIER_OBJ",
					Class.forName(
					"com.tcmis.internal.hub.beans.InventoryGroupCarrierOvBean"));

			c = selectObject(criteria, sortCriteria,connection);
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

	public Collection selectObject(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn) throws
	BaseException {
		Collection inventoryGroupCarrierOvBeanColl = new Vector();

		String query = "select value(p) from " + TABLE + " p " +
		getWhereClause(criteria) + getOrderByClause(sortCriteria);
		if (log.isDebugEnabled()) {
			log.debug("QUERY:" + query);
		}
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				InventoryGroupCarrierOvBean b = (InventoryGroupCarrierOvBean) rs.getObject(1);
				inventoryGroupCarrierOvBeanColl.add(b);
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
		return inventoryGroupCarrierOvBeanColl;
	}

}