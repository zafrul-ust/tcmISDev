package com.tcmis.client.common.beans;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import org.apache.struts.action.ActionForm;

import com.tcmis.client.catalog.beans.FacLocAppBean;
import com.tcmis.common.admin.beans.InventoryGroupDefinitionBean;
import com.tcmis.common.framework.BaseInputBean;
import com.tcmis.common.util.StringHandler;

public class WorkAreaSearchTemplateInputBean  extends BaseInputBean {

		private String		action;
		private String		application;
		private BigDecimal	binId;
		private String		branchPlant;
		private String	applicationId;
		private String	countTypeArray;
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
		private String		reportingEntityId;
		private String 		sourcePage;
		private String 		binName;
		private String 		companyId;
		private String 		showDefault;
		private String 		areaId;
		private String 		buildingId;
		private String 		deptId;
		private Collection<FacilityDockBean> facilityDocks;
		private Collection<InventoryGroupDefinitionBean> facilityInventoryGroups;
		private FacLocAppBean defaultValues;
		private String showInactive;
		private String searchArgument;
		private Date dateStart;
		private Date dateEnd;
		private String putAwayMethod;
        private String inventoryGroup;

        private BigDecimal flammabilityControlZoneId;
        private BigDecimal vocZoneId;
        
        public String getShowDefault() {
			return showDefault;
		}

		public void setShowDefault(String showDefault) {
			this.showDefault = showDefault;
		}

		// constructor
		public WorkAreaSearchTemplateInputBean()
		{
		}
		
		public WorkAreaSearchTemplateInputBean(ActionForm inputForm, Locale locale) {
			super(inputForm, locale);
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

		public String getApplicationId()
		{
			return this.applicationId;
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
		 * Getter for property reportingEntityId.
		 * 
		 * @return Value of property reportingEntityId.
		 */
		public java.lang.String getReportingEntityId()
		{
			return reportingEntityId;
		}

		public BigDecimal getCabinetId() {
		 return cabinetId;
		}
		
		public String getDeptId() {
			return deptId;
		}
		
		public String getAreaId() {
			return areaId;
		}
		public String getBuildingId() {
			return buildingId;
		}
		public void setBuildingId(String buildingId) {
			this.buildingId = buildingId;
		}
		
		public void setAreaId(String areaId) {
			this.areaId = areaId;
		}
		
		public void setDeptId(String deptId) {
			this.deptId = deptId;
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

		public void setApplicationId(String b)
		{
			this.applicationId = b;
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
		 * Setter for property reportingEntityId.
		 * 
		 * @param reportingEntityId
		 *            New value of property reportingEntityId.
		 */
		public void setReportingEntityId(java.lang.String reportingEntityId)
		{
			this.reportingEntityId = reportingEntityId;
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
		public String getCountTypeArray() {
			return countTypeArray;
		}
		public void setCountTypeArray(String countTypeArray) {
			this.countTypeArray = countTypeArray;
		}	

		public Collection<FacilityDockBean> getFacilityDocks() {
			return facilityDocks;
		}

		public Collection<InventoryGroupDefinitionBean> getFacilityInventoryGroups() {
			return facilityInventoryGroups;
		}

		public FacLocAppBean getDefaultValues() {
			return defaultValues;
		}
		
		public boolean isHistorySearch (){
			return "history".equalsIgnoreCase(getuAction());
		}

		public boolean isPopDept (){
			return "popDept".equalsIgnoreCase(getuAction());
		}
		
		public boolean isPrint (){
			return "print".equalsIgnoreCase(getuAction());
		}

		public boolean hasFacility() {
			return !StringHandler.isBlankString(this.facilityId);
		}

		public boolean hasAreaId() {
			return !StringHandler.isBlankString(this.areaId);
		}

		public boolean hasBuildingId() {
			return !StringHandler.isBlankString(this.buildingId);
		}

		public boolean hasDeptId() {
			return !StringHandler.isBlankString(this.deptId);
		}
		
		public boolean hasApplicationId() {
			return !StringHandler.isBlankString(this.applicationId);
		}
		
		public boolean hasReportingEntityId() {
			return !StringHandler.isBlankString(this.reportingEntityId);
		}

		public void setFacilityDocks(Collection<FacilityDockBean> facilityDocks) {
			this.facilityDocks = facilityDocks;
		}


		public void setFacilityInventoryGroups(Collection<InventoryGroupDefinitionBean> facilityInventoryGroups) {
			this.facilityInventoryGroups = facilityInventoryGroups;
		}


		@Override
		public void setHiddenFormFields() {
			addHiddenFormField("companyId");
			addHiddenFormField("reportingEntityId");
			addHiddenFormField("facilityId");
			addHiddenFormField("areaId");
			addHiddenFormField("buildingId");
			addHiddenFormField("deptId");
			addHiddenFormField("inactive");
			addHiddenFormField("showInactive");
			addHiddenFormField("applicationId");
            addHiddenFormField("inventoryGroup");
            addHiddenFormField("flammabilityControlZoneId");
            addHiddenFormField("vocZoneId");
        }

		public void setDefaultValues(FacLocAppBean workArea) {
			this.defaultValues = workArea;
		}

		public String getShowInactive() {
			return showInactive;
		}

		public void setShowInactive(String showInactive) {
			this.showInactive = showInactive;
		}

		public String getSearchArgument() {
			return searchArgument;
		}

		public void setSearchArgument(String searchArgument) {
			this.searchArgument = searchArgument;
		}

		public Date getDateStart() {
			return dateStart;
		}

		public Date getDateEnd() {
			return dateEnd;
		}

		public void setDateStart(Date dateStart) {
			this.dateStart = dateStart;
		}

		public void setDateEnd(Date dateEnd) {
			this.dateEnd = dateEnd;
		}

		public String getPutAwayMethod() {
			return putAwayMethod;
		}

		public void setPutAwayMethod(String putAwayMethod) {
			this.putAwayMethod = putAwayMethod;
		}

    public String getInventoryGroup() {
        return inventoryGroup;
    }

    public void setInventoryGroup(String inventoryGroup) {
        this.inventoryGroup = inventoryGroup;
    }

	public BigDecimal getFlammabilityControlZoneId() {
		return flammabilityControlZoneId;
	}

	public void setFlammabilityControlZoneId(BigDecimal flammabilityControlZoneId) {
		this.flammabilityControlZoneId = flammabilityControlZoneId;
	}

	public BigDecimal getVocZoneId() {
		return vocZoneId;
	}

	public void setVocZoneId(BigDecimal vocZoneId) {
		this.vocZoneId = vocZoneId;
	}
}
