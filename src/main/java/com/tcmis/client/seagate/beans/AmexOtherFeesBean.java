package com.tcmis.client.seagate.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;
/******************************************************************************
 * CLASSNAME: AmexOtherFeesBean <br>
 * @version: 1.0, Apr 5, 2005 <br>
 *****************************************************************************/

public class AmexOtherFeesBean
    extends AbstractAmexRecord {

  private BigDecimal feeId;
  private BigDecimal assetBillingAmount;
  private String assetBillingDescription;
  private BigDecimal commissionAmount;
  private String commissionDescription;
  private BigDecimal otherFeeAmount;
  private String otherFeeDescription;
  private Date processDate;
  //private String paymentId;
  //private Date paymentDate;

  //constructor
  public AmexOtherFeesBean() {
    super();
  }

  //setters
  public void setFeeId(BigDecimal feeId) {
    this.feeId = feeId;
  }

  public void setAssetBillingAmount(BigDecimal assetBillingAmount) {
    this.assetBillingAmount = assetBillingAmount;
  }

  public void setAssetBillingDescription(String assetBillingDescription) {
    this.assetBillingDescription = assetBillingDescription;
  }

  public void setCommissionAmount(BigDecimal commissionAmount) {
    this.commissionAmount = commissionAmount;
  }

  public void setCommissionDescription(String commissionDescription) {
    this.commissionDescription = commissionDescription;
  }

  public void setOtherFeeAmount(BigDecimal otherFeeAmount) {
    this.otherFeeAmount = otherFeeAmount;
  }

  public void setOtherFeeDescription(String otherFeeDescription) {
    this.otherFeeDescription = otherFeeDescription;
  }

  public void setProcessDate(Date processDate) {
    this.processDate = processDate;
  }

  public void setPaymentId(String paymentId) {
    this.paymentId = paymentId;
  }

  public void setPaymentDate(Date paymentDate) {
    this.paymentDate = paymentDate;
  }

  //getters
  public BigDecimal getFeeId() {
    return feeId;
  }

  public BigDecimal getAssetBillingAmount() {
    return assetBillingAmount;
  }

  public String getAssetBillingDescription() {
    return assetBillingDescription;
  }

  public BigDecimal getCommissionAmount() {
    return commissionAmount;
  }

  public String getCommissionDescription() {
    return commissionDescription;
  }

  public BigDecimal getOtherFeeAmount() {
    return otherFeeAmount;
  }

  public String getOtherFeeDescription() {
    return otherFeeDescription;
  }

  public Date getProcessDate() {
    return processDate;
  }

  public String getPaymentId() {
    return paymentId;
  }

  public Date getPaymentDate() {
    return paymentDate;
  }

}