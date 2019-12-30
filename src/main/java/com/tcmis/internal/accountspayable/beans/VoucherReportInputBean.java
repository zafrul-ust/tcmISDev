package com.tcmis.internal.accountspayable.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: VoucherReportViewBean <br>
 * @version: 1.0, Sep 13, 2006 <br>
 *****************************************************************************/


public class VoucherReportInputBean extends BaseDataBean {

	private BigDecimal apUserId;
	private String supplierName;
	private String supplierInvoiceId;
	private Date invoiceDate;
	private BigDecimal voucherAge;
	private String availableReceipts;
	private String status;
	private Date approvedDateBegin;
	private Date approvedDateEnd;
    private String sortBy;
    private String showOnlyWithReceipts;
    private String showOnlyToBeQced;
    private String submitSearch;
    private String userAction;

//	private String apNote;
//	private BigDecimal approver;
//	private Date approvedDate;
//	private String poTerms;
//	private String supplierTerms;
//	private BigDecimal netInvoiceAmount;
//	private String buyerName;
//	private String buyerEmail;
//	private String buyerPhone;
//	private BigDecimal voucherId;
//	private BigDecimal radianPo;
//	private BigDecimal qcUser;
//	private Date qcDate;
//	private Date dateVoucherImported;
//	private String apUserName;
//	private String apApproverName;
//	private String apQcUserName;
    
    // These are for Quick Supplier View
    private String opsEntityId;
	private String inventoryGroup;
	private String supplier;
	private String branchPlant;
	private BigDecimal buyerId;

	public BigDecimal getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(BigDecimal buyerId) {
		this.buyerId = buyerId;
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

	//constructor
	public VoucherReportInputBean() {
	}

	//setters
	public void setApUserId(BigDecimal apUserId) {
		this.apUserId = apUserId;
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
	public void setAvailableReceipts(String availableReceipts) {
		this.availableReceipts = availableReceipts;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setApprovedDateBegin(Date approvedDateBegin) {
		this.approvedDateBegin = approvedDateBegin;
	}
	public void setApprovedDateEnd(Date approvedDateEnd) {
		this.approvedDateEnd = approvedDateEnd;
	}
        
        public void setSortBy(String sortBy) {
           this.sortBy = sortBy;
        }        
        public void setSubmitSearch(String submitSearch) {
            this.submitSearch = submitSearch;
        }
        
//	public void setVoucherId(BigDecimal voucherId) {
//		this.voucherId = voucherId;
//	}
//	public void setRadianPo(BigDecimal radianPo) {
//		this.radianPo = radianPo;
//	}
//	public void setBuyerName(String buyerName) {
//		this.buyerName = buyerName;
//	}
//	public void setBuyerEmail(String buyerEmail) {
//		this.buyerEmail = buyerEmail;
//	}
//	public void setBuyerPhone(String buyerPhone) {
//		this.buyerPhone = buyerPhone;
//	}
//	public void setPoTerms(String poTerms) {
//		this.poTerms = poTerms;
//	}
//	public void setSupplierTerms(String supplierTerms) {
//		this.supplierTerms = supplierTerms;
//	}
//	public void setNetInvoiceAmount(BigDecimal netInvoiceAmount) {
//		this.netInvoiceAmount = netInvoiceAmount;
//	}
//	public void setApNote(String apNote) {
//		this.apNote = apNote;
//	}
//	public void setApprover(BigDecimal approver) {
//		this.approver = approver;
//	}
//	public void setApprovedDate(Date approvedDate) {
//		this.approvedDate = approvedDate;
//	}
//	public void setQcUser(BigDecimal qcUser) {
//		this.qcUser = qcUser;
//	}
//	public void setQcDate(Date qcDate) {
//		this.qcDate = qcDate;
//	}
//	public void setDateVoucherImported(Date dateVoucherImported) {
//		this.dateVoucherImported = dateVoucherImported;
//	}
//	public void setApUserName(String apUserName) {
//		this.apUserName = apUserName;
//	}
//	public void setApApproverName(String apApproverName) {
//		this.apApproverName = apApproverName;
//	}
//	public void setApQcUserName(String apQcUserName) {
//		this.apQcUserName = apQcUserName;
//	}
    public void setUserAction(String userAction) {
        this.userAction = userAction;
    }

	//getters
	public BigDecimal getApUserId() {
		return apUserId;
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
	public String getAvailableReceipts() {
		return availableReceipts;
	}
	public String getStatus() {
		return status;
	}
	public Date getApprovedDateBegin() {
		return approvedDateBegin;
	}
	public Date getApprovedDateEnd() {
		return approvedDateEnd;
	}
        
        public String getSortBy() {
           return sortBy;
        }
        
        public String getSubmitSearch() {
           return submitSearch;
        }         
                          
        /**
         * Getter for property showOnlyWithReceipts.
         * @return Value of property showOnlyWithReceipts.
         */
        public java.lang.String getShowOnlyWithReceipts() {
           return showOnlyWithReceipts;
        }
        
        /**
         * Setter for property showOnlyWithReceipts.
         * @param showOnlyWithReceipts New value of property showOnlyWithReceipts.
         */
        public void setShowOnlyWithReceipts(java.lang.String showOnlyWithReceipts) {
           this.showOnlyWithReceipts = showOnlyWithReceipts;
        }
        
        /**
         * Getter for property showOnlyToBeQced.
         * @return Value of property showOnlyToBeQced.
         */
        public java.lang.String getShowOnlyToBeQced() {
           return showOnlyToBeQced;
        }
        
        /**
         * Setter for property showOnlyToBeQced.
         * @param showOnlyToBeQced New value of property showOnlyToBeQced.
         */
        public void setShowOnlyToBeQced(java.lang.String showOnlyToBeQced) {
           this.showOnlyToBeQced = showOnlyToBeQced;
        }
        
//	public BigDecimal getVoucherId() {
//		return voucherId;
//	}
//	public BigDecimal getRadianPo() {
//		return radianPo;
//	}
//	public String getBuyerName() {
//		return buyerName;
//	}
//	public String getBuyerEmail() {
//		return buyerEmail;
//	}
//	public String getBuyerPhone() {
//		return buyerPhone;
//	}
//	public String getPoTerms() {
//		return poTerms;
//	}
//	public String getSupplierTerms() {
//		return supplierTerms;
//	}
//	public BigDecimal getNetInvoiceAmount() {
//		return netInvoiceAmount;
//	}
//	public String getApNote() {
//		return apNote;
//	}
//	public BigDecimal getApprover() {
//		return approver;
//	}
//	public Date getApprovedDate() {
//		return approvedDate;
//	}
//	public BigDecimal getQcUser() {
//		return qcUser;
//	}
//	public Date getQcDate() {
//		return qcDate;
//	}
//	public Date getDateVoucherImported() {
//		return dateVoucherImported;
//	}
//	public String getApUserName() {
//		return apUserName;
//	}
//	public String getApApproverName() {
//		return apApproverName;
//	}
//	public String getApQcUserName() {
//		return apQcUserName;
//	}
    public String getUserAction() {
        return userAction;
    }

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}    
}