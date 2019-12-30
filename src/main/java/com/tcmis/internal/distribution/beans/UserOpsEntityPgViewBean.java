package com.tcmis.internal.distribution.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: UserOpsEntityPgViewBean <br>
 * @version: 1.0, Oct 31, 2009 <br>
 *****************************************************************************/


public class UserOpsEntityPgViewBean extends BaseDataBean {

	private BigDecimal personnelId;
	private String opsEntityId;
	private String opsCompanyId;
	private String operatingEntityName;
	private String priceGroupId;
	private String priceGroupName;
	private String companyId;


	//constructor
	public UserOpsEntityPgViewBean() {
	}

	//setters
	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
	}
	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}
	public void setOperatingEntityName(String operatingEntityName) {
		this.operatingEntityName = operatingEntityName;
	}
	public void setPriceGroupId(String priceGroupId) {
		this.priceGroupId = priceGroupId;
	}
	public void setPriceGroupName(String priceGroupName) {
		this.priceGroupName = priceGroupName;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}


	//getters
	public BigDecimal getPersonnelId() {
		return personnelId;
	}
	public String getOpsEntityId() {
		return opsEntityId;
	}
	public String getOperatingEntityName() {
		return operatingEntityName;
	}
	public String getPriceGroupId() {
		return priceGroupId;
	}
	public String getPriceGroupName() {
		return priceGroupName;
	}
	public String getCompanyId() {
		return companyId;
	}
	public String getOpsCompanyId() {
		return opsCompanyId;
	}
	public void setOpsCompanyId(String opsCompanyId) {
		this.opsCompanyId = opsCompanyId;
	}

}