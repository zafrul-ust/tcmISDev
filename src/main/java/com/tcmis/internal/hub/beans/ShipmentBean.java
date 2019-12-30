package com.tcmis.internal.hub.beans;

import java.util.Collection;
import java.util.Date;
import java.util.Vector;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: ShipmentBean <br>
 * @version: 1.0, Feb 19, 2007 <br>
 *****************************************************************************/

public class ShipmentBean
    extends BaseDataBean {

	private BigDecimal shipmentId;
	private String trackingNumber;
	private String carrierCode;
	private String inventoryGroup;
	private String branchPlant;
	private String shipToLocationId;
	private String companyId;
	private Date dateDelivered;
	private Date shipConfirmDate;
	private Date dateInserted;
	private String carrierName;
	private String deliveryTicket;
	private String billOfLading;
	private Date dateShipped;
	private BigDecimal iataSignatureId;
	private String verifiedForAsn;
	private String verifiedForAsnComments;
	private String iataAdditionalHandling;
	private Date iataDateSigned;
	private String carrierLabelFilename;
	private String carrierCurrencyCode;
	private BigDecimal carrierTotalCharges;
    private String currencyId;
    private String ok;
    private Collection carrierInfoBeanCollection = new Vector();
    private String defaultTxCarrierCode;
    private String defaultTxCarrierName;
    private String defaultTxCarrierAcct;
    private String defaultTxCarrierMthd;   
    private String defaultTxCarrierComp;
    
    private String incotermRequired;
	private String incoterm;
	private String modeOfTransport;
	private String invoiceAtShipping;
	private String invoiceBy;
	private Date lastProFormaPrintDate;
	private Date proposedExportDate;
	private String proFormaRequired;

	//constructor
	public ShipmentBean() {
	}

	//setters
	public void setShipmentId(BigDecimal shipmentId) {
		this.shipmentId = shipmentId;
	}
	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setBranchPlant(String branchPlant) {
		this.branchPlant = branchPlant;
	}
	public void setShipToLocationId(String shipToLocationId) {
		this.shipToLocationId = shipToLocationId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setDateDelivered(Date dateDelivered) {
		this.dateDelivered = dateDelivered;
	}
	public void setShipConfirmDate(Date shipConfirmDate) {
		this.shipConfirmDate = shipConfirmDate;
	}
	public void setDateInserted(Date dateInserted) {
		this.dateInserted = dateInserted;
	}
	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}
	public void setDeliveryTicket(String deliveryTicket) {
		this.deliveryTicket = deliveryTicket;
	}
	public void setBillOfLading(String billOfLading) {
		this.billOfLading = billOfLading;
	}
	public void setDateShipped(Date dateShipped) {
		this.dateShipped = dateShipped;
	}
	public void setIataSignatureId(BigDecimal iataSignatureId) {
		this.iataSignatureId = iataSignatureId;
	}
	public void setVerifiedForAsn(String verifiedForAsn) {
		this.verifiedForAsn = verifiedForAsn;
	}
	public void setVerifiedForAsnComments(String verifiedForAsnComments) {
		this.verifiedForAsnComments = verifiedForAsnComments;
	}
	public void setIataAdditionalHandling(String iataAdditionalHandling) {
		this.iataAdditionalHandling = iataAdditionalHandling;
	}
	public void setIataDateSigned(Date iataDateSigned) {
		this.iataDateSigned = iataDateSigned;
	}
	public void setCarrierLabelFilename(String carrierLabelFilename) {
		this.carrierLabelFilename = carrierLabelFilename;
	}
	public void setCarrierCurrencyCode(String carrierCurrencyCode) {
		this.carrierCurrencyCode = carrierCurrencyCode;
	}
	public void setCarrierTotalCharges(BigDecimal carrierTotalCharges) {
		this.carrierTotalCharges = carrierTotalCharges;
	}
    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }
    public void setOk(String ok) {
        this.ok = ok;
    }
    public void setCarrierInfoBeanCollection(Collection c) {
    this.carrierInfoBeanCollection = c;
    }
    //getters
	public BigDecimal getShipmentId() {
		return shipmentId;
	}
	public String getTrackingNumber() {
		return trackingNumber;
	}
	public String getCarrierCode() {
		return carrierCode;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public String getBranchPlant() {
		return branchPlant;
	}
	public String getShipToLocationId() {
		return shipToLocationId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public Date getDateDelivered() {
		return dateDelivered;
	}
	public Date getShipConfirmDate() {
		return shipConfirmDate;
	}
	public Date getDateInserted() {
		return dateInserted;
	}
	public String getCarrierName() {
		return carrierName;
	}
	public String getDeliveryTicket() {
		return deliveryTicket;
	}
	public String getBillOfLading() {
		return billOfLading;
	}
	public Date getDateShipped() {
		return dateShipped;
	}
	public BigDecimal getIataSignatureId() {
		return iataSignatureId;
	}
	public String getVerifiedForAsn() {
		return verifiedForAsn;
	}
	public String getVerifiedForAsnComments() {
		return verifiedForAsnComments;
	}
	public String getIataAdditionalHandling() {
		return iataAdditionalHandling;
	}
	public Date getIataDateSigned() {
		return iataDateSigned;
	}
	public String getCarrierLabelFilename() {
		return carrierLabelFilename;
	}
	public String getCarrierCurrencyCode() {
		return carrierCurrencyCode;
	}
	public BigDecimal getCarrierTotalCharges() {
		return carrierTotalCharges;
	}
    public String getCurrencyId() {
        return currencyId;
    }
    public String getOk() {
        return ok;
   }
   public Collection getCarrierInfoBeanCollection() {
    return this.carrierInfoBeanCollection;
   }
   public String getDefaultTxCarrierCode() {
		return defaultTxCarrierCode;
	}

	public void setDefaultTxCarrierCode(String defaultTxCarrierCode) {
		this.defaultTxCarrierCode = defaultTxCarrierCode;
	}

	public String getDefaultTxCarrierName() {
		return defaultTxCarrierName;
	}

	public void setDefaultTxCarrierName(String defaultTxCarrierName) {
		this.defaultTxCarrierName = defaultTxCarrierName;
	}

	public String getDefaultTxCarrierAcct() {
		return defaultTxCarrierAcct;
	}

	public void setDefaultTxCarrierAcct(String defaultTxCarrierAcct) {
		this.defaultTxCarrierAcct = defaultTxCarrierAcct;
	}

	public String getDefaultTxCarrierMthd() {
		return defaultTxCarrierMthd;
	}

	public void setDefaultTxCarrierMthd(String defaultTxCarrierMthd) {
		this.defaultTxCarrierMthd = defaultTxCarrierMthd;
	}
	public String getDefaultTxCarrierComp() {
		return defaultTxCarrierComp;
	}
	public void setDefaultTxCarrierComp(String defaultTxCarrierComp) {
		this.defaultTxCarrierComp = defaultTxCarrierComp;
	}
	
	public String getIncoterm() {
		return incoterm;
	}

	public void setIncoterm(String incoterm) {
		this.incoterm = incoterm;
	}

	public String getModeOfTransport() {
		return modeOfTransport;
	}

	public void setModeOfTransport(String modeOfTransport) {
		this.modeOfTransport = modeOfTransport;
	}

	public String getIncotermRequired() {
		return incotermRequired;
	}

	public void setIncotermRequired(String incotermRequired) {
		this.incotermRequired = incotermRequired;
	}

	public String getInvoiceAtShipping() {
		return invoiceAtShipping;
	}

	public void setInvoiceAtShipping(String invoiceAtShipping) {
		this.invoiceAtShipping = invoiceAtShipping;
	}

	public String getInvoiceBy() {
		return invoiceBy;
	}

	public void setInvoiceBy(String invoiceBy) {
		this.invoiceBy = invoiceBy;
	}
   
	public Date getLastProFormaPrintDate() {
		return lastProFormaPrintDate;
	}

	public Date getProposedExportDate() {
		return proposedExportDate;
	}

	public void setLastProFormaPrintDate(Date lastProFormaPrintDate) {
		this.lastProFormaPrintDate = lastProFormaPrintDate;
	}

	public void setProposedExportDate(Date proposedExportDate) {
		this.proposedExportDate = proposedExportDate;
	}

	public String getProFormaRequired() {
		return proFormaRequired;
	}

	public void setProFormaRequired(String proFormaRequired) {
		this.proFormaRequired = proFormaRequired;
	}

	public boolean isProformaRequired(){
		if(!StringHandler.isBlankString(getProFormaRequired()) && getProFormaRequired().toUpperCase().equals("Y"))
			return true;
			
		return false;
	}
}