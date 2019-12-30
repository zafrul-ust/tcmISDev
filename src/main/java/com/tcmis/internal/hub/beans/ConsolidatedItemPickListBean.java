package com.tcmis.internal.hub.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: PointOfSaleInventoryViewBean <br>
 * @version: 1.0, Jun 5, 2009 <br>
 *****************************************************************************/


public class ConsolidatedItemPickListBean extends BaseDataBean  {



	private String catPartNo;
	private BigDecimal partGroupNo;
	private BigDecimal itemId;
	private BigDecimal receiptId;
	private String bin;
	private String partDescription;
	private BigDecimal quantity;


	//constructor
	public ConsolidatedItemPickListBean() {
	}

	//setters

	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}
	public void setPartGroupNo(BigDecimal partGroupNo) {
		this.partGroupNo = partGroupNo;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}
	public void setBin(String bin) {
		this.bin = bin;
	}

	//getters

	public String getCatPartNo() {
		return catPartNo;
	}
	public BigDecimal getPartGroupNo() {
		return partGroupNo;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public BigDecimal getReceiptId() {
		return receiptId;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public String getBin() {
		return bin;
	}
	public String getPartDescription() {
		return partDescription;
	}
	
	


	
}