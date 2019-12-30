package com.tcmis.client.report.beans;

import com.tcmis.common.framework.BaseDataBean;

public class ScanScoreInputBean extends BaseDataBean {

	 private String companyId;
	 private String facilityId;
	 private String facilityName;
	 private String divisionId;
	 private String divisionDescription;
	 private String facilityGroupId;
	 private String facilityGroupDescription;
	 private String uAction;
	 private String onlyFacWithBin;
	 private String reportingEntityId;
	 private String application;
	 private String onlyWithBin;
		 
	 
	//constructor
	 public ScanScoreInputBean() {
	 }
	 
		public String getReportingEntityId() {
			return reportingEntityId;
		}


		public void setReportingEntityId(String reportingEntityId) {
			this.reportingEntityId = reportingEntityId;
		}
		
		public String getApplication() {
			return application;
		}


		public void setApplication(String application) {
			this.application = application;
		}
		
		public String getOnlyWithBin() {
			return onlyWithBin;
		}


		public void setOnlyWithBin(String onlyWithBin) {
			this.onlyWithBin = onlyWithBin;
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


	
	public String getFacilityGroupId() {
		return facilityGroupId;
	}


	public void setFacilityGroupId(String facilityGroupId) {
		this.facilityGroupId = facilityGroupId;
	}


	
	public String getuAction() {
		return uAction;
	}


	public void setuAction(String uAction) {
		this.uAction = uAction;
	}


	public String getOnlyFacWithBin() {
		if("Y".equalsIgnoreCase(onlyFacWithBin))
			return onlyFacWithBin;
		else
			return "N";
	}


	public void setOnlyFacWithBin(String onlyFacWithBin) {
		this.onlyFacWithBin = onlyFacWithBin;
	}


	public String getDivisionId() {
		return divisionId;
	}


	public void setDivisionId(String divisionId) {
		this.divisionId = divisionId;
	}


	public String getDivisionDescription() {
		return divisionDescription;
	}


	public void setDivisionDescription(String divisionDescription) {
		this.divisionDescription = divisionDescription;
	}


	public String getFacilityGroupDescription() {
		return facilityGroupDescription;
	}


	public void setFacilityGroupDescription(String facilityGroupDescription) {
		this.facilityGroupDescription = facilityGroupDescription;
	}
	
		 
	 
}
