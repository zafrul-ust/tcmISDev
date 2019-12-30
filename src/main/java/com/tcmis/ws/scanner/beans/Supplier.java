package com.tcmis.ws.scanner.beans;

import com.tcmis.common.framework.BaseDataBean;

public class Supplier extends BaseDataBean {
	private String	supplier;
	private String	supplierName;

	public String getSupplier() {
		return this.supplier;
	}

	public String getSupplierName() {
		return this.supplierName;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
}
