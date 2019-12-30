package com.tcmis.client.report.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: BatchReportBean <br>
 * @version: 1.0, Sep 13, 2006 <br>
 *****************************************************************************/


public class BatchReportBean extends BaseDataBean {

	private BigDecimal personnelId;
	private Date reportDate;
	private String reportName;
	private String content;
	private String status;


	//constructor
	public BatchReportBean() {
	}

	//setters
	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
	}
	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setStatus(String status) {
		this.status = status;
	}


	//getters
	public BigDecimal getPersonnelId() {
		return personnelId;
	}
	public Date getReportDate() {
		return reportDate;
	}
	public String getReportName() {
		return reportName;
	}
	public String getContent() {
		return content;
	}
	public String getStatus() {
		return status;
	}
}