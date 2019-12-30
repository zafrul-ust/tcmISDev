package com.tcmis.internal.catalog.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseInputBean;
import com.tcmis.common.util.StringHandler;

public class ProblemQueueReasonInputBean extends BaseInputBean {
	private final String OPEN = "open";
	private final String SUBMIT = "submit";
	private final String CATALOG_NOTIFY = "catalog";
	private final String SHOW_HISTORY_ACTION = "showHistory";

	private BigDecimal qId;
	private String task;
	private String status;
	private String problemType;
	private String comments;
	private String notify;
	private BigDecimal catalogAddRequestId;
	private String reAssignTo;
	
	public BigDecimal getqId() {
		return qId;
	}

	public void setqId(BigDecimal qId) {
		this.qId = qId;
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

	public String getProblemType() {
		return problemType;
	}

	public void setProblemType(String problemType) {
		this.problemType = problemType;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public boolean isSendEmailToCatalog() {
		return CATALOG_NOTIFY.equals(notify);
	}

	public String getNotify() {
		return notify;
	}

	public void setNotify(String notify) {
		this.notify = notify;
	}

	public boolean isOpen() {
		return OPEN.equals(uAction);
	}
	
	public boolean isSubmit() {
		return SUBMIT.equals(uAction);
	}
	
	public boolean isAssignedStatus() {
		return CatalogAddReqMsdsInputBean.STATUS_ASSIGNED.equals(status);
	}
	
	public boolean isPendingQcStatus() {
		return CatalogAddReqMsdsInputBean.STATUS_PENDING_QC.equals(status);
	}
	
	public boolean isPendingApprovalStatus() {
		return CatalogAddReqMsdsInputBean.STATUS_PENDING_APPROVAL.equals(status);
	}
	
	public boolean isFailingQc() {
		return (isPendingApprovalStatus() || isPendingQcStatus());
	}
	
	@Override
	public void setHiddenFormFields() {
		// TODO Auto-generated method stub
		
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

	public boolean hasReAssignTo() {
		return !StringHandler.isBlankString(reAssignTo);
	}
	
	public boolean isReAssignToVendor() {
		return hasReAssignTo() && "vendor".equalsIgnoreCase(reAssignTo);
	}
	
	public boolean isReAssignToCatalog() {
		return hasReAssignTo() && "catalog".equalsIgnoreCase(reAssignTo);
	}

	public String getReAssignTo() {
		return reAssignTo;
	}

	public void setReAssignTo(String reAssignTo) {
		this.reAssignTo = reAssignTo;
	}
	
	public boolean isShowHistoryAction() {
		return SHOW_HISTORY_ACTION.equals(uAction);
	}
	
	public boolean isSourcingTask() {
		return CatalogAddReqMsdsInputBean.TASK_SOURCING.equals(task);
	}
	
	public boolean isItemCreationTask() {
		return CatalogAddReqMsdsInputBean.TASK_ITEM_CREATION.equals(task);
	}
}
