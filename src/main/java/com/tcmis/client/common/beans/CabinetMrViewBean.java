package com.tcmis.client.common.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: CabinetMrViewBean <br>
 * @version: 1.0, Nov 1, 2010 <br>
 *****************************************************************************/

public class CabinetMrViewBean extends BaseDataBean {

	private String application;
	private String applicationDesc;
	private String companyId;
	private String endUser;
	private String facilityId;
	private String facPartNo;
	private String inventoryGroup;
	private String itemDesc;
	private BigDecimal itemId;
	private String lineItem;
	private String notes;
	private String packaging;
	private BigDecimal prNumber;
	private BigDecimal quantity;
	private String requestLineStatus;
	private BigDecimal requestor;
	private Date requiredDatetime;
	private String shipToLocationId;
    private String facilityName;

    //constructor
	public CabinetMrViewBean() {
	}

	//getters
	public String getApplication() {
		return application;
	}
	
	//getters
	public String getApplicationDesc() {
		return applicationDesc;
	}

	public String getCompanyId() {
		return companyId;
	}

	public String getEndUser() {
		return endUser;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public String getFacPartNo() {
		return facPartNo;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public String getLineItem() {
		return lineItem;
	}

	public String getNotes() {
		return notes;
	}

	public String getPackaging() {
		return packaging;
	}

	public BigDecimal getPrNumber() {
		return prNumber;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public String getRequestLineStatus() {
		return requestLineStatus;
	}

	public BigDecimal getRequestor() {
		return requestor;
	}

	public Date getRequiredDatetime() {
		return requiredDatetime;
	}

	public String getShipToLocationId() {
		return shipToLocationId;
	}

    public String getFacilityName() {
        return facilityName;
    }

    //setters
	public void setApplication(String application) {
		this.application = application;
	}
	
	public void setApplicationDesc(String applicationDesc) {
		this.applicationDesc = applicationDesc;
	}


	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setEndUser(String endUser) {
		this.endUser = endUser;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public void setFacPartNo(String facPartNo) {
		this.facPartNo = facPartNo;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}

	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public void setRequestLineStatus(String requestLineStatus) {
		this.requestLineStatus = requestLineStatus;
	}

	public void setRequestor(BigDecimal requestor) {
		this.requestor = requestor;
	}

	public void setRequiredDatetime(Date requiredDatetime) {
		this.requiredDatetime = requiredDatetime;
	}

	public void setShipToLocationId(String shipToLocationId) {
		this.shipToLocationId = shipToLocationId;
	}

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }
}