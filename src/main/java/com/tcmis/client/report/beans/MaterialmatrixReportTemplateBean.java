package com.tcmis.client.report.beans;

import java.util.Date;
import java.util.Collection;
import java.util.Vector;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: MaterialmatrixReportTemplateBean <br>
 * @version: 1.0, Mar 28, 2008 <br>
 *****************************************************************************/


public class MaterialmatrixReportTemplateBean extends BaseDataBean {

	private BigDecimal reportTemplateId;
	private String companyId;
	private BigDecimal personnelId;
	private String templateName;
	private String reportingEntity;
	private String partNumber;
	private String facilityId;
	private Date beginDate;
	private String application;
	private String reportCriteria;
	private String listFormat;
	private Date inventoryDate;
	private String partNumberCriteria;
	private String reportGenerationType;
	private String reportName;
	private Date endDate;

  private Collection reportFieldCollection = new Vector();
  private Collection chemicalFieldCollection = new Vector();

  private String[] reportFieldList;
  private String[] chemicalFieldList;

  private String[] foo;
  private String[] bar;

	private String facilityName;
	private String applicationDesc;
	private String reportingEntityId;
  private String templateId;
  private String userGroupId;
  private String userGroupFacilityId;
  private String owner;
  private String status;
  private String templateDescription;
  private String urlPageArg;
  
  private String phCompare;
	private String ph;
	private String flashPointCompare;
	private String flashPoint;
	private String temperatureUnit;
  private String vocSearchSelect;
	private String vocPercentCompare;
	private String vocPercent;
  private String vocLwesSearchSelect;
  private String vocLwesPercentCompare;
  private String vocLwesPercent;
	private String solidsPercentCompare;
	private String solidsPercent;
	private String health;
	private String healthCompare;
	private String flammability;
	private String flammabilityCompare;
	private String reactivity;
	private String reactivityCompare;
	private String specificHazard;
	private String hmisHealth;
	private String hmisHealthCompare;
	private String hmisFlammability;
	private String hmisFlammabilityCompare;
	private String hmisReactivity;
	private String hmisReactivityCompare;
	private String personalProtection;
	private String physicalState;
	private String vaporPressureCompare;
	private String vaporPressureUnit;
	private String vaporPressure;
	private String gridType;
	private String listName;
	private String listId;
	private String constraint;
	private String operator;
	private String value;
	private String casNum;
	private String chemicalName;
	private String gridSubmit;
	private String storageAreaId;
    private Date beginDateJsp;
    private Date endDateJsp;
    private String facilityGroupId;
    private String areaId;
    private String buildingId;
    private String floorId;
    private String roomId;
	private String deptId;
	private String searchField;
	private String searchText;
	private String matchType;
	private BigDecimal mfgId;
	private String matchTypeDesc;
	private String specificHazardDesc;
	private String personalProtectionDesc;
	private String gridDesc;
    private String areaName;
    private String buildingName;
    private String floorName;
    private String roomName;
    private String facilityGroupName; 
	private String searchFieldDesc;
	private String manufacturer;
	private String reportingEntityName;
	private String deptName;
	private String storageAreaDesc;
	
	private String materialSubcategoryId;
    private String materialSubcategoryName;
	private String materialCategoryId;
    private String materialCategoryName;
    private String catalogId;
    private String catalogCompanyId;
    private boolean isMatCatFX;
    private String header;
    private String footer;

    private String baseFieldId;
    private String baseFieldName;
    private String baseDescription;
    private String chemicalFieldListId;
    private String globalizationLabel;
	private String globalizationLabelLetter;
    
    private String createdByName;
    private String companyName;
    private String userGroupDesc;
    
    private String flammable;
    
    //constructor
	public MaterialmatrixReportTemplateBean() {
	}

	//setters
	public void setReportTemplateId(BigDecimal reportTemplateId) {
		this.reportTemplateId = reportTemplateId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public void setReportingEntity(String reportingEntity) {
		this.reportingEntity = reportingEntity;
	}
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public void setReportCriteria(String reportCriteria) {
		this.reportCriteria = reportCriteria;
	}
	public void setListFormat(String listFormat) {
		this.listFormat = listFormat;
	}
	public void setInventoryDate(Date inventoryDate) {
		this.inventoryDate = inventoryDate;
	}
	public void setPartNumberCriteria(String partNumberCriteria) {
		this.partNumberCriteria = partNumberCriteria;
	}
	public void setReportGenerationType(String reportGenerationType) {
		this.reportGenerationType = reportGenerationType;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

  public void setReportFieldCollection(Collection c) {
    this.reportFieldCollection = c;
  }

  public void setChemicalFieldCollection(Collection c) {
    this.chemicalFieldCollection = c;
  }

  public void setReportFieldList(String[] reportFieldList) {
    this.reportFieldList = reportFieldList;
  }

  public void setChemicalFieldList(String[] chemicalFieldList) {
    this.chemicalFieldList = chemicalFieldList;
  }

  public void setFoo(String[] s) {
    this.foo = s;
  }

  public void setBar(String[] s) {
    this.bar = s;
  }

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public void setApplicationDesc(String applicationDesc) {
		this.applicationDesc = applicationDesc;
	}
	public void setFlammable(String flammable) {
		this.flammable = flammable;
	}

	//getters
	public String getFlammable() {
		return flammable;
	}
	public BigDecimal getReportTemplateId() {
		return reportTemplateId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public BigDecimal getPersonnelId() {
		return personnelId;
	}
	public String getTemplateName() {
		return templateName;
	}
	public String getReportingEntity() {
		return reportingEntity;
	}
	public String getPartNumber() {
		return partNumber;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public String getApplication() {
		return application;
	}
	public String getReportCriteria() {
		return reportCriteria;
	}
	public String getListFormat() {
		return listFormat;
	}
	public Date getInventoryDate() {
		return inventoryDate;
	}
	public String getPartNumberCriteria() {
		return partNumberCriteria;
	}
	public String getReportGenerationType() {
		return reportGenerationType;
	}
	public String getReportName() {
		return reportName;
	}
	public Date getEndDate() {
		return endDate;
	}
 public Collection getReportFieldCollection() {
    return this.reportFieldCollection;
  }

  public Collection getChemicalFieldCollection() {
    return this.chemicalFieldCollection;
  }

  public String[] getReportFieldList() {
    return reportFieldList;
  }

  public String[] getChemicalFieldList() {
    return chemicalFieldList;
  }

  public String[] getFoo() {
    return foo;
  }

  public String[] getBar() {
    return bar;
  }

	public String getFacilityName() {
		return facilityName;
	}

	public String getApplicationDesc() {
		return applicationDesc;
	}

	public String getReportingEntityId() {
		return reportingEntityId;
	}

	public void setReportingEntityId(String reportingEntityId) {
		this.reportingEntityId = reportingEntityId;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getUserGroupId() {
		return userGroupId;
	}

	public void setUserGroupId(String userGroupId) {
		this.userGroupId = userGroupId;
	}

	public String getUserGroupFacilityId() {
		return userGroupFacilityId;
	}

	public void setUserGroupFacilityId(String userGroupFacilityId) {
		this.userGroupFacilityId = userGroupFacilityId;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTemplateDescription() {
		return templateDescription;
	}

	public void setTemplateDescription(String templateDescription) {
		this.templateDescription = templateDescription;
	}

	public String getUrlPageArg() {
		return urlPageArg;
	}

	public void setUrlPageArg(String urlPageArg) {
		this.urlPageArg = urlPageArg;
	}
	
	 public String getVocLwesPercentCompare() {
			return vocLwesPercentCompare;
		}

		public void setVocLwesPercentCompare(String vocLwesPercentCompare) {
			this.vocLwesPercentCompare = vocLwesPercentCompare;
		}
		
	    public String getVocLwesPercent() {
			return vocLwesPercent;
		}

		public void setVocLwesPercent(String vocLwesPercent) {
			this.vocLwesPercent = vocLwesPercent;
		}
	    
	    public String getVocLwesSearchSelect() {
			return vocLwesSearchSelect;
		}

		public void setVocLwesSearchSelect(String vocLwesSearchSelect) {
			this.vocLwesSearchSelect = vocLwesSearchSelect;
		}
	    
	    public String getVocSearchSelect() {
			return vocSearchSelect;
		}

		public void setVocSearchSelect(String vocSearchSelect) {
			this.vocSearchSelect = vocSearchSelect;
		}   

		public String getFlammability() {
			return flammability;
		}

		public void setFlammability(String flammability) {
			this.flammability = flammability;
		}

		public String getFlammabilityCompare() {
			return flammabilityCompare;
		}

		public void setFlammabilityCompare(String flammabilityCompare) {
			this.flammabilityCompare = flammabilityCompare;
		}

		public String getFlashPoint() {
			return flashPoint;
		}

		public void setFlashPoint(String flashPoint) {
			this.flashPoint = flashPoint;
		}

		public String getFlashPointCompare() {
			return flashPointCompare;
		}

		public void setFlashPointCompare(String flashPointCompare) {
			this.flashPointCompare = flashPointCompare;
		}

		public String getHealth() {
			return health;
		}

		public void setHealth(String health) {
			this.health = health;
		}

		public String getHealthCompare() {
			return healthCompare;
		}

		public void setHealthCompare(String healthCompare) {
			this.healthCompare = healthCompare;
		}

		public String getHmisFlammability() {
			return hmisFlammability;
		}

		public void setHmisFlammability(String hmisFlammability) {
			this.hmisFlammability = hmisFlammability;
		}

		public String getHmisFlammabilityCompare() {
			return hmisFlammabilityCompare;
		}

		public void setHmisFlammabilityCompare(String hmisFlammabilityCompare) {
			this.hmisFlammabilityCompare = hmisFlammabilityCompare;
		}

		public String getHmisHealth() {
			return hmisHealth;
		}

		public void setHmisHealth(String hmisHealth) {
			this.hmisHealth = hmisHealth;
		}

		public String getHmisHealthCompare() {
			return hmisHealthCompare;
		}

		public void setHmisHealthCompare(String hmisHealthCompare) {
			this.hmisHealthCompare = hmisHealthCompare;
		}

		public String getHmisReactivity() {
			return hmisReactivity;
		}

		public void setHmisReactivity(String hmisReactivity) {
			this.hmisReactivity = hmisReactivity;
		}

		public String getHmisReactivityCompare() {
			return hmisReactivityCompare;
		}

		public void setHmisReactivityCompare(String hmisReactivityCompare) {
			this.hmisReactivityCompare = hmisReactivityCompare;
		}

		public String getPersonalProtection() {
			return personalProtection;
		}

		public void setPersonalProtection(String personalProtection) {
			this.personalProtection = personalProtection;
		}

		public String getPh() {
			return ph;
		}

		public void setPh(String ph) {
			this.ph = ph;
		}

		public String getPhCompare() {
			return phCompare;
		}

		public void setPhCompare(String phCompare) {
			this.phCompare = phCompare;
		}

		public String getPhysicalState() {
			return physicalState;
		}

		public void setPhysicalState(String physicalState) {
			this.physicalState = physicalState;
		}

		public String getReactivity() {
			return reactivity;
		}

		public void setReactivity(String reactivity) {
			this.reactivity = reactivity;
		}

		public String getReactivityCompare() {
			return reactivityCompare;
		}

		public void setReactivityCompare(String reactivityCompare) {
			this.reactivityCompare = reactivityCompare;
		}

		public String getSolidsPercent() {
			return solidsPercent;
		}

		public void setSolidsPercent(String solidsPercent) {
			this.solidsPercent = solidsPercent;
		}

		public String getSolidsPercentCompare() {
			return solidsPercentCompare;
		}

		public void setSolidsPercentCompare(String solidsPercentCompare) {
			this.solidsPercentCompare = solidsPercentCompare;
		}

		public String getSpecificHazard() {
			return specificHazard;
		}

		public void setSpecificHazard(String specificHazard) {
			this.specificHazard = specificHazard;
		}

		public String getTemperatureUnit() {
			return temperatureUnit;
		}

		public void setTemperatureUnit(String temperatureUnit) {
			this.temperatureUnit = temperatureUnit;
		}

		public String getVocPercent() {
			return vocPercent;
		}

		public void setVocPercent(String vocPercent) {
			this.vocPercent = vocPercent;
		}

		public String getVocPercentCompare() {
			return vocPercentCompare;
		}

		public void setVocPercentCompare(String vocPercentCompare) {
			this.vocPercentCompare = vocPercentCompare;
		}
		
		public String getChemicalName() {
			return chemicalName;
		}
		
		public void setChemicalName(String chemicalName) {
			this.chemicalName = chemicalName;
		}
		
		public String getCasNum() {
			return casNum;
		}	
		
		public void setCasNum(String casNum) {
			this.casNum = casNum;
		}	
		
		public String getConstraint() {
			return constraint;
		}
		
		public void setConstraint(String constraint) {
			this.constraint = constraint;
		}
		
		
		public String getOperator() {
			return operator;
		}
		
		public void setOperator(String operator) {
			this.operator = operator;
		}
		
		public void getValue(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}
		
		public void setListName(String listName) {
			this.listName = listName;
		}
		
		public String getListName() {
			return listName;
		}
		
		public String getListId() {
			return listId;
		}
		
		public void setListId(String listId) {
			this.listId = listId;
		}
		
		public String getGridType() {
			return gridType;
		}
		
		public void setGridType(String gridType) {
			this.gridType = gridType;
		}
		
		public String getGridSubmit() {
			return gridSubmit;
		}
		
		public void setGridSubmit(String gridSubmit) {
			this.gridSubmit = gridSubmit;
		}
		
		public String getVaporPressure() {
			return vaporPressure;
		}

		public void setVaporPressure(String vaporPressure) {
			this.vaporPressure = vaporPressure;
		}

		public String getVaporPressureCompare() {
			return vaporPressureCompare;
		}

		public void setVaporPressureCompare(String vaporPressureCompare) {
			this.vaporPressureCompare = vaporPressureCompare;
		}

		public String getVaporPressureUnit() {
			return vaporPressureUnit;
		}

		public void setVaporPressureUnit(String vaporPressureUnit) {
			this.vaporPressureUnit = vaporPressureUnit;
		}
		
		public void setStorageAreaId(String storageAreaId) {
			this.storageAreaId = storageAreaId;
		}

		public String getStorageAreaId() {
			return storageAreaId;
		}
		public void setBeginDateJsp(Date beginDateJsp) {
			this.beginDateJsp = beginDateJsp;
		}
		public void setEndDateJsp(Date endDateJsp) {
			this.endDateJsp = endDateJsp;
		}
		public Date getBeginDateJsp() {
			return beginDateJsp;
		}
		public Date getEndDateJsp() {
			return endDateJsp;
		}

	    public String getAreaId() {
	        return areaId;
	    }

	    public void setAreaId(String areaId) {
	        this.areaId = areaId;
	    }

	    public String getBuildingId() {
	        return buildingId;
	    }

	    public void setBuildingId(String buildingId) {
	        this.buildingId = buildingId;
	    }

	    public String getRoomId() {
	        return roomId;
	    }

	    public void setRoomId(String roomId) {
	        this.roomId = roomId;
	    }
		public String getFloorId() {
			return floorId;
		}

		public void setFloorId(String floorId) {
			this.floorId = floorId;
		}
		public void setDeptId(String deptId) {
			this.deptId = deptId;
		}
		public String getDeptId() {
			return deptId;
		}
		public void setSearchField(String searchField) {
			this.searchField = searchField;
		}
		public String getSearchField() {
			return searchField;
		}
		public void setSearchText(String searchText) {
			this.searchText = searchText;
		}
		public String getSearchText() {
			return searchText;
		}
		public void setMatchType(String matchType) {
			this.matchType = matchType;
		}
		public String getMatchType() {
			return matchType;
		}
		public void setMfgId(BigDecimal mfgId) {
			this.mfgId = mfgId;
		}
		public BigDecimal getMfgId() {
			return mfgId;
		}

		public String getFacilityGroupId() {
			return facilityGroupId;
		}

		public void setFacilityGroupId(String facilityGroupId) {
			this.facilityGroupId = facilityGroupId;
		}
		public String getGridDesc() {
			return gridDesc;
		}
		
		public void setGridDesc(String gridDesc) {
			this.gridDesc = gridDesc;
		}
		
		public String getMatchTypeDesc()
		{
			return matchTypeDesc;
		}
		
		public void setMatchTypeDesc(String matchTypeDesc)
		{
			this.matchTypeDesc = matchTypeDesc;
		}
		
		public String getSpecificHazardDesc()
		{
			return specificHazardDesc;
		}
		
		public void setSpecificHazardDesc(String specificHazardDesc)
		{
			this.specificHazardDesc = specificHazardDesc;
		}
		
		public String getPersonalProtectionDesc()
		{
			return personalProtectionDesc;
		}
		
		public void setPersonalProtectionDesc(String personalProtectionDesc)
		{
			this.personalProtectionDesc = personalProtectionDesc;
		}

		public String getFacilityGroupName() {
			return facilityGroupName;
		}

		public void setFacilityGroupName(String facilityGroupName) {
			this.facilityGroupName = facilityGroupName;
		}
		public String getSearchFieldDesc()
		{
			return searchFieldDesc;
		}
		
		public void setSearchFieldDesc(String searchFieldDesc)
		{
			this.searchFieldDesc = searchFieldDesc;
		}
		
		public String getManufacturer()
		{
			return manufacturer;
		}
		
		public void setManufacturer(String manufacturer)
		{
			this.manufacturer = manufacturer;
		}
		public void setAreaName(String areaName) {
			this.areaName = areaName;
		}
		public void setBuildingName(String buildingName) {
			this.buildingName = buildingName;
		}
		public void setFloorName(String floorName) {
			this.floorName = floorName;
		}
		public void setRoomName(String roomName) {
			this.roomName = roomName;
		}
		public String getAreaName() {
			return areaName;
		}
		public String getBuildingName() {
			return buildingName;
		}
		public String getFloorName() {
			return floorName;
		}
		
		public String getRoomName() {
			return roomName;
		}
		public String getReportingEntityName()
		{
			return reportingEntityName;
		}
		
		public void setReportingEntityName(String reportingEntityName)
		{
			this.reportingEntityName = reportingEntityName;
		}
		public String getDeptName()
		{
			return deptName;
		}
		
		public void setDeptName(String deptName)
		{
			this.deptName = deptName;
		}
		public void setStorageAreaDesc(String storageAreaDesc) {
			this.storageAreaDesc = storageAreaDesc;
		}

		public String getStorageAreaDesc() {
			return storageAreaDesc;
		}	
		
		public String getMaterialSubcategoryName() {
			return materialSubcategoryName;
		}
		public void setMaterialSubcategoryName(String materialSubcategoryName) {
			this.materialSubcategoryName = materialSubcategoryName;
		}
		public String getMaterialSubcategoryId() {
			return materialSubcategoryId;
		}
		public void setMaterialSubcategoryId(String materialSubcategoryId) {
			this.materialSubcategoryId = materialSubcategoryId;
		}	
		
		public String getCatalogId() {
			return catalogId;
		}
		public void setCatalogId(String catalogId) {
			this.catalogId = catalogId;
		}
		
		public String getCatalogCompanyId() {
			return catalogCompanyId;
		}
		public void setCatalogCompanyId(String catalogCompanyId) {
			this.catalogCompanyId = catalogCompanyId;
		}
		
		public boolean getIsMatCatFX() {
			return isMatCatFX;
		}
		public void setIsMatCatFX(boolean isMatCatFX) {
			this.isMatCatFX = isMatCatFX;
		}
		
		public String getMaterialCategoryName()
		{
			return materialCategoryName;
		}
		public void setMaterialCategoryName(String materialCategoryName)
		{
			this.materialCategoryName = materialCategoryName;
		}
		
		public String getMaterialCategoryId()
		{
			return materialCategoryId;
		}

		public void setMaterialCategoryId(String materialCategoryId)
		{
			this.materialCategoryId = materialCategoryId;
		}

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

	public String getBaseFieldId() {
		return baseFieldId;
	}

	public void setBaseFieldId(String baseFieldId) {
		this.baseFieldId = baseFieldId;
	}

	public String getBaseFieldName() {
		return baseFieldName;
	}

	public void setBaseFieldName(String baseFieldName) {
		this.baseFieldName = baseFieldName;
	}

	public String getBaseDescription() {
		return baseDescription;
	}

	public void setBaseDescription(String baseDescription) {
		this.baseDescription = baseDescription;
	}

	public String getChemicalFieldListId() {
		return chemicalFieldListId;
	}

	public void setChemicalFieldListId(String chemicalFieldListId) {
		this.chemicalFieldListId = chemicalFieldListId;
	}

    public String getGlobalizationLabel() {
        return globalizationLabel;
    }

    public void setGlobalizationLabel(String globalizationLabel) {
        this.globalizationLabel = globalizationLabel;
    }

    public String getGlobalizationLabelLetter() {
        return globalizationLabelLetter;
    }

    public void setGlobalizationLabelLetter(String globalizationLabelLetter) {
        this.globalizationLabelLetter = globalizationLabelLetter;
    }

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getUserGroupDesc() {
		return userGroupDesc;
	}

	public void setUserGroupDesc(String userGroupDesc) {
		this.userGroupDesc = userGroupDesc;
	}
}