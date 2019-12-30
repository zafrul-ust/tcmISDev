package com.tcmis.internal.catalog.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseInputBean;

public class SdsAuthoringSelectionInputBean extends BaseInputBean {

	private final String SELECT = "select";
	private final String REQUEST = "request";
	
	private BigDecimal materialId;
	private Date revisionDate;
	private String companyId;
	private String facilityId;
	
	public BigDecimal getMaterialId() {
		return materialId;
	}

	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}

	public Date getRevisionDate() {
		return revisionDate;
	}

	public void setRevisionDate(Date revisionDate) {
		this.revisionDate = revisionDate;
	}
	
	public boolean isSelect() {
		return SELECT.equals(uAction);
	}
	
	public boolean isRequest() {
		return REQUEST.equals(uAction);
	}

	public String getCompanyId() {
		return companyId;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	@Override
	public void setHiddenFormFields() {
		// TODO Auto-generated method stub
		
	}

}
