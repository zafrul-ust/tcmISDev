package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.tcmis.common.framework.BaseInputBean;
import com.tcmis.common.util.StringHandler;

public class ItemCountInputBean extends BaseInputBean {

	private String addItemInventoryGroup;
	private String catalogId;
	private String catPartNo;
	private boolean collection = false;
	private String companyId;
	private Date countDate;
	private String counted;
	private BigDecimal countedQuantity;
	private BigDecimal countId;
	private String countType;
	private String hub;
	private String[] inventoryGroup;
	private String issueOnReceipt;
	private BigDecimal itemId;
	private String opsEntityId;
	private BigDecimal partGroupNo;
	private String poNumber;
	private String searchText;
	private String sortBy;
	private String isProcessDisbursement;

	public ItemCountInputBean() {
	}

	public ItemCountInputBean(HttpServletRequest request, ActionForm inputForm, Locale locale) {
		super(inputForm, locale);
		setInventoryGroup(request.getParameterValues("inventoryGroup"));
	}

	public String getAddItemInventoryGroup() {
		return addItemInventoryGroup;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public String getCatPartNo() {
		return catPartNo;
	}

	public String getCompanyId() {
		return companyId;
	}

	public Date getCountDate() {
		return countDate;
	}

	public String getCounted() {
		return counted;
	}

	public BigDecimal getCountedQuantity() {
		return countedQuantity;
	}

	public BigDecimal getCountId() {
		return countId;
	}

	public String getCountType() {
		return countType;
	}

	public String getHub() {
		return hub;
	}

	public String[] getInventoryGroup() {
		return inventoryGroup;
	}

	public String getIssueOnReceipt() {
		return issueOnReceipt;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public String getOpsEntityId() {
		return opsEntityId;
	}

	public BigDecimal getPartGroupNo() {
		return partGroupNo;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public String getSearchText() {
		return searchText;
	}

	public String getSortBy() {
		return sortBy;
	}

	public boolean hasAddItemInventoryGroup() {
		return !StringHandler.isBlankString(getAddItemInventoryGroup());
	}

	public boolean hasHub() {
		return !StringHandler.isBlankString(getHub());
	}

	public boolean hasInventoryGroup() {
		return (getInventoryGroup() != null && (getInventoryGroup().length > 1|| !StringHandler.isBlankString(getInventoryGroup()[0])));
	}

	public boolean hasOpsEnitityId() {
		return !StringHandler.isBlankString(opsEntityId);
	}
	
	public boolean hasPoNumber() {
		return !StringHandler.isBlankString(poNumber);
	}
	
	public boolean hasSearchText() {
		return !StringHandler.isBlankString(searchText);
	}

	public boolean isAddItem() {
		return !StringHandler.isBlankString(getuAction()) && getuAction().startsWith("addItem");
	}

	public boolean isSelectCatPartNo() {
		return "selectCatPartNo".equals(getuAction());
	}
	
	public boolean isAddItemSearch() {
		return "addItemSearch".equals(getuAction());
	}

	public boolean isAddItemUpdate() {
		return "addItemUpdate".equals(getuAction());
	}

	public boolean isCloseCount() {
		return "closeCount".equals(getuAction());
	}

	public boolean isCollection() {
		return collection;
	}

	public boolean isIssueOnReceipt() {
		return "Y".equals(issueOnReceipt);
	}

	public boolean isRowCounted() {
		return "true".equals(counted) && StringHandler.emptyIfNull(countedQuantity).length() > 0;
	}

	public boolean isStartNewCount() {
		return "startCount".equals(getuAction());
	}
	
	public boolean isProcessDisbursement() {
		return "Y".equalsIgnoreCase(isProcessDisbursement);
	}
	
	public void setIsProcessDisbursement(String isProcessDisbursement) {
		this.isProcessDisbursement = isProcessDisbursement;
	}

	public void setAddItemInventoryGroup(String addItemInventoryGroup) {
		this.addItemInventoryGroup = addItemInventoryGroup;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}

	public void setCollection(boolean collection) {
		this.collection = collection;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setCountDate(Date countDate) {
		this.countDate = countDate;
	}

	public void setCounted(String counted) {
		this.counted = counted;
	}

	public void setCountedQuantity(BigDecimal countedQuantity) {
		this.countedQuantity = countedQuantity;
	}

	public void setCountId(BigDecimal countId) {
		this.countId = countId;
	}

	public void setCountType(String countType) {
		this.countType = countType;
	}

	@Override
	public void setHiddenFormFields() {
		addHiddenFormField("sortBy");
		addHiddenFormField("hub");
		addHiddenFormField("inventoryGroup");
		addHiddenFormField("opsEntityId");
		addHiddenFormField("addItemInventoryGroup");
		addHiddenFormField("itemId");
	}

	public void setHub(String hub) {
		this.hub = hub;
	}

	public void setInventoryGroup(String[] inventoryGroups) {
		if (inventoryGroups != null && inventoryGroups.length > 1) {
			if (StringHandler.isBlankString(inventoryGroups[0])) {
				this.inventoryGroup = new String[inventoryGroups.length - 1] ;
				System.arraycopy(inventoryGroups, 1, this.inventoryGroup, 0, this.inventoryGroup.length);
			}
			else {
				this.inventoryGroup = inventoryGroups;
			}
		}
		else {
			this.inventoryGroup = inventoryGroups;
		}
	}

	public void setIssueOnReceipt(String issueOnReceipt) {
		this.issueOnReceipt = issueOnReceipt;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}

	public void setPartGroupNo(BigDecimal partGroupNo) {
		this.partGroupNo = partGroupNo;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
}