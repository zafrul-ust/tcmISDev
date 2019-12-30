package com.tcmis.client.common.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

public class DeliveryScheduleChangeHeaderViewBean extends BaseDataBean {

	private BigDecimal prNumber;
	private String lineItem;
	private BigDecimal itemId;
	private String application;
	private String facPartNo;
	private BigDecimal quantity;
	private BigDecimal requestor;
	private String facilityId;
	private String itemDesc;
	private String packaging;
	private String facilityName;
	private String applicationDisplay;
	private String fullName;
	private String phone;
	private String email;
	
	public BigDecimal getPrNumber() {
		return prNumber;
	}
	
	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}

	public String getLineItem() {
		return lineItem;
	}

	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getFacPartNo() {
		return facPartNo;
	}

	public void setFacPartNo(String facPartNo) {
		this.facPartNo = facPartNo;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getRequestor() {
		return requestor;
	}

	public void setRequestor(BigDecimal requestor) {
		this.requestor = requestor;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public String getPackaging() {
		return packaging;
	}

	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public String getApplicationDisplay() {
		return applicationDisplay;
	}

	public void setApplicationDisplay(String applicationDisplay) {
		this.applicationDisplay = applicationDisplay;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
