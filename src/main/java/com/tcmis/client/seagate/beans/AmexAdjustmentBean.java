package com.tcmis.client.seagate.beans;

import java.math.BigDecimal;
import java.util.Date;

/******************************************************************************
 * CLASSNAME: AmexAdjustmentBean <br>
 * @version: 1.0, Apr 5, 2005 <br>
 *****************************************************************************/

public class AmexAdjustmentBean
    extends AbstractAmexRecord {

  private BigDecimal adjustmentId;
  private Date adjustmentDate;
  private BigDecimal adjustmentAmount;
  private BigDecimal discountAmount;
  private BigDecimal discountRate;
  private BigDecimal serviceFeeAmount;
  private BigDecimal serviceFeeRate;
  private String adjustmentReason;
  //private String paymentId;
  //private Date paymentDate;

  //constructor
  public AmexAdjustmentBean() {
    super();
  }

  //setters
  public void setAdjustmentId(BigDecimal adjustmentId) {
    this.adjustmentId = adjustmentId;
  }

  public void setAdjustmentDate(Date adjustmentDate) {
    this.adjustmentDate = adjustmentDate;
  }

  public void setAdjustmentAmount(BigDecimal adjustmentAmount) {
    this.adjustmentAmount = adjustmentAmount;
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

  public void setAdjustmentReason(String adjustmentReason) {
    this.adjustmentReason = adjustmentReason;
  }

  public void setPaymentId(String paymentId) {
    this.paymentId = paymentId;
  }

  public void setPaymentDate(Date paymentDate) {
    this.paymentDate = paymentDate;
  }

  //getters
  public BigDecimal getAdjustmentId() {
    return adjustmentId;
  }

  public Date getAdjustmentDate() {
    return adjustmentDate;
  }

  public BigDecimal getAdjustmentAmount() {
    return adjustmentAmount;
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

  public String getAdjustmentReason() {
    return adjustmentReason;
  }

  public String getPaymentId() {
    return paymentId;
  }

  public Date getPaymentDate() {
    return paymentDate;
  }

}