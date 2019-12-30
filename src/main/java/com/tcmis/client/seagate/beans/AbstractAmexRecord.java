package com.tcmis.client.seagate.beans;

import java.math.BigDecimal;
//import java.sql.Timestamp;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

public abstract class AbstractAmexRecord
    extends BaseDataBean {

  protected BigDecimal payeeId;
  protected BigDecimal submitter;
  protected String unit;
  protected String paymentId;
  protected Date paymentDate;

  /**
   * Constructs an AmexTrailer record
   */
  public AbstractAmexRecord() {
    super();
  }

  public BigDecimal getPayeeId() {
    return payeeId;
  }

  /*
   * @see AmexRecord#getSENumber()
   */
  public BigDecimal getSubmitterId() {
    return submitter;
  }

  /*
   * @see AmexRecord#getSEUnitID()
   */
  public String getUnitId() {
    return unit;
  }

  /*
   * @see AmexRecord#getPaymentDate()
   */
  public Date getPaymentDate() {
    return paymentDate;
  }

  /*
   * @see AmexRecord#getPaymentID()
   */
  public String getPaymentId() {
    return paymentId;
  }

  public void setPayeeId(BigDecimal payeeId) {
    this.payeeId = payeeId;
  }

  public void setSubmitter(BigDecimal submitter) {
    this.submitter = submitter;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  public void setPaymentId(String paymentId) {
    this.paymentId = paymentId;
  }

  public void setPaymentDate(Date paymentDate) {
    this.paymentDate = paymentDate;
  }

}