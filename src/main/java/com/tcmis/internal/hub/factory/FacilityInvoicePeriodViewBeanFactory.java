package com.tcmis.internal.hub.factory;

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
import com.tcmis.internal.hub.beans.FacilityInvoicePeriodViewBean;
import com.tcmis.internal.hub.beans.MonthlyInventoryDetailInputBean;

/******************************************************************************
 * CLASSNAME: FacilityInvoicePeriodViewBeanFactory <br>
 * @version: 1.0, Feb 3, 2005 <br>
 *****************************************************************************/

public class FacilityInvoicePeriodViewBeanFactory
	extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_INVENTORY_GROUP = "INVENTORY_GROUP";
  public String ATTRIBUTE_HUB = "HUB";
  public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
  public String ATTRIBUTE_INVOICE_GROUP = "INVOICE_GROUP";
  public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
  public String ATTRIBUTE_INVOICE_PERIOD = "INVOICE_PERIOD";
  public String ATTRIBUTE_END_DATE = "END_DATE";
	public String ATTRIBUTE_INVENTORY_GROUP_NAME = "INVENTORY_GROUP_NAME";
	public String ATTRIBUTE_INVENTORY_GROUP_COLLECTION = "INVENTORY_GROUP_COLLECTION";

  //table name
  public String TABLE = "FACILITY_INVOICE_PERIOD_VIEW";

  //constructor
  public FacilityInvoicePeriodViewBeanFactory(DbManager dbManager) {
	super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
	if (attributeName.equals("inventoryGroup")) {
	  return ATTRIBUTE_INVENTORY_GROUP;
	}
	else if (attributeName.equals("hub")) {
	  return ATTRIBUTE_HUB;
	}
	else if (attributeName.equals("companyId")) {
	  return ATTRIBUTE_COMPANY_ID;
	}
	else if (attributeName.equals("invoiceGroup")) {
	  return ATTRIBUTE_INVOICE_GROUP;
	}
	else if (attributeName.equals("facilityId")) {
	  return ATTRIBUTE_FACILITY_ID;
	}
	else if (attributeName.equals("invoicePeriod")) {
	  return ATTRIBUTE_INVOICE_PERIOD;
	}
	else if (attributeName.equals("endDate")) {
	  return ATTRIBUTE_END_DATE;
	}
	else {
	  return super.getColumnName(attributeName);
	}
  }

  //get column types
  public int getType(String attributeName) {
	return super.getType(attributeName, FacilityInvoicePeriodViewBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
	   public int delete(FacilityInvoicePeriodViewBean facilityInvoicePeriodViewBean)
	throws BaseException {
	SearchCriteria criteria = new SearchCriteria("inventoryGroup", "=",
	 "" + facilityInvoicePeriodViewBean.getInventoryGroup());
	Connection connection = this.getDbManager().getConnection();
	int result = this.delete(criteria, connection);
	this.getDbManager().returnConnection(connection);
	return result;
   }
   public int delete(FacilityInvoicePeriodViewBean facilityInvoicePeriodViewBean, Connection conn)
	throws BaseException {
	SearchCriteria criteria = new SearchCriteria("inventoryGroup", "=",
	 "" + facilityInvoicePeriodViewBean.getInventoryGroup());
	return delete(criteria, conn);
   }
   */

  public int delete(SearchCriteria criteria) throws BaseException {

	Connection connection = getDbManager().getConnection();
	int result = delete(criteria, connection);
	this.getDbManager().returnConnection(connection);
	return result;
  }

  public int delete(SearchCriteria criteria, Connection conn) throws
	  BaseException {

	String sqlQuery = " delete from " + TABLE + " " +
		getWhereClause(criteria);

	return new SqlManager().update(conn, sqlQuery);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//insert
	   public int insert(FacilityInvoicePeriodViewBean facilityInvoicePeriodViewBean)
	throws BaseException {
	Connection connection = getDbManager().getConnection();
	int result = insert(criteria, connection);
	this.getDbManager().returnConnection(connection);
	return result;
   }
   public int insert(FacilityInvoicePeriodViewBean facilityInvoicePeriodViewBean, Connection conn)
	throws BaseException {
	SqlManager sqlManager = new SqlManager();
	String query  =
	 "insert into " + TABLE + " (" +
	 ATTRIBUTE_INVENTORY_GROUP + "," +
	 ATTRIBUTE_HUB + "," +
	 ATTRIBUTE_COMPANY_ID + "," +
	 ATTRIBUTE_INVOICE_GROUP + "," +
	 ATTRIBUTE_FACILITY_ID + "," +
	 ATTRIBUTE_INVOICE_PERIOD + "," +
	 ATTRIBUTE_END_DATE + ")" +
	 SqlHandler.delimitString(facilityInvoicePeriodViewBean.getInventoryGroup()) + "," +
	 SqlHandler.delimitString(facilityInvoicePeriodViewBean.getHub()) + "," +
	   SqlHandler.delimitString(facilityInvoicePeriodViewBean.getCompanyId()) + "," +
	 SqlHandler.delimitString(facilityInvoicePeriodViewBean.getInvoiceGroup()) + "," +
	   SqlHandler.delimitString(facilityInvoicePeriodViewBean.getFacilityId()) + "," +
	 StringHandler.nullIfZero(facilityInvoicePeriodViewBean.getInvoicePeriod()) + "," +
	   SqlHandler.delimitString(facilityInvoicePeriodViewBean.getEndDate()) + ")";
	return sqlManager.update(conn, query);
   }
//update
	   public int update(FacilityInvoicePeriodViewBean facilityInvoicePeriodViewBean)
	throws BaseException {
	Connection connection = getDbManager().getConnection();
	int result = update(criteria, connection);
	this.getDbManager().returnConnection(connection);
	return result;
   }
   public int update(FacilityInvoicePeriodViewBean facilityInvoicePeriodViewBean, Connection conn)
	throws BaseException {
	String query  = "update " + TABLE + " set " +
	 ATTRIBUTE_INVENTORY_GROUP + "=" +
	  SqlHandler.delimitString(facilityInvoicePeriodViewBean.getInventoryGroup()) + "," +
	 ATTRIBUTE_HUB + "=" +
	  SqlHandler.delimitString(facilityInvoicePeriodViewBean.getHub()) + "," +
	 ATTRIBUTE_COMPANY_ID + "=" +
	   SqlHandler.delimitString(facilityInvoicePeriodViewBean.getCompanyId()) + "," +
	 ATTRIBUTE_INVOICE_GROUP + "=" +
	  SqlHandler.delimitString(facilityInvoicePeriodViewBean.getInvoiceGroup()) + "," +
	 ATTRIBUTE_FACILITY_ID + "=" +
	   SqlHandler.delimitString(facilityInvoicePeriodViewBean.getFacilityId()) + "," +
	 ATTRIBUTE_INVOICE_PERIOD + "=" +
	  StringHandler.nullIfZero(facilityInvoicePeriodViewBean.getInvoicePeriod()) + "," +
	 ATTRIBUTE_END_DATE + "=" +
	   SqlHandler.delimitString(facilityInvoicePeriodViewBean.getEndDate()) + " " +
	 "where " + ATTRIBUTE_INVENTORY_GROUP + "=" +
	   StringHandler.nullIfZero(facilityInvoicePeriodViewBean.getInventoryGroup());
	return new SqlManager().update(conn, query);
   }
   */

  //select
  public Collection selectForFacilityCustomer(SearchCriteria criteria) throws
	  BaseException {

	Connection connection = null;
	Collection c = null;
	try {
	  connection = this.getDbManager().getConnection();
	  c = selectForFacilityCustomer(criteria, connection);
	}
	finally {
	  this.getDbManager().returnConnection(connection);
	}
	return c;

  }

  public Collection selectForFacilityCustomer(SearchCriteria criteria,
											  Connection conn) throws
	  BaseException {

	Collection facilityInvoicePeriodViewBeanColl = new Vector();
	String sortBy = " order by " + ATTRIBUTE_FACILITY_ID;
	String query = "select distinct FACILITY_ID from " + TABLE + " " +
		getWhereClause(criteria) + sortBy;

	DataSet dataSet = new SqlManager().select(conn, query);

	Iterator dataIter = dataSet.iterator();

	while (dataIter.hasNext()) {
	  DataSetRow dataSetRow = (DataSetRow) dataIter.next();
	  FacilityInvoicePeriodViewBean facilityInvoicePeriodViewBean = new
		  FacilityInvoicePeriodViewBean();
	  load(dataSetRow, facilityInvoicePeriodViewBean);
	  facilityInvoicePeriodViewBeanColl.add(facilityInvoicePeriodViewBean);
	}

	return facilityInvoicePeriodViewBeanColl;
  }

  /**
   * @param criteria
   * @return
   * @throws BaseException
   * This method does the selection of the invoice periods based on the inventory group. This is used in the tcm_ops version of the page
   */
  //select
	public Collection selectForHub(SearchCriteria criteria,MonthlyInventoryDetailInputBean
	 bean, boolean iscollection) throws BaseException {

	Connection connection = null;
	Collection c = null;
	try {
	  connection = this.getDbManager().getConnection();
	  c = selectForHub(criteria,bean,iscollection,connection);
	}
	finally {
	  this.getDbManager().returnConnection(connection);
	}
	return c;

  }

	public Collection selectForHub(SearchCriteria criteria,MonthlyInventoryDetailInputBean
	 bean, boolean iscollection, Connection conn) throws
	  BaseException {

	Collection facilityInvoicePeriodViewBeanColl = new Vector();
	String sortBy = " order by " + ATTRIBUTE_END_DATE + " desc ";

	String whereClause = getWhereClause(criteria);
	if (iscollection) {
	 if (whereClause.trim().length() > 0) {
		whereClause += "and ";
	 }
	 else
	 {
		whereClause += "where ";
	 }


	 whereClause += " " + ATTRIBUTE_INVENTORY_GROUP + " in (select " +
		ATTRIBUTE_INVENTORY_GROUP + " from " + ATTRIBUTE_INVENTORY_GROUP_COLLECTION +
		" where " + ATTRIBUTE_HUB + " = '" + bean.getHub() + "' and " +
		ATTRIBUTE_INVENTORY_GROUP_COLLECTION + " = '" + bean.getInventoryGroup() +
		"') ";
	}

	String query = "select distinct INVOICE_PERIOD, END_DATE from " + TABLE +
		" " +	whereClause + sortBy;

	DataSet dataSet = new SqlManager().select(conn, query);

	Iterator dataIter = dataSet.iterator();

	while (dataIter.hasNext()) {
	  DataSetRow dataSetRow = (DataSetRow) dataIter.next();
	  FacilityInvoicePeriodViewBean facilityInvoicePeriodViewBean = new
		  FacilityInvoicePeriodViewBean();
	  load(dataSetRow, facilityInvoicePeriodViewBean);
	  facilityInvoicePeriodViewBeanColl.add(facilityInvoicePeriodViewBean);
	}

	return facilityInvoicePeriodViewBeanColl;
  }

	//select
	public Collection selectForCustomer(SearchCriteria criteria,
	 MonthlyInventoryDetailInputBean
	 bean, boolean iscollection) throws
	  BaseException {

	Connection connection = null;
	Collection c = null;
	try {
	  connection = this.getDbManager().getConnection();
	  c = selectForCustomer(criteria,bean,iscollection,connection);
	}
	finally {
	  this.getDbManager().returnConnection(connection);
	}
	return c;

  }

	public Collection selectForCustomer(SearchCriteria criteria,
	 MonthlyInventoryDetailInputBean
	 bean, boolean iscollection, Connection conn) throws
	  BaseException {

	Collection facilityInvoicePeriodViewBeanColl = new Vector();
	String sortBy = " order by " + ATTRIBUTE_END_DATE + " desc ";

	String whereClause = getWhereClause(criteria);
	if (iscollection) {
	 if (whereClause.trim().length() > 0) {
		whereClause += "and ";
	 }
	 else
	 {
		whereClause += "where ";
	 }

	 whereClause += " " + ATTRIBUTE_INVENTORY_GROUP + " in (select " +
		ATTRIBUTE_INVENTORY_GROUP + " from " + ATTRIBUTE_INVENTORY_GROUP_COLLECTION +
		" where " + ATTRIBUTE_HUB + " = '" + bean.getHub() + "' and " +
		ATTRIBUTE_INVENTORY_GROUP_COLLECTION + " = '" + bean.getInventoryGroup() +
		"') ";
	}


	String query =
		"select distinct INVOICE_PERIOD, END_DATE,INVENTORY_GROUP from " +
		TABLE + " " +	whereClause + sortBy;

	DataSet dataSet = new SqlManager().select(conn, query);

	Iterator dataIter = dataSet.iterator();

	while (dataIter.hasNext()) {
	  DataSetRow dataSetRow = (DataSetRow) dataIter.next();
	  FacilityInvoicePeriodViewBean facilityInvoicePeriodViewBean = new
		  FacilityInvoicePeriodViewBean();
	  load(dataSetRow, facilityInvoicePeriodViewBean);
	  facilityInvoicePeriodViewBeanColl.add(facilityInvoicePeriodViewBean);
	}

	return facilityInvoicePeriodViewBeanColl;
  }

	//select
	public Collection select(SearchCriteria criteria) throws BaseException {

	Connection connection = null;
	Collection c = null;
	try {
	 connection = this.getDbManager().getConnection();
	 c = select(criteria,connection);
	}
	 finally {
		this.getDbManager().returnConnection(connection);
	 }
	 return c;
 }

	public Collection select(SearchCriteria criteria,Connection conn) throws
	  BaseException {

	Collection facilityInvoicePeriodViewBeanColl = new Vector();

	String query = "select * from " + TABLE + " " +
		getWhereClause(criteria);

	DataSet dataSet = new SqlManager().select(conn, query);

	Iterator dataIter = dataSet.iterator();

	while (dataIter.hasNext()) {
	  DataSetRow dataSetRow = (DataSetRow) dataIter.next();
	  FacilityInvoicePeriodViewBean facilityInvoicePeriodViewBean = new
		  FacilityInvoicePeriodViewBean();
	  load(dataSetRow, facilityInvoicePeriodViewBean);
	  facilityInvoicePeriodViewBeanColl.add(facilityInvoicePeriodViewBean);
	}

	return facilityInvoicePeriodViewBeanColl;
  }
}