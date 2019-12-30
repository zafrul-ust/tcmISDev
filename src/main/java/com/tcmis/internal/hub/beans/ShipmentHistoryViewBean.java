package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Collection;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: ShipmentHistoryViewBean <br>
 * @version: 1.0, Apr 27, 2005 <br>
 *****************************************************************************/


public class ShipmentHistoryViewBean extends BaseDataBean {

	private BigDecimal shipmentId;
	private String trackingNumber;
	private String carrierCode;
	private String account;
	private String carrierName;
	private String haasVendor;
	private String carrierOwner;
	private String inventoryGroup;
	private String inventoryGroupName;
	private String branchPlant;
	private String companyId;
	private Date dateDelivered;
	private Date shipConfirmDate;
	private String facilityId;
	private String carrierMethod;
	private String operatingEntityName;
	private String hubName;
	private BigDecimal customerId;
	private String customerName;
	private String shipToLocationId;
	private String addressLine1;
	private String addressLine2;
	private String addressLine3;
	private String addressLine4;
	private String addressLine5;
    private Collection carrierInfoBeanCollection;
    private String ok;
    
	private BigDecimal issueId;
	private BigDecimal prNumber;
	private String opsEntityId;
	
	private String materialRequestOrigin;
	private String cms;
	private String distribution;
	private String shippingReference;
	
	private String printInvoice;
	
	private String incotermRequired;
	private String incoterm;
	private String modeOfTransport;
	private String invoiceAtShipping;
	private String invoiceBy;
	private Date lastProFormaPrintDate;
	private Date proposedExportDate;
	private String proFormaRequired;
	
	public String getShippingReference() {
		return shippingReference;
	}

	public void setShippingReference(String shippingReference) {
		this.shippingReference = shippingReference;
	}

	//constructor
	public ShipmentHistoryViewBean() {
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
	public void setAccount(String account) {
		this.account = account;
	}
	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}
	public void setHaasVendor(String haasVendor) {
		this.haasVendor = haasVendor;
	}
	public void setCarrierOwner(String carrierOwner) {
		this.carrierOwner = carrierOwner;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setBranchPlant(String branchPlant) {
		this.branchPlant = branchPlant;
	}
	public void setHubName(String hubName) {
		this.hubName = hubName;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setShipToLocationId(String shipToLocationId) {
		this.shipToLocationId = shipToLocationId;
	}
	public void setDateDelivered(Date dateDelivered) {
		this.dateDelivered = dateDelivered;
	}
	public void setShipConfirmDate(Date shipConfirmDate) {
		this.shipConfirmDate = shipConfirmDate;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setCarrierMethod(String carrierMethod) {
		this.carrierMethod = carrierMethod;
	}
	public void setIssueId(BigDecimal issueId) {
		this.issueId = issueId;
	}
	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}
	public void setOperatingEntityName(String operatingEntityName) {
		this.operatingEntityName = operatingEntityName;
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
	public String getAccount() {
		return account;
	}
	public String getCarrierName() {
		return carrierName;
	}
	public String getHaasVendor() {
		return haasVendor;
	}
	public String getCarrierOwner() {
		return carrierOwner;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public String getBranchPlant() {
		return branchPlant;
	}
	public String getHubName() {
		return hubName;
	}
	public String getCompanyId() {
		return companyId;
	}
	public String getShipToLocationId() {
		return shipToLocationId;
	}
	public Date getDateDelivered() {
		return dateDelivered;
	}
	public Date getShipConfirmDate() {
		return shipConfirmDate;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public String getCarrierMethod() {
		return carrierMethod;
	}
	public BigDecimal getIssueId() {
		return issueId;
	}
	public BigDecimal getPrNumber() {
		return prNumber;
	}
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public String getOpsEntityId() {
		return opsEntityId;
	}
	public String getOperatingEntityName() {
		return operatingEntityName;
	}
    public Collection getCarrierInfoBeanCollection() {
        return carrierInfoBeanCollection;
    }

    public void setCarrierInfoBeanCollection(Collection carrierInfoBeanCollection) {
        this.carrierInfoBeanCollection = carrierInfoBeanCollection;
    }

    public String getOk() {
        return ok;
    }

    public void setOk(String ok) {
        this.ok = ok;
    }

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getAddressLine3() {
		return addressLine3;
	}

	public String getAddressLine4() {
		return addressLine4;
	}

	public void setAddressLine4(String addressLine4) {
		this.addressLine4 = addressLine4;
	}

	public String getAddressLine5() {
		return addressLine5;
	}

	public void setAddressLine5(String addressLine5) {
		this.addressLine5 = addressLine5;
	}

	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}

	public String getInventoryGroupName() {
		return inventoryGroupName;
	}

	public void setInventoryGroupName(String inventoryGroupName) {
		this.inventoryGroupName = inventoryGroupName;
	}

	public String getMaterialRequestOrigin() {
		return materialRequestOrigin;
	}

	public void setMaterialRequestOrigin(String materialRequestOrigin) {
		this.materialRequestOrigin = materialRequestOrigin;
	}

	public String getCms() {
		return cms;
	}

	public void setCms(String cms) {
		this.cms = cms;
	}

	public String getDistribution() {
		return distribution;
	}

	public void setDistribution(String distribution) {
		this.distribution = distribution;
	}

	public String getPrintInvoice() {
		return printInvoice;
	}

	public void setPrintInvoice(String printInvoice) {
		this.printInvoice = printInvoice;
	}

	public String getIncotermRequired() {
		return incotermRequired;
	}

	public void setIncotermRequired(String incotermRequired) {
		this.incotermRequired = incotermRequired;
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