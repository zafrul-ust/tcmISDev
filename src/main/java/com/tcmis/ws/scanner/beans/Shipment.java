package com.tcmis.ws.scanner.beans;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: PoLineBean <br>
 * 
 * @version: 1.0, Mar 7, 2013 <br>
 *****************************************************************************/

public class Shipment extends BaseDataBean {

	private Date						arrivalScanDate;
	private String						carrierParentShortName;
	private Collection<CitrReceipt>		citrReceipts;
	private String						countAndConditionNotes;
	private Collection<DocumentId>		documentIds;
	private String						hub;
	private String						id;
	private BigDecimal					personnelId;
	private Collection<PoReceipt>		poReceipts;
	private Collection<RmaReceipt>		rmaReceipts;
	private String						shipmentId;
	private String						trackingNumber;
	private Collection<TransferReceipt>	transferReceipts;

	public Date getArrivalScanDate() {
		return arrivalScanDate;
	}

	public String getCarrierParentShortName() {
		return carrierParentShortName;
	}

	public Collection<CitrReceipt> getCitrReceipts() {
		return citrReceipts;
	}

	public String getCountAndConditionNotes() {
		return countAndConditionNotes;
	}

	public Collection<DocumentId> getDocumentIds() {
		return documentIds;
	}

	public String getHub() {
		return hub;
	}

	public String getId() {
		return this.id;
	}

	public BigDecimal getPersonnelId() {
		return personnelId;
	}

	public Collection<PoReceipt> getPoReceipts() {
		return poReceipts;
	}

	public Collection<RmaReceipt> getRmaReceipts() {
		return rmaReceipts;
	}

	public String getShipmentId() {
		return shipmentId;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public Collection<TransferReceipt> getTransferReceipts() {
		return transferReceipts;
	}

	public boolean hasId() {
		return StringUtils.isNotBlank(id);
	}

	public boolean hasCitrReceipts() {
		return citrReceipts != null && !citrReceipts.isEmpty();
	}

	public boolean hasDocumentIds() {
		return documentIds != null && !documentIds.isEmpty();
	}

	public boolean hasPoReceipts() {
		return poReceipts != null && !poReceipts.isEmpty();
	}

	public boolean hasRmaReceipts() {
		return rmaReceipts != null && !rmaReceipts.isEmpty();
	}

	public boolean hasTransferReceipts() {
		return transferReceipts != null && !transferReceipts.isEmpty();
	}

	public boolean isValid() {
		return personnelId != null && StringUtils.isNotBlank(hub) && StringUtils.isNotBlank(trackingNumber)
				&& (hasPoReceipts() || hasCitrReceipts() || hasRmaReceipts() || hasTransferReceipts());
	}

	public void setArrivalScanDate(Date arrivalScanDate) {
		this.arrivalScanDate = arrivalScanDate;
	}

	public void setCarrierParentShortName(String carrierParentShortName) {
		this.carrierParentShortName = carrierParentShortName;
	}

	public void setCitrReceipts(Collection<CitrReceipt> citrReceipts) {
		this.citrReceipts = citrReceipts;
	}

	public void setCountAndConditionNotes(String countAndConditionNotes) {
		this.countAndConditionNotes = countAndConditionNotes;
	}

	public void setDocumentIds(Collection<DocumentId> documentIds) {
		this.documentIds = documentIds;
	}

	public void setHub(String hub) {
		this.hub = hub;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
	}

	public void setPoReceipts(Collection<PoReceipt> poReceipts) {
		this.poReceipts = poReceipts;
	}

	public void setRmaReceipts(Collection<RmaReceipt> rmaReceipts) {
		this.rmaReceipts = rmaReceipts;
	}

	public void setShipmentId(String inboundShipmentId) {
		this.shipmentId = inboundShipmentId;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public void setTransferReceipts(Collection<TransferReceipt> transferReceipts) {
		this.transferReceipts = transferReceipts;
	}
}