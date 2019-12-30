package com.tcmis.internal.accountspayable.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: VoucherViewBean <br>
 * @version: 1.0, Jan 29, 2008 <br>
 *****************************************************************************/


public class VoucherViewBean extends BaseDataBean {

	private BigDecimal voucherId;
	private String supplier;
	private String supplierInvoiceId;
	private String originalInvoiceId;
	private Date invoiceDate;
	private Date dateInvoiceReceived;
	private BigDecimal radianPo;
	private String customerRefPo;
	private String invoiceFilePath;
	private BigDecimal netInvoiceAmount;
	private Date pymtTermStartDate;
	private Date dateToPay;
	private String remitToLocId;
	private BigDecimal supplierContactId;
	private String originalSupplier;
	private String status;
	private String paymentTerms;
	private String apNote;
	private BigDecimal apUserId;
	private Date dateLastUpdated;
	private Date dateSentToHass;
	private BigDecimal invoiceAmount;
	private String invoiceUrl;
	private String voucherBillingLocation;
	private String verified;
	private Date statusDate;
	private String changesAfterApproveNotes;
	private Date insertDate;
	private String currencyId;
	private BigDecimal eTaxRate;
	private BigDecimal approver;
	private Date approvedDate;
	private BigDecimal qcUser;
	private Date qcDate;
	private Date dateVoucherImported;
	private String apUserName;
	private String apApproverName;
	private String apQcUserName;
	private BigDecimal origApprover;
	private String apOrigApproverName;
	private String uploadStatus;


	//constructor
	public VoucherViewBean() {
	}

	//setters
	public void setVoucherId(BigDecimal voucherId) {
		this.voucherId = voucherId;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public void setSupplierInvoiceId(String supplierInvoiceId) {
		this.supplierInvoiceId = supplierInvoiceId;
	}
	public void setOriginalInvoiceId(String originalInvoiceId) {
		this.originalInvoiceId = originalInvoiceId;
	}
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public void setDateInvoiceReceived(Date dateInvoiceReceived) {
		this.dateInvoiceReceived = dateInvoiceReceived;
	}
	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}
	public void setCustomerRefPo(String customerRefPo) {
		this.customerRefPo = customerRefPo;
	}
	public void setInvoiceFilePath(String invoiceFilePath) {
		this.invoiceFilePath = invoiceFilePath;
	}
	public void setNetInvoiceAmount(BigDecimal netInvoiceAmount) {
		this.netInvoiceAmount = netInvoiceAmount;
	}
	public void setPymtTermStartDate(Date pymtTermStartDate) {
		this.pymtTermStartDate = pymtTermStartDate;
	}
	public void setDateToPay(Date dateToPay) {
		this.dateToPay = dateToPay;
	}
	public void setRemitToLocId(String remitToLocId) {
		this.remitToLocId = remitToLocId;
	}
	public void setSupplierContactId(BigDecimal supplierContactId) {
		this.supplierContactId = supplierContactId;
	}
	public void setOriginalSupplier(String originalSupplier) {
		this.originalSupplier = originalSupplier;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}
	public void setApNote(String apNote) {
		this.apNote = apNote;
	}
	public void setApUserId(BigDecimal apUserId) {
		this.apUserId = apUserId;
	}
	public void setDateLastUpdated(Date dateLastUpdated) {
		this.dateLastUpdated = dateLastUpdated;
	}
	public void setDateSentToHass(Date dateSentToHass) {
		this.dateSentToHass = dateSentToHass;
	}
	public void setInvoiceAmount(BigDecimal invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}
	public void setInvoiceUrl(String invoiceUrl) {
		this.invoiceUrl = invoiceUrl;
	}
	public void setVoucherBillingLocation(String voucherBillingLocation) {
		this.voucherBillingLocation = voucherBillingLocation;
	}
	public void setVerified(String verified) {
		this.verified = verified;
	}
	public void setStatusDate(Date statusDate) {
		this.statusDate = statusDate;
	}
	public void setChangesAfterApproveNotes(String changesAfterApproveNotes) {
		this.changesAfterApproveNotes = changesAfterApproveNotes;
	}
	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}
	public void setETaxRate(BigDecimal eTaxRate) {
		this.eTaxRate = eTaxRate;
	}
	public void setApprover(BigDecimal approver) {
		this.approver = approver;
	}
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	public void setQcUser(BigDecimal qcUser) {
		this.qcUser = qcUser;
	}
	public void setQcDate(Date qcDate) {
		this.qcDate = qcDate;
	}
	public void setDateVoucherImported(Date dateVoucherImported) {
		this.dateVoucherImported = dateVoucherImported;
	}
	public void setApUserName(String apUserName) {
		this.apUserName = apUserName;
	}
	public void setApApproverName(String apApproverName) {
		this.apApproverName = apApproverName;
	}
	public void setApQcUserName(String apQcUserName) {
		this.apQcUserName = apQcUserName;
	}
	public void setOrigApprover(BigDecimal origApprover) {
		this.origApprover = origApprover;
	}
	public void setApOrigApproverName(String apOrigApproverName) {
		this.apOrigApproverName = apOrigApproverName;
	}
	public void setUploadStatus(String uploadStatus) {
		this.uploadStatus = uploadStatus;
	}


	//getters
	public BigDecimal getVoucherId() {
		return voucherId;
	}
	public String getSupplier() {
		return supplier;
	}
	public String getSupplierInvoiceId() {
		return supplierInvoiceId;
	}
	public String getOriginalInvoiceId() {
		return originalInvoiceId;
	}
	public Date getInvoiceDate() {
		return invoiceDate;
	}
	public Date getDateInvoiceReceived() {
		return dateInvoiceReceived;
	}
	public BigDecimal getRadianPo() {
		return radianPo;
	}
	public String getCustomerRefPo() {
		return customerRefPo;
	}
	public String getInvoiceFilePath() {
		return invoiceFilePath;
	}
	public BigDecimal getNetInvoiceAmount() {
		return netInvoiceAmount;
	}
	public Date getPymtTermStartDate() {
		return pymtTermStartDate;
	}
	public Date getDateToPay() {
		return dateToPay;
	}
	public String getRemitToLocId() {
		return remitToLocId;
	}
	public BigDecimal getSupplierContactId() {
		return supplierContactId;
	}
	public String getOriginalSupplier() {
		return originalSupplier;
	}
	public String getStatus() {
		return status;
	}
	public String getPaymentTerms() {
		return paymentTerms;
	}
	public String getApNote() {
		return apNote;
	}
	public BigDecimal getApUserId() {
		return apUserId;
	}
	public Date getDateLastUpdated() {
		return dateLastUpdated;
	}
	public Date getDateSentToHass() {
		return dateSentToHass;
	}
	public BigDecimal getInvoiceAmount() {
		return invoiceAmount;
	}
	public String getInvoiceUrl() {
		return invoiceUrl;
	}
	public String getVoucherBillingLocation() {
		return voucherBillingLocation;
	}
	public String getVerified() {
		return verified;
	}
	public Date getStatusDate() {
		return statusDate;
	}
	public String getChangesAfterApproveNotes() {
		return changesAfterApproveNotes;
	}
	public Date getInsertDate() {
		return insertDate;
	}
	public String getCurrencyId() {
		return currencyId;
	}
	public BigDecimal getETaxRate() {
		return eTaxRate;
	}
	public BigDecimal getApprover() {
		return approver;
	}
	public Date getApprovedDate() {
		return approvedDate;
	}
	public BigDecimal getQcUser() {
		return qcUser;
	}
	public Date getQcDate() {
		return qcDate;
	}
	public Date getDateVoucherImported() {
		return dateVoucherImported;
	}
	public String getApUserName() {
		return apUserName;
	}
	public String getApApproverName() {
		return apApproverName;
	}
	public String getApQcUserName() {
		return apQcUserName;
	}
	public BigDecimal getOrigApprover() {
		return origApprover;
	}
	public String getApOrigApproverName() {
		return apOrigApproverName;
	}
	public String getUploadStatus() {
		return uploadStatus;
	}
}