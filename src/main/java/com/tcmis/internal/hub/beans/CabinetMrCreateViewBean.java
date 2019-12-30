package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: CabinetMrCreateViewBean <br>
 * 
 * @version: 1.0, Jul 27, 2006 <br>
 *****************************************************************************/

public class CabinetMrCreateViewBean extends BaseDataBean {

	private String		accountSysId;
	private String		autoApproveCabScanFinAppr;
	private String		autoApproveCabScanUseAppr;
	private String		billToCompanyId;
	private String		billToLocationId;
	private BigDecimal	binId;
	private BigDecimal	cabinetCount;
	private String		catalogCompanyId;
	private String		catalogId;
	private BigDecimal	catalogPrice;
	private String		catPartNo;
	private String		companyId;
	private Date		countDatetime;
	private String		currencyId;
	private BigDecimal	customerId;
	private String		customerPartNo;
	private String		defaultCurrencyId;
	private String		defaultShippingTerms;
	private BigDecimal	defCustomerServiceRepId;
	private String		distCurrencyId;
	private String		distributorOps;
	private BigDecimal	distUnitPrice;
	private String		dropShipOverride;
	private BigDecimal	expectedUnitCost;
	private BigDecimal	fieldSalesRepId;
	private String		hub;
	private String		inventoryGroup;
	private BigDecimal	itemId;
	private BigDecimal	mrQuantity;
	private BigDecimal	numBins;
	private BigDecimal	numBinsScanned;
	private BigDecimal	openMrQty;
	private String		opsCompanyId;
	private String		opsEntityId;
	private String		orderingApplication;
	private String		orderingFacility;
	private BigDecimal	partGroupNo;
	private String		paymentTerms;
	private String		priceGroupId;
	private String		relaxShelfLife;
	private BigDecimal	reorderPoint;
	private BigDecimal	shelfLifeRequired;
	private String		shipComplete;
	private String		status;
	private String		stocked;
	private BigDecimal	stockingLevel;
	private BigDecimal	unitPrice;

	// constructor
	public CabinetMrCreateViewBean() {
	}

	public String getAccountSysId() {
		return accountSysId;
	}

	public String getAutoApproveCabScanFinAppr() {
		return autoApproveCabScanFinAppr;
	}

	public String getAutoApproveCabScanUseAppr() {
		return autoApproveCabScanUseAppr;
	}

	public String getBillToCompanyId() {
		return billToCompanyId;
	}

	public String getBillToLocationId() {
		return billToLocationId;
	}

	public BigDecimal getBinId() {
		return binId;
	}

	public BigDecimal getCabinetCount() {
		return cabinetCount;
	}

	public String getCatalogCompanyId() {
		return catalogCompanyId;
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

	// getters
	public String getCompanyId() {
		return companyId;
	}

	public Date getCountDatetime() {
		return countDatetime;
	}

	public String getCurrencyId() {
		return currencyId;
	}

	public BigDecimal getCustomerId() {
		return customerId;
	}

	public String getCustomerPartNo() {
		return customerPartNo;
	}

	public String getDefaultCurrencyId() {
		return defaultCurrencyId;
	}

	public String getDefaultShippingTerms() {
		return defaultShippingTerms;
	}

	public BigDecimal getDefCustomerServiceRepId() {
		return defCustomerServiceRepId;
	}

	public String getDistCurrencyId() {
		return distCurrencyId;
	}

	public String getDistributorOps() {
		return distributorOps;
	}

	public BigDecimal getDistUnitPrice() {
		return distUnitPrice;
	}

	public String getDropShipOverride() {
		return dropShipOverride;
	}

	public BigDecimal getExpectedUnitCost() {
		return expectedUnitCost;
	}

	public BigDecimal getFieldSalesRepId() {
		return fieldSalesRepId;
	}

	public String getHub() {
		return hub;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public BigDecimal getMrQuantity() {
		return mrQuantity;
	}

	public BigDecimal getNumBins() {
		return numBins;
	}

	public BigDecimal getNumBinsScanned() {
		return numBinsScanned;
	}

	public BigDecimal getOpenMrQty() {
		return openMrQty;
	}

	public String getOpsCompanyId() {
		return opsCompanyId;
	}

	public String getOpsEntityId() {
		return opsEntityId;
	}

	public String getOrderingApplication() {
		return orderingApplication;
	}

	public String getOrderingFacility() {
		return orderingFacility;
	}

	public BigDecimal getPartGroupNo() {
		return partGroupNo;
	}

	public String getPaymentTerms() {
		return paymentTerms;
	}

	public String getPriceGroupId() {
		return priceGroupId;
	}

	public String getRelaxShelfLife() {
		return relaxShelfLife;
	}

	public BigDecimal getReorderPoint() {
		return reorderPoint;
	}

	public BigDecimal getShelfLifeRequired() {
		return shelfLifeRequired;
	}

	public String getShipComplete() {
		return shipComplete;
	}

	public String getStatus() {
		return status;
	}

	public String getStocked() {
		return stocked;
	}

	public BigDecimal getStockingLevel() {
		return stockingLevel;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	
	public boolean hasCurrencyId () {
		return StringUtils.isNotBlank(currencyId);
	}

	public boolean isAutoApproveCabScanFin() {
		return "Y".equals(getAutoApproveCabScanFinAppr());
	}

	public boolean isAutoApproveCabScanUse() {
		return "Y".equals(getAutoApproveCabScanUseAppr());
	}
	
	public void setAccountSysId(String accountSysId) {
		this.accountSysId = accountSysId;
	}

	public void setAutoApproveCabScanFinAppr(String autoApproveCabScanFinAppr) {
		this.autoApproveCabScanFinAppr = autoApproveCabScanFinAppr;
	}

	public void setAutoApproveCabScanUseAppr(String autoApproveCabScanUseAppr) {
		this.autoApproveCabScanUseAppr = autoApproveCabScanUseAppr;
	}

	public void setBillToCompanyId(String billToCompanyId) {
		this.billToCompanyId = billToCompanyId;
	}

	public void setBillToLocationId(String billToLocationId) {
		this.billToLocationId = billToLocationId;
	}

	public void setBinId(BigDecimal binId) {
		this.binId = binId;
	}

	public void setCabinetCount(BigDecimal cabinetCount) {
		this.cabinetCount = cabinetCount;
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

	// setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setCountDatetime(Date countDatetime) {
		this.countDatetime = countDatetime;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	public void setCustomerPartNo(String customerPartNo) {
		this.customerPartNo = customerPartNo;
	}

	public void setDefaultCurrencyId(String defaultCurrencyId) {
		this.defaultCurrencyId = defaultCurrencyId;
	}

	public void setDefaultShippingTerms(String defaultShippingTerms) {
		this.defaultShippingTerms = defaultShippingTerms;
	}

	public void setDefCustomerServiceRepId(BigDecimal defCustomerServiceRepId) {
		this.defCustomerServiceRepId = defCustomerServiceRepId;
	}

	public void setDistCurrencyId(String distCurrencyId) {
		this.distCurrencyId = distCurrencyId;
	}

	public void setDistributorOps(String distributorOps) {
		this.distributorOps = distributorOps;
	}

	public void setDistUnitPrice(BigDecimal distUnitPrice) {
		this.distUnitPrice = distUnitPrice;
	}

	public void setDropShipOverride(String dropShipOverride) {
		this.dropShipOverride = dropShipOverride;
	}

	public void setExpectedUnitCost(BigDecimal expectedUnitCost) {
		this.expectedUnitCost = expectedUnitCost;
	}

	public void setFieldSalesRepId(BigDecimal fieldSalesRepId) {
		this.fieldSalesRepId = fieldSalesRepId;
	}

	public void setHub(String hub) {
		this.hub = hub;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public void setMrQuantity(BigDecimal mrQuantity) {
		this.mrQuantity = mrQuantity;
	}

	public void setNumBins(BigDecimal numBins) {
		this.numBins = numBins;
	}

	public void setNumBinsScanned(BigDecimal numBinsScanned) {
		this.numBinsScanned = numBinsScanned;
	}

	public void setOpenMrQty(BigDecimal openMrQty) {
		this.openMrQty = openMrQty;
	}

	public void setOpsCompanyId(String opsCompanyId) {
		this.opsCompanyId = opsCompanyId;
	}

	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}

	public void setOrderingApplication(String orderingApplication) {
		this.orderingApplication = orderingApplication;
	}

	public void setOrderingFacility(String orderingFacility) {
		this.orderingFacility = orderingFacility;
	}

	public void setPartGroupNo(BigDecimal partGroupNo) {
		this.partGroupNo = partGroupNo;
	}

	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	public void setPriceGroupId(String priceGroupId) {
		this.priceGroupId = priceGroupId;
	}

	public void setRelaxShelfLife(String relaxShelfLife) {
		this.relaxShelfLife = relaxShelfLife;
	}

	public void setReorderPoint(BigDecimal reorderPoint) {
		this.reorderPoint = reorderPoint;
	}

	public void setShelfLifeRequired(BigDecimal shelfLifeRequired) {
		this.shelfLifeRequired = shelfLifeRequired;
	}

	public void setShipComplete(String shipComplete) {
		this.shipComplete = shipComplete;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setStocked(String stocked) {
		this.stocked = stocked;
	}

	public void setStockingLevel(BigDecimal stockingLevel) {
		this.stockingLevel = stockingLevel;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
}