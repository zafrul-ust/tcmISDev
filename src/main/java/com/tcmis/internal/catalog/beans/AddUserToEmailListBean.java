package com.tcmis.internal.catalog.beans;

import java.math.BigDecimal;
import java.util.Locale;

import org.apache.struts.action.ActionForm;

import com.tcmis.common.framework.BaseInputBean;
import com.tcmis.common.util.BeanHandler;

public class AddUserToEmailListBean extends BaseInputBean {
	private String admin;
	private String catalogCompanyId;
	private String catalogDesc;
	private String catalogId;
	private String companyId;
	private String companyName;
	private String email;
	private String facilityId;
	private String levelOfControl;
	private String mfrNotificationLevel;
	private String name;
	private BigDecimal personnelId;
	private String selectedPersonnelId;
	private String userGroupId;

	public AddUserToEmailListBean() {
	}

	public AddUserToEmailListBean(ActionForm form, Locale locale) throws Exception {
		try {
			BeanHandler.copyAttributes(form, this, locale);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public String getCompanyId() {
		return companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	public BigDecimal getPersonnelId() {
		return personnelId;
	}

	public String getSelectedPersonnelId() {
		return selectedPersonnelId;
	}

	public String getUserGroupId() {
		return userGroupId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public void setHiddenFormFields() {
		// TODO Auto-generated method stub
		addHiddenFormField("companyId");
		addHiddenFormField("userGroupId");
		addHiddenFormField("catalogId");
		addHiddenFormField("catalogCompanyId");
		addHiddenFormField("mfrNotificationLevel");
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
	}

	public void setSelectedPersonnelId(String selectedPersonnelId) {
		this.selectedPersonnelId = selectedPersonnelId;
	}

	public void setUserGroupId(String userGroupId) {
		this.userGroupId = userGroupId;
	}

	public String getLevelOfControl() {
		return levelOfControl;
	}

	public void setLevelOfControl(String levelOfControl) {
		this.levelOfControl = levelOfControl;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public String getCatalogDesc() {
		return catalogDesc;
	}

	public void setCatalogDesc(String catalogDesc) {
		this.catalogDesc = catalogDesc;
	}

	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}

	public void setCatalogCompanyId(String catalogCompanyId) {
		this.catalogCompanyId = catalogCompanyId;
	}

	public String getMfrNotificationLevel() {
		return mfrNotificationLevel;
	}

	public void setMfrNotificationLevel(String mfrNotificationLevel) {
		this.mfrNotificationLevel = mfrNotificationLevel;
	}

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
}
