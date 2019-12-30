package com.tcmis.internal.invoice.beans;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: InvoiceBean <br>
 * @version: 1.0, Mar 9, 2005 <br>
 *****************************************************************************/

public class InvoiceBean
    extends BaseDataBean {

  private BigDecimal invoice;
  private String companyId;
  private String facilityId;
  private String accountSysId;
  private BigDecimal invoiceAmount;
  private Date invoiceDate;
  private Date transactionDate;
  private BigDecimal invoicePeriod;
  private String accountSysLabel;
  private String locationLabel;
  private String invoiceGroup;
  private String poNumber;
  private String commodity;
  private BigDecimal originalInvoice;
  private BigDecimal creditInvoice;
  private BigDecimal creditCardNumber;
  private Date creditCardExpirationDate;
  private Date startDate;
  private Date endDate;
  private BigDecimal invoiceNumber;
  private BigDecimal invoiceCount;
  private Collection invoiceLineCollection;
  private Collection invoiceMillerCollection;
  private Collection invoiceSyracuseCollection;
  private Collection invoiceQosCollection;
  private Collection invoiceDetailCollection;
  private String  	 invoiceSuffix;
  private String partDescription;

  //this is used for fec invoices
  private String referenceNumber;
  private BigDecimal count;
  private String hardcode;

  //constructor
  public InvoiceBean() {
  }

  //setters
  public void setInvoice(BigDecimal invoice) {
    this.invoice = invoice;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public void setFacilityId(String facilityId) {
    this.facilityId = facilityId;
  }

  public void setAccountSysId(String accountSysId) {
    this.accountSysId = accountSysId;
  }

  public void setInvoiceAmount(BigDecimal invoiceAmount) {
    this.invoiceAmount = invoiceAmount;
  }

  public void setInvoiceDate(Date invoiceDate) {
    this.invoiceDate = invoiceDate;
  }

  public void setTransactionDate(Date transactionDate) {
    this.transactionDate = transactionDate;
  }

  public void setInvoicePeriod(BigDecimal invoicePeriod) {
    this.invoicePeriod = invoicePeriod;
  }

  public void setAccountSysLabel(String accountSysLabel) {
    this.accountSysLabel = accountSysLabel;
  }

  public void setLocationLabel(String locationLabel) {
    this.locationLabel = locationLabel;
  }

  public void setInvoiceGroup(String invoiceGroup) {
    this.invoiceGroup = invoiceGroup;
  }

  public void setPoNumber(String poNumber) {
    this.poNumber = poNumber;
  }

  public void setCommodity(String commodity) {
    this.commodity = commodity;
  }

  public void setOriginalInvoice(BigDecimal originalInvoice) {
    this.originalInvoice = originalInvoice;
  }

  public void setCreditInvoice(BigDecimal creditInvoice) {
    this.creditInvoice = creditInvoice;
  }

  public void setCreditCardNumber(BigDecimal creditCardNumber) {
    this.creditCardNumber = creditCardNumber;
  }

  public void setCreditCardExpirationDate(Date creditCardExpirationDate) {
    this.creditCardExpirationDate = creditCardExpirationDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public void setInvoiceNumber(BigDecimal invoiceNumber) {
    this.invoiceNumber = invoiceNumber;
  }

  public void setInvoiceCount(BigDecimal invoiceCount) {
    this.invoiceCount = invoiceCount;
  }

  public void setInvoiceLineCollection(Collection c) {
    this.invoiceLineCollection = c;
  }

  public void setInvoiceMillerCollection(Collection c) {
    this.invoiceMillerCollection = c;
  }

  public void setInvoiceSyracuseCollection(Collection c) {
    this.invoiceSyracuseCollection = c;
  }

  public void setInvoiceQosCollection(Collection c) {
    this.invoiceQosCollection = c;
  }

  public void setInvoiceDetailCollection(Collection c) {
    this.invoiceDetailCollection = c;
  }

  public void setReferenceNumber(String s) {
    this.referenceNumber = s;
  }

  public void setCount(BigDecimal b) {
    this.count = b;
  }
  //getters
  public BigDecimal getInvoice() {
    return invoice;
  }

  public String getCompanyId() {
    return companyId;
  }

  public String getFacilityId() {
    return facilityId;
  }

  public String getAccountSysId() {
    return accountSysId;
  }

  public BigDecimal getInvoiceAmount() {
    return invoiceAmount;
  }

  public Date getInvoiceDate() {
    return invoiceDate;
  }

  public Date getTransactionDate() {
    return transactionDate;
  }

  public BigDecimal getInvoicePeriod() {
    return invoicePeriod;
  }

  public String getAccountSysLabel() {
    return accountSysLabel;
  }

  public String getLocationLabel() {
    return locationLabel;
  }

  public String getInvoiceGroup() {
    return invoiceGroup;
  }

  public String getPoNumber() {
    return poNumber;
  }

  public String getCommodity() {
    return commodity;
  }

  public BigDecimal getOriginalInvoice() {
    return originalInvoice;
  }

  public BigDecimal getCreditInvoice() {
    return creditInvoice;
  }

  public BigDecimal getCreditCardNumber() {
    return creditCardNumber;
  }

  public Date getCreditCardExpirationDate() {
    return creditCardExpirationDate;
  }

  public Date getStartDate() {
    return startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public BigDecimal getInvoiceNumber() {
    return invoiceNumber;
  }
  
  public BigDecimal getInvoiceCount() {
    return invoiceCount;
  }
  public Collection getInvoiceLineCollection() {
    return this.invoiceLineCollection;
  }

  public Collection getInvoiceMillerCollection() {
    return this.invoiceMillerCollection;
  }

  public Collection getInvoiceSyracuseCollection() {
    return this.invoiceSyracuseCollection;
  }

  public Collection getInvoiceQosCollection() {
    return this.invoiceQosCollection;
  }

  public Collection getInvoiceDetailCollection() {
    return this.invoiceDetailCollection;
  }

  public String getReferenceNumber() {
    return this.referenceNumber;
  }

  public BigDecimal getCount() {
    return this.count;
  }

public String getInvoiceSuffix() {
	return invoiceSuffix;
}

public void setInvoiceSuffix(String invoiceSuffix) {
	this.invoiceSuffix = invoiceSuffix;
}

public String getHardcode() {
	return hardcode;
}

public void setHardcode(String hardcode) {
	this.hardcode = hardcode;
}

public String getPartDescription() {
	return partDescription;
}

public void setPartDescription(String partDescription) {
	this.partDescription = partDescription;
}
}