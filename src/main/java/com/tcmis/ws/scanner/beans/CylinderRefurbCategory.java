package com.tcmis.ws.scanner.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: HubBean <br>
 * 
 * @version: 1.0, Jun 1, 2004 <br>
 *****************************************************************************/

public class CylinderRefurbCategory extends BaseDataBean {

	private BigDecimal	displayOrder;
	private String		refurbCategory;
	private String		refurbSubcategory;
	private String		status;
	private BigDecimal	subDisplayOrder;
	private String		subStatus;
	private String		supplier;

	// constructor
	public CylinderRefurbCategory() {
	}

	public BigDecimal getDisplayOrder() {
		return this.displayOrder;
	}

	public String getRefurbCategory() {
		return this.refurbCategory;
	}

	public String getRefurbSubcategory() {
		return this.refurbSubcategory;
	}

	public String getStatus() {
		return this.status;
	}

	public BigDecimal getSubDisplayOrder() {
		return this.subDisplayOrder;
	}

	public String getSubStatus() {
		return this.subStatus;
	}

	public String getSupplier() {
		return this.supplier;
	}

	public void setDisplayOrder(BigDecimal displayOrder) {
		this.displayOrder = displayOrder;
	}

	public void setRefurbCategory(String refurbCategory) {
		this.refurbCategory = refurbCategory;
	}

	public void setRefurbSubcategory(String refurbSubcategory) {
		this.refurbSubcategory = refurbSubcategory;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setSubDisplayOrder(BigDecimal subDisplayOrder) {
		this.subDisplayOrder = subDisplayOrder;
	}

	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

}