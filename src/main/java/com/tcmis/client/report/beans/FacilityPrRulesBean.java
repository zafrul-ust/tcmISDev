package com.tcmis.client.report.beans;

import java.util.Collection;
import java.util.Vector;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: FacilityPrRulesBean <br>
 * @version: 1.0, Aug 15, 2019 <br>
 *****************************************************************************/


public class FacilityPrRulesBean extends BaseDataBean {

	private String facilityId;
	private Collection accountSysList;


	//constructor
	public FacilityPrRulesBean() {
	}

	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public Collection getAccountSysList() {
		return accountSysList;
	}

	public void setAccountSysList(Collection accountSysList) {
		this.accountSysList = accountSysList;
	}
}