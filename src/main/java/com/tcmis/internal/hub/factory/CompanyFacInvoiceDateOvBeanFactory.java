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
import com.tcmis.internal.hub.beans.CompanyFacInvoiceDateOvBean;


/******************************************************************************
 * CLASSNAME: CompanyFacInvoiceDateOvBeanFactory <br>
 * @version: 1.0, Feb 7, 2006 <br>
 *****************************************************************************/


public class CompanyFacInvoiceDateOvBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_PERSONNEL_ID = "PERSONNEL_ID";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_COMPANY_NAME = "COMPANY_NAME";
	public String ATTRIBUTE_FAC_INVOICE_PERIOD_VAR = "FAC_INVOICE_PERIOD_VAR";

	//table name
	public String TABLE = "COMPANY_FAC_INVOICE_DATE_OV";


	//constructor
	public CompanyFacInvoiceDateOvBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("personnelId")) {
			return ATTRIBUTE_PERSONNEL_ID;
		}
		else if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if(attributeName.equals("companyName")) {
			return ATTRIBUTE_COMPANY_NAME;
		}
		else if(attributeName.equals("facInvoicePeriodVar")) {
			return ATTRIBUTE_FAC_INVOICE_PERIOD_VAR;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, CompanyFacInvoiceDateOvBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(CompanyFacInvoiceDateOvBean companyFacInvoiceDateOvBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("personnelId", "SearchCriterion.EQUALS",
			"" + companyFacInvoiceDateOvBean.getPersonnelId());

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


	public int delete(CompanyFacInvoiceDateOvBean companyFacInvoiceDateOvBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("personnelId", "SearchCriterion.EQUALS",
			"" + companyFacInvoiceDateOvBean.getPersonnelId());

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
	public int insert(CompanyFacInvoiceDateOvBean companyFacInvoiceDateOvBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(companyFacInvoiceDateOvBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(CompanyFacInvoiceDateOvBean companyFacInvoiceDateOvBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
			ATTRIBUTE_PERSONNEL_ID + "," +
			ATTRIBUTE_COMPANY_ID + "," +
			ATTRIBUTE_COMPANY_NAME + "," +
			ATTRIBUTE_FAC_INVOICE_PERIOD_VAR + ")" +
			" values (" +
			companyFacInvoiceDateOvBean.getPersonnelId() + "," +
			SqlHandler.delimitString(companyFacInvoiceDateOvBean.getCompanyId()) + "," +
			SqlHandler.delimitString(companyFacInvoiceDateOvBean.getCompanyName()) + "," +
			SqlHandler.delimitString(companyFacInvoiceDateOvBean.getFacInvoicePeriodVar()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(CompanyFacInvoiceDateOvBean companyFacInvoiceDateOvBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(companyFacInvoiceDateOvBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(CompanyFacInvoiceDateOvBean companyFacInvoiceDateOvBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_PERSONNEL_ID + "=" +
				StringHandler.nullIfZero(companyFacInvoiceDateOvBean.getPersonnelId()) + "," +
			ATTRIBUTE_COMPANY_ID + "=" +
				SqlHandler.delimitString(companyFacInvoiceDateOvBean.getCompanyId()) + "," +
			ATTRIBUTE_COMPANY_NAME + "=" +
				SqlHandler.delimitString(companyFacInvoiceDateOvBean.getCompanyName()) + "," +
			ATTRIBUTE_FAC_INVOICE_PERIOD_VAR + "=" +
				SqlHandler.delimitString(companyFacInvoiceDateOvBean.getFacInvoicePeriodVar()) + " " +
			"where " + ATTRIBUTE_PERSONNEL_ID + "=" +
				companyFacInvoiceDateOvBean.getPersonnelId();

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

		Collection companyFacInvoiceDateOvBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			CompanyFacInvoiceDateOvBean companyFacInvoiceDateOvBean = new CompanyFacInvoiceDateOvBean();
			load(dataSetRow, companyFacInvoiceDateOvBean);
			companyFacInvoiceDateOvBeanColl.add(companyFacInvoiceDateOvBean);
		}

		return companyFacInvoiceDateOvBeanColl;
	}

 public Collection selectObject(SearchCriteria criteria) throws BaseException {
	 Connection connection = null;
	 Collection c = null;
	 try {
		connection = this.getDbManager().getConnection();
		java.util.Map map = connection.getTypeMap();

		map.put("COMP_FAC_INVOICE_PERIOD_OBJ",
		 Class.forName(
		 "com.tcmis.internal.hub.beans.CompanyFacInvoiceDateOvBean"));
		map.put("FAC_INVOICE_PERIOD_OBJ",
		 Class.forName(
		 "com.tcmis.internal.hub.beans.FacilityInvoicePeriodObjBean"));
		map.put("INVOICE_PERIOD_OBJ",
		 Class.forName(
		 "com.tcmis.internal.hub.beans.InovicePeriodObjBean"));
		map.put("ANIVERSARY_DATE_OBJ",
		 Class.forName(
		 "com.tcmis.internal.hub.beans.AniversaryDateObjBean"));
		map.put("INVENTORY_GROUP_OBJ",
			Class.forName(
			"com.tcmis.internal.hub.beans.InventoryGroupObjBean"));

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
	 Collection companyFacInvoiceDateOvBeanColl = new Vector();

	//String sortBy = " order by " + ATTRIBUTE_COMPANY_NAME;
	 String query = "select value(p) from " + TABLE + " p " +
			 getWhereClause(criteria) ;

	 if (log.isDebugEnabled()) {
		 log.debug("QUERY:" + query);
	 }
	 try {
		 Statement st = conn.createStatement();
		 ResultSet rs = st.executeQuery(query);
		 while (rs.next()) {
			 CompanyFacInvoiceDateOvBean b = (CompanyFacInvoiceDateOvBean) rs.getObject(1);
			 companyFacInvoiceDateOvBeanColl.add(b);
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
	 return companyFacInvoiceDateOvBeanColl;
 }

}