package com.tcmis.client.fedex.beans;

import java.util.Date;
import java.util.Collection;
import java.util.Vector;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: FedexShippingInfoBean <br>
 * @version: 1.0, Jan 29, 2009 <br>
 *****************************************************************************/


public class FedexShippingInfoBean extends BaseDataBean {

	private String orderNo;
	private BigDecimal packingGroupId;
	private String boxId;
	private String carrierCode;
	private String carrierName;
	private String packageCode;
	private String serviceCode;
	private String shipmentChargeType;
	private String carrierAccount;
	private String fedexAccountAddress1;
	private String fedexAccountAddress2;
	private String fedexAccountAddress3;
	private String fedexAccountCity;
	private String fedexAccountState;
	private String fedexCountryCode;
	private String fedexAccountZip;
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
	private String hazmatFlag;
  private Collection<FedexShippingInfoBean> fedexShippingInfoBeanCollection = new Vector<FedexShippingInfoBean>();
  private BigDecimal totalWeight;
  private String masterTrackingNumber;
  private String carrierSecurityKey;
  private String carrierMeterNumber;
  private String shipTimeStamp;
  private String carrierPassword;
  private BigDecimal hazWeight;

  public BigDecimal getHazWeight() {
      return hazWeight;
  }

  public void setHazWeight(BigDecimal hazWeight) {
      this.hazWeight = hazWeight;
  }

    public BigDecimal getTotalWeight() {
    return totalWeight;
  }

  public void setTotalWeight(BigDecimal totalWeight) {
    this.totalWeight = totalWeight;
  }

  public String getMasterTrackingNumber() {
    return masterTrackingNumber;
  }

  public void setMasterTrackingNumber(String masterTrackingNumber) {
    this.masterTrackingNumber = masterTrackingNumber;
  }

  //constructor
	public FedexShippingInfoBean() {
	}

	//setters
	public void setOrderNo(String orderNo) {
		if(orderNo != null && this.doTrim) {
			this.orderNo = orderNo.trim();
		}
		else {
			this.orderNo = orderNo;
		}
	}
	public void setPackingGroupId(BigDecimal packingGroupId) {
		this.packingGroupId = packingGroupId;
	}
	public void setBoxId(String boxId) {
		if(boxId != null && this.doTrim) {
			this.boxId = boxId.trim();
		}
		else {
			this.boxId = boxId;
		}
	}
	public void setCarrierCode(String carrierCode) {
		if(carrierCode != null && this.doTrim) {
			this.carrierCode = carrierCode.trim();
		}
		else {
			this.carrierCode = carrierCode;
		}
	}
	public void setCarrierName(String carrierName) {
		if(carrierName != null && this.doTrim) {
			this.carrierName = carrierName.trim();
		}
		else {
			this.carrierName = carrierName;
		}
	}
	public void setPackageCode(String packageCode) {
		if(packageCode != null && this.doTrim) {
			this.packageCode = packageCode.trim();
		}
		else {
			this.packageCode = packageCode;
		}
	}
	public void setServiceCode(String serviceCode) {
		if(serviceCode != null && this.doTrim) {
			this.serviceCode = serviceCode.trim();
		}
		else {
			this.serviceCode = serviceCode;
		}
	}
	public void setShipmentChargeType(String shipmentChargeType) {
		if(shipmentChargeType != null && this.doTrim) {
			this.shipmentChargeType = shipmentChargeType.trim();
		}
		else {
			this.shipmentChargeType = shipmentChargeType;
		}
	}
	public void setCarrierAccount(String carrierAccount) {
		if(carrierAccount != null && this.doTrim) {
			this.carrierAccount = carrierAccount.trim();
		}
		else {
			this.carrierAccount = carrierAccount;
		}
	}
	public void setFedexAccountAddress1(String fedexAccountAddress11) {
		if(fedexAccountAddress11 != null && this.doTrim) {
			this.fedexAccountAddress1 = fedexAccountAddress1.trim();
		}
		else {
			this.fedexAccountAddress1 = fedexAccountAddress1;
		}
	}
	public void setFedexAccountAddress2(String fedexAccountAddress2) {
		if(fedexAccountAddress2 != null && this.doTrim) {
			this.fedexAccountAddress2 = fedexAccountAddress2.trim();
		}
		else {
			this.fedexAccountAddress2 = fedexAccountAddress2;
		}
	}
	public void setFedexAccountAddress3(String fedexAccountAddress3) {
		if(fedexAccountAddress3 != null && this.doTrim) {
			this.fedexAccountAddress3 = fedexAccountAddress3.trim();
		}
		else {
			this.fedexAccountAddress3 = fedexAccountAddress3;
		}
	}
	public void setFedexAccountCity(String fedexAccountCity) {
		if(fedexAccountCity != null && this.doTrim) {
			this.fedexAccountCity = fedexAccountCity.trim();
		}
		else {
			this.fedexAccountCity = fedexAccountCity;
		}
	}
	public void setFedexAccountState(String fedexAccountState) {
		if(fedexAccountState != null && this.doTrim) {
			this.fedexAccountState = fedexAccountState.trim();
		}
		else {
			this.fedexAccountState = fedexAccountState;
		}
	}
	public void setFedexCountryCode(String fedexCountryCode) {
		if(fedexCountryCode != null && this.doTrim) {
			this.fedexCountryCode = fedexCountryCode.trim();
		}
		else {
			this.fedexCountryCode = fedexCountryCode;
		}
	}
	public void setFedexAccountZip(String fedexAccountZip) {
		if(fedexAccountZip != null && this.doTrim) {
			this.fedexAccountZip = fedexAccountZip.trim();
		}
		else {
			this.fedexAccountZip = fedexAccountZip;
		}
	}
	public void setServiceType(String serviceType) {
		if(serviceType != null && this.doTrim) {
			this.serviceType = serviceType.trim();
		}
		else {
			this.serviceType = serviceType;
		}
	}
	public void setFreightTerms(String freightTerms) {
		if(freightTerms != null && this.doTrim) {
			this.freightTerms = freightTerms.trim();
		}
		else {
			this.freightTerms = freightTerms;
		}
	}
	public void setShipToId(String shipToId) {
		if(shipToId != null && this.doTrim) {
			this.shipToId = shipToId.trim();
		}
		else {
			this.shipToId = shipToId;
		}
	}
	public void setShipToAddrQual(String shipToAddrQual) {
		if(shipToAddrQual != null && this.doTrim) {
			this.shipToAddrQual = shipToAddrQual.trim();
		}
		else {
			this.shipToAddrQual = shipToAddrQual;
		}
	}
	public void setShipToCompany(String shipToCompany) {
		if(shipToCompany != null && this.doTrim) {
			this.shipToCompany = shipToCompany.trim();
		}
		else {
			this.shipToCompany = shipToCompany;
		}
	}
	public void setShipToAdd1(String shipToAdd1) {
		if(shipToAdd1 != null && this.doTrim) {
			this.shipToAdd1 = shipToAdd1.trim();
		}
		else {
			this.shipToAdd1 = shipToAdd1;
		}
	}
	public void setShipToAdd2(String shipToAdd2) {
		if(shipToAdd2 != null && this.doTrim) {
			this.shipToAdd2 = shipToAdd2.trim();
		}
		else {
			this.shipToAdd2 = shipToAdd2;
		}
	}
	public void setShipToAdd3(String shipToAdd3) {
		if(shipToAdd3 != null && this.doTrim) {
			this.shipToAdd3 = shipToAdd3.trim();
		}
		else {
			this.shipToAdd3 = shipToAdd3;
		}
	}
	public void setShipToCity(String shipToCity) {
		if(shipToCity != null && this.doTrim) {
			this.shipToCity = shipToCity.trim();
		}
		else {
			this.shipToCity = shipToCity;
		}
	}
	public void setShipToState(String shipToState) {
		if(shipToState != null && this.doTrim) {
			this.shipToState = shipToState.trim();
		}
		else {
			this.shipToState = shipToState;
		}
	}
	public void setShipToZip(String shipToZip) {
		if(shipToZip != null && this.doTrim) {
			this.shipToZip = shipToZip.trim();
		}
		else {
			this.shipToZip = shipToZip;
		}
	}
	public void setShipToCountry(String shipToCountry) {
		if(shipToCountry != null && this.doTrim) {
			this.shipToCountry = shipToCountry.trim();
		}
		else {
			this.shipToCountry = shipToCountry;
		}
	}
	public void setShipToPhone(String shipToPhone) {
		if(shipToPhone != null && this.doTrim) {
			this.shipToPhone = shipToPhone.trim();
		}
		else {
			this.shipToPhone = shipToPhone;
		}
	}
	public void setShipFromId(String shipFromId) {
		if(shipFromId != null && this.doTrim) {
			this.shipFromId = shipFromId.trim();
		}
		else {
			this.shipFromId = shipFromId;
		}
	}
	public void setShipFromAddrQual(String shipFromAddrQual) {
		if(shipFromAddrQual != null && this.doTrim) {
			this.shipFromAddrQual = shipFromAddrQual.trim();
		}
		else {
			this.shipFromAddrQual = shipFromAddrQual;
		}
	}
	public void setShipFromCompany(String shipFromCompany) {
		if(shipFromCompany != null && this.doTrim) {
			this.shipFromCompany = shipFromCompany.trim();
		}
		else {
			this.shipFromCompany = shipFromCompany;
		}
	}
	public void setShipFromAdd1(String shipFromAdd1) {
		if(shipFromAdd1 != null && this.doTrim) {
			this.shipFromAdd1 = shipFromAdd1.trim();
		}
		else {
			this.shipFromAdd1 = shipFromAdd1;
		}
	}
	public void setShipFromAdd2(String shipFromAdd2) {
		if(shipFromAdd2 != null && this.doTrim) {
			this.shipFromAdd2 = shipFromAdd2.trim();
		}
		else {
			this.shipFromAdd2 = shipFromAdd2;
		}
	}
	public void setShipFromAdd3(String shipFromAdd3) {
		if(shipFromAdd3 != null && this.doTrim) {
			this.shipFromAdd3 = shipFromAdd3.trim();
		}
		else {
			this.shipFromAdd3 = shipFromAdd3;
		}
	}
	public void setShipFromCity(String shipFromCity) {
		if(shipFromCity != null && this.doTrim) {
			this.shipFromCity = shipFromCity.trim();
		}
		else {
			this.shipFromCity = shipFromCity;
		}
	}
	public void setShipFromState(String shipFromState) {
		if(shipFromState != null && this.doTrim) {
			this.shipFromState = shipFromState.trim();
		}
		else {
			this.shipFromState = shipFromState;
		}
	}
	public void setShipFromCountry(String shipFromCountry) {
		if(shipFromCountry != null && this.doTrim) {
			this.shipFromCountry = shipFromCountry.trim();
		}
		else {
			this.shipFromCountry = shipFromCountry;
		}
	}
	public void setShipFromZip(String shipFromZip) {
		if(shipFromZip != null && this.doTrim) {
			this.shipFromZip = shipFromZip.trim();
		}
		else {
			this.shipFromZip = shipFromZip;
		}
	}
	public void setShipFromPhone(String shipFromPhone) {
		if(shipFromPhone != null && this.doTrim) {
			this.shipFromPhone = shipFromPhone.trim();
		}
		else {
			this.shipFromPhone = shipFromPhone;
		}
	}
	public void setFreightClass(String freightClass) {
		if(freightClass != null && this.doTrim) {
			this.freightClass = freightClass.trim();
		}
		else {
			this.freightClass = freightClass;
		}
	}
	public void setBoxQuantity(BigDecimal boxQuantity) {
		this.boxQuantity = boxQuantity;
	}
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	public void setWeightUm(String weightUm) {
		if(weightUm != null && this.doTrim) {
			this.weightUm = weightUm.trim();
		}
		else {
			this.weightUm = weightUm;
		}
	}
	public void setHazmatFlag(String hazmatFlag) {
		if(hazmatFlag != null && this.doTrim) {
			this.hazmatFlag = hazmatFlag.trim();
		}
		else {
			this.hazmatFlag = hazmatFlag;
		}
  }
  public void setFedexShippingInfoBeanCollection(Collection<FedexShippingInfoBean> c) {
		this.fedexShippingInfoBeanCollection = c;
	}

  public void setCarrierPassword(String carrierPassword) {
		if(carrierPassword != null && this.doTrim) {
			this.carrierPassword = carrierPassword.trim();
		}
		else {
			this.carrierPassword = carrierPassword;
		}
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
	public String getCarrierAccount() {
		return carrierAccount;
	}
	public String getFedexAccountAddress1() {
		return fedexAccountAddress1;
	}
	public String getFedexAccountAddress2() {
		return fedexAccountAddress2;
	}
	public String getFedexAccountAddress3() {
		return fedexAccountAddress3;
	}
	public String getFedexAccountCity() {
		return fedexAccountCity;
	}
	public String getFedexAccountState() {
		return fedexAccountState;
	}
	public String getFedexCountryCode() {
		return fedexCountryCode;
	}
	public String getFedexAccountZip() {
		return fedexAccountZip;
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
	public String getHazmatFlag() {
		return hazmatFlag;
	}
	public Collection<FedexShippingInfoBean> getFedexShippingInfoBeanCollection() {
		return this.fedexShippingInfoBeanCollection;
	}

	public String getCarrierMeterNumber() {
		return carrierMeterNumber;
	}

	public void setCarrierMeterNumber(String carrierMeterNumber) {
		this.carrierMeterNumber = carrierMeterNumber;
	}

	public String getCarrierSecurityKey() {
		return carrierSecurityKey;
	}

	public void setCarrierSecurityKey(String carrierSecurityKey) {
		this.carrierSecurityKey = carrierSecurityKey;
	}

	public String getShipTimeStamp() {
		return shipTimeStamp;
	}

	public void setShipTimeStamp(String shipTimeStamp) {
		this.shipTimeStamp = shipTimeStamp;
	}
  
  public String getCarrierPassword() {
		return carrierPassword;
	}
}