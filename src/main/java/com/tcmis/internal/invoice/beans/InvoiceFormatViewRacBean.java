package com.tcmis.internal.invoice.beans;

import java.util.Collection;
import java.util.Date;
import java.util.Vector;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: Sbuffum.invoiceFormatViewRacBean <br>
 * @version: 1.0, May 1, 2006 <br>
 *****************************************************************************/

public class InvoiceFormatViewRacBean
    extends BaseDataBean {

  private BigDecimal invoice;
  private BigDecimal invoiceLine;
  private String poNumber;
  private String catPartNo;
  private String quantity;
  private String requestQuantity;
  private String rawInvoiceUnitPrice;
  private String adjInvoiceUnitPrice;
  private String unitPriceSavings;
  private String unitRebate;
  private String extendedPrice;
  private String haasGainshare;
  private String netAmount;
  private String netRebate;
  private String partDescription;
  private String requestorName;
  private String mrNumber;
  private String invoiceDate;
  private String unitOfSale;
  private BigDecimal qtyPerEach;
  private String serviceFeeDate;
  private String netSavings;
  private String racUnitGainshare;
  private String unitCost;
  private String invoiceDateStr;
  private BigDecimal invoicePeriod;
  private String baselinePrice;
  private String releaseNumber;
  private String dateDelivered;
  private String mfgLot;

  private Collection addChargeBeanCollection = new Vector();

  private BigDecimal totalAddCharge;
  private Date dateDeliveredDate;
  private BigDecimal invoiceAmount;
  private Date invoiceDateDate;
  //constructor
  public InvoiceFormatViewRacBean() {
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

  public void setQuantity(String quantity) {
    this.quantity = quantity;
  }

  public void setRequestQuantity(String requestQuantity) {
    this.requestQuantity = requestQuantity;
  }

  public void setRawInvoiceUnitPrice(String rawInvoiceUnitPrice) {
    this.rawInvoiceUnitPrice = rawInvoiceUnitPrice;
  }

  public void setAdjInvoiceUnitPrice(String adjInvoiceUnitPrice) {
    this.adjInvoiceUnitPrice = adjInvoiceUnitPrice;
  }

  public void setUnitPriceSavings(String unitPriceSavings) {
    this.unitPriceSavings = unitPriceSavings;
  }

  public void setUnitRebate(String unitRebate) {
    this.unitRebate = unitRebate;
  }

  public void setExtendedPrice(String extendedPrice) {
    this.extendedPrice = extendedPrice;
  }

  public void setHaasGainshare(String haasGainshare) {
    this.haasGainshare = haasGainshare;
  }

  public void setNetAmount(String netAmount) {
    this.netAmount = netAmount;
  }

  public void setNetRebate(String netRebate) {
    this.netRebate = netRebate;
  }

  public void setPartDescription(String partDescription) {
    this.partDescription = partDescription;
  }

  public void setRequestorName(String requestorName) {
    this.requestorName = requestorName;
  }

  public void setMrNumber(String mrNumber) {
    this.mrNumber = mrNumber;
  }

  public void setInvoiceDate(String invoiceDate) {
    this.invoiceDate = invoiceDate;
  }

  public void setUnitOfSale(String unitOfSale) {
    this.unitOfSale = unitOfSale;
  }

  public void setQtyPerEach(BigDecimal qtyPerEach) {
    this.qtyPerEach = qtyPerEach;
  }

  public void setServiceFeeDate(String serviceFeeDate) {
    this.serviceFeeDate = serviceFeeDate;
  }

  public void setNetSavings(String netSavings) {
    this.netSavings = netSavings;
  }

  public void setRacUnitGainshare(String racUnitGainshare) {
    this.racUnitGainshare = racUnitGainshare;
  }

  public void setUnitCost(String unitCost) {
    this.unitCost = unitCost;
  }

  public void setInvoiceDateStr(String invoiceDateStr) {
    this.invoiceDateStr = invoiceDateStr;
  }

  public void setInvoicePeriod(BigDecimal invoicePeriod) {
    this.invoicePeriod = invoicePeriod;
  }

  public void setBaselinePrice(String baselinePrice) {
    this.baselinePrice = baselinePrice;
  }

  public void setReleaseNumber(String releaseNumber) {
    this.releaseNumber = releaseNumber;
  }

  public void setDateDelivered(String dateDelivered) {
    this.dateDelivered = dateDelivered;
  }

  public void setMfgLot(String mfgLot) {
    this.mfgLot = mfgLot;
  }

  public void setAddChargeBeanCollection(Collection c) {
    this.addChargeBeanCollection = c;
  }

  public void setTotalAddCharge(BigDecimal b) {
    this.totalAddCharge = b;
  }

  public void setDateDeliveredDate(Date d) {
    this.dateDeliveredDate = d;
  }

  public void setInvoiceAmount(BigDecimal b) {
    this.invoiceAmount = b;
  }

  public void setInvoiceDateDate(Date invoiceDate) {
    this.invoiceDateDate = invoiceDate;
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

  public String getQuantity() {
    return quantity;
  }

  public String getRequestQuantity() {
    return requestQuantity;
  }

  public String getRawInvoiceUnitPrice() {
    return rawInvoiceUnitPrice;
  }

  public String getAdjInvoiceUnitPrice() {
    return adjInvoiceUnitPrice;
  }

  public String getUnitPriceSavings() {
    return unitPriceSavings;
  }

  public String getUnitRebate() {
    return unitRebate;
  }

  public String getExtendedPrice() {
    return extendedPrice;
  }

  public String getHaasGainshare() {
    return haasGainshare;
  }

  public String getNetAmount() {
    return netAmount;
  }

  public String getNetRebate() {
    return netRebate;
  }

  public String getPartDescription() {
    return partDescription;
  }

  public String getRequestorName() {
    return requestorName;
  }

  public String getMrNumber() {
    return mrNumber;
  }

  public String getInvoiceDate() {
    return invoiceDate;
  }

  public String getUnitOfSale() {
    return unitOfSale;
  }

  public BigDecimal getQtyPerEach() {
    return qtyPerEach;
  }

  public String getServiceFeeDate() {
    return serviceFeeDate;
  }

  public String getNetSavings() {
    return netSavings;
  }

  public String getRacUnitGainshare() {
    return racUnitGainshare;
  }

  public String getUnitCost() {
    return unitCost;
  }

  public String getInvoiceDateStr() {
    return invoiceDateStr;
  }

  public BigDecimal getInvoicePeriod() {
    return invoicePeriod;
  }

  public String getBaselinePrice() {
    return baselinePrice;
  }

  public String getReleaseNumber() {
    return releaseNumber;
  }

  public String getDateDelivered() {
    return dateDelivered;
  }

  public String getMfgLot() {
    return mfgLot;
  }

  public Collection getAddChargeBeanCollection() {
    return this.addChargeBeanCollection;
  }

  public BigDecimal getTotalAddCharge() {
    return this.totalAddCharge;
  }

  public Date getDateDeliveredDate() {
    return this.dateDeliveredDate;
  }

  public BigDecimal getInvoiceAmount() {
    return this.invoiceAmount;
  }

  public Date getInvoiceDateDate() {
    return invoiceDateDate;
  }
}