package com.tcmis.internal.catalog.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseInputBean;

@SuppressWarnings("serial")
public class MfrNotificationRequest extends BaseInputBean {

	private final String CREATE = "create";
	private final String INIT = "init";
	private final String RELOAD = "reload";
	private final String SELECT_MATERIAL = "selectMaterial";
	private final String LOAD_MFR = "loadMfr";
	private final String LOAD_MATERIALS = "loadMaterials";
	private final String LOAD_ITEMS = "loadItems";
	private final String ADD_MATERIALS = "addMaterials";
	private final String ADD_ITEMS = "addItems";
	private final String DELETE_MATERIALS = "deleteMaterials";
	private final String DELETE_ITEMS = "deleteItems";
	private final String SUBMIT = "submit";
	private final String APPROVE = "approve";
	private final String REJECT = "reject";
	private final String DELETE_REQUEST = "deleteRequest";
	
	private Date dateInserted;
	private Date dateSubmitted;
	private BigDecimal insertedBy;
	private BigDecimal mfgId;
	private BigDecimal acquiredMfrId;
	private BigDecimal mfrReqCategoryId;
	private BigDecimal notificationId;
	private String pageUploadCode;
	private BigDecimal qcAssignedTo;
	private BigDecimal qcUser;
	private Date qcDate;
	private String selectedCategories;
	private String status;
	private BigDecimal materialId;
	private BigDecimal itemId;
	private String materialsToAdd;
	private String itemsToAdd;
	private String internalComments;
	
	public Date getDateInserted() {
		return dateInserted;
	}

	public Date getDateSubmitted() {
		return dateSubmitted;
	}

	public BigDecimal getInsertedBy() {
		return insertedBy;
	}

	public BigDecimal getMfgId() {
		return mfgId;
	}

	public BigDecimal getMfrReqCategoryId() {
		return mfrReqCategoryId;
	}

	public BigDecimal getQcAssignedTo() {
		return qcAssignedTo;
	}

	public BigDecimal getQcUser() {
		return qcUser;
	}

	public Date getQcDate() {
		return qcDate;
	}

	public String getSelectedCategories() {
		return selectedCategories;
	}

	public String getStatus() {
		return status;
	}

	public boolean isCreate() {
		return CREATE.equals(uAction);
	}

	public boolean isInit() {
		return INIT.equals(uAction);
	}

	public boolean isReload() {
		return RELOAD.equals(uAction);
	}

	public boolean isSelectMaterial() {
		return SELECT_MATERIAL.equals(uAction);
	}
	
	public void setDateInserted(Date dateInserted) {
		this.dateInserted = dateInserted;
	}

	public void setDateSubmitted(Date dateSubmitted) {
		this.dateSubmitted = dateSubmitted;
	}

	@Override
	public void setHiddenFormFields() {
	}

	public void setInsertedBy(BigDecimal insertedBy) {
		this.insertedBy = insertedBy;
	}

	public void setMfgId(BigDecimal mfgId) {
		this.mfgId = mfgId;
	}

	public void setMfrReqCategoryId(BigDecimal mfrReqCategoryId) {
		this.mfrReqCategoryId = mfrReqCategoryId;
	}

	public void setQcAssignedTo(BigDecimal qcAssignedTo) {
		this.qcAssignedTo = qcAssignedTo;
	}
	
	public void setQcUser(BigDecimal qcUser) {
		this.qcUser = qcUser;
	}
	
	public void setQcDate(Date qcDate) {
		this.qcDate = qcDate;
	}
	
	public void setSelectedCategories(String selectedCategories) {
		this.selectedCategories = selectedCategories;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getMaterialId() {
		return materialId;
	}

	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	
	public boolean isLoadMfr() {
		return LOAD_MFR.equals(uAction);
	}
	
	public boolean isLoadMaterials() {
		return LOAD_MATERIALS.equals(uAction);
	}
	
	public boolean isLoadItems() {
		return LOAD_ITEMS.equals(uAction);
	}
	
	public boolean isAddMaterials() {
		return ADD_MATERIALS.equals(uAction);
	}
	
	public boolean isAddItems() {
		return ADD_ITEMS.equals(uAction);
	}
	
	public boolean isDeleteMaterials() {
		return DELETE_MATERIALS.equals(uAction);
	}
	
	public boolean isDeleteItems() {
		return DELETE_ITEMS.equals(uAction);
	}
	
	public boolean isSubmit() {
		return SUBMIT.equals(uAction);
	}
	
	public boolean isApprove() {
		return APPROVE.equals(uAction);
	}

	public BigDecimal getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(BigDecimal notificationId) {
		this.notificationId = notificationId;
	}

	public String getPageUploadCode() {
		return pageUploadCode;
	}

	public void setPageUploadCode(String pageUploadCode) {
		this.pageUploadCode = pageUploadCode;
	}

	public BigDecimal getAcquiredMfrId() {
		return acquiredMfrId;
	}

	public void setAcquiredMfrId(BigDecimal acquiredMfrId) {
		this.acquiredMfrId = acquiredMfrId;
	}

	public String getMaterialsToAdd() {
		return materialsToAdd;
	}

	public void setMaterialsToAdd(String materialsToAdd) {
		this.materialsToAdd = materialsToAdd;
	}

	public String getItemsToAdd() {
		return itemsToAdd;
	}

	public void setItemsToAdd(String itemsToAdd) {
		this.itemsToAdd = itemsToAdd;
	}
	
	public boolean isReject() {
		return REJECT.equals(uAction);
	}
	
	public boolean isDeleteRequest() {
		return DELETE_REQUEST.equals(uAction);
	}
	
	public boolean isPendingApproval() {
		return "Pending Approval".equals(status);
	}
	
	public boolean isApproved() {
		return "Approved".equals(status);
	}

	public String getInternalComments() {
		return internalComments;
	}

	public void setInternalComments(String internalComments) {
		if (internalComments != null) {
			internalComments = internalComments.replace("\r\n", "\n");
		}
		this.internalComments = internalComments;
	}
}
