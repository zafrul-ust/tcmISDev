package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: ScannedBinMissingReceiptBean <br>
 * @version: 1.0, Aug 3, 2005 <br>
 *****************************************************************************/


public class ScannedBinMissingReceiptBean extends BaseDataBean {

	private BigDecimal uploadId;
	private String hub;
	private String bin;
	private BigDecimal receiptId;
	private BigDecimal itemId;
	private String itemDesc;
	private BigDecimal currentExpectedQty;
	private String lotStatus;

	//constructor
	public ScannedBinMissingReceiptBean() {
	}

	//setters
	public void setUploadId(BigDecimal uploadId) {
		this.uploadId = uploadId;
	}
	public void setHub(String hub) {
		this.hub = hub;
	}
	public void setBin(String bin) {
		this.bin = bin;
	}
	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public void setCurrentExpectedQty(BigDecimal currentExpectedQty) {
		this.currentExpectedQty = currentExpectedQty;
	}
	public void setLotStatus(String lotStatus) {
	 this.lotStatus = lotStatus;
	}

	//getters
	public BigDecimal getUploadId() {
		return uploadId;
	}
	public String getHub() {
		return hub;
	}
	public String getBin() {
		return bin;
	}
	public BigDecimal getReceiptId() {
		return receiptId;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public BigDecimal getCurrentExpectedQty() {
		return currentExpectedQty;
	}
	public String getLotStatus() {
	 return lotStatus;
	}
}