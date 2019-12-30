package com.tcmis.internal.hub.beans;

import java.util.Collection;
import java.util.Date;
import java.util.Vector;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: ShipConfirmViewBean <br>
 * @version: 1.0, Jan 25, 2007 <br>
 *****************************************************************************/

public class ShipConfirmViewBean
    extends BaseDataBean {

	private BigDecimal shipmentId;
	private String carrierCode;
	private String trackingNumber;
	private String inventoryGroup;
	private BigDecimal prNumber;
	private String lineItem;
	private String companyId;
	private String facilityId;
	private String application;
	private String catPartNo;
	private String sourceHub;
	private String sourceHubName;
	private BigDecimal quantity;
//  private String datePicked;
	private Date datePicked;
	private String issuerName;
	private BigDecimal batch;
//  private String deliveredDate;
	private Date deliveredDate;
	private String shipToCompanyId;
	private String shipToLocationId;
	private String hazardCategory;
	private String overShipped;
	private String deliveryPoint;
	private String applicationDesc;
	private String autoShipConfirm;
	private String companyName;
	private String facilityName;
	private BigDecimal customerId;
	private String customerName;
	private BigDecimal customerServiceRepId;
	private String csrName;
	private Date issueQcDate;
	private String issueQcUserName;
	private String currencyId;
	private String materialRequestOrigin;
	private String shipToAddressLine1;
	private String shipToAddressLine2;
	private String shipToAddressLine3;
	private String shipToAddressLine4;
	private String shipToAddressLine5;
	private String paymentTerms;
	private String shipMfgCoc;
	private String shipMfgCoa;
	private String shipMsds;
	private String pickQcComplete;
  
	private String customerNote;
	private String shiptoNote;
	private String prInternalNote;
	private String lineInternalNote;
	private String linePurchasingNote;
  
	private String hazardous;
	private String cms;
	private String distribution;
	private String shippingReference;
	private String printInvoice;
	private String consignmentTransfer;
  
	private String incotermRequired;
	private String incoterm;
	private String modeOfTransport;
	private String invoiceAtShipping;
	private String invoiceBy;
	private String pickingUnitId;
	private BigDecimal boxCount;
	private Date lastProFormaPrintDate;
	private String proFormaRequired;

public String getPrintInvoice() {
	return printInvoice;
}

public void setPrintInvoice(String printInvoice) {
	this.printInvoice = printInvoice;
}

public String getConsignmentTransfer() {
	return consignmentTransfer;
}

public void setConsignmentTransfer(String consignmentTransfer) {
	this.consignmentTransfer = consignmentTransfer;
}

public String getShippingReference() {
	return shippingReference;
}

public void setShippingReference(String shippingReference) {
	this.shippingReference = shippingReference;
}

public String getCms() {
	return cms;
}

public void setCms(String cms) {
	this.cms = cms;
}

public String getDistribution() {
	return distribution;
}

public void setDistribution(String distribution) {
	this.distribution = distribution;
}

public BigDecimal getCustomerId() {
	return customerId;
}

public void setCustomerId(BigDecimal customerId) {
	this.customerId = customerId;
}

public String getCustomerName() {
	return customerName;
}

public void setCustomerName(String customerName) {
	this.customerName = customerName;
}

public BigDecimal getCustomerServiceRepId() {
	return customerServiceRepId;
}

public void setCustomerServiceRepId(BigDecimal customerServiceRepId) {
	this.customerServiceRepId = customerServiceRepId;
}

public String getCsrName() {
	return csrName;
}

public void setCsrName(String csrName) {
	this.csrName = csrName;
}

public Date getIssueQcDate() {
	return issueQcDate;
}

public void setIssueQcDate(Date issueQcDate) {
	this.issueQcDate = issueQcDate;
}

public String getIssueQcUserName() {
	return issueQcUserName;
}

public void setIssueQcUserName(String issueQcUserName) {
	this.issueQcUserName = issueQcUserName;
}

public String getCompanyName() {
	return companyName;
}

public void setCompanyName(String companyName) {
	this.companyName = companyName;
}

public String getFacilityName() {
	return facilityName;
}

public void setFacilityName(String facilityName) {
	this.facilityName = facilityName;
}

private Collection shipmentBeanCollection = new Vector();

  //constructor
  public ShipConfirmViewBean() {
  }

  //setters
  public void setShipmentId(BigDecimal shipmentId) {
    this.shipmentId = shipmentId;
  }

  public void setCarrierCode(String carrierCode) {
    this.carrierCode = carrierCode;
  }

  public void setTrackingNumber(String trackingNumber) {
    this.trackingNumber = trackingNumber;
  }

  public void setInventoryGroup(String inventoryGroup) {
    this.inventoryGroup = inventoryGroup;
  }

  public void setPrNumber(BigDecimal prNumber) {
    this.prNumber = prNumber;
  }

  public void setLineItem(String lineItem) {
    this.lineItem = lineItem;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public void setFacilityId(String facilityId) {
    this.facilityId = facilityId;
  }

  public void setApplication(String application) {
    this.application = application;
  }

  public void setCatPartNo(String catPartNo) {
    this.catPartNo = catPartNo;
  }

  public void setSourceHub(String sourceHub) {
    this.sourceHub = sourceHub;
  }

  public void setSourceHubName(String sourceHubName) {
    this.sourceHubName = sourceHubName;
  }

  public void setQuantity(BigDecimal quantity) {
    this.quantity = quantity;
  }

  public void setDatePicked(Date datePicked) {
    this.datePicked = datePicked;
  }

  public void setIssuerName(String issuerName) {
    this.issuerName = issuerName;
  }

  public void setBatch(BigDecimal batch) {
    this.batch = batch;
  }

  public void setDeliveredDate(Date deliveredDate) {
    this.deliveredDate = deliveredDate;
  }

  public void setShipToCompanyId(String shipToCompanyId) {
    this.shipToCompanyId = shipToCompanyId;
  }

  public void setShipToLocationId(String shipToLocationId) {
    this.shipToLocationId = shipToLocationId;
  }

  public void setHazardCategory(String hazardCategory) {
    this.hazardCategory = hazardCategory;
  }

  public void setOverShipped(String overShipped) {
    this.overShipped = overShipped;
  }

  public void setDeliveryPoint(String deliveryPoint) {
    this.deliveryPoint = deliveryPoint;
  }

  public void setApplicationDesc(String applicationDesc) {
    this.applicationDesc = applicationDesc;
  }

  public void setAutoShipConfirm(String s) {
    this.autoShipConfirm = s;
  }

  public void setShipmentBeanCollection(Collection c) {
    this.shipmentBeanCollection = c;
  }

  //getters
  public BigDecimal getShipmentId() {
    return shipmentId;
  }

  public String getCarrierCode() {
    return carrierCode;
  }

  public String getTrackingNumber() {
    return trackingNumber;
  }

  public String getInventoryGroup() {
    return inventoryGroup;
  }

  public BigDecimal getPrNumber() {
    return prNumber;
  }

  public String getLineItem() {
    return lineItem;
  }

  public String getCompanyId() {
    return companyId;
  }

  public String getFacilityId() {
    return facilityId;
  }

  public String getApplication() {
    return application;
  }

  public String getCatPartNo() {
    return catPartNo;
  }

  public String getSourceHub() {
    return sourceHub;
  }

  public String getSourceHubName() {
    return sourceHubName;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public Date getDatePicked() {
    return datePicked;
  }

  public String getIssuerName() {
    return issuerName;
  }

  public BigDecimal getBatch() {
    return batch;
  }

  public Date getDeliveredDate() {
    return deliveredDate;
  }

  public String getShipToCompanyId() {
    return shipToCompanyId;
  }

  public String getShipToLocationId() {
    return shipToLocationId;
  }

  public String getHazardCategory() {
    return hazardCategory;
  }

  public String getOverShipped() {
    return overShipped;
  }

  public String getDeliveryPoint() {
    return deliveryPoint;
  }

  public String getApplicationDesc() {
    return applicationDesc;
  }

  public String getAutoShipConfirm() {
    return this.autoShipConfirm;
  }

  public Collection getShipmentBeanCollection() {
    return this.shipmentBeanCollection;
  }
  public String getCurrencyId() {
	  return currencyId;
  }

  public void setCurrencyId(String currencyId) {
	  this.currencyId = currencyId;
  }

public String getMaterialRequestOrigin() {
	return materialRequestOrigin;
}

public void setMaterialRequestOrigin(String materialRequestOrigin) {
	this.materialRequestOrigin = materialRequestOrigin;
}

public String getShipToAddressLine1() {
	return shipToAddressLine1;
}

public void setShipToAddressLine1(String shipToAddressLine1) {
	this.shipToAddressLine1 = shipToAddressLine1;
}

public String getShipToAddressLine2() {
	return shipToAddressLine2;
}

public void setShipToAddressLine2(String shipToAddressLine2) {
	this.shipToAddressLine2 = shipToAddressLine2;
}

public String getShipToAddressLine3() {
	return shipToAddressLine3;
}

public void setShipToAddressLine3(String shipToAddressLine3) {
	this.shipToAddressLine3 = shipToAddressLine3;
}

public String getShipToAddressLine4() {
	return shipToAddressLine4;
}

public void setShipToAddressLine4(String shipToAddressLine4) {
	this.shipToAddressLine4 = shipToAddressLine4;
}

public String getShipToAddressLine5() {
	return shipToAddressLine5;
}

public void setShipToAddressLine5(String shipToAddressLine5) {
	this.shipToAddressLine5 = shipToAddressLine5;
}

public String getPaymentTerms() {
	return paymentTerms;
}

public void setPaymentTerms(String paymentTerms) {
	this.paymentTerms = paymentTerms;
}
public String getShipMfgCoc() {
	return shipMfgCoc;
}

public void setShipMfgCoc(String shipMfgCoc) {
	this.shipMfgCoc = shipMfgCoc;
}

public String getShipMfgCoa() {
	return shipMfgCoa;
}

public void setShipMfgCoa(String shipMfgCoa) {
	this.shipMfgCoa = shipMfgCoa;
}

public String getShipMsds() {
	return shipMsds;
}

public void setShipMsds(String shipMsds) {
	this.shipMsds = shipMsds;
}

public String getPickQcComplete() {
	return pickQcComplete;
}

public void setPickQcComplete(String pickQcComplete) {
	this.pickQcComplete = pickQcComplete;
}

public String getCustomerNote() {
	return customerNote;
}

public void setCustomerNote(String customerNote) {
	this.customerNote = customerNote;
}

public String getLineInternalNote() {
	return lineInternalNote;
}

public void setLineInternalNote(String lineInternalNote) {
	this.lineInternalNote = lineInternalNote;
}

public String getLinePurchasingNote() {
	return linePurchasingNote;
}

public void setLinePurchasingNote(String linePurchasingNote) {
	this.linePurchasingNote = linePurchasingNote;
}

public String getPrInternalNote() {
	return prInternalNote;
}

public void setPrInternalNote(String prInternalNote) {
	this.prInternalNote = prInternalNote;
}

public String getShiptoNote() {
	return shiptoNote;
}

public void setShiptoNote(String shiptoNote) {
	this.shiptoNote = shiptoNote;
}

public String getHazardous() {
	return hazardous;
}

public void setHazardous(String hazardous) {
	this.hazardous = hazardous;
}

public String getIncotermRequired() {
	return incotermRequired;
}

public void setIncotermRequired(String incotermRequired) {
	this.incotermRequired = incotermRequired;
}

public String getIncoterm() {
	return incoterm;
}

public void setIncoterm(String incoterm) {
	this.incoterm = incoterm;
}

public String getModeOfTransport() {
	return modeOfTransport;
}

public void setModeOfTransport(String modeOfTransport) {
	this.modeOfTransport = modeOfTransport;
}

public String getInvoiceAtShipping() {
	return invoiceAtShipping;
}

public void setInvoiceAtShipping(String invoiceAtShipping) {
	this.invoiceAtShipping = invoiceAtShipping;
}

public String getInvoiceBy() {
	return invoiceBy;
}

public void setInvoiceBy(String invoiceBy) {
	this.invoiceBy = invoiceBy;
}

public String getPickingUnitId() {
	return pickingUnitId;
}

public void setPickingUnitId(String pickingUnitId) {
	this.pickingUnitId = pickingUnitId;
}

public BigDecimal getBoxCount() {
	return boxCount;
}

public void setBoxCount(BigDecimal boxCount) {
	this.boxCount = boxCount;
}

public Date getLastProFormaPrintDate() {
	return lastProFormaPrintDate;
}

public void setLastProFormaPrintDate(Date lastProFormaPrintDate) {
	this.lastProFormaPrintDate = lastProFormaPrintDate;
}

public String getProFormaRequired() {
	return proFormaRequired;
}

public void setProFormaRequired(String proFormaRequired) {
	this.proFormaRequired = proFormaRequired;
}

public boolean isProformaRequired(){
	if(!StringHandler.isBlankString(getProFormaRequired()) && getProFormaRequired().toUpperCase().equals("Y"))
		return true;
		
	return false;
}  

}