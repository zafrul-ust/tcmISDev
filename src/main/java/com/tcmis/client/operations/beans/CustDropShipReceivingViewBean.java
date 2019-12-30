package com.tcmis.client.operations.beans;

import java.util.Date;
import java.math.BigDecimal;
import java.util.Collection;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: CustDropShipReceivingViewBean <br>
 * @version: 1.0, Sep 26, 2007 <br>
 *****************************************************************************/

public class CustDropShipReceivingViewBean
 extends BaseDataBean {

 private String companyId;
 private BigDecimal mrNumber;
 private String mrLineItem;
 private BigDecimal mrQtyOpen;
 private String requestorName;
 private String deliveryPoint;
 private String shipToLocationId;
 private BigDecimal radianPo;
 private BigDecimal lineItem;
 private BigDecimal qtyOpen;
 private BigDecimal itemId;
 private Date expected;
 private BigDecimal qty;
 private String branchPlant;
 private String itemDescription;
 private String poNotes;
 private String transType;
 private BigDecimal transferRequestId;
 private String lastSupplier;
 private String indefiniteShelfLife;
 private String critical;
 private String customerPo;
 private String orderQtyUpdateOnReceipt;
 private String inventoryGroup;
 private String manageKitsAsSingleUnit;
 private BigDecimal componentId;
 private BigDecimal materialId;
 private String packaging;
 private String materialDesc;
 private String inventoryGroupName;
 private BigDecimal numberOfKits;
 private BigDecimal purchasingUnitsPerItem;
 private String mvItem;
 private String displayPkgStyle;
 private String nonintegerReceiving;
 private String mfgLot;
 private String bin;
 private Collection receiptItemPriorBinViewBeanCollection;
 private String ok;
 private Date supplierShipDate;
 private Date dateOfReceipt;
 private Date dom;
 private Date dos;
 private Date expirationDate;
 private String expirationDateString;
 private BigDecimal quantityReceived;
 private String receiptNotes;
 private String skipReceivingQc;
 private String lotStatus;
 private BigDecimal receivedReceipt1;
 private BigDecimal receivedReceipt2;
 private String updateStatus;
 private String errorMessage;
 private Collection kitCollection;
 private BigDecimal rowSpan;
 private String deliveryTicket;
 private BigDecimal transferReceiptId;
 private String packagedQty;
 private String packagedSize;
 private String duplicatePkgLine;
 private String duplicateKitLine;
 private String catPartNo;
 private String partDescription;
 private String useFacilityId;
 private Date endDate;
 private String receivePoLine;
 private String rowNumber;
 private String purchasingUnitOfMeasure;
 private String closePoLine;
 private BigDecimal batch;
 private BigDecimal issueId;
 private String shipToFacilityId;
 private String enableLinkToPo;
 private String hubName;
 private String opsCompanyId;
 private String opsEntityId;
 private String operatingEntityName;
 
 private BigDecimal customerId;
 private String customerName;
 private String csrName;
 private BigDecimal shipmentId;
 private String countryAbbrev;

 public BigDecimal getShipmentId() {
   return shipmentId;
 }

 public void setShipmentId(BigDecimal shipmentId) {
   this.shipmentId = shipmentId;
 }
 
 

 public String getCountryAbbrev() {
	return countryAbbrev;
}

public void setCountryAbbrev(String countryAbbrev) {
	this.countryAbbrev = countryAbbrev;
}

public BigDecimal getCustomerId() {
   return customerId;
 }

public void setCustomerId(BigDecimal customerId) {
	this.customerId = customerId;
}

//constructor
 public CustDropShipReceivingViewBean() {
 }

 //setters
 public void setCompanyId(String companyId) {
	this.companyId = companyId;
 }

 public void setMrNumber(BigDecimal mrNumber) {
	this.mrNumber = mrNumber;
 }

 public void setMrLineItem(String mrLineItem) {
	this.mrLineItem = mrLineItem;
 }

 public void setMrQtyOpen(BigDecimal mrQtyOpen) {
	this.mrQtyOpen = mrQtyOpen;
 }

 public void setRequestorName(String requestorName) {
	this.requestorName = requestorName;
 }

 public void setDeliveryPoint(String deliveryPoint) {
	this.deliveryPoint = deliveryPoint;
 }

 public void setShipToLocationId(String shipToLocationId) {
	this.shipToLocationId = shipToLocationId;
 }

 public void setRadianPo(BigDecimal radianPo) {
	this.radianPo = radianPo;
 }

 public void setLineItem(BigDecimal lineItem) {
	this.lineItem = lineItem;
 }

 public void setQtyOpen(BigDecimal qtyOpen) {
	this.qtyOpen = qtyOpen;
 }

 public void setItemId(BigDecimal itemId) {
	this.itemId = itemId;
 }

 public void setExpected(Date expected) {
	this.expected = expected;
 }

 public void setQty(BigDecimal qty) {
	this.qty = qty;
 }

 public void setBranchPlant(String branchPlant) {
	this.branchPlant = branchPlant;
 }

 public void setItemDescription(String itemDescription) {
	this.itemDescription = itemDescription;
 }

 public void setPoNotes(String poNotes) {
	this.poNotes = poNotes;
 }

 public void setTransType(String transType) {
	this.transType = transType;
 }

 public void setTransferRequestId(BigDecimal transferRequestId) {
	this.transferRequestId = transferRequestId;
 }

 public void setLastSupplier(String lastSupplier) {
	this.lastSupplier = lastSupplier;
 }

 public void setIndefiniteShelfLife(String indefiniteShelfLife) {
	this.indefiniteShelfLife = indefiniteShelfLife;
 }

 public void setCritical(String critical) {
	this.critical = critical;
 }

 public void setCustomerPo(String customerPo) {
	this.customerPo = customerPo;
 }

 public void setOrderQtyUpdateOnReceipt(String orderQtyUpdateOnReceipt) {
	this.orderQtyUpdateOnReceipt = orderQtyUpdateOnReceipt;
 }

 public void setInventoryGroup(String inventoryGroup) {
	this.inventoryGroup = inventoryGroup;
 }

 public void setManageKitsAsSingleUnit(String manageKitsAsSingleUnit) {
	this.manageKitsAsSingleUnit = manageKitsAsSingleUnit;
 }

 public void setComponentId(BigDecimal componentId) {
	this.componentId = componentId;
 }

 public void setMaterialId(BigDecimal materialId) {
	this.materialId = materialId;
 }

 public void setPackaging(String packaging) {
	this.packaging = packaging;
 }

 public void setMaterialDesc(String materialDesc) {
	this.materialDesc = materialDesc;
 }

 public void setInventoryGroupName(String inventoryGroupName) {
	this.inventoryGroupName = inventoryGroupName;
 }

 public void setNumberOfKits(BigDecimal numberOfKits) {
	this.numberOfKits = numberOfKits;
 }

 public void setPurchasingUnitsPerItem(BigDecimal purchasingUnitsPerItem) {
	this.purchasingUnitsPerItem = purchasingUnitsPerItem;
 }

 public void setMvItem(String mvItem) {
	this.mvItem = mvItem;
 }

 public void setDisplayPkgStyle(String displayPkgStyle) {
	this.displayPkgStyle = displayPkgStyle;
 }

 public void setNonintegerReceiving(String nonintegerReceiving) {
	this.nonintegerReceiving = nonintegerReceiving;
 }

 public void setBin(String bin) {
	this.bin = bin;
 }

 public void setReceiptItemPriorBinViewBeanCollection(Collection
	receiptItemPriorBinViewBeanCollection) {
	this.receiptItemPriorBinViewBeanCollection =
	 receiptItemPriorBinViewBeanCollection;
 }

 public void setOk(String ok) {
	this.ok = ok;
 }

 public void setSupplierShipDate(Date date) {
	this.supplierShipDate = date;
 }

 public void setDateOfReceipt(Date dateOfReceipt) {
	this.dateOfReceipt = dateOfReceipt;
 }

 public void setDom(Date dom) {
	this.dom = dom;
 }

 public void setDos(Date dos) {
	this.dos = dos;
 }

 public void setExpirationDate(Date date) {
	this.expirationDate = date;
 }

 public void setQuantityReceived(BigDecimal quantityReceived) {
	this.quantityReceived = quantityReceived;
 }

 public void setReceiptNotes(String receiptNotes) {
	this.receiptNotes = receiptNotes;
 }

 public void setSkipReceivingQc(String skipReceivingQc) {
	this.skipReceivingQc = skipReceivingQc;
 }

 public void setLotStatus(String lotStatus) {
	this.lotStatus = lotStatus;
 }

 public void setReceivedReceipt1(BigDecimal receivedReceipt1) {
	this.receivedReceipt1 = receivedReceipt1;
 }

 public void setReceivedReceipt2(BigDecimal receivedReceipt2) {
	this.receivedReceipt2 = receivedReceipt2;
 }

 public void setUpdateStatus(String updateStatus) {
	this.updateStatus = updateStatus;
 }

 public void setErrorMessage(String errorMessage) {
	this.errorMessage = errorMessage;
 }

 public void setKitCollection(Collection
	kitCollection) {
	this.kitCollection =
	 kitCollection;
 }

 public void setRowSpan(BigDecimal rowSpan) {
	this.rowSpan = rowSpan;
 }

 public void setPurchasingUnitOfMeasure(String purchasingUnitOfMeasure) {
	this.purchasingUnitOfMeasure = purchasingUnitOfMeasure;
 }

 public void setDeliveryTicket(String deliveryTicket) {
	this.deliveryTicket = deliveryTicket;
 }

 public void setTransferReceiptId(BigDecimal transferReceiptId) {
	this.transferReceiptId = transferReceiptId;
 }

 public void setPackagedQty(java.lang.String packagedQty) {
	this.packagedQty = packagedQty;
 }

 public void setPackagedSize(java.lang.String packagedSize) {
	this.packagedSize = packagedSize;
 }

 public void setDuplicatePkgLine(java.lang.String duplicatePkgLine) {
	this.duplicatePkgLine = duplicatePkgLine;
 }

 public void setDuplicateKitLine(java.lang.String duplicateKitLine) {
	this.duplicateKitLine = duplicateKitLine;
 }

 public void setCatPartNo(String catPartNo) {
	this.catPartNo = catPartNo;
 }

 public void setPartDescription(String partDescription) {
	this.partDescription = partDescription;
 }

 public void setUseFacilityId(String useFacilityId) {
	this.useFacilityId = useFacilityId;
 }

 public void setEndDate(Date endDate) {
	this.endDate = endDate;
 }

 public void setRowNumber(String rowNumber) {
	this.rowNumber = rowNumber;
 }

 public void setMfgLot(String mfgLot) {
	this.mfgLot = mfgLot;
 }

 public void setClosePoLine(String closePoLine) {
	this.closePoLine = closePoLine;
 }

 public void setBatch(BigDecimal batch) {
	this.batch = batch;
 }

 public void setIssueId(BigDecimal issueId) {
	this.issueId = issueId;
 }

 public void setShipToFacilityId(String shipToFacilityId) {
   this.shipToFacilityId = shipToFacilityId;
 }

	public void setReceivePoLine(String receivePoLine) {
		this.receivePoLine = receivePoLine;
	}

	public void setEnableLinkToPo(String enableLinkToPo) {
		this.enableLinkToPo = enableLinkToPo;
	}

	//getters
 public String getCompanyId() {
	return companyId;
 }

 public BigDecimal getMrNumber() {
	return mrNumber;
 }

 public String getMrLineItem() {
	return mrLineItem;
 }

 public BigDecimal getMrQtyOpen() {
	return mrQtyOpen;
 }

 public String getRequestorName() {
	return requestorName;
 }

 public String getDeliveryPoint() {
	return deliveryPoint;
 }

 public String getShipToLocationId() {
	return shipToLocationId;
 }

 public BigDecimal getRadianPo() {
	return radianPo;
 }

 public BigDecimal getLineItem() {
	return lineItem;
 }

 public BigDecimal getQtyOpen() {
	return qtyOpen;
 }

 public BigDecimal getItemId() {
	return itemId;
 }

 public Date getExpected() {
	return expected;
 }

 public BigDecimal getQty() {
	return qty;
 }

 public String getBranchPlant() {
	return branchPlant;
 }

 public String getItemDescription() {
	return itemDescription;
 }

 public String getPoNotes() {
	return poNotes;
 }

 public String getTransType() {
	return transType;
 }

 public BigDecimal getTransferRequestId() {
	return transferRequestId;
 }

 public String getLastSupplier() {
	return lastSupplier;
 }

 public String getIndefiniteShelfLife() {
	return indefiniteShelfLife;
 }

 public String getCritical() {
	return critical;
 }

 public String getCustomerPo() {
	return customerPo;
 }

 public String getOrderQtyUpdateOnReceipt() {
	return orderQtyUpdateOnReceipt;
 }

 public String getInventoryGroup() {
	return inventoryGroup;
 }

 public String getManageKitsAsSingleUnit() {
	return manageKitsAsSingleUnit;
 }

 public BigDecimal getComponentId() {
	return componentId;
 }

 public BigDecimal getMaterialId() {
	return materialId;
 }

 public String getPackaging() {
	return packaging;
 }

 public String getMaterialDesc() {
	return materialDesc;
 }

 public String getInventoryGroupName() {
	return inventoryGroupName;
 }

 public BigDecimal getNumberOfKits() {
	return numberOfKits;
 }

 public BigDecimal getPurchasingUnitsPerItem() {
	return purchasingUnitsPerItem;
 }

 public String getMvItem() {
	return mvItem;
 }

 public String getDisplayPkgStyle() {
	return displayPkgStyle;
 }

 public String getNonintegerReceiving() {
	return nonintegerReceiving;
 }

 public String getBin() {
	return bin;
 }

 public Collection getReceiptItemPriorBinViewBeanCollection() {
	return receiptItemPriorBinViewBeanCollection;
 }

 public String getOk() {
	return ok;
 }

 public Date getSupplierShipDate() {
	return this.supplierShipDate;
 }

 public Date getDateOfReceipt() {
	return dateOfReceipt;
 }

 public Date getDom() {
	return dom;
 }

 public Date getDos() {
	return dos;
 }

 public Date getExpirationDate() {
	return this.expirationDate;
 }

 public BigDecimal getQuantityReceived() {
	return this.quantityReceived;
 }

 public String getReceiptNotes() {
	return receiptNotes;
 }

 public String getSkipReceivingQc() {
	return skipReceivingQc;
 }

 public String getLotStatus() {
	return lotStatus;
 }

 public BigDecimal getReceivedReceipt1() {
	return receivedReceipt1;
 }

 public BigDecimal getReceivedReceipt2() {
	return receivedReceipt2;
 }

 public String getUpdateStatus() {
	return updateStatus;
 }

 public String getErrorMessage() {
	return errorMessage;
 }

 public Collection getKitCollection() {
	return kitCollection;
 }

 public BigDecimal getRowSpan() {
	return rowSpan;
 }

 public String getPurchasingUnitOfMeasure() {
	return purchasingUnitOfMeasure;
 }

 public String getDeliveryTicket() {
	return deliveryTicket;
 }

 public BigDecimal getTransferReceiptId() {
	return transferReceiptId;
 }

 public java.lang.String getPackagedQty() {
	return packagedQty;
 }

 public java.lang.String getPackagedSize() {
	return packagedSize;
 }

 public java.lang.String getDuplicatePkgLine() {
	return duplicatePkgLine;
 }

 public java.lang.String getDuplicateKitLine() {
	return duplicateKitLine;
 }

 public String getCatPartNo() {
	return catPartNo;
 }

 public String getPartDescription() {
	return partDescription;
 }

 public String getUseFacilityId() {
	return useFacilityId;
 }

 public Date getEndDate() {
	return endDate;
 }

 public String getRowNumber() {
	return rowNumber;
 }

 public String getMfgLot() {
	return mfgLot;
 }

 public String getClosePoLine() {
	return closePoLine;
 }

 public BigDecimal getBatch() {
	return batch;
 }

 public BigDecimal getIssueId() {
	return issueId;
 }

 public String getShipToFacilityId() {
   return shipToFacilityId;
 }

	public String getReceivePoLine() {
		return receivePoLine;
	}

	public String getEnableLinkToPo() {
		return enableLinkToPo;
	}

	public String getExpirationDateString() {
		return expirationDateString;
	}

	public void setExpirationDateString(String expirationDateString) {
		this.expirationDateString = expirationDateString;
	}

	public void setHubName(String hubName) {
		this.hubName = hubName;
	}

	public String getHubName() {
		return hubName;
	}

	public void setOpsCompanyId(String opsCompanyId) {
		this.opsCompanyId = opsCompanyId;
	}

	public String getOpsCompanyId() {
		return opsCompanyId;
	}

	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}

	public String getOpsEntityId() {
		return opsEntityId;
	}

	public void setOperatingEntityName(String operatingEntityName) {
		this.operatingEntityName = operatingEntityName;
	}

	public String getOperatingEntityName() {
		return operatingEntityName;
	}

	public String getCsrName() {
		return csrName;
	}

	public void setCsrName(String csrName) {
		this.csrName = csrName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
}