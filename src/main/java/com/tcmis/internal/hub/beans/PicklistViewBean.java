package com.tcmis.internal.hub.beans;

import java.util.Collection;
import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: PicklistViewBean <br>
 * @version: 1.0, Feb 21, 2007 <br>
 *****************************************************************************/


public class PicklistViewBean extends BaseDataBean {

	private BigDecimal picklistId;
	private String hub;
	private String inventoryGroup;
	private BigDecimal prNumber;
	private String lineItem;
	private String mrLine;
	private BigDecimal receiptId;
	private BigDecimal itemId;
	private String bin;
	private String mfgLot;
	private String lotStatus;
	private Date expireDate;
	private String stockingMethod;
	private String deliveryType;
	private Date needDate;
	private BigDecimal picklistQuantity;
	private BigDecimal quantityPicked;
	private Date qcDate;
	private String application;
    private String applicationDesc;
    private String facilityId;
	private String partDescription;
	private String packaging;
	private BigDecimal inventoryQuantity;
	private String catalogId;
	private String catPartNo;
	private BigDecimal partGroupNo;
	private String companyId;
	private String catalogCompanyId;
	private String deliveryPoint;
	private String shipToLocationId;
	private String requestor;
	private String endUser;
	private BigDecimal transferRequestId;
	private String mrNotes;
	private String critical;
	private Date certificationDate;
	private String pickable;
	private BigDecimal certifiedBy;
	private String certificationNumber;
	private String qualityControlItem;
	private String facLocAppPartComment;
	private String catPartComment;
	private String cabinetReplenishment;
	private String hazmatIdMissing;
	private String receiptDocumentAvailable;
	private String nonintegerReceiving;
	private BigDecimal recertNumber;
	private String carrierCode;
	private Date pickupTime;
	private BigDecimal stopNumber;
	private String trailerNumber;
	private BigDecimal packingGroupId;
	private String carrierName;
	private String transportationMode;
	private String requiresOverpack;
	private String shippedAsSingle;
    private String sortBy;
	private String shipToLocationDesc;
	private String shipToCity;
	private String shipToStateAbbrev;
	private String shipToZip;
	private String shipToDodaac;
	private String packagedAs;
	private BigDecimal maxUnitOfIssuePerBox;
	private BigDecimal maxUnitOfIssuePerPallet;
	private String consolidationNumber;
	private BigDecimal mrCount;
	private String oconusFlag;
	private String milstripCode;
	private String trackingNumber;
	private String dot;
	private String transportationPriority;
	private String hazardous;
	private String rddComment;
	private String poNumber;
	private BigDecimal splitTcn;
	private String airGroundIndicator;
	private BigDecimal customerServiceRepId;
	private String customerServiceRepName;
	private String opsEntityId;
	private String operatingEntityName;
	private BigDecimal customerId;
	private String customerName;
	private String facilityName;
	private String inventoryGroupName;
	private String customerReceiptId;
	private String requestorName;
	private String requestorPhone;
	private String requestorFax;
	private String requestorEmail;
	private String paymentTerms;
	private String specialInstructions;
	private String carrierAccountId;
	private String carrierContact;
	private String carrierServiceType;
	private String incoTerms;
	private String flashPoint;
	private String addressLine1Display;
	private String addressLine2Display;
	private String addressLine3Display;
	private String addressLine4Display;
	private String addressLine5Display;
	private String materialRequestOrigin;
	private BigDecimal submittedBy;
	private String submittedByName;
	private String hubBinRoom;
    private String currencyId;
    private String logoImageUrl;
	private String ok;
	private String mrCompletePickable;
	private BigDecimal materialRequestOriginCount;
    
    private String chargeFreight;

  // collections for relational views
  	private Collection items;
  	private Collection receipts;
  	private int itemRowspan;
  	private int receiptRowspan;
    private String closeMr;
    
    private String cms;
    private String distribution;
    private String shippingReference;
    private String customerPartNo;
    private String reportingEntityName;
    private String issueId;
    private String ownerSegmentId;
    private String allocateByChargeNumber1;
    private String allocateByChargeNumber2;   
    private String allocateByChargeNumber3;   
    private String allocateByChargeNumber4;   
    private String receiptSpecNameList;
    private String receiptSpecVersion;
    private String releaseNumber;

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

	//constructor
	public PicklistViewBean() {
	}

	//setters
	
	public void setReportingEntityName(String reportingEntityName) {
		this.reportingEntityName = reportingEntityName;
	}
	public void setCustomerPartNo(String customerPartNo) {
		this.customerPartNo = customerPartNo;
	}
	public void setMaterialRequestOriginCount(BigDecimal materialRequestOriginCount) {
		this.materialRequestOriginCount = materialRequestOriginCount;
	}
	public void setPicklistId(BigDecimal picklistId) {
		this.picklistId = picklistId;
	}
	public void setHub(String hub) {
		this.hub = hub;
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
	public void setMrLine(String mrLine) {
		this.mrLine = mrLine;
	}
	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setBin(String bin) {
		this.bin = bin;
	}
	public void setMfgLot(String mfgLot) {
		this.mfgLot = mfgLot;
	}
	public void setLotStatus(String lotStatus) {
		this.lotStatus = lotStatus;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	public void setStockingMethod(String stockingMethod) {
		this.stockingMethod = stockingMethod;
	}
	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}
	public void setNeedDate(Date needDate) {
		this.needDate = needDate;
	}
	public void setPicklistQuantity(BigDecimal picklistQuantity) {
		this.picklistQuantity = picklistQuantity;
	}
	public void setQuantityPicked(BigDecimal quantityPicked) {
		this.quantityPicked = quantityPicked;
	}
	public void setQcDate(Date qcDate) {
		this.qcDate = qcDate;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}
	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}
	public void setInventoryQuantity(BigDecimal inventoryQuantity) {
		this.inventoryQuantity = inventoryQuantity;
	}
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}
	public void setPartGroupNo(BigDecimal partGroupNo) {
		this.partGroupNo = partGroupNo;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setCatalogCompanyId(String catalogCompanyId) {
		this.catalogCompanyId = catalogCompanyId;
	}
	public void setDeliveryPoint(String deliveryPoint) {
		this.deliveryPoint = deliveryPoint;
	}
	public void setShipToLocationId(String shipToLocationId) {
		this.shipToLocationId = shipToLocationId;
	}
	public void setRequestor(String requestor) {
		this.requestor = requestor;
	}
	public void setEndUser(String endUser) {
		this.endUser = endUser;
	}
	public void setTransferRequestId(BigDecimal transferRequestId) {
		this.transferRequestId = transferRequestId;
	}
	public void setMrNotes(String mrNotes) {
		this.mrNotes = mrNotes;
	}
	public void setCritical(String critical) {
		this.critical = critical;
	}
	public void setCertificationDate(Date certificationDate) {
		this.certificationDate = certificationDate;
	}
	public void setPickable(String pickable) {
		this.pickable = pickable;
	}
	public void setCertifiedBy(BigDecimal certifiedBy) {
		this.certifiedBy = certifiedBy;
	}
	public void setCertificationNumber(String certificationNumber) {
		this.certificationNumber = certificationNumber;
	}
	public void setQualityControlItem(String qualityControlItem) {
		this.qualityControlItem = qualityControlItem;
	}
	public void setFacLocAppPartComment(String facLocAppPartComment) {
		this.facLocAppPartComment = facLocAppPartComment;
	}
	public void setCatPartComment(String catPartComment) {
		this.catPartComment = catPartComment;
	}
	public void setCabinetReplenishment(String cabinetReplenishment) {
		this.cabinetReplenishment = cabinetReplenishment;
	}
	public void setHazmatIdMissing(String hazmatIdMissing) {
		this.hazmatIdMissing = hazmatIdMissing;
	}
	public void setReceiptDocumentAvailable(String receiptDocumentAvailable) {
		this.receiptDocumentAvailable = receiptDocumentAvailable;
	}
	public void setNonintegerReceiving(String nonintegerReceiving) {
		this.nonintegerReceiving = nonintegerReceiving;
	}
	public void setRecertNumber(BigDecimal recertNumber) {
		this.recertNumber = recertNumber;
	}
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	public void setPickupTime(Date pickupTime) {
		this.pickupTime = pickupTime;
	}
	public void setStopNumber(BigDecimal stopNumber) {
		this.stopNumber = stopNumber;
	}
	public void setTrailerNumber(String trailerNumber) {
		this.trailerNumber = trailerNumber;
	}
	public void setPackingGroupId(BigDecimal packingGroupId) {
		this.packingGroupId = packingGroupId;
	}
	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}
	public void setTransportationMode(String transportationMode) {
		this.transportationMode = transportationMode;
	}
	public void setRequiresOverpack(String requiresOverpack) {
		this.requiresOverpack = requiresOverpack;
	}
	public void setShippedAsSingle(String shippedAsSingle) {
		this.shippedAsSingle = shippedAsSingle;
	}

public void setSortBy(String sortBy) {
    this.sortBy = sortBy;
}
	public void setShipToLocationDesc(String shipToLocationDesc) {
		this.shipToLocationDesc = shipToLocationDesc;
	}
	public void setShipToCity(String shipToCity) {
		this.shipToCity = shipToCity;
	}
	public void setShipToStateAbbrev(String shipToStateAbbrev) {
		this.shipToStateAbbrev = shipToStateAbbrev;
	}
	public void setShipToZip(String shipToZip) {
		this.shipToZip = shipToZip;
	}
	public void setShipToDodaac(String shipToDodaac) {
		this.shipToDodaac = shipToDodaac;
	}
	public void setPackagedAs(String packagedAs) {
		this.packagedAs = packagedAs;
	}
	public void setMaxUnitOfIssuePerBox(BigDecimal maxUnitOfIssuePerBox) {
		this.maxUnitOfIssuePerBox = maxUnitOfIssuePerBox;
	}
	public void setMaxUnitOfIssuePerPallet(BigDecimal maxUnitOfIssuePerPallet) {
		this.maxUnitOfIssuePerPallet = maxUnitOfIssuePerPallet;
	}
	public void setConsolidationNumber(String consolidationNumber) {
		this.consolidationNumber = consolidationNumber;
	}
	public void setMrCount(BigDecimal mrCount) {
		this.mrCount = mrCount;
	}
	public void setOconusFlag(String oconusFlag) {
		this.oconusFlag = oconusFlag;
	}
	public void setMilstripCode(String milstripCode) {
		this.milstripCode = milstripCode;
	}
	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}
	public void setDot(String dot) {
		this.dot = dot;
	}
	public void setTransportationPriority(String transportationPriority) {
		this.transportationPriority = transportationPriority;
	}
	public void setHazardous(String hazardous) {
		this.hazardous = hazardous;
	}
public void setRddComment(String rddComment) {
  this.rddComment = rddComment;
}
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	public void setSplitTcn(BigDecimal splitTcn) {
		this.splitTcn = splitTcn;
	}
	public void setAirGroundIndicator(String airGroundIndicator) {
		this.airGroundIndicator = airGroundIndicator;
	}
	public void setCustomerServiceRepId(BigDecimal customerServiceRepId) {
		this.customerServiceRepId = customerServiceRepId;
	}
	public void setCustomerServiceRepName(String customerServiceRepName) {
		this.customerServiceRepName = customerServiceRepName;
	}
	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}
	public void setOperatingEntityName(String operatingEntityName) {
		this.operatingEntityName = operatingEntityName;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}
	public void setInventoryGroupName(String inventoryGroupName) {
		this.inventoryGroupName = inventoryGroupName;
	}
	public void setCustomerReceiptId(String customerReceiptId) {
		this.customerReceiptId = customerReceiptId;
	}
	public void setRequestorName(String requestorName) {
		this.requestorName = requestorName;
	}
	public void setRequestorPhone(String requestorPhone) {
		this.requestorPhone = requestorPhone;
	}
	public void setRequestorFax(String requestorFax) {
		this.requestorFax = requestorFax;
	}
	public void setRequestorEmail(String requestorEmail) {
		this.requestorEmail = requestorEmail;
	}
	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}
	public void setSpecialInstructions(String specialInstructions) {
		this.specialInstructions = specialInstructions;
	}
	public void setCarrierAccountId(String carrierAccountId) {
		this.carrierAccountId = carrierAccountId;
	}
	public void setCarrierContact(String carrierContact) {
		this.carrierContact = carrierContact;
	}
	public void setCarrierServiceType(String carrierServiceType) {
		this.carrierServiceType = carrierServiceType;
	}
	public void setIncoTerms(String incoTerms) {
		this.incoTerms = incoTerms;
	}
	public void setFlashPoint(String flashPoint) {
		this.flashPoint = flashPoint;
	}
	public void setAddressLine1Display(String addressLine1Display) {
		this.addressLine1Display = addressLine1Display;
	}
	public void setAddressLine2Display(String addressLine2Display) {
		this.addressLine2Display = addressLine2Display;
	}
	public void setAddressLine3Display(String addressLine3Display) {
		this.addressLine3Display = addressLine3Display;
	}
	public void setAddressLine4Display(String addressLine4Display) {
		this.addressLine4Display = addressLine4Display;
	}
	public void setAddressLine5Display(String addressLine5Display) {
		this.addressLine5Display = addressLine5Display;
	}
	public void setMaterialRequestOrigin(String materialRequestOrigin) {
		this.materialRequestOrigin = materialRequestOrigin;
	}
	public void setSubmittedBy(BigDecimal submittedBy) {
		this.submittedBy = submittedBy;
	}
	public void setSubmittedByName(String submittedByName) {
		this.submittedByName = submittedByName;
	}
	public void setHubBinRoom(String hubBinRoom) {
		this.hubBinRoom = hubBinRoom;
	}
    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }
    
	public void setOk(String ok) {
		this.ok = ok;
	}
 
	
	public void setMrCompletePickable(String mrCompletePickable) {
		this.mrCompletePickable = mrCompletePickable;
	}
	
	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}
			   
	public boolean isOk () {
		return "true".equals(ok);
	}
	
	public void setOwnerSegmentId(String ownerSegmentId) {
		this.ownerSegmentId = ownerSegmentId;
	}
	
	public void setAllocateByChargeNumber1(String allocateByChargeNumber1) {
		this.allocateByChargeNumber1 = allocateByChargeNumber1;
	}
	
	public void setAllocateByChargeNumber2(String allocateByChargeNumber2) {
		this.allocateByChargeNumber2 = allocateByChargeNumber2;
	}
	
	public void setAllocateByChargeNumber3(String allocateByChargeNumber3) {
		this.allocateByChargeNumber3 = allocateByChargeNumber3;
	}
	
	public void setAllocateByChargeNumber4(String allocateByChargeNumber4) {
		this.allocateByChargeNumber4 = allocateByChargeNumber4;
	}
	
	public void setReceiptSpecNameList(String receiptSpecNameList) {
		this.receiptSpecNameList = receiptSpecNameList;
	}
	
	public void setReceiptSpecVersion(String receiptSpecVersion) {
		this.receiptSpecVersion = receiptSpecVersion;
	}
	
	public void setReleaseNumber(String releaseNumber) {
		this.releaseNumber = releaseNumber;
	}
	
	public String getReleaseNumber() {
		return releaseNumber;
	}
	
	public String getReceiptSpecVersion() {
		return receiptSpecVersion;
	}
	
	public String getReceiptSpecNameList() {
		return receiptSpecNameList;
	}
	
	public String getAllocateByChargeNumber4() {
		return allocateByChargeNumber4;
	}
	
	public String getAllocateByChargeNumber3() {
		return allocateByChargeNumber3;
	}
	
	public String getAllocateByChargeNumber2() {
		return allocateByChargeNumber2;
	}
	
	public String getAllocateByChargeNumber1() {
		return allocateByChargeNumber1;
	}
	
	public String getOwnerSegmentId() {
		return ownerSegmentId;
	}
	
    //getters
	
	public String getIssueId() {
		return issueId;
	}
	
	public BigDecimal getMaterialRequestOriginCount() {
		return materialRequestOriginCount;
	}
	
	public String getMrCompletePickable() {
		return mrCompletePickable;
	}
	
	public String getCustomerPartNo() {
		return customerPartNo;
	}
	
    public String getOk() {
		return ok;
	}
	public String getReportingEntityName() {
		return reportingEntityName;
	}
	public BigDecimal getPicklistId() {
		return picklistId;
	}
	public String getHub() {
		return hub;
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
	public String getMrLine() {
		return mrLine;
	}
	public BigDecimal getReceiptId() {
		return receiptId;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public String getBin() {
		return bin;
	}
	public String getMfgLot() {
		return mfgLot;
	}
	public String getLotStatus() {
		return lotStatus;
	}
	public Date getExpireDate() {
		return expireDate;
	}
	public String getStockingMethod() {
		return stockingMethod;
	}
	public String getDeliveryType() {
		return deliveryType;
	}
	public Date getNeedDate() {
		return needDate;
	}
	public BigDecimal getPicklistQuantity() {
		return picklistQuantity;
	}
	public BigDecimal getQuantityPicked() {
		return quantityPicked;
	}
	public Date getQcDate() {
		return qcDate;
	}
	public String getApplication() {
		return application;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public String getPartDescription() {
		return partDescription;
	}
	public String getPackaging() {
		return packaging;
	}
	public BigDecimal getInventoryQuantity() {
		return inventoryQuantity;
	}
	public String getCatalogId() {
		return catalogId;
	}
	public String getCatPartNo() {
		return catPartNo;
	}
	public BigDecimal getPartGroupNo() {
		return partGroupNo;
	}
	public String getCompanyId() {
		return companyId;
	}
	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}
	public String getDeliveryPoint() {
		return deliveryPoint;
	}
	public String getShipToLocationId() {
		return shipToLocationId;
	}
	public String getRequestor() {
		return requestor;
	}
	public String getEndUser() {
		return endUser;
	}
	public BigDecimal getTransferRequestId() {
		return transferRequestId;
	}
	public String getMrNotes() {
		return mrNotes;
	}
	public String getCritical() {
		return critical;
	}
	public Date getCertificationDate() {
		return certificationDate;
	}
	public String getPickable() {
		return pickable;
	}
	public BigDecimal getCertifiedBy() {
		return certifiedBy;
	}
	public String getCertificationNumber() {
		return certificationNumber;
	}
	public String getQualityControlItem() {
		return qualityControlItem;
	}
	public String getFacLocAppPartComment() {
		return facLocAppPartComment;
	}
	public String getCatPartComment() {
		return catPartComment;
	}
	public String getCabinetReplenishment() {
		return cabinetReplenishment;
	}
	public String getHazmatIdMissing() {
		return hazmatIdMissing;
	}
	public String getReceiptDocumentAvailable() {
		return receiptDocumentAvailable;
	}
	public String getNonintegerReceiving() {
		return nonintegerReceiving;
	}
	public BigDecimal getRecertNumber() {
		return recertNumber;
	}
	public String getCarrierCode() {
		return carrierCode;
	}
	public Date getPickupTime() {
		return pickupTime;
	}
	public BigDecimal getStopNumber() {
		return stopNumber;
	}
	public String getTrailerNumber() {
		return trailerNumber;
	}
	public BigDecimal getPackingGroupId() {
		return packingGroupId;
	}
	public String getCarrierName() {
		return carrierName;
	}
	public String getTransportationMode() {
		return transportationMode;
	}
	public String getRequiresOverpack() {
		return requiresOverpack;
	}
	public String getShippedAsSingle() {
		return shippedAsSingle;
	}
	
public String getSortBy() {
  return sortBy;
}
	public String getShipToLocationDesc() {
		return shipToLocationDesc;
	}
	public String getShipToCity() {
		return shipToCity;
	}
	public String getShipToStateAbbrev() {
		return shipToStateAbbrev;
	}
	public String getShipToZip() {
		return shipToZip;
	}
	public String getShipToDodaac() {
		return shipToDodaac;
	}
	public String getPackagedAs() {
		return packagedAs;
	}
	public BigDecimal getMaxUnitOfIssuePerBox() {
		return maxUnitOfIssuePerBox;
	}
	public BigDecimal getMaxUnitOfIssuePerPallet() {
		return maxUnitOfIssuePerPallet;
	}
	public String getConsolidationNumber() {
		return consolidationNumber;
	}
	public BigDecimal getMrCount() {
		return mrCount;
	}
	public String getOconusFlag() {
		return oconusFlag;
	}
	public String getMilstripCode() {
		return milstripCode;
	}
	public String getTrackingNumber() {
		return trackingNumber;
	}
	public String getDot() {
		return dot;
	}
	public String getTransportationPriority() {
		return transportationPriority;
	}
	public String getHazardous() {
		return hazardous;
	}
	public String getRddComment() {
		return rddComment;
	}
	public String getPoNumber() {
		return poNumber;
	}
	public BigDecimal getSplitTcn() {
		return splitTcn;
	}
	public String getAirGroundIndicator() {
		return airGroundIndicator;
	}
	public BigDecimal getCustomerServiceRepId() {
		return customerServiceRepId;
	}
	public String getCustomerServiceRepName() {
		return customerServiceRepName;
	}
	public String getOpsEntityId() {
		return opsEntityId;
	}
	public String getOperatingEntityName() {
		return operatingEntityName;
	}
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public String getFacilityName() {
		return facilityName;
	}
	public String getInventoryGroupName() {
		return inventoryGroupName;
	}
	public String getCustomerReceiptId() {
		return customerReceiptId;
	}
	public String getRequestorName() {
		return requestorName;
	}
	public String getRequestorPhone() {
		return requestorPhone;
	}
	public String getRequestorFax() {
		return requestorFax;
	}
	public String getRequestorEmail() {
		return requestorEmail;
	}
	public String getPaymentTerms() {
		return paymentTerms;
	}
	public String getSpecialInstructions() {
		return specialInstructions;
	}
	public String getCarrierAccountId() {
		return carrierAccountId;
	}
	public String getCarrierContact() {
		return carrierContact;
	}
	public String getCarrierServiceType() {
		return carrierServiceType;
	}
	public String getIncoTerms() {
		return incoTerms;
	}
	public String getFlashPoint() {
		return flashPoint;
	}
	public String getAddressLine1Display() {
		return addressLine1Display;
	}
	public String getAddressLine2Display() {
		return addressLine2Display;
	}
	public String getAddressLine3Display() {
		return addressLine3Display;
	}
	public String getAddressLine4Display() {
		return addressLine4Display;
	}
	public String getAddressLine5Display() {
		return addressLine5Display;
	}
	public String getMaterialRequestOrigin() {
		return materialRequestOrigin;
	}
	public BigDecimal getSubmittedBy() {
		return submittedBy;
	}
	public String getSubmittedByName() {
		return submittedByName;
	}
	public String getHubBinRoom() {
		return hubBinRoom;
	}
    public String getCurrencyId() {
        return currencyId;
    }

    /**
   * Getter for property items.
   * @return Value of property items.
   */
  public java.util.Collection getItems() {
     return items;
  }

  /**
   * Setter for property items.
   * @param items New value of property items.
   */
  public void setItems(java.util.Collection items) {
     this.items = items;
  }

  /**
   * Getter for property receipts.
   * @return Value of property receipts.
   */
  public java.util.Collection getReceipts() {
     return receipts;
  }

  /**
   * Setter for property receipts.
   * @param receipts New value of property receipts.
   */
  public void setReceipts(java.util.Collection receipts) {
     this.receipts = receipts;
  }

  /**
   * Getter for property itemRowspan.
   * @return Value of property itemRowspan.
   */
  public int getItemRowspan() {
     return itemRowspan;
  }

  /**
   * Setter for property itemRowspan.
   * @param itemRowspan New value of property itemRowspan.
   */
  public void setItemRowspan(int itemRowspan) {
     this.itemRowspan = itemRowspan;
  }

  /**
   * Getter for property receiptRowspan.
   * @return Value of property receiptRowspan.
   */
  public int getReceiptRowspan() {
     return receiptRowspan;
  }

  /**
   * Setter for property receiptRowspan.
   * @param receiptRowspan New value of property receiptRowspan.
   */
  public void setReceiptRowspan(int receiptRowspan) {
     this.receiptRowspan = receiptRowspan;
  }

  /**
   * Getter for property closeMr.
   * @return Value of property closeMr.
   */
  public java.lang.String getCloseMr() {
     return closeMr;
  }

  /**
   * Setter for property closeMr.
   * @param closeMr New value of property closeMr.
   */
  public void setCloseMr(java.lang.String closeMr) {
     this.closeMr = closeMr;
  }

public String getLogoImageUrl() {
	return logoImageUrl;
}

public void setLogoImageUrl(String logoImageUrl) {
	this.logoImageUrl = logoImageUrl;
}

public String getChargeFreight() {
	return chargeFreight;
}

public void setChargeFreight(String chargeFreight) {
	this.chargeFreight = chargeFreight;
}

public String getApplicationDesc() {
    return applicationDesc;
}

public void setApplicationDesc(String applicationDesc) {
    this.applicationDesc = applicationDesc;
}
}