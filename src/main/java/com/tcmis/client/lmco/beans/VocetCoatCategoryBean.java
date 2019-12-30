package com.tcmis.client.lmco.beans;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: VocetCoatCategoryBean <br>
 * @version: 1.0, July 9, 2012 <br>
 *****************************************************************************/

public class VocetCoatCategoryBean extends BaseDataBean {

	private String companyId;
	private String facilityId;
	private String vocetCoatCategoryId;
	private String vocetCoatCategoryDesc;

	//constructor
	public VocetCoatCategoryBean() {
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

	public String getVocetCoatCategoryDesc() {
		return vocetCoatCategoryDesc;
	}

	public void setVocetCoatCategoryDesc(String vocetCoatCategoryDesc) {
		this.vocetCoatCategoryDesc = vocetCoatCategoryDesc;
	}

	public String getVocetCoatCategoryId() {
		return vocetCoatCategoryId;
	}

	public void setVocetCoatCategoryId(String vocetCoatCategoryId) {
		this.vocetCoatCategoryId = vocetCoatCategoryId;
	}

}