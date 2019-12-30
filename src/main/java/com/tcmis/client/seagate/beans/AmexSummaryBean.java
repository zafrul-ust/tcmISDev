package com.tcmis.client.seagate.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;
/******************************************************************************
 * CLASSNAME: AmexSummaryBean <br>
 * @version: 1.0, Apr 5, 2005 <br>
 *****************************************************************************/

public class AmexSummaryBean
    extends AbstractAmexRecord {

  //private String paymentId;
  //private Date paymentDate;
  private BigDecimal paymentAmount;
  private BigDecimal debitBalanceAmount;
  private String abaBankId;
  private String ddaAccountId;
  private Date transactionDate;

  //constructor
  public AmexSummaryBean() {
  }

  //setters
  public void setPaymentId(String paymentId) {
    this.paymentId = paymentId;
  }

  public void setPaymentDate(Date paymentDate) {
    this.paymentDate = paymentDate;
  }

  public void setPaymentAmount(BigDecimal paymentAmount) {
    this.paymentAmount = paymentAmount;
  }

  public void setDebitBalanceAmount(BigDecimal debitBalanceAmount) {
    this.debitBalanceAmount = debitBalanceAmount;
  }

  public void setAbaBankId(String abaBankId) {
    this.abaBankId = abaBankId;
  }

  public void setDdaAccountId(String ddaAccountId) {
    this.ddaAccountId = ddaAccountId;
  }

  public void setTransactionDate(Date transactionDate) {
    this.transactionDate = transactionDate;
  }

  //getters
  public String getPaymentId() {
    return paymentId;
  }

  public Date getPaymentDate() {
    return paymentDate;
  }

  public BigDecimal getPaymentAmount() {
    return paymentAmount;
  }

  public BigDecimal getDebitBalanceAmount() {
    return debitBalanceAmount;
  }

  public String getAbaBankId() {
    return abaBankId;
  }

  public String getDdaAccountId() {
    return ddaAccountId;
  }

  public Date getTransactionDate() {
    return transactionDate;
  }

}