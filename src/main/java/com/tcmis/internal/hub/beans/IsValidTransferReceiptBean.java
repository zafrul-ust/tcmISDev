package com.tcmis.internal.hub.beans;


import com.tcmis.common.framework.BaseDataBean;


public class IsValidTransferReceiptBean extends BaseDataBean {

	private String validReceipt;
	private String inventoryGroup;
	private String mfgLot;

	//constructor
	public IsValidTransferReceiptBean() {
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public String getValidReceipt() {
		return validReceipt;
	}

	public void setValidReceipt(String validReceipt) {
		this.validReceipt = validReceipt;
	}

	public String getMfgLot() {
		return mfgLot;
	}

	public void setMfgLot(String mfgLot) {
		this.mfgLot = mfgLot;
	}


}