package com.tcmis.client.report.factory;


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
import com.tcmis.client.report.beans.DivFacGrpFacOvBean;


import java.sql.SQLException;import com.tcmis.common.exceptions.DbSelectException;import java.sql.Statement;import java.sql.ResultSet;


/******************************************************************************
 * CLASSNAME: DivFacGrpFacOvBeanFactory <br>
 * @version: 1.0, Mar 15, 2011 <br>
 *****************************************************************************/


public class DivFacGrpFacOvBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_DIVISION_ID = "DIVISION_ID";
	public String ATTRIBUTE_DIVISION_DESCRIPTION = "DIVISION_DESCRIPTION";
	public String ATTRIBUTE_FACILITY_GROUP_LIST = "FACILITY_GROUP_LIST";

	//table name
	public String TABLE = "DIV_FAC_GRP_FAC_OV";


	//constructor
	public DivFacGrpFacOvBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("divisionId")) {
			return ATTRIBUTE_DIVISION_ID;
		}
		else if(attributeName.equals("divisionDescription")) {
			return ATTRIBUTE_DIVISION_DESCRIPTION;
		}
		else if(attributeName.equals("facilityGroupList")) {
			return ATTRIBUTE_FACILITY_GROUP_LIST;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, DivFacGrpFacOvBean.class);
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
		 map.put("CUSTOMER.FACILITY_GROUP_FACILITY_OBJ",
			Class.forName(
			"com.tcmis.client.report.beans.FacilityGroupFacilityObjBean"));
		 map.put("CUSTOMER.DIV_FAC_GRP_FAC_OBJ",
			Class.forName(
			"com.tcmis.client.report.beans.DivFacGrpFacOvBean"));
		 map.put("CUSTOMER.FACILITY_OBJ",
			Class.forName(
			"com.tcmis.client.report.beans.FacilityObjBean"));

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
		Collection divFacGrpFacOvBeanColl = new Vector();

		String query = "select value(p) from " + TABLE + " p " +
		 getWhereClause(criteria) + getOrderByClause(sortCriteria);
		 log.debug("QUERY:" + query);
			try {
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(query);
				while (rs.next()) {
					DivFacGrpFacOvBean b = (DivFacGrpFacOvBean) rs.getObject(1);
					divFacGrpFacOvBeanColl.add(b);
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
			return divFacGrpFacOvBeanColl;
		}

}