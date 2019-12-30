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

/******************************************************************************
 * CLASSNAME: InvoiceViewArvinMeritorBeanFactory <br>
 * @version: 1.0, Mar 3, 2005 <br>
 *****************************************************************************/

public class InvoiceViewArvinMeritorBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_INVOICE = "INVOICE";
  public String ATTRIBUTE_MATERIAL_AMOUNT = "MATERIAL_AMOUNT";
  public String ATTRIBUTE_SERVICE_AMOUNT = "SERVICE_AMOUNT";
  public String ATTRIBUTE_PO_NUMBER = "PO_NUMBER";
  public String ATTRIBUTE_INVOICE_DATE = "INVOICE_DATE";
  public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
  public String ATTRIBUTE_INVOICE_AMOUNT = "INVOICE_AMOUNT";
  public String ATTRIBUTE_INVOICE_PERIOD = "INVOICE_PERIOD";
  public String ATTRIBUTE_INVOICE_GROUP = "INVOICE_GROUP";

  //table name
  public String TABLE = "INVOICE_VIEW_ARVIN_MERITOR";

  //constructor
  public InvoiceViewArvinMeritorBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("invoice")) {
      return ATTRIBUTE_INVOICE;
    }
    else if (attributeName.equals("materialAmount")) {
      return ATTRIBUTE_MATERIAL_AMOUNT;
    }
    else if (attributeName.equals("serviceAmount")) {
      return ATTRIBUTE_SERVICE_AMOUNT;
    }
    else if (attributeName.equals("poNumber")) {
      return ATTRIBUTE_PO_NUMBER;
    }
    else if (attributeName.equals("invoiceDate")) {
      return ATTRIBUTE_INVOICE_DATE;
    }
    else if (attributeName.equals("facilityId")) {
      return ATTRIBUTE_FACILITY_ID;
    }
    else if (attributeName.equals("invoiceAmount")) {
      return ATTRIBUTE_INVOICE_AMOUNT;
    }
    else if (attributeName.equals("invoicePeriod")) {
      return ATTRIBUTE_INVOICE_PERIOD;
    }
    else if (attributeName.equals("invoiceGroup")) {
      return ATTRIBUTE_INVOICE_GROUP;
    }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, InvoiceViewArvinMeritorBean.class);
  }



  //select
  public Collection select(BigDecimal invoicePeriod) throws BaseException {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = select(invoicePeriod, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection select(BigDecimal invoicePeriod, Connection conn) throws
      BaseException {

    Collection invoiceViewArvinMeritorBeanColl = new Vector();

    String query = "select i.invoice, " +
        "round(i.invoice_amount - sum(l.service_fee),5) material_amount, " +
        "round(sum(l.service_fee),5) service_amount, " +
        "i.po_number, " +
        "i.invoice_date, " +
           "i.facility_id, " +
           "round(i.invoice_amount, 5) invoice_amount,  " +
           "i.invoice_period, " +
           "i.invoice_group " +
           "from invoice i, " +
           "invoice_line l  " +
           "where i.company_id = 'ARVIN_MERITOR' and " +
           "i.invoice = l.invoice and " +
           "i.invoice_period= " + invoicePeriod + " " +
           "group by i.invoice, " +
           "i.po_number, " +
           "i.facility_id, " +
           "i.invoice_date, " +
           "i.invoice_amount,  " +
           "i.invoice_period, " +
           "i.invoice_group " +
           "order by i.invoice";
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();
    InvoiceDetailBeanFactory detailFactory = new InvoiceDetailBeanFactory(getDbManager());
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceViewArvinMeritorBean invoiceViewArvinMeritorBean = new InvoiceViewArvinMeritorBean();
      load(dataSetRow, invoiceViewArvinMeritorBean);
      SearchCriteria detailCriteria = new SearchCriteria();
      detailCriteria.addCriterion("companyId", SearchCriterion.EQUALS, "ARVIN_MERITOR");
      detailCriteria.addCriterion("invoice", SearchCriterion.EQUALS, invoiceViewArvinMeritorBean.getInvoice().toString());
      invoiceViewArvinMeritorBean.setDetail(detailFactory.select(detailCriteria));
      invoiceViewArvinMeritorBeanColl.add(invoiceViewArvinMeritorBean);
    }

    return invoiceViewArvinMeritorBeanColl;
  }
}
