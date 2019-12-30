package com.tcmis.internal.catalog.beans;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;

import org.apache.struts.action.ActionForm;

import com.tcmis.common.framework.BaseInputBean;
import com.tcmis.common.util.StringHandler;

@SuppressWarnings("serial")
public class CatalogAddReqMsdsInputBean extends BaseInputBean {

	public static final String STATUS_OPEN = "Open";
	public static final String STATUS_ASSIGNED = "Assigned";
	public static final String STATUS_PENDING_QC = "Pending QC";
	public static final String STATUS_PENDING_APPROVAL = "Pending Approval";
	public static final String STATUS_CLOSED = "Closed";
	public static final String TASK_SOURCING = "SDS Sourcing";
	private final String TASK_INDEXING = "SDS Indexing";
	public static final String TASK_ITEM_CREATION = "Item Creation";
	private final String TASK_AUTHORING = "SDS Authoring";
	public static final String NEW_REVISION_ACTION = "newRevision";
	public static final String SEND_TO_VENDOR_ACTION = "sendToVendor";
	public static final String VIEW_REQUEST_ACTION = "viewRequest";
	public static final String CHANGE_LOCALE_ACTION = "changeLocale";
	
	private boolean approve;
	private boolean submitForQc;
	private String companyId;
	private String lineItem;
	private BigDecimal materialId;
	private String mfgId;
	private BigDecimal personnelId;
	private String requestId;
	private Date revisionDate;
	private boolean companyMsds;
    private String approvalRole;
    private String idOnly;
    //this column is for tracking what locale code user
    //selected to upload an image
    private String qLocaleCode;
    private BigDecimal catalogAddItemId;
    private BigDecimal qId;
    private String catalogQueueRowTask;
    private String catalogQueueRowStatus;
    private String rejectionComments;
    private String sdsRequired;
    private boolean expressApproval;
    private String localeOverride;
    private String generateSdsFromSequence;

	public CatalogAddReqMsdsInputBean() {
		super();
	}

	public CatalogAddReqMsdsInputBean(ActionForm inputForm, Locale locale) {
		super(inputForm, locale);
	}

	public CatalogAddReqMsdsInputBean(ActionForm inputForm, Locale locale, String dateMask) {
		super(inputForm, locale, dateMask);
	}

	public String getCompanyId() {
		return companyId;
	}

	public String getLineItem() {
		return lineItem;
	}

	public BigDecimal getMaterialId() {
		return materialId;
	}

	public String getMfgId() {
		return mfgId;
	}

	public BigDecimal getPersonnelId() {
		return personnelId;
	}

	public String getRequestId() {
		return requestId;
	}

	public Date getRevisionDate() {
		return revisionDate;
	}

	public boolean isApprove() {
		return approve;
	}

	public boolean isGetAvailableRevisionDates() {
		return "getRevDates".equals(getuAction());
	}

	public boolean isRMC() {
		return "RMC".equals(getuAction());
	}

	public boolean isGetManufacturer() {
		return "getManufacturer".equals(getuAction());
	}

	public boolean isGetMsds() {
		return "getMsds".equals(getuAction());
	}

	public boolean isGetCustomerOverride() {
		return "getCustomerOverride".equals(getuAction());
	}

	public boolean isCompanyMsds() {
		return companyMsds;
	}

	public void setCompanyMsds(boolean companyMsds) {
		this.companyMsds = companyMsds;
	}

	public void setApprove(boolean approve) {
		this.approve = approve;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	@Override
	public void setHiddenFormFields() {
	}

	public void setLineItem(String lineItem) {
		if (StringHandler.isBlankString(this.lineItem) || !StringHandler.isBlankString(lineItem))
			this.lineItem = lineItem;
	}

	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}

	public void setMfgId(String mfgId) {
		this.mfgId = mfgId;
	}

	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
	}

	public void setRequestId(String requestId) {
		if (StringHandler.isBlankString(this.requestId) || ! StringHandler.isBlankString(requestId)) {
			this.requestId = requestId;
		}
	}

	public void setRevisionDate(Date revisionDate) {
		this.revisionDate = revisionDate;
	}

    public String getApprovalRole() {
        return approvalRole;
    }

    public void setApprovalRole(String approvalRole) {
        this.approvalRole = approvalRole;
    }

    public String getIdOnly() {
        return idOnly;
    }

    public void setIdOnly(String idOnly) {
        this.idOnly = idOnly;
    }

    public String getqLocaleCode() {
        return qLocaleCode;
    }

    public void setqLocaleCode(String qLocaleCode) {
        this.qLocaleCode = qLocaleCode;
    }

	public boolean isSubmitForQc() {
		return submitForQc;
	}

	public void setSubmitForQc(boolean submitForQc) {
		this.submitForQc = submitForQc;
	}

	public boolean isRejectOutOfScope() {return "rejectOutOfScope".equals(getuAction());}
	
	public boolean isRejectCannotFulfill() {return "rejectCannotFulfill".equals(getuAction());}

	public boolean isUpdate() {
		return "update".equals(getuAction());
	}
	
	public boolean isNewRevision() {
		return NEW_REVISION_ACTION.equals(uAction);
	}

	public BigDecimal getCatalogAddItemId() {
		return catalogAddItemId;
	}

	public void setCatalogAddItemId(BigDecimal catalogAddItemId) {
		this.catalogAddItemId = catalogAddItemId;
	}

	public String getCatalogQueueRowTask() {
		return catalogQueueRowTask;
	}

	public void setCatalogQueueRowTask(String catalogQueueRowTask) {
		this.catalogQueueRowTask = catalogQueueRowTask;
	}

	public String getCatalogQueueRowStatus() {
		return catalogQueueRowStatus;
	}

	public void setCatalogQueueRowStatus(String catalogQueueRowStatus) {
		this.catalogQueueRowStatus = catalogQueueRowStatus;
	}

	public BigDecimal getqId() {
		return qId;
	}

	public void setqId(BigDecimal qId) {
		this.qId = qId;
	}
	
	public String getRejectionComments() {
		return rejectionComments;
	}

	public void setRejectionComments(String rejectionComments) {
		this.rejectionComments = rejectionComments;
	}
	
	public void setCatalogAddRequestId(String requestId) {
		if (StringHandler.isBlankString(this.requestId) || ! StringHandler.isBlankString(requestId)) {
			this.requestId = requestId;
		}
	}
	
	public void setCatalogAddRequestLineItem(String lineItem) {
		if (StringHandler.isBlankString(this.lineItem) || ! StringHandler.isBlankString(lineItem)) {
			this.lineItem = lineItem;
		}
	}

	public String getSdsRequired() {
		return sdsRequired;
	}

	public void setSdsRequired(String sdsRequired) {
		this.sdsRequired = sdsRequired;
	}
	
	public boolean isMsdsRequired() {
		return "Y".equals(sdsRequired);
	}

	public boolean isSourcing() {
		return TASK_SOURCING.equals(catalogQueueRowTask);
	}
	
	public boolean isIndexing() {
		return TASK_INDEXING.equals(catalogQueueRowTask);
	}
	
	public boolean isItemCreation() {
		return TASK_ITEM_CREATION.equals(catalogQueueRowTask);
	}
	
	public boolean isAuthoring() {
		return TASK_AUTHORING.equals(catalogQueueRowTask);
	}
	
	public boolean isAssigned() {
		return STATUS_ASSIGNED.equals(catalogQueueRowStatus);
	}
	
	public boolean isPendingVendorQc() {
		return STATUS_PENDING_QC.equals(catalogQueueRowStatus);
	}
	
	public boolean isClosedStatus() {
		return STATUS_CLOSED.equals(catalogQueueRowStatus);
	}
	
	public boolean isPendingApproval() {
		return STATUS_PENDING_APPROVAL.equals(catalogQueueRowStatus);
	}

	public boolean hasqId() {
		return qId != null;
	}

	public boolean isItemQcRole() {
		return "Item QC".equals(approvalRole);
	}
	
	public boolean isSendToVendor() {
		return SEND_TO_VENDOR_ACTION.equals(uAction);
	}
	
	public boolean isViewRequestAction() {
		return VIEW_REQUEST_ACTION.equals(uAction);
	}

	public boolean isExpressApproval() {
		return expressApproval;
}

	public void setExpressApproval(boolean isExpress) {
		this.expressApproval = isExpress;
	}
	
	public boolean isChangeLocaleAction() {
		return CHANGE_LOCALE_ACTION.equals(uAction);
	}

	public String getLocaleOverride() {
		return localeOverride;
	}

	public void setLocaleOverride(String localeOverride) {
		this.localeOverride = localeOverride;
	}

	public String getGenerateSdsFromSequence() {
		return generateSdsFromSequence;
	}

	public void setGenerateSdsFromSequence(String generateSdsFromSequence) {
		this.generateSdsFromSequence = generateSdsFromSequence;
	}
}
