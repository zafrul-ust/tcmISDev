package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: ConsignedInventoryViewBean <br>
 * @version: 1.0, Apr 29, 2008 <br>
 *****************************************************************************/


public class ConsignedInventoryViewBean extends BaseDataBean {

	private String inventoryGroup;
	private String supplier;
	private String supplierName;
	private String branchPlant;
	private String hubName;
	private BigDecimal itemId;
	private BigDecimal po;
	private BigDecimal unitPrice;
	private BigDecimal receiptId;
	private String mfgLot;
	private Date dateOfReceipt;
	private BigDecimal issued;
	private String purchasingUnitOfMeasure;
	private Date shipConfirmDate;
	private String itemDesc;
	private String catPartNo;


	//constructor
	public ConsignedInventoryViewBean() {
	}

	//setters
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public void setBranchPlant(String branchPlant) {
		this.branchPlant = branchPlant;
	}
	public void setHubName(String hubName) {
		this.hubName = hubName;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setPo(BigDecimal po) {
		this.po = po;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}
	public void setMfgLot(String mfgLot) {
		this.mfgLot = mfgLot;
	}
	public void setDateOfReceipt(Date dateOfReceipt) {
		this.dateOfReceipt = dateOfReceipt;
	}
	public void setIssued(BigDecimal issued) {
		this.issued = issued;
	}
	public void setPurchasingUnitOfMeasure(String purchasingUnitOfMeasure) {
		this.purchasingUnitOfMeasure = purchasingUnitOfMeasure;
	}
	public void setShipConfirmDate(Date shipConfirmDate) {
		this.shipConfirmDate = shipConfirmDate;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}


	//getters
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public String getSupplier() {
		return supplier;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public String getBranchPlant() {
		return branchPlant;
	}
	public String getHubName() {
		return hubName;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public BigDecimal getPo() {
		return po;
	}
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public BigDecimal getReceiptId() {
		return receiptId;
	}
	public String getMfgLot() {
		return mfgLot;
	}
	public Date getDateOfReceipt() {
		return dateOfReceipt;
	}
	public BigDecimal getIssued() {
		return issued;
	}
	public String getPurchasingUnitOfMeasure() {
		return purchasingUnitOfMeasure;
	}
	public Date getShipConfirmDate() {
		return shipConfirmDate;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public String getCatPartNo() {
		return catPartNo;
	}
}