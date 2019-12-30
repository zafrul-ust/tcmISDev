package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: ReceivingHistoryViewBean <br>
 * @version: 1.0, Mar 13, 2006 <br>
 *****************************************************************************/


public class ReceivingHistoryViewBean extends BaseDataBean {

	private String approved;
	private String bin;
	private Date dateOfReceipt;
	private String deliveryTicket;
	private String hub;
	private String inventoryGroup;
	private BigDecimal itemId;
	private String mfgLot;
	private String notes;
	private BigDecimal poLine;
	private BigDecimal qaStatement;
	private Date qcDate;
	private BigDecimal quantityReceived;
	private BigDecimal radianPo;
	private BigDecimal receiptId;
	private String supplierName;
	private String virtualRma;
	private String lotStatus;
	private String receiptDocumentAvailable;
	private Date expireDate;


	//constructor
	public ReceivingHistoryViewBean() {
	}

	public String getApproved() {
		return approved;
	}
	public String getBin() {
		return bin;
	}
	public Date getDateOfReceipt() {
		return dateOfReceipt;
	}
	public String getDeliveryTicket() {
		return deliveryTicket;
	}
	public String getHub() {
		return hub;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public String getMfgLot() {
		return mfgLot;
	}
	public String getNotes() {
		return notes;
	}
	public BigDecimal getPoLine() {
		return poLine;
	}
	public BigDecimal getQaStatement() {
		return qaStatement;
	}
	public Date getQcDate() {
		return qcDate;
	}
	public BigDecimal getQuantityReceived() {
		return quantityReceived;
	}
	//getters
	public BigDecimal getRadianPo() {
		return radianPo;
	}


	public BigDecimal getReceiptId() {
		return receiptId;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public String getVirtualRma() {
		return virtualRma;
	}
	
	public String getLotStatus() {
		return lotStatus;
	}
	
	public String getReceiptDocumentAvailable() {
		return receiptDocumentAvailable;
	}
	
	public Date getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	
	public void setReceiptDocumentAvailable(String receiptDocumentAvailable) {
		this.receiptDocumentAvailable = receiptDocumentAvailable;
	}
	
	public void setLotStatus(String lotStatus) {
		this.lotStatus =lotStatus;
	}
	
	public void setApproved(String approved) {
		this.approved = approved;
	}
	public void setBin(String bin) {
		this.bin = bin;
	}
	public void setDateOfReceipt(Date dateOfReceipt) {
		this.dateOfReceipt = dateOfReceipt;
	}
	public void setDeliveryTicket(String deliveryTicket) {
		this.deliveryTicket = deliveryTicket;
	}
	public void setHub(String hub) {
		this.hub = hub;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setMfgLot(String mfgLot) {
		this.mfgLot = mfgLot;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public void setPoLine(BigDecimal poLine) {
		this.poLine = poLine;
	}
	public void setQaStatement(BigDecimal qaStatement) {
		this.qaStatement = qaStatement;
	}

	public void setQcDate(Date qcDate) {
		this.qcDate = qcDate;
	}

	public void setQuantityReceived(BigDecimal quantityReceived) {
		this.quantityReceived = quantityReceived;
	}

	//setters
	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}

	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public void setVirtualRma(String virtualRma) {
		this.virtualRma = virtualRma;
	}
}