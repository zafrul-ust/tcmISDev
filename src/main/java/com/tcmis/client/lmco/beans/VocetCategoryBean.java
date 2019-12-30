package com.tcmis.client.lmco.beans;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: VocetCoatCategoryBean <br>
 * @version: 1.0, July 17, 2012 <br>
 *****************************************************************************/

public class VocetCategoryBean extends BaseDataBean {

	private String companyId;
	private String facilityId;
	private String vocetCategoryId;
	private String vocetCategoryDesc;

	//constructor
	public VocetCategoryBean() {
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

	public String getVocetCategoryDesc() {
		return vocetCategoryDesc;
	}

	public void setVocetCategoryDesc(String vocetCategoryDesc) {
		this.vocetCategoryDesc = vocetCategoryDesc;
	}

	public String getVocetCategoryId() {
		return vocetCategoryId;
	}

	public void setVocetCategoryId(String vocetCategoryId) {
		this.vocetCategoryId = vocetCategoryId;
	}


}