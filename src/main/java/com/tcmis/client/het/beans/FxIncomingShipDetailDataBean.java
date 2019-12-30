package com.tcmis.client.het.beans;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.StringHandler;

public class FxIncomingShipDetailDataBean extends BaseDataBean {

	private String									addressLine1;
	private String									addressLine2;
	private String									addressLine3;
	private String									addressLine4;
	private String									addressLine5;
	private String									applicationDesc;
	private BigDecimal								applicationId;
	private Collection<VvHetApplicationMethodBean>	applicationMethods;
	private BigDecimal								buildingId;
	private String									buildingName;
	private String									catalogCompanyId;
	private String									catalogId;
	private String									cms;
	private BigDecimal								componentsPerItem;
	private String									containerId;
	private String									containerSize;
	private String									containerType;
	private BigDecimal								customerId;
	private String									customerMsdsDb;
	private String									customerMsdsNo;
	private String									customerMsdsOrMixtureNo;
	private String									customerName;
	private String									customerServiceRep;
	private Date									dateDelivered;
	private String									defaultApplicationMethodCod;
	private String									defaultPartType;
	private String									defaultPermitId;
	private String									defaultSubstrateCode;
	private boolean									diluent			= false;
	private String									distribution;
	private Date									expireDate;
	private String									facilityId;
	private String									facpartNo;
	private String									hetUsageRecording;
	private boolean									inseparableKit;
	private String									itemDesc;
	private BigDecimal								itemId;
	private int										kitCount		= -1;
	private String									lineItem;
	private String									materialDesc;
	private BigDecimal								materialId;
	private String									materialRequestOrigin;
	private String									method;
	private String									mfgLot;
	private BigDecimal								notInContainer;
	private String									oldShippingReference;
	private BigDecimal								partId;
	private String									permitName;
	private Collection<HetPermitBean>				permits;
	private BigDecimal								pickListId;
	private BigDecimal								prNumber;
	private String									qcBy;
	private Date									qcDate;
	private BigDecimal								quantityShipped;
	private BigDecimal								receiptId;
	private String									reportingEntityId;
	private String									requestor;
	private int										rowCount;
	private String									rowKey			= null;
	private String									shipConfirmBy;
	private Date									shipConfirmDate;
	private BigDecimal								shipmentId;
	private String									shippingReference;
	private String									shipToLocationId;
	private boolean									solvent			= false;
	private String									substrate;
	private Collection<VvHetSubstrateBean>			substrates;
	private BigDecimal								transactionId;
	private boolean									usageLoggingReq	= true;

	// constructor
	public FxIncomingShipDetailDataBean() {
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public String getAddressLine3() {
		return addressLine3;
	}

	public String getAddressLine4() {
		return addressLine4;
	}

	public String getAddressLine5() {
		return addressLine5;
	}

	public String getApplicationDesc() {
		return applicationDesc;
	}

	public BigDecimal getApplicationId() {
		return applicationId;
	}

	public Collection<VvHetApplicationMethodBean> getApplicationMethods() {
		return applicationMethods;
	}

	public BigDecimal getBuildingId() {
		return buildingId;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public String getCms() {
		return cms;
	}

	public BigDecimal getComponentsPerItem() {
		return componentsPerItem;
	}

	public String getContainerId() {
		return containerId;
	}

	public String getContainerSize() {
		return containerSize;
	}

	public String getContainerType() {
		return containerType;
	}

	public BigDecimal getCustomerId() {
		return customerId;
	}

	public String getCustomerMsdsDb() {
		return customerMsdsDb;
	}

	public String getCustomerMsdsNo() {
		return customerMsdsNo;
	}

	public String getCustomerMsdsOrMixtureNo() {
		return customerMsdsOrMixtureNo;
	}

	public String getCustomerName() {
		return customerName;
	}

	public String getCustomerServiceRep() {
		return customerServiceRep;
	}

	public Date getDateDelivered() {
		return dateDelivered;
	}

	public String getDefaultApplicationMethodCod() {
		return defaultApplicationMethodCod;
	}

	public String getDefaultPartType() {
		return defaultPartType;
	}

	public String getDefaultPermitId() {
		return defaultPermitId;
	}

	public String getDefaultSubstrateCode() {
		return defaultSubstrateCode;
	}

	public String getDistribution() {
		return distribution;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public String getFacpartNo() {
		return facpartNo;
	}

	public String getHetUsageRecording() {
		return hetUsageRecording;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public int getKitCount() {
		return kitCount;
	}

	public String getLineItem() {
		return lineItem;
	}

	public String getMaterialDesc() {
		return materialDesc;
	}

	public BigDecimal getMaterialId() {
		return materialId;
	}

	public String getMaterialRequestOrigin() {
		return materialRequestOrigin;
	}

	public String getMethod() {
		return method;
	}

	public String getMfgLot() {
		return mfgLot;
	}

	public BigDecimal getNotInContainer() {
		return notInContainer;
	}

	public String getOldShippingReference() {
		return oldShippingReference;
	}

	public BigDecimal getPartId() {
		return partId;
	}

	public String getPermitName() {
		return permitName;
	}

	public Collection<HetPermitBean> getPermits() {
		return permits;
	}

	public BigDecimal getPickListId() {
		return pickListId;
	}

	public BigDecimal getPrNumber() {
		return prNumber;
	}

	public String getQcBy() {
		return qcBy;
	}

	public Date getQcDate() {
		return qcDate;
	}

	public BigDecimal getQuantityShipped() {
		return quantityShipped;
	}

	public BigDecimal getReceiptId() {
		return receiptId;
	}

	public String getReportingEntityId() {
		return reportingEntityId;
	}

	public String getRequestor() {
		return requestor;
	}

	public int getRowCount() {
		return rowCount;
	}

	public String getRowKey() {
		if (rowKey == null) {
			rowKey = shipmentId + "|" + applicationId + "|" + itemId + "|" + prNumber + "|" + receiptId;
		}
		return rowKey;
	}

	public String getShipConfirmBy() {
		return shipConfirmBy;
	}

	public Date getShipConfirmDate() {
		return shipConfirmDate;
	}

	public BigDecimal getShipmentId() {
		return shipmentId;
	}

	public String getShippingReference() {
		return shippingReference;
	}

	public String getShipToLocationId() {
		return shipToLocationId;
	}

	public String getSubstrate() {
		return substrate;
	}

	public Collection<VvHetSubstrateBean> getSubstrates() {
		return substrates;
	}

	public BigDecimal getTransactionId() {
		return transactionId;
	}

	public boolean hasContainerId() {
		return !StringHandler.isBlankString(containerId);
	}

	public boolean isDiluent() {
		return diluent;
	}

	public boolean isInseparableKit() {
		return inseparableKit;
	}

	public boolean isKit() {
		return kitCount != -1;
	}

	public boolean isMixture() {
		return !customerMsdsOrMixtureNo.equals(customerMsdsNo);
	}

	public boolean isSolvent() {
		return solvent;
	}

	public boolean isUsageLoggingReq() {
		return "Y".equals(hetUsageRecording) || "Daily Usage".equalsIgnoreCase(hetUsageRecording);
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}

	public void setAddressLine4(String addressLine4) {
		this.addressLine4 = addressLine4;
	}

	public void setAddressLine5(String addressLine5) {
		this.addressLine5 = addressLine5;
	}

	public void setApplicationDesc(String applicationDesc) {
		this.applicationDesc = applicationDesc;
	}

	public void setApplicationId(BigDecimal applicationId) {
		this.applicationId = applicationId;
	}

	public void setApplicationMethods(Collection<VvHetApplicationMethodBean> applicationMethods) {
		this.applicationMethods = applicationMethods;
	}

	public void setBuildingId(BigDecimal buildingId) {
		this.buildingId = buildingId;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	public void setCatalogCompanyId(String catalogCompanyId) {
		this.catalogCompanyId = catalogCompanyId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public void setCms(String cms) {
		this.cms = cms;
	}

	public void setComponentsPerItem(BigDecimal componentsPerItem) {
		this.componentsPerItem = componentsPerItem;
	}

	public void setContainerId(String containerId) {
		this.containerId = containerId;
	}

	public void setContainerSize(String containerSize) {
		this.containerSize = containerSize;
	}

	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	public void setCustomerMsdsDb(String customerMsdsDb) {
		this.customerMsdsDb = customerMsdsDb;
	}

	public void setCustomerMsdsNo(String componentMsdsNo) {
		this.customerMsdsNo = componentMsdsNo;
	}

	public void setCustomerMsdsOrMixtureNo(String customerMsdsOrMixtureNo) {
		this.customerMsdsOrMixtureNo = customerMsdsOrMixtureNo;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public void setCustomerServiceRep(String customerServiceRep) {
		this.customerServiceRep = customerServiceRep;
	}

	public void setDateDelivered(Date dateDelivered) {
		this.dateDelivered = dateDelivered;
	}

	public void setDefaultApplicationMethodCod(String defaultApplicationMethodCod) {
		this.defaultApplicationMethodCod = defaultApplicationMethodCod;
	}

	public void setDefaultPartType(String defaultPartType) {
		this.defaultPartType = defaultPartType;
	}

	public void setDefaultPermitId(String defaultPermitId) {
		this.defaultPermitId = defaultPermitId;
	}

	public void setDefaultSubstrateCode(String defaultSubstrateCode) {
		this.defaultSubstrateCode = defaultSubstrateCode;
	}

	public void setDiluent(boolean diluent) {
		this.diluent = diluent;
	}

	public void setDistribution(String distribution) {
		this.distribution = distribution;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public void setFacpartNo(String facpartNo) {
		this.facpartNo = facpartNo;
	}

	public void setHetUsageRecording(String hetUsageRecording) {
		this.hetUsageRecording = hetUsageRecording;
	}

	public void setInseparableKit(boolean inseparableKit) {
		this.inseparableKit = inseparableKit;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public void setKitCount(int kitCount) {
		this.kitCount = kitCount;
	}

	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}

	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}

	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}

	public void setMaterialRequestOrigin(String materialRequestOrigin) {
		this.materialRequestOrigin = materialRequestOrigin;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public void setMfgLot(String mfgLot) {
		this.mfgLot = mfgLot;
	}

	public void setNotInContainer(BigDecimal notInContainer) {
		this.notInContainer = notInContainer;
	}

	public void setOldShippingReference(String oldShippingReference) {
		this.oldShippingReference = oldShippingReference;
	}

	public void setPartId(BigDecimal partId) {
		this.partId = partId;
	}

	public void setPermitName(String permitName) {
		this.permitName = permitName;
	}

	public void setPermits(Collection<HetPermitBean> permits) {
		this.permits = permits;
	}

	public void setPickListId(BigDecimal pickListId) {
		this.pickListId = pickListId;
	}

	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}

	public void setQcBy(String qcBy) {
		this.qcBy = qcBy;
	}

	public void setQcDate(Date qcDate) {
		this.qcDate = qcDate;
	}

	public void setQuantityShipped(BigDecimal quantityShipped) {
		this.quantityShipped = quantityShipped;
	}

	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}

	public void setReportingEntityId(String reportingEntityId) {
		this.reportingEntityId = reportingEntityId;
	}

	public void setRequestor(String requestor) {
		this.requestor = requestor;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public void setShipConfirmBy(String shipConfirmBy) {
		this.shipConfirmBy = shipConfirmBy;
	}

	public void setShipConfirmDate(Date shipConfirmDate) {
		this.shipConfirmDate = shipConfirmDate;
	}

	public void setShipmentId(BigDecimal shipmentId) {
		this.shipmentId = shipmentId;
	}

	public void setShippingReference(String shippingReference) {
		this.shippingReference = shippingReference;
	}

	public void setShipToLocationId(String shipToLocationId) {
		this.shipToLocationId = shipToLocationId;
	}

	public void setSolvent(boolean solvent) {
		this.solvent = solvent;
	}

	public void setSubstrate(String substrate) {
		this.substrate = substrate;
	}

	public void setSubstrates(Collection<VvHetSubstrateBean> substrates) {
		this.substrates = substrates;
	}

	public void setTransactionId(BigDecimal transactionId) {
		this.transactionId = transactionId;
	}

	public void setUsageLoggingReq(boolean usageLoggingReq) {
		// this.usageLoggingReq = usageLoggingReq;
		// Not used
	}

}
