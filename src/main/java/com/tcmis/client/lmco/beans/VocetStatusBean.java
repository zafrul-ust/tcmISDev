package com.tcmis.client.lmco.beans;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: VocetStatusBean <br>
 * @version: 1.0, July 9, 2012 <br>
 *****************************************************************************/

public class VocetStatusBean extends BaseDataBean {

	private String companyId;
	private String facilityId;
	private String vocetStatusId;
	private String vocetStatusDesc;

	//constructor
	public VocetStatusBean() {
	}


	public String getCompanyId() {
		return companyId;
	}



	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}



	public String getFacilityId() {
		return facilityId;
	}



	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}



	public String getVocetStatusDesc() {
		return vocetStatusDesc;
	}



	public void setVocetStatusDesc(String vocetStatusDesc) {
		this.vocetStatusDesc = vocetStatusDesc;
	}



	public String getVocetStatusId() {
		return vocetStatusId;
	}



	public void setVocetStatusId(String vocetStatusId) {
		this.vocetStatusId = vocetStatusId;
	}



}