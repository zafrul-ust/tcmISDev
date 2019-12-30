package com.tcmis.supplier.invoice.beans;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: SupplierPaymentHeaderBean <br>
 * @version: 1.0, Aug 22, 2007 <br>
 *****************************************************************************/

public class SupplierPaymentInputBean
    extends BaseDataBean {

  private String company;
  private String paymentNum;
  private String checkNum;
  private Date checkDateFrom;
  private Date checkDateTo;
  private String vendorCode;
  private String payToCode;
  private String currencyCode;
  private String payeeName;
  private String docDesc;
  private BigDecimal amtNet;
  private BigDecimal amtOnAcct;
  private String paymentCode;
  private BigDecimal paymentType;

  private String invoiceNum;

  private Collection supplierPaymentDetailBeanCollection = new Vector();

  //constructor
  public SupplierPaymentInputBean() {
  }

  //setters
  public void setCompany(String company) {
    this.company = company;
  }

  public void setPaymentNum(String paymentNum) {
    this.paymentNum = paymentNum;
  }

  public void setCheckNum(String checkNum) {
    this.checkNum = checkNum;
  }

  public void setCheckDateFrom(Date checkDate) {
    this.checkDateFrom = checkDate;
  }

  public void setCheckDateTo(Date checkDate) {
    this.checkDateTo = checkDate;
  }

  public void setVendorCode(String vendorCode) {
    this.vendorCode = vendorCode;
  }

  public void setPayToCode(String payToCode) {
    this.payToCode = payToCode;
  }

  public void setCurrencyCode(String currencyCode) {
    this.currencyCode = currencyCode;
  }

  public void setPayeeName(String payeeName) {
    this.payeeName = payeeName;
  }

  public void setDocDesc(String docDesc) {
    this.docDesc = docDesc;
  }

  public void setAmtNet(BigDecimal amtNet) {
    this.amtNet = amtNet;
  }

  public void setAmtOnAcct(BigDecimal amtOnAcct) {
    this.amtOnAcct = amtOnAcct;
  }

  public void setPaymentCode(String paymentCode) {
    this.paymentCode = paymentCode;
  }

  public void setPaymentType(BigDecimal paymentType) {
    this.paymentType = paymentType;
  }

  public void setSupplierPaymentDetailBeanCollection(Collection c) {
    this.supplierPaymentDetailBeanCollection = c;
  }

  public void addSupplierPaymentDetailBeanCollection(SupplierPaymentDetailBean b) {
    this.supplierPaymentDetailBeanCollection.add(b);
  }

  public void setInvoiceNum(String s) {
    this.invoiceNum = s;
  }

  //getters
  public String getCompany() {
    return company;
  }

  public String getPaymentNum() {
    return paymentNum;
  }

  public String getCheckNum() {
    return checkNum;
  }

  public Date getCheckDateFrom() {
    return checkDateFrom;
  }

  public Date getCheckDateTo() {
    return checkDateTo;
  }

  public String getVendorCode() {
    return vendorCode;
  }

  public String getPayToCode() {
    return payToCode;
  }

  public String getCurrencyCode() {
    return currencyCode;
  }

  public String getPayeeName() {
    return payeeName;
  }

  public String getDocDesc() {
    return docDesc;
  }

  public BigDecimal getAmtNet() {
    return amtNet;
  }

  public BigDecimal getAmtOnAcct() {
    return amtOnAcct;
  }

  public String getPaymentCode() {
    return paymentCode;
  }

  public BigDecimal getPaymentType() {
    return paymentType;
  }

  public Collection getSupplierPaymentDetailBeanCollection() {
    return this.supplierPaymentDetailBeanCollection;
  }

  public String getInvoiceNum() {
    return invoiceNum;
  }
}