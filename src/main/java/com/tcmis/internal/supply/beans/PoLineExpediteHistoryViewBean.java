package com.tcmis.internal.supply.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: PoLineExpediteHistoryViewBean <br>
 * @version: 1.0, Jan 30, 2010 <br>
 *****************************************************************************/


public class PoLineExpediteHistoryViewBean extends BaseDataBean {

	private BigDecimal buyerId;
	private String supplier;
	private String branchPlant;
	private String inventoryGroup;
	private String customerPo;
	private String carrier;
	private Date dateSent;
	private String critical;
	private Date dateFirstConfirmed;
	private Date needDate;
	private Date vendorShipDate;
	private BigDecimal quantity;
	private BigDecimal qtyReceived;
	private BigDecimal unitPrice;
	private String currencyId;
	private String supplierName;
	private String buyerName;
	private BigDecimal quantityOpen;
	private String shipToAddress;
	private BigDecimal radianPo;
	private BigDecimal poLine;
	private BigDecimal itemId;
	private Date promisedDate;
	private Date revisedPromisedDate;
	private Date dateEntered;
	private String comments;
	private String enteredByName;
	private String opsEntityId;
    private String creditHold;
    
    public String getCreditHold() {
		return creditHold;
	}

	public void setCreditHold(String creditHold) {
		this.creditHold = creditHold;
	}


	//constructor
	public PoLineExpediteHistoryViewBean() {
	}

	//setters
	public void setBuyerId(BigDecimal buyerId) {
		this.buyerId = buyerId;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public void setBranchPlant(String branchPlant) {
		this.branchPlant = branchPlant;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setCustomerPo(String customerPo) {
		this.customerPo = customerPo;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	public void setDateSent(Date dateSent) {
		this.dateSent = dateSent;
	}
	public void setCritical(String critical) {
		this.critical = critical;
	}
	public void setDateFirstConfirmed(Date dateFirstConfirmed) {
		this.dateFirstConfirmed = dateFirstConfirmed;
	}
	public void setNeedDate(Date needDate) {
		this.needDate = needDate;
	}
	public void setVendorShipDate(Date vendorShipDate) {
		this.vendorShipDate = vendorShipDate;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public void setQtyReceived(BigDecimal qtyReceived) {
		this.qtyReceived = qtyReceived;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	public void setQuantityOpen(BigDecimal quantityOpen) {
		this.quantityOpen = quantityOpen;
	}
	public void setShipToAddress(String shipToAddress) {
		this.shipToAddress = shipToAddress;
	}
	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}
	public void setPoLine(BigDecimal poLine) {
		this.poLine = poLine;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setPromisedDate(Date promisedDate) {
		this.promisedDate = promisedDate;
	}
	public void setRevisedPromisedDate(Date revisedPromisedDate) {
		this.revisedPromisedDate = revisedPromisedDate;
	}
	public void setDateEntered(Date dateEntered) {
		this.dateEntered = dateEntered;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public void setEnteredByName(String enteredByName) {
		this.enteredByName = enteredByName;
	}
	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}


	//getters
	public BigDecimal getBuyerId() {
		return buyerId;
	}
	public String getSupplier() {
		return supplier;
	}
	public String getBranchPlant() {
		return branchPlant;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public String getCustomerPo() {
		return customerPo;
	}
	public String getCarrier() {
		return carrier;
	}
	public Date getDateSent() {
		return dateSent;
	}
	public String getCritical() {
		return critical;
	}
	public Date getDateFirstConfirmed() {
		return dateFirstConfirmed;
	}
	public Date getNeedDate() {
		return needDate;
	}
	public Date getVendorShipDate() {
		return vendorShipDate;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public BigDecimal getQtyReceived() {
		return qtyReceived;
	}
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public String getCurrencyId() {
		return currencyId;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public BigDecimal getQuantityOpen() {
		return quantityOpen;
	}
	public String getShipToAddress() {
		return shipToAddress;
	}
	public BigDecimal getRadianPo() {
		return radianPo;
	}
	public BigDecimal getPoLine() {
		return poLine;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public Date getPromisedDate() {
		return promisedDate;
	}
	public Date getRevisedPromisedDate() {
		return revisedPromisedDate;
	}
	public Date getDateEntered() {
		return dateEntered;
	}
	public String getComments() {
		return comments;
	}
	public String getEnteredByName() {
		return enteredByName;
	}
	public String getOpsEntityId() {
		return opsEntityId;
	}
}