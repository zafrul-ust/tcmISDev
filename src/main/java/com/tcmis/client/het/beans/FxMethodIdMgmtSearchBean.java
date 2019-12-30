package com.tcmis.client.het.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: FxMethodIdMgmtSearchBean <br>
 * 
 * @version: 1.0, Aug 11, 2011 <br>
 *****************************************************************************/

public class FxMethodIdMgmtSearchBean extends BaseDataBean {

	private String		applicationDesc;
	private BigDecimal	applicationId;
	private BigDecimal	areaId;
	private String		areaName;
	private BigDecimal	buildingId;
	private String		buildingName;
	private String		companyId;
	private boolean		deleted			= false;
	private String		facilityId;
	private boolean		forSolvent		= false;
	private String		method;
	private String		methodCode;
	private BigDecimal	methodId;
	private boolean		newMethod		= false;
	private boolean		newMethodRow	= false;
	private boolean		oldForSolvent	= false;
	private String		oldMethod;
	private String		oldMethodCode;

	// constructor
	public FxMethodIdMgmtSearchBean() {
	}

	public String getApplicationDesc() {
		return applicationDesc;
	}

	public BigDecimal getApplicationId() {
		return applicationId;
	}

	public BigDecimal getAreaId() {
		return areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public BigDecimal getBuildingId() {
		return buildingId;
	}

	public String getBuildingName() {
		return buildingName;
	}

	// getters
	public String getCompanyId() {
		return companyId;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public String getMethod() {
		return method;
	}

	public String getMethodCode() {
		return methodCode;
	}

	public BigDecimal getMethodId() {
		return methodId;
	}

	public String getOldMethod() {
		return oldMethod;
	}

	public String getOldMethodCode() {
		return oldMethodCode;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public boolean isForSolvent() {
		return forSolvent;
	}

	public boolean isMethodModified() {
		return !oldMethodCode.equals(getMethodCode()) || !oldMethod.equals(getMethod()) || oldForSolvent != forSolvent;
	}

	public boolean isNewMethod() {
		return newMethod;
	}

	public boolean isNewMethodRow() {
		return newMethodRow;
	}

	public boolean isOldForSolvent() {
		return oldForSolvent;
	}

	public void setApplicationDesc(String applicationDesc) {
		this.applicationDesc = applicationDesc;
	}

	public void setApplicationId(BigDecimal applicationId) {
		this.applicationId = applicationId;
	}

	public void setAreaId(BigDecimal areaId) {
		this.areaId = areaId;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public void setBuildingId(BigDecimal buildingId) {
		this.buildingId = buildingId;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	// setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public void setForSolvent(boolean forSolvent) {
		this.forSolvent = forSolvent;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public void setMethodCode(String methodCode) {
		this.methodCode = methodCode;
	}

	public void setMethodId(BigDecimal methodId) {
		this.methodId = methodId;
	}

	public void setNewMethod(boolean newMethod) {
		this.newMethod = newMethod;
	}

	public void setNewMethodRow(boolean newMethodRow) {
		this.newMethodRow = newMethodRow;
	}

	public void setOldForSolvent(boolean oldForSolvent) {
		this.oldForSolvent = oldForSolvent;
	}

	public void setOldMethod(String oldMethod) {
		this.oldMethod = oldMethod;
	}

	public void setOldMethodCode(String oldMethodCode) {
		this.oldMethodCode = oldMethodCode;
	}

}