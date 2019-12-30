package com.tcmis.client.swa.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: MatlIctBean <br>
 * @version: 1.0, Apr 19, 2007 <br>
 *****************************************************************************/


public class MatlIctBean extends BaseDataBean {

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
	private Date tcmLoadDate;
	private String fileName;
	private BigDecimal verificationCode;
	private Date processedDate;
	private BigDecimal cumulativeReceived;
	private String chemicalFlag;
	private Date endUseDatetime;
	private String endUseComplete;
	private BigDecimal itemId;
	private Date lastMod;
	private BigDecimal itemQtyIssued;
	private String facilityId;
	private String category;
	private String application;
	private String location;
	private String partDescription;
	private BigDecimal emissionFactor;
	private BigDecimal volumeGal;


	//constructor
	public MatlIctBean() {
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
	public void setTcmLoadDate(Date tcmLoadDate) {
		this.tcmLoadDate = tcmLoadDate;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public void setVerificationCode(BigDecimal verificationCode) {
		this.verificationCode = verificationCode;
	}
	public void setProcessedDate(Date processedDate) {
		this.processedDate = processedDate;
	}
	public void setCumulativeReceived(BigDecimal cumulativeReceived) {
		this.cumulativeReceived = cumulativeReceived;
	}
	public void setChemicalFlag(String chemicalFlag) {
		this.chemicalFlag = chemicalFlag;
	}
	public void setEndUseDatetime(Date endUseDatetime) {
		this.endUseDatetime = endUseDatetime;
	}
	public void setEndUseComplete(String endUseComplete) {
		this.endUseComplete = endUseComplete;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setLastMod(Date lastMod) {
		this.lastMod = lastMod;
	}
	public void setItemQtyIssued(BigDecimal itemQtyIssued) {
		this.itemQtyIssued = itemQtyIssued;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}
	public void setEmissionFactor(BigDecimal emissionFactor) {
		this.emissionFactor = emissionFactor;
	}
	public void setVolumeGal(BigDecimal volumeGal) {
		this.volumeGal = volumeGal;
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
	public Date getTcmLoadDate() {
		return tcmLoadDate;
	}
	public String getFileName() {
		return fileName;
	}
	public BigDecimal getVerificationCode() {
		return verificationCode;
	}
	public Date getProcessedDate() {
		return processedDate;
	}
	public BigDecimal getCumulativeReceived() {
		return cumulativeReceived;
	}
	public String getChemicalFlag() {
		return chemicalFlag;
	}
	public Date getEndUseDatetime() {
		return endUseDatetime;
	}
	public String getEndUseComplete() {
		return endUseComplete;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public Date getLastMod() {
		return lastMod;
	}
	public BigDecimal getItemQtyIssued() {
		return itemQtyIssued;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public String getCategory() {
		return category;
	}
	public String getApplication() {
		return application;
	}
	public String getLocation() {
		return location;
	}
	public String getPartDescription() {
		return partDescription;
	}
	public BigDecimal getEmissionFactor() {
		return emissionFactor;
	}
	public BigDecimal getVolumeGal() {
		return volumeGal;
	}
}