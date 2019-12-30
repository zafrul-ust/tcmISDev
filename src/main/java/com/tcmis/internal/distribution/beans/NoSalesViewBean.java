package com.tcmis.internal.distribution.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: NoSalesViewBean <br>
 * @version: 1.0, Jun 17, 2009 <br>
 *****************************************************************************/


public class NoSalesViewBean extends BaseDataBean {

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
	private String operatingEntityName;
	private String hubName;
	private String specs;
	private String currencyId;
	private BigDecimal receiptId;
	private Date expireDate;
	private BigDecimal notIssuedIn;
	private BigDecimal reorderPoint;
	private BigDecimal stockingLevel;
	private BigDecimal reorderQuantity;
	private BigDecimal quantityReceived;
	private Date dateOfReceipt;
	private Date lastSaleDate;
	
	//constructor
	public NoSalesViewBean() {
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
	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	public void setNotIssuedIn(BigDecimal notIssuedIn) {
		this.notIssuedIn = notIssuedIn;
	}
	public void setReorderPoint(BigDecimal reorderPoint) {
		this.reorderPoint = reorderPoint;
	}
	public void setStockingLevel(BigDecimal stockingLevel) {
		this.stockingLevel = stockingLevel;
	}
	public void setReorderQuantity(BigDecimal reorderQuantity) {
		this.reorderQuantity = reorderQuantity;
	}
	public void setQuantityReceived(BigDecimal quantityReceived) {
		this.quantityReceived = quantityReceived;
	}
	public void setDateOfReceipt(Date dateOfReceipt) {
		this.dateOfReceipt = dateOfReceipt;
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
	public BigDecimal getReceiptId() {
		return receiptId;
	}
	public Date getExpireDate() {
		return expireDate;
	}
	public BigDecimal getNotIssuedIn() {
		return notIssuedIn;
	}
	public BigDecimal getReorderPoint() {
		return reorderPoint;
	}
	public BigDecimal getStockingLevel() {
		return stockingLevel;
	}
	public BigDecimal getReorderQuantity() {
		return reorderQuantity;
	}
	public BigDecimal getQuantityReceived() {
		return quantityReceived;
	}
	public Date getDateOfReceipt() {
		return dateOfReceipt;
	}

	public String getOperatingEntityName() {
		return operatingEntityName;
	}

	public void setOperatingEntityName(String operatingEntityName) {
		this.operatingEntityName = operatingEntityName;
	}

	public Date getLastSaleDate() {
		return lastSaleDate;
	}

	public void setLastSaleDate(Date lastSaleDate) {
		this.lastSaleDate = lastSaleDate;
	}

}