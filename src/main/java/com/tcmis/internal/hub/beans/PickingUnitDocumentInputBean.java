package com.tcmis.internal.hub.beans;

import java.io.File;
import java.math.BigDecimal;

import org.apache.struts.action.ActionForm;

import com.tcmis.common.framework.BaseInputBean;

public class PickingUnitDocumentInputBean extends BaseInputBean {

	private BigDecimal documentId;
	private BigDecimal pickingUnitId;
	private BigDecimal receiptId;
	private String documentType;
	private String documentPath;
	private BigDecimal shipmentId;
	private BigDecimal issueId;
	private File theFile;
	
	public PickingUnitDocumentInputBean() {
		super();
	}
	
	public PickingUnitDocumentInputBean(ActionForm form) {
		super(form);
	}
	
	public BigDecimal getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}

	public BigDecimal getDocumentId() {
		return documentId;
	}

	public void setDocumentId(BigDecimal documentId) {
		this.documentId = documentId;
	}

	public BigDecimal getPickingUnitId() {
		return pickingUnitId;
	}

	public void setPickingUnitId(BigDecimal pickingUnitId) {
		this.pickingUnitId = pickingUnitId;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getDocumentPath() {
		return documentPath;
	}

	public void setDocumentPath(String documentPath) {
		this.documentPath = documentPath;
	}

	public BigDecimal getShipmentId() {
		return shipmentId;
	}

	public void setShipmentId(BigDecimal shipmentId) {
		this.shipmentId = shipmentId;
	}

	public BigDecimal getIssueId() {
		return issueId;
	}

	public void setIssueId(BigDecimal issueId) {
		this.issueId = issueId;
	}

	public File getTheFile() {
		return theFile;
	}

	public void setTheFile(File theFile) {
		this.theFile = theFile;
	}
	
	public boolean hasPickingUnitId() {
		return this.pickingUnitId != null;
	}
	
	public boolean hasIssueId() {
		return this.issueId != null;
	}
	
	public boolean hasReceiptId() {
		return this.receiptId != null;
	}
	
	public boolean hasShipmentId() {
		return this.shipmentId != null;
	}

	@Override
	public void setHiddenFormFields() {
		// TODO Auto-generated method stub
		
	}
}
