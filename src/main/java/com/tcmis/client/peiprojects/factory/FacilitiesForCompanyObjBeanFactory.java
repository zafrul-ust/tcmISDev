package com.tcmis.client.peiprojects.factory;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.peiprojects.beans.FacilitiesForCompanyObjBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DbSelectException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;

/******************************************************************************
 * CLASSNAME: FacilitiesForCompanyObjBeanFactory <br>
 * @version: 1.0, Jan 16, 2006 <br>
 *****************************************************************************/


public class FacilitiesForCompanyObjBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_COMPANY_NAME = "COMPANY_NAME";
	public String ATTRIBUTE_FACILITIES = "FACILITIES";

	//table name
	public String TABLE = "FACILITIES_FOR_COMPANY_OBJ";


	//constructor
	public FacilitiesForCompanyObjBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("companyName")) {
			return ATTRIBUTE_COMPANY_NAME;
		}
		else if(attributeName.equals("facilities")) {
			return ATTRIBUTE_FACILITIES;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, FacilitiesForCompanyObjBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(FacilitiesForCompanyObjBean facilitiesForCompanyObjBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
			"" + facilitiesForCompanyObjBean.getCompanyId());

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


	public int delete(FacilitiesForCompanyObjBean facilitiesForCompanyObjBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("companyId", "SearchCriterion.EQUALS",
			"" + facilitiesForCompanyObjBean.getCompanyId());

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
	public int insert(FacilitiesForCompanyObjBean facilitiesForCompanyObjBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(facilitiesForCompanyObjBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(FacilitiesForCompanyObjBean facilitiesForCompanyObjBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
			ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_COMPANY_NAME + "," +
			ATTRIBUTE_FACILITIES + ")" +
			" values (" +
			SqlHandler.delimitString(facilitiesForCompanyObjBean.getCompanyId()) + "," +
			SqlHandler.delimitString(facilitiesForCompanyObjBean.getCompanyName()) + "," +
			SqlHandler.delimitString(facilitiesForCompanyObjBean.getFacilities()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(FacilitiesForCompanyObjBean facilitiesForCompanyObjBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(facilitiesForCompanyObjBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(FacilitiesForCompanyObjBean facilitiesForCompanyObjBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_COMPANY_ID + "=" +
				SqlHandler.delimitString(facilitiesForCompanyObjBean.getCompanyId()) + "," +
			ATTRIBUTE_COMPANY_NAME + "=" +
				SqlHandler.delimitString(facilitiesForCompanyObjBean.getCompanyName()) + "," +
			ATTRIBUTE_FACILITIES + "=" +
				SqlHandler.delimitString(facilitiesForCompanyObjBean.getFacilities()) + " " +
			"where " + ATTRIBUTE_COMPANY_ID + "=" +
				facilitiesForCompanyObjBean.getCompanyId();

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

		Collection facilitiesForCompanyObjBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			FacilitiesForCompanyObjBean facilitiesForCompanyObjBean = new FacilitiesForCompanyObjBean();
			load(dataSetRow, facilitiesForCompanyObjBean);
			facilitiesForCompanyObjBeanColl.add(facilitiesForCompanyObjBean);
		}

		return facilitiesForCompanyObjBeanColl;
	}

 public Collection selectObject(SearchCriteria criteria) throws BaseException {

		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			java.util.Map map = connection.getTypeMap();
			map.put("COMPANY_TYPE",
							Class.forName(
					"com.tcmis.client.peiprojects.beans.FacilitiesForCompanyObjBean"));
			map.put("COMPANY_FACILITY_TYPE",
							Class.forName(
					"com.tcmis.client.peiprojects.beans.FacilityObjBean"));

			c = selectObject(criteria, connection);
		}
		catch (Exception e) {
			log.error("selectObject error:" + e.getMessage());
			DbSelectException ex = new DbSelectException("error.db.select");
			/*DbSelectException ex = new DbSelectException("db.selectObject.error:" +
					e.getMessage());*/
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

		Collection facilitiesForCompanyObjBeanColl = new Vector();

	String sortBy = " order by " + ATTRIBUTE_COMPANY_NAME;
		String query = "select value(p) from " + TABLE + " p " +
				getWhereClause(criteria) + sortBy;

		if (log.isDebugEnabled()) {
			log.debug("QUERY:" + query);
		}
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				FacilitiesForCompanyObjBean b = (FacilitiesForCompanyObjBean) rs.getObject(1);
				facilitiesForCompanyObjBeanColl.add(b);
			}
			rs.close();
			st.close();
		}
		catch (SQLException e) {
			log.error("selectObject error:" + e.getMessage());
			DbSelectException ex = new DbSelectException("error.db.select");
			/*DbSelectException ex = new DbSelectException("selectObject error:" +
					e.getMessage());*/
			ex.setRootCause(e);
			throw ex;
		}
		return facilitiesForCompanyObjBeanColl;
	}

}