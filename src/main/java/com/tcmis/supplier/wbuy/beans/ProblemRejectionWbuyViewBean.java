package com.tcmis.supplier.wbuy.beans;

import com.tcmis.common.framework.BaseDataBean;

import java.math.BigDecimal;
import java.util.Date;


/******************************************************************************
 * CLASSNAME: ProblemRejectionWbuyViewBean <br>
 * @version: 1.0, Jul 1, 2005 <br>
 *****************************************************************************/


public class ProblemRejectionWbuyViewBean extends BaseDataBean {

	
	private static final long serialVersionUID = 7728514015012704329L;
	
	private BigDecimal radianPo;
	private BigDecimal dbuyUserId;
	private String comments;
	private Date statusDate;
	private Date promisedDate;
	private Date vendorShipDate;
	private String supplier;
	private String supplierName;
	private String shipToCompanyId;
	private String shipToLocationId;
	private Date dateCreated;
	private String critical;
	private String freightOnBoard;
	private String carrier;
	private String paymentTerms;
	private String inventoryGroup;
	private String consignedPo;
	private String dbuyStatus;
	private BigDecimal itemId;
	private BigDecimal unitPrice;
	private BigDecimal quantity;
	private String userName;


	//constructor
	public ProblemRejectionWbuyViewBean() {
	}

	//setters
	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}
	public void setDbuyUserId(BigDecimal dbuyUserId) {
		this.dbuyUserId = dbuyUserId;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public void setStatusDate(Date statusDate) {
		this.statusDate = statusDate;
	}
	public void setPromisedDate(Date promisedDate) {
		this.promisedDate = promisedDate;
	}
	public void setVendorShipDate(Date vendorShipDate) {
		this.vendorShipDate = vendorShipDate;
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
	public void setFreightOnBoard(String freightOnBoard) {
		this.freightOnBoard = freightOnBoard;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
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
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}


	//getters
	public BigDecimal getRadianPo() {
		return radianPo;
	}
	public BigDecimal getDbuyUserId() {
		return dbuyUserId;
	}
	public String getComments() {
		return comments;
	}
	public Date getStatusDate() {
		return statusDate;
	}
	public Date getPromisedDate() {
		return promisedDate;
	}
	public Date getVendorShipDate() {
		return vendorShipDate;
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
	public String getFreightOnBoard() {
		return freightOnBoard;
	}
	public String getCarrier() {
		return carrier;
	}
	public String getPaymentTerms() {
		return paymentTerms;
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
	public BigDecimal getItemId() {
		return itemId;
	}
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public String getUserName() {
		return userName;
	}
}