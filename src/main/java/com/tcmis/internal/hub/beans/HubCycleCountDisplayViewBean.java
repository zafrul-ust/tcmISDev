package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

public class HubCycleCountDisplayViewBean extends BaseDataBean {

	private BigDecimal receiptId;
	private BigDecimal itemId;
	private String mfgLot;
	private String bin;
	private String room; 
	private Date dateOfReceipt;
	private Date expireDate;
	private BigDecimal expectedQuantity;
	private BigDecimal actualQuantity;
	private String lotStatus;
	private String hub;
	private BigDecimal counterId;
	private String unknownReceipt;
	private String comments;
	private BigDecimal countId;
	private Date dateUpdated;
	private BigDecimal approvedBy;
	private Date dateApproved;
	private Date countDatetime; 
	private String countStatus;
	private String countType;
	private BigDecimal countMonth; 
	private String packaging;
	private String itemDesc;	
	
	public BigDecimal getReceiptId() {
		return receiptId;
	}
	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public String getMfgLot() {
		return mfgLot;
	}
	public void setMfgLot(String mfgLot) {
		this.mfgLot = mfgLot;
	}
	public String getBin() {
		return bin;
	}
	public void setBin(String bin) {
		this.bin = bin;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	public Date getDateOfReceipt() {
		return dateOfReceipt;
	}
	public void setDateOfReceipt(Date dateOfReceipt) {
		this.dateOfReceipt = dateOfReceipt;
	}
	public Date getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	public BigDecimal getExpectedQuantity() {
		return expectedQuantity;
	}
	public void setExpectedQuantity(BigDecimal expectedQuantity) {
		this.expectedQuantity = expectedQuantity;
	}
	public BigDecimal getActualQuantity() {
		return actualQuantity;
	}
	public void setActualQuantity(BigDecimal actualQuantity) {
		this.actualQuantity = actualQuantity;
	}
	public String getLotStatus() {
		return lotStatus;
	}
	public void setLotStatus(String lotStatus) {
		this.lotStatus = lotStatus;
	}
	public String getHub() {
		return hub;
	}
	public void setHub(String hub) {
		this.hub = hub;
	}
	public BigDecimal getCounterId() {
		return counterId;
	}
	public void setCounterId(BigDecimal counterId) {
		this.counterId = counterId;
	}
	public String getUnknownReceipt() {
		return unknownReceipt;
	}
	public void setUnknownReceipt(String unknownReceipt) {
		this.unknownReceipt = unknownReceipt;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public BigDecimal getCountId() {
		return countId;
	}
	public void setCountId(BigDecimal countId) {
		this.countId = countId;
	}
	public Date getDateUpdated() {
		return dateUpdated;
	}
	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}
	public BigDecimal getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(BigDecimal approvedBy) {
		this.approvedBy = approvedBy;
	}
	public Date getDateApproved() {
		return dateApproved;
	}
	public void setDateApproved(Date dateApproved) {
		this.dateApproved = dateApproved;
	}
	public Date getCountDatetime() {
		return countDatetime;
	}
	public void setCountDatetime(Date countDatetime) {
		this.countDatetime = countDatetime;
	}
	public String getCountStatus() {
		return countStatus;
	}
	public void setCountStatus(String countStatus) {
		this.countStatus = countStatus;
	}
	public String getCountType() {
		return countType;
	}
	public void setCountType(String countType) {
		this.countType = countType;
	}
	public BigDecimal getCountMonth() {
		return countMonth;
	}
	public void setCountMonth(BigDecimal countMonth) {
		this.countMonth = countMonth;
	}
	public String getPackaging() {
		return packaging;
	}
	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
}
