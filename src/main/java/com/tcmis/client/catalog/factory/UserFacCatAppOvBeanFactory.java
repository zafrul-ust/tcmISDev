package com.tcmis.client.catalog.factory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.catalog.beans.UserFacCatAppOvBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DbSelectException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;


public class UserFacCatAppOvBeanFactory extends BaseBeanFactory {

	 Log log = LogFactory.getLog(this.getClass());

	 //column names
	 public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	 public String ATTRIBUTE_PERSONNEL_ID = "PERSONNEL_ID";
	 public String ATTRIBUTE_USER_NAME = "USER_NAME";
	 public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	 public String ATTRIBUTE_ADMIN_ROLE = "ADMIN_ROLE";
	 public String ATTRIBUTE_FACILITY_NAME = "FACILITY_NAME";
	 public String ATTRIBUTE_CATALOG_LIST = "CATALOG_LIST";
	 public String ATTRIBUTE_APPLICATION_LIST = "APPLICATION_LIST";
	 

	 //table name
	 public String TABLE = "USER_FAC_CAT_APP_OV";

	 //constructor
	 public UserFacCatAppOvBeanFactory(DbManager dbManager) {
		super(dbManager);
	 }

	 //get column names
	 public String getColumnName(String attributeName) {
		 if (attributeName.equals("companyId")) {
			 return ATTRIBUTE_COMPANY_ID;
			}
		 if (attributeName.equals("personnelId")) {
			 return ATTRIBUTE_PERSONNEL_ID;
			}
		 if (attributeName.equals("userName")) {
			 return ATTRIBUTE_USER_NAME;
			}
		if (attributeName.equals("facilityId")) {
		 return ATTRIBUTE_FACILITY_ID;
		}
		if (attributeName.equals("adminRole")) {
			 return ATTRIBUTE_ADMIN_ROLE;
			}
		else if (attributeName.equals("facilityName")) {
		 return ATTRIBUTE_FACILITY_NAME;
		}
		else if (attributeName.equals("catalogList")) {
		 return ATTRIBUTE_CATALOG_LIST;
		}
		else if (attributeName.equals("applicationList")) {
		 return ATTRIBUTE_APPLICATION_LIST;
		}
		
		else {
		 return super.getColumnName(attributeName);
		}
	 }

	 //get column types
	 public int getType(String attributeName) {
		return super.getType(attributeName, UserFacCatAppOvBean.class);
	 }

	

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

	 public int delete(SearchCriteria criteria,
		Connection conn) throws BaseException {

		String sqlQuery = " delete from " + TABLE + " " + getWhereClause(criteria);

		return new SqlManager().update(conn, sqlQuery);
	 }

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

	 public Collection select(SearchCriteria criteria,
		Connection conn) throws BaseException {

		Collection userFacCatAppOvBeanColl = new Vector();

		String query = "select * from " + TABLE + " " + getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
		 DataSetRow dataSetRow = (DataSetRow) dataIter.next();
		 UserFacCatAppOvBean userFacCatAppOvBean = new UserFacCatAppOvBean();
		 load(dataSetRow, userFacCatAppOvBean);
		 userFacCatAppOvBeanColl.add(userFacCatAppOvBean);
		}

		return userFacCatAppOvBeanColl;
	 }

	 public Collection selectObject(SearchCriteria criteria) throws BaseException {
		Connection connection = null;
		Collection c = null;
		try {
		 connection = this.getDbManager().getConnection();
		 java.util.Map map = connection.getTypeMap();

		 map.put("CUSTOMER.USER_FAC_CAT_APP_OBJ",
			Class.forName("com.tcmis.client.catalog.beans.UserFacCatAppOvBean"));
		 map.put("CUSTOMER.CATALOG_OBJ",
			Class.forName("com.tcmis.client.catalog.beans.CatalogObjBean"));
		 map.put("CUSTOMER.APPLICATION_OBJ",
			Class.forName("com.tcmis.client.catalog.beans.ApplicationObjBean"));
		 
		 c = selectObject(criteria, connection);
		}
		catch (Exception e) {
		 e.printStackTrace();
		 log.error("selectObject error:" + e.getMessage());
		 DbSelectException ex = new DbSelectException("error.db.select");
		 /*DbSelectException ex = new DbSelectException("db.selectObject.error:" +
				e.getMessage());*/ex.setRootCause(e);
		 throw ex;
		}
		finally {
		 this.getDbManager().returnConnection(connection);
		}
		return c;
	 }

	 public Collection selectObject(SearchCriteria criteria,
		Connection conn) throws BaseException {
		Collection userFacCatAppOvBeanColl = new Vector();

		//String sortBy = " order by " + ATTRIBUTE_FACILITY_NAME;
		String query = "select value(p) from " + TABLE + " p " +
		 getWhereClause(criteria);// + sortBy;

		if (log.isDebugEnabled()) {
		 log.debug("QUERY:" + query);
		}
		try {
		 Statement st = conn.createStatement();
		 ResultSet rs = st.executeQuery(query);
		 while (rs.next()) {
			 UserFacCatAppOvBean b = (UserFacCatAppOvBean) rs.getObject(1);
			 userFacCatAppOvBeanColl.add(b);
		 }
		 rs.close();
		 st.close();
		}
		catch (SQLException e) {
		 e.printStackTrace();
		 log.error("selectObject error:" + e.getMessage());
		 DbSelectException ex = new DbSelectException("error.db.select");
		 /*DbSelectException ex = new DbSelectException("selectObject error:" +
				e.getMessage());*/ex.setRootCause(e);
		 throw ex;
		}
		return userFacCatAppOvBeanColl;
	 }


}
