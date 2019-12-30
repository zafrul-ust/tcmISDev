package com.tcmis.internal.supply.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

public class DBuyConsolidationFreqViewBean extends BaseDataBean {

	  private String inventoryGroup;
	  private String inventoryGroupName;
	  private String supplyPath;
	  private BigDecimal runTime;
	  private BigDecimal runDay;
	  private String enteredBy;
	  private String status;
	  private Date transactionDate;
	  private String opsEntityId;
	  private String opsCompanyId ;
	  private String okDoUpdate;
	  private String uAction;
	  private BigDecimal oldRunTime;
	  private String oldSupplyPath;
	  private String igdNeedByTolerance;
	//constructor
	  public DBuyConsolidationFreqViewBean() {
	  }
	  
		public String getIgdNeedByTolerance() {
			return igdNeedByTolerance;
		}

		public void setIgdNeedByTolerance(String igdNeedByTolerance) {
			this.igdNeedByTolerance = igdNeedByTolerance;
		}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	
	

	public String getInventoryGroupName() {
		return inventoryGroupName;
	}

	public void setInventoryGroupName(String inventoryGroupName) {
		this.inventoryGroupName = inventoryGroupName;
	}

	public String getSupplyPath() {
		return supplyPath;
	}

	public void setSupplyPath(String supplyPath) {
		this.supplyPath = supplyPath;
	}

	public BigDecimal getRunTime() {
		return runTime;
	}

	public void setRunTime(BigDecimal runTime) {
		this.runTime = runTime;
	}

	public BigDecimal getRunDay() {
		return runDay;
	}

	public void setRunDay(BigDecimal runDay) {
		this.runDay = runDay;
	}

	public String getEnteredBy() {
		return enteredBy;
	}

	public void setEnteredBy(String enteredBy) {
		this.enteredBy = enteredBy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getOpsEntityId() {
		return opsEntityId;
	}

	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}

	public String getOpsCompanyId() {
		return opsCompanyId;
	}

	public void setOpsCompanyId(String opsCompanyId) {
		this.opsCompanyId = opsCompanyId;
	}

	public String getOkDoUpdate() {
		return okDoUpdate;
	}

	public void setOkDoUpdate(String okDoUpdate) {
		this.okDoUpdate = okDoUpdate;
	}

	public String getuAction() {
		return uAction;
	}

	public void setuAction(String uAction) {
		this.uAction = uAction;
	}

	public BigDecimal getOldRunTime() {
		return oldRunTime;
	}

	public void setOldRunTime(BigDecimal oldRunTime) {
		this.oldRunTime = oldRunTime;
	}

	public String getOldSupplyPath() {
		return oldSupplyPath;
	}

	public void setOldSupplyPath(String oldSupplyPath) {
		this.oldSupplyPath = oldSupplyPath;
	}
	
	  
	  
}
