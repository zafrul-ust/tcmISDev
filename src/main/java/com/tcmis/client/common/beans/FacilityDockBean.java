package com.tcmis.client.common.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: FacilityDockBean <br>
 * @version: 1.0, Nov 10, 2010 <br>
 *****************************************************************************/


public class FacilityDockBean extends BaseDataBean {

	private String companyId;
	private String dockDesc;
	private String dockLocationId;
	private String facilityId;
	private String status;


	//constructor
	public FacilityDockBean() {
	}

	public String getCompanyId() {
		return companyId;
	}
	public String getDockDesc() {
		return dockDesc;
	}
	public String getDockLocationId() {
		return dockLocationId;
	}
	//getters
	public String getFacilityId() {
		return facilityId;
	}
	public String getStatus() {
		return status;
	}


	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setDockDesc(String dockDesc) {
		this.dockDesc = dockDesc;
	}
	public void setDockLocationId(String dockLocationId) {
		this.dockLocationId = dockLocationId;
	}
	//setters
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}