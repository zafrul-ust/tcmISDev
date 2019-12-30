package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: HubCabinetViewBean <br>
 * 
 * @version: 1.0, Jul 22, 2004 <br>
 *****************************************************************************/

@SuppressWarnings("serial")
public class CabinetBinInputBean extends BaseDataBean
{

	private String		action;
	private String		application;
	private BigDecimal	binId;
	private String		binName;
	private String		branchPlant;
	private BigDecimal	cabinetId;
	private String		catalogId;
	private String		companyId;
	private String		countType;
	private String		facilityId;
	private String		facPartNo;
	private BigDecimal	itemId;
	private BigDecimal	leadTimeDays;
	private BigDecimal	personnelId;
	private BigDecimal	reorderPoint;
	private BigDecimal	stockingLevel;
	private String		useApplication;
	private String catalogCompanyId;
	private BigDecimal partGroupNo;
	private String status;
	private BigDecimal reorderQuantity;
	private BigDecimal kanbanReorderQuantity;
	private BigDecimal applicationId;
    private String catPartNo;
    private String tierIIStorageTemperature;
	private BigDecimal avgAmount;
	private BigDecimal maxAmount;
    private String homeCompanyOwned;
    private String dropShipOverride;
    private String levelUnit;
    private Date startDate;

    // constructor
	public CabinetBinInputBean()
	{
	}

	public String getAction()
	{
		return this.action;
	}

	public String getApplication()
	{
		return application;
	}

	public BigDecimal getBinId()
	{
		return this.binId;
	}

	public String getBinName()
	{
		return this.binName;
	}

	// getters
	public String getBranchPlant()
	{
		return branchPlant;
	}

	public BigDecimal getCabinetId()
	{
		return this.cabinetId;
	}

	public String getCatalogId()
	{
		return this.catalogId;
	}

	public String getCompanyId()
	{
		return this.companyId;
	}

	/**
	 * Gets the count_type
	 * 
	 * @return the countType
	 */
	public String getCountType()
	{
		return countType;
	}

	public String getFacilityId()
	{
		return facilityId;
	}

	public String getFacPartNo()
	{
		return this.facPartNo;
	}

	public BigDecimal getItemId()
	{
		return this.itemId;
	}

	public BigDecimal getLeadTimeDays()
	{
		return this.leadTimeDays;
	}

	public BigDecimal getPersonnelId()
	{
		return this.personnelId;
	}

	public BigDecimal getReorderPoint()
	{
		return this.reorderPoint;
	}

	public BigDecimal getStockingLevel()
	{
		return this.stockingLevel;
	}

	public String getUseApplication()
	{
		return useApplication;
	}
	
	public String getCatPartNo() {
		return this.catPartNo;
	}
	public String getTierIIStorageTemperature() {
		return this.tierIIStorageTemperature;
	}
	
	public void setTierIIStorageTemperature(String tierIIStorageTemperature) {
		this.tierIIStorageTemperature = tierIIStorageTemperature;
	}
	
	public void setCatPartNo(String s) {
		this.catPartNo = s;
	}

	public void setAction(String s)
	{
		this.action = s;
	}

	public void setApplication(String application)
	{
		this.application = application;
	}

	public void setBinId(BigDecimal b)
	{
		this.binId = b;
	}

	public void setBinName(String s)
	{
		this.binName = s;
	}

	// setters
	public void setBranchPlant(String branchPlant)
	{
		this.branchPlant = branchPlant;
	}

	public void setCabinetId(BigDecimal b)
	{
		this.cabinetId = b;
	}

	public void setCatalogId(String s)
	{
		this.catalogId = s;
	}

	public void setCompanyId(String s)
	{
		this.companyId = s;
	}

	/**
	 * Sets the count_type
	 * 
	 * @param countType
	 *            the countType to set
	 */
	public void setCountType(String countType)
	{
		this.countType = countType;
	}

	public void setFacilityId(String facilityId)
	{
		this.facilityId = facilityId;
	}

	public void setFacPartNo(String s)
	{
		this.facPartNo = s;
	}

	public void setItemId(BigDecimal b)
	{
		this.itemId = b;
	}

	public void setLeadTimeDays(BigDecimal b)
	{
		this.leadTimeDays = b;
	}

	public void setPersonnelId(BigDecimal b)
	{
		this.personnelId = b;
	}

	public void setReorderPoint(BigDecimal b)
	{
		this.reorderPoint = b;
	}

	public void setStockingLevel(BigDecimal b)
	{
		this.stockingLevel = b;
	}

	public void setUseApplication(String useApplication)
	{
		this.useApplication = useApplication;
	}

	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}

	public void setCatalogCompanyId(String catalogCompanyId) {
		this.catalogCompanyId = catalogCompanyId;
	}

	public BigDecimal getPartGroupNo() {
		return partGroupNo;
	}

	public void setPartGroupNo(BigDecimal partGroupNo) {
		this.partGroupNo = partGroupNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getReorderQuantity() {
		return reorderQuantity;
	}

	public void setReorderQuantity(BigDecimal reorderQuantity) {
		this.reorderQuantity = reorderQuantity;
	}

	public BigDecimal getKanbanReorderQuantity() {
		return kanbanReorderQuantity;
	}

	public void setKanbanReorderQuantity(BigDecimal kanbanReorderQuantity) {
		this.kanbanReorderQuantity = kanbanReorderQuantity;
	}

	public BigDecimal getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(BigDecimal applicationId) {
		this.applicationId = applicationId;
	}
	public BigDecimal getAvgAmount() {
		return avgAmount;
	}

	public void setAvgAmount(BigDecimal avgAmount) {
		this.avgAmount = avgAmount;
	}

	public BigDecimal getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(BigDecimal maxAmount) {
		this.maxAmount = maxAmount;
	}

    public String getHomeCompanyOwned() {
        return homeCompanyOwned;
    }

    public void setHomeCompanyOwned(String homeCompanyOwned) {
        this.homeCompanyOwned = homeCompanyOwned;
    }

    public String getDropShipOverride() {
        return dropShipOverride;
    }

    public void setDropShipOverride(String dropShipOverride) {
        this.dropShipOverride = dropShipOverride;
    }

	public String getLevelUnit() {
		return levelUnit;
	}

	public void setLevelUnit(String levelUnit) {
		this.levelUnit = levelUnit;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
}