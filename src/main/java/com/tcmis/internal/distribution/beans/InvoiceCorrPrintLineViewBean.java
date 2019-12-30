package com.tcmis.internal.distribution.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: InvoiceCorrPrintLineViewBean <br>
 * @version: 1.0, May 12, 2010 <br>
 *****************************************************************************/


public class InvoiceCorrPrintLineViewBean extends BaseDataBean {

	private BigDecimal invoice;
	private BigDecimal prNumber;
	private String lineItem;
	private String alternateName;
	private String partDescription;
	private BigDecimal orderedQuantity;
	private BigDecimal remainingQuantity;
	private BigDecimal unitOfSaleQuantity;
	private String unitOfSale;
	private BigDecimal unitOfSalePrice;
	private BigDecimal adjustedUnitPrice;
	private String currencyId;
	private BigDecimal requiredShelfLife;
	private Date requiredDatetime;
	private String customerPartNo;
	private String customerPoLine;
	private String taxExempt;
	private String deliveryNote;
	private String partShortName;
	private String specList;
	private BigDecimal itemId;
	private String deliveryType;
	private String inventoryGroup;
	private BigDecimal taxRate;
	private Collection charges;
	private String companyId;
	private BigDecimal unitOfSaleQuantityPerEach;
	// for input
	private String deleteCharge;
	private String changed;
	private String customerInvoice;

	//constructor
	public InvoiceCorrPrintLineViewBean() {
	}

	//setters
	public void setInvoice(BigDecimal invoice) {
		this.invoice = invoice;
	}
	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}
	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}
	public void setAlternateName(String alternateName) {
		this.alternateName = alternateName;
	}
	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}
	public void setOrderedQuantity(BigDecimal orderedQuantity) {
		this.orderedQuantity = orderedQuantity;
	}
	public void setRemainingQuantity(BigDecimal remainingQuantity) {
		this.remainingQuantity = remainingQuantity;
	}
	public void setUnitOfSaleQuantity(BigDecimal unitOfSaleQuantity) {
		this.unitOfSaleQuantity = unitOfSaleQuantity;
	}
	public void setUnitOfSale(String unitOfSale) {
		this.unitOfSale = unitOfSale;
	}
	public void setUnitOfSalePrice(BigDecimal unitOfSalePrice) {
		this.unitOfSalePrice = unitOfSalePrice;
	}
	public void setAdjustedUnitPrice(BigDecimal adjustedUnitPrice) {
		this.adjustedUnitPrice = adjustedUnitPrice;
	}
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}
	public void setRequiredShelfLife(BigDecimal requiredShelfLife) {
		this.requiredShelfLife = requiredShelfLife;
	}
	public void setRequiredDatetime(Date requiredDatetime) {
		this.requiredDatetime = requiredDatetime;
	}
	public void setCustomerPartNo(String customerPartNo) {
		this.customerPartNo = customerPartNo;
	}
	public void setCustomerPoLine(String customerPoLine) {
		this.customerPoLine = customerPoLine;
	}
	public void setTaxExempt(String taxExempt) {
		this.taxExempt = taxExempt;
	}
	public void setDeliveryNote(String deliveryNote) {
		this.deliveryNote = deliveryNote;
	}
	public void setPartShortName(String partShortName) {
		this.partShortName = partShortName;
	}
	public void setSpecList(String specList) {
		this.specList = specList;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}


	//getters
	public BigDecimal getInvoice() {
		return invoice;
	}
	public BigDecimal getPrNumber() {
		return prNumber;
	}
	public String getLineItem() {
		return lineItem;
	}
	public String getAlternateName() {
		return alternateName;
	}
	public String getPartDescription() {
		return partDescription;
	}
	public BigDecimal getOrderedQuantity() {
		return orderedQuantity;
	}
	public BigDecimal getRemainingQuantity() {
		return remainingQuantity;
	}
	public BigDecimal getUnitOfSaleQuantity() {
		return unitOfSaleQuantity;
	}
	public String getUnitOfSale() {
		return unitOfSale;
	}
	public BigDecimal getUnitOfSalePrice() {
		return unitOfSalePrice;
	}
	public BigDecimal getAdjustedUnitPrice() {
		return adjustedUnitPrice;
	}
	public String getCurrencyId() {
		return currencyId;
	}
	public BigDecimal getRequiredShelfLife() {
		return requiredShelfLife;
	}
	public Date getRequiredDatetime() {
		return requiredDatetime;
	}
	public String getCustomerPartNo() {
		return customerPartNo;
	}
	public String getCustomerPoLine() {
		return customerPoLine;
	}
	public String getTaxExempt() {
		return taxExempt;
	}
	public String getDeliveryNote() {
		return deliveryNote;
	}
	public String getPartShortName() {
		return partShortName;
	}
	public String getSpecList() {
		return specList;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public String getDeliveryType() {
		return deliveryType;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public BigDecimal getTaxRate() {
		return taxRate;
	}
	public Collection getCharges() {
		return charges;
	}
	public void setCharges(Collection charges) {
		this.charges = charges;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getDeleteCharge() {
		return deleteCharge;
	}
	public void setDeleteCharge(String deleteCharge) {
		this.deleteCharge = deleteCharge;
	}
	public String getChanged() {
		return changed;
	}
	public void setChanged(String changed) {
		this.changed = changed;
	}                  
	public BigDecimal getUnitOfSaleQuantityPerEach() {
		return unitOfSaleQuantityPerEach;
	}
	public void setUnitOfSaleQuantityPerEach(BigDecimal unitOfSaleQuantityPerEach) {
		this.unitOfSaleQuantityPerEach = unitOfSaleQuantityPerEach;
	}

	public String getCustomerInvoice() {
		return customerInvoice;
	}

	public void setCustomerInvoice(String customerInvoice) {
		this.customerInvoice = customerInvoice;
	}
	
	
}