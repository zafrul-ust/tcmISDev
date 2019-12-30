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
import com.tcmis.client.catalog.beans.FacAppUserGrpOvBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DbSelectException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;

/******************************************************************************
 * CLASSNAME: FacAppUserGrpOvBeanFactory <br>
 * @version: 1.0, Feb 13, 2006 <br>
 *****************************************************************************/

public class FacAppUserGrpOvBeanFactory
 extends BaseBeanFactory {

 Log log = LogFactory.getLog(this.getClass());

 //column names
 public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
 public String ATTRIBUTE_FACILITY_NAME = "FACILITY_NAME";
 public String ATTRIBUTE_APPLICATION_VAR = "APPLICATION_VAR";
 public String ATTRIBUTE_USER_GROUP_VAR = "USER_GROUP_VAR";
 public String ATTRIBUTE_FAC_DOCK_VAR = "FAC_DOCK_VAR";

 //table name
 public String TABLE = "FAC_APP_USER_GRP_OV";

 //constructor
 public FacAppUserGrpOvBeanFactory(DbManager dbManager) {
	super(dbManager);
 }

 //get column names
 public String getColumnName(String attributeName) {
	if (attributeName.equals("facilityId")) {
	 return ATTRIBUTE_FACILITY_ID;
	}
	else if (attributeName.equals("facilityName")) {
	 return ATTRIBUTE_FACILITY_NAME;
	}
	else if (attributeName.equals("applicationVar")) {
	 return ATTRIBUTE_APPLICATION_VAR;
	}
	else if (attributeName.equals("userGroupVar")) {
	 return ATTRIBUTE_USER_GROUP_VAR;
	}
	else if (attributeName.equals("facDockVar")) {
	 return ATTRIBUTE_FAC_DOCK_VAR;
	}
	else {
	 return super.getColumnName(attributeName);
	}
 }

 //get column types
 public int getType(String attributeName) {
	return super.getType(attributeName, FacAppUserGrpOvBean.class);
 }

//you need to verify the primary key(s) before uncommenting this
 /*
//delete
	 public int delete(FacAppUserGrpOvBean facAppUserGrpOvBean)
	throws BaseException {
	SearchCriteria criteria = new SearchCriteria("facilityId", "SearchCriterion.EQUALS",
	"" + facAppUserGrpOvBean.getFacilityId());
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
	 public int delete(FacAppUserGrpOvBean facAppUserGrpOvBean, Connection conn)
	throws BaseException {
	SearchCriteria criteria = new SearchCriteria("facilityId", "SearchCriterion.EQUALS",
	"" + facAppUserGrpOvBean.getFacilityId());
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

 public int delete(SearchCriteria criteria,
	Connection conn) throws BaseException {

	String sqlQuery = " delete from " + TABLE + " " + getWhereClause(criteria);

	return new SqlManager().update(conn, sqlQuery);
 }

//you need to verify the primary key(s) before uncommenting this
 /*
//insert
	 public int insert(FacAppUserGrpOvBean facAppUserGrpOvBean)
	throws BaseException {
	Connection connection = null;
	int result = 0;
	try {
	connection = getDbManager().getConnection();
	result = insert(facAppUserGrpOvBean, connection);
	}
	finally {
	this.getDbManager().returnConnection(connection);
	}
	return result;
	 }
	 public int insert(FacAppUserGrpOvBean facAppUserGrpOvBean, Connection conn)
	throws BaseException {
	SqlManager sqlManager = new SqlManager();
	String query  =
	"insert into " + TABLE + " (" +
	ATTRIBUTE_FACILITY_ID + "," +
	ATTRIBUTE_FACILITY_NAME + "," +
	ATTRIBUTE_APPLICATION_VAR + "," +
	ATTRIBUTE_USER_GROUP_VAR + "," +
	ATTRIBUTE_FAC_DOCK_VAR + ")" +
	" values (" +
	SqlHandler.delimitString(facAppUserGrpOvBean.getFacilityId()) + "," +
	SqlHandler.delimitString(facAppUserGrpOvBean.getFacilityName()) + "," +
	SqlHandler.delimitString(facAppUserGrpOvBean.getApplicationVar()) + "," +
	SqlHandler.delimitString(facAppUserGrpOvBean.getUserGroupVar()) + "," +
	SqlHandler.delimitString(facAppUserGrpOvBean.getFacDockVar()) + ")";
	return sqlManager.update(conn, query);
	 }
//update
	 public int update(FacAppUserGrpOvBean facAppUserGrpOvBean)
	throws BaseException {
	Connection connection = null;
	int result = 0;
	try {
	connection = getDbManager().getConnection();
	result = update(facAppUserGrpOvBean, connection);
	}
	finally {
	this.getDbManager().returnConnection(connection);
	}
	return result;
	 }
	 public int update(FacAppUserGrpOvBean facAppUserGrpOvBean, Connection conn)
	throws BaseException {
	String query  = "update " + TABLE + " set " +
	ATTRIBUTE_FACILITY_ID + "=" +
	 SqlHandler.delimitString(facAppUserGrpOvBean.getFacilityId()) + "," +
	ATTRIBUTE_FACILITY_NAME + "=" +
	 SqlHandler.delimitString(facAppUserGrpOvBean.getFacilityName()) + "," +
	ATTRIBUTE_APPLICATION_VAR + "=" +
	 SqlHandler.delimitString(facAppUserGrpOvBean.getApplicationVar()) + "," +
	ATTRIBUTE_USER_GROUP_VAR + "=" +
	 SqlHandler.delimitString(facAppUserGrpOvBean.getUserGroupVar()) + "," +
	ATTRIBUTE_FAC_DOCK_VAR + "=" +
	 SqlHandler.delimitString(facAppUserGrpOvBean.getFacDockVar()) + " " +
	"where " + ATTRIBUTE_FACILITY_ID + "=" +
	 facAppUserGrpOvBean.getFacilityId();
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

 public Collection select(SearchCriteria criteria,
	Connection conn) throws BaseException {

	Collection facAppUserGrpOvBeanColl = new Vector();

	String query = "select * from " + TABLE + " " + getWhereClause(criteria);

	DataSet dataSet = new SqlManager().select(conn, query);

	Iterator dataIter = dataSet.iterator();

	while (dataIter.hasNext()) {
	 DataSetRow dataSetRow = (DataSetRow) dataIter.next();
	 FacAppUserGrpOvBean facAppUserGrpOvBean = new FacAppUserGrpOvBean();
	 load(dataSetRow, facAppUserGrpOvBean);
	 facAppUserGrpOvBeanColl.add(facAppUserGrpOvBean);
	}

	return facAppUserGrpOvBeanColl;
 }

 public Collection selectObject(SearchCriteria criteria) throws BaseException {
	Connection connection = null;
	Collection c = null;
	try {
	 connection = this.getDbManager().getConnection();
	 java.util.Map map = connection.getTypeMap();

	 map.put("CUSTOMER.FAC_APP_USER_GRP_OBJ",
		Class.forName("com.tcmis.client.catalog.beans.FacAppUserGrpOvBean"));
	 map.put("CUSTOMER.APPLICATION_OBJ",
		Class.forName("com.tcmis.client.catalog.beans.ApplicationObjBean"));
	 map.put("CUSTOMER.USER_GROUP_OBJ",
		Class.forName("com.tcmis.client.catalog.beans.UserGroupObjBean"));
	 map.put("CUSTOMER.FAC_DOCK_OBJ",
		Class.forName("com.tcmis.client.catalog.beans.FacilityDockObjBean"));
	 map.put("CUSTOMER.DELIVERY_POINT_OBJ",
		Class.forName("com.tcmis.client.catalog.beans.DeliveryPointObjBean"));

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
	Collection facAppUserGrpOvBeanColl = new Vector();

	String sortBy = " order by " + ATTRIBUTE_FACILITY_NAME;
	String query = "select value(p) from " + TABLE + " p " +
	 getWhereClause(criteria) + sortBy;

	if (log.isDebugEnabled()) {
	 log.debug("QUERY:" + query);
	}
	try {
	 Statement st = conn.createStatement();
	 ResultSet rs = st.executeQuery(query);
	 while (rs.next()) {
		FacAppUserGrpOvBean b = (FacAppUserGrpOvBean) rs.getObject(1);
		facAppUserGrpOvBeanColl.add(b);
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
	return facAppUserGrpOvBeanColl;
 }
}