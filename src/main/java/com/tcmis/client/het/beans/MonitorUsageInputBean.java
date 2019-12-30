package com.tcmis.client.het.beans;

import java.util.Collection;
import java.util.Date;

import com.tcmis.common.framework.BaseInputBean;
import com.tcmis.common.util.StringHandler;

public class MonitorUsageInputBean extends BaseInputBean {
	private String applicationId;
	private Collection<VvHetApplicationMethodBean> applicationMethods;
	private String companyId;
	private String employee;
	private String facilityId;
	private String packaging;
	private Collection<HetPermitBean> permits;
	private String reportingEntity;
	private String searchArgument;
	private String searchField;
	private String searchMode;
	private Collection<VvHetSubstrateBean> substrates;
	private Date usageDateFrom;
	private Date usageDateTo;
	private String usageId;

	public String getApplicationId() {
		return applicationId;
	}

	public Collection<VvHetApplicationMethodBean> getApplicationMethods() {
		return applicationMethods;
	}

	public String getCompanyId() {
		return companyId;
	}

	public String getEmployee() {
		return employee;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public String getPackaging() {
		return packaging;
	}

	public Collection<HetPermitBean> getPermits() {
		return permits;
	}

	public String getReportingEntity() {
		return reportingEntity;
	}

	public String getSearchArgument() {
		return searchArgument;
	}

	public String getSearchField() {
		return searchField;
	}

	public String getSearchMode() {
		return searchMode;
	}

	public Collection<VvHetSubstrateBean> getSubstrates() {
		return substrates;
	}

	public Date getUsageDateFrom() {
		return usageDateFrom;
	}

	public Date getUsageDateTo() {
		return usageDateTo;
	}

	public String getUsageId() {
		return usageId;
	}

	public boolean hasApplicationId() {
		return !StringHandler.isBlankString(applicationId);
	}

	public boolean hasFromDate() {
		return usageDateFrom != null;
	}

	public boolean hasReportingEntity () {
		return !StringHandler.isBlankString(reportingEntity);
	}

	public boolean hasSearchArgument() {
		return !StringHandler.isBlankString(searchArgument);
	}

	public boolean hasToDate() {
		return usageDateTo != null;
	}

	public boolean isDelete(){
		return "delete".equalsIgnoreCase(getuAction());
	}

	public boolean isHistorySearch (){
		return "history".equalsIgnoreCase(getuAction());
	}

	public boolean isContainerSearch() {
		return hasSearchArgument() && "containerId".equalsIgnoreCase(searchField);
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public void setApplicationMethods(Collection<VvHetApplicationMethodBean> applicationMethods) {
		this.applicationMethods = applicationMethods;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setEmployee(String employee) {
		this.employee = employee;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	@Override
	public void setHiddenFormFields() {
		addHiddenFormField("companyId");
		addHiddenFormField("reportingEntity");
		addHiddenFormField("facilityId");
		addHiddenFormField("applicationId");
		addHiddenFormField("searchArgument");
		addHiddenFormField("searchField");
		addHiddenFormField("searchMode");
		addHiddenFormField("usageDateFrom");
		addHiddenFormField("usageDateTo");
	}

	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}

	public void setPermits(Collection<HetPermitBean> permits) {
		this.permits = permits;
	}

	public void setReportingEntity(String reportingEntity) {
		this.reportingEntity = reportingEntity;
	}

	public void setSearchArgument(String searchArgument) {
		this.searchArgument = searchArgument;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public void setSearchMode(String searchMode) {
		this.searchMode = searchMode;
	}

	public void setSubstrates(Collection<VvHetSubstrateBean> substrates) {
		this.substrates = substrates;
	}

	public void setUsageDateFrom(Date usageDateFrom) {
		this.usageDateFrom = usageDateFrom;
	}

	public void setUsageDateTo(Date usageDateTo) {
		this.usageDateTo = usageDateTo;
	}

	public void setUsageId(String usageId) {
		this.usageId = usageId;
	}
}
