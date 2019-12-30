package com.tcmis.client.catalog.factory;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.catalog.beans.ScheduleDeliveryHeaderViewBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;

/******************************************************************************
 * CLASSNAME: ScheduleDeliveryHeaderViewBeanFactory <br>
 * @version: 1.0, May 3, 2005 <br>
 *****************************************************************************/

public class ScheduleDeliveryHeaderViewBeanFactory
 extends BaseBeanFactory {

 Log log = LogFactory.getLog(this.getClass());

 //column names
 public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
 public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
 public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
 public String ATTRIBUTE_REQUESTOR = "REQUESTOR";
 public String ATTRIBUTE_PR_STATUS = "PR_STATUS";
 public String ATTRIBUTE_QUANTITY = "QUANTITY";
 public String ATTRIBUTE_ITEM_TYPE = "ITEM_TYPE";
 public String ATTRIBUTE_FAC_PART_NO = "FAC_PART_NO";
 public String ATTRIBUTE_PART_DESCRIPTION = "PART_DESCRIPTION";
 public String ATTRIBUTE_PACKAGING = "PACKAGING";
 public String ATTRIBUTE_STATUS_DESC = "STATUS_DESC";
 public String ATTRIBUTE_REQUEST_LINE_STATUS = "REQUEST_LINE_STATUS";

 //table name
 public String TABLE = "SCHEDULE_DELIVERY_HEADER_VIEW ";

 //constructor
 public ScheduleDeliveryHeaderViewBeanFactory(DbManager dbManager) {
	super(dbManager);
 }

 //get column names
 public String getColumnName(String attributeName) {
	if (attributeName.equals("companyId")) {
	 return ATTRIBUTE_COMPANY_ID;
        }else if (attributeName.equals("prNumber")) {
         return ATTRIBUTE_PR_NUMBER;
        }
	else if (attributeName.equals("lineItem")) {
	 return ATTRIBUTE_LINE_ITEM;
	}
	else if (attributeName.equals("requestor")) {
	 return ATTRIBUTE_REQUESTOR;
	}
	else if (attributeName.equals("prStatus")) {
	 return ATTRIBUTE_PR_STATUS;
	}
	else if (attributeName.equals("quantity")) {
	 return ATTRIBUTE_QUANTITY;
	}
	else if (attributeName.equals("itemType")) {
	 return ATTRIBUTE_ITEM_TYPE;
	}
	else if (attributeName.equals("facPartNo")) {
	 return ATTRIBUTE_FAC_PART_NO;
	}
	else if (attributeName.equals("partDescription")) {
	 return ATTRIBUTE_PART_DESCRIPTION;
	}
	else if (attributeName.equals("packaging")) {
	 return ATTRIBUTE_PACKAGING;
	}
	else if (attributeName.equals("statusDesc")) {
	 return ATTRIBUTE_STATUS_DESC;
	}
        else if (attributeName.equals("requestLineStatus")) {
         return ATTRIBUTE_REQUEST_LINE_STATUS;
        }
	else {
	 return super.getColumnName(attributeName);
	}
 }

 //get column types
 public int getType(String attributeName) {
	return super.getType(attributeName, ScheduleDeliveryHeaderViewBean.class);
 }

//you need to verify the primary key(s) before uncommenting this
 /*
//delete
	 public int delete(ScheduleDeliveryHeaderViewBean scheduleDeliveryHeaderViewBean)
	 throws BaseException {
	 SearchCriteria criteria = new SearchCriteria("prNumber", "SearchCriterion.EQUALS",
		"" + scheduleDeliveryHeaderViewBean.getPrNumber());
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
	public int delete(ScheduleDeliveryHeaderViewBean scheduleDeliveryHeaderViewBean, Connection conn)
	 throws BaseException {
	 SearchCriteria criteria = new SearchCriteria("prNumber", "SearchCriterion.EQUALS",
		"" + scheduleDeliveryHeaderViewBean.getPrNumber());
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
	 public int insert(ScheduleDeliveryHeaderViewBean scheduleDeliveryHeaderViewBean)
	 throws BaseException {
	 Connection connection = null;
	 int result = 0;
	 try {
		connection = getDbManager().getConnection();
		result = insert(scheduleDeliveryHeaderViewBean, connection);
	 }
	 finally {
		this.getDbManager().returnConnection(connection);
	 }
	 return result;
	}
	public int insert(ScheduleDeliveryHeaderViewBean scheduleDeliveryHeaderViewBean, Connection conn)
	 throws BaseException {
	 SqlManager sqlManager = new SqlManager();
	 String query  =
		"insert into " + TABLE + " (" +
		ATTRIBUTE_PR_NUMBER + "," +
		ATTRIBUTE_LINE_ITEM + "," +
		ATTRIBUTE_REQUESTOR + "," +
		ATTRIBUTE_PR_STATUS + "," +
		ATTRIBUTE_QUANTITY + "," +
		ATTRIBUTE_ITEM_TYPE + "," +
		ATTRIBUTE_FAC_PART_NO + "," +
		ATTRIBUTE_ITEM_ID + "," +
		ATTRIBUTE_ITEM_DESC + "," +
		ATTRIBUTE_STATUS_DESC + ")" +
		" values (" +
		scheduleDeliveryHeaderViewBean.getPrNumber() + "," +
	 SqlHandler.delimitString(scheduleDeliveryHeaderViewBean.getLineItem()) + "," +
		scheduleDeliveryHeaderViewBean.getRequestor() + "," +
	 SqlHandler.delimitString(scheduleDeliveryHeaderViewBean.getPrStatus()) + "," +
		scheduleDeliveryHeaderViewBean.getQuantity() + "," +
	 SqlHandler.delimitString(scheduleDeliveryHeaderViewBean.getItemType()) + "," +
	 SqlHandler.delimitString(scheduleDeliveryHeaderViewBean.getFacPartNo()) + "," +
		scheduleDeliveryHeaderViewBean.getItemId() + "," +
	 SqlHandler.delimitString(scheduleDeliveryHeaderViewBean.getItemDesc()) + "," +
	 SqlHandler.delimitString(scheduleDeliveryHeaderViewBean.getStatusDesc()) + ")";
	 return sqlManager.update(conn, query);
	}
//update
	 public int update(ScheduleDeliveryHeaderViewBean scheduleDeliveryHeaderViewBean)
	 throws BaseException {
	 Connection connection = null;
	 int result = 0;
	 try {
		connection = getDbManager().getConnection();
		result = update(scheduleDeliveryHeaderViewBean, connection);
	 }
	 finally {
		this.getDbManager().returnConnection(connection);
	 }
	 return result;
	}
	public int update(ScheduleDeliveryHeaderViewBean scheduleDeliveryHeaderViewBean, Connection conn)
	 throws BaseException {
	 String query  = "update " + TABLE + " set " +
		ATTRIBUTE_PR_NUMBER + "=" +
	 StringHandler.nullIfZero(scheduleDeliveryHeaderViewBean.getPrNumber()) + "," +
		ATTRIBUTE_LINE_ITEM + "=" +
	 SqlHandler.delimitString(scheduleDeliveryHeaderViewBean.getLineItem()) + "," +
		ATTRIBUTE_REQUESTOR + "=" +
	 StringHandler.nullIfZero(scheduleDeliveryHeaderViewBean.getRequestor()) + "," +
		ATTRIBUTE_PR_STATUS + "=" +
	 SqlHandler.delimitString(scheduleDeliveryHeaderViewBean.getPrStatus()) + "," +
		ATTRIBUTE_QUANTITY + "=" +
	 StringHandler.nullIfZero(scheduleDeliveryHeaderViewBean.getQuantity()) + "," +
		ATTRIBUTE_ITEM_TYPE + "=" +
	 SqlHandler.delimitString(scheduleDeliveryHeaderViewBean.getItemType()) + "," +
		ATTRIBUTE_FAC_PART_NO + "=" +
	 SqlHandler.delimitString(scheduleDeliveryHeaderViewBean.getFacPartNo()) + "," +
		ATTRIBUTE_ITEM_ID + "=" +
	 StringHandler.nullIfZero(scheduleDeliveryHeaderViewBean.getItemId()) + "," +
		ATTRIBUTE_ITEM_DESC + "=" +
	 SqlHandler.delimitString(scheduleDeliveryHeaderViewBean.getItemDesc()) + "," +
		ATTRIBUTE_STATUS_DESC + "=" +
	 SqlHandler.delimitString(scheduleDeliveryHeaderViewBean.getStatusDesc()) + " " +
		"where " + ATTRIBUTE_PR_NUMBER + "=" +
	scheduleDeliveryHeaderViewBean.getPrNumber();
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

	Collection scheduleDeliveryHeaderViewBeanColl = new Vector();

	String query = "select * from " + TABLE + " " + getWhereClause(criteria);

	DataSet dataSet = new SqlManager().select(conn, query);

	Iterator dataIter = dataSet.iterator();

	while (dataIter.hasNext()) {
	 DataSetRow dataSetRow = (DataSetRow) dataIter.next();
	 ScheduleDeliveryHeaderViewBean scheduleDeliveryHeaderViewBean = new
		ScheduleDeliveryHeaderViewBean();
	 load(dataSetRow, scheduleDeliveryHeaderViewBean);
	 scheduleDeliveryHeaderViewBeanColl.add(scheduleDeliveryHeaderViewBean);
	}

	return scheduleDeliveryHeaderViewBeanColl;
 }
}