package com.tcmis.client.catalog.beans;

import com.tcmis.common.framework.BaseDataBean;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

/******************************************************************************
 * CLASSNAME: CatalogAddApprovalBean <br>
 * @version: 1.0,  <br>
 *****************************************************************************/

public class CatalogAddApprovalBean extends BaseDataBean {
	private String engineeringEvaluation;
	private BigDecimal startingView;
	private BigDecimal approvalGroup;
	private BigDecimal approvalGroupSeq;
	private String qcStatus;
	private String chemicalType;
	private String engEvalFacilityId;
	private String approvalRole;
	private String roleFunction;

	private String facilityName;
	private String applicationDesc;
	private String processDesc;
	private String catPartNo;
	private BigDecimal itemId;
	private BigDecimal requestStatus;
	private String status;
	private Date approvalDate;
	private String approverName;
	private String reason;
	private String partDesc;
	private BigDecimal requestor;
	private String requestorName;
	private BigDecimal materialId;
	private String email;
	private String catalogDesc;
	private String phone;
	private BigDecimal approver;
	private String catalogAddProcessUrl;
	private String companyId;
	private BigDecimal lineItem;
	private String lineStatus;
	private String poss;
    private String approvalPath;
    private String requestorMessage;
    private String databaseName;
    private BigDecimal partId;
    private String materialDesc;
    private String manufacturer;

    private String kitMsds;
    private String customerMsdsNumber;
    private String hasHmrb;
    private String materialCategoryName;
    private String materialSubcategoryName;
    private String rejectedBy;
    private Date rejectedDate;
    private String rejectedComment;
    private String skipWithMsdsApproval;
    private String editHetUsageRecording;
    private String approvalNotificationRole;
    private String approvalNotificationMsg;
    private String approvalNotificationMsgReq;
    private String newMsdsViewer;
    private String applicationUseGroupString;
    private String includeInventoryDetail;
    private BigDecimal caiStartingView;
    private String catalogCompanyId;
    private String catalogId;
    private BigDecimal partGroupNo;
    private String customerMixtureNumber;
    private String mfgTradeName;
    private String replacesMsds;
    private String incomingTesting;
	private String createCatalogQueue;

     //constructor
 	public CatalogAddApprovalBean() {
 	}

 	//setters
	public void setEngineeringEvaluation(String engineeringEvaluation) {
		this.engineeringEvaluation = engineeringEvaluation;
	}

	public void setStartingView(BigDecimal startingView) {
		this.startingView = startingView;
	}

	public void setApprovalGroup(BigDecimal approvalGroup) {
		this.approvalGroup = approvalGroup;
	}

	public void setApprovalGroupSeq(BigDecimal approvalGroupSeq) {
		this.approvalGroupSeq = approvalGroupSeq;
	}

	public void setQcStatus(String qcStatus) {
		this.qcStatus = qcStatus;
	}

	public void setChemicalType(String chemicalType) {
		this.chemicalType = chemicalType;
	}

	public void setEngEvalFacilityId(String engEvalFacilityId) {
		this.engEvalFacilityId = engEvalFacilityId;
	}

	public void setApprovalRole(String approvalRole) {
		this.approvalRole = approvalRole;
	}

	public void setRoleFunction(String roleFunction) {
		this.roleFunction = roleFunction;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public void setApplicationDesc(String applicationDesc) {
		this.applicationDesc = applicationDesc;
	}

	public void setProcessDesc(String processDesc) {
		this.processDesc = processDesc;
	}

	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public void setRequestStatus(BigDecimal requestStatus) {
		this.requestStatus = requestStatus;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public void setPartDesc(String partDesc) {
		this.partDesc = partDesc;
	}

	public void setRequestor(BigDecimal requestor) {
		this.requestor = requestor;
	}

	public void setRequestorName(String requestorName) {
		this.requestorName = requestorName;
	}

	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setCatalogDesc(String catalogDesc) {
		this.catalogDesc = catalogDesc;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setApprover(BigDecimal approver) {
		this.approver = approver;
	}

	public void setCatalogAddProcessUrl(String catalogAddProcessUrl) {
		this.catalogAddProcessUrl = catalogAddProcessUrl;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setLineItem(BigDecimal lineItem) {
		this.lineItem = lineItem;
	}

	public void setLineStatus(String lineStatus) {
		this.lineStatus = lineStatus;
	}
	public void setCustomerMixtureNumber(String customerMixtureNumber) {
		this.customerMixtureNumber = customerMixtureNumber;
	}
	public void setMfgTradeName(String mfgTradeName) {
		this.mfgTradeName = mfgTradeName;
	}	
	//getters
	public String getMfgTradeName() {
		return mfgTradeName;
	}
	public String getCustomerMixtureNumber() {
		return customerMixtureNumber;
	}
	public String getEngineeringEvaluation() {
		return engineeringEvaluation;
	}

	public BigDecimal getStartingView() {
		return startingView;
	}

	public BigDecimal getApprovalGroup() {
		return approvalGroup;
	}

	public BigDecimal getApprovalGroupSeq() {
		return approvalGroupSeq;
	}

	public String getQcStatus() {
		return qcStatus;
	}

	public String getChemicalType() {
		return chemicalType;
	}

	public String getEngEvalFacilityId() {
		return engEvalFacilityId;
	}

	public String getApprovalRole() {
		return approvalRole;
	}

	public String getRoleFunction() {
		return roleFunction;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public String getProcessDesc() {
		return processDesc;
	}

	public String getApplicationDesc() {
		return applicationDesc;
	}

	public String getCatPartNo() {
		return catPartNo;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public BigDecimal getRequestStatus() {
		return requestStatus;
	}

	public String getStatus() {
		return status;
	}

	public Date getApprovalDate() {
		return approvalDate;
	}

	public String getApproverName() {
		return approverName;
	}

	public String getReason() {
		return reason;
	}

	public String getPartDesc() {
		return partDesc;
	}

	public BigDecimal getRequestor() {
		return requestor;
	}

	public String getRequestorName() {
		return requestorName;
	}

	public BigDecimal getMaterialId() {
		return materialId;
	}

	public String getEmail() {
		return email;
	}

	public String getCatalogDesc() {
		return catalogDesc;
	}

	public String getPhone() {
		return phone;
	}

	public BigDecimal getApprover() {
		return approver;
	}

	public String getCatalogAddProcessUrl() {
		return catalogAddProcessUrl;
	}

	public String getCompanyId() {
		return companyId;
	}

	public BigDecimal getLineItem() {
		return lineItem;
	}

	public String getLineStatus() {
		return lineStatus;
	}

	public String getPoss() {
		return poss;
	}

	public void setPoss(String poss) {
		this.poss = poss;
	}

    public String getApprovalPath() {
        return approvalPath;
    }

    public void setApprovalPath(String approvalPath) {
        this.approvalPath = approvalPath;
    }

    public String getRequestorMessage() {
        return requestorMessage;
    }

    public void setRequestorMessage(String requestorMessage) {
        this.requestorMessage = requestorMessage;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public BigDecimal getPartId() {
        return partId;
    }

    public void setPartId(BigDecimal partId) {
        this.partId = partId;
    }

    public String getMaterialDesc() {
        return materialDesc;
    }

    public void setMaterialDesc(String materialDesc) {
        this.materialDesc = materialDesc;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getKitMsds() {
        return kitMsds;
    }

    public void setKitMsds(String kitMsds) {
        this.kitMsds = kitMsds;
    }

    public String getCustomerMsdsNumber() {
        return customerMsdsNumber;
    }

    public void setCustomerMsdsNumber(String customerMsdsNumber) {
        this.customerMsdsNumber = customerMsdsNumber;
    }

    public String getHasHmrb() {
        return hasHmrb;
    }

    public void setHasHmrb(String hasHmrb) {
        this.hasHmrb = hasHmrb;
    }

    public String getMaterialCategoryName() {
        return materialCategoryName;
    }

    public void setMaterialCategoryName(String materialCategoryName) {
        this.materialCategoryName = materialCategoryName;
    }

    public String getMaterialSubcategoryName() {
        return materialSubcategoryName;
    }

    public void setMaterialSubcategoryName(String materialSubcategoryName) {
        this.materialSubcategoryName = materialSubcategoryName;
    }

    public String getRejectedBy() {
        return rejectedBy;
    }

    public void setRejectedBy(String rejectedBy) {
        this.rejectedBy = rejectedBy;
    }

    public Date getRejectedDate() {
        return rejectedDate;
    }

    public void setRejectedDate(Date rejectedDate) {
        this.rejectedDate = rejectedDate;
    }

    public String getRejectedComment() {
        return rejectedComment;
    }

    public void setRejectedComment(String rejectedComment) {
        this.rejectedComment = rejectedComment;
    }

    public String getSkipWithMsdsApproval() {
        return skipWithMsdsApproval;
    }

    public void setSkipWithMsdsApproval(String skipWithMsdsApproval) {
        this.skipWithMsdsApproval = skipWithMsdsApproval;
    }

    public String getEditHetUsageRecording() {
        return editHetUsageRecording;
    }

    public void setEditHetUsageRecording(String editHetUsageRecording) {
        this.editHetUsageRecording = editHetUsageRecording;
    }

    public String getApprovalNotificationRole() {
        return approvalNotificationRole;
    }

    public void setApprovalNotificationRole(String approvalNotificationRole) {
        this.approvalNotificationRole = approvalNotificationRole;
    }

    public String getApprovalNotificationMsg() {
        return approvalNotificationMsg;
    }

    public void setApprovalNotificationMsg(String approvalNotificationMsg) {
        this.approvalNotificationMsg = approvalNotificationMsg;
    }

    public String getApprovalNotificationMsgReq() {
        return approvalNotificationMsgReq;
    }

    public void setApprovalNotificationMsgReq(String approvalNotificationMsgReq) {
        this.approvalNotificationMsgReq = approvalNotificationMsgReq;
    }

    public String getNewMsdsViewer() {
        return newMsdsViewer;
    }

    public void setNewMsdsViewer(String newMsdsViewer) {
        this.newMsdsViewer = newMsdsViewer;
    }

    public String getApplicationUseGroupString() {
        return applicationUseGroupString;
    }

    public void setApplicationUseGroupString(String applicationUseGroupString) {
        this.applicationUseGroupString = applicationUseGroupString;
    }

    public String getIncludeInventoryDetail() {
        return includeInventoryDetail;
    }

    public void setIncludeInventoryDetail(String includeInventoryDetail) {
        this.includeInventoryDetail = includeInventoryDetail;
    }

    public BigDecimal getCaiStartingView() {
        return caiStartingView;
    }

    public void setCaiStartingView(BigDecimal caiStartingView) {
        this.caiStartingView = caiStartingView;
    }

    public String getCatalogCompanyId() {
        return catalogCompanyId;
    }

    public void setCatalogCompanyId(String catalogCompanyId) {
        this.catalogCompanyId = catalogCompanyId;
    }

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public BigDecimal getPartGroupNo() {
        return partGroupNo;
    }

    public void setPartGroupNo(BigDecimal partGroupNo) {
        this.partGroupNo = partGroupNo;
    }

    public String getReplacesMsds() {
        return replacesMsds;
    }

    public void setReplacesMsds(String replacesMsds) {
        this.replacesMsds = replacesMsds;
    }

    public String getIncomingTesting() {
        return incomingTesting;
    }

    public void setIncomingTesting(String incomingTesting) {
        this.incomingTesting = incomingTesting;
    }

	public String getCreateCatalogQueue() {
		return createCatalogQueue;
	}

	public void setCreateCatalogQueue(String createCatalogQueue) {
		this.createCatalogQueue = createCatalogQueue;
	}
}