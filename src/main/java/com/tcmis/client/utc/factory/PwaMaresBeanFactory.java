package com.tcmis.client.utc.factory;

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
import com.tcmis.client.utc.beans.PwaMaresBean;

/******************************************************************************
 * CLASSNAME: PwaMaresBeanFactory <br>
 * @version: 1.0, May 9, 2005 <br>
 *****************************************************************************/

public class PwaMaresBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_DISPOSITION_CODE = "DISPOSITION_CODE";
  public String ATTRIBUTE_REMARK = "REMARK";
  public String ATTRIBUTE_DISPOSITION_CLOCK = "DISPOSITION_CLOCK";
  public String ATTRIBUTE_SHIPMENT_ID = "SHIPMENT_ID";
  public String ATTRIBUTE_MCL_RECEIVING_NO = "MCL_RECEIVING_NO";
  public String ATTRIBUTE_DISPOSITION_DATE = "DISPOSITION_DATE";
  public String ATTRIBUTE_EXPIRATION_DATE = "EXPIRATION_DATE";
  public String ATTRIBUTE_TCM_LOAD_DATE = "TCM_LOAD_DATE";
  public String ATTRIBUTE_FILE_NAME = "FILE_NAME";
  public String ATTRIBUTE_PROCESSED_STATUS = "PROCESSED_STATUS";
  public String ATTRIBUTE_DUP = "DUP";

  //table name
  public String TABLE = "PWA_MARES";

  //constructor
  public PwaMaresBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("dispositionCode")) {
      return ATTRIBUTE_DISPOSITION_CODE;
    }
    else if (attributeName.equals("remark")) {
      return ATTRIBUTE_REMARK;
    }
    else if (attributeName.equals("dispositionClock")) {
      return ATTRIBUTE_DISPOSITION_CLOCK;
    }
    else if (attributeName.equals("shipmentId")) {
      return ATTRIBUTE_SHIPMENT_ID;
    }
    else if (attributeName.equals("mclReceivingNo")) {
      return ATTRIBUTE_MCL_RECEIVING_NO;
    }
    else if (attributeName.equals("dispositionDate")) {
      return ATTRIBUTE_DISPOSITION_DATE;
    }
    else if (attributeName.equals("expirationDate")) {
      return ATTRIBUTE_EXPIRATION_DATE;
    }
    else if (attributeName.equals("tcmLoadDate")) {
      return ATTRIBUTE_TCM_LOAD_DATE;
    }
    else if (attributeName.equals("fileName")) {
      return ATTRIBUTE_FILE_NAME;
    }
    else if (attributeName.equals("processedStatus")) {
      return ATTRIBUTE_PROCESSED_STATUS;
    }
    else if (attributeName.equals("dup")) {
      return ATTRIBUTE_DUP;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, PwaMaresBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(PwaMaresBean pwaMaresBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("dispositionCode", "SearchCriterion.EQUALS",
     "" + pwaMaresBean.getDispositionCode());
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
   public int delete(PwaMaresBean pwaMaresBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("dispositionCode", "SearchCriterion.EQUALS",
     "" + pwaMaresBean.getDispositionCode());
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
  public int insert(PwaMaresBean pwaMaresBean) throws BaseException {

    Connection connection = null;
    int result = 0;
    try {
      connection = getDbManager().getConnection();
      result = insert(pwaMaresBean, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return result;
  }

  public int insert(PwaMaresBean pwaMaresBean, Connection conn) throws
      BaseException {

    SqlManager sqlManager = new SqlManager();

    String query =
        "insert into " + TABLE + " (" +
        ATTRIBUTE_DISPOSITION_CODE + "," +
        ATTRIBUTE_REMARK + "," +
        ATTRIBUTE_DISPOSITION_CLOCK + "," +
        ATTRIBUTE_SHIPMENT_ID + "," +
        ATTRIBUTE_MCL_RECEIVING_NO + "," +
        ATTRIBUTE_DISPOSITION_DATE + "," +
        ATTRIBUTE_EXPIRATION_DATE + "," +
        ATTRIBUTE_TCM_LOAD_DATE + "," +
        ATTRIBUTE_FILE_NAME + "," +
        ATTRIBUTE_PROCESSED_STATUS + "," +
        ATTRIBUTE_DUP + ")" +
        " values (" +
        SqlHandler.delimitString(pwaMaresBean.getDispositionCode()) + "," +
        SqlHandler.delimitString(pwaMaresBean.getRemark()) + "," +
        SqlHandler.delimitString(pwaMaresBean.getDispositionClock()) + "," +
        SqlHandler.delimitString(pwaMaresBean.getShipmentId()) + "," +
        SqlHandler.delimitString(pwaMaresBean.getMclReceivingNo()) + "," +
        SqlHandler.delimitString(pwaMaresBean.getDispositionDate()) + "," +
        SqlHandler.delimitString(pwaMaresBean.getExpirationDate()) + "," +
        DateHandler.getOracleToDateFunction(pwaMaresBean.getTcmLoadDate()) +
        "," +
        SqlHandler.delimitString(pwaMaresBean.getFileName()) + "," +
        SqlHandler.delimitString(pwaMaresBean.getProcessedStatus()) + "," +
        SqlHandler.delimitString(pwaMaresBean.getDup()) + ")";

    return sqlManager.update(conn, query);
  }

   //update
   public int updateStatus(PwaMaresBean pwaMaresBean) throws BaseException {
     Connection connection = null;
     int result = 0;
     try {
       connection = getDbManager().getConnection();
       result = updateStatus(pwaMaresBean, connection);
     }finally {
       this.getDbManager().returnConnection(connection);
     }
     return result;
   }

   public int updateStatus(PwaMaresBean pwaMaresBean, Connection conn) throws BaseException {
     String query  = "update " + TABLE + " set " +ATTRIBUTE_PROCESSED_STATUS + "= 'Y' " +
         "where " + ATTRIBUTE_MCL_RECEIVING_NO + "='" +pwaMaresBean.getMclReceivingNo() +"' "+
         "and " + ATTRIBUTE_SHIPMENT_ID + "='" +pwaMaresBean.getShipmentId() + "' "+
         "and " + ATTRIBUTE_DISPOSITION_CODE + "='" +pwaMaresBean.getDispositionCode() +"' "+
         "and " + ATTRIBUTE_PROCESSED_STATUS + " is null and "+ATTRIBUTE_DUP+" = 'N'";
     return new SqlManager().update(conn, query);
   }

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

    Collection pwaMaresBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      PwaMaresBean pwaMaresBean = new PwaMaresBean();
      load(dataSetRow, pwaMaresBean);
      pwaMaresBeanColl.add(pwaMaresBean);
    }

    return pwaMaresBeanColl;
  }

  public Collection selectUnProcessedData() throws BaseException {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = selectUnProcessedData(connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection selectUnProcessedData(Connection conn) throws BaseException {

    Collection pwaMaresBeanColl = new Vector();

    String query = "select * from " + TABLE + " where processed_status is null and dup = 'N'";

    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      PwaMaresBean pwaMaresBean = new PwaMaresBean();
      load(dataSetRow, pwaMaresBean);
      pwaMaresBeanColl.add(pwaMaresBean);
    }

    return pwaMaresBeanColl;
  } //end of method

      /*****************************************************************************
   * Calls named procedure. Note that date
   * arguments should be passed as <code>java.sql.Timestamp</code>
   *
   * @param procName name of the procedure to call
   *        inParameters arguments to call the procedure with
   * @throws BaseException If there are problems calling the procedure
       ****************************************************************************/
  public void doProcedure(String procedure, Collection inParameters) throws
      BaseException {
    Connection connection = null;
    try {
      connection = getDbManager().getConnection();
      this.doProcedure(connection, procedure, inParameters);
    }
    finally {
      try {
        getDbManager().returnConnection(connection);
      }
      catch (Exception e) {
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
  public void doProcedure(Connection connection,
                          String procedure,
                          Collection inParameters) throws BaseException {
    new SqlManager().doProcedure(connection, procedure, inParameters);
  }

} //end of class