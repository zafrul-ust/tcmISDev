package com.tcmis.internal.invoice.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.internal.creditcard.process.CreditCardProcess;
import com.tcmis.internal.invoice.factory.MaxInvoicePeriodViewBeanFactory;
import com.tcmis.internal.invoice.factory.InvoiceFormatVerisignOvBeanFactory;
import com.tcmis.internal.invoice.factory.UnconfirmedInvoiceViewBeanFactory;
import com.tcmis.internal.invoice.beans.MaxInvoicePeriodViewBean;
import com.tcmis.internal.invoice.beans.InvoiceFormatVerisignOvBean;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;

public class CreditCardInvoiceProcess
    extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());
  private static boolean locked = false;
  //private Collection invoices;
  private DbManager dbManager;

  public CreditCardInvoiceProcess(String client) throws Exception {
    super(client);
    dbManager = new DbManager(getClient());
  }

  public void submit(String company, String group) throws Exception {
    if (!lockIt()) {
      log.info("The invoice process is locked, try again later.");
      MailProcess.sendEmail("deverror@tcmis.com", null, "deverror@tcmis.com",
                            "InvoiceProcess is locked",
                            "See log file");
    }else {
      try {
		  Collection invoices = this.getInvoices(company, group);
        InvoiceFormatVerisignOvBean bean = null;
		  CreditCardProcess process = new CreditCardProcess(getClient());
        Iterator iterator = invoices.iterator();
        for (int i = 1; iterator.hasNext(); i++) {
          try {
            log.info("Starting invoice #" + i);
            bean = (InvoiceFormatVerisignOvBean) iterator.next();
            process.processPayment(bean);
            log.info("Finished invoice #" + i);
          }
          catch (Exception e) {
            log.error("There was an error on invoice #" + i + " and it will be reversed");
            log.error("invoice:" + bean.getInvoice());
            log.error("amount:" + bean.getInvoiceAmount());
            log.error("cc number:" + bean.getCreditCardNumber());
            log.error("cc exp date:" + bean.getCreditCardExpirationDate());
            log.error("lines:" + bean.getInvoiceLines(), e);
            reverseInvoice(bean.getInvoice(), bean.getCompanyId(), bean.getInvoiceGroup());
            MailProcess.sendEmail("deverror@tcmis.com", null,
                                  "deverror@tcmis.com",
                                  "Verisign invoice error", "Error on invoice " + bean.getInvoice() + ":" + e.getMessage());
          }
        }
      }finally {
        unlockIt();
      }
    }
  }

  private Collection getInvoices(String company, String group) throws Exception {
    Collection ready = getReadyInvoices(company, group);
    if (ready.isEmpty()) {
      ready = getUnconfirmedInvoices(company, group);
      if (!ready.isEmpty()) {
        confirmInvoices(getMaxInvoicePeriod(company, group), company, group);
      }
      else {
        BigDecimal period = getNextInvoicePeriod(company, group);
        prepInvoices(period, company, group);
        confirmInvoices(period, company, group);
      }
      ready = getReadyInvoices(company, group);
    }
    log.info(ready.size() + " ready to be invoiced.");
    return ready;
  }

  private Collection getUnconfirmedInvoices(String company, String group) throws Exception {
    SearchCriteria criteria = new SearchCriteria();
    criteria.addCriterion("invoice", SearchCriterion.IS_NOT, "null");
    criteria.addCriterion("companyId", SearchCriterion.EQUALS, company);
    criteria.addCriterion("invoiceGroup", SearchCriterion.EQUALS, group);
    UnconfirmedInvoiceViewBeanFactory factory = new
        UnconfirmedInvoiceViewBeanFactory(dbManager);
    return factory.select(criteria);
  }

  private Collection getReadyInvoices(String company, String group) throws Exception {
    SearchCriteria criteria = new SearchCriteria();
    criteria.addCriterion("invoice", SearchCriterion.IS_NOT, "null");
    criteria.addCriterion("invoiceAmount", SearchCriterion.NOT_EQUAL, "0");
    criteria.addCriterion("companyId", SearchCriterion.EQUALS, company);
    criteria.addCriterion("invoiceGroup", SearchCriterion.EQUALS, group);
    InvoiceFormatVerisignOvBeanFactory factory = new InvoiceFormatVerisignOvBeanFactory(
        dbManager);
    return factory.selectObject(criteria);
  }

  private void confirmInvoices(BigDecimal period, String company, String group) throws Exception {
    if(log.isDebugEnabled()) {
      log.debug("Confirming invoice period " + period +"," + company + "," + group);
    }    
    Collection inArgs = new Vector(4);
    inArgs.add(company);
    inArgs.add(group);
    inArgs.add(period);
    inArgs.add(new Timestamp(new Date().getTime()));
    GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);
    factory.doProcedure("P_INVOICE_CONFIRM", inArgs);
  }

  private void prepInvoices(BigDecimal period, String company, String group) throws Exception {
    if(log.isDebugEnabled()) {
      log.debug("Prepping invoice period " + period +"," + company + "," + group);
    }
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
    //criteria.addCriterion("companyId", SearchCriterion.EQUALS, company);
    criteria.addCriterion("invoiceGroup", SearchCriterion.EQUALS, group);
    MaxInvoicePeriodViewBeanFactory factory = new MaxInvoicePeriodViewBeanFactory(
        dbManager);
    Collection c = factory.select(criteria);
    if (c.isEmpty()) {
      throw new IllegalStateException("Cannot determine invoice period");
    }
    Iterator iterator = c.iterator();
    MaxInvoicePeriodViewBean bean = (MaxInvoicePeriodViewBean) iterator.next();
    return bean.getMaxPeriod();
  }

  private BigDecimal getNextInvoicePeriod(String company, String group) throws Exception {
    return getMaxInvoicePeriod(company, group).add(new BigDecimal(1));
  }

  private void reverseInvoice(BigDecimal invoice, String company, String group) throws Exception {
    if(log.isDebugEnabled()) {
      log.debug("Reversing invoice " + invoice +"," + company + "," + group);
    }
    Vector inArgs = new Vector(3);
    inArgs.add(company);
    inArgs.add(group);
    inArgs.add(invoice);
    GenericProcedureFactory pFactory = new GenericProcedureFactory(dbManager);
    pFactory.doProcedure("P_INVOICE_REVERSE_INVOICE", inArgs);
  }

  protected boolean lockIt() {
    synchronized (CreditCardInvoiceProcess.class) {
      if (!locked) {
        return locked = true;
      }
      return false;
    }
  }

  protected boolean unlockIt() {
    synchronized (CreditCardInvoiceProcess.class) {
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

}
