package com.tcmis.internal.distribution.beans;

import java.util.*;
import java.math.*;
import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: CustomerContactSearchViewBean <br>
 * @version: 1.0, Mar 17, 2009 <br>
 *****************************************************************************/


public class BatchUpdateBuyerInputBean extends BaseDataBean {

	private BigDecimal itemId;
	private String supplier;
	private BigDecimal buyer;
	private String shipToLocationId;
	private String shipToCompanyId;
	private String opsEntityId;
	private String inventoryGroup;
	private String hub;
	private String carrier;
	private String criticalOrderCarrier;
	private BigDecimal supplierContactId;
	private BigDecimal remainingShelfLifePercent;
	private String pricingType;
	private String supplyPath;
	private Date startDate;
	private String currencyId;
	private BigDecimal unitPrice;
	private String uAction;
	private String allInventoryGroups;
	
	private String inventoryGroupList;

	//constructor
	public BatchUpdateBuyerInputBean() {
	}


	public BigDecimal getBuyer() {
		return buyer;
	}


	public void setBuyer(BigDecimal buyer) {
		this.buyer = buyer;
	}


	public String getInventoryGroup() {
		return inventoryGroup;
	}


	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}


	public BigDecimal getItemId() {
		return itemId;
	}


	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}


	public String getOpsEntityId() {
		return opsEntityId;
	}


	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}


	public String getShipToCompanyId() {
		return shipToCompanyId;
	}


	public void setShipToCompanyId(String shipToCompanyId) {
		this.shipToCompanyId = shipToCompanyId;
	}


	public String getShipToLocationId() {
		return shipToLocationId;
	}


	public void setShipToLocationId(String shipToLocationId) {
		this.shipToLocationId = shipToLocationId;
	}


	public String getSupplier() {
		return supplier;
	}


	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}


	public String getUAction() {
		return uAction;
	}


	public void setUAction(String action) {
		uAction = action;
	}

    public String getHub() {
        return hub;
    }

    public void setHub(String hub) {
        this.hub = hub;
    }


	public String getCarrier() {
		return carrier;
	}


	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}


	public String getCriticalOrderCarrier() {
		return criticalOrderCarrier;
	}


	public void setCriticalOrderCarrier(String criticalOrderCarrier) {
		this.criticalOrderCarrier = criticalOrderCarrier;
	}


	public String getPricingType() {
		return pricingType;
	}


	public void setPricingType(String pricingType) {
		this.pricingType = pricingType;
	}


	public BigDecimal getRemainingShelfLifePercent() {
		return remainingShelfLifePercent;
	}


	public void setRemainingShelfLifePercent(BigDecimal remainingShelfLifePercent) {
		this.remainingShelfLifePercent = remainingShelfLifePercent;
	}

	public Date getStartDate() {
		return startDate;
	}


	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	public BigDecimal getSupplierContactId() {
		return supplierContactId;
	}


	public void setSupplierContactId(BigDecimal supplierContactId) {
		this.supplierContactId = supplierContactId;
	}


	public String getSupplyPath() {
		return supplyPath;
	}


	public void setSupplyPath(String supplyPath) {
		this.supplyPath = supplyPath;
	}


	public BigDecimal getUnitPrice() {
		return unitPrice;
	}


	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}


	public String getCurrencyId() {
		return currencyId;
	}


	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}


	public String getAllInventoryGroups() {
		return allInventoryGroups;
	}


	public void setAllInventoryGroups(String allInventoryGroups) {
		this.allInventoryGroups = allInventoryGroups;
	}


	public String getInventoryGroupList() {
		return inventoryGroupList;
	}


	public void setInventoryGroupList(String inventoryGroupList) {
		this.inventoryGroupList = inventoryGroupList;
	}
}