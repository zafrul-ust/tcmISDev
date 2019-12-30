package com.tcmis.client.common.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: DeptBean <br>
 * @version: 1.0, Nov 29, 2011 <br>
 *****************************************************************************/

public class VocZoneBean extends BaseDataBean {

	private String companyId;
	private String facilityId;
	private BigDecimal vocZoneId;
	private String vocZoneDescription;
	private String status;

	//constructor
	public VocZoneBean() {
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

	public String getVocZoneDescription() {
		return vocZoneDescription;
	}

	public void setVocZoneDescription(String vocZoneDescription) {
		this.vocZoneDescription = vocZoneDescription;
	}

	public BigDecimal getVocZoneId() {
		return vocZoneId;
	}

	public void setVocZoneId(BigDecimal vocZoneId) {
		this.vocZoneId = vocZoneId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isNewVocZone() {
		return (vocZoneId == null);
	}

	public boolean hasVocZoneDescription() {
		return !StringHandler.isBlankString(vocZoneDescription);
	}

}