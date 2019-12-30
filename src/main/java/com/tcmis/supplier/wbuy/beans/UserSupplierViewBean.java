package com.tcmis.supplier.wbuy.beans;

import com.tcmis.common.framework.BaseDataBean;

import java.math.BigDecimal;


/******************************************************************************
 * CLASSNAME: UserSupplierViewBean <br>
 * @version: 1.0, May 17, 2009 <br>
 *****************************************************************************/


public class UserSupplierViewBean extends BaseDataBean {

	private String companyId;
	private BigDecimal personnelId;
	private String supplier;
	private String supplierName;


	//constructor
	public UserSupplierViewBean() {
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}


	//getters
	public String getCompanyId() {
		return companyId;
	}
	public BigDecimal getPersonnelId() {
		return personnelId;
	}
	public String getSupplier() {
		return supplier;
	}
	public String getSupplierName() {
		return supplierName;
	}
}