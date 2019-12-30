package com.tcmis.internal.invoice.beans;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: InvoiceViewBoeingOffsiteBean <br>
 * @version: 1.0, Mar 3, 2005 <br>
 *****************************************************************************/

public class InvoiceViewBoeingOffsiteBean
    extends BaseDataBean {

  private BigDecimal invoice;
  private String facilityId;
  private String application;
  private String workOrder;
  private String po;
  private BigDecimal quantity;
  private BigDecimal unitCost;
  private BigDecimal extendedCost;
  private BigDecimal additionalCharge;
  private BigDecimal invoicePeriod;
  private Date invoiceDate;
  private Collection detailCollection;

  //constructor
  public InvoiceViewBoeingOffsiteBean() {
  }

  //setters
  public void setInvoice(BigDecimal invoice) {
    this.invoice = invoice;
  }

  public void setFacilityId(String facilityId) {
    this.facilityId = facilityId;
  }

  public void setApplication(String application) {
    this.application = application;
  }

  public void setWorkOrder(String workOrder) {
    this.workOrder = workOrder;
  }

  public void setPo(String po) {
    this.po = po;
  }

  public void setQuantity(BigDecimal quantity) {
    this.quantity = quantity;
  }

  public void setUnitCost(BigDecimal unitCost) {
    this.unitCost = unitCost;
  }

  public void setExtendedCost(BigDecimal extendedCost) {
    this.extendedCost = extendedCost;
  }

  public void setAdditionalCharge(BigDecimal additionalCharge) {
    this.additionalCharge = additionalCharge;
  }

  public void setInvoicePeriod(BigDecimal invoicePeriod) {
    this.invoicePeriod = invoicePeriod;
  }

  public void setInvoiceDate(Date invoiceDate) {
    this.invoiceDate = invoiceDate;
  }

  public void setDetailCollection(Collection c) {
    this.detailCollection = c;
  }
  //getters
  public BigDecimal getInvoice() {
    return invoice;
  }

  public String getFacilityId() {
    return facilityId;
  }

  public String getApplication() {
    return application;
  }

  public String getWorkOrder() {
    return workOrder;
  }

  public String getPo() {
    return po;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public BigDecimal getUnitCost() {
    return unitCost;
  }

  public BigDecimal getExtendedCost() {
    return extendedCost;
  }

  public BigDecimal getAdditionalCharge() {
    return additionalCharge;
  }

  public BigDecimal getInvoicePeriod() {
    return invoicePeriod;
  }

  public Date getInvoiceDate() {
    return invoiceDate;
  }

  public Collection getDetailCollection() {
    return detailCollection;
  }
}