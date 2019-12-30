package com.tcmis.client.openCustomer.beans;

import java.math.BigDecimal;
import java.util.Locale;

import org.apache.struts.action.ActionForm;

import com.tcmis.common.framework.BaseInputBean;

/******************************************************************************
 * CLASSNAME: UserShiptoAdminViewBean <br>
 * @version: 1.0, Feb 8, 2011 <br>
 *****************************************************************************/

public class UserShiptoAdminBean extends BaseInputBean {

	private BigDecimal customerId;
	private boolean facilityAccess;
	private String facilityId;
	private String facilityName;
	private boolean invoiceGroupMember;
	private boolean oldFacilityAccess;
	private boolean oldInvoiceGroupMember;
	private BigDecimal personnelId;
	private String userAccess;
	private BigDecimal userId;

	//	constructor
	public UserShiptoAdminBean(){
	}

	public UserShiptoAdminBean(ActionForm inputForm, Locale locale, BigDecimal userId) {
		super(inputForm, locale);
		setUserId(userId);
	}

	public BigDecimal getCustomerId() {
		return customerId;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public BigDecimal getPersonnelId() {
		return personnelId;
	}

	public String getUserAccess() {
		return userAccess;
	}

	public BigDecimal getUserId() {
		return userId;
	}

	public boolean isFacilityAccess() {
		return facilityAccess;
	}

	public boolean isFacilityAccessChanged() {
		return facilityAccess != oldFacilityAccess;
	}

	public boolean isInvoiceAccessChanged() {
		return invoiceGroupMember != oldInvoiceGroupMember;
	}

	public boolean isInit() {
		return "init".equals(getuAction());
	}

	public boolean isInvoiceGroupMember() {
		return invoiceGroupMember;
	}

	public boolean isOldFacilityAccess() {
		return oldFacilityAccess;
	}

	public boolean isOldInvoiceGroupMember() {
		return oldInvoiceGroupMember;
	}

	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	public void setFacilityAccess(boolean facilityAccess) {
		this.facilityAccess = facilityAccess;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	@Override
	public void setHiddenFormFields() {
		// TODO Auto-generated method stub

	}

	public void setInvoiceGroupMember(boolean invoiceAccess) {
		this.invoiceGroupMember = invoiceAccess;
	}

	public void setOldFacilityAccess(boolean oldFacilityAccess) {
		this.oldFacilityAccess = oldFacilityAccess;
	}

	public void setOldInvoiceGroupMember(boolean oldInvoiceAccess) {
		this.oldInvoiceGroupMember = oldInvoiceAccess;
	}

	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
	}

	public void setUserAccess(String userAccess) {
		this.userAccess = userAccess;
	}

	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}

}