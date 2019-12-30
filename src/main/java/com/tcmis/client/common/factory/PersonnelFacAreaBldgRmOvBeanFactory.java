package com.tcmis.client.common.factory;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.common.beans.PersonnelFacAreaBldgRmOvBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DbSelectException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SortCriteria;


/******************************************************************************
 * CLASSNAME: PersonnelFacAreaBldgRmOvBeanFactory <br>
 * @version: 1.0, Jan 13, 2012 <br>
 *****************************************************************************/


public class PersonnelFacAreaBldgRmOvBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_PERSONNEL_ID = "PERSONNEL_ID";
	public String ATTRIBUTE_FACILITY_LIST = "FACILITY_LIST";

	//table name
	public String TABLE = "PERSONNEL_FAC_AREA_BLDG_RM_OV";


	//constructor
	public PersonnelFacAreaBldgRmOvBeanFactory(DbManager dbManager) {
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
		else if(attributeName.equals("facilityList")) {
			return ATTRIBUTE_FACILITY_LIST;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, PersonnelFacAreaBldgRmOvBean.class);
	}

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

		Collection personnelFacAreaBldgRmOvBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria)+ getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			PersonnelFacAreaBldgRmOvBean personnelFacAreaBldgRmOvBean = new PersonnelFacAreaBldgRmOvBean();
			load(dataSetRow, personnelFacAreaBldgRmOvBean);
			personnelFacAreaBldgRmOvBeanColl.add(personnelFacAreaBldgRmOvBean);
		}

		return personnelFacAreaBldgRmOvBeanColl;
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
		 map.put("CUSTOMER.FAC_NAME_AREA_BLDG_RM_OBJ",
			Class.forName(
			"com.tcmis.client.common.beans.FacNameAreaBldgRmObjBean"));
		 map.put("CUSTOMER.AREA_BLDG_FLOOR_RM_OBJ",
			Class.forName(
			"com.tcmis.client.common.beans.AreaBldgFloorRmObjBean"));
		 map.put("CUSTOMER.PERSONNEL_FAC_AREA_BLDG_RM_OBJ",
			Class.forName(
			"com.tcmis.client.common.beans.PersonnelFacAreaBldgRmOvBean"));
		 map.put("CUSTOMER.BLDG_FLOOR_OBJ",
			Class.forName(
			"com.tcmis.client.common.beans.BldgFloorObjBean"));
		 map.put("CUSTOMER.FLOOR_ROOM_OBJ",
			Class.forName(
			"com.tcmis.client.common.beans.FloorRoomObjBean"));
		 map.put("CUSTOMER.ROOM_OBJ",
			Class.forName(
			"com.tcmis.client.common.beans.RoomObjBean"));

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
		Collection personnelFacAreaBldgRmOvBeanColl = new Vector();

		String query = "select value(p) from " + TABLE + " p " +
		 getWhereClause(criteria) + getOrderByClause(sortCriteria);
		 log.debug("QUERY:" + query);
			try {
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(query);
				while (rs.next()) {
					PersonnelFacAreaBldgRmOvBean b = (PersonnelFacAreaBldgRmOvBean) rs.getObject(1);
					personnelFacAreaBldgRmOvBeanColl.add(b);
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
			return personnelFacAreaBldgRmOvBeanColl;
		}

}