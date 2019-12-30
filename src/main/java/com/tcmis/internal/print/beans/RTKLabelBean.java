package com.tcmis.internal.print.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

public class RTKLabelBean extends BaseDataBean {
	private BigDecimal materialId;
	private String materialDesc;
	private String casNumber;
	private String ingredient;
	
	public BigDecimal getMaterialId() {
		return materialId;
	}
	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}
	public String getMaterialDesc() {
		return materialDesc;
	}
	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}
	public String getCasNumber() {
		return casNumber;
	}
	public void setCasNumber(String casNumber) {
		this.casNumber = casNumber;
	}
	public String getIngredient() {
		return ingredient;
	}
	public void setIngredient(String ingredient) {
		this.ingredient = ingredient;
	}
}
