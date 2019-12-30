package com.tcmis.internal.supply.beans;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;

import org.apache.struts.action.ActionForm;

import com.tcmis.common.framework.HubBaseInputBean;


/******************************************************************************
 * CLASSNAME: PoBean <br>
 * @version: 1.0, Aug 11, 2008 <br>
 *****************************************************************************/


public class PoBean extends HubBaseInputBean {

	private BigDecimal radianPo;
	private BigDecimal bpo;
	private String supplier;
	private BigDecimal supplierContactId;
	private BigDecimal buyer;
	private String branchPlant;
	private String shipToCompanyId;
	private String shipToLocationId;
	private String freightOnBoard;
	private String carrier;
	private Date dateSent;
	private Date dateAccepted;
	private String termsAndConditions;
	private String customerPo;
	private String paymentTerms;
	private Date dateCreated;
	private String critical;
	private String qualificationLevel;
	private String inventoryGroup;
	private String followupEmails;
	private String consignedPo;
	private BigDecimal dbuyContractId;
	private String ediDocumentControlNumber;
	private Date dateAcknowledgement;
	private String dbuyStatus;
	private Date dbuyStatusSetDate;
	private BigDecimal dbuyUserId;
	private String buyerCompanyId;
	private String shipToFacilityId;
	private String ediInterchangeControlNumber;
	private String ediTradingPartnerId;


	public PoBean() {
		super();
	}

	public PoBean(ActionForm inputForm) {
		super(inputForm);
	}

	public PoBean(ActionForm inputForm, Locale locale) {
		super(inputForm, locale);
	}


	//setters
	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}
	public void setBpo(BigDecimal bpo) {
		this.bpo = bpo;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public void setSupplierContactId(BigDecimal supplierContactId) {
		this.supplierContactId = supplierContactId;
	}
	public void setBuyer(BigDecimal buyer) {
		this.buyer = buyer;
	}
	public void setBranchPlant(String branchPlant) {
		this.branchPlant = branchPlant;
	}
	public void setShipToCompanyId(String shipToCompanyId) {
		this.shipToCompanyId = shipToCompanyId;
	}
	public void setShipToLocationId(String shipToLocationId) {
		this.shipToLocationId = shipToLocationId;
	}
	public void setFreightOnBoard(String freightOnBoard) {
		this.freightOnBoard = freightOnBoard;
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
	public void setCustomerPo(String customerPo) {
		this.customerPo = customerPo;
	}
	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
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
	public void setFollowupEmails(String followupEmails) {
		this.followupEmails = followupEmails;
	}
	public void setConsignedPo(String consignedPo) {
		this.consignedPo = consignedPo;
	}
	public void setDbuyContractId(BigDecimal dbuyContractId) {
		this.dbuyContractId = dbuyContractId;
	}
	public void setEdiDocumentControlNumber(String ediDocumentControlNumber) {
		this.ediDocumentControlNumber = ediDocumentControlNumber;
	}
	public void setDateAcknowledgement(Date dateAcknowledgement) {
		this.dateAcknowledgement = dateAcknowledgement;
	}
	public void setDbuyStatus(String dbuyStatus) {
		this.dbuyStatus = dbuyStatus;
	}
	public void setDbuyStatusSetDate(Date dbuyStatusSetDate) {
		this.dbuyStatusSetDate = dbuyStatusSetDate;
	}
	public void setDbuyUserId(BigDecimal dbuyUserId) {
		this.dbuyUserId = dbuyUserId;
	}
	public void setBuyerCompanyId(String buyerCompanyId) {
		this.buyerCompanyId = buyerCompanyId;
	}
	public void setShipToFacilityId(String shipToFacilityId) {
		this.shipToFacilityId = shipToFacilityId;
	}
	public void setEdiInterchangeControlNumber(String ediInterchangeControlNumber) {
		this.ediInterchangeControlNumber = ediInterchangeControlNumber;
	}
	public void setEdiTradingPartnerId(String ediTradingPartnerId) {
		this.ediTradingPartnerId = ediTradingPartnerId;
	}


	//getters
	public BigDecimal getRadianPo() {
		return radianPo;
	}
	public BigDecimal getBpo() {
		return bpo;
	}
	public String getSupplier() {
		return supplier;
	}
	public BigDecimal getSupplierContactId() {
		return supplierContactId;
	}
	public BigDecimal getBuyer() {
		return buyer;
	}
	public String getBranchPlant() {
		return branchPlant;
	}
	public String getShipToCompanyId() {
		return shipToCompanyId;
	}
	public String getShipToLocationId() {
		return shipToLocationId;
	}
	public String getFreightOnBoard() {
		return freightOnBoard;
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
	public String getCustomerPo() {
		return customerPo;
	}
	public String getPaymentTerms() {
		return paymentTerms;
	}
	public Date getDateCreated() {
		return dateCreated;
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
	public String getFollowupEmails() {
		return followupEmails;
	}
	public String getConsignedPo() {
		return consignedPo;
	}
	public BigDecimal getDbuyContractId() {
		return dbuyContractId;
	}
	public String getEdiDocumentControlNumber() {
		return ediDocumentControlNumber;
	}
	public Date getDateAcknowledgement() {
		return dateAcknowledgement;
	}
	public String getDbuyStatus() {
		return dbuyStatus;
	}
	public Date getDbuyStatusSetDate() {
		return dbuyStatusSetDate;
	}
	public BigDecimal getDbuyUserId() {
		return dbuyUserId;
	}
	public String getBuyerCompanyId() {
		return buyerCompanyId;
	}
	public String getShipToFacilityId() {
		return shipToFacilityId;
	}
	public String getEdiInterchangeControlNumber() {
		return ediInterchangeControlNumber;
	}
	public String getEdiTradingPartnerId() {
		return ediTradingPartnerId;
	}

	@Override
	public void setHiddenFormFields() {
		// TODO Auto-generated method stub
		
	}
}