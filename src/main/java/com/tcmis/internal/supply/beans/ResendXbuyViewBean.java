package com.tcmis.internal.supply.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: ResendXbuyViewBean <br>
 * @version: 1.0, Apr 20, 2009 <br>
 * @author: Shahzad Butt
 *****************************************************************************/


public class ResendXbuyViewBean extends BaseDataBean {

	
	private static final long serialVersionUID = 4325852061676818814L;
	
	private BigDecimal radianPo;
	private BigDecimal poLine;
	private BigDecimal itemId;
	private BigDecimal quantity;
	private BigDecimal unitPrice;
	private String currencyDisplay;
	private String currencyId;
	private BigDecimal extendedPrice;
	private Date needDate;
	private Date promisedDate;
	private String supplierPartNo;
	private String poLineNote;
	private String itemDesc;
	private String itemType;
	private String packaging;
	private String deliveryComments;
	private Date vendorShipDate;
	private BigDecimal maxPriceLimit;
	private String supplier;
	private BigDecimal purchasingUnitsPerItem;
	private String purchasingUnitOfMeasure;
	private String buyerCompanyId;
	private String shipFromLocationId;
	private String supplierSalesOrderNo;
	private String supplierAccountNumber;
	private String ok;
	private String inventoryGroup;
    private String shipToAddress;

	//constructor
	public ResendXbuyViewBean() {
	}

	//setters
	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}
	public void setPoLine(BigDecimal poLine) {
		this.poLine = poLine;
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
	public void setCurrencyDisplay(String currencyDisplay) {
		this.currencyDisplay = currencyDisplay;
	}
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}
	public void setExtendedPrice(BigDecimal extendedPrice) {
		this.extendedPrice = extendedPrice;
	}
	public void setNeedDate(Date needDate) {
		this.needDate = needDate;
	}
	public void setPromisedDate(Date promisedDate) {
		this.promisedDate = promisedDate;
	}
	public void setSupplierPartNo(String supplierPartNo) {
		this.supplierPartNo = supplierPartNo;
	}
	public void setPoLineNote(String poLineNote) {
		this.poLineNote = poLineNote;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}
	public void setDeliveryComments(String deliveryComments) {
		this.deliveryComments = deliveryComments;
	}
	public void setVendorShipDate(Date vendorShipDate) {
		this.vendorShipDate = vendorShipDate;
	}	
	public void setMaxPriceLimit(BigDecimal maxPriceLimit) {
		this.maxPriceLimit = maxPriceLimit;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public void setPurchasingUnitsPerItem(BigDecimal purchasingUnitsPerItem) {
		this.purchasingUnitsPerItem = purchasingUnitsPerItem;
	}
	public void setPurchasingUnitOfMeasure(String purchasingUnitOfMeasure) {
		this.purchasingUnitOfMeasure = purchasingUnitOfMeasure;
	}
	public void setBuyerCompanyId(String buyerCompanyId) {
		this.buyerCompanyId = buyerCompanyId;
	}
	public void setShipFromLocationId(String shipFromLocationId) {
		this.shipFromLocationId = shipFromLocationId;
	}
	public void setSupplierSalesOrderNo(String supplierSalesOrderNo) {
		this.supplierSalesOrderNo = supplierSalesOrderNo;
	}	
	public void setSupplierAccountNumber(String supplierAccountNumber) {
		this.supplierAccountNumber = supplierAccountNumber;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}


	//getters
	public BigDecimal getRadianPo() {
		return radianPo;
	}
	public BigDecimal getPoLine() {
		return poLine;
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
	public String getCurrencyDisplay() {
		return currencyDisplay;
	}
	public String getCurrencyId() {
		return currencyId;
	}
	public BigDecimal getExtendedPrice() {
		return extendedPrice;
	}
	public Date getNeedDate() {
		return needDate;
	}
	public Date getPromisedDate() {
		return promisedDate;
	}
	public String getSupplierPartNo() {
		return supplierPartNo;
	}
	public String getPoLineNote() {
		return poLineNote;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public String getItemType() {
		return itemType;
	}
	public String getPackaging() {
		return packaging;
	}
	public String getDeliveryComments() {
		return deliveryComments;
	}
	public Date getVendorShipDate() {
		return vendorShipDate;
	}	
	public BigDecimal getMaxPriceLimit() {
		return maxPriceLimit;
	}
	public String getSupplier() {
		return supplier;
	}
	public BigDecimal getPurchasingUnitsPerItem() {
		return purchasingUnitsPerItem;
	}
	public String getPurchasingUnitOfMeasure() {
		return purchasingUnitOfMeasure;
	}
	public String getBuyerCompanyId() {
		return buyerCompanyId;
	}
	public String getShipFromLocationId() {
		return shipFromLocationId;
	}
	public String getSupplierSalesOrderNo() {
		return supplierSalesOrderNo;
	}
	public String getSupplierAccountNumber() {
		return supplierAccountNumber;
	}

	/**
	 * @param ok the ok to set
	 */
	public void setOk(String ok) {
		this.ok = ok;
	}

	/**
	 * @return the ok
	 */
	public String getOk() {
		return ok;
	}
	
	public String getInventoryGroup() {
		return inventoryGroup;
	}

	/**
	 * @param shipToAddress the shipToAddress to set
	 */
	public void setShipToAddress(String shipToAddress) {
		this.shipToAddress = shipToAddress;
	}

	/**
	 * @return the shipToAddress
	 */
	public String getShipToAddress() {
		return shipToAddress;
	}
}