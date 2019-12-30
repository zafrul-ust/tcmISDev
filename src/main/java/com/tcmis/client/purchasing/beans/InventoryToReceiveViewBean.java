package com.tcmis.client.purchasing.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: InventoryToReceiveViewBean <br>
 * @version: 1.0, Apr 8, 2008 <br>
 *****************************************************************************/


public class InventoryToReceiveViewBean extends BaseDataBean {

	private BigDecimal userId;
	private String inventoryGroup;
	private String readOnly;
	private String ownerCompanyId;
	private BigDecimal buyerId;
	private String buyerName;
	private String poNumber;
	private String catPartNo;
	private String inventoryGroupName;
	private BigDecimal itemId;
	private String itemDesc;
	private BigDecimal quantityToReceive;
	private BigDecimal totalQuantityReceived;
	private String packaging;
	private String supplierName;
	private Date expectedDeliveryDate;
	private Date dateInserted;
	private BigDecimal docNum;


	//constructor
	public InventoryToReceiveViewBean() {
	}

	//setters
	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setOwnerCompanyId(String ownerCompanyId) {
		this.ownerCompanyId = ownerCompanyId;
	}
	public void setBuyerId(BigDecimal buyerId) {
		this.buyerId = buyerId;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	public void setInventoryGroupName(String inventoryGroupName) {
		this.inventoryGroupName = inventoryGroupName;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public void setQuantityToReceive(BigDecimal quantityToReceive) {
		this.quantityToReceive = quantityToReceive;
	}
	public void setTotalQuantityReceived(BigDecimal totalQuantityReceived) {
		this.totalQuantityReceived = totalQuantityReceived;
	}
	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public void setExpectedDeliveryDate(Date expectedDeliveryDate) {
		this.expectedDeliveryDate = expectedDeliveryDate;
	}
	public void setDateInserted(Date dateInserted) {
		this.dateInserted = dateInserted;
	}
	public void setDocNum(BigDecimal docNum) {
		this.docNum = docNum;
	}

public void setReadOnly(String readOnly) {
	this.readOnly = readOnly;
}

public void setCatPartNo(String catPartNo) {
	this.catPartNo = catPartNo;

}//getters
	public BigDecimal getUserId() {
		return userId;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public String getOwnerCompanyId() {
		return ownerCompanyId;
	}
	public BigDecimal getBuyerId() {
		return buyerId;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public String getPoNumber() {
		return poNumber;
	}
	public String getInventoryGroupName() {
		return inventoryGroupName;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public BigDecimal getQuantityToReceive() {
		return quantityToReceive;
	}
	public BigDecimal getTotalQuantityReceived() {
		return totalQuantityReceived;
	}
	public String getPackaging() {
		return packaging;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public Date getExpectedDeliveryDate() {
		return expectedDeliveryDate;
	}
	public Date getDateInserted() {
		return dateInserted;
	}
	public BigDecimal getDocNum() {
		return docNum;
	}

public String getReadOnly() {
	return readOnly;
}

public String getCatPartNo() {
	return catPartNo;
}
}