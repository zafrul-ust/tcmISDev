package com.tcmis.internal.hub.beans;

import com.tcmis.common.framework.BaseDataBean;
import java.math.BigDecimal;
import java.util.Date;

public class BatchProductionInputBean extends BaseDataBean 
{
	/* fields of ProductionBatchViewBean:

	private BigDecimal 	batchId;
	private BigDecimal 	recipeId;
	private BigDecimal 	vesselId;
	private BigDecimal 	plannedYieldAmount;
	private BigDecimal 	actualYieldAmount;
	private String 		yieldAmountUnit;
	private String 		mfgLot;
	private BigDecimal 	receiptId;
	private Date 		batchStartDate;
	private Date 		lotApprovalDate;
	private String 		lotApprovalUsername;
	private Date 		batchCloseDate;
	private String 		batchCloseUsername;
	private String 		tradeName;
	private String 		materialDesc;
	private String 		recipeName;
	private BigDecimal 	itemId;
	private String 		vesselName;
	private String 		inventoryGroup;
	private String 		hub;
	private String 		hubName;
	private String 		homeCompanyId;

	private BigDecimal 	productionBatchCost;
	private BigDecimal 	yieldPercentage;
	private BigDecimal 	batchStartUser;
	private String 		batchStartUserName;
	private Date 		productionDate;

 */	
	private String submitSearch;
	private String hub;
	private String inventoryGroup;
	private String recipeId;
	private String materialDesc;	
	private String mfgLot;
	private String vesselId;
	
	private String vesselName;
	private String productionDateFrom;
	private String productionDateTo;
	private String openBatchesOnly;

	public BatchProductionInputBean()
	{		  
	}

	public String getSubmitSearch() {
		return submitSearch;
	}

	public String getHub() {
		return hub;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public String getRecipeId() {
		return recipeId;
	}

	public String getMaterialDesc() {
		return materialDesc;
	}

	public String getMfgLot() {
		return mfgLot;
	}

	public String getVesselName() {
		return vesselName;
	}

	public String getProductionDateFrom() {
		return productionDateFrom;
	}

	public String getProductionDateTo() {
		return productionDateTo;
	}

	public String getOpenBatchesOnly() {
		return openBatchesOnly;
	}

	public void setSubmitSearch(String submitSearch) {
		this.submitSearch = submitSearch;
	}

	public void setHub(String hub) {
		this.hub = hub;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public void setRecipeId(String recipeId) {
		this.recipeId = recipeId;
	}

	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}

	public void setMfgLot(String mfgLot) {
		this.mfgLot = mfgLot;
	}

	public void setVesselName(String vesselName) {
		this.vesselName = vesselName;
	}

	public void setProductionDateFrom(String productionDateFrom) {
		this.productionDateFrom = productionDateFrom;
	}

	public void setProductionDateTo(String productionDateTo) {
		this.productionDateTo = productionDateTo;
	}

	public void setOpenBatchesOnly(String openBatchesOnly) {
		this.openBatchesOnly = openBatchesOnly;
	}

	public String getVesselId() {
		return vesselId;
	}

	public void setVesselId(String vesselId) {
		this.vesselId = vesselId;
	}

}
