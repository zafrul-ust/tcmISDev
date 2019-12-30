package com.tcmis.internal.supply.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseInputBean;

/******************************************************************************
 * CLASSNAME: AssociatePrViewBean <br>
 * 
 * @version: 1.0, Mar 10, 2006 <br>
 *****************************************************************************/

public class EditAssociatedPrsInputBean extends BaseInputBean {
	private String hubName;
	private BigDecimal itemId;
	private BigDecimal lineId;
	private BigDecimal radianPo;
	private String shipToCompanyId;
	private String raytheonPo;
	private String shipToLocationId;
	private String sortBy;
	private BigDecimal buyerId;
	private BigDecimal mrNumber;
	private String searchWhat;
	private String searchType;
	private String searchText;
	private String supplyPath;
	private String companyId;
	private String inventoryGroup;
	private String fromPo;
	private String currencyId;
	private String attachedPr;
	private String userAction;

	// constructor
	public EditAssociatedPrsInputBean() {
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}

	public void setRaytheonPo(String raytheonPo) {
		this.raytheonPo = raytheonPo;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setMrNumber(BigDecimal mrNumber) {
		this.mrNumber = mrNumber;
	}

	public void setShipToLocationId(String shipToLocationId) {
		this.shipToLocationId = shipToLocationId;
	}

	public void setShipToCompanyId(String shipToCompanyId) {
		this.shipToCompanyId = shipToCompanyId;
	}

	public void setBuyerId(BigDecimal buyerId) {
		this.buyerId = buyerId;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public void setSupplyPath(String supplyPath) {
		this.supplyPath = supplyPath;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public void setSearchWhat(String searchWhat) {
		this.searchWhat = searchWhat;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	// getters
	public BigDecimal getItemId() {
		return itemId;
	}

	public BigDecimal getRadianPo() {
		return radianPo;
	}

	public String getRaytheonPo() {
		return raytheonPo;
	}

	public String getCompanyId() {
		return companyId;
	}

	public BigDecimal getMrNumber() {
		return mrNumber;
	}

	public String getShipToLocationId() {
		return shipToLocationId;
	}

	public String getShipToCompanyId() {
		return shipToCompanyId;
	}

	public BigDecimal getBuyerId() {
		return buyerId;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public String getSupplyPath() {
		return supplyPath;
	}

	public String getSortBy() {
		return sortBy;
	}

	public String getSearchWhat() {
		return searchWhat;
	}

	public String getSearchType() {
		return searchType;
	}

	public String getSearchText() {
		return searchText;
	}

	public String getFromPo() {
		return fromPo;
	}

	public void setFromPo(String fromPo) {
		this.fromPo = fromPo;
	}

	public String getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	public String getHubName() {
		return hubName;
	}

	public void setHubName(String hubName) {
		this.hubName = hubName;
	}

	public String getAttachedPr() {
		return attachedPr;
	}

	public void setAttachedPr(String attachedPr) {
		this.attachedPr = attachedPr;
	}

	@Override
	public void setHiddenFormFields() {
		removeHiddenFormField("totalLines");
		removeHiddenFormField("uAction");
		addHiddenFormField("raytheonPo");
		addHiddenFormField("inventoryGroup");
		addHiddenFormField("itemId");
		addHiddenFormField("radianPo");
		addHiddenFormField("shipToLocationId");
		addHiddenFormField("buyerId");
		addHiddenFormField("searchWhat");
		addHiddenFormField("searchType");
		addHiddenFormField("searchText");
		addHiddenFormField("supplyPath");
		addHiddenFormField("sortBy");
		addHiddenFormField("currencyId");
		addHiddenFormField("attachedPr");
		addHiddenFormField("userAction");
		addHiddenFormField("companyId");
		addHiddenFormField("mrNumber");
	}

	public BigDecimal getLineId() {
		return lineId;
	}

	public void setLineId(BigDecimal lineId) {
		this.lineId = lineId;
	}

	public String getUserAction() {
		return userAction;
	}

	public void setUserAction(String userAction) {
		this.userAction = userAction;
	}
}