package com.tcmis.internal.catalog.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: CatalogAddRequestNewBean <br>
 * @version: 1.0, Oct 30, 2007 <br>
 *****************************************************************************/


public class CatalogAddRequestNewBean extends BaseDataBean {

	private BigDecimal requestId;
	private BigDecimal requestor;
	private Date requestDate;
	private BigDecimal requestStatus;
	private String catalogId;
	private String catPartNo;
	private String stocked;
	private BigDecimal init90Day;
	private BigDecimal shelfLife;
	private String shelfLifeBasis;
	private String shelfLifeUnit;
	private String storageTemp;
	private String vendorContactName;
	private String vendorContactEmail;
	private String vendorContactPhone;
	private String vendorContactFax;
	private BigDecimal itemId;
	private BigDecimal startingView;
	private String suggestedVendor;
	private String vendorPartNo;
	private String requestRejected;
	private Date lastUpdated;
	private String engineeringEvaluation;
	private String engEvalWorkArea;
	private String engEvalFacilityId;
	private String accountSysId;
	private String freeSample;
	private String evalType;
	private String replacesPartNo;
	private String companyId;
	private BigDecimal customerRequestId;
	private BigDecimal customerRequestorId;
	private BigDecimal partGroupNo;
	private String shelfLifeSource;
	private Date submitDate;
	private String evalStatus;
	private BigDecimal evalRejectedBy;
	private String evalRejectedComment;
	private BigDecimal approvalGroupSeq;
	private String qcStatus;
	private Date qcDate;
	private String partDescription;
	private String poss;
	private String possEstMonUsage;
	private String possStore;
	private BigDecimal lastChangedBy;
	private String catPartDirectedChrgNo;
	private String catalogItemId;
	private String qualityControl;
	private String consumable;
	private String manageKitsAsSingleUnit;
	private String category;
	private String partNoComment;
	private BigDecimal baselinePrice;
	private BigDecimal catalogPrice;
	private String unitOfSale;
	private BigDecimal unitOfSalePrice;
	private String catAddPartNeedsReview;
	private String oldCatPartNo;


	//constructor
	public CatalogAddRequestNewBean() {
	}

	//setters
	public void setRequestId(BigDecimal requestId) {
		this.requestId = requestId;
	}
	public void setRequestor(BigDecimal requestor) {
		this.requestor = requestor;
	}
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	public void setRequestStatus(BigDecimal requestStatus) {
		this.requestStatus = requestStatus;
	}
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}
	public void setStocked(String stocked) {
		this.stocked = stocked;
	}
	public void setInit90Day(BigDecimal init90Day) {
		this.init90Day = init90Day;
	}
	public void setShelfLife(BigDecimal shelfLife) {
		this.shelfLife = shelfLife;
	}
	public void setShelfLifeBasis(String shelfLifeBasis) {
		this.shelfLifeBasis = shelfLifeBasis;
	}
	public void setShelfLifeUnit(String shelfLifeUnit) {
		this.shelfLifeUnit = shelfLifeUnit;
	}
	public void setStorageTemp(String storageTemp) {
		this.storageTemp = storageTemp;
	}
	public void setVendorContactName(String vendorContactName) {
		this.vendorContactName = vendorContactName;
	}
	public void setVendorContactEmail(String vendorContactEmail) {
		this.vendorContactEmail = vendorContactEmail;
	}
	public void setVendorContactPhone(String vendorContactPhone) {
		this.vendorContactPhone = vendorContactPhone;
	}
	public void setVendorContactFax(String vendorContactFax) {
		this.vendorContactFax = vendorContactFax;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setStartingView(BigDecimal startingView) {
		this.startingView = startingView;
	}
	public void setSuggestedVendor(String suggestedVendor) {
		this.suggestedVendor = suggestedVendor;
	}
	public void setVendorPartNo(String vendorPartNo) {
		this.vendorPartNo = vendorPartNo;
	}
	public void setRequestRejected(String requestRejected) {
		this.requestRejected = requestRejected;
	}
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	public void setEngineeringEvaluation(String engineeringEvaluation) {
		this.engineeringEvaluation = engineeringEvaluation;
	}
	public void setEngEvalWorkArea(String engEvalWorkArea) {
		this.engEvalWorkArea = engEvalWorkArea;
	}
	public void setEngEvalFacilityId(String engEvalFacilityId) {
		this.engEvalFacilityId = engEvalFacilityId;
	}
	public void setAccountSysId(String accountSysId) {
		this.accountSysId = accountSysId;
	}
	public void setFreeSample(String freeSample) {
		this.freeSample = freeSample;
	}
	public void setEvalType(String evalType) {
		this.evalType = evalType;
	}
	public void setReplacesPartNo(String replacesPartNo) {
		this.replacesPartNo = replacesPartNo;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setCustomerRequestId(BigDecimal customerRequestId) {
		this.customerRequestId = customerRequestId;
	}
	public void setCustomerRequestorId(BigDecimal customerRequestorId) {
		this.customerRequestorId = customerRequestorId;
	}
	public void setPartGroupNo(BigDecimal partGroupNo) {
		this.partGroupNo = partGroupNo;
	}
	public void setShelfLifeSource(String shelfLifeSource) {
		this.shelfLifeSource = shelfLifeSource;
	}
	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}
	public void setEvalStatus(String evalStatus) {
		this.evalStatus = evalStatus;
	}
	public void setEvalRejectedBy(BigDecimal evalRejectedBy) {
		this.evalRejectedBy = evalRejectedBy;
	}
	public void setEvalRejectedComment(String evalRejectedComment) {
		this.evalRejectedComment = evalRejectedComment;
	}
	public void setApprovalGroupSeq(BigDecimal approvalGroupSeq) {
		this.approvalGroupSeq = approvalGroupSeq;
	}
	public void setQcStatus(String qcStatus) {
		this.qcStatus = qcStatus;
	}
	public void setQcDate(Date qcDate) {
		this.qcDate = qcDate;
	}
	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}
	public void setPoss(String poss) {
		this.poss = poss;
	}
	public void setPossEstMonUsage(String possEstMonUsage) {
		this.possEstMonUsage = possEstMonUsage;
	}
	public void setPossStore(String possStore) {
		this.possStore = possStore;
	}
	public void setLastChangedBy(BigDecimal lastChangedBy) {
		this.lastChangedBy = lastChangedBy;
	}
	public void setCatPartDirectedChrgNo(String catPartDirectedChrgNo) {
		this.catPartDirectedChrgNo = catPartDirectedChrgNo;
	}
	public void setCatalogItemId(String catalogItemId) {
		this.catalogItemId = catalogItemId;
	}
	public void setQualityControl(String qualityControl) {
		this.qualityControl = qualityControl;
	}
	public void setConsumable(String consumable) {
		this.consumable = consumable;
	}
	public void setManageKitsAsSingleUnit(String manageKitsAsSingleUnit) {
		this.manageKitsAsSingleUnit = manageKitsAsSingleUnit;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public void setPartNoComment(String partNoComment) {
		this.partNoComment = partNoComment;
	}
	public void setBaselinePrice(BigDecimal baselinePrice) {
		this.baselinePrice = baselinePrice;
	}
	public void setCatalogPrice(BigDecimal catalogPrice) {
		this.catalogPrice = catalogPrice;
	}
	public void setUnitOfSale(String unitOfSale) {
		this.unitOfSale = unitOfSale;
	}
	public void setUnitOfSalePrice(BigDecimal unitOfSalePrice) {
		this.unitOfSalePrice = unitOfSalePrice;
	}
	public void setCatAddPartNeedsReview(String catAddPartNeedsReview) {
		this.catAddPartNeedsReview = catAddPartNeedsReview;
	}
	public void setOldCatPartNo(String oldCatPartNo) {
		this.oldCatPartNo = oldCatPartNo;
	}


	//getters
	public BigDecimal getRequestId() {
		return requestId;
	}
	public BigDecimal getRequestor() {
		return requestor;
	}
	public Date getRequestDate() {
		return requestDate;
	}
	public BigDecimal getRequestStatus() {
		return requestStatus;
	}
	public String getCatalogId() {
		return catalogId;
	}
	public String getCatPartNo() {
		return catPartNo;
	}
	public String getStocked() {
		return stocked;
	}
	public BigDecimal getInit90Day() {
		return init90Day;
	}
	public BigDecimal getShelfLife() {
		return shelfLife;
	}
	public String getShelfLifeBasis() {
		return shelfLifeBasis;
	}
	public String getShelfLifeUnit() {
		return shelfLifeUnit;
	}
	public String getStorageTemp() {
		return storageTemp;
	}
	public String getVendorContactName() {
		return vendorContactName;
	}
	public String getVendorContactEmail() {
		return vendorContactEmail;
	}
	public String getVendorContactPhone() {
		return vendorContactPhone;
	}
	public String getVendorContactFax() {
		return vendorContactFax;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public BigDecimal getStartingView() {
		return startingView;
	}
	public String getSuggestedVendor() {
		return suggestedVendor;
	}
	public String getVendorPartNo() {
		return vendorPartNo;
	}
	public String getRequestRejected() {
		return requestRejected;
	}
	public Date getLastUpdated() {
		return lastUpdated;
	}
	public String getEngineeringEvaluation() {
		return engineeringEvaluation;
	}
	public String getEngEvalWorkArea() {
		return engEvalWorkArea;
	}
	public String getEngEvalFacilityId() {
		return engEvalFacilityId;
	}
	public String getAccountSysId() {
		return accountSysId;
	}
	public String getFreeSample() {
		return freeSample;
	}
	public String getEvalType() {
		return evalType;
	}
	public String getReplacesPartNo() {
		return replacesPartNo;
	}
	public String getCompanyId() {
		return companyId;
	}
	public BigDecimal getCustomerRequestId() {
		return customerRequestId;
	}
	public BigDecimal getCustomerRequestorId() {
		return customerRequestorId;
	}
	public BigDecimal getPartGroupNo() {
		return partGroupNo;
	}
	public String getShelfLifeSource() {
		return shelfLifeSource;
	}
	public Date getSubmitDate() {
		return submitDate;
	}
	public String getEvalStatus() {
		return evalStatus;
	}
	public BigDecimal getEvalRejectedBy() {
		return evalRejectedBy;
	}
	public String getEvalRejectedComment() {
		return evalRejectedComment;
	}
	public BigDecimal getApprovalGroupSeq() {
		return approvalGroupSeq;
	}
	public String getQcStatus() {
		return qcStatus;
	}
	public Date getQcDate() {
		return qcDate;
	}
	public String getPartDescription() {
		return partDescription;
	}
	public String getPoss() {
		return poss;
	}
	public String getPossEstMonUsage() {
		return possEstMonUsage;
	}
	public String getPossStore() {
		return possStore;
	}
	public BigDecimal getLastChangedBy() {
		return lastChangedBy;
	}
	public String getCatPartDirectedChrgNo() {
		return catPartDirectedChrgNo;
	}
	public String getCatalogItemId() {
		return catalogItemId;
	}
	public String getQualityControl() {
		return qualityControl;
	}
	public String getConsumable() {
		return consumable;
	}
	public String getManageKitsAsSingleUnit() {
		return manageKitsAsSingleUnit;
	}
	public String getCategory() {
		return category;
	}
	public String getPartNoComment() {
		return partNoComment;
	}
	public BigDecimal getBaselinePrice() {
		return baselinePrice;
	}
	public BigDecimal getCatalogPrice() {
		return catalogPrice;
	}
	public String getUnitOfSale() {
		return unitOfSale;
	}
	public BigDecimal getUnitOfSalePrice() {
		return unitOfSalePrice;
	}
	public String getCatAddPartNeedsReview() {
		return catAddPartNeedsReview;
	}
	public String getOldCatPartNo() {
		return oldCatPartNo;
	}
}