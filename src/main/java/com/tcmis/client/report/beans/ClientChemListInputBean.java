package com.tcmis.client.report.beans;

import com.tcmis.common.framework.BaseDataBean;

import java.math.BigDecimal;
import java.util.Date;

public class ClientChemListInputBean extends BaseDataBean{
	
	private String companyId;
	private String facilityId;
	private String type;
	private String source;
	private String uploadId;
	private String workArea;
	private String workAreaName;
	private String workAreaGroup;
	private String partNo;
	private String msdsNo;
	private Date entryStartDate;
	private Date entryEndDate;
	private Date usageStartDate;
	private Date usageEndDate;
	private String uAction;

	//constructor
	public ClientChemListInputBean() {
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getUploadId() {
		return uploadId;
	}
	public void setUploadId(String uploadId) {
		this.uploadId = uploadId;
	}
	public String getWorkArea() {
		return workArea;
	}
	public void setWorkArea(String workArea) {
		this.workArea = workArea;
	}
	public String getWorkAreaName() {
		return workAreaName;
	}
	public void setWorkAreaName(String workAreaName) {
		this.workAreaName = workAreaName;
	}
	public String getWorkAreaGroup() {
		return workAreaGroup;
	}
	public void setWorkAreaGroup(String workAreaGroup) {
		this.workAreaGroup = workAreaGroup;
	}
	public String getPartNo() {
		return partNo;
	}
	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}
	public String getMsdsNo() {
		return msdsNo;
	}
	public void setMsdsNo(String msdsNo) {
		this.msdsNo = msdsNo;
	}
	public Date getEntryStartDate() {
		return entryStartDate;
	}
	public void setEntryStartDate(Date entryStartDate) {
		this.entryStartDate = entryStartDate;
	}
	public Date getEntryEndDate() {
		return entryEndDate;
	}
	public void setEntryEndDate(Date entryEndDate) {
		this.entryEndDate = entryEndDate;
	}
	public Date getUsageStartDate() {
		return usageStartDate;
	}
	public void setUsageStartDate(Date usageStartDate) {
		this.usageStartDate = usageStartDate;
	}
	public Date getUsageEndDate() {
		return usageEndDate;
	}
	public void setUsageEndDate(Date usageEndDate) {
		this.usageEndDate = usageEndDate;
	}
	public String getuAction() {
		return uAction;
	}
	public void setuAction(String uAction) {
		this.uAction = uAction;
	}
	
}
