package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: CabinetPartLevelLogViewBean <br>
 * @version: 1.0, Oct 17, 2006 <br>
 *****************************************************************************/

public class CabinetPartLevelLogViewBean
    extends BaseDataBean {

  private String companyId;
  private String catalogId;
  private String catPartNo;
  private BigDecimal partGroupNo;
  private String facilityId;
  private String application;
  private BigDecimal oldReorderPoint;
  private BigDecimal oldStockingLevel;
  private BigDecimal newReorderPoint;
  private BigDecimal newStockingLevel;
  private Date dateModified;
  private BigDecimal modifiedBy;
  private String remarks;
  private BigDecimal oldLeadTimeDays;
  private BigDecimal newLeadTimeDays;
  private String modifiedByName;
  private BigDecimal oldReorderQuantity;
  private BigDecimal newReorderQuantity;
  private BigDecimal oldKanbanReorderQuantity;
  private BigDecimal newKanbanReorderQuantity;
  private Date newLevelHoldEndDate;
  private Date oldLevelHoldEndDate;
  private String oldStatus;
  private String newStatus;
  private String oldCountType;
  private String newCountType;
  private String oldReceiptProcessingMethod;
  private String newReceiptProcessingMethod;
  private String oldTierIITemperature;
  private String newTierIITemperature;
  private BigDecimal oldTierIITemperatureCode;
  private BigDecimal newTierIITemperatureCode;
  private String oldOwnershipName;
  private String newOwnershipName;
  private String oldBinName;
  private String newBinName;
  private String newDropShipOverride;
  private String oldDropShipOverride;

  //constructor
  public CabinetPartLevelLogViewBean() {
  }

  //setters
  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public void setCatalogId(String catalogId) {
    this.catalogId = catalogId;
  }

  public void setCatPartNo(String catPartNo) {
    this.catPartNo = catPartNo;
  }

  public void setPartGroupNo(BigDecimal partGroupNo) {
    this.partGroupNo = partGroupNo;
  }

  public void setFacilityId(String facilityId) {
    this.facilityId = facilityId;
  }

  public void setApplication(String application) {
    this.application = application;
  }

  public void setOldReorderPoint(BigDecimal oldReorderPoint) {
    this.oldReorderPoint = oldReorderPoint;
  }

  public void setOldStockingLevel(BigDecimal oldStockingLevel) {
    this.oldStockingLevel = oldStockingLevel;
  }

  public void setNewReorderPoint(BigDecimal newReorderPoint) {
    this.newReorderPoint = newReorderPoint;
  }

  public void setNewStockingLevel(BigDecimal newStockingLevel) {
    this.newStockingLevel = newStockingLevel;
  }

  public void setDateModified(Date dateModified) {
    this.dateModified = dateModified;
  }

  public void setModifiedBy(BigDecimal modifiedBy) {
    this.modifiedBy = modifiedBy;
  }

  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }

  public void setOldLeadTimeDays(BigDecimal oldLeadTimeDays) {
    this.oldLeadTimeDays = oldLeadTimeDays;
  }

  public void setNewLeadTimeDays(BigDecimal newLeadTimeDays) {
    this.newLeadTimeDays = newLeadTimeDays;
  }

  public void setModifiedByName(String modifiedByName) {
    this.modifiedByName = modifiedByName;
  }

  //getters
  public String getCompanyId() {
    return companyId;
  }

  public String getCatalogId() {
    return catalogId;
  }

  public String getCatPartNo() {
    return catPartNo;
  }

  public BigDecimal getPartGroupNo() {
    return partGroupNo;
  }

  public String getFacilityId() {
    return facilityId;
  }

  public String getApplication() {
    return application;
  }

  public BigDecimal getOldReorderPoint() {
    return oldReorderPoint;
  }

  public BigDecimal getOldStockingLevel() {
    return oldStockingLevel;
  }

  public BigDecimal getNewReorderPoint() {
    return newReorderPoint;
  }

  public BigDecimal getNewStockingLevel() {
    return newStockingLevel;
  }

  public Date getDateModified() {
    return dateModified;
  }

  public BigDecimal getModifiedBy() {
    return modifiedBy;
  }

  public String getRemarks() {
    return remarks;
  }

  public BigDecimal getOldLeadTimeDays() {
    return oldLeadTimeDays;
  }

  public BigDecimal getNewLeadTimeDays() {
    return newLeadTimeDays;
  }

  public String getModifiedByName() {
    return modifiedByName;
  }

	public BigDecimal getOldReorderQuantity() {
		return oldReorderQuantity;
	}

	public void setOldReorderQuantity(BigDecimal oldReorderQuantity) {
		this.oldReorderQuantity = oldReorderQuantity;
	}

	public BigDecimal getNewReorderQuantity() {
		return newReorderQuantity;
	}

	public void setNewReorderQuantity(BigDecimal newReorderQuantity) {
		this.newReorderQuantity = newReorderQuantity;
	}

	public BigDecimal getOldKanbanReorderQuantity() {
		return oldKanbanReorderQuantity;
	}

	public void setOldKanbanReorderQuantity(BigDecimal oldKanbanReorderQuantity) {
		this.oldKanbanReorderQuantity = oldKanbanReorderQuantity;
	}

	public BigDecimal getNewKanbanReorderQuantity() {
		return newKanbanReorderQuantity;
	}

	public void setNewKanbanReorderQuantity(BigDecimal newKanbanReorderQuantity) {
		this.newKanbanReorderQuantity = newKanbanReorderQuantity;
	}

	public Date getNewLevelHoldEndDate() {
		return newLevelHoldEndDate;
	}

	public void setNewLevelHoldEndDate(Date newLevelHoldEndDate) {
		this.newLevelHoldEndDate = newLevelHoldEndDate;
	}

	public Date getOldLevelHoldEndDate() {
		return oldLevelHoldEndDate;
	}

	public void setOldLevelHoldEndDate(Date oldLevelHoldEndDate) {
		this.oldLevelHoldEndDate = oldLevelHoldEndDate;
	}

    public String getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(String oldStatus) {
        this.oldStatus = oldStatus;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }

    public String getOldCountType() {
        return oldCountType;
    }

    public void setOldCountType(String oldCountType) {
        this.oldCountType = oldCountType;
    }

    public String getNewCountType() {
        return newCountType;
    }

    public void setNewCountType(String newCountType) {
        this.newCountType = newCountType;
    }

    public String getOldReceiptProcessingMethod() {
        return oldReceiptProcessingMethod;
    }

    public void setOldReceiptProcessingMethod(String oldReceiptProcessingMethod) {
        this.oldReceiptProcessingMethod = oldReceiptProcessingMethod;
    }

    public String getNewReceiptProcessingMethod() {
        return newReceiptProcessingMethod;
    }

    public void setNewReceiptProcessingMethod(String newReceiptProcessingMethod) {
        this.newReceiptProcessingMethod = newReceiptProcessingMethod;
    }

    public String getOldTierIITemperature() {
        return oldTierIITemperature;
    }

    public void setOldTierIITemperature(String oldTierIITemperature) {
        this.oldTierIITemperature = oldTierIITemperature;
    }

    public String getNewTierIITemperature() {
        return newTierIITemperature;
    }

    public void setNewTierIITemperature(String newTierIITemperature) {
        this.newTierIITemperature = newTierIITemperature;
    }

    public BigDecimal getOldTierIITemperatureCode() {
        return oldTierIITemperatureCode;
    }

    public void setOldTierIITemperatureCode(BigDecimal oldTierIITemperatureCode) {
        this.oldTierIITemperatureCode = oldTierIITemperatureCode;
    }

    public BigDecimal getNewTierIITemperatureCode() {
        return newTierIITemperatureCode;
    }

    public void setNewTierIITemperatureCode(BigDecimal newTierIITemperatureCode) {
        this.newTierIITemperatureCode = newTierIITemperatureCode;
    }

    public String getOldOwnershipName() {
        return oldOwnershipName;
    }

    public void setOldOwnershipName(String oldOwnershipName) {
        this.oldOwnershipName = oldOwnershipName;
    }

    public String getNewOwnershipName() {
        return newOwnershipName;
    }

    public void setNewOwnershipName(String newOwnershipName) {
        this.newOwnershipName = newOwnershipName;
    }

    public String getOldBinName() {
        return oldBinName;
    }

    public void setOldBinName(String oldBinName) {
        this.oldBinName = oldBinName;
    }

    public String getNewBinName() {
        return newBinName;
    }

    public void setNewBinName(String newBinName) {
        this.newBinName = newBinName;
    }

    public String getNewDropShipOverride() {
        return newDropShipOverride;
    }

    public void setNewDropShipOverride(String newDropShipOverride) {
        this.newDropShipOverride = newDropShipOverride;
    }

    public String getOldDropShipOverride() {
        return oldDropShipOverride;
    }

    public void setOldDropShipOverride(String oldDropShipOverride) {
        this.oldDropShipOverride = oldDropShipOverride;
    }
}