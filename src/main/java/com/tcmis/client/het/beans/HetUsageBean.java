package com.tcmis.client.het.beans;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;

import org.apache.struts.action.ActionForm;

import com.tcmis.common.framework.BaseInputBean;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: HetUsageBean <br>
 * 
 * @version: 1.0, May 31, 2011 <br>
 *****************************************************************************/

public class HetUsageBean extends BaseInputBean {
	private BigDecimal	amountRemaining;
	private String		application;
	private BigDecimal	applicationId;
	private String		applicationMethod;
	private BigDecimal	buildingId;
	private String		buildingName;
	private BigDecimal	cartId;
	private String		catPartNo;
	private Date		checkedOut;
	private String		companyId;
	private String		containerId;
	private String		containerType;
	private String		controlDevice;
	private String		deletionCode;
	private boolean		discarded;
	private String		employee;
	private boolean		exported;
	private Date		exportedDate;
	private String		facilityId;
	private Date		insertDate;
	private BigDecimal	itemId;
	private BigDecimal	kitId;
	private String		loggerName;
	private BigDecimal	materialId;
	private String		method;
	private String		methodCode;
	private boolean		modified;
	private Date		modifiedDate;
	private String		msdsNo;
	private String		packaging;
	private boolean		painted;
	private String		partType;
	private String		permit;
	private BigDecimal	permitId;
	private BigDecimal	quantity;
	private String		remarks;
	private String		reportingEntityId;
	private boolean		standalone				= false;
	private String		substrate;
	private String		substrateCode;
	private String		tradeName;
	private BigDecimal	transactionId;
	private String		unitOfMeasure;
	private Date		usageDate;
	private BigDecimal	usageId;
	private BigDecimal	userId;

	private boolean		validCheckInDate		= true;
	private boolean		validContainer			= true;
	private boolean		validEmployee			= true;
	private boolean		validNonEmptyContainer	= true;
	private boolean		validQuantity			= true;
	private boolean		validUnitOfMeasure		= true;
	private boolean		validConversion			= true;

	// constructor
	public HetUsageBean() {
	}

	public HetUsageBean(ActionForm inputForm, Locale locale) {
		super(inputForm, locale);
	}

	public BigDecimal getAmountRemaining() {
		return amountRemaining;
	}

	public String getApplication() {
		return application;
	}

	public BigDecimal getApplicationId() {
		return applicationId;
	}

	public String getApplicationMethod() {
		return applicationMethod;
	}

	public BigDecimal getBuildingId() {
		return buildingId;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public BigDecimal getCartId() {
		return cartId;
	}

	public String getCatPartNo() {
		return catPartNo;
	}

	public Date getCheckedOut() {
		return checkedOut;
	}

	// getters
	public String getCompanyId() {
		return companyId;
	}

	public String getContainerId() {
		return containerId;
	}

	public String getContainerType() {
		return containerType;
	}

	public String getControlDevice() {
		return controlDevice;
	}

	public String getDeletionCode() {
		return deletionCode;
	}

	public String getEmployee() {
		return employee;
	}

	public Date getExportedDate() {
		return exportedDate;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public BigDecimal getKitId() {
		return kitId;
	}

	public String getLoggerName() {
		return loggerName;
	}

	public BigDecimal getMaterialId() {
		return materialId;
	}

	public String getMethod() {
		return method;
	}

	public String getMethodCode() {
		return methodCode;
	}

	public boolean getModified() {
		return modified;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public String getMsdsNo() {
		return msdsNo;
	}

	public String getPackaging() {
		return packaging;
	}

	public String getPartType() {
		return partType;
	}

	public String getPermit() {
		return permit;
	}

	public BigDecimal getPermitId() {
		return permitId;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public String getRemarks() {
		return remarks;
	}

	public String getReportingEntityId() {
		return reportingEntityId;
	}

	public String getSubstrate() {
		return "SELECT".equals(substrate) ? "" : substrate;
	}

	public String getSubstrateCode() {
		return substrateCode;
	}

	public String getTradeName() {
		return tradeName;
	}

	public BigDecimal getTransactionId() {
		return transactionId;
	}

	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public Date getUsageDate() {
		return usageDate;
	}

	public BigDecimal getUsageId() {
		return usageId;
	}

	public BigDecimal getUserId() {
		return userId;
	}

	public boolean hasContainer() {
		return !StringHandler.isBlankString(containerId);
	}

	public boolean hasContentLeft() {
		return amountRemaining != null && amountRemaining.compareTo(BigDecimal.ZERO) == 1;
	}

	public boolean hasEmployee() {
		return !StringHandler.isBlankString(employee);
	}

	public boolean isCheckInDateInRange() {
		return new Date().compareTo(usageDate) >= 0 && checkedOut.compareTo(usageDate) <= 0;
	}

	public boolean isDeleted() {
		return !StringHandler.isBlankString(deletionCode);
	}

	public boolean isDiscarded() {
		return discarded;
	}

	public boolean isExported() {
		return exported;
	}

	public boolean isNonHaasPurchased() {
		return !StringHandler.isBlankString(containerId) && containerId.startsWith("LMCO");
	}

	public boolean isPainted() {
		return painted;
	}

	public boolean isStandalone() {
		return standalone;
	}

	public boolean isUsageForCart() {
		return cartId != null;
	}

	public boolean isUsageForKit() {
		return kitId != null;
	}

	public boolean isUsageUnathorized() {
		return "NA".equals(applicationMethod) || "NA".equals(substrate) || "Unauthorized".equals(permit);
	}

	public boolean isValidCheckInDate() {
		return validCheckInDate;
	}

	public boolean isValidContainer() {
		return validContainer;
	}

	public boolean isValidEmployee() {
		return validEmployee;
	}

	public boolean isValidNonEmptyContainer() {
		return validNonEmptyContainer;
	}

	public boolean isValidQuantity() {
		return validQuantity;
	}

	public boolean isValidUnitOfMeasure() {
		return validUnitOfMeasure;
	}

	public void setAmountRemaining(BigDecimal quantityRemaining) {
		this.amountRemaining = quantityRemaining;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public void setApplicationId(BigDecimal applicationId) {
		this.applicationId = applicationId;
	}

	public void setApplicationMethod(String applicationMethod) {
		this.applicationMethod = applicationMethod;
	}

	public void setBuildingId(BigDecimal buildingId) {
		this.buildingId = buildingId;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	public void setCartId(BigDecimal recipeId) {
		this.cartId = recipeId;
	}

	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}

	public void setCheckedOut(Date checkedOut) {
		this.checkedOut = checkedOut;
	}

	// setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setContainerId(String containerId) {
		this.containerId = containerId;
	}

	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

	public void setControlDevice(String controlDevice) {
		this.controlDevice = controlDevice;
	}

	public void setDeletionCode(String deletionCode) {
		this.deletionCode = deletionCode;
	}

	public void setDiscarded(boolean discarded) {
		this.discarded = discarded;
	}

	public void setEmployee(String employee) {
		this.employee = employee;
	}

	public void setExported(boolean exported) {
		this.exported = exported;
	}

	public void setExportedDate(Date exportedDate) {
		this.exportedDate = exportedDate;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	@Override
	public void setHiddenFormFields() {
		// TODO Auto-generated method stub

	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public void setKitId(BigDecimal kitStart) {
		this.kitId = kitStart;
	}

	public void setLoggerName(String loggerName) {
		this.loggerName = loggerName;
	}

	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public void setMethodCode(String methodCode) {
		this.methodCode = methodCode;
	}

	public void setModified(boolean modified) {
		this.modified = modified;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public void setMsdsNo(String msdsNo) {
		this.msdsNo = msdsNo;
	}

	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}

	public void setPainted(boolean painted) {
		this.painted = painted;
	}

	public void setPartType(String partType) {
		this.partType = partType;
	}

	public void setPermit(String permit) {
		this.permit = permit;
	}

	public void setPermitId(BigDecimal permitId) {
		this.permitId = permitId;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setReportingEntityId(String reportingEntity) {
		this.reportingEntityId = reportingEntity;
	}

	public void setStandalone(boolean standalone) {
		this.standalone = standalone;
	}

	public void setSubstrate(String substrate) {
		this.substrate = substrate;
	}

	public void setSubstrateCode(String substrateCode) {
		this.substrateCode = substrateCode;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	public void setTransactionId(BigDecimal transactionId) {
		this.transactionId = transactionId;
	}

	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	public void setUsageDate(Date usageDate) {
		this.usageDate = usageDate;
	}

	public void setUsageId(BigDecimal usageId) {
		this.usageId = usageId;
	}

	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}

	public void setValidCheckInDate(boolean validCheckInDate) {
		this.validCheckInDate = validCheckInDate;
	}

	public void setValidContainer(boolean validContainer) {
		this.validContainer = validContainer;
	}

	public void setValidEmployee(boolean validEmployee) {
		this.validEmployee = validEmployee;
	}

	public void setValidNonEmptyContainer(boolean validNonEmptyContainer) {
		this.validNonEmptyContainer = validNonEmptyContainer;
	}

	public void setValidQuantity(boolean validQuantity) {
		this.validQuantity = validQuantity;
	}

	public void setValidUnitOfMeasure(boolean validUnitOfMeasure) {
		this.validUnitOfMeasure = validUnitOfMeasure;
	}

	public boolean isValidConversion() {
		return validConversion;
	}

	public void setValidConversion(boolean validConversion) {
		this.validConversion = validConversion;
	}
}