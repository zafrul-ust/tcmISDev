package com.tcmis.internal.catalog.beans;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: CatalogAddRequestOvBean <br>
 * @version: 1.0, Aug 20, 2010 <br>
 *****************************************************************************/

@SuppressWarnings("serial")
public class CatalogAddRequestVendorBean extends BaseDataBean {

	private final String SDS_SOURCING_TASK = "SDS Sourcing";
	private final String PENDING_APPROVAL_STATUS = "Pending Approval";
	private final int INACTIVE_USER = 111;
	
	private BigDecimal qId;
	private boolean grabbed;
	private BigDecimal assignedTo;
	private String assignedToName;
	private String task;
	private String status;
	private String materialDesc;
	private String manufacturer;
	private String localeCode;
	private String localeDisplay;
	private Date problemReportDate;
	private BigDecimal problemReportedBy;
	private String problemReportedByName;
	private String problemStatus;
	private String problemType;
	private String comments;
	private Date taskCompleteDate;
	private Date supplierApprovedDate;
	private BigDecimal supplierApprovedBy;
	private String supplierApprovedByName;
	private Date approvedDate;
	private BigDecimal approvedBy;
	private String approvedByName;
	private BigDecimal invoiceNumber;
	private Date invoicedDate;
	private String supplier;
	private BigDecimal catalogAddRequestId;
	private BigDecimal lineItem;
	private String companyId;
	private String originalSupplier;
	private String altSupplier;
	private String companyName;
	private String facilityName;
	private String catalogDesc;
	private Date insertDate;
	private String requestComments;
	private BigDecimal slaTurnaroundTime;

    //constructor
	public CatalogAddRequestVendorBean() {
	}
	
	public BigDecimal getQId() {
		return qId;
	}

	public void setQId(BigDecimal qId) {
		this.qId = qId;
	}
	
	public String getRequestComments() {
		return requestComments;
	}

	public void setRequestComments(String requestComments) {
		this.requestComments = requestComments;
	}

	public BigDecimal getqId() {
		return qId;
	}

	public void setqId(BigDecimal qId) {
		this.qId = qId;
	}

	public boolean isGrabbed() {
		return grabbed;
	}

	public void setGrabbed(boolean grabbed) {
		this.grabbed = grabbed;
	}

	public BigDecimal getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(BigDecimal assignedTo) {
		this.assignedTo = assignedTo;
	}

	public String getAssignedToName() {
		return assignedToName;
	}

	public void setAssignedToName(String assignedToName) {
		this.assignedToName = assignedToName;
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

	public Date getTaskCompleteDate() {
		return taskCompleteDate;
	}

	public Date getProblemReportDate() {
		return problemReportDate;
	}

	public void setProblemReportDate(Date problemReportDate) {
		this.problemReportDate = problemReportDate;
	}

	public BigDecimal getProblemReportedBy() {
		return problemReportedBy;
	}

	public void setProblemReportedBy(BigDecimal problemReportedBy) {
		this.problemReportedBy = problemReportedBy;
	}

	public String getProblemReportedByName() {
		return problemReportedByName;
	}

	public void setProblemReportedByName(String problemReportedByName) {
		this.problemReportedByName = problemReportedByName;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public void setTaskCompleteDate(Date taskCompleteDate) {
		this.taskCompleteDate = taskCompleteDate;
	}

	public Date getSupplierApprovedDate() {
		return supplierApprovedDate;
	}

	public void setSupplierApprovedDate(Date supplierApprovedDate) {
		this.supplierApprovedDate = supplierApprovedDate;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	public BigDecimal getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(BigDecimal approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getApprovedByName() {
		return approvedByName;
	}

	public void setApprovedByName(String approvedByName) {
		this.approvedByName = approvedByName;
	}

	public BigDecimal getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(BigDecimal invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public Date getInvoicedDate() {
		return invoicedDate;
	}

	public void setInvoicedDate(Date invoicedDate) {
		this.invoicedDate = invoicedDate;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public BigDecimal getCatalogAddRequestId() {
		return catalogAddRequestId;
	}

	public void setCatalogAddRequestId(BigDecimal catalogAddRequestId) {
		this.catalogAddRequestId = catalogAddRequestId;
	}

	public BigDecimal getLineItem() {
		return lineItem;
	}

	public void setLineItem(BigDecimal lineItem) {
		this.lineItem = lineItem;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getOriginalSupplier() {
		return originalSupplier;
	}

	public void setOriginalSupplier(String originalSupplier) {
		this.originalSupplier = originalSupplier;
	}

	public boolean supplierChanged() {
		return !StringHandler.emptyIfNull(supplier).equals(originalSupplier);
	}

	public String getProblemType() {
		return problemType;
	}

	public void setProblemType(String problemType) {
		this.problemType = problemType;
	}

	public BigDecimal getSupplierApprovedBy() {
		return supplierApprovedBy;
	}

	public void setSupplierApprovedBy(BigDecimal supplierApprovedBy) {
		this.supplierApprovedBy = supplierApprovedBy;
	}

	public String getSupplierApprovedByName() {
		return supplierApprovedByName;
	}

	public void setSupplierApprovedByName(String supplierApprovedByName) {
		this.supplierApprovedByName = supplierApprovedByName;
	}

	public String getProblemStatus() {
		return problemStatus;
	}

	public void setProblemStatus(String problemStatus) {
		this.problemStatus = problemStatus;
	}
	
	public String getAltSupplier() {
		return altSupplier;
	}

	public void setAltSupplier(String altSupplier) {
		this.altSupplier = altSupplier;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public String getCatalogDesc() {
		return catalogDesc;
	}

	public void setCatalogDesc(String catalogDesc) {
		this.catalogDesc = catalogDesc;
	}

	public boolean hasAssignedTo() {
		return assignedTo != null && assignedTo.intValue() != INACTIVE_USER;
	}
	
	public boolean isSourcingTask() {
		return SDS_SOURCING_TASK.equals(this.task);
	}

	public boolean isPendingApprovalStatus() {
		return PENDING_APPROVAL_STATUS.equals(this.status);
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}
	
	public BigDecimal getSlaTurnaroundTime() {
		return slaTurnaroundTime==null?new BigDecimal(2):slaTurnaroundTime;
	}

	public void setSlaTurnaroundTime(BigDecimal slaTurnaroundTime) {
		this.slaTurnaroundTime = slaTurnaroundTime;
	}
	
	private boolean isWeekend(LocalDate date) {
		return DayOfWeek.SATURDAY.compareTo(date.getDayOfWeek()) == 0
				|| DayOfWeek.SUNDAY.compareTo(date.getDayOfWeek()) == 0;
	}
	
	public Date getSlaDeadline() {
		ZonedDateTime deadline = null;
		if (insertDate != null) {
			ZonedDateTime goTime = null;
			ZonedDateTime insertTime = insertDate.toInstant().atZone(ZoneId.systemDefault());
			if (isWeekend(insertTime.toLocalDate())) {
				goTime = insertTime.toLocalDate()
						.with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atStartOfDay()
						.atZone(ZoneId.systemDefault());
			}
			else {
				goTime = insertTime;
			}
			
			deadline = goTime;
			double businessDays = getSlaTurnaroundTime().doubleValue();
			for (double i = 1;i < businessDays; businessDays--) {
				deadline = deadline.plusDays(1);
				if (isWeekend(deadline.toLocalDate())) {
					deadline = deadline.plusDays(2);
				}
			}
			if (businessDays > 0) {
				deadline = deadline.plusHours((long)businessDays*24);
				if (isWeekend(deadline.toLocalDate())) {
					deadline = deadline.plusDays(2);
				}
			}
		}
		
		return Date.from(deadline.toInstant());
	}
	
	public BigDecimal getTimeRemaining() {
		return getTimeRemainingFrom(new Date());
	}
	
	public BigDecimal getTimeRemainingFrom(Date present) {
		long diff = getSlaDeadline().getTime() - present.getTime();
		
		return BigDecimal.valueOf(diff / 1000 / 60 / 60 / 24d).setScale(1, RoundingMode.HALF_UP);
	}
}