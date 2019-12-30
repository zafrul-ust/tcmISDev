package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: ListItemDetailView Bean <br>
 * @version: 1.0, Jan 11, 2008 <br>
 *****************************************************************************/


public class ListItemDetailViewBean extends BaseDataBean {

	private BigDecimal listItemId;
	private String inventoryGroup;
	private BigDecimal itemId;
	private String itemDesc;
	private String itemType;
	private String packaging;
  private String receiptList;

	//constructor
	public ListItemDetailViewBean() {
	}

	//setters
	public void setListItemId(BigDecimal listItemId) {
		this.listItemId = listItemId;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}
  public void setReceiptList(String receiptList) {
    this.receiptList = receiptList;
  }


	//getters
	public BigDecimal getListItemId() {
		return listItemId;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public String getItemType() {
		return itemType;
	}
	public String getPackaging() {
		return packaging;
	}
  public String getReceiptList() {
    return receiptList;
  }
}