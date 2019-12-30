package com.tcmis.client.catalog.beans;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: PkgWebPrOrderTrackDetailBean <br>
 * @version: 1.0, May 6, 2005 <br>
 *****************************************************************************/

public class PkgOrderTrackWebPrOrderTrackDetailBean
 extends BaseDataBean {

 private BigDecimal useApprover;
 private String useApproverName;
 private String facPartNo;
 private BigDecimal prNumber;
 private String lineItem;
 private BigDecimal allocatedQuantity;
 private String status;
 private String ref;
// private String referenceDate;
 private Date referenceDate;
 private String notes;
 private String application;
 private BigDecimal orderQuantity;
 private String applicationDesc;
 private String itemType;
// private String requiredDatetime;
 private Date requiredDatetime;
 private String deliveryType;
// private String allocationReferenceDate;
 private Date allocationReferenceDate;
 private String mfgLot;
 private String lotStatus;
 private Date expireDate;
 private Date releaseDate;
 private String packaging;
 private Collection needDateCollection;
 private BigDecimal rowSpan;
 private String poNumber;
 private String receiptDocument;
 private Collection receiptDocumentColl;
 private String partDescription;
 
 private Collection msdsColl;
 private Collection invoiceColl;
 
 private String trackingNumber;
 private String carrierName;

 private String refType;
 private BigDecimal refNumber;
 
 private BigDecimal shipmentId;
 private String locationId;
 private String locationDesc;
 private String deliveryPoint;
 private String deliveryPointDesc;
 private String programId;
 private String ownerSegmentId;

	private String		qualityIdLabel;
	private String		qualityId;
	private String		catPartAttributeHeader;
	private String		catPartAttribute;

	public String getQualityIdLabel() {
		return qualityIdLabel;
	}

	public void setQualityIdLabel(String qualityIdLabel) {
		this.qualityIdLabel = qualityIdLabel;
	}

	public String getQualityId() {
		return qualityId;
	}

	public void setQualityId(String qualityId) {
		this.qualityId = qualityId;
	}

	public String getCatPartAttributeHeader() {
		return catPartAttributeHeader;
	}

	public void setCatPartAttributeHeader(String catPartAttributeHeader) {
		this.catPartAttributeHeader = catPartAttributeHeader;
	}

	public String getCatPartAttribute() {
		return catPartAttribute;
	}

	public void setCatPartAttribute(String catPartAttribute) {
		this.catPartAttribute = catPartAttribute;
	}

 //constructor
 public PkgOrderTrackWebPrOrderTrackDetailBean() {
 }

 //setters
 public void setUseApprover(BigDecimal useApprover) {
	this.useApprover = useApprover;
 }

 public void setUseApproverName(String useApproverName) {
	this.useApproverName = useApproverName;
 }

 public void setFacPartNo(String facPartNo) {
	this.facPartNo = facPartNo;
 }

 public void setPrNumber(BigDecimal prNumber) {
	this.prNumber = prNumber;
 }

 public void setLineItem(String lineItem) {
	this.lineItem = lineItem;
 }

 public void setAllocatedQuantity(BigDecimal allocatedQuantity) {
	this.allocatedQuantity = allocatedQuantity;
 }

 public void setStatus(String status) {
	this.status = status;
 }

 public void setRef(String ref) {
	this.ref = ref;
 }

 public void setReferenceDate(Date referenceDate) {
	this.referenceDate = referenceDate;
 }

 public void setNotes(String notes) {
	this.notes = notes;
 }

 public void setApplication(String application) {
	this.application = application;
 }

 public void setOrderQuantity(BigDecimal orderQuantity) {
	this.orderQuantity = orderQuantity;
 }

 public void setApplicationDesc(String applicationDesc) {
	this.applicationDesc = applicationDesc;
 }

 public void setItemType(String itemType) {
	this.itemType = itemType;
 }

 public void setRequiredDatetime(Date requiredDatetime) {
	this.requiredDatetime = requiredDatetime;
 }

 public void setDeliveryType(String deliveryType) {
	this.deliveryType = deliveryType;
 }

 public void setAllocationReferenceDate(Date allocationReferenceDate) {
	this.allocationReferenceDate = allocationReferenceDate;
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

 public void setReleaseDate(Date releaseDate) {
	this.releaseDate = releaseDate;
 }

 public void setPackaging(String packaging) {
	this.packaging = packaging;
 }

 public void setNeedDateCollection(Collection needDateCollection) {
	this.needDateCollection = needDateCollection;
 }

 public void setRowSpan(BigDecimal rowSpan) {
	this.rowSpan = rowSpan;
 }

 public void setPoNumber(String poNumber) {
	this.poNumber = poNumber;
 }

	public void setReceiptDocument(String receiptDocument) {
		this.receiptDocument = receiptDocument;
	}

	public void setReceiptDocumentColl(Collection receiptDocumentColl) {
		this.receiptDocumentColl = receiptDocumentColl;
	}

	public void setLocationDesc(String locationDesc) {
		this.locationDesc = locationDesc;
	}

	public void setDeliveryPointDesc(String deliveryPointDesc) {
		this.deliveryPointDesc = deliveryPointDesc;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public void setDeliveryPoint(String deliveryPoint) {
		this.deliveryPoint = deliveryPoint;
	}
	
	public void setProgramId(String programId) {
		this.programId = programId;
	}
	
	public void setOwnerSegmentId(String ownerSegmentId) {
		this.ownerSegmentId = ownerSegmentId;
	}
	
	//getters
	 public String getOwnerSegmentId() {
	   return ownerSegmentId;
	 }
	
 public String getProgramId() {
   return programId;
 }

 public BigDecimal getUseApprover() {
	return useApprover;
 }

 public String getUseApproverName() {
	return useApproverName;
 }

 public String getFacPartNo() {
	return facPartNo;
 }

 public BigDecimal getPrNumber() {
	return prNumber;
 }

 public String getLineItem() {
	return lineItem;
 }

 public BigDecimal getAllocatedQuantity() {
	return allocatedQuantity;
 }

 public String getStatus() {
	return status;
 }

 public String getRef() {
	return ref;
 }

 public Date getReferenceDate() {
	return referenceDate;
 }

 public String getNotes() {
	return notes;
 }

 public String getApplication() {
	return application;
 }

 public BigDecimal getOrderQuantity() {
	return orderQuantity;
 }

 public String getApplicationDesc() {
	return applicationDesc;
 }

 public String getItemType() {
	return itemType;
 }

 public Date getRequiredDatetime() {
	return requiredDatetime;
 }

 public String getDeliveryType() {
	return deliveryType;
 }

 public Date getAllocationReferenceDate() {
	return allocationReferenceDate;
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

 public Date getReleaseDate() {
	return releaseDate;
 }

 public String getPackaging() {
	return packaging;
 }

 public Collection getNeedDateCollection() {
	return needDateCollection;
 }

 public BigDecimal getRowSpan() {
	return rowSpan;
 }

 public String getPoNumber() {
	return poNumber;
 }

	public String getReceiptDocument() {
		return receiptDocument;
	}

	public Collection getReceiptDocumentColl() {
		return receiptDocumentColl;
	}

	/**
	 * @param partDescription the partDescription to set
	 */
	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}

	/**
	 * @return the partDescription
	 */
	public String getPartDescription() {
		return partDescription;
	}

	public String getCarrierName() {
		return carrierName;
	}

	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public Collection getMsdsColl() {
		return msdsColl;
	}

	public void setMsdsColl(Collection msdsColl) {
		this.msdsColl = msdsColl;
	}

	public BigDecimal getRefNumber() {
		return refNumber;
	}

	public void setRefNumber(BigDecimal refNumber) {
		this.refNumber = refNumber;
	}

	public String getRefType() {
		return refType;
	}

	public void setRefType(String refType) {
		this.refType = refType;
	}

	public Collection getInvoiceColl() {
		return invoiceColl;
	}

	public void setInvoiceColl(Collection invoiceColl) {
		this.invoiceColl = invoiceColl;
	}

	public BigDecimal getShipmentId() {
		return shipmentId;
	}

	public void setShipmentId(BigDecimal shipmentId) {
		this.shipmentId = shipmentId;
	}

	public String getLocationDesc() {
		return locationDesc;
	}

	public String getDeliveryPointDesc() {
		return deliveryPointDesc;
	}

	public String getLocationId() {
		return locationId;
	}

	public String getDeliveryPoint() {
		return deliveryPoint;
	}
}