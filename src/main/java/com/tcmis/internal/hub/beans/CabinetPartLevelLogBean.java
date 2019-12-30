package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: CabinetPartLevelLogBean <br>
 * @version: 1.0, Oct 16, 2006 <br>
 *****************************************************************************/

public class CabinetPartLevelLogBean
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
  private String catalogCompanyId;
  private BigDecimal oldReorderQuantity;
  private BigDecimal newReorderQuantity;
  private BigDecimal oldKanbanReorderQuantity;
  private BigDecimal newKanbanReorderQuantity;
  private Date oldLevelHoldEndDate;
  private Date newLevelHoldEndDate;

  //constructor
  public CabinetPartLevelLogBean() {
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

  public void setCatalogCompanyId(String catalogCompanyId) {
	  this.catalogCompanyId = catalogCompanyId;
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

  public String getCatalogCompanyId() {
	  return catalogCompanyId;
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