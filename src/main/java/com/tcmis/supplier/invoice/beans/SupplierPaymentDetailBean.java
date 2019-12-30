package com.tcmis.supplier.invoice.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: SupplierPaymentDetailBean <br>
 * @version: 1.0, Aug 22, 2007 <br>
 *****************************************************************************/

public class SupplierPaymentDetailBean
    extends BaseDataBean {

  private String company;
  private String paymentNum;
  private BigDecimal sequenceId;
  private String applyToNum;
  private String invoiceNum;
  private String voucherNum;
  private Date invoiceDate;
  private BigDecimal invoiceAmt;
  private String poNum;
  private BigDecimal amtApplied;
  private BigDecimal amtDiscTaken;
  private String lineDesc;

  //constructor
  public SupplierPaymentDetailBean() {
  }

  //setters
  public void setCompany(String company) {
    this.company = company;
  }

  public void setPaymentNum(String paymentNum) {
    this.paymentNum = paymentNum;
  }

  public void setSequenceId(BigDecimal sequenceId) {
    this.sequenceId = sequenceId;
  }

  public void setApplyToNum(String applyToNum) {
    this.applyToNum = applyToNum;
  }

  public void setInvoiceNum(String invoiceNum) {
    this.invoiceNum = invoiceNum;
  }

  public void setVoucherNum(String voucherNum) {
    this.voucherNum = voucherNum;
  }

  public void setInvoiceDate(Date invoiceDate) {
    this.invoiceDate = invoiceDate;
  }

  public void setInvoiceAmt(BigDecimal invoiceAmt) {
    this.invoiceAmt = invoiceAmt;
  }

  public void setPoNum(String poNum) {
    this.poNum = poNum;
  }

  public void setAmtApplied(BigDecimal amtApplied) {
    this.amtApplied = amtApplied;
  }

  public void setAmtDiscTaken(BigDecimal amtDiscTaken) {
    this.amtDiscTaken = amtDiscTaken;
  }

  public void setLineDesc(String lineDesc) {
    this.lineDesc = lineDesc;
  }

  //getters
  public String getCompany() {
    return company;
  }

  public String getPaymentNum() {
    return paymentNum;
  }

  public BigDecimal getSequenceId() {
    return sequenceId;
  }

  public String getApplyToNum() {
    return applyToNum;
  }

  public String getInvoiceNum() {
    return invoiceNum;
  }

  public String getVoucherNum() {
    return voucherNum;
  }

  public Date getInvoiceDate() {
    return invoiceDate;
  }

  public BigDecimal getInvoiceAmt() {
    return invoiceAmt;
  }

  public String getPoNum() {
    return poNum;
  }

  public BigDecimal getAmtApplied() {
    return amtApplied;
  }

  public BigDecimal getAmtDiscTaken() {
    return amtDiscTaken;
  }

  public String getLineDesc() {
    return lineDesc;
  }
}