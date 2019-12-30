package com.tcmis.internal.supply.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: PoHeaderViewBean <br>
 * @version: 1.0, May 2, 2014 <br>
 *****************************************************************************/


public class PoHeaderViewBean extends BaseDataBean {

	private String hubName;
	private BigDecimal radianPo;
	private BigDecimal bo;
	private String supplier;
	private BigDecimal supplierContactId;
	private int buyer;
	private String shipToCompanyId;
	private String shipToFacilityId;
	private String shipToLocationId;
	private String branchPlant;
	private String freightOnBoard;
	private String paymentTerms;
	private String carrier;
	private Date dateSent;
	private Date dateAccepted;
	private String termsAndConditions;
	private Date boStartDate;
	private Date boEndDate;
	private String customerPo;
	private String supplierName;
	private String supplierContactName;
	private String buyerName;
	private String buyerFirstName;
	private String buyerLastName;
	private String buyerFax;
	private String buyerEmail;
	private String buyerPhone;
	private String everConfirmed;
	private String carrierCompanyId;
	private String carrierAccount;
	private String carrierName;
	private String carrierHub;
	private String supplierCountryAbbrev;
	private String supplierStateAbbrev;
	private String supplierAddressLine1;
	private String supplierAddressLine2;
	private String supplierAddressLine3;
	private String supplierCity;
	private String supplierZip;
	private String supplierLocationDesc;
	private String shiptoCountryAbbrev;
	private String shiptoStateAbbrev;
	private String shiptoAddressLine1;
	private String shiptoAddressLine2;
	private String shiptoAddressLine3;
	private String shiptoCity;
	private String shiptoZip;
	private String shiptoLocationDesc;
	private String supplierPhone;
	private String supplierContactPhone;
	private String supplierContactFax;
	private String critical;
	private String qualificationLevel;
	private String inventoryGroup;
	private String attachedPr;
	private String haasCarrier;
	private String carrierSupplierId;
	private String consignedPo;
	private String vouchered;
	private String dbuyLockStatus;
	private String receivedStatus;
	private String email;
	private String supplierEmail;
	private BigDecimal poPrice;
	private BigDecimal poPriceOe;
	private String transport;
	private String poImageUrl;
	private String operatingEntityName;
	private String billToCompanyId;
	private String billToLocationId;
	private String operatingEntityShortName;
	private String opsEntityAlias;
	private Date dateLastAcknowledgement;
	private BigDecimal daysSinceLastStatus;
	private String opsEntityId;
	private String poTermsAndConditions;
	private String assignCharges;
	private String changeSupplier;
	private Collection poLineCollection;
	private String buyerCompanyId;
	private String nonintegerReceiving;
	private String stateTaxId;
	private String federalTaxId;
	private String homeCompanyTaxId;
	private String inventoryGroupStateTaxId;
	private String operationalTaxId;
	private String shipToAddressCode;
	private String supplierDefaultPaymentTerms;
	private String shipToDisplayAddress;
	private String supplierDisplayAddress;
	private String intercompanyMr;
	private String shipToAddressLine1Display;
	private String shipToAddressLine2Display;
	private String shipToAddressLine3Display;
	private String shipToAddressLine4Display;
	private String shipToAddressLine5Display;
	private String supplrAddressLine1Display;
	private String supplrAddressLine2Display;
	private String supplrAddressLine3Display;
	private String supplrAddressLine4Display;
	private String supplrAddressLine5Display;
	private boolean blnProblemWithBuy;
	private String requiresFinancialApproval;
	private String poApprovalStatus;

	//constructor
	public PoHeaderViewBean() {
	}

	//setters
	public void setHubName(String hubName) {
		this.hubName = hubName;
	}
	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}
	public void setBo(BigDecimal bo) {
		this.bo = bo;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public void setSupplierContactId(BigDecimal supplierContactId) {
		this.supplierContactId = supplierContactId;
	}
	public void setBuyer(int buyer) {
		this.buyer = buyer;
	}
	public void setShipToCompanyId(String shipToCompanyId) {
		this.shipToCompanyId = shipToCompanyId;
	}
	public void setShipToFacilityId(String shipToFacilityId) {
		this.shipToFacilityId = shipToFacilityId;
	}
	public void setShipToLocationId(String shipToLocationId) {
		this.shipToLocationId = shipToLocationId;
	}
	public void setBranchPlant(String branchPlant) {
		this.branchPlant = branchPlant;
	}
	public void setFreightOnBoard(String freightOnBoard) {
		this.freightOnBoard = freightOnBoard;
	}
	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	public void setDateSent(Date dateSent) {
		this.dateSent = dateSent;
	}
	public void setDateAccepted(Date dateAccepted) {
		this.dateAccepted = dateAccepted;
	}
	public void setTermsAndConditions(String termsAndConditions) {
		this.termsAndConditions = termsAndConditions;
	}
	public void setBoStartDate(Date boStartDate) {
		this.boStartDate = boStartDate;
	}
	public void setBoEndDate(Date boEndDate) {
		this.boEndDate = boEndDate;
	}
	public void setCustomerPo(String customerPo) {
		this.customerPo = customerPo;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public void setSupplierContactName(String supplierContactName) {
		this.supplierContactName = supplierContactName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	public void setBuyerFirstName(String buyerFirstName) {
		this.buyerFirstName = buyerFirstName;
	}
	public void setBuyerLastName(String buyerLastName) {
		this.buyerLastName = buyerLastName;
	}
	public void setBuyerFax(String buyerFax) {
		this.buyerFax = buyerFax;
	}
	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}
	public void setBuyerPhone(String buyerPhone) {
		this.buyerPhone = buyerPhone;
	}
	public void setEverConfirmed(String everConfirmed) {
		this.everConfirmed = everConfirmed;
	}
	public void setCarrierCompanyId(String carrierCompanyId) {
		this.carrierCompanyId = carrierCompanyId;
	}
	public void setCarrierAccount(String carrierAccount) {
		this.carrierAccount = carrierAccount;
	}
	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}
	public void setCarrierHub(String carrierHub) {
		this.carrierHub = carrierHub;
	}
	public void setSupplierCountryAbbrev(String supplierCountryAbbrev) {
		this.supplierCountryAbbrev = supplierCountryAbbrev;
	}
	public void setSupplierStateAbbrev(String supplierStateAbbrev) {
		this.supplierStateAbbrev = supplierStateAbbrev;
	}
	public void setSupplierAddressLine1(String supplierAddressLine1) {
		this.supplierAddressLine1 = supplierAddressLine1;
	}
	public void setSupplierAddressLine2(String supplierAddressLine2) {
		this.supplierAddressLine2 = supplierAddressLine2;
	}
	public void setSupplierAddressLine3(String supplierAddressLine3) {
		this.supplierAddressLine3 = supplierAddressLine3;
	}
	public void setSupplierCity(String supplierCity) {
		this.supplierCity = supplierCity;
	}
	public void setSupplierZip(String supplierZip) {
		this.supplierZip = supplierZip;
	}
	public void setSupplierLocationDesc(String supplierLocationDesc) {
		this.supplierLocationDesc = supplierLocationDesc;
	}
	public void setShiptoCountryAbbrev(String shiptoCountryAbbrev) {
		this.shiptoCountryAbbrev = shiptoCountryAbbrev;
	}
	public void setShiptoStateAbbrev(String shiptoStateAbbrev) {
		this.shiptoStateAbbrev = shiptoStateAbbrev;
	}
	public void setShiptoAddressLine1(String shiptoAddressLine1) {
		this.shiptoAddressLine1 = shiptoAddressLine1;
	}
	public void setShiptoAddressLine2(String shiptoAddressLine2) {
		this.shiptoAddressLine2 = shiptoAddressLine2;
	}
	public void setShiptoAddressLine3(String shiptoAddressLine3) {
		this.shiptoAddressLine3 = shiptoAddressLine3;
	}
	public void setShiptoCity(String shiptoCity) {
		this.shiptoCity = shiptoCity;
	}
	public void setShiptoZip(String shiptoZip) {
		this.shiptoZip = shiptoZip;
	}
	public void setShiptoLocationDesc(String shiptoLocationDesc) {
		this.shiptoLocationDesc = shiptoLocationDesc;
	}
	public void setSupplierPhone(String supplierPhone) {
		this.supplierPhone = supplierPhone;
	}
	public void setSupplierContactPhone(String supplierContactPhone) {
		this.supplierContactPhone = supplierContactPhone;
	}
	public void setSupplierContactFax(String supplierContactFax) {
		this.supplierContactFax = supplierContactFax;
	}
	public void setCritical(String critical) {
		this.critical = critical;
	}
	public void setQualificationLevel(String qualificationLevel) {
		this.qualificationLevel = qualificationLevel;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setAttachedPr(String attachedPr) {
		this.attachedPr = attachedPr;
	}
	public void setHaasCarrier(String haasCarrier) {
		this.haasCarrier = haasCarrier;
	}
	public void setCarrierSupplierId(String carrierSupplierId) {
		this.carrierSupplierId = carrierSupplierId;
	}
	public void setConsignedPo(String consignedPo) {
		this.consignedPo = consignedPo;
	}
	public void setVouchered(String vouchered) {
		this.vouchered = vouchered;
	}
	public void setDbuyLockStatus(String dbuyLockStatus) {
		this.dbuyLockStatus = dbuyLockStatus;
	}
	public void setReceivedStatus(String receivedStatus) {
		this.receivedStatus = receivedStatus;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setSupplierEmail(String supplierEmail) {
		this.supplierEmail = supplierEmail;
	}
	public void setPoPrice(BigDecimal poPrice) {
		this.poPrice = poPrice;
	}
	public void setPoPriceOe(BigDecimal poPriceOe) {
		this.poPriceOe = poPriceOe;
	}
	public void setTransport(String transport) {
		this.transport = transport;
	}
	public void setPoImageUrl(String poImageUrl) {
		this.poImageUrl = poImageUrl;
	}
	public void setOperatingEntityName(String operatingEntityName) {
		this.operatingEntityName = operatingEntityName;
	}
	public void setBillToCompanyId(String billToCompanyId) {
		this.billToCompanyId = billToCompanyId;
	}
	public void setBillToLocationId(String billToLocationId) {
		this.billToLocationId = billToLocationId;
	}
	public void setOpsEntityAlias(String opsEntityAlias) {
		this.opsEntityAlias = opsEntityAlias;
	}
	public void setDateLastAcknowledgement(Date dateLastAcknowledgement) {
		this.dateLastAcknowledgement = dateLastAcknowledgement;
	}
	public void setDaysSinceLastStatus(BigDecimal daysSinceLastStatus) {
		this.daysSinceLastStatus = daysSinceLastStatus;
	}
	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}
	public void setPoTermsAndConditions(String poTermsAndConditions) {
		this.poTermsAndConditions = poTermsAndConditions;
	}
	public void setAssignCharges(String assignCharges) {
		this.assignCharges = assignCharges;
	}
	public void setChangeSupplier(String changeSupplier) {
		this.changeSupplier = changeSupplier;
	}
	public void setBuyerCompanyId(String buyerCompanyId) {
		this.buyerCompanyId = buyerCompanyId;
	}
	public void setNonintegerReceiving(String nonintegerReceiving) {
		this.nonintegerReceiving = nonintegerReceiving;
	}
	public void setStateTaxId(String stateTaxId) {
		this.stateTaxId = stateTaxId;
	}
	public void setFederalTaxId(String federalTaxId) {
		this.federalTaxId = federalTaxId;
	}
	public void setHomeCompanyTaxId(String homeCompanyTaxId) {
		this.homeCompanyTaxId = homeCompanyTaxId;
	}
	public void setInventoryGroupStateTaxId(String inventoryGroupStateTaxId) {
		this.inventoryGroupStateTaxId = inventoryGroupStateTaxId;
	}
	public void setOperationalTaxId(String operationalTaxId) {
		this.operationalTaxId = operationalTaxId;
	}
	public void setShipToAddressCode(String shipToAddressCode) {
		this.shipToAddressCode = shipToAddressCode;
	}
	public void setSupplierDefaultPaymentTerms(String supplierDefaultPaymentTerms) {
		this.supplierDefaultPaymentTerms = supplierDefaultPaymentTerms;
	}
	public void setShipToDisplayAddress(String shipToDisplayAddress) {
		this.shipToDisplayAddress = shipToDisplayAddress;
	}
	public void setSupplierDisplayAddress(String supplierDisplayAddress) {
		this.supplierDisplayAddress = supplierDisplayAddress;
	}
	public void setIntercompanyMr(String intercompanyMr) {
		this.intercompanyMr = intercompanyMr;
	}
	public void setShipToAddressLine1Display(String shipToAddressLine1Display) {
		this.shipToAddressLine1Display = shipToAddressLine1Display;
	}
	public void setShipToAddressLine2Display(String shipToAddressLine2Display) {
		this.shipToAddressLine2Display = shipToAddressLine2Display;
	}
	public void setShipToAddressLine3Display(String shipToAddressLine3Display) {
		this.shipToAddressLine3Display = shipToAddressLine3Display;
	}
	public void setShipToAddressLine4Display(String shipToAddressLine4Display) {
		this.shipToAddressLine4Display = shipToAddressLine4Display;
	}
	public void setShipToAddressLine5Display(String shipToAddressLine5Display) {
		this.shipToAddressLine5Display = shipToAddressLine5Display;
	}
	public void setSupplrAddressLine1Display(String supplrAddressLine1Display) {
		this.supplrAddressLine1Display = supplrAddressLine1Display;
	}
	public void setSupplrAddressLine2Display(String supplrAddressLine2Display) {
		this.supplrAddressLine2Display = supplrAddressLine2Display;
	}
	public void setSupplrAddressLine3Display(String supplrAddressLine3Display) {
		this.supplrAddressLine3Display = supplrAddressLine3Display;
	}
	public void setSupplrAddressLine4Display(String supplrAddressLine4Display) {
		this.supplrAddressLine4Display = supplrAddressLine4Display;
	}
	public void setSupplrAddressLine5Display(String supplrAddressLine5Display) {
		this.supplrAddressLine5Display = supplrAddressLine5Display;
	}


	//getters
	public String getHubName() {
		return hubName;
	}
	public BigDecimal getRadianPo() {
		return radianPo;
	}
	public BigDecimal getBo() {
		return bo;
	}
	public String getSupplier() {
		return supplier;
	}
	public BigDecimal getSupplierContactId() {
		return supplierContactId;
	}
	public int getBuyer() {
		return buyer;
	}
	public String getShipToCompanyId() {
		return shipToCompanyId;
	}
	public String getShipToFacilityId() {
		return shipToFacilityId;
	}
	public String getShipToLocationId() {
		return shipToLocationId;
	}
	public String getBranchPlant() {
		return branchPlant;
	}
	public String getFreightOnBoard() {
		return freightOnBoard;
	}
	public String getPaymentTerms() {
		return paymentTerms;
	}
	public String getCarrier() {
		return carrier;
	}
	public Date getDateSent() {
		return dateSent;
	}
	public Date getDateAccepted() {
		return dateAccepted;
	}
	public String getTermsAndConditions() {
		return termsAndConditions;
	}
	public Date getBoStartDate() {
		return boStartDate;
	}
	public Date getBoEndDate() {
		return boEndDate;
	}
	public String getCustomerPo() {
		return customerPo;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public String getSupplierContactName() {
		return supplierContactName;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public String getBuyerFirstName() {
		return buyerFirstName;
	}
	public String getBuyerLastName() {
		return buyerLastName;
	}
	public String getBuyerFax() {
		return buyerFax;
	}
	public String getBuyerEmail() {
		return buyerEmail;
	}
	public String getBuyerPhone() {
		return buyerPhone;
	}
	public String getEverConfirmed() {
		return everConfirmed;
	}
	public String getCarrierCompanyId() {
		return carrierCompanyId;
	}
	public String getCarrierAccount() {
		return carrierAccount;
	}
	public String getCarrierName() {
		return carrierName;
	}
	public String getCarrierHub() {
		return carrierHub;
	}
	public String getSupplierCountryAbbrev() {
		return supplierCountryAbbrev;
	}
	public String getSupplierStateAbbrev() {
		return supplierStateAbbrev;
	}
	public String getSupplierAddressLine1() {
		return supplierAddressLine1;
	}
	public String getSupplierAddressLine2() {
		return supplierAddressLine2;
	}
	public String getSupplierAddressLine3() {
		return supplierAddressLine3;
	}
	public String getSupplierCity() {
		return supplierCity;
	}
	public String getSupplierZip() {
		return supplierZip;
	}
	public String getSupplierLocationDesc() {
		return supplierLocationDesc;
	}
	public String getShiptoCountryAbbrev() {
		return shiptoCountryAbbrev;
	}
	public String getShiptoStateAbbrev() {
		return shiptoStateAbbrev;
	}
	public String getShiptoAddressLine1() {
		return shiptoAddressLine1;
	}
	public String getShiptoAddressLine2() {
		return shiptoAddressLine2;
	}
	public String getShiptoAddressLine3() {
		return shiptoAddressLine3;
	}
	public String getShiptoCity() {
		return shiptoCity;
	}
	public String getShiptoZip() {
		return shiptoZip;
	}
	public String getShiptoLocationDesc() {
		return shiptoLocationDesc;
	}
	public String getSupplierPhone() {
		return supplierPhone;
	}
	public String getSupplierContactPhone() {
		return supplierContactPhone;
	}
	public String getSupplierContactFax() {
		return supplierContactFax;
	}
	public String getCritical() {
		return critical;
	}
	public String getQualificationLevel() {
		return qualificationLevel;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public String getAttachedPr() {
		return attachedPr;
	}
	public String getHaasCarrier() {
		return haasCarrier;
	}
	public String getCarrierSupplierId() {
		return carrierSupplierId;
	}
	public String getConsignedPo() {
		return consignedPo;
	}
	public String getVouchered() {
		return vouchered;
	}
	public String getDbuyLockStatus() {
		return dbuyLockStatus;
	}
	public String getReceivedStatus() {
		return receivedStatus;
	}
	public String getEmail() {
		return email;
	}
	public String getSupplierEmail() {
		return supplierEmail;
	}
	public BigDecimal getPoPrice() {
		return poPrice;
	}
	public BigDecimal getPoPriceOe() {
		return poPriceOe;
	}
	public String getTransport() {
		return transport;
	}
	public String getPoImageUrl() {
		return poImageUrl;
	}
	public String getOperatingEntityName() {
		return operatingEntityName;
	}
	public String getBillToCompanyId() {
		return billToCompanyId;
	}
	public String getBillToLocationId() {
		return billToLocationId;
	}
	public String getOpsEntityAlias() {
		return opsEntityAlias;
	}
	public Date getDateLastAcknowledgement() {
		return dateLastAcknowledgement;
	}
	public BigDecimal getDaysSinceLastStatus() {
		return daysSinceLastStatus;
	}
	public String getOpsEntityId() {
		return opsEntityId;
	}
	public String getPoTermsAndConditions() {
		return poTermsAndConditions;
	}
	public String getAssignCharges() {
		return assignCharges;
	}
	public String getChangeSupplier() {
		return changeSupplier;
	}
	public String getBuyerCompanyId() {
		return buyerCompanyId;
	}
	public String getNonintegerReceiving() {
		return nonintegerReceiving;
	}
	public String getStateTaxId() {
		return stateTaxId;
	}
	public String getFederalTaxId() {
		return federalTaxId;
	}
	public String getHomeCompanyTaxId() {
		return homeCompanyTaxId;
	}
	public String getInventoryGroupStateTaxId() {
		return inventoryGroupStateTaxId;
	}
	public String getOperationalTaxId() {
		return operationalTaxId;
	}
	public String getShipToAddressCode() {
		return shipToAddressCode;
	}
	public String getSupplierDefaultPaymentTerms() {
		return supplierDefaultPaymentTerms;
	}
	public String getShipToDisplayAddress() {
		return shipToDisplayAddress;
	}
	public String getSupplierDisplayAddress() {
		return supplierDisplayAddress;
	}
	public String getIntercompanyMr() {
		return intercompanyMr;
	}
	public String getShipToAddressLine1Display() {
		return shipToAddressLine1Display;
	}
	public String getShipToAddressLine2Display() {
		return shipToAddressLine2Display;
	}
	public String getShipToAddressLine3Display() {
		return shipToAddressLine3Display;
	}
	public String getShipToAddressLine4Display() {
		return shipToAddressLine4Display;
	}
	public String getShipToAddressLine5Display() {
		return shipToAddressLine5Display;
	}
	public String getSupplrAddressLine1Display() {
		return supplrAddressLine1Display;
	}
	public String getSupplrAddressLine2Display() {
		return supplrAddressLine2Display;
	}
	public String getSupplrAddressLine3Display() {
		return supplrAddressLine3Display;
	}
	public String getSupplrAddressLine4Display() {
		return supplrAddressLine4Display;
	}
	public String getSupplrAddressLine5Display() {
		return supplrAddressLine5Display;
	}

	public String getOperatingEntityShortName() {
		return operatingEntityShortName;
	}

	public void setOperatingEntityShortName(String operatingEntityShortName) {
		this.operatingEntityShortName = operatingEntityShortName;
	}

	public Collection getPoLineCollection() {
		return poLineCollection;
	}

	public void setPoLineCollection(Collection poLineCollection) {
		this.poLineCollection = poLineCollection;
	}

	public boolean isBlnProblemWithBuy() {
		return blnProblemWithBuy;
	}

	public void setBlnProblemWithBuy(boolean blnProblemWithBuy) {
		this.blnProblemWithBuy = blnProblemWithBuy;
	}

	public String getRequiresFinancialApproval() {
		return requiresFinancialApproval;
	}

	public void setRequiresFinancialApproval(String requiresFinancialApproval) {
		this.requiresFinancialApproval = requiresFinancialApproval;
	}

	public String getPoApprovalStatus() {
		return poApprovalStatus;
	}

	public void setPoApprovalStatus(String poApprovalStatus) {
		this.poApprovalStatus = poApprovalStatus;
	}
	
	public boolean isFinancialApprovalRequired() {
		return "Y".equals(requiresFinancialApproval);
	}
}