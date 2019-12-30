package com.tcmis.client.het.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: HetRecipeItemSearchViewBean <br>
 * 
 * @version: 1.0, May 31, 2011 <br>
 *****************************************************************************/

public class FxItemSearchBean extends BaseDataBean {

	private BigDecimal	amountRemaining;
	private String		application;
	private String		catPartNo;
	private String		companyId;
	private String		containerId;
	private String		containerSize;
	private String		containerType;
	private String		custMsdsNo;
	private String		customerMsdsNumber;
	private boolean		diluent;
	private String		facilityId;
	private BigDecimal	itemId;
	private BigDecimal	kitId;
	private String		materialDesc;
	private BigDecimal	materialId;
	private String		reportingEntityId;
	private boolean		solvent;
	private String		unitOfMeasure;

	// constructor
	public FxItemSearchBean() {
	}

	public BigDecimal getAmountRemaining() {
		return amountRemaining;
	}

	public String getApplication() {
		return application;
	}

	// getters
	public String getCatPartNo() {
		return catPartNo;
	}

	public String getCompanyId() {
		return companyId;
	}

	public String getContainerId() {
		return containerId;
	}

	public String getContainerSize() {
		return containerSize;
	}

	public String getContainerType() {
		return containerType;
	}

	public String getCustMsdsNo() {
		return custMsdsNo;
	}

	public String getCustomerMsdsNumber() {
		return customerMsdsNumber;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public BigDecimal getKitId() {
		return kitId;
	}

	public String getMaterialDesc() {
		return materialDesc;
	}

	public BigDecimal getMaterialId() {
		return materialId;
	}

	public String getReportingEntityId() {
		return reportingEntityId;
	}

	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public boolean isDiluent() {
		return diluent;
	}

	public boolean isSolvent() {
		return solvent;
	}

	public void setAmountRemaining(BigDecimal amountRemaining) {
		this.amountRemaining = amountRemaining;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	// setters
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setContainerId(String containerId) {
		this.containerId = containerId;
	}

	public void setContainerSize(String containerSize) {
		this.containerSize = containerSize;
	}

	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

	public void setCustMsdsNo(String custMsdsNo) {
		this.custMsdsNo = custMsdsNo;
	}

	public void setCustomerMsdsNumber(String custMsdsNo) {
		this.customerMsdsNumber = custMsdsNo;
	}

	public void setDiluent(boolean diluent) {
		this.diluent = diluent;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public void setKitId(BigDecimal kitId) {
		this.kitId = kitId;
	}

	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}

	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}

	public void setReportingEntityId(String reportingEntityId) {
		this.reportingEntityId = reportingEntityId;
	}

	public void setSolvent(boolean solvent) {
		this.solvent = solvent;
	}

	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}
}