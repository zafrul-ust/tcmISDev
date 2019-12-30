package com.tcmis.internal.hub.beans;

import java.sql.SQLData;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.SQLException;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: TlLtlCarrierIgViewBean <br>
 * @version: 1.0, Oct 25, 2009 <br>
 *****************************************************************************/


public class TlLtlCarrierIgViewBean extends BaseDataBean{

	private String carrierName;
	private String carrierCode;
	private String inventoryGroup;
	private String accountCompanyId;
	private String transportationMode;
	private String searchText;

	//constructor
	public TlLtlCarrierIgViewBean() {
	}

	//setters
	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}

	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public void setAccountCompanyId(String accountCompanyId) {
		this.accountCompanyId = accountCompanyId;
	}

	public void setTransportationMode(String transportationMode) {
		this.transportationMode = transportationMode;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	//getters
	public String getCarrierName() {
		return carrierName;
	}

	public String getCarrierCode() {
		return carrierCode;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public String getAccountCompanyId() {
		return accountCompanyId;
	}

	public String getTransportationMode() {
		return transportationMode;
	}

	public String getSearchText() {
		return searchText;
	}
}