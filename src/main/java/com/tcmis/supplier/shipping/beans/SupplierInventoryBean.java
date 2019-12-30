package com.tcmis.supplier.shipping.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

public class SupplierInventoryBean extends BaseDataBean {
	private BigDecimal adjustedQty;
	private BigDecimal availableQty;
	private String bin;
	private String bolTrackingNum;
	private String catalogCompanyId;
	private String catalogId;
	private String catPartNo;
	private String companyId;
	private Date dateOfManufacture;
	private Date expireDate;
	private BigDecimal insertedBy;
	private Date insertedDate;
	private BigDecimal inventoryId;
	private BigDecimal issuedQty;
	private String mfgLot;
	private BigDecimal pickedQty;
	private String poNumber;
	private BigDecimal quantity;
	private String shipFromLocationId;
	private String status;
	private String supplier;
	private BigDecimal updatedBy;
	private Date updatedDate;
	
	public BigDecimal getAdjustedQty() {
		return adjustedQty;
	}
	
	public void setAdjustedQty(BigDecimal adjustedQty) {
		this.adjustedQty = adjustedQty;
	}

	public String getBin() {
		return bin;
	}

	public void setBin(String bin) {
		this.bin = bin;
	}

	public String getBolTrackingNum() {
		return bolTrackingNum;
	}

	public void setBolTrackingNum(String bolTrackingNum) {
		this.bolTrackingNum = bolTrackingNum;
	}

	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}

	public void setCatalogCompanyId(String catalogCompanyId) {
		this.catalogCompanyId = catalogCompanyId;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

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

	public Date getDateOfManufacture() {
		return dateOfManufacture;
	}

	public void setDateOfManufacture(Date dateOfManufacture) {
		this.dateOfManufacture = dateOfManufacture;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public BigDecimal getInsertedBy() {
		return insertedBy;
	}

	public void setInsertedBy(BigDecimal insertedBy) {
		this.insertedBy = insertedBy;
	}

	public Date getInsertedDate() {
		return insertedDate;
	}

	public void setInsertedDate(Date insertedDate) {
		this.insertedDate = insertedDate;
	}

	public BigDecimal getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(BigDecimal inventoryId) {
		this.inventoryId = inventoryId;
	}

	public BigDecimal getIssuedQty() {
		return issuedQty;
	}

	public void setIssuedQty(BigDecimal issuedQty) {
		this.issuedQty = issuedQty;
	}

	public String getMfgLot() {
		return mfgLot;
	}

	public void setMfgLot(String mfgLot) {
		this.mfgLot = mfgLot;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public String getShipFromLocationId() {
		return shipFromLocationId;
	}

	public void setShipFromLocationId(String shipFromLocationId) {
		this.shipFromLocationId = shipFromLocationId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public BigDecimal getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(BigDecimal updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public BigDecimal getPickedQty() {
		return pickedQty;
	}

	public void setPickedQty(BigDecimal pickedQty) {
		this.pickedQty = pickedQty;
	}

	public BigDecimal getAvailableQty() {
		return availableQty;
	}

	public void setAvailableQty(BigDecimal availableQty) {
		this.availableQty = availableQty;
	}
}