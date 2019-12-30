package com.tcmis.client.report.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: ReportTemplateFieldBean <br>
 * @version: 1.0, Mar 28, 2008 <br>
 *****************************************************************************/


public class ReportTemplateFieldBean extends BaseDataBean {

	private BigDecimal templateFieldId;
	private BigDecimal reportTemplateId;
	private String reportField;


	//constructor
	public ReportTemplateFieldBean() {
	}

	//setters
	public void setTemplateFieldId(BigDecimal templateFieldId) {
		this.templateFieldId = templateFieldId;
	}
	public void setReportTemplateId(BigDecimal reportTemplateId) {
		this.reportTemplateId = reportTemplateId;
	}
	public void setReportField(String reportField) {
		this.reportField = reportField;
	}


	//getters
	public BigDecimal getTemplateFieldId() {
		return templateFieldId;
	}
	public BigDecimal getReportTemplateId() {
		return reportTemplateId;
	}
	public String getReportField() {
		return reportField;
	}
}