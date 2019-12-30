package com.tcmis.internal.distribution.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

public class CustomerReturnRequestInputBean extends BaseDataBean {
		
	
	private static final long serialVersionUID = -7033842200708915355L;
	private String operatingEntityId;
	private String prNumber;
	private String lineItem;
	private String shipReplacement="No";
	private String returnShippedMaterial="No";
	private String quantity;
	private String unitPriceCurrenty;
	private String unitPrice;
	private String action;
	private String rmaStatus;
	private BigDecimal personnelId;
	private String companyId;
	private String opsEntityId;
	private BigDecimal rmaId;
	private String requestorName;
	private String requestorEmail;
	private String requestorPhone;
	private BigDecimal returnQuantityRequested;
	private String wantReplacement;
	private BigDecimal newUnitPrice;
	private String replacementCartPN;
	private String returnMaterial;
	private String copyReasonBean;
	private String csrName;	
	private BigDecimal quantityReturnAuthorized;	
	private String copyOriginalFeesBean;
	private String copyNewFeesBean;
	private String reasonsDeleteOnly;
	private String newFeesDeleteOnly;
	private String denialComments;
    private String replacementCatPartNo;
    private BigDecimal csrId;
    private BigDecimal customerServiceRepId;
    private BigDecimal originalUnitPrice;
	private String replacementLineItem;
	private Date replacementRequiredDatetime;
	private Date replacementPromiseDate;
	private String catalogCompanyId;
	
	private BigDecimal replacementItemId;
	private String specListConcat;
	private String detailConcat;
	private String specLibraryList;
	private String cocConcat;
	private String coaConcat;
	private String inventoryGroup;
	
	private String returnNotes;
	private String correctItemShipped;

    public String getInventoryGroup() {
        return inventoryGroup;
    }

    public void setInventoryGroup(String inventoryGroup) {
        this.inventoryGroup = inventoryGroup;
    }

    public String getCoaConcat() {
		return coaConcat;
	}

	public void setCoaConcat(String coaConcat) {
		this.coaConcat = coaConcat;
	}

	public String getCocConcat() {
		return cocConcat;
	}

	public void setCocConcat(String cocConcat) {
		this.cocConcat = cocConcat;
	}

	public String getDetailConcat() {
		return detailConcat;
	}

	public void setDetailConcat(String detailConcat) {
		this.detailConcat = detailConcat;
	}

	public String getSpecLibraryList() {
		return specLibraryList;
	}

	public void setSpecLibraryList(String specLibraryList) {
		this.specLibraryList = specLibraryList;
	}

	public String getSpecListConcat() {
		return specListConcat;
	}

	public void setSpecListConcat(String specListConcat) {
		this.specListConcat = specListConcat;
	}

	public String getReplacementCatPartNo() {
        return replacementCatPartNo;
    }

    public void setReplacementCatPartNo(String replacementCatPartNo) {
        this.replacementCatPartNo = replacementCatPartNo;
    }

    /**
	 * @return the operatingEntityId
	 */
	public String getOperatingEntityId() {
		return operatingEntityId;
	}

	/**
	 * @param operatingEntityId the operatingEntityId to set
	 */
	public void setOperatingEntityId(String operatingEntityId) {
		this.operatingEntityId = operatingEntityId;
	}

	/**
	 * @return the prNumber
	 */
	public String getPrNumber() {
		return prNumber;
	}

	/**
	 * @param prNumber the prNumber to set
	 */
	public void setPrNumber(String prNumber) {
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
	 * @return the shipReplacement
	 */
	public String getShipReplacement() {
		return shipReplacement;
	}

	/**
	 * @param shipReplacement the shipReplacement to set
	 */
	public void setShipReplacement(String shipReplacement) {
		this.shipReplacement = shipReplacement;
	}

	/**
	 * @return the returnShippedMaterial
	 */
	public String getReturnShippedMaterial() {
		return returnShippedMaterial;
	}

	/**
	 * @param returnShippedMaterial the returnShippedMaterial to set
	 */
	public void setReturnShippedMaterial(String returnShippedMaterial) {
		this.returnShippedMaterial = returnShippedMaterial;
	}

	/**
	 * @return the quantity
	 */
	public String getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the unitPriceCurrenty
	 */
	public String getUnitPriceCurrenty() {
		return unitPriceCurrenty;
	}

	/**
	 * @param unitPriceCurrenty the unitPriceCurrenty to set
	 */
	public void setUnitPriceCurrenty(String unitPriceCurrenty) {
		this.unitPriceCurrenty = unitPriceCurrenty;
	}

	/**
	 * @return the unitPrice
	 */
	public String getUnitPrice() {
		return unitPrice;
	}

	/**
	 * @param unitPrice the unitPrice to set
	 */
	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}


	public void setAction(String action) {
		this.action = action;
	}

	public String getAction() {
		return action;
	}

	public void setRmaStatus(String rmaStatus) {
		this.rmaStatus = rmaStatus;
	}

	public String getRmaStatus() {
		return rmaStatus;
	}


	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}

	public String getOpsEntityId() {
		return opsEntityId;
	}

	public void setRmaId(BigDecimal rmaId) {
		this.rmaId = rmaId;
	}

	public BigDecimal getRmaId() {
		return rmaId;
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
	 * @return the returnQuantityRequested
	 */
	public BigDecimal getReturnQuantityRequested() {
		return returnQuantityRequested;
	}

	/**
	 * @param returnQuantityRequested the returnQuantityRequested to set
	 */
	public void setReturnQuantityRequested(BigDecimal returnQuantityRequested) {
		this.returnQuantityRequested = returnQuantityRequested;
	}

	/**
	 * @return the wantReplacement
	 */
	public String getWantReplacement() {
		return wantReplacement;
	}

	/**
	 * @param wantReplacement the wantReplacement to set
	 */
	public void setWantReplacement(String wantReplacement) {
		this.wantReplacement = wantReplacement;
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

	public void setReplacementCartPN(String replacementCartPN) {
		this.replacementCartPN = replacementCartPN;
	}

	public String getReplacementCartPN() {
		return replacementCartPN;
	}

	public void setCopyReasonBean(String copyReasonBean) {
		this.copyReasonBean = copyReasonBean;
	}

	public String getCopyReasonBean() {
		return copyReasonBean;
	}

	public void setCsrName(String csrName) {
		this.csrName = csrName;
	}

	public String getCsrName() {
		return csrName;
	}

	public void setQuantityReturnAuthorized(BigDecimal quantityReturnAuthorized) {
		this.quantityReturnAuthorized = quantityReturnAuthorized;
	}

	public BigDecimal getQuantityReturnAuthorized() {
		return quantityReturnAuthorized;
	}


	public void setCopyOriginalFeesBean(String copyOriginalFeesBean) {
		this.copyOriginalFeesBean = copyOriginalFeesBean;
	}

	public String getCopyOriginalFeesBean() {
		return copyOriginalFeesBean;
	}

	public void setCopyNewFeesBean(String copyNewFeesBean) {
		this.copyNewFeesBean = copyNewFeesBean;
	}

	public String getCopyNewFeesBean() {
		return copyNewFeesBean;
	}

	public void setReasonsDeleteOnly(String reasonsDeleteOnly) {
		this.reasonsDeleteOnly = reasonsDeleteOnly;
	}

	public String getReasonsDeleteOnly() {
		return reasonsDeleteOnly;
	}

	public void setNewFeesDeleteOnly(String newFeesDeleteOnly) {
		this.newFeesDeleteOnly = newFeesDeleteOnly;
	}

	public String getNewFeesDeleteOnly() {
		return newFeesDeleteOnly;
	}

	public void setDenialComments(String denialComments) {
		this.denialComments = denialComments;
	}

	public String getDenialComments() {
		return denialComments;
	}

	public void setCsrId(BigDecimal csrId) {
		this.csrId = csrId;
	}

	public BigDecimal getCsrId() {
		return csrId;
	}

	public void setOriginalUnitPrice(BigDecimal originalUnitPrice) {
		this.originalUnitPrice = originalUnitPrice;
	}

	public BigDecimal getOriginalUnitPrice() {
		return originalUnitPrice;
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

	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
	}

	public BigDecimal getPersonnelId() {
		return personnelId;
	}

	public void setCustomerServiceRepId(BigDecimal customerServiceRepId) {
		this.customerServiceRepId = customerServiceRepId;
	}

	public BigDecimal getCustomerServiceRepId() {
		return customerServiceRepId;
	}

	public void setCatalogCompanyId(String catalogCompanyId) {
		this.catalogCompanyId = catalogCompanyId;
	}

	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}

	public BigDecimal getReplacementItemId() {
		return replacementItemId;
	}

	public void setReplacementItemId(BigDecimal replacementItemId) {
		this.replacementItemId = replacementItemId;
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

	


	
	
}
