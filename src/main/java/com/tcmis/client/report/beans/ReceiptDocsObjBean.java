package com.tcmis.client.report.beans;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import com.tcmis.common.framework.BaseDataBean;

public class ReceiptDocsObjBean extends BaseDataBean implements SQLData {
	
public static final String sqlType = "CUSTOMER.RECEIPT_DOCS_OBJ";
private BigDecimal receiptId;
private Date dateDelivered;
private String companyId;
private BigDecimal prNumber;
private String lineItem;
private String facilityId;
private String application;
private String shipToLocationId;
private String poNumber;
private String releaseNumber;
private String catalogId;
private String catalogDesc;
private String facPartNo;
private String partDescription;
private String spec;
private BigDecimal itemId;
private String itemDesc;
private BigDecimal catalogPrice;
private BigDecimal baselinePrice;
private String currencyId;
private String unitOfSale;
private BigDecimal unitOfSaleQuantityPerEach;
private String onLine;
private String mrLine;
private String poLine;
private BigDecimal mrLineQty;
private BigDecimal quantityIssued;
private String mfgLot;
private Date expireDate;
private String applicationDesc;
private Date deliveryConfirmationDate;
private String deliveryComments;
private String confirmedBy;
private BigDecimal extendedPrice;
private BigDecimal additionalCharges;
private BigDecimal freightCharges;
private Collection documents;
private Array documentsArray;
private String putAwayAcceptedBy;

private BigDecimal shipmentId;

public BigDecimal getShipmentId() {
	return shipmentId;
}


public void setShipmentId(BigDecimal shipmentId) {
	this.shipmentId = shipmentId;
}


//constructor
public ReceiptDocsObjBean() {
}


public String getSQLTypeName() throws SQLException {
	return sqlType;
}


public BigDecimal getReceiptId() {
	return receiptId;
}


public void setReceiptId(BigDecimal receiptId) {
	this.receiptId = receiptId;
}


public Date getDateDelivered() {
	return dateDelivered;
}


public void setDateDelivered(Date dateDelivered) {
	this.dateDelivered = dateDelivered;
}


public String getCompanyId() {
	return companyId;
}


public void setCompanyId(String companyId) {
	this.companyId = companyId;
}


public BigDecimal getPrNumber() {
	return prNumber;
}


public void setPrNumber(BigDecimal prNumber) {
	this.prNumber = prNumber;
}


public String getLineItem() {
	return lineItem;
}


public void setLineItem(String lineItem) {
	this.lineItem = lineItem;
}


public String getFacilityId() {
	return facilityId;
}


public void setFacilityId(String facilityId) {
	this.facilityId = facilityId;
}


public String getApplication() {
	return application;
}


public void setApplication(String application) {
	this.application = application;
}


public String getShipToLocationId() {
	return shipToLocationId;
}


public void setShipToLocationId(String shipToLocationId) {
	this.shipToLocationId = shipToLocationId;
}


public String getPoNumber() {
	return poNumber;
}


public void setPoNumber(String poNumber) {
	this.poNumber = poNumber;
}


public String getReleaseNumber() {
	return releaseNumber;
}


public void setReleaseNumber(String releaseNumber) {
	this.releaseNumber = releaseNumber;
}


public String getCatalogId() {
	return catalogId;
}


public void setCatalogId(String catalogId) {
	this.catalogId = catalogId;
}


public String getCatalogDesc() {
	return catalogDesc;
}


public void setCatalogDesc(String catalogDesc) {
	this.catalogDesc = catalogDesc;
}


public String getFacPartNo() {
	return facPartNo;
}


public void setFacPartNo(String facPartNo) {
	this.facPartNo = facPartNo;
}


public String getPartDescription() {
	return partDescription;
}


public void setPartDescription(String partDescription) {
	this.partDescription = partDescription;
}


public String getSpec() {
	return spec;
}


public void setSpec(String spec) {
	this.spec = spec;
}


public BigDecimal getItemId() {
	return itemId;
}


public void setItemId(BigDecimal itemId) {
	this.itemId = itemId;
}


public String getItemDesc() {
	return itemDesc;
}


public void setItemDesc(String itemDesc) {
	this.itemDesc = itemDesc;
}


public BigDecimal getCatalogPrice() {
	return catalogPrice;
}


public void setCatalogPrice(BigDecimal catalogPrice) {
	this.catalogPrice = catalogPrice;
}


public BigDecimal getBaselinePrice() {
	return baselinePrice;
}


public void setBaselinePrice(BigDecimal baselinePrice) {
	this.baselinePrice = baselinePrice;
}


public String getCurrencyId() {
	return currencyId;
}


public void setCurrencyId(String currencyId) {
	this.currencyId = currencyId;
}


public String getUnitOfSale() {
	return unitOfSale;
}


public void setUnitOfSale(String unitOfSale) {
	this.unitOfSale = unitOfSale;
}


public BigDecimal getUnitOfSaleQuantityPerEach() {
	return unitOfSaleQuantityPerEach;
}


public void setUnitOfSaleQuantityPerEach(BigDecimal unitOfSaleQuantityPerEach) {
	this.unitOfSaleQuantityPerEach = unitOfSaleQuantityPerEach;
}


public String getOnLine() {
	return onLine;
}


public void setOnLine(String onLine) {
	this.onLine = onLine;
}


public String getMrLine() {
	return mrLine;
}


public void setMrLine(String mrLine) {
	this.mrLine = mrLine;
}


public String getPoLine() {
	return poLine;
}


public void setPoLine(String poLine) {
	this.poLine = poLine;
}


public BigDecimal getMrLineQty() {
	return mrLineQty;
}


public void setMrLineQty(BigDecimal mrLineQty) {
	this.mrLineQty = mrLineQty;
}


public BigDecimal getQuantityIssued() {
	return quantityIssued;
}


public void setQuantityIssued(BigDecimal quantityIssued) {
	this.quantityIssued = quantityIssued;
}


public String getMfgLot() {
	return mfgLot;
}


public void setMfgLot(String mfgLot) {
	this.mfgLot = mfgLot;
}


public Date getExpireDate() {
	return expireDate;
}


public void setExpireDate(Date expireDate) {
	this.expireDate = expireDate;
}


public String getApplicationDesc() {
	return applicationDesc;
}


public void setApplicationDesc(String applicationDesc) {
	this.applicationDesc = applicationDesc;
}


public Date getDeliveryConfirmationDate() {
	return deliveryConfirmationDate;
}


public void setDeliveryConfirmationDate(Date deliveryConfirmationDate) {
	this.deliveryConfirmationDate = deliveryConfirmationDate;
}


public String getDeliveryComments() {
	return deliveryComments;
}


public void setDeliveryComments(String deliveryComments) {
	this.deliveryComments = deliveryComments;
}


public String getConfirmedBy() {
	return confirmedBy;
}


public void setConfirmedBy(String confirmedBy) {
	this.confirmedBy = confirmedBy;
}



public BigDecimal getExtendedPrice() {
	return extendedPrice;
}


public void setExtendedPrice(BigDecimal extendedPrice) {
	this.extendedPrice = extendedPrice;
}


public BigDecimal getAdditionalCharges() {
	return additionalCharges;
}


public void setAdditionalCharges(BigDecimal additionalCharges) {
	this.additionalCharges = additionalCharges;
}


public BigDecimal getFreightCharges() {
	return freightCharges;
}


public void setFreightCharges(BigDecimal freightCharges) {
	this.freightCharges = freightCharges;
}


public Collection<ReceiptDocsObjBean> getDocuments() {
	return documents;
}

public void setDocuments(Collection documents) {
	this.documents = documents;
}

public int getLineCount() {
	return documents != null ? documents.size() : 0;
}

public void setDocumentsArray(Array documentsArray) {
	try {
		setDocuments(Arrays.asList((Object[]) documentsArray.getArray()));
	}
	catch (SQLException sqle) {
		System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
	}
}

public String getPutAwayAcceptedBy() {
	return putAwayAcceptedBy;
}

public void setPutAwayAcceptedBy(String putAwayAcceptedBy) {
	this.putAwayAcceptedBy = putAwayAcceptedBy;
}

public static String getSqltype() {
	return sqlType;
}


public void readSQL(SQLInput stream, String typeName) throws SQLException {
	try {
		setReceiptId(stream.readBigDecimal());
		setDateDelivered(stream.readTimestamp());
		setCompanyId(stream.readString());
		setPrNumber(stream.readBigDecimal());
		setLineItem(stream.readString());
		setFacilityId(stream.readString());
		setApplication(stream.readString());
		setShipToLocationId(stream.readString());
		setPoNumber(stream.readString());
		setReleaseNumber(stream.readString());
		setCatalogId(stream.readString());
		setCatalogDesc(stream.readString());
		setFacPartNo(stream.readString());
		setPartDescription(stream.readString());
		setSpec(stream.readString());
		setItemId(stream.readBigDecimal());
		setItemDesc(stream.readString());
		setCatalogPrice(stream.readBigDecimal());
		setBaselinePrice(stream.readBigDecimal());
		setCurrencyId(stream.readString());
		setUnitOfSale(stream.readString());
		setUnitOfSaleQuantityPerEach(stream.readBigDecimal());
		setOnLine(stream.readString());
		setMrLine(stream.readString());
		setPoLine(stream.readString());
		setMrLineQty(stream.readBigDecimal());
		setQuantityIssued(stream.readBigDecimal());
		setMfgLot(stream.readString());
		setExpireDate(stream.readTimestamp());
		setApplicationDesc(stream.readString());
		setDeliveryConfirmationDate(stream.readTimestamp());
		setConfirmedBy(stream.readString());
		setDeliveryComments(stream.readString());
		setExtendedPrice(stream.readBigDecimal());
		setAdditionalCharges(stream.readBigDecimal());
		setFreightCharges(stream.readBigDecimal());
		setDocumentsArray(stream.readArray());
		setShipmentId(stream.readBigDecimal());
		setPutAwayAcceptedBy(stream.readString());
	}
	catch (SQLException e) {
		throw e;
	}
	catch (Exception e) {
		Log log = LogFactory.getLog(this.getClass());
		log.error("readSQL method failed", e);
	}
}

public void writeSQL(SQLOutput stream) throws SQLException {
	// Aint gonna happen ;-}
	// This is a read only bean
}

}
