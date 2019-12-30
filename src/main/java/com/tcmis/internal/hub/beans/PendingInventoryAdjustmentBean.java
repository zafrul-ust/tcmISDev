package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: PendingInventoryAdjustmentBean <br>
 * @version: 1.0, Jun 29, 2009 <br>
 *****************************************************************************/


public class PendingInventoryAdjustmentBean extends BaseDataBean {
	
	private static final long serialVersionUID = -2822598526528087944L;
	
	private String operatingEntityName;
	private String branchPlant;
	private String hubName;
	private String inventoryGroup;
	private String inventoryGroupName;
	private BigDecimal requestId;
	private BigDecimal receiptId;
	private BigDecimal itemId;
	private String itemDesc;
	private String packaging;
	private BigDecimal quantity;
	private BigDecimal fullUnitPrice;
	private BigDecimal extendedPrice;
	private String requestor;
	private Date requestDate;
	private BigDecimal reviewerId;
	private Date reviewDate;
	private String status;
	private String reviewerComment;
	private String opsEntityId;
	private String opsCompanyId;
	private String approve;
	private BigDecimal totalPrice;
	private String companyId;
	private String requestorComment;
    private String homeCurrencyId;
    private BigDecimal currentInventoryQty;
    private BigDecimal writeOffDiff;
    
    private String lotStatus;

	//constructor
	public PendingInventoryAdjustmentBean() {
	}

	//setters
	public void setOperatingEntityName(String operatingEntityName) {
		this.operatingEntityName = operatingEntityName;
	}
	public void setHubName(String hubName) {
		this.hubName = hubName;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setRequestId(BigDecimal requestId) {
		this.requestId = requestId;
	}
	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public void setFullUnitPrice(BigDecimal fullUnitPrice) {
		this.fullUnitPrice = fullUnitPrice;
	}
	public void setExtendedPrice(BigDecimal extendedPrice) {
		this.extendedPrice = extendedPrice;
	}
	public void setRequestor(String requestor) {
		this.requestor = requestor;
	}
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	public void setReviewerId(BigDecimal reviewerId) {
		this.reviewerId = reviewerId;
	}
	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setReviewerComment(String reviewerComment) {
		this.reviewerComment = reviewerComment;
	}
    public void setHomeCurrencyId(String homeCurrencyId) {
        this.homeCurrencyId = homeCurrencyId;
    }

    //getters
	public String getOperatingEntityName() {
		return operatingEntityName;
	}
	public String getHubName() {
		return hubName;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public BigDecimal getRequestId() {
		return requestId;
	}
	public BigDecimal getReceiptId() {
		return receiptId;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public String getPackaging() {
		return packaging;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public BigDecimal getFullUnitPrice() {
		return fullUnitPrice;
	}
	public BigDecimal getExtendedPrice() {
		return extendedPrice;
	}
	public String getRequestor() {
		return requestor;
	}
	public Date getRequestDate() {
		return requestDate;
	}
	public BigDecimal getReviewerId() {
		return reviewerId;
	}
	public Date getReviewDate() {
		return reviewDate;
	}
	public String getStatus() {
		return status;
	}
	public String getReviewerComment() {
		return reviewerComment;
	}
    public String getHomeCurrencyId() {
        return homeCurrencyId;
    }

    /**
	 * @param opsEntityId the opsEntityId to set
	 */
	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}

	/**
	 * @return the opsEntityId
	 */
	public String getOpsEntityId() {
		return opsEntityId;
	}

	/**
	 * @param opsCompanyId the opsCompanyId to set
	 */
	public void setOpsCompanyId(String opsCompanyId) {
		this.opsCompanyId = opsCompanyId;
	}

	/**
	 * @return the opsCompanyId
	 */
	public String getOpsCompanyId() {
		return opsCompanyId;
	}	

	/**
	 * @param inventoryGroupName the inventoryGroupName to set
	 */
	public void setInventoryGroupName(String inventoryGroupName) {
		this.inventoryGroupName = inventoryGroupName;
	}

	/**
	 * @return the inventoryGroupName
	 */
	public String getInventoryGroupName() {
		return inventoryGroupName;
	}

	/**
	 * @param branchPlant the branchPlant to set
	 */
	public void setBranchPlant(String branchPlant) {
		this.branchPlant = branchPlant;
	}

	/**
	 * @return the branchPlant
	 */
	public String getBranchPlant() {
		return branchPlant;
	}

	/**
	 * @param approve the approve to set
	 */
	public void setApprove(String approve) {
		this.approve = approve;
	}

	/**
	 * @return the approve
	 */
	public String getApprove() {
		return approve;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getRequestorComment() {
		return requestorComment;
	}

	public void setRequestorComment(String requestorComment) {
		this.requestorComment = requestorComment;
	}

	public BigDecimal getCurrentInventoryQty() {
		return currentInventoryQty;
	}

	public void setCurrentInventoryQty(BigDecimal currentInventoryQty) {
		this.currentInventoryQty = currentInventoryQty;
	}

	public BigDecimal getWriteOffDiff() {
		return writeOffDiff;
	}

	public void setWriteOffDiff(BigDecimal writeOffDiff) {
		this.writeOffDiff = writeOffDiff;
	}

	public String getLotStatus() {
		return lotStatus;
	}

	public void setLotStatus(String lotStatus) {
		this.lotStatus = lotStatus;
	}
}