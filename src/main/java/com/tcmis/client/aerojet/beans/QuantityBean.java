package com.tcmis.client.aerojet.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.StringHandler;

public class QuantityBean extends BaseDataBean {
	private String qualifier;
	private String value;
	private String numOfDec;    
	private String uom;
	
	public BigDecimal getDecimalValue() {
		BigDecimal bgDec = null;
		BigDecimal decimal = null;
		
		if (!StringHandler.isBlankString(this.value)) {
			bgDec = new BigDecimal(this.value);		
			if(!StringHandler.isBlankString(this.numOfDec)) {
				decimal = new BigDecimal (this.numOfDec);
			}
			if (decimal != null && decimal.intValue() > 0){
				bgDec = bgDec.divide(new BigDecimal(10).pow(decimal.intValue()));
			}
		}
		return bgDec;
	}
	
	public String getQualifier() {
		return qualifier;
	}
	public void setQualifier(String qualifier) {
		this.qualifier = qualifier;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getNumOfDec() {
		return numOfDec;
	}
	public void setNumOfDec(String numOfDec) {
		this.numOfDec = numOfDec;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

    
}
