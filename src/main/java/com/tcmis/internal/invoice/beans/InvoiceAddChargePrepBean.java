package com.tcmis.internal.invoice.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: InvoiceAddChargePrepBean <br>
 * @version: 1.0, Mar 9, 2005 <br>
 *****************************************************************************/

public class InvoiceAddChargePrepBean
    extends BaseDataBean {

  private BigDecimal invoice;
  private BigDecimal invoiceLine;
  private BigDecimal issueId;
  private String companyId;
  private String facilityId;
  private String accountSysId;
  private String accountNumber;
  private String accountNumber2;
  private String poNumber;
  private String itemType;
  private BigDecimal prNumber;
  private String lineItem;
  private String addChargeDesc;
  private BigDecimal addChargeAmount;
  private BigDecimal addChargeItemId;
  private Date dateInserted;
  private String chargeType;
  private BigDecimal issueCostRevision;
  private String salesTaxApplied;

  //constructor
  public InvoiceAddChargePrepBean() {
  }

  //setters
  public void setInvoice(BigDecimal invoice) {
    this.invoice = invoice;
  }

  public void setInvoiceLine(BigDecimal invoiceLine) {
    this.invoiceLine = invoiceLine;
  }

  public void setIssueId(BigDecimal issueId) {
    this.issueId = issueId;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public void setFacilityId(String facilityId) {
    this.facilityId = facilityId;
  }

  public void setAccountSysId(String accountSysId) {
    this.accountSysId = accountSysId;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public void setAccountNumber2(String accountNumber2) {
    this.accountNumber2 = accountNumber2;
  }

  public void setPoNumber(String poNumber) {
    this.poNumber = poNumber;
  }

  public void setItemType(String itemType) {
    this.itemType = itemType;
  }

  public void setPrNumber(BigDecimal prNumber) {
    this.prNumber = prNumber;
  }

  public void setLineItem(String lineItem) {
    this.lineItem = lineItem;
  }

  public void setAddChargeDesc(String addChargeDesc) {
    this.addChargeDesc = addChargeDesc;
  }

  public void setAddChargeAmount(BigDecimal addChargeAmount) {
    this.addChargeAmount = addChargeAmount;
  }

  public void setAddChargeItemId(BigDecimal addChargeItemId) {
    this.addChargeItemId = addChargeItemId;
  }

  public void setDateInserted(Date dateInserted) {
    this.dateInserted = dateInserted;
  }

  public void setChargeType(String chargeType) {
    this.chargeType = chargeType;
  }

  public void setIssueCostRevision(BigDecimal issueCostRevision) {
    this.issueCostRevision = issueCostRevision;
  }

  public void setSalesTaxApplied(String salesTaxApplied) {
    this.salesTaxApplied = salesTaxApplied;
  }

  //getters
  public BigDecimal getInvoice() {
    return invoice;
  }

  public BigDecimal getInvoiceLine() {
    return invoiceLine;
  }

  public BigDecimal getIssueId() {
    return issueId;
  }

  public String getCompanyId() {
    return companyId;
  }

  public String getFacilityId() {
    return facilityId;
  }

  public String getAccountSysId() {
    return accountSysId;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public String getAccountNumber2() {
    return accountNumber2;
  }

  public String getPoNumber() {
    return poNumber;
  }

  public String getItemType() {
    return itemType;
  }

  public BigDecimal getPrNumber() {
    return prNumber;
  }

  public String getLineItem() {
    return lineItem;
  }

  public String getAddChargeDesc() {
    return addChargeDesc;
  }

  public BigDecimal getAddChargeAmount() {
    return addChargeAmount;
  }

  public BigDecimal getAddChargeItemId() {
    return addChargeItemId;
  }

  public Date getDateInserted() {
    return dateInserted;
  }

  public String getChargeType() {
    return chargeType;
  }

  public BigDecimal getIssueCostRevision() {
    return issueCostRevision;
  }

  public String getSalesTaxApplied() {
    return salesTaxApplied;
  }
}