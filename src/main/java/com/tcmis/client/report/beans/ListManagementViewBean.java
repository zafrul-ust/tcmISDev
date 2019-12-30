package com.tcmis.client.report.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

public class ListManagementViewBean extends BaseDataBean {
	
	private String companyId;
	private String listId;
	private String casNumber;
	private String rptChemical;
	private String ok;
	private boolean isAddLine;
	private String updated;
	private BigDecimal threshold;
	private BigDecimal threshold2;
	private BigDecimal threshold3;
	
	
		
	//constructor
	public ListManagementViewBean() {
	}
	
	public BigDecimal getThreshold() {
		return threshold;
	}
	
	public BigDecimal getThreshold2() {
		return threshold2;
	}
	
	public BigDecimal getThreshold3() {
		return threshold3;
	}
	
	public void setThreshold3(BigDecimal threshold3) {
		this.threshold3 = threshold3;
	}
	
	public void setThreshold2(BigDecimal threshold2) {
		this.threshold2 = threshold2;
	}
	
	public void setThreshold(BigDecimal threshold) {
		this.threshold = threshold;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getListId() {
		return listId;
	}

	public void setListId(String listId) {
		this.listId = listId;
	}

	public String getCasNumber() {
		return casNumber;
	}

	public void setCasNumber(String casNumber) {
		this.casNumber = casNumber;
	}

	public String getRptChemical() {
		return rptChemical;
	}

	public void setRptChemical(String rptChemical) {
		this.rptChemical = rptChemical;
	}

	public String getOk() {
		return ok;
	}

	public void setOk(String ok) {
		this.ok = ok;
	}
	
	
	
	public boolean getIsAddLine() {
		return isAddLine;
	}

	public void setIsAddLine(boolean isAddLine) {
		this.isAddLine =  isAddLine;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}
	
	

		

}
