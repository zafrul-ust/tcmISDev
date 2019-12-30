package com.tcmis.internal.distribution.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/**
 * OrderEntryInputBean.
 * 
 * 
 */
public class POHistoryViewBean extends BaseDataBean {

	private static final long serialVersionUID = -3410582638257885791L;

	private String action;
	private String alternateName;
	private String catalogCompanyId;
	private String catalogId;
	private String catPartNo;
	private String currencyId;
	private Date dateConfirmed;
	private Date dateCreated;
	private Date dateFirstConfirmed;
	private String inventoryGroup;
	private BigDecimal itemId;
	private BigDecimal limitDays;
	private Date needDate;
	private String opsRegionName;
	private String partDescription;
	private String partNote;
	private String partShortName;
	private BigDecimal poLine;
	private String poLineNote;
	private Date promisedDate;
	private String purchasingUnitOfMeasure;
	private BigDecimal purchasingUnitsPerItem;
	private BigDecimal quantity;
	private BigDecimal radianPo;
	private String region;
	private BigDecimal remainingShelfLifePercent;
	private String searchKey;
	private String shipToAddress;
	private String specList;
	private BigDecimal startDays;
	private String supplierName;
	private String supplierNotes;
	private BigDecimal unitPrice;
	private BigDecimal totalQuantityReceived;
	
	private Date dateLastReceived;
	
	private String supplier;

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public POHistoryViewBean() {
	}

	public String getAction() {
		return action;
	}

	/**
	 * @return the alternateName
	 */
	public String getAlternateName() {
		return alternateName;
	}

	/**
	 * @return the catalogCompanyId
	 */
	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}

	/**
	 * @return the catalogId
	 */
	public String getCatalogId() {
		return catalogId;
	}

	/**
	 * @return the catPartNo
	 */
	public String getCatPartNo() {
		return catPartNo;
	}

	/**
	 * @return the currencyId
	 */
	public String getCurrencyId() {
		return currencyId;
	}

	/**
	 * @return the dateConfirmed
	 */
	public Date getDateConfirmed() {
		return dateConfirmed;
	}

	/**
	 * @return the dateCreated
	 */
	public Date getDateCreated() {
		return dateCreated;
	}

	/**
	 * @return the dateFirstConfirmed
	 */
	public Date getDateFirstConfirmed() {
		return dateFirstConfirmed;
	}

	/**
	 * @return the inventoryGroup
	 */
	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public BigDecimal getLimitDays() {
		return limitDays;
	}

	/**
	 * @return the needDate
	 */
	public Date getNeedDate() {
		return needDate;
	}

	public String getOpsRegionName() {
		return opsRegionName;
	}

	/**
	 * @return the partDescription
	 */
	public String getPartDescription() {
		return partDescription;
	}

	/**
	 * @return the partNote
	 */
	public String getPartNote() {
		return partNote;
	}

	/**
	 * @return the partShortName
	 */
	public String getPartShortName() {
		return partShortName;
	}

	/**
	 * @return the poLine
	 */
	public BigDecimal getPoLine() {
		return poLine;
	}

	/**
	 * @return the poLineNote
	 */
	public String getPoLineNote() {
		return poLineNote;
	}

	/**
	 * @return the promisedDate
	 */
	public Date getPromisedDate() {
		return promisedDate;
	}

	/**
	 * @return the purchasingUnitOfMeasure
	 */
	public String getPurchasingUnitOfMeasure() {
		return purchasingUnitOfMeasure;
	}

	/**
	 * @return the purchasingUnitsPerItem
	 */
	public BigDecimal getPurchasingUnitsPerItem() {
		return purchasingUnitsPerItem;
	}

	/**
	 * @return the quantity
	 */
	public BigDecimal getQuantity() {
		return quantity;
	}

	/**
	 * @return the radianPo
	 */
	public BigDecimal getRadianPo() {
		return radianPo;
	}

	public String getRegion() {
		return region;
	}

	/**
	 * @return the remainingShelfLifePercent
	 */
	public BigDecimal getRemainingShelfLifePercent() {
		return remainingShelfLifePercent;
	}

	public String getSearchKey() {
		return searchKey;
	}

	public String getShipToAddress() {
		return shipToAddress;
	}

	/**
	 * @return the specList
	 */
	public String getSpecList() {
		return specList;
	}

	public BigDecimal getStartDays() {
		return startDays;
	}

	/**
	 * @return the supplierName
	 */
	public String getSupplierName() {
		return supplierName;
	}

	/**
	 * @return the supplierNotes
	 */
	public String getSupplierNotes() {
		return supplierNotes;
	}

	/**
	 * @return the unitPrice
	 */
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @param alternateName the alternateName to set
	 */
	public void setAlternateName(String alternateName) {
		this.alternateName = alternateName;
	}

	/**
	 * @param catalogCompanyId the catalogCompanyId to set
	 */
	public void setCatalogCompanyId(String catalogCompanyId) {
		this.catalogCompanyId = catalogCompanyId;
	}

	/**
	 * @param catalogId the catalogId to set
	 */
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	/**
	 * @param catPartNo the catPartNo to set
	 */
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}

	/**
	 * @param currencyId the currencyId to set
	 */
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	/**
	 * @param dateConfirmed the dateConfirmed to set
	 */
	public void setDateConfirmed(Date dateConfirmed) {
		this.dateConfirmed = dateConfirmed;
	}

	/**
	 * @param dateCreated the dateCreated to set
	 */
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	/**
	 * @param dateFirstConfirmed the dateFirstConfirmed to set
	 */
	public void setDateFirstConfirmed(Date dateFirstConfirmed) {
		this.dateFirstConfirmed = dateFirstConfirmed;
	}

	/**
	 * @param inventoryGroup the inventoryGroup to set
	 */
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public void setLimitDays(BigDecimal limitDays) {
		this.limitDays = limitDays;
	}

	/**
	 * @param needDate the needDate to set
	 */
	public void setNeedDate(Date needDate) {
		this.needDate = needDate;
	}

	public void setOpsRegionName(String opsRegionName) {
		this.opsRegionName = opsRegionName;
	}

	/**
	 * @param partDescription the partDescription to set
	 */
	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}

	/**
	 * @param partNote the partNote to set
	 */
	public void setPartNote(String partNote) {
		this.partNote = partNote;
	}

	/**
	 * @param partShortName the partShortName to set
	 */
	public void setPartShortName(String partShortName) {
		this.partShortName = partShortName;
	}

	/**
	 * @param poLine the poLine to set
	 */
	public void setPoLine(BigDecimal poLine) {
		this.poLine = poLine;
	}

	/**
	 * @param poLineNote the poLineNote to set
	 */
	public void setPoLineNote(String poLineNote) {
		this.poLineNote = poLineNote;
	}

	/**
	 * @param promisedDate the promisedDate to set
	 */
	public void setPromisedDate(Date promisedDate) {
		this.promisedDate = promisedDate;
	}

	/**
	 * @param purchasingUnitOfMeasure the purchasingUnitOfMeasure to set
	 */
	public void setPurchasingUnitOfMeasure(String purchasingUnitOfMeasure) {
		this.purchasingUnitOfMeasure = purchasingUnitOfMeasure;
	}

	/**
	 * @param purchasingUnitsPerItem the purchasingUnitsPerItem to set
	 */
	public void setPurchasingUnitsPerItem(BigDecimal purchasingUnitsPerItem) {
		this.purchasingUnitsPerItem = purchasingUnitsPerItem;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	/**
	 * @param radianPo the radianPo to set
	 */
	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 * @param remainingShelfLifePercent the remainingShelfLifePercent to set
	 */
	public void setRemainingShelfLifePercent(BigDecimal remainingShelfLifePercent) {
		this.remainingShelfLifePercent = remainingShelfLifePercent;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}

	public void setShipToAddress(String shipToAddress) {
		this.shipToAddress = shipToAddress;
	}

	/**
	 * @param specList the specList to set
	 */
	public void setSpecList(String specList) {
		this.specList = specList;
	}

	public void setStartDays(BigDecimal startDays) {
		this.startDays = startDays;
	}

	/**
	 * @param supplierName the supplierName to set
	 */
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	/**
	 * @param supplierNotes the supplierNotes to set
	 */
	public void setSupplierNotes(String supplierNotes) {
		this.supplierNotes = supplierNotes;
	}

	/**
	 * @param unitPrice the unitPrice to set
	 */
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public BigDecimal getTotalQuantityReceived() {
		return totalQuantityReceived;
	}

	public void setTotalQuantityReceived(BigDecimal totalQuantityReceived) {
		this.totalQuantityReceived = totalQuantityReceived;
	}

	public Date getDateLastReceived() {
		return dateLastReceived;
	}

	public void setDateLastReceived(Date dateLastReceived) {
		this.dateLastReceived = dateLastReceived;
	}

}