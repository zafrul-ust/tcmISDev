package com.tcmis.supplier.dbuy.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: DbuyAcknowledgementBean <br>
 * @version: 1.0, Nov 16, 2005 <br>
 *****************************************************************************/


public class DbuyAcknowledgementBean extends BaseDataBean {

	private BigDecimal radianPo;
	private BigDecimal poLine;
	private String supplierName;
	private String shiptoCountryAbbrev;
	private String shiptoStateAbbrev;
	private String shiptoAddressLine1;
	private String shiptoAddressLine2;
	private String shiptoAddressLine3;
	private String shiptoCity;
	private String shiptoZip;
	private String supplierShipToCode;
	private BigDecimal itemId;
	private BigDecimal acceptedQuantity;
	private String acceptedUom;
	private BigDecimal acceptedUnitPrice;
	private String supplierPartNo;
	private String ediUom;
	private Date promisedDate;
	private String ediDocumentControlNumber;
	private Date datePoCreated;
	private Date transactionDate;
	private Date poLineConfirmedDate;
	private String supplierPartDesc;
	private String ediAcknowledgementCode;
	private String acceptedEdiDateCode;
	private String matched;
	private BigDecimal reviewerId;
	private Date reviewDate;
	private String reviewComments;
	private BigDecimal dbuyAcknowledgementId;
	private BigDecimal orderQuantity;
	private String mismatchComments;
	private String supplierSalesOrderNo;
	private Date supplierSalesOrderDate;
	private String done;
	private Date dockDate;
	private String poNotes;
	private String poLineNotes;
        private BigDecimal batchId;


	//constructor
	public DbuyAcknowledgementBean() {
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
	public void setSupplierShipToCode(String supplierShipToCode) {
		this.supplierShipToCode = supplierShipToCode;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setAcceptedQuantity(BigDecimal acceptedQuantity) {
		this.acceptedQuantity = acceptedQuantity;
	}
	public void setAcceptedUom(String acceptedUom) {
		this.acceptedUom = acceptedUom;
	}
	public void setAcceptedUnitPrice(BigDecimal acceptedUnitPrice) {
		this.acceptedUnitPrice = acceptedUnitPrice;
	}
	public void setSupplierPartNo(String supplierPartNo) {
		this.supplierPartNo = supplierPartNo;
	}
	public void setEdiUom(String ediUom) {
		this.ediUom = ediUom;
	}
	public void setPromisedDate(Date promisedDate) {
		this.promisedDate = promisedDate;
	}
	public void setEdiDocumentControlNumber(String ediDocumentControlNumber) {
		this.ediDocumentControlNumber = ediDocumentControlNumber;
	}
	public void setDatePoCreated(Date datePoCreated) {
		this.datePoCreated = datePoCreated;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public void setPoLineConfirmedDate(Date poLineConfirmedDate) {
		this.poLineConfirmedDate = poLineConfirmedDate;
	}
	public void setSupplierPartDesc(String supplierPartDesc) {
		this.supplierPartDesc = supplierPartDesc;
	}
	public void setEdiAcknowledgementCode(String ediAcknowledgementCode) {
		this.ediAcknowledgementCode = ediAcknowledgementCode;
	}
	public void setAcceptedEdiDateCode(String acceptedEdiDateCode) {
		this.acceptedEdiDateCode = acceptedEdiDateCode;
	}
	public void setMatched(String matched) {
		this.matched = matched;
	}
	public void setReviewerId(BigDecimal reviewerId) {
		this.reviewerId = reviewerId;
	}
	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}
	public void setReviewComments(String reviewComments) {
		this.reviewComments = reviewComments;
	}
	public void setDbuyAcknowledgementId(BigDecimal dbuyAcknowledgementId) {
		this.dbuyAcknowledgementId = dbuyAcknowledgementId;
	}
	public void setOrderQuantity(BigDecimal orderQuantity) {
		this.orderQuantity = orderQuantity;
	}
	public void setMismatchComments(String mismatchComments) {
		this.mismatchComments = mismatchComments;
	}
	public void setSupplierSalesOrderNo(String supplierSalesOrderNo) {
		this.supplierSalesOrderNo = supplierSalesOrderNo;
	}
	public void setSupplierSalesOrderDate(Date supplierSalesOrderDate) {
		this.supplierSalesOrderDate = supplierSalesOrderDate;
	}
	public void setDone(String done) {
		this.done = done;
	}
	public void setDockDate(Date dockDate) {
		this.dockDate = dockDate;
	}
	public void setPoNotes(String poNotes) {
		this.poNotes = poNotes;
	}
	public void setPoLineNotes(String poLineNotes) {
		this.poLineNotes = poLineNotes;
	}
	public void setBatchId(BigDecimal batchId) {
		this.batchId = batchId;
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
	public String getSupplierShipToCode() {
		return supplierShipToCode;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public BigDecimal getAcceptedQuantity() {
		return acceptedQuantity;
	}
	public String getAcceptedUom() {
		return acceptedUom;
	}
	public BigDecimal getAcceptedUnitPrice() {
		return acceptedUnitPrice;
	}
	public String getSupplierPartNo() {
		return supplierPartNo;
	}
	public String getEdiUom() {
		return ediUom;
	}
	public Date getPromisedDate() {
		return promisedDate;
	}
	public String getEdiDocumentControlNumber() {
		return ediDocumentControlNumber;
	}
	public Date getDatePoCreated() {
		return datePoCreated;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public Date getPoLineConfirmedDate() {
		return poLineConfirmedDate;
	}
	public String getSupplierPartDesc() {
		return supplierPartDesc;
	}
	public String getEdiAcknowledgementCode() {
		return ediAcknowledgementCode;
	}
	public String getAcceptedEdiDateCode() {
		return acceptedEdiDateCode;
	}
	public String getMatched() {
		return matched;
	}
	public BigDecimal getReviewerId() {
		return reviewerId;
	}
	public Date getReviewDate() {
		return reviewDate;
	}
	public String getReviewComments() {
		return reviewComments;
	}
	public BigDecimal getDbuyAcknowledgementId() {
		return dbuyAcknowledgementId;
	}
	public BigDecimal getOrderQuantity() {
		return orderQuantity;
	}
	public String getMismatchComments() {
		return mismatchComments;
	}
	public String getSupplierSalesOrderNo() {
		return supplierSalesOrderNo;
	}
	public Date getSupplierSalesOrderDate() {
		return supplierSalesOrderDate;
	}
	public String getDone() {
		return done;
	}
	public Date getDockDate() {
		return dockDate;
	}
	public String getPoNotes() {
		return poNotes;
	}
	public String getPoLineNotes() {
		return poLineNotes;
	}
        public BigDecimal getBatchId() {
		return batchId;
	}        
}