package com.tcmis.ws.scanner.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: InventoryGroupDefinitionBean <br>
 * 
 * @version: 1.0, Jun 1, 2004 <br>
 *****************************************************************************/

public class InventoryGroupCarrier extends BaseDataBean {

	private String	accountCompanyCarrierCode;
	private String	accountCompanyId;
	private String	carrierAccountId;
	private Date	lastUpdated;

	// constructor
	public InventoryGroupCarrier() {
	}

	public String getAccountCompanyCarrierCode() {
		return this.accountCompanyCarrierCode;
	}

	public String getAccountCompanyId() {
		return this.accountCompanyId;
	}

	public String getCarrierAccountId() {
		return this.carrierAccountId;
	}

	public Date getLastUpdated() {
		return this.lastUpdated;
	}

	public void setAccountCompanyCarrierCode(String accountCompanyCarrierCode) {
		this.accountCompanyCarrierCode = accountCompanyCarrierCode;
	}

	public void setAccountCompanyId(String accountCompanyId) {
		this.accountCompanyId = accountCompanyId;
	}

	public void setCarrierAccountId(String carrierAccountId) {
		this.carrierAccountId = carrierAccountId;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

}