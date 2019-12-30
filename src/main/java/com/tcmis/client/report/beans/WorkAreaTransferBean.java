package com.tcmis.client.report.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: WorkAreaTransferBean <br>
 * @version: 1.0, March 15, 2013 <br>
 *****************************************************************************/


public class WorkAreaTransferBean extends BaseDataBean {

	private String companyId;
	private String facilityId;
	private BigDecimal toPrNumber;
	private String toLineItem;
	private String toApplication;
	private String toApplicationDesc;
	private String catalogId;
	private String catalogDesc;
	private String facPartNo;
	private String partDescription;
	private BigDecimal itemId;
	private String itemDesc;
	private BigDecimal quantity;
	private Date transferDate;
	private BigDecimal fromPrNumber;
	private String fromLineItem;
	private String fromApplication;
	private String fromApplicationDesc;

	public String getCatalogDesc() {
		return catalogDesc;
	}

	public void setCatalogDesc(String catalogDesc) {
		this.catalogDesc = catalogDesc;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getFacPartNo() {
		return facPartNo;
	}

	public void setFacPartNo(String facPartNo) {
		this.facPartNo = facPartNo;
	}

	public String getFromApplication() {
		return fromApplication;
	}

	public void setFromApplication(String fromApplication) {
		this.fromApplication = fromApplication;
	}

	public String getFromApplicationDesc() {
		return fromApplicationDesc;
	}

	public void setFromApplicationDesc(String fromApplicationDesc) {
		this.fromApplicationDesc = fromApplicationDesc;
	}

	public String getFromLineItem() {
		return fromLineItem;
	}

	public void setFromLineItem(String fromLineItem) {
		this.fromLineItem = fromLineItem;
	}

	public BigDecimal getFromPrNumber() {
		return fromPrNumber;
	}

	public void setFromPrNumber(BigDecimal fromPrNumber) {
		this.fromPrNumber = fromPrNumber;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public String getPartDescription() {
		return partDescription;
	}

	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public String getToApplication() {
		return toApplication;
	}

	public void setToApplication(String toApplication) {
		this.toApplication = toApplication;
	}

	public String getToApplicationDesc() {
		return toApplicationDesc;
	}

	public void setToApplicationDesc(String toApplicationDesc) {
		this.toApplicationDesc = toApplicationDesc;
	}

	public String getToLineItem() {
		return toLineItem;
	}

	public void setToLineItem(String toLineItem) {
		this.toLineItem = toLineItem;
	}

	public BigDecimal getToPrNumber() {
		return toPrNumber;
	}

	public void setToPrNumber(BigDecimal toPrNumber) {
		this.toPrNumber = toPrNumber;
	}

	public Date getTransferDate() {
		return transferDate;
	}

	public void setTransferDate(Date transferDate) {
		this.transferDate = transferDate;
	}

	//constructor
	public WorkAreaTransferBean() {
	}

	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}




	
}