package com.tcmis.supplier.dbuy.beans;

import java.util.Date; 
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: DbuyContractPriceBean <br>
 * @version: 1.0, Jul 19, 2006 <br>
 *****************************************************************************/


public class DbuyContractPriceBean extends BaseDataBean {

	private BigDecimal dbuyContractId;
	private Date endDate;
	private BigDecimal uptoQuantity;
	private BigDecimal unitPrice;
	private String currencyId;
	private String comments;


	//constructor
	public DbuyContractPriceBean() {
	}

	//setters
	public void setDbuyContractId(BigDecimal dbuyContractId) {
		this.dbuyContractId = dbuyContractId;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public void setUptoQuantity(BigDecimal uptoQuantity) {
		this.uptoQuantity = uptoQuantity;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}


	//getters
	public BigDecimal getDbuyContractId() {
		return dbuyContractId;
	}
	public Date getEndDate() {
		return endDate;
	}
	public BigDecimal getUptoQuantity() {
		return uptoQuantity;
	}
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public String getCurrencyId() {
		return currencyId;
	}
	public String getComments() {
		return comments;
	}
}