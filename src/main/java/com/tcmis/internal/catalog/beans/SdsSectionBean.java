package com.tcmis.internal.catalog.beans;

import java.math.BigDecimal;

public class SdsSectionBean {

	private BigDecimal sdsSectionId;
	private String description;
	private boolean mandatory;
	private BigDecimal priority;
	
	public BigDecimal getSdsSectionId() {
		return sdsSectionId;
	}

	public void setSdsSectionId(BigDecimal sdsSectionId) {
		this.sdsSectionId = sdsSectionId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isMandatory() {
		return mandatory;
	}

	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}

	public BigDecimal getPriority() {
		return priority;
	}

	public void setPriority(BigDecimal priority) {
		this.priority = priority;
	}
}
