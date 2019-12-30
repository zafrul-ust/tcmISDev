package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

public class FreightRadianPOBean extends BaseDataBean {
	
	private String inventoryGroup;
	private BigDecimal inventoryGroupId;
	private String hub;
	private String facilityId;
	private String shipToCompanyId;
	private String shipToLocationId;
	private String supplier;
	private String freightType;
	private BigDecimal radianPo;
	private String carrier;
	private String currencyId;
	
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public BigDecimal getInventoryGroupId() {
		return inventoryGroupId;
	}
	public void setInventoryGroupId(BigDecimal inventoryGroupId) {
		this.inventoryGroupId = inventoryGroupId;
	}
	public String getHub() {
		return hub;
	}
	public void setHub(String hub) {
		this.hub = hub;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
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
	public String getFreightType() {
		return freightType;
	}
	public void setFreightType(String freightType) {
		this.freightType = freightType;
	}
	public BigDecimal getRadianPo() {
		return radianPo;
	}
	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}
	public String getCarrier() {
		return carrier;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	public String getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

}
