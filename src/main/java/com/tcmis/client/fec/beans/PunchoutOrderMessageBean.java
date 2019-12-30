package com.tcmis.client.fec.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: PunchoutOrderMessageBean <br>
 * @version: 1.0, Jul 11, 2005 <br>
 *****************************************************************************/


public class PunchoutOrderMessageBean extends BaseDataBean {

	private String payloadId;
	private BigDecimal prNumber;
	private String buyerCookie;
	private String browserFormPost;
	private String responsePayloadId;
	private String punchout;
	private String postedToOracle;


	//constructor
	public PunchoutOrderMessageBean() {
	}

	//setters
	public void setPayloadId(String payloadId) {
		this.payloadId = payloadId;
	}
	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}
	public void setBuyerCookie(String buyerCookie) {
		this.buyerCookie = buyerCookie;
	}
	public void setBrowserFormPost(String browserFormPost) {
		this.browserFormPost = browserFormPost;
	}
	public void setResponsePayloadId(String responsePayloadId) {
		this.responsePayloadId = responsePayloadId;
	}
	public void setPunchout(String punchout) {
		this.punchout = punchout;
	}
	public void setPostedToOracle(String postedToOracle) {
		this.postedToOracle = postedToOracle;
	}


	//getters
	public String getPayloadId() {
		return payloadId;
	}
	public BigDecimal getPrNumber() {
		return prNumber;
	}
	public String getBuyerCookie() {
		return buyerCookie;
	}
	public String getBrowserFormPost() {
		return browserFormPost;
	}
	public String getResponsePayloadId() {
		return responsePayloadId;
	}
	public String getPunchout() {
		return punchout;
	}
	public String getPostedToOracle() {
		return postedToOracle;
	}
}