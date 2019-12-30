package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.StringHandler;


/******************************************************************************
 * CLASSNAME: TransactionTrackingViewBean <br>
 * @version: 1.0, Apr 30, 2007 <br>
 *****************************************************************************/


public class TransactionTrackingViewBean extends BaseDataBean {

	private String inventoryGroup;
	private String hub;
	private BigDecimal receiptId;
	private BigDecimal issueId;
	private BigDecimal itemId;
	private BigDecimal quantity;
	private String mfgLot;
	private String bin;
	private Date dateOfReceipt;
	private Date transactionDate;
	private BigDecimal radianPo;
	private BigDecimal poLine;
	private Date dateOfManufacture;
	private String receiverId;
	private BigDecimal prNumber;
	private String lineItem;
	private Date dateDelivered;
	private BigDecimal salesOrder;
	private String confirmed;
	private String receiverName;
	private String issuerName;
	private String transactionType;
	private String transferredFrom;
	private String transferredTo;
	private String companyId;
	private String catalogId;
	private String catPartNo;
	private String lotStatus;
	private BigDecimal qcUser;
	private Date qcDate;
	private Date shipConfirmDate;
	private BigDecimal shipConfirmUser;
	private BigDecimal priceQcUser;
	private Date priceQcDate;
	private BigDecimal picklistId;
	private Date picklistPrintDate;
	private BigDecimal picklistQuantity;
	private Date needDate;
	private Date expireDate;
	private String notes;
	private BigDecimal batch;
	private String storageTemp;
	private String inventoryGroupName;
	private String labelStorageTemp;
	private String deliveryTicket;
	private String trackingNumber;
	private String packaging;
    private String itemDesc;
    private String shipmentId;
    private String distributorOps;
    private BigDecimal cost;
    private BigDecimal landedCost;
	private String homeCurrencyId;
	private String opsCompanyId;
	private String opsEntityId;
	private String operatingEntityName;
	private String itemDescription;
	private String customerName;
	private String supplierName;
	private String sourceInvGroupName;
	private String destinationInvGroupName;
	private String receivingStatus;
	
  public String getReceivingStatus() {
    return receivingStatus;
  }

  public void setReceivingStatus(String receivingStatus) {

    this.receivingStatus = receivingStatus;
  }

  public String getPackaging() {
    return packaging;
  }

  public void setPackaging(String packaging) {
    //packaging = StringHandler.replace(packaging,new String("\""),"");
    this.packaging = packaging;
  }

  public String getItemDesc() {
    return itemDesc;
  }

  public void setItemDesc(String itemDesc) {    
    //itemDesc = StringHandler.replace(itemDesc,new String("\""),"");
    this.itemDesc = itemDesc;
  }

  //constructor
	public TransactionTrackingViewBean() {
	}

	//setters
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setHub(String hub) {
		this.hub = hub;
	}
	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}
	public void setIssueId(BigDecimal issueId) {
		this.issueId = issueId;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public void setMfgLot(String mfgLot) {
		this.mfgLot = mfgLot;
	}
	public void setBin(String bin) {
		this.bin = bin;
	}
	public void setDateOfReceipt(Date dateOfReceipt) {
		this.dateOfReceipt = dateOfReceipt;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}
	public void setPoLine(BigDecimal poLine) {
		this.poLine = poLine;
	}
	public void setDateOfManufacture(Date dateOfManufacture) {
		this.dateOfManufacture = dateOfManufacture;
	}
	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}
	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}
	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}
	public void setDateDelivered(Date dateDelivered) {
		this.dateDelivered = dateDelivered;
	}
	public void setSalesOrder(BigDecimal salesOrder) {
		this.salesOrder = salesOrder;
	}
	public void setConfirmed(String confirmed) {
		this.confirmed = confirmed;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public void setIssuerName(String issuerName) {
		this.issuerName = issuerName;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public void setTransferredFrom(String transferredFrom) {
		this.transferredFrom = transferredFrom;
	}
	public void setTransferredTo(String transferredTo) {
		this.transferredTo = transferredTo;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}
	public void setLotStatus(String lotStatus) {
		this.lotStatus = lotStatus;
	}
	public void setQcUser(BigDecimal qcUser) {
		this.qcUser = qcUser;
	}
	public void setQcDate(Date qcDate) {
		this.qcDate = qcDate;
	}
	public void setShipConfirmDate(Date shipConfirmDate) {
		this.shipConfirmDate = shipConfirmDate;
	}
	public void setShipConfirmUser(BigDecimal shipConfirmUser) {
		this.shipConfirmUser = shipConfirmUser;
	}
	public void setPriceQcUser(BigDecimal priceQcUser) {
		this.priceQcUser = priceQcUser;
	}
	public void setPriceQcDate(Date priceQcDate) {
		this.priceQcDate = priceQcDate;
	}
	public void setPicklistId(BigDecimal picklistId) {
		this.picklistId = picklistId;
	}
	public void setPicklistPrintDate(Date picklistPrintDate) {
		this.picklistPrintDate = picklistPrintDate;
	}
	public void setPicklistQuantity(BigDecimal picklistQuantity) {
		this.picklistQuantity = picklistQuantity;
	}
	public void setNeedDate(Date needDate) {
		this.needDate = needDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public void setBatch(BigDecimal batch) {
		this.batch = batch;
	}
	public void setStorageTemp(String storageTemp) {
		this.storageTemp = storageTemp;
	}
	public void setInventoryGroupName(String inventoryGroupName) {
		this.inventoryGroupName = inventoryGroupName;
	}
	public void setLabelStorageTemp(String labelStorageTemp) {
		this.labelStorageTemp = labelStorageTemp;
	}
	public void setDeliveryTicket(String deliveryTicket) {
		this.deliveryTicket = deliveryTicket;
	}
	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}
	public void setLandedCost(BigDecimal landedCost) {
		this.landedCost = landedCost;
	}
	public void setHomeCurrencyId(String homeCurrencyId) {
		this.homeCurrencyId = homeCurrencyId;
	}
	public void setOpsCompanyId(String opsCompanyId) {
		this.opsCompanyId = opsCompanyId;
	}
	public void setOpsEntityID(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}
	public void setOperatingEntityName (String operatingEntityName) {
		this.operatingEntityName = operatingEntityName;
	}


	//getters
	public String getHomeCurrencyId() {
		return homeCurrencyId;
	}
	public String getOpsCompanyId() {
		return opsCompanyId;
	}
	public String getOpsEntityId() {
		return opsEntityId;
	}
	public String getOperatingEntityName() {
		return operatingEntityName;
	}
	public BigDecimal getCost() {
		return this.cost;
	}
	
	public BigDecimal getLandedCost() {
		return this.landedCost;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public String getHub() {
		return hub;
	}
	public BigDecimal getReceiptId() {
		return receiptId;
	}
	public BigDecimal getIssueId() {
		return issueId;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public String getMfgLot() {
		return mfgLot;
	}
	public String getBin() {
		return bin;
	}
	public Date getDateOfReceipt() {
		return dateOfReceipt;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public BigDecimal getRadianPo() {
		return radianPo;
	}
	public BigDecimal getPoLine() {
		return poLine;
	}
	public Date getDateOfManufacture() {
		return dateOfManufacture;
	}
	public String getReceiverId() {
		return receiverId;
	}
	public BigDecimal getPrNumber() {
		return prNumber;
	}
	public String getLineItem() {
		return lineItem;
	}
	public Date getDateDelivered() {
		return dateDelivered;
	}
	public BigDecimal getSalesOrder() {
		return salesOrder;
	}
	public String getConfirmed() {
		return confirmed;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public String getIssuerName() {
		return issuerName;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public String getTransferredFrom() {
		return transferredFrom;
	}
	public String getTransferredTo() {
		return transferredTo;
	}
	public String getCompanyId() {
		return companyId;
	}
	public String getCatalogId() {
		return catalogId;
	}
	public String getCatPartNo() {
		return catPartNo;
	}
	public String getLotStatus() {
		return lotStatus;
	}
	public BigDecimal getQcUser() {
		return qcUser;
	}
	public Date getQcDate() {
		return qcDate;
	}
	public Date getShipConfirmDate() {
		return shipConfirmDate;
	}
	public BigDecimal getShipConfirmUser() {
		return shipConfirmUser;
	}
	public BigDecimal getPriceQcUser() {
		return priceQcUser;
	}
	public Date getPriceQcDate() {
		return priceQcDate;
	}
	public BigDecimal getPicklistId() {
		return picklistId;
	}
	public Date getPicklistPrintDate() {
		return picklistPrintDate;
	}
	public BigDecimal getPicklistQuantity() {
		return picklistQuantity;
	}
	public Date getNeedDate() {
		return needDate;
	}
	public Date getExpireDate() {
		return expireDate;
	}
	public String getNotes() {
		return notes;
	}
	public BigDecimal getBatch() {
		return batch;
	}
	public String getStorageTemp() {
		return storageTemp;
	}
	public String getInventoryGroupName() {
		return inventoryGroupName;
	}
	public String getLabelStorageTemp() {
		return labelStorageTemp;
	}
	public String getDeliveryTicket() {
		return deliveryTicket;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public String getShipmentId() {
		return shipmentId;
	}

	public void setShipmentId(String shipmentId) {
		this.shipmentId = shipmentId;
	}

	public String getDistributorOps() {
		return distributorOps;
	}

	public void setDistributorOps(String distributorOps) {
		this.distributorOps = distributorOps;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}

	public String getDestinationInvGroupName() {
		return destinationInvGroupName;
	}

	public void setDestinationInvGroupName(String destinationInvGroupName) {
		this.destinationInvGroupName = destinationInvGroupName;
	}

	public String getSourceInvGroupName() {
		return sourceInvGroupName;
	}

	public void setSourceInvGroupName(String sourceInvGroupName) {
		this.sourceInvGroupName = sourceInvGroupName;
	}

}