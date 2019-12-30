package com.tcmis.supplier.invoice.beans;

import java.util.Collection;
import java.util.Date;
import java.util.Vector;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: SupplierPaymentHeaderViewBean <br>
 * @version: 1.0, Sep 18, 2007 <br>
 *****************************************************************************/

public class SupplierPaymentHeaderViewBean
    extends BaseDataBean {

  private String company;
  private String operatingEntityName;
  private String paymentNum;
  private String checkNum;
  private Date checkDate;
  private String vendorCode;
  private String payToCode;
  private String payeeName;
  private String docDesc;
  private BigDecimal amtNet;
  private BigDecimal amtOnAcct;
  private String currencyCode;
  private String paymentCode;
  private BigDecimal paymentType;
  private String apContactName;
  private String apContactEmail;
  private String apContactPhone;
  private String supplierName;
  private String supplier;

  private Collection supplierPaymentDetailViewBeanCollection = new Vector();

  //constructor
  public SupplierPaymentHeaderViewBean() {
  }

  //setters
  public void setCompany(String company) {
    this.company = company;
  }

  public void setOperatingEntityName(String operatingEntityName) {
    this.operatingEntityName = operatingEntityName;
  }

  public void setPaymentNum(String paymentNum) {
    this.paymentNum = paymentNum;
  }

  public void setCheckNum(String checkNum) {
    this.checkNum = checkNum;
  }

  public void setCheckDate(Date checkDate) {
    this.checkDate = checkDate;
  }

  public void setVendorCode(String vendorCode) {
    this.vendorCode = vendorCode;
  }

  public void setPayToCode(String payToCode) {
    this.payToCode = payToCode;
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

  public void setCurrencyCode(String currencyCode) {
    this.currencyCode = currencyCode;
  }

  public void setPaymentCode(String paymentCode) {
    this.paymentCode = paymentCode;
  }

  public void setPaymentType(BigDecimal paymentType) {
    this.paymentType = paymentType;
  }

  public void setApContactName(String apContactName) {
    this.apContactName = apContactName;
  }

  public void setApContactEmail(String apContactEmail) {
    this.apContactEmail = apContactEmail;
  }

  public void setApContactPhone(String apContactPhone) {
    this.apContactPhone = apContactPhone;
  }

  public void setSupplierName(String supplierName) {
    this.supplierName = supplierName;
  }

  public void setSupplier(String supplier) {
    this.supplier = supplier;
  }

  public void setSupplierPaymentDetailViewBeanCollection(Collection c) {
    this.supplierPaymentDetailViewBeanCollection = c;
  }

  public void addSupplierPaymentDetailViewBeanCollection(SupplierPaymentDetailViewBean b) {
    this.supplierPaymentDetailViewBeanCollection.add(b);
  }
  //getters
  public String getCompany() {
    return company;
  }

  public String getOperatingEntityName() {
    return operatingEntityName;
  }

  public String getPaymentNum() {
    return paymentNum;
  }

  public String getCheckNum() {
    return checkNum;
  }

  public Date getCheckDate() {
    return checkDate;
  }

  public String getVendorCode() {
    return vendorCode;
  }

  public String getPayToCode() {
    return payToCode;
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

  public String getCurrencyCode() {
    return currencyCode;
  }

  public String getPaymentCode() {
    return paymentCode;
  }

  public BigDecimal getPaymentType() {
    return paymentType;
  }

  public String getApContactName() {
    return apContactName;
  }

  public String getApContactEmail() {
    return apContactEmail;
  }

  public String getApContactPhone() {
    return apContactPhone;
  }

  public String getSupplierName() {
    return supplierName;
  }

  public String getSupplier() {
    return supplier;
  }

  public Collection getSupplierPaymentDetailViewBeanCollection() {
    return this.supplierPaymentDetailViewBeanCollection;
  }
}