package com.tcmis.ws.scanner.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: PoLineBean <br>
 * 
 * @version: 1.0, Mar 7, 2013 <br>
 *****************************************************************************/

public class InboundShipment extends BaseDataBean {

	private String		accountNumber;
	private String		accountNumber2;
	private String		accountNumber3;
	private String		accountNumber4;
	private String		catalogCompanyId;
	private String		catalogId;
	private String		catPartNo;
	private String		codeSection;
	private Date		completedDate;
	private String		customerPo;
	private String		customerReceiptId;
	private BigDecimal	customerRmaId;
	private String		displayPkgStyle;
	private BigDecimal	docNum;
	private String		hub;
	private String		inventoryGroup;
	private String		itemDescription;
	private BigDecimal	itemId;
	private Date		lastModifiedDate;
	private BigDecimal	lineItem;
	private BigDecimal	materialId;
	private boolean		mvItem;
	private BigDecimal	numberOfKits;
	private BigDecimal	originalReceiptId;
	private String		ownerCompanyId;
	private String		ownerSegmentId;
	private String		packaging;
	private BigDecimal	partId;
	private String		purchasingUnitOfMeasure;
	private BigDecimal	purchasingUnitsPerItem;
	private String		qualityTrackingNumber;
	private BigDecimal	quantity;
	private BigDecimal	quantityOpen;
	private BigDecimal	radianPo;
	public String		storageTemp;
	private BigDecimal	transferRequestId;


	public String getAccountNumber() {
		return accountNumber;
	}

	public String getAccountNumber2() {
		return accountNumber2;
	}

	public String getAccountNumber3() {
		return accountNumber3;
	}

	public String getAccountNumber4() {
		return accountNumber4;
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

	public String getCodeSection() {
		return codeSection;
	}

	public Date getCompletedDate() {
		return this.completedDate;
	}

	public String getCustomerPo() {
		return customerPo;
	}

	public String getCustomerReceiptId() {
		return customerReceiptId;
	}

	public BigDecimal getCustomerRmaId() {
		return customerRmaId;
	}

	public String getDisplayPkgStyle() {
		return displayPkgStyle;
	}

	public BigDecimal getDocNum() {
		return docNum;
	}

	public String getHub() {
		return hub;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public Date getLastModifiedDate() {
		return this.lastModifiedDate;
	}

	public BigDecimal getLineItem() {
		return lineItem;
	}

	public BigDecimal getMaterialId() {
		return materialId;
	}

	public BigDecimal getNumberOfKits() {
		return numberOfKits;
	}

	public BigDecimal getOriginalReceiptId() {
		return originalReceiptId;
	}

	public String getOwnerCompanyId() {
		return ownerCompanyId;
	}

	public String getOwnerSegmentId() {
		return ownerSegmentId;
	}

	public String getPackaging() {
		return packaging;
	}

	public BigDecimal getPartId() {
		return partId;
	}

	public String getPurchasingUnitOfMeasure() {
		return purchasingUnitOfMeasure;
	}

	public BigDecimal getPurchasingUnitsPerItem() {
		return purchasingUnitsPerItem;
	}

	public String getQualityTrackingNumber() {
		return qualityTrackingNumber;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public BigDecimal getQuantityOpen() {
		return quantityOpen;
	}

	public BigDecimal getRadianPo() {
		return radianPo;
	}

	public String getStorageTemp() {
		return storageTemp;
	}

	public BigDecimal getTransferRequestId() {
		return transferRequestId;
	}
	
	public boolean hasInventoryGroup() {
		return !StringHandler.isBlankString(inventoryGroup);
	}

	public boolean hasDocNum() {
		return docNum != null;
	}
	
	public boolean hasRadianPo() {
		return radianPo != null;
	}
	
	public boolean hasLineItem() {
		return lineItem != null;
	}
	
	public boolean hasOriginalReceiptId () {
		return originalReceiptId != null;
	}

	public boolean isMvItem() {
		return mvItem;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public void setAccountNumber2(String accountNumber2) {
		this.accountNumber2 = accountNumber2;
	}

	public void setAccountNumber3(String accountNumber3) {
		this.accountNumber3 = accountNumber3;
	}

	public void setAccountNumber4(String accountNumber4) {
		this.accountNumber4 = accountNumber4;
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

	public void setCodeSection(String codeSection) {
		this.codeSection = codeSection;
	}

	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
	}

	public void setCustomerPo(String customerPo) {
		this.customerPo = customerPo;
	}

	public void setCustomerReceiptId(String customerReceiptId) {
		this.customerReceiptId = customerReceiptId;
	}

	public void setCustomerRmaId(BigDecimal customerRmaId) {
		this.customerRmaId = customerRmaId;
	}

	public void setDisplayPkgStyle(String displayPkgStyle) {
		this.displayPkgStyle = displayPkgStyle;
	}

	public void setDocNum(BigDecimal docNum) {
		this.docNum = docNum;
	}

	public void setHub(String hub) {
		this.hub = hub;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public void setLineItem(BigDecimal lineItem) {
		this.lineItem = lineItem;
	}

	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}

	public void setMvItem(boolean mvItem) {
		this.mvItem = mvItem;
	}

	public void setNumberOfKits(BigDecimal numberOfKits) {
		this.numberOfKits = numberOfKits;
	}

	public void setOriginalReceiptId(BigDecimal originalReceiptId) {
		this.originalReceiptId = originalReceiptId;
	}

	public void setOwnerCompanyId(String ownerCompanyId) {
		this.ownerCompanyId = ownerCompanyId;
	}

	public void setOwnerSegmentId(String ownerSegmentId) {
		this.ownerSegmentId = ownerSegmentId;
	}

	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}

	public void setPartId(BigDecimal partId) {
		this.partId = partId;
	}

	public void setPurchasingUnitOfMeasure(String purchasingUnitOfMeasure) {
		this.purchasingUnitOfMeasure = purchasingUnitOfMeasure;
	}

	public void setPurchasingUnitsPerItem(BigDecimal purchasingUnitsPerItem) {
		this.purchasingUnitsPerItem = purchasingUnitsPerItem;
	}

	public void setQualityTrackingNumber(String qualityTrackingNumber) {
		this.qualityTrackingNumber = qualityTrackingNumber;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public void setQuantityOpen(BigDecimal quantityOpen) {
		this.quantityOpen = quantityOpen;
	}

	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}

	public void setStorageTemp(String storageTemp) {
		this.storageTemp = storageTemp;
	}

	public void setTransferRequestId(BigDecimal transferRequestId) {
		this.transferRequestId = transferRequestId;
	}
}