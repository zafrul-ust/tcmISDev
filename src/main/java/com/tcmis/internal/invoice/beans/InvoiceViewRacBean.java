package com.tcmis.internal.invoice.beans;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: InvoiceViewRacBean <br>
 * @version: 1.0, Mar 9, 2005 <br>
 *****************************************************************************/

public class InvoiceViewRacBean
    extends BaseDataBean {

  private BigDecimal invoice;
  private BigDecimal invoiceLine;
  private String poNumber;
  private String catPartNo;
  private BigDecimal invoiceQuantity;
  private BigDecimal orderedQuantity;
  private BigDecimal racLpp;
  private BigDecimal unitPrice;
  private BigDecimal unitPriceSaving;
  private BigDecimal haasGainShare;
  private BigDecimal extendedPrice;
  private BigDecimal totalHaasGainShare;
  private BigDecimal totalAmountDue;
  private BigDecimal totalRacGainShare;
  private String partDescription;
  private String requestorName;
  private String requestNumber;
  private String unitOfSale;
  private BigDecimal unitOfSaleQuantityPerEach;
  private String feeForWeek;
  private BigDecimal totalSavings;
  private BigDecimal racGainShare;
  private BigDecimal haasUnitPrice;
  private Date invoiceDate;
  private BigDecimal invoicePeriod;
  private Collection invoiceAddChargeDetailBeanColl;

  //constructor
  public InvoiceViewRacBean() {
  }

  //setters
  public void setInvoice(BigDecimal invoice) {
    this.invoice = invoice;
  }

  public void setInvoiceLine(BigDecimal invoiceLine) {
    this.invoiceLine = invoiceLine;
  }

  public void setPoNumber(String poNumber) {
    this.poNumber = poNumber;
  }

  public void setCatPartNo(String catPartNo) {
    this.catPartNo = catPartNo;
  }

  public void setInvoiceQuantity(BigDecimal invoiceQuantity) {
    this.invoiceQuantity = invoiceQuantity;
  }

  public void setOrderedQuantity(BigDecimal orderedQuantity) {
    this.orderedQuantity = orderedQuantity;
  }

  public void setRacLpp(BigDecimal racLpp) {
    this.racLpp = racLpp;
  }

  public void setUnitPrice(BigDecimal unitPrice) {
    this.unitPrice = unitPrice;
  }

  public void setUnitPriceSaving(BigDecimal unitPriceSaving) {
    this.unitPriceSaving = unitPriceSaving;
  }

  public void setHaasGainShare(BigDecimal haasGainShare) {
    this.haasGainShare = haasGainShare;
  }

  public void setExtendedPrice(BigDecimal extendedPrice) {
    this.extendedPrice = extendedPrice;
  }

  public void setTotalHaasGainShare(BigDecimal totalHaasGainShare) {
    this.totalHaasGainShare = totalHaasGainShare;
  }

  public void setTotalAmountDue(BigDecimal totalAmountDue) {
    this.totalAmountDue = totalAmountDue;
  }

  public void setTotalRacGainShare(BigDecimal totalRacGainShare) {
    this.totalRacGainShare = totalRacGainShare;
  }

  public void setPartDescription(String partDescription) {
    this.partDescription = partDescription;
  }

  public void setRequestorName(String requestorName) {
    this.requestorName = requestorName;
  }

  public void setRequestNumber(String requestNumber) {
    this.requestNumber = requestNumber;
  }

  public void setUnitOfSale(String unitOfSale) {
    this.unitOfSale = unitOfSale;
  }

  public void setUnitOfSaleQuantityPerEach(BigDecimal unitOfSaleQuantityPerEach) {
    this.unitOfSaleQuantityPerEach = unitOfSaleQuantityPerEach;
  }

  public void setFeeForWeek(String feeForWeek) {
    this.feeForWeek = feeForWeek;
  }

  public void setTotalSavings(BigDecimal totalSavings) {
    this.totalSavings = totalSavings;
  }

  public void setRacGainShare(BigDecimal racGainShare) {
    this.racGainShare = racGainShare;
  }

  public void setHaasUnitPrice(BigDecimal haasUnitPrice) {
    this.haasUnitPrice = haasUnitPrice;
  }

  public void setInvoiceDate(Date invoiceDate) {
    this.invoiceDate = invoiceDate;
  }

  public void setInvoicePeriod(BigDecimal invoicePeriod) {
    this.invoicePeriod = invoicePeriod;
  }

  public void setInvoiceAddChargeDetailBeanColl(Collection collection) {
    this.invoiceAddChargeDetailBeanColl = collection;
  }

  //getters
  public BigDecimal getInvoice() {
    return invoice;
  }

  public BigDecimal getInvoiceLine() {
    return invoiceLine;
  }

  public String getPoNumber() {
    return poNumber;
  }

  public String getCatPartNo() {
    return catPartNo;
  }

  public BigDecimal getInvoiceQuantity() {
    return invoiceQuantity;
  }

  public BigDecimal getOrderedQuantity() {
    return orderedQuantity;
  }

  public BigDecimal getRacLpp() {
    return racLpp;
  }

  public BigDecimal getUnitPrice() {
    return unitPrice;
  }

  public BigDecimal getUnitPriceSaving() {
    return unitPriceSaving;
  }

  public BigDecimal getHaasGainShare() {
    return haasGainShare;
  }

  public BigDecimal getExtendedPrice() {
    return extendedPrice;
  }

  public BigDecimal getTotalHaasGainShare() {
    return totalHaasGainShare;
  }

  public BigDecimal getTotalAmountDue() {
    return totalAmountDue;
  }

  public BigDecimal getTotalRacGainShare() {
    return totalRacGainShare;
  }

  public String getPartDescription() {
    return partDescription;
  }

  public String getRequestorName() {
    return requestorName;
  }

  public String getRequestNumber() {
    return requestNumber;
  }

  public String getUnitOfSale() {
    return unitOfSale;
  }

  public BigDecimal getUnitOfSaleQuantityPerEach() {
    return unitOfSaleQuantityPerEach;
  }

  public String getFeeForWeek() {
    return feeForWeek;
  }

  public BigDecimal getTotalSavings() {
    return totalSavings;
  }

  public BigDecimal getRacGainShare() {
    return racGainShare;
  }

  public BigDecimal getHaasUnitPrice() {
    return haasUnitPrice;
  }

  public Date getInvoiceDate() {
    return invoiceDate;
  }

  public BigDecimal getInvoicePeriod() {
    return invoicePeriod;
  }

  public Collection getInvoiceAddChargeDetailBeanColl() {
    return this.invoiceAddChargeDetailBeanColl;
  }
}