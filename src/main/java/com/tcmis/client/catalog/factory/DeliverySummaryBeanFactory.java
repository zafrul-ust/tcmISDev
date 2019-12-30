package com.tcmis.client.catalog.factory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.catalog.beans.DeliverySummaryBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;

/******************************************************************************
 * CLASSNAME: TempDeliverySummaryBeanFactory <br>
 * @version: 1.0, May 3, 2005 <br>
 *****************************************************************************/

public class DeliverySummaryBeanFactory
 extends BaseBeanFactory {

 Log log = LogFactory.getLog(this.getClass());

 //column names
 public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
 public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
 public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
 public String ATTRIBUTE_REQUESTED_DATE_TO_DELIVER =
	"REQUESTED_DATE_TO_DELIVER";
 public String ATTRIBUTE_REQUESTED_QTY = "REQUESTED_QTY";
 public String ATTRIBUTE_REF_QUANTITY = "REF_QUANTITY";
 public String ATTRIBUTE_REF_DATE = "REF_DATE";
 public String ATTRIBUTE_STATUS = "STATUS";

 //table name
 public String TABLE = "SCHEDULE_DELIVERY_DETAIL_VIEW";

 //constructor
 public DeliverySummaryBeanFactory(DbManager dbManager) {
	super(dbManager);
 }

 //get column names
 public String getColumnName(String attributeName) {
        if (attributeName.equals("companyId")) {
           return ATTRIBUTE_COMPANY_ID;
        }
        else if (attributeName.equals("prNumber")) {
	 return ATTRIBUTE_PR_NUMBER;
	}
	else if (attributeName.equals("lineItem")) {
	 return ATTRIBUTE_LINE_ITEM;
	}
	else if (attributeName.equals("requestedDateToDeliver")) {
	 return ATTRIBUTE_REQUESTED_DATE_TO_DELIVER;
	}
	else if (attributeName.equals("requestedQty")) {
	 return ATTRIBUTE_REQUESTED_QTY;
	}
	else if (attributeName.equals("refQuantity")) {
	 return ATTRIBUTE_REF_QUANTITY;
	}
	else if (attributeName.equals("refDate")) {
	 return ATTRIBUTE_REF_DATE;
	}
	else if (attributeName.equals("status")) {
	 return ATTRIBUTE_STATUS;
	}
	else {
	 return super.getColumnName(attributeName);
	}
 }

 //get column types
 public int getType(String attributeName) {
	return super.getType(attributeName, DeliverySummaryBean.class);
 }

//you need to verify the primary key(s) before uncommenting this
 /*
//delete
	public int delete(TempDeliverySummaryBean tempDeliverySummaryBean)
	 throws BaseException {
	 SearchCriteria criteria = new SearchCriteria("prNumber", "SearchCriterion.EQUALS",
		"" + tempDeliverySummaryBean.getPrNumber());
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
	public int delete(TempDeliverySummaryBean tempDeliverySummaryBean, Connection conn)
	 throws BaseException {
	 SearchCriteria criteria = new SearchCriteria("prNumber", "SearchCriterion.EQUALS",
		"" + tempDeliverySummaryBean.getPrNumber());
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
	public int insert(TempDeliverySummaryBean tempDeliverySummaryBean)
	 throws BaseException {
	 Connection connection = null;
	 int result = 0;
	 try {
		connection = getDbManager().getConnection();
		result = insert(tempDeliverySummaryBean, connection);
	 }
	 finally {
		this.getDbManager().returnConnection(connection);
	 }
	 return result;
	}
	public int insert(TempDeliverySummaryBean tempDeliverySummaryBean, Connection conn)
	 throws BaseException {
	 SqlManager sqlManager = new SqlManager();
	 String query  =
		"insert into " + TABLE + " (" +
		ATTRIBUTE_PR_NUMBER + "," +
		ATTRIBUTE_LINE_ITEM + "," +
		ATTRIBUTE_REQUESTED_DATE_TO_DELIVER + "," +
		ATTRIBUTE_REQUESTED_QTY + "," +
		ATTRIBUTE_DELIVERED_QTY + "," +
		ATTRIBUTE_DATE_DELIVERED + "," +
		ATTRIBUTE_ISSUE_ID + ")" +
		" values (" +
		tempDeliverySummaryBean.getPrNumber() + "," +
		SqlHandler.delimitString(tempDeliverySummaryBean.getLineItem()) + "," +
		DateHandler.getOracleToDateFunction(tempDeliverySummaryBean.getRequestedDateToDeliver()) + "," +
		tempDeliverySummaryBean.getRequestedQty() + "," +
		tempDeliverySummaryBean.getDeliveredQty() + "," +
		DateHandler.getOracleToDateFunction(tempDeliverySummaryBean.getDateDelivered()) + "," +
		tempDeliverySummaryBean.getIssueId() + ")";
	 return sqlManager.update(conn, query);
	}
//update
	public int update(TempDeliverySummaryBean tempDeliverySummaryBean)
	 throws BaseException {
	 Connection connection = null;
	 int result = 0;
	 try {
		connection = getDbManager().getConnection();
		result = update(tempDeliverySummaryBean, connection);
	 }
	 finally {
		this.getDbManager().returnConnection(connection);
	 }
	 return result;
	}
	public int update(TempDeliverySummaryBean tempDeliverySummaryBean, Connection conn)
	 throws BaseException {
	 String query  = "update " + TABLE + " set " +
		ATTRIBUTE_PR_NUMBER + "=" +
	StringHandler.nullIfZero(tempDeliverySummaryBean.getPrNumber()) + "," +
		ATTRIBUTE_LINE_ITEM + "=" +
	SqlHandler.delimitString(tempDeliverySummaryBean.getLineItem()) + "," +
		ATTRIBUTE_REQUESTED_DATE_TO_DELIVER + "=" +
	DateHandler.getOracleToDateFunction(tempDeliverySummaryBean.getRequestedDateToDeliver()) + "," +
		ATTRIBUTE_REQUESTED_QTY + "=" +
	 StringHandler.nullIfZero(tempDeliverySummaryBean.getRequestedQty()) + "," +
		ATTRIBUTE_DELIVERED_QTY + "=" +
	 StringHandler.nullIfZero(tempDeliverySummaryBean.getDeliveredQty()) + "," +
		ATTRIBUTE_DATE_DELIVERED + "=" +
	DateHandler.getOracleToDateFunction(tempDeliverySummaryBean.getDateDelivered()) + "," +
		ATTRIBUTE_ISSUE_ID + "=" +
	StringHandler.nullIfZero(tempDeliverySummaryBean.getIssueId()) + " " +
		"where " + ATTRIBUTE_PR_NUMBER + "=" +
	tempDeliverySummaryBean.getPrNumber();
	 return new SqlManager().update(conn, query);
	}
	*/

 //select
 public Collection select(DeliverySummaryBean inputBean) throws BaseException,Exception {
	Connection connection = this.getDbManager().getConnection();
	try {
	 connection.setAutoCommit(false);
	}finally {
	}

	Collection cin = new Vector();
	if (inputBean.getPrNumber() != null) {
	 cin.add(inputBean.getPrNumber().toString());
	}
	else {
	 cin.add("");
	}

	if (inputBean.getLineItem() != null) {
	 cin.add(inputBean.getLineItem());
	}
	else {
	 cin.add("");
	}

        if (inputBean.getCompanyId() != null) {
         cin.add(inputBean.getCompanyId());
        }
        else {
         cin.add("");
        }

	Collection cout = new Vector();
	/*cout.add(new Integer(java.sql.Types.VARCHAR));*/Collection result = null;
	SqlManager sqlManager = new SqlManager();
	try {
	 result = sqlManager.doProcedure(connection, "p_schedule_delivery_detail", cin, cout);
	}
	finally {
	}

	Iterator i11 = result.iterator();
	String searchQuery = "";
	while (i11.hasNext()) {
	 searchQuery = (String) i11.next(); ;
	}

	Collection c = select("select COMPANY_ID, PR_NUMBER, LINE_ITEM, REQUESTED_DATE_TO_DELIVER, REQUESTED_QTY, REF_DATE, STATUS, sum(REF_QUANTITY) REF_QUANTITY"+
                              " from SCHEDULE_DELIVERY_DETAIL_VIEW"+
                              " group by COMPANY_ID, PR_NUMBER, LINE_ITEM, REQUESTED_DATE_TO_DELIVER, REQUESTED_QTY, REF_DATE, STATUS"+
                              " order by requested_date_to_deliver",connection);
	try {
         connection.commit();
	 connection.setAutoCommit(true);
	}finally {
	}
	this.getDbManager().returnConnection(connection);
	return c;
 }

 public Collection select(String query, Connection conn) throws BaseException {

	Collection deliverySummaryBeanColl = new Vector();

	/*String query = "select * from " + TABLE + " " +
	 getWhereClause(criteria);*/

	DataSet dataSet = new SqlManager().select(conn, query);

	Iterator dataIter = dataSet.iterator();

	while (dataIter.hasNext()) {
	 DataSetRow dataSetRow = (DataSetRow) dataIter.next();
	 DeliverySummaryBean deliverySummaryBean = new DeliverySummaryBean();
	 load(dataSetRow, deliverySummaryBean);
	 deliverySummaryBeanColl.add(deliverySummaryBean);
	}

	return deliverySummaryBeanColl;
 }
}