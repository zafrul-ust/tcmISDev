package com.tcmis.internal.supply.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: PoLineBean <br>
 * @version: 1.0, Aug 12, 2014 <br>
 *****************************************************************************/


public class PoLineBean extends BaseDataBean {

	private BigDecimal radianPo;
	private BigDecimal poLine;
	private BigDecimal amendment;
	private BigDecimal itemId;
	private BigDecimal quantity;
	private BigDecimal unitPrice;
	private Date needDate;
	private Date promisedDate;
	private BigDecimal allowedPriceVariance;
	private String mfgPartNo;
	private String supplierPartNo;
	private String dpasRating;
	private String qualityFlowDowns;
	private String packagingFlowDowns;
	private String poLineNote;
	private Date dateConfirmed;
	private BigDecimal supplierQty;
	private String supplierPkg;
	private BigDecimal supplierUnitPrice;
	private String genericCoc;
	private String genericCoa;
	private Date msdsRequestedDate;
	private BigDecimal remainingShelfLifePercent;
	private String remarks;
	private BigDecimal id;
	private String archived;
	private String deliveryComments;
	private Date vendorShipDate;
	private String supplier;
	private BigDecimal totalQuantityReceived;
	private BigDecimal transactionUser;
	private String currencyId;
	private BigDecimal totalQuantityReturned;
	private BigDecimal totalQuantityReceivedQc;
	private BigDecimal purchasingUnitsPerItem;
	private String purchasingUnitOfMeasure;
	private Date dateFirstConfirmed;
	private String poLineType;
	private String shipFromLocationId;
	private String supplierSalesOrderNo;
	private String openPo;
	private String inventoryGroup;
	private String legacyPartNo;
	private String opsEntityId;
	private Date supplierDateAccepted;


	//constructor
	public PoLineBean() {
	}

	//setters
	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}
	public void setPoLine(BigDecimal poLine) {
		this.poLine = poLine;
	}
	public void setAmendment(BigDecimal amendment) {
		this.amendment = amendment;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	public void setNeedDate(Date needDate) {
		this.needDate = needDate;
	}
	public void setPromisedDate(Date promisedDate) {
		this.promisedDate = promisedDate;
	}
	public void setAllowedPriceVariance(BigDecimal allowedPriceVariance) {
		this.allowedPriceVariance = allowedPriceVariance;
	}
	public void setMfgPartNo(String mfgPartNo) {
		this.mfgPartNo = mfgPartNo;
	}
	public void setSupplierPartNo(String supplierPartNo) {
		this.supplierPartNo = supplierPartNo;
	}
	public void setDpasRating(String dpasRating) {
		this.dpasRating = dpasRating;
	}
	public void setQualityFlowDowns(String qualityFlowDowns) {
		this.qualityFlowDowns = qualityFlowDowns;
	}
	public void setPackagingFlowDowns(String packagingFlowDowns) {
		this.packagingFlowDowns = packagingFlowDowns;
	}
	public void setPoLineNote(String poLineNote) {
		this.poLineNote = poLineNote;
	}
	public void setDateConfirmed(Date dateConfirmed) {
		this.dateConfirmed = dateConfirmed;
	}
	public void setSupplierQty(BigDecimal supplierQty) {
		this.supplierQty = supplierQty;
	}
	public void setSupplierPkg(String supplierPkg) {
		this.supplierPkg = supplierPkg;
	}
	public void setSupplierUnitPrice(BigDecimal supplierUnitPrice) {
		this.supplierUnitPrice = supplierUnitPrice;
	}
	public void setGenericCoc(String genericCoc) {
		this.genericCoc = genericCoc;
	}
	public void setGenericCoa(String genericCoa) {
		this.genericCoa = genericCoa;
	}
	public void setMsdsRequestedDate(Date msdsRequestedDate) {
		this.msdsRequestedDate = msdsRequestedDate;
	}
	public void setRemainingShelfLifePercent(BigDecimal remainingShelfLifePercent) {
		this.remainingShelfLifePercent = remainingShelfLifePercent;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public void setId(BigDecimal id) {
		this.id = id;
	}
	public void setArchived(String archived) {
		this.archived = archived;
	}
	public void setDeliveryComments(String deliveryComments) {
		this.deliveryComments = deliveryComments;
	}
	public void setVendorShipDate(Date vendorShipDate) {
		this.vendorShipDate = vendorShipDate;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public void setTotalQuantityReceived(BigDecimal totalQuantityReceived) {
		this.totalQuantityReceived = totalQuantityReceived;
	}
	public void setTransactionUser(BigDecimal transactionUser) {
		this.transactionUser = transactionUser;
	}
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}
	public void setTotalQuantityReturned(BigDecimal totalQuantityReturned) {
		this.totalQuantityReturned = totalQuantityReturned;
	}
	public void setTotalQuantityReceivedQc(BigDecimal totalQuantityReceivedQc) {
		this.totalQuantityReceivedQc = totalQuantityReceivedQc;
	}
	public void setPurchasingUnitsPerItem(BigDecimal purchasingUnitsPerItem) {
		this.purchasingUnitsPerItem = purchasingUnitsPerItem;
	}
	public void setPurchasingUnitOfMeasure(String purchasingUnitOfMeasure) {
		this.purchasingUnitOfMeasure = purchasingUnitOfMeasure;
	}
	public void setDateFirstConfirmed(Date dateFirstConfirmed) {
		this.dateFirstConfirmed = dateFirstConfirmed;
	}
	public void setPoLineType(String poLineType) {
		this.poLineType = poLineType;
	}
	public void setShipFromLocationId(String shipFromLocationId) {
		this.shipFromLocationId = shipFromLocationId;
	}
	public void setSupplierSalesOrderNo(String supplierSalesOrderNo) {
		this.supplierSalesOrderNo = supplierSalesOrderNo;
	}
	public void setOpenPo(String openPo) {
		this.openPo = openPo;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setLegacyPartNo(String legacyPartNo) {
		this.legacyPartNo = legacyPartNo;
	}
	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}
	public void setSupplierDateAccepted(Date supplierDateAccepted) {
		this.supplierDateAccepted = supplierDateAccepted;
	}


	//getters
	public BigDecimal getRadianPo() {
		return radianPo;
	}
	public BigDecimal getPoLine() {
		return poLine;
	}
	public BigDecimal getAmendment() {
		return amendment;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public Date getNeedDate() {
		return needDate;
	}
	public Date getPromisedDate() {
		return promisedDate;
	}
	public BigDecimal getAllowedPriceVariance() {
		return allowedPriceVariance;
	}
	public String getMfgPartNo() {
		return mfgPartNo;
	}
	public String getSupplierPartNo() {
		return supplierPartNo;
	}
	public String getDpasRating() {
		return dpasRating;
	}
	public String getQualityFlowDowns() {
		return qualityFlowDowns;
	}
	public String getPackagingFlowDowns() {
		return packagingFlowDowns;
	}
	public String getPoLineNote() {
		return poLineNote;
	}
	public Date getDateConfirmed() {
		return dateConfirmed;
	}
	public BigDecimal getSupplierQty() {
		return supplierQty;
	}
	public String getSupplierPkg() {
		return supplierPkg;
	}
	public BigDecimal getSupplierUnitPrice() {
		return supplierUnitPrice;
	}
	public String getGenericCoc() {
		return genericCoc;
	}
	public String getGenericCoa() {
		return genericCoa;
	}
	public Date getMsdsRequestedDate() {
		return msdsRequestedDate;
	}
	public BigDecimal getRemainingShelfLifePercent() {
		return remainingShelfLifePercent;
	}
	public String getRemarks() {
		return remarks;
	}
	public BigDecimal getId() {
		return id;
	}
	public String getArchived() {
		return archived;
	}
	public String getDeliveryComments() {
		return deliveryComments;
	}
	public Date getVendorShipDate() {
		return vendorShipDate;
	}
	public String getSupplier() {
		return supplier;
	}
	public BigDecimal getTotalQuantityReceived() {
		return totalQuantityReceived;
	}
	public BigDecimal getTransactionUser() {
		return transactionUser;
	}
	public String getCurrencyId() {
		return currencyId;
	}
	public BigDecimal getTotalQuantityReturned() {
		return totalQuantityReturned;
	}
	public BigDecimal getTotalQuantityReceivedQc() {
		return totalQuantityReceivedQc;
	}
	public BigDecimal getPurchasingUnitsPerItem() {
		return purchasingUnitsPerItem;
	}
	public String getPurchasingUnitOfMeasure() {
		return purchasingUnitOfMeasure;
	}
	public Date getDateFirstConfirmed() {
		return dateFirstConfirmed;
	}
	public String getPoLineType() {
		return poLineType;
	}
	public String getShipFromLocationId() {
		return shipFromLocationId;
	}
	public String getSupplierSalesOrderNo() {
		return supplierSalesOrderNo;
	}
	public String getOpenPo() {
		return openPo;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public String getLegacyPartNo() {
		return legacyPartNo;
	}
	public String getOpsEntityId() {
		return opsEntityId;
	}
	public Date getSupplierDateAccepted() {
		return supplierDateAccepted;
	}

}