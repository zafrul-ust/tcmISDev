package com.tcmis.client.fedex.beans;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: FedexGroundAccountDataBean <br>
 * @version: 1.0, May 20, 2010 <br>
 *****************************************************************************/


public class FedexGroundAccountDataBean extends BaseDataBean {

	private String opsEntityId;
	private String hub;
	private String carrierAccount;
	private String carrierSecurityKey;
	private String carrierMeterNumber;
	private String carrierPassword;
	private String groundCarrierAccount;


	//constructor
	public FedexGroundAccountDataBean() {
	}

	//setters
	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}
	public void setHub(String hub) {
		this.hub = hub;
	}
	public void setCarrierAccount(String carrierAccount) {
		this.carrierAccount = carrierAccount;
	}
	public void setCarrierSecurityKey(String carrierSecurityKey) {
		this.carrierSecurityKey = carrierSecurityKey;
	}
	public void setCarrierMeterNumber(String carrierMeterNumber) {
		this.carrierMeterNumber = carrierMeterNumber;
	}
	public void setCarrierPassword(String carrierPassword) {
		this.carrierPassword = carrierPassword;
	}
	public void setGroundCarrierAccount(String groundCarrierAccount) {
		this.groundCarrierAccount = groundCarrierAccount;
	}


	//getters
	public String getOpsEntityId() {
		return opsEntityId;
	}
	public String getHub() {
		return hub;
	}
	public String getCarrierAccount() {
		return carrierAccount;
	}
	public String getCarrierSecurityKey() {
		return carrierSecurityKey;
	}
	public String getCarrierMeterNumber() {
		return carrierMeterNumber;
	}
	public String getCarrierPassword() {
		return carrierPassword;
	}
	public String getGroundCarrierAccount() {
		return groundCarrierAccount;
	}
}