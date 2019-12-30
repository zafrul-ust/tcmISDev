package com.tcmis.internal.report.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: ApprovalCodeExpiringBean <br>
 * @version: 1.0, Jan 15, 2008 <br>
 *****************************************************************************/


public class DisbursedNotificationBean extends BaseDataBean {
	private String companyId;
	private String facilityName;
	private String applicationDesc;
	private String catPartNo;
	private String binName;
	private BigDecimal countQuantity;
	private Date disbursedDatetime;
	private String disbursedBy;
	private String emailAddress;
	private String webApplicationPath;
	private String disbursedUnitOfMeasure;

    //constructor
	public DisbursedNotificationBean() {
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public String getApplicationDesc() {
		return applicationDesc;
	}

	public void setApplicationDesc(String applicationDesc) {
		this.applicationDesc = applicationDesc;
	}

	public String getCatPartNo() {
		return catPartNo;
	}

	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}

	public String getBinName() {
		return binName;
	}

	public void setBinName(String binName) {
		this.binName = binName;
	}

	public BigDecimal getCountQuantity() {
		return countQuantity;
	}

	public void setCountQuantity(BigDecimal countQuantity) {
		this.countQuantity = countQuantity;
	}

	public Date getDisbursedDatetime() {
		return disbursedDatetime;
	}

	public void setDisbursedDatetime(Date disbursedDatetime) {
		this.disbursedDatetime = disbursedDatetime;
	}

	public String getDisbursedBy() {
		return disbursedBy;
	}

	public void setDisbursedBy(String disbursedBy) {
		this.disbursedBy = disbursedBy;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getWebApplicationPath() {
		return webApplicationPath;
	}

	public void setWebApplicationPath(String webApplicationPath) {
		this.webApplicationPath = webApplicationPath;
	}

	public String getDisbursedUnitOfMeasure() {
		return disbursedUnitOfMeasure;
	}

	public void setDisbursedUnitOfMeasure(String disbursedUnitOfMeasure) {
		this.disbursedUnitOfMeasure = disbursedUnitOfMeasure;
	}
} //end of class