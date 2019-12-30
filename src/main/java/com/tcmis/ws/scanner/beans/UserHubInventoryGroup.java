package com.tcmis.ws.scanner.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

public class UserHubInventoryGroup extends BaseDataBean {
	private Date		credentialExpiration;
	private String		credentials;
	private String		firstName;
	private String		hub;
	private String		inventoryGroup;
	private String		lastName;
	private String		logonId;
	private BigDecimal	personnelId;


	public Date getCredentialExpiration() {
		return this.credentialExpiration;
	}

	public String getCredentials() {
		return this.credentials;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getHub() {
		return hub;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public String getLastName() {
		return lastName;
	}

	public String getLogonId() {
		return logonId;
	}

	public BigDecimal getPersonnelId() {
		return personnelId;
	}

	public void setCredentialExpiration(Date credentialExpiration) {
		this.credentialExpiration = credentialExpiration;
	}

	public void setCredentials(String credentials) {
		this.credentials = credentials;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setHub(String facility) {
		this.hub = facility;
	}

	public void setInventoryGroup(String companyId) {
		this.inventoryGroup = companyId;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setLogonId(String logonId) {
		this.logonId = logonId;
	}

	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
	}
}
