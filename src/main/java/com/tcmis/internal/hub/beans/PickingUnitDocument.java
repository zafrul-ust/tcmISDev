package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

@SuppressWarnings("serial")
public class PickingUnitDocument extends BaseDataBean {

	private BigDecimal pickingUnitDocumentId;
	private BigDecimal pickingUnitId;
	private BigDecimal receiptId;
	private String documentPath;
	private String documentName;
	private String documentType;
	private Date lastUpdated;
	private BigDecimal lastUpdatedBy;
	private String lastUpdatedByName;
	
	public BigDecimal getPickingUnitDocumentId() {
		return pickingUnitDocumentId;
	}
	
	public void setPickingUnitDocumentId(BigDecimal pickingUnitDocumentId) {
		this.pickingUnitDocumentId = pickingUnitDocumentId;
	}
	
	public BigDecimal getPickingUnitId() {
		return pickingUnitId;
	}
	
	public void setPickingUnitId(BigDecimal pickingUnitId) {
		this.pickingUnitId = pickingUnitId;
	}
	
	public BigDecimal getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}

	public String getDocumentPath() {
		return documentPath;
	}
	
	public void setDocumentPath(String documentPath) {
		this.documentPath = documentPath;
		this.documentName = documentPath.substring(documentPath.lastIndexOf("/")+1);
	}
	
	public String getDocumentName() {
		return documentName;
	}
	
	public String getDocumentType() {
		return documentType;
	}
	
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	
	public Date getLastUpdated() {
		return lastUpdated;
	}
	
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	
	public BigDecimal getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	
	public void setLastUpdatedBy(BigDecimal lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public String getLastUpdatedByName() {
		return lastUpdatedByName;
	}

	public void setLastUpdatedByName(String lastUpdatedByName) {
		this.lastUpdatedByName = lastUpdatedByName;
	}
}
