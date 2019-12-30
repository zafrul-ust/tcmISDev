package com.tcmis.client.catalog.factory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.catalog.beans.UseApprovalDetailViewBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;

/******************************************************************************
 * CLASSNAME: UseApprovalDetailViewBeanFactory <br>
 * @version: 1.0, Jul 19, 2005 <br>
 *****************************************************************************/

public class UseApprovalDetailViewBeanFactory
 extends BaseBeanFactory {

 Log log = LogFactory.getLog(this.getClass());

 //column names
 public String ATTRIBUTE_FAC_PART_NO = "FAC_PART_NO";
 public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
 public String ATTRIBUTE_PART_GROUP_NO = "PART_GROUP_NO";
 public String ATTRIBUTE_USER_GROUP_ID = "USER_GROUP_ID";
 public String ATTRIBUTE_USER_GROUP_MEMBERS = "USER_GROUP_MEMBERS";
 public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
 public String ATTRIBUTE_FACILITY_DESC = "FACILITY_DESC";
 public String ATTRIBUTE_APPLICATION = "APPLICATION";
 public String ATTRIBUTE_APPLICATION_DESC = "APPLICATION_DESC";
 public String ATTRIBUTE_APPLICATION_APPROVED = "APPLICATION_APPROVED";
 public String ATTRIBUTE_LIMIT1 = "LIMIT1";
 public String ATTRIBUTE_LIMIT2 = "LIMIT2";
 public String ATTRIBUTE_APPROVAL_STATUS = "APPROVAL_STATUS";
 public String ATTRIBUTE_APPLICATION_DESC_DISPLAY = "APPLICATION_DESC_DISPLAY";

 //table name
 public String TABLE = "USE_APPROVAL_DETAIL_VIEW";

 //constructor
 public UseApprovalDetailViewBeanFactory(DbManager dbManager) {
	super(dbManager);
 }

 //get column names
 public String getColumnName(String attributeName) {
	if (attributeName.equals("facPartNo")) {
	 return ATTRIBUTE_FAC_PART_NO;
	}
	else if (attributeName.equals("catalogId")) {
	 return ATTRIBUTE_CATALOG_ID;
	}
	else if (attributeName.equals("partGroupNo")) {
	 return ATTRIBUTE_PART_GROUP_NO;
	}
	else if (attributeName.equals("userGroupId")) {
	 return ATTRIBUTE_USER_GROUP_ID;
	}
	else if (attributeName.equals("userGroupMembers")) {
	 return ATTRIBUTE_USER_GROUP_MEMBERS;
	}
	else if (attributeName.equals("facilityId")) {
	 return ATTRIBUTE_FACILITY_ID;
	}
	else if (attributeName.equals("facilityDesc")) {
	 return ATTRIBUTE_FACILITY_DESC;
	}
	else if (attributeName.equals("application")) {
	 return ATTRIBUTE_APPLICATION;
	}
	else if (attributeName.equals("applicationDesc")) {
	 return ATTRIBUTE_APPLICATION_DESC;
	}
	else if (attributeName.equals("applicationApproved")) {
	 return ATTRIBUTE_APPLICATION_APPROVED;
	}
	else if (attributeName.equals("limit1")) {
	 return ATTRIBUTE_LIMIT1;
	}
	else if (attributeName.equals("limit2")) {
	 return ATTRIBUTE_LIMIT2;
	}
	else if (attributeName.equals("approvalStatus")) {
	 return ATTRIBUTE_APPROVAL_STATUS;
	}
	else if (attributeName.equals("applicationDescDisplay")) {
	 return ATTRIBUTE_APPLICATION_DESC_DISPLAY;
	}
	else {
	 return super.getColumnName(attributeName);
	}
 }

 //get column types
 public int getType(String attributeName) {
	return super.getType(attributeName, UseApprovalDetailViewBean.class);
 }

//you need to verify the primary key(s) before uncommenting this
 /*
//delete
	public int delete(UseApprovalDetailViewBean useApprovalDetailViewBean)
	 throws BaseException {
	 SearchCriteria criteria = new SearchCriteria("facPartNo", "SearchCriterion.EQUALS",
		"" + useApprovalDetailViewBean.getFacPartNo());
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
	public int delete(UseApprovalDetailViewBean useApprovalDetailViewBean, Connection conn)
	 throws BaseException {
	 SearchCriteria criteria = new SearchCriteria("facPartNo", "SearchCriterion.EQUALS",
		"" + useApprovalDetailViewBean.getFacPartNo());
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
	public int insert(UseApprovalDetailViewBean useApprovalDetailViewBean)
	 throws BaseException {
	 Connection connection = null;
	 int result = 0;
	 try {
		connection = getDbManager().getConnection();
		result = insert(useApprovalDetailViewBean, connection);
	 }
	 finally {
		this.getDbManager().returnConnection(connection);
	 }
	 return result;
	}
	public int insert(UseApprovalDetailViewBean useApprovalDetailViewBean, Connection conn)
	 throws BaseException {
	 SqlManager sqlManager = new SqlManager();
	 String query  =
		"insert into " + TABLE + " (" +
		ATTRIBUTE_FAC_PART_NO + "," +
		ATTRIBUTE_CATALOG_ID + "," +
		ATTRIBUTE_PART_GROUP_NO + "," +
		ATTRIBUTE_USER_GROUP_ID + "," +
		ATTRIBUTE_USER_GROUP_MEMBERS + "," +
		ATTRIBUTE_FACILITY_ID + "," +
		ATTRIBUTE_FACILITY_DESC + "," +
		ATTRIBUTE_APPLICATION + "," +
		ATTRIBUTE_APPLICATION_DESC + "," +
		ATTRIBUTE_APPLICATION_APPROVED + "," +
		ATTRIBUTE_LIMIT1 + "," +
		ATTRIBUTE_LIMIT2 + "," +
		ATTRIBUTE_APPROVAL_STATUS + "," +
		ATTRIBUTE_APPLICATION_DESC_DISPLAY + ")" +
		" values (" +
		SqlHandler.delimitString(useApprovalDetailViewBean.getFacPartNo()) + "," +
		SqlHandler.delimitString(useApprovalDetailViewBean.getCatalogId()) + "," +
		useApprovalDetailViewBean.getPartGroupNo() + "," +
	 SqlHandler.delimitString(useApprovalDetailViewBean.getUserGroupId()) + "," +
		SqlHandler.delimitString(useApprovalDetailViewBean.getUserGroupMembers()) + "," +
		SqlHandler.delimitString(useApprovalDetailViewBean.getFacilityId()) + "," +
	 SqlHandler.delimitString(useApprovalDetailViewBean.getFacilityDesc()) + "," +
	 SqlHandler.delimitString(useApprovalDetailViewBean.getApplication()) + "," +
	 SqlHandler.delimitString(useApprovalDetailViewBean.getApplicationDesc()) + "," +
		SqlHandler.delimitString(useApprovalDetailViewBean.getApplicationApproved()) + "," +
		SqlHandler.delimitString(useApprovalDetailViewBean.getLimit1()) + "," +
		SqlHandler.delimitString(useApprovalDetailViewBean.getLimit2()) + "," +
	 SqlHandler.delimitString(useApprovalDetailViewBean.getApprovalStatus()) + "," +
		SqlHandler.delimitString(useApprovalDetailViewBean.getApplicationDescDisplay()) + ")";
	 return sqlManager.update(conn, query);
	}
//update
	public int update(UseApprovalDetailViewBean useApprovalDetailViewBean)
	 throws BaseException {
	 Connection connection = null;
	 int result = 0;
	 try {
		connection = getDbManager().getConnection();
		result = update(useApprovalDetailViewBean, connection);
	 }
	 finally {
		this.getDbManager().returnConnection(connection);
	 }
	 return result;
	}
	public int update(UseApprovalDetailViewBean useApprovalDetailViewBean, Connection conn)
	 throws BaseException {
	 String query  = "update " + TABLE + " set " +
		ATTRIBUTE_FAC_PART_NO + "=" +
		 SqlHandler.delimitString(useApprovalDetailViewBean.getFacPartNo()) + "," +
		ATTRIBUTE_CATALOG_ID + "=" +
		 SqlHandler.delimitString(useApprovalDetailViewBean.getCatalogId()) + "," +
		ATTRIBUTE_PART_GROUP_NO + "=" +
	 StringHandler.nullIfZero(useApprovalDetailViewBean.getPartGroupNo()) + "," +
		ATTRIBUTE_USER_GROUP_ID + "=" +
	 SqlHandler.delimitString(useApprovalDetailViewBean.getUserGroupId()) + "," +
		ATTRIBUTE_USER_GROUP_MEMBERS + "=" +
		 SqlHandler.delimitString(useApprovalDetailViewBean.getUserGroupMembers()) + "," +
		ATTRIBUTE_FACILITY_ID + "=" +
	 SqlHandler.delimitString(useApprovalDetailViewBean.getFacilityId()) + "," +
		ATTRIBUTE_FACILITY_DESC + "=" +
	 SqlHandler.delimitString(useApprovalDetailViewBean.getFacilityDesc()) + "," +
		ATTRIBUTE_APPLICATION + "=" +
	 SqlHandler.delimitString(useApprovalDetailViewBean.getApplication()) + "," +
		ATTRIBUTE_APPLICATION_DESC + "=" +
	 SqlHandler.delimitString(useApprovalDetailViewBean.getApplicationDesc()) + "," +
		ATTRIBUTE_APPLICATION_APPROVED + "=" +
		 SqlHandler.delimitString(useApprovalDetailViewBean.getApplicationApproved()) + "," +
		ATTRIBUTE_LIMIT1 + "=" +
		 SqlHandler.delimitString(useApprovalDetailViewBean.getLimit1()) + "," +
		ATTRIBUTE_LIMIT2 + "=" +
		 SqlHandler.delimitString(useApprovalDetailViewBean.getLimit2()) + "," +
		ATTRIBUTE_APPROVAL_STATUS + "=" +
	 SqlHandler.delimitString(useApprovalDetailViewBean.getApprovalStatus()) + "," +
		ATTRIBUTE_APPLICATION_DESC_DISPLAY + "=" +
		 SqlHandler.delimitString(useApprovalDetailViewBean.getApplicationDescDisplay()) + " " +
		"where " + ATTRIBUTE_FAC_PART_NO + "=" +
		 useApprovalDetailViewBean.getFacPartNo();
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

	Collection useApprovalDetailViewBeanColl = new Vector();

	String query = "select * from " + TABLE + " " + getWhereClause(criteria);

	DataSet dataSet = new SqlManager().select(conn, query);

	Iterator dataIter = dataSet.iterator();

	while (dataIter.hasNext()) {
	 DataSetRow dataSetRow = (DataSetRow) dataIter.next();
	 UseApprovalDetailViewBean useApprovalDetailViewBean = new
		UseApprovalDetailViewBean();
	 load(dataSetRow, useApprovalDetailViewBean);
	 useApprovalDetailViewBeanColl.add(useApprovalDetailViewBean);
	}

	return useApprovalDetailViewBeanColl;
 }
}