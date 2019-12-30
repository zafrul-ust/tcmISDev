package com.tcmis.client.common.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: LineAllocationViewBean <br>
 * @version: 1.0, Nov 1, 2010 <br>
 *****************************************************************************/

public class LineAllocationViewBean extends BaseDataBean {

	private String catalogId;
	private String catPartNo;
	private String companyId;
	private String description;
	private String docType;
	private String hub;
	private String hubName;
	private String inventoryGroup;
	private BigDecimal itemId;
	private String lineItem;
	private BigDecimal prNumber;
	private BigDecimal quantity;
	private String status;

	//constructor
	public LineAllocationViewBean() {
	}

	public String getCatalogId() {
		return catalogId;
	}

	public String getCatPartNo() {
		return catPartNo;
	}

	public String getCompanyId() {
		return companyId;
	}

	//getters
	public String getDescription() {
		return description;
	}

	public String getDocType() {
		return docType;
	}

	public String getHub() {
		return hub;
	}

	public String getHubName() {
		return hubName;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public String getLineItem() {
		return lineItem;
	}

	public BigDecimal getPrNumber() {
		return prNumber;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public String getStatus() {
		return status;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	//setters
	public void setDescription(String description) {
		this.description = description;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public void setHub(String hub) {
		this.hub = hub;
	}

	public void setHubName(String hubName) {
		this.hubName = hubName;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}

	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}