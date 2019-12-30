package com.tcmis.internal.invoice.factory;

import java.math.BigDecimal;
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
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.internal.invoice.beans.InvoiceViewArvinMeritorBean;
import com.tcmis.internal.invoice.beans.FssManualInvoicePeriodBean;

/******************************************************************************
 * CLASSNAME: FssManualInvoicePeriodBeanFactory <br>
 * @version: 1.0, Feb 2, 2010 <br>
 *****************************************************************************/

public class FssManualInvoicePeriodBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_INVOICE_PERIOD = "INVOICE_PERIOD";
  public String ATTRIBUTE_STATUS = "STATUS";
  public String ATTRIBUTE_PROCESS_DATE = "PROCESS_DATE";

  //table name
  public String TABLE = "FSS_MANUAL_INVOICE_PERIOD";

  //constructor
  public FssManualInvoicePeriodBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("invoicePeriod")) {
      return ATTRIBUTE_INVOICE_PERIOD;
    }else if (attributeName.equals("status")) {
      return ATTRIBUTE_STATUS;
	 }else if (attributeName.equals("processDate")) {
      return ATTRIBUTE_PROCESS_DATE;
	 }else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, InvoiceViewArvinMeritorBean.class);
  }


  public int update(FssManualInvoicePeriodBean bean) throws BaseException {

    Connection connection = null;
    int result = 0;
    try {
      connection = getDbManager().getConnection();
      result = update(bean, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return result;
  }

  public int update(FssManualInvoicePeriodBean bean, Connection conn) throws
      BaseException {

    String sqlQuery = "update " + TABLE + " set process_date = sysdate,status = '"+bean.getStatus()+"'"+
		  					 " where invoice_period = "+bean.getInvoicePeriod();
	 return new SqlManager().update(conn, sqlQuery);
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

  public Collection select(SearchCriteria criteria, Connection conn) throws BaseException {

    Collection fssManualInvoicePeriodColl = new Vector();

    String query = "select * from " + TABLE + " "+getWhereClause(criteria);
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      FssManualInvoicePeriodBean bean = new FssManualInvoicePeriodBean();
      load(dataSetRow, bean);
      fssManualInvoicePeriodColl.add(bean);
    }

    return fssManualInvoicePeriodColl;
  }
}
