package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: LessThanNWksInvViewBean <br>
 * @version: 1.0, May 20, 2009 <br>
 *****************************************************************************/


public class LessThanNWksInvViewBean extends BaseDataBean {
	
	private static final long serialVersionUID = 4355152959693485212L;
	private String partNo;
	private BigDecimal averageCost;
	private String inventoryGroup;
	private BigDecimal itemId;
	private String hub;
	private String partShortName;
	private BigDecimal onHand;
	private String inventoryGroupName;
	private String opsCompanyId;
	private String opsEntityId;
	private String hubName;
	private String specs;
	private String currencyId;
	private BigDecimal averageWeeklyUsage;
	private BigDecimal weeksLeft;
	private String operatingEntityName;
	private BigDecimal daysToStockoutQtr1;
	private BigDecimal daysToStockoutQtr2;
	private BigDecimal daysToStockoutQtr3;
	private BigDecimal daysToStockoutQtr4;

	//constructor
	public LessThanNWksInvViewBean() {
	}

	//setters
	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}
	public void setAverageCost(BigDecimal averageCost) {
		this.averageCost = averageCost;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setHub(String hub) {
		this.hub = hub;
	}
	public void setPartShortName(String partShortName) {
		this.partShortName = partShortName;
	}
	public void setOnHand(BigDecimal onHand) {
		this.onHand = onHand;
	}
	public void setInventoryGroupName(String inventoryGroupName) {
		this.inventoryGroupName = inventoryGroupName;
	}
	public void setOpsCompanyId(String opsCompanyId) {
		this.opsCompanyId = opsCompanyId;
	}
	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}
	public void setHubName(String hubName) {
		this.hubName = hubName;
	}
	public void setSpecs(String specs) {
		this.specs = specs;
	}
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}
	public void setAverageWeeklyUsage(BigDecimal averageWeeklyUsage) {
		this.averageWeeklyUsage = averageWeeklyUsage;
	}
	public void setWeeksLeft(BigDecimal weeksLeft) {
		this.weeksLeft = weeksLeft;
	}


	//getters
	public String getPartNo() {
		return partNo;
	}
	public BigDecimal getAverageCost() {
		return averageCost;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public String getHub() {
		return hub;
	}
	public String getPartShortName() {
		return partShortName;
	}
	public BigDecimal getOnHand() {
		return onHand;
	}
	public String getInventoryGroupName() {
		return inventoryGroupName;
	}
	public String getOpsCompanyId() {
		return opsCompanyId;
	}
	public String getOpsEntityId() {
		return opsEntityId;
	}
	public String getHubName() {
		return hubName;
	}
	public String getSpecs() {
		return specs;
	}
	public String getCurrencyId() {
		return currencyId;
	}
	public BigDecimal getAverageWeeklyUsage() {
		return averageWeeklyUsage;
	}
	public BigDecimal getWeeksLeft() {
		return weeksLeft;
	}

	public void setOperatingEntityName(String operatingEntityName) {
		this.operatingEntityName = operatingEntityName;
	}

	public String getOperatingEntityName() {
		return operatingEntityName;
	}

	public BigDecimal getDaysToStockoutQtr1() {
		return daysToStockoutQtr1;
	}

	public void setDaysToStockoutQtr1(BigDecimal daysToStockoutQtr1) {
		this.daysToStockoutQtr1 = daysToStockoutQtr1;
	}

	public BigDecimal getDaysToStockoutQtr2() {
		return daysToStockoutQtr2;
	}

	public void setDaysToStockoutQtr2(BigDecimal daysToStockoutQtr2) {
		this.daysToStockoutQtr2 = daysToStockoutQtr2;
	}

	public BigDecimal getDaysToStockoutQtr3() {
		return daysToStockoutQtr3;
	}

	public void setDaysToStockoutQtr3(BigDecimal daysToStockoutQtr3) {
		this.daysToStockoutQtr3 = daysToStockoutQtr3;
	}

	public BigDecimal getDaysToStockoutQtr4() {
		return daysToStockoutQtr4;
	}

	public void setDaysToStockoutQtr4(BigDecimal daysToStockoutQtr4) {
		this.daysToStockoutQtr4 = daysToStockoutQtr4;
	}
}