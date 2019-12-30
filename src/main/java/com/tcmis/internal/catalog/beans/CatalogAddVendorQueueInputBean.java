package com.tcmis.internal.catalog.beans;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Vector;

import org.apache.struts.action.ActionForm;

import com.tcmis.common.framework.BaseInputBean;
import com.tcmis.common.util.StringHandler;

public class CatalogAddVendorQueueInputBean extends BaseInputBean {

	private final String INVOICE_ACTION = "invoice";
	private final String SEND_PAYMENT_ACTION = "sendPayment";
	private final String APPROVE_ACTION = "approve";
	private final String ALL = "ALL";
	private final String PENDING_APPROVAL = "Pending Approval";
	
	private final int UNASSIGNED = -1;
	
	private BigDecimal assignedTo;
	private String task;
	private String status;
	private String taskStatus;
	private String searchField;
	private String searchMode;
	private String searchArgument;
	private boolean grabbed;
	private Vector tasks = new Vector(0);
	private BigDecimal catalogAddRequestId;
	private String calledFrom;
	private String companyId;
	private String catalogId;
	private String supplier;
	private String sortBy;
	private BigDecimal maxData;
	private String excludeMerckRequest;

	public CatalogAddVendorQueueInputBean() {
		super();
	}

	public CatalogAddVendorQueueInputBean(ActionForm inputForm, Locale locale) {
		super(inputForm, locale);
	}

	public BigDecimal getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(BigDecimal assignedTo) {
		this.assignedTo = assignedTo;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String getSearchMode() {
		return searchMode;
	}

	public void setSearchMode(String searchMode) {
		this.searchMode = searchMode;
	}

	public String getSearchArgument() {
		return searchArgument;
	}

	public void setSearchArgument(String searchArgument) {
		this.searchArgument = searchArgument;
	}
	
	public boolean hasTask() {
		return ! StringHandler.isBlankString(task);
	}
	
	public boolean hasStatus() {
		return ! StringHandler.isBlankString(status);
	}
	
	public boolean hasTaskStatus() {
		return ! StringHandler.isBlankString(taskStatus);
	}
	
	public boolean hasSearchArgument() {
		return ! StringHandler.isBlankString(searchArgument);
	}
	
	public boolean hasAssignedTo() {
		return assignedTo != null;
	}
	
	public boolean isUnassigned() {
		return assignedTo != null && assignedTo.intValue() == UNASSIGNED;
	}

	@Override
	public void setHiddenFormFields() {
		addHiddenFormField("catalogAddRequestId");
		addHiddenFormField("calledFrom");
		addHiddenFormField("status");
		addHiddenFormField("task");
		addHiddenFormField("taskStatus");
		addHiddenFormField("assignedTo");
		addHiddenFormField("searchField");
		addHiddenFormField("searchMode");
		addHiddenFormField("searchArgument");
		addHiddenFormField("companyId");
		addHiddenFormField("catalogId");
		addHiddenFormField("supplier");
		addHiddenFormField("sortBy");
		addHiddenFormField("maxData");
		addHiddenFormField("excludeMerckRequest");
	}
	
	public boolean isWescoTask() {
		return ("Pending SDS Sourcing".equals(status)
				|| "Pending SDS Indexing".equals(status)
				|| "Pending Item Creation".equals(status));
	}

	public boolean isWescoApproval()
	{
		return PENDING_APPROVAL.equals(taskStatus);
	}

	public boolean isGrabbed() {
		return grabbed;
	}

	public void setGrabbed(boolean grabbed) {
		this.grabbed = grabbed;
	}

	public Vector getTasks() {
		return tasks;
	}

	public void initializeTasks() {
		tasks = new Vector(0);
		if ("Pending SDS Sourcing".equals(status)) {
			tasks.addElement("SDS Sourcing");
			tasks.addElement("SDS Authoring");
		}else if ("Pending SDS Indexing".equals(status))
			tasks.addElement("SDS Indexing");
		else if ("Pending Item Creation".equals(status))
			tasks.addElement("Item Creation");
	}

	public boolean hasTasks() {
		return tasks.size() > 0;
	}

	public BigDecimal getCatalogAddRequestId() {
		return catalogAddRequestId;
	}

	public void setCatalogAddRequestId(BigDecimal catalogAddRequestId) {
		this.catalogAddRequestId = catalogAddRequestId;
	}

	public boolean hasCatalogAddRequestId() {
		return catalogAddRequestId != null;
	}

	public String getCalledFrom() {
		return calledFrom;
	}

	public void setCalledFrom(String calledFrom) {
		this.calledFrom = calledFrom;
	}

	public boolean isCalledFromWorkQueueDetails() {
		return "workQueueDetails".equals(calledFrom);
	}
	
	public boolean isCalledFromWesco() {
		return "wescoApproval".equals(calledFrom);
	}

	public boolean isInvoiceAction() {
		return INVOICE_ACTION.equals(uAction);
	}
	
	public boolean isSendPaymentAction() {
		return SEND_PAYMENT_ACTION.equals(uAction);
	}
	
	public boolean isApproveAction() {
		return APPROVE_ACTION.equals(uAction);
	}
	
	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}
	
	public String getTaskStatus() {
		return taskStatus;
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

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
	
	public boolean hasCompanyId() {
		return ! (StringHandler.isBlankString(companyId) || ALL.equalsIgnoreCase(companyId));
	}
	
	public boolean hasCatalogId() {
		return ! StringHandler.isBlankString(catalogId);
	}
	
	public boolean hasSupplier() {
		return ! StringHandler.isBlankString(supplier);
	}

	public BigDecimal getMaxData() {
		return maxData;
	}

	public void setMaxData(BigDecimal maxData) {
		this.maxData = maxData;
	}

	public String getExcludeMerckRequest() {
		return excludeMerckRequest;
	}

	public void setExcludeMerckRequest(String excludeMerckRequest) {
		this.excludeMerckRequest = excludeMerckRequest;
	}

	public boolean isExcludeMerck() {return "Y".equals(excludeMerckRequest);}
	
	public boolean isTaskStatusApproved() {
		return "approved".equalsIgnoreCase(taskStatus);
	}
}
