package com.tcmis.client.catalog.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

public class DeliveryConfirmViewBean extends BaseDataBean {
	
	private String companyId;
	private BigDecimal prNumber;
	private String facilityId;
	private String facilityName;
	private String application;
	private String applicationDesc;
	private String lineItem;
	private BigDecimal shipmentId;
	private String partNumber;
	private String partDescription;
	private Date shipDate;
	private BigDecimal quantityShipped;
	private String comments;
	private BigDecimal confirmedBy;
	private String confirmedByName;
	private Date confirmationDate;
	private Date transactionDate;
	private Boolean confirm;
	private String isConfirmed;
	private BigDecimal receiptId;
	private String originalReceiptId;
	private String mfgLot;
	private String ownerSegmentId;
	private String programId;
	private BigDecimal totalQuantity;
	private String trackingNumber;
	
	
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public BigDecimal getPrNumber() {
		return prNumber;
	}
	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}
	
	public String getFacilityId() {
		return facilityId;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public String getLineItem() {
		return lineItem;
	}
	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}
	public BigDecimal getShipmentId() {
		return shipmentId;
	}
	public void setShipmentId(BigDecimal shipmentId) {
		this.shipmentId = shipmentId;
	}
	public String getPartNumber() {
		return partNumber;
	}
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	public String getPartDescription() {
		return partDescription;
	}
	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}
	public Date getShipDate() {
		return shipDate;
	}
	public void setShipDate(Date shipDate) {
		this.shipDate = shipDate;
	}
	public BigDecimal getQuantityShipped() {
		return quantityShipped;
	}
	public void setQuantityShipped(BigDecimal quantityShipped) {
		this.quantityShipped = quantityShipped;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public BigDecimal getConfirmedBy() {
		return confirmedBy;
	}
	public void setConfirmedBy(BigDecimal confirmedBy) {
		this.confirmedBy = confirmedBy;
	}
	public String getConfirmedByName() {
		return confirmedByName;
	}
	public void setConfirmedByName(String confirmedByName) {
		this.confirmedByName = confirmedByName;
	}
	public Date getConfirmationDate() {
		return confirmationDate;
	}
	public void setConfirmationDate(Date confirmationDate) {
		this.confirmationDate = confirmationDate;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public Boolean getConfirm() {
		return confirm;
	}
	public void setConfirm(Boolean confirm) {
		this.confirm = confirm;
	}
	public String getIsConfirmed() {
		return isConfirmed;
	}
	public void setIsConfirmed(String isConfirmed) {
		this.isConfirmed = isConfirmed;
	}	
	public BigDecimal getReceiptId() {
		return receiptId;
	}
	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}

    public String getOriginalReceiptId() {
        return originalReceiptId;
    }

    public void setOriginalReceiptId(String originalReceiptId) {
        this.originalReceiptId = originalReceiptId;
    }

    public String getMfgLot() {
		return mfgLot;
	}
	public void setMfgLot(String mfgLot) {
		this.mfgLot = mfgLot;
	}
	public String getOwnerSegmentId() {
		return ownerSegmentId;
	}
	public void setOwnerSegmentId(String ownerSegmentId) {
		this.ownerSegmentId = ownerSegmentId;
	}
	public String getProgramId() {
		return programId;
	}
	public void setProgramId(String programId) {
		this.programId = programId;
	}
	public BigDecimal getTotalQuantity() {
		return totalQuantity;
	}
	public void setTotalQuantity(BigDecimal totalQuantity) {
		this.totalQuantity = totalQuantity;
	}
	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}
	public String getTrackingNumber() {
		return trackingNumber;
	}
	public String getFacilityName() {
		return facilityName;
	}
	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}
	public String getApplication() {
		return application;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public String getApplicationDesc() {
		return applicationDesc;
	}
	public void setApplicationDesc(String applicationDesc) {
		this.applicationDesc = applicationDesc;
	}
}
