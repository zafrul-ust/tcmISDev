package com.tcmis.client.seagate.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;
/******************************************************************************
 * CLASSNAME: AmexSocBean <br>
 * @version: 1.0, Apr 5, 2005 <br>
 *****************************************************************************/

public class AmexSocBean
    extends AbstractAmexRecord {

  private BigDecimal socId;
  private BigDecimal socLine;
  //private String paymentId;
  private Date submitDate;
  private Date processDate;
  private BigDecimal socAmount;
  private BigDecimal netSocAmount;
  private BigDecimal discountAmount;
  private BigDecimal discountRate;
  private BigDecimal rocCount;
  //not sure what this is for...
  private boolean pcard;

  //constructor
  public AmexSocBean() {
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

  public void setSubmitDate(Date submitDate) {
    this.submitDate = submitDate;
  }

  public void setProcessDate(Date processDate) {
    this.processDate = processDate;
  }

  public void setSocAmount(BigDecimal socAmount) {
    this.socAmount = socAmount;
  }

  public void setNetSocAmount(BigDecimal netSocAmount) {
    this.netSocAmount = netSocAmount;
  }

  public void setDiscountAmount(BigDecimal discountAmount) {
    this.discountAmount = discountAmount;
  }

  public void setDiscountRate(BigDecimal discountRate) {
    this.discountRate = discountRate;
  }

  public void setRocCount(BigDecimal rocCount) {
    this.rocCount = rocCount;
  }

  public void setPcard(boolean pcard) {
    this.pcard = pcard;
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

  public Date getSubmitDate() {
    return submitDate;
  }

  public Date getProcessDate() {
    return processDate;
  }

  public BigDecimal getSocAmount() {
    return socAmount;
  }

  public BigDecimal getNetSocAmount() {
    return netSocAmount;
  }

  public BigDecimal getDiscountAmount() {
    return discountAmount;
  }

  public BigDecimal getDiscountRate() {
    return discountRate;
  }

  public BigDecimal getRocCount() {
    return rocCount;
  }

  public boolean getPcard() {
    return pcard;
  }
}