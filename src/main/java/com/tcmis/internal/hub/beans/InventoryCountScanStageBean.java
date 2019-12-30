package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: InventoryCountScanStageBean <br>
 * @version: 1.0, Jul 26, 2006 <br>
 *****************************************************************************/


public class InventoryCountScanStageBean extends BaseDataBean {

	private BigDecimal uploadId;
	private BigDecimal receiptId;
	private Date scanDatetime;
	private BigDecimal counterId;
	private String scannedBin;
	private BigDecimal countedQuantity;
	private Date uploadDatetime;
	private String hub;


	//constructor
	public InventoryCountScanStageBean() {
	}

	//setters
	public void setUploadId(BigDecimal uploadId) {
		this.uploadId = uploadId;
	}
	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}
	public void setScanDatetime(Date scanDatetime) {
		this.scanDatetime = scanDatetime;
	}
	public void setCounterId(BigDecimal counterId) {
		this.counterId = counterId;
	}
	public void setScannedBin(String scannedBin) {
		this.scannedBin = scannedBin;
	}
	public void setCountedQuantity(BigDecimal countedQuantity) {
		this.countedQuantity = countedQuantity;
	}
	public void setUploadDatetime(Date uploadDatetime) {
		this.uploadDatetime = uploadDatetime;
	}
	public void setHub(String hub) {
		this.hub = hub;
	}


	//getters
	public BigDecimal getUploadId() {
		return uploadId;
	}
	public BigDecimal getReceiptId() {
		return receiptId;
	}
	public Date getScanDatetime() {
		return scanDatetime;
	}
	public BigDecimal getCounterId() {
		return counterId;
	}
	public String getScannedBin() {
		return scannedBin;
	}
	public BigDecimal getCountedQuantity() {
		return countedQuantity;
	}
	public Date getUploadDatetime() {
		return uploadDatetime;
	}
	public String getHub() {
		return hub;
	}
}