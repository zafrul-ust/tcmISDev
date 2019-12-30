package com.tcmis.internal.distribution.beans;

import com.tcmis.common.framework.BaseDataBean;
import java.math.BigDecimal;


/******************************************************************************
 * CLASSNAME: CustomerReturnRcptShpViewBean <br>
 * @version: 1.0, Apr 8, 2010 <br>
 *****************************************************************************/


public class CustomerReturnRcptShpViewBean extends BaseDataBean {
	
	private BigDecimal customerRmaId;
	private BigDecimal itemId;
	private BigDecimal receiptId;
	private BigDecimal quantity;
	private String itemDesc;


	//constructor
	public CustomerReturnRcptShpViewBean() {
	}


	public BigDecimal getCustomerRmaId() {
		return customerRmaId;
	}


	public void setCustomerRmaId(BigDecimal customerRmaId) {
		this.customerRmaId = customerRmaId;
	}


	public String getItemDesc() {
		return itemDesc;
	}


	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}


	public BigDecimal getItemId() {
		return itemId;
	}


	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}


	public BigDecimal getQuantity() {
		return quantity;
	}


	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}


	public BigDecimal getReceiptId() {
		return receiptId;
	}


	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}


}