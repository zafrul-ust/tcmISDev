package com.tcmis.internal.supply.beans;

import java.util.*;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.SQLException;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: SupplierPurchaseHistoryViewBean <br>
 * @version: 1.0, Jun 11, 2009 <br>
 *****************************************************************************/


public class SupplierPurchaseHistoryViewBean extends BaseDataBean {

	private BigDecimal unitPrice;
	private String supplierContactName;
	private String supplier;
	private String supplierName;
	private String shipToLocationId;
	private String shipToCompanyId;
	private BigDecimal quantityReceived;
	private BigDecimal quantity;
	private BigDecimal poLine;
	private String phone;
	private Date originalDateConfirmed;
	private BigDecimal itemId;
	private Date dateSent;
	private Date dateConfirmed;
	private String customerPo;
	private String currencyId;
	private String carrier;
	private String buyerName;
	private BigDecimal buyer;
	private String branchPlant;
	private BigDecimal radianPo;
	private String packaging;
	private String itemDesc;

	//constructor
	public SupplierPurchaseHistoryViewBean() {
	}

	//setters
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	public void setSupplierContactName(String supplierContactName) {
		this.supplierContactName = supplierContactName;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public void setShipToLocationId(String shipToLocationId) {
		this.shipToLocationId = shipToLocationId;
	}
	public void setShipToCompanyId(String shipToCompanyId) {
		this.shipToCompanyId = shipToCompanyId;
	}
	public void setQuantityReceived(BigDecimal quantityReceived) {
		this.quantityReceived = quantityReceived;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public void setPoLine(BigDecimal poLine) {
		this.poLine = poLine;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setOriginalDateConfirmed(Date originalDateConfirmed) {
		this.originalDateConfirmed = originalDateConfirmed;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setDateSent(Date dateSent) {
		this.dateSent = dateSent;
	}
	public void setDateConfirmed(Date dateConfirmed) {
		this.dateConfirmed = dateConfirmed;
	}
	public void setCustomerPo(String customerPo) {
		this.customerPo = customerPo;
	}
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	public void setBuyer(BigDecimal buyer) {
		this.buyer = buyer;
	}
	public void setBranchPlant(String branchPlant) {
		this.branchPlant = branchPlant;
	}
	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}
	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}


	//getters
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public String getSupplierContactName() {
		return supplierContactName;
	}
	public String getSupplier() {
		return supplier;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public String getShipToLocationId() {
		return shipToLocationId;
	}
	public String getShipToCompanyId() {
		return shipToCompanyId;
	}
	public BigDecimal getQuantityReceived() {
		return quantityReceived;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public BigDecimal getPoLine() {
		return poLine;
	}
	public String getPhone() {
		return phone;
	}
	public Date getOriginalDateConfirmed() {
		return originalDateConfirmed;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public Date getDateSent() {
		return dateSent;
	}
	public Date getDateConfirmed() {
		return dateConfirmed;
	}
	public String getCustomerPo() {
		return customerPo;
	}
	public String getCurrencyId() {
		return currencyId;
	}
	public String getCarrier() {
		return carrier;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public BigDecimal getBuyer() {
		return buyer;
	}
	public String getBranchPlant() {
		return branchPlant;
	}
	public BigDecimal getRadianPo() {
		return radianPo;
	}
	public String getPackaging() {
		return packaging;
	}
	public String getItemDesc() {
		return itemDesc;
	}

}