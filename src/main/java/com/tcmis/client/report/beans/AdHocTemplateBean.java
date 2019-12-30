package com.tcmis.client.report.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: AdHocTemplateBean <br>
 * @version: 1.0, Jul 9, 2012 <br>
 *****************************************************************************/


public class AdHocTemplateBean extends BaseDataBean {

	private BigDecimal templateId;
	private String companyId;
	private String reportType;
	private BigDecimal personnelId;
	private String templateName;
	private String list;
	private String sep;
	private Date startDate;
	private Date stopDate;
	private String listId;
	private String addlConstraint;
	private String addlFrom;
	private String casNumber;
	private String facilityId;
	private String application;
	private String catPartNo;
	private String catPartNoSearchType;
	private String dockLocationId;
	private String deliveryPoint;
	private BigDecimal categoryId;
	private String manufacturer;
	private String manufacturerSearchType;
	private String includeColumnAlias;
	private String partLocation;
	private String partCategory;
	private String accumulationPoint;
	private String vendorId;
	private BigDecimal vendorProfileId;
	private String managementOptionList;
	private String excludeHubWaste;
	private String queryType;
	private String chemicalList;
	private String chemicalListFormat;
	private String outputMode;
	private String reportName;
	private String managementOptionDesc;
	private String wasteProfileDesc;
	private BigDecimal userGroupId;
	private String userGroupFacilityId;
	private String owner;
	private String status;
	private BigDecimal lastModifiedBy;
	private Date lastModifiedOn;
	private String urlPageArg;
	private String reportingEntityId;
	private String accountSysId;
	private String chargeType;
	private String chargeNumber11;
	private String chargeNumber22;
	private String searchBy;
	private String searchText;
	private BigDecimal invoice;
	private BigDecimal invoicePeriod;
	private String reportField;
	private Date invoiceStartDate;
	private Date invoiceEndDate;
	private BigDecimal requestor;
	private String requestorName;
	private String commodity;
	private String dateDeliveredGroupBy;
	private String sourceHub;
	private String itemType;
	private String searchType;
	private String outputFileType;
	private String uom;
	private BigDecimal createdBy;
	private String templateDescription;
	private String allowEdit;
	private String lastModifiedByName;
	private String createdByName;
	private Date dateCreated;
	private String companyName;
	private String userGroupDesc;
	private String globalizationLabel;
	private String globalizationLabelLetter;
	private String chargeNumber3;
	private String chargeNumber4;
	private String poNumber;
	private String areaId;
	private String buildingId;
	private BigDecimal roomId;
	private String pageId;
	private BigDecimal floorId;
	private String facilityGroupId;
	private String divisionId;
	private String customerPartNo;
	private String shippingReference;
	private String customerInvoiceNo;
	private String invoiceNumber;
	private String searchParameter;
	private String searchValue;
	private String msdsMatchType;
	private BigDecimal mfgId;
	private String physicalState;
	private BigDecimal msdsPh;
	private String msdsPhCompare;
	private BigDecimal msdsFlashPt;
	private String msdsFpCompare;
	private String msdsFpUnit;
	private BigDecimal vaporPressure;
	private String msdsVpCompare;
	private String msdsVpUnit;
	private BigDecimal voc;
	private String vocCompare;
	private String vocUnit;
	private BigDecimal solidsPercent;
	private String msdsSpCompare;
	private String nfpaHealth;
	private String nfpaHealthComp;
	private String nfpaFlam;
	private String nfpaFlamComp;
	private String nfpaReactivity;
	private String nfpaReactComp;
	private String specificHazard;
	private String hmisHealth;
	private String hmisHealthComp;
	private String hmisFlam;
	private String hmisFlamComp;
	private String hmisReactivity;
	private String hmisReactComp;
	private String personalProt;
	private String constraintSeperator;
	private String fullDbSearch;
	private String approvedSearch;
	private String kitOnly;
	private String stocked;
	private BigDecimal vocLwes;
	private String vocLwesCompare;
	private String vocLwesUnit;
	private String storageAreaId;
	private String searchField;
	private String matchType;
	private String reportCriteria;
	private String mfgDesc;
	private String deptId;
	private String materialCategoryName;
	private String materialCategoryId;
	private String areaName;
	private String deptName;
	private String buildingName;
	private String applicationDesc;
	
	private String catalogId;
	private String catalogCompanyId;
	private String materialSubcategoryId;
	private String materialSubcategoryName;
    private String materialCategoryDesc;
    private String header;
    private String footer;

    private String reportPeriodType;
    private BigDecimal reportPeriodDay;
    
    private String emailMessage;
    private String emailSubject;
    private BigDecimal emailUserGroupId;
    private String emailMessageNeg;
    private String emailSubjectNeg;
    private BigDecimal emailUserGroupIdNeg;
    private String gateKeeping;
    private String includeOpenOrders;
    
    private String flammabilityControlZoneId;    
    private String flammabilityControlZoneDesc;
    private String vocZoneId;
    private String vocZoneDescription;
    private String overFlamCtrlZnLimit;
    private String overFlamCtrlZnLmtPercent;
    
    private String flammable;
    
    //constructor
	public AdHocTemplateBean() {
	}



	//setters
	public void setTemplateId(BigDecimal templateId) {
		this.templateId = templateId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public void setList(String list) {
		this.list = list;
	}
	public void setSep(String sep) {
		this.sep = sep;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public void setStopDate(Date stopDate) {
		this.stopDate = stopDate;
	}
	public void setListId(String listId) {
		this.listId = listId;
	}
	public void setAddlConstraint(String addlConstraint) {
		this.addlConstraint = addlConstraint;
	}
	public void setAddlFrom(String addlFrom) {
		this.addlFrom = addlFrom;
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

	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}
	public void setCatPartNoSearchType(String catPartNoSearchType) {
		this.catPartNoSearchType = catPartNoSearchType;
	}
	public void setDockLocationId(String dockLocationId) {
		this.dockLocationId = dockLocationId;
	}
	public void setDeliveryPoint(String deliveryPoint) {
		this.deliveryPoint = deliveryPoint;
	}
	public void setCategoryId(BigDecimal categoryId) {
		this.categoryId = categoryId;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public void setManufacturerSearchType(String manufacturerSearchType) {
		this.manufacturerSearchType = manufacturerSearchType;
	}
	public void setIncludeColumnAlias(String includeColumnAlias) {
		this.includeColumnAlias = includeColumnAlias;
	}
	public void setPartLocation(String partLocation) {
		this.partLocation = partLocation;
	}
	public void setPartCategory(String partCategory) {
		this.partCategory = partCategory;
	}
	public void setAccumulationPoint(String accumulationPoint) {
		this.accumulationPoint = accumulationPoint;
	}
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}
	public void setVendorProfileId(BigDecimal vendorProfileId) {
		this.vendorProfileId = vendorProfileId;
	}
	public void setManagementOptionList(String managementOptionList) {
		this.managementOptionList = managementOptionList;
	}
	public void setExcludeHubWaste(String excludeHubWaste) {
		this.excludeHubWaste = excludeHubWaste;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	public void setChemicalList(String chemicalList) {
		this.chemicalList = chemicalList;
	}
	public void setChemicalListFormat(String chemicalListFormat) {
		this.chemicalListFormat = chemicalListFormat;
	}
	public void setOutputMode(String outputMode) {
		this.outputMode = outputMode;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public void setManagementOptionDesc(String managementOptionDesc) {
		this.managementOptionDesc = managementOptionDesc;
	}
	public void setWasteProfileDesc(String wasteProfileDesc) {
		this.wasteProfileDesc = wasteProfileDesc;
	}
	public void setUserGroupId(BigDecimal userGroupId) {
		this.userGroupId = userGroupId;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setLastModifiedBy(BigDecimal lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	public void setLastModifiedOn(Date lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}
	public void setUrlPageArg(String urlPageArg) {
		this.urlPageArg = urlPageArg;
	}
	public void setReportingEntityId(String reportingEntityId) {
		this.reportingEntityId = reportingEntityId;
	}
	public void setAccountSysId(String accountSysId) {
		this.accountSysId = accountSysId;
	}
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}
	public void setChargeNumber1(String chargeNumber11) {
		this.chargeNumber11 = chargeNumber11;
	}
	public void setChargeNumber2(String chargeNumber22) {
		this.chargeNumber22 = chargeNumber22;
	}
	public void setSearchBy(String searchBy) {
		this.searchBy = searchBy;
	}
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	public void setInvoice(BigDecimal invoice) {
		this.invoice = invoice;
	}
	public void setInvoicePeriod(BigDecimal invoicePeriod) {
		this.invoicePeriod = invoicePeriod;
	}
	public void setReportField(String reportField) {
		this.reportField = reportField;
	}
	public void setInvoiceStartDate(Date invoiceStartDate) {
		this.invoiceStartDate = invoiceStartDate;
	}
	public void setInvoiceEndDate(Date invoiceEndDate) {
		this.invoiceEndDate = invoiceEndDate;
	}
	public void setRequestor(BigDecimal requestor) {
		this.requestor = requestor;
	}
	public void setRequestorName(String requestorName) {
		this.requestorName = requestorName;
	}
	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}
	public void setDateDeliveredGroupBy(String dateDeliveredGroupBy) {
		this.dateDeliveredGroupBy = dateDeliveredGroupBy;
	}
	public void setSourceHub(String sourceHub) {
		this.sourceHub = sourceHub;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public void setOutputFileType(String outputFileType) {
		this.outputFileType = outputFileType;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public void setCreatedBy(BigDecimal createdBy) {
		this.createdBy = createdBy;
	}
	public void setTemplateDescription(String templateDescription) {
		this.templateDescription = templateDescription;
	}
	public void setAllowEdit(String allowEdit) {
		this.allowEdit = allowEdit;
	}
	public void setLastModifiedByName(String lastModifiedByName) {
		this.lastModifiedByName = lastModifiedByName;
	}
	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public void setUserGroupDesc(String userGroupDesc) {
		this.userGroupDesc = userGroupDesc;
	}
	public void setGlobalizationLabel(String globalizationLabel) {
		this.globalizationLabel = globalizationLabel;
	}
	public void setGlobalizationLabelLetter(String globalizationLabelLetter) {
		this.globalizationLabelLetter = globalizationLabelLetter;
	}
	public void setChargeNumber3(String chargeNumber3) {
		this.chargeNumber3 = chargeNumber3;
	}
	public void setChargeNumber4(String chargeNumber4) {
		this.chargeNumber4 = chargeNumber4;
	}
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	
	public void setRoomId(BigDecimal roomId) {
		this.roomId = roomId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	public void setFloorId(BigDecimal floorId) {
		this.floorId = floorId;
	}
	public void setFacilityGroupId(String facilityGroupId) {
		this.facilityGroupId = facilityGroupId;
	}
	public void setDivisionId(String divisionId) {
		this.divisionId = divisionId;
	}
	public void setCustomerPartNo(String customerPartNo) {
		this.customerPartNo = customerPartNo;
	}
	public void setShippingReference(String shippingReference) {
		this.shippingReference = shippingReference;
	}
	public void setCustomerInvoiceNo(String customerInvoiceNo) {
		this.customerInvoiceNo = customerInvoiceNo;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public void setSearchParameter(String searchParameter) {
		this.searchParameter = searchParameter;
	}
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}
	public void setMsdsMatchType(String msdsMatchType) {
		this.msdsMatchType = msdsMatchType;
	}
	public void setMfgId(BigDecimal mfgId) {
		this.mfgId = mfgId;
	}
	public void setPhysicalState(String physicalState) {
		this.physicalState = physicalState;
	}
	public void setMsdsPh(BigDecimal msdsPh) {
		this.msdsPh = msdsPh;
	}
	public void setMsdsPhCompare(String msdsPhCompare) {
		this.msdsPhCompare = msdsPhCompare;
	}
	public void setMsdsFlashPt(BigDecimal msdsFlashPt) {
		this.msdsFlashPt = msdsFlashPt;
	}
	public void setMsdsFpCompare(String msdsFpCompare) {
		this.msdsFpCompare = msdsFpCompare;
	}
	public void setMsdsFpUnit(String msdsFpUnit) {
		this.msdsFpUnit = msdsFpUnit;
	}
	public void setVaporPressure(BigDecimal vaporPressure) {
		this.vaporPressure = vaporPressure;
	}
	public void setMsdsVpCompare(String msdsVpCompare) {
		this.msdsVpCompare = msdsVpCompare;
	}
	public void setMsdsVpUnit(String msdsVpUnit) {
		this.msdsVpUnit = msdsVpUnit;
	}
	public void setVoc(BigDecimal voc) {
		this.voc = voc;
	}
	public void setVocCompare(String vocCompare) {
		this.vocCompare = vocCompare;
	}
	public void setVocUnit(String vocUnit) {
		this.vocUnit = vocUnit;
	}
	public void setSolidsPercent(BigDecimal solidsPercent) {
		this.solidsPercent = solidsPercent;
	}
	public void setMsdsSpCompare(String msdsSpCompare) {
		this.msdsSpCompare = msdsSpCompare;
	}
	public void setNfpaHealth(String nfpaHealth) {
		this.nfpaHealth = nfpaHealth;
	}
	public void setNfpaHealthComp(String nfpaHealthComp) {
		this.nfpaHealthComp = nfpaHealthComp;
	}
	public void setNfpaFlam(String nfpaFlam) {
		this.nfpaFlam = nfpaFlam;
	}
	public void setNfpaFlamComp(String nfpaFlamComp) {
		this.nfpaFlamComp = nfpaFlamComp;
	}
	public void setNfpaReactivity(String nfpaReactivity) {
		this.nfpaReactivity = nfpaReactivity;
	}
	public void setNfpaReactComp(String nfpaReactComp) {
		this.nfpaReactComp = nfpaReactComp;
	}
	public void setSpecificHazard(String specificHazard) {
		this.specificHazard = specificHazard;
	}
	public void setHmisHealth(String hmisHealth) {
		this.hmisHealth = hmisHealth;
	}
	public void setHmisHealthComp(String hmisHealthComp) {
		this.hmisHealthComp = hmisHealthComp;
	}
	public void setHmisFlam(String hmisFlam) {
		this.hmisFlam = hmisFlam;
	}
	public void setHmisFlamComp(String hmisFlamComp) {
		this.hmisFlamComp = hmisFlamComp;
	}
	public void setHmisReactivity(String hmisReactivity) {
		this.hmisReactivity = hmisReactivity;
	}
	public void setHmisReactComp(String hmisReactComp) {
		this.hmisReactComp = hmisReactComp;
	}
	public void setPersonalProt(String personalProt) {
		this.personalProt = personalProt;
	}
	public void setConstraintSeperator(String constraintSeperator) {
		this.constraintSeperator = constraintSeperator;
	}
	public void setFullDbSearch(String fullDbSearch) {
		this.fullDbSearch = fullDbSearch;
	}
	public void setApprovedSearch(String approvedSearch) {
		this.approvedSearch = approvedSearch;
	}
	public void setKitOnly(String kitOnly) {
		this.kitOnly = kitOnly;
	}
	public void setStocked(String stocked) {
		this.stocked = stocked;
	}
	public void setVocLwes(BigDecimal vocLwes) {
		this.vocLwes = vocLwes;
	}
	public void setVocLwesCompare(String vocLwesCompare) {
		this.vocLwesCompare = vocLwesCompare;
	}
	public void setVocLwesUnit(String vocLwesUnit) {
		this.vocLwesUnit = vocLwesUnit;
	}
	public void setStorageAreaId(String storageAreaId) {
		this.storageAreaId = storageAreaId;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}	
	
	public String getFlammable() {
		return flammable;
	}

	public void setFlammable(String flammable) {
		this.flammable = flammable;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public String getDeptName() {
		return deptName;
	}

	public String getAreaName() {
		return areaName;
	}

	public String getStorageAreaId() {
		return storageAreaId;
	}

	public BigDecimal getTemplateId() {
		return templateId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public String getReportType() {
		return reportType;
	}
	public BigDecimal getPersonnelId() {
		return personnelId;
	}
	public String getTemplateName() {
		return templateName;
	}
	public String getList() {
		return list;
	}
	public String getSep() {
		return sep;
	}
	public Date getStartDate() {
		return startDate;
	}
	public Date getStopDate() {
		return stopDate;
	}
	public String getListId() {
		return listId;
	}
	public String getAddlConstraint() {
		return addlConstraint;
	}
	public String getAddlFrom() {
		return addlFrom;
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

	public String getCatPartNo() {
		return catPartNo;
	}
	public String getCatPartNoSearchType() {
		return catPartNoSearchType;
	}
	public String getDockLocationId() {
		return dockLocationId;
	}
	public String getDeliveryPoint() {
		return deliveryPoint;
	}
	public BigDecimal getCategoryId() {
		return categoryId;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public String getManufacturerSearchType() {
		return manufacturerSearchType;
	}
	public String getIncludeColumnAlias() {
		return includeColumnAlias;
	}
	public String getPartLocation() {
		return partLocation;
	}
	public String getPartCategory() {
		return partCategory;
	}
	public String getAccumulationPoint() {
		return accumulationPoint;
	}
	public String getVendorId() {
		return vendorId;
	}
	public BigDecimal getVendorProfileId() {
		return vendorProfileId;
	}
	public String getManagementOptionList() {
		return managementOptionList;
	}
	public String getExcludeHubWaste() {
		return excludeHubWaste;
	}
	public String getQueryType() {
		return queryType;
	}
	public String getChemicalList() {
		return chemicalList;
	}
	public String getChemicalListFormat() {
		return chemicalListFormat;
	}
	public String getOutputMode() {
		return outputMode;
	}
	public String getReportName() {
		return reportName;
	}
	public String getManagementOptionDesc() {
		return managementOptionDesc;
	}
	public String getWasteProfileDesc() {
		return wasteProfileDesc;
	}
	public BigDecimal getUserGroupId() {
		return userGroupId;
	}
	public String getOwner() {
		return owner;
	}
	public String getStatus() {
		return status;
	}
	public BigDecimal getLastModifiedBy() {
		return lastModifiedBy;
	}
	public Date getLastModifiedOn() {
		return lastModifiedOn;
	}
	public String getUrlPageArg() {
		return urlPageArg;
	}
	public String getReportingEntityId() {
		return reportingEntityId;
	}
	public String getAccountSysId() {
		return accountSysId;
	}
	public String getChargeType() {
		return chargeType;
	}
	public String getChargeNumber1() {
		return chargeNumber11;
	}
	public String getChargeNumber2() {
		return chargeNumber22;
	}
	public String getSearchBy() {
		return searchBy;
	}
	public String getSearchText() {
		return searchText;
	}
	public BigDecimal getInvoice() {
		return invoice;
	}
	public BigDecimal getInvoicePeriod() {
		return invoicePeriod;
	}
	public String getReportField() {
		return reportField;
	}
	public Date getInvoiceStartDate() {
		return invoiceStartDate;
	}
	public Date getInvoiceEndDate() {
		return invoiceEndDate;
	}
	public BigDecimal getRequestor() {
		return requestor;
	}
	public String getRequestorName() {
		return requestorName;
	}
	public String getCommodity() {
		return commodity;
	}
	public String getDateDeliveredGroupBy() {
		return dateDeliveredGroupBy;
	}
	public String getSourceHub() {
		return sourceHub;
	}
	public String getItemType() {
		return itemType;
	}
	public String getSearchType() {
		return searchType;
	}
	public String getOutputFileType() {
		return outputFileType;
	}
	public String getUom() {
		return uom;
	}
	public BigDecimal getCreatedBy() {
		return createdBy;
	}
	public String getTemplateDescription() {
		return templateDescription;
	}
	public String getAllowEdit() {
		return allowEdit;
	}
	public String getLastModifiedByName() {
		return lastModifiedByName;
	}
	public String getCreatedByName() {
		return createdByName;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public String getCompanyName() {
		return companyName;
	}
	public String getUserGroupDesc() {
		return userGroupDesc;
	}
	public String getGlobalizationLabel() {
		return globalizationLabel;
	}
	public String getGlobalizationLabelLetter() {
		return globalizationLabelLetter;
	}
	public String getChargeNumber3() {
		return chargeNumber3;
	}
	public String getChargeNumber4() {
		return chargeNumber4;
	}
	public String getPoNumber() {
		return poNumber;
	}
	public BigDecimal getRoomId() {
		return roomId;
	}
	public String getPageId() {
		return pageId;
	}
	public BigDecimal getFloorId() {
		return floorId;
	}
	public String getFacilityGroupId() {
		return facilityGroupId;
	}
	public String getDivisionId() {
		return divisionId;
	}
	public String getCustomerPartNo() {
		return customerPartNo;
	}
	public String getShippingReference() {
		return shippingReference;
	}
	public String getCustomerInvoiceNo() {
		return customerInvoiceNo;
	}
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public String getSearchParameter() {
		return searchParameter;
	}
	public String getSearchValue() {
		return searchValue;
	}
	public String getMsdsMatchType() {
		return msdsMatchType;
	}
	public BigDecimal getMfgId() {
		return mfgId;
	}
	public String getPhysicalState() {
		return physicalState;
	}
	public BigDecimal getMsdsPh() {
		return msdsPh;
	}
	public String getMsdsPhCompare() {
		return msdsPhCompare;
	}
	public BigDecimal getMsdsFlashPt() {
		return msdsFlashPt;
	}
	public String getMsdsFpCompare() {
		return msdsFpCompare;
	}
	public String getMsdsFpUnit() {
		return msdsFpUnit;
	}
	public BigDecimal getVaporPressure() {
		return vaporPressure;
	}
	public String getMsdsVpCompare() {
		return msdsVpCompare;
	}
	public String getMsdsVpUnit() {
		return msdsVpUnit;
	}
	public BigDecimal getVoc() {
		return voc;
	}
	public String getVocCompare() {
		return vocCompare;
	}
	public String getVocUnit() {
		return vocUnit;
	}
	public BigDecimal getSolidsPercent() {
		return solidsPercent;
	}
	public String getMsdsSpCompare() {
		return msdsSpCompare;
	}
	public String getNfpaHealth() {
		return nfpaHealth;
	}
	public String getNfpaHealthComp() {
		return nfpaHealthComp;
	}
	public String getNfpaFlam() {
		return nfpaFlam;
	}
	public String getNfpaFlamComp() {
		return nfpaFlamComp;
	}
	public String getNfpaReactivity() {
		return nfpaReactivity;
	}
	public String getNfpaReactComp() {
		return nfpaReactComp;
	}
	public String getSpecificHazard() {
		return specificHazard;
	}
	public String getHmisHealth() {
		return hmisHealth;
	}
	public String getHmisHealthComp() {
		return hmisHealthComp;
	}
	public String getHmisFlam() {
		return hmisFlam;
	}
	public String getHmisFlamComp() {
		return hmisFlamComp;
	}
	public String getHmisReactivity() {
		return hmisReactivity;
	}
	public String getHmisReactComp() {
		return hmisReactComp;
	}
	public String getPersonalProt() {
		return personalProt;
	}
	public String getConstraintSeperator() {
		return constraintSeperator;
	}
	public String getFullDbSearch() {
		return fullDbSearch;
	}
	public String getApprovedSearch() {
		return approvedSearch;
	}
	public String getKitOnly() {
		return kitOnly;
	}
	public String getStocked() {
		return stocked;
	}
	public BigDecimal getVocLwes() {
		return vocLwes;
	}
	public String getVocLwesCompare() {
		return vocLwesCompare;
	}
	public String getVocLwesUnit() {
		return vocLwesUnit;
	}
	public void setUserGroupFacilityId(String userGroupFacilityId) {
		this.userGroupFacilityId = userGroupFacilityId;
	}
	public String getUserGroupFacilityId() {
		return userGroupFacilityId;
	}
	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}
	public String getSearchField() {
		return searchField;
	}
	public void setMatchType(String matchType) {
		this.matchType = matchType;
	}
	public String getMatchType() {
		return matchType;
	}
	public String getReportCriteria() {
		    return reportCriteria;
	}
	public void setReportCriteria(String reportCriteria) {
		    this.reportCriteria = reportCriteria;
	}
	public void setMfgDesc(String mfgDesc) {
		this.mfgDesc = mfgDesc;
	}
	public String getMfgDesc() {
		return mfgDesc;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getDeptId() {
		return deptId;
	}
	public String getMaterialCategoryId()
	{
		return materialCategoryId;
	}

	public void setMaterialCategoryId(String materialCategoryId)
	{
		this.materialCategoryId = materialCategoryId;
	}
	public String getMaterialCategoryName()
	{
		return materialCategoryName;
	}

	public void setMaterialCategoryName(String materialCategoryName)
	{
		this.materialCategoryName = materialCategoryName;
	}

    public String getApplicationDesc() {
        return applicationDesc;
    }

    public void setApplicationDesc(String applicationDesc) {
        this.applicationDesc = applicationDesc;
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
    
	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}
	public void setCatalogCompanyId(String catalogCompanyId) {
		this.catalogCompanyId = catalogCompanyId;
	}
	
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	
	public void setMaterialSubcategoryId(String materialSubcategoryId) {
		this.materialSubcategoryId = materialSubcategoryId;
	}
	
	public void setMaterialSubcategoryName(String materialSubcategoryName) {
		this.materialSubcategoryName = materialSubcategoryName;
	}
	
	public void setMaterialCategoryDesc(String materialCategoryDesc) {
		this.materialCategoryDesc = materialCategoryDesc;
	}
   
	public String getCatalogId() {
		return catalogId;
	}
	
	public String getMaterialSubcategoryId() {
		return materialSubcategoryId;
	}
	
	public String getMaterialSubcategoryName() {
		return materialSubcategoryName;
	}
	
	public String getMaterialCategoryDesc() {
		return materialCategoryDesc;
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



	public String getReportPeriodType() {
		return reportPeriodType;
	}



	public void setReportPeriodType(String reportPeriodType) {
		this.reportPeriodType = reportPeriodType;
	}



	public BigDecimal getReportPeriodDay() {
		return reportPeriodDay;
	}



	public void setReportPeriodDay(BigDecimal reportPeriodDay) {
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



	public BigDecimal getEmailUserGroupId() {
		return emailUserGroupId;
	}



	public void setEmailUserGroupId(BigDecimal emailUserGroupId) {
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



	public BigDecimal getEmailUserGroupIdNeg() {
		return emailUserGroupIdNeg;
	}



	public void setEmailUserGroupIdNeg(BigDecimal emailUserGroupIdNeg) {
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



	public String getFlammabilityControlZoneId() {
		return flammabilityControlZoneId;
	}



	public void setFlammabilityControlZoneId(String flammabilityControlZoneId) {
		this.flammabilityControlZoneId = flammabilityControlZoneId;
	}



	public String getFlammabilityControlZoneDesc() {
		return flammabilityControlZoneDesc;
	}



	public void setFlammabilityControlZoneDesc(String flammabilityControlZoneDesc) {
		this.flammabilityControlZoneDesc = flammabilityControlZoneDesc;
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

}