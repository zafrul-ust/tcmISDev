package com.tcmis.internal.invoice.beans;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: InvoiceViewDanaBean <br>
 * @version: 1.0, Mar 3, 2005 <br>
 *****************************************************************************/

public class InvoiceViewArvinMeritorBean
    extends BaseDataBean {

  private BigDecimal invoice;
  private BigDecimal materialAmount;
  private BigDecimal serviceAmount;
  private String poNumber;
  private Date invoiceDate;
  private String facilityId;
  private BigDecimal invoiceAmount;
  private BigDecimal invoicePeriod;
  private String invoiceGroup;
  private Collection detail = new Vector();

  //constructor
  public InvoiceViewArvinMeritorBean() {
  }

  //setters
  public void setInvoice(BigDecimal invoice) {
    this.invoice = invoice;
  }

  public void setMaterialAmount(BigDecimal materialAmount) {
    this.materialAmount = materialAmount;
  }

  public void setServiceAmount(BigDecimal serviceAmount) {
    this.serviceAmount = serviceAmount;
  }

  public void setPoNumber(String poNumber) {
    this.poNumber = poNumber;
  }

  public void setInvoiceDate(Date invoiceDate) {
    this.invoiceDate = invoiceDate;
  }

  public void setFacilityId(String facilityId) {
    this.facilityId = facilityId;
  }

  public void setInvoiceAmount(BigDecimal invoiceAmount) {
    this.invoiceAmount = invoiceAmount;
  }

  public void setInvoicePeriod(BigDecimal invoicePeriod) {
    this.invoicePeriod = invoicePeriod;
  }

  public void setInvoiceGroup(String invoiceGroup) {
    this.invoiceGroup = invoiceGroup;
  }

  public void setDetail(Collection c) {
    this.detail = c;
  }
  //getters
  public BigDecimal getInvoice() {
    return invoice;
  }

  public BigDecimal getMaterialAmount() {
    return materialAmount;
  }

  public BigDecimal getServiceAmount() {
    return serviceAmount;
  }

  public String getPoNumber() {
    return poNumber;
  }

  public Date getInvoiceDate() {
    return invoiceDate;
  }

  public String getFacilityId() {
    return facilityId;
  }

  public BigDecimal getInvoiceAmount() {
    return invoiceAmount;
  }

  public BigDecimal getInvoicePeriod() {
    return invoicePeriod;
  }

  public String getInvoiceGroup() {
    return invoiceGroup;
  }

  public Collection getDetail() {
    return this.detail;
  }
}
