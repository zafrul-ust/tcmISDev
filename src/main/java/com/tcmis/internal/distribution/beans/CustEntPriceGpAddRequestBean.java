package com.tcmis.internal.distribution.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: CustEntPriceGpAddRequestBean <br>
 * @version: 1.0, Jul 12, 2010 <br>
 *****************************************************************************/


public class CustEntPriceGpAddRequestBean extends BaseDataBean {

	private BigDecimal customerRequestId;
	private BigDecimal customerId;
	private String billToCompanyId;
	private String opsEntityId;
	private String operatingCompanyId;
	private String priceGroupId;
// from page
	private String changed;
	private String billopsEntityId;
	private String billpriceGroupId;
	public String getChanged() {
		return changed;
	}
	public void setChanged(String changed) {
		this.changed = changed;
	}
	public String getBillopsEntityId() {
		return billopsEntityId;
	}
	public void setBillopsEntityId(String billopsEntityId) {
		this.billopsEntityId = billopsEntityId;
	}

	public String getBillpriceGroupId() {
		return billpriceGroupId;
	}

	public void setBillpriceGroupId(String billpriceGroupId) {
		this.billpriceGroupId = billpriceGroupId;
	}


	//constructor
	public CustEntPriceGpAddRequestBean() {
	}

	//setters
	public void setCustomerRequestId(BigDecimal customerRequestId) {
		this.customerRequestId = customerRequestId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	public void setBillToCompanyId(String billToCompanyId) {
		this.billToCompanyId = billToCompanyId;
	}
	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}
	public void setOperatingCompanyId(String operatingCompanyId) {
		this.operatingCompanyId = operatingCompanyId;
	}
	public void setPriceGroupId(String priceGroupId) {
		this.priceGroupId = priceGroupId;
	}


	//getters
	public BigDecimal getCustomerRequestId() {
		return customerRequestId;
	}
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public String getBillToCompanyId() {
		return billToCompanyId;
	}
	public String getOpsEntityId() {
		return opsEntityId;
	}
	public String getOperatingCompanyId() {
		return operatingCompanyId;
	}
	public String getPriceGroupId() {
		return priceGroupId;
	}

}