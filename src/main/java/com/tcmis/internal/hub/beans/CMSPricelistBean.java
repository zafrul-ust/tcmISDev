package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.DateHandler;

public class CMSPricelistBean extends BaseDataBean {

	private BigDecimal	baselineAnnualUsage;
	private BigDecimal	baselinePrice;
	private String		catalogCompanyId;
	private String		catalogId;
	private BigDecimal	catalogPrice;
	private String		catPartNo;
	private String		companyId;
	private String		currencyId;
	private Date		endDate;
	private Date		insertDate;
	private BigDecimal	insertedBy;
	private String		insertedName;
	private String		itemDesc;
	private String		itemId;
	private Date		lastUpdated;
	private BigDecimal	lastUpdatedBy;
	private String		lastUpdatedName;
	private String		loadingComments;
	private BigDecimal	originalCatalogPrice;
	private String		originalCurrencyId;
	private Date		originalEndDate;
	private Date		originalStartDate;
	private BigDecimal	partGroupNo;
	private String		priceGroupId;
	private Date		startDate;

	public BigDecimal getBaselineAnnualUsage() {
		return this.baselineAnnualUsage;
	}

	public BigDecimal getBaselinePrice() {
		return this.baselinePrice;
	}

	public String getCatalogCompanyId() {
		return this.catalogCompanyId;
	}

	public String getCatalogId() {
		return this.catalogId;
	}

	public BigDecimal getCatalogPrice() {
		return this.catalogPrice;
	}

	public String getCatPartNo() {
		return this.catPartNo;
	}

	public String getCompanyId() {
		return this.companyId;
	}

	public String getCurrencyId() {
		return this.currencyId;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public Date getInsertDate() {
		return this.insertDate;
	}

	public BigDecimal getInsertedBy() {
		return this.insertedBy;
	}

	public String getInsertedName() {
		return this.insertedName;
	}

	public String getItemDesc() {
		return this.itemDesc;
	}

	public String getItemId() {
		return this.itemId;
	}

	public Date getLastUpdated() {
		return this.lastUpdated;
	}

	public BigDecimal getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}

	public String getLastUpdatedName() {
		return this.lastUpdatedName;
	}

	public String getLoadingComments() {
		return this.loadingComments;
	}

	public BigDecimal getOriginalCatalogPrice() {
		return this.originalCatalogPrice;
	}

	public String getOriginalCurrencyId() {
		return this.originalCurrencyId;
	}

	public Date getOriginalEndDate() {
		return this.originalEndDate;
	}

	public Date getOriginalStartDate() {
		return this.originalStartDate;
	}

	public BigDecimal getPartGroupNo() {
		return this.partGroupNo;
	}

	public String getPriceGroupId() {
		return this.priceGroupId;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public boolean hasLoadingComments() {
		return StringUtils.isNotBlank(loadingComments);
	}

	public boolean isCatalogPriceChanged() {
		return originalCatalogPrice == null || originalCatalogPrice.compareTo(catalogPrice) != 0;
	}

	public boolean isCurrencyIdChanged() {
		return !originalCurrencyId.equals(currencyId);
	}

	public boolean isEndDateChanged() {
		return !DateHandler.isSameDay(endDate, originalEndDate);
	}

	public boolean isExpired() {
		return DateHandler.isEarlierDay(endDate, new Date());
	}
	
	public boolean isNewPart() {
		return originalStartDate == null && originalCatalogPrice == null;
	}

	public boolean isPriceFromToday() {
		return DateHandler.isSameDay(startDate, new Date())  && startDate.equals(originalStartDate);
	}

	public boolean isUpdate() {
		return isEndDateChanged() || isCurrencyIdChanged() || isCatalogPriceChanged();
	}

	public boolean isUpdateEndDateOnly() {
		return isEndDateChanged() && !isCurrencyIdChanged() && !isCatalogPriceChanged();
	}

	public void setBaselineAnnualUsage(BigDecimal baselineAnnualUsage) {
		this.baselineAnnualUsage = baselineAnnualUsage;
	}

	public void setBaselinePrice(BigDecimal baselinePrice) {
		this.baselinePrice = baselinePrice;
	}

	public void setCatalogCompanyId(String catalogCompanyId) {
		this.catalogCompanyId = catalogCompanyId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public void setCatalogPrice(BigDecimal catalogPrice) {
		this.catalogPrice = catalogPrice;
	}

	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public void setInsertedBy(BigDecimal insertedBy) {
		this.insertedBy = insertedBy;
	}

	public void setInsertedName(String insertedName) {
		this.insertedName = insertedName;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public void setLastUpdatedBy(BigDecimal lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public void setLastUpdatedName(String lastUpdatedName) {
		this.lastUpdatedName = lastUpdatedName;
	}

	public void setLoadingComments(String loadingComments) {
		this.loadingComments = loadingComments;
	}

	public void setOriginalCatalogPrice(BigDecimal originalCatalogPrice) {
		this.originalCatalogPrice = originalCatalogPrice;
	}

	public void setOriginalCurrencyId(String originalCurrencyId) {
		this.originalCurrencyId = originalCurrencyId;
	}

	public void setOriginalEndDate(Date originalEndDate) {
		this.originalEndDate = originalEndDate;
	}

	public void setOriginalStartDate(Date originalStartDate) {
		this.originalStartDate = originalStartDate;
	}

	public void setPartGroupNo(BigDecimal partGroupNo) {
		this.partGroupNo = partGroupNo;
	}

	public void setPriceGroupId(String priceGroupId) {
		this.priceGroupId = priceGroupId;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

}
