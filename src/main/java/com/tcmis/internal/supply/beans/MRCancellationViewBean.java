package com.tcmis.internal.supply.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.DateHandler;

/**
 * MRCancellationViewBean mapping to view TCM_OPS.REQUEST_CANCEL_VIEW
 */
@SuppressWarnings("serial")
public class MRCancellationViewBean extends BaseDataBean {

	private BigDecimal buyerId;
	private String csrName;
	private String customerName;
	private String deliveryType;
	private String facPartNo;
	private String hub;
	private String inventoryGroup;
	private String inventoryGroupName;
	private String itemDesc;
	private String lineItem;
	private String mrDataString;
	private String needDateString;
	private String opsEntityId;
	private BigDecimal prNumber;
	private Date promiseDate;
	private BigDecimal qtyOnBuyOrder;
	private BigDecimal qtyOnPurchaseOrder;
	private BigDecimal quantity;
	private String requestorName;
	private Date requiredDatetime;
	private BigDecimal shippedQuantity;

	public MRCancellationViewBean() {
		super();
	}

	public BigDecimal getBuyerId() {
		return this.buyerId;
	}

	public String getCsrName() {
		return this.csrName;
	}

	public String getCustomerName() {
		return this.customerName;
	}

	public String getDeliveryType() {
		return this.deliveryType;
	}

	public String getFacPartNo() {
		return this.facPartNo;
	}

	public String getHub() {
		return this.hub;
	}

	public String getInventoryGroup() {
		return this.inventoryGroup;
	}

	public String getInventoryGroupName() {
		return this.inventoryGroupName;
	}

	public String getItemDesc() {
		return this.itemDesc;
	}

	public String getLineItem() {
		return this.lineItem;
	}

	/**
	 * Returns string prNumber - lineItem
	 * 
	 * @return
	 */
	public String getMrDataString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getPrNumber()).append(" - ").append(this.getLineItem());
		return sb.toString();
	}

	/**
	 * Returns string requiredDatetime - deliveryType
	 * 
	 * @return the needDateString
	 */
	public String getNeedDateString() {
		StringBuilder sb = new StringBuilder();
		sb.append(DateHandler.formatDate(this.getRequiredDatetime(), "MM/dd/yyyy"));
		sb.append(" - ").append(this.getDeliveryType());
		return needDateString;
	}

	public String getOpsEntityId() {
		return this.opsEntityId;
	}

	public BigDecimal getPrNumber() {
		return this.prNumber;
	}

	public Date getPromiseDate() {
		return this.promiseDate;
	}

	public BigDecimal getQtyOnBuyOrder() {
		return this.qtyOnBuyOrder;
	}

	public BigDecimal getQtyOnPurchaseOrder() {
		return this.qtyOnPurchaseOrder;
	}

	public BigDecimal getQuantity() {
		return this.quantity;
	}

	public String getRequestorName() {
		return this.requestorName;
	}

	public Date getRequiredDatetime() {
		return this.requiredDatetime;
	}

	public BigDecimal getShippedQuantity() {
		return this.shippedQuantity;
	}

	public void setBuyerId(BigDecimal buyerId) {
		this.buyerId = buyerId;
	}

	public void setCsrName(String csrName) {
		this.csrName = csrName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	public void setFacPartNo(String facPartNo) {
		this.facPartNo = facPartNo;
	}

	public void setHub(String hub) {
		this.hub = hub;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public void setInventoryGroupName(String inventoryGroupName) {
		this.inventoryGroupName = inventoryGroupName;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}

	/**
	 * @param mrDataString
	 *                the mrDataString to set
	 */
	public void setMrDataString(String mrDataString) {
		this.mrDataString = mrDataString;
	}

	/**
	 * @param needDateString
	 *                the needDateString to set
	 */
	public void setNeedDateString(String needDateString) {
		this.needDateString = needDateString;
	}

	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}

	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}

	public void setPromiseDate(Date promiseDate) {
		this.promiseDate = promiseDate;
	}

	public void setQtyOnBuyOrder(BigDecimal qtyOnBuyOrder) {
		this.qtyOnBuyOrder = qtyOnBuyOrder;
	}

	public void setQtyOnPurchaseOrder(BigDecimal qtyOnPurchaseOrder) {
		this.qtyOnPurchaseOrder = qtyOnPurchaseOrder;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public void setRequestorName(String requestorName) {
		this.requestorName = requestorName;
	}

	public void setRequiredDatetime(Date requiredDatetime) {
		this.requiredDatetime = requiredDatetime;
	}

	public void setShippedQuantity(BigDecimal shippedQuantity) {
		this.shippedQuantity = shippedQuantity;
	}

}
