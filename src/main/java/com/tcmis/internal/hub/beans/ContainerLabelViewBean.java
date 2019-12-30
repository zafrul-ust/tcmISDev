package com.tcmis.internal.hub.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: ContainerLabelViewBean <br>
 * @version: 1.0, Sep 10, 2013 <br>
 *****************************************************************************/


public class ContainerLabelViewBean extends BaseDataBean {

	private BigDecimal containerLabelId;
	private BigDecimal receiptId;
	private Date expireDate;
	private String mfgLot;
	private String storageTemp;
	private String recertNumber;
	private Date dateOfReceipt;
	private Date dateOfManufacture;
	private Date dateOfShipment;
	private Date vendorShipDate;
	private String lotStatus;
	private BigDecimal certifiedBy;
	private String certifiedByName;
	private Date certificationDate;
	private String qaStatement;
	private String qualityTrackingNumber;
	private String labelColor;
	private String specDetail;
	private String specList;
	private BigDecimal quantityPrinted;
	private BigDecimal lastIdPrinted;
	private BigDecimal printUser;
	private String printUserName;
	private Date dateInserted;
	private BigDecimal partId;
	private String compMfgLot;
	private Date compExpireDate;
	private Date mfgLabelExpireDate;
	private Date compDateInserted;
	private String manageKitsAsSingleUnit;
	private String materialDesc;
	private String partNumbers; 


	//constructor
	public ContainerLabelViewBean() {
	}

	//setters
	public void setContainerLabelId(BigDecimal containerLabelId) {
		this.containerLabelId = containerLabelId;
	}
	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	public void setMfgLot(String mfgLot) {
		this.mfgLot = mfgLot;
	}
	public void setStorageTemp(String storageTemp) {
		this.storageTemp = storageTemp;
	}
	public void setRecertNumber(String recertNumber) {
		this.recertNumber = recertNumber;
	}
	public void setDateOfReceipt(Date dateOfReceipt) {
		this.dateOfReceipt = dateOfReceipt;
	}
	public void setDateOfManufacture(Date dateOfManufacture) {
		this.dateOfManufacture = dateOfManufacture;
	}
	public void setDateOfShipment(Date dateOfShipment) {
		this.dateOfShipment = dateOfShipment;
	}
	public void setVendorShipDate(Date vendorShipDate) {
		this.vendorShipDate = vendorShipDate;
	}
	public void setLotStatus(String lotStatus) {
		this.lotStatus = lotStatus;
	}
	public void setCertifiedBy(BigDecimal certifiedBy) {
		this.certifiedBy = certifiedBy;
	}
	public void setCertifiedByName(String certifiedByName) {
		this.certifiedByName = certifiedByName;
	}
	public void setCertificationDate(Date certificationDate) {
		this.certificationDate = certificationDate;
	}
	public void setQaStatement(String qaStatement) {
		this.qaStatement = qaStatement;
	}
	public void setQualityTrackingNumber(String qualityTrackingNumber) {
		this.qualityTrackingNumber = qualityTrackingNumber;
	}
	public void setLabelColor(String labelColor) {
		this.labelColor = labelColor;
	}
	public void setSpecDetail(String specDetail) {
		this.specDetail = specDetail;
	}
	public void setSpecList(String specList) {
		this.specList = specList;
	}
	public void setQuantityPrinted(BigDecimal quantityPrinted) {
		this.quantityPrinted = quantityPrinted;
	}
	public void setLastIdPrinted(BigDecimal lastIdPrinted) {
		this.lastIdPrinted = lastIdPrinted;
	}
	public void setPrintUser(BigDecimal printUser) {
		this.printUser = printUser;
	}
	public void setPrintUserName(String printUserName) {
		this.printUserName = printUserName;
	}
	public void setDateInserted(Date dateInserted) {
		this.dateInserted = dateInserted;
	}
	public void setPartId(BigDecimal partId) {
		this.partId = partId;
	}
	public void setCompMfgLot(String compMfgLot) {
		this.compMfgLot = compMfgLot;
	}
	public void setCompExpireDate(Date compExpireDate) {
		this.compExpireDate = compExpireDate;
	}
	public void setMfgLabelExpireDate(Date mfgLabelExpireDate) {
		this.mfgLabelExpireDate = mfgLabelExpireDate;
	}
	public void setCompDateInserted(Date compDateInserted) {
		this.compDateInserted = compDateInserted;
	}
	public void setManageKitsAsSingleUnit(String manageKitsAsSingleUnit) {
		this.manageKitsAsSingleUnit = manageKitsAsSingleUnit;
	}
	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}
	public void setPartNumbers(String partNumbers) {
		this.partNumbers = partNumbers;
	}
	
	//getters
	public String getPartNumbers() {
		return partNumbers;
	}
	public BigDecimal getContainerLabelId() {
		return containerLabelId;
	}
	public BigDecimal getReceiptId() {
		return receiptId;
	}
	public Date getExpireDate() {
		return expireDate;
	}
	public String getMfgLot() {
		return mfgLot;
	}
	public String getStorageTemp() {
		return storageTemp;
	}
	public String getRecertNumber() {
		return recertNumber;
	}
	public Date getDateOfReceipt() {
		return dateOfReceipt;
	}
	public Date getDateOfManufacture() {
		return dateOfManufacture;
	}
	public Date getDateOfShipment() {
		return dateOfShipment;
	}
	public Date getVendorShipDate() {
		return vendorShipDate;
	}
	public String getLotStatus() {
		return lotStatus;
	}
	public BigDecimal getCertifiedBy() {
		return certifiedBy;
	}
	public String getCertifiedByName() {
		return certifiedByName;
	}
	public Date getCertificationDate() {
		return certificationDate;
	}
	public String getQaStatement() {
		return qaStatement;
	}
	public String getQualityTrackingNumber() {
		return qualityTrackingNumber;
	}
	public String getLabelColor() {
		return labelColor;
	}
	public String getSpecDetail() {
		return specDetail;
	}
	public String getSpecList() {
		return specList;
	}
	public BigDecimal getQuantityPrinted() {
		return quantityPrinted;
	}
	public BigDecimal getLastIdPrinted() {
		return lastIdPrinted;
	}
	public BigDecimal getPrintUser() {
		return printUser;
	}
	public String getPrintUserName() {
		return printUserName;
	}
	public Date getDateInserted() {
		return dateInserted;
	}
	public BigDecimal getPartId() {
		return partId;
	}
	public String getCompMfgLot() {
		return compMfgLot;
	}
	public Date getCompExpireDate() {
		return compExpireDate;
	}
	public Date getMfgLabelExpireDate() {
		return mfgLabelExpireDate;
	}
	public Date getCompDateInserted() {
		return compDateInserted;
	}
	public String getManageKitsAsSingleUnit() {
		return manageKitsAsSingleUnit;
	}
	public String getMaterialDesc() {
		return materialDesc;
	}

}
