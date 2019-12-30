package com.tcmis.ws.scanner.beans;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: HubBean <br>
 * 
 * @version: 1.0, Jun 1, 2004 <br>
 *****************************************************************************/

public class CylinderManufacturer extends BaseDataBean {

	private String	manufacturerIdNo;
	private String	manufacturerName;
	private String	supplier;

	// constructor
	public CylinderManufacturer() {
	}

	public String getManufacturerIdNo() {
		return this.manufacturerIdNo;
	}

	public String getManufacturerName() {
		return this.manufacturerName;
	}

	public String getSupplier() {
		return this.supplier;
	}

	public void setManufacturerIdNo(String manufacturerIdNo) {
		this.manufacturerIdNo = manufacturerIdNo;
	}

	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

}