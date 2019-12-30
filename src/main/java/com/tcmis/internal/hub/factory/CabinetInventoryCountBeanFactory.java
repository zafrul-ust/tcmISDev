package com.tcmis.internal.hub.factory;

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
import com.tcmis.internal.hub.beans.CabinetInventoryCountBean;

/******************************************************************************
 * CLASSNAME: CabinetInventoryCountBeanFactory <br>
 * @version: 1.0, Oct 23, 2006 <br>
 *****************************************************************************/

public class CabinetInventoryCountBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_BIN_ID = "BIN_ID";
  public String ATTRIBUTE_COUNT_DATETIME = "COUNT_DATETIME";
  public String ATTRIBUTE_RECEIPT_ID = "RECEIPT_ID";
  public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
  public String ATTRIBUTE_PERSONNEL_ID = "PERSONNEL_ID";
  public String ATTRIBUTE_COUNT_QUANTITY = "COUNT_QUANTITY";
  public String ATTRIBUTE_DATE_PROCESSED = "DATE_PROCESSED";

  //table name
  public String TABLE = "CABINET_INVENTORY_COUNT";

  //constructor
  public CabinetInventoryCountBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("binId")) {
      return ATTRIBUTE_BIN_ID;
    }
    else if (attributeName.equals("countDatetime")) {
      return ATTRIBUTE_COUNT_DATETIME;
    }
    else if (attributeName.equals("receiptId")) {
      return ATTRIBUTE_RECEIPT_ID;
    }
    else if (attributeName.equals("companyId")) {
      return ATTRIBUTE_COMPANY_ID;
    }
    else if (attributeName.equals("personnelId")) {
      return ATTRIBUTE_PERSONNEL_ID;
    }
    else if (attributeName.equals("countQuantity")) {
      return ATTRIBUTE_COUNT_QUANTITY;
    }
    else if (attributeName.equals("dateProcessed")) {
      return ATTRIBUTE_DATE_PROCESSED;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, CabinetInventoryCountBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(CabinetInventoryCountBean cabinetInventoryCountBean)
    throws BaseException {
       SearchCriteria criteria = new SearchCriteria("binId", "SearchCriterion.EQUALS",
     "" + cabinetInventoryCountBean.getBinId());
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
   public int delete(CabinetInventoryCountBean cabinetInventoryCountBean, Connection conn)
    throws BaseException {
       SearchCriteria criteria = new SearchCriteria("binId", "SearchCriterion.EQUALS",
     "" + cabinetInventoryCountBean.getBinId());
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
  public int insert(CabinetInventoryCountBean cabinetInventoryCountBean) throws
      BaseException {
    Connection connection = null;
    int result = 0;
    try {
      connection = getDbManager().getConnection();
      result = insert(cabinetInventoryCountBean, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return result;
  }

  public int insert(CabinetInventoryCountBean cabinetInventoryCountBean,
                    Connection conn) throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query =
        "insert into " + TABLE + " (" +
        ATTRIBUTE_BIN_ID + "," +
        ATTRIBUTE_COUNT_DATETIME + "," +
        ATTRIBUTE_RECEIPT_ID + "," +
        ATTRIBUTE_COMPANY_ID + "," +
        ATTRIBUTE_PERSONNEL_ID + "," +
        ATTRIBUTE_COUNT_QUANTITY + "," +
        ATTRIBUTE_DATE_PROCESSED + ")" +
        " values (" +
        cabinetInventoryCountBean.getBinId() + "," +
        DateHandler.getOracleToDateFunction(cabinetInventoryCountBean.
                                            getCountDatetime()) + "," +
        cabinetInventoryCountBean.getReceiptId() + "," +
        SqlHandler.delimitString(cabinetInventoryCountBean.getCompanyId()) +
        "," +
        cabinetInventoryCountBean.getPersonnelId() + "," +
        cabinetInventoryCountBean.getCountQuantity() + "," +
        DateHandler.getOracleToDateFunction(cabinetInventoryCountBean.
                                            getDateProcessed()) + ")";
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    return sqlManager.update(conn, query);
  }

  /*
//update
     public int update(CabinetInventoryCountBean cabinetInventoryCountBean)
      throws BaseException {
      Connection connection = null;
      int result = 0;
      try {
       connection = getDbManager().getConnection();
       result = update(cabinetInventoryCountBean, connection);
      }
      finally {
       this.getDbManager().returnConnection(connection);
      }
      return result;
     }
     public int update(CabinetInventoryCountBean cabinetInventoryCountBean, Connection conn)
      throws BaseException {
      String query  = "update " + TABLE + " set " +
       ATTRIBUTE_BIN_ID + "=" +
        StringHandler.nullIfZero(cabinetInventoryCountBean.getBinId()) + "," +
       ATTRIBUTE_COUNT_DATETIME + "=" +
        DateHandler.getOracleToDateFunction(cabinetInventoryCountBean.getCountDatetime()) + "," +
       ATTRIBUTE_RECEIPT_ID + "=" +
       StringHandler.nullIfZero(cabinetInventoryCountBean.getReceiptId()) + "," +
       ATTRIBUTE_COMPANY_ID + "=" +
       SqlHandler.delimitString(cabinetInventoryCountBean.getCompanyId()) + "," +
       ATTRIBUTE_PERSONNEL_ID + "=" +
       StringHandler.nullIfZero(cabinetInventoryCountBean.getPersonnelId()) + "," +
       ATTRIBUTE_COUNT_QUANTITY + "=" +
       StringHandler.nullIfZero(cabinetInventoryCountBean.getCountQuantity()) + "," +
       ATTRIBUTE_DATE_PROCESSED + "=" +
        DateHandler.getOracleToDateFunction(cabinetInventoryCountBean.getDateProcessed()) + " " +
       "where " + ATTRIBUTE_BIN_ID + "=" +
        cabinetInventoryCountBean.getBinId();
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
    Collection cabinetInventoryCountBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      CabinetInventoryCountBean cabinetInventoryCountBean = new
          CabinetInventoryCountBean();
      load(dataSetRow, cabinetInventoryCountBean);
      cabinetInventoryCountBeanColl.add(cabinetInventoryCountBean);
    }
    return cabinetInventoryCountBeanColl;
  }
}