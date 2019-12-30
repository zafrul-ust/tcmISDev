package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: RepackageOrderViewBean <br>
 * @version: 1.0, Aug 30, 2007 <br>
 *****************************************************************************/


public class RepackageOrderViewBean extends BaseDataBean {

	private BigDecimal prNumber;
	private BigDecimal itemId;
	private BigDecimal orderQuantity;
	private BigDecimal originalItemId;
	private String hub;
	private String inventoryGroup;
	private BigDecimal receiptId;
	private String bin;
	private String mfgLot;
	private Date expireDate;
	private BigDecimal quantityAvailable;
	private BigDecimal repackageQuantityAvailable;
	private String itemDesc;
	private String itemPkg;
	private String mdItemPkg;
	private String maItemPkg;
	private String searchText;
	private String tapAvailable;
	private String closeable;


	//constructor
	public RepackageOrderViewBean() {
	}

	//setters
	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setOrderQuantity(BigDecimal orderQuantity) {
		this.orderQuantity = orderQuantity;
	}
	public void setOriginalItemId(BigDecimal originalItemId) {
		this.originalItemId = originalItemId;
	}
	public void setHub(String hub) {
		this.hub = hub;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}
	public void setBin(String bin) {
		this.bin = bin;
	}
	public void setMfgLot(String mfgLot) {
		this.mfgLot = mfgLot;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	public void setQuantityAvailable(BigDecimal quantityAvailable) {
		this.quantityAvailable = quantityAvailable;
	}
	public void setRepackageQuantityAvailable(BigDecimal repackageQuantityAvailable) {
		this.repackageQuantityAvailable = repackageQuantityAvailable;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public void setItemPkg(String itemPkg) {
		this.itemPkg = itemPkg;
	}
	public void setMdItemPkg(String mdItemPkg) {
		this.mdItemPkg = mdItemPkg;
	}
	public void setMaItemPkg(String maItemPkg) {
		this.maItemPkg = maItemPkg;
	}
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	public void setTapAvailable(String tapAvailable) {
		this.tapAvailable = tapAvailable;
	}
	public void setCloseable(String closeable) {
		this.closeable = closeable;
	}


	//getters
	public BigDecimal getPrNumber() {
		return prNumber;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public BigDecimal getOrderQuantity() {
		return orderQuantity;
	}
	public BigDecimal getOriginalItemId() {
		return originalItemId;
	}
	public String getHub() {
		return hub;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public BigDecimal getReceiptId() {
		return receiptId;
	}
	public String getBin() {
		return bin;
	}
	public String getMfgLot() {
		return mfgLot;
	}
	public Date getExpireDate() {
		return expireDate;
	}
	public BigDecimal getQuantityAvailable() {
		return quantityAvailable;
	}
	public BigDecimal getRepackageQuantityAvailable() {
		return repackageQuantityAvailable;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public String getItemPkg() {
		return itemPkg;
	}
	public String getMdItemPkg() {
		return mdItemPkg;
	}
	public String getMaItemPkg() {
		return maItemPkg;
	}
	public String getSearchText() {
		return searchText;
	}
	public String getTapAvailable() {
		return tapAvailable;
	}
	public String getCloseable() {
		return closeable;
	}
}