package com.tcmis.client.order.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: CustomerPoPreStageBean <br>
 * @version: 1.0, Sep 25, 2008 <br>
 *****************************************************************************/


public class CustomerPoPreStageBean extends BaseDataBean {

	private String companyId;
	private String customerPoNo;
	private String customerPoLineNo;
	private String transactionType;
	private BigDecimal lineSequence;
	private BigDecimal changeOrderSequence;
	private BigDecimal quantity;
	private BigDecimal quantityLeft;
	private String uom;
	private BigDecimal unitPrice;
	private String status;
	private String catalogId;
	private String catPartNo;
	private BigDecimal partGroupNo;
	private Date dateIssued;
	private String haasItemNo;
	private String manufacturerPartNum;
	private String itemDescription;
	private String customerPoLineNote;
	private Date requestedDeliveryDate;
	private Date estimatedDockDate;
	private BigDecimal totalAmountOnPo;
	private Date dateInserted;
	private String unitPriceCode;
	private String acceptanceCode;
	private String changeTypeCode;
	private String changeSubTypeCode;
	private String buyerPhone;
	private String buyerFax;
	private String documentControlNumber;
	private String transport;
	private String transporterAccount;
	private String tradingPartner;
	private String tradingPartnerId;
	private String customerHaasContractId;
	private String customerHaasAccountNo;
	private String customerPaymentTerms;
	private String customerPoNote;
	private String buyerNameOnPo;
	private String buyerAddress1;
	private String buyerAddress2;
	private String buyerCity;
	private String buyerState;
	private String buyerZip;
	private String buyerCountry;
	private String sellerNameOnPo;
	private String sellerIdOnPo;
	private String sellerAddress1;
	private String sellerAddress2;
	private String sellerCity;
	private String sellerState;
	private String sellerZip;
	private String sellerCountry;
	private String shiptoPartyName;
	private String shiptoPartyId;
	private String shiptoAddress1;
	private String shiptoAddress2;
	private String shiptoAddress3;
	private String shiptoCity;
	private String shiptoState;
	private String shiptoZip;
	private String shiptoCountry;
	private String billtoParty;
	private String billtoPartyId;
	private String billtoName2;
	private String billtoAddress1;
	private String billtoAddress2;
	private String billtoAddress3;
	private String billtoCity;
	private String billtoState;
	private String billtoZip;
	private String billtoCountry;
	private String interchangeControlNumber;
	private String currencyId;
	private BigDecimal loadId;
	private BigDecimal loadLine;
	private BigDecimal scheduleQuantity;
	private String scheduleUom;
	private String poTypeCode;
	private String processingComments;
	private String partShortDesc;
	private String pre860;
	private BigDecimal originalQty;
	private String originalUom;
	private String deliveryTypeCode;
	private String buyerPartyName;
	private String sellerPartyName;
	private String shiptoContactName;
	private String priceBasisUom;
	private BigDecimal priceBasisQuantity;
	private String requestorName;
	private String partialShipment;
	private String buyerIdOnPo;
	private String buyerRegion;
	private String sellerRegion;
	private String billtoRegion;
	private String shiptoRegion;
	private String carrier;
	private String poUpdateDate;
	private String freightOnBoard;
	private String paymentTerms;
	private String freightOnBoardNotes;
	private String paymentTermsNotes;
	private Date originalDateIssued;
	private String chargeNumber1;
	private String receivingLocationName;
	private String receivingLocationId;
	private String shipNoticePartyName;
	private String shipNoticePartyId;
	private String shipNoticeAddress1;
	private String shipNoticeAddress2;
	private String shipNoticeAddress3;
	private String shipNoticeCity;
	private String shipNoticeState;
	private String shipNoticeZip;
	private String mfgPlantName;
	private String mfgPlantId;
	private String scheduleNotes;
	private Date requestedDeliveryDateHeader;
	private String releaseNum;
	private String modelYearNum;
	private String productChangeNoticeNum;
	private String requisitionNumber;
	private BigDecimal totalPoNotToExceedAmount;
	private String acctDept;
	private String acctFundsSubdiv;
	private String acctFundsType;
	private String acctInstallationNum;
	private String acctObjectClass;
	private String acrn;
	private String allotmentSerialNum;
	private String buyerEmail;
	private String contractOffice;
	private String contractOfficerEmail;
	private String contractOfficerName;
	private String contractOfficerPhone;
	private String contractOfficeCode;
	private String dpasRating;
	private BigDecimal leadTime;
	private String leadTimeCode;
	private String manufacturerCageCode;
	private String milstripCode;
	private String payerAddr;
	private String payerCode;
	private String payerName;
	private String payofficeId;
	private String payofficeName;
	private String postalShiptoAddr1;
	private String postalShiptoAddr2;
	private String postalShiptoAddr3;
	private String postalShiptoCity;
	private String postalShiptoCode;
	private String postalShiptoName;
	private String postalShiptoState;
	private String postalShiptoZip;
	private String poSuffix;
	private String productGroup;
	private String purchaseRequisitionNum;
	private String receivingParty;
	private String receivingPartyCode;
	private String shippingNotes; // last added.
	private String supplementaryAddrCode;
	private BigDecimal totalLineAmt;
	private String transportationPriority;
	private String markForPartyName;
	private String markForPartyId;
	private String markForAddress1;
	private String markForAddress2;
	private String markForAddress3;
	private String markForCity;
	private String markForState;
	private String markForZip;
	private String markForCountry;
	private String buyerAdditionalName;
	private String contractOfficeAddress1;
	private String contractOfficeAddress2;
	private String contractOfficeCity;
	private String contractOfficeState;
	private String contractOfficeZip;
	private String contractOfficeCountry;
	private String payofficeAdditionalName;
	private String payofficeAddress1;
	private String payofficeAddress2;
	private String payofficeCity;
	private String payofficeState;
	private String payofficeZip;
	private String payofficeCountry;
	private String contractOfficeAlternateName;
	private String markForAdditionalAddress;
	private String markForAdditionalName;
	private String shipToAdditionalAddress;
	private String shipToAdditionalName;
	private String addlDelAdditionalAddress;
	private String addlDelAdditionalName;
	private String addlDelAddress1;
	private String addlDelAddress2;
	private String addlDelAddress3;
	private String addlDelCity;
	private String addlDelCountry;
	private String addlDelPartyId;
	private String addlDelPartyName;
	private String addlDelState;
	private String addlDelZip;
	private String internalOrderNum;
	private String projectCode;
	private String priorityRating;
	private String fmsCaseNum;
	private String portOfEmbarkation;
	private String portOfDebarkation;
	private String tac;
	private String typeOfService;
	private String oceanCarrierCode;
	private String transactionRefNum;
	private String transportationControlNum;
	private String originalTransactionType;
	private String buyerAddress3;
	private String buyerAddress4;
	private String buyerPartyId;
	private String markForAddress4;
	private String shiptoAddress4;
	private String billingCompanyPartNo;
	private String billingCompanyPo;
	private String billingCompanyPoLine;
	private String rsBox;
	private String callNumber;
	private String packagingLevel;
	private String cagePrime;
	private String exportFlag;
	private String signalCode;
	private String rdd;
	private String firstRow1348;
	private String ownerCompanyId;
	private String projectName;
	private Date desiredShipDate;
	private String piecesPerPackage;
	private String piecesPerPallet;
	private String unitWeightInLb;
	private String unitCubicFeet;
	private String seavan;
	private Date desiredDeliveryDate;
	private String invalidDodaac;
	private String shiptoAddress5;
	private String markForAddress5;
	private BigDecimal batchId;
	private Date orderDate;
	private String airGroundIndicator;
	private String dpmsFlag;
	private String seavanCarrier;
	private String seavanTrackingNumber;
	private Date seavanShipDate;
	private String tcn;
	private String shiptoPhone;
	private String shiptoEmail;
	private String deliveryNote;
	private String customerOrderType;
	private String customerPickup;
	private String catalogCompanyId;
	private String chargeNumber2;
	private String chargeNumber3;
	private String application;
	private String ownerSegmentId;

	//constructor
	public CustomerPoPreStageBean() {
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setCustomerPoNo(String customerPoNo) {
		this.customerPoNo = customerPoNo;
	}
	public void setCustomerPoLineNo(String customerPoLineNo) {
		this.customerPoLineNo = customerPoLineNo;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public void setLineSequence(BigDecimal lineSequence) {
		this.lineSequence = lineSequence;
	}
	public void setChangeOrderSequence(BigDecimal changeOrderSequence) {
		this.changeOrderSequence = changeOrderSequence;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public void setQuantityLeft(BigDecimal quantityLeft) {
		this.quantityLeft = quantityLeft;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}
	public void setPartGroupNo(BigDecimal partGroupNo) {
		this.partGroupNo = partGroupNo;
	}
	public void setDateIssued(Date dateIssued) {
		this.dateIssued = dateIssued;
	}
	public void setHaasItemNo(String haasItemNo) {
		this.haasItemNo = haasItemNo;
	}
	public void setManufacturerPartNum(String manufacturerPartNum) {
		this.manufacturerPartNum = manufacturerPartNum;
	}
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
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
	public void setTotalAmountOnPo(BigDecimal totalAmountOnPo) {
		this.totalAmountOnPo = totalAmountOnPo;
	}
	public void setDateInserted(Date dateInserted) {
		this.dateInserted = dateInserted;
	}
	public void setUnitPriceCode(String unitPriceCode) {
		this.unitPriceCode = unitPriceCode;
	}
	public void setAcceptanceCode(String acceptanceCode) {
		this.acceptanceCode = acceptanceCode;
	}
	public void setChangeTypeCode(String changeTypeCode) {
		this.changeTypeCode = changeTypeCode;
	}
	public void setChangeSubTypeCode(String changeSubTypeCode) {
		this.changeSubTypeCode = changeSubTypeCode;
	}
	public void setBuyerPhone(String buyerPhone) {
		this.buyerPhone = buyerPhone;
	}
	public void setBuyerFax(String buyerFax) {
		this.buyerFax = buyerFax;
	}
	public void setDocumentControlNumber(String documentControlNumber) {
		this.documentControlNumber = documentControlNumber;
	}
	public void setTransport(String transport) {
		this.transport = transport;
	}
	public void setTransporterAccount(String transporterAccount) {
		this.transporterAccount = transporterAccount;
	}
	public void setTradingPartner(String tradingPartner) {
		this.tradingPartner = tradingPartner;
	}
	public void setTradingPartnerId(String tradingPartnerId) {
		this.tradingPartnerId = tradingPartnerId;
	}
	public void setCustomerHaasContractId(String customerHaasContractId) {
		this.customerHaasContractId = customerHaasContractId;
	}
	public void setCustomerHaasAccountNo(String customerHaasAccountNo) {
		this.customerHaasAccountNo = customerHaasAccountNo;
	}
	public void setCustomerPaymentTerms(String customerPaymentTerms) {
		this.customerPaymentTerms = customerPaymentTerms;
	}
	public void setCustomerPoNote(String customerPoNote) {
		this.customerPoNote = customerPoNote;
	}
	public void setBuyerNameOnPo(String buyerNameOnPo) {
		this.buyerNameOnPo = buyerNameOnPo;
	}
	public void setBuyerAddress1(String buyerAddress1) {
		this.buyerAddress1 = buyerAddress1;
	}
	public void setBuyerAddress2(String buyerAddress2) {
		this.buyerAddress2 = buyerAddress2;
	}
	public void setBuyerCity(String buyerCity) {
		this.buyerCity = buyerCity;
	}
	public void setBuyerState(String buyerState) {
		this.buyerState = buyerState;
	}
	public void setBuyerZip(String buyerZip) {
		this.buyerZip = buyerZip;
	}
	public void setBuyerCountry(String buyerCountry) {
		this.buyerCountry = buyerCountry;
	}
	public void setSellerNameOnPo(String sellerNameOnPo) {
		this.sellerNameOnPo = sellerNameOnPo;
	}
	public void setSellerIdOnPo(String sellerIdOnPo) {
		this.sellerIdOnPo = sellerIdOnPo;
	}
	public void setSellerAddress1(String sellerAddress1) {
		this.sellerAddress1 = sellerAddress1;
	}
	public void setSellerAddress2(String sellerAddress2) {
		this.sellerAddress2 = sellerAddress2;
	}
	public void setSellerCity(String sellerCity) {
		this.sellerCity = sellerCity;
	}
	public void setSellerState(String sellerState) {
		this.sellerState = sellerState;
	}
	public void setSellerZip(String sellerZip) {
		this.sellerZip = sellerZip;
	}
	public void setSellerCountry(String sellerCountry) {
		this.sellerCountry = sellerCountry;
	}
	public void setShiptoPartyName(String shiptoPartyName) {
		this.shiptoPartyName = shiptoPartyName;
	}
	public void setShiptoPartyId(String shiptoPartyId) {
		this.shiptoPartyId = shiptoPartyId;
	}
	public void setShiptoAddress1(String shiptoAddress1) {
		this.shiptoAddress1 = shiptoAddress1;
	}
	public void setShiptoAddress2(String shiptoAddress2) {
		this.shiptoAddress2 = shiptoAddress2;
	}
	public void setShiptoAddress3(String shiptoAddress3) {
		this.shiptoAddress3 = shiptoAddress3;
	}
	public void setShiptoCity(String shiptoCity) {
		this.shiptoCity = shiptoCity;
	}
	public void setShiptoState(String shiptoState) {
		this.shiptoState = shiptoState;
	}
	public void setShiptoZip(String shiptoZip) {
		this.shiptoZip = shiptoZip;
	}
	public void setShiptoCountry(String shiptoCountry) {
		this.shiptoCountry = shiptoCountry;
	}
	public void setBilltoParty(String billtoParty) {
		this.billtoParty = billtoParty;
	}
	public void setBilltoPartyId(String billtoPartyId) {
		this.billtoPartyId = billtoPartyId;
	}
	public void setBilltoName2(String billtoName2) {
		this.billtoName2 = billtoName2;
	}
	public void setBilltoAddress1(String billtoAddress1) {
		this.billtoAddress1 = billtoAddress1;
	}
	public void setBilltoAddress2(String billtoAddress2) {
		this.billtoAddress2 = billtoAddress2;
	}
	public void setBilltoAddress3(String billtoAddress3) {
		this.billtoAddress3 = billtoAddress3;
	}
	public void setBilltoCity(String billtoCity) {
		this.billtoCity = billtoCity;
	}
	public void setBilltoState(String billtoState) {
		this.billtoState = billtoState;
	}
	public void setBilltoZip(String billtoZip) {
		this.billtoZip = billtoZip;
	}
	public void setBilltoCountry(String billtoCountry) {
		this.billtoCountry = billtoCountry;
	}
	public void setInterchangeControlNumber(String interchangeControlNumber) {
		this.interchangeControlNumber = interchangeControlNumber;
	}
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}
	public void setLoadId(BigDecimal loadId) {
		this.loadId = loadId;
	}
	public void setLoadLine(BigDecimal loadLine) {
		this.loadLine = loadLine;
	}
	public void setScheduleQuantity(BigDecimal scheduleQuantity) {
		this.scheduleQuantity = scheduleQuantity;
	}
	public void setScheduleUom(String scheduleUom) {
		this.scheduleUom = scheduleUom;
	}
	public void setPoTypeCode(String poTypeCode) {
		this.poTypeCode = poTypeCode;
	}
	public void setProcessingComments(String processingComments) {
		this.processingComments = processingComments;
	}
	public void setPartShortDesc(String partShortDesc) {
		this.partShortDesc = partShortDesc;
	}
	public void setPre860(String pre860) {
		this.pre860 = pre860;
	}
	public void setOriginalQty(BigDecimal originalQty) {
		this.originalQty = originalQty;
	}
	public void setOriginalUom(String originalUom) {
		this.originalUom = originalUom;
	}
	public void setDeliveryTypeCode(String deliveryTypeCode) {
		this.deliveryTypeCode = deliveryTypeCode;
	}
	public void setBuyerPartyName(String buyerPartyName) {
		this.buyerPartyName = buyerPartyName;
	}
	public void setSellerPartyName(String sellerPartyName) {
		this.sellerPartyName = sellerPartyName;
	}
	public void setShiptoContactName(String shiptoContactName) {
		this.shiptoContactName = shiptoContactName;
	}
	public void setPriceBasisUom(String priceBasisUom) {
		this.priceBasisUom = priceBasisUom;
	}
	public void setPriceBasisQuantity(BigDecimal priceBasisQuantity) {
		this.priceBasisQuantity = priceBasisQuantity;
	}
	public void setRequestorName(String requestorName) {
		this.requestorName = requestorName;
	}
	public void setPartialShipment(String partialShipment) {
		this.partialShipment = partialShipment;
	}
	public void setBuyerIdOnPo(String buyerIdOnPo) {
		this.buyerIdOnPo = buyerIdOnPo;
	}
	public void setBuyerRegion(String buyerRegion) {
		this.buyerRegion = buyerRegion;
	}
	public void setSellerRegion(String sellerRegion) {
		this.sellerRegion = sellerRegion;
	}
	public void setBilltoRegion(String billtoRegion) {
		this.billtoRegion = billtoRegion;
	}
	public void setShiptoRegion(String shiptoRegion) {
		this.shiptoRegion = shiptoRegion;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	public void setPoUpdateDate(String poUpdateDate) {
		this.poUpdateDate = poUpdateDate;
	}
	public void setFreightOnBoard(String freightOnBoard) {
		this.freightOnBoard = freightOnBoard;
	}
	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}
	public void setFreightOnBoardNotes(String freightOnBoardNotes) {
		this.freightOnBoardNotes = freightOnBoardNotes;
	}
	public void setPaymentTermsNotes(String paymentTermsNotes) {
		this.paymentTermsNotes = paymentTermsNotes;
	}
	public void setOriginalDateIssued(Date originalDateIssued) {
		this.originalDateIssued = originalDateIssued;
	}
	public void setChargeNumber1(String chargeNumber1) {
		this.chargeNumber1 = chargeNumber1;
	}
	public void setReceivingLocationName(String receivingLocationName) {
		this.receivingLocationName = receivingLocationName;
	}
	public void setReceivingLocationId(String receivingLocationId) {
		this.receivingLocationId = receivingLocationId;
	}
	public void setShipNoticePartyName(String shipNoticePartyName) {
		this.shipNoticePartyName = shipNoticePartyName;
	}
	public void setShipNoticePartyId(String shipNoticePartyId) {
		this.shipNoticePartyId = shipNoticePartyId;
	}
	public void setShipNoticeAddress1(String shipNoticeAddress1) {
		this.shipNoticeAddress1 = shipNoticeAddress1;
	}
	public void setShipNoticeAddress2(String shipNoticeAddress2) {
		this.shipNoticeAddress2 = shipNoticeAddress2;
	}
	public void setShipNoticeAddress3(String shipNoticeAddress3) {
		this.shipNoticeAddress3 = shipNoticeAddress3;
	}
	public void setShipNoticeCity(String shipNoticeCity) {
		this.shipNoticeCity = shipNoticeCity;
	}
	public void setShipNoticeState(String shipNoticeState) {
		this.shipNoticeState = shipNoticeState;
	}
	public void setShipNoticeZip(String shipNoticeZip) {
		this.shipNoticeZip = shipNoticeZip;
	}
	public void setMfgPlantName(String mfgPlantName) {
		this.mfgPlantName = mfgPlantName;
	}
	public void setMfgPlantId(String mfgPlantId) {
		this.mfgPlantId = mfgPlantId;
	}
	public void setScheduleNotes(String scheduleNotes) {
		this.scheduleNotes = scheduleNotes;
	}
	public void setRequestedDeliveryDateHeader(Date requestedDeliveryDateHeader) {
		this.requestedDeliveryDateHeader = requestedDeliveryDateHeader;
	}
	public void setReleaseNum(String releaseNum) {
		this.releaseNum = releaseNum;
	}
	public void setModelYearNum(String modelYearNum) {
		this.modelYearNum = modelYearNum;
	}
	public void setProductChangeNoticeNum(String productChangeNoticeNum) {
		this.productChangeNoticeNum = productChangeNoticeNum;
	}
	public void setRequisitionNumber(String requisitionNumber) {
		this.requisitionNumber = requisitionNumber;
	}
	public void setTotalPoNotToExceedAmount(BigDecimal totalPoNotToExceedAmount) {
		this.totalPoNotToExceedAmount = totalPoNotToExceedAmount;
	}
	public void setAcctDept(String acctDept) {
		this.acctDept = acctDept;
	}
	public void setAcctFundsSubdiv(String acctFundsSubdiv) {
		this.acctFundsSubdiv = acctFundsSubdiv;
	}
	public void setAcctFundsType(String acctFundsType) {
		this.acctFundsType = acctFundsType;
	}
	public void setAcctInstallationNum(String acctInstallationNum) {
		this.acctInstallationNum = acctInstallationNum;
	}
	public void setAcctObjectClass(String acctObjectClass) {
		this.acctObjectClass = acctObjectClass;
	}
	public void setAcrn(String acrn) {
		this.acrn = acrn;
	}
	public void setAllotmentSerialNum(String allotmentSerialNum) {
		this.allotmentSerialNum = allotmentSerialNum;
	}
	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}
	public void setContractOffice(String contractOffice) {
		this.contractOffice = contractOffice;
	}
	public void setContractOfficerEmail(String contractOfficerEmail) {
		this.contractOfficerEmail = contractOfficerEmail;
	}
	public void setContractOfficerName(String contractOfficerName) {
		this.contractOfficerName = contractOfficerName;
	}
	public void setContractOfficerPhone(String contractOfficerPhone) {
		this.contractOfficerPhone = contractOfficerPhone;
	}
	public void setContractOfficeCode(String contractOfficeCode) {
		this.contractOfficeCode = contractOfficeCode;
	}
	public void setDpasRating(String dpasRating) {
		this.dpasRating = dpasRating;
	}
	public void setLeadTime(BigDecimal leadTime) {
		this.leadTime = leadTime;
	}
	public void setLeadTimeCode(String leadTimeCode) {
		this.leadTimeCode = leadTimeCode;
	}
	public void setManufacturerCageCode(String manufacturerCageCode) {
		this.manufacturerCageCode = manufacturerCageCode;
	}
	public void setMilstripCode(String milstripCode) {
		this.milstripCode = milstripCode;
	}
	public void setPayerAddr(String payerAddr) {
		this.payerAddr = payerAddr;
	}
	public void setPayerCode(String payerCode) {
		this.payerCode = payerCode;
	}
	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}
	public void setPayofficeId(String payofficeId) {
		this.payofficeId = payofficeId;
	}
	public void setPayofficeName(String payofficeName) {
		this.payofficeName = payofficeName;
	}
	public void setPostalShiptoAddr1(String postalShiptoAddr1) {
		this.postalShiptoAddr1 = postalShiptoAddr1;
	}
	public void setPostalShiptoAddr2(String postalShiptoAddr2) {
		this.postalShiptoAddr2 = postalShiptoAddr2;
	}
	public void setPostalShiptoAddr3(String postalShiptoAddr3) {
		this.postalShiptoAddr3 = postalShiptoAddr3;
	}
	public void setPostalShiptoCity(String postalShiptoCity) {
		this.postalShiptoCity = postalShiptoCity;
	}
	public void setPostalShiptoCode(String postalShiptoCode) {
		this.postalShiptoCode = postalShiptoCode;
	}
	public void setPostalShiptoName(String postalShiptoName) {
		this.postalShiptoName = postalShiptoName;
	}
	public void setPostalShiptoState(String postalShiptoState) {
		this.postalShiptoState = postalShiptoState;
	}
	public void setPostalShiptoZip(String postalShiptoZip) {
		this.postalShiptoZip = postalShiptoZip;
	}
	public void setPoSuffix(String poSuffix) {
		this.poSuffix = poSuffix;
	}
	public void setProductGroup(String productGroup) {
		this.productGroup = productGroup;
	}
	public void setPurchaseRequisitionNum(String purchaseRequisitionNum) {
		this.purchaseRequisitionNum = purchaseRequisitionNum;
	}
	public void setReceivingParty(String receivingParty) {
		this.receivingParty = receivingParty;
	}
	public void setReceivingPartyCode(String receivingPartyCode) {
		this.receivingPartyCode = receivingPartyCode;
	}
	public void setShippingNotes(String shippingNotes) {
		this.shippingNotes = shippingNotes;
	}
	public void setSupplementaryAddrCode(String supplementaryAddrCode) {
		this.supplementaryAddrCode = supplementaryAddrCode;
	}
	public void setTotalLineAmt(BigDecimal totalLineAmt) {
		this.totalLineAmt = totalLineAmt;
	}
	public void setTransportationPriority(String transportationPriority) {
		this.transportationPriority = transportationPriority;
	}
	public void setMarkForPartyName(String markForPartyName) {
		this.markForPartyName = markForPartyName;
	}
	public void setMarkForPartyId(String markForPartyId) {
		this.markForPartyId = markForPartyId;
	}
	public void setMarkForAddress1(String markForAddress1) {
		this.markForAddress1 = markForAddress1;
	}
	public void setMarkForAddress2(String markForAddress2) {
		this.markForAddress2 = markForAddress2;
	}
	public void setMarkForAddress3(String markForAddress3) {
		this.markForAddress3 = markForAddress3;
	}
	public void setMarkForCity(String markForCity) {
		this.markForCity = markForCity;
	}
	public void setMarkForState(String markForState) {
		this.markForState = markForState;
	}
	public void setMarkForZip(String markForZip) {
		this.markForZip = markForZip;
	}
	public void setMarkForCountry(String markForCountry) {
		this.markForCountry = markForCountry;
	}
	public void setBuyerAdditionalName(String buyerAdditionalName) {
		this.buyerAdditionalName = buyerAdditionalName;
	}
	public void setContractOfficeAddress1(String contractOfficeAddress1) {
		this.contractOfficeAddress1 = contractOfficeAddress1;
	}
	public void setContractOfficeAddress2(String contractOfficeAddress2) {
		this.contractOfficeAddress2 = contractOfficeAddress2;
	}
	public void setContractOfficeCity(String contractOfficeCity) {
		this.contractOfficeCity = contractOfficeCity;
	}
	public void setContractOfficeState(String contractOfficeState) {
		this.contractOfficeState = contractOfficeState;
	}
	public void setContractOfficeZip(String contractOfficeZip) {
		this.contractOfficeZip = contractOfficeZip;
	}
	public void setContractOfficeCountry(String contractOfficeCountry) {
		this.contractOfficeCountry = contractOfficeCountry;
	}
	public void setPayofficeAdditionalName(String payofficeAdditionalName) {
		this.payofficeAdditionalName = payofficeAdditionalName;
	}
	public void setPayofficeAddress1(String payofficeAddress1) {
		this.payofficeAddress1 = payofficeAddress1;
	}
	public void setPayofficeAddress2(String payofficeAddress2) {
		this.payofficeAddress2 = payofficeAddress2;
	}
	public void setPayofficeCity(String payofficeCity) {
		this.payofficeCity = payofficeCity;
	}
	public void setPayofficeState(String payofficeState) {
		this.payofficeState = payofficeState;
	}
	public void setPayofficeZip(String payofficeZip) {
		this.payofficeZip = payofficeZip;
	}
	public void setPayofficeCountry(String payofficeCountry) {
		this.payofficeCountry = payofficeCountry;
	}
	public void setContractOfficeAlternateName(String contractOfficeAlternateName) {
		this.contractOfficeAlternateName = contractOfficeAlternateName;
	}
	public void setMarkForAdditionalAddress(String markForAdditionalAddress) {
		this.markForAdditionalAddress = markForAdditionalAddress;
	}
	public void setMarkForAdditionalName(String markForAdditionalName) {
		this.markForAdditionalName = markForAdditionalName;
	}
	public void setShipToAdditionalAddress(String shipToAdditionalAddress) {
		this.shipToAdditionalAddress = shipToAdditionalAddress;
	}
	public void setShipToAdditionalName(String shipToAdditionalName) {
		this.shipToAdditionalName = shipToAdditionalName;
	}
	public void setAddlDelAdditionalAddress(String addlDelAdditionalAddress) {
		this.addlDelAdditionalAddress = addlDelAdditionalAddress;
	}
	public void setAddlDelAdditionalName(String addlDelAdditionalName) {
		this.addlDelAdditionalName = addlDelAdditionalName;
	}
	public void setAddlDelAddress1(String addlDelAddress1) {
		this.addlDelAddress1 = addlDelAddress1;
	}
	public void setAddlDelAddress2(String addlDelAddress2) {
		this.addlDelAddress2 = addlDelAddress2;
	}
	public void setAddlDelAddress3(String addlDelAddress3) {
		this.addlDelAddress3 = addlDelAddress3;
	}
	public void setAddlDelCity(String addlDelCity) {
		this.addlDelCity = addlDelCity;
	}
	public void setAddlDelCountry(String addlDelCountry) {
		this.addlDelCountry = addlDelCountry;
	}
	public void setAddlDelPartyId(String addlDelPartyId) {
		this.addlDelPartyId = addlDelPartyId;
	}
	public void setAddlDelPartyName(String addlDelPartyName) {
		this.addlDelPartyName = addlDelPartyName;
	}
	public void setAddlDelState(String addlDelState) {
		this.addlDelState = addlDelState;
	}
	public void setAddlDelZip(String addlDelZip) {
		this.addlDelZip = addlDelZip;
	}
	public void setInternalOrderNum(String internalOrderNum) {
		this.internalOrderNum = internalOrderNum;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public void setPriorityRating(String priorityRating) {
		this.priorityRating = priorityRating;
	}
	public void setFmsCaseNum(String fmsCaseNum) {
		this.fmsCaseNum = fmsCaseNum;
	}
	public void setPortOfEmbarkation(String portOfEmbarkation) {
		this.portOfEmbarkation = portOfEmbarkation;
	}
	public void setPortOfDebarkation(String portOfDebarkation) {
		this.portOfDebarkation = portOfDebarkation;
	}
	public void setTac(String tac) {
		this.tac = tac;
	}
	public void setTypeOfService(String typeOfService) {
		this.typeOfService = typeOfService;
	}
	public void setOceanCarrierCode(String oceanCarrierCode) {
		this.oceanCarrierCode = oceanCarrierCode;
	}
	public void setTransactionRefNum(String transactionRefNum) {
		this.transactionRefNum = transactionRefNum;
	}
	public void setTransportationControlNum(String transportationControlNum) {
		this.transportationControlNum = transportationControlNum;
	}
	public void setOriginalTransactionType(String originalTransactionType) {
		this.originalTransactionType = originalTransactionType;
	}
	public void setBuyerAddress3(String buyerAddress3) {
		this.buyerAddress3 = buyerAddress3;
	}
	public void setBuyerAddress4(String buyerAddress4) {
		this.buyerAddress4 = buyerAddress4;
	}
	public void setBuyerPartyId(String buyerPartyId) {
		this.buyerPartyId = buyerPartyId;
	}
	public void setMarkForAddress4(String markForAddress4) {
		this.markForAddress4 = markForAddress4;
	}
	public void setShiptoAddress4(String shiptoAddress4) {
		this.shiptoAddress4 = shiptoAddress4;
	}
	public void setBillingCompanyPartNo(String billingCompanyPartNo) {
		this.billingCompanyPartNo = billingCompanyPartNo;
	}
	public void setBillingCompanyPo(String billingCompanyPo) {
		this.billingCompanyPo = billingCompanyPo;
	}
	public void setBillingCompanyPoLine(String billingCompanyPoLine) {
		this.billingCompanyPoLine = billingCompanyPoLine;
	}
	public void setRsBox(String rsBox) {
		this.rsBox = rsBox;
	}
	public void setCallNumber(String callNumber) {
		this.callNumber = callNumber;
	}
	public void setPackagingLevel(String packagingLevel) {
		this.packagingLevel = packagingLevel;
	}
	public void setCagePrime(String cagePrime) {
		this.cagePrime = cagePrime;
	}
	public void setExportFlag(String exportFlag) {
		this.exportFlag = exportFlag;
	}
	public void setSignalCode(String signalCode) {
		this.signalCode = signalCode;
	}
	public void setRdd(String rdd) {
		this.rdd = rdd;
	}
	public void setFirstRow1348(String firstRow1348) {
		this.firstRow1348 = firstRow1348;
	}
	public void setOwnerCompanyId(String ownerCompanyId) {
		this.ownerCompanyId = ownerCompanyId;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public void setDesiredShipDate(Date desiredShipDate) {
		this.desiredShipDate = desiredShipDate;
	}
	public void setPiecesPerPackage(String piecesPerPackage) {
		this.piecesPerPackage = piecesPerPackage;
	}
	public void setPiecesPerPallet(String piecesPerPallet) {
		this.piecesPerPallet = piecesPerPallet;
	}
	public void setUnitWeightInLb(String unitWeightInLb) {
		this.unitWeightInLb = unitWeightInLb;
	}
	public void setUnitCubicFeet(String unitCubicFeet) {
		this.unitCubicFeet = unitCubicFeet;
	}
	public void setSeavan(String seavan) {
		this.seavan = seavan;
	}
	public void setDesiredDeliveryDate(Date desiredDeliveryDate) {
		this.desiredDeliveryDate = desiredDeliveryDate;
	}
	public void setInvalidDodaac(String invalidDodaac) {
		this.invalidDodaac = invalidDodaac;
	}
	public void setShiptoAddress5(String shiptoAddress5) {
		this.shiptoAddress5 = shiptoAddress5;
	}
	public void setMarkForAddress5(String markForAddress5) {
		this.markForAddress5 = markForAddress5;
	}
	public void setBatchId(BigDecimal batchId) {
		this.batchId = batchId;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public void setAirGroundIndicator(String airGroundIndicator) {
		this.airGroundIndicator = airGroundIndicator;
	}
	public void setDpmsFlag(String dpmsFlag) {
		this.dpmsFlag = dpmsFlag;
	}
	public void setSeavanCarrier(String seavanCarrier) {
		this.seavanCarrier = seavanCarrier;
	}
	public void setSeavanTrackingNumber(String seavanTrackingNumber) {
		this.seavanTrackingNumber = seavanTrackingNumber;
	}
	public void setSeavanShipDate(Date seavanShipDate) {
		this.seavanShipDate = seavanShipDate;
	}
	public void setTcn(String tcn) {
		this.tcn = tcn;
	}
	public void setShiptoPhone(String shiptoPhone) {
		this.shiptoPhone = shiptoPhone;
	}
	public void setShiptoEmail(String shiptoEmail) {
		this.shiptoEmail = shiptoEmail;
	}
	public void setDeliveryNote(String deliveryNote) {
		this.deliveryNote = deliveryNote;
	}


	//getters
	public String getCompanyId() {
		return companyId;
	}
	public String getCustomerPoNo() {
		return customerPoNo;
	}
	public String getCustomerPoLineNo() {
		return customerPoLineNo;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public BigDecimal getLineSequence() {
		return lineSequence;
	}
	public BigDecimal getChangeOrderSequence() {
		return changeOrderSequence;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public BigDecimal getQuantityLeft() {
		return quantityLeft;
	}
	public String getUom() {
		return uom;
	}
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public String getStatus() {
		return status;
	}
	public String getCatalogId() {
		return catalogId;
	}
	public String getCatPartNo() {
		return catPartNo;
	}
	public BigDecimal getPartGroupNo() {
		return partGroupNo;
	}
	public Date getDateIssued() {
		return dateIssued;
	}
	public String getHaasItemNo() {
		return haasItemNo;
	}
	public String getManufacturerPartNum() {
		return manufacturerPartNum;
	}
	public String getItemDescription() {
		return itemDescription;
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
	public BigDecimal getTotalAmountOnPo() {
		return totalAmountOnPo;
	}
	public Date getDateInserted() {
		return dateInserted;
	}
	public String getUnitPriceCode() {
		return unitPriceCode;
	}
	public String getAcceptanceCode() {
		return acceptanceCode;
	}
	public String getChangeTypeCode() {
		return changeTypeCode;
	}
	public String getChangeSubTypeCode() {
		return changeSubTypeCode;
	}
	public String getBuyerPhone() {
		return buyerPhone;
	}
	public String getBuyerFax() {
		return buyerFax;
	}
	public String getDocumentControlNumber() {
		return documentControlNumber;
	}
	public String getTransport() {
		return transport;
	}
	public String getTransporterAccount() {
		return transporterAccount;
	}
	public String getTradingPartner() {
		return tradingPartner;
	}
	public String getTradingPartnerId() {
		return tradingPartnerId;
	}
	public String getCustomerHaasContractId() {
		return customerHaasContractId;
	}
	public String getCustomerHaasAccountNo() {
		return customerHaasAccountNo;
	}
	public String getCustomerPaymentTerms() {
		return customerPaymentTerms;
	}
	public String getCustomerPoNote() {
		return customerPoNote;
	}
	public String getBuyerNameOnPo() {
		return buyerNameOnPo;
	}
	public String getBuyerAddress1() {
		return buyerAddress1;
	}
	public String getBuyerAddress2() {
		return buyerAddress2;
	}
	public String getBuyerCity() {
		return buyerCity;
	}
	public String getBuyerState() {
		return buyerState;
	}
	public String getBuyerZip() {
		return buyerZip;
	}
	public String getBuyerCountry() {
		return buyerCountry;
	}
	public String getSellerNameOnPo() {
		return sellerNameOnPo;
	}
	public String getSellerIdOnPo() {
		return sellerIdOnPo;
	}
	public String getSellerAddress1() {
		return sellerAddress1;
	}
	public String getSellerAddress2() {
		return sellerAddress2;
	}
	public String getSellerCity() {
		return sellerCity;
	}
	public String getSellerState() {
		return sellerState;
	}
	public String getSellerZip() {
		return sellerZip;
	}
	public String getSellerCountry() {
		return sellerCountry;
	}
	public String getShiptoPartyName() {
		return shiptoPartyName;
	}
	public String getShiptoPartyId() {
		return shiptoPartyId;
	}
	public String getShiptoAddress1() {
		return shiptoAddress1;
	}
	public String getShiptoAddress2() {
		return shiptoAddress2;
	}
	public String getShiptoAddress3() {
		return shiptoAddress3;
	}
	public String getShiptoCity() {
		return shiptoCity;
	}
	public String getShiptoState() {
		return shiptoState;
	}
	public String getShiptoZip() {
		return shiptoZip;
	}
	public String getShiptoCountry() {
		return shiptoCountry;
	}
	public String getBilltoParty() {
		return billtoParty;
	}
	public String getBilltoPartyId() {
		return billtoPartyId;
	}
	public String getBilltoName2() {
		return billtoName2;
	}
	public String getBilltoAddress1() {
		return billtoAddress1;
	}
	public String getBilltoAddress2() {
		return billtoAddress2;
	}
	public String getBilltoAddress3() {
		return billtoAddress3;
	}
	public String getBilltoCity() {
		return billtoCity;
	}
	public String getBilltoState() {
		return billtoState;
	}
	public String getBilltoZip() {
		return billtoZip;
	}
	public String getBilltoCountry() {
		return billtoCountry;
	}
	public String getInterchangeControlNumber() {
		return interchangeControlNumber;
	}
	public String getCurrencyId() {
		return currencyId;
	}
	public BigDecimal getLoadId() {
		return loadId;
	}
	public BigDecimal getLoadLine() {
		return loadLine;
	}
	public BigDecimal getScheduleQuantity() {
		return scheduleQuantity;
	}
	public String getScheduleUom() {
		return scheduleUom;
	}
	public String getPoTypeCode() {
		return poTypeCode;
	}
	public String getProcessingComments() {
		return processingComments;
	}
	public String getPartShortDesc() {
		return partShortDesc;
	}
	public String getPre860() {
		return pre860;
	}
	public BigDecimal getOriginalQty() {
		return originalQty;
	}
	public String getOriginalUom() {
		return originalUom;
	}
	public String getDeliveryTypeCode() {
		return deliveryTypeCode;
	}
	public String getBuyerPartyName() {
		return buyerPartyName;
	}
	public String getSellerPartyName() {
		return sellerPartyName;
	}
	public String getShiptoContactName() {
		return shiptoContactName;
	}
	public String getPriceBasisUom() {
		return priceBasisUom;
	}
	public BigDecimal getPriceBasisQuantity() {
		return priceBasisQuantity;
	}
	public String getRequestorName() {
		return requestorName;
	}
	public String getPartialShipment() {
		return partialShipment;
	}
	public String getBuyerIdOnPo() {
		return buyerIdOnPo;
	}
	public String getBuyerRegion() {
		return buyerRegion;
	}
	public String getSellerRegion() {
		return sellerRegion;
	}
	public String getBilltoRegion() {
		return billtoRegion;
	}
	public String getShiptoRegion() {
		return shiptoRegion;
	}
	public String getCarrier() {
		return carrier;
	}
	public String getPoUpdateDate() {
		return poUpdateDate;
	}
	public String getFreightOnBoard() {
		return freightOnBoard;
	}
	public String getPaymentTerms() {
		return paymentTerms;
	}
	public String getFreightOnBoardNotes() {
		return freightOnBoardNotes;
	}
	public String getPaymentTermsNotes() {
		return paymentTermsNotes;
	}
	public Date getOriginalDateIssued() {
		return originalDateIssued;
	}
	public String getChargeNumber1() {
		return chargeNumber1;
	}
	public String getReceivingLocationName() {
		return receivingLocationName;
	}
	public String getReceivingLocationId() {
		return receivingLocationId;
	}
	public String getShipNoticePartyName() {
		return shipNoticePartyName;
	}
	public String getShipNoticePartyId() {
		return shipNoticePartyId;
	}
	public String getShipNoticeAddress1() {
		return shipNoticeAddress1;
	}
	public String getShipNoticeAddress2() {
		return shipNoticeAddress2;
	}
	public String getShipNoticeAddress3() {
		return shipNoticeAddress3;
	}
	public String getShipNoticeCity() {
		return shipNoticeCity;
	}
	public String getShipNoticeState() {
		return shipNoticeState;
	}
	public String getShipNoticeZip() {
		return shipNoticeZip;
	}
	public String getMfgPlantName() {
		return mfgPlantName;
	}
	public String getMfgPlantId() {
		return mfgPlantId;
	}
	public String getScheduleNotes() {
		return scheduleNotes;
	}
	public Date getRequestedDeliveryDateHeader() {
		return requestedDeliveryDateHeader;
	}
	public String getReleaseNum() {
		return releaseNum;
	}
	public String getModelYearNum() {
		return modelYearNum;
	}
	public String getProductChangeNoticeNum() {
		return productChangeNoticeNum;
	}
	public String getRequisitionNumber() {
		return requisitionNumber;
	}
	public BigDecimal getTotalPoNotToExceedAmount() {
		return totalPoNotToExceedAmount;
	}
	public String getAcctDept() {
		return acctDept;
	}
	public String getAcctFundsSubdiv() {
		return acctFundsSubdiv;
	}
	public String getAcctFundsType() {
		return acctFundsType;
	}
	public String getAcctInstallationNum() {
		return acctInstallationNum;
	}
	public String getAcctObjectClass() {
		return acctObjectClass;
	}
	public String getAcrn() {
		return acrn;
	}
	public String getAllotmentSerialNum() {
		return allotmentSerialNum;
	}
	public String getBuyerEmail() {
		return buyerEmail;
	}
	public String getContractOffice() {
		return contractOffice;
	}
	public String getContractOfficerEmail() {
		return contractOfficerEmail;
	}
	public String getContractOfficerName() {
		return contractOfficerName;
	}
	public String getContractOfficerPhone() {
		return contractOfficerPhone;
	}
	public String getContractOfficeCode() {
		return contractOfficeCode;
	}
	public String getDpasRating() {
		return dpasRating;
	}
	public BigDecimal getLeadTime() {
		return leadTime;
	}
	public String getLeadTimeCode() {
		return leadTimeCode;
	}
	public String getManufacturerCageCode() {
		return manufacturerCageCode;
	}
	public String getMilstripCode() {
		return milstripCode;
	}
	public String getPayerAddr() {
		return payerAddr;
	}
	public String getPayerCode() {
		return payerCode;
	}
	public String getPayerName() {
		return payerName;
	}
	public String getPayofficeId() {
		return payofficeId;
	}
	public String getPayofficeName() {
		return payofficeName;
	}
	public String getPostalShiptoAddr1() {
		return postalShiptoAddr1;
	}
	public String getPostalShiptoAddr2() {
		return postalShiptoAddr2;
	}
	public String getPostalShiptoAddr3() {
		return postalShiptoAddr3;
	}
	public String getPostalShiptoCity() {
		return postalShiptoCity;
	}
	public String getPostalShiptoCode() {
		return postalShiptoCode;
	}
	public String getPostalShiptoName() {
		return postalShiptoName;
	}
	public String getPostalShiptoState() {
		return postalShiptoState;
	}
	public String getPostalShiptoZip() {
		return postalShiptoZip;
	}
	public String getPoSuffix() {
		return poSuffix;
	}
	public String getProductGroup() {
		return productGroup;
	}
	public String getPurchaseRequisitionNum() {
		return purchaseRequisitionNum;
	}
	public String getReceivingParty() {
		return receivingParty;
	}
	public String getReceivingPartyCode() {
		return receivingPartyCode;
	}
	public String getShippingNotes() {
		return shippingNotes;
	}
	public String getSupplementaryAddrCode() {
		return supplementaryAddrCode;
	}
	public BigDecimal getTotalLineAmt() {
		return totalLineAmt;
	}
	public String getTransportationPriority() {
		return transportationPriority;
	}
	public String getMarkForPartyName() {
		return markForPartyName;
	}
	public String getMarkForPartyId() {
		return markForPartyId;
	}
	public String getMarkForAddress1() {
		return markForAddress1;
	}
	public String getMarkForAddress2() {
		return markForAddress2;
	}
	public String getMarkForAddress3() {
		return markForAddress3;
	}
	public String getMarkForCity() {
		return markForCity;
	}
	public String getMarkForState() {
		return markForState;
	}
	public String getMarkForZip() {
		return markForZip;
	}
	public String getMarkForCountry() {
		return markForCountry;
	}
	public String getBuyerAdditionalName() {
		return buyerAdditionalName;
	}
	public String getContractOfficeAddress1() {
		return contractOfficeAddress1;
	}
	public String getContractOfficeAddress2() {
		return contractOfficeAddress2;
	}
	public String getContractOfficeCity() {
		return contractOfficeCity;
	}
	public String getContractOfficeState() {
		return contractOfficeState;
	}
	public String getContractOfficeZip() {
		return contractOfficeZip;
	}
	public String getContractOfficeCountry() {
		return contractOfficeCountry;
	}
	public String getPayofficeAdditionalName() {
		return payofficeAdditionalName;
	}
	public String getPayofficeAddress1() {
		return payofficeAddress1;
	}
	public String getPayofficeAddress2() {
		return payofficeAddress2;
	}
	public String getPayofficeCity() {
		return payofficeCity;
	}
	public String getPayofficeState() {
		return payofficeState;
	}
	public String getPayofficeZip() {
		return payofficeZip;
	}
	public String getPayofficeCountry() {
		return payofficeCountry;
	}
	public String getContractOfficeAlternateName() {
		return contractOfficeAlternateName;
	}
	public String getMarkForAdditionalAddress() {
		return markForAdditionalAddress;
	}
	public String getMarkForAdditionalName() {
		return markForAdditionalName;
	}
	public String getShipToAdditionalAddress() {
		return shipToAdditionalAddress;
	}
	public String getShipToAdditionalName() {
		return shipToAdditionalName;
	}
	public String getAddlDelAdditionalAddress() {
		return addlDelAdditionalAddress;
	}
	public String getAddlDelAdditionalName() {
		return addlDelAdditionalName;
	}
	public String getAddlDelAddress1() {
		return addlDelAddress1;
	}
	public String getAddlDelAddress2() {
		return addlDelAddress2;
	}
	public String getAddlDelAddress3() {
		return addlDelAddress3;
	}
	public String getAddlDelCity() {
		return addlDelCity;
	}
	public String getAddlDelCountry() {
		return addlDelCountry;
	}
	public String getAddlDelPartyId() {
		return addlDelPartyId;
	}
	public String getAddlDelPartyName() {
		return addlDelPartyName;
	}
	public String getAddlDelState() {
		return addlDelState;
	}
	public String getAddlDelZip() {
		return addlDelZip;
	}
	public String getInternalOrderNum() {
		return internalOrderNum;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public String getPriorityRating() {
		return priorityRating;
	}
	public String getFmsCaseNum() {
		return fmsCaseNum;
	}
	public String getPortOfEmbarkation() {
		return portOfEmbarkation;
	}
	public String getPortOfDebarkation() {
		return portOfDebarkation;
	}
	public String getTac() {
		return tac;
	}
	public String getTypeOfService() {
		return typeOfService;
	}
	public String getOceanCarrierCode() {
		return oceanCarrierCode;
	}
	public String getTransactionRefNum() {
		return transactionRefNum;
	}
	public String getTransportationControlNum() {
		return transportationControlNum;
	}
	public String getOriginalTransactionType() {
		return originalTransactionType;
	}
	public String getBuyerAddress3() {
		return buyerAddress3;
	}
	public String getBuyerAddress4() {
		return buyerAddress4;
	}
	public String getBuyerPartyId() {
		return buyerPartyId;
	}
	public String getMarkForAddress4() {
		return markForAddress4;
	}
	public String getShiptoAddress4() {
		return shiptoAddress4;
	}
	public String getBillingCompanyPartNo() {
		return billingCompanyPartNo;
	}
	public String getBillingCompanyPo() {
		return billingCompanyPo;
	}
	public String getBillingCompanyPoLine() {
		return billingCompanyPoLine;
	}
	public String getRsBox() {
		return rsBox;
	}
	public String getCallNumber() {
		return callNumber;
	}
	public String getPackagingLevel() {
		return packagingLevel;
	}
	public String getCagePrime() {
		return cagePrime;
	}
	public String getExportFlag() {
		return exportFlag;
	}
	public String getSignalCode() {
		return signalCode;
	}
	public String getRdd() {
		return rdd;
	}
	public String getFirstRow1348() {
		return firstRow1348;
	}
	public String getOwnerCompanyId() {
		return ownerCompanyId;
	}
	public String getProjectName() {
		return projectName;
	}
	public Date getDesiredShipDate() {
		return desiredShipDate;
	}
	public String getPiecesPerPackage() {
		return piecesPerPackage;
	}
	public String getPiecesPerPallet() {
		return piecesPerPallet;
	}
	public String getUnitWeightInLb() {
		return unitWeightInLb;
	}
	public String getUnitCubicFeet() {
		return unitCubicFeet;
	}
	public String getSeavan() {
		return seavan;
	}
	public Date getDesiredDeliveryDate() {
		return desiredDeliveryDate;
	}
	public String getInvalidDodaac() {
		return invalidDodaac;
	}
	public String getShiptoAddress5() {
		return shiptoAddress5;
	}
	public String getMarkForAddress5() {
		return markForAddress5;
	}
	public BigDecimal getBatchId() {
		return batchId;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public String getAirGroundIndicator() {
		return airGroundIndicator;
	}
	public String getDpmsFlag() {
		return dpmsFlag;
	}
	public String getSeavanCarrier() {
		return seavanCarrier;
	}
	public String getSeavanTrackingNumber() {
		return seavanTrackingNumber;
	}
	public Date getSeavanShipDate() {
		return seavanShipDate;
	}
	public String getTcn() {
		return tcn;
	}
	public String getShiptoPhone() {
		return shiptoPhone;
	}
	public String getShiptoEmail() {
		return shiptoEmail;
	}
	public String getDeliveryNote() {
		return deliveryNote;
	}

	public String getCustomerOrderType() {
		return customerOrderType;
	}

	public void setCustomerOrderType(String customerOrderType) {
		this.customerOrderType = customerOrderType;
	}

	public String getCustomerPickup() {
		return customerPickup;
	}

	public void setCustomerPickup(String customerPickup) {
		this.customerPickup = customerPickup;
	}
	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}
 	public void setCatalogCompanyId(String catalogCompanyId) {
		this.catalogCompanyId = catalogCompanyId;
	}

	public String getChargeNumber2() {
		return chargeNumber2;
	}

	public void setChargeNumber2(String chargeNumber2) {
		this.chargeNumber2 = chargeNumber2;
	}

	public String getChargeNumber3() {
		return chargeNumber3;
	}

	public void setChargeNumber3(String chargeNumber3) {
		this.chargeNumber3 = chargeNumber3;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getOwnerSegmentId() {
		return ownerSegmentId;
	}

	public void setOwnerSegmentId(String ownerSegmentId) {
		this.ownerSegmentId = ownerSegmentId;
	}


	
}