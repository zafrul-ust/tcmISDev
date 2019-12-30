package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: TempShipmentHistoryViewBean <br>
 * @version: 1.0, Apr 13, 2005 <br>
 *****************************************************************************/

public class ShipmentHistoryInputBean
    extends BaseDataBean {

  private String shipmentId;
  private String[] deliveredDate;
  private String opsEntityId;
  private String hub;
  private String inventoryGroup;
  private String dock;
  private String companyId;
  private String facilityId;
  private String shipmentOrTracking;
  private Date toDate;
  private Date fromDate;
  private String[] trackingNumber;
  private String[] carrierCode;
  private String[] accountNumber;
  private String[] accountOwner;
  private BigDecimal[] shipmentIdArray;
  
  private BigDecimal prNumber;
  private BigDecimal customerId;
  
  private String action;

  public String getAction() {
	return action;
}

public void setAction(String action) {
	this.action = action;
}

//constructor
  public ShipmentHistoryInputBean() {
  }

  //setters
  public void setShipmentId(String shipmentId) {
    this.shipmentId = shipmentId;
  }

  public void setDeliveredDate(String[] deliveredDate) {
    this.deliveredDate = deliveredDate;
  }

  public void setInventoryGroup(String inventoryGroup) {
    this.inventoryGroup = inventoryGroup;
  }

  public void setTrackingNumber(String[] trackingNumber) {
    this.trackingNumber = trackingNumber;
  }

  public void setCarrierCode(String[] carrierCode) {
    this.carrierCode = carrierCode;
  }

  public void setAccountNumber(String[] accountNumber) {
    this.accountNumber = accountNumber;
  }

  public void setAccountOwner(String[] accountOwner) {
    this.accountOwner = accountOwner;
  }

  public void setDock(String dock) {
    this.dock = dock;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public void setFacilityId(String facilityId) {
    this.facilityId = facilityId;
  }

  public void setShipmentOrTracking(String shipmentOrTracking) {
    this.shipmentOrTracking = shipmentOrTracking;
  }

  public void setShipmentIdArray(BigDecimal[] shipmentIdArray) {
    this.shipmentIdArray = shipmentIdArray;
  }

  public void setToDate(Date toDate) {
    this.toDate = toDate;
  }

  public void setFromDate(Date fromDate) {
    this.fromDate = fromDate;
  }

  //getters
  public String getShipmentId() {
    return shipmentId;
  }

  public String[] getDeliveredDate() {
    return deliveredDate;
  }

  public String getHub() {
	return hub;
}

public void setHub(String hub) {
	this.hub = hub;
}

public String getOpsEntityId() {
	return opsEntityId;
}

public void setOpsEntityId(String opsEntityId) {
	this.opsEntityId = opsEntityId;
}

public String getInventoryGroup() {
    return inventoryGroup;
  }

  public String[] getTrackingNumber() {
    return trackingNumber;
  }

  public String[] getCarrierCode() {
    return carrierCode;
  }

  public String[] getAccountNumber() {
    return accountNumber;
  }

  public String[] getAccountOwner() {
    return accountOwner;
  }

  public String getDock() {
    return dock;
  }

  public BigDecimal[] getShipmentIdArray() {
    return shipmentIdArray;
  }

  public Date getToDate() {
    return toDate;
  }

  public Date getFromDate() {
    return fromDate;
  }

  public String getCompanyId() {
    return companyId;
  }

  public String getFacilityId() {
    return facilityId;
  }

  public String getShipmentOrTracking() {
    return shipmentOrTracking;
  }

public BigDecimal getPrNumber() {
	return prNumber;
}

public void setPrNumber(BigDecimal prNumber) {
	this.prNumber = prNumber;
}

public BigDecimal getCustomerId() {
	return customerId;
}

public void setCustomerId(BigDecimal customerId) {
	this.customerId = customerId;
}

}
