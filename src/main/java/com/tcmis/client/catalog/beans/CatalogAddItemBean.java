package com.tcmis.client.catalog.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.StringHandler;


/******************************************************************************
 * CLASSNAME: CatalogAddItemBean <br>
 * @version: 1.0, Oct 30, 2007 <br>
 *****************************************************************************/


public class CatalogAddItemBean extends BaseDataBean {

	private BigDecimal requestId;
	private String materialDesc;
	private String manufacturer;
	private BigDecimal materialId;
	private BigDecimal partSize;
	private String sizeUnit;
	private String pkgStyle;
	private BigDecimal materialApprovedBy;
	private Date materialApprovedOn;
	private BigDecimal itemApprovedBy;
	private Date itemApprovedOn;
	private String mfgCatalogId;
	private BigDecimal partId;
	private String materialType;
	private BigDecimal caseQty;
	private String dimension;
	private String grade;
	private String mfgTradeName;
	private BigDecimal netwt;
	private String netwtUnit;
	private String companyId;
	private String customerMsdsNumber;
	private BigDecimal componentsPerItem;
	private String sampleOnly;
	private String possSize;
	private String possSpecialNote;
	private BigDecimal shelfLifeDays;
	private String shelfLifeBasis;
	private String storageTemp;
	private String catalogComponentItemId;
	private String catalogComponentId;
	private Date customerRevisionDate;
	private String labelColor;
	//this is to keep track of whether users add/deleted tabs
	private String newTabComponent;

	private String materialCriteria;
	private String cylinderType;
	private String prePkgStyle;
	private String pkgSize;
	private String pkgSizeUnit;
	private BigDecimal cylinderComponentsPerItem;
	private BigDecimal cylinderSize;
	private String cylinderSizeUnit;
	private String cylinderPkgStyle;
	private String cylinderMaterial;
	private String valueType;
	private BigDecimal noMsdsComponentsPerItem;
	private String noMsdsPkgStyle;
	private BigDecimal mfgId;

	private String manufacturerContact;
	private String manufacturerNotes;
	private String manufacturerUrl;

	private BigDecimal lineItem;
	private BigDecimal itemId;
	private String suggestedVendor;
	private String vendorContactName;
	private String vendorContactEmail;
	private String vendorContactPhone;
	private String vendorContactFax;
	private String qplStatus;
	private BigDecimal startingView;
	private String approvalLetterContent;
	private String replacesMsds;
	private String aerosolContainer;
	private String radioactive;
	private String nanomaterial;
	private String customerMixtureNumber;
	private String msdsOnLine;
	private String displayStatus;
	private String unitOfMeasure;
	private BigDecimal poundsPerUnit;
	private String hetUsageRecording;
	private String mixtureDesc;
	private BigDecimal percentByWeight;
	private String vocetStatusId;
	private String vocetCoatCategoryId;
	private String msdsIndexed;
	private String approvalRole;
	private String msdsVerified;
	private String itemVerified;
	private String customMsdsVerified;
	private BigDecimal mixRatioAmount;
	private String mixRatioSizeUnit;
	private BigDecimal amount;
	private String approvedCustMixtureNumber;
	private BigDecimal numberOfComponentsPerLine;
	private String allowMixture;
	private String msdsApprovalCode;
	private String kitApprovalCode;
	private Date globalRevisionDate;
	private Date sourceRevisionDate;
	private BigDecimal catalogAddItemId;
	private String toLocaleCode;
	private BigDecimal catalogQueueItemId;
	private Date revisionDate;
	private String task;
	private String customerOnlyMsds;
	private String facilityId;
	private String qcRequired;
	private String readyForQc;
	private BigDecimal catalogQueueAssignedTo;
	private String catalogQueueStatus;

	private BigDecimal catalogVendorAssignmentId;
	private String customerMfgId;
	private String customerMfgIdDisplay;

	//constructor
	public CatalogAddItemBean() {
	}

	//setters
	public void setRequestId(BigDecimal requestId) {
		this.requestId = requestId;
	}
	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}
	public void setPartSize(BigDecimal partSize) {
		this.partSize = partSize;
	}
	public void setSizeUnit(String sizeUnit) {
		this.sizeUnit = sizeUnit;
	}
	public void setPkgStyle(String pkgStyle) {
		this.pkgStyle = pkgStyle;
	}
	public void setMaterialApprovedBy(BigDecimal materialApprovedBy) {
		this.materialApprovedBy = materialApprovedBy;
	}
	public void setMaterialApprovedOn(Date materialApprovedOn) {
		this.materialApprovedOn = materialApprovedOn;
	}
	public void setItemApprovedBy(BigDecimal itemApprovedBy) {
		this.itemApprovedBy = itemApprovedBy;
	}
	public void setItemApprovedOn(Date itemApprovedOn) {
		this.itemApprovedOn = itemApprovedOn;
	}
	public void setMfgCatalogId(String mfgCatalogId) {
		this.mfgCatalogId = mfgCatalogId;
	}
	public void setPartId(BigDecimal partId) {
		this.partId = partId;
	}
	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}
	public void setCaseQty(BigDecimal caseQty) {
		this.caseQty = caseQty;
	}
	public void setDimension(String dimension) {
		this.dimension = dimension;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public void setMfgTradeName(String mfgTradeName) {
		this.mfgTradeName = mfgTradeName;
	}
	public void setNetwt(BigDecimal netwt) {
		this.netwt = netwt;
	}
	public void setNetwtUnit(String netwtUnit) {
		this.netwtUnit = netwtUnit;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setCustomerMsdsNumber(String customerMsdsNumber) {
		this.customerMsdsNumber = customerMsdsNumber;
	}
	public void setComponentsPerItem(BigDecimal componentsPerItem) {
		this.componentsPerItem = componentsPerItem;
	}
	public void setSampleOnly(String sampleOnly) {
		this.sampleOnly = sampleOnly;
	}
	public void setPossSize(String possSize) {
		this.possSize = possSize;
	}
	public void setPossSpecialNote(String possSpecialNote) {
		this.possSpecialNote = possSpecialNote;
	}
	public void setShelfLifeDays(BigDecimal shelfLifeDays) {
		this.shelfLifeDays = shelfLifeDays;
	}
	public void setShelfLifeBasis(String shelfLifeBasis) {
		this.shelfLifeBasis = shelfLifeBasis;
	}
	public void setStorageTemp(String storageTemp) {
		this.storageTemp = storageTemp;
	}
	public void setCatalogComponentItemId(String catalogComponentItemId) {
		this.catalogComponentItemId = catalogComponentItemId;
	}
	public void setCatalogComponentId(String catalogComponentId) {
		this.catalogComponentId = catalogComponentId;
	}
	public void setCustomerRevisionDate(Date customerRevisionDate) {
		this.customerRevisionDate = customerRevisionDate;
	}
	public void setLabelColor(String labelColor) {
		this.labelColor = labelColor;
	}

	public void setNewTabComponent(String newTabComponent) {
		this.newTabComponent = newTabComponent;
	}

	public void setMaterialCriteria(String materialCriteria) {
		this.materialCriteria = materialCriteria;
	}

	public void setCylinderType(String cylinderType) {
		this.cylinderType = cylinderType;
	}

	public void setPrePkgStyle(String prePkgStyle) {
		this.prePkgStyle = prePkgStyle;
	}

	public void setPkgSize(String pkgSize) {
		this.pkgSize = pkgSize;
	}

	public void setPkgSizeUnit(String pkgSizeUnit) {
		this.pkgSizeUnit = pkgSizeUnit;
	}

	public void setCylinderComponentsPerItem(BigDecimal cylinderComponentsPerItem) {
		this.cylinderComponentsPerItem = cylinderComponentsPerItem;
	}

	public void setCylinderSize(BigDecimal cylinderSize) {
		this.cylinderSize = cylinderSize;
	}

	public void setCylinderSizeUnit(String cylinderSizeUnit) {
		this.cylinderSizeUnit = cylinderSizeUnit;
	}

	public void setCylinderPkgStyle(String cylinderPkgStyle) {
		this.cylinderPkgStyle = cylinderPkgStyle;
	}

	public void setCylinderMaterial(String cylinderMaterial) {
		this.cylinderMaterial = cylinderMaterial;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

	public void setNoMsdsComponentsPerItem(BigDecimal noMsdsComponentsPerItem) {
		this.noMsdsComponentsPerItem = noMsdsComponentsPerItem;
	}

	public void setNoMsdsPkgStyle(String noMsdsPkgStyle) {
		this.noMsdsPkgStyle = noMsdsPkgStyle;
	}

	public void setMfgId(BigDecimal mfgId) {
		this.mfgId = mfgId;
	}

	public void setLineItem(BigDecimal lineItem) {
		this.lineItem = lineItem;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public void setSuggestedVendor(String suggestedVendor) {
		this.suggestedVendor = suggestedVendor;
	}

	public void setVendorContactName(String vendorContactName) {
		this.vendorContactName = vendorContactName;
	}

	public void setVendorContactEmail(String vendorContactEmail) {
		this.vendorContactEmail = vendorContactEmail;
	}

	public void setVendorContactPhone(String vendorContactPhone) {
		this.vendorContactPhone = vendorContactPhone;
	}

	public void setVendorContactFax(String vendorContactFax) {
		this.vendorContactFax = vendorContactFax;
	}

	public void setQplStatus(String qplStatus) {
		this.qplStatus = qplStatus;
	}

	public void setStartingView(BigDecimal startingView) {
		this.startingView = startingView;
	}

	public void setCustomMsdsVerified(String customMsdsVerified) {
		this.customMsdsVerified = customMsdsVerified;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	//getters
	public BigDecimal getAmount() {
		return amount;
	}
	public String getCustomMsdsVerified() {
		return customMsdsVerified;
	}
	public BigDecimal getRequestId() {
		return requestId;
	}
	public String getMaterialDesc() {
		return materialDesc;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public BigDecimal getMaterialId() {
		return materialId;
	}
	public BigDecimal getPartSize() {
		return partSize;
	}
	public String getSizeUnit() {
		return sizeUnit;
	}
	public String getPkgStyle() {
		return pkgStyle;
	}
	public BigDecimal getMaterialApprovedBy() {
		return materialApprovedBy;
	}
	public Date getMaterialApprovedOn() {
		return materialApprovedOn;
	}
	public BigDecimal getItemApprovedBy() {
		return itemApprovedBy;
	}
	public Date getItemApprovedOn() {
		return itemApprovedOn;
	}
	public String getMfgCatalogId() {
		return mfgCatalogId;
	}
	public BigDecimal getPartId() {
		return partId;
	}
	public String getMaterialType() {
		return materialType;
	}
	public BigDecimal getCaseQty() {
		return caseQty;
	}
	public String getDimension() {
		return dimension;
	}
	public String getGrade() {
		return grade;
	}
	public String getMfgTradeName() {
		return mfgTradeName;
	}
	public BigDecimal getNetwt() {
		return netwt;
	}
	public String getNetwtUnit() {
		return netwtUnit;
	}
	public String getCompanyId() {
		return companyId;
	}
	public String getCustomerMsdsNumber() {
		return StringHandler.emptyIfNull(customerMsdsNumber);
	}
	public BigDecimal getComponentsPerItem() {
		return componentsPerItem;
	}
	public String getSampleOnly() {
		return sampleOnly;
	}
	public String getPossSize() {
		return possSize;
	}
	public String getPossSpecialNote() {
		return possSpecialNote;
	}
	public BigDecimal getShelfLifeDays() {
		return shelfLifeDays;
	}
	public String getShelfLifeBasis() {
		return shelfLifeBasis;
	}
	public String getStorageTemp() {
		return storageTemp;
	}
	public String getCatalogComponentItemId() {
		return catalogComponentItemId;
	}
	public String getCatalogComponentId() {
		return catalogComponentId;
	}
	public Date getCustomerRevisionDate() {
		return customerRevisionDate;
	}
	public String getLabelColor() {
		return labelColor;
	}

	public String getNewTabComponent() {
		return newTabComponent;
	}

	public String getMaterialCriteria() {
		return materialCriteria;
	}

	public String getCylinderType() {
		return cylinderType;
	}

	public String getPrePkgStyle() {
		return prePkgStyle;
	}

	public String getPkgSize() {
		return pkgSize;
	}

	public String getPkgSizeUnit() {
		return pkgSizeUnit;
	}

	public BigDecimal getCylinderComponentsPerItem() {
		return cylinderComponentsPerItem;
	}

	public BigDecimal getCylinderSize() {
		return cylinderSize;
	}

	public String getCylinderSizeUnit() {
		return cylinderSizeUnit;
	}

	public String getCylinderPkgStyle() {
		return cylinderPkgStyle;
	}

	public String getCylinderMaterial() {
		return cylinderMaterial;
	}

	public String getValueType() {
		return valueType;
	}

	public BigDecimal getNoMsdsComponentsPerItem() {
		return noMsdsComponentsPerItem;
	}

	public String getNoMsdsPkgStyle() {
		return noMsdsPkgStyle;
	}

	public BigDecimal getMfgId() {
		return mfgId;
	}

	public String getManufacturerContact() {
		return manufacturerContact;
	}

	public void setManufacturerContact(String manufacturerContact) {
		this.manufacturerContact = manufacturerContact;
	}

	public String getManufacturerNotes() {
		return manufacturerNotes;
	}

	public void setManufacturerNotes(String manufacturerNotes) {
		this.manufacturerNotes = manufacturerNotes;
	}

	public String getManufacturerUrl() {
		return manufacturerUrl;
	}

	public void setManufacturerUrl(String manufacturerUrl) {
		this.manufacturerUrl = manufacturerUrl;
	}

	public BigDecimal getLineItem() {
		return lineItem;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public String getSuggestedVendor() {
		return suggestedVendor;
	}

	public String getVendorContactName() {
		return vendorContactName;
	}

	public String getVendorContactEmail() {
		return vendorContactEmail;
	}

	public String getVendorContactPhone() {
		return vendorContactPhone;
	}

	public String getVendorContactFax() {
		return vendorContactFax;
	}

	public String getQplStatus() {
		return qplStatus;
	}

	public BigDecimal getStartingView() {
		return startingView;
	}

	public String getApprovalLetterContent() {
		return approvalLetterContent;
	}

	public void setApprovalLetterContent(String approvalLetterContent) {
		this.approvalLetterContent = approvalLetterContent;
	}

	public String getReplacesMsds() {
		return replacesMsds;
	}

	public void setReplacesMsds(String replacesMsds) {
		this.replacesMsds = replacesMsds;
	}

	public String getAerosolContainer() {
		return aerosolContainer;
	}

	public void setAerosolContainer(String aerosolContainer) {
		this.aerosolContainer = aerosolContainer;
	}

	public String getRadioactive() {
		return radioactive;
	}

	public void setRadioactive(String radioactive) {
		this.radioactive = radioactive;
	}

	public String getNanomaterial() {
		return nanomaterial;
	}

	public void setNanomaterial(String nanomaterial) {
		this.nanomaterial = nanomaterial;
	}

	public String getCustomerMixtureNumber() {
		return customerMixtureNumber;
	}

	public void setCustomerMixtureNumber(String customerMixtureNumber) {
		this.customerMixtureNumber = customerMixtureNumber;
	}

	public String getMsdsOnLine() {
		return msdsOnLine;
	}

	public void setMsdsOnLine(String msdsOnLine) {
		this.msdsOnLine = msdsOnLine;
	}

	public String getDisplayStatus() {
		return displayStatus;
	}

	public void setDisplayStatus(String displayStatus) {
		this.displayStatus = displayStatus;
	}

	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	public BigDecimal getPoundsPerUnit() {
		return poundsPerUnit;
	}

	public void setPoundsPerUnit(BigDecimal poundsPerUnit) {
		this.poundsPerUnit = poundsPerUnit;
	}

	public String getHetUsageRecording() {
		return hetUsageRecording;
	}

	public void setHetUsageRecording(String hetUsageRecording) {
		this.hetUsageRecording = hetUsageRecording;
	}

	public String getMixtureDesc() {
		return mixtureDesc;
	}

	public void setMixtureDesc(String mixtureDesc) {
		this.mixtureDesc = mixtureDesc;
	}

	public BigDecimal getPercentByWeight() {
		return percentByWeight;
	}

	public void setPercentByWeight(BigDecimal percentByWeight) {
		this.percentByWeight = percentByWeight;
	}

	public String getVocetStatusId() {
		return vocetStatusId;
	}

	public void setVocetStatusId(String vocetStatusId) {
		this.vocetStatusId = vocetStatusId;
	}

	public String getVocetCoatCategoryId() {
		return vocetCoatCategoryId;
	}

	public void setVocetCoatCategoryId(String vocetCoatCategoryId) {
		this.vocetCoatCategoryId = vocetCoatCategoryId;
	}

	public String getMsdsIndexed() {
		return msdsIndexed;
	}

	public void setMsdsIndexed(String msdsIndexed) {
		this.msdsIndexed = msdsIndexed;
	}

	public String getApprovalRole() {
		return approvalRole;
	}

	public void setApprovalRole(String approvalRole) {
		this.approvalRole = approvalRole;
	}

	public String getMsdsVerified() {
		return msdsVerified;
	}

	public void setMsdsVerified(String msdsVerified) {
		this.msdsVerified = msdsVerified;
	}

	public String getItemVerified() {
		return itemVerified;
	}

	public void setItemVerified(String itemVerified) {
		this.itemVerified = itemVerified;
	}

	public BigDecimal getMixRatioAmount() {
		return mixRatioAmount;
	}

	public void setMixRatioAmount(BigDecimal mixRatioAmount) {
		this.mixRatioAmount = mixRatioAmount;
	}

	public String getMixRatioSizeUnit() {
		return mixRatioSizeUnit;
	}

	public void setMixRatioSizeUnit(String mixRatioSizeUnit) {
		this.mixRatioSizeUnit = mixRatioSizeUnit;
	}

	public String getApprovedCustMixtureNumber() {
		return approvedCustMixtureNumber;
	}

	public void setApprovedCustMixtureNumber(String approvedCustMixtureNumber) {
		this.approvedCustMixtureNumber = approvedCustMixtureNumber;
	}

	public BigDecimal getNumberOfComponentsPerLine() {
		return numberOfComponentsPerLine;
	}

	public void setNumberOfComponentsPerLine(BigDecimal numberOfComponentsPerLine) {
		this.numberOfComponentsPerLine = numberOfComponentsPerLine;
	}

	public String getAllowMixture() {
		return allowMixture;
	}

	public void setAllowMixture(String allowMixture) {
		this.allowMixture = allowMixture;
	}

	public String getMsdsApprovalCode() {
		return msdsApprovalCode;
	}

	public void setMsdsApprovalCode(String msdsApprovalCode) {
		this.msdsApprovalCode = msdsApprovalCode;
	}

	public String getKitApprovalCode() {
		return kitApprovalCode;
	}

	public void setKitApprovalCode(String kitApprovalCode) {
		this.kitApprovalCode = kitApprovalCode;
	}

	public Date getGlobalRevisionDate() {
		return globalRevisionDate;
	}

	public void setGlobalRevisionDate(Date globalRevisionDate) {
		this.globalRevisionDate = globalRevisionDate;
	}

	public Date getSourceRevisionDate() {
		return sourceRevisionDate;
	}

	public void setSourceRevisionDate(Date sourceRevisionDate) {
		this.sourceRevisionDate = sourceRevisionDate;
	}

	public BigDecimal getCatalogAddItemId() {
		return catalogAddItemId;
	}

	public void setCatalogAddItemId(BigDecimal catalogAddItemId) {
		this.catalogAddItemId = catalogAddItemId;
	}

	public String getToLocaleCode() {
		return toLocaleCode;
	}

	public void setToLocaleCode(String toLocaleCode) {
		this.toLocaleCode = toLocaleCode;
	}

	public BigDecimal getCatalogQueueItemId() {
		return catalogQueueItemId;
	}

	public void setCatalogQueueItemId(BigDecimal catalogQueueItemId) {
		this.catalogQueueItemId = catalogQueueItemId;
	}

	public Date getRevisionDate() {
		return revisionDate;
	}

	public void setRevisionDate(Date revisionDate) {
		this.revisionDate = revisionDate;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public String getCustomerOnlyMsds() {
		return customerOnlyMsds;
	}

	public void setCustomerOnlyMsds(String customerOnlyMsds) {
		this.customerOnlyMsds = customerOnlyMsds;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public String getQcRequired() {
		return qcRequired;
	}

	public void setQcRequired(String qcRequired) {
		this.qcRequired = qcRequired;
	}

	public boolean isSdsQcRequired() {
		return "Y".equals(qcRequired);
	}

	public String getReadyForQc() {
		return readyForQc;
	}

	public void setReadyForQc(String readyForQc) {
		this.readyForQc = readyForQc;
	}

	public boolean isReadyForSdsQc() {
		return "Y".equals(readyForQc);
	}

	public BigDecimal getCatalogVendorAssignmentId() {
		return catalogVendorAssignmentId;
	}

	public void setCatalogVendorAssignmentId(BigDecimal catalogVendorAssignmentId) {
		this.catalogVendorAssignmentId = catalogVendorAssignmentId;
	}

	public BigDecimal getCatalogQueueAssignedTo() {
		return catalogQueueAssignedTo;
	}

	public void setCatalogQueueAssignedTo(BigDecimal catalogQueueAssignedTo) {
		this.catalogQueueAssignedTo = catalogQueueAssignedTo;
	}

	public String getCatalogQueueStatus() {
		return catalogQueueStatus;
	}

	public void setCatalogQueueStatus(String catalogQueueStatus) {
		this.catalogQueueStatus = catalogQueueStatus;
	}

	public String getCustomerMfgId() {
		return customerMfgId;
	}

	public void setCustomerMfgId(String customerMfgId) {
		this.customerMfgId = customerMfgId;
	}

	public String getCustomerMfgIdDisplay() {
		return customerMfgIdDisplay;
	}

	public void setCustomerMfgIdDisplay(String customerMfgIdDisplay) {
		this.customerMfgIdDisplay = customerMfgIdDisplay;
	}
}