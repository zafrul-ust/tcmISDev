package com.tcmis.internal.hub.beans;

import com.tcmis.common.framework.BaseDataBean;

public class SpecMisMatchDetailInputBean extends BaseDataBean {
	
	private String issueSeverity;
	private String dropShip;
	private String customerName;
	private String uAction;
	
	//constructor
	public SpecMisMatchDetailInputBean() {
	}
	
	
	
	public String getIssueSeverity() {
		return issueSeverity;
	}
	public void setIssueSeverity(String issueSeverity) {
		this.issueSeverity = issueSeverity;
	}
	public String getDropShip() {
		return dropShip;
	}
	public void setDropShip(String dropShip) {
		this.dropShip = dropShip;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getuAction() {
		return uAction;
	}
	public void setuAction(String uAction) {
		this.uAction = uAction;
	}
	
	
	
}

