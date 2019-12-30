package com.tcmis.internal.catalog.beans;

import java.math.BigDecimal;
import java.util.Locale;

import org.apache.struts.action.ActionForm;

import com.tcmis.common.framework.BaseInputBean;

public class CatalogItemDetailQcInputBean extends BaseInputBean {

	private final String SUBMIT_ACTION = "submit";
	private final String REJECT_ACTION = "reject";
	private final String ASSIGNED_STATUS = "Assigned";
	private final String PENDING_QC_STATUS = "Pending QC";
	private final String PENDING_APPROVAL_STATUS = "Pending Approval";
	private final String CLOSED_STATUS = "Closed";
	
	private String uAction;
	private String catPartNo;
	private BigDecimal itemId;
	private BigDecimal storageTemp;
	private boolean inseparableKit;
	private String itemNotes;
	private BigDecimal categoryId;
	private String itemType;
	private String itemVerified;
	private String sampleOnly;
	private BigDecimal requestId;
	private BigDecimal lineItem;
	private BigDecimal totalComps;
	private String company;
	private String companyId;
	private String catalogId;
	private String approvalRole;
	private String partDescription;
	private BigDecimal catalogAddItemId;
	private BigDecimal qId;
	private String catalogQueueRowStatus;
	private String rejectionComments;
	private String reverseComments;

	public CatalogItemDetailQcInputBean() {
		super();
	}

	public CatalogItemDetailQcInputBean(ActionForm inputForm, Locale locale) {
		super(inputForm, locale);
	}

	public CatalogItemDetailQcInputBean(ActionForm inputForm, Locale locale, String dateMask) {
		super(inputForm, locale, dateMask);
	}

	public String getuAction() {
		return uAction;
	}

	public void setuAction(String uAction) {
		this.uAction = uAction;
	}

	public boolean isApprove() {
		return "approve".equalsIgnoreCase(uAction);
	}

	public boolean isReverse() {
		return "reverse".equalsIgnoreCase(uAction);
	}

	public boolean isSave() {
		return "save".equalsIgnoreCase(uAction);
	}

	public boolean isDetails() {
		return "details".equalsIgnoreCase(uAction);
	}

	public boolean isMatch() {
		return "match".equalsIgnoreCase(uAction);
	}

	public boolean isNewItem() {
		return "newitem".equalsIgnoreCase(uAction);
	}

	public boolean isSelect() {
		return "select".equalsIgnoreCase(uAction);
	}

	public boolean isReload() {
		return "reload".equalsIgnoreCase(uAction);
	}

	public boolean isItemVerifiedStatus() {
		return "Y".equalsIgnoreCase(itemVerified);
	}

	public String getCatPartNo() {
		return catPartNo;
	}

	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public BigDecimal getStorageTemp() {
		return storageTemp;
	}

	public void setStorageTemp(BigDecimal storageTemp) {
		this.storageTemp = storageTemp;
	}

	public boolean getInseparableKit() {
		return inseparableKit;
	}

	public void setInseparableKit(boolean inseparableKit) {
		this.inseparableKit = inseparableKit;
	}

	public String getItemNotes() {
		return itemNotes;
	}

	public void setItemNotes(String itemNotes) {
		this.itemNotes = itemNotes;
	}

	public BigDecimal getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(BigDecimal categoryId) {
		this.categoryId = categoryId;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getItemVerified() {
		return itemVerified;
	}

	public void setItemVerified(String itemVerified) {
		this.itemVerified = itemVerified;
	}

	public String getSampleOnly() {
		return sampleOnly;
	}

	public void setSampleOnly(String sampleOnly) {
		this.sampleOnly = sampleOnly;
	}

	public BigDecimal getRequestId() {
		return requestId;
	}

	public void setRequestId(BigDecimal requestId) {
		this.requestId = requestId;
	}

	public BigDecimal getLineItem() {
		return lineItem;
	}

	public void setLineItem(BigDecimal lineItem) {
		this.lineItem = lineItem;
	}

	public BigDecimal getTotalComps() {
		return totalComps;
	}

	public void setTotalComps(BigDecimal totalComps) {
		this.totalComps = totalComps;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
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

	public String getApprovalRole() {
		return approvalRole;
	}

	public void setApprovalRole(String approvalRole) {
		this.approvalRole = approvalRole;
	}

	public String getPartDescription() {
		return partDescription;
	}

	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}

	public BigDecimal getCatalogAddItemId() {
		return catalogAddItemId;
	}

	public void setCatalogAddItemId(BigDecimal catalogAddItemId) {
		this.catalogAddItemId = catalogAddItemId;
	}
	
	public BigDecimal getqId() {
		return qId;
	}

	public void setqId(BigDecimal qId) {
		this.qId = qId;
	}

	public String getCatalogQueueRowStatus() {
		return catalogQueueRowStatus;
	}

	public void setCatalogQueueRowStatus(String catalogQueueRowStatus) {
		this.catalogQueueRowStatus = catalogQueueRowStatus;
	}
	
	public String getRejectionComments() {
		return rejectionComments;
	}

	public void setRejectionComments(String rejectionComments) {
		this.rejectionComments = rejectionComments;
	}

	public String getReverseComments() {
		return reverseComments;
	}

	public void setReverseComments(String reverseComments) {
		this.reverseComments = reverseComments;
	}

	public boolean isAssigned() {
		return ASSIGNED_STATUS.equals(catalogQueueRowStatus);
	}
	
	public boolean isPendingVendorQc() {
		return PENDING_QC_STATUS.equals(catalogQueueRowStatus);
	}
	
	public boolean isPendingApproval() {
		return PENDING_APPROVAL_STATUS.equals(catalogQueueRowStatus);
	}
	
	public boolean isWorkQueueItemClosed() {
		return CLOSED_STATUS.equals(catalogQueueRowStatus);
	}

	public boolean isSubmitAction() {
		return SUBMIT_ACTION.equals(uAction);
	}
	
	public boolean hasqId() {
		return qId != null;
	}
	
	public boolean hasLineItem() {
		return lineItem != null;
	}
	
	public boolean hasRequestId() {
		return requestId != null;
	}
	
	public boolean hasItemId() {
		return itemId != null;
	}
	
	public boolean isRejectAction() {
		return REJECT_ACTION.equals(uAction);
	}

	@Override
	public void setHiddenFormFields() {

	}
}
