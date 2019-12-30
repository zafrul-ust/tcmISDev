package com.tcmis.client.openCustomer.factory;


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
import com.tcmis.client.openCustomer.beans.OpenUserShiptoSelOvBean;


import java.sql.SQLException;
import com.tcmis.common.exceptions.DbSelectException;
import java.sql.Statement;
import java.sql.ResultSet;


/******************************************************************************
 * CLASSNAME: OpenUserShiptoSelOvBeanFactory <br>
 * @version: 1.0, Feb 7, 2011 <br>
 *****************************************************************************/


public class OpenUserShiptoSelOvBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_USER_ID = "USER_ID";
	public String ATTRIBUTE_PERSONNEL_ID = "PERSONNEL_ID";
	public String ATTRIBUTE_CUSTOMER_ID = "CUSTOMER_ID";
	public String ATTRIBUTE_CUSTOMER_NAME = "CUSTOMER_NAME";
	public String ATTRIBUTE_USER_ACCESS = "USER_ACCESS";
	public String ATTRIBUTE_FACILITY_LIST = "FACILITY_LIST";

	//table name
	public String TABLE = "OPEN_USER_SHIPTO_SEL_OV";

	//constructor
	public OpenUserShiptoSelOvBeanFactory(DbManager dbManager) {
		super(dbManager);
	}

	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("userId")) {
			return ATTRIBUTE_USER_ID;
		}
		else if(attributeName.equals("personnelId")) {
			return ATTRIBUTE_PERSONNEL_ID;
		}
		else if(attributeName.equals("customerId")) {
			return ATTRIBUTE_CUSTOMER_ID;
		}
		else if(attributeName.equals("customerName")) {
			return ATTRIBUTE_CUSTOMER_NAME;
		}
		else if(attributeName.equals("userAccess")) {
			return ATTRIBUTE_USER_ACCESS;
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
		return super.getType(attributeName, OpenUserShiptoSelOvBean.class);
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
		 map.put("CUSTOMER.OPEN_USER_SHIPTO_SEL_OBJ",
			Class.forName("com.tcmis.client.openCustomer.beans.OpenUserShiptoSelOvBean"));
		 map.put("CUSTOMER.OPEN_USER_FACILITY_OBJ",
			Class.forName("com.tcmis.client.openCustomer.beans.OpenUserFacilityObjBean"));

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
		Collection openUserShiptoSelOvBeanColl = new Vector();

		String query = "select value(p) from " + TABLE + " p " +
		 getWhereClause(criteria) + getOrderByClause(sortCriteria);
		 log.debug("QUERY:" + query);
			try {
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(query);
				while (rs.next()) {
					OpenUserShiptoSelOvBean b = (OpenUserShiptoSelOvBean) rs.getObject(1);
					openUserShiptoSelOvBeanColl.add(b);
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
			return openUserShiptoSelOvBeanColl;
	}

}