package com.tcmis.ws.tablet.beans;

import java.math.BigDecimal;

/******************************************************************************
 * CLASSNAME: InboundShipmentDetailBean <br>
 * 
 * @version: 1.0, Mar 5, 2013 <br>
 *****************************************************************************/

public class VerifyExistsBean extends BaseTabletBean {
	private String		inventoryGroup;
	private String		inventoryGroupName;
	private boolean		permissionToReceive	= true;
	private BigDecimal	transactionId;

	// constructor
	public VerifyExistsBean() {
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public String getInventoryGroupName() {
		return inventoryGroupName;
	}

	public BigDecimal getTransactionId() {
		return transactionId;
	}

	public boolean isPermissionToReceive() {
		return permissionToReceive;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public void setInventoryGroupName(String inventoryGroupName) {
		this.inventoryGroupName = inventoryGroupName;
	}

	public void setPermissionToReceive(boolean permissionToReceive) {
		this.permissionToReceive = permissionToReceive;
	}

	public void setTransactionId(BigDecimal transactionId) {
		this.transactionId = transactionId;
	}

}