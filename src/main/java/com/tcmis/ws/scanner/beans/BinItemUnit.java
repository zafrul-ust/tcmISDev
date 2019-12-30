package com.tcmis.ws.scanner.beans;

import com.tcmis.common.framework.BaseDataBean;

public class BinItemUnit extends BaseDataBean {
	private boolean	defaultUnit	= false;
	private String	maxLevel;
	private String	unit;
	
	public BinItemUnit(String unit, String max, boolean defaultUnit) {
		setUnit(unit);
		setMaxLevel(max);
		setDefaultUnit(defaultUnit);
	}

	public String getMaxLevel() {
		return maxLevel;
	}

	public String getUnit() {
		return unit;
	}

	public boolean isDefaultUnit() {
		return defaultUnit;
	}

	public void setDefaultUnit(boolean defaultUnit) {
		this.defaultUnit = defaultUnit;
	}

	public void setMaxLevel(String maxLevel) {
		this.maxLevel = maxLevel;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

}
