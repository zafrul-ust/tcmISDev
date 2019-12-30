package com.tcmis.supplier.shipping.beans;


	import java.math.BigDecimal;
import java.util.*;

import com.tcmis.common.framework.*;
import com.tcmis.common.util.StringHandler;

	/******************************************************************************
	 * CLASSNAME: HubCabinetViewBean <br>
	 * @version: 1.0, Jul 22, 2004 <br>
	 *****************************************************************************/

	public class SupplierShippingInputBean 
	    extends BaseDataBean {
	  private String supplier;
	  private String searchField;
	  private String searchMode;
	  private String shipFromLocationId;
	  private String sort;
	  private String showhistory;
	  private String searchArgument;
	  private BigDecimal personnelId;
    private BigDecimal picklistId;
    private String[] suppLocationIdArray;
    private String status;
	  private Date dateSentBegin;
	  private Date dateSentEnd;
	  private String openOrdersOnly;
    private String contractNumber;
	  private Date desiredShipDateBegin;
    private Date desiredShipDateEnd;
    private String supplierSalesOrderNo;
    private String selShipFromLocationId;
    private Date dateFrom; 
    private Date dateTo;
    private String dateOpt;
	private String hub;
	private String orderStatus;
    
	public String getHub() {
		return hub;
	}
	public void setHub(String hub) {
		this.hub = hub;
	}
	public boolean hasHub() {
		return !StringHandler.isBlankString(getHub());
	}	
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}		
	public void setDateOpt(String dateOpt) {
		this.dateOpt = dateOpt;
	}
	public boolean hasDateFrom() {
		return null != dateFrom;
	}
	public boolean hasDateTo() {
		return null != dateTo;
	}
	public String getDateOpt() {
		return dateOpt;
	}	
	public Date getDateFrom() {
		return dateFrom;
	}
	public Date getDateTo() {
		return dateTo;
	}    
    
    public String getSelShipFromLocationId() {
        return selShipFromLocationId;
      }

      public void setSelShipFromLocationId(String selShipFromLocationId) {
        this.selShipFromLocationId = selShipFromLocationId;
      }    
    
    public String getSupplierSalesOrderNo() {
        return supplierSalesOrderNo;
      }

      public void setSupplierSalesOrderNo(String supplierSalesOrderNo) {
        this.supplierSalesOrderNo = supplierSalesOrderNo;
      }

    public Date getDesiredShipDateBegin() {
      return desiredShipDateBegin;
    }

    public void setDesiredShipDateBegin(Date desiredShipDateBegin) {
      this.desiredShipDateBegin = desiredShipDateBegin;
    }

    public Date getDesiredShipDateEnd() {
      return desiredShipDateEnd;
    }

    public void setDesiredShipDateEnd(Date desiredShipDateEnd) {
      this.desiredShipDateEnd = desiredShipDateEnd;
    }

    public String getContractNumber() {
      return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
      this.contractNumber = contractNumber;
    }

    public String getOpenOrdersOnly() {
      return openOrdersOnly;
    }

    public void setOpenOrdersOnly(String openOrdersOnly) {
      this.openOrdersOnly = openOrdersOnly;
    }

    public Date getDateSentBegin() {
      return dateSentBegin;
    }

    public void setDateSentBegin(Date dateSentBegin) {
      this.dateSentBegin = dateSentBegin;
    }

    public Date getDateSentEnd() {
      return dateSentEnd;
    }

    public void setDateSentEnd(Date dateSentEnd) {
      this.dateSentEnd = dateSentEnd;
    }

    public String getStatus() {
      return status;
    }

    public void setStatus(String status) {
      this.status = status;
    }

    public String[] getSuppLocationIdArray() {
      return suppLocationIdArray;
    }

    public void setSuppLocationIdArray(String[] suppLocationIdArray) {
      this.suppLocationIdArray = suppLocationIdArray;
    }

    public BigDecimal getPicklistId() {
      return picklistId;
    }

    public void setPicklistId(BigDecimal picklistId) {
      this.picklistId = picklistId;
    }

    //constructor
	  public SupplierShippingInputBean() {
	  }

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String getSearchMode() {
		return searchMode;
	}

	public void setSearchMode(String searchMode) {
		this.searchMode = searchMode;
	}

	public String getShipFromLocationId() {
		return shipFromLocationId;
	}

	public void setShipFromLocationId(String shipFromLocationId) {
		this.shipFromLocationId = shipFromLocationId;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getShowhistory() {
		return showhistory;
	}

	public void setShowhistory(String showhistory) {
		this.showhistory = showhistory;
	}

	public String getSearchArgument() {
		return searchArgument;
	}

	public void setSearchArgument(String searchArgument) {
		this.searchArgument = searchArgument;
	}

	public BigDecimal getPersonnelId() {
		return personnelId;
	}

	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public boolean isAllOrderStatus() {
		return "all".equalsIgnoreCase(orderStatus);
	}
	public boolean isAllocatedOnlyOrderStatus() {
		return "allocatedOnly".equalsIgnoreCase(orderStatus);
	}
	public boolean isUnallocatedOnlyOrderStatus() {
		return "unallocatedOnly".equalsIgnoreCase(orderStatus);
	}
}
