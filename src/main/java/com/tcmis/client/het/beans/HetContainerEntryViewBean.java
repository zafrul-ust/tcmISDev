package com.tcmis.client.het.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.StringHandler;

public class HetContainerEntryViewBean extends BaseDataBean {

	private BigDecimal	amountRemaining;
	private BigDecimal	applicationId;
	private BigDecimal	buildingId;
	private String		catalogCompanyId;
	private String		catalogId;
	private String		catPartNo;
	private String		componentMsdsNo	= "";
	private String		containerId;
	private String		containerSize;
	private String		containerType;
	private String		custMsdsNo		= "";
	private String		customerMsdsDb;
	private String		defaultApplicationMethodCod;
	private String		defaultPartType;
	private String		defaultPermitId;
	private String		defaultSubstrateCode;
	private boolean		diluent;
	private Date		expirationDate;
	private String		hetUsageRecording;
	private BigDecimal	itemId;
	private BigDecimal	kitId;
	private BigDecimal	kitStart;
	private String		manufacturer	= "";
	private String		materialDesc;
	private BigDecimal	materialId;
	private BigDecimal	partId;
	private BigDecimal	quantity;
	private boolean		solvent;
	private String		tradeName		= "";
	private String		unitOfMeasure;

	// constructor
	public HetContainerEntryViewBean() {
	}

	public BigDecimal getAmountRemaining() {
		return amountRemaining;
	}

	public BigDecimal getApplicationId() {
		return applicationId;
	}

	public BigDecimal getBuildingId() {
		return buildingId;
	}

	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public String getCatPartNo() {
		return catPartNo;
	}

	public String getComponentMsdsNo() {
		return componentMsdsNo;
	}

	public String getContainerId() {
		return containerId;
	}

	public String getContainerSize() {
		return containerSize;
	}

	public String getContainerType() {
		return containerType;
	}

	public String getCustMsdsNo() {
		return custMsdsNo;
	}

	public String getCustomerMsdsDb() {
		return customerMsdsDb;
	}

	public String getDefaultApplicationMethodCod() {
		return defaultApplicationMethodCod;
	}

	public String getDefaultPartType() {
		return defaultPartType;
	}

	public String getDefaultPermitId() {
		return defaultPermitId;
	}

	public String getDefaultSubstrateCode() {
		return defaultSubstrateCode;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public String getFormattedExpirationDate() {
		return expirationDate == null ? "Indefinite" : DateHandler.formatDate(expirationDate, "yyyy-MM-dd");
	}

	public String getHetUsageRecording() {
		return hetUsageRecording;
	}

	// getters
	public BigDecimal getItemId() {
		return itemId;
	}

	public BigDecimal getKitId() {
		return kitId;
	}

	public BigDecimal getKitStart() {
		return kitStart;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public String getMaterialDesc() {
		return materialDesc;
	}

	public BigDecimal getMaterialId() {
		return materialId;
	}

	public BigDecimal getPartId() {
		return partId;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public String getReceiptId() {
		return StringHandler.isBlankString(containerId) ? "" : containerId.substring(0, containerId.indexOf("-"));
	}

	public String getTradeName() {
		return tradeName;
	}

	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public boolean hasContainerId() {
		return !StringHandler.isBlankString(containerId);
	}

	public boolean isDiluent() {
		return diluent;
	}

	public boolean isNonHaasPurchased() {
		return !StringHandler.isBlankString(containerId) && containerId.startsWith("LMCO");
	}

	public boolean isNonHasPurchasedKit() {
		return !StringHandler.isBlankString(componentMsdsNo) && !custMsdsNo.equals(componentMsdsNo);
	}

	public boolean isSolvent() {
		return solvent;
	}

	public boolean isUsageLoggingRequired() {
		return "Y".equals(hetUsageRecording) || "Daily Usage".equalsIgnoreCase(hetUsageRecording);
	}

	public void setAmountRemaining(BigDecimal amountRemaining) {
		this.amountRemaining = amountRemaining;
	}

	public void setApplicationId(BigDecimal applicationId) {
		this.applicationId = applicationId;
	}

	public void setBuildingId(BigDecimal buildingId) {
		this.buildingId = buildingId;
	}

	public void setCatalogCompanyId(String catalogCompanyId) {
		this.catalogCompanyId = catalogCompanyId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}

	public void setComponentMsdsNo(String componentMsdsNo) {
		this.componentMsdsNo = componentMsdsNo;
	}

	// setters
	public void setContainerId(String containerId) {
		this.containerId = containerId;
	}

	public void setContainerSize(String containerSize) {
		this.containerSize = containerSize;
	}

	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

	public void setCustMsdsNo(String custMsdsNo) {
		this.custMsdsNo = custMsdsNo;
	}

	public void setCustomerMsdsDb(String customerMsdsDb) {
		this.customerMsdsDb = customerMsdsDb;
	}

	public void setDefaultApplicationMethodCod(String defaultApplicationMethodCod) {
		this.defaultApplicationMethodCod = defaultApplicationMethodCod;
	}

	public void setDefaultPartType(String defaultPartType) {
		this.defaultPartType = defaultPartType;
	}

	public void setDefaultPermitId(String defaultPermitId) {
		this.defaultPermitId = defaultPermitId;
	}

	public void setDefaultSubstrateCode(String defaultSubstrateCode) {
		this.defaultSubstrateCode = defaultSubstrateCode;
	}

	public void setDiluent(boolean diluent) {
		this.diluent = diluent;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public void setHetUsageRecording(String usageLoggingReq) {
		this.hetUsageRecording = usageLoggingReq;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public void setKitId(BigDecimal kitId) {
		this.kitId = kitId;
	}

	public void setKitStart(BigDecimal kitStart) {
		this.kitStart = kitStart;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}

	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}

	public void setPartId(BigDecimal partId) {
		this.partId = partId;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public void setSolvent(boolean solvent) {
		this.solvent = solvent;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

}
