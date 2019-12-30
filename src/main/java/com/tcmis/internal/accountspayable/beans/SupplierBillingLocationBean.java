package com.tcmis.internal.accountspayable.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: SupplierBillingLocationBean <br>
 * @version: 1.0, Aug 30, 2007 <br>
 *****************************************************************************/


public class SupplierBillingLocationBean extends BaseDataBean {

	private String supplier;
	private String billingLocationId;
	private String locationKey;
	private String eSupplierId;
	private String eRemitToId;
	private String comments;
	private String billAddressVerfiedWithEe;
	private String sameAsVendor;
	private String tSupplierEeSupplierAddSame;
	private String notUsed1;
	private String notUsed2;
	private String voucheredAddress;


	//constructor
	public SupplierBillingLocationBean() {
	}

	//setters
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public void setBillingLocationId(String billingLocationId) {
		this.billingLocationId = billingLocationId;
	}
	public void setLocationKey(String locationKey) {
		this.locationKey = locationKey;
	}
	public void setESupplierId(String eSupplierId) {
		this.eSupplierId = eSupplierId;
	}
	public void setERemitToId(String eRemitToId) {
		this.eRemitToId = eRemitToId;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public void setBillAddressVerfiedWithE(String billAddressVerfiedWithEe) {
		this.billAddressVerfiedWithEe = billAddressVerfiedWithEe;
	}
	public void setSameAsVendor(String sameAsVendor) {
		this.sameAsVendor = sameAsVendor;
	}
	public void setTSupplierESupplierAddSame(String tSupplierEeSupplierAddSame) {
		this.tSupplierEeSupplierAddSame = tSupplierEeSupplierAddSame;
	}
	public void setNotUsed1(String notUsed1) {
		this.notUsed1 = notUsed1;
	}
	public void setNotUsed2(String notUsed2) {
		this.notUsed2 = notUsed2;
	}
	public void setVoucheredAddress(String voucheredAddress) {
		this.voucheredAddress = voucheredAddress;
	}


	//getters
	public String getSupplier() {
		return supplier;
	}
	public String getBillingLocationId() {
		return billingLocationId;
	}
	public String getLocationKey() {
		return locationKey;
	}
	public String getESupplierId() {
		return eSupplierId;
	}
	public String getERemitToId() {
		return eRemitToId;
	}
	public String getComments() {
		return comments;
	}
	public String getBillAddressVerfiedWithE() {
		return billAddressVerfiedWithEe;
	}
	public String getSameAsVendor() {
		return sameAsVendor;
	}
	public String getTSupplierESupplierAddSame() {
		return tSupplierEeSupplierAddSame;
	}
	public String getNotUsed1() {
		return notUsed1;
	}
	public String getNotUsed2() {
		return notUsed2;
	}
	public String getVoucheredAddress() {
		return voucheredAddress;
	}
}