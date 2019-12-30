package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: CabinetPartLevelViewBean <br>
 * 
 * @version: 1.0, Oct 10, 2006 <br>
 *****************************************************************************/

@SuppressWarnings("serial")
public class CabinetPartLevelViewBean extends BaseDataBean {

	private String		application;
	private BigDecimal	binId;
	private String		binName;
	private String		binPartStatus;
	private Vector		bins;
	private BigDecimal	cabinetId;
	private String		cabinetName;
	private int			cabinetRowspan;
	// collections for relational views
	private Vector		cabinets;
	private String		catalogCompanyId;
	private String		catalogId;
	private String		catPartNo;
	private String		companyId;
	private String		countType;
	private String		facilityId;
	private BigDecimal	itemId;
	private BigDecimal	kanbanReorderQuantity;
	private String		labelSpec;
	private BigDecimal	leadTimeDays;
	private String		materialDesc;
	private String		materialHandlingCode;
	private String		materialIdString;
	private String		mfgDesc;
	private String		oldCountType;
	private String		oldStatus;
	private String		orderingApplication;
	private String		packaging;
	private String		partDescription;
	private BigDecimal	partGroupNo;
	private BigDecimal	reorderPoint;

	private BigDecimal	reorderQuantity;
	private String		sourceHub;
	private String		status;
	private BigDecimal	stockingLevel;
	private String		useApplication;
	private int			workareaRowspan;	
	private Date 		levelHoldEndDate;
    private String      putAwayMethodOverride;
    private String      dropShipOverride;


    // constructor
	public CabinetPartLevelViewBean() {
	}

	public String getApplication() {
		return application;
	}

	public BigDecimal getBinId() {
		return binId;
	}

	public String getBinName() {
		return binName;
	}

	public String getBinPartStatus() {
		return binPartStatus;
	}

	public Collection getBins() {
		return bins;
	}

	public BigDecimal getCabinetId() {
		return cabinetId;
	}

	public String getCabinetName() {
		return cabinetName;
	}

	/**
	 * Getter for property cabinetRowspan.
	 * 
	 * @return Value of property cabinetRowspan.
	 */
	public int getCabinetRowspan() {
		return cabinetRowspan;
	}

	public Collection getCabinets() {
		return cabinets;
	}

	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public String getCatPartNo() {
		return catPartNo;
	}

	public String getCompanyId() {
		return companyId;
	}

	/**
	 * Returns the countType
	 * 
	 * @return the countType
	 */
	public String getCountType() {
		return countType;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public BigDecimal getKanbanReorderQuantity() {
		return kanbanReorderQuantity;
	}

	public String getLabelSpec() {
		return labelSpec;
	}

	public BigDecimal getLeadTimeDays() {
		return leadTimeDays;
	}

	public String getMaterialDesc() {
		return materialDesc;
	}

	public String getMaterialHandlingCode() {
		return materialHandlingCode;
	}

	public String getMaterialIdString() {
		return materialIdString;
	}

	public String getMfgDesc() {
		return mfgDesc;
	}

	public String getOldCountType() {
		return oldCountType;
	}

	public String getOldStatus() {
		return oldStatus;
	}

	public String getOrderingApplication() {
		return orderingApplication;
	}

	public String getPackaging() {
		return packaging;
	}

	public String getPartDescription() {
		return partDescription;
	}

	public BigDecimal getPartGroupNo() {
		return partGroupNo;
	}

	public BigDecimal getReorderPoint() {
		return reorderPoint;
	}

	public BigDecimal getReorderQuantity() {
		return reorderQuantity;
	}

	// getters
	public String getSourceHub() {
		return sourceHub;
	}

	public String getStatus() {
		return status;
	}

	public BigDecimal getStockingLevel() {
		return stockingLevel;
	}

	public String getUseApplication() {
		return useApplication;
	}

	/**
	 * Getter for property workareaRowspan.
	 * 
	 * @return Value of property workareaRowspan.
	 */
	public int getWorkareaRowspan() {
		return workareaRowspan;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public void setBinId(BigDecimal binId) {
		this.binId = binId;
	}

	public void setBinName(String binName) {
		this.binName = binName;
	}

	public void setBinPartStatus(String binPartStatus) {
		this.binPartStatus = binPartStatus;
	}

	public void setBins(Vector b) {
		bins = b;
	}

	public void setCabinetId(BigDecimal cabinetId) {
		this.cabinetId = cabinetId;
	}

	public void setCabinetName(String cabinetName) {
		this.cabinetName = cabinetName;
	}

	/**
	 * Setter for property cabinetRowspan.
	 * 
	 * @param cabinetRowspan
	 *            New value of property cabinetRowspan.
	 */
	public void setCabinetRowspan(int cabinetRowspan) {
		this.cabinetRowspan = cabinetRowspan;
	}

	public void setCabinets(Vector c) {
		cabinets = c;
	}

	public void setCatalogCompanyId(String catalogCompanyId) {
		this.catalogCompanyId = catalogCompanyId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	/**
	 * Sets the countType
	 * 
	 * @param countType
	 *            the countType to set
	 */
	public void setCountType(String countType) {
		this.countType = countType;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public void setKanbanReorderQuantity(BigDecimal kanbanReorderQuantity) {
		this.kanbanReorderQuantity = kanbanReorderQuantity;
	}

	public void setLabelSpec(String labelSpec) {
		this.labelSpec = labelSpec;
	}

	public void setLeadTimeDays(BigDecimal leadTimeDays) {
		this.leadTimeDays = leadTimeDays;
	}

	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}

	public void setMaterialHandlingCode(String materialHandlingCode) {
		this.materialHandlingCode = materialHandlingCode;
	}

	public void setMaterialIdString(String materialIdString) {
		this.materialIdString = materialIdString;
	}

	public void setMfgDesc(String mfgDesc) {
		this.mfgDesc = mfgDesc;
	}

	public void setOldCountType(String oldCountType) {
		this.oldCountType = oldCountType;
	}

	public void setOldStatus(String oldStatus) {
		this.oldStatus = oldStatus;
	}

	public void setOrderingApplication(String orderingApplication) {
		this.orderingApplication = orderingApplication;
	}

	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}

	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}

	public void setPartGroupNo(BigDecimal partGroupNo) {
		this.partGroupNo = partGroupNo;
	}

	public void setReorderPoint(BigDecimal reorderPoint) {
		this.reorderPoint = reorderPoint;
	}

	public void setReorderQuantity(BigDecimal reorderQuantity) {
		this.reorderQuantity = reorderQuantity;
	}

	// setters
	public void setSourceHub(String sourceHub) {
		this.sourceHub = sourceHub;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setStockingLevel(BigDecimal stockingLevel) {
		this.stockingLevel = stockingLevel;
	}

	public void setUseApplication(String useApplication) {
		this.useApplication = useApplication;
	}

	/**
	 * Setter for property workareaRowspan.
	 * 
	 * @param workareaRowspan
	 *            New value of property workareaRowspan.
	 */
	public void setWorkareaRowspan(int workareaRowspan) {
		this.workareaRowspan = workareaRowspan;
	}

	public Date getLevelHoldEndDate() {
		return levelHoldEndDate;
	}

	public void setLevelHoldEndDate(Date levelHoldEndDate) {
		this.levelHoldEndDate = levelHoldEndDate;
	}

    public String getPutAwayMethodOverride() {
        return putAwayMethodOverride;
    }

    public void setPutAwayMethodOverride(String putAwayMethodOverride) {
        this.putAwayMethodOverride = putAwayMethodOverride;
    }

    public String getDropShipOverride() {
        return dropShipOverride;
    }

    public void setDropShipOverride(String dropShipOverride) {
        this.dropShipOverride = dropShipOverride;
    }
}