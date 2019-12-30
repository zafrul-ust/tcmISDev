package com.tcmis.supplier.dbuy.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: EbuyDailyConfirmedOrdersBean <br>
 * @version: 1.0, Feb 16, 2007 <br>
 *****************************************************************************/


public class EbuyDailyConfirmedOrdersBean extends BaseDataBean {

	private String supplyPath;
	private String supplier;
	private BigDecimal radianPo;
	private Date promisedDate;
	private Date shipDate;
	private Date dateConfirmed;


	//constructor
	public EbuyDailyConfirmedOrdersBean() {
	}

	//setters
	public void setSupplyPath(String supplyPath) {
		this.supplyPath = supplyPath;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}
	public void setPromisedDate(Date promisedDate) {
		this.promisedDate = promisedDate;
	}
	public void setShipDate(Date shipDate) {
		this.shipDate = shipDate;
	}
	public void setDateConfirmed(Date dateConfirmed) {
		this.dateConfirmed = dateConfirmed;
	}


	//getters
	public String getSupplyPath() {
		return supplyPath;
	}
	public String getSupplier() {
		return supplier;
	}
	public BigDecimal getRadianPo() {
		return radianPo;
	}
	public Date getPromisedDate() {
		return promisedDate;
	}
	public Date getShipDate() {
		return shipDate;
	}
	public Date getDateConfirmed() {
		return dateConfirmed;
	}
}