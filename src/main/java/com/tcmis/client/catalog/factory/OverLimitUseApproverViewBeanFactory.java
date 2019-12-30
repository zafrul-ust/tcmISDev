package com.tcmis.client.catalog.factory;


import java.sql.Connection;
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
import com.tcmis.client.catalog.beans.OverLimitUseApproverViewBean;


/******************************************************************************
 * CLASSNAME: OverLimitUseApproverViewBeanFactory <br>
 * @version: 1.0, Aug 22, 2006 <br>
 *****************************************************************************/


public class OverLimitUseApproverViewBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
	public String ATTRIBUTE_APPLICATION = "APPLICATION";
	public String ATTRIBUTE_PERSONNEL_ID = "PERSONNEL_ID";
	public String ATTRIBUTE_USE_APPROVER = "USE_APPROVER";
	public String ATTRIBUTE_USE_APPROVER_EMAIL = "USE_APPROVER_EMAIL";
	public String ATTRIBUTE_USE_APPROVER_PHONE = "USE_APPROVER_PHONE";
	public String ATTRIBUTE_APPLICATION_DESC = "APPLICATION_DESC";

	//table name
	public String TABLE = "OVER_LIMIT_USE_APPROVER_VIEW";


	//constructor
	public OverLimitUseApproverViewBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("facilityId")) {
			return ATTRIBUTE_FACILITY_ID;
		}
		else if(attributeName.equals("application")) {
			return ATTRIBUTE_APPLICATION;
		}
		else if(attributeName.equals("personnelId")) {
			return ATTRIBUTE_PERSONNEL_ID;
		}
		else if(attributeName.equals("useApprover")) {
			return ATTRIBUTE_USE_APPROVER;
		}
		else if(attributeName.equals("useApproverEmail")) {
			return ATTRIBUTE_USE_APPROVER_EMAIL;
		}
		else if(attributeName.equals("useApproverPhone")) {
			return ATTRIBUTE_USE_APPROVER_PHONE;
		}
		else if(attributeName.equals("applicationDesc")) {
			return ATTRIBUTE_APPLICATION_DESC;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, OverLimitUseApproverViewBean.class);
	}


//you need to verify the primary key(s) before uncommenting this
/*
	//delete
	public int delete(OverLimitUseApproverViewBean overLimitUseApproverViewBean)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("facilityId", "SearchCriterion.EQUALS",
			"" + overLimitUseApproverViewBean.getFacilityId());

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


	public int delete(OverLimitUseApproverViewBean overLimitUseApproverViewBean, Connection conn)
		throws BaseException {

		SearchCriteria criteria = new SearchCriteria("facilityId", "SearchCriterion.EQUALS",
			"" + overLimitUseApproverViewBean.getFacilityId());

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
	public int insert(OverLimitUseApproverViewBean overLimitUseApproverViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(overLimitUseApproverViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int insert(OverLimitUseApproverViewBean overLimitUseApproverViewBean, Connection conn)
		throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
			ATTRIBUTE_FACILITY_ID + "," +
			ATTRIBUTE_APPLICATION + "," +
			ATTRIBUTE_PERSONNEL_ID + "," +
			ATTRIBUTE_USE_APPROVER + "," +
			ATTRIBUTE_USE_APPROVER_EMAIL + "," +
			ATTRIBUTE_USE_APPROVER_PHONE + "," +
			ATTRIBUTE_APPLICATION_DESC + ")" +
			" values (" +
			SqlHandler.delimitString(overLimitUseApproverViewBean.getFacilityId()) + "," +
			SqlHandler.delimitString(overLimitUseApproverViewBean.getApplication()) + "," +
			overLimitUseApproverViewBean.getPersonnelId() + "," +
			SqlHandler.delimitString(overLimitUseApproverViewBean.getUseApprover()) + "," +
			SqlHandler.delimitString(overLimitUseApproverViewBean.getUseApproverEmail()) + "," +
			SqlHandler.delimitString(overLimitUseApproverViewBean.getUseApproverPhone()) + "," +
			SqlHandler.delimitString(overLimitUseApproverViewBean.getApplicationDesc()) + ")";

		return sqlManager.update(conn, query);
	}


	//update
	public int update(OverLimitUseApproverViewBean overLimitUseApproverViewBean)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(overLimitUseApproverViewBean, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int update(OverLimitUseApproverViewBean overLimitUseApproverViewBean, Connection conn)
		throws BaseException {

		String query  = "update " + TABLE + " set " +
			ATTRIBUTE_FACILITY_ID + "=" +
				SqlHandler.delimitString(overLimitUseApproverViewBean.getFacilityId()) + "," +
			ATTRIBUTE_APPLICATION + "=" +
				SqlHandler.delimitString(overLimitUseApproverViewBean.getApplication()) + "," +
			ATTRIBUTE_PERSONNEL_ID + "=" +
				StringHandler.nullIfZero(overLimitUseApproverViewBean.getPersonnelId()) + "," +
			ATTRIBUTE_USE_APPROVER + "=" +
				SqlHandler.delimitString(overLimitUseApproverViewBean.getUseApprover()) + "," +
			ATTRIBUTE_USE_APPROVER_EMAIL + "=" +
				SqlHandler.delimitString(overLimitUseApproverViewBean.getUseApproverEmail()) + "," +
			ATTRIBUTE_USE_APPROVER_PHONE + "=" +
				SqlHandler.delimitString(overLimitUseApproverViewBean.getUseApproverPhone()) + "," +
			ATTRIBUTE_APPLICATION_DESC + "=" +
				SqlHandler.delimitString(overLimitUseApproverViewBean.getApplicationDesc()) + " " +
			"where " + ATTRIBUTE_FACILITY_ID + "=" +
				overLimitUseApproverViewBean.getFacilityId();

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

		Collection overLimitUseApproverViewBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			OverLimitUseApproverViewBean overLimitUseApproverViewBean = new OverLimitUseApproverViewBean();
			load(dataSetRow, overLimitUseApproverViewBean);
			overLimitUseApproverViewBeanColl.add(overLimitUseApproverViewBean);
		}

		return overLimitUseApproverViewBeanColl;
	}
}