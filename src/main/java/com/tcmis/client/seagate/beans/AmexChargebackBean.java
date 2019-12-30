package com.tcmis.client.seagate.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;
/******************************************************************************
 * CLASSNAME: AmexChargebackBean <br>
 * @version: 1.0, Apr 5, 2005 <br>
 *****************************************************************************/

public class AmexChargebackBean
    extends AbstractAmexRecord {

  private BigDecimal socId;
  private BigDecimal socLine;
  //private String paymentId;
  private BigDecimal socAmount;
  private BigDecimal chargebackAmount;
  private BigDecimal netChargebackAmount;
  private BigDecimal discountAmount;
  private BigDecimal discountRate;
  private BigDecimal serviceFeeAmount;
  private BigDecimal serviceFeeRate;
  private Date submitDate;
  private Date processDate;
  private String chargebackReason;

  //constructor
  public AmexChargebackBean() {
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

  public void setSocAmount(BigDecimal socAmount) {
    this.socAmount = socAmount;
  }

  public void setChargebackAmount(BigDecimal chargebackAmount) {
    this.chargebackAmount = chargebackAmount;
  }

  public void setNetChargebackAmount(BigDecimal netChargebackAmount) {
    this.netChargebackAmount = netChargebackAmount;
  }

  public void setDiscountAmount(BigDecimal discountAmount) {
    this.discountAmount = discountAmount;
  }

  public void setDiscountRate(BigDecimal discountRate) {
    this.discountRate = discountRate;
  }

  public void setServiceFeeAmount(BigDecimal serviceFeeAmount) {
    this.serviceFeeAmount = serviceFeeAmount;
  }

  public void setServiceFeeRate(BigDecimal serviceFeeRate) {
    this.serviceFeeRate = serviceFeeRate;
  }

  public void setSubmitDate(Date submitDate) {
    this.submitDate = submitDate;
  }

  public void setProcessDate(Date processDate) {
    this.processDate = processDate;
  }

  public void setChargebackReason(String chargebackReason) {
    this.chargebackReason = chargebackReason;
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

  public BigDecimal getSocAmount() {
    return socAmount;
  }

  public BigDecimal getChargebackAmount() {
    return chargebackAmount;
  }

  public BigDecimal getNetChargebackAmount() {
    return netChargebackAmount;
  }

  public BigDecimal getDiscountAmount() {
    return discountAmount;
  }

  public BigDecimal getDiscountRate() {
    return discountRate;
  }

  public BigDecimal getServiceFeeAmount() {
    return serviceFeeAmount;
  }

  public BigDecimal getServiceFeeRate() {
    return serviceFeeRate;
  }

  public Date getSubmitDate() {
    return submitDate;
  }

  public Date getProcessDate() {
    return processDate;
  }

  public String getChargebackReason() {
    return chargebackReason;
  }

}