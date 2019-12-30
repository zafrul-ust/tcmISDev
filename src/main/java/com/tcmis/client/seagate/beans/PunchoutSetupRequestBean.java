package com.tcmis.client.seagate.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: PunchoutSetupRequestBean <br>
 * @version: 1.0, Jul 10, 2006 <br>
 *****************************************************************************/


public class PunchoutSetupRequestBean extends BaseDataBean {

	private String payloadId;
	private String operation;
	private String buyerCookie;
	private String browserFormPost;


	//constructor
	public PunchoutSetupRequestBean() {
	}

	//setters
	public void setPayloadId(String payloadId) {
		this.payloadId = payloadId;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public void setBuyerCookie(String buyerCookie) {
		this.buyerCookie = buyerCookie;
	}
	public void setBrowserFormPost(String browserFormPost) {
		this.browserFormPost = browserFormPost;
	}


	//getters
	public String getPayloadId() {
		return payloadId;
	}
	public String getOperation() {
		return operation;
	}
	public String getBuyerCookie() {
		return buyerCookie;
	}
	public String getBrowserFormPost() {
		return browserFormPost;
	}
}