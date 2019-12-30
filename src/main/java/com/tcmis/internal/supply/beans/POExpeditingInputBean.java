package com.tcmis.internal.supply.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;


/**
* Change History --------- 03/13/09 - Shahzad Butt - Added new input variables.
* 
* 
*/
public class POExpeditingInputBean extends BaseDataBean 
{
   
	private static final long serialVersionUID = -2436237658041103069L;
	private String[] hubIdArray;
    private String inventoryGroup;
    private String buyerId;
    private String searchField;
    private String searchMode;
    private String searchArgument;
    private String supplier;
    private Date shipFromDate;
    private Date shipToDate;
    private String sortBy;
    private String expediteAge;
    private Date orderFromDate;
    private Date orderToDate;
    private Date revisedPromisedFromDate;
    private Date revisedPromisedToDate;
    private String supplyPath;
    private String openPosOnly;
    private BigDecimal radianPo;
    private BigDecimal poLine;
    private String creditHold;
    
    public String getCreditHold() {
		return creditHold;
	}


	public void setCreditHold(String creditHold) {
		this.creditHold = creditHold;
	}

    
    public POExpeditingInputBean()
	{
	}

   
    public String getSearchMode() {
        return searchMode;
    }

    public void setSearchMode(String searchMode) {
        this.searchMode = searchMode;
    }

    public String getSearchArgument() {
        return searchArgument;
    }

    public void setSearchArgument(String searchArgument) {
        this.searchArgument = searchArgument;
    }
    public String[] getHubIdArray() {
    return hubIdArray;
}

    public void setHubIdArray(String[] hubIdArray) {
        this.hubIdArray = hubIdArray;
    }

    public String getInventoryGroup() {
        return inventoryGroup;
    }

    public void setInventoryGroup(String inventoryGroup) {
        this.inventoryGroup = inventoryGroup;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getSearchField() {
        return searchField;
    }

    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public Date getShipFromDate() {
        return shipFromDate;
    }

    public void setShipFromDate(Date shipFromDate) {
        this.shipFromDate = shipFromDate;
    }

    public Date getShipToDate() {
        return shipToDate;
    }

    public void setShipToDate(Date shipToDate) {
        this.shipToDate = shipToDate;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }


	


	public Date getOrderFromDate() {
		return orderFromDate;
	}


	public void setOrderFromDate(Date orderFromDate) {
		this.orderFromDate = orderFromDate;
	}


	public Date getOrderToDate() {
		return orderToDate;
	}


	public void setOrderToDate(Date orderToDate) {
		this.orderToDate = orderToDate;
	}


	public String getSupplyPath() {
		return supplyPath;
	}


	public void setSupplyPath(String supplyPath) {
		this.supplyPath = supplyPath;
	}


	public String getOpenPosOnly() {
		return openPosOnly;
	}


	public void setOpenPosOnly(String openPosOnly) {
		this.openPosOnly = openPosOnly;
	}


	/**
	 * @param expediteAge the expediteAge to set
	 */
	public void setExpediteAge(String expediteAge) {
		this.expediteAge = expediteAge;
	}


	/**
	 * @return the expediteAge
	 */
	public String getExpediteAge() {
		return expediteAge;
	}


	/**
	 * @return the radianPo
	 */
	public BigDecimal getRadianPo() {
		return radianPo;
	}


	/**
	 * @param radianPo the radianPo to set
	 */
	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}


	/**
	 * @return the poLine
	 */
	public BigDecimal getPoLine() {
		return poLine;
	}


	/**
	 * @param poLine the poLine to set
	 */
	public void setPoLine(BigDecimal poLine) {
		this.poLine = poLine;
	}
	
    public Date getRevisedPromisedFromDate() {
        return revisedPromisedFromDate;
    }

    public void setrevisedPromisedFromDate(Date revisedPromisedFromDate) {
        this.revisedPromisedFromDate = revisedPromisedFromDate;
    }

    public Date getRevisedPromisedToDate() {
        return revisedPromisedToDate;
    }

    public void setrevisedPromisedToDate(Date revisedPromisedToDate) {
        this.revisedPromisedToDate = revisedPromisedToDate;
    }	
}