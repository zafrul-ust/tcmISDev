package com.tcmis.internal.catalog.beans;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.struts.action.ActionForm;

import com.tcmis.common.framework.BaseInputBean;
import com.tcmis.common.util.StringHandler;

public class CatalogAddQcInputBean extends BaseInputBean {

	private final String REJECTION_COMMENTS_ACTION = "promptForComments";
	private final String ASSIGN_TO_SELF_ACTION = "assignToSelf";
	private final String APPROVE_ACTION = "approve";
	private final String APPROVE_WITHHOLD_ACTION = "approveWithhold";
	private final String REJECT_ACTION = "reject";
	private final String PENDING_SDS_INDEXING_STATUS = "Pending SDS Indexing";
	private final String PENDING_ITEM_CREATION_STATUS = "Pending Item Creation";
	private final String PENDING_ASSIGNMENT_STATUS = "Pending Assignment";
	public static final String ALL_STATUS = "All Statuses";
	public static final String INDEXING_TASK = "SDS Indexing";
	public static final String ITEM_CREATION_TASK = "Item Creation";
	private final String ALL_TASK = "ALL";
	
	
	private String assignedTo;
	private String catalogDesc;
	private String catalogId;
	private String companyId;
	private String companyName;
	private BigDecimal personnelId;
	private String personnelName;
	private String requestId;
	private String searchArgument;
	private String searchField;
	private String searchMode;
	private String sortBy;
	private String status;
	private boolean updated;
	private String catalogCompanyId;
	private String excludeMerckRequest;
	private String originalAssignedTo;
	private String supplier;
	private String supplierName;
	private String approvalRole;
	private String rejectionComment;
	private final Map<String, String> statusTaskMap;
	private boolean historicalSearch;
	private BigDecimal maxData;
	

	public CatalogAddQcInputBean() {
		super();
		statusTaskMap = new HashMap<>();
		initializeStatusTaskMapping();
	}

	public CatalogAddQcInputBean(ActionForm inputForm, Locale locale) {
		super(inputForm, locale);
		statusTaskMap = new HashMap<>();
		initializeStatusTaskMapping();
	}
	
	private void initializeStatusTaskMapping() {
		statusTaskMap.put(PENDING_SDS_INDEXING_STATUS, INDEXING_TASK);
		statusTaskMap.put(PENDING_ITEM_CREATION_STATUS, ITEM_CREATION_TASK);
	}

	public String getAssignedTo() {
		return assignedTo;
	}

	public String getCatalogDesc() {
		return catalogDesc;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public BigDecimal getPersonnelId() {
		return personnelId;
	}

	public String getPersonnelName() {
		return personnelName;
	}

	public String getRequestId() {
		return requestId;
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

	public String getSortBy() {
		return sortBy;
	}

	public String getStatus() {
		return status;
	}

	public boolean hasAssignedTo() {
		return !StringHandler.isBlankString(assignedTo);
	}

	public boolean hasCatalog() {
		return !StringHandler.isBlankString(catalogId) && !"ALL".equals(catalogId);
	}

	public boolean hasCatalogId() {
		return !StringHandler.isBlankString(catalogId);
	}

	public boolean hasSearch() {
		return !StringHandler.isBlankString(searchArgument);
	}

	public boolean isAllCompanies() {
		return "ALL".equals(companyId);
	}

	public boolean isAssignedToUnnassigned() {
		return "-1".equals(assignedTo);
	}

	public boolean isCompanySeagate() {
		return "Seagate".equalsIgnoreCase(companyId);
	}

	public boolean isSortByCompanyFirst() {
		return "1".equals(sortBy);
	}

	public boolean isSortByDateFirst() {
		return "2".equals(sortBy);
	}

	public boolean isMSDS() {
		return "Pending SDS Sourcing".equals(status);
	}

	public boolean isQC() {
		return "Pending Item Creation".equals(status);
	}

	public boolean isUpdated() {
		return updated;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	public void setCatalogDesc(String catalogDesc) {
		this.catalogDesc = catalogDesc;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Override
	public void setHiddenFormFields() {
		hiddenFormFieldList.add("assignedTo");
		hiddenFormFieldList.add("companyId");
		hiddenFormFieldList.add("catalogCompanyId");
		hiddenFormFieldList.add("catalogId");
		hiddenFormFieldList.add("sortBy");
		hiddenFormFieldList.add("status");
		hiddenFormFieldList.add("searchArgument");
		hiddenFormFieldList.add("searchMode");
		hiddenFormFieldList.add("searchField");
		hiddenFormFieldList.add("approvalRole");
		hiddenFormFieldList.add("historicalSearch");
		hiddenFormFieldList.add("maxData");
		hiddenFormFieldList.add("excludeMerckRequest");
	}

	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
	}

	public void setPersonnelName(String personnelName) {
		this.personnelName = personnelName;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
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

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setUpdated(boolean update) {
		this.updated = update;
	}

	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}

	public void setCatalogCompanyId(String catalogCompanyId) {
		this.catalogCompanyId = catalogCompanyId;
	}

	public String getExcludeMerckRequest() {
		return excludeMerckRequest;
	}

	public void setExcludeMerckRequest(String excludeMerckRequest) {
		this.excludeMerckRequest = excludeMerckRequest;
	}

	public boolean isExcludeMerck() {return "Y".equals(excludeMerckRequest);}

	public String getOriginalAssignedTo() {
		return originalAssignedTo;
	}

	public void setOriginalAssignedTo(String originalAssignedTo) {
		this.originalAssignedTo = originalAssignedTo;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public boolean hasSupplier() {
		return !StringHandler.isBlankString(supplier);
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	
	public boolean isAssignToSelfAction() {
		return ASSIGN_TO_SELF_ACTION.equals(uAction);
	}

	public boolean displayByWorkQueue() {
		return ! historicalSearch && 
				("Pending SDS Sourcing".equals(status) ||
				"Pending SDS Indexing".equals(status) ||
				"Pending Item Creation".equals(status));
	}
	
	public boolean isPendingAssignment() {
		return PENDING_ASSIGNMENT_STATUS.equals(status);
	}

	public String mapStatusToTask() {
		return status==null?ALL_TASK:statusTaskMap.getOrDefault(this.status,ALL_TASK);
	}
	
	public boolean isApproveAction() {
		return APPROVE_ACTION.equals(uAction);
	}
	
	public boolean isApproveWithholdAction() {
		return APPROVE_WITHHOLD_ACTION.equals(uAction);
	}
	
	public boolean isRejectAction() {
		return REJECT_ACTION.equals(uAction);
	}

	public boolean isRejectionCommentsAction() {
		return REJECTION_COMMENTS_ACTION.equals(uAction);
	}

	public String getApprovalRole() {
		return approvalRole;
	}

	public void setApprovalRole(String approvalRole) {
		this.approvalRole = approvalRole;
	}

	public String getRejectionComment() {
		return rejectionComment;
	}

	public void setRejectionComment(String rejectionComment) {
		this.rejectionComment = rejectionComment;
	}

	public boolean isHistoricalSearch() {
		return historicalSearch;
	}

	public void setHistoricalSearch(boolean historicalSearch) {
		this.historicalSearch = historicalSearch;
	}

	public BigDecimal getMaxData() {
		return maxData;
	}

	public void setMaxData(BigDecimal maxData) {
		this.maxData = maxData;
	}
}
