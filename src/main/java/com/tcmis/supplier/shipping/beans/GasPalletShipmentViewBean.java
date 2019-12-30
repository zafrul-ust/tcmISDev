package com.tcmis.supplier.shipping.beans;

import java.util.*;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.SQLException;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: GasPalletShipmentViewBean <br>
 * @version: 1.0, May 28, 2008 <br>
 *****************************************************************************/


public class GasPalletShipmentViewBean extends BaseDataBean {

	private String supplier;
	private String shipFromLocationId;
	private BigDecimal radianPo;
	private String shipViaLocationId;
	private String shipViaCompanyId;
	private String ultimateDodaac;
	private String shipViaDodaac;
	private BigDecimal receiptId;
	private BigDecimal issueId;
	private String supplierSalesOrderNo;
	private BigDecimal quantity;
	private String catPartNo;
	private String partShortName;
	private String currentCarrierName;
	private String currentTrackingNumber;
	private BigDecimal currentShipmentId;
	private String palletId;
	private BigDecimal prNumber;
	private String lineItem;
	private Collection shipmentIdDropDown;
	private String ok;
  private String shipFromLocationName;
  private String usgovTcn;
	private String shipToLocationName;
	private String shipToCityCommaState;
	private String shipToZipcode;
	private String customerPoNo;
  
  public String getShipFromLocationName() {
    return shipFromLocationName;
  }

  public void setShipFromLocationName(String shipFromLocationName) {
    this.shipFromLocationName = shipFromLocationName;
  }

  //constructor
	public GasPalletShipmentViewBean() {
	}

	//setters
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public void setShipFromLocationId(String shipFromLocationId) {
		this.shipFromLocationId = shipFromLocationId;
	}
	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}
	public void setShipViaLocationId(String shipViaLocationId) {
		this.shipViaLocationId = shipViaLocationId;
	}
	public void setShipViaCompanyId(String shipViaCompanyId) {
		this.shipViaCompanyId = shipViaCompanyId;
	}
	public void setUltimateDodaac(String ultimateDodaac) {
		this.ultimateDodaac = ultimateDodaac;
	}
	public void setShipViaDodaac(String shipViaDodaac) {
		this.shipViaDodaac = shipViaDodaac;
	}
	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}
	public void setIssueId(BigDecimal issueId) {
		this.issueId = issueId;
	}
	public void setSupplierSalesOrderNo(String supplierSalesOrderNo) {
		this.supplierSalesOrderNo = supplierSalesOrderNo;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}
	public void setPartShortName(String partShortName) {
		this.partShortName = partShortName;
	}
	public void setCurrentCarrierName(String currentCarrierName) {
		this.currentCarrierName = currentCarrierName;
	}
	public void setCurrentTrackingNumber(String currentTrackingNumber) {
		this.currentTrackingNumber = currentTrackingNumber;
	}
	public void setCurrentShipmentId(BigDecimal currentShipmentId) {
		this.currentShipmentId = currentShipmentId;
	}
	public void setPalletId(String palletId) {
		this.palletId = palletId;
	}
	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}
	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}


	//getters
	public String getSupplier() {
		return supplier;
	}
	public String getShipFromLocationId() {
		return shipFromLocationId;
	}
	public BigDecimal getRadianPo() {
		return radianPo;
	}
	public String getShipViaLocationId() {
		return shipViaLocationId;
	}
	public String getShipViaCompanyId() {
		return shipViaCompanyId;
	}
	public String getUltimateDodaac() {
		return ultimateDodaac;
	}
	public String getShipViaDodaac() {
		return shipViaDodaac;
	}
	public BigDecimal getReceiptId() {
		return receiptId;
	}
	public BigDecimal getIssueId() {
		return issueId;
	}
	public String getSupplierSalesOrderNo() {
		return supplierSalesOrderNo;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public String getCatPartNo() {
		return catPartNo;
	}
	public String getPartShortName() {
		return partShortName;
	}
	public String getCurrentCarrierName() {
		return currentCarrierName;
	}
	public String getCurrentTrackingNumber() {
		return currentTrackingNumber;
	}
	public BigDecimal getCurrentShipmentId() {
		return currentShipmentId;
	}
	public String getPalletId() {
		return palletId;
	}
	public BigDecimal getPrNumber() {
		return prNumber;
	}
	public String getLineItem() {
		return lineItem;
	}

	public Collection getShipmentIdDropDown() {
		return shipmentIdDropDown;
	}

	public void setShipmentIdDropDown(Collection shipmentIdDropDown) {
		this.shipmentIdDropDown = shipmentIdDropDown;
	}

  public String getUsgovTcn() {
    return usgovTcn;
  }

  public void setUsgovTcn(String usgovTcn) {
    this.usgovTcn = usgovTcn;
  }

  public String getShipToLocationName() {
    return shipToLocationName;
  }

  public void setShipToLocationName(String shipToLocationName) {
    this.shipToLocationName = shipToLocationName;
  }

  public String getShipToCityCommaState() {
    return shipToCityCommaState;
  }

  public void setShipToCityCommaState(String shipToCityCommaState) {
    this.shipToCityCommaState = shipToCityCommaState;
  }

  public String getShipToZipcode() {
    return shipToZipcode;
  }

  public void setShipToZipcode(String shipToZipcode) {
    this.shipToZipcode = shipToZipcode;
  }

  public String getOk() {
		return ok;
	}

	public void setOk(String ok) {
		this.ok = ok;
	}

	public String getCustomerPoNo() {
		return customerPoNo;
	}

	public void setCustomerPoNo(String customerPoNo) {
		this.customerPoNo = customerPoNo;
	}
}