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
 * CLASSNAME: BatchPicklistViewBean <br>
 * @version: 1.0, Oct 5, 2007 <br>
 *****************************************************************************/


public class BatchPicklistViewBean extends BaseDataBean implements SQLData {

	private BigDecimal picklistId;
	private BigDecimal batchId;
	private BigDecimal receiptId;
	private BigDecimal itemId;
	private String inventoryGroup;
	private String bin;
	private String mfgLot;
	private String lotStatus;
	private Date expireDate;
	private BigDecimal picklistQuantity;
	private BigDecimal picklistAmount;
	private BigDecimal quantityPicked;
	private BigDecimal pickedAmount;
	private Date qcDate;
	private String itemDesc;
	private BigDecimal inventoryQuantity;
	private BigDecimal inventoryAmount;
	private String catPartNo;
	private BigDecimal recipeAmount;
	private String amountUnit;
	private String pickable;
	private String hub;
	private Date productionDate;
	private String companyId;
	private String modified;
	private String sqlType = "null";


	//constructor
	public BatchPicklistViewBean() {
	}

	//setters
	public void setPicklistId(BigDecimal picklistId) {
		this.picklistId = picklistId;
	}
	public void setBatchId(BigDecimal batchId) {
		this.batchId = batchId;
	}
	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setBin(String bin) {
		this.bin = bin;
	}
	public void setMfgLot(String mfgLot) {
		this.mfgLot = mfgLot;
	}
	public void setLotStatus(String lotStatus) {
		this.lotStatus = lotStatus;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	public void setPicklistQuantity(BigDecimal picklistQuantity) {
		this.picklistQuantity = picklistQuantity;
	}
	public void setPicklistAmount(BigDecimal picklistAmount) {
		this.picklistAmount = picklistAmount;
	}
	public void setQuantityPicked(BigDecimal quantityPicked) {
		this.quantityPicked = quantityPicked;
	}
	public void setPickedAmount(BigDecimal pickedAmount) {
		this.pickedAmount = pickedAmount;
	}
	public void setQcDate(Date qcDate) {
		this.qcDate = qcDate;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public void setInventoryQuantity(BigDecimal inventoryQuantity) {
		this.inventoryQuantity = inventoryQuantity;
	}
	public void setInventoryAmount(BigDecimal inventoryAmount) {
		this.inventoryAmount = inventoryAmount;
	}
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}
	public void setRecipeAmount(BigDecimal recipeAmount) {
		this.recipeAmount = recipeAmount;
	}
	public void setAmountUnit(String amountUnit) {
		this.amountUnit = amountUnit;
	}
	public void setPickable(String pickable) {
		this.pickable = pickable;
	}
	public void setHub(String hub) {
		this.hub = hub;
	}
	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}


	//getters
	public BigDecimal getPicklistId() {
		return picklistId;
	}
	public BigDecimal getBatchId() {
		return batchId;
	}
	public BigDecimal getReceiptId() {
		return receiptId;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public String getBin() {
		return bin;
	}
	public String getMfgLot() {
		return mfgLot;
	}
	public String getLotStatus() {
		return lotStatus;
	}
	public Date getExpireDate() {
		return expireDate;
	}
	public BigDecimal getPicklistQuantity() {
		return picklistQuantity;
	}
	public BigDecimal getPicklistAmount() {
		return picklistAmount;
	}
	public BigDecimal getQuantityPicked() {
		return quantityPicked;
	}
	public BigDecimal getPickedAmount() {
		return pickedAmount;
	}
	public Date getQcDate() {
		return qcDate;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public BigDecimal getInventoryQuantity() {
		return inventoryQuantity;
	}
	public BigDecimal getInventoryAmount() {
		return inventoryAmount;
	}
	public String getCatPartNo() {
		return catPartNo;
	}
	public BigDecimal getRecipeAmount() {
		return recipeAmount;
	}
	public String getAmountUnit() {
		return amountUnit;
	}
	public String getPickable() {
		return pickable;
	}
	public String getHub() {
		return hub;
	}
	public Date getProductionDate() {
		return productionDate;
	}
	public String getCompanyId() {
		return companyId;
	}

	public String getSQLTypeName() {
		return this.sqlType;
	}
	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try {
			this.setPicklistId(stream.readBigDecimal());
			this.setBatchId(stream.readBigDecimal());
			this.setReceiptId(stream.readBigDecimal());
			this.setItemId(stream.readBigDecimal());
			this.setInventoryGroup(stream.readString());
			this.setBin(stream.readString());
			this.setMfgLot(stream.readString());
			this.setLotStatus(stream.readString());
			this.setExpireDate(new Date(stream.readDate().getTime()));
			this.setPicklistQuantity(stream.readBigDecimal());
			this.setPicklistAmount(stream.readBigDecimal());
			this.setQuantityPicked(stream.readBigDecimal());
			this.setPickedAmount(stream.readBigDecimal());
			this.setQcDate(new Date(stream.readDate().getTime()));
			this.setItemDesc(stream.readString());
			this.setInventoryQuantity(stream.readBigDecimal());
			this.setInventoryAmount(stream.readBigDecimal());
			this.setCatPartNo(stream.readString());
			this.setRecipeAmount(stream.readBigDecimal());
			this.setAmountUnit(stream.readString());
			this.setPickable(stream.readString());
			this.setHub(stream.readString());
			this.setProductionDate(new Date(stream.readDate().getTime()));
			this.setCompanyId(stream.readString());
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
			stream.writeBigDecimal(this.getPicklistId());
			stream.writeBigDecimal(this.getBatchId());
			stream.writeBigDecimal(this.getReceiptId());
			stream.writeBigDecimal(this.getItemId());
			stream.writeString(this.getInventoryGroup());
			stream.writeString(this.getBin());
			stream.writeString(this.getMfgLot());
			stream.writeString(this.getLotStatus());
			stream.writeDate(new java.sql.Date(this.getExpireDate().getTime()));
			stream.writeBigDecimal(this.getPicklistQuantity());
			stream.writeBigDecimal(this.getPicklistAmount());
			stream.writeBigDecimal(this.getQuantityPicked());
			stream.writeBigDecimal(this.getPickedAmount());
			stream.writeDate(new java.sql.Date(this.getQcDate().getTime()));
			stream.writeString(this.getItemDesc());
			stream.writeBigDecimal(this.getInventoryQuantity());
			stream.writeBigDecimal(this.getInventoryAmount());
			stream.writeString(this.getCatPartNo());
			stream.writeBigDecimal(this.getRecipeAmount());
			stream.writeString(this.getAmountUnit());
			stream.writeString(this.getPickable());
			stream.writeString(this.getHub());
			stream.writeDate(new java.sql.Date(this.getProductionDate().getTime()));
			stream.writeString(this.getCompanyId());
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
}