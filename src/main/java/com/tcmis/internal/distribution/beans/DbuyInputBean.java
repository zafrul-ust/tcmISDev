package com.tcmis.internal.distribution.beans;

import static com.tcmis.common.web.IHaasConstants.CST_OPTION_SAVE;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.tcmis.common.framework.BaseInputBean;

/******************************************************************************
 * CLASSNAME: HubCabinetViewBean <br>
 * 
 * @version: 1.0, Jul 22, 2004 <br>
 *****************************************************************************/

public class DbuyInputBean extends BaseInputBean {

	private String		companyId;
	private Date		dateInsertedFromDate;
	private Date		dateInsertedToDate;
	private Date		expireDate;
	private String		hub;
	private String		inventoryGroup;
	private String		opsEntityId;
	private BigDecimal	resendPoNo;
	private String		searchArgument;
	private String		searchField;
	private String		searchMode;
	private String		showConfirmedPOs;
	private String		showExpired;
	private String		showPriceHistory;
	private String		supplier;
	private String		supplyPath;

	// constructor
	public DbuyInputBean() {
	}

	public String getCompanyId() {
		return companyId;
	}

	public Date getDateInsertedFromDate() {
		return dateInsertedFromDate;
	}

	public Date getDateInsertedToDate() {
		return dateInsertedToDate;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public String getHub() {
		return hub;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public String getOpsEntityId() {
		return opsEntityId;
	}

	public BigDecimal getResendPoNo() {
		return resendPoNo;
	}

	public String getSearchArgument() {
		return searchArgument;
	}

	public String getSearchField() {
		return searchField;
	}

	public String getSearchMode() {
		return searchMode;
	}

	public String getShowConfirmedPOs() {
		return showConfirmedPOs;
	}

	public String getShowExpired() {
		return showExpired;
	}

	public String getShowPriceHistory() {
		return showPriceHistory;
	}

	public String getSupplier() {
		return supplier;
	}

	public String getSupplyPath() {
		return this.supplyPath;
	}

	public boolean hasSupplyPath() {
		return StringUtils.isNotBlank(supplyPath) && !"ALL".equals(supplyPath);
	}
	
	public boolean isCreateInsertTemplate() {
		return "createInsertTemplate".equals(getuAction());
	}

	public boolean isCreateUpdateTemplate() {
		return "createUpdateTemplate".equals(getuAction());
	}

	public boolean isEditData() {
		return "editData".equals(getuAction());
	}

	public boolean isEditSupplementaryData() {
		return "editsupplimentorydata".equals(getuAction()) || "editsupplementarydata".equals(getuAction());
	}

	public boolean isGetPriority() {
		return "getPriority".equals(getuAction());
	}

	public boolean isLoadData() {
		return "loaddata".equals(getuAction());
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setDateInsertedFromDate(Date dateInsertedFromDate) {
		this.dateInsertedFromDate = dateInsertedFromDate;
	}

	public void setDateInsertedToDate(Date dateInsertedToDate) {
		this.dateInsertedToDate = dateInsertedToDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	@Override
	public void setHiddenFormFields() {
		// TODO Auto-generated method stub

	}

	public void setHub(String hub) {
		this.hub = hub;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}

	public void setResendPoNo(BigDecimal resendPoNo) {
		this.resendPoNo = resendPoNo;
	}

	public void setSearchArgument(String searchArgument) {
		this.searchArgument = searchArgument;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public void setSearchMode(String searchMode) {
		this.searchMode = searchMode;
	}

	public void setShowConfirmedPOs(String showConfirmedPOs) {
		this.showConfirmedPOs = showConfirmedPOs;
	}

	public void setShowExpired(String showExpired) {
		this.showExpired = showExpired;
	}

	public void setShowPriceHistory(String showPriceHistory) {
		this.showPriceHistory = showPriceHistory;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public void setSupplyPath(String supplyPath) {
		this.supplyPath = supplyPath;
	}

}