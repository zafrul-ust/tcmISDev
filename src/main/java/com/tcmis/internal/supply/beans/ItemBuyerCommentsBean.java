package com.tcmis.internal.supply.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: ItemBuyerCommentsBean <br>
 * @version: 1.0, Oct 24, 2006 <br>
 *****************************************************************************/


public class ItemBuyerCommentsBean extends BaseDataBean {

	private BigDecimal buyerId;
	private BigDecimal itemId;
	private String comments;
	private Date transactionDate;
	private BigDecimal recordNo;
	private Date lastUpdated;


	//constructor
	public ItemBuyerCommentsBean() {
	}

	//setters
	public void setBuyerId(BigDecimal buyerId) {
		this.buyerId = buyerId;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public void setRecordNo(BigDecimal recordNo) {
		this.recordNo = recordNo;
	}
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}


	//getters
	public BigDecimal getBuyerId() {
		return buyerId;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public String getComments() {
		return comments;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public BigDecimal getRecordNo() {
		return recordNo;
	}
	public Date getLastUpdated() {
		return lastUpdated;
	}
}