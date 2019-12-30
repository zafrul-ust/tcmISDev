package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;
import java.util.Collection;
import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: DistributedCountUsageViewBean <br>
 * @version: 1.0, Aug 25, 2006 <br>
 *****************************************************************************/


public class DistributedCountUsageViewItemBean extends BaseDataBean {

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
  private Collection workAreaCollection;
  private String ok;

	//constructor
	public DistributedCountUsageViewItemBean() {
	}

	//setters
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
	public void setWorkAreaCollection(Collection workAreaCollection) {
	 this.workAreaCollection = workAreaCollection;
	}
	public void setOk(String ok) {
	 this.ok = ok;
	}

	//getters
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
	public Collection getWorkAreaCollection() {
	 return workAreaCollection;
	}
	public String getOk() {
	 return ok;
	}
}