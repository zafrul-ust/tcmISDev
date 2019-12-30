package com.tcmis.ws.scanner.beans;

import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: HubBean <br>
 * 
 * @version: 1.0, Jun 1, 2004 <br>
 *****************************************************************************/

public class CylinderLocation extends BaseDataBean {

	private Date	lastUpdated;
	private String	locationId;
	private String	locationDesc;
	private String	status;
	private String	supplier;
	private String	supplierName;

	// constructor
	public CylinderLocation() {
	}

	public Date getLastUpdated() {
		return this.lastUpdated;
	}

	public String getLocationId() {
		return this.locationId;
	}

	public String getLocationDesc() {
		return this.locationDesc;
	}

	public String getStatus() {
		return this.status;
	}

	public String getSupplier() {
		return this.supplier;
	}

	public String getSupplierName() {
		return this.supplierName;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public void setLocationId(String location) {
		this.locationId = location;
	}

	public void setLocationDesc(String locationDesc) {
		this.locationDesc = locationDesc;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public void setSupplierName(String supplier) {
		this.supplierName = supplier;
	}

}