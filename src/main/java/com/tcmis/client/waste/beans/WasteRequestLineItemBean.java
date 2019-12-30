package com.tcmis.client.waste.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: WasteRequestLineItemBean <br>
 * @version: 1.0, Mar 20, 2007 <br>
 *****************************************************************************/


public class WasteRequestLineItemBean extends BaseDataBean {

	private BigDecimal wasteRequestId;
	private BigDecimal lineItem;
	private BigDecimal quantity;
	private String note;
	private BigDecimal replaceContainer;
	private BigDecimal travelerId;
	private String generationPoint;
	private Date sealDate;
	private String chargeType;
	private Date requestedTransferDate;
	private String requestedTransferTime;
	private String poNumber;
	private String releaseNumber;
	private Date releaseDate;
	private String tagNumber;


	//constructor
	public WasteRequestLineItemBean() {
	}

	//setters
	public void setWasteRequestId(BigDecimal wasteRequestId) {
		this.wasteRequestId = wasteRequestId;
	}
	public void setLineItem(BigDecimal lineItem) {
		this.lineItem = lineItem;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public void setReplaceContainer(BigDecimal replaceContainer) {
		this.replaceContainer = replaceContainer;
	}
	public void setTravelerId(BigDecimal travelerId) {
		this.travelerId = travelerId;
	}
	public void setGenerationPoint(String generationPoint) {
		this.generationPoint = generationPoint;
	}
	public void setSealDate(Date sealDate) {
		this.sealDate = sealDate;
	}
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}
	public void setRequestedTransferDate(Date requestedTransferDate) {
		this.requestedTransferDate = requestedTransferDate;
	}
	public void setRequestedTransferTime(String requestedTransferTime) {
		this.requestedTransferTime = requestedTransferTime;
	}
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	public void setReleaseNumber(String releaseNumber) {
		this.releaseNumber = releaseNumber;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	public void setTagNumber(String tagNumber) {
		this.tagNumber = tagNumber;
	}


	//getters
	public BigDecimal getWasteRequestId() {
		return wasteRequestId;
	}
	public BigDecimal getLineItem() {
		return lineItem;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public String getNote() {
		return note;
	}
	public BigDecimal getReplaceContainer() {
		return replaceContainer;
	}
	public BigDecimal getTravelerId() {
		return travelerId;
	}
	public String getGenerationPoint() {
		return generationPoint;
	}
	public Date getSealDate() {
		return sealDate;
	}
	public String getChargeType() {
		return chargeType;
	}
	public Date getRequestedTransferDate() {
		return requestedTransferDate;
	}
	public String getRequestedTransferTime() {
		return requestedTransferTime;
	}
	public String getPoNumber() {
		return poNumber;
	}
	public String getReleaseNumber() {
		return releaseNumber;
	}
	public Date getReleaseDate() {
		return releaseDate;
	}
	public String getTagNumber() {
		return tagNumber;
	}
}