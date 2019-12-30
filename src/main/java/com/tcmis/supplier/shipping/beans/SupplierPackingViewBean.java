package com.tcmis.supplier.shipping.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: SupplierPackingViewBean <br>
 * @version: 1.0, Nov 21, 2007 <br>
 *****************************************************************************/


public class SupplierPackingViewBean extends BaseDataBean {

	private String companyId;
	private String supplier;
	private String shipFromLocationId;
	private String shipFromLocationName;
	private String shipToLocationName;
	private String shipToCityCommaState;
	private String shipToZipcode;
	private BigDecimal picklistId;
	private Date picklistPrintDate;
	private String shipToLocationId;
	private String ultimateShipToDodaac;
	private BigDecimal prNumber;
	private String lineItem;
	private BigDecimal radianPo;
	private BigDecimal poLine;
	private String catPartNo;
	private String partShortName;
	private BigDecimal itemId;
	private String itemDesc;
	private String packaging;
	private BigDecimal boxLabelId;
	private BigDecimal quantity;
	private BigDecimal numberOfBoxes;
//	private String expireDate;
	private Date expireDate;
	private BigDecimal issueId;
	private String boxId;
	private String palletId;
	private BigDecimal shipmentId;
	private String trackingNumber;
	private String carrierCode;
	private String carrierName;
	private String deliveryTicket;
	private Date dateDelivered;
	private Date dateShipped;
	private String ok;
	private Date desiredShipDate;
	private Date desiredDeliveryDate;
	private String shippedAsSingle;
	private String requiresOverpack;
	private BigDecimal packingGroupId;
	private String consolidationNumber;
	private String oconus;
	private String supplierSalesOrderNo;
	private String rliShiptoLocId;
	private Date shipConfirmDate;
	private String milstripCode;
	private String inOutboundAsn;
	private String originInspectionRequired;
	private String usgovTcn;
	private String serialNumber;
	private String trackSerialNumber;
	private BigDecimal customerOrderQty;
	private String customerPoNo;
	private String mfgDateRequired;
	private String shiptoAddress;
	private String mrCompanyId;
	private BigDecimal orderQuantity;
  
  public String getTrackSerialNumber() {
		return trackSerialNumber;
	}

	public void setTrackSerialNumber(String trackSerialNumber) {
		this.trackSerialNumber = trackSerialNumber;
	}

  public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
  
  public String getRliShiptoLocId() {
		return rliShiptoLocId;
	}

	public void setRliShiptoLocId(String rliShiptoLocId) {
		this.rliShiptoLocId = rliShiptoLocId;
	}

	public String getConsolidationNumber() {
		return consolidationNumber;
	}

	public void setConsolidationNumber(String consolidationNumber) {
		this.consolidationNumber = consolidationNumber;
	}

	public String getOk() {
		return ok;
	}

	public void setOk(String ok) {
		this.ok = ok;
	}

	//constructor
	public SupplierPackingViewBean() {
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
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
	public void setShipToLocationName(String shipToLocationName) {
		this.shipToLocationName = shipToLocationName;
	}
	public void setShipToCityCommaState(String shipToCityCommaState) {
		this.shipToCityCommaState = shipToCityCommaState;
	}
	public void setShipToZipcode(String shipToZipcode) {
		this.shipToZipcode = shipToZipcode;
	}
	public void setPicklistId(BigDecimal picklistId) {
		this.picklistId = picklistId;
	}
	public void setPicklistPrintDate(Date picklistPrintDate) {
		this.picklistPrintDate = picklistPrintDate;
	}
	public void setShipToLocationId(String shipToLocationId) {
		this.shipToLocationId = shipToLocationId;
	}
	public void setUltimateShipToDodaac(String ultimateShipToDodaac) {
		this.ultimateShipToDodaac = ultimateShipToDodaac;
	}
	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}
	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}
	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}
	public void setPoLine(BigDecimal poLine) {
		this.poLine = poLine;
	}
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}
	public void setPartShortName(String partShortName) {
		this.partShortName = partShortName;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}
	public void setBoxLabelId(BigDecimal boxLabelId) {
		this.boxLabelId = boxLabelId;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public void setNumberOfBoxes(BigDecimal numberOfBoxes) {
		this.numberOfBoxes = numberOfBoxes;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	public void setIssueId(BigDecimal issueId) {
		this.issueId = issueId;
	}
	public void setBoxId(String boxId) {
		this.boxId = boxId;
	}
	public void setPalletId(String palletId) {
		this.palletId = palletId;
	}
	public void setShipmentId(BigDecimal shipmentId) {
		this.shipmentId = shipmentId;
	}
	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
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
	public void setDesiredDeliveryDate(Date desiredDeliveryDate) {
		this.desiredDeliveryDate = desiredDeliveryDate;
	}
	public void setDesiredShipDate(Date desiredShipDate) {
		this.desiredShipDate = desiredShipDate;
	}
	public void setShippedAsSingle(String shippedAsSingle) {
		this.shippedAsSingle = shippedAsSingle;
	}
	public void setRequiresOverpack(String requiresOverpack) {
		this.requiresOverpack = requiresOverpack;
	}
	public void setPackingGroupId(BigDecimal packingGroupId) {
		this.packingGroupId = packingGroupId;
	}
	public void setOconus(String oconus) {
		this.oconus = oconus;
	}
	public void setSupplierSalesOrderNo(String supplierSalesOrderNo) {
		this.supplierSalesOrderNo = supplierSalesOrderNo;
	}
	public void setShipConfirmDate(Date shipConfirmDate) {
		this.shipConfirmDate = shipConfirmDate;
	}
  public void setUsgovTcn(String usgovTcn) {
    this.usgovTcn = usgovTcn;
  }
  
  //getters
	public String getCompanyId() {
		return companyId;
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
	public String getShipToLocationName() {
		return shipToLocationName;
	}
	public String getShipToCityCommaState() {
		return shipToCityCommaState;
	}
	public String getShipToZipcode() {
		return shipToZipcode;
	}
	public BigDecimal getPicklistId() {
		return picklistId;
	}
	public Date getPicklistPrintDate() {
		return picklistPrintDate;
	}
	public String getShipToLocationId() {
		return shipToLocationId;
	}
	public String getUltimateShipToDodaac() {
		return ultimateShipToDodaac;
	}
	public BigDecimal getPrNumber() {
		return prNumber;
	}
	public String getLineItem() {
		return lineItem;
	}
	public BigDecimal getRadianPo() {
		return radianPo;
	}
	public BigDecimal getPoLine() {
		return poLine;
	}
	public String getCatPartNo() {
		return catPartNo;
	}
	public String getPartShortName() {
		return partShortName;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public String getPackaging() {
		return packaging;
	}
	public BigDecimal getBoxLabelId() {
		return boxLabelId;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public BigDecimal getNumberOfBoxes() {
		return numberOfBoxes;
	}
	public Date getExpireDate() {
		return expireDate;
	}
	public BigDecimal getIssueId() {
		return issueId;
	}
	public String getBoxId() {
		return boxId;
	}
	public String getPalletId() {
		return palletId;
	}
	public BigDecimal getShipmentId() {
		return shipmentId;
	}
	public String getTrackingNumber() {
		return trackingNumber;
	}
	public String getCarrierCode() {
		return carrierCode;
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
	public Date getDesiredDeliveryDate() {
		return desiredDeliveryDate;
	}
	public Date getDesiredShipDate() {
		return desiredShipDate;
	}
	public String getShippedAsSingle() {
		return shippedAsSingle;
	}
	public String getRequiresOverpack() {
		return requiresOverpack;
	}
	public BigDecimal getPackingGroupId() {
		return packingGroupId;
	}
	public String getSupplierSalesOrderNo() {
		return supplierSalesOrderNo;
	}  
	public String getOconus() {
		return oconus;
	}
	public Date getShipConfirmDate() {
		return shipConfirmDate;
	}

	public String getInOutboundAsn() {
		return inOutboundAsn;
	}

	public void setInOutboundAsn(String inOutboundAsn) {
		this.inOutboundAsn = inOutboundAsn;
	}

	public String getMilstripCode() {
		return milstripCode;
	}

	public void setMilstripCode(String milstripCode) {
		this.milstripCode = milstripCode;
	}

	public String getOriginInspectionRequired() {
		return originInspectionRequired;
	}

	public void setOriginInspectionRequired(String originInspectionRequired) {
		this.originInspectionRequired = originInspectionRequired;
	}

  public String getUsgovTcn() {
    return usgovTcn;
  }

public BigDecimal getCustomerOrderQty() {
	return customerOrderQty;
}

public void setCustomerOrderQty(BigDecimal customerOrderQty) {
	this.customerOrderQty = customerOrderQty;
}

public String getCustomerPoNo() {
	return customerPoNo;
}

public void setCustomerPoNo(String customerPoNo) {
	this.customerPoNo = customerPoNo;
}

public String getMfgDateRequired() {
	return mfgDateRequired;
}

public void setMfgDateRequired(String mfgDateRequired) {
	this.mfgDateRequired = mfgDateRequired;
}

public String getShiptoAddress() {
	return shiptoAddress;
}

public void setShiptoAddress(String shiptoAddress) {
	this.shiptoAddress = shiptoAddress;
}

public String getMrCompanyId() {
	return mrCompanyId;
}

public void setMrCompanyId(String mrCompanyId) {
	this.mrCompanyId = mrCompanyId;
}

public BigDecimal getOrderQuantity() {
	return orderQuantity;
}

public void setOrderQuantity(BigDecimal orderQuantity) {
	this.orderQuantity = orderQuantity;
}
}