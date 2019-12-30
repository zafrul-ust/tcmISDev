package com.tcmis.client.kilfrost.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: KilfrostDcSentBean <br>
 * @version: 1.0, Oct 7, 2008 <br>
 *****************************************************************************/


public class KilfrostDcSentBean extends BaseDataBean {

	private String poNumber;
	private BigDecimal issueId;
	private BigDecimal quantity;
	private Date dateSent;
	private Date dateShipped;
	private String orderStatus;


	//constructor
	public KilfrostDcSentBean() {
	}

	//setters
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	public void setIssueId(BigDecimal issueId) {
		this.issueId = issueId;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public void setDateSent(Date dateSent) {
		this.dateSent = dateSent;
	}
	public void setDateShipped(Date dateShipped) {
		this.dateShipped = dateShipped;
	}


	//getters
	public String getPoNumber() {
		return poNumber;
	}
	public BigDecimal getIssueId() {
		return issueId;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public Date getDateSent() {
		return dateSent;
	}
	public Date getDateShipped() {
		return dateShipped;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
}