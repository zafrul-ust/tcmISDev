package com.tcmis.ws.scanner.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: PoLineBean <br>
 * 
 * @version: 1.0, Mar 7, 2013 <br>
 *****************************************************************************/

public class PickValidation extends BaseDataBean {
	private boolean		binScanned;
	private boolean		containerBarcodeApplied;
	private boolean		containerBarcodeValidated;
	private boolean		customerPartNumberValidated;
	private boolean		expirationValidated;
	private boolean		itemIdValidated;
	private boolean		lotNumberValidated;
	private boolean		lotStatusValidated;
	private boolean		materialValidated;
	private boolean		packageValidated;
	private BigDecimal	qcQuantity;
	private boolean		receiptScanned;
	private boolean		receiptValidated;

	public BigDecimal getQcQuantity() {
		return this.qcQuantity;
	}

	public boolean isBinScanned() {
		return this.binScanned;
	}

	public boolean isContainerBarcodeApplied() {
		return this.containerBarcodeApplied;
	}

	public boolean isContainerBarcodeValidated() {
		return this.containerBarcodeValidated;
	}

	public boolean isCustomerPartNumberValidated() {
		return this.customerPartNumberValidated;
	}

	public boolean isExpirationValidated() {
		return this.expirationValidated;
	}

	public boolean isItemIdValidated() {
		return this.itemIdValidated;
	}

	public boolean isLotNumberValidated() {
		return this.lotNumberValidated;
	}

	public boolean isLotStatusValidated() {
		return this.lotStatusValidated;
	}

	public boolean isMaterialValidated() {
		return this.materialValidated;
	}

	public boolean isPackageValidated() {
		return this.packageValidated;
	}

	public boolean isReceiptScanned() {
		return this.receiptScanned;
	}

	public boolean isReceiptValidated() {
		return this.receiptValidated;
	}

	public void setBinScanned(boolean binScanned) {
		this.binScanned = binScanned;
	}

	public void setContainerBarcodeApplied(boolean containerBarcodeApplied) {
		this.containerBarcodeApplied = containerBarcodeApplied;
	}

	public void setContainerBarcodeValidated(boolean containerBarcodeValidated) {
		this.containerBarcodeValidated = containerBarcodeValidated;
	}

	public void setCustomerPartNumberValidated(boolean customerPartNumberValidated) {
		this.customerPartNumberValidated = customerPartNumberValidated;
	}

	public void setExpirationValidated(boolean expirationValidated) {
		this.expirationValidated = expirationValidated;
	}

	public void setItemIdValidated(boolean itemIdValidated) {
		this.itemIdValidated = itemIdValidated;
	}

	public void setLotNumberValidated(boolean lotNumberValidated) {
		this.lotNumberValidated = lotNumberValidated;
	}

	public void setLotStatusValidated(boolean lotStatusValidated) {
		this.lotStatusValidated = lotStatusValidated;
	}

	public void setMaterialValidated(boolean materialValidated) {
		this.materialValidated = materialValidated;
	}

	public void setPackageValidated(boolean packageValidated) {
		this.packageValidated = packageValidated;
	}

	public void setQcQuantity(BigDecimal qcQuantity) {
		this.qcQuantity = qcQuantity;
	}

	public void setReceiptScanned(boolean receiptScanned) {
		this.receiptScanned = receiptScanned;
	}

	public void setReceiptValidated(boolean receiptValidated) {
		this.receiptValidated = receiptValidated;
	}
}