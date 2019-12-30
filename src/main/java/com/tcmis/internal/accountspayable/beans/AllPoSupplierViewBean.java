package com.tcmis.internal.accountspayable.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: AllPoSupplierViewBean <br>
 * @version: 1.0, Aug 30, 2007 <br>
 *****************************************************************************/


public class AllPoSupplierViewBean extends BaseDataBean {

	private String supplier;
	private String supplierName;
	private BigDecimal radianPo;


	//constructor
	public AllPoSupplierViewBean() {
	}

	//setters
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}


	//getters
	public String getSupplier() {
		return supplier;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public BigDecimal getRadianPo() {
		return radianPo;
	}
}