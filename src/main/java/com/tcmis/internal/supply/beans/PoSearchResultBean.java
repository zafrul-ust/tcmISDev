package com.tcmis.internal.supply.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: PoSearchResultBean <br>
 * @version: 1.0, Sep 22, 2006 <br>
 *****************************************************************************/


public class PoSearchResultBean extends BaseDataBean {

	private String critical;
	private BigDecimal itemId;
	private String itemDesc;
	private String currencyId;
	private BigDecimal unitPrice;
	private BigDecimal quantity;
	private BigDecimal extPrice;
	private String paymentTerms;
	private BigDecimal radianPo;
	private String supplier;
	private String supplierName;
	private String buyerName;
	private BigDecimal buyer;
	private String branchPlant;
	private String hubName;
	private String inventoryGroup;
	private String inventoryGroupName;
	private Date dateCreated;
	private Date dateSent;
	private Date needDate;
	private Date promisedDate;
	private Date vendorShipDate;
	private Date revisedPromisedDate;
	private String customerPo;
	private String shipToAddress;
	private String partNumber;
	private BigDecimal qtyReceived;
	private Date dateConfirmed;
	private String mfgPartNo;
	private String manufacturer;
	private String supplierPartNo;
	private String supplyPath;
	
	private String opsEntityId;
	private BigDecimal quantityVouchered;
	
	private BigDecimal poLine;
	private String specList;
	private Date dateLastReceived;
	private String supplyPathJsp;

	public String getSpecList() {
		return specList;
	}

	public void setSpecList(String specList) {
		this.specList = specList;
	}

	//constructor
	public PoSearchResultBean() {
	}

	//setters
	public void setCritical(String critical) {
		this.critical = critical;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public void setExtPrice(BigDecimal extPrice) {
		this.extPrice = extPrice;
	}
	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}
	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
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
	public void setHubName(String hubName) {
		this.hubName = hubName;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setInventoryGroupName(String inventoryGroupName) {
		this.inventoryGroupName = inventoryGroupName;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public void setDateSent(Date dateSent) {
		this.dateSent = dateSent;
	}


	//getters
	public String getCritical() {
		return critical;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public BigDecimal getExtPrice() {
		return extPrice;
	}
	public String getPaymentTerms() {
		return paymentTerms;
	}
	public BigDecimal getRadianPo() {
		return radianPo;
	}
	public String getSupplier() {
		return supplier;
	}
	public String getSupplierName() {
		return supplierName;
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
	public String getHubName() {
		return hubName;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public String getInventoryGroupName() {
		return inventoryGroupName;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public Date getDateSent() {
		return dateSent;
	}

	public String getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	public Date getNeedDate() {
		return needDate;
	}

	public void setNeedDate(Date needDate) {
		this.needDate = needDate;
	}

	public Date getPromisedDate() {
		return promisedDate;
	}

	public void setPromisedDate(Date promisedDate) {
		this.promisedDate = promisedDate;
	}

	public Date getRevisedPromisedDate() {
		return revisedPromisedDate;
	}

	public void setRevisedPromisedDate(Date revisedPromisedDate) {
		this.revisedPromisedDate = revisedPromisedDate;
	}

	public Date getVendorShipDate() {
		return vendorShipDate;
	}

	public void setVendorShipDate(Date vendorShipDate) {
		this.vendorShipDate = vendorShipDate;
	}

	public String getCustomerPo() {
		return customerPo;
	}

	public void setCustomerPo(String customerPo) {
		this.customerPo = customerPo;
	}

	public String getShipToAddress() {
		return shipToAddress;
	}

	public void setShipToAddress(String shipToAddress) {
		this.shipToAddress = shipToAddress;
	}

	public Date getDateConfirmed() {
		return dateConfirmed;
	}

	public void setDateConfirmed(Date dateConfirmed) {
		this.dateConfirmed = dateConfirmed;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public BigDecimal getQtyReceived() {
		return qtyReceived;
	}

	public void setQtyReceived(BigDecimal qtyReceived) {
		this.qtyReceived = qtyReceived;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getMfgPartNo() {
		return mfgPartNo;
	}

	public void setMfgPartNo(String mfgPartNo) {
		this.mfgPartNo = mfgPartNo;
	}

	public String getSupplierPartNo() {
		return supplierPartNo;
	}

	public void setSupplierPartNo(String supplierPartNo) {
		this.supplierPartNo = supplierPartNo;
	}

	public String getSupplyPath() {
		return supplyPath;
	}

	public void setSupplyPath(String supplyPath) {
		this.supplyPath = supplyPath;
	}

	public String getOpsEntityId() {
		return opsEntityId;
	}

	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}

	public BigDecimal getQuantityVouchered() {
		return quantityVouchered;
	}

	public void setQuantityVouchered(BigDecimal quantityVouchered) {
		this.quantityVouchered = quantityVouchered;
	}

	public BigDecimal getPoLine() {
		return poLine;
	}

	public void setPoLine(BigDecimal poLine) {
		this.poLine = poLine;
	}

	public Date getDateLastReceived() {
		return dateLastReceived;
	}

	public void setDateLastReceived(Date dateLastReceived) {
		this.dateLastReceived = dateLastReceived;
	}
	
	public String getSupplyPathJsp() {
		return supplyPathJsp;
	}

	public void setSupplyPathJsp(String supplyPathJsp) {
		this.supplyPathJsp = supplyPathJsp;
	}
	
}