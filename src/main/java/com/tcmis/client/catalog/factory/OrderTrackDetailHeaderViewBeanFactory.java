package com.tcmis.client.catalog.factory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.catalog.beans.OrderTrackDetailHeaderViewBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;

/******************************************************************************
 * CLASSNAME: OrderTrackDetailHeaderViewBeanFactory <br>
 * @version: 1.0, May 6, 2005 <br>
 *****************************************************************************/

public class OrderTrackDetailHeaderViewBeanFactory
 extends BaseBeanFactory {

 Log log = LogFactory.getLog(this.getClass());

 //column names
 public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
 public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
 public String ATTRIBUTE_REQUESTOR = "REQUESTOR";
 public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
 public String ATTRIBUTE_REQUEST_DATE = "REQUEST_DATE";
 public String ATTRIBUTE_REQUESTED_FINANCE_APPROVER =
	"REQUESTED_FINANCE_APPROVER";
 public String ATTRIBUTE_REQUESTED_RELEASER = "REQUESTED_RELEASER";
 public String ATTRIBUTE_SUBMITTED_DATE = "SUBMITTED_DATE";
 public String ATTRIBUTE_MR_RELEASED_DATE = "MR_RELEASED_DATE";
 public String ATTRIBUTE_FINANCE_APPROVED_DATE = "FINANCE_APPROVED_DATE";
 public String ATTRIBUTE_REQUESTOR_NAME = "REQUESTOR_NAME";
 public String ATTRIBUTE_FINANCE_APPROVER_NAME = "FINANCE_APPROVER_NAME";
 public String ATTRIBUTE_RELEASER_NAME = "RELEASER_NAME";

 //table name
 public String TABLE = "ORDER_TRACK_DETAIL_HEADER_VIEW";

 //constructor
 public OrderTrackDetailHeaderViewBeanFactory(DbManager dbManager) {
	super(dbManager);
 }

 //get column names
 public String getColumnName(String attributeName) {
	if (attributeName.equals("companyId")) {
	 return ATTRIBUTE_COMPANY_ID;
        }else if (attributeName.equals("prNumber")) {
         return ATTRIBUTE_PR_NUMBER;
        }
	else if (attributeName.equals("requestor")) {
	 return ATTRIBUTE_REQUESTOR;
	}
	else if (attributeName.equals("facilityId")) {
	 return ATTRIBUTE_FACILITY_ID;
	}
	else if (attributeName.equals("requestDate")) {
	 return ATTRIBUTE_REQUEST_DATE;
	}
	else if (attributeName.equals("requestedFinanceApprover")) {
	 return ATTRIBUTE_REQUESTED_FINANCE_APPROVER;
	}
	else if (attributeName.equals("requestedReleaser")) {
	 return ATTRIBUTE_REQUESTED_RELEASER;
	}
	else if (attributeName.equals("submittedDate")) {
	 return ATTRIBUTE_SUBMITTED_DATE;
	}
	else if (attributeName.equals("mrReleasedDate")) {
	 return ATTRIBUTE_MR_RELEASED_DATE;
	}
	else if (attributeName.equals("financeApprovedDate")) {
	 return ATTRIBUTE_FINANCE_APPROVED_DATE;
	}
	else if (attributeName.equals("requestorName")) {
	 return ATTRIBUTE_REQUESTOR_NAME;
	}
	else if (attributeName.equals("financeApproverName")) {
	 return ATTRIBUTE_FINANCE_APPROVER_NAME;
	}
	else if (attributeName.equals("releaserName")) {
	 return ATTRIBUTE_RELEASER_NAME;
	}
	else {
	 return super.getColumnName(attributeName);
	}
 }

 //get column types
 public int getType(String attributeName) {
	return super.getType(attributeName, OrderTrackDetailHeaderViewBean.class);
 }

//you need to verify the primary key(s) before uncommenting this
 /*
//delete
	 public int delete(OrderTrackDetailHeaderViewBean orderTrackDetailHeaderViewBean)
	 throws BaseException {
	 SearchCriteria criteria = new SearchCriteria("prNumber", "SearchCriterion.EQUALS",
		"" + orderTrackDetailHeaderViewBean.getPrNumber());
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
	public int delete(OrderTrackDetailHeaderViewBean orderTrackDetailHeaderViewBean, Connection conn)
	 throws BaseException {
	 SearchCriteria criteria = new SearchCriteria("prNumber", "SearchCriterion.EQUALS",
		"" + orderTrackDetailHeaderViewBean.getPrNumber());
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
	 public int insert(OrderTrackDetailHeaderViewBean orderTrackDetailHeaderViewBean)
	 throws BaseException {
	 Connection connection = null;
	 int result = 0;
	 try {
		connection = getDbManager().getConnection();
		result = insert(orderTrackDetailHeaderViewBean, connection);
	 }
	 finally {
		this.getDbManager().returnConnection(connection);
	 }
	 return result;
	}
	public int insert(OrderTrackDetailHeaderViewBean orderTrackDetailHeaderViewBean, Connection conn)
	 throws BaseException {
	 SqlManager sqlManager = new SqlManager();
	 String query  =
		"insert into " + TABLE + " (" +
		ATTRIBUTE_PR_NUMBER + "," +
		ATTRIBUTE_REQUESTOR + "," +
		ATTRIBUTE_FACILITY_ID + "," +
		ATTRIBUTE_REQUEST_DATE + "," +
		ATTRIBUTE_REQUESTED_FINANCE_APPROVER + "," +
		ATTRIBUTE_REQUESTED_RELEASER + "," +
		ATTRIBUTE_SUBMITTED_DATE + "," +
		ATTRIBUTE_MR_RELEASED_DATE + "," +
		ATTRIBUTE_FINANCE_APPROVED_DATE + "," +
		ATTRIBUTE_REQUESTOR_NAME + "," +
		ATTRIBUTE_FINANCE_APPROVER_NAME + "," +
		ATTRIBUTE_RELEASER_NAME + ")" +
		" values (" +
		orderTrackDetailHeaderViewBean.getPrNumber() + "," +
		orderTrackDetailHeaderViewBean.getRequestor() + "," +
	 SqlHandler.delimitString(orderTrackDetailHeaderViewBean.getFacilityId()) + "," +
		DateHandler.getOracleToDateFunction(orderTrackDetailHeaderViewBean.getRequestDate()) + "," +
		orderTrackDetailHeaderViewBean.getRequestedFinanceApprover() + "," +
		orderTrackDetailHeaderViewBean.getRequestedReleaser() + "," +
		DateHandler.getOracleToDateFunction(orderTrackDetailHeaderViewBean.getSubmittedDate()) + "," +
		DateHandler.getOracleToDateFunction(orderTrackDetailHeaderViewBean.getMrReleasedDate()) + "," +
		DateHandler.getOracleToDateFunction(orderTrackDetailHeaderViewBean.getFinanceApprovedDate()) + "," +
		SqlHandler.delimitString(orderTrackDetailHeaderViewBean.getRequestorName()) + "," +
		SqlHandler.delimitString(orderTrackDetailHeaderViewBean.getFinanceApproverName()) + "," +
		SqlHandler.delimitString(orderTrackDetailHeaderViewBean.getReleaserName()) + ")";
	 return sqlManager.update(conn, query);
	}
//update
	 public int update(OrderTrackDetailHeaderViewBean orderTrackDetailHeaderViewBean)
	 throws BaseException {
	 Connection connection = null;
	 int result = 0;
	 try {
		connection = getDbManager().getConnection();
		result = update(orderTrackDetailHeaderViewBean, connection);
	 }
	 finally {
		this.getDbManager().returnConnection(connection);
	 }
	 return result;
	}
	public int update(OrderTrackDetailHeaderViewBean orderTrackDetailHeaderViewBean, Connection conn)
	 throws BaseException {
	 String query  = "update " + TABLE + " set " +
		ATTRIBUTE_PR_NUMBER + "=" +
	 StringHandler.nullIfZero(orderTrackDetailHeaderViewBean.getPrNumber()) + "," +
		ATTRIBUTE_REQUESTOR + "=" +
	 StringHandler.nullIfZero(orderTrackDetailHeaderViewBean.getRequestor()) + "," +
		ATTRIBUTE_FACILITY_ID + "=" +
	 SqlHandler.delimitString(orderTrackDetailHeaderViewBean.getFacilityId()) + "," +
		ATTRIBUTE_REQUEST_DATE + "=" +
		 DateHandler.getOracleToDateFunction(orderTrackDetailHeaderViewBean.getRequestDate()) + "," +
		ATTRIBUTE_REQUESTED_FINANCE_APPROVER + "=" +
		 StringHandler.nullIfZero(orderTrackDetailHeaderViewBean.getRequestedFinanceApprover()) + "," +
		ATTRIBUTE_REQUESTED_RELEASER + "=" +
		 StringHandler.nullIfZero(orderTrackDetailHeaderViewBean.getRequestedReleaser()) + "," +
		ATTRIBUTE_SUBMITTED_DATE + "=" +
		 DateHandler.getOracleToDateFunction(orderTrackDetailHeaderViewBean.getSubmittedDate()) + "," +
		ATTRIBUTE_MR_RELEASED_DATE + "=" +
		 DateHandler.getOracleToDateFunction(orderTrackDetailHeaderViewBean.getMrReleasedDate()) + "," +
		ATTRIBUTE_FINANCE_APPROVED_DATE + "=" +
		 DateHandler.getOracleToDateFunction(orderTrackDetailHeaderViewBean.getFinanceApprovedDate()) + "," +
		ATTRIBUTE_REQUESTOR_NAME + "=" +
		 SqlHandler.delimitString(orderTrackDetailHeaderViewBean.getRequestorName()) + "," +
		ATTRIBUTE_FINANCE_APPROVER_NAME + "=" +
		 SqlHandler.delimitString(orderTrackDetailHeaderViewBean.getFinanceApproverName()) + "," +
		ATTRIBUTE_RELEASER_NAME + "=" +
		 SqlHandler.delimitString(orderTrackDetailHeaderViewBean.getReleaserName()) + " " +
		"where " + ATTRIBUTE_PR_NUMBER + "=" +
		 orderTrackDetailHeaderViewBean.getPrNumber();
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

	Collection orderTrackDetailHeaderViewBeanColl = new Vector();

	String query = "select * from " + TABLE + " " + getWhereClause(criteria);
        log.debug(query);
	DataSet dataSet = new SqlManager().select(conn, query);

	Iterator dataIter = dataSet.iterator();

	while (dataIter.hasNext()) {
	 DataSetRow dataSetRow = (DataSetRow) dataIter.next();
	 OrderTrackDetailHeaderViewBean orderTrackDetailHeaderViewBean = new
		OrderTrackDetailHeaderViewBean();
	 load(dataSetRow, orderTrackDetailHeaderViewBean);
	 orderTrackDetailHeaderViewBeanColl.add(orderTrackDetailHeaderViewBean);
	}

	return orderTrackDetailHeaderViewBeanColl;
 }
}