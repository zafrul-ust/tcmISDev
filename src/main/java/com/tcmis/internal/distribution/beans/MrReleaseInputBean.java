package com.tcmis.internal.distribution.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

public class MrReleaseInputBean extends BaseDataBean {
	private static final long serialVersionUID = 1L;
	
	private String opsEntityId;
	private String hub;
	private String inventoryGroup;
	private String customerName;
	private Date confirmFromDate;
	private Date confirmToDate;	
	private String searchArgument;	
	private String searchField;
    private String searchMode;
    private String personnelName;
    private BigDecimal personnelId;
    private BigDecimal customerId;   
	private String[] orderStatus;
	private String action;
    private BigDecimal prNumber;
    private String lineItem;
    private String companyId;
	
    private String cancelMRReason;
    private String rcptQualityHoldNote;
    
    private Date promiseDate;
    private Date originalPromiseDate;
	
	public Date getPromiseDate() {
		return promiseDate;
	}
	public void setPromiseDate(Date promiseDate) {
		this.promiseDate = promiseDate;
	}
	public String getRcptQualityHoldNote() {
		return rcptQualityHoldNote;
	}
	public void setRcptQualityHoldNote(String rcptQualityHoldNote) {
		this.rcptQualityHoldNote = rcptQualityHoldNote;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getHub() {
		return hub;
	}
	public void setHub(String hub) {
		this.hub = hub;
	}	
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public void setOrderStatus(String[] orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String[] getOrderStatus() {
		return orderStatus;
	}	
	public void setSearchArgument(String searchArgument) {
		this.searchArgument = searchArgument;
	}
	public String getSearchArgument() {
		return searchArgument;
	}
	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}
	public String getSearchField() {
		return searchField;
	}
	public void setSearchMode(String searchMode) {
		this.searchMode = searchMode;
	}
	public String getSearchMode() {
		return searchMode;
	}
	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
	}
	public BigDecimal getPersonnelId() {
		return personnelId;
	}
	public void setPersonnelName(String personnelName) {
		this.personnelName = personnelName;
	}
	public String getPersonnelName() {
		return personnelName;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getAction() {
		return action;
	}
	public void setConfirmFromDate(Date confirmFromDate) {
		this.confirmFromDate = confirmFromDate;
	}
	public Date getConfirmFromDate() {
		return confirmFromDate;
	}
	public void setConfirmToDate(Date confirmToDate) {
		this.confirmToDate = confirmToDate;
	}
	public Date getConfirmToDate() {
		return confirmToDate;
	}
	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}
	public BigDecimal getPrNumber() {
		return prNumber;
	}
	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}
	public String getLineItem() {
		return lineItem;
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
	public String getCancelMRReason() {
		return cancelMRReason;
	}
	public void setCancelMRReason(String cancelMRReason) {
		this.cancelMRReason = cancelMRReason;
	}
	public Date getOriginalPromiseDate() {
		return originalPromiseDate;
	}
	public void setOriginalPromiseDate(Date originalPromiseDate) {
		this.originalPromiseDate = originalPromiseDate;
	}
		
}
