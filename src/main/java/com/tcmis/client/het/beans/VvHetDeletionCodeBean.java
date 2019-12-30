package com.tcmis.client.het.beans;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: VvHetDeletionCodeBean <br>
 * @version: 1.0, Aug 30, 2011 <br>
 *****************************************************************************/

public class VvHetDeletionCodeBean extends BaseDataBean {

	private String companyId;
	private String deletionCode;
	private String deletionDesc;

	//constructor
	public VvHetDeletionCodeBean() {
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setDeletionCode(String deletionCode) {
		this.deletionCode = deletionCode;
	}

	public void setDeletionDesc(String deletionDesc) {
		this.deletionDesc = deletionDesc;
	}

	//getters
	public String getCompanyId() {
		return companyId;
	}

	public String getDeletionCode() {
		return deletionCode;
	}

	public String getDeletionDesc() {
		return deletionDesc;
	}
}