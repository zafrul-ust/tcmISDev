package com.tcmis.client.api.beans;

import com.tcmis.common.framework.BaseDataBean;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

/******************************************************************************
 * CLASSNAME: EcommerceShipmentNotificationBean <br>
 * @version: 1.0, Jan 11, 2020 <br>
 *****************************************************************************/

public class EcommerceShipmentNotificationBean extends BaseDataBean {

	private BigDecimal prNumber;
    private String lineItem;
    private BigDecimal quantity;
    private String sourceHub;
    private String inventoryGroup;
    private BigDecimal shipmentId;
    private BigDecimal itemId;
    private String shipToLocationId;
    private String application;
    private String applicationDesc;
    private Date releaseDate;
    private String facPartNo;
    private BigDecimal catalogPrice;
    private String currencyId;
    private String poNumber;
    private String releaseNumber;
    private String endUser;
    private String contactInfo;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String countryAbbrev;
    private String stateAbbrev;
    private String city;
    private String zip;
    private String locationDesc;
    private String payloadId;
    private String partDescription;
    private Collection detailData;


	//constructor
	public EcommerceShipmentNotificationBean() {
	}

    public BigDecimal getPrNumber() {
        return prNumber;
    }

    public void setPrNumber(BigDecimal prNumber) {
        this.prNumber = prNumber;
    }

    public String getLineItem() {
        return lineItem;
    }

    public void setLineItem(String lineItem) {
        this.lineItem = lineItem;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getSourceHub() {
        return sourceHub;
    }

    public void setSourceHub(String sourceHub) {
        this.sourceHub = sourceHub;
    }

    public String getInventoryGroup() {
        return inventoryGroup;
    }

    public void setInventoryGroup(String inventoryGroup) {
        this.inventoryGroup = inventoryGroup;
    }

    public BigDecimal getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(BigDecimal shipmentId) {
        this.shipmentId = shipmentId;
    }

    public BigDecimal getItemId() {
        return itemId;
    }

    public void setItemId(BigDecimal itemId) {
        this.itemId = itemId;
    }

    public String getShipToLocationId() {
        return shipToLocationId;
    }

    public void setShipToLocationId(String shipToLocationId) {
        this.shipToLocationId = shipToLocationId;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getFacPartNo() {
        return facPartNo;
    }

    public void setFacPartNo(String facPartNo) {
        this.facPartNo = facPartNo;
    }

    public BigDecimal getCatalogPrice() {
        return catalogPrice;
    }

    public void setCatalogPrice(BigDecimal catalogPrice) {
        this.catalogPrice = catalogPrice;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    public String getReleaseNumber() {
        return releaseNumber;
    }

    public void setReleaseNumber(String releaseNumber) {
        this.releaseNumber = releaseNumber;
    }

    public String getEndUser() {
        return endUser;
    }

    public void setEndUser(String endUser) {
        this.endUser = endUser;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    public String getCountryAbbrev() {
        return countryAbbrev;
    }

    public void setCountryAbbrev(String countryAbbrev) {
        this.countryAbbrev = countryAbbrev;
    }

    public String getStateAbbrev() {
        return stateAbbrev;
    }

    public void setStateAbbrev(String stateAbbrev) {
        this.stateAbbrev = stateAbbrev;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getLocationDesc() {
        return locationDesc;
    }

    public void setLocationDesc(String locationDesc) {
        this.locationDesc = locationDesc;
    }

    public String getPayloadId() {
        return payloadId;
    }

    public void setPayloadId(String payloadId) {
        this.payloadId = payloadId;
    }

    public String getPartDescription() {
        return partDescription;
    }

    public void setPartDescription(String partDescription) {
        this.partDescription = partDescription;
    }

    public Collection getDetailData() {
        return detailData;
    }

    public void setDetailData(Collection detailData) {
        this.detailData = detailData;
    }

	public String getApplicationDesc() {
		return applicationDesc;
	}

	public void setApplicationDesc(String applicationDesc) {
		this.applicationDesc = applicationDesc;
	}
}