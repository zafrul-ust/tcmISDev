package com.tcmis.internal.print.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: UsgovLabelViewBean <br>
 * @version: 1.0, Feb 12, 2008 <br>
 *****************************************************************************/


public class UsgovLabelViewBean extends BaseDataBean {

	private String boxLabelId;
	private String companyId;
	private String shipFromLocationId;
	private String shipFromLocationName;
	private String shipToLocationName;
	private String shipToCityCommaState;
	private String shipToZipcode;
	private BigDecimal picklistId;
	private Date picklistPrintDate;
	private String ultimateShipToDodaac;
	private BigDecimal packingGroupId;
	private BigDecimal issueId;
	private BigDecimal prNumber;
	private String lineItem;
	private BigDecimal receiptId;
	private BigDecimal radianPo;
	private BigDecimal poLine;
	private String nsn;
	private String customerPo;
	private String cageCode;
	private String formattedNsn;
	private String pidPartNo;
	private String formattedCustomerPo;
	private String partShortName;
	private BigDecimal itemId;
	private String itemDesc;
	private String packaging;
	private BigDecimal quantity;
	private String quantityInUnitOfSale;
	private String grossWeight;
	private String dateOfManufacture;
	private String expireDate;
	private String boxId;
	private String palletId;
	private BigDecimal shipmentId;
	private String trackingNumber;
	private String carrierName;
	private String deliveryTicket;
	private String dateDelivered;
	private String dateShipped;
	private String unNumberAndProperShipName;
	private BigDecimal priorityRating;
	private Date desiredShipDate;
	private Date desiredDeliveryDate;
	private String shippedAsSingle;
	private String requiresOverpack;
	private String inventoryGroup;
	private String skipUnitLabels;
	private String quantityInUnitOfIssue;
	private String unitOfIssue;
	private String projectCode;
	private String flashpointInfo;
	private String requiredDeliveryDate;
	private String unitLabelPrinted;
	private String ownerCompanyId;
	private String unitOfSale;
	private String boxGrossWeight;
	
	private String serialNo1;
	private String serialNo2;
	private String serialNo3;
	private String serialNo4;
	private String serialNo5;
	private String serialNoComment;
	private String serialNumber;
	private String trackSerialNumber;

  //constructor
	public UsgovLabelViewBean() {
	}

	//setters
	public void setBoxLabelId(String boxLabelId) {
		this.boxLabelId = boxLabelId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
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
	public void setUltimateShipToDodaac(String ultimateShipToDodaac) {
		this.ultimateShipToDodaac = ultimateShipToDodaac;
	}
	public void setPackingGroupId(BigDecimal packingGroupId) {
		this.packingGroupId = packingGroupId;
	}
	public void setIssueId(BigDecimal issueId) {
		this.issueId = issueId;
	}
	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}
	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}
	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}
	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}
	public void setPoLine(BigDecimal poLine) {
		this.poLine = poLine;
	}
	public void setNsn(String nsn) {
		this.nsn = nsn;
	}
	public void setCustomerPo(String customerPo) {
		this.customerPo = customerPo;
	}
	public void setCageCode(String cageCode) {
		this.cageCode = cageCode;
	}
	public void setFormattedNsn(String formattedNsn) {
		this.formattedNsn = formattedNsn;
	}
	public void setPidPartNo(String pidPartNo) {
		this.pidPartNo = pidPartNo;
	}
	public void setFormattedCustomerPo(String formattedCustomerPo) {
		this.formattedCustomerPo = formattedCustomerPo;
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
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public void setQuantityInUnitOfSale(String quantityInUnitOfSale) {
		this.quantityInUnitOfSale = quantityInUnitOfSale;
	}
	public void setGrossWeight(String grossWeight) {
		this.grossWeight = grossWeight;
	}
	public void setDateOfManufacture(String dateOfManufacture) {
		this.dateOfManufacture = dateOfManufacture;
	}
	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
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
	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}
	public void setDeliveryTicket(String deliveryTicket) {
		this.deliveryTicket = deliveryTicket;
	}
	public void setDateDelivered(String dateDelivered) {
		this.dateDelivered = dateDelivered;
	}
	public void setDateShipped(String dateShipped) {
		this.dateShipped = dateShipped;
	}
	public void setUnNumberAndProperShipName(String unNumberAndProperShipName) {
		this.unNumberAndProperShipName = unNumberAndProperShipName;
	}
	public void setPriorityRating(BigDecimal priorityRating) {
		this.priorityRating = priorityRating;
	}
	public void setDesiredShipDate(Date desiredShipDate) {
		this.desiredShipDate = desiredShipDate;
	}
	public void setDesiredDeliveryDate(Date desiredDeliveryDate) {
		this.desiredDeliveryDate = desiredDeliveryDate;
	}
	public void setShippedAsSingle(String shippedAsSingle) {
		this.shippedAsSingle = shippedAsSingle;
	}
	public void setRequiresOverpack(String requiresOverpack) {
		this.requiresOverpack = requiresOverpack;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
  public void setSkipUnitLabels(String skipUnitLabels) {
    this.skipUnitLabels = skipUnitLabels;
  }
	public void setQuantityInUnitOfIssue(String quantityInUnitOfIssue) {
		this.quantityInUnitOfIssue = quantityInUnitOfIssue;
	}
	public void setUnitOfIssue(String unitOfIssue) {
		this.unitOfIssue = unitOfIssue;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public void setFlashpointInfo(String flashpointInfo) {
		this.flashpointInfo = flashpointInfo;
	}
	public void setRequiredDeliveryDate(String requiredDeliveryDate) {
		this.requiredDeliveryDate = requiredDeliveryDate;
	}
	public void setUnitLabelPrinted(String unitLabelPrinted) {
		this.unitLabelPrinted = unitLabelPrinted;
	}
	public void setOwnerCompanyId(String ownerCompanyId) {
		this.ownerCompanyId = ownerCompanyId;
	}
	public void setUnitOfSale(String unitOfSale) {
		this.unitOfSale = unitOfSale;
	}
	public void setBoxGrossWeight(String boxGrossWeight) {
		this.boxGrossWeight = boxGrossWeight;
	}

  //getters
	public String getBoxLabelId() {
		return boxLabelId;
	}
	public String getCompanyId() {
		return companyId;
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
	public String getUltimateShipToDodaac() {
		return ultimateShipToDodaac;
	}
	public BigDecimal getPackingGroupId() {
		return packingGroupId;
	}
	public BigDecimal getIssueId() {
		return issueId;
	}
	public BigDecimal getPrNumber() {
		return prNumber;
	}
	public String getLineItem() {
		return lineItem;
	}
	public BigDecimal getReceiptId() {
		return receiptId;
	}
	public BigDecimal getRadianPo() {
		return radianPo;
	}
	public BigDecimal getPoLine() {
		return poLine;
	}
	public String getNsn() {
		return nsn;
	}
	public String getCustomerPo() {
		return customerPo;
	}
	public String getCageCode() {
		return cageCode;
	}
	public String getFormattedNsn() {
		return formattedNsn;
	}
	public String getPidPartNo() {
		return pidPartNo;
	}
	public String getFormattedCustomerPo() {
		return formattedCustomerPo;
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
	public BigDecimal getQuantity() {
		return quantity;
	}
	public String getQuantityInUnitOfSale() {
		return quantityInUnitOfSale;
	}
	public String getGrossWeight() {
		return grossWeight;
	}
	public String getDateOfManufacture() {
		return dateOfManufacture;
	}
	public String getExpireDate() {
		return expireDate;
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
	public String getCarrierName() {
		return carrierName;
	}
	public String getDeliveryTicket() {
		return deliveryTicket;
	}
	public String getDateDelivered() {
		return dateDelivered;
	}
	public String getDateShipped() {
		return dateShipped;
	}
	public String getUnNumberAndProperShipName() {
		return unNumberAndProperShipName;
	}
	public BigDecimal getPriorityRating() {
		return priorityRating;
	}
	public Date getDesiredShipDate() {
		return desiredShipDate;
	}
	public Date getDesiredDeliveryDate() {
		return desiredDeliveryDate;
	}
	public String getShippedAsSingle() {
		return shippedAsSingle;
	}
	public String getRequiresOverpack() {
		return requiresOverpack;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
  public String getSkipUnitLabels() {
    return skipUnitLabels;
  }
	public String getQuantityInUnitOfIssue() {
		return quantityInUnitOfIssue;
	}
	public String getUnitOfIssue() {
		return unitOfIssue;
	}
  public String getProjectCode() {
		return projectCode;
	}
	public String getFlashpointInfo() {
		return flashpointInfo;
	}
  public String getRequiredDeliveryDate() {
		return requiredDeliveryDate;
	}
	public String getUnitLabelPrinted() {
		return unitLabelPrinted;
	}
	public String getOwnerCompanyId() {
		return ownerCompanyId;
	}
	public String getUnitOfSale() {
		return unitOfSale;
	}
	public String getBoxGrossWeight() {
		return boxGrossWeight;
	}
	
	public String getSerialNo1() {
		return this.serialNo1;
	}

	public void setSerialNo1(String serialNo1) {
		this.serialNo1 = serialNo1;
	}

	public String getSerialNo2() {
		return this.serialNo2;
	}

	public void setSerialNo2(String serialNo2) {
		this.serialNo2 = serialNo2;
	}

	public String getSerialNo3() {
		return this.serialNo3;
	}

	public void setSerialNo3(String serialNo3) {
		this.serialNo3 = serialNo3;
	}

	public String getSerialNo4() {
		return this.serialNo4;
	}

	public void setSerialNo4(String serialNo4) {
		this.serialNo4 = serialNo4;
	}

	public String getSerialNo5() {
		return this.serialNo5;
	}

	public void setSerialNo5(String serialNo5) {
		this.serialNo5 = serialNo5;
	}

	public String getSerialNoComment() {
		return this.serialNoComment;
	}

	public void setSerialNoComment(String serialNoComment) {
		this.serialNoComment = serialNoComment;
	}

	public String getSerialNumber() {
		return this.serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getTrackSerialNumber() {
		return trackSerialNumber;
	}

	public void setTrackSerialNumber(String trackSerialNumber) {
		this.trackSerialNumber = trackSerialNumber;
	}
 
	public boolean isSerialNumberTracked() {
		return "Y".equalsIgnoreCase(this.trackSerialNumber); 
	}
}