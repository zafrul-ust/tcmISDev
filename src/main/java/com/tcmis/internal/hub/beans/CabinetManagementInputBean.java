package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: HubCabinetViewBean <br>
 * 
 * @version: 1.0, Jul 22, 2004 <br>
 *****************************************************************************/

@SuppressWarnings("serial")
public class CabinetManagementInputBean extends BaseDataBean
{

	private String		action;
	private String		application;
	private BigDecimal	binId;
	private String		branchPlant;
	private String[]	cabinetIdArray;
	private String[]	countTypeArray;
	private String		countType;
	private String		criteria;
	private String		criterion;
	private String		facilityId;
	private String		facilityName;
	private String		hubName;
	private String		inactive;
	private String		itemOrPart;
	private String		oldCountType;
	private String		oldStatus;
	private BigDecimal cabinetId;
	private String		sortBy;
	private String		status;
	private String		submitSearch;
	private String		useApplication;
	private String 	sourcePage;
	private String 	binName;
	private String companyId;
	
	private String showDefault;

	public String getShowDefault() {
		return showDefault;
	}

	public void setShowDefault(String showDefault) {
		this.showDefault = showDefault;
	}

	// constructor
	public CabinetManagementInputBean()
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

	public void setCabinetId(BigDecimal cabinetId) {
	 this.cabinetId = cabinetId;
	}

	// getters
	public String getBranchPlant()
	{
		return branchPlant;
	}

	public String[] getCabinetIdArray()
	{
		return this.cabinetIdArray;
	}

	/**
	 * Returns the count_type
	 * 
	 * @return the countType
	 */
	public String getCountType()
	{
		return countType;
	}

	public String getCriteria()
	{
		return criteria;
	}

	public String getCriterion()
	{
		return criterion;
	}

	public String getFacilityId()
	{
		return facilityId;
	}

	public String getFacilityName()
	{
		return this.facilityName;
	}

	public String getHubName()
	{
		return this.hubName;
	}

	public String getInactive()
	{
		return this.inactive;
	}

	public String getItemOrPart()
	{
		return this.itemOrPart;
	}

	/**
	 * Returns the original count type
	 * 
	 * @return the oldCountType
	 */
	public String getOldCountType()
	{
		return oldCountType;
	}

	public String getOldStatus()
	{
		return this.oldStatus;
	}

	public String getSortBy()
	{
		return sortBy;
	}

	public String getStatus()
	{
		return this.status;
	}

	public String getSubmitSearch()
	{
		return this.submitSearch;
	}

	/**
	 * Getter for property useApplication.
	 * 
	 * @return Value of property useApplication.
	 */
	public java.lang.String getUseApplication()
	{
		return useApplication;
	}

	public BigDecimal getCabinetId() {
	 return cabinetId;
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

	// setters
	public void setBranchPlant(String branchPlant)
	{
		this.branchPlant = branchPlant;
	}

	public void setCabinetIdArray(String[] b)
	{
		this.cabinetIdArray = b;
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

	public void setCriteria(String s)
	{
		this.criteria = s;
	}

	public void setCriterion(String s)
	{
		this.criterion = s;
	}

	public void setFacilityId(String facilityId)
	{
		this.facilityId = facilityId;
	}

	public void setFacilityName(String facilityName)
	{
		this.facilityName = facilityName;
	}

	public void setHubName(String hubName)
	{
		this.hubName = hubName;
	}

	public void setInactive(String s)
	{
		this.inactive = s;
	}

	public void setItemOrPart(String s)
	{
		this.itemOrPart = s;
	}

	/**
	 * Sets the original count type. Used to compare with countType
	 * 
	 * @param oldCountType
	 *            the oldCountType to set
	 */
	public void setOldCountType(String oldCountType)
	{
		this.oldCountType = oldCountType;
	}

	public void setOldStatus(String s)
	{
		this.oldStatus = s;
	}

	public void setSortBy(String sortBy)
	{
		this.sortBy = sortBy;
	}

	public void setStatus(String s)
	{
		this.status = s;
	}

	public void setSubmitSearch(String s)
	{
		this.submitSearch = s;
	}

	/**
	 * Setter for property useApplication.
	 * 
	 * @param useApplication
	 *            New value of property useApplication.
	 */
	public void setUseApplication(java.lang.String useApplication)
	{
		this.useApplication = useApplication;
	}

	public String getSourcePage() {
		return sourcePage;
	}

	public void setSourcePage(String sourcePage) {
		this.sourcePage = sourcePage;
	}

	public String getBinName() {
		return binName;
	}

	public void setBinName(String binName) {
		this.binName = binName;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String[] getCountTypeArray() {
		return countTypeArray;
	}
	public void setCountTypeArray(String[] countTypeArray) {
		this.countTypeArray = countTypeArray;
	}

}