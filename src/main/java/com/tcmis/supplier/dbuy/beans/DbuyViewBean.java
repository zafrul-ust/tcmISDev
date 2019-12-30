package com.tcmis.supplier.dbuy.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: DbuyViewBean <br>
 * @version: 1.0, Nov 16, 2005 <br>
 *****************************************************************************/


public class DbuyViewBean extends BaseDataBean {

	private BigDecimal radianPo;
	private BigDecimal poLine;
	private String supplierName;
	private String supplierContactName;
	private String supplierCountryAbbrev;
	private String supplierStateAbbrev;
	private String supplierAddressLine11;
	private String supplierAddressLine22;
	private String supplierAddressLine33;
	private String supplierCity;
	private String supplierZip;
	private String shipToCompanyId;
	private String shipToLocationId;
	private String shiptoCountryAbbrev;
	private String shiptoStateAbbrev;
	private String shiptoAddressLine11;
	private String shiptoAddressLine22;
	private String shiptoAddressLine33;
	private String shiptoCity;
	private String shiptoZip;
	private String buyerName;
	private String buyerEmail;
	private String buyerPhone;
	private String mrNumber;
	private String supplierShipToCode;
	private BigDecimal numberOfLines;
	private BigDecimal itemId;
	private BigDecimal quantity;
	private BigDecimal unitPrice;
	private String itemShortDesc;
	private String packaging;
	private String supplierPartNo;
	private String ediUom;
	private String specFlowdownName;
	private String specFlowdownType;
	private String critical;
	private String needByDate;
	private String poLineNote;
	private String ediDocumentControlNumber;
	private String supplier;
	private String carrierName;
	private String carrierCode;
	private String carrierAccount;
	private String shipToId;
	private String remainingShelfLifePercent;
	private String carrierBillCompany;
	private String carrierBillName;
	private String carrierBillCountryAbbrev;
	private String carrierBillStateAbbrev;
	private String carrierBillAddressLine11;
	private String carrierBillAddressLine22;
	private String carrierBillAddressLine33;
	private String carrierBillCity;
	private String carrierBillZip;
	private String billCountryAbbrev;
	private String billStateAbbrev;
	private String billAddressLine11;
	private String billAddressLine22;
	private String billAddressLine33;
	private String billCity;
	private String billZip;
	private String paymentTerms;
	private String poNotes;
	private String notesSplited;
	private BigDecimal poNotesOrderId;
	private BigDecimal poLineNoteOrderId;
	private String transactorId;
	private String transactorMailBoxAddress;
	private BigDecimal itemDescOrderId;
	private String transactorQualifier;
	private String tradeTerms;
	private String paymentMethodCode;
	private String defaultShipmentOriginState;
	private String customerPo;
	private String shipToContactName;
	private String shipToContactPhone;


	//constructor
	public DbuyViewBean() {
	}

	//setters
	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}
	public void setPoLine(BigDecimal poLine) {
		this.poLine = poLine;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public void setSupplierContactName(String supplierContactName) {
		this.supplierContactName = supplierContactName;
	}
	public void setSupplierCountryAbbrev(String supplierCountryAbbrev) {
		this.supplierCountryAbbrev = supplierCountryAbbrev;
	}
	public void setSupplierStateAbbrev(String supplierStateAbbrev) {
		this.supplierStateAbbrev = supplierStateAbbrev;
	}
	public void setSupplierAddressLine1(String supplierAddressLine11) {
		this.supplierAddressLine11 = supplierAddressLine11;
	}
	public void setSupplierAddressLine2(String supplierAddressLine22) {
		this.supplierAddressLine22 = supplierAddressLine22;
	}
	public void setSupplierAddressLine3(String supplierAddressLine33) {
		this.supplierAddressLine33 = supplierAddressLine33;
	}
	public void setSupplierCity(String supplierCity) {
		this.supplierCity = supplierCity;
	}
	public void setSupplierZip(String supplierZip) {
		this.supplierZip = supplierZip;
	}
	public void setShipToCompanyId(String shipToCompanyId) {
		this.shipToCompanyId = shipToCompanyId;
	}
	public void setShipToLocationId(String shipToLocationId) {
		this.shipToLocationId = shipToLocationId;
	}
	public void setShiptoCountryAbbrev(String shiptoCountryAbbrev) {
		this.shiptoCountryAbbrev = shiptoCountryAbbrev;
	}
	public void setShiptoStateAbbrev(String shiptoStateAbbrev) {
		this.shiptoStateAbbrev = shiptoStateAbbrev;
	}
	public void setShiptoAddressLine1(String shiptoAddressLine11) {
		this.shiptoAddressLine11 = shiptoAddressLine11;
	}
	public void setShiptoAddressLine2(String shiptoAddressLine22) {
		this.shiptoAddressLine22 = shiptoAddressLine22;
	}
	public void setShiptoAddressLine3(String shiptoAddressLine33) {
		this.shiptoAddressLine33 = shiptoAddressLine33;
	}
	public void setShiptoCity(String shiptoCity) {
		this.shiptoCity = shiptoCity;
	}
	public void setShiptoZip(String shiptoZip) {
		this.shiptoZip = shiptoZip;
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
	public void setMrNumber(String mrNumber) {
		this.mrNumber = mrNumber;
	}
	public void setSupplierShipToCode(String supplierShipToCode) {
		this.supplierShipToCode = supplierShipToCode;
	}
	public void setNumberOfLines(BigDecimal numberOfLines) {
		this.numberOfLines = numberOfLines;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	public void setItemShortDesc(String itemShortDesc) {
		this.itemShortDesc = itemShortDesc;
	}
	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}
	public void setSupplierPartNo(String supplierPartNo) {
		this.supplierPartNo = supplierPartNo;
	}
	public void setEdiUom(String ediUom) {
		this.ediUom = ediUom;
	}
	public void setSpecFlowdownName(String specFlowdownName) {
		this.specFlowdownName = specFlowdownName;
	}
	public void setSpecFlowdownType(String specFlowdownType) {
		this.specFlowdownType = specFlowdownType;
	}
	public void setCritical(String critical) {
		this.critical = critical;
	}
	public void setNeedByDate(String needByDate) {
		this.needByDate = needByDate;
	}
	public void setPoLineNote(String poLineNote) {
		this.poLineNote = poLineNote;
	}
	public void setEdiDocumentControlNumber(String ediDocumentControlNumber) {
		this.ediDocumentControlNumber = ediDocumentControlNumber;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	public void setCarrierAccount(String carrierAccount) {
		this.carrierAccount = carrierAccount;
	}
	public void setShipToId(String shipToId) {
		this.shipToId = shipToId;
	}
	public void setRemainingShelfLifePercent(String remainingShelfLifePercent) {
		this.remainingShelfLifePercent = remainingShelfLifePercent;
	}
	public void setCarrierBillCompany(String carrierBillCompany) {
		this.carrierBillCompany = carrierBillCompany;
	}
	public void setCarrierBillName(String carrierBillName) {
		this.carrierBillName = carrierBillName;
	}
	public void setCarrierBillCountryAbbrev(String carrierBillCountryAbbrev) {
		this.carrierBillCountryAbbrev = carrierBillCountryAbbrev;
	}
	public void setCarrierBillStateAbbrev(String carrierBillStateAbbrev) {
		this.carrierBillStateAbbrev = carrierBillStateAbbrev;
	}
	public void setCarrierBillAddressLine1(String carrierBillAddressLine11) {
		this.carrierBillAddressLine11 = carrierBillAddressLine11;
	}
	public void setCarrierBillAddressLine2(String carrierBillAddressLine22) {
		this.carrierBillAddressLine22 = carrierBillAddressLine22;
	}
	public void setCarrierBillAddressLine3(String carrierBillAddressLine33) {
		this.carrierBillAddressLine33 = carrierBillAddressLine33;
	}
	public void setCarrierBillCity(String carrierBillCity) {
		this.carrierBillCity = carrierBillCity;
	}
	public void setCarrierBillZip(String carrierBillZip) {
		this.carrierBillZip = carrierBillZip;
	}
	public void setBillCountryAbbrev(String billCountryAbbrev) {
		this.billCountryAbbrev = billCountryAbbrev;
	}
	public void setBillStateAbbrev(String billStateAbbrev) {
		this.billStateAbbrev = billStateAbbrev;
	}
	public void setBillAddressLine1(String billAddressLine11) {
		this.billAddressLine11 = billAddressLine11;
	}
	public void setBillAddressLine2(String billAddressLine22) {
		this.billAddressLine22 = billAddressLine22;
	}
	public void setBillAddressLine3(String billAddressLine33) {
		this.billAddressLine33 = billAddressLine33;
	}
	public void setBillCity(String billCity) {
		this.billCity = billCity;
	}
	public void setBillZip(String billZip) {
		this.billZip = billZip;
	}
	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}
	public void setPoNotes(String poNotes) {
		this.poNotes = poNotes;
	}
	public void setNotesSplited(String notesSplited) {
		this.notesSplited = notesSplited;
	}
	public void setPoNotesOrderId(BigDecimal poNotesOrderId) {
		this.poNotesOrderId = poNotesOrderId;
	}
	public void setPoLineNoteOrderId(BigDecimal poLineNoteOrderId) {
		this.poLineNoteOrderId = poLineNoteOrderId;
	}
	public void setTransactorId(String transactorId) {
		this.transactorId = transactorId;
	}
	public void setTransactorMailBoxAddress(String transactorMailBoxAddress) {
		this.transactorMailBoxAddress = transactorMailBoxAddress;
	}
	public void setItemDescOrderId(BigDecimal itemDescOrderId) {
		this.itemDescOrderId = itemDescOrderId;
	}
	public void setTransactorQualifier(String transactorQualifier) {
		this.transactorQualifier = transactorQualifier;
	}
	public void setTradeTerms(String tradeTerms) {
		this.tradeTerms = tradeTerms;
	}
	public void setPaymentMethodCode(String paymentMethodCode) {
		this.paymentMethodCode = paymentMethodCode;
	}
	public void setDefaultShipmentOriginState(String defaultShipmentOriginState) {
		this.defaultShipmentOriginState = defaultShipmentOriginState;
	}
	public void setCustomerPo(String customerPo) {
		this.customerPo = customerPo;
	}
	public void setShipToContactName(String shipToContactName) {
		this.shipToContactName = shipToContactName;
	}
	public void setShipToContactPhone(String shipToContactPhone) {
		this.shipToContactPhone = shipToContactPhone;
	}


	//getters
	public BigDecimal getRadianPo() {
		return radianPo;
	}
	public BigDecimal getPoLine() {
		return poLine;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public String getSupplierContactName() {
		return supplierContactName;
	}
	public String getSupplierCountryAbbrev() {
		return supplierCountryAbbrev;
	}
	public String getSupplierStateAbbrev() {
		return supplierStateAbbrev;
	}
	public String getSupplierAddressLine1() {
		return supplierAddressLine11;
	}
	public String getSupplierAddressLine2() {
		return supplierAddressLine22;
	}
	public String getSupplierAddressLine3() {
		return supplierAddressLine33;
	}
	public String getSupplierCity() {
		return supplierCity;
	}
	public String getSupplierZip() {
		return supplierZip;
	}
	public String getShipToCompanyId() {
		return shipToCompanyId;
	}
	public String getShipToLocationId() {
		return shipToLocationId;
	}
	public String getShiptoCountryAbbrev() {
		return shiptoCountryAbbrev;
	}
	public String getShiptoStateAbbrev() {
		return shiptoStateAbbrev;
	}
	public String getShiptoAddressLine1() {
		return shiptoAddressLine11;
	}
	public String getShiptoAddressLine2() {
		return shiptoAddressLine22;
	}
	public String getShiptoAddressLine3() {
		return shiptoAddressLine33;
	}
	public String getShiptoCity() {
		return shiptoCity;
	}
	public String getShiptoZip() {
		return shiptoZip;
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
	public String getMrNumber() {
		return mrNumber;
	}
	public String getSupplierShipToCode() {
		return supplierShipToCode;
	}
	public BigDecimal getNumberOfLines() {
		return numberOfLines;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public String getItemShortDesc() {
		return itemShortDesc;
	}
	public String getPackaging() {
		return packaging;
	}
	public String getSupplierPartNo() {
		return supplierPartNo;
	}
	public String getEdiUom() {
		return ediUom;
	}
	public String getSpecFlowdownName() {
		return specFlowdownName;
	}
	public String getSpecFlowdownType() {
		return specFlowdownType;
	}
	public String getCritical() {
		return critical;
	}
	public String getNeedByDate() {
		return needByDate;
	}
	public String getPoLineNote() {
		return poLineNote;
	}
	public String getEdiDocumentControlNumber() {
		return ediDocumentControlNumber;
	}
	public String getSupplier() {
		return supplier;
	}
	public String getCarrierName() {
		return carrierName;
	}
	public String getCarrierCode() {
		return carrierCode;
	}
	public String getCarrierAccount() {
		return carrierAccount;
	}
	public String getShipToId() {
		return shipToId;
	}
	public String getRemainingShelfLifePercent() {
		return remainingShelfLifePercent;
	}
	public String getCarrierBillCompany() {
		return carrierBillCompany;
	}
	public String getCarrierBillName() {
		return carrierBillName;
	}
	public String getCarrierBillCountryAbbrev() {
		return carrierBillCountryAbbrev;
	}
	public String getCarrierBillStateAbbrev() {
		return carrierBillStateAbbrev;
	}
	public String getCarrierBillAddressLine1() {
		return carrierBillAddressLine11;
	}
	public String getCarrierBillAddressLine2() {
		return carrierBillAddressLine22;
	}
	public String getCarrierBillAddressLine3() {
		return carrierBillAddressLine33;
	}
	public String getCarrierBillCity() {
		return carrierBillCity;
	}
	public String getCarrierBillZip() {
		return carrierBillZip;
	}
	public String getBillCountryAbbrev() {
		return billCountryAbbrev;
	}
	public String getBillStateAbbrev() {
		return billStateAbbrev;
	}
	public String getBillAddressLine1() {
		return billAddressLine11;
	}
	public String getBillAddressLine2() {
		return billAddressLine22;
	}
	public String getBillAddressLine3() {
		return billAddressLine33;
	}
	public String getBillCity() {
		return billCity;
	}
	public String getBillZip() {
		return billZip;
	}
	public String getPaymentTerms() {
		return paymentTerms;
	}
	public String getPoNotes() {
		return poNotes;
	}
	public String getNotesSplited() {
		return notesSplited;
	}
	public BigDecimal getPoNotesOrderId() {
		return poNotesOrderId;
	}
	public BigDecimal getPoLineNoteOrderId() {
		return poLineNoteOrderId;
	}
	public String getTransactorId() {
		return transactorId;
	}
	public String getTransactorMailBoxAddress() {
		return transactorMailBoxAddress;
	}
	public BigDecimal getItemDescOrderId() {
		return itemDescOrderId;
	}
	public String getTransactorQualifier() {
		return transactorQualifier;
	}
	public String getTradeTerms() {
		return tradeTerms;
	}
	public String getPaymentMethodCode() {
		return paymentMethodCode;
	}
	public String getDefaultShipmentOriginState() {
		return defaultShipmentOriginState;
	}
	public String getCustomerPo() {
		return customerPo;
	}
	public String getShipToContactName() {
		return shipToContactName;
	}
	public String getShipToContactPhone() {
		return shipToContactPhone;
	}
}