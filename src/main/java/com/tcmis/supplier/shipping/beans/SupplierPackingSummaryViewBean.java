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
 * CLASSNAME: SupplierPackingSummaryViewBean <br>
 * @version: 1.0, Oct 30, 2007 <br>
 *****************************************************************************/


public class SupplierPackingSummaryViewBean extends BaseDataBean {

	private String companyId;
	private String supplier;
	private String shipFromLocationId;
	private String shipFromLocationName;
	private String shipToLocationName;
	private String shipToCityCommaState;
	private String shipToZipcode;
	private BigDecimal picklistId;
	private Date picklistPrintDate;
	private String shipToLocationId;
	private String ultimateShipToDodaac;
	private BigDecimal prNumber;
	private String lineItem;
	private BigDecimal radianPo;
	private BigDecimal poLine;
	private String catPartNo;
	private String partShortName;
	private BigDecimal itemId;
	private String itemDesc;
	private String packaging;
	private BigDecimal quantity;
	private BigDecimal numberOfBoxes;	
	private BigDecimal priorityRating;
  private BigDecimal receiptId;
  private String ok;
	private String shippedAsSingle;
	private String requiresOverpack;
  private String skipUnitLabels;
	private String oconus;
  private String supplierSalesOrderNo;
  private String rliShiptoLocId;
  private String inOutboundAsn;
  private String trackSerialNumber;
  private BigDecimal numOfSerialNumber;
  private BigDecimal numOfPalletId;
  
  
  public BigDecimal getNumOfPalletId() {
	  return numOfPalletId;
  }

  public void setNumOfPalletId(BigDecimal numOfPalletId) {
	  this.numOfPalletId = numOfPalletId;
  }   

  public BigDecimal getNumOfSerialNumber() {
	  return numOfSerialNumber;
  }

  public void setNumOfSerialNumber(BigDecimal numOfSerialNumber) {
	  this.numOfSerialNumber = numOfSerialNumber;
  }   

  public String getTrackSerialNumber() {
	  return trackSerialNumber;
  }

  public void setTrackSerialNumber(String trackSerialNumber) {
	  this.trackSerialNumber = trackSerialNumber;
  }  
  
  public String getRliShiptoLocId() {
    return rliShiptoLocId;
  }

  public void setRliShiptoLocId(String rliShiptoLocId) {
    this.rliShiptoLocId = rliShiptoLocId;
  }

//constructor
	public SupplierPackingSummaryViewBean() {
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public void setShipFromLocationId(String shipFromLocationId) {
		this.shipFromLocationId = shipFromLocationId;
	}
	public void setShipFromLocationName(String shipFromLocationName) {
		this.shipFromLocationName = shipFromLocationName;
	}
	public void setShipToLocationName(String shipToLocationName) {
		this.shipToLocationName = shipToLocationName;
	}
	public void setShipToCityCommaState(String shipToCityCommaState) {
		this.shipToCityCommaState = shipToCityCommaState;
	}
	public void setShipToZipcode(String shipToZipcode) {
		this.shipToZipcode = shipToZipcode;
	}
	public void setPicklistId(BigDecimal picklistId) {
		this.picklistId = picklistId;
	}
	public void setPicklistPrintDate(Date picklistPrintDate) {
		this.picklistPrintDate = picklistPrintDate;
	}
	public void setShipToLocationId(String shipToLocationId) {
		this.shipToLocationId = shipToLocationId;
	}
	public void setShipToLocationID(String shipToLocationId) {
		this.shipToLocationId = shipToLocationId;
	}
	public void setUltimateShipToDodaac(String ultimateShipToDodaac) {
		this.ultimateShipToDodaac = ultimateShipToDodaac;
	}
	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}
	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}
	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}
	public void setPoLine(BigDecimal poLine) {
		this.poLine = poLine;
	}
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}
	public void setPartShortName(String partShortName) {
		this.partShortName = partShortName;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public void setNumberOfBoxes(BigDecimal numberOfBoxes) {
		this.numberOfBoxes = numberOfBoxes;
	}
	public void setPriorityRating(BigDecimal priorityRating) {
		this.priorityRating = priorityRating;
	}
	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}
	public void setShippedAsSingle(String shippedAsSingle) {
		this.shippedAsSingle = shippedAsSingle;
	}
	public void setRequiresOverpack(String requiresOverpack) {
		this.requiresOverpack = requiresOverpack;
	}
  public void setSkipUnitLabels(String skipUnitLabels) {
    this.skipUnitLabels = skipUnitLabels;
  }
	public void setOconus(String oconus) {
		this.oconus = oconus;
	}
	public void setSupplierSalesOrderNo(String supplierSalesOrderNo) {
		this.supplierSalesOrderNo = supplierSalesOrderNo;
	}

  //getters
	public String getCompanyId() {
		return companyId;
	}
	public String getSupplier() {
		return supplier;
	}
	public String getShipFromLocationId() {
		return shipFromLocationId;
	}
	public String getShipFromLocationName() {
		return shipFromLocationName;
	}
	public String getShipToLocationName() {
		return shipToLocationName;
	}
	public String getShipToCityCommaState() {
		return shipToCityCommaState;
	}
	public String getShipToZipcode() {
		return shipToZipcode;
	}
	public BigDecimal getPicklistId() {
		return picklistId;
	}
	public Date getPicklistPrintDate() {
		return picklistPrintDate;
	}
	public String getShipToLocationId() {
		return shipToLocationId;
	}
	public String getShipToLocationID() {
		return shipToLocationId;
	}
	public String getUltimateShipToDodaac() {
		return ultimateShipToDodaac;
	}
	public BigDecimal getPrNumber() {
		return prNumber;
	}
	public String getLineItem() {
		return lineItem;
	}
	public BigDecimal getRadianPo() {
		return radianPo;
	}
	public BigDecimal getPoLine() {
		return poLine;
	}
	public String getCatPartNo() {
		return catPartNo;
	}
	public String getPartShortName() {
		return partShortName;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public String getPackaging() {
		return packaging;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public BigDecimal getNumberOfBoxes() {
		return numberOfBoxes;
	}
	public BigDecimal getPriorityRating() {
		return priorityRating;
	}
  public BigDecimal getReceiptId() {
		return receiptId;
	}
	public String getShippedAsSingle() {
		return shippedAsSingle;
	}
	public String getRequiresOverpack() {
		return requiresOverpack;
	}
  public String getSkipUnitLabels() {
    return skipUnitLabels;
  }
	public String getOconus() {
		return oconus;
	}
  public String getSupplierSalesOrderNo() {
		return supplierSalesOrderNo;
	}
  
  public String getOk() {
		return ok;
	}

	public void setOk(String ok) {
		this.ok = ok;
	}

	public String getInOutboundAsn() {
		return inOutboundAsn;
	}

	public void setInOutboundAsn(String outboundAsn) {
		inOutboundAsn = outboundAsn;
	}  
}