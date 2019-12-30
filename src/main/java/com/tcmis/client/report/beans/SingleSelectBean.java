package com.tcmis.client.report.beans;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: SingleSelectBean <br>
 * @version: 1.0, Jan 6, 2017 <br>
 *****************************************************************************/


public class SingleSelectBean extends BaseDataBean {

	private String id;
	private String description;
	private String companyId;
	private String facilityId;
	private String typeOfData;


	//constructor
	public SingleSelectBean() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getTypeOfData() {
		return typeOfData;
	}

	public void setTypeOfData(String typeOfData) {
		this.typeOfData = typeOfData;
	}

	public boolean isWorkAreaData() {
		return "workArea".equals(typeOfData);
	}
}