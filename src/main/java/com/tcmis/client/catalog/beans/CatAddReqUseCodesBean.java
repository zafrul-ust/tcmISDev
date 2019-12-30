package com.tcmis.client.catalog.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: ApprovedCatalogAddBean <br>
 * @version: 1.0, March 7, 2012 <br>
 *****************************************************************************/

public class CatAddReqUseCodesBean extends BaseDataBean {

	private String catalogCompanyId;
	private String catalogId;
	private String companyId;
	private String catPartNo;
	private Date startDate;
	private Date endDate;
	private BigDecimal requestId;
	private String applicationUseGroupName;
	
	// This is for cat_add_msds_use_code_view
	private BigDecimal materialId;
	

	//constructor
	public CatAddReqUseCodesBean() {
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
	public String getCatPartNo() {
		return catPartNo;
	}

	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}

	public BigDecimal getMaterialId() {
		return materialId;
	}

	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}

	public String getApplicationUseGroupName() {
		return applicationUseGroupName;
	}

	public void setApplicationUseGroupName(String applicationUseGroupName) {
		this.applicationUseGroupName = applicationUseGroupName;
	}

	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}

	public void setCatalogCompanyId(String catalogCompanyId) {
		this.catalogCompanyId = catalogCompanyId;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public BigDecimal getRequestId() {
		return requestId;
	}

	public void setRequestId(BigDecimal requestId) {
		this.requestId = requestId;
	}

  
}