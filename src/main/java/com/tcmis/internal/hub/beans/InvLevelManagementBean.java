package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: InvLevelManagementBean <br>
 * 
 * @version: 1.0, Oct 2, 2007 <br>
 *****************************************************************************/

public class InvLevelManagementBean extends BaseDataBean {

	private BigDecimal	avgRpValue;
	private BigDecimal	avgSlValue;
	private BigDecimal	avgUnitPrice;
	private String		currencyId;
	private String		customerPart;
	private String		hub;
	private String		hubName;
	private BigDecimal	inpurchasingMinusAlloc;
	private BigDecimal	inTransit;
	private String		inventoryGroup;
	private String		inventoryGroupName;
	private BigDecimal	issued3060;
	private BigDecimal	issued6090;
	private BigDecimal	issuedLast30;
	private String		itemDesc;
	private BigDecimal	itemId;
	private Date		lastReceived;
	private BigDecimal	maxUnitPrice;
	private BigDecimal	minUnitPrice;
	private BigDecimal	nonpickableMinusAlloc;
	private BigDecimal	onHand;
	private BigDecimal	onHandValue;
	private BigDecimal	onorderMinusAlloc;
	private String		operatingEntityName;
	private String		opsCompanyId;
	private String		opsEntityId;
	private BigDecimal	pickableMinusAlloc;
	private String		purchaseCurrencyId;
	private BigDecimal	purchaseUnitCost;
	private BigDecimal	reorderPoint;
	private BigDecimal	reorderQuantity;
	private BigDecimal	stockingLevel;
	private String		stockingMethod;

	// constructor
	public InvLevelManagementBean() {
	}

	public BigDecimal getAvgRpValue() {
		return avgRpValue;
	}

	public BigDecimal getAvgSlValue() {
		return avgSlValue;
	}

	public BigDecimal getAvgUnitPrice() {
		return avgUnitPrice;
	}

	public String getCurrencyId() {
		return currencyId;
	}

	public String getCustomerPart() {
		return customerPart;
	}

	public String getHub() {
		return hub;
	}

	public String getHubName() {
		return hubName;
	}

	public BigDecimal getInpurchasingMinusAlloc() {
		return inpurchasingMinusAlloc;
	}

	public BigDecimal getInTransit() {
		return inTransit;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public String getInventoryGroupName() {
		return inventoryGroupName;
	}

	public BigDecimal getIssued3060() {
		return issued3060;
	}

	public BigDecimal getIssued6090() {
		return issued6090;
	}

	public BigDecimal getIssuedLast30() {
		return issuedLast30;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public Date getLastReceived() {
		return lastReceived;
	}

	public BigDecimal getMaxUnitPrice() {
		return maxUnitPrice;
	}

	public BigDecimal getMinUnitPrice() {
		return minUnitPrice;
	}

	public BigDecimal getNonpickableMinusAlloc() {
		return nonpickableMinusAlloc;
	}

	public BigDecimal getOnHand() {
		return onHand;
	}

	public BigDecimal getOnHandValue() {
		return onHandValue;
	}

	public BigDecimal getOnorderMinusAlloc() {
		return onorderMinusAlloc;
	}

	public String getOperatingEntityName() {
		return operatingEntityName;
	}

	public String getOpsCompanyId() {
		return opsCompanyId;
	}

	public String getOpsEntityId() {
		return opsEntityId;
	}

	public BigDecimal getPickableMinusAlloc() {
		return pickableMinusAlloc;
	}

	public String getPurchaseCurrencyId() {
		return this.purchaseCurrencyId;
	}

	public BigDecimal getPurchaseUnitCost() {
		return this.purchaseUnitCost;
	}

	public BigDecimal getReorderPoint() {
		return reorderPoint;
	}

	public BigDecimal getReorderQuantity() {
		return reorderQuantity;
	}

	public BigDecimal getStockingLevel() {
		return stockingLevel;
	}

	public String getStockingMethod() {
		return stockingMethod;
	}

	public void setAvgRpValue(BigDecimal avgRpValue) {
		this.avgRpValue = avgRpValue;
	}

	public void setAvgSlValue(BigDecimal avgSlValue) {
		this.avgSlValue = avgSlValue;
	}

	public void setAvgUnitPrice(BigDecimal avgUnitPrice) {
		this.avgUnitPrice = avgUnitPrice;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	public void setCustomerPart(String customerPart) {
		this.customerPart = customerPart;
	}

	public void setHub(String hub) {
		this.hub = hub;
	}

	public void setHubName(String hubName) {
		this.hubName = hubName;
	}

	public void setInpurchasingMinusAlloc(BigDecimal inpurchasingMinusAlloc) {
		this.inpurchasingMinusAlloc = inpurchasingMinusAlloc;
	}

	public void setInTransit(BigDecimal inTransit) {
		this.inTransit = inTransit;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public void setInventoryGroupName(String inventoryGroupName) {
		this.inventoryGroupName = inventoryGroupName;
	}

	public void setIssued3060(BigDecimal issued3060) {
		this.issued3060 = issued3060;
	}

	public void setIssued6090(BigDecimal issued6090) {
		this.issued6090 = issued6090;
	}

	public void setIssuedLast30(BigDecimal issuedLast30) {
		this.issuedLast30 = issuedLast30;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public void setLastReceived(Date lastReceived) {
		this.lastReceived = lastReceived;
	}

	public void setMaxUnitPrice(BigDecimal maxUnitPrice) {
		this.maxUnitPrice = maxUnitPrice;
	}

	public void setMinUnitPrice(BigDecimal minUnitPrice) {
		this.minUnitPrice = minUnitPrice;
	}

	public void setNonpickableMinusAlloc(BigDecimal nonpickableMinusAlloc) {
		this.nonpickableMinusAlloc = nonpickableMinusAlloc;
	}

	public void setOnHand(BigDecimal onHand) {
		this.onHand = onHand;
	}

	public void setOnHandValue(BigDecimal onHandValue) {
		this.onHandValue = onHandValue;
	}

	public void setOnorderMinusAlloc(BigDecimal onorderMinusAlloc) {
		this.onorderMinusAlloc = onorderMinusAlloc;
	}

	public void setOperatingEntityName(String operatingEntityName) {
		this.operatingEntityName = operatingEntityName;
	}

	public void setOpsCompanyId(String opsCompanyId) {
		this.opsCompanyId = opsCompanyId;
	}

	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}

	public void setPickableMinusAlloc(BigDecimal pickableMinusAlloc) {
		this.pickableMinusAlloc = pickableMinusAlloc;
	}

	public void setPurchaseCurrencyId(String purchaseCurrencyId) {
		this.purchaseCurrencyId = purchaseCurrencyId;
	}

	public void setPurchaseUnitCost(BigDecimal purchaseUnitCost) {
		this.purchaseUnitCost = purchaseUnitCost;
	}

	public void setReorderPoint(BigDecimal reorderPoint) {
		this.reorderPoint = reorderPoint;
	}

	public void setReorderQuantity(BigDecimal reorderQuantity) {
		this.reorderQuantity = reorderQuantity;
	}

	public void setStockingLevel(BigDecimal stockingLevel) {
		this.stockingLevel = stockingLevel;
	}

	public void setStockingMethod(String stockingMethod) {
		this.stockingMethod = stockingMethod;
	}

}