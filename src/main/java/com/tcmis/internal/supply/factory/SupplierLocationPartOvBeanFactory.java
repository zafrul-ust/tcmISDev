package com.tcmis.internal.supply.factory;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DbSelectException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.internal.supply.beans.SupplierLocationPartOvBean;


/******************************************************************************
 * CLASSNAME: SupplierLocationPartOvBeanFactory <br>
 * @version: 1.0, Oct 7, 2008 <br>
 *****************************************************************************/


public class SupplierLocationPartOvBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_SUPPLIER = "SUPPLIER";
	public String ATTRIBUTE_SHIP_FROM_LOCATION_ID = "SHIP_FROM_LOCATION_ID";
	public String ATTRIBUTE_SUPPLIER_REGION_CODE = "SUPPLIER_REGION_CODE";
	public String ATTRIBUTE_SUPPLIER_LOCATION_TYPE = "SUPPLIER_LOCATION_TYPE";
	public String ATTRIBUTE_SUPPLIER_LOCATION_CODE = "SUPPLIER_LOCATION_CODE";
	public String ATTRIBUTE_SUPPLIER_PART_LIST = "SUPPLIER_PART_LIST";

	//table name
	public String TABLE = "SUPPLIER_LOCATION_PART_OV";


	//constructor
	public SupplierLocationPartOvBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	@Override
	public String getColumnName(String attributeName) {
		if(attributeName.equals("supplier")) {
			return ATTRIBUTE_SUPPLIER;
		}
		else if(attributeName.equals("shipFromLocationId")) {
			return ATTRIBUTE_SHIP_FROM_LOCATION_ID;
		}
		else if(attributeName.equals("supplierRegionCode")) {
			return ATTRIBUTE_SUPPLIER_REGION_CODE;
		}
		else if(attributeName.equals("supplierLocationType")) {
			return ATTRIBUTE_SUPPLIER_LOCATION_TYPE;
		}
		else if(attributeName.equals("supplierLocationCode")) {
			return ATTRIBUTE_SUPPLIER_LOCATION_CODE;
		}
		else if(attributeName.equals("supplierPartList")) {
			return ATTRIBUTE_SUPPLIER_PART_LIST;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, SupplierLocationPartOvBean.class);
	}

	public int delete(SupplierLocationPartOvBean lBean)
	throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();

			String sqlQuery = " delete from supplier_inventory_snap ";
			//			where supplier = '" +
			//			                  lBean.getSupplier() + "' and ship_from_location_id = '" +
			//			                  lBean.getShipFromLocationId() + "' and location_type = '" +
			//			                  lBean.getSupplierLocationType() +"'";

			result =  (new SqlManager()).update(connection, sqlQuery);
			return result;
		}
		finally {
			this.getDbManager().returnConnection(connection);
			return result;
		}
	}
	public Connection connection = null;
	String sqlQuery = " insert into supplier_inventory_snap ( supplier, ship_from_location_id, location_type, supplier_part_no, quantity) values(" +
	"?,?,?,?,?)";
	//    "'"+lBean.getSupplier() + "'," +
	//    "'"+lBean.getShipFromLocationId() + "'," +
	//    "'"+lBean.getSupplierLocationType() + "'," +
	//    "'"+partNum + "'," +
	//    ""+quantity + ")" ;
	PreparedStatement pst = null;
	public void beginBatch() throws BaseException,SQLException {
		connection = this.getDbManager().getConnection();
		pst = connection.prepareStatement(sqlQuery);
	}
	public void endBatch() throws BaseException {
		this.getDbManager().returnConnection(connection);
	}
	public int insertBatch(SupplierLocationPartOvBean lBean,String partNum, String quantity)
	throws BaseException,SQLException {

		pst.setString(1, lBean.getSupplier());
		pst.setString(2, lBean.getShipFromLocationId());
		pst.setString(3, lBean.getSupplierLocationType());
		pst.setString(4, partNum);
		//		pst.setBigDecimal(5, new BigDecimal(quantity));
		pst.setString(5,quantity);
		//pst.executeBatch(); I need to concat the strings to do this.
		//		result = pst.executeUpdate();
		return pst.executeUpdate();
	}


	public int insert(SupplierLocationPartOvBean lBean,String partNum, String quantity)
	throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			String sqlQuery = " insert into supplier_inventory_snap ( supplier, ship_from_location_id, location_type, supplier_part_no, quantity) values(" +
			"'"+lBean.getSupplier() + "'," +
			"'"+lBean.getShipFromLocationId() + "'," +
			"'"+lBean.getSupplierLocationType() + "'," +
			"'"+partNum + "'," +
			""+quantity + ")" ;
			Statement st = connection.createStatement();
			result = st.executeUpdate(sqlQuery);
			//		System.out.println(sqlQuery);
		}
		catch (SQLException e) {
			//		log.error("InsertObject error:" + e.getMessage());
			DbSelectException ex = new DbSelectException("error.db.insert");
			ex.setRootCause(e);
			try {
				if( this.getDbManager() != null && connection !=  null )
					this.getDbManager().returnConnection(connection);
			} catch(Exception ee){}
			return result;
		}

		try {
			if( this.getDbManager() != null && connection !=  null )
				this.getDbManager().returnConnection(connection);
		} catch(Exception ee){}
		return result;
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
			map.put("TCM_OPS.SUPPLIER_PART_OBJ",
					Class.forName(
					"com.tcmis.internal.supply.beans.SupplierPartObjBean"));
			map.put("TCM_OPS.SUPPLIER_LOCATION_PART_OBJ",
					Class.forName(
					"com.tcmis.internal.supply.beans.SupplierLocationPartOvBean"));

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
		Collection supplierLocationPartOvBeanColl = new Vector();

		String query = "select value(p) from " + TABLE + " p " +
		getWhereClause(criteria) + getOrderByClause(sortCriteria);
		if(log.isDebugEnabled()) {
			log.debug("QUERY:" + query);
		}
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				SupplierLocationPartOvBean b = (SupplierLocationPartOvBean) rs.getObject(1);
				supplierLocationPartOvBeanColl.add(b);
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
		return supplierLocationPartOvBeanColl;
	}
	public String selectSupplierAccountNumber(String RegionCode) throws
	BaseException {
		Connection conn = null;
		Collection c = null;
		String AccountNumber = "";
		try {
			conn = this.getDbManager().getConnection();
			String query = "select supplier_account_number from customer.supplier_location_mapping where SHIP_FROM_LOCATION_ID in ("+
			" select SHIP_FROM_LOCATION_ID from supplier_ship_from_location where supplier_region_code = '"+ RegionCode+ "') and rownum < 2";
			if(log.isDebugEnabled()) {
				log.debug("QUERY:" + query);
			}
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			rs.next();
			AccountNumber = rs.getString(1);
			rs.close();
			st.close();
		}
		catch (SQLException e) {
			log.error("selectObject error:" + e.getMessage());
			DbSelectException ex = new DbSelectException("error.db.select");
			ex.setRootCause(e);
			throw ex;
		}
		finally {
			this.getDbManager().returnConnection(conn);
		}
		return AccountNumber;
	}
}