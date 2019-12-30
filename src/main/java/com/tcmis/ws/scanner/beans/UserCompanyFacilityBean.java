package com.tcmis.ws.scanner.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

public class UserCompanyFacilityBean extends BaseDataBean {
	private String		companyId;
	private Date		credentialExpiration;
	private String		credentials;
	private String		facilityId;
	private String		firstName;
	private String		hub;
	private String		inventoryGroup;
	private String		lastName;
	private String		logonId;
	private String		permission;
	private BigDecimal	personnelId;

	public String getCompanyId() {
		return companyId;
	}

	public Date getCredentialExpiration() {
		return this.credentialExpiration;
	}

	public String getCredentials() {
		return this.credentials;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getHub() {
		return this.hub;
	}

	public String getInventoryGroup() {
		return this.inventoryGroup;
	}

	public String getLastName() {
		return lastName;
	}

	public String getLogonId() {
		return logonId;
	}

	public String getPermission() {
		return this.permission;
	}

	public BigDecimal getPersonnelId() {
		return personnelId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setCredentialExpiration(Date credentialExpiration) {
		this.credentialExpiration = credentialExpiration;
	}

	public void setCredentials(String credentials) {
		this.credentials = credentials;
	}

	public void setFacilityId(String facility) {
		this.facilityId = facility;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setHub(String hub) {
		this.hub = hub;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setLogonId(String logonId) {
		this.logonId = logonId;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
	}
}
