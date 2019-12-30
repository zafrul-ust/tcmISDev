package com.tcmis.internal.invoice.beans;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: InvoiceFormatViewUtc1Bean <br>
 * @version: 1.0, Mar 22, 2005 <br>
 *****************************************************************************/

public class InvoiceFormatViewUtc1Bean
    extends BaseDataBean {

  private BigDecimal invoicePeriod;
  private String companyId;
  private String invoiceGroup;
  private String divisionName;
  private String facilityId;
  private String contactName;
  private String contactPhone;
  private BigDecimal invoice;
  private BigDecimal invoiceAmount;
  private String poNumber;
  private String poLine;
  private String poLineDescription;
  private BigDecimal poLineAmount;
  private Date dateDelivered;
  private Date invoiceDate;
  private Collection detail;

  //constructor
  public InvoiceFormatViewUtc1Bean() {
  }

  //setters
  public void setInvoicePeriod(BigDecimal invoicePeriod) {
    this.invoicePeriod = invoicePeriod;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public void setInvoiceGroup(String invoiceGroup) {
    this.invoiceGroup = invoiceGroup;
  }

  public void setDivisionName(String divisionName) {
    this.divisionName = divisionName;
  }

  public void setFacilityId(String facilityId) {
    this.facilityId = facilityId;
  }

  public void setContactName(String contactName) {
    this.contactName = contactName;
  }

  public void setContactPhone(String contactPhone) {
    this.contactPhone = contactPhone;
  }

  public void setInvoice(BigDecimal invoice) {
    this.invoice = invoice;
  }

  public void setInvoiceAmount(BigDecimal invoiceAmount) {
    this.invoiceAmount = invoiceAmount;
  }

  public void setPoNumber(String poNumber) {
    this.poNumber = poNumber;
  }

  public void setPoLine(String poLine) {
    this.poLine = poLine;
  }

  public void setPoLineDescription(String poLineDescription) {
    this.poLineDescription = poLineDescription;
  }

  public void setPoLineAmount(BigDecimal poLineAmount) {
    this.poLineAmount = poLineAmount;
  }

  public void setDateDelivered(Date date) {
    this.dateDelivered = date;
  }

  public void setInvoiceDate(Date date) {
    this.invoiceDate = date;
  }

  public void setDetail(Collection c) {
    this.detail = c;
  }

  //getters
  public BigDecimal getInvoicePeriod() {
    return invoicePeriod;
  }

  public String getCompanyId() {
    return companyId;
  }

  public String getInvoiceGroup() {
    return invoiceGroup;
  }

  public String getDivisionName() {
    return divisionName;
  }

  public String getFacilityId() {
    return facilityId;
  }

  public String getContactName() {
    return contactName;
  }

  public String getContactPhone() {
    return contactPhone;
  }

  public BigDecimal getInvoice() {
    return invoice;
  }

  public BigDecimal getInvoiceAmount() {
    return invoiceAmount;
  }

  public String getPoNumber() {
    return poNumber;
  }

  public String getPoLine() {
    return poLine;
  }

  public String getPoLineDescription() {
    return poLineDescription;
  }

  public BigDecimal getPoLineAmount() {
    return poLineAmount;
  }

  public Date getDateDelivered() {
    return dateDelivered;
  }

  public Date getInvoiceDate() {
    return invoiceDate;
  }

  public Collection getDetail() {
    return this.detail;
  }

}