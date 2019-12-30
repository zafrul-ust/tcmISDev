package com.tcmis.internal.supply.beans;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;

import com.tcmis.common.exceptions.BeanCopyException;
import com.tcmis.common.framework.BaseInputBean;
import com.tcmis.common.util.BeanHandler;

/******************************************************************************
 * CLASSNAME: AssociatePrViewBean <br>
 * 
 * @version: 1.0, Mar 10, 2006 <br>
 *****************************************************************************/

public class BuyOrdersInputBean extends BaseInputBean {

	private BigDecimal	bestPriceDbuyContractId;
	private String		boForConfirmedPo;
	private String		boForUnconfirmedPo;
	private String		boWithNoPo;
	private String		branchPlant;
	private BigDecimal	buyerId;
	private String		buyerName;
	private String		cancel;
	private String		comments;
	private String		companyId;
	private String[]	companyIdArray;
	private String		companyName;
	private String		confirmedButNoAssociation;
	private String		critical;
	private String		customerPoNumber;
	private BigDecimal	defaultBuyerId;
	private BigDecimal	docNum;
	private boolean		excludeMxItems;
	private String		facilityId;
	private String		facilityName;
	private String		homeCurrencyId;
	private String		hub;
	private String		hubName;
	private String		inventoryGroup;
	private String		inventoryGroupName;
	private BigDecimal	itemId;
	private String		lastSupplier;
	private String		lastSupplierName;
	private String		locale;
	private BigDecimal	mrLineItem;
	private BigDecimal	mrNumber;
	private Date		needDate;
	private String		ok;
	private BigDecimal	oldBuyerId;
	private String		oldComments;
	private String		oldPoSupplierName;
	private String		oldStatus;
	private String		opsEntityId;
	private BigDecimal	orderQuantity;
	private String		poSupplierId;
	private String		poSupplierName;
	private BigDecimal	prNumber;
	private Date		promisedDate;
	private BigDecimal	radianPo;
	private BigDecimal	reasonId;
	private BigDecimal	rowNumber;
	private String		searchText;
	private String		searchType;
	private String		searchTypeDesc;
	private String		searchWhat;
	private String		searchWhatDesc;
	private String		selectedSupplierForPo;
	private String		selectedSupplierForPoName;
	private String		shipToCompanyId;
	private String		shipToLocationId;
	private String		showOnlyOpenBuys;
	private boolean		showOnlyOpenRequests;
	private String		sortBy;
	private String		sortByDesc;
	private String		status;
	private String[]	statusArray;
	private String		statusDesc;
	private String		supplyPath;
	private String		supplyPathDesc;

	// constructor
	public BuyOrdersInputBean() {
	}

	public BuyOrdersInputBean(ActionForm form) {
		try {
			BeanHandler.copyAttributes(form, this);
		}
		catch (BeanCopyException e) {
			e.printStackTrace();
		}
	}

	public BuyOrdersInputBean(LazyDynaBean bean) {
		try {
			BeanHandler.copyAttributes(bean, this);
		}
		catch (BeanCopyException e) {
			e.printStackTrace();
		}
	}

	public BigDecimal getBestPriceDbuyContractId() {
		return bestPriceDbuyContractId;
	}

	public String getBoForConfirmedPo() {
		return this.boForConfirmedPo;
	}

	public String getBoForUnconfirmedPo() {
		return this.boForUnconfirmedPo;
	}

	public String getBoWithNoPo() {
		return this.boWithNoPo;
	}

	public String getBranchPlant() {
		return branchPlant;
	}

	public BigDecimal getBuyerId() {
		return buyerId;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public String getCancel() {
		return cancel;
	}

	public String getComments() {
		return comments;
	}

	public String getCompanyId() {
		return companyId;
	}

	public String[] getCompanyIdArray() {
		return companyIdArray;
	}

	public String getCompanyName() {
		return companyName;
	}

	public String getConfirmedButNoAssociation() {
		return this.confirmedButNoAssociation;
	}

	public String getCritical() {
		return critical;
	}

	public String getCustomerPoNumber() {
		return customerPoNumber;
	}

	public BigDecimal getDefaultBuyerId() {
		return this.defaultBuyerId;
	}

	public BigDecimal getDocNum() {
		return docNum;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public String getHomeCurrencyId() {
		return homeCurrencyId;
	}

	public String getHub() {
		return hub;
	}

	public String getHubName() {
		return hubName;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public String getInventoryGroupName() {
		return inventoryGroupName;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public String getLastSupplier() {
		return lastSupplier;
	}

	public String getLastSupplierName() {
		return lastSupplierName;
	}

	public String getLocale() {
		return locale;
	}

	public BigDecimal getMrLineItem() {
		return mrLineItem;
	}

	public BigDecimal getMrNumber() {
		return mrNumber;
	}

	public Date getNeedDate() {
		return needDate;
	}

	public String getOk() {
		return ok;
	}

	public BigDecimal getOldBuyerId() {
		return oldBuyerId;
	}

	public String getOldComments() {
		return oldComments;
	}

	public String getOldPoSupplierName() {
		return oldPoSupplierName;
	}

	public String getOldStatus() {
		return oldStatus;
	}

	public String getOpsEntityId() {
		return opsEntityId;
	}

	public BigDecimal getOrderQuantity() {
		return orderQuantity;
	}

	public String getPoSupplierId() {
		return poSupplierId;
	}

	public String getPoSupplierName() {
		return poSupplierName;
	}

	public BigDecimal getPrNumber() {
		return prNumber;
	}

	public Date getPromisedDate() {
		return promisedDate;
	}

	public BigDecimal getRadianPo() {
		return radianPo;
	}

	public BigDecimal getReasonId() {
		return reasonId;
	}

	public BigDecimal getRowNumber() {
		return this.rowNumber;
	}

	public String getSearchText() {
		return searchText;
	}

	public String getSearchType() {
		return searchType;
	}

	public String getSearchTypeDesc() {
		return searchTypeDesc;
	}

	public String getSearchWhat() {
		return searchWhat;
	}

	public String getSearchWhatDesc() {
		return searchWhatDesc;
	}

	public String getSelectedSupplierForPo() {
		return selectedSupplierForPo;
	}

	public String getSelectedSupplierForPoName() {
		return selectedSupplierForPoName;
	}

	public String getShipToCompanyId() {
		return shipToCompanyId;
	}

	public String getShipToLocationId() {
		return shipToLocationId;
	}

	public String getShowOnlyOpenBuys() {
		return showOnlyOpenBuys;
	}

	public String getSortBy() {
		return sortBy;
	}

	public String getSortByDesc() {
		return sortByDesc;
	}

	public String getStatus() {
		return status;
	}

	public String[] getStatusArray() {
		return statusArray;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public String getSupplyPath() {
		return supplyPath;
	}

	public String getSupplyPathDesc() {
		return supplyPathDesc;
	}

	public boolean hasBuyerId() {
		return buyerId != null;
	}
	
	public boolean hasRowNumber () {
		return rowNumber != null;
	}
	
	public boolean hasInventoryGroup () {
		return StringUtils.isNotBlank(inventoryGroup);
	}
	
	public boolean hasSearchText() {
		return StringUtils.isNotBlank(searchText);
	}
	
	public boolean isCreatePO() {
		return "createPo".equals(getuAction());
	}
	
	public boolean isInit() {
		return "init".equals(getuAction());
	}
	
	public boolean isConvertPurchase() {
		return "convertPurchase".equals(getuAction());
	}

	public boolean isExcludeMxItems() {
		return excludeMxItems;
	}
	
	public boolean isSelected() {
		return hasRowNumber();
	}

	public boolean isShowOnlyOpenRequests() {
		return this.showOnlyOpenRequests;
	}
	
	public boolean isSupplyPathCustomer() {
		return "Customer".equals(supplyPath);
	}

	public void setBestPriceDbuyContractId(BigDecimal bestPriceDbuyContractId) {
		this.bestPriceDbuyContractId = bestPriceDbuyContractId;
	}

	public void setBoForConfirmedPo(String s) {
		this.boForConfirmedPo = s;
	}

	public void setBoForUnconfirmedPo(String s) {
		this.boForUnconfirmedPo = s;
	}

	public void setBoWithNoPo(String s) {
		this.boWithNoPo = s;
	}

	public void setBranchPlant(String branchPlant) {
		this.branchPlant = branchPlant;
	}

	public void setBuyerId(BigDecimal buyerId) {
		this.buyerId = buyerId;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public void setCancel(String cancel) {
		this.cancel = cancel;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public void setCompanyId(String s) {
		this.companyId = s;
	}

	public void setCompanyIdArray(String[] companyIdArray) {
		this.companyIdArray = companyIdArray;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setConfirmedButNoAssociation(String s) {
		this.confirmedButNoAssociation = s;
	}

	public void setCritical(String critical) {
		this.critical = critical;
	}

	public void setCustomerPoNumber(String customerPoNumber) {
		this.customerPoNumber = customerPoNumber;
	}

	public void setDefaultBuyerId(BigDecimal defaultBuyerId) {
		this.defaultBuyerId = defaultBuyerId;
	}

	// getters

	public void setDocNum(BigDecimal docNum) {
		this.docNum = docNum;
	}

	public void setExcludeMxItems(boolean excludeMxItems) {
		this.excludeMxItems = excludeMxItems;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	@Override
	public void setHiddenFormFields() {
		// TODO Auto-generated method stub

	}

	public void setHomeCurrencyId(String homeCurrencyId) {
		this.homeCurrencyId = homeCurrencyId;
	}

	public void setHub(String hub) {
		this.hub = hub;
	}

	public void setHubName(String hubName) {
		this.hubName = hubName;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public void setInventoryGroupName(String inventoryGroupName) {
		this.inventoryGroupName = inventoryGroupName;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public void setLastSupplier(String lastSupplier) {
		this.lastSupplier = lastSupplier;
	}

	public void setLastSupplierName(String lastSupplierName) {
		this.lastSupplierName = lastSupplierName;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public void setMrLineItem(BigDecimal mrLineItem) {
		this.mrLineItem = mrLineItem;
	}

	public void setMrNumber(BigDecimal mrNumber) {
		this.mrNumber = mrNumber;
	}

	public void setNeedDate(Date needDate) {
		this.needDate = needDate;
	}

	public void setOk(String ok) {
		this.ok = ok;
	}

	public void setOldBuyerId(BigDecimal oldBuyerId) {
		this.oldBuyerId = oldBuyerId;
	}

	public void setOldComments(String oldComments) {
		this.oldComments = oldComments;
	}

	public void setOldPoSupplierName(String oldPoSupplierName) {
		this.oldPoSupplierName = oldPoSupplierName;
	}

	public void setOldStatus(String oldStatus) {
		this.oldStatus = oldStatus;
	}

	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}

	public void setOrderQuantity(BigDecimal orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	public void setPoSupplierId(String poSupplierId) {
		this.poSupplierId = poSupplierId;
	}

	public void setPoSupplierName(String poSupplierName) {
		this.poSupplierName = poSupplierName;
	}

	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}

	public void setPromisedDate(Date promisedDate) {
		this.promisedDate = promisedDate;
	}

	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}

	public void setReasonId(BigDecimal reasonId) {
		this.reasonId = reasonId;
	}

	public void setRowNumber(BigDecimal b) {
		this.rowNumber = b;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public void setSearchTypeDesc(String searchTypeDesc) {
		this.searchTypeDesc = searchTypeDesc;
	}

	public void setSearchWhat(String searchWhat) {
		this.searchWhat = searchWhat;
	}

	public void setSearchWhatDesc(String searchWhatDesc) {
		this.searchWhatDesc = searchWhatDesc;
	}

	public void setSelectedSupplierForPo(String selectedSupplierForPo) {
		this.selectedSupplierForPo = selectedSupplierForPo;
	}

	public void setSelectedSupplierForPoName(String selectedSupplierForPoName) {
		this.selectedSupplierForPoName = selectedSupplierForPoName;
	}

	public void setShipToCompanyId(String shipToCompanyId) {
		this.shipToCompanyId = shipToCompanyId;
	}

	public void setShipToLocationId(String shipToLocationId) {
		this.shipToLocationId = shipToLocationId;
	}

	public void setShowOnlyOpenBuys(String showOnlyOpenBuys) {
		this.showOnlyOpenBuys = showOnlyOpenBuys;
	}

	public void setShowOnlyOpenRequests(boolean showOnlyOpenRequests) {
		this.showOnlyOpenRequests = showOnlyOpenRequests;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public void setSortByDesc(String sortByDesc) {
		this.sortByDesc = sortByDesc;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setStatusArray(String[] s) {
		this.statusArray = s;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public void setSupplyPath(String supplyPath) {
		this.supplyPath = supplyPath;
	}

	public void setSupplyPathDesc(String supplyPathDesc) {
		this.supplyPathDesc = supplyPathDesc;
	}
}