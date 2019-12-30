package com.tcmis.internal.report.beans;

import com.tcmis.common.framework.BaseDataBean;

public class CatalogOverview extends BaseDataBean {
	private String		catalogId;
	private boolean	putawayRequired;
	private boolean	useCabinets;
	private boolean	wescoOwnedCabinets;

	public String getCatalogId() {
		return catalogId;
	}

	public boolean isPutawayRequired() {
		return putawayRequired;
	}

	public boolean isUseCabinets() {
		return useCabinets;
	}

	public boolean isWescoOwnedCabinets() {
		return wescoOwnedCabinets;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public void setPutawayRequired(boolean putawayRequired) {
		this.putawayRequired = putawayRequired;
	}

	public void setUseCabinets(boolean useCabinets) {
		this.useCabinets = useCabinets;
	}

	public void setWescoOwnedCabinets(boolean wescoOwnedCabinets) {
		this.wescoOwnedCabinets = wescoOwnedCabinets;
	}
}
