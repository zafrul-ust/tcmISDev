package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

public class BoxLabelBean extends BaseDataBean {

	private BigDecimal boxLabelId;
	private String serialNumber;
	
	public BoxLabelBean() {}
	
	public BigDecimal getBoxLabelId() {
		return this.boxLabelId;
	}
	
	public String getSerialNumber() {
		return this.serialNumber;
	}
	
	public void setBoxLabelId(BigDecimal boxLabelId) {
		this.boxLabelId = boxLabelId;
	}
	
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
}
