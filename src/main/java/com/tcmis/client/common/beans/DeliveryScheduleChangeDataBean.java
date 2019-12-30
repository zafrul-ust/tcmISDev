package com.tcmis.client.common.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

public class DeliveryScheduleChangeDataBean extends BaseDataBean {

	private String companyId;
	private BigDecimal prNumber;
	private String facilityId;
	private String facilityName;
	private String requestorName;
	private BigDecimal quantity;
	private String facPartNo;
	private String partDesc;
	private String lineItem;
	private Date dateToDeliver;
	private BigDecimal originalQty;
	private BigDecimal revisedQty;
	private Date updatedDate;
	private BigDecimal updatedBy;
	private String updatedByName;
	private BigDecimal expediteApproval;
	private String expediteApprovalName;
	private BigDecimal csrApproval;
	private String csrApprovalName;
	private BigDecimal salesOrder;
	private BigDecimal itemId;
	private BigDecimal qty;
	private Date expediteApprovalDate;
	private Date csrApprovalDate;
	
	public Date getDateToDeliver() {
		return dateToDeliver;
	}
	public void setDateToDeliver(Date dateToDeliver) {
		this.dateToDeliver = dateToDeliver;
	}
	public BigDecimal getOriginalQty() {
		return originalQty;
	}
	public void setOriginalQty(BigDecimal originalQty) {
		this.originalQty = originalQty;
	}
	public BigDecimal getRevisedQty() {
		return revisedQty;
	}
	public void setRevisedQty(BigDecimal revisedQty) {
		this.revisedQty = revisedQty;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public BigDecimal getPrNumber() {
		return prNumber;
	}
	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}
	public String getLineItem() {
		return lineItem;
	}
	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	public BigDecimal getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(BigDecimal updatedBy) {
		this.updatedBy = updatedBy;
	}
	public BigDecimal getExpediteApproval() {
		return expediteApproval;
	}
	public void setExpediteApproval(BigDecimal expediteApproval) {
		this.expediteApproval = expediteApproval;
	}
	public BigDecimal getCsrApproval() {
		return csrApproval;
	}
	public void setCsrApproval(BigDecimal csrApproval) {
		this.csrApproval = csrApproval;
	}
	public BigDecimal getSalesOrder() {
		return salesOrder;
	}
	public void setSalesOrder(BigDecimal salesOrder) {
		this.salesOrder = salesOrder;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public BigDecimal getQty() {
		return qty;
	}
	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public String getFacilityName() {
		return facilityName;
	}
	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}
	public String getRequestorName() {
		return requestorName;
	}
	public void setRequestorName(String requestorName) {
		this.requestorName = requestorName;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public String getFacPartNo() {
		return facPartNo;
	}
	public void setFacPartNo(String facPartNo) {
		this.facPartNo = facPartNo;
	}
	public String getPartDesc() {
		return partDesc;
	}
	public void setPartDesc(String partDesc) {
		this.partDesc = partDesc;
	}
	public Date getExpediteApprovalDate() {
		return expediteApprovalDate;
	}
	public void setExpediteApprovalDate(Date expediteApprovalDate) {
		this.expediteApprovalDate = expediteApprovalDate;
	}
	public Date getCsrApprovalDate() {
		return csrApprovalDate;
	}
	public void setCsrApprovalDate(Date csrApprovalDate) {
		this.csrApprovalDate = csrApprovalDate;
	}
	public String getUpdatedByName() {
		return updatedByName;
	}
	public void setUpdatedByName(String updatedByName) {
		this.updatedByName = updatedByName;
	}
	public String getExpediteApprovalName() {
		return expediteApprovalName;
	}
	public void setExpediteApprovalName(String expediteApprovalName) {
		this.expediteApprovalName = expediteApprovalName;
	}
	public String getCsrApprovalName() {
		return csrApprovalName;
	}
	public void setCsrApprovalName(String csrApprovalName) {
		this.csrApprovalName = csrApprovalName;
	}
}
