package com.tcmis.internal.msds.factory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.internal.msds.beans.FacBldgFloorDeptEditOvBean;
import com.tcmis.internal.msds.beans.MsdsInputBean;

/******************************************************************************
 * CLASSNAME: FacBldgFloorDeptEditOvBeanFactory <br>
 * @version: 1.0, May 23, 2005 <br>
 *****************************************************************************/

public class FacBldgFloorDeptEditOvBeanFactory
extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_DEPT_BLDG_FLOOR = "DEPT_BLDG_FLOOR";

	//table name
	public String TABLE = "FAC_BLDG_FLOOR_DEPT_EDIT_OV";

	//constructor
	public FacBldgFloorDeptEditOvBeanFactory(DbManager dbManager) {
		super(dbManager);
	}

	//get column names
	@Override
	public String getColumnName(String attributeName) {
		if (attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if (attributeName.equals("deptBldgFloor")) {
			return ATTRIBUTE_DEPT_BLDG_FLOOR;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}

	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, FacBldgFloorDeptEditOvBean.class);
	}

	//you need to verify the primary key(s) before uncommenting this
	/*
//delete
   public int delete(FacBldgFloorDeptEditOvBean facBldgFloorDeptEditOvBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("facilityId", "SearchCriterion.EQUALS",
     "" + facBldgFloorDeptEditOvBean.getFacilityId());
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
   public int delete(FacBldgFloorDeptEditOvBean facBldgFloorDeptEditOvBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("facilityId", "SearchCriterion.EQUALS",
     "" + facBldgFloorDeptEditOvBean.getFacilityId());
    return delete(criteria, conn);
   }
	 */

	public int delete(SearchCriteria criteria) throws BaseException {

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

	public int delete(SearchCriteria criteria, Connection conn) throws
	BaseException {

		String sqlQuery = " delete from " + TABLE + " " +
		getWhereClause(criteria);

		return new SqlManager().update(conn, sqlQuery);
	}

	//you need to verify the primary key(s) before uncommenting this
	/*
//insert
   public int insert(FacBldgFloorDeptEditOvBean facBldgFloorDeptEditOvBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(facBldgFloorDeptEditOvBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(FacBldgFloorDeptEditOvBean facBldgFloorDeptEditOvBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_FACILITY_ID + "," +
     ATTRIBUTE_DEPT_BLDG_FLOOR + ")" +
     " values (" +
       SqlHandler.delimitString(facBldgFloorDeptEditOvBean.getFacilityId()) + "," +
       SqlHandler.delimitString(facBldgFloorDeptEditOvBean.getDeptBldgFloor()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(FacBldgFloorDeptEditOvBean facBldgFloorDeptEditOvBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(facBldgFloorDeptEditOvBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(FacBldgFloorDeptEditOvBean facBldgFloorDeptEditOvBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_FACILITY_ID + "=" +
       SqlHandler.delimitString(facBldgFloorDeptEditOvBean.getFacilityId()) + "," +
     ATTRIBUTE_DEPT_BLDG_FLOOR + "=" +
       SqlHandler.delimitString(facBldgFloorDeptEditOvBean.getDeptBldgFloor()) + " " +
     "where " + ATTRIBUTE_FACILITY_ID + "=" +
      facBldgFloorDeptEditOvBean.getFacilityId();
    return new SqlManager().update(conn, query);
   }
	 */

	//select
	public Collection select(SearchCriteria criteria) throws BaseException {

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

	public Collection select(SearchCriteria criteria, Connection conn) throws
	BaseException {

		Collection facBldgFloorDeptEditOvBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
		getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			FacBldgFloorDeptEditOvBean facBldgFloorDeptEditOvBean = new
			FacBldgFloorDeptEditOvBean();
			load(dataSetRow, facBldgFloorDeptEditOvBean);
			facBldgFloorDeptEditOvBeanColl.add(facBldgFloorDeptEditOvBean);
		}

		return facBldgFloorDeptEditOvBeanColl;
	}

	public Collection selectObject(SearchCriteria criteria) throws BaseException {

		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			java.util.Map map = connection.getTypeMap();
			map.put("FAC_DEPT_BLDG_FLOOR_EDIT_OBJ",
					Class.forName(
					"com.tcmis.internal.msds.beans.FacBldgFloorDeptEditOvBean"));
			map.put("DEPT_BLDG_FLOOR_EDIT_OBJ",
					Class.forName(
					"com.tcmis.internal.msds.beans.DeptBldgFloorEditOvBean"));
			map.put("BLDG_FLOOR_EDIT_OBJ",
					Class.forName(
					"com.tcmis.internal.msds.beans.BldgFloorEditOvBean"));
			map.put("FLOOR_EDIT_OBJ",
					Class.forName(
					"com.tcmis.internal.msds.beans.FloorEditOvBean"));

			c = selectObject(criteria, connection);
		}
		catch(Exception e) {
			BaseException be = new BaseException("Error querying fac_bldg_floor_dept_edit_ov:" + e.getMessage());
			be.setRootCause(e);
			throw be;
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}

	public Collection selectObject(SearchCriteria criteria, Connection conn) throws
	BaseException {

		Collection coll = new Vector();

		String query = "select value(p) from " + TABLE + " p " +
		getWhereClause(criteria);
		if (log.isDebugEnabled()) {
			log.debug("QUERY:" + query);
		}
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				FacBldgFloorDeptEditOvBean b = (FacBldgFloorDeptEditOvBean) rs.
				getObject(
						1);
				coll.add(b);
			}
			rs.close();
			st.close();
		}
		catch(Exception e) {
			BaseException be = new BaseException("Error querying fac_bldg_floor_dept_edit_ov:" + e.getMessage());
			be.setRootCause(e);
			throw be;
		}
		return coll;
	}

	public BigDecimal getNextMillerMsdsSeq() throws BaseException {

		Connection connection = null;
		BigDecimal b = null;
		try {
			connection = this.getDbManager().getConnection();
			b = new BigDecimal("" +
					new SqlManager().getOracleSequence(connection, "miller_msds_seq"));
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return b;
	}

	public String processMsds(MsdsInputBean bean) throws
	BaseException {
		//hazard classification can be null but I must pass an empty string to the proc
		if(bean.getHazardClassification() == null) {
			bean.setHazardClassification("");
		}
		Collection in = new Vector(13);
		Collection out = new Vector(1);
		if(log.isDebugEnabled()) {
			log.debug("msds:" + bean.getMsds());
			log.debug("tn:" + bean.getTradeName());
			log.debug("mf:" + bean.getManufacturerName());
			log.debug("st:" + bean.getStatus());
			log.debug("hc:" + bean.getHazardClassification());
			log.debug("fac:" + bean.getFacilityId());
			log.debug("dep:" + bean.getDepartment());
			log.debug("bui:" + bean.getBuilding());
			log.debug("flo:" + bean.getFloor());
			log.debug("fn:" + bean.getFileName());
			log.debug("ol:" + bean.getOnLine());
			log.debug("rd:" + bean.getRevisionDate());
		}

		in.add(bean.getMsds());
		in.add(bean.getTradeName());
		in.add(bean.getManufacturerName());
		in.add(new BigDecimal("0"));
		in.add(bean.getStatus());
		in.add(bean.getHazardClassification());
		in.add(bean.getFacilityId());
		in.add(bean.getBuilding());
		in.add(bean.getFloor());
		in.add(bean.getDepartment());
		in.add(bean.getFileName());
		in.add(bean.getOnLine());
		java.sql.Date procDate = null;
		if(bean.getRevisionDate() != null) {
			procDate = new java.sql.Date(bean.getRevisionDate().getTime());
		}
		in.add(procDate);
		in.add("foo");
		out.add(new Integer(java.sql.Types.VARCHAR));
		out = getDbManager().doProcedure("msds_data_load", in, out);

		Iterator iterator = out.iterator();
		String message = null;
		while (iterator.hasNext()) {
			message = (String) iterator.next(); ;
		}
		return message;
	}

	public String deleteMsdsLocation(MsdsInputBean bean) throws
	BaseException {
		Collection in = new Vector(5);
		Collection out = new Vector(1);

		in.add(bean.getMsds());
		in.add(bean.getFacilityIdToDelete());
		in.add(bean.getBuildingToDelete());
		in.add(bean.getFloorToDelete());
		in.add(bean.getDepartmentToDelete());
		out.add(new Integer(java.sql.Types.VARCHAR));
		out = getDbManager().doProcedure("msds_data_delete", in, out);

		Iterator iterator = out.iterator();
		String message = null;
		while (iterator.hasNext()) {
			message = (String) iterator.next(); ;
		}
		return message;
	}

}