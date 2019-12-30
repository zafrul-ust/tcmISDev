package com.tcmis.client.common.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: ClientConsignedInventoryReportInputBean <br>
 * @version: 1.0, Dec 200, 2010 <br>
 *****************************************************************************/

public class ClientConsignedInventoryReportInputBean extends BaseDataBean {

	 private String facilityId;
	 private String inventoryGroup;
	 private BigDecimal expiresWithin;
	 private BigDecimal expiresAfter;
	 private String showCustomerReturn;

	 //	constructor
	 public ClientConsignedInventoryReportInputBean() {
	 }

	 //setters
	 public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	 }

	 public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	 }

	 public void setExpiresWithin(BigDecimal expiresWithin) {
		this.expiresWithin = expiresWithin;
	 }

	 public void setExpiresAfter(BigDecimal expiresAfter) {
		this.expiresAfter = expiresAfter;
	 }

	 //getters
	 public String getFacilityId() {
		return this.facilityId;
	 }

	 public String getInventoryGroup() {
		return this.inventoryGroup;
	 }

	 public BigDecimal getExpiresWithin() {
		return this.expiresWithin;
	 }

	 public BigDecimal getExpiresAfter() {
		return this.expiresAfter;
	 }

	public String getShowCustomerReturn() {
		return showCustomerReturn;
	}

	public void setShowCustomerReturn(String showCustomerReturn) {
		this.showCustomerReturn = showCustomerReturn;
	}

}