package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: ExcessInventoryViewBean <br>
 * @version: 1.0, May 21, 2009 <br>
 *****************************************************************************/


public class ExcessInventoryViewBean extends BaseDataBean {

	private String partNo;
	private String currencyId;
	private String hub;
	private String hubName;
	private String inventoryGroup;
	private String inventoryGroupName;
	private String partShortName;
	private BigDecimal itemId;
	private String opsCompanyId;
	private String opsEntityId;
	private BigDecimal onHand;
	private BigDecimal inPurchasing;
	private BigDecimal onOrder;
	private String specs;
	private BigDecimal averageCost;
	private Date expireDate;
	private Date deliveryDate;
	private String operatingEntityName;	
	private BigDecimal stockingLevel;

	//constructor
	public ExcessInventoryViewBean() {
	}

	//setters
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}
	public void setHub(String hub) {
		this.hub = hub;
	}
	public void setHubName(String hubName) {
		this.hubName = hubName;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setInventoryGroupName(String inventoryGroupName) {
		this.inventoryGroupName = inventoryGroupName;
	}
	
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setOpsCompanyId(String opsCompanyId) {
		this.opsCompanyId = opsCompanyId;
	}
	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}
	public void setOnHand(BigDecimal onHand) {
		this.onHand = onHand;
	}
	public void setInPurchasing(BigDecimal inPurchasing) {
		this.inPurchasing = inPurchasing;
	}
	public void setOnOrder(BigDecimal onOrder) {
		this.onOrder = onOrder;
	}
	public void setSpecs(String specs) {
		this.specs = specs;
	}
	
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}


	//getters
	public String getCurrencyId() {
		return currencyId;
	}
	public String getHub() {
		return hub;
	}
	public String getHubName() {
		return hubName;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public String getInventoryGroupName() {
		return inventoryGroupName;
	}

	public BigDecimal getItemId() {
		return itemId;
	}
	public String getOpsCompanyId() {
		return opsCompanyId;
	}
	public String getOpsEntityId() {
		return opsEntityId;
	}
	public BigDecimal getOnHand() {
		return onHand;
	}
	public BigDecimal getInPurchasing() {
		return inPurchasing;
	}
	public BigDecimal getOnOrder() {
		return onOrder;
	}
	public String getSpecs() {
		return specs;
	}
	
	public Date getExpireDate() {
		return expireDate;
	}
	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setOperatingEntityName(String operatingEntityName) {
		this.operatingEntityName = operatingEntityName;
	}

	public String getOperatingEntityName() {
		return operatingEntityName;
	}

	public void setStockingLevel(BigDecimal stockingLevel) {
		this.stockingLevel = stockingLevel;
	}

	public BigDecimal getStockingLevel() {
		return stockingLevel;
	}

	public BigDecimal getAverageCost() {
		return averageCost;
	}

	public void setAverageCost(BigDecimal averageCost) {
		this.averageCost = averageCost;
	}

	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public String getPartShortName() {
		return partShortName;
	}

	public void setPartShortName(String partShortName) {
		this.partShortName = partShortName;
	}
}

