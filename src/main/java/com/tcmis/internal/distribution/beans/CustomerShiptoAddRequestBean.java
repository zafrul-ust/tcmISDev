package com.tcmis.internal.distribution.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: CustomerShiptoAddRequestBean <br>
 * @version: 1.0, Aug 20, 2009 <br>
 *****************************************************************************/


public class CustomerShiptoAddRequestBean extends BaseDataBean {

	private BigDecimal customerRequestId;
	private BigDecimal customerId;
	private String billToCompanyId;
	private String shipToAddressLine1;
	private String shipToAddressLine2;
	private String shipToAddressLine3;
	private String shipToAddressLine4;
	private String shipToAddressLine5;
	private String shipToCountryAbbrev;
	private String shipToStateAbbrev;
	private String shipToCity;
	private String shipToZip;
	private String defaultInventoryGroup;
	private String locationDesc;
	private BigDecimal salesAgentId;
	private BigDecimal fieldSalesRepId;
	private String priceGroupId;
	private String msdsLocaleOverride;
	private BigDecimal jdeCustomerShipTo;
	
	// from page
	private String changed;
	private String lineId;
	private String salesAgentName;
	private String fieldSalesRepName;
	private String opsEntityId;
	
	private String legacyShiptoId;
	private String internalNote;

	//constructor
	public CustomerShiptoAddRequestBean() {
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
	public void setShipToAddressLine1(String shipToAddressLine1) {
		this.shipToAddressLine1 = shipToAddressLine1;
	}
	public void setShipToAddressLine2(String shipToAddressLine2) {
		this.shipToAddressLine2 = shipToAddressLine2;
	}
	public void setShipToAddressLine3(String shipToAddressLine3) {
		this.shipToAddressLine3 = shipToAddressLine3;
	}
	public void setShipToAddressLine4(String shipToAddressLine4) {
		this.shipToAddressLine4 = shipToAddressLine4;
	}
	public void setShipToAddressLine5(String shipToAddressLine5) {
		this.shipToAddressLine5 = shipToAddressLine5;
	}
	public void setShipToCountryAbbrev(String shipToCountryAbbrev) {
		this.shipToCountryAbbrev = shipToCountryAbbrev;
	}
	public void setShipToStateAbbrev(String shipToStateAbbrev) {
		this.shipToStateAbbrev = shipToStateAbbrev;
	}
	public void setShipToCity(String shipToCity) {
		this.shipToCity = shipToCity;
	}
	public void setShipToZip(String shipToZip) {
		this.shipToZip = shipToZip;
	}
	public void setDefaultInventoryGroup(String defaultInventoryGroup) {
		this.defaultInventoryGroup = defaultInventoryGroup;
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
	public String getShipToAddressLine1() {
		return shipToAddressLine1;
	}
	public String getShipToAddressLine2() {
		return shipToAddressLine2;
	}
	public String getShipToAddressLine3() {
		return shipToAddressLine3;
	}
	public String getShipToAddressLine4() {
		return shipToAddressLine4;
	}
	public String getShipToAddressLine5() {
		return shipToAddressLine5;
	}
	public String getShipToCountryAbbrev() {
		return shipToCountryAbbrev;
	}
	public String getShipToStateAbbrev() {
		return shipToStateAbbrev;
	}
	public String getShipToCity() {
		return shipToCity;
	}
	public String getShipToZip() {
		return shipToZip;
	}
	public String getDefaultInventoryGroup() {
		return defaultInventoryGroup;
	}
	public String getLocationDesc() {
		return locationDesc;
	}

	public void setLocationDesc(String locationDesc) {
		this.locationDesc = locationDesc;
	}
	public BigDecimal getSalesAgentId() {
		return salesAgentId;
	}

	public void setSalesAgentId(BigDecimal salesAgentId) {
		this.salesAgentId = salesAgentId;
	}

	public BigDecimal getFieldSalesRepId() {
		return fieldSalesRepId;
	}

	public void setFieldSalesRepId(BigDecimal fieldSalesRepId) {
		this.fieldSalesRepId = fieldSalesRepId;
	}

	public String getSalesAgentName() {
		return salesAgentName;
	}

	public void setSalesAgentName(String salesAgentName) {
		this.salesAgentName = salesAgentName;
	}

	public String getFieldSalesRepName() {
		return fieldSalesRepName;
	}

	public void setFieldSalesRepName(String fieldSalesRepName) {
		this.fieldSalesRepName = fieldSalesRepName;
	}
	public String getPriceGroupId() {
		return priceGroupId;
	}
	public void setPriceGroupId(String priceGroupId) {
		this.priceGroupId = priceGroupId;
	}
	public String getOpsEntityId() {
		return opsEntityId;
	}
	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}

	public String getLegacyShiptoId() {
		return legacyShiptoId;
	}

	public void setLegacyShiptoId(String legacyShiptoId) {
		this.legacyShiptoId = legacyShiptoId;
	}

	public String getMsdsLocaleOverride() {
		return msdsLocaleOverride;
	}

	public void setMsdsLocaleOverride(String msdsLocaleOverride) {
		this.msdsLocaleOverride = msdsLocaleOverride;
	}

	public String getInternalNote() {
		return internalNote;
	}

	public void setInternalNote(String internalNote) {
		this.internalNote = internalNote;
	}

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	public String getChanged() {
		return changed;
	}

	public void setChanged(String changed) {
		this.changed = changed;
	}
	
	public BigDecimal getJdeCustomerShipTo() {
		return jdeCustomerShipTo;
	}

	public void setJdeCustomerShipTo(BigDecimal jdeCustomerShipTo) {
		this.jdeCustomerShipTo = jdeCustomerShipTo;
	}
	
}