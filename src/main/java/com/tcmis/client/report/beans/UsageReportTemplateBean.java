package com.tcmis.client.report.beans;

import java.util.Date;
import java.util.Collection;
import java.util.Vector;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: UsageReportTemplateBean <br>
 * @version: 1.0, Mar 28, 2008 <br>
 *****************************************************************************/


public class UsageReportTemplateBean extends BaseDataBean {

	private BigDecimal reportTemplateId;
	private String companyId;
	private BigDecimal personnelId;
	private String templateName;
	private String reportingEntity;
	private String chemicalListName;
	private String casNumber;
	private String facilityId;
	private String application;
	private String partNumber;
	private Date beginDate;
	private Date endDate;
	private String category;
	private String method;
	private String manufacturer;
	private String dockId;
	private String deliveryPoint;
	private String materialCategory;
	private String partNumberCriteria;
	private String manufacturerCriteria;
	private String reportGenerationType;
	private String reportName;
	private String reportType;
	private String location;

	private String facilityName;
	private String applicationDesc;
	private String dockDesc;
	private String deliveryPointDesc;
	private String categoryDesc;
	private String listDesc;

  private Collection reportFieldCollection = new Vector();

  private String[] reportFieldList;
  private String[] foo;
  private String reportingEntityId;
  private String templateId;
  private String userGroupId; 
  private String userGroupFacilityId;
  private String owner;
  private String status;
  private String templateDescription;
  private String urlPageArg;	
  
  private Collection chemicalFieldCollection = new Vector();
  private String[] chemicalFieldList;
  private String gridType;
	private String listName;
	private String listId;
	private String constraint;
	private String operator;
	private String value;
	private String casNum;
	private String chemicalName;
	private String gridSubmit;
	private String gridDesc;
	private String baseFieldId;
    private String baseFieldName;
    private String baseDescription;
    private String chemicalFieldListId;
    private String catalogId;
    private String listFormat;
    
    private String header;
    private String footer;

    private String facilityGroupId;
    private String areaId;
    private String areaName;
    private String buildingId;
    private String buildingName;
    private String floorId;
    private String floorName;
    private String roomId;
    private String roomName;
    private String deptId;
    private String deptName;
    private String materialCategoryName;
	private String materialCategoryId;
	private String materialSubcategoryId;
    private String materialSubcategoryName;
    private String catalogCompanyId;
    private String facilityGroupName;
    private String reportingEntityName;
    
    private Date beginDateJsp;
	private Date endDateJsp;
    private String dayOfYearJsp;
    private String selDayOfYear;
    private String selDayOfMonth;
    private String selDayOfWeek;
    private String numOfDays;
    private String reportPeriodType;
    private String reportPeriodDay;
    
    private String emailMessage;
    private String emailSubject;
    private String emailUserGroupId;
    private String emailMessageNeg;
    private String emailSubjectNeg;
    private String emailUserGroupIdNeg;
    private String gateKeeping;
    private String includeOpenOrders;
    private boolean isMatCatFX;
    
    private String flammabilityControlZoneId;
    private String flammabilityControlZoneDesc;
    private String vocZoneId;
    private String vocZoneDescription;
    private String overFlamCtrlZnLimit;
    private String overFlamCtrlZnLmtPercent;
    private String globalizationLabel;
	private String globalizationLabelLetter;
    
    private String createdByName;
    private String companyName;
    private String userGroupDesc;
    
    private String flammable;
    private String queryType;
    private BigDecimal lastModifiedBy;
    private Date lastModifiedOn;
    private String partCategory;
    
     //constructor
	public UsageReportTemplateBean() {
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
	public void setChemicalListName(String chemicalListName) {
		this.chemicalListName = chemicalListName;
	}
	public void setCasNumber(String casNumber) {
		this.casNumber = casNumber;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public void setDockId(String dockId) {
		this.dockId = dockId;
	}
	public void setDeliveryPoint(String deliveryPoint) {
		this.deliveryPoint = deliveryPoint;
	}
	public void setMaterialCategory(String materialCategory) {
		this.materialCategory = materialCategory;
	}
	public void setPartNumberCriteria(String partNumberCriteria) {
		this.partNumberCriteria = partNumberCriteria;
	}
	public void setManufacturerCriteria(String manufacturerCriteria) {
		this.manufacturerCriteria = manufacturerCriteria;
	}
	public void setReportGenerationType(String reportGenerationType) {
		this.reportGenerationType = reportGenerationType;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public void setLocation(String location) {
		this.location = location;
	}

  public void setReportFieldCollection(Collection c) {
    this.reportFieldCollection = c;
  }

  public void setReportFieldList(String[] reportFieldList) {
    this.reportFieldList = reportFieldList;
  }

  public void setFoo(String[] s) {
    this.foo = s;
  }

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public void setApplicationDesc(String applicationDesc) {
		this.applicationDesc = applicationDesc;
	}

	public void setDockDesc(String dockDesc) {
		this.dockDesc = dockDesc;
	}

	public void setDeliveryPointDesc(String deliveryPointDesc) {
		this.deliveryPointDesc = deliveryPointDesc;
	}

	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
	}

	public void setListDesc(String listDesc) {
		this.listDesc = listDesc;
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
	public String getChemicalListName() {
		return chemicalListName;
	}
	public String getCasNumber() {
		return casNumber;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public String getApplication() {
		return application;
	}
	public String getPartNumber() {
		return partNumber;
	}
	public String getCategory() {
		return category;
	}
	public String getMethod() {
		return method;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public String getDockId() {
		return dockId;
	}
	public String getDeliveryPoint() {
		return deliveryPoint;
	}
	public String getMaterialCategory() {
		return materialCategory;
	}
	public String getPartNumberCriteria() {
		return partNumberCriteria;
	}
	public String getManufacturerCriteria() {
		return manufacturerCriteria;
	}
	public String getReportGenerationType() {
		return reportGenerationType;
	}
	public String getReportName() {
		return reportName;
	}
	public String getReportType() {
		return reportType;
	}
	public String getLocation() {
		return location;
	}
  public Collection getReportFieldCollection() {
    return this.reportFieldCollection;
  }

  public String[] getReportFieldList() {
    return reportFieldList;
  }

  public String[] getFoo() {
    return foo;
  }

	public String getFacilityName() {
		return facilityName;
	}

	public String getApplicationDesc() {
		return applicationDesc;
	}

	public String getDockDesc() {
		return dockDesc;
	}

	public String getDeliveryPointDesc() {
		return deliveryPointDesc;
	}

	public String getCategoryDesc() {
		return categoryDesc;
	}

	public String getListDesc() {
		return listDesc;
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
	
	public void setTemplateIdNumeric(BigDecimal templateId) {
		this.templateId = templateId.toString();
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

	public Collection getChemicalFieldCollection() {
		return chemicalFieldCollection;
	}

	public void setChemicalFieldCollection(Collection chemicalFieldCollection) {
		this.chemicalFieldCollection = chemicalFieldCollection;
	}

	public String[] getChemicalFieldList() {
		return chemicalFieldList;
	}

	public void setChemicalFieldList(String[] chemicalFieldList) {
		this.chemicalFieldList = chemicalFieldList;
	}

	public String getGridType() {
		return gridType;
	}

	public void setGridType(String gridType) {
		this.gridType = gridType;
	}

	public String getListName() {
		return listName;
	}

	public void setListName(String listName) {
		this.listName = listName;
	}

	public String getListId() {
		return listId;
	}

	public void setListId(String listId) {
		this.listId = listId;
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCasNum() {
		return casNum;
	}

	public void setCasNum(String casNum) {
		this.casNum = casNum;
	}

	public String getChemicalName() {
		return chemicalName;
	}

	public void setChemicalName(String chemicalName) {
		this.chemicalName = chemicalName;
	}

	public String getGridSubmit() {
		return gridSubmit;
	}

	public void setGridSubmit(String gridSubmit) {
		this.gridSubmit = gridSubmit;
	}

	public String getGridDesc() {
		return gridDesc;
	}

	public void setGridDesc(String gridDesc) {
		this.gridDesc = gridDesc;
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

	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public String getListFormat() {
		return listFormat;
	}

	public void setListFormat(String listFormat) {
		this.listFormat = listFormat;
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

	public String getFacilityGroupId() {
		return facilityGroupId;
	}

	public void setFacilityGroupId(String facilityGroupId) {
		this.facilityGroupId = facilityGroupId;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(String buildingId) {
		this.buildingId = buildingId;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	public String getFloorId() {
		return floorId;
	}

	public void setFloorId(String floorId) {
		this.floorId = floorId;
	}

	public String getFloorName() {
		return floorName;
	}

	public void setFloorName(String floorName) {
		this.floorName = floorName;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getMaterialCategoryName() {
		return materialCategoryName;
	}

	public void setMaterialCategoryName(String materialCategoryName) {
		this.materialCategoryName = materialCategoryName;
	}

	public String getMaterialCategoryId() {
		return materialCategoryId;
	}

	public void setMaterialCategoryId(String materialCategoryId) {
		this.materialCategoryId = materialCategoryId;
	}

	public String getMaterialSubcategoryId() {
		return materialSubcategoryId;
	}

	public void setMaterialSubcategoryId(String materialSubcategoryId) {
		this.materialSubcategoryId = materialSubcategoryId;
	}

	public String getMaterialSubcategoryName() {
		return materialSubcategoryName;
	}

	public void setMaterialSubcategoryName(String materialSubcategoryName) {
		this.materialSubcategoryName = materialSubcategoryName;
	}

	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}

	public void setCatalogCompanyId(String catalogCompanyId) {
		this.catalogCompanyId = catalogCompanyId;
	}

	public String getFacilityGroupName() {
		return facilityGroupName;
	}

	public void setFacilityGroupName(String facilityGroupName) {
		this.facilityGroupName = facilityGroupName;
	}

	public String getReportingEntityName() {
		return reportingEntityName;
	}

	public void setReportingEntityName(String reportingEntityName) {
		this.reportingEntityName = reportingEntityName;
	}

	public String getDayOfYearJsp() {
		return dayOfYearJsp;
	}

	public void setDayOfYearJsp(String dayOfYearJsp) {
		this.dayOfYearJsp = dayOfYearJsp;
	}

	public String getSelDayOfYear() {
		return selDayOfYear;
	}

	public void setSelDayOfYear(String selDayOfYear) {
		this.selDayOfYear = selDayOfYear;
	}

	public String getSelDayOfMonth() {
		return selDayOfMonth;
	}

	public void setSelDayOfMonth(String selDayOfMonth) {
		this.selDayOfMonth = selDayOfMonth;
	}

	public String getSelDayOfWeek() {
		return selDayOfWeek;
	}

	public void setSelDayOfWeek(String selDayOfWeek) {
		this.selDayOfWeek = selDayOfWeek;
	}

	public String getNumOfDays() {
		return numOfDays;
	}

	public void setNumOfDays(String numOfDays) {
		this.numOfDays = numOfDays;
	}

	public String getReportPeriodType() {
		return reportPeriodType;
	}

	public void setReportPeriodType(String reportPeriodType) {
		this.reportPeriodType = reportPeriodType;
	}

	public String getReportPeriodDay() {
		return reportPeriodDay;
	}

	public void setReportPeriodDay(String reportPeriodDay) {
		this.reportPeriodDay = reportPeriodDay;
	}

	public String getEmailMessage() {
		return emailMessage;
	}

	public void setEmailMessage(String emailMessage) {
		this.emailMessage = emailMessage;
	}

	public String getEmailSubject() {
		return emailSubject;
	}

	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

	public String getEmailUserGroupId() {
		return emailUserGroupId;
	}

	public void setEmailUserGroupId(String emailUserGroupId) {
		this.emailUserGroupId = emailUserGroupId;
	}

	public String getEmailMessageNeg() {
		return emailMessageNeg;
	}

	public void setEmailMessageNeg(String emailMessageNeg) {
		this.emailMessageNeg = emailMessageNeg;
	}

	public String getEmailSubjectNeg() {
		return emailSubjectNeg;
	}

	public void setEmailSubjectNeg(String emailSubjectNeg) {
		this.emailSubjectNeg = emailSubjectNeg;
	}

	public String getEmailUserGroupIdNeg() {
		return emailUserGroupIdNeg;
	}

	public void setEmailUserGroupIdNeg(String emailUserGroupIdNeg) {
		this.emailUserGroupIdNeg = emailUserGroupIdNeg;
	}

	public String getGateKeeping() {
		return gateKeeping;
	}

	public void setGateKeeping(String gateKeeping) {
		this.gateKeeping = gateKeeping;
	}

	public String getIncludeOpenOrders() {
		return includeOpenOrders;
	}

	public void setIncludeOpenOrders(String includeOpenOrders) {
		this.includeOpenOrders = includeOpenOrders;
	}

	public Date getBeginDateJsp() {
		return beginDateJsp;
	}

	public void setBeginDateJsp(Date beginDateJsp) {
		this.beginDateJsp = beginDateJsp;
	}

	public Date getEndDateJsp() {
		return endDateJsp;
	}

	public void setEndDateJsp(Date endDateJsp) {
		this.endDateJsp = endDateJsp;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

    public boolean getIsMatCatFX() {
        return isMatCatFX;
    }

    public void setIsMatCatFX(boolean matCatFX) {
        isMatCatFX = matCatFX;
    }

	public String getOverFlamCtrlZnLimit() {
		return overFlamCtrlZnLimit;
	}

	public void setOverFlamCtrlZnLimit(String overFlamCtrlZnLimit) {
		this.overFlamCtrlZnLimit = overFlamCtrlZnLimit;
	}

	public String getOverFlamCtrlZnLmtPercent() {
		return overFlamCtrlZnLmtPercent;
	}

	public void setOverFlamCtrlZnLmtPercent(String overFlamCtrlZnLmtPercent) {
		this.overFlamCtrlZnLmtPercent = overFlamCtrlZnLmtPercent;
	}

	public String getFlammabilityControlZoneDesc() {
		return flammabilityControlZoneDesc;
	}

	public void setFlammabilityControlZoneDesc(String flammabilityControlZoneDesc) {
		this.flammabilityControlZoneDesc = flammabilityControlZoneDesc;
	}

	public String getFlammabilityControlZoneId() {
		return flammabilityControlZoneId;
	}

	public void setFlammabilityControlZoneId(String flammabilityControlZoneId) {
		this.flammabilityControlZoneId = flammabilityControlZoneId;
	}

	public String getVocZoneId() {
		return vocZoneId;
	}

	public void setVocZoneId(String vocZoneId) {
		this.vocZoneId = vocZoneId;
	}

	public String getVocZoneDescription() {
		return vocZoneDescription;
	}

	public void setVocZoneDescription(String vocZoneDescription) {
		this.vocZoneDescription = vocZoneDescription;
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

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public BigDecimal getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(BigDecimal lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Date getLastModifiedOn() {
		return lastModifiedOn;
	}

	public void setLastModifiedOn(Date lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}

	public String getPartCategory() {
		return partCategory;
	}

	public void setPartCategory(String partCategory) {
		this.partCategory = partCategory;
	}
}