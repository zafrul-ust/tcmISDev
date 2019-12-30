package com.tcmis.client.ups.beans;

import java.util.Date;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Vector;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: UpsShippingInfoBean <br>
 * @version: 1.0, Feb 19, 2008 <br>
 *****************************************************************************/


public class UpsShippingInfoBean extends BaseDataBean {

	private String orderNo;
	private BigDecimal packingGroupId;
	private String boxId;
	private String carrierCode;
	private String carrierName;
	private String packageCode;
	private String serviceCode;
	private String shipmentChargeType;
	private String upsAccountNumber;
	private String upsAccountAddress1;
	private String upsAccountAddress2;
	private String upsAccountAddress3;
	private String upsAccountCity;
	private String upsAccountState;
	private String upsCountryCode;
	private String upsAccountZip;
	private String serviceType;
	private String freightTerms;
	private String shipToId;
	private String shipToAddrQual;
	private String shipToCompany;
	private String shipToAdd1;
	private String shipToAdd2;
	private String shipToAdd3;
	private String shipToCity;
	private String shipToState;
	private String shipToZip;
	private String shipToCountry;
	private String shipToPhone;
	private String shipFromId;
	private String shipFromAddrQual;
	private String shipFromCompany;
	private String shipFromAdd1;
	private String shipFromAdd2;
	private String shipFromAdd3;
	private String shipFromCity;
	private String shipFromState;
	private String shipFromCountry;
	private String shipFromZip;
	private String shipFromPhone;
	private String freightClass;
	private BigDecimal boxQuantity;
	private BigDecimal weight;
	private String weightUm;
	private Collection<UpsShippingInfoBean> upsShippingInfoBeanCollection = new Vector<UpsShippingInfoBean>();


	//constructor
	public UpsShippingInfoBean() {
	}

	//setters
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public void setPackingGroupId(BigDecimal packingGroupId) {
		this.packingGroupId = packingGroupId;
	}
	public void setBoxId(String boxId) {
		this.boxId = boxId;
	}
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}
	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	public void setShipmentChargeType(String shipmentChargeType) {
		this.shipmentChargeType = shipmentChargeType;
	}
	public void setUpsAccountNumber(String upsAccountNumber) {
		this.upsAccountNumber = upsAccountNumber;
	}
	public void setUpsAccountAddress1(String upsAccountAddress11) {
		this.upsAccountAddress1 = upsAccountAddress11;
	}
	public void setUpsAccountAddress2(String upsAccountAddress22) {
		this.upsAccountAddress2 = upsAccountAddress22;
	}
	public void setUpsAccountAddress3(String upsAccountAddress33) {
		this.upsAccountAddress3 = upsAccountAddress33;
	}
	public void setUpsAccountCity(String upsAccountCity) {
		this.upsAccountCity = upsAccountCity;
	}
	public void setUpsAccountState(String upsAccountState) {
		this.upsAccountState = upsAccountState;
	}
	public void setUpsCountryCode(String upsCountryCode) {
		this.upsCountryCode = upsCountryCode;
	}
	public void setUpsAccountZip(String upsAccountZip) {
		this.upsAccountZip = upsAccountZip;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public void setFreightTerms(String freightTerms) {
		this.freightTerms = freightTerms;
	}
	public void setShipToId(String shipToId) {
		this.shipToId = shipToId;
	}
	public void setShipToAddrQual(String shipToAddrQual) {
		this.shipToAddrQual = shipToAddrQual;
	}
	public void setShipToCompany(String shipToCompany) {
		this.shipToCompany = shipToCompany;
	}
	public void setShipToAdd1(String shipToAdd1) {
		this.shipToAdd1 = shipToAdd1;
	}
	public void setShipToAdd2(String shipToAdd2) {
		this.shipToAdd2 = shipToAdd2;
	}
	public void setShipToAdd3(String shipToAdd3) {
		this.shipToAdd3 = shipToAdd3;
	}
	public void setShipToCity(String shipToCity) {
		this.shipToCity = shipToCity;
	}
	public void setShipToState(String shipToState) {
		this.shipToState = shipToState;
	}
	public void setShipToZip(String shipToZip) {
		this.shipToZip = shipToZip;
	}
	public void setShipToCountry(String shipToCountry) {
		this.shipToCountry = shipToCountry;
	}
	public void setShipToPhone(String shipToPhone) {
		this.shipToPhone = shipToPhone;
	}
	public void setShipFromId(String shipFromId) {
		this.shipFromId = shipFromId;
	}
	public void setShipFromAddrQual(String shipFromAddrQual) {
		this.shipFromAddrQual = shipFromAddrQual;
	}
	public void setShipFromCompany(String shipFromCompany) {
		this.shipFromCompany = shipFromCompany;
	}
	public void setShipFromAdd1(String shipFromAdd11) {
		this.shipFromAdd1 = shipFromAdd11;
	}
	public void setShipFromAdd2(String shipFromAdd22) {
		this.shipFromAdd2 = shipFromAdd22;
	}
	public void setShipFromAdd3(String shipFromAdd33) {
		this.shipFromAdd3 = shipFromAdd33;
	}
	public void setShipFromCity(String shipFromCity) {
		this.shipFromCity = shipFromCity;
	}
	public void setShipFromState(String shipFromState) {
		this.shipFromState = shipFromState;
	}
	public void setShipFromCountry(String shipFromCountry) {
		this.shipFromCountry = shipFromCountry;
	}
	public void setShipFromZip(String shipFromZip) {
		this.shipFromZip = shipFromZip;
	}
	public void setShipFromPhone(String shipFromPhone) {
		this.shipFromPhone = shipFromPhone;
	}
	public void setFreightClass(String freightClass) {
		this.freightClass = freightClass;
	}
	public void setBoxQuantity(BigDecimal boxQuantity) {
		this.boxQuantity = boxQuantity;
	}
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	public void setWeightUm(String weightUm) {
		this.weightUm = weightUm;
	}
	public void setUpsShippingInfoBeanCollection(Collection<UpsShippingInfoBean> c) {
		this.upsShippingInfoBeanCollection = c;
	}

	//getters
	public String getOrderNo() {
		return orderNo;
	}
	public BigDecimal getPackingGroupId() {
		return packingGroupId;
	}
	public String getBoxId() {
		return boxId;
	}
	public String getCarrierCode() {
		return carrierCode;
	}
	public String getCarrierName() {
		return carrierName;
	}
	public String getPackageCode() {
		return packageCode;
	}
	public String getServiceCode() {
		return serviceCode;
	}
	public String getShipmentChargeType() {
		return shipmentChargeType;
	}
	public String getUpsAccountNumber() {
		return upsAccountNumber;
	}
	public String getUpsAccountAddress1() {
		return upsAccountAddress1;
	}
	public String getUpsAccountAddress2() {
		return upsAccountAddress2;
	}
	public String getUpsAccountAddress3() {
		return upsAccountAddress3;
	}
	public String getUpsAccountCity() {
		return upsAccountCity;
	}
	public String getUpsAccountState() {
		return upsAccountState;
	}
	public String getUpsCountryCode() {
		return upsCountryCode;
	}
	public String getUpsAccountZip() {
		return upsAccountZip;
	}
	public String getServiceType() {
		return serviceType;
	}
	public String getFreightTerms() {
		return freightTerms;
	}
	public String getShipToId() {
		return shipToId;
	}
	public String getShipToAddrQual() {
		return shipToAddrQual;
	}
	public String getShipToCompany() {
		return shipToCompany;
	}
	public String getShipToAdd1() {
		return shipToAdd1;
	}
	public String getShipToAdd2() {
		return shipToAdd2;
	}
	public String getShipToAdd3() {
		return shipToAdd3;
	}
	public String getShipToCity() {
		return shipToCity;
	}
	public String getShipToState() {
		return shipToState;
	}
	public String getShipToZip() {
		return shipToZip;
	}
	public String getShipToCountry() {
		return shipToCountry;
	}
	public String getShipToPhone() {
		return shipToPhone;
	}
	public String getShipFromId() {
		return shipFromId;
	}
	public String getShipFromAddrQual() {
		return shipFromAddrQual;
	}
	public String getShipFromCompany() {
		return shipFromCompany;
	}
	public String getShipFromAdd1() {
		return shipFromAdd1;
	}
	public String getShipFromAdd2() {
		return shipFromAdd2;
	}
	public String getShipFromAdd3() {
		return shipFromAdd3;
	}
	public String getShipFromCity() {
		return shipFromCity;
	}
	public String getShipFromState() {
		return shipFromState;
	}
	public String getShipFromCountry() {
		return shipFromCountry;
	}
	public String getShipFromZip() {
		return shipFromZip;
	}
	public String getShipFromPhone() {
		return shipFromPhone;
	}
	public String getFreightClass() {
		return freightClass;
	}
	public BigDecimal getBoxQuantity() {
		return boxQuantity;
	}
	public BigDecimal getWeight() {
		return weight;
	}
	public String getWeightUm() {
		return weightUm;
	}
	public Collection<UpsShippingInfoBean> getUpsShippingInfoBeanCollection() {
		return this.upsShippingInfoBeanCollection;
	}
}