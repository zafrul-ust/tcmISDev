package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: EdiOrderErrorViewBean <br>
 * @version: 1.0, May 9, 2008 <br>
 *****************************************************************************/


public class EdiOrderErrorViewBean extends BaseDataBean {

	private String tradingPartnerId;  
	private String facilityId;
	private String companyId;
	private String customerPoNo;
	private String customerPoLineNoTrim;
	private String customerPoLineNo;
	private String transactionType;
	private String transactionTypeDisplay;
	private BigDecimal lineSequence;
	private BigDecimal changeOrderSequence;
	private String errorDetail;
	private BigDecimal orderedQty;
	private String orderedUom;
	private String catalogUos;
	private BigDecimal uosPerPackaging;
	private BigDecimal mrQty;
	private String unitPriceOnOrder;
	private String currentOrderStatus;
	private String catPartNoOnOrder;
	private String manufacturerPartNum;
	private String haasItemNo;
	private String packaging;
	private Date dateIssued;
	private String itemDescriptionOnOrder;
	private String customerPoLineNote;
	private Date requestedDeliveryDate;
	private Date estimatedDockDate;
	private Date dateInserted;
	private String buyerPhone;
	private String customerPoNote;
	private String buyerNameOnPo;
	private String shiptoPartyName;
	private String shiptoPartyId;
	private String statusBeforeError;
	private String mrLine;
	private BigDecimal haasPo;
	private String changeSubTypeCode;
	private String shiptoFirstLetter;
	private BigDecimal loadId;
	private BigDecimal loadLine;
	private String catalogId;
	private String catPartNoOrig;
	private String multiplePart;
	private String currencyId;
	private BigDecimal unitPriceOrig;
	private String released;
	private String shippingNotes;
	private String shipToDodaac;
	private String shipToLocationId;
	private String shipToAddress;
	private String markForDodaac;
	private String markForLocationId;
	private String markForAddress;
	private BigDecimal addressChangeRequestId;
	private String addressChangeAllowed;
	private String addressChangeType;
	private String transactionRefNum;
    private String sentToDpms;
    private String okDoReset;
    private String oldShiptoPartyId;
    private BigDecimal oldOrderedQty;
	private String oldOrderedUom;
	private String contractOwner;

	public String getContractOwner() {
		return contractOwner;
	}
	public void setContractOwner(String contractOwner) {
		this.contractOwner = contractOwner;
	}
	public BigDecimal getOldOrderedQty() {
		return oldOrderedQty;
	}

	public void setOldOrderedQty(BigDecimal oldOrderedQty) {
		this.oldOrderedQty = oldOrderedQty;
	}

	public String getOldOrderedUom() {
		return oldOrderedUom;
	}

	public void setOldOrderedUom(String oldOrderedUom) {
		this.oldOrderedUom = oldOrderedUom;
	}

	public String getOldShiptoPartyId() {
		return oldShiptoPartyId;
	}

	public void setOldShiptoPartyId(String oldShiptoPartyId) {
		this.oldShiptoPartyId = oldShiptoPartyId;
	}

	//constructor
	public EdiOrderErrorViewBean() {
	}

	//setters
	public void setTradingPartnerId(String tradingPartnerId) {
		this.tradingPartnerId = tradingPartnerId;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setCustomerPoNo(String customerPoNo) {
		this.customerPoNo = customerPoNo;
	}
	public void setCustomerPoLineNoTrim(String customerPoLineNoTrim) {
		this.customerPoLineNoTrim = customerPoLineNoTrim;
	}
	public void setCustomerPoLineNo(String customerPoLineNo) {
		this.customerPoLineNo = customerPoLineNo;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public void setTransactionTypeDisplay(String transactionTypeDisplay) {
		this.transactionTypeDisplay = transactionTypeDisplay;
	}
	public void setLineSequence(BigDecimal lineSequence) {
		this.lineSequence = lineSequence;
	}
	public void setChangeOrderSequence(BigDecimal changeOrderSequence) {
		this.changeOrderSequence = changeOrderSequence;
	}
	public void setErrorDetail(String errorDetail) {
		this.errorDetail = errorDetail;
	}
	public void setOrderedQty(BigDecimal orderedQty) {
		this.orderedQty = orderedQty;
	}
	public void setOrderedUom(String orderedUom) {
		this.orderedUom = orderedUom;
	}
	public void setCatalogUos(String catalogUos) {
		this.catalogUos = catalogUos;
	}
	public void setUosPerPackaging(BigDecimal uosPerPackaging) {
		this.uosPerPackaging = uosPerPackaging;
	}
	public void setMrQty(BigDecimal mrQty) {
		this.mrQty = mrQty;
	}
	public void setUnitPriceOnOrder(String unitPriceOnOrder) {
		this.unitPriceOnOrder = unitPriceOnOrder;
	}
	public void setCurrentOrderStatus(String currentOrderStatus) {
		this.currentOrderStatus = currentOrderStatus;
	}
	public void setCatPartNoOnOrder(String catPartNoOnOrder) {
		this.catPartNoOnOrder = catPartNoOnOrder;
	}
	public void setManufacturerPartNum(String manufacturerPartNum) {
		this.manufacturerPartNum = manufacturerPartNum;
	}
	public void setHaasItemNo(String haasItemNo) {
		this.haasItemNo = haasItemNo;
	}
	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}
	public void setDateIssued(Date dateIssued) {
		this.dateIssued = dateIssued;
	}
	public void setItemDescriptionOnOrder(String itemDescriptionOnOrder) {
		this.itemDescriptionOnOrder = itemDescriptionOnOrder;
	}
	public void setCustomerPoLineNote(String customerPoLineNote) {
		this.customerPoLineNote = customerPoLineNote;
	}
	public void setRequestedDeliveryDate(Date requestedDeliveryDate) {
		this.requestedDeliveryDate = requestedDeliveryDate;
	}
	public void setEstimatedDockDate(Date estimatedDockDate) {
		this.estimatedDockDate = estimatedDockDate;
	}
	public void setDateInserted(Date dateInserted) {
		this.dateInserted = dateInserted;
	}
	public void setBuyerPhone(String buyerPhone) {
		this.buyerPhone = buyerPhone;
	}
	public void setCustomerPoNote(String customerPoNote) {
		this.customerPoNote = customerPoNote;
	}
	public void setBuyerNameOnPo(String buyerNameOnPo) {
		this.buyerNameOnPo = buyerNameOnPo;
	}
	public void setShiptoPartyName(String shiptoPartyName) {
		this.shiptoPartyName = shiptoPartyName;
	}
	public void setShiptoPartyId(String shiptoPartyId) {
		this.shiptoPartyId = shiptoPartyId;
	}
	public void setStatusBeforeError(String statusBeforeError) {
		this.statusBeforeError = statusBeforeError;
	}
	public void setMrLine(String mrLine) {
		this.mrLine = mrLine;
	}
	public void setHaasPo(BigDecimal haasPo) {
		this.haasPo = haasPo;
	}
	public void setChangeSubTypeCode(String changeSubTypeCode) {
		this.changeSubTypeCode = changeSubTypeCode;
	}
	public void setShiptoFirstLetter(String shiptoFirstLetter) {
		this.shiptoFirstLetter = shiptoFirstLetter;
	}
	public void setLoadId(BigDecimal loadId) {
		this.loadId = loadId;
	}
	public void setLoadLine(BigDecimal loadLine) {
		this.loadLine = loadLine;
	}
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	public void setCatPartNoOrig(String catPartNoOrig) {
		this.catPartNoOrig = catPartNoOrig;
	}
	public void setMultiplePart(String multiplePart) {
		this.multiplePart = multiplePart;
	}
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}
	public void setUnitPriceOrig(BigDecimal unitPriceOrig) {
		this.unitPriceOrig = unitPriceOrig;
	}
	public void setReleased(String released) {
		this.released = released;
	}
	public void setShippingNotes(String shippingNotes) {
		this.shippingNotes = shippingNotes;
	}
	public void setShipToDodaac(String shipToDodaac) {
		this.shipToDodaac = shipToDodaac;
	}
	public void setShipToLocationId(String shipToLocationId) {
		this.shipToLocationId = shipToLocationId;
	}
	public void setShipToAddress(String shipToAddress) {
		this.shipToAddress = shipToAddress;
	}
	public void setMarkForDodaac(String markForDodaac) {
		this.markForDodaac = markForDodaac;
	}
	public void setMarkForLocationId(String markForLocationId) {
		this.markForLocationId = markForLocationId;
	}
	public void setMarkForAddress(String markForAddress) {
		this.markForAddress = markForAddress;
	}
	public void setAddressChangeRequestId(BigDecimal addressChangeRequestId) {
		this.addressChangeRequestId = addressChangeRequestId;
	}
	public void setAddressChangeAllowed(String addressChangeAllowed) {
		this.addressChangeAllowed = addressChangeAllowed;
	}
	public void setAddressChangeType(String addressChangeType) {
		this.addressChangeType = addressChangeType;
	}
	public void setTransactionRefNum(String transactionRefNum) {
		this.transactionRefNum = transactionRefNum;
	}
        public void setSentToDpms(String sentToDpms) {
           this.sentToDpms = sentToDpms;
        }
        


	//getters
	public String getTradingPartnerId() {
		return tradingPartnerId;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public String getCustomerPoNo() {
		return customerPoNo;
	}
	public String getCustomerPoLineNoTrim() {
		return customerPoLineNoTrim;
	}
	public String getCustomerPoLineNo() {
		return customerPoLineNo;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public String getTransactionTypeDisplay() {
		return transactionTypeDisplay;
	}
	public BigDecimal getLineSequence() {
		return lineSequence;
	}
	public BigDecimal getChangeOrderSequence() {
		return changeOrderSequence;
	}
	public String getErrorDetail() {
		return errorDetail;
	}
	public BigDecimal getOrderedQty() {
		return orderedQty;
	}
	public String getOrderedUom() {
		return orderedUom;
	}
	public String getCatalogUos() {
		return catalogUos;
	}
	public BigDecimal getUosPerPackaging() {
		return uosPerPackaging;
	}
	public BigDecimal getMrQty() {
		return mrQty;
	}
	public String getUnitPriceOnOrder() {
		return unitPriceOnOrder;
	}
	public String getCurrentOrderStatus() {
		return currentOrderStatus;
	}
	public String getCatPartNoOnOrder() {
		return catPartNoOnOrder;
	}
	public String getManufacturerPartNum() {
		return manufacturerPartNum;
	}
	public String getHaasItemNo() {
		return haasItemNo;
	}
	public String getPackaging() {
		return packaging;
	}
	public Date getDateIssued() {
		return dateIssued;
	}
	public String getItemDescriptionOnOrder() {
		return itemDescriptionOnOrder;
	}
	public String getCustomerPoLineNote() {
		return customerPoLineNote;
	}
	public Date getRequestedDeliveryDate() {
		return requestedDeliveryDate;
	}
	public Date getEstimatedDockDate() {
		return estimatedDockDate;
	}
	public Date getDateInserted() {
		return dateInserted;
	}
	public String getBuyerPhone() {
		return buyerPhone;
	}
	public String getCustomerPoNote() {
		return customerPoNote;
	}
	public String getBuyerNameOnPo() {
		return buyerNameOnPo;
	}
	public String getShiptoPartyName() {
		return shiptoPartyName;
	}
	public String getShiptoPartyId() {
		return shiptoPartyId;
	}
	public String getStatusBeforeError() {
		return statusBeforeError;
	}
	public String getMrLine() {
		return mrLine;
	}
	public BigDecimal getHaasPo() {
		return haasPo;
	}
	public String getChangeSubTypeCode() {
		return changeSubTypeCode;
	}
	public String getShiptoFirstLetter() {
		return shiptoFirstLetter;
	}
	public BigDecimal getLoadId() {
		return loadId;
	}
	public BigDecimal getLoadLine() {
		return loadLine;
	}
	public String getCatalogId() {
		return catalogId;
	}
	public String getCatPartNoOrig() {
		return catPartNoOrig;
	}
	public String getMultiplePart() {
		return multiplePart;
	}
	public String getCurrencyId() {
		return currencyId;
	}
	public BigDecimal getUnitPriceOrig() {
		return unitPriceOrig;
	}
	public String getReleased() {
		return released;
	}
	public String getShippingNotes() {
		return shippingNotes;
	}
	public String getShipToDodaac() {
		return shipToDodaac;
	}
	public String getShipToLocationId() {
		return shipToLocationId;
	}
	public String getShipToAddress() {
		return shipToAddress;
	}
	public String getMarkForDodaac() {
		return markForDodaac;
	}
	public String getMarkForLocationId() {
		return markForLocationId;
	}
	public String getMarkForAddress() {
		return markForAddress;
	}
	public BigDecimal getAddressChangeRequestId() {
		return addressChangeRequestId;
	}
	public String getAddressChangeAllowed() {
		return addressChangeAllowed;
	}
	public String getAddressChangeType() {
		return addressChangeType;
	}
	public String getTransactionRefNum() {
		return transactionRefNum;
	}
        public String getSentToDpms() {
           return sentToDpms;
        }

		public String getOkDoReset() {
			return okDoReset;
		}

		public void setOkDoReset(String okDoReset) {
			this.okDoReset = okDoReset;
		}

        
}