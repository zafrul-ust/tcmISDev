package com.tcmis.internal.catalog.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

@SuppressWarnings("serial")
public class MfrAffectedNotification extends BaseDataBean {

	private final String DIVISION = "DIVISION";
	
	private BigDecimal mfgId;
	private String mfgDesc;
	private String mfgUrl;
	private String phone;
	private String contact;
	private String email;
	private String cageCode;
	private boolean equipmentChange;
	private String notes;
	private BigDecimal acquiredMfrId;
	private String acquiredMfrDesc;
	private String acquisitionType;
	private String comments;
	public String getMfgDesc() {
		return mfgDesc;
	}
	public void setMfgDesc(String mfgDesc) {
		this.mfgDesc = mfgDesc;
	}
	public String getMfgUrl() {
		return mfgUrl;
	}
	public void setMfgUrl(String mfgUrl) {
		this.mfgUrl = mfgUrl;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCageCode() {
		return cageCode;
	}
	public void setCageCode(String cageCode) {
		this.cageCode = cageCode;
	}
	public boolean isEquipmentChange() {
		return equipmentChange;
	}
	public void setEquipmentChange(boolean equipmentChange) {
		this.equipmentChange = equipmentChange;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public BigDecimal getMfgId() {
		return mfgId;
	}
	public void setMfgId(BigDecimal mfgId) {
		this.mfgId = mfgId;
	}
	public BigDecimal getAcquiredMfrId() {
		return acquiredMfrId;
	}
	public void setAcquiredMfrId(BigDecimal acquiredMfrId) {
		this.acquiredMfrId = acquiredMfrId;
	}
	public String getAcquiredMfrDesc() {
		return acquiredMfrDesc;
	}
	public void setAcquiredMfrDesc(String acquiredMfrDesc) {
		this.acquiredMfrDesc = acquiredMfrDesc;
	}
	public String getAcquisitionType() {
		return acquisitionType;
	}
	public void setAcquisitionType(String acquisitionType) {
		this.acquisitionType = acquisitionType;
	}
	public boolean isDivisionAcquisition() {
		return DIVISION.equals(acquisitionType);
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
}
