package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: BillOfLadingViewBean <br>
 * 
 * @version: 1.0, Feb 2, 2018 <br>
 *****************************************************************************/

public class BillOfLadingViewBean extends BaseDataBean {

	private BigDecimal	picklistId;
	private int			totalQuantity;
	private int			quantityShipped;
	private String		packaging;
	private boolean		hazardous;
	private boolean		dot;
	private boolean		erg;
	private BigDecimal	itemId;
	private String		mfgLot;
	private Date		dateOfReceipt;
	private BigDecimal	radianPo;
	private String		expireDate;
	private BigDecimal	pr_number;
	private String		lineItem;
	private String		mrLine;
	private BigDecimal	receiptId;
	private BigDecimal	salesOrder;
	private String		shipperId;
	private String		carrier;
	private String		carrierNo;
	private String		requiredDatetime;
	private String		shipDate;
	private String		sourceHub;
	private String		taxId;
	private String		endUser;
	private String		department;
	private String		application;
	private String		applicationDesc;
	private String		deliveryPoint;
	private String		deliveryPointDesc;
	private BigDecimal	batch;
	private BigDecimal	batchId;
	private String		shipToLocationId;
	private String		hubLocationId;
	private String		shipToCountryAbbrev;
	private String		shipToStateAbbrev;
	private String		shipToAddressLine1;
	private String		shipToAddressLine2;
	private String		shipToAddressLine3;
	private String		shipToAddressLine4;
	private String		shipToCity;
	private String		shipToZip;
	private String		shipToLocationDesc;
	private String		hubCountryAbbrev;
	private String		hubStateAbbrev;
	private String		hubAddressLine1;
	private String		hubAddressLine2;
	private String		hubAddressLine3;
	private String		hubAddressLine4;
	private String		hubCity;
	private String		hubZip;
	private String		hubLocationDesc;
	private String		poNumber;
	private String		poReleaseNumber;
	private String		catPartNo;
	private String		catalogId;
	private String		itemDesc;
	private String		chargeNumber;
	private String		billToAddress1;
	private String		billToAddress2;
	private String		billToAddress3;
	private String		billToAddress4;
	private String		billToAddress5;
	private boolean		oconus;
	private String		trackingNumber;
	private BigDecimal	grossWeightLb;
	private BigDecimal	netWeightLb;
	private String		companyId;
	private String		freightOrderNumber;
	private String		shippingReference;
	private String		shipToAddress;
	private String		inventoryGroup;
	private int			numberOfBoxes;
	
	// constructor
	public BillOfLadingViewBean() {
	}
		
	public BigDecimal getPicklistId() {
		return picklistId;
	}
	public int getTotalQuantity() {
		return totalQuantity;
	}
	public int getQuantityShipped() {
		return quantityShipped;
	}
	public String getPackaging() {
		return packaging;
	}
	public boolean isHazardous() {
		return hazardous;
	}
	public boolean isDot() {
		return dot;
	}
	public boolean isErg() {
		return erg;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public String getMfgLot() {
		return mfgLot;
	}
	public Date getDateOfReceipt() {
		return dateOfReceipt;
	}
	public BigDecimal getRadianPo() {
		return radianPo;
	}
	public String getExpireDate() {
		return expireDate;
	}
	public BigDecimal getPr_number() {
		return pr_number;
	}
	public String getLineItem() {
		return lineItem;
	}
	public String getMrLine() {
		return mrLine;
	}
	public BigDecimal getReceiptId() {
		return receiptId;
	}
	public BigDecimal getSalesOrder() {
		return salesOrder;
	}
	public String getShipperId() {
		return shipperId;
	}
	public String getCarrier() {
		return carrier;
	}
	public String getCarrierNo() {
		return carrierNo;
	}
	public String getRequiredDatetime() {
		return requiredDatetime;
	}
	public String getShipDate() {
		return shipDate;
	}
	public String getSourceHub() {
		return sourceHub;
	}
	public String getTaxId() {
		return taxId;
	}
	public String getEndUser() {
		return endUser;
	}
	public String getDepartment() {
		return department;
	}
	public String getApplication() {
		return application;
	}
	public String getApplicationDesc() {
		return applicationDesc;
	}
	public String getDeliveryPoint() {
		return deliveryPoint;
	}
	public String getDeliveryPointDesc() {
		return deliveryPointDesc;
	}
	public BigDecimal getBatch() {
		return batch;
	}
	public BigDecimal getBatchId() {
		return batchId;
	}
	public String getShipToLocationId() {
		return shipToLocationId;
	}
	public String getHubLocationId() {
		return hubLocationId;
	}
	public String getShipToCountryAbbrev() {
		return shipToCountryAbbrev;
	}
	public String getShipToStateAbbrev() {
		return shipToStateAbbrev;
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
	public String getShipToCity() {
		return shipToCity;
	}
	public String getShipToZip() {
		return shipToZip;
	}
	public String getShipToLocationDesc() {
		return shipToLocationDesc;
	}
	public String getHubCountryAbbrev() {
		return hubCountryAbbrev;
	}
	public String getHubStateAbbrev() {
		return hubStateAbbrev;
	}
	public String getHubAddressLine1() {
		return hubAddressLine1;
	}
	public String getHubAddressLine2() {
		return hubAddressLine2;
	}
	public String getHubAddressLine3() {
		return hubAddressLine3;
	}
	public String getHubAddressLine4() {
		return hubAddressLine4;
	}
	public String getHubCity() {
		return hubCity;
	}
	public String getHubZip() {
		return hubZip;
	}
	public String getHubLocationDesc() {
		return hubLocationDesc;
	}
	public String getPoNumber() {
		return poNumber;
	}
	public String getPoReleaseNumber() {
		return poReleaseNumber;
	}
	public String getCatPartNo() {
		return catPartNo;
	}
	public String getCatalogId() {
		return catalogId;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public String getChargeNumber() {
		return chargeNumber;
	}
	public String getBillToAddress1() {
		return billToAddress1;
	}
	public String getBillToAddress2() {
		return billToAddress2;
	}
	public String getBillToAddress3() {
		return billToAddress3;
	}
	public String getBillToAddress4() {
		return billToAddress4;
	}
	public String getBillToAddress5() {
		return billToAddress5;
	}
	public boolean isOconus() {
		return oconus;
	}
	public String getTrackingNumber() {
		return trackingNumber;
	}
	public BigDecimal getGrossWeightLb() {
		return grossWeightLb;
	}
	public BigDecimal getNetWeightLb() {
		return netWeightLb;
	}
	public String getCompanyId() {
		return companyId;
	}
	public String getFreightOrderNumber() {
		return freightOrderNumber;
	}
	public String getShippingReference() {
		return shippingReference;
	}
	public String getShipToAddress() {
		return shipToAddress;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public int getNumberOfBoxes() {
		return numberOfBoxes;
	}
	public void setPicklistId(BigDecimal picklistId) {
		this.picklistId = picklistId;
	}
	public void setTotalQuantity(int totalQuantity) {
		this.totalQuantity = totalQuantity;
	}
	public void setQuantityShipped(int quantityShipped) {
		this.quantityShipped = quantityShipped;
	}
	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}
	public void setHazardous(boolean hazardous) {
		this.hazardous = hazardous;
	}
	public void setDot(boolean dot) {
		this.dot = dot;
	}
	public void setErg(boolean erg) {
		this.erg = erg;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setMfgLot(String mfgLot) {
		this.mfgLot = mfgLot;
	}
	public void setDateOfReceipt(Date dateOfReceipt) {
		this.dateOfReceipt = dateOfReceipt;
	}
	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}
	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}
	public void setPr_number(BigDecimal pr_number) {
		this.pr_number = pr_number;
	}
	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}
	public void setMrLine(String mrLine) {
		this.mrLine = mrLine;
	}
	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}
	public void setSalesOrder(BigDecimal salesOrder) {
		this.salesOrder = salesOrder;
	}
	public void setShipperId(String shipperId) {
		this.shipperId = shipperId;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	public void setCarrierNo(String carrierNo) {
		this.carrierNo = carrierNo;
	}
	public void setRequiredDatetime(String requiredDatetime) {
		this.requiredDatetime = requiredDatetime;
	}
	public void setShipDate(String shipDate) {
		this.shipDate = shipDate;
	}
	public void setSourceHub(String sourceHub) {
		this.sourceHub = sourceHub;
	}
	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}
	public void setEndUser(String endUser) {
		this.endUser = endUser;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public void setApplicationDesc(String applicationDesc) {
		this.applicationDesc = applicationDesc;
	}
	public void setDeliveryPoint(String deliveryPoint) {
		this.deliveryPoint = deliveryPoint;
	}
	public void setDeliveryPointDesc(String deliveryPointDesc) {
		this.deliveryPointDesc = deliveryPointDesc;
	}
	public void setBatch(BigDecimal batch) {
		this.batch = batch;
	}
	public void setBatchId(BigDecimal batchId) {
		this.batchId = batchId;
	}
	public void setShipToLocationId(String shipToLocationId) {
		this.shipToLocationId = shipToLocationId;
	}
	public void setHubLocationId(String hubLocationId) {
		this.hubLocationId = hubLocationId;
	}
	public void setShipToCountryAbbrev(String shipToCountryAbbrev) {
		this.shipToCountryAbbrev = shipToCountryAbbrev;
	}
	public void setShipToStateAbbrev(String shipToStateAbbrev) {
		this.shipToStateAbbrev = shipToStateAbbrev;
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
	public void setShipToCity(String shipToCity) {
		this.shipToCity = shipToCity;
	}
	public void setShipToZip(String shipToZip) {
		this.shipToZip = shipToZip;
	}
	public void setShipToLocationDesc(String shipToLocationDesc) {
		this.shipToLocationDesc = shipToLocationDesc;
	}
	public void setHubCountryAbbrev(String hubCountryAbbrev) {
		this.hubCountryAbbrev = hubCountryAbbrev;
	}
	public void setHubStateAbbrev(String hubStateAbbrev) {
		this.hubStateAbbrev = hubStateAbbrev;
	}
	public void setHubAddressLine1(String hubAddressLine1) {
		this.hubAddressLine1 = hubAddressLine1;
	}
	public void setHubAddressLine2(String hubAddressLine2) {
		this.hubAddressLine2 = hubAddressLine2;
	}
	public void setHubAddressLine3(String hubAddressLine3) {
		this.hubAddressLine3 = hubAddressLine3;
	}
	public void setHubAddressLine4(String hubAddressLine4) {
		this.hubAddressLine4 = hubAddressLine4;
	}
	public void setHubCity(String hubCity) {
		this.hubCity = hubCity;
	}
	public void setHubZip(String hubZip) {
		this.hubZip = hubZip;
	}
	public void setHubLocationDesc(String hubLocationDesc) {
		this.hubLocationDesc = hubLocationDesc;
	}
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	public void setPoReleaseNumber(String poReleaseNumber) {
		this.poReleaseNumber = poReleaseNumber;
	}
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public void setChargeNumber(String chargeNumber) {
		this.chargeNumber = chargeNumber;
	}
	public void setBillToAddress1(String billToAddress1) {
		this.billToAddress1 = billToAddress1;
	}
	public void setBillToAddress2(String billToAddress2) {
		this.billToAddress2 = billToAddress2;
	}
	public void setBillToAddress3(String billToAddress3) {
		this.billToAddress3 = billToAddress3;
	}
	public void setBillToAddress4(String billToAddress4) {
		this.billToAddress4 = billToAddress4;
	}
	public void setBillToAddress5(String billToAddress5) {
		this.billToAddress5 = billToAddress5;
	}
	public void setOconus(boolean oconus) {
		this.oconus = oconus;
	}
	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}
	public void setGrossWeightLb(BigDecimal grossWeightLb) {
		this.grossWeightLb = grossWeightLb;
	}
	public void setNetWeightLb(BigDecimal netWeightLb) {
		this.netWeightLb = netWeightLb;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setFreightOrderNumber(String freightOrderNumber) {
		this.freightOrderNumber = freightOrderNumber;
	}
	public void setShippingReference(String shippingReference) {
		this.shippingReference = shippingReference;
	}
	public void setShipToAddress(String shipToAddress) {
		this.shipToAddress = shipToAddress;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setNumberOfBoxes(int numberOfBoxes) {
		this.numberOfBoxes = numberOfBoxes;
	}
}