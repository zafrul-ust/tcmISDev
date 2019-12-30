package com.tcmis.client.lmco.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: VocetSourceBean <br>
 * @version: 1.0, July 11, 2012 <br>
 *****************************************************************************/

public class VocetSourceBean extends BaseDataBean {

	private String companyId;
	private String facilityId;
	private String vocetSourceId;
	private String vocetSourceDesc;


	//constructor
	public VocetSourceBean() {
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


	public String getVocetSourceDesc() {
		return vocetSourceDesc;
	}


	public void setVocetSourceDesc(String vocetSourceDesc) {
		this.vocetSourceDesc = vocetSourceDesc;
	}


	public String getVocetSourceId() {
		return vocetSourceId;
	}


	public void setVocetSourceId(String vocetSourceId) {
		this.vocetSourceId = vocetSourceId;
	}



}