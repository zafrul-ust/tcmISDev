package com.tcmis.internal.hub.beans;

import java.util.*;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.SQLException;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: RepackageBatchViewBean <br>
 * @version: 1.0, Oct 4, 2007 <br>
 *****************************************************************************/


public class RepackageBatchViewBean extends BaseDataBean implements SQLData {

	private BigDecimal batchId;
	private BigDecimal itemId;
	private BigDecimal plannedYieldAmount;
	private String yieldAmountUnit;
	private BigDecimal originalItemId;
	private String vesselName;
	private String hub;
	private String inventoryGroup;
	private BigDecimal receiptId;
	private String mfgLot;
	private Date expireDate;
	private BigDecimal quantityAvailable;
	private String itemType;
	private BigDecimal purchasingUnitsPerItem;
	private String purchasingUnitOfMeasure;
	private String itemDesc;
	private String itemPkg;
	private String myItemPkg;
	private BigDecimal capacity;
	private String capacityUnit;
	private String closeable;
	
	private BigDecimal quantityRepackaged;
	private String modified;
	private String bin;
    private Collection receiptItemPriorBinViewBeanCollection;
    private BigDecimal sizeUnit; 
	private String sqlType = "null";


	//constructor
	public RepackageBatchViewBean() {
	}

	//setters
	public void setBatchId(BigDecimal batchId) {
		this.batchId = batchId;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setPlannedYieldAmount(BigDecimal plannedYieldAmount) {
		this.plannedYieldAmount = plannedYieldAmount;
	}
	public void setYieldAmountUnit(String yieldAmountUnit) {
		this.yieldAmountUnit = yieldAmountUnit;
	}
	public void setOriginalItemId(BigDecimal originalItemId) {
		this.originalItemId = originalItemId;
	}
	public void setVesselName(String vesselName) {
		this.vesselName = vesselName;
	}
	public void setHub(String hub) {
		this.hub = hub;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}
	public void setMfgLot(String mfgLot) {
		this.mfgLot = mfgLot;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	public void setQuantityAvailable(BigDecimal quantityAvailable) {
		this.quantityAvailable = quantityAvailable;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public void setPurchasingUnitsPerItem(BigDecimal purchasingUnitsPerItem) {
		this.purchasingUnitsPerItem = purchasingUnitsPerItem;
	}
	public void setPurchasingUnitOfMeasure(String purchasingUnitOfMeasure) {
		this.purchasingUnitOfMeasure = purchasingUnitOfMeasure;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public void setItemPkg(String itemPkg) {
		this.itemPkg = itemPkg;
	}
	public void setMyItemPkg(String myItemPkg) {
		this.myItemPkg = myItemPkg;
	}
	public void setCapacity(BigDecimal capacity) {
		this.capacity = capacity;
	}
	public void setCapacityUnit(String capacityUnit) {
		this.capacityUnit = capacityUnit;
	}
	public void setCloseable(String closeable) {
		this.closeable = closeable;
	}


	//getters
	public BigDecimal getBatchId() {
		return batchId;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public BigDecimal getPlannedYieldAmount() {
		return plannedYieldAmount;
	}
	public String getYieldAmountUnit() {
		return yieldAmountUnit;
	}
	public BigDecimal getOriginalItemId() {
		return originalItemId;
	}
	public String getVesselName() {
		return vesselName;
	}
	public String getHub() {
		return hub;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public BigDecimal getReceiptId() {
		return receiptId;
	}
	public String getMfgLot() {
		return mfgLot;
	}
	public Date getExpireDate() {
		return expireDate;
	}
	public BigDecimal getQuantityAvailable() {
		return quantityAvailable;
	}
	public String getItemType() {
		return itemType;
	}
	public BigDecimal getPurchasingUnitsPerItem() {
		return purchasingUnitsPerItem;
	}
	public String getPurchasingUnitOfMeasure() {
		return purchasingUnitOfMeasure;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public String getItemPkg() {
		return itemPkg;
	}
	public String getMyItemPkg() {
		return myItemPkg;
	}
	public BigDecimal getCapacity() {
		return capacity;
	}
	public String getCapacityUnit() {
		return capacityUnit;
	}
	public String getCloseable() {
		return closeable;
	}

	public String getSQLTypeName() {
		return this.sqlType;
	}
	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try {
			this.setBatchId(stream.readBigDecimal());
			this.setItemId(stream.readBigDecimal());
			this.setPlannedYieldAmount(stream.readBigDecimal());
			this.setYieldAmountUnit(stream.readString());
			this.setOriginalItemId(stream.readBigDecimal());
			this.setVesselName(stream.readString());
			this.setHub(stream.readString());
			this.setInventoryGroup(stream.readString());
			this.setReceiptId(stream.readBigDecimal());
			this.setMfgLot(stream.readString());
			this.setExpireDate(new Date(stream.readDate().getTime()));
			this.setQuantityAvailable(stream.readBigDecimal());
			this.setItemType(stream.readString());
			this.setPurchasingUnitsPerItem(stream.readBigDecimal());
			this.setPurchasingUnitOfMeasure(stream.readString());
			this.setItemDesc(stream.readString());
			this.setItemPkg(stream.readString());
			this.setMyItemPkg(stream.readString());
			this.setCapacity(stream.readBigDecimal());
			this.setCapacityUnit(stream.readString());
			this.setCloseable(stream.readString());
		}
		catch (SQLException e) {
			throw (SQLException) e;
		}
		catch (Exception e) {
			new IllegalStateException(getClass().getName() + ".readSQL method failed").
			initCause(e);
		}
	}
	public void writeSQL(SQLOutput stream) throws SQLException {
		try {
			stream.writeBigDecimal(this.getBatchId());
			stream.writeBigDecimal(this.getItemId());
			stream.writeBigDecimal(this.getPlannedYieldAmount());
			stream.writeString(this.getYieldAmountUnit());
			stream.writeBigDecimal(this.getOriginalItemId());
			stream.writeString(this.getVesselName());
			stream.writeString(this.getHub());
			stream.writeString(this.getInventoryGroup());
			stream.writeBigDecimal(this.getReceiptId());
			stream.writeString(this.getMfgLot());
			stream.writeDate(new java.sql.Date(this.getExpireDate().getTime()));
			stream.writeBigDecimal(this.getQuantityAvailable());
			stream.writeString(this.getItemType());
			stream.writeBigDecimal(this.getPurchasingUnitsPerItem());
			stream.writeString(this.getPurchasingUnitOfMeasure());
			stream.writeString(this.getItemDesc());
			stream.writeString(this.getItemPkg());
			stream.writeString(this.getMyItemPkg());
			stream.writeBigDecimal(this.getCapacity());
			stream.writeString(this.getCapacityUnit());
			stream.writeString(this.getCloseable());
		}
		catch (SQLException e) {
			throw (SQLException) e;
		}
		catch (Exception e) {
			new IllegalStateException(getClass().getName() + ".writeSQL method failed").
			initCause(e);
		}
	}

	public String getModified() {
		return modified;
	}

	public void setModified(String modified) {
		this.modified = modified;
	}

	public BigDecimal getQuantityRepackaged() {
		return quantityRepackaged;
	}

	public void setQuantityRepackaged(BigDecimal quantityRepackaged) {
		this.quantityRepackaged = quantityRepackaged;
	}

	public String getBin() {
		return bin;
	}

	public void setBin(String bin) {
		this.bin = bin;
	}
	
	public void setReceiptItemPriorBinViewBeanCollection(Collection
		      receiptItemPriorBinViewBeanCollection) {
		    this.receiptItemPriorBinViewBeanCollection =
		        receiptItemPriorBinViewBeanCollection;
	}
	public Collection getReceiptItemPriorBinViewBeanCollection() {
		    return receiptItemPriorBinViewBeanCollection;
	}

	public BigDecimal getSizeUnit() {
		return sizeUnit;
	}

	public void setSizeUnit(BigDecimal sizeUnit) {
		this.sizeUnit = sizeUnit;
	}


}