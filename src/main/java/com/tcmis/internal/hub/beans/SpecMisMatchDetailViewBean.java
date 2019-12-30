package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

public class SpecMisMatchDetailViewBean extends BaseDataBean {

	private String mismatch;
	private String mismatchComment;
	private String issueSeverity;
	private String issueComment;
	private String dropship;
	private BigDecimal receiptPo;
	private BigDecimal receiptPoLine;
	private Date promiseDate;
	private String customerName;
	private String inventoryGroup;
	private String mrLine;
	private String facPartNo;
	private String partDescription;
	private BigDecimal receiptId;
	private BigDecimal quantity;
	private String boughtForThis;
	private String partSpecRequirement;
	private String specOnReceipt;
	private String shipToAddress;
	
	//constructor
	public SpecMisMatchDetailViewBean() {
	}
	
	public String getMismatch() {
		return mismatch;
	}
	public void setMismatch(String mismatch) {
		this.mismatch = mismatch;
	}
	public String getMismatchComment() {
		return mismatchComment;
	}
	public void setMismatchComment(String mismatchComment) {
		this.mismatchComment = mismatchComment;
	}
	public String getIssueSeverity() {
		return issueSeverity;
	}
	public void setIssueSeverity(String issueSeverity) {
		this.issueSeverity = issueSeverity;
	}
	public String getIssueComment() {
		return issueComment;
	}
	public void setIssueComment(String issueComment) {
		this.issueComment = issueComment;
	}
	
	public String getDropship() {
		return dropship;
	}

	public void setDropship(String dropship) {
		this.dropship = dropship;
	}

	public BigDecimal getReceiptPo() {
		return receiptPo;
	}
	public void setReceiptPo(BigDecimal receiptPo) {
		this.receiptPo = receiptPo;
	}
	public BigDecimal getReceiptPoLine() {
		return receiptPoLine;
	}
	public void setReceiptPoLine(BigDecimal receiptPoLine) {
		this.receiptPoLine = receiptPoLine;
	}
	public Date getPromiseDate() {
		return promiseDate;
	}
	public void setPromiseDate(Date promiseDate) {
		this.promiseDate = promiseDate;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public String getMrLine() {
		return mrLine;
	}
	public void setMrLine(String mrLine) {
		this.mrLine = mrLine;
	}
	public String getFacPartNo() {
		return facPartNo;
	}
	public void setFacPartNo(String facPartNo) {
		this.facPartNo = facPartNo;
	}
	public String getPartDescription() {
		return partDescription;
	}
	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}
	
	public BigDecimal getReceiptId() {
		return receiptId;
	}
	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public String getBoughtForThis() {
		return boughtForThis;
	}
	public void setBoughtForThis(String boughtForThis) {
		this.boughtForThis = boughtForThis;
	}
	public String getPartSpecRequirement() {
		return partSpecRequirement;
	}
	public void setPartSpecRequirement(String partSpecRequirement) {
		this.partSpecRequirement = partSpecRequirement;
	}
	public String getSpecOnReceipt() {
		return specOnReceipt;
	}
	public void setSpecOnReceipt(String specOnReceipt) {
		this.specOnReceipt = specOnReceipt;
	}
	public String getShipToAddress() {
		return shipToAddress;
	}
	public void setShipToAddress(String shipToAddress) {
		this.shipToAddress = shipToAddress;
	}
	
	
	
	
}
