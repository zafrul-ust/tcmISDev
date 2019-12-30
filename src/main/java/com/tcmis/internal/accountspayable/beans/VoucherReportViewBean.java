package com.tcmis.internal.accountspayable.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: VoucherReportViewBean <br>
 * @version: 1.0, Sep 13, 2006 <br>
 *****************************************************************************/


public class VoucherReportViewBean extends BaseDataBean {

	private BigDecimal voucherId;
	private BigDecimal radianPo;
	private BigDecimal apUserId;
	private String buyerName;
	private String buyerEmail;
	private String buyerPhone;
	private String supplierName;
	private String supplierInvoiceId;
	private Date invoiceDate;
	private BigDecimal voucherAge;
	private String poTerms;
	private String supplierTerms;
	private BigDecimal netInvoiceAmount;
	private String availableReceipts;
	private String status;
	private String apNote;
	private BigDecimal approver;
	private Date approvedDate;
	private BigDecimal qcUser;
	private Date qcDate;
	private Date dateVoucherImported;
	private String apUserName;
	private String apApproverName;
	private String apQcUserName;
	private String currencyId;
	private String opsEntityId;
	private String inventoryGroup;
	private String supplier;
	private String branchPlant;
	
	private String poLine;
	
	private String poHubName;
	private String poInventoryGroupName;
	private String poOperatingEntityName;
	private String supplierOpsEntityId;
	private BigDecimal buyerId;

	//constructor
	public VoucherReportViewBean() {
	}

	public String getPoHubName() {
		return poHubName;
	}

	public void setPoHubName(String poHubName) {
		this.poHubName = poHubName;
	}

	public String getPoInventoryGroupName() {
		return poInventoryGroupName;
	}

	public void setPoInventoryGroupName(String poInventoryGroupName) {
		this.poInventoryGroupName = poInventoryGroupName;
	}

	public String getPoOperatingEntityName() {
		return poOperatingEntityName;
	}

	public void setPoOperatingEntityName(String poOperatingEntityName) {
		this.poOperatingEntityName = poOperatingEntityName;
	}

	public String getSupplierOpsEntityId() {
		return supplierOpsEntityId;
	}

	public void setSupplierOpsEntityId(String supplierOpsEntityId) {
		this.supplierOpsEntityId = supplierOpsEntityId;
	}

	//setters
	public void setVoucherId(BigDecimal voucherId) {
		this.voucherId = voucherId;
	}
	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}
	public void setApUserId(BigDecimal apUserId) {
		this.apUserId = apUserId;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}
	public void setBuyerPhone(String buyerPhone) {
		this.buyerPhone = buyerPhone;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public void setSupplierInvoiceId(String supplierInvoiceId) {
		this.supplierInvoiceId = supplierInvoiceId;
	}
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public void setVoucherAge(BigDecimal voucherAge) {
		this.voucherAge = voucherAge;
	}
	public void setPoTerms(String poTerms) {
		this.poTerms = poTerms;
	}
	public void setSupplierTerms(String supplierTerms) {
		this.supplierTerms = supplierTerms;
	}
	public void setNetInvoiceAmount(BigDecimal netInvoiceAmount) {
		this.netInvoiceAmount = netInvoiceAmount;
	}
	public void setAvailableReceipts(String availableReceipts) {
		this.availableReceipts = availableReceipts;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setApNote(String apNote) {
		this.apNote = apNote;
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


	//getters
	public BigDecimal getVoucherId() {
		return voucherId;
	}
	public BigDecimal getRadianPo() {
		return radianPo;
	}
	public BigDecimal getApUserId() {
		return apUserId;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public String getBuyerEmail() {
		return buyerEmail;
	}
	public String getBuyerPhone() {
		return buyerPhone;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public String getSupplierInvoiceId() {
		return supplierInvoiceId;
	}
	public Date getInvoiceDate() {
		return invoiceDate;
	}
	public BigDecimal getVoucherAge() {
		return voucherAge;
	}
	public String getPoTerms() {
		return poTerms;
	}
	public String getSupplierTerms() {
		return supplierTerms;
	}
	public BigDecimal getNetInvoiceAmount() {
		return netInvoiceAmount;
	}
	public String getAvailableReceipts() {
		return availableReceipts;
	}
	public String getStatus() {
		return status;
	}
	public String getApNote() {
		return apNote;
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

	public String getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	public String getBranchPlant() {
		return branchPlant;
	}

	public void setBranchPlant(String branchPlant) {
		this.branchPlant = branchPlant;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public String getOpsEntityId() {
		return opsEntityId;
	}

	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getPoLine() {
		return poLine;
	}

	public void setPoLine(String poLine) {
		this.poLine = poLine;
	}
	
	public BigDecimal getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(BigDecimal buyerId) {
		this.buyerId = buyerId;
	}

}