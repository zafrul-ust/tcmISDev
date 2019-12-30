package com.tcmis.ws.scanner.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: InventoryGroupDefinitionBean <br>
 * 
 * @version: 1.0, Jun 1, 2004 <br>
 *****************************************************************************/

public class CarrierAccount extends BaseDataBean {

	private String	accountCompanyId;
	private BigDecimal accountCustomerId;
	private String	carrierAccount;
	private String	carrierAccountId;
	private Date	lastUpdated;
	private String  notes;
	private String	status;

	// constructor
	public CarrierAccount() {
	}

	public String getAccountCompanyId() {
		return this.accountCompanyId;
	}

	public BigDecimal getAccountCustomerId() {
		return accountCustomerId;
	}

	public String getCarrierAccount() {
		return this.carrierAccount;
	}

	public String getCarrierAccountId() {
		return this.carrierAccountId;
	}
	
	public Date getLastUpdated() {
		return this.lastUpdated;
	}

	public String getNotes() {
		return this.notes;
	}

	public String getStatus() {
		return this.status;
	}

	public void setAccountCompanyId(String accountCompanyId) {
		this.accountCompanyId = accountCompanyId;
	}

	public void setAccountCustomerId(BigDecimal accountCustomerId) {
		this.accountCustomerId = accountCustomerId;
	}

	public void setCarrierAccount(String carrierAccount) {
		this.carrierAccount = carrierAccount;
	}
	
	public void setCarrierAccountId(String carrierAccountId) {
		this.carrierAccountId = carrierAccountId;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}