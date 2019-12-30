package com.tcmis.internal.distribution.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: SalesQuoteViewBean <br>
 * @version: 1.0, Aug 21, 2009 <br>
 *****************************************************************************/


public class SalesQuoteViewBean extends BaseDataBean {

	private static final long serialVersionUID = -1417363041099749580L;
	
	private BigDecimal prNumber;
	private String status;
	private BigDecimal customerId;
	private String customerName;
	private String billToAddressLine1;
	private String billToAddressLine2;
	private String billToAddressLine3;
	private String billToAddressLine4;
	private String billToAddressLine5;
	private String companyId;
	private String facilityId;
	private String shipToLocationId;
	private String locationShortName;
	private String locationDesc;
	private String paymentTerms;
	private String shipToAddressLine1;
	private String shipToAddressLine2;
	private String shipToAddressLine3;
	private String shipToAddressLine4;
	private String shipToAddressLine5;
	private BigDecimal creditLimit;
	private BigDecimal overdueLimit;
	private String overdueLimitBasis;
	private String creditStatus;
	private String currencyId;
	private String fixedCurrency;
	private String priceGroupId;
	private BigDecimal shelfLifeRequired;
	private String shipComplete;
	private String accountSysId;
	private String chargeType;
	private BigDecimal requestor;
	private String requestorName;
	private String requestorPhone;
	private String requestorFax;
	private String requestorEmail;
	private BigDecimal originalSalesQuoteId;
	private Date submittedDate;
	private BigDecimal submittedBy;
	private String opsEntityId;
	private String opsEntityName;
	private String opsCompanyId;
	private BigDecimal customerServiceRepId;
	private String endUser;
	private String chargeFreight;
	private BigDecimal fieldSalesRepId;
	private BigDecimal salesAgentId;
	private String specialInstructions;
	private String carrierAccountId;
	private String carrierName;
	private String carrierContact;
	private String carrierServiceType;
	private String carrierAccountNumber;
	private String poNumber;
	private String inventoryGroup;
	private Date quoteExpireDate;
	private String submittedByName;
	private String customerServiceRepName;
	private String fieldSalesRepName;
	private String salesAgentName;
	private String taxRegistrationType;
	private String taxRegistrationNumber;
	private String orderType;
	private BigDecimal outstandingPayments;
	private String shipToCompanyId;
	private String printedImageUrl;
	private BigDecimal numberOfLines;
	private String catPartList;
	private String orderStatus;
	private String hub;
	private String hubName;
	private String inventoryGroupName;
	private String operatingEntityName;
    private BigDecimal totalHeaderCharge; 
    private BigDecimal availableCredit;
    private String incoTerms;
    private String ok;
	private BigDecimal totalRows;
	private String closeTab;
	private String closeOldTab; 
	private String selectedRowId;
	private String shipToUpdatable;
	private String shipToCity;
	private String shipToState;
	private String shipToZip;
	private String shipToCountry;
	private String billToUpdatable;
	private String billToCompanyId;
	private String billToLocationId;
	private String billToCity;
	private String billToState;
	private String billToZip;
	private String billToCountry;
	
	private String requestorToProcedure;
	private String requestorNameToProcedure;
	private String requestorPhoneToProcedure;
	private String requestorFaxToProcedure;
	private String requestorEmailToProcedure;
	
	private String shipToAddressLine1ToProcedure;
	private String shipToAddressLine2ToProcedure;
	private String shipToAddressLine3ToProcedure;
	private String shipToAddressLine4ToProcedure;
	private String shipToAddressLine5ToProcedure;
	private BigDecimal totalExtendedPrice;	
	
	private String selectedLineItem;
	private String withinTerms;
	private String releaseStatus;
	private BigDecimal creditLimitInHomeCurrency;
	private BigDecimal creditLimitInOrderCurrency;
	private String homeCurrencyId;
	private BigDecimal orderToHomeConversionRate;
    private String priceGroupName;
    private String customerPriceGroupId;

    private Date requestDate;
    private Date customerPoDate;
    
    private BigDecimal shipmentId;
    
    private String defaultPaymentTerms;
    private BigDecimal availableCreditInOrdCrncy;
    
    private String internalNote;
    private String customerNote;
    private String shiptoNote;
    private String orderShiptoNote;
    private String cancelMRLineReason;
    
    private String mvItem;
    private String billedFlag;
    
    private String newOrderType;
    private String originalSalesQuoteType;
    private String quoteType;  // No real use 
    
    private String marginOutSideLimits;
    private String materialRequestOrigin;
    private String holdComments;
    private Date dateFirstConfirmed;

    private String emailOrderAck;
    private String originalHoldComments;
    
    private String customerOpsEntityId;
    private Date dateDelivered;
    private String abcClassification;
    
	public String getAbcClassification() {
		return abcClassification;
	}

	public void setAbcClassification(String abcClassification) {
		this.abcClassification = abcClassification;
	}

	public String getMaterialRequestOrigin() {
		return materialRequestOrigin;
	}

	public void setMaterialRequestOrigin(String materialRequestOrigin) {
		this.materialRequestOrigin = materialRequestOrigin;
	}

	public BigDecimal getShipmentId() {
		return shipmentId;
	}

	public void setShipmentId(BigDecimal shipmentId) {
		this.shipmentId = shipmentId;
	}

	//constructor
	public SalesQuoteViewBean() {
	}

	//setters
	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setShipToLocationId(String shipToLocationId) {
		this.shipToLocationId = shipToLocationId;
	}
	public void setLocationShortName(String locationShortName) {
		this.locationShortName = locationShortName;
	}
	public void setLocationDesc(String locationDesc) {
		this.locationDesc = locationDesc;
	}
	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}
	public void setCreditLimit(BigDecimal creditLimit) {
		this.creditLimit = creditLimit;
	}
	public void setOverdueLimit(BigDecimal overdueLimit) {
		this.overdueLimit = overdueLimit;
	}
	public void setOverdueLimitBasis(String overdueLimitBasis) {
		this.overdueLimitBasis = overdueLimitBasis;
	}
	public void setCreditStatus(String creditStatus) {
		this.creditStatus = creditStatus;
	}
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}
	public void setFixedCurrency(String fixedCurrency) {
		this.fixedCurrency = fixedCurrency;
	}
	public void setPriceGroupId(String priceGroupId) {
		this.priceGroupId = priceGroupId;
	}
	public void setShelfLifeRequired(BigDecimal shelfLifeRequired) {
		this.shelfLifeRequired = shelfLifeRequired;
	}
	public void setShipComplete(String shipComplete) {
		this.shipComplete = shipComplete;
	}
	public void setAccountSysId(String accountSysId) {
		this.accountSysId = accountSysId;
	}
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}
	public void setRequestor(BigDecimal requestor) {
		this.requestor = requestor;
	}
	public void setRequestorName(String requestorName) {
		this.requestorName = requestorName;
	}
	public void setRequestorPhone(String requestorPhone) {
		this.requestorPhone = requestorPhone;
	}
	public void setRequestorFax(String requestorFax) {
		this.requestorFax = requestorFax;
	}
	public void setRequestorEmail(String requestorEmail) {
		this.requestorEmail = requestorEmail;
	}
	public void setOriginalSalesQuoteId(BigDecimal originalSalesQuoteId) {
		this.originalSalesQuoteId = originalSalesQuoteId;
	}
	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}
	public void setSubmittedBy(BigDecimal submittedBy) {
		this.submittedBy = submittedBy;
	}
	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}
	public void setOpsCompanyId(String opsCompanyId) {
		this.opsCompanyId = opsCompanyId;
	}
	public void setCustomerServiceRepId(BigDecimal customerServiceRepId) {
		this.customerServiceRepId = customerServiceRepId;
	}
	public void setEndUser(String endUser) {
		this.endUser = endUser;
	}
	public void setChargeFreight(String chargeFreight) {
		this.chargeFreight = chargeFreight;
	}
	public void setFieldSalesRepId(BigDecimal fieldSalesRepId) {
		this.fieldSalesRepId = fieldSalesRepId;
	}
	public void setSalesAgentId(BigDecimal salesAgentId) {
		this.salesAgentId = salesAgentId;
	}
	public void setSpecialInstructions(String specialInstructions) {
		this.specialInstructions = specialInstructions;
	}
	public void setCarrierAccountId(String carrierAccountId) {
		this.carrierAccountId = carrierAccountId;
	}
	public void setCarrierContact(String carrierContact) {
		this.carrierContact = carrierContact;
	}
	public void setCarrierServiceType(String carrierServiceType) {
		this.carrierServiceType = carrierServiceType;
	}
	public void setCarrierAccountNumber(String carrierAccountNumber) {
		this.carrierAccountNumber = carrierAccountNumber;
	}
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setQuoteExpireDate(Date quoteExpireDate) {
		this.quoteExpireDate = quoteExpireDate;
	}
	public void setSubmittedByName(String submittedByName) {
		this.submittedByName = submittedByName;
	}
	public void setCustomerServiceRepName(String customerServiceRepName) {
		this.customerServiceRepName = customerServiceRepName;
	}
	public void setFieldSalesRepName(String fieldSalesRepName) {
		this.fieldSalesRepName = fieldSalesRepName;
	}
	public void setSalesAgentName(String salesAgentName) {
		this.salesAgentName = salesAgentName;
	}
	public void setTaxRegistrationType(String taxRegistrationType) {
		this.taxRegistrationType = taxRegistrationType;
	}
	public void setTaxRegistrationNumber(String taxRegistrationNumber) {
		this.taxRegistrationNumber = taxRegistrationNumber;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public void setOutstandingPayments(BigDecimal outstandingPayments) {
		this.outstandingPayments = outstandingPayments;
	}
	public void setShipToCompanyId(String shipToCompanyId) {
		this.shipToCompanyId = shipToCompanyId;
	}
	public void setPrintedImageUrl(String printedImageUrl) {
		this.printedImageUrl = printedImageUrl;
	}
	public void setNumberOfLines(BigDecimal numberOfLines) {
		this.numberOfLines = numberOfLines;
	}
	public void setCatPartList(String catPartList) {
		this.catPartList = catPartList;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public void setHub(String hub) {
		this.hub = hub;
	}
	public void setHubName(String hubName) {
		this.hubName = hubName;
	}
	public void setInventoryGroupName(String inventoryGroupName) {
		this.inventoryGroupName = inventoryGroupName;
	}
	public void setTotalHeaderCharge(BigDecimal totalHeaderCharge) {
		this.totalHeaderCharge = totalHeaderCharge;
	}
	public void setWithinTerms(String withinTerms) {
		this.withinTerms = withinTerms;
	}
	public void setReleaseStatus(String releaseStatus) {
		this.releaseStatus = releaseStatus;
	}

    //getters
	public BigDecimal getPrNumber() {
		return prNumber;
	}
	public String getStatus() {
		return status;
	}
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public String getCompanyId() {
		return companyId;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public String getShipToLocationId() {
		return shipToLocationId;
	}
	public String getLocationShortName() {
		return locationShortName;
	}
	public String getLocationDesc() {
		return locationDesc;
	}
	public String getPaymentTerms() {
		return paymentTerms;
	}

	public BigDecimal getCreditLimit() {
		return creditLimit;
	}
	public BigDecimal getOverdueLimit() {
		return overdueLimit;
	}
	public String getOverdueLimitBasis() {
		return overdueLimitBasis;
	}
	public String getCreditStatus() {
		return creditStatus;
	}
	public String getCurrencyId() {
		return currencyId;
	}
	public String getFixedCurrency() {
		return fixedCurrency;
	}
	public String getPriceGroupId() {
		return priceGroupId;
	}
	public BigDecimal getShelfLifeRequired() {
		return shelfLifeRequired;
	}
	public String getShipComplete() {
		return shipComplete;
	}
	public String getAccountSysId() {
		return accountSysId;
	}
	public String getChargeType() {
		return chargeType;
	}
	public BigDecimal getRequestor() {
		return requestor;
	}
	public String getRequestorName() {
		return requestorName;
	}
	public String getRequestorPhone() {
		return requestorPhone;
	}
	public String getRequestorFax() {
		return requestorFax;
	}
	public String getRequestorEmail() {
		return requestorEmail;
	}
	public BigDecimal getOriginalSalesQuoteId() {
		return originalSalesQuoteId;
	}
	public Date getSubmittedDate() {
		return submittedDate;
	}
	public BigDecimal getSubmittedBy() {
		return submittedBy;
	}
	public String getOpsEntityId() {
		return opsEntityId;
	}
	public String getOpsCompanyId() {
		return opsCompanyId;
	}
	public BigDecimal getCustomerServiceRepId() {
		return customerServiceRepId;
	}
	public String getEndUser() {
		return endUser;
	}
	public String getChargeFreight() {
		return chargeFreight;
	}
	public BigDecimal getFieldSalesRepId() {
		return fieldSalesRepId;
	}
	public BigDecimal getSalesAgentId() {
		return salesAgentId;
	}
	public String getSpecialInstructions() {
		return specialInstructions;
	}
	public String getCarrierAccountId() {
		return carrierAccountId;
	}
	public String getCarrierContact() {
		return carrierContact;
	}
	public String getCarrierServiceType() {
		return carrierServiceType;
	}
	public String getCarrierAccountNumber() {
		return carrierAccountNumber;
	}
	public String getPoNumber() {
		return poNumber;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public Date getQuoteExpireDate() {
		return quoteExpireDate;
	}
	public String getSubmittedByName() {
		return submittedByName;
	}
	public String getCustomerServiceRepName() {
		return customerServiceRepName;
	}
	public String getFieldSalesRepName() {
		return fieldSalesRepName;
	}
	public String getSalesAgentName() {
		return salesAgentName;
	}
	public String getTaxRegistrationType() {
		return taxRegistrationType;
	}
	public String getTaxRegistrationNumber() {
		return taxRegistrationNumber;
	}
	public String getOrderType() {
		return orderType;
	}
	public BigDecimal getOutstandingPayments() {
		return outstandingPayments;
	}
	public String getShipToCompanyId() {
		return shipToCompanyId;
	}
	public String getPrintedImageUrl() {
		return printedImageUrl;
	}
	public BigDecimal getNumberOfLines() {
		return numberOfLines;
	}
	public String getCatPartList() {
		return catPartList;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public String getHub() {
		return hub;
	}
	public String getHubName() {
		return hubName;
	}
	public String getInventoryGroupName() {
		return inventoryGroupName;
	}

	public void setOperatingEntityName(String operatingEntityName) {
		this.operatingEntityName = operatingEntityName;
	}

	public String getOperatingEntityName() {
		return operatingEntityName;
	}

	public void setTotalRows(BigDecimal totalRows) {
		this.totalRows = totalRows;
	}

	public BigDecimal getTotalRows() {
		return totalRows;
	}

	public void setOk(String ok) {
		this.ok = ok;
	}

	public String getOk() {
		return ok;
	}

	public void getTotalHeaderCharge(BigDecimal totalHeaderCharge) {
		this.totalHeaderCharge = totalHeaderCharge;
	}
	public String getWithinTerms() {
		return withinTerms;
	}
	public String getReleaseStatus() {
		return releaseStatus;
	}
    
    /**
	 * @return the billToAddressLine1
	 */
	public String getBillToAddressLine1() {
		return billToAddressLine1;
	}

	/**
	 * @param billToAddressLine1 the billToAddressLine1 to set
	 */
	public void setBillToAddressLine1(String billToAddressLine1) {
		this.billToAddressLine1 = billToAddressLine1;
	}

	/**
	 * @return the billToAddressLine2
	 */
	public String getBillToAddressLine2() {
		return billToAddressLine2;
	}

	/**
	 * @param billToAddressLine2 the billToAddressLine2 to set
	 */
	public void setBillToAddressLine2(String billToAddressLine2) {
		this.billToAddressLine2 = billToAddressLine2;
	}

	/**
	 * @return the billToAddressLine3
	 */
	public String getBillToAddressLine3() {
		return billToAddressLine3;
	}

	/**
	 * @param billToAddressLine3 the billToAddressLine3 to set
	 */
	public void setBillToAddressLine3(String billToAddressLine3) {
		this.billToAddressLine3 = billToAddressLine3;
	}

	/**
	 * @return the billToAddressLine4
	 */
	public String getBillToAddressLine4() {
		return billToAddressLine4;
	}

	/**
	 * @param billToAddressLine4 the billToAddressLine4 to set
	 */
	public void setBillToAddressLine4(String billToAddressLine4) {
		this.billToAddressLine4 = billToAddressLine4;
	}

	/**
	 * @return the billToAddressLine5
	 */
	public String getBillToAddressLine5() {
		return billToAddressLine5;
	}

	/**
	 * @param billToAddressLine5 the billToAddressLine5 to set
	 */
	public void setBillToAddressLine5(String billToAddressLine5) {
		this.billToAddressLine5 = billToAddressLine5;
	}

	/**
	 * @return the shipToAddressLine1
	 */
	public String getShipToAddressLine1() {
		return shipToAddressLine1;
	}

	/**
	 * @param shipToAddressLine1 the shipToAddressLine1 to set
	 */
	public void setShipToAddressLine1(String shipToAddressLine1) {
		this.shipToAddressLine1 = shipToAddressLine1;
	}

	/**
	 * @return the shipToAddressLine2
	 */
	public String getShipToAddressLine2() {
		return shipToAddressLine2;
	}

	/**
	 * @param shipToAddressLine2 the shipToAddressLine2 to set
	 */
	public void setShipToAddressLine2(String shipToAddressLine2) {
		this.shipToAddressLine2 = shipToAddressLine2;
	}

	/**
	 * @return the shipToAddressLine3
	 */
	public String getShipToAddressLine3() {
		return shipToAddressLine3;
	}

	/**
	 * @param shipToAddressLine3 the shipToAddressLine3 to set
	 */
	public void setShipToAddressLine3(String shipToAddressLine3) {
		this.shipToAddressLine3 = shipToAddressLine3;
	}

	/**
	 * @return the shipToAddressLine4
	 */
	public String getShipToAddressLine4() {
		return shipToAddressLine4;
	}

	/**
	 * @param shipToAddressLine4 the shipToAddressLine4 to set
	 */
	public void setShipToAddressLine4(String shipToAddressLine4) {
		this.shipToAddressLine4 = shipToAddressLine4;
	}

	/**
	 * @return the shipToAddressLine5
	 */
	public String getShipToAddressLine5() {
		return shipToAddressLine5;
	}

	/**
	 * @param shipToAddressLine5 the shipToAddressLine5 to set
	 */
	public void setShipToAddressLine5(String shipToAddressLine5) {
		this.shipToAddressLine5 = shipToAddressLine5;
	}

	/**
	 * @return the requestorNameToProcedure
	 */
	public String getRequestorNameToProcedure() {
		return requestorNameToProcedure;
	}

	/**
	 * @param requestorNameToProcedure the requestorNameToProcedure to set
	 */
	public void setRequestorNameToProcedure(String requestorNameToProcedure) {
		this.requestorNameToProcedure = requestorNameToProcedure;
	}

	/**
	 * @return the requestorPhoneToProcedure
	 */
	public String getRequestorPhoneToProcedure() {
		return requestorPhoneToProcedure;
	}

	/**
	 * @param requestorPhoneToProcedure the requestorPhoneToProcedure to set
	 */
	public void setRequestorPhoneToProcedure(String requestorPhoneToProcedure) {
		this.requestorPhoneToProcedure = requestorPhoneToProcedure;
	}

	/**
	 * @return the requestorFaxToProcedure
	 */
	public String getRequestorFaxToProcedure() {
		return requestorFaxToProcedure;
	}

	/**
	 * @param requestorFaxToProcedure the requestorFaxToProcedure to set
	 */
	public void setRequestorFaxToProcedure(String requestorFaxToProcedure) {
		this.requestorFaxToProcedure = requestorFaxToProcedure;
	}

	/**
	 * @return the requestorEmailToProcedure
	 */
	public String getRequestorEmailToProcedure() {
		return requestorEmailToProcedure;
	}

	/**
	 * @param requestorEmailToProcedure the requestorEmailToProcedure to set
	 */
	public void setRequestorEmailToProcedure(String requestorEmailToProcedure) {
		this.requestorEmailToProcedure = requestorEmailToProcedure;
	}

	/**
	 * @return the shipToAddressLine1ToProcedure
	 */
	public String getShipToAddressLine1ToProcedure() {
		return shipToAddressLine1ToProcedure;
	}

	/**
	 * @param shipToAddressLine1ToProcedure the shipToAddressLine1ToProcedure to set
	 */
	public void setShipToAddressLine1ToProcedure(
			String shipToAddressLine1ToProcedure) {
		this.shipToAddressLine1ToProcedure = shipToAddressLine1ToProcedure;
	}

	/**
	 * @return the shipToAddressLine2ToProcedure
	 */
	public String getShipToAddressLine2ToProcedure() {
		return shipToAddressLine2ToProcedure;
	}

	/**
	 * @param shipToAddressLine2ToProcedure the shipToAddressLine2ToProcedure to set
	 */
	public void setShipToAddressLine2ToProcedure(
			String shipToAddressLine2ToProcedure) {
		this.shipToAddressLine2ToProcedure = shipToAddressLine2ToProcedure;
	}

	/**
	 * @return the shipToAddressLine3ToProcedure
	 */
	public String getShipToAddressLine3ToProcedure() {
		return shipToAddressLine3ToProcedure;
	}

	/**
	 * @param shipToAddressLine3ToProcedure the shipToAddressLine3ToProcedure to set
	 */
	public void setShipToAddressLine3ToProcedure(
			String shipToAddressLine3ToProcedure) {
		this.shipToAddressLine3ToProcedure = shipToAddressLine3ToProcedure;
	}

	/**
	 * @return the shipToAddressLine4ToProcedure
	 */
	public String getShipToAddressLine4ToProcedure() {
		return shipToAddressLine4ToProcedure;
	}

	/**
	 * @param shipToAddressLine4ToProcedure the shipToAddressLine4ToProcedure to set
	 */
	public void setShipToAddressLine4ToProcedure(
			String shipToAddressLine4ToProcedure) {
		this.shipToAddressLine4ToProcedure = shipToAddressLine4ToProcedure;
	}

	/**
	 * @return the shipToAddressLine5ToProcedure
	 */
	public String getShipToAddressLine5ToProcedure() {
		return shipToAddressLine5ToProcedure;
	}

	/**
	 * @param shipToAddressLine5ToProcedure the shipToAddressLine5ToProcedure to set
	 */
	public void setShipToAddressLine5ToProcedure(
			String shipToAddressLine5ToProcedure) {
		this.shipToAddressLine5ToProcedure = shipToAddressLine5ToProcedure;
	}

	public BigDecimal getTotalHeaderCharge() {
		return totalHeaderCharge;
	}

	public String getBillToCity() {
		return billToCity;
	}

	public void setBillToCity(String billToCity) {
		this.billToCity = billToCity;
	}

	public String getBillToCountry() {
		return billToCountry;
	}

	public void setBillToCountry(String billToCountry) {
		this.billToCountry = billToCountry;
	}

	public String getBillToState() {
		return billToState;
	}

	public void setBillToState(String billToState) {
		this.billToState = billToState;
	}

	public String getBillToZip() {
		return billToZip;
	}

	public void setBillToZip(String billToZip) {
		this.billToZip = billToZip;
	}

	public String getShipToCity() {
		return shipToCity;
	}

	public void setShipToCity(String shipToCity) {
		this.shipToCity = shipToCity;
	}

	public String getShipToCountry() {
		return shipToCountry;
	}

	public void setShipToCountry(String shipToCountry) {
		this.shipToCountry = shipToCountry;
	}

	public String getShipToState() {
		return shipToState;
	}

	public void setShipToState(String shipToState) {
		this.shipToState = shipToState;
	}

	public String getShipToZip() {
		return shipToZip;
	}

	public void setShipToZip(String shipToZip) {
		this.shipToZip = shipToZip;
	}

	public String getBillToCompanyId() {
		return billToCompanyId;
	}

	public void setBillToCompanyId(String billToCompanyId) {
		this.billToCompanyId = billToCompanyId;
	}

	public String getBillToLocationId() {
		return billToLocationId;
	}

	public void setBillToLocationId(String billToLocationId) {
		this.billToLocationId = billToLocationId;
	}

	public BigDecimal getAvailableCredit() {
		return availableCredit;
	}

	public void setAvailableCredit(BigDecimal availableCredit) {
		this.availableCredit = availableCredit;
	}

	public String getRequestorToProcedure() {
		return requestorToProcedure;
	}

	public void setRequestorToProcedure(String requestorToProcedure) {
		this.requestorToProcedure = requestorToProcedure;
	}

	public String getCarrierName() {
		return carrierName;
	}

	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}

	public String getSelectedLineItem() {
		return selectedLineItem;
	}

	public void setSelectedLineItem(String selectedLineItem) {
		this.selectedLineItem = selectedLineItem;
	}

	public String getCloseTab() {
		return closeTab;
	}

	public void setCloseTab(String closeTab) {
		this.closeTab = closeTab;
	}

	public String getIncoTerms() {
		return incoTerms;
	}

	public void setIncoTerms(String incoTerms) {
		this.incoTerms = incoTerms;
	}

	public String getBillToUpdatable() {
		return billToUpdatable;
	}

	public void setBillToUpdatable(String billToUpdatable) {
		this.billToUpdatable = billToUpdatable;
	}

	public String getShipToUpdatable() {
		return shipToUpdatable;
	}

	public void setShipToUpdatable(String shipToUpdatable) {
		this.shipToUpdatable = shipToUpdatable;
	}

	public String getCloseOldTab() {
		return closeOldTab;
	}

	public void setCloseOldTab(String closeOldTab) {
		this.closeOldTab = closeOldTab;
	}

	public String getSelectedRowId() {
		return selectedRowId;
	}

	public void setSelectedRowId(String selectedRowId) {
		this.selectedRowId = selectedRowId;
	}

	/**
	 * @return the totalExtendedPrice
	 */
	public BigDecimal getTotalExtendedPrice() {
		return totalExtendedPrice;
	}

	/**
	 * @param totalExtendedPrice the totalExtendedPrice to set
	 */
	public void setTotalExtendedPrice(BigDecimal totalExtendedPrice) {
		this.totalExtendedPrice = totalExtendedPrice;
	}

    public BigDecimal getCreditLimitInHomeCurrency() {
        return creditLimitInHomeCurrency;
    }

    public void setCreditLimitInHomeCurrency(BigDecimal creditLimitInHomeCurrency) {
        this.creditLimitInHomeCurrency = creditLimitInHomeCurrency;
    }

    public BigDecimal getCreditLimitInOrderCurrency() {
        return creditLimitInOrderCurrency;
    }

    public void setCreditLimitInOrderCurrency(BigDecimal creditLimitInOrderCurrency) {
        this.creditLimitInOrderCurrency = creditLimitInOrderCurrency;
    }

    public String getHomeCurrencyId() {
        return homeCurrencyId;
    }

    public void setHomeCurrencyId(String homeCurrencyId) {
        this.homeCurrencyId = homeCurrencyId;
    }

    public BigDecimal getOrderToHomeConversionRate() {
        return orderToHomeConversionRate;
    }

    public void setOrderToHomeConversionRate(BigDecimal orderToHomeConversionRate) {
        this.orderToHomeConversionRate = orderToHomeConversionRate;
    }

    public String getPriceGroupName() {
        return priceGroupName;
    }

    public void setPriceGroupName(String priceGroupName) {
        this.priceGroupName = priceGroupName;
    }

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public String getCustomerPriceGroupId() {
		return customerPriceGroupId;
	}

	public void setCustomerPriceGroupId(String customerPriceGroupId) {
		this.customerPriceGroupId = customerPriceGroupId;
	}

	public Date getCustomerPoDate() {
		return customerPoDate;
	}

	public void setCustomerPoDate(Date customerPoDate) {
		this.customerPoDate = customerPoDate;
	}

	public String getDefaultPaymentTerms() {
		return defaultPaymentTerms;
	}

	public void setDefaultPaymentTerms(String defaultPaymentTerms) {
		this.defaultPaymentTerms = defaultPaymentTerms;
	}

	public BigDecimal getAvailableCreditInOrdCrncy() {
		return availableCreditInOrdCrncy;
	}

	public void setAvailableCreditInOrdCrncy(BigDecimal availableCreditInOrdCrncy) {
		this.availableCreditInOrdCrncy = availableCreditInOrdCrncy;
	}

	public String getCustomerNote() {
		return customerNote;
	}

	public void setCustomerNote(String customerNote) {
		this.customerNote = customerNote;
	}

	public String getInternalNote() {
		return internalNote;
	}

	public void setInternalNote(String internalNote) {
		this.internalNote = internalNote;
	}

	public String getShiptoNote() {
		return shiptoNote;
	}

	public void setShiptoNote(String shiptoNote) {
		this.shiptoNote = shiptoNote;
	}

	public String getMvItem() {
		return mvItem;
	}

	public void setMvItem(String mvItem) {
		this.mvItem = mvItem;
	}

	public String getBilledFlag() {
		return billedFlag;
	}

	public void setBilledFlag(String billedFlag) {
		this.billedFlag = billedFlag;
	}

	public String getCancelMRLineReason() {
		return cancelMRLineReason;
	}

	public void setCancelMRLineReason(String cancelMRLineReason) {
		this.cancelMRLineReason = cancelMRLineReason;
	}

	public String getOrderShiptoNote() {
		return orderShiptoNote;
	}

	public void setOrderShiptoNote(String orderShiptoNote) {
		this.orderShiptoNote = orderShiptoNote;
	}

	public String getNewOrderType() {
		return newOrderType;
	}

	public void setNewOrderType(String newOrderType) {
		this.newOrderType = newOrderType;
	}

	public String getQuoteType() {
		return quoteType;
	}

	public void setQuoteType(String quoteType) {
		this.quoteType = quoteType;
	}

	public String getOriginalSalesQuoteType() {
		return originalSalesQuoteType;
	}

	public void setOriginalSalesQuoteType(String originalSalesQuoteType) {
		this.originalSalesQuoteType = originalSalesQuoteType;
	}

	public String getMarginOutSideLimits() {
		return marginOutSideLimits;
	}

	public void setMarginOutSideLimits(String marginOutSideLimits) {
		this.marginOutSideLimits = marginOutSideLimits;
	}

	public String getOpsEntityName() {
		return opsEntityName;
	}

	public void setOpsEntityName(String opsEntityName) {
		this.opsEntityName = opsEntityName;
	}
    public String getEmailOrderAck() {
		return emailOrderAck;
	}
	public void setEmailOrderAck(String emailOrderAck) {
		this.emailOrderAck = emailOrderAck;
	}

	public Date getDateFirstConfirmed() {
		return dateFirstConfirmed;
	}

	public void setDateFirstConfirmed(Date dateFirstConfirmed) {
		this.dateFirstConfirmed = dateFirstConfirmed;
	}

	public String getHoldComments() {
		return holdComments;
	}

	public void setHoldComments(String holdComments) {
		this.holdComments = holdComments;
	}

	public String getOriginalHoldComments() {
		return originalHoldComments;
	}

	public void setOriginalHoldComments(String originalHoldComments) {
		this.originalHoldComments = originalHoldComments;
	}

	public String getCustomerOpsEntityId() {
		return customerOpsEntityId;
	}

	public void setCustomerOpsEntityId(String customerOpsEntityId) {
		this.customerOpsEntityId = customerOpsEntityId;
	}

	public Date getDateDelivered() {
		return dateDelivered;
	}

	public void setDateDelivered(Date dateDelivered) {
		this.dateDelivered = dateDelivered;
	}
	
}