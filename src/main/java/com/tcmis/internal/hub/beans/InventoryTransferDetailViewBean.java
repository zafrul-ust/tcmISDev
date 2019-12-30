package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: InventoryTransferDetailViewBean <br>
 * @version: 1.0, Nov 30, 2006 <br>
 *****************************************************************************/


public class InventoryTransferDetailViewBean extends BaseDataBean {

	private BigDecimal transferRequestId;
	private String docType;
	private BigDecimal receiptId;
	private BigDecimal originalReceiptId;
	private BigDecimal quantity;
	private String mfgLot;
	private String bin;
	private Date dateDelivered;
	private Date transactionDate;
	private Date qcDate;
	private BigDecimal itemId;
	private String hub;
	private String inventoryGroup; 


	//constructor
	public InventoryTransferDetailViewBean() {
	}

	//setters
	public void setTransferRequestId(BigDecimal transferRequestId) {
		this.transferRequestId = transferRequestId;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}
	public void setOriginalReceiptId(BigDecimal originalReceiptId) {
		this.originalReceiptId = originalReceiptId;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public void setMfgLot(String mfgLot) {
		this.mfgLot = mfgLot;
	}
	public void setBin(String bin) {
		this.bin = bin;
	}
	public void setDateDelivered(Date dateDelivered) {
		this.dateDelivered = dateDelivered;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public void setQcDate(Date qcDate) {
		this.qcDate = qcDate;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setHub(String hub) {
		this.hub = hub;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}


	//getters
	public BigDecimal getTransferRequestId() {
		return transferRequestId;
	}
	public String getDocType() {
		return docType;
	}
	public BigDecimal getReceiptId() {
		return receiptId;
	}
	public BigDecimal getOriginalReceiptId() {
		return originalReceiptId;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public String getMfgLot() {
		return mfgLot;
	}
	public String getBin() {
		return bin;
	}
	public Date getDateDelivered() {
		return dateDelivered;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public Date getQcDate() {
		return qcDate;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public String getHub() {
		return hub;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
}