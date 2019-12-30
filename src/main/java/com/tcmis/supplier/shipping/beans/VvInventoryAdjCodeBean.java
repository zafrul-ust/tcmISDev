package com.tcmis.supplier.shipping.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

public class VvInventoryAdjCodeBean extends BaseDataBean {
	private String active;
	private String code;
	private BigDecimal sortOrder;
	private String supplier;

	public String getActive() {
		return active;
	}
	
	public void setActive(String active) {
		this.active = active;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public BigDecimal getSortOrder() {
		return sortOrder;
	}
	
	public void setSortOrder(BigDecimal sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
}