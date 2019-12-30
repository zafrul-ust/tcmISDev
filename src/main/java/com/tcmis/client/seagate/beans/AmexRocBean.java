package com.tcmis.client.seagate.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;
/******************************************************************************
 * CLASSNAME: AmexRocBean <br>
 * @version: 1.0, Apr 5, 2005 <br>
 *****************************************************************************/

public class AmexRocBean
    extends AbstractAmexRecord {

  private BigDecimal socId;
  private BigDecimal socLine;
  //private String paymentId;
  private BigDecimal rocId;
  private BigDecimal rocAmount;
  private Date submitDate;
  private Date processDate;
  private String creditCardNumber;
  private String poNumber;
  private String prNumber;
  private String lineItem;
  private BigDecimal entryRef;
  private BigDecimal issueId;
  private BigDecimal invoice;
  //don't know what this is for
  private BigDecimal socAmount;

  //constructor
  public AmexRocBean() {
    super();
  }

  //setters
  public void setSocId(BigDecimal socId) {
    this.socId = socId;
  }

  public void setSocLine(BigDecimal socLine) {
    this.socLine = socLine;
  }

  public void setPaymentId(String paymentId) {
    this.paymentId = paymentId;
  }

  public void setRocId(BigDecimal rocId) {
    this.rocId = rocId;
  }

  public void setRocAmount(BigDecimal rocAmount) {
    this.rocAmount = rocAmount;
  }

  public void setSubmitDate(Date submitDate) {
    this.submitDate = submitDate;
  }

  public void setProcessDate(Date processDate) {
    this.processDate = processDate;
  }

  public void setCreditCardNumber(String creditCardNumber) {
    this.creditCardNumber = creditCardNumber;
  }

  public void setPoNumber(String poNumber) {
    this.poNumber = poNumber;
  }

  public void setPrNumber(String prNumber) {
    this.prNumber = prNumber;
  }

  public void setLineItem(String lineItem) {
    this.lineItem = lineItem;
  }

  public void setEntryRef(BigDecimal entryRef) {
    this.entryRef = entryRef;
  }

  public void setIssueId(BigDecimal issueId) {
    this.issueId = issueId;
  }

  public void setInvoice(BigDecimal invoice) {
    this.invoice = invoice;
  }

  public void setSocAmount(BigDecimal socAmount) {
    this.socAmount = socAmount;
  }

  //getters
  public BigDecimal getSocId() {
    return socId;
  }

  public BigDecimal getSocLine() {
    return socLine;
  }

  public String getPaymentId() {
    return paymentId;
  }

  public BigDecimal getRocId() {
    return rocId;
  }

  public BigDecimal getRocAmount() {
    return rocAmount;
  }

  public Date getSubmitDate() {
    return submitDate;
  }

  public Date getProcessDate() {
    return processDate;
  }

  public String getCreditCardNumber() {
    return creditCardNumber;
  }

  public String getPoNumber() {
    return poNumber;
  }

  public String getPrNumber() {
    return prNumber;
  }

  public String getLineItem() {
    return lineItem;
  }

  public BigDecimal getEntryRef() {
    return entryRef;
  }

  public BigDecimal getIssueId() {
    return issueId;
  }

  public BigDecimal getInvoice() {
    return invoice;
  }

  public BigDecimal getSocAmount() {
    return socAmount;
  }

}