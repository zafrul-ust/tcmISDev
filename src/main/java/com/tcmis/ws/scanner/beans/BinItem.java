package com.tcmis.ws.scanner.beans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.tcmis.common.framework.BaseDataBean;

public class BinItem extends BaseDataBean {
	private String					applicationDesc;
	private BigDecimal				applicationId;
	private BigDecimal				binId;
	private String					binName;
	private String					companyId;
	private String					countType;
	private String					defaultSizeUnit;
	private String					includeExpiredMaterial;
	private String					itemDesc;
	private BigDecimal				itemId;
	private String					itemStatus;
	private String					itemType;
	private Date					lastModified;
	private String					maxItemAmountPerSizeUnit;
	private BigDecimal				maxLevel;
	private BigDecimal				pullWithinDaysToExpiration;
	private String					sizeUnit;
	private String					status;
	private Collection<BinItemUnit>	units	= Collections.EMPTY_SET;

	public BinItem() {
		super();
	}

	public String getApplicationDesc() {
		return applicationDesc;
	}

	public BigDecimal getApplicationId() {
		return applicationId;
	}

	public BigDecimal getBinId() {
		return binId;
	}

	public String getBinName() {
		return binName;
	}

	public String getCompanyId() {
		return companyId;
	}

	public String getCountType() {
		return countType;
	}

	public String getDefaultSizeUnit() {
		return defaultSizeUnit;
	}

	public String getIncludeExpiredMaterial() {
		return includeExpiredMaterial;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public String getItemType() {
		return itemType;
	}

	public Date getLastModified() {
		return this.lastModified;
	}

	public String getMaxItemAmountPerSizeUnit() {
		return maxItemAmountPerSizeUnit;
	}

	public BigDecimal getMaxLevel() {
		return maxLevel;
	}

	public BigDecimal getPullWithinDaysToExpiration() {
		return pullWithinDaysToExpiration;
	}

	public String getSizeUnit() {
		return sizeUnit;
	}

	public String getStatus() {
		return status;
	}

	public Collection<BinItemUnit> getUnits() {
		if (StringUtils.isNotBlank(sizeUnit) && units.isEmpty()) {
			String[] sizeUnits = sizeUnit.split("\\|");
			String[] maxSize;
			if (StringUtils.isBlank(maxItemAmountPerSizeUnit)) {
				maxSize = new String[0];
			}
			else {
				maxSize = maxItemAmountPerSizeUnit.split("\\|");
			}
			units = new ArrayList<BinItemUnit>(sizeUnits.length);
			for (int i = 0; i < sizeUnits.length; i++) {
				units.add(new BinItemUnit(sizeUnits[i], maxSize.length > i ? maxSize[i] : "", sizeUnits[i].equals(defaultSizeUnit)));
			}

		}
		return units;
	}

	public void setApplicationDesc(String applicationDesc) {
		this.applicationDesc = applicationDesc;
	}

	public void setApplicationId(BigDecimal applicationId) {
		this.applicationId = applicationId;
	}

	public void setBinId(BigDecimal binId) {
		this.binId = binId;
	}

	public void setBinName(String binName) {
		this.binName = binName;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setCountType(String countType) {
		this.countType = countType;
	}

	public void setDefaultSizeUnit(String defaultSizeUnit) {
		this.defaultSizeUnit = defaultSizeUnit;
	}

	public void setIncludeExpiredMaterial(String includeExpiredMaterial) {
		this.includeExpiredMaterial = includeExpiredMaterial;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public void setMaxItemAmountPerSizeUnit(String maxItemAmountPerSizeUnit) {
		this.maxItemAmountPerSizeUnit = maxItemAmountPerSizeUnit;
	}

	public void setMaxLevel(BigDecimal maxLevel) {
		this.maxLevel = maxLevel;
	}

	public void setPullWithinDaysToExpiration(BigDecimal pullWithinDaysToExpiration) {
		this.pullWithinDaysToExpiration = pullWithinDaysToExpiration;
	}

	public void setSizeUnit(String sizeUnit) {
		this.sizeUnit = sizeUnit;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setUnits(Collection<BinItemUnit> units) {
		this.units = units;
	}

	public String getItemStatus() {
		return this.itemStatus;
	}

	public void setItemStatus(String itemStatus) {
		this.itemStatus = itemStatus;
	}
}
