package com.tcmis.ws.tablet.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: InboundShipmentDocumentBean <br>
 * @version: 1.0, Mar 5, 2013 <br>
 *****************************************************************************/


public class InboundShipmentDocumentInputBean extends BaseDataBean {

	private String inboundShipmentDocumentId;
	private BigDecimal inboundShipmentId;
	private BigDecimal initialScanUser;
	private String receivingStatus;
	private String documentFileName;
	private Date documentFileDate;
	private Date dateUpdated;
	private Date dateInserted;


	//constructor
	public InboundShipmentDocumentInputBean() {
	}

	//setters
	public void setInboundShipmentDocumentId(String inboundShipmentDocumentId) {
		if (inboundShipmentDocumentId != null) {
			this.inboundShipmentDocumentId = inboundShipmentDocumentId.replaceAll("RDOC", "");
		}
		else {
			this.inboundShipmentDocumentId = inboundShipmentDocumentId;
		}
	}
	public void setInboundShipmentId(BigDecimal inboundShipmentId) {
		this.inboundShipmentId = inboundShipmentId;
	}
	public void setInitialScanUser(BigDecimal initialScanUser) {
		this.initialScanUser = initialScanUser;
	}
	public void setReceivingStatus(String receivingStatus) {
		this.receivingStatus = receivingStatus;
	}
	public void setDocumentFileName(String documentFileName) {
		this.documentFileName = documentFileName;
	}
	public void setDocumentFileDate(Date documentFileDate) {
		this.documentFileDate = documentFileDate;
	}
	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}
	public void setDateInserted(Date dateInserted) {
		this.dateInserted = dateInserted;
	}


	//getters
	public String getInboundShipmentDocumentId() {
		return inboundShipmentDocumentId;
	}
	public BigDecimal getInboundShipmentId() {
		return inboundShipmentId;
	}
	public BigDecimal getInitialScanUser() {
		return initialScanUser;
	}
	public String getReceivingStatus() {
		return receivingStatus;
	}
	public String getDocumentFileName() {
		return documentFileName;
	}
	public Date getDocumentFileDate() {
		return documentFileDate;
	}
	public Date getDateUpdated() {
		return dateUpdated;
	}
	public Date getDateInserted() {
		return dateInserted;
	}
}