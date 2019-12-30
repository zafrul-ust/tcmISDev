package com.tcmis.internal.supply.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: PoLineHistoryViewBean <br>
 * @version: 1.0, Aug 30, 2007 <br>
 *****************************************************************************/


public class PoLineHistoryViewBean extends BaseDataBean {

	private BigDecimal radianPo;
	private BigDecimal poLine;
	private BigDecimal amendment;
	private BigDecimal itemId;
	private BigDecimal quantity;
	private BigDecimal unitPrice;
	private String currencyId;
	private Date needDate;
	private Date promisedDate;
	private BigDecimal allowedPriceVariance;
	private String mfgPartNo;
	private String supplierPartNo;
	private String dpasRating;
	private String qualityFlowDowns;
	private String packagingFlowDowns;
	private String poLineNote;
	private Date transactionDate;
	private Date dateConfirmed;
	private String remarks;
	private BigDecimal supplierQty;
	private String supplierPkg;
	private BigDecimal supplierUnitPrice;
	private String genericCoc;
	private String genericCoa;
	private Date msdsRequestedDate;
	private BigDecimal remainingShelfLifePercent;
	private String deliveryComments;
	private Date vendorShipDate;


	//constructor
	public PoLineHistoryViewBean() {
	}

	//setters
	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}
	public void setPoLine(BigDecimal poLine) {
		this.poLine = poLine;
	}
	public void setAmendment(BigDecimal amendment) {
		this.amendment = amendment;
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
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}
	public void setNeedDate(Date needDate) {
		this.needDate = needDate;
	}
	public void setPromisedDate(Date promisedDate) {
		this.promisedDate = promisedDate;
	}
	public void setAllowedPriceVariance(BigDecimal allowedPriceVariance) {
		this.allowedPriceVariance = allowedPriceVariance;
	}
	public void setMfgPartNo(String mfgPartNo) {
		this.mfgPartNo = mfgPartNo;
	}
	public void setSupplierPartNo(String supplierPartNo) {
		this.supplierPartNo = supplierPartNo;
	}
	public void setDpasRating(String dpasRating) {
		this.dpasRating = dpasRating;
	}
	public void setQualityFlowDowns(String qualityFlowDowns) {
		this.qualityFlowDowns = qualityFlowDowns;
	}
	public void setPackagingFlowDowns(String packagingFlowDowns) {
		this.packagingFlowDowns = packagingFlowDowns;
	}
	public void setPoLineNote(String poLineNote) {
		this.poLineNote = poLineNote;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public void setDateConfirmed(Date dateConfirmed) {
		this.dateConfirmed = dateConfirmed;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public void setSupplierQty(BigDecimal supplierQty) {
		this.supplierQty = supplierQty;
	}
	public void setSupplierPkg(String supplierPkg) {
		this.supplierPkg = supplierPkg;
	}
	public void setSupplierUnitPrice(BigDecimal supplierUnitPrice) {
		this.supplierUnitPrice = supplierUnitPrice;
	}
	public void setGenericCoc(String genericCoc) {
		this.genericCoc = genericCoc;
	}
	public void setGenericCoa(String genericCoa) {
		this.genericCoa = genericCoa;
	}
	public void setMsdsRequestedDate(Date msdsRequestedDate) {
		this.msdsRequestedDate = msdsRequestedDate;
	}
	public void setRemainingShelfLifePercent(BigDecimal remainingShelfLifePercent) {
		this.remainingShelfLifePercent = remainingShelfLifePercent;
	}
	public void setDeliveryComments(String deliveryComments) {
		this.deliveryComments = deliveryComments;
	}
	public void setVendorShipDate(Date vendorShipDate) {
		this.vendorShipDate = vendorShipDate;
	}


	//getters
	public BigDecimal getRadianPo() {
		return radianPo;
	}
	public BigDecimal getPoLine() {
		return poLine;
	}
	public BigDecimal getAmendment() {
		return amendment;
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
	public String getCurrencyId() {
		return currencyId;
	}
	public Date getNeedDate() {
		return needDate;
	}
	public Date getPromisedDate() {
		return promisedDate;
	}
	public BigDecimal getAllowedPriceVariance() {
		return allowedPriceVariance;
	}
	public String getMfgPartNo() {
		return mfgPartNo;
	}
	public String getSupplierPartNo() {
		return supplierPartNo;
	}
	public String getDpasRating() {
		return dpasRating;
	}
	public String getQualityFlowDowns() {
		return qualityFlowDowns;
	}
	public String getPackagingFlowDowns() {
		return packagingFlowDowns;
	}
	public String getPoLineNote() {
		return poLineNote;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public Date getDateConfirmed() {
		return dateConfirmed;
	}
	public String getRemarks() {
		return remarks;
	}
	public BigDecimal getSupplierQty() {
		return supplierQty;
	}
	public String getSupplierPkg() {
		return supplierPkg;
	}
	public BigDecimal getSupplierUnitPrice() {
		return supplierUnitPrice;
	}
	public String getGenericCoc() {
		return genericCoc;
	}
	public String getGenericCoa() {
		return genericCoa;
	}
	public Date getMsdsRequestedDate() {
		return msdsRequestedDate;
	}
	public BigDecimal getRemainingShelfLifePercent() {
		return remainingShelfLifePercent;
	}
	public String getDeliveryComments() {
		return deliveryComments;
	}
	public Date getVendorShipDate() {
		return vendorShipDate;
	}
}