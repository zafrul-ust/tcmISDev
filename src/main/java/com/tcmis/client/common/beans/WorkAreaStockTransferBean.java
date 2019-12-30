package com.tcmis.client.common.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: WorkAreaStockTransferBean <br> 
 *****************************************************************************/

public class WorkAreaStockTransferBean extends BaseDataBean {
	private String catPartNo;
	private int quantity;
	private BigDecimal receiptId;
	private BigDecimal itemId;
	private String itemDesc;
	private BigDecimal fromBinId;
	private BigDecimal toBinId;
	private int transferQuantity;

	// constructor
	public WorkAreaStockTransferBean() {
	}

	public String getCatPartNo() {
		return catPartNo;
	}

	public int getQuantity() {
		return quantity;
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

	public BigDecimal getFromBinId() {
		return fromBinId;
	}

	public BigDecimal getToBinId() {
		return toBinId;
	}

	public int getTransferQuantity() {
		return transferQuantity;
	}

	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
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

	public void setFromBinId(BigDecimal fromBinId) {
		this.fromBinId = fromBinId;
	}

	public void setToBinId(BigDecimal toBinId) {
		this.toBinId = toBinId;
	}

	public void setTransferQuantity(int transferQuantity) {
		this.transferQuantity = transferQuantity;
	}


}
