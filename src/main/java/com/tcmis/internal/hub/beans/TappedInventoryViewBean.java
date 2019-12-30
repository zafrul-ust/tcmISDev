package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: TappedInventoryViewBean <br>
 * @version: 1.0, Aug 30, 2007 <br>
 *****************************************************************************/


public class TappedInventoryViewBean extends BaseDataBean {

	private String hub;
	private String inventoryGroup;
	private BigDecimal itemId;
	private BigDecimal itemConversionIssueId;
	private BigDecimal receiptId;
	private BigDecimal quantityReceived;
	private BigDecimal quantityIssued;
	private String itemPkg;
	private String itemDesc;
	private String searchText;


	//constructor
	public TappedInventoryViewBean() {
	}

	//setters
	public void setHub(String hub) {
		this.hub = hub;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setItemConversionIssueId(BigDecimal itemConversionIssueId) {
		this.itemConversionIssueId = itemConversionIssueId;
	}
	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}
	public void setQuantityReceived(BigDecimal quantityReceived) {
		this.quantityReceived = quantityReceived;
	}
	public void setQuantityIssued(BigDecimal quantityIssued) {
		this.quantityIssued = quantityIssued;
	}
	public void setItemPkg(String itemPkg) {
		this.itemPkg = itemPkg;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}


	//getters
	public String getHub() {
		return hub;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public BigDecimal getItemConversionIssueId() {
		return itemConversionIssueId;
	}
	public BigDecimal getReceiptId() {
		return receiptId;
	}
	public BigDecimal getQuantityReceived() {
		return quantityReceived;
	}
	public BigDecimal getQuantityIssued() {
		return quantityIssued;
	}
	public String getItemPkg() {
		return itemPkg;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public String getSearchText() {
		return searchText;
	}
}