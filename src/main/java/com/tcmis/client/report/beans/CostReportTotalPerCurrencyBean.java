package com.tcmis.client.report.beans;

import java.util.Date;
import java.util.Collection;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: CostReportTotalPerCurrencyBean <br>
 * @version: 1.0, Jul 15, 2008 <br>
 *****************************************************************************/


public class CostReportTotalPerCurrencyBean extends BaseDataBean {

	private String currencyId;
	private BigDecimal totalAddCharge;
	private BigDecimal totalSalesTax;
	private BigDecimal totalServiceFee;
	private BigDecimal totalNetAmount;
	private BigDecimal totalPeiAmount;
	private BigDecimal totalMaterialSaving;
	private BigDecimal totalFreightCharge;

	//constructor
	public CostReportTotalPerCurrencyBean() {
	}

	//setters

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	public void setTotalAddCharge(BigDecimal totalAddCharge) {
		this.totalAddCharge = totalAddCharge;
	}

	public void setTotalSalesTax(BigDecimal totalSalesTax) {
		this.totalSalesTax = totalSalesTax;
	}

	public void setTotalServiceFee(BigDecimal totalServiceFee) {
		this.totalServiceFee = totalServiceFee;
	}

	public void setTotalNetAmount(BigDecimal totalNetAmount) {
		this.totalNetAmount = totalNetAmount;
	}

	public void setTotalPeiAmount(BigDecimal totalPeiAmount) {
		this.totalPeiAmount = totalPeiAmount;
	}

	public void setTotalMaterialSaving(BigDecimal totalMaterialSaving) {
		this.totalMaterialSaving = totalMaterialSaving;
	}

	//getters
	public String getCurrencyId() {
		return currencyId;
	}

	public BigDecimal getTotalAddCharge() {
		return totalAddCharge;
	}

	public BigDecimal getTotalSalesTax() {
		return totalSalesTax;
	}

	public BigDecimal getTotalServiceFee() {
		return totalServiceFee;
	}

	public BigDecimal getTotalNetAmount() {
		return totalNetAmount;
	}

	public BigDecimal getTotalPeiAmount() {
		return totalPeiAmount;
	}

	public BigDecimal getTotalMaterialSaving() {
		return totalMaterialSaving;
	}

	public BigDecimal getTotalFreightCharge() {
		return totalFreightCharge;
	}

	public void setTotalFreightCharge(BigDecimal totalFreightCharge) {
		this.totalFreightCharge = totalFreightCharge;
	}
} //end of class