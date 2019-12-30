package com.tcmis.internal.supply.beans;

import com.tcmis.common.framework.BaseDataBean;

public class SuggestedSupplierBean extends BaseDataBean {
	 private String suggestedVendor; //cai.SUGGESTED_VENDOR,
	 private String vendorContactEmail; //cai.VENDOR_CONTACT_EMAIL;
	 private String vendorContactFax; //cai.VENDOR_CONTACT_FAX;
	 private String vendorContactName; //cai.VENDOR_CONTACT_NAME;
	 private String vendorContactPhone; //cai.VENDOR_CONTACT_PHONE;
	 private String vendorPartNo; //carn.VENDOR_PART_NO;
	 private String catPartNo;
	 private String requestId;
	 private String catalogId;
	 
	public String getSuggestedVendor() {
		return suggestedVendor;
	}
	public void setSuggestedVendor(String suggestedVendor) {
		this.suggestedVendor = suggestedVendor;
	}
	public String getVendorContactEmail() {
		return vendorContactEmail;
	}
	public void setVendorContactEmail(String vendorContactEmail) {
		this.vendorContactEmail = vendorContactEmail;
	}
	public String getVendorContactFax() {
		return vendorContactFax;
	}
	public void setVendorContactFax(String vendorContactFax) {
		this.vendorContactFax = vendorContactFax;
	}
	public String getVendorContactName() {
		return vendorContactName;
	}
	public void setVendorContactName(String vendorContactName) {
		this.vendorContactName = vendorContactName;
	}
	public String getVendorContactPhone() {
		return vendorContactPhone;
	}
	public void setVendorContactPhone(String vendorContactPhone) {
		this.vendorContactPhone = vendorContactPhone;
	}
	public String getVendorPartNo() {
		return vendorPartNo;
	}
	public void setVendorPartNo(String vendorPartNo) {
		this.vendorPartNo = vendorPartNo;
	}
	public String getCatPartNo() {
		return catPartNo;
	}
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getCatalogId() {
		return catalogId;
	}
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	 
	 
	 
	 
}
