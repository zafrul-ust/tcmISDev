package com.tcmis.client.dla.beans;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;

import org.apache.struts.action.ActionForm;
import com.tcmis.common.framework.HubBaseInputBean;

public class DlaGasOrderCorrectionsBean extends HubBaseInputBean {

	private String releaseNum;
	private String boxRfid;
	private String boxId;
	private String oldBoxRfid;
	private String oldBoxId;
	private String caseRfid;
	private BigDecimal asnId;
	private BigDecimal shipmentId;
	private BigDecimal prNumber;
	private Date estimateDeliveryDate;
	private Date shipDate;
	private String okDoUpdate;
	private String inspDodaac;	
	private String shipFromCageCode;	
	private String inspAddressLine1;	
	private String inspAddressLine2;
	private String inspAddressLine3;
	private String inspCity;
	private String inspZip;
	private String inspEmail;
	private String inspPhone;
	private String inspName;
	private String inspState;
	private String shipFromLocationId;
	private String updateInspData;
	private String companyId;
	private String status;
	private Date dateSent;
	private String customerPoNo;
	private String customerPoLine;
	private BigDecimal polineQtyShippedInShipment;
	private String documentControlNumber;
	private String interchangeControlNumber;
	private String catPartNo;
	private Date poDate;
	private String deliveryTicket;
	private String billOfLading;
	private String statusDetail;
	private String transactionRefNum;
	private String transportationControlNum;
	private String shiptoPartyName;
	private String shiptoPartyId;
	private String contractOffice;
	private String contractOfficeAlternateName;
	private String contractOfficerName;
	private String payofficeId;
	private String payofficeName;
	private String payofficeAdditionalName;
	private String itemDescription;
	private String contractOfficeCode;
	private String contractOfficeAddress1;
	private String contractOfficeAddress2;
	private String contractOfficeCity;
	private String contractOfficeState;
	private String contractOfficeZip;
	private String contractOfficeCountry;
	private String payofficeAddress1;
	private String payofficeAddress2;
	private String payofficeCity;
	private String payofficeState;
	private String payofficeZip;
	private String payofficeCountry;
	private String shiptoAddress1;
	private String shiptoAddress2;
	private String shiptoAddress3;
	private String shiptoCity;
	private String shiptoState;
	private String shiptoZip;
	private String shiptoCountry;
	private String shiptoDodaac;
	private String usgovShipmentId;
	private String poSuffix;
	private BigDecimal polineQtyShippedInBox;
	private String uom;
	private String palletId;
	private String palletRfid;
	private BigDecimal unitPrice;
	private String customerHaasContractId;
	private String status2;
	private String documentControlNumber2;
	private String interchangeControlNumber2;
	private String carrierName;
	private String trackingNumber;
	private String statusDetail2;
	private Date dateSent2;
	private String inspCountry;
	private BigDecimal inspPhoneExtension;
	private String inspRequired;
	private String inspFaxNumber;
	private String contractId;
	private boolean isHeaderRecord;
	private String sendOptions;
	private String oldUsgovShipmentId;
	private String invoiceToResubmitId;
	

	public DlaGasOrderCorrectionsBean() {
		super();
	}

	public DlaGasOrderCorrectionsBean(ActionForm inputForm) {
		super(inputForm);
	}

	public DlaGasOrderCorrectionsBean(ActionForm inputForm, Locale locale) {
		super(inputForm, locale);
	}
	public String getOldUsgovShipmentId() {
		return oldUsgovShipmentId;
	}
	
	public void setOldUsgovShipmentId(String oldUsgovShipmentId) {
		this.oldUsgovShipmentId = oldUsgovShipmentId;
	}
	public void setSendOptions(String sendOptions) {
		this.sendOptions = sendOptions;
	}
	public String getSendOptions() {
		return sendOptions;
	}
	public boolean getIsHeaderRecord() {
		return isHeaderRecord;
	}	
	public void setIsHeaderRecord(boolean isHeaderRecord) {
		this.isHeaderRecord = isHeaderRecord;
	}
	public String getShiptoDodaac() {
		return shiptoDodaac;
	}
	
	public void setShiptoDodaac(String shiptoDodaac) {
		this.shiptoDodaac = shiptoDodaac;
	}
	public String getContractId() {
		return contractId;
	}
	
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public String getUpdateInspData() {
		return updateInspData;
	}
	
	public void setUpdateInspData(String updateInspData) {
		this.updateInspData = updateInspData;
	}
	
	public String getOkDoUpdate() {
		return okDoUpdate;
	}
	
	public void setOkDoUpdate(String okDoUpdate) {
		this.okDoUpdate = okDoUpdate;
	}
	
	public String getBoxRfid() {
		return boxRfid;
	}
	public void setBoxRfid(String boxRfid) {
		this.boxRfid = boxRfid;
	}
	public String getBoxId() {
		return boxId;
	}
	public void setBoxId(String boxId) {
		this.boxId = boxId;
	}
	public void setSldBoxRfid(String oldBoxRfid) {
		this.oldBoxRfid = oldBoxRfid;
	}
	public String getOldBoxRfid() {
		return oldBoxRfid;
	}
	public void setOldBoxRfid(String oldBoxRfid) {
		this.oldBoxRfid = oldBoxRfid;
	}
	public String getOldBoxId() {
		return oldBoxId;
	}
	public void setOldBoxId(String oldBoxId) {
		this.oldBoxId = oldBoxId;
	}
	public String getCaseRfid() {
		return caseRfid;
	}
	public void setCaseRfid(String caseRfid) {
		this.caseRfid = caseRfid;
	}
	public BigDecimal getPrNumber() {
		return prNumber;
	}
	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}
	public void setEstimateDeliveryDate(Date estimateDeliveryDate) {
		this.estimateDeliveryDate = estimateDeliveryDate;
	}
	public void setShipDate(Date shipDate) {
		this.shipDate = shipDate;
	}
	@Override
	public void setHiddenFormFields() {
		addHiddenFormField("searchArgument");
		addHiddenFormField("searchField");
		addHiddenFormField("searchMode");
	}
	
	public void setPolineQtyShippedInBox(BigDecimal polineQtyShippedInBox) {
		this.polineQtyShippedInBox = polineQtyShippedInBox;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public void setPalletId(String palletId) {
		this.palletId = palletId;
	}
	public void setPalletRfid(String palletRfid) {
		this.palletRfid = palletRfid;
	}
	public BigDecimal getPolineQtyShippedInBox() {
		return polineQtyShippedInBox;
	}
	public String getUom() {
		return uom;
	}
	public String getPalletId() {
		return palletId;
	}
	public String getPalletRfid() {
		return palletRfid;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setAsnId(BigDecimal asnId) {
		this.asnId = asnId;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setDateSent(Date dateSent) {
		this.dateSent = dateSent;
	}
	public void setCustomerPoNo(String customerPoNo) {
		this.customerPoNo = customerPoNo;
	}
	public void setCustomerPoLine(String customerPoLine) {
		this.customerPoLine = customerPoLine;
	}
	public void setPolineQtyShippedInShipment(BigDecimal polineQtyShippedInShipment) {
		this.polineQtyShippedInShipment = polineQtyShippedInShipment;
	}
	public void setShipmentId(BigDecimal shipmentId) {
		this.shipmentId = shipmentId;
	}
	public void setDocumentControlNumber(String documentControlNumber) {
		this.documentControlNumber = documentControlNumber;
	}
	public void setInterchangeControlNumber(String interchangeControlNumber) {
		this.interchangeControlNumber = interchangeControlNumber;
	}
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}
	public void setPoDate(Date poDate) {
		this.poDate = poDate;
	}
	public void setDeliveryTicket(String deliveryTicket) {
		this.deliveryTicket = deliveryTicket;
	}
	public void setBillOfLading(String billOfLading) {
		this.billOfLading = billOfLading;
	}
	public void setStatusDetail(String statusDetail) {
		this.statusDetail = statusDetail;
	}
	public void setReleaseNum(String releaseNum) {
		this.releaseNum = releaseNum;
	}
	public void setTransactionRefNum(String transactionRefNum) {
		this.transactionRefNum = transactionRefNum;
	}
	public void setTransportationControlNum(String transportationControlNum) {
		this.transportationControlNum = transportationControlNum;
	}
	public void setShiptoPartyName(String shiptoPartyName) {
		this.shiptoPartyName = shiptoPartyName;
	}
	public void setShiptoPartyId(String shiptoPartyId) {
		this.shiptoPartyId = shiptoPartyId;
	}
	public void setContractOffice(String contractOffice) {
		this.contractOffice = contractOffice;
	}
	public void setContractOfficeAlternateName(String contractOfficeAlternateName) {
		this.contractOfficeAlternateName = contractOfficeAlternateName;
	}
	public void setContractOfficerName(String contractOfficerName) {
		this.contractOfficerName = contractOfficerName;
	}
	public void setPayofficeId(String payofficeId) {
		this.payofficeId = payofficeId;
	}
	public void setPayofficeName(String payofficeName) {
		this.payofficeName = payofficeName;
	}
	public void setPayofficeAdditionalName(String payofficeAdditionalName) {
		this.payofficeAdditionalName = payofficeAdditionalName;
	}
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	public void setContractOfficeCode(String contractOfficeCode) {
		this.contractOfficeCode = contractOfficeCode;
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
	public void setUsgovShipmentId(String usgovShipmentId) {
		this.usgovShipmentId = usgovShipmentId;
	}
	public void setCustomerHaasContractId(String customerHaasContractId) {
		this.customerHaasContractId = customerHaasContractId;
	}
	public void setStatus2(String status2) {
		this.status2 = status2;
	}
	public void setDocumentControlNumber2(String documentControlNumber2) {
		this.documentControlNumber2 = documentControlNumber2;
	}
	public void setInterchangeControlNumber2(String interchangeControlNumber2) {
		this.interchangeControlNumber2 = interchangeControlNumber2;
	}
	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}
	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}
	public void setStatusDetail2(String statusDetail2) {
		this.statusDetail2 = statusDetail2;
	}
	public void setDateSent2(Date dateSent2) {
		this.dateSent2 = dateSent2;
	}
	public void setPoSuffix(String poSuffix) {
		this.poSuffix = poSuffix;
	}
	public void setInspDodaac(String inspDodaac) {
		this.inspDodaac = inspDodaac;
	}
	public void setShipFromCageCode(String shipFromCageCode) {
		this.shipFromCageCode = shipFromCageCode;
	}
	public void setInspAddressLine1(String inspAddressLine1) {
		this.inspAddressLine1 = inspAddressLine1;
	}
	public void setInspAddressLine2(String inspAddressLine2) {
		this.inspAddressLine2 = inspAddressLine2;
	}
	public void setInspAddressLine3(String inspAddressLine3) {
		this.inspAddressLine3 = inspAddressLine3;
	}
	public void setInspCity(String inspCity) {
		this.inspCity = inspCity;
	}
	public void setInspZip(String inspZip) {
		this.inspZip = inspZip;
	}
	public void setInspCountry(String inspCountry) {
		this.inspCountry = inspCountry;
	}
	public void setInspEmail(String inspEmail) {
		this.inspEmail = inspEmail;
	}
	public void setInspPhone(String inspPhone) {
		this.inspPhone = inspPhone;
	}
	public void setInspPhoneExtension(BigDecimal inspPhoneExtension) {
		this.inspPhoneExtension = inspPhoneExtension;
	}
	public void setShipFromLocationId(String shipFromLocationId) {
		this.shipFromLocationId = shipFromLocationId;
	}
	public void setInspName(String inspName) {
		this.inspName = inspName;
	}
	public void setInspRequired(String inspRequired) {
		this.inspRequired = inspRequired;
	}
	public void setInspFaxNumber(String inspFaxNumber) {
		this.inspFaxNumber = inspFaxNumber;
	}
	public void setInspState(String inspState) {
		this.inspState = inspState;
	}


	//getters
	public String getCompanyId() {
		return companyId;
	}
	public BigDecimal getAsnId() {
		return asnId;
	}
	public String getStatus() {
		return status;
	}
	public Date getDateSent() {
		return dateSent;
	}
	public String getCustomerPoNo() {
		return customerPoNo;
	}
	public String getCustomerPoLine() {
		return customerPoLine;
	}
	public BigDecimal getPolineQtyShippedInShipment() {
		return polineQtyShippedInShipment;
	}
	public BigDecimal getShipmentId() {
		return shipmentId;
	}
	public String getDocumentControlNumber() {
		return documentControlNumber;
	}
	public String getInterchangeControlNumber() {
		return interchangeControlNumber;
	}
	public String getCatPartNo() {
		return catPartNo;
	}
	public Date getPoDate() {
		return poDate;
	}
	public String getDeliveryTicket() {
		return deliveryTicket;
	}
	public String getBillOfLading() {
		return billOfLading;
	}
	public Date getEstimateDeliveryDate() {
		return estimateDeliveryDate;
	}
	public Date getShipDate() {
		return shipDate;
	}
	public String getStatusDetail() {
		return statusDetail;
	}
	public String getReleaseNum() {
		return releaseNum;
	}
	public String getTransactionRefNum() {
		return transactionRefNum;
	}
	public String getTransportationControlNum() {
		return transportationControlNum;
	}
	public String getShiptoPartyName() {
		return shiptoPartyName;
	}
	public String getShiptoPartyId() {
		return shiptoPartyId;
	}
	public String getContractOffice() {
		return contractOffice;
	}
	public String getContractOfficeAlternateName() {
		return contractOfficeAlternateName;
	}
	public String getContractOfficerName() {
		return contractOfficerName;
	}
	public String getPayofficeId() {
		return payofficeId;
	}
	public String getPayofficeName() {
		return payofficeName;
	}
	public String getPayofficeAdditionalName() {
		return payofficeAdditionalName;
	}
	public String getItemDescription() {
		return itemDescription;
	}
	public String getContractOfficeCode() {
		return contractOfficeCode;
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
	public String getUsgovShipmentId() {
		return usgovShipmentId;
	}
	public String getCustomerHaasContractId() {
		return customerHaasContractId;
	}
	public String getStatus2() {
		return status2;
	}
	public String getDocumentControlNumber2() {
		return documentControlNumber2;
	}
	public String getInterchangeControlNumber2() {
		return interchangeControlNumber2;
	}
	public String getCarrierName() {
		return carrierName;
	}
	public String getTrackingNumber() {
		return trackingNumber;
	}
	public String getStatusDetail2() {
		return statusDetail2;
	}
	public Date getDateSent2() {
		return dateSent2;
	}
	public String getPoSuffix() {
		return poSuffix;
	}
	public String getInspDodaac() {
		return inspDodaac;
	}
	public String getShipFromCageCode() {
		return shipFromCageCode;
	}
	public String getInspAddressLine1() {
		return inspAddressLine1;
	}
	public String getInspAddressLine2() {
		return inspAddressLine2;
	}
	public String getInspAddressLine3() {
		return inspAddressLine3;
	}
	public String getInspCity() {
		return inspCity;
	}
	public String getInspZip() {
		return inspZip;
	}
	public String getInspCountry() {
		return inspCountry;
	}
	public String getInspEmail() {
		return inspEmail;
	}
	public String getInspPhone() {
		return inspPhone;
	}
	public BigDecimal getInspPhoneExtension() {
		return inspPhoneExtension;
	}
	public String getShipFromLocationId() {
		return shipFromLocationId;
	}
	public String getInspName() {
		return inspName;
	}
	public String getInspRequired() {
		return inspRequired;
	}
	public String getInspFaxNumber() {
		return inspFaxNumber;
	}
	public String getInspState() {
		return inspState;
	}	
	public boolean isResubmit() {
		return "resubmit".equals(uAction);
	}

	public String getInvoiceToResubmitId() {
		return invoiceToResubmitId;
	}

	public void setInvoiceToResubmitId(String invoiceToResubmitId) {
		this.invoiceToResubmitId = invoiceToResubmitId;
	}
	
	
	
	
	
	
	
	
	
	
}
