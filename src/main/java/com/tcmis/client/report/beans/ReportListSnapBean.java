package com.tcmis.client.report.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: ReportListSnapBean <br>
 * @version: 1.0, Nov 22, 2005 <br>
 *****************************************************************************/


public class ReportListSnapBean extends BaseDataBean {

	private String listId;
	private String parentListId;
	private String childListId;
	private BigDecimal listLevel;
	private String casNumber;
	private String rptChemical;
	private BigDecimal threshold;
	private String thresholdUnit;


	//constructor
	public ReportListSnapBean() {
	}

	//setters
	public void setListId(String listId) {
		this.listId = listId;
	}
	public void setParentListId(String parentListId) {
		this.parentListId = parentListId;
	}
	public void setChildListId(String childListId) {
		this.childListId = childListId;
	}
	public void setListLevel(BigDecimal listLevel) {
		this.listLevel = listLevel;
	}
	public void setCasNumber(String casNumber) {
		this.casNumber = casNumber;
	}
	public void setRptChemical(String rptChemical) {
		this.rptChemical = rptChemical;
	}
	public void setThreshold(BigDecimal threshold) {
		this.threshold = threshold;
	}
	public void setThresholdUnit(String thresholdUnit) {
		this.thresholdUnit = thresholdUnit;
	}


	//getters
	public String getListId() {
		return listId;
	}
	public String getParentListId() {
		return parentListId;
	}
	public String getChildListId() {
		return childListId;
	}
	public BigDecimal getListLevel() {
		return listLevel;
	}
	public String getCasNumber() {
		return casNumber;
	}
	public String getRptChemical() {
		return rptChemical;
	}
	public BigDecimal getThreshold() {
		return threshold;
	}
	public String getThresholdUnit() {
		return thresholdUnit;
	}
}