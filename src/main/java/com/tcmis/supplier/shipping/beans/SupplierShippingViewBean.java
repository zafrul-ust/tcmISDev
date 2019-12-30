package com.tcmis.supplier.shipping.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: SupplierShippingViewBean <br>
 * @version: 1.0, Oct 30, 2007 <br>
 *****************************************************************************/


public class SupplierShippingViewBean extends BaseDataBean {

	private String supplierParent;
	private String supplier;
	private String shipFromLocationId;
	private String shipFromLocationName;
	private String shipToCompanyId;
	private String shipToLocationId;
	private BigDecimal mrNumber;
	private String mrLineItem;
	private String catPartNo;
	private String requestorName;
	private String application;
	private String deliveryPoint;
	private String customerPo;
	private BigDecimal radianPo;
	private BigDecimal lineItem;
	private BigDecimal qtyOpen;
	private BigDecimal qtyDelivered;
	private Date dateDelivered;
	private String mfgLot;
	private Date vendorShipDate;
	private Date expireDate;
	private String expireDateString;
	private BigDecimal itemId;
	private Date expected;
	private BigDecimal qty;
	private String branchPlant;
	private String itemDescription;
	private String poNotes;
	private String transType;
	private String supplierName;
	private String indefiniteShelfLife;
	private String critical;
	private String originalMfgLot;
	private String inventoryGroup;
	private String manageKitsAsSingleUnit;
	private BigDecimal componentId;
	private BigDecimal shelfLifeDays;
	private String shelfLifeBasis;
	private BigDecimal materialId;
	private String packaging;
	private String materialDesc;
	private BigDecimal numberOfKits;
	private String locationShortName;
	private String locationDesc;
	private String addressLine1;
	private String addressLine2;
	private String addressLine3;
	private String city;
	private String stateAbbrev;
	private String zip;
	private String cityCommaState;
	private String containerLabel;
	private String dropShip;
	private BigDecimal poLine;
	private BigDecimal partsPerBox;
	private String requiresOverpack;
	private Date DOM;
	private String ok;
	private Date receiptDate;
	private BigDecimal priorityRating;
	private Date desiredShipDate;
	private Date desiredDeliveryDate;
	private String shippedAsSingle;
	private String deliveryTicket;
  private String oconus;
	private String supplierSalesOrderNo;
  private String packingGroupCount;
	private BigDecimal packingGroupId;
	private BigDecimal unitPrice;
	private String currencyId;
	private String rliShiptoLocId;
	private String originalTransactionType;
	private String originInspectionRequired;
	private String mfgDateRequired;
	private String trackSerialNumber;
	private BigDecimal availableInventoryQty;
	private String shiptoAddress;
	
	
	public String getMfgDateRequired() {
		return mfgDateRequired;
	}
	public void setMfgDateRequired(String mfgDateRequired) {
		this.mfgDateRequired = mfgDateRequired;
	}
	public String getTrackSerialNumber() {
		return trackSerialNumber;
	}
	public void setTrackSerialNumber(String trackSerialNumber) {
		this.trackSerialNumber = trackSerialNumber;
	}
	
  public String getRliShiptoLocId() {
    return rliShiptoLocId;
  }

  public void setRliShiptoLocId(String rliShiptoLocId) {
    this.rliShiptoLocId = rliShiptoLocId;
  }

  public BigDecimal getPackingGroupId() {
    return packingGroupId;
  }

  public void setPackingGroupId(BigDecimal packingGroupId) {
    this.packingGroupId = packingGroupId;
  }

  public String getPackingGroupCount() {
    return packingGroupCount;
  }

  public void setPackingGroupCount(String packingGroupCount) {
    this.packingGroupCount = packingGroupCount;
  }

  public String getDeliveryTicket() {
		return deliveryTicket;
	}

	public void setDeliveryTicket(String deliveryTicket) {
		this.deliveryTicket = deliveryTicket;
	}

public Date getDesiredDeliveryDate() {
		return desiredDeliveryDate;
	}

	public void setDesiredDeliveryDate(Date desiredDeliveryDate) {
		this.desiredDeliveryDate = desiredDeliveryDate;
	}

	public Date getDesiredShipDate() {
		return desiredShipDate;
	}

	public void setDesiredShipDate(Date desiredShipDate) {
		this.desiredShipDate = desiredShipDate;
	}

	//constructor
	public SupplierShippingViewBean() {
	}

	//setters
	public void setSupplierParent(String supplierParent) {
		this.supplierParent = supplierParent;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public void setShipFromLocationId(String shipFromLocationId) {
		this.shipFromLocationId = shipFromLocationId;
	}
	public void setShipFromLocationName(String shipFromLocationName) {
		this.shipFromLocationName = shipFromLocationName;
	}
	public void setShipToCompanyId(String shipToCompanyId) {
		this.shipToCompanyId = shipToCompanyId;
	}
	public void setShipToLocationId(String shipToLocationId) {
		this.shipToLocationId = shipToLocationId;
	}
	public void setMrNumber(BigDecimal mrNumber) {
		this.mrNumber = mrNumber;
	}
	public void setMrLineItem(String mrLineItem) {
		this.mrLineItem = mrLineItem;
	}
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}
	public void setRequestorName(String requestorName) {
		this.requestorName = requestorName;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public void setDeliveryPoint(String deliveryPoint) {
		this.deliveryPoint = deliveryPoint;
	}
	public void setCustomerPo(String customerPo) {
		this.customerPo = customerPo;
	}
	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}
	public void setLineItem(BigDecimal lineItem) {
		this.lineItem = lineItem;
	}
	public void setQtyOpen(BigDecimal qtyOpen) {
		this.qtyOpen = qtyOpen;
	}
	public void setQtyDelivered(BigDecimal qtyDelivered) {
		this.qtyDelivered = qtyDelivered;
	}
	public void setDateDelivered(Date dateDelivered) {
		this.dateDelivered = dateDelivered;
	}
	public void setMfgLot(String mfgLot) {
		this.mfgLot = mfgLot;
	}
	public void setVendorShipDate(Date vendorShipDate) {
		this.vendorShipDate = vendorShipDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setExpected(Date expected) {
		this.expected = expected;
	}
	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}
	public void setBranchPlant(String branchPlant) {
		this.branchPlant = branchPlant;
	}
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	public void setPoNotes(String poNotes) {
		this.poNotes = poNotes;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public void setIndefiniteShelfLife(String indefiniteShelfLife) {
		this.indefiniteShelfLife = indefiniteShelfLife;
	}
	public void setCritical(String critical) {
		this.critical = critical;
	}
	public void setOriginalMfgLot(String originalMfgLot) {
		this.originalMfgLot = originalMfgLot;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setManageKitsAsSingleUnit(String manageKitsAsSingleUnit) {
		this.manageKitsAsSingleUnit = manageKitsAsSingleUnit;
	}
	public void setComponentId(BigDecimal componentId) {
		this.componentId = componentId;
	}
	public void setShelfLifeDays(BigDecimal shelfLifeDays) {
		this.shelfLifeDays = shelfLifeDays;
	}
	public void setShelfLifeBasis(String shelfLifeBasis) {
		this.shelfLifeBasis = shelfLifeBasis;
	}
	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}
	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}
	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}
	public void setNumberOfKits(BigDecimal numberOfKits) {
		this.numberOfKits = numberOfKits;
	}
	public void setLocationShortName(String locationShortName) {
		this.locationShortName = locationShortName;
	}
	public void setLocationDesc(String locationDesc) {
		this.locationDesc = locationDesc;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setStateAbbrev(String stateAbbrev) {
		this.stateAbbrev = stateAbbrev;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public void setCityCommaState(String cityCommaState) {
		this.cityCommaState = cityCommaState;
	}
	public void setContainerLabel(String containerLabel) {
		this.containerLabel = containerLabel;
	}
	public void setDropShip(String dropShip) {
		this.dropShip = dropShip;
	}
	public void setPoLine(BigDecimal poLine) {
		this.poLine = poLine;
	}
	public void setPartsPerBox(BigDecimal partsPerBox) {
		this.partsPerBox = partsPerBox;
	}
	public void setRequiresOverpack(String requiresOverpack) {
		this.requiresOverpack = requiresOverpack;
	}
	public void setPriorityRating(BigDecimal priorityRating) {
		this.priorityRating = priorityRating;
	}
	public void setShippedAsSingle(String shippedAsSingle) {
		this.shippedAsSingle = shippedAsSingle;
	}
	public void setOconus(String oconus) {
		this.oconus = oconus;
	}
	public void setSupplierSalesOrderNo(String supplierSalesOrderNo) {
		this.supplierSalesOrderNo = supplierSalesOrderNo;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

  //getters
	public String getSupplierParent() {
		return supplierParent;
	}
	public String getSupplier() {
		return supplier;
	}
	public String getShipFromLocationId() {
		return shipFromLocationId;
	}
	public String getShipFromLocationName() {
		return shipFromLocationName;
	}
	public String getShipToCompanyId() {
		return shipToCompanyId;
	}
	public String getShipToLocationId() {
		return shipToLocationId;
	}
	public BigDecimal getMrNumber() {
		return mrNumber;
	}
	public String getMrLineItem() {
		return mrLineItem;
	}
	public String getCatPartNo() {
		return catPartNo;
	}
	public String getRequestorName() {
		return requestorName;
	}
	public String getApplication() {
		return application;
	}
	public String getDeliveryPoint() {
		return deliveryPoint;
	}
	public String getCustomerPo() {
		return customerPo;
	}
	public BigDecimal getRadianPo() {
		return radianPo;
	}
	public BigDecimal getLineItem() {
		return lineItem;
	}
	public BigDecimal getQtyOpen() {
		return qtyOpen;
	}
	public BigDecimal getQtyDelivered() {
		return qtyDelivered;
	}
	public Date getDateDelivered() {
		return dateDelivered;
	}
	public String getMfgLot() {
		return mfgLot;
	}
	public Date getVendorShipDate() {
		return vendorShipDate;
	}
	public Date getExpireDate() {
		return expireDate;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public Date getExpected() {
		return expected;
	}
	public BigDecimal getQty() {
		return qty;
	}
	public String getBranchPlant() {
		return branchPlant;
	}
	public String getItemDescription() {
		return itemDescription;
	}
	public String getPoNotes() {
		return poNotes;
	}
	public String getTransType() {
		return transType;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public String getIndefiniteShelfLife() {
		return indefiniteShelfLife;
	}
	public String getCritical() {
		return critical;
	}
	public String getOriginalMfgLot() {
		return originalMfgLot;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public String getManageKitsAsSingleUnit() {
		return manageKitsAsSingleUnit;
	}
	public BigDecimal getComponentId() {
		return componentId;
	}
	public BigDecimal getShelfLifeDays() {
		return shelfLifeDays;
	}
	public String getShelfLifeBasis() {
		return shelfLifeBasis;
	}
	public BigDecimal getMaterialId() {
		return materialId;
	}
	public String getPackaging() {
		return packaging;
	}
	public String getMaterialDesc() {
		return materialDesc;
	}
	public BigDecimal getNumberOfKits() {
		return numberOfKits;
	}
	public String getLocationShortName() {
		return locationShortName;
	}
	public String getLocationDesc() {
		return locationDesc;
	}
	public String getAddressLine1() {
		return addressLine1;
	}
	public String getAddressLine2() {
		return addressLine2;
	}
	public String getAddressLine3() {
		return addressLine3;
	}
	public String getCity() {
		return city;
	}
	public String getStateAbbrev() {
		return stateAbbrev;
	}
	public String getZip() {
		return zip;
	}
	public String getCityCommaState() {
		return cityCommaState;
	}
	public String getContainerLabel() {
		return containerLabel;
	}
	public String getDropShip() {
		return dropShip;
	}
	public BigDecimal getPoLine() {
		return poLine;
	}
	public BigDecimal getPartsPerBox() {
		return partsPerBox;
	}
	public String getRequiresOverpack() {
		return requiresOverpack;
	}
	public BigDecimal getPriorityRating() {
		return priorityRating;
	}
	public String getShippedAsSingle() {
		return shippedAsSingle;
	}
	public String getOconus() {
		return oconus;
	}
  public String getSupplierSalesOrderNo() {
		return supplierSalesOrderNo;
	}
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public String getCurrencyId() {
		return currencyId;
	}
  
  public Date getDOM() {
		return DOM;
	}

	public void setDOM(Date dom) {
		DOM = dom;
	}

	public String getOk() {
		return ok;
	}

	public void setOk(String ok) {
		this.ok = ok;
	}

	public Date getReceiptDate() {
		return receiptDate;
	}

	public void setReceiptDate(Date receiptDate) {
		this.receiptDate = receiptDate;
	}

	public String getExpireDateString() {
		return expireDateString;
	}

	public void setExpireDateString(String expireDateString) {
		this.expireDateString = expireDateString;
	}

	public String getOriginalTransactionType() {
		return originalTransactionType;
	}

	public void setOriginalTransactionType(String originalTransactionType) {
		this.originalTransactionType = originalTransactionType;
	}

	public String getOriginInspectionRequired() {
		return originInspectionRequired;
	}

	public void setOriginInspectionRequired(String originInspectionRequired) {
		this.originInspectionRequired = originInspectionRequired;
	}
	public BigDecimal getAvailableInventoryQty() {
		return availableInventoryQty;
	}
	public void setAvailableInventoryQty(BigDecimal availableInventoryQty) {
		this.availableInventoryQty = availableInventoryQty;
	}
	public String getShiptoAddress() {
		return shiptoAddress;
	}
	public void setShiptoAddress(String shiptoAddress) {
		this.shiptoAddress = shiptoAddress;
	}
}