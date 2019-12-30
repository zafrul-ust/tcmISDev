package com.tcmis.client.report.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: DeliveredReceiptDocsViewBean <br>
 * @version: 1.0, Sep 26, 2006 <br>
 *****************************************************************************/


public class DeliveredReceiptDocsViewBean extends BaseDataBean {

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
	private String documentUrl;
	private String onLine;
    private String mrLine;
    private String poLine;
    private BigDecimal quantityIssued;
    private String mfgLot;
	private Date expireDate;
	private String applicationDesc;
	private Date deliveryConfirmationDate;
	private String deliveryComments;
	private String confirmedBy;

	//constructor
	public DeliveredReceiptDocsViewBean() {
	}

	//setters
	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}
	public void setDateDelivered(Date dateDelivered) {
		this.dateDelivered = dateDelivered;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}
	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public void setShipToLocationId(String shipToLocationId) {
		this.shipToLocationId = shipToLocationId;
	}
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	public void setReleaseNumber(String releaseNumber) {
		this.releaseNumber = releaseNumber;
	}
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	public void setCatalogDesc(String catalogDesc) {
		this.catalogDesc = catalogDesc;
	}
	public void setFacPartNo(String facPartNo) {
		this.facPartNo = facPartNo;
	}
	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public void setCatalogPrice(BigDecimal catalogPrice) {
		this.catalogPrice = catalogPrice;
	}
	public void setBaselinePrice(BigDecimal baselinePrice) {
		this.baselinePrice = baselinePrice;
	}
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}
	public void setUnitOfSale(String unitOfSale) {
		this.unitOfSale = unitOfSale;
	}
	public void setUnitOfSaleQuantityPerEach(BigDecimal unitOfSaleQuantityPerEach) {
		this.unitOfSaleQuantityPerEach = unitOfSaleQuantityPerEach;
	}
	public void setDocumentUrl(String documentUrl) {
		this.documentUrl = documentUrl;
	}
	public void setOnLine(String onLine) {
		this.onLine = onLine;
	}
        public void setMrLine(String mrLine) {
                this.mrLine = mrLine;
        }
        public void setPoLine(String poLine) {
                this.poLine = poLine;
        }
        public void setQuantityIssued(BigDecimal quantityIssued) {
                this.quantityIssued = quantityIssued;
        }
        public void setMfgLot(String mfgLot) {
                this.mfgLot = mfgLot;
        }

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	//getters
	public BigDecimal getReceiptId() {
		return receiptId;
	}
	public Date getDateDelivered() {
		return dateDelivered;
	}
	public String getCompanyId() {
		return companyId;
	}
	public BigDecimal getPrNumber() {
		return prNumber;
	}
	public String getLineItem() {
		return lineItem;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public String getApplication() {
		return application;
	}
	public String getShipToLocationId() {
		return shipToLocationId;
	}
	public String getPoNumber() {
		return poNumber;
	}
	public String getReleaseNumber() {
		return releaseNumber;
	}
	public String getCatalogId() {
		return catalogId;
	}
	public String getCatalogDesc() {
		return catalogDesc;
	}
	public String getFacPartNo() {
		return facPartNo;
	}
	public String getPartDescription() {
		return partDescription;
	}
	public String getSpec() {
		return spec;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public BigDecimal getCatalogPrice() {
		return catalogPrice;
	}
	public BigDecimal getBaselinePrice() {
		return baselinePrice;
	}
	public String getCurrencyId() {
		return currencyId;
	}
	public String getUnitOfSale() {
		return unitOfSale;
	}
	public BigDecimal getUnitOfSaleQuantityPerEach() {
		return unitOfSaleQuantityPerEach;
	}
	public String getDocumentUrl() {
		return documentUrl;
	}
	public String getOnLine() {
		return onLine;
	}
        public String getMrLine() {
                return mrLine;
        }
        public String getPoLine() {
                return poLine;
        }
        public BigDecimal getQuantityIssued() {
                return quantityIssued;
        }
        public String getMfgLot() {
                return mfgLot;
        }

	public Date getExpireDate() {
		return expireDate;
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
	
	
}