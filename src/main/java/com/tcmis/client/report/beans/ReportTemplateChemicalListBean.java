package com.tcmis.client.report.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: ReportTemplateChemicalListBean <br>
 * @version: 1.0, Mar 28, 2008 <br>
 *****************************************************************************/


public class ReportTemplateChemicalListBean extends BaseDataBean {

	private BigDecimal templateFieldId;
	private BigDecimal reportTemplateId;
	private String chemicalList;


	//constructor
	public ReportTemplateChemicalListBean() {
	}

	//setters
	public void setTemplateFieldId(BigDecimal templateFieldId) {
		this.templateFieldId = templateFieldId;
	}
	public void setReportTemplateId(BigDecimal reportTemplateId) {
		this.reportTemplateId = reportTemplateId;
	}
	public void setChemicalList(String chemicalList) {
		this.chemicalList = chemicalList;
	}


	//getters
	public BigDecimal getTemplateFieldId() {
		return templateFieldId;
	}
	public BigDecimal getReportTemplateId() {
		return reportTemplateId;
	}
	public String getChemicalList() {
		return chemicalList;
	}
}