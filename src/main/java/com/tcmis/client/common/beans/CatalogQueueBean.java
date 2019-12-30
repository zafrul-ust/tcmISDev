package com.tcmis.client.common.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.util.StringHandler;

public class CatalogQueueBean {

	public static final String WESCO_SUPPLIER_ID = "10143465";
	public static final String WESCO_ALT_SUPPLIER_ID = "10141169";
	private final String CLOSED_STATUS = "Closed";
	private final String AUTHORING_TASK = "SDS Authoring";

	private BigDecimal qId;
	private BigDecimal itemId;
	private BigDecimal quantity;
	private Date insertDate;
	private String supplier;
	private Date supplierAssignedDate;
	private BigDecimal assignedTo;
	private Date assignedDate;
	private Date taskCompleteDate;
	private BigDecimal supplierApprovedBy;
	private Date supplierApprovedDate;
	private BigDecimal approvedBy;
	private Date approvedDate;
	private BigDecimal catalogAddRequestId;
	private String localeCode;
	private String localeDisplay;
	private String status;
	private String task;
	private BigDecimal catalogAddItemId;
	private BigDecimal lineItem;
	private String materialIdSourced;
	private String altSupplier;
	private String companyId;
	//added below columns
	private BigDecimal materialId;
	private Date revisionDate;

	public BigDecimal getqId() {
		return qId;
	}
	public void setqId(BigDecimal qId) {
		this.qId = qId;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public Date getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public Date getSupplierAssignedDate() {
		return supplierAssignedDate;
	}
	public void setSupplierAssignedDate(Date supplierAssignedDate) {
		this.supplierAssignedDate = supplierAssignedDate;
	}
	public BigDecimal getAssignedTo() {
		return assignedTo;
	}
	public void setAssignedTo(BigDecimal assignedTo) {
		this.assignedTo = assignedTo;
	}
	public Date getAssignedDate() {
		return assignedDate;
	}
	public void setAssignedDate(Date assignedDate) {
		this.assignedDate = assignedDate;
	}
	public Date getTaskCompleteDate() {
		return taskCompleteDate;
	}
	public void setTaskCompleteDate(Date taskCompleteDate) {
		this.taskCompleteDate = taskCompleteDate;
	}
	public BigDecimal getSupplierApprovedBy() {
		return supplierApprovedBy;
	}
	public void setSupplierApprovedBy(BigDecimal supplierApprovedBy) {
		this.supplierApprovedBy = supplierApprovedBy;
	}
	public Date getSupplierApprovedDate() {
		return supplierApprovedDate;
	}
	public void setSupplierApprovedDate(Date supplierApprovedDate) {
		this.supplierApprovedDate = supplierApprovedDate;
	}
	public BigDecimal getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(BigDecimal approvedBy) {
		this.approvedBy = approvedBy;
	}
	public Date getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	public BigDecimal getCatalogAddRequestId() {
		return catalogAddRequestId;
	}
	public void setCatalogAddRequestId(BigDecimal catalogAddRequestId) {
		this.catalogAddRequestId = catalogAddRequestId;
	}
	public String getLocaleCode() {
		return localeCode;
	}
	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}
	public String getLocaleDisplay() {
		return localeDisplay;
	}
	public void setLocaleDisplay(String localeDisplay) {
		this.localeDisplay = localeDisplay;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
	}
	public BigDecimal getCatalogAddItemId() {
		return catalogAddItemId;
	}
	public void setCatalogAddItemId(BigDecimal catalogAddItemId) {
		this.catalogAddItemId = catalogAddItemId;
	}
	public BigDecimal getLineItem() {
		return lineItem;
	}
	public void setLineItem(BigDecimal lineItem) {
		this.lineItem = lineItem;
	}
	public boolean isClosedStatus() {
		return CLOSED_STATUS.equals(status);
	}
	public boolean isAuthoringTask() {
		return AUTHORING_TASK.equals(task);
	}
	public boolean hasqId() {
		return qId != null;
	}
	public boolean isSupplierWesco() {
		return WESCO_SUPPLIER_ID.equals(supplier);
	}
	public String getMaterialIdSourced() {
		return materialIdSourced;
	}
	public void setMaterialIdSourced(String materialIdSourced) {
		this.materialIdSourced = materialIdSourced;
	}
	public String getAltSupplier() {
		return altSupplier;
	}
	public void setAltSupplier(String altSupplier) {
		this.altSupplier = altSupplier;
	}
	public boolean hasAltSupplier() {
		return ! StringHandler.isBlankString(altSupplier);
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public BigDecimal getMaterialId() {
		return materialId;
	}

	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}

	public Date getRevisionDate() {
		return revisionDate;
	}

	public void setRevisionDate(Date revisionDate) {
		this.revisionDate = revisionDate;
	}
}