package com.tcmis.ws.tablet.beans;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import org.apache.struts.action.ActionForm;

import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: InboundShipmentBean <br>
 * 
 * @version: 1.0, Mar 5, 2013 <br>
 *****************************************************************************/

public class InboundShipmentBean extends BaseTabletBean {

	private Date									arrivalScanDate;
	private String									arrivalScanTimestamp;
	private BigDecimal								arrivalScanUser;
	private String									carrierParentShortName;
	private String									countAndConditionNotes;
	private Date									dateInserted;
	private Date									dateOfReceipt;
	private Date									dateUpdated;
	private Collection<InboundShipmentDocumentBean>	documents;
	private Date									estimatedDeliveryDate;
	private String									hub;
	private BigDecimal								inboundShipmentId;
	private String									shipmentStatus;
	private String									trackingNumber;
	private BigDecimal								updateUser;

	// constructor
	public InboundShipmentBean() {
	}

	public InboundShipmentBean(ActionForm form, Locale tcmISLocale) {
		super(form, tcmISLocale);
	}

	public Date getArrivalScanDate() {
		return arrivalScanDate;
	}

	public String getArrivalScanTimestamp() {
		return arrivalScanTimestamp;
	}

	public BigDecimal getArrivalScanUser() {
		return arrivalScanUser;
	}

	public String getCarrierParentShortName() {
		return carrierParentShortName;
	}

	public String getCountAndConditionNotes() {
		return countAndConditionNotes;
	}

	public Date getDateInserted() {
		return dateInserted;
	}

	public Date getDateOfReceipt() {
		return dateOfReceipt;
	}

	public Date getDateUpdated() {
		return dateUpdated;
	}

	public Collection getDocuments() {
		return documents;
	}

	public Date getEstimatedDeliveryDate() {
		return estimatedDeliveryDate;
	}

	public String getHub() {
		return hub;
	}

	public BigDecimal getInboundShipmentId() {
		return inboundShipmentId;
	}

	public String getShipmentStatus() {
		return shipmentStatus;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public BigDecimal getUpdateUser() {
		return updateUser;
	}

	public boolean hasCarrierParentShortName() {
		return !StringHandler.isBlankString(carrierParentShortName);
	}

	public boolean hasHub() {
		return !StringHandler.isBlankString(hub);
	}

	public boolean hasInboundShipmentId() {
		return inboundShipmentId != null;
	}

	public boolean hasTrackingNumber() {
		return !StringHandler.isBlankString(trackingNumber);
	}

	public void setArrivalScanDate(Date arrivalScanDate) {
		this.arrivalScanDate = arrivalScanDate;
	}

	public void setArrivalScanTimestamp(String arrivalScanTimestamp) {
		this.arrivalScanTimestamp = arrivalScanTimestamp;
	}

	public void setArrivalScanUser(BigDecimal arrivalScanUser) {
		this.arrivalScanUser = arrivalScanUser;
	}

	public void setCarrierParentShortName(String carrierParentShortName) {
		this.carrierParentShortName = carrierParentShortName;
	}

	public void setCountAndConditionNotes(String countAndConditionNotes) {
		this.countAndConditionNotes = countAndConditionNotes;
	}

	public void setDateInserted(Date dateInserted) {
		this.dateInserted = dateInserted;
	}

	public void setDateOfReceipt(Date dateOfReceipt) {
		this.dateOfReceipt = dateOfReceipt;
	}

	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	public void setDocuments(Collection documents) {
		this.documents = documents;
	}

	public void setEstimatedDeliveryDate(Date estimatedDeliveryDate) {
		this.estimatedDeliveryDate = estimatedDeliveryDate;
	}

	public void setHub(String hub) {
		this.hub = hub;
	}

	// setters
	public void setInboundShipmentId(BigDecimal inboundShipmentId) {
		this.inboundShipmentId = inboundShipmentId;
	}

	public void setShipmentStatus(String receivingStatus) {
		this.shipmentStatus = receivingStatus;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public void setUpdateUser(BigDecimal updateUser) {
		this.updateUser = updateUser;
	}
}