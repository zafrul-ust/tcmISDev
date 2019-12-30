package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.util.Locale;

import org.apache.struts.action.ActionForm;

import com.tcmis.common.framework.BaseInputBean;

public class CatalogRQCEditShelfLifeBean extends BaseInputBean {

	private String uAction;
	private BigDecimal itemId;
	private BigDecimal partId;
	private BigDecimal hub;
	private String inventoryGroup;
	private String catPartNo;
	private String storageTemp;
	private String shelfLifeBasis;
	private String shelfLifeBasisDesc;
	private String jspLabel;
	private BigDecimal shelfLifeDays;
	private String source;
	private String inseparableKit;
	private String comments;
	private String caller;
	private boolean applyToAllDist;
	private String labelColor;
	private String companyId;
	private String catalogId;
	private String catalogCompanyId;
	private String facilityId;
	private String customerTemperatureId;
	
	public CatalogRQCEditShelfLifeBean() {
		super();
	}
	
	public CatalogRQCEditShelfLifeBean(ActionForm inputForm, Locale locale) {
		super(inputForm, locale);
	}
	
	public CatalogRQCEditShelfLifeBean(ActionForm inputForm, Locale locale, String dateMask) {
		super(inputForm, locale, dateMask);
	}
	
	public boolean isUpdate() {
		return "update".equals(uAction);
	}
	
	public String getuAction() {
		return uAction;
	}

	public void setuAction(String uAction) {
		this.uAction = uAction;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public BigDecimal getPartId() {
		return partId;
	}

	public void setPartId(BigDecimal partId) {
		this.partId = partId;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public String getStorageTemp() {
		return storageTemp;
	}

	public void setStorageTemp(String storageTemp) {
		this.storageTemp = storageTemp;
	}

	public String getShelfLifeBasis() {
		return shelfLifeBasis;
	}

	public void setShelfLifeBasis(String shelfLifeBasis) {
		this.shelfLifeBasis = shelfLifeBasis;
	}

	public String getShelfLifeBasisDesc() {
		return shelfLifeBasisDesc;
	}

	public void setShelfLifeBasisDesc(String shelfLifeBasisDesc) {
		this.shelfLifeBasisDesc = shelfLifeBasisDesc;
	}

	public BigDecimal getShelfLifeDays() {
		return shelfLifeDays;
	}

	public void setShelfLifeDays(BigDecimal shelfLifeDays) {
		this.shelfLifeDays = shelfLifeDays;
	}

	public String getCatPartNo() {
		return catPartNo;
	}

	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}

	public String getInseparableKit() {
		return inseparableKit;
	}

	public void setInseparableKit(String inseparableKit) {
		this.inseparableKit = inseparableKit;
	}

	public String getJspLabel() {
		return jspLabel;
	}

	public void setJspLabel(String jspLabel) {
		this.jspLabel = jspLabel;
	}

	public BigDecimal getHub() {
		return hub;
	}

	public void setHub(BigDecimal hub) {
		this.hub = hub;
	}
	
	public String getSource() {
		return source;
	}
	
	public void setSource(String source) {
		this.source = source;
	}
	
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getCaller() {
		return caller;
	}

	public void setCaller(String caller) {
		this.caller = caller;
	}

	public boolean isApplyToAllDist() {
		return applyToAllDist;
	}

	public void setApplyToAllDist(boolean applyToAllDist) {
		this.applyToAllDist = applyToAllDist;
	}

	public String getLabelColor() {
		return labelColor;
	}

	public void setLabelColor(String labelColor) {
		this.labelColor = labelColor;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
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

	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public String getCustomerTemperatureId() {
		return customerTemperatureId;
	}

	public void setCustomerTemperatureId(String customerTemperatureId) {
		this.customerTemperatureId = customerTemperatureId;
	}

	@Override
	public void setHiddenFormFields() {
		
	}
}
