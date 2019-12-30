package com.tcmis.client.report.beans;

import org.apache.struts.action.ActionForm;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import java.util.Vector;

public class InventoryPlanningInputBean extends ActionForm {

	  private String county;
	  private String companyId;
	  private String facilityId;
	  private String reportId;
	  private String reportName;
	  private String trialRun;
	  private String fileType;
	  private String reportGenerationType;
	  private String reportType;
	  private String reportDate;
	  private String[] areaListArray;
	  private String action;
	  private String submitReport;
	  
	  public InventoryPlanningInputBean() {
	  }

	
	public String getTrialRun() {
		return trialRun;
	}

	public void setTrialRun(String trialRun) {
		this.trialRun = trialRun;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getReportGenerationType() {
		return reportGenerationType;
	}

	public void setReportGenerationType(String reportGenerationType) {
		this.reportGenerationType = reportGenerationType;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	

	public String getReportDate() {
		return reportDate;
	}


	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}


	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	
	
	
	
	public String[] getAreaListArray() {
		return areaListArray;
	}


	public void setAreaListArray(String[] areaListArray) {
		this.areaListArray = areaListArray;
	}


	public String getFacilityId() {
		return facilityId;
	}


	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}


	public String getAction() {
		return action;
	}


	public void setAction(String action) {
		this.action = action;
	}


	public String getSubmitReport() {
		return submitReport;
	}


	public void setSubmitReport(String submitReport) {
		this.submitReport = submitReport;
	}
	
	

		  
}
