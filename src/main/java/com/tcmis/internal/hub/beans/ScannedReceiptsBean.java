package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: ScannedReceiptReportViewBean <br>
 * @version: 1.0, Aug 1, 2005 <br>
 *****************************************************************************/


public class ScannedReceiptsBean extends BaseDataBean {

	/*private BigDecimal uploadId;
	private BigDecimal receiptId;
	private Date scanDatetime;
	private BigDecimal counterId;*/
	private String scannedBin;
	private BigDecimal countedQuantity;
	/*private Date uploadDatetime;
	private String expectedBin;*/
	//private BigDecimal totalQtyCountedForReceipt;
	//private BigDecimal expectedQtyForReceipt;
	/*private Collection scannedReceiptsCollection;
    private BigDecimal rowSpan;*/

	//constructor
	public ScannedReceiptsBean() {
	}

	//setters
	/*public void setUploadId(BigDecimal uploadId) {
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
	}*/
	public void setScannedBin(String scannedBin) {
		this.scannedBin = scannedBin;
	}
	public void setCountedQuantity(BigDecimal countedQuantity) {
		this.countedQuantity = countedQuantity;
	}
	/*public void setUploadDatetime(Date uploadDatetime) {
		this.uploadDatetime = uploadDatetime;
	}
	public void setExpectedBin(String expectedBin) {
		this.expectedBin = expectedBin;
	}
	public void setTotalQtyCountedForReceipt(BigDecimal totalQtyCountedForReceipt) {
		this.totalQtyCountedForReceipt = totalQtyCountedForReceipt;
	}
	public void setExpectedQtyForReceipt(BigDecimal expectedQtyForReceipt) {
		this.expectedQtyForReceipt = expectedQtyForReceipt;
	}
	public void setScannedReceiptsCollection(Collection
											 scannedReceiptsCollection) {
	  this.scannedReceiptsCollection =
		  scannedReceiptsCollection;
	}
	public void setRowSpan(BigDecimal rowSpan) {
	  this.rowSpan = rowSpan;
	}*/

	//getters
	/*public BigDecimal getUploadId() {
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
	}*/
	public String getScannedBin() {
		return scannedBin;
	}
	public BigDecimal getCountedQuantity() {
		return countedQuantity;
	}
	/*public Date getUploadDatetime() {
		return uploadDatetime;
	}
	public String getExpectedBin() {
		return expectedBin;
	}
	public BigDecimal getTotalQtyCountedForReceipt() {
		return totalQtyCountedForReceipt;
	}
	public BigDecimal getExpectedQtyForReceipt() {
		return expectedQtyForReceipt;
	}
	public Collection getScannedReceiptsCollection() {
	  return scannedReceiptsCollection;
	}
	public BigDecimal getRowSpan() {
	  return rowSpan;
	}*/
}