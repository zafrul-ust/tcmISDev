package com.tcmis.client.het.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: HetUsageAuditBean <br>
 * @version: 1.0, Jul 28, 2011 <br>
 *****************************************************************************/

public class HetUsageAuditViewBean extends BaseDataBean {

	private BigDecimal amountRemaining;
	private String applicationDesc;
	private BigDecimal applicationId;
	private String applicationMethod;
	private BigDecimal cartId;
	private String catPartNo;
	private String companyId;
	private String containerId;
	private String controlDevice;
	private String deletionCode;
	private boolean discarded;
	private String employee;
	private boolean exported;
	private String facilityId;
	private Date insertDate;
	private BigDecimal itemId;
	private String loggerName;
	private boolean modified;
	private Date modifiedDate;
	private String msdsNo;
	private boolean painted;
	private BigDecimal partId;
	private String partType;
	private String permit;
	private BigDecimal quantity;
	private BigDecimal recipeId;
	private String remarks;
	private String reportingEntityId;
	private String substrate;
	private BigDecimal transactionId;
	private String unitOfMeasure;
	private Date usageDate;
	private BigDecimal usageId;
	private BigDecimal userId;

	//constructor
	public HetUsageAuditViewBean() {
	}
	
	public BigDecimal getAmountRemaining() {
		return amountRemaining;
	}

	public String getApplicationDesc() {
		return applicationDesc;
	}

	public BigDecimal getApplicationId() {
		return applicationId;
	}

	public String getApplicationMethod() {
		return applicationMethod;
	}

	public BigDecimal getCartId() {
		return cartId;
	}

	public String getCatPartNo() {
		return catPartNo;
	}

	//getters
	public String getCompanyId() {
		return companyId;
	}

	public String getContainerId() {
		return containerId;
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

	public boolean getExported() {
		return exported;
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

	public String getLoggerName() {
		return loggerName;
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

	public BigDecimal getPartId() {
		return partId;
	}

	public String getPartType() {
		return partType;
	}

	public String getPermit() {
		return permit;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public BigDecimal getRecipeId() {
		return recipeId;
	}

	public String getRemarks() {
		return remarks;
	}

	public String getReportingEntityId() {
		return reportingEntityId;
	}

	public String getSubstrate() {
		return substrate;
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

	public boolean isDiscarded() {
		return discarded;
	}
	
	public boolean isPainted() {
		return painted;
	}
	
	public void setAmountRemaining(BigDecimal amountRemaining) {
		this.amountRemaining = amountRemaining;
	}
	
	public void setApplicationDesc(String application) {
		this.applicationDesc = application;
	}

	public void setApplicationId(BigDecimal applicationId) {
		this.applicationId = applicationId;
	}

	public void setApplicationMethod(String applicationMethod) {
		this.applicationMethod = applicationMethod;
	}

	public void setCartId(BigDecimal cartId) {
		this.cartId = cartId;
	}

	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setContainerId(String containerId) {
		this.containerId = containerId;
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

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public void setLoggerName(String loggerName) {
		this.loggerName = loggerName;
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

	public void setPainted(boolean painted) {
		this.painted = painted;
	}

	public void setPartId(BigDecimal partId) {
		this.partId = partId;
	}

	public void setPartType(String partType) {
		this.partType = partType;
	}

	public void setPermit(String permit) {
		this.permit = permit;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public void setRecipeId(BigDecimal recipeId) {
		this.recipeId = recipeId;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setReportingEntityId(String reportingEntityId) {
		this.reportingEntityId = reportingEntityId;
	}

	public void setSubstrate(String substrate) {
		this.substrate = substrate;
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
}