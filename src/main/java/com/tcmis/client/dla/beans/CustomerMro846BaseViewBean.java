package com.tcmis.client.dla.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: CustomerMro846BaseViewBean <br>
 * @version: 1.0, Nov 4, 2009 <br>
 *****************************************************************************/


public class CustomerMro846BaseViewBean extends BaseDataBean {

	private String catPartNo;
	private BigDecimal quantity;
	private String uom;
	private String storageAreaId;
	private String storageAreaQualifier;
	private String ownerIcpId;
	private String ownerIcpQualifier;
	private String supplyConditionCode;
	private String supplementalDataCode;
	private String purposeCode;
	private String inventoryTransactionCode;
	private String ownershipCode;
	private String shelfLifeCode;
	private String mediaStatusCode;
	private String transactionHistoryCode;
	private String projectCode;
	private String distributionCode;
	private String cognizanceSymbol;
	private BigDecimal frTransactionDateSentWeek;
	private String fmTransactionType;
	private String utilizationCode;
	private String transactionNumSuffix;
	private Date dateSent;
	private BigDecimal mrCreated;
	private String transactionRefNum;
	private BigDecimal transactionRefLineNum;
	private String docIdCode;


	//constructor
	public CustomerMro846BaseViewBean() {
	}

	//setters
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public void setStorageAreaId(String storageAreaId) {
		this.storageAreaId = storageAreaId;
	}
	public void setStorageAreaQualifier(String storageAreaQualifier) {
		this.storageAreaQualifier = storageAreaQualifier;
	}
	public void setOwnerIcpId(String ownerIcpId) {
		this.ownerIcpId = ownerIcpId;
	}
	public void setOwnerIcpQualifier(String ownerIcpQualifier) {
		this.ownerIcpQualifier = ownerIcpQualifier;
	}
	public void setSupplyConditionCode(String supplyConditionCode) {
		this.supplyConditionCode = supplyConditionCode;
	}
	public void setSupplementalDataCode(String supplementalDataCode) {
		this.supplementalDataCode = supplementalDataCode;
	}
	public void setPurposeCode(String purposeCode) {
		this.purposeCode = purposeCode;
	}
	public void setInventoryTransactionCode(String inventoryTransactionCode) {
		this.inventoryTransactionCode = inventoryTransactionCode;
	}
	public void setOwnershipCode(String ownershipCode) {
		this.ownershipCode = ownershipCode;
	}
	public void setShelfLifeCode(String shelfLifeCode) {
		this.shelfLifeCode = shelfLifeCode;
	}
	public void setMediaStatusCode(String mediaStatusCode) {
		this.mediaStatusCode = mediaStatusCode;
	}
	public void setTransactionHistoryCode(String transactionHistoryCode) {
		this.transactionHistoryCode = transactionHistoryCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public void setDistributionCode(String distributionCode) {
		this.distributionCode = distributionCode;
	}
	public void setCognizanceSymbol(String cognizanceSymbol) {
		this.cognizanceSymbol = cognizanceSymbol;
	}
	public void setFrTransactionDateSentWeek(BigDecimal frTransactionDateSentWeek) {
		this.frTransactionDateSentWeek = frTransactionDateSentWeek;
	}
	public void setFmTransactionType(String fmTransactionType) {
		this.fmTransactionType = fmTransactionType;
	}
	public void setUtilizationCode(String utilizationCode) {
		this.utilizationCode = utilizationCode;
	}
	public void setTransactionNumSuffix(String transactionNumSuffix) {
		this.transactionNumSuffix = transactionNumSuffix;
	}
	public void setDateSent(Date dateSent) {
		this.dateSent = dateSent;
	}
	public void setMrCreated(BigDecimal mrCreated) {
		this.mrCreated = mrCreated;
	}
	public void setTransactionRefNum(String transactionRefNum) {
		this.transactionRefNum = transactionRefNum;
	}
	public void setTransactionRefLineNum(BigDecimal transactionRefLineNum) {
		this.transactionRefLineNum = transactionRefLineNum;
	}
	public void setDocIdCode(String docIdCode) {
		this.docIdCode = docIdCode;
	}


	//getters
	public String getCatPartNo() {
		return catPartNo;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public String getUom() {
		return uom;
	}
	public String getStorageAreaId() {
		return storageAreaId;
	}
	public String getStorageAreaQualifier() {
		return storageAreaQualifier;
	}
	public String getOwnerIcpId() {
		return ownerIcpId;
	}
	public String getOwnerIcpQualifier() {
		return ownerIcpQualifier;
	}
	public String getSupplyConditionCode() {
		return supplyConditionCode;
	}
	public String getSupplementalDataCode() {
		return supplementalDataCode;
	}
	public String getPurposeCode() {
		return purposeCode;
	}
	public String getInventoryTransactionCode() {
		return inventoryTransactionCode;
	}
	public String getOwnershipCode() {
		return ownershipCode;
	}
	public String getShelfLifeCode() {
		return shelfLifeCode;
	}
	public String getMediaStatusCode() {
		return mediaStatusCode;
	}
	public String getTransactionHistoryCode() {
		return transactionHistoryCode;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public String getDistributionCode() {
		return distributionCode;
	}
	public String getCognizanceSymbol() {
		return cognizanceSymbol;
	}
	public BigDecimal getFrTransactionDateSentWeek() {
		return frTransactionDateSentWeek;
	}
	public String getFmTransactionType() {
		return fmTransactionType;
	}
	public String getUtilizationCode() {
		return utilizationCode;
	}
	public String getTransactionNumSuffix() {
		return transactionNumSuffix;
	}
	public Date getDateSent() {
		return dateSent;
	}
	public BigDecimal getMrCreated() {
		return mrCreated;
	}
	public String getTransactionRefNum() {
		return transactionRefNum;
	}
	public BigDecimal getTransactionRefLineNum() {
		return transactionRefLineNum;
	}
	public String getDocIdCode() {
		return docIdCode;
	}
}