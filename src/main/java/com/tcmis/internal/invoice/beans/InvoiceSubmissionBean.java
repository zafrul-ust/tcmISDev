package com.tcmis.internal.invoice.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: InvoiceSubmissionBean <br>
 * @version: 1.0, Dec 6, 2005 <br>
 *****************************************************************************/

public class InvoiceSubmissionBean
    extends BaseDataBean {

  private BigDecimal invoice;
  private BigDecimal issueId;
  private BigDecimal issueCostRevision;
  private Date dateSent;
  private Date dateRejected;
  private String reasonRejected;
  private String facilityId;
  private String xblnr;
  private String documentControlNumber;
  private Date dateAcknowledged;
  private BigDecimal invoiceAmount;
  private BigDecimal creditCardNumber;
  private Date creditCardExpirationDate;
  private Date invoiceDate;
  private BigDecimal invoiceLine;
  private BigDecimal prNumber;
  private BigDecimal lineItem;
  private String poNumber;
  private BigDecimal issueAmount;
  private BigDecimal invoicePeriod;
  private String txref;
  private String authcode;
  private String resultCode;
  private BigDecimal entryId;
  private String status;
  private String creditCardType;
  private String creditCardName;

  //constructor
  public InvoiceSubmissionBean() {
  }

  //setters
  public void setInvoice(BigDecimal invoice) {
    this.invoice = invoice;
  }

  public void setIssueId(BigDecimal issueId) {
    this.issueId = issueId;
  }

  public void setIssueCostRevision(BigDecimal issueCostRevision) {
    this.issueCostRevision = issueCostRevision;
  }

  public void setDateSent(Date dateSent) {
    this.dateSent = dateSent;
  }

  public void setDateRejected(Date dateRejected) {
    this.dateRejected = dateRejected;
  }

  public void setReasonRejected(String reasonRejected) {
    this.reasonRejected = reasonRejected;
  }

  public void setFacilityId(String facilityId) {
    this.facilityId = facilityId;
  }

  public void setXblnr(String xblnr) {
    this.xblnr = xblnr;
  }

  public void setDocumentControlNumber(String documentControlNumber) {
    this.documentControlNumber = documentControlNumber;
  }

  public void setDateAcknowledged(Date dateAcknowledged) {
    this.dateAcknowledged = dateAcknowledged;
  }

  public void setInvoiceAmount(BigDecimal invoiceAmount) {
    this.invoiceAmount = invoiceAmount;
  }

  public void setCreditCardNumber(BigDecimal creditCardNumber) {
    this.creditCardNumber = creditCardNumber;
  }

  public void setCreditCardExpirationDate(Date creditCardExpirationDate) {
    this.creditCardExpirationDate = creditCardExpirationDate;
  }

  public void setInvoiceDate(Date invoiceDate) {
    this.invoiceDate = invoiceDate;
  }

  public void setInvoiceLine(BigDecimal invoiceLine) {
    this.invoiceLine = invoiceLine;
  }

  public void setPrNumber(BigDecimal prNumber) {
    this.prNumber = prNumber;
  }

  public void setLineItem(BigDecimal lineItem) {
    this.lineItem = lineItem;
  }

  public void setPoNumber(String poNumber) {
    this.poNumber = poNumber;
  }

  public void setIssueAmount(BigDecimal issueAmount) {
    this.issueAmount = issueAmount;
  }

  public void setInvoicePeriod(BigDecimal invoicePeriod) {
    this.invoicePeriod = invoicePeriod;
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

  public void setEntryId(BigDecimal entryId) {
    this.entryId = entryId;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public void setCreditCardType(String creditCardType) {
    this.creditCardType = creditCardType;
  }

  public void setCreditCardName(String creditCardName) {
    this.creditCardName = creditCardName;
  }

  //getters
  public BigDecimal getInvoice() {
    return invoice;
  }

  public BigDecimal getIssueId() {
    return issueId;
  }

  public BigDecimal getIssueCostRevision() {
    return issueCostRevision;
  }

  public Date getDateSent() {
    return dateSent;
  }

  public Date getDateRejected() {
    return dateRejected;
  }

  public String getReasonRejected() {
    return reasonRejected;
  }

  public String getFacilityId() {
    return facilityId;
  }

  public String getXblnr() {
    return xblnr;
  }

  public String getDocumentControlNumber() {
    return documentControlNumber;
  }

  public Date getDateAcknowledged() {
    return dateAcknowledged;
  }

  public BigDecimal getInvoiceAmount() {
    return invoiceAmount;
  }

  public BigDecimal getCreditCardNumber() {
    return creditCardNumber;
  }

  public Date getCreditCardExpirationDate() {
    return creditCardExpirationDate;
  }

  public Date getInvoiceDate() {
    return invoiceDate;
  }

  public BigDecimal getInvoiceLine() {
    return invoiceLine;
  }

  public BigDecimal getPrNumber() {
    return prNumber;
  }

  public BigDecimal getLineItem() {
    return lineItem;
  }

  public String getPoNumber() {
    return poNumber;
  }

  public BigDecimal getIssueAmount() {
    return issueAmount;
  }

  public BigDecimal getInvoicePeriod() {
    return invoicePeriod;
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

  public BigDecimal getEntryId() {
    return entryId;
  }

  public String getStatus() {
    return status;
  }

  public String getCreditCardType() {
    return creditCardType;
  }

  public String getCreditCardName() {
    return creditCardName;
  }
}