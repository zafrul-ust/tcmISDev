package com.tcmis.internal.hub.beans;

import java.util.*;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.SQLException;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: CarrierObjBean <br>
 * @version: 1.0, Jan 25, 2008 <br>
 *****************************************************************************/


public class SplitReceiptBean extends BaseDataBean {
	private String quantity; // quantity on hand.
	private BigDecimal receiptId;
	private String bin;
	private String lotStatus;
	private String inventoryGroup;
	private BigDecimal splitQuantity;
	private String netPendingAdj;
	private String hub;
//	private Date qcDate;
	private String qualityControlItem;
	private String movePendingAdjustment;
	private BigDecimal itemId;
	//constructor
	public SplitReceiptBean() {
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getReceiptId() {
		return receiptId;
	}
	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}
	public String getBin() {
		return bin;
	}
	public void setBin(String bin) {
		this.bin = bin;
	}
	public String getLotStatus() {
		return lotStatus;
	}
	public void setLotStatus(String lotStatus) {
		this.lotStatus = lotStatus;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public BigDecimal getSplitQuantity() {
		return splitQuantity;
	}
	public void setSplitQuantity(BigDecimal splitQuantity) {
		this.splitQuantity = splitQuantity;
	}
	public String getNetPendingAdj() {
		return netPendingAdj;
	}
	public void setNetPendingAdj(String netPendingAdj) {
		this.netPendingAdj = netPendingAdj;
	}
	public String getHub() {
		return hub;
	}
	public void setHub(String hub) {
		this.hub = hub;
	}
	public String getQualityControlItem() {
		return qualityControlItem;
	}
	public void setQualityControlItem(String qualityControlItem) {
		this.qualityControlItem = qualityControlItem;
	}
	public String getMovePendingAdjustment() {
		return movePendingAdjustment;
	}
	public void setMovePendingAdjustment(String movePendingAdjustment) {
		this.movePendingAdjustment = movePendingAdjustment;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	
}