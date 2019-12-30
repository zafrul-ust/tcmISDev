package com.tcmis.internal.invoice.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: UnconfirmedInvoiceViewBean <br>
 * @version: 1.0, Dec 7, 2005 <br>
 *****************************************************************************/

public class UnconfirmedInvoiceViewBean
    extends BaseDataBean {

  private BigDecimal invoice;
  private BigDecimal invoicePeriod;
  private String zip;
  private String poNumber;
  private BigDecimal openAmount;
  private BigDecimal invoiceAmount;
  private BigDecimal creditCardNumber;
  private Date creditCardExpirationDate;
  private String txref;
  private String resultCode;
  private String companyId;
  private String invoiceGroup;

  //constructor
  public UnconfirmedInvoiceViewBean() {
  }

  //setters
  public void setInvoice(BigDecimal invoice) {
    this.invoice = invoice;
  }

  public void setInvoicePeriod(BigDecimal invoicePeriod) {
    this.invoicePeriod = invoicePeriod;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }

  public void setPoNumber(String poNumber) {
    this.poNumber = poNumber;
  }

  public void setOpenAmount(BigDecimal openAmount) {
    this.openAmount = openAmount;
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

  public void setTxref(String txref) {
    this.txref = txref;
  }

  public void setResultCode(String resultCode) {
    this.resultCode = resultCode;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public void setInvoiceGroup(String invoiceGroup) {
    this.invoiceGroup = invoiceGroup;
  }

  //getters
  public BigDecimal getInvoice() {
    return invoice;
  }

  public BigDecimal getInvoicePeriod() {
    return invoicePeriod;
  }

  public String getZip() {
    return zip;
  }

  public String getPoNumber() {
    return poNumber;
  }

  public BigDecimal getOpenAmount() {
    return openAmount;
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

  public String getTxref() {
    return txref;
  }

  public String getResultCode() {
    return resultCode;
  }

  public String getCompanyId() {
    return companyId;
  }

  public String getInvoiceGroup() {
    return invoiceGroup;
  }
}