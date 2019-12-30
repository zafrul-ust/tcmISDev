package com.tcmis.internal.report.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: OpenPicksViewBean <br>
 * @version: 1.0, Jun 11, 2009 <br>
 *****************************************************************************/


public class OpenPicksViewBean extends BaseDataBean {

	
	private static final long serialVersionUID = 5759967939685878812L;
	private String hub;
	private String hubName;
	private String inventoryGroup;
	private String inventoryGroupName;
	private String opsCompanyId;
	private String opsEntityId;
	private BigDecimal picklistId;
	private String customer;
	private String customerName;
	private String shipToLocationDesc;
	private String customerPoLine;
	private String salesOrder;
	private String catPartNo;
	private String packaging;
	private String partDescription;
	private BigDecimal pickQty;
	private String currencyId;
	private BigDecimal unitPrice;
	private Date picklistPrintDate;
	private BigDecimal extPrice;
	private BigDecimal daysOld;
	private String companyId;
	private String critical;
	private String mrNotes;
	private Date needDate;
	private String requestor;
	private String operatingEntityName;
	private Date supplierDateAccepted;
	private String needByPrefix;
	   
	//constructor
	public OpenPicksViewBean() {
	}

	//setters
	public void setHub(String hub) {
		this.hub = hub;
	}
	public void setHubName(String hubName) {
		this.hubName = hubName;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setInventoryGroupName(String inventoryGroupName) {
		this.inventoryGroupName = inventoryGroupName;
	}
	public void setOpsCompanyId(String opsCompanyId) {
		this.opsCompanyId = opsCompanyId;
	}
	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}
	public void setPicklistId(BigDecimal picklistId) {
		this.picklistId = picklistId;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public void setShipToLocationDesc(String shipToLocationDesc) {
		this.shipToLocationDesc = shipToLocationDesc;
	}
	public void setCustomerPoLine(String customerPoLine) {
		this.customerPoLine = customerPoLine;
	}
	public void setSalesOrder(String salesOrder) {
		this.salesOrder = salesOrder;
	}
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}
	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}
	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}
	public void setPickQty(BigDecimal pickQty) {
		this.pickQty = pickQty;
	}
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	public void setPicklistPrintDate(Date picklistPrintDate) {
		this.picklistPrintDate = picklistPrintDate;
	}
	public void setExtPrice(BigDecimal extPrice) {
		this.extPrice = extPrice;
	}
	public void setDaysOld(BigDecimal daysOld) {
		this.daysOld = daysOld;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setCritical(String critical) {
		this.critical = critical;
	}
	public void setMrNotes(String mrNotes) {
		this.mrNotes = mrNotes;
	}
	public void setNeedDate(Date needDate) {
		this.needDate = needDate;
	}
	public void setRequestor(String requestor) {
		this.requestor = requestor;
	}


	//getters
	public String getHub() {
		return hub;
	}
	public String getHubName() {
		return hubName;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public String getInventoryGroupName() {
		return inventoryGroupName;
	}
	public String getOpsCompanyId() {
		return opsCompanyId;
	}
	public String getOpsEntityId() {
		return opsEntityId;
	}
	public BigDecimal getPicklistId() {
		return picklistId;
	}
	public String getCustomer() {
		return customer;
	}
	public String getCustomerName() {
		return customerName;
	}
	public String getShipToLocationDesc() {
		return shipToLocationDesc;
	}
	public String getCustomerPoLine() {
		return customerPoLine;
	}
	public String getSalesOrder() {
		return salesOrder;
	}
	public String getCatPartNo() {
		return catPartNo;
	}
	public String getPackaging() {
		return packaging;
	}
	public String getPartDescription() {
		return partDescription;
	}
	public BigDecimal getPickQty() {
		return pickQty;
	}
	public String getCurrencyId() {
		return currencyId;
	}
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public Date getPicklistPrintDate() {
		return picklistPrintDate;
	}
	public BigDecimal getExtPrice() {
		return extPrice;
	}
	public BigDecimal getDaysOld() {
		return daysOld;
	}
	public String getCompanyId() {
		return companyId;
	}
	public String getCritical() {
		return critical;
	}
	public String getMrNotes() {
		return mrNotes;
	}
	public Date getNeedDate() {
		return needDate;
	}
	public String getRequestor() {
		return requestor;
	}

	public void setOperatingEntityName(String operatingEntityName) {
		this.operatingEntityName = operatingEntityName;
	}

	public String getOperatingEntityName() {
		return operatingEntityName;
	}

	public Date getSupplierDateAccepted() {
		return supplierDateAccepted;
	}

	public void setSupplierDateAccepted(Date supplierDateAccepted) {
		this.supplierDateAccepted = supplierDateAccepted;
	}

	public String getNeedByPrefix() {
		return needByPrefix;
	}

	public void setNeedByPrefix(String needByPrefix) {
		this.needByPrefix = needByPrefix;
	}
}