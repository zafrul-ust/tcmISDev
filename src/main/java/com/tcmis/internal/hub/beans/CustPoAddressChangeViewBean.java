package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: CustPoAddressChangeViewBean <br>
 * @version: 1.0, Mar 14, 2008 <br>
 *****************************************************************************/


public class CustPoAddressChangeViewBean extends BaseDataBean {

	private String companyId;
	private BigDecimal addressChangeRequestId;
	private String status;
	private String origMarkForDodaac;
	private String origShipToDodaac;
	private String shippingNotes;
	private String customerHaasContractId;
	private String releaseNum;
	private String milstripCode;
	private String projectCode;
	private String priorityRating;
	private String fmsCaseNum;
	private String portOfEmbarkation;
	private String portOfDebarkation;
	private String transportationPriority;
	private String transactionRefNum;
	private String transportationControlNum;
	private String rdd;
	private BigDecimal quantity;
	private String uom;
	private String requisitionNumber;
	private String callNumber;
	private String stCountryAbbrev;
	private String stStateAbbrev;
	private String stCity;
	private String stZip;
	private String stAddressLine1Display;
	private String stAddressLine2Display;
	private String stAddressLine3Display;
	private String stAddressLine4Display;
	private String mfCountryAbbrev;
	private String mfStateAbbrev;
	private String mfCity;
	private String mfZip;
	private String mfAddressLine1Display;
	private String mfAddressLine2Display;
	private String mfAddressLine3Display;
	private String mfAddressLine4Display;
	private String nsn;
	private String clin;
	private String indexingType;
	private String shippedAsSingle;
	private String requiresOverpack;
	private BigDecimal grossWeightLbs;
	private BigDecimal cubicFeet;
	private String signalCode;
	private String supplementaryAddrCode;
	private String docIdCode;
	private String originalTransactionType;
	private String origMarkForLocationId;
	private String origShipToLocationId;
    private String markForDodaacType;
	private String shipToDodaacType;
    private String nol;
    private Date dateAddressOk;
    private String verifiedByName;
    private String markForAddress1;
    private String markForAddress2;
    private String markForAddress3;
    private String markForAddress4;
    private String shiptoAddress1;
    private String shiptoAddress2;
    private String shiptoAddress3;
    private String shiptoAddress4;
    

    //constructor
	public CustPoAddressChangeViewBean() {
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setAddressChangeRequestId(BigDecimal addressChangeRequestId) {
		this.addressChangeRequestId = addressChangeRequestId;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setOrigMarkForDodaac(String origMarkForDodaac) {
		this.origMarkForDodaac = origMarkForDodaac;
	}
	public void setOrigShipToDodaac(String origShipToDodaac) {
		this.origShipToDodaac = origShipToDodaac;
	}
	public void setShippingNotes(String shippingNotes) {
    if (shippingNotes != null)
    {
      shippingNotes =shippingNotes.trim();
    }
    this.shippingNotes = shippingNotes;
	}
	public void setCustomerHaasContractId(String customerHaasContractId) {
		this.customerHaasContractId = customerHaasContractId;
	}
	public void setReleaseNum(String releaseNum) {
		this.releaseNum = releaseNum;
	}
	public void setMilstripCode(String milstripCode) {
		this.milstripCode = milstripCode;
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
	public void setTransportationPriority(String transportationPriority) {
		this.transportationPriority = transportationPriority;
	}
	public void setTransactionRefNum(String transactionRefNum) {
		this.transactionRefNum = transactionRefNum;
	}
	public void setTransportationControlNum(String transportationControlNum) {
		this.transportationControlNum = transportationControlNum;
	}
	public void setRdd(String rdd) {
		this.rdd = rdd;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public void setRequisitionNumber(String requisitionNumber) {
		this.requisitionNumber = requisitionNumber;
	}
	public void setCallNumber(String callNumber) {
		this.callNumber = callNumber;
	}
	public void setStCountryAbbrev(String stCountryAbbrev) {
		this.stCountryAbbrev = stCountryAbbrev;
	}
	public void setStStateAbbrev(String stStateAbbrev) {
		this.stStateAbbrev = stStateAbbrev;
	}
	public void setStCity(String stCity) {
		this.stCity = stCity;
	}
	public void setStZip(String stZip) {
		this.stZip = stZip;
	}
	public void setStAddressLine1Display(String stAddressLine11Display) {
		this.stAddressLine1Display = stAddressLine11Display;
	}
	public void setStAddressLine2Display(String stAddressLine22Display) {
		this.stAddressLine2Display = stAddressLine22Display;
	}
	public void setStAddressLine3Display(String stAddressLine33Display) {
		this.stAddressLine3Display = stAddressLine33Display;
	}
	public void setStAddressLine4Display(String stAddressLine44Display) {
		this.stAddressLine4Display = stAddressLine44Display;
	}
	public void setMfCountryAbbrev(String mfCountryAbbrev) {
		this.mfCountryAbbrev = mfCountryAbbrev;
	}
	public void setMfStateAbbrev(String mfStateAbbrev) {
		this.mfStateAbbrev = mfStateAbbrev;
	}
	public void setMfCity(String mfCity) {
		this.mfCity = mfCity;
	}
	public void setMfZip(String mfZip) {
		this.mfZip = mfZip;
	}
	public void setMfAddressLine1Display(String mfAddressLine11Display) {
		this.mfAddressLine1Display = mfAddressLine11Display;
	}
	public void setMfAddressLine2Display(String mfAddressLine22Display) {
		this.mfAddressLine2Display = mfAddressLine22Display;
	}
	public void setMfAddressLine3Display(String mfAddressLine33Display) {
		this.mfAddressLine3Display = mfAddressLine33Display;
	}
	public void setMfAddressLine4Display(String mfAddressLine44Display) {
		this.mfAddressLine4Display = mfAddressLine44Display;
	}
	public void setNsn(String nsn) {
		this.nsn = nsn;
	}
	public void setClin(String clin) {
		this.clin = clin;
	}
	public void setIndexingType(String indexingType) {
		this.indexingType = indexingType;
	}
	public void setShippedAsSingle(String shippedAsSingle) {
		this.shippedAsSingle = shippedAsSingle;
	}
	public void setRequiresOverpack(String requiresOverpack) {
		this.requiresOverpack = requiresOverpack;
	}
	public void setGrossWeightLbs(BigDecimal grossWeightLbs) {
		this.grossWeightLbs = grossWeightLbs;
	}
	public void setCubicFeet(BigDecimal cubicFeet) {
		this.cubicFeet = cubicFeet;
	}
	public void setSignalCode(String signalCode) {
		this.signalCode = signalCode;
	}
	public void setSupplementaryAddrCode(String supplementaryAddrCode) {
		this.supplementaryAddrCode = supplementaryAddrCode;
	}
	public void setDocIdCode(String docIdCode) {
		this.docIdCode = docIdCode;
	}
	public void setOriginalTransactionType(String originalTransactionType) {
		this.originalTransactionType = originalTransactionType;
	}
	public void setOrigMarkForLocationId(String origMarkForLocationId) {
		this.origMarkForLocationId = origMarkForLocationId;
	}
	public void setOrigShipToLocationId(String origShipToLocationId) {
		this.origShipToLocationId = origShipToLocationId;
	}
	public void setMarkForDodaacType(String s) {
		this.markForDodaacType = s;
	}
	public void setShipToDodaacType(String s) {
		this.shipToDodaacType = s;
	}
    public void setNol(String s) {
		this.nol = s;
	}
    public void setDateAddressOk(Date d) {
		this.dateAddressOk = d;
	}
    public void setVerifiedByName(String s) {
		this.verifiedByName = s;
	}

    //getters
	public String getCompanyId() {
		return companyId;
	}
	public BigDecimal getAddressChangeRequestId() {
		return addressChangeRequestId;
	}
	public String getStatus() {
		return status;
	}
	public String getOrigMarkForDodaac() {
		return origMarkForDodaac;
	}
	public String getOrigShipToDodaac() {
		return origShipToDodaac;
	}
	public String getShippingNotes() {
		return shippingNotes;
	}
	public String getCustomerHaasContractId() {
		return customerHaasContractId;
	}
	public String getReleaseNum() {
		return releaseNum;
	}
	public String getMilstripCode() {
		return milstripCode;
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
	public String getTransportationPriority() {
		return transportationPriority;
	}
	public String getTransactionRefNum() {
		return transactionRefNum;
	}
	public String getTransportationControlNum() {
		return transportationControlNum;
	}
	public String getRdd() {
		return rdd;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public String getUom() {
		return uom;
	}
	public String getRequisitionNumber() {
		return requisitionNumber;
	}
	public String getCallNumber() {
		return callNumber;
	}
	public String getStCountryAbbrev() {
		return stCountryAbbrev;
	}
	public String getStStateAbbrev() {
		return stStateAbbrev;
	}
	public String getStCity() {
		return stCity;
	}
	public String getStZip() {
		return stZip;
	}
	public String getStAddressLine1Display() {
		return stAddressLine1Display;
	}
	public String getStAddressLine2Display() {
		return stAddressLine2Display;
	}
	public String getStAddressLine3Display() {
		return stAddressLine3Display;
	}
	public String getStAddressLine4Display() {
		return stAddressLine4Display;
	}
	public String getMfCountryAbbrev() {
		return mfCountryAbbrev;
	}
	public String getMfStateAbbrev() {
		return mfStateAbbrev;
	}
	public String getMfCity() {
		return mfCity;
	}
	public String getMfZip() {
		return mfZip;
	}
	public String getMfAddressLine1Display() {
		return mfAddressLine1Display;
	}
	public String getMfAddressLine2Display() {
		return mfAddressLine2Display;
	}
	public String getMfAddressLine3Display() {
		return mfAddressLine3Display;
	}
	public String getMfAddressLine4Display() {
		return mfAddressLine4Display;
	}
	public String getNsn() {
		return nsn;
	}
	public String getClin() {
		return clin;
	}
	public String getIndexingType() {
		return indexingType;
	}
	public String getShippedAsSingle() {
		return shippedAsSingle;
	}
	public String getRequiresOverpack() {
		return requiresOverpack;
	}
	public BigDecimal getGrossWeightLbs() {
		return grossWeightLbs;
	}
	public BigDecimal getCubicFeet() {
		return cubicFeet;
	}
	public String getSignalCode() {
		return signalCode;
	}
	public String getSupplementaryAddrCode() {
		return supplementaryAddrCode;
	}
	public String getDocIdCode() {
		return docIdCode;
	}
	public String getOriginalTransactionType() {
		return originalTransactionType;
	}
	public String getOrigMarkForLocationId() {
		return origMarkForLocationId;
	}
	public String getOrigShipToLocationId() {
		return origShipToLocationId;
	}
	public String getMarkForDodaacType() {
		return markForDodaacType;
	}
	public String getShipToDodaacType() {
		return shipToDodaacType;
	}
    public String getNol() {
		return nol;
	}
    public Date getDateAddressOk() {
		return dateAddressOk;
	}
    public String getVerifiedByName() {
		return verifiedByName;
	}

	public String getMarkForAddress1() {
		return markForAddress1;
	}

	public void setMarkForAddress1(String markForAddress1) {
		this.markForAddress1 = markForAddress1;
	}

	public String getMarkForAddress2() {
		return markForAddress2;
	}

	public void setMarkForAddress2(String markForAddress2) {
		this.markForAddress2 = markForAddress2;
	}

	public String getMarkForAddress3() {
		return markForAddress3;
	}

	public void setMarkForAddress3(String markForAddress3) {
		this.markForAddress3 = markForAddress3;
	}

	public String getMarkForAddress4() {
		return markForAddress4;
	}

	public void setMarkForAddress4(String markForAddress4) {
		this.markForAddress4 = markForAddress4;
	}

	public String getShiptoAddress1() {
		return shiptoAddress1;
	}

	public void setShiptoAddress1(String shiptoAddress1) {
		this.shiptoAddress1 = shiptoAddress1;
	}

	public String getShiptoAddress2() {
		return shiptoAddress2;
	}

	public void setShiptoAddress2(String shiptoAddress2) {
		this.shiptoAddress2 = shiptoAddress2;
	}

	public String getShiptoAddress3() {
		return shiptoAddress3;
	}

	public void setShiptoAddress3(String shiptoAddress3) {
		this.shiptoAddress3 = shiptoAddress3;
	}

	public String getShiptoAddress4() {
		return shiptoAddress4;
	}

	public void setShiptoAddress4(String shiptoAddress4) {
		this.shiptoAddress4 = shiptoAddress4;
	}
}