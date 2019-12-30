package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.util.*;

import com.tcmis.common.framework.*;

/******************************************************************************
 * CLASSNAME: HubCabinetViewBean <br>
 * @version: 1.0, Jul 22, 2004 <br>
 *****************************************************************************/

public class CabinetLevelInputBean
    extends BaseDataBean {

  private String branchPlant;
  private String hubName;
  private String facilityId;
  private String facilityName;
  private String application;
  private BigDecimal cabinetId;
  private String cabinetName;
  private String itemId;
  private String action;
  private BigDecimal binId;
  private String catPartNo;
  private String status;
  private String remarks;
  private String companyId;
  private String catalogId;
  private String partGroupNo;
  private BigDecimal reorderPoint;
  private BigDecimal stockingLevel;
  private BigDecimal leadTimeDays;
  private BigDecimal oldReorderPoint;
  private BigDecimal oldStockingLevel;
  private BigDecimal oldLeadTimeDays;
  private BigDecimal personnelId;
  private String orderingApplication;
  private String catalogCompanyId;
  private String applicationDesc;
  private String inventoryGroup;
  private BigDecimal reorderQuantity;
  private BigDecimal oldReorderQuantity;
  private BigDecimal kanbanReorderQuantity;
  private BigDecimal oldKanbanReorderQuantity;
  private Date levelHoldEndDate;
  private Date oldLevelHoldEndDate;
  private String ownershipName;
  private String putAwayMethodOverride;
  private String dropShipOverride;

  //constructor
  public CabinetLevelInputBean() {
  }

  //setters
  public void setBranchPlant(String branchPlant) {
    this.branchPlant = branchPlant;
  }

  public void setHubName(String s) {
    this.hubName = s;
  }

  public void setFacilityId(String facilityId) {
    this.facilityId = facilityId;
  }

  public void setFacilityName(String facilityName) {
    this.facilityName = facilityName;
  }

  public void setApplication(String application) {
    this.application = application;
  }

  public void setCabinetId(BigDecimal cabinetId) {
    this.cabinetId = cabinetId;
  }

  public void setItemId(String s) {
    this.itemId = s;
  }

  public void setCabinetName(String s) {
    this.cabinetName = s;
  }

  public void setAction(String s) {
    this.action = s;
  }

  public void setBinId(BigDecimal b) {
    this.binId = b;
  }

  public void setCatPartNo(String s) {
    this.catPartNo = s;
  }

  public void setStatus(String s) {
    this.status = s;
  }

  public void setRemarks(String s) {
    this.remarks = s;
  }

  public void setCompanyId(String s) {
    this.companyId = s;
  }

  public void setCatalogId(String s) {
    this.catalogId = s;
  }

  public void setPartGroupNo(String s) {
    this.partGroupNo = s;
  }

  public void setReorderPoint(BigDecimal reorderPoint) {
    this.reorderPoint = reorderPoint;
  }

  public void setStockingLevel(BigDecimal stockingLevel) {
    this.stockingLevel = stockingLevel;
  }

  public void setLeadTimeDays(BigDecimal leadTimeDays) {
    this.leadTimeDays = leadTimeDays;
  }

  public void setOldReorderPoint(BigDecimal oldReorderPoint) {
    this.oldReorderPoint = oldReorderPoint;
  }

  public void setOldStockingLevel(BigDecimal oldStockingLevel) {
    this.oldStockingLevel = oldStockingLevel;
  }

  public void setOldLeadTimeDays(BigDecimal oldLeadTimeDays) {
    this.oldLeadTimeDays = oldLeadTimeDays;
  }

  public void setPersonnelId(BigDecimal b) {
    this.personnelId = b;
  }

 public void setOrderingApplication(String orderingApplication) {
	this.orderingApplication = orderingApplication;
 }

 public void setCatalogCompanyId(String catalogCompanyId) {
	 this.catalogCompanyId = catalogCompanyId;
 }

  //getters
  public String getBranchPlant() {
    return branchPlant;
  }

  public String getHubName() {
    return hubName;
  }

  public String getFacilityId() {
    return facilityId;
  }

  public String getFacilityName() {
    return facilityName;
  }

  public String getApplication() {
    return application;
  }

  public BigDecimal getCabinetId() {
    return cabinetId;
  }

  public String getItemId() {
    return this.itemId;
  }

  public String getCabinetName() {
    return this.cabinetName;
  }

  public String getAction() {
    return this.action;
  }

  public BigDecimal getBinId() {
    return this.binId;
  }

  public String getCatPartNo() {
    return this.catPartNo;
  }

  public String getStatus() {
    return this.status;
  }

  public String getRemarks() {
    return this.remarks;
  }

  public String getCompanyId() {
    return this.companyId;
  }

  public String getCatalogId() {
    return this.catalogId;
  }

  public String getPartGroupNo() {
    return this.partGroupNo;
  }

  public BigDecimal getReorderPoint() {
    return reorderPoint;
  }

  public BigDecimal getStockingLevel() {
    return stockingLevel;
  }

  public BigDecimal getLeadTimeDays() {
    return leadTimeDays;
  }

  public BigDecimal getOldReorderPoint() {
    return oldReorderPoint;
  }

  public BigDecimal getOldStockingLevel() {
    return oldStockingLevel;
  }

  public BigDecimal getOldLeadTimeDays() {
    return oldLeadTimeDays;
  }

  public BigDecimal getPersonnelId() {
    return this.personnelId;
  }

  public String getOrderingApplication() {
	 return this.orderingApplication;
	}

  public String getCatalogCompanyId() {
	  return catalogCompanyId;
  }

	public String getApplicationDesc() {
		return applicationDesc;
	}

	public void setApplicationDesc(String applicationDesc) {
		this.applicationDesc = applicationDesc;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public BigDecimal getReorderQuantity() {
		return reorderQuantity;
	}

	public void setReorderQuantity(BigDecimal reorderQuantity) {
		this.reorderQuantity = reorderQuantity;
	}

	public BigDecimal getOldReorderQuantity() {
		return oldReorderQuantity;
	}

	public void setOldReorderQuantity(BigDecimal oldReorderQuantity) {
		this.oldReorderQuantity = oldReorderQuantity;
	}

	public BigDecimal getKanbanReorderQuantity() {
		return kanbanReorderQuantity;
	}

	public void setKanbanReorderQuantity(BigDecimal kanbanReorderQuantity) {
		this.kanbanReorderQuantity = kanbanReorderQuantity;
	}

	public BigDecimal getOldKanbanReorderQuantity() {
		return oldKanbanReorderQuantity;
	}

	public void setOldKanbanReorderQuantity(BigDecimal oldKanbanReorderQuantity) {
		this.oldKanbanReorderQuantity = oldKanbanReorderQuantity;
	}

	public Date getLevelHoldEndDate() {
		return levelHoldEndDate;
	}

	public void setLevelHoldEndDate(Date levelHoldEndDate) {
		this.levelHoldEndDate = levelHoldEndDate;
	}

	public Date getOldLevelHoldEndDate() {
		return oldLevelHoldEndDate;
	}

	public void setOldLevelHoldEndDate(Date oldLevelHoldEndDate) {
		this.oldLevelHoldEndDate = oldLevelHoldEndDate;
	}

    public String getOwnershipName() {
        return ownershipName;
    }

    public void setOwnershipName(String ownershipName) {
        this.ownershipName = ownershipName;
    }

    public String getPutAwayMethodOverride() {
        return putAwayMethodOverride;
    }

    public void setPutAwayMethodOverride(String putAwayMethodOverride) {
        this.putAwayMethodOverride = putAwayMethodOverride;
    }

    public String getDropShipOverride() {
        return dropShipOverride;
    }

    public void setDropShipOverride(String dropShipOverride) {
        this.dropShipOverride = dropShipOverride;
    }
}