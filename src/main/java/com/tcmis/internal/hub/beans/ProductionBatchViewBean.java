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
 * CLASSNAME: ProductionBatchViewBean <br>
 * @version: 1.0, Oct 3, 2007 <br>
 *****************************************************************************/


public class ProductionBatchViewBean extends BaseDataBean implements SQLData {

	private BigDecimal batchId;
	private BigDecimal recipeId;
	private BigDecimal vesselId;
	private BigDecimal plannedYieldAmount;
	private BigDecimal actualYieldAmount;
	private String yieldAmountUnit;
	private BigDecimal yieldPercentage;
	private String mfgLot;
	private BigDecimal receiptId;
	private BigDecimal productionBatchCost;
	private Date batchStartDate;
	private BigDecimal batchStartUserId;
	private String batchStartUsername;
	private Date productionDate;
	private Date lotApprovalDate;
	private String lotApprovalUsername;
	private Date batchCloseDate;
	private String batchCloseUsername;
	private String tradeName;
	private String materialDesc;
	private String recipeName;
	private BigDecimal itemId;
	private String vesselName;
	private String inventoryGroup;
	private String hub;
	private String hubName;
	private String homeCompanyId;
	private BigDecimal picklistId;
	private Date qcDate;
	private String sqlType = "null";


	//constructor
	public ProductionBatchViewBean() {
	}

	//setters
	public void setBatchId(BigDecimal batchId) {
		this.batchId = batchId;
	}
	public void setRecipeId(BigDecimal recipeId) {
		this.recipeId = recipeId;
	}
	public void setVesselId(BigDecimal vesselId) {
		this.vesselId = vesselId;
	}
	public void setPlannedYieldAmount(BigDecimal plannedYieldAmount) {
		this.plannedYieldAmount = plannedYieldAmount;
	}
	public void setActualYieldAmount(BigDecimal actualYieldAmount) {
		this.actualYieldAmount = actualYieldAmount;
	}
	public void setYieldAmountUnit(String yieldAmountUnit) {
		this.yieldAmountUnit = yieldAmountUnit;
	}
	public void setYieldPercentage(BigDecimal yieldPercentage) {
		this.yieldPercentage = yieldPercentage;
	}
	public void setMfgLot(String mfgLot) {
		this.mfgLot = mfgLot;
	}
	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}
	public void setProductionBatchCost(BigDecimal productionBatchCost) {
		this.productionBatchCost = productionBatchCost;
	}
	public void setBatchStartDate(Date batchStartDate) {
		this.batchStartDate = batchStartDate;
	}
	public void setBatchStartUserId(BigDecimal batchStartUserId) {
		this.batchStartUserId = batchStartUserId;
	}
	public void setBatchStartUsername(String batchStartUsername) {
		this.batchStartUsername = batchStartUsername;
	}
	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}
	public void setLotApprovalDate(Date lotApprovalDate) {
		this.lotApprovalDate = lotApprovalDate;
	}
	public void setLotApprovalUsername(String lotApprovalUsername) {
		this.lotApprovalUsername = lotApprovalUsername;
	}
	public void setBatchCloseDate(Date batchCloseDate) {
		this.batchCloseDate = batchCloseDate;
	}
	public void setBatchCloseUsername(String batchCloseUsername) {
		this.batchCloseUsername = batchCloseUsername;
	}
	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}
	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}
	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setVesselName(String vesselName) {
		this.vesselName = vesselName;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setHub(String hub) {
		this.hub = hub;
	}
	public void setHubName(String hubName) {
		this.hubName = hubName;
	}
	public void setHomeCompanyId(String homeCompanyId) {
		this.homeCompanyId = homeCompanyId;
	}
	public void setPicklistId(BigDecimal picklistId) {
		this.picklistId = picklistId;
	}
	public void setQcDate(Date qcDate) {
		this.qcDate = qcDate;
	}


	//getters
	public BigDecimal getBatchId() {
		return batchId;
	}
	public BigDecimal getRecipeId() {
		return recipeId;
	}
	public BigDecimal getVesselId() {
		return vesselId;
	}
	public BigDecimal getPlannedYieldAmount() {
		return plannedYieldAmount;
	}
	public BigDecimal getActualYieldAmount() {
		return actualYieldAmount;
	}
	public String getYieldAmountUnit() {
		return yieldAmountUnit;
	}
	public BigDecimal getYieldPercentage() {
		return yieldPercentage;
	}
	public String getMfgLot() {
		return mfgLot;
	}
	public BigDecimal getReceiptId() {
		return receiptId;
	}
	public BigDecimal getProductionBatchCost() {
		return productionBatchCost;
	}
	public Date getBatchStartDate() {
		return batchStartDate;
	}
	public BigDecimal getBatchStartUserId() {
		return batchStartUserId;
	}
	public String getBatchStartUsername() {
		return batchStartUsername;
	}
	public Date getProductionDate() {
		return productionDate;
	}
	public Date getLotApprovalDate() {
		return lotApprovalDate;
	}
	public String getLotApprovalUsername() {
		return lotApprovalUsername;
	}
	public Date getBatchCloseDate() {
		return batchCloseDate;
	}
	public String getBatchCloseUsername() {
		return batchCloseUsername;
	}
	public String getTradeName() {
		return tradeName;
	}
	public String getMaterialDesc() {
		return materialDesc;
	}
	public String getRecipeName() {
		return recipeName;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public String getVesselName() {
		return vesselName;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public String getHub() {
		return hub;
	}
	public String getHubName() {
		return hubName;
	}
	public String getHomeCompanyId() {
		return homeCompanyId;
	}
	public BigDecimal getPicklistId() {
		return picklistId;
	}
	public Date getQcDate() {
		return qcDate;
	}

	public String getSQLTypeName() {
		return this.sqlType;
	}
	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try {
			this.setBatchId(stream.readBigDecimal());
			this.setRecipeId(stream.readBigDecimal());
			this.setVesselId(stream.readBigDecimal());
			this.setPlannedYieldAmount(stream.readBigDecimal());
			this.setActualYieldAmount(stream.readBigDecimal());
			this.setYieldAmountUnit(stream.readString());
			this.setYieldPercentage(stream.readBigDecimal());
			this.setMfgLot(stream.readString());
			this.setReceiptId(stream.readBigDecimal());
			this.setProductionBatchCost(stream.readBigDecimal());
			this.setBatchStartDate(new Date(stream.readDate().getTime()));
			this.setBatchStartUserId(stream.readBigDecimal());
			this.setBatchStartUsername(stream.readString());
			this.setProductionDate(new Date(stream.readDate().getTime()));
			this.setLotApprovalDate(new Date(stream.readDate().getTime()));
			this.setLotApprovalUsername(stream.readString());
			this.setBatchCloseDate(new Date(stream.readDate().getTime()));
			this.setBatchCloseUsername(stream.readString());
			this.setTradeName(stream.readString());
			this.setMaterialDesc(stream.readString());
			this.setRecipeName(stream.readString());
			this.setItemId(stream.readBigDecimal());
			this.setVesselName(stream.readString());
			this.setInventoryGroup(stream.readString());
			this.setHub(stream.readString());
			this.setHubName(stream.readString());
			this.setHomeCompanyId(stream.readString());
			this.setPicklistId(stream.readBigDecimal());
			this.setQcDate(new Date(stream.readDate().getTime()));
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
			stream.writeBigDecimal(this.getRecipeId());
			stream.writeBigDecimal(this.getVesselId());
			stream.writeBigDecimal(this.getPlannedYieldAmount());
			stream.writeBigDecimal(this.getActualYieldAmount());
			stream.writeString(this.getYieldAmountUnit());
			stream.writeBigDecimal(this.getYieldPercentage());
			stream.writeString(this.getMfgLot());
			stream.writeBigDecimal(this.getReceiptId());
			stream.writeBigDecimal(this.getProductionBatchCost());
			stream.writeDate(new java.sql.Date(this.getBatchStartDate().getTime()));
			stream.writeBigDecimal(this.getBatchStartUserId());
			stream.writeString(this.getBatchStartUsername());
			stream.writeDate(new java.sql.Date(this.getProductionDate().getTime()));
			stream.writeDate(new java.sql.Date(this.getLotApprovalDate().getTime()));
			stream.writeString(this.getLotApprovalUsername());
			stream.writeDate(new java.sql.Date(this.getBatchCloseDate().getTime()));
			stream.writeString(this.getBatchCloseUsername());
			stream.writeString(this.getTradeName());
			stream.writeString(this.getMaterialDesc());
			stream.writeString(this.getRecipeName());
			stream.writeBigDecimal(this.getItemId());
			stream.writeString(this.getVesselName());
			stream.writeString(this.getInventoryGroup());
			stream.writeString(this.getHub());
			stream.writeString(this.getHubName());
			stream.writeString(this.getHomeCompanyId());
			stream.writeBigDecimal(this.getPicklistId());
			stream.writeDate(new java.sql.Date(this.getQcDate().getTime()));
		}
		catch (SQLException e) {
			throw (SQLException) e;
		}
		catch (Exception e) {
			new IllegalStateException(getClass().getName() + ".writeSQL method failed").
			initCause(e);
		}
	}
}