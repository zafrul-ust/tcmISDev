package com.tcmis.client.seagate.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: CxmlDocumentDataBean <br>
 * @version: 1.0, Jul 10, 2006 <br>
 *****************************************************************************/


public class CxmlDocumentDataBean extends BaseDataBean {

	private String payloadId;
	private String timestamp;
	private String type;
	private String fromDomain;
	private String fromIdentity;
	private String toDomain;
	private String toIdentity;
	private String senderDomain;
	private String senderIdentity;
	private String userAgent;
	private String responsePayloadId;
	private String responseCode;


	//constructor
	public CxmlDocumentDataBean() {
	}

	//setters
	public void setPayloadId(String payloadId) {
		this.payloadId = payloadId;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setFromDomain(String fromDomain) {
		this.fromDomain = fromDomain;
	}
	public void setFromIdentity(String fromIdentity) {
		this.fromIdentity = fromIdentity;
	}
	public void setToDomain(String toDomain) {
		this.toDomain = toDomain;
	}
	public void setToIdentity(String toIdentity) {
		this.toIdentity = toIdentity;
	}
	public void setSenderDomain(String senderDomain) {
		this.senderDomain = senderDomain;
	}
	public void setSenderIdentity(String senderIdentity) {
		this.senderIdentity = senderIdentity;
	}
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	public void setResponsePayloadId(String responsePayloadId) {
		this.responsePayloadId = responsePayloadId;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}


	//getters
	public String getPayloadId() {
		return payloadId;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public String getType() {
		return type;
	}
	public String getFromDomain() {
		return fromDomain;
	}
	public String getFromIdentity() {
		return fromIdentity;
	}
	public String getToDomain() {
		return toDomain;
	}
	public String getToIdentity() {
		return toIdentity;
	}
	public String getSenderDomain() {
		return senderDomain;
	}
	public String getSenderIdentity() {
		return senderIdentity;
	}
	public String getUserAgent() {
		return userAgent;
	}
	public String getResponsePayloadId() {
		return responsePayloadId;
	}
	public String getResponseCode() {
		return responseCode;
	}
}