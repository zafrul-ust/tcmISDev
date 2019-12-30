package com.tcmis.internal.invoice.beans;

import java.util.Collection;
import java.util.Date;
import java.util.Vector;
import java.math.BigDecimal;
import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: InvoiceFormatViewNalcoBean <br>
 * @version: 1.0, Oct 5, 2007 <br>
 *****************************************************************************/

public class InvoiceFormatViewNalcoBean
    extends BaseDataBean {

  private String invoiceGroup;
  private BigDecimal invoicePeriod;
  private BigDecimal invoice;
  private Date invoiceDate;
  private String facilityId;
  private String poNumber;
  private String catPartNo;
  private String partDescription;
  private BigDecimal quantity;
  private String uom;
  private BigDecimal uomPrice;
  private BigDecimal totalPrice;
  Collection lineCollection = new Vector();
  //constructor
  public InvoiceFormatViewNalcoBean() {
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

  public void setFacilityId(String facilityId) {
    this.facilityId = facilityId;
  }

  public void setPoNumber(String poNumber) {
    this.poNumber = poNumber;
  }

  public void setCatPartNo(String catPartNo) {
    this.catPartNo = catPartNo;
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

  public void setLineCollection(Collection c) {
    this.lineCollection = c;
  }

  public void addLineBean(InvoiceFormatViewNalcoBean bean) {
    this.lineCollection.add(bean);
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

  public String getFacilityId() {
    return facilityId;
  }

  public String getPoNumber() {
    return poNumber;
  }

  public String getCatPartNo() {
    return catPartNo;
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

  public Collection getLineCollection() {
    return this.lineCollection;
  }
}