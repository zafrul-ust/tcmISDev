package com.tcmis.internal.distribution.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: TmpPartSearchBean <br>
 * @version: 1.0, Aug 26, 2009 <br>
 *****************************************************************************/


public class PartSearchBean extends BaseDataBean {
    
    private String partShortName;
	private String partDescription;
	private String customerPartNo;
	private String specificCustPartNo;
	private String hazardous;
	private BigDecimal warehouseAvailable;
	private BigDecimal regionAvailable;
	private BigDecimal globalAvailable;
	private BigDecimal warehouseOnHand;
	private BigDecimal regionOnHand;
	private BigDecimal globalOnHand;
	private BigDecimal catalogPrice;
	private BigDecimal expectedCost;
	private BigDecimal orderCount;
	private Date lastOrdered;
	private BigDecimal lastPrice;
	private BigDecimal quoteCount;
	private Date lastQuoted;
	private BigDecimal lastQuotedPrice;
	private String currencyId;
	private BigDecimal itemId;
	private String alternateName;
	private String partNote;
	
	private String specList;
	private String specListId;
	private String itemType;
    private String unitOfMeasure;
    private BigDecimal unitsPerItem;
	private String catPartNo;
	
	private String specId;
    
    //constructor
	public PartSearchBean() {
	}

	//setters
	public void setPartShortName(String partShortName) {
		this.partShortName = partShortName;
	}
	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}
	public void setCustomerPartNo(String customerPartNo) {
		this.customerPartNo = customerPartNo;
	}
	public void setHazardous(String hazardous) {
		this.hazardous = hazardous;
	}
	public void setWarehouseAvailable(BigDecimal warehouseAvailable) {
		this.warehouseAvailable = warehouseAvailable;
	}
	public void setRegionAvailable(BigDecimal regionAvailable) {
		this.regionAvailable = regionAvailable;
	}
	public void setGlobalAvailable(BigDecimal globalAvailable) {
		this.globalAvailable = globalAvailable;
	}
	public void setCatalogPrice(BigDecimal catalogPrice) {
		this.catalogPrice = catalogPrice;
	}
	public void setExpectedCost(BigDecimal expectedCost) {
		this.expectedCost = expectedCost;
	}
	public void setOrderCount(BigDecimal orderCount) {
		this.orderCount = orderCount;
	}
	public void setLastOrdered(Date lastOrdered) {
		this.lastOrdered = lastOrdered;
	}
	public void setLastPrice(BigDecimal lastPrice) {
		this.lastPrice = lastPrice;
	}
	public void setQuoteCount(BigDecimal quoteCount) {
		this.quoteCount = quoteCount;
	}
	public void setLastQuoted(Date lastQuoted) {
		this.lastQuoted = lastQuoted;
	}
	public void setLastQuotedPrice(BigDecimal lastQuotedPrice) {
		this.lastQuotedPrice = lastQuotedPrice;
	}
    public void setItemType(String itemType) {
        this.itemType = itemType;
    }
    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }
    public void setUnitsPerItem(BigDecimal unitsPerItem) {
        this.unitsPerItem = unitsPerItem;
    }
    
    //getters
	public String getPartShortName() {
		return partShortName;
	}
	public String getPartDescription() {
		return partDescription;
	}
	public String getCustomerPartNo() {
		return customerPartNo;
	}
	public String getHazardous() {
		return hazardous;
	}
	public BigDecimal getWarehouseAvailable() {
		return warehouseAvailable;
	}
	public BigDecimal getRegionAvailable() {
		return regionAvailable;
	}
	public BigDecimal getGlobalAvailable() {
		return globalAvailable;
	}
	public BigDecimal getCatalogPrice() {
		return catalogPrice;
	}
	public BigDecimal getExpectedCost() {
		return expectedCost;
	}
	public BigDecimal getOrderCount() {
		return orderCount;
	}
	public Date getLastOrdered() {
		return lastOrdered;
	}
	public BigDecimal getLastPrice() {
		return lastPrice;
	}
	public BigDecimal getQuoteCount() {
		return quoteCount;
	}
	public Date getLastQuoted() {
		return lastQuoted;
	}
	public BigDecimal getLastQuotedPrice() {
		return lastQuotedPrice;
	}

	public String getAlternateName() {
		return alternateName;
	}

	public void setAlternateName(String alternateName) {
		this.alternateName = alternateName;
	}

    public String getItemType() {
        return itemType;
    }

    public BigDecimal getUnitsPerItem() {
        return unitsPerItem;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public String getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	public BigDecimal getGlobalOnHand() {
		return globalOnHand;
	}

	public void setGlobalOnHand(BigDecimal globalOnHand) {
		this.globalOnHand = globalOnHand;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public String getPartNote() {
		return partNote;
	}

	public void setPartNote(String partNote) {
		this.partNote = partNote;
	}

	public BigDecimal getRegionOnHand() {
		return regionOnHand;
	}

	public void setRegionOnHand(BigDecimal regionOnHand) {
		this.regionOnHand = regionOnHand;
	}

	public BigDecimal getWarehouseOnHand() {
		return warehouseOnHand;
	}

	public void setWarehouseOnHand(BigDecimal warehouseOnHand) {
		this.warehouseOnHand = warehouseOnHand;
	}

	public String getSpecList() {
		return specList;
	}
	public void setSpecList(String specList) {
		this.specList = specList;
	}
	public String getSpecListId() {
		return specListId;
	}
	public void setSpecListId(String specListId) {
		this.specListId = specListId;
	}
    public String getCatPartNo() {
		return catPartNo;
	}
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}

	public String getSpecificCustPartNo() {
		return specificCustPartNo;
	}

	public void setSpecificCustPartNo(String specificCustPartNo) {
		this.specificCustPartNo = specificCustPartNo;
	}

	public String getSpecId() {
		return specId;
	}

	public void setSpecId(String specId) {
		this.specId = specId;
	}
	

}