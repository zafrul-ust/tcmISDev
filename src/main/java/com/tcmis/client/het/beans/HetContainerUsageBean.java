package com.tcmis.client.het.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: HetContainerUsageBean <br>
 * 
 * @version: 1.0, Jun 2, 2011 <br>
 *****************************************************************************/

public class HetContainerUsageBean extends BaseDataBean {
	private static final BigDecimal	ZERO	= new BigDecimal("0.0");

	private BigDecimal				amountRemaining;
	private BigDecimal				applicationId;
	private String					catPartNo;
	private String					containerId;
	private String					containerType;
	private String					customerMsdsDb;
	private boolean					diluent	= false;
	private Date					expirationDate;
	private String					itemDesc;
	private BigDecimal				itemId;
	private BigDecimal				kitId;
	private String					location;
	private String					materialDesc;
	private BigDecimal				materialId;
	private String					mfgLot;
	private String					msdsNumber;
	private String					packaging;
	private BigDecimal				partId;
	private BigDecimal				partSize;
	private BigDecimal				receiptId;
	private BigDecimal				shipmentId;
	private String					sizeUnit;
	private boolean					solvent	= false;
	private String					unitOfMeasure;

	// constructor
	public HetContainerUsageBean() {
	}

	public HetContainerUsageBean(HetContainerInventoryViewBean inventoryView) {
		if (inventoryView != null) {
			try {
				BeanHandler.copyAttributes(inventoryView, this);
			}
			catch (Exception e) {
				e.printStackTrace(System.err);
			}
		}
	}

	public BigDecimal getAmountRemaining() {
		return amountRemaining;
	}

	public BigDecimal getApplicationId() {
		return applicationId;
	}

	public String getCatPartNo() {
		return catPartNo;
	}

	public String getContainerId() {
		return containerId;
	}

	public String getContainerType() {
		return containerType;
	}

	public String getCustomerMsdsDb() {
		return customerMsdsDb;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public BigDecimal getKitId() {
		return kitId;
	}

	public String getLocation() {
		return location;
	}

	public String getMaterialDesc() {
		return materialDesc;
	}

	public BigDecimal getMaterialId() {
		return materialId;
	}

	public String getMfgLot() {
		return mfgLot;
	}

	public String getMsdsNumber() {
		return msdsNumber;
	}

	public String getPackaging() {
		return packaging;
	}

	public BigDecimal getPartId() {
		return partId;
	}

	public BigDecimal getPartSize() {
		return partSize;
	}

	public BigDecimal getReceiptId() {
		return receiptId;
	}

	public BigDecimal getShipmentId() {
		return shipmentId;
	}

	public String getSizeUnit() {
		return sizeUnit;
	}

	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public boolean hasUnitOfMeasure() {
		return !StringHandler.isBlankString(unitOfMeasure);
	}

	public boolean isDiluent() {
		return diluent;
	}

	public boolean isEmpty() {
		return amountRemaining != null && amountRemaining.compareTo(ZERO) == 0;
	}

	public boolean isKit() {
		return kitId != null;
	}

	public boolean isSolvent() {
		return solvent;
	}

	public void setAmountRemaining(BigDecimal amountRemaining) {
		this.amountRemaining = amountRemaining;
	}

	public void setApplicationId(BigDecimal applicationId) {
		this.applicationId = applicationId;
	}

	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}

	// setters
	public void setContainerId(String containerId) {
		this.containerId = containerId;
	}

	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

	public void setCustomerMsdsDb(String customerMsdsDb) {
		this.customerMsdsDb = customerMsdsDb;
	}

	public void setDiluent(boolean diluent) {
		this.diluent = diluent;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public void setKitId(BigDecimal kitId) {
		this.kitId = kitId;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}

	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}

	public void setMfgLot(String mfgLot) {
		this.mfgLot = mfgLot;
	}

	public void setMsdsNumber(String msdsNumber) {
		this.msdsNumber = msdsNumber;
	}

	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}

	public void setPartId(BigDecimal partId) {
		this.partId = partId;
	}

	public void setPartSize(BigDecimal partSize) {
		this.partSize = partSize;
	}

	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}

	public void setShipmentId(BigDecimal shipmentId) {
		this.shipmentId = shipmentId;
	}

	public void setSizeUnit(String sizeUnit) {
		this.sizeUnit = sizeUnit;
	}

	public void setSolvent(boolean solvent) {
		this.solvent = solvent;
	}

	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

}