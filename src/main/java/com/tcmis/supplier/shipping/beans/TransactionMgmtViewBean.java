package com.tcmis.supplier.shipping.beans;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.BeanHandler;
import org.apache.struts.action.ActionForm;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;

public class TransactionMgmtViewBean extends BaseDataBean {
	private String active;
	private BigDecimal availableQty;
	private String barcode;
	private String bin;
	private String blanketPo;
	private String bolTrackingNum;
	private String canConvertPartNo;
	private String catalogCompanyId;
	private String catalogId;
	private String catPartNo;
	private String companyId;
	private String conversionGroup;
	private String customerPoNo;
	private BigDecimal customerPoQty;
	private Date dateOfManufacture;
	private String domRequired;
	private Date expireDate;
	private String gfp;
	private String history;
	private BigDecimal inventoryId;
	private BigDecimal inventoryLevelId;
	private String locationDesc;
	private String locationShortName;
	private String mfgLot;
	private String notes;
	private BigDecimal openQty;
	private String partDescription;
	private BigDecimal purchaseQty;
	private BigDecimal quantity;
	private String radianPoLine;
	private BigDecimal reorderPoint;
	private String shipFromLocationId;
	private BigDecimal sortOrder;
	private String status;
	private BigDecimal stockingLevel;
	private String supplier;
	private String supplierPoNumber;
	private Date transactionDate;
	private BigDecimal transactionId;
	private String transactionType;
	private String transactionUserName;
	private String uom;
	private String vmi;
    
    public TransactionMgmtViewBean() {}
    
	public TransactionMgmtViewBean(ActionForm form, Locale locale) throws Exception {
		try {
			BeanHandler.copyAttributes(form, this, locale);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public BigDecimal getReorderPoint() {
		return reorderPoint;
	}

	public void setReorderPoint(BigDecimal reorderPoint) {
		this.reorderPoint = reorderPoint;
	}

	public BigDecimal getStockingLevel() {
		return stockingLevel;
	}

	public void setStockingLevel(BigDecimal stockingLevel) {
		this.stockingLevel = stockingLevel;
	}

	public BigDecimal getPurchaseQty() {
		return purchaseQty;
	}

	public void setPurchaseQty(BigDecimal purchaseQty) {
		this.purchaseQty = purchaseQty;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public BigDecimal getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(BigDecimal inventoryId) {
		this.inventoryId = inventoryId;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getCustomerPoNo() {
		return customerPoNo;
	}

	public void setCustomerPoNo(String customerPoNo) {
		this.customerPoNo = customerPoNo;
	}

	public BigDecimal getCustomerPoQty() {
		return customerPoQty;
	}

	public void setCustomerPoQty(BigDecimal customerPoQty) {
		this.customerPoQty = customerPoQty;
	}

	public Date getDateOfManufacture() {
		return dateOfManufacture;
	}

	public void setDateOfManufacture(Date dateOfManufacture) {
		this.dateOfManufacture = dateOfManufacture;
	}

	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	public BigDecimal getInventoryLevelId() {
		return inventoryLevelId;
	}

	public void setInventoryLevelId(BigDecimal inventoryLevelId) {
		this.inventoryLevelId = inventoryLevelId;
	}

	public String getLocationDesc() {
		return locationDesc;
	}

	public void setLocationDesc(String locationDesc) {
		this.locationDesc = locationDesc;
	}

	public String getMfgLot() {
		return mfgLot;
	}

	public void setMfgLot(String mfgLot) {
		this.mfgLot = mfgLot;
	}

	public BigDecimal getOpenQty() {
		return openQty;
	}

	public void setOpenQty(BigDecimal openQty) {
		this.openQty = openQty;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public String getRadianPoLine() {
		return radianPoLine;
	}

	public void setRadianPoLine(String radianPoLine) {
		this.radianPoLine = radianPoLine;
	}

	public String getShipFromLocationId() {
		return shipFromLocationId;
	}

	public void setShipFromLocationId(String shipFromLocationId) {
		this.shipFromLocationId = shipFromLocationId;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getSupplierPoNumber() {
		return supplierPoNumber;
	}

	public void setSupplierPoNumber(String supplierPoNumber) {
		this.supplierPoNumber = supplierPoNumber;
	}

	public BigDecimal getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(BigDecimal transactionId) {
		this.transactionId = transactionId;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getTransactionUserName() {
		return transactionUserName;
	}

	public void setTransactionUserName(String transactionUserName) {
		this.transactionUserName = transactionUserName;
	}

	public String getDomRequired() {
		return domRequired;
	}

	public void setDomRequired(String domRequired) {
		this.domRequired = domRequired;
	}

	public BigDecimal getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(BigDecimal sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getBlanketPo() {
		return blanketPo;
	}

	public void setBlanketPo(String blanketPo) {
		this.blanketPo = blanketPo;
	}

	public String getGfp() {
		return gfp;
	}

	public void setGfp(String gfp) {
		this.gfp = gfp;
	}

	public BigDecimal getAvailableQty() {
		return availableQty;
	}

	public void setAvailableQty(BigDecimal availableQty) {
		this.availableQty = availableQty;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getVmi() {
		return vmi;
	}

	public void setVmi(String vmi) {
		this.vmi = vmi;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getCanConvertPartNo() {
		return canConvertPartNo;
	}

	public void setCanConvertPartNo(String canConvertPartNo) {
		this.canConvertPartNo = canConvertPartNo;
	}

	public String getPartDescription() {
		return partDescription;
	}

	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}

	public String getLocationShortName() {
		return locationShortName;
	}

	public void setLocationShortName(String locationShortName) {
		this.locationShortName = locationShortName;
	}

	public String getConversionGroup() {
		return conversionGroup;
	}

	public void setConversionGroup(String conversionGroup) {
		this.conversionGroup = conversionGroup;
	}
}