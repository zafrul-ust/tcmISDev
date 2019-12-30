package com.tcmis.client.catalog.factory;

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
import com.tcmis.client.catalog.beans.DeliveryScheduleChangeBean;
import com.tcmis.common.framework.GenericProcedureFactory;

/******************************************************************************
 * CLASSNAME: DeliveryScheduleChangeBeanFactory <br>
 * @version: 1.0, Jun 21, 2007 <br>
 *****************************************************************************/

public class DeliveryScheduleChangeBeanFactory extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
  public String ATTRIBUTE_PR_NUMBER = "PR_NUMBER";
  public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
  public String ATTRIBUTE_DATE_TO_DELIVER = "DATE_TO_DELIVER";
  public String ATTRIBUTE_ORIGINAL_QTY = "ORIGINAL_QTY";
  public String ATTRIBUTE_REVISED_QTY = "REVISED_QTY";
  public String ATTRIBUTE_UPDATED_DATE = "UPDATED_DATE";
  public String ATTRIBUTE_UPDATED_BY = "UPDATED_BY";
  public String ATTRIBUTE_EXPEDITE_APPROVAL = "EXPEDITE_APPROVAL";
  public String ATTRIBUTE_CSR_APPROVAL = "CSR_APPROVAL";
  public String ATTRIBUTE_EXPEDITE_APPROVAL_DATE = "EXPEDITE_APPROVAL_DATE";
  public String ATTRIBUTE_CSR_APPROVAL_DATE = "CSR_APPROVAL_DATE";

  //table name
  public String TABLE = "DELIVERY_SCHEDULE_CHANGE";

  //constructor
  public DeliveryScheduleChangeBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("companyId")) {
      return ATTRIBUTE_COMPANY_ID;
    } else if (attributeName.equals("prNumber")) {
      return ATTRIBUTE_PR_NUMBER;
    } else if (attributeName.equals("lineItem")) {
      return ATTRIBUTE_LINE_ITEM;
    } else if (attributeName.equals("dateToDeliver")) {
      return ATTRIBUTE_DATE_TO_DELIVER;
    } else if (attributeName.equals("originalQty")) {
      return ATTRIBUTE_ORIGINAL_QTY;
    } else if (attributeName.equals("revisedQty")) {
      return ATTRIBUTE_REVISED_QTY;
    } else if (attributeName.equals("updatedDate")) {
      return ATTRIBUTE_UPDATED_DATE;
    } else if (attributeName.equals("updatedBy")) {
      return ATTRIBUTE_UPDATED_BY;
    } else if (attributeName.equals("expediteApproval")) {
      return ATTRIBUTE_EXPEDITE_APPROVAL;
    } else if (attributeName.equals("csrApproval")) {
      return ATTRIBUTE_CSR_APPROVAL;
    } else if (attributeName.equals("expediteApprovalDate")) {
      return ATTRIBUTE_EXPEDITE_APPROVAL_DATE;
    } else if (attributeName.equals("csrApprovalDate")) {
      return ATTRIBUTE_CSR_APPROVAL_DATE;
    } else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, DeliveryScheduleChangeBean.class);
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
  public int insert(DeliveryScheduleChangeBean deliveryScheduleChangeBean) throws BaseException {

    Connection connection = null;
    int result = 0;
    try {
      connection = getDbManager().getConnection();
      result = insert(deliveryScheduleChangeBean, connection);
    } finally {
      this.getDbManager().returnConnection(connection);
    }
    return result;
  }

  public int insert(DeliveryScheduleChangeBean deliveryScheduleChangeBean, Connection conn) throws BaseException {

    SqlManager sqlManager = new SqlManager();

    String query = "insert into " + TABLE + " (" + ATTRIBUTE_COMPANY_ID + "," + ATTRIBUTE_PR_NUMBER + "," + ATTRIBUTE_LINE_ITEM + "," +
                   ATTRIBUTE_DATE_TO_DELIVER + "," + ATTRIBUTE_ORIGINAL_QTY + "," + ATTRIBUTE_REVISED_QTY + "," +
                   ATTRIBUTE_UPDATED_DATE + "," + ATTRIBUTE_UPDATED_BY + "," + ATTRIBUTE_EXPEDITE_APPROVAL + "," +
                   ATTRIBUTE_CSR_APPROVAL + ")" + " values (" + SqlHandler.delimitString(deliveryScheduleChangeBean.getCompanyId()) + "," +
                   deliveryScheduleChangeBean.getPrNumber() + "," + SqlHandler.delimitString(deliveryScheduleChangeBean.getLineItem()) + "," +
                   DateHandler.getOracleToDateFunction(deliveryScheduleChangeBean.getDateToDeliver()) + "," +
                   deliveryScheduleChangeBean.getOriginalQty() + "," + deliveryScheduleChangeBean.getRevisedQty() + "," +
                   DateHandler.getOracleToDateFunction(deliveryScheduleChangeBean.getUpdatedDate()) + "," + deliveryScheduleChangeBean.getUpdatedBy() + "," +
                   deliveryScheduleChangeBean.getExpediteApproval() + "," +
                   deliveryScheduleChangeBean.getCsrApproval() + ")";

    return sqlManager.update(conn, query);
  }

  //update
  public int update(DeliveryScheduleChangeBean deliveryScheduleChangeBean) throws BaseException {

    Connection connection = null;
    int result = 0;
    try {
      connection = getDbManager().getConnection();
      result = update(deliveryScheduleChangeBean, connection);
    } finally {
      this.getDbManager().returnConnection(connection);
    }
    return result;
  }

  public int update(DeliveryScheduleChangeBean deliveryScheduleChangeBean, Connection conn) throws BaseException {

    String query = "update " + TABLE + " set " + ATTRIBUTE_COMPANY_ID + "=" + SqlHandler.delimitString(deliveryScheduleChangeBean.getCompanyId()) + "," +
                   ATTRIBUTE_PR_NUMBER + "=" + StringHandler.nullIfZero(deliveryScheduleChangeBean.getPrNumber()) +
                   "," + ATTRIBUTE_LINE_ITEM + "=" + SqlHandler.delimitString(deliveryScheduleChangeBean.getLineItem()) + "," +
                   ATTRIBUTE_DATE_TO_DELIVER + "=" + DateHandler.getOracleToDateFunction(deliveryScheduleChangeBean.getDateToDeliver()) +
                   "," + ATTRIBUTE_ORIGINAL_QTY + "=" + StringHandler.nullIfZero(deliveryScheduleChangeBean.getOriginalQty()) + "," +
                   ATTRIBUTE_REVISED_QTY + "=" + StringHandler.nullIfZero(deliveryScheduleChangeBean.getRevisedQty()) + "," +
                   ATTRIBUTE_UPDATED_DATE + "=" + DateHandler.getOracleToDateFunction(deliveryScheduleChangeBean.getUpdatedDate()) + "," +
                   ATTRIBUTE_UPDATED_BY + "=" + StringHandler.nullIfZero(deliveryScheduleChangeBean.getUpdatedBy()) + "," +
                   ATTRIBUTE_EXPEDITE_APPROVAL + "=" + deliveryScheduleChangeBean.getExpediteApproval() + "," +
                   ATTRIBUTE_CSR_APPROVAL + "=" + deliveryScheduleChangeBean.getCsrApproval() + " " +
                   "where " + ATTRIBUTE_COMPANY_ID + "=" + deliveryScheduleChangeBean.getCompanyId();

    return new SqlManager().update(conn, query);
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

    Collection deliveryScheduleChangeBeanColl = new Vector();

    String query = "select * from " + TABLE + " " + getWhereClause(criteria);

    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      DeliveryScheduleChangeBean deliveryScheduleChangeBean = new DeliveryScheduleChangeBean();
      load(dataSetRow, deliveryScheduleChangeBean);
      deliveryScheduleChangeBeanColl.add(deliveryScheduleChangeBean);
    }

    return deliveryScheduleChangeBeanColl;
  }

  public void lineItemAllocate(Collection inArgs) throws BaseException {
    GenericProcedureFactory procFactory = new GenericProcedureFactory(this.getDbManager());
    Connection conn = this.getDbManager().getConnection();
    procFactory.doProcedure(conn, "p_line_item_allocate", inArgs);
  }

} //end of class