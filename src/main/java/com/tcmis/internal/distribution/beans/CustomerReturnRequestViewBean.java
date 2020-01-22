package com.tcmis.internal.distribution.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: CustomerReturnRequestViewBean <br>
 * @version: 1.0, Jul 14, 2009 <br>
 *****************************************************************************/

public class CustomerReturnRequestViewBean extends BaseDataBean {

	private static final long serialVersionUID = -5437988436046466260L;
	private BigDecimal customerRmaId;
	private String opsEntityId;
	private String opsEntityName;
	private String companyId;
	private BigDecimal prNumber;
	private String lineItem;
	private BigDecimal quantityReturnRequested;
	private BigDecimal quantityReturnAuthorized;
	private BigDecimal originalUnitPrice;
	private BigDecimal newUnitPrice;
	private String returnMaterial;
	private String wantReplacementMaterial;
	private String replacementCatPartNo;
	private String catalogCompanyId;
	private String catalogId;
	private String rmaStatus;
	private String returnRequestorName;
	private String returnRequestorPhone;
	private String returnRequestorEmail;
	private BigDecimal customerServiceRepId;
	private String csrName;
	private BigDecimal approverId;
	private Date requestStartDate;
	private Date approvalDate;
	private String enteredByName;
	private String approverName;
	private String defaultReplacementPart;
	private BigDecimal orderQuantity;
	private String currencyId;
	private BigDecimal unitPrice;
	private String facPartNo;
	private String customerName;
	private String partDescription;
	private String reasonCode;
	private String inventoryGroup;
	private String inventoryGroupName;
	private String poNumber;
	private String releaseNumber;
	private String facilityId;
	private String requestorName;
	private String requestorPhone;
	private String requestorEmail;
	private String haasPartNo;
	private String hub;
	private String shipToAddress;
	private String rejectionComment;
	private String returnAddress;
	private String replacementPartDescription;
	private String originalSpecList;
	private String replacementSpecList;
	private String billingAddressLine1;
	private String billingAddressLine2;
	private String billingAddressLine3;
	private String billingAddressLine4;
	private String billingAddressLine5;
	private String shipToAddressLine1;
	private String shipToAddressLine2;
	private String shipToAddressLine3;
	private String shipToAddressLine4;
	private String shipToAddressLine5;
	private String returnAddressLine1;
	private String returnAddressLine2;
	private String returnAddressLine3;
	private String returnAddressLine4;
	private String returnAddressLine5;
	private String returnPhone;
	private String returnFax;
	private String returnEmail;
	private String salesAddressLine11;
	private String salesAddressLine22;
	private String salesAddressLine33;
	private String salesAddressLine44;
	private String salesAddressLine55;
	private String salesFax;
	private String salesPhone;
	private String salesUrl;
	private String logoImageUrl;
	private BigDecimal quantityShipped;
	private String replacementLineItem;
	private Date replacementRequiredDatetime;
	private Date replacementPromiseDate;
	private String priceGroupId;
	
	private BigDecimal replacementItemId;
	private String specIdList;
	private String specLibraryList;
	private String specDetailList;
	private String specCocList;
	private String specCoaList;
	private BigDecimal customerId;
	
	private String reasonDescription;
	private String returnNotes;
	
	private String correctItemShipped;
	private String returnType;
	private String isDistribution;

	//constructor
	public CustomerReturnRequestViewBean() {
	}


	public BigDecimal getCustomerId() {
		return customerId;
	}

	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	/**
	 * @return the customerRmaId
	 */
	public BigDecimal getCustomerRmaId() {
		return customerRmaId;
	}


	/**
	 * @param customerRmaId the customerRmaId to set
	 */
	public void setCustomerRmaId(BigDecimal customerRmaId) {
		this.customerRmaId = customerRmaId;
	}


	/**
	 * @return the opsEntityId
	 */
	public String getOpsEntityId() {
		return opsEntityId;
	}


	/**
	 * @param opsEntityId the opsEntityId to set
	 */
	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}


	/**
	 * @return the opsEntityName
	 */
	public String getOpsEntityName() {
		return opsEntityName;
	}


	/**
	 * @param opsEntityName the opsEntityName to set
	 */
	public void setOpsEntityName(String opsEntityName) {
		this.opsEntityName = opsEntityName;
	}


	/**
	 * @return the companyId
	 */
	public String getCompanyId() {
		return companyId;
	}


	/**
	 * @param companyId the companyId to set
	 */
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}


	/**
	 * @return the prNumber
	 */
	public BigDecimal getPrNumber() {
		return prNumber;
	}


	/**
	 * @param prNumber the prNumber to set
	 */
	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}


	/**
	 * @return the lineItem
	 */
	public String getLineItem() {
		return lineItem;
	}


	/**
	 * @param lineItem the lineItem to set
	 */
	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}


	/**
	 * @return the quantityReturnRequested
	 */
	public BigDecimal getQuantityReturnRequested() {
		return quantityReturnRequested;
	}


	/**
	 * @param quantityReturnRequested the quantityReturnRequested to set
	 */
	public void setQuantityReturnRequested(BigDecimal quantityReturnRequested) {
		this.quantityReturnRequested = quantityReturnRequested;
	}


	/**
	 * @return the quantityReturnAuthorized
	 */
	public BigDecimal getQuantityReturnAuthorized() {
		return quantityReturnAuthorized;
	}


	/**
	 * @param quantityReturnAuthorized the quantityReturnAuthorized to set
	 */
	public void setQuantityReturnAuthorized(BigDecimal quantityReturnAuthorized) {
		this.quantityReturnAuthorized = quantityReturnAuthorized;
	}


	/**
	 * @return the originalUnitPrice
	 */
	public BigDecimal getOriginalUnitPrice() {
		return originalUnitPrice;
	}


	/**
	 * @param originalUnitPrice the originalUnitPrice to set
	 */
	public void setOriginalUnitPrice(BigDecimal originalUnitPrice) {
		this.originalUnitPrice = originalUnitPrice;
	}


	/**
	 * @return the newUnitPrice
	 */
	public BigDecimal getNewUnitPrice() {
		return newUnitPrice;
	}


	/**
	 * @param newUnitPrice the newUnitPrice to set
	 */
	public void setNewUnitPrice(BigDecimal newUnitPrice) {
		this.newUnitPrice = newUnitPrice;
	}


	/**
	 * @return the returnMaterial
	 */
	public String getReturnMaterial() {
		return returnMaterial;
	}


	/**
	 * @param returnMaterial the returnMaterial to set
	 */
	public void setReturnMaterial(String returnMaterial) {
		this.returnMaterial = returnMaterial;
	}


	/**
	 * @return the wantReplacementMaterial
	 */
	public String getWantReplacementMaterial() {
		return wantReplacementMaterial;
	}


	/**
	 * @param wantReplacementMaterial the wantReplacementMaterial to set
	 */
	public void setWantReplacementMaterial(String wantReplacementMaterial) {
		this.wantReplacementMaterial = wantReplacementMaterial;
	}


	/**
	 * @return the replacementCatPartNo
	 */
	public String getReplacementCatPartNo() {
		return replacementCatPartNo;
	}


	/**
	 * @param replacementCatPartNo the replacementCatPartNo to set
	 */
	public void setReplacementCatPartNo(String replacementCatPartNo) {
		this.replacementCatPartNo = replacementCatPartNo;
	}


	/**
	 * @return the catalogCompanyId
	 */
	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}


	/**
	 * @param catalogCompanyId the catalogCompanyId to set
	 */
	public void setCatalogCompanyId(String catalogCompanyId) {
		this.catalogCompanyId = catalogCompanyId;
	}


	/**
	 * @return the catalogId
	 */
	public String getCatalogId() {
		return catalogId;
	}


	/**
	 * @param catalogId the catalogId to set
	 */
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}


	/**
	 * @return the rmaStatus
	 */
	public String getRmaStatus() {
		return rmaStatus;
	}


	/**
	 * @param rmaStatus the rmaStatus to set
	 */
	public void setRmaStatus(String rmaStatus) {
		this.rmaStatus = rmaStatus;
	}


	/**
	 * @return the returnRequestorName
	 */
	public String getReturnRequestorName() {
		return returnRequestorName;
	}


	/**
	 * @param returnRequestorName the returnRequestorName to set
	 */
	public void setReturnRequestorName(String returnRequestorName) {
		this.returnRequestorName = returnRequestorName;
	}


	/**
	 * @return the returnRequestorPhone
	 */
	public String getReturnRequestorPhone() {
		return returnRequestorPhone;
	}


	/**
	 * @param returnRequestorPhone the returnRequestorPhone to set
	 */
	public void setReturnRequestorPhone(String returnRequestorPhone) {
		this.returnRequestorPhone = returnRequestorPhone;
	}


	/**
	 * @return the returnRequestorEmail
	 */
	public String getReturnRequestorEmail() {
		return returnRequestorEmail;
	}


	/**
	 * @param returnRequestorEmail the returnRequestorEmail to set
	 */
	public void setReturnRequestorEmail(String returnRequestorEmail) {
		this.returnRequestorEmail = returnRequestorEmail;
	}


	/**
	 * @return the customerServiceRepId
	 */
	public BigDecimal getCustomerServiceRepId() {
		return customerServiceRepId;
	}


	/**
	 * @param customerServiceRepId the customerServiceRepId to set
	 */
	public void setCustomerServiceRepId(BigDecimal customerServiceRepId) {
		this.customerServiceRepId = customerServiceRepId;
	}


	/**
	 * @return the csrName
	 */
	public String getCsrName() {
		return csrName;
	}


	/**
	 * @param csrName the csrName to set
	 */
	public void setCsrName(String csrName) {
		this.csrName = csrName;
	}


	/**
	 * @return the approverId
	 */
	public BigDecimal getApproverId() {
		return approverId;
	}


	/**
	 * @param approverId the approverId to set
	 */
	public void setApproverId(BigDecimal approverId) {
		this.approverId = approverId;
	}


	/**
	 * @return the requestStartDate
	 */
	public Date getRequestStartDate() {
		return requestStartDate;
	}


	/**
	 * @param requestStartDate the requestStartDate to set
	 */
	public void setRequestStartDate(Date requestStartDate) {
		this.requestStartDate = requestStartDate;
	}


	/**
	 * @return the approvalDate
	 */
	public Date getApprovalDate() {
		return approvalDate;
	}


	/**
	 * @param approvalDate the approvalDate to set
	 */
	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}


	/**
	 * @return the enteredByName
	 */
	public String getEnteredByName() {
		return enteredByName;
	}


	/**
	 * @param enteredByName the enteredByName to set
	 */
	public void setEnteredByName(String enteredByName) {
		this.enteredByName = enteredByName;
	}


	/**
	 * @return the approverName
	 */
	public String getApproverName() {
		return approverName;
	}


	/**
	 * @param approverName the approverName to set
	 */
	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}


	/**
	 * @return the defaultReplacementPart
	 */
	public String getDefaultReplacementPart() {
		return defaultReplacementPart;
	}


	/**
	 * @param defaultReplacementPart the defaultReplacementPart to set
	 */
	public void setDefaultReplacementPart(String defaultReplacementPart) {
		this.defaultReplacementPart = defaultReplacementPart;
	}


	/**
	 * @return the orderQuantity
	 */
	public BigDecimal getOrderQuantity() {
		return orderQuantity;
	}


	/**
	 * @param orderQuantity the orderQuantity to set
	 */
	public void setOrderQuantity(BigDecimal orderQuantity) {
		this.orderQuantity = orderQuantity;
	}


	/**
	 * @return the currencyId
	 */
	public String getCurrencyId() {
		return currencyId;
	}


	/**
	 * @param currencyId the currencyId to set
	 */
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}


	/**
	 * @return the unitPrice
	 */
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}


	/**
	 * @param unitPrice the unitPrice to set
	 */
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}


	/**
	 * @return the facPartNo
	 */
	public String getFacPartNo() {
		return facPartNo;
	}


	/**
	 * @param facPartNo the facPartNo to set
	 */
	public void setFacPartNo(String facPartNo) {
		this.facPartNo = facPartNo;
	}


	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}


	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}


	/**
	 * @return the partDescription
	 */
	public String getPartDescription() {
		return partDescription;
	}


	/**
	 * @param partDescription the partDescription to set
	 */
	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}


	/**
	 * @return the reasonCode
	 */
	public String getReasonCode() {
		return reasonCode;
	}


	/**
	 * @param reasonCode the reasonCode to set
	 */
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}


	/**
	 * @return the inventoryGroup
	 */
	public String getInventoryGroup() {
		return inventoryGroup;
	}


	/**
	 * @param inventoryGroup the inventoryGroup to set
	 */
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}


	/**
	 * @return the inventoryGroupName
	 */
	public String getInventoryGroupName() {
		return inventoryGroupName;
	}


	/**
	 * @param inventoryGroupName the inventoryGroupName to set
	 */
	public void setInventoryGroupName(String inventoryGroupName) {
		this.inventoryGroupName = inventoryGroupName;
	}


	/**
	 * @return the poNumber
	 */
	public String getPoNumber() {
		return poNumber;
	}


	/**
	 * @param poNumber the poNumber to set
	 */
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}


	/**
	 * @return the releaseNumber
	 */
	public String getReleaseNumber() {
		return releaseNumber;
	}


	/**
	 * @param releaseNumber the releaseNumber to set
	 */
	public void setReleaseNumber(String releaseNumber) {
		this.releaseNumber = releaseNumber;
	}


	/**
	 * @return the facilityId
	 */
	public String getFacilityId() {
		return facilityId;
	}


	/**
	 * @param facilityId the facilityId to set
	 */
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}


	/**
	 * @return the requestorName
	 */
	public String getRequestorName() {
		return requestorName;
	}


	/**
	 * @param requestorName the requestorName to set
	 */
	public void setRequestorName(String requestorName) {
		this.requestorName = requestorName;
	}


	/**
	 * @return the requestorPhone
	 */
	public String getRequestorPhone() {
		return requestorPhone;
	}


	/**
	 * @param requestorPhone the requestorPhone to set
	 */
	public void setRequestorPhone(String requestorPhone) {
		this.requestorPhone = requestorPhone;
	}


	/**
	 * @return the requestorEmail
	 */
	public String getRequestorEmail() {
		return requestorEmail;
	}


	/**
	 * @param requestorEmail the requestorEmail to set
	 */
	public void setRequestorEmail(String requestorEmail) {
		this.requestorEmail = requestorEmail;
	}


	/**
	 * @return the haasPartNo
	 */
	public String getHaasPartNo() {
		return haasPartNo;
	}


	/**
	 * @param haasPartNo the haasPartNo to set
	 */
	public void setHaasPartNo(String haasPartNo) {
		this.haasPartNo = haasPartNo;
	}


	/**
	 * @return the hub
	 */
	public String getHub() {
		return hub;
	}


	/**
	 * @param hub the hub to set
	 */
	public void setHub(String hub) {
		this.hub = hub;
	}


	/**
	 * @return the shipToAddress
	 */
	public String getShipToAddress() {
		return shipToAddress;
	}


	/**
	 * @param shipToAddress the shipToAddress to set
	 */
	public void setShipToAddress(String shipToAddress) {
		this.shipToAddress = shipToAddress;
	}


	/**
	 * @return the rejectionComment
	 */
	public String getRejectionComment() {
		return rejectionComment;
	}


	/**
	 * @param rejectionComment the rejectionComment to set
	 */
	public void setRejectionComment(String rejectionComment) {
		this.rejectionComment = rejectionComment;
	}


	/**
	 * @return the returnAddress
	 */
	public String getReturnAddress() {
		return returnAddress;
	}


	/**
	 * @param returnAddress the returnAddress to set
	 */
	public void setReturnAddress(String returnAddress) {
		this.returnAddress = returnAddress;
	}


	/**
	 * @return the replacementPartDescription
	 */
	public String getReplacementPartDescription() {
		return replacementPartDescription;
	}


	/**
	 * @param replacementPartDescription the replacementPartDescription to set
	 */
	public void setReplacementPartDescription(String replacementPartDescription) {
		this.replacementPartDescription = replacementPartDescription;
	}


	/**
	 * @return the originalSpecList
	 */
	public String getOriginalSpecList() {
		return originalSpecList;
	}


	/**
	 * @param originalSpecList the originalSpecList to set
	 */
	public void setOriginalSpecList(String originalSpecList) {
		this.originalSpecList = originalSpecList;
	}


	/**
	 * @return the replacementSpecList
	 */
	public String getReplacementSpecList() {
		return replacementSpecList;
	}


	/**
	 * @param replacementSpecList the replacementSpecList to set
	 */
	public void setReplacementSpecList(String replacementSpecList) {
		this.replacementSpecList = replacementSpecList;
	}


	/**
	 * @return the billingAddressLine1
	 */
	public String getBillingAddressLine1() {
		return billingAddressLine1;
	}


	/**
	 * @param billingAddressLine1 the billingAddressLine1 to set
	 */
	public void setBillingAddressLine1(String billingAddressLine1) {
		this.billingAddressLine1 = billingAddressLine1;
	}


	/**
	 * @return the billingAddressLine2
	 */
	public String getBillingAddressLine2() {
		return billingAddressLine2;
	}


	/**
	 * @param billingAddressLine2 the billingAddressLine2 to set
	 */
	public void setBillingAddressLine2(String billingAddressLine2) {
		this.billingAddressLine2 = billingAddressLine2;
	}


	/**
	 * @return the billingAddressLine3
	 */
	public String getBillingAddressLine3() {
		return billingAddressLine3;
	}


	/**
	 * @param billingAddressLine3 the billingAddressLine3 to set
	 */
	public void setBillingAddressLine3(String billingAddressLine3) {
		this.billingAddressLine3 = billingAddressLine3;
	}


	/**
	 * @return the billingAddressLine4
	 */
	public String getBillingAddressLine4() {
		return billingAddressLine4;
	}


	/**
	 * @param billingAddressLine4 the billingAddressLine4 to set
	 */
	public void setBillingAddressLine4(String billingAddressLine4) {
		this.billingAddressLine4 = billingAddressLine4;
	}


	/**
	 * @return the billingAddressLine5
	 */
	public String getBillingAddressLine5() {
		return billingAddressLine5;
	}


	/**
	 * @param billingAddressLine5 the billingAddressLine5 to set
	 */
	public void setBillingAddressLine5(String billingAddressLine5) {
		this.billingAddressLine5 = billingAddressLine5;
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
	 * @return the returnAddressLine1
	 */
	public String getReturnAddressLine1() {
		return returnAddressLine1;
	}


	/**
	 * @param returnAddressLine1 the returnAddressLine1 to set
	 */
	public void setReturnAddressLine1(String returnAddressLine1) {
		this.returnAddressLine1 = returnAddressLine1;
	}


	/**
	 * @return the returnAddressLine2
	 */
	public String getReturnAddressLine2() {
		return returnAddressLine2;
	}


	/**
	 * @param returnAddressLine2 the returnAddressLine2 to set
	 */
	public void setReturnAddressLine2(String returnAddressLine2) {
		this.returnAddressLine2 = returnAddressLine2;
	}


	/**
	 * @return the returnAddressLine3
	 */
	public String getReturnAddressLine3() {
		return returnAddressLine3;
	}


	/**
	 * @param returnAddressLine3 the returnAddressLine3 to set
	 */
	public void setReturnAddressLine3(String returnAddressLine3) {
		this.returnAddressLine3 = returnAddressLine3;
	}


	/**
	 * @return the returnAddressLine4
	 */
	public String getReturnAddressLine4() {
		return returnAddressLine4;
	}


	/**
	 * @param returnAddressLine4 the returnAddressLine4 to set
	 */
	public void setReturnAddressLine4(String returnAddressLine4) {
		this.returnAddressLine4 = returnAddressLine4;
	}


	/**
	 * @return the returnAddressLine5
	 */
	public String getReturnAddressLine5() {
		return returnAddressLine5;
	}


	/**
	 * @param returnAddressLine5 the returnAddressLine5 to set
	 */
	public void setReturnAddressLine5(String returnAddressLine5) {
		this.returnAddressLine5 = returnAddressLine5;
	}


	/**
	 * @return the returnPhone
	 */
	public String getReturnPhone() {
		return returnPhone;
	}


	/**
	 * @param returnPhone the returnPhone to set
	 */
	public void setReturnPhone(String returnPhone) {
		this.returnPhone = returnPhone;
	}


	/**
	 * @return the returnFax
	 */
	public String getReturnFax() {
		return returnFax;
	}


	/**
	 * @param returnFax the returnFax to set
	 */
	public void setReturnFax(String returnFax) {
		this.returnFax = returnFax;
	}


	/**
	 * @return the returnEmail
	 */
	public String getReturnEmail() {
		return returnEmail;
	}


	/**
	 * @param returnEmail the returnEmail to set
	 */
	public void setReturnEmail(String returnEmail) {
		this.returnEmail = returnEmail;
	}


	/**
	 * @return the salesAddressLine11
	 */
	public String getSalesAddressLine11() {
		return salesAddressLine11;
	}


	/**
	 * @param salesAddressLine11 the salesAddressLine11 to set
	 */
	public void setSalesAddressLine11(String salesAddressLine11) {
		this.salesAddressLine11 = salesAddressLine11;
	}


	/**
	 * @return the salesAddressLine22
	 */
	public String getSalesAddressLine22() {
		return salesAddressLine22;
	}


	/**
	 * @param salesAddressLine22 the salesAddressLine22 to set
	 */
	public void setSalesAddressLine22(String salesAddressLine22) {
		this.salesAddressLine22 = salesAddressLine22;
	}


	/**
	 * @return the salesAddressLine33
	 */
	public String getSalesAddressLine33() {
		return salesAddressLine33;
	}


	/**
	 * @param salesAddressLine33 the salesAddressLine33 to set
	 */
	public void setSalesAddressLine33(String salesAddressLine33) {
		this.salesAddressLine33 = salesAddressLine33;
	}


	/**
	 * @return the salesAddressLine44
	 */
	public String getSalesAddressLine44() {
		return salesAddressLine44;
	}


	/**
	 * @param salesAddressLine44 the salesAddressLine44 to set
	 */
	public void setSalesAddressLine44(String salesAddressLine44) {
		this.salesAddressLine44 = salesAddressLine44;
	}


	/**
	 * @return the salesAddressLine55
	 */
	public String getSalesAddressLine55() {
		return salesAddressLine55;
	}


	/**
	 * @param salesAddressLine55 the salesAddressLine55 to set
	 */
	public void setSalesAddressLine55(String salesAddressLine55) {
		this.salesAddressLine55 = salesAddressLine55;
	}


	/**
	 * @return the salesFax
	 */
	public String getSalesFax() {
		return salesFax;
	}


	/**
	 * @param salesFax the salesFax to set
	 */
	public void setSalesFax(String salesFax) {
		this.salesFax = salesFax;
	}


	/**
	 * @return the salesPhone
	 */
	public String getSalesPhone() {
		return salesPhone;
	}


	/**
	 * @param salesPhone the salesPhone to set
	 */
	public void setSalesPhone(String salesPhone) {
		this.salesPhone = salesPhone;
	}


	/**
	 * @return the salesUrl
	 */
	public String getSalesUrl() {
		return salesUrl;
	}


	/**
	 * @param salesUrl the salesUrl to set
	 */
	public void setSalesUrl(String salesUrl) {
		this.salesUrl = salesUrl;
	}


	/**
	 * @return the logoImageUrl
	 */
	public String getLogoImageUrl() {
		return logoImageUrl;
	}


	/**
	 * @param logoImageUrl the logoImageUrl to set
	 */
	public void setLogoImageUrl(String logoImageUrl) {
		this.logoImageUrl = logoImageUrl;
	}


	/**
	 * @return the quantityShipped
	 */
	public BigDecimal getQuantityShipped() {
		return quantityShipped;
	}


	/**
	 * @param quantityShipped the quantityShipped to set
	 */
	public void setQuantityShipped(BigDecimal quantityShipped) {
		this.quantityShipped = quantityShipped;
	}


	/**
	 * @return the replacementLineItem
	 */
	public String getReplacementLineItem() {
		return replacementLineItem;
	}


	/**
	 * @param replacementLineItem the replacementLineItem to set
	 */
	public void setReplacementLineItem(String replacementLineItem) {
		this.replacementLineItem = replacementLineItem;
	}


	/**
	 * @return the replacementRequiredDatetime
	 */
	public Date getReplacementRequiredDatetime() {
		return replacementRequiredDatetime;
	}


	/**
	 * @param replacementRequiredDatetime the replacementRequiredDatetime to set
	 */
	public void setReplacementRequiredDatetime(Date replacementRequiredDatetime) {
		this.replacementRequiredDatetime = replacementRequiredDatetime;
	}


	/**
	 * @return the replacementPromiseDate
	 */
	public Date getReplacementPromiseDate() {
		return replacementPromiseDate;
	}


	/**
	 * @param replacementPromiseDate the replacementPromiseDate to set
	 */
	public void setReplacementPromiseDate(Date replacementPromiseDate) {
		this.replacementPromiseDate = replacementPromiseDate;
	}


	public void setPriceGroupId(String priceGroupId) {
		this.priceGroupId = priceGroupId;
	}


	public String getPriceGroupId() {
		return priceGroupId;
	}


	public BigDecimal getReplacementItemId() {
		return replacementItemId;
	}


	public void setReplacementItemId(BigDecimal replacementItemId) {
		this.replacementItemId = replacementItemId;
	}


	public String getSpecCoaList() {
		return specCoaList;
	}


	public void setSpecCoaList(String specCoaList) {
		this.specCoaList = specCoaList;
	}


	public String getSpecCocList() {
		return specCocList;
	}


	public void setSpecCocList(String specCocList) {
		this.specCocList = specCocList;
	}


	public String getSpecDetailList() {
		return specDetailList;
	}


	public void setSpecDetailList(String specDetailList) {
		this.specDetailList = specDetailList;
	}


	public String getSpecIdList() {
		return specIdList;
	}


	public void setSpecIdList(String specIdList) {
		this.specIdList = specIdList;
	}


	public String getSpecLibraryList() {
		return specLibraryList;
	}


	public void setSpecLibraryList(String specLibraryList) {
		this.specLibraryList = specLibraryList;
	}


	public String getReasonDescription() {
		return reasonDescription;
	}


	public void setReasonDescription(String reasonDescription) {
		this.reasonDescription = reasonDescription;
	}


	public String getReturnNotes() {
		return returnNotes;
	}


	public void setReturnNotes(String returnNotes) {
		this.returnNotes = returnNotes;
	}


	public String getCorrectItemShipped() {
		return correctItemShipped;
	}


	public void setCorrectItemShipped(String correctItemShipped) {
		this.correctItemShipped = correctItemShipped;
	}


	public String getReturnType() {
		return returnType;
	}


	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}


	public String getIsDistribution() {
		return isDistribution;
	}


	public void setIsDistribution(String isDistribution) {
		this.isDistribution = isDistribution;
	}

	
}