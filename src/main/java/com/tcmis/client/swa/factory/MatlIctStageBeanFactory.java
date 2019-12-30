package com.tcmis.client.swa.factory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import java.lang.reflect.Method;

import java.sql.Connection;

import java.text.ParseException;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DataBeanCreationException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.util.*;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.client.swa.beans.MatlIctStageBean;

/******************************************************************************
 * CLASSNAME: MatlIctStageBeanFactory <br>
 * @version: 1.0, Apr 30, 2007 <br>
 *****************************************************************************/

public class MatlIctStageBeanFactory extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_ISSUE_DOCUMENT_NUMBER = "ISSUE_DOCUMENT_NUMBER";
  public String ATTRIBUTE_DOCUMENT_TYPE = "DOCUMENT_TYPE";
  public String ATTRIBUTE_PART_NUMBER = "PART_NUMBER";
  public String ATTRIBUTE_MANF_PART_NUMBER = "MANF_PART_NUMBER";
  public String ATTRIBUTE_NUMBER_REQUESTED = "NUMBER_REQUESTED";
  public String ATTRIBUTE_NUMBER_ISSUED = "NUMBER_ISSUED";
  public String ATTRIBUTE_REQUESTING_EMPLOYEE_ID = "REQUESTING_EMPLOYEE_ID";
  public String ATTRIBUTE_STATUS = "STATUS";
  public String ATTRIBUTE_ISSUE_CREDIT_TRANSFER_DATE = "ISSUE_CREDIT_TRANSFER_DATE";
  public String ATTRIBUTE_ACN = "ACN";
  public String ATTRIBUTE_SHIPPING_STATION = "SHIPPING_STATION";
  public String ATTRIBUTE_SHIPPING_DEPARTMENT = "SHIPPING_DEPARTMENT";
  public String ATTRIBUTE_SHIPMENT_DATE = "SHIPMENT_DATE";
  public String ATTRIBUTE_RECEIVING_STATION = "RECEIVING_STATION";
  public String ATTRIBUTE_RECEIVING_DEPARTMENT = "RECEIVING_DEPARTMENT";
  public String ATTRIBUTE_FILE_NAME = "FILE_NAME";
  public String ATTRIBUTE_CUMULATIVE_RECEIVED = "CUMULATIVE_RECEIVED";

  //table name
  public String TABLE = "MATL_ICT_STAGE";

  //constructor
  public MatlIctStageBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("issueDocumentNumber")) {
      return ATTRIBUTE_ISSUE_DOCUMENT_NUMBER;
    } else if (attributeName.equals("documentType")) {
      return ATTRIBUTE_DOCUMENT_TYPE;
    } else if (attributeName.equals("partNumber")) {
      return ATTRIBUTE_PART_NUMBER;
    } else if (attributeName.equals("manfPartNumber")) {
      return ATTRIBUTE_MANF_PART_NUMBER;
    } else if (attributeName.equals("numberRequested")) {
      return ATTRIBUTE_NUMBER_REQUESTED;
    } else if (attributeName.equals("numberIssued")) {
      return ATTRIBUTE_NUMBER_ISSUED;
    } else if (attributeName.equals("requestingEmployeeId")) {
      return ATTRIBUTE_REQUESTING_EMPLOYEE_ID;
    } else if (attributeName.equals("status")) {
      return ATTRIBUTE_STATUS;
    } else if (attributeName.equals("issueCreditTransferDate")) {
      return ATTRIBUTE_ISSUE_CREDIT_TRANSFER_DATE;
    } else if (attributeName.equals("acn")) {
      return ATTRIBUTE_ACN;
    } else if (attributeName.equals("shippingStation")) {
      return ATTRIBUTE_SHIPPING_STATION;
    } else if (attributeName.equals("shippingDepartment")) {
      return ATTRIBUTE_SHIPPING_DEPARTMENT;
    } else if (attributeName.equals("shipmentDate")) {
      return ATTRIBUTE_SHIPMENT_DATE;
    } else if (attributeName.equals("receivingStation")) {
      return ATTRIBUTE_RECEIVING_STATION;
    } else if (attributeName.equals("receivingDepartment")) {
      return ATTRIBUTE_RECEIVING_DEPARTMENT;
    } else if (attributeName.equals("fileName")) {
      return ATTRIBUTE_FILE_NAME;
    } else if (attributeName.equals("cumulativeReceived")) {
      return ATTRIBUTE_CUMULATIVE_RECEIVED;
    } else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, MatlIctStageBean.class);
  }

  public int delete(SearchCriteria criteria) throws BaseException {

    Connection connection = null;
    int result = 0;
    try {
      connection = getDbManager().getConnection();
      result = delete(criteria, connection);
    } finally {
      this.getDbManager().returnConnection(connection);
    }
    return result;
  }

  public int delete(SearchCriteria criteria, Connection conn) throws BaseException {

    String sqlQuery = " delete from " + TABLE + " " + getWhereClause(criteria);

    return new SqlManager().update(conn, sqlQuery);
  }

  //insert
  public int insert(MatlIctStageBean matlIctStageBean) throws BaseException {

    Connection connection = null;
    int result = 0;
    try {
      connection = getDbManager().getConnection();
      result = insert(matlIctStageBean, connection);
    } finally {
      this.getDbManager().returnConnection(connection);
    }
    return result;
  }

  public int insert(MatlIctStageBean matlIctStageBean, Connection conn) throws BaseException {

    SqlManager sqlManager = new SqlManager();

    String query = "insert into " + TABLE + " (" + ATTRIBUTE_ISSUE_DOCUMENT_NUMBER + "," + 
    											   ATTRIBUTE_DOCUMENT_TYPE + "," + 
    											   ATTRIBUTE_PART_NUMBER + "," + 
    											   ATTRIBUTE_MANF_PART_NUMBER + "," + 
    											   ATTRIBUTE_NUMBER_REQUESTED + "," + 
    											   ATTRIBUTE_NUMBER_ISSUED + "," +
    											   ATTRIBUTE_REQUESTING_EMPLOYEE_ID + "," + 
    											   ATTRIBUTE_STATUS + "," + 
    											   ATTRIBUTE_ISSUE_CREDIT_TRANSFER_DATE + "," + 
    											   ATTRIBUTE_ACN + "," + 
    											   ATTRIBUTE_SHIPPING_STATION + "," + 
    											   ATTRIBUTE_SHIPPING_DEPARTMENT + "," + 
    											   ATTRIBUTE_SHIPMENT_DATE + "," +
    											   ATTRIBUTE_RECEIVING_STATION + "," + 
    											   ATTRIBUTE_RECEIVING_DEPARTMENT + "," + 
    											   ATTRIBUTE_FILE_NAME + ","+ 
    											   ATTRIBUTE_CUMULATIVE_RECEIVED + 
    										  ") " + 
    				"values (" + matlIctStageBean.getIssueDocumentNumber() + "," + 
    						     SqlHandler.delimitString(matlIctStageBean.getDocumentType()) + "," +
    						     SqlHandler.delimitString(matlIctStageBean.getPartNumber()) + "," + 
    						     SqlHandler.delimitString(matlIctStageBean.getManfPartNumber()) + "," + 
    						     matlIctStageBean.getNumberRequested() + "," + 
    						     matlIctStageBean.getNumberIssued() + "," + 
    						     matlIctStageBean.getRequestingEmployeeId() + "," + 
    						     matlIctStageBean.getStatus() + "," +
    						     DateHandler.getOracleToDateFunction(matlIctStageBean.getIssueCreditTransferDate()) + "," + 
    						     SqlHandler.delimitString(matlIctStageBean.getAcn()) + "," + 
    						     SqlHandler.delimitString(matlIctStageBean.getShippingStation()) + "," +
    						     SqlHandler.delimitString(matlIctStageBean.getShippingDepartment()) + "," + 
    						     DateHandler.getOracleToDateFunction(matlIctStageBean.getShipmentDate()) + "," + 
    						     SqlHandler.delimitString(matlIctStageBean.getReceivingStation()) + "," +
    						     SqlHandler.delimitString(matlIctStageBean.getReceivingDepartment()) + "," + 
    						     SqlHandler.delimitString(matlIctStageBean.getFileName()) + "," + 
    						     matlIctStageBean.getCumulativeReceived()+ 
    						")";

    return sqlManager.update(conn, query);
  }

  //select
  public Collection select(SearchCriteria criteria) throws BaseException {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = select(criteria, connection);
    } finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection select(SearchCriteria criteria, Connection conn) throws BaseException {

    Collection matlIctStageBeanColl = new Vector();

    String query = "select * from " + TABLE + " " + getWhereClause(criteria);

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      MatlIctStageBean matlIctStageBean = new MatlIctStageBean();
      load(dataSetRow, matlIctStageBean);
      matlIctStageBeanColl.add(matlIctStageBean);
    }

    return matlIctStageBeanColl;
  }

  /*****************************************************************************
   * Calls named procedure. Note that date
   * arguments should be passed as <code>java.sql.Timestamp</code>
   *
   * @param procName name of the procedure to call
   *        inParameters arguments to call the procedure with
   * @throws BaseException If there are problems calling the procedure
   ****************************************************************************/
  public void doProcedure(String procedure, Collection inParameters) throws BaseException {
	  Connection connection = null;
	  try {
		  connection = getDbManager().getConnection();
		  this.doProcedure(connection, procedure, inParameters);
	  } finally {
		  try {
			  getDbManager().returnConnection(connection);
		  } catch (Exception e) {
			  //ignore
		  }
	  }
  }
  
  /*****************************************************************************
   * Calls named procedure. Note that date
   * arguments should be passed as <code>java.sql.Timestamp</code>
   *
   * @param procName name of the procedure to call
   *        inParameters arguments to call the procedure with
   *        outParameters output arguments to call the procedure with
   * @returns results from procedure if any
   * @throws BaseException If there are problems calling the procedure
   ****************************************************************************/
  public Collection doProcedure(String procedure, Collection inParameters, Collection outParameters) throws BaseException {
	  Connection connection = null;
	  try {
		  connection = getDbManager().getConnection();
		  return this.doProcedure(connection, procedure, inParameters, outParameters);
	  } finally {
		  try {
			  getDbManager().returnConnection(connection);
		  } catch (Exception e) {
			  //ignore
		  }
	  }
  }

  /*****************************************************************************
   * Calls named procedure. Note that date
   * arguments should be passed as <code>java.sql.Timestamp</code>
   *
   * @param connection database connection to use
   *        procName name of the procedure to call
   *        inParameters arguments to call the procedure with
   * @throws BaseException If there are problems calling the procedure
   ****************************************************************************/
  public void doProcedure(Connection connection, String procedure, Collection inParameters) throws BaseException {
    new SqlManager().doProcedure(connection, procedure, inParameters);
  }
  
  /*****************************************************************************
   * Calls named procedure. Note that date
   * arguments should be passed as <code>java.sql.Timestamp</code>
   *
   * @param connection database connection to use
   *        procName name of the procedure to call
   *        inParameters arguments to call the procedure with
   *        outParameters output arguments to call the procedure with
   * @returns results from procedure if any
   * @throws BaseException If there are problems calling the procedure
   ****************************************************************************/
  public Collection doProcedure(Connection connection, String procedure, Collection inParameters, Collection outParameters) throws BaseException {
    return new SqlManager().doProcedure(connection, procedure, inParameters, outParameters);
  }

}