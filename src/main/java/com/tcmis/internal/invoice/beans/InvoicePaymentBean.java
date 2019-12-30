package com.tcmis.internal.invoice.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: InvoicePaymentBean <br>
 * @version: 1.0, Mar 16, 2006 <br>
 *****************************************************************************/

public class InvoicePaymentBean
    extends BaseDataBean {

  private BigDecimal invoice;
  private String companyId;
  private BigDecimal paymentAmount;
  private String paymentId;
  private Date paymentDate;
  private String transactionId;

  //constructor
  public InvoicePaymentBean() {
  }

  //setters
  public void setInvoice(BigDecimal invoice) {
    this.invoice = invoice;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public void setPaymentAmount(BigDecimal paymentAmount) {
    this.paymentAmount = paymentAmount;
  }

  public void setPaymentId(String paymentId) {
    this.paymentId = paymentId;
  }

  public void setPaymentDate(Date paymentDate) {
    this.paymentDate = paymentDate;
  }

  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }

  //getters
  public BigDecimal getInvoice() {
    return invoice;
  }

  public String getCompanyId() {
    return companyId;
  }

  public BigDecimal getPaymentAmount() {
    return paymentAmount;
  }

  public String getPaymentId() {
    return paymentId;
  }

  public Date getPaymentDate() {
    return paymentDate;
  }

  public String getTransactionId() {
    return transactionId;
  }
}