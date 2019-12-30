package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: IssueHistoryViewBean <br>
 * @version: 1.0, Feb 7, 2008 <br>
 *****************************************************************************/


public class IssueHistoryViewBean extends BaseDataBean {

	private BigDecimal issueId;
	private BigDecimal batch;
	private BigDecimal picklistId;
	private String hub;
	private BigDecimal prNumber;
	private String lineItem;
	private String mrLine;
	private BigDecimal receiptId;
	private BigDecimal itemId;
	private String inventoryGroup;
	private String bin;
	private String mfgLot;
	private String lotStatus;
	private Date expireDate;
	private BigDecimal quantity;
	private String packaging;
	private String catPartNo;
	private Date dateDelivered;
	private Date shipConfirmDate;
	private String doNumber;
	private String application;
	private String facilityId;
	private String partDescription;
	private String companyId;
	private String deliveryPoint;
	private String shipToLocationId;
	private String requestor;
	private String poNumber;
	private String releaseNumber;


	//constructor
	public IssueHistoryViewBean() {
	}

	//setters
	public void setIssueId(BigDecimal issueId) {
		this.issueId = issueId;
	}
	public void setBatch(BigDecimal batch) {
		this.batch = batch;
	}
	public void setPicklistId(BigDecimal picklistId) {
		this.picklistId = picklistId;
	}
	public void setHub(String hub) {
		this.hub = hub;
	}
	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}
	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}
	public void setMrLine(String mrLine) {
		this.mrLine = mrLine;
	}
	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setBin(String bin) {
		this.bin = bin;
	}
	public void setMfgLot(String mfgLot) {
		this.mfgLot = mfgLot;
	}
	public void setLotStatus(String lotStatus) {
		this.lotStatus = lotStatus;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}
	public void setDateDelivered(Date dateDelivered) {
		this.dateDelivered = dateDelivered;
	}
	public void setShipConfirmDate(Date shipConfirmDate) {
		this.shipConfirmDate = shipConfirmDate;
	}
	public void setDoNumber(String doNumber) {
		this.doNumber = doNumber;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setDeliveryPoint(String deliveryPoint) {
		this.deliveryPoint = deliveryPoint;
	}
	public void setShipToLocationId(String shipToLocationId) {
		this.shipToLocationId = shipToLocationId;
	}
	public void setRequestor(String requestor) {
		this.requestor = requestor;
	}


	//getters
	public BigDecimal getIssueId() {
		return issueId;
	}
	public BigDecimal getBatch() {
		return batch;
	}
	public BigDecimal getPicklistId() {
		return picklistId;
	}
	public String getHub() {
		return hub;
	}
	public BigDecimal getPrNumber() {
		return prNumber;
	}
	public String getLineItem() {
		return lineItem;
	}
	public String getMrLine() {
		return mrLine;
	}
	public BigDecimal getReceiptId() {
		return receiptId;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public String getBin() {
		return bin;
	}
	public String getMfgLot() {
		return mfgLot;
	}
	public String getLotStatus() {
		return lotStatus;
	}
	public Date getExpireDate() {
		return expireDate;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public String getPackaging() {
		return packaging;
	}
	public String getCatPartNo() {
		return catPartNo;
	}
	public Date getDateDelivered() {
		return dateDelivered;
	}
	public Date getShipConfirmDate() {
		return shipConfirmDate;
	}
	public String getDoNumber() {
		return doNumber;
	}
	public String getApplication() {
		return application;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public String getPartDescription() {
		return partDescription;
	}
	public String getCompanyId() {
		return companyId;
	}
	public String getDeliveryPoint() {
		return deliveryPoint;
	}
	public String getShipToLocationId() {
		return shipToLocationId;
	}
	public String getRequestor() {
		return requestor;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public String getReleaseNumber() {
		return releaseNumber;
	}

	public void setReleaseNumber(String releaseNumber) {
		this.releaseNumber = releaseNumber;
	}
}