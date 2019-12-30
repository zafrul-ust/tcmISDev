package com.tcmis.ws.scanner.beans;

import com.tcmis.common.framework.BaseDataBean;

public class SupplierLocation extends BaseDataBean {
	private String	locationDesc;
	private String	locationId;
	private String	status;
	private String	supplier;

	public String getLocationId() {
		return this.locationId;
	}

	public String getStatus() {
		return this.status;
	}

	public String getSupplier() {
		return this.supplier;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getLocationDesc() {
		return this.locationDesc;
	}

	public void setLocationDesc(String locationDesc) {
		this.locationDesc = locationDesc;
	}
}
