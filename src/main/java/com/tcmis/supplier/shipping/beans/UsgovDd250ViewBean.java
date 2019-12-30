package com.tcmis.supplier.shipping.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: UsgovDd250ViewBean <br>
 * @version: 1.0, Dec 1, 2007 <br>
 *****************************************************************************/


public class UsgovDd250ViewBean extends BaseDataBean {

	private String inventoryGroup;
	private BigDecimal prNumber;
	private String lineItem;
	private String itemNo;
	private String catPartNo;
	private String partShortName;
	private String shippingContainers;
	private BigDecimal quantity;
	private String unitOfSale;
	private BigDecimal unitPrice;
	private BigDecimal amount;
	private String acceptancePoint;
	private String shipDate;
	private String shipmentNumber;
	private String carrierName;
	private String trackingNumber;
	private String tcn;
	private String primeContractor;
	private String primeContractorDodaac;
	private String primeContractorLine1;
	private String primeContractorLine2;
	private String primeContractorLine3;
	private String contractNumber;
	private String orderNumber;
	private String page;
	private String numberOfPages;
	private String shipFromDodaac;
	private String shipFromCompanyId;
	private String shipFromLocationId;
	private String igdCompanyId;
	private String igdLocationId;
	private String shipFromLine1;
	private String shipFromLine2;
	private String shipFromLine3;
	private String shipFromLine4;
	private String shipToDodaac;
	private String shipToCompanyId;
	private String shipToLocationId;
	private String shipToLine1;
	private String shipToLine2;
	private String shipToLine3;
	private String shipToLine4;
	private String shipViaDodaac;
	private String shipViaCompanyId;
	private String shipViaLocationId;
	private String shipViaLine1;
	private String shipViaLine2;
	private String shipViaLine3;
	private String shipViaLine4;
	private String adminDodaac;
	private String adminLine1;
	private String adminLine2;
	private String adminLine3;
	private String payerDodaac;
	private String payerLine1;
	private String payerLine2;
	private String payerLine3;
	private String deliveryTicket;
  private String notes;
	private String originInspectionRequired;
  	private BigDecimal packingGroupId;
    private String originalTransactionType;

  //constructor
	public UsgovDd250ViewBean() {
	}

	//setters
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}
	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}
	public void setPartShortName(String partShortName) {
		this.partShortName = partShortName;
	}
	public void setShippingContainers(String shippingContainers) {
		this.shippingContainers = shippingContainers;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public void setUnitOfSale(String unitOfSale) {
		this.unitOfSale = unitOfSale;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public void setAcceptancePoint(String acceptancePoint) {
		this.acceptancePoint = acceptancePoint;
	}
	public void setShipDate(String shipDate) {
		this.shipDate = shipDate;
	}
	public void setShipmentNumber(String shipmentNumber) {
		this.shipmentNumber = shipmentNumber;
	}
	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}
	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}
	public void setTcn(String tcn) {
		this.tcn = tcn;
	}
	public void setPrimeContractor(String primeContractor) {
		this.primeContractor = primeContractor;
	}
	public void setPrimeContractorDodaac(String primeContractorDodaac) {
		this.primeContractorDodaac = primeContractorDodaac;
	}
	public void setPrimeContractorLine1(String primeContractorLine1) {
		this.primeContractorLine1 = primeContractorLine1;
	}
	public void setPrimeContractorLine2(String primeContractorLine2) {
		this.primeContractorLine2 = primeContractorLine2;
	}
	public void setPrimeContractorLine3(String primeContractorLine3) {
		this.primeContractorLine3 = primeContractorLine3;
	}
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public void setNumberOfPages(String numberOfPages) {
		this.numberOfPages = numberOfPages;
	}
	public void setShipFromDodaac(String shipFromDodaac) {
		this.shipFromDodaac = shipFromDodaac;
	}
	public void setShipFromCompanyId(String shipFromCompanyId) {
		this.shipFromCompanyId = shipFromCompanyId;
	}
	public void setShipFromLocationId(String shipFromLocationId) {
		this.shipFromLocationId = shipFromLocationId;
	}
	public void setIgdCompanyId(String igdCompanyId) {
		this.igdCompanyId = igdCompanyId;
	}
	public void setIgdLocationId(String igdLocationId) {
		this.igdLocationId = igdLocationId;
	}
	public void setShipFromLine1(String shipFromLine1) {
		this.shipFromLine1 = shipFromLine1;
	}
	public void setShipFromLine2(String shipFromLine2) {
		this.shipFromLine2 = shipFromLine2;
	}
	public void setShipFromLine3(String shipFromLine3) {
		this.shipFromLine3 = shipFromLine3;
	}
	public void setShipFromLine4(String shipFromLine4) {
		this.shipFromLine4 = shipFromLine4;
	}
	public void setShipToDodaac(String shipToDodaac) {
		this.shipToDodaac = shipToDodaac;
	}
	public void setShipToCompanyId(String shipToCompanyId) {
		this.shipToCompanyId = shipToCompanyId;
	}
	public void setShipToLocationId(String shipToLocationId) {
		this.shipToLocationId = shipToLocationId;
	}
	public void setShipToLine1(String shipToLine1) {
		this.shipToLine1 = shipToLine1;
	}
	public void setShipToLine2(String shipToLine2) {
		this.shipToLine2 = shipToLine2;
	}
	public void setShipToLine3(String shipToLine3) {
		this.shipToLine3 = shipToLine3;
	}
	public void setShipToLine4(String shipToLine4) {
		this.shipToLine4 = shipToLine4;
	}
	public void setShipViaDodaac(String shipViaDodaac) {
		this.shipViaDodaac = shipViaDodaac;
	}
	public void setShipViaCompanyId(String shipViaCompanyId) {
		this.shipViaCompanyId = shipViaCompanyId;
	}
	public void setShipViaLocationId(String shipViaLocationId) {
		this.shipViaLocationId = shipViaLocationId;
	}
	public void setShipViaLine1(String shipViaLine1) {
		this.shipViaLine1 = shipViaLine1;
	}
	public void setShipViaLine2(String shipViaLine2) {
		this.shipViaLine2 = shipViaLine2;
	}
	public void setShipViaLine3(String shipViaLine3) {
		this.shipViaLine3 = shipViaLine3;
	}
	public void setShipViaLine4(String shipViaLine4) {
		this.shipViaLine4 = shipViaLine4;
	}
	public void setAdminDodaac(String adminDodaac) {
		this.adminDodaac = adminDodaac;
	}
	public void setAdminLine1(String adminLine1) {
		this.adminLine1 = adminLine1;
	}
	public void setAdminLine2(String adminLine2) {
		this.adminLine2 = adminLine2;
	}
	public void setAdminLine3(String adminLine3) {
		this.adminLine3 = adminLine3;
	}
	public void setPayerDodaac(String payerDodaac) {
		this.payerDodaac = payerDodaac;
	}
	public void setPayerLine1(String payerLine1) {
		this.payerLine1 = payerLine1;
	}
	public void setPayerLine2(String payerLine2) {
		this.payerLine2 = payerLine2;
	}
	public void setPayerLine3(String payerLine3) {
		this.payerLine3 = payerLine3;
	}
	public void setDeliveryTicket(String deliveryTicket) {
		this.deliveryTicket = deliveryTicket;
	}
  public void setNotes(String notes) {
    this.notes = notes;
  }
	public void setOriginInspectionRequired(String originInspectionRequired) {
		this.originInspectionRequired = originInspectionRequired;
	}
    public void setPackingGroupId(BigDecimal packingGroupId) {
        this.packingGroupId = packingGroupId;
    }

    //getters
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public BigDecimal getPrNumber() {
		return prNumber;
	}
	public String getLineItem() {
		return lineItem;
	}
	public String getItemNo() {
		return itemNo;
	}
	public String getCatPartNo() {
		return catPartNo;
	}
	public String getPartShortName() {
		return partShortName;
	}
	public String getShippingContainers() {
		return shippingContainers;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public String getUnitOfSale() {
		return unitOfSale;
	}
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public String getAcceptancePoint() {
		return acceptancePoint;
	}
	public String getShipDate() {
		return shipDate;
	}
	public String getShipmentNumber() {
		return shipmentNumber;
	}
	public String getCarrierName() {
		return carrierName;
	}
	public String getTrackingNumber() {
		return trackingNumber;
	}
	public String getTcn() {
		return tcn;
	}
	public String getPrimeContractor() {
		return primeContractor;
	}
	public String getPrimeContractorDodaac() {
		return primeContractorDodaac;
	}
	public String getPrimeContractorLine1() {
		return primeContractorLine1;
	}
	public String getPrimeContractorLine2() {
		return primeContractorLine2;
	}
	public String getPrimeContractorLine3() {
		return primeContractorLine3;
	}
	public String getContractNumber() {
		return contractNumber;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public String getPage() {
		return page;
	}
	public String getNumberOfPages() {
		return numberOfPages;
	}
	public String getShipFromDodaac() {
		return shipFromDodaac;
	}
	public String getShipFromCompanyId() {
		return shipFromCompanyId;
	}
	public String getShipFromLocationId() {
		return shipFromLocationId;
	}
	public String getIgdCompanyId() {
		return igdCompanyId;
	}
	public String getIgdLocationId() {
		return igdLocationId;
	}
	public String getShipFromLine1() {
		return shipFromLine1;
	}
	public String getShipFromLine2() {
		return shipFromLine2;
	}
	public String getShipFromLine3() {
		return shipFromLine3;
	}
	public String getShipFromLine4() {
		return shipFromLine4;
	}
	public String getShipToDodaac() {
		return shipToDodaac;
	}
	public String getShipToCompanyId() {
		return shipToCompanyId;
	}
	public String getShipToLocationId() {
		return shipToLocationId;
	}
	public String getShipToLine1() {
		return shipToLine1;
	}
	public String getShipToLine2() {
		return shipToLine2;
	}
	public String getShipToLine3() {
		return shipToLine3;
	}
	public String getShipToLine4() {
		return shipToLine4;
	}
	public String getShipViaDodaac() {
		return shipViaDodaac;
	}
	public String getShipViaCompanyId() {
		return shipViaCompanyId;
	}
	public String getShipViaLocationId() {
		return shipViaLocationId;
	}
	public String getShipViaLine1() {
		return shipViaLine1;
	}
	public String getShipViaLine2() {
		return shipViaLine2;
	}
	public String getShipViaLine3() {
		return shipViaLine3;
	}
	public String getShipViaLine4() {
		return shipViaLine4;
	}
	public String getAdminDodaac() {
		return adminDodaac;
	}
	public String getAdminLine1() {
		return adminLine1;
	}
	public String getAdminLine2() {
		return adminLine2;
	}
	public String getAdminLine3() {
		return adminLine3;
	}
	public String getPayerDodaac() {
		return payerDodaac;
	}
	public String getPayerLine1() {
		return payerLine1;
	}
	public String getPayerLine2() {
		return payerLine2;
	}
	public String getPayerLine3() {
		return payerLine3;
	}
  public String getDeliveryTicket() {
		return deliveryTicket;
	}
  public String getNotes() {
    return notes;
  }
	public String getOriginInspectionRequired() {
		return originInspectionRequired;
	}
    public BigDecimal getPackingGroupId() {
        return packingGroupId;
    }

    public String getOriginalTransactionType() {
        return originalTransactionType;
    }

    public void setOriginalTransactionType(String originalTransactionType) {
        this.originalTransactionType = originalTransactionType;
    }
}