package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

public class ShipmentHistoryDetailsViewBean extends BaseDataBean {

	private String addressLine1;
	private String addressLine2;
	private String addressLine3;
	private String addressLine4;
	private String addressLine5;
	private BigDecimal customerId;
	private String customerName;
	private String customerServiceRep;
	private Date expireDate;
	private String facpartNo;
	private String itemDesc;
	private BigDecimal itemId;
	private int itemRowspan = 1;
	private BigDecimal lineItem;
	private String materialRequestOrigin;
	private String mfgLot;
	private int mrRowspan = 1;
	private BigDecimal pickListId;
	private BigDecimal prNumber;
	private String qcBy;
	private Date qcDate;
	private BigDecimal quantityShipped;
	private BigDecimal receiptId;
	private int receiptRowspan = 1;
	private String requester;
	private String shipConfirmBy;

	private Date shipConfirmDate;
	private String shipToLocationId;
	
	private String cms;
	private String distribution;
	private String shippingReference;
	private String oldShippingReference;
	private String poNumber;

	public String getOldShippingReference() {
		return oldShippingReference;
	}

	public void setOldShippingReference(String oldShippingReference) {
		this.oldShippingReference = oldShippingReference;
	}

	public String getShippingReference() {
		return shippingReference;
	}

	public void setShippingReference(String shippingReference) {
		this.shippingReference = shippingReference;
	}

	public ShipmentHistoryDetailsViewBean() {
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

	public BigDecimal getCustomerId() {
		return customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public String getCustomerServiceRep() {
		return customerServiceRep;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public String getFacpartNo() {
		return facpartNo;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public int getItemRowspan() {
		return itemRowspan;
	}

	public BigDecimal getLineItem() {
		return lineItem;
	}

	public String getMaterialRequestOrigin() {
		return materialRequestOrigin;
	}

	public String getMfgLot() {
		return mfgLot;
	}

	public int getMrRowspan() {
		return mrRowspan;
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

	public int getReceiptRowspan() {
		return receiptRowspan;
	}

	public String getRequester() {
		return requester;
	}

	public String getShipConfirmBy() {
		return shipConfirmBy;
	}

	public Date getShipConfirmDate() {
		return shipConfirmDate;
	}

	public String getShipToLocationId() {
		return shipToLocationId;
	}

	public String getPoNumber() {
		return poNumber;
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

	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public void setCustomerServiceRep(String customerServiceRep) {
		this.customerServiceRep = customerServiceRep;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public void setFacpartNo(String facpartNo) {
		this.facpartNo = facpartNo;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public void setItemRowspan(int itemRowspan) {
		this.itemRowspan = itemRowspan;
	}

	public void setLineItem(BigDecimal lineItem) {
		this.lineItem = lineItem;
	}

	public void setMaterialRequestOrigin(String materialRequestOrigin) {
		this.materialRequestOrigin = materialRequestOrigin;
	}

	public void setMfgLot(String mfgLot) {
		this.mfgLot = mfgLot;
	}

	public void setMrRowspan(int mrRowspan) {
		this.mrRowspan = mrRowspan;
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

	public void setReceiptRowspan(int receiptRowspan) {
		this.receiptRowspan = receiptRowspan;
	}

	public void setRequester(String requester) {
		this.requester = requester;
	}

	public void setShipConfirmBy(String shipConfirmBy) {
		this.shipConfirmBy = shipConfirmBy;
	}

	public void setShipConfirmDate(Date shipConfirmDate) {
		this.shipConfirmDate = shipConfirmDate;
	}

	public void setShipToLocationId(String shipToLocationId) {
		this.shipToLocationId = shipToLocationId;
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

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
}
