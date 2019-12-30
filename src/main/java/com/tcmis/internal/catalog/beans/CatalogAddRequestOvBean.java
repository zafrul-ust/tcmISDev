package com.tcmis.internal.catalog.beans;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: CatalogAddRequestOvBean <br>
 * @version: 1.0, Aug 20, 2010 <br>
 *****************************************************************************/

public class CatalogAddRequestOvBean extends BaseDataBean implements SQLData {

	public static final String sqlType = "CATALOG_ADD_REQUEST_NEW_OBJ";
	private String accountSysId;
	private String alternateName;
	private BigDecimal approvalGroupSeq;
	private BigDecimal assignedTo;
	private String assignedToFullName;
	private BigDecimal baselinePrice;
	private String catAddPartNeedsReview;
	private Collection catalogAddItemData;
	private String catalogCompanyId;
	private String catalogDesc;
	private String catalogId;
	private String catalogItemId;
	private BigDecimal catalogPrice;
	private String category;
	private String catPartDirectedChrgNo;
	private String catPartNo;
	private String companyId;
	private String companyName;
	private String consumable;
	private String createTemporaryItem;
	private BigDecimal customerRequestId;
	private BigDecimal customerRequestorId;
	private String email;
	private String engEvalFacilityId;
	private String engEvalWorkArea;
	private String engineeringEvaluation;
	private BigDecimal evalRejectedBy;
	private String evalRejectedComment;
	private String evalStatus;
	private String evalType;
	private String facilityName;
	private String freeSample;
	private String fullName;
	private BigDecimal init90Day;
	private String inventoryGroup;
	private BigDecimal lastChangedBy;
	private Date lastUpdated;
	private String manageKitsAsSingleUnit;
	private BigDecimal maxStorageTemp;
	private String messageToApprovers;
	private BigDecimal minStorageTemp;
	private String oldCatPartNo;
	private String operatingEntityName;
	private String partDescription;
	private BigDecimal partGroupNo;
	private String partNoComment;
	private String phone;
	private String poss;
	private String possEstMonUsage;
	private String possStore;
	private Date qcDate;
	private String qcStatus;
	private String qualityControl;
	private String replacesPartNo;
	private Date requestDate;
	private BigDecimal requestId;
	private BigDecimal requestor;
	private String requestRejected;
	private BigDecimal requestStatus;
	private String requestStatusDesc;
	private String shelfLifeBasis;
	private BigDecimal shelfLifeDays;
	private String shelfLifeSource;
	private BigDecimal shippingWeight;
	private String shippingWeightUnit;
	private BigDecimal startingView;
	private String stocked;
	private String storageTempUnit;
	private Date submitDate;
	private String temporaryAlternateName;
	private String temporaryCatPartNo;
	private BigDecimal temporaryItemId;
	private String unitOfSale;
	private BigDecimal unitOfSalePrice;
	private String vendorPartNo;
    private String msdsIndexingStatus;
    private String jspLabel;
	private String vendorAssignmentStatus;
	private String requestDisplayStatus;
	private String rowPermission;
	private String sdsIndexingClosed;
	private String itemCreationClosed;
	private String localeCode;

    //constructor
	public CatalogAddRequestOvBean() {
	}

	public String getAccountSysId() {
		return accountSysId;
	}

	public String getAlternateName() {
		return alternateName;
	}

	public BigDecimal getApprovalGroupSeq() {
		return approvalGroupSeq;
	}

	public BigDecimal getAssignedTo() {
		return assignedTo;
	}

	public String getAssignedToFullName() {
		return assignedToFullName;
	}

	public BigDecimal getBaselinePrice() {
		return baselinePrice;
	}

	public String getCatAddPartNeedsReview() {
		return catAddPartNeedsReview;
	}

	@SuppressWarnings("unchecked")
	public Collection<CatalogAddItemQcBean> getCatalogAddItemData() {
		return catalogAddItemData;
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

	public String getCatalogItemId() {
		return catalogItemId;
	}

	public BigDecimal getCatalogPrice() {
		return catalogPrice;
	}

	public String getCategory() {
		return category;
	}

	public String getCatPartDirectedChrgNo() {
		return catPartDirectedChrgNo;
	}

	public String getCatPartNo() {
		return catPartNo;
	}

	public String getCompanyId() {
		return companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public String getConsumable() {
		return consumable;
	}

	public String getCreateTemporaryItem() {
		return createTemporaryItem;
	}

	public BigDecimal getCustomerRequestId() {
		return customerRequestId;
	}

	public BigDecimal getCustomerRequestorId() {
		return customerRequestorId;
	}

	public String getEmail() {
		return email;
	}

	public String getEngEvalFacilityId() {
		return engEvalFacilityId;
	}

	public String getEngEvalWorkArea() {
		return engEvalWorkArea;
	}

	public String getEngineeringEvaluation() {
		return engineeringEvaluation;
	}

	public BigDecimal getEvalRejectedBy() {
		return evalRejectedBy;
	}

	public String getEvalRejectedComment() {
		return evalRejectedComment;
	}

	public String getEvalStatus() {
		return evalStatus;
	}

	public String getEvalType() {
		return evalType;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public String getFreeSample() {
		return freeSample;
	}

	public String getFullName() {
		return fullName;
	}

	public BigDecimal getInit90Day() {
		return init90Day;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public BigDecimal getLastChangedBy() {
		return lastChangedBy;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public int getLineCount() {
		return catalogAddItemData != null ? catalogAddItemData.size() : 0;
	}

	public String getManageKitsAsSingleUnit() {
		return manageKitsAsSingleUnit;
	}

	public BigDecimal getMaxStorageTemp() {
		return maxStorageTemp;
	}

	public String getMessageToApprovers() {
		return messageToApprovers;
	}

	public BigDecimal getMinStorageTemp() {
		return minStorageTemp;
	}

	public String getOldCatPartNo() {
		return oldCatPartNo;
	}

	public String getOperatingEntityName() {
		return operatingEntityName;
	}

	public String getPartDescription() {
		return partDescription;
	}

	public BigDecimal getPartGroupNo() {
		return partGroupNo;
	}

	public String getPartNoComment() {
		return partNoComment;
	}

	public String getPhone() {
		return phone;
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

	public Date getQcDate() {
		return qcDate;
	}

	public String getQcStatus() {
		return qcStatus;
	}

	public String getQualityControl() {
		return qualityControl;
	}

	public String getReplacesPartNo() {
		return replacesPartNo;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	//getters
	public BigDecimal getRequestId() {
		return requestId;
	}

	public BigDecimal getRequestor() {
		return requestor;
	}

	public String getRequestRejected() {
		return requestRejected;
	}

	public BigDecimal getRequestStatus() {
		return requestStatus;
	}

	public String getRequestStatusDesc() {
		return requestStatusDesc;
	}

	public String getShelfLifeBasis() {
		return shelfLifeBasis;
	}

	public BigDecimal getShelfLifeDays() {
		return shelfLifeDays;
	}

	public String getShelfLifeSource() {
		return shelfLifeSource;
	}

	public BigDecimal getShippingWeight() {
		return shippingWeight;
	}

	public String getShippingWeightUnit() {
		return shippingWeightUnit;
	}

	public String getSQLTypeName() throws SQLException {
		return sqlType;
	}

	public BigDecimal getStartingView() {
		return startingView;
	}

	public String getStocked() {
		return stocked;
	}

	public String getStorageTempUnit() {
		return storageTempUnit;
	}

	public Date getSubmitDate() {
		return submitDate;
	}

	public String getTemporaryAlternateName() {
		return temporaryAlternateName;
	}

	public String getTemporaryCatPartNo() {
		return temporaryCatPartNo;
	}

	public BigDecimal getTemporaryItemId() {
		return temporaryItemId;
	}

	public String getUnitOfSale() {
		return unitOfSale;
	}

	public BigDecimal getUnitOfSalePrice() {
		return unitOfSalePrice;
	}

	public String getVendorPartNo() {
		return vendorPartNo;
	}

    public String getMsdsIndexingStatus() {
        return msdsIndexingStatus;
    }

    public String getJspLabel() {
        return jspLabel;
    }

    public void readSQL(SQLInput stream, String typeName) throws SQLException {
		try {
			setRequestId(stream.readBigDecimal());
			setRequestor(stream.readBigDecimal());
			setRequestDate(stream.readTimestamp());
			setRequestStatus(stream.readBigDecimal());
			setCatalogId(stream.readString());
			setCatPartNo(stream.readString());
			setStocked(stream.readString());
			setStartingView(stream.readBigDecimal());
			setVendorPartNo(stream.readString());
			setRequestRejected(stream.readString());
			setLastUpdated(stream.readTimestamp());
			setEngineeringEvaluation(stream.readString());
			setEngEvalWorkArea(stream.readString());
			setEngEvalFacilityId(stream.readString());
			setAccountSysId(stream.readString());
			setFreeSample(stream.readString());
			setEvalType(stream.readString());
			setReplacesPartNo(stream.readString());
			setCompanyId(stream.readString());
			setCustomerRequestId(stream.readBigDecimal());
			setCustomerRequestorId(stream.readBigDecimal());
			setPartGroupNo(stream.readBigDecimal());
			setShelfLifeSource(stream.readString());
			setSubmitDate(stream.readTimestamp());
			setEvalStatus(stream.readString());
			setEvalRejectedBy(stream.readBigDecimal());
			setEvalRejectedComment(stream.readString());
			setApprovalGroupSeq(stream.readBigDecimal());
			setQcStatus(stream.readString());
            setMsdsIndexingStatus(stream.readString());
            setQcDate(stream.readTimestamp());
			setPartDescription(stream.readString());
			setPoss(stream.readString());
			setPossEstMonUsage(stream.readString());
			setPossStore(stream.readString());
			setLastChangedBy(stream.readBigDecimal());
			setCatPartDirectedChrgNo(stream.readString());
			setCatalogItemId(stream.readString());
			setQualityControl(stream.readString());
			setConsumable(stream.readString());
			setManageKitsAsSingleUnit(stream.readString());
			setCategory(stream.readString());
			setPartNoComment(stream.readString());
			setBaselinePrice(stream.readBigDecimal());
			setCatalogPrice(stream.readBigDecimal());
			setUnitOfSale(stream.readString());
			setUnitOfSalePrice(stream.readBigDecimal());
			setCatAddPartNeedsReview(stream.readString());
			setOldCatPartNo(stream.readString());
			setCatalogCompanyId(stream.readString());
			setInventoryGroup(stream.readString());
			setCreateTemporaryItem(stream.readString());
			setTemporaryItemId(stream.readBigDecimal());
			setTemporaryCatPartNo(stream.readString());
			setAlternateName(stream.readString());
			setTemporaryAlternateName(stream.readString());
			setShippingWeight(stream.readBigDecimal());
			setShippingWeightUnit(stream.readString());
			setMessageToApprovers(stream.readString());
			setShelfLifeDays(stream.readBigDecimal());
			setShelfLifeBasis(stream.readString());
			setMinStorageTemp(stream.readBigDecimal());
			setMaxStorageTemp(stream.readBigDecimal());
			setStorageTempUnit(stream.readString());
			setInit90Day(stream.readBigDecimal());
			setFullName(stream.readString());
			setPhone(stream.readString());
			setEmail(stream.readString());
			setRequestStatusDesc(stream.readString());
			setCompanyName(stream.readString());
			setCatalogDesc(stream.readString());
			setAssignedTo(stream.readBigDecimal());
			setAssignedToFullName(stream.readString());
			setFacilityName(stream.readString());
			setOperatingEntityName(stream.readString());
            setJspLabel(stream.readString());
            setCatalogAddItemDataArray(stream.readArray());
		}
		catch (SQLException e) {
			throw e;
		}
		catch (Exception e) {
			Log log = LogFactory.getLog(this.getClass());
			log.error("readSQL method failed", e);
		}
	}

	public void setAccountSysId(String accountSysId) {
		this.accountSysId = accountSysId;
	}

	public void setAlternateName(String alternateName) {
		this.alternateName = alternateName;
	}

	public void setApprovalGroupSeq(BigDecimal approvalGroupSeq) {
		this.approvalGroupSeq = approvalGroupSeq;
	}

	public void setAssignedTo(BigDecimal assignedTo) {
		this.assignedTo = assignedTo;
	}

	public void setAssignedToFullName(String assignedToFullName) {
		this.assignedToFullName = assignedToFullName;
	}

	public void setBaselinePrice(BigDecimal baselinePrice) {
		this.baselinePrice = baselinePrice;
	}

	public void setCatAddPartNeedsReview(String catAddPartNeedsReview) {
		this.catAddPartNeedsReview = catAddPartNeedsReview;
	}

	@SuppressWarnings("unchecked")
	public void setCatalogAddItemData(Collection catalogAddItemData) {
		this.catalogAddItemData = catalogAddItemData;
	}

	public void setCatalogAddItemDataArray(Array catalogAddItemDataArray) {
		try {
			setCatalogAddItemData(Arrays.asList((Object[]) catalogAddItemDataArray.getArray()));
		}
		catch (SQLException sqle) {
			System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
		}
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

	public void setCatalogItemId(String catalogItemId) {
		this.catalogItemId = catalogItemId;
	}

	public void setCatalogPrice(BigDecimal catalogPrice) {
		this.catalogPrice = catalogPrice;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setCatPartDirectedChrgNo(String catPartDirectedChrgNo) {
		this.catPartDirectedChrgNo = catPartDirectedChrgNo;
	}

	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setConsumable(String consumable) {
		this.consumable = consumable;
	}

	public void setCreateTemporaryItem(String createTemporaryItem) {
		this.createTemporaryItem = createTemporaryItem;
	}

	public void setCustomerRequestId(BigDecimal customerRequestId) {
		this.customerRequestId = customerRequestId;
	}

	public void setCustomerRequestorId(BigDecimal customerRequestorId) {
		this.customerRequestorId = customerRequestorId;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setEngEvalFacilityId(String engEvalFacilityId) {
		this.engEvalFacilityId = engEvalFacilityId;
	}

	public void setEngEvalWorkArea(String engEvalWorkArea) {
		this.engEvalWorkArea = engEvalWorkArea;
	}

	public void setEngineeringEvaluation(String engineeringEvaluation) {
		this.engineeringEvaluation = engineeringEvaluation;
	}

	public void setEvalRejectedBy(BigDecimal evalRejectedBy) {
		this.evalRejectedBy = evalRejectedBy;
	}

	public void setEvalRejectedComment(String evalRejectedComment) {
		this.evalRejectedComment = evalRejectedComment;
	}

	public void setEvalStatus(String evalStatus) {
		this.evalStatus = evalStatus;
	}

	public void setEvalType(String evalType) {
		this.evalType = evalType;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public void setFreeSample(String freeSample) {
		this.freeSample = freeSample;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setInit90Day(BigDecimal init90Day) {
		this.init90Day = init90Day;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public void setLastChangedBy(BigDecimal lastChangedBy) {
		this.lastChangedBy = lastChangedBy;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public void setManageKitsAsSingleUnit(String manageKitsAsSingleUnit) {
		this.manageKitsAsSingleUnit = manageKitsAsSingleUnit;
	}

	public void setMaxStorageTemp(BigDecimal maxStorageTemp) {
		this.maxStorageTemp = maxStorageTemp;
	}

	public void setMessageToApprovers(String messageToApprovers) {
		this.messageToApprovers = messageToApprovers;
	}

	public void setMinStorageTemp(BigDecimal minStorageTemp) {
		this.minStorageTemp = minStorageTemp;
	}

	public void setOldCatPartNo(String oldCatPartNo) {
		this.oldCatPartNo = oldCatPartNo;
	}

	public void setOperatingEntityName(String operatingEntityName) {
		this.operatingEntityName = operatingEntityName;
	}

	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}

	public void setPartGroupNo(BigDecimal partGroupNo) {
		this.partGroupNo = partGroupNo;
	}

	public void setPartNoComment(String partNoComment) {
		this.partNoComment = partNoComment;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public void setQcDate(Date qcDate) {
		this.qcDate = qcDate;
	}

	public void setQcStatus(String qcStatus) {
		this.qcStatus = qcStatus;
	}

	public void setQualityControl(String qualityControl) {
		this.qualityControl = qualityControl;
	}

	public void setReplacesPartNo(String replacesPartNo) {
		this.replacesPartNo = replacesPartNo;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	//setters
	public void setRequestId(BigDecimal requestId) {
		this.requestId = requestId;
	}

	public void setRequestor(BigDecimal requestor) {
		this.requestor = requestor;
	}

	public void setRequestRejected(String requestRejected) {
		this.requestRejected = requestRejected;
	}

	public void setRequestStatus(BigDecimal requestStatus) {
		this.requestStatus = requestStatus;
	}

	public void setRequestStatusDesc(String requestStatusDesc) {
		this.requestStatusDesc = requestStatusDesc;
	}

	public void setShelfLifeBasis(String shelfLifeBasis) {
		this.shelfLifeBasis = shelfLifeBasis;
	}

	public void setShelfLifeDays(BigDecimal shelfLifeDays) {
		this.shelfLifeDays = shelfLifeDays;
	}

	public void setShelfLifeSource(String shelfLifeSource) {
		this.shelfLifeSource = shelfLifeSource;
	}

	public void setShippingWeight(BigDecimal shippingWeight) {
		this.shippingWeight = shippingWeight;
	}

	public void setShippingWeightUnit(String shippingWeightUnit) {
		this.shippingWeightUnit = shippingWeightUnit;
	}

	public void setStartingView(BigDecimal startingView) {
		this.startingView = startingView;
	}

	public void setStocked(String stocked) {
		this.stocked = stocked;
	}

	public void setStorageTempUnit(String storageTempUnit) {
		this.storageTempUnit = storageTempUnit;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

	public void setTemporaryAlternateName(String temporaryAlternateName) {
		this.temporaryAlternateName = temporaryAlternateName;
	}

	public void setTemporaryCatPartNo(String temporaryCatPartNo) {
		this.temporaryCatPartNo = temporaryCatPartNo;
	}

	public void setTemporaryItemId(BigDecimal temporaryItemId) {
		this.temporaryItemId = temporaryItemId;
	}

	public void setUnitOfSale(String unitOfSale) {
		this.unitOfSale = unitOfSale;
	}

	public void setUnitOfSalePrice(BigDecimal unitOfSalePrice) {
		this.unitOfSalePrice = unitOfSalePrice;
	}
	
	public void setVendorPartNo(String vendorPartNo) {
		this.vendorPartNo = vendorPartNo;
	}

    public void setMsdsIndexingStatus(String msdsIndexingStatus) {
        this.msdsIndexingStatus = msdsIndexingStatus;
    }

    public void setJspLabel(String jspLabel) {
        this.jspLabel = jspLabel;
    }

	public void writeSQL(SQLOutput stream) throws SQLException {
		// Aint gonna happen ;-}
		// This is a read only bean
	}

	public String getVendorAssignmentStatus() {
		return vendorAssignmentStatus;
	}

	public void setVendorAssignmentStatus(String vendorAssignmentStatus) {
		this.vendorAssignmentStatus = vendorAssignmentStatus;
	}

	public String getRequestDisplayStatus() {
		return requestDisplayStatus;
	}

	public void setRequestDisplayStatus(String requestDisplayStatus) {
		this.requestDisplayStatus = requestDisplayStatus;
	}

	public String getRowPermission() {
		return rowPermission;
	}

	public void setRowPermission(String rowPermission) {
		this.rowPermission = rowPermission;
	}

	public String getSdsIndexingClosed() {
		return sdsIndexingClosed;
	}

	public void setSdsIndexingClosed(String sdsIndexingClosed) {
		this.sdsIndexingClosed = sdsIndexingClosed;
	}

	public String getItemCreationClosed() {
		return itemCreationClosed;
	}

	public void setItemCreationClosed(String itemCreationClosed) {
		this.itemCreationClosed = itemCreationClosed;
	}

	public String getLocaleCode() {
		return localeCode;
	}

	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}
}