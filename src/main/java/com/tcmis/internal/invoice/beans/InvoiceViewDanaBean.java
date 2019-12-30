package com.tcmis.internal.invoice.beans;

import java.util.Collection;
import java.util.Date;
import java.util.Vector;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: InvoiceViewDanaBean <br>
 * @version: 1.0, Mar 6, 2007 <br>
 *****************************************************************************/

public class InvoiceViewDanaBean
    extends BaseDataBean {
  private String locationDesc;
  private String addressLine1;
  private String addressLine2;
  private String addressLine3;
  private String city;
  private String stateAbbrev;
  private String countryAbbrev;
  private String zip;
  private String attention;
  private String invoiceInquiries;
  private String contactPhone;
  private String contactEmail;
  private BigDecimal invoice;
  private String poNumber;
  private Date invoiceDate;
  private String facilityId;
  private BigDecimal invoiceAmount;
  private BigDecimal invoicePeriod;
  private String invoiceGroup;
  private BigDecimal lineAmount;
  private String lineDescription;

  private BigDecimal netAmount;
  private String partDescription;

  //this is used for cg invoices
  private Collection lineBeanCollection = new Vector();
  //constructor
  public InvoiceViewDanaBean() {
  }  
  //setters
  public void setLocationDesc(String locationDesc) {
	this.locationDesc = locationDesc;
  }	  
  public void setAddressLine1(String addressLine1) {
	this.addressLine1 = addressLine1;
  }  
  public void setAddressLine2(String addressLine2) {
	this.addressLine2 = addressLine2;
  }  
  public void setAddressLine3(String addressLine3) {
	this.addressLine3 = addressLine3;
  }  
  public void setCity(String city) {
	this.city = city;
  }  
  public void setStateAbbrev(String stateAbbrev) {
	this.stateAbbrev = stateAbbrev;
  }  
  public void setCountryAbbrev(String countryAbbrev) {
	this.countryAbbrev = countryAbbrev;
  }  
  public void setZip(String zip) {
	this.zip = zip;
  }  
  public void setAttention(String attention) {
	this.attention = attention;
  }  
  public void setInvoiceInquiries(String invoiceInquiries) {
	this.invoiceInquiries = invoiceInquiries;
  }  
  public void setContactPhone(String contactPhone) {
	this.contactPhone = contactPhone;
  }  
  public void setContactEmail(String contactEmail) {
	this.contactEmail = contactEmail;
  }  
  public void setInvoice(BigDecimal invoice) {
    this.invoice = invoice;
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
  public void setLineAmount(BigDecimal lineAmount) {
    this.lineAmount = lineAmount;
  }
  public void setLineDescription(String lineDescription) {
    this.lineDescription = lineDescription;
  }
  public void setNetAmount(BigDecimal b) {
    this.netAmount = b;
  }
  public void setPartDescription(String s) {
    this.partDescription = s;
  }
  public void setLineBeanCollection(Collection c) {
    this.lineBeanCollection = c;
  }
  public void addLineBean(InvoiceViewDanaBean b) {
    this.lineBeanCollection.add(b);
  }
  //getters
  public String getLocationDesc() {
	return locationDesc;
  }	
  public String getAddressLine1() {
	return addressLine1;
  }		
  public String getAddressLine2() {
	return addressLine2;
  }	
  public String getAddressLine3() {
	return addressLine3;
  }			
  public String getCity() {
	return city;
  }	
  public String getStateAbbrev() {
	return stateAbbrev;
  }	
  public String getCountryAbbrev() {
	return countryAbbrev;
  }	
  public String getZip() {
	return zip;
  }			
  public String getAttention() {
	return attention;
  }	
  public String getInvoiceInquiries() {
	return invoiceInquiries;
  }	
  public String getContactPhone() {
	return contactPhone;
  }		
  public String getContactEmail() {
	return contactEmail;
  }	
  public BigDecimal getInvoice() {
    return invoice;
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
  public BigDecimal getLineAmount() {
    return lineAmount;
  }
  public String getLineDescription() {
    return lineDescription;
  }
  public BigDecimal getNetAmount() {
    return this.netAmount;
  }
  public String getPartDescription() {
    return this.partDescription;
  }
  public Collection getLineBeanCollection() {
    return this.lineBeanCollection;
  }
}