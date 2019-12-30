package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;

import org.apache.struts.action.ActionForm;

import com.tcmis.common.framework.HubBaseInputBean;
import com.tcmis.common.util.StringHandler;

public class ItemConsignmentInputBean extends HubBaseInputBean {

	public static final String COUNT_BY_ITEM = "item";
	public static final String COUNT_BY_RECEIPT = "receipt";
	private static final long serialVersionUID = 1L;
	private String countBy;
	private Date dateDelivered;
	private BigDecimal itemId;
	private BigDecimal receiptId;
	private boolean rowUpdated;
	private String searchField;
	private String searchMode;
	private String searchText;
	private String shippingReference;
	private BigDecimal usedQuantity;
	private String distCustomerPartList;
	private String poNumber;
	private BigDecimal catalogPrice;
	private boolean isNewCatalogPrice;
	private String currencyId;
	

	public ItemConsignmentInputBean() {
	}

	public ItemConsignmentInputBean(ActionForm inputForm, Locale locale) {
		super(inputForm, locale);
	}
	
	public String getCurrencyId() {
		return currencyId;
	}
	
	public String getCountBy() {
		return countBy;
	}

	public Date getDateDelivered() {
		return dateDelivered;
	}
	
	public BigDecimal getCatalogPrice() {
		return catalogPrice;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public BigDecimal getReceiptId() {
		return receiptId;
	}

	public String getSearchField() {
		return searchField;
	}

	public String getSearchMode() {
		return searchMode;
	}

	public String getSearchText() {
		return searchText;
	}

	public String getShippingReference() {
		return shippingReference;
	}

	public BigDecimal getUsedQuantity() {
		return usedQuantity;
	}

	public boolean hasFieldSearch() {
		return !StringHandler.isBlankString(getSearchText());
	}

	public boolean isCountByItem() {
		return COUNT_BY_ITEM.equals(countBy);
	}

	public boolean isCountByReceipt() {
		return COUNT_BY_RECEIPT.equals(countBy);
	}

	public boolean isRowUpdated() {
		return rowUpdated;
	}
	
	public boolean isNewCatalogPrice() {
		return isNewCatalogPrice;
	}

	public boolean isSearchFieldReceiptId() {
		return "receiptId".equals(this.searchField);
	}
	
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}
	
	public void setCountBy(String countBy) {
		this.countBy = countBy;
	}

	public void setDateDelivered(Date dateDelivered) {
		this.dateDelivered = dateDelivered;
	}

	@Override
	public void setHiddenFormFields() {
		addHiddenFormField("searchText");
		addHiddenFormField("searchField");
		addHiddenFormField("searchMode");
		addHiddenFormField("countBy");
	}
	
	public void setCatalogPrice(BigDecimal catalogPrice) {
		this.catalogPrice = catalogPrice;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}

	public void setRowUpdated(boolean update) {
		this.rowUpdated = update;
	}
	
	public void setIsNewCatalogPrice(boolean isNewCatalogPrice) {
		this.isNewCatalogPrice = isNewCatalogPrice;
	}
	
	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public void setSearchMode(String searchType) {
		this.searchMode = searchType;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public void setShippingReference(String shippingReference) {
		this.shippingReference = shippingReference;
	}

	public void setUsedQuantity(BigDecimal countedQuantity) {
		this.usedQuantity = countedQuantity;
	}

	public String getDistCustomerPartList() {
		return distCustomerPartList;
	}

	public void setDistCustomerPartList(String distCustomerPartList) {
		this.distCustomerPartList = distCustomerPartList;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	
	

	
	
}