package com.tcmis.client.het.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: HetSubstrateIdForMgmtBean <br>
 * @version: 1.0, Aug 11, 2011 <br>
 *****************************************************************************/


public class HetSubstrateIdForMgmtBean extends BaseDataBean {

	private String companyId;
	private String facilityId;
	private BigDecimal substrateId;
	private String substrateCode;
	private String substrate;
	private BigDecimal applicationId;
	private String applicationDesc;
	private BigDecimal areaId;
	private String areaName;
	private BigDecimal buildingId;
	private String buildingName;
	private String reportingEntityId;
	
	private String oldSubstrateCode;
	private String oldSubstrate;
	
	private boolean deleted = false;
	private boolean newSubstrate = false;
	private boolean newSubstrateRow = false;


	//constructor
	public HetSubstrateIdForMgmtBean() {
	}
	
	public boolean isDeleted() {
		return deleted;
	}

	public boolean isNewSubstrate() {
		return newSubstrate;
	}

	public boolean isNewSubstrateRow() {
		return newSubstrateRow;
	}

	public boolean isSubstrateModified() {
		return !oldSubstrateCode.equals(getSubstrateCode()) || !oldSubstrate.equals(getSubstrate());
	}


	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setSubstrateId(BigDecimal substrateId) {
		this.substrateId = substrateId;
	}
	public void setSubstrateCode(String substrateCode) {
		this.substrateCode = substrateCode;
	}
	public void setSubstrate(String substrate) {
		this.substrate = substrate;
	}
	public void setApplicationId(BigDecimal applicationId) {
		this.applicationId = applicationId;
	}
	public void setApplicationDesc(String applicationDesc) {
		this.applicationDesc = applicationDesc;
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
	public void setReportingEntityId(String reportingEntityId) {
		this.reportingEntityId = reportingEntityId;
	}


	//getters
	public String getCompanyId() {
		return companyId;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public BigDecimal getSubstrateId() {
		return substrateId;
	}
	public String getSubstrateCode() {
		return substrateCode;
	}
	public String getSubstrate() {
		return substrate;
	}
	public BigDecimal getApplicationId() {
		return applicationId;
	}
	public String getApplicationDesc() {
		return applicationDesc;
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
	public String getReportingEntityId() {
		return reportingEntityId;
	}

	public String getOldSubstrate() {
		return oldSubstrate;
	}

	public void setOldSubstrate(String oldSubstrate) {
		this.oldSubstrate = oldSubstrate;
	}

	public String getOldSubstrateCode() {
		return oldSubstrateCode;
	}

	public void setOldSubstrateCode(String oldSubstrateCode) {
		this.oldSubstrateCode = oldSubstrateCode;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public void setNewSubstrate(boolean newSubstrate) {
		this.newSubstrate = newSubstrate;
	}

	public void setNewSubstrateRow(boolean newSubstrateRow) {
		this.newSubstrateRow = newSubstrateRow;
	}

}