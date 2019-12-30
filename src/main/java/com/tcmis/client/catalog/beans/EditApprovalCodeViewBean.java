package com.tcmis.client.catalog.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

public class EditApprovalCodeViewBean extends BaseDataBean {

	private BigDecimal approvedId;
	private BigDecimal msdsOrMixtureUseId;
	private Date startDate;
	private Date endDate;
	private String applicationUseGroupId;
	private String usageSubcategoryName;
	private String approvalCode;
	
	public EditApprovalCodeViewBean() {
		
	}

	public BigDecimal getApprovedId() {
		return approvedId;
	}

	public void setApprovedId(BigDecimal approvedId) {
		this.approvedId = approvedId;
	}

	public BigDecimal getMsdsOrMixtureUseId() {
		return msdsOrMixtureUseId;
	}

	public void setMsdsOrMixtureUseId(BigDecimal msdsOrMixtureUseId) {
		this.msdsOrMixtureUseId = msdsOrMixtureUseId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getApplicationUseGroupId() {
		return applicationUseGroupId;
	}

	public void setApplicationUseGroupId(String applicationUseGroupId) {
		this.applicationUseGroupId = applicationUseGroupId;
	}

	public String getUsageSubcategoryName() {
		return usageSubcategoryName;
	}

	public void setUsageSubcategoryName(String usageSubcategoryName) {
		this.usageSubcategoryName = usageSubcategoryName;
	}

	public String getApprovalCode() {
		return approvalCode;
	}

	public void setApprovalCode(String approvalCode) {
		this.approvalCode = approvalCode;
	}
}
