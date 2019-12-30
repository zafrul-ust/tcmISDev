package com.tcmis.supplier.shipping.beans;

import com.tcmis.common.framework.BaseInputBean;
import com.tcmis.common.util.BeanHandler;
import org.apache.struts.action.ActionForm;

import java.util.Date;
import java.util.Locale;

public class InventoryManagementInputBean extends BaseInputBean {
	private Boolean editMinMaxPermission;
    private String includeHistory;
    private String gfpPartsOnly;
    private String supplier;
    private String supplierName;
    private String shipFromLocationId;
    private String locationDesc;
    private String searchMode;
    private String searchModeName;
    private String searchField;
    private String searchFieldName;
    private String searchArgument;
    private String status;
    private Date transactionFromDate;
    private Date transactionToDate;
    private String transactionType;
    
    public InventoryManagementInputBean() {}
    
	public InventoryManagementInputBean(ActionForm form, Locale locale) throws Exception {
		try {
			BeanHandler.copyAttributes(form, this, locale);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void setHiddenFormFields() {
		addHiddenFormField("supplier");
		addHiddenFormField("supplierName");
		addHiddenFormField("shipFromLocationId");
		addHiddenFormField("locationDesc");
		addHiddenFormField("searchMode");
		addHiddenFormField("searchField");
		addHiddenFormField("searchArgument");
		addHiddenFormField("gfpPartsOnly");
		addHiddenFormField("transactionFromDate");
		addHiddenFormField("transactionToDate");
		addHiddenFormField("includeHistory");
		addHiddenFormField("status");
		addHiddenFormField("transactionType");
	}
	
	public boolean isAddPartUpdateMinMax() {
		return "addPartUpdateMinMax".equalsIgnoreCase(uAction);
	}
	
	public boolean isUpdateInsertInventory() {
		return "updateInsertInventory".equalsIgnoreCase(uAction);
	}
	
	public boolean isUpdateInsertPO() {
		return "updateInsertPO".equalsIgnoreCase(uAction);
	}
	
	public boolean isAddAdjustment() {
		return "addAdjustment".equalsIgnoreCase(uAction);
	}
	
	public boolean isPartAjaxSearch() {
		return "partAjaxSearch".equalsIgnoreCase(uAction);
	}
	
	public boolean isShowMinMaxHistory() {
		return "showMinMaxHistory".equalsIgnoreCase(uAction);
	}
	
	public boolean isPrintInventoryLabel() {
		return "printInventoryLabel".equalsIgnoreCase(uAction);
	}
	
	public boolean isBinAjaxSearch() {
		return "binAjaxSearch".equalsIgnoreCase(uAction);
	}
	
	public boolean isUpdateInsertBin() {
		return "updateInsertBin".equalsIgnoreCase(uAction);
	}
	
	public boolean isConvertiblePartAjaxSearch() {
		return "convertiblePartAjaxSearch".equalsIgnoreCase(uAction);
	}
	
	public boolean isConvertTransaction() {
		return "convertTransaction".equalsIgnoreCase(uAction);
	}
	
	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getShipFromLocationId() {
		return shipFromLocationId;
	}

	public void setShipFromLocationId(String shipFromLocationId) {
		this.shipFromLocationId = shipFromLocationId;
	}

	public String getLocationDesc() {
		return locationDesc;
	}

	public void setLocationDesc(String locationDesc) {
		this.locationDesc = locationDesc;
	}

	public String getSearchMode() {
		return searchMode;
	}

	public void setSearchMode(String searchMode) {
		this.searchMode = searchMode;
	}

	public String getSearchModeName() {
		return searchModeName;
	}

	public void setSearchModeName(String searchModeName) {
		this.searchModeName = searchModeName;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String getSearchFieldName() {
		return searchFieldName;
	}

	public void setSearchFieldName(String searchFieldName) {
		this.searchFieldName = searchFieldName;
	}

	public String getSearchArgument() {
		return searchArgument;
	}

	public void setSearchArgument(String searchArgument) {
		this.searchArgument = searchArgument;
	}

	public Date getTransactionFromDate() {
		return transactionFromDate;
	}

	public void setTransactionFromDate(Date transactionFromDate) {
		this.transactionFromDate = transactionFromDate;
	}

	public Date getTransactionToDate() {
		return transactionToDate;
	}

	public void setTransactionToDate(Date transactionToDate) {
		this.transactionToDate = transactionToDate;
	}

	public String getIncludeHistory() {
		return includeHistory;
	}

	public void setIncludeHistory(String includeHistory) {
		this.includeHistory = includeHistory;
	}

	public String getGfpPartsOnly() {
		return gfpPartsOnly;
	}

	public void setGfpPartsOnly(String gfpPartsOnly) {
		this.gfpPartsOnly = gfpPartsOnly;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public Boolean getEditMinMaxPermission() {
		return editMinMaxPermission;
	}

	public void setEditMinMaxPermission(Boolean editMinMaxPermission) {
		this.editMinMaxPermission = editMinMaxPermission;
	}
}