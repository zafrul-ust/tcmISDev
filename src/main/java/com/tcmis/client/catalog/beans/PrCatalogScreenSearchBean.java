package com.tcmis.client.catalog.beans;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: PrCatalogScreenSearchViewBean <br>
 * 
 * @version: 1.0, May 31, 2005 <br>
 *****************************************************************************/

public class PrCatalogScreenSearchBean extends BaseDataBean {

	private String		applicationId;
	private String		approvalStatus;
	private BigDecimal	availableQtyForFee;
	private String		bundle;
	private String		catalogCompanyId;
	private String		catalogDesc;
	private String		catalogId;
	private BigDecimal	catalogPrice;
	private String		catPartNo;
	private String		comments;
	private String		companyId;
	private String		componentDesc;
	private String		componentMsdsNumber;
	private String		componentPackaging;
	private BigDecimal	componentsPerItem;
	private String		currencyId;
	private String		customerTemperatureId;
	private String		customerPartRevision;
	private BigDecimal	daysPeriod1;
	private BigDecimal	daysPeriod2;
	private String		dimension;
	private BigDecimal	displayOrder;
	private String		displayTemp;
	private String		facilityId;
	private String		grade;
	private String		hetUsageRecording;
	private BigDecimal	highTemp;
	private String		incomingTesting;
	private String		inventoryGroup;
	private String		inventoryGroupName;
	private Collection	itemCollection;
	private BigDecimal	itemId;
	private String		itemType;
	private String		kitMsdsNumber;
	private BigDecimal	limitQuantityPeriod1;
	private BigDecimal	limitQuantityPeriod2;
	private BigDecimal	lowTemp;
	private String		manufacturerName;
	private String		materialCategoryName;
	private String		materialDesc;
	private BigDecimal	materialId;
	private String		materialSubcategoryName;
	private BigDecimal	maxCatalogPrice;
	private Date		maxPriceEndDate;
	private Date		maxPriceStartDate;
	private BigDecimal	maxUnitPrice;
	private BigDecimal	medianLeadTime;
	private BigDecimal	medianMrLeadTime;
	private BigDecimal	medianSupplyLeadTime;
	private String		mfgDesc;
	private String		mfgPartNo;
	private BigDecimal	minBuy;
	private BigDecimal	minCatalogPrice;

	private Date		minPriceEndDate;
	private Date		minPriceStartDate;
	private BigDecimal	minUnitPrice;
	private String		msdsOnLine;

	private BigDecimal	orderQuantity;
	private String		orderQuantityRule;
	private String		packaging;
	private String		partDescription;
	private String		partGroup;
	private BigDecimal	partGroupNo;
	private BigDecimal	partId;
	private String		partItemGroup;
	private String		partStatus;
	private BigDecimal	projectedLeadTime;
	private String		qtyOfUomPerItem;
	private BigDecimal	quantityPerBundle;
	private BigDecimal	roomTempOutTime;
	private BigDecimal	rowSpan;
	private String		serviceFeeRow	= "N";
	private String		shelfLife;
	private String		shelfLifeBasis;
	private BigDecimal	shelfLifeDays;
	private String		shelfLifeList;
	private String		sizeVaries;
	private String		sourceHub;
	private String		specList;
	private String		specs;
	private String		stockingMethod;
	private String		storageTemp;
	private String		tempUnit;
	private String		unitConversionOption;
	private String		unitOfSale;
	private BigDecimal	unitOfSaleQuantityPerEach;
	
	private String		qualityIdLabel;
	private String		qualityId;
	private String		catPartAttributeHeader;
	private String		catPartAttribute;
	private String		prop65Warning;
	private String		source;
	private BigDecimal  pricePerUnitVolume;
	private String      pricePerUnitVolumeUnit;
	private String 		image_content;
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	public String getQualityIdLabel() {
		return qualityIdLabel;
	}

	public void setQualityIdLabel(String qualityIdLabel) {
		this.qualityIdLabel = qualityIdLabel;
	}

	public String getQualityId() {
		return qualityId;
	}

	public void setQualityId(String qualityId) {
		this.qualityId = qualityId;
	}

	public String getCatPartAttributeHeader() {
		return catPartAttributeHeader;
	}

	public void setCatPartAttributeHeader(String catPartAttributeHeader) {
		this.catPartAttributeHeader = catPartAttributeHeader;
	}

	public String getCatPartAttribute() {
		return catPartAttribute;
	}

	public void setCatPartAttribute(String catPartAttribute) {
		this.catPartAttribute = catPartAttribute;
	}

	
	// constructor
	public PrCatalogScreenSearchBean() {
	}

	public String getApplicationId() {
		return applicationId;
	}

	public String getApprovalStatus() {
		return approvalStatus;
	}

	public BigDecimal getAvailableQtyForFee() {
		return availableQtyForFee;
	}

	public String getBundle() {
		return bundle;
	}

	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}

	public String getCatalogDesc() {
		return catalogDesc;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public BigDecimal getCatalogPrice() {
		return catalogPrice;
	}

	public String getCatPartNo() {
		return catPartNo;
	}

	public String getComments() {
		return comments;
	}

	public String getCompanyId() {
		return companyId;
	}

	public String getComponentDesc() {
		return componentDesc;
	}

	public String getComponentMsdsNumber() {
		return componentMsdsNumber;
	}

	public String getComponentPackaging() {
		return componentPackaging;
	}

	public BigDecimal getComponentsPerItem() {
		return componentsPerItem;
	}

	public String getCurrencyId() {
		return currencyId;
	}

	public String getCustomerTemperatureId() {
		return customerTemperatureId;
	}

	public BigDecimal getDaysPeriod1() {
		return daysPeriod1;
	}

	public BigDecimal getDaysPeriod2() {
		return daysPeriod2;
	}

	public String getDimension() {
		return dimension;
	}

	public BigDecimal getDisplayOrder() {
		return displayOrder;
	}

	public String getDisplayTemp() {
		return displayTemp;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public String getGrade() {
		return grade;
	}

	public String getHetUsageRecording() {
		return hetUsageRecording;
	}

	public BigDecimal getHighTemp() {
		return highTemp;
	}

	public String getIncomingTesting() {
		return incomingTesting;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public String getInventoryGroupName() {
		return inventoryGroupName;
	}

	public Collection getItemCollection() {
		return itemCollection;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public String getItemType() {
		return itemType;
	}

	public String getKitMsdsNumber() {
		return kitMsdsNumber;
	}

	public BigDecimal getLimitQuantityPeriod1() {
		return limitQuantityPeriod1;
	}

	public BigDecimal getLimitQuantityPeriod2() {
		return limitQuantityPeriod2;
	}

	public BigDecimal getLowTemp() {
		return lowTemp;
	}

	public String getManufacturerName() {
		return manufacturerName;
	}

	public String getMaterialCategoryName() {
		return materialCategoryName;
	}

	public String getMaterialDesc() {
		return materialDesc;
	}

	public BigDecimal getMaterialId() {
		return materialId;
	}

	public String getMaterialSubcategoryName() {
		return materialSubcategoryName;
	}

	public BigDecimal getMaxCatalogPrice() {
		return maxCatalogPrice;
	}

	public Date getMaxPriceEndDate() {
		return maxPriceEndDate;
	}

	public Date getMaxPriceStartDate() {
		return maxPriceStartDate;
	}

	public BigDecimal getMaxUnitPrice() {
		return maxUnitPrice;
	}

	public BigDecimal getMedianLeadTime() {
		return medianLeadTime;
	}

	public BigDecimal getMedianMrLeadTime() {
		return medianMrLeadTime;
	}

	public BigDecimal getMedianSupplyLeadTime() {
		return medianSupplyLeadTime;
	}

	public String getMfgDesc() {
		return mfgDesc;
	}

	public String getMfgPartNo() {
		return mfgPartNo;
	}

	public BigDecimal getMinBuy() {
		return minBuy;
	}

	public BigDecimal getMinCatalogPrice() {
		return minCatalogPrice;
	}

	public Date getMinPriceEndDate() {
		return minPriceEndDate;
	}

	public Date getMinPriceStartDate() {
		return minPriceStartDate;
	}

	public BigDecimal getMinUnitPrice() {
		return minUnitPrice;
	}

	public String getMsdsOnLine() {
		return msdsOnLine;
	}

	public BigDecimal getOrderQuantity() {
		return orderQuantity;
	}

	public String getOrderQuantityRule() {
		return orderQuantityRule;
	}

	public String getPackaging() {
		return packaging;
	}

	public String getPartDescription() {
		return partDescription;
	}

	public String getPartGroup() {
		return partGroup;
	}

	public BigDecimal getPartGroupNo() {
		return partGroupNo;
	}

	public BigDecimal getPartId() {
		return partId;
	}

	public String getPartItemGroup() {
		return partItemGroup;
	}

	public String getPartStatus() {
		return partStatus;
	}

	public BigDecimal getProjectedLeadTime() {
		return projectedLeadTime;
	}

	public String getQtyOfUomPerItem() {
		return qtyOfUomPerItem;
	}

	public BigDecimal getQuantityPerBundle() {
		return quantityPerBundle;
	}

	public BigDecimal getRoomTempOutTime() {
		return roomTempOutTime;
	}

	public BigDecimal getRowSpan() {
		return rowSpan;
	}

	public String getServiceFeeRow() {
		return serviceFeeRow;
	}

	public String getShelfLife() {
		return shelfLife;
	}

	public String getShelfLifeBasis() {
		return shelfLifeBasis;
	}

	public BigDecimal getShelfLifeDays() {
		return shelfLifeDays;
	}

	public String getSizeVaries() {
		return sizeVaries;
	}

	public String getSourceHub() {
		return sourceHub;
	}

	public String getSpecList() {
		return specList;
	}

	public String getSpecs() {
		return specs;
	}

	public String getStockingMethod() {
		return stockingMethod;
	}

	public String getStorageTemp() {
		return storageTemp;
	}

	public String getTempUnit() {
		return tempUnit;
	}

	public String getUnitConversionOption() {
		return unitConversionOption;
	}

	public String getUnitOfSale() {
		return unitOfSale;
	}

	public BigDecimal getUnitOfSaleQuantityPerEach() {
		return unitOfSaleQuantityPerEach;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public void setAvailableQtyForFee(BigDecimal availableQtyForFee) {
		this.availableQtyForFee = availableQtyForFee;
	}

	public void setBundle(String bundle) {
		this.bundle = bundle;
	}

	public void setCatalogCompanyId(String catalogCompanyId) {
		this.catalogCompanyId = catalogCompanyId;
	}

	public void setCatalogDesc(String catalogDesc) {
		this.catalogDesc = catalogDesc;
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

	public void setComments(String comments) {
		this.comments = comments;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setComponentDesc(String componentDesc) {
		this.componentDesc = componentDesc;
	}

	// setters
	public void setComponentMsdsNumber(String componentMsdsNumber) {
		this.componentMsdsNumber = componentMsdsNumber;
	}

	public void setComponentPackaging(String componentPackaging) {
		this.componentPackaging = componentPackaging;
	}

	public void setComponentsPerItem(BigDecimal componentsPerItem) {
		this.componentsPerItem = componentsPerItem;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	public void setCustomerTemperatureId(String customerTemperatureId) {
		this.customerTemperatureId = customerTemperatureId;
	}

	public void setDaysPeriod1(BigDecimal daysPeriod1) {
		this.daysPeriod1 = daysPeriod1;
	}

	public void setDaysPeriod2(BigDecimal daysPeriod2) {
		this.daysPeriod2 = daysPeriod2;
	}

	public void setDimension(String dimension) {
		this.dimension = dimension;
	}

	public void setDisplayOrder(BigDecimal displayOrder) {
		this.displayOrder = displayOrder;
	}

	public void setDisplayTemp(String displayTemp) {
		this.displayTemp = displayTemp;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public void setHetUsageRecording(String hetUsageRecording) {
		this.hetUsageRecording = hetUsageRecording;
	}

	public void setHighTemp(BigDecimal highTemp) {
		this.highTemp = highTemp;
	}

	public void setIncomingTesting(String incomingTesting) {
		this.incomingTesting = incomingTesting;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public void setInventoryGroupName(String inventoryGroupName) {
		this.inventoryGroupName = inventoryGroupName;
	}

	public void setItemCollection(Collection itemCollection) {
		this.itemCollection = itemCollection;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public void setKitMsdsNumber(String kitMsdsNumber) {
		this.kitMsdsNumber = kitMsdsNumber;
	}

	public void setLimitQuantityPeriod1(BigDecimal limitQuantityPeriod1) {
		this.limitQuantityPeriod1 = limitQuantityPeriod1;
	}

	public void setLimitQuantityPeriod2(BigDecimal limitQuantityPeriod2) {
		this.limitQuantityPeriod2 = limitQuantityPeriod2;
	}

	public void setLowTemp(BigDecimal lowTemp) {
		this.lowTemp = lowTemp;
	}

	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}

	public void setMaterialCategoryName(String materialCategoryName) {
		this.materialCategoryName = materialCategoryName;
	}

	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}

	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}

	public void setMaterialSubcategoryName(String materialSubcategoryName) {
		this.materialSubcategoryName = materialSubcategoryName;
	}

	public void setMaxCatalogPrice(BigDecimal maxCatalogPrice) {
		this.maxCatalogPrice = maxCatalogPrice;
	}

	public void setMaxPriceEndDate(Date maxPriceEndDate) {
		this.maxPriceEndDate = maxPriceEndDate;
	}

	public void setMaxPriceStartDate(Date maxPriceStartDate) {
		this.maxPriceStartDate = maxPriceStartDate;
	}

	public void setMaxUnitPrice(BigDecimal maxUnitPrice) {
		this.maxUnitPrice = maxUnitPrice;
	}

	public void setMedianLeadTime(BigDecimal medianLeadTime) {
		this.medianLeadTime = medianLeadTime;
	}

	public void setMedianMrLeadTime(BigDecimal medianMrLeadTime) {
		this.medianMrLeadTime = medianMrLeadTime;
	}

	public void setMedianSupplyLeadTime(BigDecimal medianSupplyLeadTime) {
		this.medianSupplyLeadTime = medianSupplyLeadTime;
	}

	public void setMfgDesc(String mfgDesc) {
		this.mfgDesc = mfgDesc;
	}

	public void setMfgPartNo(String mfgPartNo) {
		this.mfgPartNo = mfgPartNo;
	}

	public void setMinBuy(BigDecimal minBuy) {
		this.minBuy = minBuy;
	}

	public void setMinCatalogPrice(BigDecimal minCatalogPrice) {
		this.minCatalogPrice = minCatalogPrice;
	}

	public void setMinPriceEndDate(Date minPriceEndDate) {
		this.minPriceEndDate = minPriceEndDate;
	}

	public void setMinPriceStartDate(Date minPriceStartDate) {
		this.minPriceStartDate = minPriceStartDate;
	}

	public void setMinUnitPrice(BigDecimal minUnitPrice) {
		this.minUnitPrice = minUnitPrice;
	}

	public void setMsdsOnLine(String msdsOnLine) {
		this.msdsOnLine = msdsOnLine;
	}

	public void setOrderQuantity(BigDecimal orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	public void setOrderQuantityRule(String orderQuantityRule) {
		this.orderQuantityRule = orderQuantityRule;
	}

	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}

	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}

	public void setPartGroup(String partGroup) {
		this.partGroup = partGroup;
	}

	public void setPartGroupNo(BigDecimal partGroupNo) {
		this.partGroupNo = partGroupNo;
	}

	public void setPartId(BigDecimal partId) {
		this.partId = partId;
	}

	public void setPartItemGroup(String partItemGroup) {
		this.partItemGroup = partItemGroup;
	}

	public void setPartStatus(String partStatus) {
		this.partStatus = partStatus;
	}

	// getters

	public void setProjectedLeadTime(BigDecimal projectedLeadTime) {
		this.projectedLeadTime = projectedLeadTime;
	}

	public void setQtyOfUomPerItem(String qtyOfUomPerItem) {
		this.qtyOfUomPerItem = com.tcmis.common.util.StringHandler.replace(qtyOfUomPerItem, "\n", "<br>");
	}

	public void setQuantityPerBundle(BigDecimal quantityPerBundle) {
		this.quantityPerBundle = quantityPerBundle;
	}

	public void setRoomTempOutTime(BigDecimal roomTempOutTime) {
		this.roomTempOutTime = roomTempOutTime;
	}

	public void setRowSpan(BigDecimal rowSpan) {
		this.rowSpan = rowSpan;
	}

	public void setServiceFeeRow(String serviceFeeRow) {
		this.serviceFeeRow = serviceFeeRow;
	}

	public void setShelfLife(String shelfLife) {
		this.shelfLife = shelfLife;
	}

	public void setShelfLifeBasis(String shelfLifeBasis) {
		this.shelfLifeBasis = shelfLifeBasis;
	}

	public void setShelfLifeDays(BigDecimal shelfLifeDays) {
		this.shelfLifeDays = shelfLifeDays;
	}

	public void setSizeVaries(String sizeVaries) {
		this.sizeVaries = sizeVaries;
	}

	public void setSourceHub(String sourceHub) {
		this.sourceHub = sourceHub;
	}

	public void setSpecList(String specList) {
		this.specList = specList;
	}

	public void setSpecs(String specs) {
		this.specs = specs;
	}

	public void setStockingMethod(String stockingMethod) {
		this.stockingMethod = stockingMethod;
	}

	public void setStorageTemp(String storageTemp) {
		this.storageTemp = storageTemp;
	}

	public void setTempUnit(String tempUnit) {
		this.tempUnit = tempUnit;
	}

	public void setUnitConversionOption(String unitConversionOption) {
		this.unitConversionOption = unitConversionOption;
	}

	public void setUnitOfSale(String unitOfSale) {
		this.unitOfSale = unitOfSale;
	}

	public void setUnitOfSaleQuantityPerEach(BigDecimal unitOfSaleQuantityPerEach) {
		this.unitOfSaleQuantityPerEach = unitOfSaleQuantityPerEach;
	}

	public String getCustomerPartRevision() {
		return customerPartRevision;
	}

	public void setCustomerPartRevision(String customerPartRevision) {
		this.customerPartRevision = customerPartRevision;
	}

	public String getProp65Warning() {
		return prop65Warning;
	}

	public void setProp65Warning(String prop65Warning) {
		this.prop65Warning = prop65Warning;
	}

	public String getShelfLifeList() {
		return shelfLifeList;
	}

	public void setShelfLifeList(String shelfLifeList) {
		this.shelfLifeList = shelfLifeList;
	}

	public BigDecimal getPricePerUnitVolume() {
		return pricePerUnitVolume;
	}

	public void setPricePerUnitVolume(BigDecimal pricePerUnitVolume) {
		this.pricePerUnitVolume = pricePerUnitVolume;
	}

	public String getPricePerUnitVolumeUnit() {
		return pricePerUnitVolumeUnit;
	}

	public void setPricePerUnitVolumeUnit(String pricePerUnitVolumeUnit) {
		this.pricePerUnitVolumeUnit = pricePerUnitVolumeUnit;
  }
  
	public String getImage_content() {
		return image_content;
	}

	public void setImage_content(String image_content) {
		this.image_content = image_content;
	}
} // end of class