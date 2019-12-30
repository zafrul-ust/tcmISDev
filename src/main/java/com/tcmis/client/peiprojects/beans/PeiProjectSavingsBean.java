package com.tcmis.client.peiprojects.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: PeiProjectSavingsBean <br>
 * @version: 1.0, Dec 15, 2005 <br>
 *****************************************************************************/


public class PeiProjectSavingsBean extends BaseDataBean {

	private BigDecimal projectId;
	private BigDecimal periodId;
	private BigDecimal projectedPeriodSavings;
	private BigDecimal actualPeriodSavings;
	private String currencyId;
	private String periodName;
	private String lineStatus;
	private String companyId;
	//constructor
	public PeiProjectSavingsBean() {
	}

	//setters
	public void setProjectId(BigDecimal projectId) {
		this.projectId = projectId;
	}
	public void setPeriodId(BigDecimal periodId) {
		this.periodId = periodId;
	}
	public void setProjectedPeriodSavings(BigDecimal projectedPeriodSavings) {
		this.projectedPeriodSavings = projectedPeriodSavings;
	}
	public void setActualPeriodSavings(BigDecimal actualPeriodSavings) {
		this.actualPeriodSavings = actualPeriodSavings;
	}
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}
	public void setPeriodName(String periodName) {
		this.periodName = periodName;
	}
  public void setLineStatus(String lineStatus) {
	 this.lineStatus = lineStatus;
	}
	public void setCompanyId(String companyId) {
	 this.companyId = companyId;
	}

	//getters
	public BigDecimal getProjectId() {
		return projectId;
	}
	public BigDecimal getPeriodId() {
		return periodId;
	}
	public BigDecimal getProjectedPeriodSavings() {
		return projectedPeriodSavings;
	}
	public BigDecimal getActualPeriodSavings() {
		return actualPeriodSavings;
	}
	public String getCurrencyId() {
		return currencyId;
	}
	public String getPeriodName() {
		return periodName;
	}
  public String getLineStatus() {
	 return lineStatus;
	}
	public String getCompanyId() {
	 return companyId;
	}
}