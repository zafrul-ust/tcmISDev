package com.tcmis.client.common.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

public class VvTierIITemperatureCodeBean extends BaseDataBean {
    //constructor
	
	public VvTierIITemperatureCodeBean() {
	}
	
	private String tierIITemperature;
	private BigDecimal tierIITemperatureCode;
	
	public void setTierIITemperatureCode(BigDecimal tierIITemperatureCode) {
		this.tierIITemperatureCode = tierIITemperatureCode;
	}
	
	public BigDecimal getTierIITemperatureCode() {
		return tierIITemperatureCode;
	}
	
	public void setTierIITemperature(String tierIITemperature) {
		this.tierIITemperature = tierIITemperature;
	}
	
	public String getTierIITemperature() {
		return tierIITemperature;
	}
}
