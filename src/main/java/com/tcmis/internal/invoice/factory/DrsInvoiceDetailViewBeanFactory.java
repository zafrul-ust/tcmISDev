package com.tcmis.internal.invoice.factory;

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
import com.tcmis.internal.invoice.beans.DrsInvoiceDetailViewBean;

/******************************************************************************
 * CLASSNAME: DrsInvoiceDetailViewBeanFactory <br>
 * @version: 1.0, Jul 10, 2007 <br>
 *****************************************************************************/

public class DrsInvoiceDetailViewBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_INVOICE = "INVOICE";
  public String ATTRIBUTE_INVOICE_DATE = "INVOICE_DATE";
  public String ATTRIBUTE_INVOICE_PERIOD = "INVOICE_PERIOD";
  public String ATTRIBUTE_ACCOUNT_NUMBER = "ACCOUNT_NUMBER";
  public String ATTRIBUTE_MATERIAL_COST = "MATERIAL_COST";
  public String ATTRIBUTE_MATERIAL_REBATE = "MATERIAL_REBATE";
  public String ATTRIBUTE_WASTE_COST = "WASTE_COST";
  public String ATTRIBUTE_WASTE_REBATE = "WASTE_REBATE";
  public String ATTRIBUTE_GAS_COST = "GAS_COST";
  public String ATTRIBUTE_CYLINDER_RENTAL_COST = "CYLINDER_RENTAL_COST";
  public String ATTRIBUTE_SERVICE_FEE = "SERVICE_FEE";
  public String ATTRIBUTE_TOTAL_DUE = "TOTAL_DUE";

  //table name
  public String TABLE = "DRS_INVOICE_DETAIL_VIEW";

  //constructor
  public DrsInvoiceDetailViewBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("invoice")) {
      return ATTRIBUTE_INVOICE;
    }
    else if (attributeName.equals("invoiceDate")) {
      return ATTRIBUTE_INVOICE_DATE;
    }
    else if (attributeName.equals("invoicePeriod")) {
      return ATTRIBUTE_INVOICE_PERIOD;
    }
    else if (attributeName.equals("accountNumber")) {
      return ATTRIBUTE_ACCOUNT_NUMBER;
    }
    else if (attributeName.equals("materialCost")) {
      return ATTRIBUTE_MATERIAL_COST;
    }
    else if (attributeName.equals("materialRebate")) {
      return ATTRIBUTE_MATERIAL_REBATE;
    }
    else if (attributeName.equals("wasteCost")) {
      return ATTRIBUTE_WASTE_COST;
    }
    else if (attributeName.equals("wasteRebate")) {
      return ATTRIBUTE_WASTE_REBATE;
    }
    else if (attributeName.equals("gasCost")) {
      return ATTRIBUTE_GAS_COST;
    }
    else if (attributeName.equals("cylinderRentalCost")) {
      return ATTRIBUTE_CYLINDER_RENTAL_COST;
    }
    else if (attributeName.equals("serviceFee")) {
      return ATTRIBUTE_SERVICE_FEE;
    }
    else if (attributeName.equals("totalDue")) {
      return ATTRIBUTE_TOTAL_DUE;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, DrsInvoiceDetailViewBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(DrsInvoiceDetailViewBean drsInvoiceDetailViewBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + drsInvoiceDetailViewBean.getInvoice());
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
   public int delete(DrsInvoiceDetailViewBean drsInvoiceDetailViewBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + drsInvoiceDetailViewBean.getInvoice());
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

//you need to verify the primary key(s) before uncommenting this
  /*
//insert
   public int insert(DrsInvoiceDetailViewBean drsInvoiceDetailViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(drsInvoiceDetailViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(DrsInvoiceDetailViewBean drsInvoiceDetailViewBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_INVOICE + "," +
     ATTRIBUTE_INVOICE_DATE + "," +
     ATTRIBUTE_INVOICE_PERIOD + "," +
     ATTRIBUTE_ACCOUNT_NUMBER + "," +
     ATTRIBUTE_MATERIAL_COST + "," +
     ATTRIBUTE_MATERIAL_REBATE + "," +
     ATTRIBUTE_WASTE_COST + "," +
     ATTRIBUTE_WASTE_REBATE + "," +
     ATTRIBUTE_GAS_COST + "," +
     ATTRIBUTE_CYLINDER_RENTAL_COST + "," +
     ATTRIBUTE_SERVICE_FEE + "," +
     ATTRIBUTE_TOTAL_DUE + ")" +
     " values (" +
     drsInvoiceDetailViewBean.getInvoice() + "," +
     DateHandler.getOracleToDateFunction(drsInvoiceDetailViewBean.getInvoiceDate()) + "," +
     drsInvoiceDetailViewBean.getInvoicePeriod() + "," +
       SqlHandler.delimitString(drsInvoiceDetailViewBean.getAccountNumber()) + "," +
     drsInvoiceDetailViewBean.getMaterialCost() + "," +
     drsInvoiceDetailViewBean.getMaterialRebate() + "," +
     drsInvoiceDetailViewBean.getWasteCost() + "," +
     drsInvoiceDetailViewBean.getWasteRebate() + "," +
     drsInvoiceDetailViewBean.getGasCost() + "," +
     drsInvoiceDetailViewBean.getCylinderRentalCost() + "," +
     drsInvoiceDetailViewBean.getServiceFee() + "," +
     drsInvoiceDetailViewBean.getTotalDue() + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(DrsInvoiceDetailViewBean drsInvoiceDetailViewBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(drsInvoiceDetailViewBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(DrsInvoiceDetailViewBean drsInvoiceDetailViewBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_INVOICE + "=" +
      StringHandler.nullIfZero(drsInvoiceDetailViewBean.getInvoice()) + "," +
     ATTRIBUTE_INVOICE_DATE + "=" +
      DateHandler.getOracleToDateFunction(drsInvoiceDetailViewBean.getInvoiceDate()) + "," +
     ATTRIBUTE_INVOICE_PERIOD + "=" +
       StringHandler.nullIfZero(drsInvoiceDetailViewBean.getInvoicePeriod()) + "," +
     ATTRIBUTE_ACCOUNT_NUMBER + "=" +
       SqlHandler.delimitString(drsInvoiceDetailViewBean.getAccountNumber()) + "," +
     ATTRIBUTE_MATERIAL_COST + "=" +
       StringHandler.nullIfZero(drsInvoiceDetailViewBean.getMaterialCost()) + "," +
     ATTRIBUTE_MATERIAL_REBATE + "=" +
       StringHandler.nullIfZero(drsInvoiceDetailViewBean.getMaterialRebate()) + "," +
     ATTRIBUTE_WASTE_COST + "=" +
      StringHandler.nullIfZero(drsInvoiceDetailViewBean.getWasteCost()) + "," +
     ATTRIBUTE_WASTE_REBATE + "=" +
       StringHandler.nullIfZero(drsInvoiceDetailViewBean.getWasteRebate()) + "," +
     ATTRIBUTE_GAS_COST + "=" +
      StringHandler.nullIfZero(drsInvoiceDetailViewBean.getGasCost()) + "," +
     ATTRIBUTE_CYLINDER_RENTAL_COST + "=" +
      StringHandler.nullIfZero(drsInvoiceDetailViewBean.getCylinderRentalCost()) + "," +
     ATTRIBUTE_SERVICE_FEE + "=" +
       StringHandler.nullIfZero(drsInvoiceDetailViewBean.getServiceFee()) + "," +
     ATTRIBUTE_TOTAL_DUE + "=" +
      StringHandler.nullIfZero(drsInvoiceDetailViewBean.getTotalDue()) + " " +
     "where " + ATTRIBUTE_INVOICE + "=" +
      drsInvoiceDetailViewBean.getInvoice();
    return new SqlManager().update(conn, query);
   }
   */

  //select
  public Collection select(SearchCriteria criteria, SortCriteria sortCriteria) throws
      BaseException {
    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = select(criteria, sortCriteria, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection select(SearchCriteria criteria, SortCriteria sortCriteria,
                           Connection conn) throws BaseException {
    Collection drsInvoiceDetailViewBeanColl = new Vector();
    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria) + getOrderByClause(sortCriteria);
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      DrsInvoiceDetailViewBean drsInvoiceDetailViewBean = new
          DrsInvoiceDetailViewBean();
      load(dataSetRow, drsInvoiceDetailViewBean);
      drsInvoiceDetailViewBeanColl.add(drsInvoiceDetailViewBean);
    }
    return drsInvoiceDetailViewBeanColl;
  }
}