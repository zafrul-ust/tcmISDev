package com.tcmis.client.catalog.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

public class CaProcessEmissionPointBean extends BaseDataBean {
	private String application;
	private String appEmissionPoint;
	private String companyId;
	private String facilityId;
	private String facEmissionPoint;
	private BigDecimal processEmissionPercentage;
	private BigDecimal processId;
	private BigDecimal requestId;
	
	private String emissionPointDesc;
	
	public CaProcessEmissionPointBean() {
		
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getAppEmissionPoint() {
		return appEmissionPoint;
	}

	public void setAppEmissionPoint(String appEmissionPoint) {
		this.appEmissionPoint = appEmissionPoint;
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

	public String getFacEmissionPoint() {
		return facEmissionPoint;
	}

	public void setFacEmissionPoint(String facEmissionPoint) {
		this.facEmissionPoint = facEmissionPoint;
	}

	public BigDecimal getProcessEmissionPercentage() {
		return processEmissionPercentage;
	}

	public void setProcessEmissionPercentage(BigDecimal processEmissionPercentage) {
		this.processEmissionPercentage = processEmissionPercentage;
	}

	public BigDecimal getProcessId() {
		return processId;
	}

	public void setProcessId(BigDecimal processId) {
		this.processId = processId;
	}

	public BigDecimal getRequestId() {
		return requestId;
	}

	public void setRequestId(BigDecimal requestId) {
		this.requestId = requestId;
	}

	public String getEmissionPointDesc() {
		return emissionPointDesc;
	}

	public void setEmissionPointDesc(String emissionPointDesc) {
		this.emissionPointDesc = emissionPointDesc;
	}
	
}