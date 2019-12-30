package com.tcmis.client.ups.beans;

import com.tcmis.common.framework.BaseDataBean;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;


/******************************************************************************
 * CLASSNAME: BoxShipmentSetupBean <br>
 * @version: 1.0, Jan 28, 2008 <br>
 *****************************************************************************/


public class BoxShipmentSetupBean extends BaseDataBean {

	private String boxId;
	private String trackingNumber;
	private String carrierName;
	private String deliveryTicket;
	private Date dateDelivered;
	private Date dateShipped;
	private String shipToCompanyId;
	private String shipToLocationId;
	private String inventoryGroup;
	private String supplier;
	private String shipFromLocationId;
	private BigDecimal shippingBatchId;
	private BigDecimal shipmentId;
	private String usgovShipmentNumber;
	private String companyId;
	private String customerPoNo;
	private String usgovTcn;
	private BigDecimal carrierTransportCharges;
	private BigDecimal carrierServiceCharges;
	private BigDecimal carrierTotalCharges;
	private BigDecimal carrierBillingWeight;
	private String carrierLabelFilename;
	private String carrierControlLogRcpt;
	private BigDecimal carrierBoxServiceCharge;
	private String carrierShipTrackingNum;
	private File carrierLabel;


	//constructor
	public BoxShipmentSetupBean() {
	}

	//setters
	public void setBoxId(String boxId) {
		this.boxId = boxId;
	}
	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}
	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}
	public void setDeliveryTicket(String deliveryTicket) {
		this.deliveryTicket = deliveryTicket;
	}
	public void setDateDelivered(Date dateDelivered) {
		this.dateDelivered = dateDelivered;
	}
	public void setDateShipped(Date dateShipped) {
		this.dateShipped = dateShipped;
	}
	public void setShipToCompanyId(String shipToCompanyId) {
		this.shipToCompanyId = shipToCompanyId;
	}
	public void setShipToLocationId(String shipToLocationId) {
		this.shipToLocationId = shipToLocationId;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public void setShipFromLocationId(String shipFromLocationId) {
		this.shipFromLocationId = shipFromLocationId;
	}
	public void setShippingBatchId(BigDecimal shippingBatchId) {
		this.shippingBatchId = shippingBatchId;
	}
	public void setShipmentId(BigDecimal shipmentId) {
		this.shipmentId = shipmentId;
	}
	public void setUsgovShipmentNumber(String usgovShipmentNumber) {
		this.usgovShipmentNumber = usgovShipmentNumber;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setCustomerPoNo(String customerPoNo) {
		this.customerPoNo = customerPoNo;
	}
	public void setUsgovTcn(String usgovTcn) {
		this.usgovTcn = usgovTcn;
	}
	public void setCarrierTransportCharges(BigDecimal carrierTransportCharges) {
		this.carrierTransportCharges = carrierTransportCharges;
	}
	public void setCarrierServiceCharges(BigDecimal carrierServiceCharges) {
		this.carrierServiceCharges = carrierServiceCharges;
	}
	public void setCarrierTotalCharges(BigDecimal carrierTotalCharges) {
		this.carrierTotalCharges = carrierTotalCharges;
	}
	public void setCarrierBillingWeight(BigDecimal carrierBillingWeight) {
		this.carrierBillingWeight = carrierBillingWeight;
	}
	public void setCarrierLabelFilename(String carrierLabelFilename) {
		this.carrierLabelFilename = carrierLabelFilename;
	}
	public void setCarrierControlLogRcpt(String carrierControlLogRcpt) {
		this.carrierControlLogRcpt = carrierControlLogRcpt;
	}
	public void setCarrierBoxServiceCharge(BigDecimal carrierBoxServiceCharge) {
		this.carrierBoxServiceCharge = carrierBoxServiceCharge;
	}
	public void setCarrierShipTrackingNum(String carrierShipTrackingNum) {
		this.carrierShipTrackingNum = carrierShipTrackingNum;
	}
	public void setCarrierLabel(File carrierLabel) {
		this.carrierLabel = carrierLabel;
	}


	//getters
	public String getBoxId() {
		return boxId;
	}
	public String getTrackingNumber() {
		return trackingNumber;
	}
	public String getCarrierName() {
		return carrierName;
	}
	public String getDeliveryTicket() {
		return deliveryTicket;
	}
	public Date getDateDelivered() {
		return dateDelivered;
	}
	public Date getDateShipped() {
		return dateShipped;
	}
	public String getShipToCompanyId() {
		return shipToCompanyId;
	}
	public String getShipToLocationId() {
		return shipToLocationId;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public String getSupplier() {
		return supplier;
	}
	public String getShipFromLocationId() {
		return shipFromLocationId;
	}
	public BigDecimal getShippingBatchId() {
		return shippingBatchId;
	}
	public BigDecimal getShipmentId() {
		return shipmentId;
	}
	public String getUsgovShipmentNumber() {
		return usgovShipmentNumber;
	}
	public String getCompanyId() {
		return companyId;
	}
	public String getCustomerPoNo() {
		return customerPoNo;
	}
	public String getUsgovTcn() {
		return usgovTcn;
	}
	public BigDecimal getCarrierTransportCharges() {
		return carrierTransportCharges;
	}
	public BigDecimal getCarrierServiceCharges() {
		return carrierServiceCharges;
	}
	public BigDecimal getCarrierTotalCharges() {
		return carrierTotalCharges;
	}
	public BigDecimal getCarrierBillingWeight() {
		return carrierBillingWeight;
	}
	public String getCarrierLabelFilename() {
		return carrierLabelFilename;
	}
	public String getCarrierControlLogRcpt() {
		return carrierControlLogRcpt;
	}
	public BigDecimal getCarrierBoxServiceCharge() {
		return carrierBoxServiceCharge;
	}
	public String getCarrierShipTrackingNum() {
		return carrierShipTrackingNum;
	}
	public File getCarrierLabel() {
		return carrierLabel;
	}
}