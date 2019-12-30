package com.tcmis.supplier.shipping.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: VirtualHubSupplierBean <br>
 * @version: 1.0, Aug 30, 2007 <br>
 *****************************************************************************/


public class VirtualHubSupplierBean extends BaseDataBean {

	private String supplier;
	private String containerLabel;


	//constructor
	public VirtualHubSupplierBean() {
	}

	//setters
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public void setContainerLabel(String containerLabel) {
		this.containerLabel = containerLabel;
	}


	//getters
	public String getSupplier() {
		return supplier;
	}
	public String getContainerLabel() {
		return containerLabel;
	}
}