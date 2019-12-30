package com.tcmis.supplier.shipping.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME:  supplierCarrierBean <br>
 * @version: 1.0, Nov 13, 2007 <br>
 *****************************************************************************/


public class  SupplierCarrierBean extends BaseDataBean {

	private String supplier;
	private String carrierName;


	//constructor
	public  SupplierCarrierBean() {
	}

	//setters
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}


	//getters
	public String getSupplier() {
		return supplier;
	}
	public String getCarrierName() {
		return carrierName.trim();
	}
}