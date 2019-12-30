package com.tcmis.internal.hub.beans;

import static com.tcmis.common.web.IHaasConstants.CST_OPTION_SAVE;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.tcmis.common.framework.BaseInputBean;

/******************************************************************************
 * CLASSNAME: PicklistSelectionViewBean <br>
 * 
 * @version: 1.0, Feb 5, 2008 <br>
 *****************************************************************************/

public class PicklistSelectionViewBean extends BaseInputBean {

	private static final long	serialVersionUID	= -1216823128914038835L;
	private String				abcClassification;
	private String				ackSent;
	private String				addressLine1Display;
	private String				addressLine2Display;
	private String				addressLine3Display;
	private String				addressLine4Display;
	private String				addressLine5Display;
	private String				adr;
	private String				adrMissing;
	private String				airGroundIndicator;
	private String				application;
	private String				applicationDesc;
	private String				bin;
	private String				carrierCode;
	private String				carrierName;
	private String				catalogId;
	private BigDecimal			catalogPrice;
	private String				catPartNo;
	private String				cms;
	private String				companyId;
	private String				consolidationNumber;
	private String				critical;
	private String				currencyId;
	private BigDecimal			customerId;
	private String				customerName;
	private String				customerNote;
	private BigDecimal			customerServiceRepId;
	private String				customerServiceRepName;
	private String				deliveryPoint;
	private String				deliveryPointDesc;
	private String				deliveryType;
	private String				destInventoryGroupName;
	private String				distribution;
	private String				dot;
	private Date				expireDate;
	private String				expireDays;
	private BigDecimal			extendedPrice;
	private String				facilityId;
	private String				facilityName;
	private String				fromPickingPicklist;
	private String				hazardous;
	private String				hazmatIdMissing;
	private String				hub;
	private String				inventoryGroup;
	private String				inventoryGroupName;
	private BigDecimal			itemId;
	private String				lineInternalNote;
	private String				lineItem;
	private String				linePurchasingNote;
	private String				materialRequestOrigin;
	private String				milstripCode;
	private BigDecimal			mrCount;
	private String				mrNotes;
	private Date				needDate;
	private String				needDatePrefix;
	private String				oconusFlag;
	private String				ok;
	private String				operatingEntityName;
	private String				opsEntityId;
	private String				packaging;
	private BigDecimal			packingGroupId;
	private String				partDescription;
	private BigDecimal			partGroupNo;
	private BigDecimal			pickQty;
	private Date				pickupTime;
	private String				prInternalNote;
	private BigDecimal			prNumber;
	private BigDecimal			receiptId;
	private Date				releaseDate;
	private String				requestor;
	private String				requiresOverpack;
	private String				room;
	private String				roomDescription;
	private String				scrap;
	private String				searchArgument;

	private String				searchField;
	private String				searchMode;
	private String				seavan;
	private String				shippedAsSingle;
	private String				shippingReference;
	private String				shipToCity;

	private String				shipToDodaac;
	private String				shipToLocationDesc;
	private String				shipToLocationId;
	private String				shipToNote;
	private String				shipToStateAbbrev;
	private String				shipToZip;
	private String				showHazardousOnly;
	private String				showOCONUSonly;
	private String				sortBy;
	private BigDecimal			splitTcn;
	private String				stockingMethod;
	private BigDecimal			stopNumber;
	private String				trailerNumber;
	private String				transportationMode;
	private String				transportationPriority;
	private BigDecimal			unitGrossWeightLb;

	// constructor
	public PicklistSelectionViewBean() {
	}

	public String getAbcClassification() {
		return abcClassification;
	}

	/**
	 * @return the ackSent
	 */
	public String getAckSent() {
		return ackSent;
	}

	public String getAddressLine1Display() {
		return addressLine1Display;
	}

	public String getAddressLine2Display() {
		return addressLine2Display;
	}

	public String getAddressLine3Display() {
		return addressLine3Display;
	}

	public String getAddressLine4Display() {
		return addressLine4Display;
	}

	public String getAddressLine5Display() {
		return addressLine5Display;
	}

	public String getAdr() {
		return adr;
	}

	public String getAdrMissing() {
		return adrMissing;
	}

	/**
	 * @return the airGroundIndicator
	 */
	public String getAirGroundIndicator() {
		return airGroundIndicator;
	}

	public String getApplication() {
		return application;
	}

	public String getApplicationDesc() {
		return applicationDesc;
	}

	public String getBin() {
		return bin;
	}

	public String getCarrierCode() {
		return carrierCode;
	}

	public String getCarrierName() {
		return carrierName;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public BigDecimal getCatalogPrice() {
		return catalogPrice;
	}

	public String getCatPartNo() {
		return catPartNo;
	}

	public String getCms() {
		return cms;
	}

	public String getCompanyId() {
		return companyId;
	}

	public String getConsolidationNumber() {
		return consolidationNumber;
	}

	public String getCritical() {
		return critical;
	}

	public String getCurrencyId() {
		return currencyId;
	}

	/**
	 * @return the customerId
	 */
	public BigDecimal getCustomerId() {
		return customerId;
	}

	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	public String getCustomerNote() {
		return customerNote;
	}

	/**
	 * @return the customerServiceRepId
	 */
	public BigDecimal getCustomerServiceRepId() {
		return customerServiceRepId;
	}

	/**
	 * @return the customerServiceRepName
	 */
	public String getCustomerServiceRepName() {
		return customerServiceRepName;
	}

	public String getDeliveryPoint() {
		return deliveryPoint;
	}

	public String getDeliveryPointDesc() {
		return deliveryPointDesc;
	}

	public String getDeliveryType() {
		return deliveryType;
	}

	public String getDestInventoryGroupName() {
		return destInventoryGroupName;
	}

	public String getDistribution() {
		return distribution;
	}

	public String getDot() {
		return dot;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public String getExpireDays() {
		return expireDays;
	}

	public BigDecimal getExtendedPrice() {
		if (catalogPrice != null && pickQty != null)
			extendedPrice = pickQty.multiply(catalogPrice);
		return extendedPrice;
	}

	public String getFacilityId() {
		return facilityId;
	}

	/**
	 * @return the facilityName
	 */
	public String getFacilityName() {
		return facilityName;
	}

	public String getFromPickingPicklist() {
		return fromPickingPicklist;
	}

	public String getHazardous() {
		return hazardous;
	}

	public String getHazmatIdMissing() {
		return hazmatIdMissing;
	}

	public String getHub() {
		return hub;
	}

	// getters
	public String getInventoryGroup() {
		return inventoryGroup;
	}

	/**
	 * @return the inventoryGroupName
	 */
	public String getInventoryGroupName() {
		return inventoryGroupName;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public String getLineInternalNote() {
		return lineInternalNote;
	}

	public String getLineItem() {
		return lineItem;
	}

	public String getLinePurchasingNote() {
		return linePurchasingNote;
	}

	public String getMaterialRequestOrigin() {
		return materialRequestOrigin;
	}

	public String getMilstripCode() {
		return milstripCode;
	}

	public BigDecimal getMrCount() {
		return mrCount;
	}

	public String getMrNotes() {
		return mrNotes;
	}

	public Date getNeedDate() {
		return needDate;
	}

	public String getNeedDatePrefix() {
		return needDatePrefix;
	}

	public String getOconusFlag() {
		return oconusFlag;
	}

	public String getOk() {
		return ok;
	}

	/**
	 * @return the operatingEntityName
	 */
	public String getOperatingEntityName() {
		return operatingEntityName;
	}

	/**
	 * @return the opsEntityId
	 */
	public String getOpsEntityId() {
		return opsEntityId;
	}

	public String getPackaging() {
		return packaging;
	}

	public BigDecimal getPackingGroupId() {
		return packingGroupId;
	}

	public String getPartDescription() {
		return partDescription;
	}

	public BigDecimal getPartGroupNo() {
		return partGroupNo;
	}

	public BigDecimal getPickQty() {
		return pickQty;
	}

	public Date getPickupTime() {
		return pickupTime;
	}

	public String getPrInternalNote() {
		return prInternalNote;
	}

	public BigDecimal getPrNumber() {
		return prNumber;
	}

	public BigDecimal getReceiptId() {
		return receiptId;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public String getRequestor() {
		return requestor;
	}

	public String getRequiresOverpack() {
		return requiresOverpack;
	}

	public String getRoom() {
		return this.room;
	}

	public String getRoomDescription() {
		return this.roomDescription;
	}

	public String getScrap() {
		return scrap;
	}

	public String getSearchArgument() {
		return searchArgument;
	}

	public String getSearchField() {
		return searchField;
	}

	public String getSearchMode() {
		return searchMode;
	}

	/**
	 * @return the seavan
	 */
	public String getSeavan() {
		return seavan;
	}

	public String getShippedAsSingle() {
		return shippedAsSingle;
	}

	public String getShippingReference() {
		return shippingReference;
	}

	public String getShipToCity() {
		return shipToCity;
	}

	public String getShipToDodaac() {
		return shipToDodaac;
	}

	public String getShipToLocationDesc() {
		return shipToLocationDesc;
	}

	public String getShipToLocationId() {
		return shipToLocationId;
	}

	public String getShipToNote() {
		return shipToNote;
	}

	public String getShipToStateAbbrev() {
		return shipToStateAbbrev;
	}

	public String getShipToZip() {
		return shipToZip;
	}

	public String getShowHazardousOnly() {
		return showHazardousOnly;
	}

	public String getShowOCONUSonly() {
		return showOCONUSonly;
	}

	public String getSortBy() {
		return sortBy;
	}

	public BigDecimal getSplitTcn() {
		return splitTcn;
	}

	public String getStockingMethod() {
		return stockingMethod;
	}

	public BigDecimal getStopNumber() {
		return stopNumber;
	}

	public String getTrailerNumber() {
		return trailerNumber;
	}

	public String getTransportationMode() {
		return transportationMode;
	}

	public String getTransportationPriority() {
		return transportationPriority;
	}

	public BigDecimal getUnitGrossWeightLb() {
		return unitGrossWeightLb;
	}

	public boolean isPickableUnits() {
		return "pickableUnits".equals(getuAction());
	}

	public void setAbcClassification(String abcClassification) {
		this.abcClassification = abcClassification;
	}

	/**
	 * @param ackSent
	 *            the ackSent to set
	 */
	public void setAckSent(String ackSent) {
		this.ackSent = ackSent;
	}

	public void setAddressLine1Display(String addressLine1Display) {
		this.addressLine1Display = addressLine1Display;
	}

	public void setAddressLine2Display(String addressLine2Display) {
		this.addressLine2Display = addressLine2Display;
	}

	public void setAddressLine3Display(String addressLine3Display) {
		this.addressLine3Display = addressLine3Display;
	}

	public void setAddressLine4Display(String addressLine4Display) {
		this.addressLine4Display = addressLine4Display;
	}

	public void setAddressLine5Display(String addressLine5Display) {
		this.addressLine5Display = addressLine5Display;
	}

	public void setAdr(String adr) {
		this.adr = adr;
	}

	public void setAdrMissing(String adrMissing) {
		this.adrMissing = adrMissing;
	}

	/**
	 * @param airGroundIndicator
	 *            the airGroundIndicator to set
	 */
	public void setAirGroundIndicator(String airGroundIndicator) {
		this.airGroundIndicator = airGroundIndicator;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public void setApplicationDesc(String applicationDesc) {
		this.applicationDesc = applicationDesc;
	}

	public void setBin(String bin) {
		this.bin = bin;
	}

	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public void setCatalogPrice(BigDecimal catalogPrice) {
		this.catalogPrice = catalogPrice;
	}

	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}

	public void setCms(String cms) {
		this.cms = cms;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setConsolidationNumber(String consolidationNumber) {
		this.consolidationNumber = consolidationNumber;
	}

	public void setCritical(String critical) {
		this.critical = critical;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	/**
	 * @param customerId
	 *            the customerId to set
	 */
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	/**
	 * @param customerName
	 *            the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public void setCustomerNote(String customerNote) {
		this.customerNote = customerNote;
	}

	/**
	 * @param customerServiceRepId
	 *            the customerServiceRepId to set
	 */
	public void setCustomerServiceRepId(BigDecimal customerServiceRepId) {
		this.customerServiceRepId = customerServiceRepId;
	}

	/**
	 * @param customerServiceRepName
	 *            the customerServiceRepName to set
	 */
	public void setCustomerServiceRepName(String customerServiceRepName) {
		this.customerServiceRepName = customerServiceRepName;
	}

	public void setDeliveryPoint(String deliveryPoint) {
		this.deliveryPoint = deliveryPoint;
	}

	public void setDeliveryPointDesc(String deliveryPointDesc) {
		this.deliveryPointDesc = deliveryPointDesc;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	public void setDestInventoryGroupName(String destInventoryGroupName) {
		this.destInventoryGroupName = destInventoryGroupName;
	}

	public void setDistribution(String distribution) {
		this.distribution = distribution;
	}

	public void setDot(String dot) {
		this.dot = dot;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public void setExpireDays(String expireDays) {
		this.expireDays = expireDays;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	/**
	 * @param facilityName
	 *            the facilityName to set
	 */
	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public void setFromPickingPicklist(String fromPickingPicklist) {
		this.fromPickingPicklist = fromPickingPicklist;
	}

	public void setHazardous(String hazardous) {
		this.hazardous = hazardous;
	}

	public void setHazmatIdMissing(String hazmatIdMissing) {
		this.hazmatIdMissing = hazmatIdMissing;
	}

	@Override
	public void setHiddenFormFields() {
		// TODO Auto-generated method stub

	}

	public void setHub(String hub) {
		this.hub = hub;
	}

	// setters
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	/**
	 * @param inventoryGroupName
	 *            the inventoryGroupName to set
	 */
	public void setInventoryGroupName(String inventoryGroupName) {
		this.inventoryGroupName = inventoryGroupName;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public void setLineInternalNote(String lineInternalNote) {
		this.lineInternalNote = lineInternalNote;
	}

	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}

	public void setLinePurchasingNote(String linePurchasingNote) {
		this.linePurchasingNote = linePurchasingNote;
	}

	public void setMaterialRequestOrigin(String materialRequestOrigin) {
		this.materialRequestOrigin = materialRequestOrigin;
	}

	public void setMilstripCode(String milstripCode) {
		this.milstripCode = milstripCode;
	}

	public void setMrCount(BigDecimal mrCount) {
		this.mrCount = mrCount;
	}

	public void setMrNotes(String mrNotes) {
		this.mrNotes = mrNotes;
	}

	public void setNeedDate(Date needDate) {
		this.needDate = needDate;
	}

	public void setNeedDatePrefix(String needDatePrefix) {
		this.needDatePrefix = needDatePrefix;
	}

	public void setOconusFlag(String oconusFlag) {
		this.oconusFlag = oconusFlag;
	}

	public void setOk(String ok) {
		this.ok = ok;
	}

	/**
	 * @param operatingEntityName
	 *            the operatingEntityName to set
	 */
	public void setOperatingEntityName(String operatingEntityName) {
		this.operatingEntityName = operatingEntityName;
	}

	/**
	 * @param opsEntityId
	 *            the opsEntityId to set
	 */
	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}

	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}

	public void setPackingGroupId(BigDecimal packingGroupId) {
		this.packingGroupId = packingGroupId;
	}

	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}

	public void setPartGroupNo(BigDecimal partGroupNo) {
		this.partGroupNo = partGroupNo;
	}

	public void setPickQty(BigDecimal pickQty) {
		this.pickQty = pickQty;
	}

	public void setPickupTime(Date pickupTime) {
		this.pickupTime = pickupTime;
	}

	public void setPrInternalNote(String prInternalNote) {
		this.prInternalNote = prInternalNote;
	}

	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}

	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public void setRequestor(String requestor) {
		this.requestor = requestor;
	}

	public void setRequiresOverpack(String requiresOverpack) {
		this.requiresOverpack = requiresOverpack;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public void setRoomDescription(String roomDescription) {
		this.roomDescription = roomDescription;
	}

	public void setScrap(String scrap) {
		this.scrap = scrap;
	}

	public void setSearchArgument(String searchArgument) {
		this.searchArgument = searchArgument;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public void setSearchMode(String searchMode) {
		this.searchMode = searchMode;
	}

	/**
	 * @param seavan
	 *            the seavan to set
	 */
	public void setSeavan(String seavan) {
		this.seavan = seavan;
	}

	public void setShippedAsSingle(String shippedAsSingle) {
		this.shippedAsSingle = shippedAsSingle;
	}

	public void setShippingReference(String shippingReference) {
		this.shippingReference = shippingReference;
	}

	public void setShipToCity(String shipToCity) {
		this.shipToCity = shipToCity;
	}

	public void setShipToDodaac(String shipToDodaac) {
		this.shipToDodaac = shipToDodaac;
	}

	public void setShipToLocationDesc(String shipToLocationDesc) {
		this.shipToLocationDesc = shipToLocationDesc;
	}

	public void setShipToLocationId(String shipToLocationId) {
		this.shipToLocationId = shipToLocationId;
	}

	public void setShipToNote(String shipToNote) {
		this.shipToNote = shipToNote;
	}

	public void setShipToStateAbbrev(String shipToStateAbbrev) {
		this.shipToStateAbbrev = shipToStateAbbrev;
	}

	public void setShipToZip(String shipToZip) {
		this.shipToZip = shipToZip;
	}

	public void setShowHazardousOnly(String showHazardousOnly) {
		this.showHazardousOnly = showHazardousOnly;
	}

	public void setShowOCONUSonly(String showOCONUSonly) {
		this.showOCONUSonly = showOCONUSonly;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public void setSplitTcn(BigDecimal splitTcn) {
		this.splitTcn = splitTcn;
	}

	public void setStockingMethod(String stockingMethod) {
		this.stockingMethod = stockingMethod;
	}

	public void setStopNumber(BigDecimal stopNumber) {
		this.stopNumber = stopNumber;
	}

	public void setTrailerNumber(String trailerNumber) {
		this.trailerNumber = trailerNumber;
	}

	public void setTransportationMode(String transportationMode) {
		this.transportationMode = transportationMode;
	}

	public void setTransportationPriority(String transportationPriority) {
		this.transportationPriority = transportationPriority;
	}

	public void setUnitGrossWeightLb(BigDecimal unitGrossWeightLb) {
		this.unitGrossWeightLb = unitGrossWeightLb;
	}

	public boolean matches(PicklistSelectionViewBean otherPick) {
		return prNumber != null && otherPick.getPrNumber() != null && prNumber.compareTo(otherPick.getPrNumber()) == 0 && lineItem != null && getLineItem().equals(otherPick.getLineItem());
	}

	public void addPick(PicklistSelectionViewBean otherPick) {
		setPickQty(pickQty.add(otherPick.getPickQty()));
		if (StringUtils.isEmpty(room)) {
			setRoom(otherPick.getRoom());
		}
		else if (!room.equals(otherPick.getRoom())) {
			setRoom(room + ", " + otherPick.getRoom());
		}
		if (StringUtils.isEmpty(roomDescription)) {
			setRoomDescription(otherPick.getRoomDescription());
		}
		else if (!roomDescription.equals(otherPick.getRoomDescription())) {
			setRoom(roomDescription + ", " + otherPick.getRoomDescription());
		}
	}
}