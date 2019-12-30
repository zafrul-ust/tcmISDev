package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: ReceiptAuditBean <br>
 * @version: 1.0, Oct 9, 2006 <br>
 *****************************************************************************/


public class ReceiptAuditBean extends BaseDataBean {

	private BigDecimal itemId;
	private String mfgLot;
	private String bin;
	private Date dateOfReceipt;
	private Date transactionDate;
	private BigDecimal radianPo;
	private BigDecimal poLine;
	private Date dateOfManufacture;
	private BigDecimal quantityReceived;
	private BigDecimal docNum;
	private String docType;
	private BigDecimal receiptId;
	private String lotStatus;
	private BigDecimal transferRequestId;
	private String hub;
	private String receiverId;
	private Date expireDate;
	private BigDecimal docNumLine;
	private String approved;
	private String qcUserId;
	private String username;
	private String osuser;
	private String server;
	private String machine;
	private String terminal;
	private String program;
	private Date timeStamp;
	private Date qcDate;
	private BigDecimal receiptGroup;
	private BigDecimal usageFactor;
	private String accountNumber;
	private String accountNumber2;
	private String accountSysId;
	private Date certificationDate;
	private BigDecimal certifiedBy;
	private String chargeType;
	private BigDecimal costFactor;
	private String currencyId;
	private String customerReceiptId;
	private Date dateOfShipment;
	private String deliveryTicket;
	private Date docProcessDate;
	private BigDecimal establishedUnitPrice;
	private String excess;
	private String facilityId;
	private String facilityRestriction;
	private Date insertDate;
	private String inventoryGroup;
	private BigDecimal itemConversionIssueId;
	private Date lastLabelPrintDate;
	private BigDecimal lastModifiedBy;
	private String lotStatusRootCause;
	private String lotStatusRootCauseNotes;
	private String notes;
	private BigDecimal originalReceiptId;
	private String ownerCompanyId;
	private String pickable;
	private String poNumber;
	private BigDecimal recertNumber;
	private Date reportingTransactionDate;
	private String responsibleCompanyId;
	private String returnLineItem;
	private BigDecimal returnPrNumber;
	private BigDecimal returnReceiptId;
	private BigDecimal totalQtyVirtualRma;
	private BigDecimal totalQuantityIssued;
	private BigDecimal totalQuantityReturned;
	private BigDecimal transferReceiptId;
	private Date vendorShipDate;
	private String workAreaRestriction;
        
        private String lastDateInBin;


	//constructor
	public ReceiptAuditBean() {
	}

	//setters
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setMfgLot(String mfgLot) {
		this.mfgLot = mfgLot;
	}
	public void setBin(String bin) {
		this.bin = bin;
	}
	public void setDateOfReceipt(Date dateOfReceipt) {
		this.dateOfReceipt = dateOfReceipt;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}
	public void setPoLine(BigDecimal poLine) {
		this.poLine = poLine;
	}
	public void setDateOfManufacture(Date dateOfManufacture) {
		this.dateOfManufacture = dateOfManufacture;
	}
	public void setQuantityReceived(BigDecimal quantityReceived) {
		this.quantityReceived = quantityReceived;
	}
	public void setDocNum(BigDecimal docNum) {
		this.docNum = docNum;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}
	public void setLotStatus(String lotStatus) {
		this.lotStatus = lotStatus;
	}
	public void setTransferRequestId(BigDecimal transferRequestId) {
		this.transferRequestId = transferRequestId;
	}
	public void setHub(String hub) {
		this.hub = hub;
	}
	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	public void setDocNumLine(BigDecimal docNumLine) {
		this.docNumLine = docNumLine;
	}
	public void setApproved(String approved) {
		this.approved = approved;
	}
	public void setQcUserId(String qcUserId) {
		this.qcUserId = qcUserId;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setOsuser(String osuser) {
		this.osuser = osuser;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public void setMachine(String machine) {
		this.machine = machine;
	}
	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}
	public void setProgram(String program) {
		this.program = program;
	}
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	public void setQcDate(Date qcDate) {
		this.qcDate = qcDate;
	}
	public void setReceiptGroup(BigDecimal receiptGroup) {
		this.receiptGroup = receiptGroup;
	}
	public void setUsageFactor(BigDecimal usageFactor) {
		this.usageFactor = usageFactor;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public void setAccountNumber2(String accountNumber2) {
		this.accountNumber2 = accountNumber2;
	}
	public void setAccountSysId(String accountSysId) {
		this.accountSysId = accountSysId;
	}
	public void setCertificationDate(Date certificationDate) {
		this.certificationDate = certificationDate;
	}
	public void setCertifiedBy(BigDecimal certifiedBy) {
		this.certifiedBy = certifiedBy;
	}
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}
	public void setCostFactor(BigDecimal costFactor) {
		this.costFactor = costFactor;
	}
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}
	public void setCustomerReceiptId(String customerReceiptId) {
		this.customerReceiptId = customerReceiptId;
	}
	public void setDateOfShipment(Date dateOfShipment) {
		this.dateOfShipment = dateOfShipment;
	}
	public void setDeliveryTicket(String deliveryTicket) {
		this.deliveryTicket = deliveryTicket;
	}
	public void setDocProcessDate(Date docProcessDate) {
		this.docProcessDate = docProcessDate;
	}
	public void setEstablishedUnitPrice(BigDecimal establishedUnitPrice) {
		this.establishedUnitPrice = establishedUnitPrice;
	}
	public void setExcess(String excess) {
		this.excess = excess;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setFacilityRestriction(String facilityRestriction) {
		this.facilityRestriction = facilityRestriction;
	}
	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setItemConversionIssueId(BigDecimal itemConversionIssueId) {
		this.itemConversionIssueId = itemConversionIssueId;
	}
	public void setLastLabelPrintDate(Date lastLabelPrintDate) {
		this.lastLabelPrintDate = lastLabelPrintDate;
	}
	public void setLastModifiedBy(BigDecimal lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	public void setLotStatusRootCause(String lotStatusRootCause) {
		this.lotStatusRootCause = lotStatusRootCause;
	}
	public void setLotStatusRootCauseNotes(String lotStatusRootCauseNotes) {
		this.lotStatusRootCauseNotes = lotStatusRootCauseNotes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public void setOriginalReceiptId(BigDecimal originalReceiptId) {
		this.originalReceiptId = originalReceiptId;
	}
	public void setOwnerCompanyId(String ownerCompanyId) {
		this.ownerCompanyId = ownerCompanyId;
	}
	public void setPickable(String pickable) {
		this.pickable = pickable;
	}
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	public void setRecertNumber(BigDecimal recertNumber) {
		this.recertNumber = recertNumber;
	}
	public void setReportingTransactionDate(Date reportingTransactionDate) {
		this.reportingTransactionDate = reportingTransactionDate;
	}
	public void setResponsibleCompanyId(String responsibleCompanyId) {
		this.responsibleCompanyId = responsibleCompanyId;
	}
	public void setReturnLineItem(String returnLineItem) {
		this.returnLineItem = returnLineItem;
	}
	public void setReturnPrNumber(BigDecimal returnPrNumber) {
		this.returnPrNumber = returnPrNumber;
	}
	public void setReturnReceiptId(BigDecimal returnReceiptId) {
		this.returnReceiptId = returnReceiptId;
	}
	public void setTotalQtyVirtualRma(BigDecimal totalQtyVirtualRma) {
		this.totalQtyVirtualRma = totalQtyVirtualRma;
	}
	public void setTotalQuantityIssued(BigDecimal totalQuantityIssued) {
		this.totalQuantityIssued = totalQuantityIssued;
	}
	public void setTotalQuantityReturned(BigDecimal totalQuantityReturned) {
		this.totalQuantityReturned = totalQuantityReturned;
	}
	public void setTransferReceiptId(BigDecimal transferReceiptId) {
		this.transferReceiptId = transferReceiptId;
	}
	public void setVendorShipDate(Date vendorShipDate) {
		this.vendorShipDate = vendorShipDate;
	}
	public void setWorkAreaRestriction(String workAreaRestriction) {
		this.workAreaRestriction = workAreaRestriction;
	}
        public void setLastDateInBin(String lastDateInBin) {
           this.lastDateInBin = lastDateInBin;
        }
        


	//getters
	public BigDecimal getItemId() {
		return itemId;
	}
	public String getMfgLot() {
		return mfgLot;
	}
	public String getBin() {
		return bin;
	}
	public Date getDateOfReceipt() {
		return dateOfReceipt;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public BigDecimal getRadianPo() {
		return radianPo;
	}
	public BigDecimal getPoLine() {
		return poLine;
	}
	public Date getDateOfManufacture() {
		return dateOfManufacture;
	}
	public BigDecimal getQuantityReceived() {
		return quantityReceived;
	}
	public BigDecimal getDocNum() {
		return docNum;
	}
	public String getDocType() {
		return docType;
	}
	public BigDecimal getReceiptId() {
		return receiptId;
	}
	public String getLotStatus() {
		return lotStatus;
	}
	public BigDecimal getTransferRequestId() {
		return transferRequestId;
	}
	public String getHub() {
		return hub;
	}
	public String getReceiverId() {
		return receiverId;
	}
	public Date getExpireDate() {
		return expireDate;
	}
	public BigDecimal getDocNumLine() {
		return docNumLine;
	}
	public String getApproved() {
		return approved;
	}
	public String getQcUserId() {
		return qcUserId;
	}
	public String getUsername() {
		return username;
	}
	public String getOsuser() {
		return osuser;
	}
	public String getServer() {
		return server;
	}
	public String getMachine() {
		return machine;
	}
	public String getTerminal() {
		return terminal;
	}
	public String getProgram() {
		return program;
	}
	public Date getTimeStamp() {
		return timeStamp;
	}
	public Date getQcDate() {
		return qcDate;
	}
	public BigDecimal getReceiptGroup() {
		return receiptGroup;
	}
	public BigDecimal getUsageFactor() {
		return usageFactor;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public String getAccountNumber2() {
		return accountNumber2;
	}
	public String getAccountSysId() {
		return accountSysId;
	}
	public Date getCertificationDate() {
		return certificationDate;
	}
	public BigDecimal getCertifiedBy() {
		return certifiedBy;
	}
	public String getChargeType() {
		return chargeType;
	}
	public BigDecimal getCostFactor() {
		return costFactor;
	}
	public String getCurrencyId() {
		return currencyId;
	}
	public String getCustomerReceiptId() {
		return customerReceiptId;
	}
	public Date getDateOfShipment() {
		return dateOfShipment;
	}
	public String getDeliveryTicket() {
		return deliveryTicket;
	}
	public Date getDocProcessDate() {
		return docProcessDate;
	}
	public BigDecimal getEstablishedUnitPrice() {
		return establishedUnitPrice;
	}
	public String getExcess() {
		return excess;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public String getFacilityRestriction() {
		return facilityRestriction;
	}
	public Date getInsertDate() {
		return insertDate;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public BigDecimal getItemConversionIssueId() {
		return itemConversionIssueId;
	}
	public Date getLastLabelPrintDate() {
		return lastLabelPrintDate;
	}
	public BigDecimal getLastModifiedBy() {
		return lastModifiedBy;
	}
	public String getLotStatusRootCause() {
		return lotStatusRootCause;
	}
	public String getLotStatusRootCauseNotes() {
		return lotStatusRootCauseNotes;
	}
	public String getNotes() {
		return notes;
	}
	public BigDecimal getOriginalReceiptId() {
		return originalReceiptId;
	}
	public String getOwnerCompanyId() {
		return ownerCompanyId;
	}
	public String getPickable() {
		return pickable;
	}
	public String getPoNumber() {
		return poNumber;
	}
	public BigDecimal getRecertNumber() {
		return recertNumber;
	}
	public Date getReportingTransactionDate() {
		return reportingTransactionDate;
	}
	public String getResponsibleCompanyId() {
		return responsibleCompanyId;
	}
	public String getReturnLineItem() {
		return returnLineItem;
	}
	public BigDecimal getReturnPrNumber() {
		return returnPrNumber;
	}
	public BigDecimal getReturnReceiptId() {
		return returnReceiptId;
	}
	public BigDecimal getTotalQtyVirtualRma() {
		return totalQtyVirtualRma;
	}
	public BigDecimal getTotalQuantityIssued() {
		return totalQuantityIssued;
	}
	public BigDecimal getTotalQuantityReturned() {
		return totalQuantityReturned;
	}
	public BigDecimal getTransferReceiptId() {
		return transferReceiptId;
	}
	public Date getVendorShipDate() {
		return vendorShipDate;
	}
	public String getWorkAreaRestriction() {
		return workAreaRestriction;
	}        
        public String getLastDateInBin() {
           return lastDateInBin;
        }        
}