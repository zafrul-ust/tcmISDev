package com.tcmis.internal.catalog.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

@SuppressWarnings("serial")
public class MfrNotificationCategoryBean extends BaseDataBean {

	private final String ITEM_SEARCH = "item";
	private boolean itemData;
	private boolean manufacturerData;
	
	private final String MATERIAL_SEARCH = "material";
	private boolean materialData;
	private final String MFR_SEARCH = "mfr";
	private String mfrReqCategoryDesc;
	private BigDecimal mfrReqCategoryId;
	private String searchCriteria;
	private boolean selected;
	private String status;
	
	public String getMfrReqCategoryDesc() {
		return mfrReqCategoryDesc;
	}

	public BigDecimal getMfrReqCategoryId() {
		return mfrReqCategoryId;
	}

	public String getSearchCriteria() {
		return searchCriteria;
	}

	public String getStatus() {
		return status;
	}

	public boolean isFormulationChange() {
		return mfrReqCategoryId.intValue() == 9;
	}

	public boolean isItemData() {
		return itemData;
	}

	public boolean isItemDiscontinuation() {
		return mfrReqCategoryId.intValue() == 10;
	}

	public boolean isItemSearch() {
		return ITEM_SEARCH.equals(searchCriteria);
	}

	public boolean isManufacturerData() {
		return manufacturerData;
	}

	public boolean isMaterialData() {
		return materialData;
	}

	public boolean isMaterialDiscontinuation() {
		return mfrReqCategoryId.intValue() == 4;
	}

	public boolean isMaterialSearch() {
		return MATERIAL_SEARCH.equals(searchCriteria);
	}

	public boolean isMfrAcquisition() {
		return mfrReqCategoryId.intValue() == 1;
	}

	public boolean isMfrLocationChange() {
		return mfrReqCategoryId.intValue() == 3;
	}

	public boolean isMfrOutOfBusiness() {
		return mfrReqCategoryId.intValue() == 2;
	}

	public boolean isMfrProductCodeChange() {
		return mfrReqCategoryId.intValue() == 7;
	}

	public boolean isMfrSearch() {
		return MFR_SEARCH.equals(searchCriteria);
	}
	
	public boolean isProductFYINotice() {
		return mfrReqCategoryId.intValue() == 12;
	}
	
	public boolean isRawMaterialChange() {
		return mfrReqCategoryId.intValue() == 6;
	}
	
	public boolean isRebrandedProduct() {
		return mfrReqCategoryId.intValue() == 5;
	}
	
	public boolean isObsolescence() {
		return isMfrOutOfBusiness()
				|| isMaterialDiscontinuation()
				|| isItemDiscontinuation()
				|| isFormulationChange();
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	public boolean isSLSTUpdate() {
		return mfrReqCategoryId.intValue() == 11;
	}
	
	public boolean isSpecChange() {
		return mfrReqCategoryId.intValue() == 8;
	}
	
	public void setItemData(boolean itemData) {
		this.itemData = itemData;
	}
	
	public void setManufacturerData(boolean manufacturerData) {
		this.manufacturerData = manufacturerData;
	}
	
	public void setMaterialData(boolean materialData) {
		this.materialData = materialData;
	}
	
	public void setMfrReqCategoryDesc(String mfrReqCategoryDesc) {
		this.mfrReqCategoryDesc = mfrReqCategoryDesc;
	}
	
	public void setMfrReqCategoryId(BigDecimal mfrReqCategoryId) {
		this.mfrReqCategoryId = mfrReqCategoryId;
	}
	
	public void setSearchCriteria(String searchCriteria) {
		this.searchCriteria = searchCriteria;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
}
