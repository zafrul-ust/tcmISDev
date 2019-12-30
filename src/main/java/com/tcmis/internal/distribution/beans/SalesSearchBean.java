package com.tcmis.internal.distribution.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: SalesSearchBean <br>
 * @version: 1.0, Sep 10, 2009 <br>
 *****************************************************************************/

public class SalesSearchBean extends BaseDataBean {

	private String inventoryGroup;
	private String companyId;
	private BigDecimal prNumber;
	private String lineItem;
	private Date releaseDate;
	private String orderStatus;
	private String cancelStatus;
	private String supplyStatus;
	private BigDecimal quantity;
	private String catalogId;
	private String catalogCompanyId;
	private String catPartNo;
	private BigDecimal expectedUnitCost;
	private BigDecimal unitPrice;
	private String currencyId;
	private String customerPartNo;
	private BigDecimal remainingShelfLifePercent;
	private String specList;
	private String alternateName;
	private String opsRegionName;
	private String partShortName;
	private String partDescription;
	private String partNote;
	private BigDecimal customerId;
	private String customerName;
	private String customerPoNumber;
	private String customerPoLine;
	
	private BigDecimal quantityShipped;
	private String paymentTerms;
	private String incoTerms;
	private BigDecimal salesOrder;
	
	private BigDecimal margin;
	private Date lastShipDate;
	
	private String opsEntityId;
	private String inventoryGroupName;

	// from page.
	private BigDecimal itemId;
	private String region;
	private String limitDays;
	private Date startDate;
	private Date endDate;
	private String searchKey;
	
	private BigDecimal qtyPicked;
	private BigDecimal qtyAllocated;
	private BigDecimal qtyInPurchasing;

	private String intercompanyCustomer;
		
	
	//constructor
	public SalesSearchBean() {
	}

	//setters
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}

	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public void setCancelStatus(String cancelStatus) {
		this.cancelStatus = cancelStatus;
	}

	public void setSupplyStatus(String supplyStatus) {
		this.supplyStatus = supplyStatus;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public void setCatalogCompanyId(String catalogCompanyId) {
		this.catalogCompanyId = catalogCompanyId;
	}

	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}

	public void setExpectedUnitCost(BigDecimal expectedUnitCost) {
		this.expectedUnitCost = expectedUnitCost;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	public void setCustomerPartNo(String customerPartNo) {
		this.customerPartNo = customerPartNo;
	}

	public void setRemainingShelfLifePercent(BigDecimal remainingShelfLifePercent) {
		this.remainingShelfLifePercent = remainingShelfLifePercent;
	}

	public void setSpecList(String specList) {
		this.specList = specList;
	}

	public void setAlternateName(String alternateName) {
		this.alternateName = alternateName;
	}

	public void setPartShortName(String partShortName) {
		this.partShortName = partShortName;
	}

	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}

	public void setPartNote(String partNote) {
		this.partNote = partNote;
	}

	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public void setCustomerPoNumber(String customerPoNumber) {
		this.customerPoNumber = customerPoNumber;
	}

	public void setCustomerPoLine(String customerPoLine) {
		this.customerPoLine = customerPoLine;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	public void setIntercompanyCustomer(String intercompanyCustomer) {
		this.intercompanyCustomer = intercompanyCustomer;
	}

	//getters
	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public String getCompanyId() {
		return companyId;
	}

	public BigDecimal getPrNumber() {
		return prNumber;
	}

	public String getLineItem() {
		return lineItem;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public String getCancelStatus() {
		return cancelStatus;
	}

	public String getSupplyStatus() {
		return supplyStatus;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}

	public String getCatPartNo() {
		return catPartNo;
	}

	public BigDecimal getExpectedUnitCost() {
		return expectedUnitCost;
	}

	public String getCurrencyId() {
		return currencyId;
	}

	public String getCustomerPartNo() {
		return customerPartNo;
	}

	public BigDecimal getRemainingShelfLifePercent() {
		return remainingShelfLifePercent;
	}

	public String getSpecList() {
		return specList;
	}

	public String getAlternateName() {
		return alternateName;
	}

	public String getPartShortName() {
		return partShortName;
	}

	public String getPartDescription() {
		return partDescription;
	}

	public String getPartNote() {
		return partNote;
	}

	public BigDecimal getCustomerId() {
		return customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public String getCustomerPoNumber() {
		return customerPoNumber;
	}

	public String getCustomerPoLine() {
		return customerPoLine;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getLimitDays() {
		return limitDays;
	}

	public void setLimitDays(String limitDays) {
		this.limitDays = limitDays;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}

	public String getOpsRegionName() {
		return opsRegionName;
	}

	public void setOpsRegionName(String opsRegionName) {
		this.opsRegionName = opsRegionName;
	}

	public String getIncoTerms() {
		return incoTerms;
	}

	public void setIncoTerms(String incoTerms) {
		this.incoTerms = incoTerms;
	}

	public String getPaymentTerms() {
		return paymentTerms;
	}

	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	public BigDecimal getQuantityShipped() {
		return quantityShipped;
	}

	public void setQuantityShipped(BigDecimal quantityShipped) {
		this.quantityShipped = quantityShipped;
	}

	public BigDecimal getSalesOrder() {
		return salesOrder;
	}

	public void setSalesOrder(BigDecimal salesOrder) {
		this.salesOrder = salesOrder;
	}

	public String getInventoryGroupName() {
		return inventoryGroupName;
	}

	public void setInventoryGroupName(String inventoryGroupName) {
		this.inventoryGroupName = inventoryGroupName;
	}

	public BigDecimal getMargin() {
		return margin;
	}

	public void setMargin(BigDecimal margin) {
		this.margin = margin;
	}

	public Date getLastShipDate() {
		return lastShipDate;
	}

	public void setLastShipDate(Date lastShipDate) {
		this.lastShipDate = lastShipDate;
	}

	public String getOpsEntityId() {
		return opsEntityId;
	}

	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}

	public BigDecimal getQtyAllocated() {
		return qtyAllocated;
	}

	public void setQtyAllocated(BigDecimal qtyAllocated) {
		this.qtyAllocated = qtyAllocated;
	}

	public BigDecimal getQtyInPurchasing() {
		return qtyInPurchasing;
	}

	public void setQtyInPurchasing(BigDecimal qtyInPurchasing) {
		this.qtyInPurchasing = qtyInPurchasing;
	}

	public BigDecimal getQtyPicked() {
		return qtyPicked;
	}

	public void setQtyPicked(BigDecimal qtyPicked) {
		this.qtyPicked = qtyPicked;
	}
	
	public String getIntercompanyCustomer() {
		return intercompanyCustomer;
	}

}