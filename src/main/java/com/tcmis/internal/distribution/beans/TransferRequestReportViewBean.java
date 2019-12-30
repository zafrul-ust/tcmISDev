package com.tcmis.internal.distribution.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: TransferRequestReportViewBean <br>
 * @version: 1.0, Oct 1, 2009 <br>
 *****************************************************************************/


public class TransferRequestReportViewBean extends BaseDataBean {

	private String status;
	private BigDecimal transferRequestId;
	private Date requestDate;
	private BigDecimal itemId;
	private String itemDesc;
	private Date needDate;
	private BigDecimal quantityNeeded;
	private BigDecimal quantityShipped;
	private BigDecimal quantityReceived;
	private BigDecimal quantityInTransit;
	private Date dateDelivered;
	private BigDecimal shipmentId;
	private String carrierCode;
	private String carrierName;
	private String trackingNumber;
	private String sourceOpsEntityId;
	private String sourceHub;
	private String sourceHubName;
	private String sourceInventoryGroup;
	private String sourceInvGroupName;
	private String destinationOpsEntityId;
	private String destinationHub;
	private String destinationHubName;
	private String destinationInventoryGroup;
	private String destinationInvGroupName;
	private String buyIfNeeded;
	private String scrap;
	private String destOperatingEntityName;
	private String sourceOperatingEntityName;
	private String companyId;
	private String sourceOpsCompanyId;
	private String distCustomerPartList;
	private String customerName;
	private String rcptQualityHoldSpec;
	private String rcptQualityHoldShelfLife;
	private String releasedByName; 
	private Date releaseDate;
	private Date rcptQualHoldSlSetDate;
	private Date rcptQualHoldSpecSetDate;
	private BigDecimal releasedBy;
	private String rcptQualityHoldNote;
	private String catalogId;
	private String catPartNo;
	private BigDecimal partGroupNo;
	private String catalogCompanyId;
	private BigDecimal remainingShelfLifePercent;
	private String specList;
	private String specListConcat;
	private String specDetailConcat;
	private String specLibraryConcat;
	private String specCocConcat;
	private String specCoaConcat;

	//constructor
	public TransferRequestReportViewBean() {
	}

	//setters
	public void setStatus(String status) {
		this.status = status;
	}
	public void setTransferRequestId(BigDecimal transferRequestId) {
		this.transferRequestId = transferRequestId;
	}
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public void setNeedDate(Date needDate) {
		this.needDate = needDate;
	}
	public void setQuantityNeeded(BigDecimal quantityNeeded) {
		this.quantityNeeded = quantityNeeded;
	}
	public void setQuantityShipped(BigDecimal quantityShipped) {
		this.quantityShipped = quantityShipped;
	}
	public void setQuantityReceived(BigDecimal quantityReceived) {
		this.quantityReceived = quantityReceived;
	}
	public void setQuantityInTransit(BigDecimal quantityInTransit) {
		this.quantityInTransit = quantityInTransit;
	}
	public void setDateDelivered(Date dateDelivered) {
		this.dateDelivered = dateDelivered;
	}
	public void setShipmentId(BigDecimal shipmentId) {
		this.shipmentId = shipmentId;
	}
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}
	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}
	public void setSourceOpsEntityId(String sourceOpsEntityId) {
		this.sourceOpsEntityId = sourceOpsEntityId;
	}
	public void setSourceHub(String sourceHub) {
		this.sourceHub = sourceHub;
	}
	public void setSourceHubName(String sourceHubName) {
		this.sourceHubName = sourceHubName;
	}
	public void setSourceInventoryGroup(String sourceInventoryGroup) {
		this.sourceInventoryGroup = sourceInventoryGroup;
	}
	public void setSourceInvGroupName(String sourceInvGroupName) {
		this.sourceInvGroupName = sourceInvGroupName;
	}
	public void setDestinationOpsEntityId(String destinationOpsEntityId) {
		this.destinationOpsEntityId = destinationOpsEntityId;
	}
	public void setDestinationHub(String destinationHub) {
		this.destinationHub = destinationHub;
	}
	public void setDestinationHubName(String destinationHubName) {
		this.destinationHubName = destinationHubName;
	}
	public void setDestinationInventoryGroup(String destinationInventoryGroup) {
		this.destinationInventoryGroup = destinationInventoryGroup;
	}
	public void setDestinationInvGroupName(String destinationInvGroupName) {
		this.destinationInvGroupName = destinationInvGroupName;
	}
	public void setBuyIfNeeded(String buyIfNeeded) {
		this.buyIfNeeded = buyIfNeeded;
	}
	public void setScrap(String scrap) {
		this.scrap = scrap;
	}
	public void setDestOperatingEntityName(String destOperatingEntityName) {
		this.destOperatingEntityName = destOperatingEntityName;
	}
	public void setSourceOperatingEntityName(String sourceOperatingEntityName) {
		this.sourceOperatingEntityName = sourceOperatingEntityName;
	}
	public void setRcptQualityHoldSpec(String rcptQualityHoldSpec) {
		this.rcptQualityHoldSpec = rcptQualityHoldSpec;
	}
	public void setRcptQualityHoldShelfLife(String rcptQualityHoldShelfLife) {
		this.rcptQualityHoldShelfLife = rcptQualityHoldShelfLife;
	}
	public void setReleasedByName(String releasedByName) {
		this.releasedByName = releasedByName;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	public void setRcptQualHoldSlSetDate(Date rcptQualHoldSlSetDate) {
		this.rcptQualHoldSlSetDate = rcptQualHoldSlSetDate;
	}
	public void setRcptQualHoldSpecSetDate(Date rcptQualHoldSpecSetDate) {
		this.rcptQualHoldSpecSetDate = rcptQualHoldSpecSetDate;
	}
	public void setReleasedBy(BigDecimal releasedBy) {
		this.releasedBy = releasedBy;
	}

	public void setRcptQualityHoldNote(String rcptQualityHoldNote) {
		this.rcptQualityHoldNote = rcptQualityHoldNote;
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
	public void setCatalogCompanyId(String catalogCompanyId) {
		this.catalogCompanyId = catalogCompanyId;
	}
	public void setRemainingShelfLifePercent(BigDecimal remainingShelfLifePercent) {
		this.remainingShelfLifePercent = remainingShelfLifePercent;
	}	
	
	
	public String getSpecCoaConcat() {
		return specCoaConcat;
	}

	public void setSpecCoaConcat(String specCoaConcat) {
		this.specCoaConcat = specCoaConcat;
	}

	public String getSpecCocConcat() {
		return specCocConcat;
	}

	public void setSpecCocConcat(String specCocConcat) {
		this.specCocConcat = specCocConcat;
	}

	public String getSpecDetailConcat() {
		return specDetailConcat;
	}

	public void setSpecDetailConcat(String specDetailConcat) {
		this.specDetailConcat = specDetailConcat;
	}

	public String getSpecListConcat() {
		return specListConcat;
	}

	public void setSpecListConcat(String specListConcat) {
		this.specListConcat = specListConcat;
	}
	
	public String getSpecLibraryConcat() {
		return specLibraryConcat;
	}

	public void setSpecLibraryConcat(String specLibraryConcat) {
		this.specLibraryConcat = specLibraryConcat;
	}
	
	public String getSpecList() {
		return specList;
	}

	public void setSpecList(String specList) {
		this.specList = specList;
	}
	
	//getters
	public BigDecimal getRemainingShelfLifePercent() {
		return remainingShelfLifePercent;
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
	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}
	public String getReleasedByName() {
		return releasedByName;
	}
	public Date getReleaseDate() {
		return releaseDate;
	}
	public Date getRcptQualHoldSlSetDate() {
		return rcptQualHoldSlSetDate;
	}
	public Date getRcptQualHoldSpecSetDate() {
		return rcptQualHoldSpecSetDate;
	}
	public BigDecimal getReleasedBy () {
		return releasedBy;
	}

	public String getRcptQualityHoldNote() {
		return rcptQualityHoldNote;
	}
	public String getRcptQualityHoldSpec() {
		return rcptQualityHoldSpec;
	}
	public String getRcptQualityHoldShelfLife() {
		return rcptQualityHoldShelfLife;
	}
	public String getStatus() {
		return status;
	}
	public BigDecimal getTransferRequestId() {
		return transferRequestId;
	}
	public Date getRequestDate() {
		return requestDate;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public Date getNeedDate() {
		return needDate;
	}
	public BigDecimal getQuantityNeeded() {
		return quantityNeeded;
	}
	public BigDecimal getQuantityShipped() {
		return quantityShipped;
	}
	public BigDecimal getQuantityReceived() {
		return quantityReceived;
	}
	public BigDecimal getQuantityInTransit() {
		return quantityInTransit;
	}
	public Date getDateDelivered() {
		return dateDelivered;
	}
	public BigDecimal getShipmentId() {
		return shipmentId;
	}
	public String getCarrierCode() {
		return carrierCode;
	}
	public String getCarrierName() {
		return carrierName;
	}
	public String getTrackingNumber() {
		return trackingNumber;
	}
	public String getSourceOpsEntityId() {
		return sourceOpsEntityId;
	}
	public String getSourceHub() {
		return sourceHub;
	}
	public String getSourceHubName() {
		return sourceHubName;
	}
	public String getSourceInventoryGroup() {
		return sourceInventoryGroup;
	}
	public String getSourceInvGroupName() {
		return sourceInvGroupName;
	}
	public String getDestinationOpsEntityId() {
		return destinationOpsEntityId;
	}
	public String getDestinationHub() {
		return destinationHub;
	}
	public String getDestinationHubName() {
		return destinationHubName;
	}
	public String getDestinationInventoryGroup() {
		return destinationInventoryGroup;
	}
	public String getDestinationInvGroupName() {
		return destinationInvGroupName;
	}
	public String getBuyIfNeeded() {
		return buyIfNeeded;
	}
	public String getScrap() {
		return scrap;
	}
	public String getDestOperatingEntityName() {
		return destOperatingEntityName;
	}
	public String getSourceOperatingEntityName() {
		return sourceOperatingEntityName;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getDistCustomerPartList() {
		return distCustomerPartList;
	}

	public void setDistCustomerPartList(String distCustomerPartList) {
		this.distCustomerPartList = distCustomerPartList;
	}

	public String getSourceOpsCompanyId() {
		return sourceOpsCompanyId;
	}

	public void setSourceOpsCompanyId(String sourceOpsCompanyId) {
		this.sourceOpsCompanyId = sourceOpsCompanyId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
}