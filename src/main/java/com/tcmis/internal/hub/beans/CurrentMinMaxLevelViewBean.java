package com.tcmis.internal.hub.beans;

import java.util.Collection;
import java.util.Date;
import java.util.Vector;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: CurrentMinMaxLevelViewBean <br>
 * @version: 1.0, Oct 27, 2006 <br>
 *****************************************************************************/

public class CurrentMinMaxLevelViewBean
    extends BaseDataBean {

  private String hub;
  private String companyId;
  private String catalogId;
  private String catPartNo;
  private String inventoryGroup;
  private BigDecimal reorderPoint;
  private BigDecimal stockingLevel;
  private String establishedStockFlag;
  private String stockingMethod;
  private BigDecimal partGroupNo;
  private BigDecimal lookAheadDays;
  private BigDecimal itemId;
  private String itemDesc;
  private String currentStockingMethod;
  private String receiptProcessingMethod;
  private BigDecimal reorderQuantity;
  private String inventoryGroupName;
  private String billingMethod;
  private String partDescription;
  private String issueGeneration;
  private String specList;
  private String dropShipOverride;
  private String companyName;
  private Collection igReceiptProcessingViewBeanCollection = new Vector();
  private Collection igBillingMethodViewBeanCollection  = new Vector();
  private String catalogCompanyId;  
  private String catalogDesc; 
  private Date levelHoldEndDate;
  private BigDecimal projectedLeadTime;
    
  //constructor
  public CurrentMinMaxLevelViewBean() {
  }

  public String getCatalogCompanyId() {
      return catalogCompanyId;
  }

  public void setCatalogCompanyId(String catalogCompanyId) {
      this.catalogCompanyId = catalogCompanyId;
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

  public void setInventoryGroup(String inventoryGroup) {
    this.inventoryGroup = inventoryGroup;
  }

  public void setReorderPoint(BigDecimal reorderPoint) {
    this.reorderPoint = reorderPoint;
  }

  public void setStockingLevel(BigDecimal stockingLevel) {
    this.stockingLevel = stockingLevel;
  }

  public void setEstablishedStockFlag(String establishedStockFlag) {
    this.establishedStockFlag = establishedStockFlag;
  }

  public void setStockingMethod(String stockingMethod) {
    this.stockingMethod = stockingMethod;
  }

  public void setPartGroupNo(BigDecimal partGroupNo) {
    this.partGroupNo = partGroupNo;
  }

  public void setLookAheadDays(BigDecimal lookAheadDays) {
    this.lookAheadDays = lookAheadDays;
  }

  public void setItemId(BigDecimal itemId) {
    this.itemId = itemId;
  }

  public void setItemDesc(String itemDesc) {
    this.itemDesc = itemDesc;
  }

  public void setCurrentStockingMethod(String currentStockingMethod) {
    this.currentStockingMethod = currentStockingMethod;
  }

  public void setReceiptProcessingMethod(String receiptProcessingMethod) {
    this.receiptProcessingMethod = receiptProcessingMethod;
  }

  public void setReorderQuantity(BigDecimal reorderQuantity) {
    this.reorderQuantity = reorderQuantity;
  }

  public void setInventoryGroupName(String inventoryGroupName) {
    this.inventoryGroupName = inventoryGroupName;
  }

  public void setBillingMethod(String billingMethod) {
    this.billingMethod = billingMethod;
  }

  public void setPartDescription(String partDescription) {
    this.partDescription = partDescription;
  }

  public void setIssueGeneration(String s) {
    this.issueGeneration = s;
  }

  public void setIgReceiptProcessingViewBeanCollection(Collection c) {
    this.igReceiptProcessingViewBeanCollection = c;
  }

  public void setIgBillingMethodViewBeanCollection(Collection c) {
    this.igBillingMethodViewBeanCollection = c;
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

  public String getInventoryGroup() {
    return inventoryGroup;
  }

  public BigDecimal getReorderPoint() {
    return reorderPoint;
  }

  public BigDecimal getStockingLevel() {
    return stockingLevel;
  }

  public String getEstablishedStockFlag() {
    return establishedStockFlag;
  }

  public String getStockingMethod() {
    return stockingMethod;
  }

  public BigDecimal getPartGroupNo() {
    return partGroupNo;
  }

  public BigDecimal getLookAheadDays() {
    return lookAheadDays;
  }

  public BigDecimal getItemId() {
    return itemId;
  }

  public String getItemDesc() {
    return itemDesc;
  }

  public String getCurrentStockingMethod() {
    return currentStockingMethod;
  }

  public String getReceiptProcessingMethod() {
    return receiptProcessingMethod;
  }

  public BigDecimal getReorderQuantity() {
    return reorderQuantity;
  }

  public String getInventoryGroupName() {
    return inventoryGroupName;
  }

  public String getBillingMethod() {
    return billingMethod;
  }

  public String getPartDescription() {
    return partDescription;
  }

  public String getIssueGeneration() {
    return issueGeneration;
  }

  public Collection getIgReceiptProcessingViewBeanCollection() {
    return this.igReceiptProcessingViewBeanCollection;
  }

  public Collection getIgBillingMethodViewBeanCollection() {
    return this.igBillingMethodViewBeanCollection;
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

  public String getDropShipOverride() {
	  return dropShipOverride;
  }

  public void setDropShipOverride(String dropShipOverride) {
      this.dropShipOverride = dropShipOverride;
  }

public String getCatalogDesc() {
	return catalogDesc;
}

public void setCatalogDesc(String catalogDesc) {
	this.catalogDesc = catalogDesc;
}

public BigDecimal getProjectedLeadTime() {
	return projectedLeadTime;
}

public void setProjectedLeadTime(BigDecimal projectedLeadTime) {
	this.projectedLeadTime = projectedLeadTime;
}

public Date getLevelHoldEndDate() {
	return levelHoldEndDate;
}

public void setLevelHoldEndDate(Date levelHoldEndDate) {
	this.levelHoldEndDate = levelHoldEndDate;
}
  
}