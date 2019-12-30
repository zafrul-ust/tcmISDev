package com.tcmis.client.purchasing.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: BuyerCompanyPoViewBean <br>
 * 
 * @version: 1.0, Apr 8, 2008 <br>
 *****************************************************************************/

public class BuyerCompanyPoViewBean extends BaseDataBean {

	private String		catalogCompanyId;
	private String		catalogId;
	private String		catPartNo;
	private String		companyId;
	private Date		dateIssued;
	private BigDecimal	defaultBuyerId;
	private Date		expectedDeliveryDate;
	private String		inventoryGroup;
	private String		inventoryGroupName;
	private String		itemDesc;
	private BigDecimal	itemId;
	private BigDecimal	orderQuantity;
	private String		packaging;
	private String		poNumber;
	private BigDecimal	prNumber;
	private BigDecimal	quantityToReceive;
	private String		readOnly;
	private String		sizeUnit;
	private String		status;
	private String		supplierName;
	private BigDecimal	userId;

	// constructor
	public BuyerCompanyPoViewBean() {
	}

	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public String getCatPartNo() {
		return catPartNo;
	}

	public String getCompanyId() {
		return companyId;
	}

	public Date getDateIssued() {
		return dateIssued;
	}

	public BigDecimal getDefaultBuyerId() {
		return this.defaultBuyerId;
	}

	public Date getExpectedDeliveryDate() {
		return expectedDeliveryDate;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public String getInventoryGroupName() {
		return inventoryGroupName;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public BigDecimal getOrderQuantity() {
		return orderQuantity;
	}

	public String getPackaging() {
		return packaging;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public BigDecimal getPrNumber() {
		return prNumber;
	}

	public BigDecimal getQuantityToReceive() {
		return quantityToReceive;
	}

	public String getReadOnly() {
		return readOnly;
	}

	public String getSizeUnit() {
		return sizeUnit;
	}

	public String getStatus() {
		return status;
	}

	public String getSupplierName() {
		return supplierName;
	}

	// getters
	public BigDecimal getUserId() {
		return userId;
	}

	public void setCatalogCompanyId(String catalogCompanyId) {
		this.catalogCompanyId = catalogCompanyId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setDateIssued(Date dateIssued) {
		this.dateIssued = dateIssued;
	}

	public void setDefaultBuyerId(BigDecimal defaultBuyerId) {
		this.defaultBuyerId = defaultBuyerId;
	}

	public void setExpectedDeliveryDate(Date expectedDeliveryDate) {
		this.expectedDeliveryDate = expectedDeliveryDate;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public void setInventoryGroupName(String inventoryGroupName) {
		this.inventoryGroupName = inventoryGroupName;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public void setOrderQuantity(BigDecimal orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}

	public void setQuantityToReceive(BigDecimal quantityToReceive) {
		this.quantityToReceive = quantityToReceive;
	}

	public void setReadOnly(String readOnly) {
		this.readOnly = readOnly;
	}

	public void setSizeUnit(String sizeUnit) {
		this.sizeUnit = sizeUnit;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	// setters
	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}
}