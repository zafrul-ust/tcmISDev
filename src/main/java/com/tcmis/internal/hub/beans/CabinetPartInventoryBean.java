package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: CabinetPartInventoryBean <br>
 * @version: 1.0, Oct 16, 2006 <br>
 *****************************************************************************/

public class CabinetPartInventoryBean extends BaseDataBean {

	private String application;
	private BigDecimal binId;
	private String binName;
	private String catalogCompanyId;
	private String catalogId;
	private String catPartNo;
	private String companyId;
	private String countType;
	private String defaultApplicationMethodCod;
	private String defaultPartType;
	private String defaultPermitId;
	private String defaultSubstrateCode;
	private String facilityId;
	private BigDecimal kanbanReorderQuantity;
	private BigDecimal leadTimeDays;
	private BigDecimal partGroupNo;
	private BigDecimal reorderPoint;
	private BigDecimal reorderQuantity;
	private String status;
	private BigDecimal stockingLevel;
    private String tierIIStorageTemperatureCode;
    private BigDecimal modifiedBy;
    private Date dateModified;
    private String remarks;

    //constructor
	public CabinetPartInventoryBean() {
	}

	public String getApplication() {
		return application;
	}

	public BigDecimal getBinId() {
		return binId;
	}

	public String getBinName() {
		return binName;
	}

	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public String getCatPartNo() {
		return catPartNo;
	}

	//getters
	public String getCompanyId() {
		return companyId;
	}

	public String getCountType() {
		return countType;
	}

	public String getDefaultApplicationMethodCod() {
		return defaultApplicationMethodCod;
	}

	public String getDefaultPartType() {
		return defaultPartType;
	}

	public String getDefaultPermitId() {
		return defaultPermitId;
	}

	public String getDefaultSubstrateCode() {
		return defaultSubstrateCode;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public BigDecimal getKanbanReorderQuantity() {
		return kanbanReorderQuantity;
	}

	public BigDecimal getLeadTimeDays() {
		return leadTimeDays;
	}

	public BigDecimal getPartGroupNo() {
		return partGroupNo;
	}

	public BigDecimal getReorderPoint() {
		return reorderPoint;
	}

	public BigDecimal getReorderQuantity() {
		return reorderQuantity;
	}

	public String getStatus() {
		return status;
	}

	public BigDecimal getStockingLevel() {
		return stockingLevel;
	}
	
	public String getTierIIStorageTemperatureCode() {
		return this.tierIIStorageTemperatureCode;
	}
	
	public void setTierIIStorageTemperatureCode(String tierIIStorageTemperatureCode) {
		this.tierIIStorageTemperatureCode = tierIIStorageTemperatureCode;
	}
	

	public void setApplication(String application) {
		this.application = application;
	}

	public void setBinId(BigDecimal binId) {
		this.binId = binId;
	}

	public void setBinName(String binName) {
		this.binName = binName;
	}

	public void setCatalogCompanyId(String catalogCompanyId) {
		this.catalogCompanyId = catalogCompanyId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setCountType(String countType) {
		this.countType = countType;
	}

	public void setDefaultApplicationMethodCod(String defaultApplicationMethodId) {
		this.defaultApplicationMethodCod = defaultApplicationMethodId;
	}

	public void setDefaultPartType(String defaultPartType) {
		this.defaultPartType = defaultPartType;
	}

	public void setDefaultPermitId(String defaultPermitId) {
		this.defaultPermitId = defaultPermitId;
	}

	public void setDefaultSubstrateCode(String defaultSubstrateId) {
		this.defaultSubstrateCode = defaultSubstrateId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public void setKanbanReorderQuantity(BigDecimal kanbanReorderQuantity) {
		this.kanbanReorderQuantity = kanbanReorderQuantity;
	}

	public void setLeadTimeDays(BigDecimal leadTimeDays) {
		this.leadTimeDays = leadTimeDays;
	}

	public void setPartGroupNo(BigDecimal partGroupNo) {
		this.partGroupNo = partGroupNo;
	}

	public void setReorderPoint(BigDecimal reorderPoint) {
		this.reorderPoint = reorderPoint;
	}

	public void setReorderQuantity(BigDecimal reorderQuantity) {
		this.reorderQuantity = reorderQuantity;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setStockingLevel(BigDecimal stockingLevel) {
		this.stockingLevel = stockingLevel;
	}

    public BigDecimal getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(BigDecimal modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}