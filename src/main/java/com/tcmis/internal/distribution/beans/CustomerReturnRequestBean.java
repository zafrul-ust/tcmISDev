package com.tcmis.internal.distribution.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: CustomerReturnRequestBean <br>
 * @version: 1.0, Jul 14, 2009 <br>
 *****************************************************************************/


public class CustomerReturnRequestBean extends BaseDataBean {
	
	
	
	//private BigDecimal csrPersonnelId;	
	
	
	private static final long serialVersionUID = -6528122199124718149L;
	private BigDecimal customerRmaId;
	private String companyId;
	private BigDecimal prNumber;
	private String lineItem;
	private String opsEntityId;
	private BigDecimal quantityReturnRequested;
	private BigDecimal quantityReturnAuthorized;
	private BigDecimal originalUnitPrice;
	private BigDecimal newUnitPrice;
	private String returnMaterial;
	private String wantReplacementMaterial;
	private String returnRequestorName;
	private String rmaStatus;
	private String returnRequestorPhone;
	private String returnRequestorEmail;
	private BigDecimal customerServiceRepId;
	private BigDecimal approverId;
	private String catalogCompanyId;
	private Date requestStartDate;
	private String catalogId;
	private Date approvalDate;
	private String replacementCatPartNo;
	private String rejectionComment;
	private String replacementLineItem;
	private Date replacementRequiredDatetime;
	private Date replacementPromiseDate;
	

	//constructor
	public CustomerReturnRequestBean() {
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

	
}