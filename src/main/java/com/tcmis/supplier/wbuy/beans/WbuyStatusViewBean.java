package com.tcmis.supplier.wbuy.beans;

import com.tcmis.common.framework.BaseDataBean;

import java.math.BigDecimal;
import java.util.Date;


/******************************************************************************
 * CLASSNAME: WbuyStatusViewBean <br>
 * @version: 1.0, Mar 17, 2009 <br>
 *****************************************************************************/
/**
 * Change History
 * --------------
 * 03/23/09 - Shahzad Butt - Recreated the view bean to bring search results 							 
 */

public class WbuyStatusViewBean extends BaseDataBean {

	
	private static final long serialVersionUID = -3747715466459926989L;
	
	private BigDecimal prNumber;
	private BigDecimal radianPo;
	private Date dateAcknowledgement;
	private Date dateSent;
	private Date dateConfirmed;
	private Date promisedDate;
	private Date vendorShipDate;
	private String transactorMailBoxAddress;
	private String transactorId;
	private String supplier;
	private String supplierName;
	private String shipToCompanyId;
	private String shipToLocationId;
	private Date dateCreated;
	private String critical;
	private BigDecimal bpo;
	private BigDecimal supplierContactId;
	private BigDecimal buyer;
	private String branchPlant;
	private String freightOnBoard;
	private String carrier;
	private String termsAndConditions;
	private String customerPo;
	private String paymentTerms;
	private String qualificationLevel;
	private String inventoryGroup;
	private String consignedPo;
	private String dbuyStatus;
	private Date dbuyStatusSetDate;
	private BigDecimal dbuyUserId;
	private BigDecimal daysSinceLastStatus;
	private String operatingEntityName;
	private String comments;
	private String homeCompanyName;

    //constructor
	public WbuyStatusViewBean() {
	}

	//setters
	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}
	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}
	public void setDateAcknowledgement(Date dateAcknowledgement) {
		this.dateAcknowledgement = dateAcknowledgement;
	}
	public void setDateSent(Date dateSent) {
		this.dateSent = dateSent;
	}
	public void setDateConfirmed(Date dateConfirmed) {
		this.dateConfirmed = dateConfirmed;
	}
	public void setPromisedDate(Date promisedDate) {
		this.promisedDate = promisedDate;
	}
	public void setVendorShipDate(Date vendorShipDate) {
		this.vendorShipDate = vendorShipDate;
	}
	public void setTransactorMailBoxAddress(String transactorMailBoxAddress) {
		this.transactorMailBoxAddress = transactorMailBoxAddress;
	}
	public void setTransactorId(String transactorId) {
		this.transactorId = transactorId;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public void setShipToCompanyId(String shipToCompanyId) {
		this.shipToCompanyId = shipToCompanyId;
	}
	public void setShipToLocationId(String shipToLocationId) {
		this.shipToLocationId = shipToLocationId;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public void setCritical(String critical) {
		this.critical = critical;
	}
	public void setBpo(BigDecimal bpo) {
		this.bpo = bpo;
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
	public void setFreightOnBoard(String freightOnBoard) {
		this.freightOnBoard = freightOnBoard;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
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
	public void setQualificationLevel(String qualificationLevel) {
		this.qualificationLevel = qualificationLevel;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setConsignedPo(String consignedPo) {
		this.consignedPo = consignedPo;
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
	public void setDaysSinceLastStatus(BigDecimal daysSinceLastStatus) {
		this.daysSinceLastStatus = daysSinceLastStatus;
	}
	public void setOperatingEntityName(String operatingEntityName) {
		this.operatingEntityName = operatingEntityName;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}


	//getters
	public BigDecimal getPrNumber() {
		return prNumber;
	}
	public BigDecimal getRadianPo() {
		return radianPo;
	}
	public Date getDateAcknowledgement() {
		return dateAcknowledgement;
	}
	public Date getDateSent() {
		return dateSent;
	}
	public Date getDateConfirmed() {
		return dateConfirmed;
	}
	public Date getPromisedDate() {
		return promisedDate;
	}
	public Date getVendorShipDate() {
		return vendorShipDate;
	}
	public String getTransactorMailBoxAddress() {
		return transactorMailBoxAddress;
	}
	public String getTransactorId() {
		return transactorId;
	}
	public String getSupplier() {
		return supplier;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public String getShipToCompanyId() {
		return shipToCompanyId;
	}
	public String getShipToLocationId() {
		return shipToLocationId;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public String getCritical() {
		return critical;
	}
	public BigDecimal getBpo() {
		return bpo;
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
	public String getFreightOnBoard() {
		return freightOnBoard;
	}
	public String getCarrier() {
		return carrier;
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
	public String getQualificationLevel() {
		return qualificationLevel;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public String getConsignedPo() {
		return consignedPo;
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
	public BigDecimal getDaysSinceLastStatus() {
		return daysSinceLastStatus;
	}
	public String getOperatingEntityName() {
		return operatingEntityName;
	}
	public String getHomeCompanyName() {
		return operatingEntityName;
	}
    public String getComments() {
		return comments;
	}
}