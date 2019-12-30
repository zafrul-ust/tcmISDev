package com.tcmis.internal.distribution.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: NewShipToAddressInputBean <br>
 * @version: 1.0, Jun 24, 2009 <br>
 *****************************************************************************/


public class NewShipToAddressInputBean extends BaseDataBean {
	
	private static final long serialVersionUID = 9062514868235221526L;
	private String customerName;
	private String customerId;
	private String sapVendorCode;
	private String remitToCountryAbbrev;
	private String remitToStateAbbrev;
	private String remitToAddressLine1;
	private String remitToAddressLine2;
	private String remitToAddressLine3;
	private String remitToAddressLine4;
	private String remitToAddressLine5;
	private String remitToCity;
	private String remitToZip;
	private String remitToZipPlus;
	private String locationDesc;
	private String inventoryGroup;
	private BigDecimal salesAgentId;
	private BigDecimal fieldSalesRepId;
	private String priceGroupId;
	private BigDecimal shipToLocationId;
	private String billToCompanyId;
	
	private String companyId;
	private String opsEntityId;
	private String shiptoNotes;
	private String msdsLocaleOverride;
	
	public String getMsdsLocaleOverride() {
		return msdsLocaleOverride;
	}
	public void setMsdsLocaleOverride(String msdsLocaleOverride) {
		this.msdsLocaleOverride = msdsLocaleOverride;
	}
	public String getShiptoNotes() {
		return shiptoNotes;
	}
	public void setShiptoNotes(String shiptoNotes) {
		this.shiptoNotes = shiptoNotes;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getOpsEntityId() {
		return opsEntityId;
	}
	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}
	public String getRemitToAddressLine1() {
		return remitToAddressLine1;
	}
	public void setRemitToAddressLine1(String remitToAddressLine1) {
		this.remitToAddressLine1 = remitToAddressLine1;
	}
	public String getRemitToAddressLine2() {
		return remitToAddressLine2;
	}
	public void setRemitToAddressLine2(String remitToAddressLine2) {
		this.remitToAddressLine2 = remitToAddressLine2;
	}
	public String getRemitToAddressLine3() {
		return remitToAddressLine3;
	}
	public void setRemitToAddressLine3(String remitToAddressLine3) {
		this.remitToAddressLine3 = remitToAddressLine3;
	}
	public String getRemitToCity() {
		return remitToCity;
	}
	public void setRemitToCity(String remitToCity) {
		this.remitToCity = remitToCity;
	}
	public String getRemitToCountryAbbrev() {
		return remitToCountryAbbrev;
	}
	public void setRemitToCountryAbbrev(String remitToCountryAbbrev) {
		this.remitToCountryAbbrev = remitToCountryAbbrev;
	}
	public String getRemitToStateAbbrev() {
		return remitToStateAbbrev;
	}
	public void setRemitToStateAbbrev(String remitToStateAbbrev) {
		this.remitToStateAbbrev = remitToStateAbbrev;
	}
	public String getRemitToZip() {
		return remitToZip;
	}
	public void setRemitToZip(String remitToZip) {
		this.remitToZip = remitToZip;
	}
	public String getRemitToZipPlus() {
		return remitToZipPlus;
	}
	public void setRemitToZipPlus(String remitToZipPlus) {
		this.remitToZipPlus = remitToZipPlus;
	}
	public String getSapVendorCode() {
		return sapVendorCode;
	}
	public void setSapVendorCode(String sapVendorCode) {
		this.sapVendorCode = sapVendorCode;
	}
	public void setRemitToAddressLine4(String remitToAddressLine4) {
		this.remitToAddressLine4 = remitToAddressLine4;
	}
	public String getRemitToAddressLine4() {
		return remitToAddressLine4;
	}
	public void setRemitToAddressLine5(String remitToAddressLine5) {
		this.remitToAddressLine5 = remitToAddressLine5;
	}
	public String getRemitToAddressLine5() {
		return remitToAddressLine5;
	}
	public void setLocationDesc(String locationDesc) {
		this.locationDesc = locationDesc;
	}
	public String getLocationDesc() {
		return locationDesc;
	}
	public void setSalesAgentId(BigDecimal salesAgentId) {
		this.salesAgentId = salesAgentId;
	}
	public BigDecimal getSalesAgentId() {
		return salesAgentId;
	}
	public void setFieldSalesRepId(BigDecimal fieldSalesRepId) {
		this.fieldSalesRepId = fieldSalesRepId;
	}
	public BigDecimal getFieldSalesRepId() {
		return fieldSalesRepId;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public void setPriceGroupId(String priceGroupId) {
		this.priceGroupId = priceGroupId;
	}
	public String getPriceGroupId() {
		return priceGroupId;
	}
	public void setShipToLocationId(BigDecimal shipToLocationId) {
		this.shipToLocationId = shipToLocationId;
	}
	public BigDecimal getShipToLocationId() {
		return shipToLocationId;
	}
	public void setBillToCompanyId(String billToCompanyId) {
		this.billToCompanyId = billToCompanyId;
	}
	public String getBillToCompanyId() {
		return billToCompanyId;
	}
	
}