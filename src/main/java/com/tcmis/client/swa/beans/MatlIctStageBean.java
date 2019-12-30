package com.tcmis.client.swa.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: MatlIctStageBean <br>
 * @version: 1.0, Apr 30, 2007 <br>
 *****************************************************************************/


public class MatlIctStageBean extends BaseDataBean {

	private BigDecimal issueDocumentNumber;
	private String documentType;
	private String partNumber;
	private String manfPartNumber;
	private BigDecimal numberRequested;
	private BigDecimal numberIssued;
	private BigDecimal requestingEmployeeId;
	private BigDecimal status;
	private Date issueCreditTransferDate;
	private String acn;
	private String shippingStation;
	private String shippingDepartment;
	private Date shipmentDate;
	private String receivingStation;
	private String receivingDepartment;
	private String fileName;
        private BigDecimal cumulativeReceived;


	//constructor
	public MatlIctStageBean() {
	}

	//setters
	public void setIssueDocumentNumber(BigDecimal issueDocumentNumber) {
		this.issueDocumentNumber = issueDocumentNumber;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	public void setManfPartNumber(String manfPartNumber) {
		this.manfPartNumber = manfPartNumber;
	}
	public void setNumberRequested(BigDecimal numberRequested) {
		this.numberRequested = numberRequested;
	}
	public void setNumberIssued(BigDecimal numberIssued) {
		this.numberIssued = numberIssued;
	}
	public void setRequestingEmployeeId(BigDecimal requestingEmployeeId) {
		this.requestingEmployeeId = requestingEmployeeId;
	}
	public void setStatus(BigDecimal status) {
		this.status = status;
	}
	public void setIssueCreditTransferDate(Date issueCreditTransferDate) {
		this.issueCreditTransferDate = issueCreditTransferDate;
	}
	public void setAcn(String acn) {
		this.acn = acn;
	}
	public void setShippingStation(String shippingStation) {
		this.shippingStation = shippingStation;
	}
	public void setShippingDepartment(String shippingDepartment) {
		this.shippingDepartment = shippingDepartment;
	}
	public void setShipmentDate(Date shipmentDate) {
		this.shipmentDate = shipmentDate;
	}
	public void setReceivingStation(String receivingStation) {
		this.receivingStation = receivingStation;
	}
	public void setReceivingDepartment(String receivingDepartment) {
		this.receivingDepartment = receivingDepartment;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
        public void setCumulativeReceived(BigDecimal cumulativeReceived) {
          this.cumulativeReceived = cumulativeReceived;
        }

	//getters
	public BigDecimal getIssueDocumentNumber() {
		return issueDocumentNumber;
	}
	public String getDocumentType() {
		return documentType;
	}
	public String getPartNumber() {
		return partNumber;
	}
	public String getManfPartNumber() {
		return manfPartNumber;
	}
	public BigDecimal getNumberRequested() {
		return numberRequested;
	}
	public BigDecimal getNumberIssued() {
		return numberIssued;
	}
	public BigDecimal getRequestingEmployeeId() {
		return requestingEmployeeId;
	}
	public BigDecimal getStatus() {
		return status;
	}
	public Date getIssueCreditTransferDate() {
		return issueCreditTransferDate;
	}
	public String getAcn() {
		return acn;
	}
	public String getShippingStation() {
		return shippingStation;
	}
	public String getShippingDepartment() {
		return shippingDepartment;
	}
	public Date getShipmentDate() {
		return shipmentDate;
	}
	public String getReceivingStation() {
		return receivingStation;
	}
	public String getReceivingDepartment() {
		return receivingDepartment;
	}
	public String getFileName() {
		return fileName;
	}
        public BigDecimal getCumulativeReceived() {
          return cumulativeReceived;
        }
}