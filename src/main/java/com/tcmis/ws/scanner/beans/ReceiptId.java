package com.tcmis.ws.scanner.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.internal.hub.beans.ContainerLabelMasterViewBean;

public class ReceiptId extends BaseDataBean {
	private String		barcodeLot;
	private String		bin;
	private String		customerReceiptId;
	private Date		dateOfReceipt;
	private Date		expireDate;
	private boolean		inseparableKit			= false;
	private String		internalReceiptNotes;
	private String		inventoryGroup;
	private BigDecimal	itemId;
	private String		lastBin;
	private Date		lastUpdated;
	private String		lotNumber;
	private String		lotStatus;
	private boolean		manageKitsAsSingleUnit	= false;
	private BigDecimal  originalReceiptId;
	private BigDecimal	quantityOnHand;
	private BigDecimal	receiptId;
	private String		receivingStatus;
	private String		mfgLot;


	public ReceiptId() {
		super();
	}

	public String getBarcodeLot() {
		// Use the same code as used for labels, don't duplicate it
		ContainerLabelMasterViewBean temp = new ContainerLabelMasterViewBean();
		temp.setComponentMfgLots(barcodeLot);
		return temp.getComponentMfgLots();
	}

	public String getBin() {
		return this.bin;
	}

	public String getCustomerReceiptId() {
		return customerReceiptId;
	}

	public Date getDateOfReceipt() {
		return this.dateOfReceipt;
	}

	public Date getExpireDate() {
		return this.expireDate;
	}

	public String getInternalReceiptNotes() {
		return this.internalReceiptNotes;
	}

	public String getInventoryGroup() {
		return this.inventoryGroup;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public String getLastBin() {
		return this.lastBin;
	}

	public Date getLastUpdated() {
		return this.lastUpdated;
	}

	public String getLotNumber() {
		return lotNumber;
	}

	public String getLotStatus() {
		return lotStatus;
	}

	public BigDecimal getQuantityOnHand() {
		return this.quantityOnHand;
	}

	public BigDecimal getReceiptId() {
		return receiptId;
	}

	public String getReceivingStatus() {
		return this.receivingStatus;
	}

	public boolean isInseparableKit() {
		return this.inseparableKit;
	}

	public boolean isManageKitsAsSingleUnit() {
		return this.manageKitsAsSingleUnit;
	}

	public void setBarcodeLot(String barcodeLot) {
		this.barcodeLot = barcodeLot;
	}

	public void setBin(String bin) {
		this.bin = bin;
	}

	public void setCustomerReceiptId(String customerReceiptId) {
		this.customerReceiptId = customerReceiptId;
	}

	public void setDateOfReceipt(Date dateOfReceipt) {
		this.dateOfReceipt = dateOfReceipt;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public void setInseparableKit(boolean inseparableKit) {
		this.inseparableKit = inseparableKit;
	}

	public void setInternalReceiptNotes(String internalReceiptNotes) {
		this.internalReceiptNotes = internalReceiptNotes;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public void setLastBin(String lastBin) {
		this.lastBin = lastBin;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public void setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
	}

	public void setLotStatus(String lotStatus) {
		this.lotStatus = lotStatus;
	}

	public void setManageKitsAsSingleUnit(boolean manageKitsAsSingleUnit) {
		this.manageKitsAsSingleUnit = manageKitsAsSingleUnit;
	}

	public void setQuantityOnHand(BigDecimal quantityOnHand) {
		this.quantityOnHand = quantityOnHand;
	}

	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}

	public void setReceivingStatus(String receivingStatus) {
		this.receivingStatus = receivingStatus;
	}

	public BigDecimal getOriginalReceiptId() {
		return originalReceiptId;
	}

	public void setOriginalReceiptId(BigDecimal originalReceiptId) {
		this.originalReceiptId = originalReceiptId;
	}
}
