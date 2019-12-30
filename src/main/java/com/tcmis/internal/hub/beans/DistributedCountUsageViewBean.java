package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;
import java.util.Collection;
import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: DistributedCountUsageViewBean <br>
 * @version: 1.0, Aug 25, 2006 <br>
 *****************************************************************************/


public class DistributedCountUsageViewBean extends BaseDataBean {

  private String hub;
	private String inventoryGroup;
	private BigDecimal countId;
	private String companyId;
	private String catalogId;
	private String catPartNo;
	private String partDescription;
	private String itemId;
	private BigDecimal usage;
	private String uom;
	private String facilityId;
	private String application;
	private BigDecimal distributedUsage;
	private Date dateProcessed;
	private String countStatus;
	private String countType;
	private Collection itemCollection;
	private BigDecimal rowSpan;
  private String ok;
	private BigDecimal partGroupNo;
	private BigDecimal uomDistributedUsage;
	private String usageChanged;
	private String catalogCompanyId;


	//constructor
	public DistributedCountUsageViewBean() {
	}

	//setters
	public void setHub(String hub) {
		this.hub = hub;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setCountId(BigDecimal countId) {
		this.countId = countId;
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
	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public void setUsage(BigDecimal usage) {
		this.usage = usage;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public void setDistributedUsage(BigDecimal distributedUsage) {
		this.distributedUsage = distributedUsage;
	}
	public void setDateProcessed(Date dateProcessed) {
		this.dateProcessed = dateProcessed;
	}
	public void setCountStatus(String countStatus) {
		this.countStatus = countStatus;
	}
	public void setCountType(String countType) {
		this.countType = countType;
	}
	public void setItemCollection(Collection itemCollection) {
	 this.itemCollection = itemCollection;
	}
	public void setRowSpan(BigDecimal rowSpan) {
	 this.rowSpan = rowSpan;
	}
	public void setOk(String ok) {
	 this.ok = ok;
	}
	public void setPartGroupNo(BigDecimal partGroupNo) {
	 this.partGroupNo = partGroupNo;
	}
	public void setUomDistributedUsage(BigDecimal uomDistributedUsage) {
	 this.uomDistributedUsage = uomDistributedUsage;
	}
	public void setUsageChanged(String usageChanged) {
	 this.usageChanged = usageChanged;
	}

	//getters
	public String getHub() {
	 return hub;
	}
	public String getInventoryGroup() {
	 return inventoryGroup;
	}
	public BigDecimal getCountId() {
		return countId;
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
	public String getPartDescription() {
		return partDescription;
	}
	public String getItemId() {
		return itemId;
	}
	public BigDecimal getUsage() {
		return usage;
	}
	public String getUom() {
		return uom;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public String getApplication() {
		return application;
	}
	public BigDecimal getDistributedUsage() {
		return distributedUsage;
	}
	public Date getDateProcessed() {
		return dateProcessed;
	}
	public String getCountStatus() {
		return countStatus;
	}
	public String getCountType() {
		return countType;
	}
  public Collection getItemCollection() {
	return itemCollection;
  }
	public BigDecimal getRowSpan() {
	 return rowSpan;
	}
	public String getOk() {
	 return ok;
	}
	public BigDecimal getPartGroupNo() {
	 return partGroupNo;
	}
	public BigDecimal getUomDistributedUsage() {
	 return uomDistributedUsage;
	}
	public String getUsageChanged() {
	return usageChanged;
 }

	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}

	public void setCatalogCompanyId(String catalogCompanyId) {
		this.catalogCompanyId = catalogCompanyId;
	}
}