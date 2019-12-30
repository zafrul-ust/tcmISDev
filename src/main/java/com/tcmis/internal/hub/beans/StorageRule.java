package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

@SuppressWarnings("serial")
public class StorageRule extends BaseDataBean {

	private String USER_ACTION_SEARCH = "search";
	private String USER_ACTION_UPDATE = "update";
	private String USER_ACTION_XLS = "createXLS";
	
	private BigDecimal hubStorageRuleId;
	private BigDecimal ruleOrder;
	private String branchPlant;
	private String wmsAcidBase;
	private String wmsAerosol;
	private String wmsContainer;
	private String wmsCorrosive;
	private String wmsFlammable;
	private String wmsGas;
	private String wmsOrganicPeroxide;
	private String wmsOxidizer;
	private String wmsPyrophoric;
	private String wmsReactive;
	private String wmsStorageTemp;
	private String wmsToxic;
	private String wmsWaterMiscible;
	private String wmsWaterReactive;
	private String wmsPressureRelieving;
	private String ibc;
	private String detectMinSize;
	private BigDecimal minSize;
	private String detectMaxSize;
	private BigDecimal maxSize;
	private String sizeUnit;
	private String storageFamily;
	private String altStorageFamily;
	private String userAction;
	private boolean delete;
	private String updated;
	
	public BigDecimal getHubStorageRuleId() {
		return hubStorageRuleId;
	}
	public void setHubStorageRuleId(BigDecimal hubStorageRuleId) {
		this.hubStorageRuleId = hubStorageRuleId;
	}
	public BigDecimal getRuleOrder() {
		return ruleOrder;
	}
	public void setRuleOrder(BigDecimal ruleOrder) {
		this.ruleOrder = ruleOrder;
	}
	public String getBranchPlant() {
		return branchPlant;
	}
	public void setBranchPlant(String branchPlant) {
		this.branchPlant = branchPlant;
	}
	public String getWmsAcidBase() {
		return wmsAcidBase;
	}
	public void setWmsAcidBase(String wmsAcidBase) {
		this.wmsAcidBase = wmsAcidBase;
	}
	public String getWmsAerosol() {
		return wmsAerosol;
	}
	public void setWmsAerosol(String wmsAerosol) {
		this.wmsAerosol = wmsAerosol;
	}
	public String getWmsContainer() {
		return wmsContainer;
	}
	public void setWmsContainer(String wmsContainer) {
		this.wmsContainer = wmsContainer;
	}
	public String getWmsCorrosive() {
		return wmsCorrosive;
	}
	public void setWmsCorrosive(String wmsCorrosive) {
		this.wmsCorrosive = wmsCorrosive;
	}
	public String getWmsFlammable() {
		return wmsFlammable;
	}
	public void setWmsFlammable(String wmsFlammable) {
		this.wmsFlammable = wmsFlammable;
	}
	public String getWmsGas() {
		return wmsGas;
	}
	public void setWmsGas(String wmsGas) {
		this.wmsGas = wmsGas;
	}
	public String getWmsOrganicPeroxide() {
		return wmsOrganicPeroxide;
	}
	public void setWmsOrganicPeroxide(String wmsOrganicPeroxide) {
		this.wmsOrganicPeroxide = wmsOrganicPeroxide;
	}
	public String getWmsOxidizer() {
		return wmsOxidizer;
	}
	public void setWmsOxidizer(String wmsOxidizer) {
		this.wmsOxidizer = wmsOxidizer;
	}
	public String getWmsPyrophoric() {
		return wmsPyrophoric;
	}
	public void setWmsPyrophoric(String wmsPyrophoric) {
		this.wmsPyrophoric = wmsPyrophoric;
	}
	public String getWmsReactive() {
		return wmsReactive;
	}
	public void setWmsReactive(String wmsReactive) {
		this.wmsReactive = wmsReactive;
	}
	public String getWmsStorageTemp() {
		return wmsStorageTemp;
	}
	public void setWmsStorageTemp(String wmsStorageTemp) {
		this.wmsStorageTemp = wmsStorageTemp;
	}
	public String getWmsToxic() {
		return wmsToxic;
	}
	public void setWmsToxic(String wmsToxic) {
		this.wmsToxic = wmsToxic;
	}
	public String getWmsWaterMiscible() {
		return wmsWaterMiscible;
	}
	public void setWmsWaterMiscible(String wmsWaterMiscible) {
		this.wmsWaterMiscible = wmsWaterMiscible;
	}
	public String getWmsWaterReactive() {
		return wmsWaterReactive;
	}
	public void setWmsWaterReactive(String wmsWaterReactive) {
		this.wmsWaterReactive = wmsWaterReactive;
	}
	public String getWmsPressureRelieving() {
		return wmsPressureRelieving;
	}
	public void setWmsPressureRelieving(String wmsPressureRelieving) {
		this.wmsPressureRelieving = wmsPressureRelieving;
	}
	public String getIbc() {
		return ibc;
	}
	public void setIbc(String ibc) {
		this.ibc = ibc;
	}
	public String getDetectMinSize() {
		return detectMinSize;
	}
	public void setDetectMinSize(String detectMinSize) {
		this.detectMinSize = detectMinSize;
	}
	public BigDecimal getMinSize() {
		return minSize;
	}
	public void setMinSize(BigDecimal minSize) {
		this.minSize = minSize;
	}
	public String getDetectMaxSize() {
		return detectMaxSize;
	}
	public void setDetectMaxSize(String detectMaxSize) {
		this.detectMaxSize = detectMaxSize;
	}
	public BigDecimal getMaxSize() {
		return maxSize;
	}
	public void setMaxSize(BigDecimal maxSize) {
		this.maxSize = maxSize;
	}
	public String getSizeUnit() {
		return sizeUnit;
	}
	public void setSizeUnit(String sizeUnit) {
		this.sizeUnit = sizeUnit;
	}
	public String getStorageFamily() {
		return storageFamily;
	}
	public void setStorageFamily(String storageFamily) {
		this.storageFamily = storageFamily;
	}
	public String getAltStorageFamily() {
		return altStorageFamily;
	}
	public void setAltStorageFamily(String altStorageFamily) {
		this.altStorageFamily = altStorageFamily;
	}
	public void setUserAction(String userAction) {
		this.userAction = userAction;
	}
	public boolean isDelete() {
		return delete;
	}
	public void setDelete(boolean delete) {
		this.delete = delete;
	}
	public boolean isRuleUpdated() {
		return "Y".equals(updated);
	}
	public void setUpdated(String updated) {
		this.updated = updated;
	}
	public boolean isUserActionSearch() {
		return USER_ACTION_SEARCH.equalsIgnoreCase(userAction);
	}
	public boolean isUserActionUpdate() {
		return USER_ACTION_UPDATE.equalsIgnoreCase(userAction);
	}
	public boolean isCreateXLS() {
		return USER_ACTION_XLS.equalsIgnoreCase(userAction);
	}
}
