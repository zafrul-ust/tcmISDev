package com.tcmis.client.operations.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: CabinetInventoryCountBean <br>
 * @version: 1.0, Aug 3, 2009 <br>
 *****************************************************************************/


public class CabinetInventoryCountBean extends BaseDataBean {

	
	private static final long serialVersionUID = 866870135115510966L;
	private BigDecimal binId;
	private Date countDatetime;
	private BigDecimal receiptId;
	private String companyId;
	private BigDecimal personnelId;
	private BigDecimal countQuantity;
	private Date dateProcessed;
	private Date modifiedTimestamp;
	private String hub;
	private BigDecimal cabinetId;
	private String cabinetName;
	private String facilityId;
	private String orderingApplication;
	private String useApplication;
	private String mfgLot;
	private Date expireDate;
	private BigDecimal 	countedByPersonnelId;
	private String 		countedByName;
	private BigDecimal  itemId;
	private String 		itemDesc;
	private BigDecimal 	qtyIssuedAfterCount;
	private BigDecimal 	totalQuantity;
	private String 		qcDoc;
	private BigDecimal	unitCost;
	private String 		catPartNo;
	private BigDecimal 	stockingLevel;
	private BigDecimal	qtyAvailableAfterAlloc;
	private BigDecimal 	leadTimeDays;
	private BigDecimal reorderPoint;
    private BigDecimal openMrQty;
    private String currencyId;
    private String status;

	//constructor
	public CabinetInventoryCountBean() {
	}

    public BigDecimal getOpenMrQty() {
        return openMrQty;
    }

    public void setOpenMrQty(BigDecimal openMrQty) {
        this.openMrQty = openMrQty;
    }

    //setters
	public void setBinId(BigDecimal binId) {
		this.binId = binId;
	}
	public void setCountDatetime(Date countDatetime) {
		this.countDatetime = countDatetime;
	}
	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
	}
	public void setCountQuantity(BigDecimal countQuantity) {
		this.countQuantity = countQuantity;
	}
	public void setDateProcessed(Date dateProcessed) {
		this.dateProcessed = dateProcessed;
	}
	public void setModifiedTimestamp(Date modifiedTimestamp) {
		this.modifiedTimestamp = modifiedTimestamp;
	}


	//getters
	public BigDecimal getBinId() {
		return binId;
	}
	public Date getCountDatetime() {
		return countDatetime;
	}
	public BigDecimal getReceiptId() {
		return receiptId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public BigDecimal getPersonnelId() {
		return personnelId;
	}
	public BigDecimal getCountQuantity() {
		return countQuantity;
	}
	public Date getDateProcessed() {
		return dateProcessed;
	}
	public Date getModifiedTimestamp() {
		return modifiedTimestamp;
	}

	/**
	 * @return the hub
	 */
	public String getHub() {
		return hub;
	}

	/**
	 * @param hub the hub to set
	 */
	public void setHub(String hub) {
		this.hub = hub;
	}

	/**
	 * @return the cabinetId
	 */
	public BigDecimal getCabinetId() {
		return cabinetId;
	}

	/**
	 * @param cabinetId the cabinetId to set
	 */
	public void setCabinetId(BigDecimal cabinetId) {
		this.cabinetId = cabinetId;
	}

	/**
	 * @return the cabinetName
	 */
	public String getCabinetName() {
		return cabinetName;
	}

	/**
	 * @param cabinetName the cabinetName to set
	 */
	public void setCabinetName(String cabinetName) {
		this.cabinetName = cabinetName;
	}

	/**
	 * @return the facilityId
	 */
	public String getFacilityId() {
		return facilityId;
	}

	/**
	 * @param facilityId the facilityId to set
	 */
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	/**
	 * @return the orderingApplication
	 */
	public String getOrderingApplication() {
		return orderingApplication;
	}

	/**
	 * @param orderingApplication the orderingApplication to set
	 */
	public void setOrderingApplication(String orderingApplication) {
		this.orderingApplication = orderingApplication;
	}

	/**
	 * @return the useApplication
	 */
	public String getUseApplication() {
		return useApplication;
	}

	/**
	 * @param useApplication the useApplication to set
	 */
	public void setUseApplication(String useApplication) {
		this.useApplication = useApplication;
	}

	/**
	 * @return the mfgLot
	 */
	public String getMfgLot() {
		return mfgLot;
	}

	/**
	 * @param mfgLot the mfgLot to set
	 */
	public void setMfgLot(String mfgLot) {
		this.mfgLot = mfgLot;
	}

	/**
	 * @return the expireDate
	 */
	public Date getExpireDate() {
		return expireDate;
	}

	/**
	 * @param expireDate the expireDate to set
	 */
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	/**
	 * @return the countedByPersonnelId
	 */
	public BigDecimal getCountedByPersonnelId() {
		return countedByPersonnelId;
	}

	/**
	 * @param countedByPersonnelId the countedByPersonnelId to set
	 */
	public void setCountedByPersonnelId(BigDecimal countedByPersonnelId) {
		this.countedByPersonnelId = countedByPersonnelId;
	}

	/**
	 * @return the countedByName
	 */
	public String getCountedByName() {
		return countedByName;
	}

	/**
	 * @param countedByName the countedByName to set
	 */
	public void setCountedByName(String countedByName) {
		this.countedByName = countedByName;
	}

	/**
	 * @return the itemId
	 */
	public BigDecimal getItemId() {
		return itemId;
	}

	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	/**
	 * @return the itemDesc
	 */
	public String getItemDesc() {
		return itemDesc;
	}

	/**
	 * @param itemDesc the itemDesc to set
	 */
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	/**
	 * @return the qtyIssuedAfterCount
	 */
	public BigDecimal getQtyIssuedAfterCount() {
		return qtyIssuedAfterCount;
	}

	/**
	 * @param qtyIssuedAfterCount the qtyIssuedAfterCount to set
	 */
	public void setQtyIssuedAfterCount(BigDecimal qtyIssuedAfterCount) {
		this.qtyIssuedAfterCount = qtyIssuedAfterCount;
	}

	/**
	 * @return the totalQuantity
	 */
	public BigDecimal getTotalQuantity() {
		return totalQuantity;
	}

	/**
	 * @param totalQuantity the totalQuantity to set
	 */
	public void setTotalQuantity(BigDecimal totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	/**
	 * @return the qcDoc
	 */
	public String getQcDoc() {
		return qcDoc;
	}

	/**
	 * @param qcDoc the qcDoc to set
	 */
	public void setQcDoc(String qcDoc) {
		this.qcDoc = qcDoc;
	}

	/**
	 * @return the unitCost
	 */
	public BigDecimal getUnitCost() {
		return unitCost;
	}

	/**
	 * @param unitCost the unitCost to set
	 */
	public void setUnitCost(BigDecimal unitCost) {
		this.unitCost = unitCost;
	}

	/**
	 * @return the catPartNo
	 */
	public String getCatPartNo() {
		return catPartNo;
	}

	/**
	 * @param catPartNo the catPartNo to set
	 */
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}

	/**
	 * @return the stockingLevel
	 */
	public BigDecimal getStockingLevel() {
		return stockingLevel;
	}

	/**
	 * @param stockingLevel the stockingLevel to set
	 */
	public void setStockingLevel(BigDecimal stockingLevel) {
		this.stockingLevel = stockingLevel;
	}

	/**
	 * @return the qtyAvailableAfterAlloc
	 */
	public BigDecimal getQtyAvailableAfterAlloc() {
		return qtyAvailableAfterAlloc;
	}

	/**
	 * @param qtyAvailableAfterAlloc the qtyAvailableAfterAlloc to set
	 */
	public void setQtyAvailableAfterAlloc(BigDecimal qtyAvailableAfterAlloc) {
		this.qtyAvailableAfterAlloc = qtyAvailableAfterAlloc;
	}

	/**
	 * @return the leadTimeDays
	 */
	public BigDecimal getLeadTimeDays() {
		return leadTimeDays;
	}

	/**
	 * @param leadTimeDays the leadTimeDays to set
	 */
	public void setLeadTimeDays(BigDecimal leadTimeDays) {
		this.leadTimeDays = leadTimeDays;
	}

	/**
	 * @return the reorderPoint
	 */
	public BigDecimal getReorderPoint() {
		return reorderPoint;
	}

	/**
	 * @param reorderPoint the reorderPoint to set
	 */
	public void setReorderPoint(BigDecimal reorderPoint) {
		this.reorderPoint = reorderPoint;
	}

	public String getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}