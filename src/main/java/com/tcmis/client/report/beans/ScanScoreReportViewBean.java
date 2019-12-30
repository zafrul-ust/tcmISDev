package com.tcmis.client.report.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

public class ScanScoreReportViewBean extends BaseDataBean {
	
	 private String companyId;
	 private String companyName;
	 private String facilityId;
	 private String facilityName;
	 private String divisionId;
	 private String divisionDescription;
	 private String facilityGroupId;
	 private String facilityGroupDescription;
	 private String reportingEntityDescription;
	 private String applicationDesc;
	 private BigDecimal binTotal;
	 private BigDecimal counted14;
	 private BigDecimal counted30;
	 private BigDecimal counted90;
	 private BigDecimal counted180;
	 private BigDecimal score14;
	 private BigDecimal score30;
	 private BigDecimal score90;
	 private BigDecimal score180;
	 
	 private BigDecimal counted7;
	 private BigDecimal score7;
	 
	//constructor
	 public ScanScoreReportViewBean() {
	 }
	 
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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
	public BigDecimal getBinTotal() {
		return binTotal;
	}
	public void setBinTotal(BigDecimal binTotal) {
		this.binTotal = binTotal;
	}
	public BigDecimal getCounted14() {
		return counted14;
	}
	public void setCounted14(BigDecimal counted14) {
		this.counted14 = counted14;
	}
	public BigDecimal getCounted30() {
		return counted30;
	}
	public void setCounted30(BigDecimal counted30) {
		this.counted30 = counted30;
	}
	public BigDecimal getCounted90() {
		return counted90;
	}
	public void setCounted90(BigDecimal counted90) {
		this.counted90 = counted90;
	}
	public BigDecimal getCounted180() {
		return counted180;
	}
	public void setCounted180(BigDecimal counted180) {
		this.counted180 = counted180;
	}
	public BigDecimal getScore14() {
		return score14;
	}
	public void setScore14(BigDecimal score14) {
		this.score14 = score14;
	}
	public BigDecimal getScore30() {
		return score30;
	}
	public void setScore30(BigDecimal score30) {
		this.score30 = score30;
	}
	public BigDecimal getScore90() {
		return score90;
	}
	public void setScore90(BigDecimal score90) {
		this.score90 = score90;
	}
	public BigDecimal getScore180() {
		return score180;
	}
	public void setScore180(BigDecimal score180) {
		this.score180 = score180;
	}
	
	public void setApplicationDesc(String applicationDesc) {
		this.applicationDesc = applicationDesc;
	}
	
	public void setReportingEntityDescription(String reportingEntityDescription) {
		this.reportingEntityDescription = reportingEntityDescription;
	}
	
	public String getReportingEntityDescription() {
		return reportingEntityDescription;
	}
	
	public String getApplicationDesc() {
		return applicationDesc;
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

	public BigDecimal getCounted7() {
		return counted7;
	}

	public void setCounted7(BigDecimal counted7) {
		this.counted7 = counted7;
	}

	public BigDecimal getScore7() {
		return score7;
	}

	public void setScore7(BigDecimal score7) {
		this.score7 = score7;
	}
	
	 
	 
	 
	 
}
