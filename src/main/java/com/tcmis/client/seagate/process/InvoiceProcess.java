package com.tcmis.client.seagate.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.seagate.factory.MaxInvoicePeriodBeanFactory;
import com.tcmis.client.seagate.factory.InvoiceFormatSeagateOvBeanFactory;
import com.tcmis.client.seagate.factory.UnconfirmedInvoiceViewBeanFactory;
import com.tcmis.client.seagate.beans.MaxInvoicePeriodBean;
import com.tcmis.client.seagate.beans.InvoiceFormatSeagateOvBean;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;

public class InvoiceProcess
    extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());
  private static boolean locked;
  private Collection invoices;
  private DbManager dbManager;

  public InvoiceProcess(String client) throws Exception {
    super(client);
    //dbManager = new DbManager(getClient());
    //invoices = this.getInvoices();
  }
/*
  public void submit() throws Exception {
    if (!lockIt()) {
      log.info("The invoice process is locked, try again later.");
      MailProcess.sendEmail("deverror@tcmis.com", null, "deverror@tcmis.com",
                            "SEAGATE - InvoiceProcess is locked",
                            "See log file");
    }
    else {
      try {
        PayflowInvoiceProcess process = new PayflowInvoiceProcess(getClient());
        Iterator iterator = invoices.iterator();
        for (int i = 1; iterator.hasNext(); i++) {
          try {
            log.info("Starting invoice #" + i);
            process.submitTransaction( (InvoiceFormatSeagateOvBean) iterator.
                                      next());
            log.info("Finished invoice #" + i);
          }
          catch (Exception e) {
            log.error("There was an error on invoice #" + i, e);
            MailProcess.sendEmail("deverror@tcmis.com", null,
                                  "deverror@tcmis.com",
                                  "SEAGATE - Invoice error", e.getMessage());
          }
        }
      }
      finally {
        unlockIt();
      }
    }
  }

  private Collection getInvoices() throws Exception {
    Collection ready = getReadyInvoices();
    if (ready.isEmpty()) {
      ready = getUnconfirmedInvoices();
      if (!ready.isEmpty()) {
        confirmInvoices(getMaxInvoicePeriod());
      }
      else {
        BigDecimal period = getNextInvoicePeriod();
        prepInvoices(period);
        confirmInvoices(period);
      }
      ready = getReadyInvoices();
    }
    log.info(ready.size() + " ready to be invoiced.");
    return ready;
  }

  private Collection getUnconfirmedInvoices() throws Exception {
    SearchCriteria criteria = new SearchCriteria();
    criteria.addCriterion("invoice", SearchCriterion.IS_NOT, "null");
    UnconfirmedInvoiceViewBeanFactory factory = new
        UnconfirmedInvoiceViewBeanFactory(dbManager);
    return factory.select(criteria);
  }

  private Collection getReadyInvoices() throws Exception {
    SearchCriteria criteria = new SearchCriteria();
    criteria.addCriterion("invoice", SearchCriterion.IS_NOT, "null");
    criteria.addCriterion("invoiceAmount", SearchCriterion.NOT_EQUAL, "0");
    InvoiceFormatSeagateOvBeanFactory factory = new InvoiceFormatSeagateOvBeanFactory(
        dbManager);
    return factory.selectObject(criteria);
  }

  private void confirmInvoices(BigDecimal period, String company, String group) throws Exception {
    log.info("Confirming invoice period " + period + " for " + company + "," + group);
    Collection inArgs = new Vector(4);
    inArgs.add(company);
    inArgs.add(group);
    inArgs.add(period);
    inArgs.add(new Timestamp(new Date().getTime()));
    GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);
    factory.doProcedure("P_INVOICE_CONFIRM", inArgs);
  }

  private void prepInvoices(BigDecimal period, String company, String group) throws Exception {
    log.info("Prepping invoice period " + period + " for " + company + "," + group);
    Collection inArgs = new Vector(4);
    inArgs.add(company);
    inArgs.add(group);
    inArgs.add(period);
    inArgs.add(new Timestamp(new Date().getTime()));
    inArgs.add(new Timestamp(new Date().getTime()));
    GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);
    factory.doProcedure("P_INVOICE_PREP", inArgs);
  }

  private BigDecimal getMaxInvoicePeriod(String company, String group) throws Exception {
    SearchCriteria criteria = new SearchCriteria();
    criteria.addCriterion("maxPeriod", SearchCriterion.IS_NOT, "null");
    criteria.addCriterion("companyId", SearchCriterion.EQUALS, company);
    criteria.addCriterion("invoiceGroup", SearchCriterion.EQUALS, group);
    MaxInvoicePeriodBeanFactory factory = new MaxInvoicePeriodBeanFactory(
        dbManager);
    Collection c = factory.select(criteria);
    if (c.isEmpty()) {
      throw new IllegalStateException("Cannot determine invoice period");
    }
    Iterator iterator = c.iterator();
    MaxInvoicePeriodBean bean = (MaxInvoicePeriodBean) iterator.next();
    return bean.getMaxPeriod();
  }

  private BigDecimal getNextInvoicePeriod(String company, String group) throws Exception {
    return getMaxInvoicePeriod(company, group).add(new BigDecimal(1));
  }

  protected boolean lockIt() {
    synchronized (InvoiceProcess.class) {
      if (!locked) {
        return locked = true;
      }
      return false;
    }
  }

  protected boolean unlockIt() {
    synchronized (InvoiceProcess.class) {
      if (locked) {
        return! (locked = false);
      }
      return false;
    }
  }

  protected void logInitializationError(Exception e) throws Exception {
    log.error("Initializing invoices", e);
    MailProcess.sendEmail("deverror@haastcm.com", null,
                          "deverror@haastcm.com",
                          "Invoice processing error",
                          "Initializing invoices.\n See log file.");
  }
*/
}
