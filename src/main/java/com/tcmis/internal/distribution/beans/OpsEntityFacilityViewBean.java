package com.tcmis.internal.distribution.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: ShipToEntityPriceGrpViewBean <br>
 * @version: 1.0, Jul 9, 2010 <br>
 *****************************************************************************/


public class OpsEntityFacilityViewBean extends BaseDataBean {

	private String facilityId;
	private String facilityName;
	private String companyId;
	private String opsEntityId;
	private String operatingEntityName;
	private String priceGroupId;
	private String priceGroupName;
	private String inventoryGroupDefault;
	private String inventoryGroupName;
	private String sapCostCenter;
	private String costCenterName;
	private BigDecimal customerId;

//from page
	private String changed;


	public String getChanged() {
		return changed;
	}

	public void setChanged(String changed) {
		this.changed = changed;
	}

	//constructor
	public OpsEntityFacilityViewBean() {
	}

	//setters
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}
	public void setOperatingEntityName(String operatingEntityName) {
		this.operatingEntityName = operatingEntityName;
	}
	public void setPriceGroupId(String priceGroupId) {
		this.priceGroupId = priceGroupId;
	}
	public void setPriceGroupName(String priceGroupName) {
		this.priceGroupName = priceGroupName;
	}
	public void setInventoryGroupDefault(String inventoryGroupDefault) {
		this.inventoryGroupDefault = inventoryGroupDefault;
	}
	public void setInventoryGroupName(String inventoryGroupName) {
		this.inventoryGroupName = inventoryGroupName;
	}


	//getters
	public String getFacilityId() {
		return facilityId;
	}
	public String getFacilityName() {
		return facilityName;
	}
	public String getCompanyId() {
		return companyId;
	}
	public String getOpsEntityId() {
		return opsEntityId;
	}
	public String getOperatingEntityName() {
		return operatingEntityName;
	}
	public String getPriceGroupId() {
		return priceGroupId;
	}
	public String getPriceGroupName() {
		return priceGroupName;
	}
	public String getInventoryGroupDefault() {
		return inventoryGroupDefault;
	}
	public String getInventoryGroupName() {
		return inventoryGroupName;
	}
	public String getSapCostCenter() {
		return sapCostCenter;
	}
	public void setSapCostCenter(String sapCostCenter) {
		this.sapCostCenter = sapCostCenter;
	}
	public String getCostCenterName() {
		return costCenterName;
	}
	public void setCostCenterName(String costCenterName) {
		this.costCenterName = costCenterName;
	}

	public BigDecimal getCustomerId() {
		return customerId;
	}

	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

}