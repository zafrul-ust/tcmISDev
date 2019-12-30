package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: MinMaxLevelLogViewBean <br>
 * @version: 1.0, Nov 3, 2006 <br>
 *****************************************************************************/

public class MinMaxLevelLogViewBean
    extends BaseDataBean {

  private String hub;
  private String companyId;
  private String catalogCompanyId;
  private String catalogId;
  private String catPartNo;
  private BigDecimal oldOrderPoint;
  private BigDecimal oldStockingLevel;
  private BigDecimal newOrderPoint;
  private BigDecimal newStockingLevel;
  private Date dateModified;
  private BigDecimal modifiedBy;
  private String oldStocked;
  private String newStocked;
  private String remarks;
  private BigDecimal oldLookAhead;
  private BigDecimal newLookAhead;
  private String inventoryGroup;
  private String inventoryGroupName;
  private BigDecimal partGroupNo;
  private BigDecimal itemId;
  private BigDecimal oldReorderQuantity;
  private BigDecimal newReorderQuantity;
  private String oldReceiptProcessingMethod;
  private String newReceiptProcessingMethod;
  private String oldBillingMethod;
  private String newBillingMethod;
  private String oldCountUom;
  private String newCountUom;
  private String oldAllowForceBy;
  private String newAllowForceBy;
  private String oldSpecCheckRequired;
  private String newSpecCheckRequired;
  private String oldQualityControl;
  private String newQualityControl;
  private String oldDropshipOverride;
  private String newDropshipOverride;
  private String specList;
  private String modifiedByName;
  private String issueGeneration;
  private String companyName;
  
  private String catalogDesc;
  private Date oldLevelHoldEndDate;
  private Date newLevelHoldEndDate; 
  private BigDecimal oldProjectedLeadTime;
  private BigDecimal newProjectedLeadTime; 

//constructor
  public MinMaxLevelLogViewBean() {
  }

  //setters
  public void setHub(String hub) {
    this.hub = hub;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public void setCatalogId(String catalogId) {
    this.catalogId = catalogId;
  }

  public void setCatPartNo(String catPartNo) {
    this.catPartNo = catPartNo;
  }

  public void setOldOrderPoint(BigDecimal oldOrderPoint) {
    this.oldOrderPoint = oldOrderPoint;
  }

  public void setOldStockingLevel(BigDecimal oldStockingLevel) {
    this.oldStockingLevel = oldStockingLevel;
  }

  public void setNewOrderPoint(BigDecimal newOrderPoint) {
    this.newOrderPoint = newOrderPoint;
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

  public void setOldStocked(String oldStocked) {
    this.oldStocked = oldStocked;
  }

  public void setNewStocked(String newStocked) {
    this.newStocked = newStocked;
  }

  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }

  public void setOldLookAhead(BigDecimal oldLookAhead) {
    this.oldLookAhead = oldLookAhead;
  }

  public void setNewLookAhead(BigDecimal newLookAhead) {
    this.newLookAhead = newLookAhead;
  }

  public void setInventoryGroup(String inventoryGroup) {
    this.inventoryGroup = inventoryGroup;
  }

  public void setInventoryGroupName(String inventoryGroupName) {
    this.inventoryGroupName = inventoryGroupName;
  }

  public void setPartGroupNo(BigDecimal partGroupNo) {
    this.partGroupNo = partGroupNo;
  }

  public void setItemId(BigDecimal itemId) {
    this.itemId = itemId;
  }

  public void setOldReorderQuantity(BigDecimal oldReorderQuantity) {
    this.oldReorderQuantity = oldReorderQuantity;
  }

  public void setNewReorderQuantity(BigDecimal newReorderQuantity) {
    this.newReorderQuantity = newReorderQuantity;
  }

  public void setOldReceiptProcessingMethod(String oldReceiptProcessingMethod) {
    this.oldReceiptProcessingMethod = oldReceiptProcessingMethod;
  }

  public void setNewReceiptProcessingMethod(String newReceiptProcessingMethod) {
    this.newReceiptProcessingMethod = newReceiptProcessingMethod;
  }

  public void setOldBillingMethod(String oldBillingMethod) {
    this.oldBillingMethod = oldBillingMethod;
  }

  public void setNewBillingMethod(String newBillingMethod) {
    this.newBillingMethod = newBillingMethod;
  }

  public void setModifiedByName(String modifiedByName) {
    this.modifiedByName = modifiedByName;
  }

  //getters
  public String getHub() {
    return hub;
  }

  public String getCompanyId() {
    return companyId;
  }

  public String getCatalogId() {
    return catalogId;
  }

  public String getCatPartNo() {
    return catPartNo;
  }

  public BigDecimal getOldOrderPoint() {
    return oldOrderPoint;
  }

  public BigDecimal getOldStockingLevel() {
    return oldStockingLevel;
  }

  public BigDecimal getNewOrderPoint() {
    return newOrderPoint;
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

  public String getOldStocked() {
    return oldStocked;
  }

  public String getNewStocked() {
    return newStocked;
  }

  public String getRemarks() {
    return remarks;
  }

  public BigDecimal getOldLookAhead() {
    return oldLookAhead;
  }

  public BigDecimal getNewLookAhead() {
    return newLookAhead;
  }

  public String getInventoryGroup() {
    return inventoryGroup;
  }

  public String getInventoryGroupName() {
    return inventoryGroupName;
  }

  public BigDecimal getPartGroupNo() {
    return partGroupNo;
  }

  public BigDecimal getItemId() {
    return itemId;
  }

  public BigDecimal getOldReorderQuantity() {
    return oldReorderQuantity;
  }

  public BigDecimal getNewReorderQuantity() {
    return newReorderQuantity;
  }

  public String getOldReceiptProcessingMethod() {
    return oldReceiptProcessingMethod;
  }

  public String getNewReceiptProcessingMethod() {
    return newReceiptProcessingMethod;
  }

  public String getOldBillingMethod() {
    return oldBillingMethod;
  }

  public String getNewBillingMethod() {
    return newBillingMethod;
  }

  public String getModifiedByName() {
    return modifiedByName;
  }

  public String getSpecList() {
	  return specList;
  }

  public void setSpecList(String specList) {
	  this.specList = specList;
  }

  public String getCompanyName() {
  	return companyName;
  }

  public void setCompanyName(String companyName) {
  	this.companyName = companyName;
  }

public String getCatalogDesc() {
	return catalogDesc;
}

public void setCatalogDesc(String catalogDesc) {
	this.catalogDesc = catalogDesc;
}

public String getOldDropshipOverride() {
	return oldDropshipOverride;
}

public void setOldDropshipOverride(String oldDropshipOverride) {
	this.oldDropshipOverride = oldDropshipOverride;
}

public String getNewDropshipOverride() {
	return newDropshipOverride;
}

public void setNewDropshipOverride(String newDropshipOverride) {
	this.newDropshipOverride = newDropshipOverride;
}

public String getCatalogCompanyId() {
	return catalogCompanyId;
}

public void setCatalogCompanyId(String catalogCompanyId) {
	this.catalogCompanyId = catalogCompanyId;
}

public String getOldCountUom() {
	return oldCountUom;
}

public void setOldCountUom(String oldCountUom) {
	this.oldCountUom = oldCountUom;
}

public String getNewCountUom() {
	return newCountUom;
}

public void setNewCountUom(String newCountUom) {
	this.newCountUom = newCountUom;
}

public String getOldAllowForceBy() {
	return oldAllowForceBy;
}

public void setOldAllowForceBy(String oldAllowForceBy) {
	this.oldAllowForceBy = oldAllowForceBy;
}

public String getNewAllowForceBy() {
	return newAllowForceBy;
}

public void setNewAllowForceBy(String newAllowForceBy) {
	this.newAllowForceBy = newAllowForceBy;
}

public String getOldSpecCheckRequired() {
	return oldSpecCheckRequired;
}

public void setOldSpecCheckRequired(String oldSpecCheckRequired) {
	this.oldSpecCheckRequired = oldSpecCheckRequired;
}

public String getNewSpecCheckRequired() {
	return newSpecCheckRequired;
}

public void setNewSpecCheckRequired(String newSpecCheckRequired) {
	this.newSpecCheckRequired = newSpecCheckRequired;
}

public String getOldQualityControl() {
	return oldQualityControl;
}

public void setOldQualityControl(String oldQualityControl) {
	this.oldQualityControl = oldQualityControl;
}

public String getNewQualityControl() {
	return newQualityControl;
}

public void setNewQualityControl(String newQualityControl) {
	this.newQualityControl = newQualityControl;
}

public String getIssueGeneration() {
	return issueGeneration;
}

public void setIssueGeneration(String issueGeneration) {
	this.issueGeneration = issueGeneration;
}

public BigDecimal getOldProjectedLeadTime() {
	return oldProjectedLeadTime;
}

public void setOldProjectedLeadTime(BigDecimal oldProjectedLeadTime) {
	this.oldProjectedLeadTime = oldProjectedLeadTime;
}

public BigDecimal getNewProjectedLeadTime() {
	return newProjectedLeadTime;
}

public void setNewProjectedLeadTime(BigDecimal newProjectedLeadTime) {
	this.newProjectedLeadTime = newProjectedLeadTime;
}

public Date getOldLevelHoldEndDate() {
	return oldLevelHoldEndDate;
}

public void setOldLevelHoldEndDate(Date oldLevelHoldEndDate) {
	this.oldLevelHoldEndDate = oldLevelHoldEndDate;
}

public Date getNewLevelHoldEndDate() {
	return newLevelHoldEndDate;
}

public void setNewLevelHoldEndDate(Date newLevelHoldEndDate) {
	this.newLevelHoldEndDate = newLevelHoldEndDate;
}




}