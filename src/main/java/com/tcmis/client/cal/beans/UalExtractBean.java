package com.tcmis.client.cal.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.client.cal.beans.TransactionTypes;


/******************************************************************************
 * CLASSNAME: Cal.ualExtractBean <br>
 * @version: 1.0, Jul 16, 2012 <br>
 *****************************************************************************/


public class UalExtractBean extends BaseDataBean {

	private BigDecimal pcn;
	private String ualPart;
	private String unitOfIssue;
	private String station;
	private String ualLocation;
	private String txCd;
	private String acctCd;
	private BigDecimal keyId;
	private BigDecimal ualTransactionNbr;
	private String fileName;
	private Date dateInserted;
	private Date dateLastModified;
	private String catPartNo;
	private String facilityId;
	private BigDecimal itemId;
	private BigDecimal itemQuantityIssued;
	private String category;
	private String applicationMethod;
	private String partDescription;
	private BigDecimal emissionFactor;
	private String application;
	private BigDecimal volumeGal;
	private Date endTxTmstmp;
	private String dataConversionComplete;
	
	private Vector<TransactionTypes> transactionTypes;
	
	private String partId;
	private String partSuffix;


	//constructor
	public UalExtractBean() {
		transactionTypes = new Vector();
	}
	
	public void addTransactionTypes1( TransactionTypes tType ) {
		tType.setTxType("R");
		transactionTypes.addElement( tType );
	}
	
	public void addTransactionTypes2( TransactionTypes tType ) {
		tType.setTxType("I");
		transactionTypes.addElement( tType );
	}
	
	public void addTransactionTypes3( TransactionTypes tType ) {
		tType.setTxType("B");
		transactionTypes.addElement( tType );
	}

	//setters
	public void setPcn(BigDecimal pcn) {
		this.pcn = pcn;
	}
	public void setUalPart(String ualPart) {
		this.ualPart = ualPart;
	}
	public void setUnitOfIssue(String unitOfIssue) {
		this.unitOfIssue = unitOfIssue;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public void setUalLocation(String ualLocation) {
		this.ualLocation = ualLocation;
	}
	public void setTxCd(String txCd) {
		this.txCd = txCd;
	}
	public void setAcctCd(String acctCd) {
		this.acctCd = acctCd;
	}
	public void setKeyId(BigDecimal keyId) {
		this.keyId = keyId;
	}
	public void setUalTransactionNbr(BigDecimal ualTransactionNbr) {
		this.ualTransactionNbr = ualTransactionNbr;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public void setDateInserted(Date dateInserted) {
		this.dateInserted = dateInserted;
	}
	public void setDateLastModified(Date dateLastModified) {
		this.dateLastModified = dateLastModified;
	}
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setItemQuantityIssued(BigDecimal itemQuantityIssued) {
		this.itemQuantityIssued = itemQuantityIssued;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public void setApplicationMethod(String applicationMethod) {
		this.applicationMethod = applicationMethod;
	}
	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}
	public void setEmissionFactor(BigDecimal emissionFactor) {
		this.emissionFactor = emissionFactor;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public void setVolumeGal(BigDecimal volumeGal) {
		this.volumeGal = volumeGal;
	}
	public void setEndTxTmstmp(Date endTxTmstmp) {
		this.endTxTmstmp = endTxTmstmp;
	}
	public void setDataConversionComplete(String dataConversionComplete) {
		this.dataConversionComplete = dataConversionComplete;
	}


	//getters
	public BigDecimal getPcn() {
		return pcn;
	}
	public String getUalPart() {
		return ualPart;
	}
	public String getUnitOfIssue() {
		return unitOfIssue;
	}
	public String getStation() {
		return station;
	}
	public String getUalLocation() {
		return ualLocation;
	}
	public String getTxCd() {
		return txCd;
	}
	public String getAcctCd() {
		return acctCd;
	}
	public BigDecimal getKeyId() {
		return keyId;
	}
	public BigDecimal getUalTransactionNbr() {
		return ualTransactionNbr;
	}
	public String getFileName() {
		return fileName;
	}
	public Date getDateInserted() {
		return dateInserted;
	}
	public Date getDateLastModified() {
		return dateLastModified;
	}
	public String getCatPartNo() {
		return catPartNo;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public BigDecimal getItemQuantityIssued() {
		return itemQuantityIssued;
	}
	public String getCategory() {
		return category;
	}
	public String getApplicationMethod() {
		return applicationMethod;
	}
	public String getPartDescription() {
		return partDescription;
	}
	public BigDecimal getEmissionFactor() {
		return emissionFactor;
	}
	public String getApplication() {
		return application;
	}
	public BigDecimal getVolumeGal() {
		return volumeGal;
	}
	public Date getEndTxTmstmp() {
		return endTxTmstmp;
	}
	public String getDataConversionComplete() {
		return dataConversionComplete;
	}

	public String getPartId() {
		return partId;
	}

	public void setPartId(String partId) {
		this.partId = partId;
	}

	public String getPartSuffix() {
		return partSuffix;
	}

	public void setPartSuffix(String partSuffix) {
		this.partSuffix = partSuffix;
	}

	public Vector getTransactionTypes() {
		return transactionTypes;
	}

	public void setTransactionTypes(Vector transactionTypes) {
		this.transactionTypes = transactionTypes;
	}

}