package com.tcmis.ws.scanner.beans;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;


public class Receipt extends BaseDataBean {
	String		bin;
	BigDecimal	cabinetId;
	BigDecimal	containerNumber;
	String		customerReceiptId;
	Date		dateOfManufacture;
	Date		dateOfReceipt;
	Date		depletedDate;
	String		excess;
	Date		expireDate;
	String		inventoryGroup;
	BigDecimal	itemId;
	Timestamp	lastUpdated;
	String		mfgLot;
	BigDecimal	partId;
	Date		qcDate;
	BigDecimal	receiptId;
	String		storageTemp;

	public Receipt() {
		super();
	};

	public String getBin() {
		return this.bin;
	}

	public BigDecimal getCabinetId() {
		return cabinetId;
	}

	public BigDecimal getContainerNumber() {
		return containerNumber;
	}

	public String getCustomerReceiptId() {
		return customerReceiptId;
	}

	public Date getDateOfManufacture() {
		return this.dateOfManufacture;
	}

	public Date getDateOfReceipt() {
		return this.dateOfReceipt;
	}

	public Date getDepletedDate() {
		return this.depletedDate;
	}

	public String getExcess() {
		return this.excess;
	}

	public Date getExpireDate() {
		return this.expireDate;
	}

	public String getInventoryGroup() {
		return this.inventoryGroup;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public Timestamp getLastUpdated() {
		return this.lastUpdated;
	}

	public String getMfgLot() {
		return this.mfgLot;
	}

	public BigDecimal getPartId() {
		return this.partId;
	}

	public Date getQcDate() {
		return this.qcDate;
	}

	public BigDecimal getReceiptId() {
		return receiptId;
	}

	public String getStorageTemp() {
		return this.storageTemp;
	}

	public void setBin(String bin) {
		this.bin = bin;
	}

	public void setCabinetId(BigDecimal cabinetId) {
		this.cabinetId = cabinetId;
	}

	public void setContainerNumber(BigDecimal containerNumber) {
		this.containerNumber = containerNumber;
	}

	public void setCustomerReceiptId(String customerReceiptId) {
		this.customerReceiptId = customerReceiptId;
	}

	public void setDateOfManufacture(Date dateOfManufacture) {
		this.dateOfManufacture = dateOfManufacture;
	}

	public void setDateOfReceipt(Date dateOfReceipt) {
		this.dateOfReceipt = dateOfReceipt;
	}

	public void setDepletedDate(Date depletedDate) {
		this.depletedDate = depletedDate;
	}

	public void setExcess(String excess) {
		this.excess = excess;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public void setLastUpdated(Timestamp lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public void setMfgLot(String mfgLot) {
		this.mfgLot = mfgLot;
	}

	public void setPartId(BigDecimal partId) {
		this.partId = partId;
	}

	public void setQcDate(Date qcDate) {
		this.qcDate = qcDate;
	}

	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}

	public void setStorageTemp(String storageTemp) {
		this.storageTemp = storageTemp;
	}
}
