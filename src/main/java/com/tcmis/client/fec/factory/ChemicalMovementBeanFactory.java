package com.tcmis.client.fec.factory;

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
import com.tcmis.client.fec.beans.ChemicalMovementBean;

/******************************************************************************
 * CLASSNAME: ChemicalMovementBeanFactory <br>
 * @version: 1.0, Oct 3, 2006 <br>
 *****************************************************************************/

public class ChemicalMovementBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_ISSUE_IDENTIFIER = "ISSUE_IDENTIFIER";
  public String ATTRIBUTE_PART_NUMBER = "PART_NUMBER";
  public String ATTRIBUTE_QUANTITY_REQUESTED = "QUANTITY_REQUESTED";
  public String ATTRIBUTE_QUANTITY_ISSUED = "QUANTITY_ISSUED";
  public String ATTRIBUTE_ISSUE_UNIT_OF_MEASURE = "ISSUE_UNIT_OF_MEASURE";
  public String ATTRIBUTE_REQUESTING_EMPLOYEE_ID = "REQUESTING_EMPLOYEE_ID";
  public String ATTRIBUTE_ISSUING_FACILITY = "ISSUING_FACILITY";
  public String ATTRIBUTE_RECEIVING_FACILITY = "RECEIVING_FACILITY";
  public String ATTRIBUTE_RECEIVING_WORKAREA = "RECEIVING_WORKAREA";
  public String ATTRIBUTE_RECEIVING_EMPLOYEE_ID = "RECEIVING_EMPLOYEE_ID";
  public String ATTRIBUTE_ISSUE_DATE = "ISSUE_DATE";
  public String ATTRIBUTE_FILENAME = "FILENAME";
  public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
  public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
  public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
  public String ATTRIBUTE_ITEM_QUANTITY = "ITEM_QUANTITY";
  public String ATTRIBUTE_LOAD_DATE = "LOAD_DATE";
  public String ATTRIBUTE_REPORT_DATE = "REPORT_DATE";

  //table name
  public String TABLE = "CHEMICAL_MOVEMENT";

  //constructor
  public ChemicalMovementBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("issueIdentifier")) {
      return ATTRIBUTE_ISSUE_IDENTIFIER;
    }
    else if (attributeName.equals("partNumber")) {
      return ATTRIBUTE_PART_NUMBER;
    }
    else if (attributeName.equals("quantityRequested")) {
      return ATTRIBUTE_QUANTITY_REQUESTED;
    }
    else if (attributeName.equals("quantityIssued")) {
      return ATTRIBUTE_QUANTITY_ISSUED;
    }
    else if (attributeName.equals("issueUnitOfMeasure")) {
      return ATTRIBUTE_ISSUE_UNIT_OF_MEASURE;
    }
    else if (attributeName.equals("requestingEmployeeId")) {
      return ATTRIBUTE_REQUESTING_EMPLOYEE_ID;
    }
    else if (attributeName.equals("issuingFacility")) {
      return ATTRIBUTE_ISSUING_FACILITY;
    }
    else if (attributeName.equals("receivingFacility")) {
      return ATTRIBUTE_RECEIVING_FACILITY;
    }
    else if (attributeName.equals("receivingWorkarea")) {
      return ATTRIBUTE_RECEIVING_WORKAREA;
    }
    else if (attributeName.equals("receivingEmployeeId")) {
      return ATTRIBUTE_RECEIVING_EMPLOYEE_ID;
    }
    else if (attributeName.equals("issueDate")) {
      return ATTRIBUTE_ISSUE_DATE;
    }
    else if (attributeName.equals("filename")) {
      return ATTRIBUTE_FILENAME;
    }
    else if (attributeName.equals("facilityId")) {
      return ATTRIBUTE_FACILITY_ID;
    }
    else if (attributeName.equals("catPartNo")) {
      return ATTRIBUTE_CAT_PART_NO;
    }
    else if (attributeName.equals("itemId")) {
      return ATTRIBUTE_ITEM_ID;
    }
    else if (attributeName.equals("itemQuantity")) {
      return ATTRIBUTE_ITEM_QUANTITY;
    }
    else if (attributeName.equals("loadDate")) {
      return ATTRIBUTE_LOAD_DATE;
    }
    else if (attributeName.equals("reportDate")) {
      return ATTRIBUTE_REPORT_DATE;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, ChemicalMovementBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(ChemicalMovementBean chemicalMovementBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("issueIdentifier", "SearchCriterion.EQUALS",
     "" + chemicalMovementBean.getIssueIdentifier());
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
       public int delete(ChemicalMovementBean chemicalMovementBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("issueIdentifier", "SearchCriterion.EQUALS",
     "" + chemicalMovementBean.getIssueIdentifier());
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

  public int delete(SearchCriteria criteria, Connection conn) throws
      BaseException {
    String sqlQuery = " delete from " + TABLE + " " +
        getWhereClause(criteria);
    return new SqlManager().update(conn, sqlQuery);
  }

//insert
  public int insert(ChemicalMovementBean chemicalMovementBean) throws
      BaseException {
    Connection connection = null;
    int result = 0;
    try {
      connection = getDbManager().getConnection();
      result = insert(chemicalMovementBean, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return result;
  }

  public int insert(ChemicalMovementBean chemicalMovementBean, Connection conn) throws
      BaseException {
    SqlManager sqlManager = new SqlManager();
    String query =
        "insert into " + TABLE + " (" +
        ATTRIBUTE_ISSUE_IDENTIFIER + "," +
        ATTRIBUTE_PART_NUMBER + "," +
        ATTRIBUTE_QUANTITY_REQUESTED + "," +
        ATTRIBUTE_QUANTITY_ISSUED + "," +
        ATTRIBUTE_ISSUE_UNIT_OF_MEASURE + "," +
        ATTRIBUTE_REQUESTING_EMPLOYEE_ID + "," +
        ATTRIBUTE_ISSUING_FACILITY + "," +
        ATTRIBUTE_RECEIVING_FACILITY + "," +
        ATTRIBUTE_RECEIVING_WORKAREA + "," +
        ATTRIBUTE_RECEIVING_EMPLOYEE_ID + "," +
        ATTRIBUTE_ISSUE_DATE + "," +
        ATTRIBUTE_FILENAME + "," +
        ATTRIBUTE_FACILITY_ID + "," +
        ATTRIBUTE_CAT_PART_NO + "," +
        ATTRIBUTE_ITEM_ID + "," +
        ATTRIBUTE_ITEM_QUANTITY + "," +
        ATTRIBUTE_LOAD_DATE + "," +
        ATTRIBUTE_REPORT_DATE + ")" +
        " values (" +
        SqlHandler.delimitString(chemicalMovementBean.getIssueIdentifier()) +
        "," +
        SqlHandler.delimitString(chemicalMovementBean.getPartNumber()) + "," +
        chemicalMovementBean.getQuantityRequested() + "," +
        SqlHandler.delimitString(chemicalMovementBean.getQuantityIssued()) +
        "," +
        SqlHandler.delimitString(chemicalMovementBean.getIssueUnitOfMeasure()) +
        "," +
        SqlHandler.delimitString(chemicalMovementBean.getRequestingEmployeeId()) +
        "," +
        SqlHandler.delimitString(chemicalMovementBean.getIssuingFacility()) +
        "," +
        SqlHandler.delimitString(chemicalMovementBean.getReceivingFacility()) +
        "," +
        SqlHandler.delimitString(chemicalMovementBean.getReceivingWorkarea()) +
        "," +
        SqlHandler.delimitString(chemicalMovementBean.getReceivingEmployeeId()) +
        "," +
        SqlHandler.delimitString(chemicalMovementBean.getIssueDate()) + "," +
        SqlHandler.delimitString(chemicalMovementBean.getFilename()) + "," +
        SqlHandler.delimitString(chemicalMovementBean.getFacilityId()) + "," +
        SqlHandler.delimitString(chemicalMovementBean.getCatPartNo()) + "," +
        chemicalMovementBean.getItemId() + "," +
        chemicalMovementBean.getItemQuantity() + "," +
        DateHandler.getOracleToDateFunction(chemicalMovementBean.getLoadDate()) +
        "," +
        DateHandler.getOracleToDateFunction(chemicalMovementBean.getReportDate()) +
        ")";
    return sqlManager.update(conn, query);
  }

  /*
//update
     public int update(ChemicalMovementBean chemicalMovementBean)
      throws BaseException {
      Connection connection = null;
      int result = 0;
      try {
       connection = getDbManager().getConnection();
       result = update(chemicalMovementBean, connection);
      }
      finally {
       this.getDbManager().returnConnection(connection);
      }
      return result;
     }
       public int update(ChemicalMovementBean chemicalMovementBean, Connection conn)
      throws BaseException {
      String query  = "update " + TABLE + " set " +
       ATTRIBUTE_ISSUE_IDENTIFIER + "=" +
       SqlHandler.delimitString(chemicalMovementBean.getIssueIdentifier()) + "," +
       ATTRIBUTE_PART_NUMBER + "=" +
        SqlHandler.delimitString(chemicalMovementBean.getPartNumber()) + "," +
       ATTRIBUTE_QUANTITY_REQUESTED + "=" +
       StringHandler.nullIfZero(chemicalMovementBean.getQuantityRequested()) + "," +
       ATTRIBUTE_QUANTITY_ISSUED + "=" +
       SqlHandler.delimitString(chemicalMovementBean.getQuantityIssued()) + "," +
       ATTRIBUTE_ISSUE_UNIT_OF_MEASURE + "=" +
       SqlHandler.delimitString(chemicalMovementBean.getIssueUnitOfMeasure()) + "," +
       ATTRIBUTE_REQUESTING_EMPLOYEE_ID + "=" +
       SqlHandler.delimitString(chemicalMovementBean.getRequestingEmployeeId()) + "," +
       ATTRIBUTE_ISSUING_FACILITY + "=" +
       SqlHandler.delimitString(chemicalMovementBean.getIssuingFacility()) + "," +
       ATTRIBUTE_RECEIVING_FACILITY + "=" +
       SqlHandler.delimitString(chemicalMovementBean.getReceivingFacility()) + "," +
       ATTRIBUTE_RECEIVING_WORKAREA + "=" +
       SqlHandler.delimitString(chemicalMovementBean.getReceivingWorkarea()) + "," +
       ATTRIBUTE_RECEIVING_EMPLOYEE_ID + "=" +
       SqlHandler.delimitString(chemicalMovementBean.getReceivingEmployeeId()) + "," +
       ATTRIBUTE_ISSUE_DATE + "=" +
        SqlHandler.delimitString(chemicalMovementBean.getIssueDate()) + "," +
       ATTRIBUTE_FILENAME + "=" +
        SqlHandler.delimitString(chemicalMovementBean.getFilename()) + "," +
       ATTRIBUTE_FACILITY_ID + "=" +
        SqlHandler.delimitString(chemicalMovementBean.getFacilityId()) + "," +
       ATTRIBUTE_CAT_PART_NO + "=" +
        SqlHandler.delimitString(chemicalMovementBean.getCatPartNo()) + "," +
       ATTRIBUTE_ITEM_ID + "=" +
        StringHandler.nullIfZero(chemicalMovementBean.getItemId()) + "," +
       ATTRIBUTE_ITEM_QUANTITY + "=" +
       StringHandler.nullIfZero(chemicalMovementBean.getItemQuantity()) + "," +
       ATTRIBUTE_LOAD_DATE + "=" +
       DateHandler.getOracleToDateFunction(chemicalMovementBean.getLoadDate()) + "," +
       ATTRIBUTE_REPORT_DATE + "=" +
        DateHandler.getOracleToDateFunction(chemicalMovementBean.getReportDate()) + " " +
       "where " + ATTRIBUTE_ISSUE_IDENTIFIER + "=" +
        chemicalMovementBean.getIssueIdentifier();
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

  public Collection select(SearchCriteria criteria, Connection conn) throws
      BaseException {
    Collection chemicalMovementBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      ChemicalMovementBean chemicalMovementBean = new ChemicalMovementBean();
      load(dataSetRow, chemicalMovementBean);
      chemicalMovementBeanColl.add(chemicalMovementBean);
    }
    return chemicalMovementBeanColl;
  }
}