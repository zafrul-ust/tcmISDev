package com.tcmis.internal.invoice.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: InvoicePaymentStageBean <br>
 * @version: 1.0, Mar 17, 2006 <br>
 *****************************************************************************/

public class InvoicePaymentStageBean
    extends BaseDataBean {

  private BigDecimal totalAmountPaid;
  private String transactionReferenceNumber;
  private Date transactionCreationDate;
  private String payeeCompanyId;
  private String payerCompanyId;
  private String transactionType;
  private String invoiceNumber;
  private BigDecimal invoiceAmount;
  private String poNumber;
  private Date checkIssueDate;
  private String currencyId;
  private String payerContactPhone;
  private Date invoiceDate;
  private String remittanceNotes;

  //constructor
  public InvoicePaymentStageBean() {
  }

  //setters
  public void setTotalAmountPaid(BigDecimal totalAmountPaid) {
    this.totalAmountPaid = totalAmountPaid;
  }

  public void setTransactionReferenceNumber(String transactionReferenceNumber) {
    this.transactionReferenceNumber = transactionReferenceNumber;
  }

  public void setTransactionCreationDate(Date transactionCreationDate) {
    this.transactionCreationDate = transactionCreationDate;
  }

  public void setPayeeCompanyId(String payeeCompanyId) {
    this.payeeCompanyId = payeeCompanyId;
  }

  public void setPayerCompanyId(String payerCompanyId) {
    this.payerCompanyId = payerCompanyId;
  }

  public void setTransactionType(String transactionType) {
    this.transactionType = transactionType;
  }

  public void setInvoiceNumber(String invoiceNumber) {
    this.invoiceNumber = invoiceNumber;
  }

  public void setInvoiceAmount(BigDecimal invoiceAmount) {
    this.invoiceAmount = invoiceAmount;
  }

  public void setPoNumber(String poNumber) {
    this.poNumber = poNumber;
  }

  public void setCheckIssueDate(Date checkIssueDate) {
    this.checkIssueDate = checkIssueDate;
  }

  public void setCurrencyId(String currencyId) {
    this.currencyId = currencyId;
  }

  public void setPayerContactPhone(String payerContactPhone) {
    this.payerContactPhone = payerContactPhone;
  }

  public void setInvoiceDate(Date invoiceDate) {
    this.invoiceDate = invoiceDate;
  }

  public void setRemittanceNotes(String remittanceNotes) {
    this.remittanceNotes = remittanceNotes;
  }

  //getters
  public BigDecimal getTotalAmountPaid() {
    return totalAmountPaid;
  }

  public String getTransactionReferenceNumber() {
    return transactionReferenceNumber;
  }

  public Date getTransactionCreationDate() {
    return transactionCreationDate;
  }

  public String getPayeeCompanyId() {
    return payeeCompanyId;
  }

  public String getPayerCompanyId() {
    return payerCompanyId;
  }

  public String getTransactionType() {
    return transactionType;
  }

  public String getInvoiceNumber() {
    return invoiceNumber;
  }

  public BigDecimal getInvoiceAmount() {
    return invoiceAmount;
  }

  public String getPoNumber() {
    return poNumber;
  }

  public Date getCheckIssueDate() {
    return checkIssueDate;
  }

  public String getCurrencyId() {
    return currencyId;
  }

  public String getPayerContactPhone() {
    return payerContactPhone;
  }

  public Date getInvoiceDate() {
    return invoiceDate;
  }

  public String getRemittanceNotes() {
    return remittanceNotes;
  }
}