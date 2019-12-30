package com.tcmis.internal.supply.beans;

import java.util.Date;
import java.math.BigDecimal;
import java.util.Collection;
import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: PoHeaderViewBean <br>
 * @version: 1.0, Mar 10, 2006 <br>
 *****************************************************************************/


public class PoHeaderInputBean extends BaseDataBean {

	private String hubName; //branchPlant
	private BigDecimal radianPo;
	private BigDecimal bpo;
	private String supplierId;
	private BigDecimal buyerId;
	private String shipToCompanyId;
	private String shipToLocationId;	
	private String fob;
	private String paymentTerms;
	private String carrierID;
	private Date dateSent;
	private Date acceptedDate;//dateAccepted;
	private String termsAndConditions;
	private Date boStartDate;
	private Date boEndDate;
	private String customerPo;
	private String supplierName;
	private String buyer;
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
//	private String shiptoCountryAbbrev;
//	private String shiptoStateAbbrev;
//	private String shiptoAddressLine1;
//	private String shiptoAddressLine2;
//	private String shiptoAddressLine3;
//	private String shiptoCity;
//	private String shiptoZip;
//	private String shiptoLocationDesc;
	private String supplierPhone;
	private String contactPhoneNo; //supplierContactPhone;
	private String contactFaxNo; //supplierContactFax;
	private String criticalPo; //critical;
	private String suppRating; //qualificationLevel;
	private String invenGrp;
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
	private String transport;
	private String poImageUrl;
	private String operatingEntityName;
	private String billToCompanyId;
	private String billToLocationId;
	private String operatingEntityShortName;
	private Date dateLastAcknowledgement;
	private BigDecimal daysSinceLastStatus;
	private String opsEntityId;
	private String poTermsAndConditions;
	private String assignCharges;
	private String changeSupplier;
	private Collection poLineCollection;
	private String sortBy;
	private String submitSearch;
	private String submitSave;
	private String submitConfirm;
	private String submitPrint;
	private String orderTaker;   //supplierContactName
	private BigDecimal orderTakerID; //supplierContactId
	private String shipToFacilityId;	
	private String poNotes;
	private String emailNotes;
	private String currency;
	private BigDecimal totalCost;
	private String validPo;
	private String supplierDefaultPaymentTerms;
	
	//constructor
	public PoHeaderInputBean() {
	}

	//setters
	public void setHubName(String hubName) {
		this.hubName = hubName;
	}
	public void setShipToCompanyId(String shipToCompanyId) {
		this.shipToCompanyId = shipToCompanyId;
	}
	public void setShipToLocationId(String shipToLocationId) {
		this.shipToLocationId = shipToLocationId;
	}
	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}
	public void setDateSent(Date dateSent) {
		this.dateSent = dateSent;
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
	public void setSupplierCity(String supplierCity) {
		this.supplierCity = supplierCity;
	}
	public void setSupplierZip(String supplierZip) {
		this.supplierZip = supplierZip;
	}
	public void setSupplierLocationDesc(String supplierLocationDesc) {
		this.supplierLocationDesc = supplierLocationDesc;
	}
	public void setSupplierPhone(String supplierPhone) {
		this.supplierPhone = supplierPhone;
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
	public void setOperatingEntityShortName(String operatingEntityShortName) {
		this.operatingEntityShortName = operatingEntityShortName;
	}
	public void setDateLastAcknowledgement(Date dateLastAcknowledgement) {
		this.dateLastAcknowledgement = dateLastAcknowledgement;
	}
	public void setDaysSinceLastStatus(BigDecimal daysSinceLastStatus) {
		this.daysSinceLastStatus = daysSinceLastStatus;
	}
	public void setHomeCompanyId(String opsEntityId) {
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
	public void setPoLineCollection(Collection poLineCollection) {
		this.poLineCollection =	poLineCollection;
	}
	public void setSortBy(String sortBy) {
	 this.sortBy = sortBy;
	}
	public void setSubmitSearch(String submitSearch) {
	 this.submitSearch = submitSearch;
	}
	public void setSubmitSave(String submitSave) {
	 this.submitSave = submitSave;
	}
	public void setSubmitConfirm(String submitConfirm) {
	 this.submitConfirm = submitConfirm;
	}
	public void setSubmitPrint(String submitPrint) {
	 this.submitPrint = submitPrint;
	}

	//getters
	public String getHubName() {
		return hubName;
	}
	public String getShipToCompanyId() {
		return shipToCompanyId;
	}
	public String getShipToLocationId() {
		return shipToLocationId;
	}
	public String getPaymentTerms() {
		return paymentTerms;
	}	
	public Date getDateSent() {
		return dateSent;
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
	public String getSupplierCity() {
		return supplierCity;
	}
	public String getSupplierZip() {
		return supplierZip;
	}
	public String getSupplierLocationDesc() {
		return supplierLocationDesc;
	}
	public String getSupplierPhone() {
		return supplierPhone;
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
	public String getOperatingEntityShortName() {
		return operatingEntityShortName;
	}
	public Date getDateLastAcknowledgement() {
		return dateLastAcknowledgement;
	}
	public BigDecimal getDaysSinceLastStatus() {
		return daysSinceLastStatus;
	}
	public String getHomeCompanyId() {
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
	public Collection getPoLineCollection() {
	 return poLineCollection;
	}
	public String getSortBy() {
	 return sortBy;
	}
	public String getSubmitSearch() {
	 return submitSearch;
	}
	public String getSubmitSave() {
	 return submitSave;
	}
	public String getSubmitConfirm() {
	 return submitConfirm;
	}
  public String getSubmitPrint() {
	 return submitPrint;
	}

public BigDecimal getRadianPo() {
	return radianPo;
}

public void setRadianPo(BigDecimal radianPo) {
	this.radianPo = radianPo;
}

public BigDecimal getBpo() {
	return bpo;
}

public void setBpo(BigDecimal bpo) {
	this.bpo = bpo;
}

public BigDecimal getBuyerId() {
	return buyerId;
}

public void setBuyerId(BigDecimal buyerId) {
	this.buyerId = buyerId;
}

public String getBuyer() {
	return buyer;
}

public void setBuyer(String buyer) {
	this.buyer = buyer;
}

public String getSupplierAddressLine1() {
	return supplierAddressLine1;
}

public void setSupplierAddressLine1(String supplierAddressLine1) {
	this.supplierAddressLine1 = supplierAddressLine1;
}

public String getSupplierAddressLine2() {
	return supplierAddressLine2;
}

public void setSupplierAddressLine2(String supplierAddressLine2) {
	this.supplierAddressLine2 = supplierAddressLine2;
}

public String getSupplierAddressLine3() {
	return supplierAddressLine3;
}

public void setSupplierAddressLine3(String supplierAddressLine3) {
	this.supplierAddressLine3 = supplierAddressLine3;
}

public String getInvenGrp() {
	return invenGrp;
}

public void setInvenGrp(String invenGrp) {
	this.invenGrp = invenGrp;
}

public String getOpsEntityId() {
	return opsEntityId;
}

public void setOpsEntityId(String opsEntityId) {
	this.opsEntityId = opsEntityId;
}

public String getOrderTaker() {
	return orderTaker;
}

public void setOrderTaker(String orderTaker) {
	this.orderTaker = orderTaker;
}

public BigDecimal getOrderTakerID() {
	return orderTakerID;
}

public void setOrderTakerID(BigDecimal orderTakerID) {
	this.orderTakerID = orderTakerID;
}

public String getShipToFacilityId() {
	return shipToFacilityId;
}

public void setShipToFacilityId(String shipToFacilityId) {
	this.shipToFacilityId = shipToFacilityId;
}

public String getSupplierId() {
	return supplierId;
}

public void setSupplierId(String supplierId) {
	this.supplierId = supplierId;
}

public String getFob() {
	return fob;
}

public void setFob(String fob) {
	this.fob = fob;
}

public String getCarrierID() {
	return carrierID;
}

public void setCarrierID(String carrierID) {
	this.carrierID = carrierID;
}

public Date getAcceptedDate() {
	return acceptedDate;
}

public void setAcceptedDate(Date acceptedDate) {
	this.acceptedDate = acceptedDate;
}

public String getContactPhoneNo() {
	return contactPhoneNo;
}

public void setContactPhoneNo(String contactPhoneNo) {
	this.contactPhoneNo = contactPhoneNo;
}

public String getContactFaxNo() {
	return contactFaxNo;
}

public void setContactFaxNo(String contactFaxNo) {
	this.contactFaxNo = contactFaxNo;
}

public String getCriticalPo() {
	return criticalPo;
}

public void setCriticalPo(String criticalPo) {
	this.criticalPo = criticalPo;
}

public String getSuppRating() {
	return suppRating;
}

public void setSuppRating(String suppRating) {
	this.suppRating = suppRating;
}

public String getPoNotes() {
	return poNotes;
}

public void setPoNotes(String poNotes) {
	this.poNotes = poNotes;
}

public String getEmailNotes() {
	return emailNotes;
}

public void setEmailNotes(String emailNotes) {
	this.emailNotes = emailNotes;
}

public String getCurrency() {
	return currency;
}

public void setCurrency(String currency) {
	this.currency = currency;
}

public BigDecimal getTotalCost() {
	return totalCost;
}

public void setTotalCost(BigDecimal totalCost) {
	this.totalCost = totalCost;
}

public String getValidPo() {
	return validPo;
}

public void setValidPo(String validPo) {
	this.validPo = validPo;
}

public String getSupplierDefaultPaymentTerms() {
	return supplierDefaultPaymentTerms;
}

public void setSupplierDefaultPaymentTerms(String supplierDefaultPaymentTerms) {
	this.supplierDefaultPaymentTerms = supplierDefaultPaymentTerms;
}
}