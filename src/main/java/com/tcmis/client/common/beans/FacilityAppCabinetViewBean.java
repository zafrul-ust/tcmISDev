package com.tcmis.client.common.beans;

import java.util.Date;
import java.util.Collection;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: FacilityAppCabinetViewBean <br>
 * @version: 1.0, Oct 4, 2010 <br>
 *****************************************************************************/


public class FacilityAppCabinetViewBean extends BaseDataBean {

	private String companyId;
	private String facilityId;
	private String facilityName;
	private String useApplication;
	private String applicationDesc;
	private BigDecimal cabinetId;
	private String cabinetName;
	private String displayCabinetName;
	private String status;
	private BigDecimal personnelId;
	private Collection applicationColl;
	private Collection cabinetColl;
	private Collection facilityCabinetColl;


	//constructor
	public FacilityAppCabinetViewBean() {
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

	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public String getUseApplication() {
		return useApplication;
	}

	public void setUseApplication(String useApplication) {
		this.useApplication = useApplication;
	}

	public String getApplicationDesc() {
		return applicationDesc;
	}

	public void setApplicationDesc(String applicationDesc) {
		this.applicationDesc = applicationDesc;
	}

	public BigDecimal getCabinetId() {
		return cabinetId;
	}

	public void setCabinetId(BigDecimal cabinetId) {
		this.cabinetId = cabinetId;
	}

	public String getCabinetName() {
		return cabinetName;
	}

	public void setCabinetName(String cabinetName) {
		this.cabinetName = cabinetName;
	}

	public String getDisplayCabinetName() {
		return displayCabinetName;
	}

	public void setDisplayCabinetName(String displayCabinetName) {
		this.displayCabinetName = displayCabinetName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getPersonnelId() {
		return personnelId;
	}

	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
	}

	public Collection getApplicationColl() {
		return applicationColl;
	}

	public void setApplicationColl(Collection applicationColl) {
		this.applicationColl = applicationColl;
	}

	public Collection getCabinetColl() {
		return cabinetColl;
	}

	public void setCabinetColl(Collection cabinetColl) {
		this.cabinetColl = cabinetColl;
	}

	public Collection getFacilityCabinetColl() {
		return facilityCabinetColl;
	}

	public void setFacilityCabinetColl(Collection facilityCabinetColl) {
		this.facilityCabinetColl = facilityCabinetColl;
	}
} //end of class