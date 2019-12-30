package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: IgBilledIssuesViewBean <br>
 * @version: 1.0, Apr 6, 2006 <br>
 *****************************************************************************/


public class IgBilledIssuesViewBean extends BaseDataBean {

	private String inventoryGroup;
	private String facilityId;
	private String application;
	private String companyId;
	private String catalogId;
	private String facPartNo;
	private BigDecimal prNumber;
	private String lineItem;
	private String customerPo;
	private String releaseNo;
	private String billingMethod;
	private BigDecimal itemId;
	private String packaging;
	private BigDecimal quantity;
	private Date dateDelivered;
	private BigDecimal unitPrice;
	private Date invoiceDate;
	private String partDescription;
	private BigDecimal extPrice;

	//constructor
	public IgBilledIssuesViewBean() {
	}

	//setters
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	public void setFacPartNo(String facPartNo) {
		this.facPartNo = facPartNo;
	}
	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}
	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}
	public void setCustomerPo(String customerPo) {
		this.customerPo = customerPo;
	}
	public void setReleaseNo(String releaseNo) {
		this.releaseNo = releaseNo;
	}
	public void setBillingMethod(String billingMethod) {
		this.billingMethod = billingMethod;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public void setDateDelivered(Date dateDelivered) {
		this.dateDelivered = dateDelivered;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}
	public void setExtPrice(BigDecimal extPrice) {
	 this.extPrice = extPrice;
	}

	//getters
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public String getApplication() {
		return application;
	}
	public String getCompanyId() {
		return companyId;
	}
	public String getCatalogId() {
		return catalogId;
	}
	public String getFacPartNo() {
		return facPartNo;
	}
	public BigDecimal getPrNumber() {
		return prNumber;
	}
	public String getLineItem() {
		return lineItem;
	}
	public String getCustomerPo() {
		return customerPo;
	}
	public String getReleaseNo() {
		return releaseNo;
	}
	public String getBillingMethod() {
		return billingMethod;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public String getPackaging() {
		return packaging;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public Date getDateDelivered() {
		return dateDelivered;
	}
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public Date getInvoiceDate() {
		return invoiceDate;
	}
	public String getPartDescription() {
		return partDescription;
	}
  public BigDecimal getExtPrice() {
	 return extPrice;
	}
}