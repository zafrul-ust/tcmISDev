package com.tcmis.client.seagate.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: CxmlOrderRequestBean <br>
 * @version: 1.0, Jul 11, 2006 <br>
 *****************************************************************************/


public class CxmlOrderRequestBean extends BaseDataBean {

	private String payloadId;
	private String orderId;
	private String orderDate;
	private String orderType;
	private String requisitionId;
	private String shipComplete;
	private String currency;
	private String totalAmount;


	//constructor
	public CxmlOrderRequestBean() {
	}

	//setters
	public void setPayloadId(String payloadId) {
		this.payloadId = payloadId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public void setRequisitionId(String requisitionId) {
		this.requisitionId = requisitionId;
	}
	public void setShipComplete(String shipComplete) {
		this.shipComplete = shipComplete;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}


	//getters
	public String getPayloadId() {
		return payloadId;
	}
	public String getOrderId() {
		return orderId;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public String getOrderType() {
		return orderType;
	}
	public String getRequisitionId() {
		return requisitionId;
	}
	public String getShipComplete() {
		return shipComplete;
	}
	public String getCurrency() {
		return currency;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
}