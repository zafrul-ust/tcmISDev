package com.tcmis.ws.scanner.beans;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: PoLineBean <br>
 * 
 * @version: 1.0, Mar 7, 2013 <br>
 *****************************************************************************/

public class DepartureShipment extends BaseDataBean {

	private String					alternateCarrier;
	private String					carrierName;
	private String					customerReference;
	private BigDecimal				dryIceWeight;
	private boolean					ergChecked;
	private boolean					ergCheckedSet;
	private BigDecimal				freightCharge;
	private String					freightChargePaymentSource;	// One of "Third
																// Party",
																// "PrePaid" or
																// "Collect"
	private BigDecimal				grossWeight;
	private String					id;
	private BigDecimal				numberOfPallets;
	private String					palletTrackingNumbers;
	private BigDecimal				personnelId;
	private Collection<PickingUnit>	pickingUnits;
	private Collection<Sample>		samples;
	private boolean					placardsRequired;
	private boolean					placardsRequiredSet;
	private boolean					placardsSupplied;
	private Date					shipDate;
	private BigDecimal				shipmentId;
	private String					tabletShipmentId;
	private String					trackingNumber;

	public String getAlternateCarrier() {
		return this.alternateCarrier;
	}

	public String getCarrierName() {
		return this.carrierName;
	}

	public String getCustomerReference() {
		return this.customerReference;
	}

	public BigDecimal getDryIceWeight() {
		return this.dryIceWeight;
	}

	public BigDecimal getFreightCharge() {
		return this.freightCharge;
	}

	public String getFreightChargePaymentSource() {
		return this.freightChargePaymentSource;
	}

	public BigDecimal getGrossWeight() {
		return this.grossWeight;
	}

	public String getId() {
		return this.id;
	}

	public BigDecimal getPersonnelId() {
		return this.personnelId;
	}

	public Collection<PickingUnit> getPickingUnits() {
		return this.pickingUnits;
	}

	public Date getShipDate() {
		return this.shipDate;
	}

	public BigDecimal getShipmentId() {
		return this.shipmentId;
	}

	public String getTabletShipmentId() {
		return this.tabletShipmentId;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public boolean hasAlternateCarrier() {
		return StringUtils.isNotBlank(alternateCarrier);
	}

	public boolean hasCarrierName() {
		return StringUtils.isNotBlank(carrierName);
	}

	public boolean hasCustomerReference() {
		return StringUtils.isNotBlank(customerReference);
	}

	public boolean hasDryIceWeight() {
		return dryIceWeight != null;
	}

	public boolean hasFreightCharge() {
		return freightCharge != null;
	}

	public boolean hasFreightChargePaymentSource() {
		return StringUtils.isNotBlank(freightChargePaymentSource);
	}

	public boolean hasGrossWeight() {
		return grossWeight != null;
	}

	public boolean hasNumberOfPallets() {
		return numberOfPallets != null;
	}

	public boolean hasPersonnelId() {
		return personnelId != null;
	}

	public boolean hasPalletTrackingNumbers() {
		return StringUtils.isNotBlank(palletTrackingNumbers);
	}

	public boolean hasPickingUnits() {
		return pickingUnits != null && !pickingUnits.isEmpty();
	}

	public boolean hasShipDate() {
		return shipDate != null;
	}

	public boolean hasShipmentId() {
		return shipmentId != null;
	}

	public boolean hasTabletShipmentId() {
		return StringUtils.isNotBlank(tabletShipmentId);
	}

	public boolean hasTrackingNumber() {
		return StringUtils.isNotBlank(trackingNumber);
	}

	public boolean isErgChecked() {
		return this.ergChecked;
	}

	public boolean isErgCheckedSet() {
		return this.ergCheckedSet;
	}

	public boolean isPlacardsRequired() {
		return this.placardsRequired;
	}

	public boolean isPlacardsRequiredSet() {
		return this.placardsRequiredSet;
	}

	public boolean isPlacardsSupplied() {
		return this.placardsSupplied;
	}

	public boolean isValid() {
		return hasPickingUnits() && hasTabletShipmentId();
	}

	public void setAlternateCarrier(String alternateCarrier) {
		this.alternateCarrier = alternateCarrier;
	}

	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}

	public void setCustomerReference(String customerReference) {
		this.customerReference = customerReference;
	}

	public void setDryIceWeight(BigDecimal dryIceWeight) {
		this.dryIceWeight = dryIceWeight;
	}

	public void setErgChecked(boolean ergChecked) {
		ergCheckedSet = true;
		this.ergChecked = ergChecked;
	}

	public void setFreightCharge(BigDecimal freightCharge) {
		this.freightCharge = freightCharge;
	}

	public void setFreightChargePaymentSource(String freightChargePaymentSource) {
		this.freightChargePaymentSource = freightChargePaymentSource;
	}

	public void setGrossWeight(BigDecimal grossWeight) {
		this.grossWeight = grossWeight;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
	}

	public void setPickingUnits(Collection<PickingUnit> pickingUnits) {
		this.pickingUnits = pickingUnits;
	}

	public void setPlacardsRequired(boolean placardsRequired) {
		placardsRequiredSet = true;
		this.placardsRequired = placardsRequired;
	}

	public void setPlacardsSupplied(boolean placardsSupplied) {
		this.placardsSupplied = placardsSupplied;
	}

	public void setShipDate(Date shipDate) {
		this.shipDate = shipDate;
	}

	public void setShipmentId(BigDecimal shipmentId) {
		this.shipmentId = shipmentId;
	}

	public void setTabletShipmentId(String shipmentId) {
		this.tabletShipmentId = shipmentId;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public BigDecimal getNumberOfPallets() {
		return this.numberOfPallets;
	}

	public void setNumberOfPallets(BigDecimal numberOfPallets) {
		this.numberOfPallets = numberOfPallets;
	}

	public String getPalletTrackingNumbers() {
		return this.palletTrackingNumbers;
	}

	public void setPalletTrackingNumbers(String palletTrackingNumbers) {
		this.palletTrackingNumbers = palletTrackingNumbers;
	}

	public Collection<Sample> getSamples() {
		return this.samples == null ? Collections.emptyList() : this.samples;
	}

	public void setSamples(Collection<Sample> samples) {
		this.samples = samples;
	}
}