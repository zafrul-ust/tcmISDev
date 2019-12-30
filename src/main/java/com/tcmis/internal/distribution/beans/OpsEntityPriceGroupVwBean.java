package com.tcmis.internal.distribution.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: OpsEntityPriceGroupVwBean <br>
 * @version: 1.0, Sep 8, 2009 <br>
 *****************************************************************************/


public class OpsEntityPriceGroupVwBean extends BaseDataBean {

	private String opsEntityId;
	private String operatingEntityName;
	private String opsCompanyId;
	private String priceGroupId;
	private String priceGroupName;


	//constructor
	public OpsEntityPriceGroupVwBean() {
	}

	//setters
	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}
	public void setOperatingEntityName(String operatingEntityName) {
		this.operatingEntityName = operatingEntityName;
	}
	public void setOpsCompanyId(String opsCompanyId) {
		this.opsCompanyId = opsCompanyId;
	}
	public void setPriceGroupId(String priceGroupId) {
		this.priceGroupId = priceGroupId;
	}
	public void setPriceGroupName(String priceGroupName) {
		this.priceGroupName = priceGroupName;
	}


	//getters
	public String getOpsEntityId() {
		return opsEntityId;
	}
	public String getOperatingEntityName() {
		return operatingEntityName;
	}
	public String getOpsCompanyId() {
		return opsCompanyId;
	}
	public String getPriceGroupId() {
		return priceGroupId;
	}
	public String getPriceGroupName() {
		return priceGroupName;
	}

}