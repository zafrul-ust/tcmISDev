package com.tcmis.client.dla.beans;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.StringHandler;

import java.math.BigDecimal;
import java.util.Date;


/******************************************************************************
 * CLASSNAME: DlaGasSupplierTrackingViewBean <br>
 * @version: 1.0, Nov 25, 2008 <br>
 *****************************************************************************/


public class DlaGasSupplierTrackingViewBean extends BaseDataBean {

	private String supplierName;
	private String shipToInfo;
	private String shipFrom;
	private String issuer;
	private String partShortName;
	private String ipg;
	private String airgasIpg;
	private String orderStatus;
	private String comments;
	private BigDecimal radianPo;
	private BigDecimal poLine;
	private Date dateCreated;
	private Date dateSent;
	private Date dateAcknowledgement;
	private String shipToLocationId;
	private BigDecimal unitPrice;
	private Date dateFirstConfirmed;
	private String deliveryComments;
	private Date promisedDate;
	private BigDecimal poLineQuantity;
	private String shipFromLocationId;
	private String supplier;
	private String supplierSalesOrderNo;
	private Date vendorShipDate;
	private BigDecimal orderQuantity;
	private BigDecimal prNumber;
	private String lineItem;
	private String markForLocationId;
	private String poNumber;
	private String facPartNo;
	private String cancelStatus;
	private String cancelComment;
	private String customerHaasContractId;
	private String releaseNum;
	private String milstripCode;
	private Date desiredShipDate;
	private String shipToDodaac;
	private String shipViaDodaac;
	private String oconus;
	private Date orderDate;
	private String transactionRefNum;
	private BigDecimal issueQuantity;
	private BigDecimal shipmentId;
	private Date dateShipped;
	private String inventoryGroup;
	private String trackingNumber;
	private String carrierName;
	private String asnStatus;
	private String transportationControlNum;
	private BigDecimal openQuantity;
	private BigDecimal orderAge;
	private Date expediteDate;
	private String originalTransactionType;
	private String originalShipFrom;
	private String hubName;
	private String inventoryGroupName;
	private Date dateSentWawf;
	private String originInspectionRequired;


	//constructor
	public DlaGasSupplierTrackingViewBean() {
	}

	//setters
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public void setShipToInfo(String shipToInfo) {
		this.shipToInfo = shipToInfo;
	}
	public void setShipFrom(String shipFrom) {
		this.shipFrom = shipFrom;
	}
	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}
	public void setPartShortName(String partShortName) {
		this.partShortName = partShortName;
	}
	public void setIpg(String ipg) {
		this.ipg = ipg;
	}
	public void setAirgasIpg(String airgasIpg) {
		this.airgasIpg = airgasIpg;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}
	public void setPoLine(BigDecimal poLine) {
		this.poLine = poLine;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public void setDateSent(Date dateSent) {
		this.dateSent = dateSent;
	}
	public void setDateAcknowledgement(Date dateAcknowledgement) {
		this.dateAcknowledgement = dateAcknowledgement;
	}
	public void setShipToLocationId(String shipToLocationId) {
		this.shipToLocationId = shipToLocationId;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	public void setDateFirstConfirmed(Date dateFirstConfirmed) {
		this.dateFirstConfirmed = dateFirstConfirmed;
	}
	public void setDeliveryComments(String deliveryComments) {
		this.deliveryComments = deliveryComments;
	}
	public void setPromisedDate(Date promisedDate) {
		this.promisedDate = promisedDate;
	}
	public void setPoLineQuantity(BigDecimal poLineQuantity) {
		this.poLineQuantity = poLineQuantity;
	}
	public void setShipFromLocationId(String shipFromLocationId) {
		this.shipFromLocationId = shipFromLocationId;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public void setSupplierSalesOrderNo(String supplierSalesOrderNo) {
		this.supplierSalesOrderNo = supplierSalesOrderNo;
	}
	public void setVendorShipDate(Date vendorShipDate) {
		this.vendorShipDate = vendorShipDate;
	}
	public void setOrderQuantity(BigDecimal orderQuantity) {
		this.orderQuantity = orderQuantity;
	}
	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}
	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}
	public void setMarkForLocationId(String markForLocationId) {
		this.markForLocationId = markForLocationId;
	}
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	public void setFacPartNo(String facPartNo) {
		this.facPartNo = facPartNo;
	}
	public void setCancelStatus(String cancelStatus) {
		this.cancelStatus = cancelStatus;
	}
	public void setCancelComment(String cancelComment) {
		this.cancelComment = cancelComment;
	}
	public void setCustomerHaasContractId(String customerHaasContractId) {
		this.customerHaasContractId = customerHaasContractId;
	}
	public void setReleaseNum(String releaseNum) {
		this.releaseNum = StringHandler.emptyIfNull(releaseNum);
	}
	public void setMilstripCode(String milstripCode) {
		this.milstripCode = StringHandler.emptyIfNull(milstripCode);
	}
	public void setDesiredShipDate(Date desiredShipDate) {
		this.desiredShipDate = desiredShipDate;
	}
	public void setShipToDodaac(String shipToDodaac) {
		this.shipToDodaac = shipToDodaac;
	}
	public void setShipViaDodaac(String shipViaDodaac) {
		this.shipViaDodaac = shipViaDodaac;
	}
	public void setOconus(String oconus) {
		this.oconus = oconus;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public void setTransactionRefNum(String transactionRefNum) {
		this.transactionRefNum = transactionRefNum;
	}
	public void setIssueQuantity(BigDecimal issueQuantity) {
		this.issueQuantity = issueQuantity;
	}
	public void setShipmentId(BigDecimal shipmentId) {
		this.shipmentId = shipmentId;
	}
	public void setDateShipped(Date dateShipped) {
		this.dateShipped = dateShipped;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}
	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}
	public void setAsnStatus(String asnStatus) {
		this.asnStatus = asnStatus;
	}
	public void setTransportationControlNum(String transportationControlNum) {
		this.transportationControlNum = transportationControlNum;
	}
	public void setOpenQuantity(BigDecimal openQuantity) {
		this.openQuantity = openQuantity;
	}
	public void setOrderAge(BigDecimal orderAge) {
		this.orderAge = orderAge;
	}

	public void setOriginalShipFrom(String originalShipFrom) {
		this.originalShipFrom = originalShipFrom;
	}
	public void setHubName(String hubName) {
		this.hubName = hubName;
	}
	public void setInventoryGroupName(String inventoryGroupName) {
		this.inventoryGroupName = inventoryGroupName;
	}
	public void setDateSentWawf(Date dateSentWawf) {
		this.dateSentWawf = dateSentWawf;
	}
	public void setOriginInspectionRequired(String originInspectionRequired) {
		this.originInspectionRequired = originInspectionRequired;
	}
	//getters
	public Date getDateSentWawf() {
		return dateSentWawf;
	}
	public String getOriginInspectionRequired() {
		return originInspectionRequired;
	}
	public String getHubName() {
		return hubName;
	}
	public String getInventoryGroupName() {
		return inventoryGroupName;
	}
	public String getOriginalShipFrom() {
		return originalShipFrom;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public String getShipToInfo() {
		return shipToInfo;
	}
	public String getShipFrom() {
		return shipFrom;
	}
	public String getIssuer() {
		return issuer;
	}
	public String getPartShortName() {
		return partShortName;
	}
	public String getIpg() {
		return ipg;
	}
	public String getAirgasIpg() {
		return airgasIpg;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public String getComments() {
		return comments;
	}
	public BigDecimal getRadianPo() {
		return radianPo;
	}
	public BigDecimal getPoLine() {
		return poLine;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public Date getDateSent() {
		return dateSent;
	}
	public Date getDateAcknowledgement() {
		return dateAcknowledgement;
	}
	public String getShipToLocationId() {
		return shipToLocationId;
	}
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public Date getDateFirstConfirmed() {
		return dateFirstConfirmed;
	}
	public String getDeliveryComments() {
		return deliveryComments;
	}
	public Date getPromisedDate() {
		return promisedDate;
	}
	public BigDecimal getPoLineQuantity() {
		return poLineQuantity;
	}
	public String getShipFromLocationId() {
		return shipFromLocationId;
	}
	public String getSupplier() {
		return supplier;
	}
	public String getSupplierSalesOrderNo() {
		return supplierSalesOrderNo;
	}
	public Date getVendorShipDate() {
		return vendorShipDate;
	}
	public BigDecimal getOrderQuantity() {
		return orderQuantity;
	}
	public BigDecimal getPrNumber() {
		return prNumber;
	}
	public String getLineItem() {
		return lineItem;
	}
	public String getMarkForLocationId() {
		return markForLocationId;
	}
	public String getPoNumber() {
		return poNumber;
	}
	public String getFacPartNo() {
		return facPartNo;
	}
	public String getCancelStatus() {
		return cancelStatus;
	}
	public String getCancelComment() {
		return cancelComment;
	}
	public String getCustomerHaasContractId() {
		return customerHaasContractId;
	}
	public String getReleaseNum() {
		return releaseNum;
	}
	public String getMilstripCode() {
		return milstripCode;
	}
	public Date getDesiredShipDate() {
		return desiredShipDate;
	}
	public String getShipToDodaac() {
		return shipToDodaac;
	}
	public String getShipViaDodaac() {
		return shipViaDodaac;
	}
	public String getOconus() {
		return oconus;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public String getTransactionRefNum() {
		return transactionRefNum;
	}
	public BigDecimal getIssueQuantity() {
		return issueQuantity;
	}
	public BigDecimal getShipmentId() {
		return shipmentId;
	}
	public Date getDateShipped() {
		return dateShipped;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public String getTrackingNumber() {
		return trackingNumber;
	}
	public String getCarrierName() {
		return carrierName;
	}
	public String getAsnStatus() {
		return asnStatus;
	}
	public String getTransportationControlNum() {
		return transportationControlNum;
	}
	public BigDecimal getOpenQuantity() {
		return openQuantity;
	}
	public BigDecimal getOrderAge() {
		return orderAge;
	}

	public Date getExpediteDate() {
		return expediteDate;
	}

	public void setExpediteDate(Date expediteDate) {
		this.expediteDate = expediteDate;
	}

	public String getOriginalTransactionType() {
		return originalTransactionType;
	}

	public void setOriginalTransactionType(String originalTransactionType) {
		this.originalTransactionType = originalTransactionType;
	}

	

	
}