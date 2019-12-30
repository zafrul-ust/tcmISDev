package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.util.*;

import com.tcmis.common.framework.*;

/******************************************************************************
 * CLASSNAME: HubCabinetViewBean <br>
 * @version: 1.0, Jul 22, 2004 <br>
 *****************************************************************************/

public class MinMaxLevelInputBean
    extends BaseDataBean {

  private String opsEntityId;
  private String hub;
  private String inventoryGroup;
  private String criterion;
  private String criteria;
  private String searchMode;

/*
  private String hubName;
  private String facilityId;
  private String facilityName;
  private String application;
  private BigDecimal cabinetId;
  private String cabinetName;
  private String itemId;
  private String action;
  private BigDecimal binId;
  private String status;
*/
  private String itemId;
  private String companyId;
  private String catalogId;
  private String catPartNo;
  private String partGroupNo;

  private String stockingMethod;
  private BigDecimal reorderPoint;
  private BigDecimal stockingLevel;
  private BigDecimal lookAheadDays;
  private BigDecimal reorderQuantity;
  private String oldStockingMethod;
  private BigDecimal oldReorderPoint;
  private BigDecimal oldStockingLevel;
  private BigDecimal oldLookAheadDays;
  private BigDecimal oldReorderQuantity;
  private String receiptProcessingMethod;
  private String oldReceiptProcessingMethod;
  private String billingMethod;
  private String oldBillingMethod;
  private String remarks;
  private BigDecimal personnelId;
  private String okDoUpdate;
  private String dropShipOverride;
  private String catalogCompanyId;
  private Date levelHoldEndDate;
  private Date oldLevelHoldEndDate;
  private BigDecimal projectedLeadTime;
  private BigDecimal oldProjectedLeadTime;

    public String getCatalogCompanyId() {
        return catalogCompanyId;
    }

    public void setCatalogCompanyId(String catalogCompanyId) {
        this.catalogCompanyId = catalogCompanyId;
    }

    public String getDropShipOverride() {
	return dropShipOverride;
}

public void setDropShipOverride(String dropShipOverride) {
	this.dropShipOverride = dropShipOverride;
}

//constructor
  public MinMaxLevelInputBean() {
  }

  //setters

  public void setInventoryGroup(String inventoryGroup) {
    this.inventoryGroup = inventoryGroup;
  }

  public void setCriterion(String criterion) {
    this.criterion = criterion;
  }

  public void setCriteria(String criteria) {
    this.criteria = criteria;
  }
/*
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

  public void setStatus(String s) {
    this.status = s;
  }
*/

  public void setItemId(String s) {
    this.itemId = s;
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

  public void setCatPartNo(String s) {
    this.catPartNo = s;
  }

  public void setStockingMethod(String stockingMethod) {
    this.stockingMethod = stockingMethod;
  }

  public void setReorderPoint(BigDecimal reorderPoint) {
    this.reorderPoint = reorderPoint;
  }

  public void setStockingLevel(BigDecimal stockingLevel) {
    this.stockingLevel = stockingLevel;
  }

  public void setLookAheadDays(BigDecimal lookAheadDays) {
    this.lookAheadDays = lookAheadDays;
  }

  public void setReorderQuantity(BigDecimal reorderQuantity) {
    this.reorderQuantity = reorderQuantity;
  }

  public void setOldStockingMethod(String oldStockingMethod) {
    this.oldStockingMethod = oldStockingMethod;
  }

  public void setOldReorderPoint(BigDecimal oldReorderPoint) {
    this.oldReorderPoint = oldReorderPoint;
  }

  public void setOldStockingLevel(BigDecimal oldStockingLevel) {
    this.oldStockingLevel = oldStockingLevel;
  }

  public void setOldLookAheadDays(BigDecimal oldLookAheadDays) {
    this.oldLookAheadDays = oldLookAheadDays;
  }

  public void setOldReorderQuantity(BigDecimal oldReorderQuantity) {
    this.oldReorderQuantity = oldReorderQuantity;
  }

  public void setRemarks(String s) {
    this.remarks = s;
  }

  public void setReceiptProcessingMethod(String s) {
    this.receiptProcessingMethod = s;
  }

  public void setOldReceiptProcessingMethod(String s) {
    this.oldReceiptProcessingMethod = s;
  }

  public void setBillingMethod(String s) {
    this.billingMethod = s;
  }

  public void setOldBillingMethod(String s) {
    this.oldBillingMethod = s;
  }

  public void setPersonnelId(BigDecimal b) {
    this.personnelId = b;
  }

  //getters

  public String getInventoryGroup() {
    return inventoryGroup;
  }

  public String getCriterion() {
    return criterion;
  }

  public String getCriteria() {
    return criteria;
  }
/*
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

  public String getStatus() {
    return this.status;
  }
*/
  public String getItemId() {
    return this.itemId;
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

  public String getCatPartNo() {
    return this.catPartNo;
  }

  public String getStockingMethod() {
    return stockingMethod;
  }

  public BigDecimal getReorderPoint() {
    return reorderPoint;
  }

  public BigDecimal getStockingLevel() {
    return stockingLevel;
  }

  public BigDecimal getLookAheadDays() {
    return lookAheadDays;
  }

  public BigDecimal getReorderQuantity() {
    return reorderQuantity;
  }

  public String getOldStockingMethod() {
    return oldStockingMethod;
  }

  public BigDecimal getOldReorderPoint() {
    return oldReorderPoint;
  }

  public BigDecimal getOldStockingLevel() {
    return oldStockingLevel;
  }

  public BigDecimal getOldLookAheadDays() {
    return oldLookAheadDays;
  }

  public BigDecimal getOldReorderQuantity() {
    return oldReorderQuantity;
  }

  public String getRemarks() {
    return this.remarks;
  }

  public String getReceiptProcessingMethod() {
    return this.receiptProcessingMethod;
  }

  public String getOldReceiptProcessingMethod() {
    return this.oldReceiptProcessingMethod;
  }

  public String getBillingMethod() {
    return this.billingMethod;
  }

  public String getOldBillingMethod() {
    return this.oldBillingMethod;
  }

  public BigDecimal getPersonnelId() {
    return this.personnelId;
  }

public String getSearchMode() {
	return searchMode;
}

public void setSearchMode(String searchMode) {
	this.searchMode = searchMode;
}

public String getOkDoUpdate() {
	return okDoUpdate;
}

public void setOkDoUpdate(String okDoUpdate) {
	this.okDoUpdate = okDoUpdate;
}

public String getHub() {
	return hub;
}

public void setHub(String hub) {
	this.hub = hub;
}

public BigDecimal getProjectedLeadTime() {
	return projectedLeadTime;
}

public void setProjectedLeadTime(BigDecimal projectedLeadTime) {
	this.projectedLeadTime = projectedLeadTime;
}

public BigDecimal getOldProjectedLeadTime() {
	return oldProjectedLeadTime;
}

public void setOldProjectedLeadTime(BigDecimal oldProjectedLeadTime) {
	this.oldProjectedLeadTime = oldProjectedLeadTime;
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

public String getOpsEntityId() {
	return opsEntityId;
}

public void setOpsEntityId(String opsEntityId) {
	this.opsEntityId = opsEntityId;
}

}