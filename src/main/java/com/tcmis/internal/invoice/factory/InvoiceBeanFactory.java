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
import com.tcmis.common.util.SqlHandler;
import com.tcmis.internal.invoice.beans.InvoiceBean;
import com.tcmis.internal.invoice.factory.InvoiceLineBeanFactory;
import com.tcmis.internal.invoice.factory.InvoiceViewMillerDetailBeanFactory;
import com.tcmis.internal.invoice.factory.InvoiceDetailBeanFactory;

/******************************************************************************
 * CLASSNAME: InvoiceBeanFactory <br>
 * @version: 1.0, Mar 9, 2005 <br>
 *****************************************************************************/

public class InvoiceBeanFactory
    extends BaseBeanFactory {

  Log log = LogFactory.getLog(this.getClass());

  //column names
  public String ATTRIBUTE_INVOICE = "INVOICE";
  public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
  public String ATTRIBUTE_FACILITY_ID = "FACILITY_ID";
  public String ATTRIBUTE_ACCOUNT_SYS_ID = "ACCOUNT_SYS_ID";
  public String ATTRIBUTE_INVOICE_AMOUNT = "INVOICE_AMOUNT";
  public String ATTRIBUTE_INVOICE_DATE = "INVOICE_DATE";
  public String ATTRIBUTE_TRANSACTION_DATE = "TRANSACTION_DATE";
  public String ATTRIBUTE_INVOICE_PERIOD = "INVOICE_PERIOD";
  public String ATTRIBUTE_ACCOUNT_SYS_LABEL = "ACCOUNT_SYS_LABEL";
  public String ATTRIBUTE_LOCATION_LABEL = "LOCATION_LABEL";
  public String ATTRIBUTE_INVOICE_GROUP = "INVOICE_GROUP";
  public String ATTRIBUTE_PO_NUMBER = "PO_NUMBER";
  public String ATTRIBUTE_COMMODITY = "COMMODITY";
  public String ATTRIBUTE_ORIGINAL_INVOICE = "ORIGINAL_INVOICE";
  public String ATTRIBUTE_CREDIT_INVOICE = "CREDIT_INVOICE";
  public String ATTRIBUTE_CREDIT_CARD_NUMBER = "CREDIT_CARD_NUMBER";
  public String ATTRIBUTE_CREDIT_CARD_EXPIRATION_DATE =
      "CREDIT_CARD_EXPIRATION_DATE";
  public String ATTRIBUTE_START_DATE = "START_DATE";
  public String ATTRIBUTE_END_DATE = "END_DATE";
  public String ATTRIBUTE_PART_DESCRIPTION = "PART_DESCRIPTION";

  //this is used for fec invoices
  public String ATTRIBUTE_REFERENCE_NUMBER = "REFERENCE_NUMBER";

  //table name
  public String TABLE = "INVOICE";

  //constructor
  public InvoiceBeanFactory(DbManager dbManager) {
    super(dbManager);
  }

  //get column names
  public String getColumnName(String attributeName) {
    if (attributeName.equals("invoice")) {
      return ATTRIBUTE_INVOICE;
    }
    else if (attributeName.equals("companyId")) {
      return ATTRIBUTE_COMPANY_ID;
    }
    else if (attributeName.equals("facilityId")) {
      return ATTRIBUTE_FACILITY_ID;
    }
    else if (attributeName.equals("accountSysId")) {
      return ATTRIBUTE_ACCOUNT_SYS_ID;
    }
    else if (attributeName.equals("invoiceAmount")) {
      return ATTRIBUTE_INVOICE_AMOUNT;
    }
    else if (attributeName.equals("invoiceDate")) {
      return ATTRIBUTE_INVOICE_DATE;
    }
    else if (attributeName.equals("transactionDate")) {
      return ATTRIBUTE_TRANSACTION_DATE;
    }
    else if (attributeName.equals("invoicePeriod")) {
      return ATTRIBUTE_INVOICE_PERIOD;
    }
    else if (attributeName.equals("accountSysLabel")) {
      return ATTRIBUTE_ACCOUNT_SYS_LABEL;
    }
    else if (attributeName.equals("locationLabel")) {
      return ATTRIBUTE_LOCATION_LABEL;
    }
    else if (attributeName.equals("invoiceGroup")) {
      return ATTRIBUTE_INVOICE_GROUP;
    }
    else if (attributeName.equals("poNumber")) {
      return ATTRIBUTE_PO_NUMBER;
    }
    else if (attributeName.equals("commodity")) {
      return ATTRIBUTE_COMMODITY;
    }
    else if (attributeName.equals("originalInvoice")) {
      return ATTRIBUTE_ORIGINAL_INVOICE;
    }
    else if (attributeName.equals("creditInvoice")) {
      return ATTRIBUTE_CREDIT_INVOICE;
    }
    else if (attributeName.equals("creditCardNumber")) {
      return ATTRIBUTE_CREDIT_CARD_NUMBER;
    }
    else if (attributeName.equals("creditCardExpirationDate")) {
      return ATTRIBUTE_CREDIT_CARD_EXPIRATION_DATE;
    }
    else if (attributeName.equals("startDate")) {
      return ATTRIBUTE_START_DATE;
    }
    else if (attributeName.equals("endDate")) {
      return ATTRIBUTE_END_DATE;
    }
    else if (attributeName.equals("referenceNumber")) {
      return ATTRIBUTE_REFERENCE_NUMBER;
    }
    else if (attributeName.equals("partDescription")) {
        return ATTRIBUTE_PART_DESCRIPTION;
      }
    else {
      return super.getColumnName(attributeName);
    }
  }

  //get column types
  public int getType(String attributeName) {
    return super.getType(attributeName, InvoiceBean.class);
  }

//you need to verify the primary key(s) before uncommenting this
  /*
//delete
   public int delete(InvoiceBean invoiceBean)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + invoiceBean.getInvoice());
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
   public int delete(InvoiceBean invoiceBean, Connection conn)
    throws BaseException {
    SearchCriteria criteria = new SearchCriteria("invoice", "SearchCriterion.EQUALS",
     "" + invoiceBean.getInvoice());
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
   public int insert(InvoiceBean invoiceBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = insert(invoiceBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int insert(InvoiceBean invoiceBean, Connection conn)
    throws BaseException {
    SqlManager sqlManager = new SqlManager();
    String query  =
     "insert into " + TABLE + " (" +
     ATTRIBUTE_INVOICE + "," +
     ATTRIBUTE_COMPANY_ID + "," +
     ATTRIBUTE_FACILITY_ID + "," +
     ATTRIBUTE_ACCOUNT_SYS_ID + "," +
     ATTRIBUTE_INVOICE_AMOUNT + "," +
     ATTRIBUTE_INVOICE_DATE + "," +
     ATTRIBUTE_TRANSACTION_DATE + "," +
     ATTRIBUTE_INVOICE_PERIOD + "," +
     ATTRIBUTE_ACCOUNT_SYS_LABEL + "," +
     ATTRIBUTE_LOCATION_LABEL + "," +
     ATTRIBUTE_INVOICE_GROUP + "," +
     ATTRIBUTE_PO_NUMBER + "," +
     ATTRIBUTE_COMMODITY + "," +
     ATTRIBUTE_ORIGINAL_INVOICE + "," +
     ATTRIBUTE_CREDIT_INVOICE + "," +
     ATTRIBUTE_CREDIT_CARD_NUMBER + "," +
     ATTRIBUTE_CREDIT_CARD_EXPIRATION_DATE + ")" +
   values (
     StringHandler.nullIfZero(invoiceBean.getInvoice()) + "," +
     SqlHandler.delimitString(invoiceBean.getCompanyId()) + "," +
     SqlHandler.delimitString(invoiceBean.getFacilityId()) + "," +
     SqlHandler.delimitString(invoiceBean.getAccountSysId()) + "," +
     StringHandler.nullIfZero(invoiceBean.getInvoiceAmount()) + "," +
     DateHandler.getOracleToDateFunction(invoiceBean.getInvoiceDate()) + "," +
       DateHandler.getOracleToDateFunction(invoiceBean.getTransactionDate()) + "," +
     StringHandler.nullIfZero(invoiceBean.getInvoicePeriod()) + "," +
     SqlHandler.delimitString(invoiceBean.getAccountSysLabel()) + "," +
     SqlHandler.delimitString(invoiceBean.getLocationLabel()) + "," +
     SqlHandler.delimitString(invoiceBean.getInvoiceGroup()) + "," +
     SqlHandler.delimitString(invoiceBean.getPoNumber()) + "," +
     SqlHandler.delimitString(invoiceBean.getCommodity()) + "," +
     StringHandler.nullIfZero(invoiceBean.getOriginalInvoice()) + "," +
     StringHandler.nullIfZero(invoiceBean.getCreditInvoice()) + "," +
     StringHandler.nullIfZero(invoiceBean.getCreditCardNumber()) + "," +
     DateHandler.getOracleToDateFunction(invoiceBean.getCreditCardExpirationDate()) + ")";
    return sqlManager.update(conn, query);
   }
//update
   public int update(InvoiceBean invoiceBean)
    throws BaseException {
    Connection connection = null;
    int result = 0;
    try {
     connection = getDbManager().getConnection();
     result = update(invoiceBean, connection);
    }
    finally {
     this.getDbManager().returnConnection(connection);
    }
    return result;
   }
   public int update(InvoiceBean invoiceBean, Connection conn)
    throws BaseException {
    String query  = "update " + TABLE + " set " +
     ATTRIBUTE_INVOICE + "=" +
      StringHandler.nullIfZero(invoiceBean.getInvoice()) + "," +
     ATTRIBUTE_COMPANY_ID + "=" +
      SqlHandler.delimitString(invoiceBean.getCompanyId()) + "," +
     ATTRIBUTE_FACILITY_ID + "=" +
      SqlHandler.delimitString(invoiceBean.getFacilityId()) + "," +
     ATTRIBUTE_ACCOUNT_SYS_ID + "=" +
      SqlHandler.delimitString(invoiceBean.getAccountSysId()) + "," +
     ATTRIBUTE_INVOICE_AMOUNT + "=" +
      StringHandler.nullIfZero(invoiceBean.getInvoiceAmount()) + "," +
     ATTRIBUTE_INVOICE_DATE + "=" +
      DateHandler.getOracleToDateFunction(invoiceBean.getInvoiceDate()) + "," +
     ATTRIBUTE_TRANSACTION_DATE + "=" +
       DateHandler.getOracleToDateFunction(invoiceBean.getTransactionDate()) + "," +
     ATTRIBUTE_INVOICE_PERIOD + "=" +
      StringHandler.nullIfZero(invoiceBean.getInvoicePeriod()) + "," +
     ATTRIBUTE_ACCOUNT_SYS_LABEL + "=" +
      SqlHandler.delimitString(invoiceBean.getAccountSysLabel()) + "," +
     ATTRIBUTE_LOCATION_LABEL + "=" +
      SqlHandler.delimitString(invoiceBean.getLocationLabel()) + "," +
     ATTRIBUTE_INVOICE_GROUP + "=" +
      SqlHandler.delimitString(invoiceBean.getInvoiceGroup()) + "," +
     ATTRIBUTE_PO_NUMBER + "=" +
      SqlHandler.delimitString(invoiceBean.getPoNumber()) + "," +
     ATTRIBUTE_COMMODITY + "=" +
      SqlHandler.delimitString(invoiceBean.getCommodity()) + "," +
     ATTRIBUTE_ORIGINAL_INVOICE + "=" +
      StringHandler.nullIfZero(invoiceBean.getOriginalInvoice()) + "," +
     ATTRIBUTE_CREDIT_INVOICE + "=" +
      StringHandler.nullIfZero(invoiceBean.getCreditInvoice()) + "," +
     ATTRIBUTE_CREDIT_CARD_NUMBER + "=" +
      StringHandler.nullIfZero(invoiceBean.getCreditCardNumber()) + "," +
     ATTRIBUTE_CREDIT_CARD_EXPIRATION_DATE + "=" +
      DateHandler.getOracleToDateFunction(invoiceBean.getCreditCardExpirationDate()) + " " +
     "where " + ATTRIBUTE_INVOICE + "=" +
      StringHandler.nullIfZero(invoiceBean.getInvoice());
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

    Collection invoiceBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria);
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceBean invoiceBean = new InvoiceBean();
      load(dataSetRow, invoiceBean);
      invoiceBeanColl.add(invoiceBean);
    }

    return invoiceBeanColl;
  }

  public Collection selectWithLine(SearchCriteria criteria) throws BaseException {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = selectWithLine(criteria, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection selectWithLine(SearchCriteria criteria, Connection conn) throws
      BaseException {

    Collection invoiceBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria) + " " +
        "order by " + ATTRIBUTE_INVOICE;
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);
    InvoiceLineBeanFactory lineFactory = new InvoiceLineBeanFactory(this.getDbManager());
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceBean invoiceBean = new InvoiceBean();
      load(dataSetRow, invoiceBean);
      SearchCriteria lineCriteria = new SearchCriteria("invoice",
                                                       SearchCriterion.EQUALS,
                                                       invoiceBean.getInvoice().toString());
      invoiceBean.setInvoiceLineCollection(lineFactory.select(lineCriteria));
      invoiceBeanColl.add(invoiceBean);
    }

    return invoiceBeanColl;
  }

  public Collection selectMiller(BigDecimal invoicePeriod, BigDecimal invoice) throws BaseException {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = selectMiller(invoicePeriod, invoice, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection selectMiller(BigDecimal invoicePeriod, BigDecimal invoice,Connection conn) throws
      BaseException {

    Collection invoiceBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        "where company_id='MILLER' and ";
        if(invoice == null) {
          query = query + "invoice_group='MILLER' and ";
        }
        query = query + "invoice_period=" + invoicePeriod + " ";
        if(invoice != null) {
          query = query + "and invoice=" + invoice + " ";
        }
        query = query + "and invoice not in  " +
        "(select invoice from " +
        "(select d.invoice,d.issue_id,count(distinct quantity) " +
        "from invoice i,invoice_detail d where " +
        "i.invoice = d.invoice and " +
        "i.company_id ='MILLER' and i.invoice_period = " + invoicePeriod + " " +
        "group by d.invoice,d.issue_id having count(distinct quantity) > 1)) " +
        "order by invoice";
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);
    InvoiceViewMillerDetailBeanFactory millerFactory = new InvoiceViewMillerDetailBeanFactory(this.getDbManager());
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceBean invoiceBean = new InvoiceBean();
      load(dataSetRow, invoiceBean);
      invoiceBean.setInvoiceMillerCollection(millerFactory.select(invoiceBean.getInvoice(), invoicePeriod));
      invoiceBeanColl.add(invoiceBean);
    }
    return invoiceBeanColl;
  }

  public Collection selectMillerCorrection(BigDecimal invoicePeriod) throws BaseException {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = selectMillerCorrection(invoicePeriod, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection selectMillerCorrection(BigDecimal invoicePeriod, Connection conn) throws
      BaseException {

    Collection invoiceBeanColl = new Vector();

    String query = "select * From invoice where invoice in (select invoice from ( " +
        "select d.invoice,d.issue_id,count(distinct quantity) " +
        "from invoice i,invoice_detail d where " +
        "i.invoice = d.invoice and " +
        "i.company_id = 'MILLER' and i.invoice_group='MILLER' and i.invoice_period = " + invoicePeriod + " " +
        "group by d.invoice,d.issue_id having count(distinct quantity) > 1))";

    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);
    InvoiceViewMillerDetailBeanFactory millerFactory = new InvoiceViewMillerDetailBeanFactory(this.getDbManager());
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceBean invoiceBean = new InvoiceBean();
      load(dataSetRow, invoiceBean);
      invoiceBean.setInvoiceMillerCollection(millerFactory.selectCorrection(invoiceBean.getInvoice(), invoicePeriod));
      invoiceBeanColl.add(invoiceBean);
    }
    return invoiceBeanColl;
  }

  public Collection selectSyracuse(BigDecimal invoicePeriod) throws BaseException {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = selectSyracuse(invoicePeriod, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection selectSyracuse(BigDecimal invoicePeriod, Connection conn) throws
      BaseException {

    Collection invoiceBeanColl = new Vector();

    String query = "select distinct i.invoice,l.po_number, i.invoice_date, i.invoice_amount, " +
        "(select start_date from invoice_period i " +
        "where i.company_id = 'LOCKHEED' and invoice_group = 'SYR' and " +
        "invoice_period = " + invoicePeriod + ") as start_date, " +
        "(select end_date from invoice_period i " +
        "where i.company_id = 'LOCKHEED' and invoice_group = 'SYR' and " + 
        "invoice_period = " + invoicePeriod + ") as end_date " +
        "from invoice i, " +
        "invoice_line l where i.invoice_group = 'SYR' and " +
        "invoice_period = " + invoicePeriod + " and i.invoice = l.invoice " +
        "and l.po_number is not null order by i.invoice,l.po_number";
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);
    InvoiceViewSyracuseDetailBeanFactory syracuseFactory = new InvoiceViewSyracuseDetailBeanFactory(this.getDbManager());
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceBean invoiceBean = new InvoiceBean();
      load(dataSetRow, invoiceBean);
      invoiceBean.setInvoiceSyracuseCollection(syracuseFactory.select(invoiceBean.getInvoice(), invoiceBean.getPoNumber()));
      invoiceBeanColl.add(invoiceBean);
    }
    return invoiceBeanColl;
  }

  public Collection selectQos(BigDecimal invoicePeriod) throws BaseException {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = selectQos(invoicePeriod, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection selectQos(BigDecimal invoicePeriod, Connection conn) throws
      BaseException {

    Collection invoiceBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        "where invoice_group = 'QOS' and " +
        "invoice_period = " + invoicePeriod;

    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);
    InvoiceViewQosBeanFactory qosFactory = new InvoiceViewQosBeanFactory(this.getDbManager());
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceBean invoiceBean = new InvoiceBean();
      load(dataSetRow, invoiceBean);
      invoiceBean.setInvoiceQosCollection(qosFactory.select(invoiceBean.getInvoice()));
      invoiceBeanColl.add(invoiceBean);
    }
    return invoiceBeanColl;
  }

  public Collection selectMinUtcDate(int invoicePeriod, String division) throws
      BaseException {

    Connection connection = null;
    Collection invoiceBeanColl = new Vector();
    try {
      connection = this.getDbManager().getConnection();
      String query = "select min(invoice_date) invoice_date from invoice where invoice in " +
                     "(select invoice from invoice_format_view_utc1 where " +
                     "invoice_period = " + invoicePeriod + " and " +
                     "invoice_group = " + SqlHandler.delimitString(division) + ")";
      if(log.isDebugEnabled()) {
        log.debug("QUERY:" + query);
      }
      DataSet dataSet = new SqlManager().select(connection, query);

      Iterator dataIter = dataSet.iterator();

      while (dataIter.hasNext()) {
        DataSetRow dataSetRow = (DataSetRow) dataIter.next();
        InvoiceBean invoiceBean = new InvoiceBean();
        load(dataSetRow, invoiceBean);
        invoiceBeanColl.add(invoiceBean);
      }

    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return invoiceBeanColl;
  }

  public Collection selectSauer(BigDecimal invoicePeriod) throws BaseException {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = selectSauer(invoicePeriod, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection selectSauer(BigDecimal invoicePeriod, Connection conn) throws
      BaseException {

    Collection invoiceBeanColl = new Vector();

    String query = "select invoice,invoice_amount, " +
                   "po_number,invoice_date,facility_id " +
                   "from invoice where company_id = 'SAUER_DANFOSS' and " +
                   "invoice_period = " + invoicePeriod + " " +
                   "order by invoice";
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceBean invoiceBean = new InvoiceBean();
      load(dataSetRow, invoiceBean);
      invoiceBeanColl.add(invoiceBean);
    }

    return invoiceBeanColl;
  }

  public Collection selectFss(BigDecimal invoicePeriod) throws BaseException {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = selectFss(invoicePeriod, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection selectFss(BigDecimal invoicePeriod, Connection conn) throws
      BaseException {

    Collection invoiceBeanColl = new Vector();

    String query = "select invoice, invoice_amount," +
        //"decode(sign(invoice_amount),-1,to_char(invoice_amount,'00000000000.00'),to_char(invoice_amount,'000000000000.00')) invoice_amount, " +
        "account_sys_label,location_label,original_invoice " +
        "from invoice where invoice_period = " + invoicePeriod + " " +
        "and invoice_group = 'FSS' and transaction_date is not null " +
        "order by invoice";
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
//System.out.println("QUERY:" + query);
    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();
    InvoiceLineBeanFactory fac = new InvoiceLineBeanFactory(getDbManager());
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceBean invoiceBean = new InvoiceBean();
      load(dataSetRow, invoiceBean);
      SearchCriteria childCriteria = new SearchCriteria("invoice", SearchCriterion.EQUALS, invoiceBean.getInvoice().toString());
      invoiceBean.setInvoiceLineCollection(fac.select(childCriteria));
      invoiceBeanColl.add(invoiceBean);
    }

    return invoiceBeanColl;
  }

  public Collection selectMaxFssInvoicePeriod() throws BaseException {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = selectMaxFssInvoicePeriod(connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection selectMaxFssInvoicePeriod(Connection conn) throws
      BaseException {

    Collection invoiceBeanColl = new Vector();

    String query = "select max(invoice_period) invoice_period " +
        "from invoice where " +
        "invoice_group = 'FSS' and transaction_date is not null";
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
//System.out.println("QUERY:" + query);
    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceBean invoiceBean = new InvoiceBean();
      load(dataSetRow, invoiceBean);
      invoiceBeanColl.add(invoiceBean);
    }

    return invoiceBeanColl;
  }

  public Collection selectFec(String companyId, 
                              String poNumber, 
                              String commodity, 
                              BigDecimal invoicePeriod) throws BaseException {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = selectFec(companyId, poNumber, commodity, invoicePeriod, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection selectFec(String companyId, 
                              String poNumber, 
                              String commodity, 
                              BigDecimal invoicePeriod, 
                              Connection conn) throws
      BaseException {

    Collection invoiceBeanColl = new Vector();

    String query = "select unique d.reference_number,i.*  " +
                   "from invoice i, invoice_detail d " +
                   "where i.company_id=" + SqlHandler.delimitString(companyId) + " and " +
                   "i.invoice_period=" + invoicePeriod + " and " +
                   "i.commodity=" + SqlHandler.delimitString(commodity) + " and " +
                   "i.po_number=" + SqlHandler.delimitString(poNumber) + " and " +
                   "i.invoice=d.invoice " +
                   "order by i.invoice";
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);
    InvoiceDetailBeanFactory detailFactory = new InvoiceDetailBeanFactory(this.getDbManager());
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceBean invoiceBean = new InvoiceBean();
      load(dataSetRow, invoiceBean);
      invoiceBean.setInvoiceDetailCollection(detailFactory.selectFec(invoiceBean.getInvoice(), invoiceBean.getReferenceNumber()));
      invoiceBeanColl.add(invoiceBean);
    }

    return invoiceBeanColl;
  }

  public Vector selectWithDetailCD(SearchCriteria criteria) throws BaseException {

	    Connection connection = null;
	    Vector c = null;
	    try {
	      connection = this.getDbManager().getConnection();
	      c = selectWithDetailCD(criteria, connection);
	    }
	    finally {
	      this.getDbManager().returnConnection(connection);
	    }
	    return c;
	  }

  public Collection selectWithDetail(SearchCriteria criteria) throws BaseException {

	    Connection connection = null;
	    Collection c = null;
	    try {
	      connection = this.getDbManager().getConnection();
	      c = selectWithDetail(criteria, connection);
	    }
	    finally {
	      this.getDbManager().returnConnection(connection);
	    }
	    return c;
	  }

  public Vector selectWithDetailCD(SearchCriteria criteria, Connection conn) throws
  BaseException {
	    Vector invoiceBeanColl = new Vector();

	    String query = "select * from " + TABLE + " " +
	        getWhereClause(criteria) + " " +
	        "order by " + ATTRIBUTE_INVOICE;
	    if(log.isDebugEnabled()) {
	      log.debug("QUERY:" + query);
	    }
	    DataSet dataSet = new SqlManager().select(conn, query);
	    InvoiceDetailBeanFactory detailFactory = new InvoiceDetailBeanFactory(this.getDbManager());
	    Iterator dataIter = dataSet.iterator();
	    while (dataIter.hasNext()) {
	      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
	      InvoiceBean invoiceBean = new InvoiceBean();
	      load(dataSetRow, invoiceBean);
// add charge
	      SearchCriteria detailCriteria = new SearchCriteria("invoice",
                  SearchCriterion.EQUALS,
                  invoiceBean.getInvoice().toString());
	      detailCriteria .addCriterion("netAmount", SearchCriterion.GREATER_THAN_OR_EQUAL_TO, "0");
	      invoiceBean.setInvoiceDetailCollection(detailFactory.select(detailCriteria));
	      invoiceBeanColl.add(invoiceBean);
// add debit.
	      try {
	      invoiceBean = (InvoiceBean)invoiceBean.clone();
	      detailCriteria = new SearchCriteria("invoice",
                  SearchCriterion.EQUALS,
                  invoiceBean.getInvoice().toString());
	      detailCriteria .addCriterion("netAmount", SearchCriterion.LESS_THAN, "0");
	      invoiceBean.setInvoiceDetailCollection(detailFactory.select(detailCriteria));
	      } catch(Exception ex) {invoiceBean = null; }
	      invoiceBeanColl.add(invoiceBean);
	    }
	    return invoiceBeanColl;
  }

  public Collection selectWithDetail(SearchCriteria criteria, Connection conn) throws
      BaseException {

    Collection invoiceBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria) + " " +
        "order by " + ATTRIBUTE_INVOICE;
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);
    InvoiceDetailBeanFactory detailFactory = new InvoiceDetailBeanFactory(this.getDbManager());
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceBean invoiceBean = new InvoiceBean();
      load(dataSetRow, invoiceBean);
      SearchCriteria detailCriteria = new SearchCriteria("invoice",
                                                       SearchCriterion.EQUALS,
                                                       invoiceBean.getInvoice().toString());
      invoiceBean.setInvoiceDetailCollection(detailFactory.select(detailCriteria));
      invoiceBeanColl.add(invoiceBean);
    }

    return invoiceBeanColl;
  }

  public Collection selectEdi(BigDecimal invoicePeriod, BigDecimal invoice) throws BaseException {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = selectEdi(invoicePeriod, invoice, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection selectEdi(BigDecimal invoicePeriod, BigDecimal invoice,Connection conn) throws
      BaseException {

    Collection invoiceBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        "where company_id='MILLER' and ";
        if(invoice == null) {
          query = query + "invoice_group='EDI' and ";
        }
        query = query + "invoice_period=" + invoicePeriod + " ";
        if(invoice != null) {
          query = query + "and invoice=" + invoice + " ";
        }
        query = query + "and invoice not in  " +
        "(select invoice from " +
        "(select d.invoice,d.issue_id,count(distinct quantity) " +
        "from invoice i,invoice_detail d where " +
        "i.invoice = d.invoice and " +
        "i.company_id ='MILLER' and i.invoice_period = " + invoicePeriod + " " +
        "group by d.invoice,d.issue_id having count(distinct quantity) > 1)) " +
        "order by invoice";
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);
    InvoiceViewMillerDetailBeanFactory millerFactory = new InvoiceViewMillerDetailBeanFactory(this.getDbManager());
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceBean invoiceBean = new InvoiceBean();
      load(dataSetRow, invoiceBean);
      invoiceBean.setInvoiceMillerCollection(millerFactory.select(invoiceBean.getInvoice(), invoicePeriod));
      invoiceBeanColl.add(invoiceBean);
    }
    return invoiceBeanColl;
  }
/*
  public Collection selectMillerCorrection(BigDecimal invoicePeriod) throws BaseException {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = selectMillerCorrection(invoicePeriod, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection selectMillerCorrection(BigDecimal invoicePeriod, Connection conn) throws
      BaseException {

    Collection invoiceBeanColl = new Vector();

    String query = "select * From invoice where invoice in (select invoice from ( " +
        "select d.invoice,d.issue_id,count(distinct quantity) " +
        "from invoice i,invoice_detail d where " +
        "i.invoice = d.invoice and " +
        "i.company_id = 'MILLER' and i.invoice_group='MILLER' and i.invoice_period = " + invoicePeriod + " " +
        "group by d.invoice,d.issue_id having count(distinct quantity) > 1))";

    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);
    InvoiceViewMillerDetailBeanFactory millerFactory = new InvoiceViewMillerDetailBeanFactory(this.getDbManager());
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceBean invoiceBean = new InvoiceBean();
      load(dataSetRow, invoiceBean);
      invoiceBean.setInvoiceMillerCollection(millerFactory.selectCorrection(invoiceBean.getInvoice(), invoicePeriod));
      invoiceBeanColl.add(invoiceBean);
    }
    return invoiceBeanColl;
  }
*/

  public Collection selectCaterpillar(SearchCriteria criteria) throws BaseException {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = selectCaterpillar(criteria, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection selectCaterpillar(SearchCriteria criteria, Connection conn) throws
      BaseException {

    Collection invoiceBeanColl = new Vector();

    String query = "select * from " + TABLE + " " +
        getWhereClause(criteria) + " " +
        "order by " + ATTRIBUTE_INVOICE;
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);
    InvoiceDetailBeanFactory detailFactory = new InvoiceDetailBeanFactory(this.getDbManager());
    Iterator dataIter = dataSet.iterator();
    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceBean invoiceBean = new InvoiceBean();
      load(dataSetRow, invoiceBean);
      invoiceBean.setInvoiceDetailCollection(detailFactory.selectCaterpillar(invoiceBean.getInvoice()));
      invoiceBeanColl.add(invoiceBean);
    }

    return invoiceBeanColl;
  }
  
  public Collection selectWithPartDescription(SearchCriteria criteria) throws BaseException {

    Connection connection = null;
    Collection c = null;
    try {
      connection = this.getDbManager().getConnection();
      c = selectWithPartDescription(criteria, connection);
    }
    finally {
      this.getDbManager().returnConnection(connection);
    }
    return c;
  }

  public Collection selectWithPartDescription(SearchCriteria criteria, Connection conn) throws
      BaseException {

    Collection invoiceBeanColl = new Vector();

    String query = "SELECT distinct i.invoice, i.invoice_date, i.invoice_period, i.invoice_group, " +
    				"id.part_description , sum(id.net_amount * id.percent_split_charge) invoice_amount, i.po_number " +
    				"  FROM invoice_detail id, invoice i " + 
    				getWhereClause(criteria) +
    				"AND i.invoice = id.invoice " +
    				"group by " +
    				"i.po_number, i.invoice, i.invoice_date, i.invoice_period,  " +
    				"i.invoice_group, id.part_description"	;
    if(log.isDebugEnabled()) {
      log.debug("QUERY:" + query);
    }
    DataSet dataSet = new SqlManager().select(conn, query);

    Iterator dataIter = dataSet.iterator();

    while (dataIter.hasNext()) {
      DataSetRow dataSetRow = (DataSetRow) dataIter.next();
      InvoiceBean invoiceBean = new InvoiceBean();
      load(dataSetRow, invoiceBean);
      invoiceBeanColl.add(invoiceBean);
    }

    return invoiceBeanColl;
  }
}