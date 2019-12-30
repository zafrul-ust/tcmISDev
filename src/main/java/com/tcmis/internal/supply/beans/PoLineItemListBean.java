package com.tcmis.internal.supply.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

public class PoLineItemListBean extends BaseDataBean {
	private String type;
	private String id;
	private String description;
	private BigDecimal itemId;
	private int poLineNumber;
	private BigDecimal poLine;
	private BigDecimal poAddChargeLine;
	private BigDecimal amendment;
	private BigDecimal radianPo;
	private boolean selectPo;
	private BigDecimal quantity;
	private String changed;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public int getPoLineNumber() {
		return poLineNumber;
	}
	public void setPoLineNumber(int poLineNumber) {
		this.poLineNumber = poLineNumber;
	}
	public BigDecimal getPoLine() {
		return poLine;
	}
	public void setPoLine(BigDecimal poLine) {
		this.poLine = poLine;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public BigDecimal getPoAddChargeLine() {
		return poAddChargeLine;
	}
	public void setPoAddChargeLine(BigDecimal poAddChargeLine) {
		this.poAddChargeLine = poAddChargeLine;
	}
	public BigDecimal getAmendment() {
		return amendment;
	}
	public void setAmendment(BigDecimal amendment) {
		this.amendment = amendment;
	}
	public BigDecimal getRadianPo() {
		return radianPo;
	}
	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public boolean isSelectPo() {
		return selectPo;
	}
	public void setSelectPo(boolean selectPo) {
		this.selectPo = selectPo;
	}
	public boolean getSelectPo() {
		return selectPo;
	}
	public String getChanged() {
		return changed;
	}
	public void setChanged(String changed) {
		this.changed = changed;
	}
}
