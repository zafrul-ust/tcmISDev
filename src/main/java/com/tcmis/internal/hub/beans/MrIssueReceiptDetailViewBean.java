package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

public class MrIssueReceiptDetailViewBean extends BaseDataBean {
	private static final long serialVersionUID = 1L;
	
	private String catPartNo;
	private String companyId;
	private String companyName;
	private String creditReturn;
	private Date expireDate;
	private String facilityId;
	private String inventoryGroup;
	private String inventoryGroupName;
	private BigDecimal issueId;
	private BigDecimal itemId;
	private String lineItem;
	private String lotStatus;
	private String mfgLot;
	private String note;
	private BigDecimal orderQuantity;
	private BigDecimal prNumber;
	private BigDecimal receiptId;
	private BigDecimal recertNumber;
	private String sourceHub;
	private BigDecimal totalAvailable;
	private BigDecimal totalPendingReturn;
	private BigDecimal totalShipped;
	
	private BigDecimal coNewReturnedQuantity;
	private BigDecimal coTotalReturnedQuantity;
	private BigDecimal crTotalReturnedQuantity;
	private BigDecimal issueReturnApprovedQuantity;
	private String returnIssue;
	private String returnStatus;
	private BigDecimal totalReturned;
	
	public MrIssueReceiptDetailViewBean() {}

	public String getCatPartNo() {
		return catPartNo;
	}

	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCreditReturn() {
		return creditReturn;
	}

	public void setCreditReturn(String creditReturn) {
		this.creditReturn = creditReturn;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public BigDecimal getIssueId() {
		return issueId;
	}

	public void setIssueId(BigDecimal issueId) {
		this.issueId = issueId;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public String getLineItem() {
		return lineItem;
	}

	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}

	public String getLotStatus() {
		return lotStatus;
	}

	public void setLotStatus(String lotStatus) {
		this.lotStatus = lotStatus;
	}

	public String getMfgLot() {
		return mfgLot;
	}

	public void setMfgLot(String mfgLot) {
		this.mfgLot = mfgLot;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public BigDecimal getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(BigDecimal orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	public BigDecimal getPrNumber() {
		return prNumber;
	}

	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}

	public BigDecimal getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}

	public BigDecimal getRecertNumber() {
		return recertNumber;
	}

	public void setRecertNumber(BigDecimal recertNumber) {
		this.recertNumber = recertNumber;
	}

	public String getSourceHub() {
		return sourceHub;
	}

	public void setSourceHub(String sourceHub) {
		this.sourceHub = sourceHub;
	}

	public BigDecimal getTotalAvailable() {
		return totalAvailable;
	}

	public void setTotalAvailable(BigDecimal totalAvailable) {
		this.totalAvailable = totalAvailable;
	}

	public BigDecimal getTotalPendingReturn() {
		return totalPendingReturn;
	}

	public void setTotalPendingReturn(BigDecimal totalPendingReturn) {
		this.totalPendingReturn = totalPendingReturn;
	}

	public BigDecimal getTotalReturned() {
		return totalReturned;
	}

	public void setTotalReturned(BigDecimal totalReturned) {
		this.totalReturned = totalReturned;
	}

	public BigDecimal getTotalShipped() {
		return totalShipped;
	}

	public void setTotalShipped(BigDecimal totalShipped) {
		this.totalShipped = totalShipped;
	}

	public BigDecimal getIssueReturnApprovedQuantity() {
		return issueReturnApprovedQuantity;
	}

	public void setIssueReturnApprovedQuantity(BigDecimal issueReturnApprovedQuantity) {
		this.issueReturnApprovedQuantity = issueReturnApprovedQuantity;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getReturnIssue() {
		return returnIssue;
	}

	public void setReturnIssue(String returnIssue) {
		this.returnIssue = returnIssue;
	}

	public BigDecimal getCoTotalReturnedQuantity() {
		return coTotalReturnedQuantity;
	}

	public void setCoTotalReturnedQuantity(BigDecimal coTotalReturnedQuantity) {
		this.coTotalReturnedQuantity = coTotalReturnedQuantity;
	}

	public BigDecimal getCrTotalReturnedQuantity() {
		return crTotalReturnedQuantity;
	}

	public void setCrTotalReturnedQuantity(BigDecimal crTotalReturnedQuantity) {
		this.crTotalReturnedQuantity = crTotalReturnedQuantity;
	}

	public BigDecimal getCoNewReturnedQuantity() {
		return coNewReturnedQuantity;
	}

	public void setCoNewReturnedQuantity(BigDecimal coNewReturnedQuantity) {
		this.coNewReturnedQuantity = coNewReturnedQuantity;
	}

	public String getInventoryGroupName() {
		return inventoryGroupName;
	}

	public void setInventoryGroupName(String inventoryGroupName) {
		this.inventoryGroupName = inventoryGroupName;
	}

	public String getReturnStatus() {
		return returnStatus;
	}

	public void setReturnStatus(String returnStatus) {
		this.returnStatus = returnStatus;
	}
}