package com.tcmis.ws.scanner.beans;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: HubBean <br>
 * 
 * @version: 1.0, Jun 1, 2004 <br>
 *****************************************************************************/

public class Hub extends BaseDataBean {

	private String	companyId;
	private String	hub;
	private String	hubName;
	private String	homeCompanyId;

	// constructor
	public Hub() {
	}

	// setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setHub(String hub) {
		this.hub = hub;
	}

	public void setHubName(String hubName) {
		this.hubName = hubName;
	}

	public void setHomeCompanyId(String facilityId) {
		this.homeCompanyId = facilityId;
	}

	// getters
	public String getCompanyId() {
		return companyId;
	}

	public String getHub() {
		return hub;
	}

	public String getHubName() {
		return hubName;
	}

	public String getHomeCompanyId() {
		return homeCompanyId;
	}

}