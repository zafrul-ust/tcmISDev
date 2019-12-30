package com.tcmis.client.seagate.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: SeagateInvoiceHistoryBean <br>
 * @version: 1.0, Feb 8, 2005 <br>
 *****************************************************************************/

public class SeagateInvoiceHistoryBean
    extends BaseDataBean {

  private BigDecimal entryId;
  private String doNumber;
  private BigDecimal prNumber;
  private String lineItem;
  private BigDecimal issueId;
  private BigDecimal invoiceAmount;
  private BigDecimal ccNumber;
  private Date ccExpiration;
  private String txref;
  private String authcode;
  private String resultCode;
  private Date invoiceDate;
  private BigDecimal invoiceLine;
  private BigDecimal invoice;
  private BigDecimal issueAmount;
  private BigDecimal invoicePeriod;
  private String originalTxref;
  private String status;

  //constructor
  public SeagateInvoiceHistoryBean() {
  }

  //setters
  public void setEntryId(BigDecimal entryId) {
    this.entryId = entryId;
  }

  public void setDoNumber(String doNumber) {
    this.doNumber = doNumber;
  }

  public void setPrNumber(BigDecimal prNumber) {
    this.prNumber = prNumber;
  }

  public void setLineItem(String lineItem) {
    this.lineItem = lineItem;
  }

  public void setIssueId(BigDecimal issueId) {
    this.issueId = issueId;
  }

  public void setInvoiceAmount(BigDecimal invoiceAmount) {
    this.invoiceAmount = invoiceAmount;
  }

  public void setCcNumber(BigDecimal ccNumber) {
    this.ccNumber = ccNumber;
  }

  public void setCcExpiration(Date ccExpiration) {
    this.ccExpiration = ccExpiration;
  }

  public void setTxref(String txref) {
    this.txref = txref;
  }

  public void setAuthcode(String authcode) {
    this.authcode = authcode;
  }

  public void setResultCode(String resultCode) {
    this.resultCode = resultCode;
  }

  public void setInvoiceDate(Date invoiceDate) {
    this.invoiceDate = invoiceDate;
  }

  public void setInvoiceLine(BigDecimal invoiceLine) {
    this.invoiceLine = invoiceLine;
  }

  public void setInvoice(BigDecimal invoice) {
    this.invoice = invoice;
  }

  public void setIssueAmount(BigDecimal issueAmount) {
    this.issueAmount = issueAmount;
  }

  public void setInvoicePeriod(BigDecimal invoicePeriod) {
    this.invoicePeriod = invoicePeriod;
  }

  public void setOriginalTxref(String originalTxref) {
    this.originalTxref = originalTxref;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  //getters
  public BigDecimal getEntryId() {
    return entryId;
  }

  public String getDoNumber() {
    return doNumber;
  }

  public BigDecimal getPrNumber() {
    return prNumber;
  }

  public String getLineItem() {
    return lineItem;
  }

  public BigDecimal getIssueId() {
    return issueId;
  }

  public BigDecimal getInvoiceAmount() {
    return invoiceAmount;
  }

  public BigDecimal getCcNumber() {
    return ccNumber;
  }

  public Date getCcExpiration() {
    return ccExpiration;
  }

  public String getTxref() {
    return txref;
  }

  public String getAuthcode() {
    return authcode;
  }

  public String getResultCode() {
    return resultCode;
  }

  public Date getInvoiceDate() {
    return invoiceDate;
  }

  public BigDecimal getInvoiceLine() {
    return invoiceLine;
  }

  public BigDecimal getInvoice() {
    return invoice;
  }

  public BigDecimal getIssueAmount() {
    return issueAmount;
  }

  public BigDecimal getInvoicePeriod() {
    return invoicePeriod;
  }

  public String getOriginalTxref() {
    return originalTxref;
  }

  public String getStatus() {
    return status;
  }

}