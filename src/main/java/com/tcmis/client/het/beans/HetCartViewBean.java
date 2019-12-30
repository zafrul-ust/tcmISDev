package com.tcmis.client.het.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: HetRecipeView Bean <br>
 * @version: 1.0, May 31, 2011 <br>
 *****************************************************************************/

public class HetCartViewBean extends BaseDataBean {

	private String applicationDesc;
	private BigDecimal applicationId;
	private BigDecimal cartId;
	private String cartName;
	private String cartStatus;
	private String catPartNo;
	private Date checkedOut;
	private String companyId;
	private String containerId;
	private String containerSize;
	private String custMsdsNo;
	private String employee;
	private String facilityId;
	private BigDecimal itemId;
	private BigDecimal kitId;
	private String materialDesc;
	private String reportingEntityId;
	private BigDecimal sortOrder;

	//constructor
	public HetCartViewBean() {
	}

	public String getApplicationDesc() {
		return applicationDesc;
	}

	//setters
	public BigDecimal getApplicationId() {
		return applicationId;
	}

	public BigDecimal getCartId() {
		return cartId;
	}

	public String getCartName() {
		return cartName;
	}

	public String getCartStatus() {
		return cartStatus;
	}

	public String getCatPartNo() {
		return catPartNo;
	}

	public Date getCheckedOut() {
		return checkedOut;
	}

	public String getCompanyId() {
		return companyId;
	}

	public String getContainerId() {
		return containerId;
	}

	public String getContainerSize() {
		return containerSize;
	}

	public String getCustMsdsNo() {
		return custMsdsNo;
	}

	public String getEmployee() {
		return employee;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public BigDecimal getKitId() {
		return kitId;
	}

	public String getMaterialDesc() {
		return materialDesc;
	}

	public String getReportingEntityId() {
		return reportingEntityId;
	}

	public BigDecimal getSortOrder() {
		return sortOrder;
	}

	public boolean isActive() {
		return "A".equals(cartStatus);
	}

	public void setApplicationDesc(String application) {
		this.applicationDesc = application;
	}

	//getters
	public void setApplicationId(BigDecimal applicationId) {
		this.applicationId = applicationId;
	}

	public void setCartId(BigDecimal cartId) {
		this.cartId = cartId;
	}

	public void setCartName(String cartName) {
		this.cartName = cartName;
	}

	public void setCartStatus(String cartStatus) {
		this.cartStatus = cartStatus;
	}

	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}

	public void setCheckedOut(Date checkedOut) {
		this.checkedOut = checkedOut;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setContainerId(String containerId) {
		this.containerId = containerId;
	}

	public void setContainerSize(String containerSize) {
		this.containerSize = containerSize;
	}

	public void setCustMsdsNo(String custMsdsNo) {
		this.custMsdsNo = custMsdsNo;
	}

	public void setEmployee(String employee) {
		this.employee = employee;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public void setKitId(BigDecimal kitId) {
		this.kitId = kitId;
	}

	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}

	public void setReportingEntityId(String reportingEntityId) {
		this.reportingEntityId = reportingEntityId;
	}

	public void setSortOrder(BigDecimal sortOrder) {
		this.sortOrder = sortOrder;
	}
}