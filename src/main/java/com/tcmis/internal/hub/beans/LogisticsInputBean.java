package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.tcmis.common.framework.BaseDataBean;

public class LogisticsInputBean extends BaseDataBean {

	private String		binfrom;
	private String		binto;
	private String		checkbox;
	private BigDecimal	countId;
	private Date		dorfrom;
	private Date		dorto;
	private String		hub;
	private String		hubName;
	private String		includeHistory;
	private String		inventoryGroup;
	private String 		includeObsoleteParts;
	private String		itemId;
	private String[]	lotStatus;
	private BigDecimal	numDaysOld;
	private String		ok;
	private String		opsEntityId;
	private String		paperSize;
	private String		printKitLabels;
	private BigDecimal	receiverId;
	private String		room;
	private String		searchArgument;
	private String		searchArgument2;
	private String		searchField;
	private String		searchField2;
	private String		searchField3;
	private String		searchMode;
	private String		searchMode2;
	private String		showIssuedReceipts;
	private String		sortBy;
	private String		status;
	private Date		transactionDate;
	private String		uAction;

	// constructor
	public LogisticsInputBean() {
	}

	public String getBinfrom() {
		return binfrom;
	}

	public String getBinto() {
		return binto;
	}

	public String getCheckbox() {
		return checkbox;
	}

	public BigDecimal getCountId() {
		return countId;
	}

	public Date getDorfrom() {
		return dorfrom;
	}

	public Date getDorto() {
		return dorto;
	}

	public String getHub() {
		return hub;
	}

	public String getHubName() {
		return hubName;
	}

	public String getIncludeHistory() {
		return includeHistory;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public String getItemId() {
		return itemId;
	}

	public String[] getLotStatus() {
		return lotStatus;
	}

	public BigDecimal getNumDaysOld() {
		return numDaysOld;
	}

	public String getOk() {
		return ok;
	}

	public String getOpsEntityId() {
		return opsEntityId;
	}

	public String getPaperSize() {
		return paperSize;
	}

	public String getPrintKitLabels() {
		return printKitLabels;
	}

	public BigDecimal getReceiverId() {
		return receiverId;
	}

	public String getRoom() {
		return room;
	}

	public String getSearchArgument() {
		return searchArgument;
	}

	public String getSearchArgument2() {
		return searchArgument2;
	}

	public String getSearchField() {
		return searchField;
	}

	public String getSearchField2() {
		return searchField2;
	}

	public String getSearchField3() {
		return searchField3;
	}

	public String getSearchMode() {
		return searchMode;
	}

	public String getSearchMode2() {
		return searchMode2;
	}

	public String getShowIssuedReceipts() {
		return showIssuedReceipts;
	}

	public String getSortBy() {
		return sortBy;
	}

	public String getStatus() {
		return status;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public String getuAction() {
		return uAction;
	}

	public boolean hasHub() {
		return StringUtils.isNotBlank(hub);
	}

	public boolean hasInventoryGroup() {
		return StringUtils.isNotBlank(inventoryGroup);
	}

	public boolean hasNumDaysOld() {
		return numDaysOld != null;

	}

	public boolean hasOpsEntityId() {
		return StringUtils.isNotBlank(opsEntityId);

	}

	public boolean hasReceiverId() {
		return receiverId != null;

	}

	public boolean hasSearchArgument() {
		return StringUtils.isNotBlank(searchArgument);

	}

	public boolean hasSortBy() {
		return StringUtils.isNotBlank(sortBy);
	}

	public boolean hasTransactionDate() {
		return transactionDate != null;

	}

	public boolean isIncludeHistoryChecked() {
		return "Yes".equals(includeHistory) || "Y".equals(includeHistory);
	}

	public boolean isIncludeObsoletePartsChecked() {
		return "Yes".equals(includeObsoleteParts) || "Y".equals(includeObsoleteParts);
	}

	public boolean isModeContains() {
		return "contains".equals(searchMode);
	}

	public boolean isModeEndsWith() {
		return "endsWith".equals(searchMode);
	}

	public boolean isModeIs() {
		return "is".equals(searchMode);
	}

	public boolean isModeStartsWith() {
		return "startsWith".equals(searchMode);
	}

	public boolean isSearchFieldCaseSensitive() {
		return "itemId".equals(searchField) || "originalReceiptId".equals(searchField) || "radianPo".equals(searchField) || "receiptId".equals(searchField);
	}

	public boolean isSearchFieldCatPartNo() {
		return "catPartNo".equals(searchField);
	}

	public boolean isShowIssuedReceiptsChecked() {
		return "Yes".equals(showIssuedReceipts) || "Y".equals(showIssuedReceipts);
	}

	public void setBinfrom(String binfrom) {
		this.binfrom = binfrom;
	}

	public void setBinto(String binto) {
		this.binto = binto;
	}

	public void setCheckbox(String checkbox) {
		this.checkbox = checkbox;
	}

	public void setCountId(BigDecimal countId) {
		this.countId = countId;
	}

	public void setDorfrom(Date dorfrom) {
		this.dorfrom = dorfrom;
	}

	public void setDorto(Date dorto) {
		this.dorto = dorto;
	}

	public void setHub(String hub) {
		this.hub = hub;
	}

	public void setHubName(String hubName) {
		this.hubName = hubName;
	}

	public void setIncludeHistory(String showNeedingPrint) {
		this.includeHistory = showNeedingPrint;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public void setLotStatus(String[] lotStatus) {
		this.lotStatus = lotStatus;
	}

	public void setNumDaysOld(BigDecimal noDaysOld) {
		this.numDaysOld = noDaysOld;
	}

	public void setOk(String ok) {
		this.ok = ok;
	}

	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}

	public void setPaperSize(String paperSize) {
		this.paperSize = paperSize;
	}

	public void setPrintKitLabels(String printKitLabels) {
		this.printKitLabels = printKitLabels;
	}

	public void setReceiverId(BigDecimal receiverId) {
		this.receiverId = receiverId;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public void setSearchArgument(String searchArgument) {
		this.searchArgument = searchArgument;
	}

	public void setSearchArgument2(String searchArgument2) {
		this.searchArgument2 = searchArgument2;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public void setSearchField2(String searchField2) {
		this.searchField2 = searchField2;
	}

	public void setSearchField3(String searchField3) {
		this.searchField3 = searchField3;
	}

	public void setSearchMode(String searchMode) {
		this.searchMode = searchMode;
	}

	public void setSearchMode2(String searchMode2) {
		this.searchMode2 = searchMode2;
	}

	public void setShowIssuedReceipts(String showIssuedReceipts) {
		this.showIssuedReceipts = showIssuedReceipts;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public void setuAction(String uAction) {
		this.uAction = uAction;
	}

	public String getIncludeObsoleteParts() {
		return includeObsoleteParts;
	}

	public void setIncludeObsoleteParts(String includeObsoleteParts) {
		this.includeObsoleteParts = includeObsoleteParts;
	}
}