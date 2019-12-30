package com.tcmis.supplier.dbuy.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: DbuyMatchingDisplayViewBean <br>
 * @version: 1.0, Apr 26, 2006 <br>
 *****************************************************************************/


public class DbuyMatchingDisplayViewBean extends BaseDataBean {

	private BigDecimal radianPo;
	private BigDecimal poLine;
	private BigDecimal itemId;
	private String itemShortDesc;
	private String supplierName;
	private BigDecimal quantity;
	private BigDecimal unitPrice;
	private Date needByDate;
	private String shiptoAddressLine1;
	private String shiptoAddressLine2;
	private String shiptoCity;
	private String shiptoZip;


	//constructor
	public DbuyMatchingDisplayViewBean() {
	}

	//setters
	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}
	public void setPoLine(BigDecimal poLine) {
		this.poLine = poLine;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setItemShortDesc(String itemShortDesc) {
		this.itemShortDesc = itemShortDesc;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	public void setNeedByDate(Date needByDate) {
		this.needByDate = needByDate;
	}
	public void setShiptoAddressLine1(String shiptoAddressLine1) {
		this.shiptoAddressLine1 = shiptoAddressLine1;
	}
	public void setShiptoAddressLine2(String shiptoAddressLine2) {
		this.shiptoAddressLine2 = shiptoAddressLine2;
	}
	public void setShiptoCity(String shiptoCity) {
		this.shiptoCity = shiptoCity;
	}
	public void setShiptoZip(String shiptoZip) {
		this.shiptoZip = shiptoZip;
	}


	//getters
	public BigDecimal getRadianPo() {
		return radianPo;
	}
	public BigDecimal getPoLine() {
		return poLine;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public String getItemShortDesc() {
		return itemShortDesc;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public Date getNeedByDate() {
		return needByDate;
	}
	public String getShiptoAddressLine1() {
		return shiptoAddressLine1;
	}
	public String getShiptoAddressLine2() {
		return shiptoAddressLine2;
	}
	public String getShiptoCity() {
		return shiptoCity;
	}
	public String getShiptoZip() {
		return shiptoZip;
	}
}