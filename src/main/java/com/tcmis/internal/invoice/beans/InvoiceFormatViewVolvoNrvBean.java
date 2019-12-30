package com.tcmis.internal.invoice.beans;

import java.util.*;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: InvoiceFormatViewVolvoNrvBean <br>
 * @version: 1.0, Jun 28, 2007 <br>
 *****************************************************************************/

public class InvoiceFormatViewVolvoNrvBean
    extends BaseDataBean {

  private String invoiceGroup;
  private BigDecimal invoicePeriod;
  private BigDecimal invoice;
  private Date invoiceDate;
  private String poNumber;
  private String partDescription;
  private BigDecimal quantity;
  private String uom;
  private BigDecimal uomPrice;
  private BigDecimal totalPrice;
  private String catPartNo;
  private BigDecimal haasPo;

  private Collection lineBeanCollection = new Vector();

  //constructor
  public InvoiceFormatViewVolvoNrvBean() {
  }

  //setters
  public void setInvoiceGroup(String invoiceGroup) {
    this.invoiceGroup = invoiceGroup;
  }

  public void setInvoicePeriod(BigDecimal invoicePeriod) {
    this.invoicePeriod = invoicePeriod;
  }

  public void setInvoice(BigDecimal invoice) {
    this.invoice = invoice;
  }

  public void setInvoiceDate(Date invoiceDate) {
    this.invoiceDate = invoiceDate;
  }

  public void setPoNumber(String poNumber) {
    this.poNumber = poNumber;
  }

  public void setPartDescription(String partDescription) {
    this.partDescription = partDescription;
  }

  public void setQuantity(BigDecimal quantity) {
    this.quantity = quantity;
  }

  public void setUom(String uom) {
    this.uom = uom;
  }

  public void setUomPrice(BigDecimal uomPrice) {
    this.uomPrice = uomPrice;
  }

  public void setTotalPrice(BigDecimal totalPrice) {
    this.totalPrice = totalPrice;
  }

  public void setCatPartNo(String catPartNo) {
	this.catPartNo = catPartNo;
}

public void setHaasPo(BigDecimal haasPo) {
	this.haasPo = haasPo;
}

public void setLineBeanCollection(Collection c) {
    this.lineBeanCollection = c;
  }

  public void addLineBean(InvoiceFormatViewVolvoNrvBean b) {
    this.lineBeanCollection.add(b);
  }
  //getters
  public String getInvoiceGroup() {
    return invoiceGroup;
  }

  public BigDecimal getInvoicePeriod() {
    return invoicePeriod;
  }

  public BigDecimal getInvoice() {
    return invoice;
  }

  public Date getInvoiceDate() {
    return invoiceDate;
  }

  public String getPoNumber() {
    return poNumber;
  }

  public String getPartDescription() {
    return partDescription;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public String getUom() {
    return uom;
  }

  public BigDecimal getUomPrice() {
    return uomPrice;
  }

  public BigDecimal getTotalPrice() {
    return totalPrice;
  }

  public String getCatPartNo() {
	return catPartNo;
}

public BigDecimal getHaasPo() {
	return haasPo;
}

public Collection getLineBeanCollection() {
    return this.lineBeanCollection;
  }
}