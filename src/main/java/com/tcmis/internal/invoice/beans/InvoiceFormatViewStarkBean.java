package com.tcmis.internal.invoice.beans;

import java.util.Collection;
import java.util.Date;
import java.util.Vector;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: InvoiceFormatViewStarkBean <br>
 * @version: 1.0, Aug 10, 2007 <br>
 *****************************************************************************/

public class InvoiceFormatViewStarkBean
    extends BaseDataBean {

  private BigDecimal invoicePeriod;
  private BigDecimal invoice;
  private Date invoiceDate;
  private BigDecimal invoiceAmount;
  private String poNumber;
  private BigDecimal prNumber;
  private String lineItem;
  private String catPartNo;
  private Date dateDelivered;
  private BigDecimal quantity;
  private String unitOfSale;
  private BigDecimal unitOfSalePrice;
  private BigDecimal addCharge;
  private BigDecimal netAmount;

  Collection lineCollection = new Vector();

  //constructor
  public InvoiceFormatViewStarkBean() {
  }

  //setters
  public void setInvoicePeriod(BigDecimal invoicePeriod) {
    this.invoicePeriod = invoicePeriod;
  }

  public void setInvoice(BigDecimal invoice) {
    this.invoice = invoice;
  }

  public void setInvoiceDate(Date invoiceDate) {
    this.invoiceDate = invoiceDate;
  }

  public void setInvoiceAmount(BigDecimal invoiceAmount) {
    this.invoiceAmount = invoiceAmount;
  }

  public void setPoNumber(String poNumber) {
    this.poNumber = poNumber;
  }

  public void setPrNumber(BigDecimal prNumber) {
    this.prNumber = prNumber;
  }

  public void setLineItem(String lineItem) {
    this.lineItem = lineItem;
  }

  public void setCatPartNo(String catPartNo) {
    this.catPartNo = catPartNo;
  }

  public void setDateDelivered(Date dateDelivered) {
    this.dateDelivered = dateDelivered;
  }

  public void setQuantity(BigDecimal quantity) {
    this.quantity = quantity;
  }

  public void setUnitOfSale(String unitOfSale) {
    this.unitOfSale = unitOfSale;
  }

  public void setUnitOfSalePrice(BigDecimal unitOfSalePrice) {
    this.unitOfSalePrice = unitOfSalePrice;
  }

  public void setAddCharge(BigDecimal addCharge) {
    this.addCharge = addCharge;
  }

  public void setNetAmount(BigDecimal netAmount) {
    this.netAmount = netAmount;
  }

  public void setLineCollection(Collection c) {
    this.lineCollection = c;
  }

  public void addLineBean(InvoiceFormatViewStarkBean bean) {
    this.lineCollection.add(bean);
  }
  //getters
  public BigDecimal getInvoicePeriod() {
    return invoicePeriod;
  }

  public BigDecimal getInvoice() {
    return invoice;
  }

  public Date getInvoiceDate() {
    return invoiceDate;
  }

  public BigDecimal getInvoiceAmount() {
    return invoiceAmount;
  }

  public String getPoNumber() {
    return poNumber;
  }

  public BigDecimal getPrNumber() {
    return prNumber;
  }

  public String getLineItem() {
    return lineItem;
  }

  public String getCatPartNo() {
    return catPartNo;
  }

  public Date getDateDelivered() {
    return dateDelivered;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public String getUnitOfSale() {
    return unitOfSale;
  }

  public BigDecimal getUnitOfSalePrice() {
    return unitOfSalePrice;
  }

  public BigDecimal getAddCharge() {
    return addCharge;
  }

  public BigDecimal getNetAmount() {
    return netAmount;
  }

  public Collection getLineCollection() {
    return this.lineCollection;
  }
}