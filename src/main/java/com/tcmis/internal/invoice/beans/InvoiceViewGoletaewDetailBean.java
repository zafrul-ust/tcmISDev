package com.tcmis.internal.invoice.beans;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: InvoiceViewGoletaewDetailBean <br>
 * @version: 1.0, Dec 15, 2004 <br>
 *****************************************************************************/

public class InvoiceViewGoletaewDetailBean
    extends BaseDataBean {

  private int invoice;
  private int invoiceLine;
  private String poNumber;
  private int invoicePeriod;
  private String catPartNo;
  private BigDecimal invoiceQuantity;
  private BigDecimal requestedQuantity;
  private BigDecimal invoiceUnitPrice;
  private BigDecimal unitRebate;
  private BigDecimal extendedPrice;
  private BigDecimal rebate;
  private BigDecimal netAmount;
  private BigDecimal totalAddCharge;
  private BigDecimal serviceFee;
  private String partDescription;
  private String requestorName;
  private String requestNumber;
  private BigDecimal netExtPrice;
  private BigDecimal finalExtPrice;
  private BigDecimal invoiceAmount;
  private String mfgDesc;
  private BigDecimal serviceFeePercent;
  private String invoiceSupplier;
  private Date invoiceDate;
  private Collection invoiceAddChargeDetailBeanColl;

  //constructor
  public InvoiceViewGoletaewDetailBean() {
  }

  //setters
  public void setInvoice(int invoice) {
    this.invoice = invoice;
  }

  public void setInvoiceLine(int invoiceLine) {
    this.invoiceLine = invoiceLine;
  }

  public void setPoNumber(String poNumber) {
    this.poNumber = poNumber;
  }

  public void setInvoicePeriod(int invoicePeriod) {
    this.invoicePeriod = invoicePeriod;
  }

  public void setCatPartNo(String catPartNo) {
    this.catPartNo = catPartNo;
  }

  public void setInvoiceQuantity(BigDecimal invoiceQuantity) {
    this.invoiceQuantity = invoiceQuantity;
  }

  public void setRequestedQuantity(BigDecimal requestedQuantity) {
    this.requestedQuantity = requestedQuantity;
  }

  public void setInvoiceUnitPrice(BigDecimal invoiceUnitPrice) {
    this.invoiceUnitPrice = invoiceUnitPrice;
  }

  public void setUnitRebate(BigDecimal unitRebate) {
    this.unitRebate = unitRebate;
  }

  public void setExtendedPrice(BigDecimal extendedPrice) {
    this.extendedPrice = extendedPrice;
  }

  public void setRebate(BigDecimal rebate) {
    this.rebate = rebate;
  }

  public void setNetAmount(BigDecimal netAmount) {
    this.netAmount = netAmount;
  }

  public void setTotalAddCharge(BigDecimal totalAddCharge) {
    this.totalAddCharge = totalAddCharge;
  }

  public void setServiceFee(BigDecimal serviceFee) {
    this.serviceFee = serviceFee;
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

  public void setNetExtPrice(BigDecimal netExtPrice) {
    this.netExtPrice = netExtPrice;
  }

  public void setFinalExtPrice(BigDecimal finalExtPrice) {
    this.finalExtPrice = finalExtPrice;
  }

  public void setInvoiceAmount(BigDecimal invoiceAmount) {
    this.invoiceAmount = invoiceAmount;
  }

  public void setMfgDesc(String mfgDesc) {
    this.mfgDesc = mfgDesc;
  }

  public void setServiceFeePercent(BigDecimal serviceFeePercent) {
    this.serviceFeePercent = serviceFeePercent;
  }

  public void setInvoiceSupplier(String invoiceSupplier) {
    this.invoiceSupplier = invoiceSupplier;
  }

  public void setInvoiceDate(Date invoiceDate) {
    this.invoiceDate = invoiceDate;
  }

  public void setInvoiceAddChargeDetailBeanColl(Collection c) {
    this.invoiceAddChargeDetailBeanColl = c;
  }

  //getters
  public int getInvoice() {
    return invoice;
  }

  public int getInvoiceLine() {
    return invoiceLine;
  }

  public String getPoNumber() {
    return poNumber;
  }

  public int getInvoicePeriod() {
    return invoicePeriod;
  }

  public String getCatPartNo() {
    return catPartNo;
  }

  public BigDecimal getInvoiceQuantity() {
    return invoiceQuantity;
  }

  public BigDecimal getRequestedQuantity() {
    return requestedQuantity;
  }

  public BigDecimal getInvoiceUnitPrice() {
    return invoiceUnitPrice;
  }

  public BigDecimal getUnitRebate() {
    return unitRebate;
  }

  public BigDecimal getExtendedPrice() {
    return extendedPrice;
  }

  public BigDecimal getRebate() {
    return rebate;
  }

  public BigDecimal getNetAmount() {
    return netAmount;
  }

  public BigDecimal getTotalAddCharge() {
    return totalAddCharge;
  }

  public BigDecimal getServiceFee() {
    return serviceFee;
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

  public BigDecimal getNetExtPrice() {
    return netExtPrice;
  }

  public BigDecimal getFinalExtPrice() {
    return finalExtPrice;
  }

  public BigDecimal getInvoiceAmount() {
    return invoiceAmount;
  }

  public String getMfgDesc() {
    return mfgDesc;
  }

  public BigDecimal getServiceFeePercent() {
    return serviceFeePercent;
  }

  public String getInvoiceSupplier() {
    return invoiceSupplier;
  }

  public Date getInvoiceDate() {
    return invoiceDate;
  }

  public Collection getInvoiceAddChargeDetailBeanColl() {
    return this.invoiceAddChargeDetailBeanColl;
  }
}