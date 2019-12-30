package com.tcmis.client.het.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.StringHandler;

public class HetContainerInventoryViewBean extends BaseDataBean {

	private BigDecimal	amountRemaining;
	private String		application;
	private String		applicationDesc;
	private BigDecimal	applicationId;
	private String		cartName;
	private String		cartStatus;
	private String		catPartNo;
	private String		companyId;
	private String		containerId;
	private String		containerType;
	private BigDecimal	daysToExpire;
	private String		deletionCode;
	private String		deletionDesc;
	private String		description;
	private boolean		diluent;
	private Date		expirationDate;
	private String		facilityId;
	private String		itemDesc;
	private BigDecimal	itemId;
	private BigDecimal	kitId;
	private String		location;
	private String		materialDesc;
	private BigDecimal	materialId;
	private String		mfgLot;
	private String		msdsNumber;
	private String		okDoUpdate;
	private String		packaging;
	private String		reportingEntityId;
	private boolean		solvent;
	private String		unitOfMeasure;
	private boolean		usageLogged;

	// constructor
	public HetContainerInventoryViewBean() {
	}

	public BigDecimal getAmountRemaining() {
		return amountRemaining;
	}

	public String getApplication() {
		return application;
	}

	public String getApplicationDesc() {
		return applicationDesc;
	}

	public BigDecimal getApplicationId() {
		return applicationId;
	}

	public String getCartName() {
		return cartName;
	}

	public String getCartStatus() {
		return cartStatus;
	}

	public String getCatPartNo() {
		return catPartNo;
	}

	public String getCompanyId() {
		return companyId;
	}

	public String getContainerId() {
		return containerId;
	}

	public String getContainerType() {
		return containerType;
	}

	public BigDecimal getDaysToExpire() {
		return daysToExpire;
	}

	public String getDeletionCode() {
		return deletionCode;
	}

	public String getDeletionDesc() {
		return deletionDesc;
	}

	public String getDescription() {
		return description;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public BigDecimal getKitId() {
		return kitId;
	}

	public String getLocation() {
		return location;
	}

	public String getMaterialDesc() {
		return materialDesc;
	}

	public BigDecimal getMaterialId() {
		return materialId;
	}

	public String getMfgLot() {
		return mfgLot;
	}

	public String getMsdsNumber() {
		return msdsNumber;
	}

	public String getOkDoUpdate() {
		return okDoUpdate;
	}

	public String getPackaging() {
		return packaging;
	}

	public String getReportingEntityId() {
		return reportingEntityId;
	}

	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public boolean isCartCheckedOut() {
		return "A".equals(cartStatus);
	}

	public boolean isDiluent() {
		return diluent;
	}

	public boolean isOnCart() {
		return !StringHandler.isBlankString(cartName);
	}

	public boolean isSolvent() {
		return solvent;
	}

	public boolean isUsageLogged() {
		return usageLogged;
	}

	public void setAmountRemaining(BigDecimal amountRemaining) {
		this.amountRemaining = amountRemaining;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public void setApplicationDesc(String applicationDesc) {
		this.applicationDesc = applicationDesc;
	}

	public void setApplicationId(BigDecimal applicationId) {
		this.applicationId = applicationId;
	}

	public void setCartName(String cartName) {
		this.cartName = cartName;
	}

	public void setCartStatus(String cartStatus) {
		this.cartStatus = cartStatus;
	}

	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setContainerId(String containerId) {
		this.containerId = containerId;
	}

	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

	public void setDaysToExpire(BigDecimal daysToExpire) {
		this.daysToExpire = daysToExpire;
	}

	public void setDeletionCode(String deletionCode) {
		this.deletionCode = deletionCode;
	}

	public void setDeletionDesc(String deletionDesc) {
		this.deletionDesc = deletionDesc;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDiluent(boolean diluent) {
		this.diluent = diluent;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public void setKitId(BigDecimal kitId) {
		this.kitId = kitId;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}

	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}

	public void setMfgLot(String mfgLot) {
		this.mfgLot = mfgLot;
	}

	public void setMsdsNumber(String msdsNumber) {
		this.msdsNumber = msdsNumber;
	}

	public void setOkDoUpdate(String okDoUpdate) {
		this.okDoUpdate = okDoUpdate;
	}

	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}

	public void setReportingEntityId(String reportingEntityId) {
		this.reportingEntityId = reportingEntityId;
	}

	public void setSolvent(boolean solvent) {
		this.solvent = solvent;
	}

	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	public void setUsageLogged(boolean usageLogged) {
		this.usageLogged = usageLogged;
	}

}
