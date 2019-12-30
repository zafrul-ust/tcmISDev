package com.tcmis.internal.supply.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: PoViewBean <br>
 * @version: 1.0, Jan 16, 2008 <br>
 *****************************************************************************/


public class PoViewBean extends BaseDataBean {

	private BigDecimal radianPo;
	private BigDecimal poLine;
	private String branchPlant;
	private BigDecimal itemId;
	private BigDecimal quantity;
	private BigDecimal unitPrice;
	private String currencyId;
	private Date dateSent;
	private BigDecimal buyer;
	private String supplier;
	private Date dateConfirmed;
	private String shipToCompanyId;
	private String shipToLocationId;
	private String supplierName;
	private String supplierContactName;
	private String phone;
	private String buyerName;
	private BigDecimal quantityReceived;
	private Date firstTimeReceived;
	private String carrier;
	private Date originalDateConfirmed;
	private String customerPo;
	private String opsEntityId;
	private String inventoryGroup;
	private String inventoryGroupName;


	//constructor
	public PoViewBean() {
	}

	//setters
	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}
	public void setPoLine(BigDecimal poLine) {
		this.poLine = poLine;
	}
	public void setBranchPlant(String branchPlant) {
		this.branchPlant = branchPlant;
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
	public void setDateSent(Date dateSent) {
		this.dateSent = dateSent;
	}
	public void setBuyer(BigDecimal buyer) {
		this.buyer = buyer;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public void setDateConfirmed(Date dateConfirmed) {
		this.dateConfirmed = dateConfirmed;
	}
	public void setShipToCompanyId(String shipToCompanyId) {
		this.shipToCompanyId = shipToCompanyId;
	}
	public void setShipToLocationId(String shipToLocationId) {
		this.shipToLocationId = shipToLocationId;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public void setSupplierContactName(String supplierContactName) {
		this.supplierContactName = supplierContactName;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	public void setQuantityReceived(BigDecimal quantityReceived) {
		this.quantityReceived = quantityReceived;
	}
	public void setFirstTimeReceived(Date firstTimeReceived) {
		this.firstTimeReceived = firstTimeReceived;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	public void setOriginalDateConfirmed(Date originalDateConfirmed) {
		this.originalDateConfirmed = originalDateConfirmed;
	}
	public void setCustomerPo(String customerPo) {
		this.customerPo = customerPo;
	}
	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setInventoryGroupName(String inventoryGroupName) {
		this.inventoryGroupName = inventoryGroupName;
	}


	//getters
	public BigDecimal getRadianPo() {
		return radianPo;
	}
	public BigDecimal getPoLine() {
		return poLine;
	}
	public String getBranchPlant() {
		return branchPlant;
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
	public Date getDateSent() {
		return dateSent;
	}
	public BigDecimal getBuyer() {
		return buyer;
	}
	public String getSupplier() {
		return supplier;
	}
	public Date getDateConfirmed() {
		return dateConfirmed;
	}
	public String getShipToCompanyId() {
		return shipToCompanyId;
	}
	public String getShipToLocationId() {
		return shipToLocationId;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public String getSupplierContactName() {
		return supplierContactName;
	}
	public String getPhone() {
		return phone;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public BigDecimal getQuantityReceived() {
		return quantityReceived;
	}
	public Date getFirstTimeReceived() {
		return firstTimeReceived;
	}
	public String getCarrier() {
		return carrier;
	}
	public Date getOriginalDateConfirmed() {
		return originalDateConfirmed;
	}
	public String getCustomerPo() {
		return customerPo;
	}
	public String getOpsEntityId() {
		return opsEntityId;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public String getInventoryGroupName() {
		return inventoryGroupName;
	}
}