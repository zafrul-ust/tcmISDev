package com.tcmis.internal.distribution.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: CustomerEntPriceGrpViewBean <br>
 * @version: 1.0, Jul 12, 2010 <br>
 *****************************************************************************/


public class CustomerEntPriceGrpViewBean extends BaseDataBean {

	private BigDecimal customerId;
	private String billToCompanyId;
	private String opsEntityId;
	private String operatingEntityName;
	private String priceGroupId;
	private String priceGroupName;
	private String operatingCompanyId;

// from page
	private String changed;

	//constructor
	public CustomerEntPriceGrpViewBean() {
	}

	//setters
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	public void setBillToCompanyId(String billToCompanyId) {
		this.billToCompanyId = billToCompanyId;
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
	public void setOperatingCompanyId(String operatingCompanyId) {
		this.operatingCompanyId = operatingCompanyId;
	}


	//getters
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public String getBillToCompanyId() {
		return billToCompanyId;
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
	public String getOperatingCompanyId() {
		return operatingCompanyId;
	}
	public String getChanged() {
		return changed;
	}
	public void setChanged(String changed) {
		this.changed = changed;
	}

}