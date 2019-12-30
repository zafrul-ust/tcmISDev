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
import com.tcmis.client.fec.beans.ChemicalOnHandBean;

/******************************************************************************
 * CLASSNAME: ChemicalOnHandBeanFactory <br>
 * @version: 1.0, Oct 3, 2006 <br>
 *****************************************************************************/

public class ChemicalOnHandBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_ON_HAND_DATE = "ON_HAND_DATE";
  public String ATTRIBUTE_PART_NUMBER = "PART_NUMBER";
  public String ATTRIBUTE_FACILITY = "FACILITY";
  public String ATTRIBUTE_QUANTITY_ONHAND = "QUANTITY_ONHAND";
  public String ATTRIBUTE_UOM = "UOM";
  public String ATTRIBUTE_FILENAME = "FILENAME";
  public String ATTRIBUTE_LOAD_DATE = "LOAD_DATE";
  public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
  public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
  public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
  public String ATTRIBUTE_ITEM_QUANTITY = "ITEM_QUANTITY";
  public String ATTRIBUTE_REPORT_DATE = "REPORT_DATE";

  //table name
  public String TABLE = "CHEMICAL_ON_HAND";

  //constructor
  public ChemicalOnHandBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("onHandDate")) {
      return ATTRIBUTE_ON_HAND_DATE;
    }
    else if (attributeName.equals("partNumber")) {
      return ATTRIBUTE_PART_NUMBER;
    }
    else if (attributeName.equals("facility")) {
      return ATTRIBUTE_FACILITY;
    }
    else if (attributeName.equals("quantityOnhand")) {
      return ATTRIBUTE_QUANTITY_ONHAND;
    }
    else if (attributeName.equals("uom")) {
      return ATTRIBUTE_UOM;
    }
    else if (attributeName.equals("filename")) {
      return ATTRIBUTE_FILENAME;
    }
    else if (attributeName.equals("loadDate")) {
      return ATTRIBUTE_LOAD_DATE;
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
    else if (attributeName.equals("reportDate")) {
      return ATTRIBUTE_REPORT_DATE;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, ChemicalOnHandBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(ChemicalOnHandBean chemicalOnHandBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("onHandDate", "SearchCriterion.EQUALS",
     "" + chemicalOnHandBean.getOnHandDate());
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
   public int delete(ChemicalOnHandBean chemicalOnHandBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("onHandDate", "SearchCriterion.EQUALS",
     "" + chemicalOnHandBean.getOnHandDate());
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
  public int insert(ChemicalOnHandBean chemicalOnHandBean) throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
      connection = getDbManager().getConnection();
      result = insert(chemicalOnHandBean, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return result;
  }

  public int insert(ChemicalOnHandBean chemicalOnHandBean, Connection conn) throws
      BaseException {
    SqlManager sqlManager = new SqlManager();
    String query =
        "insert into " + TABLE + " (" +
        ATTRIBUTE_ON_HAND_DATE + "," +
        ATTRIBUTE_PART_NUMBER + "," +
        ATTRIBUTE_FACILITY + "," +
        ATTRIBUTE_QUANTITY_ONHAND + "," +
        ATTRIBUTE_UOM + "," +
        ATTRIBUTE_FILENAME + "," +
        ATTRIBUTE_LOAD_DATE + "," +
        ATTRIBUTE_FACILITY_ID + "," +
        ATTRIBUTE_CAT_PART_NO + "," +
        ATTRIBUTE_ITEM_ID + "," +
        ATTRIBUTE_ITEM_QUANTITY + "," +
        ATTRIBUTE_REPORT_DATE + ")" +
        " values (" +
        SqlHandler.delimitString(chemicalOnHandBean.getOnHandDate()) + "," +
        SqlHandler.delimitString(chemicalOnHandBean.getPartNumber()) + "," +
        SqlHandler.delimitString(chemicalOnHandBean.getFacility()) + "," +
        chemicalOnHandBean.getQuantityOnhand() + "," +
        SqlHandler.delimitString(chemicalOnHandBean.getUom()) + "," +
        SqlHandler.delimitString(chemicalOnHandBean.getFilename()) + "," +
        DateHandler.getOracleToDateFunction(chemicalOnHandBean.getLoadDate()) +
        "," +
        SqlHandler.delimitString(chemicalOnHandBean.getFacilityId()) + "," +
        SqlHandler.delimitString(chemicalOnHandBean.getCatPartNo()) + "," +
        chemicalOnHandBean.getItemId() + "," +
        chemicalOnHandBean.getItemQuantity() + "," +
        DateHandler.getOracleToDateFunction(chemicalOnHandBean.getReportDate()) +
        ")";
    return sqlManager.update(conn, query);
  }

  /*
//update
   public int update(ChemicalOnHandBean chemicalOnHandBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(chemicalOnHandBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(ChemicalOnHandBean chemicalOnHandBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_ON_HAND_DATE + "=" +
      SqlHandler.delimitString(chemicalOnHandBean.getOnHandDate()) + "," +
     ATTRIBUTE_PART_NUMBER + "=" +
      SqlHandler.delimitString(chemicalOnHandBean.getPartNumber()) + "," +
     ATTRIBUTE_FACILITY + "=" +
      SqlHandler.delimitString(chemicalOnHandBean.getFacility()) + "," +
     ATTRIBUTE_QUANTITY_ONHAND + "=" +
      StringHandler.nullIfZero(chemicalOnHandBean.getQuantityOnhand()) + "," +
     ATTRIBUTE_UOM + "=" +
      SqlHandler.delimitString(chemicalOnHandBean.getUom()) + "," +
     ATTRIBUTE_FILENAME + "=" +
      SqlHandler.delimitString(chemicalOnHandBean.getFilename()) + "," +
     ATTRIBUTE_LOAD_DATE + "=" +
       DateHandler.getOracleToDateFunction(chemicalOnHandBean.getLoadDate()) + "," +
     ATTRIBUTE_FACILITY_ID + "=" +
      SqlHandler.delimitString(chemicalOnHandBean.getFacilityId()) + "," +
     ATTRIBUTE_CAT_PART_NO + "=" +
      SqlHandler.delimitString(chemicalOnHandBean.getCatPartNo()) + "," +
     ATTRIBUTE_ITEM_ID + "=" +
      StringHandler.nullIfZero(chemicalOnHandBean.getItemId()) + "," +
     ATTRIBUTE_ITEM_QUANTITY + "=" +
      StringHandler.nullIfZero(chemicalOnHandBean.getItemQuantity()) + "," +
     ATTRIBUTE_REPORT_DATE + "=" +
       DateHandler.getOracleToDateFunction(chemicalOnHandBean.getReportDate()) + " " +
     "where " + ATTRIBUTE_ON_HAND_DATE + "=" +
      chemicalOnHandBean.getOnHandDate();
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
    Collection chemicalOnHandBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      ChemicalOnHandBean chemicalOnHandBean = new ChemicalOnHandBean();
      load(dataSetRow, chemicalOnHandBean);
      chemicalOnHandBeanColl.add(chemicalOnHandBean);
    }
    return chemicalOnHandBeanColl;
  }
}