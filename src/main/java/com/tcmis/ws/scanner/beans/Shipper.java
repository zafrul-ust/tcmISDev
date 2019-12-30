package com.tcmis.ws.scanner.beans;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: InventoryGroupDefinitionBean <br>
 * 
 * @version: 1.0, Jun 1, 2004 <br>
 *****************************************************************************/

public class Shipper extends BaseDataBean {

	private String	carrierCompanyId;
	private String	carrierMethod;
	private String	carrierName;
	private Date	lastUpdated;
	private String	status;
	private String	transportationMode;
	private Collection<CarrierAccount> carrierAccounts;
	
	private String	accountCompanyId;
	private String	carrierAccount;
	private String	carrierAccountId;
	private Date	accountLastUpdated;
	private String	accountStatus;


	// constructor
	public Shipper() {
	}
	
	protected synchronized CarrierAccount getFirstAccount() {
		CarrierAccount firstAccount = null;
		if (carrierAccounts == null || carrierAccounts.isEmpty()) {
			setCarrierAccounts(new Vector<CarrierAccount>());
			firstAccount = new CarrierAccount();
			carrierAccounts.add(firstAccount);
		}
		else {
			firstAccount = ((Vector<CarrierAccount>) carrierAccounts).get(0);
		}
		return firstAccount;
	}

	public String getCarrierCompanyId() {
		return this.carrierCompanyId;
	}

	public String getCarrierMethod() {
		return this.carrierMethod;
	}

	public String getCarrierName() {
		return this.carrierName;
	}

	public Date getLastUpdated() {
		return this.lastUpdated;
	}

	public String getStatus() {
		return this.status;
	}

	public String getTransportationMode() {
		return this.transportationMode;
	}

	public void setCarrierCompanyId(String carrierCompanyId) {
		this.carrierCompanyId = carrierCompanyId;
	}

	public void setCarrierMethod(String carrierMethod) {
		this.carrierMethod = carrierMethod;
	}

	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setTransportationMode(String transportationMode) {
		this.transportationMode = transportationMode;
	}

	public Collection<CarrierAccount> getCarrierAccounts() {
		return this.carrierAccounts;
	}

	public void setCarrierAccounts(Collection<CarrierAccount> carrierAccounts) {
		this.carrierAccounts = carrierAccounts;
	}

	public void setAccountCompanyId(String accountCompanyId) {
		getFirstAccount().setAccountCompanyId(accountCompanyId);
	}

	public void setCarrierAccount(String carrierAccount) {
		getFirstAccount().setCarrierAccount(carrierAccount);
	}

	public void setCarrierAccountId(String carrierAccountId) {
		getFirstAccount().setCarrierAccountId(carrierAccountId);
	}
	
	public void setAccountCustomerId(BigDecimal customerId) {
		getFirstAccount().setAccountCustomerId(customerId);
	}

	public void setAccountLastUpdated(Date accountLastUpdated) {
		getFirstAccount().setLastUpdated(accountLastUpdated);
	}
	
	public void setAccountNotes(String accountNotes) {
		getFirstAccount().setNotes(accountNotes);
	}

	public void setAccountStatus(String accountStatus) {
		getFirstAccount().setStatus(accountStatus);
	}

	public boolean isSameCarrier(Shipper other) {
		return StringUtils.isNotBlank(carrierName) && carrierName.equals(other.getCarrierName());
	}
	
	public void addAccount(Shipper child) {
		if (carrierAccounts == null) {
			setCarrierAccounts(new Vector<CarrierAccount>());
		}
		carrierAccounts.add(child.getFirstAccount());
	}

}