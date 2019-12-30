package com.tcmis.internal.supply.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: SupplierBillingLocationViewBean <br>
 * @version: 1.0, Jun 25, 2009 <br>
 *****************************************************************************/


public class SupplierBillingLocationViewBean extends BaseDataBean {
	
	private static final long serialVersionUID = -9140077141550543901L;
	
	private String supplier;
	private String supplierName;
	private String billingLocationId;
	private String locationKey;
	private String comments;
	private String billAddressVerfiedWithEe;
	private String sameAsVendor;
	private String tSupplierEeSupplierAddSame;
	private String voucheredAddress;
	private String sapVendorCode;
	private String status;
	private BigDecimal sapVenderAssignedBy;
	private String approverName;
	private Date sapVenderAssignedDate;
	private String countryAbbrev;
	private String stateAbbrev;
	private String addressLine1;
	private String addressLine2;
	private String addressLine3;
	private String city;
	private String zip;
	private String locationDesc;
	private String ok;
	private String statusCol;
    private String supplierInvoiceIds;
    private BigDecimal requester;
    private String requesterEmail;
    private String requesterName;
    private Date requestDate;
    private String tcmSupplierBillAddress;
    
	//constructor
	public SupplierBillingLocationViewBean() {
	}

	//setters
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public void setBillingLocationId(String billingLocationId) {
		this.billingLocationId = billingLocationId;
	}
	public void setLocationKey(String locationKey) {
		this.locationKey = locationKey;
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
	public void setVoucheredAddress(String voucheredAddress) {
		this.voucheredAddress = voucheredAddress;
	}
	public void setSapVendorCode(String sapVendorCode) {
		this.sapVendorCode = sapVendorCode;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setSapVenderAssignedBy(BigDecimal sapVenderAssignedBy) {
		this.sapVenderAssignedBy = sapVenderAssignedBy;
	}
	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}
	public void setSapVenderAssignedDate(Date sapVenderAssignedDate) {
		this.sapVenderAssignedDate = sapVenderAssignedDate;
	}
	public void setCountryAbbrev(String countryAbbrev) {
		this.countryAbbrev = countryAbbrev;
	}
	public void setStateAbbrev(String stateAbbrev) {
		this.stateAbbrev = stateAbbrev;
	}

	public void setCity(String city) {
		this.city = city;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public void setLocationDesc(String locationDesc) {
		this.locationDesc = locationDesc;
	}


	//getters
	public String getSupplier() {
		return supplier;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public String getBillingLocationId() {
		return billingLocationId;
	}
	public String getLocationKey() {
		return locationKey;
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
	public String getVoucheredAddress() {
		return voucheredAddress;
	}
	public String getSapVendorCode() {
		return sapVendorCode;
	}
	public String getStatus() {
		return status;
	}
	public BigDecimal getSapVenderAssignedBy() {
		return sapVenderAssignedBy;
	}
	public String getApproverName() {
		return approverName;
	}
	public Date getSapVenderAssignedDate() {
		return sapVenderAssignedDate;
	}
	public String getCountryAbbrev() {
		return countryAbbrev;
	}
	public String getStateAbbrev() {
		return stateAbbrev;
	}
	
	public String getCity() {
		return city;
	}
	public String getZip() {
		return zip;
	}
	public String getLocationDesc() {
		return locationDesc;
	}

	/**
	 * @param ok the ok to set
	 */
	public void setOk(String ok) {
		this.ok = ok;
	}

	/**
	 * @return the ok
	 */
	public String getOk() {
		return ok;
	}

	/**
	 * @param addressLine1 the addressLine1 to set
	 */
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	/**
	 * @return the addressLine1
	 */
	public String getAddressLine1() {
		return addressLine1;
	}

	/**
	 * @param addressLine2 the addressLine2 to set
	 */
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	/**
	 * @return the addressLine2
	 */
	public String getAddressLine2() {
		return addressLine2;
	}

	/**
	 * @param addressLine3 the addressLine3 to set
	 */
	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}

	/**
	 * @return the addressLine3
	 */
	public String getAddressLine3() {
		return addressLine3;
	}

	/**
	 * @param statusCol the statusCol to set
	 */
	public void setStatusCol(String statusCol) {
		this.statusCol = statusCol;
	}

	/**
	 * @return the statusCol
	 */
	public String getStatusCol() {
		return statusCol;
	}

	public void setSupplierInvoiceIds(String supplierInvoiceIds) {
		this.supplierInvoiceIds = supplierInvoiceIds;
	}

	public String getSupplierInvoiceIds() {
		return supplierInvoiceIds;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public BigDecimal getRequester() {
		return requester;
	}

	public void setRequester(BigDecimal requester) {
		this.requester = requester;
	}

	public String getRequesterEmail() {
		return requesterEmail;
	}

	public void setRequesterEmail(String requesterEmail) {
		this.requesterEmail = requesterEmail;
	}

    public String getRequesterName() {
        return requesterName;
    }

    public void setRequesterName(String requesterName) {
        this.requesterName = requesterName;
    }

	public String getTcmSupplierBillAddress() {
		return tcmSupplierBillAddress;
	}

	public void setTcmSupplierBillAddress(String tcmSupplierBillAddress) {
		this.tcmSupplierBillAddress = tcmSupplierBillAddress;
	}
}