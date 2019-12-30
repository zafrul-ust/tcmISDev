package com.tcmis.client.report.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

public class InventoryTurnReportTblBean extends BaseDataBean {
	
	 private String companyId;
	 private String companyName;
	 private String divisionId;
	 private String divisionDescription;
	 private String facilityGroupId;
	 private String facilityGroupDescription;
	 private String facilityId;
	 private String facilityName;
	 private String reportingEntityId;
	 private String reportingEntityDescription;
	 private String application;
	 private String applicationDesc;
	 private String catalogId;
	 private String catalogDesc;
	 private String catPartNo;
	 private String partDescription;
	 private BigDecimal reorderPoint;
	 private BigDecimal stockingLevel;
	 private BigDecimal reorderQuantity;
	 private BigDecimal kanbanReorderQuantity;
	 private String binName;
	 private BigDecimal binId;
	 private String packageSize;
	 private Date lastCounted;
	 private BigDecimal lastUsedQty;
	 private BigDecimal usedMonth1;
	 private BigDecimal usedMonth2;
	 private BigDecimal usedMonth3;
	 private BigDecimal usedMonth4;
	 private BigDecimal usedMonth5;
	 private BigDecimal usedMonth6;
	 private BigDecimal usedHalfYear;
	 private BigDecimal avgUsePerWeek;
	 private BigDecimal turnsLastYear;
	 
	 private String onlyWithBin;
	 private String uAction;
	 
	public String getuAction() {
		return uAction;
	}

	public void setuAction(String uAction) {
		this.uAction = uAction;
	}

	//constructor
	 public InventoryTurnReportTblBean() {
	 }

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getApplicationDesc() {
		return applicationDesc;
	}

	public void setApplicationDesc(String applicationDesc) {
		this.applicationDesc = applicationDesc;
	}

	public BigDecimal getAvgUsePerWeek() {
		return avgUsePerWeek;
	}

	public void setAvgUsePerWeek(BigDecimal avgUsePerWeek) {
		this.avgUsePerWeek = avgUsePerWeek;
	}

	public BigDecimal getBinId() {
		return binId;
	}

	public void setBinId(BigDecimal binId) {
		this.binId = binId;
	}

	public String getBinName() {
		return binName;
	}

	public void setBinName(String binName) {
		this.binName = binName;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public String getCatPartNo() {
		return catPartNo;
	}

	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getDivisionDescription() {
		return divisionDescription;
	}

	public void setDivisionDescription(String divisionDescription) {
		this.divisionDescription = divisionDescription;
	}

	public String getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(String divisionId) {
		this.divisionId = divisionId;
	}

	public String getFacilityGroupDescription() {
		return facilityGroupDescription;
	}

	public void setFacilityGroupDescription(String facilityGroupDescription) {
		this.facilityGroupDescription = facilityGroupDescription;
	}

	public String getFacilityGroupId() {
		return facilityGroupId;
	}

	public void setFacilityGroupId(String facilityGroupId) {
		this.facilityGroupId = facilityGroupId;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public BigDecimal getKanbanReorderQuantity() {
		return kanbanReorderQuantity;
	}

	public void setKanbanReorderQuantity(BigDecimal kanbanReorderQuantity) {
		this.kanbanReorderQuantity = kanbanReorderQuantity;
	}

	public Date getLastCounted() {
		return lastCounted;
	}

	public void setLastCounted(Date lastCounted) {
		this.lastCounted = lastCounted;
	}

	public BigDecimal getLastUsedQty() {
		return lastUsedQty;
	}

	public void setLastUsedQty(BigDecimal lastUsedQty) {
		this.lastUsedQty = lastUsedQty;
	}

	public String getPackageSize() {
		return packageSize;
	}

	public void setPackageSize(String packageSize) {
		this.packageSize = packageSize;
	}

	public String getPartDescription() {
		return partDescription;
	}

	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}

	public BigDecimal getReorderPoint() {
		return reorderPoint;
	}

	public void setReorderPoint(BigDecimal reorderPoint) {
		this.reorderPoint = reorderPoint;
	}

	public BigDecimal getReorderQuantity() {
		return reorderQuantity;
	}

	public void setReorderQuantity(BigDecimal reorderQuantity) {
		this.reorderQuantity = reorderQuantity;
	}

	public String getReportingEntityDescription() {
		return reportingEntityDescription;
	}

	public void setReportingEntityDescription(String reportingEntityDescription) {
		this.reportingEntityDescription = reportingEntityDescription;
	}

	public String getReportingEntityId() {
		return reportingEntityId;
	}

	public void setReportingEntityId(String reportingEntityId) {
		this.reportingEntityId = reportingEntityId;
	}

	public BigDecimal getStockingLevel() {
		return stockingLevel;
	}

	public void setStockingLevel(BigDecimal stockingLevel) {
		this.stockingLevel = stockingLevel;
	}

	public BigDecimal getTurnsLastYear() {
		return turnsLastYear;
	}

	public void setTurnsLastYear(BigDecimal turnsLastYear) {
		this.turnsLastYear = turnsLastYear;
	}

	public BigDecimal getUsedHalfYear() {
		return usedHalfYear;
	}

	public void setUsedHalfYear(BigDecimal usedHalfYear) {
		this.usedHalfYear = usedHalfYear;
	}

	public BigDecimal getUsedMonth1() {
		return usedMonth1;
	}

	public void setUsedMonth1(BigDecimal usedMonth1) {
		this.usedMonth1 = usedMonth1;
	}

	public BigDecimal getUsedMonth2() {
		return usedMonth2;
	}

	public void setUsedMonth2(BigDecimal usedMonth2) {
		this.usedMonth2 = usedMonth2;
	}

	public BigDecimal getUsedMonth3() {
		return usedMonth3;
	}

	public void setUsedMonth3(BigDecimal usedMonth3) {
		this.usedMonth3 = usedMonth3;
	}

	public BigDecimal getUsedMonth4() {
		return usedMonth4;
	}

	public void setUsedMonth4(BigDecimal usedMonth4) {
		this.usedMonth4 = usedMonth4;
	}

	public BigDecimal getUsedMonth5() {
		return usedMonth5;
	}

	public void setUsedMonth5(BigDecimal usedMonth5) {
		this.usedMonth5 = usedMonth5;
	}

	public BigDecimal getUsedMonth6() {
		return usedMonth6;
	}

	public void setUsedMonth6(BigDecimal usedMonth6) {
		this.usedMonth6 = usedMonth6;
	}

	public String getOnlyWithBin() {
		return onlyWithBin;
	}

	public void setOnlyWithBin(String onlyWithBin) {
		this.onlyWithBin = onlyWithBin;
	}

	public String getCatalogDesc() {
		return catalogDesc;
	}

	public void setCatalogDesc(String catalogDesc) {
		this.catalogDesc = catalogDesc;
	}
	
	 
	 
	 
}
