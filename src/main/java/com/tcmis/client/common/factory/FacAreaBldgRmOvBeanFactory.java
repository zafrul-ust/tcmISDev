package com.tcmis.client.common.factory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.common.beans.FacAreaBldgRmOvBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DbSelectException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SortCriteria;

/******************************************************************************
 * CLASSNAME: FacAreaBldgRmOvBeanFactory <br>
 * @version: 1.0, Mar 10, 2011 <br>
 *****************************************************************************/

public class FacAreaBldgRmOvBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_AREA_LIST = "AREA_LIST";
	//table name
	public String TABLE = "FAC_AREA_BLDG_RM_OV";

	//constructor
	public FacAreaBldgRmOvBeanFactory(DbManager dbManager) {
		super(dbManager);
	}

	//get column names
	@Override
	public String getColumnName(String attributeName) {
		if (attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if (attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if (attributeName.equals("areaList")) {
			return ATTRIBUTE_AREA_LIST;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}

	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, FacAreaBldgRmOvBean.class);
	}

	public Collection selectObject(SearchCriteria criteria) throws BaseException {
		return selectObject(criteria, null);
	}

	public Collection selectObject(SearchCriteria criteria, SortCriteria sortCriteria) throws BaseException {
		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			java.util.Map map = connection.getTypeMap();
			map.put("CUSTOMER.ROOM_OBJ", Class.forName("com.tcmis.client.common.beans.RoomObjBean"));
			map.put("CUSTOMER.FLOOR_ROOM_OBJ", Class.forName("com.tcmis.client.common.beans.FloorRoomObjBean"));
			map.put("CUSTOMER.BLDG_FLOOR_OBJ", Class.forName("com.tcmis.client.common.beans.BldgFloorObjBean"));
			map.put("CUSTOMER.AREA_BLDG_FLOOR_RM_OBJ", Class.forName("com.tcmis.client.common.beans.AreaBldgFloorRmObjBean"));
			map.put("CUSTOMER.FAC_AREA_BLDG_RM_OBJ", Class.forName("com.tcmis.client.common.beans.FacAreaBldgRmOvBean"));

			c = selectObject(criteria, sortCriteria, connection);
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

	public Collection selectObject(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn) throws BaseException {
		Collection facAreaBldgRmOvBeanColl = new Vector();

		String query = "select value(p) from " + TABLE + " p " + getWhereClause(criteria) + getOrderByClause(sortCriteria);
		log.debug("QUERY:" + query);
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				FacAreaBldgRmOvBean b = (FacAreaBldgRmOvBean) rs.getObject(1);
				facAreaBldgRmOvBeanColl.add(b);
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
		return facAreaBldgRmOvBeanColl;
	}

}