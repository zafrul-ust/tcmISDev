package com.tcmis.client.usgov.beans;

import java.math.BigDecimal;
import java.util.*;

import com.tcmis.common.framework.*;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * CLASSNAME: OrderTrackingInputBean <br>
 * @version: 1.0, Jul 22, 2004 <br>
 *****************************************************************************/

public class OrderTrackingInputBean
    extends BaseDataBean {

  private String orderType850;
  private String orderType940;
  private String openOrder;
  private String waiting;
  private String docNum;
  private String contractNumber;
  private String deliveryOrder;
  private String callNumber;
  private String dodaacType;
  private String ultimateDodaac;
  private String nsn;
  private String locationId;
  private String tcn;
  private String shipmentId;
  private BigDecimal prNumber;
  private String lineItem;
  private String searchArgument;
  private String searchField;
  private String searchMode;
  private String status;
  private Date dateFrom; 
  private Date dateTo;
  private String dateOpt;

  public String getShipmentId() {
    return shipmentId;
  }

  public void setShipmentId(String shipmentId) {
     this.shipmentId = shipmentId;
  }

  //constructor
  public OrderTrackingInputBean() {
  }

  //setters
  public void setOrderType850(String orderType850) {
    this.orderType850 = orderType850;
  }

  public void setOrderType940(String s) {
    this.orderType940 = s;
  }

  public void setOpenOrder(String s) {
    this.openOrder = s;
  }

  public void setWaiting(String s) {
    this.waiting = s;
  }

  public void setDocNum(String s) {
    this.docNum = s;
  }

  public void setContractNumber(String s) {
    this.contractNumber = s;
  }

  public void setDeliveryOrder(String s) {
    this.deliveryOrder = s;
  }

  public void setCallNumber(String s) {
    this.callNumber = s;
  }

  public void setDodaacType(String s) {
    this.dodaacType = s;
  }

  public void setUltimateDodaac(String s) {
    this.ultimateDodaac = s;
  }

  public void setNsn(String s) {
    this.nsn = s;
  }

  public void setTcn(String s) {
    this.tcn = s;
  }

  public void setLocationId(String s) {
    this.locationId = s;
  }

	public void setSearchArgument(String searchArgument) {
		this.searchArgument = searchArgument;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}
	public void setSearchMode(String searchMode) {
		this.searchMode = searchMode;
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
	public boolean hasSearchArgument() {
		return !StringHandler.isBlankString(searchArgument);
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
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}
	public String getSearchArgument() {
		return searchArgument;
	}

	public String getSearchField() {
		return searchField;
	}

	public String getSearchMode() {
		return searchMode;
	}
  public String getOrderType850() {
    return this.orderType850;
  }

  public String getOrderType940() {
    return this.orderType940;
  }

  public String getOpenOrder() {
    return this.openOrder;
  }

  public String getWaiting() {
    return this.waiting;
  }

  public String getDocNum() {
    return this.docNum;
  }

  public String getContractNumber() {
    return this.contractNumber;
  }

  public String getDeliveryOrder() {
    return this.deliveryOrder;
  }

  public String getCallNumber() {
    return this.callNumber;
  }

  public String getDodaacType() {
    return this.dodaacType;
  }

  public String getUltimateDodaac() {
    return this.ultimateDodaac;
  }

  public String getNsn() {
    return this.nsn;
  }

  public String getTcn() {
    return this.tcn;
  }

  public String getLocationId() {
    return this.locationId;
  }

public String getLineItem() {
	return lineItem;
}

public void setLineItem(String lineItem) {
	this.lineItem = lineItem;
}

public BigDecimal getPrNumber() {
	return prNumber;
}

public void setPrNumber(BigDecimal prNumber) {
	this.prNumber = prNumber;
}
}