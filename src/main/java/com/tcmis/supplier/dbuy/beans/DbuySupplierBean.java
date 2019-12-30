package com.tcmis.supplier.dbuy.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: DbuySupplierBean <br>
 * @version: 1.0, May 3, 2006 <br>
 *****************************************************************************/


public class DbuySupplierBean extends BaseDataBean {

	private String supplierName;
	private String supplier;


	//constructor
	public DbuySupplierBean() {
	}

	//setters
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}


	//getters
	public String getSupplierName() {
		return supplierName;
	}
	public String getSupplier() {
		return supplier;
	}
}