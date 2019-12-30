package com.tcmis.client.report.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

public class FacilityStockingLevelViewBean extends BaseDataBean {

	private String companyId;
	private String facilityId;
	private String catPartNo;
	private String itemDesc;
	private String purchasePackaging;
	private BigDecimal minStockingLevel;
	private BigDecimal maxStockingLevel;
	private BigDecimal totalStockingLevel;
	private BigDecimal avgStockingLevel;
	private BigDecimal workAreaCount;
    private String facilityName;
    private String partDescription;

    //constructor
	public FacilityStockingLevelViewBean() {
	}
	
	public String getPartDescription() {
		return partDescription;
	}

	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
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

	public String getCatPartNo() {
		return catPartNo;
	}

	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public String getPurchasePackaging() {
		return purchasePackaging;
	}

	public void setPurchasePackaging(String purchasePackaging) {
		this.purchasePackaging = purchasePackaging;
	}

	public BigDecimal getMinStockingLevel() {
		return minStockingLevel;
	}

	public void setMinStockingLevel(BigDecimal minStockingLevel) {
		this.minStockingLevel = minStockingLevel;
	}

	public BigDecimal getMaxStockingLevel() {
		return maxStockingLevel;
	}

	public void setMaxStockingLevel(BigDecimal maxStockingLevel) {
		this.maxStockingLevel = maxStockingLevel;
	}

	public BigDecimal getTotalStockingLevel() {
		return totalStockingLevel;
	}

	public void setTotalStockingLevel(BigDecimal totalStockingLevel) {
		this.totalStockingLevel = totalStockingLevel;
	}

	public BigDecimal getAvgStockingLevel() {
		return avgStockingLevel;
	}

	public void setAvgStockingLevel(BigDecimal avgStockingLevel) {
		this.avgStockingLevel = avgStockingLevel;
	}

	public BigDecimal getWorkAreaCount() {
		return workAreaCount;
	}

	public void setWorkAreaCount(BigDecimal workAreaCount) {
		this.workAreaCount = workAreaCount;
	}

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }
}
