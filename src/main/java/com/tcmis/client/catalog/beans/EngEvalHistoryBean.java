package com.tcmis.client.catalog.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: PartBean <br>
 * @version: 1.0, Jan 14, 2008 <br>
 *****************************************************************************/


public class EngEvalHistoryBean extends BaseDataBean {

	private String facilityName;
	private String applicationDisplay;
	private String userGroupId;
	private BigDecimal qty;
	private Date submitDate;
	private String requestor;
		
	//constructor
	public EngEvalHistoryBean() {
	}

	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public BigDecimal getQty() {
		return qty;
	}

	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}

	public String getRequestor() {
		return requestor;
	}

	public void setRequestor(String requestor) {
		this.requestor = requestor;
	}

	public Date getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

	public String getUserGroupId() {
		return userGroupId;
	}

	public void setUserGroupId(String userGroupId) {
		this.userGroupId = userGroupId;
	}

	public String getApplicationDisplay() {
		return applicationDisplay;
	}

	public void setApplicationDisplay(String applicationDisplay) {
		this.applicationDisplay = applicationDisplay;
	}
	

}