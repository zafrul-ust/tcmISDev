package com.tcmis.internal.distribution.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


public class DbuyContractAddChargeViewBean extends BaseDataBean {

	private BigDecimal addChargeItemId;
	private String itemDesc;
	private Date startDate;
	private BigDecimal dbuyContractId;
	private BigDecimal unitPrice;
	private Date updatedDate;
	private Date enteredDate;
	private BigDecimal enteredBy;
	private BigDecimal updatedBy;
	private boolean okDoUpdate;
	private boolean isNewLine;
	
	public DbuyContractAddChargeViewBean() {
	}

	public BigDecimal getAddChargeItemId() {
		return addChargeItemId;
	}

	public void setAddChargeItemId(BigDecimal addChargeItemId) {
		this.addChargeItemId = addChargeItemId;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public BigDecimal getDbuyContractId() {
		return dbuyContractId;
	}

	public void setDbuyContractId(BigDecimal dbuyContractId) {
		this.dbuyContractId = dbuyContractId;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Date getEnteredDate() {
		return enteredDate;
	}

	public void setEnteredDate(Date enteredDate) {
		this.enteredDate = enteredDate;
	}

	public BigDecimal getEnteredBy() {
		return enteredBy;
	}

	public void setEnteredBy(BigDecimal enteredBy) {
		this.enteredBy = enteredBy;
	}

	public BigDecimal getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(BigDecimal updatedBy) {
		this.updatedBy = updatedBy;
	}

	public boolean isOkDoUpdate() {
		return okDoUpdate;
	}

	public void setOkDoUpdate(boolean okDoUpdate) {
		this.okDoUpdate = okDoUpdate;
	}

	public boolean getIsNewLine() {
		return isNewLine;
	}

	public void setIsNewLine(boolean isNewLine) {
		this.isNewLine =  isNewLine;
	}
	
	
}
