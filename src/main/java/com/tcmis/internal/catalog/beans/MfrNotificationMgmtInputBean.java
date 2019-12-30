package com.tcmis.internal.catalog.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseInputBean;

@SuppressWarnings("serial")
public class MfrNotificationMgmtInputBean extends BaseInputBean {

	private final String CATEGORY_LOOKUP = "categoryLookup";
	private final String INIT = "init";
	private final String RELOAD = "reload";
	private final String CREATE = "create";
	
	private final String MATERIAL_ID = "materialId";
	private final String MATERIAL_DESC = "materialDesc";
	private final String MFR_ID = "mfrId";
	private final String MFR_DESC = "mfrDesc";
	private final String ACQUIRED_MFR_ID = "acquiredMfrId";
	private final String ACQUIRED_MFR_DESC = "acquiredMfrDesc";
	private final String ITEM_ID = "itemId";
	private final String ITEM_DESC = "itemDesc";
	private final String NOTIFICATION_ID = "notificationId";
	private final String INTERNAL_COMMENTS = "internalComments";
	
	private String selectedCategories;
	private BigDecimal notificationId;
	private BigDecimal requester;
	private BigDecimal assignedTo;
	private BigDecimal approvedWindow;
	private String draftStatus;
	private String pendingApprovalStatus;
	private String approvedStatus;
	private String rejectedStatus;
	private String searchWhat;
	private String searchType;
	private String searchText;
	
	public MfrNotificationMgmtInputBean() {}
	
	public boolean isCategoryLookup() {
		return CATEGORY_LOOKUP.equals(uAction);
	}
	
	public boolean isInit() {
		return INIT.equals(uAction);
	}
	
	public boolean isReload() {
		return RELOAD.equals(uAction);
	}
	
	public boolean isCreate() {
		return CREATE.equals(uAction);
	}
	
	@Override
	public void setHiddenFormFields() {
	}

	public String getSelectedCategories() {
		return selectedCategories;
	}

	public void setSelectedCategories(String selectedCategories) {
		this.selectedCategories = selectedCategories;
	}

	public BigDecimal getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(BigDecimal notificationId) {
		this.notificationId = notificationId;
	}

	public BigDecimal getRequester() {
		return requester;
	}

	public void setRequester(BigDecimal requester) {
		this.requester = requester;
	}

	public BigDecimal getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(BigDecimal assignedTo) {
		this.assignedTo = assignedTo;
	}

	public BigDecimal getApprovedWindow() {
		return approvedWindow;
	}

	public void setApprovedWindow(BigDecimal approvedWindow) {
		this.approvedWindow = approvedWindow;
	}

	public String getDraftStatus() {
		return draftStatus;
	}

	public void setDraftStatus(String draftStatus) {
		this.draftStatus = draftStatus;
	}

	public String getPendingApprovalStatus() {
		return pendingApprovalStatus;
	}

	public void setPendingApprovalStatus(String pendingApprovalStatus) {
		this.pendingApprovalStatus = pendingApprovalStatus;
	}

	public String getApprovedStatus() {
		return approvedStatus;
	}

	public void setApprovedStatus(String approvedStatus) {
		this.approvedStatus = approvedStatus;
	}

	public String getRejectedStatus() {
		return rejectedStatus;
	}

	public void setRejectedStatus(String rejectedStatus) {
		this.rejectedStatus = rejectedStatus;
	}

	public String getSearchWhat() {
		return searchWhat;
	}

	public void setSearchWhat(String searchWhat) {
		this.searchWhat = searchWhat;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	
	public boolean isMaterialIdSearch() {
		return MATERIAL_ID.equals(searchWhat);
	}
	
	public boolean isMaterialDescSearch() {
		return MATERIAL_DESC.equals(searchWhat);
	}
	
	public boolean isMfrIdSearch() {
		return MFR_ID.equals(searchWhat);
	}
	
	public boolean isMfrDescSearch() {
		return MFR_DESC.equals(searchWhat);
	}
	
	public boolean isItemIdSearch() {
		return ITEM_ID.equals(searchWhat);
	}
	
	public boolean isItemDescSearch() {
		return ITEM_DESC.equals(searchWhat);
	}
	
	public boolean isAcquiredMfrIdSearch() {
		return ACQUIRED_MFR_ID.equals(searchWhat);
	}
	
	public boolean isAcquiredMfrDescSearch() {
		return ACQUIRED_MFR_DESC.equals(searchWhat);
	}
	
	public boolean isNotificationIdSearch() {
		return NOTIFICATION_ID.equals(searchWhat);
	}
	
	public boolean isInternalCommentsSearch() {
		return INTERNAL_COMMENTS.equals(searchWhat);
	}
}
