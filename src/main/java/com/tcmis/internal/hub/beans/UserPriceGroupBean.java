package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import com.tcmis.common.framework.BaseDataBean;

public class UserPriceGroupBean extends BaseDataBean {
	private String		companyId;
	private String		companyName;
	private String		currencyId;
	private BigDecimal	personnelId;
	private boolean		priceGroupEditable;
	private String		priceGroupId;
	private String		priceGroupName;

	public String getCompanyId() {
		return this.companyId;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public String getCurrencyId() {
		return this.currencyId;
	}

	public BigDecimal getPersonnelId() {
		return this.personnelId;
	}

	public String getPriceGroupId() {
		return this.priceGroupId;
	}

	public String getPriceGroupName() {
		return StringUtils.isNotBlank(priceGroupName) ? this.priceGroupName : this.priceGroupId;
	}

	public boolean isPriceGroupEditable() {
		return this.priceGroupEditable;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
	}

	public void setPriceGroupEditable(boolean priceGroupEditable) {
		this.priceGroupEditable = priceGroupEditable;
	}

	public void setPriceGroupId(String priceGroupId) {
		this.priceGroupId = priceGroupId;
	}

	public void setPriceGroupName(String priceGroupName) {
		this.priceGroupName = priceGroupName;
	}
}
