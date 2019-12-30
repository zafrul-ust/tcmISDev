package com.tcmis.client.cxml.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: PunchoutSessionBean <br>
 * @version: 1.0, Jul 10, 2006 <br>
 *****************************************************************************/


public class PunchoutSessionBean extends BaseDataBean {

	private String sessionId;
	private String payloadId;
	private String userId;
	private String logged;
	private BigDecimal mrNumber;
	private String oracle;


	//constructor
	public PunchoutSessionBean() {
	}

	//setters
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public void setPayloadId(String payloadId) {
		this.payloadId = payloadId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setLogged(String logged) {
		this.logged = logged;
	}
	public void setMrNumber(BigDecimal mrNumber) {
		this.mrNumber = mrNumber;
	}
	public void setOracle(String oracle) {
		this.oracle = oracle;
	}


	//getters
	public String getSessionId() {
		return sessionId;
	}
	public String getPayloadId() {
		return payloadId;
	}
	public String getUserId() {
		return userId;
	}
	public String getLogged() {
		return logged;
	}
	public BigDecimal getMrNumber() {
		return mrNumber;
	}
	public String getOracle() {
		return oracle;
	}
}