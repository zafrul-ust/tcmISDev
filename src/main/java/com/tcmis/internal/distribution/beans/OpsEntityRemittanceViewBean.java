package com.tcmis.internal.distribution.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: OpsEntityRemittanceViewBean <br>
 * @version: 1.0, Sep 27, 2010 <br>
 *****************************************************************************/


public class OpsEntityRemittanceViewBean extends BaseDataBean {

	private String opsCompanyId;
	private String opsEntityId;
	private String remittanceId;
	private String currencyId;
	private String currencyDescription;
	private String currencyDisplay;
	private String remittanceName;
	private String operatingEntityName;


	//constructor
	public OpsEntityRemittanceViewBean() {
	}

	//setters
	public void setOpsCompanyId(String opsCompanyId) {
		this.opsCompanyId = opsCompanyId;
	}
	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}
	public void setRemittanceId(String remittanceId) {
		this.remittanceId = remittanceId;
	}
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}
	public void setCurrencyDescription(String currencyDescription) {
		this.currencyDescription = currencyDescription;
	}
	public void setCurrencyDisplay(String currencyDisplay) {
		this.currencyDisplay = currencyDisplay;
	}
	public void setRemittanceName(String remittanceName) {
		this.remittanceName = remittanceName;
	}
	public void setOperatingEntityName(String operatingEntityName) {
		this.operatingEntityName = operatingEntityName;
	}


	//getters
	public String getOpsCompanyId() {
		return opsCompanyId;
	}
	public String getOpsEntityId() {
		return opsEntityId;
	}
	public String getRemittanceId() {
		return remittanceId;
	}
	public String getCurrencyId() {
		return currencyId;
	}
	public String getCurrencyDescription() {
		return currencyDescription;
	}
	public String getCurrencyDisplay() {
		return currencyDisplay;
	}
	public String getRemittanceName() {
		return remittanceName;
	}
	public String getOperatingEntityName() {
		return operatingEntityName;
	}

}