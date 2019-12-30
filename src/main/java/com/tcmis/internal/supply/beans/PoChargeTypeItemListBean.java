package com.tcmis.internal.supply.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

public class PoChargeTypeItemListBean extends BaseDataBean {

	private BigDecimal poLine;
	private String chargeType;
	private String relatedPoLines;
	private Date confirmedDate;
	private String secondarySupplier;
	private String itemDesc;
	private int poLineNumber;
	
	public BigDecimal getPoLine() {
		return poLine;
	}
	public void setPoLine(BigDecimal poLine) {
		this.poLine = poLine;
	}
	public String getChargeType() {
		return chargeType;
	}
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}
	public String getRelatedPoLines() {
		return relatedPoLines;
	}
	public void setRelatedPoLines(String relatedPoLines) {
		this.relatedPoLines = relatedPoLines;
	}
	public Date getConfirmedDate() {
		return confirmedDate;
	}
	public void setConfirmedDate(Date confirmedDate) {
		this.confirmedDate = confirmedDate;
	}
	public String getSecondarySupplier() {
		return secondarySupplier;
	}
	public void setSecondarySupplier(String secondarySupplier) {
		this.secondarySupplier = secondarySupplier;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public int getPoLineNumber() {
		return poLineNumber;
	}
	public void setPoLineNumber(int poLineNumber) {
		this.poLineNumber = poLineNumber;
	}
	
	

}
