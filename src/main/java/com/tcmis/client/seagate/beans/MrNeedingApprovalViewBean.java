package com.tcmis.client.seagate.beans;


import java.math.*;
import java.util.Date;

import org.apache.struts.action.ActionForm;
import java.util.Locale;
import com.tcmis.common.framework.HubBaseInputBean;


/******************************************************************************
 * CLASSNAME: MrNeedingApprovalViewBean <br>
 * @version: 1.0, Feb 6, 2014 <br>
 *****************************************************************************/


public class MrNeedingApprovalViewBean extends HubBaseInputBean {

	private String companyId;
	private BigDecimal prNumber;
	private String lineItem;
	private String requestorName;
	private Date releaseDate;
	private String application;
	private String applicationDesc;
	private String facPartNo;
	private String partDescription;
	private BigDecimal quantity;
	private BigDecimal extendedPrice;
	private String accountSysId;
	private String okDoUpdate;
	private Date confirmFromDate;
	private Date confirmToDate;
	private String cabinetReplenishment;
	private String facilityId;
	private String facilityName;

	public MrNeedingApprovalViewBean(ActionForm inputForm) {
		super(inputForm);
	}

	public MrNeedingApprovalViewBean(ActionForm inputForm, Locale locale) {
		super(inputForm, locale);
	}

	//constructor
	public MrNeedingApprovalViewBean() {
	}

	//setters
	public void setOkDoUpdate(String okDoUpdate) {
		this.okDoUpdate = okDoUpdate;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}
	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}
	public void setRequestorName(String requestorName) {
		this.requestorName = requestorName;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public void setApplicationDesc(String applicationDesc) {
		this.applicationDesc = applicationDesc;
	}
	public void setFacPartNo(String facPartNo) {
		this.facPartNo = facPartNo;
	}
	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public void setExtendedPrice(BigDecimal extendedPrice) {
		this.extendedPrice = extendedPrice;
	}
	public void setAccountSysId(String accountSysId) {
		this.accountSysId = accountSysId;
	}
	public boolean isOkDoUpdate () {
		return "true".equals(okDoUpdate);
	}

	//getters	
	public String getOkDoUpdate() {
		return okDoUpdate;
	}
	
	public String getCompanyId() {
		return companyId;
	}
	public BigDecimal getPrNumber() {
		return prNumber;
	}
	public String getLineItem() {
		return lineItem;
	}
	public String getRequestorName() {
		return requestorName;
	}
	public Date getReleaseDate() {
		return releaseDate;
	}
	public String getApplication() {
		return application;
	}
	public String getApplicationDesc() {
		return applicationDesc;
	}
	public String getFacPartNo() {
		return facPartNo;
	}
	public String getPartDescription() {
		return partDescription;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public BigDecimal getExtendedPrice() {
		return extendedPrice;
	}
	public String getAccountSysId() {
		return accountSysId;
	}

	@Override
	public void setHiddenFormFields() {
		// TODO Auto-generated method stub
		
	}

	public void setConfirmFromDate(Date confirmFromDate) {
		this.confirmFromDate = confirmFromDate;
	}

	public Date getConfirmFromDate() {
		return confirmFromDate;
	}
	
	public boolean hasConfirmFromDate () {
		return confirmFromDate == null?false:true;
	}

	public void setConfirmToDate(Date confirmToDate) {
		this.confirmToDate = confirmToDate;
	}

	public Date getConfirmToDate() {
		return confirmToDate;
	}
	
	public boolean hasConfirmToDate () {
		return confirmToDate == null?false:true;
	}

	public String getCabinetReplenishment() {
		return cabinetReplenishment;
	}

	public void setCabinetReplenishment(String cabinetReplenishment) {
		this.cabinetReplenishment = cabinetReplenishment;
	}

	public boolean hasFacility () {
		return facilityId != null && facilityId.length() > 0 ? true : false;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public boolean hasApplication () {
		return application != null && application.length() > 0 ? true : false;
	}
	
	public boolean hasCompanyId () {
		return companyId != null && companyId.length() > 0 ? true : false;
	}
	
	public boolean hasCabinetReplenishment () {
		return "Y".equals(cabinetReplenishment);
	}
}